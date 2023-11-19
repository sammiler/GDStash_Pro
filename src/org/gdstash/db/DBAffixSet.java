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
/*  27 */   private static ConcurrentHashMap<String, DBAffixSet> hashBuffer = new ConcurrentHashMap<>(); public static final String TABLE_NAME = "GD_AFFIXSET"; private static final int ROW_AFFIXSET_ID = 1; private static final int ROW_INDEX = 2; private static final int ROW_LEVEL_MIN = 3; private static final int ROW_LEVEL_MAX = 4; private static final int ROW_AFFIX_ID = 5;
/*     */   protected String affixSetID;
/*     */   protected List<DBEntry> affixes;
/*     */   protected List<DBAffix> affixList;
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
/*     */   
/*     */   public DBAffixSet() {
/*  81 */     this.affixes = new LinkedList<>();
/*  82 */     this.affixList = new LinkedList<>();
/*     */   }
/*     */   
/*     */   private DBAffixSet(ARZRecord record) {
/*  86 */     this.affixSetID = record.getFileName();
/*     */     
/*  88 */     this.affixes = record.getAffixSetRandomizerList();
/*  89 */     this.affixList = determineAffixList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAffixSetID() {
/*  97 */     return this.affixSetID;
/*     */   }
/*     */   
/*     */   public List<DBEntry> getAffixEntries() {
/* 101 */     return this.affixes;
/*     */   }
/*     */   
/*     */   public List<DBAffix> getAffixList() {
/* 105 */     return this.affixList;
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
/* 117 */     hashBuffer.clear();
/*     */   }
/*     */   
/*     */   public static void createTables() throws SQLException {
/* 121 */     String dropTable = "DROP TABLE GD_AFFIXSET";
/* 122 */     String createTable = "CREATE TABLE GD_AFFIXSET (AFFIXSET_ID VARCHAR(256) NOT NULL, INDEX       INTEGER NOT NULL, LEVEL_MIN   INTEGER, LEVEL_MAX   INTEGER, AFFIX_ID    VARCHAR(256) NOT NULL, PRIMARY KEY (AFFIXSET_ID, INDEX))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     try (Connection conn = GDDBData.getConnection()) {
/* 131 */       boolean auto = conn.getAutoCommit();
/* 132 */       conn.setAutoCommit(false);
/*     */       
/* 134 */       try (Statement st = conn.createStatement()) {
/* 135 */         if (GDDBUtil.tableExists(conn, "GD_AFFIXSET")) {
/* 136 */           st.execute(dropTable);
/*     */         }
/* 138 */         st.execute(createTable);
/* 139 */         st.close();
/*     */         
/* 141 */         conn.commit();
/*     */       }
/* 143 */       catch (SQLException ex) {
/* 144 */         conn.rollback();
/*     */         
/* 146 */         Object[] args = { "GD_AFFIXSET" };
/* 147 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 149 */         GDMsgLogger.addError(msg);
/*     */         
/* 151 */         throw ex;
/*     */       } finally {
/*     */         
/* 154 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(String affixSetID) throws SQLException {
/* 160 */     String deleteEntry = "DELETE FROM GD_AFFIXSET WHERE AFFIXSET_ID = ?";
/*     */     
/* 162 */     try (Connection conn = GDDBData.getConnection()) {
/* 163 */       boolean auto = conn.getAutoCommit();
/* 164 */       conn.setAutoCommit(false);
/*     */       
/* 166 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 167 */         ps.setString(1, affixSetID);
/* 168 */         ps.executeUpdate();
/* 169 */         ps.close();
/*     */         
/* 171 */         conn.commit();
/*     */       }
/* 173 */       catch (SQLException ex) {
/* 174 */         conn.rollback();
/*     */         
/* 176 */         Object[] args = { affixSetID, "GD_AFFIXSET" };
/* 177 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*     */         
/* 179 */         GDMsgLogger.addError(msg);
/* 180 */         GDMsgLogger.addError(ex);
/*     */         
/* 182 */         throw ex;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 188 */     DBAffixSet entry = get(record.getFileName());
/*     */     
/* 190 */     if (entry != null)
/*     */       return; 
/* 192 */     DBAffixSet affixSet = new DBAffixSet(record);
/*     */     
/* 194 */     if (affixSet.affixes == null)
/* 195 */       return;  if (affixSet.affixes.isEmpty())
/*     */       return; 
/* 197 */     String insert = "INSERT INTO GD_AFFIXSET VALUES (?,?,?,?,?)";
/*     */     
/* 199 */     try (Connection conn = GDDBData.getConnection()) {
/* 200 */       boolean auto = conn.getAutoCommit();
/* 201 */       conn.setAutoCommit(false);
/*     */       
/* 203 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 204 */         for (DBEntry dbEntry : affixSet.affixes) {
/* 205 */           if (dbEntry.affixID != null) {
/* 206 */             ps.setString(1, affixSet.affixSetID);
/* 207 */             ps.setInt(2, dbEntry.index);
/* 208 */             ps.setInt(3, dbEntry.levelMin);
/* 209 */             ps.setInt(4, dbEntry.levelMax);
/* 210 */             ps.setString(5, dbEntry.affixID);
/*     */             
/* 212 */             ps.executeUpdate();
/*     */             
/* 214 */             ps.clearParameters();
/*     */           } 
/*     */         } 
/* 217 */         ps.close();
/*     */         
/* 219 */         conn.commit();
/*     */       }
/* 221 */       catch (SQLException ex) {
/* 222 */         conn.rollback();
/*     */         
/* 224 */         Object[] args = { record.getFileName(), "GD_AFFIXSET" };
/* 225 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */         
/* 227 */         GDMsgLogger.addLowError(msg);
/* 228 */         GDMsgLogger.addLowError(ex);
/*     */       } finally {
/*     */         
/* 231 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBAffixSet get(String affixSetID) {
/* 237 */     DBAffixSet set = null;
/*     */     
/* 239 */     set = hashBuffer.get(affixSetID);
/*     */     
/* 241 */     if (set == null) {
/* 242 */       set = getDB(affixSetID);
/*     */       
/* 244 */       if (set != null) hashBuffer.put(set.affixSetID, set);
/*     */     
/*     */     } 
/* 247 */     return set;
/*     */   }
/*     */   
/*     */   private static DBAffixSet getDB(String affixSetID) {
/* 251 */     DBAffixSet set = null;
/*     */     
/* 253 */     String command = "SELECT * FROM GD_AFFIXSET WHERE AFFIXSET_ID = ?";
/*     */     
/* 255 */     try(Connection conn = GDDBData.getConnection(); 
/* 256 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 257 */       ps.setString(1, affixSetID);
/*     */       
/* 259 */       try (ResultSet rs = ps.executeQuery()) {
/* 260 */         List<DBAffixSet> list = wrap(rs);
/*     */         
/* 262 */         if (list.isEmpty()) { set = null; }
/* 263 */         else { set = list.get(0); }
/*     */         
/* 265 */         conn.commit();
/*     */       }
/* 267 */       catch (SQLException ex) {
/* 268 */         throw ex;
/*     */       }
/*     */     
/* 271 */     } catch (SQLException ex) {
/* 272 */       Object[] args = { affixSetID, "GD_AFFIXSET" };
/* 273 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 275 */       GDMsgLogger.addError(msg);
/* 276 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 279 */     return set;
/*     */   }
/*     */   
/*     */   public static List<DBAffixSet> getByAffixSetIDs(List<String> affixSetIDs) {
/* 283 */     List<DBAffixSet> list = new LinkedList<>();
/*     */     
/* 285 */     for (String affixSetID : affixSetIDs) {
/* 286 */       DBAffixSet set = get(affixSetID);
/* 287 */       if (set != null) list.add(set);
/*     */     
/*     */     } 
/* 290 */     return list;
/*     */   }
/*     */   
/*     */   public static List<DBAffixSet> getCompletionAffixes() {
/* 294 */     List<DBAffixSet> list = null;
/*     */     
/* 296 */     String command = "SELECT * FROM GD_AFFIXSET WHERE AFFIXSET_ID LIKE 'records/items/lootaffixes/completion%'";
/*     */     
/* 298 */     try(Connection conn = GDDBData.getConnection(); 
/* 299 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*     */       
/* 301 */       try (ResultSet rs = ps.executeQuery()) {
/* 302 */         list = wrap(rs);
/*     */         
/* 304 */         conn.commit();
/*     */       }
/* 306 */       catch (SQLException ex) {
/* 307 */         throw ex;
/*     */       }
/*     */     
/* 310 */     } catch (SQLException ex) {
/* 311 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_READ_AFFIXSET_CRAFT"));
/* 312 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 315 */     return list;
/*     */   }
/*     */   
/*     */   public static List<DBAffixSet> getCraftingAffixes() {
/* 319 */     List<DBAffixSet> list = null;
/*     */     
/* 321 */     String command = "SELECT * FROM GD_AFFIXSET WHERE AFFIXSET_ID LIKE 'records/items/lootaffixes/crafting%'";
/*     */     
/* 323 */     try(Connection conn = GDDBData.getConnection(); 
/* 324 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*     */       
/* 326 */       try (ResultSet rs = ps.executeQuery()) {
/* 327 */         list = wrap(rs);
/*     */         
/* 329 */         conn.commit();
/*     */       }
/* 331 */       catch (SQLException ex) {
/* 332 */         throw ex;
/*     */       }
/*     */     
/* 335 */     } catch (SQLException ex) {
/* 336 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_READ_AFFIXSET_CRAFT"));
/* 337 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 340 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBAffixSet> wrap(ResultSet rs) throws SQLException {
/* 344 */     LinkedList<DBAffixSet> list = new LinkedList<>();
/* 345 */     boolean found = false;
/*     */     
/* 347 */     while (rs.next()) {
/* 348 */       DBAffixSet set = null;
/* 349 */       DBEntry entry = new DBEntry();
/*     */       
/* 351 */       String id = rs.getString(1);
/*     */       
/* 353 */       entry.index = rs.getInt(2);
/* 354 */       entry.levelMin = rs.getInt(3);
/* 355 */       entry.levelMax = rs.getInt(4);
/* 356 */       entry.affixID = rs.getString(5);
/*     */       
/* 358 */       found = false;
/* 359 */       for (DBAffixSet as : list) {
/* 360 */         if (as != null && as.affixSetID != null && as.affixSetID
/*     */           
/* 362 */           .equals(id)) {
/* 363 */           found = true;
/*     */           
/* 365 */           as.affixes.add(entry);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 371 */       if (!found) {
/* 372 */         set = new DBAffixSet();
/*     */         
/* 374 */         set.affixSetID = id;
/*     */         
/* 376 */         set.affixes.add(entry);
/*     */         
/* 378 */         list.add(set);
/*     */       } 
/*     */     } 
/*     */     
/* 382 */     for (DBAffixSet as : list) {
/* 383 */       as.affixList = as.determineAffixList();
/*     */     }
/*     */     
/* 386 */     return list;
/*     */   }
/*     */   
/*     */   public List<String> getAffixIDs() {
/* 390 */     List<String> list = new LinkedList<>();
/*     */     
/* 392 */     if (this.affixes == null) return list;
/*     */     
/* 394 */     for (DBEntry entry : this.affixes) {
/* 395 */       if (entry != null && entry
/* 396 */         .affixID != null) {
/* 397 */         list.add(entry.affixID);
/*     */       }
/*     */     } 
/*     */     
/* 401 */     return list;
/*     */   }
/*     */   
/*     */   public List<DBAffix> determineAffixList() {
/* 405 */     List<DBAffix> list = new LinkedList<>();
/* 406 */     List<String> ids = getAffixIDs();
/*     */     
/* 408 */     if (!ids.isEmpty()) {
/* 409 */       list = DBAffix.getByAffixSetID(ids);
/*     */     }
/*     */     
/* 412 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBAffixSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */