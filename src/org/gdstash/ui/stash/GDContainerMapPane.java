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
/*     */ import org.gdstash.character.GDChar;
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
/*  35 */       if (GDContainerMapPane.this.containers == null)
/*     */         return; 
/*  37 */       GDContainerMapPane.this.selContainer = GDContainerMapPane.this.findSelectedContainer(e);
/*     */       
/*  39 */       GDContainerMapPane.this.notifyPages(e);
/*     */       
/*  41 */       GDContainerMapPane.this.layoutContainers();
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseMoved(MouseEvent e) {
/*  46 */       if (GDContainerMapPane.this.containers == null)
/*     */         return; 
/*  48 */       GDContainerMapPane.this.selContainer = GDContainerMapPane.this.findSelectedContainer(e);
/*     */       
/*  50 */       GDContainerMapPane.this.notifyPages(e);
/*     */       
/*  52 */       GDContainerMapPane.this.layoutContainers();
/*     */     }
/*     */   }
/*     */   
/*  56 */   private static final Color COLOR_RED = Color.RED;
/*  57 */   private static final Color COLOR_DARK_RED = new Color(127, 0, 0);
/*  58 */   private static final Color COLOR_WHITE = Color.WHITE;
/*  59 */   private static final Color COLOR_GREEN = new Color(83, 255, 40);
/*     */   
/*     */   private BufferedImage bgImage;
/*     */   
/*     */   private GDUIInventory uiInventory;
/*     */   private GDUITransfer uiTransfer;
/*     */   private List<GDUIContainer> containers;
/*     */   private GDUIContainer selContainer;
/*     */   
/*     */   public GDContainerMapPane() {
/*  69 */     this((GDUIInventory)null, (GDUITransfer)null, GDImagePool.getCharStashBG());
/*     */   }
/*     */ 
/*     */   
/*     */   public GDContainerMapPane(GDUIInventory uiInventory, GDUITransfer uiTransfer, BufferedImage bgImage) {
/*  74 */     this.uiInventory = uiInventory;
/*  75 */     this.uiTransfer = uiTransfer;
/*  76 */     this.bgImage = bgImage;
/*     */     
/*  78 */     this.containers = new LinkedList<>();
/*     */     
/*  80 */     addMouseListener(new GDMouseListener());
/*  81 */     addMouseMotionListener(new GDMouseListener());
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {}
/*     */   
/*     */   public void updateConfig() {
/*  88 */     if (this.containers != null)
/*  89 */       for (GDUIContainer container : this.containers) {
/*  90 */         if (container != null) container.updateConfig();
/*     */       
/*     */       }  
/*     */   }
/*     */   
/*     */   public void setBGImage(BufferedImage bgImage) {
/*  96 */     this.bgImage = bgImage;
/*     */   }
/*     */   
/*     */   private GDUIContainer findSelectedContainer(MouseEvent e) {
/* 100 */     return findSelectedContainer(e.getX(), e.getY());
/*     */   }
/*     */   
/*     */   private GDUIContainer findSelectedContainer(int xCoord, int yCoord) {
/* 104 */     if (this.containers == null) return null;
/*     */     
/* 106 */     int x = xCoord;
/* 107 */     int y = yCoord;
/*     */     
/* 109 */     GDUIContainer container = null;
/*     */     
/* 111 */     for (GDUIContainer uic : this.containers) {
/* 112 */       int xp = uic.getXOffset();
/* 113 */       int yp = uic.getYOffset();
/* 114 */       int w = uic.getWidth();
/* 115 */       int h = uic.getHeight();
/*     */       
/* 117 */       if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 118 */         xp = xp * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 119 */         yp = yp * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*     */       } 
/*     */       
/* 122 */       if (xp <= x && xp + w >= x && yp <= y && yp + h >= y) {
/* 123 */         container = uic;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     return container;
/*     */   }
/*     */   
/*     */   private void notifyPages(MouseEvent e) {
/* 133 */     if (this.containers != null) {
/* 134 */       for (GDUIContainer container : this.containers) {
/* 135 */         container.dispatchEvent(e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChar(GDChar gdc) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearContainers() {
/* 150 */     this.containers.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addContainer(GDUIContainer container) {
/* 155 */     if (container == null)
/*     */       return; 
/* 157 */     this.containers.add(container);
/*     */     
/* 159 */     layoutContainers();
/*     */   }
/*     */   
/*     */   public void layoutContainers() {
/* 163 */     repaint();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addItem(GDItem item, int action, int xCoord, int yCoord) {
/* 168 */     boolean added = false;
/*     */     
/* 170 */     GDUIContainer container = findSelectedContainer(xCoord, yCoord);
/*     */     
/* 172 */     if (container != null) {
/* 173 */       added = container.addItem(item, action, xCoord, yCoord);
/*     */       
/* 175 */       layoutContainers();
/*     */     } 
/*     */     
/* 178 */     return added;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean deleteItem(GDItem item, int action, boolean update) {
/* 183 */     if (item == null) return true;
/*     */     
/* 185 */     if (this.containers == null) return false;
/*     */     
/* 187 */     boolean deleted = false;
/*     */     
/* 189 */     for (GDUIContainer container : this.containers) {
/* 190 */       if (container.deleteItem(item, action, update)) {
/* 191 */         deleted = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 197 */     if (update) layoutContainers();
/*     */     
/* 199 */     return deleted;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 204 */     if (this.containers == null) return false;
/*     */     
/* 206 */     boolean hasChanged = false;
/*     */     
/* 208 */     for (GDUIContainer container : this.containers) {
/* 209 */       if (container.hasChanged()) {
/* 210 */         hasChanged = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 216 */     return hasChanged;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDItem> getItemList(int action) {
/* 221 */     List<GDItem> listAll = new LinkedList<>();
/*     */     
/* 223 */     if (this.containers == null) return listAll;
/*     */     
/* 225 */     for (GDUIContainer container : this.containers) {
/* 226 */       List<GDItem> list = container.getItemList(action);
/*     */       
/* 228 */       if (list != null) listAll.addAll(list);
/*     */     
/*     */     } 
/* 231 */     return listAll;
/*     */   }
/*     */   
/*     */   public void refresh() {
/* 235 */     if (this.containers != null) {
/* 236 */       for (GDUIContainer container : this.containers) {
/* 237 */         container.refresh();
/*     */       }
/*     */       
/* 240 */       layoutContainers();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintComponent(Graphics g) {
/* 250 */     super.paintComponent(g);
/*     */     
/* 252 */     if (this.bgImage == null)
/*     */       return; 
/* 254 */     g.drawImage(drawGraphics(), 0, 0, null);
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize() {
/* 258 */     if (this.bgImage == null) return super.getPreferredSize();
/*     */     
/* 260 */     int w = this.bgImage.getWidth() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 261 */     int h = this.bgImage.getHeight() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*     */     
/* 263 */     return new Dimension(w, h);
/*     */   }
/*     */   
/*     */   public Dimension getMaximumSize() {
/* 267 */     return getPreferredSize();
/*     */   }
/*     */   
/*     */   public Dimension getMinimumSize() {
/* 271 */     return getPreferredSize();
/*     */   }
/*     */   
/*     */   public int getPreferredWidth() {
/* 275 */     return (int)getPreferredSize().getWidth();
/*     */   }
/*     */   
/*     */   public int getPreferredHeight() {
/* 279 */     return (int)getPreferredSize().getHeight();
/*     */   }
/*     */   
/*     */   private BufferedImage drawGraphics() {
/* 283 */     if (this.bgImage == null) return null;
/*     */ 
/*     */     
/* 286 */     Graphics g = null;
/* 287 */     BufferedImage image = null;
/*     */     
/* 289 */     image = new BufferedImage(this.bgImage.getWidth(), this.bgImage.getHeight(), this.bgImage.getType());
/*     */     
/* 291 */     g = image.createGraphics();
/*     */     
/* 293 */     g.drawImage(this.bgImage, 0, 0, null);
/*     */     
/* 295 */     if (this.containers != null) {
/* 296 */       for (GDUIContainer container : this.containers) {
/* 297 */         BufferedImage img = container.drawGraphics();
/*     */         
/* 299 */         g.drawImage(img, container.getXOffset(), container.getYOffset(), null);
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
/* 310 */     if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 311 */       int w = image.getWidth() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 312 */       int h = image.getHeight() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 313 */       image = DDSLoader.getScaledImage(image, w, h);
/*     */     } 
/*     */     
/* 316 */     return image;
/*     */   }


            public List<GDUIContainer> getGDUIContainers()
            {
                return containers;
            }
            public  void refreshPage()
            {
                GDContainerMapPane.this.layoutContainers();
            }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\stash\GDContainerMapPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */