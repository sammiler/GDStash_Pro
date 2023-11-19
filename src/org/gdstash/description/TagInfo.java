/*     */ package org.gdstash.description;
/*     */ public class TagInfo { public static final String TAG_UNI_RACE_DAMAGE = "TXT_UNI_RACE_DAMAGE";
/*     */   private String tag;
/*     */   private int infoType;
/*     */   private int prio;
/*     */   private ValueType valueType;
/*     */   private FlatCategory flatCat;
/*     */   private SignCategory signCat;
/*     */   private RandCategory randCat;
/*     */   private boolean baseStat;
/*     */   
/*  12 */   public enum ValueType { INT, FLOAT; }
/*  13 */   public enum FlatCategory { ABSOLUTE, PERCENTAGE, DURATION; }
/*  14 */   public enum SignCategory { OPTIONAL, MANDATORY; }
/*  15 */   public enum RandCategory { FIXED, SCALE, JITTER, SCALE_JITTER; }
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
/*     */   public TagInfo(String tag, int prio) {
/*  29 */     this(tag, prio, ValueType.INT, FlatCategory.ABSOLUTE, SignCategory.OPTIONAL, false, RandCategory.JITTER);
/*     */   }
/*     */   
/*     */   public TagInfo(String tag, int prio, boolean baseStat) {
/*  33 */     this(tag, prio, ValueType.INT, FlatCategory.ABSOLUTE, SignCategory.OPTIONAL, baseStat, RandCategory.JITTER);
/*     */   }
/*     */   
/*     */   public TagInfo(String tag, int prio, ValueType valueType, RandCategory randCat, boolean baseStat) {
/*  37 */     this(tag, prio, valueType, FlatCategory.ABSOLUTE, SignCategory.OPTIONAL, baseStat, randCat);
/*     */   }
/*     */   
/*     */   public TagInfo(String tag, int prio, ValueType valueType, RandCategory randCat) {
/*  41 */     this(tag, prio, valueType, FlatCategory.ABSOLUTE, SignCategory.OPTIONAL, false, randCat);
/*     */   }
/*     */   
/*     */   public TagInfo(String tag, int prio, FlatCategory valueCat) {
/*  45 */     this(tag, prio, ValueType.INT, valueCat, SignCategory.OPTIONAL, false, RandCategory.JITTER);
/*     */   }
/*     */   
/*     */   public TagInfo(String tag, int prio, FlatCategory flatCat, boolean baseStat) {
/*  49 */     this(tag, prio, ValueType.INT, flatCat, SignCategory.OPTIONAL, baseStat, RandCategory.JITTER);
/*     */   }
/*     */   
/*     */   public TagInfo(String tag, int prio, FlatCategory valueCat, RandCategory randCat) {
/*  53 */     this(tag, prio, ValueType.INT, valueCat, SignCategory.OPTIONAL, false, randCat);
/*     */   }
/*     */   
/*     */   public TagInfo(String tag, int prio, FlatCategory flatCat, boolean baseStat, RandCategory randCat) {
/*  57 */     this(tag, prio, ValueType.INT, flatCat, SignCategory.OPTIONAL, false, randCat);
/*     */   }
/*     */   
/*     */   public TagInfo(String tag, int prio, RandCategory randCat) {
/*  61 */     this(tag, prio, ValueType.INT, FlatCategory.ABSOLUTE, SignCategory.OPTIONAL, false, randCat);
/*     */   }
/*     */   
/*     */   public TagInfo(String tag, int prio, ValueType valueType, FlatCategory flatCat, SignCategory signCat, boolean baseStat, RandCategory randCat) {
/*  65 */     this.tag = tag;
/*  66 */     this.prio = prio;
/*  67 */     this.valueType = valueType;
/*  68 */     this.flatCat = flatCat;
/*  69 */     this.signCat = signCat;
/*  70 */     this.baseStat = baseStat;
/*  71 */     this.randCat = randCat;
/*     */     
/*  73 */     setFlags();
/*     */   }
/*     */   
/*     */   private void setFlags() {
/*  77 */     if (this.tag.startsWith("DEF_")) {
/*  78 */       this.infoType = 1;
/*  79 */       this.flatCat = FlatCategory.PERCENTAGE;
/*     */       
/*  81 */       if (this.tag.equals("DEF_PROTECTION")) this.flatCat = FlatCategory.ABSOLUTE;
/*     */     
/*     */     } 
/*  84 */     if (this.tag.startsWith("OFF_")) {
/*  85 */       this.infoType = 2;
/*     */     }
/*     */     
/*  88 */     if (this.tag.startsWith("RET_")) {
/*  89 */       this.infoType = 3;
/*     */     }
/*     */     
/*  92 */     if (this.tag.startsWith("STAT_")) {
/*  93 */       this.infoType = 4;
/*     */     }
/*     */   }
/*     */   
/*     */   public String getTag() {
/*  98 */     return this.tag;
/*     */   }
/*     */   
/*     */   public int getPriority() {
/* 102 */     return this.prio;
/*     */   }
/*     */   
/*     */   public boolean isScaling() {
/* 106 */     boolean scale = (this.randCat == RandCategory.SCALE || this.randCat == RandCategory.SCALE_JITTER);
/*     */ 
/*     */     
/* 109 */     return scale;
/*     */   }
/*     */   
/*     */   public boolean isJittering() {
/* 113 */     boolean jitter = (this.randCat == RandCategory.JITTER || this.randCat == RandCategory.SCALE_JITTER);
/*     */ 
/*     */     
/* 116 */     return jitter;
/*     */   }
/*     */   
/*     */   public boolean isBaseStat() {
/* 120 */     return this.baseStat;
/*     */   }
/*     */   
/*     */   public boolean isMinAsPerc() {
/* 124 */     return (this.flatCat == FlatCategory.PERCENTAGE);
/*     */   }
/*     */   
/*     */   public boolean isMinAsDuration() {
/* 128 */     return (this.flatCat == FlatCategory.DURATION);
/*     */   }
/*     */   
/*     */   public boolean isFloatValue() {
/* 132 */     return (this.valueType == ValueType.FLOAT);
/*     */   }
/*     */   
/*     */   public boolean isSignMandatory() {
/* 136 */     return (this.signCat == SignCategory.MANDATORY);
/*     */   }
/*     */   
/*     */   public int getInfoType() {
/* 140 */     return this.infoType;
/*     */   } }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\description\TagInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */