/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.file.ARCDecompress;
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
/*     */ 
/*     */ public class DBEngineGame
/*     */ {
/*     */   private static final String TABLE_NAME = "GDC_GAME";
/*     */   private static final int ROW_ID = 1;
/*     */   private static final int ROW_FACTION_ALT_NEUTRAL_TAG = 2;
/*     */   private static final int ROW_FACTION_ALT_NEUTRAL_TEXT = 3;
/*     */   private static final int ROW_DEVOTION_CAP = 4;
/*     */   private static DBEngineGame singleton;
/*     */   private String factionAltNeutralTag;
/*     */   private String factionAltNeutralText;
/*     */   private int devotionCap;
/*     */   protected List<DBFaction> factions;
/*     */   protected List<DBFactionReputation> reputations;
/*     */   protected List<DBEngineMasteryTier> masteryTiers;
/*     */   
/*     */   public DBEngineGame() {
/*  44 */     this.factions = new LinkedList<>();
/*  45 */     this.reputations = new LinkedList<>();
/*  46 */     this.masteryTiers = new LinkedList<>();
/*     */   }
/*     */   
/*     */   private DBEngineGame(ARZRecord record) {
/*  50 */     setFactionAltNeutralTag(record.getFactionAltNeutralTag());
/*  51 */     this.devotionCap = record.getDevotionCap();
/*     */     
/*  53 */     this.factions = new LinkedList<>();
/*  54 */     this.reputations = record.getFactionReputationList();
/*  55 */     this.masteryTiers = record.getMasteryTierList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFactionAltNeutralTag() {
/*  63 */     return this.factionAltNeutralTag;
/*     */   }
/*     */   
/*     */   public String getFactionAltNeutralText() {
/*  67 */     return this.factionAltNeutralText;
/*     */   }
/*     */   
/*     */   public int getDevotionCap() {
/*  71 */     return this.devotionCap;
/*     */   }
/*     */   
/*     */   public List<DBFactionReputation> getReputationList() {
/*  75 */     return this.reputations;
/*     */   }
/*     */   
/*     */   public int getMaxMasteryLevel() {
/*  79 */     if (this.masteryTiers == null) return 0;
/*     */     
/*  81 */     int level = 0;
/*     */     
/*  83 */     for (DBEngineMasteryTier mt : this.masteryTiers) {
/*  84 */       if (mt.getMasteryLevel() > level) level = mt.getMasteryLevel();
/*     */     
/*     */     } 
/*  87 */     return level;
/*     */   }
/*     */   
/*     */   public int getNumSkillTiers() {
/*  91 */     if (this.masteryTiers == null) return 0;
/*     */     
/*  93 */     return this.masteryTiers.size();
/*     */   }
/*     */   
/*     */   public DBEngineMasteryTier[] getSkillTierArray() {
/*  97 */     if (this.masteryTiers == null) return null;
/*     */     
/*  99 */     DBEngineMasteryTier[] arr = new DBEngineMasteryTier[getNumSkillTiers()];
/*     */     
/* 101 */     int i = 0;
/* 102 */     for (DBEngineMasteryTier mt : this.masteryTiers) {
/* 103 */       arr[i] = mt;
/*     */       
/* 105 */       i++;
/*     */     } 
/*     */     
/* 108 */     return arr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setFactionAltNeutralTag(String factionAltNeutralTag) {
/* 116 */     this.factionAltNeutralTag = factionAltNeutralTag;
/*     */     
/* 118 */     if (factionAltNeutralTag == null) {
/* 119 */       this.factionAltNeutralText = null;
/*     */     } else {
/* 121 */       this.factionAltNeutralText = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_UI, factionAltNeutralTag, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable() throws SQLException {
/* 130 */     String dropTable = "DROP TABLE GDC_GAME";
/* 131 */     String createTable = "CREATE TABLE GDC_GAME (ID          VARCHAR(8) NOT NULL, FACTION_ALT_NEUTRAL_TAG  VARCHAR(64), FACTION_ALT_NEUTRAL_TEXT VARCHAR(64), DEVOTION_CAP             INTEGER, PRIMARY KEY (ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     try (Connection conn = GDDBData.getConnection()) {
/* 139 */       boolean auto = conn.getAutoCommit();
/* 140 */       conn.setAutoCommit(false);
/*     */       
/* 142 */       try (Statement st = conn.createStatement()) {
/* 143 */         if (GDDBUtil.tableExists(conn, "GDC_GAME")) {
/* 144 */           st.execute(dropTable);
/*     */         }
/* 146 */         st.execute(createTable);
/* 147 */         st.close();
/*     */         
/* 149 */         conn.commit();
/*     */         
/* 151 */         DBFactionReputation.createTable(conn);
/* 152 */         DBEngineMasteryTier.createTable(conn);
/*     */       }
/* 154 */       catch (SQLException ex) {
/* 155 */         conn.rollback();
/*     */         
/* 157 */         Object[] args = { "GDC_GAME" };
/* 158 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 160 */         GDMsgLogger.addError(msg);
/*     */         
/* 162 */         throw ex;
/*     */       } finally {
/*     */         
/* 165 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete() throws SQLException {
/* 171 */     String deleteEntry = "DELETE FROM GDC_GAME WHERE ID = ?";
/*     */     
/* 173 */     try (Connection conn = GDDBData.getConnection()) {
/* 174 */       boolean auto = conn.getAutoCommit();
/* 175 */       conn.setAutoCommit(false);
/*     */       
/* 177 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 178 */         ps.setString(1, "DEFAULT");
/* 179 */         ps.executeUpdate();
/* 180 */         ps.close();
/*     */         
/* 182 */         DBFactionReputation.delete(conn);
/* 183 */         DBEngineMasteryTier.delete(conn);
/*     */         
/* 185 */         conn.commit();
/*     */       }
/* 187 */       catch (SQLException ex) {
/* 188 */         conn.rollback();
/*     */       } finally {
/*     */         
/* 191 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 194 */     } catch (SQLException sQLException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 201 */     singleton = null;
/*     */     
/* 203 */     DBEngineGame entry = get();
/*     */     
/* 205 */     if (entry != null)
/*     */       return; 
/* 207 */     DBEngineGame game = new DBEngineGame(record);
/*     */     
/* 209 */     String insert = "INSERT INTO GDC_GAME VALUES (?,?,?,?)";
/*     */     
/* 211 */     try (Connection conn = GDDBData.getConnection()) {
/* 212 */       boolean auto = conn.getAutoCommit();
/* 213 */       conn.setAutoCommit(false);
/*     */       
/* 215 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 216 */         ps.setString(1, "DEFAULT");
/* 217 */         ps.setString(2, game.getFactionAltNeutralTag());
/* 218 */         ps.setString(3, game.getFactionAltNeutralText());
/* 219 */         ps.setInt(4, game.getDevotionCap());
/*     */         
/* 221 */         ps.executeUpdate();
/* 222 */         ps.close();
/*     */         
/* 224 */         conn.commit();
/*     */         
/* 226 */         for (DBFactionReputation reputation : game.reputations) {
/* 227 */           DBFactionReputation.insert(conn, record, reputation);
/*     */         }
/*     */         
/* 230 */         for (DBEngineMasteryTier tier : game.masteryTiers) {
/* 231 */           DBEngineMasteryTier.insert(conn, record, tier);
/*     */         }
/*     */       }
/* 234 */       catch (SQLException ex) {
/* 235 */         conn.rollback();
/*     */         
/* 237 */         GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_IN_GAME_CONFIG"));
/* 238 */         GDMsgLogger.addError(ex);
/*     */       } finally {
/*     */         
/* 241 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBEngineGame get() {
/* 247 */     if (singleton != null) return singleton;
/*     */     
/* 249 */     singleton = new DBEngineGame();
/*     */     
/* 251 */     String command = "SELECT * FROM GDC_GAME WHERE ID = ?";
/*     */     
/* 253 */     try(Connection conn = GDDBData.getConnection(); 
/* 254 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 255 */       ps.setString(1, "DEFAULT");
/*     */       
/* 257 */       try (ResultSet rs = ps.executeQuery()) {
/* 258 */         List<DBEngineGame> list = wrap(rs);
/*     */         
/* 260 */         if (list.isEmpty()) {
/* 261 */           singleton = null;
/*     */         } else {
/* 263 */           singleton = list.get(0);
/*     */         } 
/*     */         
/* 266 */         conn.commit();
/*     */       }
/* 268 */       catch (SQLException ex) {
/* 269 */         throw ex;
/*     */       }
/*     */     
/* 272 */     } catch (SQLException ex) {
/* 273 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_READ_CONFIG_PLAYER"));
/* 274 */       GDMsgLogger.addError(ex);
/*     */       
/* 276 */       singleton = null;
/*     */     } 
/*     */     
/* 279 */     return singleton;
/*     */   }
/*     */   
/*     */   private static List<DBEngineGame> wrap(ResultSet rs) throws SQLException {
/* 283 */     List<DBFactionReputation> reputations = DBFactionReputation.get();
/* 284 */     List<DBEngineMasteryTier> masteryTiers = DBEngineMasteryTier.get();
/*     */     
/* 286 */     LinkedList<DBEngineGame> list = new LinkedList<>();
/*     */     
/* 288 */     while (rs.next()) {
/* 289 */       DBEngineGame game = new DBEngineGame();
/*     */       
/* 291 */       game.factionAltNeutralTag = rs.getString(2);
/* 292 */       game.factionAltNeutralText = rs.getString(3);
/* 293 */       game.devotionCap = rs.getInt(4);
/*     */       
/* 295 */       game.reputations = reputations;
/* 296 */       game.masteryTiers = masteryTiers;
/*     */       
/* 298 */       list.add(game);
/*     */     } 
/*     */     
/* 301 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 309 */     singleton = null;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBEngineGame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */