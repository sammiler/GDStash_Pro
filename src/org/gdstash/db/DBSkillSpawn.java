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
/*     */ public class DBSkillSpawn
/*     */ {
/*     */   public static final String TABLE_NAME = "GD_SKILL_SPAWN";
/*     */   public static final String FIELD_ID = "SKILL_ID";
/*     */   private static final int ROW_SKILL_ID = 1;
/*     */   private static final int ROW_INDEX = 2;
/*     */   private static final int ROW_PET_ID = 3;
/*     */   private String skillID;
/*     */   private int index;
/*     */   private String petID;
/*     */   private DBPet dbPet;
/*     */   
/*     */   private DBSkillSpawn() {}
/*     */   
/*     */   public DBSkillSpawn(int index, String petID) {
/*  40 */     this.index = index;
/*  41 */     this.petID = petID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPetID() {
/*  49 */     return this.petID;
/*     */   }
/*     */   
/*     */   public DBPet getPet() {
/*  53 */     return this.dbPet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable(Connection conn) throws SQLException {
/*  61 */     String dropTable = "DROP TABLE GD_SKILL_SPAWN";
/*  62 */     String createTable = "CREATE TABLE GD_SKILL_SPAWN (SKILL_ID  VARCHAR(256) NOT NULL, INDEX             INTEGER, PET_ID            VARCHAR(256), PRIMARY KEY (SKILL_ID, INDEX))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     try (Statement st = conn.createStatement()) {
/*  70 */       if (GDDBUtil.tableExists(conn, "GD_SKILL_SPAWN")) {
/*  71 */         st.execute(dropTable);
/*     */       }
/*     */       
/*  74 */       st.execute(createTable);
/*  75 */       st.close();
/*     */       
/*  77 */       conn.commit();
/*     */     }
/*  79 */     catch (SQLException ex) {
/*  80 */       Object[] args = { "GD_SKILL_SPAWN" };
/*  81 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */       
/*  83 */       GDMsgLogger.addError(msg);
/*     */       
/*  85 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(Connection conn, String skillID, List<DBSkillSpawn> spawns) throws SQLException {
/*  90 */     String insert = "INSERT INTO GD_SKILL_SPAWN VALUES (?,?,?)";
/*     */     
/*  92 */     if (spawns == null)
/*  93 */       return;  if (spawns.isEmpty())
/*     */       return; 
/*  95 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/*  96 */       for (DBSkillSpawn pet : spawns) {
/*  97 */         ps.setString(1, skillID);
/*  98 */         ps.setInt(2, pet.index);
/*  99 */         ps.setString(3, pet.petID);
/*     */         
/* 101 */         ps.executeUpdate();
/*     */         
/* 103 */         ps.clearParameters();
/*     */       } 
/*     */       
/* 106 */       ps.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBSkillSpawn> getBySkillID(String skillID) {
/* 111 */     List<DBSkillSpawn> list = new LinkedList<>();
/*     */     
/* 113 */     String command = "SELECT * FROM GD_SKILL_SPAWN WHERE SKILL_ID = ? ORDER BY INDEX";
/*     */     
/* 115 */     try(Connection conn = GDDBData.getConnection(); 
/* 116 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 117 */       ps.setString(1, skillID);
/*     */       
/* 119 */       try (ResultSet rs = ps.executeQuery()) {
/* 120 */         list = wrap(rs);
/*     */         
/* 122 */         conn.commit();
/*     */       }
/* 124 */       catch (SQLException ex) {
/* 125 */         throw ex;
/*     */       }
/*     */     
/* 128 */     } catch (SQLException ex) {
/* 129 */       Object[] args = { skillID, "GD_SKILL_SPAWN" };
/* 130 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 132 */       GDMsgLogger.addError(msg);
/* 133 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 136 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBSkillSpawn> wrap(ResultSet rs) throws SQLException {
/* 140 */     LinkedList<DBSkillSpawn> list = new LinkedList<>();
/*     */     
/* 142 */     while (rs.next()) {
/* 143 */       DBSkillSpawn pet = new DBSkillSpawn();
/*     */       
/* 145 */       pet.skillID = rs.getString(1);
/* 146 */       pet.index = rs.getInt(2);
/* 147 */       pet.petID = rs.getString(3);
/*     */       
/* 149 */       if (pet.petID != null) {
/* 150 */         pet.dbPet = DBPet.get(pet.petID);
/*     */       }
/*     */       
/* 153 */       list.add(pet);
/*     */     } 
/*     */     
/* 156 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBSkillSpawn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */