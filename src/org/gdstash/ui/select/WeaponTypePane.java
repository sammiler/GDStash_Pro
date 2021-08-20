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
/*     */ public class WeaponTypePane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JCheckBox cbAxe1H;
/*     */   private JCheckBox cbAxe2H;
/*     */   private JCheckBox cbMace1H;
/*     */   private JCheckBox cbMace2H;
/*     */   private JCheckBox cbSpear1H;
/*     */   private JCheckBox cbSword1H;
/*     */   private JCheckBox cbSword2H;
/*     */   private JCheckBox cbDagger1H;
/*     */   private JCheckBox cbScepter1H;
/*     */   private JCheckBox cbRanged1H;
/*     */   private JCheckBox cbRanged2H;
/*     */   private JCheckBox cbStaff2H;
/*     */   private JCheckBox cbShield;
/*     */   private JCheckBox cbOffhand;
/*     */   
/*     */   public WeaponTypePane() {
/*  43 */     this(1);
/*     */   }
/*     */   
/*     */   public WeaponTypePane(int direction) {
/*  47 */     adjustUI();
/*     */     
/*  49 */     GroupLayout layout = null;
/*  50 */     GroupLayout.SequentialGroup hGroup = null;
/*  51 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  53 */     layout = new GroupLayout((Container)this);
/*  54 */     setLayout(layout);
/*     */     
/*  56 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  59 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  62 */     hGroup = layout.createSequentialGroup();
/*     */     
/*  64 */     hGroup
/*  65 */       .addGroup(layout.createParallelGroup()
/*  66 */         .addComponent(this.cbAxe1H)
/*  67 */         .addComponent(this.cbDagger1H)
/*  68 */         .addComponent(this.cbMace1H)
/*  69 */         .addComponent(this.cbScepter1H)
/*  70 */         .addComponent(this.cbSpear1H)
/*  71 */         .addComponent(this.cbSword1H)
/*  72 */         .addComponent(this.cbAxe2H)
/*  73 */         .addComponent(this.cbMace2H)
/*  74 */         .addComponent(this.cbSword2H)
/*  75 */         .addComponent(this.cbStaff2H)
/*  76 */         .addComponent(this.cbRanged1H)
/*  77 */         .addComponent(this.cbRanged2H)
/*  78 */         .addComponent(this.cbShield)
/*  79 */         .addComponent(this.cbOffhand));
/*     */     
/*  81 */     vGroup = layout.createSequentialGroup();
/*     */     
/*  83 */     vGroup
/*  84 */       .addGroup(layout.createParallelGroup()
/*  85 */         .addComponent(this.cbAxe1H))
/*  86 */       .addGroup(layout.createParallelGroup()
/*  87 */         .addComponent(this.cbDagger1H))
/*  88 */       .addGroup(layout.createParallelGroup()
/*  89 */         .addComponent(this.cbMace1H))
/*  90 */       .addGroup(layout.createParallelGroup()
/*  91 */         .addComponent(this.cbScepter1H))
/*  92 */       .addGroup(layout.createParallelGroup()
/*  93 */         .addComponent(this.cbSpear1H))
/*  94 */       .addGroup(layout.createParallelGroup()
/*  95 */         .addComponent(this.cbSword1H))
/*  96 */       .addGroup(layout.createParallelGroup()
/*  97 */         .addComponent(this.cbAxe2H))
/*  98 */       .addGroup(layout.createParallelGroup()
/*  99 */         .addComponent(this.cbMace2H))
/* 100 */       .addGroup(layout.createParallelGroup()
/* 101 */         .addComponent(this.cbSword2H))
/* 102 */       .addGroup(layout.createParallelGroup()
/* 103 */         .addComponent(this.cbStaff2H))
/* 104 */       .addGroup(layout.createParallelGroup()
/* 105 */         .addComponent(this.cbRanged1H))
/* 106 */       .addGroup(layout.createParallelGroup()
/* 107 */         .addComponent(this.cbRanged2H))
/* 108 */       .addGroup(layout.createParallelGroup()
/* 109 */         .addComponent(this.cbShield))
/* 110 */       .addGroup(layout.createParallelGroup()
/* 111 */         .addComponent(this.cbOffhand));
/*     */     
/* 113 */     if (direction == 0) {
/* 114 */       layout.setHorizontalGroup(vGroup);
/* 115 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 117 */       layout.setHorizontalGroup(hGroup);
/* 118 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 121 */     layout.linkSize(0, new Component[] { this.cbAxe1H, this.cbDagger1H });
/* 122 */     layout.linkSize(0, new Component[] { this.cbAxe1H, this.cbMace1H });
/* 123 */     layout.linkSize(0, new Component[] { this.cbAxe1H, this.cbScepter1H });
/* 124 */     layout.linkSize(0, new Component[] { this.cbAxe1H, this.cbSpear1H });
/* 125 */     layout.linkSize(0, new Component[] { this.cbAxe1H, this.cbSword1H });
/* 126 */     layout.linkSize(0, new Component[] { this.cbAxe1H, this.cbAxe2H });
/* 127 */     layout.linkSize(0, new Component[] { this.cbAxe1H, this.cbMace2H });
/* 128 */     layout.linkSize(0, new Component[] { this.cbAxe1H, this.cbSword2H });
/* 129 */     layout.linkSize(0, new Component[] { this.cbAxe1H, this.cbRanged1H });
/* 130 */     layout.linkSize(0, new Component[] { this.cbAxe1H, this.cbRanged2H });
/* 131 */     layout.linkSize(0, new Component[] { this.cbAxe1H, this.cbStaff2H });
/* 132 */     layout.linkSize(0, new Component[] { this.cbAxe1H, this.cbShield });
/* 133 */     layout.linkSize(0, new Component[] { this.cbAxe1H, this.cbOffhand });
/*     */     
/* 135 */     layout.linkSize(1, new Component[] { this.cbAxe1H, this.cbDagger1H });
/* 136 */     layout.linkSize(1, new Component[] { this.cbAxe1H, this.cbMace1H });
/* 137 */     layout.linkSize(1, new Component[] { this.cbAxe1H, this.cbScepter1H });
/* 138 */     layout.linkSize(1, new Component[] { this.cbAxe1H, this.cbSpear1H });
/* 139 */     layout.linkSize(1, new Component[] { this.cbAxe1H, this.cbSword1H });
/* 140 */     layout.linkSize(1, new Component[] { this.cbAxe1H, this.cbAxe2H });
/* 141 */     layout.linkSize(1, new Component[] { this.cbAxe1H, this.cbMace2H });
/* 142 */     layout.linkSize(1, new Component[] { this.cbAxe1H, this.cbSword2H });
/* 143 */     layout.linkSize(1, new Component[] { this.cbAxe1H, this.cbRanged1H });
/* 144 */     layout.linkSize(1, new Component[] { this.cbAxe1H, this.cbRanged2H });
/* 145 */     layout.linkSize(1, new Component[] { this.cbAxe1H, this.cbStaff2H });
/* 146 */     layout.linkSize(1, new Component[] { this.cbAxe1H, this.cbShield });
/* 147 */     layout.linkSize(1, new Component[] { this.cbAxe1H, this.cbOffhand });
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
/* 165 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_TYPE_WEAPON"));
/* 166 */     text.setTitleFont(fntBorder);
/*     */     
/* 168 */     setBorder(text);
/*     */     
/* 170 */     if (this.cbAxe1H == null) this.cbAxe1H = new JCheckBox(); 
/* 171 */     this.cbAxe1H.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_WEAPON_AXE"));
/* 172 */     this.cbAxe1H.setFont(fntCheck);
/*     */     
/* 174 */     if (this.cbDagger1H == null) this.cbDagger1H = new JCheckBox(); 
/* 175 */     this.cbDagger1H.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_WEAPON_DAGGER"));
/* 176 */     this.cbDagger1H.setFont(fntCheck);
/*     */     
/* 178 */     if (this.cbMace1H == null) this.cbMace1H = new JCheckBox(); 
/* 179 */     this.cbMace1H.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_WEAPON_MACE"));
/* 180 */     this.cbMace1H.setFont(fntCheck);
/*     */     
/* 182 */     if (this.cbScepter1H == null) this.cbScepter1H = new JCheckBox(); 
/* 183 */     this.cbScepter1H.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_WEAPON_SCEPTER"));
/* 184 */     this.cbScepter1H.setFont(fntCheck);
/*     */     
/* 186 */     if (this.cbSpear1H == null) this.cbSpear1H = new JCheckBox(); 
/* 187 */     this.cbSpear1H.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_WEAPON_SPEAR"));
/* 188 */     this.cbSpear1H.setFont(fntCheck);
/*     */     
/* 190 */     if (this.cbSword1H == null) this.cbSword1H = new JCheckBox(); 
/* 191 */     this.cbSword1H.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_WEAPON_SWORD"));
/* 192 */     this.cbSword1H.setFont(fntCheck);
/*     */     
/* 194 */     if (this.cbAxe2H == null) this.cbAxe2H = new JCheckBox(); 
/* 195 */     this.cbAxe2H.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_WEAPON_AXE_2H"));
/* 196 */     this.cbAxe2H.setFont(fntCheck);
/*     */     
/* 198 */     if (this.cbMace2H == null) this.cbMace2H = new JCheckBox(); 
/* 199 */     this.cbMace2H.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_WEAPON_MACE_2H"));
/* 200 */     this.cbMace2H.setFont(fntCheck);
/*     */     
/* 202 */     if (this.cbStaff2H == null) this.cbStaff2H = new JCheckBox(); 
/* 203 */     this.cbStaff2H.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_WEAPON_STAFF_2H"));
/* 204 */     this.cbStaff2H.setFont(fntCheck);
/*     */     
/* 206 */     if (this.cbSword2H == null) this.cbSword2H = new JCheckBox(); 
/* 207 */     this.cbSword2H.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_WEAPON_SWORD_2H"));
/* 208 */     this.cbSword2H.setFont(fntCheck);
/*     */     
/* 210 */     if (this.cbRanged1H == null) this.cbRanged1H = new JCheckBox(); 
/* 211 */     this.cbRanged1H.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_WEAPON_RANGED"));
/* 212 */     this.cbRanged1H.setFont(fntCheck);
/*     */     
/* 214 */     if (this.cbRanged2H == null) this.cbRanged2H = new JCheckBox(); 
/* 215 */     this.cbRanged2H.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_WEAPON_RANGED_2H"));
/* 216 */     this.cbRanged2H.setFont(fntCheck);
/*     */     
/* 218 */     if (this.cbShield == null) this.cbShield = new JCheckBox(); 
/* 219 */     this.cbShield.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_WEAPON_SHIELD"));
/* 220 */     this.cbShield.setFont(fntCheck);
/*     */     
/* 222 */     if (this.cbOffhand == null) this.cbOffhand = new JCheckBox(); 
/* 223 */     this.cbOffhand.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_WEAPON_OFFHAND"));
/* 224 */     this.cbOffhand.setFont(fntCheck);
/*     */   }
/*     */   
/*     */   public void setSelection(int sel) {
/* 228 */     clear();
/*     */     
/* 230 */     switch (sel) {
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 235 */         this.cbAxe1H.setSelected(true);
/* 236 */         this.cbAxe2H.setSelected(true);
/* 237 */         this.cbMace1H.setSelected(true);
/* 238 */         this.cbMace2H.setSelected(true);
/* 239 */         this.cbSpear1H.setSelected(true);
/* 240 */         this.cbSword1H.setSelected(true);
/* 241 */         this.cbSword2H.setSelected(true);
/* 242 */         this.cbDagger1H.setSelected(true);
/* 243 */         this.cbScepter1H.setSelected(true);
/* 244 */         this.cbStaff2H.setSelected(true);
/* 245 */         this.cbRanged1H.setSelected(true);
/* 246 */         this.cbRanged2H.setSelected(true);
/* 247 */         this.cbShield.setSelected(true);
/* 248 */         this.cbOffhand.setSelected(true);
/*     */         break;
/*     */       
/*     */       case 2:
/* 252 */         this.cbAxe1H.setSelected(true);
/* 253 */         this.cbAxe2H.setSelected(true);
/* 254 */         this.cbMace1H.setSelected(true);
/* 255 */         this.cbMace2H.setSelected(true);
/* 256 */         this.cbSpear1H.setSelected(true);
/* 257 */         this.cbSword1H.setSelected(true);
/* 258 */         this.cbSword2H.setSelected(true);
/* 259 */         this.cbDagger1H.setSelected(true);
/* 260 */         this.cbScepter1H.setSelected(true);
/* 261 */         this.cbStaff2H.setSelected(true);
/* 262 */         this.cbRanged1H.setSelected(true);
/* 263 */         this.cbRanged2H.setSelected(true);
/* 264 */         this.cbShield.setSelected(true);
/* 265 */         this.cbOffhand.setSelected(true);
/*     */         break;
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
/*     */       case 6:
/* 278 */         this.cbAxe1H.setSelected(true);
/* 279 */         this.cbAxe2H.setSelected(true);
/* 280 */         this.cbMace1H.setSelected(true);
/* 281 */         this.cbMace2H.setSelected(true);
/* 282 */         this.cbSpear1H.setSelected(true);
/* 283 */         this.cbSword1H.setSelected(true);
/* 284 */         this.cbSword2H.setSelected(true);
/*     */         break;
/*     */       
/*     */       case 7:
/* 288 */         this.cbAxe1H.setSelected(true);
/* 289 */         this.cbMace1H.setSelected(true);
/* 290 */         this.cbSpear1H.setSelected(true);
/* 291 */         this.cbSword1H.setSelected(true);
/*     */         break;
/*     */       
/*     */       case 8:
/* 295 */         this.cbAxe2H.setSelected(true);
/* 296 */         this.cbMace2H.setSelected(true);
/* 297 */         this.cbSword2H.setSelected(true);
/*     */         break;
/*     */       
/*     */       case 9:
/* 301 */         this.cbDagger1H.setSelected(true);
/* 302 */         this.cbScepter1H.setSelected(true);
/* 303 */         this.cbStaff2H.setSelected(true);
/*     */         break;
/*     */       
/*     */       case 10:
/* 307 */         this.cbRanged1H.setSelected(true);
/* 308 */         this.cbRanged2H.setSelected(true);
/* 309 */         this.cbStaff2H.setSelected(true);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 316 */     this.cbAxe1H.setSelected(false);
/* 317 */     this.cbAxe2H.setSelected(false);
/* 318 */     this.cbMace1H.setSelected(false);
/* 319 */     this.cbMace2H.setSelected(false);
/* 320 */     this.cbSpear1H.setSelected(false);
/* 321 */     this.cbSword1H.setSelected(false);
/* 322 */     this.cbSword2H.setSelected(false);
/* 323 */     this.cbDagger1H.setSelected(false);
/* 324 */     this.cbScepter1H.setSelected(false);
/* 325 */     this.cbStaff2H.setSelected(false);
/* 326 */     this.cbRanged1H.setSelected(false);
/* 327 */     this.cbRanged2H.setSelected(false);
/* 328 */     this.cbShield.setSelected(false);
/* 329 */     this.cbOffhand.setSelected(false);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 333 */     if (this.cbAxe1H.isSelected()) criteria.itemClass.add("WeaponMelee_Axe"); 
/* 334 */     if (this.cbAxe2H.isSelected()) criteria.itemClass.add("WeaponMelee_Axe2h"); 
/* 335 */     if (this.cbMace1H.isSelected()) criteria.itemClass.add("WeaponMelee_Mace"); 
/* 336 */     if (this.cbMace2H.isSelected()) criteria.itemClass.add("WeaponMelee_Mace2h"); 
/* 337 */     if (this.cbSpear1H.isSelected()) criteria.itemClass.add("WeaponHunting_Spear"); 
/* 338 */     if (this.cbSword1H.isSelected()) criteria.itemClass.add("WeaponMelee_Sword"); 
/* 339 */     if (this.cbSword2H.isSelected()) criteria.itemClass.add("WeaponMelee_Sword2h"); 
/* 340 */     if (this.cbDagger1H.isSelected()) criteria.itemClass.add("WeaponMelee_Dagger"); 
/* 341 */     if (this.cbScepter1H.isSelected()) criteria.itemClass.add("WeaponMelee_Scepter"); 
/* 342 */     if (this.cbStaff2H.isSelected()) criteria.itemClass.add("WeaponMagical_Staff"); 
/* 343 */     if (this.cbRanged1H.isSelected()) criteria.itemClass.add("WeaponHunting_Ranged1h"); 
/* 344 */     if (this.cbRanged2H.isSelected()) criteria.itemClass.add("WeaponHunting_Ranged2h"); 
/* 345 */     if (this.cbShield.isSelected()) criteria.itemClass.add("WeaponArmor_Shield"); 
/* 346 */     if (this.cbOffhand.isSelected()) criteria.itemClass.add("WeaponArmor_Offhand"); 
/*     */   }
/*     */   
/*     */   public void addComponentCriteria(SelectionCriteria criteria) {
/* 350 */     criteria.slotAxe1H = this.cbAxe1H.isSelected();
/* 351 */     criteria.slotAxe2H = this.cbAxe2H.isSelected();
/* 352 */     criteria.slotMace1H = this.cbMace1H.isSelected();
/* 353 */     criteria.slotMace2H = this.cbMace2H.isSelected();
/* 354 */     criteria.slotSpear1H = this.cbSpear1H.isSelected();
/* 355 */     criteria.slotSword1H = this.cbSword1H.isSelected();
/* 356 */     criteria.slotSword2H = this.cbSword2H.isSelected();
/* 357 */     criteria.slotDagger1H = this.cbDagger1H.isSelected();
/* 358 */     criteria.slotScepter1H = this.cbScepter1H.isSelected();
/* 359 */     criteria.slotStaff2H = this.cbStaff2H.isSelected();
/* 360 */     criteria.slotRanged1H = this.cbRanged1H.isSelected();
/* 361 */     criteria.slotRanged2H = this.cbRanged2H.isSelected();
/* 362 */     criteria.slotShield = this.cbShield.isSelected();
/* 363 */     criteria.slotOffhand = this.cbOffhand.isSelected();
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\WeaponTypePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */