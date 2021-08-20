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
/*     */ import org.gdstash.ui.GDStashFrame;
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
/*     */ public class StashIDItemCombination
/*     */   extends AbstractItemCombination
/*     */ {
/*     */   public StashIDItemCombination() {}
/*     */   
/*     */   public StashIDItemCombination(StashIDItemCombination combo) {
/*  33 */     super(combo);
/*     */   }
/*     */   
/*     */   public StashIDItemCombination(SelectionCriteria criteria) {
/*  37 */     super(criteria);
/*     */   }
/*     */ 
/*     */   
/*     */   public StashIDItemCombination clone() {
/*  42 */     StashIDItemCombination combo = new StashIDItemCombination(this);
/*     */     
/*  44 */     return combo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String determineLevel1Statement(CriteriaCombination.Soulbound soulbound, CriteriaCombination.SC_HC schc, boolean isHardcore, String charName) {
/*  53 */     String command = "SELECT S.STASH_ID FROM STASH_ITEM_V8 S";
/*     */     
/*  55 */     boolean statFilled = !(getCriteria()).statInfos.isEmpty();
/*     */     
/*  57 */     if (getCriteria().isSkillBonusUsed()) {
/*  58 */       command = command + ", " + "GD_ITEM_SKILLS" + " ISB, " + "GD_ITEM_SKILL_LEVEL" + " ISBL";
/*     */     }
/*     */     
/*  61 */     if (getCriteria().isSkillModifierUsed()) {
/*  62 */       command = command + ", " + "GD_ITEM_SKILLMODIFIER" + " ISM";
/*     */     }
/*  64 */     if (statFilled) {
/*  65 */       command = command + ", " + "GD_ITEM_STAT" + " IST";
/*     */     }
/*     */     
/*  68 */     command = command + " WHERE ";
/*     */     
/*  70 */     if (getCriteria().isSkillBonusUsed()) {
/*  71 */       command = command + "S.ITEM_ID = ISB.ITEM_ID AND S.ITEM_ID = ISBL.ITEM_ID AND ISB.TYPE = ISBL.TYPE AND ISB.INDEX = ISBL.INDEX AND ";
/*     */     }
/*     */     
/*  74 */     if (getCriteria().isSkillModifierUsed()) {
/*  75 */       command = command + "S.ITEM_ID = ISM.ITEM_ID AND ";
/*     */     }
/*  77 */     if (statFilled) {
/*  78 */       command = command + "S.ITEM_ID = IST.ITEM_ID AND ";
/*     */     }
/*     */     
/*  81 */     command = command + "S.ITEM_CLASS = ?";
/*     */ 
/*     */     
/*  84 */     if (schc == CriteriaCombination.SC_HC.SOFTCORE) {
/*  85 */       command = command + " AND S.HARDCORE = false";
/*     */     }
/*  87 */     if (schc == CriteriaCombination.SC_HC.HARDCORE) {
/*  88 */       command = command + " AND S.HARDCORE = true";
/*     */     }
/*  90 */     if (schc == CriteriaCombination.SC_HC.SETTING && 
/*  91 */       !GDStashFrame.iniConfig.sectRestrict.transferSCHC) {
/*  92 */       if (isHardcore) {
/*  93 */         command = command + " AND S.HARDCORE = true";
/*     */       } else {
/*  95 */         command = command + " AND S.HARDCORE = false";
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 101 */     if (soulbound == CriteriaCombination.Soulbound.EXCLUDED) {
/* 102 */       command = command + " AND CHARNAME = ''";
/*     */     }
/*     */     
/* 105 */     if (soulbound == CriteriaCombination.Soulbound.INCLUDED) {
/* 106 */       if ((getCriteria()).soulboundMode == SelectionCriteria.SoulboundMode.NONBOUND) {
/* 107 */         command = command + " AND CHARNAME = ''";
/*     */       }
/*     */       
/* 110 */       if ((getCriteria()).soulboundMode == SelectionCriteria.SoulboundMode.SOULBOUND) {
/* 111 */         command = command + " AND CHARNAME <> ''";
/*     */       }
/*     */     } 
/*     */     
/* 115 */     if (soulbound == CriteriaCombination.Soulbound.SETTING) {
/* 116 */       if ((getCriteria()).soulboundMode == SelectionCriteria.SoulboundMode.ALL && 
/* 117 */         !GDStashFrame.iniConfig.sectRestrict.transferSoulbound) {
/* 118 */         if (charName == null) {
/* 119 */           command = command + " AND CHARNAME = ''";
/*     */         }
/* 121 */         else if (charName.isEmpty()) {
/* 122 */           command = command + " AND CHARNAME = ''";
/*     */         } else {
/* 124 */           command = command + " AND ( CHARNAME = '" + charName + "' OR CHARNAME = '' )";
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 130 */       if ((getCriteria()).soulboundMode == SelectionCriteria.SoulboundMode.NONBOUND) {
/* 131 */         command = command + " AND CHARNAME = ''";
/*     */       }
/*     */       
/* 134 */       if ((getCriteria()).soulboundMode == SelectionCriteria.SoulboundMode.SOULBOUND) {
/* 135 */         if (GDStashFrame.iniConfig.sectRestrict.transferSoulbound) {
/* 136 */           command = command + " AND CHARNAME <> ''";
/*     */         }
/* 138 */         else if (charName == null) {
/* 139 */           command = command + " AND CHARNAME = '___xxx___'";
/*     */         }
/* 141 */         else if (charName.isEmpty()) {
/* 142 */           command = command + " AND CHARNAME = '___xxx___'";
/*     */         } else {
/* 144 */           command = command + " AND CHARNAME = '" + charName + "'";
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 151 */     if (usesItemRarity()) command = command + " AND S.RARITY = ?"; 
/* 152 */     if (usesArmorClass()) command = command + " AND S.ARMOR_CLASS = ?"; 
/* 153 */     if (usesArtifactClass()) command = command + " AND S.ARTIFACT_CLASS = ?"; 
/* 154 */     if (usesItemName()) command = command + " AND S.NAME LIKE ?"; 
/* 155 */     if (usesSetItem()) command = command + " AND S.SET_ID IS NOT NULL"; 
/* 156 */     if (usesBonusSkill()) command = command + " AND ISB.BONUS_ENTITY = ? AND ISBL.BONUS_VALUE > 0"; 
/* 157 */     if (usesMasteryBonusSkills()) command = command + " AND ISB.MASTERY_ID = ? AND ISBL.BONUS_VALUE > 0"; 
/* 158 */     if (usesAllSkills()) command = command + " AND S.PLUS_ALLSKILLS > 0"; 
/* 159 */     if (usesSkillModifier()) command = command + " AND ISM.SKILL_ID = ?"; 
/* 160 */     if (usesMasteryModifySkills()) command = command + " AND ISM.MASTERY_ID = ?"; 
/* 161 */     if (usesAllItemSkills()) command = command + " AND S.ITEM_SKILL_ID IS NOT NULL"; 
/* 162 */     if (usesItemSkill()) command = command + " AND S.ITEM_SKILL_ID = ?"; 
/* 163 */     if (usesPrefix()) command = command + " AND S.PREFIX_ID = ?"; 
/* 164 */     if (usesSuffix()) command = command + " AND S.SUFFIX_ID = ?"; 
/* 165 */     if (getMinLevel() != -1) command = command + " AND S.REQ_LEVEL >= ?"; 
/* 166 */     if (getMaxLevel() != -1) command = command + " AND S.REQ_LEVEL <= ?"; 
/* 167 */     if (getMaxCunning() != -1) command = command + " AND S.REQ_DEX <= ?"; 
/* 168 */     if (getMaxPhysique() != -1) command = command + " AND S.REQ_STR <= ?"; 
/* 169 */     if (getMaxSpirit() != -1) command = command + " AND S.REQ_INT <= ?"; 
/* 170 */     if (usesPetBonus()) command = command + " AND S.PET_BONUS_SKILL_ID IS NOT NULL"; 
/* 171 */     if (usesConversionFrom()) {
/* 172 */       if (usesConversionTo()) {
/* 173 */         command = command + " AND (( S.CONVERT_IN = ? AND S.CONVERT_OUT = ? ) OR ( S.CONVERT_IN_2 = ? AND S.CONVERT_OUT_2 = ? ))";
/*     */       } else {
/* 175 */         command = command + " AND (( S.CONVERT_IN = ? ) OR ( S.CONVERT_IN_2 = ? ))";
/*     */       }
/*     */     
/* 178 */     } else if (usesConversionTo()) {
/* 179 */       command = command + " AND (( S.CONVERT_OUT = ? ) OR ( S.CONVERT_OUT_2 = ? ))";
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 184 */     if (isNoEnemyOnly()) command = command + " AND S.ENEMY_ONLY = ?";
/*     */     
/* 186 */     command = command + determineSlotConditions("S.");
/*     */     
/* 188 */     return command;
/*     */   }
/*     */ 
/*     */   
/*     */   public int fillLevel1Statement(PreparedStatement ps) throws SQLException {
/* 193 */     ps.clearParameters();
/*     */     
/* 195 */     int nextPos = 1;
/*     */     
/* 197 */     ps.setString(nextPos, getItemClass());
/*     */     
/* 199 */     nextPos++;
/*     */     
/* 201 */     if (usesItemRarity()) {
/* 202 */       ps.setString(nextPos, getItemRarity());
/*     */       
/* 204 */       nextPos++;
/*     */     } 
/*     */     
/* 207 */     if (usesArmorClass()) {
/* 208 */       ps.setString(nextPos, getArmorClass());
/*     */       
/* 210 */       nextPos++;
/*     */     } 
/*     */     
/* 213 */     if (usesArtifactClass()) {
/* 214 */       ps.setString(nextPos, getArtifactClass());
/*     */       
/* 216 */       nextPos++;
/*     */     } 
/*     */     
/* 219 */     if (usesItemName()) {
/* 220 */       ps.setString(nextPos, getItemName().toUpperCase(GDMsgFormatter.locale));
/*     */       
/* 222 */       nextPos++;
/*     */     } 
/*     */     
/* 225 */     if (usesBonusSkill()) {
/* 226 */       ps.setString(nextPos, getBonusSkillID());
/*     */       
/* 228 */       nextPos++;
/*     */     } 
/*     */     
/* 231 */     if (usesMasteryBonusSkills()) {
/* 232 */       ps.setString(nextPos, getBonusSkillID());
/*     */       
/* 234 */       nextPos++;
/*     */     } 
/*     */     
/* 237 */     if (usesSkillModifier()) {
/* 238 */       ps.setString(nextPos, getModifiedSkillID());
/*     */       
/* 240 */       nextPos++;
/*     */     } 
/*     */     
/* 243 */     if (usesMasteryModifySkills()) {
/* 244 */       ps.setString(nextPos, getModifiedSkillID());
/*     */       
/* 246 */       nextPos++;
/*     */     } 
/*     */     
/* 249 */     if (usesItemSkill()) {
/* 250 */       ps.setString(nextPos, getItemSkillID());
/*     */       
/* 252 */       nextPos++;
/*     */     } 
/*     */     
/* 255 */     if (usesPrefix()) {
/* 256 */       ps.setString(nextPos, getPrefixID());
/*     */       
/* 258 */       nextPos++;
/*     */     } 
/*     */     
/* 261 */     if (usesSuffix()) {
/* 262 */       ps.setString(nextPos, getSuffixID());
/*     */       
/* 264 */       nextPos++;
/*     */     } 
/*     */     
/* 267 */     if (getMinLevel() != -1) {
/* 268 */       ps.setInt(nextPos, getMinLevel());
/*     */       
/* 270 */       nextPos++;
/*     */     } 
/*     */     
/* 273 */     if (getMaxLevel() != -1) {
/* 274 */       ps.setInt(nextPos, getMaxLevel());
/*     */       
/* 276 */       nextPos++;
/*     */     } 
/*     */     
/* 279 */     if (getMaxCunning() != -1) {
/* 280 */       ps.setInt(nextPos, getMaxCunning());
/*     */       
/* 282 */       nextPos++;
/*     */     } 
/*     */     
/* 285 */     if (getMaxPhysique() != -1) {
/* 286 */       ps.setInt(nextPos, getMaxPhysique());
/*     */       
/* 288 */       nextPos++;
/*     */     } 
/*     */     
/* 291 */     if (getMaxSpirit() != -1) {
/* 292 */       ps.setInt(nextPos, getMaxSpirit());
/*     */       
/* 294 */       nextPos++;
/*     */     } 
/*     */     
/* 297 */     if (usesConversionFrom()) {
/* 298 */       if (usesConversionTo()) {
/* 299 */         ps.setString(nextPos, getDamageConvertedFrom());
/* 300 */         nextPos++;
/* 301 */         ps.setString(nextPos, getDamageConvertedTo());
/* 302 */         nextPos++;
/*     */         
/* 304 */         ps.setString(nextPos, getDamageConvertedFrom());
/* 305 */         nextPos++;
/* 306 */         ps.setString(nextPos, getDamageConvertedTo());
/* 307 */         nextPos++;
/*     */       } else {
/* 309 */         ps.setString(nextPos, getDamageConvertedFrom());
/* 310 */         nextPos++;
/* 311 */         ps.setString(nextPos, getDamageConvertedFrom());
/* 312 */         nextPos++;
/*     */       }
/*     */     
/* 315 */     } else if (usesConversionTo()) {
/* 316 */       ps.setString(nextPos, getDamageConvertedTo());
/* 317 */       nextPos++;
/* 318 */       ps.setString(nextPos, getDamageConvertedTo());
/* 319 */       nextPos++;
/*     */     } 
/*     */ 
/*     */     
/* 323 */     if (isNoEnemyOnly()) {
/* 324 */       ps.setBoolean(nextPos, false);
/*     */       
/* 326 */       nextPos++;
/*     */     } 
/*     */     
/* 329 */     return nextPos;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSingleIntCombo(List<Integer> listAll, PreparedStatement ps, String command, String statType) throws SQLException, UnsupportedOperationException {
/* 334 */     int nextPos = fillLevel1Statement(ps);
/*     */     
/* 336 */     if (statType != null) {
/* 337 */       nextPos = fillLevel2Statement(ps, statType, nextPos);
/*     */     }
/*     */     
/* 340 */     try (ResultSet rs = ps.executeQuery()) {
/* 341 */       List<Integer> ids = AbstractItemCombination.wrapInteger(rs, 1);
/*     */       
/* 343 */       if (ids != null) AbstractItemCombination.mergeIntList(listAll, ids);
/*     */     
/* 345 */     } catch (SQLException ex) {
/* 346 */       Object[] args = { command, determineLevel1Parameters() };
/* 347 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_SELECT_FAILED", args);
/*     */       
/* 349 */       GDMsgLogger.addError(msg);
/*     */       
/* 351 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Integer> getStashIDs(SelectionCriteria criteria, CriteriaCombination.Soulbound soulbound, CriteriaCombination.SC_HC schc, boolean isHardcore, String charName) {
/* 361 */     StashIDItemCombination combo = new StashIDItemCombination(criteria);
/*     */     
/* 363 */     return getStashIDs(combo, soulbound, schc, isHardcore, charName);
/*     */   }
/*     */   
/*     */   public static List<Integer> getStashIDs(StashIDItemCombination combo, CriteriaCombination.Soulbound soulbound, CriteriaCombination.SC_HC schc, boolean isHardcore, String charName) {
/* 367 */     List<Integer> listAll = new LinkedList<>();
/*     */     
/* 369 */     try (Connection conn = GDDBData.getConnection()) {
/* 370 */       List<CriteriaCombination> combos = combo.createLevel1Combinations(combo);
/*     */       
/* 372 */       for (CriteriaCombination cc : combos) {
/* 373 */         String command1 = cc.determineLevel1Statement(soulbound, schc, isHardcore, charName);
/*     */         
/* 375 */         if ((cc.getCriteria()).statInfos.isEmpty()) {
/* 376 */           try (PreparedStatement ps = conn.prepareStatement(command1)) {
/*     */             try {
/* 378 */               cc.addSingleIntCombo(listAll, ps, command1, null);
/*     */             }
/* 380 */             catch (SQLException ex) {
/* 381 */               throw ex;
/*     */             }
/*     */           
/* 384 */           } catch (SQLException ex) {
/* 385 */             Object[] args = { command1 };
/* 386 */             String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_PREPARE_FAILED", args);
/*     */             
/* 388 */             GDMsgLogger.addError(msg);
/*     */           } 
/*     */           
/* 391 */           conn.commit(); continue;
/*     */         } 
/* 393 */         for (SelectionCriteria.StatInfo info : (cc.getCriteria()).statInfos) {
/* 394 */           String command2 = cc.determineLevel2Statement(command1, info);
/*     */           
/* 396 */           for (String statType : info.statTypes) {
/* 397 */             try (PreparedStatement ps = conn.prepareStatement(command2)) {
/*     */               try {
/* 399 */                 cc.addSingleIntCombo(listAll, ps, command2, statType);
/*     */               }
/* 401 */               catch (SQLException ex) {
/* 402 */                 throw ex;
/*     */               }
/*     */             
/* 405 */             } catch (SQLException ex) {
/* 406 */               Object[] args = { command2 };
/* 407 */               String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_PREPARE_FAILED", args);
/*     */               
/* 409 */               GDMsgLogger.addError(msg);
/*     */             } 
/*     */             
/* 412 */             conn.commit();
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       } 
/* 418 */     } catch (SQLException ex) {
/* 419 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_DB_CONN_FAILED"));
/*     */     } 
/*     */     
/* 422 */     return listAll;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\criteria\StashIDItemCombination.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */