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
/*     */ public class ArtifactRarityPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JCheckBox cbLesser;
/*     */   private JCheckBox cbGreater;
/*     */   private JCheckBox cbDivine;
/*     */   
/*     */   public ArtifactRarityPane(int direction) {
/*  32 */     adjustUI();
/*     */     
/*  34 */     GroupLayout layout = null;
/*  35 */     GroupLayout.SequentialGroup hGroup = null;
/*  36 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  38 */     layout = new GroupLayout((Container)this);
/*  39 */     setLayout(layout);
/*     */     
/*  41 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  44 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  47 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  50 */     hGroup
/*  51 */       .addGroup(layout.createParallelGroup()
/*  52 */         .addComponent(this.cbLesser)
/*  53 */         .addComponent(this.cbGreater)
/*  54 */         .addComponent(this.cbDivine));
/*     */ 
/*     */     
/*  57 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  60 */     vGroup
/*  61 */       .addGroup(layout.createParallelGroup()
/*  62 */         .addComponent(this.cbLesser))
/*  63 */       .addGroup(layout.createParallelGroup()
/*  64 */         .addComponent(this.cbGreater))
/*  65 */       .addGroup(layout.createParallelGroup()
/*  66 */         .addComponent(this.cbDivine));
/*     */     
/*  68 */     if (direction == 0) {
/*  69 */       layout.setHorizontalGroup(vGroup);
/*  70 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/*  72 */       layout.setHorizontalGroup(hGroup);
/*  73 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/*  76 */     layout.linkSize(0, new Component[] { this.cbLesser, this.cbGreater });
/*  77 */     layout.linkSize(0, new Component[] { this.cbLesser, this.cbDivine });
/*     */     
/*  79 */     layout.linkSize(1, new Component[] { this.cbLesser, this.cbGreater });
/*  80 */     layout.linkSize(1, new Component[] { this.cbLesser, this.cbDivine });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  85 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  86 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/*  87 */     if (fntCheck == null) fntCheck = fntLabel; 
/*  88 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/*  89 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/*  91 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  92 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  93 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/*  95 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  96 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  97 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*  98 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RARITY_ARTIFACT"));
/*  99 */     text.setTitleFont(fntBorder);
/*     */     
/* 101 */     setBorder(text);
/*     */     
/* 103 */     if (this.cbLesser == null) this.cbLesser = new JCheckBox(); 
/* 104 */     this.cbLesser.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "RARITY_ARTIFACT_LESSER"));
/* 105 */     this.cbLesser.setFont(fntCheck);
/*     */     
/* 107 */     if (this.cbGreater == null) this.cbGreater = new JCheckBox(); 
/* 108 */     this.cbGreater.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "RARITY_ARTIFACT_GREATER"));
/* 109 */     this.cbGreater.setFont(fntCheck);
/*     */     
/* 111 */     if (this.cbDivine == null) this.cbDivine = new JCheckBox(); 
/* 112 */     this.cbDivine.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "RARITY_ARTIFACT_DIVINE"));
/* 113 */     this.cbDivine.setFont(fntCheck);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 117 */     this.cbLesser.setSelected(false);
/* 118 */     this.cbGreater.setSelected(false);
/* 119 */     this.cbDivine.setSelected(false);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 123 */     if (this.cbLesser.isSelected()) criteria.artifactClass.add("Lesser"); 
/* 124 */     if (this.cbGreater.isSelected()) criteria.artifactClass.add("Greater"); 
/* 125 */     if (this.cbDivine.isSelected()) criteria.artifactClass.add("Divine"); 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\ArtifactRarityPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */