/*     */ package org.gdstash.ui.character;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.character.GDCharUID;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ import org.gdstash.ui.util.GDAbstractShrinePane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDCharShrinePane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private GDAbstractShrinePane pnlAct1;
/*     */   private GDAbstractShrinePane pnlAct2;
/*     */   private GDAbstractShrinePane pnlAct3;
/*     */   private GDAbstractShrinePane pnlAct4;
/*     */   private GDAbstractShrinePane pnlAct5;
/*     */   private GDAbstractShrinePane pnlAct6;
/*     */   private int difficulty;
/*     */   
/*     */   public GDCharShrinePane(int difficulty) {
/*  39 */     this.difficulty = difficulty;
/*     */     
/*  41 */     adjustUI();
/*     */     
/*  43 */     JPanel pnlRift = buildShrinePanel();
/*     */     
/*  45 */     GroupLayout layout = null;
/*  46 */     GroupLayout.SequentialGroup hGroup = null;
/*  47 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  49 */     layout = new GroupLayout((Container)this);
/*  50 */     setLayout(layout);
/*     */     
/*  52 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/*  55 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/*  58 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  61 */     hGroup
/*  62 */       .addGroup(layout.createParallelGroup()
/*  63 */         .addComponent(pnlRift));
/*  64 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  67 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  70 */     vGroup
/*  71 */       .addGroup(layout.createParallelGroup()
/*  72 */         .addComponent(pnlRift));
/*     */     
/*  74 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  79 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*     */ 
/*     */ 
/*     */     
/*  83 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */ 
/*     */     
/*  86 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  87 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  88 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */ 
/*     */ 
/*     */     
/*  92 */     setBorder(compound);
/*     */     
/*  94 */     if (this.pnlAct1 == null) {
/*  95 */       this.pnlAct1 = new GDCharShrineAct1Pane(this.difficulty, 1);
/*     */     } else {
/*  97 */       this.pnlAct1.adjustUI();
/*     */     } 
/*     */     
/* 100 */     if (this.pnlAct2 == null) {
/* 101 */       this.pnlAct2 = new GDCharShrineAct2Pane(this.difficulty, 1);
/*     */     } else {
/* 103 */       this.pnlAct2.adjustUI();
/*     */     } 
/*     */     
/* 106 */     if (this.pnlAct3 == null) {
/* 107 */       this.pnlAct3 = new GDCharShrineAct3Pane(this.difficulty, 1);
/*     */     } else {
/* 109 */       this.pnlAct3.adjustUI();
/*     */     } 
/*     */     
/* 112 */     if (this.pnlAct4 == null) {
/* 113 */       this.pnlAct4 = new GDCharShrineAct4Pane(this.difficulty, 1);
/*     */     } else {
/* 115 */       this.pnlAct4.adjustUI();
/*     */     } 
/*     */     
/* 118 */     if (this.pnlAct5 == null) {
/* 119 */       this.pnlAct5 = new GDCharShrineAct5Pane(this.difficulty, 1);
/*     */     } else {
/* 121 */       this.pnlAct5.adjustUI();
/*     */     } 
/*     */     
/* 124 */     if (this.pnlAct6 == null) {
/* 125 */       this.pnlAct6 = new GDCharShrineAct6Pane(this.difficulty, 1);
/*     */     } else {
/* 127 */       this.pnlAct6.adjustUI();
/*     */     } 
/*     */   }
/*     */   
/*     */   private JPanel buildShrinePanel() {
/* 132 */     JPanel panel = new JPanel();
/*     */     
/* 134 */     GroupLayout layout = null;
/* 135 */     GroupLayout.SequentialGroup hGroup = null;
/* 136 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 138 */     layout = new GroupLayout(panel);
/* 139 */     panel.setLayout(layout);
/*     */     
/* 141 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 144 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 147 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 150 */     if (GDStashFrame.expansionForgottenGods) {
/* 151 */       hGroup
/* 152 */         .addGroup(layout.createParallelGroup()
/* 153 */           .addComponent((Component)this.pnlAct1))
/* 154 */         .addGroup(layout.createParallelGroup()
/* 155 */           .addComponent((Component)this.pnlAct2))
/* 156 */         .addGroup(layout.createParallelGroup()
/* 157 */           .addComponent((Component)this.pnlAct3))
/* 158 */         .addGroup(layout.createParallelGroup()
/* 159 */           .addComponent((Component)this.pnlAct4))
/* 160 */         .addGroup(layout.createParallelGroup()
/* 161 */           .addComponent((Component)this.pnlAct5))
/* 162 */         .addGroup(layout.createParallelGroup()
/* 163 */           .addComponent((Component)this.pnlAct6));
/*     */     }
/* 165 */     else if (GDStashFrame.expansionAshesOfMalmouth) {
/* 166 */       hGroup
/* 167 */         .addGroup(layout.createParallelGroup()
/* 168 */           .addComponent((Component)this.pnlAct1))
/* 169 */         .addGroup(layout.createParallelGroup()
/* 170 */           .addComponent((Component)this.pnlAct2))
/* 171 */         .addGroup(layout.createParallelGroup()
/* 172 */           .addComponent((Component)this.pnlAct3))
/* 173 */         .addGroup(layout.createParallelGroup()
/* 174 */           .addComponent((Component)this.pnlAct4))
/* 175 */         .addGroup(layout.createParallelGroup()
/* 176 */           .addComponent((Component)this.pnlAct5));
/*     */     } else {
/* 178 */       hGroup
/* 179 */         .addGroup(layout.createParallelGroup()
/* 180 */           .addComponent((Component)this.pnlAct1))
/* 181 */         .addGroup(layout.createParallelGroup()
/* 182 */           .addComponent((Component)this.pnlAct2))
/* 183 */         .addGroup(layout.createParallelGroup()
/* 184 */           .addComponent((Component)this.pnlAct3))
/* 185 */         .addGroup(layout.createParallelGroup()
/* 186 */           .addComponent((Component)this.pnlAct4));
/*     */     } 
/*     */ 
/*     */     
/* 190 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 193 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 196 */     if (GDStashFrame.expansionForgottenGods) {
/* 197 */       vGroup
/* 198 */         .addGroup(layout.createParallelGroup()
/* 199 */           .addComponent((Component)this.pnlAct1)
/* 200 */           .addComponent((Component)this.pnlAct2)
/* 201 */           .addComponent((Component)this.pnlAct3)
/* 202 */           .addComponent((Component)this.pnlAct4)
/* 203 */           .addComponent((Component)this.pnlAct5)
/* 204 */           .addComponent((Component)this.pnlAct6));
/*     */     }
/* 206 */     else if (GDStashFrame.expansionAshesOfMalmouth) {
/* 207 */       vGroup
/* 208 */         .addGroup(layout.createParallelGroup()
/* 209 */           .addComponent((Component)this.pnlAct1)
/* 210 */           .addComponent((Component)this.pnlAct2)
/* 211 */           .addComponent((Component)this.pnlAct3)
/* 212 */           .addComponent((Component)this.pnlAct4)
/* 213 */           .addComponent((Component)this.pnlAct5));
/*     */     } else {
/* 215 */       vGroup
/* 216 */         .addGroup(layout.createParallelGroup()
/* 217 */           .addComponent((Component)this.pnlAct1)
/* 218 */           .addComponent((Component)this.pnlAct2)
/* 219 */           .addComponent((Component)this.pnlAct3)
/* 220 */           .addComponent((Component)this.pnlAct4));
/*     */     } 
/*     */ 
/*     */     
/* 224 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 226 */     layout.linkSize(0, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct2 });
/* 227 */     layout.linkSize(0, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct3 });
/* 228 */     layout.linkSize(0, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct4 });
/* 229 */     if (GDStashFrame.expansionAshesOfMalmouth) layout.linkSize(0, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct5 }); 
/* 230 */     if (GDStashFrame.expansionForgottenGods) layout.linkSize(0, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct6 });
/*     */     
/* 232 */     layout.linkSize(1, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct2 });
/* 233 */     layout.linkSize(1, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct3 });
/* 234 */     layout.linkSize(1, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct4 });
/* 235 */     if (GDStashFrame.expansionAshesOfMalmouth) layout.linkSize(1, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct5 }); 
/* 236 */     if (GDStashFrame.expansionForgottenGods) layout.linkSize(1, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct6 });
/*     */     
/* 238 */     return panel;
/*     */   }
/*     */   
/*     */   public List<GDCharUID> getShrineList(boolean found) {
/* 242 */     List<GDCharUID> listAll = new LinkedList<>();
/*     */     
/* 244 */     List<GDCharUID> list = null;
/*     */     
/* 246 */     list = this.pnlAct1.getShrineList(found);
/* 247 */     if (list != null) listAll.addAll(list);
/*     */     
/* 249 */     list = this.pnlAct2.getShrineList(found);
/* 250 */     if (list != null) listAll.addAll(list);
/*     */     
/* 252 */     list = this.pnlAct3.getShrineList(found);
/* 253 */     if (list != null) listAll.addAll(list);
/*     */     
/* 255 */     list = this.pnlAct4.getShrineList(found);
/* 256 */     if (list != null) listAll.addAll(list);
/*     */     
/* 258 */     list = this.pnlAct5.getShrineList(found);
/* 259 */     if (list != null) listAll.addAll(list);
/*     */     
/* 261 */     list = this.pnlAct6.getShrineList(found);
/* 262 */     if (list != null) listAll.addAll(list);
/*     */     
/* 264 */     return listAll;
/*     */   }
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 268 */     this.pnlAct1.setChar(gdc);
/* 269 */     this.pnlAct2.setChar(gdc);
/* 270 */     this.pnlAct3.setChar(gdc);
/* 271 */     this.pnlAct4.setChar(gdc);
/* 272 */     this.pnlAct5.setChar(gdc);
/* 273 */     this.pnlAct6.setChar(gdc);
/*     */   }
/*     */   
/*     */   public void updateChar(GDChar gdc) {
/* 277 */     if (gdc == null)
/*     */       return; 
/* 279 */     boolean changed = false;
/*     */     
/* 281 */     changed = (changed || this.pnlAct1.hasChanged());
/* 282 */     changed = (changed || this.pnlAct2.hasChanged());
/* 283 */     changed = (changed || this.pnlAct3.hasChanged());
/* 284 */     changed = (changed || this.pnlAct4.hasChanged());
/* 285 */     changed = (changed || this.pnlAct5.hasChanged());
/* 286 */     changed = (changed || this.pnlAct6.hasChanged());
/*     */     
/* 288 */     if (!changed)
/*     */       return; 
/* 290 */     List<GDCharUID> listAddAll = getShrineList(true);
/* 291 */     List<GDCharUID> listRemoveAll = getShrineList(false);
/*     */     
/* 293 */     gdc.setRestoredShrinesList(this.difficulty, listAddAll, listRemoveAll);
/*     */     
/* 295 */     this.pnlAct1.setChanged(false);
/* 296 */     this.pnlAct2.setChanged(false);
/* 297 */     this.pnlAct3.setChanged(false);
/* 298 */     this.pnlAct4.setChanged(false);
/* 299 */     this.pnlAct5.setChanged(false);
/* 300 */     this.pnlAct6.setChanged(false);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharShrinePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */