/*     */ package org.gdstash.character;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ public class GDCharHeader
/*     */ {
/*     */   private static final int VERSION_1 = 1;
/*     */   private static final int VERSION_2 = 2;
/*     */   private int version;
/*     */   private String charName;
/*     */   private byte sex;
/*     */   private String classID;
/*     */   private int level;
/*     */   private byte hardcore;
/*     */   private byte expansionStatus;
/*     */   private boolean changed;
/*     */   
/*     */   public String getCharName() {
/*  35 */     return this.charName;
/*     */   }
/*     */   
/*     */   public byte getSex() {
/*  39 */     return this.sex;
/*     */   }
/*     */   
/*     */   public String getClassID() {
/*  43 */     return this.classID;
/*     */   }
/*     */   
/*     */   public int getLevel() {
/*  47 */     return this.level;
/*     */   }
/*     */   
/*     */   public boolean isHardcore() {
/*  51 */     return (this.hardcore == 1);
/*     */   }
/*     */   
/*     */   public boolean isAsheshOfMalmouthChar() {
/*  55 */     return (this.expansionStatus == 1);
/*     */   }
/*     */   
/*     */   public boolean isForgottenGodsChar() {
/*  59 */     return (this.expansionStatus == 3);
/*     */   }
/*     */   
/*     */   public boolean hasChanged() {
/*  63 */     return this.changed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCharName(String charName) {
/*  71 */     this.charName = charName;
/*     */     
/*  73 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setSex(byte sex) {
/*  77 */     if (sex == 0) {
/*  78 */       this.sex = 0;
/*     */     } else {
/*  80 */       this.sex = 1;
/*     */     } 
/*     */     
/*  83 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setLevel(int level) {
/*  87 */     this.level = level;
/*     */     
/*  89 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setHardcore(boolean hardcore) {
/*  93 */     if (hardcore) {
/*  94 */       this.hardcore = 1;
/*     */     } else {
/*  96 */       this.hardcore = 0;
/*     */     } 
/*     */     
/*  99 */     this.changed = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read() throws IOException {
/* 107 */     this.version = GDReader.readEncInt(true);
/* 108 */     if (this.version != 1 && this.version != 2)
/*     */     {
/* 110 */       throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     }
/*     */     
/* 113 */     this.charName = GDReader.readEncWideString();
/* 114 */     this.sex = GDReader.readEncByte();
/* 115 */     this.classID = GDReader.readEncString();
/* 116 */     this.level = GDReader.readEncInt(true);
/* 117 */     this.hardcore = GDReader.readEncByte();
/*     */     
/* 119 */     if (this.version >= 2) {
/* 120 */       this.expansionStatus = GDReader.readEncByte();
/*     */     }
/*     */     
/* 123 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/* 127 */     GDWriter.writeInt(this.version);
/*     */     
/* 129 */     GDWriter.writeWideString(this.charName);
/* 130 */     GDWriter.writeByte(this.sex);
/* 131 */     GDWriter.writeString(this.classID);
/* 132 */     GDWriter.writeInt(this.level);
/* 133 */     GDWriter.writeByte(this.hardcore);
/*     */     
/* 135 */     if (this.version >= 2) {
/* 136 */       GDWriter.writeByte(this.expansionStatus);
/*     */     }
/*     */     
/* 139 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/* 143 */     int size = 0;
/*     */     
/* 145 */     size += 4;
/* 146 */     size += 4;
/* 147 */     if (this.charName != null) size += this.charName.length() * 2;
/*     */     
/* 149 */     size++;
/*     */     
/* 151 */     size += 4;
/* 152 */     if (this.classID != null) size += this.classID.length();
/*     */     
/* 154 */     size += 4;
/* 155 */     size++;
/*     */     
/* 157 */     if (this.version >= 2) {
/* 158 */       size++;
/*     */     }
/*     */     
/* 161 */     return size;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\character\GDCharHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */