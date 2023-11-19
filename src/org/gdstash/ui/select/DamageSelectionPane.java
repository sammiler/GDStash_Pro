/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
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
/*     */ public class DamageSelectionPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private DamageClassPane pnlDmgClass;
/*     */   private DamageFlatPercPane pnlFlatPerc;
/*     */   private DamageFlatPane pnlFlat;
/*     */   private DamageDoTPane pnlDoT;
/*     */   private DamageSpecialPane pnlSpecial;
/*     */   private DamageCrowdControl pnlCrowd;
/*     */   
/*     */   public DamageSelectionPane() {
/*  33 */     adjustUI();
/*     */     
/*  35 */     JPanel pnlDmgTypes = new JPanel();
/*     */     
/*  37 */     GroupLayout layout = null;
/*  38 */     GroupLayout.SequentialGroup hGroup = null;
/*  39 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  41 */     layout = new GroupLayout(pnlDmgTypes);
/*  42 */     pnlDmgTypes.setLayout(layout);
/*     */     
/*  44 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/*  47 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/*  50 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  53 */     hGroup
/*  54 */       .addGroup(layout.createParallelGroup()
/*  55 */         .addComponent((Component)this.pnlFlat))
/*  56 */       .addGroup(layout.createParallelGroup()
/*  57 */         .addComponent((Component)this.pnlDoT))
/*  58 */       .addGroup(layout.createParallelGroup()
/*  59 */         .addComponent((Component)this.pnlSpecial))
/*  60 */       .addGroup(layout.createParallelGroup()
/*  61 */         .addComponent((Component)this.pnlCrowd));
/*  62 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  65 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  68 */     vGroup
/*  69 */       .addGroup(layout.createParallelGroup()
/*  70 */         .addComponent((Component)this.pnlFlat)
/*  71 */         .addComponent((Component)this.pnlDoT)
/*  72 */         .addComponent((Component)this.pnlSpecial)
/*  73 */         .addComponent((Component)this.pnlCrowd));
/*     */     
/*  75 */     layout.setVerticalGroup(vGroup);
/*     */     
/*  77 */     layout.linkSize(0, new Component[] { (Component)this.pnlFlat, (Component)this.pnlDoT });
/*  78 */     layout.linkSize(0, new Component[] { (Component)this.pnlFlat, (Component)this.pnlSpecial });
/*  79 */     layout.linkSize(0, new Component[] { (Component)this.pnlFlat, (Component)this.pnlCrowd });
/*     */     
/*  81 */     layout.linkSize(1, new Component[] { (Component)this.pnlFlat, (Component)this.pnlDoT });
/*  82 */     layout.linkSize(1, new Component[] { (Component)this.pnlFlat, (Component)this.pnlSpecial });
/*  83 */     layout.linkSize(1, new Component[] { (Component)this.pnlFlat, (Component)this.pnlCrowd });
/*     */ 
/*     */ 
/*     */     
/*  87 */     layout = new GroupLayout((Container)this);
/*  88 */     setLayout(layout);
/*     */     
/*  90 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/*  93 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/*  96 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  99 */     hGroup
/* 100 */       .addGroup(layout.createParallelGroup()
/* 101 */         .addComponent((Component)this.pnlDmgClass)
/* 102 */         .addComponent((Component)this.pnlFlatPerc)
/* 103 */         .addComponent(pnlDmgTypes));
/* 104 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 107 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 110 */     vGroup
/* 111 */       .addGroup(layout.createParallelGroup()
/* 112 */         .addComponent((Component)this.pnlDmgClass))
/* 113 */       .addGroup(layout.createParallelGroup()
/* 114 */         .addComponent((Component)this.pnlFlatPerc))
/* 115 */       .addGroup(layout.createParallelGroup()
/* 116 */         .addComponent(pnlDmgTypes));
/* 117 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 119 */     layout.linkSize(0, new Component[] { (Component)this.pnlDmgClass, (Component)this.pnlFlatPerc });
/* 120 */     layout.linkSize(0, new Component[] { (Component)this.pnlDmgClass, pnlDmgTypes });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 125 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 126 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 127 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 129 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 130 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 132 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 133 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 134 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 135 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_DAMAGE_INFO"));
/* 136 */     text.setTitleFont(fntBorder);
/*     */     
/* 138 */     setBorder(text);
/*     */     
/* 140 */     if (this.pnlDmgClass == null) {
/* 141 */       this.pnlDmgClass = new DamageClassPane(0);
/*     */     } else {
/* 143 */       this.pnlDmgClass.adjustUI();
/*     */     } 
/*     */     
/* 146 */     if (this.pnlFlatPerc == null) {
/* 147 */       this.pnlFlatPerc = new DamageFlatPercPane(0);
/*     */     } else {
/* 149 */       this.pnlFlatPerc.adjustUI();
/*     */     } 
/*     */     
/* 152 */     if (this.pnlFlat == null) {
/* 153 */       this.pnlFlat = new DamageFlatPane();
/*     */     } else {
/* 155 */       this.pnlFlat.adjustUI();
/*     */     } 
/*     */     
/* 158 */     if (this.pnlDoT == null) {
/* 159 */       this.pnlDoT = new DamageDoTPane();
/*     */     } else {
/* 161 */       this.pnlDoT.adjustUI();
/*     */     } 
/*     */     
/* 164 */     if (this.pnlSpecial == null) {
/* 165 */       this.pnlSpecial = new DamageSpecialPane();
/*     */     } else {
/* 167 */       this.pnlSpecial.adjustUI();
/*     */     } 
/*     */     
/* 170 */     if (this.pnlCrowd == null) {
/* 171 */       this.pnlCrowd = new DamageCrowdControl();
/*     */     } else {
/* 173 */       this.pnlCrowd.adjustUI();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clear() {
/* 178 */     this.pnlDmgClass.clear();
/* 179 */     this.pnlFlatPerc.clear();
/* 180 */     this.pnlFlat.clear();
/* 181 */     this.pnlDoT.clear();
/* 182 */     this.pnlSpecial.clear();
/* 183 */     this.pnlCrowd.clear();
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 187 */     this.pnlDmgClass.addCriteria(criteria);
/* 188 */     this.pnlFlatPerc.addCriteria(criteria);
/*     */     
/* 190 */     this.pnlFlat.addCriteria(criteria);
/* 191 */     this.pnlDoT.addCriteria(criteria);
/* 192 */     this.pnlSpecial.addCriteria(criteria);
/* 193 */     this.pnlCrowd.addCriteria(criteria);
/*     */     
/* 195 */     criteria.selMode = SelectionCriteria.SelectionMode.ITEM;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\select\DamageSelectionPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */