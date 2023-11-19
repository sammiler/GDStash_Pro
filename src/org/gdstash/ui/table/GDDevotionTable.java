/*    */ package org.gdstash.ui.table;
/*    */ 
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.table.TableCellEditor;
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
/*    */ public class GDDevotionTable
/*    */   extends JTable
/*    */ {
/*    */   public TableCellEditor getCellEditor(int row, int col) {
/* 22 */     TableCellEditor editor = null;
/*    */     
/* 24 */     if (col == 1) {
/* 25 */       GDDevotionTableModel model = (GDDevotionTableModel)getModel();
/* 26 */       editor = model.getLevelEditor(row);
/*    */     } 
/*    */     
/* 29 */     if (editor != null) return editor;
/*    */     
/* 31 */     return super.getCellEditor(row, col);
/*    */   }
/*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\table\GDDevotionTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */