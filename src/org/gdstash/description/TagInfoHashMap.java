/*     */ package org.gdstash.description;
/*     */ 
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public class TagInfoHashMap
/*     */ {
/*  18 */   public static TagInfoHashMap singleton = new TagInfoHashMap(); static {
/*  19 */     singleton.fillMap();
/*     */   }
/*     */   private ConcurrentHashMap<String, TagInfo> map;
/*     */   public static TagInfo getInfo(String key) {
/*  23 */     return singleton.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private TagInfoHashMap() {
/*  29 */     this.map = new ConcurrentHashMap<>();
/*     */   }
/*     */   
/*     */   private TagInfo get(String key) {
/*  33 */     return this.map.get(key);
/*     */   }
/*     */   
/*     */   private void fillMap() {
/*  37 */     TagInfo info = null;
/*     */ 
/*     */ 
/*     */     
/*  41 */     info = new TagInfo("DEF_REFLECT", 495);
/*  42 */     this.map.put("defensiveReflect", info);
/*     */     
/*  44 */     info = new TagInfo("DEF_BLOCK_CHANCE", 490, true);
/*  45 */     this.map.put("defensiveBlock", info);
/*     */     
/*  47 */     info = new TagInfo("STAT_SHIELD_RECOVERY_TIME", 485, TagInfo.ValueType.FLOAT, TagInfo.RandCategory.FIXED, true);
/*  48 */     this.map.put("blockRecoveryTime", info);
/*     */     
/*  50 */     info = new TagInfo("DEF_PROTECTION", 480, true);
/*  51 */     this.map.put("defensiveProtection", info);
/*     */     
/*  53 */     info = new TagInfo("DEF_PROTECTION", 475);
/*  54 */     this.map.put("defensiveBonusProtection", info);
/*     */     
/*  56 */     info = new TagInfo("DEF_ARMOR_ABSORPTION", 470);
/*  57 */     this.map.put("defensiveAbsorption", info);
/*     */     
/*  59 */     info = new TagInfo("DEF_BLOCK_AMOUNT", 465);
/*  60 */     this.map.put("defensiveBlockAmount", info);
/*     */ 
/*     */ 
/*     */     
/*  64 */     info = new TagInfo("DEF_ALL", 385);
/*  65 */     this.map.put("defensiveAll", info);
/*     */     
/*  67 */     info = new TagInfo("DEF_PHYSICAL", 380);
/*  68 */     this.map.put("defensivePhysical", info);
/*     */     
/*  70 */     info = new TagInfo("DEF_PIERCE", 370);
/*  71 */     this.map.put("defensivePierce", info);
/*     */     
/*  73 */     info = new TagInfo("DEF_ELEMENTAL", 360);
/*  74 */     this.map.put("defensiveElemental", info);
/*     */     
/*  76 */     info = new TagInfo("DEF_ELEMENTAL", 360);
/*  77 */     this.map.put("defensiveElementalResistance", info);
/*     */     
/*  79 */     info = new TagInfo("DEF_POISON", 355);
/*  80 */     this.map.put("defensivePoison", info);
/*     */     
/*  82 */     info = new TagInfo("DEF_AETHER", 350);
/*  83 */     this.map.put("defensiveAether", info);
/*     */     
/*  85 */     info = new TagInfo("DEF_CHAOS", 345);
/*  86 */     this.map.put("defensiveChaos", info);
/*     */     
/*  88 */     info = new TagInfo("DEF_COLD", 340);
/*  89 */     this.map.put("defensiveCold", info);
/*     */     
/*  91 */     info = new TagInfo("DEF_FIRE", 335);
/*  92 */     this.map.put("defensiveFire", info);
/*     */     
/*  94 */     info = new TagInfo("DEF_LIGHTNING", 330);
/*  95 */     this.map.put("defensiveLightning", info);
/*     */     
/*  97 */     info = new TagInfo("DEF_LIFE", 325);
/*  98 */     this.map.put("defensiveLife", info);
/*     */ 
/*     */ 
/*     */     
/* 102 */     info = new TagInfo("DEF_STUN", 95);
/* 103 */     this.map.put("defensiveStun", info);
/*     */     
/* 105 */     info = new TagInfo("DEF_FREEZE", 90);
/* 106 */     this.map.put("defensiveFreeze", info);
/*     */     
/* 108 */     info = new TagInfo("DEF_PETRIFY", 85);
/* 109 */     this.map.put("defensivePetrify", info);
/*     */     
/* 111 */     info = new TagInfo("DEF_SLEEP", 80);
/* 112 */     this.map.put("defensiveSleep", info);
/*     */     
/* 114 */     info = new TagInfo("DEF_TRAP", 75, TagInfo.FlatCategory.DURATION, TagInfo.RandCategory.FIXED);
/* 115 */     this.map.put("defensiveTrap", info);
/*     */     
/* 117 */     info = new TagInfo("DEF_DISRUPTION", 70);
/* 118 */     this.map.put("defensiveDisruption", info);
/*     */     
/* 120 */     info = new TagInfo("DEF_KNOCKDOWN", 65);
/* 121 */     this.map.put("defensiveKnockdown", info);
/*     */     
/* 123 */     info = new TagInfo("DEF_CONVERT", 60);
/* 124 */     this.map.put("defensiveConvert", info);
/*     */     
/* 126 */     info = new TagInfo("DEF_CONFUSION", 55);
/* 127 */     this.map.put("defensiveConfusion", info);
/*     */     
/* 129 */     info = new TagInfo("DEF_FEAR", 50);
/* 130 */     this.map.put("defensiveFear", info);
/*     */     
/* 132 */     info = new TagInfo("DEF_LIFE_PERC", 45);
/* 133 */     this.map.put("defensivePercentCurrentLife", info);
/*     */     
/* 135 */     info = new TagInfo("DEF_REFLECT_PERC", 40);
/* 136 */     this.map.put("defensivePercentReflectionResistance", info);
/*     */     
/* 138 */     info = new TagInfo("DEF_SLOW_LIFE_LEECH", 35);
/* 139 */     this.map.put("defensiveSlowLifeLeach", info);
/*     */     
/* 141 */     info = new TagInfo("DEF_BLEED", 30);
/* 142 */     this.map.put("defensiveBleeding", info);
/*     */     
/* 144 */     info = new TagInfo("DEF_TOTALSPEED_RES", 25);
/* 145 */     this.map.put("defensiveTotalSpeedResistance", info);
/*     */     
/* 147 */     info = new TagInfo("DEF_SLOW_MANA_LEECH", 20);
/* 148 */     this.map.put("defensiveSlowManaLeach", info);
/*     */     
/* 150 */     info = new TagInfo("DEF_MANA_BURN", 15);
/* 151 */     this.map.put("defensiveManaBurnRatio", info);
/*     */     
/* 153 */     info = new TagInfo("DEF_TAUNT", 10);
/* 154 */     this.map.put("defensiveTaunt", info);
/*     */ 
/*     */ 
/*     */     
/* 158 */     info = new TagInfo("OFF_WEAPON_DAMAGE", 398, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.SCALE_JITTER);
/* 159 */     this.map.put("weaponDamagePct", info);
/*     */     
/* 161 */     info = new TagInfo("DEF_DAMAGE_ABSORB", 397, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.SCALE_JITTER);
/* 162 */     this.map.put("damageAbsorptionPercent", info);
/*     */     
/* 164 */     info = new TagInfo("STAT_PROJECTILE_PIERCE", 396, TagInfo.FlatCategory.PERCENTAGE);
/* 165 */     this.map.put("piercingProjectile", info);
/*     */     
/* 167 */     info = new TagInfo("OFF_PHYSICAL", 395, true);
/* 168 */     this.map.put("offensivePhysical", info);
/*     */     
/* 170 */     info = new TagInfo("OFF_PIERCE_RATIO", 390, TagInfo.FlatCategory.PERCENTAGE, true);
/* 171 */     this.map.put("offensivePierceRatio", info);
/*     */     
/* 173 */     info = new TagInfo("OFF_POISON", 385, true);
/* 174 */     this.map.put("offensiveBasePoison", info);
/*     */     
/* 176 */     info = new TagInfo("OFF_AETHER", 380, true);
/* 177 */     this.map.put("offensiveBaseAether", info);
/*     */     
/* 179 */     info = new TagInfo("OFF_CHAOS", 375, true);
/* 180 */     this.map.put("offensiveBaseChaos", info);
/*     */     
/* 182 */     info = new TagInfo("OFF_COLD", 370, true);
/* 183 */     this.map.put("offensiveBaseCold", info);
/*     */     
/* 185 */     info = new TagInfo("OFF_FIRE", 365, true);
/* 186 */     this.map.put("offensiveBaseFire", info);
/*     */     
/* 188 */     info = new TagInfo("OFF_LIGHTNING", 360, true);
/* 189 */     this.map.put("offensiveBaseLightning", info);
/*     */     
/* 191 */     info = new TagInfo("OFF_LIFE", 355, true);
/* 192 */     this.map.put("offensiveBaseLife", info);
/*     */     
/* 194 */     info = new TagInfo("OFF_LIFE_LEECH", 350, TagInfo.FlatCategory.PERCENTAGE, true, TagInfo.RandCategory.FIXED);
/* 195 */     this.map.put("offensiveLifeLeech", info);
/*     */     
/* 197 */     info = new TagInfo("OFF_TOTALDAMAGE", 345, TagInfo.RandCategory.SCALE_JITTER);
/* 198 */     this.map.put("offensiveTotalDamage", info);
/*     */     
/* 200 */     info = new TagInfo("OFF_PHYSICAL_BONUS", 340, TagInfo.RandCategory.SCALE_JITTER);
/* 201 */     this.map.put("offensiveBonusPhysical", info);
/*     */     
/* 203 */     info = new TagInfo("RET_DAMAGE_PCT", 338, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.SCALE_JITTER);
/* 204 */     this.map.put("retaliationDamagePct", info);
/*     */     
/* 206 */     info = new TagInfo("OFF_PIERCE", 335, TagInfo.RandCategory.SCALE_JITTER);
/* 207 */     this.map.put("offensivePierce", info);
/*     */     
/* 209 */     info = new TagInfo("OFF_ELEMENTAL", 330, TagInfo.RandCategory.SCALE_JITTER);
/* 210 */     this.map.put("offensiveElemental", info);
/*     */     
/* 212 */     info = new TagInfo("OFF_POISON", 325, TagInfo.RandCategory.SCALE_JITTER);
/* 213 */     this.map.put("offensivePoison", info);
/*     */     
/* 215 */     info = new TagInfo("OFF_AETHER", 320, TagInfo.RandCategory.SCALE_JITTER);
/* 216 */     this.map.put("offensiveAether", info);
/*     */     
/* 218 */     info = new TagInfo("OFF_CHAOS", 315, TagInfo.RandCategory.SCALE_JITTER);
/* 219 */     this.map.put("offensiveChaos", info);
/*     */     
/* 221 */     info = new TagInfo("OFF_COLD", 310, TagInfo.RandCategory.SCALE_JITTER);
/* 222 */     this.map.put("offensiveCold", info);
/*     */     
/* 224 */     info = new TagInfo("OFF_FIRE", 308, TagInfo.RandCategory.SCALE_JITTER);
/* 225 */     this.map.put("offensiveFire", info);
/*     */     
/* 227 */     info = new TagInfo("OFF_LIGHTNING", 306, TagInfo.RandCategory.SCALE_JITTER);
/* 228 */     this.map.put("offensiveLightning", info);
/*     */     
/* 230 */     info = new TagInfo("OFF_LIFE", 304, TagInfo.RandCategory.SCALE_JITTER);
/* 231 */     this.map.put("offensiveLife", info);
/*     */     
/* 233 */     info = new TagInfo("OFF_CRITICAL", 302, TagInfo.RandCategory.JITTER);
/* 234 */     this.map.put("offensiveCritDamage", info);
/*     */ 
/*     */ 
/*     */     
/* 238 */     info = new TagInfo("OFF_SLOW_LIFE_LEECH", 295, TagInfo.RandCategory.SCALE_JITTER);
/* 239 */     this.map.put("offensiveSlowLifeLeach", info);
/*     */     
/* 241 */     info = new TagInfo("OFF_SLOW_PHYSICAL", 290, TagInfo.RandCategory.SCALE_JITTER);
/* 242 */     this.map.put("offensiveSlowPhysical", info);
/*     */     
/* 244 */     info = new TagInfo("OFF_SLOW_BLEED", 285, TagInfo.RandCategory.SCALE_JITTER);
/* 245 */     this.map.put("offensiveSlowBleeding", info);
/*     */     
/* 247 */     info = new TagInfo("OFF_SLOW_COLD", 280, TagInfo.RandCategory.SCALE_JITTER);
/* 248 */     this.map.put("offensiveSlowCold", info);
/*     */     
/* 250 */     info = new TagInfo("OFF_SLOW_FIRE", 275, TagInfo.RandCategory.SCALE_JITTER);
/* 251 */     this.map.put("offensiveSlowFire", info);
/*     */     
/* 253 */     info = new TagInfo("OFF_SLOW_LIGHTNING", 270, TagInfo.RandCategory.SCALE_JITTER);
/* 254 */     this.map.put("offensiveSlowLightning", info);
/*     */     
/* 256 */     info = new TagInfo("OFF_SLOW_POISON", 265, TagInfo.RandCategory.SCALE_JITTER);
/* 257 */     this.map.put("offensiveSlowPoison", info);
/*     */     
/* 259 */     info = new TagInfo("OFF_SLOW_LIFE", 260, TagInfo.RandCategory.SCALE_JITTER);
/* 260 */     this.map.put("offensiveSlowLife", info);
/*     */     
/* 262 */     info = new TagInfo("OFF_LIFE_PERC", 255, TagInfo.FlatCategory.PERCENTAGE);
/* 263 */     this.map.put("offensivePercentCurrentLife", info);
/*     */ 
/*     */ 
/*     */     
/* 267 */     info = new TagInfo("OFF_TOTALDMGRED", 195, TagInfo.FlatCategory.PERCENTAGE);
/* 268 */     this.map.put("offensiveTotalDamageReductionPercent", info);
/*     */     
/* 270 */     info = new TagInfo("OFF_SLOW_TOTALSPEED", 190, TagInfo.FlatCategory.PERCENTAGE);
/* 271 */     this.map.put("offensiveSlowTotalSpeed", info);
/*     */     
/* 273 */     info = new TagInfo("OFF_DMGMULT", 188, TagInfo.FlatCategory.ABSOLUTE);
/* 274 */     this.map.put("offensiveDamageMult", info);
/*     */     
/* 276 */     info = new TagInfo("OFF_SLOW_ATTACK", 185, TagInfo.FlatCategory.PERCENTAGE);
/* 277 */     this.map.put("offensiveSlowAttackSpeed", info);
/*     */     
/* 279 */     info = new TagInfo("OFF_SLOW_OFF_RED", 180, TagInfo.RandCategory.SCALE_JITTER);
/* 280 */     this.map.put("offensiveSlowOffensiveReduction", info);
/*     */     
/* 282 */     info = new TagInfo("OFF_PHYS_DMG_RED", 175, TagInfo.FlatCategory.PERCENTAGE);
/* 283 */     this.map.put("offensivePhysicalReductionPercent", info);
/*     */     
/* 285 */     info = new TagInfo("OFF_ELEM_DMG_RED", 170, TagInfo.FlatCategory.PERCENTAGE);
/* 286 */     this.map.put("offensiveElementalReductionPercent", info);
/*     */     
/* 288 */     info = new TagInfo("OFF_SLOW_OFFENSE", 165, TagInfo.RandCategory.SCALE_JITTER);
/* 289 */     this.map.put("offensiveSlowOffensiveAbility", info);
/*     */     
/* 291 */     info = new TagInfo("OFF_SLOW_DEFENSE", 160, TagInfo.RandCategory.SCALE_JITTER);
/* 292 */     this.map.put("offensiveSlowDefensiveAbility", info);
/*     */     
/* 294 */     info = new TagInfo("OFF_SLOW_DEF_RED", 155, TagInfo.RandCategory.SCALE_JITTER);
/* 295 */     this.map.put("offensiveSlowDefensiveReduction", info);
/*     */     
/* 297 */     info = new TagInfo("OFF_SLOW_RUNSPEED", 150, TagInfo.FlatCategory.PERCENTAGE);
/* 298 */     this.map.put("offensiveSlowRunSpeed", info);
/*     */     
/* 300 */     info = new TagInfo("OFF_TOTALRESRED", 145, TagInfo.RandCategory.SCALE_JITTER);
/* 301 */     this.map.put("offensiveTotalResistanceReductionAbsolute", info);
/*     */     
/* 303 */     info = new TagInfo("OFF_TOTALRESRED", 140, TagInfo.FlatCategory.PERCENTAGE);
/* 304 */     this.map.put("offensiveTotalResistanceReductionPercent", info);
/*     */     
/* 306 */     info = new TagInfo("OFF_PHYS_RES_RED_ABS", 135, TagInfo.RandCategory.SCALE_JITTER);
/* 307 */     this.map.put("offensivePhysicalResistanceReductionAbsolute", info);
/*     */     
/* 309 */     info = new TagInfo("OFF_PHYS_RES_RED_ABS", 130, TagInfo.FlatCategory.PERCENTAGE);
/* 310 */     this.map.put("offensivePhysicalResistanceReductionPercent", info);
/*     */     
/* 312 */     info = new TagInfo("OFF_ELEM_RES_RED_ABS", 125, TagInfo.RandCategory.SCALE_JITTER);
/* 313 */     this.map.put("offensiveElementalResistanceReductionAbsolute", info);
/*     */     
/* 315 */     info = new TagInfo("OFF_ELEM_RES_RED_ABS", 120, TagInfo.FlatCategory.PERCENTAGE);
/* 316 */     this.map.put("offensiveElementalResistanceReductionPercent", info);
/*     */     
/* 318 */     info = new TagInfo("OFF_MANA_BURN_RATIO", 115, TagInfo.FlatCategory.PERCENTAGE);
/* 319 */     this.map.put("offensiveManaBurnDamageRatio", info);
/*     */     
/* 321 */     info = new TagInfo("OFF_MANA_BURN_DRAIN", 110, TagInfo.FlatCategory.PERCENTAGE);
/* 322 */     this.map.put("offensiveManaBurnDrain", info);
/*     */     
/* 324 */     info = new TagInfo("OFF_SLOW_MANA_LEECH", 105, TagInfo.RandCategory.SCALE_JITTER);
/* 325 */     this.map.put("offensiveSlowManaLeach", info);
/*     */ 
/*     */ 
/*     */     
/* 329 */     info = new TagInfo("OFF_STUN", 95, TagInfo.FlatCategory.DURATION);
/* 330 */     this.map.put("offensiveStun", info);
/*     */     
/* 332 */     info = new TagInfo("OFF_FREEZE", 90, TagInfo.FlatCategory.DURATION);
/* 333 */     this.map.put("offensiveFreeze", info);
/*     */     
/* 335 */     info = new TagInfo("OFF_PETRIFY", 85, TagInfo.FlatCategory.DURATION);
/* 336 */     this.map.put("offensivePetrify", info);
/*     */     
/* 338 */     info = new TagInfo("OFF_SLEEP", 80, TagInfo.FlatCategory.DURATION);
/* 339 */     this.map.put("offensiveSleep", info);
/*     */     
/* 341 */     info = new TagInfo("OFF_TRAP", 75, TagInfo.FlatCategory.DURATION);
/* 342 */     this.map.put("offensiveTrap", info);
/*     */     
/* 344 */     info = new TagInfo("OFF_DISRUPTION", 70, TagInfo.FlatCategory.DURATION);
/* 345 */     this.map.put("offensiveDisruption", info);
/*     */     
/* 347 */     info = new TagInfo("OFF_KNOCKDOWN", 65, TagInfo.FlatCategory.DURATION);
/* 348 */     this.map.put("offensiveKnockdown", info);
/*     */     
/* 350 */     info = new TagInfo("OFF_CONVERT", 60, TagInfo.FlatCategory.DURATION);
/* 351 */     this.map.put("offensiveConvert", info);
/*     */     
/* 353 */     info = new TagInfo("OFF_CONFUSION", 55, TagInfo.FlatCategory.DURATION);
/* 354 */     this.map.put("offensiveConfusion", info);
/*     */     
/* 356 */     info = new TagInfo("OFF_FEAR", 50, TagInfo.FlatCategory.DURATION);
/* 357 */     this.map.put("offensiveFear", info);
/*     */ 
/*     */ 
/*     */     
/* 361 */     info = new TagInfo("RET_TOTALDAMAGE", 390);
/* 362 */     this.map.put("retaliationTotalDamage", info);
/*     */     
/* 364 */     info = new TagInfo("RET_PHYSICAL", 380);
/* 365 */     this.map.put("retaliationPhysical", info);
/*     */     
/* 367 */     info = new TagInfo("RET_PIERCE", 370);
/* 368 */     this.map.put("retaliationPierce", info);
/*     */     
/* 370 */     info = new TagInfo("RET_ELEMENTAL", 360);
/* 371 */     this.map.put("retaliationElemental", info);
/*     */     
/* 373 */     info = new TagInfo("RET_POISON", 355);
/* 374 */     this.map.put("retaliationPoison", info);
/*     */     
/* 376 */     info = new TagInfo("RET_AETHER", 350);
/* 377 */     this.map.put("retaliationAether", info);
/*     */     
/* 379 */     info = new TagInfo("RET_CHAOS", 345);
/* 380 */     this.map.put("retaliationChaos", info);
/*     */     
/* 382 */     info = new TagInfo("RET_COLD", 340);
/* 383 */     this.map.put("retaliationCold", info);
/*     */     
/* 385 */     info = new TagInfo("RET_FIRE", 335);
/* 386 */     this.map.put("retaliationFire", info);
/*     */     
/* 388 */     info = new TagInfo("RET_LIGHTNING", 330);
/* 389 */     this.map.put("retaliationLightning", info);
/*     */     
/* 391 */     info = new TagInfo("RET_LIFE", 325);
/* 392 */     this.map.put("retaliationLife", info);
/*     */ 
/*     */ 
/*     */     
/* 396 */     info = new TagInfo("RET_SLOW_LIFE_LEECH", 295);
/* 397 */     this.map.put("retaliationSlowLifeLeach", info);
/*     */     
/* 399 */     info = new TagInfo("RET_SLOW_PHYSICAL", 290);
/* 400 */     this.map.put("retaliationSlowPhysical", info);
/*     */     
/* 402 */     info = new TagInfo("RET_SLOW_BLEED", 285);
/* 403 */     this.map.put("retaliationSlowBleeding", info);
/*     */     
/* 405 */     info = new TagInfo("RET_SLOW_COLD", 280);
/* 406 */     this.map.put("retaliationSlowCold", info);
/*     */     
/* 408 */     info = new TagInfo("RET_SLOW_FIRE", 275);
/* 409 */     this.map.put("retaliationSlowFire", info);
/*     */     
/* 411 */     info = new TagInfo("RET_SLOW_LIGHTNING", 270);
/* 412 */     this.map.put("retaliationSlowLightning", info);
/*     */     
/* 414 */     info = new TagInfo("RET_SLOW_POISON", 265);
/* 415 */     this.map.put("retaliationSlowPoison", info);
/*     */     
/* 417 */     info = new TagInfo("RET_SLOW_LIFE", 260);
/* 418 */     this.map.put("retaliationSlowLife", info);
/*     */     
/* 420 */     info = new TagInfo("RET_LIFE_PERC", 255, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 421 */     this.map.put("retaliationPercentCurrentLife", info);
/*     */ 
/*     */ 
/*     */     
/* 425 */     info = new TagInfo("RET_SLOW_ATTACK", 185, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 426 */     this.map.put("retaliationSlowAttackSpeed", info);
/*     */     
/* 428 */     info = new TagInfo("RET_SLOW_OFF_RED", 180);
/* 429 */     this.map.put("retaliationSlowOffensiveReduction", info);
/*     */     
/* 431 */     info = new TagInfo("RET_SLOW_OFFENSE", 170);
/* 432 */     this.map.put("retaliationSlowOffensiveAbility", info);
/*     */     
/* 434 */     info = new TagInfo("RET_SLOW_DEFENSE", 165);
/* 435 */     this.map.put("retaliationSlowDefensiveAbility", info);
/*     */     
/* 437 */     info = new TagInfo("RET_SLOW_RUNSPEED", 155, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 438 */     this.map.put("retaliationSlowRunSpeed", info);
/*     */ 
/*     */ 
/*     */     
/* 442 */     info = new TagInfo("RET_STUN", 95, TagInfo.FlatCategory.DURATION, TagInfo.RandCategory.FIXED);
/* 443 */     this.map.put("retaliationStun", info);
/*     */     
/* 445 */     info = new TagInfo("RET_FREEZE", 90, TagInfo.FlatCategory.DURATION, TagInfo.RandCategory.FIXED);
/* 446 */     this.map.put("retaliationFreeze", info);
/*     */     
/* 448 */     info = new TagInfo("RET_PETRIFY", 85, TagInfo.FlatCategory.DURATION, TagInfo.RandCategory.FIXED);
/* 449 */     this.map.put("retaliationPetrify", info);
/*     */     
/* 451 */     info = new TagInfo("RET_SLEEP", 80, TagInfo.FlatCategory.DURATION, TagInfo.RandCategory.FIXED);
/* 452 */     this.map.put("retaliationSleep", info);
/*     */     
/* 454 */     info = new TagInfo("RET_TRAP", 75, TagInfo.FlatCategory.DURATION, TagInfo.RandCategory.FIXED);
/* 455 */     this.map.put("retaliationTrap", info);
/*     */     
/* 457 */     info = new TagInfo("RET_CONVERT", 60, TagInfo.FlatCategory.DURATION, TagInfo.RandCategory.FIXED);
/* 458 */     this.map.put("retaliationConvert", info);
/*     */     
/* 460 */     info = new TagInfo("RET_CONFUSION", 55, TagInfo.FlatCategory.DURATION, TagInfo.RandCategory.FIXED);
/* 461 */     this.map.put("retaliationConfusion", info);
/*     */     
/* 463 */     info = new TagInfo("RET_FEAR", 50, TagInfo.FlatCategory.DURATION, TagInfo.RandCategory.FIXED);
/* 464 */     this.map.put("retaliationFear", info);
/*     */     
/* 466 */     info = new TagInfo("RET_SLOW_MANA_LEECH", 40);
/* 467 */     this.map.put("retaliationSlowManaLeach", info);
/*     */ 
/*     */ 
/*     */     
/* 471 */     info = new TagInfo("STAT_SPEED_TOTAL", 998);
/* 472 */     this.map.put("characterTotalSpeed", info);
/*     */     
/* 474 */     info = new TagInfo("STAT_COOLDOWN_REDUCTION", 996, TagInfo.FlatCategory.PERCENTAGE);
/* 475 */     this.map.put("skillCooldownReduction", info);
/*     */     
/* 477 */     info = new TagInfo("STAT_SKILL_RECHARGE_REDUCTION", 994, TagInfo.ValueType.FLOAT, TagInfo.RandCategory.FIXED);
/* 478 */     this.map.put("skillCooldownTime", info);
/*     */     
/* 480 */     info = new TagInfo("STAT_SPEED_ATTACK", 992);
/* 481 */     this.map.put("characterAttackSpeed", info);
/*     */     
/* 483 */     info = new TagInfo("STAT_SPEED_CAST", 990);
/* 484 */     this.map.put("characterSpellCastSpeed", info);
/*     */     
/* 486 */     info = new TagInfo("STAT_SPEED_RUN", 988, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 487 */     this.map.put("characterRunSpeed", info);
/*     */     
/* 489 */     info = new TagInfo("STAT_SHIELD_RECOVERY_RED", 988, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 490 */     this.map.put("characterDefensiveBlockRecoveryReduction", info);
/*     */     
/* 492 */     info = new TagInfo("STAT_XP_INCREASE", 898, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 493 */     this.map.put("characterIncreasedExperience", info);
/*     */     
/* 495 */     info = new TagInfo("STAT_REQ_REDUCTION", 888, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 496 */     this.map.put("characterGlobalReqReduction", info);
/*     */     
/* 498 */     info = new TagInfo("STAT_RED_LEVEL_REQ", 884, TagInfo.RandCategory.FIXED);
/* 499 */     this.map.put("characterLevelReqReduction", info);
/*     */     
/* 501 */     info = new TagInfo("STAT_RED_ARMOR_STR", 878, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 502 */     this.map.put("characterArmorStrengthReqReduction", info);
/*     */     
/* 504 */     info = new TagInfo("STAT_RED_WEAPON_STR", 868, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 505 */     this.map.put("characterWeaponStrengthReqReduction", info);
/*     */     
/* 507 */     info = new TagInfo("STAT_RED_WEAPON_INT", 856, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 508 */     this.map.put("characterWeaponStrengthReqReduction", info);
/*     */     
/* 510 */     info = new TagInfo("STAT_RED_RANGED_DEX", 848, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 511 */     this.map.put("characterHuntingDexterityReqReduction", info);
/*     */     
/* 513 */     info = new TagInfo("STAT_RED_MELEE_DEX", 838, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 514 */     this.map.put("characterMeleeDexterityReqReduction", info);
/*     */     
/* 516 */     info = new TagInfo("STAT_RED_MELEE_STR", 836, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 517 */     this.map.put("characterMeleeStrengthReqReduction", info);
/*     */     
/* 519 */     info = new TagInfo("STAT_RED_JEWELRY_INT", 828, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 520 */     this.map.put("characterJewelryIntelligenceReqReduction", info);
/*     */     
/* 522 */     info = new TagInfo("STAT_RED_SHIELD_STR", 818, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 523 */     this.map.put("characterShieldStrengthReqReduction", info);
/*     */     
/* 525 */     info = new TagInfo("STAT_CUNNING", 798);
/* 526 */     this.map.put("characterDexterity", info);
/*     */     
/* 528 */     info = new TagInfo("STAT_PHYSIQUE", 796);
/* 529 */     this.map.put("characterStrength", info);
/*     */     
/* 531 */     info = new TagInfo("STAT_SPIRIT", 794);
/* 532 */     this.map.put("characterIntelligence", info);
/*     */     
/* 534 */     info = new TagInfo("STAT_OFF_ABIL_SHORT", 698);
/* 535 */     this.map.put("characterOffensiveAbility", info);
/*     */     
/* 537 */     info = new TagInfo("STAT_DEF_ABIL_SHORT", 696);
/* 538 */     this.map.put("characterDefensiveAbility", info);
/*     */     
/* 540 */     info = new TagInfo("STAT_AVOID_PROJECTILE", 688, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 541 */     this.map.put("characterDeflectProjectile", info);
/*     */     
/* 543 */     info = new TagInfo("STAT_AVOID_MELEE", 686, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 544 */     this.map.put("characterDodgePercent", info);
/*     */     
/* 546 */     info = new TagInfo("STAT_SKILL_HEALTH", 598);
/* 547 */     this.map.put("skillLifeBonus", info);
/*     */     
/* 549 */     info = new TagInfo("STAT_SKILL_HEALTH", 597, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 550 */     this.map.put("skillLifePercent", info);
/*     */     
/* 552 */     info = new TagInfo("STAT_HEAL_INC", 596, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.JITTER);
/* 553 */     this.map.put("characterHealIncreasePercent", info);
/*     */     
/* 555 */     info = new TagInfo("STAT_REGEN_HEALTH", 595);
/* 556 */     this.map.put("characterLifeRegen", info);
/*     */     
/* 558 */     info = new TagInfo("STAT_HEALTH", 594);
/* 559 */     this.map.put("characterLife", info);
/*     */     
/* 561 */     info = new TagInfo("STAT_CONSTITUTION", 592);
/* 562 */     this.map.put("characterConstitution", info);
/*     */     
/* 564 */     info = new TagInfo("TXT_UNI_RACE_DAMAGE", 498, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 565 */     this.map.put("racialBonusPercentDamage", info);
/*     */     
/* 567 */     info = new TagInfo("STAT_REGEN_ENERGY", 488);
/* 568 */     this.map.put("characterManaRegen", info);
/*     */     
/* 570 */     info = new TagInfo("STAT_MANACOST_REDUCTION", 478, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 571 */     this.map.put("skillManaCostReduction", info);
/*     */     
/* 573 */     info = new TagInfo("STAT_ENERGY_ABSORB", 468, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 574 */     this.map.put("characterEnergyAbsorptionPercent", info);
/*     */     
/* 576 */     info = new TagInfo("STAT_ENERGY", 398);
/* 577 */     this.map.put("characterMana", info);
/*     */     
/* 579 */     info = new TagInfo("STAT_ENERGY_LIMIT", 396);
/* 580 */     this.map.put("characterManaLimitReserve", info);
/*     */     
/* 582 */     info = new TagInfo("STAT_ENERGY_RESERVE", 394);
/* 583 */     this.map.put("characterManaLimitReserveReduction", info);
/*     */     
/* 585 */     info = new TagInfo("STAT_SKILL_RADIUS", 392, TagInfo.ValueType.FLOAT, TagInfo.RandCategory.FIXED);
/* 586 */     this.map.put("skillTargetRadius", info);
/*     */     
/* 588 */     info = new TagInfo("STAT_PET_SUMMON", 390, TagInfo.RandCategory.FIXED);
/* 589 */     this.map.put("petBurstSpawn", info);
/*     */     
/* 591 */     info = new TagInfo("STAT_PET_LIMIT", 388, TagInfo.RandCategory.FIXED);
/* 592 */     this.map.put("petLimit", info);
/*     */     
/* 594 */     info = new TagInfo("STAT_SKILL_DURATION", 386, TagInfo.ValueType.FLOAT, TagInfo.RandCategory.FIXED);
/* 595 */     this.map.put("skillActiveDuration", info);
/*     */     
/* 597 */     info = new TagInfo("STAT_MAX_LEVEL_INC", 384, TagInfo.RandCategory.FIXED);
/* 598 */     this.map.put("skillMaxLevel", info);
/*     */     
/* 600 */     info = new TagInfo("STAT_TARGET_INC", 382, TagInfo.RandCategory.FIXED);
/* 601 */     this.map.put("skillTargetNumber", info);
/*     */     
/* 603 */     info = new TagInfo("STAT_TARGET_INC", 380, TagInfo.RandCategory.FIXED);
/* 604 */     this.map.put("sparkMaxNumber", info);
/*     */     
/* 606 */     info = new TagInfo("STAT_LIGHT_RADIUS", 298, TagInfo.FlatCategory.PERCENTAGE, TagInfo.RandCategory.FIXED);
/* 607 */     this.map.put("characterLightRadius", info);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\description\TagInfoHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */