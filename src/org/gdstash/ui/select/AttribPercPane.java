/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import org.gdstash.db.SelectionCriteria;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttribPercPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JCheckBox cbPhysique;
/*     */   private JCheckBox cbCunning;
/*     */   private JCheckBox cbSpirit;
/*     */   private JCheckBox cbDefense;
/*     */   private JCheckBox cbOffense;
/*     */   private JCheckBox cbHealth;
/*     */   private JCheckBox cbHealthRegen;
/*     */   private JCheckBox cbEnergyRegen;
/*     */   private JCheckBox cbXPBonus;
/*     */   private JCheckBox cbAttackSpeed;
/*     */   private JCheckBox cbCastSpeed;
/*     */   private JCheckBox cbRunSpeed;
/*     */   private JCheckBox cbTotalSpeed;
/*     */   private JCheckBox cbCooldown;
/*     */   private JCheckBox cbShieldRecovery;
/*     */   private JCheckBox cbShieldDamage;
/*     */   private JCheckBox cbArmorAbsorb;
/*     */   private JCheckBox cbAvoidMelee;
/*     */   private JCheckBox cbAvoidProjectile;
/*     */   private JCheckBox cbReducedReq;
/*     */   private DamageConversionPane pnlDmgConversion;
/*     */   
/*     */   public AttribPercPane(int direction) {
/*  50 */     adjustUI();
/*     */     
/*  52 */     JPanel pnlMain = buildMainPanel(direction);
/*     */     
/*  54 */     GroupLayout layout = null;
/*  55 */     GroupLayout.SequentialGroup hGroup = null;
/*  56 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  58 */     layout = new GroupLayout((Container)this);
/*  59 */     setLayout(layout);
/*     */     
/*  61 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/*  64 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/*  67 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  70 */     hGroup
/*  71 */       .addGroup(layout.createParallelGroup()
/*  72 */         .addComponent(pnlMain)
/*  73 */         .addComponent((Component)this.pnlDmgConversion));
/*     */     
/*  75 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  78 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  81 */     vGroup
/*  82 */       .addGroup(layout.createParallelGroup()
/*  83 */         .addComponent(pnlMain))
/*  84 */       .addGroup(layout.createParallelGroup()
/*  85 */         .addComponent((Component)this.pnlDmgConversion));
/*     */     
/*  87 */     layout.setVerticalGroup(vGroup);
/*     */     
/*  89 */     layout.linkSize(0, new Component[] { pnlMain, (Component)this.pnlDmgConversion });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  96 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  97 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/*  98 */     if (fntCheck == null) fntCheck = fntLabel; 
/*  99 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 100 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 102 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 103 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 104 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 106 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 107 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 108 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 109 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_ATTRIB_PERC"));
/* 110 */     text.setTitleFont(fntBorder);
/*     */     
/* 112 */     setBorder(text);
/*     */     
/* 114 */     if (this.cbPhysique == null) this.cbPhysique = new JCheckBox(); 
/* 115 */     this.cbPhysique.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_PHYSIQUE"));
/* 116 */     this.cbPhysique.setFont(fntCheck);
/*     */     
/* 118 */     if (this.cbCunning == null) this.cbCunning = new JCheckBox(); 
/* 119 */     this.cbCunning.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_CUNNING"));
/* 120 */     this.cbCunning.setFont(fntCheck);
/*     */     
/* 122 */     if (this.cbSpirit == null) this.cbSpirit = new JCheckBox(); 
/* 123 */     this.cbSpirit.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_SPIRIT"));
/* 124 */     this.cbSpirit.setFont(fntCheck);
/*     */     
/* 126 */     if (this.cbDefense == null) this.cbDefense = new JCheckBox(); 
/* 127 */     this.cbDefense.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_DEF_ABIL"));
/* 128 */     this.cbDefense.setFont(fntCheck);
/*     */     
/* 130 */     if (this.cbOffense == null) this.cbOffense = new JCheckBox(); 
/* 131 */     this.cbOffense.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_OFF_ABIL"));
/* 132 */     this.cbOffense.setFont(fntCheck);
/*     */     
/* 134 */     if (this.cbHealth == null) this.cbHealth = new JCheckBox(); 
/* 135 */     this.cbHealth.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_HEALTH"));
/* 136 */     this.cbHealth.setFont(fntCheck);
/*     */     
/* 138 */     if (this.cbHealthRegen == null) this.cbHealthRegen = new JCheckBox(); 
/* 139 */     this.cbHealthRegen.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_REGEN_HEALTH"));
/* 140 */     this.cbHealthRegen.setFont(fntCheck);
/*     */     
/* 142 */     if (this.cbEnergyRegen == null) this.cbEnergyRegen = new JCheckBox(); 
/* 143 */     this.cbEnergyRegen.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_REGEN_ENERGY"));
/* 144 */     this.cbEnergyRegen.setFont(fntCheck);
/*     */     
/* 146 */     if (this.cbXPBonus == null) this.cbXPBonus = new JCheckBox(); 
/* 147 */     this.cbXPBonus.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_XP_BONUS"));
/* 148 */     this.cbXPBonus.setFont(fntCheck);
/*     */     
/* 150 */     if (this.cbAttackSpeed == null) this.cbAttackSpeed = new JCheckBox(); 
/* 151 */     this.cbAttackSpeed.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_SPEED_ATTACK"));
/* 152 */     this.cbAttackSpeed.setFont(fntCheck);
/*     */     
/* 154 */     if (this.cbCastSpeed == null) this.cbCastSpeed = new JCheckBox(); 
/* 155 */     this.cbCastSpeed.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_SPEED_CAST"));
/* 156 */     this.cbCastSpeed.setFont(fntCheck);
/*     */     
/* 158 */     if (this.cbRunSpeed == null) this.cbRunSpeed = new JCheckBox(); 
/* 159 */     this.cbRunSpeed.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_SPEED_RUN"));
/* 160 */     this.cbRunSpeed.setFont(fntCheck);
/*     */     
/* 162 */     if (this.cbTotalSpeed == null) this.cbTotalSpeed = new JCheckBox(); 
/* 163 */     this.cbTotalSpeed.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_SPEED_TOTAL"));
/* 164 */     this.cbTotalSpeed.setFont(fntCheck);
/*     */     
/* 166 */     if (this.cbCooldown == null) this.cbCooldown = new JCheckBox(); 
/* 167 */     this.cbCooldown.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_COOLDOWN_REDUCTION"));
/* 168 */     this.cbCooldown.setFont(fntCheck);
/*     */     
/* 170 */     if (this.cbShieldRecovery == null) this.cbShieldRecovery = new JCheckBox(); 
/* 171 */     this.cbShieldRecovery.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_SHIELD_RECOVERY_RED"));
/* 172 */     this.cbShieldRecovery.setFont(fntCheck);
/*     */     
/* 174 */     if (this.cbShieldDamage == null) this.cbShieldDamage = new JCheckBox(); 
/* 175 */     this.cbShieldDamage.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_SHIELD_INC_DAMAGE"));
/* 176 */     this.cbShieldDamage.setFont(fntCheck);
/*     */     
/* 178 */     if (this.cbArmorAbsorb == null) this.cbArmorAbsorb = new JCheckBox(); 
/* 179 */     this.cbArmorAbsorb.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_ARMOR_ABSORB"));
/* 180 */     this.cbArmorAbsorb.setFont(fntCheck);
/*     */     
/* 182 */     if (this.cbAvoidMelee == null) this.cbAvoidMelee = new JCheckBox(); 
/* 183 */     this.cbAvoidMelee.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_AVOID_MELEE"));
/* 184 */     this.cbAvoidMelee.setFont(fntCheck);
/*     */     
/* 186 */     if (this.cbAvoidProjectile == null) this.cbAvoidProjectile = new JCheckBox(); 
/* 187 */     this.cbAvoidProjectile.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_AVOID_PROJECTILE"));
/* 188 */     this.cbAvoidProjectile.setFont(fntCheck);
/*     */     
/* 190 */     if (this.cbReducedReq == null) this.cbReducedReq = new JCheckBox(); 
/* 191 */     this.cbReducedReq.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_REQ_REDUCTION"));
/* 192 */     this.cbReducedReq.setFont(fntCheck);
/*     */     
/* 194 */     if (this.pnlDmgConversion == null) {
/* 195 */       this.pnlDmgConversion = new DamageConversionPane();
/*     */     } else {
/* 197 */       this.pnlDmgConversion.adjustUI();
/*     */     } 
/*     */   }
/*     */   
/*     */   private JPanel buildMainPanel(int direction) {
/* 202 */     JPanel panel = new JPanel();
/*     */     
/* 204 */     GroupLayout layout = null;
/* 205 */     GroupLayout.SequentialGroup hGroup = null;
/* 206 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 208 */     JPanel pnlLeft = buildLeftSidePanel(direction);
/* 209 */     JPanel pnlRight = buildRightSidePanel(direction);
/*     */ 
/*     */ 
/*     */     
/* 213 */     layout = new GroupLayout(panel);
/* 214 */     panel.setLayout(layout);
/*     */     
/* 216 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 219 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 222 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 225 */     hGroup
/* 226 */       .addGroup(layout.createParallelGroup()
/* 227 */         .addComponent(pnlLeft))
/* 228 */       .addGroup(layout.createParallelGroup()
/* 229 */         .addComponent(pnlRight));
/*     */ 
/*     */     
/* 232 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 235 */     vGroup
/* 236 */       .addGroup(layout.createParallelGroup()
/* 237 */         .addComponent(pnlLeft)
/* 238 */         .addComponent(pnlRight));
/*     */     
/* 240 */     if (direction == 0) {
/* 241 */       layout.setHorizontalGroup(vGroup);
/* 242 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 244 */       layout.setHorizontalGroup(hGroup);
/* 245 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 250 */     layout.linkSize(1, new Component[] { pnlLeft, pnlRight });
/*     */     
/* 252 */     return panel;
/*     */   }
/*     */ 
/*     */   
/*     */   private JPanel buildLeftSidePanel(int direction) {
/* 257 */     JPanel panel = new JPanel();
/*     */     
/* 259 */     GroupLayout layout = null;
/* 260 */     GroupLayout.SequentialGroup hGroup = null;
/* 261 */     GroupLayout.SequentialGroup vGroup = null;
/*     */ 
/*     */ 
/*     */     
/* 265 */     layout = new GroupLayout(panel);
/* 266 */     panel.setLayout(layout);
/*     */     
/* 268 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 271 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 274 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 277 */     hGroup
/* 278 */       .addGroup(layout.createParallelGroup()
/* 279 */         .addComponent(this.cbPhysique)
/* 280 */         .addComponent(this.cbCunning)
/* 281 */         .addComponent(this.cbSpirit)
/* 282 */         .addComponent(this.cbDefense)
/* 283 */         .addComponent(this.cbOffense)
/* 284 */         .addComponent(this.cbHealth)
/* 285 */         .addComponent(this.cbHealthRegen)
/* 286 */         .addComponent(this.cbEnergyRegen));
/*     */ 
/*     */     
/* 289 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 292 */     vGroup
/* 293 */       .addGroup(layout.createParallelGroup()
/* 294 */         .addComponent(this.cbPhysique))
/* 295 */       .addGroup(layout.createParallelGroup()
/* 296 */         .addComponent(this.cbCunning))
/* 297 */       .addGroup(layout.createParallelGroup()
/* 298 */         .addComponent(this.cbSpirit))
/* 299 */       .addGroup(layout.createParallelGroup()
/* 300 */         .addComponent(this.cbDefense))
/* 301 */       .addGroup(layout.createParallelGroup()
/* 302 */         .addComponent(this.cbOffense))
/* 303 */       .addGroup(layout.createParallelGroup()
/* 304 */         .addComponent(this.cbHealth))
/* 305 */       .addGroup(layout.createParallelGroup()
/* 306 */         .addComponent(this.cbHealthRegen))
/* 307 */       .addGroup(layout.createParallelGroup()
/* 308 */         .addComponent(this.cbEnergyRegen));
/*     */     
/* 310 */     if (direction == 0) {
/* 311 */       layout.setHorizontalGroup(vGroup);
/* 312 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 314 */       layout.setHorizontalGroup(hGroup);
/* 315 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 318 */     layout.linkSize(0, new Component[] { this.cbPhysique, this.cbCunning });
/* 319 */     layout.linkSize(0, new Component[] { this.cbPhysique, this.cbSpirit });
/* 320 */     layout.linkSize(0, new Component[] { this.cbPhysique, this.cbDefense });
/* 321 */     layout.linkSize(0, new Component[] { this.cbPhysique, this.cbOffense });
/* 322 */     layout.linkSize(0, new Component[] { this.cbPhysique, this.cbHealth });
/* 323 */     layout.linkSize(0, new Component[] { this.cbPhysique, this.cbHealthRegen });
/* 324 */     layout.linkSize(0, new Component[] { this.cbPhysique, this.cbEnergyRegen });
/*     */     
/* 326 */     layout.linkSize(1, new Component[] { this.cbPhysique, this.cbCunning });
/* 327 */     layout.linkSize(1, new Component[] { this.cbPhysique, this.cbSpirit });
/* 328 */     layout.linkSize(1, new Component[] { this.cbPhysique, this.cbDefense });
/* 329 */     layout.linkSize(1, new Component[] { this.cbPhysique, this.cbOffense });
/* 330 */     layout.linkSize(1, new Component[] { this.cbPhysique, this.cbHealth });
/* 331 */     layout.linkSize(1, new Component[] { this.cbPhysique, this.cbHealthRegen });
/* 332 */     layout.linkSize(1, new Component[] { this.cbPhysique, this.cbEnergyRegen });
/*     */     
/* 334 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildRightSidePanel(int direction) {
/* 338 */     JPanel panel = new JPanel();
/*     */     
/* 340 */     GroupLayout layout = null;
/* 341 */     GroupLayout.SequentialGroup hGroup = null;
/* 342 */     GroupLayout.SequentialGroup vGroup = null;
/*     */ 
/*     */ 
/*     */     
/* 346 */     layout = new GroupLayout(panel);
/* 347 */     panel.setLayout(layout);
/*     */     
/* 349 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 352 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 355 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 358 */     hGroup
/* 359 */       .addGroup(layout.createParallelGroup()
/* 360 */         .addComponent(this.cbXPBonus)
/* 361 */         .addComponent(this.cbAttackSpeed)
/* 362 */         .addComponent(this.cbCastSpeed)
/* 363 */         .addComponent(this.cbRunSpeed)
/* 364 */         .addComponent(this.cbTotalSpeed)
/* 365 */         .addComponent(this.cbCooldown)
/* 366 */         .addComponent(this.cbShieldRecovery)
/* 367 */         .addComponent(this.cbShieldDamage)
/* 368 */         .addComponent(this.cbArmorAbsorb)
/* 369 */         .addComponent(this.cbAvoidMelee)
/* 370 */         .addComponent(this.cbAvoidProjectile)
/* 371 */         .addComponent(this.cbReducedReq));
/*     */ 
/*     */     
/* 374 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 377 */     vGroup
/* 378 */       .addGroup(layout.createParallelGroup()
/* 379 */         .addComponent(this.cbXPBonus))
/* 380 */       .addGroup(layout.createParallelGroup()
/* 381 */         .addComponent(this.cbAttackSpeed))
/* 382 */       .addGroup(layout.createParallelGroup()
/* 383 */         .addComponent(this.cbCastSpeed))
/* 384 */       .addGroup(layout.createParallelGroup()
/* 385 */         .addComponent(this.cbRunSpeed))
/* 386 */       .addGroup(layout.createParallelGroup()
/* 387 */         .addComponent(this.cbTotalSpeed))
/* 388 */       .addGroup(layout.createParallelGroup()
/* 389 */         .addComponent(this.cbCooldown))
/* 390 */       .addGroup(layout.createParallelGroup()
/* 391 */         .addComponent(this.cbShieldRecovery))
/* 392 */       .addGroup(layout.createParallelGroup()
/* 393 */         .addComponent(this.cbShieldDamage))
/* 394 */       .addGroup(layout.createParallelGroup()
/* 395 */         .addComponent(this.cbArmorAbsorb))
/* 396 */       .addGroup(layout.createParallelGroup()
/* 397 */         .addComponent(this.cbAvoidMelee))
/* 398 */       .addGroup(layout.createParallelGroup()
/* 399 */         .addComponent(this.cbAvoidProjectile))
/* 400 */       .addGroup(layout.createParallelGroup()
/* 401 */         .addComponent(this.cbReducedReq));
/*     */     
/* 403 */     if (direction == 0) {
/* 404 */       layout.setHorizontalGroup(vGroup);
/* 405 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 407 */       layout.setHorizontalGroup(hGroup);
/* 408 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 411 */     layout.linkSize(0, new Component[] { this.cbXPBonus, this.cbAttackSpeed });
/* 412 */     layout.linkSize(0, new Component[] { this.cbXPBonus, this.cbCastSpeed });
/* 413 */     layout.linkSize(0, new Component[] { this.cbXPBonus, this.cbRunSpeed });
/* 414 */     layout.linkSize(0, new Component[] { this.cbXPBonus, this.cbTotalSpeed });
/* 415 */     layout.linkSize(0, new Component[] { this.cbXPBonus, this.cbCooldown });
/* 416 */     layout.linkSize(0, new Component[] { this.cbXPBonus, this.cbShieldRecovery });
/* 417 */     layout.linkSize(0, new Component[] { this.cbXPBonus, this.cbShieldDamage });
/* 418 */     layout.linkSize(0, new Component[] { this.cbXPBonus, this.cbArmorAbsorb });
/* 419 */     layout.linkSize(0, new Component[] { this.cbXPBonus, this.cbAvoidMelee });
/* 420 */     layout.linkSize(0, new Component[] { this.cbXPBonus, this.cbAvoidProjectile });
/* 421 */     layout.linkSize(0, new Component[] { this.cbXPBonus, this.cbReducedReq });
/*     */     
/* 423 */     layout.linkSize(1, new Component[] { this.cbXPBonus, this.cbAttackSpeed });
/* 424 */     layout.linkSize(1, new Component[] { this.cbXPBonus, this.cbCastSpeed });
/* 425 */     layout.linkSize(1, new Component[] { this.cbXPBonus, this.cbRunSpeed });
/* 426 */     layout.linkSize(1, new Component[] { this.cbXPBonus, this.cbTotalSpeed });
/* 427 */     layout.linkSize(1, new Component[] { this.cbXPBonus, this.cbCooldown });
/* 428 */     layout.linkSize(1, new Component[] { this.cbXPBonus, this.cbShieldRecovery });
/* 429 */     layout.linkSize(1, new Component[] { this.cbXPBonus, this.cbShieldDamage });
/* 430 */     layout.linkSize(1, new Component[] { this.cbXPBonus, this.cbArmorAbsorb });
/* 431 */     layout.linkSize(1, new Component[] { this.cbXPBonus, this.cbAvoidMelee });
/* 432 */     layout.linkSize(1, new Component[] { this.cbXPBonus, this.cbAvoidProjectile });
/* 433 */     layout.linkSize(1, new Component[] { this.cbXPBonus, this.cbReducedReq });
/*     */     
/* 435 */     return panel;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 439 */     this.cbPhysique.setSelected(false);
/* 440 */     this.cbCunning.setSelected(false);
/* 441 */     this.cbSpirit.setSelected(false);
/* 442 */     this.cbDefense.setSelected(false);
/* 443 */     this.cbOffense.setSelected(false);
/* 444 */     this.cbHealth.setSelected(false);
/* 445 */     this.cbHealthRegen.setSelected(false);
/* 446 */     this.cbEnergyRegen.setSelected(false);
/* 447 */     this.cbXPBonus.setSelected(false);
/* 448 */     this.cbAttackSpeed.setSelected(false);
/* 449 */     this.cbCastSpeed.setSelected(false);
/* 450 */     this.cbRunSpeed.setSelected(false);
/* 451 */     this.cbTotalSpeed.setSelected(false);
/* 452 */     this.cbCooldown.setSelected(false);
/* 453 */     this.cbShieldRecovery.setSelected(false);
/* 454 */     this.cbShieldDamage.setSelected(false);
/* 455 */     this.cbArmorAbsorb.setSelected(false);
/* 456 */     this.cbAvoidMelee.setSelected(false);
/* 457 */     this.cbAvoidProjectile.setSelected(false);
/* 458 */     this.cbReducedReq.setSelected(false);
/* 459 */     this.pnlDmgConversion.clear();
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 463 */     SelectionCriteria.StatInfo info = null;
/*     */     
/* 465 */     if (this.cbPhysique.isSelected()) {
/* 466 */       info = new SelectionCriteria.StatInfo("characterStrength", false, true);
/*     */       
/* 468 */       criteria.statInfos.add(info);
/*     */     } 
/* 470 */     if (this.cbCunning.isSelected()) {
/* 471 */       info = new SelectionCriteria.StatInfo("characterDexterity", false, true);
/*     */       
/* 473 */       criteria.statInfos.add(info);
/*     */     } 
/* 475 */     if (this.cbSpirit.isSelected()) {
/* 476 */       info = new SelectionCriteria.StatInfo("characterIntelligence", false, true);
/*     */       
/* 478 */       criteria.statInfos.add(info);
/*     */     } 
/* 480 */     if (this.cbDefense.isSelected()) {
/* 481 */       info = new SelectionCriteria.StatInfo("characterDefensiveAbility", false, true);
/*     */       
/* 483 */       criteria.statInfos.add(info);
/*     */     } 
/* 485 */     if (this.cbOffense.isSelected()) {
/* 486 */       info = new SelectionCriteria.StatInfo("characterOffensiveAbility", false, true);
/*     */       
/* 488 */       criteria.statInfos.add(info);
/*     */     } 
/* 490 */     if (this.cbHealth.isSelected()) {
/* 491 */       info = new SelectionCriteria.StatInfo("characterLife", false, true);
/*     */       
/* 493 */       criteria.statInfos.add(info);
/*     */     } 
/* 495 */     if (this.cbHealthRegen.isSelected()) {
/* 496 */       info = new SelectionCriteria.StatInfo("characterLifeRegen", false, true);
/*     */       
/* 498 */       criteria.statInfos.add(info);
/*     */     } 
/* 500 */     if (this.cbEnergyRegen.isSelected()) {
/* 501 */       info = new SelectionCriteria.StatInfo("characterManaRegen", false, true);
/*     */       
/* 503 */       criteria.statInfos.add(info);
/*     */     } 
/* 505 */     if (this.cbXPBonus.isSelected()) {
/* 506 */       info = new SelectionCriteria.StatInfo("characterIncreasedExperience", true, false);
/*     */       
/* 508 */       criteria.statInfos.add(info);
/*     */     } 
/* 510 */     if (this.cbAttackSpeed.isSelected()) {
/* 511 */       info = new SelectionCriteria.StatInfo("characterAttackSpeed", false, true);
/*     */       
/* 513 */       criteria.statInfos.add(info);
/*     */     } 
/* 515 */     if (this.cbCastSpeed.isSelected()) {
/* 516 */       info = new SelectionCriteria.StatInfo("characterSpellCastSpeed", false, true);
/*     */       
/* 518 */       criteria.statInfos.add(info);
/*     */     } 
/* 520 */     if (this.cbRunSpeed.isSelected()) {
/* 521 */       info = new SelectionCriteria.StatInfo("characterRunSpeed", false, true);
/*     */       
/* 523 */       criteria.statInfos.add(info);
/*     */     } 
/* 525 */     if (this.cbTotalSpeed.isSelected()) {
/* 526 */       info = new SelectionCriteria.StatInfo("characterTotalSpeed", false, true);
/*     */       
/* 528 */       criteria.statInfos.add(info);
/*     */     } 
/* 530 */     if (this.cbCooldown.isSelected()) {
/* 531 */       info = new SelectionCriteria.StatInfo("skillCooldownReduction", true, false);
/*     */       
/* 533 */       criteria.statInfos.add(info);
/*     */     } 
/* 535 */     if (this.cbShieldRecovery.isSelected()) {
/* 536 */       info = new SelectionCriteria.StatInfo("characterDefensiveBlockRecoveryReduction", true, false);
/*     */       
/* 538 */       criteria.statInfos.add(info);
/*     */     } 
/* 540 */     if (this.cbShieldDamage.isSelected()) {
/* 541 */       info = new SelectionCriteria.StatInfo("defensiveBlockAmount", false, true);
/*     */       
/* 543 */       criteria.statInfos.add(info);
/*     */     } 
/* 545 */     if (this.cbArmorAbsorb.isSelected()) {
/* 546 */       info = new SelectionCriteria.StatInfo("defensiveAbsorption", false, true);
/*     */       
/* 548 */       criteria.statInfos.add(info);
/*     */     } 
/* 550 */     if (this.cbAvoidMelee.isSelected()) {
/* 551 */       info = new SelectionCriteria.StatInfo("characterDodgePercent", true, false);
/*     */       
/* 553 */       criteria.statInfos.add(info);
/*     */     } 
/* 555 */     if (this.cbAvoidProjectile.isSelected()) {
/* 556 */       info = new SelectionCriteria.StatInfo("characterDeflectProjectile", true, false);
/*     */       
/* 558 */       criteria.statInfos.add(info);
/*     */     } 
/* 560 */     if (this.cbReducedReq.isSelected()) {
/*     */       
/* 562 */       info = new SelectionCriteria.StatInfo("characterGlobalReqReduction", true, false, SelectionCriteria.ComboType.OR);
/* 563 */       criteria.statInfos.add(info);
/*     */       
/* 565 */       info = new SelectionCriteria.StatInfo("characterLevelReqReduction", true, false, SelectionCriteria.ComboType.OR);
/* 566 */       criteria.statInfos.add(info);
/*     */       
/* 568 */       info = new SelectionCriteria.StatInfo("characterWeaponStrengthReqReduction", true, false, SelectionCriteria.ComboType.OR);
/* 569 */       criteria.statInfos.add(info);
/*     */       
/* 571 */       info = new SelectionCriteria.StatInfo("characterWeaponIntelligenceReqReduction", true, false, SelectionCriteria.ComboType.OR);
/* 572 */       criteria.statInfos.add(info);
/*     */       
/* 574 */       info = new SelectionCriteria.StatInfo("characterMeleeStrengthReqReduction", true, false, SelectionCriteria.ComboType.OR);
/* 575 */       criteria.statInfos.add(info);
/*     */       
/* 577 */       info = new SelectionCriteria.StatInfo("characterMeleeDexterityReqReduction", true, false, SelectionCriteria.ComboType.OR);
/* 578 */       criteria.statInfos.add(info);
/*     */       
/* 580 */       info = new SelectionCriteria.StatInfo("characterHuntingDexterityReqReduction", true, false, SelectionCriteria.ComboType.OR);
/* 581 */       criteria.statInfos.add(info);
/*     */       
/* 583 */       info = new SelectionCriteria.StatInfo("characterShieldStrengthReqReduction", true, false, SelectionCriteria.ComboType.OR);
/* 584 */       criteria.statInfos.add(info);
/*     */       
/* 586 */       info = new SelectionCriteria.StatInfo("characterArmorStrengthReqReduction", true, false, SelectionCriteria.ComboType.OR);
/* 587 */       criteria.statInfos.add(info);
/*     */       
/* 589 */       info = new SelectionCriteria.StatInfo("characterJewelryIntelligenceReqReduction", true, false, SelectionCriteria.ComboType.OR);
/* 590 */       criteria.statInfos.add(info);
/*     */     } 
/*     */     
/* 593 */     this.pnlDmgConversion.addCriteria(criteria);
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\AttribPercPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */