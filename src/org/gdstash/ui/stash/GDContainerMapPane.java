/*     */ package org.gdstash.ui.stash;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.event.MouseInputAdapter;
/*     */ import org.gdstash.file.DDSLoader;
/*     */ import org.gdstash.item.GDItem;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.GDUITransfer;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ import org.gdstash.util.GDImagePool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDContainerMapPane
/*     */   extends AdjustablePanel
/*     */   implements GDUIMultiContainer
/*     */ {
/*     */   private class GDMouseListener
/*     */     extends MouseInputAdapter
/*     */   {
/*     */     private GDMouseListener() {}
/*     */     
/*     */     public void mousePressed(MouseEvent e) {
/*  34 */       if (GDContainerMapPane.this.containers == null)
/*     */         return; 
/*  36 */       GDContainerMapPane.this.selContainer = GDContainerMapPane.this.findSelectedContainer(e);
/*     */       
/*  38 */       GDContainerMapPane.this.notifyPages(e);
/*     */       
/*  40 */       GDContainerMapPane.this.layoutContainers();
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseMoved(MouseEvent e) {
/*  45 */       if (GDContainerMapPane.this.containers == null)
/*     */         return; 
/*  47 */       GDContainerMapPane.this.selContainer = GDContainerMapPane.this.findSelectedContainer(e);
/*     */       
/*  49 */       GDContainerMapPane.this.notifyPages(e);
/*     */       
/*  51 */       GDContainerMapPane.this.layoutContainers();
/*     */     }
/*     */   }
/*     */   
/*  55 */   private static final Color COLOR_RED = Color.RED;
/*  56 */   private static final Color COLOR_DARK_RED = new Color(127, 0, 0);
/*  57 */   private static final Color COLOR_WHITE = Color.WHITE;
/*  58 */   private static final Color COLOR_GREEN = new Color(83, 255, 40);
/*     */   
/*     */   private BufferedImage bgImage;
/*     */   
/*     */   private GDUIInventory uiInventory;
/*     */   private GDUITransfer uiTransfer;
/*     */   private List<GDUIContainer> containers;
/*     */   private GDUIContainer selContainer;
/*     */   
/*     */   public GDContainerMapPane() {
/*  68 */     this((GDUIInventory)null, (GDUITransfer)null, GDImagePool.getCharStashBG());
/*     */   }
/*     */ 
/*     */   
/*     */   public GDContainerMapPane(GDUIInventory uiInventory, GDUITransfer uiTransfer, BufferedImage bgImage) {
/*  73 */     this.uiInventory = uiInventory;
/*  74 */     this.uiTransfer = uiTransfer;
/*  75 */     this.bgImage = bgImage;
/*     */     
/*  77 */     this.containers = new LinkedList<>();
/*     */     
/*  79 */     addMouseListener(new GDMouseListener());
/*  80 */     addMouseMotionListener(new GDMouseListener());
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {}
/*     */   
/*     */   public void setBGImage(BufferedImage bgImage) {
/*  87 */     this.bgImage = bgImage;
/*     */   }
/*     */   
/*     */   private GDUIContainer findSelectedContainer(MouseEvent e) {
/*  91 */     return findSelectedContainer(e.getX(), e.getY());
/*     */   }
/*     */   
/*     */   private GDUIContainer findSelectedContainer(int xCoord, int yCoord) {
/*  95 */     if (this.containers == null) return null;
/*     */     
/*  97 */     int x = xCoord;
/*  98 */     int y = yCoord;
/*     */     
/* 100 */     GDUIContainer container = null;
/*     */     
/* 102 */     for (GDUIContainer uic : this.containers) {
/* 103 */       int xp = uic.getXOffset();
/* 104 */       int yp = uic.getYOffset();
/* 105 */       int w = uic.getWidth();
/* 106 */       int h = uic.getHeight();
/*     */       
/* 108 */       if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 109 */         xp = xp * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 110 */         yp = yp * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*     */       } 
/*     */       
/* 113 */       if (xp <= x && xp + w >= x && yp <= y && yp + h >= y) {
/* 114 */         container = uic;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 120 */     return container;
/*     */   }
/*     */   
/*     */   private void notifyPages(MouseEvent e) {
/* 124 */     if (this.containers != null) {
/* 125 */       for (GDUIContainer container : this.containers) {
/* 126 */         container.dispatchEvent(e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearContainers() {
/* 137 */     this.containers.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addContainer(GDUIContainer container) {
/* 142 */     if (container == null)
/*     */       return; 
/* 144 */     this.containers.add(container);
/*     */     
/* 146 */     layoutContainers();
/*     */   }
/*     */   
/*     */   public void layoutContainers() {
/* 150 */     repaint();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addItem(GDItem item, int action, int xCoord, int yCoord) {
/* 155 */     boolean added = false;
/*     */     
/* 157 */     GDUIContainer container = findSelectedContainer(xCoord, yCoord);
/*     */     
/* 159 */     if (container != null) {
/* 160 */       added = container.addItem(item, action, xCoord, yCoord);
/*     */       
/* 162 */       layoutContainers();
/*     */     } 
/*     */     
/* 165 */     return added;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean deleteItem(GDItem item, int action, boolean update) {
/* 170 */     if (item == null) return true;
/*     */     
/* 172 */     if (this.containers == null) return false;
/*     */     
/* 174 */     boolean deleted = false;
/*     */     
/* 176 */     for (GDUIContainer container : this.containers) {
/* 177 */       if (container.deleteItem(item, action, update)) {
/* 178 */         deleted = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 184 */     if (update) layoutContainers();
/*     */     
/* 186 */     return deleted;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 191 */     if (this.containers == null) return false;
/*     */     
/* 193 */     boolean hasChanged = false;
/*     */     
/* 195 */     for (GDUIContainer container : this.containers) {
/* 196 */       if (container.hasChanged()) {
/* 197 */         hasChanged = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     return hasChanged;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDItem> getItemList(int action) {
/* 208 */     List<GDItem> listAll = new LinkedList<>();
/*     */     
/* 210 */     if (this.containers == null) return listAll;
/*     */     
/* 212 */     for (GDUIContainer container : this.containers) {
/* 213 */       List<GDItem> list = container.getItemList(action);
/*     */       
/* 215 */       if (list != null) listAll.addAll(list);
/*     */     
/*     */     } 
/* 218 */     return listAll;
/*     */   }
/*     */   
/*     */   public void refresh() {
/* 222 */     if (this.containers != null) {
/* 223 */       for (GDUIContainer container : this.containers) {
/* 224 */         container.refresh();
/*     */       }
/*     */       
/* 227 */       layoutContainers();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintComponent(Graphics g) {
/* 237 */     super.paintComponent(g);
/*     */     
/* 239 */     if (this.bgImage == null)
/*     */       return; 
/* 241 */     g.drawImage(drawGraphics(), 0, 0, null);
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize() {
/* 245 */     if (this.bgImage == null) return super.getPreferredSize();
/*     */     
/* 247 */     int w = this.bgImage.getWidth() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 248 */     int h = this.bgImage.getHeight() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*     */     
/* 250 */     return new Dimension(w, h);
/*     */   }
/*     */   
/*     */   public Dimension getMaximumSize() {
/* 254 */     return getPreferredSize();
/*     */   }
/*     */   
/*     */   public Dimension getMinimumSize() {
/* 258 */     return getPreferredSize();
/*     */   }
/*     */   
/*     */   public int getPreferredWidth() {
/* 262 */     return (int)getPreferredSize().getWidth();
/*     */   }
/*     */   
/*     */   public int getPreferredHeight() {
/* 266 */     return (int)getPreferredSize().getHeight();
/*     */   }
/*     */   
/*     */   private BufferedImage drawGraphics() {
/* 270 */     if (this.bgImage == null) return null;
/*     */ 
/*     */     
/* 273 */     Graphics g = null;
/* 274 */     BufferedImage image = null;
/*     */     
/* 276 */     image = new BufferedImage(this.bgImage.getWidth(), this.bgImage.getHeight(), this.bgImage.getType());
/*     */     
/* 278 */     g = image.createGraphics();
/*     */     
/* 280 */     g.drawImage(this.bgImage, 0, 0, null);
/*     */     
/* 282 */     if (this.containers != null) {
/* 283 */       for (GDUIContainer container : this.containers) {
/* 284 */         BufferedImage img = container.drawGraphics();
/*     */         
/* 286 */         g.drawImage(img, container.getXOffset(), container.getYOffset(), null);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 297 */     if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 298 */       int w = image.getWidth() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 299 */       int h = image.getHeight() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 300 */       image = DDSLoader.getScaledImage(image, w, h);
/*     */     } 
/*     */     
/* 303 */     return image;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\stash\GDContainerMapPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */