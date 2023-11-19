/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JRadioButton;
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
/*     */ public class SoulboundPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JRadioButton rbAll;
/*     */   private JRadioButton rbNonbound;
/*     */   private JRadioButton rbSoulbound;
/*     */   private ButtonGroup group;
/*     */   private SelectionCriteria.SoulboundMode defaultMode;
/*     */   
/*     */   public SoulboundPane(int direction) {
/*  34 */     this(direction, SelectionCriteria.SoulboundMode.ALL, true);
/*     */   }
/*     */   
/*     */   public SoulboundPane(int direction, SelectionCriteria.SoulboundMode defaultMode, boolean enabled) {
/*  38 */     this.defaultMode = defaultMode;
/*     */     
/*  40 */     adjustUI();
/*     */     
/*  42 */     this.rbAll.setEnabled(enabled);
/*  43 */     this.rbNonbound.setEnabled(enabled);
/*  44 */     this.rbSoulbound.setEnabled(enabled);
/*     */     
/*  46 */     GroupLayout layout = null;
/*  47 */     GroupLayout.SequentialGroup hGroup = null;
/*  48 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  50 */     layout = new GroupLayout((Container)this);
/*  51 */     setLayout(layout);
/*     */     
/*  53 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  56 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  59 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  62 */     hGroup
/*  63 */       .addGroup(layout.createParallelGroup()
/*  64 */         .addComponent(this.rbAll)
/*  65 */         .addComponent(this.rbNonbound)
/*  66 */         .addComponent(this.rbSoulbound));
/*     */ 
/*     */     
/*  69 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  72 */     vGroup
/*  73 */       .addGroup(layout.createParallelGroup()
/*  74 */         .addComponent(this.rbAll))
/*  75 */       .addGroup(layout.createParallelGroup()
/*  76 */         .addComponent(this.rbNonbound))
/*  77 */       .addGroup(layout.createParallelGroup()
/*  78 */         .addComponent(this.rbSoulbound));
/*     */     
/*  80 */     if (direction == 0) {
/*  81 */       layout.setHorizontalGroup(vGroup);
/*  82 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/*  84 */       layout.setHorizontalGroup(hGroup);
/*  85 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/*  88 */     layout.linkSize(0, new Component[] { this.rbAll, this.rbNonbound });
/*  89 */     layout.linkSize(0, new Component[] { this.rbAll, this.rbSoulbound });
/*     */     
/*  91 */     layout.linkSize(1, new Component[] { this.rbAll, this.rbNonbound });
/*  92 */     layout.linkSize(1, new Component[] { this.rbAll, this.rbSoulbound });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  97 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  98 */     Font fntRadio = UIManager.getDefaults().getFont("RadioButton.font");
/*  99 */     if (fntRadio == null) fntRadio = fntLabel; 
/* 100 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 101 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 103 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 104 */     fntRadio = fntRadio.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 105 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 107 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 108 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 109 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 110 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SOULBOUND_ITEM"));
/* 111 */     text.setTitleFont(fntBorder);
/*     */     
/* 113 */     setBorder(text);
/*     */     
/* 115 */     if (this.rbAll == null) this.rbAll = new JRadioButton(); 
/* 116 */     this.rbAll.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SOULBOUND_ALL"));
/* 117 */     this.rbAll.setFont(fntRadio);
/*     */     
/* 119 */     if (this.rbNonbound == null) this.rbNonbound = new JRadioButton(); 
/* 120 */     this.rbNonbound.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SOULBOUND_NON"));
/* 121 */     this.rbNonbound.setFont(fntRadio);
/*     */     
/* 123 */     if (this.rbSoulbound == null) this.rbSoulbound = new JRadioButton(); 
/* 124 */     this.rbSoulbound.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "SOULBOUND_ONLY"));
/* 125 */     this.rbSoulbound.setFont(fntRadio);
/*     */     
/* 127 */     if (this.group == null) {
/* 128 */       this.group = new ButtonGroup();
/*     */ 
/*     */       
/* 131 */       this.rbAll.setSelected(this.defaultMode.equals(SelectionCriteria.SoulboundMode.ALL));
/* 132 */       this.rbNonbound.setSelected(this.defaultMode.equals(SelectionCriteria.SoulboundMode.NONBOUND));
/* 133 */       this.rbSoulbound.setSelected(this.defaultMode.equals(SelectionCriteria.SoulboundMode.SOULBOUND));
/*     */       
/* 135 */       this.group.add(this.rbAll);
/* 136 */       this.group.add(this.rbNonbound);
/* 137 */       this.group.add(this.rbSoulbound);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clear() {
/* 142 */     this.rbAll.setSelected(true);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 146 */     if (this.rbAll.isSelected()) criteria.soulboundMode = SelectionCriteria.SoulboundMode.ALL; 
/* 147 */     if (this.rbNonbound.isSelected()) criteria.soulboundMode = SelectionCriteria.SoulboundMode.NONBOUND; 
/* 148 */     if (this.rbSoulbound.isSelected()) criteria.soulboundMode = SelectionCriteria.SoulboundMode.SOULBOUND; 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\select\SoulboundPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */