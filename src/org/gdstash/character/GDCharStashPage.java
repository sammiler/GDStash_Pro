/*     */ package org.gdstash.character;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.gdstash.file.GDReader;
/*     */ import org.gdstash.file.GDWriter;
/*     */ import org.gdstash.item.GDAbstractContainer;
/*     */ import org.gdstash.item.GDItem;
/*     */ import org.gdstash.util.FileVersionException;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDCharStashPage
/*     */   extends GDAbstractContainer
/*     */ {
/*     */   private static final int BLOCK = 0;
/*     */   private GDChar gdc;
/*     */   private int stashVersion;
/*     */   
/*     */   public GDCharStashPage(GDChar gdc, int stashVersion) {
/*  25 */     super(3);
/*     */     
/*  27 */     this.gdc = gdc;
/*  28 */     this.stashVersion = stashVersion;
/*     */     
/*  30 */     this.changed = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  38 */     int h = getContainerHeight();
/*     */     
/*  40 */     for (GDItem item : this.items) {
/*  41 */       int y = item.getY() + item.getImage().getHeight() / 32;
/*     */       
/*  43 */       if (h < y) h = y;
/*     */     
/*     */     } 
/*  46 */     return h;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/*  50 */     int w = getContainerWidth();
/*     */     
/*  52 */     for (GDItem item : this.items) {
/*  53 */       int x = item.getX() + item.getImage().getWidth() / 32;
/*     */       
/*  55 */       if (w < x) w = x;
/*     */     
/*     */     } 
/*  58 */     return w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read() throws IOException {
/*  66 */     int val = 0;
/*     */     
/*  68 */     GDReader.Block block = new GDReader.Block();
/*  69 */     if (this.stashVersion >= 6) {
/*  70 */       val = GDReader.readBlockStart(block);
/*  71 */       if (val != 0) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/*     */     } 
/*  74 */     this.width = GDReader.readEncInt(true);
/*  75 */     this.height = GDReader.readEncInt(true);
/*     */     
/*  77 */     this.items.clear();
/*  78 */     this.removedItems.clear();
/*  79 */     val = GDReader.readEncInt(true);
/*  80 */     for (int i = 0; i < val; i++) {
/*  81 */       GDItem item = new GDItem(this.gdc.getCharName(), this.gdc.isHardcore(), 3);
/*  82 */       item.read();
/*     */       
/*  84 */       if (!item.hasErrors()) {
/*  85 */         this.items.add(item);
/*     */       } else {
/*  87 */         this.removedItems.add(item);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  92 */     if (this.stashVersion >= 6) {
/*  93 */       GDReader.readBlockEnd(block);
/*     */     }
/*     */     
/*  96 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/* 100 */     GDReader.Block block = new GDReader.Block();
/*     */     
/* 102 */     if (this.stashVersion >= 6) {
/* 103 */       GDWriter.writeBlockStart(block, 0);
/*     */     }
/*     */     
/* 106 */     GDWriter.writeInt(this.width);
/* 107 */     GDWriter.writeInt(this.height);
/*     */     
/* 109 */     int val = this.items.size() + this.removedItems.size();
/* 110 */     GDWriter.writeInt(val);
/*     */     
/* 112 */     for (GDItem item : this.items) {
/* 113 */       item.write();
/*     */     }
/*     */     
/* 116 */     for (GDItem item : this.removedItems) {
/* 117 */       item.write();
/*     */     }
/*     */ 
/*     */     
/* 121 */     if (this.stashVersion >= 6) {
/* 122 */       GDWriter.writeBlockEnd(block);
/*     */     }
/*     */     
/* 125 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/* 129 */     int size = 0;
/*     */     
/* 131 */     if (this.stashVersion >= 6) {
/* 132 */       size += 4;
/* 133 */       size += 4;
/*     */     } 
/*     */     
/* 136 */     size += 4;
/* 137 */     size += 4;
/*     */     
/* 139 */     size += 4;
/* 140 */     for (GDItem item : this.items) {
/* 141 */       size += item.getByteSize();
/*     */     }
/*     */     
/* 144 */     for (GDItem item : this.removedItems) {
/* 145 */       size += item.getByteSize();
/*     */     }
/*     */     
/* 148 */     if (this.stashVersion >= 6) {
/* 149 */       size += 4;
/*     */     }
/*     */     
/* 152 */     return size;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\character\GDCharStashPage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */