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
/* 138 */     if (this.rows[row] == null) return null;
/*     */     
/* 140 */     if (column == 0) return (this.rows[row]).icon; 
/* 141 */     if (column == 1) return (this.rows[row]).name.text; 
/* 142 */     if (column == 2) return (this.rows[row]).level.text; 
/* 143 */     if (column == 3) return (this.rows[row]).scIcon; 
/* 144 */     if (column == 4) return (this.rows[row]).hcIcon;
/*     */     
/* 146 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCellEditable(int row, int column) {
/* 151 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValueAt(Object value, int row, int column) {
/* 156 */     if (!isCellEditable(row, column))
/*     */       return; 
/* 158 */     if (column == 0) (this.rows[row]).icon = (ImageIcon)value; 
/* 159 */     if (column == 1) (this.rows[row]).name.text = (String)value; 
/* 160 */     if (column == 2) (this.rows[row]).level.text = (String)value; 
/* 161 */     if (column == 3) (this.rows[row]).scIcon = (ImageIcon)value; 
/* 162 */     if (column == 4) (this.rows[row]).hcIcon = (ImageIcon)value;
/*     */   
/*     */   }
/*     */   
/*     */   public Class getColumnClass(int column) {
/* 167 */     return GDItemCollectionRow.COLUMN_CLASSES[column];
/*     */   }
/*     */   
/*     */   public String getCellText(int row, int column) {
/* 171 */     if (this.rows == null) return null; 
/* 172 */     if (this.rows[row] == null) return null;
/*     */     
/* 174 */     if (column == 1) return (this.rows[row]).name.text; 
/* 175 */     if (column == 2) return (this.rows[row]).level.text;
/*     */     
/* 177 */     return "";
/*     */   }
/*     */   
/*     */   public Color getCellTextColor(int row, int column) {
/* 181 */     if (this.rows == null) return null; 
/* 182 */     if (this.rows[row] == null) return null;
/*     */     
/* 184 */     if (column == 1) return (this.rows[row]).name.foreground; 
/* 185 */     if (column == 2) return (this.rows[row]).level.foreground;
/*     */     
/* 187 */     return Color.BLACK;
/*     */   }
/*     */   
/*     */   public Color getCellBackgroundColor(int row, int column) {
/* 191 */     if (this.rows == null) return null; 
/* 192 */     if (this.rows[row] == null) return null;
/*     */     
/* 194 */     if (column == 1 || column == 2) {
/*     */       
/* 196 */       if ((this.rows[row]).item.scCount > 0) return GREEN_BG; 
/* 197 */       if ((this.rows[row]).item.hcCount > 0) return GREEN_BG;
/*     */       
/* 199 */       return RED_BG;
/*     */     } 
/*     */     
/* 202 */     return Color.WHITE;
/*     */   }
/*     */   
/*     */   public GDItemInfo getItem(int row) {
/* 206 */     if (row < 0) return null; 
/* 207 */     if (row > this.rows.length) return null;
/*     */     
/* 209 */     if (this.rows == null) return null; 
/* 210 */     if (this.rows[row] == null) return null;
/*     */     
/* 212 */     return (this.rows[row]).item;
/*     */   }
/*     */   
/*     */   public void filter(int filter) {
/* 216 */     if (this.rowsAll == null)
/*     */       return; 
/* 218 */     GDItem.LabelInfo li = null;
/* 219 */     GDItem.LabelInfo liEmpty = new GDItem.LabelInfo();
/* 220 */     liEmpty.text = "";
/*     */     
/* 222 */     int size = 0; int i;
/* 223 */     for (i = 0; i < this.rowsAll.length; i++) {
/* 224 */       switch (filter) {
/*     */         case 1:
/* 226 */           size++;
/*     */           break;
/*     */         
/*     */         case 2:
/* 230 */           if ((this.rowsAll[i]).item.scCount > 0 || (this.rowsAll[i]).item.hcCount > 0)
/*     */           {
/* 232 */             size++;
/*     */           }
/*     */           break;
/*     */         
/*     */         case 3:
/* 237 */           if ((this.rowsAll[i]).item.scCount == 0 && (this.rowsAll[i]).item.hcCount == 0)
/*     */           {
/* 239 */             size++;
/*     */           }
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 245 */     if (size == 0) {
/* 246 */       this.rows = null;
/*     */     } else {
/* 248 */       this.rows = new GDItemCollectionRow[size];
/*     */       
/* 250 */       int pos = 0; int j;
/* 251 */       for (j = 0; j < this.rowsAll.length; j++) {
/* 252 */         boolean addItem = false;
/* 253 */         switch (filter) {
/*     */           case 1:
/* 255 */             addItem = true;
/*     */             break;
/*     */           
/*     */           case 2:
/* 259 */             if ((this.rowsAll[j]).item.scCount > 0 || (this.rowsAll[j]).item.hcCount > 0)
/*     */             {
/* 261 */               addItem = true;
/*     */             }
/*     */             break;
/*     */ 
/*     */           
/*     */           case 3:
/* 267 */             if ((this.rowsAll[j]).item.scCount == 0 && (this.rowsAll[j]).item.hcCount == 0)
/*     */             {
/* 269 */               addItem = true;
/*     */             }
/*     */             break;
/*     */         } 
/*     */ 
/*     */         
/* 275 */         if (addItem) {
/* 276 */           this.rows[pos] = this.rowsAll[j];
/*     */           
/* 278 */           pos++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 283 */     fireTableDataChanged();
/*     */   }
/*     */   
/*     */   public void sort(Comparator<GDItemCollectionRow> comp) {
/* 287 */     if (comp == null)
/* 288 */       return;  if (this.rows == null)
/*     */       return; 
/* 290 */     Arrays.sort(this.rows, comp);
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\table\GDItemCollectionTableModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */