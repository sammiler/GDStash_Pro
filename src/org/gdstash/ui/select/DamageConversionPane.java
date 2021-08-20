/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
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
/*     */ public class DamageConversionPane
/*     */   extends AdjustablePanel
/*     */ {
/*  37 */   private static String[] dmgTypes = new String[11]; private JLabel lblDmgConversionFrom; private JComboBox<String> cbDmgConversionFrom;
/*     */   static {
/*  39 */     loadCBText();
/*     */   }
/*     */   private JLabel lblDmgConversionTo; private JComboBox<String> cbDmgConversionTo;
/*     */   private static void loadCBText() {
/*  43 */     dmgTypes[0] = "";
/*  44 */     dmgTypes[1] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_POISON");
/*  45 */     dmgTypes[2] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_AETHER");
/*  46 */     dmgTypes[3] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_CHAOS");
/*  47 */     dmgTypes[4] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_COLD");
/*  48 */     dmgTypes[5] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_ELEMENTAL");
/*  49 */     dmgTypes[6] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_FIRE");
/*  50 */     dmgTypes[7] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_LIGHTNING");
/*  51 */     dmgTypes[8] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_PHYSICAL");
/*  52 */     dmgTypes[9] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_PIERCE");
/*  53 */     dmgTypes[10] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "OFF_LIFE");
/*     */   }
/*     */   
/*     */   public DamageConversionPane() {
/*  57 */     adjustUI();
/*     */     
/*  59 */     GroupLayout layout = null;
/*  60 */     GroupLayout.SequentialGroup hGroup = null;
/*  61 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  63 */     layout = new GroupLayout((Container)this);
/*  64 */     setLayout(layout);
/*     */     
/*  66 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/*  69 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/*  72 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  75 */     hGroup
/*  76 */       .addGroup(layout.createParallelGroup()
/*  77 */         .addComponent(this.lblDmgConversionFrom)
/*  78 */         .addComponent(this.lblDmgConversionTo))
/*  79 */       .addGroup(layout.createParallelGroup()
/*  80 */         .addComponent(this.cbDmgConversionFrom)
/*  81 */         .addComponent(this.cbDmgConversionTo));
/*  82 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  85 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  88 */     vGroup
/*  89 */       .addGroup(layout.createParallelGroup()
/*  90 */         .addComponent(this.lblDmgConversionFrom)
/*  91 */         .addComponent(this.cbDmgConversionFrom))
/*  92 */       .addGroup(layout.createParallelGroup()
/*  93 */         .addComponent(this.lblDmgConversionTo)
/*  94 */         .addComponent(this.cbDmgConversionTo));
/*  95 */     layout.setVerticalGroup(vGroup);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     layout.linkSize(1, new Component[] { this.lblDmgConversionFrom, this.cbDmgConversionFrom });
/* 101 */     layout.linkSize(1, new Component[] { this.lblDmgConversionFrom, this.lblDmgConversionTo });
/* 102 */     layout.linkSize(1, new Component[] { this.lblDmgConversionFrom, this.cbDmgConversionTo });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 107 */     int size = 12;
/* 108 */     if (GDStashFrame.iniConfig != null) size = GDStashFrame.iniConfig.sectUI.fontSize; 
/* 109 */     Dimension dimMax = new Dimension(60 * size, 2 * size);
/*     */     
/* 111 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 112 */     Font fntCombo = UIManager.getDefaults().getFont("ComboBox.font");
/* 113 */     if (fntCombo == null) fntCombo = fntLabel; 
/* 114 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 115 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 117 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 118 */     fntCombo = fntCombo.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 119 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 121 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 122 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 123 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 124 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DAMAGE_CONVERSION"));
/* 125 */     text.setTitleFont(fntBorder);
/*     */     
/* 127 */     setBorder(text);
/*     */     
/* 129 */     loadCBText();
/*     */     
/* 131 */     if (this.lblDmgConversionFrom == null) {
/* 132 */       this.lblDmgConversionFrom = new JLabel();
/*     */     }
/* 134 */     this.lblDmgConversionFrom.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DMG_CONV_FROM"));
/* 135 */     this.lblDmgConversionFrom.setFont(fntLabel);
/*     */     
/* 137 */     if (this.cbDmgConversionFrom == null) {
/* 138 */       this.cbDmgConversionFrom = new JComboBox<>(dmgTypes);
/*     */     } else {
/* 140 */       updateLanguage(this.cbDmgConversionFrom);
/*     */     } 
/* 142 */     this.cbDmgConversionFrom.setFont(fntCombo);
/*     */     
/* 144 */     if (this.lblDmgConversionTo == null) {
/* 145 */       this.lblDmgConversionTo = new JLabel();
/*     */     }
/* 147 */     this.lblDmgConversionTo.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DMG_CONV_TO"));
/* 148 */     this.lblDmgConversionTo.setFont(fntLabel);
/*     */     
/* 150 */     if (this.cbDmgConversionTo == null) {
/* 151 */       this.cbDmgConversionTo = new JComboBox<>(dmgTypes);
/*     */     } else {
/* 153 */       updateLanguage(this.cbDmgConversionTo);
/*     */     } 
/* 155 */     this.cbDmgConversionTo.setFont(fntCombo);
/*     */   }
/*     */   
/*     */   private void updateLanguage(JComboBox<String> cb) {
/* 159 */     String[] texts = null;
/*     */     
/* 161 */     if (cb == this.cbDmgConversionFrom) texts = dmgTypes; 
/* 162 */     if (cb == this.cbDmgConversionTo) texts = dmgTypes;
/*     */     
/* 164 */     if (texts != null) {
/* 165 */       int index = cb.getSelectedIndex();
/*     */       
/* 167 */       cb.removeAllItems();
/*     */       int i;
/* 169 */       for (i = 0; i < texts.length; i++) {
/* 170 */         cb.addItem(texts[i]);
/*     */       }
/*     */       
/* 173 */       cb.setSelectedIndex(index);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clear() {
/* 178 */     this.cbDmgConversionFrom.setSelectedIndex(0);
/* 179 */     this.cbDmgConversionTo.setSelectedIndex(0);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 183 */     int index = 0;
/* 184 */     String dmgType = null;
/*     */     
/* 186 */     index = this.cbDmgConversionFrom.getSelectedIndex();
/* 187 */     dmgType = getDamageTypeByIndex(index);
/* 188 */     criteria.dmgConversionFrom = dmgType;
/*     */     
/* 190 */     index = this.cbDmgConversionTo.getSelectedIndex();
/* 191 */     dmgType = getDamageTypeByIndex(index);
/* 192 */     criteria.dmgConversionTo = dmgType;
/*     */   }
/*     */   
/*     */   private String getDamageTypeByIndex(int index) {
/* 196 */     String dmgType = null;
/*     */     
/* 198 */     switch (index) {
/*     */       case 1:
/* 200 */         dmgType = "Poison";
/*     */         break;
/*     */       
/*     */       case 2:
/* 204 */         dmgType = "Aether";
/*     */         break;
/*     */       
/*     */       case 3:
/* 208 */         dmgType = "Chaos";
/*     */         break;
/*     */       
/*     */       case 4:
/* 212 */         dmgType = "Cold";
/*     */         break;
/*     */       
/*     */       case 5:
/* 216 */         dmgType = "Elemental";
/*     */         break;
/*     */       
/*     */       case 6:
/* 220 */         dmgType = "Fire";
/*     */         break;
/*     */       
/*     */       case 7:
/* 224 */         dmgType = "Lightning";
/*     */         break;
/*     */       
/*     */       case 8:
/* 228 */         dmgType = "Physical";
/*     */         break;
/*     */       
/*     */       case 9:
/* 232 */         dmgType = "Pierce";
/*     */         break;
/*     */       
/*     */       case 10:
/* 236 */         dmgType = "Life";
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 241 */     return dmgType;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\DamageConversionPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */