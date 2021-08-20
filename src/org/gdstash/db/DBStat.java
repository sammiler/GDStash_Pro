/*      */ package org.gdstash.db;
/*      */ 
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import org.gdstash.description.BonusDetail;
/*      */ import org.gdstash.description.TagInfo;
/*      */ import org.gdstash.description.TagInfoHashMap;
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
/*      */ public class DBStat
/*      */   implements Cloneable, Comparable<DBStat>
/*      */ {
/*      */   public static final String TABLE_NAME_ITEM = "GD_ITEM_STAT";
/*      */   public static final String TABLE_NAME_ITEMSET = "GD_ITEMSET_STAT";
/*      */   public static final String TABLE_NAME_AFFIX = "GD_AFFIX_STAT";
/*      */   public static final String TABLE_NAME_SKILL = "GD_SKILL_STAT";
/*      */   public static final String FIELD_ID_ITEM = "ITEM_ID";
/*      */   public static final String FIELD_ID_ITEMSET = "ITEMSET_ID";
/*      */   private static final String FIELD_ID_AFFIX = "AFFIX_ID";
/*      */   private static final String FIELD_ID_SKILL = "SKILL_ID";
/*      */   private static final int ROW_ITEM_ID = 1;
/*      */   private static final int ROW_STAT_TYPE = 2;
/*      */   private static final int ROW_SKILL_LEVEL = 3;
/*      */   private static final int ROW_FLAG_GLOBAL = 4;
/*      */   private static final int ROW_FLAG_XOR = 5;
/*      */   private static final int ROW_STAT_MIN = 6;
/*      */   private static final int ROW_STAT_MAX = 7;
/*      */   private static final int ROW_STAT_CHANCE = 8;
/*      */   private static final int ROW_MODIFIER = 9;
/*      */   private static final int ROW_MODIFIER_CHANCE = 10;
/*      */   private static final int ROW_DURATION_MIN = 11;
/*      */   private static final int ROW_DURATION_MAX = 12;
/*      */   private static final int ROW_DURATION_CHANCE = 13;
/*      */   private static final int ROW_DURATION_MODIFIER = 14;
/*      */   private static final int ROW_DURATION_MODIFIER_CHANCE = 15;
/*      */   private static final int ROW_MAX_RESIST = 16;
/*      */   public static final int GLOBAL_NONE = 0;
/*      */   public static final int GLOBAL_ITEM = 1;
/*      */   public static final int GLOBAL_COMPONENT = 2;
/*      */   public static final int GLOBAL_PREFIX = 3;
/*      */   public static final int GLOBAL_PREFIX_PET = 4;
/*      */   public static final int GLOBAL_SUFFIX = 5;
/*      */   public static final int GLOBAL_SUFFIX_PET = 6;
/*      */   public static final int GLOBAL_MODIFIER = 7;
/*      */   public static final int GLOBAL_COMPLETION = 8;
/*      */   public static final int GLOBAL_ENCHANTMENT = 9;
/*      */   public static final String TYPE_SKILL_TRIGGER_HEALTH_PCT = "lifeMonitorPercent";
/*      */   public static final String TYPE_SKILL_PET_SUMMON_INC = "petBurstSpawn";
/*      */   public static final String TYPE_SKILL_PET_LIMIT_INC = "petLimit";
/*      */   public static final String TYPE_SKILL_MAX_LEVEL_INC = "skillMaxLevel";
/*      */   public static final String TYPE_SKILL_SPARK_INC = "sparkMaxNumber";
/*      */   public static final String TYPE_SKILL_TARGET_INC = "skillTargetNumber";
/*      */   public static final String TYPE_SKILL_DURATION = "skillActiveDuration";
/*      */   public static final String TYPE_SKILL_COOLDOWN_RED_PCT = "skillCooldownReduction";
/*      */   public static final String TYPE_SKILL_RECHARGE_TIME = "skillCooldownTime";
/*      */   public static final String TYPE_SKILL_HEALTH = "skillLifeBonus";
/*      */   public static final String TYPE_SKILL_HEALTH_PCT = "skillLifePercent";
/*      */   public static final String TYPE_SKILL_MANA_COST = "skillManaCost";
/*      */   public static final String TYPE_SKILL_MANA_COST_RED_PCT = "skillManaCostReduction";
/*      */   public static final String TYPE_SKILL_TARGET_RADIUS = "skillTargetRadius";
/*      */   public static final String TYPE_SKILL_WEAPON_DAMAGE_PCT = "weaponDamagePct";
/*      */   public static final String TYPE_SKILL_DAMAGE_ABSORB_PCT = "damageAbsorptionPercent";
/*      */   public static final String TYPE_SHIELD_RECOVERY_TIME = "blockRecoveryTime";
/*      */   public static final String TYPE_CHAR_ARMOR_PHYSIQUE_RED_PCT = "characterArmorStrengthReqReduction";
/*      */   public static final String TYPE_CHAR_ATTACK_SPEED = "characterAttackSpeed";
/*      */   public static final String TYPE_CHAR_CONSTITUTION = "characterConstitution";
/*      */   public static final String TYPE_CHAR_DEFENSIVE_ABILITY = "characterDefensiveAbility";
/*      */   public static final String TYPE_CHAR_SHIELD_REC_RED_PCT = "characterDefensiveBlockRecoveryReduction";
/*      */   public static final String TYPE_CHAR_AVOID_PROJECTILE_PCT = "characterDeflectProjectile";
/*      */   public static final String TYPE_CHAR_CUNNING = "characterDexterity";
/*      */   public static final String TYPE_CHAR_AVOID_MELEE_PCT = "characterDodgePercent";
/*      */   public static final String TYPE_CHAR_ENERGY_ABSORB_PCT = "characterEnergyAbsorptionPercent";
/*      */   public static final String TYPE_CHAR_REQUIREMENT_RED_PCT = "characterGlobalReqReduction";
/*      */   public static final String TYPE_CHAR_HEAL_INC_PCT = "characterHealIncreasePercent";
/*      */   public static final String TYPE_CHAR_RANGED_CUNNING_RED_PCT = "characterHuntingDexterityReqReduction";
/*      */   public static final String TYPE_CHAR_INC_EXPERIENCE_PCT = "characterIncreasedExperience";
/*      */   public static final String TYPE_CHAR_SPIRIT = "characterIntelligence";
/*      */   public static final String TYPE_CHAR_JEWELRY_SPIRIT_RED_PCT = "characterJewelryIntelligenceReqReduction";
/*      */   public static final String TYPE_CHAR_HEALTH = "characterLife";
/*      */   public static final String TYPE_CHAR_HEALTH_REGEN = "characterLifeRegen";
/*      */   public static final String TYPE_CHAR_LIGHT_RADIUS_PCT = "characterLightRadius";
/*      */   public static final String TYPE_CHAR_MANA = "characterMana";
/*      */   public static final String TYPE_CHAR_MANA_LIMIT = "characterManaLimitReserve";
/*      */   public static final String TYPE_CHAR_MANA_RESERVE = "characterManaLimitReserveReduction";
/*      */   public static final String TYPE_CHAR_MANA_REGEN = "characterManaRegen";
/*      */   public static final String TYPE_CHAR_MELEE_CUNNING_RED_PCT = "characterMeleeDexterityReqReduction";
/*      */   public static final String TYPE_CHAR_MELEE_PHYSIQUE_RED_PCT = "characterMeleeStrengthReqReduction";
/*      */   public static final String TYPE_CHAR_OFFENSIVE_ABILITY = "characterOffensiveAbility";
/*      */   public static final String TYPE_CHAR_RUN_SPEED = "characterRunSpeed";
/*      */   public static final String TYPE_CHAR_SHIELD_STRENGTH_RED_PCT = "characterShieldStrengthReqReduction";
/*      */   public static final String TYPE_CHAR_CAST_SPEED = "characterSpellCastSpeed";
/*      */   public static final String TYPE_CHAR_PHYSIQUE = "characterStrength";
/*      */   public static final String TYPE_CHAR_TOTAL_SPEED = "characterTotalSpeed";
/*      */   public static final String TYPE_CHAR_WEAPON_SPIRIT_RED_PCT = "characterWeaponIntelligenceReqReduction";
/*      */   public static final String TYPE_CHAR_WEAPON_PHYSIQUE_RED_PCT = "characterWeaponStrengthReqReduction";
/*      */   public static final String TYPE_CHAR_WEAPON_2H_PHYS_RED_PCT = "characterWeapon2HStrengthReqReduction";
/*      */   public static final String TYPE_CHAR_CONVERSION_PCT = "conversionPercentage";
/*      */   public static final String TYPE_CHAR_CONVERSION_PCT_2 = "conversionPercentage2";
/*      */   public static final String TYPE_CHAR_RACE_DAMAGE_PCT = "racialBonusPercentDamage";
/*      */   public static final String TYPE_WEAPON_PROJECTILE_PIERCE = "piercingProjectile";
/*      */   public static final String TYPE_CHAR_LEVEL_REQ_RED = "characterLevelReqReduction";
/*      */   public static final String TYPE_DEF_ABSORTPION = "defensiveAbsorption";
/*      */   public static final String TYPE_DEF_AETHER = "defensiveAether";
/*      */   public static final String TYPE_DEF_ALL = "defensiveAll";
/*      */   public static final String TYPE_DEF_BLEED = "defensiveBleeding";
/*      */   public static final String TYPE_DEF_BLOCK_CHANCE = "defensiveBlock";
/*      */   public static final String TYPE_DEF_BLOCK_AMOUNT = "defensiveBlockAmount";
/*      */   public static final String TYPE_DEF_BONUS_PROTECT = "defensiveBonusProtection";
/*      */   public static final String TYPE_DEF_CHAOS = "defensiveChaos";
/*      */   public static final String TYPE_DEF_COLD = "defensiveCold";
/*      */   public static final String TYPE_DEF_CONFUSION = "defensiveConfusion";
/*      */   public static final String TYPE_DEF_CONVERT = "defensiveConvert";
/*      */   public static final String TYPE_DEF_DISRUPTION = "defensiveDisruption";
/*      */   public static final String TYPE_DEF_ELEMENTAL = "defensiveElemental";
/*      */   public static final String TYPE_DEF_ELEMENTAL_RES = "defensiveElementalResistance";
/*      */   public static final String TYPE_DEF_FEAR = "defensiveFear";
/*      */   public static final String TYPE_DEF_FIRE = "defensiveFire";
/*      */   public static final String TYPE_DEF_FREEZE = "defensiveFreeze";
/*      */   public static final String TYPE_DEF_KNOCKDOWN = "defensiveKnockdown";
/*      */   public static final String TYPE_DEF_LIFE = "defensiveLife";
/*      */   public static final String TYPE_DEF_LIGHTNING = "defensiveLightning";
/*      */   public static final String TYPE_DEF_LIFE_PERC = "defensivePercentCurrentLife";
/*      */   public static final String TYPE_DEF_REFLECT_PERC = "defensivePercentReflectionResistance";
/*      */   public static final String TYPE_DEF_MANA_BURN = "defensiveManaBurnRatio";
/*      */   public static final String TYPE_DEF_PETRIFY = "defensivePetrify";
/*      */   public static final String TYPE_DEF_PHYSICAL = "defensivePhysical";
/*      */   public static final String TYPE_DEF_PIERCE = "defensivePierce";
/*      */   public static final String TYPE_DEF_POISON = "defensivePoison";
/*      */   public static final String TYPE_DEF_PROTECTION = "defensiveProtection";
/*      */   public static final String TYPE_DEF_REFLECT = "defensiveReflect";
/*      */   public static final String TYPE_DEF_SLEEP = "defensiveSleep";
/*      */   public static final String TYPE_DEF_SLOW_LIFE_LEECH = "defensiveSlowLifeLeach";
/*      */   public static final String TYPE_DEF_SLOW_MANA_LEECH = "defensiveSlowManaLeach";
/*      */   public static final String TYPE_DEF_STUN = "defensiveStun";
/*      */   public static final String TYPE_DEF_TAUNT = "defensiveTaunt";
/*      */   public static final String TYPE_DEF_TOTALSPEED_RES = "defensiveTotalSpeedResistance";
/*      */   public static final String TYPE_DEF_TRAP = "defensiveTrap";
/*      */   public static final String TYPE_OFF_AETHER = "offensiveAether";
/*      */   public static final String TYPE_OFF_BASE_AETHER = "offensiveBaseAether";
/*      */   public static final String TYPE_OFF_BASE_CHAOS = "offensiveBaseChaos";
/*      */   public static final String TYPE_OFF_BASE_COLD = "offensiveBaseCold";
/*      */   public static final String TYPE_OFF_BASE_FIRE = "offensiveBaseFire";
/*      */   public static final String TYPE_OFF_BASE_LIFE = "offensiveBaseLife";
/*      */   public static final String TYPE_OFF_BASE_LIGHTNING = "offensiveBaseLightning";
/*      */   public static final String TYPE_OFF_BASE_POISON = "offensiveBasePoison";
/*      */   public static final String TYPE_OFF_BONUS_PHYSICAL = "offensiveBonusPhysical";
/*      */   public static final String TYPE_OFF_CHAOS = "offensiveChaos";
/*      */   public static final String TYPE_OFF_COLD = "offensiveCold";
/*      */   public static final String TYPE_OFF_CONFUSION = "offensiveConfusion";
/*      */   public static final String TYPE_OFF_CONVERT = "offensiveConvert";
/*      */   public static final String TYPE_OFF_CRITICAL = "offensiveCritDamage";
/*      */   public static final String TYPE_OFF_DMGMULT = "offensiveDamageMult";
/*      */   public static final String TYPE_OFF_DISRUPTION = "offensiveDisruption";
/*      */   public static final String TYPE_OFF_ELEMENTAL = "offensiveElemental";
/*      */   public static final String TYPE_OFF_ELEM_DMG_RED_PERC = "offensiveElementalReductionPercent";
/*      */   public static final String TYPE_OFF_ELEM_RES_RED_ABS = "offensiveElementalResistanceReductionAbsolute";
/*      */   public static final String TYPE_OFF_ELEM_RES_RED_PERC = "offensiveElementalResistanceReductionPercent";
/*      */   public static final String TYPE_OFF_FEAR = "offensiveFear";
/*      */   public static final String TYPE_OFF_FIRE = "offensiveFire";
/*      */   public static final String TYPE_OFF_FREEZE = "offensiveFreeze";
/*      */   public static final String TYPE_OFF_FUMBLE = "offensiveFumble";
/*      */   public static final String TYPE_OFF_KNOCKDOWN = "offensiveKnockdown";
/*      */   public static final String TYPE_OFF_LIFE = "offensiveLife";
/*      */   public static final String TYPE_OFF_LIFE_LEECH = "offensiveLifeLeech";
/*      */   public static final String TYPE_OFF_LIGHTNING = "offensiveLightning";
/*      */   public static final String TYPE_OFF_MANA_BURN_RATIO = "offensiveManaBurnDamageRatio";
/*      */   public static final String TYPE_OFF_MANA_BURN_DRAIN = "offensiveManaBurnDrain";
/*      */   public static final String TYPE_OFF_LIFE_PERC = "offensivePercentCurrentLife";
/*      */   public static final String TYPE_OFF_PETRIFY = "offensivePetrify";
/*      */   public static final String TYPE_OFF_PHYSICAL = "offensivePhysical";
/*      */   public static final String TYPE_OFF_PHYS_DMG_RED_PERC = "offensivePhysicalReductionPercent";
/*      */   public static final String TYPE_OFF_PHYS_RES_RED_ABS = "offensivePhysicalResistanceReductionAbsolute";
/*      */   public static final String TYPE_OFF_PHYS_RES_RED_PERC = "offensivePhysicalResistanceReductionPercent";
/*      */   public static final String TYPE_OFF_PIERCE = "offensivePierce";
/*      */   public static final String TYPE_OFF_PIERCE_RATIO = "offensivePierceRatio";
/*      */   public static final String TYPE_OFF_POISON = "offensivePoison";
/*      */   public static final String TYPE_OFF_PROJECTILE_FUMBLE = "offensiveProjectileFumble";
/*      */   public static final String TYPE_OFF_SLEEP = "offensiveSleep";
/*      */   public static final String TYPE_OFF_SLOW_ATTACK = "offensiveSlowAttackSpeed";
/*      */   public static final String TYPE_OFF_SLOW_BLEED = "offensiveSlowBleeding";
/*      */   public static final String TYPE_OFF_SLOW_COLD = "offensiveSlowCold";
/*      */   public static final String TYPE_OFF_SLOW_DEFENSE = "offensiveSlowDefensiveAbility";
/*      */   public static final String TYPE_OFF_SLOW_DEF_RED = "offensiveSlowDefensiveReduction";
/*      */   public static final String TYPE_OFF_SLOW_FIRE = "offensiveSlowFire";
/*      */   public static final String TYPE_OFF_SLOW_LIFE = "offensiveSlowLife";
/*      */   public static final String TYPE_OFF_SLOW_LIFE_LEECH = "offensiveSlowLifeLeach";
/*      */   public static final String TYPE_OFF_SLOW_LIGHTNING = "offensiveSlowLightning";
/*      */   public static final String TYPE_OFF_SLOW_MANA_LEECH = "offensiveSlowManaLeach";
/*      */   public static final String TYPE_OFF_SLOW_OFFENSE = "offensiveSlowOffensiveAbility";
/*      */   public static final String TYPE_OFF_SLOW_OFF_RED = "offensiveSlowOffensiveReduction";
/*      */   public static final String TYPE_OFF_SLOW_PHYSICAL = "offensiveSlowPhysical";
/*      */   public static final String TYPE_OFF_SLOW_POISON = "offensiveSlowPoison";
/*      */   public static final String TYPE_OFF_SLOW_RUNSPEED = "offensiveSlowRunSpeed";
/*      */   public static final String TYPE_OFF_SLOW_TOTALSPEED = "offensiveSlowTotalSpeed";
/*      */   public static final String TYPE_OFF_STUN = "offensiveStun";
/*      */   public static final String TYPE_OFF_TAUNT = "offensiveTaunt";
/*      */   public static final String TYPE_OFF_TOTALDAMAGE = "offensiveTotalDamage";
/*      */   public static final String TYPE_OFF_TOTALDMGRED_PERC = "offensiveTotalDamageReductionPercent";
/*      */   public static final String TYPE_OFF_TOTALRESRED_ABS = "offensiveTotalResistanceReductionAbsolute";
/*      */   public static final String TYPE_OFF_TOTALRESRED_PERC = "offensiveTotalResistanceReductionPercent";
/*      */   public static final String TYPE_OFF_TRAP = "offensiveTrap";
/*      */   public static final String TYPE_RET_AETHER = "retaliationAether";
/*      */   public static final String TYPE_RET_CHAOS = "retaliationChaos";
/*      */   public static final String TYPE_RET_COLD = "retaliationCold";
/*      */   public static final String TYPE_RET_CONFUSION = "retaliationConfusion";
/*      */   public static final String TYPE_RET_CONVERT = "retaliationConvert";
/*      */   public static final String TYPE_RET_DAMAGE_PCT = "retaliationDamagePct";
/*      */   public static final String TYPE_RET_ELEMENTAL = "retaliationElemental";
/*      */   public static final String TYPE_RET_FEAR = "retaliationFear";
/*      */   public static final String TYPE_RET_FIRE = "retaliationFire";
/*      */   public static final String TYPE_RET_FREEZE = "retaliationFreeze";
/*      */   public static final String TYPE_RET_LIFE = "retaliationLife";
/*      */   public static final String TYPE_RET_LIGHTNING = "retaliationLightning";
/*      */   public static final String TYPE_RET_LIFE_PERC = "retaliationPercentCurrentLife";
/*      */   public static final String TYPE_RET_PETRIFY = "retaliationPetrify";
/*      */   public static final String TYPE_RET_PHYSICAL = "retaliationPhysical";
/*      */   public static final String TYPE_RET_PIERCE = "retaliationPierce";
/*      */   public static final String TYPE_RET_PIERCE_RATIO = "retaliationPierceRatio";
/*      */   public static final String TYPE_RET_POISON = "retaliationPoison";
/*      */   public static final String TYPE_RET_SLEEP = "retaliationSleep";
/*      */   public static final String TYPE_RET_SLOW_ATTACK = "retaliationSlowAttackSpeed";
/*      */   public static final String TYPE_RET_SLOW_BLEED = "retaliationSlowBleeding";
/*      */   public static final String TYPE_RET_SLOW_COLD = "retaliationSlowCold";
/*      */   public static final String TYPE_RET_SLOW_DEFENSE = "retaliationSlowDefensiveAbility";
/*      */   public static final String TYPE_RET_SLOW_FIRE = "retaliationSlowFire";
/*      */   public static final String TYPE_RET_SLOW_LIFE = "retaliationSlowLife";
/*      */   public static final String TYPE_RET_SLOW_LIFE_LEECH = "retaliationSlowLifeLeach";
/*      */   public static final String TYPE_RET_SLOW_LIGHTNING = "retaliationSlowLightning";
/*      */   public static final String TYPE_RET_SLOW_MANA_LEECH = "retaliationSlowManaLeach";
/*      */   public static final String TYPE_RET_SLOW_OFFENSE = "retaliationSlowOffensiveAbility";
/*      */   public static final String TYPE_RET_SLOW_OFF_RED = "retaliationSlowOffensiveReduction";
/*      */   public static final String TYPE_RET_SLOW_PHYSICAL = "retaliationSlowPhysical";
/*      */   public static final String TYPE_RET_SLOW_POISON = "retaliationSlowPoison";
/*      */   public static final String TYPE_RET_SLOW_RUNSPEED = "retaliationSlowRunSpeed";
/*      */   public static final String TYPE_RET_STUN = "retaliationStun";
/*      */   public static final String TYPE_RET_TOTALDAMAGE = "retaliationTotalDamage";
/*      */   public static final String TYPE_RET_TRAP = "retaliationTrap";
/*      */   public static final String STAT_SPEED_TOTAL = "STAT_SPEED_TOTAL";
/*      */   public static final String STAT_COOLDOWN_REDUCTION = "STAT_COOLDOWN_REDUCTION";
/*      */   public static final String STAT_SKILL_RECHARGE_REDUCTION = "STAT_SKILL_RECHARGE_REDUCTION";
/*      */   public static final String STAT_BASE_ATTACKSPEED = "STAT_BASE_ATTACKSPEED";
/*      */   public static final String STAT_SPEED_ATTACK = "STAT_SPEED_ATTACK";
/*      */   public static final String STAT_SPEED_CAST = "STAT_SPEED_CAST";
/*      */   public static final String STAT_SPEED_RUN = "STAT_SPEED_RUN";
/*      */   public static final String STAT_SHIELD_RECOVERY_TIME = "STAT_SHIELD_RECOVERY_TIME";
/*      */   public static final String STAT_SHIELD_RECOVERY_RED = "STAT_SHIELD_RECOVERY_RED";
/*      */   public static final String STAT_XP_INCREASE = "STAT_XP_INCREASE";
/*      */   public static final String STAT_MANACOST_REDUCTION = "STAT_MANACOST_REDUCTION";
/*      */   public static final String STAT_REQ_REDUCTION = "STAT_REQ_REDUCTION";
/*      */   public static final String STAT_RED_LEVEL_REQ = "STAT_RED_LEVEL_REQ";
/*      */   public static final String STAT_CUNNING = "STAT_CUNNING";
/*      */   public static final String STAT_PHYSIQUE = "STAT_PHYSIQUE";
/*      */   public static final String STAT_SPIRIT = "STAT_SPIRIT";
/*      */   public static final String STAT_OFF_ABIL_SHORT = "STAT_OFF_ABIL_SHORT";
/*      */   public static final String STAT_DEF_ABIL_SHORT = "STAT_DEF_ABIL_SHORT";
/*      */   public static final String STAT_SKILL_HEALTH = "STAT_SKILL_HEALTH";
/*      */   public static final String STAT_HEAL_INC = "STAT_HEAL_INC";
/*      */   public static final String STAT_REGEN_HEALTH = "STAT_REGEN_HEALTH";
/*      */   public static final String STAT_HEALTH = "STAT_HEALTH";
/*      */   public static final String STAT_REGEN_ENERGY = "STAT_REGEN_ENERGY";
/*      */   public static final String STAT_ENERGY_ABSORB = "STAT_ENERGY_ABSORB";
/*      */   public static final String STAT_ENERGY = "STAT_ENERGY";
/*      */   public static final String STAT_ENERGY_LIMIT = "STAT_ENERGY_LIMIT";
/*      */   public static final String STAT_ENERGY_RESERVE = "STAT_ENERGY_RESERVE";
/*      */   public static final String STAT_SKILL_RADIUS = "STAT_SKILL_RADIUS";
/*      */   public static final String STAT_SKILL_DURATION = "STAT_SKILL_DURATION";
/*      */   public static final String STAT_PET_SUMMON = "STAT_PET_SUMMON";
/*      */   public static final String STAT_PET_LIMIT = "STAT_PET_LIMIT";
/*      */   public static final String STAT_MAX_LEVEL_INC = "STAT_MAX_LEVEL_INC";
/*      */   public static final String STAT_TARGET_INC = "STAT_TARGET_INC";
/*      */   public static final String STAT_LIGHT_RADIUS = "STAT_LIGHT_RADIUS";
/*      */   public static final String STAT_RACE_DAMAGE = "TXT_UNI_RACE_DAMAGE";
/*      */   public static final String STAT_CONSTITUTION = "STAT_CONSTITUTION";
/*      */   public static final String STAT_AVOID_PROJECTILE = "STAT_AVOID_PROJECTILE";
/*      */   public static final String STAT_AVOID_MELEE = "STAT_AVOID_MELEE";
/*      */   public static final String STAT_RED_ARMOR_STR = "STAT_RED_ARMOR_STR";
/*      */   public static final String STAT_RED_RANGED_DEX = "STAT_RED_RANGED_DEX";
/*      */   public static final String STAT_RED_MELEE_DEX = "STAT_RED_MELEE_DEX";
/*      */   public static final String STAT_RED_MELEE_STR = "STAT_RED_MELEE_STR";
/*      */   public static final String STAT_RED_WEAPON_INT = "STAT_RED_WEAPON_INT";
/*      */   public static final String STAT_RED_WEAPON_STR = "STAT_RED_WEAPON_STR";
/*      */   public static final String STAT_RED_JEWELRY_INT = "STAT_RED_JEWELRY_INT";
/*      */   public static final String STAT_RED_SHIELD_STR = "STAT_RED_SHIELD_STR";
/*      */   public static final String STAT_WEAPON_PROJECTILE_PIERCE = "STAT_PROJECTILE_PIERCE";
/*      */   public static final int DMG_CLASS_DEFENSE = 1;
/*      */   public static final int DMG_CLASS_OFFENSE = 2;
/*      */   public static final int DMG_CLASS_RETALIATION = 3;
/*      */   public static final int STAT_PHYSIQUE_FLAT = 101;
/*      */   public static final int STAT_CUNNING_FLAT = 102;
/*      */   public static final int STAT_SPIRIT_FLAT = 103;
/*      */   public static final int STAT_DEF_ABIL_FLAT = 104;
/*      */   public static final int STAT_OFF_ABIL_FLAT = 105;
/*      */   public static final int STAT_HEALTH_FLAT = 106;
/*      */   public static final int STAT_HEALTH_REGEN_FLAT = 107;
/*      */   public static final int STAT_ENERGY_REGEN_FLAT = 108;
/*      */   public static final int STAT_PHYSIQUE_PERC = 201;
/*      */   public static final int STAT_CUNNING_PERC = 202;
/*      */   public static final int STAT_SPIRIT_PERC = 203;
/*      */   public static final int STAT_DEF_ABIL_PERC = 204;
/*      */   public static final int STAT_OFF_ABIL_PERC = 205;
/*      */   public static final int STAT_HEALTH_PERC = 206;
/*      */   public static final int STAT_HEALTH_REGEN_PERC = 207;
/*      */   public static final int STAT_ENERGY_REGEN_PERC = 208;
/*      */   public static final int STAT_BONUS_XP_PERC = 209;
/*      */   public static final int STAT_ATTACK_SPEED_PERC = 210;
/*      */   public static final int STAT_CAST_SPEED_PERC = 211;
/*      */   public static final int STAT_RUN_SPEED_PERC = 212;
/*      */   public static final int STAT_TOTAL_SPEED_PERC = 213;
/*      */   public static final int STAT_COOLDOWN_RED_PERC = 214;
/*      */   public static final int STAT_SHIELD_RECOVERY_RED_PERC = 215;
/*      */   public static final int STAT_CHANCE_AVOID_MELEE_PERC = 216;
/*      */   public static final int STAT_CHANCE_AVOID_PROJECTILE_PERC = 217;
/*      */   public static final int DMG_TYPE_FLAT_POISON = 301;
/*      */   public static final int DMG_TYPE_FLAT_AETHER = 302;
/*      */   public static final int DMG_TYPE_FLAT_CHAOS = 303;
/*      */   public static final int DMG_TYPE_FLAT_COLD = 304;
/*      */   public static final int DMG_TYPE_FLAT_ELEMENTAL = 305;
/*      */   public static final int DMG_TYPE_FLAT_FIRE = 306;
/*      */   public static final int DMG_TYPE_FLAT_LIFE = 307;
/*      */   public static final int DMG_TYPE_FLAT_LIGHTNING = 308;
/*      */   public static final int DMG_TYPE_FLAT_PHYSICAL = 309;
/*      */   public static final int DMG_TYPE_FLAT_PIERCE = 310;
/*      */   public static final int DMG_TYPE_DOT_POISON = 401;
/*      */   public static final int DMG_TYPE_DOT_COLD = 404;
/*      */   public static final int DMG_TYPE_DOT_FIRE = 406;
/*      */   public static final int DMG_TYPE_DOT_LIFE = 407;
/*      */   public static final int DMG_TYPE_DOT_LIGHTNING = 408;
/*      */   public static final int DMG_TYPE_DOT_PHYSICAL = 409;
/*      */   public static final int DMG_TYPE_DOT_PIERCE = 410;
/*      */   public static final int DMG_TYPE_SPEC_TOTAL = 501;
/*      */   public static final int DMG_TYPE_SPEC_CRITICAL = 502;
/*      */   public static final int DMG_TYPE_SPEC_LIFELEECH = 503;
/*      */   public static final int DMG_TYPE_SPEC_REFLECTION = 504;
/*      */   public static final int DMG_TYPE_SPEC_SLOW_ATTACK = 505;
/*      */   public static final int DMG_TYPE_SPEC_SLOW_TOTAL = 506;
/*      */   public static final int DMG_TYPE_SPEC_SLOW_DA = 507;
/*      */   public static final int DMG_TYPE_SPEC_SLOW_OA = 508;
/*      */   public static final int DMG_TYPE_SPEC_PROJECTILE_PIERCE = 509;
/*      */   public static final int DMG_TYPE_RES_RED_ABS_TOTAL = 510;
/*      */   public static final int DMG_TYPE_RES_RED_PERC_TOTAL = 511;
/*      */   public static final int DMG_TYPE_RES_RED_ABS_PHYSICAL = 512;
/*      */   public static final int DMG_TYPE_RES_RED_PERC_PHYSICAL = 513;
/*      */   public static final int DMG_TYPE_RES_RED_ABS_ELEMENTAL = 514;
/*      */   public static final int DMG_TYPE_RES_RED_PERC_ELEMENTAL = 515;
/*      */   public static final int DMG_TYPE_SPEC_RETAL_REFLECT = 516;
/*      */   public static final int DMG_TYPE_SPEC_MANA_COST = 517;
/*      */   public static final int DMG_TYPE_RESIST_STUN = 601;
/*      */   public static final int DMG_TYPE_RESIST_SLOW = 602;
/*      */   public static final int DMG_TYPE_RESIST_TRAP = 603;
/*      */   public static final int DMG_TYPE_RESIST_PETRIFY = 604;
/*      */   public static final int DMG_TYPE_RESIST_FREEZE = 605;
/*      */   private String itemID;
/*      */   private String type;
/*      */   private int skillLevel;
/*      */   private boolean global;
/*      */   private boolean xor;
/*      */   private float statMin;
/*      */   private float statMax;
/*      */   private int statChance;
/*      */   private int statModifier;
/*      */   private int statModifierChance;
/*      */   private int durationMin;
/*      */   private int durationMax;
/*      */   private int durationChance;
/*      */   private int durationModifier;
/*      */   private int durationModifierChance;
/*      */   private int maxResist;
/*      */   private DBItem itemGlobal;
/*      */   private DBAffix affixGlobal;
/*      */   private int globalType;
/*      */   private boolean petStat;
/*      */   private DBStatBonusRace statBonusRace;
/*      */   
/*      */   public DBStat() {
/*  409 */     this.itemID = null;
/*  410 */     this.type = null;
/*  411 */     this.skillLevel = 0;
/*  412 */     this.global = false;
/*  413 */     this.xor = false;
/*  414 */     this.statMin = 0.0F;
/*  415 */     this.statMax = 0.0F;
/*  416 */     this.statChance = 0;
/*  417 */     this.statModifier = 0;
/*  418 */     this.statModifierChance = 0;
/*  419 */     this.durationMin = 0;
/*  420 */     this.durationMax = 0;
/*  421 */     this.durationChance = 0;
/*  422 */     this.durationModifier = 0;
/*  423 */     this.durationModifierChance = 0;
/*  424 */     this.maxResist = 0;
/*      */     
/*  426 */     this.itemGlobal = null;
/*  427 */     this.affixGlobal = null;
/*  428 */     this.globalType = 0;
/*      */     
/*  430 */     this.petStat = false;
/*      */     
/*  432 */     this.statBonusRace = null;
/*      */   }
/*      */   
/*      */   public DBStat(DBStat stat, DBStatBonusRace statBonusRace) {
/*  436 */     fillFromStat(stat);
/*      */     
/*  438 */     this.statBonusRace = statBonusRace;
/*      */   }
/*      */   
/*      */   private DBStat(DBStat stat) {
/*  442 */     fillFromStat(stat);
/*      */   }
/*      */ 
/*      */   
/*      */   public DBStat clone() {
/*  447 */     return new DBStat(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInitial() {
/*  453 */     if (this.global) return false; 
/*  454 */     if (this.xor) return false; 
/*  455 */     if (this.statMin != 0.0F) return false; 
/*  456 */     if (this.statMax != 0.0F) return false; 
/*  457 */     if (this.statChance != 0) return false; 
/*  458 */     if (this.statModifier != 0) return false; 
/*  459 */     if (this.statModifierChance != 0) return false; 
/*  460 */     if (this.durationMin != 0) return false; 
/*  461 */     if (this.durationMax != 0) return false; 
/*  462 */     if (this.durationChance != 0) return false; 
/*  463 */     if (this.durationModifier != 0) return false; 
/*  464 */     if (this.durationModifierChance != 0) return false; 
/*  465 */     if (this.maxResist != 0) return false;
/*      */     
/*  467 */     return true;
/*      */   }
/*      */   
/*      */   public void fillFromStat(DBStat stat) {
/*  471 */     this.itemID = stat.itemID;
/*  472 */     this.type = stat.type;
/*  473 */     this.skillLevel = stat.skillLevel;
/*  474 */     this.global = stat.global;
/*  475 */     this.xor = stat.xor;
/*  476 */     this.statMin = stat.statMin;
/*  477 */     this.statMax = stat.statMax;
/*  478 */     this.statChance = stat.statChance;
/*  479 */     this.statModifier = stat.statModifier;
/*  480 */     this.statModifierChance = stat.statModifierChance;
/*  481 */     this.durationMin = stat.durationMin;
/*  482 */     this.durationMax = stat.durationMax;
/*  483 */     this.durationChance = stat.durationChance;
/*  484 */     this.durationModifier = stat.durationModifier;
/*  485 */     this.durationModifierChance = stat.durationModifierChance;
/*  486 */     this.maxResist = stat.maxResist;
/*      */     
/*  488 */     this.itemGlobal = stat.itemGlobal;
/*  489 */     this.affixGlobal = stat.affixGlobal;
/*  490 */     this.globalType = stat.globalType;
/*      */     
/*  492 */     this.petStat = stat.petStat;
/*      */     
/*  494 */     if (stat.statBonusRace != null) {
/*  495 */       this.statBonusRace = stat.statBonusRace.clone();
/*      */     }
/*      */   }
/*      */   
/*      */   public static List<DBStat> createStatsFromRaceBonusList(DBStat base, List<DBStatBonusRace> list) {
/*  500 */     List<DBStat> stats = new LinkedList<>();
/*      */     
/*  502 */     if (list == null) {
/*  503 */       stats.add(base);
/*      */       
/*  505 */       return stats;
/*      */     } 
/*      */     
/*  508 */     if (list.isEmpty()) {
/*  509 */       stats.add(base);
/*      */       
/*  511 */       return stats;
/*      */     } 
/*      */     
/*  514 */     for (DBStatBonusRace br : list) {
/*  515 */       br = br.clone();
/*      */       
/*  517 */       DBStat stat = new DBStat(base, br);
/*      */       
/*  519 */       stats.add(stat);
/*      */     } 
/*      */     
/*  522 */     return stats;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/*  531 */     if (o == null) return false; 
/*  532 */     if (!o.getClass().equals(DBStat.class)) return false;
/*      */     
/*  534 */     DBStat stat = (DBStat)o;
/*      */     
/*  536 */     if (!this.itemID.equals(stat.itemID)) return false; 
/*  537 */     if (!this.type.equals(stat.type)) return false; 
/*  538 */     if (this.statBonusRace == null && stat.statBonusRace != null) return false; 
/*  539 */     if (this.statBonusRace != null && stat.statBonusRace == null) return false; 
/*  540 */     if (this.statBonusRace != null && 
/*  541 */       !this.statBonusRace.equals(stat.statBonusRace)) return false;
/*      */ 
/*      */     
/*  544 */     return (this.skillLevel == stat.skillLevel);
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  549 */     return this.itemID.hashCode() + this.type.hashCode() + this.skillLevel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(DBStat o) {
/*  557 */     if (!o.getClass().equals(DBStat.class)) {
/*  558 */       Object[] args = { DBStat.class, o.toString() };
/*  559 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_NOT_OF_TYPE", args);
/*      */       
/*  561 */       throw new ClassCastException(msg);
/*      */     } 
/*      */     
/*  564 */     DBStat stat = o;
/*      */     
/*  566 */     int comp = 0;
/*      */     
/*  568 */     comp = this.itemID.compareTo(stat.itemID);
/*  569 */     if (comp != 0) return comp;
/*      */     
/*  571 */     comp = this.type.compareTo(stat.type);
/*  572 */     if (comp != 0) return comp;
/*      */     
/*  574 */     return this.skillLevel - stat.skillLevel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getItemID() {
/*  582 */     return this.itemID;
/*      */   }
/*      */   
/*      */   public String getStatType() {
/*  586 */     return this.type;
/*      */   }
/*      */   
/*      */   public boolean isPetStat() {
/*  590 */     return this.petStat;
/*      */   }
/*      */   
/*      */   public int getSkillLevel() {
/*  594 */     return this.skillLevel;
/*      */   }
/*      */   
/*      */   public boolean isGlobal() {
/*  598 */     return this.global;
/*      */   }
/*      */   
/*      */   public boolean isXOR() {
/*  602 */     return this.xor;
/*      */   }
/*      */   
/*      */   public float getStatMin() {
/*  606 */     return this.statMin;
/*      */   }
/*      */   
/*      */   public float getStatMax() {
/*  610 */     return this.statMax;
/*      */   }
/*      */   
/*      */   public int getStatChance() {
/*  614 */     return this.statChance;
/*      */   }
/*      */   
/*      */   public int getStatModifier() {
/*  618 */     return this.statModifier;
/*      */   }
/*      */   
/*      */   public int getStatModifierChance() {
/*  622 */     return this.statModifierChance;
/*      */   }
/*      */   
/*      */   public int getMinDuration() {
/*  626 */     return this.durationMin;
/*      */   }
/*      */   
/*      */   public int getMaxDuration() {
/*  630 */     return this.durationMax;
/*      */   }
/*      */   
/*      */   public int getDurationChance() {
/*  634 */     return this.durationChance;
/*      */   }
/*      */   
/*      */   public int getDurationModifier() {
/*  638 */     return this.durationModifier;
/*      */   }
/*      */   
/*      */   public int getDurationModifierChance() {
/*  642 */     return this.durationModifierChance;
/*      */   }
/*      */   
/*      */   public int getMaxResist() {
/*  646 */     return this.maxResist;
/*      */   }
/*      */   
/*      */   public int getGlobalOffensePerc() {
/*  650 */     if (this.itemGlobal != null) return this.itemGlobal.getOffensiveChance(); 
/*  651 */     if (this.affixGlobal != null) return this.affixGlobal.getOffensiveChance();
/*      */     
/*  653 */     return -1;
/*      */   }
/*      */   
/*      */   public int getGlobalRetaliationPerc() {
/*  657 */     if (this.itemGlobal != null) return this.itemGlobal.getRetaliationChance(); 
/*  658 */     if (this.affixGlobal != null) return this.affixGlobal.getRetaliationChance();
/*      */     
/*  660 */     return -1;
/*      */   }
/*      */   
/*      */   public int getGlobalCategory() {
/*  664 */     return this.globalType;
/*      */   }
/*      */   
/*      */   public DBStatBonusRace getBonusRace() {
/*  668 */     return this.statBonusRace;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DBStat getDBStat(List<DBStat> dbStats, String search, int level) {
/*  676 */     DBStat bestFit = null;
/*      */ 
/*      */ 
/*      */     
/*  680 */     for (DBStat dbStat : dbStats) {
/*  681 */       if (dbStat == null || 
/*  682 */         !dbStat.getStatType().equals(search) || 
/*  683 */         dbStat.getSkillLevel() > level)
/*      */         continue; 
/*  685 */       if (bestFit == null) {
/*  686 */         bestFit = dbStat;
/*      */       }
/*  688 */       else if (bestFit.getSkillLevel() < dbStat.getSkillLevel()) {
/*  689 */         bestFit = dbStat;
/*      */       } 
/*      */ 
/*      */       
/*  693 */       if (dbStat.getSkillLevel() == level) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/*  698 */     return bestFit;
/*      */   }
/*      */ 
/*      */   
/*      */   public static List<DBStat> getStatsForExactLevel(List<DBStat> dbStats, int level) {
/*  703 */     List<DBStat> list = new LinkedList<>();
/*      */ 
/*      */ 
/*      */     
/*  707 */     for (DBStat dbStat : dbStats) {
/*  708 */       if (dbStat == null || 
/*  709 */         dbStat.getSkillLevel() != level)
/*      */         continue; 
/*  711 */       list.add(dbStat);
/*      */     } 
/*      */     
/*  714 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<DBStat> getStatsForLevel(List<DBStat> dbStats, int level) {
/*  720 */     List<DBStat> list = new LinkedList<>();
/*      */ 
/*      */ 
/*      */     
/*  724 */     for (DBStat dbStat : dbStats) {
/*  725 */       if (dbStat == null || 
/*  726 */         dbStat.getSkillLevel() > level)
/*      */         continue; 
/*  728 */       boolean found = false;
/*      */       
/*  730 */       Iterator<DBStat> iter = list.iterator();
/*  731 */       while (iter.hasNext()) {
/*  732 */         DBStat dbs = iter.next();
/*      */         
/*  734 */         if (!dbs.itemID.equals(dbStat.itemID) || 
/*  735 */           !dbs.type.equals(dbStat.type))
/*      */           continue; 
/*  737 */         found = true;
/*      */         
/*  739 */         if (dbs.skillLevel < dbStat.skillLevel) {
/*  740 */           iter.remove();
/*      */           
/*  742 */           found = false;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  748 */       if (!found) list.add(dbStat);
/*      */     
/*      */     } 
/*  751 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemID(String itemID) {
/*  759 */     this.itemID = itemID;
/*      */   }
/*      */   
/*      */   public void setStatType(String type) {
/*  763 */     this.type = type;
/*      */   }
/*      */   
/*      */   public void setPetStat(boolean petStat) {
/*  767 */     this.petStat = petStat;
/*      */   }
/*      */   
/*      */   public void setSkillLevel(int skillLevel) {
/*  771 */     this.skillLevel = skillLevel;
/*      */   }
/*      */   
/*      */   public void setGlobal(boolean global) {
/*  775 */     this.global = global;
/*      */   }
/*      */   
/*      */   public void setXOR(boolean xor) {
/*  779 */     this.xor = xor;
/*      */   }
/*      */   
/*      */   public void setStatMin(float statMin) {
/*  783 */     this.statMin = statMin;
/*      */   }
/*      */   
/*      */   public void setStatMax(float statMax) {
/*  787 */     this.statMax = statMax;
/*      */   }
/*      */   
/*      */   public void setStatChance(int statChance) {
/*  791 */     this.statChance = statChance;
/*      */   }
/*      */   
/*      */   public void setStatModifier(int statModifier) {
/*  795 */     this.statModifier = statModifier;
/*      */   }
/*      */   
/*      */   public void setStatModifierChance(int statModifierChance) {
/*  799 */     this.statModifierChance = statModifierChance;
/*      */   }
/*      */   
/*      */   public void setMinDuration(int durationMin) {
/*  803 */     this.durationMin = durationMin;
/*      */   }
/*      */   
/*      */   public void setMaxDuration(int durationMax) {
/*  807 */     this.durationMax = durationMax;
/*      */   }
/*      */   
/*      */   public void setDurationChance(int durationChance) {
/*  811 */     this.durationChance = durationChance;
/*      */   }
/*      */   
/*      */   public void setDurationModifier(int durationModifier) {
/*  815 */     this.durationModifier = durationModifier;
/*      */   }
/*      */   
/*      */   public void setDurationModifierChance(int durationModifierChance) {
/*  819 */     this.durationModifierChance = durationModifierChance;
/*      */   }
/*      */   
/*      */   public void setMaxResist(int maxResist) {
/*  823 */     this.maxResist = maxResist;
/*      */   }
/*      */   
/*      */   public void setBonusRace(DBStatBonusRace statBonusRace) {
/*  827 */     this.statBonusRace = statBonusRace;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void createItemTable(Connection conn) throws SQLException {
/*  835 */     createTable(conn, "GD_ITEM_STAT", "ITEM_ID");
/*      */   }
/*      */   
/*      */   public static void createItemSetTable(Connection conn) throws SQLException {
/*  839 */     createTable(conn, "GD_ITEMSET_STAT", "ITEMSET_ID");
/*      */   }
/*      */   
/*      */   public static void createAffixTable(Connection conn) throws SQLException {
/*  843 */     createTable(conn, "GD_AFFIX_STAT", "AFFIX_ID");
/*      */   }
/*      */   
/*      */   public static void createSkillTable(Connection conn) throws SQLException {
/*  847 */     createTable(conn, "GD_SKILL_STAT", "SKILL_ID");
/*      */   }
/*      */   
/*      */   private static void createTable(Connection conn, String tabName, String idName) throws SQLException {
/*  851 */     String dropTable = "DROP TABLE " + tabName;
/*  852 */     String createTable = "CREATE TABLE " + tabName + " (" + idName + "                VARCHAR(" + "256" + ") NOT NULL, STAT_TYPE                VARCHAR(" + "64" + ") NOT NULL, SKILL_LEVEL              INTEGER, FLAG_GLOBAL              BOOLEAN, FLAG_XOR                 BOOLEAN, STAT_MIN                 FLOAT,   STAT_MAX                 FLOAT,   STAT_CHANCE              INTEGER, MODIFIER                 INTEGER, MODIFIER_CHANCE          INTEGER, DURATION_MIN             INTEGER, DURATION_MAX             INTEGER, DURATION_CHANCE          INTEGER, DURATION_MODIFIER        INTEGER, DURATION_MODIFIER_CHANCE INTEGER, MAX_RESIST               INTEGER, PRIMARY KEY (" + idName + ", STAT_TYPE, SKILL_LEVEL))";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  871 */     try (Statement st = conn.createStatement()) {
/*  872 */       if (GDDBUtil.tableExists(conn, tabName)) {
/*  873 */         st.execute(dropTable);
/*      */       }
/*  875 */       st.execute(createTable);
/*      */       
/*  877 */       st.close();
/*      */       
/*  879 */       conn.commit();
/*      */     }
/*  881 */     catch (SQLException ex) {
/*  882 */       Object[] args = { tabName };
/*  883 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*      */       
/*  885 */       GDMsgLogger.addError(msg);
/*      */       
/*  887 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void deleteItem(Connection conn, String id) throws SQLException {
/*  892 */     delete(conn, "GD_ITEM_STAT", "ITEM_ID", id);
/*      */   }
/*      */   
/*      */   public static void deleteItemSet(Connection conn, String id) throws SQLException {
/*  896 */     delete(conn, "GD_ITEMSET_STAT", "ITEMSET_ID", id);
/*      */   }
/*      */   
/*      */   public static void deleteAffix(Connection conn, String id) throws SQLException {
/*  900 */     delete(conn, "GD_AFFIX_STAT", "AFFIX_ID", id);
/*      */   }
/*      */   
/*      */   public static void deleteSkill(Connection conn, String id) throws SQLException {
/*  904 */     delete(conn, "GD_SKILL_STAT", "SKILL_ID", id);
/*      */   }
/*      */   
/*      */   private static void delete(Connection conn, String tabName, String idName, String id) throws SQLException {
/*  908 */     String deleteEntry = "DELETE FROM " + tabName + " WHERE " + idName + " = ?";
/*      */     
/*  910 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/*  911 */       ps.setString(1, id);
/*  912 */       ps.executeUpdate();
/*  913 */       ps.close();
/*      */     }
/*  915 */     catch (SQLException ex) {
/*  916 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void insertItem(Connection conn, String id, List<DBStat> stats) throws SQLException {
/*  921 */     insert(conn, "GD_ITEM_STAT", id, stats);
/*      */   }
/*      */   
/*      */   public static void insertItemSet(Connection conn, String id, List<DBStat> stats) throws SQLException {
/*  925 */     insert(conn, "GD_ITEMSET_STAT", id, stats);
/*      */   }
/*      */   
/*      */   public static void insertAffix(Connection conn, String id, List<DBStat> stats) throws SQLException {
/*  929 */     insert(conn, "GD_AFFIX_STAT", id, stats);
/*      */   }
/*      */   
/*      */   public static void insertSkill(Connection conn, String id, List<DBStat> stats) throws SQLException {
/*  933 */     insert(conn, "GD_SKILL_STAT", id, stats);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void insert(Connection conn, String tabName, String id, List<DBStat> stats) throws SQLException {
/*  939 */     String insert = "INSERT INTO " + tabName + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */     
/*  941 */     if (stats == null)
/*  942 */       return;  if (stats.isEmpty())
/*      */       return; 
/*  944 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/*  945 */       for (DBStat stat : stats) {
/*  946 */         if (!stat.isInitial()) {
/*  947 */           ps.setString(1, id);
/*  948 */           ps.setString(2, stat.type);
/*  949 */           ps.setInt(3, stat.skillLevel);
/*  950 */           ps.setBoolean(4, stat.global);
/*  951 */           ps.setBoolean(5, stat.xor);
/*  952 */           ps.setFloat(6, stat.statMin);
/*  953 */           ps.setFloat(7, stat.statMax);
/*  954 */           ps.setInt(8, stat.statChance);
/*  955 */           ps.setInt(9, stat.statModifier);
/*  956 */           ps.setInt(10, stat.statModifierChance);
/*  957 */           ps.setInt(11, stat.durationMin);
/*  958 */           ps.setInt(12, stat.durationMax);
/*  959 */           ps.setInt(13, stat.durationChance);
/*  960 */           ps.setInt(14, stat.durationModifier);
/*  961 */           ps.setInt(15, stat.durationModifierChance);
/*  962 */           ps.setInt(16, stat.maxResist);
/*      */           
/*  964 */           ps.executeUpdate();
/*      */           
/*  966 */           ps.clearParameters();
/*      */         } 
/*      */       } 
/*      */       
/*  970 */       ps.close();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static List<DBStat> getItem(String id) throws SQLException {
/*  975 */     return get("GD_ITEM_STAT", "ITEM_ID", id);
/*      */   }
/*      */   
/*      */   public static List<DBStat> getItemSet(String id) throws SQLException {
/*  979 */     return get("GD_ITEMSET_STAT", "ITEMSET_ID", id);
/*      */   }
/*      */   
/*      */   public static List<DBStat> getAffix(String id) throws SQLException {
/*  983 */     return get("GD_AFFIX_STAT", "AFFIX_ID", id);
/*      */   }
/*      */   
/*      */   public static List<DBStat> getSkill(String id) throws SQLException {
/*  987 */     return get("GD_SKILL_STAT", "SKILL_ID", id);
/*      */   }
/*      */   
/*      */   private static List<DBStat> get(String tabName, String idName, String id) {
/*  991 */     List<DBStat> list = new LinkedList<>();
/*      */     
/*  993 */     String command = "SELECT * FROM " + tabName + " WHERE " + idName + " = ?";
/*      */     
/*  995 */     try(Connection conn = GDDBData.getConnection(); 
/*  996 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*  997 */       ps.setString(1, id);
/*      */       
/*  999 */       try (ResultSet rs = ps.executeQuery()) {
/* 1000 */         list = wrap(rs);
/*      */         
/* 1002 */         conn.commit();
/*      */       }
/* 1004 */       catch (SQLException ex) {
/* 1005 */         throw ex;
/*      */       }
/*      */     
/* 1008 */     } catch (SQLException ex) {
/* 1009 */       Object[] args = { id, tabName };
/* 1010 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 1012 */       GDMsgLogger.addError(msg);
/* 1013 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 1016 */     return list;
/*      */   }
/*      */   
/*      */   private static List<DBStat> wrap(ResultSet rs) throws SQLException {
/* 1020 */     LinkedList<DBStat> list = new LinkedList<>();
/*      */     
/* 1022 */     while (rs.next()) {
/* 1023 */       DBStat stat = new DBStat();
/*      */       
/* 1025 */       stat.itemID = rs.getString(1);
/* 1026 */       stat.type = rs.getString(2);
/* 1027 */       stat.skillLevel = rs.getInt(3);
/* 1028 */       stat.global = rs.getBoolean(4);
/* 1029 */       stat.xor = rs.getBoolean(5);
/* 1030 */       stat.statMin = rs.getFloat(6);
/* 1031 */       stat.statMax = rs.getFloat(7);
/* 1032 */       stat.statChance = rs.getInt(8);
/* 1033 */       stat.statModifier = rs.getInt(9);
/* 1034 */       stat.statModifierChance = rs.getInt(10);
/* 1035 */       stat.durationMin = rs.getInt(11);
/* 1036 */       stat.durationMax = rs.getInt(12);
/* 1037 */       stat.durationChance = rs.getInt(13);
/* 1038 */       stat.durationModifier = rs.getInt(14);
/* 1039 */       stat.durationModifierChance = rs.getInt(15);
/* 1040 */       stat.maxResist = rs.getInt(16);
/*      */       
/* 1042 */       list.add(stat);
/*      */     } 
/*      */     
/* 1045 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DBStat getByType(List<DBStat> stats, String statType, int level) {
/* 1053 */     DBStat stat = null;
/* 1054 */     int maxLevel = -1;
/*      */     
/* 1056 */     if (stats == null) return null;
/*      */     
/* 1058 */     for (DBStat temp : stats) {
/* 1059 */       if (temp.getStatType().equals(statType)) {
/* 1060 */         if (temp.skillLevel <= level && temp.skillLevel > maxLevel) {
/* 1061 */           stat = temp;
/* 1062 */           maxLevel = temp.skillLevel;
/*      */         } 
/*      */         
/* 1065 */         if (temp.skillLevel == level)
/*      */           break; 
/*      */       } 
/*      */     } 
/* 1069 */     return stat;
/*      */   }
/*      */   
/*      */   public static List<DBStat> getByLevel(List<DBStat> stats, int level) {
/* 1073 */     List<DBStat> list = new LinkedList<>();
/*      */     
/* 1075 */     if (stats == null) return list;
/*      */     
/* 1077 */     for (DBStat stat : stats) {
/* 1078 */       if (stat.skillLevel > level)
/*      */         continue; 
/* 1080 */       boolean found = false;
/*      */       
/* 1082 */       for (DBStat temp : list) {
/* 1083 */         if (temp.type.equals(stat.type) && temp
/* 1084 */           .isPetStat() == stat.isPetStat()) {
/* 1085 */           found = true;
/*      */           
/* 1087 */           if (temp.skillLevel < stat.skillLevel) {
/* 1088 */             temp.fillFromStat(stat);
/*      */           }
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */       
/* 1095 */       if (!found) list.add(stat.clone());
/*      */     
/*      */     } 
/* 1098 */     return list;
/*      */   }
/*      */   
/*      */   public static void applyAttributeScale(List<DBStat> stats, int scale) {
/* 1102 */     if (stats == null)
/* 1103 */       return;  if (scale == 0)
/*      */       return; 
/* 1105 */     float factor = (100 + scale) / 100.0F;
/*      */     
/* 1107 */     for (DBStat stat : stats) {
/* 1108 */       TagInfo info = TagInfoHashMap.getInfo(stat.getStatType());
/*      */       
/* 1110 */       if (info == null)
/*      */         continue; 
/* 1112 */       if (info.isScaling()) {
/* 1113 */         stat.statMin = (int)(stat.statMin * factor);
/* 1114 */         stat.statMax = (int)(stat.statMax * factor);
/* 1115 */         stat.statModifier = (int)(stat.statModifier * factor);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean statsMeetCriteria(List<DBStat> stats, SelectionCriteria criteria) {
/* 1122 */     if (criteria.combiMode == SelectionCriteria.CombinationMode.OR) return true;
/*      */     
/* 1124 */     boolean fitsAll = true;
/*      */     
/* 1126 */     for (SelectionCriteria.StatInfo statInfo : criteria.statInfos) {
/* 1127 */       boolean found = false;
/*      */       
/* 1129 */       if (statInfo.combo == SelectionCriteria.ComboType.OR) {
/*      */         continue;
/*      */       }
/*      */       
/* 1133 */       for (String statType : statInfo.statTypes) {
/* 1134 */         for (DBStat stat : stats) {
/* 1135 */           if (stat.getStatType().equals(statType)) {
/* 1136 */             if (statInfo.flat && stat.getStatMin() > 0.0F) {
/* 1137 */               found = true;
/*      */               
/*      */               break;
/*      */             } 
/*      */             
/* 1142 */             if (statInfo.percentage && stat.getStatModifier() > 0) {
/* 1143 */               found = true;
/*      */               
/*      */               break;
/*      */             } 
/*      */             
/* 1148 */             if (statInfo.maxResist && stat.getMaxResist() > 0) {
/* 1149 */               found = true;
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/* 1156 */         if (found) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */       
/* 1161 */       if (!found) {
/* 1162 */         fitsAll = false;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1168 */     return fitsAll;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(List<DBStat> list, DBItem item, int globalType, List<DBStat> addList) {
/* 1176 */     add(list, item, null, globalType, addList);
/*      */   }
/*      */   
/*      */   public static void add(List<DBStat> list, DBAffix affix, int globalType, List<DBStat> addList) {
/* 1180 */     add(list, null, affix, globalType, addList);
/*      */   }
/*      */   
/*      */   private static void add(List<DBStat> list, DBItem item, DBAffix affix, int globalType, List<DBStat> addList) {
/* 1184 */     if (addList == null)
/*      */       return; 
/* 1186 */     for (DBStat statAdd : addList) {
/*      */       
/* 1188 */       statAdd = statAdd.clone();
/*      */       
/* 1190 */       if (statAdd.global) {
/*      */ 
/*      */         
/* 1193 */         if (item != null) statAdd.itemGlobal = item; 
/* 1194 */         if (affix != null) statAdd.affixGlobal = affix; 
/* 1195 */         statAdd.globalType = globalType;
/*      */         
/* 1197 */         list.add(statAdd);
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1202 */       for (DBStat stat : list) {
/*      */         
/* 1204 */         if (stat.global)
/*      */           continue; 
/* 1206 */         boolean found = false;
/*      */         
/* 1208 */         if (statAdd.type.equals(stat.type)) {
/* 1209 */           if (!found && 
/* 1210 */             stat.type.equals("racialBonusPercentDamage")) {
/* 1211 */             if (stat.statBonusRace == null || 
/* 1212 */               !stat.statBonusRace.equals(statAdd.statBonusRace))
/*      */               continue; 
/* 1214 */             if (statAdd.statMin > 0.0F) {
/* 1215 */               stat.statMin += statAdd.statMin;
/*      */             }
/*      */             
/* 1218 */             statAdd.statMin = 0.0F;
/*      */             
/* 1220 */             found = true;
/*      */           } 
/*      */ 
/*      */           
/* 1224 */           if (!found) {
/* 1225 */             if (statAdd.statMin > 0.0F) {
/* 1226 */               if (stat.statMin == 0.0F) {
/*      */                 
/* 1228 */                 stat.statMin = statAdd.statMin;
/* 1229 */                 stat.statMax = statAdd.statMax;
/* 1230 */                 stat.statChance = statAdd.statChance;
/* 1231 */                 stat.durationMin = statAdd.durationMin;
/*      */                 
/* 1233 */                 statAdd.statMin = 0.0F;
/* 1234 */                 statAdd.statMax = 0.0F;
/* 1235 */                 statAdd.statChance = 0;
/* 1236 */                 statAdd.durationMin = 0;
/*      */               } else {
/* 1238 */                 boolean match = true;
/*      */                 
/* 1240 */                 if (statAdd.durationMin != stat.durationMin) match = false; 
/* 1241 */                 if (statAdd.statChance != stat.statChance) match = false;
/*      */                 
/* 1243 */                 if (match) {
/* 1244 */                   stat.statMin += statAdd.statMin;
/*      */                   
/* 1246 */                   if (stat.statMax > 0.0F) {
/* 1247 */                     if (statAdd.statMax > 0.0F) {
/* 1248 */                       stat.statMax += statAdd.statMax;
/*      */                     } else {
/* 1250 */                       stat.statMax += statAdd.statMin;
/*      */                     }
/*      */                   
/* 1253 */                   } else if (statAdd.statMax > 0.0F) {
/* 1254 */                     stat.statMax = stat.statMin + statAdd.statMax;
/*      */                   } 
/*      */ 
/*      */                   
/* 1258 */                   statAdd.statMin = 0.0F;
/* 1259 */                   statAdd.statMax = 0.0F;
/* 1260 */                   statAdd.statChance = 0;
/* 1261 */                   statAdd.durationMin = 0;
/*      */                 } 
/*      */               } 
/*      */             }
/*      */             
/* 1266 */             if (statAdd.statModifier > 0) {
/* 1267 */               if (stat.statModifier == 0) {
/*      */                 
/* 1269 */                 stat.statModifier = statAdd.statModifier;
/* 1270 */                 stat.statModifierChance = statAdd.statModifierChance;
/*      */                 
/* 1272 */                 statAdd.statModifier = 0;
/* 1273 */                 statAdd.statModifierChance = 0;
/*      */               } else {
/* 1275 */                 boolean match = true;
/*      */                 
/* 1277 */                 if (statAdd.statModifierChance != stat.statModifierChance) match = false;
/*      */                 
/* 1279 */                 if (match) {
/* 1280 */                   stat.statModifier += statAdd.statModifier;
/*      */                   
/* 1282 */                   statAdd.statModifier = 0;
/* 1283 */                   statAdd.statModifierChance = 0;
/*      */                 } 
/*      */               } 
/*      */             }
/*      */             
/* 1288 */             if (statAdd.maxResist > 0) {
/* 1289 */               stat.maxResist += statAdd.maxResist;
/*      */               
/* 1291 */               statAdd.maxResist = 0;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1297 */       if (!statAdd.isInitial() && 
/* 1298 */         !statAdd.global) list.add(statAdd);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BonusDetail getBonusDetail(DBStashItem item, int detailType, String prefix) {
/* 1308 */     BonusDetail bonus = null;
/*      */     
/* 1310 */     if (this.type == null) return bonus;
/*      */     
/* 1312 */     bonus = new BonusDetail(item, detailType, this, prefix);
/*      */     
/* 1314 */     return bonus;
/*      */   }
/*      */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBStat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */