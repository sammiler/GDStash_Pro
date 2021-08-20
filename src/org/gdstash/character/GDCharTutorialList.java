/*     */ package org.gdstash.character;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDCharTutorialList
/*     */ {
/*     */   private static final int VERSION = 1;
/*     */   private static final int BLOCK = 15;
/*     */   public static final int TUTORIAL_DEATH = 23;
/*     */   private int version;
/*  30 */   private List<Integer> pages = new LinkedList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTutorial(int page) {
/*  38 */     boolean found = false;
/*     */     
/*  40 */     for (Iterator<Integer> iterator = this.pages.iterator(); iterator.hasNext(); ) { int i = ((Integer)iterator.next()).intValue();
/*  41 */       if (i == page) {
/*  42 */         found = true;
/*     */         
/*     */         break;
/*     */       }  }
/*     */ 
/*     */     
/*  48 */     if (!found) this.pages.add(Integer.valueOf(page)); 
/*     */   }
/*     */   
/*     */   public void removeTutorial(int page) {
/*  52 */     Iterator<Integer> iter = this.pages.iterator();
/*  53 */     while (iter.hasNext()) {
/*  54 */       int i = ((Integer)iter.next()).intValue();
/*     */       
/*  56 */       if (i == page) {
/*  57 */         iter.remove();
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read() throws IOException {
/*  69 */     int val = 0;
/*     */     
/*  71 */     GDReader.Block block = new GDReader.Block();
/*     */     
/*  73 */     val = GDReader.readBlockStart(block);
/*  74 */     if (val != 15) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/*  76 */     this.version = GDReader.readEncInt(true);
/*  77 */     if (this.version != 1) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/*  79 */     this.pages.clear();
/*  80 */     val = GDReader.readEncInt(true);
/*  81 */     for (int i = 0; i < val; i++) {
/*  82 */       int page = GDReader.readEncInt(true);
/*     */       
/*  84 */       this.pages.add(Integer.valueOf(page));
/*     */     } 
/*     */ 
/*     */     
/*  88 */     GDReader.readBlockEnd(block);
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/*  92 */     GDReader.Block block = new GDReader.Block();
/*  93 */     GDWriter.writeBlockStart(block, 15);
/*     */     
/*  95 */     GDWriter.writeInt(this.version);
/*     */     
/*  97 */     int val = this.pages.size();
/*  98 */     GDWriter.writeInt(val);
/*     */     
/* 100 */     for (Iterator<Integer> iterator = this.pages.iterator(); iterator.hasNext(); ) { int page = ((Integer)iterator.next()).intValue();
/* 101 */       GDWriter.writeInt(page); }
/*     */ 
/*     */ 
/*     */     
/* 105 */     GDWriter.writeBlockEnd(block);
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/* 109 */     int size = 0;
/*     */     
/* 111 */     size += 4;
/* 112 */     size += 4;
/* 113 */     size += 4;
/* 114 */     size += 4;
/* 115 */     size += 4 * this.pages.size();
/* 116 */     size += 4;
/*     */     
/* 118 */     return size;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\character\GDCharTutorialList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */