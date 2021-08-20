/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ public class ItemRarityPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JCheckBox cbCommon;
/*     */   private JCheckBox cbMagical;
/*     */   private JCheckBox cbRare;
/*     */   private JCheckBox cbEpic;
/*     */   private JCheckBox cbLegendary;
/*     */   
/*     */   public ItemRarityPane(int direction) {
/*  36 */     adjustUI();
/*     */     
/*  38 */     GroupLayout layout = null;
/*  39 */     GroupLayout.SequentialGroup hGroup = null;
/*  40 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  42 */     layout = new GroupLayout((Container)this);
/*  43 */     setLayout(layout);
/*     */     
/*  45 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  48 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  51 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  54 */     hGroup
/*  55 */       .addGroup(layout.createParallelGroup()
/*  56 */         .addComponent(this.cbCommon)
/*  57 */         .addComponent(this.cbMagical)
/*  58 */         .addComponent(this.cbRare)
/*  59 */         .addComponent(this.cbEpic)
/*  60 */         .addComponent(this.cbLegendary));
/*     */ 
/*     */     
/*  63 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  66 */     vGroup
/*  67 */       .addGroup(layout.createParallelGroup()
/*  68 */         .addComponent(this.cbCommon))
/*  69 */       .addGroup(layout.createParallelGroup()
/*  70 */         .addComponent(this.cbMagical))
/*  71 */       .addGroup(layout.createParallelGroup()
/*  72 */         .addComponent(this.cbRare))
/*  73 */       .addGroup(layout.createParallelGroup()
/*  74 */         .addComponent(this.cbEpic))
/*  75 */       .addGroup(layout.createParallelGroup()
/*  76 */         .addComponent(this.cbLegendary));
/*     */     
/*  78 */     if (direction == 0) {
/*  79 */       layout.setHorizontalGroup(vGroup);
/*  80 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/*  82 */       layout.setHorizontalGroup(hGroup);
/*  83 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/*  86 */     layout.linkSize(0, new Component[] { this.cbCommon, this.cbMagical });
/*  87 */     layout.linkSize(0, new Component[] { this.cbCommon, this.cbRare });
/*  88 */     layout.linkSize(0, new Component[] { this.cbCommon, this.cbEpic });
/*  89 */     layout.linkSize(0, new Component[] { this.cbCommon, this.cbLegendary });
/*     */     
/*  91 */     layout.linkSize(1, new Component[] { this.cbCommon, this.cbMagical });
/*  92 */     layout.linkSize(1, new Component[] { this.cbCommon, this.cbRare });
/*  93 */     layout.linkSize(1, new Component[] { this.cbCommon, this.cbEpic });
/*  94 */     layout.linkSize(1, new Component[] { this.cbCommon, this.cbLegendary });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  99 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 100 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 101 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 102 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 103 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 105 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 106 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 107 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 109 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 110 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 111 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 112 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RARITY_ITEM"));
/* 113 */     text.setTitleFont(fntBorder);
/*     */     
/* 115 */     setBorder(text);
/*     */     
/* 117 */     if (this.cbCommon == null) this.cbCommon = new JCheckBox(); 
/* 118 */     this.cbCommon.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "RARITY_ITEM_COMMON"));
/* 119 */     this.cbCommon.setFont(fntCheck);
/*     */     
/* 121 */     if (this.cbMagical == null) this.cbMagical = new JCheckBox(); 
/* 122 */     this.cbMagical.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "RARITY_ITEM_MAGICAL"));
/* 123 */     this.cbMagical.setFont(fntCheck);
/*     */     
/* 125 */     if (this.cbRare == null) this.cbRare = new JCheckBox(); 
/* 126 */     this.cbRare.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "RARITY_ITEM_RARE"));
/* 127 */     this.cbRare.setFont(fntCheck);
/*     */     
/* 129 */     if (this.cbEpic == null) this.cbEpic = new JCheckBox(); 
/* 130 */     this.cbEpic.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "RARITY_ITEM_EPIC"));
/* 131 */     this.cbEpic.setFont(fntCheck);
/*     */     
/* 133 */     if (this.cbLegendary == null) this.cbLegendary = new JCheckBox(); 
/* 134 */     this.cbLegendary.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "RARITY_ITEM_LEGENDARY"));
/* 135 */     this.cbLegendary.setFont(fntCheck);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 139 */     this.cbCommon.setSelected(false);
/* 140 */     this.cbMagical.setSelected(false);
/* 141 */     this.cbRare.setSelected(false);
/* 142 */     this.cbEpic.setSelected(false);
/* 143 */     this.cbLegendary.setSelected(false);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 147 */     criteria.itemRarity = getRarityList();
/*     */   }
/*     */   
/*     */   public List<String> getRarityList() {
/* 151 */     List<String> list = new LinkedList<>();
/*     */     
/* 153 */     if (this.cbCommon.isSelected()) list.add("Common"); 
/* 154 */     if (this.cbMagical.isSelected()) list.add("Magical"); 
/* 155 */     if (this.cbRare.isSelected()) list.add("Rare"); 
/* 156 */     if (this.cbEpic.isSelected()) list.add("Epic"); 
/* 157 */     if (this.cbLegendary.isSelected()) list.add("Legendary");
/*     */     
/* 159 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\ItemRarityPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */