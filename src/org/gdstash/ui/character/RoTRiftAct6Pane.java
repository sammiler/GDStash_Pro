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
/*     */ import org.gdstash.character.GDCharTeleportList;
/*     */ import org.gdstash.character.GDCharUID;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.util.GDAbstractRiftPane;
import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class RoTRiftAct6Pane extends GDAbstractRiftPane {
/*     */   private JCheckBox cbShatteredConclave;
/*     */   private JCheckBox cbTristram;
/*     */   private JCheckBox cbLevel02;
/*     */   private JCheckBox cbLevel04;
/*     */   private JCheckBox cbLevel06;
/*     */   private JCheckBox cbLevel08;
/*     */   private JCheckBox cbLevel10;
/*     */   private JCheckBox cbLevel12;
/*     */   private JCheckBox cbLevel14;
/*     */   private JCheckBox cbLevel16;
/*     */   private JCheckBox cbHoradrimTomb;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   private class ChangeActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  35 */       RoTRiftAct6Pane.this.changed = true;
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
/*     */   public RoTRiftAct6Pane(int difficulty, int direction) {
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
/*  78 */         .addComponent(this.cbShatteredConclave)
/*  79 */         .addComponent(this.cbTristram)
/*  80 */         .addComponent(this.cbLevel02)
/*  81 */         .addComponent(this.cbLevel04)
/*  82 */         .addComponent(this.cbLevel06)
/*  83 */         .addComponent(this.cbLevel08)
/*  84 */         .addComponent(this.cbLevel10)
/*  85 */         .addComponent(this.cbLevel12)
/*  86 */         .addComponent(this.cbLevel14)
/*  87 */         .addComponent(this.cbLevel16)
/*  88 */         .addComponent(this.cbHoradrimTomb));
/*     */ 
/*     */     
/*  91 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  94 */     vGroup
/*  95 */       .addGroup(layout.createParallelGroup()
/*  96 */         .addComponent(this.cbShatteredConclave))
/*  97 */       .addGroup(layout.createParallelGroup()
/*  98 */         .addComponent(this.cbTristram))
/*  99 */       .addGroup(layout.createParallelGroup()
/* 100 */         .addComponent(this.cbLevel02))
/* 101 */       .addGroup(layout.createParallelGroup()
/* 102 */         .addComponent(this.cbLevel04))
/* 103 */       .addGroup(layout.createParallelGroup()
/* 104 */         .addComponent(this.cbLevel06))
/* 105 */       .addGroup(layout.createParallelGroup()
/* 106 */         .addComponent(this.cbLevel08))
/* 107 */       .addGroup(layout.createParallelGroup()
/* 108 */         .addComponent(this.cbLevel10))
/* 109 */       .addGroup(layout.createParallelGroup()
/* 110 */         .addComponent(this.cbLevel12))
/* 111 */       .addGroup(layout.createParallelGroup()
/* 112 */         .addComponent(this.cbLevel14))
/* 113 */       .addGroup(layout.createParallelGroup()
/* 114 */         .addComponent(this.cbLevel16))
/* 115 */       .addGroup(layout.createParallelGroup()
/* 116 */         .addComponent(this.cbHoradrimTomb));
/*     */     
/* 118 */     if (direction == 0) {
/* 119 */       layout.setHorizontalGroup(vGroup);
/* 120 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 122 */       layout.setHorizontalGroup(hGroup);
/* 123 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 126 */     layout.linkSize(0, new Component[] { this.cbTristram, this.cbLevel02 });
/* 127 */     layout.linkSize(0, new Component[] { this.cbTristram, this.cbLevel04 });
/* 128 */     layout.linkSize(0, new Component[] { this.cbTristram, this.cbLevel06 });
/* 129 */     layout.linkSize(0, new Component[] { this.cbTristram, this.cbLevel08 });
/* 130 */     layout.linkSize(0, new Component[] { this.cbTristram, this.cbLevel10 });
/* 131 */     layout.linkSize(0, new Component[] { this.cbTristram, this.cbLevel12 });
/* 132 */     layout.linkSize(0, new Component[] { this.cbTristram, this.cbLevel14 });
/* 133 */     layout.linkSize(0, new Component[] { this.cbTristram, this.cbLevel16 });
/* 134 */     layout.linkSize(0, new Component[] { this.cbTristram, this.cbHoradrimTomb });
/*     */     
/* 136 */     layout.linkSize(1, new Component[] { this.cbTristram, this.cbLevel02 });
/* 137 */     layout.linkSize(1, new Component[] { this.cbTristram, this.cbLevel04 });
/* 138 */     layout.linkSize(1, new Component[] { this.cbTristram, this.cbLevel06 });
/* 139 */     layout.linkSize(1, new Component[] { this.cbTristram, this.cbLevel08 });
/* 140 */     layout.linkSize(1, new Component[] { this.cbTristram, this.cbLevel10 });
/* 141 */     layout.linkSize(1, new Component[] { this.cbTristram, this.cbLevel12 });
/* 142 */     layout.linkSize(1, new Component[] { this.cbTristram, this.cbLevel14 });
/* 143 */     layout.linkSize(1, new Component[] { this.cbTristram, this.cbLevel16 });
/* 144 */     layout.linkSize(1, new Component[] { this.cbTristram, this.cbHoradrimTomb });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 149 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 150 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 151 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 152 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 153 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 155 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 156 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 157 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 159 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 160 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 161 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 162 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ACT6"));
/* 163 */     text.setTitleFont(fntBorder);
/*     */     
/* 165 */     setBorder(text);
/*     */     
/* 167 */     if (this.cbShatteredConclave == null) {
/* 168 */       this.cbShatteredConclave = new JCheckBox();
/*     */       
/* 170 */       this.cbShatteredConclave.addActionListener(new ChangeActionListener());
/*     */     } 
/* 172 */     this.cbShatteredConclave.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_06_SHATTERED_CONCLAVE"));
/* 173 */     this.cbShatteredConclave.setFont(fntCheck);
/*     */     
/* 175 */     if (this.cbTristram == null) {
/* 176 */       this.cbTristram = new JCheckBox();
/*     */       
/* 178 */       this.cbTristram.addActionListener(new ChangeActionListener());
/*     */     } 
/* 180 */     this.cbTristram.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_06_TRISTRAM"));
/* 181 */     this.cbTristram.setFont(fntCheck);
/*     */     
/* 183 */     if (this.cbLevel02 == null) {
/* 184 */       this.cbLevel02 = new JCheckBox();
/*     */       
/* 186 */       this.cbLevel02.addActionListener(new ChangeActionListener());
/*     */     } 
/* 188 */     this.cbLevel02.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_06_LEVEL_02"));
/* 189 */     this.cbLevel02.setFont(fntCheck);
/*     */     
/* 191 */     if (this.cbLevel04 == null) {
/* 192 */       this.cbLevel04 = new JCheckBox();
/*     */       
/* 194 */       this.cbLevel04.addActionListener(new ChangeActionListener());
/*     */     } 
/* 196 */     this.cbLevel04.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_06_LEVEL_04"));
/* 197 */     this.cbLevel04.setFont(fntCheck);
/*     */     
/* 199 */     if (this.cbLevel06 == null) {
/* 200 */       this.cbLevel06 = new JCheckBox();
/*     */       
/* 202 */       this.cbLevel06.addActionListener(new ChangeActionListener());
/*     */     } 
/* 204 */     this.cbLevel06.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_06_LEVEL_06"));
/* 205 */     this.cbLevel06.setFont(fntCheck);
/*     */     
/* 207 */     if (this.cbLevel08 == null) {
/* 208 */       this.cbLevel08 = new JCheckBox();
/*     */       
/* 210 */       this.cbLevel08.addActionListener(new ChangeActionListener());
/*     */     } 
/* 212 */     this.cbLevel08.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_06_LEVEL_08"));
/* 213 */     this.cbLevel08.setFont(fntCheck);
/*     */     
/* 215 */     if (this.cbLevel10 == null) {
/* 216 */       this.cbLevel10 = new JCheckBox();
/*     */       
/* 218 */       this.cbLevel10.addActionListener(new ChangeActionListener());
/*     */     } 
/* 220 */     this.cbLevel10.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_06_LEVEL_10"));
/* 221 */     this.cbLevel10.setFont(fntCheck);
/*     */     
/* 223 */     if (this.cbLevel12 == null) {
/* 224 */       this.cbLevel12 = new JCheckBox();
/*     */       
/* 226 */       this.cbLevel12.addActionListener(new ChangeActionListener());
/*     */     } 
/* 228 */     this.cbLevel12.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_06_LEVEL_12"));
/* 229 */     this.cbLevel12.setFont(fntCheck);
/*     */     
/* 231 */     if (this.cbLevel14 == null) {
/* 232 */       this.cbLevel14 = new JCheckBox();
/*     */       
/* 234 */       this.cbLevel14.addActionListener(new ChangeActionListener());
/*     */     } 
/* 236 */     this.cbLevel14.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_06_LEVEL_14"));
/* 237 */     this.cbLevel14.setFont(fntCheck);
/*     */     
/* 239 */     if (this.cbLevel16 == null) {
/* 240 */       this.cbLevel16 = new JCheckBox();
/*     */       
/* 242 */       this.cbLevel16.addActionListener(new ChangeActionListener());
/*     */     } 
/* 244 */     this.cbLevel16.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_06_LEVEL_16"));
/* 245 */     this.cbLevel16.setFont(fntCheck);
/*     */     
/* 247 */     if (this.cbHoradrimTomb == null) {
/* 248 */       this.cbHoradrimTomb = new JCheckBox();
/*     */       
/* 250 */       this.cbHoradrimTomb.addActionListener(new ChangeActionListener());
/*     */     } 
/* 252 */     this.cbHoradrimTomb.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_06_HORADRIM_TOMB"));
/* 253 */     this.cbHoradrimTomb.setFont(fntCheck);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 258 */     return this.changed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged(boolean changed) {
/* 263 */     this.changed = changed;
/*     */   }
/*     */   
/*     */   public void setSelected(boolean selected) {
/* 267 */     this.cbShatteredConclave.setSelected(selected);
/* 268 */     this.cbTristram.setSelected(selected);
/* 269 */     this.cbLevel02.setSelected(selected);
/* 270 */     this.cbLevel04.setSelected(selected);
/* 271 */     this.cbLevel06.setSelected(selected);
/* 272 */     this.cbLevel08.setSelected(selected);
/* 273 */     this.cbLevel10.setSelected(selected);
/* 274 */     this.cbLevel12.setSelected(selected);
/* 275 */     this.cbLevel14.setSelected(selected);
/* 276 */     this.cbLevel16.setSelected(selected);
/* 277 */     this.cbHoradrimTomb.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getRiftList(boolean found) {
/* 282 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 284 */     if (this.cbShatteredConclave.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_06_SHATTERED_CONCLAVE); 
/* 285 */     if (this.cbTristram.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_06_TRISTRAM); 
/* 286 */     if (this.cbLevel02.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_06_LEVEL_02); 
/* 287 */     if (this.cbLevel04.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_06_LEVEL_04); 
/* 288 */     if (this.cbLevel06.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_06_LEVEL_06); 
/* 289 */     if (this.cbLevel08.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_06_LEVEL_08); 
/* 290 */     if (this.cbLevel10.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_06_LEVEL_10); 
/* 291 */     if (this.cbLevel12.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_06_LEVEL_12); 
/* 292 */     if (this.cbLevel14.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_06_LEVEL_14); 
/* 293 */     if (this.cbLevel16.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_06_LEVEL_16); 
/* 294 */     if (this.cbHoradrimTomb.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_06_HORADRIM_TOMB);
/*     */     
/* 296 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 301 */     setSelected(false);
/*     */     
/* 303 */     this.changed = false;
/*     */     
/* 305 */     if (gdc == null)
/*     */       return; 
/* 307 */     List<GDCharUID> list = gdc.getRiftList(this.difficulty);
/*     */     
/* 309 */     for (GDCharUID uid : list) {
/* 310 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_06_SHATTERED_CONCLAVE)) this.cbShatteredConclave.setSelected(true); 
/* 311 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_06_TRISTRAM)) this.cbTristram.setSelected(true); 
/* 312 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_06_LEVEL_02)) this.cbLevel02.setSelected(true); 
/* 313 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_06_LEVEL_04)) this.cbLevel04.setSelected(true); 
/* 314 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_06_LEVEL_06)) this.cbLevel06.setSelected(true); 
/* 315 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_06_LEVEL_08)) this.cbLevel08.setSelected(true); 
/* 316 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_06_LEVEL_10)) this.cbLevel10.setSelected(true); 
/* 317 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_06_LEVEL_12)) this.cbLevel12.setSelected(true); 
/* 318 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_06_LEVEL_14)) this.cbLevel14.setSelected(true); 
/* 319 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_06_LEVEL_16)) this.cbLevel16.setSelected(true); 
/* 320 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_06_HORADRIM_TOMB)) this.cbHoradrimTomb.setSelected(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\RoTRiftAct6Pane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */