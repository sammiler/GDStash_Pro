/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import org.gdstash.file.ARZRecord;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.util.GDConstants;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ import org.gdstash.util.GDMsgLogger;
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
/*     */ public class GDDBData
/*     */ {
/*     */   public static final String DERBY_LANG_DUPLICATE_KEY_CONSTRAINT = "23505";
/*     */   private static final String oldURL = "jdbc:derby:db;";
/*     */   private static final int DERBY_TRACE_NONE = 0;
/*     */   private static final int DERBY_TRACE_CONNECTION_CALLS = 1;
/*     */   private static final int DERBY_TRACE_STATEMENT_CALLS = 2;
/*     */   private static final int DERBY_TRACE_RESULT_SET_CALLS = 4;
/*     */   private static final int DERBY_TRACE_DRIVER_CONFIGURATION = 16;
/*     */   private static final int DERBY_TRACE_CONNECTS = 32;
/*     */   private static final int DERBY_TRACE_PROTOCOL_FLOWS = 64;
/*     */   private static final int DERBY_TRACE_RESULT_SET_META_DATA = 128;
/*     */   private static final int DERBY_TRACE_PARAMETER_META_DATA = 256;
/*     */   private static final int DERBY_TRACE_DIAGNOSTICS = 512;
/*     */   private static final int DERBY_TRACE_ALL = -1;
/*  38 */   private static String dbURL = null;
/*  39 */   private static String mod = null;
/*     */   private static final String derbyLocale = "en_US";
/*     */   
/*     */   private static void fillDBUrl(String mod) {
/*  43 */     String dbDir = null;
/*     */     
/*  45 */     if (mod == null) {
/*  46 */       dbDir = "db";
/*     */     }
/*  48 */     else if (mod.isEmpty()) {
/*  49 */       dbDir = "db";
/*     */     } else {
/*  51 */       dbDir = "db_" + mod;
/*     */     } 
/*     */ 
/*     */     
/*  55 */     String dir = GDConstants.USER_HOME + GDConstants.FILE_SEPARATOR + "GDStash" + GDConstants.FILE_SEPARATOR + dbDir;
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
/*  68 */     dbURL = "jdbc:derby:" + dir + ";";
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean doesExist() {
/*  73 */     fillDBUrl(null);
/*     */     
/*  75 */     return GDDBUtil.doesExist(dbURL);
/*     */   }
/*     */   
/*     */   public static boolean doesExist(String mod) {
/*  79 */     fillDBUrl(mod);
/*     */     
/*  81 */     return GDDBUtil.doesExist(dbURL);
/*     */   }
/*     */   
/*     */   public static Connection getConnection() throws SQLException {
/*  85 */     String mod = null;
/*     */     
/*  87 */     if (GDStashFrame.iniConfig != null && 
/*  88 */       GDStashFrame.iniConfig.sectHistory != null) {
/*  89 */       mod = GDStashFrame.iniConfig.sectHistory.lastMod;
/*     */     }
/*     */ 
/*     */     
/*  93 */     return getConnection(mod);
/*     */   }
/*     */   
/*     */   private static Connection getConnection(String mod) throws SQLException {
/*  97 */     fillDBUrl(mod);
/*     */     
/*  99 */     String url = dbURL + "create=true;territory=" + "en_US";
/*     */     
/* 101 */     Connection conn = DriverManager.getConnection(url);
/*     */     
/* 103 */     conn.setAutoCommit(false);
/*     */     
/* 105 */     return conn;
/*     */   }
/*     */   
/*     */   public static Connection getOldConnection() throws SQLException {
/* 109 */     String url = "jdbc:derby:db;create=false";
/*     */     
/* 111 */     Connection conn = DriverManager.getConnection(url);
/*     */     
/* 113 */     conn.setAutoCommit(false);
/*     */     
/* 115 */     return conn;
/*     */   }
/*     */   
/*     */   public static void closeConnections() {
/* 119 */     if (dbURL != null) {
/*     */       
/*     */       try {
/* 122 */         DriverManager.getConnection("jdbc:derby:;shutdown=true");
/*     */       }
/* 124 */       catch (SQLException sQLException) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createConfigTables() throws SQLException {
/* 135 */     try (Connection conn = getConnection()) {
/* 136 */       if (GDDBUtil.tableExists(conn, "INFO_CONFIG")) {
/* 137 */         String version = DBConfig.getConfigVersion();
/* 138 */         if (version == null || !version.equals("1.0.8")) {
/* 139 */           DBConfig.createTable();
/*     */         }
/*     */       } else {
/* 142 */         DBConfig.createTable();
/*     */       } 
/*     */       
/* 145 */       if (!GDDBUtil.tableExists(conn, "STASH_ITEM_V8")) {
/* 146 */         DBStashItem.createTable();
/*     */       }
/*     */       
/* 149 */       conn.commit();
/*     */     }
/* 151 */     catch (SQLException ex) {
/* 152 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean createDataTables() {
/* 157 */     boolean success = false;
/*     */     
/*     */     try {
/* 160 */       DBItem.createTables();
/* 161 */       DBItemSet.createTables();
/* 162 */       DBAffix.createTables();
/* 163 */       DBAffixSet.createTables();
/* 164 */       DBFormulaSet.createTables();
/* 165 */       DBLootTable.createTables();
/* 166 */       DBLootTableSet.createTables();
/* 167 */       DBSkillTree.createTables();
/* 168 */       DBSkill.createTables();
/* 169 */       DBPet.createTables();
/* 170 */       DBPetBio.createTables();
/* 171 */       DBController.createTables();
/*     */       
/* 173 */       DBShrine.createTables();
/* 174 */       DBConstellation.createTables();
/* 175 */       DBSkillButton.createTable();
/* 176 */       DBBitmap.createTable();
/* 177 */       DBInventoryGrid.createTable();
/* 178 */       DBCaravanWindow.createTable();
/* 179 */       DBClassTable.createTables();
/* 180 */       DBEngineGame.createTable();
/* 181 */       DBFaction.createTable();
/* 182 */       DBEngineSkillMaster.createTables();
/* 183 */       DBMerchant.createTables();
/* 184 */       DBMerchantTableSet.createTables();
/* 185 */       DBMerchantTable.createTables();
/* 186 */       DBEnginePlayer.createTable();
/* 187 */       DBEngineLevel.createTable();
/* 188 */       DBEngineTagText.createTable();
/*     */       
/* 190 */       success = true;
/*     */     }
/* 192 */     catch (Exception ex) {
/* 193 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 196 */     return success;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearBuffers() {
/* 202 */     DBAffix.clearBuffer();
/* 203 */     DBAffixSet.clearBuffer();
/* 204 */     DBFormulaSet.clearBuffer();
/* 205 */     DBItem.clearBuffer();
/* 206 */     DBItemCraft.clearBuffer();
/* 207 */     DBLootTable.clearBuffer();
/* 208 */     DBLootTableSet.clearBuffer();
/* 209 */     DBSkill.clearBuffer();
/* 210 */     DBSkillTree.clearBuffer();
/* 211 */     DBPet.clearBuffer();
/* 212 */     DBPetBio.clearBuffer();
/*     */     
/* 214 */     DBShrine.clearBuffer();
/* 215 */     DBEngineTagText.clearBuffer();
/* 216 */     DBBitmap.clearBuffer();
/* 217 */     DBInventoryGrid.clearBuffer();
/* 218 */     DBCaravanWindow.clearBuffer();
/* 219 */     DBClassTable.clearBuffer();
/* 220 */     DBController.clearBuffer();
/*     */   }
/*     */   
/*     */   public static boolean insertData(ARZRecord[] records, boolean suppressTagWarning) {
/* 224 */     boolean success = false;
/*     */     
/*     */     try {
/* 227 */       insertRecords(records, suppressTagWarning);
/*     */       
/* 229 */       success = true;
/*     */     }
/* 231 */     catch (Exception ex) {
/* 232 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 235 */     return success;
/*     */   }
/*     */   
/*     */   private static void insertRecords(ARZRecord[] records, boolean suppressTagWarning) throws SQLException {
/* 239 */     for (int i = 0; i < records.length; i++) {
/* 240 */       if (records[i] == null) {
/*     */         continue;
/*     */       }
/*     */       
/*     */       try {
/* 245 */         boolean processed = false;
/*     */         
/* 247 */         if (records[i].isBitmap()) {
/* 248 */           DBBitmap.insert(records[i]);
/*     */           
/* 250 */           processed = true;
/*     */         } 
/*     */         
/* 253 */         if (records[i].isInventoryGrid()) {
/* 254 */           DBInventoryGrid.insert(records[i]);
/*     */           
/* 256 */           processed = true;
/*     */         } 
/*     */         
/* 259 */         if (records[i].isCaravanWindow()) {
/* 260 */           DBCaravanWindow.insert(records[i]);
/*     */           
/* 262 */           processed = true;
/*     */         } 
/*     */         
/* 265 */         if (records[i].isClassTable()) {
/* 266 */           DBClassTable.insert(records[i]);
/*     */           
/* 268 */           processed = true;
/*     */         } 
/*     */         
/* 271 */         if (records[i].isSkillMaster()) {
/* 272 */           DBEngineSkillMaster.insert(records[i]);
/*     */           
/* 274 */           processed = true;
/*     */         } 
/*     */         
/* 277 */         if (records[i].isConstellation()) {
/* 278 */           DBConstellation.insert(records[i]);
/*     */           
/* 280 */           processed = true;
/*     */         } 
/*     */         
/* 283 */         if (records[i].isSkillButton()) {
/* 284 */           DBSkillButton.insert(records[i]);
/*     */           
/* 286 */           processed = true;
/*     */         } 
/*     */         
/* 289 */         if (records[i].isShrine()) {
/* 290 */           DBShrine.insert(records[i]);
/*     */           
/* 292 */           processed = true;
/*     */         } 
/*     */ 
/*     */         
/* 296 */         if (records[i].isGameEngine()) {
/*     */           
/* 298 */           DBEngineGame.insert(records[i]);
/*     */           
/* 300 */           processed = true;
/*     */         } 
/*     */ 
/*     */         
/* 304 */         if (records[i].isFaction()) {
/* 305 */           DBFaction.insert(records[i]);
/*     */           
/* 307 */           processed = true;
/*     */         } 
/*     */         
/* 310 */         if (records[i].isMerchant()) {
/* 311 */           DBMerchant.insert(records[i]);
/*     */           
/* 313 */           processed = true;
/*     */         } 
/*     */         
/* 316 */         if (records[i].isMerchantTableSet()) {
/* 317 */           DBMerchantTableSet.insert(records[i]);
/*     */           
/* 319 */           processed = true;
/*     */         } 
/*     */         
/* 322 */         if (records[i].isMerchantTable()) {
/* 323 */           DBMerchantTable.insert(records[i]);
/*     */           
/* 325 */           processed = true;
/*     */         } 
/*     */ 
/*     */         
/* 329 */         if (records[i].isPlayerEngine()) {
/*     */           
/* 331 */           DBEnginePlayer.insert(records[i]);
/*     */ 
/*     */           
/* 334 */           DBEngineLevel.insert(records[i]);
/*     */           
/* 336 */           processed = true;
/*     */         } 
/*     */ 
/*     */         
/* 340 */         if (records[i].isAffix()) {
/*     */           
/* 342 */           DBAffix.insert(records[i]);
/*     */           
/* 344 */           processed = true;
/*     */         } 
/*     */         
/* 347 */         if (records[i].isAffixSet()) {
/*     */           
/* 349 */           DBAffixSet.insert(records[i]);
/*     */           
/* 351 */           processed = true;
/*     */         } 
/*     */ 
/*     */         
/* 355 */         if (records[i].isItemSet()) {
/*     */           
/* 357 */           DBItemSet.insert(records[i]);
/*     */           
/* 359 */           processed = true;
/*     */         } 
/*     */ 
/*     */         
/* 363 */         if (records[i].isLootTable()) {
/*     */           
/* 365 */           DBLootTable.insert(records[i]);
/*     */           
/* 367 */           processed = true;
/*     */         } 
/*     */         
/* 370 */         if (records[i].isLootTableSet()) {
/*     */           
/* 372 */           DBLootTableSet.insert(records[i]);
/*     */           
/* 374 */           processed = true;
/*     */         } 
/*     */ 
/*     */         
/* 378 */         if (records[i].isFormulaSet()) {
/*     */           
/* 380 */           DBFormulaSet.insert(records[i]);
/*     */           
/* 382 */           processed = true;
/*     */         } 
/*     */         
/* 385 */         if (records[i].isSkillTree()) {
/* 386 */           DBSkillTree.insert(records[i]);
/*     */           
/* 388 */           processed = true;
/*     */         } 
/*     */         
/* 391 */         if (records[i].isSkill()) {
/* 392 */           DBSkill.insert(records[i]);
/*     */           
/* 394 */           processed = true;
/*     */         } 
/*     */         
/* 397 */         if (records[i].isPet()) {
/* 398 */           DBPet.insert(records[i]);
/*     */           
/* 400 */           processed = true;
/*     */         } 
/*     */         
/* 403 */         if (records[i].isPetBio()) {
/* 404 */           DBPetBio.insert(records[i]);
/*     */           
/* 406 */           processed = true;
/*     */         } 
/*     */         
/* 409 */         if (records[i].isController()) {
/* 410 */           DBController.insert(records[i]);
/*     */           
/* 412 */           processed = true;
/*     */         } 
/*     */         
/* 415 */         if (!processed) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 422 */           if (records[i].getFileDescription() != null && 
/* 423 */             records[i].getFileDescription().startsWith("BASE")) {
/* 424 */             boolean skipItem = true;
/*     */             
/* 426 */             if (records[i].getFileName().startsWith("records/items/gq_")) skipItem = false; 
/* 427 */             if (records[i].getFileName().startsWith("records/tq/items/")) skipItem = false;
/*     */             
/* 429 */             if (records[i].getFileName().equals("records/items/gearaccessories/medals/a200_medal.dbr")) skipItem = false;
/*     */             
/* 431 */             if (skipItem) {
/*     */               continue;
/*     */             }
/*     */           } 
/*     */           
/* 436 */           if (records[i].getItemName() == null) {
/* 437 */             if (records[i].getItemNameTag() != null && 
/* 438 */               !suppressTagWarning) {
/* 439 */               Object[] args = { records[i].getItemNameTag(), "tags_items.txt" };
/* 440 */               String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_NOT_FOUND", args);
/*     */               
/* 442 */               GDMsgLogger.addWarning(msg);
/*     */ 
/*     */             
/*     */             }
/*     */ 
/*     */ 
/*     */           
/*     */           }
/* 450 */           else if (records[i].getBitmapID() != null) {
/*     */ 
/*     */             
/* 453 */             DBItem.insert(records[i]);
/*     */           } 
/*     */         } 
/* 456 */       } catch (Exception ex) {
/* 457 */         GDMsgLogger.addError(ex);
/*     */       } 
/*     */       continue;
/*     */     } 
/* 461 */     clearBuffers();
/*     */     
/* 463 */     if (GDMsgLogger.severeErrorsInLog()) {
/* 464 */       throw new SQLException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_DB_IMPORT"));
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean updateStash() {
/* 469 */     boolean success = false;
/*     */     
/*     */     try {
/* 472 */       boolean updated = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 480 */       if (GDDBUtil.doesExist("jdbc:derby:db;")) {
/* 481 */         if (GDDBUtil.tableExists(getOldConnection(), "STASH_ITEM")) {
/* 482 */           moveStash(getOldConnection(), getConnection(), "STASH_ITEM");
/*     */           
/* 484 */           updated = true;
/*     */         }
/* 486 */         else if (GDDBUtil.tableExists(getOldConnection(), "STASH_ITEM_V2")) {
/* 487 */           moveStash(getOldConnection(), getConnection(), "STASH_ITEM_V2");
/*     */           
/* 489 */           updated = true;
/*     */         }
/* 491 */         else if (GDDBUtil.tableExists(getOldConnection(), "STASH_ITEM_V3")) {
/* 492 */           moveStash(getOldConnection(), getConnection(), "STASH_ITEM_V3");
/*     */           
/* 494 */           updated = true;
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 500 */       if (!updated) {
/* 501 */         if (GDDBUtil.tableExists("STASH_ITEM")) {
/* 502 */           convertStash("STASH_ITEM");
/*     */         }
/* 504 */         else if (GDDBUtil.tableExists("STASH_ITEM_V2")) {
/* 505 */           convertStash("STASH_ITEM_V2");
/*     */         }
/* 507 */         else if (GDDBUtil.tableExists("STASH_ITEM_V3")) {
/* 508 */           convertStash("STASH_ITEM_V3");
/*     */         }
/* 510 */         else if (GDDBUtil.tableExists("STASH_ITEM_V4")) {
/* 511 */           convertStash("STASH_ITEM_V4");
/*     */         }
/* 513 */         else if (GDDBUtil.tableExists("STASH_ITEM_V5")) {
/* 514 */           convertStash("STASH_ITEM_V5");
/*     */         }
/* 516 */         else if (GDDBUtil.tableExists("STASH_ITEM_V6")) {
/* 517 */           convertStash("STASH_ITEM_V6");
/*     */         }
/* 519 */         else if (GDDBUtil.tableExists("STASH_ITEM_V7")) {
/* 520 */           convertStash("STASH_ITEM_V7");
/*     */         } else {
/* 522 */           updateDependent();
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 532 */       success = !GDMsgLogger.severeErrorsInLog();
/*     */     }
/* 534 */     catch (SQLException ex) {
/* 535 */       GDMsgLogger.addError(ex);
/*     */     }
/* 537 */     catch (Exception ex) {
/* 538 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 541 */     return success;
/*     */   }
/*     */   
/*     */   private static void updateDependent() throws SQLException {
/* 545 */     if (GDDBUtil.tableExists("STASH_ITEM_V8")) {
/* 546 */       List<DBStashItem> items = DBStashItem.getAll();
/*     */       
/* 548 */       DBStashItem.updateDependentFields(items);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void convertStash(String tableName) throws SQLException {
/* 553 */     if (GDDBUtil.tableExists(tableName)) {
/* 554 */       List<DBStashItem> items = DBStashItem.getAllOld(getConnection(), tableName);
/*     */       
/* 556 */       DBStashItem.convertStash(tableName, items);
/*     */       
/* 558 */       DBStashItem.dropOldStash(getConnection(), tableName);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void moveStash(Connection connOld, Connection connNew, String tableName) throws SQLException {
/* 563 */     if (GDDBUtil.tableExists(connOld, tableName)) {
/* 564 */       List<DBStashItem> items = DBStashItem.getAllOld(connOld, tableName);
/*     */       
/* 566 */       DBStashItem.moveStash(connOld, connNew, tableName, items);
/*     */       
/* 568 */       DBStashItem.dropOldStash(connOld, tableName);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateDB() {
/* 574 */     DBEngineGame.reset();
/* 575 */     DBEnginePlayer.reset();
/* 576 */     DBEngineLevel.reset();
/* 577 */     DBEngineSkillMaster.reset();
/*     */     
/* 579 */     DBSkill.updateDB();
/* 580 */     DBSkill.clearBuffer();
/*     */ 
/*     */     
/* 583 */     DBSkillBonus.updateDB();
/* 584 */     DBSkillModifier.updateDB();
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\GDDBData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */