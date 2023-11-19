/*     */ package org.gdstash.ui.character;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.UIManager;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ import org.gdstash.ui.util.GDCharInfoList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDCharDataOverviewPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private GDCharStatEditPane pnlEdit;
/*     */   private GDCharFactionPane pnlFactions;
/*     */   private GDCharDevotionPane pnlDevotions;
/*     */   private GDCharStatAchievementPane pnlAchieve;
/*     */   private JPanel pnlMain;
/*     */   private GDCharEditTabbedPane tabPane;
/*     */   private GDCharInfoList.GDCharFileInfo info;
/*     */   
/*     */   public GDCharDataOverviewPane(GDCharEditTabbedPane tabPane) {
/*  33 */     this.tabPane = tabPane;
/*     */     
/*  35 */     adjustUI();
/*     */     
/*  37 */     setChar(null);
/*     */ 
/*     */ 
/*     */     
/*  41 */     JPanel pnlLeft = buildLeftSidePanel();
/*  42 */     JPanel pnlRight = buildRightSidePanel();
/*     */     
/*  44 */     GroupLayout layout = null;
/*  45 */     GroupLayout.SequentialGroup hGroup = null;
/*  46 */     GroupLayout.SequentialGroup vGroup = null;
/*     */ 
/*     */ 
/*     */     
/*  50 */     layout = new GroupLayout((Container)this);
/*  51 */     setLayout(layout);
/*     */     
/*  53 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  56 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  59 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  62 */     hGroup
/*  63 */       .addGroup(layout.createParallelGroup()
/*  64 */         .addComponent(pnlLeft))
/*  65 */       .addGroup(layout.createParallelGroup()
/*  66 */         .addComponent(pnlRight));
/*  67 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  70 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  73 */     vGroup
/*  74 */       .addGroup(layout.createParallelGroup()
/*  75 */         .addComponent(pnlLeft)
/*  76 */         .addComponent(pnlRight));
/*  77 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  82 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  83 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/*  84 */     if (fntButton == null) fntButton = fntLabel; 
/*  85 */     Font fntCombo = UIManager.getDefaults().getFont("ComboBox.font");
/*  86 */     if (fntCombo == null) fntCombo = fntLabel;
/*     */     
/*  88 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  89 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  90 */     fntCombo = fntCombo.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */ 
/*     */ 
/*     */     
/*  94 */     GDCharInfoList.adjustCharInfos(null, null);
/*     */     
/*  96 */     if (this.pnlEdit == null) {
/*  97 */       this.pnlEdit = new GDCharStatEditPane(this.tabPane);
/*     */     } else {
/*  99 */       this.pnlEdit.adjustUI();
/*     */     } 
/*     */     
/* 102 */     if (this.pnlFactions == null) {
/* 103 */       this.pnlFactions = new GDCharFactionPane();
/*     */     } else {
/* 105 */       this.pnlFactions.adjustUI();
/*     */     } 
/*     */     
/* 108 */     if (this.pnlDevotions == null) {
/* 109 */       this.pnlDevotions = new GDCharDevotionPane();
/*     */     } else {
/* 111 */       this.pnlDevotions.adjustUI();
/*     */     } 
/*     */     
/* 114 */     if (this.pnlAchieve == null) {
/* 115 */       this.pnlAchieve = new GDCharStatAchievementPane();
/*     */     } else {
/* 117 */       this.pnlAchieve.adjustUI();
/*     */     } 
/*     */   }
/*     */   
/*     */   private JPanel buildLeftSidePanel() {
/* 122 */     JPanel panel = new JPanel();
/*     */     
/* 124 */     JPanel pnlWrapAchieve = buildWrapAchievePanel();
/*     */     
/* 126 */     GroupLayout layout = null;
/* 127 */     GroupLayout.SequentialGroup hGroup = null;
/* 128 */     GroupLayout.SequentialGroup vGroup = null;
/*     */ 
/*     */ 
/*     */     
/* 132 */     layout = new GroupLayout(panel);
/* 133 */     panel.setLayout(layout);
/*     */     
/* 135 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 138 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 141 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 144 */     hGroup
/* 145 */       .addGroup(layout.createParallelGroup()
/* 146 */         .addComponent((Component)this.pnlEdit)
/* 147 */         .addComponent(pnlWrapAchieve));
/* 148 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 151 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 154 */     vGroup
/* 155 */       .addGroup(layout.createParallelGroup()
/* 156 */         .addComponent((Component)this.pnlEdit))
/* 157 */       .addGroup(layout.createParallelGroup()
/* 158 */         .addComponent(pnlWrapAchieve));
/* 159 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 161 */     layout.linkSize(0, new Component[] { (Component)this.pnlEdit, pnlWrapAchieve });
/*     */ 
/*     */ 
/*     */     
/* 165 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildWrapAchievePanel() {
/* 169 */     JPanel panel = new JPanel();
/*     */     
/* 171 */     GroupLayout layout = null;
/* 172 */     GroupLayout.SequentialGroup hGroup = null;
/* 173 */     GroupLayout.SequentialGroup vGroup = null;
/*     */ 
/*     */ 
/*     */     
/* 177 */     layout = new GroupLayout(panel);
/* 178 */     panel.setLayout(layout);
/*     */     
/* 180 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 183 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 186 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 189 */     hGroup
/* 190 */       .addGroup(layout.createParallelGroup()
/* 191 */         .addComponent((Component)this.pnlAchieve));
/* 192 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 195 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 198 */     vGroup
/* 199 */       .addGroup(layout.createParallelGroup()
/* 200 */         .addComponent((Component)this.pnlAchieve));
/* 201 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 203 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildRightSidePanel() {
/* 207 */     JPanel panel = new JPanel();
/*     */     
/* 209 */     GroupLayout layout = null;
/* 210 */     GroupLayout.SequentialGroup hGroup = null;
/* 211 */     GroupLayout.SequentialGroup vGroup = null;
/*     */ 
/*     */ 
/*     */     
/* 215 */     layout = new GroupLayout(panel);
/* 216 */     panel.setLayout(layout);
/*     */     
/* 218 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 221 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 224 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 227 */     hGroup
/* 228 */       .addGroup(layout.createParallelGroup()
/* 229 */         .addComponent((Component)this.pnlFactions))
/* 230 */       .addGroup(layout.createParallelGroup()
/* 231 */         .addComponent((Component)this.pnlDevotions));
/* 232 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 235 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 238 */     vGroup
/* 239 */       .addGroup(layout.createParallelGroup()
/* 240 */         .addComponent((Component)this.pnlFactions)
/* 241 */         .addComponent((Component)this.pnlDevotions));
/* 242 */     layout.setVerticalGroup(vGroup);
/*     */ 
/*     */ 
/*     */     
/* 246 */     layout.linkSize(1, new Component[] { (Component)this.pnlFactions, (Component)this.pnlDevotions });
/*     */     
/* 248 */     return panel;
/*     */   }
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 252 */     this.pnlEdit.setChar(gdc);
/* 253 */     this.pnlFactions.setChar(gdc);
/* 254 */     this.pnlDevotions.setChar(gdc);
/* 255 */     this.pnlAchieve.setChar(gdc);
/*     */   }
/*     */   
/*     */   public void updateChar(GDChar gdc) {
/* 259 */     this.pnlEdit.updateChar(gdc);
/* 260 */     this.pnlFactions.updateChar(gdc);
/* 261 */     this.pnlDevotions.updateChar(gdc);
/* 262 */     this.pnlAchieve.updateChar(gdc);
/*     */   }
/*     */   
/*     */   public String getCharName() {
/* 266 */     if (this.pnlEdit == null) return null;
/*     */     
/* 268 */     return this.pnlEdit.getCharName();
/*     */   }
/*     */   
/*     */   public void updateConfig() {
/* 272 */     if (this.pnlFactions != null) this.pnlFactions.updateConfig(); 
/*     */   }
/*     */   
/*     */   public void setSkillPoints(int points) {
/* 276 */     if (this.pnlEdit == null)
/*     */       return; 
/* 278 */     this.pnlEdit.setSkillPoints(points);
/*     */   }
/*     */   
/*     */   public void resetMastery(String masteryID) {
/* 282 */     if (this.pnlEdit != null) this.pnlEdit.resetMastery(masteryID); 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharDataOverviewPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */