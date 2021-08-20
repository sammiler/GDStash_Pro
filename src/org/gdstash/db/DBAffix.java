/*      */ package org.gdstash.db;
/*      */ import java.sql.*;
/*      */
/*      */
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
/*      */ import org.gdstash.description.BonusDetail;
import org.gdstash.description.DetailComposer;
/*      */ import org.gdstash.file.ARCDecompress;
import org.gdstash.file.ARZDecompress;
import org.gdstash.file.ARZRecord;
import org.gdstash.ui.GDStashFrame;
import org.gdstash.util.GDColor;
import org.gdstash.util.GDConstants;
import org.gdstash.util.GDMsgFormatter;
import org.gdstash.util.GDMsgLogger;

/*      */
/*      */ public class DBAffix implements Comparable<DBAffix> {
/*      */   public static final String TABLE_NAME = "GD_AFFIX";
/*      */   public static final String FIELD_ID = "AFFIX_ID";
/*      */   private static final int ROW_AFFIX_ID = 1;
/*      */   private static final int ROW_RARITY = 2;
/*      */   private static final int ROW_NAME_MS = 3;
/*      */   private static final int ROW_NAME_FS = 4;
/*      */   private static final int ROW_NAME_NS = 5;
/*      */   private static final int ROW_NAME_MP = 6;
/*      */   private static final int ROW_NAME_FP = 7;
/*      */   private static final int ROW_NAME_NP = 8;
/*      */   private static final int ROW_REQ_LEVEL = 9;
/*      */   private static final int ROW_TYPE = 10;
/*      */   private static final int ROW_CONVERT_IN = 11;
/*      */   private static final int ROW_CONVERT_OUT = 12;
/*      */   private static final int ROW_CONVERT_IN_2 = 13;
/*      */   private static final int ROW_CONVERT_OUT_2 = 14;
/*      */   private static final int ROW_OFFENSIVE_CHANCE = 15;
/*      */   private static final int ROW_RETALIATION_CHANCE = 16;
/*      */   private static final int ROW_LOOT_RANDOM_COST = 17;
/*      */   private static final int ROW_PET_AFFIX_ID = 18;
/*      */   private static final int ROW_ITEM_SKILL_ID = 19;
/*      */   private static final int ROW_ITEM_SKILL_LEVEL = 20;
/*      */   private static final int ROW_CONTROLLER_ID = 21;
/*      */   private static final int ROW_JITTER_PERCENT = 22;
/*      */   public static final int TYPE_UNKNOWN = -1;
/*      */   public static final int TYPE_PREFIX = 1;
/*      */   public static final int TYPE_SUFFIX = 2;
/*      */   public static final int TYPE_MODIFIER = 3;
/*      */   public static final int TYPE_COMPLETION = 4;
/*      */   
/*      */   public static class AffixComparator implements Comparator<DBAffix> {
/*      */     public int compare(DBAffix affix1, DBAffix affix2) {
/*   41 */       if (affix1 == null) {
/*   42 */         if (affix2 == null) return 0; 
/*   43 */         return 1;
/*      */       } 
/*      */       
/*   46 */       if (affix2 == null) return -1;
/*      */       
/*   48 */       int rarity1 = affix1.getRarityInt();
/*   49 */       int rarity2 = affix2.getRarityInt();
/*      */       
/*   51 */       if (rarity1 != rarity2) {
/*   52 */         return rarity2 - rarity1;
/*      */       }
/*      */       
/*   55 */       String str1 = affix1.toString();
/*   56 */       if (str1 == null) {
/*   57 */         str1 = "";
/*      */       } else {
/*   59 */         str1 = str1.toUpperCase(GDMsgFormatter.locale);
/*      */       } 
/*      */       
/*   62 */       String str2 = affix2.toString();
/*   63 */       if (str2 == null) {
/*   64 */         str2 = "";
/*      */       } else {
/*   66 */         str2 = str2.toUpperCase(GDMsgFormatter.locale);
/*      */       } 
/*      */       
/*   69 */       return str1.compareTo(str2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/*   81 */       if (obj == null) return false;
/*      */       
/*   83 */       return obj.getClass().equals(AffixComparator.class);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  119 */   private static List<DBAffix> listBuffer = null;
/*  120 */   private static List<DBAffix> listPrefix = null;
/*  121 */   private static List<DBAffix> listSuffix = null;
/*  122 */   private static ConcurrentHashMap<String, DBAffix> hashBuffer = new ConcurrentHashMap<>();
/*      */   
/*      */   private static boolean bufferComplete = false;
/*      */   
/*      */   private String affixID;
/*      */   
/*      */   private String nameMS;
/*      */   private String nameFS;
/*      */   private String nameNS;
/*      */   private String nameMP;
/*      */   private String nameFP;
/*      */   private String nameNP;
/*      */   private String rarity;
/*      */   private int reqLevel;
/*      */   private int type;
/*      */   private String convertIn;
/*      */   private String convertOut;
/*      */   private String convertIn2;
/*      */   private String convertOut2;
/*      */   private int offensiveChance;
/*      */   private int retaliationChance;
/*      */   private int lootRandomCost;
/*      */   private String petAffixID;
/*      */   private String itemSkillID;
/*      */   private int itemSkillLevel;
/*      */   private String controllerID;
/*      */   private int jitterPercent;
/*      */   private List<DBStat> stats;
/*      */   private List<DBStatBonusRace> statBonusRaces;
/*      */   private List<DBSkillBonus> bonuses;
/*      */   private List<DBSkillModifier> skillModifiers;
/*      */   private DBAffix dbPetAffix;
/*      */   private DBSkill dbPetSkill;
/*      */   private DBSkill dbItemSkill;
/*      */   private DBController dbController;
/*      */   private String strDescription;
/*      */   
/*      */   public DBAffix() {
/*  160 */     this.affixID = null;
/*  161 */     this.nameMS = null;
/*  162 */     this.nameFS = null;
/*  163 */     this.nameNS = null;
/*  164 */     this.nameMP = null;
/*  165 */     this.nameFP = null;
/*  166 */     this.nameNP = null;
/*  167 */     this.rarity = null;
/*  168 */     this.reqLevel = 0;
/*  169 */     this.type = -1;
/*  170 */     this.convertIn = null;
/*  171 */     this.convertOut = null;
/*  172 */     this.convertIn2 = null;
/*  173 */     this.convertOut2 = null;
/*  174 */     this.offensiveChance = 0;
/*  175 */     this.retaliationChance = 0;
/*  176 */     this.lootRandomCost = 0;
/*  177 */     this.petAffixID = null;
/*  178 */     this.itemSkillID = null;
/*  179 */     this.itemSkillLevel = 1;
/*  180 */     this.controllerID = null;
/*      */     
/*  182 */     this.stats = new LinkedList<>();
/*  183 */     this.statBonusRaces = new LinkedList<>();
/*  184 */     this.bonuses = new LinkedList<>();
/*  185 */     this.skillModifiers = new LinkedList<>();
/*  186 */     this.dbPetAffix = null;
/*  187 */     this.dbPetSkill = null;
/*  188 */     this.dbItemSkill = null;
/*  189 */     this.jitterPercent = 0;
/*      */     
/*  191 */     this.strDescription = null;
/*      */   }
/*      */   
/*      */   private DBAffix(ARZRecord record) {
/*  195 */     this.affixID = record.getFileName();
/*      */     
/*  197 */     determineAffixType(record.getFileName());
/*      */     
/*  199 */     setLootRandomName(record.getLootRandomName());
/*  200 */     this.rarity = record.getRarity();
/*  201 */     this.reqLevel = record.getRequiredLevel();
/*  202 */     this.convertIn = record.getConversionIn();
/*  203 */     this.convertOut = record.getConversionOut();
/*  204 */     this.convertIn2 = record.getConversionIn2();
/*  205 */     this.convertOut2 = record.getConversionOut2();
/*  206 */     this.offensiveChance = record.getOffensiveChance();
/*  207 */     this.retaliationChance = record.getRetaliationChance();
/*  208 */     this.lootRandomCost = record.getLootRandomCost();
/*  209 */     this.petAffixID = record.getPetBonusID();
/*  210 */     this.itemSkillID = record.getItemSkillID();
/*  211 */     this.itemSkillLevel = record.getItemSkillLevel();
/*  212 */     this.controllerID = record.getSkillControllerID();
/*      */     
/*  214 */     this.stats = record.getDBStatList();
/*  215 */     this.statBonusRaces = record.getDBStatBonusRaceList();
/*  216 */     this.bonuses = record.dbSkillBonuses;
/*  217 */     this.skillModifiers = record.getSkillModifierList();
/*  218 */     this.dbPetAffix = null;
/*  219 */     this.dbPetSkill = null;
/*  220 */     this.dbItemSkill = null;
/*  221 */     this.jitterPercent = record.getRNGPercent();
/*      */     
/*  223 */     this.strDescription = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/*  232 */     if (o == null) return false; 
/*  233 */     if (!o.getClass().equals(DBAffix.class)) return false;
/*      */     
/*  235 */     DBAffix affix = (DBAffix)o;
/*      */     
/*  237 */     return affix.affixID.equals(this.affixID);
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  242 */     return this.affixID.hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(DBAffix affix) {
/*  250 */     if (this.type < affix.type) return -1; 
/*  251 */     if (this.type > affix.type) return 1;
/*      */     
/*  253 */     int oRarity = getRarityInt();
/*  254 */     int aRarity = affix.getRarityInt();
/*      */     
/*  256 */     if (oRarity < aRarity) return -1; 
/*  257 */     if (oRarity > aRarity) return 1;
/*      */     
/*  259 */     if (this.reqLevel < affix.reqLevel) return -1; 
/*  260 */     if (this.reqLevel > affix.reqLevel) return 1;
/*      */     
/*  262 */     if (this.nameMS == null) {
/*  263 */       if (affix.nameMS == null) {
/*  264 */         if (this.affixID == null) {
/*  265 */           if (affix.affixID == null)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  287 */             return 0; }  return -1;
/*      */         }  if (affix.affixID == null)
/*      */           return 1;  return this.affixID.compareTo(affix.affixID);
/*      */       } 
/*      */       return -1;
/*      */     } 
/*      */     if (affix.nameMS == null)
/*      */       return 1; 
/*  295 */     return this.nameMS.compareTo(affix.nameMS); } public String getAffixID() { return this.affixID; }
/*      */ 
/*      */   
/*      */   public String getGenderText(int code) {
/*  299 */     String name = null;
/*      */     
/*  301 */     switch (code)
/*      */     { case 0:
/*  303 */         name = this.nameMS;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  332 */         return name;case 1: name = this.nameFS; return name;case 2: name = this.nameNS; return name;case 3: name = this.nameMP; return name;case 4: name = this.nameFP; return name;case 5: name = this.nameNP; return name; }  name = this.nameMS; return name;
/*      */   }
/*      */   
/*      */   public String getRarity() {
/*  336 */     return this.rarity;
/*      */   }
/*      */   
/*      */   public int getRequiredlevel() {
/*  340 */     return this.reqLevel;
/*      */   }
/*      */   
/*      */   public int getAffixType() {
/*  344 */     return this.type;
/*      */   }
/*      */   
/*      */   public int getOffensiveChance() {
/*  348 */     return this.offensiveChance;
/*      */   }
/*      */   
/*      */   public int getRetaliationChance() {
/*  352 */     return this.retaliationChance;
/*      */   }
/*      */   
/*      */   public String getConvertIn() {
/*  356 */     return this.convertIn;
/*      */   }
/*      */   
/*      */   public String getConvertOut() {
/*  360 */     return this.convertOut;
/*      */   }
/*      */   
/*      */   public String getConvertIn2() {
/*  364 */     return this.convertIn2;
/*      */   }
/*      */   
/*      */   public String getConvertOut2() {
/*  368 */     return this.convertOut2;
/*      */   }
/*      */   
/*      */   public int getConvertPerc() {
/*  372 */     DBStat stat = DBStat.getDBStat(this.stats, "conversionPercentage", 1);
/*      */     
/*  374 */     if (stat == null) return 0;
/*      */     
/*  376 */     return (int)stat.getStatMin();
/*      */   }
/*      */   
/*      */   public int getConvertPerc2() {
/*  380 */     DBStat stat = DBStat.getDBStat(this.stats, "conversionPercentage2", 1);
/*      */     
/*  382 */     if (stat == null) return 0;
/*      */     
/*  384 */     return (int)stat.getStatMin();
/*      */   }
/*      */   
/*      */   public int getLootRandomCost() {
/*  388 */     return this.lootRandomCost;
/*      */   }
/*      */   
/*      */   public int getJitterPercent() {
/*  392 */     return this.jitterPercent;
/*      */   }
/*      */   
/*      */   public List<DBStat> getStatList() {
/*  396 */     return this.stats;
/*      */   }
/*      */   
/*      */   public List<DBStat> getStatList(int scalePercent) {
/*  400 */     if (this.stats == null) return null;
/*      */     
/*  402 */     List<DBStat> list = new LinkedList<>();
/*  403 */     for (DBStat stat : this.stats) {
/*  404 */       stat = stat.clone();
/*      */       
/*  406 */       list.add(stat);
/*      */     } 
/*      */     
/*  409 */     DBStat.applyAttributeScale(list, scalePercent);
/*      */     
/*  411 */     return list;
/*      */   }
/*      */   
/*      */   public List<DBSkillBonus> getSkillBonusList() {
/*  415 */     return this.bonuses;
/*      */   }
/*      */   
/*      */   public List<DBSkillModifier> getSkillModifierList() {
/*  419 */     return this.skillModifiers;
/*      */   }
/*      */   
/*      */   public DBAffix getPetAffix() {
/*  423 */     return this.dbPetAffix;
/*      */   }
/*      */   
/*      */   public DBSkill getPetSkill() {
/*  427 */     return this.dbPetSkill;
/*      */   }
/*      */   
/*      */   public String getItemSkillID() {
/*  431 */     return this.itemSkillID;
/*      */   }
/*      */   
/*      */   public int getItemSkillLevel() {
/*  435 */     return this.itemSkillLevel;
/*      */   }
/*      */   
/*      */   public DBSkill getItemSkill() {
/*  439 */     return this.dbItemSkill;
/*      */   }
/*      */   
/*      */   public String getControllerID() {
/*  443 */     return this.controllerID;
/*      */   }
/*      */   
/*      */   public DBController getItemSkillController() {
/*  447 */     return this.dbController;
/*      */   }
/*      */   
/*      */   public String getControllerTriggerType() {
/*  451 */     if (this.dbController == null) return null;
/*      */     
/*  453 */     return this.dbController.getTriggerType();
/*      */   }
/*      */   
/*      */   public int getControllerTriggerChance() {
/*  457 */     if (this.dbController == null) return 0;
/*      */     
/*  459 */     return this.dbController.getTriggerChance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTotalAttCount() {
/*  467 */     int total = 0;
/*      */     
/*  469 */     for (DBStat stat : this.stats) {
/*  470 */       if (stat.getStatMin() > 0.0F) total++; 
/*  471 */       if (stat.getStatModifier() > 0) total++; 
/*  472 */       if (stat.getDurationModifier() > 0) total++; 
/*  473 */       if (stat.getMaxResist() > 0) total++;
/*      */     
/*      */     } 
/*  476 */     if (this.statBonusRaces != null && !this.statBonusRaces.isEmpty()) total += this.statBonusRaces.size();
/*      */     
/*  478 */     return total;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getRarityInt() {
/*  486 */     if (this.rarity == null) return -1;
/*      */     
/*  488 */     if (this.rarity.equals("Broken")) return 1; 
/*  489 */     if (this.rarity.equals("Magical")) return 2; 
/*  490 */     if (this.rarity.equals("Rare")) return 3; 
/*  491 */     if (this.rarity.equals("Epic")) return 4; 
/*  492 */     if (this.rarity.equals("Legendary")) return 5;
/*      */     
/*  494 */     return -1;
/*      */   }
/*      */   
/*      */   public boolean matchesCriteria(SelectionCriteria criteria) {
/*  498 */     if (criteria.isInitial()) return true;
/*      */     
/*  500 */     if (criteria.combiMode == SelectionCriteria.CombinationMode.OR) {
/*  501 */       return matchesCriteriaOr(criteria);
/*      */     }
/*      */     
/*  504 */     if (criteria.combiMode == SelectionCriteria.CombinationMode.AND) {
/*  505 */       return matchesCriteriaAnd(criteria);
/*      */     }
/*      */     
/*  508 */     return false;
/*      */   }
/*      */   
/*      */   private boolean matchesCriteriaOr(SelectionCriteria criteria) {
/*  512 */     if (criteria.isInitial()) return true;
/*      */     
/*  514 */     if (criteria.levelMin > 0 && 
/*  515 */       this.reqLevel < criteria.levelMin) return false;
/*      */ 
/*      */     
/*  518 */     if (criteria.levelMax > 0 && 
/*  519 */       this.reqLevel > criteria.levelMax) return false;
/*      */ 
/*      */     
/*  522 */     if (!criteria.itemRarity.isEmpty() && 
/*  523 */       !criteria.itemRarity.contains(this.rarity)) return false;
/*      */ 
/*      */     
/*  526 */     if (criteria.dmgConversionFrom != null && 
/*  527 */       !criteria.dmgConversionFrom.equals(this.convertIn) && 
/*  528 */       !criteria.dmgConversionFrom.equals(this.convertIn2)) return false;
/*      */ 
/*      */     
/*  531 */     if (criteria.dmgConversionTo != null && 
/*  532 */       !criteria.dmgConversionTo.equals(this.convertOut) && 
/*  533 */       !criteria.dmgConversionTo.equals(this.convertOut2)) return false;
/*      */ 
/*      */     
/*  536 */     if (criteria.petBonus && (
/*  537 */       this.dbPetAffix != null || this.dbPetSkill != null)) return true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  543 */     if (criteria.statInfos == null) return true; 
/*  544 */     if (criteria.statInfos.isEmpty()) return true;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  549 */     if (this.stats == null) return false;
/*      */     
/*  551 */     for (SelectionCriteria.StatInfo info : criteria.statInfos) {
/*  552 */       for (String statType : info.statTypes) {
/*  553 */         for (DBStat stat : this.stats) {
/*  554 */           if (stat.getStatType().equals(statType)) {
/*  555 */             if (info.flat == info.percentage) {
/*  556 */               if (info.maxResist) {
/*  557 */                 if (info.flat) {
/*  558 */                   if (stat.getStatMin() > 0.0F) return true; 
/*  559 */                   if (stat.getStatModifier() > 0) return true; 
/*      */                 } 
/*  561 */                 if (stat.getMaxResist() > 0) return true; 
/*      */               } else {
/*  563 */                 if (stat.getStatMin() > 0.0F) return true; 
/*  564 */                 if (stat.getStatModifier() > 0) return true; 
/*      */               } 
/*      */             } else {
/*  567 */               if (info.flat && 
/*  568 */                 stat.getStatMin() > 0.0F) return true;
/*      */ 
/*      */               
/*  571 */               if (info.percentage && 
/*  572 */                 stat.getStatModifier() > 0) return true;
/*      */             
/*      */             } 
/*      */             
/*  576 */             if (info.maxResist && 
/*  577 */               stat.getMaxResist() > 0) return true;
/*      */           
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  584 */     return false;
/*      */   }
/*      */   
/*      */   private boolean matchesCriteriaAnd(SelectionCriteria criteria) {
/*  588 */     if (criteria.isInitial()) return true;
/*      */     
/*  590 */     if (criteria.levelMin > 0 && 
/*  591 */       this.reqLevel < criteria.levelMin) return false;
/*      */ 
/*      */     
/*  594 */     if (criteria.levelMax > 0 && 
/*  595 */       this.reqLevel > criteria.levelMax) return false;
/*      */ 
/*      */     
/*  598 */     if (!criteria.itemRarity.isEmpty() && 
/*  599 */       !criteria.itemRarity.contains(this.rarity)) return false;
/*      */ 
/*      */     
/*  602 */     if (criteria.dmgConversionFrom != null && 
/*  603 */       !criteria.dmgConversionFrom.equals(this.convertIn) && 
/*  604 */       !criteria.dmgConversionFrom.equals(this.convertIn2)) return false;
/*      */ 
/*      */     
/*  607 */     if (criteria.dmgConversionTo != null && 
/*  608 */       !criteria.dmgConversionTo.equals(this.convertOut) && 
/*  609 */       !criteria.dmgConversionTo.equals(this.convertOut2)) return false;
/*      */ 
/*      */     
/*  612 */     if (criteria.petBonus && 
/*  613 */       this.dbPetAffix == null && this.dbPetSkill == null) return false;
/*      */ 
/*      */     
/*  616 */     boolean fitsAll = false;
/*  617 */     if (criteria.statInfos == null)
/*  618 */     { fitsAll = true; }
/*      */     
/*  620 */     else if (criteria.statInfos.isEmpty()) { fitsAll = true; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  626 */     if (criteria.statInfos == null) return fitsAll; 
/*  627 */     if (criteria.statInfos.isEmpty()) return fitsAll;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  632 */     if (this.stats == null) return false;
/*      */     
/*  634 */     fitsAll = true;
/*      */     
/*  636 */     for (SelectionCriteria.StatInfo info : criteria.statInfos) {
/*  637 */       boolean found = false;
/*      */       
/*  639 */       for (String statType : info.statTypes) {
/*  640 */         for (DBStat stat : this.stats) {
/*  641 */           if (stat.getStatType().equals(statType)) {
/*  642 */             if (info.flat == info.percentage) {
/*  643 */               if (info.maxResist) {
/*  644 */                 if (info.flat) {
/*  645 */                   if (stat.getStatMin() > 0.0F) {
/*  646 */                     found = true;
/*      */                     
/*      */                     break;
/*      */                   } 
/*  650 */                   if (stat.getStatModifier() > 0) {
/*  651 */                     found = true;
/*      */                     
/*      */                     break;
/*      */                   } 
/*      */                 } 
/*  656 */                 if (stat.getMaxResist() > 0) {
/*  657 */                   found = true;
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } else {
/*  662 */                 if (stat.getStatMin() > 0.0F) {
/*  663 */                   found = true;
/*      */                   
/*      */                   break;
/*      */                 } 
/*  667 */                 if (stat.getStatModifier() > 0) {
/*  668 */                   found = true;
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             } else {
/*  674 */               if (info.flat && 
/*  675 */                 stat.getStatMin() > 0.0F) {
/*  676 */                 found = true;
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */               
/*  682 */               if (info.percentage && 
/*  683 */                 stat.getStatModifier() > 0) {
/*  684 */                 found = true;
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */             
/*  691 */             if (info.maxResist && 
/*  692 */               stat.getMaxResist() > 0) {
/*  693 */               found = true;
/*      */ 
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  701 */         if (found)
/*      */           break; 
/*      */       } 
/*  704 */       if (!found) {
/*  705 */         fitsAll = false;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/*  711 */     return fitsAll;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setLootRandomName(String lootRandomName) {
/*  719 */     String text = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_ITEMS, lootRandomName, false);
/*      */     
/*  721 */     if (text != null) {
/*  722 */       String[] genders = ARZDecompress.getGenderTexts(text);
/*      */       
/*  724 */       setGenderNames(genders);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setGenderNames(String[] genders) {
/*  729 */     this.nameMS = genders[0];
/*  730 */     this.nameFS = genders[1];
/*  731 */     this.nameNS = genders[2];
/*  732 */     this.nameMP = genders[3];
/*  733 */     this.nameFP = genders[4];
/*  734 */     this.nameNP = genders[5];
/*      */   }
/*      */   
/*      */   private void determineAffixType(String filename) {
/*  738 */     this.type = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  746 */     if (filename.contains("prefix")) this.type = 1; 
/*  747 */     if (filename.contains("suffix")) this.type = 2; 
/*  748 */     if (filename.contains("crafting")) this.type = 3; 
/*  749 */     if (filename.contains("completion")) this.type = 4;
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void clearBuffer() {
/*  757 */     hashBuffer.clear();
/*  758 */     bufferComplete = false;
/*      */     
/*  760 */     if (GDStashFrame.dbConfig != null)
/*      */     {
/*      */ 
/*      */       
/*  764 */       fillBuffer();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void createTables() throws SQLException {
/*  770 */     String dropTable = "DROP TABLE GD_AFFIX";
/*  771 */     String createTable = "CREATE TABLE GD_AFFIX (AFFIX_ID VARCHAR(256) NOT NULL, RARITY           VARCHAR(32), NAME_MS          VARCHAR(256), NAME_FS          VARCHAR(256), NAME_NS          VARCHAR(256), NAME_MP          VARCHAR(256), NAME_FP          VARCHAR(256), NAME_NP          VARCHAR(256), REQ_LEVEL        INTEGER, TYPE             INTEGER, CONVERT_IN       VARCHAR(16), CONVERT_OUT      VARCHAR(16), CONVERT_IN_2     VARCHAR(16), CONVERT_OUT_2    VARCHAR(16), OFFENSE_PRC      INTEGER, RETAL_PRC        INTEGER, LOOT_RAND_COST   INTEGER, PET_AFFIX_ID     VARCHAR(256), ITEM_SKILL_ID    VARCHAR(256), ITEM_SKILL_LEVEL INTEGER, CONTROLLER_ID    VARCHAR(256), RNG_PERCENT      INTEGER, PRIMARY KEY (AFFIX_ID))";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  797 */     try (Connection conn = GDDBData.getConnection()) {
/*  798 */       boolean auto = conn.getAutoCommit();
/*  799 */       conn.setAutoCommit(false);
/*      */       
/*  801 */       try (Statement st = conn.createStatement()) {
/*  802 */         if (GDDBUtil.tableExists(conn, "GD_AFFIX")) {
/*  803 */           st.execute(dropTable);
/*      */         }
/*  805 */         if (GDDBUtil.tableExists(conn, "GD_AFFIX_CHAR")) {
/*  806 */           st.execute("DROP TABLE GD_AFFIX_CHAR");
/*      */         }
/*  808 */         if (GDDBUtil.tableExists(conn, "GD_AFFIX_CHARRACES")) {
/*  809 */           st.execute("DROP TABLE GD_AFFIX_CHARRACES");
/*      */         }
/*  811 */         if (GDDBUtil.tableExists(conn, "GD_AFFIX_DAMAGE")) {
/*  812 */           st.execute("DROP TABLE GD_AFFIX_DAMAGE");
/*      */         }
/*  814 */         st.execute(createTable);
/*  815 */         st.close();
/*      */         
/*  817 */         conn.commit();
/*      */         
/*  819 */         DBStat.createAffixTable(conn);
/*  820 */         DBStatBonusRace.createAffixTable(conn);
/*  821 */         DBSkillBonus.createAffixTable(conn);
/*  822 */         DBSkillModifier.createAffixTable(conn);
/*      */       }
/*  824 */       catch (SQLException ex) {
/*  825 */         conn.rollback();
/*      */         
/*  827 */         Object[] args = { "GD_AFFIX" };
/*  828 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*      */         
/*  830 */         GDMsgLogger.addError(msg);
/*      */         
/*  832 */         throw ex;
/*      */       } finally {
/*      */         
/*  835 */         conn.setAutoCommit(auto);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void delete(String affixID) throws SQLException {
/*  841 */     String deleteEntry = "DELETE FROM GD_AFFIX WHERE AFFIX_ID = ?";
/*      */     
/*  843 */     try (Connection conn = GDDBData.getConnection()) {
/*  844 */       boolean auto = conn.getAutoCommit();
/*  845 */       conn.setAutoCommit(false);
/*      */       
/*  847 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/*  848 */         ps.setString(1, affixID);
/*  849 */         ps.executeUpdate();
/*  850 */         ps.close();
/*      */         
/*  852 */         DBStat.deleteAffix(conn, affixID);
/*  853 */         DBStatBonusRace.deleteAffix(conn, affixID);
/*  854 */         DBSkillBonus.deleteAffix(conn, affixID);
/*  855 */         DBSkillModifier.deleteAffix(conn, affixID);
/*      */         
/*  857 */         conn.commit();
/*      */       }
/*  859 */       catch (SQLException ex) {
/*  860 */         conn.rollback();
/*      */         
/*  862 */         Object[] args = { affixID, "GD_AFFIX" };
/*  863 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*      */         
/*  865 */         GDMsgLogger.addError(msg);
/*  866 */         GDMsgLogger.addError(ex);
/*      */         
/*  868 */         throw ex;
/*      */       } finally {
/*      */         
/*  871 */         conn.setAutoCommit(auto);
/*      */       }
/*      */     
/*  874 */     } catch (SQLException ex) {
/*  875 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void insert(ARZRecord record) throws SQLException {
/*  880 */     DBAffix entry = get(record.getFileName());
/*      */     
/*  882 */     if (entry != null)
/*      */       return; 
/*  884 */     DBAffix affix = new DBAffix(record);
/*      */     
/*  886 */     String insert = "INSERT INTO GD_AFFIX VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */     
/*  888 */     try (Connection conn = GDDBData.getConnection()) {
/*  889 */       boolean auto = conn.getAutoCommit();
/*  890 */       conn.setAutoCommit(false);
/*      */       
/*  892 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/*  893 */         ps.setString(1, affix.affixID);
/*  894 */         ps.setString(2, affix.rarity);
/*  895 */         ps.setString(3, affix.nameMS);
/*  896 */         ps.setString(4, affix.nameFS);
/*  897 */         ps.setString(5, affix.nameNS);
/*  898 */         ps.setString(6, affix.nameMP);
/*  899 */         ps.setString(7, affix.nameFP);
/*  900 */         ps.setString(8, affix.nameNP);
/*  901 */         ps.setInt(9, affix.reqLevel);
/*  902 */         ps.setInt(10, affix.type);
/*  903 */         ps.setString(11, affix.convertIn);
/*  904 */         ps.setString(12, affix.convertOut);
/*  905 */         ps.setString(13, affix.convertIn2);
/*  906 */         ps.setString(14, affix.convertOut2);
/*  907 */         ps.setInt(15, affix.offensiveChance);
/*  908 */         ps.setInt(16, affix.retaliationChance);
/*  909 */         ps.setInt(17, affix.lootRandomCost);
/*  910 */         ps.setString(18, affix.petAffixID);
/*  911 */         ps.setString(19, affix.itemSkillID);
/*  912 */         ps.setInt(20, affix.itemSkillLevel);
/*  913 */         ps.setString(21, affix.controllerID);
/*  914 */         ps.setInt(22, affix.jitterPercent);
/*      */         
/*  916 */         ps.executeUpdate();
/*  917 */         ps.close();
/*      */         
/*  919 */         DBStat.insertAffix(conn, affix.affixID, affix.stats);
/*  920 */         DBStatBonusRace.insertAffix(conn, affix.affixID, affix.statBonusRaces);
/*  921 */         DBSkillBonus.insertAffix(conn, affix.affixID, affix.bonuses);
/*  922 */         DBSkillModifier.insertAffix(conn, affix.affixID, affix.skillModifiers);
/*      */         
/*  924 */         conn.commit();
/*      */       }
/*  926 */       catch (SQLException ex) {
/*  927 */         conn.rollback();
/*      */         
/*  929 */         Object[] args = { record.getFileName(), "GD_AFFIX" };
/*  930 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*      */         
/*  932 */         GDMsgLogger.addLowError(msg);
/*  933 */         GDMsgLogger.addLowError(ex);
/*      */       } finally {
/*      */         
/*  936 */         conn.setAutoCommit(auto);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static DBAffix get(String affixID) {
/*  942 */     DBAffix affix = null;
/*      */     
/*  944 */     affix = hashBuffer.get(affixID);
/*      */     
/*  946 */     if (bufferComplete) return affix;
/*      */     
/*  948 */     if (affix == null)
/*      */     {
/*  950 */       affix = getDB(affixID);
/*      */     }
/*      */     
/*  953 */     return affix;
/*      */   }
/*      */   
/*      */   private static DBAffix getDB(String affixID) {
/*  957 */     DBAffix affix = null;
/*      */     
/*  959 */     String command = "SELECT * FROM GD_AFFIX WHERE AFFIX_ID = ?";
/*      */     
/*  961 */     try(Connection conn = GDDBData.getConnection(); 
/*  962 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*  963 */       ps.setString(1, affixID);
/*      */       
/*  965 */       try (ResultSet rs = ps.executeQuery()) {
/*  966 */         List<DBAffix> list = wrap(rs);
/*      */         
/*  968 */         if (list.isEmpty()) {
/*  969 */           affix = null;
/*      */         } else {
/*  971 */           affix = list.get(0);
/*      */         } 
/*      */         
/*  974 */         conn.commit();
/*      */       }
/*  976 */       catch (SQLException ex) {
/*  977 */         affix = null;
/*      */         
/*  979 */         throw ex;
/*      */       }
/*      */     
/*  982 */     } catch (SQLException ex) {
/*  983 */       Object[] args = { affixID, "GD_AFFIX" };
/*  984 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/*  986 */       GDMsgLogger.addError(msg);
/*  987 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/*  990 */     return affix;
/*      */   }
/*      */   
/*      */   public static List<DBAffix> getByAffixIDs(List<String> affixIDs) {
/*  994 */     List<DBAffix> list = new LinkedList<>();
/*      */     
/*  996 */     for (String affixID : affixIDs) {
/*  997 */       DBAffix affix = get(affixID);
/*  998 */       if (affix != null) list.add(affix);
/*      */     
/*      */     } 
/* 1001 */     return list;
/*      */   }
/*      */   
/*      */   private static List<DBAffix> getAll() {
/* 1005 */     List<DBAffix> list = new LinkedList<>();
/*      */     
/* 1007 */     String command = "SELECT * FROM GD_AFFIX";
/*      */     
/* 1009 */     try(Connection conn = GDDBData.getConnection(); 
/* 1010 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*      */       
/* 1012 */       try (ResultSet rs = ps.executeQuery()) {
/* 1013 */         list = wrap(rs);
/*      */         
/* 1015 */         conn.commit();
/*      */       }
/* 1017 */       catch (SQLException ex) {
/* 1018 */         throw ex;
/*      */       }
/*      */     
/* 1021 */     } catch (SQLException ex) {
/* 1022 */       Object[] args = { "<all>", "GD_AFFIX" };
/* 1023 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 1025 */       GDMsgLogger.addError(msg);
/* 1026 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 1029 */     return list;
/*      */   }
/*      */   
/*      */   private void createBonusRaceStats(DBStat stat) {
/* 1033 */     if (stat == null)
/* 1034 */       return;  if (this.statBonusRaces == null)
/* 1035 */       return;  if (this.statBonusRaces.isEmpty())
/*      */       return; 
/* 1037 */     this.stats.remove(stat);
/*      */     
/* 1039 */     List<DBStat> list = DBStat.createStatsFromRaceBonusList(stat, this.statBonusRaces);
/* 1040 */     this.stats.addAll(list);
/*      */   }
/*      */   
/*      */   private static List<DBAffix> wrap(ResultSet rs) throws SQLException {
/* 1044 */     List<DBAffix> list = new LinkedList<>();
/*      */     
/* 1046 */     while (rs.next()) {
/* 1047 */       DBAffix affix = new DBAffix();
/*      */       
/* 1049 */       affix.affixID = rs.getString(1);
/*      */       
/* 1051 */       DBAffix buff = hashBuffer.get(affix.affixID);
/* 1052 */       if (buff != null) {
/* 1053 */         list.add(buff);
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1058 */       affix.rarity = rs.getString(2);
/* 1059 */       affix.nameMS = rs.getString(3);
/* 1060 */       affix.nameFS = rs.getString(4);
/* 1061 */       affix.nameNS = rs.getString(5);
/* 1062 */       affix.nameMP = rs.getString(6);
/* 1063 */       affix.nameFP = rs.getString(7);
/* 1064 */       affix.nameNP = rs.getString(8);
/* 1065 */       affix.reqLevel = rs.getInt(9);
/* 1066 */       affix.type = rs.getInt(10);
/* 1067 */       affix.convertIn = rs.getString(11);
/* 1068 */       affix.convertOut = rs.getString(12);
/* 1069 */       affix.convertIn2 = rs.getString(13);
/* 1070 */       affix.convertOut2 = rs.getString(14);
/* 1071 */       affix.offensiveChance = rs.getInt(15);
/* 1072 */       affix.retaliationChance = rs.getInt(16);
/* 1073 */       affix.lootRandomCost = rs.getInt(17);
/* 1074 */       affix.petAffixID = rs.getString(18);
/* 1075 */       affix.itemSkillID = rs.getString(19);
/* 1076 */       affix.itemSkillLevel = rs.getInt(20);
/* 1077 */       affix.controllerID = rs.getString(21);
/* 1078 */       affix.jitterPercent = rs.getInt(22);
/*      */       
/* 1080 */       affix.stats = DBStat.getAffix(affix.affixID);
/* 1081 */       Collections.sort(affix.stats);
/*      */       
/* 1083 */       DBStat stat = DBStat.getByType(affix.stats, "racialBonusPercentDamage", 1);
/* 1084 */       if (stat != null) {
/* 1085 */         affix.statBonusRaces = DBStatBonusRace.getAffix(affix.affixID);
/* 1086 */         affix.createBonusRaceStats(stat);
/*      */       } 
/*      */       
/* 1089 */       affix.bonuses = DBSkillBonus.getAffix(affix.affixID);
/* 1090 */       affix.skillModifiers = DBSkillModifier.getAffix(affix.affixID);
/*      */       
/* 1092 */       if (affix.petAffixID != null) {
/*      */         
/* 1094 */         affix.dbPetAffix = get(affix.petAffixID);
/* 1095 */         affix.dbPetSkill = DBSkill.get(affix.petAffixID);
/*      */       } 
/* 1097 */       if (affix.itemSkillID != null) affix.dbItemSkill = DBSkill.get(affix.itemSkillID);
/*      */       
/* 1099 */       if (affix.controllerID != null) {
/* 1100 */         affix.dbController = DBController.get(affix.controllerID);
/*      */       }
/*      */       
/* 1103 */       list.add(affix);
/* 1104 */       hashBuffer.put(affix.affixID, affix);
/*      */     } 
/*      */     
/* 1107 */     return list;
/*      */   }
/*      */   
/*      */   public static void fillBuffer() {
/* 1111 */     if (bufferComplete)
/*      */       return; 
/* 1113 */     if (!GDStashFrame.dbConfig.configInit || !GDStashFrame.dbConfig.gddbInit) {
/*      */       
/* 1115 */       listBuffer = new LinkedList<>();
/* 1116 */       listPrefix = new LinkedList<>();
/* 1117 */       listSuffix = new LinkedList<>();
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1122 */     listBuffer = getAll();
/* 1123 */     listPrefix = new LinkedList<>();
/* 1124 */     listSuffix = new LinkedList<>();
/*      */     
/* 1126 */     for (DBAffix affix : listBuffer) {
/* 1127 */       if (affix.getAffixType() == 1) {
/* 1128 */         listPrefix.add(affix);
/*      */       }
/* 1130 */       if (affix.getAffixType() == 2) {
/* 1131 */         listSuffix.add(affix);
/*      */       }
/*      */     } 
/*      */     
/* 1135 */     Collections.sort(listPrefix, new AffixComparator());
/* 1136 */     Collections.sort(listSuffix, new AffixComparator());
/*      */     
/* 1138 */     bufferComplete = true;
/*      */   }
/*      */   
/*      */   public static List<DBAffix> getFullAffixList() {
/* 1142 */     fillBuffer();
/*      */     
/* 1144 */     return listBuffer;
/*      */   }
/*      */   
/*      */   public static List<DBAffix> getPrefixList() {
/* 1148 */     fillBuffer();
/*      */     
/* 1150 */     return listPrefix;
/*      */   }
/*      */   
/*      */   public static List<DBAffix> getSuffixList() {
/* 1154 */     fillBuffer();
/*      */     
/* 1156 */     return listSuffix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DetailComposer getBonusComposer(String prefix) {
/* 1164 */     DetailComposer composer = new DetailComposer();
/*      */     
/* 1166 */     if (this.stats != null && !this.stats.isEmpty()) {
/* 1167 */       for (DBStat stat : this.stats) {
/* 1168 */         BonusDetail bonus = stat.getBonusDetail(null, 1, prefix);
/* 1169 */         composer.add(bonus);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1174 */     if (this.bonuses != null && 
/* 1175 */       !this.bonuses.isEmpty()) {
/* 1176 */       for (DBSkillBonus skillBonus : this.bonuses) {
/* 1177 */         BonusDetail bonus = skillBonus.getBonusDetail();
/* 1178 */         composer.add(bonus);
/*      */       } 
/*      */     }
/*      */     
/* 1182 */     if (this.dbPetAffix != null) {
/* 1183 */       String pref = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_PREFIX_PET");
/*      */       
/* 1185 */       DetailComposer comp = this.dbPetAffix.getBonusComposer(pref);
/* 1186 */       composer.add(comp);
/*      */     } 
/*      */     
/* 1189 */     if (this.dbPetSkill != null) {
/* 1190 */       String pref = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_PREFIX_PET");
/*      */       
/* 1192 */       List<DBStat> stats = DBStat.getByLevel(this.dbPetSkill.getStatList(), 1);
/*      */       
/* 1194 */       if (stats != null && !stats.isEmpty()) {
/* 1195 */         for (DBStat stat : stats) {
/* 1196 */           BonusDetail bonus = stat.getBonusDetail(null, 1, pref);
/* 1197 */           composer.add(bonus);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1202 */     if (this.dbItemSkill != null) {
/* 1203 */       BonusDetail bonus = this.dbItemSkill.getBonusDetail();
/* 1204 */       composer.add(bonus);
/*      */     } 
/*      */     
/* 1207 */     if (this.skillModifiers != null) {
/* 1208 */       for (DBSkillModifier sm : this.skillModifiers) {
/* 1209 */         BonusDetail bonus = sm.getBonusDetail(null, 1, prefix);
/* 1210 */         composer.add(bonus);
/*      */       } 
/*      */     }
/*      */     
/* 1214 */     return composer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getString(String prefix) {
/* 1222 */     DetailComposer composer = getBonusComposer(null);
/*      */     
/* 1224 */     if (this.nameMS != null) {
/* 1225 */       composer.preText = this.nameMS;
/*      */     } else {
/* 1227 */       boolean found = false;
/* 1228 */       String search = null;
/*      */ 
/*      */       
/* 1231 */       if (!found) {
/* 1232 */         search = "records/items/lootaffixes/crafting/";
/* 1233 */         found = this.affixID.startsWith(search);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1249 */       composer.preText = null;
/*      */       
/* 1251 */       if (found) {
/*      */         
/* 1253 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ad05_pierceresist.dbr") || this.affixID
/* 1254 */           .equals("records/items/lootaffixes/crafting/ad06_protection.dbr") || this.affixID
/* 1255 */           .equals("records/items/lootaffixes/crafting/ac05_physique.dbr") || this.affixID
/* 1256 */           .equals("records/items/lootaffixes/crafting/ao01_physicaldmg.dbr")) {
/* 1257 */           if (composer.preText != null) {
/* 1258 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_ANGRIM");
/*      */           } else {
/* 1260 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_ANGRIM");
/*      */           } 
/*      */         }
/*      */         
/* 1264 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ac04_energyregen.dbr") || this.affixID
/* 1265 */           .equals("records/items/lootaffixes/crafting/ad08_da.dbr") || this.affixID
/* 1266 */           .equals("records/items/lootaffixes/crafting/ac05_physique.dbr") || this.affixID
/* 1267 */           .equals("records/items/lootaffixes/crafting/ad08_elementalresist.dbr") || this.affixID
/* 1268 */           .equals("records/items/lootaffixes/crafting/ao05_elementaldmg.dbr")) {
/* 1269 */           if (composer.preText != null) {
/* 1270 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_DUNCAN");
/*      */           } else {
/* 1272 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_DUNCAN");
/*      */           } 
/*      */         }
/*      */         
/* 1276 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ad09_aetherresist.dbr") || this.affixID
/* 1277 */           .equals("records/items/lootaffixes/crafting/ad10_chaosresist.dbr") || this.affixID
/* 1278 */           .equals("records/items/lootaffixes/crafting/ac01_health.dbr")) {
/* 1279 */           if (composer.preText != null) {
/* 1280 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_BLACK_LEGION");
/*      */           } else {
/* 1282 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_BLACK_LEGION");
/*      */           } 
/*      */         }
/*      */         
/* 1286 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ad01_fireresist.dbr") || this.affixID
/* 1287 */           .equals("records/items/lootaffixes/crafting/ao11_firedmg.dbr") || this.affixID
/* 1288 */           .equals("records/items/lootaffixes/crafting/ao13_lightningdmg.dbr")) {
/* 1289 */           if (composer.preText != null) {
/* 1290 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_KYMONS_CHOSEN");
/*      */           } else {
/* 1292 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_KYMONS_CHOSEN");
/*      */           } 
/*      */         }
/*      */         
/* 1296 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ad02_coldresist.dbr") || this.affixID
/* 1297 */           .equals("records/items/lootaffixes/crafting/ao12_colddmg.dbr") || this.affixID
/* 1298 */           .equals("records/items/lootaffixes/crafting/ao07_vitalitydmg.dbr")) {
/* 1299 */           if (composer.preText != null) {
/* 1300 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_DEATHS_VIGIL");
/*      */           } else {
/* 1302 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_DEATHS_VIGIL");
/*      */           } 
/*      */         }
/*      */         
/* 1306 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ac04_energyregen.dbr") || this.affixID
/* 1307 */           .equals("records/items/lootaffixes/crafting/ad07_bleedresist.dbr") || this.affixID
/* 1308 */           .equals("records/items/lootaffixes/crafting/ad12_vitalityresist.dbr")) {
/* 1309 */           if (composer.preText != null) {
/* 1310 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_NECROPOLIS");
/*      */           } else {
/* 1312 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_NECROPOLIS");
/*      */           } 
/*      */         }
/*      */         
/* 1316 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ad102_stunresist.dbr") || this.affixID
/* 1317 */           .equals("records/items/lootaffixes/crafting/ad103_freezeresist.dbr") || this.affixID
/* 1318 */           .equals("records/items/lootaffixes/crafting/ad101_blockdamage.dbr")) {
/* 1319 */           if (composer.preText != null) {
/* 1320 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_MALMOUTH_STEELCAP");
/*      */           } else {
/* 1322 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_MALMOUTH_STEELCAP");
/*      */           } 
/*      */         }
/*      */         
/* 1326 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ad104_poisonresist.dbr") || this.affixID
/* 1327 */           .equals("records/items/lootaffixes/crafting/ac04_energyregen.dbr") || this.affixID
/* 1328 */           .equals("records/items/lootaffixes/crafting/ao101_crit.dbr") || this.affixID
/* 1329 */           .equals("records/items/lootaffixes/crafting/ao102_energyregen.dbr")) {
/* 1330 */           if (composer.preText != null) {
/* 1331 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_MALMOUTH_OUTSKIRTS");
/*      */           } else {
/* 1333 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_MALMOUTH_OUTSKIRTS");
/*      */           } 
/*      */         }
/*      */         
/* 1337 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ac02_healthregen.dbr") || this.affixID
/* 1338 */           .equals("records/items/lootaffixes/crafting/ad08_da.dbr") || this.affixID
/* 1339 */           .equals("records/items/lootaffixes/crafting/ao14_oa.dbr")) {
/* 1340 */           if (composer.preText != null) {
/* 1341 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_TYRANTS_HOLD");
/*      */           } else {
/* 1343 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_TYRANTS_HOLD");
/*      */           } 
/*      */         }
/*      */         
/* 1347 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ac201_retal.dbr") || this.affixID
/* 1348 */           .equals("records/items/lootaffixes/crafting/ac01_health.dbr") || this.affixID
/* 1349 */           .equals("records/items/lootaffixes/crafting/ac203_healing.dbr")) {
/* 1350 */           if (composer.preText != null) {
/* 1351 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_WITCHGODS");
/*      */           } else {
/* 1353 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_WITCHGODS");
/*      */           } 
/*      */         }
/*      */         
/* 1357 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ad201_slowresist.dbr") || this.affixID
/* 1358 */           .equals("records/items/lootaffixes/crafting/ac204_runspeed.dbr") || this.affixID
/* 1359 */           .equals("records/items/lootaffixes/crafting/ad202_reflectresist.dbr")) {
/* 1360 */           if (composer.preText != null) {
/* 1361 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_DESERT");
/*      */           } else {
/* 1363 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_DESERT");
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1370 */     Object[] args = { String.format("%03d", new Object[] { Integer.valueOf(this.reqLevel) }) };
/* 1371 */     String msg = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_LEVEL_NUM", args);
/* 1372 */     if (composer.preText == null) {
/* 1373 */       composer.preText = "[" + msg;
/*      */     } else {
/* 1375 */       composer.preText += " [" + msg;
/*      */     } 
/*      */     
/* 1378 */     String s = composer.getAffixText();
/*      */     
/* 1380 */     if (this.rarity != null && (
/* 1381 */       this.type == 1 || this.type == 2)) {
/* 1382 */       if (this.rarity.equals("Magical")) {
/* 1383 */         s = "<html>" + GDColor.HTML_COLOR_MAGIC + s + "</font>" + "</html>";
/*      */       }
/*      */       
/* 1386 */       if (this.rarity.equals("Rare")) {
/* 1387 */         s = "<html>" + GDColor.HTML_COLOR_RARE + s + "</font>" + "</html>";
/*      */       }
/*      */       
/* 1390 */       if (this.rarity.equals("Epic")) {
/* 1391 */         s = "<html>" + GDColor.HTML_COLOR_EPIC + s + "</font>" + "</html>";
/*      */       }
/*      */       
/* 1394 */       if (this.rarity.equals("Legendary")) {
/* 1395 */         s = "<html>" + GDColor.HTML_COLOR_LEGENDARY + s + "</font>" + "</html>";
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1400 */     return s;
/*      */   }
/*      */   
/*      */   public void resetDescription() {
/* 1404 */     this.strDescription = null;
/*      */   }
/*      */   
/*      */   public String toString() {
/* 1408 */     if (this.strDescription == null) {
/* 1409 */       this.strDescription = getString(null);
/*      */     }
/*      */     
/* 1412 */     return this.strDescription;
/*      */   }
/*      */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBAffix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */