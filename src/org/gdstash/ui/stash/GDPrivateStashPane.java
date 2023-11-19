/*     */ package org.gdstash.ui.stash;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.UIManager;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.item.GDAbstractContainer;
/*     */ import org.gdstash.item.GDItem;
/*     */ import org.gdstash.item.GDItemContainer;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.GDUITransfer;
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
/*     */ public class GDPrivateStashPane
/*     */   extends GDContainerMapPane
/*     */   implements GDUIInventory
/*     */ {
/*     */   private GDStashFrame frame;
/*     */   private JTabbedPane tabPages;
/*     */   private GDContainerMapPane[] pnlPages;
/*     */   private int filledPages;
/*     */   private GDChar gdc;
/*     */   private GDItem selItem;
/*     */   private GDContainerMapPane selPage;
/*     */   private JPanel pnlMain;
/*     */   private GDUIInventory uiInventory;
/*     */   private GDUITransfer uiTransfer;
/*     */   private boolean blockEvents;
/*     */   
/*     */   public GDPrivateStashPane(GDChar gdc, GDStashFrame frame, GDUIInventory uiInventory, GDUITransfer uiTransfer) {
/*  49 */     super(uiInventory, uiTransfer, (BufferedImage)null);
/*     */     
/*  51 */     this.gdc = gdc;
/*  52 */     this.frame = frame;
/*  53 */     this.uiInventory = uiInventory;
/*  54 */     this.uiTransfer = uiTransfer;
/*     */     
/*  56 */     this.pnlPages = new GDContainerMapPane[6];
/*  57 */     this.filledPages = 0;
/*  58 */     this.selItem = null;
/*  59 */     this.selPage = null;
/*     */     
/*  61 */     this.blockEvents = false;
/*     */     
/*  63 */     adjustUI();
/*     */ 
/*     */ 
/*     */     
/*  67 */     GroupLayout layout = null;
/*  68 */     GroupLayout.SequentialGroup hGroup = null;
/*  69 */     GroupLayout.SequentialGroup vGroup = null;
/*     */ 
/*     */ 
/*     */     
/*  73 */     this.pnlMain = buildMainPanel();
/*     */     
/*  75 */     layout = new GroupLayout((Container)this);
/*  76 */     setLayout(layout);
/*     */     
/*  78 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  81 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/*  84 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  87 */     hGroup
/*  88 */       .addGroup(layout.createParallelGroup()
/*  89 */         .addComponent(this.pnlMain));
/*  90 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  93 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  96 */     vGroup
/*  97 */       .addGroup(layout.createParallelGroup()
/*  98 */         .addComponent(this.pnlMain));
/*  99 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 104 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 105 */     Font fntTabbed = UIManager.getDefaults().getFont("TabbedPane.font");
/* 106 */     if (fntTabbed == null) fntTabbed = fntLabel;
/*     */     
/* 108 */     fntTabbed = fntTabbed.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     if (this.pnlPages[0] == null) this.pnlPages[0] = new GDContainerMapPane(this.uiInventory, this.uiTransfer, GDImagePool.getCharStashBG()); 
/* 118 */     if (this.pnlPages[1] == null) this.pnlPages[1] = new GDContainerMapPane(this.uiInventory, this.uiTransfer, GDImagePool.getCharStashBG()); 
/* 119 */     if (this.pnlPages[2] == null) this.pnlPages[2] = new GDContainerMapPane(this.uiInventory, this.uiTransfer, GDImagePool.getCharStashBG()); 
/* 120 */     if (this.pnlPages[3] == null) this.pnlPages[3] = new GDContainerMapPane(this.uiInventory, this.uiTransfer, GDImagePool.getCharStashBG()); 
/* 121 */     if (this.pnlPages[4] == null) this.pnlPages[4] = new GDContainerMapPane(this.uiInventory, this.uiTransfer, GDImagePool.getCharStashBG()); 
/* 122 */     if (this.pnlPages[5] == null) this.pnlPages[5] = new GDContainerMapPane(this.uiInventory, this.uiTransfer, GDImagePool.getCharStashBG());
/*     */     
/* 124 */     if (this.tabPages == null) {
/* 125 */       this.tabPages = new JTabbedPane();
/* 126 */       this.tabPages.setTabPlacement(2);
/*     */       
/* 128 */       this.tabPages.add("I", (Component)this.pnlPages[0]);
/* 129 */       this.tabPages.add("II", (Component)this.pnlPages[1]);
/* 130 */       this.tabPages.add("III", (Component)this.pnlPages[2]);
/* 131 */       this.tabPages.add("IV", (Component)this.pnlPages[3]);
/* 132 */       this.tabPages.add("V", (Component)this.pnlPages[4]);
/* 133 */       this.tabPages.add("VI", (Component)this.pnlPages[5]);
/*     */     } 
/* 135 */     this.tabPages.setFont(fntTabbed);
/* 136 */     this.tabPages.setMaximumSize(new Dimension(this.pnlPages[0].getPreferredWidth() + 80, this.pnlPages[0].getPreferredHeight()));
/*     */   }
/*     */   
/*     */   private JPanel buildMainPanel() {
/* 140 */     GroupLayout layout = null;
/* 141 */     GroupLayout.SequentialGroup hGroup = null;
/* 142 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 144 */     JPanel panel = new JPanel();
/*     */     
/* 146 */     layoutStash();
/*     */     
/* 148 */     layout = new GroupLayout(panel);
/* 149 */     panel.setLayout(layout);
/*     */     
/* 151 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 154 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 157 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 160 */     hGroup
/* 161 */       .addGroup(layout.createParallelGroup()
/* 162 */         .addComponent(this.tabPages));
/* 163 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 166 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 169 */     vGroup
/* 170 */       .addGroup(layout.createParallelGroup()
/* 171 */         .addComponent(this.tabPages));
/* 172 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 174 */     return panel;
/*     */   }
/*     */   
/*     */   public GDContainerMapPane getCurrentPage() {
/* 178 */     int index = this.tabPages.getSelectedIndex();
/*     */     
/* 180 */     return this.pnlPages[index];
/*     */   }
/*     */   
/*     */   public void layoutStash() {
/* 184 */     int i = 0;
/* 185 */     if (this.gdc != null) {
/* 186 */       for (GDAbstractContainer page : this.gdc.getStashPages()) {
/* 187 */         GDContainerPane container = new GDContainerPane(3, 10, 34, this.frame, this, this.uiTransfer);
/* 188 */         container.setContainer((GDItemContainer)page);
/*     */ 
/*     */         
/* 191 */         this.pnlPages[i].clearContainers();
/* 192 */         this.pnlPages[i].addContainer(container);
/* 193 */         this.tabPages.setEnabledAt(i, true);
/*     */         
/* 195 */         i++;
/*     */       } 
/*     */     }
/*     */     
/* 199 */     while (i < this.pnlPages.length) {
/* 200 */       GDContainerPane container = new GDContainerPane(3, 10, 34, this.frame, this, this.uiTransfer);
/*     */ 
/*     */       
/* 203 */       this.pnlPages[i].clearContainers();
/* 204 */       this.pnlPages[i].addContainer(container);
/* 205 */       this.tabPages.setEnabledAt(i, false);
/*     */       
/* 207 */       i++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteSelectedItem(int action) {
/* 213 */     if (this.selItem == null)
/*     */       return; 
/* 215 */     boolean success = this.selPage.deleteItem(this.selItem, action, true);
/*     */     
/* 217 */     if (success) setSelectedItem((GDItem)null); 
/*     */   }
/*     */   
/*     */   public void moveSelectedItem(int action, int x, int y) {
/* 221 */     if (this.selItem == null)
/*     */       return; 
/* 223 */     GDContainerMapPane page = getCurrentPage();
/* 224 */     GDItem clone = this.selItem.clone();
/* 225 */     boolean success = page.addItem(clone, action, x, y);
/*     */     
/* 227 */     if (success) {
/* 228 */       success = this.selPage.deleteItem(this.selItem, action, true);
/*     */       
/* 230 */       if (success) {
/* 231 */         this.selItem = clone;
/* 232 */         this.selPage = page;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBGImage(BufferedImage bgImage) {
/* 239 */     for (int i = 0; i < this.pnlPages.length; i++) {
/* 240 */       this.pnlPages[i].setBGImage(bgImage);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearContainers() {
/* 250 */     this.gdc = null;
/*     */     
/* 252 */     for (int i = 0; i < this.pnlPages.length; i++)
/*     */     {
/* 254 */       this.pnlPages[i].clearContainers();
/*     */     }
/*     */     
/* 257 */     this.tabPages.removeAll();
/*     */     
/* 259 */     this.filledPages = 0;
/*     */     
/* 261 */     this.selPage = null;
/*     */     
/* 263 */     layoutContainers();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addContainer(GDUIContainer container) {
/* 268 */     GDContainerPane pane = (GDContainerPane)container;
/*     */ 
/*     */     
/* 271 */     this.pnlPages[this.filledPages].clearContainers();
/* 272 */     this.pnlPages[this.filledPages].addContainer(container);
/*     */     
/* 274 */     String s = "";
/*     */     
/* 276 */     if (this.filledPages == 0) s = "I"; 
/* 277 */     if (this.filledPages == 1) s = "II"; 
/* 278 */     if (this.filledPages == 2) s = "III"; 
/* 279 */     if (this.filledPages == 3) s = "IV"; 
/* 280 */     if (this.filledPages == 4) s = "V"; 
/* 281 */     if (this.filledPages == 5) s = "VI";
/*     */     
/* 283 */     this.tabPages.add(s, (Component)this.pnlPages[this.filledPages]);
/*     */     
/* 285 */     this.filledPages++;
/*     */     
/* 287 */     this.selPage = getCurrentPage();
/*     */     
/* 289 */     layoutContainers();
/*     */   }
/*     */ 
/*     */   
/*     */   public void layoutContainers() {
/* 294 */     repaint();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean deleteItem(GDItem item, int action, boolean update) {
/* 299 */     if (item == null) return true;
/*     */     
/* 301 */     if (this.pnlPages == null) return false;
/*     */     
/* 303 */     boolean deleted = false;
/*     */     int i;
/* 305 */     for (i = 0; i < this.pnlPages.length; i++) {
/* 306 */       GDContainerMapPane page = this.pnlPages[i];
/*     */       
/* 308 */       if (page != null && 
/* 309 */         page.deleteItem(item, action, update)) {
/* 310 */         deleted = true;
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 317 */     if (update) layoutContainers();
/*     */     
/* 319 */     return deleted;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 324 */     if (this.pnlPages == null) return false;
/*     */     
/* 326 */     boolean hasChanged = false;
/*     */     
/* 328 */     for (int i = 0; i < this.pnlPages.length; i++) {
/* 329 */       GDContainerMapPane page = this.pnlPages[i];
/*     */       
/* 331 */       if (page.hasChanged()) {
/* 332 */         hasChanged = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 338 */     return hasChanged;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDItem> getItemList(int action) {
/* 343 */     List<GDItem> listAll = new LinkedList<>();
/*     */     
/* 345 */     GDContainerMapPane page = getCurrentPage();
/*     */     
/* 347 */     if (page == null) return listAll;
/*     */     
/* 349 */     List<GDItem> list = page.getItemList(action);
/*     */     
/* 351 */     if (list != null) listAll.addAll(list);
/*     */     
/* 353 */     return listAll;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GDItem getSelectedItem() {
/* 362 */     return this.selItem;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelectedItem(GDItem item) {
/* 367 */     this.selItem = item;
/*     */     
/* 369 */     if (item == null) {
/* 370 */       this.selPage = null;
/*     */     } else {
/* 372 */       this.selPage = getCurrentPage();
/*     */ 
/*     */       
/* 375 */       this.selPage.layoutContainers();
/*     */     } 
/*     */     
/* 378 */     this.uiTransfer.setSelectedItem(item, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh() {
/* 383 */     if (this.pnlPages != null) {
/* 384 */       for (int i = 0; i < this.pnlPages.length; i++) {
/* 385 */         this.pnlPages[i].refresh();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addItem(GDItem item, int action, int x, int y) {
/* 392 */     if (item == null) return false;
/*     */     
/* 394 */     GDContainerMapPane page = getCurrentPage();
/* 395 */     return page.addItem(item, action, x, y);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\stash\GDPrivateStashPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */