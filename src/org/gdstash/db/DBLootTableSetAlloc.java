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
/*     */ public class DBLootTableSetAlloc
/*     */ {
/*     */   private static final String TABLE_NAME = "GD_LOOTTABLESET_ALLOC";
/*     */   private static final int ROW_TABLESET_ID = 1;
/*     */   private static final int ROW_LEVEL_MIN = 2;
/*     */   private static final int ROW_TABLE_ID = 3;
/*  33 */   private int levelMin = -1;
/*  34 */   private String tableID = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinLevel() {
/*  42 */     return this.levelMin;
/*     */   }
/*     */   
/*     */   public String getTableID() {
/*  46 */     return this.tableID;
/*     */   }
/*     */   
/*     */   public static boolean contains(List<DBLootTableSetAlloc> list, String tableID) {
/*  50 */     if (list == null) return false; 
/*  51 */     if (tableID == null) return false;
/*     */     
/*  53 */     for (DBLootTableSetAlloc alloc : list) {
/*  54 */       if (alloc.tableID.equals(tableID)) return true;
/*     */     
/*     */     } 
/*  57 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinLevel(int levelMin) {
/*  65 */     this.levelMin = levelMin;
/*     */   }
/*     */   
/*     */   public void setTableID(String tableID) {
/*  69 */     this.tableID = tableID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable() throws SQLException {
/*  77 */     String dropTable = "DROP TABLE GD_LOOTTABLESET_ALLOC";
/*  78 */     String createTable = "CREATE TABLE GD_LOOTTABLESET_ALLOC (TABLESET_ID VARCHAR(256) NOT NULL, LEVEL_MIN   INTEGER, TABLE_ID    VARCHAR(256) NOT NULL, PRIMARY KEY (TABLESET_ID, LEVEL_MIN, TABLE_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     try (Connection conn = GDDBData.getConnection()) {
/*  85 */       boolean auto = conn.getAutoCommit();
/*  86 */       conn.setAutoCommit(false);
/*     */       
/*  88 */       try (Statement st = conn.createStatement()) {
/*  89 */         if (GDDBUtil.tableExists(conn, "GD_LOOTTABLESET_ALLOC")) {
/*  90 */           st.execute(dropTable);
/*     */         }
/*  92 */         st.execute(createTable);
/*  93 */         st.close();
/*     */         
/*  95 */         conn.commit();
/*     */       }
/*  97 */       catch (SQLException ex) {
/*  98 */         conn.rollback();
/*     */         
/* 100 */         Object[] args = { "GD_LOOTTABLESET_ALLOC" };
/* 101 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 103 */         GDMsgLogger.addError(msg);
/*     */         
/* 105 */         throw ex;
/*     */       } finally {
/*     */         
/* 108 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(Connection conn, String tableSetID) throws SQLException {
/* 114 */     String deleteEntry = "DELETE FROM GD_LOOTTABLESET_ALLOC WHERE TABLESET_ID = ?";
/*     */     
/* 116 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 117 */       ps.setString(1, tableSetID);
/* 118 */       ps.executeUpdate();
/* 119 */       ps.close();
/*     */     }
/* 121 */     catch (SQLException ex) {
/* 122 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void insert(Connection conn, DBLootTableSet dbTableSet) throws SQLException {
/* 128 */     if (dbTableSet.getAllocList() == null)
/* 129 */       return;  if (dbTableSet.getAllocList().isEmpty())
/*     */       return; 
/* 131 */     String insert = "INSERT INTO GD_LOOTTABLESET_ALLOC VALUES (?,?,?)";
/*     */     
/* 133 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 134 */       for (DBLootTableSetAlloc entry : dbTableSet.getAllocList()) {
/* 135 */         if (entry.tableID != null) {
/* 136 */           ps.setString(1, dbTableSet.getTableSetID());
/* 137 */           ps.setInt(2, entry.levelMin);
/* 138 */           ps.setString(3, entry.tableID);
/*     */           
/* 140 */           ps.executeUpdate();
/*     */           
/* 142 */           ps.clearParameters();
/*     */         } 
/*     */       } 
/* 145 */       ps.close();
/*     */       
/* 147 */       conn.commit();
/*     */     }
/* 149 */     catch (SQLException ex) {
/* 150 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBLootTableSetAlloc> getByTableSetID(String tableSetID) {
/* 155 */     List<DBLootTableSetAlloc> list = null;
/*     */     
/* 157 */     String command = "SELECT * FROM GD_LOOTTABLESET_ALLOC WHERE TABLESET_ID = ?";
/*     */     
/* 159 */     try(Connection conn = GDDBData.getConnection(); 
/* 160 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 161 */       ps.setString(1, tableSetID);
/*     */       
/* 163 */       try (ResultSet rs = ps.executeQuery()) {
/* 164 */         list = wrap(rs);
/*     */         
/* 166 */         conn.commit();
/*     */       }
/* 168 */       catch (SQLException ex) {
/* 169 */         throw ex;
/*     */       }
/*     */     
/* 172 */     } catch (SQLException ex) {
/* 173 */       Object[] args = { tableSetID, "GD_LOOTTABLESET_ALLOC" };
/* 174 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 176 */       GDMsgLogger.addError(msg);
/* 177 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 180 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBLootTableSetAlloc> wrap(ResultSet rs) throws SQLException {
/* 184 */     LinkedList<DBLootTableSetAlloc> list = new LinkedList<>();
/*     */     
/* 186 */     while (rs.next()) {
/* 187 */       DBLootTableSetAlloc entry = new DBLootTableSetAlloc();
/*     */       
/* 189 */       entry.levelMin = rs.getInt(2);
/* 190 */       entry.tableID = rs.getString(3);
/*     */       
/* 192 */       list.add(entry);
/*     */     } 
/*     */     
/* 195 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBLootTableSetAlloc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */