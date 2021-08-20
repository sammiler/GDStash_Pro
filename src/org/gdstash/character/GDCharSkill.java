/*     */ package org.gdstash.character;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.gdstash.db.DBSkill;
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
/*     */ public class GDCharSkill
/*     */ {
/*     */   private String name;
/*     */   private String autoCastSkill;
/*     */   private String autoCastController;
/*     */   private int level;
/*     */   private int devotionLevel;
/*     */   private int experience;
/*     */   private int active;
/*     */   private byte enabled;
/*     */   private byte unknown1;
/*     */   private byte unknown2;
/*     */   private DBSkill dbSkill;
/*     */   
/*     */   public GDCharSkill() {}
/*     */   
/*     */   public GDCharSkill(String skillID, int level) {
/*  35 */     this.name = skillID;
/*  36 */     this.level = level;
/*  37 */     this.enabled = 1;
/*  38 */     this.devotionLevel = 0;
/*  39 */     this.experience = 0;
/*  40 */     this.active = 0;
/*  41 */     this.unknown1 = 0;
/*  42 */     this.unknown2 = 0;
/*  43 */     this.autoCastSkill = null;
/*  44 */     this.autoCastController = null;
/*     */     
/*  46 */     if (this.name != null) this.dbSkill = DBSkill.get(this.name);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getID() {
/*  54 */     return this.name;
/*     */   }
/*     */   
/*     */   public int getLevel() {
/*  58 */     if (isDevotion()) {
/*  59 */       if (this.level == 0) return 0;
/*     */       
/*  61 */       return this.devotionLevel;
/*     */     } 
/*  63 */     return this.level;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSkillLevel() {
/*  68 */     return this.level;
/*     */   }
/*     */   
/*     */   public int getDevotionLevel() {
/*  72 */     return this.devotionLevel;
/*     */   }
/*     */   
/*     */   public int getMaxLevel() {
/*  76 */     if (this.dbSkill == null) return 0;
/*     */     
/*  78 */     return this.dbSkill.getMaxLevel();
/*     */   }
/*     */   
/*     */   public boolean isMastery() {
/*  82 */     if (this.dbSkill == null) return false;
/*     */     
/*  84 */     return this.dbSkill.isMastery();
/*     */   }
/*     */   
/*     */   public boolean isDevotion() {
/*  88 */     if (this.dbSkill == null) return false;
/*     */     
/*  90 */     return this.dbSkill.isDevotion();
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/*  94 */     return (this.enabled == 1);
/*     */   }
/*     */   
/*     */   public String getMasteryID() {
/*  98 */     if (this.dbSkill == null) return null;
/*     */     
/* 100 */     return this.dbSkill.getMasteryID();
/*     */   }
/*     */   
/*     */   public String getSkillName() {
/* 104 */     if (this.dbSkill == null) return null;
/*     */     
/* 106 */     return this.dbSkill.getName();
/*     */   }
/*     */   
/*     */   public String getAutoCastSkill() {
/* 110 */     return this.autoCastSkill;
/*     */   }
/*     */   
/*     */   public String getAutoCastController() {
/* 114 */     return this.autoCastController;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLevel(int level) {
/* 122 */     if (isDevotion()) {
/* 123 */       if (this.devotionLevel == level)
/*     */         return; 
/* 125 */       if (level > this.dbSkill.getMaxLevel()) {
/* 126 */         this.devotionLevel = this.dbSkill.getMaxLevel();
/*     */       } else {
/* 128 */         this.devotionLevel = level;
/*     */       } 
/*     */       
/* 131 */       if (this.devotionLevel > 0) {
/* 132 */         this.level = 1;
/*     */       } else {
/* 134 */         this.level = 0;
/*     */       } 
/*     */       
/* 137 */       this.experience = this.dbSkill.getXPForLevel(this.devotionLevel);
/*     */     } else {
/* 139 */       this.level = level;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setAutoCastSkill(String autoCastSkill) {
/* 144 */     this.autoCastSkill = autoCastSkill;
/*     */   }
/*     */   
/*     */   public void setAutoCastController(String autoCastController) {
/* 148 */     this.autoCastController = autoCastController;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read() throws IOException {
/* 156 */     this.name = GDReader.readEncString();
/* 157 */     this.level = GDReader.readEncInt(true);
/* 158 */     this.enabled = GDReader.readEncByte();
/* 159 */     this.devotionLevel = GDReader.readEncInt(true);
/* 160 */     this.experience = GDReader.readEncInt(true);
/* 161 */     this.active = GDReader.readEncInt(true);
/* 162 */     this.unknown1 = GDReader.readEncByte();
/* 163 */     this.unknown2 = GDReader.readEncByte();
/* 164 */     this.autoCastSkill = GDReader.readEncString();
/* 165 */     this.autoCastController = GDReader.readEncString();
/*     */     
/* 167 */     if (this.name != null) this.dbSkill = DBSkill.get(this.name); 
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/* 171 */     GDWriter.writeString(this.name);
/* 172 */     GDWriter.writeInt(this.level);
/* 173 */     GDWriter.writeByte(this.enabled);
/* 174 */     GDWriter.writeInt(this.devotionLevel);
/* 175 */     GDWriter.writeInt(this.experience);
/* 176 */     GDWriter.writeInt(this.active);
/* 177 */     GDWriter.writeByte(this.unknown1);
/* 178 */     GDWriter.writeByte(this.unknown2);
/* 179 */     GDWriter.writeString(this.autoCastSkill);
/* 180 */     GDWriter.writeString(this.autoCastController);
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/* 184 */     int size = 0;
/*     */     
/* 186 */     size += 4;
/* 187 */     if (this.name != null) size += this.name.length();
/*     */     
/* 189 */     size += 4;
/* 190 */     size++;
/* 191 */     size += 4;
/* 192 */     size += 4;
/* 193 */     size += 4;
/* 194 */     size++;
/* 195 */     size++;
/*     */     
/* 197 */     size += 4;
/* 198 */     if (this.autoCastSkill != null) size += this.autoCastSkill.length();
/*     */     
/* 200 */     size += 4;
/* 201 */     if (this.autoCastController != null) size += this.autoCastController.length();
/*     */     
/* 203 */     return size;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\character\GDCharSkill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */