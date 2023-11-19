/*     */ package org.gdstash.ui.character;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
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
/*     */ import org.gdstash.character.GDCharTeleportList;
/*     */ import org.gdstash.character.GDCharUID;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.util.GDAbstractRiftPane;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class RoTRiftAct2Pane extends GDAbstractRiftPane {
/*     */   private JCheckBox cbLutGholein;
/*     */   private JCheckBox cbSewers;
/*     */   private JCheckBox cbDryHills;
/*     */   private JCheckBox cbHallsDead;
/*     */   private JCheckBox cbFarOasis;
/*     */   private JCheckBox cbLostCity;
/*     */   private JCheckBox cbPalaceCellar;
/*     */   private JCheckBox cbArcaneSanctuary;
/*     */   private JCheckBox cbCanyonMagi;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   private class ChangeActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  35 */       RoTRiftAct2Pane.this.changed = true;
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
/*     */   public RoTRiftAct2Pane(int difficulty, int direction) {
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
/*  76 */         .addComponent(this.cbLutGholein)
/*  77 */         .addComponent(this.cbSewers)
/*  78 */         .addComponent(this.cbDryHills)
/*  79 */         .addComponent(this.cbHallsDead)
/*  80 */         .addComponent(this.cbFarOasis)
/*  81 */         .addComponent(this.cbLostCity)
/*  82 */         .addComponent(this.cbPalaceCellar)
/*  83 */         .addComponent(this.cbArcaneSanctuary)
/*  84 */         .addComponent(this.cbCanyonMagi));
/*     */ 
/*     */     
/*  87 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  90 */     vGroup
/*  91 */       .addGroup(layout.createParallelGroup()
/*  92 */         .addComponent(this.cbLutGholein))
/*  93 */       .addGroup(layout.createParallelGroup()
/*  94 */         .addComponent(this.cbSewers))
/*  95 */       .addGroup(layout.createParallelGroup()
/*  96 */         .addComponent(this.cbDryHills))
/*  97 */       .addGroup(layout.createParallelGroup()
/*  98 */         .addComponent(this.cbHallsDead))
/*  99 */       .addGroup(layout.createParallelGroup()
/* 100 */         .addComponent(this.cbFarOasis))
/* 101 */       .addGroup(layout.createParallelGroup()
/* 102 */         .addComponent(this.cbLostCity))
/* 103 */       .addGroup(layout.createParallelGroup()
/* 104 */         .addComponent(this.cbPalaceCellar))
/* 105 */       .addGroup(layout.createParallelGroup()
/* 106 */         .addComponent(this.cbArcaneSanctuary))
/* 107 */       .addGroup(layout.createParallelGroup()
/* 108 */         .addComponent(this.cbCanyonMagi));
/*     */     
/* 110 */     if (direction == 0) {
/* 111 */       layout.setHorizontalGroup(vGroup);
/* 112 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 114 */       layout.setHorizontalGroup(hGroup);
/* 115 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 118 */     layout.linkSize(0, new Component[] { this.cbLutGholein, this.cbSewers });
/* 119 */     layout.linkSize(0, new Component[] { this.cbLutGholein, this.cbDryHills });
/* 120 */     layout.linkSize(0, new Component[] { this.cbLutGholein, this.cbHallsDead });
/* 121 */     layout.linkSize(0, new Component[] { this.cbLutGholein, this.cbFarOasis });
/* 122 */     layout.linkSize(0, new Component[] { this.cbLutGholein, this.cbLostCity });
/* 123 */     layout.linkSize(0, new Component[] { this.cbLutGholein, this.cbPalaceCellar });
/* 124 */     layout.linkSize(0, new Component[] { this.cbLutGholein, this.cbArcaneSanctuary });
/* 125 */     layout.linkSize(0, new Component[] { this.cbLutGholein, this.cbCanyonMagi });
/*     */     
/* 127 */     layout.linkSize(1, new Component[] { this.cbLutGholein, this.cbSewers });
/* 128 */     layout.linkSize(1, new Component[] { this.cbLutGholein, this.cbDryHills });
/* 129 */     layout.linkSize(1, new Component[] { this.cbLutGholein, this.cbHallsDead });
/* 130 */     layout.linkSize(1, new Component[] { this.cbLutGholein, this.cbFarOasis });
/* 131 */     layout.linkSize(1, new Component[] { this.cbLutGholein, this.cbLostCity });
/* 132 */     layout.linkSize(1, new Component[] { this.cbLutGholein, this.cbPalaceCellar });
/* 133 */     layout.linkSize(1, new Component[] { this.cbLutGholein, this.cbArcaneSanctuary });
/* 134 */     layout.linkSize(1, new Component[] { this.cbLutGholein, this.cbCanyonMagi });
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
/* 152 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ACT2"));
/* 153 */     text.setTitleFont(fntBorder);
/*     */     
/* 155 */     setBorder(text);
/*     */     
/* 157 */     if (this.cbLutGholein == null) {
/* 158 */       this.cbLutGholein = new JCheckBox();
/*     */       
/* 160 */       this.cbLutGholein.addActionListener(new ChangeActionListener());
/*     */     } 
/* 162 */     this.cbLutGholein.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_02_LUT_GHOLEIN"));
/* 163 */     this.cbLutGholein.setFont(fntCheck);
/*     */     
/* 165 */     if (this.cbSewers == null) {
/* 166 */       this.cbSewers = new JCheckBox();
/*     */       
/* 168 */       this.cbSewers.addActionListener(new ChangeActionListener());
/*     */     } 
/* 170 */     this.cbSewers.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_02_SEWERS"));
/* 171 */     this.cbSewers.setFont(fntCheck);
/*     */     
/* 173 */     if (this.cbDryHills == null) {
/* 174 */       this.cbDryHills = new JCheckBox();
/*     */       
/* 176 */       this.cbDryHills.addActionListener(new ChangeActionListener());
/*     */     } 
/* 178 */     this.cbDryHills.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_02_DRY_HILLS"));
/* 179 */     this.cbDryHills.setFont(fntCheck);
/*     */     
/* 181 */     if (this.cbHallsDead == null) {
/* 182 */       this.cbHallsDead = new JCheckBox();
/*     */       
/* 184 */       this.cbHallsDead.addActionListener(new ChangeActionListener());
/*     */     } 
/* 186 */     this.cbHallsDead.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_02_HALLS_DEAD"));
/* 187 */     this.cbHallsDead.setFont(fntCheck);
/*     */     
/* 189 */     if (this.cbFarOasis == null) {
/* 190 */       this.cbFarOasis = new JCheckBox();
/*     */       
/* 192 */       this.cbFarOasis.addActionListener(new ChangeActionListener());
/*     */     } 
/* 194 */     this.cbFarOasis.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_02_FAR_OASIS"));
/* 195 */     this.cbFarOasis.setFont(fntCheck);
/*     */     
/* 197 */     if (this.cbLostCity == null) {
/* 198 */       this.cbLostCity = new JCheckBox();
/*     */       
/* 200 */       this.cbLostCity.addActionListener(new ChangeActionListener());
/*     */     } 
/* 202 */     this.cbLostCity.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_02_LOST_CITY"));
/* 203 */     this.cbLostCity.setFont(fntCheck);
/*     */     
/* 205 */     if (this.cbPalaceCellar == null) {
/* 206 */       this.cbPalaceCellar = new JCheckBox();
/*     */       
/* 208 */       this.cbPalaceCellar.addActionListener(new ChangeActionListener());
/*     */     } 
/* 210 */     this.cbPalaceCellar.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_02_PALACE_CELLAR"));
/* 211 */     this.cbPalaceCellar.setFont(fntCheck);
/*     */     
/* 213 */     if (this.cbArcaneSanctuary == null) {
/* 214 */       this.cbArcaneSanctuary = new JCheckBox();
/*     */       
/* 216 */       this.cbArcaneSanctuary.addActionListener(new ChangeActionListener());
/*     */     } 
/* 218 */     this.cbArcaneSanctuary.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_02_ARCANE_SANCTUARY"));
/* 219 */     this.cbArcaneSanctuary.setFont(fntCheck);
/*     */     
/* 221 */     if (this.cbCanyonMagi == null) {
/* 222 */       this.cbCanyonMagi = new JCheckBox();
/*     */       
/* 224 */       this.cbCanyonMagi.addActionListener(new ChangeActionListener());
/*     */     } 
/* 226 */     this.cbCanyonMagi.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_02_CANYON_MAGI"));
/* 227 */     this.cbCanyonMagi.setFont(fntCheck);
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
/* 241 */     this.cbLutGholein.setSelected(selected);
/* 242 */     this.cbSewers.setSelected(selected);
/* 243 */     this.cbDryHills.setSelected(selected);
/* 244 */     this.cbHallsDead.setSelected(selected);
/* 245 */     this.cbFarOasis.setSelected(selected);
/* 246 */     this.cbLostCity.setSelected(selected);
/* 247 */     this.cbPalaceCellar.setSelected(selected);
/* 248 */     this.cbArcaneSanctuary.setSelected(selected);
/* 249 */     this.cbCanyonMagi.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getRiftList(boolean found) {
/* 254 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 256 */     if (this.cbLutGholein.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_02_LUT_GHOLEIN); 
/* 257 */     if (this.cbSewers.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_02_SEWERS); 
/* 258 */     if (this.cbDryHills.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_02_DRY_HILLS); 
/* 259 */     if (this.cbHallsDead.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_02_HALLS_DEAD); 
/* 260 */     if (this.cbFarOasis.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_02_FAR_OASIS); 
/* 261 */     if (this.cbLostCity.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_02_LOST_CITY); 
/* 262 */     if (this.cbPalaceCellar.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_02_PALACE_CELLAR); 
/* 263 */     if (this.cbArcaneSanctuary.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_02_ARCANE_SANCTUARY); 
/* 264 */     if (this.cbCanyonMagi.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_02_CANYON_MAGI);
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
/* 280 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_02_LUT_GHOLEIN)) this.cbLutGholein.setSelected(true); 
/* 281 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_02_SEWERS)) this.cbSewers.setSelected(true); 
/* 282 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_02_DRY_HILLS)) this.cbDryHills.setSelected(true); 
/* 283 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_02_HALLS_DEAD)) this.cbHallsDead.setSelected(true); 
/* 284 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_02_FAR_OASIS)) this.cbFarOasis.setSelected(true); 
/* 285 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_02_LOST_CITY)) this.cbLostCity.setSelected(true); 
/* 286 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_02_PALACE_CELLAR)) this.cbPalaceCellar.setSelected(true); 
/* 287 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_02_ARCANE_SANCTUARY)) this.cbArcaneSanctuary.setSelected(true); 
/* 288 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_02_CANYON_MAGI)) this.cbCanyonMagi.setSelected(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\RoTRiftAct2Pane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */