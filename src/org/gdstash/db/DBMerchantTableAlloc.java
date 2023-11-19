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
/*     */ public class DBMerchantTableAlloc
/*     */ {
/*     */   private static final String TABLE_NAME = "GDC_MERCHANTTABLE_ALLOC";
/*     */   private static final int ROW_TABLE_ID = 1;
/*     */   private static final int ROW_ITEM_ID = 2;
/*     */   private String tableID;
/*     */   private String itemID;
/*     */   
/*     */   public static void createTable() throws SQLException {
/*  36 */     String dropTable = "DROP TABLE GDC_MERCHANTTABLE_ALLOC";
/*  37 */     String createTable = "CREATE TABLE GDC_MERCHANTTABLE_ALLOC (TABLE_ID  VARCHAR(256) NOT NULL, ITEM_ID   VARCHAR(256) NOT NULL, PRIMARY KEY (TABLE_ID, ITEM_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  42 */     try (Connection conn = GDDBData.getConnection()) {
/*  43 */       boolean auto = conn.getAutoCommit();
/*  44 */       conn.setAutoCommit(false);
/*     */       
/*  46 */       try (Statement st = conn.createStatement()) {
/*  47 */         if (GDDBUtil.tableExists(conn, "GDC_MERCHANTTABLE_ALLOC")) {
/*  48 */           st.execute(dropTable);
/*     */         }
/*  50 */         st.execute(createTable);
/*  51 */         st.close();
/*     */         
/*  53 */         conn.commit();
/*     */       }
/*  55 */       catch (SQLException ex) {
/*  56 */         conn.rollback();
/*     */         
/*  58 */         throw ex;
/*     */       } finally {
/*     */         
/*  61 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(Connection conn, String tableID) throws SQLException {
/*  67 */     String deleteEntry = "DELETE FROM GDC_MERCHANTTABLE_ALLOC WHERE TABLE_ID = ?";
/*     */     
/*  69 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/*  70 */       ps.setString(1, tableID);
/*  71 */       ps.executeUpdate();
/*  72 */       ps.close();
/*     */     }
/*  74 */     catch (SQLException ex) {
/*  75 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void insert(Connection conn, DBMerchantTable dbMerchantTable) throws SQLException {
/*  81 */     if (dbMerchantTable.getItemIDList() == null)
/*  82 */       return;  if (dbMerchantTable.getItemIDList().isEmpty())
/*     */       return; 
/*  84 */     String insert = "INSERT INTO GDC_MERCHANTTABLE_ALLOC VALUES (?,?)";
/*     */     
/*  86 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/*  87 */       for (String item : dbMerchantTable.getItemIDList()) {
/*  88 */         ps.setString(1, dbMerchantTable.getMerchantTableID());
/*  89 */         ps.setString(2, item);
/*     */         
/*  91 */         ps.executeUpdate();
/*     */         
/*  93 */         ps.clearParameters();
/*     */       } 
/*  95 */       ps.close();
/*     */       
/*  97 */       conn.commit();
/*     */     }
/*  99 */     catch (SQLException ex) {
/* 100 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<String> getByMerchantTableID(String tableID) {
/* 105 */     List<String> itemIDs = null;
/*     */     
/* 107 */     String command = "SELECT * FROM GDC_MERCHANTTABLE_ALLOC WHERE TABLE_ID = ?";
/*     */     
/* 109 */     try(Connection conn = GDDBData.getConnection(); 
/* 110 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 111 */       ps.setString(1, tableID);
/*     */       
/* 113 */       try (ResultSet rs = ps.executeQuery()) {
/* 114 */         itemIDs = wrap(rs);
/*     */         
/* 116 */         conn.commit();
/*     */       }
/* 118 */       catch (SQLException ex) {
/* 119 */         throw ex;
/*     */       }
/*     */     
/* 122 */     } catch (SQLException ex) {
/* 123 */       Object[] args = { tableID, "GDC_MERCHANTTABLE_ALLOC" };
/* 124 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 126 */       GDMsgLogger.addError(msg);
/* 127 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 130 */     return itemIDs;
/*     */   }
/*     */   
/*     */   public static String getTableIDByItemID(String itemID) {
/* 134 */     List<String> list = null;
/* 135 */     String tableID = null;
/*     */     
/* 137 */     String command = "SELECT * FROM GDC_MERCHANTTABLE_ALLOC WHERE ITEM_ID = ?";
/*     */     
/* 139 */     try(Connection conn = GDDBData.getConnection(); 
/* 140 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 141 */       ps.setString(1, itemID);
/*     */       
/* 143 */       try (ResultSet rs = ps.executeQuery()) {
/* 144 */         list = wrapTableIDs(rs);
/*     */         
/* 146 */         if (list.isEmpty()) { tableID = null; }
/* 147 */         else { tableID = list.get(0); }
/*     */         
/* 149 */         conn.commit();
/*     */       }
/* 151 */       catch (SQLException ex) {
/* 152 */         throw ex;
/*     */       }
/*     */     
/* 155 */     } catch (SQLException ex) {
/* 156 */       Object[] args = { itemID, "GDC_MERCHANTTABLE_ALLOC" };
/* 157 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 159 */       GDMsgLogger.addError(msg);
/* 160 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 163 */     return tableID;
/*     */   }
/*     */   
/*     */   private static List<String> wrap(ResultSet rs) throws SQLException {
/* 167 */     LinkedList<String> list = new LinkedList<>();
/* 168 */     boolean found = false;
/*     */     
/* 170 */     while (rs.next()) {
/* 171 */       String itemID = rs.getString(2);
/*     */       
/* 173 */       list.add(itemID);
/*     */     } 
/*     */     
/* 176 */     return list;
/*     */   }
/*     */   
/*     */   private static List<String> wrapTableIDs(ResultSet rs) throws SQLException {
/* 180 */     LinkedList<String> list = new LinkedList<>();
/* 181 */     boolean found = false;
/*     */     
/* 183 */     while (rs.next()) {
/* 184 */       String tableID = rs.getString(1);
/*     */       
/* 186 */       list.add(tableID);
/*     */     } 
/*     */     
/* 189 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBMerchantTableAlloc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */