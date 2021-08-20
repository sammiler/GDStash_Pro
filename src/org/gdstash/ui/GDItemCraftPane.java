/*     */ package org.gdstash.ui;
/*     */ import java.awt.*;
/*     */
/*     */
/*     */ import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.*;
/*     */
/*     */
/*     */
/*     */
/*     */ import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
/*     */ import org.gdstash.db.DBAffix;
/*     */ import org.gdstash.db.DBAffixSet;
/*     */ import org.gdstash.db.DBLootTable;
/*     */ import org.gdstash.db.DBLootTableSet;
/*     */ import org.gdstash.item.GDItem;
/*     */ import org.gdstash.ui.select.HexLenDocFilter;
import org.gdstash.ui.select.IntLenDocFilter;
import org.gdstash.ui.util.AdjustablePanel;
import org.gdstash.ui.util.WideComboBox;
import org.gdstash.util.GDConstants;
import org.gdstash.util.GDMsgFormatter;
import org.gdstash.util.GDMsgLogger;

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
/* 556 */     if (GDStashFrame.iniConfig != null && GDStashFrame.iniConfig.sectRestrict.affixCombi == 3) {
/*     */       
/* 558 */       affixAll = extractAffixes(item, (List<DBAffixSet>)null);
/*     */     } else {
/* 560 */       this.itemLootTables = DBLootTable.getByItemID(item.getItemID());
/* 561 */       List<DBAffixSet> affixSets = new LinkedList<>();
/*     */       
/* 563 */       if (!GDStashFrame.iniConfig.sectRestrict.completionAll && 
/* 564 */         item.isArtifact() && 
/* 565 */         item.getBonusAffixSet() != null) {
/* 566 */         affixSets.add(item.getBonusAffixSet());
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 571 */       affixAll = extractAffixes(item, affixSets);
/*     */       
/* 573 */       for (DBLootTable table : this.itemLootTables) {
/* 574 */         DBLootTable.mixAffixes(item, affixAll, table.getAllAffixes());
/*     */       }
/*     */     } 
/*     */     
/* 578 */     Collections.sort(affixAll, (Comparator<? super DBAffix>)new DBAffix.AffixComparator());
/*     */     
/* 580 */     this.allPrefixes.clear();
/* 581 */     this.allSuffixes.clear();
/* 582 */     this.filterPrefixes.clear();
/* 583 */     this.filterSuffixes.clear();
/*     */     
/* 585 */     for (DBAffix affix : affixAll) {
/* 586 */       if (affix == null)
/*     */         continue; 
/* 588 */       if (affix.getAffixType() == 1) {
/* 589 */         affix.resetDescription();
/* 590 */         this.allPrefixes.add(affix);
/* 591 */         this.filterPrefixes.add(affix);
/*     */       } 
/* 593 */       if (affix.getAffixType() == 2) {
/* 594 */         this.allSuffixes.add(affix);
/* 595 */         this.filterSuffixes.add(affix);
/*     */       } 
/*     */       
/* 598 */       if (affix.getAffixType() != 4 || 
/* 599 */         GDStashFrame.iniConfig.sectRestrict.completionAll) {
/*     */         continue;
/*     */       }
/*     */       
/* 603 */       if (item.isArtifact()) {
/* 604 */         this.dmCompletion.addElement(affix);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 609 */     if (item.isArtifact() && 
/* 610 */       GDStashFrame.iniConfig.sectRestrict.completionAll) {
/* 611 */       this.dmCompletion = GDStashFrame.getCompletion();
/*     */     }
/*     */     
/* 614 */     fillPrefixCombo();
/* 615 */     fillSuffixCombo();
/*     */     
/* 617 */     if (item.getPrefix() != null) this.dmPrefix.setSelectedItem(item.getPrefix()); 
/* 618 */     if (item.getSuffix() != null) this.dmSuffix.setSelectedItem(item.getSuffix()); 
/* 619 */     if (item.getModifier() != null) this.dmModifier.setSelectedItem(item.getModifier()); 
/* 620 */     if (item.getCompletionBonus() != null) this.dmCompletion.setSelectedItem(item.getCompletionBonus()); 
/*     */   }
/*     */   
/*     */   private void fillPrefixCombo() {
/* 624 */     List<DBAffix> currPrefixes = null;
/*     */     
/* 626 */     if (GDStashFrame.iniConfig != null && (GDStashFrame.iniConfig.sectRestrict.affixCombi == 3 || GDStashFrame.iniConfig.sectRestrict.affixCombi == 2)) {
/*     */ 
/*     */       
/* 629 */       currPrefixes = this.filterPrefixes;
/*     */     } else {
/* 631 */       currPrefixes = new LinkedList<>();
/*     */       
/* 633 */       for (DBLootTable table : this.itemLootTables) {
/* 634 */         DBLootTable.mixAffixes(this.item, currPrefixes, table.getFilterPrefixesForSuffix(this.item.getSuffix(), this.filterPrefixes));
/*     */       }
/*     */     } 
/*     */     
/* 638 */     Collections.sort(currPrefixes, (Comparator<? super DBAffix>)new DBAffix.AffixComparator());
/*     */ 
/*     */     
/* 641 */     this.skipCombo = true;
/*     */     
/* 643 */     this.dmPrefix.setSelectedItem(null);
/* 644 */     this.dmPrefix.removeAllElements();
/* 645 */     this.dmPrefix.addElement(null);
/*     */     
/* 647 */     boolean found = false;
/* 648 */     DBAffix prefix = this.item.getPrefix();
/*     */     
/* 650 */     for (DBAffix affix : currPrefixes) {
/* 651 */       this.dmPrefix.addElement(affix);
/*     */       
/* 653 */       if (prefix != null && 
/* 654 */         prefix.getAffixID().equals(affix.getAffixID())) {
/* 655 */         found = true;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 661 */     if (found) {
/* 662 */       this.dmPrefix.setSelectedItem(this.item.getPrefix());
/*     */       
/* 664 */       this.skipCombo = false;
/*     */     } else {
/* 666 */       this.skipCombo = false;
/*     */       
/* 668 */       this.item.setPrefix(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fillSuffixCombo() {
/* 673 */     List<DBAffix> currSuffixes = null;
/*     */     
/* 675 */     if (GDStashFrame.iniConfig != null && (GDStashFrame.iniConfig.sectRestrict.affixCombi == 3 || GDStashFrame.iniConfig.sectRestrict.affixCombi == 2)) {
/*     */ 
/*     */       
/* 678 */       currSuffixes = this.filterSuffixes;
/*     */     } else {
/* 680 */       currSuffixes = new LinkedList<>();
/*     */       
/* 682 */       for (DBLootTable table : this.itemLootTables) {
/* 683 */         DBLootTable.mixAffixes(this.item, currSuffixes, table.getFilterSuffixesForPrefix(this.item.getPrefix(), this.filterSuffixes));
/*     */       }
/*     */     } 
/*     */     
/* 687 */     Collections.sort(currSuffixes, (Comparator<? super DBAffix>)new DBAffix.AffixComparator());
/*     */ 
/*     */     
/* 690 */     this.skipCombo = true;
/*     */     
/* 692 */     this.dmSuffix.setSelectedItem(null);
/* 693 */     this.dmSuffix.removeAllElements();
/* 694 */     this.dmSuffix.addElement(null);
/*     */     
/* 696 */     boolean found = false;
/* 697 */     DBAffix suffix = this.item.getSuffix();
/*     */     
/* 699 */     for (DBAffix affix : currSuffixes) {
/* 700 */       this.dmSuffix.addElement(affix);
/*     */       
/* 702 */       if (suffix != null && 
/* 703 */         suffix.getAffixID().equals(affix.getAffixID())) {
/* 704 */         found = true;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 710 */     if (found) {
/* 711 */       this.dmSuffix.setSelectedItem(this.item.getSuffix());
/*     */       
/* 713 */       this.skipCombo = false;
/*     */     } else {
/* 715 */       this.skipCombo = false;
/*     */       
/* 717 */       this.item.setSuffix(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBLootTableSet> extractTableSets(List<DBLootTable> tables) {
/* 722 */     List<DBLootTableSet> setAll = new LinkedList<>();
/*     */     
/* 724 */     if (tables == null) return setAll;
/*     */     
/* 726 */     for (DBLootTable table : tables) {
/* 727 */       if (table.getAffixSetAllocList() == null)
/*     */         continue; 
/* 729 */       List<DBLootTableSet> sets = DBLootTableSet.getByTableID(table.getTableID());
/*     */       
/* 731 */       if (sets == null)
/*     */         continue; 
/* 733 */       for (DBLootTableSet set : sets) {
/* 734 */         if (set == null)
/*     */           continue; 
/* 736 */         boolean found = false;
/*     */         
/* 738 */         for (DBLootTableSet ts : setAll) {
/* 739 */           if (ts.getTableSetID().equals(set.getTableSetID())) {
/* 740 */             found = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */         
/* 746 */         if (!found) setAll.add(set);
/*     */       
/*     */       } 
/*     */     } 
/* 750 */     return setAll;
/*     */   }
/*     */   
/*     */   public static List<DBAffixSet> extractAffixSets(List<DBLootTable> tables) {
/* 754 */     List<DBAffixSet> setAll = new LinkedList<>();
/*     */     
/* 756 */     if (tables == null) return setAll;
/*     */     
/* 758 */     for (DBLootTable table : tables) {
/* 759 */       if (table.getAffixSetAllocList() == null)
/*     */         continue; 
/* 761 */       List<DBAffixSet> sets = DBAffixSet.getByAffixSetIDs(table.getAffixSetIDs());
/*     */       
/* 763 */       if (sets == null)
/*     */         continue; 
/* 765 */       for (DBAffixSet set : sets) {
/* 766 */         if (set == null)
/*     */           continue; 
/* 768 */         boolean found = false;
/*     */         
/* 770 */         for (DBAffixSet as : setAll) {
/* 771 */           if (as.getAffixSetID().equals(set.getAffixSetID())) {
/* 772 */             found = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */         
/* 778 */         if (!found) setAll.add(set);
/*     */       
/*     */       } 
/*     */     } 
/* 782 */     return setAll;
/*     */   }
/*     */   
/*     */   public static List<DBAffix> extractAffixes(GDItem item, List<DBAffixSet> sets) {
/* 786 */     List<DBAffix> affixAll = new LinkedList<>();
/*     */ 
/*     */ 
/*     */     
/* 790 */     if (GDStashFrame.iniConfig != null && 
/* 791 */       GDStashFrame.iniConfig.sectRestrict.affixCombi == 3) {
/* 792 */       affixAll = DBAffix.getFullAffixList();
/*     */       
/* 794 */       return affixAll;
/*     */     } 
/*     */ 
/*     */     
/* 798 */     if (sets == null) return affixAll;
/*     */     
/* 800 */     for (DBAffixSet set : sets) {
/* 801 */       if (set == null || 
/* 802 */         set.getAffixEntries() == null)
/*     */         continue; 
/* 804 */       for (DBAffixSet.DBEntry entry : set.getAffixEntries()) {
/* 805 */         if (entry == null)
/*     */           continue; 
/* 807 */         boolean found = false;
/*     */         
/* 809 */         for (DBAffix affix : affixAll) {
/* 810 */           if (affix.getAffixID().equals(entry.getAffixID())) {
/* 811 */             found = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */         
/* 817 */         if (!found) {
/* 818 */           DBAffix affix = DBAffix.get(entry.getAffixID());
/*     */           
/* 820 */           if (affix == null) {
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
/* 834 */           affixAll.add(affix);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 839 */     return affixAll;
/*     */   }
/*     */   
/*     */   public List<DBAffix> getPrefixes() {
/* 843 */     return this.allPrefixes;
/*     */   }
/*     */   
/*     */   public void setFilteredPrefixes(List<DBAffix> prefixes) {
/* 847 */     this.filterPrefixes = prefixes;
/*     */ 
/*     */     
/* 850 */     this.skipCombo = true;
/*     */     
/* 852 */     DBAffix prefix = (DBAffix)this.dmPrefix.getSelectedItem();
/* 853 */     boolean found = false;
/*     */     
/* 855 */     this.dmPrefix.setSelectedItem(null);
/* 856 */     this.dmPrefix.removeAllElements();
/* 857 */     this.dmPrefix.addElement(null);
/*     */     
/* 859 */     for (DBAffix affix : this.filterPrefixes) {
/* 860 */       this.dmPrefix.addElement(affix);
/*     */       
/* 862 */       if (prefix != null && 
/* 863 */         prefix.getAffixID().equals(affix.getAffixID())) {
/* 864 */         found = true;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 870 */     if (found) {
/* 871 */       this.dmPrefix.setSelectedItem(prefix);
/*     */       
/* 873 */       this.skipCombo = false;
/*     */     } else {
/* 875 */       if (prefix != null) this.dmPrefix.addElement(prefix); 
/* 876 */       this.dmPrefix.setSelectedItem(prefix);
/*     */       
/* 878 */       this.skipCombo = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<DBAffix> getSuffixes() {
/* 883 */     return this.allSuffixes;
/*     */   }
/*     */   
/*     */   public void setFilteredSuffixes(List<DBAffix> suffixes) {
/* 887 */     this.filterSuffixes = suffixes;
/*     */ 
/*     */     
/* 890 */     this.skipCombo = true;
/*     */     
/* 892 */     DBAffix suffix = (DBAffix)this.dmSuffix.getSelectedItem();
/* 893 */     boolean found = false;
/*     */     
/* 895 */     this.dmSuffix.setSelectedItem(null);
/* 896 */     this.dmSuffix.removeAllElements();
/* 897 */     this.dmSuffix.addElement(null);
/*     */     
/* 899 */     for (DBAffix affix : this.filterSuffixes) {
/* 900 */       this.dmSuffix.addElement(affix);
/*     */       
/* 902 */       if (suffix != null && 
/* 903 */         suffix.getAffixID().equals(affix.getAffixID())) {
/* 904 */         found = true;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 910 */     if (found) {
/* 911 */       this.dmSuffix.setSelectedItem(suffix);
/*     */       
/* 913 */       this.skipCombo = false;
/*     */     } else {
/* 915 */       if (suffix != null) this.dmSuffix.addElement(suffix); 
/* 916 */       this.dmSuffix.setSelectedItem(suffix);
/*     */       
/* 918 */       this.skipCombo = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public GDItem getItem() {
/* 923 */     return this.item;
/*     */   }
/*     */   
/*     */   public String getSeed() {
/* 927 */     String s = this.ftSeed.getText();
/*     */     
/* 929 */     if (s.length() != 8) {
/* 930 */       s = null;
/*     */     } else {
/* 932 */       s = s.toUpperCase(GDConstants.LOCALE_US);
/*     */     } 
/*     */     
/* 935 */     return s;
/*     */   }
/*     */   
/*     */   public int getCount() {
/* 939 */     String s = this.ftCount.getText();
/*     */     
/* 941 */     if (s == null) return 0; 
/* 942 */     if (s.isEmpty()) return 0;
/*     */     
/* 944 */     int i = 0;
/*     */     try {
/* 946 */       i = Integer.parseInt(s);
/*     */     }
/* 948 */     catch (NumberFormatException ex) {
/* 949 */       i = 0;
/*     */     } 
/*     */     
/* 952 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\GDItemCraftPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */