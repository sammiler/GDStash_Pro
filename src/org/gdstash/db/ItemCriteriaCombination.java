/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.ui.GDStashFrame;
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
/*     */ public class ItemCriteriaCombination
/*     */   implements Cloneable
/*     */ {
/*     */   private String itemClass;
/*     */   private String armorClass;
/*     */   private String artifactClass;
/*     */   private String itemRarity;
/*     */   private boolean useArmorClass;
/*     */   private boolean useArtifactClass;
/*     */   private boolean useItemRarity;
/*     */   private boolean useSlots;
/*     */   private int levelMin;
/*     */   private int levelMax;
/*     */   private int cunningMax;
/*     */   private int physiqueMax;
/*     */   private int spiritMax;
/*     */   private boolean noEnemyOnly;
/*     */   private SelectionCriteria criteria;
/*     */   
/*     */   private ItemCriteriaCombination() {
/*  39 */     this.itemClass = null;
/*  40 */     this.armorClass = null;
/*  41 */     this.artifactClass = null;
/*  42 */     this.itemRarity = null;
/*     */     
/*  44 */     this.useArmorClass = false;
/*  45 */     this.useArtifactClass = false;
/*  46 */     this.useItemRarity = false;
/*  47 */     this.useSlots = false;
/*     */     
/*  49 */     this.levelMin = -1;
/*  50 */     this.levelMax = -1;
/*  51 */     this.cunningMax = -1;
/*  52 */     this.physiqueMax = -1;
/*  53 */     this.spiritMax = -1;
/*     */     
/*  55 */     this.noEnemyOnly = true;
/*     */     
/*  57 */     this.criteria = null;
/*     */   }
/*     */   
/*     */   private ItemCriteriaCombination(ItemCriteriaCombination combo) {
/*  61 */     this.itemClass = combo.itemClass;
/*  62 */     this.armorClass = combo.armorClass;
/*  63 */     this.artifactClass = combo.artifactClass;
/*  64 */     this.itemRarity = combo.itemRarity;
/*     */     
/*  66 */     this.useArmorClass = combo.useArmorClass;
/*  67 */     this.useArtifactClass = combo.useArtifactClass;
/*  68 */     this.useItemRarity = combo.useItemRarity;
/*  69 */     this.useSlots = combo.useSlots;
/*     */     
/*  71 */     this.levelMin = combo.levelMin;
/*  72 */     this.levelMax = combo.levelMax;
/*  73 */     this.cunningMax = combo.cunningMax;
/*  74 */     this.physiqueMax = combo.physiqueMax;
/*  75 */     this.spiritMax = combo.spiritMax;
/*     */     
/*  77 */     this.noEnemyOnly = combo.noEnemyOnly;
/*     */     
/*  79 */     this.criteria = combo.criteria;
/*     */   }
/*     */   
/*     */   public ItemCriteriaCombination(SelectionCriteria criteria) {
/*  83 */     this();
/*     */     
/*  85 */     this.levelMin = criteria.levelMin;
/*  86 */     this.levelMax = criteria.levelMax;
/*  87 */     this.cunningMax = criteria.cunningMax;
/*  88 */     this.physiqueMax = criteria.physiqueMax;
/*  89 */     this.spiritMax = criteria.spiritMax;
/*  90 */     this.noEnemyOnly = criteria.noEnemyOnly;
/*     */     
/*  92 */     this.criteria = criteria;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemCriteriaCombination clone() {
/*  97 */     ItemCriteriaCombination combo = new ItemCriteriaCombination(this);
/*     */     
/*  99 */     return combo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getItemClass() {
/* 108 */     return this.itemClass;
/*     */   }
/*     */   
/*     */   public String getArmorClass() {
/* 112 */     return this.armorClass;
/*     */   }
/*     */   
/*     */   public String getArtifactClass() {
/* 116 */     return this.artifactClass;
/*     */   }
/*     */   
/*     */   public String getItemRarity() {
/* 120 */     return this.itemRarity;
/*     */   }
/*     */   
/*     */   public boolean usesArmorClass() {
/* 124 */     return this.useArmorClass;
/*     */   }
/*     */   
/*     */   public boolean usesArtifactClass() {
/* 128 */     return this.useArtifactClass;
/*     */   }
/*     */   
/*     */   public boolean usesItemRarity() {
/* 132 */     return this.useItemRarity;
/*     */   }
/*     */   
/*     */   public boolean usesSlots() {
/* 136 */     return this.useSlots;
/*     */   }
/*     */   
/*     */   public int getMinLevel() {
/* 140 */     return this.levelMin;
/*     */   }
/*     */   
/*     */   public int getMaxLevel() {
/* 144 */     return this.levelMax;
/*     */   }
/*     */   
/*     */   public int getMaxCunning() {
/* 148 */     return this.cunningMax;
/*     */   }
/*     */   
/*     */   public int getMaxPhysique() {
/* 152 */     return this.physiqueMax;
/*     */   }
/*     */   
/*     */   public int getMaxSpirit() {
/* 156 */     return this.spiritMax;
/*     */   }
/*     */   
/*     */   public boolean isNoEnemyOnly() {
/* 160 */     return this.noEnemyOnly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init() {
/* 169 */     this.useArmorClass = false;
/* 170 */     if (!this.criteria.armorClass.isEmpty() && 
/* 171 */       ItemClass.isArmor(this.itemClass)) {
/* 172 */       this.useArmorClass = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 178 */     this.useArtifactClass = false;
/* 179 */     if (!this.criteria.artifactClass.isEmpty() && 
/* 180 */       ItemClass.isArtifact(this.itemClass)) {
/* 181 */       this.useArtifactClass = true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 186 */     this.useItemRarity = false;
/* 187 */     if (!this.criteria.itemRarity.isEmpty() && 
/* 188 */       ItemClass.hasRarity(this.itemClass)) {
/* 189 */       this.useItemRarity = true;
/*     */     }
/*     */ 
/*     */     
/* 193 */     this.useSlots = false;
/* 194 */     if (ItemClass.hasSlots(this.itemClass)) {
/* 195 */       this.useSlots = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemClass(String itemClass) {
/* 204 */     if (itemClass == null) {
/* 205 */       this.itemClass = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 210 */     if (itemClass.isEmpty()) {
/* 211 */       this.itemClass = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 216 */     this.itemClass = itemClass;
/*     */     
/* 218 */     init();
/*     */   }
/*     */   
/*     */   public void setArmorClass(String armorClass) {
/* 222 */     if (armorClass == null) {
/* 223 */       this.armorClass = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 228 */     if (armorClass.isEmpty()) {
/* 229 */       this.armorClass = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 234 */     this.armorClass = armorClass;
/*     */   }
/*     */   
/*     */   public void setArtifactClass(String artifactClass) {
/* 238 */     if (artifactClass == null) {
/* 239 */       this.artifactClass = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 244 */     if (artifactClass.isEmpty()) {
/* 245 */       this.artifactClass = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 250 */     this.artifactClass = artifactClass;
/*     */   }
/*     */   
/*     */   public void setItemRarity(String itemRarity) {
/* 254 */     if (itemRarity == null) {
/* 255 */       this.itemRarity = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 260 */     if (itemRarity.isEmpty()) {
/* 261 */       this.itemRarity = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 266 */     this.itemRarity = itemRarity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String determineItemParameters() {
/* 274 */     String param = this.itemClass;
/*     */     
/* 276 */     if (this.useItemRarity) param = param + ", " + this.itemRarity; 
/* 277 */     if (this.useArmorClass) param = param + ", " + this.armorClass; 
/* 278 */     if (this.useArtifactClass) param = param + ", " + this.artifactClass;
/*     */     
/* 280 */     if (this.levelMin != -1) param = param + ", " + Integer.toString(this.levelMin); 
/* 281 */     if (this.levelMax != -1) param = param + ", " + Integer.toString(this.levelMax); 
/* 282 */     if (this.cunningMax != -1) param = param + ", " + Integer.toString(this.cunningMax); 
/* 283 */     if (this.physiqueMax != -1) param = param + ", " + Integer.toString(this.physiqueMax); 
/* 284 */     if (this.spiritMax != -1) param = param + ", " + Integer.toString(this.spiritMax);
/*     */     
/* 286 */     return param;
/*     */   }
/*     */   
/*     */   public String determineItemStatement() {
/* 290 */     String command = "SELECT * FROM GD_ITEM WHERE ITEM_CLASS = ?";
/* 291 */     if (this.useItemRarity) command = command + " AND RARITY = ?"; 
/* 292 */     if (this.useArmorClass) command = command + " AND ARMOR_CLASS = ?"; 
/* 293 */     if (this.useArtifactClass) command = command + " AND ARTIFACT_CLASS = ?"; 
/* 294 */     if (this.levelMin != -1) command = command + " AND REQ_LEVEL >= ?"; 
/* 295 */     if (this.levelMax != -1) command = command + " AND REQ_LEVEL <= ?"; 
/* 296 */     if (this.cunningMax != -1) command = command + " AND REQ_DEX <= ?"; 
/* 297 */     if (this.physiqueMax != -1) command = command + " AND REQ_STR <= ?"; 
/* 298 */     if (this.spiritMax != -1) command = command + " AND REQ_INT <= ?"; 
/* 299 */     if (this.noEnemyOnly) command = command + " AND ENEMY_ONLY = ?"; 
/* 300 */     command = command + determineSlotConditions(null);
/*     */     
/* 302 */     return command;
/*     */   }
/*     */   
/*     */   public String determineItemIDStatement() {
/* 306 */     String command = "SELECT ITEM_ID FROM GD_ITEM WHERE ITEM_CLASS = ?";
/* 307 */     if (this.useItemRarity) command = command + " AND RARITY = ?"; 
/* 308 */     if (this.useArmorClass) command = command + " AND ARMOR_CLASS = ?"; 
/* 309 */     if (this.useArtifactClass) command = command + " AND ARTIFACT_CLASS = ?"; 
/* 310 */     if (this.levelMin != -1) command = command + " AND REQ_LEVEL >= ?"; 
/* 311 */     if (this.levelMax != -1) command = command + " AND REQ_LEVEL <= ?"; 
/* 312 */     if (this.cunningMax != -1) command = command + " AND REQ_DEX <= ?"; 
/* 313 */     if (this.physiqueMax != -1) command = command + " AND REQ_STR <= ?"; 
/* 314 */     if (this.spiritMax != -1) command = command + " AND REQ_INT <= ?"; 
/* 315 */     if (this.noEnemyOnly) command = command + " AND ENEMY_ONLY = ?"; 
/* 316 */     command = command + determineSlotConditions(null);
/*     */     
/* 318 */     return command;
/*     */   }
/*     */   
/*     */   public String determineStashIDItemStatement(boolean isHardcore) {
/* 322 */     String command = "SELECT S.STASH_ID FROM STASH_ITEM_V8 S, GD_ITEM I WHERE S.ITEM_ID = I.ITEM_ID AND I.ITEM_CLASS = ?";
/*     */     
/* 324 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSCHC) {
/* 325 */       if (isHardcore) {
/* 326 */         command = command + " AND S.HARDCORE = true";
/*     */       } else {
/* 328 */         command = command + " AND S.HARDCORE = false";
/*     */       } 
/*     */     }
/* 331 */     if (this.useItemRarity) command = command + " AND I.RARITY = ?"; 
/* 332 */     if (this.useArmorClass) command = command + " AND I.ARMOR_CLASS = ?"; 
/* 333 */     if (this.useArtifactClass) command = command + " AND I.ARTIFACT_CLASS = ?"; 
/* 334 */     if (this.levelMin != -1) command = command + " AND I.REQ_LEVEL >= ?"; 
/* 335 */     if (this.levelMax != -1) command = command + " AND I.REQ_LEVEL <= ?"; 
/* 336 */     if (this.cunningMax != -1) command = command + " AND I.REQ_DEX <= ?"; 
/* 337 */     if (this.physiqueMax != -1) command = command + " AND I.REQ_STR <= ?"; 
/* 338 */     if (this.spiritMax != -1) command = command + " AND I.REQ_INT <= ?"; 
/* 339 */     if (this.noEnemyOnly) command = command + " AND I.ENEMY_ONLY = ?"; 
/* 340 */     command = command + determineSlotConditions("I.");
/*     */     
/* 342 */     return command;
/*     */   }
/*     */   
/*     */   public int fillItemStatement(PreparedStatement ps) throws SQLException {
/* 346 */     ps.clearParameters();
/*     */     
/* 348 */     int nextPos = 1;
/*     */     
/* 350 */     ps.setString(nextPos, this.itemClass);
/*     */     
/* 352 */     nextPos++;
/*     */     
/* 354 */     if (this.useItemRarity) {
/* 355 */       ps.setString(nextPos, this.itemRarity);
/*     */       
/* 357 */       nextPos++;
/*     */     } 
/*     */     
/* 360 */     if (this.useArmorClass) {
/* 361 */       ps.setString(nextPos, this.armorClass);
/*     */       
/* 363 */       nextPos++;
/*     */     } 
/*     */     
/* 366 */     if (this.useArtifactClass) {
/* 367 */       ps.setString(nextPos, this.artifactClass);
/*     */       
/* 369 */       nextPos++;
/*     */     } 
/*     */     
/* 372 */     if (this.levelMin != -1) {
/* 373 */       ps.setInt(nextPos, this.levelMin);
/*     */       
/* 375 */       nextPos++;
/*     */     } 
/*     */     
/* 378 */     if (this.levelMax != -1) {
/* 379 */       ps.setInt(nextPos, this.levelMax);
/*     */       
/* 381 */       nextPos++;
/*     */     } 
/*     */     
/* 384 */     if (this.cunningMax != -1) {
/* 385 */       ps.setInt(nextPos, this.cunningMax);
/*     */       
/* 387 */       nextPos++;
/*     */     } 
/*     */     
/* 390 */     if (this.physiqueMax != -1) {
/* 391 */       ps.setInt(nextPos, this.physiqueMax);
/*     */       
/* 393 */       nextPos++;
/*     */     } 
/*     */     
/* 396 */     if (this.spiritMax != -1) {
/* 397 */       ps.setInt(nextPos, this.spiritMax);
/*     */       
/* 399 */       nextPos++;
/*     */     } 
/*     */     
/* 402 */     if (this.noEnemyOnly) {
/* 403 */       ps.setBoolean(nextPos, false);
/*     */       
/* 405 */       nextPos++;
/*     */     } 
/*     */     
/* 408 */     return nextPos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String determineSlotConditions(String prefix) {
/* 416 */     String command = "";
/*     */     
/* 418 */     if (!this.useSlots) return command;
/*     */     
/* 420 */     boolean firstSlot = true;
/*     */     
/* 422 */     if (this.criteria.slotAxe1H) {
/* 423 */       if (firstSlot) {
/* 424 */         firstSlot = false;
/*     */         
/* 426 */         command = command + " AND ( ";
/*     */       } else {
/* 428 */         command = command + " OR ";
/*     */       } 
/* 430 */       if (prefix == null) {
/* 431 */         command = command + "SLOT_AXE_1H = true";
/*     */       } else {
/* 433 */         command = command + prefix + "SLOT_AXE_1H = true";
/*     */       } 
/*     */     } 
/*     */     
/* 437 */     if (this.criteria.slotAxe2H) {
/* 438 */       if (firstSlot) {
/* 439 */         firstSlot = false;
/*     */         
/* 441 */         command = command + " AND ( ";
/*     */       } else {
/* 443 */         command = command + " OR ";
/*     */       } 
/* 445 */       if (prefix == null) {
/* 446 */         command = command + "SLOT_AXE_2H = true";
/*     */       } else {
/* 448 */         command = command + prefix + "SLOT_AXE_2H = true";
/*     */       } 
/*     */     } 
/*     */     
/* 452 */     if (this.criteria.slotDagger1H) {
/* 453 */       if (firstSlot) {
/* 454 */         firstSlot = false;
/*     */         
/* 456 */         command = command + " AND ( ";
/*     */       } else {
/* 458 */         command = command + " OR ";
/*     */       } 
/* 460 */       if (prefix == null) {
/* 461 */         command = command + "SLOT_DAGGER_1H = true";
/*     */       } else {
/* 463 */         command = command + prefix + "SLOT_DAGGER_1H = true";
/*     */       } 
/*     */     } 
/*     */     
/* 467 */     if (this.criteria.slotMace1H) {
/* 468 */       if (firstSlot) {
/* 469 */         firstSlot = false;
/*     */         
/* 471 */         command = command + " AND ( ";
/*     */       } else {
/* 473 */         command = command + " OR ";
/*     */       } 
/* 475 */       if (prefix == null) {
/* 476 */         command = command + "SLOT_MACE_1H = true";
/*     */       } else {
/* 478 */         command = command + prefix + "SLOT_MACE_1H = true";
/*     */       } 
/*     */     } 
/*     */     
/* 482 */     if (this.criteria.slotMace2H) {
/* 483 */       if (firstSlot) {
/* 484 */         firstSlot = false;
/*     */         
/* 486 */         command = command + " AND ( ";
/*     */       } else {
/* 488 */         command = command + " OR ";
/*     */       } 
/* 490 */       if (prefix == null) {
/* 491 */         command = command + "SLOT_MACE_2H = true";
/*     */       } else {
/* 493 */         command = command + prefix + "SLOT_MACE_2H = true";
/*     */       } 
/*     */     } 
/*     */     
/* 497 */     if (this.criteria.slotScepter1H) {
/* 498 */       if (firstSlot) {
/* 499 */         firstSlot = false;
/*     */         
/* 501 */         command = command + " AND ( ";
/*     */       } else {
/* 503 */         command = command + " OR ";
/*     */       } 
/* 505 */       if (prefix == null) {
/* 506 */         command = command + "SLOT_SCEPTER_1H = true";
/*     */       } else {
/* 508 */         command = command + prefix + "SLOT_SCEPTER_1H = true";
/*     */       } 
/*     */     } 
/*     */     
/* 512 */     if (this.criteria.slotSpear1H) {
/* 513 */       if (firstSlot) {
/* 514 */         firstSlot = false;
/*     */         
/* 516 */         command = command + " AND ( ";
/*     */       } else {
/* 518 */         command = command + " OR ";
/*     */       } 
/* 520 */       if (prefix == null) {
/* 521 */         command = command + "SLOT_SPEAR_2H = true";
/*     */       } else {
/* 523 */         command = command + prefix + "SLOT_SPEAR_2H = true";
/*     */       } 
/*     */     } 
/*     */     
/* 527 */     if (this.criteria.slotStaff2H) {
/* 528 */       if (firstSlot) {
/* 529 */         firstSlot = false;
/*     */         
/* 531 */         command = command + " AND ( ";
/*     */       } else {
/* 533 */         command = command + " OR ";
/*     */       } 
/* 535 */       if (prefix == null) {
/* 536 */         command = command + "SLOT_STAFF_2H = true";
/*     */       } else {
/* 538 */         command = command + prefix + "SLOT_STAFF_2H = true";
/*     */       } 
/*     */     } 
/*     */     
/* 542 */     if (this.criteria.slotSword1H) {
/* 543 */       if (firstSlot) {
/* 544 */         firstSlot = false;
/*     */         
/* 546 */         command = command + " AND ( ";
/*     */       } else {
/* 548 */         command = command + " OR ";
/*     */       } 
/* 550 */       if (prefix == null) {
/* 551 */         command = command + "SLOT_SWORD_1H = true";
/*     */       } else {
/* 553 */         command = command + prefix + "SLOT_SWORD_1H = true";
/*     */       } 
/*     */     } 
/*     */     
/* 557 */     if (this.criteria.slotSword2H) {
/* 558 */       if (firstSlot) {
/* 559 */         firstSlot = false;
/*     */         
/* 561 */         command = command + " AND ( ";
/*     */       } else {
/* 563 */         command = command + " OR ";
/*     */       } 
/* 565 */       if (prefix == null) {
/* 566 */         command = command + "SLOT_SWORD_2H = true";
/*     */       } else {
/* 568 */         command = command + prefix + "SLOT_SWORD_2H = true";
/*     */       } 
/*     */     } 
/*     */     
/* 572 */     if (this.criteria.slotRanged1H) {
/* 573 */       if (firstSlot) {
/* 574 */         firstSlot = false;
/*     */         
/* 576 */         command = command + " AND ( ";
/*     */       } else {
/* 578 */         command = command + " OR ";
/*     */       } 
/* 580 */       if (prefix == null) {
/* 581 */         command = command + "SLOT_RANGED_1H = true";
/*     */       } else {
/* 583 */         command = command + prefix + "SLOT_RANGED_1H = true";
/*     */       } 
/*     */     } 
/*     */     
/* 587 */     if (this.criteria.slotRanged2H) {
/* 588 */       if (firstSlot) {
/* 589 */         firstSlot = false;
/*     */         
/* 591 */         command = command + " AND ( ";
/*     */       } else {
/* 593 */         command = command + " OR ";
/*     */       } 
/* 595 */       if (prefix == null) {
/* 596 */         command = command + "SLOT_RANGED_2H = true";
/*     */       } else {
/* 598 */         command = command + prefix + "SLOT_RANGED_2H = true";
/*     */       } 
/*     */     } 
/*     */     
/* 602 */     if (this.criteria.slotShield) {
/* 603 */       if (firstSlot) {
/* 604 */         firstSlot = false;
/*     */         
/* 606 */         command = command + " AND ( ";
/*     */       } else {
/* 608 */         command = command + " OR ";
/*     */       } 
/* 610 */       if (prefix == null) {
/* 611 */         command = command + "SLOT_SHIELD = true";
/*     */       } else {
/* 613 */         command = command + prefix + "SLOT_SHIELD = true";
/*     */       } 
/*     */     } 
/*     */     
/* 617 */     if (this.criteria.slotOffhand) {
/* 618 */       if (firstSlot) {
/* 619 */         firstSlot = false;
/*     */         
/* 621 */         command = command + " AND ( ";
/*     */       } else {
/* 623 */         command = command + " OR ";
/*     */       } 
/* 625 */       if (prefix == null) {
/* 626 */         command = command + "SLOT_OFFHAND = true";
/*     */       } else {
/* 628 */         command = command + prefix + "SLOT_OFFHAND = true";
/*     */       } 
/*     */     } 
/*     */     
/* 632 */     if (this.criteria.slotAmulet) {
/* 633 */       if (firstSlot) {
/* 634 */         firstSlot = false;
/*     */         
/* 636 */         command = command + " AND ( ";
/*     */       } else {
/* 638 */         command = command + " OR ";
/*     */       } 
/* 640 */       if (prefix == null) {
/* 641 */         command = command + "SLOT_AMULET = true";
/*     */       } else {
/* 643 */         command = command + prefix + "SLOT_AMULET = true";
/*     */       } 
/*     */     } 
/*     */     
/* 647 */     if (this.criteria.slotBelt) {
/* 648 */       if (firstSlot) {
/* 649 */         firstSlot = false;
/*     */         
/* 651 */         command = command + " AND ( ";
/*     */       } else {
/* 653 */         command = command + " OR ";
/*     */       } 
/* 655 */       if (prefix == null) {
/* 656 */         command = command + "SLOT_BELT = true";
/*     */       } else {
/* 658 */         command = command + prefix + "SLOT_BELT = true";
/*     */       } 
/*     */     } 
/*     */     
/* 662 */     if (this.criteria.slotMedal) {
/* 663 */       if (firstSlot) {
/* 664 */         firstSlot = false;
/*     */         
/* 666 */         command = command + " AND ( ";
/*     */       } else {
/* 668 */         command = command + " OR ";
/*     */       } 
/* 670 */       if (prefix == null) {
/* 671 */         command = command + "SLOT_MEDAL = true";
/*     */       } else {
/* 673 */         command = command + prefix + "SLOT_MEDAL = true";
/*     */       } 
/*     */     } 
/*     */     
/* 677 */     if (this.criteria.slotRing) {
/* 678 */       if (firstSlot) {
/* 679 */         firstSlot = false;
/*     */         
/* 681 */         command = command + " AND ( ";
/*     */       } else {
/* 683 */         command = command + " OR ";
/*     */       } 
/* 685 */       if (prefix == null) {
/* 686 */         command = command + "SLOT_RING = true";
/*     */       } else {
/* 688 */         command = command + prefix + "SLOT_RING = true";
/*     */       } 
/*     */     } 
/*     */     
/* 692 */     if (this.criteria.slotHead) {
/* 693 */       if (firstSlot) {
/* 694 */         firstSlot = false;
/*     */         
/* 696 */         command = command + " AND ( ";
/*     */       } else {
/* 698 */         command = command + " OR ";
/*     */       } 
/* 700 */       if (prefix == null) {
/* 701 */         command = command + "SLOT_HEAD = true";
/*     */       } else {
/* 703 */         command = command + prefix + "SLOT_HEAD = true";
/*     */       } 
/*     */     } 
/*     */     
/* 707 */     if (this.criteria.slotShoulders) {
/* 708 */       if (firstSlot) {
/* 709 */         firstSlot = false;
/*     */         
/* 711 */         command = command + " AND ( ";
/*     */       } else {
/* 713 */         command = command + " OR ";
/*     */       } 
/* 715 */       if (prefix == null) {
/* 716 */         command = command + "SLOT_SHOULDERS = true";
/*     */       } else {
/* 718 */         command = command + prefix + "SLOT_SHOULDERS = true";
/*     */       } 
/*     */     } 
/*     */     
/* 722 */     if (this.criteria.slotChest) {
/* 723 */       if (firstSlot) {
/* 724 */         firstSlot = false;
/*     */         
/* 726 */         command = command + " AND ( ";
/*     */       } else {
/* 728 */         command = command + " OR ";
/*     */       } 
/* 730 */       if (prefix == null) {
/* 731 */         command = command + "SLOT_CHEST = true";
/*     */       } else {
/* 733 */         command = command + prefix + "SLOT_CHEST = true";
/*     */       } 
/*     */     } 
/*     */     
/* 737 */     if (this.criteria.slotHands) {
/* 738 */       if (firstSlot) {
/* 739 */         firstSlot = false;
/*     */         
/* 741 */         command = command + " AND ( ";
/*     */       } else {
/* 743 */         command = command + " OR ";
/*     */       } 
/* 745 */       if (prefix == null) {
/* 746 */         command = command + "SLOT_HANDS = true";
/*     */       } else {
/* 748 */         command = command + prefix + "SLOT_HANDS = true";
/*     */       } 
/*     */     } 
/*     */     
/* 752 */     if (this.criteria.slotLegs) {
/* 753 */       if (firstSlot) {
/* 754 */         firstSlot = false;
/*     */         
/* 756 */         command = command + " AND ( ";
/*     */       } else {
/* 758 */         command = command + " OR ";
/*     */       } 
/* 760 */       if (prefix == null) {
/* 761 */         command = command + "SLOT_LEGS = true";
/*     */       } else {
/* 763 */         command = command + prefix + "SLOT_LEGS = true";
/*     */       } 
/*     */     } 
/*     */     
/* 767 */     if (this.criteria.slotFeet) {
/* 768 */       if (firstSlot) {
/* 769 */         firstSlot = false;
/*     */         
/* 771 */         command = command + " AND ( ";
/*     */       } else {
/* 773 */         command = command + " OR ";
/*     */       } 
/* 775 */       if (prefix == null) {
/* 776 */         command = command + "SLOT_FEET = true";
/*     */       } else {
/* 778 */         command = command + prefix + "SLOT_FEET = true";
/*     */       } 
/*     */     } 
/*     */     
/* 782 */     if (!firstSlot) command = command + " )";
/*     */     
/* 784 */     return command;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ItemCriteriaCombination> createItemCombinations() {
/* 792 */     List<ItemCriteriaCombination> list = new LinkedList<>();
/*     */     
/* 794 */     ItemCriteriaCombination combo = new ItemCriteriaCombination(this);
/*     */     
/* 796 */     boolean found = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 802 */     if (combo.usesItemRarity()) {
/* 803 */       for (String itemRarity : this.criteria.itemRarity) {
/* 804 */         combo.setItemRarity(itemRarity);
/*     */         
/* 806 */         if (combo.usesArmorClass()) {
/* 807 */           for (String armorClass : this.criteria.armorClass) {
/* 808 */             combo.setArmorClass(armorClass);
/*     */ 
/*     */             
/* 811 */             list.add(combo.clone());
/*     */           } 
/*     */           
/* 814 */           found = true;
/*     */         } 
/*     */         
/* 817 */         if (!found)
/*     */         {
/* 819 */           list.add(combo.clone());
/*     */         }
/*     */       } 
/*     */     } else {
/* 823 */       if (combo.usesArmorClass()) {
/* 824 */         for (String armorClass : this.criteria.armorClass) {
/* 825 */           combo.setArmorClass(armorClass);
/*     */ 
/*     */           
/* 828 */           list.add(combo.clone());
/*     */         } 
/*     */         
/* 831 */         found = true;
/*     */       } 
/*     */       
/* 834 */       if (combo.usesArtifactClass()) {
/* 835 */         for (String artifactClass : this.criteria.artifactClass) {
/* 836 */           combo.setArtifactClass(artifactClass);
/*     */ 
/*     */           
/* 839 */           list.add(combo.clone());
/*     */         } 
/*     */         
/* 842 */         found = true;
/*     */       } 
/*     */       
/* 845 */       if (!found)
/*     */       {
/* 847 */         list.add(combo.clone());
/*     */       }
/*     */     } 
/*     */     
/* 851 */     return list;
/*     */   }
/*     */   
/*     */   public List<ItemCriteriaCombination> createAffixCombinations() {
/* 855 */     List<ItemCriteriaCombination> list = new LinkedList<>();
/*     */     
/* 857 */     ItemCriteriaCombination combo = new ItemCriteriaCombination(this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 863 */     if (combo.usesItemRarity()) {
/* 864 */       for (String itemRarity : this.criteria.itemRarity) {
/* 865 */         combo.setItemRarity(itemRarity);
/*     */         
/* 867 */         list.add(combo.clone());
/*     */       } 
/*     */     } else {
/* 870 */       list.add(combo.clone());
/*     */     } 
/*     */     
/* 873 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\ItemCriteriaCombination.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */