/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JToggleButton;
/*     */ import org.gdstash.db.SelectionCriteria;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ import org.gdstash.util.GDImagePool;
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
/*     */ public class SelectAndOrPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private JToggleButton rbOr;
/*     */   private JToggleButton rbAnd;
/*     */   private ButtonGroup group;
/*     */   
/*     */   public SelectAndOrPane() {
/*  34 */     this(0);
/*     */   }
/*     */   
/*     */   public SelectAndOrPane(int direction) {
/*  38 */     adjustUI();
/*     */     
/*  40 */     GroupLayout layout = null;
/*  41 */     GroupLayout.SequentialGroup hGroup = null;
/*  42 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  44 */     layout = new GroupLayout((Container)this);
/*  45 */     setLayout(layout);
/*     */     
/*  47 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/*  50 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/*  53 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  56 */     hGroup
/*  57 */       .addGroup(layout.createParallelGroup()
/*  58 */         .addComponent(this.rbOr)
/*  59 */         .addComponent(this.rbAnd));
/*     */ 
/*     */     
/*  62 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  65 */     vGroup
/*  66 */       .addGroup(layout.createParallelGroup()
/*  67 */         .addComponent(this.rbOr))
/*  68 */       .addGroup(layout.createParallelGroup()
/*  69 */         .addComponent(this.rbAnd));
/*     */     
/*  71 */     if (direction == 0) {
/*  72 */       layout.setHorizontalGroup(vGroup);
/*  73 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/*  75 */       layout.setHorizontalGroup(hGroup);
/*  76 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/*  79 */     layout.linkSize(0, new Component[] { this.rbOr, this.rbAnd });
/*     */     
/*  81 */     layout.linkSize(1, new Component[] { this.rbOr, this.rbAnd });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 106 */     if (this.rbOr == null) {
/* 107 */       this.rbOr = new JToggleButton();
/*     */       
/* 109 */       this.rbOr.setIcon(GDImagePool.iconBoolOrUnselect);
/* 110 */       this.rbOr.setSelectedIcon(GDImagePool.iconBoolOr);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     if (this.rbAnd == null) {
/* 117 */       this.rbAnd = new JToggleButton();
/*     */       
/* 119 */       this.rbAnd.setIcon(GDImagePool.iconBoolAndUnselect);
/* 120 */       this.rbAnd.setSelectedIcon(GDImagePool.iconBoolAnd);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     if (this.group == null) {
/* 127 */       this.group = new ButtonGroup();
/*     */       
/* 129 */       this.rbOr.setSelected(true);
/*     */       
/* 131 */       this.group.add(this.rbOr);
/* 132 */       this.group.add(this.rbAnd);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {}
/*     */ 
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 141 */     criteria.combiMode = SelectionCriteria.CombinationMode.OR;
/*     */     
/* 143 */     if (this.rbAnd.isSelected()) criteria.combiMode = SelectionCriteria.CombinationMode.AND; 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\select\SelectAndOrPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */