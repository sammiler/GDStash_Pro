/*    */ package org.gdstash.file;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GDParseException
/*    */   extends Exception
/*    */ {
/*    */   private long offset;
/*    */   
/*    */   public GDParseException(String s) {
/* 17 */     this(s, -1L);
/*    */   }
/*    */   
/*    */   public GDParseException(String s, long offset) {
/* 21 */     super(s);
/*    */     
/* 23 */     this.offset = offset;
/*    */   }
/*    */   
/*    */   public long getOffset() {
/* 27 */     return this.offset;
/*    */   }
/*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\file\GDParseException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */