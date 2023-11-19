/*     */ package org.gdstash.ui;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JProgressBar;
/*     */ import javax.swing.UIManager;
/*     */ import org.gdstash.util.GDImagePool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDProgressDialog
/*     */   extends JDialog
/*     */ {
/*     */   private Component owner;
/*     */   private JLabel lblImage;
/*     */   private JProgressBar pbProgress;
/*     */   private JLabel lblText;
/*     */   
/*     */   public GDProgressDialog(Frame owner) {
/*  30 */     this(owner, (String)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public GDProgressDialog(Frame owner, String text) {
/*  35 */     super(owner, false);
/*     */     
/*  37 */     this.owner = owner;
/*     */     
/*  39 */     JPanel panel = buildPanel();
/*     */     
/*  41 */     this.lblText.setText(text);
/*     */     
/*  43 */     getContentPane().add(panel);
/*     */     
/*  45 */     pack();
/*     */     
/*  47 */     setLocationRelativeTo(owner);
/*     */     
/*  49 */     setDefaultCloseOperation(0);
/*     */   }
/*     */   
/*     */   private JPanel buildPanel() {
/*  53 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*     */     
/*  55 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/*  57 */     GroupLayout layout = null;
/*  58 */     GroupLayout.SequentialGroup hGroup = null;
/*  59 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  61 */     JPanel panel = new JPanel();
/*     */     
/*  63 */     this.lblImage = new JLabel(GDImagePool.iconProgress);
/*     */     
/*  65 */     this.pbProgress = new JProgressBar();
/*  66 */     this.pbProgress.setIndeterminate(true);
/*     */     
/*  68 */     this.lblText = new JLabel();
/*  69 */     this.lblText.setFont(fntLabel);
/*     */     
/*  71 */     layout = new GroupLayout(panel);
/*  72 */     panel.setLayout(layout);
/*     */     
/*  74 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  77 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  80 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  83 */     hGroup
/*  84 */       .addGroup(layout.createParallelGroup()
/*  85 */         .addComponent(this.lblImage)
/*  86 */         .addComponent(this.pbProgress)
/*  87 */         .addComponent(this.lblText));
/*  88 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  91 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  94 */     vGroup
/*  95 */       .addGroup(layout.createParallelGroup()
/*  96 */         .addComponent(this.lblImage))
/*  97 */       .addGroup(layout.createParallelGroup()
/*  98 */         .addComponent(this.pbProgress))
/*  99 */       .addGroup(layout.createParallelGroup()
/* 100 */         .addComponent(this.lblText));
/* 101 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 103 */     layout.linkSize(0, new Component[] { this.lblImage, this.pbProgress });
/* 104 */     layout.linkSize(0, new Component[] { this.lblImage, this.lblText });
/*     */     
/* 106 */     layout.linkSize(1, new Component[] { this.pbProgress, this.lblText });
/*     */     
/* 108 */     return panel;
/*     */   }
/*     */   
/*     */   public void setText(String text) {
/* 112 */     this.lblText.setText(text);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\GDProgressDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */