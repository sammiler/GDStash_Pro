/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.Iterator;
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
/*     */ public class DBSkillExperience
/*     */ {
/*     */   private static final String TABLE_NAME = "GD_SKILL_EXPERIENCE";
/*     */   private static final int ROW_SKILL_ID = 1;
/*     */   private static final int ROW_EXPERIENCE = 2;
/*     */   
/*     */   public static void createTable(Connection conn) throws SQLException {
/*  33 */     String dropTable = "DROP TABLE GD_SKILL_EXPERIENCE";
/*  34 */     String createTable = "CREATE TABLE GD_SKILL_EXPERIENCE (SKILL_ID     VARCHAR(256) NOT NULL, EXPERIENCE   INTEGER, PRIMARY KEY ( SKILL_ID, EXPERIENCE))";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  39 */     try (Statement st = conn.createStatement()) {
/*  40 */       if (GDDBUtil.tableExists(conn, "GD_SKILL_EXPERIENCE")) {
/*  41 */         st.execute(dropTable);
/*     */       }
/*  43 */       st.execute(createTable);
/*     */       
/*  45 */       st.close();
/*     */       
/*  47 */       conn.commit();
/*     */     }
/*  49 */     catch (SQLException ex) {
/*  50 */       Object[] args = { "GD_SKILL_EXPERIENCE" };
/*  51 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */       
/*  53 */       GDMsgLogger.addError(msg);
/*     */       
/*  55 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(Connection conn, String skillID) throws SQLException {
/*  60 */     String deleteEntry = "DELETE FROM GD_SKILL_EXPERIENCE WHERE SKILL_ID = ?";
/*     */     
/*  62 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/*  63 */       ps.setString(1, skillID);
/*  64 */       ps.executeUpdate();
/*  65 */       ps.close();
/*     */     }
/*  67 */     catch (SQLException ex) {
/*  68 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(Connection conn, String skillID, List<Integer> xpLevels) throws SQLException {
/*  73 */     String insert = "INSERT INTO GD_SKILL_EXPERIENCE VALUES (?,?)";
/*     */     
/*  75 */     if (xpLevels == null)
/*  76 */       return;  if (xpLevels.isEmpty())
/*     */       return; 
/*  78 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/*  79 */       for (Iterator<Integer> iterator = xpLevels.iterator(); iterator.hasNext(); ) { int i = ((Integer)iterator.next()).intValue();
/*  80 */         ps.setString(1, skillID);
/*  81 */         ps.setInt(2, i);
/*     */         
/*  83 */         ps.executeUpdate();
/*     */         
/*  85 */         ps.clearParameters(); }
/*     */ 
/*     */       
/*  88 */       ps.close();
/*     */       
/*  90 */       conn.commit();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<Integer> getBySkillID(String skillID) {
/*  95 */     List<Integer> list = new LinkedList<>();
/*     */     
/*  97 */     String command = "SELECT * FROM GD_SKILL_EXPERIENCE WHERE SKILL_ID = ? ORDER BY EXPERIENCE";
/*     */     
/*  99 */     try(Connection conn = GDDBData.getConnection(); 
/* 100 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 101 */       ps.setString(1, skillID);
/*     */       
/* 103 */       try (ResultSet rs = ps.executeQuery()) {
/* 104 */         list = wrap(rs);
/*     */         
/* 106 */         conn.commit();
/*     */       }
/* 108 */       catch (SQLException ex) {
/* 109 */         throw ex;
/*     */       }
/*     */     
/* 112 */     } catch (SQLException ex) {
/* 113 */       Object[] args = { skillID, "GD_SKILL_EXPERIENCE" };
/* 114 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 116 */       GDMsgLogger.addError(msg);
/* 117 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 120 */     return list;
/*     */   }
/*     */   
/*     */   private static List<Integer> wrap(ResultSet rs) throws SQLException {
/* 124 */     LinkedList<Integer> list = new LinkedList<>();
/*     */     
/* 126 */     while (rs.next()) {
/* 127 */       int i = rs.getInt(2);
/* 128 */       Integer val = Integer.valueOf(i);
/*     */       
/* 130 */       list.add(val);
/*     */     } 
/*     */     
/* 133 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBSkillExperience.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */