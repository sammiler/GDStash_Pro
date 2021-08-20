/*     */ package org.gdstash.ui.character;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.character.GDCharTeleportList;
/*     */ import org.gdstash.character.GDCharUID;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.util.GDAbstractRiftPane;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class RoTRiftAct6Pane
/*     */   extends GDAbstractRiftPane
/*     */ {
/*     */   private JCheckBox cbShatteredConclave;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   private class ChangeActionListener
/*     */     implements ActionListener
/*     */   {
/*     */     private ChangeActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  35 */       RoTRiftAct6Pane.this.changed = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RoTRiftAct6Pane(int difficulty, int direction) {
/*  45 */     this.difficulty = difficulty;
/*  46 */     this.changed = false;
/*     */     
/*  48 */     adjustUI();
/*     */     
/*  50 */     GroupLayout layout = null;
/*  51 */     GroupLayout.SequentialGroup hGroup = null;
/*  52 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  54 */     layout = new GroupLayout((Container)this);
/*  55 */     setLayout(layout);
/*     */     
/*  57 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  60 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  63 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  66 */     hGroup
/*  67 */       .addGroup(layout.createParallelGroup()
/*  68 */         .addComponent(this.cbShatteredConclave));
/*     */ 
/*     */     
/*  71 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  74 */     vGroup
/*  75 */       .addGroup(layout.createParallelGroup()
/*  76 */         .addComponent(this.cbShatteredConclave));
/*     */     
/*  78 */     if (direction == 0) {
/*  79 */       layout.setHorizontalGroup(vGroup);
/*  80 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/*  82 */       layout.setHorizontalGroup(hGroup);
/*  83 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  89 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  90 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/*  91 */     if (fntCheck == null) fntCheck = fntLabel; 
/*  92 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/*  93 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/*  95 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  96 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  97 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/*  99 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 100 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 101 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 102 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ACT1"));
/* 103 */     text.setTitleFont(fntBorder);
/*     */     
/* 105 */     setBorder(text);
/*     */     
/* 107 */     if (this.cbShatteredConclave == null) {
/* 108 */       this.cbShatteredConclave = new JCheckBox();
/*     */       
/* 110 */       this.cbShatteredConclave.addActionListener(new ChangeActionListener());
/*     */     } 
/* 112 */     this.cbShatteredConclave.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_06_SHATTERED_CONCLAVE"));
/* 113 */     this.cbShatteredConclave.setFont(fntCheck);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 118 */     return this.changed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged(boolean changed) {
/* 123 */     this.changed = changed;
/*     */   }
/*     */   
/*     */   public void setSelected(boolean selected) {
/* 127 */     this.cbShatteredConclave.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getRiftList(boolean found) {
/* 132 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 134 */     if (this.cbShatteredConclave.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_06_SHATTERED_CONCLAVE);
/*     */     
/* 136 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 141 */     setSelected(false);
/*     */     
/* 143 */     this.changed = false;
/*     */     
/* 145 */     if (gdc == null)
/*     */       return; 
/* 147 */     List<GDCharUID> list = gdc.getRiftList(this.difficulty);
/*     */     
/* 149 */     for (GDCharUID uid : list) {
/* 150 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_06_SHATTERED_CONCLAVE)) this.cbShatteredConclave.setSelected(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\character\RoTRiftAct6Pane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */