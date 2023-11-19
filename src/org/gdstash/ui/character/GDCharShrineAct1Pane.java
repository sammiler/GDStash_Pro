/*     */ package org.gdstash.ui.character;
/*     */ import java.awt.*;
/*     */
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
/*     */ public class GDCharShrineAct1Pane extends GDAbstractShrinePane {
/*     */   private JCheckBox cbBurialHill;
/*     */   private JCheckBox cbDevilsAquifier;
/*     */   private JCheckBox cbWightmireCavern;
/*     */   private JCheckBox cbFloodedPassage;
/*     */   private JCheckBox cbBurialCave;
/*     */   private JCheckBox cbHallowedHill;
/*     */   private JCheckBox cbBurrwitchEstates;
/*     */   private JCheckBox cbUndergroundTransit;
/*     */   private JCheckBox cbCraigsCrags;
/*     */   private JCheckBox cbGuardianDreeg;
/*     */   private JCheckBox cbTempleThree;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   private class ChangeActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  36 */       GDCharShrineAct1Pane.this.changed = true;
/*     */     }
/*     */ 
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
/*     */   public GDCharShrineAct1Pane(int difficulty, int direction) {
/*  56 */     this.difficulty = difficulty;
/*  57 */     this.changed = false;
/*     */     
/*  59 */     adjustUI();
/*     */     
/*  61 */     GroupLayout layout = null;
/*  62 */     GroupLayout.SequentialGroup hGroup = null;
/*  63 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  65 */     layout = new GroupLayout((Container)this);
/*  66 */     setLayout(layout);
/*     */     
/*  68 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  71 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  74 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  77 */     hGroup
/*  78 */       .addGroup(layout.createParallelGroup()
/*  79 */         .addComponent(this.cbBurialHill)
/*  80 */         .addComponent(this.cbDevilsAquifier)
/*  81 */         .addComponent(this.cbWightmireCavern)
/*  82 */         .addComponent(this.cbFloodedPassage)
/*  83 */         .addComponent(this.cbBurialCave)
/*  84 */         .addComponent(this.cbHallowedHill)
/*  85 */         .addComponent(this.cbBurrwitchEstates)
/*  86 */         .addComponent(this.cbUndergroundTransit)
/*  87 */         .addComponent(this.cbCraigsCrags)
/*  88 */         .addComponent(this.cbGuardianDreeg)
/*  89 */         .addComponent(this.cbTempleThree));
/*     */ 
/*     */     
/*  92 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  95 */     vGroup
/*  96 */       .addGroup(layout.createParallelGroup()
/*  97 */         .addComponent(this.cbBurialHill))
/*  98 */       .addGroup(layout.createParallelGroup()
/*  99 */         .addComponent(this.cbDevilsAquifier))
/* 100 */       .addGroup(layout.createParallelGroup()
/* 101 */         .addComponent(this.cbWightmireCavern))
/* 102 */       .addGroup(layout.createParallelGroup()
/* 103 */         .addComponent(this.cbFloodedPassage))
/* 104 */       .addGroup(layout.createParallelGroup()
/* 105 */         .addComponent(this.cbBurialCave))
/* 106 */       .addGroup(layout.createParallelGroup()
/* 107 */         .addComponent(this.cbHallowedHill))
/* 108 */       .addGroup(layout.createParallelGroup()
/* 109 */         .addComponent(this.cbBurrwitchEstates))
/* 110 */       .addGroup(layout.createParallelGroup()
/* 111 */         .addComponent(this.cbUndergroundTransit))
/* 112 */       .addGroup(layout.createParallelGroup()
/* 113 */         .addComponent(this.cbCraigsCrags))
/* 114 */       .addGroup(layout.createParallelGroup()
/* 115 */         .addComponent(this.cbGuardianDreeg))
/* 116 */       .addGroup(layout.createParallelGroup()
/* 117 */         .addComponent(this.cbTempleThree));
/*     */     
/* 119 */     if (direction == 0) {
/* 120 */       layout.setHorizontalGroup(vGroup);
/* 121 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 123 */       layout.setHorizontalGroup(hGroup);
/* 124 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 127 */     layout.linkSize(0, new Component[] { this.cbBurialHill, this.cbDevilsAquifier });
/* 128 */     layout.linkSize(0, new Component[] { this.cbBurialHill, this.cbWightmireCavern });
/* 129 */     layout.linkSize(0, new Component[] { this.cbBurialHill, this.cbFloodedPassage });
/* 130 */     layout.linkSize(0, new Component[] { this.cbBurialHill, this.cbBurialCave });
/* 131 */     layout.linkSize(0, new Component[] { this.cbBurialHill, this.cbHallowedHill });
/* 132 */     layout.linkSize(0, new Component[] { this.cbBurialHill, this.cbBurrwitchEstates });
/* 133 */     layout.linkSize(0, new Component[] { this.cbBurialHill, this.cbUndergroundTransit });
/* 134 */     layout.linkSize(0, new Component[] { this.cbBurialHill, this.cbCraigsCrags });
/* 135 */     layout.linkSize(0, new Component[] { this.cbBurialHill, this.cbGuardianDreeg });
/* 136 */     layout.linkSize(0, new Component[] { this.cbBurialHill, this.cbTempleThree });
/*     */     
/* 138 */     layout.linkSize(1, new Component[] { this.cbBurialHill, this.cbDevilsAquifier });
/* 139 */     layout.linkSize(1, new Component[] { this.cbBurialHill, this.cbWightmireCavern });
/* 140 */     layout.linkSize(1, new Component[] { this.cbBurialHill, this.cbFloodedPassage });
/* 141 */     layout.linkSize(1, new Component[] { this.cbBurialHill, this.cbBurialCave });
/* 142 */     layout.linkSize(1, new Component[] { this.cbBurialHill, this.cbHallowedHill });
/* 143 */     layout.linkSize(1, new Component[] { this.cbBurialHill, this.cbBurrwitchEstates });
/* 144 */     layout.linkSize(1, new Component[] { this.cbBurialHill, this.cbUndergroundTransit });
/* 145 */     layout.linkSize(1, new Component[] { this.cbBurialHill, this.cbCraigsCrags });
/* 146 */     layout.linkSize(1, new Component[] { this.cbBurialHill, this.cbGuardianDreeg });
/* 147 */     layout.linkSize(1, new Component[] { this.cbBurialHill, this.cbTempleThree });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 152 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 153 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 154 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 155 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 156 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 158 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 159 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 160 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 162 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 163 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 164 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 165 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_ACT1"));
/* 166 */     text.setTitleFont(fntBorder);
/*     */     
/* 168 */     setBorder(text);
/*     */     
/* 170 */     if (this.cbBurialHill == null) {
/* 171 */       this.cbBurialHill = new JCheckBox();
/*     */       
/* 173 */       this.cbBurialHill.addActionListener(new ChangeActionListener());
/*     */     } 
/* 175 */     this.cbBurialHill.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_01_BURIAL_HILL"));
/* 176 */     this.cbBurialHill.setFont(fntCheck);
/*     */     
/* 178 */     if (this.cbDevilsAquifier == null) {
/* 179 */       this.cbDevilsAquifier = new JCheckBox();
/*     */       
/* 181 */       this.cbDevilsAquifier.addActionListener(new ChangeActionListener());
/*     */     } 
/* 183 */     this.cbDevilsAquifier.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_01_DEVILS_AQUIFIER"));
/* 184 */     this.cbDevilsAquifier.setFont(fntCheck);
/*     */     
/* 186 */     if (this.cbWightmireCavern == null) {
/* 187 */       this.cbWightmireCavern = new JCheckBox();
/*     */       
/* 189 */       this.cbWightmireCavern.addActionListener(new ChangeActionListener());
/*     */     } 
/* 191 */     this.cbWightmireCavern.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_01_WIGHTMIRE_CAVERN"));
/* 192 */     this.cbWightmireCavern.setFont(fntCheck);
/*     */     
/* 194 */     if (this.cbFloodedPassage == null) {
/* 195 */       this.cbFloodedPassage = new JCheckBox();
/*     */       
/* 197 */       this.cbFloodedPassage.addActionListener(new ChangeActionListener());
/*     */     } 
/* 199 */     this.cbFloodedPassage.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_01_FLOODED_PASSAGE"));
/* 200 */     this.cbFloodedPassage.setFont(fntCheck);
/*     */     
/* 202 */     if (this.cbBurialCave == null) {
/* 203 */       this.cbBurialCave = new JCheckBox();
/*     */       
/* 205 */       this.cbBurialCave.addActionListener(new ChangeActionListener());
/*     */     } 
/* 207 */     this.cbBurialCave.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_01_BURIAL_CAVE"));
/* 208 */     this.cbBurialCave.setFont(fntCheck);
/*     */     
/* 210 */     if (this.cbHallowedHill == null) {
/* 211 */       this.cbHallowedHill = new JCheckBox();
/*     */       
/* 213 */       this.cbHallowedHill.addActionListener(new ChangeActionListener());
/*     */     } 
/* 215 */     this.cbHallowedHill.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_01_HALLOWED_HILL"));
/* 216 */     this.cbHallowedHill.setFont(fntCheck);
/*     */     
/* 218 */     if (this.cbBurrwitchEstates == null) {
/* 219 */       this.cbBurrwitchEstates = new JCheckBox();
/*     */       
/* 221 */       this.cbBurrwitchEstates.addActionListener(new ChangeActionListener());
/*     */     } 
/* 223 */     this.cbBurrwitchEstates.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_01_BURRWITCH_ESTATES"));
/* 224 */     this.cbBurrwitchEstates.setFont(fntCheck);
/*     */     
/* 226 */     if (this.cbUndergroundTransit == null) {
/* 227 */       this.cbUndergroundTransit = new JCheckBox();
/*     */       
/* 229 */       this.cbUndergroundTransit.addActionListener(new ChangeActionListener());
/*     */     } 
/* 231 */     this.cbUndergroundTransit.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_01_UNDERGROUND_TRANSIT"));
/* 232 */     this.cbUndergroundTransit.setFont(fntCheck);
/*     */     
/* 234 */     if (this.cbCraigsCrags == null) {
/* 235 */       this.cbCraigsCrags = new JCheckBox();
/*     */       
/* 237 */       this.cbCraigsCrags.addActionListener(new ChangeActionListener());
/*     */     } 
/* 239 */     this.cbCraigsCrags.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_01_CRAIGS_CRAGS"));
/* 240 */     this.cbCraigsCrags.setFont(fntCheck);
/*     */     
/* 242 */     if (this.cbGuardianDreeg == null) {
/* 243 */       this.cbGuardianDreeg = new JCheckBox();
/*     */       
/* 245 */       this.cbGuardianDreeg.setEnabled(false);
/*     */     } 
/*     */     
/* 248 */     this.cbGuardianDreeg.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_01_GUARDIAN_OF_DREEG"));
/* 249 */     this.cbGuardianDreeg.setFont(fntCheck);
/*     */     
/* 251 */     if (this.cbTempleThree == null) {
/* 252 */       this.cbTempleThree = new JCheckBox();
/*     */       
/* 254 */       this.cbTempleThree.addActionListener(new ChangeActionListener());
/*     */     } 
/* 256 */     this.cbTempleThree.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_01_TEMPLE_OF_THE_THREE"));
/* 257 */     this.cbTempleThree.setFont(fntCheck);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 262 */     return this.changed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged(boolean changed) {
/* 267 */     this.changed = changed;
/*     */   }
/*     */   
/*     */   public void setSelected(boolean selected) {
/* 271 */     this.cbBurialHill.setSelected(selected);
/* 272 */     this.cbDevilsAquifier.setSelected(selected);
/* 273 */     this.cbWightmireCavern.setSelected(selected);
/* 274 */     this.cbFloodedPassage.setSelected(selected);
/* 275 */     this.cbBurialCave.setSelected(selected);
/* 276 */     this.cbHallowedHill.setSelected(selected);
/* 277 */     this.cbBurrwitchEstates.setSelected(selected);
/* 278 */     this.cbUndergroundTransit.setSelected(selected);
/* 279 */     this.cbCraigsCrags.setSelected(selected);
/* 280 */     this.cbGuardianDreeg.setSelected(selected);
/* 281 */     this.cbTempleThree.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getShrineList(boolean found) {
/* 286 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 288 */     if (this.cbBurialHill.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_01_BURIAL_HILL); 
/* 289 */     if (this.cbDevilsAquifier.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_01_DEVILS_AQUIFIER); 
/* 290 */     if (this.cbWightmireCavern.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_01_WIGHTMIRE_CAVERN); 
/* 291 */     if (this.cbFloodedPassage.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_01_FLOODED_PASSAGE); 
/* 292 */     if (this.cbBurialCave.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_01_BURIAL_CAVE); 
/* 293 */     if (this.cbHallowedHill.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_01_HALLOWED_HILL); 
/* 294 */     if (this.cbBurrwitchEstates.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_01_BURRWITCH_ESTATES); 
/* 295 */     if (this.cbUndergroundTransit.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_01_UNDERGROUND_TRANSIT); 
/* 296 */     if (this.cbCraigsCrags.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_01_CRAIGS_CRAGS); 
/* 297 */     if (this.cbGuardianDreeg.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_01_GUARDIAN_DREEG); 
/* 298 */     if (this.cbTempleThree.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_01_TEMPLE_THREE);
/*     */     
/* 300 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 305 */     setSelected(false);
/*     */     
/* 307 */     this.changed = false;
/*     */     
/* 309 */     boolean expFG = GDStashFrame.expansionForgottenGods;
/*     */     
/* 311 */     if (gdc != null) {
/* 312 */       expFG = (expFG || gdc.isForgottenGodsChar());
/*     */     }
/*     */     
/* 315 */     DBShrine.Info info = null;
/*     */     
/* 317 */     info = DBShrine.getShrineInfo("devotionshrinea01.dbr", this.difficulty);
/* 318 */     this.cbBurialHill.setEnabled(info.active);
/*     */     
/* 320 */     info = DBShrine.getShrineInfo("devotionshrinea06.dbr", this.difficulty);
/* 321 */     this.cbDevilsAquifier.setEnabled(info.active);
/*     */     
/* 323 */     info = DBShrine.getShrineInfo("devotionshrinea02.dbr", this.difficulty);
/* 324 */     this.cbWightmireCavern.setEnabled(info.active);
/*     */     
/* 326 */     info = DBShrine.getShrineInfo("devotionshrinea04.dbr", this.difficulty);
/* 327 */     this.cbFloodedPassage.setEnabled(info.active);
/*     */     
/* 329 */     info = DBShrine.getShrineInfo("devotionshrinea03.dbr", this.difficulty);
/* 330 */     this.cbBurialCave.setEnabled(info.active);
/*     */     
/* 332 */     info = DBShrine.getShrineInfo("devotionshrinen04.dbr", this.difficulty);
/* 333 */     this.cbHallowedHill.setEnabled(info.active);
/* 334 */     this.cbHallowedHill.setVisible((info.exists || expFG));
/*     */     
/* 336 */     info = DBShrine.getShrineInfo("devotionshrinea05.dbr", this.difficulty);
/* 337 */     this.cbBurrwitchEstates.setEnabled(info.active);
/*     */     
/* 339 */     info = DBShrine.getShrineInfo("devotionshrinea07.dbr", this.difficulty);
/* 340 */     this.cbUndergroundTransit.setEnabled(info.active);
/*     */     
/* 342 */     info = DBShrine.getShrineInfo("devotionshrinea08.dbr", this.difficulty);
/* 343 */     this.cbCraigsCrags.setEnabled(info.active);
/*     */     
/* 345 */     info = DBShrine.getShrineInfo("devotionshrinen10.dbr", this.difficulty);
/* 346 */     this.cbGuardianDreeg.setEnabled(info.active);
/* 347 */     this.cbGuardianDreeg.setVisible((info.exists || expFG));
/*     */     
/* 349 */     info = DBShrine.getShrineInfo("devotionshrinen03.dbr", this.difficulty);
/* 350 */     this.cbTempleThree.setEnabled(info.active);
/* 351 */     this.cbTempleThree.setVisible((info.exists || expFG));
/*     */     
/* 353 */     if (gdc == null)
/*     */       return; 
/* 355 */     List<GDCharUID> list = gdc.getRestoredShrinesList(this.difficulty);
/*     */     
/* 357 */     for (GDCharUID uid : list) {
/* 358 */       if (uid.equals(GDCharShrineList.UID_SHRINE_01_BURIAL_HILL)) this.cbBurialHill.setSelected(true); 
/* 359 */       if (uid.equals(GDCharShrineList.UID_SHRINE_01_DEVILS_AQUIFIER)) this.cbDevilsAquifier.setSelected(true); 
/* 360 */       if (uid.equals(GDCharShrineList.UID_SHRINE_01_WIGHTMIRE_CAVERN)) this.cbWightmireCavern.setSelected(true); 
/* 361 */       if (uid.equals(GDCharShrineList.UID_SHRINE_01_FLOODED_PASSAGE)) this.cbFloodedPassage.setSelected(true); 
/* 362 */       if (uid.equals(GDCharShrineList.UID_SHRINE_01_BURIAL_CAVE)) this.cbBurialCave.setSelected(true); 
/* 363 */       if (uid.equals(GDCharShrineList.UID_SHRINE_01_HALLOWED_HILL)) this.cbHallowedHill.setSelected(true); 
/* 364 */       if (uid.equals(GDCharShrineList.UID_SHRINE_01_BURRWITCH_ESTATES)) this.cbBurrwitchEstates.setSelected(true); 
/* 365 */       if (uid.equals(GDCharShrineList.UID_SHRINE_01_UNDERGROUND_TRANSIT)) this.cbUndergroundTransit.setSelected(true); 
/* 366 */       if (uid.equals(GDCharShrineList.UID_SHRINE_01_CRAIGS_CRAGS)) this.cbCraigsCrags.setSelected(true); 
/* 367 */       if (uid.equals(GDCharShrineList.UID_SHRINE_01_GUARDIAN_DREEG)) this.cbGuardianDreeg.setSelected(true); 
/* 368 */       if (uid.equals(GDCharShrineList.UID_SHRINE_01_TEMPLE_THREE)) this.cbTempleThree.setSelected(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharShrineAct1Pane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */