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
/* 177 */       DBClassTable.createTables();
/* 178 */       DBEngineGame.createTable();
/* 179 */       DBFaction.createTable();
/* 180 */       DBEngineSkillMaster.createTables();
/* 181 */       DBMerchant.createTables();
/* 182 */       DBMerchantTableSet.createTables();
/* 183 */       DBMerchantTable.createTables();
/* 184 */       DBEnginePlayer.createTable();
/* 185 */       DBEngineLevel.createTable();
/* 186 */       DBEngineTagText.createTable();
/*     */       
/* 188 */       success = true;
/*     */     }
/* 190 */     catch (Exception ex) {
/* 191 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 194 */     return success;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearBuffers() {
/* 200 */     DBAffix.clearBuffer();
/* 201 */     DBAffixSet.clearBuffer();
/* 202 */     DBFormulaSet.clearBuffer();
/* 203 */     DBItem.clearBuffer();
/* 204 */     DBItemCraft.clearBuffer();
/* 205 */     DBLootTable.clearBuffer();
/* 206 */     DBLootTableSet.clearBuffer();
/* 207 */     DBSkill.clearBuffer();
/* 208 */     DBSkillTree.clearBuffer();
/* 209 */     DBPet.clearBuffer();
/* 210 */     DBPetBio.clearBuffer();
/*     */     
/* 212 */     DBShrine.clearBuffer();
/* 213 */     DBEngineTagText.clearBuffer();
/* 214 */     DBBitmap.clearBuffer();
/* 215 */     DBClassTable.clearBuffer();
/* 216 */     DBController.clearBuffer();
/*     */   }
/*     */   
/*     */   public static boolean insertData(ARZRecord[] records, boolean suppressTagWarning) {
/* 220 */     boolean success = false;
/*     */     
/*     */     try {
/* 223 */       insertRecords(records, suppressTagWarning);
/*     */       
/* 225 */       success = true;
/*     */     }
/* 227 */     catch (Exception ex) {
/* 228 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 231 */     return success;
/*     */   }
/*     */   
/*     */   private static void insertRecords(ARZRecord[] records, boolean suppressTagWarning) throws SQLException {
/* 235 */     for (int i = 0; i < records.length; i++) {
/* 236 */       if (records[i] == null) {
/*     */         continue;
/*     */       }
/*     */       
/*     */       try {
/* 241 */         boolean processed = false;
/*     */         
/* 243 */         if (records[i].isBitmap()) {
/* 244 */           DBBitmap.insert(records[i]);
/*     */           
/* 246 */           processed = true;
/*     */         } 
/*     */         
/* 249 */         if (records[i].isClassTable()) {
/* 250 */           DBClassTable.insert(records[i]);
/*     */           
/* 252 */           processed = true;
/*     */         } 
/*     */         
/* 255 */         if (records[i].isSkillMaster()) {
/* 256 */           DBEngineSkillMaster.insert(records[i]);
/*     */           
/* 258 */           processed = true;
/*     */         } 
/*     */         
/* 261 */         if (records[i].isConstellation()) {
/* 262 */           DBConstellation.insert(records[i]);
/*     */           
/* 264 */           processed = true;
/*     */         } 
/*     */         
/* 267 */         if (records[i].isSkillButton()) {
/* 268 */           DBSkillButton.insert(records[i]);
/*     */           
/* 270 */           processed = true;
/*     */         } 
/*     */         
/* 273 */         if (records[i].isShrine()) {
/* 274 */           DBShrine.insert(records[i]);
/*     */           
/* 276 */           processed = true;
/*     */         } 
/*     */ 
/*     */         
/* 280 */         if (records[i].isGameEngine()) {
/*     */           
/* 282 */           DBEngineGame.insert(records[i]);
/*     */           
/* 284 */           processed = true;
/*     */         } 
/*     */ 
/*     */         
/* 288 */         if (records[i].isFaction()) {
/* 289 */           DBFaction.insert(records[i]);
/*     */           
/* 291 */           processed = true;
/*     */         } 
/*     */         
/* 294 */         if (records[i].isMerchant()) {
/* 295 */           DBMerchant.insert(records[i]);
/*     */           
/* 297 */           processed = true;
/*     */         } 
/*     */         
/* 300 */         if (records[i].isMerchantTableSet()) {
/* 301 */           DBMerchantTableSet.insert(records[i]);
/*     */           
/* 303 */           processed = true;
/*     */         } 
/*     */         
/* 306 */         if (records[i].isMerchantTable()) {
/* 307 */           DBMerchantTable.insert(records[i]);
/*     */           
/* 309 */           processed = true;
/*     */         } 
/*     */ 
/*     */         
/* 313 */         if (records[i].isPlayerEngine()) {
/*     */           
/* 315 */           DBEnginePlayer.insert(records[i]);
/*     */ 
/*     */           
/* 318 */           DBEngineLevel.insert(records[i]);
/*     */           
/* 320 */           processed = true;
/*     */         } 
/*     */ 
/*     */         
/* 324 */         if (records[i].isAffix()) {
/*     */           
/* 326 */           DBAffix.insert(records[i]);
/*     */           
/* 328 */           processed = true;
/*     */         } 
/*     */         
/* 331 */         if (records[i].isAffixSet()) {
/*     */           
/* 333 */           DBAffixSet.insert(records[i]);
/*     */           
/* 335 */           processed = true;
/*     */         } 
/*     */ 
/*     */         
/* 339 */         if (records[i].isItemSet()) {
/*     */           
/* 341 */           DBItemSet.insert(records[i]);
/*     */           
/* 343 */           processed = true;
/*     */         } 
/*     */ 
/*     */         
/* 347 */         if (records[i].isLootTable()) {
/*     */           
/* 349 */           DBLootTable.insert(records[i]);
/*     */           
/* 351 */           processed = true;
/*     */         } 
/*     */         
/* 354 */         if (records[i].isLootTableSet()) {
/*     */           
/* 356 */           DBLootTableSet.insert(records[i]);
/*     */           
/* 358 */           processed = true;
/*     */         } 
/*     */ 
/*     */         
/* 362 */         if (records[i].isFormulaSet()) {
/*     */           
/* 364 */           DBFormulaSet.insert(records[i]);
/*     */           
/* 366 */           processed = true;
/*     */         } 
/*     */         
/* 369 */         if (records[i].isSkillTree()) {
/* 370 */           DBSkillTree.insert(records[i]);
/*     */           
/* 372 */           processed = true;
/*     */         } 
/*     */         
/* 375 */         if (records[i].isSkill()) {
/* 376 */           DBSkill.insert(records[i]);
/*     */           
/* 378 */           processed = true;
/*     */         } 
/*     */         
/* 381 */         if (records[i].isPet()) {
/* 382 */           DBPet.insert(records[i]);
/*     */           
/* 384 */           processed = true;
/*     */         } 
/*     */         
/* 387 */         if (records[i].isPetBio()) {
/* 388 */           DBPetBio.insert(records[i]);
/*     */           
/* 390 */           processed = true;
/*     */         } 
/*     */         
/* 393 */         if (records[i].isController()) {
/* 394 */           DBController.insert(records[i]);
/*     */           
/* 396 */           processed = true;
/*     */         } 
/*     */         
/* 399 */         if (!processed) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 406 */           if (records[i].getFileDescription() != null && 
/* 407 */             records[i].getFileDescription().startsWith("BASE")) {
/* 408 */             boolean skipItem = true;
/*     */             
/* 410 */             if (records[i].getFileName().startsWith("records/items/gq_")) skipItem = false; 
/* 411 */             if (records[i].getFileName().startsWith("records/tq/items/")) skipItem = false;
/*     */             
/* 413 */             if (records[i].getFileName().equals("records/items/gearaccessories/medals/a200_medal.dbr")) skipItem = false;
/*     */             
/* 415 */             if (skipItem) {
/*     */               continue;
/*     */             }
/*     */           } 
/*     */           
/* 420 */           if (records[i].getItemName() == null) {
/* 421 */             if (records[i].getItemNameTag() != null && 
/* 422 */               !suppressTagWarning) {
/* 423 */               Object[] args = { records[i].getItemNameTag(), "tags_items.txt" };
/* 424 */               String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_NOT_FOUND", args);
/*     */               
/* 426 */               GDMsgLogger.addWarning(msg);
/*     */ 
/*     */             
/*     */             }
/*     */ 
/*     */ 
/*     */           
/*     */           }
/* 434 */           else if (records[i].getBitmapID() != null) {
/*     */ 
/*     */             
/* 437 */             DBItem.insert(records[i]);
/*     */           } 
/*     */         } 
/* 440 */       } catch (Exception ex) {
/* 441 */         GDMsgLogger.addError(ex);
/*     */       } 
/*     */       continue;
/*     */     } 
/* 445 */     clearBuffers();
/*     */     
/* 447 */     if (GDMsgLogger.severeErrorsInLog()) {
/* 448 */       throw new SQLException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_DB_IMPORT"));
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean updateStash() {
/* 453 */     boolean success = false;
/*     */     
/*     */     try {
/* 456 */       boolean updated = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 464 */       if (GDDBUtil.doesExist("jdbc:derby:db;")) {
/* 465 */         if (GDDBUtil.tableExists(getOldConnection(), "STASH_ITEM")) {
/* 466 */           moveStash(getOldConnection(), getConnection(), "STASH_ITEM");
/*     */           
/* 468 */           updated = true;
/*     */         }
/* 470 */         else if (GDDBUtil.tableExists(getOldConnection(), "STASH_ITEM_V2")) {
/* 471 */           moveStash(getOldConnection(), getConnection(), "STASH_ITEM_V2");
/*     */           
/* 473 */           updated = true;
/*     */         }
/* 475 */         else if (GDDBUtil.tableExists(getOldConnection(), "STASH_ITEM_V3")) {
/* 476 */           moveStash(getOldConnection(), getConnection(), "STASH_ITEM_V3");
/*     */           
/* 478 */           updated = true;
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 484 */       if (!updated) {
/* 485 */         if (GDDBUtil.tableExists("STASH_ITEM")) {
/* 486 */           convertStash("STASH_ITEM");
/*     */         }
/* 488 */         else if (GDDBUtil.tableExists("STASH_ITEM_V2")) {
/* 489 */           convertStash("STASH_ITEM_V2");
/*     */         }
/* 491 */         else if (GDDBUtil.tableExists("STASH_ITEM_V3")) {
/* 492 */           convertStash("STASH_ITEM_V3");
/*     */         }
/* 494 */         else if (GDDBUtil.tableExists("STASH_ITEM_V4")) {
/* 495 */           convertStash("STASH_ITEM_V4");
/*     */         }
/* 497 */         else if (GDDBUtil.tableExists("STASH_ITEM_V5")) {
/* 498 */           convertStash("STASH_ITEM_V5");
/*     */         }
/* 500 */         else if (GDDBUtil.tableExists("STASH_ITEM_V6")) {
/* 501 */           convertStash("STASH_ITEM_V6");
/*     */         }
/* 503 */         else if (GDDBUtil.tableExists("STASH_ITEM_V7")) {
/* 504 */           convertStash("STASH_ITEM_V7");
/*     */         } else {
/* 506 */           updateDependent();
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 516 */       success = !GDMsgLogger.severeErrorsInLog();
/*     */     }
/* 518 */     catch (SQLException ex) {
/* 519 */       GDMsgLogger.addError(ex);
/*     */     }
/* 521 */     catch (Exception ex) {
/* 522 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 525 */     return success;
/*     */   }
/*     */   
/*     */   private static void updateDependent() throws SQLException {
/* 529 */     if (GDDBUtil.tableExists("STASH_ITEM_V8")) {
/* 530 */       List<DBStashItem> items = DBStashItem.getAll();
/*     */       
/* 532 */       DBStashItem.updateDependentFields(items);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void convertStash(String tableName) throws SQLException {
/* 537 */     if (GDDBUtil.tableExists(tableName)) {
/* 538 */       List<DBStashItem> items = DBStashItem.getAllOld(getConnection(), tableName);
/*     */       
/* 540 */       DBStashItem.convertStash(tableName, items);
/*     */       
/* 542 */       DBStashItem.dropOldStash(getConnection(), tableName);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void moveStash(Connection connOld, Connection connNew, String tableName) throws SQLException {
/* 547 */     if (GDDBUtil.tableExists(connOld, tableName)) {
/* 548 */       List<DBStashItem> items = DBStashItem.getAllOld(connOld, tableName);
/*     */       
/* 550 */       DBStashItem.moveStash(connOld, connNew, tableName, items);
/*     */       
/* 552 */       DBStashItem.dropOldStash(connOld, tableName);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateDB() {
/* 558 */     DBEngineGame.reset();
/* 559 */     DBEnginePlayer.reset();
/* 560 */     DBEngineLevel.reset();
/* 561 */     DBEngineSkillMaster.reset();
/*     */     
/* 563 */     DBSkill.updateDB();
/* 564 */     DBSkill.clearBuffer();
/*     */ 
/*     */     
/* 567 */     DBSkillBonus.updateDB();
/* 568 */     DBSkillModifier.updateDB();
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\GDDBData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */