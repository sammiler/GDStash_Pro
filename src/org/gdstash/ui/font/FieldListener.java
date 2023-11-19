/*    */ package org.gdstash.ui.font;
/*    */ 
/*    */ import javax.swing.JList;
/*    */ import javax.swing.JTextField;
/*    */ import javax.swing.ListModel;
/*    */ import javax.swing.event.DocumentEvent;
/*    */ import javax.swing.event.DocumentListener;
/*    */ import javax.swing.text.BadLocationException;
/*    */ import javax.swing.text.Document;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FieldListener
/*    */   implements DocumentListener
/*    */ {
/*    */   private JList list;
/*    */   private JTextField textField;
/*    */   private ListModel model;
/*    */   private SelectionListener selectionListener;
/*    */   
/*    */   public void setList(JList list) {
/* 26 */     this.list = list;
/*    */   }
/*    */   
/*    */   public void setTextField(JTextField textField) {
/* 30 */     this.textField = textField;
/*    */   }
/*    */   
/*    */   public void setModel(ListModel model) {
/* 34 */     this.model = model;
/*    */   }
/*    */   
/*    */   public void setSelectionListener(SelectionListener selectionListener) {
/* 38 */     this.selectionListener = selectionListener;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void changedUpdate(DocumentEvent event) {}
/*    */ 
/*    */   
/*    */   public void insertUpdate(DocumentEvent event) {
/* 47 */     setListSelection(event);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeUpdate(DocumentEvent event) {
/* 52 */     setListSelection(event);
/*    */   }
/*    */   
/*    */   private void setListSelection(DocumentEvent event) {
/* 56 */     Document document = this.textField.getDocument();
/*    */     
/*    */     try {
/* 59 */       String s = document.getText(0, document.getLength());
/* 60 */       if (s.equals("")) {
/* 61 */         this.list.clearSelection();
/* 62 */         this.list.ensureIndexIsVisible(0);
/*    */       } else {
/* 64 */         ComparisonResult result = searchModel(s);
/* 65 */         this.list.removeListSelectionListener(this.selectionListener);
/* 66 */         this.list.ensureIndexIsVisible(this.model.getSize() - 1);
/*    */         
/* 68 */         if (result.isExactMatch()) {
/* 69 */           this.list.setSelectedIndex(result.getListIndex());
/*    */         } else {
/* 71 */           this.list.clearSelection();
/* 72 */           this.list.ensureIndexIsVisible(result.getListIndex());
/*    */         } 
/*    */         
/* 75 */         this.list.addListSelectionListener(this.selectionListener);
/*    */       } 
/* 77 */     } catch (BadLocationException e) {
/* 78 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   private ComparisonResult searchModel(String searchTerm) {
/* 83 */     for (int i = 0; i < this.model.getSize(); i++) {
/* 84 */       Object object = this.model.getElementAt(i);
/* 85 */       String s = object.toString().toLowerCase();
/* 86 */       String t = searchTerm.toLowerCase();
/*    */       
/* 88 */       if (s.equals(t))
/* 89 */         return new ComparisonResult(i, true); 
/* 90 */       if (s.startsWith(t)) {
/* 91 */         return new ComparisonResult(i, false);
/*    */       }
/*    */     } 
/*    */     
/* 95 */     return new ComparisonResult(0, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\font\FieldListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */