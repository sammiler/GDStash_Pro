/*     */ package org.gdstash.ui;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import org.gdstash.db.DBStashItem;
/*     */ import org.gdstash.item.GDItem;
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
/*     */ 
/*     */ 
/*     */ public class GDItemInfoPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JLabel lblImage;
/*     */   private JLabel lblFullName;
/*     */   private JLabel lblSet;
/*     */   private JLabel lblSeed;
/*     */   private GDItemInfoSimilarTablePane pnlSimilar;
/*     */   private JLabel txtFullName;
/*     */   private JLabel txtSet;
/*     */   private JLabel txtSeed;
/*     */   private JLabel txtDescription;
/*     */   
/*     */   public GDItemInfoPane() {
/*  45 */     adjustUI();
/*     */     
/*  47 */     GroupLayout layout = null;
/*  48 */     GroupLayout.SequentialGroup hGroup = null;
/*  49 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  51 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  52 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  53 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */     
/*  55 */     JPanel panel = buildInfoPanel();
/*     */     
/*  57 */     layout = new GroupLayout((Container)this);
/*  58 */     setLayout(layout);
/*     */     
/*  60 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  63 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  66 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  69 */     hGroup
/*  70 */       .addGroup(layout.createParallelGroup()
/*  71 */         .addComponent(this.lblImage)
/*  72 */         .addComponent((Component)this.pnlSimilar)
/*  73 */         .addComponent(panel)
/*  74 */         .addComponent(this.txtDescription));
/*  75 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  78 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  81 */     vGroup
/*  82 */       .addGroup(layout.createParallelGroup()
/*  83 */         .addComponent(this.lblImage))
/*  84 */       .addGroup(layout.createParallelGroup()
/*  85 */         .addComponent((Component)this.pnlSimilar))
/*  86 */       .addGroup(layout.createParallelGroup()
/*  87 */         .addComponent(panel))
/*  88 */       .addGroup(layout.createParallelGroup()
/*  89 */         .addComponent(this.txtDescription));
/*  90 */     layout.setVerticalGroup(vGroup);
/*     */     
/*  92 */     layout.linkSize(0, new Component[] { this.lblImage, (Component)this.pnlSimilar });
/*  93 */     layout.linkSize(0, new Component[] { this.lblImage, panel });
/*  94 */     layout.linkSize(0, new Component[] { this.lblImage, this.txtDescription });
/*     */   }
/*     */   
/*     */   public void setWidth(int w) {
/*  98 */     int size = 12;
/*  99 */     if (GDStashFrame.iniConfig != null) size = GDStashFrame.iniConfig.sectUI.fontSize;
/*     */     
/* 101 */     int i = w - 4 * size;
/* 102 */     if (i < size) i = size; 
/* 103 */     Dimension dimMax = new Dimension(i, 2 * size);
/*     */     
/* 105 */     this.txtFullName.setPreferredSize(dimMax);
/* 106 */     this.txtFullName.setMaximumSize(dimMax);
/*     */     
/* 108 */     invalidate();
/* 109 */     doLayout();
/* 110 */     repaint();
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 115 */     int size = 12;
/* 116 */     if (GDStashFrame.iniConfig != null) size = GDStashFrame.iniConfig.sectUI.fontSize; 
/* 117 */     Dimension dimMax = new Dimension(37 * size, 2 * size);
/*     */     
/* 119 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*     */     
/* 121 */     fntLabel = fntLabel.deriveFont(size);
/*     */     
/* 123 */     Border compound = null;
/*     */     
/* 125 */     if (this.txtFullName == null || this.txtSet == null || this.txtSeed == null) {
/*     */ 
/*     */       
/* 128 */       Border lowered = BorderFactory.createEtchedBorder(1);
/* 129 */       Border raised = BorderFactory.createEtchedBorder(0);
/* 130 */       compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */     } 
/*     */     
/* 133 */     if (this.lblImage == null) {
/* 134 */       this.lblImage = new JLabel();
/* 135 */       this.lblImage.setBorder(compound);
/* 136 */       this.lblImage.setHorizontalAlignment(0);
/*     */     } 
/*     */     
/* 139 */     if (this.lblFullName == null) this.lblFullName = new JLabel(); 
/* 140 */     this.lblFullName.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_NAME"));
/* 141 */     this.lblFullName.setFont(fntLabel);
/*     */     
/* 143 */     if (this.txtFullName == null) {
/* 144 */       this.txtFullName = new JLabel();
/*     */       
/* 146 */       this.txtFullName.setBorder(compound);
/*     */     } 
/* 148 */     this.txtFullName.setFont(fntLabel);
/* 149 */     this.txtFullName.setPreferredSize(dimMax);
/* 150 */     this.txtFullName.setMaximumSize(dimMax);
/*     */     
/* 152 */     if (this.lblSet == null) this.lblSet = new JLabel(); 
/* 153 */     this.lblSet.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_SET"));
/* 154 */     this.lblSet.setFont(fntLabel);
/*     */     
/* 156 */     if (this.txtSet == null) {
/* 157 */       this.txtSet = new JLabel();
/*     */       
/* 159 */       this.txtSet.setBorder(compound);
/*     */     } 
/* 161 */     this.txtSet.setFont(fntLabel);
/*     */     
/* 163 */     if (this.lblSeed == null) this.lblSeed = new JLabel(); 
/* 164 */     this.lblSeed.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_SEED"));
/* 165 */     this.lblSeed.setFont(fntLabel);
/*     */     
/* 167 */     if (this.txtSeed == null) {
/* 168 */       this.txtSeed = new JLabel();
/*     */       
/* 170 */       this.txtSeed.setBorder(compound);
/*     */     } 
/* 172 */     this.txtSeed.setFont(fntLabel);
/*     */     
/* 174 */     if (this.pnlSimilar == null) {
/* 175 */       this.pnlSimilar = new GDItemInfoSimilarTablePane();
/*     */     } else {
/* 177 */       this.pnlSimilar.adjustUI();
/*     */     } 
/*     */     
/* 180 */     if (this.txtDescription == null) {
/* 181 */       this.txtDescription = new JLabel();
/* 182 */       this.txtDescription.setBorder(compound);
/*     */     } 
/* 184 */     this.txtDescription.setFont(fntLabel);
/*     */   }
/*     */   
/*     */   public void refresh() {
/* 188 */     setItemInfo((GDItem)null, false, "");
/*     */   }
/*     */   
/*     */   private JPanel buildInfoPanel() {
/* 192 */     GroupLayout layout = null;
/* 193 */     GroupLayout.SequentialGroup hGroup = null;
/* 194 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 196 */     JPanel panel = new JPanel();
/*     */     
/* 198 */     layout = new GroupLayout(panel);
/* 199 */     panel.setLayout(layout);
/*     */     
/* 201 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 204 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 207 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 210 */     hGroup
/* 211 */       .addGroup(layout.createParallelGroup()
/* 212 */         .addComponent(this.lblFullName)
/* 213 */         .addComponent(this.lblSet)
/* 214 */         .addComponent(this.lblSeed))
/* 215 */       .addGroup(layout.createParallelGroup()
/* 216 */         .addComponent(this.txtFullName)
/* 217 */         .addComponent(this.txtSet)
/* 218 */         .addComponent(this.txtSeed));
/* 219 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 222 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 225 */     vGroup
/* 226 */       .addGroup(layout.createParallelGroup()
/* 227 */         .addComponent(this.lblFullName)
/* 228 */         .addComponent(this.txtFullName))
/* 229 */       .addGroup(layout.createParallelGroup()
/* 230 */         .addComponent(this.lblSet)
/* 231 */         .addComponent(this.txtSet))
/* 232 */       .addGroup(layout.createParallelGroup()
/* 233 */         .addComponent(this.lblSeed)
/* 234 */         .addComponent(this.txtSeed));
/* 235 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 237 */     layout.linkSize(0, new Component[] { this.lblFullName, this.lblSet });
/* 238 */     layout.linkSize(0, new Component[] { this.lblFullName, this.lblSeed });
/*     */     
/* 240 */     layout.linkSize(0, new Component[] { this.txtFullName, this.txtSet });
/* 241 */     layout.linkSize(0, new Component[] { this.txtFullName, this.txtSeed });
/*     */     
/* 243 */     layout.linkSize(1, new Component[] { this.lblFullName, this.txtFullName });
/* 244 */     layout.linkSize(1, new Component[] { this.lblSet, this.txtSet });
/* 245 */     layout.linkSize(1, new Component[] { this.lblSeed, this.txtSeed });
/*     */     
/* 247 */     return panel;
/*     */   }
/*     */   
/*     */   private void setInfo(GDItem.LabelInfo info, JLabel label) {
/* 251 */     if (info == null) {
/* 252 */       label.setText("");
/*     */     } else {
/* 254 */       label.setForeground(info.foreground);
/* 255 */       label.setText(info.text);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setFullNameInfo(GDItem.LabelInfo info) {
/* 260 */     setInfo(info, this.txtFullName);
/*     */   }
/*     */   
/*     */   public void setSetInfo(GDItem.LabelInfo info) {
/* 264 */     setInfo(info, this.txtSet);
/*     */   }
/*     */   
/*     */   public void setSeedInfo(GDItem.LabelInfo info) {
/* 268 */     setInfo(info, this.txtSeed);
/*     */   }
/*     */   
/*     */   public void setDescription(String text) {
/* 272 */     this.txtDescription.setText(text);
/*     */   }
/*     */   
/*     */   public void setImage(Icon icon) {
/* 276 */     this.lblImage.setIcon(icon);
/*     */   }
/*     */   
/*     */   public void setItemInfo(GDItem item, boolean isHardcore, String charName) {
/* 280 */     if (item == null) {
/* 281 */       GDItem.LabelInfo li = new GDItem.LabelInfo();
/* 282 */       li.text = "";
/*     */       
/* 284 */       setImage((Icon)null);
/* 285 */       setFullNameInfo(li);
/* 286 */       setSetInfo(li);
/* 287 */       setSeedInfo(li);
/* 288 */       this.pnlSimilar.setInfo(null);
/* 289 */       setDescription("");
/*     */     } else {
/* 291 */       setImage(item.getImageIcon());
/* 292 */       setFullNameInfo(item.getCompleteNameInfo(false, true));
/* 293 */       setSetInfo(item.getItemSetNameInfo());
/* 294 */       setSeedInfo(item.getSeedHexInfo());
/*     */       
/* 296 */       DBStashItem.DuplicateInfo info = DBStashItem.determineSimilarItems(item, isHardcore, charName);
/* 297 */       this.pnlSimilar.setInfo(info);
/*     */       
/* 299 */       String s = item.getFullDescription();
/* 300 */       setDescription(s);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\GDItemInfoPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */