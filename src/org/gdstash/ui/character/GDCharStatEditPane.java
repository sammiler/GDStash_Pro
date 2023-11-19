/*     */ package org.gdstash.ui.character;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JPanel;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDCharStatEditPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private GDCharDataPane pnlCharData;
/*     */   private GDCharAttributePane pnlAttribute;
/*     */   private GDCharPointPane pnlPoints;
/*     */   private GDCharEditTabbedPane tabPane;
/*     */   
/*     */   public GDCharStatEditPane(GDCharEditTabbedPane tabPane) {
/*  25 */     this.tabPane = tabPane;
/*     */     
/*  27 */     adjustUI();
/*     */     
/*  29 */     setChar(null);
/*     */ 
/*     */ 
/*     */     
/*  33 */     GroupLayout layout = null;
/*  34 */     GroupLayout.SequentialGroup hGroup = null;
/*  35 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  37 */     JPanel pnlMain = buildMainPanel();
/*     */     
/*  39 */     layout = new GroupLayout((Container)this);
/*  40 */     setLayout(layout);
/*     */     
/*  42 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  45 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/*  48 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  51 */     hGroup
/*  52 */       .addGroup(layout.createParallelGroup()
/*  53 */         .addComponent(pnlMain));
/*  54 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  57 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  60 */     vGroup
/*  61 */       .addGroup(layout.createParallelGroup()
/*  62 */         .addComponent(pnlMain));
/*  63 */     layout.setVerticalGroup(vGroup);
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
/*     */   public void adjustUI() {
/*  82 */     if (this.pnlCharData == null) {
/*  83 */       this.pnlCharData = new GDCharDataPane(this.tabPane, this);
/*     */     } else {
/*  85 */       this.pnlCharData.adjustUI();
/*     */     } 
/*     */     
/*  88 */     if (this.pnlAttribute == null) {
/*  89 */       this.pnlAttribute = new GDCharAttributePane(this);
/*     */     } else {
/*  91 */       this.pnlAttribute.adjustUI();
/*     */     } 
/*     */     
/*  94 */     if (this.pnlPoints == null) {
/*  95 */       this.pnlPoints = new GDCharPointPane(this.tabPane);
/*     */     } else {
/*  97 */       this.pnlPoints.adjustUI();
/*     */     } 
/*     */   }
/*     */   
/*     */   private JPanel buildMainPanel() {
/* 102 */     JPanel panel = new JPanel();
/*     */     
/* 104 */     GroupLayout layout = null;
/* 105 */     GroupLayout.SequentialGroup hGroup = null;
/* 106 */     GroupLayout.SequentialGroup vGroup = null;
/*     */ 
/*     */ 
/*     */     
/* 110 */     layout = new GroupLayout(panel);
/* 111 */     panel.setLayout(layout);
/*     */     
/* 113 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 116 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 119 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 122 */     hGroup
/* 123 */       .addGroup(layout.createParallelGroup()
/* 124 */         .addComponent((Component)this.pnlCharData))
/* 125 */       .addGroup(layout.createParallelGroup()
/* 126 */         .addComponent((Component)this.pnlAttribute))
/* 127 */       .addGroup(layout.createParallelGroup()
/* 128 */         .addComponent((Component)this.pnlPoints));
/* 129 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 132 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 135 */     vGroup
/* 136 */       .addGroup(layout.createParallelGroup()
/* 137 */         .addComponent((Component)this.pnlCharData)
/* 138 */         .addComponent((Component)this.pnlAttribute)
/* 139 */         .addComponent((Component)this.pnlPoints));
/* 140 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 142 */     layout.linkSize(0, new Component[] { (Component)this.pnlCharData, (Component)this.pnlAttribute });
/* 143 */     layout.linkSize(0, new Component[] { (Component)this.pnlCharData, (Component)this.pnlPoints });
/*     */     
/* 145 */     layout.linkSize(1, new Component[] { (Component)this.pnlCharData, (Component)this.pnlAttribute });
/* 146 */     layout.linkSize(1, new Component[] { (Component)this.pnlCharData, (Component)this.pnlPoints });
/*     */     
/* 148 */     return panel;
/*     */   }
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 152 */     this.pnlCharData.setChar(gdc);
/* 153 */     this.pnlAttribute.setChar(gdc);
/* 154 */     this.pnlPoints.setChar(gdc);
/*     */   }
/*     */   
/*     */   public void updateChar(GDChar gdc) {
/* 158 */     if (gdc == null)
/*     */       return; 
/* 160 */     this.pnlCharData.updateChar(gdc);
/* 161 */     this.pnlAttribute.updateChar(gdc);
/* 162 */     this.pnlPoints.updateChar(gdc);
/*     */   }
/*     */   
/*     */   public String getCharName() {
/* 166 */     if (this.pnlCharData == null) return null;
/*     */     
/* 168 */     return this.pnlCharData.getCharName();
/*     */   }
/*     */   
/*     */   public int getStatPoints() {
/* 172 */     if (this.pnlPoints == null) return 0;
/*     */     
/* 174 */     return this.pnlPoints.getStatPoints();
/*     */   }
/*     */   
/*     */   public float getHealth() {
/* 178 */     if (this.pnlAttribute == null) return 0.0F;
/*     */     
/* 180 */     return this.pnlAttribute.getHealth();
/*     */   }
/*     */   
/*     */   public float getEnergy() {
/* 184 */     if (this.pnlAttribute == null) return 0.0F;
/*     */     
/* 186 */     return this.pnlAttribute.getEnergy();
/*     */   }
/*     */   
/*     */   public void setStatPoints(int points) {
/* 190 */     if (this.pnlPoints == null)
/*     */       return; 
/* 192 */     this.pnlPoints.setStatPoints(points);
/*     */   }
/*     */   
/*     */   public void setSkillPoints(int points) {
/* 196 */     if (this.pnlPoints == null)
/*     */       return; 
/* 198 */     this.pnlPoints.setSkillPoints(points);
/*     */   }
/*     */   
/*     */   public void setHealth(float health) {
/* 202 */     if (this.pnlAttribute == null)
/*     */       return; 
/* 204 */     this.pnlAttribute.setHealth(health);
/*     */   }
/*     */   
/*     */   public void setEnergy(float energy) {
/* 208 */     if (this.pnlAttribute == null)
/*     */       return; 
/* 210 */     this.pnlAttribute.setEnergy(energy);
/*     */   }
/*     */   
/*     */   public void resetMastery(String masteryID) {
/* 214 */     if (this.pnlPoints != null) this.pnlPoints.resetMastery(masteryID); 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharStatEditPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */