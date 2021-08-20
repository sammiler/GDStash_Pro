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
/*     */ public class GDItemSetCollectionSorter
/*     */   extends TableRowSorter<GDItemSetCollectionTableModel>
/*     */ {
/*     */   private static final int SORTORDER_DEFAULT = 0;
/*     */   private static final int SORTORDER_NAME = 1;
/*     */   private static final int SORTORDER_NAME_REV = 2;
/*     */   private static final int SORTORDER_LEVEL = 3;
/*     */   private static final int SORTORDER_LEVEL_REV = 4;
/*     */   private static final int SORTORDER_RARITY = 5;
/*     */   private static final int SORTORDER_RARITY_REV = 6;
/*  23 */   private static final Comparator<GDItemSetCollectionRow> COMP_NAME = new NameComparator();
/*  24 */   private static final Comparator<GDItemSetCollectionRow> COMP_NAME_REV = new NameReverseComparator();
/*  25 */   private static final Comparator<GDItemSetCollectionRow> COMP_LEVEL = new LevelComparator();
/*  26 */   private static final Comparator<GDItemSetCollectionRow> COMP_LEVEL_REV = new LevelReverseComparator();
/*  27 */   private static final Comparator<GDItemSetCollectionRow> COMP_RARITY = new RarityComparator();
/*  28 */   private static final Comparator<GDItemSetCollectionRow> COMP_RARITY_REV = new RarityReverseComparator();
/*     */   
/*     */   private int sortOrder;
/*     */   
/*     */   public GDItemSetCollectionSorter() {
/*  33 */     this((GDItemSetCollectionTableModel)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GDItemSetCollectionSorter(GDItemSetCollectionTableModel model) {
/*  39 */     setModel(model);
/*     */   }
/*     */   
/*     */   public static class NameComparator implements Comparator<GDItemSetCollectionRow> {
/*     */     public int compare(GDItemSetCollectionRow ir1, GDItemSetCollectionRow ir2) {
/*  44 */       int iName = ir1.setName.text.compareTo(ir2.setName.text);
/*     */       
/*  46 */       if (iName != 0) return iName;
/*     */       
/*  48 */       return ir1.level - ir2.level;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class NameReverseComparator implements Comparator<GDItemSetCollectionRow> {
/*     */     public int compare(GDItemSetCollectionRow ir1, GDItemSetCollectionRow ir2) {
/*  54 */       int iName = ir1.setName.text.compareTo(ir2.setName.text);
/*     */       
/*  56 */       if (iName != 0) return -iName;
/*     */       
/*  58 */       return ir1.level - ir2.level;
/*     */     }
/*     */     
/*     */     private NameReverseComparator() {} }
/*     */   
/*     */   public static class LevelComparator implements Comparator<GDItemSetCollectionRow> { public int compare(GDItemSetCollectionRow ir1, GDItemSetCollectionRow ir2) {
/*  64 */       int iLevel = ir1.level - ir2.level;
/*     */       
/*  66 */       if (iLevel != 0) return iLevel;
/*     */       
/*  68 */       return ir1.setName.text.compareTo(ir2.setName.text);
/*     */     } }
/*     */   
/*     */   private static class LevelReverseComparator implements Comparator<GDItemSetCollectionRow> { private LevelReverseComparator() {}
/*     */     
/*     */     public int compare(GDItemSetCollectionRow ir1, GDItemSetCollectionRow ir2) {
/*  74 */       int iLevel = ir1.level - ir2.level;
/*     */       
/*  76 */       if (iLevel != 0) return -iLevel;
/*     */       
/*  78 */       return ir1.setName.text.compareTo(ir2.setName.text);
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class RarityComparator implements Comparator<GDItemSetCollectionRow> {
/*     */     public int compare(GDItemSetCollectionRow ir1, GDItemSetCollectionRow ir2) {
/*  84 */       int iRarity = ir1.rarity - ir2.rarity;
/*     */       
/*  86 */       if (iRarity != 0) return iRarity;
/*     */       
/*  88 */       int iName = ir1.setName.text.compareTo(ir2.setName.text);
/*     */       
/*  90 */       if (iName != 0) return iName;
/*     */       
/*  92 */       return ir1.level - ir2.level;
/*     */     } }
/*     */   
/*     */   private static class RarityReverseComparator implements Comparator<GDItemSetCollectionRow> { private RarityReverseComparator() {}
/*     */     
/*     */     public int compare(GDItemSetCollectionRow ir1, GDItemSetCollectionRow ir2) {
/*  98 */       int iRarity = ir1.rarity - ir2.rarity;
/*     */       
/* 100 */       if (iRarity != 0) return -iRarity;
/*     */       
/* 102 */       int iName = ir1.setName.text.compareTo(ir2.setName.text);
/*     */       
/* 104 */       if (iName != 0) return iName;
/*     */       
/* 106 */       return ir1.level - ir2.level;
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModel(GDItemSetCollectionTableModel m) {
/* 112 */     super.setModel(m);
/*     */     
/* 114 */     if (m != null) {
/* 115 */       setSortable(0, false);
/*     */       
/* 117 */       this.sortOrder = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void toggleSortOrder(int column) {
/* 123 */     if (column == 0) {
/* 124 */       if (this.sortOrder == 1) {
/* 125 */         this.sortOrder = 2;
/*     */       } else {
/* 127 */         this.sortOrder = 1;
/*     */       } 
/*     */     }
/*     */     
/* 131 */     if (column == 1) {
/* 132 */       if (this.sortOrder == 3) {
/* 133 */         this.sortOrder = 4;
/*     */       } else {
/* 135 */         this.sortOrder = 3;
/*     */       } 
/*     */     }
/*     */     
/* 139 */     if (column == 2) {
/* 140 */       if (this.sortOrder == 5) {
/* 141 */         this.sortOrder = 6;
/*     */       } else {
/* 143 */         this.sortOrder = 5;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void sort() {
/* 150 */     GDItemSetCollectionTableModel model = getModel();
/*     */     
/* 152 */     if (this.sortOrder == 1) {
/* 153 */       model.sort(COMP_NAME);
/*     */     }
/* 155 */     if (this.sortOrder == 2) {
/* 156 */       model.sort(COMP_NAME_REV);
/*     */     }
/*     */     
/* 159 */     if (this.sortOrder == 3) {
/* 160 */       model.sort(COMP_LEVEL);
/*     */     }
/* 162 */     if (this.sortOrder == 4) {
/* 163 */       model.sort(COMP_LEVEL_REV);
/*     */     }
/*     */     
/* 166 */     if (this.sortOrder == 5) {
/* 167 */       model.sort(COMP_RARITY);
/*     */     }
/* 169 */     if (this.sortOrder == 6)
/* 170 */       model.sort(COMP_RARITY_REV); 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\table\GDItemSetCollectionSorter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */