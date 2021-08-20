/*     */ package org.gdstash.ui.stash;
/*     */ import java.awt.*;
/*     */ import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
/*     */ import javax.swing.*;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.character.GDCharInventorySack;
import org.gdstash.item.GDAbstractContainer;
/*     */ import org.gdstash.item.GDItem;
/*     */ import org.gdstash.item.GDItemContainer;
import org.gdstash.ui.GDStashFrame;
import org.gdstash.ui.GDUITransfer;
import org.gdstash.ui.util.AdjustablePanel;
import org.gdstash.ui.util.GDCharInfoList;
/*     */ import org.gdstash.util.GDConstants;
import org.gdstash.util.GDImagePool;
import org.gdstash.util.GDMsgFormatter;
import org.gdstash.util.GDMsgLogger;

import java.io.IOException;
import java.util.LinkedList;
import  java.util.List;

/*     */
/*     */ public class GDCharStashPane extends AdjustablePanel implements GDUIInventory {
/*     */   public static final int OFFSET_X_STASH_CHAR = 117;
/*     */   public static final int OFFSET_Y_STASH_CHAR = 99;
/*     */   public static final int OFFSET_X_STASH_CHAR_TAB = 97;
/*     */   public static final int OFFSET_Y_STASH_CHAR_TAB = 100;
/*     */   private static final int OFFSET_X_EQUIPPED_CHAR = 19;
/*     */   private static final int OFFSET_Y_EQUIPPED_CHAR = 33;
/*     */   private static final int OFFSET_X_INVENTORY_CHAR = 86;
/*     */   private static final int OFFSET_Y_INVENTORY_CHAR = 485;
/*     */   private static final int OFFSET_X_BAG_1_CHAR = 15;
/*     */   private static final int OFFSET_Y_BAG_1_CHAR = 0;
/*     */   private static final int OFFSET_X_BAG_2_CHAR = 287;
/*     */   private static final int OFFSET_Y_BAG_2_CHAR = 0;
/*     */   private static final int OFFSET_X_BAG_3_CHAR = 15;
/*     */   private static final int OFFSET_Y_BAG_3_CHAR = 261;
/*     */   private static final int OFFSET_X_BAG_4_CHAR = 287;
/*     */   private static final int OFFSET_Y_BAG_4_CHAR = 261;
/*     */   private static final int OFFSET_X_BAG_5_CHAR = 15;
/*     */   private static final int OFFSET_Y_BAG_5_CHAR = 521;
/*     */   private GDStashFrame frame;
/*     */   private JTabbedPane tabPages;
/*     */   private GDContainerMapPane[] pnlPages;
/*     */   private JComboBox<String> cbSelChar;
/*     */   private GDCharInfoList.GDCharFileInfo info;
/*     */   private GDItem selItem;
/*     */   private GDContainerMapPane selPage;
/*     */   private JPanel pnlMain;
/*     */   private GDUITransfer uiTransfer;
/*     */   
/*     */   private class CharSelectActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent ev) {
/*  42 */       int idx = GDCharStashPane.this.cbSelChar.getSelectedIndex();
/*     */       
/*  44 */       if (idx == -1) {
/*     */         return;
/*     */       }
/*  47 */       if (idx == 0) {
/*  48 */         GDCharStashPane.this.setChar(null);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  53 */       int i = 1;
/*  54 */       for (GDCharInfoList.GDCharFileInfo info : GDCharInfoList.gdCharFileInfos) {
/*  55 */         if (i == idx) {
/*  56 */           if (info.gdChar == null) {
/*  57 */             info.gdChar = new GDChar(info.charFile);
/*  58 */             info.gdChar.read();
/*     */           } 
/*     */           
/*  61 */           if (info.gdChar.hasErrors()) info.gdChar = null;
/*     */           
/*  63 */           GDCharStashPane.this.setChar(info);
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/*  68 */         i++;
/*     */       } 
/*     */       
/*  71 */       GDMsgLogger.showLog((Component)GDCharStashPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*     */     }
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
/*     */     private CharSelectActionListener() {}
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
/*     */   public GDCharStashPane(GDStashFrame frame, GDUITransfer uiTransfer) {
/* 110 */     this.frame = frame;
/* 111 */     this.uiTransfer = uiTransfer;
/*     */     
/* 113 */     this.pnlPages = new GDContainerMapPane[3];
/* 114 */     this.selItem = null;
/* 115 */     this.selPage = null;
/*     */     
/* 117 */     adjustUI();
/*     */ 
/*     */ 
/*     */     
/* 121 */     GroupLayout layout = null;
/* 122 */     GroupLayout.SequentialGroup hGroup = null;
/* 123 */     GroupLayout.SequentialGroup vGroup = null;
/*     */ 
/*     */ 
/*     */     
/* 127 */     this.pnlMain = buildMainPanel();
/*     */     
/* 129 */     layout = new GroupLayout((Container)this);
/* 130 */     setLayout(layout);
/*     */     
/* 132 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 135 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 138 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 141 */     hGroup
/* 142 */       .addGroup(layout.createParallelGroup()
/* 143 */         .addComponent(this.pnlMain));
/* 144 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 147 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 150 */     vGroup
/* 151 */       .addGroup(layout.createParallelGroup()
/* 152 */         .addComponent(this.pnlMain));
/* 153 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 158 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 159 */     Font fntCombo = UIManager.getDefaults().getFont("ComboBox.font");
/* 160 */     if (fntCombo == null) fntCombo = fntLabel; 
/* 161 */     Font fntTabbed = UIManager.getDefaults().getFont("TabbedPane.font");
/* 162 */     if (fntTabbed == null) fntTabbed = fntLabel;
/*     */     
/* 164 */     fntCombo = fntCombo.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 165 */     fntTabbed = fntTabbed.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 167 */     GDChar gdc = null;
/* 168 */     if (this.info != null) gdc = this.info.gdChar;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     if (this.pnlPages[0] == null) {
/* 174 */       this.pnlPages[0] = new GDPrivateStashPane(gdc, this.frame, this, this.uiTransfer);
/*     */       
/* 176 */       if (gdc != null) {
/* 177 */         List<GDAbstractContainer> list = gdc.getStashPages();
/*     */         
/* 179 */         for (GDAbstractContainer page : list) {
/* 180 */           GDContainerPane container = new GDContainerPane(3, 0, 0, this.frame, this, this.uiTransfer);
/* 181 */           container.setContainer((GDItemContainer)page);
/*     */           
/* 183 */           this.pnlPages[0].addContainer(container);
/*     */         } 
/*     */       } 
/*     */     } 
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
/* 199 */     if (this.pnlPages[1] == null) {
/* 200 */       this.pnlPages[1] = new GDContainerMapPane(this, this.uiTransfer, GDImagePool.getCharStashBG());
/*     */       
/* 202 */       GDUIContainer container1 = new GDEquippedContainerPane(19, 33, this.frame, this, this.uiTransfer);
/*     */       
/* 204 */       GDUIContainer container2 = new GDContainerPane(4, 86, 485, this.frame, this, this.uiTransfer);
/*     */ 
/*     */       
/* 207 */       if (gdc != null) {
/* 208 */         container1.setContainer((GDItemContainer)gdc.getEquipment());
/* 209 */         container2.setContainer((GDItemContainer)gdc.getInventory());
/*     */       } 
/*     */       
/* 212 */       this.pnlPages[1].addContainer(container1);
/* 213 */       this.pnlPages[1].addContainer(container2);
/*     */     } 
/*     */     
/* 216 */     if (this.pnlPages[2] == null) {
/* 217 */       this.pnlPages[2] = new GDContainerMapPane(this, this.uiTransfer, GDImagePool.getCharStashBG());
/*     */       
/* 219 */       buildBagContainer(gdc);
/*     */     } 
/*     */     
/* 222 */     if (this.tabPages == null) {
/* 223 */       this.tabPages = new JTabbedPane();
/* 224 */       this.tabPages.setTabPlacement(1);
/*     */     } 
/* 226 */     this.tabPages.removeAll();
/* 227 */     this.tabPages.add(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_CHAR_STASH"), (Component)this.pnlPages[0]);
/* 228 */     this.tabPages.add(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_CHAR_INVENTORY"), (Component)this.pnlPages[1]);
/* 229 */     this.tabPages.add(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_CHAR_BAGS"), (Component)this.pnlPages[2]);
/* 230 */     this.tabPages.setFont(fntTabbed);
/* 231 */     this.tabPages.setMaximumSize(new Dimension(this.pnlPages[0].getPreferredWidth(), this.pnlPages[0].getPreferredHeight() + 20));
/*     */     
/* 233 */     if (this.cbSelChar == null) {
/* 234 */       this.cbSelChar = new JComboBox<>();
/*     */       
/* 236 */       this.cbSelChar.addActionListener(new CharSelectActionListener());
/*     */     } 
/* 238 */     int idx = this.cbSelChar.getSelectedIndex();
/* 239 */     this.cbSelChar.removeAllItems();
/* 240 */     String[] entries = GDCharInfoList.getCharInfos(); int i;
/* 241 */     for (i = 0; i < entries.length; i++) {
/* 242 */       this.cbSelChar.addItem(entries[i]);
/*     */     }
/* 244 */     if (idx != -1) this.cbSelChar.setSelectedIndex(idx); 
/* 245 */     this.cbSelChar.setFont(fntCombo);
/* 246 */     this.cbSelChar.setMaximumSize(new Dimension(1000, 3 * GDStashFrame.iniConfig.sectUI.fontSize));
/*     */   }
/*     */   
/*     */   public void refresh() {
/* 250 */     if (this.pnlPages[0] != null) this.pnlPages[0].refresh(); 
/* 251 */     if (this.pnlPages[1] != null) this.pnlPages[1].refresh(); 
/* 252 */     if (this.pnlPages[2] != null) this.pnlPages[2].refresh(); 
/*     */   }
/*     */   
/*     */   private JPanel buildMainPanel() {
/* 256 */     GroupLayout layout = null;
/* 257 */     GroupLayout.SequentialGroup hGroup = null;
/* 258 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 260 */     JPanel panel = new JPanel();
/*     */     
/* 262 */     layoutStash();
/*     */     
/* 264 */     layout = new GroupLayout(panel);
/* 265 */     panel.setLayout(layout);
/*     */     
/* 267 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 270 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 273 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 276 */     hGroup
/* 277 */       .addGroup(layout.createParallelGroup()
/* 278 */         .addComponent(this.cbSelChar)
/* 279 */         .addComponent(this.tabPages));
/* 280 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 283 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 286 */     vGroup
/* 287 */       .addGroup(layout.createParallelGroup()
/* 288 */         .addComponent(this.cbSelChar))
/* 289 */       .addGroup(layout.createParallelGroup()
/* 290 */         .addComponent(this.tabPages));
/* 291 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 293 */     layout.linkSize(0, new Component[] { this.cbSelChar, this.tabPages });
/*     */     
/* 295 */     return panel;
/*     */   }
/*     */   
/*     */   public void initCharSelection() {
/* 299 */     this.cbSelChar.removeAllItems();
/* 300 */     this.cbSelChar.addItem("");
/*     */     
/* 302 */     for (GDCharInfoList.GDCharFileInfo info : GDCharInfoList.gdCharFileInfos) {
/* 303 */       this.cbSelChar.addItem(info.charInfo);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void refreshCharSelection() {
/* 309 */     File file = null;
/*     */     
/* 311 */     if (this.info != null) file = this.info.charFile;
/*     */     
/* 313 */     this.cbSelChar.removeAllItems();
/* 314 */     this.cbSelChar.addItem("");
/*     */ 
/*     */ 
/*     */     
/* 318 */     GDCharInfoList.GDCharFileInfo selInfo = null;
/* 319 */     int idx = -1;
/* 320 */     int i = 1;
/*     */     
/* 322 */     for (GDCharInfoList.GDCharFileInfo info : GDCharInfoList.gdCharFileInfos) {
/* 323 */       this.cbSelChar.addItem(info.charInfo);
/*     */       
/* 325 */       if (info.charFile.equals(file)) {
/* 326 */         selInfo = info;
/* 327 */         idx = i;
/*     */       } 
/*     */       
/* 330 */       i++;
/*     */     } 
/*     */     
/* 333 */     if (idx == -1) {
/* 334 */       setChar((GDCharInfoList.GDCharFileInfo)null);
/*     */     } else {
/* 336 */       this.cbSelChar.setSelectedIndex(idx);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renameCharDir(File fDirOld, File fDirNew) {
/* 341 */     if (this.info == null)
/* 342 */       return;  if (this.info.charFile == null)
/*     */       return; 
/* 344 */     File fDir = this.info.charFile.getParentFile();
/*     */     
/* 346 */     if (fDir == null)
/*     */       return; 
/* 348 */     if (fDir.equals(fDirOld)) {
/*     */       try {
/* 350 */         String name = this.info.charFile.getName();
/* 351 */         String dir = fDirNew.getCanonicalPath() + GDConstants.FILE_SEPARATOR + name;
/*     */         
/* 353 */         File f = new File(dir);
/*     */         
/* 355 */         this.info.charFile = f;
/* 356 */         if (this.info.gdChar != null) this.info.gdChar.setFileDir(fDirNew);
/*     */         
/* 358 */         setChar(this.info);
/*     */       }
/* 360 */       catch (IOException ex) {
/* 361 */         GDMsgLogger.addError(ex);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void setChar(GDCharInfoList.GDCharFileInfo info) {
/* 367 */     this.info = info;
/*     */     
/* 369 */     this.uiTransfer.setChar(info);
/*     */     
/* 371 */     GDChar gdc = null;
/* 372 */     if (info != null) gdc = info.gdChar;
/*     */     
/* 374 */     if (gdc == null) {
/* 375 */       GDImagePool.buildCharStash(10, 18);
/*     */     } else {
/* 377 */       GDImagePool.buildCharStash(gdc.getStashWidth(), gdc.getStashHeight());
/*     */     } 
/*     */     
/* 380 */     for (int i = 0; i < this.pnlPages.length; i++) {
/* 381 */       this.pnlPages[i].setBGImage(GDImagePool.getCharStashBG());
/* 382 */       this.pnlPages[i].revalidate();
/* 383 */       this.pnlPages[i].repaint();
/*     */     } 
/*     */     
/* 386 */     this.tabPages.revalidate();
/* 387 */     this.tabPages.repaint();
/*     */     
/* 389 */     layoutStash();
/*     */   }
/*     */   
/*     */   public GDContainerMapPane getCurrentPage() {
/* 393 */     int index = this.tabPages.getSelectedIndex();
/*     */     
/* 395 */     return this.pnlPages[index];
/*     */   }
/*     */   
/*     */   public void layoutStash() {
/* 399 */     if (this.tabPages == null)
/*     */       return; 
/* 401 */     GDChar gdc = null;
/*     */     
/* 403 */     if (this.info != null) gdc = this.info.gdChar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 412 */     this.pnlPages[0].clearContainers();
/*     */     
/* 414 */     if (gdc != null) {
/* 415 */       List<GDAbstractContainer> list = gdc.getStashPages();
/*     */       
/* 417 */       for (GDAbstractContainer page : list) {
/* 418 */         GDContainerPane container = new GDContainerPane(3, 97, 100, this.frame, this, this.uiTransfer);
/* 419 */         container.setContainer((GDItemContainer)page);
/*     */         
/* 421 */         this.pnlPages[0].addContainer(container);
/*     */       } 
/*     */     } 
/*     */     
/* 425 */     this.pnlPages[1].clearContainers();
/* 426 */     GDUIContainer container1 = new GDEquippedContainerPane(19, 33, this.frame, this, this.uiTransfer);
/*     */     
/* 428 */     GDUIContainer container2 = new GDContainerPane(4, 86, 485, this.frame, this, this.uiTransfer);
/*     */     
/* 430 */     if (gdc != null) {
/* 431 */       container1.setContainer((GDItemContainer)this.info.gdChar.getEquipment());
/* 432 */       container2.setContainer((GDItemContainer)this.info.gdChar.getInventory());
/*     */     } 
/* 434 */     this.pnlPages[1].addContainer(container1);
/* 435 */     this.pnlPages[1].addContainer(container2);
/*     */     
/* 437 */     if (gdc == null) {
/* 438 */       buildBagContainer((GDChar)null);
/*     */     } else {
/* 440 */       buildBagContainer(gdc);
/*     */     } 
/*     */     
/* 443 */     this.tabPages.removeAll();
/* 444 */     this.tabPages.add(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_CHAR_STASH"), (Component)this.pnlPages[0]);
/* 445 */     this.tabPages.add(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_CHAR_INVENTORY"), (Component)this.pnlPages[1]);
/* 446 */     this.tabPages.add(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_CHAR_BAGS"), (Component)this.pnlPages[2]);
/*     */   }
/*     */   
/*     */   private void buildBagContainer(GDChar gdc) {
/* 450 */     List<GDCharInventorySack> sacks = null;
/*     */     
/* 452 */     if (gdc == null) {
/* 453 */       sacks = new LinkedList<>();
/*     */     } else {
/* 455 */       sacks = gdc.getBags();
/*     */     } 
/*     */     
/* 458 */     this.pnlPages[2].clearContainers();
/*     */     
/* 460 */     int i = 0;
/* 461 */     int x = 0;
/* 462 */     int y = 0;
/*     */     
/* 464 */     for (GDCharInventorySack sack : sacks) {
/* 465 */       switch (i) {
/*     */         case 0:
/* 467 */           x = 15;
/* 468 */           y = 0;
/*     */           break;
/*     */         
/*     */         case 1:
/* 472 */           x = 287;
/* 473 */           y = 0;
/*     */           break;
/*     */         
/*     */         case 2:
/* 477 */           x = 15;
/* 478 */           y = 261;
/*     */           break;
/*     */         
/*     */         case 3:
/* 482 */           x = 287;
/* 483 */           y = 261;
/*     */           break;
/*     */         
/*     */         case 4:
/* 487 */           x = 15;
/* 488 */           y = 521;
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 493 */       GDUIContainer container = new GDContainerPane(5, x, y, this.frame, this, this.uiTransfer);
/* 494 */       container.setContainer((GDItemContainer)sack);
/* 495 */       this.pnlPages[2].addContainer(container);
/*     */       
/* 497 */       i++;
/*     */     } 
/*     */     
/* 500 */     while (i <= 4) {
/* 501 */       switch (i) {
/*     */         case 0:
/* 503 */           x = 15;
/* 504 */           y = 0;
/*     */           break;
/*     */         
/*     */         case 1:
/* 508 */           x = 287;
/* 509 */           y = 0;
/*     */           break;
/*     */         
/*     */         case 2:
/* 513 */           x = 15;
/* 514 */           y = 261;
/*     */           break;
/*     */         
/*     */         case 3:
/* 518 */           x = 287;
/* 519 */           y = 261;
/*     */           break;
/*     */         
/*     */         case 4:
/* 523 */           x = 15;
/* 524 */           y = 521;
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 529 */       GDUIContainer container = new GDContainerPane(5, x, y, this.frame, this, this.uiTransfer);
/* 530 */       container.setContainer(null);
/* 531 */       this.pnlPages[2].addContainer(container);
/*     */       
/* 533 */       i++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void deleteSelectedItem(int action) {
/* 538 */     if (this.selItem == null)
/*     */       return; 
/* 540 */     this.selPage.deleteItem(this.selItem, action, true);
/*     */     
/* 542 */     setSelectedItem((GDItem)null);
/*     */   }
/*     */   
/*     */   public void moveSelectedItem(int action, int x, int y) {
/* 546 */     if (this.selItem == null)
/*     */       return; 
/* 548 */     GDContainerMapPane page = getCurrentPage();
/* 549 */     GDItem clone = this.selItem.clone();
/*     */     
/* 551 */     boolean success = page.addItem(clone, action, x, y);
/*     */     
/* 553 */     if (success) {
/* 554 */       success = this.selPage.deleteItem(this.selItem, action, true);
/*     */       
/* 556 */       if (success) {
/* 557 */         this.selItem = clone;
/* 558 */         this.selPage = page;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GDItem getSelectedItem() {
/* 569 */     return this.selItem;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelectedItem(GDItem item) {
/* 574 */     this.selItem = item;
/*     */     
/* 576 */     if (item == null) {
/* 577 */       this.selPage = null;
/*     */     } else {
/* 579 */       this.selPage = getCurrentPage();
/*     */       
/* 581 */       this.selPage.layoutContainers();
/*     */     } 
/*     */     
/* 584 */     this.uiTransfer.setSelectedItem(item, 1);
/*     */   }
/*     */   
/*     */   public boolean addItem(GDItem item, int action, int x, int y) {
/* 588 */     if (item == null) return false;
/*     */     
/* 590 */     GDContainerMapPane page = getCurrentPage();
/* 591 */     return page.addItem(item.clone(), action, x, y);
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\stash\GDCharStashPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */