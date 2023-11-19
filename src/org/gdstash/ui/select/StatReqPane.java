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
/*     */ public class StatReqPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JLabel lblLevelMin;
/*     */   private JLabel lblLevelMax;
/*     */   private JLabel lblCunningMax;
/*     */   private JLabel lblPhysiqueMax;
/*     */   private JLabel lblSpiritMax;
/*     */   private JFormattedTextField ftLevelMin;
/*     */   private JFormattedTextField ftLevelMax;
/*     */   private JFormattedTextField ftCunningMax;
/*     */   private JFormattedTextField ftPhysiqueMax;
/*     */   private JFormattedTextField ftSpiritMax;
/*     */   
/*     */   public StatReqPane() {
/*  41 */     adjustUI();
/*     */     
/*  43 */     GroupLayout layout = null;
/*  44 */     GroupLayout.SequentialGroup hGroup = null;
/*  45 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  47 */     layout = new GroupLayout((Container)this);
/*  48 */     setLayout(layout);
/*     */     
/*  50 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  53 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  56 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  59 */     hGroup
/*  60 */       .addGroup(layout.createParallelGroup()
/*  61 */         .addComponent(this.lblLevelMin)
/*  62 */         .addComponent(this.lblCunningMax))
/*  63 */       .addGroup(layout.createParallelGroup()
/*  64 */         .addComponent(this.ftLevelMin)
/*  65 */         .addComponent(this.ftCunningMax))
/*  66 */       .addGroup(layout.createParallelGroup()
/*  67 */         .addComponent(this.lblLevelMax)
/*  68 */         .addComponent(this.lblPhysiqueMax))
/*  69 */       .addGroup(layout.createParallelGroup()
/*  70 */         .addComponent(this.ftLevelMax)
/*  71 */         .addComponent(this.ftPhysiqueMax))
/*  72 */       .addGroup(layout.createParallelGroup()
/*  73 */         .addComponent(this.lblSpiritMax))
/*  74 */       .addGroup(layout.createParallelGroup()
/*  75 */         .addComponent(this.ftSpiritMax));
/*  76 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  79 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  82 */     vGroup
/*  83 */       .addGroup(layout.createParallelGroup()
/*  84 */         .addComponent(this.lblLevelMin)
/*  85 */         .addComponent(this.ftLevelMin)
/*  86 */         .addComponent(this.lblLevelMax)
/*  87 */         .addComponent(this.ftLevelMax))
/*  88 */       .addGroup(layout.createParallelGroup()
/*  89 */         .addComponent(this.lblCunningMax)
/*  90 */         .addComponent(this.ftCunningMax)
/*  91 */         .addComponent(this.lblPhysiqueMax)
/*  92 */         .addComponent(this.ftPhysiqueMax)
/*  93 */         .addComponent(this.lblSpiritMax)
/*  94 */         .addComponent(this.ftSpiritMax));
/*  95 */     layout.setVerticalGroup(vGroup);
/*     */     
/*  97 */     layout.linkSize(0, new Component[] { this.lblLevelMin, this.ftLevelMin });
/*  98 */     layout.linkSize(0, new Component[] { this.lblLevelMin, this.lblLevelMax });
/*  99 */     layout.linkSize(0, new Component[] { this.lblLevelMin, this.ftLevelMax });
/* 100 */     layout.linkSize(0, new Component[] { this.lblLevelMin, this.lblCunningMax });
/* 101 */     layout.linkSize(0, new Component[] { this.lblLevelMin, this.ftCunningMax });
/* 102 */     layout.linkSize(0, new Component[] { this.lblLevelMin, this.lblPhysiqueMax });
/* 103 */     layout.linkSize(0, new Component[] { this.lblLevelMin, this.ftPhysiqueMax });
/* 104 */     layout.linkSize(0, new Component[] { this.lblLevelMin, this.lblSpiritMax });
/* 105 */     layout.linkSize(0, new Component[] { this.lblLevelMin, this.ftSpiritMax });
/*     */     
/* 107 */     layout.linkSize(1, new Component[] { this.lblLevelMin, this.ftLevelMin });
/* 108 */     layout.linkSize(1, new Component[] { this.lblLevelMin, this.lblLevelMax });
/* 109 */     layout.linkSize(1, new Component[] { this.lblLevelMin, this.ftLevelMax });
/* 110 */     layout.linkSize(1, new Component[] { this.lblLevelMin, this.lblCunningMax });
/* 111 */     layout.linkSize(1, new Component[] { this.lblLevelMin, this.ftCunningMax });
/* 112 */     layout.linkSize(1, new Component[] { this.lblLevelMin, this.lblPhysiqueMax });
/* 113 */     layout.linkSize(1, new Component[] { this.lblLevelMin, this.ftPhysiqueMax });
/* 114 */     layout.linkSize(1, new Component[] { this.lblLevelMin, this.lblSpiritMax });
/* 115 */     layout.linkSize(1, new Component[] { this.lblLevelMin, this.ftSpiritMax });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 120 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 121 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 122 */     if (fntBorder == null) fntBorder = fntLabel; 
/* 123 */     Font fntFText = UIManager.getDefaults().getFont("FormattedTextField.font");
/* 124 */     if (fntFText == null) fntFText = fntLabel;
/*     */     
/* 126 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 127 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 128 */     fntFText = fntFText.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 130 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 131 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 132 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 133 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_REQUIREMENTS"));
/* 134 */     text.setTitleFont(fntBorder);
/*     */     
/* 136 */     setBorder(text);
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
/*     */     
/* 162 */     if (this.lblCunningMax == null) this.lblCunningMax = new JLabel(); 
/* 163 */     this.lblCunningMax.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CUNNING_MAX"));
/* 164 */     this.lblCunningMax.setFont(fntLabel);
/*     */     
/* 166 */     if (this.ftCunningMax == null) {
/* 167 */       this.ftCunningMax = new JFormattedTextField();
/* 168 */       DocumentFilter filter = new IntLenDocFilter(3);
/* 169 */       AbstractDocument doc = (AbstractDocument)this.ftCunningMax.getDocument();
/* 170 */       doc.setDocumentFilter(filter);
/*     */     } 
/* 172 */     this.ftCunningMax.setFont(fntFText);
/*     */     
/* 174 */     if (this.lblPhysiqueMax == null) this.lblPhysiqueMax = new JLabel(); 
/* 175 */     this.lblPhysiqueMax.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_PHYSIQUE_MAX"));
/* 176 */     this.lblPhysiqueMax.setFont(fntLabel);
/*     */     
/* 178 */     if (this.ftPhysiqueMax == null) {
/* 179 */       this.ftPhysiqueMax = new JFormattedTextField();
/* 180 */       DocumentFilter filter = new IntLenDocFilter(3);
/* 181 */       AbstractDocument doc = (AbstractDocument)this.ftPhysiqueMax.getDocument();
/* 182 */       doc.setDocumentFilter(filter);
/*     */     } 
/* 184 */     this.ftPhysiqueMax.setFont(fntFText);
/*     */     
/* 186 */     if (this.lblSpiritMax == null) this.lblSpiritMax = new JLabel(); 
/* 187 */     this.lblSpiritMax.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SPIRIT_MAX"));
/* 188 */     this.lblSpiritMax.setFont(fntLabel);
/*     */     
/* 190 */     if (this.ftSpiritMax == null) {
/* 191 */       this.ftSpiritMax = new JFormattedTextField();
/* 192 */       DocumentFilter filter = new IntLenDocFilter(3);
/* 193 */       AbstractDocument doc = (AbstractDocument)this.ftSpiritMax.getDocument();
/* 194 */       doc.setDocumentFilter(filter);
/*     */     } 
/* 196 */     this.ftSpiritMax.setFont(fntFText);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 200 */     this.ftLevelMin.setText(null);
/* 201 */     this.ftLevelMax.setText(null);
/* 202 */     this.ftCunningMax.setText(null);
/* 203 */     this.ftPhysiqueMax.setText(null);
/* 204 */     this.ftSpiritMax.setText(null);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 208 */     String s = null;
/* 209 */     int i = 0;
/*     */     
/* 211 */     s = this.ftLevelMin.getText();
/*     */     try {
/* 213 */       i = Integer.parseInt(s);
/*     */       
/* 215 */       criteria.levelMin = i;
/*     */     }
/* 217 */     catch (NumberFormatException numberFormatException) {}
/*     */     
/* 219 */     s = this.ftLevelMax.getText();
/*     */     try {
/* 221 */       i = Integer.parseInt(s);
/*     */       
/* 223 */       criteria.levelMax = i;
/*     */     }
/* 225 */     catch (NumberFormatException numberFormatException) {}
/*     */     
/* 227 */     s = this.ftCunningMax.getText();
/*     */     try {
/* 229 */       i = Integer.parseInt(s);
/*     */       
/* 231 */       criteria.cunningMax = i;
/*     */     }
/* 233 */     catch (NumberFormatException numberFormatException) {}
/*     */     
/* 235 */     s = this.ftPhysiqueMax.getText();
/*     */     try {
/* 237 */       i = Integer.parseInt(s);
/*     */       
/* 239 */       criteria.physiqueMax = i;
/*     */     }
/* 241 */     catch (NumberFormatException numberFormatException) {}
/*     */     
/* 243 */     s = this.ftSpiritMax.getText();
/*     */     try {
/* 245 */       i = Integer.parseInt(s);
/*     */       
/* 247 */       criteria.spiritMax = i;
/*     */     }
/* 249 */     catch (NumberFormatException numberFormatException) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\select\StatReqPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */