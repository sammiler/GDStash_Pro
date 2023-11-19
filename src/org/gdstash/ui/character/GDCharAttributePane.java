/*     */ package org.gdstash.ui.character;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFormattedTextField;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.text.AbstractDocument;
/*     */ import javax.swing.text.DocumentFilter;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.select.IntLenDocFilter;
/*     */ import org.gdstash.util.GDImagePool;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDCharAttributePane extends AdjustablePanel {
/*     */   private static final int ATTRIB_DIGITS = 6;
/*     */   private static final int HEALTH_DIGITS = 8;
/*     */   private JLabel lblPhysique;
/*     */   private JLabel lblCunning;
/*     */   private JLabel lblSpirit;
/*     */   private JLabel lblHealth;
/*     */   private JLabel lblEnergy;
/*     */   private JTextField ftPhysique;
/*     */   private JButton btnPhysiquePlus;
/*     */   private JButton btnPhysiqueMinus;
/*     */   private JTextField ftCunning;
/*     */   private JButton btnCunningPlus;
/*     */   private JButton btnCunningMinus;
/*     */   private JTextField ftSpirit;
/*     */   private JButton btnSpiritPlus;
/*     */   private JButton btnSpiritMinus;
/*     */   private JTextField ftHealth;
/*     */   private JTextField ftEnergy;
/*     */   private GDCharStatEditPane editPane;
/*     */   
/*     */   private class PlusActionListener implements ActionListener {
/*     */     public PlusActionListener(JTextField textField) {
/*  44 */       this.textField = textField;
/*     */     }
/*     */     private JTextField textField;
/*     */     
/*     */     public void actionPerformed(ActionEvent ev) {
/*  49 */       int val = Integer.parseInt(this.textField.getText());
/*     */       
/*  51 */       int points = GDCharAttributePane.this.editPane.getStatPoints();
/*     */       
/*  53 */       if (points > 0) {
/*  54 */         if (this.textField == GDCharAttributePane.this.ftCunning) {
/*  55 */           this.textField.setText(Integer.toString(val + GDStashFrame.enginePlayer.getIncDex()));
/*     */         }
/*     */         
/*  58 */         if (this.textField == GDCharAttributePane.this.ftPhysique) {
/*  59 */           this.textField.setText(Integer.toString(val + GDStashFrame.enginePlayer.getIncStr()));
/*     */           
/*  61 */           float health = GDCharAttributePane.this.editPane.getHealth();
/*  62 */           GDCharAttributePane.this.editPane.setHealth(health + GDStashFrame.enginePlayer.getIncLife());
/*     */         } 
/*     */         
/*  65 */         if (this.textField == GDCharAttributePane.this.ftSpirit) {
/*  66 */           this.textField.setText(Integer.toString(val + GDStashFrame.enginePlayer.getIncInt()));
/*     */           
/*  68 */           float energy = GDCharAttributePane.this.editPane.getEnergy();
/*  69 */           GDCharAttributePane.this.editPane.setEnergy(energy + GDStashFrame.enginePlayer.getIncMana());
/*     */         } 
/*     */         
/*  72 */         GDCharAttributePane.this.editPane.setStatPoints(points - 1);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class MinusActionListener implements ActionListener {
/*     */     private JTextField textField;
/*     */     
/*     */     public MinusActionListener(JTextField textField) {
/*  81 */       this.textField = textField;
/*     */     }
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent ev) {
/*  86 */       int val = Integer.parseInt(this.textField.getText());
/*     */       
/*  88 */       if (this.textField == GDCharAttributePane.this.ftCunning) {
/*  89 */         int baseStat = GDStashFrame.enginePlayer.getBaseDex();
/*  90 */         int incStat = GDStashFrame.enginePlayer.getIncDex();
/*     */         
/*  92 */         if (val < baseStat + incStat)
/*     */           return; 
/*  94 */         this.textField.setText(Integer.toString(val - incStat));
/*     */       } 
/*     */       
/*  97 */       if (this.textField == GDCharAttributePane.this.ftPhysique) {
/*  98 */         int baseStat = GDStashFrame.enginePlayer.getBaseStr();
/*  99 */         int incStat = GDStashFrame.enginePlayer.getIncStr();
/*     */         
/* 101 */         if (val < baseStat + incStat)
/*     */           return; 
/* 103 */         float health = GDCharAttributePane.this.editPane.getHealth();
/* 104 */         int baseHealth = GDStashFrame.enginePlayer.getBaseLife();
/* 105 */         int incHealth = GDStashFrame.enginePlayer.getIncLife();
/*     */         
/* 107 */         if (health < (baseHealth + incHealth))
/*     */           return; 
/* 109 */         this.textField.setText(Integer.toString(val - incStat));
/* 110 */         GDCharAttributePane.this.editPane.setHealth(health - incHealth);
/*     */       } 
/*     */       
/* 113 */       if (this.textField == GDCharAttributePane.this.ftSpirit) {
/* 114 */         int baseStat = GDStashFrame.enginePlayer.getBaseInt();
/* 115 */         int incStat = GDStashFrame.enginePlayer.getIncInt();
/*     */         
/* 117 */         if (val < baseStat + incStat)
/*     */           return; 
/* 119 */         this.textField.setText(Integer.toString(val - incStat));
/*     */         
/* 121 */         float energy = GDCharAttributePane.this.editPane.getEnergy();
/* 122 */         int baseEnergy = GDStashFrame.enginePlayer.getBaseMana();
/* 123 */         int incEnergy = GDStashFrame.enginePlayer.getIncMana();
/*     */         
/* 125 */         if (energy < (baseEnergy + incEnergy))
/*     */           return; 
/* 127 */         this.textField.setText(Integer.toString(val - incStat));
/* 128 */         GDCharAttributePane.this.editPane.setEnergy(energy - incEnergy);
/*     */       } 
/*     */       
/* 131 */       int points = GDCharAttributePane.this.editPane.getStatPoints();
/*     */       
/* 133 */       GDCharAttributePane.this.editPane.setStatPoints(points + 1);
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
/*     */   public GDCharAttributePane(GDCharStatEditPane editPane) {
/* 158 */     this.editPane = editPane;
/*     */     
/* 160 */     adjustUI();
/*     */     
/* 162 */     JPanel pnlPhysique = buildPlusMinusButtonPanel(this.ftPhysique, this.btnPhysiquePlus, this.btnPhysiqueMinus);
/* 163 */     JPanel pnlCunning = buildPlusMinusButtonPanel(this.ftCunning, this.btnCunningPlus, this.btnCunningMinus);
/* 164 */     JPanel pnlSpirit = buildPlusMinusButtonPanel(this.ftSpirit, this.btnSpiritPlus, this.btnSpiritMinus);
/*     */ 
/*     */ 
/*     */     
/* 168 */     GroupLayout layout = null;
/* 169 */     GroupLayout.SequentialGroup hGroup = null;
/* 170 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 172 */     layout = new GroupLayout((Container)this);
/* 173 */     setLayout(layout);
/*     */     
/* 175 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 178 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 181 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 184 */     hGroup
/* 185 */       .addGroup(layout.createParallelGroup()
/* 186 */         .addComponent(this.lblPhysique)
/* 187 */         .addComponent(this.lblCunning)
/* 188 */         .addComponent(this.lblSpirit)
/* 189 */         .addComponent(this.lblHealth)
/* 190 */         .addComponent(this.lblEnergy))
/* 191 */       .addGroup(layout.createParallelGroup()
/* 192 */         .addComponent(pnlPhysique)
/* 193 */         .addComponent(pnlCunning)
/* 194 */         .addComponent(pnlSpirit)
/* 195 */         .addComponent(this.ftHealth)
/* 196 */         .addComponent(this.ftEnergy));
/* 197 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 200 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 203 */     vGroup
/* 204 */       .addGroup(layout.createParallelGroup()
/* 205 */         .addComponent(this.lblPhysique)
/* 206 */         .addComponent(pnlPhysique))
/* 207 */       .addGroup(layout.createParallelGroup()
/* 208 */         .addComponent(this.lblCunning)
/* 209 */         .addComponent(pnlCunning))
/* 210 */       .addGroup(layout.createParallelGroup()
/* 211 */         .addComponent(this.lblSpirit)
/* 212 */         .addComponent(pnlSpirit))
/* 213 */       .addGroup(layout.createParallelGroup()
/* 214 */         .addComponent(this.lblHealth)
/* 215 */         .addComponent(this.ftHealth))
/* 216 */       .addGroup(layout.createParallelGroup()
/* 217 */         .addComponent(this.lblEnergy)
/* 218 */         .addComponent(this.ftEnergy));
/* 219 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 221 */     layout.linkSize(0, new Component[] { this.lblPhysique, this.lblCunning });
/* 222 */     layout.linkSize(0, new Component[] { this.lblPhysique, this.lblSpirit });
/* 223 */     layout.linkSize(0, new Component[] { this.lblPhysique, this.lblHealth });
/* 224 */     layout.linkSize(0, new Component[] { this.lblPhysique, this.lblEnergy });
/*     */     
/* 226 */     layout.linkSize(0, new Component[] { pnlPhysique, pnlCunning });
/* 227 */     layout.linkSize(0, new Component[] { pnlPhysique, pnlSpirit });
/* 228 */     layout.linkSize(0, new Component[] { pnlPhysique, this.ftHealth });
/* 229 */     layout.linkSize(0, new Component[] { pnlPhysique, this.ftEnergy });
/*     */     
/* 231 */     layout.linkSize(1, new Component[] { this.lblPhysique, this.lblCunning });
/* 232 */     layout.linkSize(1, new Component[] { this.lblPhysique, this.lblSpirit });
/* 233 */     layout.linkSize(1, new Component[] { this.lblPhysique, this.lblHealth });
/* 234 */     layout.linkSize(1, new Component[] { this.lblPhysique, this.lblEnergy });
/*     */     
/* 236 */     layout.linkSize(1, new Component[] { this.lblPhysique, pnlPhysique });
/* 237 */     layout.linkSize(1, new Component[] { this.lblPhysique, pnlCunning });
/* 238 */     layout.linkSize(1, new Component[] { this.lblPhysique, pnlSpirit });
/* 239 */     layout.linkSize(1, new Component[] { this.lblPhysique, this.ftHealth });
/* 240 */     layout.linkSize(1, new Component[] { this.lblPhysique, this.ftEnergy });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 245 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 246 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/* 247 */     if (fntButton == null) fntButton = fntLabel; 
/* 248 */     Font fntFText = UIManager.getDefaults().getFont("FormattedTextField.font");
/* 249 */     if (fntFText == null) fntFText = fntLabel; 
/* 250 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 251 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 253 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 254 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 255 */     fntFText = fntFText.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 256 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 258 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 259 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 260 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 261 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_ATTRIBUTES"));
/* 262 */     text.setTitleFont(fntBorder);
/*     */     
/* 264 */     setBorder(text);
/*     */     
/* 266 */     if (this.lblPhysique == null) this.lblPhysique = new JLabel(); 
/* 267 */     this.lblPhysique.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_STAT_PHYSIQUE"));
/* 268 */     this.lblPhysique.setFont(fntLabel);
/*     */     
/* 270 */     if (this.ftPhysique == null) {
/* 271 */       this.ftPhysique = new JFormattedTextField();
/* 272 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(6);
/* 273 */       AbstractDocument doc = (AbstractDocument)this.ftPhysique.getDocument();
/* 274 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */     } 
/* 276 */     this.ftPhysique.setPreferredSize(new Dimension(GDStashFrame.iniConfig.sectUI.fontSize * 5, GDStashFrame.iniConfig.sectUI.fontSize * 2));
/* 277 */     this.ftPhysique.setFont(fntFText);
/*     */     
/* 279 */     if (this.btnPhysiquePlus == null) {
/* 280 */       this.btnPhysiquePlus = new JButton();
/*     */       
/* 282 */       this.btnPhysiquePlus.addActionListener(new PlusActionListener(this.ftPhysique));
/*     */     } 
/* 284 */     this.btnPhysiquePlus.setIcon(GDImagePool.iconPlus16);
/* 285 */     this.btnPhysiquePlus.setFont(fntButton);
/*     */     
/* 287 */     if (this.btnPhysiqueMinus == null) {
/* 288 */       this.btnPhysiqueMinus = new JButton();
/*     */       
/* 290 */       this.btnPhysiqueMinus.addActionListener(new MinusActionListener(this.ftPhysique));
/*     */     } 
/* 292 */     this.btnPhysiqueMinus.setIcon(GDImagePool.iconMinus16);
/* 293 */     this.btnPhysiqueMinus.setFont(fntButton);
/*     */     
/* 295 */     if (this.lblCunning == null) this.lblCunning = new JLabel(); 
/* 296 */     this.lblCunning.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_STAT_CUNNING"));
/* 297 */     this.lblCunning.setFont(fntLabel);
/*     */     
/* 299 */     if (this.ftCunning == null) {
/* 300 */       this.ftCunning = new JFormattedTextField();
/* 301 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(6);
/* 302 */       AbstractDocument doc = (AbstractDocument)this.ftCunning.getDocument();
/* 303 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */     } 
/* 305 */     this.ftCunning.setFont(fntFText);
/*     */     
/* 307 */     if (this.btnCunningPlus == null) {
/* 308 */       this.btnCunningPlus = new JButton();
/*     */       
/* 310 */       this.btnCunningPlus.addActionListener(new PlusActionListener(this.ftCunning));
/*     */     } 
/* 312 */     this.btnCunningPlus.setIcon(GDImagePool.iconPlus16);
/* 313 */     this.btnCunningPlus.setFont(fntButton);
/*     */     
/* 315 */     if (this.btnCunningMinus == null) {
/* 316 */       this.btnCunningMinus = new JButton();
/*     */       
/* 318 */       this.btnCunningMinus.addActionListener(new MinusActionListener(this.ftCunning));
/*     */     } 
/* 320 */     this.btnCunningMinus.setIcon(GDImagePool.iconMinus16);
/* 321 */     this.btnCunningMinus.setFont(fntButton);
/*     */     
/* 323 */     if (this.lblSpirit == null) this.lblSpirit = new JLabel(); 
/* 324 */     this.lblSpirit.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_STAT_SPIRIT"));
/* 325 */     this.lblSpirit.setFont(fntLabel);
/*     */     
/* 327 */     if (this.ftSpirit == null) {
/* 328 */       this.ftSpirit = new JFormattedTextField();
/* 329 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(6);
/* 330 */       AbstractDocument doc = (AbstractDocument)this.ftSpirit.getDocument();
/* 331 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */     } 
/* 333 */     this.ftSpirit.setFont(fntFText);
/*     */     
/* 335 */     if (this.btnSpiritPlus == null) {
/* 336 */       this.btnSpiritPlus = new JButton();
/*     */       
/* 338 */       this.btnSpiritPlus.addActionListener(new PlusActionListener(this.ftSpirit));
/*     */     } 
/* 340 */     this.btnSpiritPlus.setIcon(GDImagePool.iconPlus16);
/* 341 */     this.btnSpiritPlus.setFont(fntButton);
/*     */     
/* 343 */     if (this.btnSpiritMinus == null) {
/* 344 */       this.btnSpiritMinus = new JButton();
/*     */       
/* 346 */       this.btnSpiritMinus.addActionListener(new MinusActionListener(this.ftSpirit));
/*     */     } 
/* 348 */     this.btnSpiritMinus.setIcon(GDImagePool.iconMinus16);
/* 349 */     this.btnSpiritMinus.setFont(fntButton);
/*     */     
/* 351 */     if (this.lblHealth == null) this.lblHealth = new JLabel(); 
/* 352 */     this.lblHealth.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_STAT_HEALTH"));
/* 353 */     this.lblHealth.setFont(fntLabel);
/*     */     
/* 355 */     if (this.ftHealth == null) {
/* 356 */       this.ftHealth = new JFormattedTextField();
/* 357 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(8);
/* 358 */       AbstractDocument doc = (AbstractDocument)this.ftHealth.getDocument();
/* 359 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */       
/* 361 */       this.ftHealth.setEditable(false);
/*     */     } 
/* 363 */     this.ftHealth.setFont(fntFText);
/*     */     
/* 365 */     if (this.lblEnergy == null) this.lblEnergy = new JLabel(); 
/* 366 */     this.lblEnergy.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_STAT_ENERGY"));
/* 367 */     this.lblEnergy.setFont(fntLabel);
/*     */     
/* 369 */     if (this.ftEnergy == null) {
/* 370 */       this.ftEnergy = new JFormattedTextField();
/* 371 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(8);
/* 372 */       AbstractDocument doc = (AbstractDocument)this.ftEnergy.getDocument();
/* 373 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */       
/* 375 */       this.ftEnergy.setEditable(false);
/*     */     } 
/* 377 */     this.ftEnergy.setFont(fntFText);
/*     */   }
/*     */   
/*     */   private JPanel buildPlusMinusButtonPanel(JTextField textField, JButton btnPlus, JButton btnMinus) {
/* 381 */     JPanel panel = new JPanel();
/*     */     
/* 383 */     GroupLayout layout = null;
/* 384 */     GroupLayout.SequentialGroup hGroup = null;
/* 385 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 387 */     layout = new GroupLayout(panel);
/* 388 */     panel.setLayout(layout);
/*     */     
/* 390 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 393 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 396 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 399 */     hGroup
/* 400 */       .addGroup(layout.createParallelGroup()
/* 401 */         .addComponent(btnMinus))
/* 402 */       .addGroup(layout.createParallelGroup()
/* 403 */         .addComponent(textField))
/* 404 */       .addGroup(layout.createParallelGroup()
/* 405 */         .addComponent(btnPlus));
/* 406 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 409 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 412 */     vGroup
/* 413 */       .addGroup(layout.createParallelGroup()
/* 414 */         .addComponent(btnMinus)
/* 415 */         .addComponent(textField)
/* 416 */         .addComponent(btnPlus));
/* 417 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 419 */     layout.linkSize(0, new Component[] { btnMinus, btnPlus });
/*     */     
/* 421 */     layout.linkSize(1, new Component[] { btnMinus, textField });
/* 422 */     layout.linkSize(1, new Component[] { btnMinus, btnPlus });
/*     */     
/* 424 */     return panel;
/*     */   }
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 428 */     float physique = 0.0F;
/* 429 */     float cunning = 0.0F;
/* 430 */     float spirit = 0.0F;
/* 431 */     float health = 0.0F;
/* 432 */     float energy = 0.0F;
/* 433 */     if (gdc != null) {
/* 434 */       physique = gdc.getPhysique();
/* 435 */       cunning = gdc.getCunning();
/* 436 */       spirit = gdc.getSpirit();
/* 437 */       health = gdc.getHealth();
/* 438 */       energy = gdc.getEnergy();
/*     */     } 
/*     */     
/* 441 */     this.ftPhysique.setText(Integer.toString((int)physique));
/* 442 */     this.ftCunning.setText(Integer.toString((int)cunning));
/* 443 */     this.ftSpirit.setText(Integer.toString((int)spirit));
/* 444 */     this.ftHealth.setText(Integer.toString((int)health));
/* 445 */     this.ftEnergy.setText(Integer.toString((int)energy));
/*     */   }
/*     */   
/*     */   public void updateChar(GDChar gdc) {
/* 449 */     if (gdc == null)
/*     */       return; 
/* 451 */     gdc.setPhysique(Integer.parseInt(this.ftPhysique.getText()));
/* 452 */     gdc.setCunning(Integer.parseInt(this.ftCunning.getText()));
/* 453 */     gdc.setSpirit(Integer.parseInt(this.ftSpirit.getText()));
/* 454 */     gdc.setHealth(Integer.parseInt(this.ftHealth.getText()));
/* 455 */     gdc.setEnergy(Integer.parseInt(this.ftEnergy.getText()));
/*     */   }
/*     */   
/*     */   public float getHealth() {
/* 459 */     return Float.parseFloat(this.ftHealth.getText());
/*     */   }
/*     */   
/*     */   public float getEnergy() {
/* 463 */     return Float.parseFloat(this.ftEnergy.getText());
/*     */   }
/*     */   
/*     */   public void setHealth(float health) {
/* 467 */     this.ftHealth.setText(Integer.toString((int)health));
/*     */   }
/*     */   
/*     */   public void setEnergy(float energy) {
/* 471 */     this.ftEnergy.setText(Integer.toString((int)energy));
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharAttributePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */