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
/*      */   public static final int CONTAINER_MULTI = 7;
/*      */   
/*      */   private InputStream reader;
/*      */   
/*      */   private byte[] beginBlock;
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
/*  128 */     this.containerType = containerType;
/*  129 */     this.error = false;
/*      */     
/*  131 */     this.stashItem = new DBStashItem(charName, hardcore);
/*      */   }
/*      */   
/*      */   public GDItem(DBItem item) {
/*  135 */     this.containerType = 0;
/*  136 */     this.error = false;
/*      */     
/*  138 */     this.dbItem = item;
/*  139 */     this.stashItem = new DBStashItem(item);
/*      */     
/*  141 */     init();
/*      */     
/*  143 */     this.error = GDMsgLogger.errorsInLog();
/*      */     
/*  145 */     if (!this.error) {
/*  146 */       loadData(0);
/*      */     }
/*      */   }
/*      */   
/*      */   public GDItem(DBStashItem item) {
/*  151 */     this(item, null);
/*      */   }
/*      */   
/*      */   public GDItem(DBStashItem item, String filename) {
/*  155 */     this.containerType = 0;
/*  156 */     this.error = false;
/*  157 */     this.filename = filename;
/*      */     
/*  159 */     this.stashItem = new DBStashItem(item);
/*      */     
/*  161 */     init();
/*      */     
/*  163 */     this.error = GDMsgLogger.errorsInLog();
/*      */     
/*  165 */     if (!this.error) {
/*  166 */       loadData(0);
/*      */     }
/*      */   }
/*      */   
/*      */   public GDItem(InputStream reader, int stashType, int version, boolean hardcore, String filename, int page, GDLog log) {
/*  171 */     this.containerType = 1;
/*  172 */     this.error = false;
/*  173 */     this.filename = filename;
/*      */     
/*  175 */     this.stashItem = new DBStashItem();
/*      */     
/*  177 */     this.reader = reader;
/*  178 */     this.stashItem.setHardcore(hardcore);
/*  179 */     this.stashItem.setCharName(null);
/*      */     
/*  181 */     GDLog tLog = new GDLog();
/*      */     
/*  183 */     if (stashType == 0) {
/*  184 */       readOldFormat(tLog);
/*      */     }
/*      */     
/*  187 */     if (stashType == 1) {
/*  188 */       readNewFormat(version, page, tLog);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  193 */     if (log == null) {
/*  194 */       GDMsgLogger.addLog(tLog);
/*      */     } else {
/*  196 */       log.addLog(tLog);
/*      */     } 
/*      */     
/*  199 */     if (!this.error) {
/*  200 */       loadData(page);
/*      */     }
/*      */   }
/*      */   
/*      */   private void init() {
/*  205 */     this.beginBlock = BEGINBLOCK;
/*  206 */     this.endBlock = ENDBLOCK;
/*      */   }
/*      */   
/*      */   private void loadData(int page) {
/*  210 */     if (this.stashItem == null)
/*      */       return; 
/*  212 */     if (this.stashItem.getItemID() != null) {
/*  213 */       this.dbItem = this.stashItem.getDBItem();
/*      */       
/*  215 */       if (this.dbItem == null) {
/*  216 */         Object[] args = { this.filename, Integer.valueOf(page), this.stashItem.getItemID() };
/*  217 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_ITEM_NOT_FOUND", args);
/*      */         
/*  219 */         GDMsgLogger.addWarning(msg);
/*      */         
/*  221 */         this.error = true;
/*      */       } 
/*      */     } 
/*      */     
/*  225 */     if (this.stashItem.getStackCount() == 0)
/*      */     {
/*  227 */       this.stashItem.setStackCount(1);
/*      */     }
/*      */     
/*  230 */     this.dbPrefix = this.stashItem.getDBPrefix();
/*  231 */     this.dbSuffix = this.stashItem.getDBSuffix();
/*  232 */     this.dbModifier = this.stashItem.getDBModifier();
/*  233 */     this.dbComponent = this.stashItem.getDBComponent();
/*  234 */     this.dbBonus = this.stashItem.getDBCompletionBonus();
/*  235 */     this.dbEnchantment = this.stashItem.getDBAugment();
/*      */ 
/*      */     
/*  238 */     this.reqDex = this.stashItem.getRequiredCunning();
/*  239 */     this.reqInt = this.stashItem.getRequiredSpirit();
/*  240 */     this.reqStr = this.stashItem.getRequiredPhysique();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void generateRandStats() {
/*  246 */     List<DBStat> list = null;
/*      */     
/*  248 */     if (this.dbItem == null || 
/*  249 */       this.dbItem.getStatList() != null);
/*      */ 
/*      */ 
/*      */     
/*  253 */     if (this.dbPrefix == null || 
/*  254 */       this.dbPrefix.getStatList() != null);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  259 */     if (this.dbSuffix != null && 
/*  260 */       this.dbSuffix.getStatList() != null) {
/*  261 */       list = cloneStatList(this.dbSuffix.getStatList());
/*  262 */       applyRNG(list, this.dbSuffix.getJitterPercent());
/*      */     } 
/*      */ 
/*      */     
/*  266 */     System.out.println();
/*      */   }
/*      */   
/*      */   private void applyRNG(List<DBStat> list, int jitterPercent) {
/*  270 */     int min = 100 - jitterPercent;
/*  271 */     int max = 100 + jitterPercent;
/*      */     
/*  273 */     GDRandomUniform rand = new GDRandomUniform(this.stashItem.getItemSeed());
/*      */     
/*  275 */     rand.generateInt(min, max); rand.generateInt(min, max);
/*      */     
/*  277 */     for (DBStat stat : list) {
/*  278 */       TagInfo info = TagInfoHashMap.getInfo(stat.getStatType());
/*      */       
/*  280 */       if (info == null || 
/*  281 */         !info.isJittering())
/*      */         continue; 
/*  283 */       float factor = rand.generateInt(min, max) / 100.0F;
/*  284 */       stat.setStatMin((int)(stat.getStatMin() * factor));
/*  285 */       stat.setStatMax((int)(stat.getStatMax() * factor));
/*  286 */       stat.setStatModifier((int)(stat.getStatModifier() * factor));
/*      */     } 
/*      */   }
/*      */   
/*      */   private List<DBStat> cloneStatList(List<DBStat> list) {
/*  291 */     if (list == null) return null;
/*      */     
/*  293 */     List<DBStat> cloneList = new LinkedList<>();
/*      */     
/*  295 */     for (DBStat stat : list) {
/*  296 */       DBStat cloneStat = stat.clone();
/*      */       
/*  298 */       cloneList.add(cloneStat);
/*      */     } 
/*      */     
/*  301 */     return cloneList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GDItem clone() {
/*  310 */     this.stashItem.fillDependentStats(null);
/*      */     
/*  312 */     GDItem item = new GDItem(this.stashItem);
/*      */     
/*  314 */     return item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(GDItem item) {
/*  322 */     int i = 0;
/*      */     
/*  324 */     if (item.dbItem != null) {
/*  325 */       if (this.dbItem != null) {
/*  326 */         i = this.dbItem.compareTo(item.dbItem);
/*      */         
/*  328 */         if (i != 0) return i; 
/*      */       } else {
/*  330 */         return -1;
/*      */       } 
/*      */     } else {
/*  333 */       if (this.dbItem != null) {
/*  334 */         return 1;
/*      */       }
/*  336 */       return 0;
/*      */     } 
/*      */ 
/*      */     
/*  340 */     int oAffix = 0;
/*  341 */     int iAffix = 0;
/*      */     
/*  343 */     if (this.dbPrefix != null) oAffix++; 
/*  344 */     if (this.dbSuffix != null) oAffix += 2;
/*      */     
/*  346 */     if (item.dbPrefix != null) iAffix++; 
/*  347 */     if (item.dbSuffix != null) iAffix += 2;
/*      */     
/*  349 */     if (iAffix != oAffix) {
/*  350 */       return oAffix - iAffix;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  356 */     if (item.dbModifier == null && this.dbModifier != null) return 1; 
/*  357 */     if (item.dbModifier != null && this.dbModifier == null) return -1;
/*      */ 
/*      */ 
/*      */     
/*  361 */     if (item.dbComponent == null && this.dbComponent != null) return 1; 
/*  362 */     if (item.dbComponent != null && this.dbComponent == null) return -1;
/*      */ 
/*      */ 
/*      */     
/*  366 */     if (item.dbEnchantment == null && this.dbEnchantment != null) return 1; 
/*  367 */     if (item.dbEnchantment != null && this.dbEnchantment == null) return -1;
/*      */ 
/*      */ 
/*      */     
/*  371 */     int oLevel = getRequiredLevel();
/*  372 */     int iLevel = item.getRequiredLevel();
/*      */     
/*  374 */     if (oLevel < iLevel) return -1; 
/*  375 */     if (oLevel > iLevel) return 1;
/*      */ 
/*      */ 
/*      */     
/*  379 */     if (item.dbPrefix != null)
/*  380 */     { if (this.dbPrefix != null) {
/*  381 */         i = this.dbPrefix.compareTo(item.dbPrefix);
/*      */         
/*  383 */         if (i != 0) return i; 
/*      */       } else {
/*  385 */         throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_PREFIX_BOTH"));
/*      */       }
/*      */        }
/*  388 */     else if (this.dbPrefix != null) { throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_PREFIX_NEITHER")); }
/*      */ 
/*      */     
/*  391 */     if (item.dbSuffix != null)
/*  392 */     { if (this.dbSuffix != null) {
/*  393 */         i = this.dbSuffix.compareTo(item.dbSuffix);
/*      */         
/*  395 */         if (i != 0) return i; 
/*      */       } else {
/*  397 */         throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_SUFFIX_BOTH"));
/*      */       }
/*      */        }
/*  400 */     else if (this.dbSuffix != null) { throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_SUFFIX_NEITHER")); }
/*      */ 
/*      */     
/*  403 */     if (item.dbModifier != null)
/*  404 */     { if (this.dbModifier != null) {
/*  405 */         i = this.dbModifier.compareTo(item.dbModifier);
/*      */         
/*  407 */         if (i != 0) return i; 
/*      */       } else {
/*  409 */         throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_MODIFIER_BOTH"));
/*      */       }
/*      */        }
/*  412 */     else if (this.dbModifier != null) { throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_MODIFIER_NEITHER")); }
/*      */ 
/*      */     
/*  415 */     if (item.dbComponent != null)
/*  416 */     { if (this.dbComponent != null) {
/*  417 */         i = this.dbComponent.compareTo(item.dbComponent);
/*      */         
/*  419 */         if (i != 0) return i;
/*      */         
/*  421 */         if (item.stashItem.getCompletionBonusID() != null)
/*  422 */         { if (this.stashItem.getCompletionBonusID() != null) {
/*  423 */             if (!item.stashItem.getCompletionBonusID().equals(this.stashItem.getCompletionBonusID())) {
/*  424 */               i = this.stashItem.getCompletionBonusID().compareTo(item.stashItem.getCompletionBonusID());
/*      */               
/*  426 */               if (i != 0) return i; 
/*      */             } 
/*      */           } else {
/*  429 */             return -1;
/*      */           }
/*      */            }
/*  432 */         else if (this.stashItem.getCompletionBonusID() != null) { return 1; }
/*      */       
/*      */       } else {
/*  435 */         throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_COMPONENT_BOTH"));
/*      */       }
/*      */        }
/*  438 */     else if (this.dbComponent != null) { throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_COMPONENT_NEITHER")); }
/*      */ 
/*      */     
/*  441 */     if (item.dbEnchantment != null)
/*  442 */     { if (this.dbEnchantment != null) {
/*  443 */         i = this.dbEnchantment.compareTo(item.dbEnchantment);
/*      */         
/*  445 */         if (i != 0) return i; 
/*      */       } else {
/*  447 */         throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_ENCHANTMENT_BOTH"));
/*      */       }
/*      */        }
/*  450 */     else if (this.dbEnchantment != null) { throw new AssertionError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_ENCHANTMENTENT_NEITHER")); }
/*      */ 
/*      */     
/*  453 */     if (this.stashItem.getStashID() < item.stashItem.getStashID()) return -1; 
/*  454 */     if (this.stashItem.getStashID() > item.stashItem.getStashID()) return 1;
/*      */     
/*  456 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasErrors() {
/*  464 */     return this.error;
/*      */   }
/*      */   
/*      */   public int getContainerType() {
/*  468 */     return this.containerType;
/*      */   }
/*      */   
/*      */   public DBItem getDBItem() {
/*  472 */     return this.dbItem;
/*      */   }
/*      */   
/*      */   public DBStashItem getDBStashItem() {
/*  476 */     return this.stashItem;
/*      */   }
/*      */   
/*      */   public DBItem getDBComponent() {
/*  480 */     return this.dbComponent;
/*      */   }
/*      */   
/*      */   public DBAffixSet getBonusAffixSet() {
/*  484 */     if (this.dbItem == null) return null;
/*      */     
/*  486 */     return this.dbItem.getBonusAffixSet();
/*      */   }
/*      */   
/*      */   public List<DBAffix> getBonusAffixList() {
/*  490 */     List<DBAffix> list = new LinkedList<>();
/*      */     
/*  492 */     if (this.dbItem == null) return list;
/*      */     
/*  494 */     return this.dbItem.getBonusAffixList();
/*      */   }
/*      */   
/*      */   public DBStashItem getStashItem() {
/*  498 */     return this.stashItem;
/*      */   }
/*      */   
/*      */   public String getItemID() {
/*  502 */     if (this.stashItem == null) return null;
/*      */     
/*  504 */     return this.stashItem.getItemID();
/*      */   }
/*      */   
/*      */   public String getItemClass() {
/*  508 */     if (this.dbItem == null) return null;
/*      */     
/*  510 */     return this.dbItem.getItemClass();
/*      */   }
/*      */   
/*      */   public String getRarity() {
/*  514 */     if (this.stashItem != null) return this.stashItem.getRarity(); 
/*  515 */     if (this.dbItem != null) return this.dbItem.getRarity();
/*      */     
/*  517 */     return "Common";
/*      */   }
/*      */   
/*      */   public String getBaseName() {
/*  521 */     if (this.dbItem == null) return null;
/*      */     
/*  523 */     return this.dbItem.getName(true);
/*      */   }
/*      */   
/*      */   public String getShortItemName() {
/*  527 */     if (this.dbItem == null) return null;
/*      */     
/*  529 */     return this.dbItem.getName(false);
/*      */   }
/*      */   
/*      */   public String getLongItemName() {
/*  533 */     if (this.dbItem == null) return null;
/*      */     
/*  535 */     return this.dbItem.getFullName();
/*      */   }
/*      */   
/*      */   public String getComponentName() {
/*  539 */     if (this.dbComponent == null) return null;
/*      */     
/*  541 */     String s = this.dbComponent.getName(false);
/*      */     
/*  543 */     if (this.stashItem.getVar1() < this.dbComponent.getComponentPieces()) {
/*  544 */       s = s + " (" + Integer.toString(this.stashItem.getVar1() + 1) + "/" + Integer.toString(this.dbComponent.getComponentPieces()) + ")";
/*      */     }
/*      */     
/*  547 */     return s;
/*      */   }
/*      */   
/*      */   public String getEnchantmentName() {
/*  551 */     if (this.dbEnchantment == null) return null;
/*      */     
/*  553 */     return this.dbEnchantment.getName(true);
/*      */   }
/*      */   
/*      */   public List<DBStat> getStatList() {
/*  557 */     List<DBStat> listAll = new LinkedList<>();
/*  558 */     List<DBStat> list = null;
/*      */     
/*  560 */     if (this.dbItem != null) {
/*  561 */       list = this.dbItem.getStatList();
/*      */       
/*  563 */       if (list != null) listAll.addAll(list);
/*      */     
/*      */     } 
/*  566 */     if (this.dbPrefix != null) {
/*  567 */       list = this.dbPrefix.getStatList();
/*      */       
/*  569 */       if (list != null) listAll.addAll(list);
/*      */     
/*      */     } 
/*  572 */     if (this.dbSuffix != null) {
/*  573 */       list = this.dbSuffix.getStatList();
/*      */       
/*  575 */       if (list != null) listAll.addAll(list);
/*      */     
/*      */     } 
/*  578 */     return listAll;
/*      */   }
/*      */   
/*      */   public boolean hasPetBonus() {
/*  582 */     if (this.dbItem != null && 
/*  583 */       this.dbItem.getPetBonusSkill() != null) return true;
/*      */ 
/*      */     
/*  586 */     if (this.dbPrefix != null) {
/*  587 */       if (this.dbPrefix.getPetAffix() != null) return true; 
/*  588 */       if (this.dbPrefix.getPetSkill() != null) return true;
/*      */     
/*      */     } 
/*  591 */     if (this.dbSuffix != null) {
/*  592 */       if (this.dbSuffix.getPetAffix() != null) return true; 
/*  593 */       if (this.dbSuffix.getPetSkill() != null) return true;
/*      */     
/*      */     } 
/*  596 */     return false;
/*      */   }
/*      */   
/*      */   public boolean hasConvertIn(String convertIn) {
/*  600 */     if (this.dbItem != null && 
/*  601 */       convertIn.equals(this.dbItem.getConvertIn())) return true;
/*      */ 
/*      */     
/*  604 */     if (this.dbPrefix != null && 
/*  605 */       convertIn.equals(this.dbPrefix.getConvertIn())) return true;
/*      */ 
/*      */     
/*  608 */     if (this.dbSuffix != null && 
/*  609 */       convertIn.equals(this.dbSuffix.getConvertIn())) return true;
/*      */ 
/*      */     
/*  612 */     return false;
/*      */   }
/*      */   
/*      */   public boolean hasConvertOut(String convertOut) {
/*  616 */     if (this.dbItem != null && 
/*  617 */       convertOut.equals(this.dbItem.getConvertOut())) return true;
/*      */ 
/*      */     
/*  620 */     if (this.dbPrefix != null && 
/*  621 */       convertOut.equals(this.dbPrefix.getConvertOut())) return true;
/*      */ 
/*      */     
/*  624 */     if (this.dbSuffix != null && 
/*  625 */       convertOut.equals(this.dbSuffix.getConvertOut())) return true;
/*      */ 
/*      */     
/*  628 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isSoulbound() {
/*  632 */     if (this.stashItem == null) return false;
/*      */     
/*  634 */     return this.stashItem.isSoulbound();
/*      */   }
/*      */   
/*      */   public boolean isCraftable() {
/*  638 */     if (this.dbItem == null) return false;
/*      */     
/*  640 */     return this.dbItem.isCraftable();
/*      */   }
/*      */   
/*      */   public boolean isHardcore() {
/*  644 */     if (this.stashItem == null) return false;
/*      */     
/*  646 */     return this.stashItem.isHardcore();
/*      */   }
/*      */   
/*      */   public boolean isArmor() {
/*  650 */     if (this.dbItem == null) return false;
/*      */     
/*  652 */     return this.dbItem.isArmor();
/*      */   }
/*      */   
/*      */   public boolean isJewelry() {
/*  656 */     if (this.dbItem == null) return false;
/*      */     
/*  658 */     return this.dbItem.isJewelry();
/*      */   }
/*      */   
/*      */   public boolean isWeapon() {
/*  662 */     if (this.dbItem == null) return false;
/*      */     
/*  664 */     return this.dbItem.isWeapon();
/*      */   }
/*      */   
/*      */   public boolean isOffhand() {
/*  668 */     if (this.dbItem == null) return false;
/*      */     
/*  670 */     return this.dbItem.isOffhand();
/*      */   }
/*      */   
/*      */   public boolean is1HWeapon() {
/*  674 */     if (this.dbItem == null) return false;
/*      */     
/*  676 */     return this.dbItem.is1HWeapon();
/*      */   }
/*      */   
/*      */   public boolean is2HWeapon() {
/*  680 */     if (this.dbItem == null) return false;
/*      */     
/*  682 */     return this.dbItem.is2HWeapon();
/*      */   }
/*      */   
/*      */   public boolean isArtifact() {
/*  686 */     if (this.dbItem == null) return false;
/*      */     
/*  688 */     return this.dbItem.isArtifact();
/*      */   }
/*      */   
/*      */   public boolean isComponent() {
/*  692 */     if (this.dbItem == null) return false;
/*      */     
/*  694 */     return this.dbItem.isComponent();
/*      */   }
/*      */   
/*      */   public boolean isEnchantment() {
/*  698 */     if (this.dbItem == null) return false;
/*      */     
/*  700 */     return this.dbItem.isEnchantment();
/*      */   }
/*      */   
/*      */   public boolean isNote() {
/*  704 */     if (this.dbItem == null) return false;
/*      */     
/*  706 */     return this.dbItem.isNote();
/*      */   }
/*      */   
/*      */   public boolean isFactionBooster() {
/*  710 */     if (this.dbItem == null) return false;
/*      */     
/*  712 */     return this.dbItem.isFactionBooster();
/*      */   }
/*      */   
/*      */   public boolean isElixir() {
/*  716 */     if (this.dbItem == null) return false;
/*      */     
/*  718 */     return this.dbItem.isElixir();
/*      */   }
/*      */   
/*      */   public boolean isPotion() {
/*  722 */     if (this.dbItem == null) return false;
/*      */     
/*  724 */     return this.dbItem.isPotion();
/*      */   }
/*      */   
/*      */   public boolean isQuestItem() {
/*  728 */     if (this.dbItem == null) return false;
/*      */     
/*  730 */     return this.dbItem.isQuestItem();
/*      */   }
/*      */   
/*      */   public boolean isStackable() {
/*  734 */     if (this.stashItem == null) return false;
/*      */     
/*  736 */     return this.stashItem.isStackable();
/*      */   }
/*      */   
/*      */   public boolean isEpic() {
/*  740 */     if (this.stashItem == null) return false;
/*      */     
/*  742 */     return this.stashItem.isEpic();
/*      */   }
/*      */   
/*      */   public boolean isLegendary() {
/*  746 */     if (this.stashItem == null) return false;
/*      */     
/*  748 */     return this.stashItem.isLegendary();
/*      */   }
/*      */   
/*      */   public boolean isUnique() {
/*  752 */     if (this.stashItem == null) return false;
/*      */     
/*  754 */     return this.stashItem.isUnique();
/*      */   }
/*      */   
/*      */   public int getDefaultStackSize() {
/*  758 */     if (this.stashItem == null) return 1;
/*      */     
/*  760 */     return this.stashItem.getDefaultStackSize();
/*      */   }
/*      */   
/*      */   public String getPrefixID() {
/*  764 */     if (this.dbPrefix == null) return null;
/*      */     
/*  766 */     return this.dbPrefix.getAffixID();
/*      */   }
/*      */   
/*      */   public DBAffix getPrefix() {
/*  770 */     return this.dbPrefix;
/*      */   }
/*      */   
/*      */   public String getSuffixID() {
/*  774 */     if (this.dbSuffix == null) return null;
/*      */     
/*  776 */     return this.dbSuffix.getAffixID();
/*      */   }
/*      */   
/*      */   public DBAffix getSuffix() {
/*  780 */     return this.dbSuffix;
/*      */   }
/*      */   
/*      */   public DBAffix getModifier() {
/*  784 */     return this.dbModifier;
/*      */   }
/*      */   
/*      */   public DBAffix getCompletionBonus() {
/*  788 */     return this.dbBonus;
/*      */   }
/*      */   
/*      */   public DBItem getAugment() {
/*  792 */     return this.dbEnchantment;
/*      */   }
/*      */   
/*      */   public String getSeedHex() {
/*  796 */     if (this.stashItem == null) return null;
/*      */ 
/*      */     
/*  799 */     byte[] seed = GDWriter.intToBytes4(this.stashItem.getItemSeed());
/*      */ 
/*      */     
/*  802 */     String s = "";
/*  803 */     for (int i = 0; i < seed.length; i++) {
/*  804 */       byte val = seed[seed.length - 1 - i];
/*      */       
/*  806 */       s = s + byteToHex(val);
/*      */     } 
/*      */     
/*  809 */     return s;
/*      */   }
/*      */   
/*      */   public int getVar1() {
/*  813 */     if (this.stashItem == null) return 0;
/*      */     
/*  815 */     return this.stashItem.getVar1();
/*      */   }
/*      */   
/*      */   public String getVar1Str() {
/*  819 */     if (this.stashItem == null) return null;
/*      */     
/*  821 */     int i = getVar1();
/*      */     
/*  823 */     if (i == 0) i = 1;
/*      */     
/*  825 */     return Integer.toString(i);
/*      */   }
/*      */   
/*      */   public int getStackCount() {
/*  829 */     if (this.stashItem == null) return 0;
/*      */     
/*  831 */     return this.stashItem.getStackCount();
/*      */   }
/*      */   
/*      */   public String getStackCountStr() {
/*  835 */     if (this.stashItem == null) return null;
/*      */     
/*  837 */     return Integer.toString(getStackCount());
/*      */   }
/*      */   
/*      */   public int getX() {
/*  841 */     return this.x;
/*      */   }
/*      */   
/*      */   public int getY() {
/*  845 */     return this.y;
/*      */   }
/*      */   
/*      */   public String getCharName() {
/*  849 */     if (this.stashItem == null) return null;
/*      */     
/*  851 */     return this.stashItem.getCharName();
/*      */   }
/*      */   
/*      */   public int getStashID() {
/*  855 */     if (this.stashItem == null) return -1;
/*      */     
/*  857 */     return this.stashItem.getStashID();
/*      */   }
/*      */   
/*      */   public int getItemLevel() {
/*  861 */     if (this.stashItem == null) return 0;
/*      */     
/*  863 */     return this.stashItem.getItemLevel();
/*      */   }
/*      */   
/*      */   public int getItemSkillLevel() {
/*  867 */     if (this.stashItem == null) return 1;
/*      */     
/*  869 */     return this.stashItem.getItemSkillLevel();
/*      */   }
/*      */   
/*      */   public int getRequiredLevel() {
/*  873 */     if (this.stashItem == null) return 0;
/*      */     
/*  875 */     return this.stashItem.getRequiredlevel();
/*      */   }
/*      */   
/*      */   public int getRequiredCunning() {
/*  879 */     return this.reqDex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRequiredPhysique() {
/*  887 */     return this.reqStr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRequiredSpirit() {
/*  895 */     return this.reqInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getOverlayColor() {
/*  903 */     if (this.dbItem == null) return null;
/*      */     
/*  905 */     Color color = COLOR_OVERLAY_COMMON;
/*      */     
/*  907 */     String rarity = getRarity();
/*      */     
/*  909 */     if (rarity == null) return color;
/*      */     
/*  911 */     if (rarity.equals("Magical")) color = COLOR_OVERLAY_MAGICAL; 
/*  912 */     if (rarity.equals("Rare")) color = COLOR_OVERLAY_RARE; 
/*  913 */     if (rarity.equals("Epic")) color = COLOR_OVERLAY_EPIC; 
/*  914 */     if (rarity.equals("Legendary")) color = COLOR_OVERLAY_LEGENDARY;
/*      */     
/*  916 */     if (this.dbItem.isArtifact()) color = COLOR_OVERLAY_ARTIFACT; 
/*  917 */     if (this.dbItem.isComponent()) color = COLOR_OVERLAY_COMPONENT; 
/*  918 */     if (this.dbItem.isEnchantment()) color = COLOR_OVERLAY_ENCHANTMENT; 
/*  919 */     if (this.dbItem.isQuestItem()) color = COLOR_OVERLAY_QUEST;
/*      */     
/*  921 */     return color;
/*      */   }
/*      */   
/*      */   public BufferedImage getFullImage() {
/*  925 */     if (this.dbItem == null) return null;
/*      */     
/*  927 */     BufferedImage img = null;
/*      */     
/*  929 */     if (this.dbItem.getComponentPieces() > 0 && getVar1() < this.dbItem.getComponentPieces()) {
/*  930 */       img = this.dbItem.getShardImage();
/*      */     } else {
/*  932 */       img = this.dbItem.getImage();
/*      */     } 
/*      */     
/*  935 */     if (img == null) return null;
/*      */ 
/*      */     
/*  938 */     BufferedImage clone = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
/*  939 */     Graphics2D g = clone.createGraphics();
/*      */ 
/*      */     
/*  942 */     g.drawImage(img, 0, 0, (ImageObserver)null);
/*  943 */     img = clone;
/*      */     
/*  945 */     BufferedImage overlay = getOverlayImage();
/*      */     
/*  947 */     if (overlay != null) {
/*  948 */       int w = img.getWidth();
/*  949 */       int h = img.getHeight();
/*  950 */       int wo = overlay.getWidth();
/*  951 */       int ho = overlay.getHeight();
/*      */       
/*  953 */       g.drawImage(overlay, w - wo - 2, h - ho - 2, (ImageObserver)null);
/*      */     } 
/*      */     
/*  956 */     if (this.dbItem.getComponentPieces() > 0 && getVar1() < this.dbItem.getComponentPieces()) {
/*  957 */       String s = getVar1Str();
/*      */       
/*  959 */       Font font = UIManager.getDefaults().getFont("Label.font");
/*  960 */       font = font.deriveFont(1, 10.0F);
/*      */       
/*  962 */       g.setFont(font);
/*      */       
/*  964 */       int h = g.getFontMetrics().getAscent();
/*      */       
/*  966 */       int xPos = 4;
/*  967 */       int yPos = h + 2;
/*      */       
/*  969 */       g.setPaint(Color.BLACK);
/*  970 */       g.drawString(s, xPos - 1, yPos - 1);
/*  971 */       g.setPaint(Color.BLACK);
/*  972 */       g.drawString(s, xPos - 1, yPos + 1);
/*  973 */       g.setPaint(Color.BLACK);
/*  974 */       g.drawString(s, xPos + 1, yPos - 1);
/*  975 */       g.setPaint(Color.BLACK);
/*  976 */       g.drawString(s, xPos + 1, yPos + 1);
/*      */       
/*  978 */       g.setPaint(Color.WHITE);
/*  979 */       g.drawString(s, xPos, yPos);
/*      */     } 
/*      */     
/*  982 */     if (getStackCount() > 1) {
/*  983 */       String s = getStackCountStr();
/*      */       
/*  985 */       Font font = UIManager.getDefaults().getFont("Label.font");
/*  986 */       font = font.deriveFont(1, 10.0F);
/*      */       
/*  988 */       g.setFont(font);
/*  989 */       int w = g.getFontMetrics().stringWidth(s);
/*  990 */       int h = g.getFontMetrics().getDescent();
/*      */       
/*  992 */       int xPos = img.getWidth() - w - 4;
/*  993 */       int yPos = img.getHeight() - h - 2;
/*      */       
/*  995 */       g.setPaint(Color.BLACK);
/*  996 */       g.drawString(s, xPos - 1, yPos - 1);
/*  997 */       g.setPaint(Color.BLACK);
/*  998 */       g.drawString(s, xPos - 1, yPos + 1);
/*  999 */       g.setPaint(Color.BLACK);
/* 1000 */       g.drawString(s, xPos + 1, yPos - 1);
/* 1001 */       g.setPaint(Color.BLACK);
/* 1002 */       g.drawString(s, xPos + 1, yPos + 1);
/*      */       
/* 1004 */       g.setPaint(Color.WHITE);
/* 1005 */       g.drawString(s, xPos, yPos);
/*      */     } 
/*      */     
/* 1008 */     return img;
/*      */   }
/*      */   
/*      */   public BufferedImage getImage() {
/* 1012 */     if (this.dbItem == null) return null;
/*      */     
/* 1014 */     return this.dbItem.getImage();
/*      */   }
/*      */   
/*      */   public BufferedImage getOverlayImage() {
/* 1018 */     if (this.dbComponent == null) return null;
/*      */     
/* 1020 */     BufferedImage img = null;
/*      */     
/* 1022 */     if (this.dbComponent.getComponentPieces() > 0 && getVar1() < this.dbComponent.getComponentPieces()) {
/* 1023 */       img = this.dbComponent.getOverlayShard();
/*      */     } else {
/* 1025 */       img = this.dbComponent.getOverlayImage();
/*      */     } 
/*      */     
/* 1028 */     return img;
/*      */   }
/*      */   
/*      */   public ImageIcon getImageIcon() {
/* 1032 */     ImageIcon icon = null;
/* 1033 */     BufferedImage image = getFullImage();
/*      */     
/* 1035 */     if (image != null) icon = new ImageIcon(image);
/*      */     
/* 1037 */     return icon;
/*      */   }
/*      */   
/*      */   public int getPlusAllSkills() {
/* 1041 */     if (this.dbItem == null) return 0;
/*      */     
/* 1043 */     return this.dbItem.getPlusAllSkills();
/*      */   }
/*      */   
/*      */   public String getMainDamageType() {
/* 1047 */     if (this.dbItem == null) return null;
/*      */     
/* 1049 */     return this.dbItem.getMainDamageType();
/*      */   }
/*      */   
/*      */   public String getFullDescription() {
/* 1053 */     if (this.dbItem == null) return null;
/*      */     
/* 1055 */     ItemInfo info = compactBonuses();
/*      */     
/* 1057 */     return DetailComposer.getItemText(this, info);
/*      */   }
/*      */   
/*      */   public String getItemDescription() {
/* 1061 */     if (this.dbItem == null) return null;
/*      */     
/* 1063 */     return this.dbItem.getItemDescription();
/*      */   }
/*      */   
/*      */   private String getSlots() {
/* 1067 */     if (this.dbItem == null) return ""; 
/* 1068 */     if (!this.dbItem.usesSlots()) return "";
/*      */     
/* 1070 */     ItemSlots slots = this.dbItem.getSlots();
/*      */     
/* 1072 */     String allSlots = null;
/* 1073 */     String singleSlot = null;
/*      */     
/* 1075 */     if (slots.usesAllArmor()) {
/* 1076 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_ALL", null);
/*      */       
/* 1078 */       if (allSlots == null) {
/* 1079 */         allSlots = singleSlot;
/*      */       } else {
/* 1081 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */       
/* 1084 */       slots.clearAllArmor();
/*      */     } 
/*      */     
/* 1087 */     if (slots.usesAllWeapons()) {
/* 1088 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_ALL", null);
/*      */       
/* 1090 */       if (allSlots == null) {
/* 1091 */         allSlots = singleSlot;
/*      */       } else {
/* 1093 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */       
/* 1096 */       slots.clearAllWeapons();
/*      */     } 
/*      */     
/* 1099 */     if (slots.usesMeleeWeapons()) {
/* 1100 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_MELEE", null);
/*      */       
/* 1102 */       if (allSlots == null) {
/* 1103 */         allSlots = singleSlot;
/*      */       } else {
/* 1105 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */       
/* 1108 */       slots.clearMeleeWeapons();
/*      */     } 
/*      */     
/* 1111 */     if (slots.uses1HWeapons()) {
/* 1112 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_ONE_HAND", null);
/*      */       
/* 1114 */       if (allSlots == null) {
/* 1115 */         allSlots = singleSlot;
/*      */       } else {
/* 1117 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */       
/* 1120 */       slots.clear1HWeapons();
/*      */     } 
/*      */     
/* 1123 */     if (slots.uses2HWeapons()) {
/* 1124 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_TWO_HAND", null);
/*      */       
/* 1126 */       if (allSlots == null) {
/* 1127 */         allSlots = singleSlot;
/*      */       } else {
/* 1129 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */       
/* 1132 */       slots.clear2HWeapons();
/*      */     } 
/*      */     
/* 1135 */     if (slots.usesRangedWeapons()) {
/* 1136 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_RANGED", null);
/*      */       
/* 1138 */       if (allSlots == null) {
/* 1139 */         allSlots = singleSlot;
/*      */       } else {
/* 1141 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */       
/* 1144 */       slots.clearRangedWeapons();
/*      */     } 
/*      */     
/* 1147 */     if (slots.slotAxe1H) {
/* 1148 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_AXE", null);
/*      */       
/* 1150 */       if (allSlots == null) {
/* 1151 */         allSlots = singleSlot;
/*      */       } else {
/* 1153 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1157 */     if (slots.slotDagger1H) {
/* 1158 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_DAGGER", null);
/*      */       
/* 1160 */       if (allSlots == null) {
/* 1161 */         allSlots = singleSlot;
/*      */       } else {
/* 1163 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1167 */     if (slots.slotMace1H) {
/* 1168 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_MACE", null);
/*      */       
/* 1170 */       if (allSlots == null) {
/* 1171 */         allSlots = singleSlot;
/*      */       } else {
/* 1173 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1177 */     if (slots.slotScepter1H) {
/* 1178 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_SCEPTER", null);
/*      */       
/* 1180 */       if (allSlots == null) {
/* 1181 */         allSlots = singleSlot;
/*      */       } else {
/* 1183 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1187 */     if (slots.slotSpear1H) {
/* 1188 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_SPEAR", null);
/*      */       
/* 1190 */       if (allSlots == null) {
/* 1191 */         allSlots = singleSlot;
/*      */       } else {
/* 1193 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1197 */     if (slots.slotStaff2H) {
/* 1198 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_STAFF", null);
/*      */       
/* 1200 */       if (allSlots == null) {
/* 1201 */         allSlots = singleSlot;
/*      */       } else {
/* 1203 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1207 */     if (slots.slotSword1H) {
/* 1208 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_SWORD", null);
/*      */       
/* 1210 */       if (allSlots == null) {
/* 1211 */         allSlots = singleSlot;
/*      */       } else {
/* 1213 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1217 */     if (slots.slotShield) {
/* 1218 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_SHIELD", null);
/*      */       
/* 1220 */       if (allSlots == null) {
/* 1221 */         allSlots = singleSlot;
/*      */       } else {
/* 1223 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1227 */     if (slots.slotOffhand) {
/* 1228 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_WEAPON_OFFHAND", null);
/*      */       
/* 1230 */       if (allSlots == null) {
/* 1231 */         allSlots = singleSlot;
/*      */       } else {
/* 1233 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1237 */     if (slots.slotHead) {
/* 1238 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_HEAD", null);
/*      */       
/* 1240 */       if (allSlots == null) {
/* 1241 */         allSlots = singleSlot;
/*      */       } else {
/* 1243 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1247 */     if (slots.slotShoulders) {
/* 1248 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_SHOULDERS", null);
/*      */       
/* 1250 */       if (allSlots == null) {
/* 1251 */         allSlots = singleSlot;
/*      */       } else {
/* 1253 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1257 */     if (slots.slotChest) {
/* 1258 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_CHEST", null);
/*      */       
/* 1260 */       if (allSlots == null) {
/* 1261 */         allSlots = singleSlot;
/*      */       } else {
/* 1263 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1267 */     if (slots.slotHands) {
/* 1268 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_HANDS", null);
/*      */       
/* 1270 */       if (allSlots == null) {
/* 1271 */         allSlots = singleSlot;
/*      */       } else {
/* 1273 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1277 */     if (slots.slotLegs) {
/* 1278 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_LEGS", null);
/*      */       
/* 1280 */       if (allSlots == null) {
/* 1281 */         allSlots = singleSlot;
/*      */       } else {
/* 1283 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1287 */     if (slots.slotFeet) {
/* 1288 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_FEET", null);
/*      */       
/* 1290 */       if (allSlots == null) {
/* 1291 */         allSlots = singleSlot;
/*      */       } else {
/* 1293 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1297 */     if (slots.slotBelt) {
/* 1298 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_BELT", null);
/*      */       
/* 1300 */       if (allSlots == null) {
/* 1301 */         allSlots = singleSlot;
/*      */       } else {
/* 1303 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1307 */     if (slots.slotRing) {
/* 1308 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_RING", null);
/*      */       
/* 1310 */       if (allSlots == null) {
/* 1311 */         allSlots = singleSlot;
/*      */       } else {
/* 1313 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1317 */     if (slots.slotAmulet) {
/* 1318 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_AMULET", null);
/*      */       
/* 1320 */       if (allSlots == null) {
/* 1321 */         allSlots = singleSlot;
/*      */       } else {
/* 1323 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1327 */     if (slots.slotMedal) {
/* 1328 */       singleSlot = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SLOT_ARMOR_MEDAL", null);
/*      */       
/* 1330 */       if (allSlots == null) {
/* 1331 */         allSlots = singleSlot;
/*      */       } else {
/* 1333 */         allSlots = allSlots + ", " + singleSlot;
/*      */       } 
/*      */     } 
/*      */     
/* 1337 */     if (allSlots == null) return "";
/*      */     
/* 1339 */     Object[] args = { allSlots };
/*      */     
/* 1341 */     allSlots = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_USED_IN", args);
/*      */     
/* 1343 */     allSlots = "<br>" + GDColor.HTML_COLOR_USED_SLOTS + allSlots + "</font>";
/*      */     
/* 1345 */     return allSlots;
/*      */   }
/*      */   
/*      */   public String getItemCategory() {
/* 1349 */     String tagRarity = determineRarityTag();
/* 1350 */     String tagCategory = determineCategoryTag();
/* 1351 */     String tagItemType = determineItemTypeTag();
/*      */     
/* 1353 */     if (tagItemType == null) return null;
/*      */     
/* 1355 */     if (tagItemType.equals("TYPE_ITEM_COMPONENT") && 
/* 1356 */       this.stashItem.getVar1() < this.dbItem.getComponentPieces()) {
/* 1357 */       tagItemType = "TYPE_ITEM_COMPONENT_PARTIAL";
/*      */     }
/*      */ 
/*      */     
/* 1361 */     String strRarity = null;
/* 1362 */     String strCategory = null;
/* 1363 */     String strItemType = GDMsgFormatter.format(GDMsgFormatter.rbGD, tagItemType, null);
/*      */     
/* 1365 */     if (tagCategory != null) {
/* 1366 */       strCategory = GDMsgFormatter.format(GDMsgFormatter.rbGD, tagCategory, null);
/*      */     }
/*      */     
/* 1369 */     if (tagRarity != null) {
/* 1370 */       strRarity = GDMsgFormatter.format(GDMsgFormatter.rbGD, tagRarity, null);
/*      */     }
/*      */     
/* 1373 */     if (strRarity != null && strCategory != null) {
/* 1374 */       Object[] args = { strRarity, strCategory, strItemType };
/* 1375 */       strItemType = GDMsgFormatter.format(GDMsgFormatter.rbGD, "COMB_RARITY_CATEGORY_ITEM", args);
/*      */     } 
/*      */     
/* 1378 */     if (strRarity == null && strCategory != null) {
/* 1379 */       Object[] args = { strCategory, strItemType };
/* 1380 */       strItemType = GDMsgFormatter.format(GDMsgFormatter.rbGD, "COMB_CATEGORY_ITEM", args);
/*      */     } 
/*      */     
/* 1383 */     if (strRarity != null && strCategory == null) {
/* 1384 */       Object[] args = { strRarity, strItemType };
/* 1385 */       strItemType = GDMsgFormatter.format(GDMsgFormatter.rbGD, "COMB_RARITY_ITEM", args);
/*      */     } 
/*      */     
/* 1388 */     if (isSoulbound()) {
/* 1389 */       Object[] args = { strItemType };
/* 1390 */       strItemType = GDMsgFormatter.format(GDMsgFormatter.rbGD, "COMB_SOULBOUND_ITEM", args);
/*      */     } 
/*      */     
/* 1393 */     if (getItemSetName() != null) {
/* 1394 */       strItemType = strItemType + "<br>" + "[" + getItemSetName() + "]";
/*      */     }
/*      */     
/* 1397 */     strItemType = GDColor.HTML_COLOR_ITEM_TYPE + strItemType + "</font>";
/*      */     
/* 1399 */     String slots = getSlots();
/*      */     
/* 1401 */     if (slots != null) strItemType = strItemType + slots;
/*      */     
/* 1403 */     return strItemType;
/*      */   }
/*      */ 
/*      */   
/*      */   private Color getBaseItemColor() {
/* 1408 */     Color color = GDColor.COLOR_FG_COMMON;
/*      */     
/* 1410 */     if (this.dbItem != null) {
/* 1411 */       String rarity = this.dbItem.getRarity();
/*      */       
/* 1413 */       if (rarity != null) {
/* 1414 */         color = ItemClass.getRarityColor(rarity);
/*      */       }
/*      */       
/* 1417 */       if (this.dbItem.isQuestItem()) color = GDColor.COLOR_FG_LEGENDARY;
/*      */     
/*      */     } 
/* 1420 */     return color;
/*      */   }
/*      */   
/*      */   private Color getRarityBackgroundColor() {
/* 1424 */     String rarity = getRarity();
/*      */     
/* 1426 */     Color color = GDColor.COLOR_BG_COMMON;
/*      */     
/* 1428 */     if (rarity != null) {
/* 1429 */       color = ItemClass.getRarityBackgroundColor(rarity);
/*      */     }
/*      */     
/* 1432 */     if (this.dbItem != null && 
/* 1433 */       this.dbItem.isQuestItem()) color = GDColor.COLOR_BG_LEGENDARY;
/*      */ 
/*      */     
/* 1436 */     return color;
/*      */   }
/*      */ 
/*      */   
/*      */   private Color getItemColor() {
/* 1441 */     Color color = GDColor.COLOR_FG_COMMON;
/*      */     
/* 1443 */     String rarity = getRarity();
/*      */     
/* 1445 */     if (rarity != null) {
/* 1446 */       color = ItemClass.getRarityColor(rarity);
/*      */     }
/*      */     
/* 1449 */     if (this.dbItem != null && 
/* 1450 */       this.dbItem.isQuestItem()) color = GDColor.COLOR_FG_LEGENDARY;
/*      */ 
/*      */     
/* 1453 */     return color;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValid(int affixCombi, boolean completionAll) {
/* 1461 */     if (this.dbItem == null) return false; 
/* 1462 */     if (affixCombi == 3) return true;
/*      */     
/* 1464 */     boolean validCombo = isValidAffixCombo(affixCombi);
/* 1465 */     boolean validComponent = isValidComponent();
/* 1466 */     boolean validEnchantment = isValidEnchantment();
/* 1467 */     boolean validBonus = isValidBonus(completionAll);
/*      */     
/* 1469 */     boolean valid = (validCombo && validComponent && validEnchantment && validBonus);
/*      */     
/* 1471 */     return valid;
/*      */   }
/*      */   
/*      */   private boolean isValidAffixCombo(int affixCombi) {
/* 1475 */     if (this.dbItem == null) return false; 
/* 1476 */     if (affixCombi == 3) return true; 
/* 1477 */     if (this.dbPrefix == null && this.dbSuffix == null) return true;
/*      */ 
/*      */     
/* 1480 */     List<DBLootTable> tables = DBLootTable.getByItemID(this.dbItem.getItemID());
/*      */     
/* 1482 */     if (tables == null) {
/* 1483 */       if (this.dbPrefix != null || this.dbSuffix != null) return false;
/*      */       
/* 1485 */       return true;
/*      */     } 
/*      */     
/* 1488 */     boolean validCombo = false;
/* 1489 */     for (DBLootTable table : tables) {
/*      */       
/* 1491 */       boolean foundPrefix = (this.dbPrefix == null);
/* 1492 */       int rarePrefix = 6;
/* 1493 */       boolean foundSuffix = (this.dbSuffix == null);
/* 1494 */       int rareSuffix = 6;
/*      */       
/* 1496 */       List<DBLootTableAffixSetAlloc> affixSets = table.getAffixSetAllocList();
/* 1497 */       if (affixSets != null) {
/* 1498 */         for (DBLootTableAffixSetAlloc alloc : affixSets) {
/* 1499 */           int type = alloc.getAffixType();
/* 1500 */           List<DBAffixSet.DBEntry> list = alloc.getAffixEntries();
/*      */           
/* 1502 */           if (list == null)
/*      */             continue; 
/* 1504 */           if (type == 1 || type == 3)
/*      */           {
/* 1506 */             if (this.dbPrefix != null) {
/* 1507 */               for (DBAffixSet.DBEntry entry : list) {
/* 1508 */                 if (entry.getAffixID().equals(this.dbPrefix.getAffixID())) {
/* 1509 */                   foundPrefix = true;
/*      */                   
/* 1511 */                   if (type == 1 && 
/* 1512 */                     rarePrefix > 3) rarePrefix = 3;
/*      */ 
/*      */                   
/* 1515 */                   if (type == 3 && 
/* 1516 */                     rarePrefix > 4) rarePrefix = 4;
/*      */ 
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           }
/*      */           
/* 1525 */           if (type == 2 || type == 4)
/*      */           {
/* 1527 */             if (this.dbSuffix != null) {
/* 1528 */               label93: for (DBAffixSet.DBEntry entry : list) {
/* 1529 */                 if (entry.getAffixID().equals(this.dbSuffix.getAffixID())) {
/* 1530 */                   foundSuffix = true;
/*      */                   
/* 1532 */                   if (type == 2) {
/* 1533 */                     if (rareSuffix > 3) { rareSuffix = 3;
/*      */                       break label93; }
/*      */                     
/* 1536 */                     if (type == 4 && 
/* 1537 */                       rareSuffix > 4) rareSuffix = 4;
/*      */                     
/*      */                     continue;
/*      */                   } 
/*      */                   break label93;
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           }
/*      */         } 
/* 1547 */         if (affixCombi == 2)
/*      */         {
/*      */           
/* 1550 */           if (foundPrefix && foundSuffix) validCombo = true;
/*      */         
/*      */         }
/* 1553 */         if (affixCombi == 1)
/*      */         {
/* 1555 */           if (this.dbPrefix != null && this.dbSuffix != null)
/*      */           
/* 1557 */           { if (foundPrefix && foundSuffix) {
/* 1558 */               if (rarePrefix == 3 && rareSuffix == 3)
/*      */               {
/* 1560 */                 validCombo = table.isMagicPrefixMagicSuffixAllowed();
/*      */               }
/*      */               
/* 1563 */               if (rarePrefix == 3 && rareSuffix == 4)
/*      */               {
/* 1565 */                 validCombo = table.isMagicPrefixRareSuffixAllowed();
/*      */               }
/*      */               
/* 1568 */               if (rarePrefix == 4 && rareSuffix == 3)
/*      */               {
/* 1570 */                 validCombo = table.isRarePrefixMagicSuffixAllowed();
/*      */               }
/*      */               
/* 1573 */               if (rarePrefix == 4 && rareSuffix == 4)
/*      */               {
/* 1575 */                 validCombo = table.isRarePrefixRareSuffixAllowed();
/*      */               
/*      */               }
/*      */             }
/*      */              }
/*      */           
/* 1581 */           else if (foundPrefix && foundSuffix) { validCombo = true; }
/*      */         
/*      */         }
/*      */       } 
/*      */       
/* 1586 */       if (validCombo)
/*      */         break; 
/*      */     } 
/* 1589 */     return validCombo;
/*      */   }
/*      */   
/*      */   private boolean isValidSlotItem(DBItem item) {
/* 1593 */     if (this.dbItem == null) return false; 
/* 1594 */     if (item == null) return true;
/*      */     
/* 1596 */     boolean valid = false;
/*      */     
/* 1598 */     String itemClass = this.dbItem.getItemClass();
/*      */     
/* 1600 */     if (itemClass.equals("ArmorProtective_Head")) valid = item.isSlotHead(); 
/* 1601 */     if (itemClass.equals("ArmorProtective_Shoulders")) valid = item.isSlotShoulders(); 
/* 1602 */     if (itemClass.equals("ArmorProtective_Chest")) valid = item.isSlotChest(); 
/* 1603 */     if (itemClass.equals("ArmorProtective_Hands")) valid = item.isSlotHands(); 
/* 1604 */     if (itemClass.equals("ArmorProtective_Waist")) valid = item.isSlotBelt(); 
/* 1605 */     if (itemClass.equals("ArmorProtective_Legs")) valid = item.isSlotLegs(); 
/* 1606 */     if (itemClass.equals("ArmorProtective_Feet")) valid = item.isSlotFeet(); 
/* 1607 */     if (itemClass.equals("ArmorJewelry_Amulet")) valid = item.isSlotAmulet(); 
/* 1608 */     if (itemClass.equals("ArmorJewelry_Medal")) valid = item.isSlotMedal(); 
/* 1609 */     if (itemClass.equals("ArmorJewelry_Ring")) valid = item.isSlotRing(); 
/* 1610 */     if (itemClass.equals("WeaponArmor_Offhand")) valid = item.isSlotOffhand(); 
/* 1611 */     if (itemClass.equals("WeaponArmor_Shield")) valid = item.isSlotShield(); 
/* 1612 */     if (itemClass.equals("WeaponMelee_Axe")) valid = item.isSlotAxe1H(); 
/* 1613 */     if (itemClass.equals("WeaponMelee_Mace")) valid = item.isSlotMace1H(); 
/* 1614 */     if (itemClass.equals("WeaponHunting_Spear")) valid = item.isSlotSpear1H(); 
/* 1615 */     if (itemClass.equals("WeaponMelee_Sword")) valid = item.isSlotSword1H(); 
/* 1616 */     if (itemClass.equals("WeaponMelee_Dagger")) valid = item.isSlotDagger1H(); 
/* 1617 */     if (itemClass.equals("WeaponMelee_Scepter")) valid = item.isSlotScepter1H(); 
/* 1618 */     if (itemClass.equals("WeaponHunting_Ranged1h")) valid = item.isSlotRanged1H(); 
/* 1619 */     if (itemClass.equals("WeaponMelee_Axe2h")) valid = item.isSlotAxe2H(); 
/* 1620 */     if (itemClass.equals("WeaponMelee_Mace2h")) valid = item.isSlotMace2H(); 
/* 1621 */     if (itemClass.equals("WeaponMagical_Staff")) valid = item.isSlotStaff2H(); 
/* 1622 */     if (itemClass.equals("WeaponMelee_Sword2h")) valid = item.isSlotSword2H(); 
/* 1623 */     if (itemClass.equals("WeaponHunting_Ranged2h")) valid = item.isSlotRanged2H();
/*      */     
/* 1625 */     return valid;
/*      */   }
/*      */   
/*      */   private boolean isValidComponent() {
/* 1629 */     return isValidSlotItem(this.dbComponent);
/*      */   }
/*      */   
/*      */   private boolean isValidEnchantment() {
/* 1633 */     return isValidSlotItem(this.dbEnchantment);
/*      */   }
/*      */   
/*      */   private boolean isValidBonus(boolean completionAll) {
/* 1637 */     if (this.dbItem == null) return false; 
/* 1638 */     if (this.dbBonus == null) return true; 
/* 1639 */     if (this.dbComponent == null && 
/* 1640 */       !isComponent() && !isArtifact()) return false;
/*      */     
/* 1642 */     if (completionAll) return true;
/*      */     
/* 1644 */     DBItem component = null;
/*      */     
/* 1646 */     if (isComponent() || isArtifact()) {
/* 1647 */       component = this.dbItem;
/*      */     } else {
/* 1649 */       component = this.dbComponent;
/*      */     } 
/* 1651 */     if (component.getBonusAffixSet() == null) return false;
/*      */     
/* 1653 */     List<DBAffixSet.DBEntry> entries = component.getBonusAffixSet().getAffixEntries();
/* 1654 */     if (entries == null) return false;
/*      */     
/* 1656 */     boolean found = false;
/* 1657 */     for (DBAffixSet.DBEntry entry : entries) {
/* 1658 */       if (entry.getAffixID().equals(this.dbBonus.getAffixID())) {
/* 1659 */         found = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1665 */     return found;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LabelInfo getItemNameInfo() {
/* 1673 */     if (this.dbItem == null) return null;
/*      */     
/* 1675 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1677 */     boolean brackets = false;
/*      */     
/* 1679 */     li.text = parseName();
/*      */     
/* 1681 */     if (this.dbItem.getRequiredlevel() != 0) {
/* 1682 */       li.text += " [" + this.dbItem.getRequiredlevel();
/*      */       
/* 1684 */       brackets = true;
/*      */     } 
/*      */     
/* 1687 */     String dmg = this.dbItem.getMainDamageType();
/*      */     
/* 1689 */     if (dmg != null) {
/* 1690 */       if (brackets) {
/* 1691 */         li.text += ", " + dmg;
/*      */       } else {
/* 1693 */         li.text += " [" + dmg;
/*      */       } 
/*      */       
/* 1696 */       brackets = true;
/*      */     } 
/*      */     
/* 1699 */     if (brackets) li.text += "]";
/*      */     
/* 1701 */     li.foreground = getBaseItemColor();
/*      */ 
/*      */     
/* 1704 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getCompleteNameInfo(boolean inclFaction, boolean inclMod) {
/* 1708 */     if (this.stashItem == null) return null;
/*      */     
/* 1710 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1712 */     li.foreground = getItemColor();
/* 1713 */     li.background = getRarityBackgroundColor();
/* 1714 */     li.text = this.stashItem.getCompleteName(inclFaction, inclMod);
/*      */     
/* 1716 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getPrefixInfo() {
/* 1720 */     if (this.dbPrefix == null) return null;
/*      */     
/* 1722 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1724 */     li.text = this.dbPrefix.getGenderText(this.dbItem.getGenderCode());
/* 1725 */     if (this.dbPrefix.getRequiredlevel() != 0) li.text += " [" + this.dbPrefix.getRequiredlevel() + "]";
/*      */     
/* 1727 */     String rarity = this.dbPrefix.getRarity();
/*      */     
/* 1729 */     if (rarity != null) {
/* 1730 */       if (rarity.equals("Magical")) li.foreground = GDColor.COLOR_FG_MAGICAL; 
/* 1731 */       if (rarity.equals("Rare")) li.foreground = GDColor.COLOR_FG_RARE;
/*      */     
/*      */     } 
/* 1734 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getSuffixInfo() {
/* 1738 */     if (this.dbSuffix == null) return null;
/*      */     
/* 1740 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1742 */     li.text = this.dbSuffix.getGenderText(this.dbItem.getGenderCode());
/* 1743 */     if (this.dbSuffix.getRequiredlevel() != 0) li.text += " [" + this.dbSuffix.getRequiredlevel() + "]";
/*      */     
/* 1745 */     String rarity = this.dbSuffix.getRarity();
/*      */     
/* 1747 */     if (rarity != null) {
/* 1748 */       if (rarity.equals("Magical")) li.foreground = GDColor.COLOR_FG_MAGICAL; 
/* 1749 */       if (rarity.equals("Rare")) li.foreground = GDColor.COLOR_FG_RARE;
/*      */     
/*      */     } 
/* 1752 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getModifierInfo() {
/* 1756 */     if (this.dbModifier == null) return null;
/*      */     
/* 1758 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1760 */     li.text = this.dbModifier.getGenderText(this.dbItem.getGenderCode());
/*      */     
/* 1762 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getComponentInfo() {
/* 1766 */     if (this.dbComponent == null) return null;
/*      */     
/* 1768 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1770 */     li.text = this.dbComponent.getName(false);
/* 1771 */     li.foreground = GDColor.COLOR_FG_COMPONENT;
/*      */     
/* 1773 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getComponentBonusInfo() {
/* 1777 */     if (this.dbBonus == null) return null;
/*      */     
/* 1779 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1781 */     DetailComposer compCompletion = DetailComposer.createComposer(this.stashItem, 1, this.dbBonus.getStatList(), this.dbBonus.getSkillBonusList());
/*      */     
/* 1783 */     String s = DetailComposer.getComposerBonuses(compCompletion, true, true, true);
/*      */     
/* 1785 */     if (s != null) s = "<html>" + s + "</html>";
/*      */     
/* 1787 */     li.text = s;
/* 1788 */     li.foreground = GDColor.COLOR_FG_COMPONENT;
/*      */     
/* 1790 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getEnchantmentInfo() {
/* 1794 */     if (this.dbEnchantment == null) return null;
/*      */     
/* 1796 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1798 */     li.text = this.dbEnchantment.getItemName();
/* 1799 */     li.foreground = GDColor.COLOR_FG_ENCHANTMENT;
/*      */     
/* 1801 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getSeedHexInfo() {
/* 1805 */     if (this.stashItem == null) return null;
/*      */     
/* 1807 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1809 */     li.text = getSeedHex();
/*      */     
/* 1811 */     return li;
/*      */   }
/*      */   
/*      */   public String getItemSetID() {
/* 1815 */     if (this.dbItem == null) return null;
/*      */     
/* 1817 */     return this.dbItem.getItemSetID();
/*      */   }
/*      */   
/*      */   public String getItemSetName() {
/* 1821 */     if (this.dbItem == null) return null;
/*      */     
/* 1823 */     return this.dbItem.getItemSetName();
/*      */   }
/*      */   
/*      */   public LabelInfo getItemSetNameInfo() {
/* 1827 */     if (this.dbItem == null) return null; 
/* 1828 */     if (this.dbItem.getItemSetName() == null) return null;
/*      */     
/* 1830 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1832 */     li.text = this.dbItem.getItemSetName();
/*      */     
/* 1834 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getRequiredLevelInfo() {
/* 1838 */     if (this.dbItem == null) return null;
/*      */     
/* 1840 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1842 */     li.text = Integer.toString(getRequiredLevel());
/*      */     
/* 1844 */     return li;
/*      */   }
/*      */   
/*      */   public LabelInfo getCharNameInfo() {
/* 1848 */     if (this.stashItem == null) return null;
/*      */     
/* 1850 */     LabelInfo li = new LabelInfo();
/*      */     
/* 1852 */     li.text = getCharName();
/*      */     
/* 1854 */     if (li.text == null) li.text = "";
/*      */     
/* 1856 */     return li;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContainerType(int containerType) {
/* 1864 */     this.containerType = containerType;
/*      */     
/* 1866 */     if (containerType > 6) containerType = 0;
/*      */     
/* 1868 */     if (containerType == 6) this.attached = 1; 
/*      */   }
/*      */   
/*      */   public void setPrefix(DBAffix affix) {
/* 1872 */     this.dbPrefix = affix;
/*      */     
/* 1874 */     if (affix == null) {
/* 1875 */       this.stashItem.setPrefixID(null);
/*      */     } else {
/* 1877 */       this.stashItem.setPrefixID(affix.getAffixID());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setSuffix(DBAffix affix) {
/* 1882 */     this.dbSuffix = affix;
/*      */     
/* 1884 */     if (affix == null) {
/* 1885 */       this.stashItem.setSuffixID(null);
/*      */     } else {
/* 1887 */       this.stashItem.setSuffixID(affix.getAffixID());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setModifier(DBAffix affix) {
/* 1892 */     this.dbModifier = affix;
/*      */     
/* 1894 */     if (affix == null) {
/* 1895 */       this.stashItem.setModifierID(null);
/*      */     } else {
/* 1897 */       this.stashItem.setModifierID(affix.getAffixID());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setCompletionBonus(DBAffix affix) {
/* 1902 */     this.dbBonus = affix;
/*      */     
/* 1904 */     if (affix == null) {
/* 1905 */       this.stashItem.setCompletionBonusID(null);
/*      */     } else {
/* 1907 */       this.stashItem.setCompletionBonusID(affix.getAffixID());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setSeedHex(String s) {
/* 1912 */     if (s.length() != 8) {
/* 1913 */       createSeed();
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1918 */     byte[] bytes = new byte[4];
/* 1919 */     for (int i = 0; i < s.length(); i += 2) {
/* 1920 */       char c1 = s.charAt(i);
/* 1921 */       char c2 = s.charAt(i + 1);
/*      */       
/* 1923 */       byte b1 = hexDigitToByte(c1);
/* 1924 */       byte b2 = hexDigitToByte(c2);
/* 1925 */       byte b = (byte)(b1 * 16 + b2);
/*      */ 
/*      */       
/* 1928 */       bytes[bytes.length - 1 - i / 2] = b;
/*      */     } 
/*      */     
/* 1931 */     this.stashItem.setItemSeed(GDReader.getInt(bytes, 0));
/*      */   }
/*      */   
/*      */   public void setVar1(int var1) {
/* 1935 */     if (this.stashItem == null)
/*      */       return; 
/* 1937 */     int temp = 0;
/* 1938 */     if (var1 > 0) temp = var1;
/*      */     
/* 1940 */     this.stashItem.setVar1(temp);
/*      */   }
/*      */   
/*      */   public void setVar1Str(String var1) {
/* 1944 */     if (this.stashItem == null)
/*      */       return; 
/* 1946 */     int i = 0;
/*      */     try {
/* 1948 */       i = Integer.parseInt(var1);
/*      */     }
/* 1950 */     catch (NumberFormatException ex) {
/* 1951 */       i = 0;
/*      */     } 
/*      */     
/* 1954 */     setVar1(i);
/*      */   }
/*      */   
/*      */   public void setStackCount(int stackCount) {
/* 1958 */     if (this.stashItem == null)
/*      */       return; 
/* 1960 */     this.stashItem.setStackCount(stackCount);
/*      */   }
/*      */   
/*      */   public void setStackCountStr(String stackCount) {
/* 1964 */     if (this.stashItem == null)
/*      */       return; 
/* 1966 */     int i = 0;
/*      */     try {
/* 1968 */       i = Integer.parseInt(stackCount);
/*      */     }
/* 1970 */     catch (NumberFormatException ex) {
/* 1971 */       i = 0;
/*      */     } 
/*      */     
/* 1974 */     setStackCount(i);
/*      */   }
/*      */   
/*      */   public void setX(int x) {
/* 1978 */     this.x = x;
/* 1979 */     this.xPos = x;
/*      */   }
/*      */   
/*      */   public void setY(int y) {
/* 1983 */     this.y = y;
/* 1984 */     this.yPos = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void read() {
/* 1992 */     GDLog log = new GDLog();
/*      */     
/* 1994 */     readNewFormat(4, 0, log);
/*      */     
/* 1996 */     init();
/*      */     
/* 1998 */     this.error = log.containsErrors();
/*      */     
/* 2000 */     GDMsgLogger.addLog(log);
/*      */     
/* 2002 */     if (!this.error) {
/* 2003 */       loadData(0);
/*      */     }
/*      */   }
/*      */   
/*      */   private void readNewFormat(int version, int page, GDLog log) {
/* 2008 */     switch (version) {
/*      */       case 3:
/* 2010 */         readNewFormat_V3(page, log);
/*      */         return;
/*      */       
/*      */       case 4:
/* 2014 */         readNewFormat_V4(log);
/*      */         return;
/*      */       
/*      */       case 5:
/* 2018 */         readNewFormat_V4(log);
/*      */         return;
/*      */     } 
/*      */     
/* 2022 */     this.error = true;
/*      */   }
/*      */ 
/*      */   
/*      */   private void readNewFormat_V3(int page, GDLog log) {
/*      */     try {
/* 2028 */       this.stashItem.readNewFormat(3);
/*      */       
/* 2030 */       this.xPos = GDReader.readEncFloat(true);
/* 2031 */       this.x = (int)this.xPos;
/*      */       
/* 2033 */       this.yPos = GDReader.readEncFloat(true);
/* 2034 */       this.y = (int)this.yPos;
/*      */     }
/* 2036 */     catch (IOException ex) {
/* 2037 */       if (this.stashItem.getItemID() != null) {
/* 2038 */         Object[] args = { this.filename, this.stashItem.getItemID() };
/* 2039 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_ITEM", args);
/*      */         
/* 2041 */         log.addError(msg);
/*      */       } else {
/* 2043 */         Object[] args = { this.filename };
/* 2044 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_ITEM_UNKNOWN", args);
/*      */         
/* 2046 */         log.addError(msg);
/*      */       } 
/*      */       
/* 2049 */       this.error = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readNewFormat_V4(GDLog log) {
/*      */     try {
/* 2055 */       this.stashItem.readNewFormat(4);
/*      */       
/* 2057 */       if (this.containerType == 1 || this.containerType == 2 || this.containerType == 3) {
/*      */ 
/*      */         
/* 2060 */         this.xPos = GDReader.readEncFloat(true);
/* 2061 */         this.x = (int)this.xPos;
/*      */         
/* 2063 */         this.yPos = GDReader.readEncFloat(true);
/* 2064 */         this.y = (int)this.yPos;
/*      */       } 
/*      */       
/* 2067 */       if (this.containerType == 4) {
/* 2068 */         this.x = GDReader.readEncInt(true);
/* 2069 */         this.xPos = this.x;
/*      */         
/* 2071 */         this.y = GDReader.readEncInt(true);
/* 2072 */         this.yPos = this.y;
/*      */       } 
/*      */       
/* 2075 */       if (this.containerType == 6) {
/* 2076 */         this.attached = GDReader.readEncByte();
/*      */       }
/*      */     }
/* 2079 */     catch (IOException ex) {
/* 2080 */       if (this.stashItem.getItemID() != null) {
/* 2081 */         Object[] args = { this.filename, this.stashItem.getItemID() };
/* 2082 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_ITEM", args);
/*      */         
/* 2084 */         log.addError(msg);
/*      */       } else {
/* 2086 */         Object[] args = { this.filename };
/* 2087 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_ITEM_UNKNOWN", args);
/*      */         
/* 2089 */         log.addError(msg);
/*      */       } 
/*      */       
/* 2092 */       this.error = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readOldFormat(GDLog log) {
/* 2097 */     int len = 0;
/*      */     
/*      */     try {
/* 2100 */       while (len != -1) {
/* 2101 */         len = GDReader.readUInt(this.reader);
/*      */         
/* 2103 */         if (len != -1) {
/* 2104 */           String s = GDReader.readString(this.reader, len);
/*      */           
/* 2106 */           if (s.equals("stackCount")) {
/* 2107 */             this.stashItem.setStackCount(GDReader.readInt(this.reader));
/*      */           }
/* 2109 */           if (s.equals("begin_block")) {
/* 2110 */             this.beginBlock = GDReader.readBytes4(this.reader);
/*      */           }
/* 2112 */           if (s.equals("baseName")) {
/* 2113 */             len = GDReader.readUInt(this.reader);
/* 2114 */             if (len > 0) this.stashItem.setItemID(GDReader.readString(this.reader, len)); 
/*      */           } 
/* 2116 */           if (s.equals("prefixName")) {
/* 2117 */             len = GDReader.readUInt(this.reader);
/* 2118 */             if (len > 0) this.stashItem.setPrefixID(GDReader.readString(this.reader, len)); 
/*      */           } 
/* 2120 */           if (s.equals("suffixName")) {
/* 2121 */             len = GDReader.readUInt(this.reader);
/* 2122 */             if (len > 0) this.stashItem.setSuffixID(GDReader.readString(this.reader, len)); 
/*      */           } 
/* 2124 */           if (s.equals("modifierName")) {
/* 2125 */             len = GDReader.readUInt(this.reader);
/* 2126 */             if (len > 0) this.stashItem.setModifierID(GDReader.readString(this.reader, len)); 
/*      */           } 
/* 2128 */           if (s.equals("relicName")) {
/* 2129 */             len = GDReader.readUInt(this.reader);
/* 2130 */             if (len > 0) this.stashItem.setComponentID(GDReader.readString(this.reader, len)); 
/*      */           } 
/* 2132 */           if (s.equals("relicBonus")) {
/* 2133 */             len = GDReader.readUInt(this.reader);
/* 2134 */             if (len > 0) this.stashItem.setCompletionBonusID(GDReader.readString(this.reader, len)); 
/*      */           } 
/* 2136 */           if (s.equals("seed")) {
/* 2137 */             this.stashItem.setItemSeed(GDReader.readInt(this.reader));
/*      */             
/* 2139 */             if (this.stashItem.getItemSeed() == 0) {
/* 2140 */               Object[] args = { this.filename, this.stashItem.getItemID() };
/* 2141 */               String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_NO_SEED", args);
/*      */               
/* 2143 */               GDMsgLogger.addWarning(msg);
/*      */             } 
/*      */           } 
/* 2146 */           if (s.equals("relicSeed")) {
/* 2147 */             this.stashItem.setComponentSeed(GDReader.readInt(this.reader));
/*      */           }
/* 2149 */           if (s.equals("var1")) {
/* 2150 */             this.stashItem.setVar1(GDReader.readUInt(this.reader));
/*      */           }
/* 2152 */           if (s.equals("velocity")) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2157 */             int seed = GDReader.readUInt(this.reader);
/*      */             
/* 2159 */             if (this.stashItem.getComponentID() != null && 
/* 2160 */               !this.stashItem.getComponentID().isEmpty()) {
/* 2161 */               this.stashItem.setComponentSeed(seed);
/*      */             }
/*      */             
/* 2164 */             GDReader.readUInt(this.reader);
/* 2165 */             GDReader.readUInt(this.reader);
/*      */           } 
/* 2167 */           if (s.equals("owner"))
/*      */           {
/* 2169 */             GDReader.readUInt(this.reader);
/*      */           }
/* 2171 */           if (s.equals("end_block")) {
/* 2172 */             this.endBlock = GDReader.readBytes4(this.reader);
/*      */           }
/* 2174 */           if (s.equals("xOffset")) {
/* 2175 */             this.xPos = GDReader.readFloat(this.reader);
/* 2176 */             this.x = (int)this.xPos;
/*      */           } 
/* 2178 */           if (s.equals("yOffset")) {
/* 2179 */             this.yPos = GDReader.readFloat(this.reader);
/* 2180 */             this.y = (int)this.yPos;
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2188 */       this.stashItem.fillDependentStats(null);
/*      */     }
/* 2190 */     catch (IOException ex) {
/* 2191 */       if (this.stashItem.getItemID() != null) {
/* 2192 */         Object[] args = { this.filename, this.stashItem.getItemID() };
/* 2193 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_ITEM", args);
/*      */         
/* 2195 */         log.addError(msg);
/*      */       } else {
/* 2197 */         Object[] args = { this.filename };
/* 2198 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_ITEM_UNKNOWN", args);
/*      */         
/* 2200 */         log.addError(msg);
/*      */       } 
/*      */       
/* 2203 */       this.error = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getByteSize() {
/* 2208 */     int size = this.stashItem.getByteSize();
/*      */     
/* 2210 */     if (this.containerType == 1 || this.containerType == 2 || this.containerType == 3) {
/*      */ 
/*      */       
/* 2213 */       size += 4;
/* 2214 */       size += 4;
/*      */     } 
/*      */     
/* 2217 */     if (this.containerType == 4 || this.containerType == 5) {
/*      */       
/* 2219 */       size += 4;
/* 2220 */       size += 4;
/*      */     } 
/*      */     
/* 2223 */     if (this.containerType == 6) {
/* 2224 */       size++;
/*      */     }
/*      */     
/* 2227 */     return size;
/*      */   }
/*      */   
/*      */   public void write() throws IOException {
/* 2231 */     this.stashItem.write();
/*      */     
/* 2233 */     if (this.containerType == 1 || this.containerType == 2 || this.containerType == 3) {
/*      */ 
/*      */       
/* 2236 */       GDWriter.writeFloat(this.xPos);
/* 2237 */       GDWriter.writeFloat(this.yPos);
/*      */     } 
/*      */     
/* 2240 */     if (this.containerType == 4 || this.containerType == 5) {
/*      */       
/* 2242 */       GDWriter.writeInt(this.x);
/* 2243 */       GDWriter.writeInt(this.y);
/*      */     } 
/*      */     
/* 2246 */     if (this.containerType == 6) {
/* 2247 */       GDWriter.writeByte(this.attached);
/*      */     }
/*      */   }
/*      */   
/*      */   public void writeOldFormat(FileOutputStream writer, Charset cs) throws IOException {
/* 2252 */     writer.write(GDWriter.lengthToBytes4("stackCount"));
/* 2253 */     writer.write("stackCount".getBytes(cs));
/* 2254 */     writer.write(GDWriter.intToBytes4(this.stashItem.getStackCount()));
/*      */     
/* 2256 */     writer.write(GDWriter.lengthToBytes4("begin_block"));
/* 2257 */     writer.write("begin_block".getBytes(cs));
/* 2258 */     writer.write(this.beginBlock);
/*      */     
/* 2260 */     writer.write(GDWriter.lengthToBytes4("baseName"));
/* 2261 */     writer.write("baseName".getBytes(cs));
/* 2262 */     writer.write(GDWriter.lengthToBytes4(this.stashItem.getItemID()));
/* 2263 */     if (this.stashItem.getItemID() != null) writer.write(this.stashItem.getItemID().getBytes(cs));
/*      */     
/* 2265 */     writer.write(GDWriter.lengthToBytes4("prefixName"));
/* 2266 */     writer.write("prefixName".getBytes(cs));
/* 2267 */     writer.write(GDWriter.lengthToBytes4(this.stashItem.getPrefixID()));
/* 2268 */     if (this.stashItem.getPrefixID() != null) writer.write(this.stashItem.getPrefixID().getBytes(cs));
/*      */     
/* 2270 */     writer.write(GDWriter.lengthToBytes4("suffixName"));
/* 2271 */     writer.write("suffixName".getBytes(cs));
/* 2272 */     writer.write(GDWriter.lengthToBytes4(this.stashItem.getSuffixID()));
/* 2273 */     if (this.stashItem.getSuffixID() != null) writer.write(this.stashItem.getSuffixID().getBytes(cs));
/*      */     
/* 2275 */     writer.write(GDWriter.lengthToBytes4("modifierName"));
/* 2276 */     writer.write("modifierName".getBytes(cs));
/* 2277 */     writer.write(GDWriter.lengthToBytes4(this.stashItem.getModifierID()));
/* 2278 */     if (this.stashItem.getModifierID() != null) writer.write(this.stashItem.getModifierID().getBytes(cs));
/*      */     
/* 2280 */     writer.write(GDWriter.lengthToBytes4("relicName"));
/* 2281 */     writer.write("relicName".getBytes(cs));
/* 2282 */     writer.write(GDWriter.lengthToBytes4(this.stashItem.getComponentID()));
/* 2283 */     if (this.stashItem.getComponentID() != null) writer.write(this.stashItem.getComponentID().getBytes(cs));
/*      */     
/* 2285 */     writer.write(GDWriter.lengthToBytes4("relicBonus"));
/* 2286 */     writer.write("relicBonus".getBytes(cs));
/* 2287 */     writer.write(GDWriter.lengthToBytes4(this.stashItem.getCompletionBonusID()));
/* 2288 */     if (this.stashItem.getCompletionBonusID() != null) writer.write(this.stashItem.getCompletionBonusID().getBytes(cs));
/*      */     
/* 2290 */     writer.write(GDWriter.lengthToBytes4("seed"));
/* 2291 */     writer.write("seed".getBytes(cs));
/* 2292 */     writer.write(GDWriter.intToBytes4(this.stashItem.getItemSeed()));
/*      */     
/* 2294 */     writer.write(GDWriter.lengthToBytes4("relicSeed"));
/* 2295 */     writer.write("relicSeed".getBytes(cs));
/* 2296 */     writer.write(GDWriter.intToBytes4(this.stashItem.getComponentSeed()));
/*      */     
/* 2298 */     writer.write(GDWriter.lengthToBytes4("var1"));
/* 2299 */     writer.write("var1".getBytes(cs));
/* 2300 */     writer.write(GDWriter.intToBytes4(this.stashItem.getVar1()));
/*      */     
/* 2302 */     writer.write(GDWriter.lengthToBytes4("end_block"));
/* 2303 */     writer.write("end_block".getBytes(cs));
/* 2304 */     writer.write(this.endBlock);
/*      */     
/* 2306 */     writer.write(GDWriter.lengthToBytes4("xOffset"));
/* 2307 */     writer.write("xOffset".getBytes(cs));
/* 2308 */     writer.write(GDWriter.floatToBytes4(this.xPos));
/*      */     
/* 2310 */     writer.write(GDWriter.lengthToBytes4("yOffset"));
/* 2311 */     writer.write("yOffset".getBytes(cs));
/* 2312 */     writer.write(GDWriter.floatToBytes4(this.yPos));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void refresh() {
/* 2320 */     if (this.stashItem != null) {
/* 2321 */       GDLog log = new GDLog();
/* 2322 */       this.stashItem.fillDependentStats(log);
/* 2323 */       loadData(0);
/*      */       
/* 2325 */       this.error = log.containsMessages();
/*      */       
/* 2327 */       if (this.error) GDMsgLogger.addLog(log); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private String parseName() {
/* 2332 */     String name = this.dbItem.getItemName();
/*      */     
/* 2334 */     return name;
/*      */   }
/*      */   
/*      */   private ItemInfo compactBonuses() {
/* 2338 */     if (this.dbItem == null) return null;
/*      */     
/* 2340 */     ItemInfo info = new ItemInfo();
/*      */     
/* 2342 */     List<DBStat> itemStats = this.dbItem.getStatList();
/* 2343 */     if (itemStats != null) {
/* 2344 */       if (isComponent()) {
/*      */ 
/*      */         
/* 2347 */         int var1 = getVar1();
/*      */         
/* 2349 */         if (var1 == 0) var1 = 1;
/*      */         
/* 2351 */         List<DBStat> levelStats = DBStat.getStatsForLevel(itemStats, var1);
/*      */         
/* 2353 */         itemStats = levelStats;
/*      */       } 
/*      */       
/* 2356 */       DBStat.add(info.itemStats, this.dbItem, 1, itemStats);
/*      */     } 
/* 2358 */     if (this.dbItem.getSkillBonusList() != null) DBSkillBonus.add(info.itemSkillBonuses, this.dbItem.getSkillBonusList()); 
/* 2359 */     if (this.dbItem.getSkillModifierList() != null) info.skillModifiers.addAll(this.dbItem.getSkillModifierList()); 
/* 2360 */     if (this.dbItem.getItemSkill() != null) info.itemSkills.add(new ItemInfo.Skill(this.dbItem.getItemSkill(), this.dbItem.getItemSkillLevel(), this.dbItem.getItemSkillController())); 
/* 2361 */     if (this.dbItem.getPetBonusSkill() != null) info.petSkills.add(new ItemInfo.Skill(this.dbItem.getPetBonusSkill(), this.dbItem.getItemSkillLevel()));
/*      */     
/* 2363 */     if (this.dbPrefix != null) {
/* 2364 */       if (this.dbPrefix.getStatList() != null) DBStat.add(info.itemStats, this.dbPrefix, 3, this.dbPrefix.getStatList(this.dbItem.getItemScalePercent())); 
/* 2365 */       if (this.dbPrefix.getSkillBonusList() != null) DBSkillBonus.add(info.itemSkillBonuses, this.dbPrefix.getSkillBonusList()); 
/* 2366 */       if (this.dbPrefix.getSkillModifierList() != null) info.skillModifiers.addAll(this.dbPrefix.getSkillModifierList()); 
/* 2367 */       if (this.dbPrefix.getItemSkill() != null) info.itemSkills.add(new ItemInfo.Skill(this.dbPrefix.getItemSkill(), this.dbPrefix.getItemSkillLevel(), this.dbPrefix.getItemSkillController()));
/*      */ 
/*      */       
/* 2370 */       DBAffix petAffix = this.dbPrefix.getPetAffix();
/* 2371 */       if (petAffix != null && 
/* 2372 */         petAffix.getStatList() != null) DBStat.add(info.petStats, petAffix, 4, petAffix.getStatList());
/*      */       
/* 2374 */       DBSkill petSkill = this.dbPrefix.getPetSkill();
/* 2375 */       if (petSkill != null) {
/* 2376 */         List<DBStat> stats = DBStat.getByLevel(petSkill.getStatList(), 1);
/*      */         
/* 2378 */         if (stats != null && !stats.isEmpty()) DBStat.add(info.petStats, this.dbPrefix, 4, stats);
/*      */       
/*      */       } 
/*      */     } 
/* 2382 */     if (this.dbSuffix != null) {
/* 2383 */       if (this.dbSuffix.getStatList() != null) DBStat.add(info.itemStats, this.dbSuffix, 5, this.dbSuffix.getStatList(this.dbItem.getItemScalePercent())); 
/* 2384 */       if (this.dbSuffix.getSkillBonusList() != null) DBSkillBonus.add(info.itemSkillBonuses, this.dbSuffix.getSkillBonusList()); 
/* 2385 */       if (this.dbSuffix.getSkillModifierList() != null) info.skillModifiers.addAll(this.dbSuffix.getSkillModifierList()); 
/* 2386 */       if (this.dbSuffix.getItemSkill() != null) info.itemSkills.add(new ItemInfo.Skill(this.dbSuffix.getItemSkill(), this.dbSuffix.getItemSkillLevel(), this.dbSuffix.getItemSkillController()));
/*      */ 
/*      */       
/* 2389 */       DBAffix petAffix = this.dbSuffix.getPetAffix();
/* 2390 */       if (petAffix != null && 
/* 2391 */         petAffix.getStatList() != null) DBStat.add(info.petStats, petAffix, 6, petAffix.getStatList());
/*      */       
/* 2393 */       DBSkill petSkill = this.dbSuffix.getPetSkill();
/* 2394 */       if (petSkill != null) {
/* 2395 */         List<DBStat> stats = DBStat.getByLevel(petSkill.getStatList(), 1);
/*      */         
/* 2397 */         if (stats != null && !stats.isEmpty()) DBStat.add(info.petStats, this.dbSuffix, 6, stats);
/*      */       
/*      */       } 
/*      */     } 
/* 2401 */     if (this.dbModifier != null) {
/* 2402 */       if (this.dbModifier.getStatList() != null) DBStat.add(info.itemStats, this.dbModifier, 7, this.dbModifier.getStatList()); 
/* 2403 */       if (this.dbModifier.getSkillBonusList() != null) DBSkillBonus.add(info.itemSkillBonuses, this.dbModifier.getSkillBonusList()); 
/* 2404 */       if (this.dbModifier.getItemSkill() != null) info.itemSkills.add(new ItemInfo.Skill(this.dbModifier.getItemSkill(), this.dbModifier.getItemSkillLevel(), this.dbModifier.getItemSkillController()));
/*      */     
/*      */     } 
/* 2407 */     if (this.dbComponent != null) {
/* 2408 */       if (this.dbComponent.getStatList() != null) {
/*      */ 
/*      */         
/* 2411 */         int var1 = getVar1();
/*      */         
/* 2413 */         if (var1 == 0) var1 = 1;
/*      */         
/* 2415 */         List<DBStat> levelStats = DBStat.getStatsForLevel(this.dbComponent.getStatList(), var1);
/*      */         
/* 2417 */         DBStat.add(info.componentStats, this.dbComponent, 1, levelStats);
/*      */       } 
/* 2419 */       if (this.dbComponent.getSkillBonusList() != null) DBSkillBonus.add(info.componentSkillBonuses, this.dbComponent.getSkillBonusList()); 
/* 2420 */       if (this.dbComponent.getItemSkill() != null) info.componentSkills.add(new ItemInfo.Skill(this.dbComponent.getItemSkill(), this.dbComponent.getItemLevel(), this.dbComponent.getItemSkillController()));
/*      */     
/*      */     } 
/* 2423 */     if (this.dbBonus != null) {
/* 2424 */       if (this.dbBonus.getStatList() != null) DBStat.add(info.completionStats, this.dbBonus, 8, this.dbBonus.getStatList()); 
/* 2425 */       if (this.dbBonus.getSkillBonusList() != null) DBSkillBonus.add(info.completionSkillBonuses, this.dbBonus.getSkillBonusList()); 
/* 2426 */       if (this.dbBonus.getItemSkill() != null) info.completionSkills.add(new ItemInfo.Skill(this.dbBonus.getItemSkill(), this.dbBonus.getItemSkillLevel(), this.dbBonus.getItemSkillController()));
/*      */     
/*      */     } 
/* 2429 */     if (this.dbEnchantment != null && 
/* 2430 */       this.dbEnchantment.getStatList() != null) DBStat.add(info.enchantmentStats, this.dbEnchantment, 9, this.dbEnchantment.getStatList());
/*      */ 
/*      */     
/* 2433 */     return info;
/*      */   }
/*      */   
/*      */   private String determineCategoryTag() {
/* 2437 */     if (this.dbItem == null) return null;
/*      */     
/* 2439 */     String tag = null;
/*      */     
/* 2441 */     if (this.dbItem.getArmorClass() != null) {
/* 2442 */       if (this.dbItem.getArmorClass().equals("Heavy")) tag = "CATEGORY_ARMOR_HEAVY"; 
/* 2443 */       if (this.dbItem.getArmorClass().equals("Caster")) tag = "CATEGORY_ARMOR_CASTER";
/*      */     
/*      */     } 
/* 2446 */     if (this.dbItem.is1HWeapon()) tag = "CATEGORY_WEAPON_1H"; 
/* 2447 */     if (this.dbItem.is2HWeapon()) tag = "CATEGORY_WEAPON_2H";
/*      */     
/* 2449 */     return tag;
/*      */   }
/*      */   
/*      */   private String determineItemTypeTag() {
/* 2453 */     if (this.dbItem == null) return null; 
/* 2454 */     if (this.dbItem.getItemClass() == null) return null;
/*      */     
/* 2456 */     String tag = null;
/*      */     
/* 2458 */     if (this.dbItem.getItemClass().equals("ArmorProtective_Head")) tag = "TYPE_ARMOR_HEAD"; 
/* 2459 */     if (this.dbItem.getItemClass().equals("ArmorProtective_Shoulders")) tag = "TYPE_ARMOR_SHOULDERS"; 
/* 2460 */     if (this.dbItem.getItemClass().equals("ArmorProtective_Chest")) tag = "TYPE_ARMOR_CHEST"; 
/* 2461 */     if (this.dbItem.getItemClass().equals("ArmorProtective_Hands")) tag = "TYPE_ARMOR_HANDS"; 
/* 2462 */     if (this.dbItem.getItemClass().equals("ArmorProtective_Legs")) tag = "TYPE_ARMOR_LEGS"; 
/* 2463 */     if (this.dbItem.getItemClass().equals("ArmorProtective_Feet")) tag = "TYPE_ARMOR_FEET";
/*      */     
/* 2465 */     if (this.dbItem.getItemClass().equals("ArmorProtective_Waist")) tag = "TYPE_ARMOR_BELT"; 
/* 2466 */     if (this.dbItem.getItemClass().equals("ArmorJewelry_Amulet")) tag = "TYPE_ARMOR_AMULET"; 
/* 2467 */     if (this.dbItem.getItemClass().equals("ArmorJewelry_Medal")) tag = "TYPE_ARMOR_MEDAL"; 
/* 2468 */     if (this.dbItem.getItemClass().equals("ArmorJewelry_Ring")) tag = "TYPE_ARMOR_RING";
/*      */     
/* 2470 */     if (this.dbItem.getItemClass().equals("ItemRelic")) tag = "TYPE_ITEM_COMPONENT"; 
/* 2471 */     if (this.dbItem.getItemClass().equals("ItemArtifact")) tag = "TYPE_ITEM_ARTIFACT"; 
/* 2472 */     if (this.dbItem.getItemClass().equals("ItemEnchantment")) tag = "TYPE_ITEM_ENCHANTMENT";
/*      */     
/* 2474 */     if (this.dbItem.getItemClass().equals("WeaponArmor_Offhand")) tag = "TYPE_WEAPON_OFFHAND"; 
/* 2475 */     if (this.dbItem.getItemClass().equals("WeaponArmor_Shield")) tag = "TYPE_WEAPON_SHIELD";
/*      */     
/* 2477 */     if (this.dbItem.getItemClass().equals("WeaponMelee_Axe")) tag = "TYPE_WEAPON_AXE"; 
/* 2478 */     if (this.dbItem.getItemClass().equals("WeaponMelee_Mace")) tag = "TYPE_WEAPON_MACE"; 
/* 2479 */     if (this.dbItem.getItemClass().equals("WeaponHunting_Spear")) tag = "TYPE_WEAPON_SPEAR"; 
/* 2480 */     if (this.dbItem.getItemClass().equals("WeaponMelee_Sword")) tag = "TYPE_WEAPON_SWORD"; 
/* 2481 */     if (this.dbItem.getItemClass().equals("WeaponMelee_Dagger")) tag = "TYPE_WEAPON_DAGGER"; 
/* 2482 */     if (this.dbItem.getItemClass().equals("WeaponMelee_Scepter")) tag = "TYPE_WEAPON_SCEPTER"; 
/* 2483 */     if (this.dbItem.getItemClass().equals("WeaponHunting_Ranged1h")) tag = "TYPE_WEAPON_RANGED";
/*      */     
/* 2485 */     if (this.dbItem.getItemClass().equals("WeaponMelee_Axe2h")) tag = "TYPE_WEAPON_AXE"; 
/* 2486 */     if (this.dbItem.getItemClass().equals("WeaponMelee_Mace2h")) tag = "TYPE_WEAPON_MACE"; 
/* 2487 */     if (this.dbItem.getItemClass().equals("WeaponMagical_Staff")) tag = "TYPE_WEAPON_STAFF"; 
/* 2488 */     if (this.dbItem.getItemClass().equals("WeaponMelee_Sword2h")) tag = "TYPE_WEAPON_SWORD"; 
/* 2489 */     if (this.dbItem.getItemClass().equals("WeaponHunting_Ranged2h")) tag = "TYPE_WEAPON_RANGED";
/*      */     
/* 2491 */     if (this.dbItem.getItemClass().equals("ItemNote")) tag = "TYPE_ITEM_NOTE";
/*      */     
/* 2493 */     return tag;
/*      */   }
/*      */   
/*      */   public String determineRarityTag() {
/* 2497 */     if (this.dbItem == null) return null;
/*      */     
/* 2499 */     String tag = null;
/*      */     
/* 2501 */     if (this.dbItem.isArtifact()) {
/* 2502 */       if (this.dbItem.getArtifactClass() != null) {
/* 2503 */         if (this.dbItem.getArtifactClass().equals("Lesser")) tag = "CATEGORY_ARTIFACT_EMPOWERED"; 
/* 2504 */         if (this.dbItem.getArtifactClass().equals("Greater")) tag = "CATEGORY_ARTIFACT_TRANSCENDED"; 
/* 2505 */         if (this.dbItem.getArtifactClass().equals("Divine")) tag = "CATEGORY_ARTIFACT_MYTHICAL"; 
/*      */       } 
/*      */     } else {
/* 2508 */       String rarity = getRarity();
/*      */       
/* 2510 */       if (rarity != null) {
/* 2511 */         if (rarity.equals("Magical")) tag = "CATEGORY_RARITY_MAGICAL"; 
/* 2512 */         if (rarity.equals("Rare")) tag = "CATEGORY_RARITY_RARE"; 
/* 2513 */         if (rarity.equals("Epic")) tag = "CATEGORY_RARITY_EPIC"; 
/* 2514 */         if (rarity.equals("Legendary")) tag = "CATEGORY_RARITY_LEGENDARY";
/*      */       
/*      */       } 
/*      */     } 
/* 2518 */     return tag;
/*      */   }
/*      */   
/*      */   private static String byteToHex(byte b) {
/* 2522 */     int i = b;
/* 2523 */     if (i < 0) i += 256;
/*      */     
/* 2525 */     int v1 = i / 16;
/* 2526 */     int v2 = i % 16;
/*      */     
/* 2528 */     String s = intDigitToHex(v1) + intDigitToHex(v2);
/*      */     
/* 2530 */     return s;
/*      */   }
/*      */   
/*      */   private static String intDigitToHex(int val) {
/* 2534 */     String s = null;
/*      */     
/* 2536 */     switch (val)
/*      */     { case 10:
/* 2538 */         s = "A";
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
/* 2561 */         return s;case 11: s = "B"; return s;case 12: s = "C"; return s;case 13: s = "D"; return s;case 14: s = "E"; return s;case 15: s = "F"; return s; }  if (val >= 0 && val <= 9) s = Integer.toString(val);  return s;
/*      */   }
/*      */   
/*      */   private static byte hexDigitToByte(char c) {
/* 2565 */     byte b = -1;
/*      */     
/* 2567 */     switch (c) {
/*      */       case '0':
/* 2569 */         b = 0;
/*      */         break;
/*      */       case '1':
/* 2572 */         b = 1;
/*      */         break;
/*      */       case '2':
/* 2575 */         b = 2;
/*      */         break;
/*      */       case '3':
/* 2578 */         b = 3;
/*      */         break;
/*      */       case '4':
/* 2581 */         b = 4;
/*      */         break;
/*      */       case '5':
/* 2584 */         b = 5;
/*      */         break;
/*      */       case '6':
/* 2587 */         b = 6;
/*      */         break;
/*      */       case '7':
/* 2590 */         b = 7;
/*      */         break;
/*      */       case '8':
/* 2593 */         b = 8;
/*      */         break;
/*      */       case '9':
/* 2596 */         b = 9;
/*      */         break;
/*      */       case 'A':
/* 2599 */         b = 10;
/*      */         break;
/*      */       case 'B':
/* 2602 */         b = 11;
/*      */         break;
/*      */       case 'C':
/* 2605 */         b = 12;
/*      */         break;
/*      */       case 'D':
/* 2608 */         b = 13;
/*      */         break;
/*      */       case 'E':
/* 2611 */         b = 14;
/*      */         break;
/*      */       case 'F':
/* 2614 */         b = 15;
/*      */         break;
/*      */     } 
/*      */     
/* 2618 */     return b;
/*      */   }
/*      */   
/*      */   public static int generateSeed() {
/* 2622 */     return random.nextInt();
/*      */   }
/*      */   
/*      */   public void createSeed() {
/* 2626 */     this.stashItem.setItemSeed(generateSeed());
/*      */   }
/*      */   
/*      */   public String getItemSetBonuses() {
/* 2630 */     if (this.dbItem == null) return null;
/*      */     
/* 2632 */     String s = this.dbItem.getItemSetName();
/*      */     
/* 2634 */     if (s == null) return null;
/*      */     
/* 2636 */     s = GDColor.HTML_COLOR_ITEMSET + s + "</font>" + "<br>";
/*      */     
/* 2638 */     ArrayList<List<DBStat>> arrStat = this.dbItem.getItemSetBonusesPerLevel();
/* 2639 */     ArrayList<List<DBSkillBonus>> arrBonus = this.dbItem.getItemSetSkillBonusesPerLevel();
/*      */     int i;
/* 2641 */     for (i = 0; i < arrStat.size(); i++) {
/* 2642 */       Object[] args = { Integer.valueOf(i + 1) };
/* 2643 */       String title = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SET_NUM_ITEMS", args);
/*      */       
/* 2645 */       boolean hasTitle = false;
/* 2646 */       String txtStat = null;
/*      */       
/* 2648 */       if (!((List)arrStat.get(i)).isEmpty() || !((List)arrBonus.get(i)).isEmpty()) {
/* 2649 */         DetailComposer comp = DetailComposer.createComposer(this.stashItem, 1, arrStat.get(i), arrBonus.get(i));
/*      */         
/* 2651 */         txtStat = DetailComposer.getComposerBonuses(comp, true, true, false);
/* 2652 */         if (txtStat != null) {
/* 2653 */           if (!hasTitle) {
/* 2654 */             s = s + title;
/*      */             
/* 2656 */             hasTitle = true;
/*      */           } 
/*      */           
/* 2659 */           s = s + "<br>" + GDColor.HTML_COLOR_STAT + txtStat + "</font>";
/*      */         } 
/*      */       } 
/*      */       
/* 2663 */       List<DBSkillModifier> modifiers = this.dbItem.getSkillModifiers(i);
/* 2664 */       if (modifiers != null && !modifiers.isEmpty()) {
/* 2665 */         DetailComposer compSkillModifier = DetailComposer.createComposerSkillMod(this.stashItem, 1, modifiers);
/*      */         
/* 2667 */         String txtMod = DetailComposer.getComposerBonuses(compSkillModifier, true, true, false);
/* 2668 */         if (txtMod != null) {
/* 2669 */           if (!hasTitle) {
/* 2670 */             s = s + title;
/*      */             
/* 2672 */             hasTitle = true;
/*      */           } 
/*      */           
/* 2675 */           s = s + "<br>" + GDColor.HTML_COLOR_STAT + txtMod + "</font>";
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2680 */     return s;
/*      */   }
/*      */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\item\GDItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */