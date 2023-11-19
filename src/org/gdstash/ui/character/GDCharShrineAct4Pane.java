/*     */ package org.gdstash.ui.character;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
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
/*     */ import org.gdstash.ui.util.GDAbstractShrinePane;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDCharShrineAct4Pane extends GDAbstractShrinePane {
/*     */   private JCheckBox cbAsterkarnValley;
/*     */   private JCheckBox cbMogdrogensShrine;
/*     */   private JCheckBox cbTombOfTheDamned;
/*     */   private JCheckBox cbGatesOfNecropolis;
/*     */   private JCheckBox cbBlackSepulcher;
/*     */   private JCheckBox cbBastionOfChaos;
/*     */   private JCheckBox cbTombWatchers;
/*     */   private JCheckBox cbSanctumOfTheImmortal;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   private class ChangeActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  36 */       GDCharShrineAct4Pane.this.changed = true;
/*     */     }
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
/*     */   public GDCharShrineAct4Pane(int difficulty, int direction) {
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
/*  76 */         .addComponent(this.cbAsterkarnValley)
/*  77 */         .addComponent(this.cbMogdrogensShrine)
/*  78 */         .addComponent(this.cbTombOfTheDamned)
/*  79 */         .addComponent(this.cbGatesOfNecropolis)
/*  80 */         .addComponent(this.cbBlackSepulcher)
/*  81 */         .addComponent(this.cbBastionOfChaos)
/*  82 */         .addComponent(this.cbTombWatchers)
/*  83 */         .addComponent(this.cbSanctumOfTheImmortal));
/*     */ 
/*     */     
/*  86 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  89 */     vGroup
/*  90 */       .addGroup(layout.createParallelGroup()
/*  91 */         .addComponent(this.cbAsterkarnValley))
/*  92 */       .addGroup(layout.createParallelGroup()
/*  93 */         .addComponent(this.cbMogdrogensShrine))
/*  94 */       .addGroup(layout.createParallelGroup()
/*  95 */         .addComponent(this.cbTombOfTheDamned))
/*  96 */       .addGroup(layout.createParallelGroup()
/*  97 */         .addComponent(this.cbGatesOfNecropolis))
/*  98 */       .addGroup(layout.createParallelGroup()
/*  99 */         .addComponent(this.cbBlackSepulcher))
/* 100 */       .addGroup(layout.createParallelGroup()
/* 101 */         .addComponent(this.cbBastionOfChaos))
/* 102 */       .addGroup(layout.createParallelGroup()
/* 103 */         .addComponent(this.cbTombWatchers))
/* 104 */       .addGroup(layout.createParallelGroup()
/* 105 */         .addComponent(this.cbSanctumOfTheImmortal));
/*     */     
/* 107 */     if (direction == 0) {
/* 108 */       layout.setHorizontalGroup(vGroup);
/* 109 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 111 */       layout.setHorizontalGroup(hGroup);
/* 112 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 115 */     layout.linkSize(0, new Component[] { this.cbAsterkarnValley, this.cbMogdrogensShrine });
/* 116 */     layout.linkSize(0, new Component[] { this.cbAsterkarnValley, this.cbTombOfTheDamned });
/* 117 */     layout.linkSize(0, new Component[] { this.cbAsterkarnValley, this.cbGatesOfNecropolis });
/* 118 */     layout.linkSize(0, new Component[] { this.cbAsterkarnValley, this.cbBlackSepulcher });
/* 119 */     layout.linkSize(0, new Component[] { this.cbAsterkarnValley, this.cbBastionOfChaos });
/* 120 */     layout.linkSize(0, new Component[] { this.cbAsterkarnValley, this.cbTombWatchers });
/* 121 */     layout.linkSize(0, new Component[] { this.cbAsterkarnValley, this.cbSanctumOfTheImmortal });
/*     */     
/* 123 */     layout.linkSize(1, new Component[] { this.cbAsterkarnValley, this.cbMogdrogensShrine });
/* 124 */     layout.linkSize(1, new Component[] { this.cbAsterkarnValley, this.cbTombOfTheDamned });
/* 125 */     layout.linkSize(1, new Component[] { this.cbAsterkarnValley, this.cbGatesOfNecropolis });
/* 126 */     layout.linkSize(1, new Component[] { this.cbAsterkarnValley, this.cbBlackSepulcher });
/* 127 */     layout.linkSize(1, new Component[] { this.cbAsterkarnValley, this.cbBastionOfChaos });
/* 128 */     layout.linkSize(1, new Component[] { this.cbAsterkarnValley, this.cbTombWatchers });
/* 129 */     layout.linkSize(1, new Component[] { this.cbAsterkarnValley, this.cbSanctumOfTheImmortal });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 134 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 135 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 136 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 137 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 138 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 140 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 141 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 142 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 144 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 145 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 146 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 147 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_ACT4"));
/* 148 */     text.setTitleFont(fntBorder);
/*     */     
/* 150 */     setBorder(text);
/*     */     
/* 152 */     if (this.cbAsterkarnValley == null) {
/* 153 */       this.cbAsterkarnValley = new JCheckBox();
/*     */       
/* 155 */       this.cbAsterkarnValley.addActionListener(new ChangeActionListener());
/*     */     } 
/* 157 */     this.cbAsterkarnValley.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_04_ASTERKARN_VALLEY"));
/* 158 */     this.cbAsterkarnValley.setFont(fntCheck);
/*     */     
/* 160 */     if (this.cbMogdrogensShrine == null) {
/* 161 */       this.cbMogdrogensShrine = new JCheckBox();
/*     */       
/* 163 */       this.cbMogdrogensShrine.addActionListener(new ChangeActionListener());
/*     */     } 
/* 165 */     this.cbMogdrogensShrine.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_04_MOGDROGENS_SHRINE"));
/* 166 */     this.cbMogdrogensShrine.setFont(fntCheck);
/*     */     
/* 168 */     if (this.cbTombOfTheDamned == null) {
/* 169 */       this.cbTombOfTheDamned = new JCheckBox();
/*     */       
/* 171 */       this.cbTombOfTheDamned.addActionListener(new ChangeActionListener());
/*     */     } 
/* 173 */     this.cbTombOfTheDamned.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_04_TOMB_OF_THE_DAMNED"));
/* 174 */     this.cbTombOfTheDamned.setFont(fntCheck);
/*     */     
/* 176 */     if (this.cbGatesOfNecropolis == null) {
/* 177 */       this.cbGatesOfNecropolis = new JCheckBox();
/*     */       
/* 179 */       this.cbGatesOfNecropolis.addActionListener(new ChangeActionListener());
/*     */     } 
/* 181 */     this.cbGatesOfNecropolis.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_04_GATES_OF_NECROPOLIS"));
/* 182 */     this.cbGatesOfNecropolis.setFont(fntCheck);
/*     */     
/* 184 */     if (this.cbBlackSepulcher == null) {
/* 185 */       this.cbBlackSepulcher = new JCheckBox();
/*     */       
/* 187 */       this.cbBlackSepulcher.addActionListener(new ChangeActionListener());
/*     */     } 
/* 189 */     this.cbBlackSepulcher.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_04_BLACK_SEPULCHER"));
/* 190 */     this.cbBlackSepulcher.setFont(fntCheck);
/*     */     
/* 192 */     if (this.cbBastionOfChaos == null) {
/* 193 */       this.cbBastionOfChaos = new JCheckBox();
/*     */       
/* 195 */       this.cbBastionOfChaos.addActionListener(new ChangeActionListener());
/*     */     } 
/* 197 */     this.cbBastionOfChaos.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_04_BASTION_OF_CHAOS"));
/* 198 */     this.cbBastionOfChaos.setFont(fntCheck);
/*     */     
/* 200 */     if (this.cbTombWatchers == null) {
/* 201 */       this.cbTombWatchers = new JCheckBox();
/*     */       
/* 203 */       this.cbTombWatchers.addActionListener(new ChangeActionListener());
/*     */     } 
/* 205 */     this.cbTombWatchers.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_04_TOMB_OF_THE_WATCHERS"));
/* 206 */     this.cbTombWatchers.setFont(fntCheck);
/*     */     
/* 208 */     if (this.cbSanctumOfTheImmortal == null) {
/* 209 */       this.cbSanctumOfTheImmortal = new JCheckBox();
/*     */       
/* 211 */       this.cbSanctumOfTheImmortal.addActionListener(new ChangeActionListener());
/*     */     } 
/* 213 */     this.cbSanctumOfTheImmortal.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_04_SANCTUM_OF_THE_IMMORTAL"));
/* 214 */     this.cbSanctumOfTheImmortal.setFont(fntCheck);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 219 */     return this.changed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged(boolean changed) {
/* 224 */     this.changed = changed;
/*     */   }
/*     */   
/*     */   public void setSelected(boolean selected) {
/* 228 */     this.cbAsterkarnValley.setSelected(selected);
/* 229 */     this.cbMogdrogensShrine.setSelected(selected);
/* 230 */     this.cbTombOfTheDamned.setSelected(selected);
/* 231 */     this.cbGatesOfNecropolis.setSelected(selected);
/* 232 */     this.cbBlackSepulcher.setSelected(selected);
/* 233 */     this.cbBastionOfChaos.setSelected(selected);
/* 234 */     this.cbTombWatchers.setSelected(selected);
/* 235 */     this.cbSanctumOfTheImmortal.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getShrineList(boolean found) {
/* 240 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 242 */     if (this.cbAsterkarnValley.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_04_ASTERKARN_VALLEY); 
/* 243 */     if (this.cbMogdrogensShrine.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_04_MOGDROGENS_SHRINE); 
/* 244 */     if (this.cbTombOfTheDamned.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_04_TOMB_OF_THE_DAMNED); 
/* 245 */     if (this.cbGatesOfNecropolis.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_04_GATES_OF_NECROPOLIS); 
/* 246 */     if (this.cbBlackSepulcher.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_04_BLACK_SEPULCHER); 
/* 247 */     if (this.cbBastionOfChaos.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_04_BASTION_OF_CHAOS); 
/* 248 */     if (this.cbTombWatchers.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_04_TOMB_OF_WATCHERS); 
/* 249 */     if (this.cbSanctumOfTheImmortal.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_04_SANCTUM_OF_THE_IMMORTAL);
/*     */     
/* 251 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 256 */     setSelected(false);
/*     */     
/* 258 */     this.changed = false;
/*     */     
/* 260 */     boolean expFG = GDStashFrame.expansionForgottenGods;
/*     */     
/* 262 */     if (gdc != null) {
/* 263 */       expFG = (expFG || gdc.isForgottenGodsChar());
/*     */     }
/*     */     
/* 266 */     DBShrine.Info info = null;
/*     */     
/* 268 */     info = DBShrine.getShrineInfo("devotionshrined01.dbr", this.difficulty);
/* 269 */     this.cbAsterkarnValley.setEnabled(info.active);
/*     */     
/* 271 */     info = DBShrine.getShrineInfo("devotionshrine_mogdrogen.dbr", this.difficulty);
/* 272 */     this.cbMogdrogensShrine.setEnabled(info.active);
/*     */     
/* 274 */     info = DBShrine.getShrineInfo("devotionshrined02.dbr", this.difficulty);
/* 275 */     this.cbTombOfTheDamned.setEnabled(info.active);
/*     */     
/* 277 */     info = DBShrine.getShrineInfo("devotionshrined03.dbr", this.difficulty);
/* 278 */     this.cbGatesOfNecropolis.setEnabled(info.active);
/*     */     
/* 280 */     info = DBShrine.getShrineInfo("devotionshrined04.dbr", this.difficulty);
/* 281 */     this.cbBlackSepulcher.setEnabled(info.active);
/*     */     
/* 283 */     info = DBShrine.getShrineInfo("devotionshrinec07.dbr", this.difficulty);
/* 284 */     this.cbBastionOfChaos.setEnabled(info.active);
/*     */     
/* 286 */     info = DBShrine.getShrineInfo("devotionshrinen07.dbr", this.difficulty);
/* 287 */     this.cbTombWatchers.setEnabled(info.active);
/* 288 */     this.cbTombWatchers.setVisible((info.exists || expFG));
/*     */     
/* 290 */     info = DBShrine.getShrineInfo("devotionshrines01.dbr", this.difficulty);
/* 291 */     this.cbSanctumOfTheImmortal.setEnabled((info.active && this.difficulty == 2));
/*     */     
/* 293 */     if (gdc == null)
/*     */       return; 
/* 295 */     List<GDCharUID> list = gdc.getRestoredShrinesList(this.difficulty);
/*     */     
/* 297 */     for (GDCharUID uid : list) {
/* 298 */       if (uid.equals(GDCharShrineList.UID_SHRINE_04_ASTERKARN_VALLEY)) this.cbAsterkarnValley.setSelected(true); 
/* 299 */       if (uid.equals(GDCharShrineList.UID_SHRINE_04_MOGDROGENS_SHRINE)) this.cbMogdrogensShrine.setSelected(true); 
/* 300 */       if (uid.equals(GDCharShrineList.UID_SHRINE_04_TOMB_OF_THE_DAMNED)) this.cbTombOfTheDamned.setSelected(true); 
/* 301 */       if (uid.equals(GDCharShrineList.UID_SHRINE_04_GATES_OF_NECROPOLIS)) this.cbGatesOfNecropolis.setSelected(true); 
/* 302 */       if (uid.equals(GDCharShrineList.UID_SHRINE_04_BLACK_SEPULCHER)) this.cbBlackSepulcher.setSelected(true); 
/* 303 */       if (uid.equals(GDCharShrineList.UID_SHRINE_04_BASTION_OF_CHAOS)) this.cbBastionOfChaos.setSelected(true); 
/* 304 */       if (uid.equals(GDCharShrineList.UID_SHRINE_04_TOMB_OF_WATCHERS)) this.cbTombWatchers.setSelected(true); 
/* 305 */       if (uid.equals(GDCharShrineList.UID_SHRINE_04_SANCTUM_OF_THE_IMMORTAL)) this.cbSanctumOfTheImmortal.setSelected(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharShrineAct4Pane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */