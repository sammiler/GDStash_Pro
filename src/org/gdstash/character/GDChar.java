/*      */ package org.gdstash.character;

import org.gdstash.db.*;
import org.gdstash.file.GDFileSize;
import org.gdstash.file.GDReader;
import org.gdstash.file.GDWriter;
import org.gdstash.item.GDAbstractContainer;
import org.gdstash.item.GDItem;
import org.gdstash.ui.character.GDCharFactionPane;
import org.gdstash.util.FileVersionException;
import org.gdstash.util.GDConstants;
import org.gdstash.util.GDMsgFormatter;
import org.gdstash.util.GDMsgLogger;
import org.gdstash.ui.character.GDCharMasteryImagePane;
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
/*  332 */     if (this.stash == null) {
/*  333 */       List<GDAbstractContainer> list = new LinkedList<>();
/*      */       
/*  335 */       return list;
/*      */     } 
/*      */     
/*  338 */     return this.stash.getStashPages();
/*      */   }
/*      */   
/*      */   public GDCharUID getRespawnPoint(int difficulty) {
/*  342 */     if (this.respawns == null) return null;
/*      */     
/*  344 */     return this.respawns.getRespawnPoint(difficulty);
/*      */   }
/*      */   
/*      */   public List<GDCharUID> getRiftList(int difficulty) {
/*  348 */     List<GDCharUID> list = new LinkedList<>();
/*      */     
/*  350 */     if (this.teleports == null) return list;
/*      */     
/*  352 */     return this.teleports.getRiftList(difficulty);
/*      */   }
/*      */   
/*      */   public List<GDCharUID> getDiscoveredShrinesList(int difficulty) {
/*  356 */     List<GDCharUID> list = new LinkedList<>();
/*      */     
/*  358 */     if (this.shrines == null) return list;
/*      */     
/*  360 */     return this.shrines.getDiscoveredShrinesList(difficulty);
/*      */   }
/*      */   
/*      */   public List<GDCharUID> getRestoredShrinesList(int difficulty) {
/*  364 */     List<GDCharUID> list = new LinkedList<>();
/*      */     
/*  366 */     if (this.shrines == null) return list;
/*      */     
/*  368 */     return this.shrines.getRestoredShrinesList(difficulty);
/*      */   }
/*      */   
/*      */   public int getSkillReclaimPoints() {
/*  372 */     if (this.skills == null) return 0;
/*      */     
/*  374 */     return this.skills.getSkillReclaimPoints();
/*      */   }
/*      */   
/*      */   public int getDevotionReclaimPoints() {
/*  378 */     if (this.skills == null) return 0;
/*      */     
/*  380 */     return this.skills.getDevotionReclaimPoints();
/*      */   }
/*      */   
/*      */   public int getSkillMaxLevel(String skillID) {
/*  384 */     if (this.skills == null) return 0;
/*      */     
/*  386 */     return this.skills.getSkillMaxLevel(skillID);
/*      */   }
/*      */   
/*      */   public int[] getFactionReputations() {
/*  390 */     int[] reputations = null;
/*      */     
/*  392 */     if (this.factions == null) {
/*  393 */       reputations = new int[GDCharFactionPane.FACTIONS.length];
/*      */       
/*  395 */       for (int i = 0; i < reputations.length; i++) {
/*  396 */         reputations[i] = 0;
/*      */       }
/*      */     } else {
/*  399 */       reputations = this.factions.getFactionReputations();
/*      */     } 
/*      */     
/*  402 */     return reputations;
/*      */   }
/*      */   
/*      */   public int getDeaths() {
/*  406 */     if (this.stats == null) return 0;
/*      */     
/*  408 */     return this.stats.getDeaths();
/*      */   }
/*      */   
/*      */   public String getPlayTimeAsDHM() {
/*  412 */     if (this.stats == null) return null;
/*      */     
/*  414 */     return this.stats.getPlayTimeAsDHM();
/*      */   }
/*      */   
/*      */   public int getGreatestDamage() {
/*  418 */     if (this.stats == null) return 0;
/*      */     
/*  420 */     return this.stats.getGreatestDamage();
/*      */   }
/*      */   
/*      */   public int getKillsMonster() {
/*  424 */     if (this.stats == null) return 0;
/*      */     
/*  426 */     return this.stats.getKillsMonster();
/*      */   }
/*      */   
/*      */   public int getKillsHero() {
/*  430 */     if (this.stats == null) return 0;
/*      */     
/*  432 */     return this.stats.getKillsHero();
/*      */   }
/*      */   
/*      */   public int getKillsNemesis() {
/*  436 */     if (this.stats == null) return 0;
/*      */     
/*  438 */     return this.stats.getKillsNemesis();
/*      */   }
/*      */   
/*      */   public int getLoreNotesCollected() {
/*  442 */     if (this.stats == null) return 0;
/*      */     
/*  444 */     return this.stats.getLoreNotesCollected();
/*      */   }
/*      */   
/*      */   public int getOneShotChestsOpened() {
/*  448 */     if (this.stats == null) return 0;
/*      */     
/*  450 */     return this.stats.getOneShotChestsOpened();
/*      */   }
/*      */   
/*      */   public int getItemsCrafted() {
/*  454 */     if (this.stats == null) return 0;
/*      */     
/*  456 */     return this.stats.getItemsCrafted();
/*      */   }
/*      */   
/*      */   public int getRelicsCrafted() {
/*  460 */     if (this.stats == null) return 0;
/*      */     
/*  462 */     return this.stats.getRelicsCrafted();
/*      */   }
/*      */   
/*      */   public int getRelicsTranscendentCrafted() {
/*  466 */     if (this.stats == null) return 0;
/*      */     
/*  468 */     return this.stats.getRelicsTranscendentCrafted();
/*      */   }
/*      */   
/*      */   public int getRelicsMythicalCrafted() {
/*  472 */     if (this.stats == null) return 0;
/*      */     
/*  474 */     return this.stats.getRelicsMythicalCrafted();
/*      */   }
/*      */   
/*      */   public boolean hasErrors() {
/*  478 */     return this.charError;
/*      */   }
/*      */   
/*      */   public boolean hasChanged() {
/*  482 */     boolean hasChanged = false;
/*      */     
/*  484 */     hasChanged = (hasChanged || this.header.hasChanged());
/*  485 */     hasChanged = (hasChanged || this.info.hasChanged());
/*  486 */     hasChanged = (hasChanged || this.bio.hasChanged());
/*  487 */     hasChanged = (hasChanged || this.inventory.hasChanged());
/*  488 */     hasChanged = (hasChanged || this.stash.hasChanged());
/*  489 */     hasChanged = (hasChanged || this.respawns.hasChanged());
/*  490 */     hasChanged = (hasChanged || this.teleports.hasChanged());
/*  491 */     hasChanged = (hasChanged || this.shrines.hasChanged());
/*  492 */     hasChanged = (hasChanged || this.skills.hasChanged());
/*  493 */     hasChanged = (hasChanged || this.factions.hasChanged());
/*  494 */     hasChanged = (hasChanged || this.stats.hasChanged());
/*      */     
/*  496 */     return hasChanged;
/*      */   }
/*      */   
/*      */   public GDCharEquippedContainer getEquipment() {
/*  500 */     GDCharEquippedContainer equipment = null;
/*      */     
/*  502 */     if (this.inventory != null) equipment = this.inventory.getEquipment();
/*      */     
/*  504 */     return equipment;
/*      */   }
/*      */   
/*      */   public GDCharInventorySack getInventory() {
/*  508 */     GDCharInventorySack sack = null;
/*      */     
/*  510 */     if (this.inventory != null) sack = this.inventory.getInventory();
/*      */     
/*  512 */     return sack;
/*      */   }
/*      */   
/*      */   public List<GDCharInventorySack> getBags() {
/*  516 */     List<GDCharInventorySack> list = new LinkedList<>();
/*      */     
/*  518 */     if (this.inventory != null) list = this.inventory.getBags();
/*      */     
/*  520 */     return list;
/*      */   }
/*      */   
/*      */   public GDCharStash getStash() {
/*  524 */     return this.stash;
/*      */   }
/*      */   
/*      */   public List<GDItem> getItems() {
/*  528 */     List<GDItem> list = new LinkedList<>();
/*      */     
/*  530 */     list.addAll(this.inventory.getItems());
/*  531 */     list.addAll(this.stash.getItemList());
/*      */     
/*  533 */     return list;
/*      */   }
/*      */   
/*      */   private List<GDCharSkill> getMasteries() {
/*  537 */     List<GDCharSkill> masteries = null;
/*      */     
/*  539 */     if (this.skills == null) {
/*  540 */       masteries = new LinkedList<>();
/*      */     } else {
/*  542 */       masteries = this.skills.getMasteries();
/*      */     } 
/*      */     
/*  545 */     return masteries;
/*      */   }
/*      */   
/*      */   public SkillInfo[] getMasteryInfo() {
/*  549 */     List<GDCharSkill> masteries = getMasteries();
/*      */     
/*  551 */     if (masteries.size() == 0) return null;
/*      */     
/*  553 */     SkillInfo[] infos = new SkillInfo[masteries.size()];
/*      */     
/*  555 */     int i = 0;
/*  556 */     for (GDCharSkill skill : masteries) {
/*  557 */       SkillInfo info = new SkillInfo(skill, this);
/*      */       
/*  559 */       infos[i] = info;
/*      */       
/*  561 */       i++;
/*      */     } 
/*      */     
/*  564 */     return infos;
/*      */   }
/*      */   
/*      */   public List<GDCharSkill> getCharSkillList() {
/*  568 */     List<GDCharSkill> list = null;
/*      */     
/*  570 */     if (this.skills == null) {
/*  571 */       list = new LinkedList<>();
/*      */     } else {
/*  573 */       list = this.skills.getCharSkillList();
/*      */     } 
/*      */     
/*  576 */     return list;
/*      */   }
/*      */   
/*      */   public List<GDCharSkill> getSkillsByMastery(String masteryID) {
/*  580 */     List<GDCharSkill> list = null;
/*      */     
/*  582 */     if (this.skills == null) {
/*  583 */       list = new LinkedList<>();
/*      */     } else {
/*  585 */       list = this.skills.getSkillsByMastery(masteryID);
/*      */     } 
/*      */     
/*  588 */     return list;
/*      */   }
/*      */   
/*      */   private List<GDCharSkill> getDevotions() {
/*  592 */     List<GDCharSkill> devotions = null;
/*      */     
/*  594 */     if (this.skills == null) {
/*  595 */       devotions = new LinkedList<>();
/*      */     } else {
/*  597 */       devotions = this.skills.getDevotions();
/*      */     } 
/*      */     
/*  600 */     return devotions;
/*      */   }
/*      */   
/*      */   public SkillInfo[] getDevotionInfo() {
/*  604 */     List<GDCharSkill> devotions = getDevotions();
/*      */     
/*  606 */     if (devotions.size() == 0) return null;
/*      */     
/*  608 */     SkillInfo[] infos = new SkillInfo[devotions.size()];
/*      */     
/*  610 */     int i = 0;
/*  611 */     for (GDCharSkill skill : devotions) {
/*  612 */       SkillInfo info = new SkillInfo(skill);
/*      */       
/*  614 */       infos[i] = info;
/*      */       
/*  616 */       i++;
/*      */     } 
/*      */     
/*  619 */     return infos;
/*      */   }
/*      */   
/*      */   private GDCharHeader getCharHeader() {
/*  623 */     return this.header;
/*      */   }
/*      */   
/*      */   private GDCharInfo getCharInfo() {
/*  627 */     return this.info;
/*      */   }
/*      */   
/*      */   public boolean isRoTCampaign() {
/*  631 */     boolean rot = false;
/*  632 */     for (GDCharUID uid : this.teleports.getRiftList(0)) {
/*  633 */       if (uid.equals(GDCharTeleportList.UID_RIFT_ROT_01_ROGUE_ENCAMPMENT)) {
/*  634 */         rot = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/*  640 */     return rot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFileDir(File fDir) {
/*      */     try {
/*  649 */       String name = this.file.getName();
/*  650 */       String dir = fDir.getCanonicalPath();
/*      */       
/*  652 */       dir = dir + GDConstants.FILE_SEPARATOR + name;
/*      */       
/*  654 */       File f = new File(dir);
/*      */       
/*  656 */       this.file = f;
/*      */     }
/*  658 */     catch (IOException ex) {
/*  659 */       GDMsgLogger.addError(ex);
/*      */     }
    /*      */   }
/*      */   
/*      */   public void setCharName(String charName) {
/*  664 */     this.header.setCharName(charName);
/*      */   }
/*      */   
/*      */   public void setSex(byte sex) {
/*  668 */     this.header.setSex(sex);
/*      */     
/*  670 */     if (sex == 1) {
/*  671 */       this.info.setTexture("creatures/pc/hero02.tex");
/*      */     } else {
/*  673 */       this.info.setTexture(null);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setHardcore(boolean hardcore) {
/*  678 */     this.header.setHardcore(hardcore);
/*      */   }
/*      */   
/*      */   public void setInMainQuest(boolean isInMainQuest) {
/*  682 */     this.info.setInMainQuest(isInMainQuest);
/*      */   }
/*      */   
/*      */   public void setGreatestCampaignDifficulty(byte greatestDifficulty) {
/*  686 */     this.info.setGreatestCampaignDifficulty(greatestDifficulty);
/*      */   }
/*      */   
/*      */   public void setGreatestCrucibleDifficulty(byte greatestDifficulty) {
/*  690 */     this.info.setGreatestCrucibleDifficulty(greatestDifficulty);
/*      */   }
/*      */   
/*      */   public void setMoney(int money) {
/*  694 */     this.info.setMoney(money);
/*      */   }
/*      */   
/*      */   public void setTributes(int tributes) {
/*  698 */     this.info.setTributes(tributes);
/*      */   }
/*      */   
/*      */   public void setLevel(int level) {
/*  702 */     this.header.setLevel(level);
/*  703 */     this.bio.setLevel(level);
/*  704 */     this.stats.setMaxLevel(level);
/*      */     
/*  706 */     int maxMasteries = 0;
/*  707 */     if (level > 1) maxMasteries = 1; 
/*  708 */     if (level > 9) maxMasteries = 2;
/*      */     
/*  710 */     this.skills.setMasteriesAllowed(maxMasteries);
/*      */   }
/*      */   
/*      */   public void setGreatestDamage(float damage) {
/*  714 */     this.stats.setGreatestDamage(damage);
/*      */   }
/*      */   
/*      */   public void setExperience(int experience) {
/*  718 */     this.bio.setExperience(experience);
/*      */   }
/*      */   
/*      */   public void setStatPoints(int modifierPoints) {
/*  722 */     this.bio.setStatPoints(modifierPoints);
/*      */   }
/*      */   
/*      */   public void setSkillPoints(int skillPoints) {
/*  726 */     this.bio.setSkillPoints(skillPoints);
/*      */   }
/*      */   
/*      */   public void setDevotionPoints(int devotionPoints) {
/*  730 */     this.bio.setDevotionPoints(devotionPoints);
/*      */   }
/*      */   
/*      */   public void setPhysique(float physique) {
/*  734 */     this.bio.setPhysique(physique);
/*      */   }
/*      */   
/*      */   public void setCunning(float cunning) {
/*  738 */     this.bio.setCunning(cunning);
/*      */   }
/*      */   
/*      */   public void setSpirit(int spirit) {
/*  742 */     this.bio.setSpirit(spirit);
/*      */   }
/*      */   
/*      */   public void setHealth(float health) {
/*  746 */     this.bio.setHealth(health);
/*      */   }
/*      */   
/*      */   public void setEnergy(float energy) {
/*  750 */     this.bio.setEnergy(energy);
/*      */   }
/*      */   
/*      */   public void setRespawnPoint(int difficulty, GDCharUID spawn) {
/*  754 */     this.respawns.setRespawnPoint(difficulty, spawn);
/*      */   }
/*      */   
/*      */   public void setRiftList(int difficulty, List<GDCharUID> addList, List<GDCharUID> removeList) {
/*  758 */     this.teleports.setRiftList(difficulty, addList, removeList);
/*      */   }
/*      */   
/*      */   public void setDiscoveredShrinesList(int difficulty, List<GDCharUID> addList, List<GDCharUID> removeList) {
/*  762 */     this.shrines.setDiscoveredShrinesList(difficulty, addList, removeList);
/*      */   }
/*      */   
/*      */   public void setRestoredShrinesList(int difficulty, List<GDCharUID> addList, List<GDCharUID> removeList) {
/*  766 */     this.shrines.setRestoredShrinesList(difficulty, addList, removeList);
/*      */     
/*  768 */     int shrinesRestored = 0;
/*  769 */     shrinesRestored += this.shrines.getDiscoveredShrinesList(0).size();
/*  770 */     shrinesRestored += this.shrines.getDiscoveredShrinesList(1).size();
/*  771 */     shrinesRestored += this.shrines.getDiscoveredShrinesList(2).size();
/*      */     
/*  773 */     this.stats.setShrinesRestored(shrinesRestored);
/*      */   }
/*      */   
/*      */   public void setSkillReclaimPoints(int skillReclamationPointsUsed) {
/*  777 */     this.skills.setSkillReclaimPoints(skillReclamationPointsUsed);
/*      */   }
/*      */   
/*      */   public void setDevotionReclaimPoints(int devotionReclamationPointsUsed) {
/*  781 */     this.skills.setDevotionReclaimPoints(devotionReclamationPointsUsed);
/*      */   }
/*      */   
/*      */   public void setMasterySkills(String masteryID, List<GDCharMasteryImagePane.Skill> list) {
/*  785 */     this.skills.setMasterySkills(masteryID, list);
/*      */   }
/*      */   
/*      */   public void setFactionReputations(int[] reputations) {
/*  789 */     this.factions.setFactionReputations(reputations);
/*      */   }
/*      */   
/*      */   public void setDeaths(int deaths) {
/*  793 */     this.stats.setDeaths(deaths);
/*      */     
/*  795 */     if (deaths == 0) {
/*  796 */       this.tutorials.removeTutorial(23);
/*      */     }
/*      */   }
/*      */   
/*      */   public void setSkillLevel(SkillInfo[] infos) {
/*  801 */     this.skills.setSkillLevel(infos);
/*      */   }
/*      */   
/*      */   public int refundMastery(String masteryID) {
/*  805 */     if (masteryID == null) return 0;
/*      */     
/*  807 */     int points = this.skills.refundMastery(masteryID);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  813 */     return points;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static GDCharSummary readCharSummary(File file) {
/*  821 */     String path = null;
/*      */     
/*      */     try {
/*  824 */       path = file.getCanonicalPath();
/*      */     }
/*  826 */     catch (IOException ex) {
/*  827 */       return null;
/*      */     } 
/*      */     
/*  830 */     GDChar gdc = new GDChar(file);
/*      */     
/*  832 */     gdc.readSummary(path);
/*      */     
/*  834 */     if (gdc.hasErrors()) return null;
/*      */     
/*  836 */     return new GDCharSummary(gdc.getCharHeader(), gdc.getCharInfo());
/*      */   }
/*      */   
/*      */   public static void readUIDList(List<GDCharUID> uidList) throws IOException {
/*  840 */     int len = GDReader.readEncInt(true);
/*      */     
/*  842 */     uidList.clear();
/*  843 */     for (int i = 0; i < len; i++) {
/*  844 */       GDCharUID uid = new GDCharUID();
/*  845 */       uid.read();
/*      */       
/*  847 */       uidList.add(uid);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void writeUIDList(List<GDCharUID> uidList) throws IOException {
/*  852 */     int len = 0;
/*      */     
/*  854 */     if (uidList != null) len = uidList.size();
/*      */     
/*  856 */     GDWriter.writeInt(len);
/*      */     
/*  858 */     if (uidList == null)
/*      */       return; 
/*  860 */     for (GDCharUID uid : uidList) {
/*  861 */       uid.write();
/*      */     }
/*      */   }
/*      */   
/*      */   private void readSummary(String path) {
/*  866 */     int val = 0;
/*      */     try {
/*  868 */       GDReader.readEncFileToBuffer(this.file);
/*      */       
/*  870 */       this.key = GDReader.readKey();
/*      */       
/*  872 */       val = GDReader.readEncInt(true);
/*  873 */       if (val != 1480803399) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*      */       
/*  875 */       this.header.read();
/*      */       
/*  877 */       val = GDReader.readEncInt(false);
/*  878 */       if (val != 0) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*      */       
/*  880 */       this.version = GDReader.readEncInt(true);
/*  881 */       if (this.version != 6 && this.version != 7 && this.version != 8)
/*      */       {
/*      */         
/*  884 */         throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*      */       }
/*      */       
/*  887 */       this.uid.read();
/*      */       
/*  889 */       this.info.read();
/*      */     }
/*  891 */     catch (FileNotFoundException ex) {
/*  892 */       if (path != null) {
/*  893 */         Object[] args = { path };
/*  894 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*      */         
/*  896 */         GDMsgLogger.addError(msg);
/*      */       } 
/*  898 */       GDMsgLogger.addError(ex);
/*      */       
/*  900 */       this.charError = true;
/*      */     }
/*  902 */     catch (FileVersionException ex) {
/*  903 */       if (path != null) {
/*  904 */         Object[] args = { path };
/*  905 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*      */         
/*  907 */         GDMsgLogger.addError(msg);
/*      */       } 
/*  909 */       GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "INFO_UPDATE_GDSTASH"));
/*  910 */       GDMsgLogger.addError((Throwable)ex);
/*      */       
/*  912 */       this.charError = true;
/*      */     }
/*  914 */     catch (Exception ex) {
/*  915 */       if (path != null) {
/*  916 */         Object[] args = { path };
/*  917 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*      */         
/*  919 */         GDMsgLogger.addError(msg);
/*      */       } 
/*  921 */       GDMsgLogger.addError(ex);
/*      */       
/*  923 */       this.charError = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void listSkills() {
/*  928 */     for (GDCharSkill sk : this.skills.getCharSkillList()) {
/*  929 */       String s = sk.getSkillName() + " : " + Integer.toString(sk.getSkillLevel());
/*      */       
/*  931 */       if (sk.isEnabled()) {
/*  932 */         s = s + " [enabled]";
/*      */       } else {
/*  934 */         s = s + " [not enabled]";
/*      */       } 
/*      */       
/*  937 */       System.out.println(s);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void read() {
/*  942 */     String path = null;
/*      */     
/*  944 */     int val = 0;
/*      */     try {
/*  946 */       path = this.file.getCanonicalPath();
/*      */       
/*  948 */       readSummary(path);
/*      */       
/*  950 */       if (this.charError) {
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
/*  972 */       this.bio.read();
/*  973 */       this.inventory.read();
/*  974 */       this.stash.read();
/*  975 */       this.respawns.read();
/*  976 */       this.teleports.read();
/*      */       
/*  978 */       this.markers.read();
/*  979 */       this.shrines.read();
/*      */       
/*  981 */       this.skills.read();
/*  982 */       this.notes.read();
/*  983 */       this.factions.read();
/*  984 */       this.ui.read();
/*  985 */       this.tutorials.read();
/*  986 */       this.stats.read();
/*  987 */       if (this.version >= 7) {
/*  988 */         this.crucible.read();
/*      */       }
/*      */     }
/*  991 */     catch (FileNotFoundException ex) {
/*  992 */       if (path != null) {
/*  993 */         Object[] args = { path };
/*  994 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*      */         
/*  996 */         GDMsgLogger.addError(msg);
/*      */       } 
/*  998 */       GDMsgLogger.addError(ex);
/*      */       
/* 1000 */       this.charError = true;
/*      */     }
/* 1002 */     catch (FileVersionException ex) {
/* 1003 */       if (path != null) {
/* 1004 */         Object[] args = { path };
/* 1005 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*      */         
/* 1007 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 1009 */       GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "INFO_UPDATE_GDSTASH"));
/* 1010 */       GDMsgLogger.addError((Throwable)ex);
/*      */       
/* 1012 */       this.charError = true;
/*      */     }
/* 1014 */     catch (Exception ex) {
/* 1015 */       if (path != null) {
/* 1016 */         Object[] args = { path };
/* 1017 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*      */         
/* 1019 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 1021 */       GDMsgLogger.addError(ex);
/*      */       
/* 1023 */       this.charError = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getByteSize() {
/* 1029 */     int size = 0;
/*      */     
/* 1031 */     size += 4;
/* 1032 */     size += 4;
/* 1033 */     size += this.header.getByteSize();
/* 1034 */     size += 4;
/* 1035 */     size += 4;
/* 1036 */     size += GDCharUID.getByteSize();
/* 1037 */     size += this.info.getByteSize();
/* 1038 */     size += this.bio.getByteSize();
/* 1039 */     size += this.inventory.getByteSize();
/* 1040 */     size += this.stash.getByteSize();
/* 1041 */     size += this.respawns.getByteSize();
/* 1042 */     size += this.teleports.getByteSize();
/* 1043 */     size += this.markers.getByteSize();
/* 1044 */     size += this.shrines.getByteSize();
/* 1045 */     size += this.skills.getByteSize();
/* 1046 */     size += this.notes.getByteSize();
/* 1047 */     size += this.factions.getByteSize();
/* 1048 */     size += this.ui.getByteSize();
/* 1049 */     size += this.tutorials.getByteSize();
/* 1050 */     size += this.stats.getByteSize();
/* 1051 */     if (this.version >= 7) {
/* 1052 */       size += this.crucible.getByteSize();
/*      */     }
/*      */     
/* 1055 */     return size;
/*      */   }
/*      */   
/*      */   public void createBuffer() throws IOException {
/* 1059 */     GDWriter.reserveBuffer(this);
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
/* 1076 */     GDWriter.writeInt(1431655765);
/* 1077 */     GDWriter.writeInt(1480803399);
/* 1078 */     this.header.write();
/* 1079 */     GDWriter.writeInt(0);
/* 1080 */     GDWriter.writeInt(this.version);
/* 1081 */     this.uid.write();
/* 1082 */     this.info.write();
/* 1083 */     this.bio.write();
/* 1084 */     this.inventory.write();
/* 1085 */     this.stash.write();
/* 1086 */     this.respawns.write();
/* 1087 */     this.teleports.write();
/* 1088 */     this.markers.write();
/* 1089 */     this.shrines.write();
/* 1090 */     this.skills.write();
/* 1091 */     this.notes.write();
/* 1092 */     this.factions.write();
/* 1093 */     this.ui.write();
/* 1094 */     this.tutorials.write();
/* 1095 */     this.stats.write();
/* 1096 */     if (this.version >= 7) {
/* 1097 */       this.crucible.write();
/*      */     }
/*      */   }
/*      */   
/*      */   public void write() throws IOException {
/* 1102 */     createBuffer();
/*      */     
/* 1104 */     String path = this.file.getParent() + GDConstants.FILE_SEPARATOR;
/*      */     
/* 1106 */     String tname = "player.g";
/*      */     
/* 1108 */     File temp = new File(path + "temp_tmp.tmp");
/*      */     
/* 1110 */     temp.createNewFile();
/*      */     
/* 1112 */     GDWriter.writeBuffer(temp);
/*      */     
/* 1114 */     File fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(0) }));
/* 1115 */     File fNew = null; int i;
/* 1116 */     for (i = 9; i >= 0; i--) {
/* 1117 */       fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(i) }));
/* 1118 */       fNew = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(i + 1) }));
/*      */       
/* 1120 */       if (fCurr.exists()) {
/* 1121 */         if (i == 9) {
/* 1122 */           fCurr.delete();
/*      */         } else {
/* 1124 */           fCurr.renameTo(fNew);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1129 */     this.file.renameTo(fCurr);
/* 1130 */     temp.renameTo(this.file);
/*      */   }
/*      */   
/*      */   public void write2() throws IOException {
/* 1134 */     String path = this.file.getParent() + GDConstants.FILE_SEPARATOR;
/*      */     
/* 1136 */     String tname = "player.g";
/*      */ 
/*      */     
/* 1139 */     File fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(0) }));
/* 1140 */     File fNew = null; int i;
/* 1141 */     for (i = 9; i >= 0; i--) {
/* 1142 */       fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(i) }));
/* 1143 */       fNew = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(i + 1) }));
/*      */       
/* 1145 */       if (fCurr.exists()) {
/* 1146 */         if (i == 9) {
/* 1147 */           fCurr.delete();
/*      */         } else {
/* 1149 */           fCurr.renameTo(fNew);
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1155 */     GDWriter.copyFile(this.file, fCurr);
/*      */ 
/*      */     
/*      */     try {
/* 1159 */       createBuffer();
/*      */       
/* 1161 */       GDWriter.writeBuffer(this.file);
/*      */     }
/* 1163 */     catch (IOException ex) {
/*      */       
/* 1165 */       GDWriter.copyFile(fCurr, this.file);
/*      */       
/* 1167 */       throw ex;
/*      */     } 
/*      */   } }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\character\GDChar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */