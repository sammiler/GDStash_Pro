/*     */ package org.gdstash.ui.character;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.List;
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
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDCharShrineAct5Pane extends GDAbstractShrinePane {
/*     */   private JCheckBox cbGloomwaldCrossing;
/*     */   private JCheckBox cbAncientGrove;
/*     */   private JCheckBox cbUgdenbogKrVall;
/*     */   private JCheckBox cbTombOfUgdal;
/*     */   private JCheckBox cbBarrowholmMine;
/*     */   private JCheckBox cbDesolateWastes;
/*     */   private JCheckBox cbMalmouth;
/*     */   private JCheckBox cbRansackedLighthouse;
/*     */   private JCheckBox cbCrownHill;
/*     */   private JCheckBox cbInfestation;
/*     */   private JCheckBox cbSanctumOfTheChosen;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   private class ChangeActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  36 */       GDCharShrineAct5Pane.this.changed = true;
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
/*     */   public GDCharShrineAct5Pane(int difficulty, int direction) {
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
/*  79 */         .addComponent(this.cbGloomwaldCrossing)
/*  80 */         .addComponent(this.cbAncientGrove)
/*  81 */         .addComponent(this.cbUgdenbogKrVall)
/*  82 */         .addComponent(this.cbTombOfUgdal)
/*  83 */         .addComponent(this.cbBarrowholmMine)
/*  84 */         .addComponent(this.cbDesolateWastes)
/*  85 */         .addComponent(this.cbMalmouth)
/*  86 */         .addComponent(this.cbRansackedLighthouse)
/*  87 */         .addComponent(this.cbCrownHill)
/*  88 */         .addComponent(this.cbInfestation)
/*  89 */         .addComponent(this.cbSanctumOfTheChosen));
/*     */ 
/*     */     
/*  92 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  95 */     vGroup
/*  96 */       .addGroup(layout.createParallelGroup()
/*  97 */         .addComponent(this.cbGloomwaldCrossing))
/*  98 */       .addGroup(layout.createParallelGroup()
/*  99 */         .addComponent(this.cbAncientGrove))
/* 100 */       .addGroup(layout.createParallelGroup()
/* 101 */         .addComponent(this.cbUgdenbogKrVall))
/* 102 */       .addGroup(layout.createParallelGroup()
/* 103 */         .addComponent(this.cbTombOfUgdal))
/* 104 */       .addGroup(layout.createParallelGroup()
/* 105 */         .addComponent(this.cbBarrowholmMine))
/* 106 */       .addGroup(layout.createParallelGroup()
/* 107 */         .addComponent(this.cbDesolateWastes))
/* 108 */       .addGroup(layout.createParallelGroup()
/* 109 */         .addComponent(this.cbMalmouth))
/* 110 */       .addGroup(layout.createParallelGroup()
/* 111 */         .addComponent(this.cbRansackedLighthouse))
/* 112 */       .addGroup(layout.createParallelGroup()
/* 113 */         .addComponent(this.cbCrownHill))
/* 114 */       .addGroup(layout.createParallelGroup()
/* 115 */         .addComponent(this.cbInfestation))
/* 116 */       .addGroup(layout.createParallelGroup()
/* 117 */         .addComponent(this.cbSanctumOfTheChosen));
/*     */     
/* 119 */     if (direction == 0) {
/* 120 */       layout.setHorizontalGroup(vGroup);
/* 121 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 123 */       layout.setHorizontalGroup(hGroup);
/* 124 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 127 */     layout.linkSize(0, new Component[] { this.cbGloomwaldCrossing, this.cbAncientGrove });
/* 128 */     layout.linkSize(0, new Component[] { this.cbGloomwaldCrossing, this.cbUgdenbogKrVall });
/* 129 */     layout.linkSize(0, new Component[] { this.cbGloomwaldCrossing, this.cbTombOfUgdal });
/* 130 */     layout.linkSize(0, new Component[] { this.cbGloomwaldCrossing, this.cbBarrowholmMine });
/* 131 */     layout.linkSize(0, new Component[] { this.cbGloomwaldCrossing, this.cbDesolateWastes });
/* 132 */     layout.linkSize(0, new Component[] { this.cbGloomwaldCrossing, this.cbMalmouth });
/* 133 */     layout.linkSize(0, new Component[] { this.cbGloomwaldCrossing, this.cbRansackedLighthouse });
/* 134 */     layout.linkSize(0, new Component[] { this.cbGloomwaldCrossing, this.cbCrownHill });
/* 135 */     layout.linkSize(0, new Component[] { this.cbGloomwaldCrossing, this.cbInfestation });
/* 136 */     layout.linkSize(0, new Component[] { this.cbGloomwaldCrossing, this.cbSanctumOfTheChosen });
/*     */     
/* 138 */     layout.linkSize(1, new Component[] { this.cbGloomwaldCrossing, this.cbAncientGrove });
/* 139 */     layout.linkSize(1, new Component[] { this.cbGloomwaldCrossing, this.cbUgdenbogKrVall });
/* 140 */     layout.linkSize(1, new Component[] { this.cbGloomwaldCrossing, this.cbTombOfUgdal });
/* 141 */     layout.linkSize(1, new Component[] { this.cbGloomwaldCrossing, this.cbBarrowholmMine });
/* 142 */     layout.linkSize(1, new Component[] { this.cbGloomwaldCrossing, this.cbDesolateWastes });
/* 143 */     layout.linkSize(1, new Component[] { this.cbGloomwaldCrossing, this.cbMalmouth });
/* 144 */     layout.linkSize(1, new Component[] { this.cbGloomwaldCrossing, this.cbRansackedLighthouse });
/* 145 */     layout.linkSize(1, new Component[] { this.cbGloomwaldCrossing, this.cbCrownHill });
/* 146 */     layout.linkSize(1, new Component[] { this.cbGloomwaldCrossing, this.cbInfestation });
/* 147 */     layout.linkSize(1, new Component[] { this.cbGloomwaldCrossing, this.cbSanctumOfTheChosen });
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
/* 165 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_ACT5"));
/* 166 */     text.setTitleFont(fntBorder);
/*     */     
/* 168 */     setBorder(text);
/*     */     
/* 170 */     if (this.cbGloomwaldCrossing == null) {
/* 171 */       this.cbGloomwaldCrossing = new JCheckBox();
/*     */       
/* 173 */       this.cbGloomwaldCrossing.addActionListener(new ChangeActionListener());
/*     */     } 
/* 175 */     this.cbGloomwaldCrossing.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_05_GLOOMWALD_CROSSING"));
/* 176 */     this.cbGloomwaldCrossing.setFont(fntCheck);
/*     */     
/* 178 */     if (this.cbAncientGrove == null) {
/* 179 */       this.cbAncientGrove = new JCheckBox();
/*     */       
/* 181 */       this.cbAncientGrove.addActionListener(new ChangeActionListener());
/*     */     } 
/* 183 */     this.cbAncientGrove.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_05_ANCIENT_GROVE"));
/* 184 */     this.cbAncientGrove.setFont(fntCheck);
/*     */     
/* 186 */     if (this.cbUgdenbogKrVall == null) {
/* 187 */       this.cbUgdenbogKrVall = new JCheckBox();
/*     */       
/* 189 */       this.cbUgdenbogKrVall.addActionListener(new ChangeActionListener());
/*     */     } 
/* 191 */     this.cbUgdenbogKrVall.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_05_UGDENBOG_KRVALL"));
/* 192 */     this.cbUgdenbogKrVall.setFont(fntCheck);
/*     */     
/* 194 */     if (this.cbTombOfUgdal == null) {
/* 195 */       this.cbTombOfUgdal = new JCheckBox();
/*     */       
/* 197 */       this.cbTombOfUgdal.addActionListener(new ChangeActionListener());
/*     */     } 
/* 199 */     this.cbTombOfUgdal.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_05_TOMB_OF_UGDAL"));
/* 200 */     this.cbTombOfUgdal.setFont(fntCheck);
/*     */     
/* 202 */     if (this.cbBarrowholmMine == null) {
/* 203 */       this.cbBarrowholmMine = new JCheckBox();
/*     */       
/* 205 */       this.cbBarrowholmMine.addActionListener(new ChangeActionListener());
/*     */     } 
/* 207 */     this.cbBarrowholmMine.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_05_BARROWHOLM_MINE"));
/* 208 */     this.cbBarrowholmMine.setFont(fntCheck);
/*     */     
/* 210 */     if (this.cbDesolateWastes == null) {
/* 211 */       this.cbDesolateWastes = new JCheckBox();
/*     */       
/* 213 */       this.cbDesolateWastes.addActionListener(new ChangeActionListener());
/*     */     } 
/* 215 */     this.cbDesolateWastes.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_05_DESOLATE_WASTES"));
/* 216 */     this.cbDesolateWastes.setFont(fntCheck);
/*     */     
/* 218 */     if (this.cbMalmouth == null) {
/* 219 */       this.cbMalmouth = new JCheckBox();
/*     */       
/* 221 */       this.cbMalmouth.addActionListener(new ChangeActionListener());
/*     */     } 
/* 223 */     this.cbMalmouth.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_05_MALMOUTH"));
/* 224 */     this.cbMalmouth.setFont(fntCheck);
/*     */     
/* 226 */     if (this.cbRansackedLighthouse == null) {
/* 227 */       this.cbRansackedLighthouse = new JCheckBox();
/*     */       
/* 229 */       this.cbRansackedLighthouse.addActionListener(new ChangeActionListener());
/*     */     } 
/* 231 */     this.cbRansackedLighthouse.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_05_RANSACKED_LIGHTHOUSE"));
/* 232 */     this.cbRansackedLighthouse.setFont(fntCheck);
/*     */     
/* 234 */     if (this.cbCrownHill == null) {
/* 235 */       this.cbCrownHill = new JCheckBox();
/*     */       
/* 237 */       this.cbCrownHill.addActionListener(new ChangeActionListener());
/*     */     } 
/* 239 */     this.cbCrownHill.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_05_CROWN_HILL"));
/* 240 */     this.cbCrownHill.setFont(fntCheck);
/*     */     
/* 242 */     if (this.cbInfestation == null) {
/* 243 */       this.cbInfestation = new JCheckBox();
/*     */       
/* 245 */       this.cbInfestation.addActionListener(new ChangeActionListener());
/*     */     } 
/* 247 */     this.cbInfestation.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_05_INFESTATION"));
/* 248 */     this.cbInfestation.setFont(fntCheck);
/*     */     
/* 250 */     if (this.cbSanctumOfTheChosen == null) {
/* 251 */       this.cbSanctumOfTheChosen = new JCheckBox();
/*     */       
/* 253 */       this.cbSanctumOfTheChosen.addActionListener(new ChangeActionListener());
/*     */     } 
/* 255 */     this.cbSanctumOfTheChosen.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_05_SANCTUM_OF_THE_CHOSEN"));
/* 256 */     this.cbSanctumOfTheChosen.setFont(fntCheck);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 261 */     return this.changed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged(boolean changed) {
/* 266 */     this.changed = changed;
/*     */   }
/*     */   
/*     */   public void setSelected(boolean selected) {
/* 270 */     this.cbGloomwaldCrossing.setSelected(selected);
/* 271 */     this.cbAncientGrove.setSelected(selected);
/* 272 */     this.cbUgdenbogKrVall.setSelected(selected);
/* 273 */     this.cbTombOfUgdal.setSelected(selected);
/* 274 */     this.cbBarrowholmMine.setSelected(selected);
/* 275 */     this.cbDesolateWastes.setSelected(selected);
/* 276 */     this.cbMalmouth.setSelected(selected);
/* 277 */     this.cbRansackedLighthouse.setSelected(selected);
/* 278 */     this.cbCrownHill.setSelected(selected);
/* 279 */     this.cbInfestation.setSelected(selected);
/* 280 */     this.cbSanctumOfTheChosen.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getShrineList(boolean found) {
/* 285 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 287 */     if (this.cbGloomwaldCrossing.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_05_GLOOMWALD_CROSSING); 
/* 288 */     if (this.cbAncientGrove.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_05_ANCIENT_GROVE); 
/* 289 */     if (this.cbUgdenbogKrVall.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_05_UGDENBOG_KR_VALL); 
/* 290 */     if (this.cbTombOfUgdal.isSelected() == found) {
/* 291 */       if (this.difficulty == 0) list.add(GDCharShrineList.UID_SHRINE_05_TOMB_OF_UGDAL); 
/* 292 */       if (this.difficulty == 1) list.add(GDCharShrineList.UID_SHRINE_05_TOMB_OF_UGDAL_ELITE); 
/* 293 */       if (this.difficulty == 2) list.add(GDCharShrineList.UID_SHRINE_05_TOMB_OF_UGDAL); 
/*     */     } 
/* 295 */     if (this.cbBarrowholmMine.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_05_BARROWHOLM_MINE); 
/* 296 */     if (this.cbDesolateWastes.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_05_DESOLATE_WASTES); 
/* 297 */     if (this.cbMalmouth.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_05_MALMOUTH); 
/* 298 */     if (this.cbRansackedLighthouse.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_05_RANSACKED_LIGHTHOUSE); 
/* 299 */     if (this.cbCrownHill.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_05_CROWN_HILL); 
/* 300 */     if (this.cbInfestation.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_05_INFESTATION); 
/* 301 */     if (this.cbSanctumOfTheChosen.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_05_SANCTUM_OF_THE_CHOSEN);
/*     */     
/* 303 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 308 */     setSelected(false);
/*     */     
/* 310 */     this.changed = false;
/*     */     
/* 312 */     DBShrine.Info info = null;
/*     */     
/* 314 */     info = DBShrine.getShrineInfo("devotionshrinee01.dbr", this.difficulty);
/* 315 */     this.cbGloomwaldCrossing.setEnabled(info.active);
/*     */     
/* 317 */     info = DBShrine.getShrineInfo("devotionshrinef03.dbr", this.difficulty);
/* 318 */     this.cbAncientGrove.setEnabled(info.active);
/*     */     
/* 320 */     info = DBShrine.getShrineInfo("devotionshrinen09.dbr", this.difficulty);
/* 321 */     this.cbUgdenbogKrVall.setEnabled(info.active);
/*     */     
/* 323 */     info = DBShrine.getShrineInfo("devotionshrinee02.dbr", this.difficulty);
/* 324 */     this.cbTombOfUgdal.setEnabled(info.active);
/*     */     
/* 326 */     info = DBShrine.getShrineInfo("devotionshrinen08.dbr", this.difficulty);
/* 327 */     this.cbBarrowholmMine.setEnabled(info.active);
/*     */     
/* 329 */     info = DBShrine.getShrineInfo("devotionshrinen06.dbr", this.difficulty);
/* 330 */     this.cbDesolateWastes.setEnabled(info.active);
/*     */     
/* 332 */     info = DBShrine.getShrineInfo("devotionshrinef01.dbr", this.difficulty);
/* 333 */     this.cbMalmouth.setEnabled(info.active);
/*     */     
/* 335 */     info = DBShrine.getShrineInfo("devotionshrinen02.dbr", this.difficulty);
/* 336 */     this.cbRansackedLighthouse.setEnabled(info.active);
/*     */     
/* 338 */     info = DBShrine.getShrineInfo("devotionshrinef02.dbr", this.difficulty);
/* 339 */     this.cbCrownHill.setEnabled(info.active);
/*     */     
/* 341 */     info = DBShrine.getShrineInfo("devotionshrinen11.dbr", this.difficulty);
/* 342 */     this.cbInfestation.setEnabled(info.active);
/*     */     
/* 344 */     info = DBShrine.getShrineInfo("devotionshrines03.dbr", this.difficulty);
/* 345 */     this.cbSanctumOfTheChosen.setEnabled((info.active && this.difficulty == 2));
/*     */     
/* 347 */     if (gdc == null)
/*     */       return; 
/* 349 */     List<GDCharUID> list = gdc.getRestoredShrinesList(this.difficulty);
/*     */     
/* 351 */     for (GDCharUID uid : list) {
/* 352 */       if (uid.equals(GDCharShrineList.UID_SHRINE_05_GLOOMWALD_CROSSING)) this.cbGloomwaldCrossing.setSelected(true); 
/* 353 */       if (uid.equals(GDCharShrineList.UID_SHRINE_05_ANCIENT_GROVE)) this.cbAncientGrove.setSelected(true); 
/* 354 */       if (uid.equals(GDCharShrineList.UID_SHRINE_05_UGDENBOG_KR_VALL)) this.cbUgdenbogKrVall.setSelected(true); 
/* 355 */       if (uid.equals(GDCharShrineList.UID_SHRINE_05_TOMB_OF_UGDAL)) this.cbTombOfUgdal.setSelected(true); 
/* 356 */       if (uid.equals(GDCharShrineList.UID_SHRINE_05_TOMB_OF_UGDAL_ELITE)) this.cbTombOfUgdal.setSelected(true); 
/* 357 */       if (uid.equals(GDCharShrineList.UID_SHRINE_05_BARROWHOLM_MINE)) this.cbBarrowholmMine.setSelected(true); 
/* 358 */       if (uid.equals(GDCharShrineList.UID_SHRINE_05_DESOLATE_WASTES)) this.cbDesolateWastes.setSelected(true); 
/* 359 */       if (uid.equals(GDCharShrineList.UID_SHRINE_05_MALMOUTH)) this.cbMalmouth.setSelected(true); 
/* 360 */       if (uid.equals(GDCharShrineList.UID_SHRINE_05_RANSACKED_LIGHTHOUSE)) this.cbRansackedLighthouse.setSelected(true); 
/* 361 */       if (uid.equals(GDCharShrineList.UID_SHRINE_05_CROWN_HILL)) this.cbCrownHill.setSelected(true); 
/* 362 */       if (uid.equals(GDCharShrineList.UID_SHRINE_05_INFESTATION)) this.cbInfestation.setSelected(true); 
/* 363 */       if (uid.equals(GDCharShrineList.UID_SHRINE_05_SANCTUM_OF_THE_CHOSEN)) this.cbSanctumOfTheChosen.setSelected(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharShrineAct5Pane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */