/*     */ package org.gdstash.ui.table;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.LinkedList;
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
/*     */ public class GDMassImportTableModel
/*     */   extends DefaultTableModel
/*     */ {
/*     */   private List<GDItem> data;
/*     */   public GDMassImportRow[] rows;
/*     */   private Comparator<GDMassImportRow> comparator;
/*     */   
/*     */   public GDMassImportTableModel(List<GDItem> data) {
/*  30 */     adjustUI();
/*     */     
/*  32 */     setData(data);
/*     */   }
/*     */   
/*     */   public void adjustUI() {
/*  36 */     GDMassImportRow.updateColumnNames();
/*     */     
/*  38 */     setColumnIdentifiers((Object[])GDMassImportRow.columnNames);
/*     */     
/*  40 */     setData(this.data);
/*     */   }
/*     */   
/*     */   public void setData(List<GDItem> data) {
/*  44 */     this.data = data;
/*     */     
/*  46 */     this.rows = null;
/*     */     
/*  48 */     if (data == null) {
/*  49 */       fireTableDataChanged();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  54 */     int size = data.size();
/*     */     
/*  56 */     this.rows = new GDMassImportRow[size];
/*     */     
/*  58 */     GDItem.LabelInfo liEmpty = new GDItem.LabelInfo();
/*  59 */     liEmpty.text = "";
/*     */     
/*  61 */     int i = 0;
/*  62 */     GDItem.LabelInfo li = null;
/*     */     
/*  64 */     for (GDItem item : data) {
/*  65 */       this.rows[i] = new GDMassImportRow();
/*     */       
/*  67 */       (this.rows[i]).item = item;
/*  68 */       (this.rows[i]).selected = false;
/*  69 */       (this.rows[i]).baseName = item.getBaseName();
/*     */       
/*  71 */       (this.rows[i]).valid = item.isValid(GDStashFrame.iniConfig.sectRestrict.affixCombi, GDStashFrame.iniConfig.sectRestrict.completionAll);
/*  72 */       if ((this.rows[i]).valid) {
/*  73 */         (this.rows[i]).validIcon = GDImagePool.iconBtnOk24;
/*     */       } else {
/*  75 */         (this.rows[i]).validIcon = GDImagePool.iconBtnCancel24;
/*     */       } 
/*     */       
/*  78 */       BufferedImage img = item.getImage();
/*  79 */       if (img != null) {
/*  80 */         if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/*  81 */           int w = img.getWidth() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*  82 */           int h = img.getHeight() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*  83 */           img = DDSLoader.getScaledImage(img, w, h);
/*     */         } 
/*     */         
/*  86 */         (this.rows[i]).icon = new ImageIcon(img);
/*     */       } 
/*     */       
/*  89 */       li = item.getCompleteNameInfo(false, false);
/*  90 */       if (li == null) li = liEmpty; 
/*  91 */       (this.rows[i]).name = li;
/*     */       
/*  93 */       li = item.getRequiredLevelInfo();
/*  94 */       if (li == null) li = liEmpty; 
/*  95 */       (this.rows[i]).level = li;
/*     */       
/*  97 */       li = item.getPrefixInfo();
/*  98 */       if (li == null) li = liEmpty; 
/*  99 */       (this.rows[i]).prefix = li;
/*     */       
/* 101 */       li = item.getSuffixInfo();
/* 102 */       if (li == null) li = liEmpty; 
/* 103 */       (this.rows[i]).suffix = li;
/*     */       
/* 105 */       li = item.getModifierInfo();
/* 106 */       if (li == null) li = liEmpty; 
/* 107 */       (this.rows[i]).modifier = li;
/*     */       
/* 109 */       li = item.getComponentInfo();
/* 110 */       if (li == null) li = liEmpty; 
/* 111 */       (this.rows[i]).component = li;
/*     */       
/* 113 */       li = item.getComponentBonusInfo();
/* 114 */       if (li == null) li = liEmpty; 
/* 115 */       (this.rows[i]).bonus = li;
/*     */       
/* 117 */       li = item.getEnchantmentInfo();
/* 118 */       if (li == null) li = liEmpty; 
/* 119 */       (this.rows[i]).enchantment = li;
/*     */       
/* 121 */       li = item.getSeedHexInfo();
/* 122 */       if (li == null) li = liEmpty; 
/* 123 */       (this.rows[i]).seed = li;
/*     */       
/* 125 */       li = item.getCharNameInfo();
/* 126 */       if (li == null) li = liEmpty; 
/* 127 */       (this.rows[i]).charName = li;
/*     */       
/* 129 */       (this.rows[i]).hardcore = item.isHardcore();
/* 130 */       if ((this.rows[i]).hardcore) {
/* 131 */         (this.rows[i]).hcIcon = GDImagePool.iconBtnOk24;
/*     */       } else {
/* 133 */         (this.rows[i]).hcIcon = GDImagePool.iconBtnCancel24;
/*     */       } 
/*     */       
/* 136 */       i++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowCount() {
/* 142 */     if (this.rows == null) return 0;
/*     */     
/* 144 */     return this.rows.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/* 149 */     return GDMassImportRow.columnNames.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getColumnName(int column) {
/* 154 */     return GDMassImportRow.columnNames[column];
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValueAt(int row, int column) {
/* 159 */     if (this.rows == null) return null; 
/* 160 */     if (row < 0) return null; 
/* 161 */     if (row >= this.rows.length) return null; 
/* 162 */     if (this.rows[row] == null) return null;
/*     */     
/* 164 */     if (column == 0) return Boolean.valueOf((this.rows[row]).selected); 
/* 165 */     if (column == 1) return (this.rows[row]).validIcon; 
/* 166 */     if (column == 2) return (this.rows[row]).icon; 
/* 167 */     if (column == 3) return (this.rows[row]).name.text; 
/* 168 */     if (column == 4) return (this.rows[row]).level.text; 
/* 169 */     if (column == 5) return (this.rows[row]).prefix.text; 
/* 170 */     if (column == 6) return (this.rows[row]).suffix.text; 
/* 171 */     if (column == 7) return (this.rows[row]).modifier.text; 
/* 172 */     if (column == 8) return (this.rows[row]).component.text; 
/* 173 */     if (column == 9) return (this.rows[row]).bonus.text; 
/* 174 */     if (column == 10) return (this.rows[row]).enchantment.text; 
/* 175 */     if (column == 11) return (this.rows[row]).seed.text; 
/* 176 */     if (column == 12) return (this.rows[row]).charName.text; 
/* 177 */     if (column == 13) return (this.rows[row]).hcIcon;
/*     */     
/* 179 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCellEditable(int row, int column) {
/* 184 */     return (column == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValueAt(Object value, int row, int column) {
/* 189 */     if (!isCellEditable(row, column))
/*     */       return; 
/* 191 */     if (this.rows == null)
/* 192 */       return;  if (row < 0)
/* 193 */       return;  if (row >= this.rows.length)
/* 194 */       return;  if (this.rows[row] == null)
/*     */       return; 
/* 196 */     if (column == 0) (this.rows[row]).selected = ((Boolean)value).booleanValue(); 
/* 197 */     if (column == 1) (this.rows[row]).validIcon = (ImageIcon)value; 
/* 198 */     if (column == 2) (this.rows[row]).icon = (ImageIcon)value; 
/* 199 */     if (column == 3) (this.rows[row]).name.text = (String)value; 
/* 200 */     if (column == 4) (this.rows[row]).level.text = (String)value; 
/* 201 */     if (column == 5) (this.rows[row]).prefix.text = (String)value; 
/* 202 */     if (column == 6) (this.rows[row]).suffix.text = (String)value; 
/* 203 */     if (column == 7) (this.rows[row]).modifier.text = (String)value; 
/* 204 */     if (column == 8) (this.rows[row]).component.text = (String)value; 
/* 205 */     if (column == 9) (this.rows[row]).bonus.text = (String)value; 
/* 206 */     if (column == 10) (this.rows[row]).enchantment.text = (String)value; 
/* 207 */     if (column == 11) (this.rows[row]).seed.text = (String)value; 
/* 208 */     if (column == 12) {
/* 209 */       if (value == null) {
/* 210 */         (this.rows[row]).charName.text = "";
/*     */       } else {
/* 212 */         (this.rows[row]).charName.text = (String)value;
/*     */       } 
/*     */     }
/* 215 */     if (column == 13) (this.rows[row]).hcIcon = (ImageIcon)value;
/*     */   
/*     */   }
/*     */   
/*     */   public Class getColumnClass(int column) {
/* 220 */     return GDMassImportRow.COLUMN_CLASSES[column];
/*     */   }
/*     */   
/*     */   public String getCellText(int row, int column) {
/* 224 */     if (this.rows == null) return ""; 
/* 225 */     if (row < 0) return ""; 
/* 226 */     if (row >= this.rows.length) return ""; 
/* 227 */     if (this.rows[row] == null) return "";
/*     */     
/* 229 */     if (column == 3) return (this.rows[row]).name.text; 
/* 230 */     if (column == 4) return (this.rows[row]).level.text; 
/* 231 */     if (column == 5) return (this.rows[row]).prefix.text; 
/* 232 */     if (column == 6) return (this.rows[row]).suffix.text; 
/* 233 */     if (column == 7) return (this.rows[row]).modifier.text; 
/* 234 */     if (column == 8) return (this.rows[row]).component.text; 
/* 235 */     if (column == 9) return (this.rows[row]).bonus.text; 
/* 236 */     if (column == 10) return (this.rows[row]).enchantment.text; 
/* 237 */     if (column == 11) return (this.rows[row]).seed.text; 
/* 238 */     if (column == 12) return (this.rows[row]).charName.text;
/*     */     
/* 240 */     return "";
/*     */   }
/*     */   
/*     */   public Color getCellTextColor(int row, int column) {
/* 244 */     if (this.rows == null) return Color.BLACK; 
/* 245 */     if (row < 0) return Color.BLACK; 
/* 246 */     if (row >= this.rows.length) return Color.BLACK; 
/* 247 */     if (this.rows[row] == null) return Color.BLACK;
/*     */     
/* 249 */     if (column == 3) return (this.rows[row]).name.foreground; 
/* 250 */     if (column == 4) return (this.rows[row]).level.foreground; 
/* 251 */     if (column == 5) return (this.rows[row]).prefix.foreground; 
/* 252 */     if (column == 6) return (this.rows[row]).suffix.foreground; 
/* 253 */     if (column == 7) return (this.rows[row]).modifier.foreground; 
/* 254 */     if (column == 8) return (this.rows[row]).component.foreground; 
/* 255 */     if (column == 9) return (this.rows[row]).bonus.foreground; 
/* 256 */     if (column == 10) return (this.rows[row]).enchantment.foreground; 
/* 257 */     if (column == 11) return (this.rows[row]).seed.foreground; 
/* 258 */     if (column == 12) return (this.rows[row]).charName.foreground;
/*     */     
/* 260 */     return Color.BLACK;
/*     */   }
/*     */   
/*     */   public void setSelectAll(boolean selected) {
/* 264 */     if (this.rows == null)
/*     */       return; 
/* 266 */     for (int i = 0; i < this.rows.length; i++) {
/* 267 */       (this.rows[i]).selected = selected;
/*     */     }
/*     */     
/* 270 */     fireTableDataChanged();
/*     */   }
/*     */   
/*     */   public void selectValidOnly() {
/* 274 */     if (this.rows == null)
/*     */       return; 
/* 276 */     for (int i = 0; i < this.rows.length; i++) {
/* 277 */       (this.rows[i]).selected = (this.rows[i]).valid;
/*     */     }
/*     */     
/* 280 */     fireTableDataChanged();
/*     */   }
/*     */   
/*     */   public List<GDItem> getSelectedItems() {
/* 284 */     List<GDItem> list = new LinkedList<>();
/*     */     
/* 286 */     if (this.rows == null) return list;
/*     */     
/* 288 */     for (int i = 0; i < this.rows.length; i++) {
/* 289 */       if ((this.rows[i]).selected) {
/* 290 */         list.add((this.rows[i]).item);
/*     */       }
/*     */     } 
/*     */     
/* 294 */     return list;
/*     */   }
/*     */   
/*     */   public void sort(Comparator<GDMassImportRow> comp) {
/* 298 */     if (comp == null)
/*     */       return; 
/* 300 */     this.comparator = comp;
/*     */     
/* 302 */     if (this.rows == null)
/*     */       return; 
/* 304 */     Arrays.sort(this.rows, this.comparator);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\table\GDMassImportTableModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */