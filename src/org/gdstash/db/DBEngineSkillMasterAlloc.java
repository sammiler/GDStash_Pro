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
/*     */ public class DBEngineSkillMasterAlloc
/*     */ {
/*     */   public static final String TABLE_NAME = "GDC_SKILL_MASTER";
/*     */   private static final int ROW_CLASSTABLE_ID = 1;
/*     */   
/*     */   public static void createTable(Connection conn) throws SQLException {
/*  32 */     String dropTable = "DROP TABLE GDC_SKILL_MASTER";
/*  33 */     String createTable = "CREATE TABLE GDC_SKILL_MASTER (CLASSTABLE_ID  VARCHAR(256) NOT NULL, PRIMARY KEY (CLASSTABLE_ID))";
/*     */ 
/*     */ 
/*     */     
/*  37 */     try (Statement st = conn.createStatement()) {
/*  38 */       if (GDDBUtil.tableExists(conn, "GDC_SKILL_MASTER")) {
/*  39 */         st.execute(dropTable);
/*     */       }
/*  41 */       st.execute(createTable);
/*  42 */       st.close();
/*     */       
/*  44 */       conn.commit();
/*     */     }
/*  46 */     catch (SQLException ex) {
/*  47 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void insert(Connection conn, DBEngineSkillMaster dbSkillMaster) throws SQLException {
/*  53 */     if (dbSkillMaster.getClassTableList() == null)
/*  54 */       return;  if (dbSkillMaster.getClassTableList().isEmpty())
/*     */       return; 
/*  56 */     String insert = "INSERT INTO GDC_SKILL_MASTER VALUES (?)";
/*     */     
/*  58 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/*  59 */       for (String classTableID : dbSkillMaster.getClassTableList()) {
/*  60 */         ps.setString(1, classTableID);
/*     */         
/*  62 */         ps.executeUpdate();
/*     */         
/*  64 */         ps.clearParameters();
/*     */       } 
/*  66 */       ps.close();
/*     */       
/*  68 */       conn.commit();
/*     */     }
/*  70 */     catch (SQLException ex) {
/*  71 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<String> get() {
/*  76 */     List<String> classTableIDs = null;
/*     */     
/*  78 */     String command = "SELECT * FROM GDC_SKILL_MASTER";
/*     */     
/*  80 */     try(Connection conn = GDDBData.getConnection(); 
/*  81 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*  82 */       try (ResultSet rs = ps.executeQuery()) {
/*  83 */         classTableIDs = wrap(rs);
/*     */         
/*  85 */         conn.commit();
/*     */       }
/*  87 */       catch (SQLException ex) {
/*  88 */         throw ex;
/*     */       }
/*     */     
/*  91 */     } catch (SQLException ex) {
/*  92 */       Object[] args = { "-", "GDC_SKILL_MASTER" };
/*  93 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/*  95 */       GDMsgLogger.addError(msg);
/*  96 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/*  99 */     return classTableIDs;
/*     */   }
/*     */   
/*     */   private static List<String> wrap(ResultSet rs) throws SQLException {
/* 103 */     LinkedList<String> list = new LinkedList<>();
/*     */     
/* 105 */     while (rs.next()) {
/* 106 */       String classTableID = rs.getString(1);
/*     */       
/* 108 */       list.add(classTableID);
/*     */     } 
/*     */     
/* 111 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBEngineSkillMasterAlloc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */