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
/*      */   public List<DBAffix> getBonusAffixList() {
/*  656 */     List<DBAffix> list = new LinkedList<>();
/*      */     
/*  658 */     if (this.bonusAffixSet == null) return list;
/*      */     
/*  660 */     return this.bonusAffixSet.getAffixList();
/*      */   }
/*      */   
/*      */   public String getItemSkillID() {
/*  664 */     return this.itemSkillID;
/*      */   }
/*      */   
/*      */   public int getItemSkillLevel() {
/*  668 */     return this.itemSkillLevel;
/*      */   }
/*      */   
/*      */   public String getPetBonusSkillID() {
/*  672 */     return this.petBonusSkillID;
/*      */   }
/*      */   
/*      */   public String getControllerID() {
/*  676 */     return this.controllerID;
/*      */   }
/*      */   
/*      */   public String getControllerTriggerType() {
/*  680 */     if (this.dbController == null) return null;
/*      */     
/*  682 */     return this.dbController.getTriggerType();
/*      */   }
/*      */   
/*      */   public int getControllerTriggerChance() {
/*  686 */     if (this.dbController == null) return 0;
/*      */     
/*  688 */     return this.dbController.getTriggerChance();
/*      */   }
/*      */   
/*      */   public String getCostFormulaSetID() {
/*  692 */     return this.costFormulaSetID;
/*      */   }
/*      */   
/*      */   public boolean isSoulbound() {
/*  696 */     return this.soulbound;
/*      */   }
/*      */   
/*      */   public boolean isHidePrefix() {
/*  700 */     return this.hidePrefix;
/*      */   }
/*      */   
/*      */   public boolean isHideSuffix() {
/*  704 */     return this.hideSuffix;
/*      */   }
/*      */   
/*      */   public boolean isQuestItem() {
/*  708 */     return this.questItem;
/*      */   }
/*      */   
/*      */   public boolean isCraftable() {
/*  712 */     return this.craftable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemLevel() {
/*  720 */     return this.itemLevel;
/*      */   }
/*      */   
/*      */   public boolean isEnemyOnly() {
/*  724 */     return this.enemyOnly;
/*      */   }
/*      */   
/*      */   public int getRequiredlevel() {
/*  728 */     return this.reqLevel;
/*      */   }
/*      */   
/*      */   public int getRequiredCunning() {
/*  732 */     return this.reqDex;
/*      */   }
/*      */   
/*      */   public int getRequiredPhysique() {
/*  736 */     return this.reqStr;
/*      */   }
/*      */   
/*      */   public int getRequiredSpirit() {
/*  740 */     return this.reqInt;
/*      */   }
/*      */   
/*      */   public int getOffensiveChance() {
/*  744 */     return this.offensiveChance;
/*      */   }
/*      */   
/*      */   public int getRetaliationChance() {
/*  748 */     return this.retaliationChance;
/*      */   }
/*      */   
/*      */   public String getConvertIn() {
/*  752 */     return this.convertIn;
/*      */   }
/*      */   
/*      */   public String getConvertOut() {
/*  756 */     return this.convertOut;
/*      */   }
/*      */   
/*      */   public String getConvertIn2() {
/*  760 */     return this.convertIn2;
/*      */   }
/*      */   
/*      */   public String getConvertOut2() {
/*  764 */     return this.convertOut2;
/*      */   }
/*      */   
/*      */   public int getPlusAllSkills() {
/*  768 */     return this.plusAllSkills;
/*      */   }
/*      */   
/*      */   public int getComponentPieces() {
/*  772 */     return this.componentPieces;
/*      */   }
/*      */   
/*      */   public int getMaxStackSize() {
/*  776 */     return this.maxStackSize;
/*      */   }
/*      */   
/*      */   public ItemSlots getSlots() {
/*  780 */     return this.slots.clone();
/*      */   }
/*      */   
/*      */   public boolean usesSlots() {
/*  784 */     if (this.slots == null) return false;
/*      */     
/*  786 */     return this.slots.usesSlots();
/*      */   }
/*      */   
/*      */   public boolean isSlotAxe1H() {
/*  790 */     return this.slots.slotAxe1H;
/*      */   }
/*      */   
/*      */   public boolean isSlotAxe2H() {
/*  794 */     return this.slots.slotAxe2H;
/*      */   }
/*      */   
/*      */   public boolean isSlotDagger1H() {
/*  798 */     return this.slots.slotDagger1H;
/*      */   }
/*      */   
/*      */   public boolean isSlotMace1H() {
/*  802 */     return this.slots.slotMace1H;
/*      */   }
/*      */   
/*      */   public boolean isSlotMace2H() {
/*  806 */     return this.slots.slotMace2H;
/*      */   }
/*      */   
/*      */   public boolean isSlotScepter1H() {
/*  810 */     return this.slots.slotScepter1H;
/*      */   }
/*      */   
/*      */   public boolean isSlotSpear1H() {
/*  814 */     return this.slots.slotSpear1H;
/*      */   }
/*      */   
/*      */   public boolean isSlotStaff2H() {
/*  818 */     return this.slots.slotStaff2H;
/*      */   }
/*      */   
/*      */   public boolean isSlotSword1H() {
/*  822 */     return this.slots.slotSword1H;
/*      */   }
/*      */   
/*      */   public boolean isSlotSword2H() {
/*  826 */     return this.slots.slotSword2H;
/*      */   }
/*      */   
/*      */   public boolean isSlotRanged1H() {
/*  830 */     return this.slots.slotRanged1H;
/*      */   }
/*      */   
/*      */   public boolean isSlotRanged2H() {
/*  834 */     return this.slots.slotRanged2H;
/*      */   }
/*      */   
/*      */   public boolean isSlotShield() {
/*  838 */     return this.slots.slotShield;
/*      */   }
/*      */   
/*      */   public boolean isSlotOffhand() {
/*  842 */     return this.slots.slotOffhand;
/*      */   }
/*      */   
/*      */   public boolean isSlotAmulet() {
/*  846 */     return this.slots.slotAmulet;
/*      */   }
/*      */   
/*      */   public boolean isSlotBelt() {
/*  850 */     return this.slots.slotBelt;
/*      */   }
/*      */   
/*      */   public boolean isSlotMedal() {
/*  854 */     return this.slots.slotMedal;
/*      */   }
/*      */   
/*      */   public boolean isSlotRing() {
/*  858 */     return this.slots.slotRing;
/*      */   }
/*      */   
/*      */   public boolean isSlotHead() {
/*  862 */     return this.slots.slotHead;
/*      */   }
/*      */   
/*      */   public boolean isSlotShoulders() {
/*  866 */     return this.slots.slotShoulders;
/*      */   }
/*      */   
/*      */   public boolean isSlotChest() {
/*  870 */     return this.slots.slotChest;
/*      */   }
/*      */   
/*      */   public boolean isSlotHands() {
/*  874 */     return this.slots.slotHands;
/*      */   }
/*      */   
/*      */   public boolean isSlotLegs() {
/*  878 */     return this.slots.slotLegs;
/*      */   }
/*      */   
/*      */   public boolean isSlotFeet() {
/*  882 */     return this.slots.slotFeet;
/*      */   }
/*      */   
/*      */   public DBAffixSet getBonusAffixSet() {
/*  886 */     return this.bonusAffixSet;
/*      */   }
/*      */   
/*      */   public List<DBStat> getStatList() {
/*  890 */     return this.stats;
/*      */   }
/*      */   
/*      */   public List<DBSkillBonus> getSkillBonusList() {
/*  894 */     return this.bonuses;
/*      */   }
/*      */   
/*      */   public List<DBSkillModifier> getSkillModifierList() {
/*  898 */     return this.skillModifiers;
/*      */   }
/*      */   
/*      */   public DBSkill getItemSkill() {
/*  902 */     return this.dbItemSkill;
/*      */   }
/*      */   
/*      */   public DBController getItemSkillController() {
/*  906 */     return this.dbController;
/*      */   }
/*      */   
/*      */   public DBSkill getPetBonusSkill() {
/*  910 */     return this.dbPetBonusSkill;
/*      */   }
/*      */   
/*      */   public byte[] getBitmap() {
/*  914 */     return this.bitmap;
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
/*  925 */     return this.image;
/*      */   }
/*      */   
/*      */   public String getMeshID() {
/*  929 */     return this.meshID;
/*      */   }
/*      */   
/*      */   public String getBaseTextureID() {
/*  933 */     return this.baseTextureID;
/*      */   }
/*      */   
/*      */   public String getBumpTextureID() {
/*  937 */     return this.bumpTextureID;
/*      */   }
/*      */   
/*      */   public String getGlowTextureID() {
/*  941 */     return this.glowTextureID;
/*      */   }
/*      */   
/*      */   public String getShaderID() {
/*  945 */     return this.shaderID;
/*      */   }
/*      */   
/*      */   public String getImageID() {
/*  949 */     return this.bitmapID;
/*      */   }
/*      */   
/*      */   public BufferedImage getShardImage() {
/*  953 */     return this.shardImage;
/*      */   }
/*      */   
/*      */   public BufferedImage getOverlayImage() {
/*  957 */     return this.overlayImage;
/*      */   }
/*      */   
/*      */   public BufferedImage getOverlayShard() {
/*  961 */     return this.overlayShard;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DBFormulaSet.Result getStatRequirements(ParameterSet ps) {
/*  969 */     if (this.dbFormulaSet == null) return null;
/*      */     
/*  971 */     DBFormulaSet.Result result = this.dbFormulaSet.getResult(ps);
/*      */     
/*  973 */     if (result != null) {
/*      */       
/*  975 */       result.cunning = (int)(result.cunning + 0.5D);
/*  976 */       result.physique = (int)(result.physique + 0.5D);
/*  977 */       result.spirit = (int)(result.spirit + 0.5D);
/*      */     } 
/*      */     
/*  980 */     return result;
/*      */   }
/*      */   
/*      */   public void determineStatRequirements() {
/*  984 */     DBFormulaSet.Result result = getStatRequirements(this);
/*      */     
/*  986 */     if (result != null) {
/*  987 */       this.reqDex = (int)result.cunning;
/*  988 */       this.reqStr = (int)result.physique;
/*  989 */       this.reqInt = (int)result.spirit;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float getCharAttackSpeed() {
/*  995 */     DBStat stat = DBStat.getDBStat(this.stats, "characterAttackSpeed", 1);
/*      */     
/*  997 */     if (stat == null) return 0.0F;
/*      */     
/*  999 */     return stat.getStatMin();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDefenseAttrArmor() {
/* 1004 */     DBStat stat = DBStat.getDBStat(this.stats, "defensiveProtection", 1);
/*      */     
/* 1006 */     if (stat == null) return 0;
/*      */     
/* 1008 */     return (int)stat.getStatMin();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getShieldBlockDefense() {
/* 1013 */     DBStat stat = DBStat.getDBStat(this.stats, "defensiveBlock", 1);
/*      */     
/* 1015 */     if (stat == null) return 0;
/*      */     
/* 1017 */     return (int)stat.getStatMin();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getShieldBlockChance() {
/* 1022 */     DBStat stat = DBStat.getDBStat(this.stats, "defensiveBlock", 1);
/*      */     
/* 1024 */     if (stat == null) return 0;
/*      */     
/* 1026 */     return stat.getStatChance();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDamageAvgPierceRatio() {
/* 1031 */     DBStat stat = DBStat.getDBStat(this.stats, "offensivePierceRatio", 1);
/*      */     
/* 1033 */     if (stat == null) return 0;
/*      */     
/* 1035 */     int minVal = (int)stat.getStatMin();
/* 1036 */     int maxVal = (int)stat.getStatMax();
/*      */     
/* 1038 */     if (maxVal > 0) {
/* 1039 */       minVal += maxVal;
/* 1040 */       minVal /= 2;
/*      */     } 
/*      */     
/* 1043 */     return minVal;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDamageAvgBase() {
/* 1048 */     int total = 0;
/* 1049 */     int minVal = 0;
/* 1050 */     int maxVal = 0;
/*      */     
/* 1052 */     for (DBStat stat : this.stats) {
/* 1053 */       if (stat.getStatType().equals("offensiveBaseAether")) {
/* 1054 */         minVal = (int)stat.getStatMin();
/* 1055 */         maxVal = (int)stat.getStatMax();
/*      */         
/* 1057 */         if (maxVal > 0) {
/* 1058 */           minVal += maxVal;
/* 1059 */           minVal /= 2;
/*      */         } 
/*      */         
/* 1062 */         total += minVal;
/*      */       } 
/*      */       
/* 1065 */       if (stat.getStatType().equals("offensiveBaseChaos")) {
/* 1066 */         minVal = (int)stat.getStatMin();
/* 1067 */         maxVal = (int)stat.getStatMax();
/*      */         
/* 1069 */         if (maxVal > 0) {
/* 1070 */           minVal += maxVal;
/* 1071 */           minVal /= 2;
/*      */         } 
/*      */         
/* 1074 */         total += minVal;
/*      */       } 
/*      */       
/* 1077 */       if (stat.getStatType().equals("offensiveBaseCold")) {
/* 1078 */         minVal = (int)stat.getStatMin();
/* 1079 */         maxVal = (int)stat.getStatMax();
/*      */         
/* 1081 */         if (maxVal > 0) {
/* 1082 */           minVal += maxVal;
/* 1083 */           minVal /= 2;
/*      */         } 
/*      */         
/* 1086 */         total += minVal;
/*      */       } 
/*      */       
/* 1089 */       if (stat.getStatType().equals("offensiveBaseFire")) {
/* 1090 */         minVal = (int)stat.getStatMin();
/* 1091 */         maxVal = (int)stat.getStatMax();
/*      */         
/* 1093 */         if (maxVal > 0) {
/* 1094 */           minVal += maxVal;
/* 1095 */           minVal /= 2;
/*      */         } 
/*      */         
/* 1098 */         total += minVal;
/*      */       } 
/*      */       
/* 1101 */       if (stat.getStatType().equals("offensiveBaseLife")) {
/* 1102 */         minVal = (int)stat.getStatMin();
/* 1103 */         maxVal = (int)stat.getStatMax();
/*      */         
/* 1105 */         if (maxVal > 0) {
/* 1106 */           minVal += maxVal;
/* 1107 */           minVal /= 2;
/*      */         } 
/*      */         
/* 1110 */         total += minVal;
/*      */       } 
/*      */       
/* 1113 */       if (stat.getStatType().equals("offensiveBaseLightning")) {
/* 1114 */         minVal = (int)stat.getStatMin();
/* 1115 */         maxVal = (int)stat.getStatMax();
/*      */         
/* 1117 */         if (maxVal > 0) {
/* 1118 */           minVal += maxVal;
/* 1119 */           minVal /= 2;
/*      */         } 
/*      */         
/* 1122 */         total += minVal;
/*      */       } 
/*      */       
/* 1125 */       if (stat.getStatType().equals("offensivePhysical")) {
/* 1126 */         minVal = (int)stat.getStatMin();
/* 1127 */         maxVal = (int)stat.getStatMax();
/*      */         
/* 1129 */         if (maxVal > 0) {
/* 1130 */           minVal += maxVal;
/* 1131 */           minVal /= 2;
/*      */         } 
/*      */         
/* 1134 */         total += minVal;
/*      */       } 
/*      */       
/* 1137 */       if (stat.getStatType().equals("offensiveBasePoison")) {
/* 1138 */         minVal = (int)stat.getStatMin();
/* 1139 */         maxVal = (int)stat.getStatMax();
/*      */         
/* 1141 */         if (maxVal > 0) {
/* 1142 */           minVal += maxVal;
/* 1143 */           minVal /= 2;
/*      */         } 
/*      */         
/* 1146 */         total += minVal;
/*      */       } 
/*      */     } 
/*      */     
/* 1150 */     return total;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTotalAttCount() {
/* 1155 */     int total = 0;
/*      */     
/* 1157 */     for (DBStat stat : this.stats) {
/* 1158 */       if (stat.getStatMin() > 0.0F) total++; 
/* 1159 */       if (stat.getStatModifier() > 0) total++; 
/* 1160 */       if (stat.getDurationModifier() > 0) total++; 
/* 1161 */       if (stat.getMaxResist() > 0) total++;
/*      */     
/*      */     } 
/* 1164 */     if (this.statBonusRaces != null && !this.statBonusRaces.isEmpty()) total += this.statBonusRaces.size();
/*      */     
/* 1166 */     return total;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getItemPrefixCost() {
/* 1171 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getItemSuffixCost() {
/* 1176 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String appendNamePart(String s, int part, boolean withDmg) {
/* 1184 */     switch (part) {
/*      */       case 2:
/* 1186 */         if (this.qualityText != null) {
/* 1187 */           if (!s.isEmpty()) s = s + " ";
/*      */           
/* 1189 */           s = s + this.qualityText;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 3:
/* 1194 */         if (this.styleText != null) {
/* 1195 */           if (!s.isEmpty()) s = s + " ";
/*      */           
/* 1197 */           s = s + this.styleText;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 4:
/* 1202 */         if (this.name != null) {
/* 1203 */           if (!s.isEmpty()) s = s + " ";
/*      */           
/* 1205 */           s = s + this.name;
/*      */           
/* 1207 */           if (withDmg) {
/* 1208 */             String dmg = getMainDamageType();
/*      */             
/* 1210 */             if (dmg != null) {
/* 1211 */               s = s + " [" + dmg + "]";
/*      */             }
/*      */           } 
/*      */         } 
/*      */         break;
/*      */     } 
/*      */     
/* 1218 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getModName() {
/* 1223 */     if (this.rarity == null) return null;
/*      */     
/* 1225 */     String modName = null;
/*      */     
/* 1227 */     if (this.rarity.equals("Epic") || this.rarity.equals("Legendary")) {
/* 1228 */       if (this.itemID.startsWith("records/apoc/")) modName = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MOD_CAT"); 
/* 1229 */       if (this.itemID.startsWith("records/cat/")) modName = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MOD_CAT"); 
/* 1230 */       if (this.itemID.startsWith("records/doh/")) modName = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MOD_CAT"); 
/* 1231 */       if (this.itemID.startsWith("records/d2/")) modName = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MOD_D2"); 
/* 1232 */       if (this.itemID.startsWith("records/d3/")) modName = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MOD_D3"); 
/* 1233 */       if (this.itemID.startsWith("records/tq/")) modName = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MOD_GQ"); 
/* 1234 */       if (this.itemID.startsWith("records/ncff/")) modName = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MOD_NCFF"); 
/* 1235 */       if (this.itemID.startsWith("records/zen/")) modName = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MOD_ZEN");
/*      */       
/* 1237 */       if (modName != null) modName = "[" + modName + "]";
/*      */     
/*      */     } 
/* 1240 */     return modName;
/*      */   }
/*      */   
/*      */   private String combineName(boolean withDmg) {
/* 1244 */     String fullname = "";
/*      */     
/* 1246 */     fullname = appendNamePart(fullname, GDStashFrame.dbConfig.namePart1, withDmg);
/* 1247 */     fullname = appendNamePart(fullname, GDStashFrame.dbConfig.namePart2, withDmg);
/* 1248 */     fullname = appendNamePart(fullname, GDStashFrame.dbConfig.namePart3, withDmg);
/* 1249 */     fullname = appendNamePart(fullname, GDStashFrame.dbConfig.namePart4, withDmg);
/* 1250 */     fullname = appendNamePart(fullname, GDStashFrame.dbConfig.namePart5, withDmg);
/*      */ 
/*      */     
/* 1253 */     String modName = getModName();
/* 1254 */     if (modName != null) {
/* 1255 */       fullname = fullname + " " + modName;
/*      */     }
/*      */     
/* 1258 */     return fullname;
/*      */   }
/*      */   
/*      */   private int getRarityInt() {
/* 1262 */     if (this.rarity == null) return -1;
/*      */     
/* 1264 */     if (this.rarity.equals("Broken")) return 1; 
/* 1265 */     if (this.rarity.equals("Common")) return 2; 
/* 1266 */     if (this.rarity.equals("Magical")) return 3; 
/* 1267 */     if (this.rarity.equals("Rare")) return 4; 
/* 1268 */     if (this.rarity.equals("Epic")) return 5; 
/* 1269 */     if (this.rarity.equals("Legendary")) return 6; 
/* 1270 */     if (this.rarity.equals("Quest")) return 7;
/*      */     
/* 1272 */     if (this.rarity.equals("Lesser")) return 8; 
/* 1273 */     if (this.rarity.equals("Greater")) return 9; 
/* 1274 */     if (this.rarity.equals("Divine")) return 10;
/*      */     
/* 1276 */     return -1;
/*      */   }
/*      */   
/*      */   private int getArmorClassInt() {
/* 1280 */     if (this.armorClass == null) return -1;
/*      */     
/* 1282 */     if (this.armorClass.equals("Caster")) return 1; 
/* 1283 */     if (this.armorClass.equals("Light")) return 2; 
/* 1284 */     if (this.armorClass.equals("Heavy")) return 3;
/*      */     
/* 1286 */     return -1;
/*      */   }
/*      */   
/*      */   private int getArtifactClassInt() {
/* 1290 */     if (this.artifactClass == null) return -1;
/*      */     
/* 1292 */     if (this.artifactClass.equals("Lesser")) return 1; 
/* 1293 */     if (this.artifactClass.equals("Greater")) return 2; 
/* 1294 */     if (this.artifactClass.equals("Divine")) return 3;
/*      */     
/* 1296 */     return -1;
/*      */   }
/*      */   
/*      */   public boolean isArmor() {
/* 1300 */     return ItemClass.isArmor(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isArtifact() {
/* 1304 */     return ItemClass.isArtifact(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isComponent() {
/* 1308 */     return ItemClass.isComponent(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isEnchantment() {
/* 1312 */     return ItemClass.isEnchantment(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isJewelry() {
/* 1316 */     return ItemClass.isJewelry(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isNote() {
/* 1320 */     return ItemClass.isNote(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isFactionBooster() {
/* 1324 */     return ItemClass.isFactionBooster(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isElixir() {
/* 1328 */     return ItemClass.isElixir(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isPotion() {
/* 1332 */     return ItemClass.isPotion(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isOffhand() {
/* 1336 */     return ItemClass.isOffhand(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isWeapon() {
/* 1340 */     return ItemClass.isWeapon(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean is1HWeapon() {
/* 1344 */     return ItemClass.is1HWeapon(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean is2HWeapon() {
/* 1348 */     return ItemClass.is2HWeapon(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isStackable() {
/* 1352 */     return ItemClass.isStackable(this.itemClass);
/*      */   }
/*      */   
/*      */   public boolean isEpic() {
/* 1356 */     if (this.rarity == null) return false;
/*      */     
/* 1358 */     return this.rarity.equals("Epic");
/*      */   }
/*      */   
/*      */   public boolean isLegendary() {
/* 1362 */     if (this.rarity == null) return false;
/*      */     
/* 1364 */     return this.rarity.equals("Legendary");
/*      */   }
/*      */   
/*      */   public boolean isUnique() {
/* 1368 */     return (isEpic() || isLegendary());
/*      */   }
/*      */   
/*      */   public String getMainDamageType() {
/* 1372 */     if (!isWeapon()) return null;
/*      */     
/* 1374 */     DBStat maxDmgStat = null;
/* 1375 */     int max = 0;
/* 1376 */     int minDmg = 0;
/* 1377 */     int maxDmg = 0;
/*      */     
/* 1379 */     for (DBStat stat : this.stats) {
/* 1380 */       boolean found = false;
/*      */       
/* 1382 */       if (stat.getStatType().equals("offensiveAether")) found = true; 
/* 1383 */       if (stat.getStatType().equals("offensiveBaseAether")) found = true; 
/* 1384 */       if (stat.getStatType().equals("offensiveChaos")) found = true; 
/* 1385 */       if (stat.getStatType().equals("offensiveBaseChaos")) found = true; 
/* 1386 */       if (stat.getStatType().equals("offensiveCold")) found = true; 
/* 1387 */       if (stat.getStatType().equals("offensiveBaseCold")) found = true; 
/* 1388 */       if (stat.getStatType().equals("offensiveElemental")) found = true; 
/* 1389 */       if (stat.getStatType().equals("offensiveFire")) found = true; 
/* 1390 */       if (stat.getStatType().equals("offensiveBaseFire")) found = true; 
/* 1391 */       if (stat.getStatType().equals("offensiveLife")) found = true; 
/* 1392 */       if (stat.getStatType().equals("offensiveBaseLife")) found = true; 
/* 1393 */       if (stat.getStatType().equals("offensiveLightning")) found = true; 
/* 1394 */       if (stat.getStatType().equals("offensiveBaseLightning")) found = true; 
/* 1395 */       if (stat.getStatType().equals("offensivePhysical")) found = true; 
/* 1396 */       if (stat.getStatType().equals("offensiveBonusPhysical")) found = true; 
/* 1397 */       if (stat.getStatType().equals("offensivePierce")) found = true; 
/* 1398 */       if (stat.getStatType().equals("offensivePoison")) found = true; 
/* 1399 */       if (stat.getStatType().equals("offensiveBasePoison")) found = true;
/*      */ 
/*      */       
/* 1402 */       if (!found)
/*      */         continue; 
/* 1404 */       if (stat.getStatChance() == 0 && 
/* 1405 */         stat.getStatMin() > 0.0F) {
/* 1406 */         minDmg = (int)stat.getStatMin();
/* 1407 */         maxDmg = (int)stat.getStatMax();
/*      */         
/* 1409 */         if (maxDmg > 0) {
/* 1410 */           minDmg += maxDmg;
/*      */         } else {
/* 1412 */           minDmg += minDmg;
/*      */         } 
/*      */         
/* 1415 */         if (minDmg > max) {
/* 1416 */           max = minDmg;
/* 1417 */           maxDmgStat = stat;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1423 */     String sDmg = null;
/*      */     
/* 1425 */     if (maxDmgStat != null) {
/* 1426 */       if (maxDmgStat.getStatType().equals("offensiveAether")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_AETHER"); 
/* 1427 */       if (maxDmgStat.getStatType().equals("offensiveBaseAether")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_AETHER"); 
/* 1428 */       if (maxDmgStat.getStatType().equals("offensiveChaos")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_CHAOS"); 
/* 1429 */       if (maxDmgStat.getStatType().equals("offensiveBaseChaos")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_CHAOS"); 
/* 1430 */       if (maxDmgStat.getStatType().equals("offensiveCold")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_COLD"); 
/* 1431 */       if (maxDmgStat.getStatType().equals("offensiveBaseCold")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_COLD"); 
/* 1432 */       if (maxDmgStat.getStatType().equals("offensiveElemental")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_ELEMENTAL"); 
/* 1433 */       if (maxDmgStat.getStatType().equals("offensiveFire")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_FIRE"); 
/* 1434 */       if (maxDmgStat.getStatType().equals("offensiveBaseFire")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_FIRE"); 
/* 1435 */       if (maxDmgStat.getStatType().equals("offensiveLife")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_LIFE"); 
/* 1436 */       if (maxDmgStat.getStatType().equals("offensiveBaseLife")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_LIFE"); 
/* 1437 */       if (maxDmgStat.getStatType().equals("offensiveLightning")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_LIGHTNING"); 
/* 1438 */       if (maxDmgStat.getStatType().equals("offensiveBaseLightning")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_LIGHTNING"); 
/* 1439 */       if (maxDmgStat.getStatType().equals("offensivePhysical")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_PHYSICAL"); 
/* 1440 */       if (maxDmgStat.getStatType().equals("offensiveBonusPhysical")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_PHYSICAL"); 
/* 1441 */       if (maxDmgStat.getStatType().equals("offensivePierce")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_PIERCE"); 
/* 1442 */       if (maxDmgStat.getStatType().equals("offensivePoison")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_POISON"); 
/* 1443 */       if (maxDmgStat.getStatType().equals("offensiveBasePoison")) sDmg = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_POISON");
/*      */       
/* 1445 */       return sDmg;
/*      */     } 
/*      */     
/* 1448 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setItemClass(String itemClass, String filename, String dlc) {
/* 1456 */     this.itemClass = itemClass;
/*      */     
/* 1458 */     if (filename.equals("records/items/misc/inventorybag.dbr")) {
/* 1459 */       this.itemClass = "QuestItem";
/*      */     }
/*      */     
/* 1462 */     if (itemClass.equals("QuestItem")) setQuestItem(true);
/*      */ 
/*      */     
/* 1465 */     if (filename.equals("records/items/crafting/consumables/xppotion_malmouth.dbr")) {
/* 1466 */       this.itemClass = "ItemDevotionReset";
/*      */     }
/*      */ 
/*      */     
/* 1470 */     if (filename.equals("records/items/transmutes/dlc_medal_backer.dbr") || filename
/* 1471 */       .equals("records/items/transmutes/dlc_medal_ks_officer.dbr") || filename
/* 1472 */       .equals("records/items/transmutes/dlc_medal_ks_service.dbr") || filename
/* 1473 */       .equals("records/items/transmutes/dlc_medal_ks_veteran.dbr") || filename
/* 1474 */       .equals("records/items/transmutes/transmute_medal_backer.dbr") || filename
/* 1475 */       .equals("records/items/transmutes/transmute_medal_ks_officer.dbr") || filename
/* 1476 */       .equals("records/items/transmutes/transmute_medal_ks_service.dbr") || filename
/* 1477 */       .equals("records/items/transmutes/transmute_medal_ks_veteran.dbr")) {
/* 1478 */       this.itemClass = "Kickstarter";
/*      */     }
/*      */     
/* 1481 */     if (filename.equals("records/items/transmutes/invisible_head.dbr") || filename
/* 1482 */       .equals("records/items/transmutes/invisible_medal.dbr") || filename
/* 1483 */       .equals("records/items/transmutes/invisible_shoulders.dbr") || filename
/* 1484 */       .equals("records/items/transmutes/transmute_removal.dbr")) {
/* 1485 */       this.itemClass = "Kickstarter";
/*      */     }
/*      */ 
/*      */     
/* 1489 */     if (filename.equals("records/items/bonusitems/bonus_burrwitchbrew.dbr") || filename
/* 1490 */       .equals("records/items/bonusitems/bonus_summonwisp.dbr") || filename
/* 1491 */       .equals("records/items/transmutes/dlc_head_admiralhat.dbr") || filename
/* 1492 */       .equals("records/items/transmutes/dlc_head_pillager.dbr") || filename
/* 1493 */       .equals("records/items/transmutes/dlc_head_powderedwig.dbr") || filename
/* 1494 */       .equals("records/items/transmutes/dlc_head_samurai.dbr") || filename
/* 1495 */       .equals("records/items/transmutes/dlc_head_warhelm.dbr") || filename
/* 1496 */       .equals("records/items/transmutes/dlc_torso_swashbucklerscoat.dbr") || filename
/* 1497 */       .equals("records/items/transmutes/transmute_admiralhat.dbr") || filename
/* 1498 */       .equals("records/items/transmutes/transmute_pillagerhelm.dbr") || filename
/* 1499 */       .equals("records/items/transmutes/transmute_powderedwig.dbr") || filename
/* 1500 */       .equals("records/items/transmutes/transmute_samuraihelm.dbr") || filename
/* 1501 */       .equals("records/items/transmutes/transmute_swashbucklerscoat.dbr") || filename
/* 1502 */       .equals("records/items/transmutes/transmute_warhelm.dbr")) {
/* 1503 */       this.itemClass = "Kickstarter";
/*      */     }
/*      */ 
/*      */     
/* 1507 */     if (filename.equals("records/items/bonusitems/bonus_summonbat.dbr") || filename
/* 1508 */       .equals("records/items/bonusitems/bonus_summoncrate.dbr") || filename
/* 1509 */       .equals("records/items/bonusitems/bonus_summonowl.dbr") || filename
/* 1510 */       .startsWith("records/items/transmutes/dragongeneral/") || filename
/* 1511 */       .startsWith("records/items/transmutes/greatwolf/") || filename
/* 1512 */       .startsWith("records/items/transmutes/knight/") || filename
/* 1513 */       .startsWith("records/items/transmutes/wizard/") || filename
/* 1514 */       .equals("records/items/transmutes/transmute_blackknight_set.dbr") || filename
/* 1515 */       .equals("records/items/transmutes/transmute_darkwizard_set.dbr") || filename
/* 1516 */       .equals("records/items/transmutes/transmute_dragongeneral_set.dbr") || filename
/* 1517 */       .equals("records/items/transmutes/transmute_greatwolf_set.dbr") || filename
/* 1518 */       .equals("records/items/transmutes/transmute_silverknight_set.dbr") || filename
/* 1519 */       .equals("records/items/transmutes/transmute_wizard_set.dbr")) {
/* 1520 */       this.itemClass = "Kickstarter";
/*      */     }
/*      */ 
/*      */     
/* 1524 */     if (filename.startsWith("records/items/bonusitems/") || filename
/* 1525 */       .startsWith("records/items/transmutes/")) {
/* 1526 */       this.itemClass = "Kickstarter";
/*      */     }
/*      */     
/* 1529 */     if (dlc != null) {
/* 1530 */       this.itemClass = "Kickstarter";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setQuestItem(boolean questItem) {
/* 1538 */     if (!this.questItem) this.questItem = questItem; 
/*      */   }
/*      */   
/*      */   private void setCraftID(String craftID) {
/* 1542 */     this.dbCraft.setCraftID(craftID);
/*      */   }
/*      */   
/*      */   private void setItemNameTag(String itemNameTag) {
/* 1546 */     String s = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_ITEMS, itemNameTag, false);
/*      */ 
/*      */     
/* 1549 */     if (s == null) {
/* 1550 */       s = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_STORY, itemNameTag, false);
/*      */     }
/*      */     
/* 1553 */     this.genderCode = -1;
/*      */     
/* 1555 */     if (s != null && 
/* 1556 */       s.startsWith("[")) {
/*      */       
/* 1558 */       String code = s.substring(0, 4);
/* 1559 */       s = s.substring(4);
/*      */       
/* 1561 */       this.genderCode = ARZDecompress.getGenderCode(code);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1567 */     if (this.genderCode == -1) this.genderCode = 0;
/*      */     
/* 1569 */     this.name = s;
/*      */   }
/*      */   
/*      */   private void setQualityTag(String qualityTag) {
/* 1573 */     this.qualityTag = qualityTag;
/*      */     
/* 1575 */     if (qualityTag == null) {
/* 1576 */       this.qualityText = null;
/*      */     } else {
/* 1578 */       this.qualityText = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.GD, GDConstants.TAG_TEXT_ITEMS, qualityTag, false);
/*      */       
/* 1580 */       if (this.qualityText != null) {
/* 1581 */         String[] genders = ARZDecompress.getGenderTexts(this.qualityText);
/*      */         
/* 1583 */         this.qualityText = genders[this.genderCode];
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setStyleTag(String styleTag) {
/* 1589 */     this.styleTag = styleTag;
/*      */     
/* 1591 */     if (styleTag == null) {
/* 1592 */       this.styleText = null;
/*      */     } else {
/* 1594 */       this.styleText = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.GD, GDConstants.TAG_TEXT_ITEMS, styleTag, false);
/*      */       
/* 1596 */       if (this.styleText != null) {
/* 1597 */         String[] genders = ARZDecompress.getGenderTexts(this.styleText);
/*      */         
/* 1599 */         this.styleText = genders[this.genderCode];
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setItemSetNameTag(String setNameTag) {
/* 1605 */     this.setName = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_ITEMS, setNameTag, false);
/*      */   }
/*      */   
/*      */   private void setCostFormulaSetID(String costFormulaSetID) {
/* 1609 */     this.costFormulaSetID = costFormulaSetID;
/*      */     
/* 1611 */     if (ItemClass.usesCostFormula(this.itemClass)) {
/* 1612 */       if (costFormulaSetID == null) {
/* 1613 */         costFormulaSetID = "records/game/itemcostformulas.dbr";
/*      */       }
/*      */       
/* 1616 */       this.dbFormulaSet = ARZFormulaSetPool.getFormulaSet(costFormulaSetID);
/*      */     } else {
/* 1618 */       this.dbFormulaSet = null;
/*      */     } 
/*      */ 
/*      */     
/* 1622 */     if (this.dbFormulaSet != null) {
/* 1623 */       determineStatRequirements();
/*      */     }
/*      */   }
/*      */   
/*      */   private void setBitmap(byte[] bitmap) {
/* 1628 */     int size = 0;
/*      */     
/* 1630 */     if (bitmap != null) size = bitmap.length;
/*      */     
/* 1632 */     if (size >= 131072) {
/* 1633 */       Object[] args = { this.itemID };
/* 1634 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_IN_ITEM_IMAGE_SIZE", args);
/*      */       
/* 1636 */       GDMsgLogger.addWarning(msg);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1641 */     this.bitmap = bitmap;
/*      */   }
/*      */   
/*      */   private void setShardBitmap(byte[] shardBitmap) {
/* 1645 */     int size = 0;
/*      */     
/* 1647 */     if (shardBitmap != null) size = shardBitmap.length;
/*      */     
/* 1649 */     if (size >= 32768) {
/* 1650 */       Object[] args = { this.itemID };
/* 1651 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_IN_ITEM_SHARD_SIZE", args);
/*      */       
/* 1653 */       GDMsgLogger.addWarning(msg);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1658 */     this.shardBitmap = shardBitmap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void clearBuffer() {
/* 1666 */     hashBuffer.clear();
/*      */   }
/*      */   
/*      */   public static void createTables() throws SQLException {
/* 1670 */     String dropTable = "DROP TABLE GD_ITEM";
/* 1671 */     String createTable = "CREATE TABLE GD_ITEM (ITEM_ID       VARCHAR(256) NOT NULL, ITEM_CLASS         VARCHAR(32), ARMOR_CLASS        VARCHAR(32), ARTIFACT_CLASS     VARCHAR(32), MESH_ID            VARCHAR(256), BASE_TEXTURE_ID    VARCHAR(512), BUMP_TEXTURE_ID    VARCHAR(512), GLOW_TEXTURE_ID    VARCHAR(512), SHADER_ID          VARCHAR(256), BITMAP_ID          VARCHAR(256), BITMAP             BLOB(128K), SHARD_BITMAP_ID    VARCHAR(256), SHARD_BITMAP       BLOB(32K), GENDER_CODE        INTEGER, NAME               VARCHAR(256), DESCRIPTION        VARCHAR(8000), QUALITY_TAG        VARCHAR(64), QUALITY_TEXT       VARCHAR(64), STYLE_TAG          VARCHAR(64), STYLE_TEXT         VARCHAR(64), NAME_FULL          VARCHAR(256), RARITY             VARCHAR(32), SET_ID             VARCHAR(256), SET_NAME           VARCHAR(256), BONUS_AFFIXSET_ID  VARCHAR(256), ITEM_SKILL_ID      VARCHAR(256), ITEM_SKILL_LEVEL   INTEGER,PET_BONUS_SKILL_ID VARCHAR(256), CONTROLLER_ID      VARCHAR(256), COST_FORMULASET_ID VARCHAR(256), CONVERT_IN         VARCHAR(16), CONVERT_OUT        VARCHAR(16), CONVERT_IN_2       VARCHAR(16), CONVERT_OUT_2      VARCHAR(16), SOULBOUND          BOOLEAN, HIDE_PREFIX        BOOLEAN, HIDE_SUFFIX        BOOLEAN, QUESTITEM          BOOLEAN, CANNOT_PICKUP      BOOLEAN, ENEMY_ONLY         BOOLEAN, LEVEL              INTEGER, REQ_LEVEL          INTEGER, REQ_DEX            INTEGER, REQ_INT            INTEGER, REQ_STR            INTEGER, OFFENSE_PRC        INTEGER, RETAL_PRC          INTEGER, PLUS_ALLSKILLS     INTEGER, COMPONENT_PIECES   INTEGER, MAX_STACKSIZE      INTEGER, RNG_PERCENT        INTEGER, SLOT_AXE_1H        BOOLEAN, SLOT_AXE_2H        BOOLEAN, SLOT_DAGGER_1H     BOOLEAN, SLOT_MACE_1H       BOOLEAN, SLOT_MACE_2H       BOOLEAN, SLOT_SCEPTER_1H    BOOLEAN, SLOT_SPEAR_1H      BOOLEAN, SLOT_STAFF_2H      BOOLEAN, SLOT_SWORD_1H      BOOLEAN, SLOT_SWORD_2H      BOOLEAN, SLOT_RANGED_1H     BOOLEAN, SLOT_RANGED_2H     BOOLEAN, SLOT_SHIELD        BOOLEAN, SLOT_OFFHAND       BOOLEAN, SLOT_AMULET        BOOLEAN, SLOT_BELT          BOOLEAN, SLOT_MEDAL         BOOLEAN, SLOT_RING          BOOLEAN, SLOT_HEAD          BOOLEAN, SLOT_SHOULDERS     BOOLEAN, SLOT_CHEST         BOOLEAN, SLOT_HANDS         BOOLEAN, SLOT_LEGS          BOOLEAN, SLOT_FEET          BOOLEAN, PRIMARY KEY (ITEM_ID))";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1761 */     try (Connection conn = GDDBData.getConnection()) {
/* 1762 */       boolean auto = conn.getAutoCommit();
/* 1763 */       conn.setAutoCommit(false);
/*      */       
/* 1765 */       try (Statement st = conn.createStatement()) {
/* 1766 */         if (GDDBUtil.tableExists(conn, "GD_ITEM")) {
/* 1767 */           st.execute(dropTable);
/*      */         }
/* 1769 */         if (GDDBUtil.tableExists(conn, "GD_ITEM_CHAR")) {
/* 1770 */           st.execute("DROP TABLE GD_ITEM_CHAR");
/*      */         }
/* 1772 */         if (GDDBUtil.tableExists(conn, "GD_ITEM_CHARRACES")) {
/* 1773 */           st.execute("DROP TABLE GD_ITEM_CHARRACES");
/*      */         }
/* 1775 */         if (GDDBUtil.tableExists(conn, "GD_ITEM_DAMAGE")) {
/* 1776 */           st.execute("DROP TABLE GD_ITEM_DAMAGE");
/*      */         }
/* 1778 */         st.execute(createTable);
/* 1779 */         st.close();
/*      */         
/* 1781 */         conn.commit();
/*      */         
/* 1783 */         DBItemCraft.createTables(conn);
/* 1784 */         DBStat.createItemTable(conn);
/* 1785 */         DBStatBonusRace.createItemTable(conn);
/* 1786 */         DBSkillBonus.createItemTable(conn);
/* 1787 */         DBSkillModifier.createItemTable(conn);
/*      */       }
/* 1789 */       catch (SQLException ex) {
/* 1790 */         conn.rollback();
/*      */         
/* 1792 */         Object[] args = { "GD_ITEM" };
/* 1793 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*      */         
/* 1795 */         GDMsgLogger.addError(msg);
/*      */         
/* 1797 */         throw ex;
/*      */       } finally {
/*      */         
/* 1800 */         conn.setAutoCommit(auto);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void delete(String itemID) throws SQLException {
/* 1806 */     String deleteEntry = "DELETE FROM GD_ITEM WHERE ITEM_ID = ?";
/*      */     
/* 1808 */     try (Connection conn = GDDBData.getConnection()) {
/* 1809 */       boolean auto = conn.getAutoCommit();
/* 1810 */       conn.setAutoCommit(false);
/*      */       
/* 1812 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 1813 */         ps.setString(1, itemID);
/* 1814 */         ps.executeUpdate();
/* 1815 */         ps.close();
/*      */         
/* 1817 */         DBItemCraft.delete(conn, itemID);
/* 1818 */         DBStat.deleteItem(conn, itemID);
/* 1819 */         DBStatBonusRace.deleteItem(conn, itemID);
/* 1820 */         DBSkillBonus.deleteItem(conn, itemID);
/* 1821 */         DBSkillModifier.deleteItem(conn, itemID);
/*      */         
/* 1823 */         conn.commit();
/*      */       }
/* 1825 */       catch (SQLException ex) {
/* 1826 */         conn.rollback();
/*      */         
/* 1828 */         Object[] args = { itemID, "GD_ITEM" };
/* 1829 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*      */         
/* 1831 */         GDMsgLogger.addError(msg);
/* 1832 */         GDMsgLogger.addError(ex);
/*      */         
/* 1834 */         throw ex;
/*      */       } finally {
/*      */         
/* 1837 */         conn.setAutoCommit(auto);
/*      */       }
/*      */     
/* 1840 */     } catch (SQLException ex) {
/* 1841 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void insert(ARZRecord record) throws SQLException {
/* 1846 */     DBItem entry = get(record.getFileName());
/*      */     
/* 1848 */     if (entry != null)
/*      */       return; 
/* 1850 */     DBItem item = new DBItem(record);
/*      */     
/* 1852 */     String insertItem = "INSERT INTO GD_ITEM VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */ 
/*      */     
/* 1855 */     try (Connection conn = GDDBData.getConnection()) {
/* 1856 */       boolean auto = conn.getAutoCommit();
/* 1857 */       conn.setAutoCommit(false);
/*      */       
/* 1859 */       try (PreparedStatement ps = conn.prepareStatement(insertItem)) {
/* 1860 */         ps.setString(1, item.itemID);
/* 1861 */         ps.setString(2, item.itemClass);
/* 1862 */         ps.setString(3, item.armorClass);
/* 1863 */         ps.setString(4, item.artifactClass);
/* 1864 */         ps.setString(5, item.meshID);
/* 1865 */         ps.setString(6, item.baseTextureID);
/* 1866 */         ps.setString(7, item.bumpTextureID);
/* 1867 */         ps.setString(8, item.glowTextureID);
/* 1868 */         ps.setString(9, item.shaderID);
/* 1869 */         ps.setString(10, item.bitmapID);
/* 1870 */         if (item.bitmap == null) {
/* 1871 */           ps.setBlob(11, (Blob)null);
/*      */         } else {
/* 1873 */           ps.setBlob(11, new ByteArrayInputStream(item.bitmap));
/*      */         } 
/* 1875 */         ps.setString(12, item.shardBitmapID);
/* 1876 */         if (item.shardBitmap == null) {
/* 1877 */           ps.setBlob(13, (Blob)null);
/*      */         } else {
/* 1879 */           ps.setBlob(13, new ByteArrayInputStream(item.shardBitmap));
/*      */         } 
/* 1881 */         ps.setInt(14, item.genderCode);
/* 1882 */         ps.setString(15, item.name);
/* 1883 */         ps.setString(16, item.description);
/* 1884 */         ps.setString(17, item.qualityTag);
/* 1885 */         ps.setString(18, item.qualityText);
/* 1886 */         ps.setString(19, item.styleTag);
/* 1887 */         ps.setString(20, item.styleText);
/* 1888 */         ps.setString(21, item.nameFull);
/* 1889 */         ps.setString(22, item.rarity);
/* 1890 */         ps.setString(23, item.setID);
/* 1891 */         ps.setString(24, item.setName);
/* 1892 */         ps.setString(25, item.bonusAffixSetID);
/* 1893 */         ps.setString(26, item.itemSkillID);
/* 1894 */         ps.setInt(27, item.itemSkillLevel);
/* 1895 */         ps.setString(28, item.petBonusSkillID);
/* 1896 */         ps.setString(29, item.controllerID);
/* 1897 */         ps.setString(30, item.costFormulaSetID);
/* 1898 */         ps.setString(31, item.convertIn);
/* 1899 */         ps.setString(32, item.convertOut);
/* 1900 */         ps.setString(33, item.convertIn2);
/* 1901 */         ps.setString(34, item.convertOut2);
/* 1902 */         ps.setBoolean(35, item.soulbound);
/* 1903 */         ps.setBoolean(36, item.hidePrefix);
/* 1904 */         ps.setBoolean(37, item.hideSuffix);
/* 1905 */         ps.setBoolean(38, item.questItem);
/* 1906 */         ps.setBoolean(39, item.cannotPickup);
/* 1907 */         ps.setBoolean(40, item.enemyOnly);
/* 1908 */         ps.setInt(41, item.itemLevel);
/* 1909 */         ps.setInt(42, item.reqLevel);
/* 1910 */         ps.setInt(43, item.reqDex);
/* 1911 */         ps.setInt(44, item.reqInt);
/* 1912 */         ps.setInt(45, item.reqStr);
/* 1913 */         ps.setInt(46, item.offensiveChance);
/* 1914 */         ps.setInt(47, item.retaliationChance);
/* 1915 */         ps.setInt(48, item.plusAllSkills);
/* 1916 */         ps.setInt(49, item.componentPieces);
/* 1917 */         ps.setInt(50, item.maxStackSize);
/* 1918 */         ps.setInt(51, item.scalePercent);
/* 1919 */         ps.setBoolean(52, item.slots.slotAxe1H);
/* 1920 */         ps.setBoolean(53, item.slots.slotAxe2H);
/* 1921 */         ps.setBoolean(54, item.slots.slotDagger1H);
/* 1922 */         ps.setBoolean(55, item.slots.slotMace1H);
/* 1923 */         ps.setBoolean(56, item.slots.slotMace2H);
/* 1924 */         ps.setBoolean(57, item.slots.slotScepter1H);
/* 1925 */         ps.setBoolean(58, item.slots.slotSpear1H);
/* 1926 */         ps.setBoolean(59, item.slots.slotStaff2H);
/* 1927 */         ps.setBoolean(60, item.slots.slotSword1H);
/* 1928 */         ps.setBoolean(61, item.slots.slotSword2H);
/* 1929 */         ps.setBoolean(62, item.slots.slotRanged1H);
/* 1930 */         ps.setBoolean(63, item.slots.slotRanged2H);
/* 1931 */         ps.setBoolean(64, item.slots.slotShield);
/* 1932 */         ps.setBoolean(65, item.slots.slotOffhand);
/* 1933 */         ps.setBoolean(66, item.slots.slotAmulet);
/* 1934 */         ps.setBoolean(67, item.slots.slotBelt);
/* 1935 */         ps.setBoolean(68, item.slots.slotMedal);
/* 1936 */         ps.setBoolean(69, item.slots.slotRing);
/* 1937 */         ps.setBoolean(70, item.slots.slotHead);
/* 1938 */         ps.setBoolean(71, item.slots.slotShoulders);
/* 1939 */         ps.setBoolean(72, item.slots.slotChest);
/* 1940 */         ps.setBoolean(73, item.slots.slotHands);
/* 1941 */         ps.setBoolean(74, item.slots.slotLegs);
/* 1942 */         ps.setBoolean(75, item.slots.slotFeet);
/*      */         
/* 1944 */         ps.executeUpdate();
/* 1945 */         ps.close();
/*      */         
/* 1947 */         if (item.dbCraft.getCraftID() != null) {
/* 1948 */           DBItemCraft.insert(conn, item);
/*      */         }
/* 1950 */         DBStat.insertItem(conn, item.itemID, item.stats);
/* 1951 */         DBStatBonusRace.insertItem(conn, item.itemID, item.statBonusRaces);
/* 1952 */         DBSkillBonus.insertItem(conn, item.itemID, item.bonuses);
/* 1953 */         DBSkillModifier.insertItem(conn, item.itemID, item.skillModifiers);
/*      */         
/* 1955 */         conn.commit();
/*      */       }
/* 1957 */       catch (SQLException ex) {
/* 1958 */         conn.rollback();
/*      */         
/* 1960 */         Object[] args = { record.getFileName(), "GD_ITEM" };
/* 1961 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*      */         
/* 1963 */         GDMsgLogger.addLowError(msg);
/* 1964 */         GDMsgLogger.addLowError(ex);
/*      */       } finally {
/*      */         
/* 1967 */         conn.setAutoCommit(auto);
/*      */       }
/*      */     
/* 1970 */     } catch (SQLException ex) {
/* 1971 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static DBItem get(String itemID) {
/* 1976 */     DBItem item = null;
/*      */     
/* 1978 */     item = hashBuffer.get(itemID);
/*      */     
/* 1980 */     if (item == null)
/*      */     {
/* 1982 */       item = getDB(itemID);
/*      */     }
/*      */     
/* 1985 */     return item;
/*      */   }
/*      */   
/*      */   private static DBItem getDB(String itemID) {
/* 1989 */     DBItem item = null;
/*      */     
/* 1991 */     String command = "SELECT * FROM GD_ITEM WHERE ITEM_ID = ?";
/*      */     
/* 1993 */     try(Connection conn = GDDBData.getConnection(); 
/* 1994 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 1995 */       ps.setString(1, itemID);
/*      */       
/* 1997 */       try (ResultSet rs = ps.executeQuery()) {
/* 1998 */         List<DBItem> list = wrap(rs);
/*      */         
/* 2000 */         if (list.isEmpty()) {
/* 2001 */           item = null;
/*      */         } else {
/* 2003 */           item = list.get(0);
/*      */         } 
/*      */         
/* 2006 */         conn.commit();
/*      */       }
/* 2008 */       catch (SQLException ex) {
/* 2009 */         throw ex;
/*      */       }
/*      */     
/* 2012 */     } catch (SQLException ex) {
/* 2013 */       Object[] args = { itemID, "GD_ITEM" };
/* 2014 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2016 */       GDMsgLogger.addError(msg);
/* 2017 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2020 */     return item;
/*      */   }
/*      */   
/*      */   private static SetInfo getSetInfo(String itemID) {
/* 2024 */     SetInfo info = null;
/*      */     
/* 2026 */     String command = "SELECT ITEM_ID, RARITY, REQ_LEVEL FROM GD_ITEM WHERE ITEM_ID = ?";
/*      */     
/* 2028 */     try(Connection conn = GDDBData.getConnection(); 
/* 2029 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 2030 */       ps.setString(1, itemID);
/*      */       
/* 2032 */       try (ResultSet rs = ps.executeQuery()) {
/* 2033 */         List<SetInfo> list = wrapSetInfo(rs);
/*      */         
/* 2035 */         if (list.isEmpty()) {
/* 2036 */           info = null;
/*      */         } else {
/* 2038 */           info = list.get(0);
/*      */         } 
/*      */         
/* 2041 */         conn.commit();
/*      */       }
/* 2043 */       catch (SQLException ex) {
/* 2044 */         throw ex;
/*      */       }
/*      */     
/* 2047 */     } catch (SQLException ex) {
/* 2048 */       Object[] args = { itemID, "GD_ITEM" };
/* 2049 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2051 */       GDMsgLogger.addError(msg);
/* 2052 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2055 */     return info;
/*      */   }
/*      */   
/*      */   public static List<DBItem> getByItemIDs(List<String> itemIDs) {
/* 2059 */     List<DBItem> list = new LinkedList<>();
/*      */     
/* 2061 */     for (String itemID : itemIDs) {
/* 2062 */       DBItem item = get(itemID);
/* 2063 */       list.add(item);
/*      */     } 
/*      */     
/* 2066 */     return list;
/*      */   }
/*      */   
/*      */   public static List<GDItem> getGDItemsByItemIDs(List<String> itemIDs) {
/* 2070 */     List<GDItem> list = new LinkedList<>();
/*      */     
/* 2072 */     for (String itemID : itemIDs) {
/* 2073 */       DBItem dbi = get(itemID);
/*      */       
/* 2075 */       if (dbi != null) {
/* 2076 */         list.add(new GDItem(dbi));
/*      */       }
/*      */     } 
/*      */     
/* 2080 */     return list;
/*      */   }
/*      */   
/*      */   public static List<SetInfo> getSetInfoByItemIDs(List<String> itemIDs) {
/* 2084 */     List<SetInfo> list = new LinkedList<>();
/*      */     
/* 2086 */     for (String itemID : itemIDs) {
/* 2087 */       SetInfo info = getSetInfo(itemID);
/* 2088 */       list.add(info);
/*      */     } 
/*      */     
/* 2091 */     return list;
/*      */   }
/*      */   
/*      */   public static List<DBItem> getAll() {
/* 2095 */     List<DBItem> list = new LinkedList<>();
/*      */     
/* 2097 */     String command = "SELECT * FROM GD_ITEM";
/*      */     
/* 2099 */     try(Connection conn = GDDBData.getConnection(); 
/* 2100 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*      */       
/* 2102 */       try (ResultSet rs = ps.executeQuery()) {
/* 2103 */         list = wrap(rs);
/*      */         
/* 2105 */         conn.commit();
/*      */       }
/* 2107 */       catch (SQLException ex) {
/* 2108 */         throw ex;
/*      */       }
/*      */     
/* 2111 */     } catch (SQLException ex) {
/* 2112 */       Object[] args = { "All", "GD_ITEM" };
/* 2113 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2115 */       GDMsgLogger.addError(msg);
/* 2116 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2119 */     return list;
/*      */   }
/*      */   
/*      */   public static List<DBItem> getAllUniques() {
/* 2123 */     List<DBItem> list = new LinkedList<>();
/*      */     
/* 2125 */     String command = "SELECT * FROM GD_ITEM WHERE (RARITY = 'Epic' OR RARITY = 'Legendary') AND (ITEM_CLASS LIKE 'Armor%' OR ITEM_CLASS LIKE 'Weapon%')";
/*      */ 
/*      */ 
/*      */     
/* 2129 */     try(Connection conn = GDDBData.getConnection(); 
/* 2130 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*      */       
/* 2132 */       try (ResultSet rs = ps.executeQuery()) {
/* 2133 */         list = wrap(rs);
/*      */         
/* 2135 */         conn.commit();
/*      */       }
/* 2137 */       catch (SQLException ex) {
/* 2138 */         throw ex;
/*      */       }
/*      */     
/* 2141 */     } catch (SQLException ex) {
/* 2142 */       Object[] args = { "Uniques", "GD_ITEM" };
/* 2143 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2145 */       GDMsgLogger.addError(msg);
/* 2146 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2149 */     return list;
/*      */   }
/*      */   
/*      */   public static List<DBItem> getAllLegendaries() {
/* 2153 */     List<DBItem> list = new LinkedList<>();
/*      */     
/* 2155 */     String command = "SELECT * FROM GD_ITEM WHERE RARITY = 'Legendary' AND (ITEM_CLASS LIKE 'Armor%' OR ITEM_CLASS LIKE 'Weapon%')";
/*      */ 
/*      */ 
/*      */     
/* 2159 */     try(Connection conn = GDDBData.getConnection(); 
/* 2160 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*      */       
/* 2162 */       try (ResultSet rs = ps.executeQuery()) {
/* 2163 */         list = wrap(rs);
/*      */         
/* 2165 */         conn.commit();
/*      */       }
/* 2167 */       catch (SQLException ex) {
/* 2168 */         throw ex;
/*      */       }
/*      */     
/* 2171 */     } catch (SQLException ex) {
/* 2172 */       Object[] args = { "Legendaries", "GD_ITEM" };
/* 2173 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2175 */       GDMsgLogger.addError(msg);
/* 2176 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2179 */     return list;
/*      */   }
/*      */   
/*      */   public static List<DBItem> getAllBlueprints() {
/* 2183 */     List<DBItem> list = new LinkedList<>();
/*      */     
/* 2185 */     String command = "SELECT * FROM GD_ITEM WHERE (RARITY = 'Epic' OR RARITY = 'Legendary') AND ITEM_CLASS = 'ItemArtifactFormula' AND (ITEM_ID NOT LIKE '%/craft_random%' AND  ITEM_ID NOT LIKE '%test%' AND  ITEM_ID NOT LIKE 'records/mccm/%' AND  ITEM_ID NOT LIKE 'records/apoc/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/cat/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/d2/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/d3/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/doh/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/ncff/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/tq/items/crafting/blueprints/%')";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2200 */     try(Connection conn = GDDBData.getConnection(); 
/* 2201 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*      */       
/* 2203 */       try (ResultSet rs = ps.executeQuery()) {
/* 2204 */         list = wrap(rs);
/*      */         
/* 2206 */         conn.commit();
/*      */       }
/* 2208 */       catch (SQLException ex) {
/* 2209 */         throw ex;
/*      */       }
/*      */     
/* 2212 */     } catch (SQLException ex) {
/* 2213 */       Object[] args = { "Blueprints", "GD_ITEM" };
/* 2214 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2216 */       GDMsgLogger.addError(msg);
/* 2217 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2220 */     return list;
/*      */   }
/*      */   
/*      */   public static List<DBItem> getAllLegendaryBlueprints() {
/* 2224 */     List<DBItem> list = new LinkedList<>();
/*      */     
/* 2226 */     String command = "SELECT * FROM GD_ITEM WHERE RARITY = 'Legendary' AND ITEM_CLASS = 'ItemArtifactFormula' AND (ITEM_ID NOT LIKE '%/craft_random%' AND  ITEM_ID NOT LIKE '%test%' AND  ITEM_ID NOT LIKE 'records/mccm/%' AND  ITEM_ID NOT LIKE 'records/apoc/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/cat/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/d2/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/d3/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/doh/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/ncff/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/tq/items/crafting/blueprints/%')";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2241 */     try(Connection conn = GDDBData.getConnection(); 
/* 2242 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*      */       
/* 2244 */       try (ResultSet rs = ps.executeQuery()) {
/* 2245 */         list = wrap(rs);
/*      */         
/* 2247 */         conn.commit();
/*      */       }
/* 2249 */       catch (SQLException ex) {
/* 2250 */         throw ex;
/*      */       }
/*      */     
/* 2253 */     } catch (SQLException ex) {
/* 2254 */       Object[] args = { "Legendary Blueprints", "GD_ITEM" };
/* 2255 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2257 */       GDMsgLogger.addError(msg);
/* 2258 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2261 */     return list;
/*      */   }
/*      */   
/*      */   public static List<DBItem> getBlueprints() {
/* 2265 */     List<DBItem> list = new LinkedList<>();
/*      */     
/* 2267 */     String command = "SELECT * FROM GD_ITEM WHERE ITEM_CLASS = 'ItemArtifactFormula' AND (ITEM_ID NOT LIKE '%/craft_random%' AND  ITEM_ID NOT LIKE '%test%' AND  ITEM_ID NOT LIKE 'records/mccm/%' AND  ITEM_ID NOT LIKE 'records/apoc/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/cat/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/d2/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/d3/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/doh/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/ncff/items/crafting/blueprints/%' AND  ITEM_ID NOT LIKE 'records/tq/items/crafting/blueprints/%')";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2281 */     try(Connection conn = GDDBData.getConnection(); 
/* 2282 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*      */       
/* 2284 */       try (ResultSet rs = ps.executeQuery()) {
/* 2285 */         list = wrap(rs);
/*      */         
/* 2287 */         conn.commit();
/*      */       }
/* 2289 */       catch (SQLException ex) {
/* 2290 */         throw ex;
/*      */       }
/*      */     
/* 2293 */     } catch (SQLException ex) {
/* 2294 */       Object[] args = { "Blueprints", "GD_ITEM" };
/* 2295 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2297 */       GDMsgLogger.addError(msg);
/* 2298 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2301 */     return list;
/*      */   }
/*      */   
/*      */   private static List<DBItem> getByItemCriteria(SelectionCriteria criteria) {
/* 2305 */     List<String> itemsAll = new LinkedList<>();
/* 2306 */     List<String> itemIDs = null;
/* 2307 */     List<String> itemSets = null;
/*      */     
/* 2309 */     itemIDs = ItemIDItemCombination.getItemIDs(criteria, CriteriaCombination.Soulbound.INCLUDED, CriteriaCombination.SC_HC.ALL, false, null);
/* 2310 */     itemSets = ItemIDItemSetCombination.getItemIDs(criteria, CriteriaCombination.Soulbound.INCLUDED, CriteriaCombination.SC_HC.ALL, false, null);
/*      */     
/* 2312 */     mergeItemIDs(itemsAll, itemIDs);
/* 2313 */     mergeItemIDs(itemsAll, itemSets);
/*      */     
/* 2315 */     List<DBItem> listAll = getByItemIDs(itemsAll);
/*      */     
/* 2317 */     return listAll;
/*      */   }
/*      */   
/*      */   public static List<DBItem> getByCriteria(SelectionCriteria criteria) {
/* 2321 */     if (criteria.itemIDs != null && !criteria.itemIDs.isEmpty()) {
/* 2322 */       return getByItemIDs(criteria.itemIDs);
/*      */     }
/*      */     
/* 2325 */     return getByItemCriteria(criteria);
/*      */   }
/*      */   
/*      */   private static void mergeItemIDs(List<String> listAll, List<String> list) {
/* 2329 */     if (list == null)
/*      */       return; 
/* 2331 */     for (String s : list) {
/* 2332 */       boolean found = false;
/* 2333 */       for (String sRec : listAll) {
/* 2334 */         if (sRec.equals(s)) {
/* 2335 */           found = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */       
/* 2341 */       if (!found) listAll.add(s); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void addSingleCriteriaCombo(List<DBItem> listAll, PreparedStatement ps, String command, ItemCriteriaCombination combo) {
/* 2346 */     List<DBItem> list = getBySingleCriteriaCombo(ps, command, combo);
/*      */     
/* 2348 */     if (list != null && 
/* 2349 */       !list.isEmpty()) listAll.addAll(list);
/*      */   
/*      */   }
/*      */   
/*      */   private static List<DBItem> getBySingleCriteriaCombo(PreparedStatement ps, String command, ItemCriteriaCombination combo) {
/* 2354 */     List<DBItem> list = new LinkedList<>();
/*      */     
/*      */     try {
/* 2357 */       combo.fillItemStatement(ps);
/*      */       
/* 2359 */       try (ResultSet rs = ps.executeQuery()) {
/* 2360 */         list = wrap(rs);
/*      */       }
/* 2362 */       catch (SQLException ex) {
/* 2363 */         throw ex;
/*      */       }
/*      */     
/* 2366 */     } catch (SQLException ex) {
/* 2367 */       Object[] args = { command, combo.determineItemParameters() };
/* 2368 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_SELECT_FAILED", args);
/*      */       
/* 2370 */       GDMsgLogger.addError(msg);
/*      */     } 
/*      */     
/* 2373 */     return list;
/*      */   }
/*      */   
/*      */   private static void addItemIDsSingleCriteriaCombo(List<String> listAll, PreparedStatement ps, String command, ItemCriteriaCombination combo) {
/* 2377 */     List<String> list = getItemIDsBySingleCriteriaCombo(ps, command, combo);
/*      */     
/* 2379 */     if (list != null && 
/* 2380 */       !list.isEmpty()) listAll.addAll(list);
/*      */   
/*      */   }
/*      */   
/*      */   private static List<String> getItemIDsBySingleCriteriaCombo(PreparedStatement ps, String command, ItemCriteriaCombination combo) {
/* 2385 */     List<String> list = new LinkedList<>();
/*      */     
/*      */     try {
/* 2388 */       combo.fillItemStatement(ps);
/*      */       
/* 2390 */       try (ResultSet rs = ps.executeQuery()) {
/* 2391 */         list = AbstractItemCombination.wrapString(rs, 1);
/*      */       }
/* 2393 */       catch (SQLException ex) {
/* 2394 */         throw ex;
/*      */       }
/*      */     
/* 2397 */     } catch (SQLException ex) {
/* 2398 */       Object[] args = { command, combo.determineItemParameters() };
/* 2399 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_SELECT_FAILED", args);
/*      */       
/* 2401 */       GDMsgLogger.addError(msg);
/*      */     } 
/*      */     
/* 2404 */     return list;
/*      */   }
/*      */   
/*      */   private static void addSingleDamageCombo(List<String> listAll, PreparedStatement ps, String command, StatCriteriaCombination scc) throws SQLException {
/* 2408 */     int nextPos = scc.fillItemIDStatement(ps);
/*      */     
/* 2410 */     if (nextPos == -1)
/*      */       return; 
/* 2412 */     for (SelectionCriteria.StatInfo info : scc.getStatInfoList()) {
/* 2413 */       for (String statType : info.statTypes) {
/* 2414 */         ps.setString(nextPos, statType);
/*      */         
/* 2416 */         try (ResultSet rs = ps.executeQuery()) {
/* 2417 */           List<String> ids = AbstractItemCombination.wrapString(rs, 1);
/*      */           
/* 2419 */           for (String id : ids) {
/* 2420 */             boolean found = false;
/* 2421 */             for (String s : listAll) {
/* 2422 */               if (s.equals(id)) {
/* 2423 */                 found = true;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */             
/* 2429 */             if (!found) listAll.add(id);
/*      */           
/*      */           } 
/* 2432 */         } catch (SQLException ex) {
/* 2433 */           throw ex;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void createBonusRaceStats(DBStat stat) {
/* 2440 */     if (stat == null)
/* 2441 */       return;  if (this.statBonusRaces == null)
/* 2442 */       return;  if (this.statBonusRaces.isEmpty())
/*      */       return; 
/* 2444 */     this.stats.remove(stat);
/*      */     
/* 2446 */     List<DBStat> list = DBStat.createStatsFromRaceBonusList(stat, this.statBonusRaces);
/* 2447 */     this.stats.addAll(list);
/*      */   }
/*      */   
/*      */   private static List<DBItem> wrap(ResultSet rs) throws SQLException {
/* 2451 */     LinkedList<DBItem> list = new LinkedList<>();
/* 2452 */     Blob blob = null;
/*      */     
/* 2454 */     while (rs.next()) {
/* 2455 */       DBItem item = new DBItem();
/*      */       
/* 2457 */       item.itemID = rs.getString(1);
/*      */       
/* 2459 */       DBItem buff = hashBuffer.get(item.itemID);
/* 2460 */       if (buff != null) {
/* 2461 */         list.add(buff);
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 2466 */       item.itemClass = rs.getString(2);
/* 2467 */       item.armorClass = rs.getString(3);
/* 2468 */       item.artifactClass = rs.getString(4);
/*      */       
/* 2470 */       item.meshID = rs.getString(5);
/* 2471 */       item.baseTextureID = rs.getString(6);
/* 2472 */       item.bumpTextureID = rs.getString(7);
/* 2473 */       item.glowTextureID = rs.getString(8);
/* 2474 */       item.shaderID = rs.getString(9);
/* 2475 */       item.bitmapID = rs.getString(10);
/* 2476 */       blob = rs.getBlob(11);
/* 2477 */       if (blob == null) {
/* 2478 */         item.bitmap = null;
/*      */       } else {
/* 2480 */         item.bitmap = blob.getBytes(1L, (int)blob.length());
/*      */       } 
/*      */       
/* 2483 */       if (item.bitmap != null) {
/*      */         try {
/* 2485 */           item.image = DDSLoader.getImage(item.bitmap);
/*      */         }
/* 2487 */         catch (GDParseException ex) {
/* 2488 */           Object[] args = { item.itemID };
/* 2489 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_BITMAP_DECODE_FAILED", args);
/*      */           
/* 2491 */           GDMsgLogger.addError(msg);
/*      */           
/* 2493 */           item.image = null;
/*      */         } 
/*      */         
/* 2496 */         if (item.image != null) {
/* 2497 */           item.overlayImage = DDSLoader.getScaledImage(item.image, 16, 16);
/*      */         }
/*      */       } 
/*      */       
/* 2501 */       item.shardBitmapID = rs.getString(12);
/* 2502 */       blob = rs.getBlob(13);
/* 2503 */       if (blob == null) {
/* 2504 */         item.shardBitmap = null;
/*      */       } else {
/* 2506 */         item.shardBitmap = blob.getBytes(1L, (int)blob.length());
/*      */       } 
/*      */       
/* 2509 */       if (item.shardBitmap != null) {
/*      */         try {
/* 2511 */           item.shardImage = DDSLoader.getImage(item.shardBitmap);
/*      */         }
/* 2513 */         catch (GDParseException ex) {
/* 2514 */           Object[] args = { item.itemID };
/* 2515 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_BITMAP_DECODE_FAILED", args);
/*      */           
/* 2517 */           GDMsgLogger.addError(msg);
/*      */           
/* 2519 */           item.shardImage = null;
/*      */         } 
/*      */         
/* 2522 */         if (item.shardImage != null) {
/* 2523 */           item.overlayShard = DDSLoader.getScaledImage(item.shardImage, 16, 16);
/*      */         }
/*      */       } 
/*      */       
/* 2527 */       item.genderCode = rs.getInt(14);
/* 2528 */       item.name = rs.getString(15);
/* 2529 */       item.description = rs.getString(16);
/* 2530 */       item.qualityTag = rs.getString(17);
/* 2531 */       item.qualityText = rs.getString(18);
/* 2532 */       item.styleTag = rs.getString(19);
/* 2533 */       item.styleText = rs.getString(20);
/* 2534 */       item.nameFull = rs.getString(21);
/* 2535 */       item.rarity = rs.getString(22);
/* 2536 */       item.setID = rs.getString(23);
/* 2537 */       item.setName = rs.getString(24);
/* 2538 */       item.bonusAffixSetID = rs.getString(25);
/* 2539 */       item.itemSkillID = rs.getString(26);
/* 2540 */       item.itemSkillLevel = rs.getInt(27);
/* 2541 */       item.petBonusSkillID = rs.getString(28);
/* 2542 */       item.controllerID = rs.getString(29);
/* 2543 */       item.costFormulaSetID = rs.getString(30);
/* 2544 */       item.convertIn = rs.getString(31);
/* 2545 */       item.convertOut = rs.getString(32);
/* 2546 */       item.convertIn2 = rs.getString(33);
/* 2547 */       item.convertOut2 = rs.getString(34);
/* 2548 */       item.soulbound = rs.getBoolean(35);
/* 2549 */       item.hidePrefix = rs.getBoolean(36);
/* 2550 */       item.hideSuffix = rs.getBoolean(37);
/* 2551 */       item.questItem = rs.getBoolean(38);
/* 2552 */       item.cannotPickup = rs.getBoolean(39);
/* 2553 */       item.enemyOnly = rs.getBoolean(40);
/* 2554 */       item.itemLevel = rs.getInt(41);
/* 2555 */       item.reqLevel = rs.getInt(42);
/* 2556 */       item.reqDex = rs.getInt(43);
/* 2557 */       item.reqInt = rs.getInt(44);
/* 2558 */       item.reqStr = rs.getInt(45);
/* 2559 */       item.offensiveChance = rs.getInt(46);
/* 2560 */       item.retaliationChance = rs.getInt(47);
/* 2561 */       item.plusAllSkills = rs.getInt(48);
/* 2562 */       item.componentPieces = rs.getInt(49);
/* 2563 */       item.maxStackSize = rs.getInt(50);
/* 2564 */       item.scalePercent = rs.getInt(51);
/* 2565 */       item.slots.slotAxe1H = rs.getBoolean(52);
/* 2566 */       item.slots.slotAxe2H = rs.getBoolean(53);
/* 2567 */       item.slots.slotDagger1H = rs.getBoolean(54);
/* 2568 */       item.slots.slotMace1H = rs.getBoolean(55);
/* 2569 */       item.slots.slotMace2H = rs.getBoolean(56);
/* 2570 */       item.slots.slotScepter1H = rs.getBoolean(57);
/* 2571 */       item.slots.slotSpear1H = rs.getBoolean(58);
/* 2572 */       item.slots.slotStaff2H = rs.getBoolean(59);
/* 2573 */       item.slots.slotSword1H = rs.getBoolean(60);
/* 2574 */       item.slots.slotSword2H = rs.getBoolean(61);
/* 2575 */       item.slots.slotRanged1H = rs.getBoolean(62);
/* 2576 */       item.slots.slotRanged2H = rs.getBoolean(63);
/* 2577 */       item.slots.slotShield = rs.getBoolean(64);
/* 2578 */       item.slots.slotOffhand = rs.getBoolean(65);
/* 2579 */       item.slots.slotAmulet = rs.getBoolean(66);
/* 2580 */       item.slots.slotBelt = rs.getBoolean(67);
/* 2581 */       item.slots.slotMedal = rs.getBoolean(68);
/* 2582 */       item.slots.slotRing = rs.getBoolean(69);
/* 2583 */       item.slots.slotHead = rs.getBoolean(70);
/* 2584 */       item.slots.slotShoulders = rs.getBoolean(71);
/* 2585 */       item.slots.slotChest = rs.getBoolean(72);
/* 2586 */       item.slots.slotHands = rs.getBoolean(73);
/* 2587 */       item.slots.slotLegs = rs.getBoolean(74);
/* 2588 */       item.slots.slotFeet = rs.getBoolean(75);
/*      */       
/* 2590 */       item.stats = DBStat.getItem(item.itemID);
/* 2591 */       Collections.sort(item.stats);
/*      */       
/* 2593 */       DBStat stat = DBStat.getByType(item.stats, "racialBonusPercentDamage", 1);
/* 2594 */       if (stat != null) {
/* 2595 */         item.statBonusRaces = DBStatBonusRace.getItem(item.itemID);
/* 2596 */         item.createBonusRaceStats(stat);
/*      */       } 
/*      */       
/* 2599 */       DBStat.applyAttributeScale(item.stats, item.scalePercent);
/*      */       
/* 2601 */       item.bonuses = DBSkillBonus.getItem(item.itemID);
/* 2602 */       item.skillModifiers = DBSkillModifier.getItem(item.itemID);
/*      */       
/* 2604 */       if (item.setID != null) {
/* 2605 */         item.dbItemSet = DBItemSet.get(item.setID);
/*      */       }
/*      */       
/* 2608 */       if (item.bonusAffixSetID != null) {
/* 2609 */         item.bonusAffixSet = DBAffixSet.get(item.bonusAffixSetID);
/*      */       }
/*      */       
/* 2612 */       if (item.itemSkillID != null) {
/* 2613 */         item.dbItemSkill = DBSkill.get(item.itemSkillID);
/*      */       }
/*      */       
/* 2616 */       if (item.petBonusSkillID != null) {
/* 2617 */         item.dbPetBonusSkill = DBSkill.get(item.petBonusSkillID);
/*      */       }
/*      */       
/* 2620 */       if (item.controllerID != null) {
/* 2621 */         item.dbController = DBController.get(item.controllerID);
/*      */       }
/*      */ 
/*      */       
/* 2625 */       if (item.costFormulaSetID == null && 
/* 2626 */         ItemClass.usesCostFormula(item.itemClass)) {
/* 2627 */         item.costFormulaSetID = "records/game/itemcostformulas.dbr";
/*      */       }
/*      */ 
/*      */       
/* 2631 */       if (item.costFormulaSetID != null) {
/* 2632 */         item.dbFormulaSet = DBFormulaSet.get(item.costFormulaSetID);
/*      */         
/* 2634 */         if (item.dbFormulaSet != null) {
/* 2635 */           item.determineStatRequirements();
/*      */         }
/*      */       } 
/*      */       
/* 2639 */       item.craftable = DBLootTableItemAlloc.isCraftableItemID(item.itemID);
/* 2640 */       if (!item.craftable) {
/* 2641 */         item.craftable = DBItemCraft.isCraftableItemID(item.itemID);
/*      */       }
/*      */       
/* 2644 */       item.faction = DBFaction.getFactionTextByItemID(item.itemID);
/*      */       
/* 2646 */       list.add(item);
/* 2647 */       hashBuffer.put(item.itemID, item);
/*      */     } 
/*      */     
/* 2650 */     return list;
/*      */   }
/*      */   
/*      */   private static List<SetInfo> wrapSetInfo(ResultSet rs) throws SQLException {
/* 2654 */     LinkedList<SetInfo> list = new LinkedList<>();
/*      */     
/* 2656 */     while (rs.next()) {
/* 2657 */       SetInfo info = new SetInfo();
/*      */       
/* 2659 */       info.itemID = rs.getString(1);
/* 2660 */       info.rarity = rs.getString(2);
/* 2661 */       info.level = rs.getInt(3);
/*      */       
/* 2663 */       list.add(info);
/*      */     } 
/*      */     
/* 2666 */     return list;
/*      */   }
/*      */   
/*      */   public static List<ImageInfo> getImageInfos(String id) {
/* 2670 */     List<ImageInfo> list = new LinkedList<>();
/* 2671 */     Blob blob = null;
/*      */     
/* 2673 */     String command = "SELECT ITEM_ID, BITMAP_ID, BITMAP, SHARD_BITMAP_ID, SHARD_BITMAP FROM GD_ITEM WHERE ((BITMAP_ID LIKE '" + id + "%' AND BITMAP IS NULL) OR (SHARD_BITMAP_ID LIKE '" + id + "%' AND SHARD_BITMAP IS NULL))";
/*      */ 
/*      */ 
/*      */     
/* 2677 */     try(Connection conn = GDDBData.getConnection(); 
/* 2678 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 2679 */       try (ResultSet rs = ps.executeQuery()) {
/* 2680 */         while (rs.next()) {
/* 2681 */           ImageInfo info = new ImageInfo();
/*      */           
/* 2683 */           info.itemID = rs.getString(1);
/*      */           
/* 2685 */           info.bitmapID = rs.getString(2);
/* 2686 */           blob = rs.getBlob(3);
/* 2687 */           if (blob == null) {
/* 2688 */             info.bitmap = null;
/*      */           } else {
/* 2690 */             info.bitmap = blob.getBytes(1L, (int)blob.length());
/*      */           } 
/*      */           
/* 2693 */           info.shardBitmapID = rs.getString(4);
/* 2694 */           blob = rs.getBlob(5);
/* 2695 */           if (blob == null) {
/* 2696 */             info.shardBitmap = null;
/*      */           } else {
/* 2698 */             info.shardBitmap = blob.getBytes(1L, (int)blob.length());
/*      */           } 
/*      */           
/* 2701 */           list.add(info);
/*      */         } 
/*      */         
/* 2704 */         conn.commit();
/*      */       }
/* 2706 */       catch (Exception ex) {
/* 2707 */         throw ex;
/*      */       }
/*      */     
/* 2710 */     } catch (Exception ex) {
/* 2711 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2714 */     return list;
/*      */   }
/*      */   
/*      */   public static void deleteNoImageItems() {
/* 2718 */     String command = "DELETE FROM GD_ITEM WHERE BITMAP IS NULL";
/*      */     
/* 2720 */     try(Connection conn = GDDBData.getConnection(); 
/* 2721 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 2722 */       ps.execute();
/*      */       
/* 2724 */       conn.commit();
/*      */     }
/* 2726 */     catch (Exception ex) {
/* 2727 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void updateImageInfo(List<ImageInfo> list) {
/* 2732 */     if (list == null)
/*      */       return; 
/* 2734 */     String command = "UPDATE GD_ITEM SET BITMAP = ?, SHARD_BITMAP = ? WHERE ITEM_ID = ?";
/*      */ 
/*      */     
/* 2737 */     try (Connection conn = GDDBData.getConnection()) {
/* 2738 */       boolean auto = conn.getAutoCommit();
/* 2739 */       conn.setAutoCommit(false);
/*      */       
/* 2741 */       for (ImageInfo info : list) {
/* 2742 */         try (PreparedStatement ps = conn.prepareStatement(command)) {
/* 2743 */           if (info.bitmap != null || info.shardBitmap != null)
/*      */           {
/*      */             
/* 2746 */             if (info.bitmap == null) {
/* 2747 */               ps.setBlob(1, (Blob)null);
/*      */             } else {
/* 2749 */               ps.setBlob(1, new ByteArrayInputStream(info.bitmap));
/*      */             } 
/*      */             
/* 2752 */             if (info.shardBitmap == null) {
/* 2753 */               ps.setBlob(2, (Blob)null);
/*      */             } else {
/* 2755 */               ps.setBlob(2, new ByteArrayInputStream(info.shardBitmap));
/*      */             } 
/*      */             
/* 2758 */             ps.setString(3, info.itemID);
/*      */             
/* 2760 */             ps.executeUpdate();
/* 2761 */             ps.close();
/*      */             
/* 2763 */             conn.commit();
/*      */           }
/*      */         
/* 2766 */         } catch (SQLException ex) {
/* 2767 */           conn.rollback();
/*      */           
/* 2769 */           Object[] args = { info.itemID };
/* 2770 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_IN_ITEM_IMAGE_SIZE", args);
/*      */           
/* 2772 */           GDMsgLogger.addWarning(msg);
/*      */           
/* 2774 */           GDMsgLogger.addWarning(ex);
/*      */         }
/*      */       
/*      */       } 
/* 2778 */     } catch (SQLException ex) {
/* 2779 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   public DBItem() {}
/*      */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */