/*     */ package org.gdstash.character;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.db.DBSkillTree;
/*     */ import org.gdstash.file.GDReader;
/*     */ import org.gdstash.file.GDWriter;
/*     */ import org.gdstash.ui.character.GDCharMasteryImagePane;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDCharSkillList
/*     */ {
/*     */   private static final int VERSION = 5;
/*     */   private static final int BLOCK = 8;
/*     */   private int version;
/*  37 */   private List<GDCharSkill> charSkills = new LinkedList<>();
/*  38 */   private List<GDCharItemSkill> itemSkills = new LinkedList<>();
/*     */   
/*     */   private int masteriesAllowed;
/*     */   private int skillReclamationPointsUsed;
/*     */   private int devotionReclamationPointsUsed;
/*     */   private boolean changed;
/*     */   
/*     */   public int getSkillReclaimPoints() {
/*  46 */     return this.skillReclamationPointsUsed;
/*     */   }
/*     */   
/*     */   public int getDevotionReclaimPoints() {
/*  50 */     return this.devotionReclamationPointsUsed;
/*     */   }
/*     */   
/*     */   public List<GDCharSkill> getMasteries() {
/*  54 */     List<GDCharSkill> masteries = new LinkedList<>();
/*     */     
/*  56 */     if (this.charSkills != null) {
/*  57 */       for (GDCharSkill skill : this.charSkills) {
/*  58 */         if (skill.isMastery()) {
/*  59 */           masteries.add(skill);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  64 */     return masteries;
/*     */   }
/*     */   
/*     */   public List<GDCharSkill> getCharSkillList() {
/*  68 */     return this.charSkills;
/*     */   }
/*     */   
/*     */   public List<GDCharSkill> getSkillsByMastery(String masteryID) {
/*  72 */     List<GDCharSkill> list = new LinkedList<>();
/*     */     
/*  74 */     if (this.charSkills != null) {
/*  75 */       for (GDCharSkill skill : this.charSkills) {
/*  76 */         if (skill.getMasteryID() != null && 
/*  77 */           skill.getMasteryID().equals(masteryID)) list.add(skill);
/*     */       
/*     */       } 
/*     */     }
/*     */     
/*  82 */     return list;
/*     */   }
/*     */   
/*     */   public List<GDCharSkill> getDevotions() {
/*  86 */     List<GDCharSkill> devotions = new LinkedList<>();
/*     */     
/*  88 */     if (this.charSkills != null) {
/*  89 */       for (GDCharSkill skill : this.charSkills) {
/*  90 */         if (skill.isDevotion() && skill.getSkillLevel() > 0) {
/*  91 */           devotions.add(skill);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  96 */     return devotions;
/*     */   }
/*     */   
/*     */   public int getSkillMaxLevel(String skillID) {
/* 100 */     int maxLevel = 0;
/*     */     
/* 102 */     if (this.charSkills != null) {
/* 103 */       for (GDCharSkill skill : this.charSkills) {
/* 104 */         if (skill.getID().equals(skillID)) {
/* 105 */           maxLevel = skill.getMaxLevel();
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 112 */     return maxLevel;
/*     */   }
/*     */   
/*     */   public boolean hasChanged() {
/* 116 */     return this.changed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSkillReclaimPoints(int skillReclamationPointsUsed) {
/* 124 */     this.skillReclamationPointsUsed = skillReclamationPointsUsed;
/*     */     
/* 126 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setDevotionReclaimPoints(int devotionReclamationPointsUsed) {
/* 130 */     this.devotionReclamationPointsUsed = devotionReclamationPointsUsed;
/*     */     
/* 132 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setSkillLevel(GDChar.SkillInfo[] infos) {
/* 136 */     if (infos == null)
/*     */       return; 
/* 138 */     for (int i = 0; i < infos.length; i++) {
/* 139 */       for (GDCharSkill skill : this.charSkills) {
/* 140 */         if (skill.getID().equals((infos[i]).id)) {
/* 141 */           skill.setLevel((infos[i]).points);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 148 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setMasterySkills(String masteryID, List<GDCharMasteryImagePane.Skill> list) {
/* 152 */     if (list == null)
/* 153 */       return;  if (list.isEmpty())
/* 154 */       return;  if (this.charSkills == null)
/*     */       return; 
/* 156 */     List<GDCharSkill> newList = new LinkedList<>();
/*     */ 
/*     */ 
/*     */     
/* 160 */     for (GDCharSkill skill : this.charSkills) {
/* 161 */       if (skill.getMasteryID() == null) {
/* 162 */         newList.add(skill);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 167 */       if (!skill.getMasteryID().equals(masteryID)) {
/* 168 */         newList.add(skill);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 173 */       GDCharMasteryImagePane.Skill match = null;
/* 174 */       for (GDCharMasteryImagePane.Skill mps : list) {
/* 175 */         if (mps.skill.getSkillID().equals(skill.getID())) {
/* 176 */           match = mps;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 182 */       if (match != null && 
/* 183 */         match.skillLevel > 0) {
/* 184 */         GDCharSkill cs = new GDCharSkill(match.skill.getSkillID(), match.skillLevel);
/*     */         
/* 186 */         if (skill.getAutoCastController() != null) cs.setAutoCastController(skill.getAutoCastController()); 
/* 187 */         if (skill.getAutoCastSkill() != null) cs.setAutoCastSkill(skill.getAutoCastSkill());
/*     */         
/* 189 */         newList.add(cs);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 195 */     for (GDCharMasteryImagePane.Skill mps : list) {
/* 196 */       if (mps.skillLevel == 0)
/*     */         continue; 
/* 198 */       boolean found = false;
/*     */       
/* 200 */       for (GDCharSkill skill : newList) {
/* 201 */         if (mps.skill.getSkillID().equals(skill.getID())) {
/* 202 */           found = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 208 */       if (!found) {
/* 209 */         GDCharSkill cs = new GDCharSkill(mps.skill.getSkillID(), mps.skillLevel);
/*     */         
/* 211 */         newList.add(cs);
/*     */       } 
/*     */     } 
/*     */     
/* 215 */     this.charSkills = newList;
/*     */     
/* 217 */     this.changed = true;
/*     */   }
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
/*     */   public void setMasteriesAllowed(int num) {
/* 270 */     this.masteriesAllowed = num;
/*     */   }
/*     */   
/*     */   public int refundMastery(String masteryID) {
/* 274 */     if (masteryID == null) return 0;
/*     */     
/* 276 */     DBSkillTree tree = DBSkillTree.getBySkillID(masteryID);
/*     */     
/* 278 */     if (tree == null) return 0;
/*     */     
/* 280 */     int points = 0;
/*     */     
/* 282 */     Iterator<GDCharSkill> itSkill = this.charSkills.iterator();
/* 283 */     while (itSkill.hasNext()) {
/* 284 */       GDCharSkill skill = itSkill.next();
/*     */       
/* 286 */       if (tree.getSkillIDList().contains(skill.getID())) {
/* 287 */         points += skill.getSkillLevel();
/*     */         
/* 289 */         itSkill.remove();
/*     */       } 
/*     */     } 
/*     */     
/* 293 */     return points;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read() throws IOException {
/* 301 */     int val = 0;
/*     */     
/* 303 */     GDReader.Block block = new GDReader.Block();
/*     */     
/* 305 */     val = GDReader.readBlockStart(block);
/* 306 */     if (val != 8) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/* 308 */     this.version = GDReader.readEncInt(true);
/* 309 */     if (this.version != 5) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/* 311 */     this.charSkills.clear();
/* 312 */     val = GDReader.readEncInt(true); int i;
/* 313 */     for (i = 0; i < val; i++) {
/* 314 */       GDCharSkill skill = new GDCharSkill();
/* 315 */       skill.read();
/*     */       
/* 317 */       this.charSkills.add(skill);
/*     */     } 
/*     */     
/* 320 */     this.masteriesAllowed = GDReader.readEncInt(true);
/* 321 */     this.skillReclamationPointsUsed = GDReader.readEncInt(true);
/* 322 */     this.devotionReclamationPointsUsed = GDReader.readEncInt(true);
/*     */     
/* 324 */     this.itemSkills.clear();
/* 325 */     val = GDReader.readEncInt(true);
/* 326 */     for (i = 0; i < val; i++) {
/* 327 */       GDCharItemSkill skill = new GDCharItemSkill();
/* 328 */       skill.read();
/*     */       
/* 330 */       this.itemSkills.add(skill);
/*     */     } 
/*     */ 
/*     */     
/* 334 */     GDReader.readBlockEnd(block);
/*     */     
/* 336 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/* 340 */     GDReader.Block block = new GDReader.Block();
/* 341 */     GDWriter.writeBlockStart(block, 8);
/*     */     
/* 343 */     GDWriter.writeInt(this.version);
/*     */     
/* 345 */     int val = this.charSkills.size();
/* 346 */     GDWriter.writeInt(val);
/*     */     
/* 348 */     for (GDCharSkill skill : this.charSkills) {
/* 349 */       if (skill != null) skill.write();
/*     */     
/*     */     } 
/* 352 */     GDWriter.writeInt(this.masteriesAllowed);
/* 353 */     GDWriter.writeInt(this.skillReclamationPointsUsed);
/* 354 */     GDWriter.writeInt(this.devotionReclamationPointsUsed);
/*     */     
/* 356 */     val = this.itemSkills.size();
/* 357 */     GDWriter.writeInt(val);
/*     */     
/* 359 */     for (GDCharItemSkill skill : this.itemSkills) {
/* 360 */       if (skill != null) skill.write();
/*     */     
/*     */     } 
/*     */     
/* 364 */     GDWriter.writeBlockEnd(block);
/*     */     
/* 366 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/* 370 */     int size = 0;
/*     */     
/* 372 */     size += 4;
/* 373 */     size += 4;
/* 374 */     size += 4;
/*     */     
/* 376 */     size += 4;
/* 377 */     for (GDCharSkill skill : this.charSkills) {
/* 378 */       if (skill != null) size += skill.getByteSize();
/*     */     
/*     */     } 
/* 381 */     size += 4;
/* 382 */     size += 4;
/* 383 */     size += 4;
/*     */     
/* 385 */     size += 4;
/* 386 */     for (GDCharItemSkill skill : this.itemSkills) {
/* 387 */       if (skill != null) size += skill.getByteSize();
/*     */     
/*     */     } 
/* 390 */     size += 4;
/*     */     
/* 392 */     return size;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\character\GDCharSkillList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */