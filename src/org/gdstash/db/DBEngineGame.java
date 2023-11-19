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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DBEngineGame
/*     */ {
/*     */   private static final String TABLE_NAME = "GDC_GAME";
/*     */   private static final int ROW_ID = 1;
/*     */   private static final int ROW_FACTION_ALT_NEUTRAL_TAG = 2;
/*     */   private static final int ROW_FACTION_ALT_NEUTRAL_TEXT = 3;
/*     */   private static DBEngineGame singleton;
/*     */   private String factionAltNeutralTag;
/*     */   private String factionAltNeutralText;
/*     */   protected List<DBFactionReputation> reputations;
/*     */   protected List<DBEngineMasteryTier> masteryTiers;
/*     */   
/*     */   public DBEngineGame() {
/*  44 */     this.reputations = new LinkedList<>();
/*  45 */     this.masteryTiers = new LinkedList<>();
/*     */   }
/*     */   
/*     */   private DBEngineGame(ARZRecord record) {
/*  49 */     setFactionAltNeutralTag(record.getFactionAltNeutralTag());
/*     */     
/*  51 */     this.reputations = record.getFactionReputationList();
/*  52 */     this.masteryTiers = record.getMasteryTierList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFactionAltNeutralTag() {
/*  60 */     return this.factionAltNeutralTag;
/*     */   }
/*     */   
/*     */   public String getFactionAltNeutralText() {
/*  64 */     return this.factionAltNeutralText;
/*     */   }
/*     */   
/*     */   public List<DBFactionReputation> getReputationList() {
/*  68 */     return this.reputations;
/*     */   }
/*     */   
/*     */   public int getMaxMasteryLevel() {
/*  72 */     if (this.masteryTiers == null) return 0;
/*     */     
/*  74 */     int level = 0;
/*     */     
/*  76 */     for (DBEngineMasteryTier mt : this.masteryTiers) {
/*  77 */       if (mt.getMasteryLevel() > level) level = mt.getMasteryLevel();
/*     */     
/*     */     } 
/*  80 */     return level;
/*     */   }
/*     */   
/*     */   public int getNumReputations() {
/*  84 */     if (this.reputations == null) return 0;
/*     */     
/*  86 */     return this.reputations.size();
/*     */   }
/*     */   
/*     */   public int getNumSkillTiers() {
/*  90 */     if (this.masteryTiers == null) return 0;
/*     */     
/*  92 */     return this.masteryTiers.size();
/*     */   }
/*     */   
/*     */   public DBEngineMasteryTier[] getSkillTierArray() {
/*  96 */     if (this.masteryTiers == null) return null;
/*     */     
/*  98 */     DBEngineMasteryTier[] arr = new DBEngineMasteryTier[getNumSkillTiers()];
/*     */     
/* 100 */     int i = 0;
/* 101 */     for (DBEngineMasteryTier mt : this.masteryTiers) {
/* 102 */       arr[i] = mt;
/*     */       
/* 104 */       i++;
/*     */     } 
/*     */     
/* 107 */     return arr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setFactionAltNeutralTag(String factionAltNeutralTag) {
/* 115 */     this.factionAltNeutralTag = factionAltNeutralTag;
/*     */     
/* 117 */     if (factionAltNeutralTag == null) {
/* 118 */       this.factionAltNeutralText = null;
/*     */     } else {
/* 120 */       this.factionAltNeutralText = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_UI, factionAltNeutralTag, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable() throws SQLException {
/* 129 */     String dropTable = "DROP TABLE GDC_GAME";
/* 130 */     String createTable = "CREATE TABLE GDC_GAME (ID          VARCHAR(8) NOT NULL, FACTION_ALT_NEUTRAL_TAG  VARCHAR(64), FACTION_ALT_NEUTRAL_TEXT VARCHAR(64), PRIMARY KEY (ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     try (Connection conn = GDDBData.getConnection()) {
/* 137 */       boolean auto = conn.getAutoCommit();
/* 138 */       conn.setAutoCommit(false);
/*     */       
/* 140 */       try (Statement st = conn.createStatement()) {
/* 141 */         if (GDDBUtil.tableExists(conn, "GDC_GAME")) {
/* 142 */           st.execute(dropTable);
/*     */         }
/* 144 */         st.execute(createTable);
/* 145 */         st.close();
/*     */         
/* 147 */         conn.commit();
/*     */         
/* 149 */         DBFactionReputation.createTable(conn);
/* 150 */         DBEngineMasteryTier.createTable(conn);
/*     */       }
/* 152 */       catch (SQLException ex) {
/* 153 */         conn.rollback();
/*     */         
/* 155 */         Object[] args = { "GDC_GAME" };
/* 156 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 158 */         GDMsgLogger.addError(msg);
/*     */         
/* 160 */         throw ex;
/*     */       } finally {
/*     */         
/* 163 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete() throws SQLException {
/* 169 */     String deleteEntry = "DELETE FROM GDC_GAME WHERE ID = ?";
/*     */     
/* 171 */     try (Connection conn = GDDBData.getConnection()) {
/* 172 */       boolean auto = conn.getAutoCommit();
/* 173 */       conn.setAutoCommit(false);
/*     */       
/* 175 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 176 */         ps.setString(1, "DEFAULT");
/* 177 */         ps.executeUpdate();
/* 178 */         ps.close();
/*     */         
/* 180 */         DBFactionReputation.delete(conn);
/* 181 */         DBEngineMasteryTier.delete(conn);
/*     */         
/* 183 */         conn.commit();
/*     */       }
/* 185 */       catch (SQLException ex) {
/* 186 */         conn.rollback();
/*     */       } finally {
/*     */         
/* 189 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 192 */     } catch (SQLException sQLException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 197 */     DBEngineGame game = new DBEngineGame(record);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 204 */     singleton = null;
/*     */     
/* 206 */     DBEngineGame entry = get();
/*     */     
/* 208 */     if (entry != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 213 */       if (entry.getNumSkillTiers() > 0 && 
/* 214 */         game.getNumSkillTiers() > 0)
/*     */         return; 
/* 216 */       if (entry.getNumReputations() > 0 && 
/* 217 */         game.getNumReputations() > 0) {
/*     */         return;
/*     */       }
/*     */     } 
/* 221 */     boolean factions = record.getFileName().equals("records/game/gamefactions.dbr");
/* 222 */     String sql = null;
/*     */     
/* 224 */     if (entry == null) {
/* 225 */       sql = "INSERT INTO GDC_GAME VALUES (?,?,?)";
/*     */     }
/* 227 */     else if (factions) {
/* 228 */       sql = "UPDATE GDC_GAME SET FACTION_ALT_NEUTRAL_TAG = ?, FACTION_ALT_NEUTRAL_TEXT = ? WHERE ID = ?";
/*     */     } else {
/*     */       
/* 231 */       sql = null;
/*     */     } 
/*     */ 
/*     */     
/* 235 */     try (Connection conn = GDDBData.getConnection()) {
/* 236 */       boolean auto = conn.getAutoCommit();
/* 237 */       conn.setAutoCommit(false);
/*     */       
/* 239 */       if (sql != null) {
/* 240 */         try (PreparedStatement ps = conn.prepareStatement(sql)) {
/* 241 */           if (entry == null) {
/* 242 */             ps.setString(1, "DEFAULT");
/* 243 */             ps.setString(2, game.getFactionAltNeutralTag());
/* 244 */             ps.setString(3, game.getFactionAltNeutralText());
/*     */           }
/* 246 */           else if (factions) {
/* 247 */             ps.setString(1, game.getFactionAltNeutralTag());
/* 248 */             ps.setString(2, game.getFactionAltNeutralText());
/* 249 */             ps.setString(3, "DEFAULT");
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 255 */           ps.executeUpdate();
/* 256 */           ps.close();
/*     */           
/* 258 */           conn.commit();
/*     */         }
/* 260 */         catch (SQLException ex) {
/* 261 */           conn.rollback();
/*     */           
/* 263 */           GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_IN_GAME_CONFIG"));
/* 264 */           GDMsgLogger.addError(ex);
/*     */         } 
/*     */       }
/*     */       
/*     */       try {
/* 269 */         if (factions) {
/* 270 */           for (DBFactionReputation reputation : game.reputations) {
/* 271 */             DBFactionReputation.insert(conn, record, reputation);
/*     */           }
/*     */         } else {
/* 274 */           for (DBEngineMasteryTier tier : game.masteryTiers) {
/* 275 */             DBEngineMasteryTier.insert(conn, record, tier);
/*     */           }
/*     */         }
/*     */       
/* 279 */       } catch (SQLException ex) {
/* 280 */         conn.rollback();
/*     */         
/* 282 */         GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_IN_GAME_CONFIG"));
/* 283 */         GDMsgLogger.addError(ex);
/*     */       } finally {
/*     */         
/* 286 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBEngineGame get() {
/* 292 */     if (singleton != null) return singleton;
/*     */     
/* 294 */     singleton = new DBEngineGame();
/*     */     
/* 296 */     String command = "SELECT * FROM GDC_GAME WHERE ID = ?";
/*     */     
/* 298 */     try(Connection conn = GDDBData.getConnection(); 
/* 299 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 300 */       ps.setString(1, "DEFAULT");
/*     */       
/* 302 */       try (ResultSet rs = ps.executeQuery()) {
/* 303 */         List<DBEngineGame> list = wrap(rs);
/*     */         
/* 305 */         if (list.isEmpty()) {
/* 306 */           singleton = null;
/*     */         } else {
/* 308 */           singleton = list.get(0);
/*     */         } 
/*     */         
/* 311 */         conn.commit();
/*     */       }
/* 313 */       catch (SQLException ex) {
/* 314 */         throw ex;
/*     */       }
/*     */     
/* 317 */     } catch (SQLException ex) {
/* 318 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_READ_CONFIG_PLAYER"));
/* 319 */       GDMsgLogger.addError(ex);
/*     */       
/* 321 */       singleton = null;
/*     */     } 
/*     */     
/* 324 */     return singleton;
/*     */   }
/*     */   
/*     */   private static List<DBEngineGame> wrap(ResultSet rs) throws SQLException {
/* 328 */     List<DBFactionReputation> reputations = DBFactionReputation.get();
/* 329 */     List<DBEngineMasteryTier> masteryTiers = DBEngineMasteryTier.get();
/*     */     
/* 331 */     LinkedList<DBEngineGame> list = new LinkedList<>();
/*     */     
/* 333 */     while (rs.next()) {
/* 334 */       DBEngineGame game = new DBEngineGame();
/*     */       
/* 336 */       game.factionAltNeutralTag = rs.getString(2);
/* 337 */       game.factionAltNeutralText = rs.getString(3);
/*     */       
/* 339 */       game.reputations = reputations;
/* 340 */       game.masteryTiers = masteryTiers;
/*     */       
/* 342 */       list.add(game);
/*     */     } 
/*     */     
/* 345 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 353 */     singleton = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBEngineGame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */