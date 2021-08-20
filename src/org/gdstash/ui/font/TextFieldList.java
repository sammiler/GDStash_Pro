/*    */ package org.gdstash.ui.font;
/*    */ 
/*    */ import javax.swing.BoxLayout;
/*    */ import javax.swing.JList;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JScrollPane;
/*    */ import javax.swing.JTextField;
/*    */ import javax.swing.ListModel;
/*    */ import javax.swing.event.ListSelectionListener;
/*    */ import javax.swing.text.Document;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextFieldList
/*    */   extends JPanel
/*    */ {
/*    */   private int rightMargin;
/*    */   private JList<String> jlist;
/*    */   private JTextField textField;
/*    */   private ListModel<String> model;
/*    */   private SelectionListener selectionListener;
/*    */   
/*    */   public TextFieldList(ListModel<String> model) {
/* 28 */     this(model, 10);
/*    */   }
/*    */   
/*    */   public TextFieldList(ListModel<String> model, int rightMargin) {
/* 32 */     this.model = model;
/* 33 */     this.rightMargin = rightMargin;
/* 34 */     this.selectionListener = new SelectionListener();
/*    */     
/* 36 */     createPartControl();
/*    */   }
/*    */   
/*    */   private void createPartControl() {
/* 40 */     FieldListener fieldListener = new FieldListener();
/*    */     
/* 42 */     new JPanel();
/* 43 */     setLayout(new BoxLayout(this, 1));
/*    */     
/* 45 */     this.textField = new JTextField();
/* 46 */     Document document = this.textField.getDocument();
/* 47 */     document.addDocumentListener(fieldListener);
/* 48 */     add(this.textField);
/*    */     
/* 50 */     this.jlist = new JList<>(this.model);
/* 51 */     this.jlist.addListSelectionListener(this.selectionListener);
/* 52 */     this.jlist.setCellRenderer(new BorderListCellRenderer<>(this.rightMargin));
/* 53 */     this.jlist.setSelectionMode(0);
/* 54 */     JScrollPane scrollPane = new JScrollPane(this.jlist);
/* 55 */     add(scrollPane);
/*    */     
/* 57 */     fieldListener.setList(this.jlist);
/* 58 */     fieldListener.setModel(this.model);
/* 59 */     fieldListener.setSelectionListener(this.selectionListener);
/* 60 */     fieldListener.setTextField(this.textField);
/*    */     
/* 62 */     this.selectionListener.setFieldListener(fieldListener);
/* 63 */     this.selectionListener.setList(this.jlist);
/* 64 */     this.selectionListener.setTextField(this.textField);
/*    */   }
/*    */   
/*    */   public void setVisibleRowCount(int count) {
/* 68 */     this.jlist.setVisibleRowCount(count);
/*    */   }
/*    */   
/*    */   public void setSelectedValue(Object object) {
/* 72 */     this.jlist.setSelectedValue(object, true);
/*    */   }
/*    */   
/*    */   public void setSelectedIndex(int index) {
/* 76 */     this.jlist.setSelectedIndex(index);
/*    */   }
/*    */   
/*    */   public String getSelectedValue() {
/* 80 */     return this.jlist.getSelectedValue();
/*    */   }
/*    */   
/*    */   public int getSelectedIndex() {
/* 84 */     return this.jlist.getSelectedIndex();
/*    */   }
/*    */   
/*    */   public void addListSelectionListener(ListSelectionListener listener) {
/* 88 */     this.jlist.addListSelectionListener(listener);
/*    */   }
/*    */   
/*    */   public JList getJList() {
/* 92 */     return this.jlist;
/*    */   }
/*    */   
/*    */   public JTextField getTextField() {
/* 96 */     return this.textField;
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\font\TextFieldList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */