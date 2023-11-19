/*     */ package org.gdstash.ui.character;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
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
/*  50 */   private static int[] LEVEL_TIER = new int[] { 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9 };
/*  51 */   private static int[] LEVEL_BAR = new int[] { 0, 80, 100, 120, 140, 160, 176, 192, 208, 224, 240, 256, 272, 288, 304, 320, 336, 352, 368, 384, 400, 416, 432, 448, 464, 480, 491, 503, 514, 526, 537, 549, 560, 570, 580, 590, 600, 610, 620, 630, 640, 648, 656, 664, 672, 680, 688, 696, 704, 712, 720 };
/*     */   
/*     */   private static BufferedImage IMG_BACKGROUND;
/*     */   
/*     */   private static BufferedImage BORDER_SQUARE_GOLD;
/*     */   private static BufferedImage BORDER_ROUND_GOLD;
/*  57 */   private static Font SKILL_FONT = new Font("Serif", 1, 16);
/*     */   
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
/*  71 */     DBBitmap bmp = null;
/*     */     
/*  73 */     bmp = DBBitmap.get("records/ui/skills/class01/classpanelbackgroundimage.dbr");
/*  74 */     if (bmp != null) IMG_BACKGROUND = bmp.getImage();
/*     */     
/*  76 */     bmp = DBBitmap.get("records/ui/skills/classcommon/skillbuttons_goldbitmap.dbr");
/*  77 */     if (bmp != null) BORDER_SQUARE_GOLD = bmp.getImage();
/*     */     
/*  79 */     bmp = DBBitmap.get("records/ui/skills/classcommon/skillbuttons_goldroundbitmap.dbr");
/*  80 */     if (bmp != null) BORDER_ROUND_GOLD = bmp.getImage();
/*     */     
/*  82 */     DBEngineGame game = DBEngineGame.get();
/*     */     
/*  84 */     if (game == null)
/*     */       return; 
/*  86 */     int levels = game.getMaxMasteryLevel();
/*  87 */     int numTiers = game.getNumSkillTiers();
/*  88 */     DBEngineMasteryTier[] tiers = game.getSkillTierArray();
/*     */     
/*  90 */     if (tiers == null)
/*     */       return; 
/*  92 */     LEVEL_TIER = new int[levels + 1];
/*  93 */     LEVEL_BAR = new int[levels + 1];
/*     */     
/*  95 */     LEVEL_TIER[0] = 0; int i;
/*  96 */     for (i = 0; i < tiers.length - 1; i++) {
/*  97 */       int k; for (k = tiers[i].getMasteryLevel(); k < tiers[i + 1].getMasteryLevel(); k++) {
/*  98 */         LEVEL_TIER[k] = tiers[i].getMasteryTier();
/*     */       }
/* 100 */       for (k = tiers[i + 1].getMasteryLevel(); k < LEVEL_TIER.length; k++) {
/* 101 */         LEVEL_TIER[k] = tiers[i + 1].getMasteryTier();
/*     */       }
/*     */     } 
/*     */     
/* 105 */     LEVEL_BAR[0] = 0;
/* 106 */     int lastLevel = 0;
/* 107 */     float total = 0.0F; int j;
/* 108 */     for (j = 0; j < tiers.length; j++) {
/* 109 */       float step = 720.0F / numTiers / (tiers[j].getMasteryLevel() - lastLevel);
/*     */       int k;
/* 111 */       for (k = lastLevel + 1; k <= tiers[j].getMasteryLevel(); k++) {
/* 112 */         total += step;
/*     */         
/* 114 */         LEVEL_BAR[k] = Math.round(total);
/*     */       } 
/*     */       
/* 117 */       lastLevel = tiers[j].getMasteryLevel();
/*     */     } 
/*     */   }
/*     */   
/*     */   public GDCharMasteryImagePane(GDUIMasterySupport masterySupport, boolean selMastery) {
/* 122 */     this.masterySupport = masterySupport;
/* 123 */     this.selMastery = selMastery;
/* 124 */     this.skills = new LinkedList<>();
/*     */     
/* 126 */     this.bgImage = IMG_BACKGROUND;
/*     */   }
/*     */   
/*     */   public void adjustUI() {
/* 130 */     createImage();
/* 131 */     repaint();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateConfig() {}
/*     */ 
/*     */   
/*     */   private Skill getSkill(int x, int y) {
/* 139 */     int cX = getX();
/* 140 */     int cY = getY();
/*     */     
/* 142 */     Skill sk = null;
/*     */     
/* 144 */     for (Skill mps : this.skills) {
/* 145 */       int imgX = mps.button.getPosX();
/* 146 */       int imgY = mps.button.getPosY();
/* 147 */       int imgH = mps.skill.getImageUp().getHeight() + 2 * mps.button.getOffsetX();
/* 148 */       int imgW = mps.skill.getImageUp().getWidth() + 2 * mps.button.getOffsetY();
/*     */       
/* 150 */       if (imgX <= x && imgX + imgW >= x && imgY <= y && imgY + imgH >= y) {
/*     */         
/* 152 */         sk = mps;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 158 */     return sk;
/*     */   }
/*     */   
/*     */   public String getSkillDescription(int x, int y) {
/* 162 */     int xPos = x;
/* 163 */     int yPos = y;
/* 164 */     if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 165 */       xPos = xPos * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/* 166 */       yPos = yPos * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/*     */     } 
/*     */     
/* 169 */     Skill mps = getSkill(xPos, yPos);
/*     */     
/* 171 */     String s = null;
/*     */     
/* 173 */     if (mps != null) {
/* 174 */       s = mps.skill.getSkillNextLevelDescription(mps.skillLevel, this.masterySupport.getCharLevel());
/*     */     }
/*     */     
/* 177 */     return s;
/*     */   }
/*     */   
/*     */   public String decSkill(int x, int y) {
/* 181 */     int xPos = x;
/* 182 */     int yPos = y;
/* 183 */     if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 184 */       xPos = xPos * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/* 185 */       yPos = yPos * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/*     */     } 
/*     */     
/* 188 */     Skill mps = getSkill(xPos, yPos);
/*     */     
/* 190 */     String s = null;
/*     */     
/* 192 */     if (mps != null) {
/* 193 */       boolean dec = true;
/*     */       
/* 195 */       if (mps.mastery) {
/* 196 */         int maxTier = 0;
/*     */         
/* 198 */         for (Skill sk : this.skills) {
/* 199 */           if (sk.skillLevel > 0) {
/* 200 */             int tier = sk.skill.getSkillTier();
/* 201 */             if (maxTier < tier) maxTier = tier;
/*     */           
/*     */           } 
/*     */         } 
/* 205 */         if (mps.skillLevel <= 0) {
/* 206 */           dec = false;
/*     */         } else {
/*     */           
/* 209 */           int idx = mps.skillLevel - 1;
/*     */           
/* 211 */           if (idx > LEVEL_TIER.length - 1) idx = LEVEL_TIER.length - 1;
/*     */           
/* 213 */           if (maxTier > LEVEL_TIER[idx]) {
/* 214 */             dec = false;
/*     */           }
/*     */         } 
/*     */       } else {
/* 218 */         if (mps.skillLevel <= 0) {
/* 219 */           dec = false;
/*     */         }
/*     */ 
/*     */         
/* 223 */         if (mps.skill.isBaseSkill() && 
/* 224 */           mps.skillLevel == 1) {
/* 225 */           boolean check = false;
/*     */           
/* 227 */           for (Skill sk : this.skills) {
/* 228 */             if (sk.skill.isBaseSkill()) {
/* 229 */               if (check)
/*     */                 break; 
/* 231 */               if (mps.button.getButtonID().equals(sk.button.getButtonID()))
/* 232 */                 check = true; 
/*     */               continue;
/*     */             } 
/* 235 */             if (check && 
/* 236 */               sk.skillLevel > 0) {
/* 237 */               dec = false;
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
/* 248 */       if (dec) {
/* 249 */         mps.skillLevel--;
/* 250 */         this.pointsSpent--;
/*     */         
/* 252 */         this.masterySupport.setSkillPoints(this.masterySupport.getSkillPoints() + 1);
/*     */         
/* 254 */         createImage();
/* 255 */         repaint();
/*     */       } 
/*     */       
/* 258 */       s = mps.skill.getSkillNextLevelDescription(mps.skillLevel, this.masterySupport.getCharLevel());
/*     */     } 
/*     */     
/* 261 */     return s;
/*     */   }
/*     */   
/*     */   public String incSkill(int x, int y) {
/* 265 */     int xPos = x;
/* 266 */     int yPos = y;
/* 267 */     if (GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 268 */       xPos = xPos * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/* 269 */       yPos = yPos * 100 / GDStashFrame.iniConfig.sectUI.graphicScale;
/*     */     } 
/*     */     
/* 272 */     Skill mps = getSkill(xPos, yPos);
/*     */     
/* 274 */     String s = null;
/*     */     
/* 276 */     if (mps != null) {
/* 277 */       boolean inc = true;
/*     */       
/* 279 */       if (mps.mastery) {
/* 280 */         if (mps.skillLevel >= mps.skill.getMaxLevel()) {
/* 281 */           inc = false;
/*     */         }
/*     */       } else {
/* 284 */         if (mps.skillLevel >= mps.skill.getMaxLevel()) {
/* 285 */           inc = false;
/*     */         }
/*     */ 
/*     */         
/* 289 */         int idx = this.mastery.skillLevel;
/*     */         
/* 291 */         if (idx > LEVEL_TIER.length - 1) idx = LEVEL_TIER.length - 1;
/*     */         
/* 293 */         if (mps.skill.getSkillTier() > LEVEL_TIER[idx]) {
/* 294 */           inc = false;
/*     */         }
/*     */         
/* 297 */         if (mps.skill.getSkillDependencyList() != null && 
/* 298 */           !mps.skill.getSkillDependencyList().isEmpty()) {
/* 299 */           int count = 0;
/*     */           
/* 301 */           for (DBSkillDependency dep : mps.skill.getSkillDependencyList()) {
/* 302 */             if (this.masterySupport.getPointsForSkill(dep.getDependentSkillID()) > 0) {
/* 303 */               count++;
/*     */             }
/*     */           } 
/*     */           
/* 307 */           if (mps.skill.isDependencyAll())
/* 308 */           { if (count < mps.skill.getSkillDependencyList().size()) inc = false;
/*     */              }
/* 310 */           else if (count == 0) { inc = false; }
/*     */         
/*     */         } 
/*     */ 
/*     */         
/* 315 */         if (!mps.skill.isBaseSkill() && 
/* 316 */           mps.base.skillLevel <= 0) inc = false;
/*     */       
/*     */       } 
/*     */       
/* 320 */       int points = this.masterySupport.getSkillPoints();
/* 321 */       if (points <= 0) inc = false;
/*     */       
/* 323 */       if (inc) {
/* 324 */         mps.skillLevel++;
/* 325 */         this.pointsSpent++;
/*     */         
/* 327 */         this.masterySupport.setSkillPoints(points - 1);
/*     */         
/* 329 */         createImage();
/* 330 */         repaint();
/*     */       } 
/*     */       
/* 333 */       s = mps.skill.getSkillNextLevelDescription(mps.skillLevel, this.masterySupport.getCharLevel());
/*     */     } 
/*     */     
/* 336 */     return s;
/*     */   }
/*     */   
/*     */   public int getPointsSpent() {
/* 340 */     return this.pointsSpent;
/*     */   }
/*     */   
/*     */   public int getPointsForSkill(String skillID) {
/* 344 */     int points = 0;
/*     */     
/* 346 */     for (Skill sk : this.skills) {
/* 347 */       if (sk.skill.getSkillID().equals(skillID)) {
/* 348 */         points = sk.skillLevel;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 354 */     return points;
/*     */   }
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 358 */     this.gdc = gdc;
/*     */   }
/*     */   
/*     */   public void updateChar(GDChar gdc) {
/* 362 */     if (gdc == null)
/* 363 */       return;  if (this.mastery == null)
/*     */       return; 
/* 365 */     gdc.setMasterySkills(this.mastery.skill.getSkillID(), this.skills);
/*     */   }
/*     */   
/*     */   private void determineMasterySkills(String masteryID) {
/* 369 */     this.classTable = null;
/* 370 */     this.skillTree = null;
/* 371 */     this.mastery = null;
/* 372 */     this.skills.clear();
/* 373 */     this.pointsSpent = 0;
/*     */     
/* 375 */     if (masteryID == null)
/*     */       return; 
/* 377 */     if (!this.selMastery) {
/* 378 */       boolean found = false;
/*     */       
/* 380 */       if (this.gdc != null) {
/* 381 */         GDChar.SkillInfo[] infos = this.gdc.getMasteryInfo();
/*     */         
/* 383 */         if (infos != null) {
/* 384 */           int i; for (i = 0; i < infos.length; i++) {
/* 385 */             if ((infos[i]).id.equals(masteryID)) {
/* 386 */               found = true;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 394 */       if (!found)
/*     */         return; 
/*     */     } 
/* 397 */     DBSkillButton sb = DBSkillButton.getBySkillID(masteryID);
/*     */     
/* 399 */     if (sb == null)
/*     */       return; 
/* 401 */     this.classTable = DBClassTable.getByMasteryID(sb.getButtonID());
/*     */     
/* 403 */     if (this.classTable == null)
/*     */       return; 
/* 405 */     List<DBClassTableButton> buttons = this.classTable.getButtonList();
/*     */     
/* 407 */     if (masteryID != null) {
/* 408 */       String treeID = DBSkillTreeAlloc.getTreeIDBySkillID(masteryID);
/*     */       
/* 410 */       if (treeID != null) {
/* 411 */         this.skillTree = DBSkillTree.get(treeID);
/*     */       }
/*     */     } 
/*     */     
/* 415 */     int index = 1;
/* 416 */     for (String skillID : this.skillTree.getSkillIDList()) {
/* 417 */       for (DBClassTableButton button : buttons) {
/* 418 */         if (button.getSkill() == null)
/*     */           continue; 
/* 420 */         if (skillID.equals(button.getSkill().getSkillID())) {
/* 421 */           Skill temp = new Skill();
/* 422 */           temp.button = button.getButton();
/* 423 */           temp.skill = button.getSkill();
/* 424 */           temp.base = null;
/* 425 */           temp.index = index;
/* 426 */           temp.skillLevel = 0;
/* 427 */           temp.mastery = false;
/*     */           
/* 429 */           if (temp.skill.isMastery()) {
/* 430 */             temp.mastery = true;
/*     */             
/* 432 */             this.mastery = temp;
/*     */           } 
/*     */           
/* 435 */           if (!this.selMastery) {
/* 436 */             List<GDCharSkill> charSkills = this.gdc.getSkillsByMastery(masteryID);
/* 437 */             for (GDCharSkill cs : charSkills) {
/* 438 */               if (cs.getID().equals(temp.skill.getSkillID())) {
/* 439 */                 temp.skillLevel = cs.getLevel();
/*     */                 
/* 441 */                 this.pointsSpent += temp.skillLevel;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 448 */           this.skills.add(temp);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 454 */       index++;
/*     */     } 
/*     */     
/* 457 */     Skill base = null;
/* 458 */     for (Skill mps : this.skills) {
/* 459 */       if (mps.skill.isBaseSkill()) {
/* 460 */         base = mps; continue;
/*     */       } 
/* 462 */       mps.base = base;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void createImage() {
/* 468 */     BufferedImage image = null;
/*     */     
/* 470 */     if (this.classTable != null) {
/* 471 */       DBBitmap bar = this.classTable.getMasteryBarDB();
/* 472 */       DBBitmap img = this.classTable.getMasteryBitmapDB();
/* 473 */       DBBitmap bg = this.classTable.getSkillPaneDB();
/* 474 */       BufferedImage bi = null;
/*     */       
/* 476 */       if (bg == null) {
/* 477 */         bi = IMG_BACKGROUND;
/*     */       } else {
/* 479 */         bi = bg.getImage();
/*     */       } 
/*     */       
/* 482 */       Graphics g = null;
/*     */       
/* 484 */       image = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
/*     */       
/* 486 */       g = image.createGraphics();
/*     */       
/* 488 */       g.drawImage(bi, 0, 0, null);
/* 489 */       if (img != null) {
/* 490 */         g.drawImage(img.getImage(), img.getPosX(), img.getPosY(), null);
/*     */       }
/*     */       
/* 493 */       if (this.mastery != null) {
/* 494 */         g.drawImage(bar.getImage(), bar.getPosX(), bar.getPosY(), LEVEL_BAR[this.mastery.skillLevel], bar.getImage().getHeight(), null);
/*     */       }
/*     */       
/* 497 */       List<DBClassTableButton> btns = this.classTable.getButtonList();
/*     */ 
/*     */       
/* 500 */       for (Skill mps : this.skills) {
/* 501 */         List<DBSkillConnector> connections = mps.skill.getSkillConnectionList();
/*     */         
/* 503 */         int imgX = mps.button.getPosX() + mps.button.getOffsetX();
/* 504 */         int imgY = mps.button.getPosY() + mps.button.getOffsetY();
/*     */         
/* 506 */         if (connections != null && 
/* 507 */           !connections.isEmpty()) {
/* 508 */           int x = imgX + 32;
/* 509 */           int y = imgY - 24;
/* 510 */           for (DBSkillConnector connection : connections) {
/* 511 */             if (connection == null) {
/*     */               continue;
/*     */             }
/*     */             
/* 515 */             BufferedImage coff = connection.getConnectionOff();
/* 516 */             BufferedImage con = connection.getConnectionOn();
/*     */             
/* 518 */             if (con != null && coff != null) {
/* 519 */               if (mps.skillLevel == 0) {
/* 520 */                 g.drawImage(connection.getConnectionOff(), x, y, null);
/*     */               } else {
/* 522 */                 g.drawImage(connection.getConnectionOn(), x, y, null);
/*     */               } 
/*     */               
/* 525 */               BufferedImage ci = connection.getConnectionOn();
/* 526 */               if (ci == null) ci = connection.getConnectionOff();
/*     */               
/* 528 */               x += ci.getWidth();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 534 */       for (Skill mps : this.skills) {
/* 535 */         int imgX = mps.button.getPosX() + mps.button.getOffsetX();
/* 536 */         int imgY = mps.button.getPosY() + mps.button.getOffsetY();
/* 537 */         int brdX = mps.button.getPosX();
/* 538 */         int brdY = mps.button.getPosY();
/*     */         
/* 540 */         if (mps.mastery) {
/* 541 */           g.drawImage(mps.skill.getImageUp(), imgX, imgY, null);
/*     */           continue;
/*     */         } 
/* 544 */         int idx = this.mastery.skillLevel;
/*     */         
/* 546 */         if (idx > LEVEL_TIER.length - 1) idx = LEVEL_TIER.length - 1;
/*     */         
/* 548 */         if (mps.skill.getSkillTier() <= LEVEL_TIER[idx]) {
/* 549 */           if (mps.skill.isBaseSkill()) {
/* 550 */             if (mps.button.isCircularButton()) {
/* 551 */               g.drawImage(BORDER_ROUND_GOLD, brdX, brdY, null);
/*     */             } else {
/* 553 */               g.drawImage(BORDER_SQUARE_GOLD, brdX, brdY, null);
/*     */             }
/*     */           
/* 556 */           } else if (mps.base.skillLevel > 0) {
/* 557 */             if (mps.button.isCircularButton()) {
/* 558 */               g.drawImage(BORDER_ROUND_GOLD, brdX, brdY, null);
/*     */             } else {
/* 560 */               g.drawImage(BORDER_SQUARE_GOLD, brdX, brdY, null);
/*     */             }
/*     */           
/* 563 */           } else if (mps.button.isCircularButton()) {
/* 564 */             g.drawImage(GDImagePool.imgBorderRound, brdX, brdY, null);
/*     */           } else {
/* 566 */             g.drawImage(GDImagePool.imgBorderSquare, brdX, brdY, null);
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 571 */         else if (mps.button.isCircularButton()) {
/* 572 */           g.drawImage(GDImagePool.imgBorderRound, brdX, brdY, null);
/*     */         } else {
/* 574 */           g.drawImage(GDImagePool.imgBorderSquare, brdX, brdY, null);
/*     */         } 
/*     */ 
/*     */         
/* 578 */         if (mps.skillLevel == 0) {
/*     */           
/* 580 */           BufferedImage si = mps.skill.getImageDown();
/* 581 */           if (si == null) si = mps.skill.getImageUp();
/*     */           
/* 583 */           g.drawImage(si, imgX, imgY, null); continue;
/*     */         } 
/* 585 */         g.drawImage(mps.skill.getImageUp(), imgX, imgY, null);
/*     */         
/* 587 */         g.setColor(Color.CYAN);
/* 588 */         g.setFont(SKILL_FONT);
/* 589 */         if (mps.skillLevel < 10) {
/* 590 */           g.drawString(Integer.toString(mps.skillLevel) + " / " + Integer.toString(mps.skill.getMaxLevel()), imgX - 2, imgY + 52);
/*     */           continue;
/*     */         } 
/* 593 */         g.drawString(Integer.toString(mps.skillLevel) + " / " + Integer.toString(mps.skill.getMaxLevel()), imgX - 6, imgY + 52);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 600 */     if (image == null) {
/* 601 */       this.bgImage = IMG_BACKGROUND;
/*     */     } else {
/* 603 */       this.bgImage = image;
/*     */     } 
/*     */     
/* 606 */     if (this.bgImage != null && 
/* 607 */       GDStashFrame.iniConfig.sectUI.graphicScale != 100) {
/* 608 */       int w = this.bgImage.getWidth() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 609 */       int h = this.bgImage.getHeight() * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/*     */       
/* 611 */       this.bgImage = DDSLoader.getScaledImage(this.bgImage, w, h);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMastery(String masteryID) {
/* 617 */     determineMasterySkills(masteryID);
/* 618 */     createImage();
/* 619 */     repaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintComponent(Graphics g) {
/* 628 */     super.paintComponent(g);
/*     */     
/* 630 */     if (this.bgImage == null)
/*     */       return; 
/* 632 */     g.drawImage(drawGraphics(), 0, 0, null);
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize() {
/* 636 */     if (this.bgImage == null) return super.getPreferredSize();
/*     */     
/* 638 */     int w = this.bgImage.getWidth();
/* 639 */     int h = this.bgImage.getHeight();
/*     */     
/* 641 */     return new Dimension(w, h);
/*     */   }
/*     */   
/*     */   public Dimension getMaximumSize() {
/* 645 */     return getPreferredSize();
/*     */   }
/*     */   
/*     */   public Dimension getMinimumSize() {
/* 649 */     return getPreferredSize();
/*     */   }
/*     */   
/*     */   public int getPreferredWidth() {
/* 653 */     return (int)getPreferredSize().getWidth();
/*     */   }
/*     */   
/*     */   public int getPreferredHeight() {
/* 657 */     return (int)getPreferredSize().getHeight();
/*     */   }
/*     */   
/*     */   private BufferedImage drawGraphics() {
/* 661 */     if (this.bgImage == null) return null;
/*     */ 
/*     */     
/* 664 */     Graphics g = null;
/* 665 */     BufferedImage image = null;
/*     */     
/* 667 */     image = new BufferedImage(this.bgImage.getWidth(), this.bgImage.getHeight(), this.bgImage.getType());
/*     */     
/* 669 */     g = image.createGraphics();
/*     */     
/* 671 */     g.drawImage(this.bgImage, 0, 0, null);
/*     */     
/* 673 */     return image;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharMasteryImagePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */