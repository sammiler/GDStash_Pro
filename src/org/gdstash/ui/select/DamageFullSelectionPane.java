/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import org.gdstash.db.SelectionCriteria;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.GDTabbedSearchDialog;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DamageFullSelectionPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private WeaponTypePane pnlWeaponTypes;
/*     */   private ArmorTypePane pnlArmorTypes;
/*     */   private MiscTypePane pnlMiscTypes;
/*     */   private ItemRarityPane pnlItemRarity;
/*     */   private StatReqPane pnlStats;
/*     */   private DamageSelectionPane pnlDamage;
/*     */   private TitledBorder brdItem;
/*     */   private GDTabbedSearchDialog.Mode mode;
/*     */   
/*     */   public DamageFullSelectionPane(GDTabbedSearchDialog.Mode mode) {
/*  36 */     this.mode = mode;
/*     */     
/*  38 */     adjustUI();
/*     */     
/*  40 */     JPanel pnlItemTypes = new JPanel();
/*     */     
/*  42 */     GroupLayout layout = null;
/*  43 */     GroupLayout.SequentialGroup hGroup = null;
/*  44 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  46 */     layout = new GroupLayout(pnlItemTypes);
/*  47 */     pnlItemTypes.setLayout(layout);
/*     */     
/*  49 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/*  52 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/*  55 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  58 */     hGroup
/*  59 */       .addGroup(layout.createParallelGroup()
/*  60 */         .addComponent((Component)this.pnlWeaponTypes))
/*  61 */       .addGroup(layout.createParallelGroup()
/*  62 */         .addComponent((Component)this.pnlArmorTypes))
/*  63 */       .addGroup(layout.createParallelGroup()
/*  64 */         .addComponent((Component)this.pnlMiscTypes));
/*  65 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  68 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  71 */     vGroup
/*  72 */       .addGroup(layout.createParallelGroup()
/*  73 */         .addComponent((Component)this.pnlWeaponTypes)
/*  74 */         .addComponent((Component)this.pnlArmorTypes)
/*  75 */         .addComponent((Component)this.pnlMiscTypes));
/*     */     
/*  77 */     layout.setVerticalGroup(vGroup);
/*     */     
/*  79 */     layout.linkSize(0, new Component[] { (Component)this.pnlWeaponTypes, (Component)this.pnlArmorTypes });
/*  80 */     layout.linkSize(0, new Component[] { (Component)this.pnlWeaponTypes, (Component)this.pnlMiscTypes });
/*     */     
/*  82 */     layout.linkSize(1, new Component[] { (Component)this.pnlWeaponTypes, (Component)this.pnlArmorTypes });
/*  83 */     layout.linkSize(1, new Component[] { (Component)this.pnlWeaponTypes, (Component)this.pnlMiscTypes });
/*     */ 
/*     */ 
/*     */     
/*  87 */     JPanel pnlItemInfo = new JPanel();
/*     */     
/*  89 */     layout = new GroupLayout(pnlItemInfo);
/*  90 */     pnlItemInfo.setLayout(layout);
/*     */     
/*  92 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/*  95 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/*  98 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 101 */     hGroup
/* 102 */       .addGroup(layout.createParallelGroup()
/* 103 */         .addComponent(pnlItemTypes)
/* 104 */         .addComponent((Component)this.pnlItemRarity)
/* 105 */         .addComponent((Component)this.pnlStats));
/* 106 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 109 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 112 */     vGroup
/* 113 */       .addGroup(layout.createParallelGroup()
/* 114 */         .addComponent(pnlItemTypes))
/* 115 */       .addGroup(layout.createParallelGroup()
/* 116 */         .addComponent((Component)this.pnlItemRarity))
/* 117 */       .addGroup(layout.createParallelGroup()
/* 118 */         .addComponent((Component)this.pnlStats));
/*     */     
/* 120 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 122 */     layout.linkSize(0, new Component[] { pnlItemTypes, (Component)this.pnlItemRarity });
/* 123 */     layout.linkSize(0, new Component[] { pnlItemTypes, (Component)this.pnlStats });
/*     */     
/* 125 */     pnlItemInfo.setBorder(this.brdItem);
/*     */ 
/*     */ 
/*     */     
/* 129 */     layout = new GroupLayout((Container)this);
/* 130 */     setLayout(layout);
/*     */     
/* 132 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 135 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 138 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 141 */     hGroup
/* 142 */       .addGroup(layout.createParallelGroup()
/* 143 */         .addComponent(pnlItemInfo))
/* 144 */       .addGroup(layout.createParallelGroup()
/* 145 */         .addComponent((Component)this.pnlDamage));
/* 146 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 149 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 152 */     vGroup
/* 153 */       .addGroup(layout.createParallelGroup()
/* 154 */         .addComponent(pnlItemInfo)
/* 155 */         .addComponent((Component)this.pnlDamage));
/* 156 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 158 */     layout.linkSize(1, new Component[] { pnlItemInfo, (Component)this.pnlDamage });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 163 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 164 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 165 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 167 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 168 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 170 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 171 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 172 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */     
/* 174 */     if (this.brdItem == null) {
/* 175 */       this.brdItem = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_ITEM_INFO"));
/*     */     }
/* 177 */     this.brdItem.setTitleFont(fntBorder);
/*     */ 
/*     */ 
/*     */     
/* 181 */     if (this.pnlWeaponTypes == null) {
/* 182 */       this.pnlWeaponTypes = new WeaponTypePane();
/*     */     } else {
/* 184 */       this.pnlWeaponTypes.adjustUI();
/*     */     } 
/*     */     
/* 187 */     if (this.pnlArmorTypes == null) {
/* 188 */       this.pnlArmorTypes = new ArmorTypePane();
/*     */     } else {
/* 190 */       this.pnlArmorTypes.adjustUI();
/*     */     } 
/*     */     
/* 193 */     if (this.pnlMiscTypes == null) {
/* 194 */       this.pnlMiscTypes = new MiscTypePane(this.mode);
/*     */     } else {
/* 196 */       this.pnlMiscTypes.adjustUI();
/*     */     } 
/*     */     
/* 199 */     if (this.pnlItemRarity == null) {
/* 200 */       this.pnlItemRarity = new ItemRarityPane(0);
/*     */     } else {
/* 202 */       this.pnlItemRarity.adjustUI();
/*     */     } 
/*     */     
/* 205 */     if (this.pnlStats == null) {
/* 206 */       this.pnlStats = new StatReqPane();
/*     */     } else {
/* 208 */       this.pnlStats.adjustUI();
/*     */     } 
/*     */     
/* 211 */     if (this.pnlDamage == null) {
/* 212 */       this.pnlDamage = new DamageSelectionPane();
/*     */     } else {
/* 214 */       this.pnlDamage.adjustUI();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clear() {
/* 219 */     this.pnlWeaponTypes.clear();
/* 220 */     this.pnlArmorTypes.clear();
/* 221 */     this.pnlMiscTypes.clear();
/* 222 */     this.pnlItemRarity.clear();
/* 223 */     this.pnlStats.clear();
/* 224 */     this.pnlDamage.clear();
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 228 */     this.pnlWeaponTypes.addCriteria(criteria);
/* 229 */     this.pnlArmorTypes.addCriteria(criteria);
/* 230 */     this.pnlMiscTypes.addCriteria(criteria);
/* 231 */     this.pnlItemRarity.addCriteria(criteria);
/* 232 */     this.pnlStats.addCriteria(criteria);
/* 233 */     this.pnlDamage.addCriteria(criteria);
/*     */     
/* 235 */     criteria.selMode = SelectionCriteria.SelectionMode.ITEM;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\select\DamageFullSelectionPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */