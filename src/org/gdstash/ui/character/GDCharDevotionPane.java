/*     */ package org.gdstash.ui.character;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import javax.swing.table.TableModel;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.table.GDDevotionTable;
/*     */ import org.gdstash.ui.table.GDDevotionTableModel;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDCharDevotionPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private GDDevotionTableModel model;
/*     */   private GDDevotionTable table;
/*     */   
/*     */   public GDCharDevotionPane() {
/*  33 */     adjustUI();
/*     */     
/*  35 */     GroupLayout layout = null;
/*  36 */     GroupLayout.SequentialGroup hGroup = null;
/*  37 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  39 */     JScrollPane scroll = new JScrollPane((Component)this.table);
/*     */     
/*  41 */     layout = new GroupLayout((Container)this);
/*  42 */     setLayout(layout);
/*     */     
/*  44 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  47 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  50 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  53 */     hGroup
/*  54 */       .addGroup(layout.createParallelGroup()
/*  55 */         .addComponent(scroll));
/*  56 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  59 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  62 */     vGroup
/*  63 */       .addGroup(layout.createParallelGroup()
/*  64 */         .addComponent(scroll));
/*  65 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  72 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  73 */     Font fntTable = UIManager.getDefaults().getFont("TableHeader.font");
/*  74 */     if (fntTable == null) fntTable = fntLabel; 
/*  75 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/*  76 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/*  78 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  79 */     fntTable = fntTable.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  80 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/*  82 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  83 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  84 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*  85 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DEVOTION_SKILLS"));
/*  86 */     text.setTitleFont(fntBorder);
/*     */     
/*  88 */     setBorder(text);
/*     */     
/*  90 */     if (this.table == null) {
/*  91 */       this.table = new GDDevotionTable();
/*  92 */       this.table.setPreferredScrollableViewportSize(new Dimension(GDStashFrame.iniConfig.sectUI.fontSize * 25, GDStashFrame.iniConfig.sectUI.fontSize * 15));
/*     */       
/*  94 */       setChar((GDChar)null);
/*     */     } 
/*  96 */     this.table.getTableHeader().setFont(fntTable);
/*  97 */     this.table.setFont(fntLabel);
/*     */   }
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 101 */     this.model = new GDDevotionTableModel(gdc);
/* 102 */     this.table.setModel((TableModel)this.model);
/*     */     
/* 104 */     this.table.setSelectionMode(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateChar(GDChar gdc) {
/* 109 */     if (gdc == null)
/*     */       return; 
/* 111 */     GDDevotionTableModel model = (GDDevotionTableModel)this.table.getModel();
/* 112 */     GDChar.SkillInfo[] devotions = model.getDevotions();
/*     */     
/* 114 */     gdc.setSkillLevel(devotions);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharDevotionPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */