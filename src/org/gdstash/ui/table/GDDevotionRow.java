/*    */ package org.gdstash.ui.table;
/*    */ 
/*    */ import javax.swing.DefaultCellEditor;
/*    */ import javax.swing.JComboBox;
/*    */ import javax.swing.table.TableCellEditor;
/*    */ import org.gdstash.character.GDChar;
/*    */ import org.gdstash.util.GDMsgFormatter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GDDevotionRow
/*    */ {
/* 18 */   public static final Class[] COLUMN_CLASSES = new Class[] { String.class, String.class };
/*    */   
/* 20 */   public static String[] columnNames = null;
/*    */   
/*    */   public GDChar.SkillInfo devotion;
/*    */   public int maxLevel;
/*    */   public TableCellEditor editor;
/*    */   
/*    */   static {
/* 27 */     updateColumnNames();
/*    */   }
/*    */   
/*    */   public GDDevotionRow(GDChar.SkillInfo devotion, int maxLevel) {
/* 31 */     this.devotion = devotion;
/* 32 */     this.maxLevel = maxLevel;
/*    */     
/* 34 */     if (maxLevel > 0) {
/* 35 */       JComboBox<String> cbLevels = new JComboBox<>();
/*    */       int i;
/* 37 */       for (i = 1; i <= maxLevel; i++) {
/* 38 */         cbLevels.addItem(Integer.toString(i));
/*    */       }
/*    */       
/* 41 */       this.editor = new DefaultCellEditor(cbLevels);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void updateColumnNames() {
/* 46 */     if (columnNames == null) columnNames = new String[2];
/*    */     
/* 48 */     columnNames[0] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_COL_DEVOTION");
/* 49 */     columnNames[1] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_COL_LEVEL");
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\table\GDDevotionRow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */