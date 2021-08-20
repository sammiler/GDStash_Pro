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
/*     */ public class AttribSelectionPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private AttribFlatPane pnlAttribFlat;
/*     */   private AttribPercPane pnlAttribPerc;
/*     */   
/*     */   public AttribSelectionPane() {
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
/*  48 */         .addComponent((Component)this.pnlAttribFlat))
/*  49 */       .addGroup(layout.createParallelGroup()
/*  50 */         .addComponent((Component)this.pnlAttribPerc));
/*  51 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  54 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  57 */     vGroup
/*  58 */       .addGroup(layout.createParallelGroup()
/*  59 */         .addComponent((Component)this.pnlAttribFlat)
/*  60 */         .addComponent((Component)this.pnlAttribPerc));
/*  61 */     layout.setVerticalGroup(vGroup);
/*     */ 
/*     */ 
/*     */     
/*  65 */     layout.linkSize(1, new Component[] { (Component)this.pnlAttribFlat, (Component)this.pnlAttribPerc });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  70 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  71 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/*  72 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/*  74 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  75 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/*  77 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  78 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  79 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*  80 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_ATTRIBS"));
/*  81 */     text.setTitleFont(fntBorder);
/*     */     
/*  83 */     setBorder(text);
/*     */     
/*  85 */     if (this.pnlAttribFlat == null) {
/*  86 */       this.pnlAttribFlat = new AttribFlatPane(1);
/*     */     } else {
/*  88 */       this.pnlAttribFlat.adjustUI();
/*     */     } 
/*     */     
/*  91 */     if (this.pnlAttribPerc == null) {
/*  92 */       this.pnlAttribPerc = new AttribPercPane(1);
/*     */     } else {
/*  94 */       this.pnlAttribPerc.adjustUI();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clear() {
/*  99 */     this.pnlAttribFlat.clear();
/* 100 */     this.pnlAttribPerc.clear();
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 104 */     this.pnlAttribFlat.addCriteria(criteria);
/* 105 */     this.pnlAttribPerc.addCriteria(criteria);
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\AttribSelectionPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */