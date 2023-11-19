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
/*    */ public class GDItemNameRow
/*    */ {
/* 17 */   public static final Class[] COLUMN_CLASSES = new Class[] { Icon.class, String.class, String.class };
/*    */   
/* 19 */   public static String[] columnNames = null;
/*    */   
/*    */   public GDItem item;
/*    */   public String baseName;
/*    */   public ImageIcon icon;
/*    */   public GDItem.LabelInfo name;
/*    */   public GDItem.LabelInfo level;
/*    */   
/*    */   static {
/* 28 */     updateColumnNames();
/*    */   }
/*    */   
/*    */   public static void updateColumnNames() {
/* 32 */     if (columnNames == null) columnNames = new String[3];
/*    */     
/* 34 */     columnNames[0] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_PICTURE");
/* 35 */     columnNames[1] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_ITEM_NAME");
/* 36 */     columnNames[2] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_LEVEL");
/*    */   }
/*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\table\GDItemNameRow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */