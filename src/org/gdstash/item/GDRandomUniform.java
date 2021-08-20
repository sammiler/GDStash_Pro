/*     */ package org.gdstash.item;
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
/*     */ public class GDRandomUniform
/*     */ {
/*     */   private static final int a = 16807;
/*     */   private static final int m = 2147483647;
/*     */   private static final int q = 127773;
/*     */   private static final int r = 2836;
/*     */   private long previous;
/*     */   
/*     */   public GDRandomUniform(int seed) {
/*  25 */     setSeed(seed);
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
/*     */   private long generate() {
/*  54 */     int hi = (int)(this.previous / 127773L);
/*  55 */     int lo = (int)(this.previous - (127773 * hi));
/*  56 */     int t = 16807 * lo - 2836 * hi;
/*     */ 
/*     */ 
/*     */     
/*  60 */     if (t >= 0) {
/*  61 */       this.previous = t;
/*     */     } else {
/*  63 */       this.previous = (t + Integer.MAX_VALUE);
/*     */     } 
/*     */     
/*  66 */     return this.previous;
/*     */   }
/*     */   
/*     */   public void setSeed(int seed) {
/*  70 */     long l = 0L;
/*     */     
/*  72 */     if (seed < 0) {
/*  73 */       l = intToUnsignedLong(seed);
/*     */     } else {
/*  75 */       l = seed;
/*     */     } 
/*     */     
/*  78 */     setSeedLong(l);
/*     */   }
/*     */   
/*     */   private void setSeedLong(long seed) {
/*  82 */     this.previous = seed;
/*     */     
/*  84 */     generate();
/*     */   }
/*     */   
/*     */   public float generateFloat(int min, int max) {
/*  88 */     float f = (float)generate() / 2.14748365E9F;
/*     */     
/*  90 */     return f * (max - min) + min;
/*     */   }
/*     */   
/*     */   public int generateInt(int min, int max) {
/*  94 */     long l = generate();
/*     */     
/*  96 */     return (int)(l % (max - min + 1)) + min;
/*     */   }
/*     */   
/*     */   public long generateSeed() {
/* 100 */     return generate();
/*     */   }
/*     */   
/*     */   public static long intToUnsignedLong(int i) {
/* 104 */     return i & 0xFFFFFFFFL;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\item\GDRandomUniform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */