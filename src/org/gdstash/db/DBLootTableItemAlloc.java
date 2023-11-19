/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLIntegrityConstraintViolationException;
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
/*     */ public class DBLootTableItemAlloc
/*     */ {
/*     */   private static final String TABLE_NAME = "GD_LOOTTABLE_ITEM";
/*     */   private static final int ROW_TABLE_ID = 1;
/*     */   private static final int ROW_ITEM_ID = 2;
/*     */   private String tableID;
/*     */   private String itemID;
/*     */   
/*     */   public DBLootTableItemAlloc() {
/*  33 */     this.tableID = null;
/*  34 */     this.itemID = null;
/*     */   }
/*     */   
/*     */   public DBLootTableItemAlloc(String tableID, String itemID) {
/*  38 */     this.tableID = tableID;
/*  39 */     this.itemID = itemID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTableID() {
/*  47 */     return this.tableID;
/*     */   }
/*     */   
/*     */   public String getItemID() {
/*  51 */     return this.itemID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable() throws SQLException {
/*  59 */     String dropTable = "DROP TABLE GD_LOOTTABLE_ITEM";
/*  60 */     String createTable = "CREATE TABLE GD_LOOTTABLE_ITEM (TABLE_ID    VARCHAR(256) NOT NULL, ITEM_ID     VARCHAR(256) NOT NULL, PRIMARY KEY (TABLE_ID, ITEM_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  65 */     try (Connection conn = GDDBData.getConnection()) {
/*  66 */       boolean auto = conn.getAutoCommit();
/*  67 */       conn.setAutoCommit(false);
/*     */       
/*  69 */       try (Statement st = conn.createStatement()) {
/*  70 */         if (GDDBUtil.tableExists(conn, "GD_LOOTTABLE_ITEM")) {
/*  71 */           st.execute(dropTable);
/*     */         }
/*  73 */         st.execute(createTable);
/*  74 */         st.close();
/*     */         
/*  76 */         conn.commit();
/*     */       }
/*  78 */       catch (SQLException ex) {
/*  79 */         conn.rollback();
/*     */         
/*  81 */         Object[] args = { "GD_LOOTTABLE_ITEM" };
/*  82 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/*  84 */         GDMsgLogger.addError(msg);
/*     */         
/*  86 */         throw ex;
/*     */       } finally {
/*     */         
/*  89 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(Connection conn, String tableID) throws SQLException {
/*  95 */     String deleteEntry = "DELETE FROM GD_LOOTTABLE_ITEM WHERE TABLE_ID = ?";
/*     */     
/*  97 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/*  98 */       ps.setString(1, tableID);
/*  99 */       ps.executeUpdate();
/* 100 */       ps.close();
/*     */     }
/* 102 */     catch (SQLException ex) {
/* 103 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void insert(Connection conn, DBLootTable dbTable) throws SQLException {
/* 109 */     if (dbTable.getItemAllocList() == null)
/* 110 */       return;  if (dbTable.getItemAllocList().isEmpty())
/*     */       return; 
/* 112 */     String insert = "INSERT INTO GD_LOOTTABLE_ITEM VALUES (?,?)";
/*     */     
/* 114 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 115 */       for (DBLootTableItemAlloc item : dbTable.getItemAllocList()) {
/*     */         try {
/* 117 */           ps.setString(1, dbTable.getTableID());
/* 118 */           ps.setString(2, item.itemID);
/*     */           
/* 120 */           ps.executeUpdate();
/*     */         }
/* 122 */         catch (SQLIntegrityConstraintViolationException ex) {
/* 123 */           if (ex.getSQLState().equals("23505")) {
/* 124 */             if (!item.itemID.equals("records/mccm/items/misc/token_epic.dbr") && 
/* 125 */               !item.itemID.equals("records/mccm/items/misc/token_legendary.dbr")) {
/* 126 */               Object[] args = { dbTable.getTableID() };
/* 127 */               String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_LOOT_ITEM_DUPLICATE", args);
/*     */               
/* 129 */               GDMsgLogger.addWarning(msg);
/* 130 */               GDMsgLogger.addWarning(ex);
/*     */             } 
/*     */           } else {
/* 133 */             throw ex;
/*     */           } 
/*     */         } 
/*     */         
/* 137 */         ps.clearParameters();
/*     */       } 
/* 139 */       ps.close();
/*     */       
/* 141 */       conn.commit();
/*     */     }
/* 143 */     catch (SQLException ex) {
/* 144 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBLootTableItemAlloc> getByTableID(String tableID) {
/* 149 */     List<DBLootTableItemAlloc> items = null;
/*     */     
/* 151 */     String command = "SELECT * FROM GD_LOOTTABLE_ITEM WHERE TABLE_ID = ?";
/*     */     
/* 153 */     try(Connection conn = GDDBData.getConnection(); 
/* 154 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 155 */       ps.setString(1, tableID);
/*     */       
/* 157 */       try (ResultSet rs = ps.executeQuery()) {
/* 158 */         items = wrap(rs);
/*     */         
/* 160 */         conn.commit();
/*     */       }
/* 162 */       catch (SQLException ex) {
/* 163 */         throw ex;
/*     */       }
/*     */     
/* 166 */     } catch (SQLException ex) {
/* 167 */       Object[] args = { tableID, "GD_LOOTTABLE_ITEM" };
/* 168 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 170 */       GDMsgLogger.addError(msg);
/* 171 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 174 */     return items;
/*     */   }
/*     */   
/*     */   public static List<DBLootTableItemAlloc> getByItemID(String itemID) {
/* 178 */     List<DBLootTableItemAlloc> items = null;
/*     */     
/* 180 */     String command = "SELECT * FROM GD_LOOTTABLE_ITEM WHERE ITEM_ID = ?";
/*     */     
/* 182 */     try(Connection conn = GDDBData.getConnection(); 
/* 183 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 184 */       ps.setString(1, itemID);
/*     */       
/* 186 */       try (ResultSet rs = ps.executeQuery()) {
/* 187 */         items = wrap(rs);
/*     */         
/* 189 */         conn.commit();
/*     */       }
/* 191 */       catch (SQLException ex) {
/* 192 */         throw ex;
/*     */       }
/*     */     
/* 195 */     } catch (SQLException ex) {
/* 196 */       Object[] args = { itemID, "GD_LOOTTABLE_ITEM" };
/* 197 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 199 */       GDMsgLogger.addError(msg);
/* 200 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 203 */     return items;
/*     */   }
/*     */   
/*     */   public static boolean isCraftableItemID(String itemID) {
/* 207 */     List<DBLootTableItemAlloc> items = null;
/* 208 */     boolean craftable = false;
/*     */     
/* 210 */     String command = "SELECT * FROM GD_LOOTTABLE_ITEM WHERE ITEM_ID = ? AND TABLE_ID LIKE 'records/items/loottables/%/tdyn_craft%.dbr'";
/*     */ 
/*     */     
/* 213 */     try(Connection conn = GDDBData.getConnection(); 
/* 214 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 215 */       ps.setString(1, itemID);
/*     */       
/* 217 */       try (ResultSet rs = ps.executeQuery()) {
/* 218 */         items = wrap(rs);
/*     */         
/* 220 */         craftable = !items.isEmpty();
/*     */         
/* 222 */         conn.commit();
/*     */       }
/* 224 */       catch (SQLException ex) {
/* 225 */         craftable = false;
/*     */         
/* 227 */         throw ex;
/*     */       }
/*     */     
/* 230 */     } catch (SQLException ex) {
/* 231 */       Object[] args = { itemID, "GD_LOOTTABLE_ITEM" };
/* 232 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 234 */       GDMsgLogger.addError(msg);
/* 235 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 238 */     return craftable;
/*     */   }
/*     */   
/*     */   private static List<DBLootTableItemAlloc> wrap(ResultSet rs) throws SQLException {
/* 242 */     LinkedList<DBLootTableItemAlloc> list = new LinkedList<>();
/* 243 */     boolean found = false;
/*     */     
/* 245 */     while (rs.next()) {
/* 246 */       DBLootTableItemAlloc item = new DBLootTableItemAlloc();
/*     */       
/* 248 */       item.tableID = rs.getString(1);
/* 249 */       item.itemID = rs.getString(2);
/*     */       
/* 251 */       list.add(item);
/*     */     } 
/*     */     
/* 254 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBLootTableItemAlloc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */