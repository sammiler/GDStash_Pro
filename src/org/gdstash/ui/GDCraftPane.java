/*     */ package org.gdstash.ui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.UIManager;
/*     */ import org.gdstash.db.DBAffix;
/*     */ import org.gdstash.db.DBItem;
/*     */ import org.gdstash.db.SelectionCriteria;
/*     */ import org.gdstash.item.GDItem;
/*     */ import org.gdstash.ui.util.GDStashInfoList;
/*     */ import org.gdstash.util.GDConstants;
/*     */ import org.gdstash.util.GDImagePool;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ import org.gdstash.util.GDMsgLogger;
/*     */ 
/*     */ public class GDCraftPane extends AdjustablePanel implements GDUITransfer, GDUISearch, GDUIFilter {
/*     */   private GDStashFrame frame;
/*     */   private GDSharedStashPane pnlStash;
/*     */   private JButton btnFilter;
/*     */   private GDItemCraftPane pnlCraft;
/*     */   private JButton btnReload;
/*     */   private JButton btnHelp;
/*     */   private JButton btnFileSave;
/*     */   private GDItemNameTablePane pnlTable;
/*     */   private JButton btnSearch;
/*     */   private GDStashInfoList.GDStashFileInfo info;
/*     */   private GDItem selItem;
/*     */   private int location;
/*     */   private JPanel pnlMain;
/*     */   private GDTabbedSearchDialog dlgSearch;
/*     */   private GDFilterDialog dlgFilter;
/*     */   private SelectionCriteria criteria;
/*     */   
/*     */   private class TableActionListener implements ActionListener { public void actionPerformed(ActionEvent ev) {
/*  47 */       GDItem item = GDCraftPane.this.pnlTable.getSelectedItem();
/*     */       
/*  49 */       GDCraftPane.this.setSelectedItem(item, 2);
/*     */       
/*  51 */       GDMsgLogger.showLog((Component)GDCraftPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*     */     }
/*     */     private TableActionListener() {} }
/*     */   
/*     */   private class StashReloadActionListener implements ActionListener { private StashReloadActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent ev) {
/*  58 */       if (GDCraftPane.this.info.gdStash != null && GDCraftPane.this.info.gdStash.hasChanged() && 
/*  59 */         GDStashFrame.iniConfig.sectRestrict.dbStashMove) {
/*  60 */         JButton[] buttons = new JButton[2];
/*     */         
/*  62 */         buttons[0] = new JButton(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_YES"), GDImagePool.iconBtnOk24);
/*  63 */         buttons[1] = new JButton(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_NO"), GDImagePool.iconBtnCancel24);
/*     */         
/*  65 */         String tag = "TXT_RELOAD_STASH";
/*     */         
/*  67 */         int i = GDFlexDialog.showDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, tag), buttons, 3, (Frame)null, true);
/*     */         
/*  69 */         if (i == -1)
/*  70 */           return;  if (i == 1) {
/*     */           return;
/*     */         }
/*     */       } 
/*  74 */       GDStashInfoList.findStashes(GDCraftPane.this.frame, GDCraftPane.this.info, null);
/*     */       
/*  76 */       if (GDCraftPane.this.info != null) {
/*  77 */         for (GDStashInfoList.GDStashFileInfo si : GDStashInfoList.gdStashFileInfos) {
/*  78 */           if (si.fileName.equals(GDCraftPane.this.info.fileName)) {
/*  79 */             GDCraftPane.this.info = si;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/*  86 */       if (GDCraftPane.this.frame.pnlTransfer != null) {
/*  87 */         GDCraftPane.this.frame.pnlTransfer.refreshStashInfo(GDCraftPane.this.info);
/*     */       }
/*  89 */       if (GDCraftPane.this.frame.pnlCraft != null) {
/*  90 */         GDCraftPane.this.frame.pnlCraft.refreshStashInfo(GDCraftPane.this.info);
/*     */       }
/*     */       
/*  93 */       GDMsgLogger.showLog((Component)GDCraftPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*     */     } }
/*     */ 
/*     */   
/*     */   private class FileSaveActionListener implements ActionListener {
/*     */     private FileSaveActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent ev) {
/* 101 */       if (GDCraftPane.this.info == null)
/* 102 */         return;  if (GDCraftPane.this.info.gdStash == null)
/*     */         return; 
/* 104 */       if (GDCraftPane.this.frame.isCloudSaveDir() && 
/* 105 */         GDStashFrame.isGrimDawnRunning()) {
/* 106 */         GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_CLOUD_SAVE"));
/* 107 */         GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_CLOSE"));
/*     */         
/* 109 */         GDMsgLogger.showLog(GDCraftPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_RUNNING"), GDLog.MessageType.Info, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_RUNNING"));
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 115 */       boolean success = false;
/*     */       
/*     */       try {
/* 118 */         boolean update = GDCraftPane.this.updateStashFile();
/*     */         
/* 120 */         GDCraftPane.this.info.gdStash.write();
/*     */         
/* 122 */         if (update) {
/*     */ 
/*     */           
/* 125 */           GDCraftPane.this.info.setFile(GDCraftPane.this.info.gdStash.getFile());
/* 126 */           GDStashFrame.iniConfig.sectHistory.lastStash = GDCraftPane.this.info.fileName;
/*     */           
/* 128 */           GDCraftPane.this.btnReload.doClick();
/*     */         } 
/*     */         
/* 131 */         success = true;
/*     */       }
/* 133 */       catch (IOException ex) {
/* 134 */         GDMsgLogger.addError(ex);
/*     */       } 
/*     */       
/* 137 */       if (success) {
/* 138 */         GDCraftPane.this.btnFileSave.setEnabled(GDCraftPane.this.info.gdStash.hasChanged());
/*     */       }
/*     */       
/* 141 */       GDMsgLogger.showLog((Component)GDCraftPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_STASH_SAVE"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_STASH_SAVE"));
/*     */     }
/*     */   }
/*     */   
/*     */   private class FilterAffixListener implements ActionListener { private FilterAffixListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/* 148 */       if (GDCraftPane.this.dlgFilter == null) {
/* 149 */         GDCraftPane.this.dlgFilter = new GDFilterDialog(GDCraftPane.this.frame, GDCraftPane.this);
/*     */       }
/*     */       
/* 152 */       GDCraftPane.this.dlgFilter.setVisible(true);
/*     */     } }
/*     */   
/*     */   private class SearchItemsListener implements ActionListener {
/*     */     private SearchItemsListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/* 159 */       if (GDCraftPane.this.dlgSearch == null) {
/* 160 */         GDCraftPane.this.dlgSearch = new GDTabbedSearchDialog(GDCraftPane.this.frame, GDCraftPane.this, GDTabbedSearchDialog.Mode.CRAFT, SelectionCriteria.SoulboundMode.ALL, false);
/*     */       }
/*     */ 
/*     */       
/* 164 */       GDCraftPane.this.dlgSearch.setVisible(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GDCraftPane(GDStashInfoList.GDStashFileInfo info, GDStashFrame frame) {
/* 215 */     this.info = info;
/* 216 */     this.frame = frame;
/*     */     
/* 218 */     this.selItem = null;
/* 219 */     this.location = 0;
/*     */     
/* 221 */     this.dlgSearch = null;
/* 222 */     this.dlgFilter = null;
/*     */     
/* 224 */     adjustUI();
/*     */ 
/*     */ 
/*     */     
/* 228 */     GroupLayout layout = null;
/* 229 */     GroupLayout.SequentialGroup hGroup = null;
/* 230 */     GroupLayout.SequentialGroup vGroup = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 247 */     this.pnlMain = buildMainPanel();
/* 248 */     JScrollPane scroll = new JScrollPane(this.pnlMain);
/* 249 */     scroll.getVerticalScrollBar().setUnitIncrement(2 * GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 251 */     layout = new GroupLayout((Container)this);
/* 252 */     setLayout(layout);
/*     */     
/* 254 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 257 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 260 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 263 */     hGroup
/* 264 */       .addGroup(layout.createParallelGroup()
/* 265 */         .addComponent(scroll));
/* 266 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 269 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 272 */     vGroup
/* 273 */       .addGroup(layout.createParallelGroup()
/* 274 */         .addComponent(scroll));
/* 275 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 280 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 281 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/* 282 */     if (fntButton == null) fntButton = fntLabel; 
/* 283 */     Font fntCombo = UIManager.getDefaults().getFont("ComboBox.font");
/* 284 */     if (fntCombo == null) fntCombo = fntLabel; 
/* 285 */     Font fntTabbed = UIManager.getDefaults().getFont("TabbedPane.font");
/* 286 */     if (fntTabbed == null) fntTabbed = fntLabel;
/*     */     
/* 288 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 289 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 290 */     fntCombo = fntCombo.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 291 */     fntTabbed = fntTabbed.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 293 */     if (this.btnSearch == null) {
/* 294 */       this.btnSearch = new JButton();
/*     */       
/* 296 */       this.btnSearch.addActionListener(new SearchItemsListener());
/*     */     } 
/* 298 */     this.btnSearch.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_SEARCH"));
/* 299 */     this.btnSearch.setIcon(GDImagePool.iconBtnSearch24);
/* 300 */     this.btnSearch.setFont(fntButton);
/* 301 */     GDStashFrame.setMnemonic(this.btnSearch, "MNC_SEARCH_SECOND");
/*     */     
/* 303 */     if (this.btnFilter == null) {
/* 304 */       this.btnFilter = new JButton();
/*     */       
/* 306 */       this.btnFilter.addActionListener(new FilterAffixListener());
/* 307 */       this.btnFilter.setEnabled(false);
/*     */     } 
/* 309 */     this.btnFilter.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_FILTER"));
/* 310 */     this.btnFilter.setIcon(GDImagePool.iconBtnFilter24);
/* 311 */     this.btnFilter.setFont(fntButton);
/* 312 */     GDStashFrame.setMnemonic(this.btnFilter, "MNC_FILTER");
/*     */     
/* 314 */     if (this.btnFileSave == null) {
/* 315 */       this.btnFileSave = new JButton();
/*     */       
/* 317 */       this.btnFileSave.setEnabled(false);
/* 318 */       this.btnFileSave.addActionListener(new FileSaveActionListener());
/*     */     } 
/* 320 */     this.btnFileSave.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_STASH_SAVE"));
/* 321 */     this.btnFileSave.setIcon(GDImagePool.iconBtnSave24);
/* 322 */     this.btnFileSave.setFont(fntButton);
/* 323 */     GDStashFrame.setMnemonic(this.btnFileSave, "MNC_STASH_SAVE");
/*     */     
/* 325 */     if (this.btnReload == null) {
/* 326 */       this.btnReload = new JButton();
/*     */       
/* 328 */       this.btnReload.addActionListener(new StashReloadActionListener());
/*     */     } 
/* 330 */     this.btnReload.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_RELOAD"));
/* 331 */     this.btnReload.setIcon(GDImagePool.iconBtnReload24);
/* 332 */     this.btnReload.setFont(fntButton);
/* 333 */     GDStashFrame.setMnemonic(this.btnReload, "MNC_RELOAD");
/*     */     
/* 335 */     if (this.btnHelp == null) {
/* 336 */       this.btnHelp = new JButton();
/*     */       
/* 338 */       this.btnHelp.addActionListener(new GDHelpActionListener("05_craft.html"));
/*     */     } 
/* 340 */     this.btnHelp.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_HELP"));
/* 341 */     this.btnHelp.setIcon(GDImagePool.iconBtnQuestion24);
/* 342 */     this.btnHelp.setFont(fntButton);
/* 343 */     GDStashFrame.setMnemonic(this.btnHelp, "MNC_HELP");
/*     */     
/* 345 */     if (this.pnlCraft == null) {
/* 346 */       this.pnlCraft = new GDItemCraftPane();
/*     */     } else {
/* 348 */       this.pnlCraft.adjustUI();
/*     */     } 
/*     */     
/* 351 */     if (this.pnlTable == null) {
/* 352 */       this.pnlTable = new GDItemNameTablePane(null, true);
/* 353 */       this.pnlTable.addActionListener(new TableActionListener());
/*     */       
/* 355 */       this.pnlTable.setMinimumSize(new Dimension(300, 200));
/* 356 */       this.pnlTable.setMaximumSize(new Dimension(600, 2000));
/*     */     } else {
/* 358 */       this.pnlTable.adjustUI();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 364 */     if (this.pnlStash == null) {
/* 365 */       this.pnlStash = new GDSharedStashPane(this.info, 2, this.frame, this);
/*     */     } else {
/* 367 */       this.pnlStash.adjustUI();
/*     */     } 
/*     */     
/* 370 */     if (this.dlgSearch != null) {
/* 371 */       this.dlgSearch.adjustUI();
/*     */     }
/*     */     
/* 374 */     if (this.dlgFilter != null) {
/* 375 */       this.dlgFilter.adjustUI();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateUI() {
/* 381 */     super.updateUI();
/*     */     
/* 383 */     if (this.dlgSearch != null) {
/* 384 */       SwingUtilities.updateComponentTreeUI(this.dlgSearch);
/*     */     }
/*     */     
/* 387 */     if (this.dlgFilter != null) {
/* 388 */       SwingUtilities.updateComponentTreeUI(this.dlgFilter);
/*     */     }
/*     */   }
/*     */   
/*     */   public void refresh() {
/* 393 */     if (this.pnlTable != null) this.pnlTable.refresh(); 
/* 394 */     if (this.pnlCraft != null) this.pnlCraft.refresh(); 
/*     */   }
/*     */   
/*     */   private JPanel buildMainPanel() {
/* 398 */     JPanel panel = new JPanel();
/*     */     
/* 400 */     GroupLayout layout = null;
/* 401 */     GroupLayout.SequentialGroup hGroup = null;
/* 402 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 404 */     JPanel pnlTable = buildTablePanel();
/* 405 */     JPanel pnlItemInfo = buildInfoPanel();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 416 */     layout = new GroupLayout(panel);
/* 417 */     panel.setLayout(layout);
/*     */     
/* 419 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 422 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 425 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 428 */     hGroup
/* 429 */       .addGroup(layout.createParallelGroup()
/* 430 */         .addComponent(pnlTable))
/* 431 */       .addGroup(layout.createParallelGroup()
/* 432 */         .addComponent(pnlItemInfo))
/* 433 */       .addGroup(layout.createParallelGroup()
/* 434 */         .addComponent((Component)this.pnlStash));
/* 435 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 438 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 441 */     vGroup
/* 442 */       .addGroup(layout.createParallelGroup()
/* 443 */         .addComponent((Component)this.pnlStash)
/* 444 */         .addComponent(pnlItemInfo)
/* 445 */         .addComponent(pnlTable));
/* 446 */     layout.setVerticalGroup(vGroup);
/*     */ 
/*     */ 
/*     */     
/* 450 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildTablePanel() {
/* 454 */     GroupLayout layout = null;
/* 455 */     GroupLayout.SequentialGroup hGroup = null;
/* 456 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 458 */     JPanel panel = new JPanel();
/*     */     
/* 460 */     layout = new GroupLayout(panel);
/* 461 */     panel.setLayout(layout);
/*     */     
/* 463 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 466 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 469 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 472 */     hGroup
/* 473 */       .addGroup(layout.createParallelGroup()
/* 474 */         .addComponent(this.btnSearch)
/* 475 */         .addComponent((Component)this.pnlTable));
/* 476 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 479 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 482 */     vGroup
/* 483 */       .addGroup(layout.createParallelGroup()
/* 484 */         .addComponent(this.btnSearch))
/* 485 */       .addGroup(layout.createParallelGroup()
/* 486 */         .addComponent((Component)this.pnlTable));
/* 487 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 489 */     layout.linkSize(0, new Component[] { this.btnSearch, (Component)this.pnlTable });
/*     */     
/* 491 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildInfoPanel() {
/* 495 */     GroupLayout layout = null;
/* 496 */     GroupLayout.SequentialGroup hGroup = null;
/* 497 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 499 */     JPanel panel = new JPanel();
/*     */     
/* 501 */     JPanel pnlButtonFile = buildFileButtonPanel();
/*     */     
/* 503 */     layout = new GroupLayout(panel);
/* 504 */     panel.setLayout(layout);
/*     */     
/* 506 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 509 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 512 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 515 */     hGroup
/* 516 */       .addGroup(layout.createParallelGroup()
/* 517 */         .addComponent(pnlButtonFile)
/* 518 */         .addComponent(this.btnFilter)
/* 519 */         .addComponent((Component)this.pnlCraft));
/* 520 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 523 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 526 */     vGroup
/* 527 */       .addGroup(layout.createParallelGroup()
/* 528 */         .addComponent(pnlButtonFile))
/* 529 */       .addGroup(layout.createParallelGroup()
/* 530 */         .addComponent(this.btnFilter))
/* 531 */       .addGroup(layout.createParallelGroup()
/* 532 */         .addComponent((Component)this.pnlCraft));
/* 533 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 535 */     layout.linkSize(0, new Component[] { pnlButtonFile, this.btnFilter });
/* 536 */     layout.linkSize(0, new Component[] { pnlButtonFile, (Component)this.pnlCraft });
/*     */     
/* 538 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildFileButtonPanel() {
/* 542 */     BorderLayout layout = new BorderLayout();
/*     */     
/* 544 */     JPanel panel = new JPanel();
/*     */     
/* 546 */     panel.setLayout(layout);
/*     */     
/* 548 */     panel.add(this.btnFileSave, "Center");
/* 549 */     panel.add(this.btnReload, "West");
/* 550 */     panel.add(this.btnHelp, "East");
/*     */     
/* 552 */     int size = 12;
/* 553 */     if (GDStashFrame.iniConfig != null) size = GDStashFrame.iniConfig.sectUI.fontSize; 
/* 554 */     Dimension dimPref = new Dimension(30 * size, 2 * size);
/* 555 */     Dimension dimMax = new Dimension(50 * size, 2 * size);
/*     */     
/* 557 */     panel.setPreferredSize(dimPref);
/* 558 */     panel.setMaximumSize(dimMax);
/*     */     
/* 560 */     return panel;
/*     */   }
/*     */   
/*     */   public void updateStash() {
/* 564 */     this.pnlStash.layoutStash();
/* 565 */     this.pnlStash.updateModSelection();
/*     */   }
/*     */   
/*     */   public void updateConfig() {
/* 569 */     if (this.dlgSearch != null) this.dlgSearch.updateConfig();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshStashSelection() {
/* 577 */     this.pnlStash.refreshStashSelection();
/*     */   }
/*     */   
/*     */   public void setStashInfo(GDStashInfoList.GDStashFileInfo info) {
/* 581 */     this.info = info;
/*     */     
/* 583 */     if (this.pnlStash != null) this.pnlStash.setStash(info); 
/*     */   }
/*     */   
/*     */   public void refreshStashInfo(GDStashInfoList.GDStashFileInfo info) {
/* 587 */     if (info == null)
/* 588 */       return;  if (this.info == null)
/*     */       return; 
/* 590 */     if (this.info.fileName.equals(info.fileName)) {
/* 591 */       this.info = info;
/*     */       
/* 593 */       if (this.pnlStash != null) this.pnlStash.setStash(info); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean updateStashFile() {
/* 598 */     if (this.info == null) return false; 
/* 599 */     if (this.info.gdStash == null) return false; 
/* 600 */     if (GDStashFrame.iniConfig == null) return false; 
/* 601 */     if (GDStashFrame.iniConfig.sectDir.savePath == null) return false;
/*     */     
/*     */     try {
/* 604 */       String stashMod = this.info.gdStash.getModName();
/* 605 */       if (stashMod == null) stashMod = "";
/*     */       
/* 607 */       String dir = this.info.gdStash.getFile().getParentFile().getCanonicalPath();
/*     */       
/* 609 */       if (dir.length() >= GDStashFrame.iniConfig.sectDir.savePath.length()) {
/* 610 */         dir = dir.substring(GDStashFrame.iniConfig.sectDir.savePath.length());
/*     */       } else {
/* 612 */         dir = "";
/*     */       } 
/*     */       
/* 615 */       if (stashMod.equals(dir)) return false;
/*     */       
/* 617 */       dir = GDStashFrame.iniConfig.sectDir.savePath;
/* 618 */       if (!dir.endsWith(GDConstants.FILE_SEPARATOR)) dir = dir + GDConstants.FILE_SEPARATOR;
/*     */       
/* 620 */       if (!stashMod.equals("")) dir = dir + stashMod + GDConstants.FILE_SEPARATOR;
/*     */       
/* 622 */       if (this.info.gdStash.isHardcore()) {
/* 623 */         dir = dir + "transfer.gsh";
/*     */       } else {
/* 625 */         dir = dir + "transfer.gst";
/*     */       } 
/*     */       
/* 628 */       File file = new File(dir);
/*     */       
/* 630 */       this.info.gdStash.setFile(file);
/*     */       
/* 632 */       return true;
/*     */     }
/* 634 */     catch (IOException iOException) {
/*     */       
/* 636 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChar(GDCharInfoList.GDCharFileInfo info) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStash(GDStashInfoList.GDStashFileInfo info) {
/* 651 */     if (this.info == info)
/*     */       return; 
/* 653 */     this.info = info;
/*     */     
/* 655 */     checkSaveButton();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getItemLocation() {
/* 660 */     return this.location;
/*     */   }
/*     */ 
/*     */   
/*     */   public GDItem getSelectedItem() {
/* 665 */     return this.selItem;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedItem(GDItem item, int location) {
/* 671 */     if (location == 1) {
/* 672 */       this.pnlTable.clearSelection();
/*     */     }
/*     */     
/* 675 */     this.selItem = item;
/* 676 */     this.location = location;
/*     */     
/* 678 */     this.pnlCraft.setItem(item, location);
/*     */     
/* 680 */     this.btnFilter.setEnabled((item != null));
/*     */     
/* 682 */     if (this.pnlCraft.getPrefixes().isEmpty() && this.pnlCraft
/* 683 */       .getSuffixes().isEmpty())
/* 684 */     { this.btnFilter.setEnabled(false); }
/*     */     
/* 686 */     else if (item != null && 
/* 687 */       !item.isUnique() && 
/* 688 */       this.criteria != null && 
/* 689 */       !this.criteria.isInitial()) { filter(this.criteria); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transferSelectedItem(int action, int x, int y) {
/* 697 */     switch (action) {
/*     */       
/*     */       case 1:
/* 700 */         if (this.location == 1) {
/* 701 */           GDItem item = this.pnlStash.getSelectedItem();
/*     */           
/* 703 */           if (item != null) {
/* 704 */             this.pnlStash.moveSelectedItem(1, x, y);
/*     */             
/* 706 */             this.btnFileSave.setEnabled(this.info.gdStash.hasChanged());
/*     */           } 
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 714 */         if (this.location == 2) {
/* 715 */           GDItem clone = this.pnlCraft.getItem().clone();
/* 716 */           String s = this.pnlCraft.getSeed();
/*     */           
/* 718 */           if (s == null) {
/* 719 */             clone.createSeed();
/*     */           } else {
/* 721 */             clone.setSeedHex(s);
/*     */           } 
/*     */           
/* 724 */           if (clone.isComponent()) {
/* 725 */             clone.setVar1(clone.getDBItem().getComponentPieces());
/*     */           }
/*     */           
/* 728 */           int i = this.pnlCraft.getCount();
/* 729 */           if (i > 0)
/*     */           {
/* 731 */             clone.setStackCount(i);
/*     */           }
/*     */           
/* 734 */           this.pnlStash.addItem(clone, 4, x, y);
/*     */           
/* 736 */           this.btnFileSave.setEnabled(this.info.gdStash.hasChanged());
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkSaveButton() {
/* 746 */     if (this.info == null) {
/* 747 */       this.btnFileSave.setEnabled(false);
/*     */     }
/* 749 */     else if (this.info.gdStash == null) {
/* 750 */       this.btnFileSave.setEnabled(false);
/*     */     } else {
/* 752 */       this.btnFileSave.setEnabled(this.info.gdStash.hasChanged());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Frame getFrame() {
/* 759 */     return this.frame;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void search(SelectionCriteria criteria) {
/* 769 */     this.pnlCraft.setItem(null, 0);
/*     */     
/* 771 */     List<GDItem> items = new LinkedList<>();
/*     */     
/* 773 */     criteria.noEnemyOnly = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 785 */     List<DBItem> dbItems = DBItem.getByCriteria(criteria);
/*     */     
/* 787 */     for (DBItem dbi : dbItems) {
/* 788 */       if (criteria.combiMode == SelectionCriteria.CombinationMode.AND) {
/* 789 */         if (!DBStat.statsMeetCriteria(dbi.getStatList(), criteria))
/*     */           continue; 
/* 791 */         if (criteria.dmgConversionFrom != null && 
/* 792 */           !criteria.dmgConversionFrom.equals(dbi.getConvertIn())) {
/*     */           continue;
/*     */         }
/* 795 */         if (criteria.dmgConversionTo != null && 
/* 796 */           !criteria.dmgConversionTo.equals(dbi.getConvertOut())) {
/*     */           continue;
/*     */         }
/* 799 */         if (criteria.petBonus && 
/* 800 */           dbi.getPetBonusSkill() == null) {
/*     */           continue;
/*     */         }
/*     */       } 
/* 804 */       GDItem item = new GDItem(dbi);
/*     */       
/* 806 */       items.add(item);
/*     */     } 
/*     */     
/* 809 */     this.pnlTable.setItems(items);
/*     */     
/* 811 */     GDMsgLogger.showLog((Component)this, GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_SEARCH"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void filter(SelectionCriteria criteria) {
/* 820 */     this.criteria = criteria;
/*     */     
/* 822 */     if (criteria == null)
/*     */       return; 
/* 824 */     List<DBAffix> prefixes = this.pnlCraft.getPrefixes();
/* 825 */     List<DBAffix> suffixes = this.pnlCraft.getSuffixes();
/*     */     
/* 827 */     List<DBAffix> filterPrefixes = new LinkedList<>();
/* 828 */     List<DBAffix> filterSuffixes = new LinkedList<>();
/*     */     
/* 830 */     for (DBAffix affix : prefixes) {
/* 831 */       if (affix.matchesCriteria(criteria)) filterPrefixes.add(affix);
/*     */     
/*     */     } 
/* 834 */     for (DBAffix affix : suffixes) {
/* 835 */       if (affix.matchesCriteria(criteria)) filterSuffixes.add(affix);
/*     */     
/*     */     } 
/* 838 */     this.pnlCraft.setFilteredPrefixes(filterPrefixes);
/* 839 */     this.pnlCraft.setFilteredSuffixes(filterSuffixes);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\GDCraftPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */