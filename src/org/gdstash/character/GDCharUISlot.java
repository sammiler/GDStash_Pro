/*     */ package org.gdstash.character;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.gdstash.file.GDReader;
/*     */ import org.gdstash.file.GDWriter;
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
/*     */ public class GDCharUISlot
/*     */ {
/*     */   public static final int TYPE_NONE = -1;
/*     */   public static final int TYPE_SKILL = 0;
/*     */   public static final int TYPE_ITEM = 4;
/*     */   private int type;
/*     */   private String skill;
/*     */   private byte isItemSkill;
/*     */   private String item;
/*     */   private int equipLocation;
/*     */   private String bitmapUp;
/*     */   private String bitmapDown;
/*     */   private String label;
/*     */   
/*     */   public void read() throws IOException {
/*  33 */     this.type = GDReader.readEncInt(true);
/*     */     
/*  35 */     if (this.type == 0) {
/*  36 */       this.skill = GDReader.readEncString();
/*  37 */       this.isItemSkill = GDReader.readEncByte();
/*  38 */       this.item = GDReader.readEncString();
/*  39 */       this.equipLocation = GDReader.readEncInt(true);
/*     */     
/*     */     }
/*  42 */     else if (this.type == 4) {
/*  43 */       this.item = GDReader.readEncString();
/*  44 */       this.bitmapUp = GDReader.readEncString();
/*  45 */       this.bitmapDown = GDReader.readEncString();
/*  46 */       this.label = GDReader.readEncWideString();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write() throws IOException {
/*  52 */     GDWriter.writeInt(this.type);
/*     */     
/*  54 */     if (this.type == 0) {
/*  55 */       GDWriter.writeString(this.skill);
/*  56 */       GDWriter.writeByte(this.isItemSkill);
/*  57 */       GDWriter.writeString(this.item);
/*  58 */       GDWriter.writeInt(this.equipLocation);
/*     */     
/*     */     }
/*  61 */     else if (this.type == 4) {
/*  62 */       GDWriter.writeString(this.item);
/*  63 */       GDWriter.writeString(this.bitmapUp);
/*  64 */       GDWriter.writeString(this.bitmapDown);
/*  65 */       GDWriter.writeWideString(this.label);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getByteSize() {
/*  71 */     int size = 0;
/*     */     
/*  73 */     size += 4;
/*     */     
/*  75 */     if (this.type == 0) {
/*  76 */       size += 4;
/*  77 */       if (this.skill != null) size += this.skill.length();
/*     */       
/*  79 */       size++;
/*     */       
/*  81 */       size += 4;
/*  82 */       if (this.item != null) size += this.item.length();
/*     */       
/*  84 */       size += 4;
/*     */     
/*     */     }
/*  87 */     else if (this.type == 4) {
/*  88 */       size += 4;
/*  89 */       if (this.item != null) size += this.item.length();
/*     */       
/*  91 */       size += 4;
/*  92 */       if (this.bitmapUp != null) size += this.bitmapUp.length();
/*     */       
/*  94 */       size += 4;
/*  95 */       if (this.bitmapDown != null) size += this.bitmapDown.length();
/*     */       
/*  97 */       size += 4;
/*  98 */       if (this.label != null) size += this.label.length() * 2;
/*     */     
/*     */     } 
/*     */     
/* 102 */     return size;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\character\GDCharUISlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */