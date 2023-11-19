/*      */ package org.gdstash.util;
/*      */ 
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import javax.imageio.ImageIO;
/*      */ import javax.swing.ImageIcon;
/*      */ import org.gdstash.db.DBBitmap;
/*      */ import org.gdstash.db.DBCaravanWindow;
/*      */ import org.gdstash.db.DBInventoryGrid;
/*      */ import org.gdstash.ui.GDStashFrame;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GDImagePool
/*      */ {
/*      */   public static ImageIcon iconProgress;
/*      */   public static ImageIcon iconLoading;
/*      */   public static ImageIcon iconLogo64x64;
/*      */   public static ImageIcon iconBoolOr;
/*      */   public static ImageIcon iconBoolOrUnselect;
/*      */   public static ImageIcon iconBoolAnd;
/*      */   public static ImageIcon iconBoolAndUnselect;
/*      */   public static BufferedImage imgDefaultSharedStash;
/*      */   private static BufferedImage imgCharBackground;
/*      */   public static BufferedImage imgDefaultCharStash;
/*      */   public static BufferedImage imgCharBag;
/*      */   public static BufferedImage imgCharInventory;
/*      */   public static BufferedImage imgCharEquipped;
/*      */   public static ImageIcon iconTab;
/*      */   public static ImageIcon iconTabHover;
/*      */   public static ImageIcon iconTabSelected;
/*      */   public static ImageIcon iconMsgError16;
/*      */   public static ImageIcon iconMsgInfo16;
/*      */   public static ImageIcon iconMsgSuccess16;
/*      */   public static ImageIcon iconMsgWarning16;
/*      */   public static ImageIcon iconTabCollection16;
/*      */   public static ImageIcon iconTabCollectionSet16;
/*      */   public static ImageIcon iconTabConfigure16;
/*      */   public static ImageIcon iconTabStashCraft16;
/*      */   public static ImageIcon iconTabFileImp16;
/*      */   public static ImageIcon iconTabStashTransfer16;
/*      */   public static ImageIcon iconTabCharEdit16;
/*      */   public static ImageIcon iconTabCharData16;
/*      */   public static ImageIcon iconTabCharPortal16;
/*      */   public static ImageIcon iconTabCharTransfer16;
/*      */   public static ImageIcon iconTabMasteryInfo16;
/*      */   public static ImageIcon iconCharEditMasteryReset16;
/*      */   public static ImageIcon iconPlus16;
/*      */   public static ImageIcon iconMinus16;
/*      */   public static ImageIcon iconBtnCancel24;
/*      */   public static ImageIcon iconBtnDir24;
/*      */   public static ImageIcon iconBtnFilter24;
/*      */   public static ImageIcon iconBtnOk24;
/*      */   public static ImageIcon iconBtnQuestion24;
/*      */   public static ImageIcon iconBtnReload24;
/*      */   public static ImageIcon iconBtnSave24;
/*      */   public static ImageIcon iconBtnSearch24;
/*      */   public static ImageIcon iconBtnTrash24;
/*      */   public static ImageIcon iconWindowCollapse24;
/*      */   public static ImageIcon iconWindowExpand24;
/*      */   public static ImageIcon iconDBDelete24;
/*      */   public static ImageIcon iconItemCopy24;
/*      */   public static ImageIcon iconItemDelete24;
/*      */   public static ImageIcon iconItemMove24;
/*      */   public static ImageIcon iconPageCopy24;
/*      */   public static ImageIcon iconPageDelete24;
/*      */   public static ImageIcon iconPageMove24;
/*      */   public static ImageIcon iconCollectAll24;
/*      */   public static ImageIcon iconCollectFound24;
/*      */   public static ImageIcon iconCollectMiss24;
/*      */   public static ImageIcon iconConfigARZExport24;
/*      */   public static ImageIcon iconConfigDBImport24;
/*      */   public static ImageIcon iconConfigTransmuteAdd24;
/*      */   public static ImageIcon iconConfigTransmuteFull24;
/*      */   public static ImageIcon iconConfigDevotionList24;
/*      */   public static ImageIcon iconConfigTextureConvert24;
/*      */   public static ImageIcon iconConfigSettingSave24;
/*      */   public static ImageIcon iconMassAll24;
/*      */   public static ImageIcon iconMassNone24;
/*      */   public static ImageIcon iconMassGDSExport24;
/*      */   public static ImageIcon iconMassGDSLoad24;
/*      */   public static ImageIcon iconMassIASLoad24;
/*      */   public static ImageIcon iconMassItemImport24;
/*      */   public static ImageIcon iconMassItemLoad24;
/*      */   public static ImageIcon iconMassUniqueClipboard24;
/*      */   public static ImageIcon iconMassLegendClipboard24;
/*      */   public static ImageIcon iconMassBlueprints24;
/*      */   public static ImageIcon iconMassValid24;
/*      */   public static ImageIcon iconTransGDSExport24;
/*      */   public static ImageIcon iconMsgError32;
/*      */   public static ImageIcon iconMsgInfo32;
/*      */   public static ImageIcon iconMsgSuccess32;
/*      */   public static ImageIcon iconMsgWarning32;
/*      */   public static BufferedImage imgBorderSquare;
/*      */   public static BufferedImage imgBorderRound;
/*      */   public static BufferedImage tileTopLeft;
/*      */   public static BufferedImage tileTopRight;
/*      */   public static BufferedImage tileTop;
/*      */   public static BufferedImage tileBottomLeft;
/*      */   public static BufferedImage tileBottomRight;
/*      */   public static BufferedImage tileBottom;
/*      */   public static BufferedImage tileCenterLeft;
/*      */   public static BufferedImage tileCenterRight;
/*      */   public static BufferedImage tileCenter;
/*      */   public static BufferedImage tileNoise;
/*      */   private static BufferedImage imgSharedStash;
/*      */   private static int sharedXOffset;
/*      */   private static int sharedYOffset;
/*      */   private static BufferedImage imgCraftingStash;
/*      */   private static int craftingXOffset;
/*      */   private static int craftingYOffset;
/*      */   private static BufferedImage imgCharStash;
/*      */   private static BufferedImage imgCharBG;
/*      */   private static int charXOffset;
/*      */   private static int charYOffset;
/*      */   public static final int STASH_X_OFFSET = 3;
/*      */   public static final int STASH_Y_OFFSET = 3;
/*      */   
/*      */   public static void loadLogos() {
/*  139 */     BufferedImage image = null;
/*      */     
/*      */     try {
/*  142 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GDS_Logo.png"));
/*      */       
/*  144 */       iconLogo64x64 = new ImageIcon(image);
/*  145 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  148 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GDStash_Progress_1.png"));
/*      */       
/*  150 */       iconProgress = new ImageIcon(image);
/*  151 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  154 */       if (GDStashFrame.expansionForgottenGods) {
/*  155 */         image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GDStash_Load_4.png"));
/*      */       }
/*  157 */       else if (GDStashFrame.expansionAshesOfMalmouth) {
/*  158 */         image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GDStash_Load_3.png"));
/*      */       } else {
/*  160 */         image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GDStash_Load_2.png"));
/*      */       } 
/*      */ 
/*      */       
/*  164 */       iconLoading = new ImageIcon(image);
/*  165 */     } catch (IOException iOException) {}
/*      */   }
/*      */   
/*      */   public static void loadTiles() {
/*  169 */     BufferedImage image = null;
/*      */     
/*      */     try {
/*  172 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GD_Char_BG.png"));
/*      */       
/*  174 */       imgCharBackground = image;
/*  175 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  178 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GD_SharedStash_Tiles.png"));
/*      */       
/*  180 */       imgDefaultSharedStash = image;
/*  181 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  184 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GD_CharStash_Tiles.png"));
/*      */       
/*  186 */       imgDefaultCharStash = image;
/*  187 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  190 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GD_CharInventory_Tiles.png"));
/*      */       
/*  192 */       imgCharInventory = image;
/*  193 */     } catch (IOException iOException) {}
/*      */ 
/*      */     
/*      */     try {
/*  197 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GD_Char_Equipment_Tiles.png"));
/*      */       
/*  199 */       imgCharEquipped = image;
/*  200 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  203 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GD_CharBag_Tiles.png"));
/*      */       
/*  205 */       imgCharBag = image;
/*  206 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  209 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GD_Tab.png"));
/*      */       
/*  211 */       iconTab = new ImageIcon(image);
/*  212 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  215 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GD_Tab_Hover.png"));
/*      */       
/*  217 */       iconTabHover = new ImageIcon(image);
/*  218 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  221 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GD_Tab_Selected.png"));
/*      */       
/*  223 */       iconTabSelected = new ImageIcon(image);
/*  224 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  227 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_TopLeft.png"));
/*      */       
/*  229 */       tileTopLeft = image;
/*  230 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  233 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_TopRight.png"));
/*      */       
/*  235 */       tileTopRight = image;
/*  236 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  239 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_Top.png"));
/*      */       
/*  241 */       tileTop = image;
/*  242 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  245 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_CenterLeft.png"));
/*      */       
/*  247 */       tileCenterLeft = image;
/*  248 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  251 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_CenterRight.png"));
/*      */       
/*  253 */       tileCenterRight = image;
/*  254 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  257 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_Center.png"));
/*      */       
/*  259 */       tileCenter = image;
/*  260 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  263 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_BottomLeft.png"));
/*      */       
/*  265 */       tileBottomLeft = image;
/*  266 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  269 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_BottomRight.png"));
/*      */       
/*  271 */       tileBottomRight = image;
/*  272 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  275 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_Bottom.png"));
/*      */       
/*  277 */       tileBottom = image;
/*  278 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  281 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_Noise.png"));
/*      */       
/*  283 */       tileNoise = image;
/*  284 */     } catch (IOException iOException) {}
/*      */ 
/*      */     
/*  287 */     buildSharedStash(8, 16);
/*  288 */     buildCraftingStash(8, 16);
/*  289 */     buildCharStash(10, 18);
/*      */   }
/*      */ 
/*      */   
/*      */   public static BufferedImage buildtSharedStashBG(int width, int height) {
/*  294 */     int tempwidth = width;
/*  295 */     int tempheight = height;
/*      */     
/*  297 */     if (tempwidth < 0 || tempwidth > 100) {
/*  298 */       if (GDStashFrame.expansionAshesOfMalmouth) {
/*  299 */         tempwidth = 10;
/*      */       } else {
/*  301 */         tempwidth = 8;
/*      */       } 
/*      */     }
/*  304 */     if (tempheight < 0 || tempheight > 100) {
/*  305 */       if (GDStashFrame.expansionAshesOfMalmouth) {
/*  306 */         tempheight = 18;
/*      */       } else {
/*  308 */         tempheight = 16;
/*      */       } 
/*      */     }
/*      */     
/*  312 */     int bgH = getSharedStashHeight();
/*  313 */     int bgW = getSharedStashBGWidth();
/*      */     
/*  315 */     if (tempwidth == bgW && tempheight == bgH) {
/*  316 */       BufferedImage img = getSharedStashBG();
/*      */       
/*  318 */       if (img != null) return img;
/*      */     
/*      */     } 
/*  321 */     if (tempwidth == 8 && tempheight == 16) {
/*  322 */       return imgDefaultSharedStash;
/*      */     }
/*      */     
/*  325 */     if (tempwidth == 10 && tempheight == 18) {
/*  326 */       return imgDefaultCharStash;
/*      */     }
/*      */     
/*  329 */     int tileTopW = tileTopLeft.getWidth();
/*  330 */     int tileTopH = tileTopLeft.getHeight();
/*  331 */     int tileBottomW = tileBottomRight.getWidth();
/*  332 */     int tileBottomH = tileBottomRight.getHeight();
/*      */     
/*  334 */     int w = tileTopW + (width - 2) * 32 + tileBottomW;
/*  335 */     int h = tileTopH + (height - 2) * 32 + tileBottomH;
/*  336 */     BufferedImage stash = new BufferedImage(w, h, 1);
/*      */     
/*  338 */     Graphics2D g = stash.createGraphics();
/*      */     
/*  340 */     BufferedImage tile = null;
/*  341 */     int yPos = 0; int y;
/*  342 */     for (y = 0; y < height; y++) {
/*  343 */       int xPos = 0; int x;
/*  344 */       for (x = 0; x < width; x++) {
/*  345 */         if (y == 0) {
/*  346 */           if (x == 0) {
/*  347 */             tile = tileTopLeft;
/*      */           }
/*  349 */           else if (x == width - 1) {
/*  350 */             tile = tileTopRight;
/*      */           } else {
/*  352 */             tile = tileTop;
/*      */           }
/*      */         
/*      */         }
/*  356 */         else if (y == height - 1) {
/*  357 */           if (x == 0) {
/*  358 */             tile = tileBottomLeft;
/*      */           }
/*  360 */           else if (x == width - 1) {
/*  361 */             tile = tileBottomRight;
/*      */           } else {
/*  363 */             tile = tileBottom;
/*      */           }
/*      */         
/*      */         }
/*  367 */         else if (x == 0) {
/*  368 */           tile = tileCenterLeft;
/*      */         }
/*  370 */         else if (x == width - 1) {
/*  371 */           tile = tileCenterRight;
/*      */         } else {
/*  373 */           tile = tileCenter;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  379 */         g.drawImage(tile, xPos, yPos, null);
/*      */         
/*  381 */         xPos += tile.getWidth();
/*      */       } 
/*      */       
/*  384 */       yPos += tile.getHeight();
/*      */     } 
/*      */     
/*  387 */     return stash;
/*      */   }
/*      */   
/*      */   public static int getSharedStashHeight() {
/*  391 */     DBInventoryGrid grid = DBInventoryGrid.get("records/ui/caravan/caravan_transferinventory.dbr");
/*      */     
/*  393 */     if (grid == null) return -1;
/*      */     
/*  395 */     int h = grid.getYSize();
/*      */     
/*  397 */     return h / 32;
/*      */   }
/*      */   
/*      */   public static int getSharedStashBGWidth() {
/*  401 */     DBInventoryGrid grid = DBInventoryGrid.get("records/ui/caravan/caravan_transferinventory.dbr");
/*      */     
/*  403 */     if (grid == null) return -1;
/*      */     
/*  405 */     int w = grid.getXSize();
/*      */     
/*  407 */     return w / 32;
/*      */   }
/*      */   
/*      */   public static BufferedImage getSharedStashBG() {
/*  411 */     DBBitmap bitmap = DBBitmap.get("records/ui/caravan/caravan_backgroundimage.dbr");
/*  412 */     DBInventoryGrid grid = DBInventoryGrid.get("records/ui/caravan/caravan_transferinventory.dbr");
/*  413 */     DBCaravanWindow window = DBCaravanWindow.get("records/ui/caravan/caravan_transferwindow.dbr");
/*      */     
/*  415 */     if (bitmap == null || grid == null || window == null) return null;
/*      */     
/*  417 */     int x = grid.getXOffset() + window.getXPos();
/*  418 */     int y = grid.getYOffset() + window.getYPos();
/*  419 */     int w = grid.getXSize();
/*  420 */     int h = grid.getYSize();
/*      */ 
/*      */ 
/*      */     
/*  424 */     h = h / 32 * 32;
/*  425 */     w = w / 32 * 32;
/*      */     
/*  427 */     BufferedImage img = bitmap.getImage();
/*      */     
/*  429 */     if (img != null) {
/*  430 */       img = img.getSubimage(x, y, w, h);
/*      */     }
/*      */ 
/*      */     
/*  434 */     BufferedImage frame = new BufferedImage(img.getWidth() + 6 + 2, img.getHeight() + 6 + 2, img.getType());
/*  435 */     Graphics2D g = frame.createGraphics();
/*      */     
/*  437 */     g.drawImage(img, 4, 4, null);
/*      */     
/*  439 */     return frame;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void buildSharedStash(int width, int height) {
/*  444 */     int tempwidth = width;
/*  445 */     int tempheight = height;
/*      */     
/*  447 */     if (tempwidth < 0 || tempwidth > 100) {
/*  448 */       if (GDStashFrame.expansionAshesOfMalmouth) {
/*  449 */         tempwidth = 10;
/*      */       } else {
/*  451 */         tempwidth = 8;
/*      */       } 
/*      */     }
/*  454 */     if (tempheight < 0 || tempheight > 100) {
/*  455 */       if (GDStashFrame.expansionAshesOfMalmouth) {
/*  456 */         tempheight = 18;
/*      */       } else {
/*  458 */         tempheight = 16;
/*      */       } 
/*      */     }
/*      */     
/*  462 */     if (tempwidth == 8 && tempheight == 16) {
/*  463 */       imgSharedStash = imgDefaultSharedStash;
/*  464 */       sharedXOffset = 2;
/*  465 */       sharedYOffset = 2;
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  470 */     if (tempwidth == 10 && tempheight == 18) {
/*  471 */       imgSharedStash = imgDefaultCharStash;
/*  472 */       sharedXOffset = 2;
/*  473 */       sharedYOffset = 2;
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  478 */     int tileTopW = tileTopLeft.getWidth();
/*  479 */     int tileTopH = tileTopLeft.getHeight();
/*  480 */     int tileBottomW = tileBottomRight.getWidth();
/*  481 */     int tileBottomH = tileBottomRight.getHeight();
/*      */     
/*  483 */     int w = tileTopW + (width - 2) * 32 + tileBottomW;
/*  484 */     int h = tileTopH + (height - 2) * 32 + tileBottomH;
/*  485 */     BufferedImage stash = new BufferedImage(w, h, 1);
/*      */     
/*  487 */     Graphics2D g = stash.createGraphics();
/*      */     
/*  489 */     BufferedImage tile = null;
/*  490 */     int yPos = 0; int y;
/*  491 */     for (y = 0; y < height; y++) {
/*  492 */       int xPos = 0; int x;
/*  493 */       for (x = 0; x < width; x++) {
/*  494 */         if (y == 0) {
/*  495 */           if (x == 0) {
/*  496 */             tile = tileTopLeft;
/*      */           }
/*  498 */           else if (x == width - 1) {
/*  499 */             tile = tileTopRight;
/*      */           } else {
/*  501 */             tile = tileTop;
/*      */           }
/*      */         
/*      */         }
/*  505 */         else if (y == height - 1) {
/*  506 */           if (x == 0) {
/*  507 */             tile = tileBottomLeft;
/*      */           }
/*  509 */           else if (x == width - 1) {
/*  510 */             tile = tileBottomRight;
/*      */           } else {
/*  512 */             tile = tileBottom;
/*      */           }
/*      */         
/*      */         }
/*  516 */         else if (x == 0) {
/*  517 */           tile = tileCenterLeft;
/*      */         }
/*  519 */         else if (x == width - 1) {
/*  520 */           tile = tileCenterRight;
/*      */         } else {
/*  522 */           tile = tileCenter;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  528 */         g.drawImage(tile, xPos, yPos, null);
/*      */         
/*  530 */         xPos += tile.getWidth();
/*      */       } 
/*      */       
/*  533 */       yPos += tile.getHeight();
/*      */     } 
/*      */     
/*  536 */     imgSharedStash = stash;
/*  537 */     sharedXOffset = tileTopW - 32;
/*  538 */     sharedYOffset = tileTopH - 32;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void buildCraftingStash(int width, int height) {
/*  543 */     int tempwidth = width;
/*  544 */     int tempheight = height;
/*      */     
/*  546 */     if (tempwidth < 0 || tempwidth > 100) {
/*  547 */       if (GDStashFrame.expansionAshesOfMalmouth) {
/*  548 */         tempwidth = 10;
/*      */       } else {
/*  550 */         tempwidth = 8;
/*      */       } 
/*      */     }
/*  553 */     if (tempheight < 0 || tempheight > 100) {
/*  554 */       if (GDStashFrame.expansionAshesOfMalmouth) {
/*  555 */         tempheight = 18;
/*      */       } else {
/*  557 */         tempheight = 16;
/*      */       } 
/*      */     }
/*      */     
/*  561 */     if (tempwidth == 8 && tempheight == 16) {
/*  562 */       imgCraftingStash = imgDefaultSharedStash;
/*  563 */       craftingXOffset = 2;
/*  564 */       craftingYOffset = 2;
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  569 */     if (tempwidth == 10 && tempheight == 18) {
/*  570 */       imgCraftingStash = imgDefaultCharStash;
/*  571 */       craftingXOffset = 2;
/*  572 */       craftingYOffset = 2;
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  577 */     int tileTopW = tileTopLeft.getWidth();
/*  578 */     int tileTopH = tileTopLeft.getHeight();
/*  579 */     int tileBottomW = tileBottomRight.getWidth();
/*  580 */     int tileBottomH = tileBottomRight.getHeight();
/*      */     
/*  582 */     int w = tileTopW + (width - 2) * 32 + tileBottomW;
/*  583 */     int h = tileTopH + (height - 2) * 32 + tileBottomH;
/*  584 */     BufferedImage stash = new BufferedImage(w, h, 1);
/*      */     
/*  586 */     Graphics2D g = stash.createGraphics();
/*      */     
/*  588 */     BufferedImage tile = null;
/*  589 */     int yPos = 0; int y;
/*  590 */     for (y = 0; y < height; y++) {
/*  591 */       int xPos = 0; int x;
/*  592 */       for (x = 0; x < width; x++) {
/*  593 */         if (y == 0) {
/*  594 */           if (x == 0) {
/*  595 */             tile = tileTopLeft;
/*      */           }
/*  597 */           else if (x == width - 1) {
/*  598 */             tile = tileTopRight;
/*      */           } else {
/*  600 */             tile = tileTop;
/*      */           }
/*      */         
/*      */         }
/*  604 */         else if (y == height - 1) {
/*  605 */           if (x == 0) {
/*  606 */             tile = tileBottomLeft;
/*      */           }
/*  608 */           else if (x == width - 1) {
/*  609 */             tile = tileBottomRight;
/*      */           } else {
/*  611 */             tile = tileBottom;
/*      */           }
/*      */         
/*      */         }
/*  615 */         else if (x == 0) {
/*  616 */           tile = tileCenterLeft;
/*      */         }
/*  618 */         else if (x == width - 1) {
/*  619 */           tile = tileCenterRight;
/*      */         } else {
/*  621 */           tile = tileCenter;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  627 */         g.drawImage(tile, xPos, yPos, null);
/*      */         
/*  629 */         xPos += tile.getWidth();
/*      */       } 
/*      */       
/*  632 */       yPos += tile.getHeight();
/*      */     } 
/*      */     
/*  635 */     imgCraftingStash = stash;
/*  636 */     craftingXOffset = tileTopW - 32;
/*  637 */     craftingYOffset = tileTopH - 32;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void buildCharStash(int width, int height) {
/*  642 */     int tempwidth = width;
/*  643 */     int tempheight = height;
/*      */     
/*  645 */     if (tempwidth < 0 || tempwidth > 100) tempwidth = 10; 
/*  646 */     if (tempheight < 0 || tempheight > 100) tempheight = 18;
/*      */     
/*  648 */     if (tempwidth == 10 && tempheight == 18) {
/*  649 */       imgCharStash = imgDefaultCharStash;
/*  650 */       imgCharBG = imgCharBackground;
/*  651 */       charXOffset = 2;
/*  652 */       charYOffset = 2;
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  657 */     int tileTopW = tileTopLeft.getWidth();
/*  658 */     int tileTopH = tileTopLeft.getHeight();
/*  659 */     int tileBottomW = tileBottomRight.getWidth();
/*  660 */     int tileBottomH = tileBottomRight.getHeight();
/*      */ 
/*      */     
/*  663 */     int w = tileTopW + (width - 2) * 32 + tileBottomW;
/*  664 */     int h = tileTopH + (height - 2) * 32 + tileBottomH;
/*  665 */     BufferedImage stash = new BufferedImage(w, h, 1);
/*      */     
/*  667 */     Graphics2D g = stash.createGraphics();
/*      */     
/*  669 */     BufferedImage tile = null;
/*  670 */     int yPos = 0; int y;
/*  671 */     for (y = 0; y < height; y++) {
/*  672 */       int xPos = 0; int x;
/*  673 */       for (x = 0; x < width; x++) {
/*  674 */         if (y == 0) {
/*  675 */           if (x == 0) {
/*  676 */             tile = tileTopLeft;
/*      */           }
/*  678 */           else if (x == width - 1) {
/*  679 */             tile = tileTopRight;
/*      */           } else {
/*  681 */             tile = tileTop;
/*      */           }
/*      */         
/*      */         }
/*  685 */         else if (y == height - 1) {
/*  686 */           if (x == 0) {
/*  687 */             tile = tileBottomLeft;
/*      */           }
/*  689 */           else if (x == width - 1) {
/*  690 */             tile = tileBottomRight;
/*      */           } else {
/*  692 */             tile = tileBottom;
/*      */           }
/*      */         
/*      */         }
/*  696 */         else if (x == 0) {
/*  697 */           tile = tileCenterLeft;
/*      */         }
/*  699 */         else if (x == width - 1) {
/*  700 */           tile = tileCenterRight;
/*      */         } else {
/*  702 */           tile = tileCenter;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  708 */         g.drawImage(tile, xPos, yPos, null);
/*      */         
/*  710 */         xPos += tile.getWidth();
/*      */       } 
/*      */       
/*  713 */       yPos += tile.getHeight();
/*      */     } 
/*      */     
/*  716 */     w = tileTopW + (width - 2) * 32 + tileBottomW + 234;
/*  717 */     h = tileTopH + (height - 2) * 32 + tileBottomH + 198;
/*  718 */     BufferedImage bg = new BufferedImage(w, h, 1);
/*      */     
/*  720 */     g = bg.createGraphics();
/*      */     
/*  722 */     tile = tileNoise; int i;
/*  723 */     for (i = 0; i < h; i += tile.getHeight()) {
/*  724 */       int x; for (x = 0; x < w; x += tile.getWidth()) {
/*  725 */         g.drawImage(tile, x, i, null);
/*      */       }
/*      */     } 
/*      */     
/*  729 */     imgCharStash = stash;
/*  730 */     imgCharBG = bg;
/*  731 */     charXOffset = tileTopW - 32;
/*  732 */     charYOffset = tileTopH - 32;
/*      */   }
/*      */   
/*      */   public static void loadImages() {
/*  736 */     String dir16 = "16";
/*  737 */     String dir24 = "24";
/*  738 */     String dir32 = "32";
/*      */ 
/*      */     
/*  741 */     if (GDStashFrame.iniConfig.sectUI.fontSize < 12) {
/*  742 */       dir24 = "16";
/*  743 */       dir32 = "24";
/*      */     } 
/*      */     
/*  746 */     if (GDStashFrame.iniConfig.sectUI.fontSize > 14) {
/*  747 */       dir16 = "24";
/*  748 */       dir24 = "32";
/*      */     } 
/*      */     
/*  751 */     if (GDStashFrame.iniConfig.sectUI.fontSize > 16) {
/*  752 */       dir32 = "48";
/*      */     }
/*      */     
/*  755 */     dir16 = "image" + GDConstants.FILE_SEPARATOR + "icons" + GDConstants.FILE_SEPARATOR + dir16 + GDConstants.FILE_SEPARATOR;
/*      */ 
/*      */     
/*  758 */     dir24 = "image" + GDConstants.FILE_SEPARATOR + "icons" + GDConstants.FILE_SEPARATOR + dir24 + GDConstants.FILE_SEPARATOR;
/*      */ 
/*      */     
/*  761 */     dir32 = "image" + GDConstants.FILE_SEPARATOR + "icons" + GDConstants.FILE_SEPARATOR + dir32 + GDConstants.FILE_SEPARATOR;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  766 */     BufferedImage image = null;
/*      */     
/*      */     try {
/*  769 */       image = ImageIO.read(new File(dir24 + "bool_or_sel.png"));
/*      */       
/*  771 */       iconBoolOr = new ImageIcon(image);
/*  772 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  775 */       image = ImageIO.read(new File(dir24 + "bool_or_unsel.png"));
/*      */       
/*  777 */       iconBoolOrUnselect = new ImageIcon(image);
/*  778 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  781 */       image = ImageIO.read(new File(dir24 + "bool_and_sel.png"));
/*      */       
/*  783 */       iconBoolAnd = new ImageIcon(image);
/*  784 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  787 */       image = ImageIO.read(new File(dir24 + "bool_and_unsel.png"));
/*      */       
/*  789 */       iconBoolAndUnselect = new ImageIcon(image);
/*  790 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  793 */       image = ImageIO.read(new File(dir16 + "msg_error.png"));
/*      */       
/*  795 */       iconMsgError16 = new ImageIcon(image);
/*  796 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  799 */       image = ImageIO.read(new File(dir16 + "msg_info.png"));
/*      */       
/*  801 */       iconMsgInfo16 = new ImageIcon(image);
/*  802 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  805 */       image = ImageIO.read(new File(dir16 + "msg_success.png"));
/*      */       
/*  807 */       iconMsgSuccess16 = new ImageIcon(image);
/*  808 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  811 */       image = ImageIO.read(new File(dir16 + "msg_warn.png"));
/*      */       
/*  813 */       iconMsgWarning16 = new ImageIcon(image);
/*  814 */     } catch (IOException iOException) {}
/*      */ 
/*      */     
/*      */     try {
/*  818 */       image = ImageIO.read(new File(dir16 + "tab_collect.png"));
/*      */       
/*  820 */       iconTabCollection16 = new ImageIcon(image);
/*  821 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  824 */       image = ImageIO.read(new File(dir16 + "tab_collect_set.png"));
/*      */       
/*  826 */       iconTabCollectionSet16 = new ImageIcon(image);
/*  827 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  830 */       image = ImageIO.read(new File(dir16 + "tab_config.png"));
/*      */       
/*  832 */       iconTabConfigure16 = new ImageIcon(image);
/*  833 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  836 */       image = ImageIO.read(new File(dir16 + "tab_file_import.png"));
/*      */       
/*  838 */       iconTabFileImp16 = new ImageIcon(image);
/*  839 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  842 */       image = ImageIO.read(new File(dir16 + "tab_stash_craft.png"));
/*      */       
/*  844 */       iconTabStashCraft16 = new ImageIcon(image);
/*  845 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  848 */       image = ImageIO.read(new File(dir16 + "tab_stash_trans.png"));
/*      */       
/*  850 */       iconTabStashTransfer16 = new ImageIcon(image);
/*  851 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  854 */       image = ImageIO.read(new File(dir16 + "tab_char_edit.png"));
/*      */       
/*  856 */       iconTabCharEdit16 = new ImageIcon(image);
/*  857 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  860 */       image = ImageIO.read(new File(dir16 + "tab_char_data.png"));
/*      */       
/*  862 */       iconTabCharData16 = new ImageIcon(image);
/*  863 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  866 */       image = ImageIO.read(new File(dir16 + "tab_char_portal.png"));
/*      */       
/*  868 */       iconTabCharPortal16 = new ImageIcon(image);
/*  869 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  872 */       image = ImageIO.read(new File(dir16 + "tab_char_trans.png"));
/*      */       
/*  874 */       iconTabCharTransfer16 = new ImageIcon(image);
/*  875 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  878 */       image = ImageIO.read(new File(dir16 + "tab_mastery_info.png"));
/*      */       
/*  880 */       iconTabMasteryInfo16 = new ImageIcon(image);
/*  881 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  884 */       image = ImageIO.read(new File(dir16 + "btn_cancel.png"));
/*      */       
/*  886 */       iconCharEditMasteryReset16 = new ImageIcon(image);
/*  887 */     } catch (IOException iOException) {}
/*      */ 
/*      */     
/*      */     try {
/*  891 */       image = ImageIO.read(new File(dir16 + "btn_plus.png"));
/*      */       
/*  893 */       iconPlus16 = new ImageIcon(image);
/*  894 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  897 */       image = ImageIO.read(new File(dir16 + "btn_minus.png"));
/*      */       
/*  899 */       iconMinus16 = new ImageIcon(image);
/*  900 */     } catch (IOException iOException) {}
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  905 */       image = ImageIO.read(new File(dir24 + "btn_cancel.png"));
/*      */       
/*  907 */       iconBtnCancel24 = new ImageIcon(image);
/*  908 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  911 */       image = ImageIO.read(new File(dir24 + "btn_dir.png"));
/*      */       
/*  913 */       iconBtnDir24 = new ImageIcon(image);
/*  914 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  917 */       image = ImageIO.read(new File(dir24 + "btn_filter.png"));
/*      */       
/*  919 */       iconBtnFilter24 = new ImageIcon(image);
/*  920 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  923 */       image = ImageIO.read(new File(dir24 + "btn_ok.png"));
/*      */       
/*  925 */       iconBtnOk24 = new ImageIcon(image);
/*  926 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  929 */       image = ImageIO.read(new File(dir24 + "btn_question.png"));
/*      */       
/*  931 */       iconBtnQuestion24 = new ImageIcon(image);
/*  932 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  935 */       image = ImageIO.read(new File(dir24 + "btn_reload.png"));
/*      */       
/*  937 */       iconBtnReload24 = new ImageIcon(image);
/*  938 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  941 */       image = ImageIO.read(new File(dir24 + "btn_save.png"));
/*      */       
/*  943 */       iconBtnSave24 = new ImageIcon(image);
/*  944 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  947 */       image = ImageIO.read(new File(dir24 + "btn_search.png"));
/*      */       
/*  949 */       iconBtnSearch24 = new ImageIcon(image);
/*  950 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  953 */       image = ImageIO.read(new File(dir24 + "btn_trash.png"));
/*      */       
/*  955 */       iconBtnTrash24 = new ImageIcon(image);
/*  956 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  959 */       image = ImageIO.read(new File(dir24 + "window_collapse_oxygen.png"));
/*      */       
/*  961 */       iconWindowCollapse24 = new ImageIcon(image);
/*  962 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  965 */       image = ImageIO.read(new File(dir24 + "window_expand_oxygen.png"));
/*      */       
/*  967 */       iconWindowExpand24 = new ImageIcon(image);
/*  968 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  971 */       image = ImageIO.read(new File(dir24 + "db_delete.png"));
/*      */       
/*  973 */       iconDBDelete24 = new ImageIcon(image);
/*  974 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  977 */       image = ImageIO.read(new File(dir24 + "item_copy.png"));
/*      */       
/*  979 */       iconItemCopy24 = new ImageIcon(image);
/*  980 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  983 */       image = ImageIO.read(new File(dir24 + "item_delete.png"));
/*      */       
/*  985 */       iconItemDelete24 = new ImageIcon(image);
/*  986 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  989 */       image = ImageIO.read(new File(dir24 + "item_move.png"));
/*      */       
/*  991 */       iconItemMove24 = new ImageIcon(image);
/*  992 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  995 */       image = ImageIO.read(new File(dir24 + "page_copy.png"));
/*      */       
/*  997 */       iconPageCopy24 = new ImageIcon(image);
/*  998 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1001 */       image = ImageIO.read(new File(dir24 + "page_delete.png"));
/*      */       
/* 1003 */       iconPageDelete24 = new ImageIcon(image);
/* 1004 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1007 */       image = ImageIO.read(new File(dir24 + "page_move.png"));
/*      */       
/* 1009 */       iconPageMove24 = new ImageIcon(image);
/* 1010 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1013 */       image = ImageIO.read(new File(dir24 + "coll_all.png"));
/*      */       
/* 1015 */       iconCollectAll24 = new ImageIcon(image);
/* 1016 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1019 */       image = ImageIO.read(new File(dir24 + "coll_found.png"));
/*      */       
/* 1021 */       iconCollectFound24 = new ImageIcon(image);
/* 1022 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1025 */       image = ImageIO.read(new File(dir24 + "coll_miss.png"));
/*      */       
/* 1027 */       iconCollectMiss24 = new ImageIcon(image);
/* 1028 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1031 */       image = ImageIO.read(new File(dir24 + "cfg_arz_exp.png"));
/*      */       
/* 1033 */       iconConfigARZExport24 = new ImageIcon(image);
/* 1034 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1037 */       image = ImageIO.read(new File(dir24 + "cfg_devotion.png"));
/*      */       
/* 1039 */       iconConfigDevotionList24 = new ImageIcon(image);
/* 1040 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1043 */       image = ImageIO.read(new File(dir24 + "cfg_texconv.png"));
/*      */       
/* 1045 */       iconConfigTextureConvert24 = new ImageIcon(image);
/* 1046 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1049 */       image = ImageIO.read(new File(dir24 + "cfg_db_imp.png"));
/*      */       
/* 1051 */       iconConfigDBImport24 = new ImageIcon(image);
/* 1052 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1055 */       image = ImageIO.read(new File(dir24 + "cfg_save.png"));
/*      */       
/* 1057 */       iconConfigSettingSave24 = new ImageIcon(image);
/* 1058 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1061 */       image = ImageIO.read(new File(dir24 + "cfg_transmute_add.png"));
/*      */       
/* 1063 */       iconConfigTransmuteAdd24 = new ImageIcon(image);
/* 1064 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1067 */       image = ImageIO.read(new File(dir24 + "cfg_transmute_full.png"));
/*      */       
/* 1069 */       iconConfigTransmuteFull24 = new ImageIcon(image);
/* 1070 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1073 */       image = ImageIO.read(new File(dir24 + "mass_all.png"));
/*      */       
/* 1075 */       iconMassAll24 = new ImageIcon(image);
/* 1076 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1079 */       image = ImageIO.read(new File(dir24 + "mass_none.png"));
/*      */       
/* 1081 */       iconMassNone24 = new ImageIcon(image);
/* 1082 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1085 */       image = ImageIO.read(new File(dir24 + "mass_valid.png"));
/*      */       
/* 1087 */       iconMassValid24 = new ImageIcon(image);
/* 1088 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1091 */       image = ImageIO.read(new File(dir24 + "mass_gds_exp.png"));
/*      */       
/* 1093 */       iconMassGDSExport24 = new ImageIcon(image);
/* 1094 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1097 */       image = ImageIO.read(new File(dir24 + "mass_gds_load.png"));
/*      */       
/* 1099 */       iconMassGDSLoad24 = new ImageIcon(image);
/* 1100 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1103 */       image = ImageIO.read(new File(dir24 + "mass_ias_load.png"));
/*      */       
/* 1105 */       iconMassIASLoad24 = new ImageIcon(image);
/* 1106 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1109 */       image = ImageIO.read(new File(dir24 + "mass_item_imp.png"));
/*      */       
/* 1111 */       iconMassItemImport24 = new ImageIcon(image);
/* 1112 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1115 */       image = ImageIO.read(new File(dir24 + "mass_item_load.png"));
/*      */       
/* 1117 */       iconMassItemLoad24 = new ImageIcon(image);
/* 1118 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1121 */       image = ImageIO.read(new File(dir24 + "mass_item_export.png"));
/*      */       
/* 1123 */       iconMassUniqueClipboard24 = new ImageIcon(image);
/* 1124 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1127 */       image = ImageIO.read(new File(dir24 + "mass_legend_export.png"));
/*      */       
/* 1129 */       iconMassLegendClipboard24 = new ImageIcon(image);
/* 1130 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1133 */       image = ImageIO.read(new File(dir24 + "mass_formula.png"));
/*      */       
/* 1135 */       iconMassBlueprints24 = new ImageIcon(image);
/* 1136 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1139 */       image = ImageIO.read(new File(dir24 + "trans_exp.png"));
/*      */       
/* 1141 */       iconTransGDSExport24 = new ImageIcon(image);
/* 1142 */     } catch (IOException iOException) {}
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1147 */       image = ImageIO.read(new File(dir32 + "msg_error.png"));
/*      */       
/* 1149 */       iconMsgError32 = new ImageIcon(image);
/* 1150 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1153 */       image = ImageIO.read(new File(dir32 + "msg_info.png"));
/*      */       
/* 1155 */       iconMsgInfo32 = new ImageIcon(image);
/* 1156 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1159 */       image = ImageIO.read(new File(dir32 + "msg_success.png"));
/*      */       
/* 1161 */       iconMsgSuccess32 = new ImageIcon(image);
/* 1162 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1165 */       image = ImageIO.read(new File(dir32 + "msg_warn.png"));
/*      */       
/* 1167 */       iconMsgWarning32 = new ImageIcon(image);
/* 1168 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1171 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "skills_buttonborder01.png"));
/*      */       
/* 1173 */       imgBorderSquare = image;
/* 1174 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1177 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "skills_buttonborderround01.png"));
/*      */       
/* 1179 */       imgBorderRound = image;
/* 1180 */     } catch (IOException iOException) {}
/*      */   }
/*      */   
/*      */   public static BufferedImage getSharedStashGrid() {
/* 1184 */     return imgSharedStash;
/*      */   }
/*      */   
/*      */   public static int getSharedStashXOffset() {
/* 1188 */     return sharedXOffset;
/*      */   }
/*      */   
/*      */   public static int getSharedStashYOffset() {
/* 1192 */     return sharedYOffset;
/*      */   }
/*      */   
/*      */   public static BufferedImage getCraftingStashGrid() {
/* 1196 */     return imgCraftingStash;
/*      */   }
/*      */   
/*      */   public static int getCraftingStashXOffset() {
/* 1200 */     return craftingXOffset;
/*      */   }
/*      */   
/*      */   public static int getCraftingStashYOffset() {
/* 1204 */     return craftingYOffset;
/*      */   }
/*      */   
/*      */   public static BufferedImage getCharStashGrid() {
/* 1208 */     return imgCharStash;
/*      */   }
/*      */   
/*      */   public static BufferedImage getCharStashBG() {
/* 1212 */     return imgCharBG;
/*      */   }
/*      */   
/*      */   public static BufferedImage copyImage(BufferedImage source) {
/* 1216 */     BufferedImage img = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
/* 1217 */     Graphics g = img.getGraphics();
/*      */     
/* 1219 */     g.drawImage(source, 0, 0, null);
/* 1220 */     g.dispose();
/*      */     
/* 1222 */     return img;
/*      */   }
/*      */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\util\GDImagePool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */