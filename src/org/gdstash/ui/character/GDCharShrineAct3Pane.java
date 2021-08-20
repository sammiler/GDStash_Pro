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
/*     */ import org.gdstash.character.GDCharShrineList;
/*     */ import org.gdstash.character.GDCharUID;
/*     */ import org.gdstash.db.DBShrine;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.util.GDAbstractShrinePane;
import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDCharShrineAct3Pane extends GDAbstractShrinePane {
/*     */   private JCheckBox cbMountainDeeps;
/*     */   private JCheckBox cbForgottenDepths;
/*     */   private JCheckBox cbTyrantsHold;
/*     */   private JCheckBox cbInfestedFarms;
/*     */   private JCheckBox cbDenOfTheLost;
/*     */   private JCheckBox cbConflagration;
/*     */   private JCheckBox cbPortValbury;
/*     */   private JCheckBox cbBloodGrove;
/*     */   private JCheckBox cbStonerendQuarry;
/*     */   private JCheckBox cbVillageOfDarkvale;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   private class ChangeActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  36 */       GDCharShrineAct3Pane.this.changed = true;
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
/*     */   
/*     */   public GDCharShrineAct3Pane(int difficulty, int direction) {
/*  55 */     this.difficulty = difficulty;
/*  56 */     this.changed = false;
/*     */     
/*  58 */     adjustUI();
/*     */     
/*  60 */     GroupLayout layout = null;
/*  61 */     GroupLayout.SequentialGroup hGroup = null;
/*  62 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  64 */     layout = new GroupLayout((Container)this);
/*  65 */     setLayout(layout);
/*     */     
/*  67 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  70 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  73 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  76 */     hGroup
/*  77 */       .addGroup(layout.createParallelGroup()
/*  78 */         .addComponent(this.cbMountainDeeps)
/*  79 */         .addComponent(this.cbForgottenDepths)
/*  80 */         .addComponent(this.cbTyrantsHold)
/*  81 */         .addComponent(this.cbInfestedFarms)
/*  82 */         .addComponent(this.cbDenOfTheLost)
/*  83 */         .addComponent(this.cbConflagration)
/*  84 */         .addComponent(this.cbPortValbury)
/*  85 */         .addComponent(this.cbBloodGrove)
/*  86 */         .addComponent(this.cbStonerendQuarry)
/*  87 */         .addComponent(this.cbVillageOfDarkvale));
/*     */ 
/*     */     
/*  90 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  93 */     vGroup
/*  94 */       .addGroup(layout.createParallelGroup()
/*  95 */         .addComponent(this.cbMountainDeeps))
/*  96 */       .addGroup(layout.createParallelGroup()
/*  97 */         .addComponent(this.cbForgottenDepths))
/*  98 */       .addGroup(layout.createParallelGroup()
/*  99 */         .addComponent(this.cbTyrantsHold))
/* 100 */       .addGroup(layout.createParallelGroup()
/* 101 */         .addComponent(this.cbInfestedFarms))
/* 102 */       .addGroup(layout.createParallelGroup()
/* 103 */         .addComponent(this.cbDenOfTheLost))
/* 104 */       .addGroup(layout.createParallelGroup()
/* 105 */         .addComponent(this.cbConflagration))
/* 106 */       .addGroup(layout.createParallelGroup()
/* 107 */         .addComponent(this.cbPortValbury))
/* 108 */       .addGroup(layout.createParallelGroup()
/* 109 */         .addComponent(this.cbBloodGrove))
/* 110 */       .addGroup(layout.createParallelGroup()
/* 111 */         .addComponent(this.cbStonerendQuarry))
/* 112 */       .addGroup(layout.createParallelGroup()
/* 113 */         .addComponent(this.cbVillageOfDarkvale));
/*     */     
/* 115 */     if (direction == 0) {
/* 116 */       layout.setHorizontalGroup(vGroup);
/* 117 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 119 */       layout.setHorizontalGroup(hGroup);
/* 120 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 123 */     layout.linkSize(0, new Component[] { this.cbMountainDeeps, this.cbForgottenDepths });
/* 124 */     layout.linkSize(0, new Component[] { this.cbMountainDeeps, this.cbTyrantsHold });
/* 125 */     layout.linkSize(0, new Component[] { this.cbMountainDeeps, this.cbInfestedFarms });
/* 126 */     layout.linkSize(0, new Component[] { this.cbMountainDeeps, this.cbDenOfTheLost });
/* 127 */     layout.linkSize(0, new Component[] { this.cbMountainDeeps, this.cbConflagration });
/* 128 */     layout.linkSize(0, new Component[] { this.cbMountainDeeps, this.cbPortValbury });
/* 129 */     layout.linkSize(0, new Component[] { this.cbMountainDeeps, this.cbBloodGrove });
/* 130 */     layout.linkSize(0, new Component[] { this.cbMountainDeeps, this.cbStonerendQuarry });
/* 131 */     layout.linkSize(0, new Component[] { this.cbMountainDeeps, this.cbVillageOfDarkvale });
/*     */     
/* 133 */     layout.linkSize(1, new Component[] { this.cbMountainDeeps, this.cbForgottenDepths });
/* 134 */     layout.linkSize(1, new Component[] { this.cbMountainDeeps, this.cbTyrantsHold });
/* 135 */     layout.linkSize(1, new Component[] { this.cbMountainDeeps, this.cbInfestedFarms });
/* 136 */     layout.linkSize(1, new Component[] { this.cbMountainDeeps, this.cbDenOfTheLost });
/* 137 */     layout.linkSize(1, new Component[] { this.cbMountainDeeps, this.cbConflagration });
/* 138 */     layout.linkSize(1, new Component[] { this.cbMountainDeeps, this.cbPortValbury });
/* 139 */     layout.linkSize(1, new Component[] { this.cbMountainDeeps, this.cbBloodGrove });
/* 140 */     layout.linkSize(1, new Component[] { this.cbMountainDeeps, this.cbStonerendQuarry });
/* 141 */     layout.linkSize(1, new Component[] { this.cbMountainDeeps, this.cbVillageOfDarkvale });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 146 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 147 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 148 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 149 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 150 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 152 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 153 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 154 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 156 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 157 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 158 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 159 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_ACT3"));
/* 160 */     text.setTitleFont(fntBorder);
/*     */     
/* 162 */     setBorder(text);
/*     */     
/* 164 */     if (this.cbMountainDeeps == null) {
/* 165 */       this.cbMountainDeeps = new JCheckBox();
/*     */       
/* 167 */       this.cbMountainDeeps.addActionListener(new ChangeActionListener());
/*     */     } 
/* 169 */     this.cbMountainDeeps.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_03_MOUNTAIN_DEEPS"));
/* 170 */     this.cbMountainDeeps.setFont(fntCheck);
/*     */     
/* 172 */     if (this.cbForgottenDepths == null) {
/* 173 */       this.cbForgottenDepths = new JCheckBox();
/*     */       
/* 175 */       this.cbForgottenDepths.addActionListener(new ChangeActionListener());
/*     */     } 
/* 177 */     this.cbForgottenDepths.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_03_FORGOTTEN_DEPTHS"));
/* 178 */     this.cbForgottenDepths.setFont(fntCheck);
/*     */     
/* 180 */     if (this.cbTyrantsHold == null) {
/* 181 */       this.cbTyrantsHold = new JCheckBox();
/*     */       
/* 183 */       this.cbTyrantsHold.addActionListener(new ChangeActionListener());
/*     */     } 
/* 185 */     this.cbTyrantsHold.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_03_TYRANTS_HOLD"));
/* 186 */     this.cbTyrantsHold.setFont(fntCheck);
/*     */     
/* 188 */     if (this.cbInfestedFarms == null) {
/* 189 */       this.cbInfestedFarms = new JCheckBox();
/*     */       
/* 191 */       this.cbInfestedFarms.addActionListener(new ChangeActionListener());
/*     */     } 
/* 193 */     this.cbInfestedFarms.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_03_INFESTED_FARMS"));
/* 194 */     this.cbInfestedFarms.setFont(fntCheck);
/*     */     
/* 196 */     if (this.cbDenOfTheLost == null) {
/* 197 */       this.cbDenOfTheLost = new JCheckBox();
/*     */       
/* 199 */       this.cbDenOfTheLost.addActionListener(new ChangeActionListener());
/*     */     } 
/* 201 */     this.cbDenOfTheLost.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_03_DEN_OF_THE_LOST"));
/* 202 */     this.cbDenOfTheLost.setFont(fntCheck);
/*     */     
/* 204 */     if (this.cbConflagration == null) {
/* 205 */       this.cbConflagration = new JCheckBox();
/*     */       
/* 207 */       this.cbConflagration.addActionListener(new ChangeActionListener());
/*     */     } 
/* 209 */     this.cbConflagration.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_03_CONFLAGRATION"));
/* 210 */     this.cbConflagration.setFont(fntCheck);
/*     */     
/* 212 */     if (this.cbPortValbury == null) {
/* 213 */       this.cbPortValbury = new JCheckBox();
/*     */       
/* 215 */       this.cbPortValbury.addActionListener(new ChangeActionListener());
/*     */     } 
/* 217 */     this.cbPortValbury.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_03_PORT_VALBURY"));
/* 218 */     this.cbPortValbury.setFont(fntCheck);
/*     */     
/* 220 */     if (this.cbBloodGrove == null) {
/* 221 */       this.cbBloodGrove = new JCheckBox();
/*     */       
/* 223 */       this.cbBloodGrove.addActionListener(new ChangeActionListener());
/*     */     } 
/* 225 */     this.cbBloodGrove.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_03_BLOOD_GROVE"));
/* 226 */     this.cbBloodGrove.setFont(fntCheck);
/*     */     
/* 228 */     if (this.cbStonerendQuarry == null) {
/* 229 */       this.cbStonerendQuarry = new JCheckBox();
/*     */       
/* 231 */       this.cbStonerendQuarry.addActionListener(new ChangeActionListener());
/*     */     } 
/* 233 */     this.cbStonerendQuarry.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_03_STONEREND_QUARRY"));
/* 234 */     this.cbStonerendQuarry.setFont(fntCheck);
/*     */     
/* 236 */     if (this.cbVillageOfDarkvale == null) {
/* 237 */       this.cbVillageOfDarkvale = new JCheckBox();
/*     */       
/* 239 */       this.cbVillageOfDarkvale.addActionListener(new ChangeActionListener());
/*     */     } 
/* 241 */     this.cbVillageOfDarkvale.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_03_VILLAGE_OF_DARKVALE"));
/* 242 */     this.cbVillageOfDarkvale.setFont(fntCheck);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 247 */     return this.changed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged(boolean changed) {
/* 252 */     this.changed = changed;
/*     */   }
/*     */   
/*     */   public void setSelected(boolean selected) {
/* 256 */     this.cbMountainDeeps.setSelected(selected);
/* 257 */     this.cbForgottenDepths.setSelected(selected);
/* 258 */     this.cbTyrantsHold.setSelected(selected);
/* 259 */     this.cbInfestedFarms.setSelected(selected);
/* 260 */     this.cbDenOfTheLost.setSelected(selected);
/* 261 */     this.cbConflagration.setSelected(selected);
/* 262 */     this.cbPortValbury.setSelected(selected);
/* 263 */     this.cbBloodGrove.setSelected(selected);
/* 264 */     this.cbStonerendQuarry.setSelected(selected);
/* 265 */     this.cbVillageOfDarkvale.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getShrineList(boolean found) {
/* 270 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 272 */     if (this.cbMountainDeeps.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_03_MOUNTAIN_DEEPS); 
/* 273 */     if (this.cbForgottenDepths.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_03_FORGOTTEN_DEPTHS); 
/* 274 */     if (this.cbTyrantsHold.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_03_TYRANTS_HOLD); 
/* 275 */     if (this.cbInfestedFarms.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_03_INFESTED_FARMS); 
/* 276 */     if (this.cbDenOfTheLost.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_03_DEN_OF_THE_LOST); 
/* 277 */     if (this.cbConflagration.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_03_CONFLAGRATION); 
/* 278 */     if (this.cbPortValbury.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_03_PORT_VALBURY); 
/* 279 */     if (this.cbBloodGrove.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_03_BLOOD_GROVE); 
/* 280 */     if (this.cbStonerendQuarry.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_03_STONEREND_QUARRY); 
/* 281 */     if (this.cbVillageOfDarkvale.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_03_VILLAGE_OF_DARKVALE);
/*     */     
/* 283 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 288 */     setSelected(false);
/*     */     
/* 290 */     this.changed = false;
/*     */     
/* 292 */     boolean expFG = GDStashFrame.expansionForgottenGods;
/*     */     
/* 294 */     if (gdc != null) {
/* 295 */       expFG = (expFG || gdc.isForgottenGodsChar());
/*     */     }
/*     */     
/* 298 */     DBShrine.Info info = null;
/*     */     
/* 300 */     info = DBShrine.getShrineInfo("devotionshrinec01.dbr", this.difficulty);
/* 301 */     this.cbMountainDeeps.setEnabled(info.active);
/*     */     
/* 303 */     info = DBShrine.getShrineInfo("devotionshrinec02.dbr", this.difficulty);
/* 304 */     this.cbForgottenDepths.setEnabled(info.active);
/*     */     
/* 306 */     info = DBShrine.getShrineInfo("devotionshrinec03.dbr", this.difficulty);
/* 307 */     this.cbTyrantsHold.setEnabled(info.active);
/*     */     
/* 309 */     info = DBShrine.getShrineInfo("devotionshrinec05.dbr", this.difficulty);
/* 310 */     this.cbInfestedFarms.setEnabled(info.active);
/*     */     
/* 312 */     info = DBShrine.getShrineInfo("devotionshrinec04.dbr", this.difficulty);
/* 313 */     this.cbDenOfTheLost.setEnabled(info.active);
/*     */     
/* 315 */     info = DBShrine.getShrineInfo("devotionshrinen12.dbr", this.difficulty);
/* 316 */     this.cbConflagration.setEnabled(info.active);
/* 317 */     this.cbConflagration.setVisible((info.exists || expFG));
/*     */     
/* 319 */     info = DBShrine.getShrineInfo("devotionshrinen01.dbr", this.difficulty);
/* 320 */     this.cbPortValbury.setEnabled(info.active);
/* 321 */     this.cbPortValbury.setVisible((info.exists || expFG));
/*     */     
/* 323 */     info = DBShrine.getShrineInfo("devotionshrinec08.dbr", this.difficulty);
/* 324 */     this.cbBloodGrove.setEnabled(info.active);
/*     */     
/* 326 */     info = DBShrine.getShrineInfo("devotionshrinen05.dbr", this.difficulty);
/* 327 */     this.cbStonerendQuarry.setEnabled(info.active);
/* 328 */     this.cbStonerendQuarry.setVisible((info.exists || expFG));
/*     */     
/* 330 */     info = DBShrine.getShrineInfo("devotionshrinec06.dbr", this.difficulty);
/* 331 */     this.cbVillageOfDarkvale.setEnabled(info.active);
/*     */     
/* 333 */     if (gdc == null)
/*     */       return; 
/* 335 */     List<GDCharUID> list = gdc.getRestoredShrinesList(this.difficulty);
/*     */     
/* 337 */     for (GDCharUID uid : list) {
/* 338 */       if (uid.equals(GDCharShrineList.UID_SHRINE_03_MOUNTAIN_DEEPS)) this.cbMountainDeeps.setSelected(true); 
/* 339 */       if (uid.equals(GDCharShrineList.UID_SHRINE_03_FORGOTTEN_DEPTHS)) this.cbForgottenDepths.setSelected(true); 
/* 340 */       if (uid.equals(GDCharShrineList.UID_SHRINE_03_TYRANTS_HOLD)) this.cbTyrantsHold.setSelected(true); 
/* 341 */       if (uid.equals(GDCharShrineList.UID_SHRINE_03_INFESTED_FARMS)) this.cbInfestedFarms.setSelected(true); 
/* 342 */       if (uid.equals(GDCharShrineList.UID_SHRINE_03_DEN_OF_THE_LOST)) this.cbDenOfTheLost.setSelected(true); 
/* 343 */       if (uid.equals(GDCharShrineList.UID_SHRINE_03_CONFLAGRATION)) this.cbConflagration.setSelected(true); 
/* 344 */       if (uid.equals(GDCharShrineList.UID_SHRINE_03_PORT_VALBURY)) this.cbPortValbury.setSelected(true); 
/* 345 */       if (uid.equals(GDCharShrineList.UID_SHRINE_03_BLOOD_GROVE)) this.cbBloodGrove.setSelected(true); 
/* 346 */       if (uid.equals(GDCharShrineList.UID_SHRINE_03_STONEREND_QUARRY)) this.cbStonerendQuarry.setSelected(true); 
/* 347 */       if (uid.equals(GDCharShrineList.UID_SHRINE_03_VILLAGE_OF_DARKVALE)) this.cbVillageOfDarkvale.setSelected(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\character\GDCharShrineAct3Pane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */