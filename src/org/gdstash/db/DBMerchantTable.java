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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DBMerchantTable
/*     */ {
/*     */   private static final String TABLE_NAME = "GDC_MERCHANTTABLE";
/*     */   private static final int ROW_TABLE_ID = 1;
/*     */   private String tableID;
/*  32 */   private List<String> itemIDs = new LinkedList<>();
/*     */ 
/*     */   
/*     */   private DBMerchantTable(ARZRecord record) {
/*  36 */     this();
/*     */     
/*  38 */     this.tableID = record.getFileName();
/*  39 */     this.itemIDs = record.getMerchantTableItemIDList();
/*     */   }
/*     */ 
/*     */   
/*     */   public DBMerchantTable() {}
/*     */ 
/*     */   
/*     */   public String getMerchantTableID() {
/*  47 */     return this.tableID;
/*     */   }
/*     */   
/*     */   public List<String> getItemIDList() {
/*  51 */     return this.itemIDs;
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
/*  63 */     String dropTable = "DROP TABLE GDC_MERCHANTTABLE";
/*  64 */     String createTable = "CREATE TABLE GDC_MERCHANTTABLE (TABLE_ID    VARCHAR(256) NOT NULL, PRIMARY KEY (TABLE_ID))";
/*     */ 
/*     */ 
/*     */     
/*  68 */     try (Connection conn = GDDBData.getConnection()) {
/*  69 */       boolean auto = conn.getAutoCommit();
/*  70 */       conn.setAutoCommit(false);
/*     */       
/*  72 */       try (Statement st = conn.createStatement()) {
/*  73 */         if (GDDBUtil.tableExists(conn, "GDC_MERCHANTTABLE")) {
/*  74 */           st.execute(dropTable);
/*     */         }
/*  76 */         st.execute(createTable);
/*  77 */         st.close();
/*     */         
/*  79 */         conn.commit();
/*     */       }
/*  81 */       catch (SQLException ex) {
/*  82 */         conn.rollback();
/*     */         
/*  84 */         Object[] args = { "GDC_MERCHANTTABLE" };
/*  85 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/*  87 */         GDMsgLogger.addError(msg);
/*     */         
/*  89 */         throw ex;
/*     */       } finally {
/*     */         
/*  92 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */     
/*  96 */     DBMerchantTableAlloc.createTable();
/*     */   }
/*     */   
/*     */   public static void delete(String tableID) throws SQLException {
/* 100 */     String deleteEntry = "DELETE FROM GDC_MERCHANTTABLE WHERE TABLE_ID = ?";
/*     */     
/* 102 */     try (Connection conn = GDDBData.getConnection()) {
/* 103 */       boolean auto = conn.getAutoCommit();
/* 104 */       conn.setAutoCommit(false);
/*     */       
/* 106 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 107 */         ps.setString(1, tableID);
/* 108 */         ps.executeUpdate();
/* 109 */         ps.close();
/*     */         
/* 111 */         DBMerchantTableAlloc.delete(conn, tableID);
/*     */         
/* 113 */         conn.commit();
/*     */       }
/* 115 */       catch (SQLException ex) {
/* 116 */         conn.rollback();
/*     */         
/* 118 */         Object[] args = { tableID, "GDC_MERCHANTTABLE" };
/* 119 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*     */         
/* 121 */         GDMsgLogger.addError(msg);
/* 122 */         GDMsgLogger.addError(ex);
/*     */         
/* 124 */         throw ex;
/*     */       } finally {
/*     */         
/* 127 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 130 */     } catch (SQLException ex) {
/* 131 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 136 */     DBMerchantTable entry = get(record.getFileName());
/*     */     
/* 138 */     if (entry != null)
/*     */       return; 
/* 140 */     DBMerchantTable merchantTable = new DBMerchantTable(record);
/*     */ 
/*     */     
/* 143 */     if (merchantTable.itemIDs == null)
/* 144 */       return;  if (merchantTable.itemIDs.isEmpty())
/*     */       return; 
/* 146 */     String insert = "INSERT INTO GDC_MERCHANTTABLE VALUES (?)";
/*     */     
/* 148 */     try (Connection conn = GDDBData.getConnection()) {
/* 149 */       boolean auto = conn.getAutoCommit();
/* 150 */       conn.setAutoCommit(false);
/*     */       
/* 152 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/*     */         
/* 154 */         ps.setString(1, merchantTable.tableID);
/*     */         
/* 156 */         ps.executeUpdate();
/*     */         
/* 158 */         ps.close();
/* 159 */         conn.commit();
/*     */         
/* 161 */         DBMerchantTableAlloc.insert(conn, merchantTable);
/*     */       }
/* 163 */       catch (SQLException ex) {
/* 164 */         conn.rollback();
/*     */         
/* 166 */         Object[] args = { record.getFileName(), "GDC_MERCHANTTABLE" };
/* 167 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */         
/* 169 */         GDMsgLogger.addLowError(msg);
/* 170 */         GDMsgLogger.addLowError(ex);
/*     */       } finally {
/*     */         
/* 173 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBMerchantTable get(String tableID) {
/* 179 */     DBMerchantTable table = new DBMerchantTable();
/*     */     
/* 181 */     String command = "SELECT * FROM GDC_MERCHANTTABLE WHERE TABLE_ID = ?";
/*     */     
/* 183 */     try(Connection conn = GDDBData.getConnection(); 
/* 184 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 185 */       ps.setString(1, tableID);
/*     */       
/* 187 */       try (ResultSet rs = ps.executeQuery()) {
/* 188 */         List<DBMerchantTable> list = wrap(rs);
/*     */         
/* 190 */         if (list.isEmpty()) { table = null; }
/* 191 */         else { table = list.get(0); }
/*     */         
/* 193 */         conn.commit();
/*     */       }
/* 195 */       catch (SQLException ex) {
/* 196 */         throw ex;
/*     */       }
/*     */     
/* 199 */     } catch (SQLException ex) {
/* 200 */       Object[] args = { tableID, "GDC_MERCHANTTABLE" };
/* 201 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 203 */       GDMsgLogger.addError(msg);
/* 204 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 207 */     return table;
/*     */   }
/*     */   
/*     */   private static List<DBMerchantTable> wrap(ResultSet rs) throws SQLException {
/* 211 */     LinkedList<DBMerchantTable> list = new LinkedList<>();
/*     */     
/* 213 */     while (rs.next()) {
/* 214 */       DBMerchantTable table = new DBMerchantTable();
/*     */       
/* 216 */       table.tableID = rs.getString(1);
/*     */       
/* 218 */       table.itemIDs = DBMerchantTableAlloc.getByMerchantTableID(table.tableID);
/*     */       
/* 220 */       list.add(table);
/*     */     } 
/*     */     
/* 223 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBMerchantTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */