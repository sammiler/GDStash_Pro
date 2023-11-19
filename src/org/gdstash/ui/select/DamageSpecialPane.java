/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JCheckBox;
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
/*     */ 
/*     */ public class DamageSpecialPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JCheckBox cbTotal;
/*     */   private JCheckBox cbCritical;
/*     */   private JCheckBox cbLifeLeech;
/*     */   private JCheckBox cbReflect;
/*     */   private JCheckBox cbProjectilePierce;
/*     */   private JCheckBox cbAttackSpeed;
/*     */   private JCheckBox cbTotalSpeed;
/*     */   private JCheckBox cbDefenseAbil;
/*     */   private JCheckBox cbOffenseAbil;
/*     */   private JCheckBox cbResRedAbsolute;
/*     */   private JCheckBox cbResRedPercent;
/*     */   private JCheckBox cbRetalReflect;
/*     */   private JCheckBox cbManaCostRed;
/*     */   private JCheckBox cbPetBonus;
/*     */   
/*     */   public DamageSpecialPane() {
/*  43 */     this(1);
/*     */   }
/*     */   
/*     */   public DamageSpecialPane(int direction) {
/*  47 */     adjustUI();
/*     */     
/*  49 */     GroupLayout layout = null;
/*  50 */     GroupLayout.SequentialGroup hGroup = null;
/*  51 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  53 */     layout = new GroupLayout((Container)this);
/*  54 */     setLayout(layout);
/*     */     
/*  56 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  59 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  62 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  65 */     hGroup
/*  66 */       .addGroup(layout.createParallelGroup()
/*  67 */         .addComponent(this.cbTotal)
/*  68 */         .addComponent(this.cbCritical)
/*  69 */         .addComponent(this.cbLifeLeech)
/*  70 */         .addComponent(this.cbReflect)
/*  71 */         .addComponent(this.cbProjectilePierce)
/*  72 */         .addComponent(this.cbAttackSpeed)
/*  73 */         .addComponent(this.cbTotalSpeed)
/*  74 */         .addComponent(this.cbDefenseAbil)
/*  75 */         .addComponent(this.cbOffenseAbil)
/*  76 */         .addComponent(this.cbResRedAbsolute)
/*  77 */         .addComponent(this.cbResRedPercent)
/*  78 */         .addComponent(this.cbRetalReflect)
/*  79 */         .addComponent(this.cbManaCostRed)
/*  80 */         .addComponent(this.cbPetBonus));
/*     */ 
/*     */     
/*  83 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  86 */     vGroup
/*  87 */       .addGroup(layout.createParallelGroup()
/*  88 */         .addComponent(this.cbTotal))
/*  89 */       .addGroup(layout.createParallelGroup()
/*  90 */         .addComponent(this.cbCritical))
/*  91 */       .addGroup(layout.createParallelGroup()
/*  92 */         .addComponent(this.cbLifeLeech))
/*  93 */       .addGroup(layout.createParallelGroup()
/*  94 */         .addComponent(this.cbReflect))
/*  95 */       .addGroup(layout.createParallelGroup()
/*  96 */         .addComponent(this.cbProjectilePierce))
/*  97 */       .addGroup(layout.createParallelGroup()
/*  98 */         .addComponent(this.cbAttackSpeed))
/*  99 */       .addGroup(layout.createParallelGroup()
/* 100 */         .addComponent(this.cbTotalSpeed))
/* 101 */       .addGroup(layout.createParallelGroup()
/* 102 */         .addComponent(this.cbDefenseAbil))
/* 103 */       .addGroup(layout.createParallelGroup()
/* 104 */         .addComponent(this.cbOffenseAbil))
/* 105 */       .addGroup(layout.createParallelGroup()
/* 106 */         .addComponent(this.cbResRedAbsolute))
/* 107 */       .addGroup(layout.createParallelGroup()
/* 108 */         .addComponent(this.cbResRedPercent))
/* 109 */       .addGroup(layout.createParallelGroup()
/* 110 */         .addComponent(this.cbRetalReflect))
/* 111 */       .addGroup(layout.createParallelGroup()
/* 112 */         .addComponent(this.cbManaCostRed))
/* 113 */       .addGroup(layout.createParallelGroup()
/* 114 */         .addComponent(this.cbPetBonus));
/*     */     
/* 116 */     if (direction == 0) {
/* 117 */       layout.setHorizontalGroup(vGroup);
/* 118 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 120 */       layout.setHorizontalGroup(hGroup);
/* 121 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 124 */     layout.linkSize(0, new Component[] { this.cbTotal, this.cbLifeLeech });
/* 125 */     layout.linkSize(0, new Component[] { this.cbTotal, this.cbLifeLeech });
/* 126 */     layout.linkSize(0, new Component[] { this.cbTotal, this.cbReflect });
/* 127 */     layout.linkSize(0, new Component[] { this.cbTotal, this.cbProjectilePierce });
/* 128 */     layout.linkSize(0, new Component[] { this.cbTotal, this.cbAttackSpeed });
/* 129 */     layout.linkSize(0, new Component[] { this.cbTotal, this.cbTotalSpeed });
/* 130 */     layout.linkSize(0, new Component[] { this.cbTotal, this.cbDefenseAbil });
/* 131 */     layout.linkSize(0, new Component[] { this.cbTotal, this.cbOffenseAbil });
/* 132 */     layout.linkSize(0, new Component[] { this.cbTotal, this.cbOffenseAbil });
/* 133 */     layout.linkSize(0, new Component[] { this.cbTotal, this.cbResRedPercent });
/* 134 */     layout.linkSize(0, new Component[] { this.cbTotal, this.cbRetalReflect });
/* 135 */     layout.linkSize(0, new Component[] { this.cbTotal, this.cbManaCostRed });
/* 136 */     layout.linkSize(0, new Component[] { this.cbTotal, this.cbPetBonus });
/*     */     
/* 138 */     layout.linkSize(1, new Component[] { this.cbTotal, this.cbCritical });
/* 139 */     layout.linkSize(1, new Component[] { this.cbTotal, this.cbLifeLeech });
/* 140 */     layout.linkSize(1, new Component[] { this.cbTotal, this.cbReflect });
/* 141 */     layout.linkSize(1, new Component[] { this.cbTotal, this.cbProjectilePierce });
/* 142 */     layout.linkSize(1, new Component[] { this.cbTotal, this.cbAttackSpeed });
/* 143 */     layout.linkSize(1, new Component[] { this.cbTotal, this.cbTotalSpeed });
/* 144 */     layout.linkSize(1, new Component[] { this.cbTotal, this.cbDefenseAbil });
/* 145 */     layout.linkSize(1, new Component[] { this.cbTotal, this.cbOffenseAbil });
/* 146 */     layout.linkSize(1, new Component[] { this.cbTotal, this.cbResRedAbsolute });
/* 147 */     layout.linkSize(1, new Component[] { this.cbTotal, this.cbResRedPercent });
/* 148 */     layout.linkSize(1, new Component[] { this.cbTotal, this.cbRetalReflect });
/* 149 */     layout.linkSize(1, new Component[] { this.cbTotal, this.cbManaCostRed });
/* 150 */     layout.linkSize(1, new Component[] { this.cbTotal, this.cbPetBonus });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 155 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 156 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 157 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 158 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 159 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 161 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 162 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 163 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 165 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 166 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 167 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 168 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_DMG_SPECIAL"));
/* 169 */     text.setTitleFont(fntBorder);
/*     */     
/* 171 */     setBorder(text);
/*     */     
/* 173 */     if (this.cbTotal == null) this.cbTotal = new JCheckBox(); 
/* 174 */     this.cbTotal.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_TOTAL"));
/* 175 */     this.cbTotal.setFont(fntCheck);
/*     */     
/* 177 */     if (this.cbCritical == null) this.cbCritical = new JCheckBox(); 
/* 178 */     this.cbCritical.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_CRITICAL"));
/* 179 */     this.cbCritical.setFont(fntCheck);
/*     */     
/* 181 */     if (this.cbLifeLeech == null) this.cbLifeLeech = new JCheckBox(); 
/* 182 */     this.cbLifeLeech.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_LIFELEECH"));
/* 183 */     this.cbLifeLeech.setFont(fntCheck);
/*     */     
/* 185 */     if (this.cbReflect == null) this.cbReflect = new JCheckBox(); 
/* 186 */     this.cbReflect.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_REFLECT"));
/* 187 */     this.cbReflect.setFont(fntCheck);
/*     */     
/* 189 */     if (this.cbProjectilePierce == null) this.cbProjectilePierce = new JCheckBox(); 
/* 190 */     this.cbProjectilePierce.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_PROJECTILE_PIERCE"));
/* 191 */     this.cbProjectilePierce.setFont(fntCheck);
/*     */     
/* 193 */     if (this.cbAttackSpeed == null) this.cbAttackSpeed = new JCheckBox(); 
/* 194 */     this.cbAttackSpeed.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_SLOW_ATTACK"));
/* 195 */     this.cbAttackSpeed.setFont(fntCheck);
/*     */     
/* 197 */     if (this.cbTotalSpeed == null) this.cbTotalSpeed = new JCheckBox(); 
/* 198 */     this.cbTotalSpeed.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_SLOW_TOTAL"));
/* 199 */     this.cbTotalSpeed.setFont(fntCheck);
/*     */     
/* 201 */     if (this.cbDefenseAbil == null) this.cbDefenseAbil = new JCheckBox(); 
/* 202 */     this.cbDefenseAbil.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_SLOW_DA"));
/* 203 */     this.cbDefenseAbil.setFont(fntCheck);
/*     */     
/* 205 */     if (this.cbOffenseAbil == null) this.cbOffenseAbil = new JCheckBox(); 
/* 206 */     this.cbOffenseAbil.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_SLOW_OA"));
/* 207 */     this.cbOffenseAbil.setFont(fntCheck);
/*     */     
/* 209 */     if (this.cbResRedAbsolute == null) this.cbResRedAbsolute = new JCheckBox(); 
/* 210 */     this.cbResRedAbsolute.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_RES_RED_ABS"));
/* 211 */     this.cbResRedAbsolute.setFont(fntCheck);
/*     */     
/* 213 */     if (this.cbResRedPercent == null) this.cbResRedPercent = new JCheckBox(); 
/* 214 */     this.cbResRedPercent.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_RES_RED_PERC"));
/* 215 */     this.cbResRedPercent.setFont(fntCheck);
/*     */     
/* 217 */     if (this.cbRetalReflect == null) this.cbRetalReflect = new JCheckBox(); 
/* 218 */     this.cbRetalReflect.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_RETAL_REFLECT"));
/* 219 */     this.cbRetalReflect.setFont(fntCheck);
/*     */     
/* 221 */     if (this.cbManaCostRed == null) this.cbManaCostRed = new JCheckBox(); 
/* 222 */     this.cbManaCostRed.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_MANACOST_REDUCTION"));
/* 223 */     this.cbManaCostRed.setFont(fntCheck);
/*     */     
/* 225 */     if (this.cbPetBonus == null) this.cbPetBonus = new JCheckBox(); 
/* 226 */     this.cbPetBonus.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_PET_BONUS"));
/* 227 */     this.cbPetBonus.setFont(fntCheck);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 231 */     this.cbTotal.setSelected(false);
/* 232 */     this.cbCritical.setSelected(false);
/* 233 */     this.cbLifeLeech.setSelected(false);
/* 234 */     this.cbReflect.setSelected(false);
/* 235 */     this.cbAttackSpeed.setSelected(false);
/* 236 */     this.cbTotalSpeed.setSelected(false);
/* 237 */     this.cbDefenseAbil.setSelected(false);
/* 238 */     this.cbOffenseAbil.setSelected(false);
/* 239 */     this.cbResRedAbsolute.setSelected(false);
/* 240 */     this.cbResRedPercent.setSelected(false);
/* 241 */     this.cbRetalReflect.setSelected(false);
/* 242 */     this.cbManaCostRed.setSelected(false);
/* 243 */     this.cbPetBonus.setSelected(false);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 247 */     if (this.cbTotal.isSelected()) {
/* 248 */       SelectionCriteria.addStatInfos(criteria, 501);
/*     */     }
/* 250 */     if (this.cbCritical.isSelected()) {
/* 251 */       SelectionCriteria.addStatInfos(criteria, 502);
/*     */     }
/* 253 */     if (this.cbLifeLeech.isSelected()) {
/* 254 */       SelectionCriteria.addStatInfos(criteria, 503);
/*     */     }
/* 256 */     if (this.cbReflect.isSelected()) {
/* 257 */       SelectionCriteria.addStatInfos(criteria, 504);
/*     */     }
/* 259 */     if (this.cbProjectilePierce.isSelected()) {
/* 260 */       SelectionCriteria.addStatInfos(criteria, 509);
/*     */     }
/* 262 */     if (this.cbAttackSpeed.isSelected()) {
/* 263 */       SelectionCriteria.addStatInfos(criteria, 505);
/*     */     }
/* 265 */     if (this.cbTotalSpeed.isSelected()) {
/* 266 */       SelectionCriteria.addStatInfos(criteria, 506);
/*     */     }
/* 268 */     if (this.cbDefenseAbil.isSelected()) {
/* 269 */       SelectionCriteria.addStatInfos(criteria, 507);
/*     */     }
/* 271 */     if (this.cbOffenseAbil.isSelected()) {
/* 272 */       SelectionCriteria.addStatInfos(criteria, 508);
/*     */     }
/* 274 */     if (this.cbResRedAbsolute.isSelected()) {
/* 275 */       SelectionCriteria.addStatInfos(criteria, 510);
/* 276 */       SelectionCriteria.addStatInfos(criteria, 512);
/* 277 */       SelectionCriteria.addStatInfos(criteria, 514);
/*     */     } 
/* 279 */     if (this.cbResRedPercent.isSelected()) {
/* 280 */       SelectionCriteria.addStatInfos(criteria, 511);
/* 281 */       SelectionCriteria.addStatInfos(criteria, 513);
/* 282 */       SelectionCriteria.addStatInfos(criteria, 515);
/*     */     } 
/* 284 */     if (this.cbRetalReflect.isSelected()) {
/* 285 */       SelectionCriteria.addStatInfos(criteria, 516);
/*     */     }
/* 287 */     if (this.cbManaCostRed.isSelected()) {
/* 288 */       SelectionCriteria.addStatInfos(criteria, 517);
/*     */     }
/* 290 */     criteria.petBonus = this.cbPetBonus.isSelected();
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\select\DamageSpecialPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */