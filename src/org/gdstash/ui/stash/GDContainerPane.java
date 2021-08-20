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
/*     */ public class GDContainerPane
/*     */   extends JLabel
/*     */   implements GDUIContainer
/*     */ {
/*     */   private class GDContainerPageMouseListener
/*     */     extends MouseInputAdapter
/*     */   {
/*     */     private GDContainerPageMouseListener() {}
/*     */     
/*     */     public void mousePressed(MouseEvent e) {
/*  38 */       selectItem(e);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseMoved(MouseEvent e) {
/*  48 */       GDItem item = null;
/*     */       
/*  50 */       if (GDContainerPane.this.uiTransfer != null) {
/*  51 */         item = GDContainerPane.this.uiTransfer.getSelectedItem();
/*     */       }
/*     */       
/*  54 */       if (item == null)
/*     */         return; 
/*  56 */       BufferedImage img = item.getImage();
/*  57 */       if (img == null)
/*     */         return; 
/*  59 */       int x = e.getX();
/*  60 */       int y = e.getY();
/*     */       
/*  62 */       int xOff = GDContainerPane.this.getXOffset();
/*  63 */       int yOff = GDContainerPane.this.getYOffset();
/*  64 */       int w = GDContainerPane.this.getWidth();
/*  65 */       int h = GDContainerPane.this.getHeight();
/*     */       
/*  67 */       if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/*  68 */         xOff = xOff * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*  69 */         yOff = yOff * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*     */       } 
/*     */ 
/*     */       
/*  73 */       if (x < xOff || x > xOff + w || y < yOff || y > yOff + h) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  78 */       x -= xOff;
/*  79 */       y -= yOff;
/*     */       
/*  81 */       if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/*  82 */         x = x * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/*  83 */         y = y * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/*     */         
/*  85 */         w = w * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/*  86 */         h = h * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/*     */       } 
/*     */ 
/*     */       
/*  90 */       if (x < 10 || x > w - 10 || y < 10 || y > h - 10) {
/*     */         
/*  92 */         GDContainerPane.this.mouseRect = null;
/*     */       } else {
/*     */         
/*  95 */         x /= 32;
/*  96 */         y /= 32;
/*     */         
/*  98 */         x = x * 32 + GDImagePool.getSharedStashXOffset() + 1;
/*  99 */         y = y * 32 + GDImagePool.getSharedStashXOffset() + 1;
/*     */         
/* 101 */         GDContainerPane.this.mouseRect = new Rectangle(x, y, img.getWidth() - 2, img.getHeight() - 2);
/*     */       } 
/*     */       
/* 104 */       GDContainerPane.this.layoutPage();
/*     */     }
/*     */     
/*     */     private void selectItem(MouseEvent e) {
/* 108 */       if (GDContainerPane.this.container == null)
/*     */         return; 
/* 110 */       int button = e.getButton();
/*     */       
/* 112 */       if (button == 3 && 
/* 113 */         GDContainerPane.this.uiInventory != null) {
/* 114 */         GDContainerPane.this.uiInventory.setSelectedItem(null);
/*     */       }
/*     */ 
/*     */       
/* 118 */       if (button != 1)
/*     */         return; 
/* 120 */       int x = e.getX();
/* 121 */       int y = e.getY();
/*     */       
/* 123 */       int xOff = GDContainerPane.this.getXOffset();
/* 124 */       int yOff = GDContainerPane.this.getYOffset();
/* 125 */       int w = GDContainerPane.this.getWidth();
/* 126 */       int h = GDContainerPane.this.getHeight();
/*     */       
/* 128 */       if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 129 */         xOff = xOff * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 130 */         yOff = yOff * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*     */       } 
/*     */ 
/*     */       
/* 134 */       if (x < xOff || x > xOff + w || y < yOff || y > yOff + h) {
/*     */ 
/*     */         
/* 137 */         GDContainerPane.this.layoutPage();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 142 */       x -= xOff;
/* 143 */       y -= yOff;
/*     */       
/* 145 */       if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 146 */         x = x * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/* 147 */         y = y * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/*     */       } 
/*     */       
/* 150 */       GDItem selItem = null;
/*     */       
/* 152 */       if (GDContainerPane.this.uiTransfer != null) {
/* 153 */         selItem = GDContainerPane.this.uiTransfer.getSelectedItem();
/*     */       }
/*     */       
/* 156 */       for (GDItem gDItem : GDContainerPane.this.container.getItemList()) {
/* 157 */         BufferedImage img = gDItem.getImage();
/*     */         
/* 159 */         if (img != null) {
/* 160 */           int x1 = gDItem.getX() * 32 + GDImagePool.getSharedStashXOffset();
/* 161 */           int y1 = gDItem.getY() * 32 + GDImagePool.getSharedStashYOffset();
/* 162 */           int x2 = x1 + img.getWidth();
/* 163 */           int y2 = y1 + img.getHeight();
/*     */           
/* 165 */           if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
/*     */ 
/*     */             
/* 168 */             if (gDItem != selItem) {
/* 169 */               if (GDContainerPane.this.uiInventory != null) {
/* 170 */                 GDContainerPane.this.uiInventory.setSelectedItem(gDItem);
/*     */               }
/*     */               
/* 173 */               selItem = gDItem;
/*     */               
/* 175 */               GDContainerPane.this.layoutPage();
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 185 */       GDItem item = null;
/*     */       
/* 187 */       if (GDContainerPane.this.uiTransfer != null) {
/* 188 */         item = GDContainerPane.this.uiTransfer.getSelectedItem();
/*     */       }
/*     */       
/* 191 */       if (item != null && 
/* 192 */         !GDContainerPane.this.isBlocked()) {
/* 193 */         GDContainerPane.this.mouseRect = null;
/*     */         
/* 195 */         if (GDContainerPane.this.uiTransfer.getItemLocation() == 1) {
/* 196 */           GDContainerPane.this.uiTransfer.transferSelectedItem(1, e.getX(), e.getY());
/*     */         }
/*     */         
/* 199 */         if (GDContainerPane.this.uiTransfer.getItemLocation() == 2) {
/* 200 */           GDContainerPane.this.uiTransfer.transferSelectedItem(4, e.getX(), e.getY());
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 207 */   protected static final Color COLOR_RED = Color.RED;
/* 208 */   protected static final Color COLOR_DARK_RED = new Color(127, 0, 0);
/* 209 */   protected static final Color COLOR_WHITE = Color.WHITE;
/* 210 */   protected static final Color COLOR_GREEN = new Color(83, 255, 40);
/*     */   
/*     */   protected GDItemContainer container;
/*     */   
/*     */   protected int containerType;
/*     */   protected GDUIInventory uiInventory;
/*     */   protected GDUITransfer uiTransfer;
/*     */   protected Rectangle mouseRect;
/*     */   protected int xOffset;
/*     */   protected int yOffset;
/*     */   protected GDStashFrame frame;
/*     */   
/*     */   public GDContainerPane(int containerType, int xOffset, int yOffset, GDStashFrame frame, GDUIInventory uiInventory, GDUITransfer uiTransfer) {
/* 223 */     this.frame = frame;
/* 224 */     this.containerType = containerType;
/* 225 */     this.xOffset = xOffset;
/* 226 */     this.yOffset = yOffset;
/* 227 */     this.uiInventory = uiInventory;
/* 228 */     this.uiTransfer = uiTransfer;
/*     */     
/* 230 */     this.mouseRect = null;
/*     */     
/* 232 */     addMouseListener(new GDContainerPageMouseListener());
/* 233 */     addMouseMotionListener(new GDContainerPageMouseListener());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContainer(GDItemContainer container) {
/* 242 */     this.container = container;
/* 243 */     if (container != null) this.containerType = container.getContainerType();
/*     */     
/* 245 */     layoutPage();
/*     */   }
/*     */ 
/*     */   
/*     */   public BufferedImage getBackgroundImage() {
/* 250 */     BufferedImage img = null;
/*     */     
/* 252 */     switch (this.containerType)
/*     */     { case 1:
/* 254 */         img = GDImagePool.getSharedStashGrid();
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
/* 282 */         return img;case 2: img = GDImagePool.getCraftingStashGrid(); return img;case 3: img = GDImagePool.getCharStashGrid(); return img;case 4: img = GDImagePool.imgCharInventory; return img;case 5: img = GDImagePool.imgCharBag; return img;case 6: img = GDImagePool.imgCharEquipped; return img; }  img = GDImagePool.getSharedStashGrid(); return img;
/*     */   }
/*     */ 
/*     */   
/*     */   public BufferedImage drawGraphics() {
/* 287 */     GDItem selItem = null;
/*     */     
/* 289 */     if (this.uiTransfer != null) {
/* 290 */       selItem = this.uiTransfer.getSelectedItem();
/*     */     }
/*     */ 
/*     */     
/* 294 */     BufferedImage imgBG = getBackgroundImage();
/*     */     
/* 296 */     BufferedImage image = new BufferedImage(imgBG.getWidth(), imgBG.getHeight(), imgBG.getType());
/*     */     
/* 298 */     Graphics2D g = image.createGraphics();
/*     */     
/* 300 */     if (this.container == null) {
/* 301 */       g.setColor(COLOR_DARK_RED);
/* 302 */       g.setComposite(AlphaComposite.getInstance(10, 0.8F));
/* 303 */       g.fillRect(0, 0, imgBG.getWidth(), imgBG.getHeight());
/*     */     } 
/*     */     
/* 306 */     g.drawImage(imgBG, 0, 0, (ImageObserver)null);
/* 307 */     Rectangle rect = null;
/*     */     
/* 309 */     if (this.container != null) {
/* 310 */       for (GDItem item : this.container.getItemList()) {
/* 311 */         BufferedImage img = item.getFullImage();
/* 312 */         int x = item.getX() * 32 + GDImagePool.getSharedStashXOffset();
/* 313 */         int y = item.getY() * 32 + GDImagePool.getSharedStashYOffset();
/*     */         
/* 315 */         if (img != null) {
/* 316 */           Color color = item.getOverlayColor();
/*     */           
/* 318 */           if (color != null) {
/* 319 */             g.setColor(color);
/* 320 */             g.setComposite(AlphaComposite.getInstance(10, 0.3F));
/* 321 */             g.fillRect(x + 1, y + 1, img.getWidth() - 2, img.getHeight() - 2);
/*     */             
/* 323 */             g.setComposite(AlphaComposite.getInstance(10, 1.0F));
/* 324 */             g.drawRect(x + 1, y + 1, img.getWidth() - 2, img.getHeight() - 2);
/*     */           } 
/*     */           
/* 327 */           g.drawImage(img, x, y, (ImageObserver)null);
/*     */         } 
/*     */         
/* 330 */         if (item == selItem) {
/* 331 */           rect = new Rectangle(x, y, img.getWidth(), img.getHeight());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 336 */     if (rect != null) {
/* 337 */       g.setColor(COLOR_GREEN);
/* 338 */       g.drawRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
/* 339 */       g.drawRect((int)rect.getX() + 1, (int)rect.getY() - 1, (int)rect.getWidth() - 2, (int)rect.getHeight() + 2);
/*     */     } 
/*     */     
/* 342 */     if (this.mouseRect != null) {
/* 343 */       if (isBlocked()) {
/* 344 */         g.setColor(COLOR_RED);
/*     */       } else {
/* 346 */         g.setColor(COLOR_WHITE);
/*     */       } 
/* 348 */       g.drawRect((int)this.mouseRect.getX(), (int)this.mouseRect.getY(), (int)this.mouseRect.getWidth(), (int)this.mouseRect.getHeight());
/* 349 */       g.drawRect((int)this.mouseRect.getX() + 1, (int)this.mouseRect.getY() - 1, (int)this.mouseRect.getWidth() - 2, (int)this.mouseRect.getHeight() + 2);
/*     */     } 
/*     */     
/* 352 */     return image;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getXOffset() {
/* 357 */     return this.xOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getYOffset() {
/* 362 */     return this.yOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 367 */     return getPreferredHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 372 */     return getPreferredWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addItem(GDItem item, int action, int xCoord, int yCoord) {
/* 377 */     boolean added = false;
/*     */     
/* 379 */     if (this.container != null) {
/*     */       
/* 381 */       int xPos = xCoord;
/* 382 */       int yPos = yCoord;
/* 383 */       int xOff = getXOffset();
/* 384 */       int yOff = getYOffset();
/*     */       
/* 386 */       if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 387 */         xOff = xOff * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 388 */         yOff = yOff * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*     */       } 
/*     */       
/* 391 */       xPos -= xOff;
/* 392 */       yPos -= yOff;
/*     */       
/* 394 */       if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 395 */         xPos = xPos * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/* 396 */         yPos = yPos * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/*     */       } 
/*     */       
/* 399 */       xPos /= 32;
/* 400 */       yPos /= 32;
/*     */       
/* 402 */       item.setX(xPos);
/* 403 */       item.setY(yPos);
/* 404 */       added = this.container.addItem(item);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 409 */     return added;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean deleteItem(GDItem item, int action, boolean update) {
/* 414 */     if (item == null) return true;
/*     */     
/* 416 */     boolean deleted = false;
/*     */     
/* 418 */     if (this.container != null) {
/* 419 */       deleted = this.container.removeItem(item);
/*     */       
/* 421 */       if (update) layoutPage();
/*     */     
/*     */     } 
/* 424 */     return deleted;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 429 */     if (this.container == null) return false;
/*     */     
/* 431 */     return this.container.hasChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDItem> getItemList(int action) {
/* 436 */     List<GDItem> list = new LinkedList<>();
/*     */     
/* 438 */     if (this.container == null) return list;
/*     */     
/* 440 */     return this.container.getItemList();
/*     */   }
/*     */   
/*     */   public void refresh() {
/* 444 */     if (this.container != null) {
/* 445 */       this.container.refresh();
/*     */       
/* 447 */       layoutPage();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize() {
/* 456 */     BufferedImage image = getBackgroundImage();
/*     */     
/* 458 */     int w = image.getWidth() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 459 */     int h = image.getHeight() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*     */     
/* 461 */     return new Dimension(w, h);
/*     */   }
/*     */   
/*     */   public Dimension getMaximumSize() {
/* 465 */     return getPreferredSize();
/*     */   }
/*     */   
/*     */   public Dimension getMinimumSize() {
/* 469 */     return getPreferredSize();
/*     */   }
/*     */   
/*     */   public int getPreferredWidth() {
/* 473 */     return (int)getPreferredSize().getWidth();
/*     */   }
/*     */   
/*     */   public int getPreferredHeight() {
/* 477 */     return (int)getPreferredSize().getHeight();
/*     */   }
/*     */   
/*     */   public void layoutPage() {
/* 481 */     if (this.uiTransfer == null) {
/* 482 */       this.mouseRect = null;
/*     */     }
/* 484 */     else if (this.uiTransfer.getSelectedItem() == null) {
/* 485 */       this.mouseRect = null;
/*     */     } 
/*     */ 
/*     */     
/* 489 */     BufferedImage image = drawGraphics();
/*     */     
/* 491 */     if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 492 */       int w = image.getWidth() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 493 */       int h = image.getHeight() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 494 */       image = DDSLoader.getScaledImage(image, w, h);
/*     */     } 
/*     */     
/* 497 */     setIcon(new ImageIcon(image));
/*     */   }
/*     */   
/*     */   private boolean isBlocked() {
/* 501 */     if (this.mouseRect == null) return true; 
/* 502 */     if (this.container == null) return true;
/*     */     
/* 504 */     int xr = (int)this.mouseRect.getX();
/* 505 */     int yr = (int)this.mouseRect.getY();
/* 506 */     int wr = (int)this.mouseRect.getWidth();
/* 507 */     int hr = (int)this.mouseRect.getHeight();
/*     */     
/* 509 */     int wp = getPreferredWidth();
/* 510 */     int hp = getPreferredHeight();
/*     */     
/* 512 */     if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 513 */       wp = wp * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/* 514 */       hp = hp * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/*     */     } 
/*     */ 
/*     */     
/* 518 */     if (xr + wr >= wp) {
/* 519 */       return true;
/*     */     }
/*     */     
/* 522 */     if (yr + hr >= hp) {
/* 523 */       return true;
/*     */     }
/*     */     
/* 526 */     if (this.container != null) {
/* 527 */       GDItem selItem = this.uiInventory.getSelectedItem();
/*     */       
/* 529 */       for (GDItem item : this.container.getItemList()) {
/*     */         
/* 531 */         if (item == selItem)
/*     */           continue; 
/* 533 */         BufferedImage img = item.getImage();
/*     */         
/* 535 */         if (img != null) {
/* 536 */           int xi = item.getX() * 32 + GDImagePool.getSharedStashXOffset();
/* 537 */           int yi = item.getY() * 32 + GDImagePool.getSharedStashYOffset();
/* 538 */           int wi = img.getWidth();
/* 539 */           int hi = img.getHeight();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 549 */           if (xr >= xi && xr <= xi + wi) {
/* 550 */             if (yr >= yi && yr <= yi + hi) {
/* 551 */               return true;
/*     */             }
/*     */             
/* 554 */             if (yr + hr >= yi && yr + hr <= yi + hi) {
/* 555 */               return true;
/*     */             }
/*     */           } 
/*     */           
/* 559 */           if (xr + wr >= xi && xr + wr <= xi + wi) {
/* 560 */             if (yr >= yi && yr <= yi + hi) {
/* 561 */               return true;
/*     */             }
/*     */             
/* 564 */             if (yr + hr >= yi && yr + hr <= yi + hi) {
/* 565 */               return true;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 572 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\stash\GDContainerPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */