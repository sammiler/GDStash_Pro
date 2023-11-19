/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JTextField;
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
/*     */ public class ItemNamePane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JLabel lblItemName;
/*     */   private JTextField tfItemName;
/*     */   
/*     */   public ItemNamePane() {
/*  30 */     adjustUI();
/*     */     
/*  32 */     GroupLayout layout = null;
/*  33 */     GroupLayout.SequentialGroup hGroup = null;
/*  34 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  36 */     layout = new GroupLayout((Container)this);
/*  37 */     setLayout(layout);
/*     */     
/*  39 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  42 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  45 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  48 */     hGroup
/*  49 */       .addGroup(layout.createParallelGroup()
/*  50 */         .addComponent(this.lblItemName))
/*  51 */       .addGroup(layout.createParallelGroup()
/*  52 */         .addComponent(this.tfItemName));
/*  53 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  56 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  59 */     vGroup
/*  60 */       .addGroup(layout.createParallelGroup()
/*  61 */         .addComponent(this.lblItemName)
/*  62 */         .addComponent(this.tfItemName));
/*  63 */     layout.setVerticalGroup(vGroup);
/*     */     
/*  65 */     layout.linkSize(1, new Component[] { this.lblItemName, this.tfItemName });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  70 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  71 */     Font fntText = UIManager.getDefaults().getFont("TextField.font");
/*  72 */     if (fntText == null) fntText = fntLabel; 
/*  73 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/*  74 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/*  76 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  77 */     fntText = fntText.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  78 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/*  80 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  81 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  82 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*  83 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_ITEM_NAME"));
/*  84 */     text.setTitleFont(fntBorder);
/*     */     
/*  86 */     setBorder(text);
/*     */     
/*  88 */     if (this.lblItemName == null) this.lblItemName = new JLabel(); 
/*  89 */     this.lblItemName.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_ITEM_NAME"));
/*  90 */     this.lblItemName.setFont(fntLabel);
/*     */     
/*  92 */     if (this.tfItemName == null) {
/*  93 */       this.tfItemName = new JTextField();
/*     */     }
/*  95 */     this.tfItemName.setFont(fntText);
/*     */   }
/*     */   
/*     */   public void clear() {
/*  99 */     this.tfItemName.setText("");
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 103 */     String s = null;
/* 104 */     int i = 0;
/*     */     
/* 106 */     s = this.tfItemName.getText();
/* 107 */     if (s != null && 
/* 108 */       !s.isEmpty()) {
/* 109 */       int pos = s.indexOf("*");
/* 110 */       while (pos != -1) {
/* 111 */         s = s.substring(0, pos) + "%" + s.substring(pos + 1);
/*     */         
/* 113 */         pos = s.indexOf("*");
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 118 */     if (!s.startsWith("%")) s = "%" + s; 
/* 119 */     if (!s.endsWith("%")) s = s + "%";
/*     */     
/* 121 */     criteria.itemName = s;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\select\ItemNamePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */