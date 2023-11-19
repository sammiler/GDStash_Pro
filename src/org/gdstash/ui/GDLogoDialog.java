/*     */ package org.gdstash.ui;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowEvent;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import org.gdstash.util.GDImagePool;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDLogoDialog
/*     */   extends JDialog {
/*     */   private Component owner;
/*     */   private JPanel panel;
/*     */   private JLabel lblImage;
/*     */   private JLabel lblIcon;
/*     */   private JLabel lblText;
/*     */   private JButton btnClose;
/*     */   
/*     */   private class CloseActionListener implements ActionListener {
/*     */     private CloseActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  34 */       GDLogoDialog.this.setVisible(false);
/*  35 */       GDLogoDialog.this.dispatchEvent(new WindowEvent(GDLogoDialog.this, 201));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GDLogoDialog(String text) {
/*  48 */     this(text, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GDLogoDialog(String text, int iconType) {
/*  54 */     this.owner = null;
/*     */     
/*  56 */     buildPanel(text, iconType);
/*     */   }
/*     */   
/*     */   public GDLogoDialog(String text, Frame owner) {
/*  60 */     this(text, 0, owner, "", false);
/*     */   }
/*     */   
/*     */   public GDLogoDialog(String text, int iconType, Frame owner) {
/*  64 */     this(text, iconType, owner, "", false);
/*     */   }
/*     */   
/*     */   public GDLogoDialog(String text, Frame owner, boolean modal) {
/*  68 */     this(text, 0, owner, "", modal);
/*     */   }
/*     */   
/*     */   public GDLogoDialog(String text, int iconType, Frame owner, boolean modal) {
/*  72 */     this(text, iconType, owner, "", modal);
/*     */   }
/*     */   
/*     */   public GDLogoDialog(String text, Frame owner, String title) {
/*  76 */     this(text, 0, owner, title, false);
/*     */   }
/*     */   
/*     */   public GDLogoDialog(String text, int iconType, Frame owner, String title) {
/*  80 */     this(text, iconType, owner, title, false);
/*     */   }
/*     */   
/*     */   public GDLogoDialog(String text, int iconType, Frame owner, String title, boolean modal) {
/*  84 */     super(owner, title, modal);
/*     */     
/*  86 */     this.owner = owner;
/*     */     
/*  88 */     buildPanel(text, iconType);
/*     */   }
/*     */   
/*     */   protected void buildPanel(String text, int iconType) {
/*  92 */     this.panel = new JPanel();
/*     */     
/*  94 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  95 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/*  96 */     if (fntButton == null) fntButton = fntLabel;
/*     */     
/*  98 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  99 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 101 */     GroupLayout layout = null;
/* 102 */     GroupLayout.SequentialGroup hGroup = null;
/* 103 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 105 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 106 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 107 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */     
/* 109 */     this.lblImage = new JLabel();
/* 110 */     this.lblImage.setIcon(GDImagePool.iconProgress);
/* 111 */     this.lblImage.setBorder(compound);
/* 112 */     this.lblImage.setHorizontalAlignment(0);
/*     */     
/* 114 */     JPanel pnlText = buildTextPanel(text, iconType);
/*     */     
/* 116 */     this.btnClose = new JButton(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_OK"));
/* 117 */     this.btnClose.setIcon(GDImagePool.iconBtnOk24);
/* 118 */     this.btnClose.setFont(fntButton);
/* 119 */     this.btnClose.addActionListener(new CloseActionListener());
/*     */     
/* 121 */     layout = new GroupLayout(this.panel);
/* 122 */     this.panel.setLayout(layout);
/*     */     
/* 124 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 127 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 130 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 133 */     hGroup
/* 134 */       .addGroup(layout.createParallelGroup()
/* 135 */         .addComponent(this.lblImage)
/* 136 */         .addComponent(pnlText)
/* 137 */         .addComponent(this.btnClose));
/* 138 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 141 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 144 */     vGroup
/* 145 */       .addGroup(layout.createParallelGroup()
/* 146 */         .addComponent(this.lblImage))
/* 147 */       .addGroup(layout.createParallelGroup()
/* 148 */         .addComponent(pnlText))
/* 149 */       .addGroup(layout.createParallelGroup()
/* 150 */         .addComponent(this.btnClose));
/* 151 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 153 */     layout.linkSize(0, new Component[] { this.lblImage, pnlText });
/* 154 */     layout.linkSize(0, new Component[] { this.lblImage, this.btnClose });
/*     */     
/* 156 */     getContentPane().add(this.panel);
/*     */     
/* 158 */     pack();
/*     */     
/* 160 */     setLocationRelativeTo(this.owner);
/*     */     
/* 162 */     setIconImage(GDImagePool.iconLogo64x64.getImage());
/*     */   }
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
/*     */   private JPanel buildTextPanel(String text, int iconType) {
/* 176 */     JPanel panel = new JPanel();
/*     */     
/* 178 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*     */     
/* 180 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 182 */     GroupLayout layout = null;
/* 183 */     GroupLayout.SequentialGroup hGroup = null;
/* 184 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 186 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 187 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 188 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */     
/* 190 */     panel.setBorder(compound);
/*     */     
/* 192 */     if (iconType != 0) {
/* 193 */       this.lblIcon = new JLabel();
/* 194 */       setIcon(iconType);
/* 195 */       this.lblIcon.setBorder(compound);
/*     */     } 
/*     */     
/* 198 */     this.lblText = new JLabel();
/* 199 */     this.lblText.setText(text);
/* 200 */     this.lblText.setVerticalAlignment(0);
/* 201 */     this.lblText.setFont(fntLabel);
/*     */     
/* 203 */     layout = new GroupLayout(panel);
/* 204 */     panel.setLayout(layout);
/*     */     
/* 206 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 209 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 212 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 215 */     if (iconType == 0) {
/* 216 */       hGroup
/* 217 */         .addGroup(layout.createParallelGroup()
/* 218 */           .addComponent(this.lblText));
/*     */     } else {
/* 220 */       hGroup
/* 221 */         .addGroup(layout.createParallelGroup()
/* 222 */           .addComponent(this.lblIcon))
/* 223 */         .addGroup(layout.createParallelGroup()
/* 224 */           .addComponent(this.lblText));
/*     */     } 
/* 226 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 229 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 232 */     if (iconType == 0) {
/* 233 */       vGroup
/* 234 */         .addGroup(layout.createParallelGroup()
/* 235 */           .addComponent(this.lblText));
/*     */     } else {
/* 237 */       vGroup
/* 238 */         .addGroup(layout.createParallelGroup()
/* 239 */           .addComponent(this.lblIcon)
/* 240 */           .addComponent(this.lblText));
/*     */     } 
/* 242 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 244 */     if (iconType != 0) layout.linkSize(1, new Component[] { this.lblIcon, this.lblText });
/*     */     
/* 246 */     return panel;
/*     */   }
/*     */   
/*     */   private void setIcon(int iconType) {
/* 250 */     switch (iconType) {
/*     */       case 1:
/* 252 */         this.lblIcon.setIcon(GDImagePool.iconMsgSuccess32);
/*     */         return;
/*     */       
/*     */       case 2:
/* 256 */         this.lblIcon.setIcon(GDImagePool.iconMsgInfo32);
/*     */         return;
/*     */       
/*     */       case 3:
/* 260 */         this.lblIcon.setIcon(GDImagePool.iconMsgWarning32);
/*     */         return;
/*     */       
/*     */       case 4:
/* 264 */         this.lblIcon.setIcon(GDImagePool.iconMsgError32);
/*     */         return;
/*     */     } 
/*     */     
/* 268 */     this.lblIcon.setIcon((Icon)null);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\GDLogoDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */