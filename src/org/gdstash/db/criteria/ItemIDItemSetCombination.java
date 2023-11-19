/*     */ package org.gdstash.db.criteria;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.db.GDDBData;
/*     */ import org.gdstash.db.SelectionCriteria;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ import org.gdstash.util.GDMsgLogger;
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
/*     */ public class ItemIDItemSetCombination
/*     */   extends AbstractItemCombination
/*     */ {
/*     */   public ItemIDItemSetCombination() {}
/*     */   
/*     */   public ItemIDItemSetCombination(ItemIDItemSetCombination combo) {
/*  32 */     super(combo);
/*     */   }
/*     */   
/*     */   public ItemIDItemSetCombination(SelectionCriteria criteria) {
/*  36 */     super(criteria);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemIDItemSetCombination clone() {
/*  41 */     ItemIDItemSetCombination combo = new ItemIDItemSetCombination(this);
/*     */     
/*  43 */     return combo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String determineLevel1Statement(CriteriaCombination.Soulbound soulbound, CriteriaCombination.SC_HC schc, boolean isHardcore, String charName) {
/*  52 */     String command = "SELECT I.ITEM_ID FROM GD_ITEM I";
/*     */     
/*  54 */     boolean statFilled = !(getCriteria()).statInfos.isEmpty();
/*     */     
/*  56 */     if (getCriteria().isSkillBonusUsed()) {
/*  57 */       command = command + ", " + "GD_ITEMSET_SKILLS" + " ISB, " + "GD_ITEMSET_SKILL_LEVEL" + " ISBL";
/*     */     }
/*     */     
/*  60 */     if (getCriteria().isSkillModifierUsed()) {
/*  61 */       command = command + ", " + "GD_ITEMSET_SKILLMODIFIER" + " ISM";
/*     */     }
/*  63 */     if (statFilled) {
/*  64 */       command = command + ", " + "GD_ITEMSET_STAT" + " IST";
/*     */     }
/*     */     
/*  67 */     command = command + " WHERE ";
/*     */     
/*  69 */     if (getCriteria().isSkillBonusUsed()) {
/*  70 */       command = command + "I.SET_ID = ISB.ITEMSET_ID AND I.SET_ID = ISBL.ITEMSET_ID AND ISB.TYPE = ISBL.TYPE AND ISB.INDEX = ISBL.INDEX AND ";
/*     */     }
/*     */     
/*  73 */     if (getCriteria().isSkillModifierUsed()) {
/*  74 */       command = command + "I.SET_ID = ISM.ITEMSET_ID AND ";
/*     */     }
/*  76 */     if (statFilled) {
/*  77 */       command = command + "I.SET_ID = IST.ITEMSET_ID AND ";
/*     */     }
/*     */     
/*  80 */     command = command + "I.ITEM_CLASS = ?";
/*     */     
/*  82 */     if (usesItemRarity()) command = command + " AND I.RARITY = ?"; 
/*  83 */     if (usesArmorClass()) command = command + " AND I.ARMOR_CLASS = ?"; 
/*  84 */     if (usesArtifactClass()) command = command + " AND I.ARTIFACT_CLASS = ?"; 
/*  85 */     if (usesItemName()) command = command + " AND UPPER(I.NAME_FULL) LIKE ?"; 
/*  86 */     if (usesSetItem()) command = command + " AND I.SET_ID IS NOT NULL"; 
/*  87 */     if (usesBonusSkill()) command = command + " AND ISB.BONUS_ENTITY = ? AND ISBL.BONUS_VALUE > 0"; 
/*  88 */     if (usesMasteryBonusSkills()) command = command + " AND ISB.MASTERY_ID = ? AND ISBL.BONUS_VALUE > 0"; 
/*  89 */     if (usesAllSkills()) command = command + " AND I.PLUS_ALLSKILLS > 0"; 
/*  90 */     if (usesSkillModifier()) command = command + " AND ISM.SKILL_ID = ?"; 
/*  91 */     if (usesMasteryModifySkills()) command = command + " AND ISM.MASTERY_ID = ?"; 
/*  92 */     if (usesAllItemSkills()) command = command + " AND I.ITEM_SKILL_ID IS NOT NULL"; 
/*  93 */     if (usesItemSkill()) command = command + " AND I.ITEM_SKILL_ID = ?"; 
/*  94 */     if (getMinLevel() != -1) command = command + " AND I.REQ_LEVEL >= ?"; 
/*  95 */     if (getMaxLevel() != -1) command = command + " AND I.REQ_LEVEL <= ?"; 
/*  96 */     if (getMaxCunning() != -1) command = command + " AND I.REQ_DEX <= ?"; 
/*  97 */     if (getMaxPhysique() != -1) command = command + " AND I.REQ_STR <= ?"; 
/*  98 */     if (getMaxSpirit() != -1) command = command + " AND I.REQ_INT <= ?"; 
/*  99 */     if (usesPetBonus()) command = command + " AND I.PET_BONUS_SKILL_ID IS NOT NULL"; 
/* 100 */     if (usesConversionFrom()) {
/* 101 */       if (usesConversionTo()) {
/* 102 */         command = command + " AND (( I.CONVERT_IN = ? AND I.CONVERT_OUT = ? ) OR ( I.CONVERT_IN_2 = ? AND I.CONVERT_OUT_2 = ? ))";
/*     */       } else {
/* 104 */         command = command + " AND (( I.CONVERT_IN = ? ) OR ( I.CONVERT_IN_2 = ? ))";
/*     */       }
/*     */     
/* 107 */     } else if (usesConversionTo()) {
/* 108 */       command = command + " AND (( I.CONVERT_OUT = ? ) OR ( I.CONVERT_OUT_2 = ? ))";
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 113 */     if (isNoEnemyOnly()) command = command + " AND I.ENEMY_ONLY = ?";
/*     */     
/* 115 */     command = command + determineSlotConditions("I.");
/*     */     
/* 117 */     return command;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSingleStringCombo(List<String> listAll, PreparedStatement ps, String command, String statType) throws SQLException, UnsupportedOperationException {
/* 122 */     int nextPos = fillLevel1Statement(ps);
/*     */     
/* 124 */     if (statType != null) {
/* 125 */       nextPos = fillLevel2Statement(ps, statType, nextPos);
/*     */     }
/*     */     
/* 128 */     try (ResultSet rs = ps.executeQuery()) {
/* 129 */       List<String> ids = AbstractItemCombination.wrapString(rs, 1);
/*     */       
/* 131 */       if (ids != null) AbstractItemCombination.mergeStringList(listAll, ids);
/*     */     
/* 133 */     } catch (SQLException ex) {
/* 134 */       Object[] args = { command, determineLevel1Parameters() };
/* 135 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_SELECT_FAILED", args);
/*     */       
/* 137 */       GDMsgLogger.addError(msg);
/*     */       
/* 139 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<String> getItemIDs(SelectionCriteria criteria, CriteriaCombination.Soulbound soulbound, CriteriaCombination.SC_HC schc, boolean isHardcore, String charName) {
/* 148 */     ItemIDItemSetCombination combo = new ItemIDItemSetCombination(criteria);
/*     */     
/* 150 */     return getItemIDs(combo, soulbound, schc, isHardcore, charName);
/*     */   }
/*     */   
/*     */   public static List<String> getItemIDs(ItemIDItemSetCombination combo, CriteriaCombination.Soulbound soulbound, CriteriaCombination.SC_HC schc, boolean isHardcore, String charName) {
/* 154 */     List<String> listAll = new LinkedList<>();
/*     */     
/* 156 */     try (Connection conn = GDDBData.getConnection()) {
/* 157 */       List<CriteriaCombination> combos = combo.createLevel1Combinations(combo);
/*     */       
/* 159 */       for (CriteriaCombination cc : combos) {
/* 160 */         String command1 = cc.determineLevel1Statement(soulbound, schc, isHardcore, charName);
/*     */         
/* 162 */         if ((cc.getCriteria()).statInfos.isEmpty()) {
/* 163 */           try (PreparedStatement ps = conn.prepareStatement(command1)) {
/*     */             try {
/* 165 */               cc.addSingleStringCombo(listAll, ps, command1, null);
/*     */             }
/* 167 */             catch (SQLException ex) {
/* 168 */               throw ex;
/*     */             }
/*     */           
/* 171 */           } catch (SQLException ex) {
/* 172 */             Object[] args = { command1 };
/* 173 */             String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_PREPARE_FAILED", args);
/*     */             
/* 175 */             GDMsgLogger.addError(msg);
/*     */           } 
/*     */           
/* 178 */           conn.commit(); continue;
/*     */         } 
/* 180 */         for (SelectionCriteria.StatInfo info : (cc.getCriteria()).statInfos) {
/* 181 */           String command2 = cc.determineLevel2Statement(command1, info);
/*     */           
/* 183 */           for (String statType : info.statTypes) {
/* 184 */             try (PreparedStatement ps = conn.prepareStatement(command2)) {
/*     */               try {
/* 186 */                 cc.addSingleStringCombo(listAll, ps, command2, statType);
/*     */               }
/* 188 */               catch (SQLException ex) {
/* 189 */                 throw ex;
/*     */               }
/*     */             
/* 192 */             } catch (SQLException ex) {
/* 193 */               Object[] args = { command2 };
/* 194 */               String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_PREPARE_FAILED", args);
/*     */               
/* 196 */               GDMsgLogger.addError(msg);
/*     */             } 
/*     */             
/* 199 */             conn.commit();
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       } 
/* 205 */     } catch (SQLException ex) {
/* 206 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_DB_CONN_FAILED"));
/*     */     } 
/*     */     
/* 209 */     return listAll;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\criteria\ItemIDItemSetCombination.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */