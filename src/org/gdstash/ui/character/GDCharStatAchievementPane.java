/*     */ package org.gdstash.ui.character;
/*     */ import java.awt.*;
/*     */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*     */ import javax.swing.*;
/*     */
/*     */
/*     */
/*     */
/*     */ import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.ui.GDStashFrame;
import org.gdstash.ui.select.IntLenDocFilter;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
import org.gdstash.util.GDImagePool;
import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDCharStatAchievementPane extends AdjustablePanel {
/*     */   private JLabel lblGreatestDamage;
/*     */   private JLabel lblMonstersKilled;
/*     */   private JLabel lblHeroesKilled;
/*     */   private JLabel lblNemesisKilled;
/*     */   private JLabel lblNotesCollected;
/*     */   private JLabel lblOneshotsOpened;
/*     */   private JLabel lblPlayTime;
/*     */   private JLabel lblItemsCrafted;
/*     */   private JLabel lblRelicsCrafted;
/*     */   private JLabel lblRelicsTransCrafted;
/*     */   private JLabel lblRelicsMythicalCrafted;
/*     */   private JTextField ftGreatestDamage;
/*     */   private JTextField ftMonstersKilled;
/*     */   private JTextField ftHeroesKilled;
/*     */   private JTextField ftNemesisKilled;
/*     */   private JTextField ftNotesCollected;
/*     */   private JTextField ftOneshotsOpened;
/*     */   private JTextField ftPlayTime;
/*     */   private JTextField ftItemsCrafted;
/*     */   private JTextField ftRelicsCrafted;
/*     */   private JTextField ftRelicsTransCrafted;
/*     */   private JTextField ftRelicsMythicalCrafted;
/*     */   private JButton btnGreatestDamageReset;
/*     */   private GDChar gdc;
/*     */   
/*     */   private class ResetGreatestDamageListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent ev) {
/*  42 */       if (GDCharStatAchievementPane.this.gdc == null)
/*     */         return; 
/*  44 */       GDCharStatAchievementPane.this.ftGreatestDamage.setText("0");
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
/*     */     private int index;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private ResetGreatestDamageListener() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GDCharStatAchievementPane() {
/*  76 */     adjustUI();
/*     */     
/*  78 */     JPanel pnlLeft = buildLeftSidePanel();
/*  79 */     JPanel pnlRight = buildRightSidePanel();
/*     */     
/*  81 */     GroupLayout layout = null;
/*  82 */     GroupLayout.SequentialGroup hGroup = null;
/*  83 */     GroupLayout.SequentialGroup vGroup = null;
/*     */ 
/*     */ 
/*     */     
/*  87 */     layout = new GroupLayout((Container)this);
/*  88 */     setLayout(layout);
/*     */     
/*  90 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  93 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  96 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  99 */     hGroup
/* 100 */       .addGroup(layout.createParallelGroup()
/* 101 */         .addComponent(pnlLeft))
/* 102 */       .addGroup(layout.createParallelGroup()
/* 103 */         .addComponent(pnlRight));
/* 104 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 107 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 110 */     vGroup
/* 111 */       .addGroup(layout.createParallelGroup()
/* 112 */         .addComponent(pnlLeft)
/* 113 */         .addComponent(pnlRight));
/* 114 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 116 */     layout.linkSize(0, new Component[] { pnlLeft, pnlRight });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 123 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 124 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/* 125 */     if (fntButton == null) fntButton = fntLabel; 
/* 126 */     Font fntFText = UIManager.getDefaults().getFont("FormattedTextField.font");
/* 127 */     if (fntFText == null) fntFText = fntLabel; 
/* 128 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 129 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 131 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 132 */     fntFText = fntFText.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 133 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 135 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 136 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 137 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 138 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_ACHIEVEMENTS"));
/* 139 */     text.setTitleFont(fntBorder);
/*     */     
/* 141 */     setBorder(text);
/*     */ 
/*     */     
/* 144 */     if (this.lblGreatestDamage == null) this.lblGreatestDamage = new JLabel(); 
/* 145 */     this.lblGreatestDamage.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_GREATEST_DAMAGE"));
/* 146 */     this.lblGreatestDamage.setFont(fntLabel);
/*     */     
/* 148 */     if (this.ftGreatestDamage == null) {
/* 149 */       this.ftGreatestDamage = new JFormattedTextField();
/* 150 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(7);
/* 151 */       AbstractDocument doc = (AbstractDocument)this.ftGreatestDamage.getDocument();
/* 152 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/* 153 */       this.ftGreatestDamage.setEditable(false);
/*     */     } 
/* 155 */     this.ftGreatestDamage.setPreferredSize(new Dimension(GDStashFrame.iniConfig.sectUI.fontSize * 7, GDStashFrame.iniConfig.sectUI.fontSize * 2));
/* 156 */     this.ftGreatestDamage.setFont(fntFText);
/*     */     
/* 158 */     if (this.btnGreatestDamageReset == null) {
/* 159 */       this.btnGreatestDamageReset = new JButton();
/*     */       
/* 161 */       this.btnGreatestDamageReset.addActionListener(new ResetGreatestDamageListener());
/*     */     } 
/* 163 */     this.btnGreatestDamageReset.setIcon(GDImagePool.iconCharEditMasteryReset16);
/* 164 */     this.btnGreatestDamageReset.setFont(fntButton);
/*     */     
/* 166 */     if (this.lblMonstersKilled == null) this.lblMonstersKilled = new JLabel(); 
/* 167 */     this.lblMonstersKilled.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_KILLED_MONSTER"));
/* 168 */     this.lblMonstersKilled.setFont(fntLabel);
/*     */     
/* 170 */     if (this.ftMonstersKilled == null) {
/* 171 */       this.ftMonstersKilled = new JFormattedTextField();
/* 172 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(7);
/* 173 */       AbstractDocument doc = (AbstractDocument)this.ftMonstersKilled.getDocument();
/* 174 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/* 175 */       this.ftMonstersKilled.setEditable(false);
/*     */     } 
/* 177 */     this.ftMonstersKilled.setFont(fntFText);
/*     */     
/* 179 */     if (this.lblHeroesKilled == null) this.lblHeroesKilled = new JLabel(); 
/* 180 */     this.lblHeroesKilled.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_KILLED_HERO"));
/* 181 */     this.lblHeroesKilled.setFont(fntLabel);
/*     */     
/* 183 */     if (this.ftHeroesKilled == null) {
/* 184 */       this.ftHeroesKilled = new JFormattedTextField();
/* 185 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(7);
/* 186 */       AbstractDocument doc = (AbstractDocument)this.ftHeroesKilled.getDocument();
/* 187 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/* 188 */       this.ftHeroesKilled.setEditable(false);
/*     */     } 
/* 190 */     this.ftHeroesKilled.setFont(fntFText);
/*     */     
/* 192 */     if (this.lblNemesisKilled == null) this.lblNemesisKilled = new JLabel(); 
/* 193 */     this.lblNemesisKilled.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_KILLED_NEMESIS"));
/* 194 */     this.lblNemesisKilled.setFont(fntLabel);
/*     */     
/* 196 */     if (this.ftNemesisKilled == null) {
/* 197 */       this.ftNemesisKilled = new JFormattedTextField();
/* 198 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(7);
/* 199 */       AbstractDocument doc = (AbstractDocument)this.ftNemesisKilled.getDocument();
/* 200 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/* 201 */       this.ftNemesisKilled.setEditable(false);
/*     */     } 
/* 203 */     this.ftNemesisKilled.setFont(fntFText);
/*     */     
/* 205 */     if (this.lblNotesCollected == null) this.lblNotesCollected = new JLabel(); 
/* 206 */     this.lblNotesCollected.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_FOUND_NOTES"));
/* 207 */     this.lblNotesCollected.setFont(fntLabel);
/*     */     
/* 209 */     if (this.ftNotesCollected == null) {
/* 210 */       this.ftNotesCollected = new JFormattedTextField();
/* 211 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(7);
/* 212 */       AbstractDocument doc = (AbstractDocument)this.ftNotesCollected.getDocument();
/* 213 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/* 214 */       this.ftNotesCollected.setEditable(false);
/*     */     } 
/* 216 */     this.ftNotesCollected.setFont(fntFText);
/*     */     
/* 218 */     if (this.lblOneshotsOpened == null) this.lblOneshotsOpened = new JLabel(); 
/* 219 */     this.lblOneshotsOpened.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_FOUND_CHESTS"));
/* 220 */     this.lblOneshotsOpened.setFont(fntLabel);
/*     */     
/* 222 */     if (this.ftOneshotsOpened == null) {
/* 223 */       this.ftOneshotsOpened = new JFormattedTextField();
/* 224 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(7);
/* 225 */       AbstractDocument doc = (AbstractDocument)this.ftOneshotsOpened.getDocument();
/* 226 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/* 227 */       this.ftOneshotsOpened.setEditable(false);
/*     */     } 
/* 229 */     this.ftOneshotsOpened.setFont(fntFText);
/*     */     
/* 231 */     if (this.lblPlayTime == null) this.lblPlayTime = new JLabel(); 
/* 232 */     this.lblPlayTime.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_PLAY_TIME"));
/* 233 */     this.lblPlayTime.setFont(fntLabel);
/*     */     
/* 235 */     if (this.ftPlayTime == null) {
/* 236 */       this.ftPlayTime = new JFormattedTextField();
/* 237 */       this.ftPlayTime.setEditable(false);
/*     */     } 
/* 239 */     this.ftPlayTime.setFont(fntFText);
/*     */     
/* 241 */     if (this.lblItemsCrafted == null) this.lblItemsCrafted = new JLabel(); 
/* 242 */     this.lblItemsCrafted.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_CRAFTED_ITEMS"));
/* 243 */     this.lblItemsCrafted.setFont(fntLabel);
/*     */     
/* 245 */     if (this.ftItemsCrafted == null) {
/* 246 */       this.ftItemsCrafted = new JFormattedTextField();
/* 247 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(7);
/* 248 */       AbstractDocument doc = (AbstractDocument)this.ftItemsCrafted.getDocument();
/* 249 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/* 250 */       this.ftItemsCrafted.setEditable(false);
/*     */     } 
/* 252 */     this.ftItemsCrafted.setPreferredSize(new Dimension(GDStashFrame.iniConfig.sectUI.fontSize * 7, GDStashFrame.iniConfig.sectUI.fontSize * 2));
/* 253 */     this.ftItemsCrafted.setFont(fntFText);
/*     */     
/* 255 */     if (this.lblRelicsCrafted == null) this.lblRelicsCrafted = new JLabel(); 
/* 256 */     this.lblRelicsCrafted.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_CRAFTED_RELICS"));
/* 257 */     this.lblRelicsCrafted.setFont(fntLabel);
/*     */     
/* 259 */     if (this.ftRelicsCrafted == null) {
/* 260 */       this.ftRelicsCrafted = new JFormattedTextField();
/* 261 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(7);
/* 262 */       AbstractDocument doc = (AbstractDocument)this.ftRelicsCrafted.getDocument();
/* 263 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/* 264 */       this.ftRelicsCrafted.setEditable(false);
/*     */     } 
/* 266 */     this.ftRelicsCrafted.setFont(fntFText);
/*     */     
/* 268 */     if (this.lblRelicsTransCrafted == null) this.lblRelicsTransCrafted = new JLabel(); 
/* 269 */     this.lblRelicsTransCrafted.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_CRAFTED_RELICS_TRANS"));
/* 270 */     this.lblRelicsTransCrafted.setFont(fntLabel);
/*     */     
/* 272 */     if (this.ftRelicsTransCrafted == null) {
/* 273 */       this.ftRelicsTransCrafted = new JFormattedTextField();
/* 274 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(7);
/* 275 */       AbstractDocument doc = (AbstractDocument)this.ftRelicsTransCrafted.getDocument();
/* 276 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/* 277 */       this.ftRelicsTransCrafted.setEditable(false);
/*     */     } 
/* 279 */     this.ftRelicsTransCrafted.setFont(fntFText);
/*     */     
/* 281 */     if (this.lblRelicsMythicalCrafted == null) this.lblRelicsMythicalCrafted = new JLabel(); 
/* 282 */     this.lblRelicsMythicalCrafted.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_CRAFTED_RELICS_MYTH"));
/* 283 */     this.lblRelicsMythicalCrafted.setFont(fntLabel);
/*     */     
/* 285 */     if (this.ftRelicsMythicalCrafted == null) {
/* 286 */       this.ftRelicsMythicalCrafted = new JFormattedTextField();
/* 287 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(7);
/* 288 */       AbstractDocument doc = (AbstractDocument)this.ftRelicsMythicalCrafted.getDocument();
/* 289 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/* 290 */       this.ftRelicsMythicalCrafted.setEditable(false);
/*     */     } 
/* 292 */     this.ftRelicsMythicalCrafted.setFont(fntFText);
/*     */   }
/*     */   
/*     */   private JPanel buildLeftSidePanel() {
/* 296 */     JPanel panel = new JPanel();
/*     */     
/* 298 */     GroupLayout layout = null;
/* 299 */     GroupLayout.SequentialGroup hGroup = null;
/* 300 */     GroupLayout.SequentialGroup vGroup = null;
/*     */ 
/*     */ 
/*     */     
/* 304 */     layout = new GroupLayout(panel);
/* 305 */     panel.setLayout(layout);
/*     */     
/* 307 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 310 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 313 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 316 */     hGroup
/* 317 */       .addGroup(layout.createParallelGroup()
/* 318 */         .addComponent(this.lblGreatestDamage)
/* 319 */         .addComponent(this.lblMonstersKilled)
/* 320 */         .addComponent(this.lblHeroesKilled)
/* 321 */         .addComponent(this.lblNemesisKilled)
/* 322 */         .addComponent(this.lblNotesCollected)
/* 323 */         .addComponent(this.lblOneshotsOpened))
/* 324 */       .addGroup(layout.createParallelGroup()
/* 325 */         .addComponent(this.ftGreatestDamage)
/* 326 */         .addComponent(this.ftMonstersKilled)
/* 327 */         .addComponent(this.ftHeroesKilled)
/* 328 */         .addComponent(this.ftNemesisKilled)
/* 329 */         .addComponent(this.ftNotesCollected)
/* 330 */         .addComponent(this.ftOneshotsOpened))
/* 331 */       .addGroup(layout.createParallelGroup()
/* 332 */         .addComponent(this.btnGreatestDamageReset));
/* 333 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 336 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 339 */     vGroup
/* 340 */       .addGroup(layout.createParallelGroup()
/* 341 */         .addComponent(this.lblGreatestDamage)
/* 342 */         .addComponent(this.ftGreatestDamage)
/* 343 */         .addComponent(this.btnGreatestDamageReset))
/* 344 */       .addGroup(layout.createParallelGroup()
/* 345 */         .addComponent(this.lblMonstersKilled)
/* 346 */         .addComponent(this.ftMonstersKilled))
/* 347 */       .addGroup(layout.createParallelGroup()
/* 348 */         .addComponent(this.lblHeroesKilled)
/* 349 */         .addComponent(this.ftHeroesKilled))
/* 350 */       .addGroup(layout.createParallelGroup()
/* 351 */         .addComponent(this.lblNemesisKilled)
/* 352 */         .addComponent(this.ftNemesisKilled))
/* 353 */       .addGroup(layout.createParallelGroup()
/* 354 */         .addComponent(this.lblNotesCollected)
/* 355 */         .addComponent(this.ftNotesCollected))
/* 356 */       .addGroup(layout.createParallelGroup()
/* 357 */         .addComponent(this.lblOneshotsOpened)
/* 358 */         .addComponent(this.ftOneshotsOpened));
/* 359 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 361 */     layout.linkSize(0, new Component[] { this.lblGreatestDamage, this.lblMonstersKilled });
/* 362 */     layout.linkSize(0, new Component[] { this.lblGreatestDamage, this.lblHeroesKilled });
/* 363 */     layout.linkSize(0, new Component[] { this.lblGreatestDamage, this.lblNemesisKilled });
/* 364 */     layout.linkSize(0, new Component[] { this.lblGreatestDamage, this.lblNotesCollected });
/* 365 */     layout.linkSize(0, new Component[] { this.lblGreatestDamage, this.lblOneshotsOpened });
/*     */     
/* 367 */     layout.linkSize(0, new Component[] { this.ftGreatestDamage, this.ftMonstersKilled });
/* 368 */     layout.linkSize(0, new Component[] { this.ftGreatestDamage, this.ftHeroesKilled });
/* 369 */     layout.linkSize(0, new Component[] { this.ftGreatestDamage, this.ftNemesisKilled });
/* 370 */     layout.linkSize(0, new Component[] { this.ftGreatestDamage, this.ftNotesCollected });
/* 371 */     layout.linkSize(0, new Component[] { this.ftGreatestDamage, this.ftOneshotsOpened });
/*     */     
/* 373 */     layout.linkSize(1, new Component[] { this.lblGreatestDamage, this.lblMonstersKilled });
/* 374 */     layout.linkSize(1, new Component[] { this.lblGreatestDamage, this.lblHeroesKilled });
/* 375 */     layout.linkSize(1, new Component[] { this.lblGreatestDamage, this.lblNemesisKilled });
/* 376 */     layout.linkSize(1, new Component[] { this.lblGreatestDamage, this.lblNotesCollected });
/* 377 */     layout.linkSize(1, new Component[] { this.lblGreatestDamage, this.lblOneshotsOpened });
/*     */     
/* 379 */     layout.linkSize(1, new Component[] { this.lblGreatestDamage, this.ftGreatestDamage });
/* 380 */     layout.linkSize(1, new Component[] { this.lblGreatestDamage, this.ftMonstersKilled });
/* 381 */     layout.linkSize(1, new Component[] { this.lblGreatestDamage, this.ftHeroesKilled });
/* 382 */     layout.linkSize(1, new Component[] { this.lblGreatestDamage, this.ftNemesisKilled });
/* 383 */     layout.linkSize(1, new Component[] { this.lblGreatestDamage, this.ftNotesCollected });
/* 384 */     layout.linkSize(1, new Component[] { this.lblGreatestDamage, this.ftOneshotsOpened });
/*     */     
/* 386 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildRightSidePanel() {
/* 390 */     JPanel panel = new JPanel();
/*     */     
/* 392 */     GroupLayout layout = null;
/* 393 */     GroupLayout.SequentialGroup hGroup = null;
/* 394 */     GroupLayout.SequentialGroup vGroup = null;
/*     */ 
/*     */ 
/*     */     
/* 398 */     layout = new GroupLayout(panel);
/* 399 */     panel.setLayout(layout);
/*     */     
/* 401 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 404 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 407 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 410 */     hGroup
/* 411 */       .addGroup(layout.createParallelGroup()
/* 412 */         .addComponent(this.lblPlayTime)
/* 413 */         .addComponent(this.lblItemsCrafted)
/* 414 */         .addComponent(this.lblRelicsCrafted)
/* 415 */         .addComponent(this.lblRelicsTransCrafted)
/* 416 */         .addComponent(this.lblRelicsMythicalCrafted))
/* 417 */       .addGroup(layout.createParallelGroup()
/* 418 */         .addComponent(this.ftPlayTime)
/* 419 */         .addComponent(this.ftItemsCrafted)
/* 420 */         .addComponent(this.ftRelicsCrafted)
/* 421 */         .addComponent(this.ftRelicsTransCrafted)
/* 422 */         .addComponent(this.ftRelicsMythicalCrafted));
/* 423 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 426 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 429 */     vGroup
/* 430 */       .addGroup(layout.createParallelGroup()
/* 431 */         .addComponent(this.lblPlayTime)
/* 432 */         .addComponent(this.ftPlayTime))
/* 433 */       .addGroup(layout.createParallelGroup()
/* 434 */         .addComponent(this.lblItemsCrafted)
/* 435 */         .addComponent(this.ftItemsCrafted))
/* 436 */       .addGroup(layout.createParallelGroup()
/* 437 */         .addComponent(this.lblRelicsCrafted)
/* 438 */         .addComponent(this.ftRelicsCrafted))
/* 439 */       .addGroup(layout.createParallelGroup()
/* 440 */         .addComponent(this.lblRelicsTransCrafted)
/* 441 */         .addComponent(this.ftRelicsTransCrafted))
/* 442 */       .addGroup(layout.createParallelGroup()
/* 443 */         .addComponent(this.lblRelicsMythicalCrafted)
/* 444 */         .addComponent(this.ftRelicsMythicalCrafted));
/* 445 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 447 */     layout.linkSize(0, new Component[] { this.lblItemsCrafted, this.lblPlayTime });
/* 448 */     layout.linkSize(0, new Component[] { this.lblItemsCrafted, this.lblRelicsCrafted });
/* 449 */     layout.linkSize(0, new Component[] { this.lblItemsCrafted, this.lblRelicsTransCrafted });
/* 450 */     layout.linkSize(0, new Component[] { this.lblItemsCrafted, this.lblRelicsMythicalCrafted });
/*     */     
/* 452 */     layout.linkSize(0, new Component[] { this.ftItemsCrafted, this.ftPlayTime });
/* 453 */     layout.linkSize(0, new Component[] { this.ftItemsCrafted, this.ftRelicsCrafted });
/* 454 */     layout.linkSize(0, new Component[] { this.ftItemsCrafted, this.ftRelicsTransCrafted });
/* 455 */     layout.linkSize(0, new Component[] { this.ftItemsCrafted, this.ftRelicsMythicalCrafted });
/*     */     
/* 457 */     layout.linkSize(1, new Component[] { this.lblItemsCrafted, this.lblPlayTime });
/* 458 */     layout.linkSize(1, new Component[] { this.lblItemsCrafted, this.lblRelicsCrafted });
/* 459 */     layout.linkSize(1, new Component[] { this.lblItemsCrafted, this.lblRelicsTransCrafted });
/* 460 */     layout.linkSize(1, new Component[] { this.lblItemsCrafted, this.lblRelicsMythicalCrafted });
/*     */     
/* 462 */     layout.linkSize(1, new Component[] { this.lblItemsCrafted, this.ftPlayTime });
/* 463 */     layout.linkSize(1, new Component[] { this.lblItemsCrafted, this.ftItemsCrafted });
/* 464 */     layout.linkSize(1, new Component[] { this.lblItemsCrafted, this.ftRelicsCrafted });
/* 465 */     layout.linkSize(1, new Component[] { this.lblItemsCrafted, this.ftRelicsTransCrafted });
/* 466 */     layout.linkSize(1, new Component[] { this.lblItemsCrafted, this.ftRelicsMythicalCrafted });
/*     */     
/* 468 */     return panel;
/*     */   }
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 472 */     this.gdc = gdc;
/*     */     
/* 474 */     int greatestDamage = 0;
/* 475 */     int killsMonster = 0;
/* 476 */     int killsHero = 0;
/* 477 */     int killsNemesis = 0;
/* 478 */     int foundNotes = 0;
/* 479 */     int foundOneShot = 0;
/* 480 */     String playTime = "";
/* 481 */     int craftItems = 0;
/* 482 */     int craftRelic = 0;
/* 483 */     int craftRelicTrans = 0;
/* 484 */     int craftRelicMyth = 0;
/*     */     
/* 486 */     if (gdc != null) {
/* 487 */       greatestDamage = gdc.getGreatestDamage();
/* 488 */       killsMonster = gdc.getKillsMonster();
/* 489 */       killsHero = gdc.getKillsHero();
/* 490 */       killsNemesis = gdc.getKillsNemesis();
/* 491 */       foundNotes = gdc.getLoreNotesCollected();
/* 492 */       foundOneShot = gdc.getOneShotChestsOpened();
/* 493 */       playTime = gdc.getPlayTimeAsDHM();
/* 494 */       craftItems = gdc.getItemsCrafted();
/* 495 */       craftRelic = gdc.getRelicsCrafted();
/* 496 */       craftRelicTrans = gdc.getRelicsTranscendentCrafted();
/* 497 */       craftRelicMyth = gdc.getRelicsMythicalCrafted();
/*     */     } 
/*     */     
/* 500 */     this.ftGreatestDamage.setText(Integer.toString(greatestDamage));
/* 501 */     this.ftMonstersKilled.setText(Integer.toString(killsMonster));
/* 502 */     this.ftHeroesKilled.setText(Integer.toString(killsHero));
/* 503 */     this.ftNemesisKilled.setText(Integer.toString(killsNemesis));
/* 504 */     this.ftNotesCollected.setText(Integer.toString(foundNotes));
/* 505 */     this.ftOneshotsOpened.setText(Integer.toString(foundOneShot));
/* 506 */     this.ftPlayTime.setText(playTime);
/* 507 */     this.ftItemsCrafted.setText(Integer.toString(craftItems));
/* 508 */     this.ftRelicsCrafted.setText(Integer.toString(craftRelic));
/* 509 */     this.ftRelicsTransCrafted.setText(Integer.toString(craftRelicTrans));
/* 510 */     this.ftRelicsMythicalCrafted.setText(Integer.toString(craftRelicMyth));
/*     */   }
/*     */   
/*     */   public void updateChar(GDChar gdc) {
/* 514 */     if (gdc == null)
/*     */       return; 
/* 516 */     gdc.setGreatestDamage(Integer.parseInt(this.ftGreatestDamage.getText()));
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharStatAchievementPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */