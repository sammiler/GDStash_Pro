/*    */ package org.gdstash.ui.table;
/*    */ 
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.ImageIcon;
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
/*    */ 
/*    */ public class GDItemCollectionRow
/*    */ {
/* 18 */   public static final Class[] COLUMN_CLASSES = new Class[] { Icon.class, String.class, String.class, Icon.class, Icon.class };
/*    */   
/* 20 */   public static String[] columnNames = null;
/*    */   
/*    */   public GDItemInfo item;
/*    */   public String baseName;
/*    */   public boolean softcore;
/*    */   public boolean hardcore;
/*    */   public ImageIcon icon;
/*    */   public GDItem.LabelInfo name;
/*    */   public GDItem.LabelInfo level;
/*    */   public ImageIcon scIcon;
/*    */   public ImageIcon hcIcon;
/*    */   
/*    */   static {
/* 33 */     updateColumnNames();
/*    */   }
/*    */   
/*    */   public static void updateColumnNames() {
/* 37 */     if (columnNames == null) columnNames = new String[5];
/*    */     
/* 39 */     columnNames[0] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_PICTURE");
/* 40 */     columnNames[1] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_ITEM_NAME");
/* 41 */     columnNames[2] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_LEVEL");
/* 42 */     columnNames[3] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_SOFTCORE");
/* 43 */     columnNames[4] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_HARDCORE");
/*    */   }
/*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\table\GDItemCollectionRow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */