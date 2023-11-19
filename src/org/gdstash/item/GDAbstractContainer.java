/*     */ package org.gdstash.item;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ import org.gdstash.util.GDMsgLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GDAbstractContainer
/*     */   implements GDItemContainer
/*     */ {
/*     */   protected List<GDItem> items;
/*     */   protected List<GDItem> removedItems;
/*     */   protected boolean changed;
/*     */   protected int containerType;
/*     */   protected int width;
/*     */   protected int height;
/*     */   
/*     */   public GDAbstractContainer(int containerType) {
/*  25 */     this.containerType = containerType;
/*     */     
/*  27 */     this.items = new LinkedList<>();
/*  28 */     this.removedItems = new LinkedList<>();
/*  29 */     this.changed = false;
/*  30 */     this.width = -1;
/*  31 */     this.height = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<GDItem> getItemList() {
/*  40 */     List<GDItem> list = new LinkedList<>();
/*     */     
/*  42 */     for (GDItem item : this.items) {
/*  43 */       list.add(item);
/*     */     }
/*     */     
/*  46 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDItem> getRemovedItemList() {
/*  51 */     List<GDItem> list = new LinkedList<>();
/*     */     
/*  53 */     for (GDItem item : this.removedItems) {
/*  54 */       list.add(item);
/*     */     }
/*     */     
/*  57 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addItem(GDItem item) {
/*  62 */     item.setContainerType(this.containerType);
/*  63 */     this.items.add(item);
/*     */     
/*  65 */     this.changed = true;
/*     */     
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeItem(GDItem item) {
/*  72 */     boolean found = false;
/*     */     
/*  74 */     if (this.items != null) {
/*  75 */       Iterator<GDItem> iter = this.items.iterator();
/*  76 */       while (iter.hasNext()) {
/*  77 */         GDItem ti = iter.next();
/*     */         
/*  79 */         if (ti == item) {
/*  80 */           iter.remove();
/*     */           
/*  82 */           found = true;
/*     */           
/*  84 */           this.changed = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  91 */     return found;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/*  96 */     return this.changed;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerHeight() {
/* 101 */     if (this.height > 0) return this.height;
/*     */     
/* 103 */     int h = 0;
/*     */     
/* 105 */     switch (this.containerType)
/*     */     { case 1:
/* 107 */         h = 16;
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
/* 127 */         return h;case 3: h = 18; return h;case 4: h = 8; return h;case 5: h = 8; return h; }  h = 16; return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerWidth() {
/* 132 */     if (this.width > 0) return this.width;
/*     */     
/* 134 */     int w = 0;
/*     */     
/* 136 */     switch (this.containerType)
/*     */     { case 1:
/* 138 */         w = 8;
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
/* 158 */         return w;case 3: w = 10; return w;case 4: w = 12; return w;case 5: w = 8; return w; }  w = 8; return w;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerType() {
/* 163 */     return this.containerType;
/*     */   }
/*     */   
/*     */   public void setContainerType(int containerType) {
/* 167 */     this.containerType = containerType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh() {
/* 172 */     List<GDItem> temp = new LinkedList<>();
/*     */     
/* 174 */     Iterator<GDItem> iter = this.items.iterator();
/* 175 */     while (iter.hasNext()) {
/* 176 */       GDItem item = iter.next();
/*     */       
/* 178 */       item.refresh();
/*     */       
/* 180 */       if (item.hasErrors()) {
/*     */         try {
/* 182 */           iter.remove();
/*     */         }
/* 184 */         catch (UnsupportedOperationException unsupportedOperationException) {}
/*     */         
/* 186 */         temp.add(item);
/*     */       } 
/*     */     } 
/*     */     
/* 190 */     iter = this.removedItems.iterator();
/* 191 */     while (iter.hasNext()) {
/* 192 */       GDItem item = iter.next();
/*     */       
/* 194 */       item.refresh();
/*     */       
/* 196 */       if (!item.hasErrors()) {
/*     */         try {
/* 198 */           iter.remove();
/*     */           
/* 200 */           this.items.add(item);
/*     */         }
/* 202 */         catch (UnsupportedOperationException unsupportedOperationException) {}
/*     */       }
/*     */     } 
/*     */     
/* 206 */     if (!temp.isEmpty()) {
/* 207 */       iter = temp.iterator();
/* 208 */       while (iter.hasNext()) {
/* 209 */         GDItem item = iter.next();
/*     */         
/* 211 */         this.removedItems.add(item);
/*     */       } 
/*     */     } 
/*     */     
/* 215 */     if (!this.removedItems.isEmpty())
/* 216 */       GDMsgLogger.addWarning(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_STASH_ITEMS_REMOVED")); 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\item\GDAbstractContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */