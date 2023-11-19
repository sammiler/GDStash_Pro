/*     */ package org.gdstash.ui.character;
/*     */ 
/*     */ import java.awt.Component;
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
/*     */ public class RoTRiftAct4Pane extends GDAbstractRiftPane {
/*     */   private JCheckBox cbPandemoniumFortress;
/*     */   private JCheckBox cbPlainsDispair;
/*     */   private JCheckBox cbCityDamned;
/*     */   private JCheckBox cbRiverFlame;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   private class ChangeActionListener implements ActionListener {
/*     */     private ChangeActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  35 */       RoTRiftAct4Pane.this.changed = true;
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
/*     */   public RoTRiftAct4Pane(int difficulty, int direction) {
/*  48 */     this.difficulty = difficulty;
/*  49 */     this.changed = false;
/*     */     
/*  51 */     adjustUI();
/*     */     
/*  53 */     GroupLayout layout = null;
/*  54 */     GroupLayout.SequentialGroup hGroup = null;
/*  55 */     GroupLayout.SequentialGroup vGroup = null;
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
/*  71 */         .addComponent(this.cbPandemoniumFortress)
/*  72 */         .addComponent(this.cbPlainsDispair)
/*  73 */         .addComponent(this.cbCityDamned)
/*  74 */         .addComponent(this.cbRiverFlame));
/*     */ 
/*     */     
/*  77 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  80 */     vGroup
/*  81 */       .addGroup(layout.createParallelGroup()
/*  82 */         .addComponent(this.cbPandemoniumFortress))
/*  83 */       .addGroup(layout.createParallelGroup()
/*  84 */         .addComponent(this.cbPlainsDispair))
/*  85 */       .addGroup(layout.createParallelGroup()
/*  86 */         .addComponent(this.cbCityDamned))
/*  87 */       .addGroup(layout.createParallelGroup()
/*  88 */         .addComponent(this.cbRiverFlame));
/*     */     
/*  90 */     if (direction == 0) {
/*  91 */       layout.setHorizontalGroup(vGroup);
/*  92 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/*  94 */       layout.setHorizontalGroup(hGroup);
/*  95 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/*  98 */     layout.linkSize(0, new Component[] { this.cbPandemoniumFortress, this.cbPlainsDispair });
/*  99 */     layout.linkSize(0, new Component[] { this.cbPandemoniumFortress, this.cbCityDamned });
/* 100 */     layout.linkSize(0, new Component[] { this.cbPandemoniumFortress, this.cbRiverFlame });
/*     */     
/* 102 */     layout.linkSize(1, new Component[] { this.cbPandemoniumFortress, this.cbPlainsDispair });
/* 103 */     layout.linkSize(1, new Component[] { this.cbPandemoniumFortress, this.cbCityDamned });
/* 104 */     layout.linkSize(1, new Component[] { this.cbPandemoniumFortress, this.cbRiverFlame });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 109 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 110 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 111 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 112 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 113 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 115 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 116 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 117 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 119 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 120 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 121 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 122 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ACT4"));
/* 123 */     text.setTitleFont(fntBorder);
/*     */     
/* 125 */     setBorder(text);
/*     */     
/* 127 */     if (this.cbPandemoniumFortress == null) {
/* 128 */       this.cbPandemoniumFortress = new JCheckBox();
/*     */       
/* 130 */       this.cbPandemoniumFortress.addActionListener(new ChangeActionListener());
/*     */     } 
/* 132 */     this.cbPandemoniumFortress.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_04_PANDEMONIUM_FORTRESS"));
/* 133 */     this.cbPandemoniumFortress.setFont(fntCheck);
/*     */     
/* 135 */     if (this.cbPlainsDispair == null) {
/* 136 */       this.cbPlainsDispair = new JCheckBox();
/*     */       
/* 138 */       this.cbPlainsDispair.addActionListener(new ChangeActionListener());
/*     */     } 
/* 140 */     this.cbPlainsDispair.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_04_PLAINS_DESPAIR"));
/* 141 */     this.cbPlainsDispair.setFont(fntCheck);
/*     */     
/* 143 */     if (this.cbCityDamned == null) {
/* 144 */       this.cbCityDamned = new JCheckBox();
/*     */       
/* 146 */       this.cbCityDamned.addActionListener(new ChangeActionListener());
/*     */     } 
/* 148 */     this.cbCityDamned.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_04_CITY_DAMNED"));
/* 149 */     this.cbCityDamned.setFont(fntCheck);
/*     */     
/* 151 */     if (this.cbRiverFlame == null) {
/* 152 */       this.cbRiverFlame = new JCheckBox();
/*     */       
/* 154 */       this.cbRiverFlame.addActionListener(new ChangeActionListener());
/*     */     } 
/* 156 */     this.cbRiverFlame.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_04_RIVER_FLAME"));
/* 157 */     this.cbRiverFlame.setFont(fntCheck);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 162 */     return this.changed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged(boolean changed) {
/* 167 */     this.changed = changed;
/*     */   }
/*     */   
/*     */   public void setSelected(boolean selected) {
/* 171 */     this.cbPandemoniumFortress.setSelected(selected);
/* 172 */     this.cbPlainsDispair.setSelected(selected);
/* 173 */     this.cbCityDamned.setSelected(selected);
/* 174 */     this.cbRiverFlame.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getRiftList(boolean found) {
/* 179 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 181 */     if (this.cbPandemoniumFortress.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_04_PANDEMONIUM_FORTRESS); 
/* 182 */     if (this.cbPlainsDispair.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_04_PLAINS_DESPAIR); 
/* 183 */     if (this.cbCityDamned.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_04_CITY_DAMNED); 
/* 184 */     if (this.cbRiverFlame.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_04_RIVER_FLAME);
/*     */     
/* 186 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 191 */     setSelected(false);
/*     */     
/* 193 */     this.changed = false;
/*     */     
/* 195 */     if (gdc == null)
/*     */       return; 
/* 197 */     List<GDCharUID> list = gdc.getRiftList(this.difficulty);
/*     */     
/* 199 */     for (GDCharUID uid : list) {
/* 200 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_04_PANDEMONIUM_FORTRESS)) this.cbPandemoniumFortress.setSelected(true); 
/* 201 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_04_PLAINS_DESPAIR)) this.cbPlainsDispair.setSelected(true); 
/* 202 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_04_CITY_DAMNED)) this.cbCityDamned.setSelected(true); 
/* 203 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_04_RIVER_FLAME)) this.cbRiverFlame.setSelected(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\RoTRiftAct4Pane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */