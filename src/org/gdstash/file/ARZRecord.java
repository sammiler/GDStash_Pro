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
/*      */ 
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
/*  297 */   private ItemSlots slots = new ItemSlots();
/*  298 */   private List<DBFactionReputation> factionReputations = new LinkedList<>();
/*  299 */   private List<DBEngineMasteryTier> masteryTiers = new LinkedList<>();
/*  300 */   private List<String> classTableIDs = new LinkedList<>();
/*  301 */   private List<DBSkillModifier> skillModifiers = new LinkedList<>();
/*  302 */   private List<DBAffixSet.DBEntry> randomizers = new LinkedList<>();
/*  303 */   private List<String> itemSetItemIDs = new LinkedList<>();
/*  304 */   private List<DBLootTableItemAlloc> tableItems = new LinkedList<>();
/*  305 */   private List<DBLootTableAffixSetAlloc> tableAffixSets = new LinkedList<>();
/*  306 */   private List<DBLootTableSetAlloc> tableSetAllocs = new LinkedList<>();
/*  307 */   private List<DBFormula> formulaSetFormulas = new LinkedList<>();
/*      */   
/*  309 */   private DBFormulaSet dbFormulaSet = null;
/*  310 */   private List<DBStatBonusRace> dbStatBonusRaces = new LinkedList<>();
/*  311 */   private List<DBSkillSpawn> dbSpawnPets = new LinkedList<>();
/*  312 */   private List<DBStat> dbStats = new LinkedList<>();
/*  313 */   public List<DBSkillBonus> dbSkillBonuses = new LinkedList<>();
/*  314 */   private List<String> merchantTableIDs = new LinkedList<>();
/*  315 */   private List<String> merchantItemIDs = new LinkedList<>();
/*  316 */   private List<DBEnginePlayerMasteries> masteries = new LinkedList<>();
/*  317 */   private List<DBEngineLevel.LevelStats> levelStats = new LinkedList<>();
/*  318 */   private List<DBConstellationAffinity> constellationAffinities = new LinkedList<>();
/*  319 */   private List<DBConstellationStar> constellationStars = new LinkedList<>();
/*  320 */   private List<DBSkillTreeAlloc> masterySkillIDs = new LinkedList<>();
/*  321 */   private List<String> skillButtons = new LinkedList<>();
/*  322 */   private List<Integer> skillXPLevels = new LinkedList<>();
/*  323 */   private List<DBSkillDependency> skillDependencies = new LinkedList<>();
/*  324 */   private List<DBSkillConnector> skillConnections = new LinkedList<>();
/*      */   
/*      */   private boolean devotion = false;
/*  327 */   private List<DBPetSkill> petSkills = new LinkedList<>(); public int strID; public int len_str; public String str; public int offset; public int len_comp; public int len_decomp; public byte[] filedata; private String fileName; private String fileDesc; private String recordClass; private String template; private String factionAltNeutralTag; private boolean factionBountyEnabled; private boolean factionQuestEnabled; private String factionTag; private String merchantFactionID; private String merchantTableSetID; private int playerBaseDex; private int playerBaseInt; private int playerBaseStr; private int playerBaseLife; private int playerBaseMana; private int playerIncDex; private int playerIncInt; private int playerIncStr; private int playerIncLife; private int playerIncMana; private int playerMaxDevotion; private int playerMaxLevel; private String xpFormula; private int xOffset; private int xSize; private int yOffset; private int ySize; private String constellationNameTag; private String constellationInfoTag; private String shrineNameTag; private String shrineName; private boolean shrineNormalDisabled; private boolean shrineNormalLocked; private boolean shrineEpicDisabled; private boolean shrineEpicLocked; private boolean shrineLegendaryDisabled; private boolean shrineLegendaryLocked; private String buttonSkillID; private int posX; private int posY; private int offsetX; private int offsetY; private boolean circularButton; private String masteryBarID; private String skillPaneID; private String masteryBitmapID; private String titleTag; private String skillBuffID; private String skillDescription; private String skillNameTag; private String skillName; private boolean mastery;
/*      */   private int skillTier;
/*      */   private int maxLevel;
/*      */   private String bitmapDownID;
/*      */   private String bitmapUpID;
/*      */   private int skillLevel;
/*      */   
/*      */   public String getFileDescription() {
/*  335 */     return this.fileDesc;
/*      */   }
/*      */   private int skillDuration; private boolean bonusIncrement; private boolean skillModified; private boolean skillDependencyAll; private int triggerChance; private String triggerType; private int lootRandomCost; private String lootRandomName; private String convertIn; private String convertOut; private String convertIn2; private String convertOut2; private String petBonusID; private String rarity; private int reqLevel; private String itemSkillID; private String itemSkillLevelFormula; private int itemSkillLevel; private int offensiveChance; private int retaliationChance; private int rngPercent; private String skillControllerID; private String itemSetDescTag; private int skillModifierLevel; private String itemSetNameTag; private boolean nPre_nSuf; private boolean nPre_rSuf; private boolean rPre_nSuf; private boolean rPre_rSuf; private String armorClass; private String artifactClass; private String artifactID; private String meshID; private String baseTextureID; private String bumpTextureID; private String glowTextureID; private String shaderID; private String bitmapID; private String shardBitmapID; private String itemNameTag; private String itemDescriptionTag; private int genderCode; private String itemName; private String itemDescription; private String qualityTag; private String styleTag; private String itemSetID; private String bonusAffixSetID; private String costFormulaSetID; private boolean soulbound; private boolean hidePrefix; private boolean hideSuffix; private boolean questItem; private boolean cannotPickup; private int itemLevel; private int plusAllSkills; private int componentPieces; private int maxStackSize; private String petName; private String petFormulaLevel; private String petBioID; private String petDieSkillID; private String bioFormulaLife; private String bioFormulaMana; private String dlcRequirement;
/*      */   public String getFileName() {
/*  339 */     return this.fileName;
/*      */   }
/*      */   
/*      */   public String getRecordClass() {
/*  343 */     return this.recordClass;
/*      */   }
/*      */   
/*      */   public String getTemplate() {
/*  347 */     return this.template;
/*      */   }
/*      */   
/*      */   public String getDLCRequirement() {
/*  351 */     return this.dlcRequirement;
/*      */   }
/*      */   
/*      */   public DBFormulaSet getDBFormulaSet() {
/*  355 */     if (this.dbFormulaSet == null) {
/*  356 */       this.dbFormulaSet = new DBFormulaSet(this);
/*      */     }
/*  358 */     return this.dbFormulaSet;
/*      */   }
/*      */   
/*      */   public List<DBStat> getDBStatList() {
/*  362 */     return this.dbStats;
/*      */   }
/*      */   
/*      */   public List<DBStatBonusRace> getDBStatBonusRaceList() {
/*  366 */     return this.dbStatBonusRaces;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DBFactionReputation> getFactionReputationList() {
/*  374 */     return this.factionReputations;
/*      */   }
/*      */   
/*      */   public List<DBEngineMasteryTier> getMasteryTierList() {
/*  378 */     return this.masteryTiers;
/*      */   }
/*      */   
/*      */   public String getFactionAltNeutralTag() {
/*  382 */     return this.factionAltNeutralTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getFactionBountyEnabled() {
/*  390 */     return this.factionBountyEnabled;
/*      */   }
/*      */   
/*      */   public boolean getFactionQuestEnabled() {
/*  394 */     return this.factionQuestEnabled;
/*      */   }
/*      */   
/*      */   public String getFactionTag() {
/*  398 */     return this.factionTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getSkillMasterList() {
/*  406 */     return this.classTableIDs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMerchantFactionID() {
/*  414 */     return this.merchantFactionID;
/*      */   }
/*      */   
/*      */   public String getMerchantTableSetID() {
/*  418 */     return this.merchantTableSetID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getMerchantTableIDList() {
/*  426 */     return this.merchantTableIDs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getMerchantTableItemIDList() {
/*  434 */     return this.merchantItemIDs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPlayerBaseDex() {
/*  442 */     return this.playerBaseDex;
/*      */   }
/*      */   
/*      */   public int getPlayerBaseInt() {
/*  446 */     return this.playerBaseInt;
/*      */   }
/*      */   
/*      */   public int getPlayerBaseStr() {
/*  450 */     return this.playerBaseStr;
/*      */   }
/*      */   
/*      */   public int getPlayerBaseLife() {
/*  454 */     return this.playerBaseLife;
/*      */   }
/*      */   
/*      */   public int getPlayerBaseMana() {
/*  458 */     return this.playerBaseMana;
/*      */   }
/*      */   
/*      */   public List<DBEnginePlayerMasteries> getMasteryList() {
/*  462 */     return this.masteries;
/*      */   }
/*      */   
/*      */   public int getPlayerIncDex() {
/*  466 */     return this.playerIncDex;
/*      */   }
/*      */   
/*      */   public int getPlayerIncInt() {
/*  470 */     return this.playerIncInt;
/*      */   }
/*      */   
/*      */   public int getPlayerIncStr() {
/*  474 */     return this.playerIncStr;
/*      */   }
/*      */   
/*      */   public int getPlayerIncLife() {
/*  478 */     return this.playerIncLife;
/*      */   }
/*      */   
/*      */   public int getPlayerIncMana() {
/*  482 */     return this.playerIncMana;
/*      */   }
/*      */   
/*      */   public int getPlayerMaxDevotion() {
/*  486 */     return this.playerMaxDevotion;
/*      */   }
/*      */   
/*      */   public int getPlayerMaxLevel() {
/*  490 */     return this.playerMaxLevel;
/*      */   }
/*      */   
/*      */   public String getXPFormula() {
/*  494 */     return this.xpFormula;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DBEngineLevel.LevelStats> getLevelStatList() {
/*  502 */     return this.levelStats;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getXOffset() {
/*  510 */     return this.xOffset;
/*      */   }
/*      */   
/*      */   public int getXSize() {
/*  514 */     return this.xSize;
/*      */   }
/*      */   
/*      */   public int getYOffset() {
/*  518 */     return this.yOffset;
/*      */   }
/*      */   
/*      */   public int getYSize() {
/*  522 */     return this.ySize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getConstellationNameTag() {
/*  530 */     return this.constellationNameTag;
/*      */   }
/*      */   
/*      */   public String getConstellationInfoTag() {
/*  534 */     return this.constellationInfoTag;
/*      */   }
/*      */   
/*      */   public List<DBConstellationAffinity> getConstellationAffinityList() {
/*  538 */     return this.constellationAffinities;
/*      */   }
/*      */   
/*      */   public List<DBConstellationStar> getConstellationStarList() {
/*  542 */     return this.constellationStars;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getShrineName() {
/*  550 */     return this.shrineName;
/*      */   }
/*      */   
/*      */   public boolean getShrineNormalDisabled() {
/*  554 */     return this.shrineNormalDisabled;
/*      */   }
/*      */   
/*      */   public boolean getShrineNormalLocked() {
/*  558 */     return this.shrineNormalLocked;
/*      */   }
/*      */   
/*      */   public boolean getShrineEpicDisabled() {
/*  562 */     return this.shrineEpicDisabled;
/*      */   }
/*      */   
/*      */   public boolean getShrineEpicLocked() {
/*  566 */     return this.shrineEpicLocked;
/*      */   }
/*      */   
/*      */   public boolean getShrineLegendaryDisabled() {
/*  570 */     return this.shrineLegendaryDisabled;
/*      */   }
/*      */   
/*      */   public boolean getShrineLegendaryLocked() {
/*  574 */     return this.shrineLegendaryLocked;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getButtonSkillID() {
/*  582 */     return this.buttonSkillID;
/*      */   }
/*      */   
/*      */   public int getPosX() {
/*  586 */     return this.posX;
/*      */   }
/*      */   
/*      */   public int getPosY() {
/*  590 */     return this.posY;
/*      */   }
/*      */   
/*      */   public int getOffsetX() {
/*  594 */     return this.offsetX;
/*      */   }
/*      */   
/*      */   public int getOffsetY() {
/*  598 */     return this.offsetY;
/*      */   }
/*      */   
/*      */   public boolean getCircularButton() {
/*  602 */     return this.circularButton;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMasteryBarID() {
/*  610 */     return this.masteryBarID;
/*      */   }
/*      */   
/*      */   public String getSkillPaneID() {
/*  614 */     return this.skillPaneID;
/*      */   }
/*      */   
/*      */   public String getMasteryBitmapID() {
/*  618 */     return this.masteryBitmapID;
/*      */   }
/*      */   
/*      */   public List<String> getSkillButtonList() {
/*  622 */     return this.skillButtons;
/*      */   }
/*      */   
/*      */   public String getTitleTag() {
/*  626 */     return this.titleTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DBSkillTreeAlloc> getMasterySkillList() {
/*  634 */     return this.masterySkillIDs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSkillBuffID() {
/*  642 */     return this.skillBuffID;
/*      */   }
/*      */   
/*      */   public String getSkillDescription() {
/*  646 */     return this.skillDescription;
/*      */   }
/*      */   
/*      */   public String getSkillNameTag() {
/*  650 */     return this.skillNameTag;
/*      */   }
/*      */   
/*      */   public String getSkillName() {
/*  654 */     return this.skillName;
/*      */   }
/*      */   
/*      */   public boolean isMastery() {
/*  658 */     return this.mastery;
/*      */   }
/*      */   
/*      */   public boolean isDevotion() {
/*  662 */     return this.devotion;
/*      */   }
/*      */   
/*      */   public int getSkillTier() {
/*  666 */     return this.skillTier;
/*      */   }
/*      */   
/*      */   public int getSkillMaxLevel() {
/*  670 */     return this.maxLevel;
/*      */   }
/*      */   
/*      */   public String getSkillBitmapDownID() {
/*  674 */     return this.bitmapDownID;
/*      */   }
/*      */   
/*      */   public String getSkillBitmapUpID() {
/*  678 */     return this.bitmapUpID;
/*      */   }
/*      */   
/*      */   public int getSkillLevel() {
/*  682 */     return this.skillLevel;
/*      */   }
/*      */   
/*      */   public int getSkillDuration() {
/*  686 */     return this.skillDuration;
/*      */   }
/*      */   
/*      */   public boolean hasSkillBonusIncrement() {
/*  690 */     return this.bonusIncrement;
/*      */   }
/*      */   
/*      */   public boolean hasSkillModifier() {
/*  694 */     return this.skillModified;
/*      */   }
/*      */   
/*      */   public boolean isDependencyAll() {
/*  698 */     return this.skillDependencyAll;
/*      */   }
/*      */   
/*      */   public List<DBSkillSpawn> getSpawnPetList() {
/*  702 */     return this.dbSpawnPets;
/*      */   }
/*      */   
/*      */   public List<Integer> getSkillXPLevelList() {
/*  706 */     return this.skillXPLevels;
/*      */   }
/*      */   
/*      */   public List<DBSkillDependency> getSkillDependencyList() {
/*  710 */     return this.skillDependencies;
/*      */   }
/*      */   
/*      */   public List<DBSkillConnector> getSkillConnectorList() {
/*  714 */     return this.skillConnections;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTriggerChance() {
/*  722 */     return this.triggerChance;
/*      */   }
/*      */   
/*      */   public String getTriggerType() {
/*  726 */     return this.triggerType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLootRandomCost() {
/*  734 */     return this.lootRandomCost;
/*      */   }
/*      */   
/*      */   public String getLootRandomName() {
/*  738 */     return this.lootRandomName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getConversionIn() {
/*  746 */     return this.convertIn;
/*      */   }
/*      */   
/*      */   public String getConversionOut() {
/*  750 */     return this.convertOut;
/*      */   }
/*      */   
/*      */   public String getConversionIn2() {
/*  754 */     return this.convertIn2;
/*      */   }
/*      */   
/*      */   public String getConversionOut2() {
/*  758 */     return this.convertOut2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getItemSkillID() {
/*  766 */     return this.itemSkillID;
/*      */   }
/*      */   
/*      */   public String getItemSkillLevelFormula() {
/*  770 */     return this.itemSkillLevelFormula;
/*      */   }
/*      */   
/*      */   public int getItemSkillLevel() {
/*  774 */     return this.itemSkillLevel;
/*      */   }
/*      */   
/*      */   public int getOffensiveChance() {
/*  778 */     return this.offensiveChance;
/*      */   }
/*      */   
/*      */   public String getPetBonusID() {
/*  782 */     return this.petBonusID;
/*      */   }
/*      */   
/*      */   public String getRarity() {
/*  786 */     return this.rarity;
/*      */   }
/*      */   
/*      */   public int getRequiredLevel() {
/*  790 */     return this.reqLevel;
/*      */   }
/*      */   
/*      */   public int getRetaliationChance() {
/*  794 */     return this.retaliationChance;
/*      */   }
/*      */   
/*      */   public int getRNGPercent() {
/*  798 */     return this.rngPercent;
/*      */   }
/*      */   
/*      */   public String getSkillControllerID() {
/*  802 */     return this.skillControllerID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DBAffixSet.DBEntry> getAffixSetRandomizerList() {
/*  810 */     return this.randomizers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getItemSetItemIDList() {
/*  818 */     return this.itemSetItemIDs;
/*      */   }
/*      */   
/*      */   public String getItemSetDescriptionTag() {
/*  822 */     return this.itemSetDescTag;
/*      */   }
/*      */   
/*      */   public int getItemSetSkillModifierLevel() {
/*  826 */     return this.skillModifierLevel;
/*      */   }
/*      */   
/*      */   public String getItemSetNameTag() {
/*  830 */     return this.itemSetNameTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getTableNormalPrefixSuffix() {
/*  838 */     return this.nPre_nSuf;
/*      */   }
/*      */   
/*      */   public boolean getTableNormalPrefixRareSuffix() {
/*  842 */     return this.nPre_rSuf;
/*      */   }
/*      */   
/*      */   public boolean getTableRarePrefixNormalSuffix() {
/*  846 */     return this.rPre_nSuf;
/*      */   }
/*      */   
/*      */   public boolean getTableRarePrefixSuffix() {
/*  850 */     return this.rPre_rSuf;
/*      */   }
/*      */   
/*      */   public List<DBLootTableItemAlloc> getTableItemAllocList() {
/*  854 */     return this.tableItems;
/*      */   }
/*      */   
/*      */   public List<DBLootTableAffixSetAlloc> getTableAffixSetAllocList() {
/*  858 */     return this.tableAffixSets;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DBLootTableSetAlloc> getTableSetAllocList() {
/*  866 */     return this.tableSetAllocs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DBFormula> getFormulaSetFormulaList() {
/*  874 */     return this.formulaSetFormulas;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getArmorClass() {
/*  882 */     return this.armorClass;
/*      */   }
/*      */   
/*      */   public String getArtifactClass() {
/*  886 */     return this.artifactClass;
/*      */   }
/*      */   
/*      */   public String getMeshID() {
/*  890 */     return this.meshID;
/*      */   }
/*      */   
/*      */   public String getBaseTextureID() {
/*  894 */     return this.baseTextureID;
/*      */   }
/*      */   
/*      */   public String getBumpTextureID() {
/*  898 */     return this.bumpTextureID;
/*      */   }
/*      */   
/*      */   public String getGlowTextureID() {
/*  902 */     return this.glowTextureID;
/*      */   }
/*      */   
/*      */   public String getShaderID() {
/*  906 */     return this.shaderID;
/*      */   }
/*      */   
/*      */   public String getBitmapID() {
/*  910 */     return this.bitmapID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getShardBitmapID() {
/*  918 */     return this.shardBitmapID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getItemNameTag() {
/*  926 */     return this.itemNameTag;
/*      */   }
/*      */   
/*      */   public int getGenderCode() {
/*  930 */     return this.genderCode;
/*      */   }
/*      */   
/*      */   public String getItemDescription() {
/*  934 */     return this.itemDescription;
/*      */   }
/*      */   
/*      */   public String getItemName() {
/*  938 */     return this.itemName;
/*      */   }
/*      */   
/*      */   public String getQualityTag() {
/*  942 */     return this.qualityTag;
/*      */   }
/*      */   
/*      */   public String getStyleTag() {
/*  946 */     return this.styleTag;
/*      */   }
/*      */   
/*      */   public String getItemSetID() {
/*  950 */     return this.itemSetID;
/*      */   }
/*      */   
/*      */   public String getArtifactID() {
/*  954 */     return this.artifactID;
/*      */   }
/*      */   
/*      */   public String getBonusAffixSetID() {
/*  958 */     return this.bonusAffixSetID;
/*      */   }
/*      */   
/*      */   public String getCostFormulaSetID() {
/*  962 */     return this.costFormulaSetID;
/*      */   }
/*      */   
/*      */   public boolean isSoulbound() {
/*  966 */     return this.soulbound;
/*      */   }
/*      */   
/*      */   public boolean isHidePrefix() {
/*  970 */     return this.hidePrefix;
/*      */   }
/*      */   
/*      */   public boolean isHideSuffix() {
/*  974 */     return this.hideSuffix;
/*      */   }
/*      */   
/*      */   public boolean isQuestItem() {
/*  978 */     return this.questItem;
/*      */   }
/*      */   
/*      */   public boolean isCannotPickup() {
/*  982 */     return this.cannotPickup;
/*      */   }
/*      */   
/*      */   public int getItemLevel() {
/*  986 */     return this.itemLevel;
/*      */   }
/*      */   
/*      */   public int getPlusAllSkills() {
/*  990 */     return this.plusAllSkills;
/*      */   }
/*      */   
/*      */   public int getComponentPieces() {
/*  994 */     return this.componentPieces;
/*      */   }
/*      */   
/*      */   public int getMaxStackSize() {
/*  998 */     return this.maxStackSize;
/*      */   }
/*      */   
/*      */   public List<DBSkillModifier> getSkillModifierList() {
/* 1002 */     return this.skillModifiers;
/*      */   }
/*      */   
/*      */   public void addModifierSkillID(int index, String skillID) {
/* 1006 */     boolean found = false;
/*      */     
/* 1008 */     for (DBSkillModifier modifier : this.skillModifiers) {
/* 1009 */       if (modifier.getIndex() == index) {
/* 1010 */         modifier.setSkillID(skillID);
/*      */         
/* 1012 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1018 */     if (!found) {
/* 1019 */       DBSkillModifier modifier = new DBSkillModifier();
/*      */       
/* 1021 */       modifier.setIndex(index);
/* 1022 */       modifier.setSkillID(skillID);
/*      */       
/* 1024 */       this.skillModifiers.add(modifier);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addModifierModifierID(int index, String modifierID) {
/* 1029 */     boolean found = false;
/*      */     
/* 1031 */     for (DBSkillModifier modifier : this.skillModifiers) {
/* 1032 */       if (modifier.getIndex() == index) {
/* 1033 */         modifier.setModifierID(modifierID);
/*      */         
/* 1035 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1041 */     if (!found) {
/* 1042 */       DBSkillModifier modifier = new DBSkillModifier();
/*      */       
/* 1044 */       modifier.setIndex(index);
/* 1045 */       modifier.setModifierID(modifierID);
/*      */       
/* 1047 */       this.skillModifiers.add(modifier);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemSlots getItemSlots() {
/* 1056 */     return this.slots;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPetFormulaLevel() {
/* 1064 */     return this.petFormulaLevel;
/*      */   }
/*      */   
/*      */   public String getPetBioID() {
/* 1068 */     return this.petBioID;
/*      */   }
/*      */   
/*      */   public String getPetDieSkillID() {
/* 1072 */     return this.petDieSkillID;
/*      */   }
/*      */   
/*      */   public List<DBPetSkill> getPetSkillList() {
/* 1076 */     return this.petSkills;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPetName() {
/* 1084 */     return this.petName;
/*      */   }
/*      */   
/*      */   public String getBioFormulaLife() {
/* 1088 */     return this.bioFormulaLife;
/*      */   }
/*      */   
/*      */   public String getBioFormulaMana() {
/* 1092 */     return this.bioFormulaMana;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFileDescription(String filedesc) {
/* 1100 */     this.fileDesc = filedesc;
/*      */   }
/*      */   
/*      */   public void setFileName(String fileName) {
/* 1104 */     this.fileName = fileName;
/*      */   }
/*      */   
/*      */   public void setRecordClass(String recordClass) {
/* 1108 */     this.recordClass = recordClass;
/*      */   }
/*      */   
/*      */   public void setTemplate(String template) {
/* 1112 */     this.template = template;
/*      */     
/* 1114 */     if (template != null) {
/* 1115 */       this.mastery = template.equals("database/templates/skill_mastery.tpl");
/*      */     }
/*      */   }
/*      */   
/*      */   public void setDLCRequirement(String dlcRequirement) {
/* 1120 */     dlcRequirement = this.dlcRequirement;
/*      */   }
/*      */   
/*      */   public void setDBFormulaSet(DBFormulaSet dbFormulaSet) {
/* 1124 */     this.dbFormulaSet = dbFormulaSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addFactionReputationStateTag(int index, String stateTag) {
/* 1132 */     boolean found = false;
/*      */     
/* 1134 */     for (DBFactionReputation reputation : this.factionReputations) {
/* 1135 */       if (reputation.getIndex() == index) {
/* 1136 */         reputation.setStateTag(stateTag);
/*      */         
/* 1138 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1144 */     if (!found) {
/* 1145 */       DBFactionReputation reputation = new DBFactionReputation();
/*      */       
/* 1147 */       reputation.setIndex(index);
/* 1148 */       reputation.setStateTag(stateTag);
/*      */       
/* 1150 */       this.factionReputations.add(reputation);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addFactionReputationValue(int index, int value) {
/* 1155 */     boolean found = false;
/*      */     
/* 1157 */     for (DBFactionReputation reputation : this.factionReputations) {
/* 1158 */       if (reputation.getIndex() == index) {
/* 1159 */         reputation.setValue(value);
/*      */         
/* 1161 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1167 */     if (!found) {
/* 1168 */       DBFactionReputation reputation = new DBFactionReputation();
/*      */       
/* 1170 */       reputation.setIndex(index);
/* 1171 */       reputation.setValue(value);
/*      */       
/* 1173 */       this.factionReputations.add(reputation);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addMasteryTier(int index, int value) {
/* 1178 */     DBEngineMasteryTier mt = new DBEngineMasteryTier(index, value);
/*      */     
/* 1180 */     this.masteryTiers.add(mt);
/*      */   }
/*      */   
/*      */   public void setFactionAltNeutralTag(String factionAltNeutralTag) {
/* 1184 */     this.factionAltNeutralTag = factionAltNeutralTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFactionBountyEnabled(boolean factionBountyEnabled) {
/* 1192 */     this.factionBountyEnabled = factionBountyEnabled;
/*      */   }
/*      */   
/*      */   public void setFactionQuestEnabled(boolean factionQuestEnabled) {
/* 1196 */     this.factionQuestEnabled = factionQuestEnabled;
/*      */   }
/*      */   
/*      */   public void setFactionTag(String factionTag) {
/* 1200 */     this.factionTag = factionTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSkillMaster(String classTableID) {
/* 1208 */     this.classTableIDs.add(classTableID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMerchantFactionID(String merchantFactionID) {
/* 1216 */     this.merchantFactionID = merchantFactionID;
/*      */   }
/*      */   
/*      */   public void setMerchantTableSetID(String merchantTableSetID) {
/* 1220 */     this.merchantTableSetID = merchantTableSetID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMerchantTableSetTableID(String tableID) {
/* 1228 */     this.merchantTableIDs.add(tableID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMerchantTableItemID(String itemID) {
/* 1236 */     this.merchantItemIDs.add(itemID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlayerBaseDex(int playerBaseDex) {
/* 1244 */     this.playerBaseDex = playerBaseDex;
/*      */   }
/*      */   
/*      */   public void setPlayerBaseInt(int playerBaseInt) {
/* 1248 */     this.playerBaseInt = playerBaseInt;
/*      */   }
/*      */   
/*      */   public void setPlayerBaseStr(int playerBaseStr) {
/* 1252 */     this.playerBaseStr = playerBaseStr;
/*      */   }
/*      */   
/*      */   public void setPlayerBaseLife(int playerBaseLife) {
/* 1256 */     this.playerBaseLife = playerBaseLife;
/*      */   }
/*      */   
/*      */   public void setPlayerBaseMana(int playerBaseMana) {
/* 1260 */     this.playerBaseMana = playerBaseMana;
/*      */   }
/*      */   
/*      */   public void addMastery(String tag, String masteryID) {
/* 1264 */     String s = tag.substring("skillTree".length());
/*      */     
/* 1266 */     int index = 0;
/*      */     try {
/* 1268 */       index = Integer.parseInt(s);
/*      */     }
/* 1270 */     catch (NumberFormatException ex) {
/*      */       return;
/*      */     } 
/*      */     
/* 1274 */     if (!DBEnginePlayerMasteries.containsSkillTreeID(this.masteries, masteryID)) {
/* 1275 */       this.masteries.add(new DBEnginePlayerMasteries(masteryID, index));
/*      */     }
/*      */   }
/*      */   
/*      */   public void setPlayerIncDex(int playerIncDex) {
/* 1280 */     this.playerIncDex = playerIncDex;
/*      */   }
/*      */   
/*      */   public void setPlayerIncInt(int playerIncInt) {
/* 1284 */     this.playerIncInt = playerIncInt;
/*      */   }
/*      */   
/*      */   public void setPlayerIncStr(int playerIncStr) {
/* 1288 */     this.playerIncStr = playerIncStr;
/*      */   }
/*      */   
/*      */   public void setPlayerIncLife(int playerIncLife) {
/* 1292 */     this.playerIncLife = playerIncLife;
/*      */   }
/*      */   
/*      */   public void setPlayerIncMana(int playerIncMana) {
/* 1296 */     this.playerIncMana = playerIncMana;
/*      */   }
/*      */   
/*      */   public void setPlayerMaxDevotion(int playerMaxDevotion) {
/* 1300 */     this.playerMaxDevotion = playerMaxDevotion;
/*      */   }
/*      */   
/*      */   public void setPlayerMaxLevel(int playerMaxLevel) {
/* 1304 */     this.playerMaxLevel = playerMaxLevel;
/*      */   }
/*      */   
/*      */   public void setXPFormula(String xpFormula) {
/* 1308 */     this.xpFormula = xpFormula;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addLevelStatPoints(int points, int index) {
/* 1316 */     DBEngineLevel.LevelStats stats = null;
/*      */     
/* 1318 */     int skillPoints = 0;
/* 1319 */     for (DBEngineLevel.LevelStats ls : this.levelStats) {
/* 1320 */       int p = ls.getSkillPoints();
/* 1321 */       if (p != 0) {
/* 1322 */         skillPoints = p;
/*      */       }
/*      */       
/* 1325 */       if (stats.getLevel() == index) {
/* 1326 */         stats = ls;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1332 */     boolean statExists = false;
/* 1333 */     if (stats == null) {
/* 1334 */       statExists = false;
/*      */     } else {
/* 1336 */       statExists = (stats.getLevel() == index);
/*      */     } 
/*      */     
/* 1339 */     if (statExists) {
/* 1340 */       stats.setStatPoints(points);
/*      */     } else {
/* 1342 */       stats = new DBEngineLevel.LevelStats();
/* 1343 */       stats.setLevel(index);
/* 1344 */       stats.setStatPoints(points);
/* 1345 */       stats.setSkillPoints(skillPoints);
/*      */       
/* 1347 */       this.levelStats.add(stats);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addLevelSkillPoints(int points, int index) {
/* 1352 */     DBEngineLevel.LevelStats stats = null;
/*      */     
/* 1354 */     int statPoints = 0;
/* 1355 */     for (DBEngineLevel.LevelStats ls : this.levelStats) {
/* 1356 */       int p = ls.getStatPoints();
/* 1357 */       if (p != 0) {
/* 1358 */         statPoints = p;
/*      */       }
/*      */       
/* 1361 */       if (ls.getLevel() == index) {
/* 1362 */         stats = ls;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1368 */     boolean statExists = false;
/* 1369 */     if (stats == null) {
/* 1370 */       statExists = false;
/*      */     } else {
/* 1372 */       statExists = (stats.getLevel() == index);
/*      */     } 
/*      */     
/* 1375 */     if (statExists) {
/* 1376 */       stats.setSkillPoints(points);
/*      */     } else {
/* 1378 */       stats = new DBEngineLevel.LevelStats();
/* 1379 */       stats.setLevel(index);
/* 1380 */       stats.setStatPoints(statPoints);
/* 1381 */       stats.setSkillPoints(points);
/*      */       
/* 1383 */       this.levelStats.add(stats);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setXOffset(int xOffset) {
/* 1392 */     this.xOffset = xOffset;
/*      */   }
/*      */   
/*      */   public void setXSize(int xSize) {
/* 1396 */     this.xSize = xSize;
/*      */   }
/*      */   
/*      */   public void setYOffset(int yOffset) {
/* 1400 */     this.yOffset = yOffset;
/*      */   }
/*      */   
/*      */   public void setYSize(int ySize) {
/* 1404 */     this.ySize = ySize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConstellationNameTag(String constellationNameTag) {
/* 1412 */     this.constellationNameTag = constellationNameTag;
/*      */   }
/*      */   
/*      */   public void getConstellationInfoTag(String constellationInfoTag) {
/* 1416 */     this.constellationInfoTag = constellationInfoTag;
/*      */   }
/*      */   
/*      */   public void addConstellationAffinityName(String tag, String name) {
/* 1420 */     boolean found = false;
/*      */     
/* 1422 */     boolean required = tag.startsWith("affinityRequired");
/* 1423 */     int index = 0;
/* 1424 */     String s = null;
/*      */     
/* 1426 */     if (required) {
/* 1427 */       s = tag.substring("affinityRequiredName".length());
/*      */     } else {
/* 1429 */       s = tag.substring("affinityGivenName".length());
/*      */     } 
/*      */     
/*      */     try {
/* 1433 */       index = Integer.parseInt(s);
/*      */     }
/* 1435 */     catch (NumberFormatException ex) {
/*      */       return;
/*      */     } 
/*      */     
/* 1439 */     for (DBConstellationAffinity dbAffinity : this.constellationAffinities) {
/* 1440 */       if (dbAffinity.getIndex() == index && dbAffinity
/* 1441 */         .isRequired() == required) {
/* 1442 */         dbAffinity.setAffinity(name);
/*      */         
/* 1444 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1450 */     if (!found) {
/* 1451 */       DBConstellationAffinity dbAffinity = new DBConstellationAffinity();
/*      */       
/* 1453 */       dbAffinity.setIndex(index);
/* 1454 */       dbAffinity.setRequired(required);
/* 1455 */       dbAffinity.setAffinity(name);
/*      */       
/* 1457 */       this.constellationAffinities.add(dbAffinity);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addConstellationAffinityPoints(String tag, int points) {
/* 1462 */     boolean found = false;
/*      */     
/* 1464 */     boolean required = tag.startsWith("affinityRequired");
/* 1465 */     int index = 0;
/* 1466 */     String s = null;
/*      */     
/* 1468 */     if (required) {
/* 1469 */       s = tag.substring("affinityRequired".length());
/*      */     } else {
/* 1471 */       s = tag.substring("affinityGiven".length());
/*      */     } 
/*      */     
/*      */     try {
/* 1475 */       index = Integer.parseInt(s);
/*      */     }
/* 1477 */     catch (NumberFormatException ex) {
/*      */       return;
/*      */     } 
/*      */     
/* 1481 */     for (DBConstellationAffinity dbAffinity : this.constellationAffinities) {
/* 1482 */       if (dbAffinity.getIndex() == index && dbAffinity
/* 1483 */         .isRequired() == required) {
/* 1484 */         dbAffinity.setPoints(points);
/*      */         
/* 1486 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1492 */     if (!found) {
/* 1493 */       DBConstellationAffinity dbAffinity = new DBConstellationAffinity();
/*      */       
/* 1495 */       dbAffinity.setIndex(index);
/* 1496 */       dbAffinity.setRequired(required);
/* 1497 */       dbAffinity.setPoints(points);
/*      */       
/* 1499 */       this.constellationAffinities.add(dbAffinity);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addConstellationStar(String tag, String skillID) {
/* 1504 */     int index = 0;
/* 1505 */     String s = null;
/*      */     
/* 1507 */     s = tag.substring("devotionButton".length());
/*      */     
/*      */     try {
/* 1510 */       index = Integer.parseInt(s);
/*      */     }
/* 1512 */     catch (NumberFormatException ex) {
/*      */       return;
/*      */     } 
/*      */     
/* 1516 */     DBConstellationStar dbStar = new DBConstellationStar();
/*      */     
/* 1518 */     dbStar.setIndex(index);
/* 1519 */     dbStar.setButtonID(skillID);
/*      */     
/* 1521 */     this.constellationStars.add(dbStar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShrineName(String shrineNameTag) {
/* 1529 */     this.shrineName = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_SHRINES, shrineNameTag, false);
/*      */   }
/*      */   
/*      */   public void setShrineNormalDisabled(boolean shrineNormalDisabled) {
/* 1533 */     this.shrineNormalDisabled = shrineNormalDisabled;
/*      */   }
/*      */   
/*      */   public void setShrineNormalLocked(boolean shrineNormalLocked) {
/* 1537 */     this.shrineNormalLocked = shrineNormalLocked;
/*      */   }
/*      */   
/*      */   public void setShrineEpicDisabled(boolean shrineEpicDisabled) {
/* 1541 */     this.shrineEpicDisabled = shrineEpicDisabled;
/*      */   }
/*      */   
/*      */   public void setShrineEpicLocked(boolean shrineEpicLocked) {
/* 1545 */     this.shrineEpicLocked = shrineEpicLocked;
/*      */   }
/*      */   
/*      */   public void setShrineLegendaryDisabled(boolean shrineLegendaryDisabled) {
/* 1549 */     this.shrineLegendaryDisabled = shrineLegendaryDisabled;
/*      */   }
/*      */   
/*      */   public void setShrineLegendaryLocked(boolean shrineLegendaryLocked) {
/* 1553 */     this.shrineLegendaryLocked = shrineLegendaryLocked;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setButtonSkillID(String buttonSkillID) {
/* 1561 */     this.buttonSkillID = buttonSkillID;
/*      */   }
/*      */   
/*      */   public void setPosX(int posX) {
/* 1565 */     this.posX = posX;
/*      */   }
/*      */   
/*      */   public void setPosY(int posY) {
/* 1569 */     this.posY = posY;
/*      */   }
/*      */   
/*      */   public void setOffsetX(int offsetX) {
/* 1573 */     this.offsetX = offsetX;
/*      */   }
/*      */   
/*      */   public void setOffsetY(int offsetY) {
/* 1577 */     this.offsetY = offsetY;
/*      */   }
/*      */   
/*      */   public void setCircularButton(boolean circularButton) {
/* 1581 */     this.circularButton = circularButton;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMasteryBarID(String masteryBarID) {
/* 1589 */     this.masteryBarID = masteryBarID;
/*      */   }
/*      */   
/*      */   public void setSkillPaneID(String skillPaneID) {
/* 1593 */     this.skillPaneID = skillPaneID;
/*      */   }
/*      */   
/*      */   public void setMasteryBitmapID(String masteryBitmapID) {
/* 1597 */     this.masteryBitmapID = masteryBitmapID;
/*      */   }
/*      */   
/*      */   public void addSkillButton(String skillButton) {
/* 1601 */     this.skillButtons.add(skillButton);
/*      */   }
/*      */   
/*      */   public void setTitleTag(String titleTag) {
/* 1605 */     this.titleTag = titleTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMasterySkill(String skillID, int index) {
/* 1613 */     DBSkillTreeAlloc alloc = new DBSkillTreeAlloc(skillID, index);
/*      */     
/* 1615 */     this.masterySkillIDs.add(alloc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSkillBuffID(String skillBuffID) {
/* 1623 */     this.skillBuffID = skillBuffID;
/*      */   }
/*      */   
/*      */   public void setSkillDescription(String skillBaseDescription) {
/* 1627 */     this.skillDescription = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_SKILLS, skillBaseDescription, true);
/*      */   }
/*      */   
/*      */   public void setDevotion(boolean devotion) {
/* 1631 */     this.devotion = devotion;
/*      */   }
/*      */   
/*      */   public void setSkillTier(int skillTier) {
/* 1635 */     this.skillTier = skillTier;
/*      */   }
/*      */   
/*      */   public void setSkillMaxLevel(int maxLevel) {
/* 1639 */     this.maxLevel = maxLevel;
/*      */   }
/*      */   
/*      */   public void setSkillBitmapDownID(String bitmapDownID) {
/* 1643 */     this.bitmapDownID = bitmapDownID;
/*      */   }
/*      */   
/*      */   public void setSkillBitmapUpID(String bitmapUpID) {
/* 1647 */     this.bitmapUpID = bitmapUpID;
/*      */   }
/*      */   
/*      */   public void setSkillLevel(int skillLevel) {
/* 1651 */     this.skillLevel = skillLevel;
/*      */   }
/*      */   
/*      */   public void setSkillDuration(int skillDuration) {
/* 1655 */     this.skillDuration = skillDuration;
/*      */   }
/*      */   
/*      */   public void setSkillBonusIncrement(boolean bonusIncrement) {
/* 1659 */     this.bonusIncrement = bonusIncrement;
/*      */   }
/*      */   
/*      */   public void setSkillModified(boolean skillModified) {
/* 1663 */     this.skillModified = skillModified;
/*      */   }
/*      */   
/*      */   public void setDependencyAll(boolean skillDependencyAll) {
/* 1667 */     this.skillDependencyAll = skillDependencyAll;
/*      */   }
/*      */   
/*      */   public void addSpawnPet(int index, String petID) {
/* 1671 */     DBSkillSpawn pet = new DBSkillSpawn(index, petID);
/*      */     
/* 1673 */     this.dbSpawnPets.add(pet);
/*      */   }
/*      */   
/*      */   public void addSkillXPLevel(String xp) {
/*      */     try {
/* 1678 */       Integer i = Integer.valueOf(xp);
/*      */       
/* 1680 */       this.skillXPLevels.add(i);
/*      */       
/* 1682 */       if (i.intValue() > 0) this.devotion = true;
/*      */     
/* 1684 */     } catch (NumberFormatException numberFormatException) {}
/*      */   }
/*      */   
/*      */   public void addSkillConnectionOff(String connector, int index) {
/* 1688 */     boolean found = false;
/*      */     
/* 1690 */     for (DBSkillConnector connection : this.skillConnections) {
/* 1691 */       if (connection.getIndex() == index) {
/* 1692 */         found = true;
/*      */         
/* 1694 */         connection.setConnectionOffID(connector);
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1700 */     if (!found) {
/* 1701 */       DBSkillConnector connection = new DBSkillConnector(index, connector, null);
/*      */       
/* 1703 */       this.skillConnections.add(connection);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addSkillConnectionOn(String connector, int index) {
/* 1708 */     boolean found = false;
/*      */     
/* 1710 */     for (DBSkillConnector connection : this.skillConnections) {
/* 1711 */       if (connection.getIndex() == index) {
/* 1712 */         found = true;
/*      */         
/* 1714 */         connection.setConnectionOnID(connector);
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1720 */     if (!found) {
/* 1721 */       DBSkillConnector connection = new DBSkillConnector(index, null, connector);
/*      */       
/* 1723 */       this.skillConnections.add(connection);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addSkillDependency(String skillDep) {
/* 1728 */     DBSkillDependency dep = new DBSkillDependency(null, skillDep);
/*      */     
/* 1730 */     this.skillDependencies.add(dep);
/*      */   }
/*      */   
/*      */   public void setSkillNameTag(String skillNameTag) {
/* 1734 */     this.skillNameTag = skillNameTag;
/*      */     
/* 1736 */     if (skillNameTag != null) {
/* 1737 */       this.skillName = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_SKILLS, skillNameTag, false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTriggerChance(int triggerChance) {
/* 1746 */     this.triggerChance = triggerChance;
/*      */   }
/*      */   
/*      */   public void setTriggerType(String triggerType) {
/* 1750 */     this.triggerType = triggerType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLootRandomCost(int lootRandomCost) {
/* 1758 */     this.lootRandomCost = lootRandomCost;
/*      */   }
/*      */   
/*      */   public void setLootRandomName(String lootRandomName) {
/* 1762 */     this.lootRandomName = lootRandomName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConversionIn(String convertIn) {
/* 1770 */     if (convertIn == null)
/*      */       return; 
/* 1772 */     if (convertIn.equals("Physical;Pierce;Elemental;Cold;Fire;Poison;Lightning;Life;Chaos;Aether;Stun"))
/*      */       return; 
/* 1774 */     this.convertIn = convertIn;
/*      */   }
/*      */   
/*      */   public void setConversionOut(String convertOut) {
/* 1778 */     if (convertOut == null)
/*      */       return; 
/* 1780 */     if (convertOut.equals("Physical;Pierce;Elemental;Cold;Fire;Poison;Lightning;Life;Chaos;Aether;Stun"))
/*      */       return; 
/* 1782 */     this.convertOut = convertOut;
/*      */   }
/*      */   
/*      */   public void setConversionIn2(String convertIn) {
/* 1786 */     if (convertIn == null)
/*      */       return; 
/* 1788 */     if (convertIn.equals("Physical;Pierce;Elemental;Cold;Fire;Poison;Lightning;Life;Chaos;Aether;Stun"))
/*      */       return; 
/* 1790 */     this.convertIn2 = convertIn;
/*      */   }
/*      */   
/*      */   public void setConversionOut2(String convertOut) {
/* 1794 */     if (convertOut == null)
/*      */       return; 
/* 1796 */     if (convertOut.equals("Physical;Pierce;Elemental;Cold;Fire;Poison;Lightning;Life;Chaos;Aether;Stun"))
/*      */       return; 
/* 1798 */     this.convertOut2 = convertOut;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRace(String tag, String name) {
/* 1806 */     boolean found = false;
/*      */     
/* 1808 */     for (DBStatBonusRace race : this.dbStatBonusRaces) {
/* 1809 */       if (race.getRaceTag().equals(tag)) {
/* 1810 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1816 */     if (!found) {
/* 1817 */       DBStatBonusRace race = new DBStatBonusRace();
/*      */       
/* 1819 */       race.setID(this.fileName);
/* 1820 */       race.setRaceTag(tag);
/* 1821 */       race.setRaceName(name);
/*      */       
/* 1823 */       this.dbStatBonusRaces.add(race);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setItemSkillID(String itemSkillID) {
/* 1828 */     this.itemSkillID = itemSkillID;
/*      */   }
/*      */   
/*      */   public void setItemSkillLevelFormula(String itemSkillLevelFormula) {
/* 1832 */     this.itemSkillLevelFormula = itemSkillLevelFormula;
/*      */   }
/*      */   
/*      */   public void setItemSkillLevel(int itemSkillLevel) {
/* 1836 */     this.itemSkillLevel = itemSkillLevel;
/*      */   }
/*      */   
/*      */   public void setOffensiveChance(int offensiveChance) {
/* 1840 */     this.offensiveChance = offensiveChance;
/*      */   }
/*      */   
/*      */   public void setPetBonusID(String petBonusID) {
/* 1844 */     this.petBonusID = petBonusID;
/*      */   }
/*      */   
/*      */   public void setRarity(String rarity) {
/* 1848 */     if (rarity == null)
/*      */       return; 
/* 1850 */     if (rarity.equals("Common;Magical;Rare;Epic;Legendary;Broken;"))
/* 1851 */       return;  if (rarity.equals("Common;Magical;Rare;Epic;Legendary;Quest"))
/*      */       return; 
/* 1853 */     this.rarity = rarity;
/*      */   }
/*      */   
/*      */   public void setRequiredLevel(int reqLevel) {
/* 1857 */     this.reqLevel = reqLevel;
/*      */   }
/*      */   
/*      */   public void setRetaliationChance(int retaliationChance) {
/* 1861 */     this.retaliationChance = retaliationChance;
/*      */   }
/*      */   
/*      */   public void setRNGPercent(int rngPercent) {
/* 1865 */     this.rngPercent = rngPercent;
/*      */   }
/*      */   
/*      */   public void setSkillControllerID(String skillControllerID) {
/* 1869 */     this.skillControllerID = skillControllerID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRandomizerAffixIDEntry(int index, String affixID) {
/* 1877 */     boolean found = false;
/*      */     
/* 1879 */     for (DBAffixSet.DBEntry entry : this.randomizers) {
/* 1880 */       if (entry.getIndex() == index) {
/* 1881 */         entry.setAffixID(affixID);
/*      */         
/* 1883 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1889 */     if (!found) {
/* 1890 */       DBAffixSet.DBEntry entry = new DBAffixSet.DBEntry();
/*      */       
/* 1892 */       entry.setIndex(index);
/* 1893 */       entry.setAffixID(affixID);
/*      */       
/* 1895 */       this.randomizers.add(entry);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addRandomizerMaxLevelEntry(int index, int level) {
/* 1900 */     boolean found = false;
/*      */     
/* 1902 */     for (DBAffixSet.DBEntry entry : this.randomizers) {
/* 1903 */       if (entry.getIndex() == index) {
/* 1904 */         entry.setMaxLevel(level);
/*      */         
/* 1906 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1912 */     if (!found) {
/* 1913 */       DBAffixSet.DBEntry entry = new DBAffixSet.DBEntry();
/*      */       
/* 1915 */       entry.setIndex(index);
/* 1916 */       entry.setMaxLevel(level);
/*      */       
/* 1918 */       this.randomizers.add(entry);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addRandomizerMinLevelEntry(int index, int level) {
/* 1923 */     boolean found = false;
/*      */     
/* 1925 */     for (DBAffixSet.DBEntry entry : this.randomizers) {
/* 1926 */       if (entry.getIndex() == index) {
/* 1927 */         entry.setMinLevel(level);
/*      */         
/* 1929 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1935 */     if (!found) {
/* 1936 */       DBAffixSet.DBEntry entry = new DBAffixSet.DBEntry();
/*      */       
/* 1938 */       entry.setIndex(index);
/* 1939 */       entry.setMinLevel(level);
/*      */       
/* 1941 */       this.randomizers.add(entry);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addItemSetItemID(String itemID) {
/* 1950 */     this.itemSetItemIDs.add(itemID);
/*      */   }
/*      */   
/*      */   public void setItemSetDescriptionTag(String itemSetDescTag) {
/* 1954 */     this.itemSetDescTag = itemSetDescTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemSetSkillModifierLevel(int value, int index) {
/* 1961 */     if (value == 1 && 
/* 1962 */       this.skillModifierLevel == 0) this.skillModifierLevel = index;
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemSetNameTag(String itemSetNameTag) {
/* 1971 */     this.itemSetNameTag = itemSetNameTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addTableItemID(String itemID) {
/* 1979 */     DBLootTableItemAlloc alloc = new DBLootTableItemAlloc(this.fileName, itemID);
/* 1980 */     this.tableItems.add(alloc);
/*      */   }
/*      */   
/*      */   public void addTableAffixSetID(int index, int type, String affixSetID) {
/* 1984 */     boolean found = false;
/*      */     
/* 1986 */     for (DBLootTableAffixSetAlloc affixSet : this.tableAffixSets) {
/* 1987 */       if (affixSet.getIndex() == index && affixSet
/* 1988 */         .getAffixType() == type) {
/* 1989 */         affixSet.setAffixSetID(affixSetID);
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
/* 2002 */       affixSet.setAffixSetID(affixSetID);
/*      */       
/* 2004 */       this.tableAffixSets.add(affixSet);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addTableMaxLevel(int index, int type, int levelMax) {
/* 2009 */     boolean found = false;
/*      */     
/* 2011 */     for (DBLootTableAffixSetAlloc affixSet : this.tableAffixSets) {
/* 2012 */       if (affixSet.getIndex() == index && affixSet
/* 2013 */         .getAffixType() == type) {
/* 2014 */         affixSet.setMaxLevel(levelMax);
/*      */         
/* 2016 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 2022 */     if (!found) {
/* 2023 */       DBLootTableAffixSetAlloc affixSet = new DBLootTableAffixSetAlloc();
/*      */       
/* 2025 */       affixSet.setIndex(index);
/* 2026 */       affixSet.setAffixType(type);
/* 2027 */       affixSet.setMaxLevel(levelMax);
/*      */       
/* 2029 */       this.tableAffixSets.add(affixSet);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addTableMinLevel(int index, int type, int levelMin) {
/* 2034 */     boolean found = false;
/*      */     
/* 2036 */     for (DBLootTableAffixSetAlloc affixSet : this.tableAffixSets) {
/* 2037 */       if (affixSet.getIndex() == index && affixSet
/* 2038 */         .getAffixType() == type) {
/* 2039 */         affixSet.setMinLevel(levelMin);
/*      */         
/* 2041 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 2047 */     if (!found) {
/* 2048 */       DBLootTableAffixSetAlloc affixSet = new DBLootTableAffixSetAlloc();
/*      */       
/* 2050 */       affixSet.setIndex(index);
/* 2051 */       affixSet.setAffixType(type);
/* 2052 */       affixSet.setMinLevel(levelMin);
/*      */       
/* 2054 */       this.tableAffixSets.add(affixSet);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setTableNormalPrefixSuffix(boolean nPre_nSuf) {
/* 2059 */     this.nPre_nSuf = nPre_nSuf;
/*      */   }
/*      */   
/*      */   public void setTableNormalPrefixRareSuffix(boolean nPre_rSuf) {
/* 2063 */     this.nPre_rSuf = nPre_rSuf;
/*      */   }
/*      */   
/*      */   public void setTableRarePrefixNormalSuffix(boolean rPre_nSuf) {
/* 2067 */     this.rPre_nSuf = rPre_nSuf;
/*      */   }
/*      */   
/*      */   public void setTableRarePrefixSuffix(boolean rPre_rSuf) {
/* 2071 */     this.rPre_rSuf = rPre_rSuf;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addTableSetTableID(String tableID) {
/* 2079 */     boolean found = false;
/*      */     
/* 2081 */     for (DBLootTableSetAlloc entry : this.tableSetAllocs) {
/* 2082 */       if (entry.getTableID() == null) {
/* 2083 */         entry.setTableID(tableID);
/*      */         
/* 2085 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 2091 */     if (!found) {
/* 2092 */       DBLootTableSetAlloc entry = new DBLootTableSetAlloc();
/*      */       
/* 2094 */       entry.setTableID(tableID);
/*      */       
/* 2096 */       this.tableSetAllocs.add(entry);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addTableSetMinLevel(int levelMin) {
/* 2101 */     boolean found = false;
/*      */     
/* 2103 */     for (DBLootTableSetAlloc entry : this.tableSetAllocs) {
/* 2104 */       if (entry.getMinLevel() == -1) {
/* 2105 */         entry.setMinLevel(levelMin);
/*      */         
/* 2107 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 2113 */     if (!found) {
/* 2114 */       DBLootTableSetAlloc entry = new DBLootTableSetAlloc();
/*      */       
/* 2116 */       entry.setMinLevel(levelMin);
/*      */       
/* 2118 */       this.tableSetAllocs.add(entry);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addFormulaSetFormula(String formulaID, String formula) {
/* 2127 */     if (formula == null)
/* 2128 */       return;  if (formula.isEmpty())
/*      */       return; 
/* 2130 */     DBFormula dbf = new DBFormula();
/*      */     
/* 2132 */     dbf.setFormulaID(formulaID);
/* 2133 */     dbf.setFormula(formula);
/*      */     
/* 2135 */     this.formulaSetFormulas.add(dbf);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setArmorClass(String armorClass) {
/* 2143 */     if (armorClass == null)
/*      */       return; 
/* 2145 */     if (armorClass.equals("Caster;Light;Heavy;"))
/*      */       return; 
/* 2147 */     this.armorClass = armorClass;
/*      */   }
/*      */   
/*      */   public void setArtifactClass(String artifactClass) {
/* 2151 */     if (artifactClass == null)
/*      */       return; 
/* 2153 */     if (artifactClass.equals("Lesser;Greater;Divine;"))
/*      */       return; 
/* 2155 */     this.artifactClass = artifactClass;
/*      */   }
/*      */   
/*      */   public void setMeshID(String meshID) {
/* 2159 */     this.meshID = meshID;
/*      */   }
/*      */   
/*      */   public void setBaseTextureID(String baseTextureID) {
/* 2163 */     this.baseTextureID = baseTextureID;
/*      */   }
/*      */   
/*      */   public void addBaseTextureID(String baseTextureID) {
/* 2167 */     if (this.baseTextureID == null) {
/* 2168 */       this.baseTextureID = baseTextureID;
/*      */     } else {
/* 2170 */       this.baseTextureID += ";" + baseTextureID;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setBumpTextureID(String bumpTextureID) {
/* 2175 */     this.bumpTextureID = bumpTextureID;
/*      */   }
/*      */   
/*      */   public void addBumpTextureID(String bumpTextureID) {
/* 2179 */     if (this.bumpTextureID == null) {
/* 2180 */       this.bumpTextureID = bumpTextureID;
/*      */     } else {
/* 2182 */       this.bumpTextureID += ";" + bumpTextureID;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setGlowTextureID(String glowTextureID) {
/* 2187 */     this.glowTextureID = glowTextureID;
/*      */   }
/*      */   
/*      */   public void addGlowTextureID(String glowTextureID) {
/* 2191 */     if (this.glowTextureID == null) {
/* 2192 */       this.glowTextureID = glowTextureID;
/*      */     } else {
/* 2194 */       this.glowTextureID += ";" + glowTextureID;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setShaderID(String shaderID) {
/* 2199 */     this.shaderID = shaderID;
/*      */   }
/*      */   
/*      */   public void setBitmapID(String bitmapID) {
/* 2203 */     this.bitmapID = bitmapID;
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
/* 2216 */     this.shardBitmapID = shardBitmapID;
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
/* 2229 */     this.itemDescriptionTag = itemDescriptionTag;
/*      */     
/* 2231 */     this.itemDescription = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_ITEMS, itemDescriptionTag, true);
/*      */ 
/*      */     
/* 2234 */     if (this.itemDescription == null) {
/* 2235 */       this.itemDescription = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_STORY, itemDescriptionTag, true);
/*      */     }
/*      */   }
/*      */   
/*      */   private String insertLineBreaks(String s) {
/* 2240 */     String newS = "";
/* 2241 */     int len = 0;
/*      */     
/* 2243 */     int posBlank = s.indexOf(" ");
/* 2244 */     while (posBlank != -1) {
/* 2245 */       int posBreak = s.indexOf("<br>");
/*      */       
/* 2247 */       if (posBreak != -1 && posBreak < posBlank) {
/* 2248 */         String word = s.substring(0, posBreak + 4);
/* 2249 */         s = s.substring(posBreak + 4);
/* 2250 */         newS = newS + word;
/*      */         
/* 2252 */         len = 0;
/*      */       } else {
/* 2254 */         String word = s.substring(0, posBlank);
/* 2255 */         s = s.substring(posBlank + 1);
/* 2256 */         newS = newS + word;
/*      */         
/* 2258 */         len += word.length();
/* 2259 */         if (len < 80) {
/* 2260 */           newS = newS + " ";
/*      */         } else {
/* 2262 */           newS = newS + "<br>";
/* 2263 */           len = 0;
/*      */         } 
/*      */       } 
/*      */       
/* 2267 */       posBlank = s.indexOf(" ");
/*      */     } 
/*      */     
/* 2270 */     newS = newS + s;
/*      */     
/* 2272 */     return newS;
/*      */   }
/*      */   
/*      */   public void setItemNameTag(String itemNameTag) {
/* 2276 */     this.itemNameTag = itemNameTag;
/*      */     
/* 2278 */     String s = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_ITEMS, itemNameTag, false);
/*      */ 
/*      */     
/* 2281 */     if (s == null) {
/* 2282 */       s = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_STORY, itemNameTag, false);
/*      */     }
/*      */     
/* 2285 */     this.genderCode = -1;
/*      */     
/* 2287 */     if (s != null && 
/* 2288 */       s.startsWith("[")) {
/*      */       
/* 2290 */       String code = s.substring(0, 4);
/* 2291 */       s = s.substring(4);
/*      */       
/* 2293 */       this.genderCode = ARZDecompress.getGenderCode(code);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2299 */     if (this.genderCode == -1) this.genderCode = 0;
/*      */     
/* 2301 */     this.itemName = s;
/*      */   }
/*      */   
/*      */   public void setQualityTag(String qualityTag) {
/* 2305 */     this.qualityTag = qualityTag;
/*      */   }
/*      */   
/*      */   public void setStyleTag(String styleTag) {
/* 2309 */     this.styleTag = styleTag;
/*      */   }
/*      */   
/*      */   public void setItemSetID(String itemSetID) {
/* 2313 */     this.itemSetID = itemSetID;
/*      */   }
/*      */   
/*      */   public void setArtifactID(String artifactID) {
/* 2317 */     this.artifactID = artifactID;
/*      */   }
/*      */   
/*      */   public void setBonusAffixSetID(String bonusAffixSetID) {
/* 2321 */     this.bonusAffixSetID = bonusAffixSetID;
/*      */   }
/*      */   
/*      */   public void setCostFormulaSetID(String costFormulaSetID) {
/* 2325 */     this.costFormulaSetID = costFormulaSetID;
/*      */   }
/*      */   
/*      */   public void setSoulbound(boolean soulbound) {
/* 2329 */     this.soulbound = soulbound;
/*      */   }
/*      */   
/*      */   public void setHidePrefix(boolean hidePrefix) {
/* 2333 */     this.hidePrefix = hidePrefix;
/*      */   }
/*      */   
/*      */   public void setHideSuffix(boolean hideSuffix) {
/* 2337 */     this.hideSuffix = hideSuffix;
/*      */   }
/*      */   
/*      */   public void setQuestItem(boolean questItem) {
/* 2341 */     this.questItem = questItem;
/*      */   }
/*      */   
/*      */   public void setCannotPickup(boolean cannotPickup) {
/* 2345 */     this.cannotPickup = cannotPickup;
/*      */   }
/*      */   
/*      */   public void setItemLevel(int itemLevel) {
/* 2349 */     this.itemLevel = itemLevel;
/*      */   }
/*      */   
/*      */   public void setPlusAllSkills(int plusAllSkills) {
/* 2353 */     this.plusAllSkills = plusAllSkills;
/*      */   }
/*      */   
/*      */   public void setComponentPieces(int componentPieces) {
/* 2357 */     this.componentPieces = componentPieces;
/*      */   }
/*      */   
/*      */   public void setMaxStackSize(int maxStackSize) {
/* 2361 */     this.maxStackSize = maxStackSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSlotAxe1H(boolean slotAxe1H) {
/* 2369 */     this.slots.slotAxe1H = slotAxe1H;
/*      */   }
/*      */   
/*      */   public void setSlotAxe2H(boolean slotAxe2H) {
/* 2373 */     this.slots.slotAxe2H = slotAxe2H;
/*      */   }
/*      */   
/*      */   public void setSlotDagger1H(boolean slotDagger1H) {
/* 2377 */     this.slots.slotDagger1H = slotDagger1H;
/*      */   }
/*      */   
/*      */   public void setSlotMace1H(boolean slotMace1H) {
/* 2381 */     this.slots.slotMace1H = slotMace1H;
/*      */   }
/*      */   
/*      */   public void setSlotMace2H(boolean slotMace2H) {
/* 2385 */     this.slots.slotMace2H = slotMace2H;
/*      */   }
/*      */   
/*      */   public void setSlotScepter1H(boolean slotScepter1H) {
/* 2389 */     this.slots.slotScepter1H = slotScepter1H;
/*      */   }
/*      */   
/*      */   public void setSlotSpear2H(boolean slotSpear2H) {
/* 2393 */     this.slots.slotSpear1H = slotSpear2H;
/*      */   }
/*      */   
/*      */   public void setSlotStaff2H(boolean slotStaff2H) {
/* 2397 */     this.slots.slotStaff2H = slotStaff2H;
/*      */   }
/*      */   
/*      */   public void setSlotSword1H(boolean slotSword1H) {
/* 2401 */     this.slots.slotSword1H = slotSword1H;
/*      */   }
/*      */   
/*      */   public void setSlotSword2H(boolean slotSword2H) {
/* 2405 */     this.slots.slotSword2H = slotSword2H;
/*      */   }
/*      */   
/*      */   public void setSlotRanged1H(boolean slotRanged1H) {
/* 2409 */     this.slots.slotRanged1H = slotRanged1H;
/*      */   }
/*      */   
/*      */   public void setSlotRanged2H(boolean slotRanged2H) {
/* 2413 */     this.slots.slotRanged2H = slotRanged2H;
/*      */   }
/*      */   
/*      */   public void setSlotShield(boolean slotShield) {
/* 2417 */     this.slots.slotShield = slotShield;
/*      */   }
/*      */   
/*      */   public void setSlotOffhand(boolean slotOffhand) {
/* 2421 */     this.slots.slotOffhand = slotOffhand;
/*      */   }
/*      */   
/*      */   public void setSlotAmulet(boolean slotAmulet) {
/* 2425 */     this.slots.slotAmulet = slotAmulet;
/*      */   }
/*      */   
/*      */   public void setSlotBelt(boolean slotBelt) {
/* 2429 */     this.slots.slotBelt = slotBelt;
/*      */   }
/*      */   
/*      */   public void setSlotMedal(boolean slotMedal) {
/* 2433 */     this.slots.slotMedal = slotMedal;
/*      */   }
/*      */   
/*      */   public void setSlotRing(boolean slotRing) {
/* 2437 */     this.slots.slotRing = slotRing;
/*      */   }
/*      */   
/*      */   public void setSlotHead(boolean slotHead) {
/* 2441 */     this.slots.slotHead = slotHead;
/*      */   }
/*      */   
/*      */   public void setSlotShoulders(boolean slotShoulders) {
/* 2445 */     this.slots.slotShoulders = slotShoulders;
/*      */   }
/*      */   
/*      */   public void setSlotChest(boolean slotChest) {
/* 2449 */     this.slots.slotChest = slotChest;
/*      */   }
/*      */   
/*      */   public void setSlotHands(boolean slotHands) {
/* 2453 */     this.slots.slotHands = slotHands;
/*      */   }
/*      */   
/*      */   public void setSlotLegs(boolean slotLegs) {
/* 2457 */     this.slots.slotLegs = slotLegs;
/*      */   }
/*      */   
/*      */   public void setSlotFeet(boolean slotFeet) {
/* 2461 */     this.slots.slotFeet = slotFeet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DBStat getCreateDBStat(String search, int index) {
/* 2469 */     DBStat dbStat = null;
/* 2470 */     DBStat first = null;
/* 2471 */     boolean found = false;
/*      */     
/* 2473 */     int level = index + 1;
/* 2474 */     for (DBStat dbs : this.dbStats) {
/* 2475 */       if (dbs == null || 
/* 2476 */         !dbs.getStatType().equals(search))
/*      */         continue; 
/* 2478 */       if (first == null) first = dbs;
/*      */       
/* 2480 */       if (dbs.getSkillLevel() == level) {
/* 2481 */         dbStat = dbs;
/* 2482 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 2488 */     if (!found) {
/* 2489 */       if (first == null) {
/* 2490 */         dbStat = new DBStat();
/*      */       } else {
/* 2492 */         dbStat = first.clone();
/*      */       } 
/*      */       
/* 2495 */       dbStat.setStatType(search);
/* 2496 */       dbStat.setSkillLevel(level);
/*      */       
/* 2498 */       this.dbStats.add(dbStat);
/*      */     } 
/*      */     
/* 2501 */     return dbStat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processItemSkillLevelFormula() {
/* 2509 */     if (this.itemSkillLevelFormula == null)
/*      */       return; 
/*      */     try {
/* 2512 */       ExpressionBuilder builder = new ExpressionBuilder(this.itemSkillLevelFormula);
/* 2513 */       builder = builder.variables(new String[] { "itemLevel" });
/*      */       
/* 2515 */       Expression expression = builder.build();
/*      */       
/* 2517 */       expression.setVariable("itemLevel", getRequiredLevel());
/*      */       
/* 2519 */       int value = (int)expression.evaluate();
/*      */       
/* 2521 */       this.itemSkillLevel = value;
/*      */     }
/* 2523 */     catch (Exception exception) {}
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
/* 2571 */     this.skillNameTag = petNameTag;
/*      */     
/* 2573 */     if (petNameTag != null) {
/* 2574 */       this.petName = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_PETS, petNameTag, false);
/*      */     }
/*      */   }
/*      */   
/*      */   public void setPetFormulaLevel(String petFormulaLevel) {
/* 2579 */     this.petFormulaLevel = petFormulaLevel;
/*      */   }
/*      */   
/*      */   public void setPetBioID(String petBioID) {
/* 2583 */     this.petBioID = petBioID;
/*      */   }
/*      */   
/*      */   public void setPetDieSkillID(String petDieSkillID) {
/* 2587 */     this.petDieSkillID = petDieSkillID;
/*      */   }
/*      */   
/*      */   public void addPetSkillsSkillID(int index, String skillID) {
/* 2591 */     boolean found = false;
/*      */     
/* 2593 */     for (DBPetSkill psk : this.petSkills) {
/* 2594 */       if (psk.getIndex() == index) {
/* 2595 */         psk.setSkillID(skillID);
/*      */         
/* 2597 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 2603 */     if (!found) {
/* 2604 */       DBPetSkill psk = new DBPetSkill();
/*      */       
/* 2606 */       psk.setIndex(index);
/* 2607 */       psk.setSkillID(skillID);
/*      */       
/* 2609 */       this.petSkills.add(psk);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addPetSkillsFormulaLevel(int index, String formulaLevel) {
/* 2614 */     boolean found = false;
/*      */     
/* 2616 */     for (DBPetSkill psk : this.petSkills) {
/* 2617 */       if (psk.getIndex() == index) {
/* 2618 */         psk.setFormulaLevel(formulaLevel);
/*      */         
/* 2620 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 2626 */     if (!found) {
/* 2627 */       DBPetSkill psk = new DBPetSkill();
/*      */       
/* 2629 */       psk.setIndex(index);
/* 2630 */       psk.setFormulaLevel(formulaLevel);
/*      */       
/* 2632 */       this.petSkills.add(psk);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBioFormulaLife(String bioFormulaLife) {
/* 2641 */     this.bioFormulaLife = bioFormulaLife;
/*      */   }
/*      */   
/*      */   public void setBioFormulaMana(String bioFormulaMana) {
/* 2645 */     this.bioFormulaMana = bioFormulaMana;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean processDir(String dir) {
/* 2650 */     if (dir.startsWith("records/controllers/itemskills/basetemplates/")) return false; 
/* 2651 */     if (dir.startsWith("records/default/")) return false; 
/* 2652 */     if (dir.startsWith("records/fx/")) return false; 
/* 2653 */     if (dir.startsWith("records/game/old/")) return false; 
/* 2654 */     if (dir.startsWith("records/game/reqformula031116/")) return false; 
/* 2655 */     if (dir.startsWith("records/ingameui/")) return false;
/*      */     
/* 2657 */     if (dir.startsWith("records/items/lootchests/")) return false; 
/* 2658 */     if (dir.startsWith("records/items/testitems/")) return false; 
/* 2659 */     if (dir.startsWith("records/items/crafting/crafting_")) return false; 
/* 2660 */     if (dir.startsWith("records/level art/")) return false;
/*      */     
/* 2662 */     if (dir.startsWith("records/proxies/")) return false; 
/* 2663 */     if (dir.startsWith("records/sandbox/")) return false; 
/* 2664 */     if (dir.startsWith("records/soundgenerators/")) return false; 
/* 2665 */     if (dir.startsWith("records/sounds/")) return false; 
/* 2666 */     if (dir.startsWith("records/terraintextures/")) return false; 
/* 2667 */     if (dir.startsWith("records/triggervolumes/")) return false; 
/* 2668 */     if (dir.startsWith("records/watertype/")) return false; 
/* 2669 */     if (dir.startsWith("records/items/gearweapons/staff/crafting/")) return false; 
/* 2670 */     if (dir.startsWith("records/items/gearweapons/spears/crafting/%")) return false; 
/* 2671 */     if (dir.startsWith("records/items/crafting/blueprints/weapon/crafting_")) return false;
/*      */ 
/*      */     
/* 2674 */     if (dir.startsWith("records/creatures/pc/malepc01.dbr")) return true; 
/* 2675 */     if (dir.startsWith("records/creatures/pc/playerlevels.dbr")) return true; 
/* 2676 */     if (dir.startsWith("records/creatures/npcs/merchants/")) return true; 
/* 2677 */     if (dir.startsWith("records/creatures/npcs/npcgear/")) return true;
/*      */ 
/*      */     
/* 2680 */     if (dir.startsWith("records/creatures/")) return false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2685 */     if (dir.startsWith("records/ui/")) return true; 
/* 2686 */     if (dir.startsWith("records/ui load tables/")) return false;
/*      */     
/* 2688 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean processTemplate(String template, boolean extractItems) {
/* 2693 */     if (template.equals("database/templates/armor_chest.tpl")) return extractItems; 
/* 2694 */     if (template.equals("database/templates/armor_feet.tpl")) return extractItems; 
/* 2695 */     if (template.equals("database/templates/armor_hands.tpl")) return extractItems; 
/* 2696 */     if (template.equals("database/templates/armor_head.tpl")) return extractItems; 
/* 2697 */     if (template.equals("database/templates/armor_legs.tpl")) return extractItems; 
/* 2698 */     if (template.equals("database/templates/armor_shoulders.tpl")) return extractItems;
/*      */ 
/*      */     
/* 2701 */     if (template.equals("database/templates/weapon_axe.tpl")) return extractItems; 
/* 2702 */     if (template.equals("database/templates/weapon_mace.tpl")) return extractItems; 
/* 2703 */     if (template.equals("database/templates/weapon_dagger.tpl")) return extractItems; 
/* 2704 */     if (template.equals("database/templates/weapon_scepter.tpl")) return extractItems; 
/* 2705 */     if (template.equals("database/templates/weapon_spear.tpl")) return extractItems; 
/* 2706 */     if (template.equals("database/templates/weapon_staff.tpl")) return extractItems; 
/* 2707 */     if (template.equals("database/templates/weapon_sword.tpl")) return extractItems; 
/* 2708 */     if (template.equals("database/templates/weapon_ranged1h.tpl")) return extractItems; 
/* 2709 */     if (template.equals("database/templates/weapon_ranged2h.tpl")) return extractItems; 
/* 2710 */     if (template.equals("database/templates/weapon_axe2h.tpl")) return extractItems; 
/* 2711 */     if (template.equals("database/templates/weapon_mace2h.tpl")) return extractItems; 
/* 2712 */     if (template.equals("database/templates/weapon_sword2h.tpl")) return extractItems; 
/* 2713 */     if (template.equals("database/templates/weapon_offhand.tpl")) return extractItems; 
/* 2714 */     if (template.equals("database/templates/weaponarmor_shield.tpl")) return extractItems;
/*      */ 
/*      */     
/* 2717 */     if (template.equals("database/templates/jewelry_amulet.tpl")) return extractItems; 
/* 2718 */     if (template.equals("database/templates/jewelry_medal.tpl")) return extractItems; 
/* 2719 */     if (template.equals("database/templates/jewelry_ring.tpl")) return extractItems; 
/* 2720 */     if (template.equals("database/templates/armor_waist.tpl")) return extractItems;
/*      */ 
/*      */     
/* 2723 */     if (template.equals("database/templates/itemusableskill.tpl")) return extractItems; 
/* 2724 */     if (template.equals("database/templates/itemtransmuter.tpl")) return extractItems; 
/* 2725 */     if (template.equals("database/templates/itemtransmuterset.tpl")) return extractItems; 
/* 2726 */     if (template.equals("database/templates/itemartifactformula.tpl")) return extractItems; 
/* 2727 */     if (template.equals("database/templates/questitem.tpl")) return extractItems; 
/* 2728 */     if (template.equals("database/templates/oneshot_scroll.tpl")) return extractItems; 
/* 2729 */     if (template.equals("database/templates/oneshot_endlessdungeon.tpl")) return extractItems; 
/* 2730 */     if (template.equals("database/templates/oneshot_potion.tpl")) return extractItems; 
/* 2731 */     if (template.equals("database/templates/itemenchantment.tpl")) return extractItems; 
/* 2732 */     if (template.equals("database/templates/itemrelic.tpl")) return extractItems; 
/* 2733 */     if (template.equals("database/templates/itemartifact.tpl")) return extractItems; 
/* 2734 */     if (template.equals("database/templates/itemnote.tpl")) return extractItems; 
/* 2735 */     if (template.equals("database/templates/itemfactionbooster.tpl")) return extractItems; 
/* 2736 */     if (template.equals("database/templates/itemfactionwarrant.tpl")) return extractItems; 
/* 2737 */     if (template.equals("database/templates/itemdifficultyunlock.tpl")) return extractItems;
/*      */     
/* 2739 */     if (template.equals("database/templates/oneshot_food.tpl")) return extractItems; 
/* 2740 */     if (template.equals("database/templates/oneshot_potionmana.tpl")) return extractItems; 
/* 2741 */     if (template.equals("database/templates/oneshot_potionhealth.tpl")) return extractItems; 
/* 2742 */     if (template.equals("database/templates/itemdevotionreset.tpl")) return extractItems; 
/* 2743 */     if (template.equals("database/templates/itemattributereset.tpl")) return extractItems; 
/* 2744 */     if (template.equals("database/templates/oneshot_sack.tpl")) return extractItems;
/*      */ 
/*      */     
/* 2747 */     if (template.equals("database/templates/itemset.tpl")) return extractItems;
/*      */ 
/*      */     
/* 2750 */     if (template.equals("database/templates/lootrandomizer.tpl")) return true; 
/* 2751 */     if (template.equals("database/templates/lootrandomizertabledynamic.tpl")) return true;
/*      */ 
/*      */     
/* 2754 */     if (template.equals("database/templates/leveltable.tpl")) return true; 
/* 2755 */     if (template.equals("database/templates/lootitemtable_dynweighted_dynaffix.tpl")) return true;
/*      */ 
/*      */     
/* 2758 */     if (template.equals("database/templates/skilltree.tpl")) return true; 
/* 2759 */     if (template.equals("database/templates/skilltree_expanded.tpl")) return true; 
/* 2760 */     if (template.equals("database/templates/skilltree_100.tpl")) return true; 
/* 2761 */     if (template.startsWith("database/templates/skill")) return true;
/*      */ 
/*      */ 
/*      */     
/* 2765 */     if (template.equals("database/templates/petbonus.tpl")) return true; 
/* 2766 */     if (template.equals("database/templates/fixeditemskill_buff.tpl")) return true; 
/* 2767 */     if (template.equals("database/templates/skillautocastcontroller.tpl")) return true;
/*      */ 
/*      */     
/* 2770 */     if (template.equals("database/templates/pet.tpl")) return true; 
/* 2771 */     if (template.equals("database/templates/petplayerscaling.tpl")) return true; 
/* 2772 */     if (template.equals("database/templates/characterattributeequations.tpl")) return true;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2777 */     if (template.equals("database/templates/staticshrine.tpl")) return true; 
/* 2778 */     if (template.equals("database/templates/factionpack.tpl")) return true; 
/* 2779 */     if (template.equals("database/templates/npcmerchant.tpl")) return true; 
/* 2780 */     if (template.equals("database/templates/factionmarket.tpl")) return true; 
/* 2781 */     if (template.equals("database/templates/factiontier.tpl")) return true; 
/* 2782 */     if (template.equals("database/templates/gameengine.tpl")) return true; 
/* 2783 */     if (template.equals("database/templates/gamefactions.tpl")) return true; 
/* 2784 */     if (template.equals("database/templates/itemcost.tpl")) return true; 
/* 2785 */     if (template.equals("database/templates/player.tpl")) return true; 
/* 2786 */     if (template.equals("database/templates/experiencelevelcontrol.tpl")) return true; 
/* 2787 */     if (template.equals("database/templates/ingameui/devotionconstellation.tpl")) return true; 
/* 2788 */     if (template.equals("database/templates/ingameui/skillbutton.tpl")) return true; 
/* 2789 */     if (template.equals("database/templates/ingameui/bitmapsingle.tpl")) return true; 
/* 2790 */     if (template.equals("database/templates/ingameui/inventorygrid.tpl")) return true; 
/* 2791 */     if (template.equals("database/templates/ingameui/caravanwindowprivate.tpl")) return true; 
/* 2792 */     if (template.equals("database/templates/ingameui/caravanwindowpublic.tpl")) return true; 
/* 2793 */     if (template.equals("database/templates/ingameui/bargraph.tpl")) return true; 
/* 2794 */     if (template.equals("database/templates/ingameui/skillpanectrl.tpl")) return true; 
/* 2795 */     if (template.equals("database/templates/ingameui/skillswindow.tpl")) return true;
/*      */ 
/*      */ 
/*      */     
/* 2799 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean processFile(String filename, String template) {
/* 2803 */     if (template.equals("database/templates/gameengine.tpl")) {
/* 2804 */       return filename.equals("records/game/gameengine.dbr");
/*      */     }
/*      */     
/* 2807 */     if (filename.equals("records/items/loottables/weapons/tdyn_gun2hcrossbow_c01.dbr")) return false; 
/* 2808 */     if (filename.equals("records/items/loottables/weapons/tdyn_gun2hcrossbow_c01b.dbr")) return false; 
/* 2809 */     if (filename.equals("records/items/loottables/weapons/tdyn_gun2hcrossbow_d01.dbr")) return false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2818 */     if (filename.endsWith("_blank.dbr")) return false; 
/* 2819 */     if (filename.contains("/old/")) return false;
/*      */     
/* 2821 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isGameEngine() {
/* 2825 */     if (this.fileName.equals("records/game/gameengine.dbr")) return true; 
/* 2826 */     if (this.fileName.equals("records/game/gamefactions.dbr")) return true;
/*      */     
/* 2828 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isFaction() {
/* 2832 */     if (this.template == null) return true;
/*      */     
/* 2834 */     if (this.template.equals("database/templates/factionpack.tpl")) return true;
/*      */     
/* 2836 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isMerchant() {
/* 2840 */     if (this.template == null) return true;
/*      */     
/* 2842 */     if (this.template.equals("database/templates/npcmerchant.tpl")) return true;
/*      */     
/* 2844 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isMerchantTableSet() {
/* 2848 */     if (this.template == null) return true;
/*      */     
/* 2850 */     if (this.template.equals("database/templates/npcmerchant.tpl")) return true; 
/* 2851 */     if (this.template.equals("database/templates/factionmarket.tpl")) return true; 
/* 2852 */     if (this.template.equals("database/templates/factiontier.tpl")) return true;
/*      */     
/* 2854 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isMerchantTable() {
/* 2858 */     if (this.template == null) return true;
/*      */     
/* 2860 */     if (this.template.equals("database/templates/factiontier.tpl")) return true;
/*      */     
/* 2862 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isPlayerEngine() {
/* 2866 */     if (this.fileName.equals("records/creatures/pc/playerlevels.dbr")) return true; 
/* 2867 */     if (this.fileName.equals("records/creatures/pc/malepc01.dbr")) return true;
/*      */     
/* 2869 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isConstellation() {
/* 2873 */     if (this.template == null) return true;
/*      */     
/* 2875 */     if (this.template.equals("database/templates/ingameui/devotionconstellation.tpl")) return true;
/*      */     
/* 2877 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isSkillButton() {
/* 2881 */     if (this.template == null) return true;
/*      */     
/* 2883 */     if (this.template.equals("database/templates/ingameui/skillbutton.tpl")) return true;
/*      */     
/* 2885 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isBitmap() {
/* 2889 */     if (this.template == null) return true;
/*      */     
/* 2891 */     if (this.template.equals("database/templates/ingameui/bitmapsingle.tpl")) return true; 
/* 2892 */     if (this.template.equals("database/templates/ingameui/bargraph.tpl")) return true;
/*      */     
/* 2894 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isInventoryGrid() {
/* 2898 */     if (this.template == null) return true;
/*      */     
/* 2900 */     if (this.template.equals("database/templates/ingameui/inventorygrid.tpl")) return true;
/*      */     
/* 2902 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isCaravanWindow() {
/* 2906 */     if (this.template == null) return true;
/*      */     
/* 2908 */     if (this.template.equals("database/templates/ingameui/caravanwindowprivate.tpl")) return true; 
/* 2909 */     if (this.template.equals("database/templates/ingameui/caravanwindowpublic.tpl")) return true;
/*      */     
/* 2911 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isClassTable() {
/* 2915 */     if (this.template == null) return true;
/*      */     
/* 2917 */     if (this.template.equals("database/templates/ingameui/skillpanectrl.tpl")) return true;
/*      */     
/* 2919 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isSkillMaster() {
/* 2923 */     if (this.template == null) return true;
/*      */     
/* 2925 */     if (this.template.equals("database/templates/ingameui/skillswindow.tpl")) return true;
/*      */     
/* 2927 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isShrine() {
/* 2931 */     if (this.template == null) return true;
/*      */     
/* 2933 */     if (this.template.equals("database/templates/staticshrine.tpl")) return true;
/*      */     
/* 2935 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isSkillTree() {
/* 2939 */     if (this.template == null) return true;
/*      */     
/* 2941 */     if (this.template.equals("database/templates/skilltree.tpl")) return true; 
/* 2942 */     if (this.template.equals("database/templates/skilltree_expanded.tpl")) return true; 
/* 2943 */     if (this.template.equals("database/templates/skilltree_100.tpl")) return true;
/*      */     
/* 2945 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isSkill() {
/* 2949 */     if (this.template == null) return true;
/*      */     
/* 2951 */     if (this.template.equals("database/templates/skilltree.tpl")) return false; 
/* 2952 */     if (this.template.equals("database/templates/skilltree_expanded.tpl")) return false; 
/* 2953 */     if (this.template.equals("database/templates/skilltree_100.tpl")) return false; 
/* 2954 */     if (this.template.equals("database/templates/skillautocastcontroller.tpl")) return false;
/*      */     
/* 2956 */     if (this.template.startsWith("database/templates/skill")) return true;
/*      */ 
/*      */ 
/*      */     
/* 2960 */     if (this.template.equals("database/templates/petbonus.tpl") && 
/* 2961 */       !this.fileName.startsWith("records/items/lootaffixes/")) return true;
/*      */     
/* 2963 */     if (this.template.equals("database/templates/fixeditemskill_buff.tpl")) return true;
/*      */ 
/*      */     
/* 2966 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isPet() {
/* 2970 */     if (this.template == null) return true;
/*      */     
/* 2972 */     if (this.template.equals("database/templates/pet.tpl")) return true; 
/* 2973 */     if (this.template.equals("database/templates/petplayerscaling.tpl")) return true;
/*      */     
/* 2975 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isPetBio() {
/* 2979 */     if (this.template == null) return true;
/*      */     
/* 2981 */     if (this.template.equals("database/templates/characterattributeequations.tpl")) return true;
/*      */     
/* 2983 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isController() {
/* 2987 */     if (this.template == null) return true;
/*      */     
/* 2989 */     if (this.template.equals("database/templates/skillautocastcontroller.tpl")) return true;
/*      */     
/* 2991 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isAffix() {
/* 2995 */     if (this.template == null) return true;
/*      */     
/* 2997 */     if (this.template.equals("database/templates/lootrandomizer.tpl")) return true; 
/* 2998 */     if (this.template.equals("database/templates/petbonus.tpl") && 
/* 2999 */       this.fileName.startsWith("records/items/lootaffixes/")) return true;
/*      */ 
/*      */     
/* 3002 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isAffixSet() {
/* 3006 */     if (this.template == null) return true;
/*      */     
/* 3008 */     if (this.template.equals("database/templates/lootrandomizertabledynamic.tpl")) return true;
/*      */     
/* 3010 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isFormulaSet() {
/* 3014 */     if (this.template == null) return true;
/*      */     
/* 3016 */     if (this.template.equals("database/templates/itemcost.tpl")) return true;
/*      */     
/* 3018 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isItemSet() {
/* 3022 */     if (this.template == null) return true;
/*      */     
/* 3024 */     if (this.template.equals("database/templates/itemset.tpl")) return true;
/*      */     
/* 3026 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isLootTable() {
/* 3030 */     if (this.template == null) return true;
/*      */     
/* 3032 */     if (this.template.equals("database/templates/lootitemtable_dynweighted_dynaffix.tpl")) return true;
/*      */     
/* 3034 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isLootTableSet() {
/* 3038 */     if (this.template == null) return true;
/*      */     
/* 3040 */     if (this.template.equals("database/templates/leveltable.tpl")) return true;
/*      */     
/* 3042 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\file\ARZRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */