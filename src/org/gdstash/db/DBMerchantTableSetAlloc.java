/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ public class DBMerchantTableSetAlloc
/*     */ {
/*     */   private static final String TABLE_NAME = "GDC_MERCHANTTABLESET_ALLOC";
/*     */   private static final int ROW_TABLESET_ID = 1;
/*     */   private static final int ROW_TABLE_ID = 2;
/*     */   private String tableSetID;
/*     */   private String tableID;
/*     */   
/*     */   public static void createTable() throws SQLException {
/*  39 */     String dropTable = "DROP TABLE GDC_MERCHANTTABLESET_ALLOC";
/*  40 */     String createTable = "CREATE TABLE GDC_MERCHANTTABLESET_ALLOC (TABLESET_ID  VARCHAR(256) NOT NULL, TABLE_ID     VARCHAR(256) NOT NULL, PRIMARY KEY (TABLESET_ID, TABLE_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  45 */     try (Connection conn = GDDBData.getConnection()) {
/*  46 */       boolean auto = conn.getAutoCommit();
/*  47 */       conn.setAutoCommit(false);
/*     */       
/*  49 */       try (Statement st = conn.createStatement()) {
/*  50 */         if (GDDBUtil.tableExists(conn, "GDC_MERCHANTTABLESET_ALLOC")) {
/*  51 */           st.execute(dropTable);
/*     */         }
/*  53 */         st.execute(createTable);
/*  54 */         st.close();
/*     */         
/*  56 */         conn.commit();
/*     */       }
/*  58 */       catch (SQLException ex) {
/*  59 */         conn.rollback();
/*     */         
/*  61 */         throw ex;
/*     */       } finally {
/*     */         
/*  64 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(Connection conn, String tableSetID) throws SQLException {
/*  70 */     String deleteEntry = "DELETE FROM GDC_MERCHANTTABLESET_ALLOC WHERE TABLESET_ID = ?";
/*     */     
/*  72 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/*  73 */       ps.setString(1, tableSetID);
/*  74 */       ps.executeUpdate();
/*  75 */       ps.close();
/*     */     }
/*  77 */     catch (SQLException ex) {
/*  78 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void insert(Connection conn, DBMerchantTableSet dbMerchantTableSet) throws SQLException {
/*  84 */     if (dbMerchantTableSet.getTableIDList() == null)
/*  85 */       return;  if (dbMerchantTableSet.getTableIDList().isEmpty())
/*     */       return; 
/*  87 */     String insert = "INSERT INTO GDC_MERCHANTTABLESET_ALLOC VALUES (?,?)";
/*     */     
/*  89 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/*  90 */       for (String table : dbMerchantTableSet.getTableIDList()) {
/*  91 */         ps.setString(1, dbMerchantTableSet.getMerchantTableSetID());
/*  92 */         ps.setString(2, table);
/*     */         
/*  94 */         ps.executeUpdate();
/*     */         
/*  96 */         ps.clearParameters();
/*     */       } 
/*  98 */       ps.close();
/*     */       
/* 100 */       conn.commit();
/*     */     }
/* 102 */     catch (SQLException ex) {
/* 103 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<String> getByMerchantTableSetID(String tableSetID) {
/* 108 */     List<String> tableIDs = null;
/*     */     
/* 110 */     String command = "SELECT * FROM GDC_MERCHANTTABLESET_ALLOC WHERE TABLE_ID = ?";
/*     */     
/* 112 */     try(Connection conn = GDDBData.getConnection(); 
/* 113 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 114 */       ps.setString(1, tableSetID);
/*     */       
/* 116 */       try (ResultSet rs = ps.executeQuery()) {
/* 117 */         tableIDs = wrap(rs);
/*     */         
/* 119 */         conn.commit();
/*     */       }
/* 121 */       catch (SQLException ex) {
/* 122 */         throw ex;
/*     */       }
/*     */     
/* 125 */     } catch (SQLException ex) {
/* 126 */       Object[] args = { tableSetID, "GDC_MERCHANTTABLESET_ALLOC" };
/* 127 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 129 */       GDMsgLogger.addError(msg);
/* 130 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 133 */     return tableIDs;
/*     */   }
/*     */   
/*     */   public static String getTableSetIDByTableID(String tableID) {
/* 137 */     List<String> list = null;
/* 138 */     String tableSetID = null;
/*     */     
/* 140 */     String command = "SELECT * FROM GDC_MERCHANTTABLESET_ALLOC WHERE TABLE_ID = ?";
/*     */     
/* 142 */     try(Connection conn = GDDBData.getConnection(); 
/* 143 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 144 */       ps.setString(1, tableID);
/*     */       
/* 146 */       try (ResultSet rs = ps.executeQuery()) {
/* 147 */         list = wrapTableSetIDs(rs);
/*     */         
/* 149 */         if (list.isEmpty()) { tableID = null; }
/* 150 */         else { tableID = list.get(0); }
/*     */         
/* 152 */         conn.commit();
/*     */       }
/* 154 */       catch (SQLException ex) {
/* 155 */         throw ex;
/*     */       }
/*     */     
/* 158 */     } catch (SQLException ex) {
/* 159 */       Object[] args = { tableID, "GDC_MERCHANTTABLESET_ALLOC" };
/* 160 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 162 */       GDMsgLogger.addError(msg);
/* 163 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 166 */     return tableID;
/*     */   }
/*     */   
/*     */   private static List<String> wrap(ResultSet rs) throws SQLException {
/* 170 */     LinkedList<String> list = new LinkedList<>();
/* 171 */     boolean found = false;
/*     */     
/* 173 */     while (rs.next()) {
/* 174 */       String tableID = rs.getString(2);
/*     */       
/* 176 */       list.add(tableID);
/*     */     } 
/*     */     
/* 179 */     return list;
/*     */   }
/*     */   
/*     */   private static List<String> wrapTableSetIDs(ResultSet rs) throws SQLException {
/* 183 */     LinkedList<String> list = new LinkedList<>();
/* 184 */     boolean found = false;
/*     */     
/* 186 */     while (rs.next()) {
/* 187 */       String tableSetID = rs.getString(1);
/*     */       
/* 189 */       list.add(tableSetID);
/*     */     } 
/*     */     
/* 192 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBMerchantTableSetAlloc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */