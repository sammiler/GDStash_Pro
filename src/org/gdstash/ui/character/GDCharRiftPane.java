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
/*     */ import org.gdstash.ui.util.GDAbstractRiftPane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDCharRiftPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private GDAbstractRiftPane pnlAct1;
/*     */   private GDAbstractRiftPane pnlAct2;
/*     */   private GDAbstractRiftPane pnlAct3;
/*     */   private GDAbstractRiftPane pnlAct4;
/*     */   private GDAbstractRiftPane pnlAct5;
/*     */   private GDAbstractRiftPane pnlAct6;
/*     */   private int difficulty;
/*     */   
/*     */   public GDCharRiftPane(int difficulty) {
/*  39 */     this.difficulty = difficulty;
/*     */     
/*  41 */     adjustUI();
/*     */     
/*  43 */     JPanel pnlRift = buildRiftPanel();
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
/*     */         
/*  64 */         .addComponent(pnlRift));
/*  65 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  68 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  71 */     vGroup
/*     */ 
/*     */       
/*  74 */       .addGroup(layout.createParallelGroup()
/*  75 */         .addComponent(pnlRift));
/*     */     
/*  77 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  82 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*     */ 
/*     */ 
/*     */     
/*  86 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */ 
/*     */     
/*  89 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  90 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  91 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */ 
/*     */ 
/*     */     
/*  95 */     setBorder(compound);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     if (this.pnlAct1 == null) {
/* 104 */       this.pnlAct1 = new GDCharRiftAct1Pane(this.difficulty, 1);
/*     */     } else {
/* 106 */       this.pnlAct1.adjustUI();
/*     */     } 
/*     */     
/* 109 */     if (this.pnlAct2 == null) {
/* 110 */       this.pnlAct2 = new GDCharRiftAct2Pane(this.difficulty, 1);
/*     */     } else {
/* 112 */       this.pnlAct2.adjustUI();
/*     */     } 
/*     */     
/* 115 */     if (this.pnlAct3 == null) {
/* 116 */       this.pnlAct3 = new GDCharRiftAct3Pane(this.difficulty, 1);
/*     */     } else {
/* 118 */       this.pnlAct3.adjustUI();
/*     */     } 
/*     */     
/* 121 */     if (this.pnlAct4 == null) {
/* 122 */       this.pnlAct4 = new GDCharRiftAct4Pane(this.difficulty, 1);
/*     */     } else {
/* 124 */       this.pnlAct4.adjustUI();
/*     */     } 
/*     */     
/* 127 */     if (this.pnlAct5 == null) {
/* 128 */       this.pnlAct5 = new GDCharRiftAct5Pane(this.difficulty, 1);
/*     */     } else {
/* 130 */       this.pnlAct5.adjustUI();
/*     */     } 
/*     */     
/* 133 */     if (this.pnlAct6 == null) {
/* 134 */       this.pnlAct6 = new GDCharRiftAct6Pane(this.difficulty, 1);
/*     */     } else {
/* 136 */       this.pnlAct6.adjustUI();
/*     */     } 
/*     */   }
/*     */   
/*     */   private JPanel buildRiftPanel() {
/* 141 */     JPanel panel = new JPanel();
/*     */     
/* 143 */     GroupLayout layout = null;
/* 144 */     GroupLayout.SequentialGroup hGroup = null;
/* 145 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 147 */     layout = new GroupLayout(panel);
/* 148 */     panel.setLayout(layout);
/*     */     
/* 150 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 153 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 156 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 159 */     if (GDStashFrame.expansionForgottenGods) {
/* 160 */       hGroup
/* 161 */         .addGroup(layout.createParallelGroup()
/* 162 */           .addComponent((Component)this.pnlAct1))
/* 163 */         .addGroup(layout.createParallelGroup()
/* 164 */           .addComponent((Component)this.pnlAct2))
/* 165 */         .addGroup(layout.createParallelGroup()
/* 166 */           .addComponent((Component)this.pnlAct3))
/* 167 */         .addGroup(layout.createParallelGroup()
/* 168 */           .addComponent((Component)this.pnlAct4))
/* 169 */         .addGroup(layout.createParallelGroup()
/* 170 */           .addComponent((Component)this.pnlAct5))
/* 171 */         .addGroup(layout.createParallelGroup()
/* 172 */           .addComponent((Component)this.pnlAct6));
/*     */     }
/* 174 */     else if (GDStashFrame.expansionAshesOfMalmouth) {
/* 175 */       hGroup
/* 176 */         .addGroup(layout.createParallelGroup()
/* 177 */           .addComponent((Component)this.pnlAct1))
/* 178 */         .addGroup(layout.createParallelGroup()
/* 179 */           .addComponent((Component)this.pnlAct2))
/* 180 */         .addGroup(layout.createParallelGroup()
/* 181 */           .addComponent((Component)this.pnlAct3))
/* 182 */         .addGroup(layout.createParallelGroup()
/* 183 */           .addComponent((Component)this.pnlAct4))
/* 184 */         .addGroup(layout.createParallelGroup()
/* 185 */           .addComponent((Component)this.pnlAct5));
/*     */     } else {
/* 187 */       hGroup
/* 188 */         .addGroup(layout.createParallelGroup()
/* 189 */           .addComponent((Component)this.pnlAct1))
/* 190 */         .addGroup(layout.createParallelGroup()
/* 191 */           .addComponent((Component)this.pnlAct2))
/* 192 */         .addGroup(layout.createParallelGroup()
/* 193 */           .addComponent((Component)this.pnlAct3))
/* 194 */         .addGroup(layout.createParallelGroup()
/* 195 */           .addComponent((Component)this.pnlAct4));
/*     */     } 
/*     */ 
/*     */     
/* 199 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 202 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 205 */     if (GDStashFrame.expansionForgottenGods) {
/* 206 */       vGroup
/* 207 */         .addGroup(layout.createParallelGroup()
/* 208 */           .addComponent((Component)this.pnlAct1)
/* 209 */           .addComponent((Component)this.pnlAct2)
/* 210 */           .addComponent((Component)this.pnlAct3)
/* 211 */           .addComponent((Component)this.pnlAct4)
/* 212 */           .addComponent((Component)this.pnlAct5)
/* 213 */           .addComponent((Component)this.pnlAct6));
/*     */     }
/* 215 */     else if (GDStashFrame.expansionAshesOfMalmouth) {
/* 216 */       vGroup
/* 217 */         .addGroup(layout.createParallelGroup()
/* 218 */           .addComponent((Component)this.pnlAct1)
/* 219 */           .addComponent((Component)this.pnlAct2)
/* 220 */           .addComponent((Component)this.pnlAct3)
/* 221 */           .addComponent((Component)this.pnlAct4)
/* 222 */           .addComponent((Component)this.pnlAct5));
/*     */     } else {
/* 224 */       vGroup
/* 225 */         .addGroup(layout.createParallelGroup()
/* 226 */           .addComponent((Component)this.pnlAct1)
/* 227 */           .addComponent((Component)this.pnlAct2)
/* 228 */           .addComponent((Component)this.pnlAct3)
/* 229 */           .addComponent((Component)this.pnlAct4));
/*     */     } 
/*     */ 
/*     */     
/* 233 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 235 */     layout.linkSize(0, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct2 });
/* 236 */     layout.linkSize(0, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct3 });
/* 237 */     layout.linkSize(0, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct4 });
/* 238 */     if (GDStashFrame.expansionAshesOfMalmouth) layout.linkSize(0, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct5 }); 
/* 239 */     if (GDStashFrame.expansionForgottenGods) layout.linkSize(0, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct6 });
/*     */     
/* 241 */     layout.linkSize(1, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct2 });
/* 242 */     layout.linkSize(1, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct3 });
/* 243 */     layout.linkSize(1, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct4 });
/* 244 */     if (GDStashFrame.expansionAshesOfMalmouth) layout.linkSize(1, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct5 }); 
/* 245 */     if (GDStashFrame.expansionForgottenGods) layout.linkSize(1, new Component[] { (Component)this.pnlAct1, (Component)this.pnlAct6 });
/*     */     
/* 247 */     return panel;
/*     */   }
/*     */   
/*     */   public List<GDCharUID> getRiftList(boolean found) {
/* 251 */     List<GDCharUID> listAll = new LinkedList<>();
/*     */     
/* 253 */     List<GDCharUID> list = null;
/*     */     
/* 255 */     list = this.pnlAct1.getRiftList(found);
/* 256 */     if (list != null) listAll.addAll(list);
/*     */     
/* 258 */     list = this.pnlAct2.getRiftList(found);
/* 259 */     if (list != null) listAll.addAll(list);
/*     */     
/* 261 */     list = this.pnlAct3.getRiftList(found);
/* 262 */     if (list != null) listAll.addAll(list);
/*     */     
/* 264 */     list = this.pnlAct4.getRiftList(found);
/* 265 */     if (list != null) listAll.addAll(list);
/*     */     
/* 267 */     list = this.pnlAct5.getRiftList(found);
/* 268 */     if (list != null) listAll.addAll(list);
/*     */     
/* 270 */     list = this.pnlAct6.getRiftList(found);
/* 271 */     if (list != null) listAll.addAll(list);
/*     */     
/* 273 */     return listAll;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 278 */     this.pnlAct1.setChar(gdc);
/* 279 */     this.pnlAct2.setChar(gdc);
/* 280 */     this.pnlAct3.setChar(gdc);
/* 281 */     this.pnlAct4.setChar(gdc);
/* 282 */     this.pnlAct5.setChar(gdc);
/* 283 */     this.pnlAct6.setChar(gdc);
/*     */   }
/*     */   
/*     */   public void updateChar(GDChar gdc) {
/* 287 */     if (gdc == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 293 */     boolean changed = false;
/*     */     
/* 295 */     changed = (changed || this.pnlAct1.hasChanged());
/* 296 */     changed = (changed || this.pnlAct2.hasChanged());
/* 297 */     changed = (changed || this.pnlAct3.hasChanged());
/* 298 */     changed = (changed || this.pnlAct4.hasChanged());
/* 299 */     changed = (changed || this.pnlAct5.hasChanged());
/* 300 */     changed = (changed || this.pnlAct6.hasChanged());
/*     */     
/* 302 */     if (!changed)
/*     */       return; 
/* 304 */     List<GDCharUID> listAddAll = getRiftList(true);
/* 305 */     List<GDCharUID> listRemoveAll = getRiftList(false);
/*     */     
/* 307 */     gdc.setRiftList(this.difficulty, listAddAll, listRemoveAll);
/*     */     
/* 309 */     this.pnlAct1.setChanged(false);
/* 310 */     this.pnlAct2.setChanged(false);
/* 311 */     this.pnlAct3.setChanged(false);
/* 312 */     this.pnlAct4.setChanged(false);
/* 313 */     this.pnlAct5.setChanged(false);
/* 314 */     this.pnlAct6.setChanged(false);
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\character\GDCharRiftPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */