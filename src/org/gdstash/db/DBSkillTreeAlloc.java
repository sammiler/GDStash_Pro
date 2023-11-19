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
/*     */ public class DBSkillTreeAlloc
/*     */ {
/*     */   public static final String TABLE_NAME = "GD_SKILLTREE_ALLOC";
/*     */   private static final int ROW_SKILLTREE_ID = 1;
/*     */   private static final int ROW_SKILL_ID = 2;
/*     */   private static final int ROW_INDEX = 3;
/*     */   private String treeID;
/*     */   private String skillID;
/*     */   private int index;
/*     */   
/*     */   public DBSkillTreeAlloc() {
/*  35 */     this.treeID = null;
/*  36 */     this.skillID = null;
/*  37 */     this.index = 0;
/*     */   }
/*     */   
/*     */   public DBSkillTreeAlloc(String skillID, int index) {
/*  41 */     this.treeID = null;
/*  42 */     this.skillID = skillID;
/*  43 */     this.index = index;
/*     */   }
/*     */   
/*     */   public String getSkillID() {
/*  47 */     return this.skillID;
/*     */   }
/*     */   
/*     */   public String getTreeID() {
/*  51 */     return this.treeID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable(Connection conn) throws SQLException {
/*  59 */     String dropTable = "DROP TABLE GD_SKILLTREE_ALLOC";
/*  60 */     String createTable = "CREATE TABLE GD_SKILLTREE_ALLOC (SKILLTREE_ID       VARCHAR(256) NOT NULL, SKILL_ID           VARCHAR(256) NOT NULL, INDEX              INTEGER NOT NULL, PRIMARY KEY (SKILLTREE_ID, SKILL_ID, INDEX))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  66 */     try (Statement st = conn.createStatement()) {
/*  67 */       if (GDDBUtil.tableExists(conn, "GD_SKILLTREE_ALLOC")) {
/*  68 */         st.execute(dropTable);
/*     */       }
/*  70 */       st.execute(createTable);
/*  71 */       st.close();
/*     */       
/*  73 */       conn.commit();
/*     */     }
/*  75 */     catch (SQLException ex) {
/*  76 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(Connection conn, String skillTreeID) throws SQLException {
/*  81 */     String deleteEntry = "DELETE FROM GD_SKILLTREE_ALLOC WHERE SKILLTREE_ID = ?";
/*     */     
/*  83 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/*  84 */       ps.setString(1, skillTreeID);
/*  85 */       ps.executeUpdate();
/*  86 */       ps.close();
/*     */     }
/*  88 */     catch (SQLException ex) {
/*  89 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void insert(Connection conn, DBSkillTree dbSkillTree) throws SQLException {
/*  95 */     if (dbSkillTree.getSkillIDList() == null)
/*  96 */       return;  if (dbSkillTree.getSkillIDList().isEmpty())
/*     */       return; 
/*  98 */     String insert = "INSERT INTO GD_SKILLTREE_ALLOC VALUES (?,?,?)";
/*     */     
/* 100 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 101 */       for (DBSkillTreeAlloc alloc : dbSkillTree.getSkillAllocList()) {
/* 102 */         ps.setString(1, dbSkillTree.getSkillTreeID());
/* 103 */         ps.setString(2, alloc.skillID);
/* 104 */         ps.setInt(3, alloc.index);
/*     */         
/* 106 */         ps.executeUpdate();
/*     */         
/* 108 */         ps.clearParameters();
/*     */       } 
/* 110 */       ps.close();
/*     */       
/* 112 */       conn.commit();
/*     */     }
/* 114 */     catch (SQLException ex) {
/* 115 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBSkillTreeAlloc> getAllocByTreeID(String skillTreeID) {
/* 120 */     List<DBSkillTreeAlloc> skillIDs = null;
/*     */     
/* 122 */     String command = "SELECT * FROM GD_SKILLTREE_ALLOC WHERE SKILLTREE_ID = ? ORDER BY INDEX";
/*     */     
/* 124 */     try(Connection conn = GDDBData.getConnection(); 
/* 125 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 126 */       ps.setString(1, skillTreeID);
/*     */       
/* 128 */       try (ResultSet rs = ps.executeQuery()) {
/* 129 */         skillIDs = wrapAlloc(rs);
/*     */         
/* 131 */         conn.commit();
/*     */       }
/* 133 */       catch (SQLException ex) {
/* 134 */         throw ex;
/*     */       }
/*     */     
/* 137 */     } catch (SQLException ex) {
/* 138 */       Object[] args = { skillTreeID, "GD_SKILLTREE_ALLOC" };
/* 139 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 141 */       GDMsgLogger.addError(msg);
/* 142 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 145 */     return skillIDs;
/*     */   }
/*     */   
/*     */   public static List<DBSkillTreeAlloc> getAllocBySkillID(String skillID) {
/* 149 */     List<DBSkillTreeAlloc> skillIDs = null;
/*     */     
/* 151 */     String command = "SELECT * FROM GD_SKILLTREE_ALLOC WHERE SKILL_ID = ?";
/*     */     
/* 153 */     try(Connection conn = GDDBData.getConnection(); 
/* 154 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 155 */       ps.setString(1, skillID);
/*     */       
/* 157 */       try (ResultSet rs = ps.executeQuery()) {
/* 158 */         skillIDs = wrapAlloc(rs);
/*     */         
/* 160 */         conn.commit();
/*     */       }
/* 162 */       catch (SQLException ex) {
/* 163 */         throw ex;
/*     */       }
/*     */     
/* 166 */     } catch (SQLException ex) {
/* 167 */       Object[] args = { skillID, "GD_SKILLTREE_ALLOC" };
/* 168 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 170 */       GDMsgLogger.addError(msg);
/* 171 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 174 */     return skillIDs;
/*     */   }
/*     */   
/*     */   public static List<String> getSkillIDByTreeID(String skillTreeID) {
/* 178 */     List<String> skillIDs = null;
/*     */     
/* 180 */     String command = "SELECT * FROM GD_SKILLTREE_ALLOC WHERE SKILLTREE_ID = ? ORDER BY INDEX";
/*     */     
/* 182 */     try(Connection conn = GDDBData.getConnection(); 
/* 183 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 184 */       ps.setString(1, skillTreeID);
/*     */       
/* 186 */       try (ResultSet rs = ps.executeQuery()) {
/* 187 */         skillIDs = wrapSkill(rs);
/*     */         
/* 189 */         conn.commit();
/*     */       }
/* 191 */       catch (SQLException ex) {
/* 192 */         throw ex;
/*     */       }
/*     */     
/* 195 */     } catch (SQLException ex) {
/* 196 */       Object[] args = { skillTreeID, "GD_SKILLTREE_ALLOC" };
/* 197 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 199 */       GDMsgLogger.addError(msg);
/* 200 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 203 */     return skillIDs;
/*     */   }
/*     */   
/*     */   public static String getTreeIDBySkillID(String skillID) {
/* 207 */     List<String> skillIDs = null;
/*     */     
/* 209 */     String command = "SELECT * FROM GD_SKILLTREE_ALLOC, GDC_PLAYER_MASTERIES WHERE GD_SKILLTREE_ALLOC.SKILL_ID = ? AND GD_SKILLTREE_ALLOC.SKILLTREE_ID = GDC_PLAYER_MASTERIES.SKILLTREE_ID";
/*     */ 
/*     */ 
/*     */     
/* 213 */     try(Connection conn = GDDBData.getConnection(); 
/* 214 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 215 */       ps.setString(1, skillID);
/*     */       
/* 217 */       try (ResultSet rs = ps.executeQuery()) {
/* 218 */         skillIDs = wrapTree(rs);
/*     */         
/* 220 */         conn.commit();
/*     */       }
/* 222 */       catch (SQLException ex) {
/* 223 */         throw ex;
/*     */       }
/*     */     
/* 226 */     } catch (SQLException ex) {
/* 227 */       Object[] args = { skillID, "GD_SKILLTREE_ALLOC" };
/* 228 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 230 */       GDMsgLogger.addError(msg);
/* 231 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 234 */     if (skillIDs == null) return null; 
/* 235 */     if (skillIDs.isEmpty()) return null;
/*     */     
/* 237 */     return skillIDs.get(0);
/*     */   }
/*     */   
/*     */   private static List<String> wrapSkill(ResultSet rs) throws SQLException {
/* 241 */     LinkedList<String> list = new LinkedList<>();
/*     */     
/* 243 */     while (rs.next()) {
/* 244 */       String skillID = rs.getString(2);
/*     */       
/* 246 */       list.add(skillID);
/*     */     } 
/*     */     
/* 249 */     return list;
/*     */   }
/*     */   
/*     */   private static List<String> wrapTree(ResultSet rs) throws SQLException {
/* 253 */     LinkedList<String> list = new LinkedList<>();
/*     */     
/* 255 */     while (rs.next()) {
/* 256 */       String treeID = rs.getString(1);
/*     */       
/* 258 */       list.add(treeID);
/*     */     } 
/*     */     
/* 261 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBSkillTreeAlloc> wrapAlloc(ResultSet rs) throws SQLException {
/* 265 */     LinkedList<DBSkillTreeAlloc> list = new LinkedList<>();
/*     */     
/* 267 */     while (rs.next()) {
/* 268 */       DBSkillTreeAlloc alloc = new DBSkillTreeAlloc();
/*     */       
/* 270 */       alloc.treeID = rs.getString(1);
/* 271 */       alloc.skillID = rs.getString(2);
/* 272 */       alloc.index = rs.getInt(3);
/*     */       
/* 274 */       list.add(alloc);
/*     */     } 
/*     */     
/* 277 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBSkillTreeAlloc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */