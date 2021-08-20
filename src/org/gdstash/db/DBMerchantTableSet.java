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
/*     */ public class DBMerchantTableSet
/*     */ {
/*     */   private static final String TABLE_NAME = "GDC_MERCHANTTABLESET";
/*     */   private static final int ROW_TABLESET_ID = 1;
/*     */   private String tableSetID;
/*     */   private List<String> tableIDs;
/*     */   
/*     */   public DBMerchantTableSet() {
/*  32 */     this.tableIDs = new LinkedList<>();
/*     */   }
/*     */   
/*     */   private DBMerchantTableSet(ARZRecord record) {
/*  36 */     this.tableSetID = record.getFileName();
/*  37 */     this.tableIDs = record.getMerchantTableIDList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMerchantTableSetID() {
/*  45 */     return this.tableSetID;
/*     */   }
/*     */   
/*     */   public List<String> getTableIDList() {
/*  49 */     return this.tableIDs;
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
/*     */   public static void createTables() throws SQLException {
/*  61 */     String dropTable = "DROP TABLE GDC_MERCHANTTABLESET";
/*  62 */     String createTable = "CREATE TABLE GDC_MERCHANTTABLESET (TABLESET_ID       VARCHAR(256) NOT NULL, PRIMARY KEY (TABLESET_ID))";
/*     */ 
/*     */ 
/*     */     
/*  66 */     try (Connection conn = GDDBData.getConnection()) {
/*  67 */       boolean auto = conn.getAutoCommit();
/*  68 */       conn.setAutoCommit(false);
/*     */       
/*  70 */       try (Statement st = conn.createStatement()) {
/*  71 */         if (GDDBUtil.tableExists(conn, "GDC_MERCHANTTABLESET")) {
/*  72 */           st.execute(dropTable);
/*     */         }
/*  74 */         st.execute(createTable);
/*  75 */         st.close();
/*     */         
/*  77 */         conn.commit();
/*     */       }
/*  79 */       catch (SQLException ex) {
/*  80 */         conn.rollback();
/*     */         
/*  82 */         Object[] args = { "GDC_MERCHANTTABLESET" };
/*  83 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/*  85 */         GDMsgLogger.addError(msg);
/*     */         
/*  87 */         throw ex;
/*     */       } finally {
/*     */         
/*  90 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     DBMerchantTableSetAlloc.createTable();
/*     */   }
/*     */   
/*     */   public static void delete(String tableSetID) throws SQLException {
/*  98 */     String deleteEntry = "DELETE FROM GDC_MERCHANTTABLESET WHERE TABLE_ID = ?";
/*     */     
/* 100 */     try (Connection conn = GDDBData.getConnection()) {
/* 101 */       boolean auto = conn.getAutoCommit();
/* 102 */       conn.setAutoCommit(false);
/*     */       
/* 104 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 105 */         ps.setString(1, tableSetID);
/* 106 */         ps.executeUpdate();
/* 107 */         ps.close();
/*     */         
/* 109 */         DBMerchantTableSetAlloc.delete(conn, tableSetID);
/*     */         
/* 111 */         conn.commit();
/*     */       }
/* 113 */       catch (SQLException ex) {
/* 114 */         conn.rollback();
/*     */         
/* 116 */         Object[] args = { tableSetID, "GDC_MERCHANTTABLESET" };
/* 117 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*     */         
/* 119 */         GDMsgLogger.addError(msg);
/* 120 */         GDMsgLogger.addError(ex);
/*     */         
/* 122 */         throw ex;
/*     */       } finally {
/*     */         
/* 125 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 128 */     } catch (SQLException ex) {
/* 129 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 134 */     DBMerchantTableSet entry = get(record.getFileName());
/*     */     
/* 136 */     if (entry != null)
/*     */       return; 
/* 138 */     DBMerchantTableSet merchantTableSet = new DBMerchantTableSet(record);
/*     */ 
/*     */     
/* 141 */     if (merchantTableSet.tableIDs == null)
/* 142 */       return;  if (merchantTableSet.tableIDs.isEmpty())
/*     */       return; 
/* 144 */     String insert = "INSERT INTO GDC_MERCHANTTABLESET VALUES (?)";
/*     */     
/* 146 */     try (Connection conn = GDDBData.getConnection()) {
/* 147 */       boolean auto = conn.getAutoCommit();
/* 148 */       conn.setAutoCommit(false);
/*     */       
/* 150 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/*     */         
/* 152 */         ps.setString(1, merchantTableSet.tableSetID);
/*     */         
/* 154 */         ps.executeUpdate();
/*     */         
/* 156 */         ps.close();
/* 157 */         conn.commit();
/*     */         
/* 159 */         DBMerchantTableSetAlloc.insert(conn, merchantTableSet);
/*     */       }
/* 161 */       catch (SQLException ex) {
/* 162 */         conn.rollback();
/*     */         
/* 164 */         Object[] args = { record.getFileName(), "GDC_MERCHANTTABLESET" };
/* 165 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */         
/* 167 */         GDMsgLogger.addLowError(msg);
/* 168 */         GDMsgLogger.addLowError(ex);
/*     */       } finally {
/*     */         
/* 171 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBMerchantTableSet get(String tableSetID) {
/* 177 */     DBMerchantTableSet tableSet = new DBMerchantTableSet();
/*     */     
/* 179 */     String command = "SELECT * FROM GDC_MERCHANTTABLESET WHERE TABLESET_ID = ?";
/*     */     
/* 181 */     try(Connection conn = GDDBData.getConnection(); 
/* 182 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 183 */       ps.setString(1, tableSetID);
/*     */       
/* 185 */       try (ResultSet rs = ps.executeQuery()) {
/* 186 */         List<DBMerchantTableSet> list = wrap(rs);
/*     */         
/* 188 */         if (list.isEmpty()) { tableSet = null; }
/* 189 */         else { tableSet = list.get(0); }
/*     */         
/* 191 */         conn.commit();
/*     */       }
/* 193 */       catch (SQLException ex) {
/* 194 */         throw ex;
/*     */       }
/*     */     
/* 197 */     } catch (SQLException ex) {
/* 198 */       Object[] args = { tableSetID, "GDC_MERCHANTTABLESET" };
/* 199 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 201 */       GDMsgLogger.addError(msg);
/* 202 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 205 */     return tableSet;
/*     */   }
/*     */   
/*     */   private static List<DBMerchantTableSet> wrap(ResultSet rs) throws SQLException {
/* 209 */     LinkedList<DBMerchantTableSet> list = new LinkedList<>();
/*     */     
/* 211 */     while (rs.next()) {
/* 212 */       DBMerchantTableSet tableSet = new DBMerchantTableSet();
/*     */       
/* 214 */       tableSet.tableSetID = rs.getString(1);
/*     */       
/* 216 */       tableSet.tableIDs = DBMerchantTableSetAlloc.getByMerchantTableSetID(tableSet.tableSetID);
/*     */       
/* 218 */       list.add(tableSet);
/*     */     } 
/*     */     
/* 221 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBMerchantTableSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */