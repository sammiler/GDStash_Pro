/*     */ package org.gdstash.item;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.db.*;

/*     */
/*     */ public class ItemInfo {
/*     */   public List<DBStat> itemStats;
/*     */   public List<DBStat> petStats;
/*     */   public List<DBStat> componentStats;
/*     */   public List<DBStat> completionStats;
/*     */   public List<DBStat> enchantmentStats;
/*     */   public List<DBSkillBonus> itemSkillBonuses;
/*     */   public List<DBSkillBonus> componentSkillBonuses;
/*     */   public List<DBSkillBonus> completionSkillBonuses;
/*     */   public List<DBSkillModifier> skillModifiers;
/*     */   public List<Skill> itemSkills;
/*     */   public List<Skill> petSkills;
/*     */   public List<Skill> componentSkills;
/*     */   public List<Skill> completionSkills;
/*     */   
/*     */   public static class Skill {
/*     */     private DBSkill dbSkill;
/*     */     
/*     */     Skill(DBSkill skill, int level, DBController controller) {
/*  25 */       this.dbSkill = skill;
/*  26 */       this.dbController = controller;
/*     */       
/*  28 */       if (level > 0) {
/*  29 */         this.level = level;
/*     */       } else {
/*  31 */         this.level = 1;
/*     */       } 
/*     */     }
/*     */     private int level; private DBController dbController;
/*     */     Skill(DBSkill skill, int level) {
/*  36 */       this.dbSkill = skill;
/*  37 */       this.dbController = null;
/*     */       
/*  39 */       if (level > 0) {
/*  40 */         this.level = level;
/*     */       } else {
/*  42 */         this.level = 1;
/*     */       } 
/*     */     }
/*     */     
/*     */     public DBSkill getSkill() {
/*  47 */       return this.dbSkill;
/*     */     }
/*     */ 
/*     */     
/*     */     public DBController getController() {
/*  52 */       return this.dbController;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getControllerTriggerType() {
/*  57 */       if (this.dbController == null) return null;
/*     */       
/*  59 */       return this.dbController.getTriggerType();
/*     */     }
/*     */     
/*     */     public int getControllerTriggerChance() {
/*  63 */       if (this.dbController == null) return 0;
/*     */       
/*  65 */       return this.dbController.getTriggerChance();
/*     */     }
/*     */     
/*     */     public int getLevel() {
/*  69 */       return this.level;
/*     */     }
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
/*     */   public ItemInfo() {
/*  88 */     this.itemStats = new LinkedList<>();
/*  89 */     this.petStats = new LinkedList<>();
/*  90 */     this.componentStats = new LinkedList<>();
/*  91 */     this.completionStats = new LinkedList<>();
/*  92 */     this.enchantmentStats = new LinkedList<>();
/*  93 */     this.itemSkillBonuses = new LinkedList<>();
/*  94 */     this.itemSkillBonuses = new LinkedList<>();
/*  95 */     this.componentSkillBonuses = new LinkedList<>();
/*  96 */     this.completionSkillBonuses = new LinkedList<>();
/*  97 */     this.skillModifiers = new LinkedList<>();
/*  98 */     this.itemSkills = new LinkedList<>();
/*  99 */     this.petSkills = new LinkedList<>();
/* 100 */     this.componentSkills = new LinkedList<>();
/* 101 */     this.completionSkills = new LinkedList<>();
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\item\ItemInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */