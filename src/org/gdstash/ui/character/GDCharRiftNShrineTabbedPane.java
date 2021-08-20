/*     */ package org.gdstash.ui.character;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import javax.swing.UIManager;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.util.AdjustableTabbedPane;
/*     */ import org.gdstash.ui.util.GDAbstractUpdateCharPane;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDCharRiftNShrineTabbedPane
/*     */   extends AdjustableTabbedPane
/*     */ {
/*     */   private GDAbstractUpdateCharPane pnlNormal;
/*     */   private GDAbstractUpdateCharPane pnlElite;
/*     */   private GDAbstractUpdateCharPane pnlUltimate;
/*     */   private GDChar gdc;
/*     */   
/*     */   public GDCharRiftNShrineTabbedPane() {
/*  27 */     adjustUI();
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  32 */     Font fntTabbed = UIManager.getDefaults().getFont("TabbedPane.font");
/*     */     
/*  34 */     fntTabbed = fntTabbed.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  35 */     setFont(fntTabbed);
/*     */     
/*  37 */     boolean vanilla = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  43 */     if (this.pnlNormal == null) {
/*  44 */       this.pnlNormal = new GDCharRiftNShrinePane(0);
/*     */     } else {
/*  46 */       this.pnlNormal.adjustUI();
/*     */     } 
/*     */     
/*  49 */     if (this.pnlElite == null) {
/*  50 */       this.pnlElite = new GDCharRiftNShrinePane(1);
/*     */     } else {
/*  52 */       this.pnlElite.adjustUI();
/*     */     } 
/*     */     
/*  55 */     if (this.pnlUltimate == null) {
/*  56 */       this.pnlUltimate = new GDCharRiftNShrinePane(2);
/*     */     } else {
/*  58 */       this.pnlUltimate.adjustUI();
/*     */     } 
/*     */     
/*  61 */     if (getTabCount() == 0) {
/*  62 */       add((Component)this.pnlNormal);
/*  63 */       add((Component)this.pnlElite);
/*  64 */       add((Component)this.pnlUltimate);
/*     */     } 
/*     */     
/*  67 */     setTitleAt(0, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DIFF_NORMAL"));
/*  68 */     setTitleAt(1, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DIFF_ELITE"));
/*  69 */     setTitleAt(2, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DIFF_ULTIMATE"));
/*     */   }
/*     */   
/*     */   public void setChar(GDChar gdc) {
/*  73 */     this.gdc = gdc;
/*     */     
/*  75 */     boolean rot = false;
/*  76 */     if (gdc != null && 
/*  77 */       gdc.isRoTCampaign()) rot = true;
/*     */ 
/*     */     
/*  80 */     if (rot) {
/*  81 */       if (this.pnlNormal instanceof GDCharRiftNShrinePane) {
/*  82 */         this.pnlNormal = new RoTRiftNShrinePane(0);
/*  83 */         this.pnlElite = new RoTRiftNShrinePane(1);
/*  84 */         this.pnlUltimate = new RoTRiftNShrinePane(2);
/*     */         
/*  86 */         removeAll();
/*  87 */         add((Component)this.pnlNormal);
/*  88 */         add((Component)this.pnlElite);
/*  89 */         add((Component)this.pnlUltimate);
/*     */         
/*  91 */         setTitleAt(0, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DIFF_NORMAL"));
/*  92 */         setTitleAt(1, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DIFF_ELITE"));
/*  93 */         setTitleAt(2, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DIFF_ULTIMATE"));
/*     */       }
/*     */     
/*  96 */     } else if (this.pnlNormal instanceof RoTRiftNShrinePane) {
/*  97 */       this.pnlNormal = new GDCharRiftNShrinePane(0);
/*  98 */       this.pnlElite = new GDCharRiftNShrinePane(1);
/*  99 */       this.pnlUltimate = new GDCharRiftNShrinePane(2);
/*     */       
/* 101 */       removeAll();
/* 102 */       add((Component)this.pnlNormal);
/* 103 */       add((Component)this.pnlElite);
/* 104 */       add((Component)this.pnlUltimate);
/*     */       
/* 106 */       setTitleAt(0, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DIFF_NORMAL"));
/* 107 */       setTitleAt(1, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DIFF_ELITE"));
/* 108 */       setTitleAt(2, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DIFF_ULTIMATE"));
/*     */     } 
/*     */ 
/*     */     
/* 112 */     this.pnlNormal.setChar(gdc);
/* 113 */     this.pnlElite.setChar(gdc);
/* 114 */     this.pnlUltimate.setChar(gdc);
/*     */   }
/*     */   
/*     */   public void updateChar(GDChar gdc) {
/* 118 */     if (gdc == null)
/*     */       return; 
/* 120 */     this.pnlNormal.updateChar(gdc);
/* 121 */     this.pnlElite.updateChar(gdc);
/* 122 */     this.pnlUltimate.updateChar(gdc);
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\character\GDCharRiftNShrineTabbedPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */