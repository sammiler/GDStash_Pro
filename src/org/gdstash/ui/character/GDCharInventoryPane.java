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
/*      */   private class SearchItemsListener
/*      */     implements ActionListener
/*      */   {
/*      */     private SearchItemsListener() {}
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
/*      */     public void actionPerformed(ActionEvent e) {
/*  239 */       if (GDCharInventoryPane.this.dlgSearch == null) {
/*  240 */         GDCharInventoryPane.this.dlgSearch = new GDTabbedSearchDialog((Frame)GDCharInventoryPane.this.frame, GDCharInventoryPane.this, GDTabbedSearchDialog.Mode.TRANSFER);
/*      */       }
/*      */ 
/*      */       
/*  244 */       GDCharInventoryPane.this.dlgSearch.setVisible(true);
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
/*  278 */     this.frame = frame;
/*      */     
/*  280 */     this.strSave = GDStashFrame.iniConfig.sectDir.savePath;
/*  281 */     this.selItem = null;
/*  282 */     this.location = 0;
/*      */     
/*  284 */     this.dlgSearch = null;
/*      */     
/*  286 */     adjustUI();
/*      */     
/*  288 */     this.pnlMain = buildMainPanel();
/*      */ 
/*      */ 
/*      */     
/*  292 */     GroupLayout layout = null;
/*  293 */     GroupLayout.SequentialGroup hGroup = null;
/*  294 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/*  296 */     JScrollPane scroll = new JScrollPane(this.pnlMain);
/*  297 */     scroll.getVerticalScrollBar().setUnitIncrement(2 * GDStashFrame.iniConfig.sectUI.fontSize);
/*      */     
/*  299 */     layout = new GroupLayout((Container)this);
/*  300 */     setLayout(layout);
/*      */     
/*  302 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  305 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  308 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  311 */     hGroup
/*  312 */       .addGroup(layout.createParallelGroup()
/*  313 */         .addComponent(scroll));
/*  314 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  317 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  320 */     vGroup
/*  321 */       .addGroup(layout.createParallelGroup()
/*  322 */         .addComponent(scroll));
/*  323 */     layout.setVerticalGroup(vGroup);
/*      */   }
/*      */ 
/*      */   
/*      */   public void adjustUI() {
/*  328 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  329 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/*  330 */     if (fntButton == null) fntButton = fntLabel;
/*      */     
/*  332 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  333 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*      */ 
/*      */ 
/*      */     
/*  337 */     GDCharInfoList.adjustCharInfos(null, null);
/*      */     
/*  339 */     if (this.btnFileSave == null) {
/*  340 */       this.btnFileSave = new JButton();
/*      */       
/*  342 */       this.btnFileSave.setEnabled(false);
/*  343 */       this.btnFileSave.addActionListener(new FileSaveActionListener());
/*      */     } 
/*  345 */     this.btnFileSave.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_CHAR_SAVE"));
/*  346 */     this.btnFileSave.setIcon(GDImagePool.iconBtnSave24);
/*  347 */     this.btnFileSave.setFont(fntButton);
/*  348 */     GDStashFrame.setMnemonic(this.btnFileSave, "MNC_CHAR_SAVE");
/*      */     
/*  350 */     if (this.btnDBDel == null) {
/*  351 */       this.btnDBDel = new JButton();
/*      */       
/*  353 */       this.btnDBDel.setEnabled(false);
/*  354 */       this.btnDBDel.addActionListener(new DBDelActionListener());
/*      */     } 
/*  356 */     this.btnDBDel.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_ITEM_DEL_DB"));
/*  357 */     this.btnDBDel.setIcon(GDImagePool.iconDBDelete24);
/*  358 */     this.btnDBDel.setFont(fntButton);
/*  359 */     GDStashFrame.setMnemonic(this.btnDBDel, "MNC_ITEM_DEL_DB");
/*      */     
/*  361 */     if (this.btnDBCopy == null) {
/*  362 */       this.btnDBCopy = new JButton();
/*      */       
/*  364 */       this.btnDBCopy.setEnabled(false);
/*  365 */       this.btnDBCopy.addActionListener(new DBCopyActionListener());
/*      */     } 
/*  367 */     this.btnDBCopy.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_ITEM_COPY_DB"));
/*  368 */     this.btnDBCopy.setIcon(GDImagePool.iconItemCopy24);
/*  369 */     this.btnDBCopy.setFont(fntButton);
/*  370 */     GDStashFrame.setMnemonic(this.btnDBCopy, "MNC_ITEM_COPY_DB");
/*      */     
/*  372 */     if (this.btnDBMove == null) {
/*  373 */       this.btnDBMove = new JButton();
/*      */       
/*  375 */       this.btnDBMove.setEnabled(false);
/*  376 */       this.btnDBMove.addActionListener(new DBMoveActionListener());
/*      */     } 
/*  378 */     this.btnDBMove.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_ITEM_MOVE_DB"));
/*  379 */     this.btnDBMove.setIcon(GDImagePool.iconItemMove24);
/*  380 */     this.btnDBMove.setFont(fntButton);
/*  381 */     GDStashFrame.setMnemonic(this.btnDBMove, "MNC_ITEM_MOVE_DB");
/*      */     
/*  383 */     if (this.btnPageCopy == null) {
/*  384 */       this.btnPageCopy = new JButton();
/*      */       
/*  386 */       this.btnPageCopy.setEnabled(true);
/*  387 */       this.btnPageCopy.addActionListener(new PageCopyActionListener());
/*      */     } 
/*  389 */     this.btnPageCopy.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_PAGE_COPY_DB"));
/*  390 */     this.btnPageCopy.setIcon(GDImagePool.iconPageCopy24);
/*  391 */     this.btnPageCopy.setFont(fntButton);
/*      */ 
/*      */     
/*  394 */     if (this.btnPageMove == null) {
/*  395 */       this.btnPageMove = new JButton();
/*      */       
/*  397 */       this.btnPageMove.setEnabled(true);
/*  398 */       this.btnPageMove.addActionListener(new PageMoveActionListener());
/*      */     } 
/*  400 */     this.btnPageMove.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_PAGE_MOVE_DB"));
/*  401 */     this.btnPageMove.setIcon(GDImagePool.iconPageMove24);
/*  402 */     this.btnPageMove.setFont(fntButton);
/*      */ 
/*      */     
/*  405 */     if (this.btnStashDel == null) {
/*  406 */       this.btnStashDel = new JButton();
/*      */       
/*  408 */       this.btnStashDel.setEnabled(false);
/*  409 */       this.btnStashDel.addActionListener(new StashDelActionListener());
/*      */     } 
/*  411 */     this.btnStashDel.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_ITEM_DEL_CHAR"));
/*  412 */     this.btnStashDel.setIcon(GDImagePool.iconItemDelete24);
/*  413 */     this.btnStashDel.setFont(fntButton);
/*  414 */     GDStashFrame.setMnemonic(this.btnStashDel, "MNC_ITEM_DEL_CHAR");
/*      */     
/*  416 */     if (this.btnPageDel == null) {
/*  417 */       this.btnPageDel = new JButton();
/*      */       
/*  419 */       this.btnPageDel.setEnabled(true);
/*  420 */       this.btnPageDel.addActionListener(new PageDelActionListener());
/*      */     } 
/*  422 */     this.btnPageDel.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_PAGE_DEL"));
/*  423 */     this.btnPageDel.setIcon(GDImagePool.iconPageDelete24);
/*  424 */     this.btnPageDel.setFont(fntButton);
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
/*  435 */     if (this.btnSearch == null) {
/*  436 */       this.btnSearch = new JButton();
/*      */       
/*  438 */       this.btnSearch.addActionListener(new SearchItemsListener());
/*      */     } 
/*  440 */     this.btnSearch.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_SEARCH"));
/*  441 */     this.btnSearch.setIcon(GDImagePool.iconBtnSearch24);
/*  442 */     this.btnSearch.setFont(fntButton);
/*  443 */     GDStashFrame.setMnemonic(this.btnSearch, "MNC_SEARCH_SECOND");
/*      */     
/*  445 */     if (this.btnReload == null) {
/*  446 */       this.btnReload = new JButton();
/*      */       
/*  448 */       this.btnReload.addActionListener(new ReloadActionListener());
/*      */     } 
/*  450 */     this.btnReload.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_RELOAD"));
/*  451 */     this.btnReload.setIcon(GDImagePool.iconBtnReload24);
/*  452 */     this.btnReload.setFont(fntButton);
/*  453 */     GDStashFrame.setMnemonic(this.btnReload, "MNC_RELOAD");
/*      */     
/*  455 */     if (this.btnHelp == null) {
/*  456 */       this.btnHelp = new JButton();
/*      */       
/*  458 */       this.btnHelp.addActionListener((ActionListener)new GDHelpActionListener("04_transfer_char.html"));
/*      */     } 
/*  460 */     this.btnHelp.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_HELP"));
/*  461 */     this.btnHelp.setIcon(GDImagePool.iconBtnQuestion24);
/*  462 */     this.btnHelp.setFont(fntButton);
/*  463 */     GDStashFrame.setMnemonic(this.btnHelp, "MNC_HELP");
/*      */     
/*  465 */     if (this.pnlInfo == null) {
/*  466 */       this.pnlInfo = new GDItemInfoPane();
/*      */     }
/*  468 */     this.pnlInfo.adjustUI();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  473 */     if (this.pnlTable == null) {
/*  474 */       this.pnlTable = new GDItemNameTablePane(null, false);
/*  475 */       this.pnlTable.addActionListener(new TableActionListener());
/*      */       
/*  477 */       this.pnlTable.setMinimumSize(new Dimension(300, 200));
/*  478 */       this.pnlTable.setMaximumSize(new Dimension(600, 2000));
/*      */     } else {
/*  480 */       this.pnlTable.adjustUI();
/*      */     } 
/*      */     
/*  483 */     if (this.pnlStash == null) {
/*  484 */       this.pnlStash = new GDCharStashPane(this.frame, this);
/*      */     } else {
/*  486 */       this.pnlStash.adjustUI();
/*      */     } 
/*      */     
/*  489 */     if (this.dlgSearch != null) {
/*  490 */       this.dlgSearch.adjustUI();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateUI() {
/*  496 */     super.updateUI();
/*      */     
/*  498 */     if (this.dlgSearch != null) {
/*  499 */       SwingUtilities.updateComponentTreeUI((Component)this.dlgSearch);
/*      */     }
/*      */   }
/*      */   
/*      */   public void refresh() {
/*  504 */     if (this.pnlTable != null) this.pnlTable.refresh(); 
/*  505 */     if (this.pnlInfo != null) this.pnlInfo.refresh(); 
/*  506 */     if (this.pnlStash != null) this.pnlStash.refresh();
/*      */ 
/*      */     
/*  509 */     refreshCharSelection();
/*      */   }
/*      */   
/*      */   private JPanel buildMainPanel() {
/*  513 */     JPanel panel = new JPanel();
/*      */     
/*  515 */     GroupLayout layout = null;
/*  516 */     GroupLayout.SequentialGroup hGroup = null;
/*  517 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/*  519 */     JPanel pnlTable = buildTablePanel();
/*      */     
/*  521 */     JPanel pnlItemInfo = buildInfoPanel();
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
/*  533 */     layout = new GroupLayout(panel);
/*  534 */     panel.setLayout(layout);
/*      */     
/*  536 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  539 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  542 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  545 */     hGroup
/*  546 */       .addGroup(layout.createParallelGroup()
/*  547 */         .addComponent(pnlTable))
/*  548 */       .addGroup(layout.createParallelGroup()
/*  549 */         .addComponent(pnlItemInfo))
/*  550 */       .addGroup(layout.createParallelGroup()
/*  551 */         .addComponent((Component)this.pnlStash));
/*  552 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  555 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  558 */     vGroup
/*  559 */       .addGroup(layout.createParallelGroup()
/*  560 */         .addComponent(pnlTable)
/*  561 */         .addComponent(pnlItemInfo)
/*  562 */         .addComponent((Component)this.pnlStash));
/*  563 */     layout.setVerticalGroup(vGroup);
/*      */ 
/*      */ 
/*      */     
/*  567 */     return panel;
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
/*  592 */     GroupLayout layout = null;
/*  593 */     GroupLayout.SequentialGroup hGroup = null;
/*  594 */     GroupLayout.SequentialGroup vGroup = null;
/*      */ 
/*      */ 
/*      */     
/*  598 */     JPanel panel = new JPanel();
/*      */     
/*  600 */     layout = new GroupLayout(panel);
/*  601 */     panel.setLayout(layout);
/*      */     
/*  603 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  606 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  609 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  612 */     hGroup
/*  613 */       .addGroup(layout.createParallelGroup()
/*  614 */         .addComponent(this.btnSearch)
/*  615 */         .addComponent((Component)this.pnlTable));
/*  616 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  619 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  622 */     vGroup
/*  623 */       .addGroup(layout.createParallelGroup()
/*  624 */         .addComponent(this.btnSearch))
/*  625 */       .addGroup(layout.createParallelGroup()
/*  626 */         .addComponent((Component)this.pnlTable));
/*  627 */     layout.setVerticalGroup(vGroup);
/*      */     
/*  629 */     layout.linkSize(0, new Component[] { this.btnSearch, (Component)this.pnlTable });
/*      */     
/*  631 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildInfoPanel() {
/*  635 */     GroupLayout layout = null;
/*  636 */     GroupLayout.SequentialGroup hGroup = null;
/*  637 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/*  639 */     JPanel panel = new JPanel();
/*      */     
/*  641 */     JPanel pnlButtonFile = buildFileButtonPanel();
/*  642 */     JPanel pnlButtonOper = buildOperationButtonPanel();
/*      */     
/*  644 */     layout = new GroupLayout(panel);
/*  645 */     panel.setLayout(layout);
/*      */     
/*  647 */     layout.setAutoCreateGaps(false);
/*      */ 
/*      */     
/*  650 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  653 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  656 */     hGroup
/*  657 */       .addGroup(layout.createParallelGroup()
/*  658 */         .addComponent(pnlButtonFile)
/*  659 */         .addComponent(pnlButtonOper)
/*  660 */         .addComponent((Component)this.pnlInfo));
/*  661 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  664 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  667 */     vGroup
/*  668 */       .addGroup(layout.createParallelGroup()
/*  669 */         .addComponent(pnlButtonFile))
/*  670 */       .addGroup(layout.createParallelGroup()
/*  671 */         .addComponent(pnlButtonOper))
/*  672 */       .addGroup(layout.createParallelGroup()
/*  673 */         .addComponent((Component)this.pnlInfo));
/*  674 */     layout.setVerticalGroup(vGroup);
/*      */     
/*  676 */     layout.linkSize(0, new Component[] { pnlButtonFile, pnlButtonOper });
/*  677 */     layout.linkSize(0, new Component[] { pnlButtonFile, (Component)this.pnlInfo });
/*      */ 
/*      */ 
/*      */     
/*  681 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildFileButtonPanel() {
/*  685 */     BorderLayout layout = new BorderLayout();
/*      */     
/*  687 */     JPanel panel = new JPanel();
/*      */     
/*  689 */     panel.setLayout(layout);
/*      */     
/*  691 */     panel.add(this.btnFileSave, "Center");
/*  692 */     panel.add(this.btnReload, "West");
/*  693 */     panel.add(this.btnHelp, "East");
/*      */     
/*  695 */     int size = 12;
/*  696 */     if (GDStashFrame.iniConfig != null) size = GDStashFrame.iniConfig.sectUI.fontSize; 
/*  697 */     Dimension dimPref = new Dimension(30 * size, 2 * size);
/*  698 */     Dimension dimMax = new Dimension(50 * size, 2 * size);
/*      */     
/*  700 */     panel.setPreferredSize(dimPref);
/*  701 */     panel.setMaximumSize(dimMax);
/*      */     
/*  703 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildOperationButtonPanel() {
/*  707 */     GroupLayout layout = null;
/*  708 */     GroupLayout.SequentialGroup hGroup = null;
/*  709 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/*  711 */     JPanel panel = new JPanel();
/*      */     
/*  713 */     layout = new GroupLayout(panel);
/*  714 */     panel.setLayout(layout);
/*      */     
/*  716 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  719 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  722 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  725 */     hGroup
/*  726 */       .addGroup(layout.createParallelGroup()
/*  727 */         .addComponent(this.btnDBCopy)
/*  728 */         .addComponent(this.btnDBMove)
/*  729 */         .addComponent(this.btnStashDel)
/*  730 */         .addComponent(this.btnDBDel))
/*  731 */       .addGroup(layout.createParallelGroup()
/*  732 */         .addComponent(this.btnPageCopy)
/*  733 */         .addComponent(this.btnPageMove)
/*  734 */         .addComponent(this.btnPageDel));
/*  735 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  738 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  741 */     vGroup
/*  742 */       .addGroup(layout.createParallelGroup()
/*  743 */         .addComponent(this.btnDBCopy)
/*  744 */         .addComponent(this.btnPageCopy))
/*  745 */       .addGroup(layout.createParallelGroup()
/*  746 */         .addComponent(this.btnDBMove)
/*  747 */         .addComponent(this.btnPageMove))
/*  748 */       .addGroup(layout.createParallelGroup()
/*  749 */         .addComponent(this.btnStashDel)
/*  750 */         .addComponent(this.btnPageDel))
/*  751 */       .addGroup(layout.createParallelGroup()
/*  752 */         .addComponent(this.btnDBDel));
/*  753 */     layout.setVerticalGroup(vGroup);
/*      */     
/*  755 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnDBMove });
/*  756 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnStashDel });
/*  757 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnDBDel });
/*  758 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnPageCopy });
/*  759 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnPageMove });
/*  760 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnPageDel });
/*      */     
/*  762 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnDBMove });
/*  763 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnStashDel });
/*  764 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnDBDel });
/*  765 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnPageCopy });
/*  766 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnPageMove });
/*  767 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnPageDel });
/*      */     
/*  769 */     return panel;
/*      */   }
/*      */   
/*      */   public void checkSaveButton() {
/*  773 */     if (this.info == null) {
/*  774 */       this.btnFileSave.setEnabled(false);
/*      */     }
/*  776 */     else if (this.info.gdChar == null) {
/*  777 */       this.btnFileSave.setEnabled(false);
/*      */     } else {
/*  779 */       this.btnFileSave.setEnabled(this.info.gdChar.hasChanged());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateConfig() {
/*  785 */     if (this.dlgSearch != null) this.dlgSearch.updateConfig(); 
/*      */   }
/*      */   
/*      */   public void initCharSelection() {
/*  789 */     if (this.pnlStash != null) this.pnlStash.initCharSelection(); 
/*      */   }
/*      */   
/*      */   public void refreshCharSelection() {
/*  793 */     this.pnlStash.refreshCharSelection();
/*      */   }
/*      */   
/*      */   public void refreshCharInfo(GDCharInfoList.GDCharFileInfo info) {
/*  797 */     if (info == null)
/*  798 */       return;  if (this.info == null)
/*      */       return; 
/*  800 */     if (this.info.fileName.equals(info.fileName)) {
/*  801 */       this.info = info;
/*      */       
/*  803 */       if (this.pnlStash != null) this.pnlStash.setChar(info); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renameCharDir(File fDirOld, File fDirNew) {
/*  808 */     if (this.pnlStash != null) this.pnlStash.renameCharDir(fDirOld, fDirNew);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setChar(GDCharInfoList.GDCharFileInfo info) {
/*  817 */     if (this.info == info)
/*      */       return; 
/*  819 */     this.info = info;
/*      */     
/*  821 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSoulbound) this.pnlTable.setItems(null);
/*      */     
/*  823 */     checkSaveButton();
/*      */   }

    @Override
    public void setStash(GDStashInfoList.GDStashFileInfo paramGDStashFileInfo) {

    }

    /*      */
/*      */ 
/*      */ 
/*      */   

/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemLocation() {
/*  834 */     return this.location;
/*      */   }
/*      */ 
/*      */   
/*      */   public GDItem getSelectedItem() {
/*  839 */     return this.selItem;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelectedItem(GDItem item, int location) {
/*  845 */     if (location == 1) {
/*  846 */       this.pnlTable.clearSelection();
/*      */     }
/*      */     
/*  849 */     this.selItem = item;
/*  850 */     this.location = location;
/*      */     
/*  852 */     boolean hardcore = false;
/*  853 */     String charName = "";
/*      */     
/*  855 */     if (this.info != null && 
/*  856 */       this.info.gdChar != null) {
/*  857 */       hardcore = this.info.gdChar.isHardcore();
/*  858 */       charName = this.info.gdChar.getCharName();
/*      */     } 
/*      */ 
/*      */     
/*  862 */     this.pnlInfo.setItemInfo(item, hardcore, charName);
/*      */     
/*  864 */     switch (location) {
/*      */       case 1:
/*  866 */         this.btnDBCopy.setEnabled((item != null));
/*  867 */         this.btnDBMove.setEnabled((item != null && item.getContainerType() != 6));
/*  868 */         this.btnStashDel.setEnabled((item != null && item.getContainerType() != 6));
/*  869 */         this.btnDBDel.setEnabled(false);
/*  870 */         this.btnPageCopy.setEnabled(true);
/*  871 */         this.btnPageMove.setEnabled(true);
/*  872 */         this.btnPageDel.setEnabled(true);
/*      */         return;
/*      */ 
/*      */       
/*      */       case 2:
/*  877 */         this.btnDBCopy.setEnabled(false);
/*  878 */         this.btnDBMove.setEnabled(false);
/*  879 */         this.btnStashDel.setEnabled(false);
/*  880 */         this.btnDBDel.setEnabled((item != null));
/*  881 */         this.btnPageCopy.setEnabled(false);
/*  882 */         this.btnPageMove.setEnabled(false);
/*  883 */         this.btnPageDel.setEnabled(false);
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/*  888 */     this.btnDBCopy.setEnabled(false);
/*  889 */     this.btnDBMove.setEnabled(false);
/*  890 */     this.btnStashDel.setEnabled(false);
/*  891 */     this.btnDBDel.setEnabled(false);
/*  892 */     this.btnPageCopy.setEnabled(true);
/*  893 */     this.btnPageMove.setEnabled(true);
/*  894 */     this.btnPageDel.setEnabled(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transferSelectedItem(int action, int x, int y) {
/*  902 */     switch (action) {
/*      */       
/*      */       case 1:
/*  905 */         if (this.location == 1) {
/*  906 */           GDItem item = this.pnlStash.getSelectedItem();
/*      */           
/*  908 */           if (item != null) {
/*  909 */             this.pnlStash.moveSelectedItem(1, x, y);
/*      */             
/*  911 */             this.btnFileSave.setEnabled(this.info.gdChar.hasChanged());
/*      */           } 
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  919 */         if (this.location == 1) {
/*  920 */           boolean success = DBStashItem.storeItem(this.selItem);
/*      */           
/*  922 */           if (success) setSelectedItem((GDItem)null, 0);
/*      */           
/*  924 */           GDMsgLogger.showLog((Component)this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_STORED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_STORED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*  931 */         if (this.location == 1) {
/*  932 */           boolean success = DBStashItem.storeItem(this.selItem);
/*      */           
/*  934 */           if (success) {
/*  935 */             this.pnlStash.deleteSelectedItem(3);
/*      */             
/*  937 */             setSelectedItem((GDItem)null, 0);
/*      */             
/*  939 */             this.btnFileSave.setEnabled(this.info.gdChar.hasChanged());
/*      */           } 
/*      */           
/*  942 */           GDMsgLogger.showLog((Component)this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_STORED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_STORED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/*  949 */         if (this.location == 2) {
/*  950 */           boolean success = this.pnlStash.addItem(this.selItem, 4, x, y);
/*      */           
/*  952 */           if (success && 
/*  953 */             GDStashFrame.iniConfig.sectRestrict.dbStashMove) {
/*  954 */             transferSelectedItem(5, x, y);
/*      */           }
/*      */ 
/*      */           
/*  958 */           this.btnFileSave.setEnabled(this.info.gdChar.hasChanged());
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 5:
/*  965 */         if (this.location == 2) {
/*  966 */           boolean success = DBStashItem.delete(this.selItem);
/*      */           
/*  968 */           if (success) {
/*  969 */             if (this.selItem.isStackable()) {
/*  970 */               GDItem item = DBStashItem.getStack(this.selItem);
/*      */               
/*  972 */               this.pnlTable.updateItem(item);
/*      */             } else {
/*  974 */               this.pnlTable.deleteItem();
/*      */             } 
/*      */             
/*  977 */             setSelectedItem(this.pnlTable.getSelectedItem(), 2);
/*      */           } 
/*      */           
/*  980 */           GDMsgLogger.showLog((Component)this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_DELETED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_DELETED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 6:
/*  987 */         if (this.location == 1) {
/*  988 */           GDItem item = getSelectedItem();
/*      */           
/*  990 */           if (item == null)
/*      */             break; 
/*  992 */           this.pnlStash.deleteSelectedItem(6);
/*      */           
/*  994 */           setSelectedItem((GDItem)null, 0);
/*      */           
/*  996 */           this.btnFileSave.setEnabled(this.info.gdChar.hasChanged());
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 7:
/* 1003 */         if (this.location == 1 || this.location == 0) {
/*      */           
/* 1005 */           GDContainerMapPane page = this.pnlStash.getCurrentPage();
/*      */           
/* 1007 */           if (page == null)
/*      */             return; 
/* 1009 */           List<GDItem> items = page.getItemList(7);
/*      */           
/* 1011 */           for (GDItem item : items) {
/* 1012 */             DBStashItem.storeItem(item);
/*      */           }
/*      */           
/* 1015 */           GDMsgLogger.showLog((Component)this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_STORED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_STORED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 8:
/* 1022 */         if (this.location == 1 || this.location == 0) {
/*      */           
/* 1024 */           GDContainerMapPane page = this.pnlStash.getCurrentPage();
/*      */           
/* 1026 */           if (page == null)
/*      */             return; 
/* 1028 */           List<GDItem> items = page.getItemList(8);
/*      */           
/* 1030 */           for (GDItem item : items) {
/* 1031 */             boolean success = DBStashItem.storeItem(item);
/*      */             
/* 1033 */             if (success) page.deleteItem(item, 8, false);
/*      */           
/*      */           } 
/* 1036 */           page.layoutContainers();
/*      */           
/* 1038 */           setSelectedItem((GDItem)null, 0);
/*      */           
/* 1040 */           this.btnFileSave.setEnabled(this.info.gdChar.hasChanged());
/*      */           
/* 1042 */           GDMsgLogger.showLog((Component)this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_STORED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_STORED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 9:
/* 1049 */         if (this.location == 1 || this.location == 0) {
/*      */           
/* 1051 */           GDContainerMapPane page = this.pnlStash.getCurrentPage();
/*      */           
/* 1053 */           if (page == null)
/*      */             return; 
/* 1055 */           List<GDItem> items = page.getItemList(9);
/*      */           
/* 1057 */           for (GDItem item : items) {
/* 1058 */             page.deleteItem(item, 9, false);
/*      */           }
/*      */           
/* 1061 */           page.layoutContainers();
/*      */           
/* 1063 */           setSelectedItem((GDItem)null, 0);
/*      */           
/* 1065 */           this.btnFileSave.setEnabled(this.info.gdChar.hasChanged());
/*      */           
/* 1067 */           GDMsgLogger.showLog((Component)this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_DELETED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_DELETED"));
/*      */         } 
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Frame getFrame() {
/* 1076 */     return (Frame)this.frame;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void search(SelectionCriteria criteria) {
/* 1085 */     if (this.info == null)
/* 1086 */       return;  if (this.info.gdChar == null)
/*      */       return; 
/* 1088 */     criteria.noEnemyOnly = true;
/*      */     
/* 1090 */     List<GDItem> items = DBStashItem.getGDItemByCriteria(criteria, CriteriaCombination.Soulbound.SETTING, CriteriaCombination.SC_HC.SETTING, this.info.gdChar.isHardcore(), this.info.gdChar.getCharName());
/*      */     
/* 1092 */     if (criteria.combiMode == SelectionCriteria.CombinationMode.AND) {
/* 1093 */       List<GDItem> list = new LinkedList<>();
/*      */       
/* 1095 */       for (GDItem gdi : items) {
/* 1096 */         if (!DBStat.statsMeetCriteria(gdi.getStatList(), criteria))
/*      */           continue; 
/* 1098 */         if (criteria.dmgConversionFrom != null && 
/* 1099 */           !gdi.hasConvertIn(criteria.dmgConversionFrom)) {
/*      */           continue;
/*      */         }
/* 1102 */         if (criteria.dmgConversionTo != null && 
/* 1103 */           !gdi.hasConvertOut(criteria.dmgConversionTo)) {
/*      */           continue;
/*      */         }
/* 1106 */         if (criteria.petBonus && 
/* 1107 */           !gdi.hasPetBonus()) {
/*      */           continue;
/*      */         }
/* 1110 */         list.add(gdi);
/*      */       } 
/*      */       
/* 1113 */       items = list;
/*      */     } 
/*      */     
/* 1116 */     this.pnlTable.setItems(items);
/*      */     
/* 1118 */     GDMsgLogger.showLog((Component)this, GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_SEARCH"));
/*      */   }
/*      */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\character\GDCharInventoryPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */