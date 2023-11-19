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
/*      */   public void setAffixType(int type) {
/*  463 */     this.type = type;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTotalAttCount() {
/*  471 */     int total = 0;
/*      */     
/*  473 */     for (DBStat stat : this.stats) {
/*  474 */       if (stat.getStatMin() > 0.0F) total++; 
/*  475 */       if (stat.getStatModifier() > 0) total++; 
/*  476 */       if (stat.getDurationModifier() > 0) total++; 
/*  477 */       if (stat.getMaxResist() > 0) total++;
/*      */     
/*      */     } 
/*  480 */     if (this.statBonusRaces != null && !this.statBonusRaces.isEmpty()) total += this.statBonusRaces.size();
/*      */     
/*  482 */     return total;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getRarityInt() {
/*  490 */     if (this.rarity == null) return -1;
/*      */     
/*  492 */     if (this.rarity.equals("Broken")) return 1; 
/*  493 */     if (this.rarity.equals("Magical")) return 2; 
/*  494 */     if (this.rarity.equals("Rare")) return 3; 
/*  495 */     if (this.rarity.equals("Epic")) return 4; 
/*  496 */     if (this.rarity.equals("Legendary")) return 5;
/*      */     
/*  498 */     return -1;
/*      */   }
/*      */   
/*      */   public boolean matchesCriteria(SelectionCriteria criteria) {
/*  502 */     if (criteria.isInitial()) return true;
/*      */     
/*  504 */     if (criteria.combiMode == SelectionCriteria.CombinationMode.OR) {
/*  505 */       return matchesCriteriaOr(criteria);
/*      */     }
/*      */     
/*  508 */     if (criteria.combiMode == SelectionCriteria.CombinationMode.AND) {
/*  509 */       return matchesCriteriaAnd(criteria);
/*      */     }
/*      */     
/*  512 */     return false;
/*      */   }
/*      */   
/*      */   private boolean matchesCriteriaOr(SelectionCriteria criteria) {
/*  516 */     if (criteria.isInitial()) return true;
/*      */     
/*  518 */     if (criteria.levelMin > 0 && 
/*  519 */       this.reqLevel < criteria.levelMin) return false;
/*      */ 
/*      */     
/*  522 */     if (criteria.levelMax > 0 && 
/*  523 */       this.reqLevel > criteria.levelMax) return false;
/*      */ 
/*      */     
/*  526 */     if (!criteria.itemRarity.isEmpty() && 
/*  527 */       !criteria.itemRarity.contains(this.rarity)) return false;
/*      */ 
/*      */     
/*  530 */     if (criteria.dmgConversionFrom != null && 
/*  531 */       !criteria.dmgConversionFrom.equals(this.convertIn) && 
/*  532 */       !criteria.dmgConversionFrom.equals(this.convertIn2)) return false;
/*      */ 
/*      */     
/*  535 */     if (criteria.dmgConversionTo != null && 
/*  536 */       !criteria.dmgConversionTo.equals(this.convertOut) && 
/*  537 */       !criteria.dmgConversionTo.equals(this.convertOut2)) return false;
/*      */ 
/*      */     
/*  540 */     if (criteria.petBonus && (
/*  541 */       this.dbPetAffix != null || this.dbPetSkill != null)) return true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  547 */     if (criteria.statInfos == null) return true; 
/*  548 */     if (criteria.statInfos.isEmpty()) return true;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  553 */     if (this.stats == null) return false;
/*      */     
/*  555 */     for (SelectionCriteria.StatInfo info : criteria.statInfos) {
/*  556 */       for (String statType : info.statTypes) {
/*  557 */         for (DBStat stat : this.stats) {
/*  558 */           if (stat.getStatType().equals(statType)) {
/*  559 */             if (info.flat == info.percentage) {
/*  560 */               if (info.maxResist) {
/*  561 */                 if (info.flat) {
/*  562 */                   if (stat.getStatMin() > 0.0F) return true; 
/*  563 */                   if (stat.getStatModifier() > 0) return true; 
/*      */                 } 
/*  565 */                 if (stat.getMaxResist() > 0) return true; 
/*      */               } else {
/*  567 */                 if (stat.getStatMin() > 0.0F) return true; 
/*  568 */                 if (stat.getStatModifier() > 0) return true; 
/*      */               } 
/*      */             } else {
/*  571 */               if (info.flat && 
/*  572 */                 stat.getStatMin() > 0.0F) return true;
/*      */ 
/*      */               
/*  575 */               if (info.percentage && 
/*  576 */                 stat.getStatModifier() > 0) return true;
/*      */             
/*      */             } 
/*      */             
/*  580 */             if (info.maxResist && 
/*  581 */               stat.getMaxResist() > 0) return true;
/*      */           
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  588 */     return false;
/*      */   }
/*      */   
/*      */   private boolean matchesCriteriaAnd(SelectionCriteria criteria) {
/*  592 */     if (criteria.isInitial()) return true;
/*      */     
/*  594 */     if (criteria.levelMin > 0 && 
/*  595 */       this.reqLevel < criteria.levelMin) return false;
/*      */ 
/*      */     
/*  598 */     if (criteria.levelMax > 0 && 
/*  599 */       this.reqLevel > criteria.levelMax) return false;
/*      */ 
/*      */     
/*  602 */     if (!criteria.itemRarity.isEmpty() && 
/*  603 */       !criteria.itemRarity.contains(this.rarity)) return false;
/*      */ 
/*      */     
/*  606 */     if (criteria.dmgConversionFrom != null && 
/*  607 */       !criteria.dmgConversionFrom.equals(this.convertIn) && 
/*  608 */       !criteria.dmgConversionFrom.equals(this.convertIn2)) return false;
/*      */ 
/*      */     
/*  611 */     if (criteria.dmgConversionTo != null && 
/*  612 */       !criteria.dmgConversionTo.equals(this.convertOut) && 
/*  613 */       !criteria.dmgConversionTo.equals(this.convertOut2)) return false;
/*      */ 
/*      */     
/*  616 */     if (criteria.petBonus && 
/*  617 */       this.dbPetAffix == null && this.dbPetSkill == null) return false;
/*      */ 
/*      */     
/*  620 */     boolean fitsAll = false;
/*  621 */     if (criteria.statInfos == null)
/*  622 */     { fitsAll = true; }
/*      */     
/*  624 */     else if (criteria.statInfos.isEmpty()) { fitsAll = true; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  630 */     if (criteria.statInfos == null) return fitsAll; 
/*  631 */     if (criteria.statInfos.isEmpty()) return fitsAll;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  636 */     if (this.stats == null) return false;
/*      */     
/*  638 */     fitsAll = true;
/*      */     
/*  640 */     for (SelectionCriteria.StatInfo info : criteria.statInfos) {
/*  641 */       boolean found = false;
/*      */       
/*  643 */       for (String statType : info.statTypes) {
/*  644 */         for (DBStat stat : this.stats) {
/*  645 */           if (stat.getStatType().equals(statType)) {
/*  646 */             if (info.flat == info.percentage) {
/*  647 */               if (info.maxResist) {
/*  648 */                 if (info.flat) {
/*  649 */                   if (stat.getStatMin() > 0.0F) {
/*  650 */                     found = true;
/*      */                     
/*      */                     break;
/*      */                   } 
/*  654 */                   if (stat.getStatModifier() > 0) {
/*  655 */                     found = true;
/*      */                     
/*      */                     break;
/*      */                   } 
/*      */                 } 
/*  660 */                 if (stat.getMaxResist() > 0) {
/*  661 */                   found = true;
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } else {
/*  666 */                 if (stat.getStatMin() > 0.0F) {
/*  667 */                   found = true;
/*      */                   
/*      */                   break;
/*      */                 } 
/*  671 */                 if (stat.getStatModifier() > 0) {
/*  672 */                   found = true;
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             } else {
/*  678 */               if (info.flat && 
/*  679 */                 stat.getStatMin() > 0.0F) {
/*  680 */                 found = true;
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */               
/*  686 */               if (info.percentage && 
/*  687 */                 stat.getStatModifier() > 0) {
/*  688 */                 found = true;
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */             
/*  695 */             if (info.maxResist && 
/*  696 */               stat.getMaxResist() > 0) {
/*  697 */               found = true;
/*      */ 
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  705 */         if (found)
/*      */           break; 
/*      */       } 
/*  708 */       if (!found) {
/*  709 */         fitsAll = false;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/*  715 */     return fitsAll;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setLootRandomName(String lootRandomName) {
/*  723 */     String text = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_ITEMS, lootRandomName, false);
/*      */     
/*  725 */     if (text != null) {
/*  726 */       String[] genders = ARZDecompress.getGenderTexts(text);
/*      */       
/*  728 */       setGenderNames(genders);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setGenderNames(String[] genders) {
/*  733 */     this.nameMS = genders[0];
/*  734 */     this.nameFS = genders[1];
/*  735 */     this.nameNS = genders[2];
/*  736 */     this.nameMP = genders[3];
/*  737 */     this.nameFP = genders[4];
/*  738 */     this.nameNP = genders[5];
/*      */   }
/*      */   
/*      */   private void determineAffixType(String filename) {
/*  742 */     this.type = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  750 */     if (filename.contains("prefix")) this.type = 1; 
/*  751 */     if (filename.contains("suffix")) this.type = 2; 
/*  752 */     if (filename.contains("crafting")) {
/*  753 */       this.type = 3;
/*      */     }
/*  755 */     if (filename.contains("completion")) {
/*  756 */       this.type = 4;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void clearBuffer() {
/*  765 */     hashBuffer.clear();
/*  766 */     bufferComplete = false;
/*      */     
/*  768 */     if (GDStashFrame.dbConfig != null)
/*      */     {
/*      */ 
/*      */       
/*  772 */       fillBuffer();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void createTables() throws SQLException {
/*  778 */     String dropTable = "DROP TABLE GD_AFFIX";
/*  779 */     String createTable = "CREATE TABLE GD_AFFIX (AFFIX_ID VARCHAR(256) NOT NULL, RARITY           VARCHAR(32), NAME_MS          VARCHAR(256), NAME_FS          VARCHAR(256), NAME_NS          VARCHAR(256), NAME_MP          VARCHAR(256), NAME_FP          VARCHAR(256), NAME_NP          VARCHAR(256), REQ_LEVEL        INTEGER, TYPE             INTEGER, CONVERT_IN       VARCHAR(16), CONVERT_OUT      VARCHAR(16), CONVERT_IN_2     VARCHAR(16), CONVERT_OUT_2    VARCHAR(16), OFFENSE_PRC      INTEGER, RETAL_PRC        INTEGER, LOOT_RAND_COST   INTEGER, PET_AFFIX_ID     VARCHAR(256), ITEM_SKILL_ID    VARCHAR(256), ITEM_SKILL_LEVEL INTEGER, CONTROLLER_ID    VARCHAR(256), RNG_PERCENT      INTEGER, PRIMARY KEY (AFFIX_ID))";
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
/*  805 */     try (Connection conn = GDDBData.getConnection()) {
/*  806 */       boolean auto = conn.getAutoCommit();
/*  807 */       conn.setAutoCommit(false);
/*      */       
/*  809 */       try (Statement st = conn.createStatement()) {
/*  810 */         if (GDDBUtil.tableExists(conn, "GD_AFFIX")) {
/*  811 */           st.execute(dropTable);
/*      */         }
/*  813 */         if (GDDBUtil.tableExists(conn, "GD_AFFIX_CHAR")) {
/*  814 */           st.execute("DROP TABLE GD_AFFIX_CHAR");
/*      */         }
/*  816 */         if (GDDBUtil.tableExists(conn, "GD_AFFIX_CHARRACES")) {
/*  817 */           st.execute("DROP TABLE GD_AFFIX_CHARRACES");
/*      */         }
/*  819 */         if (GDDBUtil.tableExists(conn, "GD_AFFIX_DAMAGE")) {
/*  820 */           st.execute("DROP TABLE GD_AFFIX_DAMAGE");
/*      */         }
/*  822 */         st.execute(createTable);
/*  823 */         st.close();
/*      */         
/*  825 */         conn.commit();
/*      */         
/*  827 */         DBStat.createAffixTable(conn);
/*  828 */         DBStatBonusRace.createAffixTable(conn);
/*  829 */         DBSkillBonus.createAffixTable(conn);
/*  830 */         DBSkillModifier.createAffixTable(conn);
/*      */       }
/*  832 */       catch (SQLException ex) {
/*  833 */         conn.rollback();
/*      */         
/*  835 */         Object[] args = { "GD_AFFIX" };
/*  836 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*      */         
/*  838 */         GDMsgLogger.addError(msg);
/*      */         
/*  840 */         throw ex;
/*      */       } finally {
/*      */         
/*  843 */         conn.setAutoCommit(auto);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void delete(String affixID) throws SQLException {
/*  849 */     String deleteEntry = "DELETE FROM GD_AFFIX WHERE AFFIX_ID = ?";
/*      */     
/*  851 */     try (Connection conn = GDDBData.getConnection()) {
/*  852 */       boolean auto = conn.getAutoCommit();
/*  853 */       conn.setAutoCommit(false);
/*      */       
/*  855 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/*  856 */         ps.setString(1, affixID);
/*  857 */         ps.executeUpdate();
/*  858 */         ps.close();
/*      */         
/*  860 */         DBStat.deleteAffix(conn, affixID);
/*  861 */         DBStatBonusRace.deleteAffix(conn, affixID);
/*  862 */         DBSkillBonus.deleteAffix(conn, affixID);
/*  863 */         DBSkillModifier.deleteAffix(conn, affixID);
/*      */         
/*  865 */         conn.commit();
/*      */       }
/*  867 */       catch (SQLException ex) {
/*  868 */         conn.rollback();
/*      */         
/*  870 */         Object[] args = { affixID, "GD_AFFIX" };
/*  871 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*      */         
/*  873 */         GDMsgLogger.addError(msg);
/*  874 */         GDMsgLogger.addError(ex);
/*      */         
/*  876 */         throw ex;
/*      */       } finally {
/*      */         
/*  879 */         conn.setAutoCommit(auto);
/*      */       }
/*      */     
/*  882 */     } catch (SQLException ex) {
/*  883 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void insert(ARZRecord record) throws SQLException {
/*  888 */     DBAffix entry = get(record.getFileName());
/*      */     
/*  890 */     if (entry != null)
/*      */       return; 
/*  892 */     DBAffix affix = new DBAffix(record);
/*      */     
/*  894 */     String insert = "INSERT INTO GD_AFFIX VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */     
/*  896 */     try (Connection conn = GDDBData.getConnection()) {
/*  897 */       boolean auto = conn.getAutoCommit();
/*  898 */       conn.setAutoCommit(false);
/*      */       
/*  900 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/*  901 */         ps.setString(1, affix.affixID);
/*  902 */         ps.setString(2, affix.rarity);
/*  903 */         ps.setString(3, affix.nameMS);
/*  904 */         ps.setString(4, affix.nameFS);
/*  905 */         ps.setString(5, affix.nameNS);
/*  906 */         ps.setString(6, affix.nameMP);
/*  907 */         ps.setString(7, affix.nameFP);
/*  908 */         ps.setString(8, affix.nameNP);
/*  909 */         ps.setInt(9, affix.reqLevel);
/*  910 */         ps.setInt(10, affix.type);
/*  911 */         ps.setString(11, affix.convertIn);
/*  912 */         ps.setString(12, affix.convertOut);
/*  913 */         ps.setString(13, affix.convertIn2);
/*  914 */         ps.setString(14, affix.convertOut2);
/*  915 */         ps.setInt(15, affix.offensiveChance);
/*  916 */         ps.setInt(16, affix.retaliationChance);
/*  917 */         ps.setInt(17, affix.lootRandomCost);
/*  918 */         ps.setString(18, affix.petAffixID);
/*  919 */         ps.setString(19, affix.itemSkillID);
/*  920 */         ps.setInt(20, affix.itemSkillLevel);
/*  921 */         ps.setString(21, affix.controllerID);
/*  922 */         ps.setInt(22, affix.jitterPercent);
/*      */         
/*  924 */         ps.executeUpdate();
/*  925 */         ps.close();
/*      */         
/*  927 */         DBStat.insertAffix(conn, affix.affixID, affix.stats);
/*  928 */         DBStatBonusRace.insertAffix(conn, affix.affixID, affix.statBonusRaces);
/*  929 */         DBSkillBonus.insertAffix(conn, affix.affixID, affix.bonuses);
/*  930 */         DBSkillModifier.insertAffix(conn, affix.affixID, affix.skillModifiers);
/*      */         
/*  932 */         conn.commit();
/*      */       }
/*  934 */       catch (SQLException ex) {
/*  935 */         conn.rollback();
/*      */         
/*  937 */         Object[] args = { record.getFileName(), "GD_AFFIX" };
/*  938 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*      */         
/*  940 */         GDMsgLogger.addLowError(msg);
/*  941 */         GDMsgLogger.addLowError(ex);
/*      */       } finally {
/*      */         
/*  944 */         conn.setAutoCommit(auto);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static DBAffix get(String affixID) {
/*  950 */     DBAffix affix = null;
/*      */     
/*  952 */     affix = hashBuffer.get(affixID);
/*      */     
/*  954 */     if (bufferComplete) return affix;
/*      */     
/*  956 */     if (affix == null)
/*      */     {
/*  958 */       affix = getDB(affixID);
/*      */     }
/*      */     
/*  961 */     return affix;
/*      */   }
/*      */   
/*      */   private static DBAffix getDB(String affixID) {
/*  965 */     DBAffix affix = null;
/*      */     
/*  967 */     String command = "SELECT * FROM GD_AFFIX WHERE AFFIX_ID = ?";
/*      */     
/*  969 */     try(Connection conn = GDDBData.getConnection(); 
/*  970 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*  971 */       ps.setString(1, affixID);
/*      */       
/*  973 */       try (ResultSet rs = ps.executeQuery()) {
/*  974 */         List<DBAffix> list = wrap(rs);
/*      */         
/*  976 */         if (list.isEmpty()) {
/*  977 */           affix = null;
/*      */         } else {
/*  979 */           affix = list.get(0);
/*      */         } 
/*      */         
/*  982 */         conn.commit();
/*      */       }
/*  984 */       catch (SQLException ex) {
/*  985 */         affix = null;
/*      */         
/*  987 */         throw ex;
/*      */       }
/*      */     
/*  990 */     } catch (SQLException ex) {
/*  991 */       Object[] args = { affixID, "GD_AFFIX" };
/*  992 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/*  994 */       GDMsgLogger.addError(msg);
/*  995 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/*  998 */     return affix;
/*      */   }
/*      */   
/*      */   public static List<DBAffix> getByAffixIDs(List<String> affixIDs) {
/* 1002 */     List<DBAffix> list = new LinkedList<>();
/*      */     
/* 1004 */     for (String affixID : affixIDs) {
/* 1005 */       DBAffix affix = get(affixID);
/* 1006 */       if (affix != null) list.add(affix);
/*      */     
/*      */     } 
/* 1009 */     return list;
/*      */   }
/*      */   
/*      */   public static List<DBAffix> getByAffixSetID(List<String> affixIDs) {
/* 1013 */     List<DBAffix> list = new LinkedList<>();
/*      */     
/* 1015 */     for (String affixID : affixIDs) {
/* 1016 */       DBAffix affix = get(affixID);
/* 1017 */       if (affix != null) list.add(affix);
/*      */     
/*      */     } 
/* 1020 */     return list;
/*      */   }
/*      */   
/*      */   private static List<DBAffix> getAll() {
/* 1024 */     List<DBAffix> list = new LinkedList<>();
/*      */     
/* 1026 */     String command = "SELECT * FROM GD_AFFIX";
/*      */     
/* 1028 */     try(Connection conn = GDDBData.getConnection(); 
/* 1029 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*      */       
/* 1031 */       try (ResultSet rs = ps.executeQuery()) {
/* 1032 */         list = wrap(rs);
/*      */         
/* 1034 */         conn.commit();
/*      */       }
/* 1036 */       catch (SQLException ex) {
/* 1037 */         throw ex;
/*      */       }
/*      */     
/* 1040 */     } catch (SQLException ex) {
/* 1041 */       Object[] args = { "<all>", "GD_AFFIX" };
/* 1042 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 1044 */       GDMsgLogger.addError(msg);
/* 1045 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 1048 */     return list;
/*      */   }
/*      */   
/*      */   private void createBonusRaceStats(DBStat stat) {
/* 1052 */     if (stat == null)
/* 1053 */       return;  if (this.statBonusRaces == null)
/* 1054 */       return;  if (this.statBonusRaces.isEmpty())
/*      */       return; 
/* 1056 */     this.stats.remove(stat);
/*      */     
/* 1058 */     List<DBStat> list = DBStat.createStatsFromRaceBonusList(stat, this.statBonusRaces);
/* 1059 */     this.stats.addAll(list);
/*      */   }
/*      */   
/*      */   private static List<DBAffix> wrap(ResultSet rs) throws SQLException {
/* 1063 */     List<DBAffix> list = new LinkedList<>();
/*      */     
/* 1065 */     while (rs.next()) {
/* 1066 */       DBAffix affix = new DBAffix();
/*      */       
/* 1068 */       affix.affixID = rs.getString(1);
/*      */       
/* 1070 */       DBAffix buff = hashBuffer.get(affix.affixID);
/* 1071 */       if (buff != null) {
/* 1072 */         list.add(buff);
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1077 */       affix.rarity = rs.getString(2);
/* 1078 */       affix.nameMS = rs.getString(3);
/* 1079 */       affix.nameFS = rs.getString(4);
/* 1080 */       affix.nameNS = rs.getString(5);
/* 1081 */       affix.nameMP = rs.getString(6);
/* 1082 */       affix.nameFP = rs.getString(7);
/* 1083 */       affix.nameNP = rs.getString(8);
/* 1084 */       affix.reqLevel = rs.getInt(9);
/* 1085 */       affix.type = rs.getInt(10);
/* 1086 */       affix.convertIn = rs.getString(11);
/* 1087 */       affix.convertOut = rs.getString(12);
/* 1088 */       affix.convertIn2 = rs.getString(13);
/* 1089 */       affix.convertOut2 = rs.getString(14);
/* 1090 */       affix.offensiveChance = rs.getInt(15);
/* 1091 */       affix.retaliationChance = rs.getInt(16);
/* 1092 */       affix.lootRandomCost = rs.getInt(17);
/* 1093 */       affix.petAffixID = rs.getString(18);
/* 1094 */       affix.itemSkillID = rs.getString(19);
/* 1095 */       affix.itemSkillLevel = rs.getInt(20);
/* 1096 */       affix.controllerID = rs.getString(21);
/* 1097 */       affix.jitterPercent = rs.getInt(22);
/*      */       
/* 1099 */       affix.stats = DBStat.getAffix(affix.affixID);
/* 1100 */       Collections.sort(affix.stats);
/*      */       
/* 1102 */       DBStat stat = DBStat.getByType(affix.stats, "racialBonusPercentDamage", 1);
/* 1103 */       if (stat != null) {
/* 1104 */         affix.statBonusRaces = DBStatBonusRace.getAffix(affix.affixID);
/* 1105 */         affix.createBonusRaceStats(stat);
/*      */       } 
/*      */       
/* 1108 */       affix.bonuses = DBSkillBonus.getAffix(affix.affixID);
/* 1109 */       affix.skillModifiers = DBSkillModifier.getAffix(affix.affixID);
/*      */       
/* 1111 */       if (affix.petAffixID != null) {
/*      */         
/* 1113 */         affix.dbPetAffix = get(affix.petAffixID);
/* 1114 */         affix.dbPetSkill = DBSkill.get(affix.petAffixID);
/*      */       } 
/* 1116 */       if (affix.itemSkillID != null) affix.dbItemSkill = DBSkill.get(affix.itemSkillID);
/*      */       
/* 1118 */       if (affix.controllerID != null) {
/* 1119 */         affix.dbController = DBController.get(affix.controllerID);
/*      */       }
/*      */       
/* 1122 */       list.add(affix);
/* 1123 */       hashBuffer.put(affix.affixID, affix);
/*      */     } 
/*      */     
/* 1126 */     return list;
/*      */   }
/*      */   
/*      */   public static void fillBuffer() {
/* 1130 */     if (bufferComplete)
/*      */       return; 
/* 1132 */     if (!GDStashFrame.dbConfig.configInit || !GDStashFrame.dbConfig.gddbInit) {
/*      */       
/* 1134 */       listBuffer = new LinkedList<>();
/* 1135 */       listPrefix = new LinkedList<>();
/* 1136 */       listSuffix = new LinkedList<>();
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1141 */     listBuffer = getAll();
/* 1142 */     listPrefix = new LinkedList<>();
/* 1143 */     listSuffix = new LinkedList<>();
/*      */     
/* 1145 */     for (DBAffix affix : listBuffer) {
/* 1146 */       if (affix.getAffixType() == 1) {
/* 1147 */         listPrefix.add(affix);
/*      */       }
/* 1149 */       if (affix.getAffixType() == 2) {
/* 1150 */         listSuffix.add(affix);
/*      */       }
/*      */     } 
/*      */     
/* 1154 */     Collections.sort(listPrefix, new AffixComparator());
/* 1155 */     Collections.sort(listSuffix, new AffixComparator());
/*      */     
/* 1157 */     bufferComplete = true;
/*      */   }
/*      */   
/*      */   public static List<DBAffix> getFullAffixList() {
/* 1161 */     fillBuffer();
/*      */     
/* 1163 */     return listBuffer;
/*      */   }
/*      */   
/*      */   public static List<DBAffix> getPrefixList() {
/* 1167 */     fillBuffer();
/*      */     
/* 1169 */     return listPrefix;
/*      */   }
/*      */   
/*      */   public static List<DBAffix> getSuffixList() {
/* 1173 */     fillBuffer();
/*      */     
/* 1175 */     return listSuffix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DetailComposer getBonusComposer(String prefix) {
/* 1183 */     DetailComposer composer = new DetailComposer();
/*      */     
/* 1185 */     if (this.stats != null && !this.stats.isEmpty()) {
/* 1186 */       for (DBStat stat : this.stats) {
/* 1187 */         BonusDetail bonus = stat.getBonusDetail(null, 1, prefix);
/* 1188 */         composer.add(bonus);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1193 */     if (this.bonuses != null && 
/* 1194 */       !this.bonuses.isEmpty()) {
/* 1195 */       for (DBSkillBonus skillBonus : this.bonuses) {
/* 1196 */         BonusDetail bonus = skillBonus.getBonusDetail();
/* 1197 */         composer.add(bonus);
/*      */       } 
/*      */     }
/*      */     
/* 1201 */     if (this.dbPetAffix != null) {
/* 1202 */       String pref = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_PREFIX_PET");
/*      */       
/* 1204 */       DetailComposer comp = this.dbPetAffix.getBonusComposer(pref);
/* 1205 */       composer.add(comp);
/*      */     } 
/*      */     
/* 1208 */     if (this.dbPetSkill != null) {
/* 1209 */       String pref = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_PREFIX_PET");
/*      */       
/* 1211 */       List<DBStat> stats = DBStat.getByLevel(this.dbPetSkill.getStatList(), 1);
/*      */       
/* 1213 */       if (stats != null && !stats.isEmpty()) {
/* 1214 */         for (DBStat stat : stats) {
/* 1215 */           BonusDetail bonus = stat.getBonusDetail(null, 1, pref);
/* 1216 */           composer.add(bonus);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1221 */     if (this.dbItemSkill != null) {
/* 1222 */       BonusDetail bonus = this.dbItemSkill.getBonusDetail();
/* 1223 */       composer.add(bonus);
/*      */     } 
/*      */     
/* 1226 */     if (this.skillModifiers != null) {
/* 1227 */       for (DBSkillModifier sm : this.skillModifiers) {
/* 1228 */         BonusDetail bonus = sm.getBonusDetail(null, 1, prefix);
/* 1229 */         composer.add(bonus);
/*      */       } 
/*      */     }
/*      */     
/* 1233 */     return composer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getString(String prefix) {
/* 1241 */     DetailComposer composer = getBonusComposer(null);
/*      */     
/* 1243 */     if (this.nameMS != null) {
/* 1244 */       composer.preText = this.nameMS;
/*      */     } else {
/* 1246 */       boolean found = false;
/* 1247 */       String search = null;
/*      */ 
/*      */       
/* 1250 */       if (!found) {
/* 1251 */         search = "records/items/lootaffixes/crafting/";
/* 1252 */         found = this.affixID.startsWith(search);
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
/* 1268 */       composer.preText = null;
/*      */       
/* 1270 */       if (found) {
/*      */         
/* 1272 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ad05_pierceresist.dbr") || this.affixID
/* 1273 */           .equals("records/items/lootaffixes/crafting/ad06_protection.dbr") || this.affixID
/* 1274 */           .equals("records/items/lootaffixes/crafting/ac05_physique.dbr") || this.affixID
/* 1275 */           .equals("records/items/lootaffixes/crafting/ao01_physicaldmg.dbr")) {
/* 1276 */           if (composer.preText != null) {
/* 1277 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_ANGRIM");
/*      */           } else {
/* 1279 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_ANGRIM");
/*      */           } 
/*      */         }
/*      */         
/* 1283 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ac04_energyregen.dbr") || this.affixID
/* 1284 */           .equals("records/items/lootaffixes/crafting/ad08_da.dbr") || this.affixID
/* 1285 */           .equals("records/items/lootaffixes/crafting/ac05_physique.dbr") || this.affixID
/* 1286 */           .equals("records/items/lootaffixes/crafting/ad08_elementalresist.dbr") || this.affixID
/* 1287 */           .equals("records/items/lootaffixes/crafting/ao05_elementaldmg.dbr")) {
/* 1288 */           if (composer.preText != null) {
/* 1289 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_DUNCAN");
/*      */           } else {
/* 1291 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_DUNCAN");
/*      */           } 
/*      */         }
/*      */         
/* 1295 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ad09_aetherresist.dbr") || this.affixID
/* 1296 */           .equals("records/items/lootaffixes/crafting/ad10_chaosresist.dbr") || this.affixID
/* 1297 */           .equals("records/items/lootaffixes/crafting/ac01_health.dbr")) {
/* 1298 */           if (composer.preText != null) {
/* 1299 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_BLACK_LEGION");
/*      */           } else {
/* 1301 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_BLACK_LEGION");
/*      */           } 
/*      */         }
/*      */         
/* 1305 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ad01_fireresist.dbr") || this.affixID
/* 1306 */           .equals("records/items/lootaffixes/crafting/ao11_firedmg.dbr") || this.affixID
/* 1307 */           .equals("records/items/lootaffixes/crafting/ao13_lightningdmg.dbr")) {
/* 1308 */           if (composer.preText != null) {
/* 1309 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_KYMONS_CHOSEN");
/*      */           } else {
/* 1311 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_KYMONS_CHOSEN");
/*      */           } 
/*      */         }
/*      */         
/* 1315 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ad02_coldresist.dbr") || this.affixID
/* 1316 */           .equals("records/items/lootaffixes/crafting/ao12_colddmg.dbr") || this.affixID
/* 1317 */           .equals("records/items/lootaffixes/crafting/ao07_vitalitydmg.dbr")) {
/* 1318 */           if (composer.preText != null) {
/* 1319 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_DEATHS_VIGIL");
/*      */           } else {
/* 1321 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_DEATHS_VIGIL");
/*      */           } 
/*      */         }
/*      */         
/* 1325 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ac04_energyregen.dbr") || this.affixID
/* 1326 */           .equals("records/items/lootaffixes/crafting/ad07_bleedresist.dbr") || this.affixID
/* 1327 */           .equals("records/items/lootaffixes/crafting/ad12_vitalityresist.dbr")) {
/* 1328 */           if (composer.preText != null) {
/* 1329 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_NECROPOLIS");
/*      */           } else {
/* 1331 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_NECROPOLIS");
/*      */           } 
/*      */         }
/*      */         
/* 1335 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ad102_stunresist.dbr") || this.affixID
/* 1336 */           .equals("records/items/lootaffixes/crafting/ad103_freezeresist.dbr") || this.affixID
/* 1337 */           .equals("records/items/lootaffixes/crafting/ad101_blockdamage.dbr")) {
/* 1338 */           if (composer.preText != null) {
/* 1339 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_MALMOUTH_STEELCAP");
/*      */           } else {
/* 1341 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_MALMOUTH_STEELCAP");
/*      */           } 
/*      */         }
/*      */         
/* 1345 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ad104_poisonresist.dbr") || this.affixID
/* 1346 */           .equals("records/items/lootaffixes/crafting/ac04_energyregen.dbr") || this.affixID
/* 1347 */           .equals("records/items/lootaffixes/crafting/ao101_crit.dbr") || this.affixID
/* 1348 */           .equals("records/items/lootaffixes/crafting/ao102_energyregen.dbr")) {
/* 1349 */           if (composer.preText != null) {
/* 1350 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_MALMOUTH_OUTSKIRTS");
/*      */           } else {
/* 1352 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_MALMOUTH_OUTSKIRTS");
/*      */           } 
/*      */         }
/*      */         
/* 1356 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ac02_healthregen.dbr") || this.affixID
/* 1357 */           .equals("records/items/lootaffixes/crafting/ad08_da.dbr") || this.affixID
/* 1358 */           .equals("records/items/lootaffixes/crafting/ao14_oa.dbr")) {
/* 1359 */           if (composer.preText != null) {
/* 1360 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_TYRANTS_HOLD");
/*      */           } else {
/* 1362 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_TYRANTS_HOLD");
/*      */           } 
/*      */         }
/*      */         
/* 1366 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ac201_retal.dbr") || this.affixID
/* 1367 */           .equals("records/items/lootaffixes/crafting/ac01_health.dbr") || this.affixID
/* 1368 */           .equals("records/items/lootaffixes/crafting/ac203_healing.dbr")) {
/* 1369 */           if (composer.preText != null) {
/* 1370 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_WITCHGODS");
/*      */           } else {
/* 1372 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_WITCHGODS");
/*      */           } 
/*      */         }
/*      */         
/* 1376 */         if (this.affixID.equals("records/items/lootaffixes/crafting/ad201_slowresist.dbr") || this.affixID
/* 1377 */           .equals("records/items/lootaffixes/crafting/ac204_runspeed.dbr") || this.affixID
/* 1378 */           .equals("records/items/lootaffixes/crafting/ad202_reflectresist.dbr")) {
/* 1379 */           if (composer.preText != null) {
/* 1380 */             composer.preText += ", " + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_DESERT");
/*      */           } else {
/* 1382 */             composer.preText = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SMITH_DESERT");
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1389 */     Object[] args = { String.format("%03d", new Object[] { Integer.valueOf(this.reqLevel) }) };
/* 1390 */     String msg = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_LEVEL_NUM", args);
/* 1391 */     if (composer.preText == null) {
/* 1392 */       composer.preText = "[" + msg;
/*      */     } else {
/* 1394 */       composer.preText += " [" + msg;
/*      */     } 
/*      */     
/* 1397 */     String s = composer.getAffixText();
/*      */     
/* 1399 */     if (this.rarity != null && (
/* 1400 */       this.type == 1 || this.type == 2)) {
/* 1401 */       if (this.rarity.equals("Magical")) {
/* 1402 */         s = "<html>" + GDColor.HTML_COLOR_MAGIC + s + "</font>" + "</html>";
/*      */       }
/*      */       
/* 1405 */       if (this.rarity.equals("Rare")) {
/* 1406 */         s = "<html>" + GDColor.HTML_COLOR_RARE + s + "</font>" + "</html>";
/*      */       }
/*      */       
/* 1409 */       if (this.rarity.equals("Epic")) {
/* 1410 */         s = "<html>" + GDColor.HTML_COLOR_EPIC + s + "</font>" + "</html>";
/*      */       }
/*      */       
/* 1413 */       if (this.rarity.equals("Legendary")) {
/* 1414 */         s = "<html>" + GDColor.HTML_COLOR_LEGENDARY + s + "</font>" + "</html>";
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1419 */     return s;
/*      */   }
/*      */   
/*      */   public void resetDescription() {
/* 1423 */     this.strDescription = null;
/*      */   }
/*      */   
/*      */   public String toString() {
/* 1427 */     if (this.strDescription == null) {
/* 1428 */       this.strDescription = getString(null);
/*      */     }
/*      */     
/* 1431 */     return this.strDescription;
/*      */   }
/*      */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBAffix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */