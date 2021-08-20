/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Blob;
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
/*     */ public class DBSkillDependency
/*     */ {
/*     */   public static final String TABLE_NAME = "GD_SKILL_DEPEND";
/*     */   public static final String FIELD_ID = "SKILL_ID";
/*     */   private static final int ROW_SKILL_ID = 1;
/*     */   private static final int ROW_DEP_SKILL_ID = 2;
/*     */   private String skillID;
/*     */   private String depSkillID;
/*     */   
/*     */   public DBSkillDependency() {}
/*     */   
/*     */   public DBSkillDependency(String skillID, String depSkillID) {
/*  36 */     this.skillID = skillID;
/*  37 */     this.depSkillID = depSkillID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDependentSkillID() {
/*  45 */     return this.depSkillID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable(Connection conn) throws SQLException {
/*  53 */     String dropTable = "DROP TABLE GD_SKILL_DEPEND";
/*  54 */     String createTable = "CREATE TABLE GD_SKILL_DEPEND (SKILL_ID  VARCHAR(256) NOT NULL, DEP_SKILL_ID VARCHAR(256), PRIMARY KEY (SKILL_ID, DEP_SKILL_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  61 */     try (Statement st = conn.createStatement()) {
/*  62 */       if (GDDBUtil.tableExists(conn, "GD_SKILL_DEPEND")) {
/*  63 */         st.execute(dropTable);
/*     */       }
/*  65 */       st.execute(createTable);
/*     */       
/*  67 */       st.close();
/*     */       
/*  69 */       conn.commit();
/*     */     }
/*  71 */     catch (SQLException ex) {
/*  72 */       Object[] args = { "GD_SKILL_DEPEND" };
/*  73 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */       
/*  75 */       GDMsgLogger.addError(msg);
/*     */       
/*  77 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(Connection conn, String skillID) throws SQLException {
/*  82 */     String deleteEntry = "DELETE FROM GD_SKILL_DEPEND WHERE SKILL_ID = ?";
/*     */     
/*  84 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/*  85 */       ps.setString(1, skillID);
/*  86 */       ps.executeUpdate();
/*  87 */       ps.close();
/*     */     }
/*  89 */     catch (SQLException ex) {
/*  90 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(Connection conn, String skillID, List<DBSkillDependency> dependencies) throws SQLException {
/*  95 */     String insert = "INSERT INTO GD_SKILL_DEPEND VALUES (?,?)";
/*     */     
/*  97 */     if (dependencies == null)
/*  98 */       return;  if (dependencies.isEmpty())
/*     */       return; 
/* 100 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 101 */       for (DBSkillDependency dep : dependencies) {
/* 102 */         ps.setString(1, skillID);
/* 103 */         ps.setString(2, dep.depSkillID);
/*     */         
/* 105 */         ps.executeUpdate();
/*     */         
/* 107 */         ps.clearParameters();
/*     */       } 
/*     */       
/* 110 */       ps.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBSkillDependency> getBySkillID(String skillID) {
/* 115 */     if (skillID == null) return null;
/*     */     
/* 117 */     List<DBSkillDependency> list = new LinkedList<>();
/*     */     
/* 119 */     String command = "SELECT * FROM GD_SKILL_DEPEND WHERE SKILL_ID = ?";
/*     */     
/* 121 */     try(Connection conn = GDDBData.getConnection(); 
/* 122 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 123 */       ps.setString(1, skillID);
/*     */       
/* 125 */       try (ResultSet rs = ps.executeQuery()) {
/* 126 */         list = wrap(rs);
/*     */         
/* 128 */         conn.commit();
/*     */       }
/* 130 */       catch (SQLException ex) {
/* 131 */         throw ex;
/*     */       }
/*     */     
/* 134 */     } catch (SQLException ex) {
/* 135 */       Object[] args = { skillID, "GD_SKILL_DEPEND" };
/* 136 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 138 */       GDMsgLogger.addError(msg);
/* 139 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 142 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBSkillDependency> wrap(ResultSet rs) throws SQLException {
/* 146 */     LinkedList<DBSkillDependency> list = new LinkedList<>();
/* 147 */     Blob blob = null;
/*     */     
/* 149 */     while (rs.next()) {
/* 150 */       DBSkillDependency dep = new DBSkillDependency();
/*     */       
/* 152 */       dep.skillID = rs.getString(1);
/* 153 */       dep.depSkillID = rs.getString(2);
/*     */       
/* 155 */       list.add(dep);
/*     */     } 
/*     */     
/* 158 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBSkillDependency.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */