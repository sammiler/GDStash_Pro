/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.util.List;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import org.gdstash.db.DBViewItemSkill;
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
/*     */ public class ItemSkillPane
/*     */   extends AdjustablePanel
/*     */ {
/*  30 */   private static List<DBViewItemSkill> skills = null;
/*  31 */   private static DBViewItemSkill[] itemSkills = null;
/*     */   
/*     */   static {
/*  34 */     loadSkills();
/*     */   }
/*     */   private JComboBox<DBViewItemSkill> cbSkills;
/*     */   public static void loadSkills() {
/*  38 */     String s = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_SKILL_ALL");
/*     */     
/*  40 */     if (GDStashFrame.dbConfig == null || !GDStashFrame.dbConfig.gddbInit) {
/*     */       
/*  42 */       itemSkills = new DBViewItemSkill[2];
/*  43 */       itemSkills[0] = null;
/*     */       
/*  45 */       DBViewItemSkill all = new DBViewItemSkill(s);
/*  46 */       itemSkills[1] = all;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  51 */     skills = DBViewItemSkill.getItemSkills();
/*     */     
/*  53 */     if (skills == null) {
/*  54 */       itemSkills = new DBViewItemSkill[1];
/*  55 */       itemSkills[0] = null;
/*     */     } else {
/*  57 */       itemSkills = new DBViewItemSkill[skills.size() + 2];
/*  58 */       itemSkills[0] = null;
/*     */       
/*  60 */       DBViewItemSkill all = new DBViewItemSkill(s);
/*  61 */       itemSkills[1] = all;
/*     */       
/*  63 */       int pos = 2;
/*     */       
/*  65 */       for (DBViewItemSkill skill : skills) {
/*  66 */         itemSkills[pos] = skill;
/*     */         
/*  68 */         pos++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemSkillPane() {
/*  76 */     adjustUI();
/*     */     
/*  78 */     GroupLayout layout = null;
/*  79 */     GroupLayout.SequentialGroup hGroup = null;
/*  80 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  82 */     layout = new GroupLayout((Container)this);
/*  83 */     setLayout(layout);
/*     */     
/*  85 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/*  88 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/*  91 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  94 */     hGroup
/*  95 */       .addGroup(layout.createParallelGroup()
/*  96 */         .addComponent(this.cbSkills));
/*  97 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 100 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 103 */     vGroup
/* 104 */       .addGroup(layout.createParallelGroup()
/* 105 */         .addComponent(this.cbSkills));
/* 106 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 111 */     int size = 12;
/* 112 */     if (GDStashFrame.iniConfig != null) size = GDStashFrame.iniConfig.sectUI.fontSize; 
/* 113 */     Dimension dimMax = new Dimension(50 * size, 2 * size);
/*     */     
/* 115 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 116 */     Font fntCombo = UIManager.getDefaults().getFont("ComboBox.font");
/* 117 */     if (fntCombo == null) fntCombo = fntLabel; 
/* 118 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 119 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 121 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 122 */     fntCombo = fntCombo.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 123 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 125 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 126 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 127 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 128 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_ITEM_SKILL"));
/* 129 */     text.setTitleFont(fntBorder);
/*     */     
/* 131 */     setBorder(text);
/*     */     
/* 133 */     if (this.cbSkills == null) {
/* 134 */       this.cbSkills = new JComboBox<>(itemSkills);
/*     */     }
/* 136 */     this.cbSkills.setFont(fntCombo);
/* 137 */     this.cbSkills.setPreferredSize(dimMax);
/* 138 */     this.cbSkills.setMaximumSize(dimMax);
/*     */   }
/*     */   
/*     */   public void updateItemSkills() {
/* 142 */     DefaultComboBoxModel<DBViewItemSkill> model = new DefaultComboBoxModel<>(itemSkills);
/*     */     
/* 144 */     this.cbSkills.setModel(model);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 148 */     this.cbSkills.getModel().setSelectedItem(null);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 152 */     int index = this.cbSkills.getSelectedIndex();
/*     */     
/* 154 */     criteria.allItemSkills = false;
/* 155 */     criteria.itemSkill = null;
/*     */     
/* 157 */     if (index == 1) {
/* 158 */       criteria.allItemSkills = true;
/*     */     } else {
/* 160 */       DBViewItemSkill skill = (DBViewItemSkill)this.cbSkills.getSelectedItem();
/*     */       
/* 162 */       if (skill != null)
/* 163 */         criteria.itemSkill = skill.getSkillID(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\select\ItemSkillPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */