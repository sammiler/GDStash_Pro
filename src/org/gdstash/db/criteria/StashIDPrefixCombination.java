/*     */ package org.gdstash.db.criteria;
/*     */ 
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import org.gdstash.db.SelectionCriteria;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StashIDPrefixCombination
/*     */   extends StashIDItemCombination
/*     */ {
/*     */   public StashIDPrefixCombination() {}
/*     */   
/*     */   public StashIDPrefixCombination(StashIDPrefixCombination combo) {
/*  30 */     super(combo);
/*     */   }
/*     */   
/*     */   public StashIDPrefixCombination(SelectionCriteria criteria) {
/*  34 */     super(criteria);
/*     */   }
/*     */ 
/*     */   
/*     */   public StashIDPrefixCombination clone() {
/*  39 */     StashIDPrefixCombination combo = new StashIDPrefixCombination(this);
/*     */     
/*  41 */     return combo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String determineLevel1Statement(CriteriaCombination.Soulbound soulbound, CriteriaCombination.SC_HC schc, boolean isHardcore, String charName) {
/*  50 */     String command = "SELECT S.STASH_ID FROM STASH_ITEM_V8 S, GD_AFFIX A";
/*     */     
/*  52 */     boolean statFilled = !(getCriteria()).statInfos.isEmpty();
/*     */     
/*  54 */     if (getCriteria().isSkillBonusUsed()) {
/*  55 */       command = command + ", " + "GD_AFFIX_SKILLS" + " ASB, " + "GD_AFFIX_SKILL_LEVEL" + " ASBL";
/*     */     }
/*     */     
/*  58 */     if (getCriteria().isSkillModifierUsed()) {
/*  59 */       command = command + ", " + "GD_AFFIX_SKILLMODIFIER" + " ASM";
/*     */     }
/*  61 */     if (statFilled) {
/*  62 */       command = command + ", " + "GD_AFFIX_STAT" + " AST";
/*     */     }
/*     */     
/*  65 */     command = command + " WHERE ";
/*     */     
/*  67 */     if (getCriteria().isSkillBonusUsed()) {
/*  68 */       command = command + "A.AFFIX_ID = ASB.AFFIX_ID AND A.AFFIX_ID = ASBL.AFFIX_ID AND ASB.TYPE = ASBL.TYPE AND ASB.INDEX = ASBL.INDEX AND ";
/*     */     }
/*     */     
/*  71 */     if (getCriteria().isSkillModifierUsed()) {
/*  72 */       command = command + "A.AFFIX_ID = ASM.AFFIX_ID AND ";
/*     */     }
/*  74 */     if (statFilled) {
/*  75 */       command = command + "A.AFFIX_ID = AST.AFFIX_ID AND ";
/*     */     }
/*     */     
/*  78 */     command = command + "S.PREFIX_ID = A.AFFIX_ID AND A.TYPE = 1 AND S.ITEM_CLASS = ?";
/*     */ 
/*     */     
/*  81 */     if (schc == CriteriaCombination.SC_HC.SOFTCORE) {
/*  82 */       command = command + " AND S.HARDCORE = false";
/*     */     }
/*  84 */     if (schc == CriteriaCombination.SC_HC.HARDCORE) {
/*  85 */       command = command + " AND S.HARDCORE = true";
/*     */     }
/*  87 */     if (schc == CriteriaCombination.SC_HC.SETTING && 
/*  88 */       !GDStashFrame.iniConfig.sectRestrict.transferSCHC) {
/*  89 */       if (isHardcore) {
/*  90 */         command = command + " AND S.HARDCORE = true";
/*     */       } else {
/*  92 */         command = command + " AND S.HARDCORE = false";
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  98 */     if (soulbound == CriteriaCombination.Soulbound.EXCLUDED) {
/*  99 */       command = command + " AND CHARNAME = ''";
/*     */     }
/*     */     
/* 102 */     if (soulbound == CriteriaCombination.Soulbound.INCLUDED) {
/* 103 */       if ((getCriteria()).soulboundMode == SelectionCriteria.SoulboundMode.NONBOUND) {
/* 104 */         command = command + " AND CHARNAME = ''";
/*     */       }
/*     */       
/* 107 */       if ((getCriteria()).soulboundMode == SelectionCriteria.SoulboundMode.SOULBOUND) {
/* 108 */         command = command + " AND CHARNAME <> ''";
/*     */       }
/*     */     } 
/*     */     
/* 112 */     if (soulbound == CriteriaCombination.Soulbound.SETTING) {
/* 113 */       if ((getCriteria()).soulboundMode == SelectionCriteria.SoulboundMode.ALL && 
/* 114 */         !GDStashFrame.iniConfig.sectRestrict.transferSoulbound) {
/* 115 */         if (charName == null) {
/* 116 */           command = command + " AND CHARNAME = ''";
/*     */         }
/* 118 */         else if (charName.isEmpty()) {
/* 119 */           command = command + " AND CHARNAME = ''";
/*     */         } else {
/* 121 */           command = command + " AND ( CHARNAME = '" + charName + "' OR CHARNAME = '' )";
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 127 */       if ((getCriteria()).soulboundMode == SelectionCriteria.SoulboundMode.NONBOUND) {
/* 128 */         command = command + " AND CHARNAME = ''";
/*     */       }
/*     */       
/* 131 */       if ((getCriteria()).soulboundMode == SelectionCriteria.SoulboundMode.SOULBOUND) {
/* 132 */         if (GDStashFrame.iniConfig.sectRestrict.transferSoulbound) {
/* 133 */           command = command + " AND CHARNAME <> ''";
/*     */         }
/* 135 */         else if (charName == null) {
/* 136 */           command = command + " AND CHARNAME = '___xxx___'";
/*     */         }
/* 138 */         else if (charName.isEmpty()) {
/* 139 */           command = command + " AND CHARNAME = '___xxx___'";
/*     */         } else {
/* 141 */           command = command + " AND CHARNAME = '" + charName + "'";
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 148 */     if (usesItemRarity()) command = command + " AND S.RARITY = ?"; 
/* 149 */     if (usesArmorClass()) command = command + " AND S.ARMOR_CLASS = ?"; 
/* 150 */     if (usesArtifactClass()) command = command + " AND S.ARTIFACT_CLASS = ?"; 
/* 151 */     if (usesItemName()) command = command + " AND S.NAME LIKE ?"; 
/* 152 */     if (usesSetItem()) command = command + " AND S.SET_ID IS NOT NULL"; 
/* 153 */     if (usesBonusSkill()) command = command + " AND ASB.BONUS_ENTITY = ? AND ASBL.BONUS_VALUE > 0"; 
/* 154 */     if (usesMasteryBonusSkills()) command = command + " AND ASB.MASTERY_ID = ? AND ASBL.BONUS_VALUE > 0"; 
/* 155 */     if (usesAllSkills()) command = command + " AND S.PLUS_ALLSKILLS > 0"; 
/* 156 */     if (usesSkillModifier()) command = command + " AND ASM.SKILL_ID = ?"; 
/* 157 */     if (usesMasteryModifySkills()) command = command + " AND ASM.MASTERY_ID = ?"; 
/* 158 */     if (usesAllItemSkills()) command = command + " AND A.ITEM_SKILL_ID IS NOT NULL"; 
/* 159 */     if (usesItemSkill()) command = command + " AND A.ITEM_SKILL_ID = ?"; 
/* 160 */     if (getMinLevel() != -1) command = command + " AND S.REQ_LEVEL >= ?"; 
/* 161 */     if (getMaxLevel() != -1) command = command + " AND S.REQ_LEVEL <= ?"; 
/* 162 */     if (getMaxCunning() != -1) command = command + " AND S.REQ_DEX <= ?"; 
/* 163 */     if (getMaxPhysique() != -1) command = command + " AND S.REQ_STR <= ?"; 
/* 164 */     if (getMaxSpirit() != -1) command = command + " AND S.REQ_INT <= ?"; 
/* 165 */     if (usesPetBonus()) command = command + " AND A.PET_AFFIX_ID IS NOT NULL"; 
/* 166 */     if (usesConversionFrom()) {
/* 167 */       if (usesConversionTo()) {
/* 168 */         command = command + " AND (( A.CONVERT_IN = ? AND A.CONVERT_OUT = ? ) OR ( A.CONVERT_IN_2 = ? AND A.CONVERT_OUT_2 = ? ))";
/*     */       } else {
/* 170 */         command = command + " AND (( A.CONVERT_IN = ? ) OR ( A.CONVERT_IN_2 = ? ))";
/*     */       }
/*     */     
/* 173 */     } else if (usesConversionTo()) {
/* 174 */       command = command + " AND (( A.CONVERT_OUT = ? ) OR ( A.CONVERT_OUT_2 = ? ))";
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 179 */     if (isNoEnemyOnly()) command = command + " AND S.ENEMY_ONLY = ?";
/*     */     
/* 181 */     command = command + determineSlotConditions("S.");
/*     */     
/* 183 */     return command;
/*     */   }
/*     */ 
/*     */   
/*     */   public int fillLevel1Statement(PreparedStatement ps) throws SQLException {
/* 188 */     ps.clearParameters();
/*     */     
/* 190 */     int nextPos = 1;
/*     */     
/* 192 */     ps.setString(nextPos, getItemClass());
/*     */     
/* 194 */     nextPos++;
/*     */     
/* 196 */     if (usesItemRarity()) {
/* 197 */       ps.setString(nextPos, getItemRarity());
/*     */       
/* 199 */       nextPos++;
/*     */     } 
/*     */     
/* 202 */     if (usesArmorClass()) {
/* 203 */       ps.setString(nextPos, getArmorClass());
/*     */       
/* 205 */       nextPos++;
/*     */     } 
/*     */     
/* 208 */     if (usesArtifactClass()) {
/* 209 */       ps.setString(nextPos, getArtifactClass());
/*     */       
/* 211 */       nextPos++;
/*     */     } 
/*     */     
/* 214 */     if (usesItemName()) {
/* 215 */       ps.setString(nextPos, getItemName().toUpperCase(GDMsgFormatter.locale));
/*     */       
/* 217 */       nextPos++;
/*     */     } 
/*     */     
/* 220 */     if (usesBonusSkill()) {
/* 221 */       ps.setString(nextPos, getBonusSkillID());
/*     */       
/* 223 */       nextPos++;
/*     */     } 
/*     */     
/* 226 */     if (usesMasteryBonusSkills()) {
/* 227 */       ps.setString(nextPos, getBonusSkillID());
/*     */       
/* 229 */       nextPos++;
/*     */     } 
/*     */     
/* 232 */     if (usesSkillModifier()) {
/* 233 */       ps.setString(nextPos, getModifiedSkillID());
/*     */       
/* 235 */       nextPos++;
/*     */     } 
/*     */     
/* 238 */     if (usesMasteryModifySkills()) {
/* 239 */       ps.setString(nextPos, getModifiedSkillID());
/*     */       
/* 241 */       nextPos++;
/*     */     } 
/*     */     
/* 244 */     if (usesItemSkill()) {
/* 245 */       ps.setString(nextPos, getItemSkillID());
/*     */       
/* 247 */       nextPos++;
/*     */     } 
/*     */     
/* 250 */     if (getMinLevel() != -1) {
/* 251 */       ps.setInt(nextPos, getMinLevel());
/*     */       
/* 253 */       nextPos++;
/*     */     } 
/*     */     
/* 256 */     if (getMaxLevel() != -1) {
/* 257 */       ps.setInt(nextPos, getMaxLevel());
/*     */       
/* 259 */       nextPos++;
/*     */     } 
/*     */     
/* 262 */     if (getMaxCunning() != -1) {
/* 263 */       ps.setInt(nextPos, getMaxCunning());
/*     */       
/* 265 */       nextPos++;
/*     */     } 
/*     */     
/* 268 */     if (getMaxPhysique() != -1) {
/* 269 */       ps.setInt(nextPos, getMaxPhysique());
/*     */       
/* 271 */       nextPos++;
/*     */     } 
/*     */     
/* 274 */     if (getMaxSpirit() != -1) {
/* 275 */       ps.setInt(nextPos, getMaxSpirit());
/*     */       
/* 277 */       nextPos++;
/*     */     } 
/*     */     
/* 280 */     if (usesConversionFrom()) {
/* 281 */       if (usesConversionTo()) {
/* 282 */         ps.setString(nextPos, getDamageConvertedFrom());
/* 283 */         nextPos++;
/* 284 */         ps.setString(nextPos, getDamageConvertedTo());
/* 285 */         nextPos++;
/*     */         
/* 287 */         ps.setString(nextPos, getDamageConvertedFrom());
/* 288 */         nextPos++;
/* 289 */         ps.setString(nextPos, getDamageConvertedTo());
/* 290 */         nextPos++;
/*     */       } else {
/* 292 */         ps.setString(nextPos, getDamageConvertedFrom());
/* 293 */         nextPos++;
/* 294 */         ps.setString(nextPos, getDamageConvertedFrom());
/* 295 */         nextPos++;
/*     */       }
/*     */     
/* 298 */     } else if (usesConversionTo()) {
/* 299 */       ps.setString(nextPos, getDamageConvertedTo());
/* 300 */       nextPos++;
/* 301 */       ps.setString(nextPos, getDamageConvertedTo());
/* 302 */       nextPos++;
/*     */     } 
/*     */ 
/*     */     
/* 306 */     if (isNoEnemyOnly()) {
/* 307 */       ps.setBoolean(nextPos, false);
/*     */       
/* 309 */       nextPos++;
/*     */     } 
/*     */     
/* 312 */     return nextPos;
/*     */   }
/*     */ 
/*     */   
/*     */   public String determineLevel2Statement(String level1command, SelectionCriteria.StatInfo info) {
/* 317 */     String level2command = level1command;
/*     */     
/* 319 */     if (info != null) {
/* 320 */       level2command = level2command + " AND (AST.STAT_TYPE = ?";
/* 321 */       if (info.flat == info.percentage) {
/* 322 */         if (info.maxResist) {
/* 323 */           if (info.flat) {
/* 324 */             level2command = level2command + " AND ((AST.STAT_MIN > 0) OR (AST.MODIFIER > 0) OR (AST.MAX_RESIST > 0))";
/*     */           } else {
/* 326 */             level2command = level2command + " AND (AST.MAX_RESIST > 0)";
/*     */           } 
/*     */         } else {
/* 329 */           level2command = level2command + " AND ((AST.STAT_MIN > 0) OR (AST.MODIFIER > 0))";
/*     */         } 
/*     */       } else {
/* 332 */         if (info.flat) {
/* 333 */           level2command = level2command + " AND ((AST.STAT_MIN > 0)";
/*     */         }
/*     */         
/* 336 */         if (info.percentage) {
/* 337 */           level2command = level2command + " AND ((AST.MODIFIER > 0)";
/*     */         }
/*     */         
/* 340 */         if (info.maxResist) {
/* 341 */           level2command = level2command + " OR (AST.MAX_RESIST > 0))";
/*     */         } else {
/* 343 */           level2command = level2command + ")";
/*     */         } 
/*     */       } 
/*     */       
/* 347 */       level2command = level2command + ")";
/*     */     } 
/*     */     
/* 350 */     return level2command;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Integer> getStashIDs(SelectionCriteria criteria, CriteriaCombination.Soulbound soulbound, CriteriaCombination.SC_HC schc, boolean isHardcore, String charName) {
/* 358 */     StashIDPrefixCombination combo = new StashIDPrefixCombination(criteria);
/*     */     
/* 360 */     return StashIDItemCombination.getStashIDs(combo, soulbound, schc, isHardcore, charName);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\criteria\StashIDPrefixCombination.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */