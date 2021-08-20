/*      */ package org.gdstash.util;
/*      */ 
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import javax.imageio.ImageIO;
/*      */ import javax.swing.ImageIcon;
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
/*      */   
/*      */   public static void loadLogos() {
/*  132 */     BufferedImage image = null;
/*      */     
/*      */     try {
/*  135 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GDS_Logo.png"));
/*      */       
/*  137 */       iconLogo64x64 = new ImageIcon(image);
/*  138 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  141 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GDStash_Progress_1.png"));
/*      */       
/*  143 */       iconProgress = new ImageIcon(image);
/*  144 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  147 */       if (GDStashFrame.expansionForgottenGods) {
/*  148 */         image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GDStash_Load_4.png"));
/*      */       }
/*  150 */       else if (GDStashFrame.expansionAshesOfMalmouth) {
/*  151 */         image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GDStash_Load_3.png"));
/*      */       } else {
/*  153 */         image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GDStash_Load_2.png"));
/*      */       } 
/*      */ 
/*      */       
/*  157 */       iconLoading = new ImageIcon(image);
/*  158 */     } catch (IOException iOException) {}
/*      */   }
/*      */   
/*      */   public static void loadTiles() {
/*  162 */     BufferedImage image = null;
/*      */     
/*      */     try {
/*  165 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GD_Char_BG.png"));
/*      */       
/*  167 */       imgCharBackground = image;
/*  168 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  171 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GD_SharedStash_Tiles.png"));
/*      */       
/*  173 */       imgDefaultSharedStash = image;
/*  174 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  177 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GD_CharStash_Tiles.png"));
/*      */       
/*  179 */       imgDefaultCharStash = image;
/*  180 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  183 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GD_CharInventory_Tiles.png"));
/*      */       
/*  185 */       imgCharInventory = image;
/*  186 */     } catch (IOException iOException) {}
/*      */ 
/*      */     
/*      */     try {
/*  190 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GD_Char_Equipment_Tiles.png"));
/*      */       
/*  192 */       imgCharEquipped = image;
/*  193 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  196 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GD_CharBag_Tiles.png"));
/*      */       
/*  198 */       imgCharBag = image;
/*  199 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  202 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GD_Tab.png"));
/*      */       
/*  204 */       iconTab = new ImageIcon(image);
/*  205 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  208 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GD_Tab_Hover.png"));
/*      */       
/*  210 */       iconTabHover = new ImageIcon(image);
/*  211 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  214 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "GD_Tab_Selected.png"));
/*      */       
/*  216 */       iconTabSelected = new ImageIcon(image);
/*  217 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  220 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_TopLeft.png"));
/*      */       
/*  222 */       tileTopLeft = image;
/*  223 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  226 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_TopRight.png"));
/*      */       
/*  228 */       tileTopRight = image;
/*  229 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  232 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_Top.png"));
/*      */       
/*  234 */       tileTop = image;
/*  235 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  238 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_CenterLeft.png"));
/*      */       
/*  240 */       tileCenterLeft = image;
/*  241 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  244 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_CenterRight.png"));
/*      */       
/*  246 */       tileCenterRight = image;
/*  247 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  250 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_Center.png"));
/*      */       
/*  252 */       tileCenter = image;
/*  253 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  256 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_BottomLeft.png"));
/*      */       
/*  258 */       tileBottomLeft = image;
/*  259 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  262 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_BottomRight.png"));
/*      */       
/*  264 */       tileBottomRight = image;
/*  265 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  268 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_Bottom.png"));
/*      */       
/*  270 */       tileBottom = image;
/*  271 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  274 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "tiles" + GDConstants.FILE_SEPARATOR + "Tile_Noise.png"));
/*      */       
/*  276 */       tileNoise = image;
/*  277 */     } catch (IOException iOException) {}
/*      */ 
/*      */     
/*  280 */     buildSharedStash(8, 16);
/*  281 */     buildCraftingStash(8, 16);
/*  282 */     buildCharStash(10, 18);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void buildSharedStash(int width, int height) {
/*  287 */     int tempwidth = width;
/*  288 */     int tempheight = height;
/*      */     
/*  290 */     if (tempwidth < 0 || tempwidth > 100) {
/*  291 */       if (GDStashFrame.expansionAshesOfMalmouth) {
/*  292 */         tempwidth = 10;
/*      */       } else {
/*  294 */         tempwidth = 8;
/*      */       } 
/*      */     }
/*  297 */     if (tempheight < 0 || tempheight > 100) {
/*  298 */       if (GDStashFrame.expansionAshesOfMalmouth) {
/*  299 */         tempheight = 18;
/*      */       } else {
/*  301 */         tempheight = 16;
/*      */       } 
/*      */     }
/*      */     
/*  305 */     if (tempwidth == 8 && tempheight == 16) {
/*  306 */       imgSharedStash = imgDefaultSharedStash;
/*  307 */       sharedXOffset = 2;
/*  308 */       sharedYOffset = 2;
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  313 */     if (tempwidth == 10 && tempheight == 18) {
/*  314 */       imgSharedStash = imgDefaultCharStash;
/*  315 */       sharedXOffset = 2;
/*  316 */       sharedYOffset = 2;
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  321 */     int tileTopW = tileTopLeft.getWidth();
/*  322 */     int tileTopH = tileTopLeft.getHeight();
/*  323 */     int tileBottomW = tileBottomRight.getWidth();
/*  324 */     int tileBottomH = tileBottomRight.getHeight();
/*      */     
/*  326 */     int w = tileTopW + (width - 2) * 32 + tileBottomW;
/*  327 */     int h = tileTopH + (height - 2) * 32 + tileBottomH;
/*  328 */     BufferedImage stash = new BufferedImage(w, h, 1);
/*      */     
/*  330 */     Graphics2D g = stash.createGraphics();
/*      */     
/*  332 */     BufferedImage tile = null;
/*  333 */     int yPos = 0; int y;
/*  334 */     for (y = 0; y < height; y++) {
/*  335 */       int xPos = 0; int x;
/*  336 */       for (x = 0; x < width; x++) {
/*  337 */         if (y == 0) {
/*  338 */           if (x == 0) {
/*  339 */             tile = tileTopLeft;
/*      */           }
/*  341 */           else if (x == width - 1) {
/*  342 */             tile = tileTopRight;
/*      */           } else {
/*  344 */             tile = tileTop;
/*      */           }
/*      */         
/*      */         }
/*  348 */         else if (y == height - 1) {
/*  349 */           if (x == 0) {
/*  350 */             tile = tileBottomLeft;
/*      */           }
/*  352 */           else if (x == width - 1) {
/*  353 */             tile = tileBottomRight;
/*      */           } else {
/*  355 */             tile = tileBottom;
/*      */           }
/*      */         
/*      */         }
/*  359 */         else if (x == 0) {
/*  360 */           tile = tileCenterLeft;
/*      */         }
/*  362 */         else if (x == width - 1) {
/*  363 */           tile = tileCenterRight;
/*      */         } else {
/*  365 */           tile = tileCenter;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  371 */         g.drawImage(tile, xPos, yPos, null);
/*      */         
/*  373 */         xPos += tile.getWidth();
/*      */       } 
/*      */       
/*  376 */       yPos += tile.getHeight();
/*      */     } 
/*      */     
/*  379 */     imgSharedStash = stash;
/*  380 */     sharedXOffset = tileTopW - 32;
/*  381 */     sharedYOffset = tileTopH - 32;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void buildCraftingStash(int width, int height) {
/*  386 */     int tempwidth = width;
/*  387 */     int tempheight = height;
/*      */     
/*  389 */     if (tempwidth < 0 || tempwidth > 100) {
/*  390 */       if (GDStashFrame.expansionAshesOfMalmouth) {
/*  391 */         tempwidth = 10;
/*      */       } else {
/*  393 */         tempwidth = 8;
/*      */       } 
/*      */     }
/*  396 */     if (tempheight < 0 || tempheight > 100) {
/*  397 */       if (GDStashFrame.expansionAshesOfMalmouth) {
/*  398 */         tempheight = 18;
/*      */       } else {
/*  400 */         tempheight = 16;
/*      */       } 
/*      */     }
/*      */     
/*  404 */     if (tempwidth == 8 && tempheight == 16) {
/*  405 */       imgCraftingStash = imgDefaultSharedStash;
/*  406 */       craftingXOffset = 2;
/*  407 */       craftingYOffset = 2;
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  412 */     if (tempwidth == 10 && tempheight == 18) {
/*  413 */       imgCraftingStash = imgDefaultCharStash;
/*  414 */       craftingXOffset = 2;
/*  415 */       craftingYOffset = 2;
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  420 */     int tileTopW = tileTopLeft.getWidth();
/*  421 */     int tileTopH = tileTopLeft.getHeight();
/*  422 */     int tileBottomW = tileBottomRight.getWidth();
/*  423 */     int tileBottomH = tileBottomRight.getHeight();
/*      */     
/*  425 */     int w = tileTopW + (width - 2) * 32 + tileBottomW;
/*  426 */     int h = tileTopH + (height - 2) * 32 + tileBottomH;
/*  427 */     BufferedImage stash = new BufferedImage(w, h, 1);
/*      */     
/*  429 */     Graphics2D g = stash.createGraphics();
/*      */     
/*  431 */     BufferedImage tile = null;
/*  432 */     int yPos = 0; int y;
/*  433 */     for (y = 0; y < height; y++) {
/*  434 */       int xPos = 0; int x;
/*  435 */       for (x = 0; x < width; x++) {
/*  436 */         if (y == 0) {
/*  437 */           if (x == 0) {
/*  438 */             tile = tileTopLeft;
/*      */           }
/*  440 */           else if (x == width - 1) {
/*  441 */             tile = tileTopRight;
/*      */           } else {
/*  443 */             tile = tileTop;
/*      */           }
/*      */         
/*      */         }
/*  447 */         else if (y == height - 1) {
/*  448 */           if (x == 0) {
/*  449 */             tile = tileBottomLeft;
/*      */           }
/*  451 */           else if (x == width - 1) {
/*  452 */             tile = tileBottomRight;
/*      */           } else {
/*  454 */             tile = tileBottom;
/*      */           }
/*      */         
/*      */         }
/*  458 */         else if (x == 0) {
/*  459 */           tile = tileCenterLeft;
/*      */         }
/*  461 */         else if (x == width - 1) {
/*  462 */           tile = tileCenterRight;
/*      */         } else {
/*  464 */           tile = tileCenter;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  470 */         g.drawImage(tile, xPos, yPos, null);
/*      */         
/*  472 */         xPos += tile.getWidth();
/*      */       } 
/*      */       
/*  475 */       yPos += tile.getHeight();
/*      */     } 
/*      */     
/*  478 */     imgCraftingStash = stash;
/*  479 */     craftingXOffset = tileTopW - 32;
/*  480 */     craftingYOffset = tileTopH - 32;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void buildCharStash(int width, int height) {
/*  485 */     int tempwidth = width;
/*  486 */     int tempheight = height;
/*      */     
/*  488 */     if (tempwidth < 0 || tempwidth > 100) tempwidth = 10; 
/*  489 */     if (tempheight < 0 || tempheight > 100) tempheight = 18;
/*      */     
/*  491 */     if (tempwidth == 10 && tempheight == 18) {
/*  492 */       imgCharStash = imgDefaultCharStash;
/*  493 */       imgCharBG = imgCharBackground;
/*  494 */       charXOffset = 2;
/*  495 */       charYOffset = 2;
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  500 */     int tileTopW = tileTopLeft.getWidth();
/*  501 */     int tileTopH = tileTopLeft.getHeight();
/*  502 */     int tileBottomW = tileBottomRight.getWidth();
/*  503 */     int tileBottomH = tileBottomRight.getHeight();
/*      */ 
/*      */     
/*  506 */     int w = tileTopW + (width - 2) * 32 + tileBottomW;
/*  507 */     int h = tileTopH + (height - 2) * 32 + tileBottomH;
/*  508 */     BufferedImage stash = new BufferedImage(w, h, 1);
/*      */     
/*  510 */     Graphics2D g = stash.createGraphics();
/*      */     
/*  512 */     BufferedImage tile = null;
/*  513 */     int yPos = 0; int y;
/*  514 */     for (y = 0; y < height; y++) {
/*  515 */       int xPos = 0; int x;
/*  516 */       for (x = 0; x < width; x++) {
/*  517 */         if (y == 0) {
/*  518 */           if (x == 0) {
/*  519 */             tile = tileTopLeft;
/*      */           }
/*  521 */           else if (x == width - 1) {
/*  522 */             tile = tileTopRight;
/*      */           } else {
/*  524 */             tile = tileTop;
/*      */           }
/*      */         
/*      */         }
/*  528 */         else if (y == height - 1) {
/*  529 */           if (x == 0) {
/*  530 */             tile = tileBottomLeft;
/*      */           }
/*  532 */           else if (x == width - 1) {
/*  533 */             tile = tileBottomRight;
/*      */           } else {
/*  535 */             tile = tileBottom;
/*      */           }
/*      */         
/*      */         }
/*  539 */         else if (x == 0) {
/*  540 */           tile = tileCenterLeft;
/*      */         }
/*  542 */         else if (x == width - 1) {
/*  543 */           tile = tileCenterRight;
/*      */         } else {
/*  545 */           tile = tileCenter;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  551 */         g.drawImage(tile, xPos, yPos, null);
/*      */         
/*  553 */         xPos += tile.getWidth();
/*      */       } 
/*      */       
/*  556 */       yPos += tile.getHeight();
/*      */     } 
/*      */     
/*  559 */     w = tileTopW + (width - 2) * 32 + tileBottomW + 234;
/*  560 */     h = tileTopH + (height - 2) * 32 + tileBottomH + 198;
/*  561 */     BufferedImage bg = new BufferedImage(w, h, 1);
/*      */     
/*  563 */     g = bg.createGraphics();
/*      */     
/*  565 */     tile = tileNoise; int i;
/*  566 */     for (i = 0; i < h; i += tile.getHeight()) {
/*  567 */       int x; for (x = 0; x < w; x += tile.getWidth()) {
/*  568 */         g.drawImage(tile, x, i, null);
/*      */       }
/*      */     } 
/*      */     
/*  572 */     imgCharStash = stash;
/*  573 */     imgCharBG = bg;
/*  574 */     charXOffset = tileTopW - 32;
/*  575 */     charYOffset = tileTopH - 32;
/*      */   }
/*      */   
/*      */   public static void loadImages() {
/*  579 */     String dir16 = "16";
/*  580 */     String dir24 = "24";
/*  581 */     String dir32 = "32";
/*      */ 
/*      */     
/*  584 */     if (GDStashFrame.iniConfig.sectUI.fontSize < 12) {
/*  585 */       dir24 = "16";
/*  586 */       dir32 = "24";
/*      */     } 
/*      */     
/*  589 */     if (GDStashFrame.iniConfig.sectUI.fontSize > 14) {
/*  590 */       dir16 = "24";
/*  591 */       dir24 = "32";
/*      */     } 
/*      */     
/*  594 */     if (GDStashFrame.iniConfig.sectUI.fontSize > 16) {
/*  595 */       dir32 = "48";
/*      */     }
/*      */     
/*  598 */     dir16 = "image" + GDConstants.FILE_SEPARATOR + "icons" + GDConstants.FILE_SEPARATOR + dir16 + GDConstants.FILE_SEPARATOR;
/*      */ 
/*      */     
/*  601 */     dir24 = "image" + GDConstants.FILE_SEPARATOR + "icons" + GDConstants.FILE_SEPARATOR + dir24 + GDConstants.FILE_SEPARATOR;
/*      */ 
/*      */     
/*  604 */     dir32 = "image" + GDConstants.FILE_SEPARATOR + "icons" + GDConstants.FILE_SEPARATOR + dir32 + GDConstants.FILE_SEPARATOR;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  609 */     BufferedImage image = null;
/*      */     
/*      */     try {
/*  612 */       image = ImageIO.read(new File(dir24 + "bool_or_sel.png"));
/*      */       
/*  614 */       iconBoolOr = new ImageIcon(image);
/*  615 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  618 */       image = ImageIO.read(new File(dir24 + "bool_or_unsel.png"));
/*      */       
/*  620 */       iconBoolOrUnselect = new ImageIcon(image);
/*  621 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  624 */       image = ImageIO.read(new File(dir24 + "bool_and_sel.png"));
/*      */       
/*  626 */       iconBoolAnd = new ImageIcon(image);
/*  627 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  630 */       image = ImageIO.read(new File(dir24 + "bool_and_unsel.png"));
/*      */       
/*  632 */       iconBoolAndUnselect = new ImageIcon(image);
/*  633 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  636 */       image = ImageIO.read(new File(dir16 + "msg_error.png"));
/*      */       
/*  638 */       iconMsgError16 = new ImageIcon(image);
/*  639 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  642 */       image = ImageIO.read(new File(dir16 + "msg_info.png"));
/*      */       
/*  644 */       iconMsgInfo16 = new ImageIcon(image);
/*  645 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  648 */       image = ImageIO.read(new File(dir16 + "msg_success.png"));
/*      */       
/*  650 */       iconMsgSuccess16 = new ImageIcon(image);
/*  651 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  654 */       image = ImageIO.read(new File(dir16 + "msg_warn.png"));
/*      */       
/*  656 */       iconMsgWarning16 = new ImageIcon(image);
/*  657 */     } catch (IOException iOException) {}
/*      */ 
/*      */     
/*      */     try {
/*  661 */       image = ImageIO.read(new File(dir16 + "tab_collect.png"));
/*      */       
/*  663 */       iconTabCollection16 = new ImageIcon(image);
/*  664 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  667 */       image = ImageIO.read(new File(dir16 + "tab_collect_set.png"));
/*      */       
/*  669 */       iconTabCollectionSet16 = new ImageIcon(image);
/*  670 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  673 */       image = ImageIO.read(new File(dir16 + "tab_config.png"));
/*      */       
/*  675 */       iconTabConfigure16 = new ImageIcon(image);
/*  676 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  679 */       image = ImageIO.read(new File(dir16 + "tab_file_import.png"));
/*      */       
/*  681 */       iconTabFileImp16 = new ImageIcon(image);
/*  682 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  685 */       image = ImageIO.read(new File(dir16 + "tab_stash_craft.png"));
/*      */       
/*  687 */       iconTabStashCraft16 = new ImageIcon(image);
/*  688 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  691 */       image = ImageIO.read(new File(dir16 + "tab_stash_trans.png"));
/*      */       
/*  693 */       iconTabStashTransfer16 = new ImageIcon(image);
/*  694 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  697 */       image = ImageIO.read(new File(dir16 + "tab_char_edit.png"));
/*      */       
/*  699 */       iconTabCharEdit16 = new ImageIcon(image);
/*  700 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  703 */       image = ImageIO.read(new File(dir16 + "tab_char_data.png"));
/*      */       
/*  705 */       iconTabCharData16 = new ImageIcon(image);
/*  706 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  709 */       image = ImageIO.read(new File(dir16 + "tab_char_portal.png"));
/*      */       
/*  711 */       iconTabCharPortal16 = new ImageIcon(image);
/*  712 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  715 */       image = ImageIO.read(new File(dir16 + "tab_char_trans.png"));
/*      */       
/*  717 */       iconTabCharTransfer16 = new ImageIcon(image);
/*  718 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  721 */       image = ImageIO.read(new File(dir16 + "tab_mastery_info.png"));
/*      */       
/*  723 */       iconTabMasteryInfo16 = new ImageIcon(image);
/*  724 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  727 */       image = ImageIO.read(new File(dir16 + "btn_cancel.png"));
/*      */       
/*  729 */       iconCharEditMasteryReset16 = new ImageIcon(image);
/*  730 */     } catch (IOException iOException) {}
/*      */ 
/*      */     
/*      */     try {
/*  734 */       image = ImageIO.read(new File(dir16 + "btn_plus.png"));
/*      */       
/*  736 */       iconPlus16 = new ImageIcon(image);
/*  737 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  740 */       image = ImageIO.read(new File(dir16 + "btn_minus.png"));
/*      */       
/*  742 */       iconMinus16 = new ImageIcon(image);
/*  743 */     } catch (IOException iOException) {}
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  748 */       image = ImageIO.read(new File(dir24 + "btn_cancel.png"));
/*      */       
/*  750 */       iconBtnCancel24 = new ImageIcon(image);
/*  751 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  754 */       image = ImageIO.read(new File(dir24 + "btn_dir.png"));
/*      */       
/*  756 */       iconBtnDir24 = new ImageIcon(image);
/*  757 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  760 */       image = ImageIO.read(new File(dir24 + "btn_filter.png"));
/*      */       
/*  762 */       iconBtnFilter24 = new ImageIcon(image);
/*  763 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  766 */       image = ImageIO.read(new File(dir24 + "btn_ok.png"));
/*      */       
/*  768 */       iconBtnOk24 = new ImageIcon(image);
/*  769 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  772 */       image = ImageIO.read(new File(dir24 + "btn_question.png"));
/*      */       
/*  774 */       iconBtnQuestion24 = new ImageIcon(image);
/*  775 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  778 */       image = ImageIO.read(new File(dir24 + "btn_reload.png"));
/*      */       
/*  780 */       iconBtnReload24 = new ImageIcon(image);
/*  781 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  784 */       image = ImageIO.read(new File(dir24 + "btn_save.png"));
/*      */       
/*  786 */       iconBtnSave24 = new ImageIcon(image);
/*  787 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  790 */       image = ImageIO.read(new File(dir24 + "btn_search.png"));
/*      */       
/*  792 */       iconBtnSearch24 = new ImageIcon(image);
/*  793 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  796 */       image = ImageIO.read(new File(dir24 + "btn_trash.png"));
/*      */       
/*  798 */       iconBtnTrash24 = new ImageIcon(image);
/*  799 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  802 */       image = ImageIO.read(new File(dir24 + "window_collapse_oxygen.png"));
/*      */       
/*  804 */       iconWindowCollapse24 = new ImageIcon(image);
/*  805 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  808 */       image = ImageIO.read(new File(dir24 + "window_expand_oxygen.png"));
/*      */       
/*  810 */       iconWindowExpand24 = new ImageIcon(image);
/*  811 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  814 */       image = ImageIO.read(new File(dir24 + "db_delete.png"));
/*      */       
/*  816 */       iconDBDelete24 = new ImageIcon(image);
/*  817 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  820 */       image = ImageIO.read(new File(dir24 + "item_copy.png"));
/*      */       
/*  822 */       iconItemCopy24 = new ImageIcon(image);
/*  823 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  826 */       image = ImageIO.read(new File(dir24 + "item_delete.png"));
/*      */       
/*  828 */       iconItemDelete24 = new ImageIcon(image);
/*  829 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  832 */       image = ImageIO.read(new File(dir24 + "item_move.png"));
/*      */       
/*  834 */       iconItemMove24 = new ImageIcon(image);
/*  835 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  838 */       image = ImageIO.read(new File(dir24 + "page_copy.png"));
/*      */       
/*  840 */       iconPageCopy24 = new ImageIcon(image);
/*  841 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  844 */       image = ImageIO.read(new File(dir24 + "page_delete.png"));
/*      */       
/*  846 */       iconPageDelete24 = new ImageIcon(image);
/*  847 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  850 */       image = ImageIO.read(new File(dir24 + "page_move.png"));
/*      */       
/*  852 */       iconPageMove24 = new ImageIcon(image);
/*  853 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  856 */       image = ImageIO.read(new File(dir24 + "coll_all.png"));
/*      */       
/*  858 */       iconCollectAll24 = new ImageIcon(image);
/*  859 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  862 */       image = ImageIO.read(new File(dir24 + "coll_found.png"));
/*      */       
/*  864 */       iconCollectFound24 = new ImageIcon(image);
/*  865 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  868 */       image = ImageIO.read(new File(dir24 + "coll_miss.png"));
/*      */       
/*  870 */       iconCollectMiss24 = new ImageIcon(image);
/*  871 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  874 */       image = ImageIO.read(new File(dir24 + "cfg_arz_exp.png"));
/*      */       
/*  876 */       iconConfigARZExport24 = new ImageIcon(image);
/*  877 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  880 */       image = ImageIO.read(new File(dir24 + "cfg_devotion.png"));
/*      */       
/*  882 */       iconConfigDevotionList24 = new ImageIcon(image);
/*  883 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  886 */       image = ImageIO.read(new File(dir24 + "cfg_texconv.png"));
/*      */       
/*  888 */       iconConfigTextureConvert24 = new ImageIcon(image);
/*  889 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  892 */       image = ImageIO.read(new File(dir24 + "cfg_db_imp.png"));
/*      */       
/*  894 */       iconConfigDBImport24 = new ImageIcon(image);
/*  895 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  898 */       image = ImageIO.read(new File(dir24 + "cfg_save.png"));
/*      */       
/*  900 */       iconConfigSettingSave24 = new ImageIcon(image);
/*  901 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  904 */       image = ImageIO.read(new File(dir24 + "cfg_transmute_add.png"));
/*      */       
/*  906 */       iconConfigTransmuteAdd24 = new ImageIcon(image);
/*  907 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  910 */       image = ImageIO.read(new File(dir24 + "cfg_transmute_full.png"));
/*      */       
/*  912 */       iconConfigTransmuteFull24 = new ImageIcon(image);
/*  913 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  916 */       image = ImageIO.read(new File(dir24 + "mass_all.png"));
/*      */       
/*  918 */       iconMassAll24 = new ImageIcon(image);
/*  919 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  922 */       image = ImageIO.read(new File(dir24 + "mass_none.png"));
/*      */       
/*  924 */       iconMassNone24 = new ImageIcon(image);
/*  925 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  928 */       image = ImageIO.read(new File(dir24 + "mass_valid.png"));
/*      */       
/*  930 */       iconMassValid24 = new ImageIcon(image);
/*  931 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  934 */       image = ImageIO.read(new File(dir24 + "mass_gds_exp.png"));
/*      */       
/*  936 */       iconMassGDSExport24 = new ImageIcon(image);
/*  937 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  940 */       image = ImageIO.read(new File(dir24 + "mass_gds_load.png"));
/*      */       
/*  942 */       iconMassGDSLoad24 = new ImageIcon(image);
/*  943 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  946 */       image = ImageIO.read(new File(dir24 + "mass_ias_load.png"));
/*      */       
/*  948 */       iconMassIASLoad24 = new ImageIcon(image);
/*  949 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  952 */       image = ImageIO.read(new File(dir24 + "mass_item_imp.png"));
/*      */       
/*  954 */       iconMassItemImport24 = new ImageIcon(image);
/*  955 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  958 */       image = ImageIO.read(new File(dir24 + "mass_item_load.png"));
/*      */       
/*  960 */       iconMassItemLoad24 = new ImageIcon(image);
/*  961 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  964 */       image = ImageIO.read(new File(dir24 + "mass_item_export.png"));
/*      */       
/*  966 */       iconMassUniqueClipboard24 = new ImageIcon(image);
/*  967 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  970 */       image = ImageIO.read(new File(dir24 + "mass_legend_export.png"));
/*      */       
/*  972 */       iconMassLegendClipboard24 = new ImageIcon(image);
/*  973 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  976 */       image = ImageIO.read(new File(dir24 + "mass_formula.png"));
/*      */       
/*  978 */       iconMassBlueprints24 = new ImageIcon(image);
/*  979 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  982 */       image = ImageIO.read(new File(dir24 + "trans_exp.png"));
/*      */       
/*  984 */       iconTransGDSExport24 = new ImageIcon(image);
/*  985 */     } catch (IOException iOException) {}
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  990 */       image = ImageIO.read(new File(dir32 + "msg_error.png"));
/*      */       
/*  992 */       iconMsgError32 = new ImageIcon(image);
/*  993 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/*  996 */       image = ImageIO.read(new File(dir32 + "msg_info.png"));
/*      */       
/*  998 */       iconMsgInfo32 = new ImageIcon(image);
/*  999 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1002 */       image = ImageIO.read(new File(dir32 + "msg_success.png"));
/*      */       
/* 1004 */       iconMsgSuccess32 = new ImageIcon(image);
/* 1005 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1008 */       image = ImageIO.read(new File(dir32 + "msg_warn.png"));
/*      */       
/* 1010 */       iconMsgWarning32 = new ImageIcon(image);
/* 1011 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1014 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "skills_buttonborder01.png"));
/*      */       
/* 1016 */       imgBorderSquare = image;
/* 1017 */     } catch (IOException iOException) {}
/*      */     
/*      */     try {
/* 1020 */       image = ImageIO.read(new File("image" + GDConstants.FILE_SEPARATOR + "skills_buttonborderround01.png"));
/*      */       
/* 1022 */       imgBorderRound = image;
/* 1023 */     } catch (IOException iOException) {}
/*      */   }
/*      */   
/*      */   public static BufferedImage getSharedStashGrid() {
/* 1027 */     return imgSharedStash;
/*      */   }
/*      */   
/*      */   public static int getSharedStashXOffset() {
/* 1031 */     return sharedXOffset;
/*      */   }
/*      */   
/*      */   public static int getSharedStashYOffset() {
/* 1035 */     return sharedYOffset;
/*      */   }
/*      */   
/*      */   public static BufferedImage getCraftingStashGrid() {
/* 1039 */     return imgCraftingStash;
/*      */   }
/*      */   
/*      */   public static int getCraftingStashXOffset() {
/* 1043 */     return craftingXOffset;
/*      */   }
/*      */   
/*      */   public static int getCraftingStashYOffset() {
/* 1047 */     return craftingYOffset;
/*      */   }
/*      */   
/*      */   public static BufferedImage getCharStashGrid() {
/* 1051 */     return imgCharStash;
/*      */   }
/*      */   
/*      */   public static BufferedImage getCharStashBG() {
/* 1055 */     return imgCharBG;
/*      */   }
/*      */   
/*      */   public static BufferedImage copyImage(BufferedImage source) {
/* 1059 */     BufferedImage img = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
/* 1060 */     Graphics g = img.getGraphics();
/*      */     
/* 1062 */     g.drawImage(source, 0, 0, null);
/* 1063 */     g.dispose();
/*      */     
/* 1065 */     return img;
/*      */   }
/*      */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\util\GDImagePool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */