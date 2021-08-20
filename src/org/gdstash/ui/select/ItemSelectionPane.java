/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JComboBox;
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
/*     */ 
/*     */ public class ItemSelectionPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private ItemTypePane pnlItemTypes;
/*     */   private ItemSetPane pnlItemSet;
/*     */   private ArmorClassPane pnlArmorClasses;
/*     */   private JComboBox cbSets;
/*     */   private ItemRarityPane pnlItemRarity;
/*     */   private ArtifactRarityPane pnlArtifactRarity;
/*     */   private LevelSelPane pnlLevel;
/*     */   private GDTabbedSearchDialog.Mode mode;
/*     */   
/*     */   public ItemSelectionPane(GDTabbedSearchDialog.Mode mode) {
/*  38 */     this.mode = mode;
/*     */     
/*  40 */     adjustUI();
/*     */     
/*  42 */     GroupLayout layout = null;
/*  43 */     GroupLayout.SequentialGroup hGroup = null;
/*  44 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  46 */     JPanel pnlRarity = new JPanel();
/*     */     
/*  48 */     layout = new GroupLayout(pnlRarity);
/*  49 */     pnlRarity.setLayout(layout);
/*     */     
/*  51 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/*  54 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/*  57 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  60 */     hGroup
/*  61 */       .addGroup(layout.createParallelGroup()
/*  62 */         .addComponent((Component)this.pnlItemSet)
/*  63 */         .addComponent((Component)this.pnlItemRarity)
/*  64 */         .addComponent((Component)this.pnlArtifactRarity)
/*  65 */         .addComponent((Component)this.pnlArmorClasses)
/*  66 */         .addComponent((Component)this.pnlLevel));
/*  67 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  70 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  73 */     vGroup
/*  74 */       .addGroup(layout.createParallelGroup()
/*  75 */         .addComponent((Component)this.pnlItemSet))
/*  76 */       .addGroup(layout.createParallelGroup()
/*  77 */         .addComponent((Component)this.pnlItemRarity))
/*  78 */       .addGroup(layout.createParallelGroup()
/*  79 */         .addComponent((Component)this.pnlArtifactRarity))
/*  80 */       .addGroup(layout.createParallelGroup()
/*  81 */         .addComponent((Component)this.pnlArmorClasses))
/*  82 */       .addGroup(layout.createParallelGroup()
/*  83 */         .addComponent((Component)this.pnlLevel));
/*     */     
/*  85 */     layout.setVerticalGroup(vGroup);
/*     */     
/*  87 */     layout.linkSize(0, new Component[] { (Component)this.pnlItemSet, (Component)this.pnlItemRarity });
/*  88 */     layout.linkSize(0, new Component[] { (Component)this.pnlItemSet, (Component)this.pnlArtifactRarity });
/*  89 */     layout.linkSize(0, new Component[] { (Component)this.pnlItemSet, (Component)this.pnlArmorClasses });
/*  90 */     layout.linkSize(0, new Component[] { (Component)this.pnlItemSet, (Component)this.pnlLevel });
/*     */     
/*  92 */     layout.linkSize(1, new Component[] { (Component)this.pnlItemSet, (Component)this.pnlItemTypes });
/*  93 */     layout.linkSize(1, new Component[] { (Component)this.pnlItemSet, (Component)this.pnlArtifactRarity });
/*  94 */     layout.linkSize(1, new Component[] { (Component)this.pnlItemSet, (Component)this.pnlArmorClasses });
/*  95 */     layout.linkSize(1, new Component[] { (Component)this.pnlItemSet, (Component)this.pnlLevel });
/*     */ 
/*     */ 
/*     */     
/*  99 */     layout = new GroupLayout((Container)this);
/* 100 */     setLayout(layout);
/*     */     
/* 102 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 105 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 108 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 111 */     hGroup
/* 112 */       .addGroup(layout.createParallelGroup()
/* 113 */         .addComponent((Component)this.pnlItemTypes)
/* 114 */         .addComponent(pnlRarity));
/* 115 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 118 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 121 */     vGroup
/* 122 */       .addGroup(layout.createParallelGroup()
/* 123 */         .addComponent((Component)this.pnlItemTypes))
/* 124 */       .addGroup(layout.createParallelGroup()
/* 125 */         .addComponent(pnlRarity));
/* 126 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 128 */     layout.linkSize(0, new Component[] { pnlRarity, (Component)this.pnlItemTypes });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 133 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 134 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 135 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 137 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 138 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 140 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 141 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 142 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 143 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_ITEM_INFO"));
/* 144 */     text.setTitleFont(fntBorder);
/*     */     
/* 146 */     setBorder(text);
/*     */     
/* 148 */     if (this.pnlItemTypes == null) {
/* 149 */       this.pnlItemTypes = new ItemTypePane(this.mode);
/*     */     } else {
/* 151 */       this.pnlItemTypes.adjustUI();
/*     */     } 
/*     */     
/* 154 */     if (this.pnlItemSet == null) {
/* 155 */       this.pnlItemSet = new ItemSetPane();
/*     */     } else {
/* 157 */       this.pnlItemSet.adjustUI();
/*     */     } 
/*     */     
/* 160 */     if (this.pnlItemRarity == null) {
/* 161 */       this.pnlItemRarity = new ItemRarityPane(0);
/*     */     } else {
/* 163 */       this.pnlItemRarity.adjustUI();
/*     */     } 
/*     */     
/* 166 */     if (this.pnlArtifactRarity == null) {
/* 167 */       this.pnlArtifactRarity = new ArtifactRarityPane(0);
/*     */     } else {
/* 169 */       this.pnlArtifactRarity.adjustUI();
/*     */     } 
/*     */     
/* 172 */     if (this.pnlArmorClasses == null) {
/* 173 */       this.pnlArmorClasses = new ArmorClassPane(0);
/*     */     } else {
/* 175 */       this.pnlArmorClasses.adjustUI();
/*     */     } 
/*     */     
/* 178 */     if (this.pnlLevel == null) {
/* 179 */       this.pnlLevel = new LevelSelPane(0);
/*     */     } else {
/* 181 */       this.pnlLevel.adjustUI();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateConfig() {
/* 186 */     if (this.pnlItemSet != null) this.pnlItemSet.updateItemSets(); 
/*     */   }
/*     */   
/*     */   public void clear() {
/* 190 */     this.pnlItemTypes.clear();
/* 191 */     this.pnlItemSet.clear();
/* 192 */     this.pnlArmorClasses.clear();
/* 193 */     this.pnlItemRarity.clear();
/* 194 */     this.pnlArtifactRarity.clear();
/* 195 */     this.pnlLevel.clear();
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 199 */     this.pnlItemTypes.addCriteria(criteria);
/* 200 */     this.pnlItemSet.addCriteria(criteria);
/* 201 */     this.pnlArmorClasses.addCriteria(criteria);
/* 202 */     this.pnlItemRarity.addCriteria(criteria);
/* 203 */     this.pnlArtifactRarity.addCriteria(criteria);
/* 204 */     this.pnlLevel.addCriteria(criteria);
/*     */     
/* 206 */     criteria.selMode = SelectionCriteria.SelectionMode.ITEM;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\ItemSelectionPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */