/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import org.gdstash.db.DBSkill;
/*     */ import org.gdstash.db.DBSkillTree;
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
/*     */ public class SkillBonusPane
/*     */   extends AdjustablePanel
/*     */ {
/*  31 */   private static List<DBSkill> skills = null;
/*  32 */   private static List<DBSkill> masteries = null;
/*  33 */   private static DBSkill[] bonusSkills = null;
/*     */   
/*     */   static {
/*  36 */     loadSkills();
/*     */   }
/*     */   private JComboBox<DBSkill> cbSkills;
/*     */   public static void loadSkills() {
/*  40 */     String s = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_SKILL_ALL");
/*     */     
/*  42 */     if (GDStashFrame.dbConfig == null || !GDStashFrame.dbConfig.gddbInit) {
/*     */       
/*  44 */       bonusSkills = new DBSkill[2];
/*  45 */       bonusSkills[0] = null;
/*     */       
/*  47 */       DBSkill all = new DBSkill(s);
/*  48 */       bonusSkills[1] = all;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  53 */     skills = DBSkill.getBonusSkills();
/*  54 */     SkillBonusPane.masteries = new LinkedList<>();
/*     */     
/*  56 */     if (skills == null) {
/*  57 */       bonusSkills = new DBSkill[2];
/*  58 */       bonusSkills[0] = null;
/*     */       
/*  60 */       DBSkill all = new DBSkill(s);
/*  61 */       bonusSkills[1] = all;
/*     */     } else {
/*  63 */       List<DBSkillTree> masteries = DBSkillTree.getMasteryTrees();
/*     */       
/*  65 */       bonusSkills = new DBSkill[skills.size() + masteries.size() + 2];
/*  66 */       bonusSkills[0] = null;
/*     */       
/*  68 */       DBSkill all = new DBSkill(s);
/*  69 */       bonusSkills[1] = all;
/*     */       
/*  71 */       int pos = 2;
/*     */       
/*  73 */       for (DBSkillTree tree : masteries) {
/*  74 */         DBSkill mSkill = tree.getMasterySkill();
/*     */         
/*  76 */         if (mSkill != null) {
/*  77 */           Object[] args = { mSkill.getName() };
/*  78 */           String txt = GDMsgFormatter.format(GDMsgFormatter.rbUI, "TXT_ALL_SKILLS_MASTERY", args);
/*     */           
/*  80 */           DBSkill skill = new DBSkill(txt, mSkill.getSkillID());
/*     */           
/*  82 */           SkillBonusPane.masteries.add(skill);
/*     */           
/*  84 */           bonusSkills[pos] = skill;
/*     */           
/*  86 */           pos++;
/*     */         } 
/*     */       } 
/*     */       
/*  90 */       for (DBSkill skill : skills) {
/*  91 */         bonusSkills[pos] = skill;
/*     */         
/*  93 */         pos++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SkillBonusPane() {
/* 101 */     adjustUI();
/*     */     
/* 103 */     GroupLayout layout = null;
/* 104 */     GroupLayout.SequentialGroup hGroup = null;
/* 105 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 107 */     layout = new GroupLayout((Container)this);
/* 108 */     setLayout(layout);
/*     */     
/* 110 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 113 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 116 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 119 */     hGroup
/* 120 */       .addGroup(layout.createParallelGroup()
/* 121 */         .addComponent(this.cbSkills));
/* 122 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 125 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 128 */     vGroup
/* 129 */       .addGroup(layout.createParallelGroup()
/* 130 */         .addComponent(this.cbSkills));
/* 131 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 136 */     int size = 12;
/* 137 */     if (GDStashFrame.iniConfig != null) size = GDStashFrame.iniConfig.sectUI.fontSize; 
/* 138 */     Dimension dimMax = new Dimension(50 * size, 2 * size);
/*     */     
/* 140 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 141 */     Font fntCombo = UIManager.getDefaults().getFont("ComboBox.font");
/* 142 */     if (fntCombo == null) fntCombo = fntLabel; 
/* 143 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 144 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 146 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 147 */     fntCombo = fntCombo.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 148 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 150 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 151 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 152 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 153 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_SKILL_BONUS"));
/* 154 */     text.setTitleFont(fntBorder);
/*     */     
/* 156 */     setBorder(text);
/*     */     
/* 158 */     if (this.cbSkills == null) {
/* 159 */       this.cbSkills = new JComboBox<>(bonusSkills);
/*     */     }
/* 161 */     this.cbSkills.setFont(fntCombo);
/* 162 */     this.cbSkills.setPreferredSize(dimMax);
/* 163 */     this.cbSkills.setMaximumSize(dimMax);
/*     */   }
/*     */   
/*     */   public void updateBonusSkills() {
/* 167 */     DefaultComboBoxModel<DBSkill> model = new DefaultComboBoxModel<>(bonusSkills);
/*     */     
/* 169 */     this.cbSkills.setModel(model);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 173 */     this.cbSkills.getModel().setSelectedItem(null);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 177 */     int index = this.cbSkills.getSelectedIndex();
/*     */     
/* 179 */     criteria.bonusSkill = null;
/* 180 */     criteria.allSkills = false;
/* 181 */     criteria.masteryBonusSkills = false;
/*     */     
/* 183 */     if (index == 1) {
/* 184 */       criteria.allSkills = true;
/*     */     } else {
/* 186 */       DBSkill skill = (DBSkill)this.cbSkills.getSelectedItem();
/*     */       
/* 188 */       if (skill != null) {
/* 189 */         criteria.bonusSkill = skill.getSkillID();
/*     */         
/* 191 */         if (index < masteries.size() + 2) criteria.masteryBonusSkills = true; 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\select\SkillBonusPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */