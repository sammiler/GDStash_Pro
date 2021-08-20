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
/*     */ import org.gdstash.ui.GDTabbedSearchDialog;
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
/*     */ public class MiscTypePane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JCheckBox cbComponent;
/*     */   private JCheckBox cbEnchantment;
/*     */   private JCheckBox cbArtifact;
/*     */   private JCheckBox cbFormula;
/*     */   private JCheckBox cbPotion;
/*     */   private JCheckBox cbScroll;
/*     */   private JCheckBox cbFaction;
/*     */   private JCheckBox cbNote;
/*     */   private JCheckBox cbQuest;
/*     */   private JCheckBox cbUseableSkill;
/*     */   private GDTabbedSearchDialog.Mode mode;
/*     */   
/*     */   public MiscTypePane(GDTabbedSearchDialog.Mode mode) {
/*  41 */     this(mode, 1);
/*     */   }
/*     */   
/*     */   public MiscTypePane(GDTabbedSearchDialog.Mode mode, int direction) {
/*  45 */     this.mode = mode;
/*     */     
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
/*  64 */     GroupLayout.ParallelGroup pg = layout.createParallelGroup();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     pg = pg.addComponent(this.cbComponent).addComponent(this.cbEnchantment).addComponent(this.cbArtifact).addComponent(this.cbFormula).addComponent(this.cbPotion).addComponent(this.cbScroll).addComponent(this.cbFaction).addComponent(this.cbNote).addComponent(this.cbQuest);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     hGroup.addGroup(pg);
/*     */ 
/*     */     
/*  83 */     vGroup = layout.createSequentialGroup();
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
/*     */     
/* 102 */     vGroup = vGroup.addGroup(layout.createParallelGroup().addComponent(this.cbComponent)).addGroup(layout.createParallelGroup().addComponent(this.cbEnchantment)).addGroup(layout.createParallelGroup().addComponent(this.cbArtifact)).addGroup(layout.createParallelGroup().addComponent(this.cbFormula)).addGroup(layout.createParallelGroup().addComponent(this.cbPotion)).addGroup(layout.createParallelGroup().addComponent(this.cbScroll)).addGroup(layout.createParallelGroup().addComponent(this.cbFaction)).addGroup(layout.createParallelGroup().addComponent(this.cbNote)).addGroup(layout.createParallelGroup()
/* 103 */         .addComponent(this.cbQuest));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     if (direction == 0) {
/* 112 */       layout.setHorizontalGroup(vGroup);
/* 113 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 115 */       layout.setHorizontalGroup(hGroup);
/* 116 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 119 */     layout.linkSize(0, new Component[] { this.cbComponent, this.cbEnchantment });
/* 120 */     layout.linkSize(0, new Component[] { this.cbComponent, this.cbArtifact });
/* 121 */     layout.linkSize(0, new Component[] { this.cbComponent, this.cbFormula });
/* 122 */     layout.linkSize(0, new Component[] { this.cbComponent, this.cbPotion });
/* 123 */     layout.linkSize(0, new Component[] { this.cbComponent, this.cbScroll });
/* 124 */     layout.linkSize(0, new Component[] { this.cbComponent, this.cbFaction });
/* 125 */     layout.linkSize(0, new Component[] { this.cbComponent, this.cbNote });
/* 126 */     layout.linkSize(0, new Component[] { this.cbComponent, this.cbQuest });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     layout.linkSize(1, new Component[] { this.cbComponent, this.cbEnchantment });
/* 132 */     layout.linkSize(1, new Component[] { this.cbComponent, this.cbArtifact });
/* 133 */     layout.linkSize(1, new Component[] { this.cbComponent, this.cbFormula });
/* 134 */     layout.linkSize(1, new Component[] { this.cbComponent, this.cbPotion });
/* 135 */     layout.linkSize(1, new Component[] { this.cbComponent, this.cbScroll });
/* 136 */     layout.linkSize(1, new Component[] { this.cbComponent, this.cbFaction });
/* 137 */     layout.linkSize(1, new Component[] { this.cbComponent, this.cbNote });
/* 138 */     layout.linkSize(1, new Component[] { this.cbComponent, this.cbQuest });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 146 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 147 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 148 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 149 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 150 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 152 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 153 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 154 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 156 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 157 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 158 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 159 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_TYPE_ITEM_MISC"));
/* 160 */     text.setTitleFont(fntBorder);
/*     */     
/* 162 */     setBorder(text);
/*     */     
/* 164 */     if (this.cbComponent == null) this.cbComponent = new JCheckBox(); 
/* 165 */     this.cbComponent.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ITEM_COMPONENT"));
/* 166 */     this.cbComponent.setFont(fntCheck);
/*     */     
/* 168 */     if (this.cbEnchantment == null) this.cbEnchantment = new JCheckBox(); 
/* 169 */     this.cbEnchantment.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ITEM_ENCHANTMENT"));
/* 170 */     this.cbEnchantment.setFont(fntCheck);
/*     */     
/* 172 */     if (this.cbArtifact == null) this.cbArtifact = new JCheckBox(); 
/* 173 */     this.cbArtifact.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ITEM_ARTIFACT"));
/* 174 */     this.cbArtifact.setFont(fntCheck);
/*     */     
/* 176 */     if (this.cbFormula == null) this.cbFormula = new JCheckBox(); 
/* 177 */     this.cbFormula.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ITEM_BLUEPRINT"));
/* 178 */     this.cbFormula.setFont(fntCheck);
/*     */     
/* 180 */     if (this.cbPotion == null) this.cbPotion = new JCheckBox(); 
/* 181 */     this.cbPotion.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ITEM_POTION"));
/* 182 */     this.cbPotion.setFont(fntCheck);
/*     */     
/* 184 */     if (this.cbScroll == null) this.cbScroll = new JCheckBox(); 
/* 185 */     this.cbScroll.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ITEM_SCROLL"));
/* 186 */     this.cbScroll.setFont(fntCheck);
/*     */     
/* 188 */     if (this.cbFaction == null) this.cbFaction = new JCheckBox(); 
/* 189 */     this.cbFaction.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ITEM_FACTION"));
/* 190 */     this.cbFaction.setFont(fntCheck);
/*     */     
/* 192 */     if (this.cbNote == null) this.cbNote = new JCheckBox(); 
/* 193 */     this.cbNote.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ITEM_NOTE"));
/* 194 */     this.cbNote.setFont(fntCheck);
/*     */     
/* 196 */     if (this.cbQuest == null) this.cbQuest = new JCheckBox(); 
/* 197 */     this.cbQuest.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ITEM_QUEST"));
/* 198 */     this.cbQuest.setFont(fntCheck);
/*     */     
/* 200 */     if (this.cbUseableSkill == null) this.cbUseableSkill = new JCheckBox(); 
/* 201 */     this.cbUseableSkill.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TYPE_ITEM_USEABLE_SKILL"));
/* 202 */     this.cbUseableSkill.setFont(fntCheck);
/*     */   }
/*     */   
/*     */   public void setSelection(int sel) {
/* 206 */     clear();
/*     */     
/* 208 */     switch (sel) {
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 213 */         this.cbComponent.setSelected(true);
/* 214 */         this.cbEnchantment.setSelected(true);
/* 215 */         this.cbArtifact.setSelected(true);
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
/* 248 */     this.cbComponent.setSelected(false);
/* 249 */     this.cbEnchantment.setSelected(false);
/* 250 */     this.cbArtifact.setSelected(false);
/* 251 */     this.cbFormula.setSelected(false);
/* 252 */     this.cbPotion.setSelected(false);
/* 253 */     this.cbScroll.setSelected(false);
/* 254 */     this.cbFaction.setSelected(false);
/* 255 */     this.cbNote.setSelected(false);
/* 256 */     this.cbQuest.setSelected(false);
/* 257 */     this.cbUseableSkill.setSelected(false);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 261 */     if (this.cbComponent.isSelected()) criteria.itemClass.add("ItemRelic"); 
/* 262 */     if (this.cbEnchantment.isSelected()) criteria.itemClass.add("ItemEnchantment"); 
/* 263 */     if (this.cbArtifact.isSelected()) criteria.itemClass.add("ItemArtifact"); 
/* 264 */     if (this.cbFormula.isSelected()) criteria.itemClass.add("ItemArtifactFormula"); 
/* 265 */     if (this.cbPotion.isSelected()) {
/* 266 */       criteria.itemClass.add("OneShot_PotionHealth");
/* 267 */       criteria.itemClass.add("OneShot_PotionMana");
/* 268 */       criteria.itemClass.add("ItemDevotionReset");
/* 269 */       criteria.itemClass.add("ItemAttributeReset");
/* 270 */       if (this.mode == GDTabbedSearchDialog.Mode.TRANSFER) {
/* 271 */         criteria.itemClass.add("Kickstarter");
/*     */       }
/*     */     } 
/* 274 */     if (this.cbScroll.isSelected()) {
/* 275 */       criteria.itemClass.add("OneShot_Scroll");
/* 276 */       criteria.itemClass.add("OneShot_Potion");
/* 277 */       criteria.itemClass.add("ItemUsableSkill");
/*     */     } 
/* 279 */     if (this.cbFaction.isSelected()) {
/* 280 */       criteria.itemClass.add("ItemFactionBooster");
/* 281 */       criteria.itemClass.add("ItemFactionWarrant");
/* 282 */       criteria.itemClass.add("ItemDifficultyUnlock");
/*     */     } 
/* 284 */     if (this.cbNote.isSelected()) criteria.itemClass.add("ItemNote"); 
/* 285 */     if (this.cbQuest.isSelected()) criteria.itemClass.add("QuestItem"); 
/* 286 */     if (this.cbUseableSkill.isSelected()) {
/* 287 */       criteria.itemClass.add("Kickstarter");
/* 288 */       criteria.itemClass.add("OneShot_EndlessDungeon");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\MiscTypePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */