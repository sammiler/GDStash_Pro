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
/*     */ public class DBLootTableSet
/*     */ {
/*     */   private static final String TABLE_NAME = "GD_LOOTTABLESET_HD";
/*     */   private static final int ROW_TABLESET_ID = 1;
/*  31 */   private static ConcurrentHashMap<String, DBLootTableSet> hashBuffer = new ConcurrentHashMap<>();
/*     */   
/*     */   private String tableSetID;
/*     */   
/*     */   private List<DBLootTableSetAlloc> entries;
/*     */   
/*     */   public DBLootTableSet() {
/*  38 */     this.entries = new LinkedList<>();
/*     */   }
/*     */   
/*     */   private DBLootTableSet(ARZRecord record) {
/*  42 */     this.tableSetID = record.getFileName();
/*     */     
/*  44 */     this.entries = record.getTableSetAllocList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTableSetID() {
/*  52 */     return this.tableSetID;
/*     */   }
/*     */   
/*     */   public List<DBLootTableSetAlloc> getAllocList() {
/*  56 */     return this.entries;
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
/*  68 */     hashBuffer.clear();
/*     */   }
/*     */   
/*     */   public static void createTables() throws SQLException {
/*  72 */     String dropTable = "DROP TABLE GD_LOOTTABLESET_HD";
/*  73 */     String createTable = "CREATE TABLE GD_LOOTTABLESET_HD (TABLESET_ID VARCHAR(256) NOT NULL, PRIMARY KEY (TABLESET_ID))";
/*     */ 
/*     */ 
/*     */     
/*  77 */     try (Connection conn = GDDBData.getConnection()) {
/*  78 */       boolean auto = conn.getAutoCommit();
/*  79 */       conn.setAutoCommit(false);
/*     */       
/*  81 */       try (Statement st = conn.createStatement()) {
/*  82 */         if (GDDBUtil.tableExists(conn, "GD_LOOTTABLESET_HD")) {
/*  83 */           st.execute(dropTable);
/*     */         }
/*  85 */         st.execute(createTable);
/*  86 */         st.close();
/*     */         
/*  88 */         conn.commit();
/*     */       }
/*  90 */       catch (SQLException ex) {
/*  91 */         conn.rollback();
/*     */         
/*  93 */         Object[] args = { "GD_LOOTTABLESET_HD" };
/*  94 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/*  96 */         GDMsgLogger.addError(msg);
/*     */         
/*  98 */         throw ex;
/*     */       } finally {
/*     */         
/* 101 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     DBLootTableSetAlloc.createTable();
/*     */   }
/*     */   
/*     */   public static void delete(String tableSetID) throws SQLException {
/* 109 */     String deleteEntry = "DELETE FROM GD_LOOTTABLESET_HD WHERE TABLESET_ID = ?";
/*     */     
/* 111 */     try (Connection conn = GDDBData.getConnection()) {
/* 112 */       boolean auto = conn.getAutoCommit();
/* 113 */       conn.setAutoCommit(false);
/*     */       
/* 115 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 116 */         ps.setString(1, tableSetID);
/* 117 */         ps.executeUpdate();
/* 118 */         ps.close();
/*     */         
/* 120 */         DBLootTableSetAlloc.delete(conn, tableSetID);
/*     */         
/* 122 */         conn.commit();
/*     */       }
/* 124 */       catch (SQLException ex) {
/* 125 */         conn.rollback();
/*     */         
/* 127 */         Object[] args = { tableSetID, "GD_LOOTTABLESET_HD" };
/* 128 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*     */         
/* 130 */         GDMsgLogger.addError(msg);
/* 131 */         GDMsgLogger.addError(ex);
/*     */         
/* 133 */         throw ex;
/*     */       } finally {
/*     */         
/* 136 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 139 */     } catch (SQLException ex) {
/* 140 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 145 */     DBLootTableSet entry = get(record.getFileName());
/*     */     
/* 147 */     if (entry != null)
/*     */       return; 
/* 149 */     DBLootTableSet tableSet = new DBLootTableSet(record);
/*     */ 
/*     */     
/* 152 */     if (tableSet.entries == null)
/* 153 */       return;  if (tableSet.entries.isEmpty())
/*     */       return; 
/* 155 */     String insert = "INSERT INTO GD_LOOTTABLESET_HD VALUES (?)";
/*     */     
/* 157 */     try (Connection conn = GDDBData.getConnection()) {
/* 158 */       boolean auto = conn.getAutoCommit();
/* 159 */       conn.setAutoCommit(false);
/*     */       
/* 161 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 162 */         ps.setString(1, record.getFileName());
/*     */         
/* 164 */         ps.executeUpdate();
/* 165 */         ps.close();
/*     */         
/* 167 */         conn.commit();
/*     */         
/* 169 */         DBLootTableSetAlloc.insert(conn, tableSet);
/*     */       }
/* 171 */       catch (SQLException ex) {
/* 172 */         conn.rollback();
/*     */         
/* 174 */         Object[] args = { record.getFileName(), "GD_LOOTTABLESET_HD" };
/* 175 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */         
/* 177 */         GDMsgLogger.addWarning(msg);
/* 178 */         GDMsgLogger.addWarning(ex);
/*     */       } finally {
/*     */         
/* 181 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBLootTableSet get(String tableSetID) {
/* 187 */     DBLootTableSet set = null;
/*     */     
/* 189 */     set = hashBuffer.get(tableSetID);
/*     */     
/* 191 */     if (set == null) {
/* 192 */       set = getDB(tableSetID);
/*     */       
/* 194 */       if (set != null) hashBuffer.put(set.tableSetID, set);
/*     */     
/*     */     } 
/* 197 */     return set;
/*     */   }
/*     */   
/*     */   private static DBLootTableSet getDB(String tableSetID) {
/* 201 */     DBLootTableSet set = null;
/*     */     
/* 203 */     String command = "SELECT * FROM GD_LOOTTABLESET_HD WHERE TABLESET_ID = ?";
/*     */     
/* 205 */     try(Connection conn = GDDBData.getConnection(); 
/* 206 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 207 */       ps.setString(1, tableSetID);
/*     */       
/* 209 */       try (ResultSet rs = ps.executeQuery()) {
/* 210 */         List<DBLootTableSet> list = wrap(rs);
/*     */         
/* 212 */         if (list.isEmpty()) { set = null; }
/* 213 */         else { set = list.get(0); }
/*     */         
/* 215 */         conn.commit();
/*     */       }
/* 217 */       catch (SQLException ex) {
/* 218 */         throw ex;
/*     */       }
/*     */     
/* 221 */     } catch (SQLException ex) {
/* 222 */       Object[] args = { tableSetID, "GD_LOOTTABLESET_HD" };
/* 223 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 225 */       GDMsgLogger.addError(msg);
/* 226 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 229 */     return set;
/*     */   }
/*     */   
/*     */   public static List<DBLootTableSet> getByTableID(String tableID) {
/* 233 */     List<DBLootTableSet> list = null;
/*     */     
/* 235 */     String command = "SELECT * FROM GD_LOOTTABLESET_HD WHERE TABLE_ID = ?";
/*     */     
/* 237 */     try(Connection conn = GDDBData.getConnection(); 
/* 238 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 239 */       ps.setString(1, tableID);
/*     */       
/* 241 */       try (ResultSet rs = ps.executeQuery()) {
/* 242 */         list = wrap(rs);
/*     */         
/* 244 */         conn.commit();
/*     */       }
/* 246 */       catch (SQLException ex) {
/* 247 */         list = null;
/*     */         
/* 249 */         throw ex;
/*     */       }
/*     */     
/* 252 */     } catch (SQLException ex) {
/* 253 */       Object[] args = { tableID, "GD_LOOTTABLESET_HD" };
/* 254 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 256 */       GDMsgLogger.addError(msg);
/* 257 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 260 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBLootTableSet> wrap(ResultSet rs) throws SQLException {
/* 264 */     List<DBLootTableSet> list = new LinkedList<>();
/*     */     
/* 266 */     while (rs.next()) {
/* 267 */       DBLootTableSet set = new DBLootTableSet();
/*     */       
/* 269 */       set.tableSetID = rs.getString(1);
/*     */       
/* 271 */       set.entries = DBLootTableSetAlloc.getByTableSetID(set.tableSetID);
/*     */       
/* 273 */       list.add(set);
/*     */     } 
/*     */     
/* 276 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBLootTableSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */