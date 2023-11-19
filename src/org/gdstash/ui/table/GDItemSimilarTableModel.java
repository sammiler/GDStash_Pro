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
/*  67 */     if (row < 0) return null; 
/*  68 */     if (row >= this.rows.length) return null; 
/*  69 */     if (this.rows[row] == null) return null;
/*     */     
/*  71 */     if (column == 0) return (this.rows[row]).itemCombo.text; 
/*  72 */     if (column == 1) return (this.rows[row]).classCombo.text; 
/*  73 */     if (column == 2) return (this.rows[row]).itemPrefix.text; 
/*  74 */     if (column == 3) return (this.rows[row]).levelPrefix.text; 
/*  75 */     if (column == 4) return (this.rows[row]).itemSuffix.text; 
/*  76 */     if (column == 5) return (this.rows[row]).levelSuffix.text;
/*     */     
/*  78 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCellEditable(int row, int column) {
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValueAt(Object value, int row, int column) {
/*  88 */     if (!isCellEditable(row, column))
/*     */       return; 
/*  90 */     if (this.rows == null)
/*  91 */       return;  if (row < 0)
/*  92 */       return;  if (row >= this.rows.length)
/*  93 */       return;  if (this.rows[row] == null)
/*     */       return; 
/*  95 */     if (column == 0) (this.rows[row]).itemCombo.text = (String)value; 
/*  96 */     if (column == 1) (this.rows[row]).classCombo.text = (String)value; 
/*  97 */     if (column == 2) (this.rows[row]).itemPrefix.text = (String)value; 
/*  98 */     if (column == 3) (this.rows[row]).levelPrefix.text = (String)value; 
/*  99 */     if (column == 4) (this.rows[row]).itemSuffix.text = (String)value; 
/* 100 */     if (column == 5) (this.rows[row]).levelSuffix.text = (String)value;
/*     */   
/*     */   }
/*     */   
/*     */   public Class getColumnClass(int column) {
/* 105 */     return GDItemSimilarRow.COLUMN_CLASSES[column];
/*     */   }
/*     */   
/*     */   public String getCellText(int row, int column) {
/* 109 */     if (this.rows == null) return ""; 
/* 110 */     if (row < 0) return ""; 
/* 111 */     if (row >= this.rows.length) return ""; 
/* 112 */     if (this.rows[row] == null) return "";
/*     */     
/* 114 */     if (column == 0) return (this.rows[row]).itemCombo.text; 
/* 115 */     if (column == 1) return (this.rows[row]).classCombo.text; 
/* 116 */     if (column == 2) return (this.rows[row]).itemPrefix.text; 
/* 117 */     if (column == 3) return (this.rows[row]).levelPrefix.text; 
/* 118 */     if (column == 4) return (this.rows[row]).itemSuffix.text; 
/* 119 */     if (column == 5) return (this.rows[row]).levelSuffix.text;
/*     */     
/* 121 */     return "";
/*     */   }
/*     */   
/*     */   public Color getCellTextColor(int row, int column) {
/* 125 */     if (this.rows == null) return Color.BLACK; 
/* 126 */     if (row < 0) return Color.BLACK; 
/* 127 */     if (row >= this.rows.length) return Color.BLACK; 
/* 128 */     if (this.rows[row] == null) return Color.BLACK;
/*     */     
/* 130 */     if (column == 0) return (this.rows[row]).itemCombo.foreground; 
/* 131 */     if (column == 1) return (this.rows[row]).classCombo.foreground; 
/* 132 */     if (column == 2) return (this.rows[row]).itemPrefix.foreground; 
/* 133 */     if (column == 3) return (this.rows[row]).levelPrefix.foreground; 
/* 134 */     if (column == 4) return (this.rows[row]).itemSuffix.foreground; 
/* 135 */     if (column == 5) return (this.rows[row]).levelSuffix.foreground;
/*     */     
/* 137 */     return Color.BLACK;
/*     */   }
/*     */   
/*     */   public Color getCellBackgroundColor(int row, int column) {
/* 141 */     if (this.rows == null) return Color.WHITE; 
/* 142 */     if (row < 0) return Color.WHITE; 
/* 143 */     if (row >= this.rows.length) return Color.WHITE; 
/* 144 */     if (this.rows[row] == null) return Color.WHITE;
/*     */     
/* 146 */     if (column == 0) return (this.rows[row]).itemCombo.background; 
/* 147 */     if (column == 1) return (this.rows[row]).classCombo.background; 
/* 148 */     if (column == 2) return (this.rows[row]).itemPrefix.background; 
/* 149 */     if (column == 3) return (this.rows[row]).levelPrefix.background; 
/* 150 */     if (column == 4) return (this.rows[row]).itemSuffix.background; 
/* 151 */     if (column == 5) return (this.rows[row]).levelSuffix.background;
/*     */     
/* 153 */     return Color.WHITE;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\table\GDItemSimilarTableModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */