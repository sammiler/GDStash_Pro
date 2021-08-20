/*      */ package org.gdstash.file;
/*      */ 
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import net.objecthunter.exp4j.Expression;
/*      */ import net.objecthunter.exp4j.ExpressionBuilder;
/*      */ import org.gdstash.db.DBAffixSet;
/*      */ import org.gdstash.db.DBConstellationAffinity;
/*      */ import org.gdstash.db.DBConstellationStar;
/*      */ import org.gdstash.db.DBEngineLevel;
/*      */ import org.gdstash.db.DBEngineMasteryTier;
/*      */ import org.gdstash.db.DBEnginePlayerMasteries;
/*      */ import org.gdstash.db.DBFactionReputation;
/*      */ import org.gdstash.db.DBFormula;
/*      */ import org.gdstash.db.DBFormulaSet;
/*      */ import org.gdstash.db.DBLootTableAffixSetAlloc;
/*      */ import org.gdstash.db.DBLootTableItemAlloc;
/*      */ import org.gdstash.db.DBLootTableSetAlloc;
/*      */ import org.gdstash.db.DBPetSkill;
/*      */ import org.gdstash.db.DBSkillBonus;
/*      */ import org.gdstash.db.DBSkillConnector;
/*      */ import org.gdstash.db.DBSkillDependency;
/*      */ import org.gdstash.db.DBSkillModifier;
/*      */ import org.gdstash.db.DBSkillSpawn;
/*      */ import org.gdstash.db.DBSkillTreeAlloc;
/*      */ import org.gdstash.db.DBStat;
/*      */ import org.gdstash.db.DBStatBonusRace;
/*      */ import org.gdstash.db.ItemSlots;
/*      */ import org.gdstash.ui.GDStashFrame;
/*      */ import org.gdstash.util.GDConstants;
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
/*      */ public class ARZRecord
/*      */ {
/*      */   public boolean error = false;
/*      */   public boolean storeInDB = false;
/*  287 */   private ItemSlots slots = new ItemSlots();
/*  288 */   private List<DBFactionReputation> factionReputations = new LinkedList<>();
/*  289 */   private List<DBEngineMasteryTier> masteryTiers = new LinkedList<>();
/*  290 */   private List<String> classTableIDs = new LinkedList<>();
/*  291 */   private List<DBSkillModifier> skillModifiers = new LinkedList<>();
/*  292 */   private List<DBAffixSet.DBEntry> randomizers = new LinkedList<>();
/*  293 */   private List<String> itemSetItemIDs = new LinkedList<>();
/*  294 */   private List<DBLootTableItemAlloc> tableItems = new LinkedList<>();
/*  295 */   private List<DBLootTableAffixSetAlloc> tableAffixSets = new LinkedList<>();
/*  296 */   private List<DBLootTableSetAlloc> tableSetAllocs = new LinkedList<>();
/*  297 */   private List<DBFormula> formulaSetFormulas = new LinkedList<>();
/*      */   
/*  299 */   private DBFormulaSet dbFormulaSet = null;
/*  300 */   private List<DBStatBonusRace> dbStatBonusRaces = new LinkedList<>();
/*  301 */   private List<DBSkillSpawn> dbSpawnPets = new LinkedList<>();
/*  302 */   private List<DBStat> dbStats = new LinkedList<>();
/*  303 */   public List<DBSkillBonus> dbSkillBonuses = new LinkedList<>();
/*  304 */   private List<String> merchantTableIDs = new LinkedList<>();
/*  305 */   private List<String> merchantItemIDs = new LinkedList<>();
/*  306 */   private List<DBEnginePlayerMasteries> masteries = new LinkedList<>();
/*  307 */   private List<DBEngineLevel.LevelStats> levelStats = new LinkedList<>();
/*  308 */   private List<DBConstellationAffinity> constellationAffinities = new LinkedList<>();
/*  309 */   private List<DBConstellationStar> constellationStars = new LinkedList<>();
/*  310 */   private List<DBSkillTreeAlloc> masterySkillIDs = new LinkedList<>();
/*  311 */   private List<String> skillButtons = new LinkedList<>();
/*  312 */   private List<Integer> skillXPLevels = new LinkedList<>();
/*  313 */   private List<DBSkillDependency> skillDependencies = new LinkedList<>();
/*  314 */   private List<DBSkillConnector> skillConnections = new LinkedList<>();
/*      */   
/*      */   private boolean devotion = false;
/*  317 */   private List<DBPetSkill> petSkills = new LinkedList<>(); public int strID; public int len_str; public String str; public int offset; public int len_comp; public int len_decomp; public byte[] filedata; private String fileName; private String fileDesc; private String recordClass; private String template; private String factionAltNeutralTag; private int devotionCap; private boolean factionBountyEnabled; private boolean factionQuestEnabled; private String factionTag; private String merchantFactionID; private String merchantTableSetID; private int playerBaseDex; private int playerBaseInt; private int playerBaseStr; private int playerBaseLife; private int playerBaseMana; private int playerIncDex; private int playerIncInt; private int playerIncStr; private int playerIncLife; private int playerIncMana; private int playerMaxLevel; private String xpFormula; private String constellationNameTag; private String constellationInfoTag; private String shrineNameTag; private String shrineName; private boolean shrineNormalDisabled; private boolean shrineNormalLocked; private boolean shrineEpicDisabled; private boolean shrineEpicLocked; private boolean shrineLegendaryDisabled; private boolean shrineLegendaryLocked; private String buttonSkillID; private int posX; private int posY; private int offsetX; private int offsetY; private boolean circularButton; private String masteryBarID; private String skillPaneID; private String masteryBitmapID; private String titleTag; private String skillBuffID; private String skillDescription; private String skillNameTag; private String skillName; private boolean mastery; private int skillTier; private int maxLevel;
/*      */   private String bitmapDownID;
/*      */   private String bitmapUpID;
/*      */   private int skillLevel;
/*      */   private int skillDuration;
/*      */   private boolean bonusIncrement;
/*      */   
/*      */   public String getFileDescription() {
/*  325 */     return this.fileDesc;
/*      */   }
/*      */   private boolean skillModified; private boolean skillDependencyAll; private int triggerChance; private String triggerType; private int lootRandomCost; private String lootRandomName; private String convertIn; private String convertOut; private String convertIn2; private String convertOut2; private String petBonusID; private String rarity; private int reqLevel; private String itemSkillID; private String itemSkillLevelFormula; private int itemSkillLevel; private int offensiveChance; private int retaliationChance; private int rngPercent; private String skillControllerID; private String itemSetDescTag; private int skillModifierLevel; private String itemSetNameTag; private boolean nPre_nSuf; private boolean nPre_rSuf; private boolean rPre_nSuf; private boolean rPre_rSuf; private String armorClass; private String artifactClass; private String artifactID; private String meshID; private String baseTextureID; private String bumpTextureID; private String glowTextureID; private String shaderID; private String bitmapID; private String shardBitmapID; private String itemNameTag; private String itemDescriptionTag; private int genderCode; private String itemName; private String itemDescription; private String qualityTag; private String styleTag; private String itemSetID; private String bonusAffixSetID; private String costFormulaSetID; private boolean soulbound; private boolean hidePrefix; private boolean hideSuffix; private boolean questItem; private boolean cannotPickup; private int itemLevel; private int plusAllSkills; private int componentPieces; private int maxStackSize; private String petName; private String petFormulaLevel; private String petBioID; private String petDieSkillID; private String bioFormulaLife; private String bioFormulaMana; private String dlcRequirement;
/*      */   public String getFileName() {
/*  329 */     return this.fileName;
/*      */   }
/*      */   
/*      */   public String getRecordClass() {
/*  333 */     return this.recordClass;
/*      */   }
/*      */   
/*      */   public String getTemplate() {
/*  337 */     return this.template;
/*      */   }
/*      */   
/*      */   public String getDLCRequirement() {
/*  341 */     return this.dlcRequirement;
/*      */   }
/*      */   
/*      */   public DBFormulaSet getDBFormulaSet() {
/*  345 */     if (this.dbFormulaSet == null) {
/*  346 */       this.dbFormulaSet = new DBFormulaSet(this);
/*      */     }
/*  348 */     return this.dbFormulaSet;
/*      */   }
/*      */   
/*      */   public List<DBStat> getDBStatList() {
/*  352 */     return this.dbStats;
/*      */   }
/*      */   
/*      */   public List<DBStatBonusRace> getDBStatBonusRaceList() {
/*  356 */     return this.dbStatBonusRaces;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DBFactionReputation> getFactionReputationList() {
/*  364 */     return this.factionReputations;
/*      */   }
/*      */   
/*      */   public List<DBEngineMasteryTier> getMasteryTierList() {
/*  368 */     return this.masteryTiers;
/*      */   }
/*      */   
/*      */   public String getFactionAltNeutralTag() {
/*  372 */     return this.factionAltNeutralTag;
/*      */   }
/*      */   
/*      */   public int getDevotionCap() {
/*  376 */     return this.devotionCap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getFactionBountyEnabled() {
/*  384 */     return this.factionBountyEnabled;
/*      */   }
/*      */   
/*      */   public boolean getFactionQuestEnabled() {
/*  388 */     return this.factionQuestEnabled;
/*      */   }
/*      */   
/*      */   public String getFactionTag() {
/*  392 */     return this.factionTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getSkillMasterList() {
/*  400 */     return this.classTableIDs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMerchantFactionID() {
/*  408 */     return this.merchantFactionID;
/*      */   }
/*      */   
/*      */   public String getMerchantTableSetID() {
/*  412 */     return this.merchantTableSetID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getMerchantTableIDList() {
/*  420 */     return this.merchantTableIDs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getMerchantTableItemIDList() {
/*  428 */     return this.merchantItemIDs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPlayerBaseDex() {
/*  436 */     return this.playerBaseDex;
/*      */   }
/*      */   
/*      */   public int getPlayerBaseInt() {
/*  440 */     return this.playerBaseInt;
/*      */   }
/*      */   
/*      */   public int getPlayerBaseStr() {
/*  444 */     return this.playerBaseStr;
/*      */   }
/*      */   
/*      */   public int getPlayerBaseLife() {
/*  448 */     return this.playerBaseLife;
/*      */   }
/*      */   
/*      */   public int getPlayerBaseMana() {
/*  452 */     return this.playerBaseMana;
/*      */   }
/*      */   
/*      */   public List<DBEnginePlayerMasteries> getMasteryList() {
/*  456 */     return this.masteries;
/*      */   }
/*      */   
/*      */   public int getPlayerIncDex() {
/*  460 */     return this.playerIncDex;
/*      */   }
/*      */   
/*      */   public int getPlayerIncInt() {
/*  464 */     return this.playerIncInt;
/*      */   }
/*      */   
/*      */   public int getPlayerIncStr() {
/*  468 */     return this.playerIncStr;
/*      */   }
/*      */   
/*      */   public int getPlayerIncLife() {
/*  472 */     return this.playerIncLife;
/*      */   }
/*      */   
/*      */   public int getPlayerIncMana() {
/*  476 */     return this.playerIncMana;
/*      */   }
/*      */   
/*      */   public int getPlayerMaxLevel() {
/*  480 */     return this.playerMaxLevel;
/*      */   }
/*      */   
/*      */   public String getXPFormula() {
/*  484 */     return this.xpFormula;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DBEngineLevel.LevelStats> getLevelStatList() {
/*  492 */     return this.levelStats;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getConstellationNameTag() {
/*  500 */     return this.constellationNameTag;
/*      */   }
/*      */   
/*      */   public String getConstellationInfoTag() {
/*  504 */     return this.constellationInfoTag;
/*      */   }
/*      */   
/*      */   public List<DBConstellationAffinity> getConstellationAffinityList() {
/*  508 */     return this.constellationAffinities;
/*      */   }
/*      */   
/*      */   public List<DBConstellationStar> getConstellationStarList() {
/*  512 */     return this.constellationStars;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getShrineName() {
/*  520 */     return this.shrineName;
/*      */   }
/*      */   
/*      */   public boolean getShrineNormalDisabled() {
/*  524 */     return this.shrineNormalDisabled;
/*      */   }
/*      */   
/*      */   public boolean getShrineNormalLocked() {
/*  528 */     return this.shrineNormalLocked;
/*      */   }
/*      */   
/*      */   public boolean getShrineEpicDisabled() {
/*  532 */     return this.shrineEpicDisabled;
/*      */   }
/*      */   
/*      */   public boolean getShrineEpicLocked() {
/*  536 */     return this.shrineEpicLocked;
/*      */   }
/*      */   
/*      */   public boolean getShrineLegendaryDisabled() {
/*  540 */     return this.shrineLegendaryDisabled;
/*      */   }
/*      */   
/*      */   public boolean getShrineLegendaryLocked() {
/*  544 */     return this.shrineLegendaryLocked;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getButtonSkillID() {
/*  552 */     return this.buttonSkillID;
/*      */   }
/*      */   
/*      */   public int getPosX() {
/*  556 */     return this.posX;
/*      */   }
/*      */   
/*      */   public int getPosY() {
/*  560 */     return this.posY;
/*      */   }
/*      */   
/*      */   public int getOffsetX() {
/*  564 */     return this.offsetX;
/*      */   }
/*      */   
/*      */   public int getOffsetY() {
/*  568 */     return this.offsetY;
/*      */   }
/*      */   
/*      */   public boolean getCircularButton() {
/*  572 */     return this.circularButton;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMasteryBarID() {
/*  580 */     return this.masteryBarID;
/*      */   }
/*      */   
/*      */   public String getSkillPaneID() {
/*  584 */     return this.skillPaneID;
/*      */   }
/*      */   
/*      */   public String getMasteryBitmapID() {
/*  588 */     return this.masteryBitmapID;
/*      */   }
/*      */   
/*      */   public List<String> getSkillButtonList() {
/*  592 */     return this.skillButtons;
/*      */   }
/*      */   
/*      */   public String getTitleTag() {
/*  596 */     return this.titleTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DBSkillTreeAlloc> getMasterySkillList() {
/*  604 */     return this.masterySkillIDs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSkillBuffID() {
/*  612 */     return this.skillBuffID;
/*      */   }
/*      */   
/*      */   public String getSkillDescription() {
/*  616 */     return this.skillDescription;
/*      */   }
/*      */   
/*      */   public String getSkillNameTag() {
/*  620 */     return this.skillNameTag;
/*      */   }
/*      */   
/*      */   public String getSkillName() {
/*  624 */     return this.skillName;
/*      */   }
/*      */   
/*      */   public boolean isMastery() {
/*  628 */     return this.mastery;
/*      */   }
/*      */   
/*      */   public boolean isDevotion() {
/*  632 */     return this.devotion;
/*      */   }
/*      */   
/*      */   public int getSkillTier() {
/*  636 */     return this.skillTier;
/*      */   }
/*      */   
/*      */   public int getSkillMaxLevel() {
/*  640 */     return this.maxLevel;
/*      */   }
/*      */   
/*      */   public String getSkillBitmapDownID() {
/*  644 */     return this.bitmapDownID;
/*      */   }
/*      */   
/*      */   public String getSkillBitmapUpID() {
/*  648 */     return this.bitmapUpID;
/*      */   }
/*      */   
/*      */   public int getSkillLevel() {
/*  652 */     return this.skillLevel;
/*      */   }
/*      */   
/*      */   public int getSkillDuration() {
/*  656 */     return this.skillDuration;
/*      */   }
/*      */   
/*      */   public boolean hasSkillBonusIncrement() {
/*  660 */     return this.bonusIncrement;
/*      */   }
/*      */   
/*      */   public boolean hasSkillModifier() {
/*  664 */     return this.skillModified;
/*      */   }
/*      */   
/*      */   public boolean isDependencyAll() {
/*  668 */     return this.skillDependencyAll;
/*      */   }
/*      */   
/*      */   public List<DBSkillSpawn> getSpawnPetList() {
/*  672 */     return this.dbSpawnPets;
/*      */   }
/*      */   
/*      */   public List<Integer> getSkillXPLevelList() {
/*  676 */     return this.skillXPLevels;
/*      */   }
/*      */   
/*      */   public List<DBSkillDependency> getSkillDependencyList() {
/*  680 */     return this.skillDependencies;
/*      */   }
/*      */   
/*      */   public List<DBSkillConnector> getSkillConnectorList() {
/*  684 */     return this.skillConnections;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTriggerChance() {
/*  692 */     return this.triggerChance;
/*      */   }
/*      */   
/*      */   public String getTriggerType() {
/*  696 */     return this.triggerType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLootRandomCost() {
/*  704 */     return this.lootRandomCost;
/*      */   }
/*      */   
/*      */   public String getLootRandomName() {
/*  708 */     return this.lootRandomName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getConversionIn() {
/*  716 */     return this.convertIn;
/*      */   }
/*      */   
/*      */   public String getConversionOut() {
/*  720 */     return this.convertOut;
/*      */   }
/*      */   
/*      */   public String getConversionIn2() {
/*  724 */     return this.convertIn2;
/*      */   }
/*      */   
/*      */   public String getConversionOut2() {
/*  728 */     return this.convertOut2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getItemSkillID() {
/*  736 */     return this.itemSkillID;
/*      */   }
/*      */   
/*      */   public String getItemSkillLevelFormula() {
/*  740 */     return this.itemSkillLevelFormula;
/*      */   }
/*      */   
/*      */   public int getItemSkillLevel() {
/*  744 */     return this.itemSkillLevel;
/*      */   }
/*      */   
/*      */   public int getOffensiveChance() {
/*  748 */     return this.offensiveChance;
/*      */   }
/*      */   
/*      */   public String getPetBonusID() {
/*  752 */     return this.petBonusID;
/*      */   }
/*      */   
/*      */   public String getRarity() {
/*  756 */     return this.rarity;
/*      */   }
/*      */   
/*      */   public int getRequiredLevel() {
/*  760 */     return this.reqLevel;
/*      */   }
/*      */   
/*      */   public int getRetaliationChance() {
/*  764 */     return this.retaliationChance;
/*      */   }
/*      */   
/*      */   public int getRNGPercent() {
/*  768 */     return this.rngPercent;
/*      */   }
/*      */   
/*      */   public String getSkillControllerID() {
/*  772 */     return this.skillControllerID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DBAffixSet.DBEntry> getAffixSetRandomizerList() {
/*  780 */     return this.randomizers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getItemSetItemIDList() {
/*  788 */     return this.itemSetItemIDs;
/*      */   }
/*      */   
/*      */   public String getItemSetDescriptionTag() {
/*  792 */     return this.itemSetDescTag;
/*      */   }
/*      */   
/*      */   public int getItemSetSkillModifierLevel() {
/*  796 */     return this.skillModifierLevel;
/*      */   }
/*      */   
/*      */   public String getItemSetNameTag() {
/*  800 */     return this.itemSetNameTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getTableNormalPrefixSuffix() {
/*  808 */     return this.nPre_nSuf;
/*      */   }
/*      */   
/*      */   public boolean getTableNormalPrefixRareSuffix() {
/*  812 */     return this.nPre_rSuf;
/*      */   }
/*      */   
/*      */   public boolean getTableRarePrefixNormalSuffix() {
/*  816 */     return this.rPre_nSuf;
/*      */   }
/*      */   
/*      */   public boolean getTableRarePrefixSuffix() {
/*  820 */     return this.rPre_rSuf;
/*      */   }
/*      */   
/*      */   public List<DBLootTableItemAlloc> getTableItemAllocList() {
/*  824 */     return this.tableItems;
/*      */   }
/*      */   
/*      */   public List<DBLootTableAffixSetAlloc> getTableAffixSetAllocList() {
/*  828 */     return this.tableAffixSets;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DBLootTableSetAlloc> getTableSetAllocList() {
/*  836 */     return this.tableSetAllocs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DBFormula> getFormulaSetFormulaList() {
/*  844 */     return this.formulaSetFormulas;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getArmorClass() {
/*  852 */     return this.armorClass;
/*      */   }
/*      */   
/*      */   public String getArtifactClass() {
/*  856 */     return this.artifactClass;
/*      */   }
/*      */   
/*      */   public String getMeshID() {
/*  860 */     return this.meshID;
/*      */   }
/*      */   
/*      */   public String getBaseTextureID() {
/*  864 */     return this.baseTextureID;
/*      */   }
/*      */   
/*      */   public String getBumpTextureID() {
/*  868 */     return this.bumpTextureID;
/*      */   }
/*      */   
/*      */   public String getGlowTextureID() {
/*  872 */     return this.glowTextureID;
/*      */   }
/*      */   
/*      */   public String getShaderID() {
/*  876 */     return this.shaderID;
/*      */   }
/*      */   
/*      */   public String getBitmapID() {
/*  880 */     return this.bitmapID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getShardBitmapID() {
/*  888 */     return this.shardBitmapID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getItemNameTag() {
/*  896 */     return this.itemNameTag;
/*      */   }
/*      */   
/*      */   public int getGenderCode() {
/*  900 */     return this.genderCode;
/*      */   }
/*      */   
/*      */   public String getItemDescription() {
/*  904 */     return this.itemDescription;
/*      */   }
/*      */   
/*      */   public String getItemName() {
/*  908 */     return this.itemName;
/*      */   }
/*      */   
/*      */   public String getQualityTag() {
/*  912 */     return this.qualityTag;
/*      */   }
/*      */   
/*      */   public String getStyleTag() {
/*  916 */     return this.styleTag;
/*      */   }
/*      */   
/*      */   public String getItemSetID() {
/*  920 */     return this.itemSetID;
/*      */   }
/*      */   
/*      */   public String getArtifactID() {
/*  924 */     return this.artifactID;
/*      */   }
/*      */   
/*      */   public String getBonusAffixSetID() {
/*  928 */     return this.bonusAffixSetID;
/*      */   }
/*      */   
/*      */   public String getCostFormulaSetID() {
/*  932 */     return this.costFormulaSetID;
/*      */   }
/*      */   
/*      */   public boolean isSoulbound() {
/*  936 */     return this.soulbound;
/*      */   }
/*      */   
/*      */   public boolean isHidePrefix() {
/*  940 */     return this.hidePrefix;
/*      */   }
/*      */   
/*      */   public boolean isHideSuffix() {
/*  944 */     return this.hideSuffix;
/*      */   }
/*      */   
/*      */   public boolean isQuestItem() {
/*  948 */     return this.questItem;
/*      */   }
/*      */   
/*      */   public boolean isCannotPickup() {
/*  952 */     return this.cannotPickup;
/*      */   }
/*      */   
/*      */   public int getItemLevel() {
/*  956 */     return this.itemLevel;
/*      */   }
/*      */   
/*      */   public int getPlusAllSkills() {
/*  960 */     return this.plusAllSkills;
/*      */   }
/*      */   
/*      */   public int getComponentPieces() {
/*  964 */     return this.componentPieces;
/*      */   }
/*      */   
/*      */   public int getMaxStackSize() {
/*  968 */     return this.maxStackSize;
/*      */   }
/*      */   
/*      */   public List<DBSkillModifier> getSkillModifierList() {
/*  972 */     return this.skillModifiers;
/*      */   }
/*      */   
/*      */   public void addModifierSkillID(int index, String skillID) {
/*  976 */     boolean found = false;
/*      */     
/*  978 */     for (DBSkillModifier modifier : this.skillModifiers) {
/*  979 */       if (modifier.getIndex() == index) {
/*  980 */         modifier.setSkillID(skillID);
/*      */         
/*  982 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/*  988 */     if (!found) {
/*  989 */       DBSkillModifier modifier = new DBSkillModifier();
/*      */       
/*  991 */       modifier.setIndex(index);
/*  992 */       modifier.setSkillID(skillID);
/*      */       
/*  994 */       this.skillModifiers.add(modifier);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addModifierModifierID(int index, String modifierID) {
/*  999 */     boolean found = false;
/*      */     
/* 1001 */     for (DBSkillModifier modifier : this.skillModifiers) {
/* 1002 */       if (modifier.getIndex() == index) {
/* 1003 */         modifier.setModifierID(modifierID);
/*      */         
/* 1005 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1011 */     if (!found) {
/* 1012 */       DBSkillModifier modifier = new DBSkillModifier();
/*      */       
/* 1014 */       modifier.setIndex(index);
/* 1015 */       modifier.setModifierID(modifierID);
/*      */       
/* 1017 */       this.skillModifiers.add(modifier);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemSlots getItemSlots() {
/* 1026 */     return this.slots;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPetFormulaLevel() {
/* 1034 */     return this.petFormulaLevel;
/*      */   }
/*      */   
/*      */   public String getPetBioID() {
/* 1038 */     return this.petBioID;
/*      */   }
/*      */   
/*      */   public String getPetDieSkillID() {
/* 1042 */     return this.petDieSkillID;
/*      */   }
/*      */   
/*      */   public List<DBPetSkill> getPetSkillList() {
/* 1046 */     return this.petSkills;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPetName() {
/* 1054 */     return this.petName;
/*      */   }
/*      */   
/*      */   public String getBioFormulaLife() {
/* 1058 */     return this.bioFormulaLife;
/*      */   }
/*      */   
/*      */   public String getBioFormulaMana() {
/* 1062 */     return this.bioFormulaMana;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFileDescription(String filedesc) {
/* 1070 */     this.fileDesc = filedesc;
/*      */   }
/*      */   
/*      */   public void setFileName(String fileName) {
/* 1074 */     this.fileName = fileName;
/*      */   }
/*      */   
/*      */   public void setRecordClass(String recordClass) {
/* 1078 */     this.recordClass = recordClass;
/*      */   }
/*      */   
/*      */   public void setTemplate(String template) {
/* 1082 */     this.template = template;
/*      */     
/* 1084 */     if (template != null) {
/* 1085 */       this.mastery = template.equals("database/templates/skill_mastery.tpl");
/*      */     }
/*      */   }
/*      */   
/*      */   public void setDLCRequirement(String dlcRequirement) {
/* 1090 */     dlcRequirement = this.dlcRequirement;
/*      */   }
/*      */   
/*      */   public void setDBFormulaSet(DBFormulaSet dbFormulaSet) {
/* 1094 */     this.dbFormulaSet = dbFormulaSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addFactionReputationStateTag(int index, String stateTag) {
/* 1102 */     boolean found = false;
/*      */     
/* 1104 */     for (DBFactionReputation reputation : this.factionReputations) {
/* 1105 */       if (reputation.getIndex() == index) {
/* 1106 */         reputation.setStateTag(stateTag);
/*      */         
/* 1108 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1114 */     if (!found) {
/* 1115 */       DBFactionReputation reputation = new DBFactionReputation();
/*      */       
/* 1117 */       reputation.setIndex(index);
/* 1118 */       reputation.setStateTag(stateTag);
/*      */       
/* 1120 */       this.factionReputations.add(reputation);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addFactionReputationValue(int index, int value) {
/* 1125 */     boolean found = false;
/*      */     
/* 1127 */     for (DBFactionReputation reputation : this.factionReputations) {
/* 1128 */       if (reputation.getIndex() == index) {
/* 1129 */         reputation.setValue(value);
/*      */         
/* 1131 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1137 */     if (!found) {
/* 1138 */       DBFactionReputation reputation = new DBFactionReputation();
/*      */       
/* 1140 */       reputation.setIndex(index);
/* 1141 */       reputation.setValue(value);
/*      */       
/* 1143 */       this.factionReputations.add(reputation);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addMasteryTier(int index, int value) {
/* 1148 */     DBEngineMasteryTier mt = new DBEngineMasteryTier(index, value);
/*      */     
/* 1150 */     this.masteryTiers.add(mt);
/*      */   }
/*      */   
/*      */   public void setFactionAltNeutralTag(String factionAltNeutralTag) {
/* 1154 */     this.factionAltNeutralTag = factionAltNeutralTag;
/*      */   }
/*      */   
/*      */   public void setDevotionCap(int devotionCap) {
/* 1158 */     this.devotionCap = devotionCap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFactionBountyEnabled(boolean factionBountyEnabled) {
/* 1166 */     this.factionBountyEnabled = factionBountyEnabled;
/*      */   }
/*      */   
/*      */   public void setFactionQuestEnabled(boolean factionQuestEnabled) {
/* 1170 */     this.factionQuestEnabled = factionQuestEnabled;
/*      */   }
/*      */   
/*      */   public void setFactionTag(String factionTag) {
/* 1174 */     this.factionTag = factionTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSkillMaster(String classTableID) {
/* 1182 */     this.classTableIDs.add(classTableID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMerchantFactionID(String merchantFactionID) {
/* 1190 */     this.merchantFactionID = merchantFactionID;
/*      */   }
/*      */   
/*      */   public void setMerchantTableSetID(String merchantTableSetID) {
/* 1194 */     this.merchantTableSetID = merchantTableSetID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMerchantTableSetTableID(String tableID) {
/* 1202 */     this.merchantTableIDs.add(tableID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMerchantTableItemID(String itemID) {
/* 1210 */     this.merchantItemIDs.add(itemID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlayerBaseDex(int playerBaseDex) {
/* 1218 */     this.playerBaseDex = playerBaseDex;
/*      */   }
/*      */   
/*      */   public void setPlayerBaseInt(int playerBaseInt) {
/* 1222 */     this.playerBaseInt = playerBaseInt;
/*      */   }
/*      */   
/*      */   public void setPlayerBaseStr(int playerBaseStr) {
/* 1226 */     this.playerBaseStr = playerBaseStr;
/*      */   }
/*      */   
/*      */   public void setPlayerBaseLife(int playerBaseLife) {
/* 1230 */     this.playerBaseLife = playerBaseLife;
/*      */   }
/*      */   
/*      */   public void setPlayerBaseMana(int playerBaseMana) {
/* 1234 */     this.playerBaseMana = playerBaseMana;
/*      */   }
/*      */   
/*      */   public void addMastery(String tag, String masteryID) {
/* 1238 */     String s = tag.substring("skillTree".length());
/*      */     
/* 1240 */     int index = 0;
/*      */     try {
/* 1242 */       index = Integer.parseInt(s);
/*      */     }
/* 1244 */     catch (NumberFormatException ex) {
/*      */       return;
/*      */     } 
/*      */     
/* 1248 */     if (!DBEnginePlayerMasteries.containsSkillTreeID(this.masteries, masteryID)) {
/* 1249 */       this.masteries.add(new DBEnginePlayerMasteries(masteryID, index));
/*      */     }
/*      */   }
/*      */   
/*      */   public void setPlayerIncDex(int playerIncDex) {
/* 1254 */     this.playerIncDex = playerIncDex;
/*      */   }
/*      */   
/*      */   public void setPlayerIncInt(int playerIncInt) {
/* 1258 */     this.playerIncInt = playerIncInt;
/*      */   }
/*      */   
/*      */   public void setPlayerIncStr(int playerIncStr) {
/* 1262 */     this.playerIncStr = playerIncStr;
/*      */   }
/*      */   
/*      */   public void setPlayerIncLife(int playerIncLife) {
/* 1266 */     this.playerIncLife = playerIncLife;
/*      */   }
/*      */   
/*      */   public void setPlayerIncMana(int playerIncMana) {
/* 1270 */     this.playerIncMana = playerIncMana;
/*      */   }
/*      */   
/*      */   public void setPlayerMaxLevel(int playerMaxLevel) {
/* 1274 */     this.playerMaxLevel = playerMaxLevel;
/*      */   }
/*      */   
/*      */   public void setXPFormula(String xpFormula) {
/* 1278 */     this.xpFormula = xpFormula;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addLevelStatPoints(int points, int index) {
/* 1286 */     DBEngineLevel.LevelStats stats = null;
/*      */     
/* 1288 */     int skillPoints = 0;
/* 1289 */     for (DBEngineLevel.LevelStats ls : this.levelStats) {
/* 1290 */       int p = ls.getSkillPoints();
/* 1291 */       if (p != 0) {
/* 1292 */         skillPoints = p;
/*      */       }
/*      */       
/* 1295 */       if (stats.getLevel() == index) {
/* 1296 */         stats = ls;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1302 */     boolean statExists = false;
/* 1303 */     if (stats == null) {
/* 1304 */       statExists = false;
/*      */     } else {
/* 1306 */       statExists = (stats.getLevel() == index);
/*      */     } 
/*      */     
/* 1309 */     if (statExists) {
/* 1310 */       stats.setStatPoints(points);
/*      */     } else {
/* 1312 */       stats = new DBEngineLevel.LevelStats();
/* 1313 */       stats.setLevel(index);
/* 1314 */       stats.setStatPoints(points);
/* 1315 */       stats.setSkillPoints(skillPoints);
/*      */       
/* 1317 */       this.levelStats.add(stats);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addLevelSkillPoints(int points, int index) {
/* 1322 */     DBEngineLevel.LevelStats stats = null;
/*      */     
/* 1324 */     int statPoints = 0;
/* 1325 */     for (DBEngineLevel.LevelStats ls : this.levelStats) {
/* 1326 */       int p = ls.getStatPoints();
/* 1327 */       if (p != 0) {
/* 1328 */         statPoints = p;
/*      */       }
/*      */       
/* 1331 */       if (ls.getLevel() == index) {
/* 1332 */         stats = ls;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1338 */     boolean statExists = false;
/* 1339 */     if (stats == null) {
/* 1340 */       statExists = false;
/*      */     } else {
/* 1342 */       statExists = (stats.getLevel() == index);
/*      */     } 
/*      */     
/* 1345 */     if (statExists) {
/* 1346 */       stats.setSkillPoints(points);
/*      */     } else {
/* 1348 */       stats = new DBEngineLevel.LevelStats();
/* 1349 */       stats.setLevel(index);
/* 1350 */       stats.setStatPoints(statPoints);
/* 1351 */       stats.setSkillPoints(points);
/*      */       
/* 1353 */       this.levelStats.add(stats);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConstellationNameTag(String constellationNameTag) {
/* 1362 */     this.constellationNameTag = constellationNameTag;
/*      */   }
/*      */   
/*      */   public void getConstellationInfoTag(String constellationInfoTag) {
/* 1366 */     this.constellationInfoTag = constellationInfoTag;
/*      */   }
/*      */   
/*      */   public void addConstellationAffinityName(String tag, String name) {
/* 1370 */     boolean found = false;
/*      */     
/* 1372 */     boolean required = tag.startsWith("affinityRequired");
/* 1373 */     int index = 0;
/* 1374 */     String s = null;
/*      */     
/* 1376 */     if (required) {
/* 1377 */       s = tag.substring("affinityRequiredName".length());
/*      */     } else {
/* 1379 */       s = tag.substring("affinityGivenName".length());
/*      */     } 
/*      */     
/*      */     try {
/* 1383 */       index = Integer.parseInt(s);
/*      */     }
/* 1385 */     catch (NumberFormatException ex) {
/*      */       return;
/*      */     } 
/*      */     
/* 1389 */     for (DBConstellationAffinity dbAffinity : this.constellationAffinities) {
/* 1390 */       if (dbAffinity.getIndex() == index && dbAffinity
/* 1391 */         .isRequired() == required) {
/* 1392 */         dbAffinity.setAffinity(name);
/*      */         
/* 1394 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1400 */     if (!found) {
/* 1401 */       DBConstellationAffinity dbAffinity = new DBConstellationAffinity();
/*      */       
/* 1403 */       dbAffinity.setIndex(index);
/* 1404 */       dbAffinity.setRequired(required);
/* 1405 */       dbAffinity.setAffinity(name);
/*      */       
/* 1407 */       this.constellationAffinities.add(dbAffinity);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addConstellationAffinityPoints(String tag, int points) {
/* 1412 */     boolean found = false;
/*      */     
/* 1414 */     boolean required = tag.startsWith("affinityRequired");
/* 1415 */     int index = 0;
/* 1416 */     String s = null;
/*      */     
/* 1418 */     if (required) {
/* 1419 */       s = tag.substring("affinityRequired".length());
/*      */     } else {
/* 1421 */       s = tag.substring("affinityGiven".length());
/*      */     } 
/*      */     
/*      */     try {
/* 1425 */       index = Integer.parseInt(s);
/*      */     }
/* 1427 */     catch (NumberFormatException ex) {
/*      */       return;
/*      */     } 
/*      */     
/* 1431 */     for (DBConstellationAffinity dbAffinity : this.constellationAffinities) {
/* 1432 */       if (dbAffinity.getIndex() == index && dbAffinity
/* 1433 */         .isRequired() == required) {
/* 1434 */         dbAffinity.setPoints(points);
/*      */         
/* 1436 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1442 */     if (!found) {
/* 1443 */       DBConstellationAffinity dbAffinity = new DBConstellationAffinity();
/*      */       
/* 1445 */       dbAffinity.setIndex(index);
/* 1446 */       dbAffinity.setRequired(required);
/* 1447 */       dbAffinity.setPoints(points);
/*      */       
/* 1449 */       this.constellationAffinities.add(dbAffinity);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addConstellationStar(String tag, String skillID) {
/* 1454 */     int index = 0;
/* 1455 */     String s = null;
/*      */     
/* 1457 */     s = tag.substring("devotionButton".length());
/*      */     
/*      */     try {
/* 1460 */       index = Integer.parseInt(s);
/*      */     }
/* 1462 */     catch (NumberFormatException ex) {
/*      */       return;
/*      */     } 
/*      */     
/* 1466 */     DBConstellationStar dbStar = new DBConstellationStar();
/*      */     
/* 1468 */     dbStar.setIndex(index);
/* 1469 */     dbStar.setButtonID(skillID);
/*      */     
/* 1471 */     this.constellationStars.add(dbStar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShrineName(String shrineNameTag) {
/* 1479 */     this.shrineName = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_SHRINES, shrineNameTag, false);
/*      */   }
/*      */   
/*      */   public void setShrineNormalDisabled(boolean shrineNormalDisabled) {
/* 1483 */     this.shrineNormalDisabled = shrineNormalDisabled;
/*      */   }
/*      */   
/*      */   public void setShrineNormalLocked(boolean shrineNormalLocked) {
/* 1487 */     this.shrineNormalLocked = shrineNormalLocked;
/*      */   }
/*      */   
/*      */   public void setShrineEpicDisabled(boolean shrineEpicDisabled) {
/* 1491 */     this.shrineEpicDisabled = shrineEpicDisabled;
/*      */   }
/*      */   
/*      */   public void setShrineEpicLocked(boolean shrineEpicLocked) {
/* 1495 */     this.shrineEpicLocked = shrineEpicLocked;
/*      */   }
/*      */   
/*      */   public void setShrineLegendaryDisabled(boolean shrineLegendaryDisabled) {
/* 1499 */     this.shrineLegendaryDisabled = shrineLegendaryDisabled;
/*      */   }
/*      */   
/*      */   public void setShrineLegendaryLocked(boolean shrineLegendaryLocked) {
/* 1503 */     this.shrineLegendaryLocked = shrineLegendaryLocked;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setButtonSkillID(String buttonSkillID) {
/* 1511 */     this.buttonSkillID = buttonSkillID;
/*      */   }
/*      */   
/*      */   public void setPosX(int posX) {
/* 1515 */     this.posX = posX;
/*      */   }
/*      */   
/*      */   public void setPosY(int posY) {
/* 1519 */     this.posY = posY;
/*      */   }
/*      */   
/*      */   public void setOffsetX(int offsetX) {
/* 1523 */     this.offsetX = offsetX;
/*      */   }
/*      */   
/*      */   public void setOffsetY(int offsetY) {
/* 1527 */     this.offsetY = offsetY;
/*      */   }
/*      */   
/*      */   public void setCircularButton(boolean circularButton) {
/* 1531 */     this.circularButton = circularButton;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMasteryBarID(String masteryBarID) {
/* 1539 */     this.masteryBarID = masteryBarID;
/*      */   }
/*      */   
/*      */   public void setSkillPaneID(String skillPaneID) {
/* 1543 */     this.skillPaneID = skillPaneID;
/*      */   }
/*      */   
/*      */   public void setMasteryBitmapID(String masteryBitmapID) {
/* 1547 */     this.masteryBitmapID = masteryBitmapID;
/*      */   }
/*      */   
/*      */   public void addSkillButton(String skillButton) {
/* 1551 */     this.skillButtons.add(skillButton);
/*      */   }
/*      */   
/*      */   public void setTitleTag(String titleTag) {
/* 1555 */     this.titleTag = titleTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMasterySkill(String skillID, int index) {
/* 1563 */     DBSkillTreeAlloc alloc = new DBSkillTreeAlloc(skillID, index);
/*      */     
/* 1565 */     this.masterySkillIDs.add(alloc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSkillBuffID(String skillBuffID) {
/* 1573 */     this.skillBuffID = skillBuffID;
/*      */   }
/*      */   
/*      */   public void setSkillDescription(String skillBaseDescription) {
/* 1577 */     this.skillDescription = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_SKILLS, skillBaseDescription, true);
/*      */   }
/*      */   
/*      */   public void setDevotion(boolean devotion) {
/* 1581 */     this.devotion = devotion;
/*      */   }
/*      */   
/*      */   public void setSkillTier(int skillTier) {
/* 1585 */     this.skillTier = skillTier;
/*      */   }
/*      */   
/*      */   public void setSkillMaxLevel(int maxLevel) {
/* 1589 */     this.maxLevel = maxLevel;
/*      */   }
/*      */   
/*      */   public void setSkillBitmapDownID(String bitmapDownID) {
/* 1593 */     this.bitmapDownID = bitmapDownID;
/*      */   }
/*      */   
/*      */   public void setSkillBitmapUpID(String bitmapUpID) {
/* 1597 */     this.bitmapUpID = bitmapUpID;
/*      */   }
/*      */   
/*      */   public void setSkillLevel(int skillLevel) {
/* 1601 */     this.skillLevel = skillLevel;
/*      */   }
/*      */   
/*      */   public void setSkillDuration(int skillDuration) {
/* 1605 */     this.skillDuration = skillDuration;
/*      */   }
/*      */   
/*      */   public void setSkillBonusIncrement(boolean bonusIncrement) {
/* 1609 */     this.bonusIncrement = bonusIncrement;
/*      */   }
/*      */   
/*      */   public void setSkillModified(boolean skillModified) {
/* 1613 */     this.skillModified = skillModified;
/*      */   }
/*      */   
/*      */   public void setDependencyAll(boolean skillDependencyAll) {
/* 1617 */     this.skillDependencyAll = skillDependencyAll;
/*      */   }
/*      */   
/*      */   public void addSpawnPet(int index, String petID) {
/* 1621 */     DBSkillSpawn pet = new DBSkillSpawn(index, petID);
/*      */     
/* 1623 */     this.dbSpawnPets.add(pet);
/*      */   }
/*      */   
/*      */   public void addSkillXPLevel(String xp) {
/*      */     try {
/* 1628 */       Integer i = Integer.valueOf(xp);
/*      */       
/* 1630 */       this.skillXPLevels.add(i);
/*      */       
/* 1632 */       if (i.intValue() > 0) this.devotion = true;
/*      */     
/* 1634 */     } catch (NumberFormatException numberFormatException) {}
/*      */   }
/*      */   
/*      */   public void addSkillConnectionOff(String connector, int index) {
/* 1638 */     boolean found = false;
/*      */     
/* 1640 */     for (DBSkillConnector connection : this.skillConnections) {
/* 1641 */       if (connection.getIndex() == index) {
/* 1642 */         found = true;
/*      */         
/* 1644 */         connection.setConnectionOffID(connector);
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1650 */     if (!found) {
/* 1651 */       DBSkillConnector connection = new DBSkillConnector(index, connector, null);
/*      */       
/* 1653 */       this.skillConnections.add(connection);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addSkillConnectionOn(String connector, int index) {
/* 1658 */     boolean found = false;
/*      */     
/* 1660 */     for (DBSkillConnector connection : this.skillConnections) {
/* 1661 */       if (connection.getIndex() == index) {
/* 1662 */         found = true;
/*      */         
/* 1664 */         connection.setConnectionOnID(connector);
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1670 */     if (!found) {
/* 1671 */       DBSkillConnector connection = new DBSkillConnector(index, null, connector);
/*      */       
/* 1673 */       this.skillConnections.add(connection);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addSkillDependency(String skillDep) {
/* 1678 */     DBSkillDependency dep = new DBSkillDependency(null, skillDep);
/*      */     
/* 1680 */     this.skillDependencies.add(dep);
/*      */   }
/*      */   
/*      */   public void setSkillNameTag(String skillNameTag) {
/* 1684 */     this.skillNameTag = skillNameTag;
/*      */     
/* 1686 */     if (skillNameTag != null) {
/* 1687 */       this.skillName = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_SKILLS, skillNameTag, false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTriggerChance(int triggerChance) {
/* 1696 */     this.triggerChance = triggerChance;
/*      */   }
/*      */   
/*      */   public void setTriggerType(String triggerType) {
/* 1700 */     this.triggerType = triggerType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLootRandomCost(int lootRandomCost) {
/* 1708 */     this.lootRandomCost = lootRandomCost;
/*      */   }
/*      */   
/*      */   public void setLootRandomName(String lootRandomName) {
/* 1712 */     this.lootRandomName = lootRandomName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConversionIn(String convertIn) {
/* 1720 */     if (convertIn == null)
/*      */       return; 
/* 1722 */     if (convertIn.equals("Physical;Pierce;Elemental;Cold;Fire;Poison;Lightning;Life;Chaos;Aether;Stun"))
/*      */       return; 
/* 1724 */     this.convertIn = convertIn;
/*      */   }
/*      */   
/*      */   public void setConversionOut(String convertOut) {
/* 1728 */     if (convertOut == null)
/*      */       return; 
/* 1730 */     if (convertOut.equals("Physical;Pierce;Elemental;Cold;Fire;Poison;Lightning;Life;Chaos;Aether;Stun"))
/*      */       return; 
/* 1732 */     this.convertOut = convertOut;
/*      */   }
/*      */   
/*      */   public void setConversionIn2(String convertIn) {
/* 1736 */     if (convertIn == null)
/*      */       return; 
/* 1738 */     if (convertIn.equals("Physical;Pierce;Elemental;Cold;Fire;Poison;Lightning;Life;Chaos;Aether;Stun"))
/*      */       return; 
/* 1740 */     this.convertIn2 = convertIn;
/*      */   }
/*      */   
/*      */   public void setConversionOut2(String convertOut) {
/* 1744 */     if (convertOut == null)
/*      */       return; 
/* 1746 */     if (convertOut.equals("Physical;Pierce;Elemental;Cold;Fire;Poison;Lightning;Life;Chaos;Aether;Stun"))
/*      */       return; 
/* 1748 */     this.convertOut2 = convertOut;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRace(String tag, String name) {
/* 1756 */     boolean found = false;
/*      */     
/* 1758 */     for (DBStatBonusRace race : this.dbStatBonusRaces) {
/* 1759 */       if (race.getRaceTag().equals(tag)) {
/* 1760 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1766 */     if (!found) {
/* 1767 */       DBStatBonusRace race = new DBStatBonusRace();
/*      */       
/* 1769 */       race.setID(this.fileName);
/* 1770 */       race.setRaceTag(tag);
/* 1771 */       race.setRaceName(name);
/*      */       
/* 1773 */       this.dbStatBonusRaces.add(race);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setItemSkillID(String itemSkillID) {
/* 1778 */     this.itemSkillID = itemSkillID;
/*      */   }
/*      */   
/*      */   public void setItemSkillLevelFormula(String itemSkillLevelFormula) {
/* 1782 */     this.itemSkillLevelFormula = itemSkillLevelFormula;
/*      */   }
/*      */   
/*      */   public void setItemSkillLevel(int itemSkillLevel) {
/* 1786 */     this.itemSkillLevel = itemSkillLevel;
/*      */   }
/*      */   
/*      */   public void setOffensiveChance(int offensiveChance) {
/* 1790 */     this.offensiveChance = offensiveChance;
/*      */   }
/*      */   
/*      */   public void setPetBonusID(String petBonusID) {
/* 1794 */     this.petBonusID = petBonusID;
/*      */   }
/*      */   
/*      */   public void setRarity(String rarity) {
/* 1798 */     if (rarity == null)
/*      */       return; 
/* 1800 */     if (rarity.equals("Common;Magical;Rare;Epic;Legendary;Broken;"))
/* 1801 */       return;  if (rarity.equals("Common;Magical;Rare;Epic;Legendary;Quest"))
/*      */       return; 
/* 1803 */     this.rarity = rarity;
/*      */   }
/*      */   
/*      */   public void setRequiredLevel(int reqLevel) {
/* 1807 */     this.reqLevel = reqLevel;
/*      */   }
/*      */   
/*      */   public void setRetaliationChance(int retaliationChance) {
/* 1811 */     this.retaliationChance = retaliationChance;
/*      */   }
/*      */   
/*      */   public void setRNGPercent(int rngPercent) {
/* 1815 */     this.rngPercent = rngPercent;
/*      */   }
/*      */   
/*      */   public void setSkillControllerID(String skillControllerID) {
/* 1819 */     this.skillControllerID = skillControllerID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRandomizerAffixIDEntry(int index, String affixID) {
/* 1827 */     boolean found = false;
/*      */     
/* 1829 */     for (DBAffixSet.DBEntry entry : this.randomizers) {
/* 1830 */       if (entry.getIndex() == index) {
/* 1831 */         entry.setAffixID(affixID);
/*      */         
/* 1833 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1839 */     if (!found) {
/* 1840 */       DBAffixSet.DBEntry entry = new DBAffixSet.DBEntry();
/*      */       
/* 1842 */       entry.setIndex(index);
/* 1843 */       entry.setAffixID(affixID);
/*      */       
/* 1845 */       this.randomizers.add(entry);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addRandomizerMaxLevelEntry(int index, int level) {
/* 1850 */     boolean found = false;
/*      */     
/* 1852 */     for (DBAffixSet.DBEntry entry : this.randomizers) {
/* 1853 */       if (entry.getIndex() == index) {
/* 1854 */         entry.setMaxLevel(level);
/*      */         
/* 1856 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1862 */     if (!found) {
/* 1863 */       DBAffixSet.DBEntry entry = new DBAffixSet.DBEntry();
/*      */       
/* 1865 */       entry.setIndex(index);
/* 1866 */       entry.setMaxLevel(level);
/*      */       
/* 1868 */       this.randomizers.add(entry);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addRandomizerMinLevelEntry(int index, int level) {
/* 1873 */     boolean found = false;
/*      */     
/* 1875 */     for (DBAffixSet.DBEntry entry : this.randomizers) {
/* 1876 */       if (entry.getIndex() == index) {
/* 1877 */         entry.setMinLevel(level);
/*      */         
/* 1879 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1885 */     if (!found) {
/* 1886 */       DBAffixSet.DBEntry entry = new DBAffixSet.DBEntry();
/*      */       
/* 1888 */       entry.setIndex(index);
/* 1889 */       entry.setMinLevel(level);
/*      */       
/* 1891 */       this.randomizers.add(entry);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addItemSetItemID(String itemID) {
/* 1900 */     this.itemSetItemIDs.add(itemID);
/*      */   }
/*      */   
/*      */   public void setItemSetDescriptionTag(String itemSetDescTag) {
/* 1904 */     this.itemSetDescTag = itemSetDescTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemSetSkillModifierLevel(int value, int index) {
/* 1911 */     if (value == 1 && 
/* 1912 */       this.skillModifierLevel == 0) this.skillModifierLevel = index;
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemSetNameTag(String itemSetNameTag) {
/* 1921 */     this.itemSetNameTag = itemSetNameTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addTableItemID(String itemID) {
/* 1929 */     DBLootTableItemAlloc alloc = new DBLootTableItemAlloc(this.fileName, itemID);
/* 1930 */     this.tableItems.add(alloc);
/*      */   }
/*      */   
/*      */   public void addTableAffixSetID(int index, int type, String affixSetID) {
/* 1934 */     boolean found = false;
/*      */     
/* 1936 */     for (DBLootTableAffixSetAlloc affixSet : this.tableAffixSets) {
/* 1937 */       if (affixSet.getIndex() == index && affixSet
/* 1938 */         .getAffixType() == type) {
/* 1939 */         affixSet.setAffixSetID(affixSetID);
/*      */         
/* 1941 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1947 */     if (!found) {
/* 1948 */       DBLootTableAffixSetAlloc affixSet = new DBLootTableAffixSetAlloc();
/*      */       
/* 1950 */       affixSet.setIndex(index);
/* 1951 */       affixSet.setAffixType(type);
/* 1952 */       affixSet.setAffixSetID(affixSetID);
/*      */       
/* 1954 */       this.tableAffixSets.add(affixSet);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addTableMaxLevel(int index, int type, int levelMax) {
/* 1959 */     boolean found = false;
/*      */     
/* 1961 */     for (DBLootTableAffixSetAlloc affixSet : this.tableAffixSets) {
/* 1962 */       if (affixSet.getIndex() == index && affixSet
/* 1963 */         .getAffixType() == type) {
/* 1964 */         affixSet.setMaxLevel(levelMax);
/*      */         
/* 1966 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1972 */     if (!found) {
/* 1973 */       DBLootTableAffixSetAlloc affixSet = new DBLootTableAffixSetAlloc();
/*      */       
/* 1975 */       affixSet.setIndex(index);
/* 1976 */       affixSet.setAffixType(type);
/* 1977 */       affixSet.setMaxLevel(levelMax);
/*      */       
/* 1979 */       this.tableAffixSets.add(affixSet);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addTableMinLevel(int index, int type, int levelMin) {
/* 1984 */     boolean found = false;
/*      */     
/* 1986 */     for (DBLootTableAffixSetAlloc affixSet : this.tableAffixSets) {
/* 1987 */       if (affixSet.getIndex() == index && affixSet
/* 1988 */         .getAffixType() == type) {
/* 1989 */         affixSet.setMinLevel(levelMin);
/*      */         
/* 1991 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1997 */     if (!found) {
/* 1998 */       DBLootTableAffixSetAlloc affixSet = new DBLootTableAffixSetAlloc();
/*      */       
/* 2000 */       affixSet.setIndex(index);
/* 2001 */       affixSet.setAffixType(type);
/* 2002 */       affixSet.setMinLevel(levelMin);
/*      */       
/* 2004 */       this.tableAffixSets.add(affixSet);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setTableNormalPrefixSuffix(boolean nPre_nSuf) {
/* 2009 */     this.nPre_nSuf = nPre_nSuf;
/*      */   }
/*      */   
/*      */   public void setTableNormalPrefixRareSuffix(boolean nPre_rSuf) {
/* 2013 */     this.nPre_rSuf = nPre_rSuf;
/*      */   }
/*      */   
/*      */   public void setTableRarePrefixNormalSuffix(boolean rPre_nSuf) {
/* 2017 */     this.rPre_nSuf = rPre_nSuf;
/*      */   }
/*      */   
/*      */   public void setTableRarePrefixSuffix(boolean rPre_rSuf) {
/* 2021 */     this.rPre_rSuf = rPre_rSuf;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addTableSetTableID(String tableID) {
/* 2029 */     boolean found = false;
/*      */     
/* 2031 */     for (DBLootTableSetAlloc entry : this.tableSetAllocs) {
/* 2032 */       if (entry.getTableID() == null) {
/* 2033 */         entry.setTableID(tableID);
/*      */         
/* 2035 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 2041 */     if (!found) {
/* 2042 */       DBLootTableSetAlloc entry = new DBLootTableSetAlloc();
/*      */       
/* 2044 */       entry.setTableID(tableID);
/*      */       
/* 2046 */       this.tableSetAllocs.add(entry);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addTableSetMinLevel(int levelMin) {
/* 2051 */     boolean found = false;
/*      */     
/* 2053 */     for (DBLootTableSetAlloc entry : this.tableSetAllocs) {
/* 2054 */       if (entry.getMinLevel() == -1) {
/* 2055 */         entry.setMinLevel(levelMin);
/*      */         
/* 2057 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 2063 */     if (!found) {
/* 2064 */       DBLootTableSetAlloc entry = new DBLootTableSetAlloc();
/*      */       
/* 2066 */       entry.setMinLevel(levelMin);
/*      */       
/* 2068 */       this.tableSetAllocs.add(entry);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addFormulaSetFormula(String formulaID, String formula) {
/* 2077 */     if (formula == null)
/* 2078 */       return;  if (formula.isEmpty())
/*      */       return; 
/* 2080 */     DBFormula dbf = new DBFormula();
/*      */     
/* 2082 */     dbf.setFormulaID(formulaID);
/* 2083 */     dbf.setFormula(formula);
/*      */     
/* 2085 */     this.formulaSetFormulas.add(dbf);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setArmorClass(String armorClass) {
/* 2093 */     if (armorClass == null)
/*      */       return; 
/* 2095 */     if (armorClass.equals("Caster;Light;Heavy;"))
/*      */       return; 
/* 2097 */     this.armorClass = armorClass;
/*      */   }
/*      */   
/*      */   public void setArtifactClass(String artifactClass) {
/* 2101 */     if (artifactClass == null)
/*      */       return; 
/* 2103 */     if (artifactClass.equals("Lesser;Greater;Divine;"))
/*      */       return; 
/* 2105 */     this.artifactClass = artifactClass;
/*      */   }
/*      */   
/*      */   public void setMeshID(String meshID) {
/* 2109 */     this.meshID = meshID;
/*      */   }
/*      */   
/*      */   public void setBaseTextureID(String baseTextureID) {
/* 2113 */     this.baseTextureID = baseTextureID;
/*      */   }
/*      */   
/*      */   public void addBaseTextureID(String baseTextureID) {
/* 2117 */     if (this.baseTextureID == null) {
/* 2118 */       this.baseTextureID = baseTextureID;
/*      */     } else {
/* 2120 */       this.baseTextureID += ";" + baseTextureID;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setBumpTextureID(String bumpTextureID) {
/* 2125 */     this.bumpTextureID = bumpTextureID;
/*      */   }
/*      */   
/*      */   public void addBumpTextureID(String bumpTextureID) {
/* 2129 */     if (this.bumpTextureID == null) {
/* 2130 */       this.bumpTextureID = bumpTextureID;
/*      */     } else {
/* 2132 */       this.bumpTextureID += ";" + bumpTextureID;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setGlowTextureID(String glowTextureID) {
/* 2137 */     this.glowTextureID = glowTextureID;
/*      */   }
/*      */   
/*      */   public void addGlowTextureID(String glowTextureID) {
/* 2141 */     if (this.glowTextureID == null) {
/* 2142 */       this.glowTextureID = glowTextureID;
/*      */     } else {
/* 2144 */       this.glowTextureID += ";" + glowTextureID;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setShaderID(String shaderID) {
/* 2149 */     this.shaderID = shaderID;
/*      */   }
/*      */   
/*      */   public void setBitmapID(String bitmapID) {
/* 2153 */     this.bitmapID = bitmapID;
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
/*      */   public void setShardBitmapID(String shardBitmapID) {
/* 2166 */     this.shardBitmapID = shardBitmapID;
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
/*      */   public void setItemDescriptionTag(String itemDescriptionTag) {
/* 2179 */     this.itemDescriptionTag = itemDescriptionTag;
/*      */     
/* 2181 */     this.itemDescription = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_ITEMS, itemDescriptionTag, true);
/*      */ 
/*      */     
/* 2184 */     if (this.itemDescription == null) {
/* 2185 */       this.itemDescription = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_STORY, itemDescriptionTag, true);
/*      */     }
/*      */   }
/*      */   
/*      */   private String insertLineBreaks(String s) {
/* 2190 */     String newS = "";
/* 2191 */     int len = 0;
/*      */     
/* 2193 */     int posBlank = s.indexOf(" ");
/* 2194 */     while (posBlank != -1) {
/* 2195 */       int posBreak = s.indexOf("<br>");
/*      */       
/* 2197 */       if (posBreak != -1 && posBreak < posBlank) {
/* 2198 */         String word = s.substring(0, posBreak + 4);
/* 2199 */         s = s.substring(posBreak + 4);
/* 2200 */         newS = newS + word;
/*      */         
/* 2202 */         len = 0;
/*      */       } else {
/* 2204 */         String word = s.substring(0, posBlank);
/* 2205 */         s = s.substring(posBlank + 1);
/* 2206 */         newS = newS + word;
/*      */         
/* 2208 */         len += word.length();
/* 2209 */         if (len < 80) {
/* 2210 */           newS = newS + " ";
/*      */         } else {
/* 2212 */           newS = newS + "<br>";
/* 2213 */           len = 0;
/*      */         } 
/*      */       } 
/*      */       
/* 2217 */       posBlank = s.indexOf(" ");
/*      */     } 
/*      */     
/* 2220 */     newS = newS + s;
/*      */     
/* 2222 */     return newS;
/*      */   }
/*      */   
/*      */   public void setItemNameTag(String itemNameTag) {
/* 2226 */     this.itemNameTag = itemNameTag;
/*      */     
/* 2228 */     String s = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_ITEMS, itemNameTag, false);
/*      */ 
/*      */     
/* 2231 */     if (s == null) {
/* 2232 */       s = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_STORY, itemNameTag, false);
/*      */     }
/*      */     
/* 2235 */     this.genderCode = -1;
/*      */     
/* 2237 */     if (s != null && 
/* 2238 */       s.startsWith("[")) {
/*      */       
/* 2240 */       String code = s.substring(0, 4);
/* 2241 */       s = s.substring(4);
/*      */       
/* 2243 */       this.genderCode = ARZDecompress.getGenderCode(code);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2249 */     if (this.genderCode == -1) this.genderCode = 0;
/*      */     
/* 2251 */     this.itemName = s;
/*      */   }
/*      */   
/*      */   public void setQualityTag(String qualityTag) {
/* 2255 */     this.qualityTag = qualityTag;
/*      */   }
/*      */   
/*      */   public void setStyleTag(String styleTag) {
/* 2259 */     this.styleTag = styleTag;
/*      */   }
/*      */   
/*      */   public void setItemSetID(String itemSetID) {
/* 2263 */     this.itemSetID = itemSetID;
/*      */   }
/*      */   
/*      */   public void setArtifactID(String artifactID) {
/* 2267 */     this.artifactID = artifactID;
/*      */   }
/*      */   
/*      */   public void setBonusAffixSetID(String bonusAffixSetID) {
/* 2271 */     this.bonusAffixSetID = bonusAffixSetID;
/*      */   }
/*      */   
/*      */   public void setCostFormulaSetID(String costFormulaSetID) {
/* 2275 */     this.costFormulaSetID = costFormulaSetID;
/*      */   }
/*      */   
/*      */   public void setSoulbound(boolean soulbound) {
/* 2279 */     this.soulbound = soulbound;
/*      */   }
/*      */   
/*      */   public void setHidePrefix(boolean hidePrefix) {
/* 2283 */     this.hidePrefix = hidePrefix;
/*      */   }
/*      */   
/*      */   public void setHideSuffix(boolean hideSuffix) {
/* 2287 */     this.hideSuffix = hideSuffix;
/*      */   }
/*      */   
/*      */   public void setQuestItem(boolean questItem) {
/* 2291 */     this.questItem = questItem;
/*      */   }
/*      */   
/*      */   public void setCannotPickup(boolean cannotPickup) {
/* 2295 */     this.cannotPickup = cannotPickup;
/*      */   }
/*      */   
/*      */   public void setItemLevel(int itemLevel) {
/* 2299 */     this.itemLevel = itemLevel;
/*      */   }
/*      */   
/*      */   public void setPlusAllSkills(int plusAllSkills) {
/* 2303 */     this.plusAllSkills = plusAllSkills;
/*      */   }
/*      */   
/*      */   public void setComponentPieces(int componentPieces) {
/* 2307 */     this.componentPieces = componentPieces;
/*      */   }
/*      */   
/*      */   public void setMaxStackSize(int maxStackSize) {
/* 2311 */     this.maxStackSize = maxStackSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSlotAxe1H(boolean slotAxe1H) {
/* 2319 */     this.slots.slotAxe1H = slotAxe1H;
/*      */   }
/*      */   
/*      */   public void setSlotAxe2H(boolean slotAxe2H) {
/* 2323 */     this.slots.slotAxe2H = slotAxe2H;
/*      */   }
/*      */   
/*      */   public void setSlotDagger1H(boolean slotDagger1H) {
/* 2327 */     this.slots.slotDagger1H = slotDagger1H;
/*      */   }
/*      */   
/*      */   public void setSlotMace1H(boolean slotMace1H) {
/* 2331 */     this.slots.slotMace1H = slotMace1H;
/*      */   }
/*      */   
/*      */   public void setSlotMace2H(boolean slotMace2H) {
/* 2335 */     this.slots.slotMace2H = slotMace2H;
/*      */   }
/*      */   
/*      */   public void setSlotScepter1H(boolean slotScepter1H) {
/* 2339 */     this.slots.slotScepter1H = slotScepter1H;
/*      */   }
/*      */   
/*      */   public void setSlotSpear2H(boolean slotSpear2H) {
/* 2343 */     this.slots.slotSpear1H = slotSpear2H;
/*      */   }
/*      */   
/*      */   public void setSlotStaff2H(boolean slotStaff2H) {
/* 2347 */     this.slots.slotStaff2H = slotStaff2H;
/*      */   }
/*      */   
/*      */   public void setSlotSword1H(boolean slotSword1H) {
/* 2351 */     this.slots.slotSword1H = slotSword1H;
/*      */   }
/*      */   
/*      */   public void setSlotSword2H(boolean slotSword2H) {
/* 2355 */     this.slots.slotSword2H = slotSword2H;
/*      */   }
/*      */   
/*      */   public void setSlotRanged1H(boolean slotRanged1H) {
/* 2359 */     this.slots.slotRanged1H = slotRanged1H;
/*      */   }
/*      */   
/*      */   public void setSlotRanged2H(boolean slotRanged2H) {
/* 2363 */     this.slots.slotRanged2H = slotRanged2H;
/*      */   }
/*      */   
/*      */   public void setSlotShield(boolean slotShield) {
/* 2367 */     this.slots.slotShield = slotShield;
/*      */   }
/*      */   
/*      */   public void setSlotOffhand(boolean slotOffhand) {
/* 2371 */     this.slots.slotOffhand = slotOffhand;
/*      */   }
/*      */   
/*      */   public void setSlotAmulet(boolean slotAmulet) {
/* 2375 */     this.slots.slotAmulet = slotAmulet;
/*      */   }
/*      */   
/*      */   public void setSlotBelt(boolean slotBelt) {
/* 2379 */     this.slots.slotBelt = slotBelt;
/*      */   }
/*      */   
/*      */   public void setSlotMedal(boolean slotMedal) {
/* 2383 */     this.slots.slotMedal = slotMedal;
/*      */   }
/*      */   
/*      */   public void setSlotRing(boolean slotRing) {
/* 2387 */     this.slots.slotRing = slotRing;
/*      */   }
/*      */   
/*      */   public void setSlotHead(boolean slotHead) {
/* 2391 */     this.slots.slotHead = slotHead;
/*      */   }
/*      */   
/*      */   public void setSlotShoulders(boolean slotShoulders) {
/* 2395 */     this.slots.slotShoulders = slotShoulders;
/*      */   }
/*      */   
/*      */   public void setSlotChest(boolean slotChest) {
/* 2399 */     this.slots.slotChest = slotChest;
/*      */   }
/*      */   
/*      */   public void setSlotHands(boolean slotHands) {
/* 2403 */     this.slots.slotHands = slotHands;
/*      */   }
/*      */   
/*      */   public void setSlotLegs(boolean slotLegs) {
/* 2407 */     this.slots.slotLegs = slotLegs;
/*      */   }
/*      */   
/*      */   public void setSlotFeet(boolean slotFeet) {
/* 2411 */     this.slots.slotFeet = slotFeet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DBStat getCreateDBStat(String search, int index) {
/* 2419 */     DBStat dbStat = null;
/* 2420 */     DBStat first = null;
/* 2421 */     boolean found = false;
/*      */     
/* 2423 */     int level = index + 1;
/* 2424 */     for (DBStat dbs : this.dbStats) {
/* 2425 */       if (dbs == null || 
/* 2426 */         !dbs.getStatType().equals(search))
/*      */         continue; 
/* 2428 */       if (first == null) first = dbs;
/*      */       
/* 2430 */       if (dbs.getSkillLevel() == level) {
/* 2431 */         dbStat = dbs;
/* 2432 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 2438 */     if (!found) {
/* 2439 */       if (first == null) {
/* 2440 */         dbStat = new DBStat();
/*      */       } else {
/* 2442 */         dbStat = first.clone();
/*      */       } 
/*      */       
/* 2445 */       dbStat.setStatType(search);
/* 2446 */       dbStat.setSkillLevel(level);
/*      */       
/* 2448 */       this.dbStats.add(dbStat);
/*      */     } 
/*      */     
/* 2451 */     return dbStat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processItemSkillLevelFormula() {
/* 2459 */     if (this.itemSkillLevelFormula == null)
/*      */       return; 
/*      */     try {
/* 2462 */       ExpressionBuilder builder = new ExpressionBuilder(this.itemSkillLevelFormula);
/* 2463 */       builder = builder.variables(new String[] { "itemLevel" });
/*      */       
/* 2465 */       Expression expression = builder.build();
/*      */       
/* 2467 */       expression.setVariable("itemLevel", getRequiredLevel());
/*      */       
/* 2469 */       int value = (int)expression.evaluate();
/*      */       
/* 2471 */       this.itemSkillLevel = value;
/*      */     }
/* 2473 */     catch (Exception exception) {}
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
/*      */   public void setPetNameTag(String petNameTag) {
/* 2521 */     this.skillNameTag = petNameTag;
/*      */     
/* 2523 */     if (petNameTag != null) {
/* 2524 */       this.petName = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_PETS, petNameTag, false);
/*      */     }
/*      */   }
/*      */   
/*      */   public void setPetFormulaLevel(String petFormulaLevel) {
/* 2529 */     this.petFormulaLevel = petFormulaLevel;
/*      */   }
/*      */   
/*      */   public void setPetBioID(String petBioID) {
/* 2533 */     this.petBioID = petBioID;
/*      */   }
/*      */   
/*      */   public void setPetDieSkillID(String petDieSkillID) {
/* 2537 */     this.petDieSkillID = petDieSkillID;
/*      */   }
/*      */   
/*      */   public void addPetSkillsSkillID(int index, String skillID) {
/* 2541 */     boolean found = false;
/*      */     
/* 2543 */     for (DBPetSkill psk : this.petSkills) {
/* 2544 */       if (psk.getIndex() == index) {
/* 2545 */         psk.setSkillID(skillID);
/*      */         
/* 2547 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 2553 */     if (!found) {
/* 2554 */       DBPetSkill psk = new DBPetSkill();
/*      */       
/* 2556 */       psk.setIndex(index);
/* 2557 */       psk.setSkillID(skillID);
/*      */       
/* 2559 */       this.petSkills.add(psk);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addPetSkillsFormulaLevel(int index, String formulaLevel) {
/* 2564 */     boolean found = false;
/*      */     
/* 2566 */     for (DBPetSkill psk : this.petSkills) {
/* 2567 */       if (psk.getIndex() == index) {
/* 2568 */         psk.setFormulaLevel(formulaLevel);
/*      */         
/* 2570 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 2576 */     if (!found) {
/* 2577 */       DBPetSkill psk = new DBPetSkill();
/*      */       
/* 2579 */       psk.setIndex(index);
/* 2580 */       psk.setFormulaLevel(formulaLevel);
/*      */       
/* 2582 */       this.petSkills.add(psk);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBioFormulaLife(String bioFormulaLife) {
/* 2591 */     this.bioFormulaLife = bioFormulaLife;
/*      */   }
/*      */   
/*      */   public void setBioFormulaMana(String bioFormulaMana) {
/* 2595 */     this.bioFormulaMana = bioFormulaMana;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean processDir(String dir) {
/* 2600 */     if (dir.startsWith("records/controllers/itemskills/basetemplates/")) return false; 
/* 2601 */     if (dir.startsWith("records/default/")) return false; 
/* 2602 */     if (dir.startsWith("records/fx/")) return false; 
/* 2603 */     if (dir.startsWith("records/game/old/")) return false; 
/* 2604 */     if (dir.startsWith("records/game/reqformula031116/")) return false; 
/* 2605 */     if (dir.startsWith("records/ingameui/")) return false;
/*      */     
/* 2607 */     if (dir.startsWith("records/items/lootchests/")) return false; 
/* 2608 */     if (dir.startsWith("records/items/testitems/")) return false; 
/* 2609 */     if (dir.startsWith("records/items/crafting/crafting_")) return false; 
/* 2610 */     if (dir.startsWith("records/level art/")) return false;
/*      */     
/* 2612 */     if (dir.startsWith("records/proxies/")) return false; 
/* 2613 */     if (dir.startsWith("records/sandbox/")) return false; 
/* 2614 */     if (dir.startsWith("records/soundgenerators/")) return false; 
/* 2615 */     if (dir.startsWith("records/sounds/")) return false; 
/* 2616 */     if (dir.startsWith("records/terraintextures/")) return false; 
/* 2617 */     if (dir.startsWith("records/triggervolumes/")) return false; 
/* 2618 */     if (dir.startsWith("records/watertype/")) return false; 
/* 2619 */     if (dir.startsWith("records/items/gearweapons/staff/crafting/")) return false; 
/* 2620 */     if (dir.startsWith("records/items/gearweapons/spears/crafting/%")) return false; 
/* 2621 */     if (dir.startsWith("records/items/crafting/blueprints/weapon/crafting_")) return false;
/*      */ 
/*      */     
/* 2624 */     if (dir.startsWith("records/creatures/pc/malepc01.dbr")) return true; 
/* 2625 */     if (dir.startsWith("records/creatures/pc/playerlevels.dbr")) return true; 
/* 2626 */     if (dir.startsWith("records/creatures/npcs/merchants/")) return true; 
/* 2627 */     if (dir.startsWith("records/creatures/npcs/npcgear/")) return true;
/*      */ 
/*      */     
/* 2630 */     if (dir.startsWith("records/creatures/")) return false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2635 */     if (dir.startsWith("records/ui/")) return true; 
/* 2636 */     if (dir.startsWith("records/ui load tables/")) return false;
/*      */     
/* 2638 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean processTemplate(String template, boolean extractItems) {
/* 2643 */     if (template.equals("database/templates/armor_chest.tpl")) return extractItems; 
/* 2644 */     if (template.equals("database/templates/armor_feet.tpl")) return extractItems; 
/* 2645 */     if (template.equals("database/templates/armor_hands.tpl")) return extractItems; 
/* 2646 */     if (template.equals("database/templates/armor_head.tpl")) return extractItems; 
/* 2647 */     if (template.equals("database/templates/armor_legs.tpl")) return extractItems; 
/* 2648 */     if (template.equals("database/templates/armor_shoulders.tpl")) return extractItems;
/*      */ 
/*      */     
/* 2651 */     if (template.equals("database/templates/weapon_axe.tpl")) return extractItems; 
/* 2652 */     if (template.equals("database/templates/weapon_mace.tpl")) return extractItems; 
/* 2653 */     if (template.equals("database/templates/weapon_dagger.tpl")) return extractItems; 
/* 2654 */     if (template.equals("database/templates/weapon_scepter.tpl")) return extractItems; 
/* 2655 */     if (template.equals("database/templates/weapon_spear.tpl")) return extractItems; 
/* 2656 */     if (template.equals("database/templates/weapon_staff.tpl")) return extractItems; 
/* 2657 */     if (template.equals("database/templates/weapon_sword.tpl")) return extractItems; 
/* 2658 */     if (template.equals("database/templates/weapon_ranged1h.tpl")) return extractItems; 
/* 2659 */     if (template.equals("database/templates/weapon_ranged2h.tpl")) return extractItems; 
/* 2660 */     if (template.equals("database/templates/weapon_axe2h.tpl")) return extractItems; 
/* 2661 */     if (template.equals("database/templates/weapon_mace2h.tpl")) return extractItems; 
/* 2662 */     if (template.equals("database/templates/weapon_sword2h.tpl")) return extractItems; 
/* 2663 */     if (template.equals("database/templates/weapon_offhand.tpl")) return extractItems; 
/* 2664 */     if (template.equals("database/templates/weaponarmor_shield.tpl")) return extractItems;
/*      */ 
/*      */     
/* 2667 */     if (template.equals("database/templates/jewelry_amulet.tpl")) return extractItems; 
/* 2668 */     if (template.equals("database/templates/jewelry_medal.tpl")) return extractItems; 
/* 2669 */     if (template.equals("database/templates/jewelry_ring.tpl")) return extractItems; 
/* 2670 */     if (template.equals("database/templates/armor_waist.tpl")) return extractItems;
/*      */ 
/*      */     
/* 2673 */     if (template.equals("database/templates/itemusableskill.tpl")) return extractItems; 
/* 2674 */     if (template.equals("database/templates/itemtransmuter.tpl")) return extractItems; 
/* 2675 */     if (template.equals("database/templates/itemtransmuterset.tpl")) return extractItems; 
/* 2676 */     if (template.equals("database/templates/itemartifactformula.tpl")) return extractItems; 
/* 2677 */     if (template.equals("database/templates/questitem.tpl")) return extractItems; 
/* 2678 */     if (template.equals("database/templates/oneshot_scroll.tpl")) return extractItems; 
/* 2679 */     if (template.equals("database/templates/oneshot_endlessdungeon.tpl")) return extractItems; 
/* 2680 */     if (template.equals("database/templates/oneshot_potion.tpl")) return extractItems; 
/* 2681 */     if (template.equals("database/templates/itemenchantment.tpl")) return extractItems; 
/* 2682 */     if (template.equals("database/templates/itemrelic.tpl")) return extractItems; 
/* 2683 */     if (template.equals("database/templates/itemartifact.tpl")) return extractItems; 
/* 2684 */     if (template.equals("database/templates/itemnote.tpl")) return extractItems; 
/* 2685 */     if (template.equals("database/templates/itemfactionbooster.tpl")) return extractItems; 
/* 2686 */     if (template.equals("database/templates/itemfactionwarrant.tpl")) return extractItems; 
/* 2687 */     if (template.equals("database/templates/itemdifficultyunlock.tpl")) return extractItems;
/*      */     
/* 2689 */     if (template.equals("database/templates/oneshot_food.tpl")) return extractItems; 
/* 2690 */     if (template.equals("database/templates/oneshot_potionmana.tpl")) return extractItems; 
/* 2691 */     if (template.equals("database/templates/oneshot_potionhealth.tpl")) return extractItems; 
/* 2692 */     if (template.equals("database/templates/itemdevotionreset.tpl")) return extractItems; 
/* 2693 */     if (template.equals("database/templates/itemattributereset.tpl")) return extractItems; 
/* 2694 */     if (template.equals("database/templates/oneshot_sack.tpl")) return extractItems;
/*      */ 
/*      */     
/* 2697 */     if (template.equals("database/templates/itemset.tpl")) return extractItems;
/*      */ 
/*      */     
/* 2700 */     if (template.equals("database/templates/lootrandomizer.tpl")) return true; 
/* 2701 */     if (template.equals("database/templates/lootrandomizertabledynamic.tpl")) return true;
/*      */ 
/*      */     
/* 2704 */     if (template.equals("database/templates/leveltable.tpl")) return true; 
/* 2705 */     if (template.equals("database/templates/lootitemtable_dynweighted_dynaffix.tpl")) return true;
/*      */ 
/*      */     
/* 2708 */     if (template.equals("database/templates/skilltree.tpl")) return true; 
/* 2709 */     if (template.equals("database/templates/skilltree_expanded.tpl")) return true; 
/* 2710 */     if (template.equals("database/templates/skilltree_100.tpl")) return true; 
/* 2711 */     if (template.startsWith("database/templates/skill")) return true;
/*      */ 
/*      */ 
/*      */     
/* 2715 */     if (template.equals("database/templates/petbonus.tpl")) return true; 
/* 2716 */     if (template.equals("database/templates/fixeditemskill_buff.tpl")) return true; 
/* 2717 */     if (template.equals("database/templates/skillautocastcontroller.tpl")) return true;
/*      */ 
/*      */     
/* 2720 */     if (template.equals("database/templates/pet.tpl")) return true; 
/* 2721 */     if (template.equals("database/templates/petplayerscaling.tpl")) return true; 
/* 2722 */     if (template.equals("database/templates/characterattributeequations.tpl")) return true;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2727 */     if (template.equals("database/templates/staticshrine.tpl")) return true; 
/* 2728 */     if (template.equals("database/templates/factionpack.tpl")) return true; 
/* 2729 */     if (template.equals("database/templates/npcmerchant.tpl")) return true; 
/* 2730 */     if (template.equals("database/templates/factionmarket.tpl")) return true; 
/* 2731 */     if (template.equals("database/templates/factiontier.tpl")) return true; 
/* 2732 */     if (template.equals("database/templates/gameengine.tpl")) return true; 
/* 2733 */     if (template.equals("database/templates/itemcost.tpl")) return true; 
/* 2734 */     if (template.equals("database/templates/player.tpl")) return true; 
/* 2735 */     if (template.equals("database/templates/experiencelevelcontrol.tpl")) return true; 
/* 2736 */     if (template.equals("database/templates/ingameui/devotionconstellation.tpl")) return true; 
/* 2737 */     if (template.equals("database/templates/ingameui/skillbutton.tpl")) return true; 
/* 2738 */     if (template.equals("database/templates/ingameui/bitmapsingle.tpl")) return true; 
/* 2739 */     if (template.equals("database/templates/ingameui/bargraph.tpl")) return true; 
/* 2740 */     if (template.equals("database/templates/ingameui/skillpanectrl.tpl")) return true; 
/* 2741 */     if (template.equals("database/templates/ingameui/skillswindow.tpl")) return true;
/*      */ 
/*      */ 
/*      */     
/* 2745 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean processFile(String filename, String template) {
/* 2749 */     if (template.equals("database/templates/gameengine.tpl")) {
/* 2750 */       return filename.equals("records/game/gameengine.dbr");
/*      */     }
/*      */     
/* 2753 */     if (filename.equals("records/items/loottables/weapons/tdyn_gun2hcrossbow_c01.dbr")) return false; 
/* 2754 */     if (filename.equals("records/items/loottables/weapons/tdyn_gun2hcrossbow_c01b.dbr")) return false; 
/* 2755 */     if (filename.equals("records/items/loottables/weapons/tdyn_gun2hcrossbow_d01.dbr")) return false;
/*      */     
/* 2757 */     if (filename.contains("/old/")) return false;
/*      */     
/* 2759 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isGameEngine() {
/* 2763 */     return this.fileName.equals("records/game/gameengine.dbr");
/*      */   }
/*      */   
/*      */   public boolean isFaction() {
/* 2767 */     if (this.template == null) return true;
/*      */     
/* 2769 */     if (this.template.equals("database/templates/factionpack.tpl")) return true;
/*      */     
/* 2771 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isMerchant() {
/* 2775 */     if (this.template == null) return true;
/*      */     
/* 2777 */     if (this.template.equals("database/templates/npcmerchant.tpl")) return true;
/*      */     
/* 2779 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isMerchantTableSet() {
/* 2783 */     if (this.template == null) return true;
/*      */     
/* 2785 */     if (this.template.equals("database/templates/npcmerchant.tpl")) return true; 
/* 2786 */     if (this.template.equals("database/templates/factionmarket.tpl")) return true; 
/* 2787 */     if (this.template.equals("database/templates/factiontier.tpl")) return true;
/*      */     
/* 2789 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isMerchantTable() {
/* 2793 */     if (this.template == null) return true;
/*      */     
/* 2795 */     if (this.template.equals("database/templates/factiontier.tpl")) return true;
/*      */     
/* 2797 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isPlayerEngine() {
/* 2801 */     if (this.fileName.equals("records/creatures/pc/playerlevels.dbr")) return true; 
/* 2802 */     if (this.fileName.equals("records/creatures/pc/malepc01.dbr")) return true;
/*      */     
/* 2804 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isConstellation() {
/* 2808 */     if (this.template == null) return true;
/*      */     
/* 2810 */     if (this.template.equals("database/templates/ingameui/devotionconstellation.tpl")) return true;
/*      */     
/* 2812 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isSkillButton() {
/* 2816 */     if (this.template == null) return true;
/*      */     
/* 2818 */     if (this.template.equals("database/templates/ingameui/skillbutton.tpl")) return true;
/*      */     
/* 2820 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isBitmap() {
/* 2824 */     if (this.template == null) return true;
/*      */     
/* 2826 */     if (this.template.equals("database/templates/ingameui/bitmapsingle.tpl")) return true; 
/* 2827 */     if (this.template.equals("database/templates/ingameui/bargraph.tpl")) return true;
/*      */     
/* 2829 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isClassTable() {
/* 2833 */     if (this.template == null) return true;
/*      */     
/* 2835 */     if (this.template.equals("database/templates/ingameui/skillpanectrl.tpl")) return true;
/*      */     
/* 2837 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isSkillMaster() {
/* 2841 */     if (this.template == null) return true;
/*      */     
/* 2843 */     if (this.template.equals("database/templates/ingameui/skillswindow.tpl")) return true;
/*      */     
/* 2845 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isShrine() {
/* 2849 */     if (this.template == null) return true;
/*      */     
/* 2851 */     if (this.template.equals("database/templates/staticshrine.tpl")) return true;
/*      */     
/* 2853 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isSkillTree() {
/* 2857 */     if (this.template == null) return true;
/*      */     
/* 2859 */     if (this.template.equals("database/templates/skilltree.tpl")) return true; 
/* 2860 */     if (this.template.equals("database/templates/skilltree_expanded.tpl")) return true; 
/* 2861 */     if (this.template.equals("database/templates/skilltree_100.tpl")) return true;
/*      */     
/* 2863 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isSkill() {
/* 2867 */     if (this.template == null) return true;
/*      */     
/* 2869 */     if (this.template.equals("database/templates/skilltree.tpl")) return false; 
/* 2870 */     if (this.template.equals("database/templates/skilltree_expanded.tpl")) return false; 
/* 2871 */     if (this.template.equals("database/templates/skilltree_100.tpl")) return false; 
/* 2872 */     if (this.template.equals("database/templates/skillautocastcontroller.tpl")) return false;
/*      */     
/* 2874 */     if (this.template.startsWith("database/templates/skill")) return true;
/*      */ 
/*      */ 
/*      */     
/* 2878 */     if (this.template.equals("database/templates/petbonus.tpl") && 
/* 2879 */       !this.fileName.startsWith("records/items/lootaffixes/")) return true;
/*      */     
/* 2881 */     if (this.template.equals("database/templates/fixeditemskill_buff.tpl")) return true;
/*      */ 
/*      */     
/* 2884 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isPet() {
/* 2888 */     if (this.template == null) return true;
/*      */     
/* 2890 */     if (this.template.equals("database/templates/pet.tpl")) return true; 
/* 2891 */     if (this.template.equals("database/templates/petplayerscaling.tpl")) return true;
/*      */     
/* 2893 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isPetBio() {
/* 2897 */     if (this.template == null) return true;
/*      */     
/* 2899 */     if (this.template.equals("database/templates/characterattributeequations.tpl")) return true;
/*      */     
/* 2901 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isController() {
/* 2905 */     if (this.template == null) return true;
/*      */     
/* 2907 */     if (this.template.equals("database/templates/skillautocastcontroller.tpl")) return true;
/*      */     
/* 2909 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isAffix() {
/* 2913 */     if (this.template == null) return true;
/*      */     
/* 2915 */     if (this.template.equals("database/templates/lootrandomizer.tpl")) return true; 
/* 2916 */     if (this.template.equals("database/templates/petbonus.tpl") && 
/* 2917 */       this.fileName.startsWith("records/items/lootaffixes/")) return true;
/*      */ 
/*      */     
/* 2920 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isAffixSet() {
/* 2924 */     if (this.template == null) return true;
/*      */     
/* 2926 */     if (this.template.equals("database/templates/lootrandomizertabledynamic.tpl")) return true;
/*      */     
/* 2928 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isFormulaSet() {
/* 2932 */     if (this.template == null) return true;
/*      */     
/* 2934 */     if (this.template.equals("database/templates/itemcost.tpl")) return true;
/*      */     
/* 2936 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isItemSet() {
/* 2940 */     if (this.template == null) return true;
/*      */     
/* 2942 */     if (this.template.equals("database/templates/itemset.tpl")) return true;
/*      */     
/* 2944 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isLootTable() {
/* 2948 */     if (this.template == null) return true;
/*      */     
/* 2950 */     if (this.template.equals("database/templates/lootitemtable_dynweighted_dynaffix.tpl")) return true;
/*      */     
/* 2952 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isLootTableSet() {
/* 2956 */     if (this.template == null) return true;
/*      */     
/* 2958 */     if (this.template.equals("database/templates/leveltable.tpl")) return true;
/*      */     
/* 2960 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\file\ARZRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */