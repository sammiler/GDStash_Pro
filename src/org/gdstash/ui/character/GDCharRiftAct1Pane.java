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
/*     */ import org.gdstash.character.GDCharTeleportList;
/*     */ import org.gdstash.character.GDCharUID;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.util.GDAbstractRiftPane;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDCharRiftAct1Pane extends GDAbstractRiftPane {
/*     */   private JCheckBox cbDevilsCrossing;
/*     */   private JCheckBox cbLowerCrossing;
/*     */   private JCheckBox cbWightmire;
/*     */   private JCheckBox cbFoggyBank;
/*     */   private JCheckBox cbFloodedPassage;
/*     */   private JCheckBox cbBurrwitchOutskirts;
/*     */   private JCheckBox cbBurrwitchVillage;
/*     */   private JCheckBox cbWardensCellar;
/*     */   private JCheckBox cbUndergroundTransit;
/*     */   private JCheckBox cbWardensLaboratory;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   private class ChangeActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  35 */       GDCharRiftAct1Pane.this.changed = true;
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
/*     */   public GDCharRiftAct1Pane(int difficulty, int direction) {
/*  54 */     this.difficulty = difficulty;
/*  55 */     this.changed = false;
/*     */     
/*  57 */     adjustUI();
/*     */     
/*  59 */     GroupLayout layout = null;
/*  60 */     GroupLayout.SequentialGroup hGroup = null;
/*  61 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  63 */     layout = new GroupLayout((Container)this);
/*  64 */     setLayout(layout);
/*     */     
/*  66 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  69 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  72 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  75 */     hGroup
/*  76 */       .addGroup(layout.createParallelGroup()
/*  77 */         .addComponent(this.cbDevilsCrossing)
/*  78 */         .addComponent(this.cbLowerCrossing)
/*  79 */         .addComponent(this.cbWightmire)
/*  80 */         .addComponent(this.cbFoggyBank)
/*  81 */         .addComponent(this.cbFloodedPassage)
/*  82 */         .addComponent(this.cbBurrwitchOutskirts)
/*  83 */         .addComponent(this.cbBurrwitchVillage)
/*  84 */         .addComponent(this.cbWardensCellar)
/*  85 */         .addComponent(this.cbUndergroundTransit)
/*  86 */         .addComponent(this.cbWardensLaboratory));
/*     */ 
/*     */     
/*  89 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  92 */     vGroup
/*  93 */       .addGroup(layout.createParallelGroup()
/*  94 */         .addComponent(this.cbDevilsCrossing))
/*  95 */       .addGroup(layout.createParallelGroup()
/*  96 */         .addComponent(this.cbLowerCrossing))
/*  97 */       .addGroup(layout.createParallelGroup()
/*  98 */         .addComponent(this.cbWightmire))
/*  99 */       .addGroup(layout.createParallelGroup()
/* 100 */         .addComponent(this.cbFoggyBank))
/* 101 */       .addGroup(layout.createParallelGroup()
/* 102 */         .addComponent(this.cbFloodedPassage))
/* 103 */       .addGroup(layout.createParallelGroup()
/* 104 */         .addComponent(this.cbBurrwitchOutskirts))
/* 105 */       .addGroup(layout.createParallelGroup()
/* 106 */         .addComponent(this.cbBurrwitchVillage))
/* 107 */       .addGroup(layout.createParallelGroup()
/* 108 */         .addComponent(this.cbWardensCellar))
/* 109 */       .addGroup(layout.createParallelGroup()
/* 110 */         .addComponent(this.cbUndergroundTransit))
/* 111 */       .addGroup(layout.createParallelGroup()
/* 112 */         .addComponent(this.cbWardensLaboratory));
/*     */     
/* 114 */     if (direction == 0) {
/* 115 */       layout.setHorizontalGroup(vGroup);
/* 116 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 118 */       layout.setHorizontalGroup(hGroup);
/* 119 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 122 */     layout.linkSize(0, new Component[] { this.cbDevilsCrossing, this.cbLowerCrossing });
/* 123 */     layout.linkSize(0, new Component[] { this.cbDevilsCrossing, this.cbWightmire });
/* 124 */     layout.linkSize(0, new Component[] { this.cbDevilsCrossing, this.cbFoggyBank });
/* 125 */     layout.linkSize(0, new Component[] { this.cbDevilsCrossing, this.cbFloodedPassage });
/* 126 */     layout.linkSize(0, new Component[] { this.cbDevilsCrossing, this.cbBurrwitchOutskirts });
/* 127 */     layout.linkSize(0, new Component[] { this.cbDevilsCrossing, this.cbBurrwitchVillage });
/* 128 */     layout.linkSize(0, new Component[] { this.cbDevilsCrossing, this.cbWardensCellar });
/* 129 */     layout.linkSize(0, new Component[] { this.cbDevilsCrossing, this.cbUndergroundTransit });
/* 130 */     layout.linkSize(0, new Component[] { this.cbDevilsCrossing, this.cbWardensLaboratory });
/*     */     
/* 132 */     layout.linkSize(1, new Component[] { this.cbDevilsCrossing, this.cbLowerCrossing });
/* 133 */     layout.linkSize(1, new Component[] { this.cbDevilsCrossing, this.cbWightmire });
/* 134 */     layout.linkSize(1, new Component[] { this.cbDevilsCrossing, this.cbFoggyBank });
/* 135 */     layout.linkSize(1, new Component[] { this.cbDevilsCrossing, this.cbFloodedPassage });
/* 136 */     layout.linkSize(1, new Component[] { this.cbDevilsCrossing, this.cbBurrwitchOutskirts });
/* 137 */     layout.linkSize(1, new Component[] { this.cbDevilsCrossing, this.cbBurrwitchVillage });
/* 138 */     layout.linkSize(1, new Component[] { this.cbDevilsCrossing, this.cbWardensCellar });
/* 139 */     layout.linkSize(1, new Component[] { this.cbDevilsCrossing, this.cbUndergroundTransit });
/* 140 */     layout.linkSize(1, new Component[] { this.cbDevilsCrossing, this.cbWardensLaboratory });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 145 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 146 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 147 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 148 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 149 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 151 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 152 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 153 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 155 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 156 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 157 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 158 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ACT1"));
/* 159 */     text.setTitleFont(fntBorder);
/*     */     
/* 161 */     setBorder(text);
/*     */     
/* 163 */     if (this.cbDevilsCrossing == null) {
/* 164 */       this.cbDevilsCrossing = new JCheckBox();
/*     */       
/* 166 */       this.cbDevilsCrossing.addActionListener(new ChangeActionListener());
/*     */     } 
/* 168 */     this.cbDevilsCrossing.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_01_DEVILS_CROSSING"));
/* 169 */     this.cbDevilsCrossing.setFont(fntCheck);
/*     */     
/* 171 */     if (this.cbLowerCrossing == null) {
/* 172 */       this.cbLowerCrossing = new JCheckBox();
/*     */       
/* 174 */       this.cbLowerCrossing.addActionListener(new ChangeActionListener());
/*     */     } 
/* 176 */     this.cbLowerCrossing.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_01_LOWER_CROSSING"));
/* 177 */     this.cbLowerCrossing.setFont(fntCheck);
/*     */     
/* 179 */     if (this.cbWightmire == null) {
/* 180 */       this.cbWightmire = new JCheckBox();
/*     */       
/* 182 */       this.cbWightmire.addActionListener(new ChangeActionListener());
/*     */     } 
/* 184 */     this.cbWightmire.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_01_WIGHTMIRE"));
/* 185 */     this.cbWightmire.setFont(fntCheck);
/*     */     
/* 187 */     if (this.cbFoggyBank == null) {
/* 188 */       this.cbFoggyBank = new JCheckBox();
/*     */       
/* 190 */       this.cbFoggyBank.addActionListener(new ChangeActionListener());
/*     */     } 
/* 192 */     this.cbFoggyBank.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_01_FOGGY_BANK"));
/* 193 */     this.cbFoggyBank.setFont(fntCheck);
/*     */     
/* 195 */     if (this.cbFloodedPassage == null) {
/* 196 */       this.cbFloodedPassage = new JCheckBox();
/*     */       
/* 198 */       this.cbFloodedPassage.addActionListener(new ChangeActionListener());
/*     */     } 
/* 200 */     this.cbFloodedPassage.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_01_FLOODED_PASSAGE"));
/* 201 */     this.cbFloodedPassage.setFont(fntCheck);
/*     */     
/* 203 */     if (this.cbBurrwitchOutskirts == null) {
/* 204 */       this.cbBurrwitchOutskirts = new JCheckBox();
/*     */       
/* 206 */       this.cbBurrwitchOutskirts.addActionListener(new ChangeActionListener());
/*     */     } 
/* 208 */     this.cbBurrwitchOutskirts.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_01_BURRWITCH_OUTSKIRTS"));
/* 209 */     this.cbBurrwitchOutskirts.setFont(fntCheck);
/*     */     
/* 211 */     if (this.cbBurrwitchVillage == null) {
/* 212 */       this.cbBurrwitchVillage = new JCheckBox();
/*     */       
/* 214 */       this.cbBurrwitchVillage.addActionListener(new ChangeActionListener());
/*     */     } 
/* 216 */     this.cbBurrwitchVillage.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_01_BURRWITCH_VILLAGE"));
/* 217 */     this.cbBurrwitchVillage.setFont(fntCheck);
/*     */     
/* 219 */     if (this.cbWardensCellar == null) {
/* 220 */       this.cbWardensCellar = new JCheckBox();
/*     */       
/* 222 */       this.cbWardensCellar.addActionListener(new ChangeActionListener());
/*     */     } 
/* 224 */     this.cbWardensCellar.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_01_WARDENS_CELLAR"));
/* 225 */     this.cbWardensCellar.setFont(fntCheck);
/*     */     
/* 227 */     if (this.cbUndergroundTransit == null) {
/* 228 */       this.cbUndergroundTransit = new JCheckBox();
/*     */       
/* 230 */       this.cbUndergroundTransit.addActionListener(new ChangeActionListener());
/*     */     } 
/* 232 */     this.cbUndergroundTransit.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_01_UNDERGROUND_TRANSIT"));
/* 233 */     this.cbUndergroundTransit.setFont(fntCheck);
/*     */     
/* 235 */     if (this.cbWardensLaboratory == null) {
/* 236 */       this.cbWardensLaboratory = new JCheckBox();
/*     */       
/* 238 */       this.cbWardensLaboratory.addActionListener(new ChangeActionListener());
/*     */     } 
/* 240 */     this.cbWardensLaboratory.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_01_WARDENS_LABORATORY"));
/* 241 */     this.cbWardensLaboratory.setFont(fntCheck);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 246 */     return this.changed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged(boolean changed) {
/* 251 */     this.changed = changed;
/*     */   }
/*     */   
/*     */   public void setSelected(boolean selected) {
/* 255 */     this.cbDevilsCrossing.setSelected(selected);
/* 256 */     this.cbLowerCrossing.setSelected(selected);
/* 257 */     this.cbWightmire.setSelected(selected);
/* 258 */     this.cbFoggyBank.setSelected(selected);
/* 259 */     this.cbFloodedPassage.setSelected(selected);
/* 260 */     this.cbBurrwitchOutskirts.setSelected(selected);
/* 261 */     this.cbBurrwitchVillage.setSelected(selected);
/* 262 */     this.cbWardensCellar.setSelected(selected);
/* 263 */     this.cbUndergroundTransit.setSelected(selected);
/* 264 */     this.cbWardensLaboratory.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getRiftList(boolean found) {
/* 269 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 271 */     if (this.cbDevilsCrossing.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_01_DEVILS_CROSSING); 
/* 272 */     if (this.cbLowerCrossing.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_01_LOWER_CROSSING); 
/* 273 */     if (this.cbWightmire.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_01_WIGHTMIRE); 
/* 274 */     if (this.cbFoggyBank.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_01_FOGGY_BANK); 
/* 275 */     if (this.cbFloodedPassage.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_01_FLOODED_PASSAGE); 
/* 276 */     if (this.cbBurrwitchOutskirts.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_01_BURRWITCH_OUTSKIRTS); 
/* 277 */     if (this.cbBurrwitchVillage.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_01_BURRWITCH_VILLAGE); 
/* 278 */     if (this.cbWardensCellar.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_01_WARDENS_CELLAR); 
/* 279 */     if (this.cbUndergroundTransit.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_01_UNDERGROUND_TRANSIT); 
/* 280 */     if (this.cbWardensLaboratory.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_01_WARDENS_LABORATORY);
/*     */     
/* 282 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 287 */     setSelected(false);
/*     */     
/* 289 */     this.changed = false;
/*     */     
/* 291 */     if (gdc == null)
/*     */       return; 
/* 293 */     List<GDCharUID> list = gdc.getRiftList(this.difficulty);
/*     */     
/* 295 */     for (GDCharUID uid : list) {
/* 296 */       if (uid.equals(GDCharTeleportList.UID_RIFT_01_DEVILS_CROSSING)) this.cbDevilsCrossing.setSelected(true); 
/* 297 */       if (uid.equals(GDCharTeleportList.UID_RIFT_01_LOWER_CROSSING)) this.cbLowerCrossing.setSelected(true); 
/* 298 */       if (uid.equals(GDCharTeleportList.UID_RIFT_01_WIGHTMIRE)) this.cbWightmire.setSelected(true); 
/* 299 */       if (uid.equals(GDCharTeleportList.UID_RIFT_01_FOGGY_BANK)) this.cbFoggyBank.setSelected(true); 
/* 300 */       if (uid.equals(GDCharTeleportList.UID_RIFT_01_FLOODED_PASSAGE)) this.cbFloodedPassage.setSelected(true); 
/* 301 */       if (uid.equals(GDCharTeleportList.UID_RIFT_01_BURRWITCH_OUTSKIRTS)) this.cbBurrwitchOutskirts.setSelected(true); 
/* 302 */       if (uid.equals(GDCharTeleportList.UID_RIFT_01_BURRWITCH_VILLAGE)) this.cbBurrwitchVillage.setSelected(true); 
/* 303 */       if (uid.equals(GDCharTeleportList.UID_RIFT_01_WARDENS_CELLAR)) this.cbWardensCellar.setSelected(true); 
/* 304 */       if (uid.equals(GDCharTeleportList.UID_RIFT_01_UNDERGROUND_TRANSIT)) this.cbUndergroundTransit.setSelected(true); 
/* 305 */       if (uid.equals(GDCharTeleportList.UID_RIFT_01_WARDENS_LABORATORY)) this.cbWardensLaboratory.setSelected(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharRiftAct1Pane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */