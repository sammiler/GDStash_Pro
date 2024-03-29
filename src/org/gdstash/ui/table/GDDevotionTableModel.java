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
/*  74 */     if (row < 0) return null; 
/*  75 */     if (row >= this.rows.length) return null; 
/*  76 */     if (this.rows[row] == null) return null;
/*     */     
/*  78 */     if (column == 0) return (this.rows[row]).devotion.name; 
/*  79 */     if (column == 1) return Integer.toString((this.rows[row]).devotion.points);
/*     */     
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCellEditable(int row, int column) {
/*  86 */     if (column == 1) return true;
/*     */     
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValueAt(Object value, int row, int column) {
/*  93 */     if (!isCellEditable(row, column))
/*     */       return; 
/*  95 */     if (this.rows == null)
/*  96 */       return;  if (row < 0)
/*  97 */       return;  if (row >= this.rows.length)
/*  98 */       return;  if (this.rows[row] == null)
/*     */       return; 
/* 100 */     if (column == 0) (this.rows[row]).devotion.name = (String)value; 
/* 101 */     if (column == 1) (this.rows[row]).devotion.points = Integer.parseInt((String)value);
/*     */   
/*     */   }
/*     */   
/*     */   public Class getColumnClass(int column) {
/* 106 */     return GDDevotionRow.COLUMN_CLASSES[column];
/*     */   }
/*     */   
/*     */   public GDChar.SkillInfo[] getDevotions() {
/* 110 */     GDChar.SkillInfo[] devotions = null;
/*     */     
/* 112 */     if (this.rows != null && this.rows.length > 0) {
/* 113 */       devotions = new GDChar.SkillInfo[this.rows.length];
/*     */       
/* 115 */       for (int i = 0; i < this.rows.length; i++) {
/* 116 */         devotions[i] = (this.rows[i]).devotion;
/*     */       }
/*     */     } 
/*     */     
/* 120 */     return devotions;
/*     */   }
/*     */   
/*     */   public TableCellEditor getLevelEditor(int row) {
/* 124 */     if (this.rows == null) return null; 
/* 125 */     if (row < 0) return null; 
/* 126 */     if (row >= this.rows.length) return null; 
/* 127 */     if (this.rows[row] == null) return null;
/*     */     
/* 129 */     return (this.rows[row]).editor;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\table\GDDevotionTableModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */