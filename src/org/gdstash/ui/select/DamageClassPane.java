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
/*     */ public class DamageClassPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JCheckBox cbOffense;
/*     */   private JCheckBox cbDefense;
/*     */   private JCheckBox cbRetal;
/*     */   
/*     */   public DamageClassPane(int direction) {
/*  30 */     adjustUI();
/*     */     
/*  32 */     GroupLayout layout = null;
/*  33 */     GroupLayout.SequentialGroup hGroup = null;
/*  34 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  36 */     layout = new GroupLayout((Container)this);
/*  37 */     setLayout(layout);
/*     */     
/*  39 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  42 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  45 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  48 */     hGroup
/*  49 */       .addGroup(layout.createParallelGroup()
/*  50 */         .addComponent(this.cbOffense)
/*  51 */         .addComponent(this.cbDefense)
/*  52 */         .addComponent(this.cbRetal));
/*     */ 
/*     */     
/*  55 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  58 */     vGroup
/*  59 */       .addGroup(layout.createParallelGroup()
/*  60 */         .addComponent(this.cbOffense))
/*  61 */       .addGroup(layout.createParallelGroup()
/*  62 */         .addComponent(this.cbDefense))
/*  63 */       .addGroup(layout.createParallelGroup()
/*  64 */         .addComponent(this.cbRetal));
/*     */     
/*  66 */     if (direction == 0) {
/*  67 */       layout.setHorizontalGroup(vGroup);
/*  68 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/*  70 */       layout.setHorizontalGroup(hGroup);
/*  71 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/*  74 */     layout.linkSize(0, new Component[] { this.cbOffense, this.cbDefense });
/*  75 */     layout.linkSize(0, new Component[] { this.cbOffense, this.cbRetal });
/*     */     
/*  77 */     layout.linkSize(1, new Component[] { this.cbOffense, this.cbDefense });
/*  78 */     layout.linkSize(1, new Component[] { this.cbOffense, this.cbRetal });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  83 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  84 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/*  85 */     if (fntCheck == null) fntCheck = fntLabel; 
/*  86 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/*  87 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/*  89 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  90 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  91 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/*  93 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  94 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  95 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*  96 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_DMG_OFF_DEF_RET"));
/*  97 */     text.setTitleFont(fntBorder);
/*     */     
/*  99 */     setBorder(text);
/*     */     
/* 101 */     if (this.cbOffense == null) this.cbOffense = new JCheckBox(); 
/* 102 */     this.cbOffense.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_DMG_OFFENSIVE"));
/* 103 */     this.cbOffense.setFont(fntCheck);
/*     */     
/* 105 */     if (this.cbDefense == null) this.cbDefense = new JCheckBox(); 
/* 106 */     this.cbDefense.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_DMG_DEFENSIVE"));
/* 107 */     this.cbDefense.setFont(fntCheck);
/*     */     
/* 109 */     if (this.cbRetal == null) this.cbRetal = new JCheckBox(); 
/* 110 */     this.cbRetal.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_DMG_RETALIATION"));
/* 111 */     this.cbRetal.setFont(fntCheck);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 116 */     this.cbOffense.setSelected(false);
/* 117 */     this.cbDefense.setSelected(false);
/* 118 */     this.cbRetal.setSelected(false);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 122 */     criteria.dmgClassInfo.offense = this.cbOffense.isSelected();
/* 123 */     criteria.dmgClassInfo.defense = this.cbDefense.isSelected();
/* 124 */     criteria.dmgClassInfo.retaliation = this.cbRetal.isSelected();
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\DamageClassPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */