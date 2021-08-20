/*     */ package org.gdstash.ui;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JWindow;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import org.gdstash.util.GDImagePool;
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
/*     */ public class GDSplashScreen
/*     */   extends JWindow
/*     */ {
/*     */   private Component owner;
/*     */   private JLabel lblImage;
/*     */   private JLabel lblText;
/*     */   
/*     */   public GDSplashScreen() {
/*  32 */     this((JFrame)null);
/*     */   }
/*     */   
/*     */   public GDSplashScreen(JFrame frame) {
/*  36 */     super(frame);
/*     */     
/*  38 */     this.owner = frame;
/*     */     
/*  40 */     JPanel panel = buildPanel();
/*     */     
/*  42 */     getContentPane().add(panel);
/*  43 */     pack();
/*     */     
/*  45 */     setLocationRelativeTo(this.owner);
/*     */     
/*  47 */     setVisible(true);
/*     */   }
/*     */   
/*     */   private JPanel buildPanel() {
/*  51 */     GroupLayout layout = null;
/*  52 */     GroupLayout.SequentialGroup hGroup = null;
/*  53 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  55 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  56 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  57 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */     
/*  59 */     JPanel panel = new JPanel();
/*     */     
/*  61 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*     */     
/*  63 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/*  65 */     this.lblImage = new JLabel(GDImagePool.iconLoading);
/*  66 */     this.lblText = new JLabel(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_LOADING"));
/*  67 */     this.lblText.setFont(fntLabel);
/*     */     
/*  69 */     layout = new GroupLayout(panel);
/*  70 */     panel.setLayout(layout);
/*     */     
/*  72 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  75 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  78 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  81 */     hGroup
/*  82 */       .addGroup(layout.createParallelGroup()
/*  83 */         .addComponent(this.lblImage)
/*  84 */         .addComponent(this.lblText));
/*  85 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  88 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  91 */     vGroup
/*  92 */       .addGroup(layout.createParallelGroup()
/*  93 */         .addComponent(this.lblImage))
/*  94 */       .addGroup(layout.createParallelGroup()
/*  95 */         .addComponent(this.lblText));
/*  96 */     layout.setVerticalGroup(vGroup);
/*     */     
/*  98 */     layout.linkSize(0, new Component[] { this.lblImage, this.lblText });
/*     */     
/* 100 */     panel.setBorder(compound);
/*     */     
/* 102 */     return panel;
/*     */   }
/*     */   
/*     */   public void setText(String text) {
/* 106 */     this.lblText.setText(text);
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\GDSplashScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */