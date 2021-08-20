/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatCriteriaCombination
/*     */ {
/*     */   private ItemCriteriaCombination itemCombo;
/*     */   private SelectionCriteria criteria;
/*     */   
/*     */   public StatCriteriaCombination(SelectionCriteria criteria) {
/*  21 */     this.criteria = criteria;
/*  22 */     this.itemCombo = new ItemCriteriaCombination(criteria);
/*     */   }
/*     */   
/*     */   public StatCriteriaCombination(SelectionCriteria criteria, ItemCriteriaCombination combo) {
/*  26 */     this.criteria = criteria;
/*  27 */     this.itemCombo = combo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<SelectionCriteria.StatInfo> getStatInfoList() {
/*  35 */     return this.criteria.statInfos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemClass(String itemClass) {
/*  43 */     this.itemCombo.setItemClass(itemClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String determineItemParameters() {
/*  51 */     if (this.itemCombo == null) return null;
/*     */     
/*  53 */     return this.itemCombo.determineItemParameters();
/*     */   }
/*     */   
/*     */   public String determineItemIDStatement() {
/*  57 */     String command = "SELECT I.ITEM_ID FROM GD_ITEM I INNER JOIN GD_ITEM_STAT D ON I.ITEM_ID = S.ITEM_ID WHERE I.ITEM_CLASS = ?";
/*     */     
/*  59 */     if (this.itemCombo.usesItemRarity()) command = command + " AND I.RARITY = ?"; 
/*  60 */     if (this.itemCombo.usesArmorClass()) command = command + " AND I.ARMOR_CLASS = ?"; 
/*  61 */     if (this.itemCombo.usesArtifactClass()) command = command + " AND I.ARTIFACT_CLASS = ?"; 
/*  62 */     if (this.itemCombo.getMinLevel() != -1) command = command + " AND I.REQ_LEVEL >= ?"; 
/*  63 */     if (this.itemCombo.getMaxLevel() != -1) command = command + " AND I.REQ_LEVEL <= ?"; 
/*  64 */     if (this.itemCombo.getMaxCunning() != -1) command = command + " AND I.REQ_DEX <= ?"; 
/*  65 */     if (this.itemCombo.getMaxPhysique() != -1) command = command + " AND I.REQ_STR <= ?"; 
/*  66 */     if (this.itemCombo.getMaxSpirit() != -1) command = command + " AND I.REQ_INT <= ?"; 
/*  67 */     if (this.itemCombo.isNoEnemyOnly()) command = command + " AND I.ENEMY_ONLY = ?"; 
/*  68 */     command = command + this.itemCombo.determineSlotConditions("I.");
/*  69 */     command = command + " AND (S.STAT_TYPE = ? AND ((S.STAT_MIN > 0) OR (D.MODIFIER > 0)))";
/*     */     
/*  71 */     return command;
/*     */   }
/*     */   
/*     */   public String determineStashIDItemStatement(boolean isHardcore) {
/*  75 */     String command = "SELECT S.STASH_ID FROM STASH_ITEM_V8 SI, GD_ITEM I, GD_ITEM_STAT S WHERE SI.ITEM_ID = I.ITEM_ID AND I.ITEM_ID = S.ITEM_ID AND I.ITEM_CLASS = ?";
/*     */     
/*  77 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSCHC) {
/*  78 */       if (isHardcore) {
/*  79 */         command = command + " AND SI.HARDCORE = true";
/*     */       } else {
/*  81 */         command = command + " AND SI.HARDCORE = false";
/*     */       } 
/*     */     }
/*  84 */     if (this.itemCombo.usesItemRarity()) command = command + " AND I.RARITY = ?"; 
/*  85 */     if (this.itemCombo.usesArmorClass()) command = command + " AND I.ARMOR_CLASS = ?"; 
/*  86 */     if (this.itemCombo.usesArtifactClass()) command = command + " AND I.ARTIFACT_CLASS = ?"; 
/*  87 */     if (this.itemCombo.getMinLevel() != -1) command = command + " AND I.REQ_LEVEL >= ?"; 
/*  88 */     if (this.itemCombo.getMaxLevel() != -1) command = command + " AND I.REQ_LEVEL <= ?"; 
/*  89 */     if (this.itemCombo.getMaxCunning() != -1) command = command + " AND I.REQ_DEX <= ?"; 
/*  90 */     if (this.itemCombo.getMaxPhysique() != -1) command = command + " AND I.REQ_STR <= ?"; 
/*  91 */     if (this.itemCombo.getMaxSpirit() != -1) command = command + " AND I.REQ_INT <= ?"; 
/*  92 */     if (this.itemCombo.isNoEnemyOnly()) command = command + " AND I.ENEMY_ONLY = ?"; 
/*  93 */     command = command + this.itemCombo.determineSlotConditions("I.");
/*  94 */     command = command + " AND (S.STAT_TYPE = ? AND ((S.STAT_MIN > 0) OR (S.MODIFIER > 0)))";
/*     */     
/*  96 */     return command;
/*     */   }
/*     */   
/*     */   public int fillItemIDStatement(PreparedStatement ps) throws SQLException {
/* 100 */     if (this.criteria.statInfos == null) return -1;
/*     */     
/* 102 */     return this.itemCombo.fillItemStatement(ps);
/*     */   }
/*     */   
/*     */   public String determineStashIDPrefixStatement(boolean isHardcore) {
/* 106 */     String command = "SELECT S.STASH_ID FROM STASH_ITEM_V8 SI, GD_ITEM I, GD_AFFIX A, GD_AFFIX_STAT S WHERE SI.ITEM_ID = I.ITEM_ID AND SI.PREFIX_ID = A.AFFIX_ID AND A.TYPE = 1 AND A.AFFIX_ID = S.AFFIX_ID AND I.ITEM_CLASS = ?";
/*     */     
/* 108 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSCHC) {
/* 109 */       if (isHardcore) {
/* 110 */         command = command + " AND SI.HARDCORE = true";
/*     */       } else {
/* 112 */         command = command + " AND SI.HARDCORE = false";
/*     */       } 
/*     */     }
/* 115 */     if (this.itemCombo.usesArmorClass()) command = command + " AND I.ARMOR_CLASS = ?"; 
/* 116 */     if (this.itemCombo.usesArtifactClass()) command = command + " AND I.ARTIFACT_CLASS = ?"; 
/* 117 */     if (this.itemCombo.getMaxLevel() != -1) command = command + " AND I.REQ_LEVEL <= ?"; 
/* 118 */     if (this.itemCombo.getMaxCunning() != -1) command = command + " AND I.REQ_DEX <= ?"; 
/* 119 */     if (this.itemCombo.getMaxPhysique() != -1) command = command + " AND I.REQ_STR <= ?"; 
/* 120 */     if (this.itemCombo.getMaxSpirit() != -1) command = command + " AND I.REQ_INT <= ?"; 
/* 121 */     if (this.itemCombo.isNoEnemyOnly()) command = command + " AND I.ENEMY_ONLY = ?"; 
/* 122 */     if (this.itemCombo.usesItemRarity()) command = command + " AND A.RARITY = ?"; 
/* 123 */     if (this.itemCombo.getMinLevel() != -1) command = command + " AND A.REQ_LEVEL >= ?"; 
/* 124 */     if (this.itemCombo.getMaxLevel() != -1) command = command + " AND A.REQ_LEVEL <= ?"; 
/* 125 */     command = command + this.itemCombo.determineSlotConditions("I.");
/* 126 */     command = command + " AND (S.STAT_TYPE = ? AND ((S.STAT_MIN > 0) OR (S.MODIFIER > 0)))";
/*     */     
/* 128 */     return command;
/*     */   }
/*     */   
/*     */   public String determineStashIDSuffixStatement(boolean isHardcore) {
/* 132 */     String command = "SELECT S.STASH_ID FROM STASH_ITEM_V8 SI, GD_ITEM I, GD_AFFIX A, GD_AFFIX_STAT S WHERE SI.ITEM_ID = I.ITEM_ID AND SI.SUFFIX_ID = A.AFFIX_ID AND A.TYPE = 2 AND A.AFFIX_ID = S.AFFIX_ID AND I.ITEM_CLASS = ?";
/*     */     
/* 134 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSCHC) {
/* 135 */       if (isHardcore) {
/* 136 */         command = command + " AND SI.HARDCORE = true";
/*     */       } else {
/* 138 */         command = command + " AND SI.HARDCORE = false";
/*     */       } 
/*     */     }
/* 141 */     if (this.itemCombo.usesArmorClass()) command = command + " AND I.ARMOR_CLASS = ?"; 
/* 142 */     if (this.itemCombo.usesArtifactClass()) command = command + " AND I.ARTIFACT_CLASS = ?"; 
/* 143 */     if (this.itemCombo.getMaxLevel() != -1) command = command + " AND I.REQ_LEVEL <= ?"; 
/* 144 */     if (this.itemCombo.getMaxCunning() != -1) command = command + " AND I.REQ_DEX <= ?"; 
/* 145 */     if (this.itemCombo.getMaxPhysique() != -1) command = command + " AND I.REQ_STR <= ?"; 
/* 146 */     if (this.itemCombo.getMaxSpirit() != -1) command = command + " AND I.REQ_INT <= ?"; 
/* 147 */     if (this.itemCombo.isNoEnemyOnly()) command = command + " AND I.ENEMY_ONLY = ?"; 
/* 148 */     if (this.itemCombo.usesItemRarity()) command = command + " AND A.RARITY = ?"; 
/* 149 */     if (this.itemCombo.getMinLevel() != -1) command = command + " AND A.REQ_LEVEL >= ?"; 
/* 150 */     if (this.itemCombo.getMaxLevel() != -1) command = command + " AND A.REQ_LEVEL <= ?"; 
/* 151 */     command = command + this.itemCombo.determineSlotConditions("I.");
/* 152 */     command = command + " AND (S.STAT_TYPE = ? AND ((S.STAT_MIN > 0) OR (S.MODIFIER > 0)))";
/*     */     
/* 154 */     return command;
/*     */   }
/*     */   
/*     */   public int fillAffixIDStatement(PreparedStatement ps) throws SQLException {
/* 158 */     if (this.criteria.statInfos == null) return -1;
/*     */     
/* 160 */     ps.clearParameters();
/*     */     
/* 162 */     int nextPos = 1;
/*     */     
/* 164 */     ps.setString(nextPos, this.itemCombo.getItemClass());
/*     */     
/* 166 */     nextPos++;
/*     */     
/* 168 */     if (this.itemCombo.usesArmorClass()) {
/* 169 */       ps.setString(nextPos, this.itemCombo.getArmorClass());
/*     */       
/* 171 */       nextPos++;
/*     */     } 
/*     */     
/* 174 */     if (this.itemCombo.usesArtifactClass()) {
/* 175 */       ps.setString(nextPos, this.itemCombo.getArtifactClass());
/*     */       
/* 177 */       nextPos++;
/*     */     } 
/*     */     
/* 180 */     if (this.itemCombo.getMaxLevel() != -1) {
/* 181 */       ps.setInt(nextPos, this.itemCombo.getMaxLevel());
/*     */       
/* 183 */       nextPos++;
/*     */     } 
/*     */     
/* 186 */     if (this.itemCombo.getMaxCunning() != -1) {
/* 187 */       ps.setInt(nextPos, this.itemCombo.getMaxCunning());
/*     */       
/* 189 */       nextPos++;
/*     */     } 
/*     */     
/* 192 */     if (this.itemCombo.getMaxPhysique() != -1) {
/* 193 */       ps.setInt(nextPos, this.itemCombo.getMaxPhysique());
/*     */       
/* 195 */       nextPos++;
/*     */     } 
/*     */     
/* 198 */     if (this.itemCombo.getMaxSpirit() != -1) {
/* 199 */       ps.setInt(nextPos, this.itemCombo.getMaxSpirit());
/*     */       
/* 201 */       nextPos++;
/*     */     } 
/*     */     
/* 204 */     if (this.itemCombo.isNoEnemyOnly()) {
/* 205 */       ps.setBoolean(nextPos, false);
/*     */       
/* 207 */       nextPos++;
/*     */     } 
/*     */     
/* 210 */     if (this.itemCombo.usesItemRarity()) {
/* 211 */       ps.setString(nextPos, this.itemCombo.getItemRarity());
/*     */       
/* 213 */       nextPos++;
/*     */     } 
/*     */     
/* 216 */     if (this.itemCombo.getMinLevel() != -1) {
/* 217 */       ps.setInt(nextPos, this.itemCombo.getMinLevel());
/*     */       
/* 219 */       nextPos++;
/*     */     } 
/*     */     
/* 222 */     if (this.itemCombo.getMaxLevel() != -1) {
/* 223 */       ps.setInt(nextPos, this.itemCombo.getMaxLevel());
/*     */       
/* 225 */       nextPos++;
/*     */     } 
/*     */     
/* 228 */     return nextPos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<StatCriteriaCombination> createItemStatCombinations() {
/* 236 */     List<StatCriteriaCombination> list = new LinkedList<>();
/*     */     
/* 238 */     for (String itemClass : this.criteria.itemClass) {
/* 239 */       this.itemCombo.setItemClass(itemClass);
/*     */       
/* 241 */       List<ItemCriteriaCombination> itemCombos = this.itemCombo.createItemCombinations();
/*     */       
/* 243 */       for (ItemCriteriaCombination combo : itemCombos) {
/* 244 */         StatCriteriaCombination scc = new StatCriteriaCombination(this.criteria, combo);
/*     */         
/* 246 */         list.add(scc);
/*     */       } 
/*     */     } 
/*     */     
/* 250 */     return list;
/*     */   }
/*     */   
/*     */   public List<StatCriteriaCombination> createAffixStatCombinations() {
/* 254 */     List<StatCriteriaCombination> list = new LinkedList<>();
/*     */     
/* 256 */     for (String itemClass : this.criteria.itemClass) {
/* 257 */       this.itemCombo.setItemClass(itemClass);
/*     */       
/* 259 */       List<ItemCriteriaCombination> itemCombos = this.itemCombo.createAffixCombinations();
/*     */       
/* 261 */       for (ItemCriteriaCombination combo : itemCombos) {
/* 262 */         StatCriteriaCombination scc = new StatCriteriaCombination(this.criteria, combo);
/*     */         
/* 264 */         list.add(scc);
/*     */       } 
/*     */     } 
/*     */     
/* 268 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\StatCriteriaCombination.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */