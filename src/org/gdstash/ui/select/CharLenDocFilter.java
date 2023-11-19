/*    */ package org.gdstash.ui.select;
/*    */ 
/*    */ import java.awt.Toolkit;
/*    */ import javax.swing.text.AttributeSet;
/*    */ import javax.swing.text.BadLocationException;
/*    */ import javax.swing.text.DocumentFilter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CharLenDocFilter
/*    */   extends DocumentFilter
/*    */ {
/*    */   private int maxLen;
/*    */   
/*    */   public CharLenDocFilter(int len) {
/* 20 */     this.maxLen = len;
/*    */   }
/*    */ 
/*    */   
/*    */   public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) {
/* 25 */     if (accept(fb, offset, string)) {
/*    */       try {
/* 27 */         super.insertString(fb, offset, string, attr);
/*    */       }
/* 29 */       catch (BadLocationException badLocationException) {}
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) {
/* 35 */     if (accept(fb, offset, text)) {
/*    */       try {
/* 37 */         super.replace(fb, offset, length, text, attrs);
/*    */       }
/* 39 */       catch (BadLocationException badLocationException) {}
/*    */     }
/*    */   }
/*    */   
/*    */   protected boolean accept(DocumentFilter.FilterBypass fb, int offset, String str) {
/* 44 */     if (str == null) return true;
/*    */     
/* 46 */     int strLen = str.length();
/* 47 */     int textLen = fb.getDocument().getLength();
/* 48 */     boolean valid = false;
/*    */     
/* 50 */     if (strLen + textLen <= this.maxLen) {
/* 51 */       valid = true;
/*    */     }
/*    */     
/* 54 */     if (!valid) Toolkit.getDefaultToolkit().beep();
/*    */     
/* 56 */     return valid;
/*    */   }
/*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\select\CharLenDocFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */