/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
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
/*     */ public class DBEngineMasteryTier
/*     */ {
/*     */   private static final String TABLE_NAME = "GDC_MASTERY_TIER";
/*     */   private static final int ROW_ID = 1;
/*     */   private static final int ROW_INDEX = 2;
/*     */   private static final int ROW_MASTERY_LEVEL = 3;
/*     */   private String id;
/*     */   private int index;
/*     */   private int masteryLevel;
/*     */   
/*     */   public DBEngineMasteryTier() {}
/*     */   
/*     */   public DBEngineMasteryTier(int index, int masteryLevel) {
/*  38 */     this.id = "DEFAULT";
/*     */     
/*  40 */     this.index = index;
/*  41 */     this.masteryLevel = masteryLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMasteryTier() {
/*  49 */     return this.index + 1;
/*     */   }
/*     */   
/*     */   public int getMasteryLevel() {
/*  53 */     return this.masteryLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable(Connection conn) throws SQLException {
/*  61 */     String dropTable = "DROP TABLE GDC_MASTERY_TIER";
/*  62 */     String createTable = "CREATE TABLE GDC_MASTERY_TIER (ID            VARCHAR(8) NOT NULL, INDEX         INTEGER, MASTERY_LEVEL INTEGER, PRIMARY KEY (ID, INDEX))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     try (Statement st = conn.createStatement()) {
/*  69 */       if (GDDBUtil.tableExists(conn, "GDC_MASTERY_TIER")) {
/*  70 */         st.execute(dropTable);
/*     */       }
/*  72 */       st.execute(createTable);
/*  73 */       st.close();
/*     */       
/*  75 */       conn.commit();
/*     */     }
/*  77 */     catch (SQLException ex) {
/*  78 */       conn.rollback();
/*     */       
/*  80 */       Object[] args = { "GDC_MASTERY_TIER" };
/*  81 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */       
/*  83 */       GDMsgLogger.addError(msg);
/*     */       
/*  85 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(Connection conn) throws SQLException {
/*  90 */     String deleteEntry = "DELETE FROM GDC_MASTERY_TIER";
/*     */     
/*  92 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/*  93 */       ps.executeUpdate();
/*  94 */       ps.close();
/*     */     }
/*  96 */     catch (SQLException ex) {
/*  97 */       conn.rollback();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(Connection conn, ARZRecord record, DBEngineMasteryTier masteryTier) throws SQLException {
/* 102 */     String insert = "INSERT INTO GDC_MASTERY_TIER VALUES (?,?,?)";
/*     */     
/* 104 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 105 */       ps.setString(1, "DEFAULT");
/* 106 */       ps.setInt(2, masteryTier.index);
/* 107 */       ps.setInt(3, masteryTier.masteryLevel);
/*     */       
/* 109 */       ps.executeUpdate();
/*     */       
/* 111 */       ps.clearParameters();
/*     */       
/* 113 */       ps.close();
/*     */       
/* 115 */       conn.commit();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBEngineMasteryTier> get() {
/* 120 */     List<DBEngineMasteryTier> list = null;
/*     */     
/* 122 */     String command = "SELECT * FROM GDC_MASTERY_TIER ORDER BY INDEX";
/*     */     
/* 124 */     try(Connection conn = GDDBData.getConnection(); 
/* 125 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*     */       
/* 127 */       try (ResultSet rs = ps.executeQuery()) {
/* 128 */         list = wrap(rs);
/*     */         
/* 130 */         conn.commit();
/*     */       }
/* 132 */       catch (SQLException ex) {
/* 133 */         throw ex;
/*     */       }
/*     */     
/* 136 */     } catch (SQLException ex) {
/* 137 */       Object[] args = { "-", "GDC_MASTERY_TIER" };
/* 138 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 140 */       GDMsgLogger.addError(msg);
/* 141 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 144 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBEngineMasteryTier> wrap(ResultSet rs) throws SQLException {
/* 148 */     List<DBEngineMasteryTier> list = new ArrayList<>(8);
/*     */     
/* 150 */     while (rs.next()) {
/* 151 */       DBEngineMasteryTier mt = new DBEngineMasteryTier();
/*     */       
/* 153 */       mt.id = rs.getString(1);
/* 154 */       mt.index = rs.getInt(2);
/* 155 */       mt.masteryLevel = rs.getInt(3);
/*     */       
/* 157 */       list.add(mt);
/*     */     } 
/*     */     
/* 160 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBEngineMasteryTier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */