/*     */ package org.gdstash.ui.table;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.db.*;
/*     */
/*     */
/*     */
/*     */
/*     */ import org.gdstash.item.GDItem;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDItemSetInfo implements Comparable<GDItemSetInfo> {
/*     */   private DBItemSet dbItemSet;
/*     */   private int itemSetLevel;
/*     */   private String itemSetRarity;
/*     */   private List<GDItemInfo> itemInfos;
/*     */   private List<DBItemCraft> dbItemCrafts;
/*     */   private List<GDItemInfo> bpInfos;
/*     */   private boolean scComplete;
/*     */   private boolean hcComplete;
/*     */   
/*     */   private static class ItemInfoComparator implements Comparator<GDItemInfo> { public int compare(GDItemInfo ii1, GDItemInfo ii2) {
/*  28 */       int i1 = ItemClass.getClassInt(ii1.gdItem.getItemClass());
/*  29 */       int i2 = ItemClass.getClassInt(ii2.gdItem.getItemClass());
/*     */       
/*  31 */       return i1 - i2;
/*     */     }
/*     */     private ItemInfoComparator() {} }
/*     */   private static class ItemSetInfoComparator implements Comparator<GDItemSetInfo> { private ItemSetInfoComparator() {}
/*     */     
/*     */     public int compare(GDItemSetInfo is1, GDItemSetInfo is2) {
/*  37 */       int iRarity = is1.getItemSetRarityInt() - is2.getItemSetRarityInt();
/*     */       
/*  39 */       if (iRarity != 0) return iRarity;
/*     */       
/*  41 */       int iName = is1.dbItemSet.getName().compareTo(is2.dbItemSet.getName());
/*     */       
/*  43 */       if (iName != 0) return iName;
/*     */       
/*  45 */       return is1.getItemSetLevel() - is2.getItemSetLevel();
/*     */     } }
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
/*     */   public static List<GDItemSetInfo> buildSetInfo() {
/*  59 */     List<GDItemSetInfo> list = new LinkedList<>();
/*     */     
/*  61 */     List<DBItemSet> sets = DBItemSet.getAll();
/*  62 */     for (DBItemSet set : sets) {
/*  63 */       GDItemSetInfo setInfo = new GDItemSetInfo(set);
/*     */       
/*  65 */       list.add(setInfo);
/*     */     } 
/*     */     
/*  68 */     Collections.sort(list, new ItemSetInfoComparator());
/*     */     
/*  70 */     return list;
/*     */   }
/*     */   
/*     */   private GDItemSetInfo(DBItemSet set) {
/*  74 */     this.dbItemSet = set;
/*     */     
/*  76 */     List<String> itemIDs = DBItemSetAlloc.getByItemSetID(set.getItemSetID());
/*  77 */     List<GDItem> setItems = DBItem.getGDItemsByItemIDs(itemIDs);
/*     */     
/*  79 */     this.dbItemCrafts = new LinkedList<>();
/*  80 */     List<GDItem> blueprints = new LinkedList<>();
/*  81 */     List<GDItem> uniqueBPs = new LinkedList<>();
/*  82 */     for (String itemID : itemIDs) {
/*  83 */       DBItemCraft ic = DBItemCraft.getByCraftID(itemID);
/*     */       
/*  85 */       if (ic != null) {
/*  86 */         this.dbItemCrafts.add(ic);
/*     */         
/*  88 */         List<GDItem> bps = DBStashItem.getGDItemsByItemID(ic.getItemID());
/*     */         
/*  90 */         if (bps != null && !bps.isEmpty()) {
/*  91 */           blueprints.addAll(bps);
/*     */           
/*  93 */           uniqueBPs.add(bps.get(0));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  98 */     if (!setItems.isEmpty()) {
/*  99 */       GDItem gdi = setItems.get(0);
/*     */       
/* 101 */       this.itemSetLevel = gdi.getRequiredLevel();
/* 102 */       this.itemSetRarity = gdi.getRarity();
/*     */     } 
/*     */     
/* 105 */     List<GDItem> items = DBStashItem.getGDItemsByItemIDs(itemIDs);
/*     */     
/* 107 */     this.itemInfos = GDItemInfo.buildItemInfo(setItems, items);
/* 108 */     this.bpInfos = GDItemInfo.buildItemInfo(uniqueBPs, blueprints);
/*     */     
/* 110 */     Collections.sort(this.itemInfos, new ItemInfoComparator());
/*     */     
/* 112 */     assignBlueprints();
/*     */   }
/*     */ 
/*     */   
/*     */   private void assignBlueprints() {
/* 117 */     if (this.bpInfos == null)
/*     */       return; 
/* 119 */     for (GDItemInfo bpInfo : this.bpInfos) {
/* 120 */       label23: for (DBItemCraft dbItemCraft : this.dbItemCrafts) {
/* 121 */         if (dbItemCraft.getItemID().equals(bpInfo.gdItem.getItemID())) {
/* 122 */           Iterator<GDItemInfo> iterator = this.itemInfos.iterator();
/*     */           break label23;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void determineCompleteness() {
/* 138 */     this.scComplete = true;
/* 139 */     this.hcComplete = true;
/*     */     
/* 141 */     for (GDItemInfo itemInfo : this.itemInfos) {
/* 142 */       if (itemInfo.scCount <= 0 && itemInfo.scBlueprint == null) {
/* 143 */         this.scComplete = false;
/*     */       }
/*     */       
/* 146 */       if (itemInfo.hcCount <= 0 && itemInfo.hcBlueprint == null) {
/* 147 */         this.hcComplete = false;
/*     */       }
/*     */       
/* 150 */       if (!this.scComplete && !this.hcComplete)
/*     */         break; 
/*     */     } 
/*     */   }
/*     */   public String getItemSetID() {
/* 155 */     return this.dbItemSet.getItemSetID();
/*     */   }
/*     */   
/*     */   public String getItemSetName() {
/* 159 */     return this.dbItemSet.getName();
/*     */   }
/*     */   
/*     */   public GDItem.LabelInfo getItemSetNameLabel() {
/* 163 */     GDItem.LabelInfo label = new GDItem.LabelInfo();
/*     */     
/* 165 */     label.text = getItemSetName();
/* 166 */     label.foreground = ItemClass.getRarityColor(this.itemSetRarity);
/*     */ 
/*     */ 
/*     */     
/* 170 */     return label;
/*     */   }
/*     */   
/*     */   public int getItemSetLevel() {
/* 174 */     return this.itemSetLevel;
/*     */   }
/*     */   
/*     */   public GDItem.LabelInfo getItemSetLevelLabel() {
/* 178 */     GDItem.LabelInfo label = new GDItem.LabelInfo();
/*     */     
/* 180 */     label.text = Integer.toString(getItemSetLevel());
/* 181 */     label.foreground = ItemClass.getRarityColor(this.itemSetRarity);
/*     */ 
/*     */ 
/*     */     
/* 185 */     return label;
/*     */   }
/*     */   
/*     */   public String getItemSetRarity() {
/* 189 */     return this.itemSetRarity;
/*     */   }
/*     */   
/*     */   public GDItem.LabelInfo getItemSetRarityLabel() {
/* 193 */     GDItem.LabelInfo label = new GDItem.LabelInfo();
/*     */     
/* 195 */     label.text = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "RARITY_ITEM_COMMON");
/*     */     
/* 197 */     if (this.itemSetRarity.equals("Magical")) {
/* 198 */       label.text = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "RARITY_ITEM_MAGICAL");
/*     */     }
/* 200 */     if (this.itemSetRarity.equals("Rare")) {
/* 201 */       label.text = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "RARITY_ITEM_RARE");
/*     */     }
/* 203 */     if (this.itemSetRarity.equals("Epic")) {
/* 204 */       label.text = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "RARITY_ITEM_EPIC");
/*     */     }
/* 206 */     if (this.itemSetRarity.equals("Legendary")) {
/* 207 */       label.text = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "RARITY_ITEM_LEGENDARY");
/*     */     }
/*     */     
/* 210 */     label.foreground = ItemClass.getRarityColor(this.itemSetRarity);
/*     */ 
/*     */ 
/*     */     
/* 214 */     return label;
/*     */   }
/*     */   
/*     */   public int getItemSetRarityInt() {
/* 218 */     return ItemClass.getRarityInt(this.itemSetRarity);
/*     */   }
/*     */   
/*     */   public List<GDItemInfo> getItemInfoList() {
/* 222 */     return this.itemInfos;
/*     */   }
/*     */   
/*     */   public int getMaxImageHeight() {
/* 226 */     int height = 0;
/*     */     
/* 228 */     for (GDItemInfo info : this.itemInfos) {
/* 229 */       BufferedImage img = info.getFullImage();
/*     */       
/* 231 */       if (img != null && 
/* 232 */         height < img.getHeight()) height = img.getHeight();
/*     */     
/*     */     } 
/*     */     
/* 236 */     return height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(GDItemSetInfo info) {
/* 244 */     if (!this.itemSetRarity.equals(info.itemSetRarity)) {
/* 245 */       return this.itemSetRarity.equals("Legendary") ? 1 : -1;
/*     */     }
/*     */     
/* 248 */     int i = 0;
/*     */     
/* 250 */     i = getItemSetName().compareTo(info.getItemSetName());
/*     */     
/* 252 */     if (i != 0) return i;
/*     */     
/* 254 */     i = this.itemSetLevel - info.itemSetLevel;
/*     */     
/* 256 */     if (i != 0) return i;
/*     */     
/* 258 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\table\GDItemSetInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */