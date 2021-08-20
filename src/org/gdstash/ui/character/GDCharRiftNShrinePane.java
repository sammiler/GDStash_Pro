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
/*     */ import org.gdstash.ui.GDStashFrame;
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
/*     */ 
/*     */ 
/*     */ public class GDCharRiftNShrinePane
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
/*     */   public GDCharRiftNShrinePane(int difficulty) {
/*  46 */     this.difficulty = difficulty;
/*     */     
/*  48 */     adjustUI();
/*     */     
/*  50 */     JPanel pnlMain = buildMainPanel();
/*     */     
/*  52 */     GroupLayout layout = null;
/*  53 */     GroupLayout.SequentialGroup hGroup = null;
/*  54 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  56 */     layout = new GroupLayout((Container)this);
/*  57 */     setLayout(layout);
/*     */     
/*  59 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/*  62 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/*  65 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  68 */     hGroup
/*  69 */       .addGroup(layout.createParallelGroup()
/*     */         
/*  71 */         .addComponent(pnlMain));
/*  72 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  75 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  78 */     vGroup
/*     */ 
/*     */       
/*  81 */       .addGroup(layout.createParallelGroup()
/*  82 */         .addComponent(pnlMain));
/*     */     
/*  84 */     layout.setVerticalGroup(vGroup);
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
/*  96 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  97 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  98 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */ 
/*     */ 
/*     */     
/* 102 */     setBorder(compound);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     if (this.pnlRift1 == null) {
/* 111 */       this.pnlRift1 = new GDCharRiftAct1Pane(this.difficulty, 1);
/*     */     } else {
/* 113 */       this.pnlRift1.adjustUI();
/*     */     } 
/*     */     
/* 116 */     if (this.pnlRift2 == null) {
/* 117 */       this.pnlRift2 = new GDCharRiftAct2Pane(this.difficulty, 1);
/*     */     } else {
/* 119 */       this.pnlRift2.adjustUI();
/*     */     } 
/*     */     
/* 122 */     if (this.pnlRift3 == null) {
/* 123 */       this.pnlRift3 = new GDCharRiftAct3Pane(this.difficulty, 1);
/*     */     } else {
/* 125 */       this.pnlRift3.adjustUI();
/*     */     } 
/*     */     
/* 128 */     if (this.pnlRift4 == null) {
/* 129 */       this.pnlRift4 = new GDCharRiftAct4Pane(this.difficulty, 1);
/*     */     } else {
/* 131 */       this.pnlRift4.adjustUI();
/*     */     } 
/*     */     
/* 134 */     if (this.pnlRift5 == null) {
/* 135 */       this.pnlRift5 = new GDCharRiftAct5Pane(this.difficulty, 1);
/*     */     } else {
/* 137 */       this.pnlRift5.adjustUI();
/*     */     } 
/*     */     
/* 140 */     if (this.pnlRift6 == null) {
/* 141 */       this.pnlRift6 = new GDCharRiftAct6Pane(this.difficulty, 1);
/*     */     } else {
/* 143 */       this.pnlRift6.adjustUI();
/*     */     } 
/*     */     
/* 146 */     if (this.pnlShrine1 == null) {
/* 147 */       this.pnlShrine1 = new GDCharShrineAct1Pane(this.difficulty, 1);
/*     */     } else {
/* 149 */       this.pnlShrine1.adjustUI();
/*     */     } 
/*     */     
/* 152 */     if (this.pnlShrine2 == null) {
/* 153 */       this.pnlShrine2 = new GDCharShrineAct2Pane(this.difficulty, 1);
/*     */     } else {
/* 155 */       this.pnlShrine2.adjustUI();
/*     */     } 
/*     */     
/* 158 */     if (this.pnlShrine3 == null) {
/* 159 */       this.pnlShrine3 = new GDCharShrineAct3Pane(this.difficulty, 1);
/*     */     } else {
/* 161 */       this.pnlShrine3.adjustUI();
/*     */     } 
/*     */     
/* 164 */     if (this.pnlShrine4 == null) {
/* 165 */       this.pnlShrine4 = new GDCharShrineAct4Pane(this.difficulty, 1);
/*     */     } else {
/* 167 */       this.pnlShrine4.adjustUI();
/*     */     } 
/*     */     
/* 170 */     if (this.pnlShrine5 == null) {
/* 171 */       this.pnlShrine5 = new GDCharShrineAct5Pane(this.difficulty, 1);
/*     */     } else {
/* 173 */       this.pnlShrine5.adjustUI();
/*     */     } 
/*     */     
/* 176 */     if (this.pnlShrine6 == null) {
/* 177 */       this.pnlShrine6 = new GDCharShrineAct6Pane(this.difficulty, 1);
/*     */     } else {
/* 179 */       this.pnlShrine6.adjustUI();
/*     */     } 
/*     */   }
/*     */   
/*     */   private JPanel buildMainPanel() {
/* 184 */     JPanel panel = new JPanel();
/*     */     
/* 186 */     GroupLayout layout = null;
/* 187 */     GroupLayout.SequentialGroup hGroup = null;
/* 188 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 190 */     layout = new GroupLayout(panel);
/* 191 */     panel.setLayout(layout);
/*     */     
/* 193 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 196 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 199 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 202 */     hGroup
/* 203 */       .addGroup(layout.createParallelGroup()
/* 204 */         .addComponent((Component)this.pnlRift1)
/* 205 */         .addComponent((Component)this.pnlShrine1))
/* 206 */       .addGroup(layout.createParallelGroup()
/* 207 */         .addComponent((Component)this.pnlRift2)
/* 208 */         .addComponent((Component)this.pnlShrine2))
/* 209 */       .addGroup(layout.createParallelGroup()
/* 210 */         .addComponent((Component)this.pnlRift3)
/* 211 */         .addComponent((Component)this.pnlShrine3))
/* 212 */       .addGroup(layout.createParallelGroup()
/* 213 */         .addComponent((Component)this.pnlRift4)
/* 214 */         .addComponent((Component)this.pnlShrine4))
/* 215 */       .addGroup(layout.createParallelGroup()
/* 216 */         .addComponent((Component)this.pnlRift5)
/* 217 */         .addComponent((Component)this.pnlShrine5))
/* 218 */       .addGroup(layout.createParallelGroup()
/* 219 */         .addComponent((Component)this.pnlRift6)
/* 220 */         .addComponent((Component)this.pnlShrine6));
/*     */     
/* 222 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 225 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 228 */     vGroup
/* 229 */       .addGroup(layout.createParallelGroup()
/* 230 */         .addComponent((Component)this.pnlRift1)
/* 231 */         .addComponent((Component)this.pnlRift2)
/* 232 */         .addComponent((Component)this.pnlRift3)
/* 233 */         .addComponent((Component)this.pnlRift4)
/* 234 */         .addComponent((Component)this.pnlRift5)
/* 235 */         .addComponent((Component)this.pnlRift6))
/* 236 */       .addGroup(layout.createParallelGroup()
/* 237 */         .addComponent((Component)this.pnlShrine1)
/* 238 */         .addComponent((Component)this.pnlShrine2)
/* 239 */         .addComponent((Component)this.pnlShrine3)
/* 240 */         .addComponent((Component)this.pnlShrine4)
/* 241 */         .addComponent((Component)this.pnlShrine5)
/* 242 */         .addComponent((Component)this.pnlShrine6));
/*     */     
/* 244 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 246 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift2 });
/* 247 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift3 });
/* 248 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift4 });
/* 249 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift5 });
/* 250 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift6 });
/*     */     
/* 252 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlShrine1 });
/* 253 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlShrine2 });
/* 254 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlShrine3 });
/* 255 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlShrine4 });
/* 256 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlShrine5 });
/* 257 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlShrine6 });
/*     */     
/* 259 */     layout.linkSize(1, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift2 });
/* 260 */     layout.linkSize(1, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift3 });
/* 261 */     layout.linkSize(1, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift4 });
/* 262 */     layout.linkSize(1, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift5 });
/* 263 */     layout.linkSize(1, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift6 });
/*     */     
/* 265 */     layout.linkSize(1, new Component[] { (Component)this.pnlShrine1, (Component)this.pnlShrine2 });
/* 266 */     layout.linkSize(1, new Component[] { (Component)this.pnlShrine1, (Component)this.pnlShrine3 });
/* 267 */     layout.linkSize(1, new Component[] { (Component)this.pnlShrine1, (Component)this.pnlShrine4 });
/* 268 */     layout.linkSize(1, new Component[] { (Component)this.pnlShrine1, (Component)this.pnlShrine5 });
/* 269 */     layout.linkSize(1, new Component[] { (Component)this.pnlShrine1, (Component)this.pnlShrine6 });
/*     */     
/* 271 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildMainPanel_old() {
/* 275 */     JPanel panel = new JPanel();
/*     */     
/* 277 */     GroupLayout layout = null;
/* 278 */     GroupLayout.SequentialGroup hGroup = null;
/* 279 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 281 */     layout = new GroupLayout(panel);
/* 282 */     panel.setLayout(layout);
/*     */     
/* 284 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 287 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 290 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 293 */     if (GDStashFrame.expansionForgottenGods) {
/* 294 */       hGroup
/* 295 */         .addGroup(layout.createParallelGroup()
/* 296 */           .addComponent((Component)this.pnlRift1)
/* 297 */           .addComponent((Component)this.pnlShrine1))
/* 298 */         .addGroup(layout.createParallelGroup()
/* 299 */           .addComponent((Component)this.pnlRift2)
/* 300 */           .addComponent((Component)this.pnlShrine2))
/* 301 */         .addGroup(layout.createParallelGroup()
/* 302 */           .addComponent((Component)this.pnlRift3)
/* 303 */           .addComponent((Component)this.pnlShrine3))
/* 304 */         .addGroup(layout.createParallelGroup()
/* 305 */           .addComponent((Component)this.pnlRift4)
/* 306 */           .addComponent((Component)this.pnlShrine4))
/* 307 */         .addGroup(layout.createParallelGroup()
/* 308 */           .addComponent((Component)this.pnlRift5)
/* 309 */           .addComponent((Component)this.pnlShrine5))
/* 310 */         .addGroup(layout.createParallelGroup()
/* 311 */           .addComponent((Component)this.pnlRift6)
/* 312 */           .addComponent((Component)this.pnlShrine6));
/*     */     }
/* 314 */     else if (GDStashFrame.expansionAshesOfMalmouth) {
/* 315 */       hGroup
/* 316 */         .addGroup(layout.createParallelGroup()
/* 317 */           .addComponent((Component)this.pnlRift1)
/* 318 */           .addComponent((Component)this.pnlShrine1))
/* 319 */         .addGroup(layout.createParallelGroup()
/* 320 */           .addComponent((Component)this.pnlRift2)
/* 321 */           .addComponent((Component)this.pnlShrine2))
/* 322 */         .addGroup(layout.createParallelGroup()
/* 323 */           .addComponent((Component)this.pnlRift3)
/* 324 */           .addComponent((Component)this.pnlShrine3))
/* 325 */         .addGroup(layout.createParallelGroup()
/* 326 */           .addComponent((Component)this.pnlRift4)
/* 327 */           .addComponent((Component)this.pnlShrine4))
/* 328 */         .addGroup(layout.createParallelGroup()
/* 329 */           .addComponent((Component)this.pnlRift5)
/* 330 */           .addComponent((Component)this.pnlShrine5));
/*     */     } else {
/* 332 */       hGroup
/* 333 */         .addGroup(layout.createParallelGroup()
/* 334 */           .addComponent((Component)this.pnlRift1)
/* 335 */           .addComponent((Component)this.pnlShrine1))
/* 336 */         .addGroup(layout.createParallelGroup()
/* 337 */           .addComponent((Component)this.pnlRift2)
/* 338 */           .addComponent((Component)this.pnlShrine2))
/* 339 */         .addGroup(layout.createParallelGroup()
/* 340 */           .addComponent((Component)this.pnlRift3)
/* 341 */           .addComponent((Component)this.pnlShrine3))
/* 342 */         .addGroup(layout.createParallelGroup()
/* 343 */           .addComponent((Component)this.pnlRift4)
/* 344 */           .addComponent((Component)this.pnlShrine4));
/*     */     } 
/*     */ 
/*     */     
/* 348 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 351 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 354 */     if (GDStashFrame.expansionForgottenGods) {
/* 355 */       vGroup
/* 356 */         .addGroup(layout.createParallelGroup()
/* 357 */           .addComponent((Component)this.pnlRift1)
/* 358 */           .addComponent((Component)this.pnlRift2)
/* 359 */           .addComponent((Component)this.pnlRift3)
/* 360 */           .addComponent((Component)this.pnlRift4)
/* 361 */           .addComponent((Component)this.pnlRift5)
/* 362 */           .addComponent((Component)this.pnlRift6))
/* 363 */         .addGroup(layout.createParallelGroup()
/* 364 */           .addComponent((Component)this.pnlShrine1)
/* 365 */           .addComponent((Component)this.pnlShrine2)
/* 366 */           .addComponent((Component)this.pnlShrine3)
/* 367 */           .addComponent((Component)this.pnlShrine4)
/* 368 */           .addComponent((Component)this.pnlShrine5)
/* 369 */           .addComponent((Component)this.pnlShrine6));
/*     */     }
/* 371 */     else if (GDStashFrame.expansionAshesOfMalmouth) {
/* 372 */       vGroup
/* 373 */         .addGroup(layout.createParallelGroup()
/* 374 */           .addComponent((Component)this.pnlRift1)
/* 375 */           .addComponent((Component)this.pnlRift2)
/* 376 */           .addComponent((Component)this.pnlRift3)
/* 377 */           .addComponent((Component)this.pnlRift4)
/* 378 */           .addComponent((Component)this.pnlRift5))
/* 379 */         .addGroup(layout.createParallelGroup()
/* 380 */           .addComponent((Component)this.pnlShrine1)
/* 381 */           .addComponent((Component)this.pnlShrine2)
/* 382 */           .addComponent((Component)this.pnlShrine3)
/* 383 */           .addComponent((Component)this.pnlShrine4)
/* 384 */           .addComponent((Component)this.pnlShrine5));
/*     */     } else {
/* 386 */       vGroup
/* 387 */         .addGroup(layout.createParallelGroup()
/* 388 */           .addComponent((Component)this.pnlRift1)
/* 389 */           .addComponent((Component)this.pnlRift2)
/* 390 */           .addComponent((Component)this.pnlRift3)
/* 391 */           .addComponent((Component)this.pnlRift4))
/* 392 */         .addGroup(layout.createParallelGroup()
/* 393 */           .addComponent((Component)this.pnlShrine1)
/* 394 */           .addComponent((Component)this.pnlShrine2)
/* 395 */           .addComponent((Component)this.pnlShrine3)
/* 396 */           .addComponent((Component)this.pnlShrine4));
/*     */     } 
/*     */ 
/*     */     
/* 400 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 402 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift2 });
/* 403 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift3 });
/* 404 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift4 });
/* 405 */     if (GDStashFrame.expansionAshesOfMalmouth) layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift5 }); 
/* 406 */     if (GDStashFrame.expansionForgottenGods) layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift6 });
/*     */     
/* 408 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlShrine1 });
/* 409 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlShrine2 });
/* 410 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlShrine3 });
/* 411 */     layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlShrine4 });
/* 412 */     if (GDStashFrame.expansionAshesOfMalmouth) layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlShrine5 }); 
/* 413 */     if (GDStashFrame.expansionForgottenGods) layout.linkSize(0, new Component[] { (Component)this.pnlRift1, (Component)this.pnlShrine6 });
/*     */     
/* 415 */     layout.linkSize(1, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift2 });
/* 416 */     layout.linkSize(1, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift3 });
/* 417 */     layout.linkSize(1, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift4 });
/* 418 */     if (GDStashFrame.expansionAshesOfMalmouth) layout.linkSize(1, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift5 }); 
/* 419 */     if (GDStashFrame.expansionForgottenGods) layout.linkSize(1, new Component[] { (Component)this.pnlRift1, (Component)this.pnlRift6 });
/*     */     
/* 421 */     layout.linkSize(1, new Component[] { (Component)this.pnlShrine1, (Component)this.pnlShrine2 });
/* 422 */     layout.linkSize(1, new Component[] { (Component)this.pnlShrine1, (Component)this.pnlShrine3 });
/* 423 */     layout.linkSize(1, new Component[] { (Component)this.pnlShrine1, (Component)this.pnlShrine4 });
/* 424 */     if (GDStashFrame.expansionAshesOfMalmouth) layout.linkSize(1, new Component[] { (Component)this.pnlShrine1, (Component)this.pnlShrine5 }); 
/* 425 */     if (GDStashFrame.expansionForgottenGods) layout.linkSize(1, new Component[] { (Component)this.pnlShrine1, (Component)this.pnlShrine6 });
/*     */     
/* 427 */     return panel;
/*     */   }
/*     */   
/*     */   public List<GDCharUID> getRiftList(boolean found) {
/* 431 */     List<GDCharUID> listAll = new LinkedList<>();
/*     */     
/* 433 */     List<GDCharUID> list = null;
/*     */     
/* 435 */     list = this.pnlRift1.getRiftList(found);
/* 436 */     if (list != null) listAll.addAll(list);
/*     */     
/* 438 */     list = this.pnlRift2.getRiftList(found);
/* 439 */     if (list != null) listAll.addAll(list);
/*     */     
/* 441 */     list = this.pnlRift3.getRiftList(found);
/* 442 */     if (list != null) listAll.addAll(list);
/*     */     
/* 444 */     list = this.pnlRift4.getRiftList(found);
/* 445 */     if (list != null) listAll.addAll(list);
/*     */     
/* 447 */     list = this.pnlRift5.getRiftList(found);
/* 448 */     if (list != null) listAll.addAll(list);
/*     */     
/* 450 */     list = this.pnlRift6.getRiftList(found);
/* 451 */     if (list != null) listAll.addAll(list);
/*     */     
/* 453 */     return listAll;
/*     */   }
/*     */   
/*     */   public List<GDCharUID> getShrineList(boolean found) {
/* 457 */     List<GDCharUID> listAll = new LinkedList<>();
/*     */     
/* 459 */     List<GDCharUID> list = null;
/*     */     
/* 461 */     list = this.pnlShrine1.getShrineList(found);
/* 462 */     if (list != null) listAll.addAll(list);
/*     */     
/* 464 */     list = this.pnlShrine2.getShrineList(found);
/* 465 */     if (list != null) listAll.addAll(list);
/*     */     
/* 467 */     list = this.pnlShrine3.getShrineList(found);
/* 468 */     if (list != null) listAll.addAll(list);
/*     */     
/* 470 */     list = this.pnlShrine4.getShrineList(found);
/* 471 */     if (list != null) listAll.addAll(list);
/*     */     
/* 473 */     list = this.pnlShrine5.getShrineList(found);
/* 474 */     if (list != null) listAll.addAll(list);
/*     */     
/* 476 */     list = this.pnlShrine6.getShrineList(found);
/* 477 */     if (list != null) listAll.addAll(list);
/*     */     
/* 479 */     return listAll;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 484 */     this.pnlRift1.setChar(gdc);
/* 485 */     this.pnlRift2.setChar(gdc);
/* 486 */     this.pnlRift3.setChar(gdc);
/* 487 */     this.pnlRift4.setChar(gdc);
/* 488 */     this.pnlRift5.setChar(gdc);
/* 489 */     this.pnlRift6.setChar(gdc);
/*     */     
/* 491 */     this.pnlShrine1.setChar(gdc);
/* 492 */     this.pnlShrine2.setChar(gdc);
/* 493 */     this.pnlShrine3.setChar(gdc);
/* 494 */     this.pnlShrine4.setChar(gdc);
/* 495 */     this.pnlShrine5.setChar(gdc);
/* 496 */     this.pnlShrine6.setChar(gdc);
/*     */   }
/*     */   
/*     */   public void updateChar(GDChar gdc) {
/* 500 */     if (gdc == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 506 */     boolean changed = false;
/*     */     
/* 508 */     changed = (changed || this.pnlRift1.hasChanged());
/* 509 */     changed = (changed || this.pnlRift2.hasChanged());
/* 510 */     changed = (changed || this.pnlRift3.hasChanged());
/* 511 */     changed = (changed || this.pnlRift4.hasChanged());
/* 512 */     changed = (changed || this.pnlRift5.hasChanged());
/* 513 */     changed = (changed || this.pnlRift6.hasChanged());
/*     */     
/* 515 */     changed = (changed || this.pnlShrine1.hasChanged());
/* 516 */     changed = (changed || this.pnlShrine2.hasChanged());
/* 517 */     changed = (changed || this.pnlShrine3.hasChanged());
/* 518 */     changed = (changed || this.pnlShrine4.hasChanged());
/* 519 */     changed = (changed || this.pnlShrine5.hasChanged());
/* 520 */     changed = (changed || this.pnlShrine6.hasChanged());
/*     */     
/* 522 */     if (!changed)
/*     */       return; 
/* 524 */     List<GDCharUID> listAddRifts = getRiftList(true);
/* 525 */     List<GDCharUID> listRemoveRifts = getRiftList(false);
/*     */     
/* 527 */     gdc.setRiftList(this.difficulty, listAddRifts, listRemoveRifts);
/*     */     
/* 529 */     List<GDCharUID> listAddShrines = getShrineList(true);
/* 530 */     List<GDCharUID> listRemoveShrines = getShrineList(false);
/*     */     
/* 532 */     gdc.setRestoredShrinesList(this.difficulty, listAddShrines, listRemoveShrines);
/*     */     
/* 534 */     this.pnlRift1.setChanged(false);
/* 535 */     this.pnlRift2.setChanged(false);
/* 536 */     this.pnlRift3.setChanged(false);
/* 537 */     this.pnlRift4.setChanged(false);
/* 538 */     this.pnlRift5.setChanged(false);
/* 539 */     this.pnlRift6.setChanged(false);
/*     */     
/* 541 */     this.pnlShrine1.setChanged(false);
/* 542 */     this.pnlShrine2.setChanged(false);
/* 543 */     this.pnlShrine3.setChanged(false);
/* 544 */     this.pnlShrine4.setChanged(false);
/* 545 */     this.pnlShrine5.setChanged(false);
/* 546 */     this.pnlShrine6.setChanged(false);
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\character\GDCharRiftNShrinePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */