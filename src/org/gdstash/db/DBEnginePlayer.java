/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.objecthunter.exp4j.Expression;
/*     */ import net.objecthunter.exp4j.ExpressionBuilder;
/*     */ import org.gdstash.file.ARZRecord;
/*     */ import org.gdstash.file.GDParseException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DBEnginePlayer
/*     */ {
/*     */   private static final String TABLE_NAME = "GDC_PLAYER";
/*     */   private static final int ROW_ID = 1;
/*     */   private static final int ROW_BASE_DEX = 2;
/*     */   private static final int ROW_BASE_INT = 3;
/*     */   private static final int ROW_BASE_STR = 4;
/*     */   private static final int ROW_BASE_LIFE = 5;
/*     */   private static final int ROW_BASE_MANA = 6;
/*     */   private static final int ROW_INCREMENT_DEX = 7;
/*     */   private static final int ROW_INCREMENT_INT = 8;
/*     */   private static final int ROW_INCREMENT_STR = 9;
/*     */   private static final int ROW_INCREMENT_LIFE = 10;
/*     */   private static final int ROW_INCREMENT_MANA = 11;
/*     */   private static final int ROW_MAX_DEVOTION = 12;
/*     */   private static final int ROW_MAX_LEVEL = 13;
/*     */   private static final int ROW_XP_FORMULA = 14;
/*     */   private static DBEnginePlayer singleton;
/*     */   private int baseDex;
/*     */   private int baseInt;
/*     */   private int baseStr;
/*     */   private int baseLife;
/*     */   private int baseMana;
/*     */   private List<DBEnginePlayerMasteries> skillTrees;
/*     */   private int incDex;
/*     */   private int incInt;
/*     */   private int incStr;
/*     */   private int incLife;
/*     */   private int incMana;
/*     */   private int maxDevotion;
/*     */   private int maxLevel;
/*     */   private String xpFormula;
/*     */   private Expression expression;
/*     */   
/*     */   public DBEnginePlayer() {
/*  66 */     this.baseDex = 50;
/*  67 */     this.baseInt = 50;
/*  68 */     this.baseStr = 50;
/*  69 */     this.baseLife = 250;
/*  70 */     this.baseMana = 250;
/*  71 */     this.skillTrees = new LinkedList<>();
/*     */     
/*  73 */     this.incDex = 8;
/*  74 */     this.incInt = 8;
/*  75 */     this.incStr = 8;
/*  76 */     this.incLife = 20;
/*  77 */     this.incMana = 16;
/*     */     
/*  79 */     this.expression = null;
/*     */   }
/*     */   
/*     */   private DBEnginePlayer(ARZRecord record) {
/*  83 */     this.baseDex = record.getPlayerBaseDex();
/*  84 */     this.baseInt = record.getPlayerBaseInt();
/*  85 */     this.baseStr = record.getPlayerBaseStr();
/*  86 */     this.baseLife = record.getPlayerBaseLife();
/*  87 */     this.baseMana = record.getPlayerBaseMana();
/*  88 */     this.skillTrees = record.getMasteryList();
/*     */     
/*  90 */     this.incDex = record.getPlayerIncDex();
/*  91 */     this.incInt = record.getPlayerIncInt();
/*  92 */     this.incStr = record.getPlayerIncStr();
/*  93 */     this.incLife = record.getPlayerIncLife();
/*  94 */     this.incMana = record.getPlayerIncMana();
/*  95 */     this.maxDevotion = record.getPlayerMaxDevotion();
/*  96 */     this.maxLevel = record.getPlayerMaxLevel();
/*     */     
/*     */     try {
/*  99 */       setXPFormula(record.getXPFormula());
/*     */     }
/* 101 */     catch (GDParseException ex) {
/* 102 */       GDMsgLogger.addError((Throwable)ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBaseDex() {
/* 111 */     return this.baseDex;
/*     */   }
/*     */   
/*     */   public int getBaseInt() {
/* 115 */     return this.baseInt;
/*     */   }
/*     */   
/*     */   public int getBaseStr() {
/* 119 */     return this.baseStr;
/*     */   }
/*     */   
/*     */   public int getBaseLife() {
/* 123 */     return this.baseLife;
/*     */   }
/*     */   
/*     */   public int getBaseMana() {
/* 127 */     return this.baseMana;
/*     */   }
/*     */   
/*     */   public List<DBEnginePlayerMasteries> getMasteryTreeList() {
/* 131 */     return this.skillTrees;
/*     */   }
/*     */   
/*     */   public boolean containsSkillTreeID(String id) {
/* 135 */     if (this.skillTrees == null) return false;
/*     */     
/* 137 */     return DBEnginePlayerMasteries.containsSkillTreeID(this.skillTrees, id);
/*     */   }
/*     */   
/*     */   public DBEnginePlayerMasteries retrieveID(String id) {
/* 141 */     if (this.skillTrees == null) return null;
/*     */     
/* 143 */     return DBEnginePlayerMasteries.retrieveID(this.skillTrees, id);
/*     */   }
/*     */   
/*     */   public int getIncDex() {
/* 147 */     return this.incDex;
/*     */   }
/*     */   
/*     */   public int getIncInt() {
/* 151 */     return this.incInt;
/*     */   }
/*     */   
/*     */   public int getIncStr() {
/* 155 */     return this.incStr;
/*     */   }
/*     */   
/*     */   public int getIncLife() {
/* 159 */     return this.incLife;
/*     */   }
/*     */   
/*     */   public int getIncMana() {
/* 163 */     return this.incMana;
/*     */   }
/*     */   
/*     */   public int getMaxDevotion() {
/* 167 */     return this.maxDevotion;
/*     */   }
/*     */   
/*     */   public int getMaxLevel() {
/* 171 */     return this.maxLevel;
/*     */   }
/*     */   
/*     */   public String getXPFormula() {
/* 175 */     return this.xpFormula;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setXPFormula(String xpFormula) throws GDParseException {
/* 183 */     this.xpFormula = xpFormula;
/*     */     
/* 185 */     if (xpFormula != null) {
/* 186 */       ExpressionBuilder builder = new ExpressionBuilder(xpFormula);
/* 187 */       builder = builder.variables(new String[] { "playerLevel" });
/* 188 */       builder = builder.variables(new String[] { "PLAYERLEVEL" });
/*     */       
/*     */       try {
/* 191 */         this.expression = builder.build();
/*     */       }
/* 193 */       catch (Throwable ex) {
/* 194 */         this.expression = null;
/*     */         
/* 196 */         throw new GDParseException(ex.getMessage());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setPlayerLevel(int level) {
/* 206 */     if (this.expression == null)
/*     */       return; 
/* 208 */     this.expression.setVariable("playerLevel", level);
/* 209 */     this.expression.setVariable("PLAYERLEVEL", level);
/*     */   }
/*     */   
/*     */   private double getPlayerXP() {
/* 213 */     if (this.expression == null) return 0.0D;
/*     */     
/* 215 */     double value = this.expression.evaluate();
/*     */     
/* 217 */     return value;
/*     */   }
/*     */   
/*     */   public int getPlayerXPByLevel(int level) {
/* 221 */     int lvl = 1;
/* 222 */     if (level > lvl) lvl = level;
/*     */     
/* 224 */     if (lvl == 1) return 0;
/*     */     
/* 226 */     setPlayerLevel(lvl - 1);
/*     */     
/* 228 */     return (int)(getPlayerXP() + 0.5D);
/*     */   }
/*     */   
/*     */   public int getPlayerLevelByXP(int xp) {
/* 232 */     int currLevel = 1;
/* 233 */     int currXP = 0;
/*     */     
/* 235 */     while (currXP < xp) {
/* 236 */       currLevel++;
/* 237 */       setPlayerLevel(currLevel);
/*     */       
/* 239 */       currXP = (int)getPlayerXP();
/*     */     } 
/*     */     
/* 242 */     return currLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable() throws SQLException {
/* 250 */     String dropTable = "DROP TABLE GDC_PLAYER";
/* 251 */     String createTable = "CREATE TABLE GDC_PLAYER (ID           VARCHAR(8) NOT NULL, BASE_DEX     INTEGER, BASE_INT     INTEGER, BASE_STR     INTEGER, BASE_LIFE    INTEGER, BASE_MANA    INTEGER, INC_DEX      INTEGER, INC_INT      INTEGER, INC_STR      INTEGER, INC_LIFE     INTEGER, INC_MANA     INTEGER, MAX_DEVOTION INTEGER, MAX_LEVEL    INTEGER, XP_FORMULA   VARCHAR(256), PRIMARY KEY (ID))";
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
/* 268 */     try (Connection conn = GDDBData.getConnection()) {
/* 269 */       boolean auto = conn.getAutoCommit();
/* 270 */       conn.setAutoCommit(false);
/*     */       
/* 272 */       try (Statement st = conn.createStatement()) {
/* 273 */         if (GDDBUtil.tableExists(conn, "GDC_PLAYER")) {
/* 274 */           st.execute(dropTable);
/*     */         }
/* 276 */         st.execute(createTable);
/* 277 */         st.close();
/*     */         
/* 279 */         conn.commit();
/*     */         
/* 281 */         DBEnginePlayerMasteries.createTable(conn);
/*     */       }
/* 283 */       catch (SQLException ex) {
/* 284 */         conn.rollback();
/*     */         
/* 286 */         Object[] args = { "GDC_PLAYER" };
/* 287 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 289 */         GDMsgLogger.addError(msg);
/*     */         
/* 291 */         throw ex;
/*     */       } finally {
/*     */         
/* 294 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete() throws SQLException {
/* 300 */     String deleteEntry = "DELETE FROM GDC_PLAYER WHERE ID = ?";
/*     */     
/* 302 */     try (Connection conn = GDDBData.getConnection()) {
/* 303 */       boolean auto = conn.getAutoCommit();
/* 304 */       conn.setAutoCommit(false);
/*     */       
/* 306 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 307 */         ps.setString(1, "DEFAULT");
/* 308 */         ps.executeUpdate();
/* 309 */         ps.close();
/*     */         
/* 311 */         DBEnginePlayerMasteries.delete(conn);
/*     */         
/* 313 */         conn.commit();
/*     */       }
/* 315 */       catch (SQLException ex) {
/* 316 */         conn.rollback();
/*     */       } finally {
/*     */         
/* 319 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 322 */     } catch (SQLException sQLException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 327 */     DBEnginePlayer player = new DBEnginePlayer(record);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 334 */     singleton = null;
/*     */     
/* 336 */     DBEnginePlayer entry = get();
/*     */     
/* 338 */     if (entry != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 343 */       if (entry.maxLevel != 0 && 
/* 344 */         player.maxLevel != 0)
/*     */         return; 
/* 346 */       if (entry.baseLife != 0 && 
/* 347 */         player.baseLife != 0) {
/*     */         return;
/*     */       }
/*     */     } 
/* 351 */     boolean levels = record.getFileName().equals("records/creatures/pc/playerlevels.dbr");
/* 352 */     String sql = null;
/*     */     
/* 354 */     if (entry == null) {
/* 355 */       sql = "INSERT INTO GDC_PLAYER VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*     */     }
/* 357 */     else if (levels) {
/* 358 */       sql = "UPDATE GDC_PLAYER SET INC_DEX = ?, INC_INT = ?, INC_STR = ?, INC_LIFE = ?, INC_MANA = ?, MAX_DEVOTION = ?, MAX_LEVEL = ?, XP_FORMULA = ? WHERE ID = ?";
/*     */     } else {
/*     */       
/* 361 */       sql = "UPDATE GDC_PLAYER SET BASE_DEX = ?, BASE_INT = ?, BASE_STR = ?, BASE_LIFE = ?, BASE_MANA = ? WHERE ID = ?";
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 366 */     try (Connection conn = GDDBData.getConnection()) {
/* 367 */       boolean auto = conn.getAutoCommit();
/* 368 */       conn.setAutoCommit(false);
/*     */       
/* 370 */       try (PreparedStatement ps = conn.prepareStatement(sql)) {
/* 371 */         if (entry == null) {
/* 372 */           ps.setString(1, "DEFAULT");
/* 373 */           ps.setInt(2, player.getBaseDex());
/* 374 */           ps.setInt(3, player.getBaseInt());
/* 375 */           ps.setInt(4, player.getBaseStr());
/* 376 */           ps.setInt(5, player.getBaseLife());
/* 377 */           ps.setInt(6, player.getBaseMana());
/* 378 */           ps.setInt(7, player.getIncDex());
/* 379 */           ps.setInt(8, player.getIncInt());
/* 380 */           ps.setInt(9, player.getIncStr());
/* 381 */           ps.setInt(10, player.getIncLife());
/* 382 */           ps.setInt(11, player.getIncMana());
/* 383 */           ps.setInt(12, player.getMaxDevotion());
/* 384 */           ps.setInt(13, player.getMaxLevel());
/* 385 */           ps.setString(14, player.getXPFormula());
/*     */         }
/* 387 */         else if (levels) {
/* 388 */           ps.setInt(1, player.getIncDex());
/* 389 */           ps.setInt(2, player.getIncInt());
/* 390 */           ps.setInt(3, player.getIncStr());
/* 391 */           ps.setInt(4, player.getIncLife());
/* 392 */           ps.setInt(5, player.getIncMana());
/* 393 */           ps.setInt(6, player.getMaxDevotion());
/* 394 */           ps.setInt(7, player.getMaxLevel());
/* 395 */           ps.setString(8, player.getXPFormula());
/* 396 */           ps.setString(9, "DEFAULT");
/*     */         } else {
/* 398 */           ps.setInt(1, player.getBaseDex());
/* 399 */           ps.setInt(2, player.getBaseInt());
/* 400 */           ps.setInt(3, player.getBaseStr());
/* 401 */           ps.setInt(4, player.getBaseLife());
/* 402 */           ps.setInt(5, player.getBaseMana());
/* 403 */           ps.setString(6, "DEFAULT");
/*     */         } 
/*     */ 
/*     */         
/* 407 */         ps.executeUpdate();
/* 408 */         ps.close();
/*     */         
/* 410 */         conn.commit();
/*     */         
/* 412 */         if (!levels) DBEnginePlayerMasteries.insert(conn, player);
/*     */       
/* 414 */       } catch (SQLException ex) {
/* 415 */         conn.rollback();
/*     */         
/* 417 */         GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_IN_PLAYER_CONFIG"));
/* 418 */         GDMsgLogger.addError(ex);
/*     */       } finally {
/*     */         
/* 421 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBEnginePlayer get() {
/* 427 */     if (singleton != null) return singleton;
/*     */     
/* 429 */     String command = "SELECT * FROM GDC_PLAYER WHERE ID = ?";
/*     */     
/* 431 */     try(Connection conn = GDDBData.getConnection(); 
/* 432 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 433 */       ps.setString(1, "DEFAULT");
/*     */       
/* 435 */       try (ResultSet rs = ps.executeQuery()) {
/* 436 */         List<DBEnginePlayer> list = wrap(rs);
/*     */         
/* 438 */         if (list.isEmpty()) {
/* 439 */           singleton = null;
/*     */         } else {
/* 441 */           singleton = list.get(0);
/*     */         } 
/*     */         
/* 444 */         conn.commit();
/*     */       }
/* 446 */       catch (SQLException ex) {
/* 447 */         throw ex;
/*     */       }
/*     */     
/* 450 */     } catch (SQLException ex) {
/* 451 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_READ_CONFIG_PLAYER"));
/* 452 */       GDMsgLogger.addError(ex);
/*     */       
/* 454 */       singleton = null;
/*     */     } 
/*     */     
/* 457 */     return singleton;
/*     */   }
/*     */   
/*     */   private static List<DBEnginePlayer> wrap(ResultSet rs) throws SQLException {
/* 461 */     LinkedList<DBEnginePlayer> list = new LinkedList<>();
/*     */     
/* 463 */     while (rs.next()) {
/* 464 */       DBEnginePlayer player = new DBEnginePlayer();
/*     */       
/* 466 */       player.baseDex = rs.getInt(2);
/* 467 */       player.baseInt = rs.getInt(3);
/* 468 */       player.baseStr = rs.getInt(4);
/* 469 */       player.baseLife = rs.getInt(5);
/* 470 */       player.baseMana = rs.getInt(6);
/* 471 */       player.incDex = rs.getInt(7);
/* 472 */       player.incInt = rs.getInt(8);
/* 473 */       player.incStr = rs.getInt(9);
/* 474 */       player.incLife = rs.getInt(10);
/* 475 */       player.incMana = rs.getInt(11);
/* 476 */       player.maxLevel = rs.getInt(13);
/* 477 */       player.xpFormula = rs.getString(14);
/*     */       
/* 479 */       if (player.xpFormula != null) player.xpFormula = player.xpFormula.toUpperCase(GDConstants.LOCALE_US);
/*     */       
/*     */       try {
/* 482 */         player.setXPFormula(player.xpFormula);
/*     */       }
/* 484 */       catch (GDParseException ex) {
/* 485 */         GDMsgLogger.addError((Throwable)ex);
/*     */       } 
/*     */       
/* 488 */       player.skillTrees = DBEnginePlayerMasteries.get();
/*     */       
/* 490 */       list.add(player);
/*     */     } 
/*     */     
/* 493 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 501 */     singleton = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBEnginePlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */