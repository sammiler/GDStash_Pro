/*     */ package org.gdstash.ui.character;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import javax.swing.UIManager;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.util.AdjustableTabbedPane;
/*     */ import org.gdstash.util.GDImagePool;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDCharEditTabbedPane
/*     */   extends AdjustableTabbedPane
/*     */   implements GDUIMasterySupport
/*     */ {
/*     */   private GDCharDataOverviewPane pnlCharData;
/*     */   private GDCharRiftNShrineTabbedPane pnlRiftShrine;
/*     */   private GDCharMasteryPane pnlMastery1;
/*     */   private GDCharMasteryPane pnlMastery2;
/*     */   private int skillPoints;
/*     */   private GDChar gdc;
/*     */   
/*     */   public GDCharEditTabbedPane() {
/*  31 */     this.pnlCharData = new GDCharDataOverviewPane(this);
/*  32 */     this.pnlRiftShrine = new GDCharRiftNShrineTabbedPane();
/*  33 */     this.pnlMastery1 = new GDCharMasteryPane(this, false);
/*  34 */     this.pnlMastery2 = new GDCharMasteryPane(this, false);
/*     */     
/*  36 */     add((Component)this.pnlCharData);
/*  37 */     add((Component)this.pnlRiftShrine);
/*  38 */     add((Component)this.pnlMastery1);
/*  39 */     add((Component)this.pnlMastery2);
/*     */     
/*  41 */     adjustUI();
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  46 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  47 */     Font fntTabbed = UIManager.getDefaults().getFont("TabbedPane.font");
/*     */     
/*  49 */     fntTabbed = fntTabbed.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  50 */     setFont(fntTabbed);
/*     */     
/*  52 */     int index = 0;
/*     */     
/*  54 */     setTitleAt(index, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_CHAR_DATA"));
/*  55 */     setIconAt(index, GDImagePool.iconTabCharData16);
/*  56 */     index++;
/*     */     
/*  58 */     setTitleAt(index, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_SHRINE_RIFT"));
/*  59 */     setIconAt(index, GDImagePool.iconTabCharPortal16);
/*  60 */     index++;
/*     */     
/*  62 */     setTitleAt(index, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_MASTERY_1"));
/*  63 */     index++;
/*     */     
/*  65 */     setTitleAt(index, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_MASTERY_2"));
/*  66 */     index++;
/*     */     
/*  68 */     this.pnlCharData.adjustUI();
/*  69 */     this.pnlRiftShrine.adjustUI();
/*  70 */     this.pnlMastery1.adjustUI();
/*  71 */     this.pnlMastery2.adjustUI();
/*     */   }
/*     */   
/*     */   public void setChar(GDChar gdc) {
/*  75 */     this.gdc = gdc;
/*     */     
/*  77 */     if (gdc == null) {
/*  78 */       this.skillPoints = 0;
/*     */     } else {
/*  80 */       this.skillPoints = gdc.getSkillPoints();
/*     */     } 
/*     */     
/*  83 */     this.pnlCharData.setChar(gdc);
/*  84 */     this.pnlRiftShrine.setChar(gdc);
/*  85 */     this.pnlMastery1.setChar(gdc);
/*  86 */     this.pnlMastery2.setChar(gdc);
/*     */     
/*  88 */     if (gdc == null) {
/*  89 */       setTitleAt(2, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_MASTERY_1"));
/*  90 */       setTitleAt(3, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_MASTERY_2"));
/*     */       
/*  92 */       this.pnlMastery1.setMastery(null);
/*  93 */       this.pnlMastery2.setMastery(null);
/*     */     } else {
/*  95 */       GDChar.SkillInfo[] infos = gdc.getMasteryInfo();
/*     */       
/*  97 */       if (infos == null) {
/*  98 */         setTitleAt(2, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_MASTERY_1"));
/*  99 */         setTitleAt(3, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_MASTERY_2"));
/*     */         
/* 101 */         this.pnlMastery1.setMastery(null);
/* 102 */         this.pnlMastery2.setMastery(null);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 107 */       if (infos.length == 1) {
/* 108 */         setTitleAt(2, (infos[0]).name);
/* 109 */         setTitleAt(3, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_MASTERY_2"));
/*     */         
/* 111 */         this.pnlMastery1.setMastery((infos[0]).id);
/* 112 */         this.pnlMastery2.setMastery(null);
/*     */       } 
/*     */       
/* 115 */       if (infos.length == 2) {
/* 116 */         setTitleAt(2, (infos[0]).name);
/* 117 */         setTitleAt(3, (infos[1]).name);
/*     */         
/* 119 */         this.pnlMastery1.setMastery((infos[0]).id);
/* 120 */         this.pnlMastery2.setMastery((infos[1]).id);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateChar(GDChar gdc) {
/* 126 */     this.pnlMastery1.updateChar(gdc);
/* 127 */     this.pnlMastery2.updateChar(gdc);
/*     */ 
/*     */ 
/*     */     
/* 131 */     this.pnlCharData.updateChar(gdc);
/* 132 */     this.pnlRiftShrine.updateChar(gdc);
/*     */   }
/*     */   
/*     */   public String getCharName() {
/* 136 */     if (this.pnlCharData == null) return null;
/*     */     
/* 138 */     return this.pnlCharData.getCharName();
/*     */   }
/*     */   
/*     */   public void updateConfig() {
/* 142 */     if (this.pnlCharData != null) this.pnlCharData.updateConfig(); 
/* 143 */     if (this.pnlMastery1 != null) this.pnlMastery1.updateConfig(); 
/* 144 */     if (this.pnlMastery2 != null) this.pnlMastery2.updateConfig();
/*     */   
/*     */   }
/*     */   
/*     */   public int getSkillPoints() {
/* 149 */     return this.skillPoints;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSkillPoints(int points) {
/* 154 */     this.skillPoints = points;
/*     */     
/* 156 */     if (this.pnlCharData != null) this.pnlCharData.setSkillPoints(points); 
/* 157 */     if (this.pnlMastery1 != null) this.pnlMastery1.setSkillPoints(points); 
/* 158 */     if (this.pnlMastery2 != null) this.pnlMastery2.setSkillPoints(points);
/*     */   
/*     */   }
/*     */   
/*     */   public int getPointsForSkill(String skillID) {
/* 163 */     int points = this.pnlMastery1.getPointsForSkill(skillID);
/*     */     
/* 165 */     if (points > 0) return points;
/*     */     
/* 167 */     points = this.pnlMastery2.getPointsForSkill(skillID);
/*     */     
/* 169 */     return points;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCharLevel() {
/* 174 */     if (this.gdc == null) return 0;
/*     */     
/* 176 */     return this.gdc.getLevel();
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetMastery(String masteryID) {
/* 181 */     if (masteryID == null)
/*     */       return; 
/* 183 */     int points = this.gdc.refundMastery(masteryID);
/*     */     
/* 185 */     if (points > 0) {
/* 186 */       points += getSkillPoints();
/*     */       
/* 188 */       setSkillPoints(points);
/*     */       
/* 190 */       this.pnlCharData.resetMastery(masteryID);
/* 191 */       this.pnlMastery1.resetMastery(masteryID);
/* 192 */       this.pnlMastery2.resetMastery(masteryID);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharEditTabbedPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */