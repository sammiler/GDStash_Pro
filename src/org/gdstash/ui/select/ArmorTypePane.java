/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import org.gdstash.db.SelectionCriteria;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArmorTypePane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JCheckBox cbHead;
/*     */   private JCheckBox cbShoulders;
/*     */   private JCheckBox cbChest;
/*     */   private JCheckBox cbHands;
/*     */   private JCheckBox cbLegs;
/*     */   private JCheckBox cbFeet;
/*     */   private JCheckBox cbBelt;
/*     */   private JCheckBox cbAmulet;
/*     */   private JCheckBox cbRing;
/*     */   private JCheckBox cbMedal;
/*     */   
/*     */   public ArmorTypePane() {
/*  39 */     this(1);
/*     */   }
/*     */   
/*     */   public ArmorTypePane(int direction) {
/*  43 */     adjustUI();
/*     */     
/*  45 */     GroupLayout layout = null;
/*  46 */     GroupLayout.SequentialGroup hGroup = null;
/*  47 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  49 */     layout = new GroupLayout((Container)this);
/*  50 */     setLayout(layout);
/*     */     
/*  52 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  55 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  58 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  61 */     hGroup
/*  62 */       .addGroup(layout.createParallelGroup()
/*  63 */         .addComponent(this.cbHead)
/*  64 */         .addComponent(this.cbShoulders)
/*  65 */         .addComponent(this.cbChest)
/*  66 */         .addComponent(this.cbHands)
/*  67 */         .addComponent(this.cbLegs)
/*  68 */         .addComponent(this.cbFeet)
/*  69 */         .addComponent(this.cbBelt)
/*  70 */         .addComponent(this.cbAmulet)
/*  71 */         .addComponent(this.cbRing)
/*  72 */         .addComponent(this.cbMedal));
/*     */ 
/*     */     
/*  75 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  78 */     vGroup
/*  79 */       .addGroup(layout.createParallelGroup()
/*  80 */         .addComponent(this.cbHead))
/*  81 */       .addGroup(layout.createParallelGroup()
/*  82 */         .addComponent(this.cbShoulders))
/*  83 */       .addGroup(layout.createParallelGroup()
/*  84 */         .addComponent(this.cbChest))
/*  85 */       .addGroup(layout.createParallelGroup()
/*  86 */         .addComponent(this.cbHands))
/*  87 */       .addGroup(layout.createParallelGroup()
/*  88 */         .addComponent(this.cbLegs))
/*  89 */       .addGroup(layout.createParallelGroup()
/*  90 */         .addComponent(this.cbFeet))
/*  91 */       .addGroup(layout.createParallelGroup()
/*  92 */         .addComponent(this.cbBelt))
/*  93 */       .addGroup(layout.createParallelGroup()
/*  94 */         .addComponent(this.cbAmulet))
/*  95 */       .addGroup(layout.createParallelGroup()
/*  96 */         .addComponent(this.cbRing))
/*  97 */       .addGroup(layout.createParallelGroup()
/*  98 */         .addComponent(this.cbMedal));
/*     */     
/* 100 */     if (direction == 0) {
/* 101 */       layout.setHorizontalGroup(vGroup);
/* 102 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 104 */       layout.setHorizontalGroup(hGroup);
/* 105 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 108 */     layout.linkSize(0, new Component[] { this.cbHead, this.cbShoulders });
/* 109 */     layout.linkSize(0, new Component[] { this.cbHead, this.cbChest });
/* 110 */     layout.linkSize(0, new Component[] { this.cbHead, this.cbHands });
/* 111 */     layout.linkSize(0, new Component[] { this.cbHead, this.cbLegs });
/* 112 */     layout.linkSize(0, new Component[] { this.cbHead, this.cbFeet });
/* 113 */     layout.linkSize(0, new Component[] { this.cbHead, this.cbBelt });
/* 114 */     layout.linkSize(0, new Component[] { this.cbHead, this.cbAmulet });
/* 115 */     layout.linkSize(0, new Component[] { this.cbHead, this.cbRing });
/* 116 */     layout.linkSize(0, new Component[] { this.cbHead, this.cbMedal });
/*     */     
/* 118 */     layout.linkSize(1, new Component[] { this.cbHead, this.cbShoulders });
/* 119 */     layout.linkSize(1, new Component[] { this.cbHead, this.cbChest });
/* 120 */     layout.linkSize(1, new Component[] { this.cbHead, this.cbHands });
/* 121 */     layout.linkSize(1, new Component[] { this.cbHead, this.cbLegs });
/* 122 */     layout.linkSize(1, new Component[] { this.cbHead, this.cbFeet });
/* 123 */     layout.linkSize(1, new Component[] { this.cbHead, this.cbBelt });
/* 124 */     layout.linkSize(1, new Component[] { this.cbHead, this.cbAmulet });
/* 125 */     layout.linkSize(1, new Component[] { this.cbHead, this.cbRing });
/* 126 */     layout.linkSize(1, new Component[] { this.cbHead, this.cbMedal });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 131 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 132 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 133 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 134 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 135 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 137 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 138 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 139 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 141 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 142 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 143 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 144 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_TYPE_ARMOR"));
/* 145 */     text.setTitleFont(fntBorder);
/*     */     
/* 147 */     setBorder(text);
/*     */     
/* 149 */     if (this.cbHead == null) this.cbHead = new JCheckBox(); 
/* 150 */     this.cbHead.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ARMOR_HEAD"));
/* 151 */     this.cbHead.setFont(fntCheck);
/*     */     
/* 153 */     if (this.cbShoulders == null) this.cbShoulders = new JCheckBox(); 
/* 154 */     this.cbShoulders.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ARMOR_SHOULDERS"));
/* 155 */     this.cbShoulders.setFont(fntCheck);
/*     */     
/* 157 */     if (this.cbChest == null) this.cbChest = new JCheckBox(); 
/* 158 */     this.cbChest.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ARMOR_CHEST"));
/* 159 */     this.cbChest.setFont(fntCheck);
/*     */     
/* 161 */     if (this.cbHands == null) this.cbHands = new JCheckBox(); 
/* 162 */     this.cbHands.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ARMOR_HANDS"));
/* 163 */     this.cbHands.setFont(fntCheck);
/*     */     
/* 165 */     if (this.cbLegs == null) this.cbLegs = new JCheckBox(); 
/* 166 */     this.cbLegs.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ARMOR_LEGS"));
/* 167 */     this.cbLegs.setFont(fntCheck);
/*     */     
/* 169 */     if (this.cbFeet == null) this.cbFeet = new JCheckBox(); 
/* 170 */     this.cbFeet.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ARMOR_FEET"));
/* 171 */     this.cbFeet.setFont(fntCheck);
/*     */     
/* 173 */     if (this.cbBelt == null) this.cbBelt = new JCheckBox(); 
/* 174 */     this.cbBelt.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ARMOR_BELT"));
/* 175 */     this.cbBelt.setFont(fntCheck);
/*     */     
/* 177 */     if (this.cbAmulet == null) this.cbAmulet = new JCheckBox(); 
/* 178 */     this.cbAmulet.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ARMOR_AMULET"));
/* 179 */     this.cbAmulet.setFont(fntCheck);
/*     */     
/* 181 */     if (this.cbRing == null) this.cbRing = new JCheckBox(); 
/* 182 */     this.cbRing.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ARMOR_RING"));
/* 183 */     this.cbRing.setFont(fntCheck);
/*     */     
/* 185 */     if (this.cbMedal == null) this.cbMedal = new JCheckBox(); 
/* 186 */     this.cbMedal.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ARMOR_MEDAL"));
/* 187 */     this.cbMedal.setFont(fntCheck);
/*     */   }
/*     */   
/*     */   public void setSelection(int sel) {
/* 191 */     clear();
/*     */     
/* 193 */     switch (sel) {
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 198 */         this.cbHead.setSelected(true);
/* 199 */         this.cbShoulders.setSelected(true);
/* 200 */         this.cbChest.setSelected(true);
/* 201 */         this.cbHands.setSelected(true);
/* 202 */         this.cbLegs.setSelected(true);
/* 203 */         this.cbFeet.setSelected(true);
/* 204 */         this.cbBelt.setSelected(true);
/* 205 */         this.cbAmulet.setSelected(true);
/* 206 */         this.cbRing.setSelected(true);
/* 207 */         this.cbMedal.setSelected(true);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 214 */         this.cbHead.setSelected(true);
/* 215 */         this.cbShoulders.setSelected(true);
/* 216 */         this.cbChest.setSelected(true);
/* 217 */         this.cbHands.setSelected(true);
/* 218 */         this.cbLegs.setSelected(true);
/* 219 */         this.cbFeet.setSelected(true);
/* 220 */         this.cbBelt.setSelected(true);
/* 221 */         this.cbAmulet.setSelected(true);
/* 222 */         this.cbRing.setSelected(true);
/* 223 */         this.cbMedal.setSelected(true);
/*     */         break;
/*     */       
/*     */       case 4:
/* 227 */         this.cbHead.setSelected(true);
/* 228 */         this.cbShoulders.setSelected(true);
/* 229 */         this.cbChest.setSelected(true);
/* 230 */         this.cbHands.setSelected(true);
/* 231 */         this.cbLegs.setSelected(true);
/* 232 */         this.cbFeet.setSelected(true);
/*     */         break;
/*     */       
/*     */       case 5:
/* 236 */         this.cbBelt.setSelected(true);
/* 237 */         this.cbAmulet.setSelected(true);
/* 238 */         this.cbRing.setSelected(true);
/* 239 */         this.cbMedal.setSelected(true);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 261 */     this.cbHead.setSelected(false);
/* 262 */     this.cbShoulders.setSelected(false);
/* 263 */     this.cbChest.setSelected(false);
/* 264 */     this.cbHands.setSelected(false);
/* 265 */     this.cbLegs.setSelected(false);
/* 266 */     this.cbFeet.setSelected(false);
/* 267 */     this.cbBelt.setSelected(false);
/* 268 */     this.cbAmulet.setSelected(false);
/* 269 */     this.cbRing.setSelected(false);
/* 270 */     this.cbMedal.setSelected(false);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 274 */     if (this.cbHead.isSelected()) criteria.itemClass.add("ArmorProtective_Head"); 
/* 275 */     if (this.cbShoulders.isSelected()) criteria.itemClass.add("ArmorProtective_Shoulders"); 
/* 276 */     if (this.cbChest.isSelected()) criteria.itemClass.add("ArmorProtective_Chest"); 
/* 277 */     if (this.cbHands.isSelected()) criteria.itemClass.add("ArmorProtective_Hands"); 
/* 278 */     if (this.cbLegs.isSelected()) criteria.itemClass.add("ArmorProtective_Legs"); 
/* 279 */     if (this.cbFeet.isSelected()) criteria.itemClass.add("ArmorProtective_Feet"); 
/* 280 */     if (this.cbBelt.isSelected()) criteria.itemClass.add("ArmorProtective_Waist"); 
/* 281 */     if (this.cbAmulet.isSelected()) criteria.itemClass.add("ArmorJewelry_Amulet"); 
/* 282 */     if (this.cbRing.isSelected()) criteria.itemClass.add("ArmorJewelry_Ring"); 
/* 283 */     if (this.cbMedal.isSelected()) criteria.itemClass.add("ArmorJewelry_Medal"); 
/*     */   }
/*     */   
/*     */   public void addComponentCriteria(SelectionCriteria criteria) {
/* 287 */     criteria.slotHead = this.cbHead.isSelected();
/* 288 */     criteria.slotShoulders = this.cbShoulders.isSelected();
/* 289 */     criteria.slotChest = this.cbChest.isSelected();
/* 290 */     criteria.slotHands = this.cbHands.isSelected();
/* 291 */     criteria.slotLegs = this.cbLegs.isSelected();
/* 292 */     criteria.slotFeet = this.cbFeet.isSelected();
/* 293 */     criteria.slotBelt = this.cbBelt.isSelected();
/* 294 */     criteria.slotAmulet = this.cbAmulet.isSelected();
/* 295 */     criteria.slotRing = this.cbRing.isSelected();
/* 296 */     criteria.slotMedal = this.cbMedal.isSelected();
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\ArmorTypePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */