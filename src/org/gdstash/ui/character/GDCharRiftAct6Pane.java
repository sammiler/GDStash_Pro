/*     */ package org.gdstash.ui.character;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.util.List;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.character.GDCharTeleportList;
/*     */ import org.gdstash.character.GDCharUID;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDCharRiftAct6Pane extends GDAbstractRiftPane {
/*     */   private JCheckBox cbConclaveOfTheThree;
/*     */   private JCheckBox cbKorvanPlateau;
/*     */   private JCheckBox cbTempleOfOsyr;
/*     */   private JCheckBox cbKorvanSands;
/*     */   private JCheckBox cbCairanDocks;
/*     */   private JCheckBox cbSunbaneOasis;
/*     */   private JCheckBox cbVanguardOfTheThree;
/*     */   private JCheckBox cbRuinsOfAbyd;
/*     */   private JCheckBox cbInfernalWastes;
/*     */   private JCheckBox cbKorvanCity;
/*     */   private JCheckBox cbTombOfTheEldritchSun;
/*     */   private JCheckBox cbEldritchGate;
/*     */   private JCheckBox cbLostOasis;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   private class ChangeActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  35 */       GDCharRiftAct6Pane.this.changed = true;
/*     */     }
/*     */ 
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
/*     */   public GDCharRiftAct6Pane(int difficulty, int direction) {
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
/*  80 */         .addComponent(this.cbConclaveOfTheThree)
/*  81 */         .addComponent(this.cbKorvanPlateau)
/*  82 */         .addComponent(this.cbTempleOfOsyr)
/*  83 */         .addComponent(this.cbKorvanSands)
/*  84 */         .addComponent(this.cbCairanDocks)
/*  85 */         .addComponent(this.cbSunbaneOasis)
/*  86 */         .addComponent(this.cbVanguardOfTheThree)
/*  87 */         .addComponent(this.cbRuinsOfAbyd)
/*  88 */         .addComponent(this.cbInfernalWastes)
/*  89 */         .addComponent(this.cbKorvanCity)
/*  90 */         .addComponent(this.cbTombOfTheEldritchSun)
/*  91 */         .addComponent(this.cbEldritchGate)
/*  92 */         .addComponent(this.cbLostOasis));
/*     */ 
/*     */     
/*  95 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  98 */     vGroup
/*  99 */       .addGroup(layout.createParallelGroup()
/* 100 */         .addComponent(this.cbConclaveOfTheThree))
/* 101 */       .addGroup(layout.createParallelGroup()
/* 102 */         .addComponent(this.cbKorvanPlateau))
/* 103 */       .addGroup(layout.createParallelGroup()
/* 104 */         .addComponent(this.cbTempleOfOsyr))
/* 105 */       .addGroup(layout.createParallelGroup()
/* 106 */         .addComponent(this.cbKorvanSands))
/* 107 */       .addGroup(layout.createParallelGroup()
/* 108 */         .addComponent(this.cbCairanDocks))
/* 109 */       .addGroup(layout.createParallelGroup()
/* 110 */         .addComponent(this.cbSunbaneOasis))
/* 111 */       .addGroup(layout.createParallelGroup()
/* 112 */         .addComponent(this.cbVanguardOfTheThree))
/* 113 */       .addGroup(layout.createParallelGroup()
/* 114 */         .addComponent(this.cbRuinsOfAbyd))
/* 115 */       .addGroup(layout.createParallelGroup()
/* 116 */         .addComponent(this.cbInfernalWastes))
/* 117 */       .addGroup(layout.createParallelGroup()
/* 118 */         .addComponent(this.cbKorvanCity))
/* 119 */       .addGroup(layout.createParallelGroup()
/* 120 */         .addComponent(this.cbTombOfTheEldritchSun))
/* 121 */       .addGroup(layout.createParallelGroup()
/* 122 */         .addComponent(this.cbEldritchGate))
/* 123 */       .addGroup(layout.createParallelGroup()
/* 124 */         .addComponent(this.cbLostOasis));
/*     */     
/* 126 */     if (direction == 0) {
/* 127 */       layout.setHorizontalGroup(vGroup);
/* 128 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 130 */       layout.setHorizontalGroup(hGroup);
/* 131 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 134 */     layout.linkSize(0, new Component[] { this.cbConclaveOfTheThree, this.cbKorvanPlateau });
/* 135 */     layout.linkSize(0, new Component[] { this.cbConclaveOfTheThree, this.cbTempleOfOsyr });
/* 136 */     layout.linkSize(0, new Component[] { this.cbConclaveOfTheThree, this.cbKorvanSands });
/* 137 */     layout.linkSize(0, new Component[] { this.cbConclaveOfTheThree, this.cbCairanDocks });
/* 138 */     layout.linkSize(0, new Component[] { this.cbConclaveOfTheThree, this.cbSunbaneOasis });
/* 139 */     layout.linkSize(0, new Component[] { this.cbConclaveOfTheThree, this.cbVanguardOfTheThree });
/* 140 */     layout.linkSize(0, new Component[] { this.cbConclaveOfTheThree, this.cbRuinsOfAbyd });
/* 141 */     layout.linkSize(0, new Component[] { this.cbConclaveOfTheThree, this.cbInfernalWastes });
/* 142 */     layout.linkSize(0, new Component[] { this.cbConclaveOfTheThree, this.cbKorvanCity });
/* 143 */     layout.linkSize(0, new Component[] { this.cbConclaveOfTheThree, this.cbTombOfTheEldritchSun });
/* 144 */     layout.linkSize(0, new Component[] { this.cbConclaveOfTheThree, this.cbEldritchGate });
/* 145 */     layout.linkSize(0, new Component[] { this.cbConclaveOfTheThree, this.cbLostOasis });
/*     */     
/* 147 */     layout.linkSize(1, new Component[] { this.cbConclaveOfTheThree, this.cbKorvanPlateau });
/* 148 */     layout.linkSize(1, new Component[] { this.cbConclaveOfTheThree, this.cbTempleOfOsyr });
/* 149 */     layout.linkSize(1, new Component[] { this.cbConclaveOfTheThree, this.cbKorvanSands });
/* 150 */     layout.linkSize(1, new Component[] { this.cbConclaveOfTheThree, this.cbCairanDocks });
/* 151 */     layout.linkSize(1, new Component[] { this.cbConclaveOfTheThree, this.cbSunbaneOasis });
/* 152 */     layout.linkSize(1, new Component[] { this.cbConclaveOfTheThree, this.cbVanguardOfTheThree });
/* 153 */     layout.linkSize(1, new Component[] { this.cbConclaveOfTheThree, this.cbRuinsOfAbyd });
/* 154 */     layout.linkSize(1, new Component[] { this.cbConclaveOfTheThree, this.cbInfernalWastes });
/* 155 */     layout.linkSize(1, new Component[] { this.cbConclaveOfTheThree, this.cbKorvanCity });
/* 156 */     layout.linkSize(1, new Component[] { this.cbConclaveOfTheThree, this.cbTombOfTheEldritchSun });
/* 157 */     layout.linkSize(1, new Component[] { this.cbConclaveOfTheThree, this.cbEldritchGate });
/* 158 */     layout.linkSize(1, new Component[] { this.cbConclaveOfTheThree, this.cbLostOasis });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 163 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 164 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 165 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 166 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 167 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 169 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 170 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 171 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 173 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 174 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 175 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 176 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ACT6"));
/* 177 */     text.setTitleFont(fntBorder);
/*     */     
/* 179 */     setBorder(text);
/*     */     
/* 181 */     if (this.cbConclaveOfTheThree == null) {
/* 182 */       this.cbConclaveOfTheThree = new JCheckBox();
/*     */       
/* 184 */       this.cbConclaveOfTheThree.addActionListener(new ChangeActionListener());
/*     */     } 
/* 186 */     this.cbConclaveOfTheThree.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_06_CONCLAVE"));
/* 187 */     this.cbConclaveOfTheThree.setFont(fntCheck);
/*     */     
/* 189 */     if (this.cbKorvanPlateau == null) {
/* 190 */       this.cbKorvanPlateau = new JCheckBox();
/*     */       
/* 192 */       this.cbKorvanPlateau.addActionListener(new ChangeActionListener());
/*     */     } 
/* 194 */     this.cbKorvanPlateau.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_06_KORVAN_PLATEAU"));
/* 195 */     this.cbKorvanPlateau.setFont(fntCheck);
/*     */     
/* 197 */     if (this.cbTempleOfOsyr == null) {
/* 198 */       this.cbTempleOfOsyr = new JCheckBox();
/*     */       
/* 200 */       this.cbTempleOfOsyr.addActionListener(new ChangeActionListener());
/*     */     } 
/* 202 */     this.cbTempleOfOsyr.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_06_TEMPLE_OSYR"));
/* 203 */     this.cbTempleOfOsyr.setFont(fntCheck);
/*     */     
/* 205 */     if (this.cbKorvanSands == null) {
/* 206 */       this.cbKorvanSands = new JCheckBox();
/*     */       
/* 208 */       this.cbKorvanSands.addActionListener(new ChangeActionListener());
/*     */     } 
/* 210 */     this.cbKorvanSands.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_06_KORVAN_SANDS"));
/* 211 */     this.cbKorvanSands.setFont(fntCheck);
/*     */     
/* 213 */     if (this.cbCairanDocks == null) {
/* 214 */       this.cbCairanDocks = new JCheckBox();
/*     */       
/* 216 */       this.cbCairanDocks.addActionListener(new ChangeActionListener());
/*     */     } 
/* 218 */     this.cbCairanDocks.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_06_CAIRAN_DOCKS"));
/* 219 */     this.cbCairanDocks.setFont(fntCheck);
/*     */     
/* 221 */     if (this.cbSunbaneOasis == null) {
/* 222 */       this.cbSunbaneOasis = new JCheckBox();
/*     */       
/* 224 */       this.cbSunbaneOasis.addActionListener(new ChangeActionListener());
/*     */     } 
/* 226 */     this.cbSunbaneOasis.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_06_SUNBANE_OASIS"));
/* 227 */     this.cbSunbaneOasis.setFont(fntCheck);
/*     */     
/* 229 */     if (this.cbVanguardOfTheThree == null) {
/* 230 */       this.cbVanguardOfTheThree = new JCheckBox();
/*     */       
/* 232 */       this.cbVanguardOfTheThree.addActionListener(new ChangeActionListener());
/*     */     } 
/* 234 */     this.cbVanguardOfTheThree.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_06_VANGUARD"));
/* 235 */     this.cbVanguardOfTheThree.setFont(fntCheck);
/*     */     
/* 237 */     if (this.cbRuinsOfAbyd == null) {
/* 238 */       this.cbRuinsOfAbyd = new JCheckBox();
/*     */       
/* 240 */       this.cbRuinsOfAbyd.addActionListener(new ChangeActionListener());
/*     */     } 
/* 242 */     this.cbRuinsOfAbyd.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_06_RUINS_ABYD"));
/* 243 */     this.cbRuinsOfAbyd.setFont(fntCheck);
/*     */     
/* 245 */     if (this.cbInfernalWastes == null) {
/* 246 */       this.cbInfernalWastes = new JCheckBox();
/*     */       
/* 248 */       this.cbInfernalWastes.addActionListener(new ChangeActionListener());
/*     */     } 
/* 250 */     this.cbInfernalWastes.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_06_INFERNAL_WASTES"));
/* 251 */     this.cbInfernalWastes.setFont(fntCheck);
/*     */     
/* 253 */     if (this.cbKorvanCity == null) {
/* 254 */       this.cbKorvanCity = new JCheckBox();
/*     */       
/* 256 */       this.cbKorvanCity.addActionListener(new ChangeActionListener());
/*     */     } 
/* 258 */     this.cbKorvanCity.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_06_KORVAN_CITY"));
/* 259 */     this.cbKorvanCity.setFont(fntCheck);
/*     */     
/* 261 */     if (this.cbTombOfTheEldritchSun == null) {
/* 262 */       this.cbTombOfTheEldritchSun = new JCheckBox();
/*     */       
/* 264 */       this.cbTombOfTheEldritchSun.addActionListener(new ChangeActionListener());
/*     */     } 
/* 266 */     this.cbTombOfTheEldritchSun.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_06_ELDRITCH_SUN"));
/* 267 */     this.cbTombOfTheEldritchSun.setFont(fntCheck);
/*     */     
/* 269 */     if (this.cbEldritchGate == null) {
/* 270 */       this.cbEldritchGate = new JCheckBox();
/*     */       
/* 272 */       this.cbEldritchGate.addActionListener(new ChangeActionListener());
/*     */     } 
/* 274 */     this.cbEldritchGate.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_06_ELDRITCH_GATE"));
/* 275 */     this.cbEldritchGate.setFont(fntCheck);
/*     */     
/* 277 */     if (this.cbLostOasis == null) {
/* 278 */       this.cbLostOasis = new JCheckBox();
/*     */       
/* 280 */       this.cbLostOasis.addActionListener(new ChangeActionListener());
/*     */     } 
/* 282 */     this.cbLostOasis.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_06_LOST_OASIS"));
/* 283 */     this.cbLostOasis.setFont(fntCheck);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 288 */     return this.changed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged(boolean changed) {
/* 293 */     this.changed = changed;
/*     */   }
/*     */   
/*     */   public void setSelected(boolean selected) {
/* 297 */     this.cbConclaveOfTheThree.setSelected(selected);
/* 298 */     this.cbKorvanPlateau.setSelected(selected);
/* 299 */     this.cbTempleOfOsyr.setSelected(selected);
/* 300 */     this.cbKorvanSands.setSelected(selected);
/* 301 */     this.cbCairanDocks.setSelected(selected);
/* 302 */     this.cbSunbaneOasis.setSelected(selected);
/* 303 */     this.cbVanguardOfTheThree.setSelected(selected);
/* 304 */     this.cbRuinsOfAbyd.setSelected(selected);
/* 305 */     this.cbInfernalWastes.setSelected(selected);
/* 306 */     this.cbKorvanCity.setSelected(selected);
/* 307 */     this.cbTombOfTheEldritchSun.setSelected(selected);
/* 308 */     this.cbEldritchGate.setSelected(selected);
/* 309 */     this.cbLostOasis.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getRiftList(boolean found) {
/* 314 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 316 */     if (this.cbConclaveOfTheThree.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_06_CONCLAVE_THREE); 
/* 317 */     if (this.cbKorvanPlateau.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_06_KORVAN_PLATEAU); 
/* 318 */     if (this.cbTempleOfOsyr.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_06_TEMPLE_OSYR); 
/* 319 */     if (this.cbKorvanSands.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_06_KORVAN_SANDS); 
/* 320 */     if (this.cbCairanDocks.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_06_CAIRAN_DOCKS); 
/* 321 */     if (this.cbSunbaneOasis.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_06_SUNBANE_OASIS); 
/* 322 */     if (this.cbVanguardOfTheThree.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_06_VANGUARD_THREE); 
/* 323 */     if (this.cbRuinsOfAbyd.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_06_RUINS_ABYD); 
/* 324 */     if (this.cbInfernalWastes.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_06_INFERNAL_WASTES); 
/* 325 */     if (this.cbKorvanCity.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_06_KORVAN_CITY); 
/* 326 */     if (this.cbTombOfTheEldritchSun.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_06_ELDRITCH_SUN); 
/* 327 */     if (this.cbEldritchGate.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_06_ELDRITCH_GATE); 
/* 328 */     if (this.cbLostOasis.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_06_LOST_OASIS);
/*     */     
/* 330 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 335 */     setSelected(false);
/*     */     
/* 337 */     this.changed = false;
/*     */     
/* 339 */     boolean expFG = GDStashFrame.expansionForgottenGods;
/*     */     
/* 341 */     if (gdc != null) {
/* 342 */       expFG = (expFG || gdc.isForgottenGodsChar());
/*     */     }
/*     */     
/* 345 */     if (expFG) {
/* 346 */       this.cbConclaveOfTheThree.setEnabled(true);
/* 347 */       this.cbKorvanPlateau.setEnabled(true);
/* 348 */       this.cbTempleOfOsyr.setEnabled(true);
/* 349 */       this.cbKorvanSands.setEnabled(true);
/* 350 */       this.cbCairanDocks.setEnabled(true);
/* 351 */       this.cbSunbaneOasis.setEnabled(true);
/* 352 */       this.cbVanguardOfTheThree.setEnabled(true);
/* 353 */       this.cbRuinsOfAbyd.setEnabled(true);
/* 354 */       this.cbInfernalWastes.setEnabled(true);
/* 355 */       this.cbKorvanCity.setEnabled(true);
/* 356 */       this.cbTombOfTheEldritchSun.setEnabled(true);
/* 357 */       this.cbEldritchGate.setEnabled(true);
/* 358 */       this.cbLostOasis.setEnabled(true);
/*     */     } else {
/* 360 */       this.cbConclaveOfTheThree.setEnabled(false);
/* 361 */       this.cbKorvanPlateau.setEnabled(false);
/* 362 */       this.cbTempleOfOsyr.setEnabled(false);
/* 363 */       this.cbKorvanSands.setEnabled(false);
/* 364 */       this.cbCairanDocks.setEnabled(false);
/* 365 */       this.cbSunbaneOasis.setEnabled(false);
/* 366 */       this.cbVanguardOfTheThree.setEnabled(false);
/* 367 */       this.cbRuinsOfAbyd.setEnabled(false);
/* 368 */       this.cbInfernalWastes.setEnabled(false);
/* 369 */       this.cbKorvanCity.setEnabled(false);
/* 370 */       this.cbTombOfTheEldritchSun.setEnabled(false);
/* 371 */       this.cbEldritchGate.setEnabled(false);
/* 372 */       this.cbLostOasis.setEnabled(false);
/*     */     } 
/*     */     
/* 375 */     if (gdc == null)
/*     */       return; 
/* 377 */     List<GDCharUID> list = gdc.getRiftList(this.difficulty);
/*     */     
/* 379 */     for (GDCharUID uid : list) {
/* 380 */       if (uid.equals(GDCharTeleportList.UID_RIFT_06_CONCLAVE_THREE)) this.cbConclaveOfTheThree.setSelected(true); 
/* 381 */       if (uid.equals(GDCharTeleportList.UID_RIFT_06_KORVAN_PLATEAU)) this.cbKorvanPlateau.setSelected(true); 
/* 382 */       if (uid.equals(GDCharTeleportList.UID_RIFT_06_TEMPLE_OSYR)) this.cbTempleOfOsyr.setSelected(true); 
/* 383 */       if (uid.equals(GDCharTeleportList.UID_RIFT_06_KORVAN_SANDS)) this.cbKorvanSands.setSelected(true); 
/* 384 */       if (uid.equals(GDCharTeleportList.UID_RIFT_06_CAIRAN_DOCKS)) this.cbCairanDocks.setSelected(true); 
/* 385 */       if (uid.equals(GDCharTeleportList.UID_RIFT_06_SUNBANE_OASIS)) this.cbSunbaneOasis.setSelected(true); 
/* 386 */       if (uid.equals(GDCharTeleportList.UID_RIFT_06_VANGUARD_THREE)) this.cbVanguardOfTheThree.setSelected(true); 
/* 387 */       if (uid.equals(GDCharTeleportList.UID_RIFT_06_RUINS_ABYD)) this.cbRuinsOfAbyd.setSelected(true); 
/* 388 */       if (uid.equals(GDCharTeleportList.UID_RIFT_06_INFERNAL_WASTES)) this.cbInfernalWastes.setSelected(true); 
/* 389 */       if (uid.equals(GDCharTeleportList.UID_RIFT_06_KORVAN_CITY)) this.cbKorvanCity.setSelected(true); 
/* 390 */       if (uid.equals(GDCharTeleportList.UID_RIFT_06_ELDRITCH_SUN)) this.cbTombOfTheEldritchSun.setSelected(true); 
/* 391 */       if (uid.equals(GDCharTeleportList.UID_RIFT_06_ELDRITCH_GATE)) this.cbEldritchGate.setSelected(true); 
/* 392 */       if (uid.equals(GDCharTeleportList.UID_RIFT_06_LOST_OASIS)) this.cbLostOasis.setSelected(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharRiftAct6Pane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */