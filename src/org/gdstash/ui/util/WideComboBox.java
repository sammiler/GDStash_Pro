/*    */ package org.gdstash.ui.util;
/*    */ 
/*    */ import java.awt.Dimension;
/*    */ import java.awt.FontMetrics;
/*    */ import java.util.Vector;
/*    */ import javax.swing.ComboBoxModel;
/*    */ import javax.swing.JComboBox;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WideComboBox<T>
/*    */   extends JComboBox<T>
/*    */ {
/*    */   private boolean layingOut = false;
/*    */   
/*    */   public WideComboBox() {}
/*    */   
/*    */   public WideComboBox(T[] items) {
/* 25 */     super(items);
/*    */   }
/*    */   
/*    */   public WideComboBox(Vector<T> items) {
/* 29 */     super(items);
/*    */   }
/*    */   
/*    */   public WideComboBox(ComboBoxModel<T> aModel) {
/* 33 */     super(aModel);
/*    */   }
/*    */   
/*    */   public void doLayout() {
/*    */     try {
/* 38 */       this.layingOut = true;
/* 39 */       super.doLayout();
/*    */     } finally {
/* 41 */       this.layingOut = false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Dimension getSize() {
/* 46 */     Dimension dim = super.getSize();
/*    */     
/* 48 */     if (!this.layingOut) {
/* 49 */       FontMetrics metrics = getFontMetrics(getFont());
/*    */       
/* 51 */       int maxWidth = 0; int i;
/* 52 */       for (i = 0; i < getItemCount(); i++) {
/* 53 */         if (getItemAt(i) != null) {
/*    */           
/* 55 */           String s = getItemAt(i).toString();
/* 56 */           s = stripTags(s);
/*    */           
/* 58 */           int currentWidth = metrics.stringWidth(s);
/* 59 */           if (maxWidth < currentWidth) maxWidth = currentWidth; 
/*    */         } 
/*    */       } 
/* 62 */       dim.width = Math.max(dim.width, maxWidth) + 20;
/*    */     } 
/*    */     
/* 65 */     return dim;
/*    */   }
/*    */   
/*    */   private String stripTags(String s) {
/* 69 */     String newS = "";
/* 70 */     String remainder = s;
/*    */     
/* 72 */     int pos = remainder.indexOf("<");
/* 73 */     while (pos != -1) {
/* 74 */       newS = newS + remainder.substring(0, pos);
/*    */       
/* 76 */       pos = remainder.indexOf(">", pos);
/* 77 */       remainder = remainder.substring(pos + 1);
/*    */       
/* 79 */       pos = remainder.indexOf("<");
/*    */     } 
/*    */     
/* 82 */     newS = newS + remainder;
/*    */     
/* 84 */     return newS;
/*    */   }
/*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\u\\util\WideComboBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */