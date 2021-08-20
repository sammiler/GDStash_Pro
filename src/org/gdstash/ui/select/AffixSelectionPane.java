/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
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
/*     */ public class AffixSelectionPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private AffixRarityPane pnlRarity;
/*     */   private DamageSelectionPane pnlDamage;
/*     */   private AttribSelectionPane pnlAttrib;
/*     */   private LevelSelPane pnlLevel;
/*     */   
/*     */   public AffixSelectionPane() {
/*  33 */     adjustUI();
/*     */     
/*  35 */     GroupLayout layout = null;
/*  36 */     GroupLayout.SequentialGroup hGroup = null;
/*  37 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  39 */     JPanel pnlMain = buildMainPanel();
/*  40 */     JScrollPane scroll = new JScrollPane(pnlMain);
/*  41 */     scroll.getVerticalScrollBar().setUnitIncrement(2 * GDStashFrame.iniConfig.sectUI.fontSize);
/*  42 */     scroll.setWheelScrollingEnabled(true);
/*     */     
/*  44 */     layout = new GroupLayout((Container)this);
/*  45 */     setLayout(layout);
/*     */     
/*  47 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  50 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  53 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  56 */     hGroup
/*  57 */       .addGroup(layout.createParallelGroup()
/*  58 */         .addComponent(scroll));
/*  59 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  62 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  65 */     vGroup
/*  66 */       .addGroup(layout.createParallelGroup()
/*  67 */         .addComponent(scroll));
/*  68 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  73 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  74 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/*  75 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */ 
/*     */     
/*  78 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/*  80 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  81 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  82 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*  83 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_AFFIX_INFO"));
/*  84 */     text.setTitleFont(fntBorder);
/*     */     
/*  86 */     setBorder(text);
/*     */     
/*  88 */     if (this.pnlRarity == null) {
/*  89 */       this.pnlRarity = new AffixRarityPane(0);
/*     */     } else {
/*  91 */       this.pnlRarity.adjustUI();
/*     */     } 
/*     */     
/*  94 */     if (this.pnlDamage == null) {
/*  95 */       this.pnlDamage = new DamageSelectionPane();
/*     */     } else {
/*  97 */       this.pnlDamage.adjustUI();
/*     */     } 
/*     */     
/* 100 */     if (this.pnlAttrib == null) {
/* 101 */       this.pnlAttrib = new AttribSelectionPane();
/*     */     } else {
/* 103 */       this.pnlAttrib.adjustUI();
/*     */     } 
/*     */     
/* 106 */     if (this.pnlLevel == null) {
/* 107 */       this.pnlLevel = new LevelSelPane();
/*     */     } else {
/* 109 */       this.pnlLevel.adjustUI();
/*     */     } 
/*     */   }
/*     */   
/*     */   private JPanel buildMainPanel() {
/* 114 */     JPanel panel = new JPanel();
/*     */     
/* 116 */     BorderLayout layout = new BorderLayout();
/*     */     
/* 118 */     JPanel pnlDmgAttrib = buildDamageAttribPanel();
/*     */     
/* 120 */     panel.setLayout(layout);
/*     */     
/* 122 */     panel.add((Component)this.pnlRarity, "North");
/* 123 */     panel.add(pnlDmgAttrib, "Center");
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
/* 166 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildDamageAttribPanel() {
/* 170 */     JPanel panel = new JPanel();
/*     */     
/* 172 */     GroupLayout layout = null;
/* 173 */     GroupLayout.SequentialGroup hGroup = null;
/* 174 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 176 */     JPanel pnlAttrib = buildAttribPanel();
/*     */     
/* 178 */     layout = new GroupLayout(panel);
/* 179 */     panel.setLayout(layout);
/*     */     
/* 181 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 184 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 187 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 190 */     hGroup
/* 191 */       .addGroup(layout.createParallelGroup()
/* 192 */         .addComponent((Component)this.pnlDamage))
/* 193 */       .addGroup(layout.createParallelGroup()
/* 194 */         .addComponent(pnlAttrib));
/* 195 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 198 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 201 */     vGroup
/* 202 */       .addGroup(layout.createParallelGroup()
/* 203 */         .addComponent((Component)this.pnlDamage)
/* 204 */         .addComponent(pnlAttrib));
/*     */     
/* 206 */     layout.setVerticalGroup(vGroup);
/*     */ 
/*     */ 
/*     */     
/* 210 */     layout.linkSize(1, new Component[] { (Component)this.pnlDamage, pnlAttrib });
/*     */     
/* 212 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildAttribPanel() {
/* 216 */     JPanel panel = new JPanel();
/*     */     
/* 218 */     GroupLayout layout = null;
/* 219 */     GroupLayout.SequentialGroup hGroup = null;
/* 220 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 222 */     layout = new GroupLayout(panel);
/* 223 */     panel.setLayout(layout);
/*     */     
/* 225 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 228 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 231 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 234 */     hGroup
/* 235 */       .addGroup(layout.createParallelGroup()
/* 236 */         .addComponent((Component)this.pnlAttrib)
/* 237 */         .addComponent((Component)this.pnlLevel));
/* 238 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 241 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 244 */     vGroup
/* 245 */       .addGroup(layout.createParallelGroup()
/* 246 */         .addComponent((Component)this.pnlAttrib))
/* 247 */       .addGroup(layout.createParallelGroup()
/* 248 */         .addComponent((Component)this.pnlLevel));
/*     */     
/* 250 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 252 */     layout.linkSize(0, new Component[] { (Component)this.pnlAttrib, (Component)this.pnlLevel });
/*     */ 
/*     */ 
/*     */     
/* 256 */     return panel;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 260 */     this.pnlRarity.clear();
/* 261 */     this.pnlDamage.clear();
/* 262 */     this.pnlAttrib.clear();
/* 263 */     this.pnlLevel.clear();
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 267 */     this.pnlRarity.addCriteria(criteria);
/* 268 */     this.pnlDamage.addCriteria(criteria);
/* 269 */     this.pnlAttrib.addCriteria(criteria);
/* 270 */     this.pnlLevel.addCriteria(criteria);
/*     */     
/* 272 */     criteria.selMode = SelectionCriteria.SelectionMode.AFFIX;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\AffixSelectionPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */