/*    */ package org.gdstash.ui.table;
/*    */ 
/*    */ import java.awt.Point;
/*    */ import java.awt.event.MouseEvent;
/*    */ import javax.swing.table.JTableHeader;
/*    */ import javax.swing.table.TableColumnModel;
/*    */ import org.gdstash.util.GDMsgFormatter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GDItemSimilarTableHeader
/*    */   extends JTableHeader
/*    */ {
/* 18 */   public static String[] toolTips = null;
/*    */   
/*    */   static {
/* 21 */     updateToolTips();
/*    */   }
/*    */   
/*    */   public GDItemSimilarTableHeader(TableColumnModel columnModel) {
/* 25 */     super(columnModel);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getToolTipText(MouseEvent e) {
/* 30 */     Point p = e.getPoint();
/* 31 */     int index = this.columnModel.getColumnIndexAtX(p.x);
/* 32 */     int realIndex = this.columnModel.getColumn(index).getModelIndex();
/*    */     
/* 34 */     return toolTips[realIndex];
/*    */   }
/*    */   
/*    */   public static void updateToolTips() {
/* 38 */     if (toolTips == null) toolTips = new String[6];
/*    */     
/* 40 */     toolTips[0] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TIP_NUM_ITEM");
/* 41 */     toolTips[1] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TIP_NUM_ITEM_CLASS");
/* 42 */     toolTips[2] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TIP_NUM_PREFIX");
/* 43 */     toolTips[3] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TIP_LEVEL_PREFIX");
/* 44 */     toolTips[4] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TIP_NUM_SUFFIX");
/* 45 */     toolTips[5] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TIP_LEVEL_SUFFIX");
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\table\GDItemSimilarTableHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */