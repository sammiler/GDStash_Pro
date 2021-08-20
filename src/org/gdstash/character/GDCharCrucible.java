/*     */ package org.gdstash.character;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.file.GDReader;
/*     */ import org.gdstash.file.GDWriter;
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
/*     */ 
/*     */ public class GDCharCrucible
/*     */ {
/*     */   private static final int VERSION = 2;
/*     */   private static final int BLOCK = 10;
/*     */   private int version;
/*     */   private ArrayList<List<String>> tokensPerDifficulty;
/*     */   
/*     */   public GDCharCrucible() {
/*  28 */     int size = 3;
/*     */     
/*  30 */     this.tokensPerDifficulty = new ArrayList<>(size);
/*  31 */     for (int i = 0; i < size; i++) {
/*  32 */       this.tokensPerDifficulty.add(new LinkedList<>());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read() throws IOException {
/*  41 */     int val = 0;
/*     */     
/*  43 */     GDReader.Block block = new GDReader.Block();
/*     */     
/*  45 */     val = GDReader.readBlockStart(block);
/*  46 */     if (val != 10) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/*  48 */     this.version = GDReader.readEncInt(true);
/*  49 */     if (this.version != 2) {
/*  50 */       throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     }
/*     */     
/*  53 */     for (int i = 0; i < this.tokensPerDifficulty.size(); i++) {
/*  54 */       ((List)this.tokensPerDifficulty.get(i)).clear();
/*     */       
/*  56 */       int num = GDReader.readEncInt(true);
/*     */       int j;
/*  58 */       for (j = 0; j < num; j++) {
/*  59 */         String s = GDReader.readEncString();
/*     */         
/*  61 */         if (!((List)this.tokensPerDifficulty.get(i)).contains(s)) ((List<String>)this.tokensPerDifficulty.get(i)).add(s);
/*     */       
/*     */       } 
/*     */     } 
/*     */     
/*  66 */     GDReader.readBlockEnd(block);
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/*  70 */     GDReader.Block block = new GDReader.Block();
/*  71 */     GDWriter.writeBlockStart(block, 10);
/*     */     
/*  73 */     GDWriter.writeInt(this.version);
/*     */     
/*  75 */     for (int i = 0; i < this.tokensPerDifficulty.size(); i++) {
/*  76 */       GDWriter.writeInt(((List)this.tokensPerDifficulty.get(i)).size());
/*     */       
/*  78 */       for (String s : this.tokensPerDifficulty.get(i)) {
/*  79 */         GDWriter.writeString(s);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  84 */     GDWriter.writeBlockEnd(block);
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/*  88 */     int size = 0;
/*  89 */     int val = 0;
/*     */     
/*  91 */     size += 4;
/*  92 */     size += 4;
/*  93 */     size += 4;
/*     */     
/*  95 */     for (int i = 0; i < this.tokensPerDifficulty.size(); i++) {
/*  96 */       size += 4;
/*     */       
/*  98 */       for (String s : this.tokensPerDifficulty.get(i)) {
/*  99 */         size += 4;
/* 100 */         if (s != null) size += s.length();
/*     */       
/*     */       } 
/*     */     } 
/* 104 */     size += 4;
/*     */     
/* 106 */     return size;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\character\GDCharCrucible.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */