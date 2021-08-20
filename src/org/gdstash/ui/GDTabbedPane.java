/*     */ package org.gdstash.ui;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import org.gdstash.ui.util.AdjustableTabbedPane;
/*     */ import org.gdstash.util.GDImagePool;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ 
/*     */ public class GDTabbedPane
/*     */   extends AdjustableTabbedPane
/*     */ {
/*     */   private GDStashFrame frame;
/*     */   
/*     */   private class TabChangeListener
/*     */     implements ChangeListener
/*     */   {
/*     */     private TabChangeListener() {}
/*     */     
/*     */     public void stateChanged(ChangeEvent e) {
/*  25 */       Component comp = GDTabbedPane.this.getSelectedComponent();
/*     */       
/*  27 */       if (comp == GDTabbedPane.this.frame.pnlTransfer) {
/*  28 */         GDTabbedPane.this.frame.pnlTransfer.checkSaveButton();
/*  29 */         GDTabbedPane.this.frame.pnlTransfer.updateStash();
/*     */       } 
/*     */       
/*  32 */       if (comp == GDTabbedPane.this.frame.pnlCraft) {
/*  33 */         GDTabbedPane.this.frame.pnlCraft.checkSaveButton();
/*  34 */         GDTabbedPane.this.frame.pnlCraft.updateStash();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  41 */   private String[] titles = new String[8];
/*  42 */   private Icon[] icons = new Icon[8];
/*     */   
/*     */   public GDTabbedPane(GDStashFrame frame) {
/*  45 */     this.frame = frame;
/*     */     
/*  47 */     add((Component)frame.pnlTransfer);
/*  48 */     add((Component)frame.pnlCharInventory);
/*  49 */     if (GDStashFrame.iniConfig.sectRestrict.allowEdit) {
/*  50 */       add((Component)frame.pnlCraft);
/*     */     }
/*  52 */     if (GDStashFrame.iniConfig.sectRestrict.allowEdit) {
/*  53 */       add((Component)frame.pnlCharEdit);
/*     */     }
/*  55 */     add((Component)frame.pnlMasteryInfo);
/*  56 */     add((Component)frame.pnlCollection);
/*  57 */     add((Component)frame.pnlMassImport);
/*  58 */     add((Component)frame.pnlConfig);
/*     */     
/*  60 */     adjustUI();
/*     */     
/*  62 */     addChangeListener(new TabChangeListener());
/*     */     
/*  64 */     checkTabs();
/*     */     
/*  66 */     if (!GDStashFrame.dbConfig.configInit || !GDStashFrame.dbConfig.gddbInit)
/*     */     {
/*  68 */       setSelectedIndex(getTabCount() - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  74 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  75 */     Font fntTabbed = UIManager.getDefaults().getFont("TabbedPane.font");
/*     */     
/*  77 */     fntTabbed = fntTabbed.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  78 */     setFont(fntTabbed);
/*     */     
/*  80 */     int index = 0;
/*     */     
/*  82 */     this.titles[0] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_ITEM_TRANS");
/*  83 */     this.titles[1] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_CHAR_TRANS");
/*  84 */     this.titles[2] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_CRAFTING");
/*  85 */     this.titles[3] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_CHAR_EDIT");
/*  86 */     this.titles[4] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_MASTERY_INFO");
/*  87 */     this.titles[5] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_COLLECTION");
/*  88 */     this.titles[6] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_MASS_IMPORT");
/*  89 */     this.titles[7] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_CONFIG");
/*     */     
/*  91 */     this.icons[0] = GDImagePool.iconTabStashTransfer16;
/*  92 */     this.icons[1] = GDImagePool.iconTabCharTransfer16;
/*  93 */     this.icons[2] = GDImagePool.iconTabStashCraft16;
/*  94 */     this.icons[3] = GDImagePool.iconTabCharEdit16;
/*  95 */     this.icons[4] = GDImagePool.iconTabMasteryInfo16;
/*  96 */     this.icons[5] = GDImagePool.iconTabCollection16;
/*  97 */     this.icons[6] = GDImagePool.iconTabFileImp16;
/*  98 */     this.icons[7] = GDImagePool.iconTabConfigure16;
/*     */     
/* 100 */     setTitleAt(index, this.titles[0]);
/* 101 */     setIconAt(index, this.icons[0]);
/* 102 */     index++;
/*     */     
/* 104 */     setTitleAt(index, this.titles[1]);
/* 105 */     setIconAt(index, this.icons[1]);
/* 106 */     index++;
/*     */     
/* 108 */     if (GDStashFrame.iniConfig.sectRestrict.allowEdit) {
/* 109 */       setTitleAt(index, this.titles[2]);
/* 110 */       setIconAt(index, this.icons[2]);
/* 111 */       index++;
/*     */     } 
/*     */     
/* 114 */     if (GDStashFrame.iniConfig.sectRestrict.allowEdit) {
/* 115 */       setTitleAt(index, this.titles[3]);
/* 116 */       setIconAt(index, this.icons[3]);
/* 117 */       index++;
/*     */     } 
/*     */     
/* 120 */     setTitleAt(index, this.titles[4]);
/* 121 */     setIconAt(index, this.icons[4]);
/* 122 */     index++;
/*     */     
/* 124 */     setTitleAt(index, this.titles[5]);
/* 125 */     setIconAt(index, this.icons[5]);
/* 126 */     index++;
/*     */     
/* 128 */     setTitleAt(index, this.titles[6]);
/* 129 */     setIconAt(index, this.icons[6]);
/* 130 */     index++;
/*     */     
/* 132 */     setTitleAt(index, this.titles[7]);
/* 133 */     setIconAt(index, this.icons[7]);
/* 134 */     index++;
/*     */   }
/*     */   
/*     */   public void checkTabs() {
/* 138 */     boolean enable = (GDStashFrame.dbConfig.configInit && GDStashFrame.dbConfig.gddbInit);
/*     */ 
/*     */     
/* 141 */     int count = getTabCount();
/* 142 */     for (int i = 0; i < count - 1; i++) {
/* 143 */       setEnabledAt(i, enable);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setEdit(boolean allowed) {
/* 148 */     int tabs = getTabCount();
/*     */     
/* 150 */     if (allowed) {
/* 151 */       if (tabs == 7)
/*     */         return; 
/* 153 */       insertTab(this.titles[2], this.icons[2], (Component)this.frame.pnlCraft, null, 2);
/* 154 */       insertTab(this.titles[3], this.icons[3], (Component)this.frame.pnlCharEdit, null, 3);
/*     */     } else {
/* 156 */       if (tabs == 5) {
/*     */         return;
/*     */       }
/*     */       
/* 160 */       removeTabAt(3);
/* 161 */       removeTabAt(2);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\GDTabbedPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */