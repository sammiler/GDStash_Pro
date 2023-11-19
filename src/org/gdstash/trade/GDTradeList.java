/*     */ package org.gdstash.trade;
/*     */ 
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.Clipboard;
/*     */ import java.awt.datatransfer.StringSelection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.db.DBItem;
/*     */ import org.gdstash.db.DBStashItem;
/*     */ import org.gdstash.item.GDItem;
/*     */ import org.gdstash.util.GDConstants;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDTradeList
/*     */ {
/*     */   private static class Sorter
/*     */     implements Comparator<ItemCount>
/*     */   {
/*     */     private Sorter() {}
/*     */     
/*     */     public int compare(GDTradeList.ItemCount i1, GDTradeList.ItemCount i2) {
/*  29 */       if (i1 == null) {
/*  30 */         if (i2 == null) return 0;
/*     */         
/*  32 */         return -1;
/*     */       } 
/*     */       
/*  35 */       if (i2 == null) return 1;
/*     */       
/*  37 */       int ir1 = GDTradeList.getRarityInt(i1.getRarity());
/*  38 */       int ir2 = GDTradeList.getRarityInt(i2.getRarity());
/*     */       
/*  40 */       if (ir1 > ir2) return 1; 
/*  41 */       if (ir1 < ir2) return -1;
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
/*     */ 
/*     */       
/*  56 */       int ic1 = GDTradeList.getItemClassInt(i1.getItemClass());
/*  57 */       int ic2 = GDTradeList.getItemClassInt(i2.getItemClass());
/*     */       
/*  59 */       if (ic1 > ic2) return 1; 
/*  60 */       if (ic1 < ic2) return -1;
/*     */       
/*  62 */       String in1 = i1.getLongItemName();
/*  63 */       String in2 = i2.getLongItemName();
/*     */       
/*  65 */       if (in1 == null) {
/*  66 */         if (in2 == null) return 0;
/*     */         
/*  68 */         return -1;
/*     */       } 
/*     */       
/*  71 */       if (in2 == null) return 1;
/*     */       
/*  73 */       return in1.compareTo(in2);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ItemCount {
/*     */     private GDItem item;
/*     */     private int count;
/*     */     
/*     */     public ItemCount(GDItem item) {
/*  82 */       this(item, 1);
/*     */     }
/*     */     
/*     */     public ItemCount(GDItem item, int count) {
/*  86 */       this.item = item;
/*  87 */       this.count = count;
/*     */ 
/*     */       
/*  90 */       if (item.getStackCount() > 1) this.count = item.getStackCount(); 
/*     */     }
/*     */     
/*     */     public String getDescription() {
/*  94 */       String name = this.item.getLongItemName();
/*  95 */       if (this.item.getItemSetName() != null) {
/*  96 */         name = name + " [" + this.item.getItemSetName() + "]";
/*     */       }
/*     */       
/*  99 */       String tagItem = "TAG_LIST_ITEM";
/* 100 */       if (this.item.getRarity().equals("Epic")) tagItem = "TAG_LIST_ITEM_EPIC"; 
/* 101 */       if (this.item.getRarity().equals("Legendary")) tagItem = "TAG_LIST_ITEM_LEGENDARY";
/*     */       
/* 103 */       String text = null;
/* 104 */       if (this.count > 1) {
/* 105 */         tagItem = tagItem + "_MULTI";
/*     */         
/* 107 */         Object[] args = { name, Integer.valueOf(this.count) };
/* 108 */         text = GDMsgFormatter.format(GDMsgFormatter.rbHTML, tagItem, args);
/*     */       } else {
/* 110 */         Object[] args = { name };
/* 111 */         text = GDMsgFormatter.format(GDMsgFormatter.rbHTML, tagItem, args);
/*     */       } 
/*     */       
/* 114 */       return text;
/*     */     }
/*     */     
/*     */     public String getItemID() {
/* 118 */       return this.item.getItemID();
/*     */     }
/*     */     
/*     */     public void incrementCount() {
/* 122 */       this.count++;
/*     */     }
/*     */     
/*     */     public String getRarity() {
/* 126 */       return this.item.getRarity();
/*     */     }
/*     */     
/*     */     public String getItemClass() {
/* 130 */       return this.item.getItemClass();
/*     */     }
/*     */     
/*     */     public String getLongItemName() {
/* 134 */       return this.item.getLongItemName();
/*     */     }
/*     */   }
/*     */   
/*     */   private static int getRarityInt(String rarity) {
/* 139 */     int val = 0;
/*     */     
/* 141 */     if (rarity.equals("Epic")) val = 1; 
/* 142 */     if (rarity.equals("Legendary")) val = 2;
/*     */     
/* 144 */     return val;
/*     */   }
/*     */   
/*     */   private static int getItemClassInt(String itemClass) {
/* 148 */     if (itemClass == null) return 0;
/*     */     
/* 150 */     int val = 0;
/*     */     
/* 152 */     if (itemClass.equals("ArmorProtective_Head")) val = 1; 
/* 153 */     if (itemClass.equals("ArmorProtective_Shoulders")) val = 2; 
/* 154 */     if (itemClass.equals("ArmorProtective_Chest")) val = 3; 
/* 155 */     if (itemClass.equals("ArmorProtective_Hands")) val = 4; 
/* 156 */     if (itemClass.equals("ArmorProtective_Legs")) val = 5; 
/* 157 */     if (itemClass.equals("ArmorProtective_Feet")) val = 6;
/*     */     
/* 159 */     if (itemClass.equals("ArmorProtective_Waist")) val = 11; 
/* 160 */     if (itemClass.equals("ArmorJewelry_Amulet")) val = 12; 
/* 161 */     if (itemClass.equals("ArmorJewelry_Medal")) val = 13; 
/* 162 */     if (itemClass.equals("ArmorJewelry_Ring")) val = 14;
/*     */     
/* 164 */     if (itemClass.equals("WeaponMelee_Axe")) val = 21; 
/* 165 */     if (itemClass.equals("WeaponMelee_Dagger")) val = 22; 
/* 166 */     if (itemClass.equals("WeaponMelee_Mace")) val = 23; 
/* 167 */     if (itemClass.equals("WeaponMelee_Scepter")) val = 24; 
/* 168 */     if (itemClass.equals("WeaponHunting_Spear")) val = 25; 
/* 169 */     if (itemClass.equals("WeaponMelee_Sword")) val = 26;
/*     */     
/* 171 */     if (itemClass.equals("WeaponArmor_Shield")) val = 31; 
/* 172 */     if (itemClass.equals("WeaponArmor_Offhand")) val = 32;
/*     */     
/* 174 */     if (itemClass.equals("WeaponMelee_Axe2h")) val = 41; 
/* 175 */     if (itemClass.equals("WeaponMelee_Mace2h")) val = 42; 
/* 176 */     if (itemClass.equals("WeaponMagical_Staff")) val = 43; 
/* 177 */     if (itemClass.equals("WeaponMelee_Sword2h")) val = 44;
/*     */     
/* 179 */     if (itemClass.equals("WeaponHunting_Ranged1h")) val = 51; 
/* 180 */     if (itemClass.equals("WeaponHunting_Ranged2h")) val = 52;
/*     */     
/* 182 */     if (itemClass.equals("ItemArtifactFormula")) val = 61;
/*     */     
/* 184 */     return val;
/*     */   }
/*     */   
/*     */   private static List<ItemCount> getUniques() {
/* 188 */     List<DBItem> list = DBItem.getAllUniques();
/* 189 */     List<DBItem> bp = DBItem.getAllBlueprints();
/*     */     
/* 191 */     list.addAll(bp);
/*     */     
/* 193 */     return filterItems(list);
/*     */   }
/*     */   
/*     */   private static List<ItemCount> getGDStashItemListUniques(List<GDItem> list) {
/* 197 */     if (list == null) return null;
/*     */     
/* 199 */     List<GDItem> uniques = new LinkedList<>();
/*     */     
/* 201 */     for (GDItem gdi : list) {
/* 202 */       if (gdi.isUnique()) {
/* 203 */         uniques.add(gdi);
/*     */       }
/*     */     } 
/*     */     
/* 207 */     return buildItemCount(uniques);
/*     */   }
/*     */   
/*     */   private static List<ItemCount> getLegendaries() {
/* 211 */     List<DBItem> list = DBItem.getAllLegendaries();
/* 212 */     List<DBItem> bp = DBItem.getAllLegendaryBlueprints();
/*     */     
/* 214 */     list.addAll(bp);
/*     */     
/* 216 */     return filterItems(list);
/*     */   }
/*     */   
/*     */   private static List<ItemCount> getGDStashItemListLegendaries(List<GDItem> list) {
/* 220 */     if (list == null) return null;
/*     */     
/* 222 */     List<GDItem> legendaries = new LinkedList<>();
/*     */     
/* 224 */     for (GDItem gdi : list) {
/* 225 */       if (gdi.isLegendary()) {
/* 226 */         legendaries.add(gdi);
/*     */       }
/*     */     } 
/*     */     
/* 230 */     return buildItemCount(legendaries);
/*     */   }
/*     */   
/*     */   private static List<ItemCount> filterItems(List<DBItem> list) {
/* 234 */     List<ItemCount> allItems = new LinkedList<>();
/*     */     
/* 236 */     if (list == null) return allItems;
/*     */     
/* 238 */     for (DBItem dbi : list) {
/* 239 */       String id = dbi.getItemID();
/*     */       
/* 241 */       List<GDItem> items = DBStashItem.getGDItemsByItemID(id);
/*     */       
/* 243 */       Iterator<GDItem> iterator = items.iterator(); if (iterator.hasNext()) { GDItem gdi = iterator.next();
/* 244 */         allItems.add(new ItemCount(gdi, items.size())); }
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 251 */     return allItems;
/*     */   }
/*     */   
/*     */   private static List<ItemCount> buildItemCount(List<GDItem> list) {
/* 255 */     List<ItemCount> allItems = new LinkedList<>();
/*     */     
/* 257 */     if (list == null) return allItems;
/*     */     
/* 259 */     for (GDItem gdi : list) {
/* 260 */       String id = gdi.getItemID();
/* 261 */       boolean found = false;
/*     */       
/* 263 */       for (ItemCount ic : allItems) {
/* 264 */         if (id.equals(ic.getItemID())) {
/* 265 */           found = true;
/*     */           
/* 267 */           ic.incrementCount();
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 273 */       if (!found) {
/* 274 */         allItems.add(new ItemCount(gdi));
/*     */       }
/*     */     } 
/*     */     
/* 278 */     return allItems;
/*     */   }
/*     */   
/*     */   private static String buildText(List<ItemCount> items) {
/* 282 */     Collections.sort(items, new Sorter());
/*     */     
/* 284 */     String text = "";
/*     */     
/* 286 */     String rarity = null;
/* 287 */     String itemClass = null;
/* 288 */     String set = null;
/*     */     
/* 290 */     boolean firstList = true;
/*     */     
/* 292 */     for (ItemCount gdi : items) {
/* 293 */       String iRare = gdi.getRarity();
/* 294 */       String iClass = gdi.getItemClass();
/*     */ 
/*     */       
/* 297 */       boolean newRarity = false;
/* 298 */       boolean newClass = false;
/*     */       
/* 300 */       boolean newList = false;
/*     */       
/* 302 */       if (rarity == null) {
/* 303 */         newRarity = true;
/*     */       } else {
/* 305 */         newRarity = !rarity.equals(iRare);
/*     */       } 
/*     */       
/* 308 */       if (itemClass == null) {
/* 309 */         newClass = true;
/*     */       } else {
/* 311 */         newClass = !itemClass.equals(iClass);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 320 */       rarity = iRare;
/* 321 */       itemClass = iClass;
/*     */ 
/*     */       
/* 324 */       String preList = "";
/*     */       
/* 326 */       if (newRarity) {
/* 327 */         String str = null;
/*     */         
/* 329 */         if (rarity.equals("Epic")) {
/* 330 */           str = GDMsgFormatter.getString(GDMsgFormatter.rbHTML, "TXT_EPIC_ITEMS");
/*     */           
/* 332 */           Object[] args = { str };
/* 333 */           str = GDMsgFormatter.format(GDMsgFormatter.rbHTML, "TAG_EPIC_HEADER", args);
/*     */         } 
/*     */         
/* 336 */         if (rarity.equals("Legendary")) {
/* 337 */           str = GDMsgFormatter.getString(GDMsgFormatter.rbHTML, "TXT_LEGENDARY_ITEMS");
/*     */           
/* 339 */           Object[] args = { str };
/* 340 */           str = GDMsgFormatter.format(GDMsgFormatter.rbHTML, "TAG_LEGENDARY_HEADER", args);
/*     */         } 
/*     */         
/* 343 */         if (str != null) preList = preList + str + GDConstants.LINE_SEPARATOR;
/*     */         
/* 345 */         newList = true;
/*     */       } 
/*     */ 
/*     */       
/* 349 */       if (newClass) {
/* 350 */         String str = null;
/*     */         
/* 352 */         if (rarity.equals("Epic")) {
/* 353 */           str = GDMsgFormatter.getString(GDMsgFormatter.rbHTML, itemClass);
/*     */           
/* 355 */           Object[] args = { str };
/* 356 */           str = GDMsgFormatter.format(GDMsgFormatter.rbHTML, "TAG_EPIC_ITEMTYPE", args);
/*     */         } 
/*     */         
/* 359 */         if (rarity.equals("Legendary")) {
/* 360 */           str = GDMsgFormatter.getString(GDMsgFormatter.rbHTML, itemClass);
/*     */           
/* 362 */           Object[] args = { str };
/* 363 */           str = GDMsgFormatter.format(GDMsgFormatter.rbHTML, "TAG_LEGENDARY_ITEMTYPE", args);
/*     */         } 
/*     */         
/* 366 */         if (str != null) preList = preList + str + GDConstants.LINE_SEPARATOR;
/*     */         
/* 368 */         newList = true;
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 389 */       if (newList) {
/* 390 */         if (firstList) {
/* 391 */           firstList = false;
/*     */         } else {
/* 393 */           String str1 = GDMsgFormatter.getString(GDMsgFormatter.rbHTML, "TAG_LIST_END");
/*     */           
/* 395 */           if (str1 != null) text = text + str1 + GDConstants.LINE_SEPARATOR;
/*     */         
/*     */         } 
/* 398 */         if (preList.length() > 0) text = text + preList;
/*     */         
/* 400 */         String str = null;
/*     */         
/* 402 */         str = GDMsgFormatter.getString(GDMsgFormatter.rbHTML, "TAG_LIST_BEGIN");
/*     */         
/* 404 */         if (str != null) text = text + str + GDConstants.LINE_SEPARATOR;
/*     */       
/*     */       } 
/* 407 */       String s = gdi.getDescription();
/*     */       
/* 409 */       if (s != null) text = text + s + GDConstants.LINE_SEPARATOR;
/*     */     
/*     */     } 
/* 412 */     if (!firstList) {
/* 413 */       String s = GDMsgFormatter.getString(GDMsgFormatter.rbHTML, "TAG_LIST_END");
/*     */       
/* 415 */       if (s != null) text = text + s + GDConstants.LINE_SEPARATOR;
/*     */     
/*     */     } 
/* 418 */     return text;
/*     */   }
/*     */   
/*     */   public static void extractUniques() {
/* 422 */     List<ItemCount> items = getUniques();
/*     */     
/* 424 */     exportItems(items);
/*     */   }
/*     */   
/*     */   public static void extractUniques(List<GDItem> list) {
/* 428 */     List<ItemCount> items = getGDStashItemListUniques(list);
/*     */     
/* 430 */     if (list == null || list.isEmpty()) {
/* 431 */       items = getUniques();
/*     */     }
/*     */     
/* 434 */     exportItems(items);
/*     */   }
/*     */   
/*     */   public static void extractLegendaries() {
/* 438 */     List<ItemCount> items = getLegendaries();
/*     */     
/* 440 */     exportItems(items);
/*     */   }
/*     */   
/*     */   public static void extractLegendaries(List<GDItem> list) {
/* 444 */     List<ItemCount> items = getGDStashItemListLegendaries(list);
/*     */     
/* 446 */     if (list == null || list.isEmpty()) {
/* 447 */       items = getLegendaries();
/*     */     }
/*     */     
/* 450 */     exportItems(items);
/*     */   }
/*     */   
/*     */   public static void exportItems(List<ItemCount> items) {
/* 454 */     String text = buildText(items);
/*     */     
/* 456 */     Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
/* 457 */     StringSelection strSel = new StringSelection(text);
/* 458 */     clipboard.setContents(strSel, null);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\trade\GDTradeList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */