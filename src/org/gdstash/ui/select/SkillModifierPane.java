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
/*     */ public class SkillModifierPane
/*     */   extends AdjustablePanel
/*     */ {
/*  31 */   private static List<DBSkill> skills = null;
/*  32 */   private static List<DBSkill> masteries = null;
/*  33 */   private static DBSkill[] modifiedSkills = null;
/*     */   
/*     */   static {
/*  36 */     loadSkills();
/*     */   }
/*     */   private JComboBox<DBSkill> cbSkills;
/*     */   public static void loadSkills() {
/*  40 */     if (GDStashFrame.dbConfig == null || !GDStashFrame.dbConfig.gddbInit) {
/*     */       
/*  42 */       modifiedSkills = new DBSkill[1];
/*  43 */       modifiedSkills[0] = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  48 */     skills = DBSkill.getModifiedSkills();
/*  49 */     SkillModifierPane.masteries = new LinkedList<>();
/*     */     
/*  51 */     if (skills == null) {
/*  52 */       modifiedSkills = new DBSkill[1];
/*  53 */       modifiedSkills[0] = null;
/*     */     } else {
/*  55 */       List<DBSkillTree> masteries = DBSkillTree.getMasteryTrees();
/*     */       
/*  57 */       modifiedSkills = new DBSkill[skills.size() + masteries.size() + 1];
/*  58 */       modifiedSkills[0] = null;
/*     */       
/*  60 */       int pos = 1;
/*     */       
/*  62 */       for (DBSkillTree tree : masteries) {
/*  63 */         DBSkill mSkill = tree.getMasterySkill();
/*     */         
/*  65 */         if (mSkill != null) {
/*  66 */           Object[] args = { mSkill.getName() };
/*  67 */           String txt = GDMsgFormatter.format(GDMsgFormatter.rbUI, "TXT_ALL_SKILLS_MASTERY", args);
/*     */           
/*  69 */           DBSkill skill = new DBSkill(txt, mSkill.getSkillID());
/*     */           
/*  71 */           SkillModifierPane.masteries.add(skill);
/*     */           
/*  73 */           modifiedSkills[pos] = skill;
/*     */           
/*  75 */           pos++;
/*     */         } 
/*     */       } 
/*     */       
/*  79 */       for (DBSkill skill : skills) {
/*  80 */         modifiedSkills[pos] = skill;
/*     */         
/*  82 */         pos++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SkillModifierPane() {
/*  90 */     adjustUI();
/*     */     
/*  92 */     GroupLayout layout = null;
/*  93 */     GroupLayout.SequentialGroup hGroup = null;
/*  94 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  96 */     layout = new GroupLayout((Container)this);
/*  97 */     setLayout(layout);
/*     */     
/*  99 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 102 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 105 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 108 */     hGroup
/* 109 */       .addGroup(layout.createParallelGroup()
/* 110 */         .addComponent(this.cbSkills));
/* 111 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 114 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 117 */     vGroup
/* 118 */       .addGroup(layout.createParallelGroup()
/* 119 */         .addComponent(this.cbSkills));
/* 120 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 125 */     int size = 12;
/* 126 */     if (GDStashFrame.iniConfig != null) size = GDStashFrame.iniConfig.sectUI.fontSize; 
/* 127 */     Dimension dimMax = new Dimension(50 * size, 2 * size);
/*     */     
/* 129 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 130 */     Font fntCombo = UIManager.getDefaults().getFont("ComboBox.font");
/* 131 */     if (fntCombo == null) fntCombo = fntLabel; 
/* 132 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 133 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 135 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 136 */     fntCombo = fntCombo.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 137 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 139 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 140 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 141 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 142 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_SKILL_MODIFIER"));
/* 143 */     text.setTitleFont(fntBorder);
/*     */     
/* 145 */     setBorder(text);
/*     */     
/* 147 */     if (this.cbSkills == null) {
/* 148 */       this.cbSkills = new JComboBox<>(modifiedSkills);
/*     */     }
/* 150 */     this.cbSkills.setFont(fntCombo);
/* 151 */     this.cbSkills.setPreferredSize(dimMax);
/* 152 */     this.cbSkills.setMaximumSize(dimMax);
/*     */   }
/*     */   
/*     */   public void updateModifiedSkills() {
/* 156 */     DefaultComboBoxModel<DBSkill> model = new DefaultComboBoxModel<>(modifiedSkills);
/*     */     
/* 158 */     this.cbSkills.setModel(model);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 162 */     this.cbSkills.getModel().setSelectedItem(null);
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 166 */     int index = this.cbSkills.getSelectedIndex();
/*     */     
/* 168 */     criteria.modifiedSkill = null;
/* 169 */     criteria.masteryModifySkills = false;
/*     */     
/* 171 */     DBSkill skill = (DBSkill)this.cbSkills.getSelectedItem();
/*     */     
/* 173 */     if (skill != null) {
/* 174 */       criteria.modifiedSkill = skill.getSkillID();
/*     */       
/* 176 */       if (index < masteries.size() + 1) criteria.masteryModifySkills = true; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\SkillModifierPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */