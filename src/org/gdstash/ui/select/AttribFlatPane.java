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
/*     */ public class AttribFlatPane
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
/*     */   
/*     */   public AttribFlatPane(int direction) {
/*  37 */     adjustUI();
/*     */     
/*  39 */     GroupLayout layout = null;
/*  40 */     GroupLayout.SequentialGroup hGroup = null;
/*  41 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  43 */     layout = new GroupLayout((Container)this);
/*  44 */     setLayout(layout);
/*     */     
/*  46 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  49 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  52 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  55 */     hGroup
/*  56 */       .addGroup(layout.createParallelGroup()
/*  57 */         .addComponent(this.cbPhysique)
/*  58 */         .addComponent(this.cbCunning)
/*  59 */         .addComponent(this.cbSpirit)
/*  60 */         .addComponent(this.cbDefense)
/*  61 */         .addComponent(this.cbOffense)
/*  62 */         .addComponent(this.cbHealth)
/*  63 */         .addComponent(this.cbHealthRegen)
/*  64 */         .addComponent(this.cbEnergyRegen));
/*     */ 
/*     */     
/*  67 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  70 */     vGroup
/*  71 */       .addGroup(layout.createParallelGroup()
/*  72 */         .addComponent(this.cbPhysique))
/*  73 */       .addGroup(layout.createParallelGroup()
/*  74 */         .addComponent(this.cbCunning))
/*  75 */       .addGroup(layout.createParallelGroup()
/*  76 */         .addComponent(this.cbSpirit))
/*  77 */       .addGroup(layout.createParallelGroup()
/*  78 */         .addComponent(this.cbDefense))
/*  79 */       .addGroup(layout.createParallelGroup()
/*  80 */         .addComponent(this.cbOffense))
/*  81 */       .addGroup(layout.createParallelGroup()
/*  82 */         .addComponent(this.cbHealth))
/*  83 */       .addGroup(layout.createParallelGroup()
/*  84 */         .addComponent(this.cbHealthRegen))
/*  85 */       .addGroup(layout.createParallelGroup()
/*  86 */         .addComponent(this.cbEnergyRegen));
/*     */     
/*  88 */     if (direction == 0) {
/*  89 */       layout.setHorizontalGroup(vGroup);
/*  90 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/*  92 */       layout.setHorizontalGroup(hGroup);
/*  93 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/*  96 */     layout.linkSize(0, new Component[] { this.cbPhysique, this.cbCunning });
/*  97 */     layout.linkSize(0, new Component[] { this.cbPhysique, this.cbSpirit });
/*  98 */     layout.linkSize(0, new Component[] { this.cbPhysique, this.cbDefense });
/*  99 */     layout.linkSize(0, new Component[] { this.cbPhysique, this.cbOffense });
/* 100 */     layout.linkSize(0, new Component[] { this.cbPhysique, this.cbHealth });
/* 101 */     layout.linkSize(0, new Component[] { this.cbPhysique, this.cbHealthRegen });
/* 102 */     layout.linkSize(0, new Component[] { this.cbPhysique, this.cbEnergyRegen });
/*     */     
/* 104 */     layout.linkSize(1, new Component[] { this.cbPhysique, this.cbCunning });
/* 105 */     layout.linkSize(1, new Component[] { this.cbPhysique, this.cbSpirit });
/* 106 */     layout.linkSize(1, new Component[] { this.cbPhysique, this.cbDefense });
/* 107 */     layout.linkSize(1, new Component[] { this.cbPhysique, this.cbOffense });
/* 108 */     layout.linkSize(1, new Component[] { this.cbPhysique, this.cbHealth });
/* 109 */     layout.linkSize(1, new Component[] { this.cbPhysique, this.cbHealthRegen });
/* 110 */     layout.linkSize(1, new Component[] { this.cbPhysique, this.cbEnergyRegen });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 115 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 116 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 117 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 118 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 119 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 121 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 122 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 123 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 125 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 126 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 127 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 128 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_ATTRIB_ABS"));
/* 129 */     text.setTitleFont(fntBorder);
/*     */     
/* 131 */     setBorder(text);
/*     */     
/* 133 */     if (this.cbPhysique == null) this.cbPhysique = new JCheckBox(); 
/* 134 */     this.cbPhysique.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_PHYSIQUE"));
/* 135 */     this.cbPhysique.setFont(fntCheck);
/*     */     
/* 137 */     if (this.cbCunning == null) this.cbCunning = new JCheckBox(); 
/* 138 */     this.cbCunning.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_CUNNING"));
/* 139 */     this.cbCunning.setFont(fntCheck);
/*     */     
/* 141 */     if (this.cbSpirit == null) this.cbSpirit = new JCheckBox(); 
/* 142 */     this.cbSpirit.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_SPIRIT"));
/* 143 */     this.cbSpirit.setFont(fntCheck);
/*     */     
/* 145 */     if (this.cbDefense == null) this.cbDefense = new JCheckBox(); 
/* 146 */     this.cbDefense.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_DEF_ABIL"));
/* 147 */     this.cbDefense.setFont(fntCheck);
/*     */     
/* 149 */     if (this.cbOffense == null) this.cbOffense = new JCheckBox(); 
/* 150 */     this.cbOffense.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_OFF_ABIL"));
/* 151 */     this.cbOffense.setFont(fntCheck);
/*     */     
/* 153 */     if (this.cbHealth == null) this.cbHealth = new JCheckBox(); 
/* 154 */     this.cbHealth.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_HEALTH"));
/* 155 */     this.cbHealth.setFont(fntCheck);
/*     */     
/* 157 */     if (this.cbHealthRegen == null) this.cbHealthRegen = new JCheckBox(); 
/* 158 */     this.cbHealthRegen.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_REGEN_HEALTH"));
/* 159 */     this.cbHealthRegen.setFont(fntCheck);
/*     */     
/* 161 */     if (this.cbEnergyRegen == null) this.cbEnergyRegen = new JCheckBox(); 
/* 162 */     this.cbEnergyRegen.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "STAT_REGEN_ENERGY"));
/* 163 */     this.cbEnergyRegen.setFont(fntCheck);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 167 */     this.cbCunning.setSelected(false);
/* 168 */     this.cbPhysique.setSelected(false);
/* 169 */     this.cbSpirit.setSelected(false);
/* 170 */     this.cbDefense.setSelected(false);
/* 171 */     this.cbOffense.setSelected(false);
/* 172 */     this.cbHealth.setSelected(false);
/* 173 */     this.cbHealthRegen.setSelected(false);
/* 174 */     this.cbEnergyRegen.setSelected(false);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 178 */     SelectionCriteria.StatInfo info = null;
/*     */     
/* 180 */     if (this.cbPhysique.isSelected()) {
/* 181 */       info = new SelectionCriteria.StatInfo("characterStrength", true, false);
/*     */       
/* 183 */       criteria.statInfos.add(info);
/*     */     } 
/* 185 */     if (this.cbCunning.isSelected()) {
/* 186 */       info = new SelectionCriteria.StatInfo("characterDexterity", true, false);
/*     */       
/* 188 */       criteria.statInfos.add(info);
/*     */     } 
/* 190 */     if (this.cbSpirit.isSelected()) {
/* 191 */       info = new SelectionCriteria.StatInfo("characterIntelligence", true, false);
/*     */       
/* 193 */       criteria.statInfos.add(info);
/*     */     } 
/* 195 */     if (this.cbDefense.isSelected()) {
/* 196 */       info = new SelectionCriteria.StatInfo("characterDefensiveAbility", true, false);
/*     */       
/* 198 */       criteria.statInfos.add(info);
/*     */     } 
/* 200 */     if (this.cbOffense.isSelected()) {
/* 201 */       info = new SelectionCriteria.StatInfo("characterOffensiveAbility", true, false);
/*     */       
/* 203 */       criteria.statInfos.add(info);
/*     */     } 
/* 205 */     if (this.cbHealth.isSelected()) {
/* 206 */       info = new SelectionCriteria.StatInfo("characterLife", true, false);
/*     */       
/* 208 */       criteria.statInfos.add(info);
/*     */     } 
/* 210 */     if (this.cbHealthRegen.isSelected()) {
/* 211 */       info = new SelectionCriteria.StatInfo("characterLifeRegen", true, false);
/*     */       
/* 213 */       criteria.statInfos.add(info);
/*     */     } 
/* 215 */     if (this.cbEnergyRegen.isSelected()) {
/* 216 */       info = new SelectionCriteria.StatInfo("characterManaRegen", true, false);
/*     */       
/* 218 */       criteria.statInfos.add(info);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\AttribFlatPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */