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
/*     */ 
/*     */ public class DBLootTableAffixSetAlloc
/*     */ {
/*     */   private static final String TABLE_NAME = "GD_LOOTTABLE_AFFIXSET";
/*     */   private static final int ROW_TABLE_ID = 1;
/*     */   private static final int ROW_TYPE = 2;
/*     */   private static final int ROW_INDEX = 3;
/*     */   private static final int ROW_LEVEL_MIN = 4;
/*     */   private static final int ROW_LEVEL_MAX = 5;
/*     */   private static final int ROW_AFFIXSET_ID = 6;
/*     */   public static final int TYPE_PREFIX_NORMAL = 1;
/*     */   public static final int TYPE_SUFFIX_NORMAL = 2;
/*     */   public static final int TYPE_PREFIX_RARE = 3;
/*     */   public static final int TYPE_SUFFIX_RARE = 4;
/*     */   private String tableID;
/*     */   private int type;
/*     */   private int index;
/*     */   private int levelMin;
/*     */   private int levelMax;
/*     */   private String affixSetID;
/*     */   private DBAffixSet affixSet;
/*     */   
/*     */   public String getTableID() {
/*  53 */     return this.tableID;
/*     */   }
/*     */   
/*     */   public int getAffixType() {
/*  57 */     return this.type;
/*     */   }
/*     */   
/*     */   public int getIndex() {
/*  61 */     return this.index;
/*     */   }
/*     */   
/*     */   public int getMinLevel() {
/*  65 */     return this.levelMin;
/*     */   }
/*     */   
/*     */   public int getMaxLevel() {
/*  69 */     return this.levelMax;
/*     */   }
/*     */   
/*     */   public String getAffixSetID() {
/*  73 */     return this.affixSetID;
/*     */   }
/*     */   
/*     */   public List<DBAffixSet.DBEntry> getAffixEntries() {
/*  77 */     if (this.affixSet == null) return null;
/*     */     
/*  79 */     return this.affixSet.getAffixEntries();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTableID(String tableID) {
/*  87 */     this.tableID = tableID;
/*     */   }
/*     */   
/*     */   public void setAffixType(int type) {
/*  91 */     this.type = type;
/*     */   }
/*     */   
/*     */   public void setIndex(int index) {
/*  95 */     this.index = index;
/*     */   }
/*     */   
/*     */   public void setMinLevel(int levelMin) {
/*  99 */     this.levelMin = levelMin;
/*     */   }
/*     */   
/*     */   public void setMaxLevel(int levelMax) {
/* 103 */     this.levelMax = levelMax;
/*     */   }
/*     */   
/*     */   public void setAffixSetID(String affixSetID) {
/* 107 */     this.affixSetID = affixSetID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable() throws SQLException {
/* 115 */     String dropTable = "DROP TABLE GD_LOOTTABLE_AFFIXSET";
/* 116 */     String createTable = "CREATE TABLE GD_LOOTTABLE_AFFIXSET (TABLE_ID    VARCHAR(256) NOT NULL, TYPE        INTEGER, INDEX       INTEGER, LEVEL_MIN   INTEGER, LEVEL_MAX   INTEGER, AFFIXSET_ID VARCHAR(256) NOT NULL, PRIMARY KEY (TABLE_ID, TYPE, INDEX))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     try (Connection conn = GDDBData.getConnection()) {
/* 127 */       boolean auto = conn.getAutoCommit();
/* 128 */       conn.setAutoCommit(false);
/*     */       
/* 130 */       try (Statement st = conn.createStatement()) {
/* 131 */         if (GDDBUtil.tableExists(conn, "GD_LOOTTABLE_AFFIXSET")) {
/* 132 */           st.execute(dropTable);
/*     */         }
/* 134 */         st.execute(createTable);
/* 135 */         st.close();
/*     */         
/* 137 */         conn.commit();
/*     */       }
/* 139 */       catch (SQLException ex) {
/* 140 */         conn.rollback();
/*     */         
/* 142 */         Object[] args = { "GD_LOOTTABLE_AFFIXSET" };
/* 143 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 145 */         GDMsgLogger.addError(msg);
/*     */         
/* 147 */         throw ex;
/*     */       } finally {
/*     */         
/* 150 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(Connection conn, String tableID) throws SQLException {
/* 156 */     String deleteEntry = "DELETE FROM GD_LOOTTABLE_AFFIXSET WHERE TABLE_ID = ?";
/*     */     
/* 158 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 159 */       ps.setString(1, tableID);
/* 160 */       ps.executeUpdate();
/* 161 */       ps.close();
/*     */     }
/* 163 */     catch (SQLException ex) {
/* 164 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void insert(Connection conn, DBLootTable dbTable) throws SQLException {
/* 170 */     if (dbTable.getAffixSetAllocList() == null)
/* 171 */       return;  if (dbTable.getAffixSetAllocList().isEmpty())
/*     */       return; 
/* 173 */     String insert = "INSERT INTO GD_LOOTTABLE_AFFIXSET VALUES (?,?,?,?,?,?)";
/*     */     
/* 175 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 176 */       for (DBLootTableAffixSetAlloc affixSet : dbTable.getAffixSetAllocList()) {
/* 177 */         if (affixSet.affixSetID != null) {
/* 178 */           ps.setString(1, dbTable.getTableID());
/* 179 */           ps.setInt(2, affixSet.type);
/* 180 */           ps.setInt(3, affixSet.index);
/* 181 */           ps.setInt(4, affixSet.levelMin);
/* 182 */           ps.setInt(5, affixSet.levelMax);
/* 183 */           ps.setString(6, affixSet.affixSetID);
/*     */           
/* 185 */           ps.executeUpdate();
/*     */           
/* 187 */           ps.clearParameters();
/*     */         } 
/*     */       } 
/* 190 */       ps.close();
/*     */       
/* 192 */       conn.commit();
/*     */     }
/* 194 */     catch (SQLException ex) {
/* 195 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBLootTableAffixSetAlloc> getByTableID(String tableID) {
/* 200 */     List<DBLootTableAffixSetAlloc> list = null;
/*     */     
/* 202 */     String command = "SELECT * FROM GD_LOOTTABLE_AFFIXSET WHERE TABLE_ID = ?";
/*     */     
/* 204 */     try(Connection conn = GDDBData.getConnection(); 
/* 205 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 206 */       ps.setString(1, tableID);
/*     */       
/* 208 */       try (ResultSet rs = ps.executeQuery()) {
/* 209 */         list = wrap(rs);
/*     */         
/* 211 */         conn.commit();
/*     */       }
/* 213 */       catch (SQLException ex) {
/* 214 */         throw ex;
/*     */       }
/*     */     
/* 217 */     } catch (SQLException ex) {
/* 218 */       Object[] args = { tableID, "GD_LOOTTABLE_AFFIXSET" };
/* 219 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 221 */       GDMsgLogger.addError(msg);
/* 222 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 225 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBLootTableAffixSetAlloc> wrap(ResultSet rs) throws SQLException {
/* 229 */     LinkedList<DBLootTableAffixSetAlloc> list = new LinkedList<>();
/*     */     
/* 231 */     while (rs.next()) {
/* 232 */       DBLootTableAffixSetAlloc set = new DBLootTableAffixSetAlloc();
/*     */       
/* 234 */       set.tableID = rs.getString(1);
/* 235 */       set.type = rs.getInt(2);
/* 236 */       set.index = rs.getInt(3);
/* 237 */       set.levelMin = rs.getInt(4);
/* 238 */       set.levelMax = rs.getInt(5);
/* 239 */       set.affixSetID = rs.getString(6);
/*     */       
/* 241 */       if (set.affixSetID != null) set.affixSet = DBAffixSet.get(set.affixSetID);
/*     */       
/* 243 */       list.add(set);
/*     */     } 
/*     */     
/* 246 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBLootTableAffixSetAlloc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */