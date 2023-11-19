/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.util.List;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import org.gdstash.db.DBAffix;
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
/*     */ public class AffixPane
/*     */   extends AdjustablePanel
/*     */ {
/*  30 */   private static List<DBAffix> listPrefix = null;
/*  31 */   private static DBAffix[] arrPrefix = null;
/*  32 */   private static List<DBAffix> listSuffix = null;
/*  33 */   private static DBAffix[] arrSuffix = null; private JLabel lblPrefix;
/*     */   private JComboBox<DBAffix> cbPrefix;
/*     */   
/*     */   static {
/*  37 */     loadAffixes();
/*     */   }
/*     */   private JLabel lblSuffix; private JComboBox<DBAffix> cbSuffix;
/*     */   public static void loadAffixes() {
/*  41 */     if (GDStashFrame.dbConfig == null || !GDStashFrame.dbConfig.gddbInit) {
/*     */       
/*  43 */       arrPrefix = new DBAffix[1];
/*  44 */       arrPrefix[0] = null;
/*     */       
/*  46 */       arrSuffix = new DBAffix[1];
/*  47 */       arrSuffix[0] = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  52 */     listPrefix = DBAffix.getPrefixList();
/*     */     
/*  54 */     if (listPrefix == null) {
/*  55 */       arrPrefix = new DBAffix[1];
/*  56 */       arrPrefix[0] = null;
/*     */     } else {
/*  58 */       arrPrefix = new DBAffix[listPrefix.size() + 1];
/*  59 */       arrPrefix[0] = null;
/*     */       
/*  61 */       int pos = 1;
/*     */       
/*  63 */       for (DBAffix affix : listPrefix) {
/*  64 */         arrPrefix[pos] = affix;
/*     */         
/*  66 */         pos++;
/*     */       } 
/*     */     } 
/*     */     
/*  70 */     listSuffix = DBAffix.getSuffixList();
/*     */     
/*  72 */     if (listSuffix == null) {
/*  73 */       arrSuffix = new DBAffix[1];
/*  74 */       arrSuffix[0] = null;
/*     */     } else {
/*  76 */       arrSuffix = new DBAffix[listSuffix.size() + 1];
/*  77 */       arrSuffix[0] = null;
/*     */       
/*  79 */       int pos = 1;
/*     */       
/*  81 */       for (DBAffix affix : listSuffix) {
/*  82 */         arrSuffix[pos] = affix;
/*     */         
/*  84 */         pos++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AffixPane() {
/*  95 */     adjustUI();
/*     */     
/*  97 */     GroupLayout layout = null;
/*  98 */     GroupLayout.SequentialGroup hGroup = null;
/*  99 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 101 */     layout = new GroupLayout((Container)this);
/* 102 */     setLayout(layout);
/*     */     
/* 104 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 107 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 110 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 113 */     hGroup
/* 114 */       .addGroup(layout.createParallelGroup()
/* 115 */         .addComponent(this.lblPrefix)
/* 116 */         .addComponent(this.lblSuffix))
/* 117 */       .addGroup(layout.createParallelGroup()
/* 118 */         .addComponent(this.cbPrefix)
/* 119 */         .addComponent(this.cbSuffix));
/* 120 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 123 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 126 */     vGroup
/* 127 */       .addGroup(layout.createParallelGroup()
/* 128 */         .addComponent(this.lblPrefix)
/* 129 */         .addComponent(this.cbPrefix))
/* 130 */       .addGroup(layout.createParallelGroup()
/* 131 */         .addComponent(this.lblSuffix)
/* 132 */         .addComponent(this.cbSuffix));
/* 133 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 135 */     layout.linkSize(0, new Component[] { this.lblPrefix, this.lblSuffix });
/*     */     
/* 137 */     layout.linkSize(0, new Component[] { this.cbPrefix, this.cbSuffix });
/*     */     
/* 139 */     layout.linkSize(1, new Component[] { this.lblPrefix, this.cbPrefix });
/* 140 */     layout.linkSize(1, new Component[] { this.lblPrefix, this.lblSuffix });
/* 141 */     layout.linkSize(1, new Component[] { this.lblPrefix, this.cbSuffix });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 146 */     int size = 12;
/* 147 */     if (GDStashFrame.iniConfig != null) size = GDStashFrame.iniConfig.sectUI.fontSize; 
/* 148 */     Dimension dimMax = new Dimension(50 * size, 2 * size);
/*     */     
/* 150 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 151 */     Font fntCombo = UIManager.getDefaults().getFont("ComboBox.font");
/* 152 */     if (fntCombo == null) fntCombo = fntLabel; 
/* 153 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 154 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 156 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 157 */     fntCombo = fntCombo.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 159 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 160 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 161 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */     
/* 163 */     setBorder(compound);
/*     */     
/* 165 */     if (this.lblPrefix == null) this.lblPrefix = new JLabel(); 
/* 166 */     this.lblPrefix.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_PREFIX"));
/* 167 */     this.lblPrefix.setFont(fntLabel);
/*     */     
/* 169 */     if (this.cbPrefix == null) {
/* 170 */       this.cbPrefix = new JComboBox<>(arrPrefix);
/*     */     }
/* 172 */     this.cbPrefix.setFont(fntCombo);
/* 173 */     this.cbPrefix.setPreferredSize(dimMax);
/* 174 */     this.cbPrefix.setMaximumSize(dimMax);
/*     */     
/* 176 */     if (this.lblSuffix == null) this.lblSuffix = new JLabel(); 
/* 177 */     this.lblSuffix.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_SUFFIX"));
/* 178 */     this.lblSuffix.setFont(fntLabel);
/*     */     
/* 180 */     if (this.cbSuffix == null) {
/* 181 */       this.cbSuffix = new JComboBox<>(arrSuffix);
/*     */     }
/* 183 */     this.cbSuffix.setFont(fntCombo);
/* 184 */     this.cbSuffix.setPreferredSize(dimMax);
/* 185 */     this.cbSuffix.setMaximumSize(dimMax);
/*     */   }
/*     */   
/*     */   public void updateAffixes() {
/* 189 */     DefaultComboBoxModel<DBAffix> modelPrefix = new DefaultComboBoxModel<>(arrPrefix);
/* 190 */     DefaultComboBoxModel<DBAffix> modelSuffix = new DefaultComboBoxModel<>(arrSuffix);
/*     */     
/* 192 */     this.cbPrefix.setModel(modelPrefix);
/* 193 */     this.cbSuffix.setModel(modelSuffix);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 197 */     this.cbPrefix.getModel().setSelectedItem(null);
/* 198 */     this.cbSuffix.getModel().setSelectedItem(null);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 202 */     int index = 0;
/*     */     
/* 204 */     criteria.prefixID = null;
/* 205 */     criteria.suffixID = null;
/*     */     
/* 207 */     index = this.cbPrefix.getSelectedIndex();
/* 208 */     if (index >= 0) {
/* 209 */       DBAffix affix = (DBAffix)this.cbPrefix.getSelectedItem();
/*     */       
/* 211 */       if (affix != null) {
/* 212 */         criteria.prefixID = affix.getAffixID();
/*     */       }
/*     */     } 
/*     */     
/* 216 */     index = this.cbSuffix.getSelectedIndex();
/* 217 */     if (index >= 0) {
/* 218 */       DBAffix affix = (DBAffix)this.cbSuffix.getSelectedItem();
/*     */       
/* 220 */       if (affix != null)
/* 221 */         criteria.suffixID = affix.getAffixID(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\select\AffixPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */