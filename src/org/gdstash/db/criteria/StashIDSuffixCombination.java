/*     */ package org.gdstash.db.criteria;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.gdstash.db.SelectionCriteria;
/*     */ import org.gdstash.ui.GDStashFrame;
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
/*     */ public class StashIDSuffixCombination
/*     */   extends StashIDPrefixCombination
/*     */ {
/*     */   public StashIDSuffixCombination() {}
/*     */   
/*     */   public StashIDSuffixCombination(StashIDSuffixCombination combo) {
/*  27 */     super(combo);
/*     */   }
/*     */   
/*     */   public StashIDSuffixCombination(SelectionCriteria criteria) {
/*  31 */     super(criteria);
/*     */   }
/*     */ 
/*     */   
/*     */   public StashIDSuffixCombination clone() {
/*  36 */     StashIDSuffixCombination combo = new StashIDSuffixCombination(this);
/*     */     
/*  38 */     return combo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String determineLevel1Statement(CriteriaCombination.Soulbound soulbound, CriteriaCombination.SC_HC schc, boolean isHardcore, String charName) {
/*  47 */     String command = "SELECT S.STASH_ID FROM STASH_ITEM_V8 S, GD_AFFIX A";
/*     */     
/*  49 */     boolean statFilled = !(getCriteria()).statInfos.isEmpty();
/*     */     
/*  51 */     if (getCriteria().isSkillBonusUsed()) {
/*  52 */       command = command + ", " + "GD_AFFIX_SKILLS" + " ASB, " + "GD_AFFIX_SKILL_LEVEL" + " ASBL";
/*     */     }
/*     */     
/*  55 */     if (getCriteria().isSkillModifierUsed()) {
/*  56 */       command = command + ", " + "GD_AFFIX_SKILLMODIFIER" + " ASM";
/*     */     }
/*  58 */     if (statFilled) {
/*  59 */       command = command + ", " + "GD_AFFIX_STAT" + " AST";
/*     */     }
/*     */     
/*  62 */     command = command + " WHERE ";
/*     */     
/*  64 */     if (getCriteria().isSkillBonusUsed()) {
/*  65 */       command = command + "A.AFFIX_ID = ASB.AFFIX_ID AND A.AFFIX_ID = ASBL.AFFIX_ID AND ASB.TYPE = ASBL.TYPE AND ASB.INDEX = ASBL.INDEX AND ";
/*     */     }
/*     */     
/*  68 */     if (getCriteria().isSkillModifierUsed()) {
/*  69 */       command = command + "A.AFFIX_ID = ASM.AFFIX_ID AND ";
/*     */     }
/*  71 */     if (statFilled) {
/*  72 */       command = command + "A.AFFIX_ID = AST.AFFIX_ID AND ";
/*     */     }
/*     */     
/*  75 */     command = command + "S.SUFFIX_ID = A.AFFIX_ID AND A.TYPE = 2 AND S.ITEM_CLASS = ?";
/*     */ 
/*     */     
/*  78 */     if (schc == CriteriaCombination.SC_HC.SOFTCORE) {
/*  79 */       command = command + " AND S.HARDCORE = false";
/*     */     }
/*  81 */     if (schc == CriteriaCombination.SC_HC.HARDCORE) {
/*  82 */       command = command + " AND S.HARDCORE = true";
/*     */     }
/*  84 */     if (schc == CriteriaCombination.SC_HC.SETTING && 
/*  85 */       !GDStashFrame.iniConfig.sectRestrict.transferSCHC) {
/*  86 */       if (isHardcore) {
/*  87 */         command = command + " AND S.HARDCORE = true";
/*     */       } else {
/*  89 */         command = command + " AND S.HARDCORE = false";
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  95 */     if (soulbound == CriteriaCombination.Soulbound.EXCLUDED) {
/*  96 */       command = command + " AND CHARNAME = ''";
/*     */     }
/*     */     
/*  99 */     if (soulbound == CriteriaCombination.Soulbound.INCLUDED) {
/* 100 */       if ((getCriteria()).soulboundMode == SelectionCriteria.SoulboundMode.NONBOUND) {
/* 101 */         command = command + " AND CHARNAME = ''";
/*     */       }
/*     */       
/* 104 */       if ((getCriteria()).soulboundMode == SelectionCriteria.SoulboundMode.SOULBOUND) {
/* 105 */         command = command + " AND CHARNAME <> ''";
/*     */       }
/*     */     } 
/*     */     
/* 109 */     if (soulbound == CriteriaCombination.Soulbound.SETTING) {
/* 110 */       if ((getCriteria()).soulboundMode == SelectionCriteria.SoulboundMode.ALL && 
/* 111 */         !GDStashFrame.iniConfig.sectRestrict.transferSoulbound) {
/* 112 */         if (charName == null) {
/* 113 */           command = command + " AND CHARNAME = ''";
/*     */         }
/* 115 */         else if (charName.isEmpty()) {
/* 116 */           command = command + " AND CHARNAME = ''";
/*     */         } else {
/* 118 */           command = command + " AND ( CHARNAME = '" + charName + "' OR CHARNAME = '' )";
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 124 */       if ((getCriteria()).soulboundMode == SelectionCriteria.SoulboundMode.NONBOUND) {
/* 125 */         command = command + " AND CHARNAME = ''";
/*     */       }
/*     */       
/* 128 */       if ((getCriteria()).soulboundMode == SelectionCriteria.SoulboundMode.SOULBOUND) {
/* 129 */         if (GDStashFrame.iniConfig.sectRestrict.transferSoulbound) {
/* 130 */           command = command + " AND CHARNAME <> ''";
/*     */         }
/* 132 */         else if (charName == null) {
/* 133 */           command = command + " AND CHARNAME = '___xxx___'";
/*     */         }
/* 135 */         else if (charName.isEmpty()) {
/* 136 */           command = command + " AND CHARNAME = '___xxx___'";
/*     */         } else {
/* 138 */           command = command + " AND CHARNAME = '" + charName + "'";
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 145 */     if (usesItemRarity()) command = command + " AND S.RARITY = ?"; 
/* 146 */     if (usesArmorClass()) command = command + " AND S.ARMOR_CLASS = ?"; 
/* 147 */     if (usesArtifactClass()) command = command + " AND S.ARTIFACT_CLASS = ?"; 
/* 148 */     if (usesItemName()) command = command + " AND S.NAME LIKE ?"; 
/* 149 */     if (usesSetItem()) command = command + " AND S.SET_ID IS NOT NULL"; 
/* 150 */     if (usesBonusSkill()) command = command + " AND ASB.BONUS_ENTITY = ? AND ASBL.BONUS_VALUE > 0"; 
/* 151 */     if (usesMasteryBonusSkills()) command = command + " AND ASB.MASTERY_ID = ? AND ASBL.BONUS_VALUE > 0"; 
/* 152 */     if (usesAllSkills()) command = command + " AND S.PLUS_ALLSKILLS > 0"; 
/* 153 */     if (usesSkillModifier()) command = command + " AND ASM.SKILL_ID = ?"; 
/* 154 */     if (usesMasteryModifySkills()) command = command + " AND ASM.MASTERY_ID = ?"; 
/* 155 */     if (usesAllItemSkills()) command = command + " AND A.ITEM_SKILL_ID IS NOT NULL"; 
/* 156 */     if (usesItemSkill()) command = command + " AND A.ITEM_SKILL_ID = ?"; 
/* 157 */     if (getMinLevel() != -1) command = command + " AND S.REQ_LEVEL >= ?"; 
/* 158 */     if (getMaxLevel() != -1) command = command + " AND S.REQ_LEVEL <= ?"; 
/* 159 */     if (getMaxCunning() != -1) command = command + " AND S.REQ_DEX <= ?"; 
/* 160 */     if (getMaxPhysique() != -1) command = command + " AND S.REQ_STR <= ?"; 
/* 161 */     if (getMaxSpirit() != -1) command = command + " AND S.REQ_INT <= ?"; 
/* 162 */     if (usesPetBonus()) command = command + " AND A.PET_AFFIX_ID IS NOT NULL"; 
/* 163 */     if (usesConversionFrom()) {
/* 164 */       if (usesConversionTo()) {
/* 165 */         command = command + " AND (( A.CONVERT_IN = ? AND A.CONVERT_OUT = ? ) OR ( A.CONVERT_IN_2 = ? AND A.CONVERT_OUT_2 = ? ))";
/*     */       } else {
/* 167 */         command = command + " AND (( A.CONVERT_IN = ? ) OR ( A.CONVERT_IN_2 = ? ))";
/*     */       }
/*     */     
/* 170 */     } else if (usesConversionTo()) {
/* 171 */       command = command + " AND (( A.CONVERT_OUT = ? ) OR ( A.CONVERT_OUT_2 = ? ))";
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 176 */     if (isNoEnemyOnly()) command = command + " AND S.ENEMY_ONLY = ?";
/*     */     
/* 178 */     command = command + determineSlotConditions("S.");
/*     */     
/* 180 */     return command;
/*     */   }
/*     */   
/*     */   public static List<Integer> getStashIDs(SelectionCriteria criteria, CriteriaCombination.Soulbound soulbound, CriteriaCombination.SC_HC schc, boolean isHardcore, String charName) {
/* 184 */     StashIDSuffixCombination combo = new StashIDSuffixCombination(criteria);
/*     */     
/* 186 */     return StashIDItemCombination.getStashIDs(combo, soulbound, schc, isHardcore, charName);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\criteria\StashIDSuffixCombination.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */