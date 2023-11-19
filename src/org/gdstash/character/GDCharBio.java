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
/*     */ 
/*     */ public class GDCharBio
/*     */ {
/*     */   private static final int VERSION = 8;
/*     */   private static final int BLOCK = 2;
/*     */   private int version;
/*     */   private int level;
/*     */   private int experience;
/*     */   private int modifierPoints;
/*     */   private int skillPoints;
/*     */   private int devotionPoints;
/*     */   private int totalDevotion;
/*     */   private float physique;
/*     */   private float cunning;
/*     */   private float spirit;
/*     */   private float health;
/*     */   private float energy;
/*     */   private boolean changed;
/*     */   
/*     */   public int getLevel() {
/*  41 */     return this.level;
/*     */   }
/*     */   
/*     */   public int getExperience() {
/*  45 */     return this.experience;
/*     */   }
/*     */   
/*     */   public int getStatPoints() {
/*  49 */     return this.modifierPoints;
/*     */   }
/*     */   
/*     */   public int getSkillPoints() {
/*  53 */     return this.skillPoints;
/*     */   }
/*     */   
/*     */   public int getDevotionPoints() {
/*  57 */     return this.devotionPoints;
/*     */   }
/*     */   
/*     */   public float getPhysique() {
/*  61 */     return this.physique;
/*     */   }
/*     */   
/*     */   public float getCunning() {
/*  65 */     return this.cunning;
/*     */   }
/*     */   
/*     */   public float getSpirit() {
/*  69 */     return this.spirit;
/*     */   }
/*     */   
/*     */   public float getHealth() {
/*  73 */     return this.health;
/*     */   }
/*     */   
/*     */   public float getEnergy() {
/*  77 */     return this.energy;
/*     */   }
/*     */   
/*     */   public boolean hasChanged() {
/*  81 */     return this.changed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLevel(int level) {
/*  89 */     this.level = level;
/*     */     
/*  91 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setExperience(int experience) {
/*  95 */     this.experience = experience;
/*     */     
/*  97 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setStatPoints(int modifierPoints) {
/* 101 */     this.modifierPoints = modifierPoints;
/*     */     
/* 103 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setSkillPoints(int skillPoints) {
/* 107 */     this.skillPoints = skillPoints;
/*     */     
/* 109 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setDevotionPoints(int devotionPoints) {
/* 113 */     this.totalDevotion = this.totalDevotion + devotionPoints - this.devotionPoints;
/*     */ 
/*     */     
/* 116 */     if (this.totalDevotion < 0) this.totalDevotion = 0;
/*     */ 
/*     */     
/* 119 */     this.devotionPoints = devotionPoints;
/*     */     
/* 121 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setPhysique(float physique) {
/* 125 */     this.physique = physique;
/*     */     
/* 127 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setCunning(float cunning) {
/* 131 */     this.cunning = cunning;
/*     */     
/* 133 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setSpirit(float spirit) {
/* 137 */     this.spirit = spirit;
/*     */     
/* 139 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setHealth(float health) {
/* 143 */     this.health = health;
/*     */     
/* 145 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setEnergy(float energy) {
/* 149 */     this.energy = energy;
/*     */     
/* 151 */     this.changed = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read() throws IOException {
/* 159 */     int val = 0;
/*     */     
/* 161 */     GDReader.Block block = new GDReader.Block();
/*     */     
/* 163 */     val = GDReader.readBlockStart(block);
/* 164 */     if (val != 2) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/* 166 */     this.version = GDReader.readEncInt(true);
/* 167 */     if (this.version != 8) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/* 169 */     this.level = GDReader.readEncInt(true);
/* 170 */     this.experience = GDReader.readEncInt(true);
/* 171 */     this.modifierPoints = GDReader.readEncInt(true);
/* 172 */     this.skillPoints = GDReader.readEncInt(true);
/* 173 */     this.devotionPoints = GDReader.readEncInt(true);
/* 174 */     this.totalDevotion = GDReader.readEncInt(true);
/* 175 */     this.physique = GDReader.readEncFloat(true);
/* 176 */     this.cunning = GDReader.readEncFloat(true);
/* 177 */     this.spirit = GDReader.readEncFloat(true);
/* 178 */     this.health = GDReader.readEncFloat(true);
/* 179 */     this.energy = GDReader.readEncFloat(true);
/*     */ 
/*     */     
/* 182 */     GDReader.readBlockEnd(block);
/*     */     
/* 184 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/* 188 */     GDReader.Block block = new GDReader.Block();
/* 189 */     GDWriter.writeBlockStart(block, 2);
/*     */     
/* 191 */     GDWriter.writeInt(8);
/*     */     
/* 193 */     GDWriter.writeInt(this.level);
/* 194 */     GDWriter.writeInt(this.experience);
/* 195 */     GDWriter.writeInt(this.modifierPoints);
/* 196 */     GDWriter.writeInt(this.skillPoints);
/* 197 */     GDWriter.writeInt(this.devotionPoints);
/* 198 */     GDWriter.writeInt(this.totalDevotion);
/* 199 */     GDWriter.writeFloat(this.physique);
/* 200 */     GDWriter.writeFloat(this.cunning);
/* 201 */     GDWriter.writeFloat(this.spirit);
/* 202 */     GDWriter.writeFloat(this.health);
/* 203 */     GDWriter.writeFloat(this.energy);
/*     */ 
/*     */     
/* 206 */     GDWriter.writeBlockEnd(block);
/*     */     
/* 208 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/* 212 */     int size = 0;
/*     */     
/* 214 */     size += 4;
/* 215 */     size += 4;
/* 216 */     size += 4;
/* 217 */     size += 4;
/* 218 */     size += 4;
/* 219 */     size += 4;
/* 220 */     size += 4;
/* 221 */     size += 4;
/* 222 */     size += 4;
/* 223 */     size += 4;
/* 224 */     size += 4;
/* 225 */     size += 4;
/* 226 */     size += 4;
/* 227 */     size += 4;
/* 228 */     size += 4;
/*     */     
/* 230 */     return size;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\character\GDCharBio.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */