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
/*     */ public class DamageFlatPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JCheckBox cbPoison;
/*     */   private JCheckBox cbAether;
/*     */   private JCheckBox cbChaos;
/*     */   private JCheckBox cbCold;
/*     */   private JCheckBox cbElemental;
/*     */   private JCheckBox cbFire;
/*     */   private JCheckBox cbLightning;
/*     */   private JCheckBox cbPhysical;
/*     */   private JCheckBox cbPierce;
/*     */   private JCheckBox cbLife;
/*     */   
/*     */   public DamageFlatPane() {
/*  38 */     this(1);
/*     */   }
/*     */   
/*     */   public DamageFlatPane(int direction) {
/*  42 */     adjustUI();
/*     */     
/*  44 */     GroupLayout layout = null;
/*  45 */     GroupLayout.SequentialGroup hGroup = null;
/*  46 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  48 */     layout = new GroupLayout((Container)this);
/*  49 */     setLayout(layout);
/*     */     
/*  51 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  54 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  57 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  60 */     hGroup
/*  61 */       .addGroup(layout.createParallelGroup()
/*  62 */         .addComponent(this.cbPoison)
/*  63 */         .addComponent(this.cbAether)
/*  64 */         .addComponent(this.cbChaos)
/*  65 */         .addComponent(this.cbCold)
/*  66 */         .addComponent(this.cbElemental)
/*  67 */         .addComponent(this.cbFire)
/*  68 */         .addComponent(this.cbLightning)
/*  69 */         .addComponent(this.cbPhysical)
/*  70 */         .addComponent(this.cbPierce)
/*  71 */         .addComponent(this.cbLife));
/*     */ 
/*     */     
/*  74 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  77 */     vGroup
/*  78 */       .addGroup(layout.createParallelGroup()
/*  79 */         .addComponent(this.cbPoison))
/*  80 */       .addGroup(layout.createParallelGroup()
/*  81 */         .addComponent(this.cbAether))
/*  82 */       .addGroup(layout.createParallelGroup()
/*  83 */         .addComponent(this.cbChaos))
/*  84 */       .addGroup(layout.createParallelGroup()
/*  85 */         .addComponent(this.cbCold))
/*  86 */       .addGroup(layout.createParallelGroup()
/*  87 */         .addComponent(this.cbElemental))
/*  88 */       .addGroup(layout.createParallelGroup()
/*  89 */         .addComponent(this.cbFire))
/*  90 */       .addGroup(layout.createParallelGroup()
/*  91 */         .addComponent(this.cbLightning))
/*  92 */       .addGroup(layout.createParallelGroup()
/*  93 */         .addComponent(this.cbPhysical))
/*  94 */       .addGroup(layout.createParallelGroup()
/*  95 */         .addComponent(this.cbPierce))
/*  96 */       .addGroup(layout.createParallelGroup()
/*  97 */         .addComponent(this.cbLife));
/*     */     
/*  99 */     if (direction == 0) {
/* 100 */       layout.setHorizontalGroup(vGroup);
/* 101 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 103 */       layout.setHorizontalGroup(hGroup);
/* 104 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 107 */     layout.linkSize(0, new Component[] { this.cbPoison, this.cbAether });
/* 108 */     layout.linkSize(0, new Component[] { this.cbPoison, this.cbChaos });
/* 109 */     layout.linkSize(0, new Component[] { this.cbPoison, this.cbCold });
/* 110 */     layout.linkSize(0, new Component[] { this.cbPoison, this.cbElemental });
/* 111 */     layout.linkSize(0, new Component[] { this.cbPoison, this.cbFire });
/* 112 */     layout.linkSize(0, new Component[] { this.cbPoison, this.cbLightning });
/* 113 */     layout.linkSize(0, new Component[] { this.cbPoison, this.cbPhysical });
/* 114 */     layout.linkSize(0, new Component[] { this.cbPoison, this.cbPierce });
/* 115 */     layout.linkSize(0, new Component[] { this.cbPoison, this.cbLife });
/*     */     
/* 117 */     layout.linkSize(1, new Component[] { this.cbPoison, this.cbAether });
/* 118 */     layout.linkSize(1, new Component[] { this.cbPoison, this.cbChaos });
/* 119 */     layout.linkSize(1, new Component[] { this.cbPoison, this.cbCold });
/* 120 */     layout.linkSize(1, new Component[] { this.cbPoison, this.cbElemental });
/* 121 */     layout.linkSize(1, new Component[] { this.cbPoison, this.cbFire });
/* 122 */     layout.linkSize(1, new Component[] { this.cbPoison, this.cbLightning });
/* 123 */     layout.linkSize(1, new Component[] { this.cbPoison, this.cbPhysical });
/* 124 */     layout.linkSize(1, new Component[] { this.cbPoison, this.cbPierce });
/* 125 */     layout.linkSize(1, new Component[] { this.cbPoison, this.cbLife });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 130 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 131 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 132 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 133 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 134 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 136 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 137 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 138 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 140 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 141 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 142 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 143 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_DMG_FLAT"));
/* 144 */     text.setTitleFont(fntBorder);
/*     */     
/* 146 */     setBorder(text);
/*     */     
/* 148 */     if (this.cbPoison == null) this.cbPoison = new JCheckBox(); 
/* 149 */     this.cbPoison.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_ACID"));
/* 150 */     this.cbPoison.setFont(fntCheck);
/*     */     
/* 152 */     if (this.cbAether == null) this.cbAether = new JCheckBox(); 
/* 153 */     this.cbAether.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_AETHER"));
/* 154 */     this.cbAether.setFont(fntCheck);
/*     */     
/* 156 */     if (this.cbChaos == null) this.cbChaos = new JCheckBox(); 
/* 157 */     this.cbChaos.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_CHAOS"));
/* 158 */     this.cbChaos.setFont(fntCheck);
/*     */     
/* 160 */     if (this.cbCold == null) this.cbCold = new JCheckBox(); 
/* 161 */     this.cbCold.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_COLD"));
/* 162 */     this.cbCold.setFont(fntCheck);
/*     */     
/* 164 */     if (this.cbElemental == null) this.cbElemental = new JCheckBox(); 
/* 165 */     this.cbElemental.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_ELEMENTAL"));
/* 166 */     this.cbElemental.setFont(fntCheck);
/*     */     
/* 168 */     if (this.cbFire == null) this.cbFire = new JCheckBox(); 
/* 169 */     this.cbFire.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_FIRE"));
/* 170 */     this.cbFire.setFont(fntCheck);
/*     */     
/* 172 */     if (this.cbLightning == null) this.cbLightning = new JCheckBox(); 
/* 173 */     this.cbLightning.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_LIGHTNING"));
/* 174 */     this.cbLightning.setFont(fntCheck);
/*     */     
/* 176 */     if (this.cbPhysical == null) this.cbPhysical = new JCheckBox(); 
/* 177 */     this.cbPhysical.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_PHYSICAL"));
/* 178 */     this.cbPhysical.setFont(fntCheck);
/*     */     
/* 180 */     if (this.cbPierce == null) this.cbPierce = new JCheckBox(); 
/* 181 */     this.cbPierce.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_PIERCE"));
/* 182 */     this.cbPierce.setFont(fntCheck);
/*     */     
/* 184 */     if (this.cbLife == null) this.cbLife = new JCheckBox(); 
/* 185 */     this.cbLife.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_VITALITY"));
/* 186 */     this.cbLife.setFont(fntCheck);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 190 */     this.cbPoison.setSelected(false);
/* 191 */     this.cbAether.setSelected(false);
/* 192 */     this.cbChaos.setSelected(false);
/* 193 */     this.cbCold.setSelected(false);
/* 194 */     this.cbElemental.setSelected(false);
/* 195 */     this.cbFire.setSelected(false);
/* 196 */     this.cbLightning.setSelected(false);
/* 197 */     this.cbPhysical.setSelected(false);
/* 198 */     this.cbPierce.setSelected(false);
/* 199 */     this.cbLife.setSelected(false);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 203 */     if (this.cbPoison.isSelected()) {
/* 204 */       SelectionCriteria.addStatInfos(criteria, 301);
/*     */     }
/* 206 */     if (this.cbAether.isSelected()) {
/* 207 */       SelectionCriteria.addStatInfos(criteria, 302);
/*     */     }
/* 209 */     if (this.cbChaos.isSelected()) {
/* 210 */       SelectionCriteria.addStatInfos(criteria, 303);
/*     */     }
/* 212 */     if (this.cbCold.isSelected()) {
/* 213 */       SelectionCriteria.addStatInfos(criteria, 304);
/*     */     }
/* 215 */     if (this.cbElemental.isSelected()) {
/* 216 */       SelectionCriteria.addStatInfos(criteria, 305);
/*     */     }
/* 218 */     if (this.cbFire.isSelected()) {
/* 219 */       SelectionCriteria.addStatInfos(criteria, 306);
/*     */     }
/* 221 */     if (this.cbLightning.isSelected()) {
/* 222 */       SelectionCriteria.addStatInfos(criteria, 308);
/*     */     }
/* 224 */     if (this.cbPhysical.isSelected()) {
/* 225 */       SelectionCriteria.addStatInfos(criteria, 309);
/*     */     }
/* 227 */     if (this.cbPierce.isSelected()) {
/* 228 */       SelectionCriteria.addStatInfos(criteria, 310);
/*     */     }
/* 230 */     if (this.cbLife.isSelected())
/* 231 */       SelectionCriteria.addStatInfos(criteria, 307); 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\select\DamageFlatPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */