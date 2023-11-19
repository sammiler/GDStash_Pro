/*    */ package org.gdstash.ui.util;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import javax.swing.JList;
/*    */ import javax.swing.UIManager;
/*    */ import javax.swing.plaf.basic.BasicComboBoxRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnabledComboBoxRenderer<E>
/*    */   extends BasicComboBoxRenderer
/*    */ {
/*    */   private EnabledComboBoxModel model;
/*    */   private Color disabledColor;
/*    */   
/*    */   public EnabledComboBoxRenderer(EnabledComboBoxModel model) {
/* 24 */     this.model = model;
/* 25 */     this.disabledColor = Color.lightGray;
/*    */   }
/*    */   
/*    */   public void setDisabledColor(Color disabledColor) {
/* 29 */     this.disabledColor = disabledColor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
/* 36 */     Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
/*    */     
/* 38 */     if (this.model == null) {
/* 39 */       c.setBackground(getBackground());
/* 40 */       c.setForeground(getForeground());
/*    */       
/* 42 */       return c;
/*    */     } 
/*    */     
/* 45 */     if (!this.model.isEnabled(index)) {
/* 46 */       if (isSelected) {
/* 47 */         c.setBackground(UIManager.getColor("ComboBox.background"));
/*    */       } else {
/* 49 */         c.setBackground(getBackground());
/*    */       } 
/*    */       
/* 52 */       c.setForeground(this.disabledColor);
/*    */     } else {
/* 54 */       c.setBackground(getBackground());
/* 55 */       c.setForeground(getForeground());
/*    */     } 
/*    */     
/* 58 */     return c;
/*    */   }
/*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\u\\util\EnabledComboBoxRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */