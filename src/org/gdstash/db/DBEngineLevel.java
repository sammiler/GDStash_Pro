/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.file.ARZRecord;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ import org.gdstash.util.GDMsgLogger;
/*     */ 
/*     */ public class DBEngineLevel
/*     */ {
/*     */   private static final String TABLE_NAME = "GDC_LEVEL";
/*     */   private static final int ROW_LEVEL = 1;
/*     */   private static final int ROW_STAT_POINTS = 2;
/*     */   private static final int ROW_SKILL_POINTS = 3;
/*     */   private static DBEngineLevel singleton;
/*     */   List<LevelStats> levels;
/*     */   
/*     */   public static class LevelStats {
/*     */     private int level;
/*     */     private int statPoints;
/*     */     private int skillPoints;
/*     */     
/*     */     public int getLevel() {
/*  29 */       return this.level;
/*     */     }
/*     */     
/*     */     public int getStatPoints() {
/*  33 */       return this.statPoints;
/*     */     }
/*     */     
/*     */     public int getSkillPoints() {
/*  37 */       return this.skillPoints;
/*     */     }
/*     */     
/*     */     public void setLevel(int level) {
/*  41 */       this.level = level;
/*     */     }
/*     */     
/*     */     public void setStatPoints(int statPoints) {
/*  45 */       this.statPoints = statPoints;
/*     */     }
/*     */     
/*     */     public void setSkillPoints(int skillPoints) {
/*  49 */       this.skillPoints = skillPoints;
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
/*     */   public DBEngineLevel() {
/*  64 */     this.levels = new LinkedList<>();
/*     */   }
/*     */   
/*     */   private DBEngineLevel(ARZRecord record) {
/*  68 */     this.levels = record.getLevelStatList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStatPointsForRange(int start, int end) {
/*  76 */     int min = start;
/*  77 */     int max = end;
/*     */     
/*  79 */     if (min > max) {
/*  80 */       int temp = min;
/*  81 */       min = max;
/*  82 */       max = temp;
/*     */     } 
/*     */     
/*  85 */     max--;
/*  86 */     min--;
/*     */     
/*  88 */     if (max > this.levels.size()) max = this.levels.size(); 
/*  89 */     if (min < 0) min = 0;
/*     */     
/*  91 */     int sum = 0;
/*  92 */     for (LevelStats stats : this.levels) {
/*  93 */       if (stats.level >= max)
/*     */         break; 
/*  95 */       if (stats.level >= min) sum += stats.statPoints;
/*     */     
/*     */     } 
/*  98 */     return sum;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSkillPointsForRange(int start, int end) {
/* 103 */     int min = start;
/* 104 */     int max = end;
/*     */     
/* 106 */     if (min > max) {
/* 107 */       int temp = min;
/* 108 */       min = max;
/* 109 */       max = temp;
/*     */     } 
/*     */     
/* 112 */     max--;
/* 113 */     min--;
/*     */     
/* 115 */     if (max > this.levels.size()) max = this.levels.size(); 
/* 116 */     if (min < 0) min = 0;
/*     */     
/* 118 */     int sum = 0;
/* 119 */     for (LevelStats stats : this.levels) {
/* 120 */       if (stats.level >= max)
/*     */         break; 
/* 122 */       if (stats.level >= min) sum += stats.skillPoints;
/*     */     
/*     */     } 
/* 125 */     return sum;
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
/*     */   public static void createTable() throws SQLException {
/* 137 */     String dropTable = "DROP TABLE GDC_LEVEL";
/* 138 */     String createTable = "CREATE TABLE GDC_LEVEL (LEVEL        INTEGER NOT NULL, STAT_POINTS  INTEGER, SKILL_POINTS INTEGER, PRIMARY KEY (LEVEL))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     try (Connection conn = GDDBData.getConnection()) {
/* 145 */       boolean auto = conn.getAutoCommit();
/* 146 */       conn.setAutoCommit(false);
/*     */       
/* 148 */       try (Statement st = conn.createStatement()) {
/* 149 */         if (GDDBUtil.tableExists(conn, "GDC_LEVEL")) {
/* 150 */           st.execute(dropTable);
/*     */         }
/* 152 */         st.execute(createTable);
/* 153 */         st.close();
/*     */         
/* 155 */         conn.commit();
/*     */       
/*     */       }
/* 158 */       catch (SQLException ex) {
/* 159 */         conn.rollback();
/*     */         
/* 161 */         Object[] args = { "GDC_LEVEL" };
/* 162 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 164 */         GDMsgLogger.addError(msg);
/*     */         
/* 166 */         throw ex;
/*     */       } finally {
/*     */         
/* 169 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete() throws SQLException {
/* 175 */     String deleteEntry = "DELETE FROM GDC_LEVEL";
/*     */     
/* 177 */     try (Connection conn = GDDBData.getConnection()) {
/* 178 */       boolean auto = conn.getAutoCommit();
/* 179 */       conn.setAutoCommit(false);
/*     */       
/* 181 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 182 */         ps.executeUpdate();
/* 183 */         ps.close();
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
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 199 */     if (!record.getFileName().equals("records/creatures/pc/playerlevels.dbr")) {
/*     */       return;
/*     */     }
/*     */     
/* 203 */     singleton = null;
/*     */     
/* 205 */     DBEngineLevel entry = get();
/*     */     
/* 207 */     if (entry != null && 
/* 208 */       entry.levels != null && 
/* 209 */       !entry.levels.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 213 */     DBEngineLevel player = new DBEngineLevel(record);
/*     */     
/* 215 */     if (player.levels == null)
/* 216 */       return;  if (player.levels.isEmpty())
/*     */       return; 
/* 218 */     String insert = "INSERT INTO GDC_LEVEL VALUES (?,?,?)";
/*     */     
/* 220 */     try (Connection conn = GDDBData.getConnection()) {
/* 221 */       boolean auto = conn.getAutoCommit();
/* 222 */       conn.setAutoCommit(false);
/*     */       
/* 224 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 225 */         for (LevelStats stats : player.levels) {
/* 226 */           ps.setInt(1, stats.level);
/* 227 */           ps.setInt(2, stats.statPoints);
/* 228 */           ps.setInt(3, stats.skillPoints);
/*     */           
/* 230 */           ps.executeUpdate();
/*     */         } 
/*     */         
/* 233 */         ps.close();
/*     */         
/* 235 */         conn.commit();
/*     */       }
/* 237 */       catch (SQLException ex) {
/* 238 */         conn.rollback();
/*     */         
/* 240 */         GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_IN_PLAYER_CONFIG"));
/* 241 */         GDMsgLogger.addError(ex);
/*     */       } finally {
/*     */         
/* 244 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBEngineLevel get() {
/* 250 */     if (singleton != null) return singleton;
/*     */     
/* 252 */     singleton = new DBEngineLevel();
/*     */     
/* 254 */     String command = "SELECT * FROM GDC_LEVEL ORDER BY LEVEL";
/*     */     
/* 256 */     try(Connection conn = GDDBData.getConnection(); 
/* 257 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*     */       
/* 259 */       try (ResultSet rs = ps.executeQuery()) {
/* 260 */         singleton.levels = wrap(rs);
/*     */         
/* 262 */         conn.commit();
/*     */       }
/* 264 */       catch (SQLException ex) {
/* 265 */         throw ex;
/*     */       }
/*     */     
/* 268 */     } catch (SQLException ex) {
/* 269 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_READ_CONFIG_PLAYER"));
/* 270 */       GDMsgLogger.addError(ex);
/*     */       
/* 272 */       singleton = null;
/*     */     } 
/*     */     
/* 275 */     return singleton;
/*     */   }
/*     */   
/*     */   private static List<LevelStats> wrap(ResultSet rs) throws SQLException {
/* 279 */     List<LevelStats> list = new LinkedList<>();
/*     */     
/* 281 */     while (rs.next()) {
/* 282 */       LevelStats stats = new LevelStats();
/*     */       
/* 284 */       stats.level = rs.getInt(1);
/* 285 */       stats.statPoints = rs.getInt(2);
/* 286 */       stats.skillPoints = rs.getInt(3);
/*     */       
/* 288 */       list.add(stats);
/*     */     } 
/*     */     
/* 291 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 299 */     singleton = null;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBEngineLevel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */