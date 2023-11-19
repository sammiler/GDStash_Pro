/*     */ package org.gdstash.description;
/*     */ 
/*     */ import java.util.Comparator;
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
/*     */ public class ItemComparator
/*     */   implements Comparator<BonusInfoSort>
/*     */ {
/*     */   public int compare(BonusInfoSort bis1, BonusInfoSort bis2) {
/*  20 */     boolean base1 = bis1.isBaseStat();
/*  21 */     boolean base2 = bis2.isBaseStat();
/*     */     
/*  23 */     if (base1 && 
/*  24 */       !base2) return -1;
/*     */     
/*  26 */     if (base2 && 
/*  27 */       !base1) return 1;
/*     */ 
/*     */     
/*  30 */     int rate1 = rateInfoType(bis1);
/*  31 */     int rate2 = rateInfoType(bis2);
/*     */     
/*  33 */     if (rate1 < rate2) return 1; 
/*  34 */     if (rate1 > rate2) return -1;
/*     */     
/*  36 */     int type1 = bis1.getTypeInt();
/*  37 */     int type2 = bis2.getTypeInt();
/*     */     
/*  39 */     if (type1 < type2) return 1; 
/*  40 */     if (type1 > type2) return -1;
/*     */     
/*  42 */     int tag1 = bis1.getTagInt();
/*  43 */     int tag2 = bis2.getTagInt();
/*     */     
/*  45 */     if (tag1 < tag2) return 1; 
/*  46 */     if (tag1 > tag2) return -1;
/*     */     
/*  48 */     int prio1 = bis1.prio;
/*  49 */     int prio2 = bis2.prio;
/*     */     
/*  51 */     if (prio1 < prio2) return 1; 
/*  52 */     if (prio1 > prio2) return -1;
/*     */     
/*  54 */     return 0;
/*     */   }
/*     */   
/*     */   protected int rateInfoType(BonusInfoSort bis) {
/*  58 */     int prio = 0;
/*     */     
/*  60 */     if (bis.getTag().equals("DEF_BLOCK_CHANCE")) {
/*  61 */       return 700;
/*     */     }
/*     */     
/*  64 */     if (bis.getTag().equals("STAT_SHIELD_RECOVERY_TIME")) {
/*  65 */       return 650;
/*     */     }
/*     */     
/*  68 */     if (bis.getInfoType() == 2) {
/*  69 */       prio = 600;
/*     */       
/*  71 */       if (bis.isGlobal()) {
/*  72 */         if (bis.getGlobalCategory() == 1) prio -= 10; 
/*  73 */         if (bis.getGlobalCategory() == 3) prio -= 20; 
/*  74 */         if (bis.getGlobalCategory() == 4) prio -= 25; 
/*  75 */         if (bis.getGlobalCategory() == 5) prio -= 30; 
/*  76 */         if (bis.getGlobalCategory() == 6) prio -= 35; 
/*  77 */         if (bis.getGlobalCategory() == 7) prio -= 40; 
/*  78 */         if (bis.getGlobalCategory() == 2) prio -= 50; 
/*  79 */         if (bis.getGlobalCategory() == 8) prio -= 60; 
/*  80 */         if (bis.getGlobalCategory() == 0) prio -= 70;
/*     */       
/*     */       } 
/*  83 */       return prio;
/*     */     } 
/*  85 */     if (bis.getInfoType() == 1) return 500; 
/*  86 */     if (bis.getInfoType() == 4) return 400; 
/*  87 */     if (bis.getInfoType() == 3) {
/*  88 */       prio = 300;
/*     */       
/*  90 */       if (bis.isGlobal()) {
/*  91 */         if (bis.getGlobalCategory() == 1) prio -= 10; 
/*  92 */         if (bis.getGlobalCategory() == 3) prio -= 20; 
/*  93 */         if (bis.getGlobalCategory() == 4) prio -= 25; 
/*  94 */         if (bis.getGlobalCategory() == 5) prio -= 30; 
/*  95 */         if (bis.getGlobalCategory() == 6) prio -= 35; 
/*  96 */         if (bis.getGlobalCategory() == 7) prio -= 40; 
/*  97 */         if (bis.getGlobalCategory() == 2) prio -= 50; 
/*  98 */         if (bis.getGlobalCategory() == 8) prio -= 60; 
/*  99 */         if (bis.getGlobalCategory() == 0) prio -= 70;
/*     */       
/*     */       } 
/* 102 */       return prio;
/*     */     } 
/* 104 */     if (bis.getInfoType() == 5) return 200; 
/* 105 */     if (bis.getInfoType() == 6) return 100;
/*     */     
/* 107 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\description\ItemComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */