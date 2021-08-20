/*     */ package org.gdstash.ui.character;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.JPanel;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.character.GDCharSkill;
/*     */ import org.gdstash.db.DBBitmap;
/*     */ import org.gdstash.db.DBClassTable;
/*     */ import org.gdstash.db.DBClassTableButton;
/*     */ import org.gdstash.db.DBEngineGame;
/*     */ import org.gdstash.db.DBEngineMasteryTier;
/*     */ import org.gdstash.db.DBSkill;
/*     */ import org.gdstash.db.DBSkillButton;
/*     */ import org.gdstash.db.DBSkillConnector;
/*     */ import org.gdstash.db.DBSkillDependency;
/*     */ import org.gdstash.db.DBSkillTree;
/*     */ import org.gdstash.db.DBSkillTreeAlloc;
/*     */ import org.gdstash.file.DDSLoader;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ import org.gdstash.util.GDImagePool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDCharMasteryImagePane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private static final String STR_BACKGROUND = "records/ui/skills/class01/classpanelbackgroundimage.dbr";
/*     */   private static final String STR_SQUARE_GOLD = "records/ui/skills/classcommon/skillbuttons_goldbitmap.dbr";
/*     */   private static final String STR_ROUND_GOLD = "records/ui/skills/classcommon/skillbuttons_goldroundbitmap.dbr";
/*     */   
/*     */   public static class Skill
/*     */   {
/*     */     public DBSkillButton button;
/*     */     public DBSkill skill;
/*     */     public Skill base;
/*     */     public int index;
/*     */     public int skillLevel;
/*     */     public boolean mastery;
/*     */   }
/*  48 */   private static int[] LEVEL_TIER = new int[] { 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9 };
/*  49 */   private static int[] LEVEL_BAR = new int[] { 0, 80, 100, 120, 140, 160, 176, 192, 208, 224, 240, 256, 272, 288, 304, 320, 336, 352, 368, 384, 400, 416, 432, 448, 464, 480, 491, 503, 514, 526, 537, 549, 560, 570, 580, 590, 600, 610, 620, 630, 640, 648, 656, 664, 672, 680, 688, 696, 704, 712, 720 };
/*     */   
/*     */   private static BufferedImage IMG_BACKGROUND;
/*     */   
/*     */   private static BufferedImage BORDER_SQUARE_GOLD;
/*     */   private static BufferedImage BORDER_ROUND_GOLD;
/*     */   private GDChar gdc;
/*     */   private DBClassTable classTable;
/*     */   private DBSkillTree skillTree;
/*     */   private Skill mastery;
/*     */   private List<Skill> skills;
/*     */   private JPanel pnlMastery;
/*     */   private BufferedImage bgImage;
/*     */   private GDUIMasterySupport masterySupport;
/*     */   private boolean selMastery;
/*     */   private int pointsSpent;
/*     */   
/*     */   public static void initStats() {
/*  67 */     DBBitmap bmp = null;
/*     */     
/*  69 */     bmp = DBBitmap.get("records/ui/skills/class01/classpanelbackgroundimage.dbr");
/*  70 */     if (bmp != null) IMG_BACKGROUND = bmp.getImage();
/*     */     
/*  72 */     bmp = DBBitmap.get("records/ui/skills/classcommon/skillbuttons_goldbitmap.dbr");
/*  73 */     if (bmp != null) BORDER_SQUARE_GOLD = bmp.getImage();
/*     */     
/*  75 */     bmp = DBBitmap.get("records/ui/skills/classcommon/skillbuttons_goldroundbitmap.dbr");
/*  76 */     if (bmp != null) BORDER_ROUND_GOLD = bmp.getImage();
/*     */     
/*  78 */     DBEngineGame game = DBEngineGame.get();
/*     */     
/*  80 */     if (game == null)
/*     */       return; 
/*  82 */     int levels = game.getMaxMasteryLevel();
/*  83 */     int numTiers = game.getNumSkillTiers();
/*  84 */     DBEngineMasteryTier[] tiers = game.getSkillTierArray();
/*     */     
/*  86 */     if (tiers == null)
/*     */       return; 
/*  88 */     LEVEL_TIER = new int[levels + 1];
/*  89 */     LEVEL_BAR = new int[levels + 1];
/*     */     
/*  91 */     LEVEL_TIER[0] = 0; int i;
/*  92 */     for (i = 0; i < tiers.length - 1; i++) {
/*  93 */       int k; for (k = tiers[i].getMasteryLevel(); k < tiers[i + 1].getMasteryLevel(); k++) {
/*  94 */         LEVEL_TIER[k] = tiers[i].getMasteryTier();
/*     */       }
/*  96 */       for (k = tiers[i + 1].getMasteryLevel(); k < LEVEL_TIER.length; k++) {
/*  97 */         LEVEL_TIER[k] = tiers[i + 1].getMasteryTier();
/*     */       }
/*     */     } 
/*     */     
/* 101 */     LEVEL_BAR[0] = 0;
/* 102 */     int lastLevel = 0;
/* 103 */     float total = 0.0F; int j;
/* 104 */     for (j = 0; j < tiers.length; j++) {
/* 105 */       float step = 720.0F / numTiers / (tiers[j].getMasteryLevel() - lastLevel);
/*     */       int k;
/* 107 */       for (k = lastLevel + 1; k <= tiers[j].getMasteryLevel(); k++) {
/* 108 */         total += step;
/*     */         
/* 110 */         LEVEL_BAR[k] = Math.round(total);
/*     */       } 
/*     */       
/* 113 */       lastLevel = tiers[j].getMasteryLevel();
/*     */     } 
/*     */   }
/*     */   
/*     */   public GDCharMasteryImagePane(GDUIMasterySupport masterySupport, boolean selMastery) {
/* 118 */     this.masterySupport = masterySupport;
/* 119 */     this.selMastery = selMastery;
/* 120 */     this.skills = new LinkedList<>();
/*     */     
/* 122 */     this.bgImage = IMG_BACKGROUND;
/*     */   }
/*     */   
/*     */   public void adjustUI() {
/* 126 */     createImage();
/* 127 */     repaint();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateConfig() {}
/*     */ 
/*     */   
/*     */   private Skill getSkill(int x, int y) {
/* 135 */     int cX = getX();
/* 136 */     int cY = getY();
/*     */     
/* 138 */     Skill sk = null;
/*     */     
/* 140 */     for (Skill mps : this.skills) {
/* 141 */       int imgX = mps.button.getPosX();
/* 142 */       int imgY = mps.button.getPosY();
/* 143 */       int imgH = mps.skill.getImageUp().getHeight() + 2 * mps.button.getOffsetX();
/* 144 */       int imgW = mps.skill.getImageUp().getWidth() + 2 * mps.button.getOffsetY();
/*     */       
/* 146 */       if (imgX <= x && imgX + imgW >= x && imgY <= y && imgY + imgH >= y) {
/*     */         
/* 148 */         sk = mps;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 154 */     return sk;
/*     */   }
/*     */   
/*     */   public String getSkillDescription(int x, int y) {
/* 158 */     int xPos = x;
/* 159 */     int yPos = y;
/* 160 */     if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 161 */       xPos = xPos * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/* 162 */       yPos = yPos * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/*     */     } 
/*     */     
/* 165 */     Skill mps = getSkill(xPos, yPos);
/*     */     
/* 167 */     String s = null;
/*     */     
/* 169 */     if (mps != null) {
/* 170 */       s = mps.skill.getSkillNextLevelDescription(mps.skillLevel, this.masterySupport.getCharLevel());
/*     */     }
/*     */     
/* 173 */     return s;
/*     */   }
/*     */   
/*     */   public String decSkill(int x, int y) {
/* 177 */     int xPos = x;
/* 178 */     int yPos = y;
/* 179 */     if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 180 */       xPos = xPos * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/* 181 */       yPos = yPos * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/*     */     } 
/*     */     
/* 184 */     Skill mps = getSkill(xPos, yPos);
/*     */     
/* 186 */     String s = null;
/*     */     
/* 188 */     if (mps != null) {
/* 189 */       boolean dec = true;
/*     */       
/* 191 */       if (mps.mastery) {
/* 192 */         int maxTier = 0;
/*     */         
/* 194 */         for (Skill sk : this.skills) {
/* 195 */           if (sk.skillLevel > 0) {
/* 196 */             int tier = sk.skill.getSkillTier();
/* 197 */             if (maxTier < tier) maxTier = tier;
/*     */           
/*     */           } 
/*     */         } 
/* 201 */         if (mps.skillLevel <= 0) {
/* 202 */           dec = false;
/*     */         } else {
/*     */           
/* 205 */           int idx = mps.skillLevel - 1;
/*     */           
/* 207 */           if (idx > LEVEL_TIER.length - 1) idx = LEVEL_TIER.length - 1;
/*     */           
/* 209 */           if (maxTier > LEVEL_TIER[idx]) {
/* 210 */             dec = false;
/*     */           }
/*     */         } 
/*     */       } else {
/* 214 */         if (mps.skillLevel <= 0) {
/* 215 */           dec = false;
/*     */         }
/*     */ 
/*     */         
/* 219 */         if (mps.skill.isBaseSkill() && 
/* 220 */           mps.skillLevel == 1) {
/* 221 */           boolean check = false;
/*     */           
/* 223 */           for (Skill sk : this.skills) {
/* 224 */             if (sk.skill.isBaseSkill()) {
/* 225 */               if (check)
/*     */                 break; 
/* 227 */               if (mps.button.getButtonID().equals(sk.button.getButtonID()))
/* 228 */                 check = true; 
/*     */               continue;
/*     */             } 
/* 231 */             if (check && 
/* 232 */               sk.skillLevel > 0) {
/* 233 */               dec = false;
/*     */ 
/*     */ 
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 244 */       if (dec) {
/* 245 */         mps.skillLevel--;
/* 246 */         this.pointsSpent--;
/*     */         
/* 248 */         this.masterySupport.setSkillPoints(this.masterySupport.getSkillPoints() + 1);
/*     */         
/* 250 */         createImage();
/* 251 */         repaint();
/*     */       } 
/*     */       
/* 254 */       s = mps.skill.getSkillNextLevelDescription(mps.skillLevel, this.masterySupport.getCharLevel());
/*     */     } 
/*     */     
/* 257 */     return s;
/*     */   }
/*     */   
/*     */   public String incSkill(int x, int y) {
/* 261 */     int xPos = x;
/* 262 */     int yPos = y;
/* 263 */     if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 264 */       xPos = xPos * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/* 265 */       yPos = yPos * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/*     */     } 
/*     */     
/* 268 */     Skill mps = getSkill(xPos, yPos);
/*     */     
/* 270 */     String s = null;
/*     */     
/* 272 */     if (mps != null) {
/* 273 */       boolean inc = true;
/*     */       
/* 275 */       if (mps.mastery) {
/* 276 */         if (mps.skillLevel >= mps.skill.getMaxLevel()) {
/* 277 */           inc = false;
/*     */         }
/*     */       } else {
/* 280 */         if (mps.skillLevel >= mps.skill.getMaxLevel()) {
/* 281 */           inc = false;
/*     */         }
/*     */ 
/*     */         
/* 285 */         int idx = this.mastery.skillLevel;
/*     */         
/* 287 */         if (idx > LEVEL_TIER.length - 1) idx = LEVEL_TIER.length - 1;
/*     */         
/* 289 */         if (mps.skill.getSkillTier() > LEVEL_TIER[idx]) {
/* 290 */           inc = false;
/*     */         }
/*     */         
/* 293 */         if (mps.skill.getSkillDependencyList() != null && 
/* 294 */           !mps.skill.getSkillDependencyList().isEmpty()) {
/* 295 */           int count = 0;
/*     */           
/* 297 */           for (DBSkillDependency dep : mps.skill.getSkillDependencyList()) {
/* 298 */             if (this.masterySupport.getPointsForSkill(dep.getDependentSkillID()) > 0) {
/* 299 */               count++;
/*     */             }
/*     */           } 
/*     */           
/* 303 */           if (mps.skill.isDependencyAll())
/* 304 */           { if (count < mps.skill.getSkillDependencyList().size()) inc = false;
/*     */              }
/* 306 */           else if (count == 0) { inc = false; }
/*     */         
/*     */         } 
/*     */ 
/*     */         
/* 311 */         if (!mps.skill.isBaseSkill() && 
/* 312 */           mps.base.skillLevel <= 0) inc = false;
/*     */       
/*     */       } 
/*     */       
/* 316 */       int points = this.masterySupport.getSkillPoints();
/* 317 */       if (points <= 0) inc = false;
/*     */       
/* 319 */       if (inc) {
/* 320 */         mps.skillLevel++;
/* 321 */         this.pointsSpent++;
/*     */         
/* 323 */         this.masterySupport.setSkillPoints(points - 1);
/*     */         
/* 325 */         createImage();
/* 326 */         repaint();
/*     */       } 
/*     */       
/* 329 */       s = mps.skill.getSkillNextLevelDescription(mps.skillLevel, this.masterySupport.getCharLevel());
/*     */     } 
/*     */     
/* 332 */     return s;
/*     */   }
/*     */   
/*     */   public int getPointsSpent() {
/* 336 */     return this.pointsSpent;
/*     */   }
/*     */   
/*     */   public int getPointsForSkill(String skillID) {
/* 340 */     int points = 0;
/*     */     
/* 342 */     for (Skill sk : this.skills) {
/* 343 */       if (sk.skill.getSkillID().equals(skillID)) {
/* 344 */         points = sk.skillLevel;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 350 */     return points;
/*     */   }
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 354 */     this.gdc = gdc;
/*     */   }
/*     */   
/*     */   public void updateChar(GDChar gdc) {
/* 358 */     if (gdc == null)
/* 359 */       return;  if (this.mastery == null)
/*     */       return; 
/* 361 */     gdc.setMasterySkills(this.mastery.skill.getSkillID(), this.skills);
/*     */   }
/*     */   
/*     */   private void determineMasterySkills(String masteryID) {
/* 365 */     this.classTable = null;
/* 366 */     this.skillTree = null;
/* 367 */     this.mastery = null;
/* 368 */     this.skills.clear();
/* 369 */     this.pointsSpent = 0;
/*     */     
/* 371 */     if (masteryID == null)
/*     */       return; 
/* 373 */     if (!this.selMastery) {
/* 374 */       boolean found = false;
/*     */       
/* 376 */       if (this.gdc != null) {
/* 377 */         GDChar.SkillInfo[] infos = this.gdc.getMasteryInfo();
/*     */         
/* 379 */         if (infos != null) {
/* 380 */           int i; for (i = 0; i < infos.length; i++) {
/* 381 */             if ((infos[i]).id.equals(masteryID)) {
/* 382 */               found = true;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 390 */       if (!found)
/*     */         return; 
/*     */     } 
/* 393 */     DBSkillButton sb = DBSkillButton.getBySkillID(masteryID);
/*     */     
/* 395 */     if (sb == null)
/*     */       return; 
/* 397 */     this.classTable = DBClassTable.getByMasteryID(sb.getButtonID());
/*     */     
/* 399 */     if (this.classTable == null)
/*     */       return; 
/* 401 */     List<DBClassTableButton> buttons = this.classTable.getButtonList();
/*     */     
/* 403 */     if (masteryID != null) {
/* 404 */       String treeID = DBSkillTreeAlloc.getTreeIDBySkillID(masteryID);
/*     */       
/* 406 */       if (treeID != null) {
/* 407 */         this.skillTree = DBSkillTree.get(treeID);
/*     */       }
/*     */     } 
/*     */     
/* 411 */     int index = 1;
/* 412 */     for (String skillID : this.skillTree.getSkillIDList()) {
/* 413 */       for (DBClassTableButton button : buttons) {
/* 414 */         if (button.getSkill() == null)
/*     */           continue; 
/* 416 */         if (skillID.equals(button.getSkill().getSkillID())) {
/* 417 */           Skill temp = new Skill();
/* 418 */           temp.button = button.getButton();
/* 419 */           temp.skill = button.getSkill();
/* 420 */           temp.base = null;
/* 421 */           temp.index = index;
/* 422 */           temp.skillLevel = 0;
/* 423 */           temp.mastery = false;
/*     */           
/* 425 */           if (temp.skill.isMastery()) {
/* 426 */             temp.mastery = true;
/*     */             
/* 428 */             this.mastery = temp;
/*     */           } 
/*     */           
/* 431 */           if (!this.selMastery) {
/* 432 */             List<GDCharSkill> charSkills = this.gdc.getSkillsByMastery(masteryID);
/* 433 */             for (GDCharSkill cs : charSkills) {
/* 434 */               if (cs.getID().equals(temp.skill.getSkillID())) {
/* 435 */                 temp.skillLevel = cs.getLevel();
/*     */                 
/* 437 */                 this.pointsSpent += temp.skillLevel;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 444 */           this.skills.add(temp);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 450 */       index++;
/*     */     } 
/*     */     
/* 453 */     Skill base = null;
/* 454 */     for (Skill mps : this.skills) {
/* 455 */       if (mps.skill.isBaseSkill()) {
/* 456 */         base = mps; continue;
/*     */       } 
/* 458 */       mps.base = base;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void createImage() {
/* 464 */     BufferedImage image = null;
/*     */     
/* 466 */     if (this.classTable != null) {
/* 467 */       DBBitmap bar = this.classTable.getMasteryBarDB();
/* 468 */       DBBitmap img = this.classTable.getMasteryBitmapDB();
/* 469 */       DBBitmap bg = this.classTable.getSkillPaneDB();
/* 470 */       BufferedImage bi = null;
/*     */       
/* 472 */       if (bg == null) {
/* 473 */         bi = IMG_BACKGROUND;
/*     */       } else {
/* 475 */         bi = bg.getImage();
/*     */       } 
/*     */       
/* 478 */       Graphics g = null;
/*     */       
/* 480 */       image = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
/*     */       
/* 482 */       g = image.createGraphics();
/*     */       
/* 484 */       g.drawImage(bi, 0, 0, null);
/* 485 */       if (img != null) {
/* 486 */         g.drawImage(img.getImage(), img.getPosX(), img.getPosY(), null);
/*     */       }
/*     */       
/* 489 */       if (this.mastery != null) {
/* 490 */         g.drawImage(bar.getImage(), bar.getPosX(), bar.getPosY(), LEVEL_BAR[this.mastery.skillLevel], bar.getImage().getHeight(), null);
/*     */       }
/*     */       
/* 493 */       List<DBClassTableButton> btns = this.classTable.getButtonList();
/*     */ 
/*     */       
/* 496 */       for (Skill mps : this.skills) {
/* 497 */         List<DBSkillConnector> connections = mps.skill.getSkillConnectionList();
/*     */         
/* 499 */         int imgX = mps.button.getPosX() + mps.button.getOffsetX();
/* 500 */         int imgY = mps.button.getPosY() + mps.button.getOffsetY();
/*     */         
/* 502 */         if (connections != null && 
/* 503 */           !connections.isEmpty()) {
/* 504 */           int x = imgX + 32;
/* 505 */           int y = imgY - 24;
/* 506 */           for (DBSkillConnector connection : connections) {
/* 507 */             if (connection == null) {
/*     */               continue;
/*     */             }
/*     */             
/* 511 */             BufferedImage coff = connection.getConnectionOff();
/* 512 */             BufferedImage con = connection.getConnectionOn();
/*     */             
/* 514 */             if (con != null && coff != null) {
/* 515 */               if (mps.skillLevel == 0) {
/* 516 */                 g.drawImage(connection.getConnectionOff(), x, y, null);
/*     */               } else {
/* 518 */                 g.drawImage(connection.getConnectionOn(), x, y, null);
/*     */               } 
/*     */               
/* 521 */               BufferedImage ci = connection.getConnectionOn();
/* 522 */               if (ci == null) ci = connection.getConnectionOff();
/*     */               
/* 524 */               x += ci.getWidth();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 530 */       for (Skill mps : this.skills) {
/* 531 */         int imgX = mps.button.getPosX() + mps.button.getOffsetX();
/* 532 */         int imgY = mps.button.getPosY() + mps.button.getOffsetY();
/* 533 */         int brdX = mps.button.getPosX();
/* 534 */         int brdY = mps.button.getPosY();
/*     */         
/* 536 */         if (mps.mastery) {
/* 537 */           g.drawImage(mps.skill.getImageUp(), imgX, imgY, null);
/*     */           continue;
/*     */         } 
/* 540 */         int idx = this.mastery.skillLevel;
/*     */         
/* 542 */         if (idx > LEVEL_TIER.length - 1) idx = LEVEL_TIER.length - 1;
/*     */         
/* 544 */         if (mps.skill.getSkillTier() <= LEVEL_TIER[idx]) {
/* 545 */           if (mps.skill.isBaseSkill()) {
/* 546 */             if (mps.button.isCircularButton()) {
/* 547 */               g.drawImage(BORDER_ROUND_GOLD, brdX, brdY, null);
/*     */             } else {
/* 549 */               g.drawImage(BORDER_SQUARE_GOLD, brdX, brdY, null);
/*     */             }
/*     */           
/* 552 */           } else if (mps.base.skillLevel > 0) {
/* 553 */             if (mps.button.isCircularButton()) {
/* 554 */               g.drawImage(BORDER_ROUND_GOLD, brdX, brdY, null);
/*     */             } else {
/* 556 */               g.drawImage(BORDER_SQUARE_GOLD, brdX, brdY, null);
/*     */             }
/*     */           
/* 559 */           } else if (mps.button.isCircularButton()) {
/* 560 */             g.drawImage(GDImagePool.imgBorderRound, brdX, brdY, null);
/*     */           } else {
/* 562 */             g.drawImage(GDImagePool.imgBorderSquare, brdX, brdY, null);
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 567 */         else if (mps.button.isCircularButton()) {
/* 568 */           g.drawImage(GDImagePool.imgBorderRound, brdX, brdY, null);
/*     */         } else {
/* 570 */           g.drawImage(GDImagePool.imgBorderSquare, brdX, brdY, null);
/*     */         } 
/*     */ 
/*     */         
/* 574 */         if (mps.skillLevel == 0) {
/*     */           
/* 576 */           BufferedImage si = mps.skill.getImageDown();
/* 577 */           if (si == null) si = mps.skill.getImageUp();
/*     */           
/* 579 */           g.drawImage(si, imgX, imgY, null); continue;
/*     */         } 
/* 581 */         g.drawImage(mps.skill.getImageUp(), imgX, imgY, null);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 587 */     if (image == null) {
/* 588 */       this.bgImage = IMG_BACKGROUND;
/*     */     } else {
/* 590 */       this.bgImage = image;
/*     */     } 
/*     */     
/* 593 */     if (this.bgImage != null && 
/* 594 */       GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 595 */       int w = this.bgImage.getWidth() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 596 */       int h = this.bgImage.getHeight() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*     */       
/* 598 */       this.bgImage = DDSLoader.getScaledImage(this.bgImage, w, h);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMastery(String masteryID) {
/* 604 */     determineMasterySkills(masteryID);
/* 605 */     createImage();
/* 606 */     repaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintComponent(Graphics g) {
/* 615 */     super.paintComponent(g);
/*     */     
/* 617 */     if (this.bgImage == null)
/*     */       return; 
/* 619 */     g.drawImage(drawGraphics(), 0, 0, null);
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize() {
/* 623 */     if (this.bgImage == null) return super.getPreferredSize();
/*     */     
/* 625 */     int w = this.bgImage.getWidth();
/* 626 */     int h = this.bgImage.getHeight();
/*     */     
/* 628 */     return new Dimension(w, h);
/*     */   }
/*     */   
/*     */   public Dimension getMaximumSize() {
/* 632 */     return getPreferredSize();
/*     */   }
/*     */   
/*     */   public Dimension getMinimumSize() {
/* 636 */     return getPreferredSize();
/*     */   }
/*     */   
/*     */   public int getPreferredWidth() {
/* 640 */     return (int)getPreferredSize().getWidth();
/*     */   }
/*     */   
/*     */   public int getPreferredHeight() {
/* 644 */     return (int)getPreferredSize().getHeight();
/*     */   }
/*     */   
/*     */   private BufferedImage drawGraphics() {
/* 648 */     if (this.bgImage == null) return null;
/*     */ 
/*     */     
/* 651 */     Graphics g = null;
/* 652 */     BufferedImage image = null;
/*     */     
/* 654 */     image = new BufferedImage(this.bgImage.getWidth(), this.bgImage.getHeight(), this.bgImage.getType());
/*     */     
/* 656 */     g = image.createGraphics();
/*     */     
/* 658 */     g.drawImage(this.bgImage, 0, 0, null);
/*     */     
/* 660 */     return image;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\character\GDCharMasteryImagePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */