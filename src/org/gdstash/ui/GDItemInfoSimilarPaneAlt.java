/*     */ package org.gdstash.ui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import org.gdstash.db.DBStashItem;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ import org.gdstash.util.GDColor;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDItemInfoSimilarPaneAlt
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JLabel lblItemCombo;
/*     */   private JLabel lblItemPrefix;
/*     */   private JLabel lblItemSuffix;
/*     */   private JLabel lblClassCombo;
/*     */   private JLabel txtItemCombo;
/*     */   private JLabel txtItemPrefix;
/*     */   private JLabel txtItemPrefixLevel;
/*     */   private JLabel txtItemSuffix;
/*     */   private JLabel txtItemSuffixLevel;
/*     */   private JLabel txtClassCombo;
/*     */   
/*     */   public GDItemInfoSimilarPaneAlt() {
/*  40 */     adjustUI();
/*     */     
/*  42 */     GroupLayout layout = null;
/*  43 */     GroupLayout.SequentialGroup hGroup = null;
/*  44 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  46 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  47 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  48 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */     
/*  50 */     layout = new GroupLayout((Container)this);
/*  51 */     setLayout(layout);
/*     */     
/*  53 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  56 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/*  59 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  62 */     hGroup
/*  63 */       .addGroup(layout.createParallelGroup()
/*  64 */         .addComponent(this.lblItemCombo))
/*  65 */       .addGroup(layout.createParallelGroup()
/*  66 */         .addComponent(this.txtItemCombo))
/*  67 */       .addGroup(layout.createParallelGroup()
/*  68 */         .addComponent(this.lblClassCombo))
/*  69 */       .addGroup(layout.createParallelGroup()
/*  70 */         .addComponent(this.txtClassCombo))
/*  71 */       .addGroup(layout.createParallelGroup()
/*  72 */         .addComponent(this.lblItemPrefix))
/*  73 */       .addGroup(layout.createParallelGroup()
/*  74 */         .addComponent(this.txtItemPrefix))
/*  75 */       .addGroup(layout.createParallelGroup()
/*  76 */         .addComponent(this.txtItemPrefixLevel))
/*  77 */       .addGroup(layout.createParallelGroup()
/*  78 */         .addComponent(this.lblItemSuffix))
/*  79 */       .addGroup(layout.createParallelGroup()
/*  80 */         .addComponent(this.txtItemSuffix))
/*  81 */       .addGroup(layout.createParallelGroup()
/*  82 */         .addComponent(this.txtItemSuffixLevel));
/*  83 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  86 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  89 */     vGroup
/*  90 */       .addGroup(layout.createParallelGroup()
/*  91 */         .addComponent(this.lblItemCombo)
/*  92 */         .addComponent(this.txtItemCombo)
/*  93 */         .addComponent(this.lblClassCombo)
/*  94 */         .addComponent(this.txtClassCombo)
/*  95 */         .addComponent(this.lblItemPrefix)
/*  96 */         .addComponent(this.txtItemPrefix)
/*  97 */         .addComponent(this.txtItemPrefixLevel)
/*  98 */         .addComponent(this.lblItemSuffix)
/*  99 */         .addComponent(this.txtItemSuffix)
/* 100 */         .addComponent(this.txtItemSuffixLevel));
/* 101 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 103 */     layout.linkSize(0, new Component[] { this.lblItemCombo, this.lblClassCombo });
/* 104 */     layout.linkSize(0, new Component[] { this.lblItemCombo, this.lblItemPrefix });
/* 105 */     layout.linkSize(0, new Component[] { this.lblItemCombo, this.lblItemSuffix });
/*     */     
/* 107 */     layout.linkSize(0, new Component[] { this.txtItemCombo, this.txtClassCombo });
/* 108 */     layout.linkSize(0, new Component[] { this.txtItemCombo, this.txtItemPrefix });
/* 109 */     layout.linkSize(0, new Component[] { this.txtItemCombo, this.txtItemPrefixLevel });
/* 110 */     layout.linkSize(0, new Component[] { this.txtItemCombo, this.txtItemSuffix });
/* 111 */     layout.linkSize(0, new Component[] { this.txtItemCombo, this.txtItemSuffixLevel });
/*     */     
/* 113 */     layout.linkSize(1, new Component[] { this.lblItemCombo, this.lblClassCombo });
/* 114 */     layout.linkSize(1, new Component[] { this.lblItemCombo, this.lblItemPrefix });
/* 115 */     layout.linkSize(1, new Component[] { this.lblItemCombo, this.lblItemSuffix });
/* 116 */     layout.linkSize(1, new Component[] { this.lblItemCombo, this.txtItemCombo });
/* 117 */     layout.linkSize(1, new Component[] { this.lblItemCombo, this.txtClassCombo });
/* 118 */     layout.linkSize(1, new Component[] { this.lblItemCombo, this.txtItemPrefix });
/* 119 */     layout.linkSize(1, new Component[] { this.lblItemCombo, this.txtItemPrefixLevel });
/* 120 */     layout.linkSize(1, new Component[] { this.lblItemCombo, this.txtItemSuffix });
/* 121 */     layout.linkSize(1, new Component[] { this.lblItemCombo, this.txtItemSuffixLevel });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 126 */     int size = 12;
/* 127 */     if (GDStashFrame.iniConfig != null) size = GDStashFrame.iniConfig.sectUI.fontSize; 
/* 128 */     Dimension dimMax = new Dimension(4 * size, 2 * size);
/*     */     
/* 130 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*     */     
/* 132 */     fntLabel = fntLabel.deriveFont(size);
/*     */     
/* 134 */     Border border = null;
/*     */     
/* 136 */     if (this.txtItemCombo == null || this.txtItemPrefix == null || this.txtItemSuffix == null || this.txtClassCombo == null)
/*     */     {
/*     */ 
/*     */       
/* 140 */       border = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
/*     */     }
/*     */     
/* 143 */     if (this.lblItemCombo == null) this.lblItemCombo = new JLabel(); 
/* 144 */     this.lblItemCombo.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_NUM_ITEM"));
/* 145 */     this.lblItemCombo.setFont(fntLabel);
/*     */     
/* 147 */     if (this.txtItemCombo == null) {
/* 148 */       this.txtItemCombo = new JLabel();
/*     */       
/* 150 */       this.txtItemCombo.setBorder(border);
/*     */     } 
/* 152 */     this.txtItemCombo.setFont(fntLabel);
/* 153 */     this.txtItemCombo.setPreferredSize(dimMax);
/* 154 */     this.txtItemCombo.setMaximumSize(dimMax);
/*     */     
/* 156 */     if (this.lblClassCombo == null) this.lblClassCombo = new JLabel(); 
/* 157 */     this.lblClassCombo.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_NUM_ITEM_CLASS"));
/* 158 */     this.lblClassCombo.setFont(fntLabel);
/*     */     
/* 160 */     if (this.txtClassCombo == null) {
/* 161 */       this.txtClassCombo = new JLabel();
/*     */       
/* 163 */       this.txtClassCombo.setBorder(border);
/*     */     } 
/* 165 */     this.txtClassCombo.setFont(fntLabel);
/*     */     
/* 167 */     if (this.lblItemPrefix == null) this.lblItemPrefix = new JLabel(); 
/* 168 */     this.lblItemPrefix.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_NUM_PREFIX"));
/* 169 */     this.lblItemPrefix.setFont(fntLabel);
/*     */     
/* 171 */     if (this.txtItemPrefix == null) {
/* 172 */       this.txtItemPrefix = new JLabel();
/*     */       
/* 174 */       this.txtItemPrefix.setBorder(border);
/*     */     } 
/* 176 */     this.txtItemPrefix.setFont(fntLabel);
/*     */     
/* 178 */     if (this.txtItemPrefixLevel == null) {
/* 179 */       this.txtItemPrefixLevel = new JLabel();
/*     */       
/* 181 */       this.txtItemPrefixLevel.setBorder(border);
/*     */     } 
/* 183 */     this.txtItemPrefixLevel.setFont(fntLabel);
/*     */     
/* 185 */     if (this.lblItemSuffix == null) this.lblItemSuffix = new JLabel(); 
/* 186 */     this.lblItemSuffix.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_NUM_SUFFIX"));
/* 187 */     this.lblItemSuffix.setFont(fntLabel);
/*     */     
/* 189 */     if (this.txtItemSuffix == null) {
/* 190 */       this.txtItemSuffix = new JLabel();
/*     */       
/* 192 */       this.txtItemSuffix.setBorder(border);
/*     */     } 
/* 194 */     this.txtItemSuffix.setFont(fntLabel);
/*     */     
/* 196 */     if (this.txtItemSuffixLevel == null) {
/* 197 */       this.txtItemSuffixLevel = new JLabel();
/*     */       
/* 199 */       this.txtItemSuffixLevel.setBorder(border);
/*     */     } 
/* 201 */     this.txtItemSuffixLevel.setFont(fntLabel);
/*     */   }
/*     */   
/*     */   public void setInfo(DBStashItem.DuplicateInfo info) {
/* 205 */     if (info == null) {
/* 206 */       this.lblItemCombo.setForeground(GDColor.COLOR_FG_COMMON);
/* 207 */       this.lblItemPrefix.setForeground(GDColor.COLOR_FG_COMMON);
/* 208 */       this.lblItemSuffix.setForeground(GDColor.COLOR_FG_COMMON);
/* 209 */       this.lblItemSuffix.setForeground(GDColor.COLOR_FG_COMMON);
/*     */       
/* 211 */       this.txtItemCombo.setForeground(GDColor.COLOR_FG_COMMON);
/* 212 */       this.txtClassCombo.setForeground(GDColor.COLOR_FG_COMMON);
/* 213 */       this.txtItemPrefix.setForeground(GDColor.COLOR_FG_COMMON);
/* 214 */       this.txtItemPrefixLevel.setForeground(GDColor.COLOR_FG_COMMON);
/* 215 */       this.txtItemSuffix.setForeground(GDColor.COLOR_FG_COMMON);
/* 216 */       this.txtItemSuffixLevel.setForeground(GDColor.COLOR_FG_COMMON);
/*     */       
/* 218 */       this.txtItemCombo.setText("");
/* 219 */       this.txtClassCombo.setText("");
/* 220 */       this.txtItemPrefix.setText("");
/* 221 */       this.txtItemPrefixLevel.setText("");
/* 222 */       this.txtItemSuffix.setText("");
/* 223 */       this.txtItemSuffixLevel.setText("");
/*     */     } else {
/* 225 */       this.lblItemCombo.setForeground(info.numItemCombo.foreground);
/* 226 */       this.lblItemPrefix.setForeground(info.numItemPrefix.foreground);
/* 227 */       this.lblItemSuffix.setForeground(info.numItemSuffix.foreground);
/* 228 */       this.lblItemSuffix.setForeground(info.numItemSuffix.foreground);
/*     */       
/* 230 */       this.txtItemCombo.setForeground(info.numItemCombo.foreground);
/* 231 */       this.txtClassCombo.setForeground(info.numClassCombo.foreground);
/* 232 */       this.txtItemPrefix.setForeground(info.numItemPrefix.foreground);
/* 233 */       this.txtItemPrefixLevel.setForeground(info.levelItemPrefix.foreground);
/* 234 */       this.txtItemSuffix.setForeground(info.numItemSuffix.foreground);
/* 235 */       this.txtItemSuffixLevel.setForeground(info.levelItemSuffix.foreground);
/*     */       
/* 237 */       this.txtItemCombo.setText(info.numItemCombo.text);
/* 238 */       this.txtClassCombo.setText(info.numClassCombo.text);
/* 239 */       this.txtItemPrefix.setText(info.numItemPrefix.text);
/* 240 */       this.txtItemPrefixLevel.setText(info.levelItemPrefix.text);
/* 241 */       this.txtItemSuffix.setText(info.numItemSuffix.text);
/* 242 */       this.txtItemSuffixLevel.setText(info.levelItemSuffix.text);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\GDItemInfoSimilarPaneAlt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */