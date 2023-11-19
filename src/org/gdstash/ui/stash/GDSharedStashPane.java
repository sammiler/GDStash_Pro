/*     */ package org.gdstash.ui.stash;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.File;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.UIManager;
/*     */ import org.gdstash.item.GDItem;
/*     */ import org.gdstash.item.GDStash;
/*     */ import org.gdstash.item.GDStashPage;
/*     */ import org.gdstash.ui.GDLogoDialog;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.GDUITransfer;
/*     */ import org.gdstash.ui.util.GDStashInfoList;
/*     */ import org.gdstash.util.GDConstants;
/*     */ import org.gdstash.util.GDImagePool;
/*     */ import org.gdstash.util.GDLog;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDSharedStashPane extends AdjustablePanel implements GDUIInventory {
/*     */   private GDStashFrame frame;
/*     */   private JTabbedPane tabPages;
/*     */   private int containerType;
/*     */   private GDContainerMapPane[] pnlPages;
/*     */   private JComboBox<String> cbSelStash;
/*     */   private JLabel lblSelMod;
/*     */   private JComboBox<String> cbSelMod;
/*     */   private GDStashInfoList.GDStashFileInfo info;
/*     */   private GDItem selItem;
/*     */   private GDContainerMapPane selPage;
/*     */   private JPanel pnlMain;
/*     */   private GDUITransfer uiTransfer;
/*     */   private boolean blockEvents;
/*     */   
/*     */   private class StashSelectActionListener implements ActionListener { public void actionPerformed(ActionEvent ev) {
/*  42 */       if (GDSharedStashPane.this.blockEvents)
/*     */         return; 
/*  44 */       GDLog log = new GDLog();
/*     */       
/*  46 */       int idx = GDSharedStashPane.this.cbSelStash.getSelectedIndex();
/*     */       
/*  48 */       if (idx == -1) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  58 */       int i = 0;
/*     */       
/*  60 */       for (GDStashInfoList.GDStashFileInfo info : GDStashInfoList.gdStashFileInfos) {
/*  61 */         if (i == idx) {
/*  62 */           if (info.gdStash == null) {
/*  63 */             info.gdStash = new GDStash(info.stashFile, log);
/*     */           }
/*     */ 
/*     */           
/*  67 */           if (info.gdStash.hasStashErrors()) {
/*  68 */             info.gdStash = null;
/*     */             
/*  70 */             GDStashFrame.iniConfig.sectHistory.lastStash = "";
/*     */           } else {
/*  72 */             GDStashFrame.iniConfig.sectHistory.lastStash = info.fileName;
/*     */           } 
/*     */           
/*  75 */           GDSharedStashPane.this.setStash(info);
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/*  80 */         i++;
/*     */       } 
/*     */       
/*  83 */       log.showLog((Component)GDSharedStashPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*     */     }
/*     */     private StashSelectActionListener() {} }
/*     */   
/*     */   private class ModSelectActionListener implements ActionListener { private ModSelectActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent ev) {
/*  90 */       if (GDSharedStashPane.this.blockEvents)
/*     */         return; 
/*  92 */       int idx = GDSharedStashPane.this.cbSelMod.getSelectedIndex();
/*     */       
/*  94 */       if (idx == -1)
/*     */         return; 
/*  96 */       String mod = (String)GDSharedStashPane.this.cbSelMod.getSelectedItem();
/*     */       
/*  98 */       if (GDSharedStashPane.this.info != null) {
/*  99 */         if (GDSharedStashPane.this.isModStashConflict(mod)) {
/*     */           
/* 101 */           GDLogoDialog dialog = new GDLogoDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_STASH_EXISTS"), 4, GDSharedStashPane.this.uiTransfer.getFrame());
/* 102 */           dialog.setVisible(true);
/*     */           
/* 104 */           String s = GDSharedStashPane.this.info.gdStash.getModName();
/* 105 */           if (s == null) s = ""; 
/* 106 */           GDSharedStashPane.this.cbSelMod.setSelectedItem(s);
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 111 */         if (GDSharedStashPane.this.info.gdStash != null) GDSharedStashPane.this.info.gdStash.setModName(mod);
/*     */         
/* 113 */         GDSharedStashPane.this.uiTransfer.checkSaveButton();
/*     */       } 
/*     */     } }
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
/*     */   public GDSharedStashPane(GDStashInfoList.GDStashFileInfo info, int containerType, GDStashFrame frame, GDUITransfer uiTransfer) {
/* 141 */     this.info = info;
/* 142 */     this.containerType = containerType;
/* 143 */     this.frame = frame;
/* 144 */     this.uiTransfer = uiTransfer;
/*     */     
/* 146 */     this.pnlPages = new GDContainerMapPane[6];
/* 147 */     this.selItem = null;
/* 148 */     this.selPage = null;
/*     */     
/* 150 */     this.blockEvents = false;
/*     */     
/* 152 */     adjustUI();
/*     */ 
/*     */ 
/*     */     
/* 156 */     GroupLayout layout = null;
/* 157 */     GroupLayout.SequentialGroup hGroup = null;
/* 158 */     GroupLayout.SequentialGroup vGroup = null;
/*     */ 
/*     */ 
/*     */     
/* 162 */     this.pnlMain = buildMainPanel();
/*     */     
/* 164 */     layout = new GroupLayout((Container)this);
/* 165 */     setLayout(layout);
/*     */     
/* 167 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 170 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 173 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 176 */     hGroup
/* 177 */       .addGroup(layout.createParallelGroup()
/* 178 */         .addComponent(this.pnlMain));
/* 179 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 182 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 185 */     vGroup
/* 186 */       .addGroup(layout.createParallelGroup()
/* 187 */         .addComponent(this.pnlMain));
/* 188 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 193 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 194 */     Font fntCombo = UIManager.getDefaults().getFont("ComboBox.font");
/* 195 */     if (fntCombo == null) fntCombo = fntLabel; 
/* 196 */     Font fntTabbed = UIManager.getDefaults().getFont("TabbedPane.font");
/* 197 */     if (fntTabbed == null) fntTabbed = fntLabel;
/*     */     
/* 199 */     fntCombo = fntCombo.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 200 */     fntTabbed = fntTabbed.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     if (this.containerType == 1) {
/* 206 */       if (this.pnlPages[0] == null) this.pnlPages[0] = new GDContainerMapPane(this, this.uiTransfer, GDImagePool.getSharedStashGrid()); 
/* 207 */       if (this.pnlPages[1] == null) this.pnlPages[1] = new GDContainerMapPane(this, this.uiTransfer, GDImagePool.getSharedStashGrid()); 
/* 208 */       if (this.pnlPages[2] == null) this.pnlPages[2] = new GDContainerMapPane(this, this.uiTransfer, GDImagePool.getSharedStashGrid()); 
/* 209 */       if (this.pnlPages[3] == null) this.pnlPages[3] = new GDContainerMapPane(this, this.uiTransfer, GDImagePool.getSharedStashGrid()); 
/* 210 */       if (this.pnlPages[4] == null) this.pnlPages[4] = new GDContainerMapPane(this, this.uiTransfer, GDImagePool.getSharedStashGrid()); 
/* 211 */       if (this.pnlPages[5] == null) this.pnlPages[5] = new GDContainerMapPane(this, this.uiTransfer, GDImagePool.getSharedStashGrid());
/*     */     
/*     */     } 
/* 214 */     if (this.containerType == 2) {
/* 215 */       if (this.pnlPages[0] == null) this.pnlPages[0] = new GDContainerMapPane(this, this.uiTransfer, GDImagePool.getCraftingStashGrid()); 
/* 216 */       if (this.pnlPages[1] == null) this.pnlPages[1] = new GDContainerMapPane(this, this.uiTransfer, GDImagePool.getCraftingStashGrid()); 
/* 217 */       if (this.pnlPages[2] == null) this.pnlPages[2] = new GDContainerMapPane(this, this.uiTransfer, GDImagePool.getCraftingStashGrid()); 
/* 218 */       if (this.pnlPages[3] == null) this.pnlPages[3] = new GDContainerMapPane(this, this.uiTransfer, GDImagePool.getCraftingStashGrid()); 
/* 219 */       if (this.pnlPages[4] == null) this.pnlPages[4] = new GDContainerMapPane(this, this.uiTransfer, GDImagePool.getCraftingStashGrid()); 
/* 220 */       if (this.pnlPages[5] == null) this.pnlPages[5] = new GDContainerMapPane(this, this.uiTransfer, GDImagePool.getCraftingStashGrid());
/*     */     
/*     */     } 
/* 223 */     if (this.tabPages == null) {
/* 224 */       this.tabPages = new JTabbedPane();
/* 225 */       this.tabPages.setTabPlacement(2);
/*     */       
/* 227 */       this.tabPages.add("I", (Component)this.pnlPages[0]);
/* 228 */       this.tabPages.add("II", (Component)this.pnlPages[1]);
/* 229 */       this.tabPages.add("III", (Component)this.pnlPages[2]);
/* 230 */       this.tabPages.add("IV", (Component)this.pnlPages[3]);
/* 231 */       this.tabPages.add("V", (Component)this.pnlPages[4]);
/* 232 */       this.tabPages.add("VI", (Component)this.pnlPages[5]);
/*     */     } 
/* 234 */     this.tabPages.setFont(fntTabbed);
/* 235 */     this.tabPages.setMaximumSize(new Dimension(this.pnlPages[0].getPreferredWidth() + 80, this.pnlPages[0].getPreferredHeight()));
/*     */     
/* 237 */     if (this.cbSelStash == null) {
/* 238 */       this.cbSelStash = new JComboBox<>();
/*     */ 
/*     */       
/* 241 */       this.cbSelStash.addActionListener(new StashSelectActionListener());
/*     */     } 
/*     */     
/* 244 */     if (this.lblSelMod == null) this.lblSelMod = new JLabel(); 
/* 245 */     this.lblSelMod.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MOD"));
/* 246 */     this.lblSelMod.setFont(fntLabel);
/*     */     
/* 248 */     if (this.cbSelMod == null) {
/* 249 */       this.cbSelMod = new JComboBox<>();
/*     */       
/* 251 */       this.cbSelMod.addActionListener(new ModSelectActionListener());
/*     */     } 
/*     */     
/* 254 */     refreshStashSelection();
/*     */     
/* 256 */     this.cbSelStash.setFont(fntCombo);
/* 257 */     this.cbSelStash.setPreferredSize(new Dimension(270, fntCombo.getSize() + 8));
/* 258 */     this.cbSelStash.setMaximumSize(new Dimension(270, fntCombo.getSize() + 8));
/*     */   }
/*     */   
/*     */   private JPanel buildMainPanel() {
/* 262 */     GroupLayout layout = null;
/* 263 */     GroupLayout.SequentialGroup hGroup = null;
/* 264 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 266 */     JPanel panel = new JPanel();
/*     */     
/* 268 */     JPanel pnlMod = buildModPanel();
/*     */     
/* 270 */     layoutStash();
/*     */     
/* 272 */     layout = new GroupLayout(panel);
/* 273 */     panel.setLayout(layout);
/*     */     
/* 275 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 278 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 281 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 284 */     hGroup
/* 285 */       .addGroup(layout.createParallelGroup()
/* 286 */         .addComponent(this.cbSelStash)
/* 287 */         .addComponent(pnlMod)
/* 288 */         .addComponent(this.tabPages));
/* 289 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 292 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 295 */     vGroup
/* 296 */       .addGroup(layout.createParallelGroup()
/* 297 */         .addComponent(this.cbSelStash))
/* 298 */       .addGroup(layout.createParallelGroup()
/* 299 */         .addComponent(pnlMod))
/* 300 */       .addGroup(layout.createParallelGroup()
/* 301 */         .addComponent(this.tabPages));
/* 302 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 304 */     layout.linkSize(1, new Component[] { this.cbSelStash, pnlMod });
/*     */     
/* 306 */     layout.linkSize(0, new Component[] { this.cbSelStash, pnlMod });
/* 307 */     layout.linkSize(0, new Component[] { this.cbSelStash, this.tabPages });
/*     */     
/* 309 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildModPanel() {
/* 313 */     GroupLayout layout = null;
/* 314 */     GroupLayout.SequentialGroup hGroup = null;
/* 315 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 317 */     JPanel panel = new JPanel();
/*     */     
/* 319 */     layoutStash();
/*     */     
/* 321 */     layout = new GroupLayout(panel);
/* 322 */     panel.setLayout(layout);
/*     */     
/* 324 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 327 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 330 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 333 */     hGroup
/* 334 */       .addGroup(layout.createParallelGroup()
/* 335 */         .addComponent(this.lblSelMod))
/* 336 */       .addGroup(layout.createParallelGroup()
/* 337 */         .addComponent(this.cbSelMod));
/* 338 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 341 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 344 */     vGroup
/* 345 */       .addGroup(layout.createParallelGroup()
/* 346 */         .addComponent(this.lblSelMod)
/* 347 */         .addComponent(this.cbSelMod));
/* 348 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 350 */     layout.linkSize(1, new Component[] { this.lblSelMod, this.cbSelMod });
/*     */     
/* 352 */     return panel;
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
/*     */   public void refreshStashSelection() {
/* 365 */     this.blockEvents = true;
/*     */     
/* 367 */     int idx = -1;
/* 368 */     this.cbSelStash.removeAllItems();
/* 369 */     String[] entries = GDStashInfoList.getStashInfos();
/* 370 */     for (int i = 0; i < entries.length; i++) {
/* 371 */       this.cbSelStash.addItem(entries[i]);
/*     */       
/* 373 */       if (this.info != null && 
/* 374 */         entries[i].equals(this.info.stashInfo)) idx = i;
/*     */     
/*     */     } 
/*     */     
/* 378 */     this.cbSelMod.removeAllItems();
/* 379 */     if (GDStashInfoList.modDirs != null) {
/* 380 */       for (String s : GDStashInfoList.modDirs) {
/* 381 */         this.cbSelMod.addItem(s);
/*     */       }
/*     */       
/* 384 */       if (GDStashInfoList.modDirs.isEmpty()) {
/* 385 */         this.cbSelMod.addItem("");
/*     */       }
/*     */     } 
/*     */     
/* 389 */     this.blockEvents = false;
/*     */     
/* 391 */     if (idx == -1) {
/*     */ 
/*     */       
/* 394 */       this.cbSelStash.setSelectedIndex(0);
/*     */     } else {
/* 396 */       this.cbSelStash.setSelectedIndex(idx);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setStash(GDStashInfoList.GDStashFileInfo info) {
/* 401 */     this.info = info;
/*     */     
/* 403 */     this.uiTransfer.setStash(info);
/*     */     
/* 405 */     GDStash stash = null;
/* 406 */     if (info != null) stash = info.gdStash;
/*     */     
/* 408 */     if (stash == null) {
/* 409 */       this.cbSelMod.setSelectedItem("");
/*     */       
/* 411 */       if (this.containerType == 1) {
/* 412 */         GDImagePool.buildSharedStash(8, 16);
/*     */       }
/* 414 */       if (this.containerType == 2) {
/* 415 */         GDImagePool.buildCraftingStash(8, 16);
/*     */       }
/*     */     } else {
/* 418 */       this.cbSelMod.setSelectedItem(stash.getModName());
/*     */       
/* 420 */       if (this.containerType == 1) {
/* 421 */         GDImagePool.buildSharedStash(stash.getWidth(), stash.getHeight());
/*     */       }
/* 423 */       if (this.containerType == 2) {
/* 424 */         GDImagePool.buildCraftingStash(stash.getWidth(), stash.getHeight());
/*     */       }
/*     */     } 
/*     */     
/* 428 */     if (this.containerType == 1) {
/* 429 */       for (int i = 0; i < this.pnlPages.length; i++) {
/* 430 */         this.pnlPages[i].setBGImage(GDImagePool.getSharedStashGrid());
/*     */       }
/*     */     }
/*     */     
/* 434 */     if (this.containerType == 2) {
/* 435 */       for (int i = 0; i < this.pnlPages.length; i++) {
/* 436 */         this.pnlPages[i].setBGImage(GDImagePool.getCraftingStashGrid());
/*     */       }
/*     */     }
/*     */     
/* 440 */     setSelectedItem((GDItem)null);
/*     */     
/* 442 */     layoutStash();
/*     */   }
/*     */   
/*     */   public GDContainerMapPane getCurrentPage() {
/* 446 */     int index = this.tabPages.getSelectedIndex();
/*     */     
/* 448 */     return this.pnlPages[index];
/*     */   }
/*     */   
/*     */   public void layoutStash() {
/* 452 */     GDStash stash = null;
/*     */     
/* 454 */     if (this.info != null) stash = this.info.gdStash;
/*     */     
/* 456 */     int i = 0;
/* 457 */     if (stash != null) {
/* 458 */       for (GDStashPage page : stash.getPages()) {
/*     */         
/* 460 */         if (i >= this.pnlPages.length)
/*     */           break; 
/* 462 */         page.setContainerType(this.containerType);
/*     */         
/* 464 */         GDContainerPane container = new GDContainerPane(this.containerType, 0, 0, this.frame, this, this.uiTransfer);
/* 465 */         container.setContainer((GDItemContainer)page);
/*     */         
/* 467 */         this.pnlPages[i].clearContainers();
/* 468 */         this.pnlPages[i].addContainer(container);
/* 469 */         this.tabPages.setEnabledAt(i, true);
/*     */         
/* 471 */         i++;
/*     */       } 
/*     */     }
/*     */     
/* 475 */     while (i < this.pnlPages.length) {
/* 476 */       GDContainerPane container = new GDContainerPane(this.containerType, 0, 0, this.frame, this, this.uiTransfer);
/*     */       
/* 478 */       this.pnlPages[i].clearContainers();
/* 479 */       this.pnlPages[i].addContainer(container);
/* 480 */       this.tabPages.setEnabledAt(i, false);
/*     */       
/* 482 */       i++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateModSelection() {
/* 487 */     if (this.info == null)
/* 488 */       return;  if (this.info.gdStash == null)
/*     */       return; 
/* 490 */     String s = this.info.gdStash.getModName();
/* 491 */     if (s == null) s = "";
/*     */     
/* 493 */     this.cbSelMod.setSelectedItem(s);
/*     */   }
/*     */   
/*     */   public void deleteSelectedItem(int action) {
/* 497 */     if (this.selItem == null)
/*     */       return; 
/* 499 */     boolean success = this.selPage.deleteItem(this.selItem, action, true);
/*     */     
/* 501 */     if (success) setSelectedItem((GDItem)null); 
/*     */   }
/*     */   
/*     */   public void moveSelectedItem(int action, int x, int y) {
/* 505 */     if (this.selItem == null)
/*     */       return; 
/* 507 */     GDContainerMapPane page = getCurrentPage();
/* 508 */     GDItem clone = this.selItem.clone();
/* 509 */     boolean success = page.addItem(clone, action, x, y);
/*     */     
/* 511 */     if (success) {
/* 512 */       success = this.selPage.deleteItem(this.selItem, action, true);
/*     */       
/* 514 */       if (success) {
/* 515 */         this.selItem = clone;
/* 516 */         this.selPage = page;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isModStashConflict(String mod) {
/* 522 */     if (this.info == null) return false; 
/* 523 */     if (this.info.gdStash == null) return false; 
/* 524 */     if (GDStashFrame.iniConfig == null) return false; 
/* 525 */     if (GDStashFrame.iniConfig.sectDir.savePath == null) return false;
/*     */     
/* 527 */     boolean conflict = false;
/*     */     
/*     */     try {
/* 530 */       String sMod = mod;
/* 531 */       if (sMod == null) sMod = "";
/*     */       
/* 533 */       String stashMod = this.info.gdStash.getModName();
/* 534 */       if (stashMod == null) stashMod = "";
/*     */       
/* 536 */       if (sMod.equals(stashMod)) return false;
/*     */       
/* 538 */       String dir = this.info.gdStash.getFile().getParentFile().getCanonicalPath();
/*     */       
/* 540 */       if (dir.length() > GDStashFrame.iniConfig.sectDir.savePath.length()) {
/* 541 */         dir = dir.substring(GDStashFrame.iniConfig.sectDir.savePath.length() + 1);
/*     */       } else {
/* 543 */         dir = "";
/*     */       } 
/*     */       
/* 546 */       if (mod.equals(dir)) return false;
/*     */       
/* 548 */       dir = GDStashFrame.iniConfig.sectDir.savePath;
/* 549 */       if (!dir.endsWith(GDConstants.FILE_SEPARATOR)) dir = dir + GDConstants.FILE_SEPARATOR;
/*     */       
/* 551 */       if (!sMod.equals("")) dir = dir + sMod + GDConstants.FILE_SEPARATOR;
/*     */       
/* 553 */       if (this.info.gdStash.isHardcore()) {
/* 554 */         dir = dir + "transfer.gsh";
/*     */       } else {
/* 556 */         dir = dir + "transfer.gst";
/*     */       } 
/*     */       
/* 559 */       File file = new File(dir);
/*     */       
/* 561 */       conflict = file.exists();
/*     */     }
/* 563 */     catch (IOException iOException) {}
/*     */     
/* 565 */     return conflict;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GDItem getSelectedItem() {
/* 574 */     return this.selItem;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelectedItem(GDItem item) {
/* 579 */     this.selItem = item;
/*     */     
/* 581 */     if (item == null) {
/* 582 */       this.selPage = null;
/*     */     } else {
/* 584 */       this.selPage = getCurrentPage();
/*     */       
/* 586 */       this.selPage.layoutContainers();
/*     */     } 
/*     */     
/* 589 */     this.uiTransfer.setSelectedItem(item, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh() {
/* 594 */     if (this.pnlPages != null) {
/* 595 */       for (int i = 0; i < this.pnlPages.length; i++) {
/* 596 */         this.pnlPages[i].refresh();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean addItem(GDItem item, int action, int x, int y) {
/* 602 */     if (item == null) return false;
/*     */     
/* 604 */     GDContainerMapPane page = getCurrentPage();
/* 605 */     return page.addItem(item.clone(), action, x, y);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\stash\GDSharedStashPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */