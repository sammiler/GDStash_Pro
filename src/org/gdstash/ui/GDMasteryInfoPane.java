/*     */ package org.gdstash.ui;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import javax.swing.UIManager;
/*     */ import org.gdstash.db.DBEngineLevel;
/*     */ import org.gdstash.db.DBEnginePlayer;
/*     */ import org.gdstash.ui.character.GDCharMasteryPane;
/*     */ import org.gdstash.ui.character.GDUIMasterySupport;
/*     */ import org.gdstash.ui.util.AdjustableTabbedPane;
/*     */ import org.gdstash.util.GDMsgFormatter;
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
/*     */ public class GDMasteryInfoPane
/*     */   extends AdjustableTabbedPane
/*     */   implements GDUIMasterySupport
/*     */ {
/*     */   private static int MAX_POINTS;
/*     */   private static int MAX_LEVEL;
/*     */   private static final int QUEST_POINTS = 11;
/*     */   private GDCharMasteryPane pnlMastery1;
/*     */   private GDCharMasteryPane pnlMastery2;
/*     */   private int points;
/*     */   
/*     */   public static void initStats() {
/*  40 */     MAX_LEVEL = 100;
/*  41 */     MAX_POINTS = 248;
/*     */     
/*  43 */     DBEnginePlayer ep = DBEnginePlayer.get();
/*  44 */     if (ep != null) {
/*  45 */       MAX_LEVEL = ep.getMaxLevel();
/*     */       
/*  47 */       DBEngineLevel el = DBEngineLevel.get();
/*  48 */       if (el != null) {
/*  49 */         MAX_POINTS = el.getSkillPointsForRange(1, MAX_LEVEL) + 11;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public GDMasteryInfoPane(GDStashFrame frame) {
/*  55 */     this.points = MAX_POINTS;
/*     */     
/*  57 */     adjustUI();
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  62 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  63 */     Font fntTabbed = UIManager.getDefaults().getFont("TabbedPane.font");
/*     */     
/*  65 */     fntTabbed = fntTabbed.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  66 */     setFont(fntTabbed);
/*     */     
/*  68 */     if (this.pnlMastery1 == null) {
/*  69 */       this.pnlMastery1 = new GDCharMasteryPane(this, true);
/*     */       
/*  71 */       this.pnlMastery1.setSkillPoints(this.points);
/*     */       
/*  73 */       add((Component)this.pnlMastery1);
/*     */     } else {
/*  75 */       this.pnlMastery1.adjustUI();
/*     */     } 
/*     */     
/*  78 */     if (this.pnlMastery2 == null) {
/*  79 */       this.pnlMastery2 = new GDCharMasteryPane(this, true);
/*     */       
/*  81 */       this.pnlMastery2.setSkillPoints(this.points);
/*     */       
/*  83 */       add((Component)this.pnlMastery2);
/*     */     } else {
/*  85 */       this.pnlMastery2.adjustUI();
/*     */     } 
/*     */     
/*  88 */     int index = 0;
/*     */     
/*  90 */     setTitleAt(index, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_MASTERY_1"));
/*  91 */     index++;
/*     */     
/*  93 */     setTitleAt(index, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_MASTERY_2"));
/*  94 */     index++;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateConfig() {
/* 100 */     setSkillPoints(MAX_POINTS);
/*     */     
/* 102 */     if (this.pnlMastery1 != null) this.pnlMastery1.updateConfig(); 
/* 103 */     if (this.pnlMastery2 != null) this.pnlMastery2.updateConfig();
/*     */   
/*     */   }
/*     */   
/*     */   public int getSkillPoints() {
/* 108 */     return this.points;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSkillPoints(int points) {
/* 113 */     this.points = points;
/*     */     
/* 115 */     this.pnlMastery1.setSkillPoints(points);
/* 116 */     this.pnlMastery2.setSkillPoints(points);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPointsForSkill(String skillID) {
/* 121 */     int points = this.pnlMastery1.getPointsForSkill(skillID);
/*     */     
/* 123 */     if (points > 0) return points;
/*     */     
/* 125 */     points = this.pnlMastery2.getPointsForSkill(skillID);
/*     */     
/* 127 */     return points;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCharLevel() {
/* 132 */     return MAX_LEVEL;
/*     */   }
/*     */   
/*     */   public void resetMastery(String masteryID) {}
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\GDMasteryInfoPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */