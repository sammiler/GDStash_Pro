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
/*     */ public class GDItemNameSorter
/*     */   extends TableRowSorter<GDItemNameTableModel>
/*     */ {
/*     */   private static final int SORTORDER_DEFAULT = 0;
/*     */   private static final int SORTORDER_BASE = 1;
/*     */   private static final int SORTORDER_BASE_REV = 2;
/*     */   private static final int SORTORDER_NAME = 3;
/*     */   private static final int SORTORDER_NAME_REV = 4;
/*     */   private static final int SORTORDER_LEVEL = 5;
/*     */   private static final int SORTORDER_LEVEL_REV = 6;
/*  23 */   private static final Comparator<GDItemNameRow> COMP_BASE = new BaseComparator();
/*  24 */   private static final Comparator<GDItemNameRow> COMP_BASE_REV = new BaseReverseComparator();
/*  25 */   private static final Comparator<GDItemNameRow> COMP_NAME = new NameComparator();
/*  26 */   private static final Comparator<GDItemNameRow> COMP_NAME_REV = new NameReverseComparator();
/*  27 */   private static final Comparator<GDItemNameRow> COMP_LEVEL = new LevelComparator();
/*  28 */   private static final Comparator<GDItemNameRow> COMP_LEVEL_REV = new LevelReverseComparator();
/*     */   
/*     */   private int sortOrder;
/*     */   
/*     */   public GDItemNameSorter() {
/*  33 */     this((GDItemNameTableModel)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GDItemNameSorter(GDItemNameTableModel model) {
/*  39 */     setModel(model);
/*     */   }
/*     */   
/*     */   private static class BaseComparator implements Comparator<GDItemNameRow> {
/*     */     public int compare(GDItemNameRow ir1, GDItemNameRow ir2) {
/*  44 */       int iBase = ir1.baseName.compareTo(ir2.baseName);
/*     */       
/*  46 */       if (iBase != 0) return iBase;
/*     */       
/*  48 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/*  49 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/*  51 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/*  53 */       if (iLevel != 0) return iLevel;
/*     */       
/*  55 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/*  57 */       if (iName != 0) return iName;
/*     */       
/*  59 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/*  60 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/*  62 */       return 0;
/*     */     }
/*     */     private BaseComparator() {} }
/*     */   private static class BaseReverseComparator implements Comparator<GDItemNameRow> { private BaseReverseComparator() {}
/*     */     
/*     */     public int compare(GDItemNameRow ir1, GDItemNameRow ir2) {
/*  68 */       int iBase = ir1.baseName.compareTo(ir2.baseName);
/*     */       
/*  70 */       if (iBase != 0) return -iBase;
/*     */       
/*  72 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/*  73 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/*  75 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/*  77 */       if (iLevel != 0) return iLevel;
/*     */       
/*  79 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/*  81 */       if (iName != 0) return iName;
/*     */       
/*  83 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/*  84 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/*  86 */       return 0;
/*     */     } }
/*     */   
/*     */   private static class NameComparator implements Comparator<GDItemNameRow> { private NameComparator() {}
/*     */     
/*     */     public int compare(GDItemNameRow ir1, GDItemNameRow ir2) {
/*  92 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/*  94 */       if (iName != 0) return iName;
/*     */       
/*  96 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/*  97 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/*  99 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 101 */       if (iLevel != 0) return iLevel;
/*     */       
/* 103 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/* 104 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/* 106 */       return 0;
/*     */     } }
/*     */   
/*     */   private static class NameReverseComparator implements Comparator<GDItemNameRow> { private NameReverseComparator() {}
/*     */     
/*     */     public int compare(GDItemNameRow ir1, GDItemNameRow ir2) {
/* 112 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 114 */       if (iName != 0) return -iName;
/*     */       
/* 116 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 117 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 119 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 121 */       if (iLevel != 0) return iLevel;
/*     */       
/* 123 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/* 124 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/* 126 */       return 0;
/*     */     } }
/*     */   
/*     */   private static class LevelComparator implements Comparator<GDItemNameRow> { private LevelComparator() {}
/*     */     
/*     */     public int compare(GDItemNameRow ir1, GDItemNameRow ir2) {
/* 132 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 133 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 135 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 137 */       if (iLevel != 0) return iLevel;
/*     */       
/* 139 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 141 */       if (iName != 0) return iName;
/*     */       
/* 143 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/* 144 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/* 146 */       return 0;
/*     */     } }
/*     */   
/*     */   private static class LevelReverseComparator implements Comparator<GDItemNameRow> { private LevelReverseComparator() {}
/*     */     
/*     */     public int compare(GDItemNameRow ir1, GDItemNameRow ir2) {
/* 152 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 153 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 155 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 157 */       if (iLevel != 0) return -iLevel;
/*     */       
/* 159 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 161 */       if (iName != 0) return iName;
/*     */       
/* 163 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/* 164 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/* 166 */       return 0;
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModel(GDItemNameTableModel m) {
/* 172 */     super.setModel(m);
/*     */     
/* 174 */     if (m != null) {
/* 175 */       setSortable(0, false);
/*     */       
/* 177 */       this.sortOrder = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void toggleSortOrder(int column) {
/* 183 */     if (column == 0) {
/* 184 */       if (this.sortOrder == 1) {
/* 185 */         this.sortOrder = 2;
/*     */       } else {
/* 187 */         this.sortOrder = 1;
/*     */       } 
/*     */     }
/*     */     
/* 191 */     if (column == 1) {
/* 192 */       if (this.sortOrder == 3) {
/* 193 */         this.sortOrder = 4;
/*     */       } else {
/* 195 */         this.sortOrder = 3;
/*     */       } 
/*     */     }
/*     */     
/* 199 */     if (column == 2) {
/* 200 */       if (this.sortOrder == 5) {
/* 201 */         this.sortOrder = 6;
/*     */       } else {
/* 203 */         this.sortOrder = 5;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void sort() {
/* 210 */     GDItemNameTableModel model = getModel();
/*     */     
/* 212 */     if (this.sortOrder == 1) {
/* 213 */       model.sort(COMP_BASE);
/*     */     }
/*     */     
/* 216 */     if (this.sortOrder == 2) {
/* 217 */       model.sort(COMP_BASE_REV);
/*     */     }
/*     */     
/* 220 */     if (this.sortOrder == 3) {
/* 221 */       model.sort(COMP_NAME);
/*     */     }
/*     */     
/* 224 */     if (this.sortOrder == 4) {
/* 225 */       model.sort(COMP_NAME_REV);
/*     */     }
/*     */     
/* 228 */     if (this.sortOrder == 5) {
/* 229 */       model.sort(COMP_LEVEL);
/*     */     }
/*     */     
/* 232 */     if (this.sortOrder == 6)
/* 233 */       model.sort(COMP_LEVEL_REV); 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\table\GDItemNameSorter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */