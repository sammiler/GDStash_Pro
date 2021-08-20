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
/*     */ public class RoTRiftAct5Pane extends GDAbstractRiftPane {
/*     */   private JCheckBox cbHarrogath;
/*     */   private JCheckBox cbFrigidHighlands;
/*     */   private JCheckBox cbArreatPlateau;
/*     */   private JCheckBox cbCrystallinePassage;
/*     */   private JCheckBox cbHallsPain;
/*     */   private JCheckBox cbGlacialTrail;
/*     */   private JCheckBox cbFrozenTundra;
/*     */   private JCheckBox cbAncientsWay;
/*     */   private JCheckBox cbWorldstoneKeep;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   private class ChangeActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  35 */       RoTRiftAct5Pane.this.changed = true;
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
/*     */   public RoTRiftAct5Pane(int difficulty, int direction) {
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
/*  76 */         .addComponent(this.cbHarrogath)
/*  77 */         .addComponent(this.cbFrigidHighlands)
/*  78 */         .addComponent(this.cbArreatPlateau)
/*  79 */         .addComponent(this.cbCrystallinePassage)
/*  80 */         .addComponent(this.cbHallsPain)
/*  81 */         .addComponent(this.cbGlacialTrail)
/*  82 */         .addComponent(this.cbFrozenTundra)
/*  83 */         .addComponent(this.cbAncientsWay)
/*  84 */         .addComponent(this.cbWorldstoneKeep));
/*     */ 
/*     */     
/*  87 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  90 */     vGroup
/*  91 */       .addGroup(layout.createParallelGroup()
/*  92 */         .addComponent(this.cbHarrogath))
/*  93 */       .addGroup(layout.createParallelGroup()
/*  94 */         .addComponent(this.cbFrigidHighlands))
/*  95 */       .addGroup(layout.createParallelGroup()
/*  96 */         .addComponent(this.cbArreatPlateau))
/*  97 */       .addGroup(layout.createParallelGroup()
/*  98 */         .addComponent(this.cbCrystallinePassage))
/*  99 */       .addGroup(layout.createParallelGroup()
/* 100 */         .addComponent(this.cbHallsPain))
/* 101 */       .addGroup(layout.createParallelGroup()
/* 102 */         .addComponent(this.cbGlacialTrail))
/* 103 */       .addGroup(layout.createParallelGroup()
/* 104 */         .addComponent(this.cbFrozenTundra))
/* 105 */       .addGroup(layout.createParallelGroup()
/* 106 */         .addComponent(this.cbAncientsWay))
/* 107 */       .addGroup(layout.createParallelGroup()
/* 108 */         .addComponent(this.cbWorldstoneKeep));
/*     */     
/* 110 */     if (direction == 0) {
/* 111 */       layout.setHorizontalGroup(vGroup);
/* 112 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 114 */       layout.setHorizontalGroup(hGroup);
/* 115 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 118 */     layout.linkSize(0, new Component[] { this.cbHarrogath, this.cbFrigidHighlands });
/* 119 */     layout.linkSize(0, new Component[] { this.cbHarrogath, this.cbArreatPlateau });
/* 120 */     layout.linkSize(0, new Component[] { this.cbHarrogath, this.cbCrystallinePassage });
/* 121 */     layout.linkSize(0, new Component[] { this.cbHarrogath, this.cbHallsPain });
/* 122 */     layout.linkSize(0, new Component[] { this.cbHarrogath, this.cbGlacialTrail });
/* 123 */     layout.linkSize(0, new Component[] { this.cbHarrogath, this.cbFrozenTundra });
/* 124 */     layout.linkSize(0, new Component[] { this.cbHarrogath, this.cbAncientsWay });
/* 125 */     layout.linkSize(0, new Component[] { this.cbHarrogath, this.cbWorldstoneKeep });
/*     */     
/* 127 */     layout.linkSize(1, new Component[] { this.cbHarrogath, this.cbFrigidHighlands });
/* 128 */     layout.linkSize(1, new Component[] { this.cbHarrogath, this.cbArreatPlateau });
/* 129 */     layout.linkSize(1, new Component[] { this.cbHarrogath, this.cbCrystallinePassage });
/* 130 */     layout.linkSize(1, new Component[] { this.cbHarrogath, this.cbHallsPain });
/* 131 */     layout.linkSize(1, new Component[] { this.cbHarrogath, this.cbGlacialTrail });
/* 132 */     layout.linkSize(1, new Component[] { this.cbHarrogath, this.cbFrozenTundra });
/* 133 */     layout.linkSize(1, new Component[] { this.cbHarrogath, this.cbAncientsWay });
/* 134 */     layout.linkSize(1, new Component[] { this.cbHarrogath, this.cbWorldstoneKeep });
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
/* 157 */     if (this.cbHarrogath == null) {
/* 158 */       this.cbHarrogath = new JCheckBox();
/*     */       
/* 160 */       this.cbHarrogath.addActionListener(new ChangeActionListener());
/*     */     } 
/* 162 */     this.cbHarrogath.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_05_HARROGATH"));
/* 163 */     this.cbHarrogath.setFont(fntCheck);
/*     */     
/* 165 */     if (this.cbFrigidHighlands == null) {
/* 166 */       this.cbFrigidHighlands = new JCheckBox();
/*     */       
/* 168 */       this.cbFrigidHighlands.addActionListener(new ChangeActionListener());
/*     */     } 
/* 170 */     this.cbFrigidHighlands.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_05_FRIGID_HIGHLANDS"));
/* 171 */     this.cbFrigidHighlands.setFont(fntCheck);
/*     */     
/* 173 */     if (this.cbArreatPlateau == null) {
/* 174 */       this.cbArreatPlateau = new JCheckBox();
/*     */       
/* 176 */       this.cbArreatPlateau.addActionListener(new ChangeActionListener());
/*     */     } 
/* 178 */     this.cbArreatPlateau.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_05_ARREAT_PLATEAU"));
/* 179 */     this.cbArreatPlateau.setFont(fntCheck);
/*     */     
/* 181 */     if (this.cbCrystallinePassage == null) {
/* 182 */       this.cbCrystallinePassage = new JCheckBox();
/*     */       
/* 184 */       this.cbCrystallinePassage.addActionListener(new ChangeActionListener());
/*     */     } 
/* 186 */     this.cbCrystallinePassage.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_05_CRYSTALLINE_PASSAGE"));
/* 187 */     this.cbCrystallinePassage.setFont(fntCheck);
/*     */     
/* 189 */     if (this.cbHallsPain == null) {
/* 190 */       this.cbHallsPain = new JCheckBox();
/*     */       
/* 192 */       this.cbHallsPain.addActionListener(new ChangeActionListener());
/*     */     } 
/* 194 */     this.cbHallsPain.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_05_HALLS_PAIN"));
/* 195 */     this.cbHallsPain.setFont(fntCheck);
/*     */     
/* 197 */     if (this.cbGlacialTrail == null) {
/* 198 */       this.cbGlacialTrail = new JCheckBox();
/*     */       
/* 200 */       this.cbGlacialTrail.addActionListener(new ChangeActionListener());
/*     */     } 
/* 202 */     this.cbGlacialTrail.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_05_GLACIAL_TRAIL"));
/* 203 */     this.cbGlacialTrail.setFont(fntCheck);
/*     */     
/* 205 */     if (this.cbFrozenTundra == null) {
/* 206 */       this.cbFrozenTundra = new JCheckBox();
/*     */       
/* 208 */       this.cbFrozenTundra.addActionListener(new ChangeActionListener());
/*     */     } 
/* 210 */     this.cbFrozenTundra.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_05_FROZEN_TUNDRA"));
/* 211 */     this.cbFrozenTundra.setFont(fntCheck);
/*     */     
/* 213 */     if (this.cbAncientsWay == null) {
/* 214 */       this.cbAncientsWay = new JCheckBox();
/*     */       
/* 216 */       this.cbAncientsWay.addActionListener(new ChangeActionListener());
/*     */     } 
/* 218 */     this.cbAncientsWay.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_05_ANCIENTS_WAY"));
/* 219 */     this.cbAncientsWay.setFont(fntCheck);
/*     */     
/* 221 */     if (this.cbWorldstoneKeep == null) {
/* 222 */       this.cbWorldstoneKeep = new JCheckBox();
/*     */       
/* 224 */       this.cbWorldstoneKeep.addActionListener(new ChangeActionListener());
/*     */     } 
/* 226 */     this.cbWorldstoneKeep.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_05_WORLDSTONE_KEEP"));
/* 227 */     this.cbWorldstoneKeep.setFont(fntCheck);
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
/* 241 */     this.cbHarrogath.setSelected(selected);
/* 242 */     this.cbFrigidHighlands.setSelected(selected);
/* 243 */     this.cbArreatPlateau.setSelected(selected);
/* 244 */     this.cbCrystallinePassage.setSelected(selected);
/* 245 */     this.cbHallsPain.setSelected(selected);
/* 246 */     this.cbGlacialTrail.setSelected(selected);
/* 247 */     this.cbFrozenTundra.setSelected(selected);
/* 248 */     this.cbAncientsWay.setSelected(selected);
/* 249 */     this.cbWorldstoneKeep.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getRiftList(boolean found) {
/* 254 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 256 */     if (this.cbHarrogath.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_05_HARROGATH); 
/* 257 */     if (this.cbFrigidHighlands.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_05_FRIGID_HIGHLANDS); 
/* 258 */     if (this.cbArreatPlateau.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_05_ARREAT_PLATEAU); 
/* 259 */     if (this.cbCrystallinePassage.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_05_CRYSTALLINE_PASSAGE); 
/* 260 */     if (this.cbHallsPain.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_05_HALLS_PAIN); 
/* 261 */     if (this.cbGlacialTrail.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_05_GLACIAL_TRAIL); 
/* 262 */     if (this.cbFrozenTundra.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_05_FROZEN_TUNDRA); 
/* 263 */     if (this.cbAncientsWay.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_05_ANCIENTS_WAY); 
/* 264 */     if (this.cbWorldstoneKeep.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_05_WORLDSTONE_KEEP);
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
/* 280 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_05_HARROGATH)) this.cbHarrogath.setSelected(true); 
/* 281 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_05_FRIGID_HIGHLANDS)) this.cbFrigidHighlands.setSelected(true); 
/* 282 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_05_ARREAT_PLATEAU)) this.cbArreatPlateau.setSelected(true); 
/* 283 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_05_CRYSTALLINE_PASSAGE)) this.cbCrystallinePassage.setSelected(true); 
/* 284 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_05_HALLS_PAIN)) this.cbHallsPain.setSelected(true); 
/* 285 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_05_GLACIAL_TRAIL)) this.cbGlacialTrail.setSelected(true); 
/* 286 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_05_FROZEN_TUNDRA)) this.cbFrozenTundra.setSelected(true); 
/* 287 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_05_ANCIENTS_WAY)) this.cbAncientsWay.setSelected(true); 
/* 288 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_05_WORLDSTONE_KEEP)) this.cbWorldstoneKeep.setSelected(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\character\RoTRiftAct5Pane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */