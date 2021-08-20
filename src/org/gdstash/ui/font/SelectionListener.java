/*    */ package org.gdstash.ui.font;
/*    */ 
/*    */ import javax.swing.JList;
/*    */ import javax.swing.JTextField;
/*    */ import javax.swing.event.ListSelectionEvent;
/*    */ import javax.swing.event.ListSelectionListener;
/*    */ import javax.swing.text.Document;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SelectionListener
/*    */   implements ListSelectionListener
/*    */ {
/*    */   private FieldListener fieldListener;
/*    */   private JList list;
/*    */   private JTextField textField;
/*    */   
/*    */   public void setFieldListener(FieldListener fieldListener) {
/* 23 */     this.fieldListener = fieldListener;
/*    */   }
/*    */   
/*    */   public void setList(JList list) {
/* 27 */     this.list = list;
/*    */   }
/*    */   
/*    */   public void setTextField(JTextField textField) {
/* 31 */     this.textField = textField;
/*    */   }
/*    */ 
/*    */   
/*    */   public void valueChanged(ListSelectionEvent event) {
/* 36 */     setFieldValue();
/*    */   }
/*    */   
/*    */   private void setFieldValue() {
/* 40 */     Document document = this.textField.getDocument();
/*    */     
/* 42 */     document.removeDocumentListener(this.fieldListener);
/*    */     
/* 44 */     Object object = this.list.getSelectedValue();
/* 45 */     if (object != null) {
/* 46 */       this.textField.setText(object.toString());
/*    */     }
/*    */     
/* 49 */     document.addDocumentListener(this.fieldListener);
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\font\SelectionListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */