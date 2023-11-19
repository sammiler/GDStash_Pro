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
/*     */ public class DamageFlatPercPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JCheckBox cbFlat;
/*     */   private JCheckBox cbPerc;
/*     */   private JCheckBox cbIncMaxRes;
/*     */   
/*     */   public DamageFlatPercPane(int direction) {
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
/*  50 */         .addComponent(this.cbFlat)
/*  51 */         .addComponent(this.cbPerc)
/*  52 */         .addComponent(this.cbIncMaxRes));
/*     */ 
/*     */     
/*  55 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  58 */     vGroup
/*  59 */       .addGroup(layout.createParallelGroup()
/*  60 */         .addComponent(this.cbFlat))
/*  61 */       .addGroup(layout.createParallelGroup()
/*  62 */         .addComponent(this.cbPerc))
/*  63 */       .addGroup(layout.createParallelGroup()
/*  64 */         .addComponent(this.cbIncMaxRes));
/*     */     
/*  66 */     if (direction == 0) {
/*  67 */       layout.setHorizontalGroup(vGroup);
/*  68 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/*  70 */       layout.setHorizontalGroup(hGroup);
/*  71 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/*  74 */     layout.linkSize(0, new Component[] { this.cbFlat, this.cbPerc });
/*  75 */     layout.linkSize(0, new Component[] { this.cbFlat, this.cbIncMaxRes });
/*     */     
/*  77 */     layout.linkSize(1, new Component[] { this.cbFlat, this.cbPerc });
/*  78 */     layout.linkSize(1, new Component[] { this.cbFlat, this.cbIncMaxRes });
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
/*  96 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_DAMAGE"));
/*  97 */     text.setTitleFont(fntBorder);
/*     */     
/*  99 */     setBorder(text);
/*     */     
/* 101 */     if (this.cbFlat == null) this.cbFlat = new JCheckBox(); 
/* 102 */     this.cbFlat.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DAMAGE_FLAT"));
/* 103 */     this.cbFlat.setFont(fntCheck);
/*     */     
/* 105 */     if (this.cbPerc == null) this.cbPerc = new JCheckBox(); 
/* 106 */     this.cbPerc.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DAMAGE_PERCENT"));
/* 107 */     this.cbPerc.setFont(fntCheck);
/*     */     
/* 109 */     if (this.cbIncMaxRes == null) this.cbIncMaxRes = new JCheckBox(); 
/* 110 */     this.cbIncMaxRes.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DAMAGE_INC_MAX_RES"));
/* 111 */     this.cbIncMaxRes.setFont(fntCheck);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 115 */     this.cbFlat.setSelected(false);
/* 116 */     this.cbPerc.setSelected(false);
/* 117 */     this.cbIncMaxRes.setSelected(false);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 121 */     criteria.fieldInfo.flat = this.cbFlat.isSelected();
/* 122 */     criteria.fieldInfo.percentage = this.cbPerc.isSelected();
/* 123 */     criteria.fieldInfo.maxResist = this.cbIncMaxRes.isSelected();
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\select\DamageFlatPercPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */