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
/*     */ 
/*     */ public class GDCharInventorySack
/*     */   extends GDAbstractContainer
/*     */ {
/*     */   private static final int BLOCK = 0;
/*     */   private byte tempBool;
/*     */   private GDChar gdc;
/*     */   private int sackPos;
/*     */   
/*     */   public GDCharInventorySack(GDChar gdc, int sackPos) {
/*  27 */     super(5);
/*     */     
/*  29 */     this.gdc = gdc;
/*  30 */     this.sackPos = sackPos;
/*     */     
/*  32 */     if (sackPos == 0) this.containerType = 4;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read() throws IOException {
/*  40 */     int val = 0;
/*     */     
/*  42 */     GDReader.Block block = new GDReader.Block();
/*     */     
/*  44 */     val = GDReader.readBlockStart(block);
/*  45 */     if (val != 0) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/*  47 */     this.tempBool = GDReader.readEncByte();
/*     */     
/*  49 */     this.items.clear();
/*  50 */     this.removedItems.clear();
/*  51 */     val = GDReader.readEncInt(true);
/*  52 */     for (int i = 0; i < val; i++) {
/*  53 */       GDItem item = new GDItem(this.gdc.getCharName(), this.gdc.isHardcore(), 4);
/*  54 */       item.read();
/*     */       
/*  56 */       if (!item.hasErrors()) {
/*  57 */         this.items.add(item);
/*     */       } else {
/*  59 */         this.removedItems.add(item);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  64 */     GDReader.readBlockEnd(block);
/*     */     
/*  66 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/*  70 */     GDReader.Block block = new GDReader.Block();
/*  71 */     GDWriter.writeBlockStart(block, 0);
/*     */     
/*  73 */     GDWriter.writeByte(this.tempBool);
/*     */     
/*  75 */     int val = this.items.size() + this.removedItems.size();
/*  76 */     GDWriter.writeInt(val);
/*     */     
/*  78 */     for (GDItem item : this.items) {
/*  79 */       item.write();
/*     */     }
/*     */     
/*  82 */     for (GDItem item : this.removedItems) {
/*  83 */       item.write();
/*     */     }
/*     */ 
/*     */     
/*  87 */     GDWriter.writeBlockEnd(block);
/*     */     
/*  89 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/*  93 */     int size = 0;
/*     */     
/*  95 */     size += 4;
/*  96 */     size += 4;
/*  97 */     size++;
/*     */     
/*  99 */     size += 4;
/* 100 */     for (GDItem item : this.items) {
/* 101 */       size += item.getByteSize();
/*     */     }
/*     */     
/* 104 */     for (GDItem item : this.removedItems) {
/* 105 */       size += item.getByteSize();
/*     */     }
/*     */     
/* 108 */     size += 4;
/*     */     
/* 110 */     return size;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\character\GDCharInventorySack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */