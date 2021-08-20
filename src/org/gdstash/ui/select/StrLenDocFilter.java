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
/*    */ public class StrLenDocFilter
/*    */   extends DocumentFilter
/*    */ {
/*    */   private int maxLen;
/*    */   
/*    */   public StrLenDocFilter(int len) {
/* 20 */     this.maxLen = len;
/*    */   }
/*    */ 
/*    */   
/*    */   public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) {
/* 25 */     if (accept(fb, offset, 0, string)) {
/*    */       try {
/* 27 */         super.insertString(fb, offset, string, attr);
/*    */       }
/* 29 */       catch (BadLocationException badLocationException) {}
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) {
/* 35 */     if (accept(fb, offset, length, text)) {
/*    */       try {
/* 37 */         super.replace(fb, offset, length, text, attrs);
/*    */       }
/* 39 */       catch (BadLocationException badLocationException) {}
/*    */     }
/*    */   }
/*    */   
/*    */   protected boolean accept(DocumentFilter.FilterBypass fb, int offset, int length, String str) {
/* 44 */     if (str == null) return true;
/*    */     
/* 46 */     int strLen = str.length();
/* 47 */     int textLen = fb.getDocument().getLength();
/* 48 */     boolean valid = false;
/*    */ 
/*    */     
/* 51 */     if (strLen + textLen - length <= this.maxLen && strLen + offset <= this.maxLen)
/*    */     {
/* 53 */       valid = true;
/*    */     }
/* 55 */     if (!valid) Toolkit.getDefaultToolkit().beep();
/*    */     
/* 57 */     return valid;
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\StrLenDocFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */