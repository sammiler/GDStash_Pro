/*     */ package org.gdstash.description;
/*     */ 
/*     */ import org.gdstash.db.DBStat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BonusInfoSort
/*     */ {
/*     */   public BonusDetail bonus;
/*     */   public BonusInfo info;
/*     */   public int prio;
/*     */   
/*     */   public BonusInfoSort(BonusDetail bonus, BonusInfo info) {
/*  19 */     this.bonus = bonus;
/*  20 */     this.info = info;
/*  21 */     this.prio = info.prio;
/*     */   }
/*     */   
/*     */   public boolean isBaseStat() {
/*  25 */     return this.info.baseStat;
/*     */   }
/*     */   
/*     */   public boolean isGlobal() {
/*  29 */     return this.info.dmgGlobal;
/*     */   }
/*     */   
/*     */   public boolean isXOR() {
/*  33 */     return this.info.dmgXOR;
/*     */   }
/*     */   
/*     */   public int getInfoType() {
/*  37 */     return this.info.infoType;
/*     */   }
/*     */   
/*     */   public int getGlobalOffensePerc() {
/*  41 */     if (this.bonus.type != 1) return -1; 
/*  42 */     if (this.info.infoType != 2) return -1;
/*     */     
/*  44 */     DBStat stat = (DBStat)this.bonus.entity;
/*  45 */     return stat.getGlobalOffensePerc();
/*     */   }
/*     */   
/*     */   public int getGlobalRetaliationPerc() {
/*  49 */     if (this.bonus.type != 1) return -1; 
/*  50 */     if (this.info.infoType != 3) return -1;
/*     */     
/*  52 */     DBStat stat = (DBStat)this.bonus.entity;
/*  53 */     return stat.getGlobalRetaliationPerc();
/*     */   }
/*     */   
/*     */   public int getGlobalCategory() {
/*  57 */     if (this.bonus.type != 1) return 0;
/*     */     
/*  59 */     DBStat stat = (DBStat)this.bonus.entity;
/*  60 */     return stat.getGlobalCategory();
/*     */   }
/*     */   
/*     */   public int getTypeInt() {
/*  64 */     int value = 0;
/*     */     
/*  66 */     switch (this.bonus.type) {
/*     */       case 1:
/*  68 */         value = 30;
/*     */         
/*  70 */         if (this.info.dmgFlat) value = 105; 
/*  71 */         if (this.info.dmgPerc) value = 104;
/*     */         
/*  73 */         if (this.info.baseStat) value += 100; 
/*  74 */         if (this.info.dmgDuration) value -= 2; 
/*  75 */         if (this.info.dmgChance) value -= 90; 
/*  76 */         if (this.info.dmgGlobal) value -= 20;
/*     */         
/*     */         break;
/*     */       
/*     */       case 3:
/*  81 */         value = 60;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/*  86 */         value = 50;
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/*  91 */     return value;
/*     */   }
/*     */   
/*     */   public String getTag() {
/*  95 */     if (this.info.tag == null) return "";
/*     */     
/*  97 */     return this.info.tag;
/*     */   }
/*     */   
/*     */   public int getTagInt() {
/* 101 */     int value = 0;
/*     */     
/* 103 */     switch (this.bonus.type) {
/*     */       case 1:
/* 105 */         value = getStatTagInt();
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     return value;
/*     */   }
/*     */   
/*     */   public int getInfoTypeInt() {
/* 120 */     int value = 0;
/*     */     
/* 122 */     switch (this.info.infoType) {
/*     */       case 1:
/* 124 */         value = 9;
/*     */         break;
/*     */       
/*     */       case 2:
/* 128 */         value = 10;
/*     */         break;
/*     */       
/*     */       case 3:
/* 132 */         value = 5;
/*     */         break;
/*     */       
/*     */       case 4:
/* 136 */         value = 8;
/*     */         break;
/*     */       
/*     */       case 5:
/* 140 */         value = 6;
/*     */         break;
/*     */       
/*     */       case 6:
/* 144 */         value = 7;
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 149 */     return value;
/*     */   }
/*     */   
/*     */   private int getStatTagInt() {
/* 153 */     if (this.info.tag == null) return -1;
/*     */     
/* 155 */     TagInfo ti = TagInfoHashMap.getInfo(this.info.tag);
/*     */     
/* 157 */     if (ti != null) return ti.getPriority();
/*     */     
/* 159 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\description\BonusInfoSort.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */