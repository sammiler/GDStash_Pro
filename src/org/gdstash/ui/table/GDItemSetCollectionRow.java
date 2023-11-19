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
/*    */ public class GDItemSetCollectionRow
/*    */ {
/* 17 */   public static final Class[] COLUMN_CLASSES = new Class[] { String.class, String.class, String.class, Icon.class, Icon.class, Icon.class, Icon.class, Icon.class, Icon.class };
/*    */   
/* 19 */   public static String[] columnNames = null;
/*    */   
/*    */   public GDItemSetInfo set;
/*    */   public GDItem.LabelInfo setName;
/*    */   public GDItem.LabelInfo setLevel;
/*    */   public GDItem.LabelInfo setRarity;
/*    */   public int level;
/*    */   public int rarity;
/*    */   public ImageIcon iconItem1;
/*    */   public int scItem1;
/*    */   public int hcItem1;
/*    */   public boolean blueprint1;
/*    */   public ImageIcon iconItem2;
/*    */   public int scItem2;
/*    */   public int hcItem2;
/*    */   public boolean blueprint2;
/*    */   public ImageIcon iconItem3;
/*    */   public int scItem3;
/*    */   public int hcItem3;
/*    */   public boolean blueprint3;
/*    */   public ImageIcon iconItem4;
/*    */   public int scItem4;
/*    */   public int hcItem4;
/*    */   public boolean blueprint4;
/*    */   public ImageIcon iconItem5;
/*    */   public int scItem5;
/*    */   public int hcItem5;
/*    */   public boolean blueprint5;
/*    */   public ImageIcon iconItem6;
/*    */   public int scItem6;
/*    */   public int hcItem6;
/*    */   public boolean blueprint6;
/*    */   
/*    */   static {
/* 53 */     updateColumnNames();
/*    */   }
/*    */   
/*    */   public static void updateColumnNames() {
/* 57 */     if (columnNames == null) columnNames = new String[9];
/*    */     
/* 59 */     columnNames[0] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_SET");
/* 60 */     columnNames[1] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_LEVEL");
/* 61 */     columnNames[2] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_RARITY");
/* 62 */     columnNames[3] = "";
/* 63 */     columnNames[4] = "";
/* 64 */     columnNames[5] = "";
/* 65 */     columnNames[6] = "";
/* 66 */     columnNames[7] = "";
/* 67 */     columnNames[8] = "";
/*    */   }
/*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\table\GDItemSetCollectionRow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */