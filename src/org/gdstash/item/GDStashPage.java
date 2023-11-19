/*     */ package org.gdstash.item;
/*     */ 
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import org.gdstash.file.GDReader;
/*     */ import org.gdstash.file.GDWriter;
/*     */ import org.gdstash.util.GDLog;
/*     */ import org.gdstash.util.GDMsgLogger;
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
/*     */ public class GDStashPage
/*     */   extends GDAbstractContainer
/*     */ {
/*     */   private InputStream reader;
/*     */   private String filename;
/*     */   private int stashType;
/*     */   private boolean itemError;
/*     */   private boolean pageError;
/*     */   
/*     */   public GDStashPage(InputStream reader, int stashType, int version, int w, boolean hardcore, String filename, int page, GDLog log) {
/*  32 */     super(1);
/*     */     
/*  34 */     this.itemError = false;
/*  35 */     this.pageError = false;
/*  36 */     this.reader = reader;
/*  37 */     this.filename = filename;
/*  38 */     this.stashType = stashType;
/*     */     
/*  40 */     this.width = w;
/*     */     
/*  42 */     if (stashType == 0) {
/*  43 */       readOldFormat(hardcore, page + 1, version);
/*     */     }
/*     */     
/*  46 */     if (stashType == 1) {
/*  47 */       readNewFormat(hardcore, page + 1, version, log);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasItemErrors() {
/*  52 */     return this.itemError;
/*     */   }
/*     */   
/*     */   public boolean hasPageErrors() {
/*  56 */     return this.pageError;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readNewFormat(boolean hardcore, int page, int version, GDLog log) {
/*     */     try {
/*  66 */       int val = 0;
/*     */       
/*  68 */       GDReader.Block block = new GDReader.Block();
/*     */       
/*  70 */       val = GDReader.readBlockStart(block);
/*     */       
/*  72 */       this.width = GDReader.readEncInt(true);
/*  73 */       this.height = GDReader.readEncInt(true);
/*  74 */       int numItems = GDReader.readEncInt(true);
/*     */       
/*  76 */       this.items.clear();
/*  77 */       this.removedItems.clear(); int i;
/*  78 */       for (i = 0; i < numItems; i++) {
/*  79 */         GDItem item = new GDItem(this.reader, this.stashType, version, hardcore, this.filename, page, log);
/*     */         
/*  81 */         if (!item.hasErrors()) {
/*  82 */           this.items.add(item);
/*     */         } else {
/*  84 */           this.removedItems.add(item);
/*     */           
/*  86 */           this.itemError = true;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  94 */       GDReader.readBlockEnd(block);
/*     */       
/*  96 */       this.changed = false;
/*     */     }
/*  98 */     catch (Exception ex) {
/*  99 */       this.pageError = true;
/*     */       
/* 101 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readOldFormat(boolean hardcore, int page, int version) {
/*     */     try {
/* 107 */       int numItems = 0;
/* 108 */       int len = 0;
/* 109 */       while (len != -1) {
/* 110 */         len = GDReader.readUInt(this.reader);
/*     */         
/* 112 */         if (len != -1) {
/* 113 */           String s = GDReader.readString(this.reader, len);
/*     */           
/* 115 */           if (s.equals("sackWidth")) {
/* 116 */             this.width = GDReader.readInt(this.reader);
/*     */           }
/* 118 */           if (s.equals("sackHeight")) {
/* 119 */             this.height = GDReader.readInt(this.reader);
/*     */           }
/* 121 */           if (s.equals("numItems")) {
/* 122 */             numItems = GDReader.readUInt(this.reader);
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 130 */       this.items.clear(); int i;
/* 131 */       for (i = 0; i < numItems; i++) {
/* 132 */         GDItem item = new GDItem(this.reader, this.stashType, version, hardcore, this.filename, page, null);
/*     */         
/* 134 */         if (!item.hasErrors()) {
/* 135 */           this.items.add(item);
/*     */         } else {
/* 137 */           this.itemError = true;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 144 */       this.removedItems.clear();
/*     */       
/* 146 */       this.changed = false;
/*     */     }
/* 148 */     catch (IOException ex) {
/* 149 */       this.pageError = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/* 154 */     int size = 0;
/*     */     
/* 156 */     size += 4;
/* 157 */     size += 4;
/* 158 */     size += 4;
/*     */     
/* 160 */     for (GDItem item : this.items) {
/* 161 */       size += item.getByteSize();
/*     */     }
/*     */     
/* 164 */     for (GDItem item : this.removedItems) {
/* 165 */       size += item.getByteSize();
/*     */     }
/*     */     
/* 168 */     return size;
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/* 172 */     GDReader.Block block = new GDReader.Block();
/* 173 */     GDWriter.writeBlockStart(block, 0);
/*     */     
/* 175 */     GDWriter.writeInt(this.width);
/* 176 */     GDWriter.writeInt(this.height);
/*     */     
/* 178 */     int val = this.items.size() + this.removedItems.size();
/* 179 */     GDWriter.writeInt(val);
/*     */     
/* 181 */     for (GDItem item : this.items) {
/* 182 */       item.write();
/*     */     }
/*     */     
/* 185 */     for (GDItem item : this.removedItems) {
/* 186 */       item.write();
/*     */     }
/*     */ 
/*     */     
/* 190 */     GDWriter.writeBlockEnd(block);
/*     */     
/* 192 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public void writeOldFormat(FileOutputStream writer, Charset cs) throws IOException {
/* 196 */     writer.write(GDWriter.lengthToBytes4("sackWidth"));
/* 197 */     writer.write("sackWidth".getBytes(cs));
/* 198 */     writer.write(GDWriter.intToBytes4(this.width));
/*     */     
/* 200 */     writer.write(GDWriter.lengthToBytes4("sackHeight"));
/* 201 */     writer.write("sackHeight".getBytes(cs));
/* 202 */     writer.write(GDWriter.intToBytes4(this.height));
/*     */     
/* 204 */     writer.write(GDWriter.lengthToBytes4("numItems"));
/* 205 */     writer.write("numItems".getBytes(cs));
/*     */     
/* 207 */     int val = this.items.size() + this.removedItems.size();
/* 208 */     GDWriter.writeInt(val);
/* 209 */     writer.write(GDWriter.intToBytes4(val));
/*     */     
/* 211 */     for (GDItem item : this.items) {
/* 212 */       item.writeOldFormat(writer, cs);
/*     */     }
/*     */     
/* 215 */     for (GDItem item : this.removedItems) {
/* 216 */       item.writeOldFormat(writer, cs);
/*     */     }
/*     */     
/* 219 */     this.changed = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\item\GDStashPage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */