/*      */ package org.gdstash.character;

import org.gdstash.db.*;
import org.gdstash.file.GDFileSize;
import org.gdstash.file.GDReader;
import org.gdstash.file.GDWriter;
import org.gdstash.item.GDAbstractContainer;
import org.gdstash.item.GDItem;
import org.gdstash.ui.character.GDCharFactionPane;
import org.gdstash.ui.character.GDCharMasteryImagePane;
import org.gdstash.util.FileVersionException;
import org.gdstash.util.GDConstants;
import org.gdstash.util.GDMsgFormatter;
import org.gdstash.util.GDMsgLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GDChar implements GDFileSize { public static final byte BYTE_VANILLA = 0; public static final byte BYTE_ASHES_OF_MALMOUTH = 1; public static final byte BYTE_FORGOTTEN_GODS = 3; public static final byte BYTE_FALSE = 0; public static final byte BYTE_TRUE = 1; public static final byte SEX_FEMALE = 0; public static final byte SEX_MALE = 1; public static final String TEXTURE_MALE = "creatures/pc/hero02.tex"; public static final byte SOFTCORE = 0; public static final byte HARDCORE = 1; public static final byte DIFFICULTY_NORMAL = 0; public static final byte DIFFICULTY_ELITE = 1; public static final byte DIFFICULTY_ULTIMATE = 2; public static final byte CRUCIBLE_ASPIRANT = 0; public static final byte CRUCIBLE_CHALLENGER = 1; public static final byte CRUCIBLE_GLADIATOR = 2; public static final int EQUIP_SLOT_HEAD = 0; public static final int EQUIP_SLOT_AMULET = 1; public static final int EQUIP_SLOT_CHEST = 2; public static final int EQUIP_SLOT_LEGS = 3; public static final int EQUIP_SLOT_FEET = 4; public static final int EQUIP_SLOT_HANDS = 5; public static final int EQUIP_SLOT_RING_RIGHT = 6;
/*      */   public static final int EQUIP_SLOT_RING_LEFT = 7;
/*      */   public static final int EQUIP_SLOT_BELT = 8;
/*      */   public static final int EQUIP_SLOT_SHOULDERS = 9;
/*      */   public static final int EQUIP_SLOT_MEDAL = 10;
/*      */   public static final int EQUIP_SLOT_RELIC = 11;
/*      */   public static final int EQUIP_SLOT_WEAPON_1_RIGHT = 12;
/*      */   public static final int EQUIP_SLOT_WEAPON_1_LEFT = 13;
/*      */   public static final int EQUIP_SLOT_WEAPON_2_RIGHT = 14;
/*      */   public static final int EQUIP_SLOT_WEAPON_2_LEFT = 15;
/*      */   public static final int EQUIP_SLOT_HAND_RIGHT = 0;
/*      */   public static final int EQUIP_SLOT_HAND_LEFT = 1;
/*      */   private static final int VERSION_1 = 1;
/*      */   private static final int VERSION_2 = 2;
/*      */   private static final int VERSION_6 = 6;
/*      */   private static final int VERSION_7 = 7;
/*      */   private static final int VERSION_8 = 8;
/*      */   private File file;
/*      */   private int key;
/*      */   private int version;
/*      */   private boolean charError;
/*      */   private GDCharHeader header;
/*      */   private GDCharUID uid;
/*      */   private GDCharInfo info;
/*      */   private GDCharBio bio;
/*      */   private GDCharInventory inventory;
/*      */   private GDCharStash stash;
/*      */   private GDCharRespawnList respawns;
/*      */   private GDCharTeleportList teleports;
/*      */   private GDCharMarkerList markers;
/*      */   private GDCharShrineList shrines;
/*      */   private GDCharSkillList skills;
/*      */   private GDCharNoteList notes;
/*      */   private GDCharFactionList factions;
/*      */   private GDCharUISettings ui;
/*      */   private GDCharTutorialList tutorials;
/*      */   private GDCharStats stats;
/*      */   private GDCharCrucible crucible;
/*      */   
/*      */   public static class SkillInfo { public String id;
/*      */     
/*      */     public SkillInfo(GDCharSkill skill) {
/*   43 */       if (skill == null)
/*      */         return; 
/*   45 */       this.id = skill.getID();
/*      */       
/*   47 */       DBSkill dbSkill = DBSkill.get(this.id);
/*   48 */       if (dbSkill == null)
/*      */         return; 
/*   50 */       this.name = dbSkill.getName();
/*   51 */       this.points = skill.getLevel();
/*      */     }
/*      */     public String name; public int points;
/*      */     public SkillInfo(GDCharSkill skill, GDChar gdc) {
/*   55 */       if (skill == null)
/*      */         return; 
/*   57 */       this.id = skill.getID();
/*      */       
/*   59 */       DBSkill dbSkill = DBSkill.get(this.id);
/*   60 */       if (dbSkill == null)
/*      */         return; 
/*   62 */       this.name = dbSkill.getName();
/*      */       
/*   64 */       String treeID = DBSkillTreeAlloc.getTreeIDBySkillID(this.id);
/*   65 */       if (treeID != null) {
/*   66 */         DBEnginePlayer player = DBEnginePlayer.get();
/*      */         
/*   68 */         DBEnginePlayerMasteries mt = player.retrieveID(treeID);
/*      */         
/*   70 */         if (mt != null) {
/*   71 */           int index = mt.getIndex();
/*   72 */           String tag = "tagSkillClassName" + String.format("%02d", new Object[] { Integer.valueOf(index) });
/*      */           
/*   74 */           DBEngineTagText text = DBEngineTagText.get(tag);
/*      */           
/*   76 */           if (text != null) {
/*   77 */             this.name = text.getText(gdc.getGenderCode());
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*   82 */       this.points = skill.getLevel();
/*      */     }
/*      */     
/*      */     public SkillInfo(String skillID, int level) {
/*   86 */       if (skillID == null)
/*      */         return; 
/*   88 */       this.id = skillID;
/*      */       
/*   90 */       DBSkill dbSkill = DBSkill.get(this.id);
/*   91 */       this.name = dbSkill.getName();
/*      */       
/*   93 */       this.points = level;
/*      */     } }
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
/*      */   public GDChar(File file) {
/*  164 */     this.file = file;
/*  165 */     this.charError = false;
/*      */     
/*  167 */     this.header = new GDCharHeader();
/*  168 */     this.uid = new GDCharUID();
/*  169 */     this.info = new GDCharInfo();
/*  170 */     this.bio = new GDCharBio();
/*  171 */     this.inventory = new GDCharInventory(this);
/*  172 */     this.stash = new GDCharStash(this);
/*  173 */     this.respawns = new GDCharRespawnList();
/*  174 */     this.teleports = new GDCharTeleportList(this);
/*  175 */     this.markers = new GDCharMarkerList();
/*  176 */     this.shrines = new GDCharShrineList(this);
/*  177 */     this.skills = new GDCharSkillList();
/*  178 */     this.notes = new GDCharNoteList();
/*  179 */     this.factions = new GDCharFactionList();
/*  180 */     this.ui = new GDCharUISettings();
/*  181 */     this.tutorials = new GDCharTutorialList();
/*  182 */     this.stats = new GDCharStats();
/*  183 */     this.crucible = new GDCharCrucible();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCharName() {
/*  191 */     if (this.header == null) return null;
/*      */     
/*  193 */     return this.header.getCharName();
/*      */   }
/*      */   
/*      */   public byte getSex() {
/*  197 */     if (this.header == null) return 1;
/*      */     
/*  199 */     return this.header.getSex();
/*      */   }
/*      */   
/*      */   public int getGenderCode() {
/*  203 */     int genderCode = 0;
/*  204 */     byte sex = getSex();
/*      */     
/*  206 */     if (sex == 0) genderCode = 1;
/*      */     
/*  208 */     return genderCode;
/*      */   }
/*      */   
/*      */   public boolean isHardcore() {
/*  212 */     if (this.header == null) return false;
/*      */     
/*  214 */     return this.header.isHardcore();
/*      */   }
/*      */   
/*      */   public boolean isAsheshOfMalmouthChar() {
/*  218 */     if (this.header == null) return false;
/*      */     
/*  220 */     return this.header.isAsheshOfMalmouthChar();
/*      */   }
/*      */   
/*      */   public boolean isForgottenGodsChar() {
/*  224 */     if (this.header == null) return false;
/*      */     
/*  226 */     return this.header.isForgottenGodsChar();
/*      */   }
/*      */   
/*      */   public boolean isInMainQuest() {
/*  230 */     if (this.info == null) return false;
/*      */     
/*  232 */     return this.info.isInMainQuest();
/*      */   }
/*      */   
/*      */   public byte getGreatestCampaignDifficulty() {
/*  236 */     if (this.info == null) return 0;
/*      */     
/*  238 */     return this.info.getGreatestCampaignDifficulty();
/*      */   }
/*      */   
/*      */   public byte getGreatestCrucibleDifficulty() {
/*  242 */     if (this.info == null) return 0;
/*      */     
/*  244 */     return this.info.getGreatestCrucibleDifficulty();
/*      */   }
/*      */   
/*      */   public int getMoney() {
/*  248 */     if (this.info == null) return 0;
/*      */     
/*  250 */     return this.info.getMoney();
/*      */   }
/*      */   
/*      */   public int getTributes() {
/*  254 */     if (this.info == null) return 0;
/*      */     
/*  256 */     return this.info.getTributes();
/*      */   }
/*      */   
/*      */   public int getLevel() {
/*  260 */     if (this.bio == null) return 1;
/*      */     
/*  262 */     return this.bio.getLevel();
/*      */   }
/*      */   
/*      */   public int getExperience() {
/*  266 */     if (this.bio == null) return 0;
/*      */     
/*  268 */     return this.bio.getExperience();
/*      */   }
/*      */   
/*      */   public int getStatPoints() {
/*  272 */     if (this.bio == null) return 0;
/*      */     
/*  274 */     return this.bio.getStatPoints();
/*      */   }
/*      */   
/*      */   public int getSkillPoints() {
/*  278 */     if (this.bio == null) return 0;
/*      */     
/*  280 */     return this.bio.getSkillPoints();
/*      */   }
/*      */   
/*      */   public int getDevotionPoints() {
/*  284 */     if (this.bio == null) return 0;
/*      */     
/*  286 */     return this.bio.getDevotionPoints();
/*      */   }
/*      */   
/*      */   public float getPhysique() {
/*  290 */     if (this.bio == null) return 0.0F;
/*      */     
/*  292 */     return this.bio.getPhysique();
/*      */   }
/*      */   
/*      */   public float getCunning() {
/*  296 */     if (this.bio == null) return 0.0F;
/*      */     
/*  298 */     return this.bio.getCunning();
/*      */   }
/*      */   
/*      */   public float getSpirit() {
/*  302 */     if (this.bio == null) return 0.0F;
/*      */     
/*  304 */     return this.bio.getSpirit();
/*      */   }
/*      */   
/*      */   public float getHealth() {
/*  308 */     if (this.bio == null) return 0.0F;
/*      */     
/*  310 */     return this.bio.getHealth();
/*      */   }
/*      */   
/*      */   public float getEnergy() {
/*  314 */     if (this.bio == null) return 0.0F;
/*      */     
/*  316 */     return this.bio.getEnergy();
/*      */   }
/*      */   
/*      */   public int getStashHeight() {
/*  320 */     if (this.stash == null) return 18;
/*      */     
/*  322 */     return this.stash.getHeight();
/*      */   }
/*      */   
/*      */   public int getStashWidth() {
/*  326 */     if (this.stash == null) return 10;
/*      */     
/*  328 */     return this.stash.getWidth();
/*      */   }
/*      */   
/*      */   public List<GDAbstractContainer> getStashPages() {
/*  332 */     List<GDAbstractContainer> list = new LinkedList<>();
/*      */     
/*  334 */     if (this.stash == null) return list;
/*      */     
/*  336 */     return this.stash.getStashPages();
/*      */   }
/*      */   
/*      */   public GDCharUID getRespawnPoint(int difficulty) {
/*  340 */     if (this.respawns == null) return null;
/*      */     
/*  342 */     return this.respawns.getRespawnPoint(difficulty);
/*      */   }
/*      */   
/*      */   public List<GDCharUID> getRiftList(int difficulty) {
/*  346 */     List<GDCharUID> list = new LinkedList<>();
/*      */     
/*  348 */     if (this.teleports == null) return list;
/*      */     
/*  350 */     return this.teleports.getRiftList(difficulty);
/*      */   }
/*      */   
/*      */   public List<GDCharUID> getDiscoveredShrinesList(int difficulty) {
/*  354 */     List<GDCharUID> list = new LinkedList<>();
/*      */     
/*  356 */     if (this.shrines == null) return list;
/*      */     
/*  358 */     return this.shrines.getDiscoveredShrinesList(difficulty);
/*      */   }
/*      */   
/*      */   public List<GDCharUID> getRestoredShrinesList(int difficulty) {
/*  362 */     List<GDCharUID> list = new LinkedList<>();
/*      */     
/*  364 */     if (this.shrines == null) return list;
/*      */     
/*  366 */     return this.shrines.getRestoredShrinesList(difficulty);
/*      */   }
/*      */   
/*      */   public int getSkillReclaimPoints() {
/*  370 */     if (this.skills == null) return 0;
/*      */     
/*  372 */     return this.skills.getSkillReclaimPoints();
/*      */   }
/*      */   
/*      */   public int getDevotionReclaimPoints() {
/*  376 */     if (this.skills == null) return 0;
/*      */     
/*  378 */     return this.skills.getDevotionReclaimPoints();
/*      */   }
/*      */   
/*      */   public int getSkillMaxLevel(String skillID) {
/*  382 */     if (this.skills == null) return 0;
/*      */     
/*  384 */     return this.skills.getSkillMaxLevel(skillID);
/*      */   }
/*      */   
/*      */   public int[] getFactionReputations() {
/*  388 */     int[] reputations = null;
/*      */     
/*  390 */     if (this.factions == null) {
/*  391 */       reputations = new int[GDCharFactionPane.FACTIONS.length];
/*      */       
/*  393 */       for (int i = 0; i < reputations.length; i++) {
/*  394 */         reputations[i] = 0;
/*      */       }
/*      */     } else {
/*  397 */       reputations = this.factions.getFactionReputations();
/*      */     } 
/*      */     
/*  400 */     return reputations;
/*      */   }
/*      */   
/*      */   public int getDeaths() {
/*  404 */     if (this.stats == null) return 0;
/*      */     
/*  406 */     return this.stats.getDeaths();
/*      */   }
/*      */   
/*      */   public String getPlayTimeAsDHM() {
/*  410 */     if (this.stats == null) return null;
/*      */     
/*  412 */     return this.stats.getPlayTimeAsDHM();
/*      */   }
/*      */   
/*      */   public int getGreatestDamage() {
/*  416 */     if (this.stats == null) return 0;
/*      */     
/*  418 */     return this.stats.getGreatestDamage();
/*      */   }
/*      */   
/*      */   public int getKillsMonster() {
/*  422 */     if (this.stats == null) return 0;
/*      */     
/*  424 */     return this.stats.getKillsMonster();
/*      */   }
/*      */   
/*      */   public int getKillsHero() {
/*  428 */     if (this.stats == null) return 0;
/*      */     
/*  430 */     return this.stats.getKillsHero();
/*      */   }
/*      */   
/*      */   public int getKillsNemesis() {
/*  434 */     if (this.stats == null) return 0;
/*      */     
/*  436 */     return this.stats.getKillsNemesis();
/*      */   }
/*      */   
/*      */   public int getLoreNotesCollected() {
/*  440 */     if (this.stats == null) return 0;
/*      */     
/*  442 */     return this.stats.getLoreNotesCollected();
/*      */   }
/*      */   
/*      */   public int getOneShotChestsOpened() {
/*  446 */     if (this.stats == null) return 0;
/*      */     
/*  448 */     return this.stats.getOneShotChestsOpened();
/*      */   }
/*      */   
/*      */   public int getItemsCrafted() {
/*  452 */     if (this.stats == null) return 0;
/*      */     
/*  454 */     return this.stats.getItemsCrafted();
/*      */   }
/*      */   
/*      */   public int getRelicsCrafted() {
/*  458 */     if (this.stats == null) return 0;
/*      */     
/*  460 */     return this.stats.getRelicsCrafted();
/*      */   }
/*      */   
/*      */   public int getRelicsTranscendentCrafted() {
/*  464 */     if (this.stats == null) return 0;
/*      */     
/*  466 */     return this.stats.getRelicsTranscendentCrafted();
/*      */   }
/*      */   
/*      */   public int getRelicsMythicalCrafted() {
/*  470 */     if (this.stats == null) return 0;
/*      */     
/*  472 */     return this.stats.getRelicsMythicalCrafted();
/*      */   }
/*      */   
/*      */   public boolean hasErrors() {
/*  476 */     return this.charError;
/*      */   }
/*      */   
/*      */   public boolean hasChanged() {
/*  480 */     boolean hasChanged = false;
/*      */     
/*  482 */     hasChanged = (hasChanged || this.header.hasChanged());
/*  483 */     hasChanged = (hasChanged || this.info.hasChanged());
/*  484 */     hasChanged = (hasChanged || this.bio.hasChanged());
/*  485 */     hasChanged = (hasChanged || this.inventory.hasChanged());
/*  486 */     hasChanged = (hasChanged || this.stash.hasChanged());
/*  487 */     hasChanged = (hasChanged || this.respawns.hasChanged());
/*  488 */     hasChanged = (hasChanged || this.teleports.hasChanged());
/*  489 */     hasChanged = (hasChanged || this.shrines.hasChanged());
/*  490 */     hasChanged = (hasChanged || this.skills.hasChanged());
/*  491 */     hasChanged = (hasChanged || this.factions.hasChanged());
/*  492 */     hasChanged = (hasChanged || this.stats.hasChanged());
/*      */     
/*  494 */     return hasChanged;
/*      */   }
/*      */   
/*      */   public GDCharEquippedContainer getEquipment() {
/*  498 */     GDCharEquippedContainer equipment = null;
/*      */     
/*  500 */     if (this.inventory != null) equipment = this.inventory.getEquipment();
/*      */     
/*  502 */     return equipment;
/*      */   }
/*      */   
/*      */   public GDCharInventorySack getInventory() {
/*  506 */     GDCharInventorySack sack = null;
/*      */     
/*  508 */     if (this.inventory != null) sack = this.inventory.getInventory();
/*      */     
/*  510 */     return sack;
/*      */   }
/*      */   
/*      */   public List<GDCharInventorySack> getBags() {
/*  514 */     List<GDCharInventorySack> list = new LinkedList<>();
/*      */     
/*  516 */     if (this.inventory != null) list = this.inventory.getBags();
/*      */     
/*  518 */     return list;
/*      */   }
/*      */   
/*      */   public GDCharStash getStash() {
/*  522 */     return this.stash;
/*      */   }
/*      */   
/*      */   public List<GDItem> getItems() {
/*  526 */     List<GDItem> list = new LinkedList<>();
/*      */     
/*  528 */     list.addAll(this.inventory.getItems());
/*  529 */     list.addAll(this.stash.getItemList());
/*      */     
/*  531 */     return list;
/*      */   }
/*      */   
/*      */   private List<GDCharSkill> getMasteries() {
/*  535 */     List<GDCharSkill> masteries = null;
/*      */     
/*  537 */     if (this.skills == null) {
/*  538 */       masteries = new LinkedList<>();
/*      */     } else {
/*  540 */       masteries = this.skills.getMasteries();
/*      */     } 
/*      */     
/*  543 */     return masteries;
/*      */   }
/*      */   
/*      */   public SkillInfo[] getMasteryInfo() {
/*  547 */     List<GDCharSkill> masteries = getMasteries();
/*      */     
/*  549 */     if (masteries.size() == 0) return null;
/*      */     
/*  551 */     SkillInfo[] infos = new SkillInfo[masteries.size()];
/*      */     
/*  553 */     int i = 0;
/*  554 */     for (GDCharSkill skill : masteries) {
/*  555 */       SkillInfo info = new SkillInfo(skill, this);
/*      */       
/*  557 */       infos[i] = info;
/*      */       
/*  559 */       i++;
/*      */     } 
/*      */     
/*  562 */     return infos;
/*      */   }
/*      */   
/*      */   public List<GDCharSkill> getCharSkillList() {
/*  566 */     List<GDCharSkill> list = null;
/*      */     
/*  568 */     if (this.skills == null) {
/*  569 */       list = new LinkedList<>();
/*      */     } else {
/*  571 */       list = this.skills.getCharSkillList();
/*      */     } 
/*      */     
/*  574 */     return list;
/*      */   }
/*      */   
/*      */   public List<GDCharSkill> getSkillsByMastery(String masteryID) {
/*  578 */     List<GDCharSkill> list = null;
/*      */     
/*  580 */     if (this.skills == null) {
/*  581 */       list = new LinkedList<>();
/*      */     } else {
/*  583 */       list = this.skills.getSkillsByMastery(masteryID);
/*      */     } 
/*      */     
/*  586 */     return list;
/*      */   }
/*      */   
/*      */   private List<GDCharSkill> getDevotions() {
/*  590 */     List<GDCharSkill> devotions = null;
/*      */     
/*  592 */     if (this.skills == null) {
/*  593 */       devotions = new LinkedList<>();
/*      */     } else {
/*  595 */       devotions = this.skills.getDevotions();
/*      */     } 
/*      */     
/*  598 */     return devotions;
/*      */   }
/*      */   
/*      */   public SkillInfo[] getDevotionInfo() {
/*  602 */     List<GDCharSkill> devotions = getDevotions();
/*      */     
/*  604 */     if (devotions.size() == 0) return null;
/*      */     
/*  606 */     SkillInfo[] infos = new SkillInfo[devotions.size()];
/*      */     
/*  608 */     int i = 0;
/*  609 */     for (GDCharSkill skill : devotions) {
/*  610 */       SkillInfo info = new SkillInfo(skill);
/*      */       
/*  612 */       infos[i] = info;
/*      */       
/*  614 */       i++;
/*      */     } 
/*      */     
/*  617 */     return infos;
/*      */   }
/*      */   
/*      */   private GDCharHeader getCharHeader() {
/*  621 */     return this.header;
/*      */   }
/*      */   
/*      */   private GDCharInfo getCharInfo() {
/*  625 */     return this.info;
/*      */   }
/*      */   
/*      */   public boolean isRoTCampaign() {
/*  629 */     boolean rot = false;
/*  630 */     for (GDCharUID uid : this.teleports.getRiftList(0)) {
/*  631 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_01_ROGUE_ENCAMPMENT)) {
/*  632 */         rot = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/*  638 */     return rot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFileDir(File fDir) {
/*      */     try {
/*  647 */       String name = this.file.getName();
/*  648 */       String dir = fDir.getCanonicalPath();
/*      */       
/*  650 */       dir = dir + GDConstants.FILE_SEPARATOR + name;
/*      */       
/*  652 */       File f = new File(dir);
/*      */       
/*  654 */       this.file = f;
/*      */     }
/*  656 */     catch (IOException ex) {
/*  657 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setCharName(String charName) {
/*  662 */     this.header.setCharName(charName);
/*      */   }
/*      */   
/*      */   public void setSex(byte sex) {
/*  666 */     this.header.setSex(sex);
/*      */     
/*  668 */     if (sex == 1) {
/*  669 */       this.info.setTexture("creatures/pc/hero02.tex");
/*      */     } else {
/*  671 */       this.info.setTexture(null);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setHardcore(boolean hardcore) {
/*  676 */     this.header.setHardcore(hardcore);
/*      */   }
/*      */   
/*      */   public void setInMainQuest(boolean isInMainQuest) {
/*  680 */     this.info.setInMainQuest(isInMainQuest);
/*      */   }
/*      */   
/*      */   public void setGreatestCampaignDifficulty(byte greatestDifficulty) {
/*  684 */     this.info.setGreatestCampaignDifficulty(greatestDifficulty);
/*      */   }
/*      */   
/*      */   public void setGreatestCrucibleDifficulty(byte greatestDifficulty) {
/*  688 */     this.info.setGreatestCrucibleDifficulty(greatestDifficulty);
/*      */   }
/*      */   
/*      */   public void setMoney(int money) {
/*  692 */     this.info.setMoney(money);
/*      */   }
/*      */   
/*      */   public void setTributes(int tributes) {
/*  696 */     this.info.setTributes(tributes);
/*      */   }
/*      */   
/*      */   public void setLevel(int level) {
/*  700 */     this.header.setLevel(level);
/*  701 */     this.bio.setLevel(level);
/*  702 */     this.stats.setMaxLevel(level);
/*      */     
/*  704 */     int maxMasteries = 0;
/*  705 */     if (level > 1) maxMasteries = 1; 
/*  706 */     if (level > 9) maxMasteries = 2;
/*      */     
/*  708 */     this.skills.setMasteriesAllowed(maxMasteries);
/*      */   }
/*      */   
/*      */   public void setGreatestDamage(float damage) {
/*  712 */     this.stats.setGreatestDamage(damage);
/*      */   }
/*      */   
/*      */   public void setExperience(int experience) {
/*  716 */     this.bio.setExperience(experience);
/*      */   }
/*      */   
/*      */   public void setStatPoints(int modifierPoints) {
/*  720 */     this.bio.setStatPoints(modifierPoints);
/*      */   }
/*      */   
/*      */   public void setSkillPoints(int skillPoints) {
/*  724 */     this.bio.setSkillPoints(skillPoints);
/*      */   }
/*      */   
/*      */   public void setDevotionPoints(int devotionPoints) {
/*  728 */     this.bio.setDevotionPoints(devotionPoints);
/*      */   }
/*      */   
/*      */   public void setPhysique(float physique) {
/*  732 */     this.bio.setPhysique(physique);
/*      */   }
/*      */   
/*      */   public void setCunning(float cunning) {
/*  736 */     this.bio.setCunning(cunning);
/*      */   }
/*      */   
/*      */   public void setSpirit(int spirit) {
/*  740 */     this.bio.setSpirit(spirit);
/*      */   }
/*      */   
/*      */   public void setHealth(float health) {
/*  744 */     this.bio.setHealth(health);
/*      */   }
/*      */   
/*      */   public void setEnergy(float energy) {
/*  748 */     this.bio.setEnergy(energy);
/*      */   }
/*      */   
/*      */   public void setRespawnPoint(int difficulty, GDCharUID spawn) {
/*  752 */     this.respawns.setRespawnPoint(difficulty, spawn);
/*      */   }
/*      */   
/*      */   public void setRiftList(int difficulty, List<GDCharUID> addList, List<GDCharUID> removeList) {
/*  756 */     this.teleports.setRiftList(difficulty, addList, removeList);
/*      */   }
/*      */   
/*      */   public void setDiscoveredShrinesList(int difficulty, List<GDCharUID> addList, List<GDCharUID> removeList) {
/*  760 */     this.shrines.setDiscoveredShrinesList(difficulty, addList, removeList);
/*      */   }
/*      */   
/*      */   public void setRestoredShrinesList(int difficulty, List<GDCharUID> addList, List<GDCharUID> removeList) {
/*  764 */     this.shrines.setRestoredShrinesList(difficulty, addList, removeList);
/*      */     
/*  766 */     int shrinesRestored = 0;
/*  767 */     shrinesRestored += this.shrines.getDiscoveredShrinesList(0).size();
/*  768 */     shrinesRestored += this.shrines.getDiscoveredShrinesList(1).size();
/*  769 */     shrinesRestored += this.shrines.getDiscoveredShrinesList(2).size();
/*      */     
/*  771 */     this.stats.setShrinesRestored(shrinesRestored);
/*      */   }
/*      */   
/*      */   public void setSkillReclaimPoints(int skillReclamationPointsUsed) {
/*  775 */     this.skills.setSkillReclaimPoints(skillReclamationPointsUsed);
/*      */   }
/*      */   
/*      */   public void setDevotionReclaimPoints(int devotionReclamationPointsUsed) {
/*  779 */     this.skills.setDevotionReclaimPoints(devotionReclamationPointsUsed);
/*      */   }
/*      */   
/*      */   public void setMasterySkills(String masteryID, List<GDCharMasteryImagePane.Skill> list) {
/*  783 */     this.skills.setMasterySkills(masteryID, list);
/*      */   }
/*      */   
/*      */   public void setFactionReputations(int[] reputations) {
/*  787 */     this.factions.setFactionReputations(reputations);
/*      */   }
/*      */   
/*      */   public void setDeaths(int deaths) {
/*  791 */     this.stats.setDeaths(deaths);
/*      */     
/*  793 */     if (deaths == 0) {
/*  794 */       this.tutorials.removeTutorial(23);
/*      */     }
/*      */   }
/*      */   
/*      */   public void setSkillLevel(SkillInfo[] infos) {
/*  799 */     this.skills.setSkillLevel(infos);
/*      */   }
/*      */   
/*      */   public int refundMastery(String masteryID) {
/*  803 */     if (masteryID == null) return 0;
/*      */     
/*  805 */     int points = this.skills.refundMastery(masteryID);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  811 */     return points;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static GDCharSummary readCharSummary(File file) {
/*  819 */     String path = null;
/*      */     
/*      */     try {
/*  822 */       path = file.getCanonicalPath();
/*      */     }
/*  824 */     catch (IOException ex) {
/*  825 */       return null;
/*      */     } 
/*      */     
/*  828 */     GDChar gdc = new GDChar(file);
/*      */     
/*  830 */     gdc.readSummary(path);
/*      */     
/*  832 */     if (gdc.hasErrors()) return null;
/*      */     
/*  834 */     return new GDCharSummary(gdc.getCharHeader(), gdc.getCharInfo());
/*      */   }
/*      */   
/*      */   public static void readUIDList(List<GDCharUID> uidList) throws IOException {
/*  838 */     int len = GDReader.readEncInt(true);
/*      */     
/*  840 */     uidList.clear();
/*  841 */     for (int i = 0; i < len; i++) {
/*  842 */       GDCharUID uid = new GDCharUID();
/*  843 */       uid.read();
/*      */       
/*  845 */       uidList.add(uid);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void writeUIDList(List<GDCharUID> uidList) throws IOException {
/*  850 */     int len = 0;
/*      */     
/*  852 */     if (uidList != null) len = uidList.size();
/*      */     
/*  854 */     GDWriter.writeInt(len);
/*      */     
/*  856 */     if (uidList == null)
/*      */       return; 
/*  858 */     for (GDCharUID uid : uidList) {
/*  859 */       uid.write();
/*      */     }
/*      */   }
/*      */   
/*      */   private void readSummary(String path) {
/*  864 */     int val = 0;
/*      */     try {
/*  866 */       GDReader.readEncFileToBuffer(this.file);
/*      */       
/*  868 */       this.key = GDReader.readKey();
/*      */       
/*  870 */       val = GDReader.readEncInt(true);
/*  871 */       if (val != 1480803399) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*      */       
/*  873 */       this.header.read();
/*      */       
/*  875 */       val = GDReader.readEncInt(false);
/*  876 */       if (val != 0) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*      */       
/*  878 */       this.version = GDReader.readEncInt(true);
/*  879 */       if (this.version != 6 && this.version != 7 && this.version != 8)
/*      */       {
/*      */         
/*  882 */         throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*      */       }
/*      */       
/*  885 */       this.uid.read();
/*      */       
/*  887 */       this.info.read();
/*      */     }
/*  889 */     catch (FileNotFoundException ex) {
/*  890 */       if (path != null) {
/*  891 */         Object[] args = { path };
/*  892 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*      */         
/*  894 */         GDMsgLogger.addError(msg);
/*      */       } 
/*  896 */       GDMsgLogger.addError(ex);
/*      */       
/*  898 */       this.charError = true;
/*      */     }
/*  900 */     catch (FileVersionException ex) {
/*  901 */       if (path != null) {
/*  902 */         Object[] args = { path };
/*  903 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*      */         
/*  905 */         GDMsgLogger.addError(msg);
/*      */       } 
/*  907 */       GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "INFO_UPDATE_GDSTASH"));
/*  908 */       GDMsgLogger.addError((Throwable)ex);
/*      */       
/*  910 */       this.charError = true;
/*      */     }
/*  912 */     catch (Exception ex) {
/*  913 */       if (path != null) {
/*  914 */         Object[] args = { path };
/*  915 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*      */         
/*  917 */         GDMsgLogger.addError(msg);
/*      */       } 
/*  919 */       GDMsgLogger.addError(ex);
/*      */       
/*  921 */       this.charError = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void listSkills() {
/*  926 */     for (GDCharSkill sk : this.skills.getCharSkillList()) {
/*  927 */       String s = sk.getSkillName() + " : " + Integer.toString(sk.getSkillLevel());
/*      */       
/*  929 */       if (sk.isEnabled()) {
/*  930 */         s = s + " [enabled]";
/*      */       } else {
/*  932 */         s = s + " [not enabled]";
/*      */       } 
/*      */       
/*  935 */       System.out.println(s);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void read() {
/*  940 */     String path = null;
/*      */     
/*  942 */     int val = 0;
/*      */     try {
/*  944 */       path = this.file.getCanonicalPath();
/*      */       
/*  946 */       readSummary(path);
/*      */       
/*  948 */       if (this.charError) {
/*      */         return;
/*      */       }
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
/*  970 */       this.bio.read();
/*  971 */       this.inventory.read();
/*  972 */       this.stash.read();
/*  973 */       this.respawns.read();
/*  974 */       this.teleports.read();
/*      */       
/*  976 */       this.markers.read();
/*  977 */       this.shrines.read();
/*      */       
/*  979 */       this.skills.read();
/*  980 */       this.notes.read();
/*  981 */       this.factions.read();
/*  982 */       this.ui.read();
/*  983 */       this.tutorials.read();
/*  984 */       this.stats.read();
/*  985 */       if (this.version >= 7) {
/*  986 */         this.crucible.read();
/*      */       }
/*      */     }
/*  989 */     catch (FileNotFoundException ex) {
/*  990 */       if (path != null) {
/*  991 */         Object[] args = { path };
/*  992 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*      */         
/*  994 */         GDMsgLogger.addError(msg);
/*      */       } 
/*  996 */       GDMsgLogger.addError(ex);
/*      */       
/*  998 */       this.charError = true;
/*      */     }
/* 1000 */     catch (FileVersionException ex) {
/* 1001 */       if (path != null) {
/* 1002 */         Object[] args = { path };
/* 1003 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*      */         
/* 1005 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 1007 */       GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "INFO_UPDATE_GDSTASH"));
/* 1008 */       GDMsgLogger.addError((Throwable)ex);
/*      */       
/* 1010 */       this.charError = true;
/*      */     }
/* 1012 */     catch (Exception ex) {
/* 1013 */       if (path != null) {
/* 1014 */         Object[] args = { path };
/* 1015 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*      */         
/* 1017 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 1019 */       GDMsgLogger.addError(ex);
/*      */       
/* 1021 */       this.charError = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getByteSize() {
/* 1027 */     int size = 0;
/*      */     
/* 1029 */     size += 4;
/* 1030 */     size += 4;
/* 1031 */     size += this.header.getByteSize();
/* 1032 */     size += 4;
/* 1033 */     size += 4;
/* 1034 */     size += GDCharUID.getByteSize();
/* 1035 */     size += this.info.getByteSize();
/* 1036 */     size += this.bio.getByteSize();
/* 1037 */     size += this.inventory.getByteSize();
/* 1038 */     size += this.stash.getByteSize();
/* 1039 */     size += this.respawns.getByteSize();
/* 1040 */     size += this.teleports.getByteSize();
/* 1041 */     size += this.markers.getByteSize();
/* 1042 */     size += this.shrines.getByteSize();
/* 1043 */     size += this.skills.getByteSize();
/* 1044 */     size += this.notes.getByteSize();
/* 1045 */     size += this.factions.getByteSize();
/* 1046 */     size += this.ui.getByteSize();
/* 1047 */     size += this.tutorials.getByteSize();
/* 1048 */     size += this.stats.getByteSize();
/* 1049 */     if (this.version >= 7) {
/* 1050 */       size += this.crucible.getByteSize();
/*      */     }
/*      */     
/* 1053 */     return size;
/*      */   }
/*      */   
/*      */   public void createBuffer() throws IOException {
/* 1057 */     GDWriter.reserveBuffer(this);
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
/* 1074 */     GDWriter.writeInt(1431655765);
/* 1075 */     GDWriter.writeInt(1480803399);
/* 1076 */     this.header.write();
/* 1077 */     GDWriter.writeInt(0);
/* 1078 */     GDWriter.writeInt(this.version);
/* 1079 */     this.uid.write();
/* 1080 */     this.info.write();
/* 1081 */     this.bio.write();
/* 1082 */     this.inventory.write();
/* 1083 */     this.stash.write();
/* 1084 */     this.respawns.write();
/* 1085 */     this.teleports.write();
/* 1086 */     this.markers.write();
/* 1087 */     this.shrines.write();
/* 1088 */     this.skills.write();
/* 1089 */     this.notes.write();
/* 1090 */     this.factions.write();
/* 1091 */     this.ui.write();
/* 1092 */     this.tutorials.write();
/* 1093 */     this.stats.write();
/* 1094 */     if (this.version >= 7) {
/* 1095 */       this.crucible.write();
/*      */     }
/*      */   }
/*      */   
/*      */   public void write() throws IOException {
/* 1100 */     createBuffer();
/*      */     
/* 1102 */     String path = this.file.getParent() + GDConstants.FILE_SEPARATOR;
/*      */     
/* 1104 */     String tname = "player.g";
/*      */     
/* 1106 */     File temp = new File(path + "temp_tmp.tmp");
/*      */     
/* 1108 */     temp.createNewFile();
/*      */     
/* 1110 */     GDWriter.writeBuffer(temp);
/*      */     
/* 1112 */     File fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(0) }));
/* 1113 */     File fNew = null; int i;
/* 1114 */     for (i = 9; i >= 0; i--) {
/* 1115 */       fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(i) }));
/* 1116 */       fNew = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(i + 1) }));
/*      */       
/* 1118 */       if (fCurr.exists()) {
/* 1119 */         if (i == 9) {
/* 1120 */           fCurr.delete();
/*      */         } else {
/* 1122 */           fCurr.renameTo(fNew);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1127 */     this.file.renameTo(fCurr);
/* 1128 */     temp.renameTo(this.file);
/*      */   }
/*      */   
/*      */   public void write2() throws IOException {
/* 1132 */     String path = this.file.getParent() + GDConstants.FILE_SEPARATOR;
/*      */     
/* 1134 */     String tname = "player.g";
/*      */ 
/*      */     
/* 1137 */     File fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(0) }));
/* 1138 */     File fNew = null; int i;
/* 1139 */     for (i = 9; i >= 0; i--) {
/* 1140 */       fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(i) }));
/* 1141 */       fNew = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(i + 1) }));
/*      */       
/* 1143 */       if (fCurr.exists()) {
/* 1144 */         if (i == 9) {
/* 1145 */           fCurr.delete();
/*      */         } else {
/* 1147 */           fCurr.renameTo(fNew);
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1153 */     GDWriter.copyFile(this.file, fCurr);
/*      */ 
/*      */     
/*      */     try {
/* 1157 */       createBuffer();
/*      */       
/* 1159 */       GDWriter.writeBuffer(this.file);
/*      */     }
/* 1161 */     catch (IOException ex) {
/*      */       
/* 1163 */       GDWriter.copyFile(fCurr, this.file);
/*      */       
/* 1165 */       throw ex;
/*      */     } 
/*      */   } }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\character\GDChar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */