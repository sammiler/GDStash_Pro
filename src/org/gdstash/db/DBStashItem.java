/*      */ package org.gdstash.db;public class DBStashItem implements Cloneable, ParameterSet { public static final String TABLE_NAME = "STASH_ITEM_V8"; public static final String TABLE_NAME_V1 = "STASH_ITEM"; public static final String TABLE_NAME_V2 = "STASH_ITEM_V2"; public static final String TABLE_NAME_V3 = "STASH_ITEM_V3"; public static final String TABLE_NAME_V4 = "STASH_ITEM_V4"; public static final String TABLE_NAME_V5 = "STASH_ITEM_V5"; public static final String TABLE_NAME_V6 = "STASH_ITEM_V6"; public static final String TABLE_NAME_V7 = "STASH_ITEM_V7"; public static final String TABLE_NAME_V8 = "STASH_ITEM_V8"; public static final int VERSION_STASH_FILE = 1; private static final int ROW_STASH_ID = 1; private static final int ROW_ITEM_ID = 2; private static final int ROW_PREFIX_ID = 3; private static final int ROW_SUFFIX_ID = 4; private static final int ROW_MODIFIER_ID = 5; private static final int ROW_TRANSMUTE_ID = 6; private static final int ROW_ITEM_SEED = 7; private static final int ROW_RELIC_ID = 8; private static final int ROW_RELICBONUS_ID = 9; private static final int ROW_RELIC_SEED = 10; private static final int ROW_ENCHANTMENT_ID = 11; private static final int ROW_UNKNOWN = 12; private static final int ROW_ENCHANTMENT_SEED = 13; private static final int ROW_ITEM_VAR1 = 14; private static final int ROW_STACKCOUNT = 15; private static final int ROW_HARDCORE = 16; private static final int ROW_CHARNAME = 17; private static final int ROW_SOULBOUND = 18; private static final int ROW_RARITY = 19; private static final int ROW_REQ_LEVEL = 20; private static final int ROW_REQ_DEX = 21; private static final int ROW_REQ_INT = 22; private static final int ROW_REQ_STR = 23; private static final int ROW_ITEM_CLASS = 24; private static final int ROW_ARMOR_CLASS = 25; private static final int ROW_ARTIFACT_CLASS = 26; private static final int ROW_ITEM_LEVEL = 27; private static final int ROW_SET_ID = 28; private static final int ROW_ITEM_NAME = 29; private static final int ROW_PET_BONUS_SKILL_ID = 30; private static final int ROW_PLUS_ALLSKILLS = 31; private static final int ROW_ITEM_SKILL_ID = 32; private static final int ROW_CONVERT_IN = 33; private static final int ROW_CONVERT_OUT = 34; private static final int ROW_CONVERT_IN_2 = 35; private static final int ROW_CONVERT_OUT_2 = 36; private static final int ROW_ENEMY_ONLY = 37; private static final int ROW_SLOT_AXE_1H = 38; private static final int ROW_SLOT_AXE_2H = 39; private static final int ROW_SLOT_DAGGER_1H = 40; private static final int ROW_SLOT_MACE_1H = 41; private static final int ROW_SLOT_MACE_2H = 42; private static final int ROW_SLOT_SCEPTER_1H = 43; private static final int ROW_SLOT_SPEAR_2H = 44; private static final int ROW_SLOT_STAFF_2H = 45; private static final int ROW_SLOT_SWORD_1H = 46; private static final int ROW_SLOT_SWORD_2H = 47; private static final int ROW_SLOT_RANGED_1H = 48; private static final int ROW_SLOT_RANGED_2H = 49; private static final int ROW_SLOT_SHIELD = 50; private static final int ROW_SLOT_OFFHAND = 51; private static final int ROW_SLOT_AMULET = 52; private static final int ROW_SLOT_BELT = 53; private static final int ROW_SLOT_MEDAL = 54; private static final int ROW_SLOT_RING = 55; private static final int ROW_SLOT_HEAD = 56; private static final int ROW_SLOT_SHOULDERS = 57;
/*      */   private static final int ROW_SLOT_CHEST = 58;
/*      */   private static final int ROW_SLOT_HANDS = 59;
/*      */   private static final int ROW_SLOT_LEGS = 60;
/*      */   private static final int ROW_SLOT_FEET = 61;
/*      */   private int stashID;
/*      */   private String itemID;
/*      */   private String prefixID;
/*      */   private String suffixID;
/*      */   private String modifierID;
/*      */   private String transmuteID;
/*      */   private int seed;
/*      */   private String relicID;
/*      */   private String relicBonusID;
/*      */   private int relicSeed;
/*      */   private String enchantmentID;
/*      */   private int unknown;
/*      */   private int enchantmentSeed;
/*      */   private int var1;
/*      */   private int stackCount;
/*      */   private boolean hardcore;
/*      */   private String charname;
/*      */   private boolean soulbound;
/*      */   private String rarity;
/*      */   private int reqLevel;
/*      */   private int reqDex;
/*      */   private int reqInt;
/*      */   private int reqStr;
/*      */   private String itemClass;
/*      */   private String armorClass;
/*      */   private String artifactClass;
/*      */   private int itemLevel;
/*      */   private String setID;
/*      */   private String itemName;
/*      */   private String petBonusSkillID;
/*      */   private int plusAllSkills;
/*      */   private String itemSkillID;
/*      */   private String convertIn;
/*      */   private String convertOut;
/*      */   private String convertIn2;
/*      */   private String convertOut2;
/*      */   private boolean enemyOnly;
/*      */   private ItemSlots slots;
/*      */   private DBItem dbItem;
/*      */   private DBAffix dbPrefix;
/*      */   private DBAffix dbSuffix;
/*      */   private DBAffix dbModifier;
/*      */   private DBItem dbComponent;
/*      */   private DBAffix dbBonus;
/*      */   private DBItem dbEnchantment;
/*      */   
/*   52 */   public static class DuplicateInfo { public GDItem.LabelInfo numItemCombo = new GDItem.LabelInfo();
/*   53 */     public GDItem.LabelInfo numItemPrefix = new GDItem.LabelInfo();
/*   54 */     public GDItem.LabelInfo levelItemPrefix = new GDItem.LabelInfo();
/*   55 */     public GDItem.LabelInfo numItemSuffix = new GDItem.LabelInfo();
/*   56 */     public GDItem.LabelInfo levelItemSuffix = new GDItem.LabelInfo();
/*   57 */     public GDItem.LabelInfo numClassCombo = new GDItem.LabelInfo(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DBStashItem() {
/*  184 */     this.stashID = -1;
/*      */     
/*  186 */     this.itemID = null;
/*  187 */     this.prefixID = null;
/*  188 */     this.suffixID = null;
/*  189 */     this.modifierID = null;
/*  190 */     this.transmuteID = null;
/*  191 */     this.seed = 0;
/*  192 */     this.relicID = null;
/*  193 */     this.relicBonusID = null;
/*  194 */     this.relicSeed = 0;
/*  195 */     this.enchantmentID = null;
/*  196 */     this.unknown = 0;
/*  197 */     this.enchantmentSeed = 0;
/*  198 */     this.var1 = 0;
/*  199 */     this.stackCount = 0;
/*  200 */     this.hardcore = false;
/*  201 */     this.charname = null;
/*      */     
/*  203 */     this.slots = new ItemSlots();
/*      */     
/*  205 */     fillDependentStats(null);
/*      */   }
/*      */   
/*      */   public DBStashItem(String charname, boolean hardcore) {
/*  209 */     this();
/*      */     
/*  211 */     this.charname = charname;
/*  212 */     this.hardcore = hardcore;
/*      */   }
/*      */   
/*      */   public DBStashItem(DBItem item) {
/*  216 */     this();
/*      */     
/*  218 */     if (item != null) {
/*  219 */       this.itemID = item.getItemID();
/*      */ 
/*      */       
/*  222 */       if (item.isComponent()) this.var1 = item.getComponentPieces();
/*      */     
/*      */     } 
/*  225 */     fillDependentStats(null);
/*      */   }
/*      */   
/*      */   public DBStashItem(DBStashItem item) {
/*  229 */     this.stashID = -1;
/*      */     
/*  231 */     this.itemID = item.itemID;
/*  232 */     this.prefixID = item.prefixID;
/*  233 */     this.suffixID = item.suffixID;
/*  234 */     this.modifierID = item.modifierID;
/*  235 */     this.transmuteID = item.transmuteID;
/*  236 */     this.seed = item.seed;
/*  237 */     this.relicID = item.relicID;
/*  238 */     this.relicBonusID = item.relicBonusID;
/*  239 */     this.relicSeed = item.relicSeed;
/*  240 */     this.enchantmentID = item.enchantmentID;
/*  241 */     this.unknown = item.unknown;
/*  242 */     this.enchantmentSeed = item.enchantmentSeed;
/*  243 */     this.var1 = item.var1;
/*  244 */     this.stackCount = item.stackCount;
/*  245 */     this.hardcore = item.hardcore;
/*  246 */     this.charname = item.charname;
/*      */     
/*  248 */     this.slots = item.slots.clone();
/*      */     
/*  250 */     fillDependentStats(null);
/*      */   }
/*      */ 
/*      */   
/*      */   public DBStashItem clone() {
/*  255 */     DBStashItem item = new DBStashItem(this);
/*      */     
/*  257 */     return item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStashID() {
/*  265 */     return this.stashID;
/*      */   }
/*      */   
/*      */   public String getItemID() {
/*  269 */     return this.itemID;
/*      */   }
/*      */   
/*      */   public String getPrefixID() {
/*  273 */     return this.prefixID;
/*      */   }
/*      */   
/*      */   public String getSuffixID() {
/*  277 */     return this.suffixID;
/*      */   }
/*      */   
/*      */   public String getModifierID() {
/*  281 */     return this.modifierID;
/*      */   }
/*      */   
/*      */   public String getTransmuteID() {
/*  285 */     return this.transmuteID;
/*      */   }
/*      */   
/*      */   public int getItemSeed() {
/*  289 */     return this.seed;
/*      */   }
/*      */   
/*      */   public String getComponentID() {
/*  293 */     return this.relicID;
/*      */   }
/*      */   
/*      */   public String getCompletionBonusID() {
/*  297 */     return this.relicBonusID;
/*      */   }
/*      */   
/*      */   public int getComponentSeed() {
/*  301 */     return this.relicSeed;
/*      */   }
/*      */   
/*      */   public String getAugmentID() {
/*  305 */     return this.enchantmentID;
/*      */   }
/*      */   
/*      */   public int getUnknown() {
/*  309 */     return this.unknown;
/*      */   }
/*      */   
/*      */   public int getAugmentSeed() {
/*  313 */     return this.enchantmentSeed;
/*      */   }
/*      */   
/*      */   public int getVar1() {
/*  317 */     return this.var1;
/*      */   }
/*      */   
/*      */   public int getStackCount() {
/*  321 */     return this.stackCount;
/*      */   }
/*      */   
/*      */   public boolean isHardcore() {
/*  325 */     return this.hardcore;
/*      */   }
/*      */   
/*      */   public String getCharName() {
/*  329 */     return this.charname;
/*      */   }
/*      */   
/*      */   public boolean isSoulbound() {
/*  333 */     return this.soulbound;
/*      */   }
/*      */   
/*      */   public String getRarity() {
/*  337 */     return this.rarity;
/*      */   }
/*      */   
/*      */   public int getRequiredlevel() {
/*  341 */     return this.reqLevel;
/*      */   }
/*      */   
/*      */   public int getItemSkillLevel() {
/*  345 */     if (this.dbItem == null) return 1;
/*      */     
/*  347 */     return this.dbItem.getItemSkillLevel();
/*      */   }
/*      */   
/*      */   public int getRequiredCunning() {
/*  351 */     return this.reqDex;
/*      */   }
/*      */   
/*      */   public int getRequiredPhysique() {
/*  355 */     return this.reqStr;
/*      */   }
/*      */   
/*      */   public int getRequiredSpirit() {
/*  359 */     return this.reqInt;
/*      */   }
/*      */   
/*      */   public DBItem getDBItem() {
/*  363 */     return this.dbItem;
/*      */   }
/*      */   
/*      */   public DBAffix getDBPrefix() {
/*  367 */     return this.dbPrefix;
/*      */   }
/*      */   
/*      */   public DBAffix getDBSuffix() {
/*  371 */     return this.dbSuffix;
/*      */   }
/*      */   
/*      */   public DBAffix getDBModifier() {
/*  375 */     return this.dbModifier;
/*      */   }
/*      */   
/*      */   public DBItem getDBComponent() {
/*  379 */     return this.dbComponent;
/*      */   }
/*      */   
/*      */   public DBAffix getDBCompletionBonus() {
/*  383 */     return this.dbBonus;
/*      */   }
/*      */   
/*      */   public DBItem getDBAugment() {
/*  387 */     return this.dbEnchantment;
/*      */   }
/*      */   
/*      */   public boolean isComponent() {
/*  391 */     if (this.dbItem == null) return false;
/*      */     
/*  393 */     return this.dbItem.isComponent();
/*      */   }
/*      */   
/*      */   public boolean isIncompleteComponent() {
/*  397 */     if (this.dbItem == null) return true;
/*      */     
/*  399 */     if (this.dbItem.isComponent()) {
/*  400 */       if (getVar1() == this.dbItem.getComponentPieces()) return false;
/*      */       
/*  402 */       return true;
/*      */     } 
/*      */     
/*  405 */     if (this.dbComponent == null) return true;
/*      */     
/*  407 */     if (getVar1() == this.dbComponent.getComponentPieces()) return false;
/*      */     
/*  409 */     return true;
/*      */   }
/*      */   
/*      */   public int getComponentPieces() {
/*  413 */     if (this.dbItem == null) return 0;
/*      */     
/*  415 */     if (this.dbItem.isComponent()) return this.dbItem.getComponentPieces();
/*      */     
/*  417 */     if (this.dbComponent == null) return 0;
/*      */     
/*  419 */     return this.dbComponent.getComponentPieces();
/*      */   }
/*      */   
/*      */   public boolean isStackable() {
/*  423 */     if (this.dbItem == null) return false;
/*      */ 
/*      */     
/*  426 */     if (this.dbItem.getItemClass().equals("ItemRelic")) {
/*      */ 
/*      */       
/*  429 */       if (getVar1() >= this.dbItem.getComponentPieces()) {
/*  430 */         return true;
/*      */       }
/*  432 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  436 */     return this.dbItem.isStackable();
/*      */   }
/*      */   
/*      */   public boolean isEpic() {
/*  440 */     if (this.dbItem == null) return false;
/*      */     
/*  442 */     return this.dbItem.isEpic();
/*      */   }
/*      */   
/*      */   public boolean isLegendary() {
/*  446 */     if (this.dbItem == null) return false;
/*      */     
/*  448 */     return this.dbItem.isLegendary();
/*      */   }
/*      */   
/*      */   public boolean isUnique() {
/*  452 */     if (this.dbItem == null) return false;
/*      */     
/*  454 */     return this.dbItem.isUnique();
/*      */   }
/*      */   
/*      */   public int getDefaultStackSize() {
/*  458 */     if (this.dbItem == null) return 1;
/*      */     
/*  460 */     int size = this.dbItem.getMaxStackSize();
/*      */     
/*  462 */     if (size == 0) {
/*  463 */       size = 1;
/*  464 */       String itemClass = this.dbItem.getItemClass();
/*      */ 
/*      */ 
/*      */       
/*  468 */       size = ItemClass.getDefaultStackSize(itemClass);
/*  469 */       if (itemClass.equals("QuestItem") && 
/*  470 */         DBItem.QUEST_ITEMS.contains(this.itemID)) size = 100;
/*      */     
/*      */     } 
/*      */     
/*  474 */     if (size > this.stackCount) size = this.stackCount;
/*      */     
/*  476 */     return size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStashID(int stashID) {
/*  484 */     this.stashID = stashID;
/*      */   }
/*      */   
/*      */   public void setItemID(String itemID) {
/*  488 */     this.itemID = itemID;
/*      */   }
/*      */   
/*      */   public void setPrefixID(String prefixID) {
/*  492 */     this.prefixID = prefixID;
/*      */   }
/*      */   
/*      */   public void setSuffixID(String suffixID) {
/*  496 */     this.suffixID = suffixID;
/*      */   }
/*      */   
/*      */   public void setModifierID(String modifierID) {
/*  500 */     this.modifierID = modifierID;
/*      */   }
/*      */   
/*      */   public void setTransmuteID(String transmuteID) {
/*  504 */     this.transmuteID = transmuteID;
/*      */   }
/*      */   
/*      */   public void setItemSeed(int seed) {
/*  508 */     this.seed = seed;
/*      */   }
/*      */   
/*      */   public void setComponentID(String relicID) {
/*  512 */     this.relicID = relicID;
/*      */   }
/*      */   
/*      */   public void setCompletionBonusID(String relicBonusID) {
/*  516 */     this.relicBonusID = relicBonusID;
/*      */   }
/*      */   
/*      */   public void setComponentSeed(int relicSeed) {
/*  520 */     this.relicSeed = relicSeed;
/*      */   }
/*      */   
/*      */   public void setAugmentID(String enchantmentID) {
/*  524 */     this.enchantmentID = enchantmentID;
/*      */   }
/*      */   
/*      */   public void setUnknown(int unknown) {
/*  528 */     this.unknown = unknown;
/*      */   }
/*      */   
/*      */   public void setAugmentSeed(int enchantmentSeed) {
/*  532 */     this.enchantmentSeed = enchantmentSeed;
/*      */   }
/*      */   
/*      */   public void setVar1(int var1) {
/*  536 */     this.var1 = var1;
/*      */   }
/*      */   
/*      */   public void setStackCount(int stackCount) {
/*  540 */     this.stackCount = stackCount;
/*      */   }
/*      */   
/*      */   public void setHardcore(boolean hardcore) {
/*  544 */     this.hardcore = hardcore;
/*      */   }
/*      */   
/*      */   public void setCharName(String charname) {
/*  548 */     this.charname = charname;
/*      */   }
/*      */   
/*      */   public void setSoulbound(boolean soulbound) {
/*  552 */     this.soulbound = soulbound;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillDependentStats(GDLog log) {
/*  560 */     this.soulbound = false;
/*  561 */     this.rarity = null;
/*  562 */     this.reqLevel = 0;
/*  563 */     this.reqDex = 0;
/*  564 */     this.reqInt = 0;
/*  565 */     this.reqStr = 0;
/*  566 */     this.itemClass = null;
/*  567 */     this.armorClass = null;
/*  568 */     this.artifactClass = null;
/*  569 */     this.itemLevel = 0;
/*  570 */     this.setID = null;
/*  571 */     this.itemName = null;
/*  572 */     this.petBonusSkillID = null;
/*  573 */     this.plusAllSkills = 0;
/*  574 */     this.itemSkillID = null;
/*  575 */     this.convertIn = null;
/*  576 */     this.convertOut = null;
/*  577 */     this.convertIn2 = null;
/*  578 */     this.convertOut2 = null;
/*  579 */     this.enemyOnly = false;
/*      */     
/*  581 */     if (this.slots != null) this.slots.clear();
/*      */     
/*  583 */     this.dbItem = null;
/*  584 */     this.dbPrefix = null;
/*  585 */     this.dbSuffix = null;
/*  586 */     this.dbModifier = null;
/*  587 */     this.dbComponent = null;
/*  588 */     this.dbBonus = null;
/*  589 */     this.dbEnchantment = null;
/*      */     
/*  591 */     if (getStackCount() == 0)
/*      */     {
/*  593 */       setStackCount(1);
/*      */     }
/*      */     
/*  596 */     if (getItemID() == null)
/*      */       return; 
/*  598 */     this.dbItem = DBItem.get(getItemID());
/*      */     
/*  600 */     if (this.dbItem == null) {
/*  601 */       Object[] args = { "", Integer.valueOf(0), getItemID() };
/*  602 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_ITEM_NOT_FOUND", args);
/*      */       
/*  604 */       if (log == null) {
/*  605 */         GDMsgLogger.addWarning(msg);
/*      */       } else {
/*  607 */         log.addWarning(msg);
/*      */       } 
/*      */     } 
/*      */     
/*  611 */     if (this.dbItem == null)
/*      */       return; 
/*  613 */     if (getPrefixID() != null) {
/*  614 */       this.dbPrefix = DBAffix.get(getPrefixID());
/*      */       
/*  616 */       if (this.dbPrefix == null) {
/*  617 */         Object[] args = { "", Integer.valueOf(0), this.dbItem.getItemName(), getPrefixID() };
/*  618 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_AFFIX_NOT_FOUND", args);
/*      */         
/*  620 */         if (log == null) {
/*  621 */           GDMsgLogger.addWarning(msg);
/*      */         } else {
/*  623 */           log.addWarning(msg);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  630 */     if (getSuffixID() != null) {
/*  631 */       this.dbSuffix = DBAffix.get(getSuffixID());
/*      */       
/*  633 */       if (this.dbSuffix == null) {
/*  634 */         Object[] args = { "", Integer.valueOf(0), this.dbItem.getItemName(), getSuffixID() };
/*  635 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_AFFIX_NOT_FOUND", args);
/*      */         
/*  637 */         if (log == null) {
/*  638 */           GDMsgLogger.addWarning(msg);
/*      */         } else {
/*  640 */           log.addWarning(msg);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  647 */     if (getModifierID() != null) {
/*  648 */       this.dbModifier = DBAffix.get(getModifierID());
/*      */       
/*  650 */       if (this.dbModifier == null) {
/*  651 */         Object[] args = { "", Integer.valueOf(0), this.dbItem.getItemName(), getModifierID() };
/*  652 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_AFFIX_NOT_FOUND", args);
/*      */         
/*  654 */         if (log == null) {
/*  655 */           GDMsgLogger.addWarning(msg);
/*      */         } else {
/*  657 */           log.addWarning(msg);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  664 */     if (getComponentID() != null) {
/*  665 */       this.dbComponent = DBItem.get(getComponentID());
/*      */       
/*  667 */       if (this.dbComponent == null) {
/*  668 */         Object[] args = { "", Integer.valueOf(0), getComponentID() };
/*  669 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_ITEM_NOT_FOUND", args);
/*      */         
/*  671 */         if (log == null) {
/*  672 */           GDMsgLogger.addWarning(msg);
/*      */         } else {
/*  674 */           log.addWarning(msg);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  682 */     if (getCompletionBonusID() != null) {
/*  683 */       this.dbBonus = DBAffix.get(getCompletionBonusID());
/*      */       
/*  685 */       if (this.dbBonus == null) {
/*  686 */         Object[] args = { "", Integer.valueOf(0), this.dbItem.getItemName(), getCompletionBonusID() };
/*  687 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_AFFIX_NOT_FOUND", args);
/*      */         
/*  689 */         if (log == null) {
/*  690 */           GDMsgLogger.addWarning(msg);
/*      */         } else {
/*  692 */           log.addWarning(msg);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  699 */     if (getAugmentID() != null) {
/*  700 */       this.dbEnchantment = DBItem.get(getAugmentID());
/*      */       
/*  702 */       if (this.dbEnchantment == null) {
/*  703 */         Object[] args = { "", Integer.valueOf(0), this.dbItem.getItemName(), getAugmentID() };
/*  704 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_AUGMENT_NOT_FOUND", args);
/*      */         
/*  706 */         if (log == null) {
/*  707 */           GDMsgLogger.addWarning(msg);
/*      */         } else {
/*  709 */           log.addWarning(msg);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  718 */     DBFormulaSet.Result result = this.dbItem.getStatRequirements(this);
/*      */     
/*  720 */     if (result != null) {
/*  721 */       this.reqDex = (int)result.cunning;
/*  722 */       this.reqInt = (int)result.spirit;
/*  723 */       this.reqStr = (int)result.physique;
/*      */     } 
/*      */ 
/*      */     
/*  727 */     if (this.dbItem.isComponent()) {
/*      */ 
/*      */ 
/*      */       
/*  731 */       if (getVar1() > this.dbItem.getComponentPieces()) setVar1(this.dbItem.getComponentPieces());
/*      */       
/*  733 */       if (getCompletionBonusID() != null) setCompletionBonusID(null);
/*      */     
/*      */     } 
/*  736 */     this.soulbound = this.dbItem.isSoulbound();
/*  737 */     if (this.dbComponent != null && 
/*  738 */       this.dbComponent.isSoulbound()) this.soulbound = true;
/*      */     
/*  740 */     if (this.dbEnchantment != null && 
/*  741 */       this.dbEnchantment.isSoulbound()) this.soulbound = true;
/*      */     
/*  743 */     if (this.charname == null) this.soulbound = false;
/*      */     
/*  745 */     this.rarity = determineRarity();
/*  746 */     this.reqLevel = determineRequiredLevel();
/*  747 */     this.itemClass = this.dbItem.getItemClass();
/*  748 */     this.armorClass = this.dbItem.getArmorClass();
/*  749 */     this.artifactClass = this.dbItem.getArtifactClass();
/*  750 */     this.itemLevel = this.dbItem.getItemLevel();
/*  751 */     this.setID = this.dbItem.getItemSetID();
/*  752 */     this.itemName = getCompleteName(false, false).toUpperCase(GDMsgFormatter.locale);
/*  753 */     this.petBonusSkillID = this.dbItem.getPetBonusSkillID();
/*  754 */     this.plusAllSkills = this.dbItem.getPlusAllSkills();
/*  755 */     this.itemSkillID = this.dbItem.getItemSkillID();
/*  756 */     this.convertIn = this.dbItem.getConvertIn();
/*  757 */     this.convertOut = this.dbItem.getConvertOut();
/*  758 */     this.convertIn2 = this.dbItem.getConvertIn2();
/*  759 */     this.convertOut2 = this.dbItem.getConvertOut2();
/*  760 */     this.enemyOnly = this.dbItem.isEnemyOnly();
/*  761 */     this.slots = this.dbItem.getSlots();
/*      */ 
/*      */     
/*  764 */     if (!this.soulbound && 
/*  765 */       !this.dbItem.isQuestItem()) this.charname = null;
/*      */   
/*      */   }
/*      */   
/*      */   private String appendNamePart(String s, int part, boolean withDmg) {
/*  770 */     if (this.dbItem == null) return s;
/*      */     
/*  772 */     String sPart = null;
/*      */     
/*  774 */     switch (part) {
/*      */       case 1:
/*  776 */         if (this.dbPrefix != null && 
/*  777 */           !this.dbItem.isHidePrefix()) {
/*  778 */           sPart = this.dbPrefix.getGenderText(this.dbItem.getGenderCode());
/*      */           
/*  780 */           if (sPart != null) {
/*  781 */             if (!s.isEmpty()) s = s + " ";
/*      */             
/*  783 */             s = s + sPart;
/*      */           } 
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/*  790 */         sPart = this.dbItem.getQualityText();
/*      */         
/*  792 */         if (sPart != null) {
/*  793 */           if (!s.isEmpty()) s = s + " ";
/*      */           
/*  795 */           s = s + sPart;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 3:
/*  800 */         sPart = this.dbItem.getStyleText();
/*      */         
/*  802 */         if (sPart != null) {
/*  803 */           if (!s.isEmpty()) s = s + " ";
/*      */           
/*  805 */           s = s + sPart;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 4:
/*  810 */         sPart = this.dbItem.getName(false);
/*      */         
/*  812 */         if (sPart != null) {
/*  813 */           if (!s.isEmpty()) s = s + " ";
/*      */           
/*  815 */           s = s + sPart;
/*      */           
/*  817 */           if (withDmg) {
/*  818 */             String dmg = this.dbItem.getMainDamageType();
/*      */             
/*  820 */             if (dmg != null) {
/*  821 */               s = s + " [" + dmg + "]";
/*      */             }
/*      */           } 
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 5:
/*  828 */         if (this.dbSuffix != null && 
/*  829 */           !this.dbItem.isHideSuffix()) {
/*  830 */           sPart = this.dbSuffix.getGenderText(this.dbItem.getGenderCode());
/*      */           
/*  832 */           if (sPart != null) {
/*  833 */             if (!s.isEmpty()) s = s + " ";
/*      */             
/*  835 */             s = s + sPart;
/*      */           } 
/*      */         } 
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  843 */     return s;
/*      */   }
/*      */   
/*      */   public String getCompleteName(boolean inclFaction, boolean inclMod) {
/*  847 */     if (this.dbItem == null) return null;
/*      */     
/*  849 */     String name = "";
/*      */     
/*  851 */     name = appendNamePart(name, GDStashFrame.dbConfig.namePart1, false);
/*  852 */     name = appendNamePart(name, GDStashFrame.dbConfig.namePart2, false);
/*  853 */     name = appendNamePart(name, GDStashFrame.dbConfig.namePart3, false);
/*  854 */     name = appendNamePart(name, GDStashFrame.dbConfig.namePart4, false);
/*  855 */     name = appendNamePart(name, GDStashFrame.dbConfig.namePart5, false);
/*      */     
/*  857 */     if (inclFaction && 
/*  858 */       this.dbItem.getFaction() != null) {
/*  859 */       name = name + " (" + this.dbItem.getFaction() + ")";
/*      */     }
/*      */ 
/*      */     
/*  863 */     if (inclMod) {
/*      */       
/*  865 */       String modName = this.dbItem.getModName();
/*  866 */       if (modName != null) {
/*  867 */         name = name + " " + modName;
/*      */       }
/*      */     } 
/*      */     
/*  871 */     return name;
/*      */   }
/*      */   
/*      */   public String determineRarity() {
/*  875 */     String itemRarity = "Common";
/*  876 */     String prefixRarity = "Common";
/*  877 */     String suffixRarity = "Common";
/*      */     
/*  879 */     if (this.dbItem == null) return "Broken";
/*      */     
/*  881 */     if (this.dbItem.getRarity() != null) itemRarity = this.dbItem.getRarity(); 
/*  882 */     if (this.dbPrefix != null && 
/*  883 */       this.dbPrefix.getRarity() != null) prefixRarity = this.dbPrefix.getRarity();
/*      */     
/*  885 */     if (this.dbSuffix != null && 
/*  886 */       this.dbSuffix.getRarity() != null) suffixRarity = this.dbSuffix.getRarity();
/*      */ 
/*      */     
/*  889 */     String rarity = null;
/*      */     
/*  891 */     rarity = determineBetterRarity(prefixRarity, suffixRarity);
/*  892 */     rarity = determineBetterRarity(itemRarity, rarity);
/*      */     
/*  894 */     return rarity;
/*      */   }
/*      */   
/*      */   private static String determineBetterRarity(String rarity1, String rarity2) {
/*  898 */     if (rarity1.equals("Quest")) return rarity1; 
/*  899 */     if (rarity2.equals("Quest")) return rarity2;
/*      */     
/*  901 */     String rarity = rarity1;
/*      */     
/*  903 */     if (rarity.equals("Legendary")) return rarity;
/*      */     
/*  905 */     if (rarity.equals("Epic") && 
/*  906 */       rarity2.equals("Legendary")) {
/*  907 */       rarity = rarity2;
/*      */     }
/*      */ 
/*      */     
/*  911 */     if (rarity.equals("Rare") && (
/*  912 */       rarity2.equals("Legendary") || rarity2
/*  913 */       .equals("Epic"))) {
/*  914 */       rarity = rarity2;
/*      */     }
/*      */ 
/*      */     
/*  918 */     if (rarity.equals("Magical") && (
/*  919 */       rarity2.equals("Legendary") || rarity2
/*  920 */       .equals("Epic") || rarity2
/*  921 */       .equals("Rare"))) {
/*  922 */       rarity = rarity2;
/*      */     }
/*      */ 
/*      */     
/*  926 */     if (rarity.equals("Common") && (
/*  927 */       rarity2.equals("Legendary") || rarity2
/*  928 */       .equals("Epic") || rarity2
/*  929 */       .equals("Rare") || rarity2
/*  930 */       .equals("Magical"))) {
/*  931 */       rarity = rarity2;
/*      */     }
/*      */ 
/*      */     
/*  935 */     return rarity;
/*      */   }
/*      */   
/*      */   public int determineRequiredLevel() {
/*  939 */     int level = 0;
/*      */     
/*  941 */     if (this.dbItem == null) return 0;
/*      */     
/*  943 */     if (this.dbItem != null && 
/*  944 */       this.dbItem.getRequiredlevel() >= level) level = this.dbItem.getRequiredlevel();
/*      */ 
/*      */     
/*  947 */     if (this.dbPrefix != null && 
/*  948 */       this.dbPrefix.getRequiredlevel() >= level) level = this.dbPrefix.getRequiredlevel();
/*      */ 
/*      */     
/*  951 */     if (this.dbSuffix != null && 
/*  952 */       this.dbSuffix.getRequiredlevel() >= level) level = this.dbSuffix.getRequiredlevel();
/*      */ 
/*      */     
/*  955 */     if (this.dbModifier != null && 
/*  956 */       this.dbModifier.getRequiredlevel() >= level) level = this.dbModifier.getRequiredlevel();
/*      */ 
/*      */     
/*  959 */     if (this.dbComponent != null && 
/*  960 */       this.dbComponent.getRequiredlevel() >= level) level = this.dbComponent.getRequiredlevel();
/*      */ 
/*      */     
/*  963 */     if (this.dbBonus != null && 
/*  964 */       this.dbBonus.getRequiredlevel() >= level) level = this.dbBonus.getRequiredlevel();
/*      */ 
/*      */     
/*  967 */     if (this.dbEnchantment != null && 
/*  968 */       this.dbEnchantment.getRequiredlevel() >= level) level = this.dbEnchantment.getRequiredlevel();
/*      */ 
/*      */     
/*  971 */     return level;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getItemClass() {
/*  981 */     if (this.dbItem == null) return null;
/*      */     
/*  983 */     return this.dbItem.getItemClass();
/*      */   }
/*      */ 
/*      */   
/*      */   public float getCharAttackSpeed() {
/*  988 */     if (this.dbItem == null) return 0.0F;
/*      */     
/*  990 */     return this.dbItem.getCharAttackSpeed();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDefenseAttrArmor() {
/*  995 */     if (this.dbItem == null) return 0;
/*      */     
/*  997 */     return this.dbItem.getDefenseAttrArmor();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getItemLevel() {
/* 1002 */     if (this.dbItem == null) return 0;
/*      */     
/* 1004 */     return this.dbItem.getItemLevel();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getShieldBlockDefense() {
/* 1009 */     if (this.dbItem == null) return 0;
/*      */     
/* 1011 */     return this.dbItem.getShieldBlockDefense();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getShieldBlockChance() {
/* 1016 */     if (this.dbItem == null) return 0;
/*      */     
/* 1018 */     return this.dbItem.getShieldBlockChance();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDamageAvgPierceRatio() {
/* 1023 */     if (this.dbItem == null) return 0;
/*      */     
/* 1025 */     return this.dbItem.getDamageAvgPierceRatio();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDamageAvgBase() {
/* 1030 */     if (this.dbItem == null) return 0;
/*      */     
/* 1032 */     return this.dbItem.getDamageAvgBase();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTotalAttCount() {
/* 1037 */     if (this.dbItem == null) return 0;
/*      */     
/* 1039 */     int total = this.dbItem.getTotalAttCount();
/*      */     
/* 1041 */     if (this.dbPrefix != null) total += this.dbPrefix.getTotalAttCount(); 
/* 1042 */     if (this.dbSuffix != null) total += this.dbSuffix.getTotalAttCount();
/*      */     
/* 1044 */     return total;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getItemPrefixCost() {
/* 1049 */     if (this.dbPrefix == null) return 0;
/*      */     
/* 1051 */     return this.dbPrefix.getLootRandomCost();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getItemSuffixCost() {
/* 1056 */     if (this.dbSuffix == null) return 0;
/*      */     
/* 1058 */     return this.dbSuffix.getLootRandomCost();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void createTable() throws SQLException {
/* 1067 */     String dropTable = "DROP TABLE STASH_ITEM_V8";
/* 1068 */     String createTable = "CREATE TABLE STASH_ITEM_V8 (STASH_ID             INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),ITEM_ID              VARCHAR(256) NOT NULL, PREFIX_ID            VARCHAR(256), SUFFIX_ID            VARCHAR(256), MODIFIER_ID          VARCHAR(256), TRANSMUTE_ID         VARCHAR(256), ITEM_SEED            INTEGER, RELIC_ID             VARCHAR(256), RELICBONUS_ID        VARCHAR(256), RELIC_SEED           INTEGER, ENCHANTMENT_ID       VARCHAR(256), UNKNOWN_VAR          INTEGER, ENCHANTMENT_SEED     INTEGER, ITEM_VAR1            INTEGER, STACK_COUNT          INTEGER, HARDCORE             BOOLEAN, CHARNAME             VARCHAR(64), SOULBOUND            BOOLEAN, RARITY               VARCHAR(32), REQ_LEVEL            INTEGER, REQ_DEX              INTEGER, REQ_INT              INTEGER, REQ_STR              INTEGER, ITEM_CLASS           VARCHAR(32), ARMOR_CLASS          VARCHAR(32), ARTIFACT_CLASS       VARCHAR(32), ITEM_LEVEL           INTEGER, SET_ID               VARCHAR(256), NAME                 VARCHAR(256), PET_BONUS_SKILL_ID   VARCHAR(256), PLUS_ALLSKILLS       INTEGER, ITEM_SKILL_ID        VARCHAR(256), COMPONENT_SKILL_ID   VARCHAR(256), ENCHANTMENT_SKILL_ID VARCHAR(256), CONVERT_IN           VARCHAR(16), CONVERT_OUT          VARCHAR(16), CONVERT_IN_2         VARCHAR(16), CONVERT_OUT_2        VARCHAR(16), ENEMY_ONLY           BOOLEAN, SLOT_AXE_1H          BOOLEAN, SLOT_AXE_2H          BOOLEAN, SLOT_DAGGER_1H       BOOLEAN, SLOT_MACE_1H         BOOLEAN, SLOT_MACE_2H         BOOLEAN, SLOT_SCEPTER_1H      BOOLEAN, SLOT_SPEAR_2H        BOOLEAN, SLOT_STAFF_2H        BOOLEAN, SLOT_SWORD_1H        BOOLEAN, SLOT_SWORD_2H        BOOLEAN, SLOT_RANGED_1H       BOOLEAN, SLOT_RANGED_2H       BOOLEAN, SLOT_SHIELD          BOOLEAN, SLOT_OFFHAND         BOOLEAN, SLOT_AMULET          BOOLEAN, SLOT_BELT            BOOLEAN, SLOT_MEDAL           BOOLEAN, SLOT_RING            BOOLEAN, SLOT_HEAD            BOOLEAN, SLOT_SHOULDERS       BOOLEAN, SLOT_CHEST           BOOLEAN, SLOT_HANDS           BOOLEAN, SLOT_LEGS            BOOLEAN, SLOT_FEET            BOOLEAN, PRIMARY KEY (STASH_ID))";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1134 */     try (Connection conn = GDDBData.getConnection()) {
/* 1135 */       boolean auto = conn.getAutoCommit();
/* 1136 */       conn.setAutoCommit(false);
/*      */       
/* 1138 */       try (Statement st = conn.createStatement()) {
/* 1139 */         if (GDDBUtil.tableExists(conn, "STASH_ITEM_V8")) {
/* 1140 */           st.execute(dropTable);
/*      */         }
/* 1142 */         st.execute(createTable);
/* 1143 */         st.close();
/*      */         
/* 1145 */         conn.commit();
/*      */       }
/* 1147 */       catch (SQLException ex) {
/* 1148 */         conn.rollback();
/*      */         
/* 1150 */         Object[] args = { "STASH_ITEM_V8" };
/* 1151 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*      */         
/* 1153 */         GDMsgLogger.addError(msg);
/*      */         
/* 1155 */         throw ex;
/*      */       } finally {
/*      */         
/* 1158 */         conn.setAutoCommit(auto);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeGDItems(List<GDItem> items) {
/* 1168 */     if (items == null)
/*      */       return; 
/* 1170 */     int iFound = 0;
/* 1171 */     int iInsert = 0;
/*      */     
/* 1173 */     for (GDItem item : items) {
/* 1174 */       boolean found = false;
/*      */       
/*      */       try {
/* 1177 */         found = isStored(item.getStashItem());
/*      */       }
/* 1179 */       catch (SQLException ex) {
/* 1180 */         Object[] arrayOfObject = { item.getItemID() };
/* 1181 */         String str = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_STASH_ITEM_SEARCH", arrayOfObject);
/*      */         
/* 1183 */         GDMsgLogger.addError(str);
/* 1184 */         GDMsgLogger.addError(ex);
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1189 */       if (found) {
/* 1190 */         iFound++;
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*      */       try {
/* 1196 */         insert(item.getStashItem());
/*      */         
/* 1198 */         iInsert++;
/*      */       }
/* 1200 */       catch (SQLException ex) {
/* 1201 */         Object[] arrayOfObject = { item.getItemID() };
/* 1202 */         String str = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_STASH_ITEM_STORE", arrayOfObject);
/*      */         
/* 1204 */         GDMsgLogger.addError(str);
/* 1205 */         GDMsgLogger.addError(ex);
/*      */       } 
/*      */     } 
/*      */     
/* 1209 */     Object[] args = { Integer.valueOf(iInsert) };
/* 1210 */     String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "INFO_STASH_STORED_NUM", args);
/*      */     
/* 1212 */     GDMsgLogger.addSuccess(msg);
/*      */     
/* 1214 */     Object[] args2 = { Integer.valueOf(iFound) };
/* 1215 */     msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "INFO_STASH_FOUND_NUM", args2);
/*      */     
/* 1217 */     GDMsgLogger.addWarning(msg);
/*      */   }
/*      */   
/*      */   public static void storeDBItems(List<DBStashItem> items) {
/* 1221 */     if (items == null)
/*      */       return; 
/* 1223 */     int iFound = 0;
/* 1224 */     int iInsert = 0;
/*      */     
/* 1226 */     for (DBStashItem item : items) {
/* 1227 */       boolean found = false;
/*      */       
/*      */       try {
/* 1230 */         found = isStored(item);
/*      */       }
/* 1232 */       catch (SQLException ex) {
/* 1233 */         Object[] arrayOfObject = { item.itemID };
/* 1234 */         String str = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_STASH_ITEM_SEARCH", arrayOfObject);
/*      */         
/* 1236 */         GDMsgLogger.addError(str);
/* 1237 */         GDMsgLogger.addError(ex);
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1242 */       if (found) {
/* 1243 */         iFound++;
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*      */       try {
/* 1249 */         insert(item);
/*      */         
/* 1251 */         iInsert++;
/*      */       }
/* 1253 */       catch (SQLException ex) {
/* 1254 */         Object[] arrayOfObject = { item.itemID };
/* 1255 */         String str = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_STASH_ITEM_STORE", arrayOfObject);
/*      */         
/* 1257 */         GDMsgLogger.addError(str);
/* 1258 */         GDMsgLogger.addError(ex);
/*      */       } 
/*      */     } 
/*      */     
/* 1262 */     Object[] args = { Integer.valueOf(iInsert) };
/* 1263 */     String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "INFO_STASH_STORED_NUM", args);
/*      */     
/* 1265 */     GDMsgLogger.addError(msg);
/*      */     
/* 1267 */     Object[] args2 = { Integer.valueOf(iFound) };
/* 1268 */     msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "INFO_STASH_FOUND_NUM", args2);
/*      */   }
/*      */   
/*      */   public static boolean storeItem(GDItem item) {
/* 1272 */     return storeItem(item.getStashItem());
/*      */   }
/*      */   
/*      */   public static boolean storeItem(DBStashItem item) {
/* 1276 */     boolean success = false;
/* 1277 */     boolean found = false;
/*      */     
/*      */     try {
/* 1280 */       found = isStored(item);
/*      */     }
/* 1282 */     catch (SQLException ex) {
/* 1283 */       Object[] args = { item.itemID };
/* 1284 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_STASH_ITEM_STORE", args);
/*      */       
/* 1286 */       GDMsgLogger.addError(msg);
/* 1287 */       GDMsgLogger.addError(ex);
/*      */       
/* 1289 */       return false;
/*      */     } 
/*      */     
/* 1292 */     if (found) {
/* 1293 */       Object[] args = { item.itemID };
/* 1294 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_STASH_ITEM_IN_STORE", args);
/*      */       
/* 1296 */       GDMsgLogger.addWarning(msg);
/*      */       
/* 1298 */       return false;
/*      */     } 
/*      */     
/*      */     try {
/* 1302 */       insert(item);
/*      */       
/* 1304 */       success = true;
/*      */     }
/* 1306 */     catch (SQLException ex) {
/* 1307 */       Object[] args = { item.itemID };
/* 1308 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_STASH_ITEM_STORE", args);
/*      */       
/* 1310 */       GDMsgLogger.addError(msg);
/* 1311 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 1314 */     return success;
/*      */   }
/*      */   
/*      */   private static void insert(DBStashItem item) throws SQLException {
/* 1318 */     if (item.isStackable()) {
/* 1319 */       insertStack(item);
/*      */     } else {
/* 1321 */       insertNonStack(item);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertStack(DBStashItem item) throws SQLException {
/* 1326 */     DBStashItem si = getStashItemForStack(item);
/*      */     
/* 1328 */     if (si == null) {
/*      */       
/* 1330 */       insertNonStack(item);
/*      */     } else {
/* 1332 */       updateStackCount(si.stashID, si.stackCount + item.stackCount);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void updateStackCount(int stashID, int stackSize) throws SQLException {
/* 1337 */     try (Connection conn = GDDBData.getConnection()) {
/* 1338 */       boolean auto = conn.getAutoCommit();
/* 1339 */       conn.setAutoCommit(false);
/*      */       
/*      */       try {
/* 1342 */         updateStackCount(conn, stashID, stackSize);
/*      */         
/* 1344 */         conn.commit();
/*      */       }
/* 1346 */       catch (SQLException ex) {
/* 1347 */         conn.rollback();
/*      */         
/* 1349 */         throw ex;
/*      */       } finally {
/*      */         
/* 1352 */         conn.setAutoCommit(auto);
/*      */       }
/*      */     
/* 1355 */     } catch (SQLException ex) {
/* 1356 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void updateStackCount(Connection conn, int stashID, int stackSize) throws SQLException {
/* 1361 */     String update = "UPDATE STASH_ITEM_V8 SET STACK_COUNT = ? WHERE STASH_ID = ?";
/*      */     
/* 1363 */     try (PreparedStatement ps = conn.prepareStatement(update)) {
/* 1364 */       ps.setInt(1, stackSize);
/* 1365 */       ps.setInt(2, stashID);
/*      */       
/* 1367 */       ps.executeUpdate();
/* 1368 */       ps.close();
/*      */     }
/* 1370 */     catch (SQLException ex) {
/* 1371 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertNonStack(DBStashItem item) throws SQLException {
/* 1376 */     try (Connection conn = GDDBData.getConnection()) {
/* 1377 */       boolean auto = conn.getAutoCommit();
/* 1378 */       conn.setAutoCommit(false);
/*      */       
/*      */       try {
/* 1381 */         insertNonStack(conn, item);
/*      */         
/* 1383 */         conn.commit();
/*      */       }
/* 1385 */       catch (SQLException ex) {
/* 1386 */         conn.rollback();
/*      */         
/* 1388 */         throw ex;
/*      */       } finally {
/*      */         
/* 1391 */         conn.setAutoCommit(auto);
/*      */       }
/*      */     
/* 1394 */     } catch (SQLException ex) {
/* 1395 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertNonStack(Connection conn, DBStashItem item) throws SQLException {
/* 1400 */     String insert = "INSERT INTO STASH_ITEM_V8(ITEM_ID, PREFIX_ID, SUFFIX_ID, MODIFIER_ID, TRANSMUTE_ID, ITEM_SEED, RELIC_ID, RELICBONUS_ID, RELIC_SEED, ENCHANTMENT_ID, UNKNOWN_VAR, ENCHANTMENT_SEED, ITEM_VAR1, STACK_COUNT, HARDCORE, CHARNAME, SOULBOUND, RARITY, REQ_LEVEL, REQ_DEX, REQ_INT, REQ_STR, ITEM_CLASS, ARMOR_CLASS, ARTIFACT_CLASS, ITEM_LEVEL, SET_ID, NAME, PET_BONUS_SKILL_ID, PLUS_ALLSKILLS, ITEM_SKILL_ID, CONVERT_IN, CONVERT_OUT, CONVERT_IN_2, CONVERT_OUT_2, ENEMY_ONLY, SLOT_AXE_1H, SLOT_AXE_2H, SLOT_DAGGER_1H, SLOT_MACE_1H, SLOT_MACE_2H, SLOT_SCEPTER_1H, SLOT_SPEAR_2H, SLOT_STAFF_2H, SLOT_SWORD_1H, SLOT_SWORD_2H, SLOT_RANGED_1H, SLOT_RANGED_2H, SLOT_SHIELD, SLOT_OFFHAND, SLOT_AMULET, SLOT_BELT, SLOT_MEDAL, SLOT_RING, SLOT_HEAD, SLOT_SHOULDERS, SLOT_CHEST, SLOT_HANDS, SLOT_LEGS, SLOT_FEET) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1411 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 1412 */       ps.setString(1, item.itemID);
/* 1413 */       if (item.prefixID == null) { ps.setString(2, ""); }
/* 1414 */       else { ps.setString(2, item.prefixID); }
/* 1415 */        if (item.suffixID == null) { ps.setString(3, ""); }
/* 1416 */       else { ps.setString(3, item.suffixID); }
/* 1417 */        if (item.modifierID == null) { ps.setString(4, ""); }
/* 1418 */       else { ps.setString(4, item.modifierID); }
/* 1419 */        if (item.transmuteID == null) { ps.setString(5, ""); }
/* 1420 */       else { ps.setString(5, item.transmuteID); }
/* 1421 */        ps.setInt(6, item.seed);
/* 1422 */       if (item.relicID == null) { ps.setString(7, ""); }
/* 1423 */       else { ps.setString(7, item.relicID); }
/* 1424 */        if (item.relicBonusID == null) { ps.setString(8, ""); }
/* 1425 */       else { ps.setString(8, item.relicBonusID); }
/* 1426 */        ps.setInt(9, item.relicSeed);
/* 1427 */       if (item.enchantmentID == null) { ps.setString(10, ""); }
/* 1428 */       else { ps.setString(10, item.enchantmentID); }
/* 1429 */        ps.setInt(11, item.unknown);
/* 1430 */       ps.setInt(12, item.enchantmentSeed);
/* 1431 */       ps.setInt(13, item.var1);
/* 1432 */       ps.setInt(14, item.stackCount);
/* 1433 */       ps.setBoolean(15, item.hardcore);
/* 1434 */       if (item.charname == null) { ps.setString(16, ""); }
/* 1435 */       else { ps.setString(16, item.charname); }
/* 1436 */        ps.setBoolean(17, item.soulbound);
/* 1437 */       ps.setString(18, item.rarity);
/* 1438 */       ps.setInt(19, item.reqLevel);
/* 1439 */       ps.setInt(20, item.reqDex);
/* 1440 */       ps.setInt(21, item.reqInt);
/* 1441 */       ps.setInt(22, item.reqStr);
/* 1442 */       ps.setString(23, item.itemClass);
/* 1443 */       ps.setString(24, item.armorClass);
/* 1444 */       ps.setString(25, item.artifactClass);
/* 1445 */       ps.setInt(26, item.itemLevel);
/* 1446 */       ps.setString(27, item.setID);
/* 1447 */       ps.setString(28, item.itemName);
/* 1448 */       ps.setString(29, item.petBonusSkillID);
/* 1449 */       ps.setInt(30, item.plusAllSkills);
/* 1450 */       ps.setString(31, item.itemSkillID);
/* 1451 */       ps.setString(32, item.convertIn);
/* 1452 */       ps.setString(33, item.convertOut);
/* 1453 */       ps.setString(34, item.convertIn2);
/* 1454 */       ps.setString(35, item.convertOut2);
/* 1455 */       ps.setBoolean(36, item.enemyOnly);
/* 1456 */       ps.setBoolean(37, item.slots.slotAxe1H);
/* 1457 */       ps.setBoolean(38, item.slots.slotAxe2H);
/* 1458 */       ps.setBoolean(39, item.slots.slotDagger1H);
/* 1459 */       ps.setBoolean(40, item.slots.slotMace1H);
/* 1460 */       ps.setBoolean(41, item.slots.slotMace2H);
/* 1461 */       ps.setBoolean(42, item.slots.slotScepter1H);
/* 1462 */       ps.setBoolean(43, item.slots.slotSpear1H);
/* 1463 */       ps.setBoolean(44, item.slots.slotStaff2H);
/* 1464 */       ps.setBoolean(45, item.slots.slotSword1H);
/* 1465 */       ps.setBoolean(46, item.slots.slotSword2H);
/* 1466 */       ps.setBoolean(47, item.slots.slotRanged1H);
/* 1467 */       ps.setBoolean(48, item.slots.slotRanged2H);
/* 1468 */       ps.setBoolean(49, item.slots.slotShield);
/* 1469 */       ps.setBoolean(50, item.slots.slotOffhand);
/* 1470 */       ps.setBoolean(51, item.slots.slotAmulet);
/* 1471 */       ps.setBoolean(52, item.slots.slotBelt);
/* 1472 */       ps.setBoolean(53, item.slots.slotMedal);
/* 1473 */       ps.setBoolean(54, item.slots.slotRing);
/* 1474 */       ps.setBoolean(55, item.slots.slotHead);
/* 1475 */       ps.setBoolean(56, item.slots.slotShoulders);
/* 1476 */       ps.setBoolean(57, item.slots.slotChest);
/* 1477 */       ps.setBoolean(58, item.slots.slotHands);
/* 1478 */       ps.setBoolean(59, item.slots.slotLegs);
/* 1479 */       ps.setBoolean(60, item.slots.slotFeet);
/* 1480 */       ps.executeUpdate();
/* 1481 */       ps.close();
/*      */     }
/* 1483 */     catch (SQLException ex) {
/* 1484 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean isStored(GDItem item) throws SQLException {
/* 1489 */     return isStored(item.getStashItem());
/*      */   }
/*      */   
/*      */   private static boolean isStored(DBStashItem item) throws SQLException {
/* 1493 */     if (item.isStackable())
/*      */     {
/* 1495 */       return false;
/*      */     }
/* 1497 */     return isStoredNonStack(item);
/*      */   }
/*      */ 
/*      */   
/*      */   private static DBStashItem getStashItemForStack(DBStashItem item) throws SQLException {
/* 1502 */     DBStashItem si = null;
/*      */     
/* 1504 */     String command = "SELECT * FROM STASH_ITEM_V8 WHERE ITEM_ID = ? AND ITEM_VAR1 = ? AND HARDCORE = ? AND CHARNAME = ?";
/*      */ 
/*      */ 
/*      */     
/* 1508 */     try(Connection conn = GDDBData.getConnection(); 
/* 1509 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 1510 */       ps.setString(1, item.itemID);
/* 1511 */       ps.setInt(2, item.var1);
/* 1512 */       ps.setBoolean(3, item.hardcore);
/* 1513 */       if (item.charname == null) {
/* 1514 */         ps.setString(4, "");
/*      */       } else {
/* 1516 */         ps.setString(4, item.charname);
/*      */       } 
/*      */       
/* 1519 */       try (ResultSet rs = ps.executeQuery()) {
/* 1520 */         List<DBStashItem> list = wrap(rs);
/*      */         
/* 1522 */         if (list.isEmpty()) { si = null; }
/* 1523 */         else { si = list.get(0); }
/*      */         
/* 1525 */         conn.commit();
/*      */       }
/* 1527 */       catch (SQLException ex) {
/* 1528 */         si = null;
/*      */         
/* 1530 */         throw ex;
/*      */       }
/*      */     
/* 1533 */     } catch (SQLException ex) {
/* 1534 */       throw ex;
/*      */     } 
/*      */     
/* 1537 */     return si;
/*      */   }
/*      */   
/*      */   private static int getStashIDForStack(DBStashItem item) throws SQLException {
/* 1541 */     int id = -1;
/*      */     
/* 1543 */     String command = "SELECT STASH_ID FROM STASH_ITEM_V8 WHERE ITEM_ID = ? AND HARDCORE = ? AND CHARNAME = ?";
/*      */ 
/*      */ 
/*      */     
/* 1547 */     try(Connection conn = GDDBData.getConnection(); 
/* 1548 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 1549 */       ps.setString(1, item.itemID);
/* 1550 */       ps.setBoolean(2, item.hardcore);
/* 1551 */       if (item.charname == null) {
/* 1552 */         ps.setString(3, "");
/*      */       } else {
/* 1554 */         ps.setString(3, item.charname);
/*      */       } 
/*      */       
/* 1557 */       try (ResultSet rs = ps.executeQuery()) {
/* 1558 */         if (rs.next()) {
/* 1559 */           id = rs.getInt(1);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1564 */         conn.commit();
/*      */       }
/* 1566 */       catch (SQLException ex) {
/* 1567 */         id = -1;
/*      */         
/* 1569 */         throw ex;
/*      */       }
/*      */     
/* 1572 */     } catch (SQLException ex) {
/* 1573 */       throw ex;
/*      */     } 
/*      */     
/* 1576 */     return id;
/*      */   }
/*      */   
/*      */   private static boolean isStoredStack(DBStashItem item) throws SQLException {
/* 1580 */     boolean found = false;
/*      */     
/* 1582 */     String command = "SELECT * FROM STASH_ITEM_V8 WHERE ITEM_ID = ? AND HARDCORE = ? AND CHARNAME = ?";
/*      */ 
/*      */ 
/*      */     
/* 1586 */     try(Connection conn = GDDBData.getConnection(); 
/* 1587 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 1588 */       ps.setString(1, item.itemID);
/* 1589 */       ps.setBoolean(2, item.hardcore);
/* 1590 */       if (item.charname == null) {
/* 1591 */         ps.setString(3, "");
/*      */       } else {
/* 1593 */         ps.setString(3, item.charname);
/*      */       } 
/*      */       
/* 1596 */       try (ResultSet rs = ps.executeQuery()) {
/* 1597 */         if (rs.next()) {
/* 1598 */           found = true;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1603 */         conn.commit();
/*      */       }
/* 1605 */       catch (SQLException ex) {
/* 1606 */         throw ex;
/*      */       }
/*      */     
/* 1609 */     } catch (SQLException ex) {
/* 1610 */       throw ex;
/*      */     } 
/*      */     
/* 1613 */     return found;
/*      */   }
/*      */   
/*      */   private static boolean isStoredNonStack(DBStashItem item) throws SQLException {
/* 1617 */     boolean found = false;
/*      */     
/* 1619 */     String command = "SELECT * FROM STASH_ITEM_V8 WHERE ITEM_ID = ? AND PREFIX_ID = ? AND SUFFIX_ID = ? AND MODIFIER_ID = ? AND TRANSMUTE_ID = ? AND ITEM_SEED = ? AND RELIC_ID = ? AND RELICBONUS_ID = ? AND RELIC_SEED = ? AND ENCHANTMENT_ID =? AND UNKNOWN_VAR = ? AND ENCHANTMENT_SEED = ? AND ITEM_VAR1 = ? AND STACK_COUNT = ? AND HARDCORE = ? AND CHARNAME = ?";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1626 */     try(Connection conn = GDDBData.getConnection(); 
/* 1627 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 1628 */       ps.setString(1, item.itemID);
/* 1629 */       if (item.prefixID == null) { ps.setString(2, ""); }
/* 1630 */       else { ps.setString(2, item.prefixID); }
/* 1631 */        if (item.suffixID == null) { ps.setString(3, ""); }
/* 1632 */       else { ps.setString(3, item.suffixID); }
/* 1633 */        if (item.modifierID == null) { ps.setString(4, ""); }
/* 1634 */       else { ps.setString(4, item.modifierID); }
/* 1635 */        if (item.transmuteID == null) { ps.setString(5, ""); }
/* 1636 */       else { ps.setString(5, item.transmuteID); }
/* 1637 */        ps.setInt(6, item.seed);
/* 1638 */       if (item.relicID == null) { ps.setString(7, ""); }
/* 1639 */       else { ps.setString(7, item.relicID); }
/* 1640 */        if (item.relicBonusID == null) { ps.setString(8, ""); }
/* 1641 */       else { ps.setString(8, item.relicBonusID); }
/* 1642 */        ps.setInt(9, item.relicSeed);
/* 1643 */       if (item.enchantmentID == null) { ps.setString(10, ""); }
/* 1644 */       else { ps.setString(10, item.enchantmentID); }
/* 1645 */        ps.setInt(11, item.unknown);
/* 1646 */       ps.setInt(12, item.enchantmentSeed);
/* 1647 */       ps.setInt(13, item.var1);
/* 1648 */       ps.setInt(14, item.stackCount);
/* 1649 */       ps.setBoolean(15, item.hardcore);
/* 1650 */       if (item.charname == null) { ps.setString(16, ""); }
/* 1651 */       else { ps.setString(16, item.charname); }
/*      */       
/* 1653 */       try (ResultSet rs = ps.executeQuery()) {
/* 1654 */         if (rs.next()) {
/* 1655 */           found = true;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1660 */         conn.commit();
/*      */       }
/* 1662 */       catch (SQLException ex) {
/* 1663 */         throw ex;
/*      */       }
/*      */     
/* 1666 */     } catch (SQLException ex) {
/* 1667 */       throw ex;
/*      */     } 
/*      */     
/* 1670 */     return found;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean delete(GDItem item) {
/* 1678 */     return delete(item.getStashItem());
/*      */   }
/*      */   
/*      */   private static boolean delete(DBStashItem item) {
/* 1682 */     if (item.isStackable()) {
/* 1683 */       return deleteStack(item);
/*      */     }
/* 1685 */     return deleteNonStack(item);
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean deleteStack(DBStashItem item) {
/* 1690 */     DBStashItem si = null;
/*      */     
/*      */     try {
/* 1693 */       si = getStashItemForStack(item);
/*      */     }
/* 1695 */     catch (SQLException ex) {
/* 1696 */       return false;
/*      */     } 
/*      */     
/* 1699 */     if (si == null) {
/* 1700 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1704 */     if (si.stackCount <= item.stackCount) {
/* 1705 */       deleteStackDB(si);
/*      */     } else {
/*      */       try {
/* 1708 */         updateStackCount(si.stashID, si.stackCount - item.stackCount);
/*      */       }
/* 1710 */       catch (SQLException ex) {
/* 1711 */         return false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1716 */     return true;
/*      */   }
/*      */   
/*      */   private static boolean deleteStackDB(DBStashItem item) {
/* 1720 */     if (item == null) return false;
/*      */     
/* 1722 */     boolean success = false;
/*      */ 
/*      */ 
/*      */     
/* 1726 */     String command = "DELETE FROM STASH_ITEM_V8 WHERE ITEM_ID = ? AND ITEM_VAR1 = ? AND HARDCORE = ? AND CHARNAME = ?";
/*      */ 
/*      */ 
/*      */     
/* 1730 */     try (Connection conn = GDDBData.getConnection()) {
/* 1731 */       boolean auto = conn.getAutoCommit();
/* 1732 */       conn.setAutoCommit(false);
/*      */       
/* 1734 */       try (PreparedStatement ps = conn.prepareStatement(command)) {
/* 1735 */         ps.setString(1, item.itemID);
/* 1736 */         ps.setInt(2, item.var1);
/* 1737 */         ps.setBoolean(3, item.hardcore);
/* 1738 */         if (item.charname == null) {
/* 1739 */           ps.setString(4, "");
/*      */         } else {
/* 1741 */           ps.setString(4, item.charname);
/*      */         } 
/*      */         
/* 1744 */         ps.executeUpdate();
/* 1745 */         ps.close();
/*      */         
/* 1747 */         conn.commit();
/*      */         
/* 1749 */         success = true;
/*      */       }
/* 1751 */       catch (SQLException ex) {
/* 1752 */         conn.rollback();
/*      */         
/* 1754 */         throw ex;
/*      */       } finally {
/*      */         
/* 1757 */         conn.setAutoCommit(auto);
/*      */       }
/*      */     
/* 1760 */     } catch (SQLException ex) {
/* 1761 */       Object[] args = { item.itemID };
/* 1762 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_STASH_ITEM_DELETE", args);
/*      */       
/* 1764 */       GDMsgLogger.addError(msg);
/* 1765 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 1768 */     return success;
/*      */   }
/*      */   
/*      */   private static boolean deleteNonStack(DBStashItem item) {
/* 1772 */     if (item == null) return false;
/*      */     
/* 1774 */     boolean success = false;
/*      */     
/* 1776 */     String command = "DELETE FROM STASH_ITEM_V8 WHERE ITEM_ID = ? AND PREFIX_ID = ? AND SUFFIX_ID = ? AND MODIFIER_ID = ? AND TRANSMUTE_ID = ? AND ITEM_SEED = ? AND RELIC_ID = ? AND RELICBONUS_ID = ? AND RELIC_SEED = ? AND ENCHANTMENT_ID =? AND UNKNOWN_VAR = ? AND ENCHANTMENT_SEED = ? AND ITEM_VAR1 = ? AND STACK_COUNT = ? AND HARDCORE = ? AND CHARNAME = ?";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1783 */     try (Connection conn = GDDBData.getConnection()) {
/* 1784 */       boolean auto = conn.getAutoCommit();
/* 1785 */       conn.setAutoCommit(false);
/*      */       
/* 1787 */       try (PreparedStatement ps = conn.prepareStatement(command)) {
/* 1788 */         ps.setString(1, item.itemID);
/* 1789 */         if (item.prefixID == null) { ps.setString(2, ""); }
/* 1790 */         else { ps.setString(2, item.prefixID); }
/* 1791 */          if (item.suffixID == null) { ps.setString(3, ""); }
/* 1792 */         else { ps.setString(3, item.suffixID); }
/* 1793 */          if (item.modifierID == null) { ps.setString(4, ""); }
/* 1794 */         else { ps.setString(4, item.modifierID); }
/* 1795 */          if (item.transmuteID == null) { ps.setString(5, ""); }
/* 1796 */         else { ps.setString(5, item.transmuteID); }
/* 1797 */          ps.setInt(6, item.seed);
/* 1798 */         if (item.relicID == null) { ps.setString(7, ""); }
/* 1799 */         else { ps.setString(7, item.relicID); }
/* 1800 */          if (item.relicBonusID == null) { ps.setString(8, ""); }
/* 1801 */         else { ps.setString(8, item.relicBonusID); }
/* 1802 */          ps.setInt(9, item.relicSeed);
/* 1803 */         if (item.enchantmentID == null) { ps.setString(10, ""); }
/* 1804 */         else { ps.setString(10, item.enchantmentID); }
/* 1805 */          ps.setInt(11, item.unknown);
/* 1806 */         ps.setInt(12, item.enchantmentSeed);
/* 1807 */         ps.setInt(13, item.var1);
/* 1808 */         ps.setInt(14, item.stackCount);
/* 1809 */         ps.setBoolean(15, item.hardcore);
/* 1810 */         if (item.charname == null) { ps.setString(16, ""); }
/* 1811 */         else { ps.setString(16, item.charname); }
/*      */         
/* 1813 */         ps.executeUpdate();
/* 1814 */         ps.close();
/*      */         
/* 1816 */         conn.commit();
/*      */         
/* 1818 */         success = true;
/*      */       }
/* 1820 */       catch (SQLException ex) {
/* 1821 */         conn.rollback();
/*      */         
/* 1823 */         throw ex;
/*      */       } finally {
/*      */         
/* 1826 */         conn.setAutoCommit(auto);
/*      */       }
/*      */     
/* 1829 */     } catch (SQLException ex) {
/* 1830 */       Object[] args = { item.itemID };
/* 1831 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_STASH_ITEM_DELETE", args);
/*      */       
/* 1833 */       GDMsgLogger.addError(msg);
/* 1834 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 1837 */     return success;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateDependentFields(List<DBStashItem> items) {
/* 1845 */     if (items == null)
/*      */       return; 
/* 1847 */     int iFound = 0;
/* 1848 */     int iMissing = 0;
/* 1849 */     int iRemoved = 0;
/* 1850 */     int iError = 0;
/*      */     
/* 1852 */     for (DBStashItem item : items) {
/* 1853 */       if (item.stashID == -1) {
/* 1854 */         iMissing++;
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/* 1862 */         if (item.getDBItem() == null) {
/* 1863 */           iRemoved++;
/*      */           
/* 1865 */           deleteItemByStashID(item); continue;
/*      */         } 
/* 1867 */         updateDependentFields(item);
/*      */         
/* 1869 */         iFound++;
/*      */       
/*      */       }
/* 1872 */       catch (SQLException ex) {
/* 1873 */         iError++;
/*      */       } 
/*      */     } 
/*      */     
/* 1877 */     if (iFound > 0) {
/* 1878 */       Object[] args = { Integer.valueOf(iFound) };
/* 1879 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "INFO_STASH_UPD_FOUND", args);
/*      */       
/* 1881 */       GDMsgLogger.addInfo(msg);
/*      */     } 
/*      */     
/* 1884 */     if (iMissing > 0) {
/* 1885 */       Object[] args = { Integer.valueOf(iMissing) };
/* 1886 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "INFO_STASH_UPD_MISS", args);
/*      */       
/* 1888 */       GDMsgLogger.addWarning(msg);
/*      */     } 
/*      */     
/* 1891 */     if (iRemoved > 0) {
/* 1892 */       Object[] args = { Integer.valueOf(iRemoved) };
/* 1893 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "INFO_STASH_CONV_DEL", args);
/*      */       
/* 1895 */       GDMsgLogger.addWarning(msg);
/*      */     } 
/*      */     
/* 1898 */     if (iError > 0) {
/* 1899 */       Object[] args = { Integer.valueOf(iError) };
/* 1900 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "INFO_STASH_UPD_ERROR", args);
/*      */       
/* 1902 */       GDMsgLogger.addError(msg);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void deleteItemByStashID(DBStashItem item) throws SQLException {
/* 1907 */     if (item == null)
/* 1908 */       return;  if (item.stashID == -1)
/*      */       return; 
/* 1910 */     String command = "DELETE FROM STASH_ITEM_V8 WHERE STASH_ID = ?";
/*      */ 
/*      */     
/* 1913 */     try (Connection conn = GDDBData.getConnection()) {
/* 1914 */       boolean auto = conn.getAutoCommit();
/* 1915 */       conn.setAutoCommit(false);
/*      */       
/* 1917 */       try (PreparedStatement ps = conn.prepareStatement(command)) {
/* 1918 */         ps.setInt(1, item.stashID);
/*      */         
/* 1920 */         ps.executeUpdate();
/* 1921 */         ps.close();
/*      */         
/* 1923 */         conn.commit();
/*      */       }
/* 1925 */       catch (SQLException ex) {
/* 1926 */         conn.rollback();
/*      */         
/* 1928 */         throw ex;
/*      */       } finally {
/*      */         
/* 1931 */         conn.setAutoCommit(auto);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void updateDependentFields(DBStashItem item) throws SQLException {
/* 1939 */     String update = "UPDATE STASH_ITEM_V8 SET PREFIX_ID = ?, SUFFIX_ID = ?, MODIFIER_ID = ?, TRANSMUTE_ID = ?, ITEM_SEED = ?, RELIC_ID = ?, RELICBONUS_ID = ?, RELIC_SEED = ?, ENCHANTMENT_ID = ?, UNKNOWN_VAR = ?, ENCHANTMENT_SEED = ?, ITEM_VAR1 = ?, STACK_COUNT = ?, HARDCORE = ?, CHARNAME = ?, SOULBOUND = ?, RARITY = ?,  REQ_LEVEL = ?, REQ_DEX = ?, REQ_INT = ?, REQ_STR = ?, ITEM_CLASS = ?, ARMOR_CLASS = ?, ARTIFACT_CLASS = ?, ITEM_LEVEL = ?, SET_ID = ?, NAME = ?, PET_BONUS_SKILL_ID = ?, PLUS_ALLSKILLS = ?, ITEM_SKILL_ID = ?, CONVERT_IN = ?, CONVERT_OUT = ?, CONVERT_IN_2 = ?, CONVERT_OUT_2 = ?, ENEMY_ONLY = ? WHERE STASH_ID = ?";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1949 */     try (Connection conn = GDDBData.getConnection()) {
/* 1950 */       boolean auto = conn.getAutoCommit();
/* 1951 */       conn.setAutoCommit(false);
/*      */       
/* 1953 */       try (PreparedStatement ps = conn.prepareStatement(update)) {
/*      */         
/* 1955 */         if (item.prefixID == null) { ps.setString(1, ""); }
/* 1956 */         else { ps.setString(1, item.prefixID); }
/* 1957 */          if (item.suffixID == null) { ps.setString(2, ""); }
/* 1958 */         else { ps.setString(2, item.suffixID); }
/* 1959 */          if (item.modifierID == null) { ps.setString(3, ""); }
/* 1960 */         else { ps.setString(3, item.modifierID); }
/* 1961 */          if (item.transmuteID == null) { ps.setString(4, ""); }
/* 1962 */         else { ps.setString(4, item.transmuteID); }
/* 1963 */          ps.setInt(5, item.seed);
/* 1964 */         if (item.relicID == null) { ps.setString(6, ""); }
/* 1965 */         else { ps.setString(6, item.relicID); }
/* 1966 */          if (item.relicBonusID == null) { ps.setString(7, ""); }
/* 1967 */         else { ps.setString(7, item.relicBonusID); }
/* 1968 */          ps.setInt(8, item.relicSeed);
/* 1969 */         if (item.enchantmentID == null) { ps.setString(9, ""); }
/* 1970 */         else { ps.setString(9, item.enchantmentID); }
/* 1971 */          ps.setInt(10, item.unknown);
/* 1972 */         ps.setInt(11, item.enchantmentSeed);
/* 1973 */         ps.setInt(12, item.var1);
/* 1974 */         ps.setInt(13, item.stackCount);
/* 1975 */         ps.setBoolean(14, item.hardcore);
/* 1976 */         if (item.charname == null) { ps.setString(15, ""); }
/* 1977 */         else { ps.setString(15, item.charname); }
/* 1978 */          ps.setBoolean(16, item.soulbound);
/* 1979 */         ps.setString(17, item.rarity);
/* 1980 */         ps.setInt(18, item.reqLevel);
/* 1981 */         ps.setInt(19, item.reqDex);
/* 1982 */         ps.setInt(20, item.reqInt);
/* 1983 */         ps.setInt(21, item.reqStr);
/* 1984 */         ps.setString(22, item.itemClass);
/* 1985 */         ps.setString(23, item.armorClass);
/* 1986 */         ps.setString(24, item.artifactClass);
/* 1987 */         ps.setInt(25, item.itemLevel);
/* 1988 */         ps.setString(26, item.setID);
/* 1989 */         ps.setString(27, item.itemName);
/* 1990 */         ps.setString(28, item.petBonusSkillID);
/* 1991 */         ps.setInt(29, item.plusAllSkills);
/* 1992 */         ps.setString(30, item.itemSkillID);
/* 1993 */         ps.setString(31, item.convertIn);
/* 1994 */         ps.setString(32, item.convertOut);
/* 1995 */         ps.setString(33, item.convertIn2);
/* 1996 */         ps.setString(34, item.convertOut2);
/* 1997 */         ps.setBoolean(35, item.enemyOnly);
/*      */         
/* 1999 */         ps.setInt(36, item.stashID);
/*      */         
/* 2001 */         ps.executeUpdate();
/* 2002 */         ps.close();
/*      */         
/* 2004 */         conn.commit();
/*      */       }
/* 2006 */       catch (SQLException ex) {
/* 2007 */         conn.rollback();
/*      */         
/* 2009 */         throw ex;
/*      */       } finally {
/*      */         
/* 2012 */         conn.setAutoCommit(auto);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void convertStash(String tableName, List<DBStashItem> items) {
/* 2022 */     if (items == null)
/*      */       return; 
/* 2024 */     int iFound = 0;
/* 2025 */     int iMissing = 0;
/* 2026 */     int iRemoved = 0;
/* 2027 */     int iError = 0;
/*      */     
/* 2029 */     for (DBStashItem item : items) {
/* 2030 */       if (item.stashID == -1) {
/* 2031 */         iMissing++;
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */ 
/*      */       
/* 2038 */       if (item.getDBItem() == null) {
/* 2039 */         iRemoved++;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 2046 */         convertStash(tableName, item);
/*      */         
/* 2048 */         iFound++;
/*      */       }
/* 2050 */       catch (SQLException ex) {
/* 2051 */         iError++;
/*      */       } 
/*      */     } 
/*      */     
/* 2055 */     if (iFound > 0) {
/* 2056 */       Object[] args = { Integer.valueOf(iFound) };
/* 2057 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "INFO_STASH_CONV_FOUND", args);
/*      */       
/* 2059 */       GDMsgLogger.addInfo(msg);
/*      */     } 
/*      */     
/* 2062 */     if (iMissing > 0) {
/* 2063 */       Object[] args = { Integer.valueOf(iMissing) };
/* 2064 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "INFO_STASH_CONV_MISS", args);
/*      */       
/* 2066 */       GDMsgLogger.addWarning(msg);
/*      */     } 
/*      */     
/* 2069 */     if (iRemoved > 0) {
/* 2070 */       Object[] args = { Integer.valueOf(iRemoved) };
/* 2071 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "INFO_STASH_CONV_DEL", args);
/*      */       
/* 2073 */       GDMsgLogger.addWarning(msg);
/*      */     } 
/*      */     
/* 2076 */     if (iError > 0) {
/* 2077 */       Object[] args = { Integer.valueOf(iError) };
/* 2078 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "INFO_STASH_CONV_ERROR", args);
/*      */       
/* 2080 */       GDMsgLogger.addError(msg);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void convertStash(String tableName, DBStashItem item) throws SQLException {
/* 2085 */     try (Connection conn = GDDBData.getConnection()) {
/* 2086 */       boolean auto = conn.getAutoCommit();
/* 2087 */       conn.setAutoCommit(false);
/*      */       
/*      */       try {
/* 2090 */         if (item.getDBItem() != null) {
/* 2091 */           insert(conn, item);
/*      */         }
/* 2093 */         deleteOld(conn, tableName, item);
/*      */         
/* 2095 */         conn.commit();
/*      */       }
/* 2097 */       catch (SQLException ex) {
/* 2098 */         conn.rollback();
/*      */         
/* 2100 */         throw ex;
/*      */       } finally {
/*      */         
/* 2103 */         conn.setAutoCommit(auto);
/*      */       }
/*      */     
/* 2106 */     } catch (SQLException ex) {
/* 2107 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void moveStash(Connection connOld, Connection connNew, String tableName, List<DBStashItem> items) {
/* 2112 */     if (items == null)
/*      */       return; 
/* 2114 */     int iFound = 0;
/* 2115 */     int iMissing = 0;
/* 2116 */     int iRemoved = 0;
/* 2117 */     int iError = 0;
/*      */     
/* 2119 */     for (DBStashItem item : items) {
/* 2120 */       if (item.stashID == -1) {
/* 2121 */         iMissing++;
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */ 
/*      */       
/* 2128 */       if (item.getDBItem() == null) {
/* 2129 */         iRemoved++;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 2136 */         moveStash(connOld, connNew, tableName, item);
/*      */         
/* 2138 */         iFound++;
/*      */       }
/* 2140 */       catch (SQLException ex) {
/* 2141 */         iError++;
/*      */       } 
/*      */     } 
/*      */     
/* 2145 */     if (iFound > 0) {
/* 2146 */       Object[] args = { Integer.valueOf(iFound) };
/* 2147 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "INFO_STASH_CONV_FOUND", args);
/*      */       
/* 2149 */       GDMsgLogger.addInfo(msg);
/*      */     } 
/*      */     
/* 2152 */     if (iMissing > 0) {
/* 2153 */       Object[] args = { Integer.valueOf(iMissing) };
/* 2154 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "INFO_STASH_CONV_MISS", args);
/*      */       
/* 2156 */       GDMsgLogger.addWarning(msg);
/*      */     } 
/*      */     
/* 2159 */     if (iRemoved > 0) {
/* 2160 */       Object[] args = { Integer.valueOf(iRemoved) };
/* 2161 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "INFO_STASH_CONV_DEL", args);
/*      */       
/* 2163 */       GDMsgLogger.addWarning(msg);
/*      */     } 
/*      */     
/* 2166 */     if (iError > 0) {
/* 2167 */       Object[] args = { Integer.valueOf(iError) };
/* 2168 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "INFO_STASH_CONV_ERROR", args);
/*      */       
/* 2170 */       GDMsgLogger.addError(msg);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void moveStash(Connection connOld, Connection connNew, String tableName, DBStashItem item) throws SQLException {
/*      */     try {
/* 2176 */       boolean autoOld = connOld.getAutoCommit();
/* 2177 */       connOld.setAutoCommit(false);
/*      */       
/* 2179 */       boolean autoNew = connNew.getAutoCommit();
/* 2180 */       connNew.setAutoCommit(false);
/*      */       
/*      */       try {
/* 2183 */         if (item.getDBItem() != null) {
/* 2184 */           insert(connNew, item);
/*      */         }
/* 2186 */         deleteOld(connOld, tableName, item);
/*      */         
/* 2188 */         connNew.commit();
/* 2189 */         connOld.commit();
/*      */       }
/* 2191 */       catch (SQLException ex) {
/* 2192 */         connNew.rollback();
/* 2193 */         connOld.rollback();
/*      */         
/* 2195 */         throw ex;
/*      */       } finally {
/*      */         
/* 2198 */         connOld.setAutoCommit(autoOld);
/* 2199 */         connNew.setAutoCommit(autoNew);
/*      */       }
/*      */     
/* 2202 */     } catch (SQLException ex) {
/* 2203 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insert(Connection conn, DBStashItem item) throws SQLException {
/* 2208 */     if (item.isStackable()) {
/* 2209 */       insertStack(conn, item);
/*      */     } else {
/* 2211 */       insertNonStack(conn, item);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertStack(Connection conn, DBStashItem item) throws SQLException {
/* 2216 */     DBStashItem si = getStashItemForStack(item);
/*      */     
/* 2218 */     if (si == null) {
/*      */       
/* 2220 */       insertNonStack(conn, item);
/*      */     } else {
/* 2222 */       updateStackCount(conn, si.stashID, si.stackCount + item.stackCount);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void deleteOld(Connection conn, String tableName, DBStashItem item) throws SQLException {
/* 2227 */     if (item == null)
/* 2228 */       return;  if (item.stashID == -1)
/*      */       return; 
/* 2230 */     String command = "DELETE FROM " + tableName + " WHERE STASH_ID = ?";
/*      */ 
/*      */     
/* 2233 */     try (PreparedStatement ps = conn.prepareStatement(command)) {
/* 2234 */       ps.setInt(1, item.stashID);
/*      */       
/* 2236 */       ps.executeUpdate();
/* 2237 */       ps.close();
/*      */     }
/* 2239 */     catch (SQLException ex) {
/* 2240 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void dropOldStash(Connection conn, String tableName) {
/* 2245 */     String dropTable = "DROP TABLE " + tableName;
/*      */     
/*      */     try {
/* 2248 */       boolean auto = conn.getAutoCommit();
/* 2249 */       conn.setAutoCommit(false);
/*      */       
/* 2251 */       try (Statement st = conn.createStatement()) {
/* 2252 */         if (GDDBUtil.tableExists(conn, tableName)) {
/* 2253 */           List<DBStashItem> items = getAllOld(conn, tableName);
/*      */           
/* 2255 */           if (items.isEmpty()) {
/* 2256 */             st.execute(dropTable);
/*      */           } else {
/* 2258 */             Object[] args = { tableName };
/* 2259 */             String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "TABLE_CONTAINS_ENTRIES", args);
/*      */             
/* 2261 */             GDMsgLogger.addError(msg);
/*      */           } 
/*      */         } 
/* 2264 */         st.close();
/*      */         
/* 2266 */         conn.commit();
/*      */       }
/* 2268 */       catch (SQLException ex) {
/* 2269 */         conn.rollback();
/*      */         
/* 2271 */         Object[] args = { tableName };
/* 2272 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DROP_TABLE", args);
/*      */         
/* 2274 */         GDMsgLogger.addError(msg);
/*      */         
/* 2276 */         throw ex;
/*      */       } finally {
/*      */         
/* 2279 */         conn.setAutoCommit(auto);
/*      */       }
/*      */     
/* 2282 */     } catch (SQLException sQLException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static GDItem getStack(GDItem item) {
/* 2290 */     DBStashItem si = getStack(item.getStashItem());
/*      */     
/* 2292 */     if (si == null) return null;
/*      */     
/* 2294 */     return new GDItem(si);
/*      */   }
/*      */   
/*      */   private static DBStashItem getStack(DBStashItem item) {
/* 2298 */     if (item == null) return null;
/*      */     
/* 2300 */     String command = "SELECT * FROM STASH_ITEM_V8 WHERE ITEM_ID = ? AND ITEM_VAR1 = ? AND HARDCORE = ? AND CHARNAME = ?";
/*      */ 
/*      */     
/* 2303 */     try(Connection conn = GDDBData.getConnection(); 
/* 2304 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 2305 */       ps.setString(1, item.itemID);
/* 2306 */       ps.setInt(2, item.var1);
/* 2307 */       ps.setBoolean(3, item.hardcore);
/* 2308 */       if (item.charname == null) {
/* 2309 */         ps.setString(4, "");
/*      */       } else {
/* 2311 */         ps.setString(4, item.charname);
/*      */       } 
/*      */       
/* 2314 */       try (ResultSet rs = ps.executeQuery()) {
/* 2315 */         List<DBStashItem> list = wrap(rs);
/*      */         
/* 2317 */         if (list.isEmpty()) { item = null; }
/* 2318 */         else { item = list.get(0); }
/*      */         
/* 2320 */         conn.commit();
/*      */       }
/* 2322 */       catch (SQLException ex) {
/* 2323 */         item = null;
/*      */         
/* 2325 */         throw ex;
/*      */       }
/*      */     
/* 2328 */     } catch (SQLException ex) {
/* 2329 */       Object[] args = { item.itemID, "STASH_ITEM_V8" };
/* 2330 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/* 2331 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_STASH_ITEM_READ_ALL"));
/* 2332 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2335 */     return item;
/*      */   }
/*      */   
/*      */   public static DBStashItem get(int stashID, CriteriaCombination.Soulbound soulbound, CriteriaCombination.SC_HC schc, boolean isHardcore, String charName) {
/* 2339 */     DBStashItem item = null;
/*      */     
/* 2341 */     String command = "SELECT * FROM STASH_ITEM_V8 WHERE STASH_ID = ?";
/*      */ 
/*      */     
/* 2344 */     if (schc == CriteriaCombination.SC_HC.SOFTCORE) {
/* 2345 */       command = command + " AND S.HARDCORE = false";
/*      */     }
/* 2347 */     if (schc == CriteriaCombination.SC_HC.HARDCORE) {
/* 2348 */       command = command + " AND S.HARDCORE = true";
/*      */     }
/* 2350 */     if (schc == CriteriaCombination.SC_HC.SETTING && 
/* 2351 */       !GDStashFrame.iniConfig.sectRestrict.transferSCHC) {
/* 2352 */       if (isHardcore) {
/* 2353 */         command = command + " AND HARDCORE = true";
/*      */       } else {
/* 2355 */         command = command + " AND HARDCORE = false";
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2361 */     if (soulbound == CriteriaCombination.Soulbound.EXCLUDED) {
/* 2362 */       command = command + " AND CHARNAME = ''";
/*      */     }
/* 2364 */     if (soulbound == CriteriaCombination.Soulbound.SETTING && 
/* 2365 */       !GDStashFrame.iniConfig.sectRestrict.transferSoulbound) {
/* 2366 */       if (charName == null) {
/* 2367 */         command = command + " AND CHARNAME = ''";
/*      */       }
/* 2369 */       else if (charName.isEmpty()) {
/* 2370 */         command = command + " AND CHARNAME = ''";
/*      */       } else {
/* 2372 */         command = command + " AND ( CHARNAME = '" + charName + "' OR CHARNAME = '' )";
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2378 */     try(Connection conn = GDDBData.getConnection(); 
/* 2379 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 2380 */       ps.setInt(1, stashID);
/*      */       
/* 2382 */       try (ResultSet rs = ps.executeQuery()) {
/* 2383 */         List<DBStashItem> list = wrap(rs);
/*      */         
/* 2385 */         if (list.isEmpty()) { item = null; }
/* 2386 */         else { item = list.get(0); }
/*      */         
/* 2388 */         conn.commit();
/*      */       }
/* 2390 */       catch (SQLException ex) {
/* 2391 */         item = null;
/*      */         
/* 2393 */         throw ex;
/*      */       }
/*      */     
/* 2396 */     } catch (SQLException ex) {
/* 2397 */       Object[] args = { Integer.toString(stashID), "STASH_ITEM_V8" };
/* 2398 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/* 2399 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_STASH_ITEM_READ_ALL"));
/* 2400 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2403 */     return item;
/*      */   }
/*      */   
/*      */   public static List<DBStashItem> getAllOld(Connection conn, String tableName) {
/* 2407 */     List<DBStashItem> list = new LinkedList<>();
/*      */     
/* 2409 */     String command = "SELECT * FROM " + tableName;
/*      */     
/* 2411 */     try (PreparedStatement ps = conn.prepareStatement(command)) {
/* 2412 */       try (ResultSet rs = ps.executeQuery()) {
/* 2413 */         list = wrap(rs);
/*      */         
/* 2415 */         conn.commit();
/*      */       }
/* 2417 */       catch (SQLException ex) {
/* 2418 */         throw ex;
/*      */       }
/*      */     
/* 2421 */     } catch (SQLException ex) {
/* 2422 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID"));
/* 2423 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2426 */     return list;
/*      */   }
/*      */   
/*      */   public static List<DBStashItem> getAll() {
/* 2430 */     List<DBStashItem> list = new LinkedList<>();
/*      */     
/* 2432 */     String command = "SELECT * FROM STASH_ITEM_V8";
/*      */     
/* 2434 */     try(Connection conn = GDDBData.getConnection(); 
/* 2435 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 2436 */       try (ResultSet rs = ps.executeQuery()) {
/* 2437 */         list = wrap(rs);
/*      */         
/* 2439 */         conn.commit();
/*      */       }
/* 2441 */       catch (SQLException ex) {
/* 2442 */         throw ex;
/*      */       }
/*      */     
/* 2445 */     } catch (SQLException ex) {
/* 2446 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID"));
/* 2447 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2450 */     return list;
/*      */   }
/*      */   
/*      */   public static List<DBItem> getAllDistinct(boolean hardcore) {
/* 2454 */     List<DBItem> list = new LinkedList<>();
/*      */     
/* 2456 */     String command = "SELECT DISTINCT ITEM_ID FROM STASH_ITEM_V8";
/*      */     
/* 2458 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSCHC) {
/* 2459 */       if (hardcore) {
/* 2460 */         command = command + " WHERE HARDCORE = 'true'";
/*      */       } else {
/* 2462 */         command = command + " WHERE HARDCORE = 'false'";
/*      */       } 
/*      */     }
/*      */     
/* 2466 */     try(Connection conn = GDDBData.getConnection(); 
/* 2467 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 2468 */       try (ResultSet rs = ps.executeQuery()) {
/* 2469 */         while (rs.next()) {
/* 2470 */           String itemID = rs.getString(1);
/*      */           
/* 2472 */           DBItem item = DBItem.get(itemID);
/*      */           
/* 2474 */           list.add(item);
/*      */         } 
/*      */         
/* 2477 */         conn.commit();
/*      */       }
/* 2479 */       catch (SQLException ex) {
/* 2480 */         throw ex;
/*      */       }
/*      */     
/* 2483 */     } catch (SQLException ex) {
/* 2484 */       Object[] args = { "All", "STASH_ITEM_V8" };
/* 2485 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2487 */       GDMsgLogger.addError(msg);
/* 2488 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2491 */     return list;
/*      */   }
/*      */   
/*      */   public static List<GDItem> getGDItemsByItemID(String itemID, boolean isHardcore) {
/* 2495 */     List<GDItem> list = new LinkedList<>();
/*      */     
/* 2497 */     String command = "SELECT * FROM STASH_ITEM_V8 WHERE ITEM_ID = ?";
/* 2498 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSCHC) {
/* 2499 */       command = command + " AND HARDCORE = ?";
/*      */     }
/*      */     
/* 2502 */     try(Connection conn = GDDBData.getConnection(); 
/* 2503 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 2504 */       ps.setString(1, itemID);
/* 2505 */       if (!GDStashFrame.iniConfig.sectRestrict.transferSCHC) {
/* 2506 */         ps.setBoolean(2, isHardcore);
/*      */       }
/*      */       
/* 2509 */       try (ResultSet rs = ps.executeQuery()) {
/* 2510 */         list = wrapGDItem(rs);
/*      */         
/* 2512 */         conn.commit();
/*      */       }
/* 2514 */       catch (SQLException ex) {
/* 2515 */         throw ex;
/*      */       }
/*      */     
/* 2518 */     } catch (SQLException ex) {
/* 2519 */       Object[] args = { itemID, "STASH_ITEM_V8" };
/* 2520 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2522 */       GDMsgLogger.addError(msg);
/* 2523 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2526 */     return list;
/*      */   }
/*      */   
/*      */   public static List<GDItem> getGDItemsByItemIDs(List<String> itemIDs, boolean isHarcore) {
/* 2530 */     List<GDItem> list = new LinkedList<>();
/*      */     
/* 2532 */     for (String itemID : itemIDs) {
/* 2533 */       List<GDItem> l = getGDItemsByItemID(itemID, isHarcore);
/*      */       
/* 2535 */       if (!l.isEmpty()) list.addAll(l);
/*      */     
/*      */     } 
/* 2538 */     return list;
/*      */   }
/*      */   
/*      */   public static List<GDItem> getGDItemsByItemID(String itemID) {
/* 2542 */     List<GDItem> list = new LinkedList<>();
/*      */     
/* 2544 */     String command = "SELECT * FROM STASH_ITEM_V8 WHERE ITEM_ID = ?";
/*      */     
/* 2546 */     try(Connection conn = GDDBData.getConnection(); 
/* 2547 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 2548 */       ps.setString(1, itemID);
/*      */       
/* 2550 */       try (ResultSet rs = ps.executeQuery()) {
/* 2551 */         list = wrapGDItem(rs);
/*      */         
/* 2553 */         conn.commit();
/*      */       }
/* 2555 */       catch (SQLException ex) {
/* 2556 */         throw ex;
/*      */       }
/*      */     
/* 2559 */     } catch (SQLException ex) {
/* 2560 */       Object[] args = { itemID, "STASH_ITEM_V8" };
/* 2561 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2563 */       GDMsgLogger.addError(msg);
/* 2564 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2567 */     return list;
/*      */   }
/*      */   
/*      */   public static List<GDItem> getGDItemsByItemIDs(List<String> itemIDs) {
/* 2571 */     List<GDItem> list = new LinkedList<>();
/*      */     
/* 2573 */     for (String itemID : itemIDs) {
/* 2574 */       List<GDItem> l = getGDItemsByItemID(itemID);
/* 2575 */       if (!l.isEmpty()) list.addAll(l);
/*      */     
/*      */     } 
/* 2578 */     return list;
/*      */   }
/*      */   
/*      */   private static List<GDItem> getGDItemsByStashIDs(List<Integer> stashIDs, CriteriaCombination.Soulbound soulbound, CriteriaCombination.SC_HC schc, boolean isHardcore, String charName) {
/* 2582 */     List<GDItem> list = new LinkedList<>();
/*      */     
/* 2584 */     if (stashIDs == null) return list;
/*      */     
/* 2586 */     for (Iterator<Integer> iterator = stashIDs.iterator(); iterator.hasNext(); ) { int stashID = ((Integer)iterator.next()).intValue();
/* 2587 */       DBStashItem si = get(stashID, soulbound, schc, isHardcore, charName);
/* 2588 */       if (si != null) {
/* 2589 */         GDItem item = new GDItem(si);
/*      */         
/* 2591 */         list.add(item);
/*      */       }  }
/*      */ 
/*      */     
/* 2595 */     return list;
/*      */   }
/*      */   
/*      */   private static List<DBStashItem> wrap(ResultSet rs) throws SQLException {
/* 2599 */     LinkedList<DBStashItem> list = new LinkedList<>();
/*      */     
/* 2601 */     while (rs.next()) {
/*      */       try {
/* 2603 */         DBStashItem item = wrapSingle(rs);
/*      */         
/* 2605 */         list.add(item);
/*      */       }
/* 2607 */       catch (SQLException sQLException) {}
/*      */     } 
/*      */     
/* 2610 */     return list;
/*      */   }
/*      */   
/*      */   private static List<GDItem> wrapGDItem(ResultSet rs) throws SQLException {
/* 2614 */     GDItem item = null;
/* 2615 */     LinkedList<GDItem> list = new LinkedList<>();
/*      */     
/* 2617 */     while (rs.next()) {
/*      */       try {
/* 2619 */         DBStashItem si = wrapSingle(rs);
/*      */         
/* 2621 */         item = new GDItem(si);
/*      */         
/* 2623 */         list.add(item);
/*      */       }
/* 2625 */       catch (SQLException sQLException) {}
/*      */     } 
/*      */     
/* 2628 */     return list;
/*      */   }
/*      */   
/*      */   private static DBStashItem wrapSingle(ResultSet rs) throws SQLException {
/* 2632 */     DBStashItem item = new DBStashItem();
/*      */     
/*      */     try {
/* 2635 */       item.stashID = rs.getInt(1);
/* 2636 */       item.itemID = rs.getString(2);
/* 2637 */       item.prefixID = rs.getString(3);
/* 2638 */       if (item.prefixID != null && 
/* 2639 */         item.prefixID.equals("")) item.prefixID = null;
/*      */       
/* 2641 */       item.suffixID = rs.getString(4);
/* 2642 */       if (item.suffixID != null && 
/* 2643 */         item.suffixID.equals("")) item.suffixID = null;
/*      */       
/* 2645 */       item.modifierID = rs.getString(5);
/* 2646 */       if (item.modifierID != null && 
/* 2647 */         item.modifierID.equals("")) item.modifierID = null;
/*      */       
/* 2649 */       item.transmuteID = rs.getString(6);
/* 2650 */       if (item.transmuteID != null && 
/* 2651 */         item.transmuteID.equals("")) item.transmuteID = null;
/*      */       
/* 2653 */       item.seed = rs.getInt(7);
/* 2654 */       item.relicID = rs.getString(8);
/* 2655 */       if (item.relicID != null && 
/* 2656 */         item.relicID.equals("")) item.relicID = null;
/*      */       
/* 2658 */       item.relicBonusID = rs.getString(9);
/* 2659 */       if (item.relicBonusID != null && 
/* 2660 */         item.relicBonusID.equals("")) item.relicBonusID = null;
/*      */       
/* 2662 */       item.relicSeed = rs.getInt(10);
/* 2663 */       item.enchantmentID = rs.getString(11);
/* 2664 */       if (item.enchantmentID != null && 
/* 2665 */         item.enchantmentID.equals("")) item.enchantmentID = null;
/*      */       
/* 2667 */       item.unknown = rs.getInt(12);
/* 2668 */       item.enchantmentSeed = rs.getInt(13);
/* 2669 */       item.var1 = rs.getInt(14);
/* 2670 */       item.stackCount = rs.getInt(15);
/* 2671 */       item.hardcore = rs.getBoolean(16);
/* 2672 */       item.charname = rs.getString(17);
/* 2673 */       if (item.charname != null && 
/* 2674 */         item.charname.equals("")) item.charname = null;
/*      */ 
/*      */ 
/*      */       
/* 2678 */       item.fillDependentStats(null);
/*      */     }
/* 2680 */     catch (SQLException ex) {
/* 2681 */       if (item.itemID != null) {
/* 2682 */         Object[] args = { item.itemID, Integer.valueOf(item.stashID) };
/* 2683 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_STASH_READ_BY_STASH", args);
/*      */         
/* 2685 */         throw new ClassCastException(msg);
/*      */       } 
/* 2687 */       if (item.stashID != -1) {
/* 2688 */         Object[] args = { Integer.valueOf(item.stashID), "STASH_ITEM_V8" };
/* 2689 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */         
/* 2691 */         GDMsgLogger.addError(msg);
/*      */       } else {
/* 2693 */         GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_STASH_READ"));
/*      */       } 
/*      */ 
/*      */       
/* 2697 */       GDMsgLogger.addError(ex);
/*      */       
/* 2699 */       throw ex;
/*      */     } 
/*      */     
/* 2702 */     return item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static List<Integer> getStashIDsByItemIDs(List<String> itemIDs, boolean isHardcore, String charName) {
/* 2710 */     List<Integer> listAll = new LinkedList<>();
/*      */     
/* 2712 */     for (String itemID : itemIDs) {
/* 2713 */       List<Integer> list = getStashIDsByItemID(itemID, isHardcore, charName);
/*      */       
/* 2715 */       if (list != null) listAll.addAll(list);
/*      */     
/*      */     } 
/* 2718 */     return listAll;
/*      */   }
/*      */   
/*      */   private static List<Integer> getStashIDsByItemID(String itemID, boolean isHardcore, String charName) {
/* 2722 */     List<Integer> list = new LinkedList<>();
/*      */     
/* 2724 */     String command = "SELECT STASH_ID FROM STASH_ITEM_V8 WHERE ITEM_ID = ?";
/* 2725 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSCHC) {
/* 2726 */       if (isHardcore) {
/* 2727 */         command = command + " AND HARDCORE = true";
/*      */       } else {
/* 2729 */         command = command + " AND HARDCORE = false";
/*      */       } 
/*      */     }
/* 2732 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSoulbound) {
/* 2733 */       if (charName == null) {
/* 2734 */         command = command + " AND CHARNAME = ''";
/*      */       }
/* 2736 */       else if (charName.isEmpty()) {
/* 2737 */         command = command + " AND CHARNAME = ''";
/*      */       } else {
/* 2739 */         command = command + " AND ( CHARNAME = '" + charName + "' OR CHARNAME = '' )";
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 2744 */     try(Connection conn = GDDBData.getConnection(); 
/* 2745 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 2746 */       ps.setString(1, itemID);
/*      */       
/* 2748 */       try (ResultSet rs = ps.executeQuery()) {
/* 2749 */         list = AbstractItemCombination.wrapInteger(rs, 1);
/*      */         
/* 2751 */         conn.commit();
/*      */       }
/* 2753 */       catch (SQLException ex) {
/* 2754 */         throw ex;
/*      */       }
/*      */     
/* 2757 */     } catch (SQLException ex) {
/* 2758 */       Object[] args = { itemID, "STASH_ITEM_V8" };
/* 2759 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2761 */       GDMsgLogger.addError(msg);
/* 2762 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2765 */     return list;
/*      */   }
/*      */   
/*      */   private static void mergeStashIDs(List<Integer> listAll, List<Integer> list) {
/* 2769 */     if (list == null)
/*      */       return; 
/* 2771 */     for (Integer sInt : list) {
/* 2772 */       int stashID = sInt.intValue();
/*      */       
/* 2774 */       boolean found = false;
/* 2775 */       for (Iterator<Integer> iterator = listAll.iterator(); iterator.hasNext(); ) { int sid = ((Integer)iterator.next()).intValue();
/* 2776 */         if (sid == stashID) {
/* 2777 */           found = true;
/*      */           
/*      */           break;
/*      */         }  }
/*      */ 
/*      */       
/* 2783 */       if (!found) listAll.add(sInt); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static List<Integer> getByCriteria(SelectionCriteria criteria, CriteriaCombination.Soulbound soulbound, CriteriaCombination.SC_HC schc, boolean isHardcore, String charName) {
/* 2788 */     List<Integer> itemsAll = new LinkedList<>();
/* 2789 */     List<Integer> items = null;
/* 2790 */     List<Integer> itemSets = null;
/* 2791 */     List<Integer> prefixes = null;
/* 2792 */     List<Integer> suffixes = null;
/*      */     
/* 2794 */     items = StashIDItemCombination.getStashIDs(criteria, soulbound, schc, isHardcore, charName);
/* 2795 */     itemSets = StashIDItemSetCombination.getStashIDs(criteria, soulbound, schc, isHardcore, charName);
/*      */     
/* 2797 */     if (criteria.prefixID == null)
/*      */     {
/* 2799 */       prefixes = StashIDPrefixCombination.getStashIDs(criteria, soulbound, schc, isHardcore, charName);
/*      */     }
/* 2801 */     if (criteria.suffixID == null)
/*      */     {
/* 2803 */       suffixes = StashIDSuffixCombination.getStashIDs(criteria, soulbound, schc, isHardcore, charName);
/*      */     }
/*      */     
/* 2806 */     mergeStashIDs(itemsAll, items);
/* 2807 */     mergeStashIDs(itemsAll, itemSets);
/* 2808 */     mergeStashIDs(itemsAll, prefixes);
/* 2809 */     mergeStashIDs(itemsAll, suffixes);
/*      */     
/* 2811 */     return itemsAll;
/*      */   }
/*      */   
/*      */   public static List<GDItem> getGDItemByCriteria(SelectionCriteria criteria, CriteriaCombination.Soulbound soulbound, CriteriaCombination.SC_HC schc, boolean isHardcore, String charName) {
/* 2815 */     List<Integer> stashIDs = null;
/*      */     
/* 2817 */     if (criteria.itemIDs != null && !criteria.itemIDs.isEmpty()) {
/* 2818 */       stashIDs = getStashIDsByItemIDs(criteria.itemIDs, isHardcore, charName);
/*      */     } else {
/* 2820 */       stashIDs = getByCriteria(criteria, soulbound, schc, isHardcore, charName);
/*      */     } 
/*      */     
/* 2823 */     return getGDItemsByStashIDs(stashIDs, soulbound, schc, isHardcore, charName);
/*      */   }
/*      */   
/*      */   public static DuplicateInfo determineSimilarItems(GDItem item, boolean isHardcore, String charName) {
/* 2827 */     if (item == null) return null;
/*      */     
/* 2829 */     String cname = null;
/*      */     
/* 2831 */     if (item.getDBItem() != null && 
/* 2832 */       item.getDBItem().isSoulbound()) cname = charName;
/*      */ 
/*      */     
/* 2835 */     DuplicateInfo info = new DuplicateInfo();
/*      */     
/* 2837 */     List<Integer> list = null;
/* 2838 */     if (item.getRarity().equals("Epic") || item
/* 2839 */       .getRarity().equals("Legendary")) {
/* 2840 */       info.numItemCombo.background = ItemClass.getRarityBackgroundColor(item.getDBItem().getRarity());
/*      */       
/* 2842 */       if (item.getPrefixID() == null && item.getSuffixID() == null) {
/* 2843 */         list = getStashIDsByItemID(item.getItemID(), isHardcore, cname);
/* 2844 */         info.numItemCombo.text = Integer.toString(list.size());
/*      */       } else {
/*      */         
/* 2847 */         list = getStashIDsByItemAndPrefixSuffix(item.getItemID(), item.getPrefixID(), item.getSuffixID(), isHardcore, cname);
/* 2848 */         info.numItemCombo.text = Integer.toString(list.size());
/*      */         
/* 2850 */         if (item.getPrefix() != null) {
/* 2851 */           list = getStashIDsByItemAndPrefix(item.getItemID(), item.getPrefixID(), isHardcore, cname);
/* 2852 */           info.numItemPrefix.text = Integer.toString(list.size());
/*      */           
/* 2854 */           info.numItemPrefix.background = ItemClass.getRarityBackgroundColor(item.getPrefix().getRarity());
/* 2855 */           info.levelItemPrefix.text = Integer.toString(item.getPrefix().getRequiredlevel());
/* 2856 */           info.levelItemPrefix.foreground = info.numItemPrefix.foreground;
/* 2857 */           info.levelItemPrefix.background = info.numItemPrefix.background;
/*      */         } 
/*      */         
/* 2860 */         if (item.getSuffix() != null) {
/* 2861 */           list = getStashIDsByItemAndSuffix(item.getItemID(), item.getSuffixID(), isHardcore, cname);
/* 2862 */           info.numItemSuffix.text = Integer.toString(list.size());
/*      */           
/* 2864 */           info.numItemSuffix.background = ItemClass.getRarityBackgroundColor(item.getSuffix().getRarity());
/* 2865 */           info.levelItemSuffix.text = Integer.toString(item.getSuffix().getRequiredlevel());
/* 2866 */           info.levelItemSuffix.foreground = info.numItemSuffix.foreground;
/* 2867 */           info.levelItemSuffix.background = info.numItemSuffix.background;
/*      */         } 
/*      */         
/* 2870 */         list = getStashIDsByItemID(item.getItemID(), isHardcore, cname);
/* 2871 */         info.numClassCombo.text = Integer.toString(list.size());
/* 2872 */         info.numClassCombo.background = info.numItemCombo.background;
/*      */       } 
/*      */     } 
/*      */     
/* 2876 */     if ((item.getRarity().equals("Magical") || item
/* 2877 */       .getRarity().equals("Rare")) && (
/* 2878 */       ItemClass.isArmor(item.getItemClass()) || 
/* 2879 */       ItemClass.isJewelry(item.getItemClass()) || 
/* 2880 */       ItemClass.isWeapon(item.getItemClass()))) {
/* 2881 */       list = getStashIDsByItemAndPrefixSuffix(item.getItemID(), item.getPrefixID(), item.getSuffixID(), isHardcore, cname);
/* 2882 */       info.numItemCombo.text = Integer.toString(list.size());
/* 2883 */       info.numItemCombo.background = ItemClass.getRarityBackgroundColor(item.getDBItem().getRarity());
/*      */       
/* 2885 */       if (item.getPrefix() != null) {
/* 2886 */         list = getStashIDsByItemAndPrefix(item.getItemID(), item.getPrefixID(), isHardcore, cname);
/* 2887 */         info.numItemPrefix.text = Integer.toString(list.size());
/*      */         
/* 2889 */         info.numItemPrefix.background = ItemClass.getRarityBackgroundColor(item.getPrefix().getRarity());
/* 2890 */         info.levelItemPrefix.text = Integer.toString(item.getPrefix().getRequiredlevel());
/* 2891 */         info.levelItemPrefix.foreground = info.numItemPrefix.foreground;
/* 2892 */         info.levelItemPrefix.background = info.numItemPrefix.background;
/*      */       } 
/*      */       
/* 2895 */       if (item.getSuffix() != null) {
/* 2896 */         list = getStashIDsByItemAndSuffix(item.getItemID(), item.getSuffixID(), isHardcore, cname);
/* 2897 */         info.numItemSuffix.text = Integer.toString(list.size());
/*      */         
/* 2899 */         info.numItemSuffix.background = ItemClass.getRarityBackgroundColor(item.getSuffix().getRarity());
/* 2900 */         info.levelItemSuffix.text = Integer.toString(item.getSuffix().getRequiredlevel());
/* 2901 */         info.levelItemSuffix.foreground = info.numItemSuffix.foreground;
/* 2902 */         info.levelItemSuffix.background = info.numItemSuffix.background;
/*      */       } 
/*      */       
/* 2905 */       list = getStashIDsByClassAndPrefixSuffix(item.getItemClass(), item.getPrefixID(), item.getSuffixID(), isHardcore, cname);
/* 2906 */       info.numClassCombo.text = Integer.toString(list.size());
/*      */     } 
/*      */ 
/*      */     
/* 2910 */     return info;
/*      */   }
/*      */   
/*      */   private static List<Integer> getStashIDsByItemAndPrefixSuffix(String itemID, String prefixID, String suffixID, boolean isHardcore, String charName) {
/* 2914 */     List<Integer> list = new LinkedList<>();
/*      */     
/* 2916 */     if (prefixID == null && suffixID == null) return list;
/*      */     
/* 2918 */     String command = "SELECT STASH_ID FROM STASH_ITEM_V8 WHERE ITEM_ID = ? AND PREFIX_ID = ? AND SUFFIX_ID = ?";
/* 2919 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSCHC) {
/* 2920 */       if (isHardcore) {
/* 2921 */         command = command + " AND HARDCORE = true";
/*      */       } else {
/* 2923 */         command = command + " AND HARDCORE = false";
/*      */       } 
/*      */     }
/* 2926 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSoulbound && 
/* 2927 */       charName != null) {
/* 2928 */       command = command + " AND CHARNAME = '" + charName + "'";
/*      */     }
/*      */ 
/*      */     
/* 2932 */     String pre = "";
/* 2933 */     if (prefixID != null) pre = prefixID; 
/* 2934 */     String suf = "";
/* 2935 */     if (suffixID != null) suf = suffixID;
/*      */     
/* 2937 */     try(Connection conn = GDDBData.getConnection(); 
/* 2938 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 2939 */       ps.setString(1, itemID);
/* 2940 */       ps.setString(2, pre);
/* 2941 */       ps.setString(3, suf);
/*      */       
/* 2943 */       try (ResultSet rs = ps.executeQuery()) {
/* 2944 */         list = AbstractItemCombination.wrapInteger(rs, 1);
/*      */         
/* 2946 */         conn.commit();
/*      */       }
/* 2948 */       catch (SQLException ex) {
/* 2949 */         throw ex;
/*      */       }
/*      */     
/* 2952 */     } catch (SQLException ex) {
/* 2953 */       Object[] args = { itemID, "STASH_ITEM_V8" };
/* 2954 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 2956 */       GDMsgLogger.addError(msg);
/* 2957 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 2960 */     return list;
/*      */   }
/*      */   
/*      */   private static List<Integer> getStashIDsByItemAndPrefix(String itemID, String prefixID, boolean isHardcore, String charName) {
/* 2964 */     List<Integer> list = new LinkedList<>();
/*      */     
/* 2966 */     if (prefixID == null) return list;
/*      */     
/* 2968 */     String command = "SELECT STASH_ID FROM STASH_ITEM_V8 WHERE ITEM_ID = ? AND PREFIX_ID = ?";
/* 2969 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSCHC) {
/* 2970 */       if (isHardcore) {
/* 2971 */         command = command + " AND HARDCORE = true";
/*      */       } else {
/* 2973 */         command = command + " AND HARDCORE = false";
/*      */       } 
/*      */     }
/* 2976 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSoulbound && 
/* 2977 */       charName != null) {
/* 2978 */       command = command + " AND CHARNAME = '" + charName + "'";
/*      */     }
/*      */ 
/*      */     
/* 2982 */     try(Connection conn = GDDBData.getConnection(); 
/* 2983 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 2984 */       ps.setString(1, itemID);
/* 2985 */       ps.setString(2, prefixID);
/*      */       
/* 2987 */       try (ResultSet rs = ps.executeQuery()) {
/* 2988 */         list = AbstractItemCombination.wrapInteger(rs, 1);
/*      */         
/* 2990 */         conn.commit();
/*      */       }
/* 2992 */       catch (SQLException ex) {
/* 2993 */         throw ex;
/*      */       }
/*      */     
/* 2996 */     } catch (SQLException ex) {
/* 2997 */       Object[] args = { itemID, "STASH_ITEM_V8" };
/* 2998 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 3000 */       GDMsgLogger.addError(msg);
/* 3001 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 3004 */     return list;
/*      */   }
/*      */   
/*      */   private static List<Integer> getStashIDsByItemAndSuffix(String itemID, String suffixID, boolean isHardcore, String charName) {
/* 3008 */     List<Integer> list = new LinkedList<>();
/*      */     
/* 3010 */     if (suffixID == null) return list;
/*      */     
/* 3012 */     String command = "SELECT STASH_ID FROM STASH_ITEM_V8 WHERE ITEM_ID = ? AND SUFFIX_ID = ?";
/* 3013 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSCHC) {
/* 3014 */       if (isHardcore) {
/* 3015 */         command = command + " AND HARDCORE = true";
/*      */       } else {
/* 3017 */         command = command + " AND HARDCORE = false";
/*      */       } 
/*      */     }
/* 3020 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSoulbound && 
/* 3021 */       charName != null) {
/* 3022 */       command = command + " AND CHARNAME = '" + charName + "'";
/*      */     }
/*      */ 
/*      */     
/* 3026 */     try(Connection conn = GDDBData.getConnection(); 
/* 3027 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 3028 */       ps.setString(1, itemID);
/* 3029 */       ps.setString(2, suffixID);
/*      */       
/* 3031 */       try (ResultSet rs = ps.executeQuery()) {
/* 3032 */         list = AbstractItemCombination.wrapInteger(rs, 1);
/*      */         
/* 3034 */         conn.commit();
/*      */       }
/* 3036 */       catch (SQLException ex) {
/* 3037 */         throw ex;
/*      */       }
/*      */     
/* 3040 */     } catch (SQLException ex) {
/* 3041 */       Object[] args = { itemID, "STASH_ITEM_V8" };
/* 3042 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 3044 */       GDMsgLogger.addError(msg);
/* 3045 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 3048 */     return list;
/*      */   }
/*      */   
/*      */   private static List<Integer> getStashIDsByClassAndPrefixSuffix(String itemClassID, String prefixID, String suffixID, boolean isHardcore, String charName) {
/* 3052 */     List<Integer> list = new LinkedList<>();
/*      */     
/* 3054 */     if (prefixID == null && suffixID == null) return list;
/*      */     
/* 3056 */     String command = "SELECT STASH_ID FROM STASH_ITEM_V8 WHERE ITEM_CLASS = ? AND PREFIX_ID = ? AND SUFFIX_ID = ?";
/* 3057 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSCHC) {
/* 3058 */       if (isHardcore) {
/* 3059 */         command = command + " AND HARDCORE = true";
/*      */       } else {
/* 3061 */         command = command + " AND HARDCORE = false";
/*      */       } 
/*      */     }
/* 3064 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSoulbound && 
/* 3065 */       charName != null) {
/* 3066 */       command = command + " AND CHARNAME = '" + charName + "'";
/*      */     }
/*      */ 
/*      */     
/* 3070 */     String pre = "";
/* 3071 */     if (prefixID != null) pre = prefixID; 
/* 3072 */     String suf = "";
/* 3073 */     if (suffixID != null) suf = suffixID;
/*      */     
/* 3075 */     try(Connection conn = GDDBData.getConnection(); 
/* 3076 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 3077 */       ps.setString(1, itemClassID);
/* 3078 */       ps.setString(2, pre);
/* 3079 */       ps.setString(3, suf);
/*      */       
/* 3081 */       try (ResultSet rs = ps.executeQuery()) {
/* 3082 */         list = AbstractItemCombination.wrapInteger(rs, 1);
/*      */         
/* 3084 */         conn.commit();
/*      */       }
/* 3086 */       catch (SQLException ex) {
/* 3087 */         throw ex;
/*      */       }
/*      */     
/* 3090 */     } catch (SQLException ex) {
/* 3091 */       Object[] args = { itemClassID, "STASH_ITEM_V8" };
/* 3092 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 3094 */       GDMsgLogger.addError(msg);
/* 3095 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 3098 */     return list;
/*      */   }
/*      */   
/*      */   private static List<Integer> getStashIDsByClassAndPrefix(String itemClassID, String prefixID, boolean isHardcore, String charName) {
/* 3102 */     List<Integer> list = new LinkedList<>();
/*      */     
/* 3104 */     if (prefixID == null) return list;
/*      */     
/* 3106 */     String command = "SELECT STASH_ID FROM STASH_ITEM_V8 WHERE ITEM_CLASS = ? AND PREFIX_ID = ?";
/* 3107 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSCHC) {
/* 3108 */       if (isHardcore) {
/* 3109 */         command = command + " AND HARDCORE = true";
/*      */       } else {
/* 3111 */         command = command + " AND HARDCORE = false";
/*      */       } 
/*      */     }
/* 3114 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSoulbound && 
/* 3115 */       charName != null) {
/* 3116 */       command = command + " AND CHARNAME = '" + charName + "'";
/*      */     }
/*      */ 
/*      */     
/* 3120 */     try(Connection conn = GDDBData.getConnection(); 
/* 3121 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 3122 */       ps.setString(1, itemClassID);
/* 3123 */       ps.setString(2, prefixID);
/*      */       
/* 3125 */       try (ResultSet rs = ps.executeQuery()) {
/* 3126 */         list = AbstractItemCombination.wrapInteger(rs, 1);
/*      */         
/* 3128 */         conn.commit();
/*      */       }
/* 3130 */       catch (SQLException ex) {
/* 3131 */         throw ex;
/*      */       }
/*      */     
/* 3134 */     } catch (SQLException ex) {
/* 3135 */       Object[] args = { itemClassID, "STASH_ITEM_V8" };
/* 3136 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 3138 */       GDMsgLogger.addError(msg);
/* 3139 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 3142 */     return list;
/*      */   }
/*      */   
/*      */   private static List<Integer> getStashIDsByClassAndSuffix(String itemClassID, String suffixID, boolean isHardcore, String charName) {
/* 3146 */     List<Integer> list = new LinkedList<>();
/*      */     
/* 3148 */     if (suffixID == null) return list;
/*      */     
/* 3150 */     String command = "SELECT STASH_ID FROM STASH_ITEM_V8 WHERE ITEM_CLASS = ? AND SUFFIX_ID = ?";
/* 3151 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSCHC) {
/* 3152 */       if (isHardcore) {
/* 3153 */         command = command + " AND HARDCORE = true";
/*      */       } else {
/* 3155 */         command = command + " AND HARDCORE = false";
/*      */       } 
/*      */     }
/* 3158 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSoulbound && 
/* 3159 */       charName != null) {
/* 3160 */       command = command + " AND CHARNAME = '" + charName + "'";
/*      */     }
/*      */ 
/*      */     
/* 3164 */     try(Connection conn = GDDBData.getConnection(); 
/* 3165 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 3166 */       ps.setString(1, itemClassID);
/* 3167 */       ps.setString(2, suffixID);
/*      */       
/* 3169 */       try (ResultSet rs = ps.executeQuery()) {
/* 3170 */         list = AbstractItemCombination.wrapInteger(rs, 1);
/*      */         
/* 3172 */         conn.commit();
/*      */       }
/* 3174 */       catch (SQLException ex) {
/* 3175 */         throw ex;
/*      */       }
/*      */     
/* 3178 */     } catch (SQLException ex) {
/* 3179 */       Object[] args = { itemClassID, "STASH_ITEM_V8" };
/* 3180 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*      */       
/* 3182 */       GDMsgLogger.addError(msg);
/* 3183 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 3186 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean readGDS(InputStream reader, String filename, Charset cs) {
/* 3194 */     boolean success = false;
/*      */     
/*      */     try {
/* 3197 */       this.itemID = GDReader.readStringUByte(reader, cs);
/* 3198 */       this.prefixID = GDReader.readStringUByte(reader, cs);
/* 3199 */       this.suffixID = GDReader.readStringUByte(reader, cs);
/* 3200 */       this.modifierID = GDReader.readStringUByte(reader, cs);
/* 3201 */       this.transmuteID = GDReader.readStringUByte(reader, cs);
/* 3202 */       this.seed = GDReader.readInt(reader);
/* 3203 */       this.relicID = GDReader.readStringUByte(reader, cs);
/* 3204 */       this.relicBonusID = GDReader.readStringUByte(reader, cs);
/* 3205 */       this.relicSeed = GDReader.readInt(reader);
/* 3206 */       this.enchantmentID = GDReader.readStringUByte(reader, cs);
/* 3207 */       this.unknown = GDReader.readInt(reader);
/* 3208 */       this.enchantmentSeed = GDReader.readInt(reader);
/* 3209 */       this.var1 = GDReader.readInt(reader);
/* 3210 */       this.stackCount = GDReader.readInt(reader);
/* 3211 */       this.hardcore = GDReader.readByteBool(reader);
/* 3212 */       this.charname = GDReader.readStringUByte(reader, cs);
/*      */       
/* 3214 */       success = true;
/*      */     }
/* 3216 */     catch (IOException ex) {
/* 3217 */       Object[] args = { filename };
/* 3218 */       String s = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_FILE", args);
/*      */       
/* 3220 */       GDMsgLogger.addError(s);
/* 3221 */       GDMsgLogger.addError(ex);
/*      */       
/* 3223 */       success = false;
/*      */     } 
/*      */     
/* 3226 */     if (success) {
/* 3227 */       fillDependentStats(null);
/*      */       
/* 3229 */       if (GDMsgLogger.errorsInLog()) success = false;
/*      */     
/*      */     } 
/* 3232 */     return success;
/*      */   }
/*      */   
/*      */   public void writeGDS(FileOutputStream writer, Charset cs) throws IOException {
/* 3236 */     GDWriter.writeStringUByte(writer, this.itemID, cs);
/* 3237 */     GDWriter.writeStringUByte(writer, this.prefixID, cs);
/* 3238 */     GDWriter.writeStringUByte(writer, this.suffixID, cs);
/* 3239 */     GDWriter.writeStringUByte(writer, this.modifierID, cs);
/* 3240 */     GDWriter.writeStringUByte(writer, this.transmuteID, cs);
/* 3241 */     writer.write(GDWriter.intToBytes4(this.seed));
/* 3242 */     GDWriter.writeStringUByte(writer, this.relicID, cs);
/* 3243 */     GDWriter.writeStringUByte(writer, this.relicBonusID, cs);
/* 3244 */     writer.write(GDWriter.intToBytes4(this.relicSeed));
/* 3245 */     GDWriter.writeStringUByte(writer, this.enchantmentID, cs);
/* 3246 */     writer.write(GDWriter.intToBytes4(this.unknown));
/* 3247 */     writer.write(GDWriter.intToBytes4(this.enchantmentSeed));
/* 3248 */     writer.write(GDWriter.intToBytes4(this.var1));
/* 3249 */     writer.write(GDWriter.intToBytes4(this.stackCount));
/* 3250 */     if (this.hardcore) { writer.write(1); } else { writer.write(0); }
/* 3251 */      GDWriter.writeStringUByte(writer, this.charname, cs);
/*      */   }
/*      */   
/*      */   public static List<GDItem> loadGDS(File file, Charset cs) {
/* 3255 */     List<GDItem> items = new LinkedList<>();
/*      */     
/* 3257 */     String filename = null;
/*      */     try {
/* 3259 */       filename = file.getCanonicalPath();
/*      */     }
/* 3261 */     catch (IOException ex) {
/* 3262 */       filename = null;
/*      */     } 
/*      */     
/* 3265 */     try (InputStream reader = new BufferedInputStream(new FileInputStream(file))) {
/* 3266 */       int version = GDReader.readInt(reader);
/*      */       
/* 3268 */       int size = GDReader.readInt(reader);
/*      */       int i;
/* 3270 */       for (i = 0; i < size; i++) {
/* 3271 */         DBStashItem si = new DBStashItem();
/* 3272 */         boolean read = si.readGDS(reader, filename, cs);
/*      */         
/* 3274 */         if (read) {
/* 3275 */           GDItem item = new GDItem(si, filename);
/* 3276 */           items.add(item);
/*      */         }
/*      */       
/*      */       } 
/* 3280 */     } catch (IOException ex) {
/* 3281 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_LOAD_FILE"));
/* 3282 */       GDMsgLogger.addError(ex);
/*      */       
/* 3284 */       items = null;
/*      */     } 
/*      */     
/* 3287 */     return items;
/*      */   }
/*      */   
/*      */   private static boolean writeDBStashItemListGDS(File file, Charset cs, List<DBStashItem> items) {
/* 3291 */     boolean success = false;
/*      */     
/*      */     try {
/* 3294 */       file.createNewFile();
/*      */       
/* 3296 */       try (FileOutputStream writer = new FileOutputStream(file)) {
/* 3297 */         writer.write(GDWriter.intToBytes4(1));
/*      */         
/* 3299 */         int size = items.size();
/* 3300 */         writer.write(GDWriter.intToBytes4(size));
/*      */         
/* 3302 */         for (DBStashItem item : items) {
/* 3303 */           item.writeGDS(writer, cs);
/*      */         }
/*      */         
/* 3306 */         writer.flush();
/*      */         
/* 3308 */         success = true;
/*      */       }
/* 3310 */       catch (IOException ex) {
/* 3311 */         throw ex;
/*      */       }
/*      */     
/* 3314 */     } catch (IOException ex) {
/* 3315 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_WRITE_FILE"));
/* 3316 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 3319 */     return success;
/*      */   }
/*      */   
/*      */   public static boolean writeAllGDS(File file, Charset cs) {
/* 3323 */     boolean success = false;
/*      */     
/* 3325 */     List<DBStashItem> items = getAll();
/*      */     
/* 3327 */     success = writeDBStashItemListGDS(file, cs, items);
/*      */     
/* 3329 */     return success;
/*      */   }
/*      */   
/*      */   public static boolean writeGDStashItemListGDS(File file, Charset cs, List<GDItem> list) {
/* 3333 */     boolean success = false;
/*      */     
/* 3335 */     List<DBStashItem> items = new LinkedList<>();
/*      */     
/* 3337 */     for (GDItem gdi : list) {
/* 3338 */       DBStashItem si = gdi.getDBStashItem();
/*      */       
/* 3340 */       if (si != null) items.add(si);
/*      */     
/*      */     } 
/* 3343 */     success = writeDBStashItemListGDS(file, cs, items);
/*      */     
/* 3345 */     return success;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean readIAS(InputStream reader, int version, String filename) {
/* 3350 */     boolean success = false;
/* 3351 */     int len = 0;
/*      */     
/*      */     try {
/* 3354 */       this.itemID = GDReader.readStringUByte(reader);
/* 3355 */       this.prefixID = GDReader.readStringUByte(reader);
/* 3356 */       this.suffixID = GDReader.readStringUByte(reader);
/* 3357 */       this.modifierID = GDReader.readStringUByte(reader);
/* 3358 */       this.transmuteID = GDReader.readStringUByte(reader);
/* 3359 */       this.seed = GDReader.readInt(reader);
/* 3360 */       this.relicID = GDReader.readStringUByte(reader);
/* 3361 */       this.relicBonusID = GDReader.readStringUByte(reader);
/* 3362 */       this.relicSeed = GDReader.readInt(reader);
/* 3363 */       this.enchantmentID = GDReader.readStringUByte(reader);
/* 3364 */       this.unknown = GDReader.readInt(reader);
/* 3365 */       this.enchantmentSeed = GDReader.readInt(reader);
/* 3366 */       this.var1 = GDReader.readShort(reader);
/* 3367 */       this.stackCount = GDReader.readInt(reader);
/* 3368 */       this.hardcore = GDReader.readByteBool(reader);
/* 3369 */       byte exp = GDReader.readByte(reader);
/* 3370 */       String modname = GDReader.readStringUByte(reader);
/* 3371 */       if (version == 1) {
/* 3372 */         long l = GDReader.readLong(reader);
/*      */       }
/* 3374 */       if (version >= 2) {
/* 3375 */         String azurePartition = GDReader.readStringUByte(reader);
/* 3376 */         String str1 = GDReader.readStringUByte(reader);
/*      */       } 
/* 3378 */       if (version >= 3) {
/* 3379 */         byte b = GDReader.readByte(reader);
/*      */       }
/*      */       
/* 3382 */       if (version >= 5) {
/* 3383 */         GDReader.readStringUByte(reader);
/* 3384 */         GDReader.readStringUByte(reader);
/* 3385 */         GDReader.readByte(reader);
/*      */       } 
/*      */       
/* 3388 */       success = true;
/*      */     }
/* 3390 */     catch (IOException ex) {
/* 3391 */       Object[] args = { filename };
/* 3392 */       String s = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_FILE", args);
/*      */       
/* 3394 */       GDMsgLogger.addError(s);
/* 3395 */       GDMsgLogger.addError(ex);
/*      */       
/* 3397 */       success = false;
/*      */     } 
/*      */     
/* 3400 */     if (success) {
/* 3401 */       fillDependentStats(null);
/*      */       
/* 3403 */       if (GDMsgLogger.errorsInLog()) success = false;
/*      */     
/*      */     } 
/* 3406 */     return success;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<GDItem> loadIAS(File file) {
/* 3412 */     List<GDItem> items = new LinkedList<>();
/*      */     
/* 3414 */     String filename = null;
/*      */     try {
/* 3416 */       filename = file.getCanonicalPath();
/*      */     }
/* 3418 */     catch (IOException ex) {
/* 3419 */       filename = null;
/*      */     } 
/*      */     
/* 3422 */     try (InputStream reader = new BufferedInputStream(new FileInputStream(file))) {
/* 3423 */       int version = GDReader.readShort(reader);
/*      */       
/* 3425 */       if (version < 1 || version > 4) throw new FileVersionException();
/*      */       
/* 3427 */       int size = GDReader.readInt(reader);
/*      */       int i;
/* 3429 */       for (i = 0; i < size; i++) {
/* 3430 */         DBStashItem si = new DBStashItem();
/* 3431 */         boolean read = si.readIAS(reader, version, filename);
/*      */         
/* 3433 */         if (read) {
/* 3434 */           GDItem item = new GDItem(si, filename);
/* 3435 */           items.add(item);
/*      */         }
/*      */       
/*      */       } 
/* 3439 */     } catch (FileVersionException ex) {
/* 3440 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/* 3441 */       GDMsgLogger.addError((Throwable)ex);
/*      */       
/* 3443 */       items = null;
/*      */     }
/* 3445 */     catch (IOException ex) {
/* 3446 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_LOAD_FILE"));
/* 3447 */       GDMsgLogger.addError(ex);
/*      */       
/* 3449 */       items = null;
/*      */     } 
/*      */     
/* 3452 */     return items;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readNewFormat(int version) throws IOException {
/* 3460 */     switch (version) {
/*      */       case 3:
/* 3462 */         readNewFormat_V3();
/*      */         break;
/*      */       
/*      */       case 4:
/* 3466 */         readNewFormat_V4();
/*      */         break;
/*      */       
/*      */       default:
/* 3470 */         throw new IOException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*      */     } 
/*      */     
/* 3473 */     fillDependentStats(null);
/*      */   }
/*      */   
/*      */   private void readNewFormat_V3() throws IOException {
/* 3477 */     String s = null;
/* 3478 */     int i = 0;
/*      */     
/* 3480 */     i = GDReader.readEncInt(true);
/* 3481 */     setStackCount(i);
/*      */     
/* 3483 */     s = GDReader.readEncString();
/* 3484 */     setItemID(s);
/*      */     
/* 3486 */     s = GDReader.readEncString();
/* 3487 */     setPrefixID(s);
/*      */     
/* 3489 */     s = GDReader.readEncString();
/* 3490 */     setSuffixID(s);
/*      */     
/* 3492 */     s = GDReader.readEncString();
/* 3493 */     setModifierID(s);
/*      */     
/* 3495 */     s = GDReader.readEncString();
/* 3496 */     setTransmuteID(s);
/*      */     
/* 3498 */     i = GDReader.readEncInt(true);
/* 3499 */     setItemSeed(i);
/*      */     
/* 3501 */     s = GDReader.readEncString();
/* 3502 */     setComponentID(s);
/*      */     
/* 3504 */     s = GDReader.readEncString();
/* 3505 */     setCompletionBonusID(s);
/*      */     
/* 3507 */     i = GDReader.readEncInt(true);
/* 3508 */     setComponentSeed(i);
/*      */     
/* 3510 */     s = GDReader.readEncString();
/* 3511 */     setAugmentID(s);
/*      */     
/* 3513 */     i = GDReader.readEncInt(true);
/* 3514 */     setUnknown(i);
/*      */     
/* 3516 */     i = GDReader.readEncInt(true);
/* 3517 */     setAugmentSeed(i);
/*      */     
/* 3519 */     i = GDReader.readEncInt(true);
/* 3520 */     setVar1(i);
/*      */   }
/*      */   
/*      */   private void readNewFormat_V4() throws IOException {
/* 3524 */     String s = null;
/* 3525 */     int i = 0;
/*      */     
/* 3527 */     s = GDReader.readEncString();
/* 3528 */     setItemID(s);
/*      */     
/* 3530 */     s = GDReader.readEncString();
/* 3531 */     setPrefixID(s);
/*      */     
/* 3533 */     s = GDReader.readEncString();
/* 3534 */     setSuffixID(s);
/*      */     
/* 3536 */     s = GDReader.readEncString();
/* 3537 */     setModifierID(s);
/*      */     
/* 3539 */     s = GDReader.readEncString();
/* 3540 */     setTransmuteID(s);
/*      */     
/* 3542 */     i = GDReader.readEncInt(true);
/* 3543 */     setItemSeed(i);
/*      */     
/* 3545 */     s = GDReader.readEncString();
/* 3546 */     setComponentID(s);
/*      */     
/* 3548 */     s = GDReader.readEncString();
/* 3549 */     setCompletionBonusID(s);
/*      */     
/* 3551 */     i = GDReader.readEncInt(true);
/* 3552 */     setComponentSeed(i);
/*      */     
/* 3554 */     s = GDReader.readEncString();
/* 3555 */     setAugmentID(s);
/*      */     
/* 3557 */     i = GDReader.readEncInt(true);
/* 3558 */     setUnknown(i);
/*      */     
/* 3560 */     i = GDReader.readEncInt(true);
/* 3561 */     setAugmentSeed(i);
/*      */     
/* 3563 */     i = GDReader.readEncInt(true);
/* 3564 */     setVar1(i);
/*      */     
/* 3566 */     i = GDReader.readEncInt(true);
/* 3567 */     setStackCount(i);
/*      */   }
/*      */   
/*      */   public int getByteSize() {
/* 3571 */     int size = 0;
/* 3572 */     String s = null;
/*      */     
/* 3574 */     size += 4;
/* 3575 */     s = getItemID();
/* 3576 */     if (s != null) size += s.length();
/*      */     
/* 3578 */     size += 4;
/* 3579 */     s = getPrefixID();
/* 3580 */     if (s != null) size += s.length();
/*      */     
/* 3582 */     size += 4;
/* 3583 */     s = getSuffixID();
/* 3584 */     if (s != null) size += s.length();
/*      */     
/* 3586 */     size += 4;
/* 3587 */     s = getModifierID();
/* 3588 */     if (s != null) size += s.length();
/*      */     
/* 3590 */     size += 4;
/* 3591 */     s = getTransmuteID();
/* 3592 */     if (s != null) size += s.length();
/*      */     
/* 3594 */     size += 4;
/*      */     
/* 3596 */     size += 4;
/* 3597 */     s = getComponentID();
/* 3598 */     if (s != null) size += s.length();
/*      */     
/* 3600 */     size += 4;
/* 3601 */     s = getCompletionBonusID();
/* 3602 */     if (s != null) size += s.length();
/*      */     
/* 3604 */     size += 4;
/*      */     
/* 3606 */     size += 4;
/* 3607 */     s = getAugmentID();
/* 3608 */     if (s != null) size += s.length();
/*      */     
/* 3610 */     size += 4;
/* 3611 */     size += 4;
/* 3612 */     size += 4;
/* 3613 */     size += 4;
/*      */     
/* 3615 */     return size;
/*      */   }
/*      */   
/*      */   public void write() throws IOException {
/* 3619 */     GDWriter.writeString(getItemID());
/* 3620 */     GDWriter.writeString(getPrefixID());
/* 3621 */     GDWriter.writeString(getSuffixID());
/* 3622 */     GDWriter.writeString(getModifierID());
/* 3623 */     GDWriter.writeString(getTransmuteID());
/* 3624 */     GDWriter.writeInt(getItemSeed());
/* 3625 */     GDWriter.writeString(getComponentID());
/* 3626 */     GDWriter.writeString(getCompletionBonusID());
/* 3627 */     GDWriter.writeInt(getComponentSeed());
/* 3628 */     GDWriter.writeString(getAugmentID());
/* 3629 */     GDWriter.writeInt(getUnknown());
/* 3630 */     GDWriter.writeInt(getAugmentSeed());
/* 3631 */     GDWriter.writeInt(getVar1());
/* 3632 */     GDWriter.writeInt(getStackCount());
/*      */   } }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBStashItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */