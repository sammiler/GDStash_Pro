/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JFormattedTextField;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.text.AbstractDocument;
/*     */ import javax.swing.text.DocumentFilter;
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
/*     */ public class StatSelPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JLabel lblPhysiqueMax;
/*     */   private JLabel lblCunningMax;
/*     */   private JLabel lblSpiritMax;
/*     */   private JFormattedTextField ftPhysiqueMax;
/*     */   private JFormattedTextField ftCunningMax;
/*     */   private JFormattedTextField ftSpiritMax;
/*     */   
/*     */   public StatSelPane(int direction) {
/*  36 */     adjustUI();
/*     */     
/*  38 */     GroupLayout layout = null;
/*  39 */     GroupLayout.SequentialGroup hGroup = null;
/*  40 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  42 */     layout = new GroupLayout((Container)this);
/*  43 */     setLayout(layout);
/*     */     
/*  45 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  48 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  51 */     hGroup = layout.createSequentialGroup();
/*     */     
/*  53 */     if (direction == 0) {
/*     */       
/*  55 */       hGroup
/*  56 */         .addGroup(layout.createParallelGroup()
/*  57 */           .addComponent(this.lblPhysiqueMax))
/*  58 */         .addGroup(layout.createParallelGroup()
/*  59 */           .addComponent(this.ftPhysiqueMax))
/*  60 */         .addGroup(layout.createParallelGroup()
/*  61 */           .addComponent(this.lblCunningMax))
/*  62 */         .addGroup(layout.createParallelGroup()
/*  63 */           .addComponent(this.ftCunningMax))
/*  64 */         .addGroup(layout.createParallelGroup()
/*  65 */           .addComponent(this.lblSpiritMax))
/*  66 */         .addGroup(layout.createParallelGroup()
/*  67 */           .addComponent(this.ftSpiritMax));
/*     */     } else {
/*     */       
/*  70 */       hGroup
/*  71 */         .addGroup(layout.createParallelGroup()
/*  72 */           .addComponent(this.lblPhysiqueMax)
/*  73 */           .addComponent(this.lblCunningMax)
/*  74 */           .addComponent(this.lblSpiritMax))
/*  75 */         .addGroup(layout.createParallelGroup()
/*  76 */           .addComponent(this.ftPhysiqueMax)
/*  77 */           .addComponent(this.ftCunningMax)
/*  78 */           .addComponent(this.ftSpiritMax));
/*     */     } 
/*  80 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  83 */     vGroup = layout.createSequentialGroup();
/*     */     
/*  85 */     if (direction == 0) {
/*     */       
/*  87 */       vGroup
/*  88 */         .addGroup(layout.createParallelGroup()
/*  89 */           .addComponent(this.lblPhysiqueMax))
/*  90 */         .addGroup(layout.createParallelGroup()
/*  91 */           .addComponent(this.ftPhysiqueMax))
/*  92 */         .addGroup(layout.createParallelGroup()
/*  93 */           .addComponent(this.lblCunningMax))
/*  94 */         .addGroup(layout.createParallelGroup()
/*  95 */           .addComponent(this.ftCunningMax))
/*  96 */         .addGroup(layout.createParallelGroup()
/*  97 */           .addComponent(this.lblSpiritMax))
/*  98 */         .addGroup(layout.createParallelGroup()
/*  99 */           .addComponent(this.ftSpiritMax));
/*     */     } else {
/*     */       
/* 102 */       vGroup
/* 103 */         .addGroup(layout.createParallelGroup()
/* 104 */           .addComponent(this.lblPhysiqueMax)
/* 105 */           .addComponent(this.ftPhysiqueMax))
/* 106 */         .addGroup(layout.createParallelGroup()
/* 107 */           .addComponent(this.lblCunningMax)
/* 108 */           .addComponent(this.ftCunningMax))
/* 109 */         .addGroup(layout.createParallelGroup()
/* 110 */           .addComponent(this.lblSpiritMax)
/* 111 */           .addComponent(this.ftSpiritMax));
/*     */     } 
/* 113 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 115 */     layout.linkSize(0, new Component[] { this.lblPhysiqueMax, this.ftPhysiqueMax });
/* 116 */     layout.linkSize(0, new Component[] { this.lblPhysiqueMax, this.ftCunningMax });
/* 117 */     layout.linkSize(0, new Component[] { this.lblPhysiqueMax, this.lblCunningMax });
/* 118 */     layout.linkSize(0, new Component[] { this.lblPhysiqueMax, this.lblSpiritMax });
/* 119 */     layout.linkSize(0, new Component[] { this.lblPhysiqueMax, this.ftSpiritMax });
/*     */     
/* 121 */     layout.linkSize(1, new Component[] { this.lblPhysiqueMax, this.ftPhysiqueMax });
/* 122 */     layout.linkSize(1, new Component[] { this.lblPhysiqueMax, this.ftCunningMax });
/* 123 */     layout.linkSize(1, new Component[] { this.lblPhysiqueMax, this.lblCunningMax });
/* 124 */     layout.linkSize(1, new Component[] { this.lblPhysiqueMax, this.lblSpiritMax });
/* 125 */     layout.linkSize(1, new Component[] { this.lblPhysiqueMax, this.ftSpiritMax });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 130 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 131 */     Font fntFText = UIManager.getDefaults().getFont("FormattedTextField.font");
/* 132 */     if (fntFText == null) fntFText = fntLabel;
/*     */     
/* 134 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 135 */     fntFText = fntFText.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 137 */     if (this.lblPhysiqueMax == null) this.lblPhysiqueMax = new JLabel(); 
/* 138 */     this.lblPhysiqueMax.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_PHYSIQUE_MAX"));
/* 139 */     this.lblPhysiqueMax.setFont(fntLabel);
/*     */     
/* 141 */     if (this.ftPhysiqueMax == null) {
/* 142 */       this.ftPhysiqueMax = new JFormattedTextField();
/* 143 */       DocumentFilter filter = new IntLenDocFilter(3);
/* 144 */       AbstractDocument doc = (AbstractDocument)this.ftPhysiqueMax.getDocument();
/* 145 */       doc.setDocumentFilter(filter);
/*     */     } 
/* 147 */     this.ftPhysiqueMax.setFont(fntFText);
/*     */     
/* 149 */     if (this.lblCunningMax == null) this.lblCunningMax = new JLabel(); 
/* 150 */     this.lblCunningMax.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CUNNING_MAX"));
/* 151 */     this.lblCunningMax.setFont(fntLabel);
/*     */     
/* 153 */     if (this.ftCunningMax == null) {
/* 154 */       this.ftCunningMax = new JFormattedTextField();
/* 155 */       DocumentFilter filter = new IntLenDocFilter(3);
/* 156 */       AbstractDocument doc = (AbstractDocument)this.ftCunningMax.getDocument();
/* 157 */       doc.setDocumentFilter(filter);
/*     */     } 
/* 159 */     this.ftCunningMax.setFont(fntFText);
/*     */     
/* 161 */     if (this.lblSpiritMax == null) this.lblSpiritMax = new JLabel(); 
/* 162 */     this.lblSpiritMax.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SPIRIT_MAX"));
/* 163 */     this.lblSpiritMax.setFont(fntLabel);
/*     */     
/* 165 */     if (this.ftSpiritMax == null) {
/* 166 */       this.ftSpiritMax = new JFormattedTextField();
/* 167 */       DocumentFilter filter = new IntLenDocFilter(3);
/* 168 */       AbstractDocument doc = (AbstractDocument)this.ftSpiritMax.getDocument();
/* 169 */       doc.setDocumentFilter(filter);
/*     */     } 
/* 171 */     this.ftSpiritMax.setFont(fntFText);
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\StatSelPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */