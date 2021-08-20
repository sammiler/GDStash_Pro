/*     */ package org.gdstash.ui.stash;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.event.MouseInputAdapter;
/*     */ import org.gdstash.file.DDSLoader;
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
/*     */ public class GDEquippedContainerPane
/*     */   extends JLabel
/*     */   implements GDUIContainer
/*     */ {
/*     */   private class GDContainerPageMouseListener
/*     */     extends MouseInputAdapter
/*     */   {
/*     */     private GDContainerPageMouseListener() {}
/*     */     
/*     */     public void mousePressed(MouseEvent e) {
/*  39 */       selectItem(e);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseMoved(MouseEvent e) {
/*  49 */       GDItem item = null;
/*     */       
/*  51 */       if (GDEquippedContainerPane.this.uiTransfer != null) {
/*  52 */         item = GDEquippedContainerPane.this.uiTransfer.getSelectedItem();
/*     */       }
/*     */       
/*  55 */       if (item == null)
/*     */         return; 
/*  57 */       BufferedImage img = item.getImage();
/*  58 */       if (img == null)
/*     */         return; 
/*  60 */       int x = e.getX();
/*  61 */       int y = e.getY();
/*     */       
/*  63 */       int xOff = GDEquippedContainerPane.this.getXOffset();
/*  64 */       int yOff = GDEquippedContainerPane.this.getYOffset();
/*  65 */       int w = GDEquippedContainerPane.this.getWidth();
/*  66 */       int h = GDEquippedContainerPane.this.getHeight();
/*     */       
/*  68 */       if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/*  69 */         xOff = xOff * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*  70 */         yOff = yOff * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*     */       } 
/*     */ 
/*     */       
/*  74 */       if (x < xOff || x > xOff + w || y < yOff || y > yOff + h) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  79 */       x -= xOff;
/*  80 */       y -= yOff;
/*     */       
/*  82 */       if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/*  83 */         x = x * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/*  84 */         y = y * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/*     */         
/*  86 */         w = w * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/*  87 */         h = h * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/*     */       } 
/*     */       int i;
/*  90 */       for (i = 0; i < GDEquippedContainerPane.itemRectangles.length; i++) {
/*  91 */         if (GDEquippedContainerPane.itemRectangles[i].contains(x, y)) {
/*  92 */           GDEquippedContainerPane.this.mouseRect = GDEquippedContainerPane.itemRectangles[i];
/*     */         }
/*     */       } 
/*     */       
/*  96 */       GDEquippedContainerPane.this.layoutPage();
/*     */     }
/*     */     
/*     */     private void selectItem(MouseEvent e) {
/* 100 */       if (GDEquippedContainerPane.this.container == null)
/*     */         return; 
/* 102 */       int button = e.getButton();
/*     */       
/* 104 */       if (button == 3 && 
/* 105 */         GDEquippedContainerPane.this.uiInventory != null) {
/* 106 */         GDEquippedContainerPane.this.uiInventory.setSelectedItem(null);
/*     */       }
/*     */ 
/*     */       
/* 110 */       if (button != 1)
/*     */         return; 
/* 112 */       int x = e.getX();
/* 113 */       int y = e.getY();
/*     */       
/* 115 */       int xOff = GDEquippedContainerPane.this.getXOffset();
/* 116 */       int yOff = GDEquippedContainerPane.this.getYOffset();
/* 117 */       int w = GDEquippedContainerPane.this.getWidth();
/* 118 */       int h = GDEquippedContainerPane.this.getHeight();
/*     */       
/* 120 */       if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 121 */         xOff = xOff * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 122 */         yOff = yOff * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*     */       } 
/*     */ 
/*     */       
/* 126 */       if (x < xOff || x > xOff + w || y < yOff || y > yOff + h) {
/*     */ 
/*     */         
/* 129 */         GDEquippedContainerPane.this.layoutPage();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 134 */       x -= xOff;
/* 135 */       y -= yOff;
/*     */       
/* 137 */       if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 138 */         x = x * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/* 139 */         y = y * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/*     */       } 
/*     */       
/* 142 */       GDItem selItem = null;
/*     */       
/* 144 */       if (GDEquippedContainerPane.this.uiTransfer != null) {
/* 145 */         selItem = GDEquippedContainerPane.this.uiTransfer.getSelectedItem();
/*     */       }
/*     */       
/* 148 */       GDItem item = GDEquippedContainerPane.this.getItemFromCoord(x, y);
/* 149 */       if (item != null) GDEquippedContainerPane.this.uiInventory.setSelectedItem(item);
/*     */       
/* 151 */       item = null;
/*     */       
/* 153 */       if (GDEquippedContainerPane.this.uiTransfer != null) {
/* 154 */         item = GDEquippedContainerPane.this.uiTransfer.getSelectedItem();
/*     */       }
/*     */       
/* 157 */       if (item != null && 
/* 158 */         !GDEquippedContainerPane.this.isBlocked()) {
/* 159 */         GDEquippedContainerPane.this.mouseRect = null;
/*     */         
/* 161 */         if (GDEquippedContainerPane.this.uiTransfer.getItemLocation() == 1) {
/* 162 */           GDEquippedContainerPane.this.uiTransfer.transferSelectedItem(1, e.getX(), e.getY());
/*     */         }
/*     */         
/* 165 */         if (GDEquippedContainerPane.this.uiTransfer.getItemLocation() == 2) {
/* 166 */           GDEquippedContainerPane.this.uiTransfer.transferSelectedItem(4, e.getX(), e.getY());
/*     */         }
/*     */       } 
/*     */     }
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
/* 186 */   private static Rectangle[] itemRectangles = new Rectangle[16];
/*     */   
/*     */   protected GDItemContainer container;
/*     */   
/*     */   protected int containerType;
/*     */   
/*     */   protected GDUIInventory uiInventory;
/*     */   
/*     */   protected GDUITransfer uiTransfer;
/*     */   
/*     */   protected Rectangle mouseRect;
/*     */   
/*     */   protected int xOffset;
/*     */   
/*     */   protected int yOffset;
/*     */   
/*     */   protected GDStashFrame frame;
/*     */ 
/*     */   
/*     */   static {
/* 206 */     itemRectangles[0] = new Rectangle(226, 3, 73, 72);
/* 207 */     itemRectangles[1] = new Rectangle(312, 3, 38, 37);
/* 208 */     itemRectangles[2] = new Rectangle(89, 151, 73, 104);
/* 209 */     itemRectangles[3] = new Rectangle(89, 281, 73, 104);
/* 210 */     itemRectangles[4] = new Rectangle(363, 313, 73, 72);
/* 211 */     itemRectangles[5] = new Rectangle(363, 232, 73, 72);
/* 212 */     itemRectangles[6] = new Rectangle(175, 54, 38, 37);
/* 213 */     itemRectangles[7] = new Rectangle(312, 54, 38, 37);
/* 214 */     itemRectangles[8] = new Rectangle(226, 375, 73, 40);
/* 215 */     itemRectangles[9] = new Rectangle(363, 151, 73, 72);
/* 216 */     itemRectangles[10] = new Rectangle(312, 375, 41, 40);
/* 217 */     itemRectangles[11] = new Rectangle(172, 375, 41, 40);
/* 218 */     itemRectangles[12] = new Rectangle(3, 3, 73, 134);
/* 219 */     itemRectangles[13] = new Rectangle(363, 3, 73, 134);
/* 220 */     itemRectangles[14] = new Rectangle(89, 3, 73, 134);
/* 221 */     itemRectangles[15] = new Rectangle(449, 3, 73, 134);
/*     */   }
/*     */   
/*     */   public GDEquippedContainerPane(int xOffset, int yOffset, GDStashFrame frame, GDUIInventory uiInventory, GDUITransfer uiTransfer) {
/* 225 */     this.frame = frame;
/* 226 */     this.containerType = 6;
/* 227 */     this.xOffset = xOffset;
/* 228 */     this.yOffset = yOffset;
/* 229 */     this.uiInventory = uiInventory;
/* 230 */     this.uiTransfer = uiTransfer;
/*     */     
/* 232 */     this.mouseRect = null;
/*     */     
/* 234 */     addMouseListener(new GDContainerPageMouseListener());
/* 235 */     addMouseMotionListener(new GDContainerPageMouseListener());
/*     */   }
/*     */   
/*     */   private int getItemPosition(GDItem item) {
/* 239 */     if (item == null) return -1;
/*     */     
/* 241 */     int pos = item.getY();
/*     */     
/* 243 */     if (item.getX() >= 1) pos += 12; 
/* 244 */     if (item.getX() >= 2) pos += 2;
/*     */     
/* 246 */     return pos;
/*     */   }
/*     */   
/*     */   private int getPositionFromCoord(int x, int y) {
/* 250 */     for (int i = 0; i < itemRectangles.length; i++) {
/* 251 */       if (itemRectangles[i].contains(x, y)) {
/* 252 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 256 */     return -1;
/*     */   }
/*     */   
/*     */   private GDItem getItemFromCoord(int x, int y) {
/* 260 */     GDItem posItem = null;
/*     */     
/* 262 */     if (this.container != null) {
/* 263 */       int i; for (i = 0; i < itemRectangles.length; i++) {
/* 264 */         if (itemRectangles[i].contains(x, y)) {
/* 265 */           for (GDItem item : this.container.getItemList()) {
/* 266 */             if (item != null) {
/* 267 */               int pos = getItemPosition(item);
/*     */               
/* 269 */               if (pos == i) posItem = item;
/*     */             
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 276 */     return posItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContainer(GDItemContainer container) {
/* 285 */     if (container == null)
/*     */       return; 
/* 287 */     if (container.getContainerType() != 6)
/*     */       return; 
/* 289 */     this.container = container;
/* 290 */     this.containerType = container.getContainerType();
/*     */     
/* 292 */     layoutPage();
/*     */   }
/*     */ 
/*     */   
/*     */   public BufferedImage getBackgroundImage() {
/* 297 */     return GDImagePool.imgCharEquipped;
/*     */   }
/*     */ 
/*     */   
/*     */   public BufferedImage drawGraphics() {
/* 302 */     GDItem selItem = null;
/*     */     
/* 304 */     if (this.uiTransfer != null) {
/* 305 */       selItem = this.uiTransfer.getSelectedItem();
/*     */     }
/*     */ 
/*     */     
/* 309 */     BufferedImage imgBG = getBackgroundImage();
/*     */     
/* 311 */     BufferedImage image = new BufferedImage(imgBG.getWidth(), imgBG.getHeight(), imgBG.getType());
/*     */     
/* 313 */     Graphics2D g = image.createGraphics();
/*     */     
/* 315 */     if (this.container == null) {
/* 316 */       g.setColor(GDContainerPane.COLOR_DARK_RED);
/* 317 */       g.setComposite(AlphaComposite.getInstance(10, 0.8F));
/* 318 */       g.fillRect(0, 0, imgBG.getWidth(), imgBG.getHeight());
/*     */     } 
/*     */     
/* 321 */     g.drawImage(imgBG, 0, 0, (ImageObserver)null);
/* 322 */     Rectangle rect = null;
/*     */     
/* 324 */     if (this.container != null) {
/* 325 */       for (GDItem item : this.container.getItemList()) {
/* 326 */         BufferedImage img = item.getFullImage();
/* 327 */         if (img != null) {
/* 328 */           int pos = getItemPosition(item);
/*     */           
/* 330 */           int x = (itemRectangles[pos]).x;
/* 331 */           int y = (itemRectangles[pos]).y;
/* 332 */           int w = (itemRectangles[pos]).width;
/* 333 */           int h = (itemRectangles[pos]).height;
/*     */           
/* 335 */           int xpos = x + ((itemRectangles[pos]).width - img.getWidth()) / 2;
/* 336 */           int ypos = y + ((itemRectangles[pos]).height - img.getHeight()) / 2;
/*     */           
/* 338 */           if (img != null) {
/* 339 */             Color color = item.getOverlayColor();
/*     */             
/* 341 */             if (color != null) {
/* 342 */               g.setColor(color);
/* 343 */               g.setComposite(AlphaComposite.getInstance(10, 0.3F));
/* 344 */               g.fillRect(x + 1, y + 1, w - 2, h - 2);
/*     */               
/* 346 */               g.setComposite(AlphaComposite.getInstance(10, 1.0F));
/* 347 */               g.drawRect(x + 1, y + 1, w - 2, h - 2);
/*     */             } 
/*     */ 
/*     */             
/* 351 */             g.drawImage(img, xpos, ypos, (ImageObserver)null);
/*     */           } 
/*     */           
/* 354 */           if (item == selItem) {
/* 355 */             rect = itemRectangles[pos];
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 361 */     if (rect != null) {
/* 362 */       g.setColor(GDContainerPane.COLOR_GREEN);
/* 363 */       g.drawRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
/* 364 */       g.drawRect((int)rect.getX() + 1, (int)rect.getY() - 1, (int)rect.getWidth() - 2, (int)rect.getHeight() + 2);
/*     */     } 
/*     */     
/* 367 */     if (this.mouseRect != null) {
/* 368 */       if (isBlocked()) {
/* 369 */         g.setColor(GDContainerPane.COLOR_RED);
/*     */       } else {
/* 371 */         g.setColor(GDContainerPane.COLOR_WHITE);
/*     */       } 
/* 373 */       g.drawRect((int)this.mouseRect.getX(), (int)this.mouseRect.getY(), (int)this.mouseRect.getWidth(), (int)this.mouseRect.getHeight());
/* 374 */       g.drawRect((int)this.mouseRect.getX() + 1, (int)this.mouseRect.getY() - 1, (int)this.mouseRect.getWidth() - 2, (int)this.mouseRect.getHeight() + 2);
/*     */     } 
/*     */     
/* 377 */     return image;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getXOffset() {
/* 382 */     return this.xOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getYOffset() {
/* 387 */     return this.yOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 392 */     return getPreferredHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 397 */     return getPreferredWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addItem(GDItem item, int action, int xCoord, int yCoord) {
/* 402 */     return false;
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
/*     */   public boolean deleteItem(GDItem item, int action, boolean update) {
/* 460 */     return false;
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
/*     */   public boolean hasChanged() {
/* 479 */     if (this.container == null) return false;
/*     */     
/* 481 */     return this.container.hasChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDItem> getItemList(int action) {
/* 486 */     List<GDItem> list = new LinkedList<>();
/*     */     
/* 488 */     if (this.container == null) return list;
/*     */     
/* 490 */     return this.container.getItemList();
/*     */   }
/*     */   
/*     */   public void refresh() {
/* 494 */     if (this.container != null) this.container.refresh();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize() {
/* 502 */     BufferedImage image = getBackgroundImage();
/*     */     
/* 504 */     int w = image.getWidth() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 505 */     int h = image.getHeight() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*     */     
/* 507 */     return new Dimension(w, h);
/*     */   }
/*     */   
/*     */   public Dimension getMaximumSize() {
/* 511 */     return getPreferredSize();
/*     */   }
/*     */   
/*     */   public int getPreferredWidth() {
/* 515 */     return (int)getPreferredSize().getWidth();
/*     */   }
/*     */   
/*     */   public int getPreferredHeight() {
/* 519 */     return (int)getPreferredSize().getHeight();
/*     */   }
/*     */   
/*     */   public void layoutPage() {
/* 523 */     if (this.uiTransfer == null) {
/* 524 */       this.mouseRect = null;
/*     */     }
/* 526 */     else if (this.uiTransfer.getSelectedItem() == null) {
/* 527 */       this.mouseRect = null;
/*     */     } 
/*     */ 
/*     */     
/* 531 */     BufferedImage image = drawGraphics();
/*     */     
/* 533 */     if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 534 */       int w = image.getWidth() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 535 */       int h = image.getHeight() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 536 */       image = DDSLoader.getScaledImage(image, w, h);
/*     */     } 
/*     */     
/* 539 */     setIcon(new ImageIcon(image));
/*     */   }
/*     */   
/*     */   private boolean isBlocked() {
/* 543 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\stash\GDEquippedContainerPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */