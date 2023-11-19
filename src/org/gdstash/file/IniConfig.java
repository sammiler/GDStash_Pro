/*     */ package org.gdstash.file;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Toolkit;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import org.gdstash.util.GDConstants;
/*     */ import org.gdstash.util.GDMsgLogger;
/*     */ import org.ini4j.Ini;
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
/*     */ public class IniConfig
/*     */   implements Cloneable
/*     */ {
/*     */   public static final String DEFAULT_LANGUAGE = "English";
/*     */   public static final int AFFIX_COMBI_VALID = 1;
/*     */   public static final int AFFIX_COMBI_ALL_COMBOS = 2;
/*     */   public static final int AFFIX_COMBI_ALL_AFFIXES = 3;
/*     */   
/*     */   public static class DirectorySection
/*     */     implements Cloneable
/*     */   {
/*     */     private static final String SECTION = "directories";
/*     */     private static final String KEY_GD_PATH = "gd_path";
/*     */     private static final String KEY_SAVE_PATH = "save_path";
/*     */     private static final String KEY_EXPORT_PATH = "export_path";
/*     */     private static final String KEY_BUFFER_TYPE = "buffer_type";
/*     */     public String gdPath;
/*     */     public String savePath;
/*     */     public String exportPath;
/*     */     
/*     */     public DirectorySection() {
/*  41 */       File file = null;
/*     */       
/*  43 */       this.gdPath = "";
/*  44 */       this.savePath = "";
/*     */       
/*  46 */       this.exportPath = GDConstants.USER_DIR;
/*     */     }
/*     */ 
/*     */     
/*     */     public DirectorySection clone() {
/*  51 */       DirectorySection section = new DirectorySection();
/*     */       
/*  53 */       section.gdPath = this.gdPath;
/*  54 */       section.savePath = this.savePath;
/*     */       
/*  56 */       section.exportPath = this.exportPath;
/*     */       
/*  58 */       return section;
/*     */     }
/*     */     
/*     */     public void getIniValues(Ini ini) {
/*  62 */       File file = null;
/*  63 */       String dirSteam = null;
/*     */       
/*  65 */       this.gdPath = ini.get("directories", "gd_path");
/*     */       
/*  67 */       if (this.gdPath == null) {
/*  68 */         this.gdPath = "C:\\Program Files (x86)\\Steam\\steamapps\\common\\Grim Dawn";
/*     */         
/*  70 */         file = new File(this.gdPath);
/*  71 */         if (file.exists()) {
/*  72 */           dirSteam = "C:\\Program Files (x86)\\Steam";
/*     */         } else {
/*  74 */           this.gdPath = null;
/*     */         } 
/*     */       } 
/*  77 */       if (this.gdPath == null) {
/*  78 */         this.gdPath = "C:\\Program Files\\Steam\\steamapps\\common\\Grim Dawn";
/*     */         
/*  80 */         file = new File(this.gdPath);
/*  81 */         if (file.exists()) {
/*  82 */           dirSteam = "C:\\Program Files\\Steam";
/*     */         } else {
/*  84 */           this.gdPath = null;
/*     */         } 
/*     */       } 
/*  87 */       if (this.gdPath == null) {
/*  88 */         this.gdPath = "C:\\GOG Games\\Grim Dawn";
/*     */         
/*  90 */         file = new File(this.gdPath);
/*  91 */         if (!file.exists()) this.gdPath = null;
/*     */       
/*     */       } 
/*  94 */       this.savePath = ini.get("directories", "save_path");
/*     */       
/*  96 */       if (this.savePath == null) {
/*  97 */         this.savePath = GDConstants.USER_HOME + "\\Documents\\My Games\\Grim Dawn\\save";
/*     */         
/*  99 */         file = new File(this.savePath);
/* 100 */         if (!file.exists()) this.savePath = null; 
/*     */       } 
/* 102 */       if (this.savePath == null && 
/* 103 */         dirSteam != null) {
/* 104 */         this.savePath = dirSteam + "\\userdata";
/*     */         
/* 106 */         file = new File(this.savePath);
/* 107 */         this.savePath = null;
/* 108 */         if (file.exists()) {
/* 109 */           File[] files = file.listFiles(); int i;
/* 110 */           for (i = 0; i < files.length; i++) {
/* 111 */             if (files[i].isDirectory()) {
/*     */               try {
/* 113 */                 file = new File(files[i].getCanonicalPath() + "\\219990\\remote\\save");
/*     */                 
/* 115 */                 if (file.exists()) {
/* 116 */                   this.savePath = file.getCanonicalPath();
/*     */                   
/*     */                   break;
/*     */                 } 
/* 120 */               } catch (IOException iOException) {}
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 133 */       this.exportPath = ini.get("directories", "export_path");
/*     */     }
/*     */ 
/*     */     
/*     */     public void putIniValues(Ini ini) {
/* 138 */       ini.remove("directories", "gd_path");
/* 139 */       ini.remove("directories", "save_path");
/*     */       
/* 141 */       ini.remove("directories", "export_path");
/* 142 */       ini.remove("directories", "buffer_type");
/*     */       
/* 144 */       ini.put("directories", "gd_path", this.gdPath);
/* 145 */       ini.put("directories", "save_path", this.savePath);
/*     */       
/* 147 */       ini.put("directories", "export_path", this.exportPath);
/*     */     }
/*     */     
/*     */     public void remove(Ini ini) {
/* 151 */       ini.remove("directories");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class RestrictionSection
/*     */     implements Cloneable
/*     */   {
/*     */     private static final String SECTION = "restrictions";
/*     */     
/*     */     private static final String KEY_ALLOW_EDIT = "allow_edit";
/*     */     
/*     */     private static final String KEY_TRANS_SC_HC = "transfer_sc_hc";
/*     */     
/*     */     private static final String KEY_TRANS_SOUL = "transfer_soulbound";
/*     */     
/*     */     private static final String KEY_COMPLETION_ALL = "completion_all";
/*     */     
/*     */     private static final String KEY_BLACKSMITH_ALL = "blacksmith_all";
/*     */     
/*     */     private static final String KEY_AFFIX_COMBI = "affix_combi";
/*     */     
/*     */     private static final String KEY_STASH_MOVE = "stash_move";
/*     */     public boolean allowEdit = true;
/*     */     public boolean transferSCHC = false;
/*     */     public boolean transferSoulbound = false;
/* 177 */     public int affixCombi = 1;
/*     */     
/*     */     public boolean completionAll = false;
/*     */     
/*     */     public boolean blacksmithAll = false;
/*     */     public boolean dbStashMove = false;
/*     */     
/*     */     public RestrictionSection clone() {
/* 185 */       RestrictionSection section = new RestrictionSection();
/*     */       
/* 187 */       section.allowEdit = this.allowEdit;
/* 188 */       section.transferSCHC = this.transferSCHC;
/* 189 */       section.transferSoulbound = this.transferSoulbound;
/* 190 */       section.affixCombi = this.affixCombi;
/* 191 */       section.completionAll = this.completionAll;
/* 192 */       section.blacksmithAll = this.blacksmithAll;
/* 193 */       section.dbStashMove = this.dbStashMove;
/*     */       
/* 195 */       return section;
/*     */     }
/*     */     
/*     */     public void getIniValues(Ini ini) {
/* 199 */       String flag = ini.get("restrictions", "allow_edit");
/* 200 */       if (flag == null) {
/* 201 */         this.allowEdit = true;
/*     */       } else {
/* 203 */         this.allowEdit = Boolean.parseBoolean(flag);
/*     */       } 
/*     */       
/* 206 */       this.transferSCHC = Boolean.parseBoolean(ini.get("restrictions", "transfer_sc_hc"));
/* 207 */       this.transferSoulbound = Boolean.parseBoolean(ini.get("restrictions", "transfer_soulbound"));
/* 208 */       this.completionAll = Boolean.parseBoolean(ini.get("restrictions", "completion_all"));
/* 209 */       this.blacksmithAll = Boolean.parseBoolean(ini.get("restrictions", "blacksmith_all"));
/* 210 */       this.dbStashMove = Boolean.parseBoolean(ini.get("restrictions", "stash_move"));
/*     */       
/*     */       try {
/* 213 */         this.affixCombi = Integer.parseInt(ini.get("restrictions", "affix_combi"));
/*     */       }
/* 215 */       catch (NumberFormatException ex) {
/* 216 */         this.affixCombi = 1;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void putIniValues(Ini ini) {
/* 222 */       ini.remove("restrictions", "allow_edit");
/* 223 */       ini.remove("restrictions", "transfer_sc_hc");
/* 224 */       ini.remove("restrictions", "transfer_soulbound");
/* 225 */       ini.remove("restrictions", "affix_combi");
/* 226 */       ini.remove("restrictions", "completion_all");
/* 227 */       ini.remove("restrictions", "blacksmith_all");
/* 228 */       ini.remove("restrictions", "stash_move");
/*     */       
/* 230 */       ini.put("restrictions", "allow_edit", Boolean.toString(this.allowEdit));
/* 231 */       ini.put("restrictions", "transfer_sc_hc", Boolean.toString(this.transferSCHC));
/* 232 */       ini.put("restrictions", "transfer_soulbound", Boolean.toString(this.transferSoulbound));
/* 233 */       ini.put("restrictions", "affix_combi", Integer.toString(this.affixCombi));
/* 234 */       ini.put("restrictions", "completion_all", Boolean.toString(this.completionAll));
/* 235 */       ini.put("restrictions", "blacksmith_all", Boolean.toString(this.blacksmithAll));
/* 236 */       ini.put("restrictions", "stash_move", Boolean.toString(this.dbStashMove));
/*     */     }
/*     */     
/*     */     public void remove(Ini ini) {
/* 240 */       ini.remove("restrictions");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class UISection
/*     */     implements Cloneable
/*     */   {
/*     */     private static final String SECTION = "ui";
/*     */     
/*     */     private static final String KEY_LANGUAGE = "language";
/*     */     
/*     */     private static final String KEY_LOOK_N_FEEL = "look_feel";
/*     */     
/*     */     private static final String KEY_FONT_SIZE = "font_size";
/*     */     
/*     */     private static final String KEY_SYS_TRAY = "system_tray";
/*     */     
/*     */     private static final String KEY_GRAPHIC_SCALE = "graphic_scale";
/*     */     
/*     */     private static final String KEY_STASH_LAST = "stash_last";
/*     */     private static final int DEFAULT_FONTSIZE = 12;
/*     */     private static final int DEFAULT_GRAPHICSCALE = 100;
/*     */     private static final String DEFAULT_LOOK_N_FEEL = "Metal";
/* 264 */     public String language = "English";
/* 265 */     public int fontSize = 12;
/* 266 */     public int graphicScale = 100;
/* 267 */     public String lookNFeel = "Metal";
/*     */     
/*     */     public boolean minimizeToTray = false;
/*     */ 
/*     */     
/*     */     public UISection clone() {
/* 273 */       UISection section = new UISection();
/*     */       
/* 275 */       section.language = this.language;
/* 276 */       section.fontSize = this.fontSize;
/* 277 */       section.graphicScale = this.graphicScale;
/* 278 */       section.lookNFeel = this.lookNFeel;
/* 279 */       section.minimizeToTray = this.minimizeToTray;
/*     */       
/* 281 */       return section;
/*     */     }
/*     */     
/*     */     public void getIniValues(Ini ini) {
/* 285 */       this.language = ini.get("ui", "language");
/* 286 */       if (this.language == null) this.language = "English";
/*     */       
/* 288 */       this.lookNFeel = ini.get("ui", "look_feel");
/* 289 */       if (this.lookNFeel == null) this.lookNFeel = "Metal";
/*     */       
/*     */       try {
/* 292 */         this.fontSize = Integer.parseInt(ini.get("ui", "font_size"));
/*     */       }
/* 294 */       catch (NumberFormatException ex) {
/* 295 */         this.fontSize = 12;
/*     */       } 
/*     */       
/*     */       try {
/* 299 */         this.graphicScale = Integer.parseInt(ini.get("ui", "graphic_scale"));
/*     */       }
/* 301 */       catch (NumberFormatException ex) {
/* 302 */         this.graphicScale = 100;
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 307 */         this.minimizeToTray = Boolean.parseBoolean(ini.get("ui", "system_tray"));
/*     */       }
/* 309 */       catch (NumberFormatException ex) {
/* 310 */         this.minimizeToTray = false;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void putIniValues(Ini ini) {
/* 316 */       ini.remove("ui", "language");
/* 317 */       ini.remove("ui", "font_size");
/* 318 */       ini.remove("ui", "graphic_scale");
/* 319 */       ini.remove("ui", "look_feel");
/* 320 */       ini.remove("ui", "system_tray");
/*     */       
/* 322 */       ini.put("ui", "language", this.language);
/* 323 */       ini.put("ui", "font_size", Integer.toString(this.fontSize));
/* 324 */       ini.put("ui", "graphic_scale", Integer.toString(this.graphicScale));
/* 325 */       ini.put("ui", "look_feel", this.lookNFeel);
/* 326 */       ini.put("ui", "system_tray", Boolean.toString(this.minimizeToTray));
/*     */     }
/*     */     
/*     */     public void remove(Ini ini) {
/* 330 */       ini.remove("ui");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class HistorySection
/*     */     implements Cloneable
/*     */   {
/*     */     private static final String SECTION = "history";
/*     */     
/*     */     private static final String KEY_LAST_STASH = "last_stash";
/*     */     
/*     */     private static final String KEY_LAST_MOD = "last_mod";
/* 343 */     public String lastStash = null;
/* 344 */     public String lastMod = null;
/*     */ 
/*     */ 
/*     */     
/*     */     public HistorySection clone() {
/* 349 */       HistorySection section = new HistorySection();
/*     */       
/* 351 */       section.lastStash = this.lastStash;
/* 352 */       section.lastMod = this.lastMod;
/*     */       
/* 354 */       return section;
/*     */     }
/*     */     
/*     */     public void getIniValues(Ini ini) {
/* 358 */       this.lastStash = ini.get("history", "last_stash");
/* 359 */       this.lastMod = ini.get("history", "last_mod");
/*     */     }
/*     */ 
/*     */     
/*     */     public void putIniValues(Ini ini) {
/* 364 */       ini.remove("history", "last_stash");
/* 365 */       ini.remove("history", "last_mod");
/*     */       
/* 367 */       ini.put("history", "last_stash", this.lastStash);
/* 368 */       ini.put("history", "last_mod", this.lastMod);
/*     */     }
/*     */     
/*     */     public void remove(Ini ini) {
/* 372 */       ini.remove("history");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class WindowSection
/*     */     implements Cloneable
/*     */   {
/*     */     private static final String SECTION = "window";
/*     */     private static final String KEY_X_POS = "x_pos";
/*     */     private static final String KEY_Y_POS = "y_pos";
/*     */     private static final String KEY_HEIGHT = "height";
/*     */     private static final String KEY_WIDTH = "width";
/*     */     private static final String KEY_MAXIMIZED = "maximized";
/*     */     private static final int DEFAULT_HEIGHT = 920;
/*     */     private static final int DEFAULT_WIDTH = 1680;
/*     */     public int x;
/*     */     public int y;
/*     */     public int w;
/*     */     public int h;
/*     */     public boolean maximized;
/*     */     
/*     */     public WindowSection() {
/* 394 */       Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/* 395 */       int scrWidth = (int)screenSize.getWidth();
/* 396 */       int scrHeight = (int)screenSize.getHeight();
/*     */       
/* 398 */       if (scrWidth < 1680) {
/* 399 */         this.w = scrWidth;
/*     */       } else {
/* 401 */         this.w = 1680;
/*     */       } 
/* 403 */       if (scrHeight < 920) {
/* 404 */         this.h = scrHeight;
/*     */       } else {
/* 406 */         this.h = 920;
/*     */       } 
/*     */       
/* 409 */       this.x = (scrWidth - this.w) / 2;
/* 410 */       if (scrWidth < this.w) this.x = 0;
/*     */       
/* 412 */       this.y = (scrHeight - this.h) / 2;
/* 413 */       if (scrHeight < this.h) this.y = 0;
/*     */       
/* 415 */       this.maximized = false;
/*     */     }
/*     */ 
/*     */     
/*     */     public WindowSection clone() {
/* 420 */       WindowSection section = new WindowSection();
/*     */       
/* 422 */       section.x = this.x;
/* 423 */       section.y = this.y;
/* 424 */       section.w = this.w;
/* 425 */       section.h = this.h;
/* 426 */       section.maximized = this.maximized;
/*     */       
/* 428 */       return section;
/*     */     }
/*     */     
/*     */     public void getIniValues(Ini ini) {
/* 432 */       Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/* 433 */       int scrWidth = (int)screenSize.getWidth();
/* 434 */       int scrHeight = (int)screenSize.getHeight();
/*     */       
/* 436 */       this.maximized = Boolean.parseBoolean(ini.get("window", "maximized"));
/*     */       
/*     */       try {
/* 439 */         this.h = Integer.parseInt(ini.get("window", "height"));
/*     */         
/* 441 */         if (this.h < 0) this.h = 920; 
/* 442 */         if (this.h > scrHeight) this.h = scrHeight;
/*     */       
/* 444 */       } catch (NumberFormatException ex) {
/* 445 */         if (scrHeight < 920) {
/* 446 */           this.h = scrHeight;
/*     */         } else {
/* 448 */           this.h = 920;
/*     */         } 
/*     */       } 
/*     */       
/*     */       try {
/* 453 */         this.w = Integer.parseInt(ini.get("window", "width"));
/*     */         
/* 455 */         if (this.w < 0) this.w = 1680; 
/* 456 */         if (this.w > scrWidth) this.w = scrWidth;
/*     */       
/* 458 */       } catch (NumberFormatException ex) {
/* 459 */         if (scrWidth < 1680) {
/* 460 */           this.w = scrWidth;
/*     */         } else {
/* 462 */           this.w = 1680;
/*     */         } 
/*     */       } 
/*     */       
/*     */       try {
/* 467 */         this.x = Integer.parseInt(ini.get("window", "x_pos"));
/*     */         
/* 469 */         if (this.x < 0) this.x = 0; 
/* 470 */         if (this.x > scrWidth) {
/* 471 */           this.x = (scrWidth - this.w) / 2;
/* 472 */           if (this.x < 0) this.x = 0;
/*     */         
/*     */         } 
/* 475 */       } catch (NumberFormatException ex) {
/* 476 */         this.x = (scrWidth - this.w) / 2;
/* 477 */         if (this.x < 0) this.x = 0;
/*     */       
/*     */       } 
/*     */       try {
/* 481 */         this.y = Integer.parseInt(ini.get("window", "y_pos"));
/*     */         
/* 483 */         if (this.y < 0) this.y = 0; 
/* 484 */         if (this.y > scrHeight) {
/* 485 */           this.y = (scrHeight - this.h) / 2;
/* 486 */           if (this.y < 0) this.y = 0;
/*     */         
/*     */         } 
/* 489 */       } catch (NumberFormatException ex) {
/* 490 */         this.y = (scrHeight - this.h) / 2;
/* 491 */         if (this.y < 0) this.y = 0;
/*     */       
/*     */       } 
/*     */     }
/*     */     
/*     */     public void putIniValues(Ini ini) {
/* 497 */       ini.remove("window", "x_pos");
/* 498 */       ini.remove("window", "y_pos");
/* 499 */       ini.remove("window", "width");
/* 500 */       ini.remove("window", "height");
/* 501 */       ini.remove("window", "maximized");
/*     */       
/* 503 */       ini.put("window", "x_pos", Integer.toString(this.x));
/* 504 */       ini.put("window", "y_pos", Integer.toString(this.y));
/* 505 */       ini.put("window", "width", Integer.toString(this.w));
/* 506 */       ini.put("window", "height", Integer.toString(this.h));
/* 507 */       ini.put("window", "maximized", Boolean.toString(this.maximized));
/*     */     }
/*     */     
/*     */     public void remove(Ini ini) {
/* 511 */       ini.remove("window");
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
/*     */ 
/*     */   
/* 531 */   private static String iniPath = GDConstants.USER_HOME + GDConstants.FILE_SEPARATOR + "GDStash" + GDConstants.FILE_SEPARATOR + "GDStash.ini"; private File iniFile; private Ini ini; public DirectorySection sectDir; public RestrictionSection sectRestrict;
/*     */   public UISection sectUI;
/*     */   public HistorySection sectHistory;
/*     */   
/*     */   public IniConfig() {
/* 536 */     this.sectDir = new DirectorySection();
/* 537 */     this.sectRestrict = new RestrictionSection();
/* 538 */     this.sectUI = new UISection();
/* 539 */     this.sectHistory = new HistorySection();
/* 540 */     this.sectWindow = new WindowSection();
/*     */     
/* 542 */     this.sectDirLoaded = new DirectorySection();
/* 543 */     this.sectRestrictLoaded = new RestrictionSection();
/* 544 */     this.sectUILoaded = new UISection();
/* 545 */     this.sectHistoryLoaded = new HistorySection();
/* 546 */     this.sectWindowLoaded = new WindowSection();
/*     */   }
/*     */   public WindowSection sectWindow; private DirectorySection sectDirLoaded; private RestrictionSection sectRestrictLoaded; private UISection sectUILoaded; private HistorySection sectHistoryLoaded; private WindowSection sectWindowLoaded;
/*     */   public IniConfig(File file) {
/* 550 */     this();
/*     */     
/* 552 */     this.iniFile = file;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 557 */       if (!file.exists()) {
/* 558 */         File dir = file.getParentFile();
/*     */         
/* 560 */         if (dir != null && 
/* 561 */           !dir.exists()) dir.mkdirs();
/*     */ 
/*     */         
/* 564 */         file.createNewFile();
/*     */       } 
/*     */       
/* 567 */       this.ini = new Ini(file);
/*     */     }
/* 569 */     catch (IOException ex) {
/* 570 */       this.ini = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 577 */     IniConfig config = new IniConfig();
/*     */     
/* 579 */     config.sectDirLoaded = this.sectDirLoaded.clone();
/* 580 */     config.sectRestrictLoaded = this.sectRestrictLoaded.clone();
/* 581 */     config.sectUILoaded = this.sectUILoaded.clone();
/* 582 */     config.sectHistoryLoaded = this.sectHistoryLoaded.clone();
/* 583 */     config.sectWindowLoaded = this.sectWindowLoaded.clone();
/*     */     
/* 585 */     config.sectDir = this.sectDir.clone();
/* 586 */     config.sectRestrict = this.sectRestrict.clone();
/* 587 */     config.sectUI = this.sectUI.clone();
/* 588 */     config.sectHistory = this.sectHistory.clone();
/* 589 */     config.sectWindow = this.sectWindow.clone();
/*     */     
/* 591 */     config.iniFile = this.iniFile;
/*     */     
/* 593 */     return config;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IniConfig get() {
/* 601 */     File file = new File(iniPath);
/* 602 */     IniConfig config = new IniConfig(file);
/*     */     
/* 604 */     if (!config.read()) {
/* 605 */       config = new IniConfig(file);
/*     */     }
/*     */     
/* 608 */     return config;
/*     */   }
/*     */   
/*     */   public boolean read() {
/* 612 */     boolean success = false;
/*     */     
/*     */     try {
/* 615 */       this.ini = new Ini(this.iniFile);
/* 616 */       this.ini.load();
/*     */       
/* 618 */       this.sectDirLoaded.getIniValues(this.ini);
/* 619 */       this.sectRestrictLoaded.getIniValues(this.ini);
/* 620 */       this.sectUILoaded.getIniValues(this.ini);
/* 621 */       this.sectHistoryLoaded.getIniValues(this.ini);
/* 622 */       this.sectWindowLoaded.getIniValues(this.ini);
/*     */       
/* 624 */       this.sectDir = this.sectDirLoaded.clone();
/* 625 */       this.sectRestrict = this.sectRestrictLoaded.clone();
/* 626 */       this.sectUI = this.sectUILoaded.clone();
/* 627 */       this.sectHistory = this.sectHistoryLoaded.clone();
/* 628 */       this.sectWindow = this.sectWindowLoaded.clone();
/*     */       
/* 630 */       success = true;
/*     */     }
/* 632 */     catch (IOException ex) {
/* 633 */       success = false;
/*     */     } 
/*     */     
/* 636 */     return success;
/*     */   }
/*     */   
/*     */   public boolean write() {
/* 640 */     boolean success = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 645 */     this.sectDir.putIniValues(this.ini);
/* 646 */     this.sectRestrict.putIniValues(this.ini);
/* 647 */     this.sectUI.putIniValues(this.ini);
/* 648 */     this.sectHistory.putIniValues(this.ini);
/* 649 */     this.sectWindow.putIniValues(this.ini);
/*     */     
/*     */     try {
/* 652 */       this.ini.store();
/*     */       
/* 654 */       this.sectDirLoaded = this.sectDir.clone();
/* 655 */       this.sectRestrictLoaded = this.sectRestrict.clone();
/* 656 */       this.sectUILoaded = this.sectUI.clone();
/* 657 */       this.sectHistoryLoaded = this.sectHistory.clone();
/* 658 */       this.sectWindowLoaded = this.sectWindow.clone();
/*     */       
/* 660 */       success = true;
/*     */     }
/* 662 */     catch (IOException ex) {
/* 663 */       GDMsgLogger.addError(ex);
/*     */       
/* 665 */       success = false;
/*     */     } 
/*     */     
/* 668 */     return success;
/*     */   }
/*     */   
/*     */   public boolean writeWindowSection() {
/* 672 */     boolean success = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 677 */     this.sectDirLoaded.putIniValues(this.ini);
/* 678 */     this.sectRestrictLoaded.putIniValues(this.ini);
/* 679 */     this.sectUILoaded.putIniValues(this.ini);
/* 680 */     this.sectHistory.putIniValues(this.ini);
/* 681 */     this.sectWindow.putIniValues(this.ini);
/*     */     
/*     */     try {
/* 684 */       this.ini.store();
/*     */       
/* 686 */       success = true;
/*     */     }
/* 688 */     catch (IOException ex) {
/* 689 */       GDMsgLogger.addError(ex);
/*     */       
/* 691 */       success = false;
/*     */     } 
/*     */     
/* 694 */     return success;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\file\IniConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */