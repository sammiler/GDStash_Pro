/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
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
/*     */ public class ItemSetSelectionPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private ItemRarityPane pnlItemRarity;
/*     */   private LevelSelPane pnlLevel;
/*     */   
/*     */   public ItemSetSelectionPane() {
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
/*  50 */         .addComponent((Component)this.pnlItemRarity))
/*  51 */       .addGroup(layout.createParallelGroup()
/*  52 */         .addComponent((Component)this.pnlLevel));
/*  53 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  56 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  59 */     vGroup
/*  60 */       .addGroup(layout.createParallelGroup()
/*  61 */         .addComponent((Component)this.pnlItemRarity)
/*  62 */         .addComponent((Component)this.pnlLevel));
/*     */     
/*  64 */     layout.setVerticalGroup(vGroup);
/*     */     
/*  66 */     layout.linkSize(0, new Component[] { (Component)this.pnlItemRarity, (Component)this.pnlLevel });
/*     */     
/*  68 */     layout.linkSize(1, new Component[] { (Component)this.pnlItemRarity, (Component)this.pnlLevel });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  73 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  74 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/*  75 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/*  77 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  78 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/*  80 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  81 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  82 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*  83 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SET_INFO"));
/*  84 */     text.setTitleFont(fntBorder);
/*     */     
/*  86 */     setBorder(text);
/*     */     
/*  88 */     if (this.pnlItemRarity == null) {
/*  89 */       this.pnlItemRarity = new ItemRarityPane(0);
/*     */     } else {
/*  91 */       this.pnlItemRarity.adjustUI();
/*     */     } 
/*     */     
/*  94 */     if (this.pnlLevel == null) {
/*  95 */       this.pnlLevel = new LevelSelPane(0);
/*     */     } else {
/*  97 */       this.pnlLevel.adjustUI();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clear() {
/* 102 */     this.pnlItemRarity.clear();
/* 103 */     this.pnlLevel.clear();
/*     */   }
/*     */   
/*     */   public int getMinLevel() {
/* 107 */     if (this.pnlLevel == null) return -1;
/*     */     
/* 109 */     return this.pnlLevel.getMinLevel();
/*     */   }
/*     */   
/*     */   public int getMaxLevel() {
/* 113 */     if (this.pnlLevel == null) return -1;
/*     */     
/* 115 */     return this.pnlLevel.getMaxLevel();
/*     */   }
/*     */   
/*     */   public List<String> getRarityList() {
/* 119 */     if (this.pnlItemRarity == null) return new LinkedList<>();
/*     */     
/* 121 */     return this.pnlItemRarity.getRarityList();
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\select\ItemSetSelectionPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */