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
/*     */
/*     */
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
/*     */ import javax.swing.text.DocumentFilter;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.select.IntLenDocFilter;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
import org.gdstash.util.GDImagePool;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDCharAttributePane extends AdjustablePanel {
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
/*  41 */       this.textField = textField;
/*     */     }
/*     */     private JTextField textField;
/*     */     
/*     */     public void actionPerformed(ActionEvent ev) {
/*  46 */       int val = Integer.parseInt(this.textField.getText());
/*     */       
/*  48 */       int points = GDCharAttributePane.this.editPane.getStatPoints();
/*     */       
/*  50 */       if (points > 0) {
/*  51 */         if (this.textField == GDCharAttributePane.this.ftCunning) {
/*  52 */           this.textField.setText(Integer.toString(val + GDStashFrame.enginePlayer.getIncDex()));
/*     */         }
/*     */         
/*  55 */         if (this.textField == GDCharAttributePane.this.ftPhysique) {
/*  56 */           this.textField.setText(Integer.toString(val + GDStashFrame.enginePlayer.getIncStr()));
/*     */           
/*  58 */           float health = GDCharAttributePane.this.editPane.getHealth();
/*  59 */           GDCharAttributePane.this.editPane.setHealth(health + GDStashFrame.enginePlayer.getIncLife());
/*     */         } 
/*     */         
/*  62 */         if (this.textField == GDCharAttributePane.this.ftSpirit) {
/*  63 */           this.textField.setText(Integer.toString(val + GDStashFrame.enginePlayer.getIncInt()));
/*     */           
/*  65 */           float energy = GDCharAttributePane.this.editPane.getEnergy();
/*  66 */           GDCharAttributePane.this.editPane.setEnergy(energy + GDStashFrame.enginePlayer.getIncMana());
/*     */         } 
/*     */         
/*  69 */         GDCharAttributePane.this.editPane.setStatPoints(points - 1);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class MinusActionListener implements ActionListener {
/*     */     private JTextField textField;
/*     */     
/*     */     public MinusActionListener(JTextField textField) {
/*  78 */       this.textField = textField;
/*     */     }
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent ev) {
/*  83 */       int val = Integer.parseInt(this.textField.getText());
/*     */       
/*  85 */       if (this.textField == GDCharAttributePane.this.ftCunning) {
/*  86 */         int baseStat = GDStashFrame.enginePlayer.getBaseDex();
/*  87 */         int incStat = GDStashFrame.enginePlayer.getIncDex();
/*     */         
/*  89 */         if (val < baseStat + incStat)
/*     */           return; 
/*  91 */         this.textField.setText(Integer.toString(val - incStat));
/*     */       } 
/*     */       
/*  94 */       if (this.textField == GDCharAttributePane.this.ftPhysique) {
/*  95 */         int baseStat = GDStashFrame.enginePlayer.getBaseStr();
/*  96 */         int incStat = GDStashFrame.enginePlayer.getIncStr();
/*     */         
/*  98 */         if (val < baseStat + incStat)
/*     */           return; 
/* 100 */         float health = GDCharAttributePane.this.editPane.getHealth();
/* 101 */         int baseHealth = GDStashFrame.enginePlayer.getBaseLife();
/* 102 */         int incHealth = GDStashFrame.enginePlayer.getIncLife();
/*     */         
/* 104 */         if (health < (baseHealth + incHealth))
/*     */           return; 
/* 106 */         this.textField.setText(Integer.toString(val - incStat));
/* 107 */         GDCharAttributePane.this.editPane.setHealth(health - incHealth);
/*     */       } 
/*     */       
/* 110 */       if (this.textField == GDCharAttributePane.this.ftSpirit) {
/* 111 */         int baseStat = GDStashFrame.enginePlayer.getBaseInt();
/* 112 */         int incStat = GDStashFrame.enginePlayer.getIncInt();
/*     */         
/* 114 */         if (val < baseStat + incStat)
/*     */           return; 
/* 116 */         this.textField.setText(Integer.toString(val - incStat));
/*     */         
/* 118 */         float energy = GDCharAttributePane.this.editPane.getEnergy();
/* 119 */         int baseEnergy = GDStashFrame.enginePlayer.getBaseMana();
/* 120 */         int incEnergy = GDStashFrame.enginePlayer.getIncMana();
/*     */         
/* 122 */         if (energy < (baseEnergy + incEnergy))
/*     */           return; 
/* 124 */         this.textField.setText(Integer.toString(val - incStat));
/* 125 */         GDCharAttributePane.this.editPane.setEnergy(energy - incEnergy);
/*     */       } 
/*     */       
/* 128 */       int points = GDCharAttributePane.this.editPane.getStatPoints();
/*     */       
/* 130 */       GDCharAttributePane.this.editPane.setStatPoints(points + 1);
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
/* 155 */     this.editPane = editPane;
/*     */     
/* 157 */     adjustUI();
/*     */     
/* 159 */     JPanel pnlPhysique = buildPlusMinusButtonPanel(this.ftPhysique, this.btnPhysiquePlus, this.btnPhysiqueMinus);
/* 160 */     JPanel pnlCunning = buildPlusMinusButtonPanel(this.ftCunning, this.btnCunningPlus, this.btnCunningMinus);
/* 161 */     JPanel pnlSpirit = buildPlusMinusButtonPanel(this.ftSpirit, this.btnSpiritPlus, this.btnSpiritMinus);
/*     */ 
/*     */ 
/*     */     
/* 165 */     GroupLayout layout = null;
/* 166 */     GroupLayout.SequentialGroup hGroup = null;
/* 167 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 169 */     layout = new GroupLayout((Container)this);
/* 170 */     setLayout(layout);
/*     */     
/* 172 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 175 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 178 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 181 */     hGroup
/* 182 */       .addGroup(layout.createParallelGroup()
/* 183 */         .addComponent(this.lblPhysique)
/* 184 */         .addComponent(this.lblCunning)
/* 185 */         .addComponent(this.lblSpirit)
/* 186 */         .addComponent(this.lblHealth)
/* 187 */         .addComponent(this.lblEnergy))
/* 188 */       .addGroup(layout.createParallelGroup()
/* 189 */         .addComponent(pnlPhysique)
/* 190 */         .addComponent(pnlCunning)
/* 191 */         .addComponent(pnlSpirit)
/* 192 */         .addComponent(this.ftHealth)
/* 193 */         .addComponent(this.ftEnergy));
/* 194 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 197 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 200 */     vGroup
/* 201 */       .addGroup(layout.createParallelGroup()
/* 202 */         .addComponent(this.lblPhysique)
/* 203 */         .addComponent(pnlPhysique))
/* 204 */       .addGroup(layout.createParallelGroup()
/* 205 */         .addComponent(this.lblCunning)
/* 206 */         .addComponent(pnlCunning))
/* 207 */       .addGroup(layout.createParallelGroup()
/* 208 */         .addComponent(this.lblSpirit)
/* 209 */         .addComponent(pnlSpirit))
/* 210 */       .addGroup(layout.createParallelGroup()
/* 211 */         .addComponent(this.lblHealth)
/* 212 */         .addComponent(this.ftHealth))
/* 213 */       .addGroup(layout.createParallelGroup()
/* 214 */         .addComponent(this.lblEnergy)
/* 215 */         .addComponent(this.ftEnergy));
/* 216 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 218 */     layout.linkSize(0, new Component[] { this.lblPhysique, this.lblCunning });
/* 219 */     layout.linkSize(0, new Component[] { this.lblPhysique, this.lblSpirit });
/* 220 */     layout.linkSize(0, new Component[] { this.lblPhysique, this.lblHealth });
/* 221 */     layout.linkSize(0, new Component[] { this.lblPhysique, this.lblEnergy });
/*     */     
/* 223 */     layout.linkSize(0, new Component[] { pnlPhysique, pnlCunning });
/* 224 */     layout.linkSize(0, new Component[] { pnlPhysique, pnlSpirit });
/* 225 */     layout.linkSize(0, new Component[] { pnlPhysique, this.ftHealth });
/* 226 */     layout.linkSize(0, new Component[] { pnlPhysique, this.ftEnergy });
/*     */     
/* 228 */     layout.linkSize(1, new Component[] { this.lblPhysique, this.lblCunning });
/* 229 */     layout.linkSize(1, new Component[] { this.lblPhysique, this.lblSpirit });
/* 230 */     layout.linkSize(1, new Component[] { this.lblPhysique, this.lblHealth });
/* 231 */     layout.linkSize(1, new Component[] { this.lblPhysique, this.lblEnergy });
/*     */     
/* 233 */     layout.linkSize(1, new Component[] { this.lblPhysique, pnlPhysique });
/* 234 */     layout.linkSize(1, new Component[] { this.lblPhysique, pnlCunning });
/* 235 */     layout.linkSize(1, new Component[] { this.lblPhysique, pnlSpirit });
/* 236 */     layout.linkSize(1, new Component[] { this.lblPhysique, this.ftHealth });
/* 237 */     layout.linkSize(1, new Component[] { this.lblPhysique, this.ftEnergy });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 242 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 243 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/* 244 */     if (fntButton == null) fntButton = fntLabel; 
/* 245 */     Font fntFText = UIManager.getDefaults().getFont("FormattedTextField.font");
/* 246 */     if (fntFText == null) fntFText = fntLabel; 
/* 247 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 248 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 250 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 251 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 252 */     fntFText = fntFText.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 253 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 255 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 256 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 257 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 258 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_ATTRIBUTES"));
/* 259 */     text.setTitleFont(fntBorder);
/*     */     
/* 261 */     setBorder(text);
/*     */     
/* 263 */     if (this.lblPhysique == null) this.lblPhysique = new JLabel(); 
/* 264 */     this.lblPhysique.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_STAT_PHYSIQUE"));
/* 265 */     this.lblPhysique.setFont(fntLabel);
/*     */     
/* 267 */     if (this.ftPhysique == null) {
/* 268 */       this.ftPhysique = new JFormattedTextField();
/* 269 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(4);
/* 270 */       AbstractDocument doc = (AbstractDocument)this.ftPhysique.getDocument();
/* 271 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */     } 
/* 273 */     this.ftPhysique.setPreferredSize(new Dimension(GDStashFrame.iniConfig.sectUI.fontSize * 5, GDStashFrame.iniConfig.sectUI.fontSize * 2));
/* 274 */     this.ftPhysique.setFont(fntFText);
/*     */     
/* 276 */     if (this.btnPhysiquePlus == null) {
/* 277 */       this.btnPhysiquePlus = new JButton();
/*     */       
/* 279 */       this.btnPhysiquePlus.addActionListener(new PlusActionListener(this.ftPhysique));
/*     */     } 
/* 281 */     this.btnPhysiquePlus.setIcon(GDImagePool.iconPlus16);
/* 282 */     this.btnPhysiquePlus.setFont(fntButton);
/*     */     
/* 284 */     if (this.btnPhysiqueMinus == null) {
/* 285 */       this.btnPhysiqueMinus = new JButton();
/*     */       
/* 287 */       this.btnPhysiqueMinus.addActionListener(new MinusActionListener(this.ftPhysique));
/*     */     } 
/* 289 */     this.btnPhysiqueMinus.setIcon(GDImagePool.iconMinus16);
/* 290 */     this.btnPhysiqueMinus.setFont(fntButton);
/*     */     
/* 292 */     if (this.lblCunning == null) this.lblCunning = new JLabel(); 
/* 293 */     this.lblCunning.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_STAT_CUNNING"));
/* 294 */     this.lblCunning.setFont(fntLabel);
/*     */     
/* 296 */     if (this.ftCunning == null) {
/* 297 */       this.ftCunning = new JFormattedTextField();
/* 298 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(4);
/* 299 */       AbstractDocument doc = (AbstractDocument)this.ftCunning.getDocument();
/* 300 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */     } 
/* 302 */     this.ftCunning.setFont(fntFText);
/*     */     
/* 304 */     if (this.btnCunningPlus == null) {
/* 305 */       this.btnCunningPlus = new JButton();
/*     */       
/* 307 */       this.btnCunningPlus.addActionListener(new PlusActionListener(this.ftCunning));
/*     */     } 
/* 309 */     this.btnCunningPlus.setIcon(GDImagePool.iconPlus16);
/* 310 */     this.btnCunningPlus.setFont(fntButton);
/*     */     
/* 312 */     if (this.btnCunningMinus == null) {
/* 313 */       this.btnCunningMinus = new JButton();
/*     */       
/* 315 */       this.btnCunningMinus.addActionListener(new MinusActionListener(this.ftCunning));
/*     */     } 
/* 317 */     this.btnCunningMinus.setIcon(GDImagePool.iconMinus16);
/* 318 */     this.btnCunningMinus.setFont(fntButton);
/*     */     
/* 320 */     if (this.lblSpirit == null) this.lblSpirit = new JLabel(); 
/* 321 */     this.lblSpirit.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_STAT_SPIRIT"));
/* 322 */     this.lblSpirit.setFont(fntLabel);
/*     */     
/* 324 */     if (this.ftSpirit == null) {
/* 325 */       this.ftSpirit = new JFormattedTextField();
/* 326 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(4);
/* 327 */       AbstractDocument doc = (AbstractDocument)this.ftSpirit.getDocument();
/* 328 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */     } 
/* 330 */     this.ftSpirit.setFont(fntFText);
/*     */     
/* 332 */     if (this.btnSpiritPlus == null) {
/* 333 */       this.btnSpiritPlus = new JButton();
/*     */       
/* 335 */       this.btnSpiritPlus.addActionListener(new PlusActionListener(this.ftSpirit));
/*     */     } 
/* 337 */     this.btnSpiritPlus.setIcon(GDImagePool.iconPlus16);
/* 338 */     this.btnSpiritPlus.setFont(fntButton);
/*     */     
/* 340 */     if (this.btnSpiritMinus == null) {
/* 341 */       this.btnSpiritMinus = new JButton();
/*     */       
/* 343 */       this.btnSpiritMinus.addActionListener(new MinusActionListener(this.ftSpirit));
/*     */     } 
/* 345 */     this.btnSpiritMinus.setIcon(GDImagePool.iconMinus16);
/* 346 */     this.btnSpiritMinus.setFont(fntButton);
/*     */     
/* 348 */     if (this.lblHealth == null) this.lblHealth = new JLabel(); 
/* 349 */     this.lblHealth.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_STAT_HEALTH"));
/* 350 */     this.lblHealth.setFont(fntLabel);
/*     */     
/* 352 */     if (this.ftHealth == null) {
/* 353 */       this.ftHealth = new JFormattedTextField();
/* 354 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(5);
/* 355 */       AbstractDocument doc = (AbstractDocument)this.ftHealth.getDocument();
/* 356 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */       
/* 358 */       this.ftHealth.setEditable(false);
/*     */     } 
/* 360 */     this.ftHealth.setFont(fntFText);
/*     */     
/* 362 */     if (this.lblEnergy == null) this.lblEnergy = new JLabel(); 
/* 363 */     this.lblEnergy.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_STAT_ENERGY"));
/* 364 */     this.lblEnergy.setFont(fntLabel);
/*     */     
/* 366 */     if (this.ftEnergy == null) {
/* 367 */       this.ftEnergy = new JFormattedTextField();
/* 368 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(5);
/* 369 */       AbstractDocument doc = (AbstractDocument)this.ftEnergy.getDocument();
/* 370 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */       
/* 372 */       this.ftEnergy.setEditable(false);
/*     */     } 
/* 374 */     this.ftEnergy.setFont(fntFText);
/*     */   }
/*     */   
/*     */   private JPanel buildPlusMinusButtonPanel(JTextField textField, JButton btnPlus, JButton btnMinus) {
/* 378 */     JPanel panel = new JPanel();
/*     */     
/* 380 */     GroupLayout layout = null;
/* 381 */     GroupLayout.SequentialGroup hGroup = null;
/* 382 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 384 */     layout = new GroupLayout(panel);
/* 385 */     panel.setLayout(layout);
/*     */     
/* 387 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 390 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 393 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 396 */     hGroup
/* 397 */       .addGroup(layout.createParallelGroup()
/* 398 */         .addComponent(btnMinus))
/* 399 */       .addGroup(layout.createParallelGroup()
/* 400 */         .addComponent(textField))
/* 401 */       .addGroup(layout.createParallelGroup()
/* 402 */         .addComponent(btnPlus));
/* 403 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 406 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 409 */     vGroup
/* 410 */       .addGroup(layout.createParallelGroup()
/* 411 */         .addComponent(btnMinus)
/* 412 */         .addComponent(textField)
/* 413 */         .addComponent(btnPlus));
/* 414 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 416 */     layout.linkSize(0, new Component[] { btnMinus, btnPlus });
/*     */     
/* 418 */     layout.linkSize(1, new Component[] { btnMinus, textField });
/* 419 */     layout.linkSize(1, new Component[] { btnMinus, btnPlus });
/*     */     
/* 421 */     return panel;
/*     */   }
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 425 */     float physique = 0.0F;
/* 426 */     float cunning = 0.0F;
/* 427 */     float spirit = 0.0F;
/* 428 */     float health = 0.0F;
/* 429 */     float energy = 0.0F;
/* 430 */     if (gdc != null) {
/* 431 */       physique = gdc.getPhysique();
/* 432 */       cunning = gdc.getCunning();
/* 433 */       spirit = gdc.getSpirit();
/* 434 */       health = gdc.getHealth();
/* 435 */       energy = gdc.getEnergy();
/*     */     } 
/*     */     
/* 438 */     this.ftPhysique.setText(Integer.toString((int)physique));
/* 439 */     this.ftCunning.setText(Integer.toString((int)cunning));
/* 440 */     this.ftSpirit.setText(Integer.toString((int)spirit));
/* 441 */     this.ftHealth.setText(Integer.toString((int)health));
/* 442 */     this.ftEnergy.setText(Integer.toString((int)energy));
/*     */   }
/*     */   
/*     */   public void updateChar(GDChar gdc) {
/* 446 */     if (gdc == null)
/*     */       return; 
/* 448 */     gdc.setPhysique(Integer.parseInt(this.ftPhysique.getText()));
/* 449 */     gdc.setCunning(Integer.parseInt(this.ftCunning.getText()));
/* 450 */     gdc.setSpirit(Integer.parseInt(this.ftSpirit.getText()));
/* 451 */     gdc.setHealth(Integer.parseInt(this.ftHealth.getText()));
/* 452 */     gdc.setEnergy(Integer.parseInt(this.ftEnergy.getText()));
/*     */   }
/*     */   
/*     */   public float getHealth() {
/* 456 */     return Float.parseFloat(this.ftHealth.getText());
/*     */   }
/*     */   
/*     */   public float getEnergy() {
/* 460 */     return Float.parseFloat(this.ftEnergy.getText());
/*     */   }
/*     */   
/*     */   public void setHealth(float health) {
/* 464 */     this.ftHealth.setText(Integer.toString((int)health));
/*     */   }
/*     */   
/*     */   public void setEnergy(float energy) {
/* 468 */     this.ftEnergy.setText(Integer.toString((int)energy));
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\character\GDCharAttributePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */