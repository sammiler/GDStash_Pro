/*     */ package org.gdstash.ui.table;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import org.gdstash.file.DDSLoader;
/*     */ import org.gdstash.item.GDItem;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDItemNameTableModel
/*     */   extends DefaultTableModel
/*     */ {
/*     */   private List<GDItem> data;
/*     */   private GDItemNameRow[] rows;
/*     */   private Comparator<GDItemNameRow> comparator;
/*     */   private boolean inclFaction;
/*     */   
/*     */   public GDItemNameTableModel(List<GDItem> data, boolean inclFaction) {
/*  30 */     this.data = data;
/*  31 */     this.inclFaction = inclFaction;
/*  32 */     this.comparator = null;
/*     */     
/*  34 */     adjustUI();
/*     */   }
/*     */   
/*     */   public void adjustUI() {
/*  38 */     GDItemNameRow.updateColumnNames();
/*     */     
/*  40 */     setColumnIdentifiers((Object[])GDItemNameRow.columnNames);
/*     */     
/*  42 */     setData(this.data);
/*     */   }
/*     */   
/*     */   public void setData(List<GDItem> data) {
/*  46 */     this.data = data;
/*     */     
/*  48 */     this.rows = null;
/*     */     
/*  50 */     if (data == null)
/*     */       return; 
/*  52 */     int size = data.size();
/*     */     
/*  54 */     this.rows = new GDItemNameRow[size];
/*     */     
/*  56 */     GDItem.LabelInfo liEmpty = new GDItem.LabelInfo();
/*  57 */     liEmpty.text = "";
/*     */     
/*  59 */     int i = 0;
/*  60 */     GDItem.LabelInfo li = null;
/*     */     
/*  62 */     for (GDItem item : data) {
/*  63 */       this.rows[i] = new GDItemNameRow();
/*     */       
/*  65 */       BufferedImage img = item.getFullImage();
/*     */       
/*  67 */       (this.rows[i]).item = item;
/*  68 */       (this.rows[i]).baseName = item.getBaseName();
/*     */ 
/*     */       
/*  71 */       if (img != null) {
/*  72 */         if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/*  73 */           int w = img.getWidth() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*  74 */           int h = img.getHeight() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*  75 */           img = DDSLoader.getScaledImage(img, w, h);
/*     */         } 
/*     */         
/*  78 */         (this.rows[i]).icon = new ImageIcon(img);
/*     */       } 
/*     */       
/*  81 */       li = item.getCompleteNameInfo(this.inclFaction, true);
/*  82 */       if (li == null) li = liEmpty; 
/*  83 */       (this.rows[i]).name = li;
/*     */       
/*  85 */       li = item.getRequiredLevelInfo();
/*  86 */       if (li == null) li = liEmpty; 
/*  87 */       (this.rows[i]).level = li;
/*     */       
/*  89 */       i++;
/*     */     } 
/*     */ 
/*     */     
/*  93 */     if (this.comparator != null) Arrays.sort(this.rows, this.comparator);
/*     */     
/*  95 */     fireTableDataChanged();
/*     */   }
/*     */   
/*     */   public void updateRow(int row, GDItem item) {
/*  99 */     if (this.data == null)
/*     */       return; 
/* 101 */     this.data.set(row, item);
/*     */     
/* 103 */     setData(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeRow(int row) {
/* 108 */     if (row < 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 114 */     GDItem item = (this.rows[row]).item;
/*     */     
/* 116 */     this.data.remove(item);
/*     */     
/* 118 */     setData(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowCount() {
/* 123 */     if (this.rows == null) return 0;
/*     */     
/* 125 */     return this.rows.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/* 130 */     return GDItemNameRow.columnNames.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getColumnName(int column) {
/* 135 */     return GDItemNameRow.columnNames[column];
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValueAt(int row, int column) {
/* 140 */     if (this.rows == null) return null; 
/* 141 */     if (this.rows.length < row) return null;
/*     */     
/* 143 */     if (column == 0) return (this.rows[row]).icon; 
/* 144 */     if (column == 1) return (this.rows[row]).name.text; 
/* 145 */     if (column == 2) return (this.rows[row]).level.text;
/*     */     
/* 147 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCellEditable(int row, int column) {
/* 152 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValueAt(Object value, int row, int column) {
/* 157 */     if (!isCellEditable(row, column))
/*     */       return; 
/* 159 */     if (column == 0) (this.rows[row]).icon = (ImageIcon)value; 
/* 160 */     if (column == 1) (this.rows[row]).name.text = (String)value; 
/* 161 */     if (column == 2) (this.rows[row]).level.text = (String)value;
/*     */   
/*     */   }
/*     */   
/*     */   public Class getColumnClass(int column) {
/* 166 */     return GDItemNameRow.COLUMN_CLASSES[column];
/*     */   }
/*     */   
/*     */   public String getCellText(int row, int column) {
/* 170 */     if (column == 1) return (this.rows[row]).name.text; 
/* 171 */     if (column == 2) return (this.rows[row]).level.text;
/*     */     
/* 173 */     return "";
/*     */   }
/*     */   
/*     */   public Color getCellTextColor(int row, int column) {
/* 177 */     if (column == 1) return (this.rows[row]).name.foreground; 
/* 178 */     if (column == 2) return (this.rows[row]).level.foreground;
/*     */     
/* 180 */     return Color.BLACK;
/*     */   }
/*     */   
/*     */   public Color getCellBackgroundColor(int row, int column) {
/* 184 */     if (column == 1) return (this.rows[row]).name.background; 
/* 185 */     if (column == 2) return (this.rows[row]).name.background;
/*     */     
/* 187 */     return Color.WHITE;
/*     */   }
/*     */   
/*     */   public GDItem getItem(int row) {
/* 191 */     if (row < 0) return null; 
/* 192 */     if (row > this.rows.length) return null;
/*     */     
/* 194 */     return (this.rows[row]).item;
/*     */   }
/*     */   
/*     */   public void sort(Comparator<GDItemNameRow> comp) {
/* 198 */     if (comp == null)
/*     */       return; 
/* 200 */     this.comparator = comp;
/*     */     
/* 202 */     if (this.rows == null)
/*     */       return; 
/* 204 */     Arrays.sort(this.rows, this.comparator);
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\table\GDItemNameTableModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */