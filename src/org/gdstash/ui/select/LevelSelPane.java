/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JFormattedTextField;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import javax.swing.text.AbstractDocument;
/*     */ import javax.swing.text.DocumentFilter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LevelSelPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JLabel lblLevelMin;
/*     */   private JLabel lblLevelMax;
/*     */   private JFormattedTextField ftLevelMin;
/*     */   private JFormattedTextField ftLevelMax;
/*     */   
/*     */   public LevelSelPane() {
/*  39 */     this(0);
/*     */   }
/*     */   
/*     */   public LevelSelPane(int direction) {
/*  43 */     adjustUI();
/*     */     
/*  45 */     GroupLayout layout = null;
/*  46 */     GroupLayout.SequentialGroup hGroup = null;
/*  47 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  49 */     layout = new GroupLayout((Container)this);
/*  50 */     setLayout(layout);
/*     */     
/*  52 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  55 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  58 */     hGroup = layout.createSequentialGroup();
/*     */     
/*  60 */     if (direction == 0) {
/*     */       
/*  62 */       hGroup
/*  63 */         .addGroup(layout.createParallelGroup()
/*  64 */           .addComponent(this.lblLevelMin))
/*  65 */         .addGroup(layout.createParallelGroup()
/*  66 */           .addComponent(this.ftLevelMin))
/*  67 */         .addGroup(layout.createParallelGroup()
/*  68 */           .addComponent(this.lblLevelMax))
/*  69 */         .addGroup(layout.createParallelGroup()
/*  70 */           .addComponent(this.ftLevelMax));
/*     */     } else {
/*     */       
/*  73 */       hGroup
/*  74 */         .addGroup(layout.createParallelGroup()
/*  75 */           .addComponent(this.lblLevelMin)
/*  76 */           .addComponent(this.lblLevelMax))
/*  77 */         .addGroup(layout.createParallelGroup()
/*  78 */           .addComponent(this.ftLevelMin)
/*  79 */           .addComponent(this.ftLevelMax));
/*     */     } 
/*  81 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  84 */     vGroup = layout.createSequentialGroup();
/*     */     
/*  86 */     if (direction == 0) {
/*     */       
/*  88 */       vGroup
/*  89 */         .addGroup(layout.createParallelGroup()
/*  90 */           .addComponent(this.lblLevelMin)
/*  91 */           .addComponent(this.ftLevelMin)
/*  92 */           .addComponent(this.lblLevelMax)
/*  93 */           .addComponent(this.ftLevelMax));
/*     */     } else {
/*     */       
/*  96 */       vGroup
/*  97 */         .addGroup(layout.createParallelGroup()
/*  98 */           .addComponent(this.lblLevelMin)
/*  99 */           .addComponent(this.ftLevelMin))
/* 100 */         .addGroup(layout.createParallelGroup()
/* 101 */           .addComponent(this.lblLevelMax)
/* 102 */           .addComponent(this.ftLevelMax));
/*     */     } 
/* 104 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 106 */     layout.linkSize(0, new Component[] { this.lblLevelMin, this.ftLevelMin });
/* 107 */     layout.linkSize(0, new Component[] { this.lblLevelMin, this.lblLevelMax });
/* 108 */     layout.linkSize(0, new Component[] { this.lblLevelMin, this.ftLevelMax });
/*     */     
/* 110 */     layout.linkSize(1, new Component[] { this.lblLevelMin, this.ftLevelMin });
/* 111 */     layout.linkSize(1, new Component[] { this.lblLevelMin, this.lblLevelMax });
/* 112 */     layout.linkSize(1, new Component[] { this.lblLevelMin, this.ftLevelMax });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 117 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 118 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 119 */     if (fntBorder == null) fntBorder = fntLabel; 
/* 120 */     Font fntFText = UIManager.getDefaults().getFont("FormattedTextField.font");
/* 121 */     if (fntFText == null) fntFText = fntLabel;
/*     */     
/* 123 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 124 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 125 */     fntFText = fntFText.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 127 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 128 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 129 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 130 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_REQUIREMENTS"));
/* 131 */     text.setTitleFont(fntBorder);
/*     */     
/* 133 */     setBorder(text);
/*     */     
/* 135 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 136 */     fntFText = fntFText.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 138 */     if (this.lblLevelMin == null) this.lblLevelMin = new JLabel(); 
/* 139 */     this.lblLevelMin.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_LEVEL_MIN"));
/* 140 */     this.lblLevelMin.setFont(fntLabel);
/*     */     
/* 142 */     if (this.ftLevelMin == null) {
/* 143 */       this.ftLevelMin = new JFormattedTextField();
/* 144 */       DocumentFilter filter = new IntLenDocFilter(3);
/* 145 */       AbstractDocument doc = (AbstractDocument)this.ftLevelMin.getDocument();
/* 146 */       doc.setDocumentFilter(filter);
/*     */     } 
/* 148 */     this.ftLevelMin.setFont(fntFText);
/*     */     
/* 150 */     if (this.lblLevelMax == null) this.lblLevelMax = new JLabel(); 
/* 151 */     this.lblLevelMax.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_LEVEL_MAX"));
/* 152 */     this.lblLevelMax.setFont(fntLabel);
/*     */     
/* 154 */     if (this.ftLevelMax == null) {
/* 155 */       this.ftLevelMax = new JFormattedTextField();
/* 156 */       DocumentFilter filter = new IntLenDocFilter(3);
/* 157 */       AbstractDocument doc = (AbstractDocument)this.ftLevelMax.getDocument();
/* 158 */       doc.setDocumentFilter(filter);
/*     */     } 
/* 160 */     this.ftLevelMax.setFont(fntFText);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 164 */     this.ftLevelMin.setText(null);
/* 165 */     this.ftLevelMax.setText(null);
/*     */   }
/*     */   
/*     */   public int getMinLevel() {
/* 169 */     int i = -1;
/*     */     
/* 171 */     String s = this.ftLevelMin.getText();
/*     */     try {
/* 173 */       i = Integer.parseInt(s);
/*     */     }
/* 175 */     catch (NumberFormatException ex) {
/* 176 */       i = -1;
/*     */     } 
/*     */     
/* 179 */     return i;
/*     */   }
/*     */   
/*     */   public int getMaxLevel() {
/* 183 */     int i = -1;
/*     */     
/* 185 */     String s = this.ftLevelMax.getText();
/*     */     try {
/* 187 */       i = Integer.parseInt(s);
/*     */     }
/* 189 */     catch (NumberFormatException ex) {
/* 190 */       i = -1;
/*     */     } 
/*     */     
/* 193 */     return i;
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 197 */     int minLevel = getMinLevel();
/* 198 */     int maxLevel = getMaxLevel();
/*     */     
/* 200 */     if (minLevel != -1) criteria.levelMin = minLevel; 
/* 201 */     if (maxLevel != -1) criteria.levelMax = maxLevel; 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\select\LevelSelPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */