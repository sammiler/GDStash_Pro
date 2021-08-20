/*      */ package org.gdstash.db;

import org.gdstash.description.BonusDetail;
import org.gdstash.description.DetailComposer;
import org.gdstash.file.ARZRecord;
import org.gdstash.file.DDSLoader;
import org.gdstash.file.GDParseException;
import org.gdstash.util.GDMsgFormatter;
import org.gdstash.util.GDMsgLogger;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DBSkill { public static final String TABLE_NAME = "GD_SKILL"; public static final String FIELD_ID = "SKILL_ID"; public static final String TEMPLATE_MASTERY = "database/templates/skill_mastery.tpl"; private static final int ROW_SKILL_ID = 1;
/*      */   private static final int ROW_REC_CLASS = 2;
/*      */   private static final int ROW_NAME = 3;
/*      */   private static final int ROW_DESCRIPTION = 4;
/*      */   private static final int ROW_MASTERY = 5;
/*      */   private static final int ROW_DEVOTION = 6;
/*      */   private static final int ROW_MASTERY_ID = 7;
/*      */   private static final int ROW_SKILL_TIER = 8;
/*      */   private static final int ROW_MAX_LEVEL = 9;
/*      */   private static final int ROW_BITMAP_DOWN_ID = 10;
/*      */   private static final int ROW_BITMAP_DOWN = 11;
/*      */   private static final int ROW_BITMAP_UP_ID = 12;
/*      */   private static final int ROW_BITMAP_UP = 13;
/*      */   private static final int ROW_SKILL_LEVEL = 14;
/*      */   private static final int ROW_SKILL_DURATION = 15;
/*      */   private static final int ROW_BONUS_INC = 16;
/*      */   private static final int ROW_MODIFIED = 17;
/*      */   private static final int ROW_BUFF_SKILL_ID = 18;
/*      */   private static final int ROW_PET_SKILL_ID = 19;
/*      */   private static final int ROW_CONVERT_IN = 20;
/*      */   private static final int ROW_CONVERT_OUT = 21;
/*      */   private static final int ROW_CONVERT_IN_2 = 22;
/*      */   private static final int ROW_CONVERT_OUT_2 = 23;
/*      */   private static final int ROW_DEPENDENCY_ALL = 24;
/*      */   private static final String CLASS_MODIFIER = "Skill_Modifier";
/*      */   private static final String CLASS_MODIFIER_PROJECTILE = "Skill_ProjectileModifier";
/*      */   private static final String CLASS_MODIFIER_COOLDOWN = "Skill_RefreshCooldownModifier";
/*      */   private static final String CLASS_MODIFIER_PET = "SkillSecondary_PetModifier";
/*      */   private static final String CLASS_TRANSMUTER = "Skill_Transmuter";
/*      */   private static final String CLASS_TRANSMUTER_PROJECTILE = "Skill_ProjectileTransmuter";
/*      */   private static final String CLASS_TRANSMUTER_PET = "Skill_SpawnPetTransmuter";
/*      */   
/*   33 */   public enum SkillDetail { ALL, SKILL, PET; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class ImageInfo
/*      */   {
/*      */     public String skillID;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String bitmapDownID;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String bitmapUpID;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte[] bitmapDown;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte[] bitmapUp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   83 */   private static ConcurrentHashMap<String, DBSkill> hashBuffer = new ConcurrentHashMap<>();
/*      */   
/*      */   private String skillID;
/*      */   
/*      */   private String recClass;
/*      */   
/*      */   private String name;
/*      */   private String description;
/*      */   private boolean mastery;
/*      */   private boolean devotion;
/*      */   private String masteryID;
/*      */   private int skillTier;
/*      */   private int maxLevel;
/*      */   private String bitmapDownID;
/*      */   private byte[] bitmapDown;
/*      */   private String bitmapUpID;
/*      */   private byte[] bitmapUp;
/*      */   private int level;
/*      */   private int duration;
/*      */   private boolean bonusIncrement;
/*      */   private boolean modified;
/*      */   private String buffSkillID;
/*      */   private String petSkillID;
/*      */   private String convertIn;
/*      */   private String convertOut;
/*      */   private String convertIn2;
/*      */   private String convertOut2;
/*      */   private boolean dependencyAll;
/*      */   private List<Integer> xpLevels;
/*      */   private List<DBStat> stats;
/*      */   private List<DBStatBonusRace> statBonusRaces;
/*      */   private List<DBSkillSpawn> spawnPets;
/*      */   private List<DBSkillDependency> dependencies;
/*      */   private List<DBSkillConnector> connections;
/*      */   private DBSkill dbBuffSkill;
/*      */   private DBSkill dbPetSkill;
/*      */   private int numXPLevels;
/*      */   private DBSkill dbMastery;
/*      */   private BufferedImage imageDown;
/*      */   private BufferedImage imageUp;
/*      */   
/*      */   public DBSkill() {
/*  125 */     this.skillID = null;
/*  126 */     this.recClass = null;
/*  127 */     this.name = null;
/*  128 */     this.description = null;
/*  129 */     this.mastery = false;
/*  130 */     this.devotion = false;
/*  131 */     this.masteryID = null;
/*  132 */     this.skillTier = 0;
/*  133 */     this.maxLevel = 0;
/*  134 */     this.bitmapDownID = null;
/*  135 */     this.bitmapDown = null;
/*  136 */     this.bitmapUpID = null;
/*  137 */     this.bitmapUp = null;
/*  138 */     this.level = 0;
/*  139 */     this.duration = 0;
/*  140 */     this.bonusIncrement = false;
/*  141 */     this.modified = false;
/*  142 */     this.buffSkillID = null;
/*  143 */     this.petSkillID = null;
/*  144 */     this.convertIn = null;
/*  145 */     this.convertOut = null;
/*  146 */     this.convertIn2 = null;
/*  147 */     this.convertOut2 = null;
/*  148 */     this.dependencyAll = false;
/*  149 */     this.numXPLevels = 0;
/*  150 */     this.xpLevels = null;
/*  151 */     this.stats = new LinkedList<>();
/*  152 */     this.statBonusRaces = new LinkedList<>();
/*  153 */     this.spawnPets = new LinkedList<>();
/*  154 */     this.dependencies = new LinkedList<>();
/*  155 */     this.connections = new LinkedList<>();
/*      */   }
/*      */   
/*      */   public DBSkill(String name) {
/*  159 */     this();
/*      */     
/*  161 */     this.name = name;
/*      */   }
/*      */   
/*      */   public DBSkill(String name, String skillID) {
/*  165 */     this();
/*      */     
/*  167 */     this.name = name;
/*  168 */     this.skillID = skillID;
/*      */   }
/*      */   
/*      */   private DBSkill(ARZRecord record) {
/*  172 */     this.skillID = record.getFileName();
/*      */     
/*  174 */     this.name = record.getSkillName();
/*  175 */     this.recClass = record.getRecordClass();
/*  176 */     this.description = record.getSkillDescription();
/*  177 */     this.mastery = record.isMastery();
/*  178 */     this.devotion = record.isDevotion();
/*  179 */     this.masteryID = null;
/*  180 */     this.skillTier = record.getSkillTier();
/*  181 */     this.maxLevel = record.getSkillMaxLevel();
/*  182 */     this.bitmapDownID = record.getSkillBitmapDownID();
/*  183 */     this.bitmapUpID = record.getSkillBitmapUpID();
/*  184 */     this.level = record.getSkillLevel();
/*  185 */     this.duration = record.getSkillDuration();
/*  186 */     this.bonusIncrement = record.hasSkillBonusIncrement();
/*  187 */     this.modified = record.hasSkillModifier();
/*  188 */     this.buffSkillID = record.getSkillBuffID();
/*  189 */     this.petSkillID = record.getPetBonusID();
/*  190 */     this.convertIn = record.getConversionIn();
/*  191 */     this.convertOut = record.getConversionOut();
/*  192 */     this.convertIn2 = record.getConversionIn2();
/*  193 */     this.convertOut2 = record.getConversionOut2();
/*  194 */     this.dependencyAll = record.isDependencyAll();
/*  195 */     this.xpLevels = record.getSkillXPLevelList();
/*  196 */     this.numXPLevels = this.xpLevels.size();
/*  197 */     this.stats = record.getDBStatList();
/*  198 */     this.statBonusRaces = record.getDBStatBonusRaceList();
/*  199 */     this.spawnPets = record.getSpawnPetList();
/*  200 */     this.dependencies = record.getSkillDependencyList();
/*  201 */     this.connections = record.getSkillConnectorList();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSkillID() {
/*  209 */     return this.skillID;
/*      */   }
/*      */   
/*      */   public String getRecordClass() {
/*  213 */     return this.recClass;
/*      */   }
/*      */   
/*      */   public boolean isBaseSkill() {
/*  217 */     if (this.recClass.equals("Skill_Transmuter")) return false; 
/*  218 */     if (this.recClass.equals("Skill_ProjectileTransmuter")) return false; 
/*  219 */     if (this.recClass.equals("Skill_SpawnPetTransmuter")) return false; 
/*  220 */     if (this.recClass.equals("Skill_Modifier")) return false; 
/*  221 */     if (this.recClass.equals("Skill_ProjectileModifier")) return false; 
/*  222 */     if (this.recClass.equals("Skill_RefreshCooldownModifier")) return false; 
/*  223 */     if (this.recClass.equals("SkillSecondary_PetModifier")) return false; 
/*  224 */     if (this.recClass.startsWith("SkillSecondary_")) return false;
/*      */     
/*  226 */     return true;
/*      */   }
/*      */   
/*      */   public String getName() {
/*  230 */     if (this.name != null) return this.name;
/*      */     
/*  232 */     if (this.dbBuffSkill != null && 
/*  233 */       this.dbBuffSkill.name != null) return this.dbBuffSkill.name;
/*      */ 
/*      */     
/*  236 */     if (this.dbPetSkill != null && 
/*  237 */       this.dbPetSkill.name != null) return this.dbPetSkill.name;
/*      */ 
/*      */     
/*  240 */     return null;
/*      */   }
/*      */   
/*      */   public String getMasteryName() {
/*  244 */     if (isMastery()) {
/*  245 */       return getName();
/*      */     }
/*  247 */     if (this.dbMastery != null) return this.dbMastery.getName();
/*      */ 
/*      */     
/*  250 */     return null;
/*      */   }
/*      */   
/*      */   public String getMasteryID() {
/*  254 */     return this.masteryID;
/*      */   }
/*      */   
/*      */   public String getDescription() {
/*  258 */     if (this.description != null) return this.description;
/*      */     
/*  260 */     if (this.dbPetSkill != null) return this.dbPetSkill.getDescription(); 
/*  261 */     if (this.dbBuffSkill != null) return this.dbBuffSkill.getDescription();
/*      */     
/*  263 */     return null;
/*      */   }
/*      */   
/*      */   public boolean isMastery() {
/*  267 */     return this.mastery;
/*      */   }
/*      */   
/*      */   public boolean isDevotion() {
/*  271 */     return this.devotion;
/*      */   }
/*      */   
/*      */   public boolean isDependencyAll() {
/*  275 */     return this.dependencyAll;
/*      */   }
/*      */   
/*      */   public boolean hasBonusIncrement() {
/*  279 */     return this.bonusIncrement;
/*      */   }
/*      */   
/*      */   public boolean hasSkillModifier() {
/*  283 */     return this.modified;
/*      */   }
/*      */   
/*      */   public int getSkillTier() {
/*  287 */     if (this.dbPetSkill != null) {
/*  288 */       int tier = this.dbPetSkill.getSkillTier();
/*      */       
/*  290 */       if (tier > 0) return tier; 
/*      */     } 
/*  292 */     if (this.dbBuffSkill != null) {
/*  293 */       int tier = this.dbBuffSkill.getSkillTier();
/*      */       
/*  295 */       if (tier > 0) return tier;
/*      */     
/*      */     } 
/*  298 */     return this.skillTier;
/*      */   }
/*      */   
/*      */   public int getMaxLevel() {
/*  302 */     if (this.dbPetSkill != null) {
/*  303 */       int level = this.dbPetSkill.getMaxLevel();
/*      */       
/*  305 */       if (level > 0) return level; 
/*      */     } 
/*  307 */     if (this.dbBuffSkill != null) {
/*  308 */       int level = this.dbBuffSkill.getMaxLevel();
/*      */       
/*  310 */       if (level > 0) return level;
/*      */     
/*      */     } 
/*  313 */     if (this.numXPLevels > 0)
/*      */     {
/*  315 */       return this.numXPLevels;
/*      */     }
/*      */     
/*  318 */     return this.maxLevel;
/*      */   }
/*      */ 
/*      */   
/*      */   public BufferedImage getImageDown() {
/*  323 */     if (this.imageDown != null) return this.imageDown;
/*      */     
/*  325 */     if (this.dbPetSkill != null) return this.dbPetSkill.getImageDown(); 
/*  326 */     if (this.dbBuffSkill != null) return this.dbBuffSkill.getImageDown();
/*      */     
/*  328 */     return null;
/*      */   }
/*      */   
/*      */   public List<DBSkillDependency> getSkillDependencyList() {
/*  332 */     if (this.dbBuffSkill != null) return this.dbBuffSkill.getSkillDependencyList(); 
/*  333 */     if (this.dbPetSkill != null) return this.dbPetSkill.getSkillDependencyList();
/*      */     
/*  335 */     return this.dependencies;
/*      */   }
/*      */   
/*      */   public List<DBSkillConnector> getSkillConnectionList() {
/*  339 */     if (!this.connections.isEmpty()) return this.connections;
/*      */     
/*  341 */     if (this.dbBuffSkill != null) return this.dbBuffSkill.getSkillConnectionList(); 
/*  342 */     if (this.dbPetSkill != null) return this.dbPetSkill.getSkillConnectionList();
/*      */     
/*  344 */     return null;
/*      */   }
/*      */   
/*      */   public BufferedImage getImageUp() {
/*  348 */     if (this.imageUp != null) return this.imageUp;
/*      */     
/*  350 */     if (this.dbPetSkill != null) return this.dbPetSkill.getImageUp(); 
/*  351 */     if (this.dbBuffSkill != null) return this.dbBuffSkill.getImageUp();
/*      */     
/*  353 */     return null;
/*      */   }
/*      */   
/*      */   public int getSkillLevel() {
/*  357 */     if (this.dbBuffSkill != null) return this.dbBuffSkill.level;
/*      */     
/*  359 */     return this.level;
/*      */   }
/*      */   
/*      */   public int getSkillDuration() {
/*  363 */     if (this.dbBuffSkill != null) return this.dbBuffSkill.duration;
/*      */     
/*  365 */     return this.duration;
/*      */   }
/*      */   
/*      */   public int getXPForLevel(int level) {
/*  369 */     List<Integer> list = null;
/*      */     
/*  371 */     if (this.dbBuffSkill != null) {
/*  372 */       list = this.dbBuffSkill.xpLevels;
/*      */     } else {
/*  374 */       list = this.xpLevels;
/*      */     } 
/*      */     
/*  377 */     int lvl = 1;
/*  378 */     int xp = 0;
/*      */     
/*  380 */     for (Iterator<Integer> iterator = list.iterator(); iterator.hasNext(); ) { int i = ((Integer)iterator.next()).intValue();
/*  381 */       if (lvl == level) {
/*  382 */         xp = i;
/*      */         
/*      */         break;
/*      */       } 
/*      */       
/*  387 */       lvl++; }
/*      */ 
/*      */     
/*  390 */     return xp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DBPet getPet(int index) {
/*  397 */     DBPet pet = null;
/*      */     
/*  399 */     int i = 0;
/*  400 */     for (DBSkillSpawn spawn : this.spawnPets) {
/*  401 */       pet = spawn.getPet();
/*      */       
/*  403 */       i++;
/*      */       
/*  405 */       if (i == index)
/*      */         break; 
/*      */     } 
/*  408 */     return pet;
/*      */   }
/*      */   
/*      */   public int getManaCost(int index) {
/*  412 */     if (this.dbBuffSkill != null) return this.dbBuffSkill.getManaCost(index); 
/*  413 */     if (this.dbPetSkill != null) return this.dbPetSkill.getManaCost(index);
/*      */     
/*  415 */     DBStat stat = DBStat.getDBStat(this.stats, "skillManaCost", index);
/*      */     
/*  417 */     if (stat == null) return 0;
/*      */     
/*  419 */     return (int)stat.getStatMin();
/*      */   }
/*      */   
/*      */   public float getCooldownTime(int index) {
/*  423 */     if (this.dbBuffSkill != null) return this.dbBuffSkill.getCooldownTime(index); 
/*  424 */     if (this.dbPetSkill != null) return this.dbPetSkill.getCooldownTime(index);
/*      */     
/*  426 */     DBStat stat = DBStat.getDBStat(this.stats, "skillCooldownTime", index);
/*      */     
/*  428 */     if (stat == null) return 0.0F;
/*      */     
/*  430 */     return stat.getStatMin();
/*      */   }
/*      */   
/*      */   public int getActiveDuration(int index) {
/*  434 */     if (this.dbBuffSkill != null) return this.dbBuffSkill.getActiveDuration(index); 
/*  435 */     if (this.dbPetSkill != null) return this.dbPetSkill.getActiveDuration(index);
/*      */     
/*  437 */     if (this.duration > 0) return this.duration;
/*      */     
/*  439 */     DBStat stat = DBStat.getDBStat(this.stats, "skillActiveDuration", index);
/*      */     
/*  441 */     if (stat == null) return 0;
/*      */     
/*  443 */     return (int)stat.getStatMin();
/*      */   }
/*      */   
/*      */   public int getTriggerLifePerc(int index) {
/*  447 */     if (this.dbBuffSkill != null) return this.dbBuffSkill.getTriggerLifePerc(index);
/*      */     
/*  449 */     DBStat stat = DBStat.getDBStat(this.stats, "lifeMonitorPercent", index);
/*      */     
/*  451 */     if (stat == null) return 0;
/*      */     
/*  453 */     return (int)stat.getStatMin();
/*      */   }
/*      */   
/*      */   public int getWeaponDamagePerc(int index) {
/*  457 */     if (this.dbBuffSkill != null) return this.dbBuffSkill.getWeaponDamagePerc(index);
/*      */     
/*  459 */     DBStat stat = DBStat.getDBStat(this.stats, "weaponDamagePct", index);
/*      */     
/*  461 */     if (stat == null) return 0;
/*      */     
/*  463 */     return (int)stat.getStatMin();
/*      */   }
/*      */   
/*      */   public String getConvertIn() {
/*  467 */     return this.convertIn;
/*      */   }
/*      */   
/*      */   public String getConvertOut() {
/*  471 */     return this.convertOut;
/*      */   }
/*      */   
/*      */   public String getConvertIn2() {
/*  475 */     return this.convertIn2;
/*      */   }
/*      */   
/*      */   public String getConvertOut2() {
/*  479 */     return this.convertOut2;
/*      */   }
/*      */   
/*      */   public int getConvertPerc() {
/*  483 */     return getConvertPerc(1);
/*      */   }
/*      */   
/*      */   public int getConvertPerc(int level) {
/*  487 */     DBStat stat = DBStat.getDBStat(this.stats, "conversionPercentage", level);
/*      */     
/*  489 */     if (stat == null) return 0;
/*      */     
/*  491 */     return (int)stat.getStatMin();
/*      */   }
/*      */   
/*      */   public int getConvertPerc2() {
/*  495 */     return getConvertPerc2(1);
/*      */   }
/*      */   
/*      */   public int getConvertPerc2(int level) {
/*  499 */     DBStat stat = DBStat.getDBStat(this.stats, "conversionPercentage2", level);
/*      */     
/*  501 */     if (stat == null) return 0;
/*      */     
/*  503 */     return (int)stat.getStatMin();
/*      */   }
/*      */   
/*      */   public List<DBStat> getStatList() {
/*  507 */     return getStatList(null, SkillDetail.ALL, false);
/*      */   }
/*      */   
/*      */   public List<DBStat> getStatList(String[] statExclude, SkillDetail detail) {
/*  511 */     return getStatList(statExclude, detail, false);
/*      */   }
/*      */   
/*      */   public List<DBStat> getStatList(String[] statExclude, SkillDetail detail, boolean petStat) {
/*  515 */     List<DBStat> list = new LinkedList<>();
/*      */     
/*  517 */     if ((petStat && detail != SkillDetail.SKILL) || (!petStat && detail != SkillDetail.PET))
/*      */     {
/*  519 */       for (DBStat stat : this.stats) {
/*  520 */         boolean exclude = false;
/*      */         
/*  522 */         if (statExclude != null) {
/*  523 */           for (String se : statExclude) {
/*  524 */             if (stat.getStatType().equals(se)) {
/*  525 */               exclude = true;
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*      */         
/*  532 */         if (exclude)
/*      */           continue; 
/*  534 */         if (detail == SkillDetail.PET) {
/*      */           
/*  536 */           stat.setPetStat(false);
/*      */         } else {
/*  538 */           stat.setPetStat(petStat);
/*      */         } 
/*      */         
/*  541 */         list.add(stat);
/*      */       } 
/*      */     }
/*      */     
/*  545 */     if (this.dbBuffSkill != null) {
/*  546 */       list.addAll(this.dbBuffSkill.getStatList(statExclude, detail, petStat));
/*      */     }
/*      */     
/*  549 */     if (this.dbPetSkill != null) {
/*  550 */       list.addAll(this.dbPetSkill.getStatList(statExclude, detail, true));
/*      */     }
/*      */     
/*  553 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<DBStat> getStatListNoPet() {
/*  558 */     if (this.dbBuffSkill != null) {
/*  559 */       return this.dbBuffSkill.stats;
/*      */     }
/*      */     
/*  562 */     return this.stats;
/*      */   }
/*      */   
/*      */   public String getBuffSkillID() {
/*  566 */     return this.buffSkillID;
/*      */   }
/*      */   
/*      */   public DBSkill getBuffSkill() {
/*  570 */     return this.dbBuffSkill;
/*      */   }
/*      */   
/*      */   public String getPetSkillID() {
/*  574 */     return this.petSkillID;
/*      */   }
/*      */   
/*      */   public DBSkill getPetSkill() {
/*  578 */     return this.dbPetSkill;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void clearBuffer() {
/*  586 */     hashBuffer.clear();
/*      */   }
/*      */   
/*      */   public static void createTables() throws SQLException {
/*  590 */     String dropTable = "DROP TABLE GD_SKILL";
/*  591 */     String createTable = "CREATE TABLE GD_SKILL (SKILL_ID  VARCHAR(256) NOT NULL, REC_CLASS         VARCHAR(64), NAME              VARCHAR(256), DESCRIPTION       VARCHAR(8000), MASTERY           BOOLEAN, DEVOTION          BOOLEAN, MASTERY_ID        VARCHAR(256), SKILL_TIER        INTEGER, MAX_LEVEL         INTEGER, BITMAP_DOWN_ID    VARCHAR(256), BITMAP_DOWN       BLOB(128K), BITMAP_UP_ID      VARCHAR(256), BITMAP_UP         BLOB(128K), LEVEL             INTEGER, DURATION          INTEGER, BONUS_INCREMENT   BOOLEAN, MODIFIED          BOOLEAN, BUFF_SKILL_ID     VARCHAR(256), PET_SKILL_ID      VARCHAR(256), CONVERT_IN        VARCHAR(16), CONVERT_OUT       VARCHAR(16), CONVERT_IN_2      VARCHAR(16), CONVERT_OUT_2     VARCHAR(16), DEPEND_ALL        BOOLEAN, PRIMARY KEY (SKILL_ID))";
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
/*  619 */     try (Connection conn = GDDBData.getConnection()) {
/*  620 */       boolean auto = conn.getAutoCommit();
/*  621 */       conn.setAutoCommit(false);
/*      */       
/*  623 */       try (Statement st = conn.createStatement()) {
/*  624 */         if (GDDBUtil.tableExists(conn, "GD_SKILL")) {
/*  625 */           st.execute(dropTable);
/*      */         }
/*  627 */         if (GDDBUtil.tableExists(conn, "GD_SKILL_CHAR")) {
/*  628 */           st.execute("DROP TABLE GD_SKILL_CHAR");
/*      */         }
/*  630 */         if (GDDBUtil.tableExists(conn, "GD_SKILL_CHARRACES")) {
/*  631 */           st.execute("DROP TABLE GD_SKILL_CHARRACES");
/*      */         }
/*  633 */         if (GDDBUtil.tableExists(conn, "GD_SKILL_DAMAGE")) {
/*  634 */           st.execute("DROP TABLE GD_SKILL_DAMAGE");
/*      */         }
/*  636 */         st.execute(createTable);
/*  637 */         st.close();
/*      */         
/*  639 */         conn.commit();
/*      */         
/*  641 */         DBStat.createSkillTable(conn);
/*  642 */         DBStatBonusRace.createSkillTable(conn);
/*  643 */         DBSkillSpawn.createTable(conn);
/*  644 */         DBSkillExperience.createTable(conn);
/*  645 */         DBSkillDependency.createTable(conn);
/*  646 */         DBSkillConnector.createTable(conn);
/*      */       }
/*  648 */       catch (SQLException ex) {
/*  649 */         conn.rollback();
/*      */         
/*  651 */         Object[] args = { "GD_SKILL" };
/*  652 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*      */         
/*  654 */         GDMsgLogger.addError(msg);
/*      */         
/*  656 */         throw ex;
/*      */       } finally {
/*      */         
/*  659 */         conn.setAutoCommit(auto);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void delete(String skillID) throws SQLException {
/*  665 */     String deleteEntry = "DELETE FROM GD_SKILL WHERE SKILL_ID = ?";
/*      */     
/*  667 */     try (Connection conn = GDDBData.getConnection()) {
/*  668 */       boolean auto = conn.getAutoCommit();
/*  669 */       conn.setAutoCommit(false);
/*      */       
/*  671 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/*  672 */         ps.setString(1, skillID);
/*  673 */         ps.executeUpdate();
/*  674 */         ps.close();
/*      */         
/*  676 */         DBStat.deleteSkill(conn, skillID);
/*  677 */         DBStatBonusRace.deleteSkill(conn, skillID);
/*  678 */         DBSkillExperience.delete(conn, skillID);
/*  679 */         DBSkillConnector.delete(conn, skillID);
/*      */         
/*  681 */         conn.commit();
/*      */       }
/*  683 */       catch (SQLException ex) {
/*  684 */         conn.rollback();
/*      */         
/*  686 */         Object[] args = { skillID, "GD_SKILL" };
/*  687 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*      */         
/*  689 */         GDMsgLogger.addError(msg);
/*  690 */         GDMsgLogger.addError(ex);
/*      */         
/*  692 */         throw ex;
/*      */       } finally {
/*      */         
/*  695 */         conn.setAutoCommit(auto);
/*      */       }
/*      */     
/*  698 */     } catch (SQLException ex) {
/*  699 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void insert(ARZRecord record) throws SQLException {
/*  704 */     DBSkill entry = get(record.getFileName());
/*      */     
/*  706 */     if (entry != null)
/*      */       return; 
/*  708 */     DBSkill skill = new DBSkill(record);
/*      */     
/*  710 */     String insert = "INSERT INTO GD_SKILL VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */     
/*  712 */     try (Connection conn = GDDBData.getConnection()) {
/*  713 */       boolean auto = conn.getAutoCommit();
/*  714 */       conn.setAutoCommit(false);
/*      */       
/*  716 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/*  717 */         ps.setString(1, skill.skillID);
/*  718 */         ps.setString(2, skill.recClass);
/*  719 */         ps.setString(3, skill.name);
/*  720 */         ps.setString(4, skill.description);
/*  721 */         ps.setBoolean(5, skill.mastery);
/*  722 */         ps.setBoolean(6, skill.devotion);
/*  723 */         ps.setString(7, skill.masteryID);
/*  724 */         ps.setInt(8, skill.skillTier);
/*  725 */         ps.setInt(9, skill.maxLevel);
/*  726 */         ps.setString(10, skill.bitmapDownID);
/*  727 */         if (skill.bitmapDown == null) {
/*  728 */           ps.setBlob(11, (Blob)null);
/*      */         } else {
/*  730 */           ps.setBlob(11, new ByteArrayInputStream(skill.bitmapDown));
/*      */         } 
/*  732 */         ps.setString(12, skill.bitmapUpID);
/*  733 */         if (skill.bitmapUp == null) {
/*  734 */           ps.setBlob(13, (Blob)null);
/*      */         } else {
/*  736 */           ps.setBlob(13, new ByteArrayInputStream(skill.bitmapUp));
/*      */         } 
/*  738 */         ps.setInt(14, skill.level);
/*  739 */         ps.setInt(15, skill.duration);
/*  740 */         ps.setBoolean(16, skill.bonusIncrement);
/*  741 */         ps.setBoolean(17, skill.modified);
/*  742 */         ps.setString(18, skill.buffSkillID);
/*  743 */         ps.setString(19, skill.petSkillID);
/*  744 */         ps.setString(20, skill.convertIn);
/*  745 */         ps.setString(21, skill.convertOut);
/*  746 */         ps.setString(22, skill.convertIn2);
/*  747 */         ps.setString(23, skill.convertOut2);
/*  748 */         ps.setBoolean(24, skill.dependencyAll);
/*      */         
/*  750 */         ps.executeUpdate();
/*  751 */         ps.close();
/*      */         
/*  753 */         DBStat.insertSkill(conn, skill.skillID, skill.stats);
/*  754 */         DBStatBonusRace.insertSkill(conn, skill.skillID, skill.statBonusRaces);
/*  755 */         DBSkillSpawn.insert(conn, skill.skillID, skill.spawnPets);
/*  756 */         DBSkillExperience.insert(conn, skill.skillID, skill.xpLevels);
/*  757 */         DBSkillDependency.insert(conn, skill.skillID, skill.dependencies);
/*  758 */         DBSkillConnector.insert(conn, skill.skillID, skill.connections);
/*      */         
/*  760 */         conn.commit();
/*      */       }
/*  762 */       catch (SQLException ex) {
/*  763 */         conn.rollback();
/*      */         
/*  765 */         Object[] args = { record.getFileName(), "GD_SKILL" };
/*  766 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*      */         
/*  768 */         if (skill.isDevotion()) {
/*      */           
/*  770 */           GDMsgLogger.addError(msg);
/*  771 */           GDMsgLogger.addError(ex);
/*      */         } else {
/*  773 */           GDMsgLogger.addLowError(msg);
/*  774 */           GDMsgLogger.addLowError(ex);
/*      */         } 
/*      */       } finally {
/*      */         
/*  778 */         conn.setAutoCommit(auto);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static DBSkill get(String skillID) {
/*  784 */     if (skillID == null) return null;
/*      */     
/*  786 */     DBSkill skill = null;
/*      */     
/*  788 */     skill = hashBuffer.get(skillID);
/*      */     
/*  790 */     if (skill == null) {
/*  791 */       skill = getDB(skillID);
/*      */       
/*  793 */       if (skill != null) hashBuffer.put(skill.skillID, skill);
/*      */     
/*      */     } 
/*  796 */     return skill;
/*      */   }
/*      */   
/*      */   public static List<DBSkill> getAll() {
/*  800 */     String command = "SELECT SKILL_ID FROM GD_SKILL";
/*      */     
/*  802 */     List<DBSkill> skills = new LinkedList<>();
/*  803 */     List<String> skillIDs = new LinkedList<>();
/*  804 */     try(Connection conn = GDDBData.getConnection(); 
/*  805 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*  806 */       try (ResultSet rs = ps.executeQuery()) {
/*  807 */         while (rs.next()) {
/*  808 */           String s = rs.getString(1);
/*  809 */           skillIDs.add(s);
/*      */         }
/*      */       
/*  812 */       } catch (SQLException ex) {
/*  813 */         throw ex;
/*      */       } 
/*      */       
/*  816 */       conn.commit();
/*      */       
/*  818 */       for (String skillID : skillIDs) {
/*  819 */         DBSkill skill = get(skillID);
/*  820 */         skills.add(skill);
/*      */       }
/*      */     
/*  823 */     } catch (SQLException ex) {
/*  824 */       Object[] args = { "-", "GD_SKILL" };
/*  825 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/*  827 */       GDMsgLogger.addError(msg);
/*  828 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/*  831 */     return skills;
/*      */   }
/*      */   
/*      */   public static List<DBSkill> getBonusSkills() {
/*  835 */     String command = "SELECT SKILL_ID FROM GD_SKILL WHERE BONUS_INCREMENT = true ORDER BY MASTERY DESC, NAME ASC";
/*      */     
/*  837 */     List<DBSkill> skills = new LinkedList<>();
/*  838 */     List<String> skillIDs = new LinkedList<>();
/*  839 */     try(Connection conn = GDDBData.getConnection(); 
/*  840 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*  841 */       try (ResultSet rs = ps.executeQuery()) {
/*  842 */         while (rs.next()) {
/*  843 */           String s = rs.getString(1);
/*  844 */           skillIDs.add(s);
/*      */         }
/*      */       
/*  847 */       } catch (SQLException ex) {
/*  848 */         throw ex;
/*      */       } 
/*      */       
/*  851 */       conn.commit();
/*      */       
/*  853 */       for (String skillID : skillIDs) {
/*  854 */         DBSkill skill = get(skillID);
/*  855 */         skills.add(skill);
/*      */       }
/*      */     
/*  858 */     } catch (SQLException ex) {
/*  859 */       Object[] args = { "-", "GD_SKILL" };
/*  860 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/*  862 */       GDMsgLogger.addError(msg);
/*  863 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/*  866 */     return skills;
/*      */   }
/*      */   
/*      */   public static List<DBSkill> getModifiedSkills() {
/*  870 */     String command = "SELECT SKILL_ID FROM GD_SKILL WHERE MODIFIED = true ORDER BY MASTERY DESC, NAME ASC";
/*      */     
/*  872 */     List<DBSkill> skills = new LinkedList<>();
/*  873 */     List<String> skillIDs = new LinkedList<>();
/*  874 */     try(Connection conn = GDDBData.getConnection(); 
/*  875 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*  876 */       try (ResultSet rs = ps.executeQuery()) {
/*  877 */         while (rs.next()) {
/*  878 */           String s = rs.getString(1);
/*  879 */           skillIDs.add(s);
/*      */         }
/*      */       
/*  882 */       } catch (SQLException ex) {
/*  883 */         throw ex;
/*      */       } 
/*      */       
/*  886 */       conn.commit();
/*      */       
/*  888 */       for (String skillID : skillIDs) {
/*  889 */         DBSkill skill = get(skillID);
/*  890 */         skills.add(skill);
/*      */       }
/*      */     
/*  893 */     } catch (SQLException ex) {
/*  894 */       Object[] args = { "-", "GD_SKILL" };
/*  895 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/*  897 */       GDMsgLogger.addError(msg);
/*  898 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/*  901 */     return skills;
/*      */   }
/*      */   
/*      */   private static DBSkill getDB(String skillID) {
/*  905 */     DBSkill skill = null;
/*      */     
/*  907 */     String command = "SELECT * FROM GD_SKILL WHERE SKILL_ID = ?";
/*      */     
/*  909 */     try(Connection conn = GDDBData.getConnection(); 
/*  910 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*  911 */       ps.setString(1, skillID);
/*      */       
/*  913 */       try (ResultSet rs = ps.executeQuery()) {
/*  914 */         List<DBSkill> list = wrap(rs);
/*      */         
/*  916 */         if (list.isEmpty()) {
/*  917 */           skill = null;
/*      */         } else {
/*  919 */           skill = list.get(0);
/*      */         } 
/*      */         
/*  922 */         conn.commit();
/*      */       }
/*  924 */       catch (SQLException ex) {
/*  925 */         throw ex;
/*      */       }
/*      */     
/*  928 */     } catch (SQLException ex) {
/*  929 */       Object[] args = { skillID, "GD_SKILL" };
/*  930 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/*  932 */       GDMsgLogger.addError(msg);
/*  933 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/*  936 */     return skill;
/*      */   }
/*      */   
/*      */   private void createBonusRaceStats(DBStat stat) {
/*  940 */     if (stat == null)
/*  941 */       return;  if (this.statBonusRaces == null)
/*  942 */       return;  if (this.statBonusRaces.isEmpty())
/*      */       return; 
/*  944 */     this.stats.remove(stat);
/*      */     
/*  946 */     List<DBStat> list = DBStat.createStatsFromRaceBonusList(stat, this.statBonusRaces);
/*  947 */     this.stats.addAll(list);
/*      */   }
/*      */   
/*      */   private static List<DBSkill> wrap(ResultSet rs) throws SQLException {
/*  951 */     LinkedList<DBSkill> list = new LinkedList<>();
/*  952 */     Blob blob = null;
/*      */     
/*  954 */     while (rs.next()) {
/*  955 */       DBSkill skill = new DBSkill();
/*      */       
/*  957 */       skill.skillID = rs.getString(1);
/*  958 */       skill.recClass = rs.getString(2);
/*  959 */       skill.name = rs.getString(3);
/*  960 */       skill.description = rs.getString(4);
/*  961 */       skill.mastery = rs.getBoolean(5);
/*  962 */       skill.devotion = rs.getBoolean(6);
/*  963 */       skill.masteryID = rs.getString(7);
/*  964 */       skill.skillTier = rs.getInt(8);
/*  965 */       skill.maxLevel = rs.getInt(9);
/*  966 */       skill.bitmapDownID = rs.getString(10);
/*  967 */       blob = rs.getBlob(11);
/*  968 */       if (blob == null) {
/*  969 */         skill.bitmapDown = null;
/*      */       } else {
/*  971 */         skill.bitmapDown = blob.getBytes(1L, (int)blob.length());
/*      */       } 
/*      */       
/*  974 */       if (skill.bitmapDown != null) {
/*      */         try {
/*  976 */           skill.imageDown = DDSLoader.getImage(skill.bitmapDown);
/*      */         }
/*  978 */         catch (GDParseException ex) {
/*  979 */           Object[] args = { skill.skillID };
/*  980 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_BITMAP_DECODE_FAILED", args);
/*      */           
/*  982 */           GDMsgLogger.addError(msg);
/*      */           
/*  984 */           skill.imageDown = null;
/*      */         } 
/*      */       }
/*      */       
/*  988 */       skill.bitmapUpID = rs.getString(12);
/*  989 */       blob = rs.getBlob(13);
/*  990 */       if (blob == null) {
/*  991 */         skill.bitmapUp = null;
/*      */       } else {
/*  993 */         skill.bitmapUp = blob.getBytes(1L, (int)blob.length());
/*      */       } 
/*      */       
/*  996 */       if (skill.bitmapUp != null) {
/*      */         try {
/*  998 */           skill.imageUp = DDSLoader.getImage(skill.bitmapUp);
/*      */         }
/* 1000 */         catch (GDParseException ex) {
/* 1001 */           Object[] args = { skill.skillID };
/* 1002 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_BITMAP_DECODE_FAILED", args);
/*      */           
/* 1004 */           GDMsgLogger.addError(msg);
/*      */           
/* 1006 */           skill.imageUp = null;
/*      */         } 
/*      */       }
/* 1009 */       skill.level = rs.getInt(14);
/* 1010 */       skill.duration = rs.getInt(15);
/* 1011 */       skill.bonusIncrement = rs.getBoolean(16);
/* 1012 */       skill.modified = rs.getBoolean(17);
/* 1013 */       skill.buffSkillID = rs.getString(18);
/* 1014 */       skill.petSkillID = rs.getString(19);
/* 1015 */       skill.convertIn = rs.getString(20);
/* 1016 */       skill.convertOut = rs.getString(21);
/* 1017 */       skill.convertIn2 = rs.getString(22);
/* 1018 */       skill.convertOut2 = rs.getString(23);
/* 1019 */       skill.dependencyAll = rs.getBoolean(24);
/*      */       
/* 1021 */       skill.xpLevels = DBSkillExperience.getBySkillID(skill.skillID);
/* 1022 */       skill.numXPLevels = skill.xpLevels.size();
/*      */       
/* 1024 */       skill.stats = DBStat.getSkill(skill.skillID);
/* 1025 */       Collections.sort(skill.stats);
/*      */       
/* 1027 */       DBStat stat = DBStat.getByType(skill.stats, "racialBonusPercentDamage", 1);
/* 1028 */       if (stat != null) {
/* 1029 */         skill.statBonusRaces = DBStatBonusRace.getSkill(skill.skillID);
/* 1030 */         skill.createBonusRaceStats(stat);
/*      */       } 
/*      */       
/* 1033 */       skill.spawnPets = DBSkillSpawn.getBySkillID(skill.skillID);
/* 1034 */       skill.dependencies = DBSkillDependency.getBySkillID(skill.skillID);
/* 1035 */       skill.connections = DBSkillConnector.getBySkillID(skill.skillID);
/*      */       
/* 1037 */       if (skill.masteryID != null && 
/* 1038 */         !skill.masteryID.equals(skill.skillID)) {
/* 1039 */         skill.dbMastery = get(skill.masteryID);
/*      */       }
/*      */ 
/*      */       
/* 1043 */       if (skill.buffSkillID != null && 
/* 1044 */         !skill.buffSkillID.equals(skill.skillID)) {
/* 1045 */         skill.dbBuffSkill = get(skill.buffSkillID);
/*      */       }
/*      */ 
/*      */       
/* 1049 */       if (skill.petSkillID != null && 
/* 1050 */         !skill.petSkillID.equals(skill.skillID)) {
/* 1051 */         skill.dbPetSkill = get(skill.petSkillID);
/*      */       }
/*      */ 
/*      */       
/* 1055 */       list.add(skill);
/*      */     } 
/*      */     
/* 1058 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<ImageInfo> getImageInfos(String id) {
/* 1066 */     List<ImageInfo> list = new LinkedList<>();
/* 1067 */     Blob blob = null;
/*      */     
/* 1069 */     String command = "SELECT SKILL_ID, BITMAP_DOWN_ID, BITMAP_DOWN, BITMAP_UP_ID, BITMAP_UP FROM GD_SKILL WHERE ((BITMAP_DOWN_ID LIKE '" + id + "%' AND BITMAP_DOWN IS NULL) OR (BITMAP_UP_ID LIKE '" + id + "%' AND BITMAP_UP IS NULL))";
/*      */ 
/*      */ 
/*      */     
/* 1073 */     try(Connection conn = GDDBData.getConnection(); 
/* 1074 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 1075 */       try (ResultSet rs = ps.executeQuery()) {
/* 1076 */         while (rs.next()) {
/* 1077 */           ImageInfo info = new ImageInfo();
/*      */           
/* 1079 */           info.skillID = rs.getString(1);
/*      */           
/* 1081 */           info.bitmapDownID = rs.getString(2);
/* 1082 */           blob = rs.getBlob(3);
/* 1083 */           if (blob == null) {
/* 1084 */             info.bitmapDown = null;
/*      */           } else {
/* 1086 */             info.bitmapDown = blob.getBytes(1L, (int)blob.length());
/*      */           } 
/*      */           
/* 1089 */           info.bitmapUpID = rs.getString(4);
/* 1090 */           blob = rs.getBlob(5);
/* 1091 */           if (blob == null) {
/* 1092 */             info.bitmapUp = null;
/*      */           } else {
/* 1094 */             info.bitmapUp = blob.getBytes(1L, (int)blob.length());
/*      */           } 
/*      */           
/* 1097 */           list.add(info);
/*      */         } 
/*      */         
/* 1100 */         conn.commit();
/*      */       }
/* 1102 */       catch (Exception ex) {
/* 1103 */         throw ex;
/*      */       }
/*      */     
/* 1106 */     } catch (Exception ex) {
/* 1107 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 1110 */     return list;
/*      */   }
/*      */   
/*      */   public static void updateImageInfo(List<ImageInfo> list) {
/* 1114 */     if (list == null)
/*      */       return; 
/* 1116 */     String command = "UPDATE GD_SKILL SET BITMAP_DOWN = ?, BITMAP_UP = ? WHERE SKILL_ID = ?";
/*      */ 
/*      */     
/* 1119 */     try (Connection conn = GDDBData.getConnection()) {
/* 1120 */       boolean auto = conn.getAutoCommit();
/* 1121 */       conn.setAutoCommit(false);
/*      */       
/* 1123 */       for (ImageInfo info : list) {
/* 1124 */         try (PreparedStatement ps = conn.prepareStatement(command)) {
/* 1125 */           if (info.bitmapDown != null || info.bitmapUp != null)
/*      */           {
/*      */             
/* 1128 */             if (info.bitmapDown == null) {
/* 1129 */               ps.setBlob(1, (Blob)null);
/*      */             } else {
/* 1131 */               ps.setBlob(1, new ByteArrayInputStream(info.bitmapDown));
/*      */             } 
/*      */             
/* 1134 */             if (info.bitmapUp == null) {
/* 1135 */               ps.setBlob(2, (Blob)null);
/*      */             } else {
/* 1137 */               ps.setBlob(2, new ByteArrayInputStream(info.bitmapUp));
/*      */             } 
/*      */             
/* 1140 */             ps.setString(3, info.skillID);
/*      */             
/* 1142 */             ps.executeUpdate();
/* 1143 */             ps.close();
/*      */             
/* 1145 */             conn.commit();
/*      */           }
/*      */         
/* 1148 */         } catch (SQLException ex) {
/* 1149 */           conn.rollback();
/*      */           
/* 1151 */           Object[] args = { info.skillID };
/* 1152 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_IN_ITEM_IMAGE_SIZE", args);
/*      */           
/* 1154 */           GDMsgLogger.addWarning(msg);
/*      */           
/* 1156 */           GDMsgLogger.addWarning(ex);
/*      */         }
/*      */       
/*      */       } 
/* 1160 */     } catch (SQLException ex) {
/* 1161 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateDB() {
/* 1170 */     setMasteryID();
/* 1171 */     updateBonusIncFlag();
/* 1172 */     updateModifiedFlag();
/* 1173 */     updateSkillNames();
/*      */   }
/*      */   
/*      */   public static void setMasteryID() {
/* 1177 */     String command = "UPDATE GD_SKILL SET MASTERY_ID = ? WHERE SKILL_ID = ?";
/*      */ 
/*      */     
/* 1180 */     try(Connection conn = GDDBData.getConnection(); 
/* 1181 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*      */       
/* 1183 */       List<DBSkillTree> trees = DBSkillTree.getMasteryTrees();
/* 1184 */       List<DBSkill> skills = getAll();
/*      */       
/* 1186 */       for (DBSkill skill : skills) {
/* 1187 */         for (DBSkillTree tree : trees) {
/* 1188 */           if (tree.getSkillIDList().contains(skill.skillID)) {
/* 1189 */             DBSkill mastery = tree.getMasterySkill();
/*      */             
/* 1191 */             if (mastery != null) {
/* 1192 */               ps.setString(1, mastery.getSkillID());
/* 1193 */               ps.setString(2, skill.skillID);
/*      */               
/* 1195 */               ps.executeUpdate();
/*      */               
/* 1197 */               ps.clearParameters();
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1205 */       ps.close();
/*      */       
/* 1207 */       conn.commit();
/*      */     }
/* 1209 */     catch (SQLException ex) {
/* 1210 */       Object[] args = { "-", "GD_SKILL" };
/* 1211 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 1213 */       GDMsgLogger.addError(msg);
/* 1214 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void updateBonusIncFlag() {
/* 1219 */     List<String> list = new LinkedList<>();
/*      */     
/*      */     try {
/* 1222 */       List<DBSkillBonus> bonuses = DBSkillBonus.getAllItem();
/*      */       
/* 1224 */       if (bonuses != null) {
/* 1225 */         for (DBSkillBonus bonus : bonuses) {
/* 1226 */           String skillID = bonus.getEntity();
/*      */           
/* 1228 */           if (skillID != null && 
/* 1229 */             !list.contains(skillID)) {
/* 1230 */             list.add(skillID);
/*      */           }
/*      */         }
/*      */       
/*      */       }
/*      */     }
/* 1236 */     catch (SQLException sQLException) {}
/*      */     
/*      */     try {
/* 1239 */       List<DBSkillBonus> bonuses = DBSkillBonus.getAllAffix();
/*      */       
/* 1241 */       if (bonuses != null) {
/* 1242 */         for (DBSkillBonus bonus : bonuses) {
/* 1243 */           String skillID = bonus.getEntity();
/*      */           
/* 1245 */           if (skillID != null && 
/* 1246 */             !list.contains(skillID)) {
/* 1247 */             list.add(skillID);
/*      */           }
/*      */         }
/*      */       
/*      */       }
/*      */     }
/* 1253 */     catch (SQLException sQLException) {}
/*      */     
/* 1255 */     updateBonusIncFlag(list);
/*      */   }
/*      */   
/*      */   private static void updateBonusIncFlag(List<String> list) {
/* 1259 */     String command = "UPDATE GD_SKILL SET BONUS_INCREMENT = true WHERE SKILL_ID = ?";
/*      */ 
/*      */     
/* 1262 */     try(Connection conn = GDDBData.getConnection(); 
/* 1263 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*      */       
/* 1265 */       for (String skillID : list) {
/* 1266 */         if (skillID == null)
/*      */           continue; 
/* 1268 */         ps.setString(1, skillID);
/*      */         
/* 1270 */         ps.executeUpdate();
/*      */         
/* 1272 */         ps.clearParameters();
/*      */       } 
/*      */       
/* 1275 */       ps.close();
/*      */       
/* 1277 */       conn.commit();
/*      */     }
/* 1279 */     catch (SQLException ex) {
/* 1280 */       Object[] args = { "-", "GD_SKILL" };
/* 1281 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 1283 */       GDMsgLogger.addError(msg);
/* 1284 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void updateModifiedFlag() {
/* 1289 */     List<String> list = new LinkedList<>();
/*      */     
/*      */     try {
/* 1292 */       List<DBSkillModifier> modifiers = DBSkillModifier.getAllItem();
/*      */       
/* 1294 */       if (modifiers != null) {
/* 1295 */         for (DBSkillModifier modifier : modifiers) {
/* 1296 */           String skillID = modifier.getSkillID();
/* 1297 */           if (!list.contains(skillID)) {
/* 1298 */             list.add(skillID);
/*      */           }
/*      */         }
/*      */       
/*      */       }
/* 1303 */     } catch (SQLException sQLException) {}
/*      */     
/*      */     try {
/* 1306 */       List<DBSkillModifier> modifiers = DBSkillModifier.getAllAffix();
/*      */       
/* 1308 */       if (modifiers != null) {
/* 1309 */         for (DBSkillModifier modifier : modifiers) {
/* 1310 */           String skillID = modifier.getSkillID();
/* 1311 */           if (!list.contains(skillID)) {
/* 1312 */             list.add(skillID);
/*      */           }
/*      */         }
/*      */       
/*      */       }
/* 1317 */     } catch (SQLException sQLException) {}
/*      */     
/* 1319 */     updateModifiedFlag(list);
/*      */   }
/*      */   
/*      */   private static void updateModifiedFlag(List<String> list) {
/* 1323 */     String command = "UPDATE GD_SKILL SET MODIFIED = true WHERE SKILL_ID = ?";
/*      */ 
/*      */     
/* 1326 */     try(Connection conn = GDDBData.getConnection(); 
/* 1327 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*      */       
/* 1329 */       for (String skillID : list) {
/* 1330 */         if (skillID == null)
/*      */           continue; 
/* 1332 */         ps.setString(1, skillID);
/*      */         
/* 1334 */         ps.executeUpdate();
/*      */         
/* 1336 */         ps.clearParameters();
/*      */       } 
/*      */       
/* 1339 */       ps.close();
/*      */       
/* 1341 */       conn.commit();
/*      */     }
/* 1343 */     catch (SQLException ex) {
/* 1344 */       Object[] args = { "-", "GD_SKILL" };
/* 1345 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 1347 */       GDMsgLogger.addError(msg);
/* 1348 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateSkillNames() {
/* 1354 */     boolean changed = true;
/* 1355 */     while (changed) {
/* 1356 */       changed = false;
/*      */       
/* 1358 */       List<DBSkill> list = getEmptyNames();
/*      */       
/* 1360 */       if (list == null)
/*      */         continue; 
/* 1362 */       for (DBSkill skill : list) {
/* 1363 */         if (skill == null)
/*      */           continue; 
/* 1365 */         String id = null;
/*      */         
/* 1367 */         if (skill.getPetSkillID() != null) id = skill.getPetSkillID(); 
/* 1368 */         if (skill.getBuffSkillID() != null) id = skill.getBuffSkillID();
/*      */         
/* 1370 */         if (id == null)
/*      */           continue; 
/* 1372 */         DBSkill dbs = get(id);
/*      */         
/* 1374 */         if (dbs == null) {
/*      */           
/* 1376 */           int pos = skill.skillID.indexOf("/old/");
/*      */           
/* 1378 */           if (pos == -1) {
/* 1379 */             Object[] args = { "GD_SKILL", skill.skillID, id, "GD_SKILL" };
/* 1380 */             String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_REF_TABLE_ID_FROM_ID", args);
/*      */             
/* 1382 */             GDMsgLogger.addWarning(msg);
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 1388 */         skill.name = dbs.getName();
/*      */         
/* 1390 */         if (skill.name == null)
/*      */           continue; 
/* 1392 */         changed = true;
/*      */       } 
/*      */       
/* 1395 */       if (changed) {
/* 1396 */         updateNames(list);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private static List<DBSkill> getEmptyNames() {
/* 1402 */     List<DBSkill> list = new LinkedList<>();
/*      */     
/* 1404 */     String command = "SELECT * FROM GD_SKILL WHERE NAME is null AND ((BUFF_SKILL_ID is not null) OR (PET_SKILL_ID is not null))";
/*      */ 
/*      */     
/* 1407 */     try(Connection conn = GDDBData.getConnection(); 
/* 1408 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*      */       
/* 1410 */       try (ResultSet rs = ps.executeQuery()) {
/* 1411 */         list = wrap(rs);
/*      */         
/* 1413 */         conn.commit();
/*      */       }
/* 1415 */       catch (SQLException ex) {
/* 1416 */         throw ex;
/*      */       }
/*      */     
/* 1419 */     } catch (SQLException ex) {
/* 1420 */       Object[] args = { "NAME is null", "GD_SKILL" };
/* 1421 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 1423 */       GDMsgLogger.addError(msg);
/* 1424 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 1427 */     return list;
/*      */   }
/*      */   
/*      */   private static void updateNames(List<DBSkill> list) {
/* 1431 */     String command = "UPDATE GD_SKILL SET NAME = ? WHERE SKILL_ID = ?";
/*      */ 
/*      */     
/* 1434 */     try(Connection conn = GDDBData.getConnection(); 
/* 1435 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*      */       
/* 1437 */       for (DBSkill skill : list) {
/* 1438 */         if (skill.name == null)
/*      */           continue; 
/* 1440 */         ps.setString(1, skill.name);
/* 1441 */         ps.setString(2, skill.skillID);
/*      */         
/* 1443 */         ps.executeUpdate();
/*      */         
/* 1445 */         ps.clearParameters();
/*      */       } 
/*      */       
/* 1448 */       ps.close();
/*      */       
/* 1450 */       conn.commit();
/*      */     }
/* 1452 */     catch (SQLException ex) {
/* 1453 */       Object[] args = { "-", "GD_SKILL" };
/* 1454 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 1456 */       GDMsgLogger.addError(msg);
/* 1457 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BonusDetail getBonusDetail() {
/* 1466 */     BonusDetail bonus = new BonusDetail(this);
/*      */     
/* 1468 */     return bonus;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1476 */     String s = this.name;
/*      */     
/* 1478 */     if (isMastery()) {
/* 1479 */       s = s + " (" + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_MASTERY") + ")";
/*      */     } else {
/* 1481 */       String m = getMasteryName();
/*      */       
/* 1483 */       if (m != null) {
/* 1484 */         s = s + " (" + m + ")";
/*      */       }
/*      */     } 
/*      */     
/* 1488 */     return s;
/*      */   }
/*      */   
/*      */   public String getSkillDescription(int level, int charLevel) {
/* 1492 */     return DetailComposer.getSkillDescription(this, level, charLevel, false, true);
/*      */   }
/*      */   
/*      */   public String getSkillNextLevelDescription(int level, int charLevel) {
/* 1496 */     return DetailComposer.getSkillDescription(this, level, charLevel, true, true);
/*      */   }
/*      */   
/*      */   public String getSkillDescription(int level, int charLevel, boolean fullHTML) {
/* 1500 */     return DetailComposer.getSkillDescription(this, level, charLevel, false, fullHTML);
/*      */   } }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBSkill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */