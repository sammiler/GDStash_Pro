/*     */ package org.gdstash.ui.table;
/*     */ 
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.TableCellEditor;
/*     */ import org.gdstash.character.GDChar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDDevotionTableModel
/*     */   extends DefaultTableModel
/*     */ {
/*     */   private GDChar gdc;
/*     */   private GDDevotionRow[] rows;
/*     */   
/*     */   public GDDevotionTableModel(GDChar gdc) {
/*  20 */     this.gdc = gdc;
/*     */     
/*  22 */     adjustUI();
/*     */   }
/*     */   
/*     */   public void adjustUI() {
/*  26 */     GDDevotionRow.updateColumnNames();
/*     */     
/*  28 */     setColumnIdentifiers((Object[])GDDevotionRow.columnNames);
/*     */     
/*  30 */     setData(this.gdc);
/*     */   }
/*     */   
/*     */   public void setData(GDChar gdc) {
/*  34 */     this.gdc = gdc;
/*     */     
/*  36 */     this.rows = null;
/*     */     
/*  38 */     if (gdc == null)
/*     */       return; 
/*  40 */     GDChar.SkillInfo[] devotions = gdc.getDevotionInfo();
/*  41 */     if (devotions == null)
/*     */       return; 
/*  43 */     int size = devotions.length;
/*     */     
/*  45 */     this.rows = new GDDevotionRow[size];
/*     */     int i;
/*  47 */     for (i = 0; i < devotions.length; i++) {
/*  48 */       this.rows[i] = new GDDevotionRow(devotions[i], gdc.getSkillMaxLevel((devotions[i]).id));
/*     */     }
/*     */     
/*  51 */     fireTableDataChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowCount() {
/*  56 */     if (this.rows == null) return 0;
/*     */     
/*  58 */     return this.rows.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/*  63 */     return GDDevotionRow.columnNames.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getColumnName(int column) {
/*  68 */     return GDDevotionRow.columnNames[column];
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValueAt(int row, int column) {
/*  73 */     if (this.rows == null) return null;
/*     */     
/*  75 */     if (column == 0) return (this.rows[row]).devotion.name; 
/*  76 */     if (column == 1) return Integer.toString((this.rows[row]).devotion.points);
/*     */     
/*  78 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCellEditable(int row, int column) {
/*  83 */     if (column == 1) return true;
/*     */     
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValueAt(Object value, int row, int column) {
/*  90 */     if (!isCellEditable(row, column))
/*     */       return; 
/*  92 */     if (column == 0) (this.rows[row]).devotion.name = (String)value; 
/*  93 */     if (column == 1) (this.rows[row]).devotion.points = Integer.parseInt((String)value);
/*     */   
/*     */   }
/*     */   
/*     */   public Class getColumnClass(int column) {
/*  98 */     return GDDevotionRow.COLUMN_CLASSES[column];
/*     */   }
/*     */   
/*     */   public GDChar.SkillInfo[] getDevotions() {
/* 102 */     GDChar.SkillInfo[] devotions = null;
/*     */     
/* 104 */     if (this.rows != null && this.rows.length > 0) {
/* 105 */       devotions = new GDChar.SkillInfo[this.rows.length];
/*     */       
/* 107 */       for (int i = 0; i < this.rows.length; i++) {
/* 108 */         devotions[i] = (this.rows[i]).devotion;
/*     */       }
/*     */     } 
/*     */     
/* 112 */     return devotions;
/*     */   }
/*     */   
/*     */   public TableCellEditor getLevelEditor(int row) {
/* 116 */     if (row > this.rows.length) return null;
/*     */     
/* 118 */     return (this.rows[row]).editor;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\table\GDDevotionTableModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */