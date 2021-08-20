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
/*     */ 
/*     */ public class GDMassImportSorter
/*     */   extends TableRowSorter<GDMassImportTableModel>
/*     */ {
/*     */   private static final int SORTORDER_DEFAULT = 0;
/*     */   private static final int SORTORDER_BASE = 1;
/*     */   private static final int SORTORDER_BASE_REV = 2;
/*     */   private static final int SORTORDER_SELECT = 3;
/*     */   private static final int SORTORDER_SELECT_REV = 4;
/*     */   private static final int SORTORDER_VALID = 5;
/*     */   private static final int SORTORDER_VALID_REV = 6;
/*     */   private static final int SORTORDER_ITEMNAME = 7;
/*     */   private static final int SORTORDER_ITEMNAME_REV = 8;
/*     */   private static final int SORTORDER_LEVEL = 9;
/*     */   private static final int SORTORDER_LEVEL_REV = 10;
/*     */   private static final int SORTORDER_CHARNAME = 11;
/*     */   private static final int SORTORDER_CHARNAME_REV = 12;
/*     */   private static final int SORTORDER_SC_HC = 13;
/*     */   private static final int SORTORDER_SC_HC_REV = 14;
/*  32 */   private static final Comparator<GDMassImportRow> COMP_BASE = new BaseComparator();
/*  33 */   private static final Comparator<GDMassImportRow> COMP_BASE_REV = new BaseReverseComparator();
/*  34 */   private static final Comparator<GDMassImportRow> COMP_SELECT = new SelectComparator();
/*  35 */   private static final Comparator<GDMassImportRow> COMP_SELECT_REV = new SelectReverseComparator();
/*  36 */   private static final Comparator<GDMassImportRow> COMP_VALID = new ValidComparator();
/*  37 */   private static final Comparator<GDMassImportRow> COMP_VALID_REV = new ValidReverseComparator();
/*  38 */   private static final Comparator<GDMassImportRow> COMP_ITEMNAME = new ItemNameComparator();
/*  39 */   private static final Comparator<GDMassImportRow> COMP_ITEMNAME_REV = new ItemNameReverseComparator();
/*  40 */   private static final Comparator<GDMassImportRow> COMP_LEVEL = new LevelComparator();
/*  41 */   private static final Comparator<GDMassImportRow> COMP_LEVEL_REV = new LevelReverseComparator();
/*  42 */   private static final Comparator<GDMassImportRow> COMP_CHARNAME = new CharNameComparator();
/*  43 */   private static final Comparator<GDMassImportRow> COMP_CHARNAME_REV = new CharNameReverseComparator();
/*  44 */   private static final Comparator<GDMassImportRow> COMP_SC_HC = new SCHCComparator();
/*  45 */   private static final Comparator<GDMassImportRow> COMP_SC_HC_REV = new SCHCReverseComparator();
/*     */   
/*     */   private int sortOrder;
/*     */   
/*     */   public GDMassImportSorter() {
/*  50 */     this((GDMassImportTableModel)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GDMassImportSorter(GDMassImportTableModel model) {
/*  56 */     setModel(model);
/*     */   }
/*     */   
/*     */   public static class BaseComparator implements Comparator<GDMassImportRow> {
/*     */     public int compare(GDMassImportRow ir1, GDMassImportRow ir2) {
/*  61 */       int iBase = ir1.baseName.compareTo(ir2.baseName);
/*     */       
/*  63 */       if (iBase != 0) return iBase;
/*     */       
/*  65 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/*  66 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/*  68 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/*  70 */       if (iLevel != 0) return iLevel;
/*     */       
/*  72 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/*  74 */       if (iName != 0) return iName;
/*     */       
/*  76 */       int iCharName = ir1.charName.text.compareTo(ir2.charName.text);
/*     */       
/*  78 */       if (iCharName != 0) return iCharName;
/*     */       
/*  80 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/*  81 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/*  83 */       return 0;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class BaseReverseComparator implements Comparator<GDMassImportRow> {
/*     */     public int compare(GDMassImportRow ir1, GDMassImportRow ir2) {
/*  89 */       int iBase = ir1.baseName.compareTo(ir2.baseName);
/*     */       
/*  91 */       if (iBase != 0) return -iBase;
/*     */       
/*  93 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/*  94 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/*  96 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/*  98 */       if (iLevel != 0) return iLevel;
/*     */       
/* 100 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 102 */       if (iName != 0) return iName;
/*     */       
/* 104 */       int iCharName = ir1.charName.text.compareTo(ir2.charName.text);
/*     */       
/* 106 */       if (iCharName != 0) return iCharName;
/*     */       
/* 108 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/* 109 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/* 111 */       return 0;
/*     */     }
/*     */     
/*     */     private BaseReverseComparator() {} }
/*     */   
/*     */   public static class SelectComparator implements Comparator<GDMassImportRow> { public int compare(GDMassImportRow ir1, GDMassImportRow ir2) {
/* 117 */       int iSelected = 0;
/*     */       
/* 119 */       if (ir1.selected != ir2.selected) {
/* 120 */         if (ir1.selected) {
/* 121 */           iSelected = -1;
/*     */         } else {
/* 123 */           iSelected = 1;
/*     */         } 
/*     */       }
/*     */       
/* 127 */       if (iSelected != 0) return iSelected;
/*     */       
/* 129 */       int iBase = ir1.baseName.compareTo(ir2.baseName);
/*     */       
/* 131 */       if (iBase != 0) return iBase;
/*     */       
/* 133 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 134 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 136 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 138 */       if (iLevel != 0) return iLevel;
/*     */       
/* 140 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 142 */       if (iName != 0) return iName;
/*     */       
/* 144 */       int iCharName = ir1.charName.text.compareTo(ir2.charName.text);
/*     */       
/* 146 */       if (iCharName != 0) return iCharName;
/*     */       
/* 148 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/* 149 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/* 151 */       return 0;
/*     */     } }
/*     */   
/*     */   private static class SelectReverseComparator implements Comparator<GDMassImportRow> { private SelectReverseComparator() {}
/*     */     
/*     */     public int compare(GDMassImportRow ir1, GDMassImportRow ir2) {
/* 157 */       int iSelected = 0;
/*     */       
/* 159 */       if (ir1.selected != ir2.selected) {
/* 160 */         if (ir1.selected) {
/* 161 */           iSelected = -1;
/*     */         } else {
/* 163 */           iSelected = 1;
/*     */         } 
/*     */       }
/*     */       
/* 167 */       if (iSelected != 0) return -iSelected;
/*     */       
/* 169 */       int iBase = ir1.baseName.compareTo(ir2.baseName);
/*     */       
/* 171 */       if (iBase != 0) return iBase;
/*     */       
/* 173 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 174 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 176 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 178 */       if (iLevel != 0) return iLevel;
/*     */       
/* 180 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 182 */       if (iName != 0) return iName;
/*     */       
/* 184 */       int iCharName = ir1.charName.text.compareTo(ir2.charName.text);
/*     */       
/* 186 */       if (iCharName != 0) return iCharName;
/*     */       
/* 188 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/* 189 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/* 191 */       return 0;
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class ValidComparator implements Comparator<GDMassImportRow> {
/*     */     public int compare(GDMassImportRow ir1, GDMassImportRow ir2) {
/* 197 */       int iValid = 0;
/*     */       
/* 199 */       if (ir1.valid != ir2.valid) {
/* 200 */         if (ir1.valid) {
/* 201 */           iValid = -1;
/*     */         } else {
/* 203 */           iValid = 1;
/*     */         } 
/*     */       }
/*     */       
/* 207 */       if (iValid != 0) return iValid;
/*     */       
/* 209 */       int iBase = ir1.baseName.compareTo(ir2.baseName);
/*     */       
/* 211 */       if (iBase != 0) return iBase;
/*     */       
/* 213 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 214 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 216 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 218 */       if (iLevel != 0) return iLevel;
/*     */       
/* 220 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 222 */       if (iName != 0) return iName;
/*     */       
/* 224 */       int iCharName = ir1.charName.text.compareTo(ir2.charName.text);
/*     */       
/* 226 */       if (iCharName != 0) return iCharName;
/*     */       
/* 228 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/* 229 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/* 231 */       return 0;
/*     */     } }
/*     */   
/*     */   private static class ValidReverseComparator implements Comparator<GDMassImportRow> { private ValidReverseComparator() {}
/*     */     
/*     */     public int compare(GDMassImportRow ir1, GDMassImportRow ir2) {
/* 237 */       int iValid = 0;
/*     */       
/* 239 */       if (ir1.valid != ir2.valid) {
/* 240 */         if (ir1.valid) {
/* 241 */           iValid = -1;
/*     */         } else {
/* 243 */           iValid = 1;
/*     */         } 
/*     */       }
/*     */       
/* 247 */       if (iValid != 0) return -iValid;
/*     */       
/* 249 */       int iBase = ir1.baseName.compareTo(ir2.baseName);
/*     */       
/* 251 */       if (iBase != 0) return iBase;
/*     */       
/* 253 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 254 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 256 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 258 */       if (iLevel != 0) return iLevel;
/*     */       
/* 260 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 262 */       if (iName != 0) return iName;
/*     */       
/* 264 */       int iCharName = ir1.charName.text.compareTo(ir2.charName.text);
/*     */       
/* 266 */       if (iCharName != 0) return iCharName;
/*     */       
/* 268 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/* 269 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/* 271 */       return 0;
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class ItemNameComparator implements Comparator<GDMassImportRow> {
/*     */     public int compare(GDMassImportRow ir1, GDMassImportRow ir2) {
/* 277 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 279 */       if (iName != 0) return iName;
/*     */       
/* 281 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 282 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 284 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 286 */       if (iLevel != 0) return iLevel;
/*     */       
/* 288 */       int iCharName = ir1.charName.text.compareTo(ir2.charName.text);
/*     */       
/* 290 */       if (iCharName != 0) return iCharName;
/*     */       
/* 292 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/* 293 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/* 295 */       return 0;
/*     */     } }
/*     */   
/*     */   private static class ItemNameReverseComparator implements Comparator<GDMassImportRow> { private ItemNameReverseComparator() {}
/*     */     
/*     */     public int compare(GDMassImportRow ir1, GDMassImportRow ir2) {
/* 301 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 303 */       if (iName != 0) return -iName;
/*     */       
/* 305 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 306 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 308 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 310 */       if (iLevel != 0) return iLevel;
/*     */       
/* 312 */       int iCharName = ir1.charName.text.compareTo(ir2.charName.text);
/*     */       
/* 314 */       if (iCharName != 0) return iCharName;
/*     */       
/* 316 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/* 317 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/* 319 */       return 0;
/*     */     } }
/*     */   
/*     */   private static class LevelComparator implements Comparator<GDMassImportRow> { private LevelComparator() {}
/*     */     
/*     */     public int compare(GDMassImportRow ir1, GDMassImportRow ir2) {
/* 325 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 326 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 328 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 330 */       if (iLevel != 0) return iLevel;
/*     */       
/* 332 */       int iBase = ir1.baseName.compareTo(ir2.baseName);
/*     */       
/* 334 */       if (iBase != 0) return iBase;
/*     */       
/* 336 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 338 */       if (iName != 0) return iName;
/*     */       
/* 340 */       int iCharName = ir1.charName.text.compareTo(ir2.charName.text);
/*     */       
/* 342 */       if (iCharName != 0) return iCharName;
/*     */       
/* 344 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/* 345 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/* 347 */       return 0;
/*     */     } }
/*     */   
/*     */   private static class LevelReverseComparator implements Comparator<GDMassImportRow> { private LevelReverseComparator() {}
/*     */     
/*     */     public int compare(GDMassImportRow ir1, GDMassImportRow ir2) {
/* 353 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 354 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 356 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 358 */       if (iLevel != 0) return -iLevel;
/*     */       
/* 360 */       int iBase = ir1.baseName.compareTo(ir2.baseName);
/*     */       
/* 362 */       if (iBase != 0) return iBase;
/*     */       
/* 364 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 366 */       if (iName != 0) return iName;
/*     */       
/* 368 */       int iCharName = ir1.charName.text.compareTo(ir2.charName.text);
/*     */       
/* 370 */       if (iCharName != 0) return iCharName;
/*     */       
/* 372 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/* 373 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/* 375 */       return 0;
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class CharNameComparator implements Comparator<GDMassImportRow> {
/*     */     public int compare(GDMassImportRow ir1, GDMassImportRow ir2) {
/* 381 */       int iCharName = ir1.charName.text.compareTo(ir2.charName.text);
/*     */       
/* 383 */       if (iCharName != 0) return iCharName;
/*     */       
/* 385 */       int iBase = ir1.baseName.compareTo(ir2.baseName);
/*     */       
/* 387 */       if (iBase != 0) return iBase;
/*     */       
/* 389 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 390 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 392 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 394 */       if (iLevel != 0) return iLevel;
/*     */       
/* 396 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 398 */       if (iName != 0) return iName;
/*     */       
/* 400 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/* 401 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/* 403 */       return 0;
/*     */     } }
/*     */   
/*     */   private static class CharNameReverseComparator implements Comparator<GDMassImportRow> { private CharNameReverseComparator() {}
/*     */     
/*     */     public int compare(GDMassImportRow ir1, GDMassImportRow ir2) {
/* 409 */       int iCharName = ir1.charName.text.compareTo(ir2.charName.text);
/*     */       
/* 411 */       if (iCharName != 0) return -iCharName;
/*     */       
/* 413 */       int iBase = ir1.baseName.compareTo(ir2.baseName);
/*     */       
/* 415 */       if (iBase != 0) return iBase;
/*     */       
/* 417 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 418 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 420 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 422 */       if (iLevel != 0) return iLevel;
/*     */       
/* 424 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 426 */       if (iName != 0) return iName;
/*     */       
/* 428 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/* 429 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/* 431 */       return 0;
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class SCHCComparator implements Comparator<GDMassImportRow> {
/*     */     public int compare(GDMassImportRow ir1, GDMassImportRow ir2) {
/* 437 */       int iSCHC = 0;
/*     */       
/* 439 */       if (ir1.hardcore != ir2.hardcore) {
/* 440 */         if (ir1.hardcore) {
/* 441 */           iSCHC = 1;
/*     */         } else {
/* 443 */           iSCHC = -1;
/*     */         } 
/*     */       }
/*     */       
/* 447 */       if (iSCHC != 0) return iSCHC;
/*     */       
/* 449 */       int iCharName = ir1.charName.text.compareTo(ir2.charName.text);
/*     */       
/* 451 */       if (iCharName != 0) return iCharName;
/*     */       
/* 453 */       int iBase = ir1.baseName.compareTo(ir2.baseName);
/*     */       
/* 455 */       if (iBase != 0) return iBase;
/*     */       
/* 457 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 458 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 460 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 462 */       if (iLevel != 0) return iLevel;
/*     */       
/* 464 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 466 */       if (iName != 0) return iName;
/*     */       
/* 468 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/* 469 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/* 471 */       return 0;
/*     */     } }
/*     */   
/*     */   private static class SCHCReverseComparator implements Comparator<GDMassImportRow> { private SCHCReverseComparator() {}
/*     */     
/*     */     public int compare(GDMassImportRow ir1, GDMassImportRow ir2) {
/* 477 */       int iSCHC = 0;
/*     */       
/* 479 */       if (ir1.hardcore != ir2.hardcore) {
/* 480 */         if (ir1.hardcore) {
/* 481 */           iSCHC = 1;
/*     */         } else {
/* 483 */           iSCHC = -1;
/*     */         } 
/*     */       }
/*     */       
/* 487 */       if (iSCHC != 0) return -iSCHC;
/*     */       
/* 489 */       int iCharName = ir1.charName.text.compareTo(ir2.charName.text);
/*     */       
/* 491 */       if (iCharName != 0) return iCharName;
/*     */       
/* 493 */       int iBase = ir1.baseName.compareTo(ir2.baseName);
/*     */       
/* 495 */       if (iBase != 0) return iBase;
/*     */       
/* 497 */       int iLvl1 = Integer.parseInt(ir1.level.text);
/* 498 */       int iLvl2 = Integer.parseInt(ir2.level.text);
/*     */       
/* 500 */       int iLevel = iLvl1 - iLvl2;
/*     */       
/* 502 */       if (iLevel != 0) return iLevel;
/*     */       
/* 504 */       int iName = ir1.name.text.compareTo(ir2.name.text);
/*     */       
/* 506 */       if (iName != 0) return iName;
/*     */       
/* 508 */       if (ir1.item.getStashID() < ir2.item.getStashID()) return -1; 
/* 509 */       if (ir1.item.getStashID() > ir2.item.getStashID()) return 1;
/*     */       
/* 511 */       return 0;
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModel(GDMassImportTableModel m) {
/* 517 */     super.setModel(m);
/*     */     
/* 519 */     if (m != null) {
/* 520 */       setSortable(0, false);
/*     */       
/* 522 */       this.sortOrder = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void toggleSortOrder(int column) {
/* 528 */     if (column == 0) {
/* 529 */       if (this.sortOrder == 3) {
/* 530 */         this.sortOrder = 4;
/*     */       } else {
/* 532 */         this.sortOrder = 3;
/*     */       } 
/*     */     }
/*     */     
/* 536 */     if (column == 1) {
/* 537 */       if (this.sortOrder == 5) {
/* 538 */         this.sortOrder = 6;
/*     */       } else {
/* 540 */         this.sortOrder = 5;
/*     */       } 
/*     */     }
/*     */     
/* 544 */     if (column == 2) {
/* 545 */       if (this.sortOrder == 1) {
/* 546 */         this.sortOrder = 2;
/*     */       } else {
/* 548 */         this.sortOrder = 1;
/*     */       } 
/*     */     }
/*     */     
/* 552 */     if (column == 3) {
/* 553 */       if (this.sortOrder == 7) {
/* 554 */         this.sortOrder = 8;
/*     */       } else {
/* 556 */         this.sortOrder = 7;
/*     */       } 
/*     */     }
/*     */     
/* 560 */     if (column == 4) {
/* 561 */       if (this.sortOrder == 9) {
/* 562 */         this.sortOrder = 10;
/*     */       } else {
/* 564 */         this.sortOrder = 9;
/*     */       } 
/*     */     }
/*     */     
/* 568 */     if (column == 12) {
/* 569 */       if (this.sortOrder == 11) {
/* 570 */         this.sortOrder = 12;
/*     */       } else {
/* 572 */         this.sortOrder = 11;
/*     */       } 
/*     */     }
/*     */     
/* 576 */     if (column == 13) {
/* 577 */       if (this.sortOrder == 13) {
/* 578 */         this.sortOrder = 14;
/*     */       } else {
/* 580 */         this.sortOrder = 13;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void sort() {
/* 587 */     GDMassImportTableModel model = getModel();
/*     */     
/* 589 */     if (this.sortOrder == 1) {
/* 590 */       model.sort(COMP_BASE);
/*     */     }
/*     */     
/* 593 */     if (this.sortOrder == 2) {
/* 594 */       model.sort(COMP_BASE_REV);
/*     */     }
/*     */     
/* 597 */     if (this.sortOrder == 3) {
/* 598 */       model.sort(COMP_SELECT);
/*     */     }
/*     */     
/* 601 */     if (this.sortOrder == 4) {
/* 602 */       model.sort(COMP_SELECT_REV);
/*     */     }
/*     */     
/* 605 */     if (this.sortOrder == 5) {
/* 606 */       model.sort(COMP_VALID);
/*     */     }
/*     */     
/* 609 */     if (this.sortOrder == 6) {
/* 610 */       model.sort(COMP_VALID_REV);
/*     */     }
/*     */     
/* 613 */     if (this.sortOrder == 7) {
/* 614 */       model.sort(COMP_ITEMNAME);
/*     */     }
/*     */     
/* 617 */     if (this.sortOrder == 8) {
/* 618 */       model.sort(COMP_ITEMNAME_REV);
/*     */     }
/*     */     
/* 621 */     if (this.sortOrder == 9) {
/* 622 */       model.sort(COMP_LEVEL);
/*     */     }
/*     */     
/* 625 */     if (this.sortOrder == 10) {
/* 626 */       model.sort(COMP_LEVEL_REV);
/*     */     }
/*     */     
/* 629 */     if (this.sortOrder == 11) {
/* 630 */       model.sort(COMP_CHARNAME);
/*     */     }
/*     */     
/* 633 */     if (this.sortOrder == 12) {
/* 634 */       model.sort(COMP_CHARNAME_REV);
/*     */     }
/*     */     
/* 637 */     if (this.sortOrder == 13) {
/* 638 */       model.sort(COMP_SC_HC);
/*     */     }
/*     */     
/* 641 */     if (this.sortOrder == 14)
/* 642 */       model.sort(COMP_SC_HC_REV); 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\table\GDMassImportSorter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */