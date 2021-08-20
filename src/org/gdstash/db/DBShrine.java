/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.gdstash.file.ARZRecord;
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
/*     */ public class DBShrine
/*     */ {
/*     */   public static final String TABLE_NAME = "GD_SHRINE";
/*     */   public static final String FIELD_ID = "SHRINE_ID";
/*     */   private static final int ROW_SHRINE_ID = 1;
/*     */   private static final int ROW_NAME = 2;
/*     */   private static final int ROW_NORMAL_DISABLED = 3;
/*     */   private static final int ROW_NORMAL_LOCKED = 4;
/*     */   private static final int ROW_EPIC_DISABLED = 5;
/*     */   private static final int ROW_EPIC_LOCKED = 6;
/*     */   private static final int ROW_LEGENDARY_DISABLED = 7;
/*     */   private static final int ROW_LEGENDARY_LOCKED = 8;
/*     */   
/*     */   public static class Info
/*     */   {
/*     */     public boolean exists = false;
/*     */     public boolean active = false;
/*     */   }
/*  48 */   private static ConcurrentHashMap<String, DBShrine> hashBuffer = new ConcurrentHashMap<>();
/*     */   
/*     */   private String shrineID;
/*     */   private String name;
/*     */   private boolean normalDisabled;
/*     */   private boolean normalLocked;
/*     */   private boolean epicDisabled;
/*     */   private boolean epicLocked;
/*     */   private boolean legendaryDisabled;
/*     */   private boolean legendaryLocked;
/*     */   
/*     */   public DBShrine() {
/*  60 */     this.shrineID = null;
/*  61 */     this.name = null;
/*  62 */     this.normalDisabled = false;
/*  63 */     this.normalLocked = false;
/*  64 */     this.epicDisabled = false;
/*  65 */     this.epicLocked = false;
/*  66 */     this.legendaryDisabled = false;
/*  67 */     this.legendaryLocked = false;
/*     */   }
/*     */   
/*     */   private DBShrine(ARZRecord record) {
/*  71 */     this.shrineID = record.getFileName();
/*     */     
/*  73 */     this.name = record.getShrineName();
/*  74 */     this.normalDisabled = record.getShrineNormalDisabled();
/*  75 */     this.normalLocked = record.getShrineNormalLocked();
/*  76 */     this.epicDisabled = record.getShrineEpicDisabled();
/*  77 */     this.epicLocked = record.getShrineEpicLocked();
/*  78 */     this.legendaryDisabled = record.getShrineLegendaryDisabled();
/*  79 */     this.legendaryLocked = record.getShrineLegendaryLocked();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getShrineID() {
/*  87 */     return this.shrineID;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  91 */     return this.name;
/*     */   }
/*     */   
/*     */   public boolean isActive(int difficulty) {
/*  95 */     if (difficulty == 0) {
/*  96 */       return !this.normalDisabled;
/*     */     }
/*     */     
/*  99 */     if (difficulty == 1) {
/* 100 */       return !this.epicDisabled;
/*     */     }
/*     */     
/* 103 */     if (difficulty == 2) {
/* 104 */       return !this.legendaryDisabled;
/*     */     }
/*     */     
/* 107 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean doesExist(String shrineID) {
/* 111 */     String id = shrineID;
/*     */     
/* 113 */     if (!id.startsWith("records/interactive/")) {
/* 114 */       id = "records/interactive/" + id;
/*     */     }
/*     */     
/* 117 */     DBShrine shrine = get(id);
/*     */     
/* 119 */     return (shrine != null);
/*     */   }
/*     */   
/*     */   public static boolean isActive(String shrineID, int difficulty) {
/* 123 */     String id = shrineID;
/*     */     
/* 125 */     if (!id.startsWith("records/interactive/")) {
/* 126 */       id = "records/interactive/" + id;
/*     */     }
/*     */     
/* 129 */     DBShrine shrine = get(id);
/*     */     
/* 131 */     if (shrine == null) return false;
/*     */     
/* 133 */     return shrine.isActive(difficulty);
/*     */   }
/*     */   
/*     */   public static Info getShrineInfo(String shrineID, int difficulty) {
/* 137 */     Info info = new Info();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 143 */       if (!GDDBUtil.tableExists("GD_SHRINE")) return info;
/*     */     
/* 145 */     } catch (SQLException ex) {
/* 146 */       return info;
/*     */     } 
/*     */     
/* 149 */     String id = shrineID;
/*     */     
/* 151 */     if (!id.startsWith("records/")) {
/* 152 */       id = "records/interactive/" + id;
/*     */     }
/*     */     
/* 155 */     DBShrine shrine = get(id);
/*     */     
/* 157 */     info.exists = (shrine != null);
/*     */     
/* 159 */     if (info.exists) {
/* 160 */       info.active = shrine.isActive(difficulty);
/*     */     }
/*     */     
/* 163 */     return info;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearBuffer() {
/* 171 */     hashBuffer.clear();
/*     */   }
/*     */   
/*     */   public static void createTables() throws SQLException {
/* 175 */     String dropTable = "DROP TABLE GD_SHRINE";
/* 176 */     String createTable = "CREATE TABLE GD_SHRINE (SHRINE_ID VARCHAR(256) NOT NULL, NAME               VARCHAR(256), NORMAL_DISBALED    BOOLEAN, NORMAL_LOCKED      BOOLEAN, EPIC_DISBALED      BOOLEAN, EPIC_LOCKED        BOOLEAN, LEGENDARY_DISBALED BOOLEAN, LEGEBNDARY_LOCKED  BOOLEAN, PRIMARY KEY (SHRINE_ID))";
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
/* 189 */     try (Connection conn = GDDBData.getConnection()) {
/* 190 */       boolean auto = conn.getAutoCommit();
/* 191 */       conn.setAutoCommit(false);
/*     */       
/* 193 */       try (Statement st = conn.createStatement()) {
/* 194 */         if (GDDBUtil.tableExists(conn, "GD_SHRINE")) {
/* 195 */           st.execute(dropTable);
/*     */         }
/*     */         
/* 198 */         st.execute(createTable);
/* 199 */         st.close();
/*     */         
/* 201 */         conn.commit();
/*     */       }
/* 203 */       catch (SQLException ex) {
/* 204 */         conn.rollback();
/*     */         
/* 206 */         Object[] args = { "GD_SHRINE" };
/* 207 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 209 */         GDMsgLogger.addError(msg);
/*     */         
/* 211 */         throw ex;
/*     */       } finally {
/*     */         
/* 214 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(String shrineID) throws SQLException {
/* 220 */     String deleteEntry = "DELETE FROM GD_SHRINE WHERE SHRINE_ID = ?";
/*     */     
/* 222 */     try (Connection conn = GDDBData.getConnection()) {
/* 223 */       boolean auto = conn.getAutoCommit();
/* 224 */       conn.setAutoCommit(false);
/*     */       
/* 226 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 227 */         ps.setString(1, shrineID);
/* 228 */         ps.executeUpdate();
/* 229 */         ps.close();
/*     */         
/* 231 */         conn.commit();
/*     */       }
/* 233 */       catch (SQLException ex) {
/* 234 */         conn.rollback();
/*     */         
/* 236 */         Object[] args = { shrineID, "GD_SHRINE" };
/* 237 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*     */         
/* 239 */         GDMsgLogger.addError(msg);
/* 240 */         GDMsgLogger.addError(ex);
/*     */         
/* 242 */         throw ex;
/*     */       } finally {
/*     */         
/* 245 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 248 */     } catch (SQLException ex) {
/* 249 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 254 */     DBShrine entry = get(record.getFileName());
/*     */     
/* 256 */     if (entry != null)
/*     */       return; 
/* 258 */     DBShrine shrine = new DBShrine(record);
/*     */     
/* 260 */     String insert = "INSERT INTO GD_SHRINE VALUES (?,?,?,?,?,?,?,?)";
/*     */     
/* 262 */     try (Connection conn = GDDBData.getConnection()) {
/* 263 */       boolean auto = conn.getAutoCommit();
/* 264 */       conn.setAutoCommit(false);
/*     */       
/* 266 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 267 */         ps.setString(1, shrine.shrineID);
/* 268 */         ps.setString(2, shrine.name);
/* 269 */         ps.setBoolean(3, shrine.normalDisabled);
/* 270 */         ps.setBoolean(4, shrine.normalLocked);
/* 271 */         ps.setBoolean(5, shrine.epicDisabled);
/* 272 */         ps.setBoolean(6, shrine.epicLocked);
/* 273 */         ps.setBoolean(7, shrine.legendaryDisabled);
/* 274 */         ps.setBoolean(8, shrine.legendaryLocked);
/*     */         
/* 276 */         ps.executeUpdate();
/* 277 */         ps.close();
/*     */         
/* 279 */         conn.commit();
/*     */       }
/* 281 */       catch (SQLException ex) {
/* 282 */         conn.rollback();
/*     */         
/* 284 */         Object[] args = { record.getFileName(), "GD_SHRINE" };
/* 285 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */ 
/*     */         
/* 288 */         GDMsgLogger.addError(msg);
/* 289 */         GDMsgLogger.addError(ex);
/*     */       } finally {
/*     */         
/* 292 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBShrine get(String shrineID) {
/* 298 */     DBShrine shrine = null;
/*     */     
/* 300 */     shrine = hashBuffer.get(shrineID);
/*     */     
/* 302 */     if (shrine == null) {
/* 303 */       shrine = getDB(shrineID);
/*     */       
/* 305 */       if (shrine != null) hashBuffer.put(shrine.shrineID, shrine);
/*     */     
/*     */     } 
/* 308 */     return shrine;
/*     */   }
/*     */   
/*     */   private static DBShrine getDB(String shrineID) {
/* 312 */     DBShrine shrine = null;
/*     */     
/* 314 */     String command = "SELECT * FROM GD_SHRINE WHERE SHRINE_ID = ?";
/*     */     
/* 316 */     try(Connection conn = GDDBData.getConnection(); 
/* 317 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 318 */       ps.setString(1, shrineID);
/*     */       
/* 320 */       try (ResultSet rs = ps.executeQuery()) {
/* 321 */         List<DBShrine> list = wrap(rs);
/*     */         
/* 323 */         if (list.isEmpty()) {
/* 324 */           shrine = null;
/*     */         } else {
/* 326 */           shrine = list.get(0);
/*     */         } 
/*     */         
/* 329 */         conn.commit();
/*     */       }
/* 331 */       catch (SQLException ex) {
/* 332 */         throw ex;
/*     */       }
/*     */     
/* 335 */     } catch (SQLException ex) {
/* 336 */       Object[] args = { shrineID, "GD_SHRINE" };
/* 337 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 339 */       GDMsgLogger.addError(msg);
/* 340 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 343 */     return shrine;
/*     */   }
/*     */   
/*     */   private static List<DBShrine> wrap(ResultSet rs) throws SQLException {
/* 347 */     LinkedList<DBShrine> list = new LinkedList<>();
/*     */     
/* 349 */     while (rs.next()) {
/* 350 */       DBShrine shrine = new DBShrine();
/*     */       
/* 352 */       shrine.shrineID = rs.getString(1);
/* 353 */       shrine.name = rs.getString(2);
/* 354 */       shrine.normalDisabled = rs.getBoolean(3);
/* 355 */       shrine.normalLocked = rs.getBoolean(4);
/* 356 */       shrine.epicDisabled = rs.getBoolean(5);
/* 357 */       shrine.epicLocked = rs.getBoolean(6);
/* 358 */       shrine.legendaryDisabled = rs.getBoolean(7);
/* 359 */       shrine.legendaryLocked = rs.getBoolean(8);
/*     */       
/* 361 */       list.add(shrine);
/*     */     } 
/*     */     
/* 364 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBShrine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */