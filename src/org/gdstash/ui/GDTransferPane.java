/*      */ package org.gdstash.ui;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.util.List;
/*      */ import javax.swing.GroupLayout;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JPanel;
/*      */ import org.gdstash.db.SelectionCriteria;
/*      */ import org.gdstash.item.GDItem;
/*      */ import org.gdstash.ui.stash.GDContainerMapPane;
/*      */ import org.gdstash.ui.util.GDStashInfoList;
/*      */ import org.gdstash.util.GDImagePool;
/*      */ import org.gdstash.util.GDLog;
/*      */ import org.gdstash.util.GDMsgFormatter;
/*      */ import org.gdstash.util.GDMsgLogger;
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
/*   49 */       GDItem item = GDTransferPane.this.pnlTable.getSelectedItem();
/*      */       
/*   51 */       GDTransferPane.this.setSelectedItem(item, 2);
/*      */       
/*   53 */       GDMsgLogger.showLog((Component)GDTransferPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     }
/*      */     private TableActionListener() {} }
/*      */   
/*      */   private class StashReloadActionListener implements ActionListener { private StashReloadActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*   60 */       if (GDTransferPane.this.info.gdStash != null && GDTransferPane.this.info.gdStash.hasChanged() && 
/*   61 */         GDStashFrame.iniConfig.sectRestrict.dbStashMove) {
/*   62 */         JButton[] buttons = new JButton[2];
/*      */         
/*   64 */         buttons[0] = new JButton(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_YES"), GDImagePool.iconBtnOk24);
/*   65 */         buttons[1] = new JButton(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_NO"), GDImagePool.iconBtnCancel24);
/*      */         
/*   67 */         String tag = "TXT_RELOAD_STASH";
/*      */         
/*   69 */         int i = GDFlexDialog.showDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, tag), buttons, 3, (Frame)null, true);
/*      */         
/*   71 */         if (i == -1)
/*   72 */           return;  if (i == 1) {
/*      */           return;
/*      */         }
/*      */       } 
/*   76 */       GDStashInfoList.findStashes(GDTransferPane.this.frame, GDTransferPane.this.info, null);
/*      */ 
/*      */       
/*   79 */       if (GDTransferPane.this.info != null) {
/*   80 */         for (GDStashInfoList.GDStashFileInfo si : GDStashInfoList.gdStashFileInfos) {
/*   81 */           if (si.fileName.equals(GDTransferPane.this.info.fileName)) {
/*   82 */             GDTransferPane.this.info = si;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/*   89 */       if (GDTransferPane.this.frame.pnlTransfer != null) {
/*   90 */         GDTransferPane.this.frame.pnlTransfer.refreshStashInfo(GDTransferPane.this.info);
/*      */       }
/*   92 */       if (GDTransferPane.this.frame.pnlCraft != null) {
/*   93 */         GDTransferPane.this.frame.pnlCraft.refreshStashInfo(GDTransferPane.this.info);
/*      */       }
/*      */       
/*   96 */       GDMsgLogger.showLog((Component)GDTransferPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "MESSAGES"), GDLog.MessageType.Info, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"), false, false);
/*      */     } }
/*      */   
/*      */   private class DBDelActionListener implements ActionListener {
/*      */     private DBDelActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  103 */       GDItem item = GDTransferPane.this.pnlTable.getSelectedItem();
/*      */       
/*  105 */       if (item == null)
/*      */         return; 
/*  107 */       GDTransferPane.this.transferSelectedItem(5, 0, 0);
/*      */       
/*  109 */       GDMsgLogger.showLog((Component)GDTransferPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     }
/*      */   }
/*      */   
/*      */   private class DBCopyActionListener implements ActionListener { private DBCopyActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  116 */       GDItem item = GDTransferPane.this.getSelectedItem();
/*      */       
/*  118 */       if (item == null)
/*      */         return; 
/*  120 */       GDTransferPane.this.transferSelectedItem(2, 0, 0);
/*      */       
/*  122 */       GDMsgLogger.showLog((Component)GDTransferPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     } }
/*      */   
/*      */   private class DBMoveActionListener implements ActionListener {
/*      */     private DBMoveActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  129 */       GDItem item = GDTransferPane.this.getSelectedItem();
/*      */       
/*  131 */       if (item == null)
/*      */         return; 
/*  133 */       GDTransferPane.this.transferSelectedItem(3, 0, 0);
/*      */       
/*  135 */       GDMsgLogger.showLog((Component)GDTransferPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     }
/*      */   }
/*      */   
/*      */   private class PageCopyActionListener implements ActionListener { private PageCopyActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  142 */       if (GDTransferPane.this.pnlStash.getCurrentPage() == null)
/*      */         return; 
/*  144 */       GDTransferPane.this.transferSelectedItem(7, 0, 0);
/*      */       
/*  146 */       GDMsgLogger.showLog((Component)GDTransferPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     } }
/*      */   
/*      */   private class PageMoveActionListener implements ActionListener {
/*      */     private PageMoveActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  153 */       if (GDTransferPane.this.pnlStash.getCurrentPage() == null)
/*      */         return; 
/*  155 */       GDTransferPane.this.transferSelectedItem(8, 0, 0);
/*      */       
/*  157 */       GDMsgLogger.showLog((Component)GDTransferPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     }
/*      */   }
/*      */   
/*      */   private class StashDelActionListener implements ActionListener { private StashDelActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  164 */       GDTransferPane.this.transferSelectedItem(6, 0, 0);
/*      */       
/*  166 */       GDMsgLogger.showLog((Component)GDTransferPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     } }
/*      */   
/*      */   private class PageDelActionListener implements ActionListener {
/*      */     private PageDelActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  173 */       if (GDTransferPane.this.pnlStash.getCurrentPage() == null)
/*      */         return; 
/*  175 */       GDTransferPane.this.transferSelectedItem(9, 0, 0);
/*      */       
/*  177 */       GDMsgLogger.showLog((Component)GDTransferPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     }
/*      */   }
/*      */   
/*      */   private class FileSaveActionListener implements ActionListener {
/*      */     private FileSaveActionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent ev) {
/*  185 */       if (GDTransferPane.this.info == null)
/*  186 */         return;  if (GDTransferPane.this.info.gdStash == null)
/*      */         return; 
/*  188 */       if (GDTransferPane.this.frame.isCloudSaveDir() && 
/*  189 */         GDStashFrame.isGrimDawnRunning()) {
/*  190 */         GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_CLOUD_SAVE"));
/*  191 */         GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_CLOSE"));
/*      */         
/*  193 */         GDMsgLogger.showLog(GDTransferPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_RUNNING"), GDLog.MessageType.Info, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_RUNNING"));
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  199 */       boolean success = false;
/*      */       
/*      */       try {
/*  202 */         boolean update = GDTransferPane.this.updateStashFile();
/*      */         
/*  204 */         GDTransferPane.this.info.gdStash.write();
/*      */         
/*  206 */         if (update) {
/*      */ 
/*      */           
/*  209 */           GDTransferPane.this.info.setFile(GDTransferPane.this.info.gdStash.getFile());
/*  210 */           GDStashFrame.iniConfig.sectHistory.lastStash = GDTransferPane.this.info.fileName;
/*      */           
/*  212 */           GDTransferPane.this.btnReload.doClick();
/*      */         } 
/*      */         
/*  215 */         success = true;
/*      */       }
/*  217 */       catch (IOException ex) {
/*  218 */         GDMsgLogger.addError(ex);
/*      */       } 
/*      */       
/*  221 */       if (success) {
/*  222 */         GDTransferPane.this.btnFileSave.setEnabled(GDTransferPane.this.info.gdStash.hasChanged());
/*      */       }
/*      */       
/*  225 */       GDMsgLogger.showLog(GDTransferPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_STASH_SAVE"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_STASH_SAVE"));
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
/*  259 */       if (GDTransferPane.this.dlgSearch == null) {
/*  260 */         GDTransferPane.this.dlgSearch = new GDTabbedSearchDialog(GDTransferPane.this.frame, GDTransferPane.this, GDTabbedSearchDialog.Mode.TRANSFER, SelectionCriteria.SoulboundMode.NONBOUND);
/*      */       }
/*      */ 
/*      */       
/*  264 */       GDTransferPane.this.dlgSearch.setVisible(true);
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
/*  324 */     this.info = info;
/*  325 */     this.frame = frame;
/*      */     
/*  327 */     determineStashType();
/*      */     
/*  329 */     this.strSave = GDStashFrame.iniConfig.sectDir.savePath;
/*  330 */     this.selItem = null;
/*  331 */     this.location = 0;
/*      */     
/*  333 */     this.dlgSearch = null;
/*      */     
/*  335 */     adjustUI();
/*      */ 
/*      */ 
/*      */     
/*  339 */     GroupLayout layout = null;
/*  340 */     GroupLayout.SequentialGroup hGroup = null;
/*  341 */     GroupLayout.SequentialGroup vGroup = null;
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
/*  360 */     this.pnlMain = buildMainPanel();
/*  361 */     JScrollPane scroll = new JScrollPane(this.pnlMain);
/*  362 */     scroll.getVerticalScrollBar().setUnitIncrement(2 * GDStashFrame.iniConfig.sectUI.fontSize);
/*      */     
/*  364 */     layout = new GroupLayout((Container)this);
/*  365 */     setLayout(layout);
/*      */     
/*  367 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  370 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  373 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  376 */     hGroup
/*  377 */       .addGroup(layout.createParallelGroup()
/*  378 */         .addComponent(scroll));
/*  379 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  382 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  385 */     vGroup
/*  386 */       .addGroup(layout.createParallelGroup()
/*  387 */         .addComponent(scroll));
/*  388 */     layout.setVerticalGroup(vGroup);
/*      */   }
/*      */ 
/*      */   
/*      */   public void adjustUI() {
/*  393 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  394 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/*  395 */     if (fntButton == null) fntButton = fntLabel;
/*      */     
/*  397 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  398 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*      */     
/*  400 */     if (this.btnFileSave == null) {
/*  401 */       this.btnFileSave = new JButton();
/*      */       
/*  403 */       this.btnFileSave.setEnabled(false);
/*  404 */       this.btnFileSave.addActionListener(new FileSaveActionListener());
/*      */     } 
/*  406 */     this.btnFileSave.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_STASH_SAVE"));
/*  407 */     this.btnFileSave.setIcon(GDImagePool.iconBtnSave24);
/*  408 */     this.btnFileSave.setFont(fntButton);
/*  409 */     GDStashFrame.setMnemonic(this.btnFileSave, "MNC_STASH_SAVE");
/*      */     
/*  411 */     if (this.btnDBDel == null) {
/*  412 */       this.btnDBDel = new JButton();
/*      */       
/*  414 */       this.btnDBDel.setEnabled(false);
/*  415 */       this.btnDBDel.addActionListener(new DBDelActionListener());
/*      */     } 
/*  417 */     this.btnDBDel.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_ITEM_DEL_DB"));
/*  418 */     this.btnDBDel.setIcon(GDImagePool.iconDBDelete24);
/*  419 */     this.btnDBDel.setFont(fntButton);
/*  420 */     GDStashFrame.setMnemonic(this.btnDBDel, "MNC_ITEM_DEL_DB");
/*      */     
/*  422 */     if (this.btnDBCopy == null) {
/*  423 */       this.btnDBCopy = new JButton();
/*      */       
/*  425 */       this.btnDBCopy.setEnabled(false);
/*  426 */       this.btnDBCopy.addActionListener(new DBCopyActionListener());
/*      */     } 
/*  428 */     this.btnDBCopy.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_ITEM_COPY_DB"));
/*  429 */     this.btnDBCopy.setIcon(GDImagePool.iconItemCopy24);
/*  430 */     this.btnDBCopy.setFont(fntButton);
/*  431 */     GDStashFrame.setMnemonic(this.btnDBCopy, "MNC_ITEM_COPY_DB");
/*      */     
/*  433 */     if (this.btnDBMove == null) {
/*  434 */       this.btnDBMove = new JButton();
/*      */       
/*  436 */       this.btnDBMove.setEnabled(false);
/*  437 */       this.btnDBMove.addActionListener(new DBMoveActionListener());
/*      */     } 
/*  439 */     this.btnDBMove.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_ITEM_MOVE_DB"));
/*  440 */     this.btnDBMove.setIcon(GDImagePool.iconItemMove24);
/*  441 */     this.btnDBMove.setFont(fntButton);
/*  442 */     GDStashFrame.setMnemonic(this.btnDBMove, "MNC_ITEM_MOVE_DB");
/*      */     
/*  444 */     if (this.btnPageCopy == null) {
/*  445 */       this.btnPageCopy = new JButton();
/*      */       
/*  447 */       this.btnPageCopy.setEnabled(true);
/*  448 */       this.btnPageCopy.addActionListener(new PageCopyActionListener());
/*      */     } 
/*  450 */     this.btnPageCopy.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_PAGE_COPY_DB"));
/*  451 */     this.btnPageCopy.setIcon(GDImagePool.iconPageCopy24);
/*  452 */     this.btnPageCopy.setFont(fntButton);
/*      */ 
/*      */     
/*  455 */     if (this.btnPageMove == null) {
/*  456 */       this.btnPageMove = new JButton();
/*      */       
/*  458 */       this.btnPageMove.setEnabled(true);
/*  459 */       this.btnPageMove.addActionListener(new PageMoveActionListener());
/*      */     } 
/*  461 */     this.btnPageMove.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_PAGE_MOVE_DB"));
/*  462 */     this.btnPageMove.setIcon(GDImagePool.iconPageMove24);
/*  463 */     this.btnPageMove.setFont(fntButton);
/*      */ 
/*      */     
/*  466 */     if (this.btnStashDel == null) {
/*  467 */       this.btnStashDel = new JButton();
/*      */       
/*  469 */       this.btnStashDel.setEnabled(false);
/*  470 */       this.btnStashDel.addActionListener(new StashDelActionListener());
/*      */     } 
/*  472 */     this.btnStashDel.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_ITEM_DEL_STASH"));
/*  473 */     this.btnStashDel.setIcon(GDImagePool.iconItemDelete24);
/*  474 */     this.btnStashDel.setFont(fntButton);
/*  475 */     GDStashFrame.setMnemonic(this.btnStashDel, "MNC_ITEM_DEL_STASH");
/*      */     
/*  477 */     if (this.btnPageDel == null) {
/*  478 */       this.btnPageDel = new JButton();
/*      */       
/*  480 */       this.btnPageDel.setEnabled(true);
/*  481 */       this.btnPageDel.addActionListener(new PageDelActionListener());
/*      */     } 
/*  483 */     this.btnPageDel.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_PAGE_DEL"));
/*  484 */     this.btnPageDel.setIcon(GDImagePool.iconPageDelete24);
/*  485 */     this.btnPageDel.setFont(fntButton);
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
/*  496 */     if (this.btnSearch == null) {
/*  497 */       this.btnSearch = new JButton();
/*      */       
/*  499 */       this.btnSearch.addActionListener(new SearchItemsListener());
/*      */     } 
/*  501 */     this.btnSearch.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_SEARCH"));
/*  502 */     this.btnSearch.setIcon(GDImagePool.iconBtnSearch24);
/*  503 */     this.btnSearch.setFont(fntButton);
/*  504 */     GDStashFrame.setMnemonic(this.btnSearch, "MNC_SEARCH_SECOND");
/*      */     
/*  506 */     if (this.btnReload == null) {
/*  507 */       this.btnReload = new JButton();
/*      */       
/*  509 */       this.btnReload.addActionListener(new StashReloadActionListener());
/*      */     } 
/*  511 */     this.btnReload.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_RELOAD"));
/*  512 */     this.btnReload.setIcon(GDImagePool.iconBtnReload24);
/*  513 */     this.btnReload.setFont(fntButton);
/*  514 */     GDStashFrame.setMnemonic(this.btnReload, "MNC_RELOAD");
/*      */     
/*  516 */     if (this.btnHelp == null) {
/*  517 */       this.btnHelp = new JButton();
/*      */       
/*  519 */       this.btnHelp.addActionListener(new GDHelpActionListener("03_transfer.html"));
/*      */     } 
/*  521 */     this.btnHelp.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_HELP"));
/*  522 */     this.btnHelp.setIcon(GDImagePool.iconBtnQuestion24);
/*  523 */     this.btnHelp.setFont(fntButton);
/*  524 */     GDStashFrame.setMnemonic(this.btnHelp, "MNC_HELP");
/*      */     
/*  526 */     if (this.pnlInfo == null) {
/*  527 */       this.pnlInfo = new GDItemInfoPane();
/*      */     }
/*  529 */     this.pnlInfo.adjustUI();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  534 */     if (this.pnlTable == null) {
/*  535 */       this.pnlTable = new GDItemNameTablePane(null, false);
/*  536 */       this.pnlTable.addActionListener(new TableActionListener());
/*      */       
/*  538 */       this.pnlTable.setMinimumSize(new Dimension(300, 200));
/*  539 */       this.pnlTable.setMaximumSize(new Dimension(600, 2000));
/*      */     } else {
/*  541 */       this.pnlTable.adjustUI();
/*      */     } 
/*      */     
/*  544 */     if (this.pnlStash == null) {
/*  545 */       this.pnlStash = new GDSharedStashPane(this.info, 1, this.frame, this);
/*      */     } else {
/*  547 */       this.pnlStash.adjustUI();
/*      */     } 
/*      */     
/*  550 */     if (this.dlgSearch != null) {
/*  551 */       this.dlgSearch.adjustUI();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateUI() {
/*  557 */     super.updateUI();
/*      */     
/*  559 */     if (this.dlgSearch != null) {
/*  560 */       SwingUtilities.updateComponentTreeUI(this.dlgSearch);
/*      */     }
/*      */   }
/*      */   
/*      */   public void refresh() {
/*  565 */     if (this.pnlTable != null) this.pnlTable.refresh(); 
/*  566 */     if (this.pnlInfo != null) this.pnlInfo.refresh(); 
/*  567 */     if (this.pnlStash != null) this.pnlStash.refresh(); 
/*      */   }
/*      */   
/*      */   private JPanel buildMainPanel() {
/*  571 */     JPanel panel = new JPanel();
/*      */     
/*  573 */     GroupLayout layout = null;
/*  574 */     GroupLayout.SequentialGroup hGroup = null;
/*  575 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/*  577 */     JPanel pnlTable = buildTablePanel();
/*      */     
/*  579 */     JPanel pnlItemInfo = buildInfoPanel();
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
/*  591 */     layout = new GroupLayout(panel);
/*  592 */     panel.setLayout(layout);
/*      */     
/*  594 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  597 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  600 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  603 */     hGroup
/*  604 */       .addGroup(layout.createParallelGroup()
/*  605 */         .addComponent(pnlTable))
/*  606 */       .addGroup(layout.createParallelGroup()
/*  607 */         .addComponent(pnlItemInfo))
/*  608 */       .addGroup(layout.createParallelGroup()
/*  609 */         .addComponent((Component)this.pnlStash));
/*  610 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  613 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  616 */     vGroup
/*  617 */       .addGroup(layout.createParallelGroup()
/*  618 */         .addComponent(pnlTable)
/*  619 */         .addComponent(pnlItemInfo)
/*  620 */         .addComponent((Component)this.pnlStash));
/*  621 */     layout.setVerticalGroup(vGroup);
/*      */ 
/*      */ 
/*      */     
/*  625 */     return panel;
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
/*  650 */     GroupLayout layout = null;
/*  651 */     GroupLayout.SequentialGroup hGroup = null;
/*  652 */     GroupLayout.SequentialGroup vGroup = null;
/*      */ 
/*      */ 
/*      */     
/*  656 */     JPanel panel = new JPanel();
/*      */     
/*  658 */     layout = new GroupLayout(panel);
/*  659 */     panel.setLayout(layout);
/*      */     
/*  661 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  664 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  667 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  670 */     hGroup
/*  671 */       .addGroup(layout.createParallelGroup()
/*  672 */         .addComponent(this.btnSearch)
/*  673 */         .addComponent((Component)this.pnlTable));
/*  674 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  677 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  680 */     vGroup
/*  681 */       .addGroup(layout.createParallelGroup()
/*  682 */         .addComponent(this.btnSearch))
/*  683 */       .addGroup(layout.createParallelGroup()
/*  684 */         .addComponent((Component)this.pnlTable));
/*  685 */     layout.setVerticalGroup(vGroup);
/*      */     
/*  687 */     layout.linkSize(0, new Component[] { this.btnSearch, (Component)this.pnlTable });
/*      */     
/*  689 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildInfoPanel() {
/*  693 */     GroupLayout layout = null;
/*  694 */     GroupLayout.SequentialGroup hGroup = null;
/*  695 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/*  697 */     JPanel panel = new JPanel();
/*      */     
/*  699 */     JPanel pnlButtonFile = buildFileButtonPanel();
/*  700 */     JPanel pnlButtonOper = buildOperationButtonPanel();
/*      */     
/*  702 */     layout = new GroupLayout(panel);
/*  703 */     panel.setLayout(layout);
/*      */     
/*  705 */     layout.setAutoCreateGaps(false);
/*      */ 
/*      */     
/*  708 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  711 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  714 */     hGroup
/*  715 */       .addGroup(layout.createParallelGroup()
/*  716 */         .addComponent(pnlButtonFile)
/*  717 */         .addComponent(pnlButtonOper)
/*  718 */         .addComponent((Component)this.pnlInfo));
/*  719 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  722 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  725 */     vGroup
/*  726 */       .addGroup(layout.createParallelGroup()
/*  727 */         .addComponent(pnlButtonFile))
/*  728 */       .addGroup(layout.createParallelGroup()
/*  729 */         .addComponent(pnlButtonOper))
/*  730 */       .addGroup(layout.createParallelGroup()
/*  731 */         .addComponent((Component)this.pnlInfo));
/*  732 */     layout.setVerticalGroup(vGroup);
/*      */     
/*  734 */     layout.linkSize(0, new Component[] { pnlButtonFile, pnlButtonOper });
/*  735 */     layout.linkSize(0, new Component[] { pnlButtonFile, (Component)this.pnlInfo });
/*      */ 
/*      */ 
/*      */     
/*  739 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildFileButtonPanel() {
/*  743 */     BorderLayout layout = new BorderLayout();
/*      */     
/*  745 */     JPanel panel = new JPanel();
/*      */     
/*  747 */     panel.setLayout(layout);
/*      */     
/*  749 */     panel.add(this.btnFileSave, "Center");
/*  750 */     panel.add(this.btnReload, "West");
/*  751 */     panel.add(this.btnHelp, "East");
/*      */     
/*  753 */     int size = 12;
/*  754 */     if (GDStashFrame.iniConfig != null) size = GDStashFrame.iniConfig.sectUI.fontSize; 
/*  755 */     Dimension dimPref = new Dimension(30 * size, 2 * size);
/*  756 */     Dimension dimMax = new Dimension(50 * size, 2 * size);
/*      */     
/*  758 */     panel.setPreferredSize(dimPref);
/*  759 */     panel.setMaximumSize(dimMax);
/*      */     
/*  761 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildOperationButtonPanel() {
/*  765 */     GroupLayout layout = null;
/*  766 */     GroupLayout.SequentialGroup hGroup = null;
/*  767 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/*  769 */     JPanel panel = new JPanel();
/*      */     
/*  771 */     layout = new GroupLayout(panel);
/*  772 */     panel.setLayout(layout);
/*      */     
/*  774 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  777 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  780 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  783 */     hGroup
/*  784 */       .addGroup(layout.createParallelGroup()
/*  785 */         .addComponent(this.btnDBCopy)
/*  786 */         .addComponent(this.btnDBMove)
/*  787 */         .addComponent(this.btnStashDel)
/*  788 */         .addComponent(this.btnDBDel))
/*  789 */       .addGroup(layout.createParallelGroup()
/*  790 */         .addComponent(this.btnPageCopy)
/*  791 */         .addComponent(this.btnPageMove)
/*  792 */         .addComponent(this.btnPageDel));
/*  793 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  796 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  799 */     vGroup
/*  800 */       .addGroup(layout.createParallelGroup()
/*  801 */         .addComponent(this.btnDBCopy)
/*  802 */         .addComponent(this.btnPageCopy))
/*  803 */       .addGroup(layout.createParallelGroup()
/*  804 */         .addComponent(this.btnDBMove)
/*  805 */         .addComponent(this.btnPageMove))
/*  806 */       .addGroup(layout.createParallelGroup()
/*  807 */         .addComponent(this.btnStashDel)
/*  808 */         .addComponent(this.btnPageDel))
/*  809 */       .addGroup(layout.createParallelGroup()
/*  810 */         .addComponent(this.btnDBDel));
/*  811 */     layout.setVerticalGroup(vGroup);
/*      */     
/*  813 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnDBMove });
/*  814 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnStashDel });
/*  815 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnDBDel });
/*  816 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnPageCopy });
/*  817 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnPageMove });
/*  818 */     layout.linkSize(0, new Component[] { this.btnDBCopy, this.btnPageDel });
/*      */     
/*  820 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnDBMove });
/*  821 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnStashDel });
/*  822 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnDBDel });
/*  823 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnPageCopy });
/*  824 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnPageMove });
/*  825 */     layout.linkSize(1, new Component[] { this.btnDBCopy, this.btnPageDel });
/*      */     
/*  827 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildButtonGridPanel() {
/*  831 */     GridLayout layout = null;
/*      */     
/*  833 */     JPanel panel = new JPanel();
/*      */     
/*  835 */     layout = new GridLayout(4, 2, 10, 10);
/*  836 */     panel.setLayout(layout);
/*      */     
/*  838 */     panel.add(this.btnDBCopy);
/*  839 */     panel.add(this.btnPageCopy);
/*  840 */     panel.add(this.btnDBMove);
/*  841 */     panel.add(this.btnPageMove);
/*  842 */     panel.add(this.btnStashDel);
/*  843 */     panel.add(this.btnPageDel);
/*  844 */     panel.add(this.btnDBDel);
/*      */     
/*  846 */     return panel;
/*      */   }
/*      */   
/*      */   private void determineStashType() {
/*  850 */     this.stashType = 0;
/*  851 */     if (this.info != null && 
/*  852 */       this.info.gdStash != null) {
/*  853 */       if (this.info.gdStash.isHardcore()) {
/*  854 */         this.stashType = 2;
/*      */       } else {
/*  856 */         this.stashType = 1;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void clearSelItems() {
/*  863 */     this.pnlInfo.setItemInfo(null, false, "");
/*  864 */     this.pnlTable.setItems((List<GDItem>)null);
/*      */   }
/*      */   
/*      */   public void updateStash() {
/*  868 */     this.pnlStash.layoutStash();
/*  869 */     this.pnlStash.updateModSelection();
/*      */   }
/*      */   
/*      */   public void updateConfig() {
/*  873 */     if (this.dlgSearch != null) this.dlgSearch.updateConfig();
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void refreshStashSelection() {
/*  881 */     this.pnlStash.refreshStashSelection();
/*      */   }
/*      */   
/*      */   public void setStashInfo(GDStashInfoList.GDStashFileInfo info) {
/*  885 */     this.info = info;
/*      */     
/*  887 */     if (this.pnlStash != null) this.pnlStash.setStash(info);
/*      */     
/*  889 */     doLayout();
/*      */   }
/*      */   
/*      */   public void refreshStashInfo(GDStashInfoList.GDStashFileInfo info) {
/*  893 */     if (info == null)
/*  894 */       return;  if (this.info == null)
/*      */       return; 
/*  896 */     if (this.info.fileName.equals(info.fileName)) {
/*  897 */       this.info = info;
/*      */       
/*  899 */       if (this.pnlStash != null) this.pnlStash.setStash(info); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean updateStashFile() {
/*  904 */     if (this.info == null) return false; 
/*  905 */     if (this.info.gdStash == null) return false; 
/*  906 */     if (GDStashFrame.iniConfig == null) return false; 
/*  907 */     if (GDStashFrame.iniConfig.sectDir.savePath == null) return false;
/*      */     
/*      */     try {
/*  910 */       String stashMod = this.info.gdStash.getModName();
/*  911 */       if (stashMod == null) stashMod = "";
/*      */       
/*  913 */       String dir = this.info.gdStash.getFile().getParentFile().getCanonicalPath();
/*      */       
/*  915 */       if (dir.length() >= GDStashFrame.iniConfig.sectDir.savePath.length()) {
/*  916 */         dir = dir.substring(GDStashFrame.iniConfig.sectDir.savePath.length());
/*      */       } else {
/*  918 */         dir = "";
/*      */       } 
/*      */       
/*  921 */       if (stashMod.equals(dir)) return false;
/*      */       
/*  923 */       dir = GDStashFrame.iniConfig.sectDir.savePath;
/*  924 */       if (!dir.endsWith(GDConstants.FILE_SEPARATOR)) dir = dir + GDConstants.FILE_SEPARATOR;
/*      */       
/*  926 */       if (!stashMod.equals("")) dir = dir + stashMod + GDConstants.FILE_SEPARATOR;
/*      */       
/*  928 */       dir = dir + this.info.stashFile.getName();
/*      */       
/*  930 */       File file = new File(dir);
/*      */       
/*  932 */       this.info.gdStash.setFile(file);
/*      */       
/*  934 */       return true;
/*      */     }
/*  936 */     catch (IOException iOException) {
/*      */       
/*  938 */       return false;
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
/*  953 */     if (this.info == info)
/*      */       return; 
/*  955 */     this.info = info;
/*      */     
/*  957 */     int oldStashType = this.stashType;
/*  958 */     determineStashType();
/*  959 */     if (!GDStashFrame.iniConfig.sectRestrict.transferSCHC && 
/*  960 */       oldStashType != 0 && 
/*  961 */       oldStashType != this.stashType) this.pnlTable.setItems((List<GDItem>)null);
/*      */ 
/*      */ 
/*      */     
/*  965 */     checkSaveButton();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getItemLocation() {
/*  970 */     return this.location;
/*      */   }
/*      */ 
/*      */   
/*      */   public GDItem getSelectedItem() {
/*  975 */     return this.selItem;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelectedItem(GDItem item, int location) {
/*  981 */     if (location == 1) {
/*  982 */       this.pnlTable.clearSelection();
/*      */     }
/*      */     
/*  985 */     this.selItem = item;
/*  986 */     this.location = location;
/*      */     
/*  988 */     boolean hardcore = false;
/*      */     
/*  990 */     if (this.info != null && 
/*  991 */       this.info.gdStash != null) {
/*  992 */       hardcore = this.info.gdStash.isHardcore();
/*      */     }
/*      */ 
/*      */     
/*  996 */     this.pnlInfo.setItemInfo(item, hardcore, "");
/*      */     
/*  998 */     switch (location) {
/*      */       case 1:
/* 1000 */         this.btnDBCopy.setEnabled((item != null));
/* 1001 */         this.btnDBMove.setEnabled((item != null));
/* 1002 */         this.btnStashDel.setEnabled((item != null));
/* 1003 */         this.btnDBDel.setEnabled(false);
/* 1004 */         this.btnPageCopy.setEnabled(true);
/* 1005 */         this.btnPageMove.setEnabled(true);
/* 1006 */         this.btnPageDel.setEnabled(true);
/*      */         return;
/*      */ 
/*      */       
/*      */       case 2:
/* 1011 */         this.btnDBCopy.setEnabled(false);
/* 1012 */         this.btnDBMove.setEnabled(false);
/* 1013 */         this.btnStashDel.setEnabled(false);
/* 1014 */         this.btnDBDel.setEnabled((item != null));
/* 1015 */         this.btnPageCopy.setEnabled(false);
/* 1016 */         this.btnPageMove.setEnabled(false);
/* 1017 */         this.btnPageDel.setEnabled(false);
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/* 1022 */     this.btnDBCopy.setEnabled(false);
/* 1023 */     this.btnDBMove.setEnabled(false);
/* 1024 */     this.btnStashDel.setEnabled(false);
/* 1025 */     this.btnDBDel.setEnabled(false);
/* 1026 */     this.btnPageCopy.setEnabled(true);
/* 1027 */     this.btnPageMove.setEnabled(true);
/* 1028 */     this.btnPageDel.setEnabled(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transferSelectedItem(int action, int x, int y) {
/* 1036 */     switch (action) {
/*      */       
/*      */       case 1:
/* 1039 */         if (this.location == 1) {
/* 1040 */           GDItem item = this.pnlStash.getSelectedItem();
/*      */           
/* 1042 */           if (item != null) {
/* 1043 */             this.pnlStash.moveSelectedItem(1, x, y);
/*      */             
/* 1045 */             this.btnFileSave.setEnabled(this.info.gdStash.hasChanged());
/*      */           } 
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/* 1053 */         if (this.location == 1) {
/* 1054 */           boolean success = DBStashItem.storeItem(this.selItem);
/*      */           
/* 1056 */           if (success) setSelectedItem((GDItem)null, 0);
/*      */           
/* 1058 */           GDMsgLogger.showLog(this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_STORED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_STORED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/* 1065 */         if (this.location == 1) {
/* 1066 */           boolean success = DBStashItem.storeItem(this.selItem);
/*      */           
/* 1068 */           if (success) {
/* 1069 */             this.pnlStash.deleteSelectedItem(3);
/*      */             
/* 1071 */             setSelectedItem((GDItem)null, 0);
/*      */             
/* 1073 */             this.btnFileSave.setEnabled(this.info.gdStash.hasChanged());
/*      */           } 
/*      */           
/* 1076 */           GDMsgLogger.showLog(this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_STORED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_STORED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/* 1083 */         if (this.location == 2) {
/* 1084 */           boolean success = this.pnlStash.addItem(this.selItem, 4, x, y);
/*      */           
/* 1086 */           if (success && 
/* 1087 */             GDStashFrame.iniConfig.sectRestrict.dbStashMove) {
/* 1088 */             transferSelectedItem(5, x, y);
/*      */           }
/*      */ 
/*      */           
/* 1092 */           this.btnFileSave.setEnabled(this.info.gdStash.hasChanged());
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 5:
/* 1099 */         if (this.location == 2) {
/* 1100 */           boolean success = DBStashItem.delete(this.selItem);
/*      */           
/* 1102 */           if (success) {
/* 1103 */             if (this.selItem.isStackable()) {
/* 1104 */               GDItem item = DBStashItem.getStack(this.selItem);
/*      */               
/* 1106 */               this.pnlTable.updateItem(item);
/*      */             } else {
/* 1108 */               this.pnlTable.deleteItem();
/*      */             } 
/*      */             
/* 1111 */             setSelectedItem(this.pnlTable.getSelectedItem(), 2);
/*      */           } 
/*      */           
/* 1114 */           GDMsgLogger.showLog(this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_DELETED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_DELETED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 6:
/* 1121 */         if (this.location == 1) {
/* 1122 */           GDItem item = getSelectedItem();
/*      */           
/* 1124 */           if (item == null)
/*      */             break; 
/* 1126 */           this.pnlStash.deleteSelectedItem(6);
/*      */           
/* 1128 */           setSelectedItem((GDItem)null, 0);
/*      */           
/* 1130 */           this.btnFileSave.setEnabled(this.info.gdStash.hasChanged());
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 7:
/* 1137 */         if (this.location == 1 || this.location == 0) {
/*      */           
/* 1139 */           GDContainerMapPane page = this.pnlStash.getCurrentPage();
/*      */           
/* 1141 */           if (page == null)
/*      */             return; 
/* 1143 */           List<GDItem> items = page.getItemList(7);
/*      */           
/* 1145 */           for (GDItem item : items) {
/* 1146 */             DBStashItem.storeItem(item);
/*      */           }
/*      */ 
/*      */           
/* 1150 */           GDMsgLogger.showLog(this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_STORED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_STORED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 8:
/* 1157 */         if (this.location == 1 || this.location == 0) {
/*      */           
/* 1159 */           GDContainerMapPane page = this.pnlStash.getCurrentPage();
/*      */           
/* 1161 */           if (page == null)
/*      */             return; 
/* 1163 */           List<GDItem> items = page.getItemList(8);
/*      */           
/* 1165 */           for (GDItem item : items) {
/* 1166 */             boolean success = DBStashItem.storeItem(item);
/*      */             
/* 1168 */             if (success) page.deleteItem(item, 8, false);
/*      */           
/*      */           } 
/* 1171 */           page.layoutContainers();
/*      */ 
/*      */           
/* 1174 */           setSelectedItem((GDItem)null, 0);
/*      */           
/* 1176 */           this.btnFileSave.setEnabled(this.info.gdStash.hasChanged());
/*      */           
/* 1178 */           GDMsgLogger.showLog(this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_STORED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_STORED"));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 9:
/* 1185 */         if (this.location == 1 || this.location == 0) {
/*      */           
/* 1187 */           GDContainerMapPane page = this.pnlStash.getCurrentPage();
/*      */           
/* 1189 */           if (page == null)
/*      */             return; 
/* 1191 */           List<GDItem> items = page.getItemList(9);
/*      */           
/* 1193 */           for (GDItem item : items) {
/* 1194 */             page.deleteItem(item, 9, false);
/*      */           }
/*      */           
/* 1197 */           page.layoutContainers();
/*      */ 
/*      */           
/* 1200 */           setSelectedItem((GDItem)null, 0);
/*      */           
/* 1202 */           this.btnFileSave.setEnabled(this.info.gdStash.hasChanged());
/*      */           
/* 1204 */           GDMsgLogger.showLog(this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_ITEM_DELETED"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_DELETED"));
/*      */         } 
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkSaveButton() {
/* 1213 */     if (this.info == null) {
/* 1214 */       this.btnFileSave.setEnabled(false);
/*      */     }
/* 1216 */     else if (this.info.gdStash == null) {
/* 1217 */       this.btnFileSave.setEnabled(false);
/*      */     } else {
/* 1219 */       this.btnFileSave.setEnabled(this.info.gdStash.hasChanged());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Frame getFrame() {
/* 1226 */     return this.frame;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void search(SelectionCriteria criteria) {
/* 1235 */     if (this.info == null)
/* 1236 */       return;  if (this.info.gdStash == null)
/*      */       return; 
/* 1238 */     boolean hardcore = this.info.gdStash.isHardcore();
/*      */     
/* 1240 */     criteria.noEnemyOnly = true;
/*      */     
/* 1242 */     List<GDItem> items = DBStashItem.getGDItemByCriteria(criteria, CriteriaCombination.Soulbound.INCLUDED, CriteriaCombination.SC_HC.SETTING, hardcore, null);
/*      */     
/* 1244 */     if (criteria.combiMode == SelectionCriteria.CombinationMode.AND) {
/* 1245 */       List<GDItem> list = new LinkedList<>();
/*      */       
/* 1247 */       for (GDItem gdi : items) {
/* 1248 */         if (!DBStat.statsMeetCriteria(gdi.getStatList(), criteria))
/*      */           continue; 
/* 1250 */         if (criteria.dmgConversionFrom != null && 
/* 1251 */           !gdi.hasConvertIn(criteria.dmgConversionFrom)) {
/*      */           continue;
/*      */         }
/* 1254 */         if (criteria.dmgConversionTo != null && 
/* 1255 */           !gdi.hasConvertOut(criteria.dmgConversionTo)) {
/*      */           continue;
/*      */         }
/* 1258 */         if (criteria.petBonus && 
/* 1259 */           !gdi.hasPetBonus()) {
/*      */           continue;
/*      */         }
/* 1262 */         list.add(gdi);
/*      */       } 
/*      */       
/* 1265 */       items = list;
/*      */     } 
/*      */     
/* 1268 */     this.pnlTable.setItems(items);
/*      */     
/* 1270 */     GDMsgLogger.showLog((Component)this, GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_SEARCH"));
/*      */   }
/*      */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\GDTransferPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */