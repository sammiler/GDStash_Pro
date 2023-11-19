/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.util.GDLog;
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
/*     */ public class DBConfig
/*     */   implements Cloneable
/*     */ {
/*     */   public static final String TABLE_NAME = "INFO_CONFIG";
/*     */   private static final int ROW_ID = 1;
/*     */   private static final int ROW_CONFIG_VER = 2;
/*     */   private static final int ROW_CONFIG_INIT = 3;
/*     */   private static final int ROW_GDDB_VER = 4;
/*     */   private static final int ROW_GDDB_INIT = 5;
/*     */   private static final int ROW_GD_LOCAL = 6;
/*     */   private static final int ROW_MOD_STANDALONE = 7;
/*     */   private static final int ROW_NAME_PART_POS1 = 8;
/*     */   private static final int ROW_NAME_PART_POS2 = 9;
/*     */   private static final int ROW_NAME_PART_POS3 = 10;
/*     */   private static final int ROW_NAME_PART_POS4 = 11;
/*     */   private static final int ROW_NAME_PART_POS5 = 12;
/*     */   public static final int NAME_PART_NONE = 0;
/*     */   public static final int NAME_PART_PREFIX = 1;
/*     */   public static final int NAME_PART_QUALITY = 2;
/*     */   public static final int NAME_PART_STYLE = 3;
/*     */   public static final int NAME_PART_NAME = 4;
/*     */   public static final int NAME_PART_SUFFIX = 5;
/*     */   private static DBConfig singleton;
/*  61 */   public String configVer = "1.0.8";
/*     */   public boolean configInit = false;
/*  63 */   public String gddbVer = "1.7.4";
/*     */   public boolean gddbInit = false;
/*  65 */   public String gdLocal = "";
/*     */   public boolean modStandalone = false;
/*  67 */   public int namePart1 = 1;
/*  68 */   public int namePart2 = 2;
/*  69 */   public int namePart3 = 3;
/*  70 */   public int namePart4 = 4;
/*  71 */   public int namePart5 = 5;
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*  76 */     DBConfig config = new DBConfig();
/*     */     
/*  78 */     config.configVer = this.configVer;
/*  79 */     config.configInit = this.configInit;
/*  80 */     config.gddbVer = this.gddbVer;
/*  81 */     config.gddbInit = this.gddbInit;
/*  82 */     config.gdLocal = this.gdLocal;
/*  83 */     config.modStandalone = this.modStandalone;
/*     */     
/*  85 */     config.namePart1 = this.namePart1;
/*  86 */     config.namePart2 = this.namePart2;
/*  87 */     config.namePart3 = this.namePart3;
/*  88 */     config.namePart4 = this.namePart4;
/*  89 */     config.namePart5 = this.namePart5;
/*     */     
/*  91 */     return config;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable() throws SQLException {
/*  99 */     String dropTable = "DROP TABLE INFO_CONFIG";
/* 100 */     String createTable = "CREATE TABLE INFO_CONFIG (ID             VARCHAR(8)   NOT NULL, CONFIG_VER     VARCHAR(8)   NOT NULL, CONFIG_INIT    BOOLEAN, GDDB_VER       VARCHAR(8)   NOT NULL, GDDB_INIT      BOOLEAN, GD_LOCAL       VARCHAR(128), MOD_STANDALONE BOOLEAN, NAME_PART1     INTEGER, NAME_PART2     INTEGER, NAME_PART3     INTEGER, NAME_PART4     INTEGER, NAME_PART5     INTEGER, PRIMARY KEY (ID))";
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
/* 115 */     try (Connection conn = GDDBData.getConnection()) {
/* 116 */       boolean auto = conn.getAutoCommit();
/* 117 */       conn.setAutoCommit(false);
/*     */       
/* 119 */       try (Statement st = conn.createStatement()) {
/* 120 */         DBConfig config = null;
/*     */         
/* 122 */         if (GDDBUtil.tableExists(conn, "INFO_CONFIG")) {
/*     */ 
/*     */           
/* 125 */           config = rescueConfig();
/*     */           
/* 127 */           st.execute(dropTable);
/*     */         } 
/* 129 */         st.execute(createTable);
/* 130 */         st.close();
/*     */         
/* 132 */         conn.commit();
/*     */         
/* 134 */         if (config != null) {
/* 135 */           restoreConfig(config);
/*     */         }
/*     */       }
/* 138 */       catch (SQLException ex) {
/* 139 */         conn.rollback();
/*     */         
/* 141 */         throw ex;
/*     */       } finally {
/*     */         
/* 144 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void insert(DBConfig config) throws SQLException {
/* 150 */     String insert = "INSERT INTO INFO_CONFIG VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
/*     */     
/* 152 */     try (Connection conn = GDDBData.getConnection()) {
/* 153 */       boolean auto = conn.getAutoCommit();
/* 154 */       conn.setAutoCommit(false);
/*     */       
/* 156 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 157 */         ps.setString(1, "DEFAULT");
/* 158 */         ps.setString(2, config.configVer);
/* 159 */         ps.setBoolean(3, config.configInit);
/* 160 */         ps.setString(4, config.gddbVer);
/* 161 */         ps.setBoolean(5, config.gddbInit);
/* 162 */         ps.setString(6, config.gdLocal);
/* 163 */         ps.setBoolean(7, config.modStandalone);
/* 164 */         ps.setInt(8, config.namePart1);
/* 165 */         ps.setInt(9, config.namePart2);
/* 166 */         ps.setInt(10, config.namePart3);
/* 167 */         ps.setInt(11, config.namePart4);
/* 168 */         ps.setInt(12, config.namePart5);
/*     */         
/* 170 */         ps.executeUpdate();
/* 171 */         ps.close();
/*     */         
/* 173 */         conn.commit();
/*     */       }
/* 175 */       catch (SQLException ex) {
/* 176 */         conn.rollback();
/*     */         
/* 178 */         GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_SAVE_CONFIG"));
/* 179 */         GDMsgLogger.addError(ex);
/*     */       } finally {
/*     */         
/* 182 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBConfig get() {
/* 188 */     if (singleton != null) return singleton;
/*     */     
/* 190 */     singleton = new DBConfig();
/*     */     
/* 192 */     String command = "SELECT * FROM INFO_CONFIG WHERE ID = ?";
/*     */     
/* 194 */     try(Connection conn = GDDBData.getConnection(); 
/* 195 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 196 */       ps.setString(1, "DEFAULT");
/*     */       
/* 198 */       try (ResultSet rs = ps.executeQuery()) {
/* 199 */         List<DBConfig> list = wrap(rs);
/*     */         
/* 201 */         if (list.isEmpty()) {
/* 202 */           singleton = null;
/*     */         } else {
/* 204 */           singleton = list.get(0);
/*     */         } 
/*     */         
/* 207 */         conn.commit();
/*     */       }
/* 209 */       catch (SQLException ex) {
/* 210 */         throw ex;
/*     */       }
/*     */     
/* 213 */     } catch (SQLException ex) {
/* 214 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_READ_CONFIG"));
/* 215 */       GDMsgLogger.addError(ex);
/*     */       
/* 217 */       singleton = rescueConfig();
/*     */     } 
/*     */     
/* 220 */     return singleton;
/*     */   }
/*     */   
/*     */   private static List<DBConfig> wrap(ResultSet rs) throws SQLException {
/* 224 */     LinkedList<DBConfig> list = new LinkedList<>();
/*     */     
/* 226 */     while (rs.next()) {
/* 227 */       DBConfig config = new DBConfig();
/*     */       
/* 229 */       config.configVer = rs.getString(2);
/* 230 */       config.configInit = rs.getBoolean(3);
/* 231 */       config.gddbVer = rs.getString(4);
/* 232 */       config.gddbInit = rs.getBoolean(5);
/* 233 */       config.gdLocal = rs.getString(6);
/* 234 */       config.modStandalone = rs.getBoolean(7);
/* 235 */       config.namePart1 = rs.getInt(8);
/* 236 */       config.namePart2 = rs.getInt(9);
/* 237 */       config.namePart3 = rs.getInt(10);
/* 238 */       config.namePart4 = rs.getInt(11);
/* 239 */       config.namePart5 = rs.getInt(12);
/*     */       
/* 241 */       list.add(config);
/*     */     } 
/*     */     
/* 244 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DBConfig rescueConfig() {
/* 252 */     DBConfig config = new DBConfig();
/*     */     
/* 254 */     String id = getConfigID();
/*     */     
/* 256 */     if (id == null) return null;
/*     */     
/* 258 */     config.configVer = getConfigVersion();
/* 259 */     config.configInit = getConfigInit();
/* 260 */     config.gddbVer = getGDDBVersion();
/* 261 */     config.gddbInit = getGDDBInit();
/* 262 */     config.gdLocal = getGDLocal();
/* 263 */     config.modStandalone = getLoadVanilla();
/*     */     
/* 265 */     return config;
/*     */   }
/*     */   
/*     */   private static void restoreConfig(DBConfig config) {
/*     */     try {
/* 270 */       insert(config);
/*     */     }
/* 272 */     catch (SQLException sQLException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getStringValue(String fieldname) {
/* 280 */     String str = null;
/* 281 */     boolean found = false;
/*     */     
/* 283 */     String command = "SELECT " + fieldname + " FROM " + "INFO_CONFIG" + " WHERE ID = ?";
/*     */     
/* 285 */     try(Connection conn = GDDBData.getConnection(); 
/* 286 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 287 */       ps.setString(1, "DEFAULT");
/*     */       
/* 289 */       try (ResultSet rs = ps.executeQuery()) {
/* 290 */         if (rs.next()) {
/* 291 */           str = rs.getString(1);
/*     */           
/* 293 */           found = true;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 298 */         conn.commit();
/*     */       }
/* 300 */       catch (SQLException ex) {
/* 301 */         throw ex;
/*     */       }
/*     */     
/* 304 */     } catch (SQLException ex) {
/* 305 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_READ_CONFIG"));
/* 306 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 309 */     if (!found) str = null;
/*     */     
/* 311 */     return str;
/*     */   }
/*     */   
/*     */   public static String getConfigID() {
/* 315 */     return getStringValue("ID");
/*     */   }
/*     */   
/*     */   public static String getConfigVersion() {
/* 319 */     return getStringValue("CONFIG_VER");
/*     */   }
/*     */   
/*     */   public static String getGDDBVersion() {
/* 323 */     return getStringValue("GDDB_VER");
/*     */   }
/*     */   
/*     */   public static String getGDLocal() {
/* 327 */     return getStringValue("GD_LOCAL");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getIntValue(String fieldname) {
/* 335 */     int i = -1;
/* 336 */     boolean found = false;
/*     */     
/* 338 */     String command = "SELECT " + fieldname + " FROM " + "INFO_CONFIG" + " WHERE ID = ?";
/*     */     
/* 340 */     try(Connection conn = GDDBData.getConnection(); 
/* 341 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 342 */       ps.setString(1, "DEFAULT");
/*     */       
/* 344 */       try (ResultSet rs = ps.executeQuery()) {
/* 345 */         if (rs.next()) {
/* 346 */           i = rs.getInt(1);
/*     */           
/* 348 */           found = true;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 353 */         conn.commit();
/*     */       }
/* 355 */       catch (SQLException ex) {
/* 356 */         throw ex;
/*     */       }
/*     */     
/* 359 */     } catch (SQLException ex) {
/* 360 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_READ_CONFIG"));
/* 361 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 364 */     if (!found) i = -1;
/*     */     
/* 366 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getBoolValue(String fieldname, boolean defaultVal) {
/* 374 */     boolean flag = defaultVal;
/* 375 */     boolean found = false;
/*     */     
/* 377 */     String command = "SELECT " + fieldname + " FROM " + "INFO_CONFIG" + " WHERE ID = ?";
/*     */     
/* 379 */     try(Connection conn = GDDBData.getConnection(); 
/* 380 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 381 */       ps.setString(1, "DEFAULT");
/*     */       
/* 383 */       try (ResultSet rs = ps.executeQuery()) {
/* 384 */         if (rs.next()) {
/* 385 */           flag = rs.getBoolean(1);
/*     */           
/* 387 */           found = true;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 392 */         conn.commit();
/*     */       }
/* 394 */       catch (SQLException ex) {
/* 395 */         throw ex;
/*     */       }
/*     */     
/* 398 */     } catch (SQLException ex) {
/* 399 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_READ_CONFIG"));
/* 400 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 403 */     if (!found) flag = defaultVal;
/*     */     
/* 405 */     return flag;
/*     */   }
/*     */   
/*     */   public static boolean getConfigInit() {
/* 409 */     return getBoolValue("CONFIG_INIT", false);
/*     */   }
/*     */   
/*     */   public static boolean getGDDBInit() {
/* 413 */     return getBoolValue("GDDB_INIT", false);
/*     */   }
/*     */   
/*     */   public static boolean getLoadVanilla() {
/* 417 */     return getBoolValue("MOD_STANDALONE", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean setVersions(String configVer, String gddbVer) {
/* 425 */     String command = null;
/* 426 */     boolean success = false;
/*     */     
/* 428 */     try (Connection conn = GDDBData.getConnection()) {
/* 429 */       boolean auto = conn.getAutoCommit();
/* 430 */       conn.setAutoCommit(false);
/*     */       
/* 432 */       if (configExists(conn)) {
/* 433 */         command = "UPDATE INFO_CONFIG SET CONFIG_VER = ?, GDDB_VER = ? WHERE ID = ?";
/*     */         
/* 435 */         try (PreparedStatement ps = conn.prepareStatement(command)) {
/* 436 */           ps.setString(1, configVer);
/* 437 */           ps.setString(2, gddbVer);
/* 438 */           ps.setString(3, "DEFAULT");
/*     */           
/* 440 */           ps.executeUpdate();
/* 441 */           ps.close();
/*     */           
/* 443 */           conn.commit();
/*     */           
/* 445 */           success = true;
/*     */         }
/* 447 */         catch (SQLException ex) {
/* 448 */           conn.rollback();
/*     */           
/* 450 */           throw ex;
/*     */         } finally {
/*     */           
/* 453 */           conn.setAutoCommit(auto);
/*     */         } 
/*     */       } else {
/*     */         
/*     */         try {
/* 458 */           DBConfig config = new DBConfig();
/* 459 */           config.configVer = configVer;
/* 460 */           config.gddbVer = gddbVer;
/* 461 */           insert(config);
/*     */           
/* 463 */           success = true;
/*     */         }
/* 465 */         catch (SQLException ex) {
/* 466 */           success = false;
/*     */           
/* 468 */           throw ex;
/*     */         }
/*     */       
/*     */       } 
/* 472 */     } catch (SQLException ex) {
/* 473 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_SAVE_CONFIG"));
/* 474 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 477 */     return success;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean setConfigInit(String configVer, String gddbVer, boolean init) {
/* 485 */     String command = null;
/* 486 */     boolean success = false;
/*     */     
/* 488 */     try (Connection conn = GDDBData.getConnection()) {
/* 489 */       boolean auto = conn.getAutoCommit();
/* 490 */       conn.setAutoCommit(false);
/*     */       
/* 492 */       if (configExists(conn)) {
/* 493 */         command = "UPDATE INFO_CONFIG SET CONFIG_INIT = ? WHERE ID = ?";
/*     */         
/* 495 */         try (PreparedStatement ps = conn.prepareStatement(command)) {
/* 496 */           ps.setBoolean(1, init);
/* 497 */           ps.setString(2, "DEFAULT");
/*     */           
/* 499 */           ps.executeUpdate();
/* 500 */           ps.close();
/*     */           
/* 502 */           conn.commit();
/*     */           
/* 504 */           success = true;
/*     */         }
/* 506 */         catch (SQLException ex) {
/* 507 */           conn.rollback();
/*     */           
/* 509 */           throw ex;
/*     */         } finally {
/*     */           
/* 512 */           conn.setAutoCommit(auto);
/*     */         } 
/*     */       } else {
/*     */         
/*     */         try {
/* 517 */           DBConfig config = new DBConfig();
/* 518 */           config.configVer = configVer;
/* 519 */           config.gddbVer = gddbVer;
/* 520 */           config.configInit = init;
/* 521 */           insert(config);
/*     */           
/* 523 */           success = true;
/*     */         }
/* 525 */         catch (SQLException ex) {
/* 526 */           success = false;
/*     */           
/* 528 */           throw ex;
/*     */         }
/*     */       
/*     */       } 
/* 532 */     } catch (SQLException ex) {
/* 533 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_SAVE_CONFIG"));
/* 534 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 537 */     return success;
/*     */   }
/*     */   
/*     */   public static boolean setGDDBInit(String configVer, String gddbVer, boolean init) {
/* 541 */     String command = null;
/* 542 */     boolean success = false;
/*     */     
/* 544 */     try (Connection conn = GDDBData.getConnection()) {
/* 545 */       boolean auto = conn.getAutoCommit();
/* 546 */       conn.setAutoCommit(false);
/*     */       
/* 548 */       if (configExists(conn)) {
/* 549 */         command = "UPDATE INFO_CONFIG SET GDDB_INIT = ? WHERE ID = ?";
/*     */         
/* 551 */         try (PreparedStatement ps = conn.prepareStatement(command)) {
/* 552 */           ps.setBoolean(1, init);
/* 553 */           ps.setString(2, "DEFAULT");
/*     */           
/* 555 */           ps.executeUpdate();
/* 556 */           ps.close();
/*     */           
/* 558 */           conn.commit();
/*     */           
/* 560 */           success = true;
/*     */         }
/* 562 */         catch (SQLException ex) {
/* 563 */           conn.rollback();
/*     */           
/* 565 */           throw ex;
/*     */         } finally {
/*     */           
/* 568 */           conn.setAutoCommit(auto);
/*     */         } 
/*     */       } else {
/*     */         
/*     */         try {
/* 573 */           DBConfig config = new DBConfig();
/* 574 */           config.configVer = configVer;
/* 575 */           config.gddbVer = gddbVer;
/* 576 */           config.gddbInit = init;
/* 577 */           insert(config);
/*     */           
/* 579 */           success = true;
/*     */         }
/* 581 */         catch (SQLException ex) {
/* 582 */           success = false;
/*     */           
/* 584 */           throw ex;
/*     */         }
/*     */       
/*     */       } 
/* 588 */     } catch (SQLException ex) {
/* 589 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_SAVE_CONFIG"));
/* 590 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 593 */     return success;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean setItemNameOrder(String configVer, String gddbVer, String order) {
/* 601 */     if (order == null) return false;
/*     */     
/* 603 */     GDLog tempLog = new GDLog();
/* 604 */     int[] nameOrder = determineItemNameOrder(order, tempLog);
/*     */     
/* 606 */     if (tempLog.containsErrors()) {
/* 607 */       GDMsgLogger.addLog(tempLog);
/*     */       
/* 609 */       return false;
/*     */     } 
/*     */     
/* 612 */     return writeItemNameOrder(configVer, gddbVer, nameOrder);
/*     */   }
/*     */   
/*     */   private static int[] determineItemNameOrder(String order, GDLog log) {
/* 616 */     int[] nameOrder = new int[5];
/* 617 */     for (int i = 0; i < nameOrder.length; i++) {
/* 618 */       nameOrder[i] = 0;
/*     */     }
/*     */     
/* 621 */     int posOrder = 0;
/* 622 */     String rest = order;
/*     */     
/* 624 */     while (!rest.isEmpty()) {
/* 625 */       int pos = rest.indexOf("}");
/*     */       
/* 627 */       if (pos == -1) {
/*     */         break;
/*     */       }
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
/* 641 */       String sTag = rest.substring(0, pos + 1);
/*     */       
/* 643 */       String sPart = sTag.substring(sTag.length() - 2, sTag.length() - 1);
/*     */       try {
/* 645 */         int iPart = Integer.valueOf(sPart).intValue();
/*     */         
/* 647 */         if (iPart > 4) {
/* 648 */           throw new NumberFormatException();
/*     */         }
/*     */ 
/*     */         
/* 652 */         iPart++;
/* 653 */         nameOrder[posOrder] = iPart;
/* 654 */         posOrder++;
/*     */       }
/* 656 */       catch (NumberFormatException ex) {
/*     */         break;
/*     */       } 
/*     */       
/* 660 */       rest = rest.substring(pos + 1);
/*     */     } 
/*     */     
/* 663 */     if (!rest.isEmpty()) {
/* 664 */       Object[] args = { order };
/* 665 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_GENDER_TAG", args);
/* 666 */       GDMsgLogger.addError(msg);
/*     */       
/* 668 */       return null;
/*     */     } 
/*     */     
/* 671 */     return nameOrder;
/*     */   }
/*     */   
/*     */   private static boolean writeItemNameOrder(String configVer, String gddbVer, int[] order) {
/* 675 */     String command = null;
/* 676 */     boolean success = false;
/*     */     
/* 678 */     try (Connection conn = GDDBData.getConnection()) {
/* 679 */       boolean auto = conn.getAutoCommit();
/* 680 */       conn.setAutoCommit(false);
/*     */       
/* 682 */       if (configExists(conn)) {
/* 683 */         command = "UPDATE INFO_CONFIG SET NAME_PART1 = ?, NAME_PART2 = ?, NAME_PART3 = ?, NAME_PART4 = ?, NAME_PART5 = ? WHERE ID = ?";
/*     */         
/* 685 */         try (PreparedStatement ps = conn.prepareStatement(command)) {
/* 686 */           ps.setInt(1, order[0]);
/* 687 */           ps.setInt(2, order[1]);
/* 688 */           ps.setInt(3, order[2]);
/* 689 */           ps.setInt(4, order[3]);
/* 690 */           ps.setInt(5, order[4]);
/* 691 */           ps.setString(6, "DEFAULT");
/*     */           
/* 693 */           ps.executeUpdate();
/* 694 */           ps.close();
/*     */           
/* 696 */           conn.commit();
/*     */           
/* 698 */           success = true;
/*     */         }
/* 700 */         catch (SQLException ex) {
/* 701 */           conn.rollback();
/*     */           
/* 703 */           throw ex;
/*     */         } finally {
/*     */           
/* 706 */           conn.setAutoCommit(auto);
/*     */         } 
/*     */       } else {
/*     */         
/*     */         try {
/* 711 */           DBConfig config = new DBConfig();
/* 712 */           config.configVer = configVer;
/* 713 */           config.gddbVer = gddbVer;
/* 714 */           config.namePart1 = order[0];
/* 715 */           config.namePart2 = order[1];
/* 716 */           config.namePart3 = order[2];
/* 717 */           config.namePart4 = order[3];
/* 718 */           config.namePart5 = order[4];
/* 719 */           insert(config);
/*     */           
/* 721 */           success = true;
/*     */         }
/* 723 */         catch (SQLException ex) {
/* 724 */           success = false;
/*     */           
/* 726 */           throw ex;
/*     */         }
/*     */       
/*     */       } 
/* 730 */     } catch (SQLException ex) {
/* 731 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_SAVE_CONFIG"));
/* 732 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 735 */     return success;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean setConfigData(String configVer, String gddbVer, DBConfig config) {
/* 743 */     String command = null;
/* 744 */     boolean success = false;
/*     */     
/* 746 */     try (Connection conn = GDDBData.getConnection()) {
/* 747 */       boolean auto = conn.getAutoCommit();
/* 748 */       conn.setAutoCommit(false);
/*     */       
/* 750 */       if (configExists(conn)) {
/* 751 */         command = "UPDATE INFO_CONFIG SET GD_LOCAL = ?, MOD_STANDALONE = ? WHERE ID = ?";
/*     */ 
/*     */         
/* 754 */         try (PreparedStatement ps = conn.prepareStatement(command)) {
/* 755 */           ps.setString(1, config.gdLocal);
/* 756 */           ps.setBoolean(2, config.modStandalone);
/* 757 */           ps.setString(3, "DEFAULT");
/*     */           
/* 759 */           ps.executeUpdate();
/* 760 */           ps.close();
/*     */           
/* 762 */           conn.commit();
/*     */           
/* 764 */           success = true;
/*     */         }
/* 766 */         catch (SQLException ex) {
/* 767 */           conn.rollback();
/*     */           
/* 769 */           throw ex;
/*     */         } finally {
/*     */           
/* 772 */           conn.setAutoCommit(auto);
/*     */         } 
/*     */       } else {
/*     */         
/*     */         try {
/* 777 */           DBConfig cfg = new DBConfig();
/* 778 */           cfg.configVer = configVer;
/* 779 */           cfg.gddbVer = gddbVer;
/* 780 */           cfg.gdLocal = config.gdLocal;
/* 781 */           cfg.modStandalone = config.modStandalone;
/* 782 */           insert(cfg);
/*     */           
/* 784 */           success = true;
/*     */         }
/* 786 */         catch (SQLException ex) {
/* 787 */           success = false;
/*     */           
/* 789 */           throw ex;
/*     */         }
/*     */       
/*     */       } 
/* 793 */     } catch (SQLException ex) {
/* 794 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_SAVE_CONFIG"));
/* 795 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 798 */     return success;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 806 */     singleton = null;
/*     */   }
/*     */   
/*     */   private static boolean configExists(Connection conn) throws SQLException {
/* 810 */     String select = "SELECT CONFIG_VER FROM INFO_CONFIG WHERE ID = ?";
/*     */     
/* 812 */     boolean found = false;
/*     */     
/* 814 */     if (!GDDBUtil.tableExists(conn, "INFO_CONFIG")) {
/* 815 */       createTable();
/*     */     }
/*     */     
/* 818 */     String version = getConfigVersion();
/* 819 */     if (version == null || !version.equals("1.0.8")) {
/* 820 */       createTable();
/*     */     }
/*     */     
/* 823 */     try (PreparedStatement ps = conn.prepareStatement(select)) {
/* 824 */       if (!GDDBUtil.tableExists(conn, "INFO_CONFIG")) return false;
/*     */       
/* 826 */       ps.setString(1, "DEFAULT");
/*     */       
/* 828 */       ResultSet rs = ps.executeQuery();
/*     */       
/* 830 */       if (rs.next()) {
/* 831 */         found = true;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 836 */       conn.commit();
/*     */     }
/* 838 */     catch (SQLException sQLException) {}
/*     */     
/* 840 */     return found;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */