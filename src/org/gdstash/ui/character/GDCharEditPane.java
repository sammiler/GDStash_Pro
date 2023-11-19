/*     */ package org.gdstash.ui.character;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.UIManager;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.ui.GDHelpActionListener;
/*     */ import org.gdstash.ui.GDLogoDialog;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ import org.gdstash.ui.util.GDCharInfoList;
/*     */ import org.gdstash.util.GDConstants;
/*     */ import org.gdstash.util.GDImagePool;
/*     */ import org.gdstash.util.GDLog;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ import org.gdstash.util.GDMsgLogger;
/*     */ 
/*     */ public class GDCharEditPane extends AdjustablePanel {
/*     */   private GDStashFrame frame;
/*     */   private GDCharEditTabbedPane tabEdit;
/*     */   private JButton btnReload;
/*     */   private JButton btnHelp;
/*     */   private JComboBox<String> cbSelChar;
/*     */   private JButton btnFileSave;
/*     */   private JPanel pnlMain;
/*     */   private GDCharInfoList.GDCharFileInfo info;
/*     */   
/*     */   private class ReloadActionListener implements ActionListener { public void actionPerformed(ActionEvent ev) {
/*  42 */       GDCharInfoList.findChars(GDCharEditPane.this.frame, GDCharEditPane.this.info);
/*     */       
/*  44 */       if (GDCharEditPane.this.info != null) {
/*  45 */         for (GDCharInfoList.GDCharFileInfo ci : GDCharInfoList.gdCharFileInfos) {
/*  46 */           if (ci.fileName.equals(GDCharEditPane.this.info.fileName)) {
/*  47 */             GDCharEditPane.this.info = ci;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/*  54 */       if (GDCharEditPane.this.frame.pnlCharInventory != null) {
/*  55 */         GDCharEditPane.this.frame.pnlCharInventory.refreshCharInfo(GDCharEditPane.this.info);
/*     */       }
/*  57 */       if (GDCharEditPane.this.frame.pnlCharEdit != null) {
/*  58 */         GDCharEditPane.this.frame.pnlCharEdit.refreshCharInfo(GDCharEditPane.this.info);
/*     */       }
/*     */       
/*  61 */       GDMsgLogger.showLog((Component)GDCharEditPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*     */     }
/*     */     private ReloadActionListener() {} }
/*     */   
/*     */   private class CharSelectActionListener implements ActionListener { private CharSelectActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent ev) {
/*  68 */       int idx = GDCharEditPane.this.cbSelChar.getSelectedIndex();
/*     */       
/*  70 */       if (idx == -1) {
/*     */         return;
/*     */       }
/*  73 */       if (idx == 0) {
/*  74 */         GDCharEditPane.this.setChar(null);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  79 */       int i = 1;
/*     */       
/*  81 */       for (GDCharInfoList.GDCharFileInfo info : GDCharInfoList.gdCharFileInfos) {
/*  82 */         if (i == idx) {
/*  83 */           if (info.gdChar == null) {
/*  84 */             info.gdChar = new GDChar(info.charFile);
/*  85 */             info.gdChar.read();
/*     */           } 
/*     */           
/*  88 */           if (info.gdChar.hasErrors()) info.gdChar = null;
/*     */           
/*  90 */           if (info.gdChar != null) info.gdChar.getMasteryInfo();
/*     */           
/*  92 */           GDCharEditPane.this.setChar(info);
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/*  97 */         i++;
/*     */       } 
/*     */       
/* 100 */       GDMsgLogger.showLog((Component)GDCharEditPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*     */     } }
/*     */ 
/*     */   
/*     */   private class FileSaveActionListener implements ActionListener {
/*     */     private FileSaveActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent ev) {
/* 108 */       if (GDCharEditPane.this.info == null)
/* 109 */         return;  if (GDCharEditPane.this.info.charFile == null)
/* 110 */         return;  if (GDCharEditPane.this.info.gdChar == null)
/*     */         return; 
/* 112 */       if (GDCharEditPane.this.frame.isCloudSaveDir() && 
/* 113 */         GDStashFrame.isGrimDawnRunning()) {
/* 114 */         GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_CLOUD_SAVE"));
/* 115 */         GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_CLOSE"));
/*     */         
/* 117 */         GDMsgLogger.showLog((Component)GDCharEditPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_RUNNING"), GDLog.MessageType.Info, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_RUNNING"));
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */       
/* 124 */       String origName = GDCharEditPane.this.info.gdChar.getCharName();
/* 125 */       String newName = GDCharEditPane.this.tabEdit.getCharName();
/*     */       
/* 127 */       if (origName != null && newName != null && !origName.equals(newName)) {
/*     */         try {
/* 129 */           File fDirOrig = GDCharEditPane.this.info.charFile.getParentFile();
/* 130 */           String dirOrig = fDirOrig.getCanonicalPath();
/*     */           
/* 132 */           int pos = dirOrig.lastIndexOf(GDConstants.FILE_SEPARATOR);
/* 133 */           if (pos != -1) {
/* 134 */             String name = dirOrig.substring(pos + 2);
/* 135 */             String dirNew = dirOrig.substring(0, pos + 1);
/*     */             
/* 137 */             dirNew = dirNew + "_" + newName;
/*     */             
/* 139 */             File fDirNew = new File(dirNew);
/*     */             
/* 141 */             if (fDirNew.exists()) {
/*     */               
/* 143 */               GDLogoDialog dialog = new GDLogoDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_CHAR_EXISTS"), 4, (Frame)GDCharEditPane.this.frame);
/* 144 */               dialog.setVisible(true);
/*     */ 
/*     */               
/*     */               return;
/*     */             } 
/*     */           } 
/* 150 */         } catch (IOException ex) {
/* 151 */           GDMsgLogger.addError(ex);
/*     */         } 
/*     */       }
/*     */       
/* 155 */       GDCharEditPane.this.updateChar();
/*     */       
/* 157 */       boolean success = false;
/*     */       
/*     */       try {
/* 160 */         GDCharEditPane.this.info.gdChar.write();
/*     */         
/* 162 */         String charName = GDCharEditPane.this.info.gdChar.getCharName();
/* 163 */         if (charName != null && !charName.isEmpty()) {
/* 164 */           File fDirOrig = GDCharEditPane.this.info.charFile.getParentFile();
/* 165 */           String dirOrig = fDirOrig.getCanonicalPath();
/*     */           
/* 167 */           int pos = dirOrig.lastIndexOf(GDConstants.FILE_SEPARATOR);
/* 168 */           if (pos != -1) {
/* 169 */             String name = dirOrig.substring(pos + 2);
/* 170 */             String dirNew = dirOrig.substring(0, pos + 1);
/*     */             
/* 172 */             if (!name.equals(charName)) {
/* 173 */               dirNew = dirNew + "_" + charName;
/*     */               
/* 175 */               File fDirNew = new File(dirNew);
/*     */               
/* 177 */               fDirOrig.renameTo(fDirNew);
/*     */               
/* 179 */               GDStashFrame.renameCharDir(fDirOrig, fDirNew, GDCharEditPane.this.frame);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 184 */         success = true;
/*     */       }
/* 186 */       catch (IOException ex) {
/* 187 */         GDMsgLogger.addError(ex);
/*     */       } 
/*     */       
/* 190 */       GDMsgLogger.showLog((Component)GDCharEditPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_CHAR_SAVE"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_CHAR_SAVE"), true);
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
/*     */   public GDCharEditPane(GDStashFrame frame) {
/* 208 */     this.frame = frame;
/*     */     
/* 210 */     adjustUI();
/*     */     
/* 212 */     setChar(null);
/*     */ 
/*     */ 
/*     */     
/* 216 */     GroupLayout layout = null;
/* 217 */     GroupLayout.SequentialGroup hGroup = null;
/* 218 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 220 */     this.pnlMain = buildMainPanel();
/* 221 */     JScrollPane scroll = new JScrollPane(this.pnlMain);
/* 222 */     scroll.getVerticalScrollBar().setUnitIncrement(2 * GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 224 */     layout = new GroupLayout((Container)this);
/* 225 */     setLayout(layout);
/*     */     
/* 227 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 230 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 233 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 236 */     hGroup
/* 237 */       .addGroup(layout.createParallelGroup()
/* 238 */         .addComponent(scroll));
/* 239 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 242 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 245 */     vGroup
/* 246 */       .addGroup(layout.createParallelGroup()
/* 247 */         .addComponent(scroll));
/* 248 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 253 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 254 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/* 255 */     if (fntButton == null) fntButton = fntLabel; 
/* 256 */     Font fntCombo = UIManager.getDefaults().getFont("ComboBox.font");
/* 257 */     if (fntCombo == null) fntCombo = fntLabel;
/*     */     
/* 259 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 260 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 261 */     fntCombo = fntCombo.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */ 
/*     */ 
/*     */     
/* 265 */     GDCharInfoList.adjustCharInfos(null, null);
/*     */     
/* 267 */     if (this.tabEdit == null) {
/* 268 */       this.tabEdit = new GDCharEditTabbedPane();
/*     */     } else {
/* 270 */       this.tabEdit.adjustUI();
/*     */     } 
/*     */     
/* 273 */     if (this.btnReload == null) {
/* 274 */       this.btnReload = new JButton();
/*     */       
/* 276 */       this.btnReload.addActionListener(new ReloadActionListener());
/*     */     } 
/* 278 */     this.btnReload.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_RELOAD"));
/* 279 */     this.btnReload.setIcon(GDImagePool.iconBtnReload24);
/* 280 */     this.btnReload.setFont(fntButton);
/* 281 */     GDStashFrame.setMnemonic(this.btnReload, "MNC_RELOAD");
/*     */     
/* 283 */     if (this.btnHelp == null) {
/* 284 */       this.btnHelp = new JButton();
/*     */       
/* 286 */       this.btnHelp.addActionListener((ActionListener)new GDHelpActionListener("07_charedit.html"));
/*     */     } 
/* 288 */     this.btnHelp.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_HELP"));
/* 289 */     this.btnHelp.setIcon(GDImagePool.iconBtnQuestion24);
/* 290 */     this.btnHelp.setFont(fntButton);
/* 291 */     GDStashFrame.setMnemonic(this.btnHelp, "MNC_HELP");
/*     */     
/* 293 */     if (this.cbSelChar == null) {
/* 294 */       this.cbSelChar = new JComboBox<>();
/*     */       
/* 296 */       this.cbSelChar.addActionListener(new CharSelectActionListener());
/*     */     } 
/* 298 */     int idx = this.cbSelChar.getSelectedIndex();
/* 299 */     this.cbSelChar.removeAllItems();
/* 300 */     String[] entries = GDCharInfoList.getCharInfos(); int i;
/* 301 */     for (i = 0; i < entries.length; i++) {
/* 302 */       this.cbSelChar.addItem(entries[i]);
/*     */     }
/* 304 */     if (idx != -1) this.cbSelChar.setSelectedIndex(idx); 
/* 305 */     this.cbSelChar.setFont(fntCombo);
/* 306 */     this.cbSelChar.setMaximumSize(new Dimension(1000, 3 * GDStashFrame.iniConfig.sectUI.fontSize));
/*     */     
/* 308 */     if (this.btnFileSave == null) {
/* 309 */       this.btnFileSave = new JButton();
/*     */       
/* 311 */       this.btnFileSave.addActionListener(new FileSaveActionListener());
/*     */     } 
/* 313 */     this.btnFileSave.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_CHAR_SAVE"));
/* 314 */     this.btnFileSave.setIcon(GDImagePool.iconBtnSave24);
/* 315 */     this.btnFileSave.setFont(fntButton);
/* 316 */     GDStashFrame.setMnemonic(this.btnFileSave, "MNC_CHAR_SAVE");
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh() {
/* 321 */     refreshCharSelection();
/*     */   }
/*     */   
/*     */   private JPanel buildMainPanel() {
/* 325 */     JPanel panel = new JPanel();
/*     */     
/* 327 */     JPanel pnlCharSel = buildCharSelPanel();
/*     */     
/* 329 */     GroupLayout layout = null;
/* 330 */     GroupLayout.SequentialGroup hGroup = null;
/* 331 */     GroupLayout.SequentialGroup vGroup = null;
/*     */ 
/*     */ 
/*     */     
/* 335 */     layout = new GroupLayout(panel);
/* 336 */     panel.setLayout(layout);
/*     */     
/* 338 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 341 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 344 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 347 */     hGroup
/* 348 */       .addGroup(layout.createParallelGroup()
/* 349 */         .addComponent(pnlCharSel)
/* 350 */         .addComponent((Component)this.tabEdit));
/* 351 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 354 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 357 */     vGroup
/* 358 */       .addGroup(layout.createParallelGroup()
/* 359 */         .addComponent(pnlCharSel))
/* 360 */       .addGroup(layout.createParallelGroup()
/* 361 */         .addComponent((Component)this.tabEdit));
/* 362 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 364 */     layout.linkSize(0, new Component[] { pnlCharSel, (Component)this.tabEdit });
/*     */ 
/*     */ 
/*     */     
/* 368 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildCharSelPanel() {
/* 372 */     JPanel panel = new JPanel();
/*     */     
/* 374 */     JPanel pnlButtonFile = buildFileButtonPanel();
/*     */     
/* 376 */     GroupLayout layout = null;
/* 377 */     GroupLayout.SequentialGroup hGroup = null;
/* 378 */     GroupLayout.SequentialGroup vGroup = null;
/*     */ 
/*     */ 
/*     */     
/* 382 */     layout = new GroupLayout(panel);
/* 383 */     panel.setLayout(layout);
/*     */     
/* 385 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 388 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 391 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 394 */     hGroup
/* 395 */       .addGroup(layout.createParallelGroup()
/* 396 */         .addComponent(pnlButtonFile)
/* 397 */         .addComponent(this.cbSelChar));
/* 398 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 401 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 404 */     vGroup
/* 405 */       .addGroup(layout.createParallelGroup()
/* 406 */         .addComponent(pnlButtonFile))
/* 407 */       .addGroup(layout.createParallelGroup()
/* 408 */         .addComponent(this.cbSelChar));
/* 409 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 411 */     layout.linkSize(0, new Component[] { pnlButtonFile, this.cbSelChar });
/*     */     
/* 413 */     layout.linkSize(1, new Component[] { pnlButtonFile, this.cbSelChar });
/*     */     
/* 415 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildFileButtonPanel() {
/* 419 */     BorderLayout layout = new BorderLayout();
/*     */     
/* 421 */     JPanel panel = new JPanel();
/*     */     
/* 423 */     panel.setLayout(layout);
/*     */     
/* 425 */     panel.add(this.btnFileSave, "Center");
/* 426 */     panel.add(this.btnReload, "West");
/* 427 */     panel.add(this.btnHelp, "East");
/*     */     
/* 429 */     int size = 12;
/* 430 */     if (GDStashFrame.iniConfig != null) size = GDStashFrame.iniConfig.sectUI.fontSize;
/*     */     
/* 432 */     Dimension dimMax = new Dimension(50 * size, 2 * size);
/*     */     
/* 434 */     panel.setPreferredSize(dimMax);
/* 435 */     panel.setMaximumSize(dimMax);
/*     */     
/* 437 */     return panel;
/*     */   }
/*     */   
/*     */   public void initCharSelection() {
/* 441 */     this.cbSelChar.removeAllItems();
/* 442 */     this.cbSelChar.addItem("");
/*     */     
/* 444 */     for (GDCharInfoList.GDCharFileInfo info : GDCharInfoList.gdCharFileInfos) {
/* 445 */       this.cbSelChar.addItem(info.charInfo);
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateConfig() {
/* 450 */     if (this.tabEdit != null) this.tabEdit.updateConfig();
/*     */   
/*     */   }
/*     */   
/*     */   public void refreshCharSelection() {
/* 455 */     File file = null;
/*     */     
/* 457 */     if (this.info != null) file = this.info.charFile;
/*     */     
/* 459 */     this.cbSelChar.removeAllItems();
/* 460 */     this.cbSelChar.addItem("");
/*     */ 
/*     */ 
/*     */     
/* 464 */     GDCharInfoList.GDCharFileInfo selInfo = null;
/* 465 */     int idx = -1;
/* 466 */     int i = 1;
/*     */     
/* 468 */     for (GDCharInfoList.GDCharFileInfo info : GDCharInfoList.gdCharFileInfos) {
/* 469 */       this.cbSelChar.addItem(info.charInfo);
/*     */       
/* 471 */       if (info.charFile.equals(file)) {
/* 472 */         selInfo = info;
/* 473 */         idx = i;
/*     */       } 
/*     */       
/* 476 */       i++;
/*     */     } 
/*     */     
/* 479 */     if (idx == -1) {
/* 480 */       setChar((GDCharInfoList.GDCharFileInfo)null);
/*     */     } else {
/* 482 */       this.cbSelChar.setSelectedIndex(idx);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void refreshCharInfo(GDCharInfoList.GDCharFileInfo info) {
/* 487 */     if (info == null)
/* 488 */       return;  if (this.info == null)
/*     */       return; 
/* 490 */     if (this.info.fileName.equals(info.fileName)) {
/* 491 */       this.info = info;
/*     */       
/* 493 */       setChar(info);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setChar(GDCharInfoList.GDCharFileInfo info) {
/* 498 */     this.info = info;
/*     */     
/* 500 */     if (info == null) {
/* 501 */       this.tabEdit.setChar(null);
/*     */     } else {
/* 503 */       this.tabEdit.setChar(info.gdChar);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateChar() {
/* 508 */     if (this.info == null)
/* 509 */       return;  if (this.info.gdChar == null)
/*     */       return; 
/* 511 */     this.tabEdit.updateChar(this.info.gdChar);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharEditPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */