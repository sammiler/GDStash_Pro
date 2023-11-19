/*     */ package org.gdstash.ui;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.border.Border;
/*     */ import org.gdstash.db.DBAffix;
/*     */ import org.gdstash.db.DBAffixSet;
/*     */ import org.gdstash.db.DBLootTable;
/*     */ import org.gdstash.db.DBLootTableSet;
/*     */ import org.gdstash.item.GDItem;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDItemCraftPane extends AdjustablePanel {
/*     */   private JLabel lblImage;
/*     */   private JLabel lblName;
/*     */   private JLabel lblPrefix;
/*     */   private JLabel lblSuffix;
/*     */   private JLabel lblModifier;
/*     */   private JLabel lblCompletion;
/*     */   private JLabel lblSeed;
/*     */   private JLabel lblCount;
/*     */   private JLabel txtName;
/*     */   private JComboBox<DBAffix> cbPrefix;
/*     */   private JComboBox<DBAffix> cbSuffix;
/*     */   private JComboBox<DBAffix> cbModifier;
/*     */   private JComboBox<DBAffix> cbCompletion;
/*     */   private JFormattedTextField ftSeed;
/*     */   private JFormattedTextField ftCount;
/*     */   private List<DBLootTable> itemLootTables;
/*     */   private List<DBAffix> allPrefixes;
/*     */   private List<DBAffix> allSuffixes;
/*     */   private List<DBAffix> filterPrefixes;
/*     */   private List<DBAffix> filterSuffixes;
/*     */   private boolean skipCombo;
/*     */   private DefaultComboBoxModel<DBAffix> dmPrefix;
/*     */   private DefaultComboBoxModel<DBAffix> dmSuffix;
/*     */   private DefaultComboBoxModel<DBAffix> dmModifier;
/*     */   private DefaultComboBoxModel<DBAffix> dmModifierCelestial;
/*     */   private DefaultComboBoxModel<DBAffix> dmCompletion;
/*     */   private JLabel txtDescription;
/*     */   private GDItem item;
/*     */   
/*     */   private class ComboBoxActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  52 */       if (GDItemCraftPane.this.item == null)
/*  53 */         return;  if (GDItemCraftPane.this.skipCombo)
/*     */         return; 
/*  55 */       DBAffix affix = null;
/*     */       
/*  57 */       JComboBox<DBAffix> cb = (JComboBox)e.getSource();
/*     */       
/*  59 */       int i = cb.getSelectedIndex();
/*  60 */       if (i > 0) {
/*  61 */         affix = cb.getItemAt(i);
/*     */       }
/*     */       
/*  64 */       if (cb == GDItemCraftPane.this.cbPrefix) {
/*  65 */         GDItemCraftPane.this.item.setPrefix(affix);
/*     */         
/*  67 */         GDItemCraftPane.this.fillSuffixCombo();
/*     */       } 
/*  69 */       if (cb == GDItemCraftPane.this.cbSuffix) {
/*  70 */         GDItemCraftPane.this.item.setSuffix(affix);
/*     */         
/*  72 */         GDItemCraftPane.this.fillPrefixCombo();
/*     */       } 
/*     */       
/*  75 */       if (cb == GDItemCraftPane.this.cbModifier) GDItemCraftPane.this.item.setModifier(affix); 
/*  76 */       if (cb == GDItemCraftPane.this.cbCompletion) GDItemCraftPane.this.item.setCompletionBonus(affix);
/*     */       
/*  78 */       GDItemCraftPane.this.updateDescription();
/*     */       
/*  80 */       GDMsgLogger.showLog((Component)GDItemCraftPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
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
/*     */     
/*     */     private ComboBoxActionListener() {}
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
/*     */   public GDItemCraftPane() {
/* 120 */     this.skipCombo = false;
/*     */     
/* 122 */     this.allPrefixes = new LinkedList<>();
/* 123 */     this.allSuffixes = new LinkedList<>();
/* 124 */     this.filterPrefixes = new LinkedList<>();
/* 125 */     this.filterSuffixes = new LinkedList<>();
/*     */     
/* 127 */     adjustUI();
/*     */     
/* 129 */     GroupLayout layout = null;
/* 130 */     GroupLayout.SequentialGroup hGroup = null;
/* 131 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 133 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 134 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 135 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */     
/* 137 */     JPanel panel = buildInfoPanel();
/*     */     
/* 139 */     this.lblImage = new JLabel();
/* 140 */     this.lblImage.setBorder(compound);
/* 141 */     this.lblImage.setHorizontalAlignment(0);
/*     */     
/* 143 */     layout = new GroupLayout((Container)this);
/* 144 */     setLayout(layout);
/*     */     
/* 146 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 149 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 152 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 155 */     hGroup
/* 156 */       .addGroup(layout.createParallelGroup()
/* 157 */         .addComponent(this.lblImage)
/* 158 */         .addComponent(panel)
/* 159 */         .addComponent(this.txtDescription));
/* 160 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 163 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 166 */     vGroup
/* 167 */       .addGroup(layout.createParallelGroup()
/* 168 */         .addComponent(this.lblImage))
/* 169 */       .addGroup(layout.createParallelGroup()
/* 170 */         .addComponent(panel))
/* 171 */       .addGroup(layout.createParallelGroup()
/* 172 */         .addComponent(this.txtDescription));
/* 173 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 175 */     layout.linkSize(0, new Component[] { panel, this.lblImage });
/* 176 */     layout.linkSize(0, new Component[] { panel, this.txtDescription });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 181 */     int size = 12;
/* 182 */     if (GDStashFrame.iniConfig != null) size = GDStashFrame.iniConfig.sectUI.fontSize; 
/* 183 */     Dimension dimMax = new Dimension(50 * size, 2 * size);
/* 184 */     Dimension dimCBMax = new Dimension(50 * size, 4 * size);
/*     */     
/* 186 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 187 */     Font fntCombo = UIManager.getDefaults().getFont("ComboBox.font");
/* 188 */     if (fntCombo == null) fntCombo = fntLabel; 
/* 189 */     Font fntFText = UIManager.getDefaults().getFont("FormattedTextField.font");
/* 190 */     if (fntFText == null) fntFText = fntLabel;
/*     */     
/* 192 */     fntLabel = fntLabel.deriveFont(size);
/* 193 */     fntCombo = fntCombo.deriveFont(size);
/* 194 */     fntFText = fntFText.deriveFont(size);
/*     */     
/* 196 */     Border compound = null;
/*     */     
/* 198 */     if (this.txtName == null || this.txtDescription == null) {
/*     */       
/* 200 */       Border lowered = BorderFactory.createEtchedBorder(1);
/* 201 */       Border raised = BorderFactory.createEtchedBorder(0);
/* 202 */       compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */     } 
/*     */     
/* 205 */     if (this.lblName == null) this.lblName = new JLabel(); 
/* 206 */     this.lblName.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_NAME"));
/* 207 */     this.lblName.setFont(fntLabel);
/*     */     
/* 209 */     if (this.txtName == null) {
/* 210 */       this.txtName = new JLabel();
/*     */       
/* 212 */       this.txtName.setBorder(compound);
/*     */     } 
/* 214 */     this.txtName.setFont(fntLabel);
/* 215 */     this.txtName.setPreferredSize(dimMax);
/* 216 */     this.txtName.setMaximumSize(dimMax);
/*     */     
/* 218 */     if (this.lblPrefix == null) this.lblPrefix = new JLabel(); 
/* 219 */     this.lblPrefix.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_PREFIX"));
/* 220 */     this.lblPrefix.setFont(fntLabel);
/*     */     
/* 222 */     if (this.cbPrefix == null) {
/* 223 */       this.cbPrefix = (JComboBox<DBAffix>)new WideComboBox();
/* 224 */       this.cbPrefix.setMaximumRowCount(25);
/*     */       
/* 226 */       this.cbPrefix.addActionListener(new ComboBoxActionListener());
/*     */     } 
/* 228 */     this.cbPrefix.setFont(fntCombo);
/* 229 */     this.cbPrefix.setPreferredSize(dimCBMax);
/* 230 */     this.cbPrefix.setMaximumSize(dimCBMax);
/*     */     
/* 232 */     if (this.lblSuffix == null) this.lblSuffix = new JLabel(); 
/* 233 */     this.lblSuffix.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_SUFFIX"));
/* 234 */     this.lblSuffix.setFont(fntLabel);
/*     */     
/* 236 */     if (this.cbSuffix == null) {
/* 237 */       this.cbSuffix = (JComboBox<DBAffix>)new WideComboBox();
/* 238 */       this.cbSuffix.setMaximumRowCount(25);
/*     */       
/* 240 */       this.cbSuffix.addActionListener(new ComboBoxActionListener());
/*     */     } 
/* 242 */     this.cbSuffix.setFont(fntCombo);
/* 243 */     this.cbSuffix.setPreferredSize(dimCBMax);
/* 244 */     this.cbSuffix.setMaximumSize(dimCBMax);
/*     */     
/* 246 */     if (this.lblModifier == null) this.lblModifier = new JLabel(); 
/* 247 */     this.lblModifier.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MODIFIER"));
/* 248 */     this.lblModifier.setFont(fntLabel);
/*     */     
/* 250 */     if (this.cbModifier == null) {
/* 251 */       this.cbModifier = (JComboBox<DBAffix>)new WideComboBox();
/*     */       
/* 253 */       this.cbModifier.addActionListener(new ComboBoxActionListener());
/*     */     } 
/* 255 */     this.cbModifier.setFont(fntCombo);
/*     */     
/* 257 */     if (this.lblCompletion == null) this.lblCompletion = new JLabel(); 
/* 258 */     this.lblCompletion.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_COMPLETION"));
/* 259 */     this.lblCompletion.setFont(fntLabel);
/*     */     
/* 261 */     if (this.cbCompletion == null) {
/* 262 */       this.cbCompletion = (JComboBox<DBAffix>)new WideComboBox();
/*     */       
/* 264 */       this.cbCompletion.addActionListener(new ComboBoxActionListener());
/*     */     } 
/* 266 */     this.cbCompletion.setFont(fntCombo);
/*     */     
/* 268 */     if (this.lblSeed == null) this.lblSeed = new JLabel(); 
/* 269 */     this.lblSeed.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_SEED"));
/* 270 */     this.lblSeed.setFont(fntLabel);
/*     */     
/* 272 */     if (this.ftSeed == null) {
/* 273 */       this.ftSeed = new JFormattedTextField();
/*     */       
/* 275 */       HexLenDocFilter hexLenDocFilter = new HexLenDocFilter(8);
/* 276 */       AbstractDocument doc = (AbstractDocument)this.ftSeed.getDocument();
/* 277 */       doc.setDocumentFilter((DocumentFilter)hexLenDocFilter);
/*     */     } 
/* 279 */     this.ftSeed.setFont(fntFText);
/*     */     
/* 281 */     if (this.lblCount == null) this.lblCount = new JLabel(); 
/* 282 */     this.lblCount.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_COUNT"));
/* 283 */     this.lblCount.setFont(fntLabel);
/*     */     
/* 285 */     if (this.ftCount == null) {
/* 286 */       this.ftCount = new JFormattedTextField();
/*     */       
/* 288 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(4);
/* 289 */       AbstractDocument doc = (AbstractDocument)this.ftCount.getDocument();
/* 290 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */     } 
/* 292 */     this.ftCount.setFont(fntFText);
/*     */     
/* 294 */     if (this.txtDescription == null) {
/* 295 */       this.txtDescription = new JLabel();
/*     */       
/* 297 */       this.txtDescription.setBorder(compound);
/*     */     } 
/* 299 */     this.txtDescription.setFont(fntLabel);
/*     */   }
/*     */   
/*     */   public void refresh() {
/* 303 */     setItem((GDItem)null, 0);
/*     */   }
/*     */   
/*     */   private JPanel buildInfoPanel() {
/* 307 */     GroupLayout layout = null;
/* 308 */     GroupLayout.SequentialGroup hGroup = null;
/* 309 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 311 */     JPanel panel = new JPanel();
/*     */     
/* 313 */     layout = new GroupLayout(panel);
/* 314 */     panel.setLayout(layout);
/*     */     
/* 316 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 319 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 322 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 325 */     hGroup
/* 326 */       .addGroup(layout.createParallelGroup()
/* 327 */         .addComponent(this.lblName)
/* 328 */         .addComponent(this.lblPrefix)
/* 329 */         .addComponent(this.lblSuffix)
/* 330 */         .addComponent(this.lblModifier)
/* 331 */         .addComponent(this.lblCompletion)
/* 332 */         .addComponent(this.lblSeed)
/* 333 */         .addComponent(this.lblCount))
/* 334 */       .addGroup(layout.createParallelGroup()
/* 335 */         .addComponent(this.txtName)
/* 336 */         .addComponent(this.cbPrefix)
/* 337 */         .addComponent(this.cbSuffix)
/* 338 */         .addComponent(this.cbModifier)
/* 339 */         .addComponent(this.cbCompletion)
/* 340 */         .addComponent(this.ftSeed)
/* 341 */         .addComponent(this.ftCount));
/* 342 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 345 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 348 */     vGroup
/* 349 */       .addGroup(layout.createParallelGroup()
/* 350 */         .addComponent(this.lblName)
/* 351 */         .addComponent(this.txtName))
/* 352 */       .addGroup(layout.createParallelGroup()
/* 353 */         .addComponent(this.lblPrefix)
/* 354 */         .addComponent(this.cbPrefix))
/* 355 */       .addGroup(layout.createParallelGroup()
/* 356 */         .addComponent(this.lblSuffix)
/* 357 */         .addComponent(this.cbSuffix))
/* 358 */       .addGroup(layout.createParallelGroup()
/* 359 */         .addComponent(this.lblModifier)
/* 360 */         .addComponent(this.cbModifier))
/* 361 */       .addGroup(layout.createParallelGroup()
/* 362 */         .addComponent(this.lblCompletion)
/* 363 */         .addComponent(this.cbCompletion))
/* 364 */       .addGroup(layout.createParallelGroup()
/* 365 */         .addComponent(this.lblSeed)
/* 366 */         .addComponent(this.ftSeed))
/* 367 */       .addGroup(layout.createParallelGroup()
/* 368 */         .addComponent(this.lblCount)
/* 369 */         .addComponent(this.ftCount));
/* 370 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 372 */     layout.linkSize(0, new Component[] { this.lblName, this.lblPrefix });
/* 373 */     layout.linkSize(0, new Component[] { this.lblName, this.lblSuffix });
/* 374 */     layout.linkSize(0, new Component[] { this.lblName, this.lblModifier });
/* 375 */     layout.linkSize(0, new Component[] { this.lblName, this.lblCompletion });
/* 376 */     layout.linkSize(0, new Component[] { this.lblName, this.lblSeed });
/* 377 */     layout.linkSize(0, new Component[] { this.lblName, this.lblCount });
/*     */     
/* 379 */     layout.linkSize(0, new Component[] { this.txtName, this.cbPrefix });
/* 380 */     layout.linkSize(0, new Component[] { this.txtName, this.cbSuffix });
/* 381 */     layout.linkSize(0, new Component[] { this.txtName, this.cbModifier });
/* 382 */     layout.linkSize(0, new Component[] { this.txtName, this.cbCompletion });
/* 383 */     layout.linkSize(0, new Component[] { this.txtName, this.ftSeed });
/* 384 */     layout.linkSize(0, new Component[] { this.txtName, this.ftCount });
/*     */     
/* 386 */     layout.linkSize(1, new Component[] { this.lblName, this.txtName });
/* 387 */     layout.linkSize(1, new Component[] { this.lblPrefix, this.cbPrefix });
/* 388 */     layout.linkSize(1, new Component[] { this.lblSuffix, this.cbSuffix });
/* 389 */     layout.linkSize(1, new Component[] { this.lblModifier, this.cbModifier });
/* 390 */     layout.linkSize(1, new Component[] { this.lblCompletion, this.cbCompletion });
/* 391 */     layout.linkSize(1, new Component[] { this.lblSeed, this.ftSeed });
/* 392 */     layout.linkSize(1, new Component[] { this.lblCount, this.ftCount });
/*     */     
/* 394 */     return panel;
/*     */   }
/*     */   
/*     */   private void setInfo(GDItem.LabelInfo info, JLabel label) {
/* 398 */     if (info == null) {
/* 399 */       label.setText("");
/*     */     } else {
/* 401 */       label.setForeground(info.foreground);
/* 402 */       label.setText(info.text);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setNameInfo(GDItem.LabelInfo info) {
/* 407 */     setInfo(info, this.txtName);
/*     */   }
/*     */   
/*     */   public void setImage(Icon icon) {
/* 411 */     this.lblImage.setIcon(icon);
/*     */   }
/*     */   
/*     */   public void setItem(GDItem item, int location) {
/* 415 */     if (item == null) {
/* 416 */       this.item = null;
/*     */     } else {
/*     */       
/* 419 */       this.item = item.clone();
/*     */     } 
/*     */     
/* 422 */     initItemInfo(location);
/*     */   }
/*     */   
/*     */   private void initItemInfo(int location) {
/* 426 */     if (this.item == null) {
/* 427 */       GDItem.LabelInfo li = new GDItem.LabelInfo();
/* 428 */       li.text = "";
/*     */       
/* 430 */       setImage((Icon)null);
/* 431 */       setNameInfo(li);
/*     */       
/* 433 */       this.itemLootTables = null;
/* 434 */       this.cbPrefix.removeAllItems();
/* 435 */       this.cbPrefix.setEnabled(false);
/* 436 */       this.cbSuffix.removeAllItems();
/* 437 */       this.cbSuffix.setEnabled(false);
/* 438 */       this.cbModifier.removeAllItems();
/* 439 */       this.cbModifier.setEnabled(false);
/* 440 */       this.cbCompletion.removeAllItems();
/* 441 */       this.cbCompletion.setEnabled(false);
/*     */       
/* 443 */       this.ftSeed.setText(null);
/* 444 */       this.ftCount.setText(null);
/*     */     } else {
/* 446 */       setImage(this.item.getImageIcon());
/* 447 */       setNameInfo(this.item.getItemNameInfo());
/*     */       
/* 449 */       fillCombos(this.item);
/*     */ 
/*     */       
/* 452 */       GDMsgLogger.showLog((Component)this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*     */       
/* 454 */       this.cbPrefix.setModel(this.dmPrefix);
/* 455 */       this.cbSuffix.setModel(this.dmSuffix);
/*     */       
/* 457 */       if (this.item.isCraftable() || GDStashFrame.iniConfig.sectRestrict.blacksmithAll) {
/* 458 */         this.cbModifier.setModel(this.dmModifier);
/*     */       }
/* 460 */       else if (this.item.getRarity().equals("Legendary")) {
/* 461 */         this.cbModifier.setModel(this.dmModifierCelestial);
/*     */       } else {
/* 463 */         this.cbModifier.removeAllItems();
/*     */       } 
/*     */       
/* 466 */       this.cbCompletion.setModel(this.dmCompletion);
/*     */       
/* 468 */       if (location == 1) {
/*     */         
/* 470 */         this.cbPrefix.setEnabled(false);
/* 471 */         this.cbSuffix.setEnabled(false);
/* 472 */         this.cbModifier.setEnabled(false);
/* 473 */         this.cbCompletion.setEnabled(false);
/*     */ 
/*     */         
/* 476 */         this.ftSeed.setText(this.item.getSeedHex());
/* 477 */         this.ftCount.setText(this.item.getStackCountStr());
/*     */         
/* 479 */         this.ftSeed.setEditable(false);
/* 480 */         this.ftCount.setEditable(false);
/*     */       }
/*     */       else {
/*     */         
/* 484 */         this.cbPrefix.setEnabled((this.dmPrefix.getSize() > 1));
/* 485 */         this.cbSuffix.setEnabled((this.dmSuffix.getSize() > 1));
/*     */         
/* 487 */         if (this.item.isCraftable() || GDStashFrame.iniConfig.sectRestrict.blacksmithAll) {
/* 488 */           this.cbModifier.setEnabled((this.dmModifier.getSize() > 1));
/*     */         }
/* 490 */         else if (this.item.getRarity().equals("Legendary")) {
/* 491 */           this.cbModifier.setEnabled((this.dmModifierCelestial.getSize() > 1));
/*     */         } else {
/* 493 */           this.cbModifier.setEnabled(false);
/*     */         } 
/*     */         
/* 496 */         this.cbCompletion.setEnabled((this.dmCompletion.getSize() > 1));
/*     */ 
/*     */         
/* 499 */         this.ftSeed.setText(null);
/* 500 */         this.ftCount.setText(null);
/*     */         
/* 502 */         this.ftSeed.setEditable(true);
/* 503 */         this.ftCount.setEditable(true);
/*     */       } 
/*     */     } 
/*     */     
/* 507 */     updateDescription();
/*     */   }
/*     */   
/*     */   private void updateDescription() {
/* 511 */     String s = null;
/*     */     
/* 513 */     if (this.item == null) {
/* 514 */       s = "";
/*     */     } else {
/* 516 */       s = this.item.getFullDescription();
/*     */     } 
/*     */     
/* 519 */     this.txtDescription.setText(s);
/*     */   }
/*     */   
/*     */   private void fillCombos(GDItem item) {
/* 523 */     this.dmPrefix = new DefaultComboBoxModel<>();
/* 524 */     this.dmSuffix = new DefaultComboBoxModel<>();
/* 525 */     this.dmModifier = new DefaultComboBoxModel<>();
/* 526 */     this.dmModifierCelestial = new DefaultComboBoxModel<>();
/* 527 */     this.dmCompletion = new DefaultComboBoxModel<>();
/*     */     
/* 529 */     this.dmPrefix.addElement(null);
/* 530 */     this.dmSuffix.addElement(null);
/* 531 */     this.dmModifier.addElement(null);
/* 532 */     this.dmModifierCelestial.addElement(null);
/* 533 */     this.dmCompletion.addElement(null);
/*     */     
/* 535 */     this.dmPrefix.setSelectedItem(null);
/* 536 */     this.dmSuffix.setSelectedItem(null);
/* 537 */     this.dmModifier.setSelectedItem(null);
/* 538 */     this.dmModifierCelestial.setSelectedItem(null);
/* 539 */     this.dmCompletion.setSelectedItem(null);
/*     */     
/* 541 */     if (item.getItemID() == null)
/*     */       return; 
/* 543 */     if (item.isArmor() || item
/* 544 */       .isJewelry() || item
/* 545 */       .isWeapon() || item
/* 546 */       .isArtifact()) {
/* 547 */       this.dmModifier = GDStashFrame.getModifier();
/* 548 */       this.dmModifierCelestial = GDStashFrame.getCelestialModifier();
/*     */       
/* 550 */       this.dmModifier.setSelectedItem(null);
/* 551 */       this.dmModifierCelestial.setSelectedItem(null);
/*     */     } 
/*     */     
/* 554 */     List<DBAffix> affixAll = null;
/*     */     
/* 556 */     List<DBAffixSet> affixSets = new LinkedList<>();
/* 557 */     if (item.isArtifact() && 
/* 558 */       item.getBonusAffixSet() != null) {
/* 559 */       affixSets.add(item.getBonusAffixSet());
/*     */     }
/*     */ 
/*     */     
/* 563 */     affixAll = extractAffixes(item, affixSets, -1);
/*     */     
/* 565 */     if (GDStashFrame.iniConfig == null || GDStashFrame.iniConfig.sectRestrict.affixCombi != 3) {
/*     */       
/* 567 */       this.itemLootTables = DBLootTable.getByItemID(item.getItemID());
/*     */       
/* 569 */       for (DBLootTable table : this.itemLootTables) {
/* 570 */         DBLootTable.mixAffixes(item, affixAll, table.getAllAffixes());
/*     */       }
/*     */     } 
/*     */     
/* 574 */     Collections.sort(affixAll, (Comparator<? super DBAffix>)new DBAffix.AffixComparator());
/*     */     
/* 576 */     this.allPrefixes.clear();
/* 577 */     this.allSuffixes.clear();
/* 578 */     this.filterPrefixes.clear();
/* 579 */     this.filterSuffixes.clear();
/*     */     
/* 581 */     for (DBAffix affix : affixAll) {
/* 582 */       if (affix == null)
/*     */         continue; 
/* 584 */       if (affix.getAffixType() == 1) {
/* 585 */         affix.resetDescription();
/* 586 */         this.allPrefixes.add(affix);
/* 587 */         this.filterPrefixes.add(affix);
/*     */       } 
/* 589 */       if (affix.getAffixType() == 2) {
/* 590 */         this.allSuffixes.add(affix);
/* 591 */         this.filterSuffixes.add(affix);
/*     */       } 
/*     */       
/* 594 */       if (affix.getAffixType() != 4 || 
/* 595 */         GDStashFrame.iniConfig.sectRestrict.completionAll) {
/*     */         continue;
/*     */       }
/*     */       
/* 599 */       if (item.isArtifact()) {
/* 600 */         this.dmCompletion.addElement(affix);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 605 */     if (item.isArtifact()) {
/* 606 */       if (GDStashFrame.iniConfig.sectRestrict.completionAll) {
/* 607 */         this.dmCompletion = GDStashFrame.getCompletion();
/*     */       }
/*     */ 
/*     */       
/* 611 */       if (!item.getBonusAffixList().isEmpty()) {
/* 612 */         for (DBAffix affix : item.getBonusAffixList()) {
/* 613 */           boolean found = false; int i;
/* 614 */           for (i = 0; i < this.dmCompletion.getSize(); i++) {
/* 615 */             DBAffix dba = this.dmCompletion.getElementAt(i);
/*     */             
/* 617 */             if (affix.equals(dba)) {
/* 618 */               found = true;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */           
/* 624 */           if (!found) this.dmCompletion.addElement(affix);
/*     */         
/*     */         } 
/*     */       }
/*     */     } 
/* 629 */     fillPrefixCombo();
/* 630 */     fillSuffixCombo();
/*     */     
/* 632 */     if (item.getPrefix() != null) this.dmPrefix.setSelectedItem(item.getPrefix()); 
/* 633 */     if (item.getSuffix() != null) this.dmSuffix.setSelectedItem(item.getSuffix()); 
/* 634 */     if (item.getModifier() != null) this.dmModifier.setSelectedItem(item.getModifier()); 
/* 635 */     if (item.getCompletionBonus() != null) this.dmCompletion.setSelectedItem(item.getCompletionBonus()); 
/*     */   }
/*     */   
/*     */   private void fillPrefixCombo() {
/* 639 */     List<DBAffix> currPrefixes = null;
/*     */     
/* 641 */     if (GDStashFrame.iniConfig != null && (GDStashFrame.iniConfig.sectRestrict.affixCombi == 3 || GDStashFrame.iniConfig.sectRestrict.affixCombi == 2)) {
/*     */ 
/*     */       
/* 644 */       currPrefixes = this.filterPrefixes;
/*     */     } else {
/* 646 */       currPrefixes = new LinkedList<>();
/*     */       
/* 648 */       for (DBLootTable table : this.itemLootTables) {
/* 649 */         DBLootTable.mixAffixes(this.item, currPrefixes, table.getFilterPrefixesForSuffix(this.item.getSuffix(), this.filterPrefixes));
/*     */       }
/*     */     } 
/*     */     
/* 653 */     Collections.sort(currPrefixes, (Comparator<? super DBAffix>)new DBAffix.AffixComparator());
/*     */ 
/*     */     
/* 656 */     this.skipCombo = true;
/*     */     
/* 658 */     this.dmPrefix.setSelectedItem(null);
/* 659 */     this.dmPrefix.removeAllElements();
/* 660 */     this.dmPrefix.addElement(null);
/*     */     
/* 662 */     boolean found = false;
/* 663 */     DBAffix prefix = this.item.getPrefix();
/*     */     
/* 665 */     for (DBAffix affix : currPrefixes) {
/* 666 */       this.dmPrefix.addElement(affix);
/*     */       
/* 668 */       if (prefix != null && 
/* 669 */         prefix.getAffixID().equals(affix.getAffixID())) {
/* 670 */         found = true;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 676 */     if (found) {
/* 677 */       this.dmPrefix.setSelectedItem(this.item.getPrefix());
/*     */       
/* 679 */       this.skipCombo = false;
/*     */     } else {
/* 681 */       this.skipCombo = false;
/*     */       
/* 683 */       this.item.setPrefix(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fillSuffixCombo() {
/* 688 */     List<DBAffix> currSuffixes = null;
/*     */     
/* 690 */     if (GDStashFrame.iniConfig != null && (GDStashFrame.iniConfig.sectRestrict.affixCombi == 3 || GDStashFrame.iniConfig.sectRestrict.affixCombi == 2)) {
/*     */ 
/*     */       
/* 693 */       currSuffixes = this.filterSuffixes;
/*     */     } else {
/* 695 */       currSuffixes = new LinkedList<>();
/*     */       
/* 697 */       for (DBLootTable table : this.itemLootTables) {
/* 698 */         DBLootTable.mixAffixes(this.item, currSuffixes, table.getFilterSuffixesForPrefix(this.item.getPrefix(), this.filterSuffixes));
/*     */       }
/*     */     } 
/*     */     
/* 702 */     Collections.sort(currSuffixes, (Comparator<? super DBAffix>)new DBAffix.AffixComparator());
/*     */ 
/*     */     
/* 705 */     this.skipCombo = true;
/*     */     
/* 707 */     this.dmSuffix.setSelectedItem(null);
/* 708 */     this.dmSuffix.removeAllElements();
/* 709 */     this.dmSuffix.addElement(null);
/*     */     
/* 711 */     boolean found = false;
/* 712 */     DBAffix suffix = this.item.getSuffix();
/*     */     
/* 714 */     for (DBAffix affix : currSuffixes) {
/* 715 */       this.dmSuffix.addElement(affix);
/*     */       
/* 717 */       if (suffix != null && 
/* 718 */         suffix.getAffixID().equals(affix.getAffixID())) {
/* 719 */         found = true;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 725 */     if (found) {
/* 726 */       this.dmSuffix.setSelectedItem(this.item.getSuffix());
/*     */       
/* 728 */       this.skipCombo = false;
/*     */     } else {
/* 730 */       this.skipCombo = false;
/*     */       
/* 732 */       this.item.setSuffix(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBLootTableSet> extractTableSets(List<DBLootTable> tables) {
/* 737 */     List<DBLootTableSet> setAll = new LinkedList<>();
/*     */     
/* 739 */     if (tables == null) return setAll;
/*     */     
/* 741 */     for (DBLootTable table : tables) {
/* 742 */       if (table.getAffixSetAllocList() == null)
/*     */         continue; 
/* 744 */       List<DBLootTableSet> sets = DBLootTableSet.getByTableID(table.getTableID());
/*     */       
/* 746 */       if (sets == null)
/*     */         continue; 
/* 748 */       for (DBLootTableSet set : sets) {
/* 749 */         if (set == null)
/*     */           continue; 
/* 751 */         boolean found = false;
/*     */         
/* 753 */         for (DBLootTableSet ts : setAll) {
/* 754 */           if (ts.getTableSetID().equals(set.getTableSetID())) {
/* 755 */             found = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */         
/* 761 */         if (!found) setAll.add(set);
/*     */       
/*     */       } 
/*     */     } 
/* 765 */     return setAll;
/*     */   }
/*     */   
/*     */   public static List<DBAffixSet> extractAffixSets(List<DBLootTable> tables) {
/* 769 */     List<DBAffixSet> setAll = new LinkedList<>();
/*     */     
/* 771 */     if (tables == null) return setAll;
/*     */     
/* 773 */     for (DBLootTable table : tables) {
/* 774 */       if (table.getAffixSetAllocList() == null)
/*     */         continue; 
/* 776 */       List<DBAffixSet> sets = DBAffixSet.getByAffixSetIDs(table.getAffixSetIDs());
/*     */       
/* 778 */       if (sets == null)
/*     */         continue; 
/* 780 */       for (DBAffixSet set : sets) {
/* 781 */         if (set == null)
/*     */           continue; 
/* 783 */         boolean found = false;
/*     */         
/* 785 */         for (DBAffixSet as : setAll) {
/* 786 */           if (as.getAffixSetID().equals(set.getAffixSetID())) {
/* 787 */             found = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */         
/* 793 */         if (!found) setAll.add(set);
/*     */       
/*     */       } 
/*     */     } 
/* 797 */     return setAll;
/*     */   }
/*     */   
/*     */   public static List<DBAffix> extractAffixes(GDItem item, List<DBAffixSet> sets, int affixType) {
/* 801 */     List<DBAffix> affixAll = new LinkedList<>();
/*     */ 
/*     */ 
/*     */     
/* 805 */     if (GDStashFrame.iniConfig != null && 
/* 806 */       GDStashFrame.iniConfig.sectRestrict.affixCombi == 3) {
/* 807 */       affixAll = DBAffix.getFullAffixList();
/*     */     }
/*     */ 
/*     */     
/* 811 */     if (sets == null) return affixAll;
/*     */     
/* 813 */     for (DBAffixSet set : sets) {
/* 814 */       if (set == null || 
/* 815 */         set.getAffixEntries() == null)
/*     */         continue; 
/* 817 */       for (DBAffixSet.DBEntry entry : set.getAffixEntries()) {
/* 818 */         if (entry == null)
/*     */           continue; 
/* 820 */         boolean found = false;
/*     */         
/* 822 */         if (affixType == 4) {
/* 823 */           for (DBAffix affix : affixAll) {
/* 824 */             if (affix.getAffixID().equals(entry.getAffixID())) {
/* 825 */               found = true;
/*     */               
/* 827 */               affix.setAffixType(4);
/*     */ 
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 835 */         if (!found) {
/* 836 */           for (DBAffix affix : affixAll) {
/* 837 */             if (affix.getAffixID().equals(entry.getAffixID())) {
/* 838 */               found = true;
/*     */               
/* 840 */               if (affixType != -1) affix.setAffixType(affixType);
/*     */ 
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/* 847 */         if (!found) {
/* 848 */           DBAffix affix = DBAffix.get(entry.getAffixID());
/*     */           
/* 850 */           if (affix == null) {
/*     */             continue;
/*     */           }
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
/* 864 */           if (affixType != -1) affix.setAffixType(affixType);
/*     */           
/* 866 */           affixAll.add(affix);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 871 */     return affixAll;
/*     */   }
/*     */   
/*     */   public List<DBAffix> getPrefixes() {
/* 875 */     return this.allPrefixes;
/*     */   }
/*     */   
/*     */   public void setFilteredPrefixes(List<DBAffix> prefixes) {
/* 879 */     this.filterPrefixes = prefixes;
/*     */ 
/*     */     
/* 882 */     this.skipCombo = true;
/*     */     
/* 884 */     DBAffix prefix = (DBAffix)this.dmPrefix.getSelectedItem();
/* 885 */     boolean found = false;
/*     */     
/* 887 */     this.dmPrefix.setSelectedItem(null);
/* 888 */     this.dmPrefix.removeAllElements();
/* 889 */     this.dmPrefix.addElement(null);
/*     */     
/* 891 */     for (DBAffix affix : this.filterPrefixes) {
/* 892 */       this.dmPrefix.addElement(affix);
/*     */       
/* 894 */       if (prefix != null && 
/* 895 */         prefix.getAffixID().equals(affix.getAffixID())) {
/* 896 */         found = true;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 902 */     if (found) {
/* 903 */       this.dmPrefix.setSelectedItem(prefix);
/*     */       
/* 905 */       this.skipCombo = false;
/*     */     } else {
/* 907 */       if (prefix != null) this.dmPrefix.addElement(prefix); 
/* 908 */       this.dmPrefix.setSelectedItem(prefix);
/*     */       
/* 910 */       this.skipCombo = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<DBAffix> getSuffixes() {
/* 915 */     return this.allSuffixes;
/*     */   }
/*     */   
/*     */   public void setFilteredSuffixes(List<DBAffix> suffixes) {
/* 919 */     this.filterSuffixes = suffixes;
/*     */ 
/*     */     
/* 922 */     this.skipCombo = true;
/*     */     
/* 924 */     DBAffix suffix = (DBAffix)this.dmSuffix.getSelectedItem();
/* 925 */     boolean found = false;
/*     */     
/* 927 */     this.dmSuffix.setSelectedItem(null);
/* 928 */     this.dmSuffix.removeAllElements();
/* 929 */     this.dmSuffix.addElement(null);
/*     */     
/* 931 */     for (DBAffix affix : this.filterSuffixes) {
/* 932 */       this.dmSuffix.addElement(affix);
/*     */       
/* 934 */       if (suffix != null && 
/* 935 */         suffix.getAffixID().equals(affix.getAffixID())) {
/* 936 */         found = true;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 942 */     if (found) {
/* 943 */       this.dmSuffix.setSelectedItem(suffix);
/*     */       
/* 945 */       this.skipCombo = false;
/*     */     } else {
/* 947 */       if (suffix != null) this.dmSuffix.addElement(suffix); 
/* 948 */       this.dmSuffix.setSelectedItem(suffix);
/*     */       
/* 950 */       this.skipCombo = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public GDItem getItem() {
/* 955 */     return this.item;
/*     */   }
/*     */   
/*     */   public String getSeed() {
/* 959 */     String s = this.ftSeed.getText();
/*     */     
/* 961 */     if (s.length() != 8) {
/* 962 */       s = null;
/*     */     } else {
/* 964 */       s = s.toUpperCase(GDConstants.LOCALE_US);
/*     */     } 
/*     */     
/* 967 */     return s;
/*     */   }
/*     */   
/*     */   public int getCount() {
/* 971 */     String s = this.ftCount.getText();
/*     */     
/* 973 */     if (s == null) return 0; 
/* 974 */     if (s.isEmpty()) return 0;
/*     */     
/* 976 */     int i = 0;
/*     */     try {
/* 978 */       i = Integer.parseInt(s);
/*     */     }
/* 980 */     catch (NumberFormatException ex) {
/* 981 */       i = 0;
/*     */     } 
/*     */     
/* 984 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\GDItemCraftPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */