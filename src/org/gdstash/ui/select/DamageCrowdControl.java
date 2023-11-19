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
/*     */ public class DamageCrowdControl
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JCheckBox cbStun;
/*     */   private JCheckBox cbSlow;
/*     */   private JCheckBox cbTrap;
/*     */   private JCheckBox cbPetrify;
/*     */   private JCheckBox cbFreeze;
/*     */   
/*     */   public DamageCrowdControl() {
/*  34 */     this(1);
/*     */   }
/*     */   
/*     */   public DamageCrowdControl(int direction) {
/*  38 */     adjustUI();
/*     */     
/*  40 */     GroupLayout layout = null;
/*  41 */     GroupLayout.SequentialGroup hGroup = null;
/*  42 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  44 */     layout = new GroupLayout((Container)this);
/*  45 */     setLayout(layout);
/*     */     
/*  47 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  50 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  53 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  56 */     hGroup
/*  57 */       .addGroup(layout.createParallelGroup()
/*  58 */         .addComponent(this.cbStun)
/*  59 */         .addComponent(this.cbSlow)
/*  60 */         .addComponent(this.cbTrap)
/*  61 */         .addComponent(this.cbPetrify)
/*  62 */         .addComponent(this.cbFreeze));
/*     */ 
/*     */     
/*  65 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  68 */     vGroup
/*  69 */       .addGroup(layout.createParallelGroup()
/*  70 */         .addComponent(this.cbStun))
/*  71 */       .addGroup(layout.createParallelGroup()
/*  72 */         .addComponent(this.cbSlow))
/*  73 */       .addGroup(layout.createParallelGroup()
/*  74 */         .addComponent(this.cbTrap))
/*  75 */       .addGroup(layout.createParallelGroup()
/*  76 */         .addComponent(this.cbPetrify))
/*  77 */       .addGroup(layout.createParallelGroup()
/*  78 */         .addComponent(this.cbFreeze));
/*     */     
/*  80 */     if (direction == 0) {
/*  81 */       layout.setHorizontalGroup(vGroup);
/*  82 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/*  84 */       layout.setHorizontalGroup(hGroup);
/*  85 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/*  88 */     layout.linkSize(0, new Component[] { this.cbStun, this.cbSlow });
/*  89 */     layout.linkSize(0, new Component[] { this.cbStun, this.cbTrap });
/*  90 */     layout.linkSize(0, new Component[] { this.cbStun, this.cbPetrify });
/*  91 */     layout.linkSize(0, new Component[] { this.cbStun, this.cbFreeze });
/*     */     
/*  93 */     layout.linkSize(1, new Component[] { this.cbStun, this.cbSlow });
/*  94 */     layout.linkSize(1, new Component[] { this.cbStun, this.cbTrap });
/*  95 */     layout.linkSize(1, new Component[] { this.cbStun, this.cbPetrify });
/*  96 */     layout.linkSize(1, new Component[] { this.cbStun, this.cbFreeze });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 101 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 102 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 103 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 104 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 105 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 107 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 108 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 109 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 111 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 112 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 113 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 114 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_DMG_CROWD_CONTROL"));
/* 115 */     text.setTitleFont(fntBorder);
/*     */     
/* 117 */     setBorder(text);
/*     */     
/* 119 */     if (this.cbStun == null) this.cbStun = new JCheckBox(); 
/* 120 */     this.cbStun.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_RESIST_STUN"));
/* 121 */     this.cbStun.setFont(fntCheck);
/*     */     
/* 123 */     if (this.cbSlow == null) this.cbSlow = new JCheckBox(); 
/* 124 */     this.cbSlow.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_RESIST_SLOW"));
/* 125 */     this.cbSlow.setFont(fntCheck);
/*     */     
/* 127 */     if (this.cbTrap == null) this.cbTrap = new JCheckBox(); 
/* 128 */     this.cbTrap.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_RESIST_TRAP"));
/* 129 */     this.cbTrap.setFont(fntCheck);
/*     */     
/* 131 */     if (this.cbPetrify == null) this.cbPetrify = new JCheckBox(); 
/* 132 */     this.cbPetrify.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_RESIST_PETRIFY"));
/* 133 */     this.cbPetrify.setFont(fntCheck);
/*     */     
/* 135 */     if (this.cbFreeze == null) this.cbFreeze = new JCheckBox(); 
/* 136 */     this.cbFreeze.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "DMGTYPE_RESIST_FREEZE"));
/* 137 */     this.cbFreeze.setFont(fntCheck);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 141 */     this.cbStun.setSelected(false);
/* 142 */     this.cbSlow.setSelected(false);
/* 143 */     this.cbTrap.setSelected(false);
/* 144 */     this.cbPetrify.setSelected(false);
/* 145 */     this.cbFreeze.setSelected(false);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 149 */     if (this.cbStun.isSelected()) {
/* 150 */       SelectionCriteria.addStatInfos(criteria, 601);
/*     */     }
/* 152 */     if (this.cbSlow.isSelected()) {
/* 153 */       SelectionCriteria.addStatInfos(criteria, 602);
/*     */     }
/* 155 */     if (this.cbTrap.isSelected()) {
/* 156 */       SelectionCriteria.addStatInfos(criteria, 603);
/*     */     }
/* 158 */     if (this.cbPetrify.isSelected()) {
/* 159 */       SelectionCriteria.addStatInfos(criteria, 604);
/*     */     }
/* 161 */     if (this.cbFreeze.isSelected())
/* 162 */       SelectionCriteria.addStatInfos(criteria, 605); 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\select\DamageCrowdControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */