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
/*     */     
/* 161 */     if (column == 0) return Boolean.valueOf((this.rows[row]).selected); 
/* 162 */     if (column == 1) return (this.rows[row]).validIcon; 
/* 163 */     if (column == 2) return (this.rows[row]).icon; 
/* 164 */     if (column == 3) return (this.rows[row]).name.text; 
/* 165 */     if (column == 4) return (this.rows[row]).level.text; 
/* 166 */     if (column == 5) return (this.rows[row]).prefix.text; 
/* 167 */     if (column == 6) return (this.rows[row]).suffix.text; 
/* 168 */     if (column == 7) return (this.rows[row]).modifier.text; 
/* 169 */     if (column == 8) return (this.rows[row]).component.text; 
/* 170 */     if (column == 9) return (this.rows[row]).bonus.text; 
/* 171 */     if (column == 10) return (this.rows[row]).enchantment.text; 
/* 172 */     if (column == 11) return (this.rows[row]).seed.text; 
/* 173 */     if (column == 12) return (this.rows[row]).charName.text; 
/* 174 */     if (column == 13) return (this.rows[row]).hcIcon;
/*     */     
/* 176 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCellEditable(int row, int column) {
/* 181 */     return (column == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValueAt(Object value, int row, int column) {
/* 186 */     if (!isCellEditable(row, column))
/*     */       return; 
/* 188 */     if (column == 0) (this.rows[row]).selected = ((Boolean)value).booleanValue(); 
/* 189 */     if (column == 1) (this.rows[row]).validIcon = (ImageIcon)value; 
/* 190 */     if (column == 2) (this.rows[row]).icon = (ImageIcon)value; 
/* 191 */     if (column == 3) (this.rows[row]).name.text = (String)value; 
/* 192 */     if (column == 4) (this.rows[row]).level.text = (String)value; 
/* 193 */     if (column == 5) (this.rows[row]).prefix.text = (String)value; 
/* 194 */     if (column == 6) (this.rows[row]).suffix.text = (String)value; 
/* 195 */     if (column == 7) (this.rows[row]).modifier.text = (String)value; 
/* 196 */     if (column == 8) (this.rows[row]).component.text = (String)value; 
/* 197 */     if (column == 9) (this.rows[row]).bonus.text = (String)value; 
/* 198 */     if (column == 10) (this.rows[row]).enchantment.text = (String)value; 
/* 199 */     if (column == 11) (this.rows[row]).seed.text = (String)value; 
/* 200 */     if (column == 12) {
/* 201 */       if (value == null) {
/* 202 */         (this.rows[row]).charName.text = "";
/*     */       } else {
/* 204 */         (this.rows[row]).charName.text = (String)value;
/*     */       } 
/*     */     }
/* 207 */     if (column == 13) (this.rows[row]).hcIcon = (ImageIcon)value;
/*     */   
/*     */   }
/*     */   
/*     */   public Class getColumnClass(int column) {
/* 212 */     return GDMassImportRow.COLUMN_CLASSES[column];
/*     */   }
/*     */   
/*     */   public String getCellText(int row, int column) {
/* 216 */     if (column == 3) return (this.rows[row]).name.text; 
/* 217 */     if (column == 4) return (this.rows[row]).level.text; 
/* 218 */     if (column == 5) return (this.rows[row]).prefix.text; 
/* 219 */     if (column == 6) return (this.rows[row]).suffix.text; 
/* 220 */     if (column == 7) return (this.rows[row]).modifier.text; 
/* 221 */     if (column == 8) return (this.rows[row]).component.text; 
/* 222 */     if (column == 9) return (this.rows[row]).bonus.text; 
/* 223 */     if (column == 10) return (this.rows[row]).enchantment.text; 
/* 224 */     if (column == 11) return (this.rows[row]).seed.text; 
/* 225 */     if (column == 12) return (this.rows[row]).charName.text;
/*     */     
/* 227 */     return "";
/*     */   }
/*     */   
/*     */   public Color getCellTextColor(int row, int column) {
/* 231 */     if (column == 3) return (this.rows[row]).name.foreground; 
/* 232 */     if (column == 4) return (this.rows[row]).level.foreground; 
/* 233 */     if (column == 5) return (this.rows[row]).prefix.foreground; 
/* 234 */     if (column == 6) return (this.rows[row]).suffix.foreground; 
/* 235 */     if (column == 7) return (this.rows[row]).modifier.foreground; 
/* 236 */     if (column == 8) return (this.rows[row]).component.foreground; 
/* 237 */     if (column == 9) return (this.rows[row]).bonus.foreground; 
/* 238 */     if (column == 10) return (this.rows[row]).enchantment.foreground; 
/* 239 */     if (column == 11) return (this.rows[row]).seed.foreground; 
/* 240 */     if (column == 12) return (this.rows[row]).charName.foreground;
/*     */     
/* 242 */     return Color.BLACK;
/*     */   }
/*     */   
/*     */   public void setSelectAll(boolean selected) {
/* 246 */     if (this.rows == null)
/*     */       return; 
/* 248 */     for (int i = 0; i < this.rows.length; i++) {
/* 249 */       (this.rows[i]).selected = selected;
/*     */     }
/*     */     
/* 252 */     fireTableDataChanged();
/*     */   }
/*     */   
/*     */   public void selectValidOnly() {
/* 256 */     if (this.rows == null)
/*     */       return; 
/* 258 */     for (int i = 0; i < this.rows.length; i++) {
/* 259 */       (this.rows[i]).selected = (this.rows[i]).valid;
/*     */     }
/*     */     
/* 262 */     fireTableDataChanged();
/*     */   }
/*     */   
/*     */   public List<GDItem> getSelectedItems() {
/* 266 */     List<GDItem> list = new LinkedList<>();
/*     */     
/* 268 */     if (this.rows == null) return list;
/*     */     
/* 270 */     for (int i = 0; i < this.rows.length; i++) {
/* 271 */       if ((this.rows[i]).selected) {
/* 272 */         list.add((this.rows[i]).item);
/*     */       }
/*     */     } 
/*     */     
/* 276 */     return list;
/*     */   }
/*     */   
/*     */   public void sort(Comparator<GDMassImportRow> comp) {
/* 280 */     if (comp == null)
/*     */       return; 
/* 282 */     this.comparator = comp;
/*     */     
/* 284 */     if (this.rows == null)
/*     */       return; 
/* 286 */     Arrays.sort(this.rows, this.comparator);
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\table\GDMassImportTableModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */