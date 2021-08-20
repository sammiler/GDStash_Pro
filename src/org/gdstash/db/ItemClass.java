/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import org.gdstash.util.GDColor;
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
/*     */ public class ItemClass
/*     */ {
/*     */   public static int getRarityInt(String rarity) {
/*  20 */     if (rarity == null) return -1;
/*     */     
/*  22 */     if (rarity.equals("Broken")) return 1; 
/*  23 */     if (rarity.equals("Common")) return 2; 
/*  24 */     if (rarity.equals("Magical")) return 3; 
/*  25 */     if (rarity.equals("Rare")) return 4; 
/*  26 */     if (rarity.equals("Epic")) return 5; 
/*  27 */     if (rarity.equals("Legendary")) return 6; 
/*  28 */     if (rarity.equals("Rare")) return 7;
/*     */     
/*  30 */     return -1;
/*     */   }
/*     */   
/*     */   public static int getClassInt(String itemClass) {
/*  34 */     if (itemClass == null) return -1;
/*     */     
/*  36 */     if (itemClass.equals("ArmorProtective_Head")) return 101; 
/*  37 */     if (itemClass.equals("ArmorProtective_Shoulders")) return 102; 
/*  38 */     if (itemClass.equals("ArmorProtective_Chest")) return 103; 
/*  39 */     if (itemClass.equals("ArmorProtective_Hands")) return 104; 
/*  40 */     if (itemClass.equals("ArmorProtective_Waist")) return 105; 
/*  41 */     if (itemClass.equals("ArmorProtective_Legs")) return 106; 
/*  42 */     if (itemClass.equals("ArmorProtective_Feet")) return 107;
/*     */     
/*  44 */     if (itemClass.equals("ArmorJewelry_Amulet")) return 201; 
/*  45 */     if (itemClass.equals("ArmorJewelry_Medal")) return 202; 
/*  46 */     if (itemClass.equals("ArmorJewelry_Ring")) return 203;
/*     */     
/*  48 */     if (itemClass.equals("WeaponArmor_Offhand")) return 301; 
/*  49 */     if (itemClass.equals("WeaponArmor_Shield")) return 311; 
/*  50 */     if (itemClass.equals("WeaponMelee_Axe")) return 321; 
/*  51 */     if (itemClass.equals("WeaponMelee_Mace")) return 322; 
/*  52 */     if (itemClass.equals("WeaponHunting_Spear")) return 323; 
/*  53 */     if (itemClass.equals("WeaponMelee_Sword")) return 324; 
/*  54 */     if (itemClass.equals("WeaponMelee_Dagger")) return 331; 
/*  55 */     if (itemClass.equals("WeaponMelee_Scepter")) return 332; 
/*  56 */     if (itemClass.equals("WeaponHunting_Ranged1h")) return 341; 
/*  57 */     if (itemClass.equals("WeaponMelee_Axe2h")) return 351; 
/*  58 */     if (itemClass.equals("WeaponMelee_Mace2h")) return 352; 
/*  59 */     if (itemClass.equals("WeaponMagical_Staff")) return 353; 
/*  60 */     if (itemClass.equals("WeaponMelee_Sword2h")) return 354; 
/*  61 */     if (itemClass.equals("WeaponHunting_Ranged2h")) return 361;
/*     */     
/*  63 */     if (itemClass.equals("ItemArtifact")) return 401;
/*     */     
/*  65 */     if (itemClass.equals("ItemRelic")) return 501;
/*     */     
/*  67 */     if (itemClass.equals("ItemArtifactFormula")) return 601;
/*     */     
/*  69 */     if (itemClass.equals("QuestItem")) return 701; 
/*  70 */     if (itemClass.equals("ItemDifficultyUnlock")) return 702; 
/*  71 */     if (itemClass.equals("ItemFactionBooster")) return 703; 
/*  72 */     if (itemClass.equals("ItemFactionWarrant")) return 704; 
/*  73 */     if (itemClass.equals("ItemTransmuter")) return 705;
/*     */     
/*  75 */     if (itemClass.equals("OneShot_PotionHealth")) return 801; 
/*  76 */     if (itemClass.equals("OneShot_PotionMana")) return 802; 
/*  77 */     if (itemClass.equals("ItemUsableSkill")) return 803; 
/*  78 */     if (itemClass.equals("OneShot_Scroll")) return 804; 
/*  79 */     if (itemClass.equals("OneShot_Potion")) return 804;
/*     */     
/*  81 */     if (itemClass.equals("ItemNote")) return 901;
/*     */     
/*  83 */     return -1;
/*     */   }
/*     */   
/*     */   public static int getTransmuteType(String itemClass) {
/*  87 */     if (itemClass.equals("ArmorProtective_Head")) return 1; 
/*  88 */     if (itemClass.equals("ArmorProtective_Shoulders")) return 14; 
/*  89 */     if (itemClass.equals("ArmorProtective_Chest")) return 3; 
/*  90 */     if (itemClass.equals("ArmorProtective_Hands")) return 7; 
/*  91 */     if (itemClass.equals("ArmorProtective_Legs")) return 4; 
/*  92 */     if (itemClass.equals("ArmorProtective_Feet")) return 5;
/*     */     
/*  94 */     if (itemClass.equals("ArmorJewelry_Medal")) return 15;
/*     */     
/*  96 */     if (itemClass.equals("WeaponArmor_Offhand")) return 8; 
/*  97 */     if (itemClass.equals("WeaponArmor_Shield")) return 8; 
/*  98 */     if (itemClass.equals("WeaponMelee_Axe")) return 9; 
/*  99 */     if (itemClass.equals("WeaponMelee_Mace")) return 9; 
/* 100 */     if (itemClass.equals("WeaponHunting_Spear")) return 9; 
/* 101 */     if (itemClass.equals("WeaponMelee_Sword")) return 9; 
/* 102 */     if (itemClass.equals("WeaponMelee_Dagger")) return 9; 
/* 103 */     if (itemClass.equals("WeaponMelee_Scepter")) return 9; 
/* 104 */     if (itemClass.equals("WeaponHunting_Ranged1h")) return 9; 
/* 105 */     if (itemClass.equals("WeaponMelee_Axe2h")) return 9; 
/* 106 */     if (itemClass.equals("WeaponMelee_Mace2h")) return 9; 
/* 107 */     if (itemClass.equals("WeaponMagical_Staff")) return 9; 
/* 108 */     if (itemClass.equals("WeaponMelee_Sword2h")) return 9; 
/* 109 */     if (itemClass.equals("WeaponHunting_Ranged2h")) return 9;
/*     */     
/* 111 */     return -1;
/*     */   }
/*     */   
/*     */   public static boolean isArmor(String itemClass) {
/* 115 */     int i = getClassInt(itemClass);
/*     */     
/* 117 */     return (i >= 100 && i < 200);
/*     */   }
/*     */   
/*     */   public static boolean isArtifact(String itemClass) {
/* 121 */     if (itemClass == null) return false;
/*     */     
/* 123 */     return itemClass.equals("ItemArtifact");
/*     */   }
/*     */   
/*     */   public static boolean isComponent(String itemClass) {
/* 127 */     if (itemClass == null) return false;
/*     */     
/* 129 */     return itemClass.equals("ItemRelic");
/*     */   }
/*     */   
/*     */   public static boolean isEnchantment(String itemClass) {
/* 133 */     if (itemClass == null) return false;
/*     */     
/* 135 */     return itemClass.equals("ItemEnchantment");
/*     */   }
/*     */   
/*     */   public static boolean isJewelry(String itemClass) {
/* 139 */     int i = getClassInt(itemClass);
/*     */     
/* 141 */     return (i >= 200 && i < 300);
/*     */   }
/*     */   
/*     */   public static boolean isNote(String itemClass) {
/* 145 */     if (itemClass == null) return false;
/*     */     
/* 147 */     return itemClass.equals("ItemNote");
/*     */   }
/*     */   
/*     */   public static boolean isFactionBooster(String itemClass) {
/* 151 */     if (itemClass == null) return false;
/*     */ 
/*     */     
/* 154 */     if (itemClass.equals("ItemFactionBooster")) return true; 
/* 155 */     if (itemClass.equals("ItemFactionWarrant")) return true; 
/* 156 */     if (itemClass.equals("ItemDifficultyUnlock")) return true;
/*     */     
/* 158 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isElixir(String itemClass) {
/* 162 */     if (itemClass == null) return false;
/*     */ 
/*     */     
/* 165 */     if (itemClass.equals("OneShot_Scroll")) return true; 
/* 166 */     if (itemClass.equals("OneShot_Potion")) return true; 
/* 167 */     if (itemClass.equals("ItemUsableSkill")) return true;
/*     */     
/* 169 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isPotion(String itemClass) {
/* 173 */     if (itemClass == null) return false;
/*     */ 
/*     */     
/* 176 */     if (itemClass.equals("OneShot_PotionHealth")) return true; 
/* 177 */     if (itemClass.equals("OneShot_PotionMana")) return true; 
/* 178 */     if (itemClass.equals("ItemDevotionReset")) return true; 
/* 179 */     if (itemClass.equals("ItemAttributeReset")) return true;
/*     */     
/* 181 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isOffhand(String itemClass) {
/* 185 */     int i = getClassInt(itemClass);
/*     */     
/* 187 */     return (i >= 300 && i < 320);
/*     */   }
/*     */   
/*     */   public static boolean isWeapon(String itemClass) {
/* 191 */     int i = getClassInt(itemClass);
/*     */     
/* 193 */     return (i >= 300 && i < 400);
/*     */   }
/*     */   
/*     */   public static boolean is1HWeapon(String itemClass) {
/* 197 */     int i = getClassInt(itemClass);
/*     */     
/* 199 */     return (i >= 320 && i < 350);
/*     */   }
/*     */   
/*     */   public static boolean is2HWeapon(String itemClass) {
/* 203 */     int i = getClassInt(itemClass);
/*     */     
/* 205 */     return (i >= 350 && i < 400);
/*     */   }
/*     */   
/*     */   public static boolean hasSlots(String itemClass) {
/* 209 */     if (itemClass == null) return false;
/*     */     
/* 211 */     if (itemClass.equals("ItemRelic") || itemClass
/* 212 */       .equals("ItemEnchantment")) {
/* 213 */       return true;
/*     */     }
/*     */     
/* 216 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean hasRarity(String itemClass) {
/* 220 */     if (itemClass == null) return false;
/*     */     
/* 222 */     if (itemClass.equals("ArmorProtective_Head") || itemClass
/* 223 */       .equals("ArmorProtective_Shoulders") || itemClass
/* 224 */       .equals("ArmorProtective_Chest") || itemClass
/* 225 */       .equals("ArmorProtective_Hands") || itemClass
/* 226 */       .equals("ArmorProtective_Waist") || itemClass
/* 227 */       .equals("ArmorProtective_Legs") || itemClass
/* 228 */       .equals("ArmorProtective_Feet") || itemClass
/* 229 */       .equals("ArmorJewelry_Amulet") || itemClass
/* 230 */       .equals("ArmorJewelry_Ring") || itemClass
/* 231 */       .equals("ArmorJewelry_Medal") || itemClass
/* 232 */       .equals("WeaponMelee_Axe") || itemClass
/* 233 */       .equals("WeaponMelee_Dagger") || itemClass
/* 234 */       .equals("WeaponMelee_Mace") || itemClass
/* 235 */       .equals("WeaponHunting_Ranged1h") || itemClass
/* 236 */       .equals("WeaponMelee_Scepter") || itemClass
/* 237 */       .equals("WeaponHunting_Spear") || itemClass
/* 238 */       .equals("WeaponMelee_Sword") || itemClass
/* 239 */       .equals("WeaponMelee_Axe2h") || itemClass
/* 240 */       .equals("WeaponMelee_Mace2h") || itemClass
/* 241 */       .equals("WeaponHunting_Ranged2h") || itemClass
/* 242 */       .equals("WeaponMagical_Staff") || itemClass
/* 243 */       .equals("WeaponMelee_Sword2h") || itemClass
/* 244 */       .equals("WeaponArmor_Shield") || itemClass
/* 245 */       .equals("WeaponArmor_Offhand") || itemClass
/* 246 */       .equals("ItemArtifactFormula") || itemClass
/* 247 */       .equals("ItemRelic") || itemClass
/* 248 */       .equals("ItemEnchantment") || itemClass
/* 249 */       .equals("ItemFactionBooster") || itemClass
/* 250 */       .equals("OneShot_Scroll")) {
/* 251 */       return true;
/*     */     }
/*     */     
/* 254 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isStackable(String itemClass) {
/* 258 */     if (itemClass == null) return false;
/*     */     
/* 260 */     if (itemClass.equals("ItemRelic")) return true; 
/* 261 */     if (itemClass.equals("ItemEnchantment")) return true; 
/* 262 */     if (itemClass.equals("OneShot_PotionHealth")) return true; 
/* 263 */     if (itemClass.equals("OneShot_PotionMana")) return true; 
/* 264 */     if (itemClass.equals("ItemDevotionReset")) return true; 
/* 265 */     if (itemClass.equals("ItemAttributeReset")) return true; 
/* 266 */     if (itemClass.equals("QuestItem")) return true; 
/* 267 */     if (itemClass.equals("OneShot_Scroll")) return true; 
/* 268 */     if (itemClass.equals("OneShot_Potion")) return true; 
/* 269 */     if (itemClass.equals("ItemArtifactFormula")) return true; 
/* 270 */     if (itemClass.equals("ItemFactionBooster")) return true; 
/* 271 */     if (itemClass.equals("ItemFactionWarrant")) return true; 
/* 272 */     if (itemClass.equals("ItemNote")) return true; 
/* 273 */     if (itemClass.equals("ItemUsableSkill")) return true;
/*     */     
/* 275 */     return false;
/*     */   }
/*     */   
/*     */   public static int getDefaultStackSize(String itemClass) {
/* 279 */     if (itemClass == null) return 1;
/*     */     
/* 281 */     if (itemClass.equals("ItemRelic")) return 100; 
/* 282 */     if (itemClass.equals("ItemEnchantment")) return 100; 
/* 283 */     if (itemClass.equals("OneShot_PotionHealth")) return 100; 
/* 284 */     if (itemClass.equals("OneShot_PotionMana")) return 100; 
/* 285 */     if (itemClass.equals("ItemDevotionReset")) return 100; 
/* 286 */     if (itemClass.equals("ItemAttributeReset")) return 100; 
/* 287 */     if (itemClass.equals("QuestItem")) return 1; 
/* 288 */     if (itemClass.equals("OneShot_Scroll")) return 100; 
/* 289 */     if (itemClass.equals("OneShot_Potion")) return 100; 
/* 290 */     if (itemClass.equals("ItemEnchantment")) return 100; 
/* 291 */     if (itemClass.equals("ItemArtifactFormula")) return 100; 
/* 292 */     if (itemClass.equals("ItemFactionBooster")) return 100; 
/* 293 */     if (itemClass.equals("ItemFactionWarrant")) return 100; 
/* 294 */     if (itemClass.equals("ItemNote")) return 100; 
/* 295 */     if (itemClass.equals("ItemUsableSkill")) return 100;
/*     */     
/* 297 */     return 1;
/*     */   }
/*     */   
/*     */   public static boolean usesAffixes(String itemClass) {
/* 301 */     if (itemClass == null) return false;
/*     */     
/* 303 */     if (itemClass.equals("ArmorProtective_Head") || itemClass
/* 304 */       .equals("ArmorProtective_Shoulders") || itemClass
/* 305 */       .equals("ArmorProtective_Chest") || itemClass
/* 306 */       .equals("ArmorProtective_Hands") || itemClass
/* 307 */       .equals("ArmorProtective_Waist") || itemClass
/* 308 */       .equals("ArmorProtective_Legs") || itemClass
/* 309 */       .equals("ArmorProtective_Feet") || itemClass
/* 310 */       .equals("ArmorJewelry_Amulet") || itemClass
/* 311 */       .equals("ArmorJewelry_Ring") || itemClass
/* 312 */       .equals("ArmorJewelry_Medal") || itemClass
/* 313 */       .equals("WeaponMelee_Axe") || itemClass
/* 314 */       .equals("WeaponMelee_Dagger") || itemClass
/* 315 */       .equals("WeaponMelee_Mace") || itemClass
/* 316 */       .equals("WeaponHunting_Ranged1h") || itemClass
/* 317 */       .equals("WeaponMelee_Scepter") || itemClass
/* 318 */       .equals("WeaponHunting_Spear") || itemClass
/* 319 */       .equals("WeaponMelee_Sword") || itemClass
/* 320 */       .equals("WeaponMelee_Axe2h") || itemClass
/* 321 */       .equals("WeaponMelee_Mace2h") || itemClass
/* 322 */       .equals("WeaponHunting_Ranged2h") || itemClass
/* 323 */       .equals("WeaponMagical_Staff") || itemClass
/* 324 */       .equals("WeaponMelee_Sword2h") || itemClass
/* 325 */       .equals("WeaponArmor_Shield") || itemClass
/* 326 */       .equals("WeaponArmor_Offhand")) {
/* 327 */       return true;
/*     */     }
/*     */     
/* 330 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean usesCostFormula(String itemClass) {
/* 334 */     if (itemClass == null) return false;
/*     */     
/* 336 */     if (itemClass.equals("ArmorProtective_Head")) return true; 
/* 337 */     if (itemClass.equals("ArmorProtective_Shoulders")) return true; 
/* 338 */     if (itemClass.equals("ArmorProtective_Chest")) return true; 
/* 339 */     if (itemClass.equals("ArmorProtective_Hands")) return true; 
/* 340 */     if (itemClass.equals("ArmorProtective_Legs")) return true; 
/* 341 */     if (itemClass.equals("ArmorProtective_Feet")) return true; 
/* 342 */     if (itemClass.equals("ArmorProtective_Waist")) return true; 
/* 343 */     if (itemClass.equals("ArmorJewelry_Amulet")) return true; 
/* 344 */     if (itemClass.equals("ArmorJewelry_Medal")) return true; 
/* 345 */     if (itemClass.equals("ArmorJewelry_Ring")) return true; 
/* 346 */     if (itemClass.equals("WeaponArmor_Offhand")) return true; 
/* 347 */     if (itemClass.equals("WeaponArmor_Shield")) return true; 
/* 348 */     if (itemClass.equals("WeaponMelee_Axe")) return true; 
/* 349 */     if (itemClass.equals("WeaponMelee_Mace")) return true; 
/* 350 */     if (itemClass.equals("WeaponHunting_Spear")) return true; 
/* 351 */     if (itemClass.equals("WeaponMelee_Sword")) return true; 
/* 352 */     if (itemClass.equals("WeaponMelee_Dagger")) return true; 
/* 353 */     if (itemClass.equals("WeaponMelee_Scepter")) return true; 
/* 354 */     if (itemClass.equals("WeaponHunting_Ranged1h")) return true; 
/* 355 */     if (itemClass.equals("WeaponMelee_Axe2h")) return true; 
/* 356 */     if (itemClass.equals("WeaponMelee_Mace2h")) return true; 
/* 357 */     if (itemClass.equals("WeaponMagical_Staff")) return true; 
/* 358 */     if (itemClass.equals("WeaponMelee_Sword2h")) return true; 
/* 359 */     if (itemClass.equals("WeaponHunting_Ranged2h")) return true;
/*     */     
/* 361 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color getRarityColor(String rarity) {
/* 369 */     Color color = GDColor.COLOR_FG_COMMON;
/*     */     
/* 371 */     if (rarity != null) {
/* 372 */       if (rarity.equals("Magical")) color = GDColor.COLOR_FG_MAGICAL; 
/* 373 */       if (rarity.equals("Rare")) color = GDColor.COLOR_FG_RARE; 
/* 374 */       if (rarity.equals("Epic")) color = GDColor.COLOR_FG_EPIC; 
/* 375 */       if (rarity.equals("Legendary")) color = GDColor.COLOR_FG_LEGENDARY;
/*     */     
/*     */     } 
/* 378 */     return color;
/*     */   }
/*     */   
/*     */   public static Color getRarityBackgroundColor(String rarity) {
/* 382 */     Color color = GDColor.COLOR_BG_COMMON;
/*     */     
/* 384 */     if (rarity != null) {
/* 385 */       if (rarity.equals("Magical")) color = GDColor.COLOR_BG_MAGICAL; 
/* 386 */       if (rarity.equals("Rare")) color = GDColor.COLOR_BG_RARE; 
/* 387 */       if (rarity.equals("Epic")) color = GDColor.COLOR_BG_EPIC; 
/* 388 */       if (rarity.equals("Legendary")) color = GDColor.COLOR_BG_LEGENDARY;
/*     */     
/*     */     } 
/* 391 */     return color;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\ItemClass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */