/*     */ package org.gdstash.ui.character;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.util.AdjustableTabbedPane;
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
/*     */ public class GDCharRiftShrineTabbedPane
/*     */   extends AdjustableTabbedPane
/*     */ {
/*     */   private TitledBorder brdRift;
/*     */   private GDCharRiftPane pnlRiftNormal;
/*     */   private GDCharRiftPane pnlRiftElite;
/*     */   private GDCharRiftPane pnlRiftUltimate;
/*     */   private TitledBorder brdShrine;
/*     */   private GDCharShrinePane pnlShrineNormal;
/*     */   private GDCharShrinePane pnlShrineElite;
/*     */   private GDCharShrinePane pnlShrineUltimate;
/*     */   
/*     */   public GDCharRiftShrineTabbedPane() {
/*  37 */     adjustUI();
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  42 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  43 */     Font fntTabbed = UIManager.getDefaults().getFont("TabbedPane.font");
/*  44 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/*  45 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/*  47 */     fntTabbed = fntTabbed.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  48 */     setFont(fntTabbed);
/*     */     
/*  50 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/*  52 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  53 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  54 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */     
/*  56 */     this.brdRift = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_CHAR_RIFTS"));
/*  57 */     this.brdRift.setTitleFont(fntBorder);
/*     */     
/*  59 */     if (this.pnlRiftNormal == null) {
/*  60 */       this.pnlRiftNormal = new GDCharRiftPane(0);
/*     */     } else {
/*  62 */       this.pnlRiftNormal.adjustUI();
/*     */     } 
/*  64 */     this.pnlRiftNormal.setBorder(this.brdRift);
/*     */     
/*  66 */     if (this.pnlRiftElite == null) {
/*  67 */       this.pnlRiftElite = new GDCharRiftPane(1);
/*     */     } else {
/*  69 */       this.pnlRiftElite.adjustUI();
/*     */     } 
/*  71 */     this.pnlRiftElite.setBorder(this.brdRift);
/*     */     
/*  73 */     if (this.pnlRiftUltimate == null) {
/*  74 */       this.pnlRiftUltimate = new GDCharRiftPane(2);
/*     */     } else {
/*  76 */       this.pnlRiftUltimate.adjustUI();
/*     */     } 
/*  78 */     this.pnlRiftUltimate.setBorder(this.brdRift);
/*     */     
/*  80 */     this.brdShrine = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_CHAR_SHRINES"));
/*  81 */     this.brdShrine.setTitleFont(fntBorder);
/*     */     
/*  83 */     if (this.pnlShrineNormal == null) {
/*  84 */       this.pnlShrineNormal = new GDCharShrinePane(0);
/*     */     } else {
/*  86 */       this.pnlShrineNormal.adjustUI();
/*     */     } 
/*  88 */     this.pnlShrineNormal.setBorder(this.brdShrine);
/*     */     
/*  90 */     if (this.pnlShrineElite == null) {
/*  91 */       this.pnlShrineElite = new GDCharShrinePane(1);
/*     */     } else {
/*  93 */       this.pnlShrineElite.adjustUI();
/*     */     } 
/*  95 */     this.pnlShrineElite.setBorder(this.brdShrine);
/*     */     
/*  97 */     if (this.pnlShrineUltimate == null) {
/*  98 */       this.pnlShrineUltimate = new GDCharShrinePane(2);
/*     */     } else {
/* 100 */       this.pnlShrineUltimate.adjustUI();
/*     */     } 
/* 102 */     this.pnlShrineUltimate.setBorder(this.brdShrine);
/*     */     
/* 104 */     if (getTabCount() == 0) {
/* 105 */       JPanel pnlNormal = buildPagePanel((JPanel)this.pnlRiftNormal, (JPanel)this.pnlShrineNormal);
/* 106 */       JPanel pnlElite = buildPagePanel((JPanel)this.pnlRiftElite, (JPanel)this.pnlShrineElite);
/* 107 */       JPanel pnlUltimate = buildPagePanel((JPanel)this.pnlRiftUltimate, (JPanel)this.pnlShrineUltimate);
/*     */       
/* 109 */       add(pnlNormal);
/* 110 */       add(pnlElite);
/* 111 */       add(pnlUltimate);
/*     */     } 
/*     */     
/* 114 */     setTitleAt(0, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DIFF_NORMAL"));
/* 115 */     setTitleAt(1, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DIFF_ELITE"));
/* 116 */     setTitleAt(2, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DIFF_ULTIMATE"));
/*     */   }
/*     */   
/*     */   private JPanel buildPagePanel(JPanel pnlRift, JPanel pnlShrine) {
/* 120 */     JPanel panel = new JPanel();
/*     */     
/* 122 */     GroupLayout layout = null;
/* 123 */     GroupLayout.SequentialGroup hGroup = null;
/* 124 */     GroupLayout.SequentialGroup vGroup = null;
/*     */ 
/*     */ 
/*     */     
/* 128 */     layout = new GroupLayout(panel);
/* 129 */     panel.setLayout(layout);
/*     */     
/* 131 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 134 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 137 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 140 */     hGroup
/* 141 */       .addGroup(layout.createParallelGroup()
/* 142 */         .addComponent(pnlRift)
/* 143 */         .addComponent(pnlShrine));
/*     */     
/* 145 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 148 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 151 */     vGroup
/* 152 */       .addGroup(layout.createParallelGroup()
/* 153 */         .addComponent(pnlRift))
/* 154 */       .addGroup(layout.createParallelGroup()
/* 155 */         .addComponent(pnlShrine));
/*     */     
/* 157 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 159 */     layout.linkSize(0, new Component[] { pnlRift, pnlShrine });
/*     */ 
/*     */ 
/*     */     
/* 163 */     return panel;
/*     */   }
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 167 */     this.pnlRiftNormal.setChar(gdc);
/* 168 */     this.pnlRiftElite.setChar(gdc);
/* 169 */     this.pnlRiftUltimate.setChar(gdc);
/*     */     
/* 171 */     this.pnlShrineNormal.setChar(gdc);
/* 172 */     this.pnlShrineElite.setChar(gdc);
/* 173 */     this.pnlShrineUltimate.setChar(gdc);
/*     */   }
/*     */   
/*     */   public void updateChar(GDChar gdc) {
/* 177 */     if (gdc == null)
/*     */       return; 
/* 179 */     this.pnlRiftNormal.updateChar(gdc);
/* 180 */     this.pnlRiftElite.updateChar(gdc);
/* 181 */     this.pnlRiftUltimate.updateChar(gdc);
/*     */     
/* 183 */     this.pnlShrineNormal.updateChar(gdc);
/* 184 */     this.pnlShrineElite.updateChar(gdc);
/* 185 */     this.pnlShrineUltimate.updateChar(gdc);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharRiftShrineTabbedPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */