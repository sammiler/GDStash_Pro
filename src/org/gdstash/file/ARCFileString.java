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
/*    */ public class ARCFileString
/*    */ {
/*    */   private String fileName;
/*    */   private int tocIndex;
/*    */   private byte[] data;
/*    */   private String text;
/*    */   
/*    */   public ARCFileString(String fileName) {
/* 19 */     this.fileName = fileName;
/*    */     
/* 21 */     this.tocIndex = -1;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 25 */     return this.fileName;
/*    */   }
/*    */   
/*    */   public String getFileName() {
/* 29 */     return this.fileName;
/*    */   }
/*    */   
/*    */   public int getToCIndex() {
/* 33 */     return this.tocIndex;
/*    */   }
/*    */   
/*    */   public void setToCIndex(int index) {
/* 37 */     this.tocIndex = index;
/*    */   }
/*    */   
/*    */   public void setText(String text) {
/* 41 */     this.text = text;
/*    */   }
/*    */   
/*    */   public String getText() {
/* 45 */     return this.text;
/*    */   }
/*    */   
/*    */   public void setData(byte[] data) {
/* 49 */     this.data = data;
/*    */   }
/*    */   
/*    */   public byte[] getData() {
/* 53 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\file\ARCFileString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */