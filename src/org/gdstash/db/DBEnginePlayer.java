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
/*     */   private static final int ROW_MAX_LEVEL = 12;
/*     */   private static final int ROW_XP_FORMULA = 13;
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
/*     */   private int maxLevel;
/*     */   private String xpFormula;
/*     */   private Expression expression;
/*     */   
/*     */   public DBEnginePlayer() {
/*  64 */     this.baseDex = 50;
/*  65 */     this.baseInt = 50;
/*  66 */     this.baseStr = 50;
/*  67 */     this.baseLife = 250;
/*  68 */     this.baseMana = 250;
/*  69 */     this.skillTrees = new LinkedList<>();
/*     */     
/*  71 */     this.incDex = 8;
/*  72 */     this.incInt = 8;
/*  73 */     this.incStr = 8;
/*  74 */     this.incLife = 20;
/*  75 */     this.incMana = 16;
/*     */     
/*  77 */     this.expression = null;
/*     */   }
/*     */   
/*     */   private DBEnginePlayer(ARZRecord record) {
/*  81 */     this.baseDex = record.getPlayerBaseDex();
/*  82 */     this.baseInt = record.getPlayerBaseInt();
/*  83 */     this.baseStr = record.getPlayerBaseStr();
/*  84 */     this.baseLife = record.getPlayerBaseLife();
/*  85 */     this.baseMana = record.getPlayerBaseMana();
/*  86 */     this.skillTrees = record.getMasteryList();
/*     */     
/*  88 */     this.incDex = record.getPlayerIncDex();
/*  89 */     this.incInt = record.getPlayerIncInt();
/*  90 */     this.incStr = record.getPlayerIncStr();
/*  91 */     this.incLife = record.getPlayerIncLife();
/*  92 */     this.incMana = record.getPlayerIncMana();
/*  93 */     this.maxLevel = record.getPlayerMaxLevel();
/*     */     
/*     */     try {
/*  96 */       setXPFormula(record.getXPFormula());
/*     */     }
/*  98 */     catch (GDParseException ex) {
/*  99 */       GDMsgLogger.addError((Throwable)ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBaseDex() {
/* 108 */     return this.baseDex;
/*     */   }
/*     */   
/*     */   public int getBaseInt() {
/* 112 */     return this.baseInt;
/*     */   }
/*     */   
/*     */   public int getBaseStr() {
/* 116 */     return this.baseStr;
/*     */   }
/*     */   
/*     */   public int getBaseLife() {
/* 120 */     return this.baseLife;
/*     */   }
/*     */   
/*     */   public int getBaseMana() {
/* 124 */     return this.baseMana;
/*     */   }
/*     */   
/*     */   public List<DBEnginePlayerMasteries> getMasteryTreeList() {
/* 128 */     return this.skillTrees;
/*     */   }
/*     */   
/*     */   public boolean containsSkillTreeID(String id) {
/* 132 */     if (this.skillTrees == null) return false;
/*     */     
/* 134 */     return DBEnginePlayerMasteries.containsSkillTreeID(this.skillTrees, id);
/*     */   }
/*     */   
/*     */   public DBEnginePlayerMasteries retrieveID(String id) {
/* 138 */     if (this.skillTrees == null) return null;
/*     */     
/* 140 */     return DBEnginePlayerMasteries.retrieveID(this.skillTrees, id);
/*     */   }
/*     */   
/*     */   public int getIncDex() {
/* 144 */     return this.incDex;
/*     */   }
/*     */   
/*     */   public int getIncInt() {
/* 148 */     return this.incInt;
/*     */   }
/*     */   
/*     */   public int getIncStr() {
/* 152 */     return this.incStr;
/*     */   }
/*     */   
/*     */   public int getIncLife() {
/* 156 */     return this.incLife;
/*     */   }
/*     */   
/*     */   public int getIncMana() {
/* 160 */     return this.incMana;
/*     */   }
/*     */   
/*     */   public int getMaxLevel() {
/* 164 */     return this.maxLevel;
/*     */   }
/*     */   
/*     */   public String getXPFormula() {
/* 168 */     return this.xpFormula;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setXPFormula(String xpFormula) throws GDParseException {
/* 176 */     this.xpFormula = xpFormula;
/*     */     
/* 178 */     if (xpFormula != null) {
/* 179 */       ExpressionBuilder builder = new ExpressionBuilder(xpFormula);
/* 180 */       builder = builder.variables(new String[] { "playerLevel" });
/* 181 */       builder = builder.variables(new String[] { "PLAYERLEVEL" });
/*     */       
/*     */       try {
/* 184 */         this.expression = builder.build();
/*     */       }
/* 186 */       catch (Throwable ex) {
/* 187 */         this.expression = null;
/*     */         
/* 189 */         throw new GDParseException(ex.getMessage());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setPlayerLevel(int level) {
/* 199 */     if (this.expression == null)
/*     */       return; 
/* 201 */     this.expression.setVariable("playerLevel", level);
/* 202 */     this.expression.setVariable("PLAYERLEVEL", level);
/*     */   }
/*     */   
/*     */   private double getPlayerXP() {
/* 206 */     if (this.expression == null) return 0.0D;
/*     */     
/* 208 */     double value = this.expression.evaluate();
/*     */     
/* 210 */     return value;
/*     */   }
/*     */   
/*     */   public int getPlayerXPByLevel(int level) {
/* 214 */     int lvl = 1;
/* 215 */     if (level > lvl) lvl = level;
/*     */     
/* 217 */     if (lvl == 1) return 0;
/*     */     
/* 219 */     setPlayerLevel(lvl - 1);
/*     */     
/* 221 */     return (int)(getPlayerXP() + 0.5D);
/*     */   }
/*     */   
/*     */   public int getPlayerLevelByXP(int xp) {
/* 225 */     int currLevel = 1;
/* 226 */     int currXP = 0;
/*     */     
/* 228 */     while (currXP < xp) {
/* 229 */       currLevel++;
/* 230 */       setPlayerLevel(currLevel);
/*     */       
/* 232 */       currXP = (int)getPlayerXP();
/*     */     } 
/*     */     
/* 235 */     return currLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable() throws SQLException {
/* 243 */     String dropTable = "DROP TABLE GDC_PLAYER";
/* 244 */     String createTable = "CREATE TABLE GDC_PLAYER (ID          VARCHAR(8) NOT NULL, BASE_DEX    INTEGER, BASE_INT    INTEGER, BASE_STR    INTEGER, BASE_LIFE   INTEGER, BASE_MANA   INTEGER, INC_DEX     INTEGER, INC_INT     INTEGER, INC_STR     INTEGER, INC_LIFE    INTEGER, INC_MANA    INTEGER, MAX_LEVEL   INTEGER, XP_FORMULA  VARCHAR(256), PRIMARY KEY (ID))";
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
/* 260 */     try (Connection conn = GDDBData.getConnection()) {
/* 261 */       boolean auto = conn.getAutoCommit();
/* 262 */       conn.setAutoCommit(false);
/*     */       
/* 264 */       try (Statement st = conn.createStatement()) {
/* 265 */         if (GDDBUtil.tableExists(conn, "GDC_PLAYER")) {
/* 266 */           st.execute(dropTable);
/*     */         }
/* 268 */         st.execute(createTable);
/* 269 */         st.close();
/*     */         
/* 271 */         conn.commit();
/*     */         
/* 273 */         DBEnginePlayerMasteries.createTable(conn);
/*     */       }
/* 275 */       catch (SQLException ex) {
/* 276 */         conn.rollback();
/*     */         
/* 278 */         Object[] args = { "GDC_PLAYER" };
/* 279 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 281 */         GDMsgLogger.addError(msg);
/*     */         
/* 283 */         throw ex;
/*     */       } finally {
/*     */         
/* 286 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete() throws SQLException {
/* 292 */     String deleteEntry = "DELETE FROM GDC_PLAYER WHERE ID = ?";
/*     */     
/* 294 */     try (Connection conn = GDDBData.getConnection()) {
/* 295 */       boolean auto = conn.getAutoCommit();
/* 296 */       conn.setAutoCommit(false);
/*     */       
/* 298 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 299 */         ps.setString(1, "DEFAULT");
/* 300 */         ps.executeUpdate();
/* 301 */         ps.close();
/*     */         
/* 303 */         DBEnginePlayerMasteries.delete(conn);
/*     */         
/* 305 */         conn.commit();
/*     */       }
/* 307 */       catch (SQLException ex) {
/* 308 */         conn.rollback();
/*     */       } finally {
/*     */         
/* 311 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 314 */     } catch (SQLException sQLException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 319 */     DBEnginePlayer player = new DBEnginePlayer(record);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 326 */     singleton = null;
/*     */     
/* 328 */     DBEnginePlayer entry = get();
/*     */     
/* 330 */     if (entry != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 335 */       if (entry.maxLevel != 0 && 
/* 336 */         player.maxLevel != 0)
/*     */         return; 
/* 338 */       if (entry.baseLife != 0 && 
/* 339 */         player.baseLife != 0) {
/*     */         return;
/*     */       }
/*     */     } 
/* 343 */     boolean levels = record.getFileName().equals("records/creatures/pc/playerlevels.dbr");
/* 344 */     String sql = null;
/*     */     
/* 346 */     if (entry == null) {
/* 347 */       sql = "INSERT INTO GDC_PLAYER VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*     */     }
/* 349 */     else if (levels) {
/* 350 */       sql = "UPDATE GDC_PLAYER SET INC_DEX = ?, INC_INT = ?, INC_STR = ?, INC_LIFE = ?, INC_MANA = ?, MAX_LEVEL = ?, XP_FORMULA = ? WHERE ID = ?";
/*     */     } else {
/*     */       
/* 353 */       sql = "UPDATE GDC_PLAYER SET BASE_DEX = ?, BASE_INT = ?, BASE_STR = ?, BASE_LIFE = ?, BASE_MANA = ? WHERE ID = ?";
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 358 */     try (Connection conn = GDDBData.getConnection()) {
/* 359 */       boolean auto = conn.getAutoCommit();
/* 360 */       conn.setAutoCommit(false);
/*     */       
/* 362 */       try (PreparedStatement ps = conn.prepareStatement(sql)) {
/* 363 */         if (entry == null) {
/* 364 */           ps.setString(1, "DEFAULT");
/* 365 */           ps.setInt(2, player.getBaseDex());
/* 366 */           ps.setInt(3, player.getBaseInt());
/* 367 */           ps.setInt(4, player.getBaseStr());
/* 368 */           ps.setInt(5, player.getBaseLife());
/* 369 */           ps.setInt(6, player.getBaseMana());
/* 370 */           ps.setInt(7, player.getIncDex());
/* 371 */           ps.setInt(8, player.getIncInt());
/* 372 */           ps.setInt(9, player.getIncStr());
/* 373 */           ps.setInt(10, player.getIncLife());
/* 374 */           ps.setInt(11, player.getIncMana());
/* 375 */           ps.setInt(12, player.getMaxLevel());
/* 376 */           ps.setString(13, player.getXPFormula());
/*     */         }
/* 378 */         else if (levels) {
/* 379 */           ps.setInt(1, player.getIncDex());
/* 380 */           ps.setInt(2, player.getIncInt());
/* 381 */           ps.setInt(3, player.getIncStr());
/* 382 */           ps.setInt(4, player.getIncLife());
/* 383 */           ps.setInt(5, player.getIncMana());
/* 384 */           ps.setInt(6, player.getMaxLevel());
/* 385 */           ps.setString(7, player.getXPFormula());
/* 386 */           ps.setString(8, "DEFAULT");
/*     */         } else {
/* 388 */           ps.setInt(1, player.getBaseDex());
/* 389 */           ps.setInt(2, player.getBaseInt());
/* 390 */           ps.setInt(3, player.getBaseStr());
/* 391 */           ps.setInt(4, player.getBaseLife());
/* 392 */           ps.setInt(5, player.getBaseMana());
/* 393 */           ps.setString(6, "DEFAULT");
/*     */         } 
/*     */ 
/*     */         
/* 397 */         ps.executeUpdate();
/* 398 */         ps.close();
/*     */         
/* 400 */         conn.commit();
/*     */         
/* 402 */         if (!levels) DBEnginePlayerMasteries.insert(conn, player);
/*     */       
/* 404 */       } catch (SQLException ex) {
/* 405 */         conn.rollback();
/*     */         
/* 407 */         GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_IN_PLAYER_CONFIG"));
/* 408 */         GDMsgLogger.addError(ex);
/*     */       } finally {
/*     */         
/* 411 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBEnginePlayer get() {
/* 417 */     if (singleton != null) return singleton;
/*     */     
/* 419 */     String command = "SELECT * FROM GDC_PLAYER WHERE ID = ?";
/*     */     
/* 421 */     try(Connection conn = GDDBData.getConnection(); 
/* 422 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 423 */       ps.setString(1, "DEFAULT");
/*     */       
/* 425 */       try (ResultSet rs = ps.executeQuery()) {
/* 426 */         List<DBEnginePlayer> list = wrap(rs);
/*     */         
/* 428 */         if (list.isEmpty()) {
/* 429 */           singleton = null;
/*     */         } else {
/* 431 */           singleton = list.get(0);
/*     */         } 
/*     */         
/* 434 */         conn.commit();
/*     */       }
/* 436 */       catch (SQLException ex) {
/* 437 */         throw ex;
/*     */       }
/*     */     
/* 440 */     } catch (SQLException ex) {
/* 441 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_READ_CONFIG_PLAYER"));
/* 442 */       GDMsgLogger.addError(ex);
/*     */       
/* 444 */       singleton = null;
/*     */     } 
/*     */     
/* 447 */     return singleton;
/*     */   }
/*     */   
/*     */   private static List<DBEnginePlayer> wrap(ResultSet rs) throws SQLException {
/* 451 */     LinkedList<DBEnginePlayer> list = new LinkedList<>();
/*     */     
/* 453 */     while (rs.next()) {
/* 454 */       DBEnginePlayer player = new DBEnginePlayer();
/*     */       
/* 456 */       player.baseDex = rs.getInt(2);
/* 457 */       player.baseInt = rs.getInt(3);
/* 458 */       player.baseStr = rs.getInt(4);
/* 459 */       player.baseLife = rs.getInt(5);
/* 460 */       player.baseMana = rs.getInt(6);
/* 461 */       player.incDex = rs.getInt(7);
/* 462 */       player.incInt = rs.getInt(8);
/* 463 */       player.incStr = rs.getInt(9);
/* 464 */       player.incLife = rs.getInt(10);
/* 465 */       player.incMana = rs.getInt(11);
/* 466 */       player.maxLevel = rs.getInt(12);
/* 467 */       player.xpFormula = rs.getString(13);
/*     */       
/* 469 */       if (player.xpFormula != null) player.xpFormula = player.xpFormula.toUpperCase(GDConstants.LOCALE_US);
/*     */       
/*     */       try {
/* 472 */         player.setXPFormula(player.xpFormula);
/*     */       }
/* 474 */       catch (GDParseException ex) {
/* 475 */         GDMsgLogger.addError((Throwable)ex);
/*     */       } 
/*     */       
/* 478 */       player.skillTrees = DBEnginePlayerMasteries.get();
/*     */       
/* 480 */       list.add(player);
/*     */     } 
/*     */     
/* 483 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 491 */     singleton = null;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBEnginePlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */