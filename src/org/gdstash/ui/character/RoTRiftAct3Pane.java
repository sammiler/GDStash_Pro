/*     */ package org.gdstash.ui.character;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*     */ import java.util.LinkedList;
import java.util.List;
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
/*     */ public class RoTRiftAct3Pane extends GDAbstractRiftPane {
/*     */   private JCheckBox cbKurastDocks;
/*     */   private JCheckBox cbSpiderForest;
/*     */   private JCheckBox cbGreatMarsh;
/*     */   private JCheckBox cbFlayerJungle;
/*     */   private JCheckBox cbKurastLower;
/*     */   private JCheckBox cbKurastBazaar;
/*     */   private JCheckBox cbKurastUpper;
/*     */   private JCheckBox cbTravincal;
/*     */   private JCheckBox cbDuranceHate;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   private class ChangeActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  35 */       RoTRiftAct3Pane.this.changed = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private ChangeActionListener() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RoTRiftAct3Pane(int difficulty, int direction) {
/*  53 */     this.difficulty = difficulty;
/*  54 */     this.changed = false;
/*     */     
/*  56 */     adjustUI();
/*     */     
/*  58 */     GroupLayout layout = null;
/*  59 */     GroupLayout.SequentialGroup hGroup = null;
/*  60 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  62 */     layout = new GroupLayout((Container)this);
/*  63 */     setLayout(layout);
/*     */     
/*  65 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  68 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  71 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  74 */     hGroup
/*  75 */       .addGroup(layout.createParallelGroup()
/*  76 */         .addComponent(this.cbKurastDocks)
/*  77 */         .addComponent(this.cbSpiderForest)
/*  78 */         .addComponent(this.cbGreatMarsh)
/*  79 */         .addComponent(this.cbFlayerJungle)
/*  80 */         .addComponent(this.cbKurastLower)
/*  81 */         .addComponent(this.cbKurastBazaar)
/*  82 */         .addComponent(this.cbKurastUpper)
/*  83 */         .addComponent(this.cbTravincal)
/*  84 */         .addComponent(this.cbDuranceHate));
/*     */ 
/*     */     
/*  87 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  90 */     vGroup
/*  91 */       .addGroup(layout.createParallelGroup()
/*  92 */         .addComponent(this.cbKurastDocks))
/*  93 */       .addGroup(layout.createParallelGroup()
/*  94 */         .addComponent(this.cbSpiderForest))
/*  95 */       .addGroup(layout.createParallelGroup()
/*  96 */         .addComponent(this.cbGreatMarsh))
/*  97 */       .addGroup(layout.createParallelGroup()
/*  98 */         .addComponent(this.cbFlayerJungle))
/*  99 */       .addGroup(layout.createParallelGroup()
/* 100 */         .addComponent(this.cbKurastLower))
/* 101 */       .addGroup(layout.createParallelGroup()
/* 102 */         .addComponent(this.cbKurastBazaar))
/* 103 */       .addGroup(layout.createParallelGroup()
/* 104 */         .addComponent(this.cbKurastUpper))
/* 105 */       .addGroup(layout.createParallelGroup()
/* 106 */         .addComponent(this.cbTravincal))
/* 107 */       .addGroup(layout.createParallelGroup()
/* 108 */         .addComponent(this.cbDuranceHate));
/*     */     
/* 110 */     if (direction == 0) {
/* 111 */       layout.setHorizontalGroup(vGroup);
/* 112 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 114 */       layout.setHorizontalGroup(hGroup);
/* 115 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 118 */     layout.linkSize(0, new Component[] { this.cbKurastDocks, this.cbSpiderForest });
/* 119 */     layout.linkSize(0, new Component[] { this.cbKurastDocks, this.cbGreatMarsh });
/* 120 */     layout.linkSize(0, new Component[] { this.cbKurastDocks, this.cbFlayerJungle });
/* 121 */     layout.linkSize(0, new Component[] { this.cbKurastDocks, this.cbKurastLower });
/* 122 */     layout.linkSize(0, new Component[] { this.cbKurastDocks, this.cbKurastBazaar });
/* 123 */     layout.linkSize(0, new Component[] { this.cbKurastDocks, this.cbKurastUpper });
/* 124 */     layout.linkSize(0, new Component[] { this.cbKurastDocks, this.cbTravincal });
/* 125 */     layout.linkSize(0, new Component[] { this.cbKurastDocks, this.cbDuranceHate });
/*     */     
/* 127 */     layout.linkSize(1, new Component[] { this.cbKurastDocks, this.cbSpiderForest });
/* 128 */     layout.linkSize(1, new Component[] { this.cbKurastDocks, this.cbGreatMarsh });
/* 129 */     layout.linkSize(1, new Component[] { this.cbKurastDocks, this.cbFlayerJungle });
/* 130 */     layout.linkSize(1, new Component[] { this.cbKurastDocks, this.cbKurastLower });
/* 131 */     layout.linkSize(1, new Component[] { this.cbKurastDocks, this.cbKurastBazaar });
/* 132 */     layout.linkSize(1, new Component[] { this.cbKurastDocks, this.cbKurastUpper });
/* 133 */     layout.linkSize(1, new Component[] { this.cbKurastDocks, this.cbTravincal });
/* 134 */     layout.linkSize(1, new Component[] { this.cbKurastDocks, this.cbDuranceHate });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 139 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 140 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 141 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 142 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 143 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 145 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 146 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 147 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 149 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 150 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 151 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 152 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ACT1"));
/* 153 */     text.setTitleFont(fntBorder);
/*     */     
/* 155 */     setBorder(text);
/*     */     
/* 157 */     if (this.cbKurastDocks == null) {
/* 158 */       this.cbKurastDocks = new JCheckBox();
/*     */       
/* 160 */       this.cbKurastDocks.addActionListener(new ChangeActionListener());
/*     */     } 
/* 162 */     this.cbKurastDocks.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_03_KURAST_DOCKS"));
/* 163 */     this.cbKurastDocks.setFont(fntCheck);
/*     */     
/* 165 */     if (this.cbSpiderForest == null) {
/* 166 */       this.cbSpiderForest = new JCheckBox();
/*     */       
/* 168 */       this.cbSpiderForest.addActionListener(new ChangeActionListener());
/*     */     } 
/* 170 */     this.cbSpiderForest.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_03_SPIDER_FOREST"));
/* 171 */     this.cbSpiderForest.setFont(fntCheck);
/*     */     
/* 173 */     if (this.cbGreatMarsh == null) {
/* 174 */       this.cbGreatMarsh = new JCheckBox();
/*     */       
/* 176 */       this.cbGreatMarsh.addActionListener(new ChangeActionListener());
/*     */     } 
/* 178 */     this.cbGreatMarsh.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_03_GREAT_MARSH"));
/* 179 */     this.cbGreatMarsh.setFont(fntCheck);
/*     */     
/* 181 */     if (this.cbFlayerJungle == null) {
/* 182 */       this.cbFlayerJungle = new JCheckBox();
/*     */       
/* 184 */       this.cbFlayerJungle.addActionListener(new ChangeActionListener());
/*     */     } 
/* 186 */     this.cbFlayerJungle.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_03_FLAYER_JUNGLE"));
/* 187 */     this.cbFlayerJungle.setFont(fntCheck);
/*     */     
/* 189 */     if (this.cbKurastLower == null) {
/* 190 */       this.cbKurastLower = new JCheckBox();
/*     */       
/* 192 */       this.cbKurastLower.addActionListener(new ChangeActionListener());
/*     */     } 
/* 194 */     this.cbKurastLower.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_03_KURAST_LOWER"));
/* 195 */     this.cbKurastLower.setFont(fntCheck);
/*     */     
/* 197 */     if (this.cbKurastBazaar == null) {
/* 198 */       this.cbKurastBazaar = new JCheckBox();
/*     */       
/* 200 */       this.cbKurastBazaar.addActionListener(new ChangeActionListener());
/*     */     } 
/* 202 */     this.cbKurastBazaar.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_03_KURAST_BAZAAR"));
/* 203 */     this.cbKurastBazaar.setFont(fntCheck);
/*     */     
/* 205 */     if (this.cbKurastUpper == null) {
/* 206 */       this.cbKurastUpper = new JCheckBox();
/*     */       
/* 208 */       this.cbKurastUpper.addActionListener(new ChangeActionListener());
/*     */     } 
/* 210 */     this.cbKurastUpper.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_03_KURAST_UPPER"));
/* 211 */     this.cbKurastUpper.setFont(fntCheck);
/*     */     
/* 213 */     if (this.cbTravincal == null) {
/* 214 */       this.cbTravincal = new JCheckBox();
/*     */       
/* 216 */       this.cbTravincal.addActionListener(new ChangeActionListener());
/*     */     } 
/* 218 */     this.cbTravincal.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_03_TRAVINCAL"));
/* 219 */     this.cbTravincal.setFont(fntCheck);
/*     */     
/* 221 */     if (this.cbDuranceHate == null) {
/* 222 */       this.cbDuranceHate = new JCheckBox();
/*     */       
/* 224 */       this.cbDuranceHate.addActionListener(new ChangeActionListener());
/*     */     } 
/* 226 */     this.cbDuranceHate.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_03_DURANCE_HATE"));
/* 227 */     this.cbDuranceHate.setFont(fntCheck);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 232 */     return this.changed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged(boolean changed) {
/* 237 */     this.changed = changed;
/*     */   }
/*     */   
/*     */   public void setSelected(boolean selected) {
/* 241 */     this.cbKurastDocks.setSelected(selected);
/* 242 */     this.cbSpiderForest.setSelected(selected);
/* 243 */     this.cbGreatMarsh.setSelected(selected);
/* 244 */     this.cbFlayerJungle.setSelected(selected);
/* 245 */     this.cbKurastLower.setSelected(selected);
/* 246 */     this.cbKurastBazaar.setSelected(selected);
/* 247 */     this.cbKurastUpper.setSelected(selected);
/* 248 */     this.cbTravincal.setSelected(selected);
/* 249 */     this.cbDuranceHate.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getRiftList(boolean found) {
/* 254 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 256 */     if (this.cbKurastDocks.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_03_KURAST_DOCKS); 
/* 257 */     if (this.cbSpiderForest.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_03_SPIDER_FOREST); 
/* 258 */     if (this.cbGreatMarsh.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_03_GREAT_MARSH); 
/* 259 */     if (this.cbFlayerJungle.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_03_FLAYER_JUNGLE); 
/* 260 */     if (this.cbKurastLower.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_03_KURAST_LOWER); 
/* 261 */     if (this.cbKurastBazaar.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_03_KURAST_BAZAAR); 
/* 262 */     if (this.cbKurastUpper.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_03_KURAST_UPPER); 
/* 263 */     if (this.cbTravincal.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_03_TRAVINCAL); 
/* 264 */     if (this.cbDuranceHate.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_03_DURANCE_HATE);
/*     */     
/* 266 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 271 */     setSelected(false);
/*     */     
/* 273 */     this.changed = false;
/*     */     
/* 275 */     if (gdc == null)
/*     */       return; 
/* 277 */     List<GDCharUID> list = gdc.getRiftList(this.difficulty);
/*     */     
/* 279 */     for (GDCharUID uid : list) {
/* 280 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_03_KURAST_DOCKS)) this.cbKurastDocks.setSelected(true); 
/* 281 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_03_SPIDER_FOREST)) this.cbSpiderForest.setSelected(true); 
/* 282 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_03_GREAT_MARSH)) this.cbGreatMarsh.setSelected(true); 
/* 283 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_03_FLAYER_JUNGLE)) this.cbFlayerJungle.setSelected(true); 
/* 284 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_03_KURAST_LOWER)) this.cbKurastLower.setSelected(true); 
/* 285 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_03_KURAST_BAZAAR)) this.cbKurastBazaar.setSelected(true); 
/* 286 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_03_KURAST_UPPER)) this.cbKurastUpper.setSelected(true); 
/* 287 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_03_TRAVINCAL)) this.cbTravincal.setSelected(true); 
/* 288 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_03_DURANCE_HATE)) this.cbDuranceHate.setSelected(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\character\RoTRiftAct3Pane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */