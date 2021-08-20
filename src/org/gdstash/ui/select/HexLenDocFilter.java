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
/*    */ public class HexLenDocFilter
/*    */   extends DocumentFilter
/*    */ {
/*    */   private int maxLen;
/*    */   
/*    */   public HexLenDocFilter(int len) {
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
/* 53 */       if (isHex(str)) valid = true;
/*    */     
/*    */     }
/* 56 */     if (!valid) Toolkit.getDefaultToolkit().beep();
/*    */     
/* 58 */     return valid;
/*    */   }
/*    */   
/*    */   public boolean isHex(String str) {
/* 62 */     boolean valid = true;
/*    */     
/* 64 */     for (int i = 0; i < str.length(); i++) {
/* 65 */       char c = str.charAt(i);
/*    */       
/* 67 */       if (c != '0' && c != '1' && c != '2' && c != '3' && c != '4' && c != '5' && c != '6' && c != '7' && c != '8' && c != '9' && c != 'A' && c != 'B' && c != 'C' && c != 'D' && c != 'E' && c != 'F' && c != 'a' && c != 'b' && c != 'c' && c != 'd' && c != 'e' && c != 'f') {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 89 */         valid = false;
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/*    */     
/* 95 */     return valid;
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\HexLenDocFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */