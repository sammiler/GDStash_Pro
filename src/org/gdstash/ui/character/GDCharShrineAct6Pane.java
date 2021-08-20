/*     */ package org.gdstash.ui.character;
/*     */ import java.awt.*;
/*     */
/*     */ import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
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
/*     */ public class GDCharShrineAct6Pane extends GDAbstractShrinePane {
/*     */   private JCheckBox cbShatteredRealms;
/*     */   private JCheckBox cbTempleOfOsyr;
/*     */   private JCheckBox cbKorvanOasis;
/*     */   private JCheckBox cbCairanDocks;
/*     */   private JCheckBox cbSandblownRuins;
/*     */   private JCheckBox cbSanctuaryOfHorran;
/*     */   private JCheckBox cbRuinsOfAbyd;
/*     */   private JCheckBox cbValleyOfTheChosen;
/*     */   private JCheckBox cbSunwardSpire;
/*     */   private JCheckBox cbTombOfTheEldritchSun;
/*     */   private JCheckBox cbTombOfTheHeretic;
/*     */   private JCheckBox cbSecretQuest;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   private class ChangeActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  36 */       GDCharShrineAct6Pane.this.changed = true;
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
/*     */   
/*     */   public GDCharShrineAct6Pane(int difficulty, int direction) {
/*  57 */     this.difficulty = difficulty;
/*  58 */     this.changed = false;
/*     */     
/*  60 */     adjustUI();
/*     */     
/*  62 */     GroupLayout layout = null;
/*  63 */     GroupLayout.SequentialGroup hGroup = null;
/*  64 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  66 */     layout = new GroupLayout((Container)this);
/*  67 */     setLayout(layout);
/*     */     
/*  69 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  72 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  75 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  78 */     hGroup
/*  79 */       .addGroup(layout.createParallelGroup()
/*  80 */         .addComponent(this.cbShatteredRealms)
/*  81 */         .addComponent(this.cbTempleOfOsyr)
/*  82 */         .addComponent(this.cbKorvanOasis)
/*  83 */         .addComponent(this.cbCairanDocks)
/*  84 */         .addComponent(this.cbSandblownRuins)
/*  85 */         .addComponent(this.cbSanctuaryOfHorran)
/*  86 */         .addComponent(this.cbRuinsOfAbyd)
/*  87 */         .addComponent(this.cbValleyOfTheChosen)
/*  88 */         .addComponent(this.cbSunwardSpire)
/*  89 */         .addComponent(this.cbTombOfTheEldritchSun)
/*  90 */         .addComponent(this.cbTombOfTheHeretic)
/*  91 */         .addComponent(this.cbSecretQuest));
/*     */ 
/*     */     
/*  94 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  97 */     vGroup
/*  98 */       .addGroup(layout.createParallelGroup()
/*  99 */         .addComponent(this.cbShatteredRealms))
/* 100 */       .addGroup(layout.createParallelGroup()
/* 101 */         .addComponent(this.cbTempleOfOsyr))
/* 102 */       .addGroup(layout.createParallelGroup()
/* 103 */         .addComponent(this.cbKorvanOasis))
/* 104 */       .addGroup(layout.createParallelGroup()
/* 105 */         .addComponent(this.cbCairanDocks))
/* 106 */       .addGroup(layout.createParallelGroup()
/* 107 */         .addComponent(this.cbSandblownRuins))
/* 108 */       .addGroup(layout.createParallelGroup()
/* 109 */         .addComponent(this.cbSanctuaryOfHorran))
/* 110 */       .addGroup(layout.createParallelGroup()
/* 111 */         .addComponent(this.cbRuinsOfAbyd))
/* 112 */       .addGroup(layout.createParallelGroup()
/* 113 */         .addComponent(this.cbValleyOfTheChosen))
/* 114 */       .addGroup(layout.createParallelGroup()
/* 115 */         .addComponent(this.cbSunwardSpire))
/* 116 */       .addGroup(layout.createParallelGroup()
/* 117 */         .addComponent(this.cbTombOfTheEldritchSun))
/* 118 */       .addGroup(layout.createParallelGroup()
/* 119 */         .addComponent(this.cbTombOfTheHeretic))
/* 120 */       .addGroup(layout.createParallelGroup()
/* 121 */         .addComponent(this.cbSecretQuest));
/*     */     
/* 123 */     if (direction == 0) {
/* 124 */       layout.setHorizontalGroup(vGroup);
/* 125 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 127 */       layout.setHorizontalGroup(hGroup);
/* 128 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 131 */     layout.linkSize(0, new Component[] { this.cbShatteredRealms, this.cbTempleOfOsyr });
/* 132 */     layout.linkSize(0, new Component[] { this.cbShatteredRealms, this.cbKorvanOasis });
/* 133 */     layout.linkSize(0, new Component[] { this.cbShatteredRealms, this.cbCairanDocks });
/* 134 */     layout.linkSize(0, new Component[] { this.cbShatteredRealms, this.cbSandblownRuins });
/* 135 */     layout.linkSize(0, new Component[] { this.cbShatteredRealms, this.cbSanctuaryOfHorran });
/* 136 */     layout.linkSize(0, new Component[] { this.cbShatteredRealms, this.cbRuinsOfAbyd });
/* 137 */     layout.linkSize(0, new Component[] { this.cbShatteredRealms, this.cbValleyOfTheChosen });
/* 138 */     layout.linkSize(0, new Component[] { this.cbShatteredRealms, this.cbSunwardSpire });
/* 139 */     layout.linkSize(0, new Component[] { this.cbShatteredRealms, this.cbTombOfTheEldritchSun });
/* 140 */     layout.linkSize(0, new Component[] { this.cbShatteredRealms, this.cbTombOfTheHeretic });
/* 141 */     layout.linkSize(0, new Component[] { this.cbShatteredRealms, this.cbSecretQuest });
/*     */     
/* 143 */     layout.linkSize(1, new Component[] { this.cbShatteredRealms, this.cbTempleOfOsyr });
/* 144 */     layout.linkSize(1, new Component[] { this.cbShatteredRealms, this.cbKorvanOasis });
/* 145 */     layout.linkSize(1, new Component[] { this.cbShatteredRealms, this.cbCairanDocks });
/* 146 */     layout.linkSize(1, new Component[] { this.cbShatteredRealms, this.cbSandblownRuins });
/* 147 */     layout.linkSize(1, new Component[] { this.cbShatteredRealms, this.cbSanctuaryOfHorran });
/* 148 */     layout.linkSize(1, new Component[] { this.cbShatteredRealms, this.cbRuinsOfAbyd });
/* 149 */     layout.linkSize(1, new Component[] { this.cbShatteredRealms, this.cbValleyOfTheChosen });
/* 150 */     layout.linkSize(1, new Component[] { this.cbShatteredRealms, this.cbSunwardSpire });
/* 151 */     layout.linkSize(1, new Component[] { this.cbShatteredRealms, this.cbTombOfTheEldritchSun });
/* 152 */     layout.linkSize(1, new Component[] { this.cbShatteredRealms, this.cbTombOfTheHeretic });
/* 153 */     layout.linkSize(1, new Component[] { this.cbShatteredRealms, this.cbSecretQuest });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 158 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 159 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 160 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 161 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 162 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 164 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 165 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 166 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 168 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 169 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 170 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 171 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_ACT6"));
/* 172 */     text.setTitleFont(fntBorder);
/*     */     
/* 174 */     setBorder(text);
/*     */     
/* 176 */     if (this.cbShatteredRealms == null) {
/* 177 */       this.cbShatteredRealms = new JCheckBox();
/*     */       
/* 179 */       this.cbShatteredRealms.setEnabled(false);
/*     */       
/* 181 */       this.cbShatteredRealms.addActionListener(new ChangeActionListener());
/*     */     } 
/* 183 */     this.cbShatteredRealms.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_06_SHATTERED_REALMS"));
/* 184 */     this.cbShatteredRealms.setFont(fntCheck);
/*     */     
/* 186 */     if (this.cbTempleOfOsyr == null) {
/* 187 */       this.cbTempleOfOsyr = new JCheckBox();
/*     */       
/* 189 */       this.cbTempleOfOsyr.addActionListener(new ChangeActionListener());
/*     */     } 
/* 191 */     this.cbTempleOfOsyr.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_06_TEMPLE_OSYR"));
/* 192 */     this.cbTempleOfOsyr.setFont(fntCheck);
/*     */     
/* 194 */     if (this.cbKorvanOasis == null) {
/* 195 */       this.cbKorvanOasis = new JCheckBox();
/*     */       
/* 197 */       this.cbKorvanOasis.setEnabled(false);
/*     */       
/* 199 */       this.cbKorvanOasis.addActionListener(new ChangeActionListener());
/*     */     } 
/* 201 */     this.cbKorvanOasis.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_06_KORVAN_OASIS"));
/* 202 */     this.cbKorvanOasis.setFont(fntCheck);
/*     */     
/* 204 */     if (this.cbCairanDocks == null) {
/* 205 */       this.cbCairanDocks = new JCheckBox();
/*     */       
/* 207 */       this.cbCairanDocks.addActionListener(new ChangeActionListener());
/*     */     } 
/* 209 */     this.cbCairanDocks.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_06_CAIRAN_DOCKS"));
/* 210 */     this.cbCairanDocks.setFont(fntCheck);
/*     */     
/* 212 */     if (this.cbSandblownRuins == null) {
/* 213 */       this.cbSandblownRuins = new JCheckBox();
/*     */       
/* 215 */       this.cbSandblownRuins.setEnabled(false);
/*     */       
/* 217 */       this.cbSandblownRuins.addActionListener(new ChangeActionListener());
/*     */     } 
/* 219 */     this.cbSandblownRuins.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_06_SANDBLOWN_RUINS"));
/* 220 */     this.cbSandblownRuins.setFont(fntCheck);
/*     */     
/* 222 */     if (this.cbSanctuaryOfHorran == null) {
/* 223 */       this.cbSanctuaryOfHorran = new JCheckBox();
/*     */       
/* 225 */       this.cbSanctuaryOfHorran.addActionListener(new ChangeActionListener());
/*     */     } 
/* 227 */     this.cbSanctuaryOfHorran.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_06_SANCTUARY_HORRAN"));
/* 228 */     this.cbSanctuaryOfHorran.setFont(fntCheck);
/*     */     
/* 230 */     if (this.cbRuinsOfAbyd == null) {
/* 231 */       this.cbRuinsOfAbyd = new JCheckBox();
/*     */       
/* 233 */       this.cbRuinsOfAbyd.addActionListener(new ChangeActionListener());
/*     */     } 
/* 235 */     this.cbRuinsOfAbyd.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_06_RUINS_ABYD"));
/* 236 */     this.cbRuinsOfAbyd.setFont(fntCheck);
/*     */     
/* 238 */     if (this.cbValleyOfTheChosen == null) {
/* 239 */       this.cbValleyOfTheChosen = new JCheckBox();
/*     */       
/* 241 */       this.cbValleyOfTheChosen.addActionListener(new ChangeActionListener());
/*     */     } 
/* 243 */     this.cbValleyOfTheChosen.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_06_VALLEY_CHOSEN"));
/* 244 */     this.cbValleyOfTheChosen.setFont(fntCheck);
/*     */     
/* 246 */     if (this.cbSunwardSpire == null) {
/* 247 */       this.cbSunwardSpire = new JCheckBox();
/*     */       
/* 249 */       this.cbSunwardSpire.setEnabled(false);
/*     */       
/* 251 */       this.cbSunwardSpire.addActionListener(new ChangeActionListener());
/*     */     } 
/* 253 */     this.cbSunwardSpire.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_06_SUNWARD_SPIRE"));
/* 254 */     this.cbSunwardSpire.setFont(fntCheck);
/*     */     
/* 256 */     if (this.cbTombOfTheEldritchSun == null) {
/* 257 */       this.cbTombOfTheEldritchSun = new JCheckBox();
/*     */       
/* 259 */       this.cbTombOfTheEldritchSun.setEnabled(false);
/*     */       
/* 261 */       this.cbTombOfTheEldritchSun.addActionListener(new ChangeActionListener());
/*     */     } 
/* 263 */     this.cbTombOfTheEldritchSun.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_06_ELDRITCH_SUN"));
/* 264 */     this.cbTombOfTheEldritchSun.setFont(fntCheck);
/*     */     
/* 266 */     if (this.cbTombOfTheHeretic == null) {
/* 267 */       this.cbTombOfTheHeretic = new JCheckBox();
/*     */       
/* 269 */       this.cbTombOfTheHeretic.setEnabled(false);
/*     */       
/* 271 */       this.cbTombOfTheHeretic.addActionListener(new ChangeActionListener());
/*     */     } 
/* 273 */     this.cbTombOfTheHeretic.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_06_TOMB_HERETIC"));
/* 274 */     this.cbTombOfTheHeretic.setFont(fntCheck);
/*     */     
/* 276 */     if (this.cbSecretQuest == null) {
/* 277 */       this.cbSecretQuest = new JCheckBox();
/*     */       
/* 279 */       this.cbSecretQuest.addActionListener(new ChangeActionListener());
/*     */     } 
/* 281 */     this.cbSecretQuest.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_06_SECRET_QUEST"));
/* 282 */     this.cbSecretQuest.setFont(fntCheck);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 287 */     return this.changed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged(boolean changed) {
/* 292 */     this.changed = changed;
/*     */   }
/*     */   
/*     */   public void setSelected(boolean selected) {
/* 296 */     this.cbShatteredRealms.setSelected(selected);
/* 297 */     this.cbTempleOfOsyr.setSelected(selected);
/* 298 */     this.cbKorvanOasis.setSelected(selected);
/* 299 */     this.cbCairanDocks.setSelected(selected);
/* 300 */     this.cbSandblownRuins.setSelected(selected);
/* 301 */     this.cbSanctuaryOfHorran.setSelected(selected);
/* 302 */     this.cbRuinsOfAbyd.setSelected(selected);
/* 303 */     this.cbValleyOfTheChosen.setSelected(selected);
/* 304 */     this.cbSunwardSpire.setSelected(selected);
/* 305 */     this.cbTombOfTheEldritchSun.setSelected(selected);
/* 306 */     this.cbSecretQuest.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getShrineList(boolean found) {
/* 311 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 313 */     if (this.cbShatteredRealms.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_06_SHATTERED_REALMS); 
/* 314 */     if (this.cbTempleOfOsyr.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_06_TEMPLE_OSYR); 
/* 315 */     if (this.cbKorvanOasis.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_06_KORVAN_OASIS); 
/* 316 */     if (this.cbCairanDocks.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_06_CAIRAN_DOCKS); 
/* 317 */     if (this.cbSandblownRuins.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_06_SANDBLOWN_RUINS); 
/* 318 */     if (this.cbSanctuaryOfHorran.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_06_SANCTUARY_HORRAN); 
/* 319 */     if (this.cbRuinsOfAbyd.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_06_RUINS_ABYD); 
/* 320 */     if (this.cbValleyOfTheChosen.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_06_VALLEY_CHOSEN); 
/* 321 */     if (this.cbSunwardSpire.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_06_SUNWARD_SPIRE); 
/* 322 */     if (this.cbTombOfTheEldritchSun.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_06_ELDRITCH_SUN); 
/* 323 */     if (this.cbTombOfTheHeretic.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_06_LOST_OASIS); 
/* 324 */     if (this.cbSecretQuest.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_06_SECRET_QUEST);
/*     */     
/* 326 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 331 */     setSelected(false);
/*     */     
/* 333 */     this.changed = false;
/*     */     
/* 335 */     DBShrine.Info info = null;
/*     */     
/* 337 */     info = DBShrine.getShrineInfo("devotionshrineg09.dbr", this.difficulty);
/* 338 */     this.cbShatteredRealms.setEnabled(info.active);
/*     */     
/* 340 */     info = DBShrine.getShrineInfo("devotionshrineg01.dbr", this.difficulty);
/* 341 */     this.cbTempleOfOsyr.setEnabled(info.active);
/*     */     
/* 343 */     info = DBShrine.getShrineInfo("devotionshrineg08.dbr", this.difficulty);
/* 344 */     this.cbKorvanOasis.setEnabled(info.active);
/*     */     
/* 346 */     info = DBShrine.getShrineInfo("devotionshrineg02.dbr", this.difficulty);
/* 347 */     this.cbCairanDocks.setEnabled(info.active);
/*     */     
/* 349 */     info = DBShrine.getShrineInfo("devotionshrineg10.dbr", this.difficulty);
/* 350 */     this.cbSandblownRuins.setEnabled(info.active);
/*     */     
/* 352 */     info = DBShrine.getShrineInfo("devotionshrineg07.dbr", this.difficulty);
/* 353 */     this.cbSanctuaryOfHorran.setEnabled(info.active);
/*     */     
/* 355 */     info = DBShrine.getShrineInfo("devotionshrineg03.dbr", this.difficulty);
/* 356 */     this.cbRuinsOfAbyd.setEnabled(info.active);
/*     */     
/* 358 */     info = DBShrine.getShrineInfo("devotionshrineg04.dbr", this.difficulty);
/* 359 */     this.cbValleyOfTheChosen.setEnabled(info.active);
/*     */     
/* 361 */     info = DBShrine.getShrineInfo("devotionshrineg06.dbr", this.difficulty);
/* 362 */     this.cbSunwardSpire.setEnabled(info.active);
/*     */     
/* 364 */     info = DBShrine.getShrineInfo("devotionshrineg05.dbr", this.difficulty);
/* 365 */     this.cbTombOfTheEldritchSun.setEnabled(info.active);
/*     */     
/* 367 */     info = DBShrine.getShrineInfo("devotionshrineg11.dbr", this.difficulty);
/* 368 */     this.cbTombOfTheHeretic.setEnabled(info.active);
/*     */     
/* 370 */     info = DBShrine.getShrineInfo("devotionshrines04.dbr", this.difficulty);
/* 371 */     this.cbSecretQuest.setEnabled((info.active && this.difficulty == 2));
/*     */     
/* 373 */     if (gdc == null)
/*     */       return; 
/* 375 */     List<GDCharUID> list = gdc.getRestoredShrinesList(this.difficulty);
/*     */     
/* 377 */     for (GDCharUID uid : list) {
/* 378 */       if (uid.equals(GDCharShrineList.UID_SHRINE_06_SHATTERED_REALMS)) this.cbShatteredRealms.setSelected(true); 
/* 379 */       if (uid.equals(GDCharShrineList.UID_SHRINE_06_TEMPLE_OSYR)) this.cbTempleOfOsyr.setSelected(true); 
/* 380 */       if (uid.equals(GDCharShrineList.UID_SHRINE_06_KORVAN_OASIS)) this.cbKorvanOasis.setSelected(true); 
/* 381 */       if (uid.equals(GDCharShrineList.UID_SHRINE_06_CAIRAN_DOCKS)) this.cbCairanDocks.setSelected(true); 
/* 382 */       if (uid.equals(GDCharShrineList.UID_SHRINE_06_SANDBLOWN_RUINS)) this.cbSandblownRuins.setSelected(true); 
/* 383 */       if (uid.equals(GDCharShrineList.UID_SHRINE_06_SANCTUARY_HORRAN)) this.cbSanctuaryOfHorran.setSelected(true); 
/* 384 */       if (uid.equals(GDCharShrineList.UID_SHRINE_06_RUINS_ABYD)) this.cbRuinsOfAbyd.setSelected(true); 
/* 385 */       if (uid.equals(GDCharShrineList.UID_SHRINE_06_VALLEY_CHOSEN)) this.cbValleyOfTheChosen.setSelected(true); 
/* 386 */       if (uid.equals(GDCharShrineList.UID_SHRINE_06_SUNWARD_SPIRE)) this.cbSunwardSpire.setSelected(true); 
/* 387 */       if (uid.equals(GDCharShrineList.UID_SHRINE_06_ELDRITCH_SUN)) this.cbTombOfTheEldritchSun.setSelected(true); 
/* 388 */       if (uid.equals(GDCharShrineList.UID_SHRINE_06_LOST_OASIS)) this.cbTombOfTheHeretic.setSelected(true); 
/* 389 */       if (uid.equals(GDCharShrineList.UID_SHRINE_06_SECRET_QUEST)) this.cbSecretQuest.setSelected(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\character\GDCharShrineAct6Pane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */