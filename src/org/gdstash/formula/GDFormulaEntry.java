/*    */ package org.gdstash.formula;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GDFormulaEntry
/*    */ {
/* 12 */   private static int READ_TRUE = 1;
/*    */   
/*    */   private String formula;
/*    */   private int read;
/*    */   
/*    */   public GDFormulaEntry(String formula) {
/* 18 */     this(formula, READ_TRUE);
/*    */   }
/*    */   
/*    */   public GDFormulaEntry(String formula, int read) {
/* 22 */     this.formula = formula;
/* 23 */     this.read = read;
/*    */   }
/*    */   
/*    */   public String getFormulaID() {
/* 27 */     return this.formula;
/*    */   }
/*    */   
/*    */   public int getFormulaRead() {
/* 31 */     return this.read;
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\formula\GDFormulaEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */