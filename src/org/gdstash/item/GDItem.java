/*      */ package org.gdstash.item;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.ImageObserver;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.nio.charset.Charset;
/*      */ import java.util.ArrayList;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.UIManager;
/*      */ import org.gdstash.db.DBAffix;
/*      */ import org.gdstash.db.DBAffixSet;
/*      */ import org.gdstash.db.DBItem;
/*      */ import org.gdstash.db.DBLootTable;
/*      */ import org.gdstash.db.DBLootTableAffixSetAlloc;
/*      */ import org.gdstash.db.DBSkill;
/*      */ import org.gdstash.db.DBSkillBonus;
/*      */ import org.gdstash.db.DBSkillModifier;
/*      */ import org.gdstash.db.DBStashItem;
/*      */ import org.gdstash.db.DBStat;
/*      */ import org.gdstash.db.ItemClass;
/*      */ import org.gdstash.db.ItemSlots;
/*      */ import org.gdstash.description.DetailComposer;
/*      */ import org.gdstash.description.TagInfo;
/*      */ import org.gdstash.description.TagInfoHashMap;
/*      */ import org.gdstash.file.GDReader;
/*      */ import org.gdstash.file.GDWriter;
/*      */ import org.gdstash.util.GDColor;
/*      */ import org.gdstash.util.GDLog;
/*      */ import org.gdstash.util.GDMsgFormatter;
/*      */ import org.gdstash.util.GDMsgLogger;
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
/*      */ public class GDItem
/*      */   implements Cloneable, Comparable<GDItem>
/*      */ {
/*   55 */   private static Random random = new Random();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class LabelInfo
/*      */   {
/*   64 */     public Color foreground = GDColor.COLOR_FG_COMMON;
/*   65 */     public Color background = GDColor.COLOR_BG_COMMON;
/*   66 */     public String text = "";
/*      */   }
/*      */ 
/*      */   
/*   70 */   private static final byte[] BEGINBLOCK = new byte[] { -50, -6, 29, -80 };
/*   71 */   private static final byte[] ENDBLOCK = new byte[] { -34, -64, -83, -34 };
/*      */ 
/*      */   
/*   74 */   public static final Color COLOR_OVERLAY_COMMON = new Color(204, 204, 204);
/*   75 */   public static final Color COLOR_OVERLAY_MAGICAL = new Color(229, 204, 0);
/*   76 */   public static final Color COLOR_OVERLAY_RARE = new Color(102, 217, 0);
/*   77 */   public static final Color COLOR_OVERLAY_EPIC = new Color(76, 115, 217);
/*   78 */   public static final Color COLOR_OVERLAY_LEGENDARY = new Color(76, 38, 166);
/*      */   
/*   80 */   public static final Color COLOR_OVERLAY_ARTIFACT = new Color(0, 255, 255);
/*   81 */   public static final Color COLOR_OVERLAY_COMPONENT = new Color(255, 178, 51);
/*   82 */   public static final Color COLOR_OVERLAY_ENCHANTMENT = new Color(145, 203, 0);
/*   83 */   public static final Color COLOR_OVERLAY_QUEST = new Color(204, 51, 229);
/*   84 */   public static final Color COLOR_OVERLAY_LORE = new Color(194, 176, 196);
/*      */   
/*      */   public static final int CONTAINER_UNDEF = 0;
/*      */   
/*      */   public static final int CONTAINER_STASH_SHARED = 1;
/*      */   
/*      */   public static final int CONTAINER_STASH_CRAFTING = 2;
/*      */   
/*      */   public static final int CONTAINER_STASH_CHAR = 3;
/*      */   
/*      */   public static final int CONTAINER_INVENTORY_CHAR = 4;
/*      */   
/*      */   public static final int CONTAINER_BAG_CHAR = 5;
/*      */   
/*      */   public static final int CONTAINER_EQUIPPED_CHAR = 6;
/*      */   
/*      */   private InputStream reader;
/*      */   
/*      */   private byte[] beginBlock;
/*      */   
/*      */   private DBStashItem stashItem;
/*      */   private byte[] endBlock;
/*      */   private float xPos;
/*      */   private int x;
/*      */   private float yPos;
/*      */   private int y;
/*      */   private byte attached;
/*      */   private DBItem dbItem;
/*      */   private DBAffix dbPrefix;
/*      */   private DBAffix dbSuffix;
/*      */   private DBAffix dbModifier;
/*      */   private DBItem dbComponent;
/*      */   private DBAffix dbBonus;
/*      */   private DBItem dbEnchantment;
/*      */   private List<DBStat> rngStats;
/*      */   private int reqDex;
/*      */   private int reqInt;
/*      */   private int reqStr;
/*      */   private boolean error;
/*      */   private String filename;
/*      */   private int containerType;
/*      */   
/*      */   public GDItem(String charName, boolean hardcore, int containerType) {
/*  127 */     this.containerType = containerType;
/*  128 */     this.error = false;
/*      */     
/*  130 */     this.stashItem = new DBStashItem(charName, hardcore);
/*      */   }
/*      */   
/*      */   public GDItem(DBItem item) {
/*  134 */     this.containerType = 0;
/*  135 */     this.error = false;
/*      */     
/*  137 */     this.dbItem = item;
/*  138 */     this.stashItem = new DBStashItem(item);
/*      */     
/*  140 */     init();
/*      */     
/*  142 */     this.error = GDMsgLogger.errorsInLog();
/*      */     
/*  144 */     if (!this.error) {
/*  145 */       loadData(0);
/*      */     }
/*      */   }
/*      */   
/*      */   public GDItem(DBStashItem item) {
/*  150 */     this(item, null);
/*      */   }
/*      */   
/*      */   public GDItem(DBStashItem item, String filename) {
/*  154 */     this.containerType = 0;
/*  155 */     this.error = false;
/*  156 */     this.filename = filename;
/*      */     
/*  158 */     this.stashItem = new DBStashItem(item);
/*      */     
/*  160 */     init();
/*      */     
/*  162 */     this.error = GDMsgLogger.errorsInLog();
/*      */     
/*  164 */     if (!this.error) {
/*  165 */       loadData(0);
/*      */     }
/*      */   }
/*      */   
/*      */   public GDItem(InputStream reader, int stashType, int version, boolean hardcore, String filename, int page, GDLog log) {
/*  170 */     this.containerType = 1;
/*  171 */     this.error = false;
/*  172 */     this.filename = filename;
/*      */     
/*  174 */     this.stashItem = new DBStashItem();
/*      */     
/*  176 */     this.reader = reader;
/*  177 */     this.stashItem.setHardcore(hardcore);
/*  178 */     this.stashItem.setCharName(null);
/*      */     
/*  180 */     GDLog tLog = new GDLog();
/*      */     
/*  182 */     if (stashType == 0) {
/*  183 */       readOldFormat(tLog);
/*      */     }
/*      */     
/*  186 */     if (stashType == 1) {
/*  187 */       readNewFormat(version, page, tLog);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  192 */     if (log == null) {
/*  193 */       GDMsgLogger.addLog(tLog);
/*      */     } else {
/*  195 */       log.addLog(tLog);
/*      */     } 
/*      */     
/*  198 */     if (!this.error) {
/*  199 */       loadData(page);
/*      */     }
/*      */   }
/*      */   
/*      */   private void init() {
/*  204 */     this.beginBlock = BEGINBLOCK;
/*  205 */     this.endBlock = ENDBLOCK;
/*      */   }
/*      */   
/*      */   private void loadData(int page) {
/*  209 */     if (this.stashItem == null)
/*      */       return; 
/*  211 */     if (this.stashItem.getItemID() != null) {
/*  212 */       this.dbItem = this.stashItem.getDBItem();
/*      */       
/*  214 */       if (this.dbItem == null) {
/*  215 */         Object[] args = { this.filename, Integer.valueOf(page), this.stashItem.getItemID() };
/*  216 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_ITEM_NOT_FOUND", args);
/*      */         
/*  218 */         GDMsgLogger.addWarning(msg);
/*      */         
/*  220 */         this.error = true;
/*      */       } 
/*      */     } 
/*      */     
/*  224 */     if (this.stashItem.getStackCount() == 0)
/*      */     {
/*  226 */       this.stashItem.setStackCount(1);
/*      */     }
/*      */     
/*  229 */     this.dbPrefix = this.stashItem.getDBPrefix();
/*  230 */     this.dbSuffix = this.stashItem.getDBSuffix();
/*  231 */     this.dbModifier = this.stashItem.getDBModifier();
/*  232 */     this.dbComponent = this.stashItem.getDBComponent();
/*  233 */     this.dbBonus = this.stashItem.getDBCompletionBonus();
/*  234 */     this.dbEnchantment = this.stashItem.getDBAugment();
/*      */ 
/*      */     
/*  237 */     this.reqDex = this.stashItem.getRequiredCunning();
/*  238 */     this.reqInt = this.stashItem.getRequiredSpirit();
/*  239 */     this.reqStr = this.stashItem.getRequiredPhysique();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void generateRandStats() {
/*  245 */     List<DBStat> list = null;
/*      */     
/*  247 */     if (this.dbItem == null || 
/*  248 */       this.dbItem.getStatList() != null);
/*      */ 
/*      */ 
/*      */     
/*  252 */     if (this.dbPrefix == null || 
/*  253 */       this.dbPrefix.getStatList() != null);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  258 */     if (this.dbSuffix != null && 
/*  259 */       this.dbSuffix.getStatList() != null) {
/*  260 */       list = cloneStatList(this.dbSuffix.getStatList());
/*  261 */       applyRNG(list, this.dbSuffix.getJitterPercent());
/*      */     } 
/*      */ 
/*      */     
/*  265 */     System.out.println();
/*      */   }
/*      */   
/*      */   private void applyRNG(List<DBStat> list, int jitterPercent) {
/*  269 */     int min = 100 - jitterPercent;
/*  270 */     int max = 100 + jitterPercent;
/*      */     
/*  272 */     GDRandomUniform rand = new GDRandomUniform(this.stashItem.getItemSeed());
/*      */     
/*  274 */     rand.generateInt(min, max); rand.generateInt(min, max);
/*      */     
/*  276 */     for (DBStat stat : list) {
/*  277 */       TagInfo info = TagInfoHashMap.getInfo(stat.getStatType());
/*      */       
/*  279 */       if (info == null || 
/*  280 */         !info.isJittering())
/*      */         continue; 
/*  282 */       float factor = rand.generateInt(min, max) / 100.0F;
/*  283 */       stat.setStatMin((int)(stat.getStatMin() * factor));
/*  284 */       stat.setStatMax((int)(stat.getStatMax() * factor));
/*  285 */       stat.setStatModifier((int)(stat.getStatModifier() * factor));
/*      */     } 
/*      */   }
/*      */   
/*      */   private List<DBStat> cloneStatList(List<DBStat> list) {
/*  290 */     if (list == null) return null;
/*      */     
/*  292 */     List<DBStat> cloneList = new LinkedList<>();
/*      */     
/*  294 */     for (DBStat stat : list) {
/*  295 */       DBStat cloneStat = stat.clone();
/*      */       
/*  297 */       cloneList.add(cloneStat);
/*      */     } 
/*      */     
/*  300 */     return cloneList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GDItem clone() {
/*  309 */     this.stashItem.fillDependentStats(null);
/*      */     
/*  311 */     GDItem item = new GDItem(this.stashItem);
/*      */     
/*  313 */     return item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(GDItem item) {
/*  321 */     int i = 0;
/*      */     
/*  323 */     if (item.dbItem != null) {
/*  324 */       if (this.dbItem != null) {
/*  325 */         i = this.dbItem.compareTo(item.dbItem);
/*      */         
/*  327 */         if (i != 0) return i; 
/*      */       } else {
/*  329 */         return -1;
/*      */       } 
/*      */     } else {
/*  332 */       if (this.dbItem != null) {
/*  333 */         return 1;
/*      */       }
/*  335 */       return 0;
/*      */     } 
/*      */ 
/*      */     
/*  339 */     int oAffix = 0;
/*  340 */     int iAffix = 0;
/*      */     
/*  342 */     if (this.dbPrefix != null) oAffix++; 
/*  343 */     if (this.dbSuffix != null) oAffix += 2;
/*      */     
/*  345 */     if (item.dbPrefix != null) iAffix++; 
/*  346 */     if (item.dbSuffix != null) iAffix += 2;
/*      */     
/*  348 */     if (iAffix != oAffix) {
/*  349 */       return oAffix - iAffix;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  355 */     if (item.dbModifier == null && this.dbModifier != null) return 1; 
/*  356 */     if (item.dbModifier != null && this.dbModifier == null) return -1;
/*      */ 
/*      */ 
/*      */     
/*  360 */     if (item.dbComponent == null && this.dbComponent != null) return 1; 
/*  361 */     if (item.dbComponent != null && this.dbComponent == null) return -1;
/*      */ 
/*      */ 
/*      */     
/*  365 */     if (item.dbEnchantment == null && this.dbEnchantment != null) return 1; 
/*  366 */     if (item.dbEnchantment != null && this.dbEnchantment == null) return -1;
/*      */ 
/*      */ 
/*      */     
/*  370 */     int oLevel = getRequiredLevel();
/*  371 */     int iLevel = item.getRequiredLevel();
/*      */     
/*  373 */     if (oLevel < iLevel) return -1; 
/*  374 */     if (oLevel > iLevel) return 1;
/*      */ 
/*      */ 
/*      */     
/*  378 */     if (item.dbPrefix != null)
/*  379 */     { if (this.dbPrefix != null) {
/*  380 */         i = this.dbPrefix.compareTo(item.dbPrefix);
/*      */         
/*  382 */         if (i != 0) return i; 
/*      */       } else {
/*  384 */         throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_PREFIX_BOTH"));
/*      */       }
/*      */        }
/*  387 */     else if (this.dbPrefix != null) { throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_PREFIX_NEITHER")); }
/*      */ 
/*      */     
/*  390 */     if (item.dbSuffix != null)
/*  391 */     { if (this.dbSuffix != null) {
/*  392 */         i = this.dbSuffix.compareTo(item.dbSuffix);
/*      */         
/*  394 */         if (i != 0) return i; 
/*      */       } else {
/*  396 */         throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_SUFFIX_BOTH"));
/*      */       }
/*      */        }
/*  399 */     else if (this.dbSuffix != null) { throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_SUFFIX_NEITHER")); }
/*      */ 
/*      */     
/*  402 */     if (item.dbModifier != null)
/*  403 */     { if (this.dbModifier != null) {
/*  404 */         i = this.dbModifier.compareTo(item.dbModifier);
/*      */         
/*  406 */         if (i != 0) return i; 
/*      */       } else {
/*  408 */         throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_MODIFIER_BOTH"));
/*      */       }
/*      */        }
/*  411 */     else if (this.dbModifier != null) { throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_MODIFIER_NEITHER")); }
/*      */ 
/*      */     
/*  414 */     if (item.dbComponent != null)
/*  415 */     { if (this.dbComponent != null) {
/*  416 */         i = this.dbComponent.compareTo(item.dbComponent);
/*      */         
/*  418 */         if (i != 0) return i;
/*      */         
/*  420 */         if (item.stashItem.getCompletionBonusID() != null)
/*  421 */         { if (this.stashItem.getCompletionBonusID() != null) {
/*  422 */             if (!item.stashItem.getCompletionBonusID().equals(this.stashItem.getCompletionBonusID())) {
/*  423 */               i = this.stashItem.getCompletionBonusID().compareTo(item.stashItem.getCompletionBonusID());
/*      */               
/*  425 */               if (i != 0) return i; 
/*      */             } 
/*      */           } else {
/*  428 */             return -1;
/*      */           }
/*      */            }
/*  431 */         else if (this.stashItem.getCompletionBonusID() != null) { return 1; }
/*      */       
/*      */       } else {
/*  434 */         throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_COMPONENT_BOTH"));
/*      */       }
/*      */        }
/*  437 */     else if (this.dbComponent != null) { throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_COMPONENT_NEITHER")); }
/*      */ 
/*      */     
/*  440 */     if (item.dbEnchantment != null)
/*  441 */     { if (this.dbEnchantment != null) {
/*  442 */         i = this.dbEnchantment.compareTo(item.dbEnchantment);
/*      */         
/*  444 */         if (i != 0) return i; 
/*      */       } else {
/*  446 */         throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_ENCHANTMENT_BOTH"));
/*      */       }
/*      */        }
/*  449 */     else if (this.dbEnchantment != null) { throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_ENCHANTMENTENT_NEITHER")); }
/*      */ 
/*      */     
/*  452 */     if (this.stashItem.getStashID() < item.stashItem.getStashID()) return -1; 
/*  453 */     if (this.stashItem.getStashID() > item.stashItem.getStashID()) return 1;
/*      */     
/*  455 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasErrors() {
/*  463 */     return this.error;
/*      */   }
/*      */   
/*      */   public int getContainerType() {
/*  467 */     return this.containerType;
/*      */   }
/*      */   
/*      */   public DBItem getDBItem() {
/*  471 */     return this.dbItem;
/*      */   }
/*      */   
/*      */   public DBStashItem getDBStashItem() {
/*  475 */     return this.stashItem;
/*      */   }
/*      */   
/*      */   public DBItem getDBComponent() {
/*  479 */     return this.dbComponent;
/*      */   }
/*      */   
/*      */   public DBAffixSet getBonusAffixSet() {
/*  483 */     if (this.dbItem == null) return null;
/*      */     
/*  485 */     return this.dbItem.getBonusAffixSet();
/*      */   }
/*      */   
/*      */   public DBStashItem getStashItem() {
/*  489 */     return this.stashItem;
/*      */   }
/*      */   
/*      */   public String getItemID() {
/*  493 */     if (this.stashItem == null) return null;
/*      */     
/*  495 */     return this.stashItem.getItemID();
/*      */   }
/*      */   
/*      */   public String getItemClass() {
/*  499 */     if (this.dbItem == null) return null;
/*      */     
/*  501 */     return this.dbItem.getItemClass();
/*      */   }
/*      */   
/*      */   public String getRarity() {
/*  505 */     if (this.stashItem != null) return this.stashItem.getRarity(); 
/*  506 */     if (this.dbItem != null) return this.dbItem.getRarity();
/*      */     
/*  508 */     return "Common";
/*      */   }
/*      */   
/*      */   public String getBaseName() {
/*  512 */     if (this.dbItem == null) return null;
/*      */     
/*  514 */     return this.dbItem.getName(true);
/*      */   }
/*      */   
/*      */   public String getShortItemName() {
/*  518 */     if (this.dbItem == null) return null;
/*      */     
/*  520 */     return this.dbItem.getName(false);
/*      */   }
/*      */   
/*      */   public String getLongItemName() {
/*  524 */     if (this.dbItem == null) return null;
/*      */     
/*  526 */     return this.dbItem.getFullName();
/*      */   }
/*      */   
/*      */   public String getComponentName() {
/*  530 */     if (this.dbComponent == null) return null;
/*      */     
/*  532 */     String s = this.dbComponent.getName(false);
/*      */     
/*  534 */     if (this.stashItem.getVar1() < this.dbComponent.getComponentPieces()) {
/*  535 */       s = s + " (" + Integer.toString(this.stashItem.getVar1() + 1) + "/" + Integer.toString(this.dbComponent.getComponentPieces()) + ")";
/*      */     }
/*      */     
/*  538 */     return s;
/*      */   }
/*      */   
/*      */   public String getEnchantmentName() {
/*  542 */     if (this.dbEnchantment == null) return null;
/*      */     
/*  544 */     return this.dbEnchantment.getName(true);
/*      */   }
/*      */   
/*      */   public List<DBStat> getStatList() {
/*  548 */     List<DBStat> listAll = new LinkedList<>();
/*  549 */     List<DBStat> list = null;
/*      */     
/*  551 */     if (this.dbItem != null) {
/*  552 */       list = this.dbItem.getStatList();
/*      */       
/*  554 */       if (list != null) listAll.addAll(list);
/*      */     
/*      */     } 
/*  557 */     if (this.dbPrefix != null) {
/*  558 */       list = this.dbPrefix.getStatList();
/*      */       
/*  560 */       if (list != null) listAll.addAll(list);
/*      */     
/*      */     } 
/*  563 */     if (this.dbSuffix != null) {
/*  564 */       list = this.dbSuffix.getStatList();
/*      */       
/*  566 */       if (list != null) listAll.addAll(list);
/*      */     
/*      */     } 
/*  569 */     return listAll;
/*      */   }
/*      */   
/*      */   public boolean hasPetBonus() {
/*  573 */     if (this.dbItem != null && 
/*  574 */       this.dbItem.getPetBonusSkill() != null) return true;
/*      */ 
/*      */     
/*  577 */     if (this.dbPrefix != null) {
/*  578 */       if (this.dbPrefix.getPetAffix() != null) return true; 
/*  579 */       if (this.dbPrefix.getPetSkill() != null) return true;
/*      */     
/*      */     } 
/*  582 */     if (this.dbSuffix != null) {
/*  583 */       if (this.dbSuffix.getPetAffix() != null) return true; 
/*  584 */       if (this.dbSuffix.getPetSkill() != null) return true;
/*      */     
/*      */     } 
/*  587 */     return false;
/*      */   }
/*      */   
/*      */   public boolean hasConvertIn(String convertIn) {
/*  591 */     if (this.dbItem != null && 
/*  592 */       convertIn.equals(this.dbItem.getConvertIn())) return true;
/*      */ 
/*      */     
/*  595 */     if (this.dbPrefix != null && 
/*  596 */       convertIn.equals(this.dbPrefix.getConvertIn())) return true;
/*      */ 
/*      */     
/*  599 */     if (this.dbSuffix != null && 
/*  600 */       convertIn.equals(this.dbSuffix.getConvertIn())) return true;
/*      */ 
/*      */     
/*  603 */     return false;
/*      */   }
/*      */   
/*      */   public boolean hasConvertOut(String convertOut) {
/*  607 */     if (this.dbItem != null && 
/*  608 */       convertOut.equals(this.dbItem.getConvertOut())) return true;
/*      */ 
/*      */     
/*  611 */     if (this.dbPrefix != null && 
/*  612 */       convertOut.equals(this.dbPrefix.getConvertOut())) return true;
/*      */ 
/*      */     
/*  615 */     if (this.dbSuffix != null && 
/*  616 */       convertOut.equals(this.dbSuffix.getConvertOut())) return true;
/*      */ 
/*      */     
/*  619 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isSoulbound() {
/*  623 */     if (this.stashItem == null) return false;
/*      */     
/*  625 */     return this.stashItem.isSoulbound();
/*      */   }
/*      */   
/*      */   public boolean isCraftable() {
/*  629 */     if (this.dbItem == null) return false;
/*      */     
/*  631 */     return this.dbItem.isCraftable();
/*      */   }
/*      */   
/*      */   public boolean isHardcore() {
/*  635 */     if (this.stashItem == null) return false;
/*      */     
/*  637 */     return this.stashItem.isHardcore();
/*      */   }
/*      */   
/*      */   public boolean isArmor() {
/*  641 */     if (this.dbItem == null) return false;
/*      */     
/*  643 */     return this.dbItem.isArmor();
/*      */   }
/*      */   
/*      */   public boolean isJewelry() {
/*  647 */     if (this.dbItem == null) return false;
/*      */     
/*  649 */     return this.dbItem.isJewelry();
/*      */   }
/*      */   
/*      */   public boolean isWeapon() {
/*  653 */     if (this.dbItem == null) return false;
/*      */     
/*  655 */     return this.dbItem.isWeapon();
/*      */   }
/*      */   
/*      */   public boolean isOffhand() {
/*  659 */     if (this.dbItem == null) return false;
/*      */     
/*  661 */     return this.dbItem.isOffhand();
/*      */   }
/*      */   
/*      */   public boolean is1HWeapon() {
/*  665 */     if (this.dbItem == null) return false;
/*      */     
/*  667 */     return this.dbItem.is1HWeapon();
/*      */   }
/*      */   
/*      */   public boolean is2HWeapon() {
/*  671 */     if (this.dbItem == null) return false;
/*      */     
/*  673 */     return this.dbItem.is2HWeapon();
/*      */   }
/*      */   
/*      */   public boolean isArtifact() {
/*  677 */     if (this.dbItem == null) return false;
/*      */     
/*  679 */     return this.dbItem.isArtifact();
/*      */   }
/*      */   
/*      */   public boolean isComponent() {
/*  683 */     if (this.dbItem == null) return false;
/*      */     
/*  685 */     return this.dbItem.isComponent();
/*      */   }
/*      */   
/*      */   public boolean isEnchantment() {
/*  689 */     if (this.dbItem == null) return false;
/*      */     
/*  691 */     return this.dbItem.isEnchantment();
/*      */   }
/*      */   
/*      */   public boolean isNote() {
/*  695 */     if (this.dbItem == null) return false;
/*      */     
/*  697 */     return this.dbItem.isNote();
/*      */   }
/*      */   
/*      */   public boolean isFactionBooster() {
/*  701 */     if (this.dbItem == null) return false;
/*      */     
/*  703 */     return this.dbItem.isFactionBooster();
/*      */   }
/*      */   
/*      */   public boolean isElixir() {
/*  707 */     if (this.dbItem == null) return false;
/*      */     
/*  709 */     return this.dbItem.isElixir();
/*      */   }
/*      */   
/*      */   public boolean isPotion() {
/*  713 */     if (this.dbItem == null) return false;
/*      */     
/*  715 */     return this.dbItem.isPotion();
/*      */   }
/*      */   
/*      */   public boolean isQuestItem() {
/*  719 */     if (this.dbItem == null) return false;
/*      */     
/*  721 */     return this.dbItem.isQuestItem();
/*      */   }
/*      */   
/*      */   public boolean isStackable() {
/*  725 */     if (this.stashItem == null) return false;
/*      */     
/*  727 */     return this.stashItem.isStackable();
/*      */   }
/*      */   
/*      */   public boolean isEpic() {
/*  731 */     if (this.stashItem == null) return false;
/*      */     
/*  733 */     return this.stashItem.isEpic();
/*      */   }
/*      */   
/*      */   public boolean isLegendary() {
/*  737 */     if (this.stashItem == null) return false;
/*      */     
/*  739 */     return this.stashItem.isLegendary();
/*      */   }
/*      */   
/*      */   public boolean isUnique() {
/*  743 */     if (this.stashItem == null) return false;
/*      */     
/*  745 */     return this.stashItem.isUnique();
/*      */   }
/*      */   
/*      */   public int getDefaultStackSize() {
/*  749 */     if (this.stashItem == null) return 1;
/*      */     
/*  751 */     return this.stashItem.getDefaultStackSize();
/*      */   }
/*      */   
/*      */   public String getPrefixID() {
/*  755 */     if (this.dbPrefix == null) return null;
/*      */     
/*  757 */     return this.dbPrefix.getAffixID();
/*      */   }
/*      */   
/*      */   public DBAffix getPrefix() {
/*  761 */     return this.dbPrefix;
/*      */   }
/*      */   
/*      */   public String getSuffixID() {
/*  765 */     if (this.dbSuffix == null) return null;
/*      */     
/*  767 */     return this.dbSuffix.getAffixID();
/*      */   }
/*      */   
/*      */   public DBAffix getSuffix() {
/*  771 */     return this.dbSuffix;
/*      */   }
/*      */   
/*      */   public DBAffix getModifier() {
/*  775 */     return this.dbModifier;
/*      */   }
/*      */   
/*      */   public DBAffix getCompletionBonus() {
/*  779 */     return this.dbBonus;
/*      */   }
/*      */   
/*      */   public DBItem getAugment() {
/*  783 */     return this.dbEnchantment;
/*      */   }
/*      */   
/*      */   public String getSeedHex() {
/*  787 */     if (this.stashItem == null) return null;
/*      */ 
/*      */     
/*  790 */     byte[] seed = GDWriter.intToBytes4(this.stashItem.getItemSeed());
/*      */ 
/*      */     
/*  793 */     String s = "";
/*  794 */     for (int i = 0; i < seed.length; i++) {
/*  795 */       byte val = seed[seed.length - 1 - i];
/*      */       
/*  797 */       s = s + byteToHex(val);
/*      */     } 
/*      */     
/*  800 */     return s;
/*      */   }
/*      */   
/*      */   public int getVar1() {
/*  804 */     if (this.stashItem == null) return 0;
/*      */     
/*  806 */     return this.stashItem.getVar1();
/*      */   }
/*      */   
/*      */   public String getVar1Str() {
/*  810 */     if (this.stashItem == null) return null;
/*      */     
/*  812 */     int i = getVar1();
/*      */     
/*  814 */     if (i == 0) i = 1;
/*      */     
/*  816 */     return Integer.toString(i);
/*      */   }
/*      */   
/*      */   public int getStackCount() {
/*  820 */     if (this.stashItem == null) return 0;
/*      */     
/*  822 */     return this.stashItem.getStackCount();
/*      */   }
/*      */   
/*      */   public String getStackCountStr() {
/*  826 */     if (this.stashItem == null) return null;
/*      */     
/*  828 */     return Integer.toString(getStackCount());
/*      */   }
/*      */   
/*      */   public int getX() {
/*  832 */     return this.x;
/*      */   }
/*      */   
/*      */   public int getY() {
/*  836 */     return this.y;
/*      */   }
/*      */   
/*      */   public String getCharName() {
/*  840 */     if (this.stashItem == null) return null;
/*      */     
/*  842 */     return this.stashItem.getCharName();
/*      */   }
/*      */   
/*      */   public int getStashID() {
/*  846 */     if (this.stashItem == null) return -1;
/*      */     
/*  848 */     return this.stashItem.getStashID();
/*      */   }
/*      */   
/*      */   public int getItemLevel() {
/*  852 */     if (this.stashItem == null) return 0;
/*      */     
/*  854 */     return this.stashItem.getItemLevel();
/*      */   }
/*      */   
/*      */   public int getItemSkillLevel() {
/*  858 */     if (this.stashItem == null) return 1;
/*      */     
/*  860 */     return this.stashItem.getItemSkillLevel();
/*      */   }
/*      */   
/*      */   public int getRequiredLevel() {
/*  864 */     if (this.stashItem == null) return 0;
/*      */     
/*  866 */     return this.stashItem.getRequiredlevel();
/*      */   }
/*      */   
/*      */   public int getRequiredCunning() {
/*  870 */     return this.reqDex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRequiredPhysique() {
/*  878 */     return this.reqStr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRequiredSpirit() {
/*  886 */     return this.reqInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getOverlayColor() {
/*  894 */     if (this.dbItem == null) return null;
/*      */     
/*  896 */     Color color = COLOR_OVERLAY_COMMON;
/*      */     
/*  898 */     String rarity = getRarity();
/*      */     
/*  900 */     if (rarity == null) return color;
/*      */     
/*  902 */     if (rarity.equals("Magical")) color = COLOR_OVERLAY_MAGICAL; 
/*  903 */     if (rarity.equals("Rare")) color = COLOR_OVERLAY_RARE; 
/*  904 */     if (rarity.equals("Epic")) color = COLOR_OVERLAY_EPIC; 
/*  905 */     if (rarity.equals("Legendary")) color = COLOR_OVERLAY_LEGENDARY;
/*      */     
/*  907 */     if (this.dbItem.isArtifact()) color = COLOR_OVERLAY_ARTIFACT; 
/*  908 */     if (this.dbItem.isComponent()) color = COLOR_OVERLAY_COMPONENT; 
/*  909 */     if (this.dbItem.isEnchantment()) color = COLOR_OVERLAY_ENCHANTMENT; 
/*  910 */     if (this.dbItem.isQuestItem()) color = COLOR_OVERLAY_QUEST;
/*      */     
/*  912 */     return color;
/*      */   }
/*      */   
/*      */   public BufferedImage getFullImage() {
/*  916 */     if (this.dbItem == null) return null;
/*      */     
/*  918 */     BufferedImage img = null;
/*      */     
/*  920 */     if (this.dbItem.getComponentPieces() > 0 && getVar1() < this.dbItem.getComponentPieces()) {
/*  921 */       img = this.dbItem.getShardImage();
/*      */     } else {
/*  923 */       img = this.dbItem.getImage();
/*      */     } 
/*      */     
/*  926 */     if (img == null) return null;
/*      */ 
/*      */     
/*  929 */     BufferedImage clone = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
/*  930 */     Graphics2D g = clone.createGraphics();
/*      */ 
/*      */     
/*  933 */     g.drawImage(img, 0, 0, (ImageObserver)null);
/*  934 */     img = clone;
/*      */     
/*  936 */     BufferedImage overlay = getOverlayImage();
/*      */     
/*  938 */     if (overlay != null) {
/*  939 */       int w = img.getWidth();
/*  940 */       int h = img.getHeight();
/*  941 */       int wo = overlay.getWidth();
/*  942 */       int ho = overlay.getHeight();
/*      */       
/*  944 */       g.drawImage(overlay, w - wo - 2, h - ho - 2, (ImageObserver)null);
/*      */     } 
/*      */     
/*  947 */     if (this.dbItem.getComponentPieces() > 0 && getVar1() < this.dbItem.getComponentPieces()) {
/*  948 */       String s = getVar1Str();
/*      */       
/*  950 */       Font font = UIManager.getDefaults().getFont("Label.font");
/*  951 */       font = font.deriveFont(1, 10.0F);
/*      */       
/*  953 */       g.setFont(font);
/*      */       
/*  955 */       int h = g.getFontMetrics().getAscent();
/*      */       
/*  957 */       int xPos = 4;
/*  958 */       int yPos = h + 2;
/*      */       
/*  960 */       g.setPaint(Color.BLACK);
/*  961 */       g.drawString(s, xPos - 1, yPos - 1);
/*  962 */       g.setPaint(Color.BLACK);
/*  963 */       g.drawString(s, xPos - 1, yPos + 1);
/*  964 */       g.setPaint(Color.BLACK);
/*  965 */       g.drawString(s, xPos + 1, yPos - 1);
/*  966 */       g.setPaint(Color.BLACK);
/*  967 */       g.drawString(s, xPos + 1, yPos + 1);
/*      */       
/*  969 */       g.setPaint(Color.WHITE);
/*  970 */       g.drawString(s, xPos, yPos);
/*      */     } 
/*      */     
/*  973 */     if (getStackCount() > 1) {
/*  974 */       String s = getStackCountStr();
/*      */       
/*  976 */       Font font = UIManager.getDefaults().getFont("Label.font");
/*  977 */       font = font.deriveFont(1, 10.0F);
/*      */       
/*  979 */       g.setFont(font);
/*  980 */       int w = g.getFontMetrics().stringWidth(s);
/*  981 */       int h = g.getFontMetrics().getDescent();
/*      */       
/*  983 */       int xPos = img.getWidth() - w - 4;
/*  984 */       int yPos = img.getHeight() - h - 2;
/*      */       
/*  986 */       g.setPaint(Color.BLACK);
/*  987 */       g.drawString(s, xPos - 1, yPos - 1);
/*  988 */       g.setPaint(Color.BLACK);
/*  989 */       g.drawString(s, xPos - 1, yPos + 1);
/*  990 */       g.setPaint(Color.BLACK);
/*  991 */       g.drawString(s, xPos + 1, yPos - 1);
/*  992 */       g.setPaint(Color.BLACK);
/*  993 */       g.drawString(s, xPos + 1, yPos + 1);
/*      */       
/*  995 */       g.setPaint(Color.WHITE);
/*  996 */       g.drawString(s, xPos, yPos);
/*      */     } 
/*      */     
/*  999 */     return img;
/*      */   }
/*      */   
/*      */   public BufferedImage getImage() {
/* 1003 */     if (this.dbItem == null) return null;
/*      */     
/* 1005 */     return this.dbItem.getImage();
/*      */   }
/*      */   
/*      */   public BufferedImage getOverlayImage() {
/* 1009 */     if (this.dbComponent == null) return null;
/*      */     
/* 1011 */     BufferedImage img = null;
/*      */     
/* 1013 */     if (this.dbComponent.getComponentPieces() > 0 && getVar1() < this.dbComponent.getComponentPieces()) {
/* 1014 */       img = this.dbComponent.getOverlayShard();
/*      */     } else {
/* 1016 */       img = this.dbComponent.getOverlayImage();
/*      */     } 
/*      */     
/* 1019 */     return img;
/*      */   }
/*      */   
/*      */   public ImageIcon getImageIcon() {
/* 1023 */     ImageIcon icon = null;
/* 1024 */     BufferedImage image = getFullImage();
/*      */     
/* 1026 */     if (image != null) icon = new ImageIcon(image);
/*      */     
/* 1028 */     return icon;
/*      */   }
/*      */   
/*      */   public int getPlusAllSkills() {
/* 1032 */     if (this.dbItem == null) return 0;
/*      */     
/* 1034 */     return this.dbItem.getPlusAllSkills();
/*      */   }
/*      */   
/*      */   public String getMainDamageType() {
/* 1038 */     if (this.dbItem == null) return null;
/*      */     
/* 1040 */     return this.dbItem.getMainDamageType();
/*      */   }
/*      */   
/*      */   public String getFullDescription() {
/* 1044 */     if (this.dbItem == null) return null;
/*      */     
/* 1046 */     ItemInfo info = compactBonuses();
/*      */     
/* 1048 */     return DetailComposer.getItemText(this, info);
/*      */   }
/*      */   
/*      */   public String getItemDescription() {
/* 1052 */     if (this.dbItem == null) return null;
/*      */     
/* 1054 */     return this.dbItem.getItemDescription();
/*      */   }
/*      */   
/*      */   private String getSlots() {
/* 1058 */     if (this.dbItem == null) return ""; 
/* 1059 */     if (!this.dbItem.usesSlots()) return "";
/*      */     
/* 1061 */     ItemSlots slots = this.dbItem.getSlots();
/*      */     
/* 1063 */     String allSlots = null;
/* 1064 */     String singleSlot = null;
/*      */     
/* 1066 */     if (slots.usesAllArmor()) {
/* 1067 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_ALL", null);
/*      */       
/* 1069 */       if (allSlots == null) {
/* 1070 */         allSlots = singleSlot;
/*      */       } else {
/* 1072 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */       
/* 1075 */       slots.clearAllArmor();
/*      */     } 
/*      */     
/* 1078 */     if (slots.usesAllWeapons()) {
/* 1079 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_ALL", null);
/*      */       
/* 1081 */       if (allSlots == null) {
/* 1082 */         allSlots = singleSlot;
/*      */       } else {
/* 1084 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */       
/* 1087 */       slots.clearAllWeapons();
/*      */     } 
/*      */     
/* 1090 */     if (slots.usesMeleeWeapons()) {
/* 1091 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_MELEE", null);
/*      */       
/* 1093 */       if (allSlots == null) {
/* 1094 */         allSlots = singleSlot;
/*      */       } else {
/* 1096 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */       
/* 1099 */       slots.clearMeleeWeapons();
/*      */     } 
/*      */     
/* 1102 */     if (slots.uses1HWeapons()) {
/* 1103 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_ONE_HAND", null);
/*      */       
/* 1105 */       if (allSlots == null) {
/* 1106 */         allSlots = singleSlot;
/*      */       } else {
/* 1108 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */       
/* 1111 */       slots.clear1HWeapons();
/*      */     } 
/*      */     
/* 1114 */     if (slots.uses2HWeapons()) {
/* 1115 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_TWO_HAND", null);
/*      */       
/* 1117 */       if (allSlots == null) {
/* 1118 */         allSlots = singleSlot;
/*      */       } else {
/* 1120 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */       
/* 1123 */       slots.clear2HWeapons();
/*      */     } 
/*      */     
/* 1126 */     if (slots.usesRangedWeapons()) {
/* 1127 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_RANGED", null);
/*      */       
/* 1129 */       if (allSlots == null) {
/* 1130 */         allSlots = singleSlot;
/*      */       } else {
/* 1132 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */       
/* 1135 */       slots.clearRangedWeapons();
/*      */     } 
/*      */     
/* 1138 */     if (slots.slotAxe1H) {
/* 1139 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_AXE", null);
/*      */       
/* 1141 */       if (allSlots == null) {
/* 1142 */         allSlots = singleSlot;
/*      */       } else {
/* 1144 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1148 */     if (slots.slotDagger1H) {
/* 1149 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_DAGGER", null);
/*      */       
/* 1151 */       if (allSlots == null) {
/* 1152 */         allSlots = singleSlot;
/*      */       } else {
/* 1154 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1158 */     if (slots.slotMace1H) {
/* 1159 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_MACE", null);
/*      */       
/* 1161 */       if (allSlots == null) {
/* 1162 */         allSlots = singleSlot;
/*      */       } else {
/* 1164 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1168 */     if (slots.slotScepter1H) {
/* 1169 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_SCEPTER", null);
/*      */       
/* 1171 */       if (allSlots == null) {
/* 1172 */         allSlots = singleSlot;
/*      */       } else {
/* 1174 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1178 */     if (slots.slotSpear1H) {
/* 1179 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_SPEAR", null);
/*      */       
/* 1181 */       if (allSlots == null) {
/* 1182 */         allSlots = singleSlot;
/*      */       } else {
/* 1184 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1188 */     if (slots.slotStaff2H) {
/* 1189 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_STAFF", null);
/*      */       
/* 1191 */       if (allSlots == null) {
/* 1192 */         allSlots = singleSlot;
/*      */       } else {
/* 1194 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1198 */     if (slots.slotSword1H) {
/* 1199 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_SWORD", null);
/*      */       
/* 1201 */       if (allSlots == null) {
/* 1202 */         allSlots = singleSlot;
/*      */       } else {
/* 1204 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1208 */     if (slots.slotShield) {
/* 1209 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_SHIELD", null);
/*      */       
/* 1211 */       if (allSlots == null) {
/* 1212 */         allSlots = singleSlot;
/*      */       } else {
/* 1214 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1218 */     if (slots.slotOffhand) {
/* 1219 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_OFFHAND", null);
/*      */       
/* 1221 */       if (allSlots == null) {
/* 1222 */         allSlots = singleSlot;
/*      */       } else {
/* 1224 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1228 */     if (slots.slotHead) {
/* 1229 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_HEAD", null);
/*      */       
/* 1231 */       if (allSlots == null) {
/* 1232 */         allSlots = singleSlot;
/*      */       } else {
/* 1234 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1238 */     if (slots.slotShoulders) {
/* 1239 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_SHOULDERS", null);
/*      */       
/* 1241 */       if (allSlots == null) {
/* 1242 */         allSlots = singleSlot;
/*      */       } else {
/* 1244 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1248 */     if (slots.slotChest) {
/* 1249 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_CHEST", null);
/*      */       
/* 1251 */       if (allSlots == null) {
/* 1252 */         allSlots = singleSlot;
/*      */       } else {
/* 1254 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1258 */     if (slots.slotHands) {
/* 1259 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_HANDS", null);
/*      */       
/* 1261 */       if (allSlots == null) {
/* 1262 */         allSlots = singleSlot;
/*      */       } else {
/* 1264 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1268 */     if (slots.slotLegs) {
/* 1269 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_LEGS", null);
/*      */       
/* 1271 */       if (allSlots == null) {
/* 1272 */         allSlots = singleSlot;
/*      */       } else {
/* 1274 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1278 */     if (slots.slotFeet) {
/* 1279 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_FEET", null);
/*      */       
/* 1281 */       if (allSlots == null) {
/* 1282 */         allSlots = singleSlot;
/*      */       } else {
/* 1284 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1288 */     if (slots.slotBelt) {
/* 1289 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_BELT", null);
/*      */       
/* 1291 */       if (allSlots == null) {
/* 1292 */         allSlots = singleSlot;
/*      */       } else {
/* 1294 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1298 */     if (slots.slotRing) {
/* 1299 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_RING", null);
/*      */       
/* 1301 */       if (allSlots == null) {
/* 1302 */         allSlots = singleSlot;
/*      */       } else {
/* 1304 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1308 */     if (slots.slotAmulet) {
/* 1309 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_AMULET", null);
/*      */       
/* 1311 */       if (allSlots == null) {
/* 1312 */         allSlots = singleSlot;
/*      */       } else {
/* 1314 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1318 */     if (slots.slotMedal) {
/* 1319 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_MEDAL", null);
/*      */       
/* 1321 */       if (allSlots == null) {
/* 1322 */         allSlots = singleSlot;
/*      */       } else {
/* 1324 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1328 */     if (allSlots == null) return "";
/*      */     
/* 1330 */     Object[] args = { allSlots };
/*      */     
/* 1332 */     allSlots = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_USED_IN", args);
/*      */     
/* 1334 */     allSlots = "<br>" + GDColor.HTML_COLOR_USED_SLOTS + allSlots + "</font>";
/*      */     
/* 1336 */     return allSlots;
/*      */   }
/*      */   
/*      */   public String getItemCategory() {
/* 1340 */     String tagRarity = determineRarityTag();
/* 1341 */     String tagCategory = determineCategoryTag();
/* 1342 */     String tagItemType = determineItemTypeTag();
/*      */     
/* 1344 */     if (tagItemType == null) return null;
/*      */     
/* 1346 */     if (tagItemType.equals("TYPE_ITEM_COMPONENT") && 
/* 1347 */       this.stashItem.getVar1() < this.dbItem.getComponentPieces()) {
/* 1348 */       tagItemType = "TYPE_ITEM_COMPONENT_PARTIAL";
/*      */     }
/*      */ 
/*      */     
/* 1352 */     String strRarity = null;
/* 1353 */     String strCategory = null;
/* 1354 */     String strItemType = GDMsgFormatter.format(GDMsgFormatter.rbGD, tagItemType, null);
/*      */     
/* 1356 */     if (tagCategory != null) {
/* 1357 */       strCategory = GDMsgFormatter.format(GDMsgFormatter.rbGD, tagCategory, null);
/*      */     }
/*      */     
/* 1360 */     if (tagRarity != null) {
/* 1361 */       strRarity = GDMsgFormatter.format(GDMsgFormatter.rbGD, tagRarity, null);
/*      */     }
/*      */     
/* 1364 */     if (strRarity != null && strCategory != null) {
/* 1365 */       Object[] args = { strRarity, strCategory, strItemType };
/* 1366 */       strItemType = GDMsgFormatter.format(GDMsgFormatter.rbGD, "COMB_RARITY_CATEGORY_ITEM", args);
/*      */     } 
/*      */     
/* 1369 */     if (strRarity == null && strCategory != null) {
/* 1370 */       Object[] args = { strCategory, strItemType };
/* 1371 */       strItemType = GDMsgFormatter.format(GDMsgFormatter.rbGD, "COMB_CATEGORY_ITEM", args);
/*      */     } 
/*      */     
/* 1374 */     if (strRarity != null && strCategory == null) {
/* 1375 */       Object[] args = { strRarity, strItemType };
/* 1376 */       strItemType = GDMsgFormatter.format(GDMsgFormatter.rbGD, "COMB_RARITY_ITEM", args);
/*      */     } 
/*      */     
/* 1379 */     if (isSoulbound()) {
/* 1380 */       Object[] args = { strItemType };
/* 1381 */       strItemType = GDMsgFormatter.format(GDMsgFormatter.rbGD, "COMB_SOULBOUND_ITEM", args);
/*      */     } 
/*      */     
/* 1384 */     if (getItemSetName() != null) {
/* 1385 */       strItemType = strItemType + "<br>" + "[" + getItemSetName() + "]";
/*      */     }
/*      */     
/* 1388 */     strItemType = GDColor.HTML_COLOR_ITEM_TYPE + strItemType + "</font>";
/*      */     
/* 1390 */     String slots = getSlots();
/*      */     
/* 1392 */     if (slots != null) strItemType = strItemType + slots;
/*      */     
/* 1394 */     return strItemType;
/*      */   }
/*      */ 
/*      */   
/*      */   private Color getBaseItemColor() {
/* 1399 */     Color color = GDColor.COLOR_FG_COMMON;
/*      */     
/* 1401 */     if (this.dbItem != null) {
/* 1402 */       String rarity = this.dbItem.getRarity();
/*      */       
/* 1404 */       if (rarity != null) {
/* 1405 */         color = ItemClass.getRarityColor(rarity);
/*      */       }
/*      */       
/* 1408 */       if (this.dbItem.isQuestItem()) color = GDColor.COLOR_FG_LEGENDARY;
/*      */     
/*      */     } 
/* 1411 */     return color;
/*      */   }
/*      */   
/*      */   private Color getRarityBackgroundColor() {
/* 1415 */     String rarity = getRarity();
/*      */     
/* 1417 */     Color color = GDColor.COLOR_BG_COMMON;
/*      */     
/* 1419 */     if (rarity != null) {
/* 1420 */       color = ItemClass.getRarityBackgroundColor(rarity);
/*      */     }
/*      */     
/* 1423 */     if (this.dbItem != null && 
/* 1424 */       this.dbItem.isQuestItem()) color = GDColor.COLOR_BG_LEGENDARY;
/*      */ 
/*      */     
/* 1427 */     return color;
/*      */   }
/*      */ 
/*      */   
/*      */   private Color getItemColor() {
/* 1432 */     Color color = GDColor.COLOR_FG_COMMON;
/*      */     
/* 1434 */     String rarity = getRarity();
/*      */     
/* 1436 */     if (rarity != null) {
/* 1437 */       color = ItemClass.getRarityColor(rarity);
/*      */     }
/*      */     
/* 1440 */     if (this.dbItem != null && 
/* 1441 */       this.dbItem.isQuestItem()) color = GDColor.COLOR_FG_LEGENDARY;
/*      */ 
/*      */     
/* 1444 */     return color;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValid(int affixCombi, boolean completionAll) {
/* 1452 */     if (this.dbItem == null) return false; 
/* 1453 */     if (affixCombi == 3) return true;
/*      */     
/* 1455 */     boolean validCombo = isValidAffixCombo(affixCombi);
/* 1456 */     boolean validComponent = isValidComponent();
/* 1457 */     boolean validEnchantment = isValidEnchantment();
/* 1458 */     boolean validBonus = isValidBonus(completionAll);
/*      */     
/* 1460 */     boolean valid = (validCombo && validComponent && validEnchantment && validBonus);
/*      */     
/* 1462 */     return valid;
/*      */   }
/*      */   
/*      */   private boolean isValidAffixCombo(int affixCombi) {
/* 1466 */     if (this.dbItem == null) return false; 
/* 1467 */     if (affixCombi == 3) return true; 
/* 1468 */     if (this.dbPrefix == null && this.dbSuffix == null) return true;
/*      */ 
/*      */     
/* 1471 */     List<DBLootTable> tables = DBLootTable.getByItemID(this.dbItem.getItemID());
/*      */     
/* 1473 */     if (tables == null) {
/* 1474 */       if (this.dbPrefix != null || this.dbSuffix != null) return false;
/*      */       
/* 1476 */       return true;
/*      */     } 
/*      */     
/* 1479 */     boolean validCombo = false;
/* 1480 */     for (DBLootTable table : tables) {
/*      */       
/* 1482 */       boolean foundPrefix = (this.dbPrefix == null);
/* 1483 */       int rarePrefix = 6;
/* 1484 */       boolean foundSuffix = (this.dbSuffix == null);
/* 1485 */       int rareSuffix = 6;
/*      */       
/* 1487 */       List<DBLootTableAffixSetAlloc> affixSets = table.getAffixSetAllocList();
/* 1488 */       if (affixSets != null) {
/* 1489 */         for (DBLootTableAffixSetAlloc alloc : affixSets) {
/* 1490 */           int type = alloc.getAffixType();
/* 1491 */           List<DBAffixSet.DBEntry> list = alloc.getAffixEntries();
/*      */           
/* 1493 */           if (list == null)
/*      */             continue; 
/* 1495 */           if (type == 1 || type == 3)
/*      */           {
/* 1497 */             if (this.dbPrefix != null) {
/* 1498 */               for (DBAffixSet.DBEntry entry : list) {
/* 1499 */                 if (entry.getAffixID().equals(this.dbPrefix.getAffixID())) {
/* 1500 */                   foundPrefix = true;
/*      */                   
/* 1502 */                   if (type == 1 && 
/* 1503 */                     rarePrefix > 3) rarePrefix = 3;
/*      */ 
/*      */                   
/* 1506 */                   if (type == 3 && 
/* 1507 */                     rarePrefix > 4) rarePrefix = 4;
/*      */ 
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           }
/*      */           
/* 1516 */           if (type == 2 || type == 4)
/*      */           {
/* 1518 */             if (this.dbSuffix != null) {
/* 1519 */               label93: for (DBAffixSet.DBEntry entry : list) {
/* 1520 */                 if (entry.getAffixID().equals(this.dbSuffix.getAffixID())) {
/* 1521 */                   foundSuffix = true;
/*      */                   
/* 1523 */                   if (type == 2) {
/* 1524 */                     if (rareSuffix > 3) { rareSuffix = 3;
/*      */                       break label93; }
/*      */                     
/* 1527 */                     if (type == 4 && 
/* 1528 */                       rareSuffix > 4) rareSuffix = 4;
/*      */                     
/*      */                     continue;
/*      */                   } 
/*      */                   break label93;
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           }
/*      */         } 
/* 1538 */         if (affixCombi == 2)
/*      */         {
/*      */           
/* 1541 */           if (foundPrefix && foundSuffix) validCombo = true;
/*      */         
/*      */         }
/* 1544 */         if (affixCombi == 1)
/*      */         {
/* 1546 */           if (this.dbPrefix != null && this.dbSuffix != null)
/*      */           
/* 1548 */           { if (foundPrefix && foundSuffix) {
/* 1549 */               if (rarePrefix == 3 && rareSuffix == 3)
/*      */               {
/* 1551 */                 validCombo = table.isMagicPrefixMagicSuffixAllowed();
/*      */               }
/*      */               
/* 1554 */               if (rarePrefix == 3 && rareSuffix == 4)
/*      */               {
/* 1556 */                 validCombo = table.isMagicPrefixRareSuffixAllowed();
/*      */               }
/*      */               
/* 1559 */               if (rarePrefix == 4 && rareSuffix == 3)
/*      */               {
/* 1561 */                 validCombo = table.isRarePrefixMagicSuffixAllowed();
/*      */               }
/*      */               
/* 1564 */               if (rarePrefix == 4 && rareSuffix == 4)
/*      */               {
/* 1566 */                 validCombo = table.isRarePrefixRareSuffixAllowed();
/*      */               
/*      */               }
/*      */             }
/*      */              }
/*      */           
/* 1572 */           else if (foundPrefix && foundSuffix) { validCombo = true; }
/*      */         
/*      */         }
/*      */       } 
/*      */       
/* 1577 */       if (validCombo)
/*      */         break; 
/*      */     } 
/* 1580 */     return validCombo;
/*      */   }
/*      */   
/*      */   private boolean isValidSlotItem(DBItem item) {
/* 1584 */     if (this.dbItem == null) return false; 
/* 1585 */     if (item == null) return true;
/*      */     
/* 1587 */     boolean valid = false;
/*      */     
/* 1589 */     String itemClass = this.dbItem.getItemClass();
/*      */     
/* 1591 */     if (itemClass.equals("ArmorProtective_Head")) valid = item.isSlotHead(); 
/* 1592 */     if (itemClass.equals("ArmorProtective_Shoulders")) valid = item.isSlotShoulders(); 
/* 1593 */     if (itemClass.equals("ArmorProtective_Chest")) valid = item.isSlotChest(); 
/* 1594 */     if (itemClass.equals("ArmorProtective_Hands")) valid = item.isSlotHands(); 
/* 1595 */     if (itemClass.equals("ArmorProtective_Waist")) valid = item.isSlotBelt(); 
/* 1596 */     if (itemClass.equals("ArmorProtective_Legs")) valid = item.isSlotLegs(); 
/* 1597 */     if (itemClass.equals("ArmorProtective_Feet")) valid = item.isSlotFeet(); 
/* 1598 */     if (itemClass.equals("ArmorJewelry_Amulet")) valid = item.isSlotAmulet(); 
/* 1599 */     if (itemClass.equals("ArmorJewelry_Medal")) valid = item.isSlotMedal(); 
/* 1600 */     if (itemClass.equals("ArmorJewelry_Ring")) valid = item.isSlotRing(); 
/* 1601 */     if (itemClass.equals("WeaponArmor_Offhand")) valid = item.isSlotOffhand(); 
/* 1602 */     if (itemClass.equals("WeaponArmor_Shield")) valid = item.isSlotShield(); 
/* 1603 */     if (itemClass.equals("WeaponMelee_Axe")) valid = item.isSlotAxe1H(); 
/* 1604 */     if (itemClass.equals("WeaponMelee_Mace")) valid = item.isSlotMace1H(); 
/* 1605 */     if (itemClass.equals("WeaponHunting_Spear")) valid = item.isSlotSpear1H(); 
/* 1606 */     if (itemClass.equals("WeaponMelee_Sword")) valid = item.isSlotSword1H(); 
/* 1607 */     if (itemClass.equals("WeaponMelee_Dagger")) valid = item.isSlotDagger1H(); 
/* 1608 */     if (itemClass.equals("WeaponMelee_Scepter")) valid = item.isSlotScepter1H(); 
/* 1609 */     if (itemClass.equals("WeaponHunting_Ranged1h")) valid = item.isSlotRanged1H(); 
/* 1610 */     if (itemClass.equals("WeaponMelee_Axe2h")) valid = item.isSlotAxe2H(); 
/* 1611 */     if (itemClass.equals("WeaponMelee_Mace2h")) valid = item.isSlotMace2H(); 
/* 1612 */     if (itemClass.equals("WeaponMagical_Staff")) valid = item.isSlotStaff2H(); 
/* 1613 */     if (itemClass.equals("WeaponMelee_Sword2h")) valid = item.isSlotSword2H(); 
/* 1614 */     if (itemClass.equals("WeaponHunting_Ranged2h")) valid = item.isSlotRanged2H();
/*      */     
/* 1616 */     return valid;
/*      */   }
/*      */   
/*      */   private boolean isValidComponent() {
/* 1620 */     return isValidSlotItem(this.dbComponent);
/*      */   }
/*      */   
/*      */   private boolean isValidEnchantment() {
/* 1624 */     return isValidSlotItem(this.dbEnchantment);
/*      */   }
/*      */   
/*      */   private boolean isValidBonus(boolean completionAll) {
/* 1628 */     if (this.dbItem == null) return false; 
/* 1629 */     if (this.dbBonus == null) return true; 
/* 1630 */     if (this.dbComponent == null && 
/* 1631 */       !isComponent() && !isArtifact()) return false;
/*      */     
/* 1633 */     if (completionAll) return true;
/*      */     
/* 1635 */     DBItem component = null;
/*      */     
/* 1637 */     if (isComponent() || isArtifact()) {
/* 1638 */       component = this.dbItem;
/*      */     } else {
/* 1640 */       component = this.dbComponent;
/*      */     } 
/* 1642 */     if (component.getBonusAffixSet() == null) return false;
/*      */     
/* 1644 */     List<DBAffixSet.DBEntry> entries = component.getBonusAffixSet().getAffixEntries();
/* 1645 */     if (entries == null) return false;
/*      */     
/* 1647 */     boolean found = false;
/* 1648 */     for (DBAffixSet.DBEntry entry : entries) {
/* 1649 */       if (entry.getAffixID().equals(this.dbBonus.getAffixID())) {
/* 1650 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1656 */     return found;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LabelInfo getItemNameInfo() {
/* 1664 */     if (this.dbItem == null) return null;
/*      */     
/* 1666 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1668 */     boolean brackets = false;
/*      */     
/* 1670 */     li.text = parseName();
/*      */     
/* 1672 */     if (this.dbItem.getRequiredlevel() != 0) {
/* 1673 */       li.text += " [" + this.dbItem.getRequiredlevel();
/*      */       
/* 1675 */       brackets = true;
/*      */     } 
/*      */     
/* 1678 */     String dmg = this.dbItem.getMainDamageType();
/*      */     
/* 1680 */     if (dmg != null) {
/* 1681 */       if (brackets) {
/* 1682 */         li.text += ", " + dmg;
/*      */       } else {
/* 1684 */         li.text += " [" + dmg;
/*      */       } 
/*      */       
/* 1687 */       brackets = true;
/*      */     } 
/*      */     
/* 1690 */     if (brackets) li.text += "]";
/*      */     
/* 1692 */     li.foreground = getBaseItemColor();
/*      */ 
/*      */     
/* 1695 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getCompleteNameInfo(boolean inclFaction, boolean inclMod) {
/* 1699 */     if (this.stashItem == null) return null;
/*      */     
/* 1701 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1703 */     li.foreground = getItemColor();
/* 1704 */     li.background = getRarityBackgroundColor();
/* 1705 */     li.text = this.stashItem.getCompleteName(inclFaction, inclMod);
/*      */     
/* 1707 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getPrefixInfo() {
/* 1711 */     if (this.dbPrefix == null) return null;
/*      */     
/* 1713 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1715 */     li.text = this.dbPrefix.getGenderText(this.dbItem.getGenderCode());
/* 1716 */     if (this.dbPrefix.getRequiredlevel() != 0) li.text += " [" + this.dbPrefix.getRequiredlevel() + "]";
/*      */     
/* 1718 */     String rarity = this.dbPrefix.getRarity();
/*      */     
/* 1720 */     if (rarity != null) {
/* 1721 */       if (rarity.equals("Magical")) li.foreground = GDColor.COLOR_FG_MAGICAL; 
/* 1722 */       if (rarity.equals("Rare")) li.foreground = GDColor.COLOR_FG_RARE;
/*      */     
/*      */     } 
/* 1725 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getSuffixInfo() {
/* 1729 */     if (this.dbSuffix == null) return null;
/*      */     
/* 1731 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1733 */     li.text = this.dbSuffix.getGenderText(this.dbItem.getGenderCode());
/* 1734 */     if (this.dbSuffix.getRequiredlevel() != 0) li.text += " [" + this.dbSuffix.getRequiredlevel() + "]";
/*      */     
/* 1736 */     String rarity = this.dbSuffix.getRarity();
/*      */     
/* 1738 */     if (rarity != null) {
/* 1739 */       if (rarity.equals("Magical")) li.foreground = GDColor.COLOR_FG_MAGICAL; 
/* 1740 */       if (rarity.equals("Rare")) li.foreground = GDColor.COLOR_FG_RARE;
/*      */     
/*      */     } 
/* 1743 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getModifierInfo() {
/* 1747 */     if (this.dbModifier == null) return null;
/*      */     
/* 1749 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1751 */     li.text = this.dbModifier.getGenderText(this.dbItem.getGenderCode());
/*      */     
/* 1753 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getComponentInfo() {
/* 1757 */     if (this.dbComponent == null) return null;
/*      */     
/* 1759 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1761 */     li.text = this.dbComponent.getName(false);
/* 1762 */     li.foreground = GDColor.COLOR_FG_COMPONENT;
/*      */     
/* 1764 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getComponentBonusInfo() {
/* 1768 */     if (this.dbBonus == null) return null;
/*      */     
/* 1770 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1772 */     DetailComposer compCompletion = DetailComposer.createComposer(this.stashItem, 1, this.dbBonus.getStatList(), this.dbBonus.getSkillBonusList());
/*      */     
/* 1774 */     String s = DetailComposer.getComposerBonuses(compCompletion, true, true, true);
/*      */     
/* 1776 */     if (s != null) s = "<html>" + s + "</html>";
/*      */     
/* 1778 */     li.text = s;
/* 1779 */     li.foreground = GDColor.COLOR_FG_COMPONENT;
/*      */     
/* 1781 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getEnchantmentInfo() {
/* 1785 */     if (this.dbEnchantment == null) return null;
/*      */     
/* 1787 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1789 */     li.text = this.dbEnchantment.getItemName();
/* 1790 */     li.foreground = GDColor.COLOR_FG_ENCHANTMENT;
/*      */     
/* 1792 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getSeedHexInfo() {
/* 1796 */     if (this.stashItem == null) return null;
/*      */     
/* 1798 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1800 */     li.text = getSeedHex();
/*      */     
/* 1802 */     return li;
/*      */   }
/*      */   
/*      */   public String getItemSetID() {
/* 1806 */     if (this.dbItem == null) return null;
/*      */     
/* 1808 */     return this.dbItem.getItemSetID();
/*      */   }
/*      */   
/*      */   public String getItemSetName() {
/* 1812 */     if (this.dbItem == null) return null;
/*      */     
/* 1814 */     return this.dbItem.getItemSetName();
/*      */   }
/*      */   
/*      */   public LabelInfo getItemSetNameInfo() {
/* 1818 */     if (this.dbItem == null) return null; 
/* 1819 */     if (this.dbItem.getItemSetName() == null) return null;
/*      */     
/* 1821 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1823 */     li.text = this.dbItem.getItemSetName();
/*      */     
/* 1825 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getRequiredLevelInfo() {
/* 1829 */     if (this.dbItem == null) return null;
/*      */     
/* 1831 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1833 */     li.text = Integer.toString(getRequiredLevel());
/*      */     
/* 1835 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getCharNameInfo() {
/* 1839 */     if (this.stashItem == null) return null;
/*      */     
/* 1841 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1843 */     li.text = getCharName();
/*      */     
/* 1845 */     if (li.text == null) li.text = "";
/*      */     
/* 1847 */     return li;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContainerType(int containerType) {
/* 1855 */     this.containerType = containerType;
/*      */     
/* 1857 */     if (containerType > 6) containerType = 0;
/*      */     
/* 1859 */     if (containerType == 6) this.attached = 1; 
/*      */   }
/*      */   
/*      */   public void setPrefix(DBAffix affix) {
/* 1863 */     this.dbPrefix = affix;
/*      */     
/* 1865 */     if (affix == null) {
/* 1866 */       this.stashItem.setPrefixID(null);
/*      */     } else {
/* 1868 */       this.stashItem.setPrefixID(affix.getAffixID());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setSuffix(DBAffix affix) {
/* 1873 */     this.dbSuffix = affix;
/*      */     
/* 1875 */     if (affix == null) {
/* 1876 */       this.stashItem.setSuffixID(null);
/*      */     } else {
/* 1878 */       this.stashItem.setSuffixID(affix.getAffixID());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setModifier(DBAffix affix) {
/* 1883 */     this.dbModifier = affix;
/*      */     
/* 1885 */     if (affix == null) {
/* 1886 */       this.stashItem.setModifierID(null);
/*      */     } else {
/* 1888 */       this.stashItem.setModifierID(affix.getAffixID());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setCompletionBonus(DBAffix affix) {
/* 1893 */     this.dbBonus = affix;
/*      */     
/* 1895 */     if (affix == null) {
/* 1896 */       this.stashItem.setCompletionBonusID(null);
/*      */     } else {
/* 1898 */       this.stashItem.setCompletionBonusID(affix.getAffixID());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setSeedHex(String s) {
/* 1903 */     if (s.length() != 8) {
/* 1904 */       createSeed();
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1909 */     byte[] bytes = new byte[4];
/* 1910 */     for (int i = 0; i < s.length(); i += 2) {
/* 1911 */       char c1 = s.charAt(i);
/* 1912 */       char c2 = s.charAt(i + 1);
/*      */       
/* 1914 */       byte b1 = hexDigitToByte(c1);
/* 1915 */       byte b2 = hexDigitToByte(c2);
/* 1916 */       byte b = (byte)(b1 * 16 + b2);
/*      */ 
/*      */       
/* 1919 */       bytes[bytes.length - 1 - i / 2] = b;
/*      */     } 
/*      */     
/* 1922 */     this.stashItem.setItemSeed(GDReader.getInt(bytes, 0));
/*      */   }
/*      */   
/*      */   public void setVar1(int var1) {
/* 1926 */     if (this.stashItem == null)
/*      */       return; 
/* 1928 */     int temp = 0;
/* 1929 */     if (var1 > 0) temp = var1;
/*      */     
/* 1931 */     this.stashItem.setVar1(temp);
/*      */   }
/*      */   
/*      */   public void setVar1Str(String var1) {
/* 1935 */     if (this.stashItem == null)
/*      */       return; 
/* 1937 */     int i = 0;
/*      */     try {
/* 1939 */       i = Integer.parseInt(var1);
/*      */     }
/* 1941 */     catch (NumberFormatException ex) {
/* 1942 */       i = 0;
/*      */     } 
/*      */     
/* 1945 */     setVar1(i);
/*      */   }
/*      */   
/*      */   public void setStackCount(int stackCount) {
/* 1949 */     if (this.stashItem == null)
/*      */       return; 
/* 1951 */     this.stashItem.setStackCount(stackCount);
/*      */   }
/*      */   
/*      */   public void setStackCountStr(String stackCount) {
/* 1955 */     if (this.stashItem == null)
/*      */       return; 
/* 1957 */     int i = 0;
/*      */     try {
/* 1959 */       i = Integer.parseInt(stackCount);
/*      */     }
/* 1961 */     catch (NumberFormatException ex) {
/* 1962 */       i = 0;
/*      */     } 
/*      */     
/* 1965 */     setStackCount(i);
/*      */   }
/*      */   
/*      */   public void setX(int x) {
/* 1969 */     this.x = x;
/* 1970 */     this.xPos = x;
/*      */   }
/*      */   
/*      */   public void setY(int y) {
/* 1974 */     this.y = y;
/* 1975 */     this.yPos = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void read() {
/* 1983 */     GDLog log = new GDLog();
/*      */     
/* 1985 */     readNewFormat(4, 0, log);
/*      */     
/* 1987 */     init();
/*      */     
/* 1989 */     this.error = log.containsErrors();
/*      */     
/* 1991 */     GDMsgLogger.addLog(log);
/*      */     
/* 1993 */     if (!this.error) {
/* 1994 */       loadData(0);
/*      */     }
/*      */   }
/*      */   
/*      */   private void readNewFormat(int version, int page, GDLog log) {
/* 1999 */     switch (version) {
/*      */       case 3:
/* 2001 */         readNewFormat_V3(page, log);
/*      */         return;
/*      */       
/*      */       case 4:
/* 2005 */         readNewFormat_V4(log);
/*      */         return;
/*      */       
/*      */       case 5:
/* 2009 */         readNewFormat_V4(log);
/*      */         return;
/*      */     } 
/*      */     
/* 2013 */     this.error = true;
/*      */   }
/*      */ 
/*      */   
/*      */   private void readNewFormat_V3(int page, GDLog log) {
/*      */     try {
/* 2019 */       this.stashItem.readNewFormat(3);
/*      */       
/* 2021 */       this.xPos = GDReader.readEncFloat(true);
/* 2022 */       this.x = (int)this.xPos;
/*      */       
/* 2024 */       this.yPos = GDReader.readEncFloat(true);
/* 2025 */       this.y = (int)this.yPos;
/*      */     }
/* 2027 */     catch (IOException ex) {
/* 2028 */       if (this.stashItem.getItemID() != null) {
/* 2029 */         Object[] args = { this.filename, this.stashItem.getItemID() };
/* 2030 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_ITEM", args);
/*      */         
/* 2032 */         log.addError(msg);
/*      */       } else {
/* 2034 */         Object[] args = { this.filename };
/* 2035 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_ITEM_UNKNOWN", args);
/*      */         
/* 2037 */         log.addError(msg);
/*      */       } 
/*      */       
/* 2040 */       this.error = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readNewFormat_V4(GDLog log) {
/*      */     try {
/* 2046 */       this.stashItem.readNewFormat(4);
/*      */       
/* 2048 */       if (this.containerType == 1 || this.containerType == 2 || this.containerType == 3) {
/*      */ 
/*      */         
/* 2051 */         this.xPos = GDReader.readEncFloat(true);
/* 2052 */         this.x = (int)this.xPos;
/*      */         
/* 2054 */         this.yPos = GDReader.readEncFloat(true);
/* 2055 */         this.y = (int)this.yPos;
/*      */       } 
/*      */       
/* 2058 */       if (this.containerType == 4) {
/* 2059 */         this.x = GDReader.readEncInt(true);
/* 2060 */         this.xPos = this.x;
/*      */         
/* 2062 */         this.y = GDReader.readEncInt(true);
/* 2063 */         this.yPos = this.y;
/*      */       } 
/*      */       
/* 2066 */       if (this.containerType == 6) {
/* 2067 */         this.attached = GDReader.readEncByte();
/*      */       }
/*      */     }
/* 2070 */     catch (IOException ex) {
/* 2071 */       if (this.stashItem.getItemID() != null) {
/* 2072 */         Object[] args = { this.filename, this.stashItem.getItemID() };
/* 2073 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_ITEM", args);
/*      */         
/* 2075 */         log.addError(msg);
/*      */       } else {
/* 2077 */         Object[] args = { this.filename };
/* 2078 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_ITEM_UNKNOWN", args);
/*      */         
/* 2080 */         log.addError(msg);
/*      */       } 
/*      */       
/* 2083 */       this.error = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readOldFormat(GDLog log) {
/* 2088 */     int len = 0;
/*      */     
/*      */     try {
/* 2091 */       while (len != -1) {
/* 2092 */         len = GDReader.readUInt(this.reader);
/*      */         
/* 2094 */         if (len != -1) {
/* 2095 */           String s = GDReader.readString(this.reader, len);
/*      */           
/* 2097 */           if (s.equals("stackCount")) {
/* 2098 */             this.stashItem.setStackCount(GDReader.readInt(this.reader));
/*      */           }
/* 2100 */           if (s.equals("begin_block")) {
/* 2101 */             this.beginBlock = GDReader.readBytes4(this.reader);
/*      */           }
/* 2103 */           if (s.equals("baseName")) {
/* 2104 */             len = GDReader.readUInt(this.reader);
/* 2105 */             if (len > 0) this.stashItem.setItemID(GDReader.readString(this.reader, len)); 
/*      */           } 
/* 2107 */           if (s.equals("prefixName")) {
/* 2108 */             len = GDReader.readUInt(this.reader);
/* 2109 */             if (len > 0) this.stashItem.setPrefixID(GDReader.readString(this.reader, len)); 
/*      */           } 
/* 2111 */           if (s.equals("suffixName")) {
/* 2112 */             len = GDReader.readUInt(this.reader);
/* 2113 */             if (len > 0) this.stashItem.setSuffixID(GDReader.readString(this.reader, len)); 
/*      */           } 
/* 2115 */           if (s.equals("modifierName")) {
/* 2116 */             len = GDReader.readUInt(this.reader);
/* 2117 */             if (len > 0) this.stashItem.setModifierID(GDReader.readString(this.reader, len)); 
/*      */           } 
/* 2119 */           if (s.equals("relicName")) {
/* 2120 */             len = GDReader.readUInt(this.reader);
/* 2121 */             if (len > 0) this.stashItem.setComponentID(GDReader.readString(this.reader, len)); 
/*      */           } 
/* 2123 */           if (s.equals("relicBonus")) {
/* 2124 */             len = GDReader.readUInt(this.reader);
/* 2125 */             if (len > 0) this.stashItem.setCompletionBonusID(GDReader.readString(this.reader, len)); 
/*      */           } 
/* 2127 */           if (s.equals("seed")) {
/* 2128 */             this.stashItem.setItemSeed(GDReader.readInt(this.reader));
/*      */             
/* 2130 */             if (this.stashItem.getItemSeed() == 0) {
/* 2131 */               Object[] args = { this.filename, this.stashItem.getItemID() };
/* 2132 */               String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_NO_SEED", args);
/*      */               
/* 2134 */               GDMsgLogger.addWarning(msg);
/*      */             } 
/*      */           } 
/* 2137 */           if (s.equals("relicSeed")) {
/* 2138 */             this.stashItem.setComponentSeed(GDReader.readInt(this.reader));
/*      */           }
/* 2140 */           if (s.equals("var1")) {
/* 2141 */             this.stashItem.setVar1(GDReader.readUInt(this.reader));
/*      */           }
/* 2143 */           if (s.equals("velocity")) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2148 */             int seed = GDReader.readUInt(this.reader);
/*      */             
/* 2150 */             if (this.stashItem.getComponentID() != null && 
/* 2151 */               !this.stashItem.getComponentID().isEmpty()) {
/* 2152 */               this.stashItem.setComponentSeed(seed);
/*      */             }
/*      */             
/* 2155 */             GDReader.readUInt(this.reader);
/* 2156 */             GDReader.readUInt(this.reader);
/*      */           } 
/* 2158 */           if (s.equals("owner"))
/*      */           {
/* 2160 */             GDReader.readUInt(this.reader);
/*      */           }
/* 2162 */           if (s.equals("end_block")) {
/* 2163 */             this.endBlock = GDReader.readBytes4(this.reader);
/*      */           }
/* 2165 */           if (s.equals("xOffset")) {
/* 2166 */             this.xPos = GDReader.readFloat(this.reader);
/* 2167 */             this.x = (int)this.xPos;
/*      */           } 
/* 2169 */           if (s.equals("yOffset")) {
/* 2170 */             this.yPos = GDReader.readFloat(this.reader);
/* 2171 */             this.y = (int)this.yPos;
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2179 */       this.stashItem.fillDependentStats(null);
/*      */     }
/* 2181 */     catch (IOException ex) {
/* 2182 */       if (this.stashItem.getItemID() != null) {
/* 2183 */         Object[] args = { this.filename, this.stashItem.getItemID() };
/* 2184 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_ITEM", args);
/*      */         
/* 2186 */         log.addError(msg);
/*      */       } else {
/* 2188 */         Object[] args = { this.filename };
/* 2189 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_ITEM_UNKNOWN", args);
/*      */         
/* 2191 */         log.addError(msg);
/*      */       } 
/*      */       
/* 2194 */       this.error = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getByteSize() {
/* 2199 */     int size = this.stashItem.getByteSize();
/*      */     
/* 2201 */     if (this.containerType == 1 || this.containerType == 2 || this.containerType == 3) {
/*      */ 
/*      */       
/* 2204 */       size += 4;
/* 2205 */       size += 4;
/*      */     } 
/*      */     
/* 2208 */     if (this.containerType == 4 || this.containerType == 5) {
/*      */       
/* 2210 */       size += 4;
/* 2211 */       size += 4;
/*      */     } 
/*      */     
/* 2214 */     if (this.containerType == 6) {
/* 2215 */       size++;
/*      */     }
/*      */     
/* 2218 */     return size;
/*      */   }
/*      */   
/*      */   public void write() throws IOException {
/* 2222 */     this.stashItem.write();
/*      */     
/* 2224 */     if (this.containerType == 1 || this.containerType == 2 || this.containerType == 3) {
/*      */ 
/*      */       
/* 2227 */       GDWriter.writeFloat(this.xPos);
/* 2228 */       GDWriter.writeFloat(this.yPos);
/*      */     } 
/*      */     
/* 2231 */     if (this.containerType == 4 || this.containerType == 5) {
/*      */       
/* 2233 */       GDWriter.writeInt(this.x);
/* 2234 */       GDWriter.writeInt(this.y);
/*      */     } 
/*      */     
/* 2237 */     if (this.containerType == 6) {
/* 2238 */       GDWriter.writeByte(this.attached);
/*      */     }
/*      */   }
/*      */   
/*      */   public void writeOldFormat(FileOutputStream writer, Charset cs) throws IOException {
/* 2243 */     writer.write(GDWriter.lengthToBytes4("stackCount"));
/* 2244 */     writer.write("stackCount".getBytes(cs));
/* 2245 */     writer.write(GDWriter.intToBytes4(this.stashItem.getStackCount()));
/*      */     
/* 2247 */     writer.write(GDWriter.lengthToBytes4("begin_block"));
/* 2248 */     writer.write("begin_block".getBytes(cs));
/* 2249 */     writer.write(this.beginBlock);
/*      */     
/* 2251 */     writer.write(GDWriter.lengthToBytes4("baseName"));
/* 2252 */     writer.write("baseName".getBytes(cs));
/* 2253 */     writer.write(GDWriter.lengthToBytes4(this.stashItem.getItemID()));
/* 2254 */     if (this.stashItem.getItemID() != null) writer.write(this.stashItem.getItemID().getBytes(cs));
/*      */     
/* 2256 */     writer.write(GDWriter.lengthToBytes4("prefixName"));
/* 2257 */     writer.write("prefixName".getBytes(cs));
/* 2258 */     writer.write(GDWriter.lengthToBytes4(this.stashItem.getPrefixID()));
/* 2259 */     if (this.stashItem.getPrefixID() != null) writer.write(this.stashItem.getPrefixID().getBytes(cs));
/*      */     
/* 2261 */     writer.write(GDWriter.lengthToBytes4("suffixName"));
/* 2262 */     writer.write("suffixName".getBytes(cs));
/* 2263 */     writer.write(GDWriter.lengthToBytes4(this.stashItem.getSuffixID()));
/* 2264 */     if (this.stashItem.getSuffixID() != null) writer.write(this.stashItem.getSuffixID().getBytes(cs));
/*      */     
/* 2266 */     writer.write(GDWriter.lengthToBytes4("modifierName"));
/* 2267 */     writer.write("modifierName".getBytes(cs));
/* 2268 */     writer.write(GDWriter.lengthToBytes4(this.stashItem.getModifierID()));
/* 2269 */     if (this.stashItem.getModifierID() != null) writer.write(this.stashItem.getModifierID().getBytes(cs));
/*      */     
/* 2271 */     writer.write(GDWriter.lengthToBytes4("relicName"));
/* 2272 */     writer.write("relicName".getBytes(cs));
/* 2273 */     writer.write(GDWriter.lengthToBytes4(this.stashItem.getComponentID()));
/* 2274 */     if (this.stashItem.getComponentID() != null) writer.write(this.stashItem.getComponentID().getBytes(cs));
/*      */     
/* 2276 */     writer.write(GDWriter.lengthToBytes4("relicBonus"));
/* 2277 */     writer.write("relicBonus".getBytes(cs));
/* 2278 */     writer.write(GDWriter.lengthToBytes4(this.stashItem.getCompletionBonusID()));
/* 2279 */     if (this.stashItem.getCompletionBonusID() != null) writer.write(this.stashItem.getCompletionBonusID().getBytes(cs));
/*      */     
/* 2281 */     writer.write(GDWriter.lengthToBytes4("seed"));
/* 2282 */     writer.write("seed".getBytes(cs));
/* 2283 */     writer.write(GDWriter.intToBytes4(this.stashItem.getItemSeed()));
/*      */     
/* 2285 */     writer.write(GDWriter.lengthToBytes4("relicSeed"));
/* 2286 */     writer.write("relicSeed".getBytes(cs));
/* 2287 */     writer.write(GDWriter.intToBytes4(this.stashItem.getComponentSeed()));
/*      */     
/* 2289 */     writer.write(GDWriter.lengthToBytes4("var1"));
/* 2290 */     writer.write("var1".getBytes(cs));
/* 2291 */     writer.write(GDWriter.intToBytes4(this.stashItem.getVar1()));
/*      */     
/* 2293 */     writer.write(GDWriter.lengthToBytes4("end_block"));
/* 2294 */     writer.write("end_block".getBytes(cs));
/* 2295 */     writer.write(this.endBlock);
/*      */     
/* 2297 */     writer.write(GDWriter.lengthToBytes4("xOffset"));
/* 2298 */     writer.write("xOffset".getBytes(cs));
/* 2299 */     writer.write(GDWriter.floatToBytes4(this.xPos));
/*      */     
/* 2301 */     writer.write(GDWriter.lengthToBytes4("yOffset"));
/* 2302 */     writer.write("yOffset".getBytes(cs));
/* 2303 */     writer.write(GDWriter.floatToBytes4(this.yPos));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void refresh() {
/* 2311 */     if (this.stashItem != null) {
/* 2312 */       GDLog log = new GDLog();
/* 2313 */       this.stashItem.fillDependentStats(log);
/* 2314 */       loadData(0);
/*      */       
/* 2316 */       this.error = log.containsMessages();
/*      */       
/* 2318 */       if (this.error) GDMsgLogger.addLog(log); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private String parseName() {
/* 2323 */     String name = this.dbItem.getItemName();
/*      */     
/* 2325 */     return name;
/*      */   }
/*      */   
/*      */   private ItemInfo compactBonuses() {
/* 2329 */     if (this.dbItem == null) return null;
/*      */     
/* 2331 */     ItemInfo info = new ItemInfo();
/*      */     
/* 2333 */     List<DBStat> itemStats = this.dbItem.getStatList();
/* 2334 */     if (itemStats != null) {
/* 2335 */       if (isComponent()) {
/*      */ 
/*      */         
/* 2338 */         int var1 = getVar1();
/*      */         
/* 2340 */         if (var1 == 0) var1 = 1;
/*      */         
/* 2342 */         List<DBStat> levelStats = DBStat.getStatsForLevel(itemStats, var1);
/*      */         
/* 2344 */         itemStats = levelStats;
/*      */       } 
/*      */       
/* 2347 */       DBStat.add(info.itemStats, this.dbItem, 1, itemStats);
/*      */     } 
/* 2349 */     if (this.dbItem.getSkillBonusList() != null) DBSkillBonus.add(info.itemSkillBonuses, this.dbItem.getSkillBonusList()); 
/* 2350 */     if (this.dbItem.getSkillModifierList() != null) info.skillModifiers.addAll(this.dbItem.getSkillModifierList()); 
/* 2351 */     if (this.dbItem.getItemSkill() != null) info.itemSkills.add(new ItemInfo.Skill(this.dbItem.getItemSkill(), this.dbItem.getItemSkillLevel(), this.dbItem.getItemSkillController())); 
/* 2352 */     if (this.dbItem.getPetBonusSkill() != null) info.petSkills.add(new ItemInfo.Skill(this.dbItem.getPetBonusSkill(), this.dbItem.getItemSkillLevel()));
/*      */     
/* 2354 */     if (this.dbPrefix != null) {
/* 2355 */       if (this.dbPrefix.getStatList() != null) DBStat.add(info.itemStats, this.dbPrefix, 3, this.dbPrefix.getStatList(this.dbItem.getItemScalePercent())); 
/* 2356 */       if (this.dbPrefix.getSkillBonusList() != null) DBSkillBonus.add(info.itemSkillBonuses, this.dbPrefix.getSkillBonusList()); 
/* 2357 */       if (this.dbPrefix.getSkillModifierList() != null) info.skillModifiers.addAll(this.dbPrefix.getSkillModifierList()); 
/* 2358 */       if (this.dbPrefix.getItemSkill() != null) info.itemSkills.add(new ItemInfo.Skill(this.dbPrefix.getItemSkill(), this.dbPrefix.getItemSkillLevel(), this.dbPrefix.getItemSkillController()));
/*      */ 
/*      */       
/* 2361 */       DBAffix petAffix = this.dbPrefix.getPetAffix();
/* 2362 */       if (petAffix != null && 
/* 2363 */         petAffix.getStatList() != null) DBStat.add(info.petStats, petAffix, 4, petAffix.getStatList());
/*      */       
/* 2365 */       DBSkill petSkill = this.dbPrefix.getPetSkill();
/* 2366 */       if (petSkill != null) {
/* 2367 */         List<DBStat> stats = DBStat.getByLevel(petSkill.getStatList(), 1);
/*      */         
/* 2369 */         if (stats != null && !stats.isEmpty()) DBStat.add(info.petStats, this.dbPrefix, 4, stats);
/*      */       
/*      */       } 
/*      */     } 
/* 2373 */     if (this.dbSuffix != null) {
/* 2374 */       if (this.dbSuffix.getStatList() != null) DBStat.add(info.itemStats, this.dbSuffix, 5, this.dbSuffix.getStatList(this.dbItem.getItemScalePercent())); 
/* 2375 */       if (this.dbSuffix.getSkillBonusList() != null) DBSkillBonus.add(info.itemSkillBonuses, this.dbSuffix.getSkillBonusList()); 
/* 2376 */       if (this.dbSuffix.getSkillModifierList() != null) info.skillModifiers.addAll(this.dbSuffix.getSkillModifierList()); 
/* 2377 */       if (this.dbSuffix.getItemSkill() != null) info.itemSkills.add(new ItemInfo.Skill(this.dbSuffix.getItemSkill(), this.dbSuffix.getItemSkillLevel(), this.dbSuffix.getItemSkillController()));
/*      */ 
/*      */       
/* 2380 */       DBAffix petAffix = this.dbSuffix.getPetAffix();
/* 2381 */       if (petAffix != null && 
/* 2382 */         petAffix.getStatList() != null) DBStat.add(info.petStats, petAffix, 6, petAffix.getStatList());
/*      */       
/* 2384 */       DBSkill petSkill = this.dbSuffix.getPetSkill();
/* 2385 */       if (petSkill != null) {
/* 2386 */         List<DBStat> stats = DBStat.getByLevel(petSkill.getStatList(), 1);
/*      */         
/* 2388 */         if (stats != null && !stats.isEmpty()) DBStat.add(info.petStats, this.dbSuffix, 6, stats);
/*      */       
/*      */       } 
/*      */     } 
/* 2392 */     if (this.dbModifier != null) {
/* 2393 */       if (this.dbModifier.getStatList() != null) DBStat.add(info.itemStats, this.dbModifier, 7, this.dbModifier.getStatList()); 
/* 2394 */       if (this.dbModifier.getSkillBonusList() != null) DBSkillBonus.add(info.itemSkillBonuses, this.dbModifier.getSkillBonusList()); 
/* 2395 */       if (this.dbModifier.getItemSkill() != null) info.itemSkills.add(new ItemInfo.Skill(this.dbModifier.getItemSkill(), this.dbModifier.getItemSkillLevel(), this.dbModifier.getItemSkillController()));
/*      */     
/*      */     } 
/* 2398 */     if (this.dbComponent != null) {
/* 2399 */       if (this.dbComponent.getStatList() != null) {
/*      */ 
/*      */         
/* 2402 */         int var1 = getVar1();
/*      */         
/* 2404 */         if (var1 == 0) var1 = 1;
/*      */         
/* 2406 */         List<DBStat> levelStats = DBStat.getStatsForLevel(this.dbComponent.getStatList(), var1);
/*      */         
/* 2408 */         DBStat.add(info.componentStats, this.dbComponent, 1, levelStats);
/*      */       } 
/* 2410 */       if (this.dbComponent.getSkillBonusList() != null) DBSkillBonus.add(info.componentSkillBonuses, this.dbComponent.getSkillBonusList()); 
/* 2411 */       if (this.dbComponent.getItemSkill() != null) info.componentSkills.add(new ItemInfo.Skill(this.dbComponent.getItemSkill(), this.dbComponent.getItemLevel(), this.dbComponent.getItemSkillController()));
/*      */     
/*      */     } 
/* 2414 */     if (this.dbBonus != null) {
/* 2415 */       if (this.dbBonus.getStatList() != null) DBStat.add(info.completionStats, this.dbBonus, 8, this.dbBonus.getStatList()); 
/* 2416 */       if (this.dbBonus.getSkillBonusList() != null) DBSkillBonus.add(info.completionSkillBonuses, this.dbBonus.getSkillBonusList()); 
/* 2417 */       if (this.dbBonus.getItemSkill() != null) info.completionSkills.add(new ItemInfo.Skill(this.dbBonus.getItemSkill(), this.dbBonus.getItemSkillLevel(), this.dbBonus.getItemSkillController()));
/*      */     
/*      */     } 
/* 2420 */     if (this.dbEnchantment != null && 
/* 2421 */       this.dbEnchantment.getStatList() != null) DBStat.add(info.enchantmentStats, this.dbEnchantment, 9, this.dbEnchantment.getStatList());
/*      */ 
/*      */     
/* 2424 */     return info;
/*      */   }
/*      */   
/*      */   private String determineCategoryTag() {
/* 2428 */     if (this.dbItem == null) return null;
/*      */     
/* 2430 */     String tag = null;
/*      */     
/* 2432 */     if (this.dbItem.getArmorClass() != null) {
/* 2433 */       if (this.dbItem.getArmorClass().equals("Heavy")) tag = "CATEGORY_ARMOR_HEAVY"; 
/* 2434 */       if (this.dbItem.getArmorClass().equals("Caster")) tag = "CATEGORY_ARMOR_CASTER";
/*      */     
/*      */     } 
/* 2437 */     if (this.dbItem.is1HWeapon()) tag = "CATEGORY_WEAPON_1H"; 
/* 2438 */     if (this.dbItem.is2HWeapon()) tag = "CATEGORY_WEAPON_2H";
/*      */     
/* 2440 */     return tag;
/*      */   }
/*      */   
/*      */   private String determineItemTypeTag() {
/* 2444 */     if (this.dbItem == null) return null; 
/* 2445 */     if (this.dbItem.getItemClass() == null) return null;
/*      */     
/* 2447 */     String tag = null;
/*      */     
/* 2449 */     if (this.dbItem.getItemClass().equals("ArmorProtective_Head")) tag = "TYPE_ARMOR_HEAD"; 
/* 2450 */     if (this.dbItem.getItemClass().equals("ArmorProtective_Shoulders")) tag = "TYPE_ARMOR_SHOULDERS"; 
/* 2451 */     if (this.dbItem.getItemClass().equals("ArmorProtective_Chest")) tag = "TYPE_ARMOR_CHEST"; 
/* 2452 */     if (this.dbItem.getItemClass().equals("ArmorProtective_Hands")) tag = "TYPE_ARMOR_HANDS"; 
/* 2453 */     if (this.dbItem.getItemClass().equals("ArmorProtective_Legs")) tag = "TYPE_ARMOR_LEGS"; 
/* 2454 */     if (this.dbItem.getItemClass().equals("ArmorProtective_Feet")) tag = "TYPE_ARMOR_FEET";
/*      */     
/* 2456 */     if (this.dbItem.getItemClass().equals("ArmorProtective_Waist")) tag = "TYPE_ARMOR_BELT"; 
/* 2457 */     if (this.dbItem.getItemClass().equals("ArmorJewelry_Amulet")) tag = "TYPE_ARMOR_AMULET"; 
/* 2458 */     if (this.dbItem.getItemClass().equals("ArmorJewelry_Medal")) tag = "TYPE_ARMOR_MEDAL"; 
/* 2459 */     if (this.dbItem.getItemClass().equals("ArmorJewelry_Ring")) tag = "TYPE_ARMOR_RING";
/*      */     
/* 2461 */     if (this.dbItem.getItemClass().equals("ItemRelic")) tag = "TYPE_ITEM_COMPONENT"; 
/* 2462 */     if (this.dbItem.getItemClass().equals("ItemArtifact")) tag = "TYPE_ITEM_ARTIFACT"; 
/* 2463 */     if (this.dbItem.getItemClass().equals("ItemEnchantment")) tag = "TYPE_ITEM_ENCHANTMENT";
/*      */     
/* 2465 */     if (this.dbItem.getItemClass().equals("WeaponArmor_Offhand")) tag = "TYPE_WEAPON_OFFHAND"; 
/* 2466 */     if (this.dbItem.getItemClass().equals("WeaponArmor_Shield")) tag = "TYPE_WEAPON_SHIELD";
/*      */     
/* 2468 */     if (this.dbItem.getItemClass().equals("WeaponMelee_Axe")) tag = "TYPE_WEAPON_AXE"; 
/* 2469 */     if (this.dbItem.getItemClass().equals("WeaponMelee_Mace")) tag = "TYPE_WEAPON_MACE"; 
/* 2470 */     if (this.dbItem.getItemClass().equals("WeaponHunting_Spear")) tag = "TYPE_WEAPON_SPEAR"; 
/* 2471 */     if (this.dbItem.getItemClass().equals("WeaponMelee_Sword")) tag = "TYPE_WEAPON_SWORD"; 
/* 2472 */     if (this.dbItem.getItemClass().equals("WeaponMelee_Dagger")) tag = "TYPE_WEAPON_DAGGER"; 
/* 2473 */     if (this.dbItem.getItemClass().equals("WeaponMelee_Scepter")) tag = "TYPE_WEAPON_SCEPTER"; 
/* 2474 */     if (this.dbItem.getItemClass().equals("WeaponHunting_Ranged1h")) tag = "TYPE_WEAPON_RANGED";
/*      */     
/* 2476 */     if (this.dbItem.getItemClass().equals("WeaponMelee_Axe2h")) tag = "TYPE_WEAPON_AXE"; 
/* 2477 */     if (this.dbItem.getItemClass().equals("WeaponMelee_Mace2h")) tag = "TYPE_WEAPON_MACE"; 
/* 2478 */     if (this.dbItem.getItemClass().equals("WeaponMagical_Staff")) tag = "TYPE_WEAPON_STAFF"; 
/* 2479 */     if (this.dbItem.getItemClass().equals("WeaponMelee_Sword2h")) tag = "TYPE_WEAPON_SWORD"; 
/* 2480 */     if (this.dbItem.getItemClass().equals("WeaponHunting_Ranged2h")) tag = "TYPE_WEAPON_RANGED";
/*      */     
/* 2482 */     if (this.dbItem.getItemClass().equals("ItemNote")) tag = "TYPE_ITEM_NOTE";
/*      */     
/* 2484 */     return tag;
/*      */   }
/*      */   
/*      */   public String determineRarityTag() {
/* 2488 */     if (this.dbItem == null) return null;
/*      */     
/* 2490 */     String tag = null;
/*      */     
/* 2492 */     if (this.dbItem.isArtifact()) {
/* 2493 */       if (this.dbItem.getArtifactClass() != null) {
/* 2494 */         if (this.dbItem.getArtifactClass().equals("Lesser")) tag = "CATEGORY_ARTIFACT_EMPOWERED"; 
/* 2495 */         if (this.dbItem.getArtifactClass().equals("Greater")) tag = "CATEGORY_ARTIFACT_TRANSCENDED"; 
/* 2496 */         if (this.dbItem.getArtifactClass().equals("Divine")) tag = "CATEGORY_ARTIFACT_MYTHICAL"; 
/*      */       } 
/*      */     } else {
/* 2499 */       String rarity = getRarity();
/*      */       
/* 2501 */       if (rarity != null) {
/* 2502 */         if (rarity.equals("Magical")) tag = "CATEGORY_RARITY_MAGICAL"; 
/* 2503 */         if (rarity.equals("Rare")) tag = "CATEGORY_RARITY_RARE"; 
/* 2504 */         if (rarity.equals("Epic")) tag = "CATEGORY_RARITY_EPIC"; 
/* 2505 */         if (rarity.equals("Legendary")) tag = "CATEGORY_RARITY_LEGENDARY";
/*      */       
/*      */       } 
/*      */     } 
/* 2509 */     return tag;
/*      */   }
/*      */   
/*      */   private static String byteToHex(byte b) {
/* 2513 */     int i = b;
/* 2514 */     if (i < 0) i += 256;
/*      */     
/* 2516 */     int v1 = i / 16;
/* 2517 */     int v2 = i % 16;
/*      */     
/* 2519 */     String s = intDigitToHex(v1) + intDigitToHex(v2);
/*      */     
/* 2521 */     return s;
/*      */   }
/*      */   
/*      */   private static String intDigitToHex(int val) {
/* 2525 */     String s = null;
/*      */     
/* 2527 */     switch (val)
/*      */     { case 10:
/* 2529 */         s = "A";
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
/* 2552 */         return s;case 11: s = "B"; return s;case 12: s = "C"; return s;case 13: s = "D"; return s;case 14: s = "E"; return s;case 15: s = "F"; return s; }  if (val >= 0 && val <= 9) s = Integer.toString(val);  return s;
/*      */   }
/*      */   
/*      */   private static byte hexDigitToByte(char c) {
/* 2556 */     byte b = -1;
/*      */     
/* 2558 */     switch (c) {
/*      */       case '0':
/* 2560 */         b = 0;
/*      */         break;
/*      */       case '1':
/* 2563 */         b = 1;
/*      */         break;
/*      */       case '2':
/* 2566 */         b = 2;
/*      */         break;
/*      */       case '3':
/* 2569 */         b = 3;
/*      */         break;
/*      */       case '4':
/* 2572 */         b = 4;
/*      */         break;
/*      */       case '5':
/* 2575 */         b = 5;
/*      */         break;
/*      */       case '6':
/* 2578 */         b = 6;
/*      */         break;
/*      */       case '7':
/* 2581 */         b = 7;
/*      */         break;
/*      */       case '8':
/* 2584 */         b = 8;
/*      */         break;
/*      */       case '9':
/* 2587 */         b = 9;
/*      */         break;
/*      */       case 'A':
/* 2590 */         b = 10;
/*      */         break;
/*      */       case 'B':
/* 2593 */         b = 11;
/*      */         break;
/*      */       case 'C':
/* 2596 */         b = 12;
/*      */         break;
/*      */       case 'D':
/* 2599 */         b = 13;
/*      */         break;
/*      */       case 'E':
/* 2602 */         b = 14;
/*      */         break;
/*      */       case 'F':
/* 2605 */         b = 15;
/*      */         break;
/*      */     } 
/*      */     
/* 2609 */     return b;
/*      */   }
/*      */   
/*      */   public static int generateSeed() {
/* 2613 */     return random.nextInt();
/*      */   }
/*      */   
/*      */   public void createSeed() {
/* 2617 */     this.stashItem.setItemSeed(generateSeed());
/*      */   }
/*      */   
/*      */   public String getItemSetBonuses() {
/* 2621 */     if (this.dbItem == null) return null;
/*      */     
/* 2623 */     String s = this.dbItem.getItemSetName();
/*      */     
/* 2625 */     if (s == null) return null;
/*      */     
/* 2627 */     s = GDColor.HTML_COLOR_ITEMSET + s + "</font>" + "<br>";
/*      */     
/* 2629 */     ArrayList<List<DBStat>> arrStat = this.dbItem.getItemSetBonusesPerLevel();
/* 2630 */     ArrayList<List<DBSkillBonus>> arrBonus = this.dbItem.getItemSetSkillBonusesPerLevel();
/*      */     int i;
/* 2632 */     for (i = 0; i < arrStat.size(); i++) {
/* 2633 */       Object[] args = { Integer.valueOf(i + 1) };
/* 2634 */       String title = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SET_NUM_ITEMS", args);
/*      */       
/* 2636 */       boolean hasTitle = false;
/* 2637 */       String txtStat = null;
/*      */       
/* 2639 */       if (!((List)arrStat.get(i)).isEmpty() || !((List)arrBonus.get(i)).isEmpty()) {
/* 2640 */         DetailComposer comp = DetailComposer.createComposer(this.stashItem, 1, arrStat.get(i), arrBonus.get(i));
/*      */         
/* 2642 */         txtStat = DetailComposer.getComposerBonuses(comp, true, true, false);
/* 2643 */         if (txtStat != null) {
/* 2644 */           if (!hasTitle) {
/* 2645 */             s = s + title;
/*      */             
/* 2647 */             hasTitle = true;
/*      */           } 
/*      */           
/* 2650 */           s = s + "<br>" + GDColor.HTML_COLOR_STAT + txtStat + "</font>";
/*      */         } 
/*      */       } 
/*      */       
/* 2654 */       List<DBSkillModifier> modifiers = this.dbItem.getSkillModifiers(i);
/* 2655 */       if (modifiers != null && !modifiers.isEmpty()) {
/* 2656 */         DetailComposer compSkillModifier = DetailComposer.createComposerSkillMod(this.stashItem, 1, modifiers);
/*      */         
/* 2658 */         String txtMod = DetailComposer.getComposerBonuses(compSkillModifier, true, true, false);
/* 2659 */         if (txtMod != null) {
/* 2660 */           if (!hasTitle) {
/* 2661 */             s = s + title;
/*      */             
/* 2663 */             hasTitle = true;
/*      */           } 
/*      */           
/* 2666 */           s = s + "<br>" + GDColor.HTML_COLOR_STAT + txtMod + "</font>";
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2671 */     return s;
/*      */   }
/*      */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\item\GDItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */