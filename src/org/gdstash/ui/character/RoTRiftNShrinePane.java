/*     */ package org.gdstash.ui.character;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.border.Border;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.character.GDCharUID;
/*     */ import org.gdstash.ui.util.GDAbstractRiftPane;
/*     */ import org.gdstash.ui.util.GDAbstractShrinePane;
/*     */ import org.gdstash.ui.util.GDAbstractUpdateCharPane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RoTRiftNShrinePane
/*     */   extends GDAbstractUpdateCharPane
/*     */ {
/*     */   private GDAbstractRiftPane pnlRift1;
/*     */   private GDAbstractRiftPane pnlRift2;
/*     */   private GDAbstractRiftPane pnlRift3;
/*     */   private GDAbstractRiftPane pnlRift4;
/*     */   private GDAbstractRiftPane pnlRift5;
/*     */   private GDAbstractRiftPane pnlRift6;
/*     */   private GDAbstractShrinePane pnlShrine1;
/*     */   private GDAbstractShrinePane pnlShrine2;
/*     */   private GDAbstractShrinePane pnlShrine3;
/*     */   private GDAbstractShrinePane pnlShrine4;
/*     */   private GDAbstractShrinePane pnlShrine5;
/*     */   private GDAbstractShrinePane pnlShrine6;
/*     */   private int difficulty;
/*     */   
/*     */   public RoTRiftNShrinePane(int difficulty) {
/*  43 */     this.difficulty = difficulty;
/*     */     
/*  45 */     adjustUI();
/*     */     
/*  47 */     JPanel pnlMain = buildMainPanel();
/*     */     
/*  49 */     GroupLayout layout = null;
/*  50 */     GroupLayout.SequentialGroup hGroup = null;
/*  51 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  53 */     layout = new GroupLayout((Container)this);
/*  54 */     setLayout(layout);
/*     */     
/*  56 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/*  59 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/*  62 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  65 */     hGroup
/*  66 */       .addGroup(layout.createParallelGroup()
/*     */         
/*  68 */         .addComponent(pnlMain));
/*  69 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  72 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  75 */     vGroup
/*     */ 
/*     */       
/*  78 */       .addGroup(layout.createParallelGroup()
/*  79 */         .addComponent(pnlMain));
/*     */     
/*  81 */     layout.setVerticalGroup(vGroup);
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
/*     */   public void adjustUI() {
/*  93 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  94 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  95 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */ 
/*     */ 
/*     */     
/*  99 */     setBorder(compound);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     if (this.pnlRift1 == null) {
/* 108 */       this.pnlRift1 = new RoTRiftAct1Pane(this.difficulty, 1);
/*     */     } else {
/* 110 */       this.pnlRift1.adjustUI();
/*     */     } 
/*     */     
/* 113 */     if (this.pnlRift2 == null) {
/* 114 */       this.pnlRift2 = new RoTRiftAct2Pane(this.difficulty, 1);
/*     */     } else {
/* 116 */       this.pnlRift2.adjustUI();
/*     */     } 
/*     */     
/* 119 */     if (this.pnlRift3 == null) {
/* 120 */       this.pnlRift3 = new RoTRiftAct3Pane(this.difficulty, 1);
/*     */     } else {
/* 122 */       this.pnlRift3.adjustUI();
/*     */     } 
/*     */     
/* 125 */     if (this.pnlRift4 == null) {
/* 126 */       this.pnlRift4 = new RoTRiftAct4Pane(this.difficulty, 1);
/*     */     } else {
/* 128 */       this.pnlRift4.adjustUI();
/*     */     } 
/*     */     
/* 131 */     if (this.pnlRift5 == null) {
/* 132 */       this.pnlRift5 = new RoTRiftAct5Pane(this.difficulty, 1);
/*     */     } else {
/* 134 */       this.pnlRift5.adjustUI();
/*     */     } 
/*     */     
/* 137 */     if (this.pnlRift6 == null) {
/* 138 */       this.pnlRift6 = new RoTRiftAct6Pane(this.difficulty, 1);
/*     */     } else {
/* 140 */       this.pnlRift6.adjustUI();
/*     */     } 
/*     */     
/* 143 */     if (this.pnlShrine1 == null) {
/* 144 */       this.pnlShrine1 = new GDCharShrineAct1Pane(this.difficulty, 1);
/*     */     } else {
/* 146 */       this.pnlShrine1.adjustUI();
/*     */     } 
/*     */     
/* 149 */     if (this.pnlShrine2 == null) {
/* 150 */       this.pnlShrine2 = new GDCharShrineAct2Pane(this.difficulty, 1);
/*     */     } else {
/* 152 */       this.pnlShrine2.adjustUI();
/*     */     } 
/*     */     
/* 155 */     if (this.pnlShrine3 == null) {
/* 156 */       this.pnlShrine3 = new GDCharShrineAct3Pane(this.difficulty, 1);
/*     */     } else {
/* 158 */       this.pnlShrine3.adjustUI();
/*     */     } 
/*     */     
/* 161 */     if (this.pnlShrine4 == null) {
/* 162 */       this.pnlShrine4 = new GDCharShrineAct4Pane(this.difficulty, 1);
/*     */     } else {
/* 164 */       this.pnlShrine4.adjustUI();
/*     */     } 
/*     */     
/* 167 */     if (this.pnlShrine5 == null) {
/* 168 */       this.pnlShrine5 = new GDCharShrineAct5Pane(this.difficulty, 1);
/*     */     } else {
/* 170 */       this.pnlShrine5.adjustUI();
/*     */     } 
/*     */     
/* 173 */     if (this.pnlShrine6 == null) {
/* 174 */       this.pnlShrine6 = new GDCharShrineAct6Pane(this.difficulty, 1);
/*     */     } else {
/* 176 */       this.pnlShrine6.adjustUI();
/*     */     } 
/*     */   }
/*     */   
/*     */   private JPanel buildMainPanel() {
/* 181 */     JPanel panel = new JPanel();
/*     */     
/* 183 */     GroupLayout layout = null;
/* 184 */     GroupLayout.SequentialGroup hGroup = null;
/* 185 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 187 */     layout = new GroupLayout(panel);
/* 188 */     panel.setLayout(layout);
/*     */     
/* 190 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 193 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 196 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 199 */     hGroup
/* 200 */       .addGroup(layout.createParallelGroup()
/* 201 */         .addComponent((Component)this.pnlRift1)
/* 202 */         .addComponent((Component)this.pnlShrine1))
/* 203 */       .addGroup(layout.createParallelGroup()
/* 204 */         .addComponent((Component)this.pnlRift2)
/* 205 */         .addComponent((Component)this.pnlShrine2))
/* 206 */       .addGroup(layout.createParallelGroup()
/* 207 */         .addComponent((Component)this.pnlRift3)
/* 208 */         .addComponent((Component)this.pnlShrine3))
/* 209 */       .addGroup(layout.createParallelGroup()
/* 210 */         .addComponent((Component)this.pnlRift4)
/* 211 */         .addComponent((Component)this.pnlShrine4))
/* 212 */       .addGroup(layout.createParallelGroup()
/* 213 */         .addComponent((Component)this.pnlRift5)
/* 214 */         .addComponent((Component)this.pnlShrine5))
/* 215 */       .addGroup(layout.createParallelGroup()
/* 216 */         .addComponent((Component)this.pnlRift6)
/* 217 */         .addComponent((Component)this.pnlShrine6));
/*     */     
/* 219 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 222 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 225 */     vGroup
/* 226 */       .addGroup(layout.createParallelGroup()
/* 227 */         .addComponent((Component)this.pnlRift1)
/* 228 */         .addComponent((Component)this.pnlRift2)
/* 229 */         .addComponent((Component)this.pnlRift3)
/* 230 */         .addComponent((Component)this.pnlRift4)
/* 231 */         .addComponent((Component)this.pnlRift5)
/* 232 */         .addComponent((Component)this.pnlRift6))
/* 233 */       .addGroup(layout.createParallelGroup()
/* 234 */         .addComponent((Component)this.pnlShrine1)
/* 235 */         .addComponent((Component)this.pnlShrine2)
/* 236 */         .addComponent((Component)this.pnlShrine3)
/* 237 */         .addComponent((Component)this.pnlShrine4)
/* 238 */         .addComponent((Component)this.pnlShrine5)
/* 239 */         .addComponent((Component)this.pnlShrine6));
/*     */     
/* 241 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 243 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift2 });
/* 244 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift3 });
/* 245 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift4 });
/* 246 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift5 });
/* 247 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift6 });
/*     */     
/* 249 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlShrine1 });
/* 250 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlShrine2 });
/* 251 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlShrine3 });
/* 252 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlShrine4 });
/* 253 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlShrine5 });
/* 254 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlShrine6 });
/*     */     
/* 256 */     layout.linkSize(1, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift2 });
/* 257 */     layout.linkSize(1, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift3 });
/* 258 */     layout.linkSize(1, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift4 });
/* 259 */     layout.linkSize(1, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift5 });
/* 260 */     layout.linkSize(1, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift6 });
/*     */     
/* 262 */     layout.linkSize(1, new Component[] { (Component)this.pnlShrine1, (Component)this.pnlShrine2 });
/* 263 */     layout.linkSize(1, new Component[] { (Component)this.pnlShrine1, (Component)this.pnlShrine3 });
/* 264 */     layout.linkSize(1, new Component[] { (Component)this.pnlShrine1, (Component)this.pnlShrine4 });
/* 265 */     layout.linkSize(1, new Component[] { (Component)this.pnlShrine1, (Component)this.pnlShrine5 });
/* 266 */     layout.linkSize(1, new Component[] { (Component)this.pnlShrine1, (Component)this.pnlShrine6 });
/*     */     
/* 268 */     return panel;
/*     */   }
/*     */   
/*     */   public List<GDCharUID> getRiftList(boolean found) {
/* 272 */     List<GDCharUID> listAll = new LinkedList<>();
/*     */     
/* 274 */     List<GDCharUID> list = null;
/*     */     
/* 276 */     list = this.pnlRift1.getRiftList(found);
/* 277 */     if (list != null) listAll.addAll(list);
/*     */     
/* 279 */     list = this.pnlRift2.getRiftList(found);
/* 280 */     if (list != null) listAll.addAll(list);
/*     */     
/* 282 */     list = this.pnlRift3.getRiftList(found);
/* 283 */     if (list != null) listAll.addAll(list);
/*     */     
/* 285 */     list = this.pnlRift4.getRiftList(found);
/* 286 */     if (list != null) listAll.addAll(list);
/*     */     
/* 288 */     list = this.pnlRift5.getRiftList(found);
/* 289 */     if (list != null) listAll.addAll(list);
/*     */     
/* 291 */     list = this.pnlRift6.getRiftList(found);
/* 292 */     if (list != null) listAll.addAll(list);
/*     */     
/* 294 */     return listAll;
/*     */   }
/*     */   
/*     */   public List<GDCharUID> getShrineList(boolean found) {
/* 298 */     List<GDCharUID> listAll = new LinkedList<>();
/*     */     
/* 300 */     List<GDCharUID> list = null;
/*     */     
/* 302 */     list = this.pnlShrine1.getShrineList(found);
/* 303 */     if (list != null) listAll.addAll(list);
/*     */     
/* 305 */     list = this.pnlShrine2.getShrineList(found);
/* 306 */     if (list != null) listAll.addAll(list);
/*     */     
/* 308 */     list = this.pnlShrine3.getShrineList(found);
/* 309 */     if (list != null) listAll.addAll(list);
/*     */     
/* 311 */     list = this.pnlShrine4.getShrineList(found);
/* 312 */     if (list != null) listAll.addAll(list);
/*     */     
/* 314 */     list = this.pnlShrine5.getShrineList(found);
/* 315 */     if (list != null) listAll.addAll(list);
/*     */     
/* 317 */     list = this.pnlShrine6.getShrineList(found);
/* 318 */     if (list != null) listAll.addAll(list);
/*     */     
/* 320 */     return listAll;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 325 */     this.pnlRift1.setChar(gdc);
/* 326 */     this.pnlRift2.setChar(gdc);
/* 327 */     this.pnlRift3.setChar(gdc);
/* 328 */     this.pnlRift4.setChar(gdc);
/* 329 */     this.pnlRift5.setChar(gdc);
/* 330 */     this.pnlRift6.setChar(gdc);
/*     */     
/* 332 */     this.pnlShrine1.setChar(gdc);
/* 333 */     this.pnlShrine2.setChar(gdc);
/* 334 */     this.pnlShrine3.setChar(gdc);
/* 335 */     this.pnlShrine4.setChar(gdc);
/* 336 */     this.pnlShrine5.setChar(gdc);
/* 337 */     this.pnlShrine6.setChar(gdc);
/*     */   }
/*     */   
/*     */   public void updateChar(GDChar gdc) {
/* 341 */     if (gdc == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 347 */     boolean changed = false;
/*     */     
/* 349 */     changed = (changed || this.pnlRift1.hasChanged());
/* 350 */     changed = (changed || this.pnlRift2.hasChanged());
/* 351 */     changed = (changed || this.pnlRift3.hasChanged());
/* 352 */     changed = (changed || this.pnlRift4.hasChanged());
/* 353 */     changed = (changed || this.pnlRift5.hasChanged());
/* 354 */     changed = (changed || this.pnlRift6.hasChanged());
/*     */     
/* 356 */     changed = (changed || this.pnlShrine1.hasChanged());
/* 357 */     changed = (changed || this.pnlShrine2.hasChanged());
/* 358 */     changed = (changed || this.pnlShrine3.hasChanged());
/* 359 */     changed = (changed || this.pnlShrine4.hasChanged());
/* 360 */     changed = (changed || this.pnlShrine5.hasChanged());
/* 361 */     changed = (changed || this.pnlShrine6.hasChanged());
/*     */     
/* 363 */     if (!changed)
/*     */       return; 
/* 365 */     List<GDCharUID> listAddRifts = getRiftList(true);
/* 366 */     List<GDCharUID> listRemoveRifts = getRiftList(false);
/*     */     
/* 368 */     gdc.setRiftList(this.difficulty, listAddRifts, listRemoveRifts);
/*     */     
/* 370 */     List<GDCharUID> listAddShrines = getShrineList(true);
/* 371 */     List<GDCharUID> listRemoveShrines = getShrineList(false);
/*     */     
/* 373 */     gdc.setRestoredShrinesList(this.difficulty, listAddRifts, listRemoveRifts);
/*     */     
/* 375 */     this.pnlRift1.setChanged(false);
/* 376 */     this.pnlRift2.setChanged(false);
/* 377 */     this.pnlRift3.setChanged(false);
/* 378 */     this.pnlRift4.setChanged(false);
/* 379 */     this.pnlRift5.setChanged(false);
/* 380 */     this.pnlRift6.setChanged(false);
/*     */     
/* 382 */     this.pnlShrine1.setChanged(false);
/* 383 */     this.pnlShrine2.setChanged(false);
/* 384 */     this.pnlShrine3.setChanged(false);
/* 385 */     this.pnlShrine4.setChanged(false);
/* 386 */     this.pnlShrine5.setChanged(false);
/* 387 */     this.pnlShrine6.setChanged(false);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\RoTRiftNShrinePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */