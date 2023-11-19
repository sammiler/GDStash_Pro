/*    */ package org.gdstash.ui.font;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ComparisonResult
/*    */ {
/*    */   private boolean exactMatch;
/*    */   private int listIndex;
/*    */   
/*    */   public ComparisonResult(int listIndex, boolean exactMatch) {
/* 17 */     this.listIndex = listIndex;
/* 18 */     this.exactMatch = exactMatch;
/*    */   }
/*    */   
/*    */   public boolean isExactMatch() {
/* 22 */     return this.exactMatch;
/*    */   }
/*    */   
/*    */   public int getListIndex() {
/* 26 */     return this.listIndex;
/*    */   }
/*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\font\ComparisonResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */