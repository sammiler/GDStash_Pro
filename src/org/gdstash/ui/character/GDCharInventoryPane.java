/*      */ package org.gdstash.ui.character;
/*      */ 
/*      */ import java.awt.*;
/*      */
/*      */
/*      */
/*      */
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
/*      */ import javax.swing.*;
/*      */
/*      */
/*      */
/*      */ import org.gdstash.db.DBStashItem;
/*      */ import org.gdstash.db.DBStat;
import org.gdstash.db.SelectionCriteria;
/*      */ import org.gdstash.db.criteria.CriteriaCombination;
import org.gdstash.item.GDItem;
/*      */ import org.gdstash.ui.*;
/*      */
/*      */
/*      */
/*      */ import org.gdstash.ui.stash.GDCharStashPane;
/*      */ import org.gdstash.ui.stash.GDContainerMapPane;
/*      */ import org.gdstash.ui.util.AdjustablePanel;
import org.gdstash.ui.util.GDCharInfoList;
/*      */ import org.gdstash.ui.util.GDStashInfoList;
import org.gdstash.util.GDImagePool;
/*      */ import org.gdstash.util.GDLog;
/*      */ import org.gdstash.util.GDMsgFormatter;
/*      */ import org.gdstash.util.GDMsgLogger;
/*      */ 
/*      */ public class GDCharInventoryPane extends AdjustablePanel implements GDUITransfer, GDUISearch {
/*      */   private GDStashFrame frame;
/*      */   private GDCharStashPane pnlStash;
/*      */   private GDItemInfoPane pnlInfo;
/*      */   private JButton btnDBCopy;
/*      */   private JButton btnDBMove;
/*      */   private JButton btnDBDel;
/*      */   private JButton btnStashDel;
/*      */   private JButton btnPageCopy;
/*      */   private JButton btnPageMove;
/*      */   private JButton btnPageDel;
/*      */   private JButton btnReload;
/*      */   private JButton btnHelp;
/*      */   private JButton btnFileSave;
/*      */   private GDItemNameTablePane pnlTable;
/*      */   private String strSave;
/*      */   private JButton btnSearch;
/*      */   private GDCharInfoList.GDCharFileInfo info;
/*      */   private GDItem selItem;
/*      */   private int location;
/*      */   private JPanel pnlMain;
/*      */   private GDTabbedSearchDialog dlgSearch;
/*      */   
/*      */   private class TableActionListener implements ActionListener { public void actionPerformed(ActionEvent ev) {
/*   55 */       GDItem item = GDCharInventoryPane.this.pnlTable.getSelectedItem();
/*      */       
/*   57 */       GDCharInventoryPane.this.setSelectedItem(item, 2);
/*      */       
/*   59 */       GDMsgLogger.showLog((Component)GDCharInventoryPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     }
/*      */     private TableActionListener() {} }
/*      */   
/*      */   private class ReloadActionListener implements ActionListener { private ReloadActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*   66 */       GDCharInfoList.findChars(GDCharInventoryPane.this.frame, GDCharInventoryPane.this.info);
/*      */       
/*   68 */       if (GDCharInventoryPane.this.info != null) {
/*   69 */         for (GDCharInfoList.GDCharFileInfo ci : GDCharInfoList.gdCharFileInfos) {
/*   70 */           if (ci.fileName.equals(GDCharInventoryPane.this.info.fileName)) {
/*   71 */             GDCharInventoryPane.this.info = ci;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/*   78 */       if (GDCharInventoryPane.this.frame.pnlCharInventory != null) {
/*   79 */         GDCharInventoryPane.this.frame.pnlCharInventory.refreshCharInfo(GDCharInventoryPane.this.info);
/*      */       }
/*   81 */       if (GDCharInventoryPane.this.frame.pnlCharEdit != null) {
/*   82 */         GDCharInventoryPane.this.frame.pnlCharEdit.refreshCharInfo(GDCharInventoryPane.this.info);
/*      */       }
/*      */       
/*   85 */       GDMsgLogger.showLog((Component)GDCharInventoryPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     } }
/*      */   
/*      */   private class DBDelActionListener implements ActionListener {
/*      */     private DBDelActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*   92 */       GDItem item = GDCharInventoryPane.this.pnlTable.getSelectedItem();
/*      */       
/*   94 */       if (item == null)
/*      */         return; 
/*   96 */       GDCharInventoryPane.this.transferSelectedItem(5, 0, 0);
/*      */       
/*   98 */       GDMsgLogger.showLog((Component)GDCharInventoryPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     }
/*      */   }
/*      */   
/*      */   private class DBCopyActionListener implements ActionListener { private DBCopyActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  105 */       GDItem item = GDCharInventoryPane.this.getSelectedItem();
/*      */       
/*  107 */       if (item == null)
/*      */         return; 
/*  109 */       GDCharInventoryPane.this.transferSelectedItem(2, 0, 0);
/*      */       
/*  111 */       GDMsgLogger.showLog((Component)GDCharInventoryPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     } }
/*      */   
/*      */   private class DBMoveActionListener implements ActionListener {
/*      */     private DBMoveActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  118 */       GDItem item = GDCharInventoryPane.this.getSelectedItem();
/*      */       
/*  120 */       if (item == null)
/*      */         return; 
/*  122 */       GDCharInventoryPane.this.transferSelectedItem(3, 0, 0);
/*      */       
/*  124 */       GDMsgLogger.showLog((Component)GDCharInventoryPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     }
/*      */   }
/*      */   
/*      */   private class PageCopyActionListener implements ActionListener { private PageCopyActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  131 */       if (GDCharInventoryPane.this.pnlStash.getCurrentPage() == null)
/*      */         return; 
/*  133 */       GDCharInventoryPane.this.transferSelectedItem(7, 0, 0);
/*      */       
/*  135 */       GDMsgLogger.showLog((Component)GDCharInventoryPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     } }
/*      */   
/*      */   private class PageMoveActionListener implements ActionListener {
/*      */     private PageMoveActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  142 */       if (GDCharInventoryPane.this.pnlStash.getCurrentPage() == null)
/*      */         return; 
/*  144 */       GDCharInventoryPane.this.transferSelectedItem(8, 0, 0);
/*      */       
/*  146 */       GDMsgLogger.showLog((Component)GDCharInventoryPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     }
/*      */   }
/*      */   
/*      */   private class StashDelActionListener implements ActionListener { private StashDelActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  153 */       GDCharInventoryPane.this.transferSelectedItem(6, 0, 0);
/*      */       
/*  155 */       GDMsgLogger.showLog((Component)GDCharInventoryPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     } }
/*      */   
/*      */   private class PageDelActionListener implements ActionListener {
/*      */     private PageDelActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  162 */       if (GDCharInventoryPane.this.pnlStash.getCurrentPage() == null)
/*      */         return; 
/*  164 */       GDCharInventoryPane.this.transferSelectedItem(9, 0, 0);
/*      */       
/*  166 */       GDMsgLogger.showLog((Component)GDCharInventoryPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     }
/*      */   }
/*      */   
/*      */   private class FileSaveActionListener implements ActionListener {
/*      */     private FileSaveActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  174 */       if (GDCharInventoryPane.this.info == null)
/*  175 */         return;  if (GDCharInventoryPane.this.info.gdChar == null)
/*      */         return; 
/*  177 */       if (GDCharInventoryPane.this.frame.isCloudSaveDir() && 
/*  178 */         GDStashFrame.isGrimDawnRunning()) {
/*  179 */         GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_CLOUD_SAVE"));
/*  180 */         GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_CLOSE"));
/*      */         
/*  182 */         GDMsgLogger.showLog((Component)GDCharInventoryPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_RUNNING"), GDLog.MessageType.Info, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_RUNNING"));
/*      */ 
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */ 
/*      */       
/*  190 */       boolean success = false;
/*      */       
/*      */       try {
/*  193 */         GDCharInventoryPane.this.info.gdChar.write();
/*      */         
/*  195 */         success = true;
/*      */       }
/*  197 */       catch (IOException ex) {
/*  198 */         GDMsgLogger.addError(ex);
/*      */       } 
/*      */       
/*  201 */       if (success) {
/*  202 */         GDCharInventoryPane.this.checkSaveButton();
/*      */       }
/*      */       
/*  205 */       GDMsgLogger.showLog((Component)GDCharInventoryPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_CHAR_SAVE"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_CHAR_SAVE"), true);
/*      */     } }
/*      */   
/*      */   private class SearchItemsListener implements ActionListener {
/*      */     private SearchItemsListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  212 */       if (GDCharInventoryPane.this.dlgSearch == null) {
/*  213 */         GDCharInventoryPane.this.dlgSearch = new GDTabbedSearchDialog((Frame)GDCharInventoryPane.this.frame, GDCharInventoryPane.this, GDTabbedSearchDialog.Mode.TRANSFER);
/*      */       }
/*      */ 
/*      */       
/*  217 */       GDCharInventoryPane.this.dlgSearch.setVisible(true);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GDCharInventoryPane(GDStashFrame frame) {
/*  251 */     this.frame = frame;
/*      */     
/*  253 */     this.strSave = GDStashFrame.iniConfig.sectDir.savePath;
/*  254 */     this.selItem = null;
/*  255 */     this.location = 0;
/*      */     
/*  257 */     this.dlgSearch = null;
/*      */     
/*  259 */     adjustUI();
/*      */     
/*  261 */     this.pnlMain = buildMainPanel();
/*      */ 
/*      */ 
/*      */     
/*  265 */     GroupLayout layout = null;
/*  266 */     GroupLayout.SequentialGroup hGroup = null;
/*  267 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/*  269 */     JScrollPane scroll = new JScrollPane(this.pnlMain);
/*  270 */     scroll.getVerticalScrollBar().setUnitIncrement(2 * GDStashFrame.iniConfig.sectUI.fontSize);
/*      */     
/*  272 */     layout = new GroupLayout((Container)this);
/*  273 */     setLayout(layout);
/*      */     
/*  275 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  278 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  281 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  284 */     hGroup
/*  285 */       .addGroup(layout.createParallelGroup()
/*  286 */         .addComponent(scroll));
/*  287 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  290 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  293 */     vGroup
/*  294 */       .addGroup(layout.createParallelGroup()
/*  295 */         .addComponent(scroll));
/*  296 */     layout.setVerticalGroup(vGroup);
/*      */   }
/*      */ 
/*      */   
/*      */   public void adjustUI() {
/*  301 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  302 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/*  303 */     if (fntButton == null) fntButton = fntLabel;
/*      */     
/*  305 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  306 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*      */ 
/*      */ 
/*      */     
/*  310 */     GDCharInfoList.adjustCharInfos(null, null);
/*      */     
/*  312 */     if (this.btnFileSave == null) {
/*  313 */       this.btnFileSave = new JButton();
/*      */       
/*  315 */       this.btnFileSave.setEnabled(false);
/*  316 */       this.btnFileSave.addActionListener(new FileSaveActionListener());
/*      */     } 
/*  318 */     this.btnFileSave.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_CHAR_SAVE"));
/*  319 */     this.btnFileSave.setIcon(GDImagePool.iconBtnSave24);
/*  320 */     this.btnFileSave.setFont(fntButton);
/*  321 */     GDStashFrame.setMnemonic(this.btnFileSave, "MNC_CHAR_SAVE");
/*      */     
/*  323 */     if (this.btnDBDel == null) {
/*  324 */       this.btnDBDel = new JButton();
/*      */       
/*  326 */       this.btnDBDel.setEnabled(false);
/*  327 */       this.btnDBDel.addActionListener(new DBDelActionListener());
/*      */     } 
/*  329 */     this.btnDBDel.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_ITEM_DEL_DB"));
/*  330 */     this.btnDBDel.setIcon(GDImagePool.iconDBDelete24);
/*  331 */     this.btnDBDel.setFont(fntButton);
/*  332 */     GDStashFrame.setMnemonic(this.btnDBDel, "MNC_ITEM_DEL_DB");
/*      */     
/*  334 */     if (this.btnDBCopy == null) {
/*  335 */       this.btnDBCopy = new JButton();
/*      */       
/*  337 */       this.btnDBCopy.setEnabled(false);
/*  338 */       this.btnDBCopy.addActionListener(new DBCopyActionListener());
/*      */     } 
/*  340 */     this.btnDBCopy.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_ITEM_COPY_DB"));
/*  341 */     this.btnDBCopy.setIcon(GDImagePool.iconItemCopy24);
/*  342 */     this.btnDBCopy.setFont(fntButton);
/*  343 */     GDStashFrame.setMnemonic(this.btnDBCopy, "MNC_ITEM_COPY_DB");
/*      */     
/*  345 */     if (this.btnDBMove == null) {
/*  346 */       this.btnDBMove = new JButton();
/*      */       
/*  348 */       this.btnDBMove.setEnabled(false);
/*  349 */       this.btnDBMove.addActionListener(new DBMoveActionListener());
/*      */     } 
/*  351 */     this.btnDBMove.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_ITEM_MOVE_DB"));
/*  352 */     this.btnDBMove.setIcon(GDImagePool.iconItemMove24);
/*  353 */     this.btnDBMove.setFont(fntButton);
/*  354 */     GDStashFrame.setMnemonic(this.btnDBMove, "MNC_ITEM_MOVE_DB");
/*      */     
/*  356 */     if (this.btnPageCopy == null) {
/*  357 */       this.btnPageCopy = new JButton();
/*      */       
/*  359 */       this.btnPageCopy.setEnabled(true);
/*  360 */       this.btnPageCopy.addActionListener(new PageCopyActionListener());
/*      */     } 
/*  362 */     this.btnPageCopy.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_PAGE_COPY_DB"));
/*  363 */     this.btnPageCopy.setIcon(GDImagePool.iconPageCopy24);
/*  364 */     this.btnPageCopy.setFont(fntButton);
/*      */ 
/*      */     
/*  367 */     if (this.btnPageMove == null) {
/*  368 */       this.btnPageMove = new JButton();
/*      */       
/*  370 */       this.btnPageMove.setEnabled(true);
/*  371 */       this.btnPageMove.addActionListener(new PageMoveActionListener());
/*      */     } 
/*  373 */     this.btnPageMove.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_PAGE_MOVE_DB"));
/*  374 */     this.btnPageMove.setIcon(GDImagePool.iconPageMove24);
/*  375 */     this.btnPageMove.setFont(fntButton);
/*      */ 
/*      */     
/*  378 */     if (this.btnStashDel == null) {
/*  379 */       this.btnStashDel = new JButton();
/*      */       
/*  381 */       this.btnStashDel.setEnabled(false);
/*  382 */       this.btnStashDel.addActionListener(new StashDelActionListener());
/*      */     } 
/*  384 */     this.btnStashDel.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_ITEM_DEL_CHAR"));
/*  385 */     this.btnStashDel.setIcon(GDImagePool.iconItemDelete24);
/*  386 */     this.btnStashDel.setFont(fntButton);
/*  387 */     GDStashFrame.setMnemonic(this.btnStashDel, "MNC_ITEM_DEL_CHAR");
/*      */     
/*  389 */     if (this.btnPageDel == null) {
/*  390 */       this.btnPageDel = new JButton();
/*      */       
/*  392 */       this.btnPageDel.setEnabled(true);
/*  393 */       this.btnPageDel.addActionListener(new PageDelActionListener());
/*      */     } 
/*  395 */     this.btnPageDel.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_PAGE_DEL"));
/*  396 */     this.btnPageDel.setIcon(GDImagePool.iconPageDelete24);
/*  397 */     this.btnPageDel.setFont(fntButton);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  408 */     if (this.btnSearch == null) {
/*  409 */       this.btnSearch = new JButton();
/*      */       
/*  411 */       this.btnSearch.addActionListener(new SearchItemsListener());
/*      */     } 
/*  413 */     this.btnSearch.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_SEARCH"));
/*  414 */     this.btnSearch.setIcon(GDImagePool.iconBtnSearch24);
/*  415 */     this.btnSearch.setFont(fntButton);
/*  416 */     GDStashFrame.setMnemonic(this.btnSearch, "MNC_SEARCH_SECOND");
/*      */     
/*  418 */     if (this.btnReload == null) {
/*  419 */       this.btnReload = new JButton();
/*      */       
/*  421 */       this.btnReload.addActionListener(new ReloadActionListener());
/*      */     } 
/*  423 */     this.btnReload.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_RELOAD"));
/*  424 */     this.btnReload.setIcon(GDImagePool.iconBtnReload24);
/*  425 */     this.btnReload.setFont(fntButton);
/*  426 */     GDStashFrame.setMnemonic(this.btnReload, "MNC_RELOAD");
/*      */     
/*  428 */     if (this.btnHelp == null) {
/*  429 */       this.btnHelp = new JButton();
/*      */       
/*  431 */       this.btnHelp.addActionListener((ActionListener)new GDHelpActionListener("04_transfer_char.html"));
/*      */     } 
/*  433 */     this.btnHelp.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_HELP"));
/*  434 */     this.btnHelp.setIcon(GDImagePool.iconBtnQuestion24);
/*  435 */     this.btnHelp.setFont(fntButton);
/*  436 */     GDStashFrame.setMnemonic(this.btnHelp, "MNC_HELP");
/*      */     
/*  438 */     if (this.pnlInfo == null) {
/*  439 */       this.pnlInfo = new GDItemInfoPane();
/*      */     }
/*  441 */     this.pnlInfo.adjustUI();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  446 */     if (this.pnlTable == null) {
/*  447 */       this.pnlTable = new GDItemNameTablePane(null, false);
/*  448 */       this.pnlTable.addActionListener(new TableActionListener());
/*      */       
/*  450 */       this.pnlTable.setMinimumSize(new Dimension(300, 200));
/*  451 */       this.pnlTable.setMaximumSize(new Dimension(600, 2000));
/*      */     } else {
/*  453 */       this.pnlTable.adjustUI();
/*      */     } 
/*      */     
/*  456 */     if (this.pnlStash == null) {
/*  457 */       this.pnlStash = new GDCharStashPane(this.frame, this);
/*      */     } else {
/*  459 */       this.pnlStash.adjustUI();
/*      */     } 
/*      */     
/*  462 */     if (this.dlgSearch != null) {
/*  463 */       this.dlgSearch.adjustUI();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateUI() {
/*  469 */     super.updateUI();
/*      */     
/*  471 */     if (this.dlgSearch != null) {
/*  472 */       SwingUtilities.updateComponentTreeUI((Component)this.dlgSearch);
/*      */     }
/*      */   }
/*      */   
/*      */   public void refresh() {
/*  477 */     if (this.pnlTable != null) this.pnlTable.refresh(); 
/*  478 */     if (this.pnlInfo != null) this.pnlInfo.refresh(); 
/*  479 */     if (this.pnlStash != null) this.pnlStash.refresh();
/*      */ 
/*      */     
/*  482 */     refreshCharSelection();
/*      */   }
/*      */   
/*      */   private JPanel buildMainPanel() {
/*  486 */     JPanel panel = new JPanel();
/*      */     
/*  488 */     GroupLayout layout = null;
/*  489 */     GroupLayout.SequentialGroup hGroup = null;
/*  490 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/*  492 */     JPanel pnlTable = buildTablePanel();
/*      */     
/*  494 */     JPanel pnlItemInfo = buildInfoPanel();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  506 */     layout = new GroupLayout(panel);
/*  507 */     panel.setLayout(layout);
/*      */     
/*  509 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  512 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  515 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  518 */     hGroup
/*  519 */       .addGroup(layout.createParallelGroup()
/*  520 */         .addComponent(pnlTable))
/*  521 */       .addGroup(layout.createParallelGroup()
/*  522 */         .addComponent(pnlItemInfo))
/*  523 */       .addGroup(layout.createParallelGroup()
/*  524 */         .addComponent((Component)this.pnlStash));
/*  525 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  528 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  531 */     vGroup
/*  532 */       .addGroup(layout.createParallelGroup()
/*  533 */         .addComponent(pnlTable)
/*  534 */         .addComponent(pnlItemInfo)
/*  535 */         .addComponent((Component)this.pnlStash));
/*  536 */     layout.setVerticalGroup(vGroup);
/*      */ 
/*      */ 
/*      */     
/*  540 */     return panel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JPanel buildTablePanel() {
/*  565 */     GroupLayout layout = null;
/*  566 */     GroupLayout.SequentialGroup hGroup = null;
/*  567 */     GroupLayout.SequentialGroup vGroup = null;
/*      */ 
/*      */ 
/*      */     
/*  571 */     JPanel panel = new JPanel();
/*      */     
/*  573 */     layout = new GroupLayout(panel);
/*  574 */     panel.setLayout(layout);
/*      */     
/*  576 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  579 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  582 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  585 */     hGroup
/*  586 */       .addGroup(layout.createParallelGroup()
/*  587 */         .addComponent(this.btnSearch)
/*  588 */         .addComponent((Component)this.pnlTable));
/*  589 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  592 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  595 */     vGroup
/*  596 */       .addGroup(layout.createParallelGroup()
/*  597 */         .addComponent(this.btnSearch))
/*  598 */       .addGroup(layout.createParallelGroup()
/*  599 */         .addComponent((Component)this.pnlTable));
/*  600 */     layout.setVerticalGroup(vGroup);
/*      */     
/*  602 */     layout.linkSize(0, new Component[] { this.btnSearch, (Component)this.pnlTable });
/*      */     
/*  604 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildInfoPanel() {
/*  608 */     GroupLayout layout = null;
/*  609 */     GroupLayout.SequentialGroup hGroup = null;
/*  610 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/*  612 */     JPanel panel = new JPanel();
/*      */     
/*  614 */     JPanel pnlButtonFile = buildFileButtonPanel();
/*  615 */     JPanel pnlButtonOper = buildOperationButtonPanel();
/*      */     
/*  617 */     layout = new GroupLayout(panel);
/*  618 */     panel.setLayout(layout);
/*      */     
/*  620 */     layout.setAutoCreateGaps(false);
/*      */ 
/*      */     
/*  623 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  626 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  629 */     hGroup
/*  630 */       .addGroup(layout.createParallelGroup()
/*  631 */         .addComponent(pnlButtonFile)
/*  632 */         .addComponent(pnlButtonOper)
/*  633 */         .addComponent((Component)this.pnlInfo));
/*  634 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  637 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  640 */     vGroup
/*  641 */       .addGroup(layout.createParallelGroup()
/*  642 */         .addComponent(pnlButtonFile))
/*  643 */       .addGroup(layout.createParallelGroup()
/*  644 */         .addComponent(pnlButtonOper))
/*  645 */       .addGroup(layout.createParallelGroup()
/*  646 */         .addComponent((Component)this.pnlInfo));
/*  647 */     layout.setVerticalGroup(vGroup);
/*      */     
/*  649 */     layout.linkSize(0, new Component[] { pnlButtonFile, pnlButtonOper });
/*  650 */     layout.linkSize(0, new Component[] { pnlButtonFile, (Component)this.pnlInfo });
/*      */ 
/*      */ 
/*      */     
/*  654 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildFileButtonPanel() {
/*  658 */     BorderLayout layout = new BorderLayout();
/*      */     
/*  660 */     JPanel panel = new JPanel();
/*      */     
/*  662 */     panel.setLayout(layout);
/*      */     
/*  664 */     panel.add(this.btnFileSave, "Center");
/*  665 */     panel.add(this.btnReload, "West");
/*  666 */     panel.add(this.btnHelp, "East");
/*      */     
/*  668 */     int size = 12;
/*  669 */     if (GDStashFrame.iniConfig != null) size = GDStashFrame.iniConfig.sectUI.fontSize; 
/*  670 */     Dimension dimPref = new Dimension(30 * size, 2 * size);
/*  671 */     Dimension dimMax = new Dimension(50 * size, 2 * size);
/*      */     
/*  673 */     panel.setPreferredSize(dimPref);
/*  674 */     panel.setMaximumSize(dimMax);
/*      */     
/*  676 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildOperationButtonPanel() {
/*  680 */     GroupLayout layout = null;
/*  681 */     GroupLayout.SequentialGroup hGroup = null;
/*  682 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/*  684 */     JPanel panel = new JPanel();
/*      */     
/*  686 */     layout = new GroupLayout(panel);
/*  687 */     panel.setLayout(layout);
/*      */     
/*  689 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  692 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  695 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  698 */     hGroup
/*  699 */       .addGroup(layout.createParallelGroup()
/*  700 */         .addComponent(this.btnDBCopy)
/*  701 */         .addComponent(this.btnDBMove)
/*  702 */         .addComponent(this.btnStashDel)
/*  703 */         .addComponent(this.btnDBDel))
/*  704 */       .addGroup(layout.createParallelGroup()
/*  705 */         .addComponent(this.btnPageCopy)
/*  706 */         .addComponent(this.btnPageMove)
/*  707 */         .addComponent(this.btnPageDel));
/*  708 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  711 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  714 */     vGroup
/*  715 */       .addGroup(layout.createParallelGroup()
/*  716 */         .addComponent(this.btnDBCopy)
/*  717 */         .addComponent(this.btnPageCopy))
/*  718 */       .addGroup(layout.createParallelGroup()
/*  719 */         .addComponent(this.btnDBMove)
/*  720 */         .addComponent(this.btnPageMove))
/*  721 */       .addGroup(layout.createParallelGroup()
/*  722 */         .addComponent(this.btnStashDel)
/*  723 */         .addComponent(this.btnPageDel))
/*  724 */       .addGroup(layout.createParallelGroup()
/*  725 */         .addComponent(this.btnDBDel));
/*  726 */     layout.setVerticalGroup(vGroup);
/*      */     
/*  728 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnDBMove });
/*  729 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnStashDel });
/*  730 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnDBDel });
/*  731 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnPageCopy });
/*  732 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnPageMove });
/*  733 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnPageDel });
/*      */     
/*  735 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnDBMove });
/*  736 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnStashDel });
/*  737 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnDBDel });
/*  738 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnPageCopy });
/*  739 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnPageMove });
/*  740 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnPageDel });
/*      */     
/*  742 */     return panel;
/*      */   }
/*      */   
/*      */   public void checkSaveButton() {
/*  746 */     if (this.info == null) {
/*  747 */       this.btnFileSave.setEnabled(false);
/*      */     }
/*  749 */     else if (this.info.gdChar == null) {
/*  750 */       this.btnFileSave.setEnabled(false);
/*      */     } else {
/*  752 */       this.btnFileSave.setEnabled(this.info.gdChar.hasChanged());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateConfig() {
/*  758 */     if (this.pnlStash != null) this.pnlStash.updateConfig(); 
/*  759 */     if (this.dlgSearch != null) this.dlgSearch.updateConfig(); 
/*      */   }
/*      */   
/*      */   public void initCharSelection() {
/*  763 */     if (this.pnlStash != null) this.pnlStash.initCharSelection(); 
/*      */   }
/*      */   
/*      */   public void refreshCharSelection() {
/*  767 */     this.pnlStash.refreshCharSelection();
/*      */   }
/*      */   
/*      */   public void refreshCharInfo(GDCharInfoList.GDCharFileInfo info) {
/*  771 */     if (info == null)
/*  772 */       return;  if (this.info == null)
/*      */       return; 
/*  774 */     if (this.info.fileName.equals(info.fileName)) {
/*  775 */       this.info = info;
/*      */       
/*  777 */       if (this.pnlStash != null) this.pnlStash.setChar(info); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renameCharDir(File fDirOld, File fDirNew) {
/*  782 */     if (this.pnlStash != null) this.pnlStash.renameCharDir(fDirOld, fDirNew);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setChar(GDCharInfoList.GDCharFileInfo info) {
/*  791 */     if (this.info == info)
/*      */       return; 
/*  793 */     this.info = info;
/*      */     
/*  795 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSoulbound) this.pnlTable.setItems(null);
/*      */     
/*  797 */     checkSaveButton();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStash(GDStashInfoList.GDStashFileInfo info) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemLocation() {
/*  808 */     return this.location;
/*      */   }
/*      */ 
/*      */   
/*      */   public GDItem getSelectedItem() {
/*  813 */     return this.selItem;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelectedItem(GDItem item, int location) {
/*  819 */     if (location == 1) {
/*  820 */       this.pnlTable.clearSelection();
/*      */     }
/*      */     
/*  823 */     this.selItem = item;
/*  824 */     this.location = location;
/*      */     
/*  826 */     boolean hardcore = false;
/*  827 */     String charName = "";
/*      */     
/*  829 */     if (this.info != null && 
/*  830 */       this.info.gdChar != null) {
/*  831 */       hardcore = this.info.gdChar.isHardcore();
/*  832 */       charName = this.info.gdChar.getCharName();
/*      */     } 
/*      */ 
/*      */     
/*  836 */     this.pnlInfo.setItemInfo(item, hardcore, charName);
/*      */     
/*  838 */     switch (location) {
/*      */       case 1:
/*  840 */         this.btnDBCopy.setEnabled((item != null));
/*  841 */         this.btnDBMove.setEnabled((item != null && item.getContainerType() != 6));
/*  842 */         this.btnStashDel.setEnabled((item != null && item.getContainerType() != 6));
/*  843 */         this.btnDBDel.setEnabled(false);
/*  844 */         this.btnPageCopy.setEnabled(true);
/*  845 */         this.btnPageMove.setEnabled(true);
/*  846 */         this.btnPageDel.setEnabled(true);
/*      */         return;
/*      */ 
/*      */       
/*      */       case 2:
/*  851 */         this.btnDBCopy.setEnabled(false);
/*  852 */         this.btnDBMove.setEnabled(false);
/*  853 */         this.btnStashDel.setEnabled(false);
/*  854 */         this.btnDBDel.setEnabled((item != null));
/*  855 */         this.btnPageCopy.setEnabled(false);
/*  856 */         this.btnPageMove.setEnabled(false);
/*  857 */         this.btnPageDel.setEnabled(false);
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/*  862 */     this.btnDBCopy.setEnabled(false);
/*  863 */     this.btnDBMove.setEnabled(false);
/*  864 */     this.btnStashDel.setEnabled(false);
/*  865 */     this.btnDBDel.setEnabled(false);
/*  866 */     this.btnPageCopy.setEnabled(true);
/*  867 */     this.btnPageMove.setEnabled(true);
/*  868 */     this.btnPageDel.setEnabled(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transferSelectedItem(int action, int x, int y) {
/*  876 */     switch (action) {
/*      */       
/*      */       case 1:
/*  879 */         if (this.location == 1) {
/*  880 */           GDItem item = this.pnlStash.getSelectedItem();
/*      */           
/*  882 */           if (item != null) {
/*  883 */             this.pnlStash.moveSelectedItem(1, x, y);
/*      */             
/*  885 */             this.btnFileSave.setEnabled(this.info.gdChar.hasChanged());
/*      */           } 
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  893 */         if (this.location == 1) {
/*  894 */           boolean success = DBStashItem.storeItem(this.selItem);
/*      */           
/*  896 */           if (success) setSelectedItem((GDItem)null, 0);
/*      */           
/*  898 */           GDMsgLogger.showLog((Component)this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_STORED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_STORED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*  905 */         if (this.location == 1) {
/*  906 */           boolean success = DBStashItem.storeItem(this.selItem);
/*      */           
/*  908 */           if (success) {
/*  909 */             this.pnlStash.deleteSelectedItem(3);
/*      */             
/*  911 */             setSelectedItem((GDItem)null, 0);
/*      */             
/*  913 */             this.btnFileSave.setEnabled(this.info.gdChar.hasChanged());
/*      */           } 
/*      */           
/*  916 */           GDMsgLogger.showLog((Component)this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_STORED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_STORED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/*  923 */         if (this.location == 2) {
/*  924 */           boolean success = this.pnlStash.addItem(this.selItem, 4, x, y);
/*      */           
/*  926 */           if (success && 
/*  927 */             GDStashFrame.iniConfig.sectRestrict.dbStashMove) {
/*  928 */             transferSelectedItem(5, x, y);
/*      */           }
/*      */ 
/*      */           
/*  932 */           this.btnFileSave.setEnabled(this.info.gdChar.hasChanged());
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 5:
/*  939 */         if (this.location == 2) {
/*  940 */           boolean success = DBStashItem.delete(this.selItem);
/*      */           
/*  942 */           if (success) {
/*  943 */             if (this.selItem.isStackable()) {
/*  944 */               GDItem item = DBStashItem.getStack(this.selItem);
/*      */               
/*  946 */               this.pnlTable.updateItem(item);
/*      */             } else {
/*  948 */               this.pnlTable.deleteItem();
/*      */             } 
/*      */             
/*  951 */             setSelectedItem(this.pnlTable.getSelectedItem(), 2);
/*      */           } 
/*      */           
/*  954 */           GDMsgLogger.showLog((Component)this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_DELETED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_DELETED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 6:
/*  961 */         if (this.location == 1) {
/*  962 */           GDItem item = getSelectedItem();
/*      */           
/*  964 */           if (item == null)
/*      */             break; 
/*  966 */           this.pnlStash.deleteSelectedItem(6);
/*      */           
/*  968 */           setSelectedItem((GDItem)null, 0);
/*      */           
/*  970 */           this.btnFileSave.setEnabled(this.info.gdChar.hasChanged());
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 7:
/*  977 */         if (this.location == 1 || this.location == 0) {
/*      */           
/*  979 */           GDContainerMapPane page = this.pnlStash.getCurrentPage();
/*      */           
/*  981 */           if (page == null)
/*      */             return; 
/*  983 */           List<GDItem> items = page.getItemList(7);
/*      */           
/*  985 */           for (GDItem item : items) {
/*  986 */             DBStashItem.storeItem(item);
/*      */           }
/*      */           
/*  989 */           GDMsgLogger.showLog((Component)this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_STORED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_STORED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 8:
/*  996 */         if (this.location == 1 || this.location == 0) {
/*      */           
/*  998 */           GDContainerMapPane page = this.pnlStash.getCurrentPage();
/*      */           
/* 1000 */           if (page == null)
/*      */             return; 
/* 1002 */           List<GDItem> items = page.getItemList(8);
/*      */           
/* 1004 */           for (GDItem item : items) {
/* 1005 */             boolean success = DBStashItem.storeItem(item);
/*      */             
/* 1007 */             if (success) page.deleteItem(item, 8, false);
/*      */           
/*      */           } 
/* 1010 */           page.layoutContainers();
/*      */           
/* 1012 */           setSelectedItem((GDItem)null, 0);
/*      */           
/* 1014 */           this.btnFileSave.setEnabled(this.info.gdChar.hasChanged());
/*      */           
/* 1016 */           GDMsgLogger.showLog((Component)this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_STORED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_STORED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 9:
/* 1023 */         if (this.location == 1 || this.location == 0) {
/*      */           
/* 1025 */           GDContainerMapPane page = this.pnlStash.getCurrentPage();
/*      */           
/* 1027 */           if (page == null)
/*      */             return; 
/* 1029 */           List<GDItem> items = page.getItemList(9);
/*      */           
/* 1031 */           for (GDItem item : items) {
/* 1032 */             page.deleteItem(item, 9, false);
/*      */           }
/*      */           
/* 1035 */           page.layoutContainers();
/*      */           
/* 1037 */           setSelectedItem((GDItem)null, 0);
/*      */           
/* 1039 */           this.btnFileSave.setEnabled(this.info.gdChar.hasChanged());
/*      */           
/* 1041 */           GDMsgLogger.showLog((Component)this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_DELETED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_DELETED"));
/*      */         } 
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Frame getFrame() {
/* 1050 */     return (Frame)this.frame;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void search(SelectionCriteria criteria) {
/* 1059 */     if (this.info == null)
/* 1060 */       return;  if (this.info.gdChar == null)
/*      */       return; 
/* 1062 */     criteria.noEnemyOnly = true;
/*      */     
/* 1064 */     List<GDItem> items = DBStashItem.getGDItemByCriteria(criteria, CriteriaCombination.Soulbound.SETTING, CriteriaCombination.SC_HC.SETTING, this.info.gdChar.isHardcore(), this.info.gdChar.getCharName());
/*      */     
/* 1066 */     if (criteria.combiMode == SelectionCriteria.CombinationMode.AND) {
/* 1067 */       List<GDItem> list = new LinkedList<>();
/*      */       
/* 1069 */       for (GDItem gdi : items) {
/* 1070 */         if (!DBStat.statsMeetCriteria(gdi.getStatList(), criteria))
/*      */           continue; 
/* 1072 */         if (criteria.dmgConversionFrom != null && 
/* 1073 */           !gdi.hasConvertIn(criteria.dmgConversionFrom)) {
/*      */           continue;
/*      */         }
/* 1076 */         if (criteria.dmgConversionTo != null && 
/* 1077 */           !gdi.hasConvertOut(criteria.dmgConversionTo)) {
/*      */           continue;
/*      */         }
/* 1080 */         if (criteria.petBonus && 
/* 1081 */           !gdi.hasPetBonus()) {
/*      */           continue;
/*      */         }
/* 1084 */         list.add(gdi);
/*      */       } 
/*      */       
/* 1087 */       items = list;
/*      */     } 
/*      */     
/* 1090 */     this.pnlTable.setItems(items);
/*      */     
/* 1092 */     GDMsgLogger.showLog((Component)this, GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_SEARCH"));
/*      */   }
/*      */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharInventoryPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */