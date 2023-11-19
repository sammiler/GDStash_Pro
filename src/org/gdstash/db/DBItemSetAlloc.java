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
/*     */ public class DBItemSetAlloc
/*     */ {
/*     */   private static final String TABLE_NAME = "GD_ITEMSET_ALLOC";
/*     */   private static final int ROW_ITEMSET_ID = 1;
/*     */   private static final int ROW_ITEM_ID = 2;
/*     */   
/*     */   public static void createTable(Connection conn) throws SQLException {
/*  33 */     String dropTable = "DROP TABLE GD_ITEMSET_ALLOC";
/*  34 */     String createTable = "CREATE TABLE GD_ITEMSET_ALLOC (ITEMSET_ID  VARCHAR(256) NOT NULL, ITEM_ID     VARCHAR(256) NOT NULL, PRIMARY KEY (ITEMSET_ID, ITEM_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  39 */     try (Statement st = conn.createStatement()) {
/*  40 */       if (GDDBUtil.tableExists(conn, "GD_ITEMSET_ALLOC")) {
/*  41 */         st.execute(dropTable);
/*     */       }
/*  43 */       st.execute(createTable);
/*  44 */       st.close();
/*     */       
/*  46 */       conn.commit();
/*     */     }
/*  48 */     catch (SQLException ex) {
/*  49 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(Connection conn, String itemSetID) throws SQLException {
/*  54 */     String deleteEntry = "DELETE FROM GD_ITEMSET_ALLOC WHERE ITEMSET_ID = ?";
/*     */     
/*  56 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/*  57 */       ps.setString(1, itemSetID);
/*  58 */       ps.executeUpdate();
/*  59 */       ps.close();
/*     */     }
/*  61 */     catch (SQLException ex) {
/*  62 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void insert(Connection conn, DBItemSet dbItemSet) throws SQLException {
/*  68 */     if (dbItemSet.getItemIDList() == null)
/*  69 */       return;  if (dbItemSet.getItemIDList().isEmpty())
/*     */       return; 
/*  71 */     String insert = "INSERT INTO GD_ITEMSET_ALLOC VALUES (?,?)";
/*     */     
/*  73 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/*  74 */       for (String itemID : dbItemSet.getItemIDList()) {
/*  75 */         ps.setString(1, dbItemSet.getItemSetID());
/*  76 */         ps.setString(2, itemID);
/*     */         
/*  78 */         ps.executeUpdate();
/*     */         
/*  80 */         ps.clearParameters();
/*     */       } 
/*  82 */       ps.close();
/*     */       
/*  84 */       conn.commit();
/*     */     }
/*  86 */     catch (SQLException ex) {
/*  87 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<String> getByItemSetID(String itemSetID) {
/*  92 */     List<String> itemIDs = null;
/*     */     
/*  94 */     String command = "SELECT * FROM GD_ITEMSET_ALLOC WHERE ITEMSET_ID = ?";
/*     */     
/*  96 */     try(Connection conn = GDDBData.getConnection(); 
/*  97 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*  98 */       ps.setString(1, itemSetID);
/*     */       
/* 100 */       try (ResultSet rs = ps.executeQuery()) {
/* 101 */         itemIDs = wrap(rs);
/*     */         
/* 103 */         conn.commit();
/*     */       }
/* 105 */       catch (SQLException ex) {
/* 106 */         throw ex;
/*     */       }
/*     */     
/* 109 */     } catch (SQLException ex) {
/* 110 */       Object[] args = { itemSetID, "GD_ITEMSET_ALLOC" };
/* 111 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 113 */       GDMsgLogger.addError(msg);
/* 114 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 117 */     return itemIDs;
/*     */   }
/*     */   
/*     */   private static List<String> wrap(ResultSet rs) throws SQLException {
/* 121 */     LinkedList<String> list = new LinkedList<>();
/*     */     
/* 123 */     while (rs.next()) {
/* 124 */       String itemID = rs.getString(2);
/*     */       
/* 126 */       list.add(itemID);
/*     */     } 
/*     */     
/* 129 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBItemSetAlloc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */