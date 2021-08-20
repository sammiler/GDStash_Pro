/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
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
/*     */ public class SlotSelectionPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private WeaponTypePane pnlWeaponTypes;
/*     */   private ArmorTypePane pnlArmorTypes;
/*     */   
/*     */   public SlotSelectionPane() {
/*  28 */     adjustUI();
/*     */     
/*  30 */     GroupLayout layout = null;
/*  31 */     GroupLayout.SequentialGroup hGroup = null;
/*  32 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  34 */     layout = new GroupLayout((Container)this);
/*  35 */     setLayout(layout);
/*     */     
/*  37 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/*  40 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/*  43 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  46 */     hGroup
/*  47 */       .addGroup(layout.createParallelGroup()
/*  48 */         .addComponent((Component)this.pnlWeaponTypes))
/*  49 */       .addGroup(layout.createParallelGroup()
/*  50 */         .addComponent((Component)this.pnlArmorTypes));
/*  51 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  54 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  57 */     vGroup
/*  58 */       .addGroup(layout.createParallelGroup()
/*  59 */         .addComponent((Component)this.pnlWeaponTypes)
/*  60 */         .addComponent((Component)this.pnlArmorTypes));
/*     */     
/*  62 */     layout.setVerticalGroup(vGroup);
/*     */     
/*  64 */     layout.linkSize(0, new Component[] { (Component)this.pnlWeaponTypes, (Component)this.pnlArmorTypes });
/*     */     
/*  66 */     layout.linkSize(1, new Component[] { (Component)this.pnlWeaponTypes, (Component)this.pnlArmorTypes });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  71 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  72 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/*  73 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/*  75 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  76 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/*  78 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  79 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  80 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*  81 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SLOT_INFO"));
/*  82 */     text.setTitleFont(fntBorder);
/*     */     
/*  84 */     setBorder(text);
/*     */     
/*  86 */     if (this.pnlWeaponTypes == null) {
/*  87 */       this.pnlWeaponTypes = new WeaponTypePane();
/*     */     } else {
/*  89 */       this.pnlWeaponTypes.adjustUI();
/*     */     } 
/*     */     
/*  92 */     if (this.pnlArmorTypes == null) {
/*  93 */       this.pnlArmorTypes = new ArmorTypePane();
/*     */     } else {
/*  95 */       this.pnlArmorTypes.adjustUI();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clear() {
/* 100 */     this.pnlWeaponTypes.clear();
/* 101 */     this.pnlArmorTypes.clear();
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 105 */     criteria.selMode = SelectionCriteria.SelectionMode.COMPONENT;
/*     */     
/* 107 */     this.pnlWeaponTypes.addComponentCriteria(criteria);
/* 108 */     this.pnlArmorTypes.addComponentCriteria(criteria);
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\SlotSelectionPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */