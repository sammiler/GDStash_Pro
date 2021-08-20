/*    */ package org.gdstash.ui.table;
/*    */ 
/*    */ import org.gdstash.db.DBStashItem;
/*    */ import org.gdstash.item.GDItem;
/*    */ import org.gdstash.util.GDMsgFormatter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GDItemSimilarRow
/*    */ {
/* 16 */   public static final Class[] COLUMN_CLASSES = new Class[] { String.class, String.class, String.class, String.class, String.class, String.class };
/*    */   
/* 18 */   public static String[] columnNames = null;
/*    */   
/*    */   public DBStashItem.DuplicateInfo info;
/*    */   public GDItem.LabelInfo itemCombo;
/*    */   public GDItem.LabelInfo classCombo;
/*    */   public GDItem.LabelInfo itemPrefix;
/*    */   public GDItem.LabelInfo levelPrefix;
/*    */   public GDItem.LabelInfo itemSuffix;
/*    */   public GDItem.LabelInfo levelSuffix;
/*    */   
/*    */   static {
/* 29 */     updateColumnNames();
/*    */   }
/*    */   
/*    */   public GDItemSimilarRow(DBStashItem.DuplicateInfo info) {
/* 33 */     this.info = info;
/*    */     
/* 35 */     if (info == null) {
/* 36 */       this.itemCombo = new GDItem.LabelInfo();
/* 37 */       this.classCombo = new GDItem.LabelInfo();
/* 38 */       this.itemPrefix = new GDItem.LabelInfo();
/* 39 */       this.levelPrefix = new GDItem.LabelInfo();
/* 40 */       this.itemSuffix = new GDItem.LabelInfo();
/* 41 */       this.levelSuffix = new GDItem.LabelInfo();
/*    */     } else {
/* 43 */       this.itemCombo = info.numItemCombo;
/* 44 */       this.classCombo = info.numClassCombo;
/* 45 */       this.itemPrefix = info.numItemPrefix;
/* 46 */       this.levelPrefix = info.levelItemPrefix;
/* 47 */       this.itemSuffix = info.numItemSuffix;
/* 48 */       this.levelSuffix = info.levelItemSuffix;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void updateColumnNames() {
/* 53 */     if (columnNames == null) columnNames = new String[6];
/*    */     
/* 55 */     columnNames[0] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_NUM_ITEM");
/* 56 */     columnNames[1] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_NUM_ITEM_CLASS");
/* 57 */     columnNames[2] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_NUM_PREFIX");
/* 58 */     columnNames[3] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_LEVEL_PREFIX");
/* 59 */     columnNames[4] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_NUM_SUFFIX");
/* 60 */     columnNames[5] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_LEVEL_SUFFIX");
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\table\GDItemSimilarRow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */