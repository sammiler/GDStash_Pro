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
/*     */ import org.gdstash.util.GDImagePool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDItemCollectionTableModel
/*     */   extends DefaultTableModel
/*     */ {
/*     */   public static final int FILTER_ALL = 1;
/*     */   public static final int FILTER_FOUND = 2;
/*     */   public static final int FILTER_MISSING = 3;
/*  28 */   public static final Color GREEN_BG = new Color(170, 255, 180);
/*  29 */   public static final Color RED_BG = new Color(255, 180, 170);
/*     */   
/*     */   private List<GDItemInfo> data;
/*     */   private GDItemCollectionRow[] rowsAll;
/*     */   private GDItemCollectionRow[] rows;
/*     */   
/*     */   public GDItemCollectionTableModel(List<GDItemInfo> data) {
/*  36 */     this.data = data;
/*     */     
/*  38 */     adjustUI();
/*     */   }
/*     */   
/*     */   public void adjustUI() {
/*  42 */     GDItemCollectionRow.updateColumnNames();
/*     */     
/*  44 */     setColumnIdentifiers((Object[])GDItemCollectionRow.columnNames);
/*     */     
/*  46 */     setData(this.data);
/*     */   }
/*     */   
/*     */   public void setData(List<GDItemInfo> data) {
/*  50 */     this.data = data;
/*     */     
/*  52 */     this.rowsAll = null;
/*  53 */     this.rows = null;
/*     */     
/*  55 */     if (data == null)
/*     */       return; 
/*  57 */     int size = data.size();
/*     */     
/*  59 */     this.rowsAll = new GDItemCollectionRow[size];
/*  60 */     this.rows = new GDItemCollectionRow[size];
/*     */     
/*  62 */     GDItem.LabelInfo liEmpty = new GDItem.LabelInfo();
/*  63 */     liEmpty.text = "";
/*     */     
/*  65 */     int i = 0;
/*  66 */     GDItem.LabelInfo li = null;
/*     */     
/*  68 */     for (GDItemInfo item : data) {
/*  69 */       this.rowsAll[i] = new GDItemCollectionRow();
/*  70 */       this.rows[i] = this.rowsAll[i];
/*     */ 
/*     */       
/*  73 */       BufferedImage img = item.gdItem.getImage();
/*     */       
/*  75 */       (this.rowsAll[i]).item = item;
/*  76 */       (this.rows[i]).baseName = item.gdItem.getBaseName();
/*     */       
/*  78 */       if (img != null) {
/*  79 */         if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/*  80 */           int w = img.getWidth() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*  81 */           int h = img.getHeight() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*  82 */           img = DDSLoader.getScaledImage(img, w, h);
/*     */         } 
/*     */         
/*  85 */         (this.rowsAll[i]).icon = new ImageIcon(img);
/*     */       } 
/*     */       
/*  88 */       li = item.gdItem.getCompleteNameInfo(false, true);
/*  89 */       if (li == null) li = liEmpty; 
/*  90 */       (this.rowsAll[i]).name = li;
/*     */       
/*  92 */       li = item.gdItem.getRequiredLevelInfo();
/*  93 */       if (li == null) li = liEmpty; 
/*  94 */       (this.rowsAll[i]).level = li;
/*     */       
/*  96 */       if (item.scCount == 0) {
/*  97 */         (this.rowsAll[i]).softcore = false;
/*  98 */         (this.rowsAll[i]).scIcon = GDImagePool.iconBtnCancel24;
/*     */       } else {
/* 100 */         (this.rowsAll[i]).softcore = true;
/* 101 */         (this.rowsAll[i]).scIcon = GDImagePool.iconBtnOk24;
/*     */       } 
/*     */       
/* 104 */       if (item.hcCount == 0) {
/* 105 */         (this.rowsAll[i]).hardcore = false;
/* 106 */         (this.rowsAll[i]).hcIcon = GDImagePool.iconBtnCancel24;
/*     */       } else {
/* 108 */         (this.rowsAll[i]).hardcore = true;
/* 109 */         (this.rowsAll[i]).hcIcon = GDImagePool.iconBtnOk24;
/*     */       } 
/*     */       
/* 112 */       i++;
/*     */     } 
/*     */     
/* 115 */     fireTableDataChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowCount() {
/* 120 */     if (this.rows == null) return 0;
/*     */     
/* 122 */     return this.rows.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/* 127 */     return GDItemCollectionRow.columnNames.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getColumnName(int column) {
/* 132 */     return GDItemCollectionRow.columnNames[column];
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValueAt(int row, int column) {
/* 137 */     if (this.rows == null) return null; 
/* 138 */     if (row < 0) return null; 
/* 139 */     if (row >= this.rows.length) return null; 
/* 140 */     if (this.rows[row] == null) return null;
/*     */     
/* 142 */     if (column == 0) return (this.rows[row]).icon; 
/* 143 */     if (column == 1) return (this.rows[row]).name.text; 
/* 144 */     if (column == 2) return (this.rows[row]).level.text; 
/* 145 */     if (column == 3) return (this.rows[row]).scIcon; 
/* 146 */     if (column == 4) return (this.rows[row]).hcIcon;
/*     */     
/* 148 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCellEditable(int row, int column) {
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValueAt(Object value, int row, int column) {
/* 158 */     if (!isCellEditable(row, column))
/*     */       return; 
/* 160 */     if (this.rows == null)
/* 161 */       return;  if (row < 0)
/* 162 */       return;  if (row >= this.rows.length)
/* 163 */       return;  if (this.rows[row] == null)
/*     */       return; 
/* 165 */     if (column == 0) (this.rows[row]).icon = (ImageIcon)value; 
/* 166 */     if (column == 1) (this.rows[row]).name.text = (String)value; 
/* 167 */     if (column == 2) (this.rows[row]).level.text = (String)value; 
/* 168 */     if (column == 3) (this.rows[row]).scIcon = (ImageIcon)value; 
/* 169 */     if (column == 4) (this.rows[row]).hcIcon = (ImageIcon)value;
/*     */   
/*     */   }
/*     */   
/*     */   public Class getColumnClass(int column) {
/* 174 */     return GDItemCollectionRow.COLUMN_CLASSES[column];
/*     */   }
/*     */   
/*     */   public String getCellText(int row, int column) {
/* 178 */     if (this.rows == null) return ""; 
/* 179 */     if (row < 0) return ""; 
/* 180 */     if (row >= this.rows.length) return ""; 
/* 181 */     if (this.rows[row] == null) return "";
/*     */     
/* 183 */     if (column == 1) return (this.rows[row]).name.text; 
/* 184 */     if (column == 2) return (this.rows[row]).level.text;
/*     */     
/* 186 */     return "";
/*     */   }
/*     */   
/*     */   public Color getCellTextColor(int row, int column) {
/* 190 */     if (this.rows == null) return Color.BLACK; 
/* 191 */     if (row < 0) return Color.BLACK; 
/* 192 */     if (row >= this.rows.length) return Color.BLACK; 
/* 193 */     if (this.rows[row] == null) return Color.BLACK;
/*     */     
/* 195 */     if (column == 1) return (this.rows[row]).name.foreground; 
/* 196 */     if (column == 2) return (this.rows[row]).level.foreground;
/*     */     
/* 198 */     return Color.BLACK;
/*     */   }
/*     */   
/*     */   public Color getCellBackgroundColor(int row, int column) {
/* 202 */     if (this.rows == null) return Color.WHITE; 
/* 203 */     if (row < 0) return Color.WHITE; 
/* 204 */     if (row >= this.rows.length) return Color.WHITE; 
/* 205 */     if (this.rows[row] == null) return Color.WHITE;
/*     */     
/* 207 */     if (column == 1 || column == 2) {
/*     */       
/* 209 */       if ((this.rows[row]).item.scCount > 0) return GREEN_BG; 
/* 210 */       if ((this.rows[row]).item.hcCount > 0) return GREEN_BG;
/*     */       
/* 212 */       return RED_BG;
/*     */     } 
/*     */     
/* 215 */     return Color.WHITE;
/*     */   }
/*     */   
/*     */   public GDItemInfo getItem(int row) {
/* 219 */     if (this.rows == null) return null; 
/* 220 */     if (row < 0) return null; 
/* 221 */     if (row >= this.rows.length) return null; 
/* 222 */     if (this.rows[row] == null) return null;
/*     */     
/* 224 */     return (this.rows[row]).item;
/*     */   }
/*     */   
/*     */   public void filter(int filter) {
/* 228 */     if (this.rowsAll == null)
/*     */       return; 
/* 230 */     GDItem.LabelInfo li = null;
/* 231 */     GDItem.LabelInfo liEmpty = new GDItem.LabelInfo();
/* 232 */     liEmpty.text = "";
/*     */     
/* 234 */     int size = 0; int i;
/* 235 */     for (i = 0; i < this.rowsAll.length; i++) {
/* 236 */       switch (filter) {
/*     */         case 1:
/* 238 */           size++;
/*     */           break;
/*     */         
/*     */         case 2:
/* 242 */           if ((this.rowsAll[i]).item.scCount > 0 || (this.rowsAll[i]).item.hcCount > 0)
/*     */           {
/* 244 */             size++;
/*     */           }
/*     */           break;
/*     */         
/*     */         case 3:
/* 249 */           if ((this.rowsAll[i]).item.scCount == 0 && (this.rowsAll[i]).item.hcCount == 0)
/*     */           {
/* 251 */             size++;
/*     */           }
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 257 */     if (size == 0) {
/* 258 */       this.rows = null;
/*     */     } else {
/* 260 */       this.rows = new GDItemCollectionRow[size];
/*     */       
/* 262 */       int pos = 0; int j;
/* 263 */       for (j = 0; j < this.rowsAll.length; j++) {
/* 264 */         boolean addItem = false;
/* 265 */         switch (filter) {
/*     */           case 1:
/* 267 */             addItem = true;
/*     */             break;
/*     */           
/*     */           case 2:
/* 271 */             if ((this.rowsAll[j]).item.scCount > 0 || (this.rowsAll[j]).item.hcCount > 0)
/*     */             {
/* 273 */               addItem = true;
/*     */             }
/*     */             break;
/*     */ 
/*     */           
/*     */           case 3:
/* 279 */             if ((this.rowsAll[j]).item.scCount == 0 && (this.rowsAll[j]).item.hcCount == 0)
/*     */             {
/* 281 */               addItem = true;
/*     */             }
/*     */             break;
/*     */         } 
/*     */ 
/*     */         
/* 287 */         if (addItem) {
/* 288 */           this.rows[pos] = this.rowsAll[j];
/*     */           
/* 290 */           pos++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 295 */     fireTableDataChanged();
/*     */   }
/*     */   
/*     */   public void sort(Comparator<GDItemCollectionRow> comp) {
/* 299 */     if (comp == null)
/* 300 */       return;  if (this.rows == null)
/*     */       return; 
/* 302 */     Arrays.sort(this.rows, comp);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\table\GDItemCollectionTableModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */