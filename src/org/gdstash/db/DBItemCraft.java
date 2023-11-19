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
/*     */ public class DBItemCraft
/*     */ {
/*     */   private static final String TABLE_NAME = "GD_ITEM_CRAFT";
/*     */   private static final String FIELD_ID = "ITEM_ID";
/*     */   private static final int ROW_ITEM_ID = 1;
/*     */   private static final int ROW_CRAFT_ID = 2;
/*  30 */   private static ConcurrentHashMap<String, DBItemCraft> hashBuffer = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private String itemID;
/*     */ 
/*     */ 
/*     */   
/*     */   private String craftID;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getItemID() {
/*  44 */     return this.itemID;
/*     */   }
/*     */   
/*     */   public String getCraftID() {
/*  48 */     return this.craftID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemID(String itemID) {
/*  56 */     this.itemID = itemID;
/*     */   }
/*     */   
/*     */   public void setCraftID(String craftID) {
/*  60 */     this.craftID = craftID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearBuffer() {
/*  68 */     hashBuffer.clear();
/*     */   }
/*     */   
/*     */   public static void createTables(Connection conn) throws SQLException {
/*  72 */     String dropTable = "DROP TABLE GD_ITEM_CRAFT";
/*  73 */     String createTable = "CREATE TABLE GD_ITEM_CRAFT (ITEM_ID VARCHAR(256) NOT NULL, CRAFT_ID     VARCHAR(256), PRIMARY KEY (ITEM_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     try (Statement st = conn.createStatement()) {
/*  80 */       if (GDDBUtil.tableExists(conn, "GD_ITEM_CRAFT")) {
/*  81 */         st.execute(dropTable);
/*     */       }
/*  83 */       st.execute(createTable);
/*  84 */       st.close();
/*     */       
/*  86 */       conn.commit();
/*     */     }
/*  88 */     catch (SQLException ex) {
/*  89 */       conn.rollback();
/*     */       
/*  91 */       Object[] args = { "GD_ITEM_CRAFT" };
/*  92 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */       
/*  94 */       GDMsgLogger.addError(msg);
/*     */       
/*  96 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(Connection conn, String itemID) throws SQLException {
/* 101 */     String deleteEntry = "DELETE FROM GD_ITEM_CRAFT WHERE ITEM_ID = ?";
/*     */     
/* 103 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 104 */       ps.setString(1, itemID);
/* 105 */       ps.executeUpdate();
/* 106 */       ps.close();
/*     */     }
/* 108 */     catch (SQLException ex) {
/* 109 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(Connection conn, DBItem dbItem) throws SQLException {
/* 114 */     String insertItem = "INSERT INTO GD_ITEM_CRAFT VALUES (?,?)";
/*     */     
/* 116 */     try (PreparedStatement ps = conn.prepareStatement(insertItem)) {
/* 117 */       ps.setString(1, dbItem.getItemID());
/* 118 */       ps.setString(2, dbItem.getCraftID());
/*     */       
/* 120 */       ps.executeUpdate();
/* 121 */       ps.close();
/*     */       
/* 123 */       conn.commit();
/*     */     }
/* 125 */     catch (SQLException ex) {
/* 126 */       conn.rollback();
/*     */       
/* 128 */       Object[] args = { dbItem.getItemID(), "GD_ITEM_CRAFT" };
/* 129 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */       
/* 131 */       GDMsgLogger.addLowError(msg);
/* 132 */       GDMsgLogger.addLowError(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBItemCraft get(String itemID) {
/* 137 */     DBItemCraft craft = null;
/*     */     
/* 139 */     craft = hashBuffer.get(itemID);
/*     */     
/* 141 */     if (craft == null)
/*     */     {
/* 143 */       craft = getDB(itemID);
/*     */     }
/*     */     
/* 146 */     return craft;
/*     */   }
/*     */   
/*     */   private static DBItemCraft getDB(String itemID) {
/* 150 */     DBItemCraft craft = null;
/*     */     
/* 152 */     String command = "SELECT * FROM GD_ITEM_CRAFT WHERE ITEM_ID = ?";
/*     */     
/* 154 */     try(Connection conn = GDDBData.getConnection(); 
/* 155 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 156 */       ps.setString(1, itemID);
/*     */       
/* 158 */       try (ResultSet rs = ps.executeQuery()) {
/* 159 */         List<DBItemCraft> list = wrap(rs);
/*     */         
/* 161 */         if (list.isEmpty()) { craft = null; }
/* 162 */         else { craft = list.get(0); }
/*     */         
/* 164 */         conn.commit();
/*     */       }
/* 166 */       catch (SQLException ex) {
/* 167 */         throw ex;
/*     */       }
/*     */     
/* 170 */     } catch (SQLException ex) {
/* 171 */       Object[] args = { itemID, "GD_ITEM_CRAFT" };
/* 172 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 174 */       GDMsgLogger.addError(msg);
/* 175 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 178 */     return craft;
/*     */   }
/*     */   
/*     */   public static List<DBItemCraft> getByItemIDs(List<String> itemIDs) {
/* 182 */     List<DBItemCraft> list = new LinkedList<>();
/*     */     
/* 184 */     for (String itemID : itemIDs) {
/* 185 */       DBItemCraft craft = get(itemID);
/* 186 */       list.add(craft);
/*     */     } 
/*     */     
/* 189 */     return list;
/*     */   }
/*     */   
/*     */   public static DBItemCraft getByCraftID(String itemID) {
/* 193 */     DBItemCraft craft = null;
/*     */     
/* 195 */     String command = "SELECT * FROM GD_ITEM_CRAFT WHERE CRAFT_ID = ?";
/*     */     
/* 197 */     try(Connection conn = GDDBData.getConnection(); 
/* 198 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 199 */       ps.setString(1, itemID);
/*     */       
/* 201 */       try (ResultSet rs = ps.executeQuery()) {
/* 202 */         List<DBItemCraft> list = wrap(rs);
/*     */         
/* 204 */         if (list.isEmpty()) { craft = null; }
/* 205 */         else { craft = list.get(0); }
/*     */         
/* 207 */         conn.commit();
/*     */       }
/* 209 */       catch (SQLException ex) {
/* 210 */         throw ex;
/*     */       }
/*     */     
/* 213 */     } catch (SQLException ex) {
/* 214 */       Object[] args = { itemID, "GD_ITEM_CRAFT" };
/* 215 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 217 */       GDMsgLogger.addError(msg);
/* 218 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 221 */     return craft;
/*     */   }
/*     */   
/*     */   public static boolean isCraftableItemID(String itemID) {
/* 225 */     List<DBItemCraft> crafts = null;
/* 226 */     boolean craftable = false;
/*     */     
/* 228 */     String command = "SELECT * FROM GD_ITEM_CRAFT WHERE CRAFT_ID = ?";
/*     */     
/* 230 */     try(Connection conn = GDDBData.getConnection(); 
/* 231 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 232 */       ps.setString(1, itemID);
/*     */       
/* 234 */       try (ResultSet rs = ps.executeQuery()) {
/* 235 */         if (rs.next()) {
/* 236 */           craftable = true;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 241 */         conn.commit();
/*     */       }
/* 243 */       catch (SQLException ex) {
/* 244 */         craftable = false;
/*     */         
/* 246 */         throw ex;
/*     */       }
/*     */     
/* 249 */     } catch (SQLException ex) {
/* 250 */       Object[] args = { itemID, "GD_ITEM_CRAFT" };
/* 251 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 253 */       GDMsgLogger.addError(msg);
/* 254 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 257 */     return craftable;
/*     */   }
/*     */   
/*     */   private static List<DBItemCraft> wrap(ResultSet rs) throws SQLException {
/* 261 */     LinkedList<DBItemCraft> list = new LinkedList<>();
/*     */     
/* 263 */     while (rs.next()) {
/* 264 */       DBItemCraft craft = new DBItemCraft();
/*     */       
/* 266 */       craft.itemID = rs.getString(1);
/* 267 */       craft.craftID = rs.getString(2);
/*     */       
/* 269 */       list.add(craft);
/* 270 */       hashBuffer.put(craft.itemID, craft);
/*     */     } 
/*     */     
/* 273 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBItemCraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */