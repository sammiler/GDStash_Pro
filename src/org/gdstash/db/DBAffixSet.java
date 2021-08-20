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
/*     */ public class DBAffixSet
/*     */ {
/*  27 */   private static ConcurrentHashMap<String, DBAffixSet> hashBuffer = new ConcurrentHashMap<>(); public static final String TABLE_NAME = "GD_AFFIXSET"; private static final int ROW_AFFIXSET_ID = 1; private static final int ROW_INDEX = 2; private static final int ROW_LEVEL_MIN = 3; private static final int ROW_LEVEL_MAX = 4;
/*     */   private static final int ROW_AFFIX_ID = 5;
/*     */   protected String affixSetID;
/*     */   protected List<DBEntry> affixes;
/*     */   
/*     */   public static class DBEntry { private int index;
/*     */     private int levelMin;
/*     */     
/*     */     public int getIndex() {
/*  36 */       return this.index;
/*     */     }
/*     */     private int levelMax; private String affixID;
/*     */     public int getMinLevel() {
/*  40 */       return this.levelMin;
/*     */     }
/*     */     
/*     */     public int getMaxLevel() {
/*  44 */       return this.levelMax;
/*     */     }
/*     */     
/*     */     public String getAffixID() {
/*  48 */       return this.affixID;
/*     */     }
/*     */     
/*     */     public void setIndex(int index) {
/*  52 */       this.index = index;
/*     */     }
/*     */     
/*     */     public void setMinLevel(int levelMin) {
/*  56 */       this.levelMin = levelMin;
/*     */     }
/*     */     
/*     */     public void setMaxLevel(int levelMax) {
/*  60 */       this.levelMax = levelMax;
/*     */     }
/*     */     
/*     */     public void setAffixID(String affixID) {
/*  64 */       this.affixID = affixID;
/*     */     } }
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
/*     */   public DBAffixSet() {
/*  80 */     this.affixes = new LinkedList<>();
/*     */   }
/*     */   
/*     */   private DBAffixSet(ARZRecord record) {
/*  84 */     this.affixSetID = record.getFileName();
/*     */     
/*  86 */     this.affixes = record.getAffixSetRandomizerList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAffixSetID() {
/*  94 */     return this.affixSetID;
/*     */   }
/*     */   
/*     */   public List<DBEntry> getAffixEntries() {
/*  98 */     return this.affixes;
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
/*     */   public static void clearBuffer() {
/* 110 */     hashBuffer.clear();
/*     */   }
/*     */   
/*     */   public static void createTables() throws SQLException {
/* 114 */     String dropTable = "DROP TABLE GD_AFFIXSET";
/* 115 */     String createTable = "CREATE TABLE GD_AFFIXSET (AFFIXSET_ID VARCHAR(256) NOT NULL, INDEX       INTEGER NOT NULL, LEVEL_MIN   INTEGER, LEVEL_MAX   INTEGER, AFFIX_ID    VARCHAR(256) NOT NULL, PRIMARY KEY (AFFIXSET_ID, INDEX))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     try (Connection conn = GDDBData.getConnection()) {
/* 124 */       boolean auto = conn.getAutoCommit();
/* 125 */       conn.setAutoCommit(false);
/*     */       
/* 127 */       try (Statement st = conn.createStatement()) {
/* 128 */         if (GDDBUtil.tableExists(conn, "GD_AFFIXSET")) {
/* 129 */           st.execute(dropTable);
/*     */         }
/* 131 */         st.execute(createTable);
/* 132 */         st.close();
/*     */         
/* 134 */         conn.commit();
/*     */       }
/* 136 */       catch (SQLException ex) {
/* 137 */         conn.rollback();
/*     */         
/* 139 */         Object[] args = { "GD_AFFIXSET" };
/* 140 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 142 */         GDMsgLogger.addError(msg);
/*     */         
/* 144 */         throw ex;
/*     */       } finally {
/*     */         
/* 147 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(String affixSetID) throws SQLException {
/* 153 */     String deleteEntry = "DELETE FROM GD_AFFIXSET WHERE AFFIXSET_ID = ?";
/*     */     
/* 155 */     try (Connection conn = GDDBData.getConnection()) {
/* 156 */       boolean auto = conn.getAutoCommit();
/* 157 */       conn.setAutoCommit(false);
/*     */       
/* 159 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 160 */         ps.setString(1, affixSetID);
/* 161 */         ps.executeUpdate();
/* 162 */         ps.close();
/*     */         
/* 164 */         conn.commit();
/*     */       }
/* 166 */       catch (SQLException ex) {
/* 167 */         conn.rollback();
/*     */         
/* 169 */         Object[] args = { affixSetID, "GD_AFFIXSET" };
/* 170 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*     */         
/* 172 */         GDMsgLogger.addError(msg);
/* 173 */         GDMsgLogger.addError(ex);
/*     */         
/* 175 */         throw ex;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 181 */     DBAffixSet entry = get(record.getFileName());
/*     */     
/* 183 */     if (entry != null)
/*     */       return; 
/* 185 */     DBAffixSet affixSet = new DBAffixSet(record);
/*     */     
/* 187 */     if (affixSet.affixes == null)
/* 188 */       return;  if (affixSet.affixes.isEmpty())
/*     */       return; 
/* 190 */     String insert = "INSERT INTO GD_AFFIXSET VALUES (?,?,?,?,?)";
/*     */     
/* 192 */     try (Connection conn = GDDBData.getConnection()) {
/* 193 */       boolean auto = conn.getAutoCommit();
/* 194 */       conn.setAutoCommit(false);
/*     */       
/* 196 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 197 */         for (DBEntry dbEntry : affixSet.affixes) {
/* 198 */           if (dbEntry.affixID != null) {
/* 199 */             ps.setString(1, affixSet.affixSetID);
/* 200 */             ps.setInt(2, dbEntry.index);
/* 201 */             ps.setInt(3, dbEntry.levelMin);
/* 202 */             ps.setInt(4, dbEntry.levelMax);
/* 203 */             ps.setString(5, dbEntry.affixID);
/*     */             
/* 205 */             ps.executeUpdate();
/*     */             
/* 207 */             ps.clearParameters();
/*     */           } 
/*     */         } 
/* 210 */         ps.close();
/*     */         
/* 212 */         conn.commit();
/*     */       }
/* 214 */       catch (SQLException ex) {
/* 215 */         conn.rollback();
/*     */         
/* 217 */         Object[] args = { record.getFileName(), "GD_AFFIXSET" };
/* 218 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */         
/* 220 */         GDMsgLogger.addLowError(msg);
/* 221 */         GDMsgLogger.addLowError(ex);
/*     */       } finally {
/*     */         
/* 224 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBAffixSet get(String affixSetID) {
/* 230 */     DBAffixSet set = null;
/*     */     
/* 232 */     set = hashBuffer.get(affixSetID);
/*     */     
/* 234 */     if (set == null) {
/* 235 */       set = getDB(affixSetID);
/*     */       
/* 237 */       if (set != null) hashBuffer.put(set.affixSetID, set);
/*     */     
/*     */     } 
/* 240 */     return set;
/*     */   }
/*     */   
/*     */   private static DBAffixSet getDB(String affixSetID) {
/* 244 */     DBAffixSet set = null;
/*     */     
/* 246 */     String command = "SELECT * FROM GD_AFFIXSET WHERE AFFIXSET_ID = ?";
/*     */     
/* 248 */     try(Connection conn = GDDBData.getConnection(); 
/* 249 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 250 */       ps.setString(1, affixSetID);
/*     */       
/* 252 */       try (ResultSet rs = ps.executeQuery()) {
/* 253 */         List<DBAffixSet> list = wrap(rs);
/*     */         
/* 255 */         if (list.isEmpty()) { set = null; }
/* 256 */         else { set = list.get(0); }
/*     */         
/* 258 */         conn.commit();
/*     */       }
/* 260 */       catch (SQLException ex) {
/* 261 */         throw ex;
/*     */       }
/*     */     
/* 264 */     } catch (SQLException ex) {
/* 265 */       Object[] args = { affixSetID, "GD_AFFIXSET" };
/* 266 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 268 */       GDMsgLogger.addError(msg);
/* 269 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 272 */     return set;
/*     */   }
/*     */   
/*     */   public static List<DBAffixSet> getByAffixSetIDs(List<String> affixSetIDs) {
/* 276 */     List<DBAffixSet> list = new LinkedList<>();
/*     */     
/* 278 */     for (String affixSetID : affixSetIDs) {
/* 279 */       DBAffixSet set = get(affixSetID);
/* 280 */       if (set != null) list.add(set);
/*     */     
/*     */     } 
/* 283 */     return list;
/*     */   }
/*     */   
/*     */   public static List<DBAffixSet> getCompletionAffixes() {
/* 287 */     List<DBAffixSet> list = null;
/*     */     
/* 289 */     String command = "SELECT * FROM GD_AFFIXSET WHERE AFFIXSET_ID LIKE 'records/items/lootaffixes/completion%'";
/*     */     
/* 291 */     try(Connection conn = GDDBData.getConnection(); 
/* 292 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*     */       
/* 294 */       try (ResultSet rs = ps.executeQuery()) {
/* 295 */         list = wrap(rs);
/*     */         
/* 297 */         conn.commit();
/*     */       }
/* 299 */       catch (SQLException ex) {
/* 300 */         throw ex;
/*     */       }
/*     */     
/* 303 */     } catch (SQLException ex) {
/* 304 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_READ_AFFIXSET_CRAFT"));
/* 305 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 308 */     return list;
/*     */   }
/*     */   
/*     */   public static List<DBAffixSet> getCraftingAffixes() {
/* 312 */     List<DBAffixSet> list = null;
/*     */     
/* 314 */     String command = "SELECT * FROM GD_AFFIXSET WHERE AFFIXSET_ID LIKE 'records/items/lootaffixes/crafting%'";
/*     */     
/* 316 */     try(Connection conn = GDDBData.getConnection(); 
/* 317 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*     */       
/* 319 */       try (ResultSet rs = ps.executeQuery()) {
/* 320 */         list = wrap(rs);
/*     */         
/* 322 */         conn.commit();
/*     */       }
/* 324 */       catch (SQLException ex) {
/* 325 */         throw ex;
/*     */       }
/*     */     
/* 328 */     } catch (SQLException ex) {
/* 329 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_READ_AFFIXSET_CRAFT"));
/* 330 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 333 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBAffixSet> wrap(ResultSet rs) throws SQLException {
/* 337 */     LinkedList<DBAffixSet> list = new LinkedList<>();
/* 338 */     boolean found = false;
/*     */     
/* 340 */     while (rs.next()) {
/* 341 */       DBAffixSet set = null;
/* 342 */       DBEntry entry = new DBEntry();
/*     */       
/* 344 */       String id = rs.getString(1);
/*     */       
/* 346 */       entry.index = rs.getInt(2);
/* 347 */       entry.levelMin = rs.getInt(3);
/* 348 */       entry.levelMax = rs.getInt(4);
/* 349 */       entry.affixID = rs.getString(5);
/*     */       
/* 351 */       found = false;
/* 352 */       for (DBAffixSet as : list) {
/* 353 */         if (as != null && as.affixSetID != null && as.affixSetID
/*     */           
/* 355 */           .equals(id)) {
/* 356 */           found = true;
/*     */           
/* 358 */           as.affixes.add(entry);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 364 */       if (!found) {
/* 365 */         set = new DBAffixSet();
/*     */         
/* 367 */         set.affixSetID = id;
/*     */         
/* 369 */         set.affixes.add(entry);
/*     */         
/* 371 */         list.add(set);
/*     */       } 
/*     */     } 
/*     */     
/* 375 */     return list;
/*     */   }
/*     */   
/*     */   public List<String> getAffixIDs() {
/* 379 */     List<String> list = new LinkedList<>();
/*     */     
/* 381 */     if (this.affixes == null) return list;
/*     */     
/* 383 */     for (DBEntry entry : this.affixes) {
/* 384 */       if (entry != null && entry
/* 385 */         .affixID != null) {
/* 386 */         list.add(entry.affixID);
/*     */       }
/*     */     } 
/*     */     
/* 390 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBAffixSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */