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
/*     */ public class ArmorClassPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JCheckBox cbCaster;
/*     */   private JCheckBox cbLight;
/*     */   private JCheckBox cbHeavy;
/*     */   
/*     */   public ArmorClassPane(int direction) {
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
/*  49 */     hGroup
/*  50 */       .addGroup(layout.createParallelGroup()
/*  51 */         .addComponent(this.cbCaster)
/*  52 */         .addComponent(this.cbLight)
/*  53 */         .addComponent(this.cbHeavy));
/*     */     
/*  55 */     vGroup = layout.createSequentialGroup();
/*     */     
/*  57 */     vGroup
/*  58 */       .addGroup(layout.createParallelGroup()
/*  59 */         .addComponent(this.cbCaster))
/*  60 */       .addGroup(layout.createParallelGroup()
/*  61 */         .addComponent(this.cbLight))
/*  62 */       .addGroup(layout.createParallelGroup()
/*  63 */         .addComponent(this.cbHeavy));
/*     */     
/*  65 */     if (direction == 0) {
/*  66 */       layout.setHorizontalGroup(vGroup);
/*  67 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/*  69 */       layout.setHorizontalGroup(hGroup);
/*  70 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/*  73 */     layout.linkSize(0, new Component[] { this.cbCaster, this.cbLight });
/*  74 */     layout.linkSize(0, new Component[] { this.cbCaster, this.cbHeavy });
/*     */     
/*  76 */     layout.linkSize(1, new Component[] { this.cbCaster, this.cbLight });
/*  77 */     layout.linkSize(1, new Component[] { this.cbCaster, this.cbHeavy });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  82 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  83 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/*  84 */     if (fntCheck == null) fntCheck = fntLabel; 
/*  85 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/*  86 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/*  88 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  89 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  90 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/*  92 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  93 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  94 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*  95 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_ARMOR_CLASS"));
/*  96 */     text.setTitleFont(fntBorder);
/*     */     
/*  98 */     setBorder(text);
/*     */     
/* 100 */     if (this.cbCaster == null) this.cbCaster = new JCheckBox(); 
/* 101 */     this.cbCaster.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "ARMOR_CLASS_CASTER"));
/* 102 */     this.cbCaster.setFont(fntCheck);
/*     */     
/* 104 */     if (this.cbLight == null) this.cbLight = new JCheckBox(); 
/* 105 */     this.cbLight.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "ARMOR_CLASS_LIGHT"));
/* 106 */     this.cbLight.setFont(fntCheck);
/*     */     
/* 108 */     if (this.cbHeavy == null) this.cbHeavy = new JCheckBox(); 
/* 109 */     this.cbHeavy.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "ARMOR_CLASS_HEAVY"));
/* 110 */     this.cbHeavy.setFont(fntCheck);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 114 */     this.cbCaster.setSelected(false);
/* 115 */     this.cbLight.setSelected(false);
/* 116 */     this.cbHeavy.setSelected(false);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 120 */     if (this.cbCaster.isSelected()) criteria.armorClass.add("Caster"); 
/* 121 */     if (this.cbLight.isSelected()) criteria.armorClass.add("Light"); 
/* 122 */     if (this.cbHeavy.isSelected()) criteria.armorClass.add("Heavy"); 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\ArmorClassPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */