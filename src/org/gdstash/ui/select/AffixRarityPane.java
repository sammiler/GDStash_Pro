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
/*     */ public class AffixRarityPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JCheckBox cbMagical;
/*     */   private JCheckBox cbRare;
/*     */   
/*     */   public AffixRarityPane(int direction) {
/*  31 */     adjustUI();
/*     */     
/*  33 */     GroupLayout layout = null;
/*  34 */     GroupLayout.SequentialGroup hGroup = null;
/*  35 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  37 */     layout = new GroupLayout((Container)this);
/*  38 */     setLayout(layout);
/*     */     
/*  40 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  43 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  46 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  49 */     hGroup
/*  50 */       .addGroup(layout.createParallelGroup()
/*  51 */         .addComponent(this.cbMagical)
/*  52 */         .addComponent(this.cbRare));
/*     */ 
/*     */     
/*  55 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  58 */     vGroup
/*  59 */       .addGroup(layout.createParallelGroup()
/*  60 */         .addComponent(this.cbMagical))
/*  61 */       .addGroup(layout.createParallelGroup()
/*  62 */         .addComponent(this.cbRare));
/*     */     
/*  64 */     if (direction == 0) {
/*  65 */       layout.setHorizontalGroup(vGroup);
/*  66 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/*  68 */       layout.setHorizontalGroup(hGroup);
/*  69 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/*  72 */     layout.linkSize(0, new Component[] { this.cbMagical, this.cbRare });
/*     */     
/*  74 */     layout.linkSize(1, new Component[] { this.cbMagical, this.cbRare });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  79 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  80 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/*  81 */     if (fntCheck == null) fntCheck = fntLabel; 
/*  82 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/*  83 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/*  85 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  86 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  87 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/*  89 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  90 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  91 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*  92 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RARITY_AFFIX"));
/*  93 */     text.setTitleFont(fntBorder);
/*     */     
/*  95 */     setBorder(text);
/*     */     
/*  97 */     if (this.cbMagical == null) this.cbMagical = new JCheckBox(); 
/*  98 */     this.cbMagical.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "RARITY_AFFIX_MAGICAL"));
/*  99 */     this.cbMagical.setFont(fntCheck);
/*     */     
/* 101 */     if (this.cbRare == null) this.cbRare = new JCheckBox(); 
/* 102 */     this.cbRare.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "RARITY_AFFIX_RARE"));
/* 103 */     this.cbRare.setFont(fntCheck);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 107 */     this.cbMagical.setSelected(false);
/* 108 */     this.cbRare.setSelected(false);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 112 */     if (this.cbMagical.isSelected()) criteria.itemRarity.add("Magical"); 
/* 113 */     if (this.cbRare.isSelected()) criteria.itemRarity.add("Rare"); 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\AffixRarityPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */