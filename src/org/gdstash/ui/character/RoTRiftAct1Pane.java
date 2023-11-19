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
/*     */ public class RoTRiftAct1Pane extends GDAbstractRiftPane {
/*     */   private JCheckBox cbRogueEncampment;
/*     */   private JCheckBox cbColdPlains;
/*     */   private JCheckBox cbStonyField;
/*     */   private JCheckBox cbDarkWood;
/*     */   private JCheckBox cbBlackMarsh;
/*     */   private JCheckBox cbCloisterOuter;
/*     */   private JCheckBox cbJail;
/*     */   private JCheckBox cbCloisterInner;
/*     */   private JCheckBox cbCatacombs;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   private class ChangeActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  35 */       RoTRiftAct1Pane.this.changed = true;
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
/*     */   public RoTRiftAct1Pane(int difficulty, int direction) {
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
/*  76 */         .addComponent(this.cbRogueEncampment)
/*  77 */         .addComponent(this.cbColdPlains)
/*  78 */         .addComponent(this.cbStonyField)
/*  79 */         .addComponent(this.cbDarkWood)
/*  80 */         .addComponent(this.cbBlackMarsh)
/*  81 */         .addComponent(this.cbCloisterOuter)
/*  82 */         .addComponent(this.cbJail)
/*  83 */         .addComponent(this.cbCloisterInner)
/*  84 */         .addComponent(this.cbCatacombs));
/*     */ 
/*     */     
/*  87 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  90 */     vGroup
/*  91 */       .addGroup(layout.createParallelGroup()
/*  92 */         .addComponent(this.cbRogueEncampment))
/*  93 */       .addGroup(layout.createParallelGroup()
/*  94 */         .addComponent(this.cbColdPlains))
/*  95 */       .addGroup(layout.createParallelGroup()
/*  96 */         .addComponent(this.cbStonyField))
/*  97 */       .addGroup(layout.createParallelGroup()
/*  98 */         .addComponent(this.cbDarkWood))
/*  99 */       .addGroup(layout.createParallelGroup()
/* 100 */         .addComponent(this.cbBlackMarsh))
/* 101 */       .addGroup(layout.createParallelGroup()
/* 102 */         .addComponent(this.cbCloisterOuter))
/* 103 */       .addGroup(layout.createParallelGroup()
/* 104 */         .addComponent(this.cbJail))
/* 105 */       .addGroup(layout.createParallelGroup()
/* 106 */         .addComponent(this.cbCloisterInner))
/* 107 */       .addGroup(layout.createParallelGroup()
/* 108 */         .addComponent(this.cbCatacombs));
/*     */     
/* 110 */     if (direction == 0) {
/* 111 */       layout.setHorizontalGroup(vGroup);
/* 112 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 114 */       layout.setHorizontalGroup(hGroup);
/* 115 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 118 */     layout.linkSize(0, new Component[] { this.cbRogueEncampment, this.cbColdPlains });
/* 119 */     layout.linkSize(0, new Component[] { this.cbRogueEncampment, this.cbStonyField });
/* 120 */     layout.linkSize(0, new Component[] { this.cbRogueEncampment, this.cbDarkWood });
/* 121 */     layout.linkSize(0, new Component[] { this.cbRogueEncampment, this.cbBlackMarsh });
/* 122 */     layout.linkSize(0, new Component[] { this.cbRogueEncampment, this.cbCloisterOuter });
/* 123 */     layout.linkSize(0, new Component[] { this.cbRogueEncampment, this.cbJail });
/* 124 */     layout.linkSize(0, new Component[] { this.cbRogueEncampment, this.cbCloisterInner });
/* 125 */     layout.linkSize(0, new Component[] { this.cbRogueEncampment, this.cbCatacombs });
/*     */     
/* 127 */     layout.linkSize(1, new Component[] { this.cbRogueEncampment, this.cbColdPlains });
/* 128 */     layout.linkSize(1, new Component[] { this.cbRogueEncampment, this.cbStonyField });
/* 129 */     layout.linkSize(1, new Component[] { this.cbRogueEncampment, this.cbDarkWood });
/* 130 */     layout.linkSize(1, new Component[] { this.cbRogueEncampment, this.cbBlackMarsh });
/* 131 */     layout.linkSize(1, new Component[] { this.cbRogueEncampment, this.cbCloisterOuter });
/* 132 */     layout.linkSize(1, new Component[] { this.cbRogueEncampment, this.cbJail });
/* 133 */     layout.linkSize(1, new Component[] { this.cbRogueEncampment, this.cbCloisterInner });
/* 134 */     layout.linkSize(1, new Component[] { this.cbRogueEncampment, this.cbCatacombs });
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
/* 157 */     if (this.cbRogueEncampment == null) {
/* 158 */       this.cbRogueEncampment = new JCheckBox();
/*     */       
/* 160 */       this.cbRogueEncampment.addActionListener(new ChangeActionListener());
/*     */     } 
/* 162 */     this.cbRogueEncampment.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_01_ROGUE_ENCAMPMENT"));
/* 163 */     this.cbRogueEncampment.setFont(fntCheck);
/*     */     
/* 165 */     if (this.cbColdPlains == null) {
/* 166 */       this.cbColdPlains = new JCheckBox();
/*     */       
/* 168 */       this.cbColdPlains.addActionListener(new ChangeActionListener());
/*     */     } 
/* 170 */     this.cbColdPlains.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_01_COLD_PLAINS"));
/* 171 */     this.cbColdPlains.setFont(fntCheck);
/*     */     
/* 173 */     if (this.cbStonyField == null) {
/* 174 */       this.cbStonyField = new JCheckBox();
/*     */       
/* 176 */       this.cbStonyField.addActionListener(new ChangeActionListener());
/*     */     } 
/* 178 */     this.cbStonyField.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_01_STONY_FIELD"));
/* 179 */     this.cbStonyField.setFont(fntCheck);
/*     */     
/* 181 */     if (this.cbDarkWood == null) {
/* 182 */       this.cbDarkWood = new JCheckBox();
/*     */       
/* 184 */       this.cbDarkWood.addActionListener(new ChangeActionListener());
/*     */     } 
/* 186 */     this.cbDarkWood.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_01_DARK_WOOD"));
/* 187 */     this.cbDarkWood.setFont(fntCheck);
/*     */     
/* 189 */     if (this.cbBlackMarsh == null) {
/* 190 */       this.cbBlackMarsh = new JCheckBox();
/*     */       
/* 192 */       this.cbBlackMarsh.addActionListener(new ChangeActionListener());
/*     */     } 
/* 194 */     this.cbBlackMarsh.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_01_BLACK_MARSH"));
/* 195 */     this.cbBlackMarsh.setFont(fntCheck);
/*     */     
/* 197 */     if (this.cbCloisterOuter == null) {
/* 198 */       this.cbCloisterOuter = new JCheckBox();
/*     */       
/* 200 */       this.cbCloisterOuter.addActionListener(new ChangeActionListener());
/*     */     } 
/* 202 */     this.cbCloisterOuter.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_01_CLOISTER_OUTER"));
/* 203 */     this.cbCloisterOuter.setFont(fntCheck);
/*     */     
/* 205 */     if (this.cbJail == null) {
/* 206 */       this.cbJail = new JCheckBox();
/*     */       
/* 208 */       this.cbJail.addActionListener(new ChangeActionListener());
/*     */     } 
/* 210 */     this.cbJail.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_01_JAIL"));
/* 211 */     this.cbJail.setFont(fntCheck);
/*     */     
/* 213 */     if (this.cbCloisterInner == null) {
/* 214 */       this.cbCloisterInner = new JCheckBox();
/*     */       
/* 216 */       this.cbCloisterInner.addActionListener(new ChangeActionListener());
/*     */     } 
/* 218 */     this.cbCloisterInner.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_01_CLOISTER_INNER"));
/* 219 */     this.cbCloisterInner.setFont(fntCheck);
/*     */     
/* 221 */     if (this.cbCatacombs == null) {
/* 222 */       this.cbCatacombs = new JCheckBox();
/*     */       
/* 224 */       this.cbCatacombs.addActionListener(new ChangeActionListener());
/*     */     } 
/* 226 */     this.cbCatacombs.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ROT_01_CATACOMBS"));
/* 227 */     this.cbCatacombs.setFont(fntCheck);
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
/* 241 */     this.cbRogueEncampment.setSelected(selected);
/* 242 */     this.cbColdPlains.setSelected(selected);
/* 243 */     this.cbStonyField.setSelected(selected);
/* 244 */     this.cbDarkWood.setSelected(selected);
/* 245 */     this.cbBlackMarsh.setSelected(selected);
/* 246 */     this.cbCloisterOuter.setSelected(selected);
/* 247 */     this.cbJail.setSelected(selected);
/* 248 */     this.cbCloisterInner.setSelected(selected);
/* 249 */     this.cbCatacombs.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getRiftList(boolean found) {
/* 254 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 256 */     if (this.cbRogueEncampment.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_01_ROGUE_ENCAMPMENT); 
/* 257 */     if (this.cbColdPlains.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_01_COLD_PLAINS); 
/* 258 */     if (this.cbStonyField.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_01_STONY_FIELD); 
/* 259 */     if (this.cbDarkWood.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_01_DARK_WOOD); 
/* 260 */     if (this.cbBlackMarsh.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_01_BLACK_MARSH); 
/* 261 */     if (this.cbCloisterOuter.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_01_CLOISTER_OUTER); 
/* 262 */     if (this.cbJail.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_01_JAIL); 
/* 263 */     if (this.cbCloisterInner.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_01_CLOISTER_INNER); 
/* 264 */     if (this.cbCatacombs.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_ROT_01_CATACOMBS);
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
/* 280 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_01_ROGUE_ENCAMPMENT)) this.cbRogueEncampment.setSelected(true); 
/* 281 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_01_COLD_PLAINS)) this.cbColdPlains.setSelected(true); 
/* 282 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_01_STONY_FIELD)) this.cbStonyField.setSelected(true); 
/* 283 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_01_DARK_WOOD)) this.cbDarkWood.setSelected(true); 
/* 284 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_01_BLACK_MARSH)) this.cbBlackMarsh.setSelected(true); 
/* 285 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_01_CLOISTER_OUTER)) this.cbCloisterOuter.setSelected(true); 
/* 286 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_01_JAIL)) this.cbJail.setSelected(true); 
/* 287 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_01_CLOISTER_INNER)) this.cbCloisterInner.setSelected(true); 
/* 288 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_01_CATACOMBS)) this.cbCatacombs.setSelected(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\RoTRiftAct1Pane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */