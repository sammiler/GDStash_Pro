/*    */ package org.gdstash.ui.font;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import javax.swing.DefaultListCellRenderer;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JList;
/*    */ import javax.swing.ListCellRenderer;
/*    */ import javax.swing.border.Border;
/*    */ import javax.swing.border.EmptyBorder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BorderListCellRenderer<E extends String>
/*    */   implements ListCellRenderer<String>
/*    */ {
/*    */   private Border insetBorder;
/*    */   private DefaultListCellRenderer defaultRenderer;
/*    */   
/*    */   public BorderListCellRenderer(int rightMargin) {
/* 25 */     this.insetBorder = new EmptyBorder(0, 2, 0, rightMargin);
/* 26 */     this.defaultRenderer = new DefaultListCellRenderer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
/* 33 */     JLabel renderer = (JLabel)this.defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
/*    */ 
/*    */     
/* 36 */     renderer.setBorder(this.insetBorder);
/*    */     
/* 38 */     return renderer;
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\font\BorderListCellRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */