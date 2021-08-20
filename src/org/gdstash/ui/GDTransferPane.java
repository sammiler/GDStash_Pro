/*      */ package org.gdstash.ui;
/*      */ 
/*      */ import java.awt.*;
/*      */
/*      */
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
/*      */ import javax.swing.*;
/*      */
/*      */
/*      */ import org.gdstash.db.DBStashItem;
/*      */ import org.gdstash.db.DBStat;
import org.gdstash.db.SelectionCriteria;
/*      */ import org.gdstash.db.criteria.CriteriaCombination;
import org.gdstash.item.GDItem;
/*      */ import org.gdstash.ui.stash.GDContainerMapPane;
/*      */ import org.gdstash.ui.stash.GDSharedStashPane;
import org.gdstash.ui.util.AdjustablePanel;
import org.gdstash.ui.util.GDCharInfoList;
import org.gdstash.ui.util.GDStashInfoList;
/*      */ import org.gdstash.util.*;
/*      */
/*      */
/*      */

/*      */
/*      */ public class GDTransferPane extends AdjustablePanel implements GDUITransfer, GDUISearch {
/*      */   private static final int STASHTYPE_UNKNOWN = 0;
/*      */   private static final int STASHTYPE_SOFTCORE = 1;
/*      */   private static final int STASHTYPE_HARDCORE = 2;
/*      */   private GDStashFrame frame;
/*      */   private GDSharedStashPane pnlStash;
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
/*      */   private GDStashInfoList.GDStashFileInfo info;
/*      */   private GDItem selItem;
/*      */   private int location;
/*      */   private JPanel pnlMain;
/*      */   private GDTabbedSearchDialog dlgSearch;
/*      */   private int stashType;
/*      */   
/*      */   private class TableActionListener implements ActionListener { public void actionPerformed(ActionEvent ev) {
/*   50 */       GDItem item = GDTransferPane.this.pnlTable.getSelectedItem();
/*      */       
/*   52 */       GDTransferPane.this.setSelectedItem(item, 2);
/*      */       
/*   54 */       GDMsgLogger.showLog((Component)GDTransferPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     }
/*      */     private TableActionListener() {} }
/*      */   
/*      */   private class StashReloadActionListener implements ActionListener { private StashReloadActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*   61 */       if (GDTransferPane.this.info.gdStash != null && GDTransferPane.this.info.gdStash.hasChanged() && 
/*   62 */         GDStashFrame.iniConfig.sectRestrict.dbStashMove) {
/*   63 */         JButton[] buttons = new JButton[2];
/*      */         
/*   65 */         buttons[0] = new JButton(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_YES"), GDImagePool.iconBtnOk24);
/*   66 */         buttons[1] = new JButton(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_NO"), GDImagePool.iconBtnCancel24);
/*      */         
/*   68 */         String tag = "TXT_RELOAD_STASH";
/*      */         
/*   70 */         int i = GDFlexDialog.showDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, tag), buttons, 3, (Frame)null, true);
/*      */         
/*   72 */         if (i == -1)
/*   73 */           return;  if (i == 1) {
/*      */           return;
/*      */         }
/*      */       } 
/*   77 */       GDStashInfoList.findStashes(GDTransferPane.this.frame, GDTransferPane.this.info, null);
/*      */ 
/*      */       
/*   80 */       if (GDTransferPane.this.info != null) {
/*   81 */         for (GDStashInfoList.GDStashFileInfo si : GDStashInfoList.gdStashFileInfos) {
/*   82 */           if (si.fileName.equals(GDTransferPane.this.info.fileName)) {
/*   83 */             GDTransferPane.this.info = si;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/*   90 */       if (GDTransferPane.this.frame.pnlTransfer != null) {
/*   91 */         GDTransferPane.this.frame.pnlTransfer.refreshStashInfo(GDTransferPane.this.info);
/*      */       }
/*   93 */       if (GDTransferPane.this.frame.pnlCraft != null) {
/*   94 */         GDTransferPane.this.frame.pnlCraft.refreshStashInfo(GDTransferPane.this.info);
/*      */       }
/*      */       
/*   97 */       GDMsgLogger.showLog((Component)GDTransferPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "MESSAGES"), GDLog.MessageType.Info, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"), false, false);
/*      */     } }
/*      */   
/*      */   private class DBDelActionListener implements ActionListener {
/*      */     private DBDelActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  104 */       GDItem item = GDTransferPane.this.pnlTable.getSelectedItem();
/*      */       
/*  106 */       if (item == null)
/*      */         return; 
/*  108 */       GDTransferPane.this.transferSelectedItem(5, 0, 0);
/*      */       
/*  110 */       GDMsgLogger.showLog((Component)GDTransferPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     }
/*      */   }
/*      */   
/*      */   private class DBCopyActionListener implements ActionListener { private DBCopyActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  117 */       GDItem item = GDTransferPane.this.getSelectedItem();
/*      */       
/*  119 */       if (item == null)
/*      */         return; 
/*  121 */       GDTransferPane.this.transferSelectedItem(2, 0, 0);
/*      */       
/*  123 */       GDMsgLogger.showLog((Component)GDTransferPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     } }
/*      */   
/*      */   private class DBMoveActionListener implements ActionListener {
/*      */     private DBMoveActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  130 */       GDItem item = GDTransferPane.this.getSelectedItem();
/*      */       
/*  132 */       if (item == null)
/*      */         return; 
/*  134 */       GDTransferPane.this.transferSelectedItem(3, 0, 0);
/*      */       
/*  136 */       GDMsgLogger.showLog((Component)GDTransferPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     }
/*      */   }
/*      */   
/*      */   private class PageCopyActionListener implements ActionListener { private PageCopyActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  143 */       if (GDTransferPane.this.pnlStash.getCurrentPage() == null)
/*      */         return; 
/*  145 */       GDTransferPane.this.transferSelectedItem(7, 0, 0);
/*      */       
/*  147 */       GDMsgLogger.showLog((Component)GDTransferPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     } }
/*      */   
/*      */   private class PageMoveActionListener implements ActionListener {
/*      */     private PageMoveActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  154 */       if (GDTransferPane.this.pnlStash.getCurrentPage() == null)
/*      */         return; 
/*  156 */       GDTransferPane.this.transferSelectedItem(8, 0, 0);
/*      */       
/*  158 */       GDMsgLogger.showLog((Component)GDTransferPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     }
/*      */   }
/*      */   
/*      */   private class StashDelActionListener implements ActionListener { private StashDelActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  165 */       GDTransferPane.this.transferSelectedItem(6, 0, 0);
/*      */       
/*  167 */       GDMsgLogger.showLog((Component)GDTransferPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     } }
/*      */   
/*      */   private class PageDelActionListener implements ActionListener {
/*      */     private PageDelActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  174 */       if (GDTransferPane.this.pnlStash.getCurrentPage() == null)
/*      */         return; 
/*  176 */       GDTransferPane.this.transferSelectedItem(9, 0, 0);
/*      */       
/*  178 */       GDMsgLogger.showLog((Component)GDTransferPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     }
/*      */   }
/*      */   
/*      */   private class FileSaveActionListener implements ActionListener {
/*      */     private FileSaveActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  186 */       if (GDTransferPane.this.info == null)
/*  187 */         return;  if (GDTransferPane.this.info.gdStash == null)
/*      */         return; 
/*  189 */       if (GDTransferPane.this.frame.isCloudSaveDir() && 
/*  190 */         GDStashFrame.isGrimDawnRunning()) {
/*  191 */         GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_CLOUD_SAVE"));
/*  192 */         GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_CLOSE"));
/*      */         
/*  194 */         GDMsgLogger.showLog(GDTransferPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_RUNNING"), GDLog.MessageType.Info, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_RUNNING"));
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  200 */       boolean success = false;
/*      */       
/*      */       try {
/*  203 */         boolean update = GDTransferPane.this.updateStashFile();
/*      */         
/*  205 */         GDTransferPane.this.info.gdStash.write();
/*      */         
/*  207 */         if (update) {
/*      */ 
/*      */           
/*  210 */           GDTransferPane.this.info.setFile(GDTransferPane.this.info.gdStash.getFile());
/*  211 */           GDStashFrame.iniConfig.sectHistory.lastStash = GDTransferPane.this.info.fileName;
/*      */           
/*  213 */           GDTransferPane.this.btnReload.doClick();
/*      */         } 
/*      */         
/*  216 */         success = true;
/*      */       }
/*  218 */       catch (IOException ex) {
/*  219 */         GDMsgLogger.addError(ex);
/*      */       } 
/*      */       
/*  222 */       if (success) {
/*  223 */         GDTransferPane.this.btnFileSave.setEnabled(GDTransferPane.this.info.gdStash.hasChanged());
/*      */       }
/*      */       
/*  226 */       GDMsgLogger.showLog(GDTransferPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_STASH_SAVE"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_STASH_SAVE"));
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
/*  260 */       if (GDTransferPane.this.dlgSearch == null) {
/*  261 */         GDTransferPane.this.dlgSearch = new GDTabbedSearchDialog(GDTransferPane.this.frame, GDTransferPane.this, GDTabbedSearchDialog.Mode.TRANSFER, SelectionCriteria.SoulboundMode.NONBOUND);
/*      */       }
/*      */ 
/*      */       
/*  265 */       GDTransferPane.this.dlgSearch.setVisible(true);
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
/*      */   public GDTransferPane(GDStashInfoList.GDStashFileInfo info, GDStashFrame frame) {
/*  325 */     this.info = info;
/*  326 */     this.frame = frame;
/*      */     
/*  328 */     determineStashType();
/*      */     
/*  330 */     this.strSave = GDStashFrame.iniConfig.sectDir.savePath;
/*  331 */     this.selItem = null;
/*  332 */     this.location = 0;
/*      */     
/*  334 */     this.dlgSearch = null;
/*      */     
/*  336 */     adjustUI();
/*      */ 
/*      */ 
/*      */     
/*  340 */     GroupLayout layout = null;
/*  341 */     GroupLayout.SequentialGroup hGroup = null;
/*  342 */     GroupLayout.SequentialGroup vGroup = null;
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
/*  361 */     this.pnlMain = buildMainPanel();
/*  362 */     JScrollPane scroll = new JScrollPane(this.pnlMain);
/*  363 */     scroll.getVerticalScrollBar().setUnitIncrement(2 * GDStashFrame.iniConfig.sectUI.fontSize);
/*      */     
/*  365 */     layout = new GroupLayout((Container)this);
/*  366 */     setLayout(layout);
/*      */     
/*  368 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  371 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  374 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  377 */     hGroup
/*  378 */       .addGroup(layout.createParallelGroup()
/*  379 */         .addComponent(scroll));
/*  380 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  383 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  386 */     vGroup
/*  387 */       .addGroup(layout.createParallelGroup()
/*  388 */         .addComponent(scroll));
/*  389 */     layout.setVerticalGroup(vGroup);
/*      */   }
/*      */ 
/*      */   
/*      */   public void adjustUI() {
/*  394 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  395 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/*  396 */     if (fntButton == null) fntButton = fntLabel;
/*      */     
/*  398 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  399 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*      */     
/*  401 */     if (this.btnFileSave == null) {
/*  402 */       this.btnFileSave = new JButton();
/*      */       
/*  404 */       this.btnFileSave.setEnabled(false);
/*  405 */       this.btnFileSave.addActionListener(new FileSaveActionListener());
/*      */     } 
/*  407 */     this.btnFileSave.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_STASH_SAVE"));
/*  408 */     this.btnFileSave.setIcon(GDImagePool.iconBtnSave24);
/*  409 */     this.btnFileSave.setFont(fntButton);
/*  410 */     GDStashFrame.setMnemonic(this.btnFileSave, "MNC_STASH_SAVE");
/*      */     
/*  412 */     if (this.btnDBDel == null) {
/*  413 */       this.btnDBDel = new JButton();
/*      */       
/*  415 */       this.btnDBDel.setEnabled(false);
/*  416 */       this.btnDBDel.addActionListener(new DBDelActionListener());
/*      */     } 
/*  418 */     this.btnDBDel.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_ITEM_DEL_DB"));
/*  419 */     this.btnDBDel.setIcon(GDImagePool.iconDBDelete24);
/*  420 */     this.btnDBDel.setFont(fntButton);
/*  421 */     GDStashFrame.setMnemonic(this.btnDBDel, "MNC_ITEM_DEL_DB");
/*      */     
/*  423 */     if (this.btnDBCopy == null) {
/*  424 */       this.btnDBCopy = new JButton();
/*      */       
/*  426 */       this.btnDBCopy.setEnabled(false);
/*  427 */       this.btnDBCopy.addActionListener(new DBCopyActionListener());
/*      */     } 
/*  429 */     this.btnDBCopy.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_ITEM_COPY_DB"));
/*  430 */     this.btnDBCopy.setIcon(GDImagePool.iconItemCopy24);
/*  431 */     this.btnDBCopy.setFont(fntButton);
/*  432 */     GDStashFrame.setMnemonic(this.btnDBCopy, "MNC_ITEM_COPY_DB");
/*      */     
/*  434 */     if (this.btnDBMove == null) {
/*  435 */       this.btnDBMove = new JButton();
/*      */       
/*  437 */       this.btnDBMove.setEnabled(false);
/*  438 */       this.btnDBMove.addActionListener(new DBMoveActionListener());
/*      */     } 
/*  440 */     this.btnDBMove.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_ITEM_MOVE_DB"));
/*  441 */     this.btnDBMove.setIcon(GDImagePool.iconItemMove24);
/*  442 */     this.btnDBMove.setFont(fntButton);
/*  443 */     GDStashFrame.setMnemonic(this.btnDBMove, "MNC_ITEM_MOVE_DB");
/*      */     
/*  445 */     if (this.btnPageCopy == null) {
/*  446 */       this.btnPageCopy = new JButton();
/*      */       
/*  448 */       this.btnPageCopy.setEnabled(true);
/*  449 */       this.btnPageCopy.addActionListener(new PageCopyActionListener());
/*      */     } 
/*  451 */     this.btnPageCopy.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_PAGE_COPY_DB"));
/*  452 */     this.btnPageCopy.setIcon(GDImagePool.iconPageCopy24);
/*  453 */     this.btnPageCopy.setFont(fntButton);
/*      */ 
/*      */     
/*  456 */     if (this.btnPageMove == null) {
/*  457 */       this.btnPageMove = new JButton();
/*      */       
/*  459 */       this.btnPageMove.setEnabled(true);
/*  460 */       this.btnPageMove.addActionListener(new PageMoveActionListener());
/*      */     } 
/*  462 */     this.btnPageMove.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_PAGE_MOVE_DB"));
/*  463 */     this.btnPageMove.setIcon(GDImagePool.iconPageMove24);
/*  464 */     this.btnPageMove.setFont(fntButton);
/*      */ 
/*      */     
/*  467 */     if (this.btnStashDel == null) {
/*  468 */       this.btnStashDel = new JButton();
/*      */       
/*  470 */       this.btnStashDel.setEnabled(false);
/*  471 */       this.btnStashDel.addActionListener(new StashDelActionListener());
/*      */     } 
/*  473 */     this.btnStashDel.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_ITEM_DEL_STASH"));
/*  474 */     this.btnStashDel.setIcon(GDImagePool.iconItemDelete24);
/*  475 */     this.btnStashDel.setFont(fntButton);
/*  476 */     GDStashFrame.setMnemonic(this.btnStashDel, "MNC_ITEM_DEL_STASH");
/*      */     
/*  478 */     if (this.btnPageDel == null) {
/*  479 */       this.btnPageDel = new JButton();
/*      */       
/*  481 */       this.btnPageDel.setEnabled(true);
/*  482 */       this.btnPageDel.addActionListener(new PageDelActionListener());
/*      */     } 
/*  484 */     this.btnPageDel.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_PAGE_DEL"));
/*  485 */     this.btnPageDel.setIcon(GDImagePool.iconPageDelete24);
/*  486 */     this.btnPageDel.setFont(fntButton);
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
/*  497 */     if (this.btnSearch == null) {
/*  498 */       this.btnSearch = new JButton();
/*      */       
/*  500 */       this.btnSearch.addActionListener(new SearchItemsListener());
/*      */     } 
/*  502 */     this.btnSearch.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_SEARCH"));
/*  503 */     this.btnSearch.setIcon(GDImagePool.iconBtnSearch24);
/*  504 */     this.btnSearch.setFont(fntButton);
/*  505 */     GDStashFrame.setMnemonic(this.btnSearch, "MNC_SEARCH_SECOND");
/*      */     
/*  507 */     if (this.btnReload == null) {
/*  508 */       this.btnReload = new JButton();
/*      */       
/*  510 */       this.btnReload.addActionListener(new StashReloadActionListener());
/*      */     } 
/*  512 */     this.btnReload.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_RELOAD"));
/*  513 */     this.btnReload.setIcon(GDImagePool.iconBtnReload24);
/*  514 */     this.btnReload.setFont(fntButton);
/*  515 */     GDStashFrame.setMnemonic(this.btnReload, "MNC_RELOAD");
/*      */     
/*  517 */     if (this.btnHelp == null) {
/*  518 */       this.btnHelp = new JButton();
/*      */       
/*  520 */       this.btnHelp.addActionListener(new GDHelpActionListener("03_transfer.html"));
/*      */     } 
/*  522 */     this.btnHelp.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_HELP"));
/*  523 */     this.btnHelp.setIcon(GDImagePool.iconBtnQuestion24);
/*  524 */     this.btnHelp.setFont(fntButton);
/*  525 */     GDStashFrame.setMnemonic(this.btnHelp, "MNC_HELP");
/*      */     
/*  527 */     if (this.pnlInfo == null) {
/*  528 */       this.pnlInfo = new GDItemInfoPane();
/*      */     }
/*  530 */     this.pnlInfo.adjustUI();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  535 */     if (this.pnlTable == null) {
/*  536 */       this.pnlTable = new GDItemNameTablePane(null, false);
/*  537 */       this.pnlTable.addActionListener(new TableActionListener());
/*      */       
/*  539 */       this.pnlTable.setMinimumSize(new Dimension(300, 200));
/*  540 */       this.pnlTable.setMaximumSize(new Dimension(600, 2000));
/*      */     } else {
/*  542 */       this.pnlTable.adjustUI();
/*      */     } 
/*      */     
/*  545 */     if (this.pnlStash == null) {
/*  546 */       this.pnlStash = new GDSharedStashPane(this.info, 1, this.frame, this);
/*      */     } else {
/*  548 */       this.pnlStash.adjustUI();
/*      */     } 
/*      */     
/*  551 */     if (this.dlgSearch != null) {
/*  552 */       this.dlgSearch.adjustUI();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateUI() {
/*  558 */     super.updateUI();
/*      */     
/*  560 */     if (this.dlgSearch != null) {
/*  561 */       SwingUtilities.updateComponentTreeUI(this.dlgSearch);
/*      */     }
/*      */   }
/*      */   
/*      */   public void refresh() {
/*  566 */     if (this.pnlTable != null) this.pnlTable.refresh(); 
/*  567 */     if (this.pnlInfo != null) this.pnlInfo.refresh(); 
/*  568 */     if (this.pnlStash != null) this.pnlStash.refresh(); 
/*      */   }
/*      */   
/*      */   private JPanel buildMainPanel() {
/*  572 */     JPanel panel = new JPanel();
/*      */     
/*  574 */     GroupLayout layout = null;
/*  575 */     GroupLayout.SequentialGroup hGroup = null;
/*  576 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/*  578 */     JPanel pnlTable = buildTablePanel();
/*      */     
/*  580 */     JPanel pnlItemInfo = buildInfoPanel();
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
/*  592 */     layout = new GroupLayout(panel);
/*  593 */     panel.setLayout(layout);
/*      */     
/*  595 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  598 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  601 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  604 */     hGroup
/*  605 */       .addGroup(layout.createParallelGroup()
/*  606 */         .addComponent(pnlTable))
/*  607 */       .addGroup(layout.createParallelGroup()
/*  608 */         .addComponent(pnlItemInfo))
/*  609 */       .addGroup(layout.createParallelGroup()
/*  610 */         .addComponent((Component)this.pnlStash));
/*  611 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  614 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  617 */     vGroup
/*  618 */       .addGroup(layout.createParallelGroup()
/*  619 */         .addComponent(pnlTable)
/*  620 */         .addComponent(pnlItemInfo)
/*  621 */         .addComponent((Component)this.pnlStash));
/*  622 */     layout.setVerticalGroup(vGroup);
/*      */ 
/*      */ 
/*      */     
/*  626 */     return panel;
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
/*  651 */     GroupLayout layout = null;
/*  652 */     GroupLayout.SequentialGroup hGroup = null;
/*  653 */     GroupLayout.SequentialGroup vGroup = null;
/*      */ 
/*      */ 
/*      */     
/*  657 */     JPanel panel = new JPanel();
/*      */     
/*  659 */     layout = new GroupLayout(panel);
/*  660 */     panel.setLayout(layout);
/*      */     
/*  662 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  665 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  668 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  671 */     hGroup
/*  672 */       .addGroup(layout.createParallelGroup()
/*  673 */         .addComponent(this.btnSearch)
/*  674 */         .addComponent((Component)this.pnlTable));
/*  675 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  678 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  681 */     vGroup
/*  682 */       .addGroup(layout.createParallelGroup()
/*  683 */         .addComponent(this.btnSearch))
/*  684 */       .addGroup(layout.createParallelGroup()
/*  685 */         .addComponent((Component)this.pnlTable));
/*  686 */     layout.setVerticalGroup(vGroup);
/*      */     
/*  688 */     layout.linkSize(0, new Component[] { this.btnSearch, (Component)this.pnlTable });
/*      */     
/*  690 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildInfoPanel() {
/*  694 */     GroupLayout layout = null;
/*  695 */     GroupLayout.SequentialGroup hGroup = null;
/*  696 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/*  698 */     JPanel panel = new JPanel();
/*      */     
/*  700 */     JPanel pnlButtonFile = buildFileButtonPanel();
/*  701 */     JPanel pnlButtonOper = buildOperationButtonPanel();
/*      */     
/*  703 */     layout = new GroupLayout(panel);
/*  704 */     panel.setLayout(layout);
/*      */     
/*  706 */     layout.setAutoCreateGaps(false);
/*      */ 
/*      */     
/*  709 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  712 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  715 */     hGroup
/*  716 */       .addGroup(layout.createParallelGroup()
/*  717 */         .addComponent(pnlButtonFile)
/*  718 */         .addComponent(pnlButtonOper)
/*  719 */         .addComponent((Component)this.pnlInfo));
/*  720 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  723 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  726 */     vGroup
/*  727 */       .addGroup(layout.createParallelGroup()
/*  728 */         .addComponent(pnlButtonFile))
/*  729 */       .addGroup(layout.createParallelGroup()
/*  730 */         .addComponent(pnlButtonOper))
/*  731 */       .addGroup(layout.createParallelGroup()
/*  732 */         .addComponent((Component)this.pnlInfo));
/*  733 */     layout.setVerticalGroup(vGroup);
/*      */     
/*  735 */     layout.linkSize(0, new Component[] { pnlButtonFile, pnlButtonOper });
/*  736 */     layout.linkSize(0, new Component[] { pnlButtonFile, (Component)this.pnlInfo });
/*      */ 
/*      */ 
/*      */     
/*  740 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildFileButtonPanel() {
/*  744 */     BorderLayout layout = new BorderLayout();
/*      */     
/*  746 */     JPanel panel = new JPanel();
/*      */     
/*  748 */     panel.setLayout(layout);
/*      */     
/*  750 */     panel.add(this.btnFileSave, "Center");
/*  751 */     panel.add(this.btnReload, "West");
/*  752 */     panel.add(this.btnHelp, "East");
/*      */     
/*  754 */     int size = 12;
/*  755 */     if (GDStashFrame.iniConfig != null) size = GDStashFrame.iniConfig.sectUI.fontSize; 
/*  756 */     Dimension dimPref = new Dimension(30 * size, 2 * size);
/*  757 */     Dimension dimMax = new Dimension(50 * size, 2 * size);
/*      */     
/*  759 */     panel.setPreferredSize(dimPref);
/*  760 */     panel.setMaximumSize(dimMax);
/*      */     
/*  762 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildOperationButtonPanel() {
/*  766 */     GroupLayout layout = null;
/*  767 */     GroupLayout.SequentialGroup hGroup = null;
/*  768 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/*  770 */     JPanel panel = new JPanel();
/*      */     
/*  772 */     layout = new GroupLayout(panel);
/*  773 */     panel.setLayout(layout);
/*      */     
/*  775 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  778 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  781 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  784 */     hGroup
/*  785 */       .addGroup(layout.createParallelGroup()
/*  786 */         .addComponent(this.btnDBCopy)
/*  787 */         .addComponent(this.btnDBMove)
/*  788 */         .addComponent(this.btnStashDel)
/*  789 */         .addComponent(this.btnDBDel))
/*  790 */       .addGroup(layout.createParallelGroup()
/*  791 */         .addComponent(this.btnPageCopy)
/*  792 */         .addComponent(this.btnPageMove)
/*  793 */         .addComponent(this.btnPageDel));
/*  794 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  797 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  800 */     vGroup
/*  801 */       .addGroup(layout.createParallelGroup()
/*  802 */         .addComponent(this.btnDBCopy)
/*  803 */         .addComponent(this.btnPageCopy))
/*  804 */       .addGroup(layout.createParallelGroup()
/*  805 */         .addComponent(this.btnDBMove)
/*  806 */         .addComponent(this.btnPageMove))
/*  807 */       .addGroup(layout.createParallelGroup()
/*  808 */         .addComponent(this.btnStashDel)
/*  809 */         .addComponent(this.btnPageDel))
/*  810 */       .addGroup(layout.createParallelGroup()
/*  811 */         .addComponent(this.btnDBDel));
/*  812 */     layout.setVerticalGroup(vGroup);
/*      */     
/*  814 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnDBMove });
/*  815 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnStashDel });
/*  816 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnDBDel });
/*  817 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnPageCopy });
/*  818 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnPageMove });
/*  819 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnPageDel });
/*      */     
/*  821 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnDBMove });
/*  822 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnStashDel });
/*  823 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnDBDel });
/*  824 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnPageCopy });
/*  825 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnPageMove });
/*  826 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnPageDel });
/*      */     
/*  828 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildButtonGridPanel() {
/*  832 */     GridLayout layout = null;
/*      */     
/*  834 */     JPanel panel = new JPanel();
/*      */     
/*  836 */     layout = new GridLayout(4, 2, 10, 10);
/*  837 */     panel.setLayout(layout);
/*      */     
/*  839 */     panel.add(this.btnDBCopy);
/*  840 */     panel.add(this.btnPageCopy);
/*  841 */     panel.add(this.btnDBMove);
/*  842 */     panel.add(this.btnPageMove);
/*  843 */     panel.add(this.btnStashDel);
/*  844 */     panel.add(this.btnPageDel);
/*  845 */     panel.add(this.btnDBDel);
/*      */     
/*  847 */     return panel;
/*      */   }
/*      */   
/*      */   private void determineStashType() {
/*  851 */     this.stashType = 0;
/*  852 */     if (this.info != null && 
/*  853 */       this.info.gdStash != null) {
/*  854 */       if (this.info.gdStash.isHardcore()) {
/*  855 */         this.stashType = 2;
/*      */       } else {
/*  857 */         this.stashType = 1;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void clearSelItems() {
/*  864 */     this.pnlInfo.setItemInfo(null, false, "");
/*  865 */     this.pnlTable.setItems((List<GDItem>)null);
/*      */   }
/*      */   
/*      */   public void updateStash() {
/*  869 */     this.pnlStash.layoutStash();
/*  870 */     this.pnlStash.updateModSelection();
/*      */   }
/*      */   
/*      */   public void updateConfig() {
/*  874 */     if (this.dlgSearch != null) this.dlgSearch.updateConfig();
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void refreshStashSelection() {
/*  882 */     this.pnlStash.refreshStashSelection();
/*      */   }
/*      */   
/*      */   public void setStashInfo(GDStashInfoList.GDStashFileInfo info) {
/*  886 */     this.info = info;
/*      */     
/*  888 */     if (this.pnlStash != null) this.pnlStash.setStash(info); 
/*      */   }
/*      */   
/*      */   public void refreshStashInfo(GDStashInfoList.GDStashFileInfo info) {
/*  892 */     if (info == null)
/*  893 */       return;  if (this.info == null)
/*      */       return; 
/*  895 */     if (this.info.fileName.equals(info.fileName)) {
/*  896 */       this.info = info;
/*      */       
/*  898 */       if (this.pnlStash != null) this.pnlStash.setStash(info); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean updateStashFile() {
/*  903 */     if (this.info == null) return false; 
/*  904 */     if (this.info.gdStash == null) return false; 
/*  905 */     if (GDStashFrame.iniConfig == null) return false; 
/*  906 */     if (GDStashFrame.iniConfig.sectDir.savePath == null) return false;
/*      */     
/*      */     try {
/*  909 */       String stashMod = this.info.gdStash.getModName();
/*  910 */       if (stashMod == null) stashMod = "";
/*      */       
/*  912 */       String dir = this.info.gdStash.getFile().getParentFile().getCanonicalPath();
/*      */       
/*  914 */       if (dir.length() >= GDStashFrame.iniConfig.sectDir.savePath.length()) {
/*  915 */         dir = dir.substring(GDStashFrame.iniConfig.sectDir.savePath.length());
/*      */       } else {
/*  917 */         dir = "";
/*      */       } 
/*      */       
/*  920 */       if (stashMod.equals(dir)) return false;
/*      */       
/*  922 */       dir = GDStashFrame.iniConfig.sectDir.savePath;
/*  923 */       if (!dir.endsWith(GDConstants.FILE_SEPARATOR)) dir = dir + GDConstants.FILE_SEPARATOR;
/*      */       
/*  925 */       if (!stashMod.equals("")) dir = dir + stashMod + GDConstants.FILE_SEPARATOR;
/*      */       
/*  927 */       dir = dir + this.info.stashFile.getName();
/*      */       
/*  929 */       File file = new File(dir);
/*      */       
/*  931 */       this.info.gdStash.setFile(file);
/*      */       
/*  933 */       return true;
/*      */     }
/*  935 */     catch (IOException iOException) {
/*      */       
/*  937 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setChar(GDCharInfoList.GDCharFileInfo info) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStash(GDStashInfoList.GDStashFileInfo info) {
/*  952 */     if (this.info == info)
/*      */       return; 
/*  954 */     this.info = info;
/*      */     
/*  956 */     int oldStashType = this.stashType;
/*  957 */     determineStashType();
/*  958 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSCHC && 
/*  959 */       oldStashType != 0 && 
/*  960 */       oldStashType != this.stashType) this.pnlTable.setItems((List<GDItem>)null);
/*      */ 
/*      */ 
/*      */     
/*  964 */     checkSaveButton();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getItemLocation() {
/*  969 */     return this.location;
/*      */   }
/*      */ 
/*      */   
/*      */   public GDItem getSelectedItem() {
/*  974 */     return this.selItem;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelectedItem(GDItem item, int location) {
/*  980 */     if (location == 1) {
/*  981 */       this.pnlTable.clearSelection();
/*      */     }
/*      */     
/*  984 */     this.selItem = item;
/*  985 */     this.location = location;
/*      */     
/*  987 */     boolean hardcore = false;
/*      */     
/*  989 */     if (this.info != null && 
/*  990 */       this.info.gdStash != null) {
/*  991 */       hardcore = this.info.gdStash.isHardcore();
/*      */     }
/*      */ 
/*      */     
/*  995 */     this.pnlInfo.setItemInfo(item, hardcore, "");
/*      */     
/*  997 */     switch (location) {
/*      */       case 1:
/*  999 */         this.btnDBCopy.setEnabled((item != null));
/* 1000 */         this.btnDBMove.setEnabled((item != null));
/* 1001 */         this.btnStashDel.setEnabled((item != null));
/* 1002 */         this.btnDBDel.setEnabled(false);
/* 1003 */         this.btnPageCopy.setEnabled(true);
/* 1004 */         this.btnPageMove.setEnabled(true);
/* 1005 */         this.btnPageDel.setEnabled(true);
/*      */         return;
/*      */ 
/*      */       
/*      */       case 2:
/* 1010 */         this.btnDBCopy.setEnabled(false);
/* 1011 */         this.btnDBMove.setEnabled(false);
/* 1012 */         this.btnStashDel.setEnabled(false);
/* 1013 */         this.btnDBDel.setEnabled((item != null));
/* 1014 */         this.btnPageCopy.setEnabled(false);
/* 1015 */         this.btnPageMove.setEnabled(false);
/* 1016 */         this.btnPageDel.setEnabled(false);
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/* 1021 */     this.btnDBCopy.setEnabled(false);
/* 1022 */     this.btnDBMove.setEnabled(false);
/* 1023 */     this.btnStashDel.setEnabled(false);
/* 1024 */     this.btnDBDel.setEnabled(false);
/* 1025 */     this.btnPageCopy.setEnabled(true);
/* 1026 */     this.btnPageMove.setEnabled(true);
/* 1027 */     this.btnPageDel.setEnabled(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transferSelectedItem(int action, int x, int y) {
/* 1035 */     switch (action) {
/*      */       
/*      */       case 1:
/* 1038 */         if (this.location == 1) {
/* 1039 */           GDItem item = this.pnlStash.getSelectedItem();
/*      */           
/* 1041 */           if (item != null) {
/* 1042 */             this.pnlStash.moveSelectedItem(1, x, y);
/*      */             
/* 1044 */             this.btnFileSave.setEnabled(this.info.gdStash.hasChanged());
/*      */           } 
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/* 1052 */         if (this.location == 1) {
/* 1053 */           boolean success = DBStashItem.storeItem(this.selItem);
/*      */           
/* 1055 */           if (success) setSelectedItem((GDItem)null, 0);
/*      */           
/* 1057 */           GDMsgLogger.showLog(this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_STORED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_STORED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/* 1064 */         if (this.location == 1) {
/* 1065 */           boolean success = DBStashItem.storeItem(this.selItem);
/*      */           
/* 1067 */           if (success) {
/* 1068 */             this.pnlStash.deleteSelectedItem(3);
/*      */             
/* 1070 */             setSelectedItem((GDItem)null, 0);
/*      */             
/* 1072 */             this.btnFileSave.setEnabled(this.info.gdStash.hasChanged());
/*      */           } 
/*      */           
/* 1075 */           GDMsgLogger.showLog(this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_STORED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_STORED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/* 1082 */         if (this.location == 2) {
/* 1083 */           boolean success = this.pnlStash.addItem(this.selItem, 4, x, y);
/*      */           
/* 1085 */           if (success && 
/* 1086 */             GDStashFrame.iniConfig.sectRestrict.dbStashMove) {
/* 1087 */             transferSelectedItem(5, x, y);
/*      */           }
/*      */ 
/*      */           
/* 1091 */           this.btnFileSave.setEnabled(this.info.gdStash.hasChanged());
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 5:
/* 1098 */         if (this.location == 2) {
/* 1099 */           boolean success = DBStashItem.delete(this.selItem);
/*      */           
/* 1101 */           if (success) {
/* 1102 */             if (this.selItem.isStackable()) {
/* 1103 */               GDItem item = DBStashItem.getStack(this.selItem);
/*      */               
/* 1105 */               this.pnlTable.updateItem(item);
/*      */             } else {
/* 1107 */               this.pnlTable.deleteItem();
/*      */             } 
/*      */             
/* 1110 */             setSelectedItem(this.pnlTable.getSelectedItem(), 2);
/*      */           } 
/*      */           
/* 1113 */           GDMsgLogger.showLog(this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_DELETED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_DELETED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 6:
/* 1120 */         if (this.location == 1) {
/* 1121 */           GDItem item = getSelectedItem();
/*      */           
/* 1123 */           if (item == null)
/*      */             break; 
/* 1125 */           this.pnlStash.deleteSelectedItem(6);
/*      */           
/* 1127 */           setSelectedItem((GDItem)null, 0);
/*      */           
/* 1129 */           this.btnFileSave.setEnabled(this.info.gdStash.hasChanged());
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 7:
/* 1136 */         if (this.location == 1 || this.location == 0) {
/*      */           
/* 1138 */           GDContainerMapPane page = this.pnlStash.getCurrentPage();
/*      */           
/* 1140 */           if (page == null)
/*      */             return; 
/* 1142 */           List<GDItem> items = page.getItemList(7);
/*      */           
/* 1144 */           for (GDItem item : items) {
/* 1145 */             DBStashItem.storeItem(item);
/*      */           }
/*      */ 
/*      */           
/* 1149 */           GDMsgLogger.showLog(this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_STORED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_STORED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 8:
/* 1156 */         if (this.location == 1 || this.location == 0) {
/*      */           
/* 1158 */           GDContainerMapPane page = this.pnlStash.getCurrentPage();
/*      */           
/* 1160 */           if (page == null)
/*      */             return; 
/* 1162 */           List<GDItem> items = page.getItemList(8);
/*      */           
/* 1164 */           for (GDItem item : items) {
/* 1165 */             boolean success = DBStashItem.storeItem(item);
/*      */             
/* 1167 */             if (success) page.deleteItem(item, 8, false);
/*      */           
/*      */           } 
/* 1170 */           page.layoutContainers();
/*      */           
/* 1172 */           setSelectedItem((GDItem)null, 0);
/*      */           
/* 1174 */           this.btnFileSave.setEnabled(this.info.gdStash.hasChanged());
/*      */           
/* 1176 */           GDMsgLogger.showLog(this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_STORED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_STORED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 9:
/* 1183 */         if (this.location == 1 || this.location == 0) {
/*      */           
/* 1185 */           GDContainerMapPane page = this.pnlStash.getCurrentPage();
/*      */           
/* 1187 */           if (page == null)
/*      */             return; 
/* 1189 */           List<GDItem> items = page.getItemList(9);
/*      */           
/* 1191 */           for (GDItem item : items) {
/* 1192 */             page.deleteItem(item, 9, false);
/*      */           }
/*      */           
/* 1195 */           page.layoutContainers();
/*      */           
/* 1197 */           setSelectedItem((GDItem)null, 0);
/*      */           
/* 1199 */           this.btnFileSave.setEnabled(this.info.gdStash.hasChanged());
/*      */           
/* 1201 */           GDMsgLogger.showLog(this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_DELETED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_DELETED"));
/*      */         } 
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkSaveButton() {
/* 1210 */     if (this.info == null) {
/* 1211 */       this.btnFileSave.setEnabled(false);
/*      */     }
/* 1213 */     else if (this.info.gdStash == null) {
/* 1214 */       this.btnFileSave.setEnabled(false);
/*      */     } else {
/* 1216 */       this.btnFileSave.setEnabled(this.info.gdStash.hasChanged());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Frame getFrame() {
/* 1223 */     return this.frame;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void search(SelectionCriteria criteria) {
/* 1232 */     if (this.info == null)
/* 1233 */       return;  if (this.info.gdStash == null)
/*      */       return; 
/* 1235 */     boolean hardcore = this.info.gdStash.isHardcore();
/*      */     
/* 1237 */     criteria.noEnemyOnly = true;
/*      */     
/* 1239 */     List<GDItem> items = DBStashItem.getGDItemByCriteria(criteria, CriteriaCombination.Soulbound.INCLUDED, CriteriaCombination.SC_HC.SETTING, hardcore, null);
/*      */     
/* 1241 */     if (criteria.combiMode == SelectionCriteria.CombinationMode.AND) {
/* 1242 */       List<GDItem> list = new LinkedList<>();
/*      */       
/* 1244 */       for (GDItem gdi : items) {
/* 1245 */         if (!DBStat.statsMeetCriteria(gdi.getStatList(), criteria))
/*      */           continue; 
/* 1247 */         if (criteria.dmgConversionFrom != null && 
/* 1248 */           !gdi.hasConvertIn(criteria.dmgConversionFrom)) {
/*      */           continue;
/*      */         }
/* 1251 */         if (criteria.dmgConversionTo != null && 
/* 1252 */           !gdi.hasConvertOut(criteria.dmgConversionTo)) {
/*      */           continue;
/*      */         }
/* 1255 */         if (criteria.petBonus && 
/* 1256 */           !gdi.hasPetBonus()) {
/*      */           continue;
/*      */         }
/* 1259 */         list.add(gdi);
/*      */       } 
/*      */       
/* 1262 */       items = list;
/*      */     } 
/*      */     
/* 1265 */     this.pnlTable.setItems(items);
/*      */     
/* 1267 */     GDMsgLogger.showLog((Component)this, GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_SEARCH"));
/*      */   }
/*      */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\GDTransferPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */