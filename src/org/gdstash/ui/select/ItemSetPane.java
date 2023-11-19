/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.util.List;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import org.gdstash.db.DBItemSet;
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
/*     */ public class ItemSetPane
/*     */   extends AdjustablePanel
/*     */ {
/*  29 */   private static List<DBItemSet> sets = null;
/*  30 */   private static DBItemSet[] itemSets = null;
/*     */   
/*     */   static {
/*  33 */     loadSets();
/*     */   }
/*     */   private JComboBox<DBItemSet> cbSets;
/*     */   public static void loadSets() {
/*  37 */     if (GDStashFrame.dbConfig == null || !GDStashFrame.dbConfig.gddbInit) {
/*     */       
/*  39 */       itemSets = new DBItemSet[1];
/*  40 */       itemSets[0] = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  45 */     sets = DBItemSet.getAll();
/*     */     
/*  47 */     if (sets == null) {
/*  48 */       itemSets = new DBItemSet[1];
/*  49 */       itemSets[0] = null;
/*     */     } else {
/*  51 */       itemSets = new DBItemSet[sets.size() + 2];
/*  52 */       itemSets[0] = null;
/*  53 */       itemSets[1] = new DBItemSet(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_ITEMSET_ALL"));
/*     */       
/*  55 */       int pos = 2;
/*  56 */       for (DBItemSet set : sets) {
/*  57 */         itemSets[pos] = set;
/*     */         
/*  59 */         pos++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemSetPane() {
/*  67 */     adjustUI();
/*     */     
/*  69 */     GroupLayout layout = null;
/*  70 */     GroupLayout.SequentialGroup hGroup = null;
/*  71 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  73 */     layout = new GroupLayout((Container)this);
/*  74 */     setLayout(layout);
/*     */     
/*  76 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/*  79 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/*  82 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  85 */     hGroup
/*  86 */       .addGroup(layout.createParallelGroup()
/*  87 */         .addComponent(this.cbSets));
/*  88 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  91 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  94 */     vGroup
/*  95 */       .addGroup(layout.createParallelGroup()
/*  96 */         .addComponent(this.cbSets));
/*  97 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 102 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 103 */     Font fntCombo = UIManager.getDefaults().getFont("ComboBox.font");
/* 104 */     if (fntCombo == null) fntCombo = fntLabel; 
/* 105 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 106 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 108 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 109 */     fntCombo = fntCombo.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 110 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 112 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 113 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 114 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 115 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SETS"));
/* 116 */     text.setTitleFont(fntBorder);
/*     */     
/* 118 */     setBorder(text);
/*     */     
/* 120 */     if (this.cbSets == null) {
/* 121 */       this.cbSets = new JComboBox<>(itemSets);
/*     */     }
/* 123 */     this.cbSets.setFont(fntCombo);
/*     */   }
/*     */   
/*     */   public void updateItemSets() {
/* 127 */     DefaultComboBoxModel<DBItemSet> model = new DefaultComboBoxModel<>(itemSets);
/*     */     
/* 129 */     this.cbSets.setModel(model);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 133 */     this.cbSets.getModel().setSelectedItem(null);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 137 */     criteria.setItem = false;
/*     */     
/* 139 */     int index = this.cbSets.getSelectedIndex();
/*     */     
/* 141 */     if (index == 1) {
/* 142 */       criteria.setItem = true;
/*     */     }
/*     */     
/* 145 */     if (index > 1) {
/* 146 */       DBItemSet set = (DBItemSet)this.cbSets.getSelectedItem();
/*     */       
/* 148 */       if (set != null) {
/* 149 */         criteria.itemIDs = set.getItemIDList();
/*     */       } else {
/* 151 */         criteria.itemIDs = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\select\ItemSetPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */