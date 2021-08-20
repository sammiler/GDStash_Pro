/*      */ package org.gdstash.db;
/*      */ 
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import org.gdstash.db.criteria.AbstractItemCombination;
/*      */ import org.gdstash.db.criteria.CriteriaCombination;
/*      */ import org.gdstash.db.criteria.ItemIDItemCombination;
/*      */ import org.gdstash.db.criteria.ItemIDItemSetCombination;
/*      */ import org.gdstash.file.ARCDecompress;
/*      */ import org.gdstash.file.ARZDecompress;
/*      */ import org.gdstash.file.ARZFormulaSetPool;
/*      */ import org.gdstash.file.ARZRecord;
/*      */ import org.gdstash.file.DDSLoader;
/*      */ import org.gdstash.file.GDParseException;
/*      */ import org.gdstash.item.GDItem;
/*      */ import org.gdstash.ui.GDStashFrame;
/*      */ import org.gdstash.util.GDColor;
/*      */ import org.gdstash.util.GDConstants;
/*      */ import org.gdstash.util.GDMsgFormatter;
/*      */ import org.gdstash.util.GDMsgLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DBItem
/*      */   implements Comparable<DBItem>, ParameterSet
/*      */ {
/*      */   public static class ImageInfo
/*      */   {
/*      */     public String itemID;
/*      */     public String bitmapID;
/*      */     public String shardBitmapID;
/*      */     public byte[] bitmap;
/*      */     public byte[] shardBitmap;
/*      */   }
/*      */   
/*      */   public static class SetInfo
/*      */   {
/*      */     public String itemID;
/*      */     public String rarity;
/*      */     public int level;
/*      */   }
/*  265 */   public static final List<String> QUEST_ITEMS = new LinkedList<>();
/*      */   
/*      */   static {
/*  268 */     QUEST_ITEMS.add("records/items/materia/compa_aethercrystal.dbr");
/*  269 */     QUEST_ITEMS.add("records/items/crafting/materials/craft_aethercrystalcluster.dbr");
/*  270 */     QUEST_ITEMS.add("records/items/crafting/materials/craft_aethershard.dbr");
/*  271 */     QUEST_ITEMS.add("records/items/crafting/materials/craft_aetherialmissive.dbr");
/*  272 */     QUEST_ITEMS.add("records/items/crafting/materials/craft_aetherialmutagen.dbr");
/*  273 */     QUEST_ITEMS.add("records/items/crafting/materials/craft_ancientheart.dbr");
/*  274 */     QUEST_ITEMS.add("records/items/crafting/materials/craft_bloodchthon.dbr");
/*  275 */     QUEST_ITEMS.add("records/items/questitems/quest_areag_bysmielstatueessence.dbr");
/*  276 */     QUEST_ITEMS.add("records/items/crafting/materials/craft_sacrifice.dbr");
/*  277 */     QUEST_ITEMS.add("records/items/crafting/materials/craft_celestiallotus.dbr");
/*  278 */     QUEST_ITEMS.add("records/items/crafting/materials/craft_cultistseal.dbr");
/*  279 */     QUEST_ITEMS.add("records/items/questitems/quest_dynamite.dbr");
/*  280 */     QUEST_ITEMS.add("records/items/crafting/materials/craft_eldritchessence.dbr");
/*  281 */     QUEST_ITEMS.add("records/items/crafting/materials/craft_manticore.dbr");
/*  282 */     QUEST_ITEMS.add("records/items/crafting/materials/craft_royaljelly.dbr");
/*  283 */     QUEST_ITEMS.add("records/items/questitems/scrapmetal.dbr");
/*  284 */     QUEST_ITEMS.add("records/items/crafting/materials/craft_skeletonkey.dbr");
/*  285 */     QUEST_ITEMS.add("records/items/crafting/materials/craft_taintedbrain.dbr");
/*  286 */     QUEST_ITEMS.add("records/items/crafting/materials/craft_ugdenbloom.dbr");
/*  287 */     QUEST_ITEMS.add("records/items/crafting/materials/craft_wendigospirit.dbr");
/*      */     
/*  289 */     QUEST_ITEMS.add("records/items/questitems/quest_blacklegioninsignia.dbr.dbr");
/*  290 */     QUEST_ITEMS.add("records/items/questitems/quest_slithnecklace.dbr.dbr");
/*      */   }
/*      */   
/*  293 */   private static ConcurrentHashMap<String, DBItem> hashBuffer = new ConcurrentHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  378 */   private List<DBStat> stats = new LinkedList<>();
/*  379 */   private List<DBStatBonusRace> statBonusRaces = new LinkedList<>();
/*  380 */   private List<DBSkillBonus> bonuses = new LinkedList<>();
/*  381 */   private List<DBSkillModifier> skillModifiers = new LinkedList<>();
/*  382 */   private ItemSlots slots = new ItemSlots(); public static final String TABLE_NAME = "GD_ITEM"; public static final String FIELD_ID = "ITEM_ID"; private static final int ROW_ITEM_ID = 1; private static final int ROW_ITEM_CLASS = 2; private static final int ROW_ARMOR_CLASS = 3; private static final int ROW_ARTIFACT_CLASS = 4; private static final int ROW_MESH_ID = 5; private static final int ROW_BASE_TEXTURE_ID = 6; private static final int ROW_BUMP_TEXTURE_ID = 7; private static final int ROW_GLOW_TEXTURE_ID = 8; private static final int ROW_SHADER_ID = 9; private static final int ROW_BITMAP_ID = 10; private static final int ROW_BITMAP = 11; private static final int ROW_SHARD_BITMAP_ID = 12; private static final int ROW_SHARD_BITMAP = 13; private static final int ROW_GENDER_CODE = 14; private static final int ROW_NAME = 15; private static final int ROW_DESCRIPTION = 16; private static final int ROW_QUALITY_TAG = 17; private static final int ROW_QUALITY_TEXT = 18; private static final int ROW_STYLE_TAG = 19; private static final int ROW_STYLE_TEXT = 20; private static final int ROW_NAME_FULL = 21; private static final int ROW_RARITY = 22; private static final int ROW_SET_ID = 23; private static final int ROW_SET_NAME = 24; private static final int ROW_BONUS_AFFIXSET_ID = 25; private static final int ROW_ITEM_SKILL_ID = 26; private static final int ROW_ITEM_SKILL_LEVEL = 27; private static final int ROW_PET_BONUS_SKILL_ID = 28; private static final int ROW_CONTROLLER_ID = 29; private static final int ROW_COST_FORMULASET_ID = 30; private static final int ROW_CONVERT_IN = 31; private static final int ROW_CONVERT_OUT = 32; private static final int ROW_CONVERT_IN_2 = 33; private static final int ROW_CONVERT_OUT_2 = 34; private static final int ROW_SOULBOUND = 35; private static final int ROW_HIDE_PREFIX = 36; private static final int ROW_HIDE_SUFFIX = 37; private static final int ROW_QUESTITEM = 38; private static final int ROW_CANNOT_PICKUP = 39; private static final int ROW_ENEMY_ONLY = 40; private static final int ROW_LEVEL = 41; private static final int ROW_REQ_LEVEL = 42; private static final int ROW_REQ_DEX = 43; private static final int ROW_REQ_INT = 44; private static final int ROW_REQ_STR = 45; private static final int ROW_OFFENSIVE_CHANCE = 46; private static final int ROW_RETALIATION_CHANCE = 47; private static final int ROW_PLUS_ALLSKILLS = 48; private static final int ROW_COMPONENT_PIECES = 49; private static final int ROW_MAX_STACKSIZE = 50; private static final int ROW_SCALE_PERCENT = 51; private static final int ROW_SLOT_AXE_1H = 52; private static final int ROW_SLOT_AXE_2H = 53; private static final int ROW_SLOT_DAGGER_1H = 54; private static final int ROW_SLOT_MACE_1H = 55; private static final int ROW_SLOT_MACE_2H = 56; private static final int ROW_SLOT_SCEPTER_1H = 57; private static final int ROW_SLOT_SPEAR_1H = 58;
/*      */   private static final int ROW_SLOT_STAFF_2H = 59;
/*  384 */   private DBItemCraft dbCraft = new DBItemCraft(); private static final int ROW_SLOT_SWORD_1H = 60; private static final int ROW_SLOT_SWORD_2H = 61; private static final int ROW_SLOT_RANGED_1H = 62; private static final int ROW_SLOT_RANGED_2H = 63; private static final int ROW_SLOT_SHIELD = 64; private static final int ROW_SLOT_OFFHAND = 65; private static final int ROW_SLOT_AMULET = 66; private static final int ROW_SLOT_BELT = 67; private static final int ROW_SLOT_MEDAL = 68; private static final int ROW_SLOT_RING = 69; private static final int ROW_SLOT_HEAD = 70; private static final int ROW_SLOT_SHOULDERS = 71; private static final int ROW_SLOT_CHEST = 72; private static final int ROW_SLOT_HANDS = 73; private static final int ROW_SLOT_LEGS = 74; private static final int ROW_SLOT_FEET = 75; public static final int GENDERCODE_UNDEF = -1; public static final int GENDERCODE_MALE_SINGLE = 0; public static final int GENDERCODE_FEMALE_SINGLE = 1; public static final int GENDERCODE_NEUTRAL_SINGLE = 2; public static final int GENDERCODE_MALE_PLURAL = 3; public static final int GENDERCODE_FEMALE_PLURAL = 4; public static final int GENDERCODE_NEUTRAL_PLURAL = 5; public static final String CLASS_ARMOR_HEAD = "ArmorProtective_Head"; public static final String CLASS_ARMOR_SHOULDERS = "ArmorProtective_Shoulders"; public static final String CLASS_ARMOR_CHEST = "ArmorProtective_Chest"; public static final String CLASS_ARMOR_HANDS = "ArmorProtective_Hands"; public static final String CLASS_ARMOR_BELT = "ArmorProtective_Waist"; public static final String CLASS_ARMOR_LEGS = "ArmorProtective_Legs"; public static final String CLASS_ARMOR_FEET = "ArmorProtective_Feet"; public static final String CLASS_JEWELRY_AMULET = "ArmorJewelry_Amulet"; public static final String CLASS_JEWELRY_MEDAL = "ArmorJewelry_Medal"; public static final String CLASS_JEWELRY_RING = "ArmorJewelry_Ring"; public static final String CLASS_WEAPON_OFFHAND = "WeaponArmor_Offhand"; public static final String CLASS_WEAPON_SHIELD = "WeaponArmor_Shield"; public static final String CLASS_WEAPON_1H_AXE = "WeaponMelee_Axe"; public static final String CLASS_WEAPON_1H_DAGGER = "WeaponMelee_Dagger"; public static final String CLASS_WEAPON_1H_MACE = "WeaponMelee_Mace"; public static final String CLASS_WEAPON_1H_SCEPTER = "WeaponMelee_Scepter"; public static final String CLASS_WEAPON_1H_SPEAR = "WeaponHunting_Spear"; public static final String CLASS_WEAPON_1H_SWORD = "WeaponMelee_Sword"; public static final String CLASS_WEAPON_1H_RANGED = "WeaponHunting_Ranged1h"; public static final String CLASS_WEAPON_2H_AXE = "WeaponMelee_Axe2h"; public static final String CLASS_WEAPON_2H_MACE = "WeaponMelee_Mace2h"; public static final String CLASS_WEAPON_2H_STAFF = "WeaponMagical_Staff"; public static final String CLASS_WEAPON_2H_SWORD = "WeaponMelee_Sword2h"; public static final String CLASS_WEAPON_2H_RANGED = "WeaponHunting_Ranged2h"; public static final String CLASS_ARTIFACT = "ItemArtifact"; public static final String CLASS_COMPONENT = "ItemRelic"; public static final String CLASS_FORMULA_ARTIFACT = "ItemArtifactFormula"; public static final String CLASS_POTION_HEALTH = "OneShot_PotionHealth"; public static final String CLASS_POTION_MANA = "OneShot_PotionMana"; public static final String CLASS_POTION_ENDLESS_DUNGEON = "OneShot_EndlessDungeon"; public static final String CLASS_TONIC_CLARITY = "ItemDevotionReset"; public static final String CLASS_TONIC_OF_RESHAPING = "ItemAttributeReset"; public static final String CLASS_POTION_SCROLL = "OneShot_Scroll"; public static final String CLASS_POTION_CATACLYSM = "OneShot_Potion"; public static final String CLASS_ENCHANTMENT = "ItemEnchantment"; public static final String CLASS_FACTION_BOOSTER = "ItemFactionBooster"; public static final String CLASS_QUESTITEM = "QuestItem";
/*      */   public static final String CLASS_TRANSMUTER = "ItemTransmuter";
/*      */   
/*      */   private DBItem(ARZRecord record) {
/*  388 */     this();
/*      */     
/*  390 */     this.itemID = record.getFileName();
/*  391 */     this.enemyOnly = (this.itemID.startsWith("records/items/enemygear/") || this.itemID.startsWith("records/creatures/"));
/*      */     
/*  393 */     String dlc = record.getDLCRequirement();
/*      */     
/*  395 */     setItemClass(record.getRecordClass(), record.getFileName(), dlc);
/*      */     
/*  397 */     setQuestItem(record.isQuestItem());
/*      */ 
/*      */     
/*  400 */     this.genderCode = record.getGenderCode();
/*  401 */     this.name = record.getItemName();
/*  402 */     if (this.name == null) {
/*  403 */       this.name = record.getFileDescription();
/*      */     }
/*  405 */     this.description = record.getItemDescription();
/*  406 */     setQualityTag(record.getQualityTag());
/*  407 */     setStyleTag(record.getStyleTag());
/*      */     
/*  409 */     this.nameFull = getItemName();
/*      */     
/*  411 */     setCraftID(record.getArtifactID());
/*      */     
/*  413 */     this.armorClass = record.getArmorClass();
/*  414 */     this.artifactClass = record.getArtifactClass();
/*  415 */     this.meshID = record.getMeshID();
/*  416 */     this.baseTextureID = record.getBaseTextureID();
/*  417 */     this.bumpTextureID = record.getBumpTextureID();
/*  418 */     this.glowTextureID = record.getGlowTextureID();
/*  419 */     this.shaderID = record.getShaderID();
/*  420 */     this.bitmapID = record.getBitmapID();
/*  421 */     this.shardBitmapID = record.getShardBitmapID();
/*      */ 
/*      */     
/*  424 */     this.rarity = record.getRarity();
/*  425 */     this.setID = record.getItemSetID();
/*  426 */     setItemSetNameTag(record.getItemSetNameTag());
/*  427 */     this.bonusAffixSetID = record.getBonusAffixSetID();
/*  428 */     this.itemSkillID = record.getItemSkillID();
/*  429 */     this.itemSkillLevel = record.getItemSkillLevel();
/*  430 */     this.petBonusSkillID = record.getPetBonusID();
/*  431 */     this.controllerID = record.getSkillControllerID();
/*  432 */     this.convertIn = record.getConversionIn();
/*  433 */     this.convertOut = record.getConversionOut();
/*  434 */     this.convertIn2 = record.getConversionIn2();
/*  435 */     this.convertOut2 = record.getConversionOut2();
/*  436 */     this.soulbound = record.isSoulbound();
/*  437 */     this.hidePrefix = record.isHidePrefix();
/*  438 */     this.hideSuffix = record.isHideSuffix();
/*  439 */     this.cannotPickup = record.isCannotPickup();
/*  440 */     if (this.cannotPickup) this.enemyOnly = true; 
/*  441 */     this.itemLevel = record.getItemLevel();
/*  442 */     this.reqLevel = record.getRequiredLevel();
/*  443 */     this.offensiveChance = record.getOffensiveChance();
/*  444 */     this.retaliationChance = record.getRetaliationChance();
/*  445 */     this.plusAllSkills = record.getPlusAllSkills();
/*  446 */     this.componentPieces = record.getComponentPieces();
/*  447 */     this.maxStackSize = record.getMaxStackSize();
/*  448 */     this.scalePercent = record.getRNGPercent();
/*  449 */     this.slots = record.getItemSlots();
/*      */     
/*  451 */     this.stats = record.getDBStatList();
/*  452 */     this.statBonusRaces = record.getDBStatBonusRaceList();
/*  453 */     this.bonuses = record.dbSkillBonuses;
/*  454 */     this.skillModifiers = record.getSkillModifierList();
/*      */     
/*  456 */     setCostFormulaSetID(record.getCostFormulaSetID());
/*      */   }
/*      */   public static final String CLASS_FACTION_WARRANT = "ItemFactionWarrant"; public static final String CLASS_CRUCIBLE_UNLOCK = "ItemDifficultyUnlock"; public static final String CLASS_NOTE = "ItemNote"; public static final String CLASS_USABLE_SKILL = "ItemUsableSkill"; public static final String CLASS_KICKSTARTER = "Kickstarter"; public static final String RARITY_BROKEN = "Broken"; public static final String RARITY_COMMON = "Common"; public static final String RARITY_MAGICAL = "Magical"; public static final String RARITY_RARE = "Rare"; public static final String RARITY_EPIC = "Epic"; public static final String RARITY_LEGENDARY = "Legendary"; public static final String RARITY_QUEST = "Quest"; public static final int RARITY_INT_BROKEN = 1; public static final int RARITY_INT_COMMON = 2; public static final int RARITY_INT_MAGICAL = 3; public static final int RARITY_INT_RARE = 4; public static final int RARITY_INT_EPIC = 5; public static final int RARITY_INT_LEGENDARY = 6; public static final int RARITY_INT_QUEST = 7; public static final String ARMOR_CLASS_CASTER = "Caster"; public static final String ARMOR_CLASS_LIGHT = "Light"; public static final String ARMOR_CLASS_HEAVY = "Heavy"; public static final String ARTIFACT_CLASS_LESSER = "Lesser"; public static final String ARTIFACT_CLASS_GREATER = "Greater"; public static final String ARTIFACT_CLASS_DIVINE = "Divine"; public static final String CONV_DAMAGE_TYPE_AETHER = "Aether"; public static final String CONV_DAMAGE_TYPE_CHAOS = "Chaos"; public static final String CONV_DAMAGE_TYPE_COLD = "Cold"; public static final String CONV_DAMAGE_TYPE_ELEMENTAL = "Elemental"; public static final String CONV_DAMAGE_TYPE_FIRE = "Fire"; public static final String CONV_DAMAGE_TYPE_VITALITY = "Life"; public static final String CONV_DAMAGE_TYPE_LIGHTNING = "Lightning"; public static final String CONV_DAMAGE_TYPE_PHYSICAL = "Physical"; public static final String CONV_DAMAGE_TYPE_PIERCE = "Pierce"; public static final String CONV_DAMAGE_TYPE_ACID = "Poison"; public static final String ITEM_QUEST_AETHER_CRYSTAL = "records/items/materia/compa_aethercrystal.dbr"; public static final String ITEM_QUEST_AETHER_CLUSTER = "records/items/crafting/materials/craft_aethercrystalcluster.dbr"; public static final String ITEM_QUEST_AETHER_SHARD = "records/items/crafting/materials/craft_aethershard.dbr"; public static final String ITEM_QUEST_AETHERIAL_MISSIVE = "records/items/crafting/materials/craft_aetherialmissive.dbr"; public static final String ITEM_QUEST_AETHERIAL_MUTAGEN = "records/items/crafting/materials/craft_aetherialmutagen.dbr"; public static final String ITEM_QUEST_ANCIENT_HEART = "records/items/crafting/materials/craft_ancientheart.dbr"; public static final String ITEM_QUEST_BLOOD_CHTHON = "records/items/crafting/materials/craft_bloodchthon.dbr"; public static final String ITEM_QUEST_BLOOD_WATCHERS = "records/items/questitems/quest_areag_bysmielstatueessence.dbr"; public static final String ITEM_QUEST_CELESTIAL_ESSENCE = "records/items/crafting/materials/craft_sacrifice.dbr"; public static final String ITEM_QUEST_CELESTIAL_LOTUS = "records/items/crafting/materials/craft_celestiallotus.dbr"; public static final String ITEM_QUEST_CHTHONIC_SEAL = "records/items/crafting/materials/craft_cultistseal.dbr"; public static final String ITEM_QUEST_DYNAMITE = "records/items/questitems/quest_dynamite.dbr"; public static final String ITEM_QUEST_ELDRITCH_ESSENCE = "records/items/crafting/materials/craft_eldritchessence.dbr"; public static final String ITEM_QUEST_MANTICORE_EYE = "records/items/crafting/materials/craft_manticore.dbr"; public static final String ITEM_QUEST_ROYAL_JELLY = "records/items/crafting/materials/craft_royaljelly.dbr"; public static final String ITEM_QUEST_SCRAPMETAL = "records/items/questitems/scrapmetal.dbr"; public static final String ITEM_QUEST_SKELETON_KEY = "records/items/crafting/materials/craft_skeletonkey.dbr"; public static final String ITEM_QUEST_TAINTED_BRAIN = "records/items/crafting/materials/craft_taintedbrain.dbr"; public static final String ITEM_QUEST_UGDENBLOOM = "records/items/crafting/materials/craft_ugdenbloom.dbr"; public static final String ITEM_QUEST_WENDIGO_SPIRIT = "records/items/crafting/materials/craft_wendigospirit.dbr"; public static final String ITEM_QUEST_FORGOTTEN_PASSAGE = "records/items/questitems/quest_forgottenpassage.dbr"; public static final String ITEM_QUEST_LEGION_INSIGNIA = "records/items/questitems/quest_blacklegioninsignia.dbr.dbr";
/*      */   public static final String ITEM_QUEST_SLITH_NECKLACE = "records/items/questitems/quest_slithnecklace.dbr.dbr";
/*      */   private String itemID;
/*      */   private String itemClass;
/*      */   private String armorClass;
/*      */   
/*      */   public boolean equals(Object o) {
/*  465 */     if (o == null) return false; 
/*  466 */     if (!o.getClass().equals(DBItem.class)) return false;
/*      */     
/*  468 */     DBItem item = (DBItem)o;
/*      */     
/*  470 */     return item.itemID.equals(this.itemID);
/*      */   }
/*      */   private String artifactClass; private String meshID; private String baseTextureID; private String bumpTextureID; private String glowTextureID; private String shaderID; private String bitmapID; private byte[] bitmap; private String shardBitmapID; private byte[] shardBitmap; private int genderCode; private String name; private String description; private String qualityTag; private String qualityText; private String styleTag; private String styleText; private String nameFull; private String rarity; private String setID; private String setName; private String bonusAffixSetID; private String itemSkillID; private int itemSkillLevel; private String petBonusSkillID; private String controllerID; private String costFormulaSetID; private String convertIn; private String convertOut; private String convertIn2; private String convertOut2; private boolean soulbound; private boolean hidePrefix; private boolean hideSuffix; private boolean questItem; private boolean cannotPickup; private boolean enemyOnly; private int itemLevel; private int reqLevel; private int reqDex; private int reqInt; private int reqStr; private int offensiveChance; private int retaliationChance; private int plusAllSkills; private int componentPieces; private int maxStackSize; private int scalePercent; private DBAffixSet bonusAffixSet; private DBSkill dbItemSkill; private DBSkill dbPetBonusSkill; private DBController dbController; private DBFormulaSet dbFormulaSet; private DBItemCraft dbBlueprint; private DBItemSet dbItemSet; private BufferedImage image; private BufferedImage shardImage; private BufferedImage overlayImage; private BufferedImage overlayShard; private boolean craftable; private String faction;
/*      */   
/*      */   public int hashCode() {
/*  475 */     return this.itemID.hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(DBItem o) {
/*  483 */     if (!o.getClass().equals(DBItem.class)) {
/*  484 */       Object[] args = { DBItem.class, o.toString() };
/*  485 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_NOT_OF_TYPE", args);
/*      */       
/*  487 */       throw new ClassCastException(msg);
/*      */     } 
/*      */     
/*  490 */     DBItem item = o;
/*      */     
/*  492 */     int oClass = ItemClass.getClassInt(this.itemClass);
/*  493 */     int iClass = ItemClass.getClassInt(item.itemClass);
/*      */     
/*  495 */     if (oClass < iClass) return -1; 
/*  496 */     if (oClass > iClass) return 1;
/*      */     
/*  498 */     int oRarity = getRarityInt();
/*  499 */     int iRarity = item.getRarityInt();
/*      */     
/*  501 */     if (oRarity < iRarity) return -1; 
/*  502 */     if (oRarity > iRarity) return 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  514 */     String fullname1 = combineName(true);
/*  515 */     String fullname2 = item.combineName(true);
/*      */     
/*  517 */     if (fullname1 == null) {
/*  518 */       if (fullname2 != null) {
/*  519 */         return -1;
/*      */       }
/*      */     } else {
/*  522 */       if (fullname2 == null) {
/*  523 */         return 1;
/*      */       }
/*  525 */       if (!fullname2.equals(fullname1)) return fullname1.compareTo(fullname2);
/*      */     
/*      */     } 
/*      */     
/*  529 */     if (this.reqLevel < item.reqLevel) return -1; 
/*  530 */     if (this.reqLevel > item.reqLevel) return 1;
/*      */     
/*  532 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getItemID() {
/*  540 */     return this.itemID;
/*      */   }
/*      */   
/*      */   public String getItemClass() {
/*  544 */     return this.itemClass;
/*      */   }
/*      */   
/*      */   public String getArmorClass() {
/*  548 */     return this.armorClass;
/*      */   }
/*      */   
/*      */   public String getArtifactClass() {
/*  552 */     return this.artifactClass;
/*      */   }
/*      */   
/*      */   public int getGenderCode() {
/*  556 */     return this.genderCode;
/*      */   }
/*      */   
/*      */   public String getItemDescription() {
/*  560 */     return GDColor.replaceTags(this.description);
/*      */   }
/*      */   
/*      */   public String getName(boolean inclFaction) {
/*  564 */     if (inclFaction && 
/*  565 */       this.faction != null) {
/*  566 */       return this.name + " (" + this.faction + ")";
/*      */     }
/*      */ 
/*      */     
/*  570 */     return this.name;
/*      */   }
/*      */   
/*      */   public String getFullName() {
/*  574 */     return this.nameFull;
/*      */   }
/*      */   
/*      */   public String getFaction() {
/*  578 */     return this.faction;
/*      */   }
/*      */   
/*      */   public String getItemName() {
/*  582 */     return combineName(false);
/*      */   }
/*      */   
/*      */   public String getItemNameWithDmg() {
/*  586 */     return combineName(true);
/*      */   }
/*      */   
/*      */   public int getItemScalePercent() {
/*  590 */     return this.scalePercent;
/*      */   }
/*      */   
/*      */   public String getQualityText() {
/*  594 */     return this.qualityText;
/*      */   }
/*      */   
/*      */   public String getStyleText() {
/*  598 */     return this.styleText;
/*      */   }
/*      */   
/*      */   public String getRarity() {
/*  602 */     return this.rarity;
/*      */   }
/*      */   
/*      */   public String getItemSetID() {
/*  606 */     return this.setID;
/*      */   }
/*      */   
/*      */   public String getItemSetName() {
/*  610 */     return this.setName;
/*      */   }
/*      */   
/*      */   public ArrayList<List<DBStat>> getItemSetBonusesPerLevel() {
/*  614 */     if (this.dbItemSet == null) return null;
/*      */     
/*  616 */     return this.dbItemSet.getBonusesPerLevel();
/*      */   }
/*      */   
/*      */   public ArrayList<List<DBSkillBonus>> getItemSetSkillBonusesPerLevel() {
/*  620 */     if (this.dbItemSet == null) return null;
/*      */     
/*  622 */     return this.dbItemSet.getItemSetSkillBonusesPerLevel();
/*      */   }
/*      */   
/*      */   public List<DBSkillModifier> getSkillModifiers(int level) {
/*  626 */     if (this.dbItemSet == null) return null;
/*      */     
/*  628 */     return this.dbItemSet.getSkillModifiers(level);
/*      */   }
/*      */   
/*      */   public int getConvertPerc() {
/*  632 */     DBStat stat = DBStat.getDBStat(this.stats, "conversionPercentage", 1);
/*      */     
/*  634 */     if (stat == null) return 0;
/*      */     
/*  636 */     return (int)stat.getStatMin();
/*      */   }
/*      */   
/*      */   public int getConvertPerc2() {
/*  640 */     DBStat stat = DBStat.getDBStat(this.stats, "conversionPercentage2", 1);
/*      */     
/*  642 */     if (stat == null) return 0;
/*      */     
/*  644 */     return (int)stat.getStatMin();
/*      */   }
/*      */   
/*      */   public String getCraftID() {
/*  648 */     return this.dbCraft.getCraftID();
/*      */   }
/*      */   
/*      */   public String getBonusAffixSetID() {
/*  652 */     return this.bonusAffixSetID;
/*      */   }
/*      */   
/*      */   public String getItemSkillID() {
/*  656 */     return this.itemSkillID;
/*      */   }
/*      */   
/*      */   public int getItemSkillLevel() {
/*  660 */     return this.itemSkillLevel;
/*      */   }
/*      */   
/*      */   public String getPetBonusSkillID() {
/*  664 */     return this.petBonusSkillID;
/*      */   }
/*      */   
/*      */   public String getControllerID() {
/*  668 */     return this.controllerID;
/*      */   }
/*      */   
/*      */   public String getControllerTriggerType() {
/*  672 */     if (this.dbController == null) return null;
/*      */     
/*  674 */     return this.dbController.getTriggerType();
/*      */   }
/*      */   
/*      */   public int getControllerTriggerChance() {
/*  678 */     if (this.dbController == null) return 0;
/*      */     
/*  680 */     return this.dbController.getTriggerChance();
/*      */   }
/*      */   
/*      */   public String getCostFormulaSetID() {
/*  684 */     return this.costFormulaSetID;
/*      */   }
/*      */   
/*      */   public boolean isSoulbound() {
/*  688 */     return this.soulbound;
/*      */   }
/*      */   
/*      */   public boolean isHidePrefix() {
/*  692 */     return this.hidePrefix;
/*      */   }
/*      */   
/*      */   public boolean isHideSuffix() {
/*  696 */     return this.hideSuffix;
/*      */   }
/*      */   
/*      */   public boolean isQuestItem() {
/*  700 */     return this.questItem;
/*      */   }
/*      */   
/*      */   public boolean isCraftable() {
/*  704 */     return this.craftable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemLevel() {
/*  712 */     return this.itemLevel;
/*      */   }
/*      */   
/*      */   public boolean isEnemyOnly() {
/*  716 */     return this.enemyOnly;
/*      */   }
/*      */   
/*      */   public int getRequiredlevel() {
/*  720 */     return this.reqLevel;
/*      */   }
/*      */   
/*      */   public int getRequiredCunning() {
/*  724 */     return this.reqDex;
/*      */   }
/*      */   
/*      */   public int getRequiredPhysique() {
/*  728 */     return this.reqStr;
/*      */   }
/*      */   
/*      */   public int getRequiredSpirit() {
/*  732 */     return this.reqInt;
/*      */   }
/*      */   
/*      */   public int getOffensiveChance() {
/*  736 */     return this.offensiveChance;
/*      */   }
/*      */   
/*      */   public int getRetaliationChance() {
/*  740 */     return this.retaliationChance;
/*      */   }
/*      */   
/*      */   public String getConvertIn() {
/*  744 */     return this.convertIn;
/*      */   }
/*      */   
/*      */   public String getConvertOut() {
/*  748 */     return this.convertOut;
/*      */   }
/*      */   
/*      */   public String getConvertIn2() {
/*  752 */     return this.convertIn2;
/*      */   }
/*      */   
/*      */   public String getConvertOut2() {
/*  756 */     return this.convertOut2;
/*      */   }
/*      */   
/*      */   public int getPlusAllSkills() {
/*  760 */     return this.plusAllSkills;
/*      */   }
/*      */   
/*      */   public int getComponentPieces() {
/*  764 */     return this.componentPieces;
/*      */   }
/*      */   
/*      */   public int getMaxStackSize() {
/*  768 */     return this.maxStackSize;
/*      */   }
/*      */   
/*      */   public ItemSlots getSlots() {
/*  772 */     return this.slots.clone();
/*      */   }
/*      */   
/*      */   public boolean usesSlots() {
/*  776 */     if (this.slots == null) return false;
/*      */     
/*  778 */     return this.slots.usesSlots();
/*      */   }
/*      */   
/*      */   public boolean isSlotAxe1H() {
/*  782 */     return this.slots.slotAxe1H;
/*      */   }
/*      */   
/*      */   public boolean isSlotAxe2H() {
/*  786 */     return this.slots.slotAxe2H;
/*      */   }
/*      */   
/*      */   public boolean isSlotDagger1H() {
/*  790 */     return this.slots.slotDagger1H;
/*      */   }
/*      */   
/*      */   public boolean isSlotMace1H() {
/*  794 */     return this.slots.slotMace1H;
/*      */   }
/*      */   
/*      */   public boolean isSlotMace2H() {
/*  798 */     return this.slots.slotMace2H;
/*      */   }
/*      */   
/*      */   public boolean isSlotScepter1H() {
/*  802 */     return this.slots.slotScepter1H;
/*      */   }
/*      */   
/*      */   public boolean isSlotSpear1H() {
/*  806 */     return this.slots.slotSpear1H;
/*      */   }
/*      */   
/*      */   public boolean isSlotStaff2H() {
/*  810 */     return this.slots.slotStaff2H;
/*      */   }
/*      */   
/*      */   public boolean isSlotSword1H() {
/*  814 */     return this.slots.slotSword1H;
/*      */   }
/*      */   
/*      */   public boolean isSlotSword2H() {
/*  818 */     return this.slots.slotSword2H;
/*      */   }
/*      */   
/*      */   public boolean isSlotRanged1H() {
/*  822 */     return this.slots.slotRanged1H;
/*      */   }
/*      */   
/*      */   public boolean isSlotRanged2H() {
/*  826 */     return this.slots.slotRanged2H;
/*      */   }
/*      */   
/*      */   public boolean isSlotShield() {
/*  830 */     return this.slots.slotShield;
/*      */   }
/*      */   
/*      */   public boolean isSlotOffhand() {
/*  834 */     return this.slots.slotOffhand;
/*      */   }
/*      */   
/*      */   public boolean isSlotAmulet() {
/*  838 */     return this.slots.slotAmulet;
/*      */   }
/*      */   
/*      */   public boolean isSlotBelt() {
/*  842 */     return this.slots.slotBelt;
/*      */   }
/*      */   
/*      */   public boolean isSlotMedal() {
/*  846 */     return this.slots.slotMedal;
/*      */   }
/*      */   
/*      */   public boolean isSlotRing() {
/*  850 */     return this.slots.slotRing;
/*      */   }
/*      */   
/*      */   public boolean isSlotHead() {
/*  854 */     return this.slots.slotHead;
/*      */   }
/*      */   
/*      */   public boolean isSlotShoulders() {
/*  858 */     return this.slots.slotShoulders;
/*      */   }
/*      */   
/*      */   public boolean isSlotChest() {
/*  862 */     return this.slots.slotChest;
/*      */   }
/*      */   
/*      */   public boolean isSlotHands() {
/*  866 */     return this.slots.slotHands;
/*      */   }
/*      */   
/*      */   public boolean isSlotLegs() {
/*  870 */     return this.slots.slotLegs;
/*      */   }
/*      */   
/*      */   public boolean isSlotFeet() {
/*  874 */     return this.slots.slotFeet;
/*      */   }
/*      */   
/*      */   public DBAffixSet getBonusAffixSet() {
/*  878 */     return this.bonusAffixSet;
/*      */   }
/*      */   
/*      */   public List<DBStat> getStatList() {
/*  882 */     return this.stats;
/*      */   }
/*      */   
/*      */   public List<DBSkillBonus> getSkillBonusList() {
/*  886 */     return this.bonuses;
/*      */   }
/*      */   
/*      */   public List<DBSkillModifier> getSkillModifierList() {
/*  890 */     return this.skillModifiers;
/*      */   }
/*      */   
/*      */   public DBSkill getItemSkill() {
/*  894 */     return this.dbItemSkill;
/*      */   }
/*      */   
/*      */   public DBController getItemSkillController() {
/*  898 */     return this.dbController;
/*      */   }
/*      */   
/*      */   public DBSkill getPetBonusSkill() {
/*  902 */     return this.dbPetBonusSkill;
/*      */   }
/*      */   
/*      */   public byte[] getBitmap() {
/*  906 */     return this.bitmap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BufferedImage getImage() {
/*  917 */     return this.image;
/*      */   }
/*      */   
/*      */   public String getMeshID() {
/*  921 */     return this.meshID;
/*      */   }
/*      */   
/*      */   public String getBaseTextureID() {
/*  925 */     return this.baseTextureID;
/*      */   }
/*      */   
/*      */   public String getBumpTextureID() {
/*  929 */     return this.bumpTextureID;
/*      */   }
/*      */   
/*      */   public String getGlowTextureID() {
/*  933 */     return this.glowTextureID;
/*      */   }
/*      */   
/*      */   public String getShaderID() {
/*  937 */     return this.shaderID;
/*      */   }
/*      */   
/*      */   public String getImageID() {
/*  941 */     return this.bitmapID;
/*      */   }
/*      */   
/*      */   public BufferedImage getShardImage() {
/*  945 */     return this.shardImage;
/*      */   }
/*      */   
/*      */   public BufferedImage getOverlayImage() {
/*  949 */     return this.overlayImage;
/*      */   }
/*      */   
/*      */   public BufferedImage getOverlayShard() {
/*  953 */     return this.overlayShard;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DBFormulaSet.Result getStatRequirements(ParameterSet ps) {
/*  961 */     if (this.dbFormulaSet == null) return null;
/*      */     
/*  963 */     DBFormulaSet.Result result = this.dbFormulaSet.getResult(ps);
/*      */     
/*  965 */     if (result != null) {
/*      */       
/*  967 */       result.cunning = (int)(result.cunning + 0.5D);
/*  968 */       result.physique = (int)(result.physique + 0.5D);
/*  969 */       result.spirit = (int)(result.spirit + 0.5D);
/*      */     } 
/*      */     
/*  972 */     return result;
/*      */   }
/*      */   
/*      */   public void determineStatRequirements() {
/*  976 */     DBFormulaSet.Result result = getStatRequirements(this);
/*      */     
/*  978 */     if (result != null) {
/*  979 */       this.reqDex = (int)result.cunning;
/*  980 */       this.reqStr = (int)result.physique;
/*  981 */       this.reqInt = (int)result.spirit;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float getCharAttackSpeed() {
/*  987 */     DBStat stat = DBStat.getDBStat(this.stats, "characterAttackSpeed", 1);
/*      */     
/*  989 */     if (stat == null) return 0.0F;
/*      */     
/*  991 */     return stat.getStatMin();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDefenseAttrArmor() {
/*  996 */     DBStat stat = DBStat.getDBStat(this.stats, "defensiveProtection", 1);
/*      */     
/*  998 */     if (stat == null) return 0;
/*      */     
/* 1000 */     return (int)stat.getStatMin();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getShieldBlockDefense() {
/* 1005 */     DBStat stat = DBStat.getDBStat(this.stats, "defensiveBlock", 1);
/*      */     
/* 1007 */     if (stat == null) return 0;
/*      */     
/* 1009 */     return (int)stat.getStatMin();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getShieldBlockChance() {
/* 1014 */     DBStat stat = DBStat.getDBStat(this.stats, "defensiveBlock", 1);
/*      */     
/* 1016 */     if (stat == null) return 0;
/*      */     
/* 1018 */     return stat.getStatChance();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDamageAvgPierceRatio() {
/* 1023 */     DBStat stat = DBStat.getDBStat(this.stats, "offensivePierceRatio", 1);
/*      */     
/* 1025 */     if (stat == null) return 0;
/*      */     
/* 1027 */     int minVal = (int)stat.getStatMin();
/* 1028 */     int maxVal = (int)stat.getStatMax();
/*      */     
/* 1030 */     if (maxVal > 0) {
/* 1031 */       minVal += maxVal;
/* 1032 */       minVal /= 2;
/*      */     } 
/*      */     
/* 1035 */     return minVal;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDamageAvgBase() {
/* 1040 */     int total = 0;
/* 1041 */     int minVal = 0;
/* 1042 */     int maxVal = 0;
/*      */     
/* 1044 */     for (DBStat stat : this.stats) {
/* 1045 */       if (stat.getStatType().equals("offensiveBaseAether")) {
/* 1046 */         minVal = (int)stat.getStatMin();
/* 1047 */         maxVal = (int)stat.getStatMax();
/*      */         
/* 1049 */         if (maxVal > 0) {
/* 1050 */           minVal += maxVal;
/* 1051 */           minVal /= 2;
/*      */         } 
/*      */         
/* 1054 */         total += minVal;
/*      */       } 
/*      */       
/* 1057 */       if (stat.getStatType().equals("offensiveBaseChaos")) {
/* 1058 */         minVal = (int)stat.getStatMin();
/* 1059 */         maxVal = (int)stat.getStatMax();
/*      */         
/* 1061 */         if (maxVal > 0) {
/* 1062 */           minVal += maxVal;
/* 1063 */           minVal /= 2;
/*      */         } 
/*      */         
/* 1066 */         total += minVal;
/*      */       } 
/*      */       
/* 1069 */       if (stat.getStatType().equals("offensiveBaseCold")) {
/* 1070 */         minVal = (int)stat.getStatMin();
/* 1071 */         maxVal = (int)stat.getStatMax();
/*      */         
/* 1073 */         if (maxVal > 0) {
/* 1074 */           minVal += maxVal;
/* 1075 */           minVal /= 2;
/*      */         } 
/*      */         
/* 1078 */         total += minVal;
/*      */       } 
/*      */       
/* 1081 */       if (stat.getStatType().equals("offensiveBaseFire")) {
/* 1082 */         minVal = (int)stat.getStatMin();
/* 1083 */         maxVal = (int)stat.getStatMax();
/*      */         
/* 1085 */         if (maxVal > 0) {
/* 1086 */           minVal += maxVal;
/* 1087 */           minVal /= 2;
/*      */         } 
/*      */         
/* 1090 */         total += minVal;
/*      */       } 
/*      */       
/* 1093 */       if (stat.getStatType().equals("offensiveBaseLife")) {
/* 1094 */         minVal = (int)stat.getStatMin();
/* 1095 */         maxVal = (int)stat.getStatMax();
/*      */         
/* 1097 */         if (maxVal > 0) {
/* 1098 */           minVal += maxVal;
/* 1099 */           minVal /= 2;
/*      */         } 
/*      */         
/* 1102 */         total += minVal;
/*      */       } 
/*      */       
/* 1105 */       if (stat.getStatType().equals("offensiveBaseLightning")) {
/* 1106 */         minVal = (int)stat.getStatMin();
/* 1107 */         maxVal = (int)stat.getStatMax();
/*      */         
/* 1109 */         if (maxVal > 0) {
/* 1110 */           minVal += maxVal;
/* 1111 */           minVal /= 2;
/*      */         } 
/*      */         
/* 1114 */         total += minVal;
/*      */       } 
/*      */       
/* 1117 */       if (stat.getStatType().equals("offensivePhysical")) {
/* 1118 */         minVal = (int)stat.getStatMin();
/* 1119 */         maxVal = (int)stat.getStatMax();
/*      */         
/* 1121 */         if (maxVal > 0) {
/* 1122 */           minVal += maxVal;
/* 1123 */           minVal /= 2;
/*      */         } 
/*      */         
/* 1126 */         total += minVal;
/*      */       } 
/*      */       
/* 1129 */       if (stat.getStatType().equals("offensiveBasePoison")) {
/* 1130 */         minVal = (int)stat.getStatMin();
/* 1131 */         maxVal = (int)stat.getStatMax();
/*      */         
/* 1133 */         if (maxVal > 0) {
/* 1134 */           minVal += maxVal;
/* 1135 */           minVal /= 2;
/*      */         } 
/*      */         
/* 1138 */         total += minVal;
/*      */       } 
/*      */     } 
/*      */     
/* 1142 */     return total;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTotalAttCount() {
/* 1147 */     int total = 0;
/*      */     
/* 1149 */     for (DBStat stat : this.stats) {
/* 1150 */       if (stat.getStatMin() > 0.0F) total++; 
/* 1151 */       if (stat.getStatModifier() > 0) total++; 
/* 1152 */       if (stat.getDurationModifier() > 0) total++; 
/* 1153 */       if (stat.getMaxResist() > 0) total++;
/*      */     
/*      */     } 
/* 1156 */     if (this.statBonusRaces != null && !this.statBonusRaces.isEmpty()) total += this.statBonusRaces.size();
/*      */     
/* 1158 */     return total;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getItemPrefixCost() {
/* 1163 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getItemSuffixCost() {
/* 1168 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String appendNamePart(String s, int part, boolean withDmg) {
/* 1176 */     switch (part) {
/*      */       case 2:
/* 1178 */         if (this.qualityText != null) {
/* 1179 */           if (!s.isEmpty()) s = s + " ";
/*      */           
/* 1181 */           s = s + this.qualityText;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 3:
/* 1186 */         if (this.styleText != null) {
/* 1187 */           if (!s.isEmpty()) s = s + " ";
/*      */           
/* 1189 */           s = s + this.styleText;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 4:
/* 1194 */         if (this.name != null) {
/* 1195 */           if (!s.isEmpty()) s = s + " ";
/*      */           
/* 1197 */           s = s + this.name;
/*      */           
/* 1199 */           if (withDmg) {
/* 1200 */             String dmg = getMainDamageType();
/*      */             
/* 1202 */             if (dmg != null) {
/* 1203 */               s = s + " [" + dmg + "]";
/*      */             }
/*      */           } 
/*      */         } 
/*      */         break;
/*      */     } 
/*      */     
/* 1210 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getModName() {
/* 1215 */     if (this.rarity == null) return null;
/*      */     
/* 1217 */     String modName = null;
/*      */     
/* 1219 */     if (this.rarity.equals("Epic") || this.rarity.equals("Legendary")) {
/* 1220 */       if (this.itemID.startsWith("records/apoc/")) modName = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MOD_CAT"); 
/* 1221 */       if (this.itemID.startsWith("records/cat/")) modName = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MOD_CAT"); 
/* 1222 */       if (this.itemID.startsWith("records/doh/")) modName = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MOD_CAT"); 
/* 1223 */       if (this.itemID.startsWith("records/d2/")) modName = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MOD_D2"); 
/* 1224 */       if (this.itemID.startsWith("records/d3/")) modName = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MOD_D3"); 
/* 1225 */       if (this.itemID.startsWith("records/tq/")) modName = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MOD_GQ"); 
/* 1226 */       if (this.itemID.startsWith("records/ncff/")) modName = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MOD_NCFF"); 
/* 1227 */       if (this.itemID.startsWith("records/zen/")) modName = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MOD_ZEN");
/*      */       
/* 1229 */       if (modName != null) modName = "[" + modName + "]";
/*      */     
/*      */     } 
/* 1232 */     return modName;
/*      */   }
/*      */   
/*      */   private String combineName(boolean withDmg) {
/* 1236 */     String fullname = "";
/*      */     
/* 1238 */     fullname = appendNamePart(fullname, GDStashFrame.dbConfig.namePart1, withDmg);
/* 1239 */     fullname = appendNamePart(fullname, GDStashFrame.dbConfig.namePart2, withDmg);
/* 1240 */     fullname = appendNamePart(fullname, GDStashFrame.dbConfig.namePart3, withDmg);
/* 1241 */     fullname = appendNamePart(fullname, GDStashFrame.dbConfig.namePart4, withDmg);
/* 1242 */     fullname = appendNamePart(fullname, GDStashFrame.dbConfig.namePart5, withDmg);
/*      */ 
/*      */     
/* 1245 */     String modName = getModName();
/* 1246 */     if (modName != null) {
/* 1247 */       fullname = fullname + " " + modName;
/*      */     }
/*      */     
/* 1250 */     return fullname;
/*      */   }
/*      */   
/*      */   private int getRarityInt() {
/* 1254 */     if (this.rarity == null) return -1;
/*      */     
/* 1256 */     if (this.rarity.equals("Broken")) return 1; 
/* 1257 */     if (this.rarity.equals("Common")) return 2; 
/* 1258 */     if (this.rarity.equals("Magical")) return 3; 
/* 1259 */     if (this.rarity.equals("Rare")) return 4; 
/* 1260 */     if (this.rarity.equals("Epic")) return 5; 
/* 1261 */     if (this.rarity.equals("Legendary")) return 6; 
/* 1262 */     if (this.rarity.equals("Quest")) return 7;
/*      */     
/* 1264 */     if (this.rarity.equals("Lesser")) return 8; 
/* 1265 */     if (this.rarity.equals("Greater")) return 9; 
/* 1266 */     if (this.rarity.equals("Divine")) return 10;
/*      */     
/* 1268 */     return -1;
/*      */   }
/*      */   
/*      */   private int getArmorClassInt() {
/* 1272 */     if (this.armorClass == null) return -1;
/*      */     
/* 1274 */     if (this.armorClass.equals("Caster")) return 1; 
/* 1275 */     if (this.armorClass.equals("Light")) return 2; 
/* 1276 */     if (this.armorClass.equals("Heavy")) return 3;
/*      */     
/* 1278 */     return -1;
/*      */   }
/*      */   
/*      */   private int getArtifactClassInt() {
/* 1282 */     if (this.artifactClass == null) return -1;
/*      */     
/* 1284 */     if (this.artifactClass.equals("Lesser")) return 1; 
/* 1285 */     if (this.artifactClass.equals("Greater")) return 2; 
/* 1286 */     if (this.artifactClass.equals("Divine")) return 3;
/*      */     
/* 1288 */     return -1;
/*      */   }
/*      */   
/*      */   public boolean isArmor() {
/* 1292 */     return ItemClass.isArmor(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isArtifact() {
/* 1296 */     return ItemClass.isArtifact(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isComponent() {
/* 1300 */     return ItemClass.isComponent(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isEnchantment() {
/* 1304 */     return ItemClass.isEnchantment(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isJewelry() {
/* 1308 */     return ItemClass.isJewelry(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isNote() {
/* 1312 */     return ItemClass.isNote(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isFactionBooster() {
/* 1316 */     return ItemClass.isFactionBooster(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isElixir() {
/* 1320 */     return ItemClass.isElixir(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isPotion() {
/* 1324 */     return ItemClass.isPotion(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isOffhand() {
/* 1328 */     return ItemClass.isOffhand(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isWeapon() {
/* 1332 */     return ItemClass.isWeapon(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean is1HWeapon() {
/* 1336 */     return ItemClass.is1HWeapon(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean is2HWeapon() {
/* 1340 */     return ItemClass.is2HWeapon(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isStackable() {
/* 1344 */     return ItemClass.isStackable(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isEpic() {
/* 1348 */     if (this.rarity == null) return false;
/*      */     
/* 1350 */     return this.rarity.equals("Epic");
/*      */   }
/*      */   
/*      */   public boolean isLegendary() {
/* 1354 */     if (this.rarity == null) return false;
/*      */     
/* 1356 */     return this.rarity.equals("Legendary");
/*      */   }
/*      */   
/*      */   public boolean isUnique() {
/* 1360 */     return (isEpic() || isLegendary());
/*      */   }
/*      */   
/*      */   public String getMainDamageType() {
/* 1364 */     if (!isWeapon()) return null;
/*      */     
/* 1366 */     DBStat maxDmgStat = null;
/* 1367 */     int max = 0;
/* 1368 */     int minDmg = 0;
/* 1369 */     int maxDmg = 0;
/*      */     
/* 1371 */     for (DBStat stat : this.stats) {
/* 1372 */       boolean found = false;
/*      */       
/* 1374 */       if (stat.getStatType().equals("offensiveAether")) found = true; 
/* 1375 */       if (stat.getStatType().equals("offensiveBaseAether")) found = true; 
/* 1376 */       if (stat.getStatType().equals("offensiveChaos")) found = true; 
/* 1377 */       if (stat.getStatType().equals("offensiveBaseChaos")) found = true; 
/* 1378 */       if (stat.getStatType().equals("offensiveCold")) found = true; 
/* 1379 */       if (stat.getStatType().equals("offensiveBaseCold")) found = true; 
/* 1380 */       if (stat.getStatType().equals("offensiveElemental")) found = true; 
/* 1381 */       if (stat.getStatType().equals("offensiveFire")) found = true; 
/* 1382 */       if (stat.getStatType().equals("offensiveBaseFire")) found = true; 
/* 1383 */       if (stat.getStatType().equals("offensiveLife")) found = true; 
/* 1384 */       if (stat.getStatType().equals("offensiveBaseLife")) found = true; 
/* 1385 */       if (stat.getStatType().equals("offensiveLightning")) found = true; 
/* 1386 */       if (stat.getStatType().equals("offensiveBaseLightning")) found = true; 
/* 1387 */       if (stat.getStatType().equals("offensivePhysical")) found = true; 
/* 1388 */       if (stat.getStatType().equals("offensiveBonusPhysical")) found = true; 
/* 1389 */       if (stat.getStatType().equals("offensivePierce")) found = true; 
/* 1390 */       if (stat.getStatType().equals("offensivePoison")) found = true; 
/* 1391 */       if (stat.getStatType().equals("offensiveBasePoison")) found = true;
/*      */ 
/*      */       
/* 1394 */       if (!found)
/*      */         continue; 
/* 1396 */       if (stat.getStatChance() == 0 && 
/* 1397 */         stat.getStatMin() > 0.0F) {
/* 1398 */         minDmg = (int)stat.getStatMin();
/* 1399 */         maxDmg = (int)stat.getStatMax();
/*      */         
/* 1401 */         if (maxDmg > 0) {
/* 1402 */           minDmg += maxDmg;
/*      */         } else {
/* 1404 */           minDmg += minDmg;
/*      */         } 
/*      */         
/* 1407 */         if (minDmg > max) {
/* 1408 */           max = minDmg;
/* 1409 */           maxDmgStat = stat;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1415 */     String sDmg = null;
/*      */     
/* 1417 */     if (maxDmgStat != null) {
/* 1418 */       if (maxDmgStat.getStatType().equals("offensiveAether")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_AETHER"); 
/* 1419 */       if (maxDmgStat.getStatType().equals("offensiveBaseAether")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_AETHER"); 
/* 1420 */       if (maxDmgStat.getStatType().equals("offensiveChaos")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_CHAOS"); 
/* 1421 */       if (maxDmgStat.getStatType().equals("offensiveBaseChaos")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_CHAOS"); 
/* 1422 */       if (maxDmgStat.getStatType().equals("offensiveCold")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_COLD"); 
/* 1423 */       if (maxDmgStat.getStatType().equals("offensiveBaseCold")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_COLD"); 
/* 1424 */       if (maxDmgStat.getStatType().equals("offensiveElemental")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_ELEMENTAL"); 
/* 1425 */       if (maxDmgStat.getStatType().equals("offensiveFire")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_FIRE"); 
/* 1426 */       if (maxDmgStat.getStatType().equals("offensiveBaseFire")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_FIRE"); 
/* 1427 */       if (maxDmgStat.getStatType().equals("offensiveLife")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_LIFE"); 
/* 1428 */       if (maxDmgStat.getStatType().equals("offensiveBaseLife")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_LIFE"); 
/* 1429 */       if (maxDmgStat.getStatType().equals("offensiveLightning")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_LIGHTNING"); 
/* 1430 */       if (maxDmgStat.getStatType().equals("offensiveBaseLightning")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_LIGHTNING"); 
/* 1431 */       if (maxDmgStat.getStatType().equals("offensivePhysical")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_PHYSICAL"); 
/* 1432 */       if (maxDmgStat.getStatType().equals("offensiveBonusPhysical")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_PHYSICAL"); 
/* 1433 */       if (maxDmgStat.getStatType().equals("offensivePierce")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_PIERCE"); 
/* 1434 */       if (maxDmgStat.getStatType().equals("offensivePoison")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_POISON"); 
/* 1435 */       if (maxDmgStat.getStatType().equals("offensiveBasePoison")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_POISON");
/*      */       
/* 1437 */       return sDmg;
/*      */     } 
/*      */     
/* 1440 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setItemClass(String itemClass, String filename, String dlc) {
/* 1448 */     this.itemClass = itemClass;
/*      */     
/* 1450 */     if (filename.equals("records/items/misc/inventorybag.dbr")) {
/* 1451 */       this.itemClass = "QuestItem";
/*      */     }
/*      */     
/* 1454 */     if (itemClass.equals("QuestItem")) setQuestItem(true);
/*      */ 
/*      */     
/* 1457 */     if (filename.equals("records/items/crafting/consumables/xppotion_malmouth.dbr")) {
/* 1458 */       this.itemClass = "ItemDevotionReset";
/*      */     }
/*      */ 
/*      */     
/* 1462 */     if (filename.equals("records/items/transmutes/dlc_medal_backer.dbr") || filename
/* 1463 */       .equals("records/items/transmutes/dlc_medal_ks_officer.dbr") || filename
/* 1464 */       .equals("records/items/transmutes/dlc_medal_ks_service.dbr") || filename
/* 1465 */       .equals("records/items/transmutes/dlc_medal_ks_veteran.dbr") || filename
/* 1466 */       .equals("records/items/transmutes/transmute_medal_backer.dbr") || filename
/* 1467 */       .equals("records/items/transmutes/transmute_medal_ks_officer.dbr") || filename
/* 1468 */       .equals("records/items/transmutes/transmute_medal_ks_service.dbr") || filename
/* 1469 */       .equals("records/items/transmutes/transmute_medal_ks_veteran.dbr")) {
/* 1470 */       this.itemClass = "Kickstarter";
/*      */     }
/*      */     
/* 1473 */     if (filename.equals("records/items/transmutes/invisible_head.dbr") || filename
/* 1474 */       .equals("records/items/transmutes/invisible_medal.dbr") || filename
/* 1475 */       .equals("records/items/transmutes/invisible_shoulders.dbr") || filename
/* 1476 */       .equals("records/items/transmutes/transmute_removal.dbr")) {
/* 1477 */       this.itemClass = "Kickstarter";
/*      */     }
/*      */ 
/*      */     
/* 1481 */     if (filename.equals("records/items/bonusitems/bonus_burrwitchbrew.dbr") || filename
/* 1482 */       .equals("records/items/bonusitems/bonus_summonwisp.dbr") || filename
/* 1483 */       .equals("records/items/transmutes/dlc_head_admiralhat.dbr") || filename
/* 1484 */       .equals("records/items/transmutes/dlc_head_pillager.dbr") || filename
/* 1485 */       .equals("records/items/transmutes/dlc_head_powderedwig.dbr") || filename
/* 1486 */       .equals("records/items/transmutes/dlc_head_samurai.dbr") || filename
/* 1487 */       .equals("records/items/transmutes/dlc_head_warhelm.dbr") || filename
/* 1488 */       .equals("records/items/transmutes/dlc_torso_swashbucklerscoat.dbr") || filename
/* 1489 */       .equals("records/items/transmutes/transmute_admiralhat.dbr") || filename
/* 1490 */       .equals("records/items/transmutes/transmute_pillagerhelm.dbr") || filename
/* 1491 */       .equals("records/items/transmutes/transmute_powderedwig.dbr") || filename
/* 1492 */       .equals("records/items/transmutes/transmute_samuraihelm.dbr") || filename
/* 1493 */       .equals("records/items/transmutes/transmute_swashbucklerscoat.dbr") || filename
/* 1494 */       .equals("records/items/transmutes/transmute_warhelm.dbr")) {
/* 1495 */       this.itemClass = "Kickstarter";
/*      */     }
/*      */ 
/*      */     
/* 1499 */     if (filename.equals("records/items/bonusitems/bonus_summonbat.dbr") || filename
/* 1500 */       .equals("records/items/bonusitems/bonus_summoncrate.dbr") || filename
/* 1501 */       .equals("records/items/bonusitems/bonus_summonowl.dbr") || filename
/* 1502 */       .startsWith("records/items/transmutes/dragongeneral/") || filename
/* 1503 */       .startsWith("records/items/transmutes/greatwolf/") || filename
/* 1504 */       .startsWith("records/items/transmutes/knight/") || filename
/* 1505 */       .startsWith("records/items/transmutes/wizard/") || filename
/* 1506 */       .equals("records/items/transmutes/transmute_blackknight_set.dbr") || filename
/* 1507 */       .equals("records/items/transmutes/transmute_darkwizard_set.dbr") || filename
/* 1508 */       .equals("records/items/transmutes/transmute_dragongeneral_set.dbr") || filename
/* 1509 */       .equals("records/items/transmutes/transmute_greatwolf_set.dbr") || filename
/* 1510 */       .equals("records/items/transmutes/transmute_silverknight_set.dbr") || filename
/* 1511 */       .equals("records/items/transmutes/transmute_wizard_set.dbr")) {
/* 1512 */       this.itemClass = "Kickstarter";
/*      */     }
/*      */ 
/*      */     
/* 1516 */     if (filename.startsWith("records/items/bonusitems/") || filename
/* 1517 */       .startsWith("records/items/transmutes/")) {
/* 1518 */       this.itemClass = "Kickstarter";
/*      */     }
/*      */     
/* 1521 */     if (dlc != null) {
/* 1522 */       this.itemClass = "Kickstarter";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setQuestItem(boolean questItem) {
/* 1530 */     if (!this.questItem) this.questItem = questItem; 
/*      */   }
/*      */   
/*      */   private void setCraftID(String craftID) {
/* 1534 */     this.dbCraft.setCraftID(craftID);
/*      */   }
/*      */   
/*      */   private void setItemNameTag(String itemNameTag) {
/* 1538 */     String s = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_ITEMS, itemNameTag, false);
/*      */ 
/*      */     
/* 1541 */     if (s == null) {
/* 1542 */       s = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_STORY, itemNameTag, false);
/*      */     }
/*      */     
/* 1545 */     this.genderCode = -1;
/*      */     
/* 1547 */     if (s != null && 
/* 1548 */       s.startsWith("[")) {
/*      */       
/* 1550 */       String code = s.substring(0, 4);
/* 1551 */       s = s.substring(4);
/*      */       
/* 1553 */       this.genderCode = ARZDecompress.getGenderCode(code);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1559 */     if (this.genderCode == -1) this.genderCode = 0;
/*      */     
/* 1561 */     this.name = s;
/*      */   }
/*      */   
/*      */   private void setQualityTag(String qualityTag) {
/* 1565 */     this.qualityTag = qualityTag;
/*      */     
/* 1567 */     if (qualityTag == null) {
/* 1568 */       this.qualityText = null;
/*      */     } else {
/* 1570 */       this.qualityText = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.GD, GDConstants.TAG_TEXT_ITEMS, qualityTag, false);
/*      */       
/* 1572 */       if (this.qualityText != null) {
/* 1573 */         String[] genders = ARZDecompress.getGenderTexts(this.qualityText);
/*      */         
/* 1575 */         this.qualityText = genders[this.genderCode];
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setStyleTag(String styleTag) {
/* 1581 */     this.styleTag = styleTag;
/*      */     
/* 1583 */     if (styleTag == null) {
/* 1584 */       this.styleText = null;
/*      */     } else {
/* 1586 */       this.styleText = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.GD, GDConstants.TAG_TEXT_ITEMS, styleTag, false);
/*      */       
/* 1588 */       if (this.styleText != null) {
/* 1589 */         String[] genders = ARZDecompress.getGenderTexts(this.styleText);
/*      */         
/* 1591 */         this.styleText = genders[this.genderCode];
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setItemSetNameTag(String setNameTag) {
/* 1597 */     this.setName = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_ITEMS, setNameTag, false);
/*      */   }
/*      */   
/*      */   private void setCostFormulaSetID(String costFormulaSetID) {
/* 1601 */     this.costFormulaSetID = costFormulaSetID;
/*      */     
/* 1603 */     if (ItemClass.usesCostFormula(this.itemClass)) {
/* 1604 */       if (costFormulaSetID == null) {
/* 1605 */         costFormulaSetID = "records/game/itemcostformulas.dbr";
/*      */       }
/*      */       
/* 1608 */       this.dbFormulaSet = ARZFormulaSetPool.getFormulaSet(costFormulaSetID);
/*      */     } else {
/* 1610 */       this.dbFormulaSet = null;
/*      */     } 
/*      */ 
/*      */     
/* 1614 */     if (this.dbFormulaSet != null) {
/* 1615 */       determineStatRequirements();
/*      */     }
/*      */   }
/*      */   
/*      */   private void setBitmap(byte[] bitmap) {
/* 1620 */     int size = 0;
/*      */     
/* 1622 */     if (bitmap != null) size = bitmap.length;
/*      */     
/* 1624 */     if (size >= 131072) {
/* 1625 */       Object[] args = { this.itemID };
/* 1626 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_IN_ITEM_IMAGE_SIZE", args);
/*      */       
/* 1628 */       GDMsgLogger.addWarning(msg);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1633 */     this.bitmap = bitmap;
/*      */   }
/*      */   
/*      */   private void setShardBitmap(byte[] shardBitmap) {
/* 1637 */     int size = 0;
/*      */     
/* 1639 */     if (shardBitmap != null) size = shardBitmap.length;
/*      */     
/* 1641 */     if (size >= 32768) {
/* 1642 */       Object[] args = { this.itemID };
/* 1643 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_IN_ITEM_SHARD_SIZE", args);
/*      */       
/* 1645 */       GDMsgLogger.addWarning(msg);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1650 */     this.shardBitmap = shardBitmap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void clearBuffer() {
/* 1658 */     hashBuffer.clear();
/*      */   }
/*      */   
/*      */   public static void createTables() throws SQLException {
/* 1662 */     String dropTable = "DROP TABLE GD_ITEM";
/* 1663 */     String createTable = "CREATE TABLE GD_ITEM (ITEM_ID       VARCHAR(256) NOT NULL, ITEM_CLASS         VARCHAR(32), ARMOR_CLASS        VARCHAR(32), ARTIFACT_CLASS     VARCHAR(32), MESH_ID            VARCHAR(256), BASE_TEXTURE_ID    VARCHAR(512), BUMP_TEXTURE_ID    VARCHAR(512), GLOW_TEXTURE_ID    VARCHAR(512), SHADER_ID          VARCHAR(256), BITMAP_ID          VARCHAR(256), BITMAP             BLOB(128K), SHARD_BITMAP_ID    VARCHAR(256), SHARD_BITMAP       BLOB(32K), GENDER_CODE        INTEGER, NAME               VARCHAR(256), DESCRIPTION        VARCHAR(8000), QUALITY_TAG        VARCHAR(64), QUALITY_TEXT       VARCHAR(64), STYLE_TAG          VARCHAR(64), STYLE_TEXT         VARCHAR(64), NAME_FULL          VARCHAR(256), RARITY             VARCHAR(32), SET_ID             VARCHAR(256), SET_NAME           VARCHAR(256), BONUS_AFFIXSET_ID  VARCHAR(256), ITEM_SKILL_ID      VARCHAR(256), ITEM_SKILL_LEVEL   INTEGER,PET_BONUS_SKILL_ID VARCHAR(256), CONTROLLER_ID      VARCHAR(256), COST_FORMULASET_ID VARCHAR(256), CONVERT_IN         VARCHAR(16), CONVERT_OUT        VARCHAR(16), CONVERT_IN_2       VARCHAR(16), CONVERT_OUT_2      VARCHAR(16), SOULBOUND          BOOLEAN, HIDE_PREFIX        BOOLEAN, HIDE_SUFFIX        BOOLEAN, QUESTITEM          BOOLEAN, CANNOT_PICKUP      BOOLEAN, ENEMY_ONLY         BOOLEAN, LEVEL              INTEGER, REQ_LEVEL          INTEGER, REQ_DEX            INTEGER, REQ_INT            INTEGER, REQ_STR            INTEGER, OFFENSE_PRC        INTEGER, RETAL_PRC          INTEGER, PLUS_ALLSKILLS     INTEGER, COMPONENT_PIECES   INTEGER, MAX_STACKSIZE      INTEGER, RNG_PERCENT        INTEGER, SLOT_AXE_1H        BOOLEAN, SLOT_AXE_2H        BOOLEAN, SLOT_DAGGER_1H     BOOLEAN, SLOT_MACE_1H       BOOLEAN, SLOT_MACE_2H       BOOLEAN, SLOT_SCEPTER_1H    BOOLEAN, SLOT_SPEAR_1H      BOOLEAN, SLOT_STAFF_2H      BOOLEAN, SLOT_SWORD_1H      BOOLEAN, SLOT_SWORD_2H      BOOLEAN, SLOT_RANGED_1H     BOOLEAN, SLOT_RANGED_2H     BOOLEAN, SLOT_SHIELD        BOOLEAN, SLOT_OFFHAND       BOOLEAN, SLOT_AMULET        BOOLEAN, SLOT_BELT          BOOLEAN, SLOT_MEDAL         BOOLEAN, SLOT_RING          BOOLEAN, SLOT_HEAD          BOOLEAN, SLOT_SHOULDERS     BOOLEAN, SLOT_CHEST         BOOLEAN, SLOT_HANDS         BOOLEAN, SLOT_LEGS          BOOLEAN, SLOT_FEET          BOOLEAN, PRIMARY KEY (ITEM_ID))";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1753 */     try (Connection conn = GDDBData.getConnection()) {
/* 1754 */       boolean auto = conn.getAutoCommit();
/* 1755 */       conn.setAutoCommit(false);
/*      */       
/* 1757 */       try (Statement st = conn.createStatement()) {
/* 1758 */         if (GDDBUtil.tableExists(conn, "GD_ITEM")) {
/* 1759 */           st.execute(dropTable);
/*      */         }
/* 1761 */         if (GDDBUtil.tableExists(conn, "GD_ITEM_CHAR")) {
/* 1762 */           st.execute("DROP TABLE GD_ITEM_CHAR");
/*      */         }
/* 1764 */         if (GDDBUtil.tableExists(conn, "GD_ITEM_CHARRACES")) {
/* 1765 */           st.execute("DROP TABLE GD_ITEM_CHARRACES");
/*      */         }
/* 1767 */         if (GDDBUtil.tableExists(conn, "GD_ITEM_DAMAGE")) {
/* 1768 */           st.execute("DROP TABLE GD_ITEM_DAMAGE");
/*      */         }
/* 1770 */         st.execute(createTable);
/* 1771 */         st.close();
/*      */         
/* 1773 */         conn.commit();
/*      */         
/* 1775 */         DBItemCraft.createTables(conn);
/* 1776 */         DBStat.createItemTable(conn);
/* 1777 */         DBStatBonusRace.createItemTable(conn);
/* 1778 */         DBSkillBonus.createItemTable(conn);
/* 1779 */         DBSkillModifier.createItemTable(conn);
/*      */       }
/* 1781 */       catch (SQLException ex) {
/* 1782 */         conn.rollback();
/*      */         
/* 1784 */         Object[] args = { "GD_ITEM" };
/* 1785 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*      */         
/* 1787 */         GDMsgLogger.addError(msg);
/*      */         
/* 1789 */         throw ex;
/*      */       } finally {
/*      */         
/* 1792 */         conn.setAutoCommit(auto);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void delete(String itemID) throws SQLException {
/* 1798 */     String deleteEntry = "DELETE FROM GD_ITEM WHERE ITEM_ID = ?";
/*      */     
/* 1800 */     try (Connection conn = GDDBData.getConnection()) {
/* 1801 */       boolean auto = conn.getAutoCommit();
/* 1802 */       conn.setAutoCommit(false);
/*      */       
/* 1804 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 1805 */         ps.setString(1, itemID);
/* 1806 */         ps.executeUpdate();
/* 1807 */         ps.close();
/*      */         
/* 1809 */         DBItemCraft.delete(conn, itemID);
/* 1810 */         DBStat.deleteItem(conn, itemID);
/* 1811 */         DBStatBonusRace.deleteItem(conn, itemID);
/* 1812 */         DBSkillBonus.deleteItem(conn, itemID);
/* 1813 */         DBSkillModifier.deleteItem(conn, itemID);
/*      */         
/* 1815 */         conn.commit();
/*      */       }
/* 1817 */       catch (SQLException ex) {
/* 1818 */         conn.rollback();
/*      */         
/* 1820 */         Object[] args = { itemID, "GD_ITEM" };
/* 1821 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*      */         
/* 1823 */         GDMsgLogger.addError(msg);
/* 1824 */         GDMsgLogger.addError(ex);
/*      */         
/* 1826 */         throw ex;
/*      */       } finally {
/*      */         
/* 1829 */         conn.setAutoCommit(auto);
/*      */       }
/*      */     
/* 1832 */     } catch (SQLException ex) {
/* 1833 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void insert(ARZRecord record) throws SQLException {
/* 1838 */     DBItem entry = get(record.getFileName());
/*      */     
/* 1840 */     if (entry != null)
/*      */       return; 
/* 1842 */     DBItem item = new DBItem(record);
/*      */     
/* 1844 */     String insertItem = "INSERT INTO GD_ITEM VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */ 
/*      */     
/* 1847 */     try (Connection conn = GDDBData.getConnection()) {
/* 1848 */       boolean auto = conn.getAutoCommit();
/* 1849 */       conn.setAutoCommit(false);
/*      */       
/* 1851 */       try (PreparedStatement ps = conn.prepareStatement(insertItem)) {
/* 1852 */         ps.setString(1, item.itemID);
/* 1853 */         ps.setString(2, item.itemClass);
/* 1854 */         ps.setString(3, item.armorClass);
/* 1855 */         ps.setString(4, item.artifactClass);
/* 1856 */         ps.setString(5, item.meshID);
/* 1857 */         ps.setString(6, item.baseTextureID);
/* 1858 */         ps.setString(7, item.bumpTextureID);
/* 1859 */         ps.setString(8, item.glowTextureID);
/* 1860 */         ps.setString(9, item.shaderID);
/* 1861 */         ps.setString(10, item.bitmapID);
/* 1862 */         if (item.bitmap == null) {
/* 1863 */           ps.setBlob(11, (Blob)null);
/*      */         } else {
/* 1865 */           ps.setBlob(11, new ByteArrayInputStream(item.bitmap));
/*      */         } 
/* 1867 */         ps.setString(12, item.shardBitmapID);
/* 1868 */         if (item.shardBitmap == null) {
/* 1869 */           ps.setBlob(13, (Blob)null);
/*      */         } else {
/* 1871 */           ps.setBlob(13, new ByteArrayInputStream(item.shardBitmap));
/*      */         } 
/* 1873 */         ps.setInt(14, item.genderCode);
/* 1874 */         ps.setString(15, item.name);
/* 1875 */         ps.setString(16, item.description);
/* 1876 */         ps.setString(17, item.qualityTag);
/* 1877 */         ps.setString(18, item.qualityText);
/* 1878 */         ps.setString(19, item.styleTag);
/* 1879 */         ps.setString(20, item.styleText);
/* 1880 */         ps.setString(21, item.nameFull);
/* 1881 */         ps.setString(22, item.rarity);
/* 1882 */         ps.setString(23, item.setID);
/* 1883 */         ps.setString(24, item.setName);
/* 1884 */         ps.setString(25, item.bonusAffixSetID);
/* 1885 */         ps.setString(26, item.itemSkillID);
/* 1886 */         ps.setInt(27, item.itemSkillLevel);
/* 1887 */         ps.setString(28, item.petBonusSkillID);
/* 1888 */         ps.setString(29, item.controllerID);
/* 1889 */         ps.setString(30, item.costFormulaSetID);
/* 1890 */         ps.setString(31, item.convertIn);
/* 1891 */         ps.setString(32, item.convertOut);
/* 1892 */         ps.setString(33, item.convertIn2);
/* 1893 */         ps.setString(34, item.convertOut2);
/* 1894 */         ps.setBoolean(35, item.soulbound);
/* 1895 */         ps.setBoolean(36, item.hidePrefix);
/* 1896 */         ps.setBoolean(37, item.hideSuffix);
/* 1897 */         ps.setBoolean(38, item.questItem);
/* 1898 */         ps.setBoolean(39, item.cannotPickup);
/* 1899 */         ps.setBoolean(40, item.enemyOnly);
/* 1900 */         ps.setInt(41, item.itemLevel);
/* 1901 */         ps.setInt(42, item.reqLevel);
/* 1902 */         ps.setInt(43, item.reqDex);
/* 1903 */         ps.setInt(44, item.reqInt);
/* 1904 */         ps.setInt(45, item.reqStr);
/* 1905 */         ps.setInt(46, item.offensiveChance);
/* 1906 */         ps.setInt(47, item.retaliationChance);
/* 1907 */         ps.setInt(48, item.plusAllSkills);
/* 1908 */         ps.setInt(49, item.componentPieces);
/* 1909 */         ps.setInt(50, item.maxStackSize);
/* 1910 */         ps.setInt(51, item.scalePercent);
/* 1911 */         ps.setBoolean(52, item.slots.slotAxe1H);
/* 1912 */         ps.setBoolean(53, item.slots.slotAxe2H);
/* 1913 */         ps.setBoolean(54, item.slots.slotDagger1H);
/* 1914 */         ps.setBoolean(55, item.slots.slotMace1H);
/* 1915 */         ps.setBoolean(56, item.slots.slotMace2H);
/* 1916 */         ps.setBoolean(57, item.slots.slotScepter1H);
/* 1917 */         ps.setBoolean(58, item.slots.slotSpear1H);
/* 1918 */         ps.setBoolean(59, item.slots.slotStaff2H);
/* 1919 */         ps.setBoolean(60, item.slots.slotSword1H);
/* 1920 */         ps.setBoolean(61, item.slots.slotSword2H);
/* 1921 */         ps.setBoolean(62, item.slots.slotRanged1H);
/* 1922 */         ps.setBoolean(63, item.slots.slotRanged2H);
/* 1923 */         ps.setBoolean(64, item.slots.slotShield);
/* 1924 */         ps.setBoolean(65, item.slots.slotOffhand);
/* 1925 */         ps.setBoolean(66, item.slots.slotAmulet);
/* 1926 */         ps.setBoolean(67, item.slots.slotBelt);
/* 1927 */         ps.setBoolean(68, item.slots.slotMedal);
/* 1928 */         ps.setBoolean(69, item.slots.slotRing);
/* 1929 */         ps.setBoolean(70, item.slots.slotHead);
/* 1930 */         ps.setBoolean(71, item.slots.slotShoulders);
/* 1931 */         ps.setBoolean(72, item.slots.slotChest);
/* 1932 */         ps.setBoolean(73, item.slots.slotHands);
/* 1933 */         ps.setBoolean(74, item.slots.slotLegs);
/* 1934 */         ps.setBoolean(75, item.slots.slotFeet);
/*      */         
/* 1936 */         ps.executeUpdate();
/* 1937 */         ps.close();
/*      */         
/* 1939 */         if (item.dbCraft.getCraftID() != null) {
/* 1940 */           DBItemCraft.insert(conn, item);
/*      */         }
/* 1942 */         DBStat.insertItem(conn, item.itemID, item.stats);
/* 1943 */         DBStatBonusRace.insertItem(conn, item.itemID, item.statBonusRaces);
/* 1944 */         DBSkillBonus.insertItem(conn, item.itemID, item.bonuses);
/* 1945 */         DBSkillModifier.insertItem(conn, item.itemID, item.skillModifiers);
/*      */         
/* 1947 */         conn.commit();
/*      */       }
/* 1949 */       catch (SQLException ex) {
/* 1950 */         conn.rollback();
/*      */         
/* 1952 */         Object[] args = { record.getFileName(), "GD_ITEM" };
/* 1953 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*      */         
/* 1955 */         GDMsgLogger.addLowError(msg);
/* 1956 */         GDMsgLogger.addLowError(ex);
/*      */       } finally {
/*      */         
/* 1959 */         conn.setAutoCommit(auto);
/*      */       }
/*      */     
/* 1962 */     } catch (SQLException ex) {
/* 1963 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static DBItem get(String itemID) {
/* 1968 */     DBItem item = null;
/*      */     
/* 1970 */     item = hashBuffer.get(itemID);
/*      */     
/* 1972 */     if (item == null)
/*      */     {
/* 1974 */       item = getDB(itemID);
/*      */     }
/*      */     
/* 1977 */     return item;
/*      */   }
/*      */   
/*      */   private static DBItem getDB(String itemID) {
/* 1981 */     DBItem item = null;
/*      */     
/* 1983 */     String command = "SELECT * FROM GD_ITEM WHERE ITEM_ID = ?";
/*      */     
/* 1985 */     try(Connection conn = GDDBData.getConnection(); 
/* 1986 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 1987 */       ps.setString(1, itemID);
/*      */       
/* 1989 */       try (ResultSet rs = ps.executeQuery()) {
/* 1990 */         List<DBItem> list = wrap(rs);
/*      */         
/* 1992 */         if (list.isEmpty()) {
/* 1993 */           item = null;
/*      */         } else {
/* 1995 */           item = list.get(0);
/*      */         } 
/*      */         
/* 1998 */         conn.commit();
/*      */       }
/* 2000 */       catch (SQLException ex) {
/* 2001 */         throw ex;
/*      */       }
/*      */     
/* 2004 */     } catch (SQLException ex) {
/* 2005 */       Object[] args = { itemID, "GD_ITEM" };
/* 2006 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2008 */       GDMsgLogger.addError(msg);
/* 2009 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2012 */     return item;
/*      */   }
/*      */   
/*      */   private static SetInfo getSetInfo(String itemID) {
/* 2016 */     SetInfo info = null;
/*      */     
/* 2018 */     String command = "SELECT ITEM_ID, RARITY, REQ_LEVEL FROM GD_ITEM WHERE ITEM_ID = ?";
/*      */     
/* 2020 */     try(Connection conn = GDDBData.getConnection(); 
/* 2021 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 2022 */       ps.setString(1, itemID);
/*      */       
/* 2024 */       try (ResultSet rs = ps.executeQuery()) {
/* 2025 */         List<SetInfo> list = wrapSetInfo(rs);
/*      */         
/* 2027 */         if (list.isEmpty()) {
/* 2028 */           info = null;
/*      */         } else {
/* 2030 */           info = list.get(0);
/*      */         } 
/*      */         
/* 2033 */         conn.commit();
/*      */       }
/* 2035 */       catch (SQLException ex) {
/* 2036 */         throw ex;
/*      */       }
/*      */     
/* 2039 */     } catch (SQLException ex) {
/* 2040 */       Object[] args = { itemID, "GD_ITEM" };
/* 2041 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2043 */       GDMsgLogger.addError(msg);
/* 2044 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2047 */     return info;
/*      */   }
/*      */   
/*      */   public static List<DBItem> getByItemIDs(List<String> itemIDs) {
/* 2051 */     List<DBItem> list = new LinkedList<>();
/*      */     
/* 2053 */     for (String itemID : itemIDs) {
/* 2054 */       DBItem item = get(itemID);
/* 2055 */       list.add(item);
/*      */     } 
/*      */     
/* 2058 */     return list;
/*      */   }
/*      */   
/*      */   public static List<GDItem> getGDItemsByItemIDs(List<String> itemIDs) {
/* 2062 */     List<GDItem> list = new LinkedList<>();
/*      */     
/* 2064 */     for (String itemID : itemIDs) {
/* 2065 */       DBItem dbi = get(itemID);
/*      */       
/* 2067 */       if (dbi != null) {
/* 2068 */         list.add(new GDItem(dbi));
/*      */       }
/*      */     } 
/*      */     
/* 2072 */     return list;
/*      */   }
/*      */   
/*      */   public static List<SetInfo> getSetInfoByItemIDs(List<String> itemIDs) {
/* 2076 */     List<SetInfo> list = new LinkedList<>();
/*      */     
/* 2078 */     for (String itemID : itemIDs) {
/* 2079 */       SetInfo info = getSetInfo(itemID);
/* 2080 */       list.add(info);
/*      */     } 
/*      */     
/* 2083 */     return list;
/*      */   }
/*      */   
/*      */   public static List<DBItem> getAll() {
/* 2087 */     List<DBItem> list = new LinkedList<>();
/*      */     
/* 2089 */     String command = "SELECT * FROM GD_ITEM";
/*      */     
/* 2091 */     try(Connection conn = GDDBData.getConnection(); 
/* 2092 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*      */       
/* 2094 */       try (ResultSet rs = ps.executeQuery()) {
/* 2095 */         list = wrap(rs);
/*      */         
/* 2097 */         conn.commit();
/*      */       }
/* 2099 */       catch (SQLException ex) {
/* 2100 */         throw ex;
/*      */       }
/*      */     
/* 2103 */     } catch (SQLException ex) {
/* 2104 */       Object[] args = { "All", "GD_ITEM" };
/* 2105 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2107 */       GDMsgLogger.addError(msg);
/* 2108 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2111 */     return list;
/*      */   }
/*      */   
/*      */   public static List<DBItem> getAllUniques() {
/* 2115 */     List<DBItem> list = new LinkedList<>();
/*      */     
/* 2117 */     String command = "SELECT * FROM GD_ITEM WHERE (RARITY = 'Epic' OR RARITY = 'Legendary') AND (ITEM_CLASS LIKE 'Armor%' OR ITEM_CLASS LIKE 'Weapon%')";
/*      */ 
/*      */ 
/*      */     
/* 2121 */     try(Connection conn = GDDBData.getConnection(); 
/* 2122 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*      */       
/* 2124 */       try (ResultSet rs = ps.executeQuery()) {
/* 2125 */         list = wrap(rs);
/*      */         
/* 2127 */         conn.commit();
/*      */       }
/* 2129 */       catch (SQLException ex) {
/* 2130 */         throw ex;
/*      */       }
/*      */     
/* 2133 */     } catch (SQLException ex) {
/* 2134 */       Object[] args = { "Uniques", "GD_ITEM" };
/* 2135 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2137 */       GDMsgLogger.addError(msg);
/* 2138 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2141 */     return list;
/*      */   }
/*      */   
/*      */   public static List<DBItem> getAllLegendaries() {
/* 2145 */     List<DBItem> list = new LinkedList<>();
/*      */     
/* 2147 */     String command = "SELECT * FROM GD_ITEM WHERE RARITY = 'Legendary' AND (ITEM_CLASS LIKE 'Armor%' OR ITEM_CLASS LIKE 'Weapon%')";
/*      */ 
/*      */ 
/*      */     
/* 2151 */     try(Connection conn = GDDBData.getConnection(); 
/* 2152 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*      */       
/* 2154 */       try (ResultSet rs = ps.executeQuery()) {
/* 2155 */         list = wrap(rs);
/*      */         
/* 2157 */         conn.commit();
/*      */       }
/* 2159 */       catch (SQLException ex) {
/* 2160 */         throw ex;
/*      */       }
/*      */     
/* 2163 */     } catch (SQLException ex) {
/* 2164 */       Object[] args = { "Legendaries", "GD_ITEM" };
/* 2165 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2167 */       GDMsgLogger.addError(msg);
/* 2168 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2171 */     return list;
/*      */   }
/*      */   
/*      */   public static List<DBItem> getAllBlueprints() {
/* 2175 */     List<DBItem> list = new LinkedList<>();
/*      */     
/* 2177 */     String command = "SELECT * FROM GD_ITEM WHERE (RARITY = 'Epic' OR RARITY = 'Legendary') AND ITEM_CLASS = 'ItemArtifactFormula' AND (ITEM_ID NOT LIKE '%/craft_random%' AND  ITEM_ID NOT LIKE '%test%' AND  ITEM_ID NOT LIKE 'records/mccm/%' AND  ITEM_ID NOT LIKE 'records/apoc/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/cat/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/d2/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/d3/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/doh/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/ncff/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/tq/items/crafting/blueprints/%')";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2192 */     try(Connection conn = GDDBData.getConnection(); 
/* 2193 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*      */       
/* 2195 */       try (ResultSet rs = ps.executeQuery()) {
/* 2196 */         list = wrap(rs);
/*      */         
/* 2198 */         conn.commit();
/*      */       }
/* 2200 */       catch (SQLException ex) {
/* 2201 */         throw ex;
/*      */       }
/*      */     
/* 2204 */     } catch (SQLException ex) {
/* 2205 */       Object[] args = { "Blueprints", "GD_ITEM" };
/* 2206 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2208 */       GDMsgLogger.addError(msg);
/* 2209 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2212 */     return list;
/*      */   }
/*      */   
/*      */   public static List<DBItem> getAllLegendaryBlueprints() {
/* 2216 */     List<DBItem> list = new LinkedList<>();
/*      */     
/* 2218 */     String command = "SELECT * FROM GD_ITEM WHERE RARITY = 'Legendary' AND ITEM_CLASS = 'ItemArtifactFormula' AND (ITEM_ID NOT LIKE '%/craft_random%' AND  ITEM_ID NOT LIKE '%test%' AND  ITEM_ID NOT LIKE 'records/mccm/%' AND  ITEM_ID NOT LIKE 'records/apoc/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/cat/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/d2/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/d3/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/doh/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/ncff/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/tq/items/crafting/blueprints/%')";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2233 */     try(Connection conn = GDDBData.getConnection(); 
/* 2234 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*      */       
/* 2236 */       try (ResultSet rs = ps.executeQuery()) {
/* 2237 */         list = wrap(rs);
/*      */         
/* 2239 */         conn.commit();
/*      */       }
/* 2241 */       catch (SQLException ex) {
/* 2242 */         throw ex;
/*      */       }
/*      */     
/* 2245 */     } catch (SQLException ex) {
/* 2246 */       Object[] args = { "Legendary Blueprints", "GD_ITEM" };
/* 2247 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2249 */       GDMsgLogger.addError(msg);
/* 2250 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2253 */     return list;
/*      */   }
/*      */   
/*      */   public static List<DBItem> getBlueprints() {
/* 2257 */     List<DBItem> list = new LinkedList<>();
/*      */     
/* 2259 */     String command = "SELECT * FROM GD_ITEM WHERE ITEM_CLASS = 'ItemArtifactFormula' AND (ITEM_ID NOT LIKE '%/craft_random%' AND  ITEM_ID NOT LIKE '%test%' AND  ITEM_ID NOT LIKE 'records/mccm/%' AND  ITEM_ID NOT LIKE 'records/apoc/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/cat/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/d2/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/d3/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/doh/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/ncff/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/tq/items/crafting/blueprints/%')";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2273 */     try(Connection conn = GDDBData.getConnection(); 
/* 2274 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*      */       
/* 2276 */       try (ResultSet rs = ps.executeQuery()) {
/* 2277 */         list = wrap(rs);
/*      */         
/* 2279 */         conn.commit();
/*      */       }
/* 2281 */       catch (SQLException ex) {
/* 2282 */         throw ex;
/*      */       }
/*      */     
/* 2285 */     } catch (SQLException ex) {
/* 2286 */       Object[] args = { "Blueprints", "GD_ITEM" };
/* 2287 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2289 */       GDMsgLogger.addError(msg);
/* 2290 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2293 */     return list;
/*      */   }
/*      */   
/*      */   private static List<DBItem> getByItemCriteria(SelectionCriteria criteria) {
/* 2297 */     List<String> itemsAll = new LinkedList<>();
/* 2298 */     List<String> itemIDs = null;
/* 2299 */     List<String> itemSets = null;
/*      */     
/* 2301 */     itemIDs = ItemIDItemCombination.getItemIDs(criteria, CriteriaCombination.Soulbound.INCLUDED, CriteriaCombination.SC_HC.ALL, false, null);
/* 2302 */     itemSets = ItemIDItemSetCombination.getItemIDs(criteria, CriteriaCombination.Soulbound.INCLUDED, CriteriaCombination.SC_HC.ALL, false, null);
/*      */     
/* 2304 */     mergeItemIDs(itemsAll, itemIDs);
/* 2305 */     mergeItemIDs(itemsAll, itemSets);
/*      */     
/* 2307 */     List<DBItem> listAll = getByItemIDs(itemsAll);
/*      */     
/* 2309 */     return listAll;
/*      */   }
/*      */   
/*      */   public static List<DBItem> getByCriteria(SelectionCriteria criteria) {
/* 2313 */     if (criteria.itemIDs != null && !criteria.itemIDs.isEmpty()) {
/* 2314 */       return getByItemIDs(criteria.itemIDs);
/*      */     }
/*      */     
/* 2317 */     return getByItemCriteria(criteria);
/*      */   }
/*      */   
/*      */   private static void mergeItemIDs(List<String> listAll, List<String> list) {
/* 2321 */     if (list == null)
/*      */       return; 
/* 2323 */     for (String s : list) {
/* 2324 */       boolean found = false;
/* 2325 */       for (String sRec : listAll) {
/* 2326 */         if (sRec.equals(s)) {
/* 2327 */           found = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */       
/* 2333 */       if (!found) listAll.add(s); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void addSingleCriteriaCombo(List<DBItem> listAll, PreparedStatement ps, String command, ItemCriteriaCombination combo) {
/* 2338 */     List<DBItem> list = getBySingleCriteriaCombo(ps, command, combo);
/*      */     
/* 2340 */     if (list != null && 
/* 2341 */       !list.isEmpty()) listAll.addAll(list);
/*      */   
/*      */   }
/*      */   
/*      */   private static List<DBItem> getBySingleCriteriaCombo(PreparedStatement ps, String command, ItemCriteriaCombination combo) {
/* 2346 */     List<DBItem> list = new LinkedList<>();
/*      */     
/*      */     try {
/* 2349 */       combo.fillItemStatement(ps);
/*      */       
/* 2351 */       try (ResultSet rs = ps.executeQuery()) {
/* 2352 */         list = wrap(rs);
/*      */       }
/* 2354 */       catch (SQLException ex) {
/* 2355 */         throw ex;
/*      */       }
/*      */     
/* 2358 */     } catch (SQLException ex) {
/* 2359 */       Object[] args = { command, combo.determineItemParameters() };
/* 2360 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_SELECT_FAILED", args);
/*      */       
/* 2362 */       GDMsgLogger.addError(msg);
/*      */     } 
/*      */     
/* 2365 */     return list;
/*      */   }
/*      */   
/*      */   private static void addItemIDsSingleCriteriaCombo(List<String> listAll, PreparedStatement ps, String command, ItemCriteriaCombination combo) {
/* 2369 */     List<String> list = getItemIDsBySingleCriteriaCombo(ps, command, combo);
/*      */     
/* 2371 */     if (list != null && 
/* 2372 */       !list.isEmpty()) listAll.addAll(list);
/*      */   
/*      */   }
/*      */   
/*      */   private static List<String> getItemIDsBySingleCriteriaCombo(PreparedStatement ps, String command, ItemCriteriaCombination combo) {
/* 2377 */     List<String> list = new LinkedList<>();
/*      */     
/*      */     try {
/* 2380 */       combo.fillItemStatement(ps);
/*      */       
/* 2382 */       try (ResultSet rs = ps.executeQuery()) {
/* 2383 */         list = AbstractItemCombination.wrapString(rs, 1);
/*      */       }
/* 2385 */       catch (SQLException ex) {
/* 2386 */         throw ex;
/*      */       }
/*      */     
/* 2389 */     } catch (SQLException ex) {
/* 2390 */       Object[] args = { command, combo.determineItemParameters() };
/* 2391 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_SELECT_FAILED", args);
/*      */       
/* 2393 */       GDMsgLogger.addError(msg);
/*      */     } 
/*      */     
/* 2396 */     return list;
/*      */   }
/*      */   
/*      */   private static void addSingleDamageCombo(List<String> listAll, PreparedStatement ps, String command, StatCriteriaCombination scc) throws SQLException {
/* 2400 */     int nextPos = scc.fillItemIDStatement(ps);
/*      */     
/* 2402 */     if (nextPos == -1)
/*      */       return; 
/* 2404 */     for (SelectionCriteria.StatInfo info : scc.getStatInfoList()) {
/* 2405 */       for (String statType : info.statTypes) {
/* 2406 */         ps.setString(nextPos, statType);
/*      */         
/* 2408 */         try (ResultSet rs = ps.executeQuery()) {
/* 2409 */           List<String> ids = AbstractItemCombination.wrapString(rs, 1);
/*      */           
/* 2411 */           for (String id : ids) {
/* 2412 */             boolean found = false;
/* 2413 */             for (String s : listAll) {
/* 2414 */               if (s.equals(id)) {
/* 2415 */                 found = true;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */             
/* 2421 */             if (!found) listAll.add(id);
/*      */           
/*      */           } 
/* 2424 */         } catch (SQLException ex) {
/* 2425 */           throw ex;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void createBonusRaceStats(DBStat stat) {
/* 2432 */     if (stat == null)
/* 2433 */       return;  if (this.statBonusRaces == null)
/* 2434 */       return;  if (this.statBonusRaces.isEmpty())
/*      */       return; 
/* 2436 */     this.stats.remove(stat);
/*      */     
/* 2438 */     List<DBStat> list = DBStat.createStatsFromRaceBonusList(stat, this.statBonusRaces);
/* 2439 */     this.stats.addAll(list);
/*      */   }
/*      */   
/*      */   private static List<DBItem> wrap(ResultSet rs) throws SQLException {
/* 2443 */     LinkedList<DBItem> list = new LinkedList<>();
/* 2444 */     Blob blob = null;
/*      */     
/* 2446 */     while (rs.next()) {
/* 2447 */       DBItem item = new DBItem();
/*      */       
/* 2449 */       item.itemID = rs.getString(1);
/*      */       
/* 2451 */       DBItem buff = hashBuffer.get(item.itemID);
/* 2452 */       if (buff != null) {
/* 2453 */         list.add(buff);
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 2458 */       item.itemClass = rs.getString(2);
/* 2459 */       item.armorClass = rs.getString(3);
/* 2460 */       item.artifactClass = rs.getString(4);
/*      */       
/* 2462 */       item.meshID = rs.getString(5);
/* 2463 */       item.baseTextureID = rs.getString(6);
/* 2464 */       item.bumpTextureID = rs.getString(7);
/* 2465 */       item.glowTextureID = rs.getString(8);
/* 2466 */       item.shaderID = rs.getString(9);
/* 2467 */       item.bitmapID = rs.getString(10);
/* 2468 */       blob = rs.getBlob(11);
/* 2469 */       if (blob == null) {
/* 2470 */         item.bitmap = null;
/*      */       } else {
/* 2472 */         item.bitmap = blob.getBytes(1L, (int)blob.length());
/*      */       } 
/*      */       
/* 2475 */       if (item.bitmap != null) {
/*      */         try {
/* 2477 */           item.image = DDSLoader.getImage(item.bitmap);
/*      */         }
/* 2479 */         catch (GDParseException ex) {
/* 2480 */           Object[] args = { item.itemID };
/* 2481 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_BITMAP_DECODE_FAILED", args);
/*      */           
/* 2483 */           GDMsgLogger.addError(msg);
/*      */           
/* 2485 */           item.image = null;
/*      */         } 
/*      */         
/* 2488 */         if (item.image != null) {
/* 2489 */           item.overlayImage = DDSLoader.getScaledImage(item.image, 16, 16);
/*      */         }
/*      */       } 
/*      */       
/* 2493 */       item.shardBitmapID = rs.getString(12);
/* 2494 */       blob = rs.getBlob(13);
/* 2495 */       if (blob == null) {
/* 2496 */         item.shardBitmap = null;
/*      */       } else {
/* 2498 */         item.shardBitmap = blob.getBytes(1L, (int)blob.length());
/*      */       } 
/*      */       
/* 2501 */       if (item.shardBitmap != null) {
/*      */         try {
/* 2503 */           item.shardImage = DDSLoader.getImage(item.shardBitmap);
/*      */         }
/* 2505 */         catch (GDParseException ex) {
/* 2506 */           Object[] args = { item.itemID };
/* 2507 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_BITMAP_DECODE_FAILED", args);
/*      */           
/* 2509 */           GDMsgLogger.addError(msg);
/*      */           
/* 2511 */           item.shardImage = null;
/*      */         } 
/*      */         
/* 2514 */         if (item.shardImage != null) {
/* 2515 */           item.overlayShard = DDSLoader.getScaledImage(item.shardImage, 16, 16);
/*      */         }
/*      */       } 
/*      */       
/* 2519 */       item.genderCode = rs.getInt(14);
/* 2520 */       item.name = rs.getString(15);
/* 2521 */       item.description = rs.getString(16);
/* 2522 */       item.qualityTag = rs.getString(17);
/* 2523 */       item.qualityText = rs.getString(18);
/* 2524 */       item.styleTag = rs.getString(19);
/* 2525 */       item.styleText = rs.getString(20);
/* 2526 */       item.nameFull = rs.getString(21);
/* 2527 */       item.rarity = rs.getString(22);
/* 2528 */       item.setID = rs.getString(23);
/* 2529 */       item.setName = rs.getString(24);
/* 2530 */       item.bonusAffixSetID = rs.getString(25);
/* 2531 */       item.itemSkillID = rs.getString(26);
/* 2532 */       item.itemSkillLevel = rs.getInt(27);
/* 2533 */       item.petBonusSkillID = rs.getString(28);
/* 2534 */       item.controllerID = rs.getString(29);
/* 2535 */       item.costFormulaSetID = rs.getString(30);
/* 2536 */       item.convertIn = rs.getString(31);
/* 2537 */       item.convertOut = rs.getString(32);
/* 2538 */       item.convertIn2 = rs.getString(33);
/* 2539 */       item.convertOut2 = rs.getString(34);
/* 2540 */       item.soulbound = rs.getBoolean(35);
/* 2541 */       item.hidePrefix = rs.getBoolean(36);
/* 2542 */       item.hideSuffix = rs.getBoolean(37);
/* 2543 */       item.questItem = rs.getBoolean(38);
/* 2544 */       item.cannotPickup = rs.getBoolean(39);
/* 2545 */       item.enemyOnly = rs.getBoolean(40);
/* 2546 */       item.itemLevel = rs.getInt(41);
/* 2547 */       item.reqLevel = rs.getInt(42);
/* 2548 */       item.reqDex = rs.getInt(43);
/* 2549 */       item.reqInt = rs.getInt(44);
/* 2550 */       item.reqStr = rs.getInt(45);
/* 2551 */       item.offensiveChance = rs.getInt(46);
/* 2552 */       item.retaliationChance = rs.getInt(47);
/* 2553 */       item.plusAllSkills = rs.getInt(48);
/* 2554 */       item.componentPieces = rs.getInt(49);
/* 2555 */       item.maxStackSize = rs.getInt(50);
/* 2556 */       item.scalePercent = rs.getInt(51);
/* 2557 */       item.slots.slotAxe1H = rs.getBoolean(52);
/* 2558 */       item.slots.slotAxe2H = rs.getBoolean(53);
/* 2559 */       item.slots.slotDagger1H = rs.getBoolean(54);
/* 2560 */       item.slots.slotMace1H = rs.getBoolean(55);
/* 2561 */       item.slots.slotMace2H = rs.getBoolean(56);
/* 2562 */       item.slots.slotScepter1H = rs.getBoolean(57);
/* 2563 */       item.slots.slotSpear1H = rs.getBoolean(58);
/* 2564 */       item.slots.slotStaff2H = rs.getBoolean(59);
/* 2565 */       item.slots.slotSword1H = rs.getBoolean(60);
/* 2566 */       item.slots.slotSword2H = rs.getBoolean(61);
/* 2567 */       item.slots.slotRanged1H = rs.getBoolean(62);
/* 2568 */       item.slots.slotRanged2H = rs.getBoolean(63);
/* 2569 */       item.slots.slotShield = rs.getBoolean(64);
/* 2570 */       item.slots.slotOffhand = rs.getBoolean(65);
/* 2571 */       item.slots.slotAmulet = rs.getBoolean(66);
/* 2572 */       item.slots.slotBelt = rs.getBoolean(67);
/* 2573 */       item.slots.slotMedal = rs.getBoolean(68);
/* 2574 */       item.slots.slotRing = rs.getBoolean(69);
/* 2575 */       item.slots.slotHead = rs.getBoolean(70);
/* 2576 */       item.slots.slotShoulders = rs.getBoolean(71);
/* 2577 */       item.slots.slotChest = rs.getBoolean(72);
/* 2578 */       item.slots.slotHands = rs.getBoolean(73);
/* 2579 */       item.slots.slotLegs = rs.getBoolean(74);
/* 2580 */       item.slots.slotFeet = rs.getBoolean(75);
/*      */       
/* 2582 */       item.stats = DBStat.getItem(item.itemID);
/* 2583 */       Collections.sort(item.stats);
/*      */       
/* 2585 */       DBStat stat = DBStat.getByType(item.stats, "racialBonusPercentDamage", 1);
/* 2586 */       if (stat != null) {
/* 2587 */         item.statBonusRaces = DBStatBonusRace.getItem(item.itemID);
/* 2588 */         item.createBonusRaceStats(stat);
/*      */       } 
/*      */       
/* 2591 */       DBStat.applyAttributeScale(item.stats, item.scalePercent);
/*      */       
/* 2593 */       item.bonuses = DBSkillBonus.getItem(item.itemID);
/* 2594 */       item.skillModifiers = DBSkillModifier.getItem(item.itemID);
/*      */       
/* 2596 */       if (item.setID != null) {
/* 2597 */         item.dbItemSet = DBItemSet.get(item.setID);
/*      */       }
/*      */       
/* 2600 */       if (item.bonusAffixSetID != null) {
/* 2601 */         item.bonusAffixSet = DBAffixSet.get(item.bonusAffixSetID);
/*      */       }
/*      */       
/* 2604 */       if (item.itemSkillID != null) {
/* 2605 */         item.dbItemSkill = DBSkill.get(item.itemSkillID);
/*      */       }
/*      */       
/* 2608 */       if (item.petBonusSkillID != null) {
/* 2609 */         item.dbPetBonusSkill = DBSkill.get(item.petBonusSkillID);
/*      */       }
/*      */       
/* 2612 */       if (item.controllerID != null) {
/* 2613 */         item.dbController = DBController.get(item.controllerID);
/*      */       }
/*      */ 
/*      */       
/* 2617 */       if (item.costFormulaSetID == null && 
/* 2618 */         ItemClass.usesCostFormula(item.itemClass)) {
/* 2619 */         item.costFormulaSetID = "records/game/itemcostformulas.dbr";
/*      */       }
/*      */ 
/*      */       
/* 2623 */       if (item.costFormulaSetID != null) {
/* 2624 */         item.dbFormulaSet = DBFormulaSet.get(item.costFormulaSetID);
/*      */         
/* 2626 */         if (item.dbFormulaSet != null) {
/* 2627 */           item.determineStatRequirements();
/*      */         }
/*      */       } 
/*      */       
/* 2631 */       item.craftable = DBLootTableItemAlloc.isCraftableItemID(item.itemID);
/* 2632 */       if (!item.craftable) {
/* 2633 */         item.craftable = DBItemCraft.isCraftableItemID(item.itemID);
/*      */       }
/*      */       
/* 2636 */       item.faction = DBFaction.getFactionTextByItemID(item.itemID);
/*      */       
/* 2638 */       list.add(item);
/* 2639 */       hashBuffer.put(item.itemID, item);
/*      */     } 
/*      */     
/* 2642 */     return list;
/*      */   }
/*      */   
/*      */   private static List<SetInfo> wrapSetInfo(ResultSet rs) throws SQLException {
/* 2646 */     LinkedList<SetInfo> list = new LinkedList<>();
/*      */     
/* 2648 */     while (rs.next()) {
/* 2649 */       SetInfo info = new SetInfo();
/*      */       
/* 2651 */       info.itemID = rs.getString(1);
/* 2652 */       info.rarity = rs.getString(2);
/* 2653 */       info.level = rs.getInt(3);
/*      */       
/* 2655 */       list.add(info);
/*      */     } 
/*      */     
/* 2658 */     return list;
/*      */   }
/*      */   
/*      */   public static List<ImageInfo> getImageInfos(String id) {
/* 2662 */     List<ImageInfo> list = new LinkedList<>();
/* 2663 */     Blob blob = null;
/*      */     
/* 2665 */     String command = "SELECT ITEM_ID, BITMAP_ID, BITMAP, SHARD_BITMAP_ID, SHARD_BITMAP FROM GD_ITEM WHERE ((BITMAP_ID LIKE '" + id + "%' AND BITMAP IS NULL) OR (SHARD_BITMAP_ID LIKE '" + id + "%' AND SHARD_BITMAP IS NULL))";
/*      */ 
/*      */ 
/*      */     
/* 2669 */     try(Connection conn = GDDBData.getConnection(); 
/* 2670 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 2671 */       try (ResultSet rs = ps.executeQuery()) {
/* 2672 */         while (rs.next()) {
/* 2673 */           ImageInfo info = new ImageInfo();
/*      */           
/* 2675 */           info.itemID = rs.getString(1);
/*      */           
/* 2677 */           info.bitmapID = rs.getString(2);
/* 2678 */           blob = rs.getBlob(3);
/* 2679 */           if (blob == null) {
/* 2680 */             info.bitmap = null;
/*      */           } else {
/* 2682 */             info.bitmap = blob.getBytes(1L, (int)blob.length());
/*      */           } 
/*      */           
/* 2685 */           info.shardBitmapID = rs.getString(4);
/* 2686 */           blob = rs.getBlob(5);
/* 2687 */           if (blob == null) {
/* 2688 */             info.shardBitmap = null;
/*      */           } else {
/* 2690 */             info.shardBitmap = blob.getBytes(1L, (int)blob.length());
/*      */           } 
/*      */           
/* 2693 */           list.add(info);
/*      */         } 
/*      */         
/* 2696 */         conn.commit();
/*      */       }
/* 2698 */       catch (Exception ex) {
/* 2699 */         throw ex;
/*      */       }
/*      */     
/* 2702 */     } catch (Exception ex) {
/* 2703 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2706 */     return list;
/*      */   }
/*      */   
/*      */   public static void deleteNoImageItems() {
/* 2710 */     String command = "DELETE FROM GD_ITEM WHERE BITMAP IS NULL";
/*      */     
/* 2712 */     try(Connection conn = GDDBData.getConnection(); 
/* 2713 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 2714 */       ps.execute();
/*      */       
/* 2716 */       conn.commit();
/*      */     }
/* 2718 */     catch (Exception ex) {
/* 2719 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void updateImageInfo(List<ImageInfo> list) {
/* 2724 */     if (list == null)
/*      */       return; 
/* 2726 */     String command = "UPDATE GD_ITEM SET BITMAP = ?, SHARD_BITMAP = ? WHERE ITEM_ID = ?";
/*      */ 
/*      */     
/* 2729 */     try (Connection conn = GDDBData.getConnection()) {
/* 2730 */       boolean auto = conn.getAutoCommit();
/* 2731 */       conn.setAutoCommit(false);
/*      */       
/* 2733 */       for (ImageInfo info : list) {
/* 2734 */         try (PreparedStatement ps = conn.prepareStatement(command)) {
/* 2735 */           if (info.bitmap != null || info.shardBitmap != null)
/*      */           {
/*      */             
/* 2738 */             if (info.bitmap == null) {
/* 2739 */               ps.setBlob(1, (Blob)null);
/*      */             } else {
/* 2741 */               ps.setBlob(1, new ByteArrayInputStream(info.bitmap));
/*      */             } 
/*      */             
/* 2744 */             if (info.shardBitmap == null) {
/* 2745 */               ps.setBlob(2, (Blob)null);
/*      */             } else {
/* 2747 */               ps.setBlob(2, new ByteArrayInputStream(info.shardBitmap));
/*      */             } 
/*      */             
/* 2750 */             ps.setString(3, info.itemID);
/*      */             
/* 2752 */             ps.executeUpdate();
/* 2753 */             ps.close();
/*      */             
/* 2755 */             conn.commit();
/*      */           }
/*      */         
/* 2758 */         } catch (SQLException ex) {
/* 2759 */           conn.rollback();
/*      */           
/* 2761 */           Object[] args = { info.itemID };
/* 2762 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_IN_ITEM_IMAGE_SIZE", args);
/*      */           
/* 2764 */           GDMsgLogger.addWarning(msg);
/*      */           
/* 2766 */           GDMsgLogger.addWarning(ex);
/*      */         }
/*      */       
/*      */       } 
/* 2770 */     } catch (SQLException ex) {
/* 2771 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   public DBItem() {}
/*      */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */