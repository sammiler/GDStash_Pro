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
/*    */ public class GDMassImportRow
/*    */ {
/* 17 */   public static final Class[] COLUMN_CLASSES = new Class[] { Boolean.class, Icon.class, Icon.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, Icon.class };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 22 */   public static String[] columnNames = null;
/*    */   
/*    */   public GDItem item;
/*    */   public boolean valid;
/*    */   public String baseName;
/*    */   public boolean hardcore;
/*    */   public boolean selected;
/*    */   public ImageIcon validIcon;
/*    */   public ImageIcon icon;
/*    */   public GDItem.LabelInfo name;
/*    */   public GDItem.LabelInfo level;
/*    */   public GDItem.LabelInfo prefix;
/*    */   public GDItem.LabelInfo suffix;
/*    */   public GDItem.LabelInfo modifier;
/*    */   public GDItem.LabelInfo component;
/*    */   public GDItem.LabelInfo bonus;
/*    */   public GDItem.LabelInfo enchantment;
/*    */   public GDItem.LabelInfo seed;
/*    */   public GDItem.LabelInfo charName;
/*    */   public ImageIcon hcIcon;
/*    */   
/*    */   static {
/* 44 */     updateColumnNames();
/*    */   }
/*    */   
/*    */   public static void updateColumnNames() {
/* 48 */     if (columnNames == null) columnNames = new String[COLUMN_CLASSES.length];
/*    */     
/* 50 */     columnNames[0] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_SELECT");
/* 51 */     columnNames[1] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_VALID");
/* 52 */     columnNames[2] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_PICTURE");
/* 53 */     columnNames[3] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_ITEM_NAME");
/* 54 */     columnNames[4] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_LEVEL");
/* 55 */     columnNames[5] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_PREFIX");
/* 56 */     columnNames[6] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_SUFFIX");
/* 57 */     columnNames[7] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MODIFIER");
/* 58 */     columnNames[8] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_COMPONENT");
/* 59 */     columnNames[9] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_COMPLETION_BONUS");
/* 60 */     columnNames[10] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_ENCHANTMENT");
/* 61 */     columnNames[11] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_SEED");
/* 62 */     columnNames[12] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_CHAR_NAME");
/* 63 */     columnNames[13] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_HARDCORE");
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\table\GDMassImportRow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */