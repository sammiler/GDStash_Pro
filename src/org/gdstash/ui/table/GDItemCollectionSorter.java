/*     */ package org.gdstash.ui.table;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import javax.swing.table.TableModel;
/*     */ import javax.swing.table.TableRowSorter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDItemCollectionSorter
/*     */   extends TableRowSorter<GDItemCollectionTableModel>
/*     */ {
/*     */   private static final int SORTORDER_DEFAULT = 0;
/*     */   private static final int SORTORDER_BASE = 1;
/*     */   private static final int SORTORDER_BASE_REV = 2;
/*     */   private static final int SORTORDER_NAME = 3;
/*     */   private static final int SORTORDER_NAME_REV = 4;
/*     */   private static final int SORTORDER_LEVEL = 5;
/*     */   private static final int SORTORDER_LEVEL_REV = 6;
/*     */   private static final int SORTORDER_SC = 7;
/*     */   private static final int SORTORDER_SC_REV = 8;
/*     */   private static final int SORTORDER_HC = 9;
/*     */   private static final int SORTORDER_HC_REV = 10;
/*  27 */   private static final Comparator<GDItemCollectionRow> COMP_BASE = new BaseComparator();
/*  28 */   private static final Comparator<GDItemCollectionRow> COMP_BASE_REV = new BaseReverseComparator();
/*  29 */   private static final Comparator<GDItemCollectionRow> COMP_NAME = new NameComparator();
/*  30 */   private static final Comparator<GDItemCollectionRow> COMP_NAME_REV = new NameReverseComparator();
/*  31 */   private static final Comparator<GDItemCollectionRow> COMP_LEVEL = new LevelComparator();
/*  32 */   private static final Comparator<GDItemCollectionRow> COMP_LEVEL_REV = new LevelReverseComparator();
/*  33 */   private static final Comparator<GDItemCollectionRow> COMP_SC = new SoftcoreComparator();
/*  34 */   private static final Comparator<GDItemCollectionRow> COMP_SC_REV = new SoftcoreReverseComparator();
/*  35 */   private static final Comparator<GDItemCollectionRow> COMP_HC = new HardcoreComparator();
/*  36 */   private static final Comparator<GDItemCollectionRow> COMP_HC_REV = new HardcoreReverseComparator();
/*     */   
/*     */   private int sortOrder;
/*     */   
/*     */   public GDItemCollectionSorter() {
/*  41 */     this((GDItemCollectionTableModel)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GDItemCollectionSorter(GDItemCollectionTableModel model) {
/*  47 */     setModel(model);
/*     */   }
/*     */   
/*     */   public static class BaseComparator implements Comparator<GDItemCollectionRow> {
/*     */     public int compare(GDItemCollectionRow ir1, GDItemCollectionRow ir2) {
/*  52 */       int iBase = ir1.baseName.compareTo(ir2.baseName);
/*     */       
/*  54 */       if (iBase != 0) return iBase;
/*     */       
/*  56 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/*  57 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/*  59 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/*  61 */       if (iLevel != 0) return iLevel;
/*     */       
/*  63 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/*  65 */       if (iName != 0) return iName;
/*     */       
/*  67 */       return 0;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class BaseReverseComparator implements Comparator<GDItemCollectionRow> {
/*     */     public int compare(GDItemCollectionRow ir1, GDItemCollectionRow ir2) {
/*  73 */       int iBase = ir1.baseName.compareTo(ir2.baseName);
/*     */       
/*  75 */       if (iBase != 0) return -iBase;
/*     */       
/*  77 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/*  78 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/*  80 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/*  82 */       if (iLevel != 0) return iLevel;
/*     */       
/*  84 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/*  86 */       if (iName != 0) return iName;
/*     */       
/*  88 */       return 0;
/*     */     }
/*     */     
/*     */     private BaseReverseComparator() {} }
/*     */   
/*     */   public static class NameComparator implements Comparator<GDItemCollectionRow> { public int compare(GDItemCollectionRow ir1, GDItemCollectionRow ir2) {
/*  94 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/*  96 */       if (iName != 0) return iName;
/*     */       
/*  98 */       return ir1.level.text.compareTo(ir2.level.text);
/*     */     } }
/*     */   
/*     */   private static class NameReverseComparator implements Comparator<GDItemCollectionRow> { private NameReverseComparator() {}
/*     */     
/*     */     public int compare(GDItemCollectionRow ir1, GDItemCollectionRow ir2) {
/* 104 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 106 */       if (iName != 0) return -iName;
/*     */       
/* 108 */       return ir1.level.text.compareTo(ir2.level.text);
/*     */     } }
/*     */   
/*     */   private static class LevelComparator implements Comparator<GDItemCollectionRow> { private LevelComparator() {}
/*     */     
/*     */     public int compare(GDItemCollectionRow ir1, GDItemCollectionRow ir2) {
/* 114 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 115 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 117 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 119 */       if (iLevel != 0) return iLevel;
/*     */       
/* 121 */       return ir1.name.text.compareTo(ir2.name.text);
/*     */     } }
/*     */   
/*     */   private static class LevelReverseComparator implements Comparator<GDItemCollectionRow> { private LevelReverseComparator() {}
/*     */     
/*     */     public int compare(GDItemCollectionRow ir1, GDItemCollectionRow ir2) {
/* 127 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 128 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 130 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 132 */       if (iLevel != 0) return -iLevel;
/*     */       
/* 134 */       return ir1.name.text.compareTo(ir2.name.text);
/*     */     } }
/*     */   
/*     */   private static class SoftcoreComparator implements Comparator<GDItemCollectionRow> { private SoftcoreComparator() {}
/*     */     
/*     */     public int compare(GDItemCollectionRow ir1, GDItemCollectionRow ir2) {
/* 140 */       int iFound = 0;
/*     */       
/* 142 */       if (ir1.softcore != ir2.softcore) {
/* 143 */         if (ir1.softcore) {
/* 144 */           iFound = -1;
/*     */         } else {
/* 146 */           iFound = 1;
/*     */         } 
/*     */       }
/*     */       
/* 150 */       if (iFound != 0) return iFound;
/*     */       
/* 152 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 154 */       if (iName != 0) return iName;
/*     */       
/* 156 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 157 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 159 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 161 */       if (iLevel != 0) return iLevel;
/*     */       
/* 163 */       return 0;
/*     */     } }
/*     */   
/*     */   private static class SoftcoreReverseComparator implements Comparator<GDItemCollectionRow> { private SoftcoreReverseComparator() {}
/*     */     
/*     */     public int compare(GDItemCollectionRow ir1, GDItemCollectionRow ir2) {
/* 169 */       int iFound = 0;
/*     */       
/* 171 */       if (ir1.softcore != ir2.softcore) {
/* 172 */         if (ir1.softcore) {
/* 173 */           iFound = -1;
/*     */         } else {
/* 175 */           iFound = 1;
/*     */         } 
/*     */       }
/*     */       
/* 179 */       if (iFound != 0) return -iFound;
/*     */       
/* 181 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 183 */       if (iName != 0) return iName;
/*     */       
/* 185 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 186 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 188 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 190 */       if (iLevel != 0) return iLevel;
/*     */       
/* 192 */       return 0;
/*     */     } }
/*     */   
/*     */   private static class HardcoreComparator implements Comparator<GDItemCollectionRow> { private HardcoreComparator() {}
/*     */     
/*     */     public int compare(GDItemCollectionRow ir1, GDItemCollectionRow ir2) {
/* 198 */       int iFound = 0;
/*     */       
/* 200 */       if (ir1.hardcore != ir2.hardcore) {
/* 201 */         if (ir1.hardcore) {
/* 202 */           iFound = -1;
/*     */         } else {
/* 204 */           iFound = 1;
/*     */         } 
/*     */       }
/*     */       
/* 208 */       if (iFound != 0) return iFound;
/*     */       
/* 210 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 212 */       if (iName != 0) return iName;
/*     */       
/* 214 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 215 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 217 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 219 */       if (iLevel != 0) return iLevel;
/*     */       
/* 221 */       return 0;
/*     */     } }
/*     */   
/*     */   private static class HardcoreReverseComparator implements Comparator<GDItemCollectionRow> { private HardcoreReverseComparator() {}
/*     */     
/*     */     public int compare(GDItemCollectionRow ir1, GDItemCollectionRow ir2) {
/* 227 */       int iFound = 0;
/*     */       
/* 229 */       if (ir1.hardcore != ir2.hardcore) {
/* 230 */         if (ir1.hardcore) {
/* 231 */           iFound = -1;
/*     */         } else {
/* 233 */           iFound = 1;
/*     */         } 
/*     */       }
/*     */       
/* 237 */       if (iFound != 0) return -iFound;
/*     */       
/* 239 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 241 */       if (iName != 0) return iName;
/*     */       
/* 243 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 244 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 246 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 248 */       if (iLevel != 0) return iLevel;
/*     */       
/* 250 */       return 0;
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModel(GDItemCollectionTableModel m) {
/* 256 */     super.setModel(m);
/*     */     
/* 258 */     if (m != null) {
/* 259 */       setSortable(0, false);
/*     */       
/* 261 */       this.sortOrder = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void toggleSortOrder(int column) {
/* 267 */     if (column == 0) {
/* 268 */       if (this.sortOrder == 1) {
/* 269 */         this.sortOrder = 2;
/*     */       } else {
/* 271 */         this.sortOrder = 1;
/*     */       } 
/*     */     }
/*     */     
/* 275 */     if (column == 1) {
/* 276 */       if (this.sortOrder == 3) {
/* 277 */         this.sortOrder = 4;
/*     */       } else {
/* 279 */         this.sortOrder = 3;
/*     */       } 
/*     */     }
/*     */     
/* 283 */     if (column == 2) {
/* 284 */       if (this.sortOrder == 5) {
/* 285 */         this.sortOrder = 6;
/*     */       } else {
/* 287 */         this.sortOrder = 5;
/*     */       } 
/*     */     }
/*     */     
/* 291 */     if (column == 3) {
/* 292 */       if (this.sortOrder == 7) {
/* 293 */         this.sortOrder = 8;
/*     */       } else {
/* 295 */         this.sortOrder = 7;
/*     */       } 
/*     */     }
/*     */     
/* 299 */     if (column == 4) {
/* 300 */       if (this.sortOrder == 9) {
/* 301 */         this.sortOrder = 10;
/*     */       } else {
/* 303 */         this.sortOrder = 9;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void sort() {
/* 310 */     GDItemCollectionTableModel model = getModel();
/*     */     
/* 312 */     if (this.sortOrder == 1) {
/* 313 */       model.sort(COMP_BASE);
/*     */     }
/*     */     
/* 316 */     if (this.sortOrder == 2) {
/* 317 */       model.sort(COMP_BASE_REV);
/*     */     }
/*     */     
/* 320 */     if (this.sortOrder == 3) {
/* 321 */       model.sort(COMP_NAME);
/*     */     }
/*     */     
/* 324 */     if (this.sortOrder == 4) {
/* 325 */       model.sort(COMP_NAME_REV);
/*     */     }
/*     */     
/* 328 */     if (this.sortOrder == 5) {
/* 329 */       model.sort(COMP_LEVEL);
/*     */     }
/*     */     
/* 332 */     if (this.sortOrder == 6) {
/* 333 */       model.sort(COMP_LEVEL_REV);
/*     */     }
/*     */     
/* 336 */     if (this.sortOrder == 7) {
/* 337 */       model.sort(COMP_SC);
/*     */     }
/*     */     
/* 340 */     if (this.sortOrder == 8) {
/* 341 */       model.sort(COMP_SC_REV);
/*     */     }
/*     */     
/* 344 */     if (this.sortOrder == 9) {
/* 345 */       model.sort(COMP_HC);
/*     */     }
/*     */     
/* 348 */     if (this.sortOrder == 10)
/* 349 */       model.sort(COMP_HC_REV); 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\table\GDItemCollectionSorter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */