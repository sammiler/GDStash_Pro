/*     */ package org.gdstash.ui.table;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.UIManager;
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
/*     */ public class GDItemSetCollectionTableModel
/*     */   extends DefaultTableModel
/*     */ {
/*     */   private List<GDItemSetInfo> data;
/*     */   private GDItemSetCollectionRow[] rowsAll;
/*     */   private GDItemSetCollectionRow[] rows;
/*     */   
/*     */   public GDItemSetCollectionTableModel(List<GDItemSetInfo> data) {
/*  29 */     this.data = data;
/*     */     
/*  31 */     adjustUI();
/*     */   }
/*     */   
/*     */   public void adjustUI() {
/*  35 */     GDItemCollectionRow.updateColumnNames();
/*     */     
/*  37 */     setColumnIdentifiers((Object[])GDItemCollectionRow.columnNames);
/*     */     
/*  39 */     setData(this.data);
/*     */   }
/*     */   
/*     */   public void setData(List<GDItemSetInfo> data) {
/*  43 */     this.data = data;
/*     */     
/*  45 */     this.rowsAll = null;
/*  46 */     this.rows = null;
/*     */     
/*  48 */     if (data == null)
/*     */       return; 
/*  50 */     int size = data.size();
/*     */     
/*  52 */     this.rowsAll = new GDItemSetCollectionRow[size];
/*  53 */     this.rows = new GDItemSetCollectionRow[size];
/*     */     
/*  55 */     GDItem.LabelInfo liEmpty = new GDItem.LabelInfo();
/*  56 */     liEmpty.text = "";
/*     */     
/*  58 */     int i = 0;
/*  59 */     GDItem.LabelInfo li = null;
/*     */     
/*  61 */     for (GDItemSetInfo set : data) {
/*  62 */       this.rowsAll[i] = new GDItemSetCollectionRow();
/*  63 */       this.rows[i] = this.rowsAll[i];
/*     */       
/*  65 */       (this.rowsAll[i]).set = set;
/*  66 */       (this.rows[i]).setName = set.getItemSetNameLabel();
/*  67 */       (this.rows[i]).setLevel = set.getItemSetLevelLabel();
/*  68 */       (this.rows[i]).setRarity = set.getItemSetRarityLabel();
/*  69 */       (this.rows[i]).level = set.getItemSetLevel();
/*  70 */       (this.rows[i]).rarity = set.getItemSetRarityInt();
/*  71 */       (this.rowsAll[i]).iconItem1 = null;
/*  72 */       (this.rowsAll[i]).scItem1 = -1;
/*  73 */       (this.rowsAll[i]).hcItem1 = -1;
/*  74 */       (this.rowsAll[i]).blueprint1 = false;
/*  75 */       (this.rowsAll[i]).iconItem2 = null;
/*  76 */       (this.rowsAll[i]).scItem2 = -1;
/*  77 */       (this.rowsAll[i]).hcItem2 = -1;
/*  78 */       (this.rowsAll[i]).blueprint2 = false;
/*  79 */       (this.rowsAll[i]).iconItem3 = null;
/*  80 */       (this.rowsAll[i]).scItem3 = -1;
/*  81 */       (this.rowsAll[i]).hcItem3 = -1;
/*  82 */       (this.rowsAll[i]).blueprint3 = false;
/*  83 */       (this.rowsAll[i]).iconItem4 = null;
/*  84 */       (this.rowsAll[i]).scItem4 = -1;
/*  85 */       (this.rowsAll[i]).hcItem4 = -1;
/*  86 */       (this.rowsAll[i]).blueprint4 = false;
/*  87 */       (this.rowsAll[i]).iconItem5 = null;
/*  88 */       (this.rowsAll[i]).scItem5 = -1;
/*  89 */       (this.rowsAll[i]).hcItem5 = -1;
/*  90 */       (this.rowsAll[i]).blueprint5 = false;
/*  91 */       (this.rowsAll[i]).iconItem6 = null;
/*  92 */       (this.rowsAll[i]).scItem6 = -1;
/*  93 */       (this.rowsAll[i]).hcItem6 = -1;
/*  94 */       (this.rowsAll[i]).blueprint6 = false;
/*     */       
/*  96 */       int count = 0;
/*  97 */       for (GDItemInfo info : set.getItemInfoList()) {
/*     */         
/*  99 */         BufferedImage img = info.getFullImage();
/*     */         
/* 101 */         if (img != null) {
/* 102 */           if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 103 */             int w = img.getWidth() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 104 */             int h = img.getHeight() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 105 */             img = DDSLoader.getScaledImage(img, w, h);
/*     */           } 
/*     */           
/* 108 */           if (count == 0) {
/* 109 */             (this.rowsAll[i]).iconItem1 = new ImageIcon(img);
/* 110 */             (this.rowsAll[i]).scItem1 = info.scCount;
/* 111 */             (this.rowsAll[i]).hcItem1 = info.hcCount;
/* 112 */             (this.rowsAll[i]).blueprint1 = (info.scBlueprint != null || info.hcBlueprint != null);
/*     */           } 
/* 114 */           if (count == 1) {
/* 115 */             (this.rowsAll[i]).iconItem2 = new ImageIcon(img);
/* 116 */             (this.rowsAll[i]).scItem2 = info.scCount;
/* 117 */             (this.rowsAll[i]).hcItem2 = info.hcCount;
/* 118 */             (this.rowsAll[i]).blueprint2 = (info.scBlueprint != null || info.hcBlueprint != null);
/*     */           } 
/* 120 */           if (count == 2) {
/* 121 */             (this.rowsAll[i]).iconItem3 = new ImageIcon(img);
/* 122 */             (this.rowsAll[i]).scItem3 = info.scCount;
/* 123 */             (this.rowsAll[i]).hcItem3 = info.hcCount;
/* 124 */             (this.rowsAll[i]).blueprint3 = (info.scBlueprint != null || info.hcBlueprint != null);
/*     */           } 
/* 126 */           if (count == 3) {
/* 127 */             (this.rowsAll[i]).iconItem4 = new ImageIcon(img);
/* 128 */             (this.rowsAll[i]).scItem4 = info.scCount;
/* 129 */             (this.rowsAll[i]).hcItem4 = info.hcCount;
/* 130 */             (this.rowsAll[i]).blueprint4 = (info.scBlueprint != null || info.hcBlueprint != null);
/*     */           } 
/* 132 */           if (count == 4) {
/* 133 */             (this.rowsAll[i]).iconItem5 = new ImageIcon(img);
/* 134 */             (this.rowsAll[i]).scItem5 = info.scCount;
/* 135 */             (this.rowsAll[i]).hcItem5 = info.hcCount;
/* 136 */             (this.rowsAll[i]).blueprint5 = (info.scBlueprint != null || info.hcBlueprint != null);
/*     */           } 
/* 138 */           if (count == 5) {
/* 139 */             (this.rowsAll[i]).iconItem6 = new ImageIcon(img);
/* 140 */             (this.rowsAll[i]).scItem6 = info.scCount;
/* 141 */             (this.rowsAll[i]).hcItem6 = info.hcCount;
/* 142 */             (this.rowsAll[i]).blueprint6 = (info.scBlueprint != null || info.hcBlueprint != null);
/*     */           } 
/*     */         } 
/*     */         
/* 146 */         count++;
/*     */       } 
/*     */       
/* 149 */       i++;
/*     */     } 
/*     */     
/* 152 */     fireTableDataChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowCount() {
/* 157 */     if (this.rows == null) return 0;
/*     */     
/* 159 */     return this.rows.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/* 164 */     return GDItemSetCollectionRow.columnNames.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getColumnName(int column) {
/* 169 */     return GDItemSetCollectionRow.columnNames[column];
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValueAt(int row, int column) {
/* 174 */     if (this.rows == null) return null; 
/* 175 */     if (this.rows[row] == null) return null;
/*     */     
/* 177 */     if (column == 0) return (this.rows[row]).setName.text; 
/* 178 */     if (column == 1) return (this.rows[row]).setLevel.text; 
/* 179 */     if (column == 2) return (this.rows[row]).setRarity.text; 
/* 180 */     if (column == 3) return (this.rows[row]).iconItem1; 
/* 181 */     if (column == 4) return (this.rows[row]).iconItem2; 
/* 182 */     if (column == 5) return (this.rows[row]).iconItem3; 
/* 183 */     if (column == 6) return (this.rows[row]).iconItem4; 
/* 184 */     if (column == 7) return (this.rows[row]).iconItem5; 
/* 185 */     if (column == 8) return (this.rows[row]).iconItem6;
/*     */     
/* 187 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCellEditable(int row, int column) {
/* 192 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValueAt(Object value, int row, int column) {
/* 197 */     if (!isCellEditable(row, column))
/*     */       return; 
/* 199 */     if (column == 0) (this.rows[row]).setName.text = (String)value; 
/* 200 */     if (column == 1) (this.rows[row]).setLevel.text = (String)value; 
/* 201 */     if (column == 2) (this.rows[row]).setRarity.text = (String)value; 
/* 202 */     if (column == 3) (this.rows[row]).iconItem1 = (ImageIcon)value; 
/* 203 */     if (column == 4) (this.rows[row]).iconItem2 = (ImageIcon)value; 
/* 204 */     if (column == 5) (this.rows[row]).iconItem3 = (ImageIcon)value; 
/* 205 */     if (column == 6) (this.rows[row]).iconItem4 = (ImageIcon)value; 
/* 206 */     if (column == 7) (this.rows[row]).iconItem5 = (ImageIcon)value; 
/* 207 */     if (column == 8) (this.rows[row]).iconItem6 = (ImageIcon)value;
/*     */   
/*     */   }
/*     */   
/*     */   public Class getColumnClass(int column) {
/* 212 */     return GDItemSetCollectionRow.COLUMN_CLASSES[column];
/*     */   }
/*     */   
/*     */   public String getCellText(int row, int column) {
/* 216 */     if (this.rows == null) return null; 
/* 217 */     if (this.rows[row] == null) return null;
/*     */     
/* 219 */     if (column == 0) return (this.rows[row]).setName.text; 
/* 220 */     if (column == 1) return (this.rows[row]).setLevel.text; 
/* 221 */     if (column == 2) return (this.rows[row]).setRarity.text;
/*     */     
/* 223 */     return "";
/*     */   }
/*     */   
/*     */   public Color getCellTextColor(int row, int column) {
/* 227 */     if (this.rows == null) return null; 
/* 228 */     if (this.rows[row] == null) return null;
/*     */     
/* 230 */     if (column == 0) return (this.rows[row]).setName.foreground; 
/* 231 */     if (column == 1) return (this.rows[row]).setLevel.foreground; 
/* 232 */     if (column == 2) return (this.rows[row]).setRarity.foreground;
/*     */     
/* 234 */     return Color.BLACK;
/*     */   }
/*     */   
/*     */   public Color getCellBackgroundColor(int row, int column) {
/* 238 */     if (this.rows == null) return null; 
/* 239 */     if (this.rows[row] == null) return null;
/*     */     
/* 241 */     if (column == 0) return (this.rows[row]).setName.background; 
/* 242 */     if (column == 1) return (this.rows[row]).setLevel.background; 
/* 243 */     if (column == 2) return (this.rows[row]).setRarity.background; 
/* 244 */     if (column == 3) {
/* 245 */       if ((this.rows[row]).scItem1 > 0 || (this.rows[row]).hcItem1 > 0) {
/* 246 */         return GDItemInfo.COLOR_GREEN;
/*     */       }
/* 248 */       if ((this.rows[row]).scItem1 == -1) {
/* 249 */         return UIManager.getColor("Table.background");
/*     */       }
/* 251 */       if ((this.rows[row]).blueprint1) {
/* 252 */         return GDItemInfo.COLOR_YELLOW;
/*     */       }
/* 254 */       return GDItemInfo.COLOR_RED;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 259 */     if (column == 4) {
/* 260 */       if ((this.rows[row]).scItem2 > 0 || (this.rows[row]).hcItem2 > 0) {
/* 261 */         return GDItemInfo.COLOR_GREEN;
/*     */       }
/* 263 */       if ((this.rows[row]).scItem2 == -1) {
/* 264 */         return UIManager.getColor("Table.background");
/*     */       }
/* 266 */       if ((this.rows[row]).blueprint2) {
/* 267 */         return GDItemInfo.COLOR_YELLOW;
/*     */       }
/* 269 */       return GDItemInfo.COLOR_RED;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 274 */     if (column == 5) {
/* 275 */       if ((this.rows[row]).scItem3 > 0 || (this.rows[row]).hcItem3 > 0) {
/* 276 */         return GDItemInfo.COLOR_GREEN;
/*     */       }
/* 278 */       if ((this.rows[row]).scItem3 == -1) {
/* 279 */         return UIManager.getColor("Table.background");
/*     */       }
/* 281 */       if ((this.rows[row]).blueprint3) {
/* 282 */         return GDItemInfo.COLOR_YELLOW;
/*     */       }
/* 284 */       return GDItemInfo.COLOR_RED;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 289 */     if (column == 6) {
/* 290 */       if ((this.rows[row]).scItem4 > 0 || (this.rows[row]).hcItem4 > 0) {
/* 291 */         return GDItemInfo.COLOR_GREEN;
/*     */       }
/* 293 */       if ((this.rows[row]).scItem4 == -1) {
/* 294 */         return UIManager.getColor("Table.background");
/*     */       }
/* 296 */       if ((this.rows[row]).blueprint4) {
/* 297 */         return GDItemInfo.COLOR_YELLOW;
/*     */       }
/* 299 */       return GDItemInfo.COLOR_RED;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 304 */     if (column == 7) {
/* 305 */       if ((this.rows[row]).scItem5 > 0 || (this.rows[row]).hcItem5 > 0) {
/* 306 */         return GDItemInfo.COLOR_GREEN;
/*     */       }
/* 308 */       if ((this.rows[row]).scItem5 == -1) {
/* 309 */         return UIManager.getColor("Table.background");
/*     */       }
/* 311 */       if ((this.rows[row]).blueprint5) {
/* 312 */         return GDItemInfo.COLOR_YELLOW;
/*     */       }
/* 314 */       return GDItemInfo.COLOR_RED;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 319 */     if (column == 8) {
/* 320 */       if ((this.rows[row]).scItem6 > 0 || (this.rows[row]).hcItem6 > 0) {
/* 321 */         return GDItemInfo.COLOR_GREEN;
/*     */       }
/* 323 */       if ((this.rows[row]).scItem6 == -1) {
/* 324 */         return UIManager.getColor("Table.background");
/*     */       }
/* 326 */       if ((this.rows[row]).blueprint6) {
/* 327 */         return GDItemInfo.COLOR_YELLOW;
/*     */       }
/* 329 */       return GDItemInfo.COLOR_RED;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 335 */     return Color.WHITE;
/*     */   }
/*     */   
/*     */   public void sort(Comparator<GDItemSetCollectionRow> comp) {
/* 339 */     if (comp == null)
/* 340 */       return;  if (this.rows == null)
/*     */       return; 
/* 342 */     Arrays.sort(this.rows, comp);
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\table\GDItemSetCollectionTableModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */