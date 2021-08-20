/*     */ package org.gdstash.ui.table;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import org.gdstash.db.DBStashItem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDItemSimilarTableModel
/*     */   extends DefaultTableModel
/*     */ {
/*     */   private DBStashItem.DuplicateInfo data;
/*     */   private GDItemSimilarRow[] rows;
/*     */   
/*     */   public GDItemSimilarTableModel(DBStashItem.DuplicateInfo info) {
/*  20 */     this.data = info;
/*     */     
/*  22 */     adjustUI();
/*     */   }
/*     */   
/*     */   public void adjustUI() {
/*  26 */     GDItemSimilarRow.updateColumnNames();
/*     */     
/*  28 */     setColumnIdentifiers((Object[])GDItemSimilarRow.columnNames);
/*     */     
/*  30 */     setData(this.data);
/*     */   }
/*     */   
/*     */   public void setData(DBStashItem.DuplicateInfo info) {
/*  34 */     this.data = info;
/*     */     
/*  36 */     this.rows = new GDItemSimilarRow[1];
/*  37 */     this.rows[0] = new GDItemSimilarRow(info);
/*     */     
/*  39 */     fireTableDataChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeRow(int row) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowCount() {
/*  49 */     if (this.rows == null) return 0;
/*     */     
/*  51 */     return this.rows.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/*  56 */     return GDItemSimilarRow.columnNames.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getColumnName(int column) {
/*  61 */     return GDItemSimilarRow.columnNames[column];
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValueAt(int row, int column) {
/*  66 */     if (this.rows == null) return null;
/*     */     
/*  68 */     if (column == 0) return (this.rows[row]).itemCombo.text; 
/*  69 */     if (column == 1) return (this.rows[row]).classCombo.text; 
/*  70 */     if (column == 2) return (this.rows[row]).itemPrefix.text; 
/*  71 */     if (column == 3) return (this.rows[row]).levelPrefix.text; 
/*  72 */     if (column == 4) return (this.rows[row]).itemSuffix.text; 
/*  73 */     if (column == 5) return (this.rows[row]).levelSuffix.text;
/*     */     
/*  75 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCellEditable(int row, int column) {
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValueAt(Object value, int row, int column) {
/*  85 */     if (!isCellEditable(row, column))
/*     */       return; 
/*  87 */     if (column == 0) (this.rows[row]).itemCombo.text = (String)value; 
/*  88 */     if (column == 1) (this.rows[row]).classCombo.text = (String)value; 
/*  89 */     if (column == 2) (this.rows[row]).itemPrefix.text = (String)value; 
/*  90 */     if (column == 3) (this.rows[row]).levelPrefix.text = (String)value; 
/*  91 */     if (column == 4) (this.rows[row]).itemSuffix.text = (String)value; 
/*  92 */     if (column == 5) (this.rows[row]).levelSuffix.text = (String)value;
/*     */   
/*     */   }
/*     */   
/*     */   public Class getColumnClass(int column) {
/*  97 */     return GDItemSimilarRow.COLUMN_CLASSES[column];
/*     */   }
/*     */   
/*     */   public String getCellText(int row, int column) {
/* 101 */     if (column == 0) return (this.rows[row]).itemCombo.text; 
/* 102 */     if (column == 1) return (this.rows[row]).classCombo.text; 
/* 103 */     if (column == 2) return (this.rows[row]).itemPrefix.text; 
/* 104 */     if (column == 3) return (this.rows[row]).levelPrefix.text; 
/* 105 */     if (column == 4) return (this.rows[row]).itemSuffix.text; 
/* 106 */     if (column == 5) return (this.rows[row]).levelSuffix.text;
/*     */     
/* 108 */     return "";
/*     */   }
/*     */   
/*     */   public Color getCellTextColor(int row, int column) {
/* 112 */     if (column == 0) return (this.rows[row]).itemCombo.foreground; 
/* 113 */     if (column == 1) return (this.rows[row]).classCombo.foreground; 
/* 114 */     if (column == 2) return (this.rows[row]).itemPrefix.foreground; 
/* 115 */     if (column == 3) return (this.rows[row]).levelPrefix.foreground; 
/* 116 */     if (column == 4) return (this.rows[row]).itemSuffix.foreground; 
/* 117 */     if (column == 5) return (this.rows[row]).levelSuffix.foreground;
/*     */     
/* 119 */     return Color.BLACK;
/*     */   }
/*     */   
/*     */   public Color getCellBackgroundColor(int row, int column) {
/* 123 */     if (column == 0) return (this.rows[row]).itemCombo.background; 
/* 124 */     if (column == 1) return (this.rows[row]).classCombo.background; 
/* 125 */     if (column == 2) return (this.rows[row]).itemPrefix.background; 
/* 126 */     if (column == 3) return (this.rows[row]).levelPrefix.background; 
/* 127 */     if (column == 4) return (this.rows[row]).itemSuffix.background; 
/* 128 */     if (column == 5) return (this.rows[row]).levelSuffix.background;
/*     */     
/* 130 */     return Color.WHITE;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\table\GDItemSimilarTableModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */