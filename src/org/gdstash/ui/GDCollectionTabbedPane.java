/*    */ package org.gdstash.ui;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Font;
/*    */ import javax.swing.UIManager;
/*    */ import org.gdstash.ui.util.AdjustableTabbedPane;
/*    */ import org.gdstash.util.GDImagePool;
/*    */ import org.gdstash.util.GDMsgFormatter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GDCollectionTabbedPane
/*    */   extends AdjustableTabbedPane
/*    */ {
/*    */   private GDStashFrame frame;
/*    */   private GDCollectionPane pnlItemCollection;
/*    */   private GDCollectionSetPane pnlSetCollection;
/*    */   
/*    */   public GDCollectionTabbedPane(GDStashFrame frame) {
/* 24 */     this.frame = frame;
/*    */     
/* 26 */     this.pnlItemCollection = new GDCollectionPane(frame);
/* 27 */     this.pnlSetCollection = new GDCollectionSetPane(frame);
/*    */     
/* 29 */     add((Component)this.pnlItemCollection);
/* 30 */     add((Component)this.pnlSetCollection);
/*    */     
/* 32 */     adjustUI();
/*    */   }
/*    */ 
/*    */   
/*    */   public void adjustUI() {
/* 37 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 38 */     Font fntTabbed = UIManager.getDefaults().getFont("TabbedPane.font");
/*    */     
/* 40 */     fntTabbed = fntTabbed.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 41 */     setFont(fntTabbed);
/*    */     
/* 43 */     int index = 0;
/*    */     
/* 45 */     setTitleAt(index, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_ITEM_COLL"));
/* 46 */     setIconAt(index, GDImagePool.iconTabCollection16);
/* 47 */     index++;
/*    */     
/* 49 */     setTitleAt(index, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_SET_COLL"));
/* 50 */     setIconAt(index, GDImagePool.iconTabCollectionSet16);
/* 51 */     index++;
/*    */     
/* 53 */     this.pnlItemCollection.adjustUI();
/* 54 */     this.pnlSetCollection.adjustUI();
/*    */   }
/*    */   
/*    */   public void updateConfig() {
/* 58 */     if (this.pnlItemCollection != null) this.pnlItemCollection.updateConfig(); 
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\GDCollectionTabbedPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */