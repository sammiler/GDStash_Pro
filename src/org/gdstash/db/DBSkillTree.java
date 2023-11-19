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
/*     */ public class DBSkillTree
/*     */ {
/*     */   public static final String TABLE_NAME = "GD_SKILLTREE";
/*     */   private static final int ROW_SKILLTREE_ID = 1;
/*  30 */   private static ConcurrentHashMap<String, DBSkillTree> hashBuffer = new ConcurrentHashMap<>();
/*     */   
/*     */   private String skillTreeID;
/*     */   private List<DBSkillTreeAlloc> skillIDs;
/*     */   
/*     */   public DBSkillTree() {
/*  36 */     this.skillTreeID = null;
/*     */     
/*  38 */     this.skillIDs = new LinkedList<>();
/*     */   }
/*     */   
/*     */   public DBSkillTree(String skillTreeID) {
/*  42 */     this();
/*     */     
/*  44 */     this.skillTreeID = skillTreeID;
/*     */   }
/*     */   
/*     */   private DBSkillTree(ARZRecord record) {
/*  48 */     this.skillTreeID = record.getFileName();
/*     */     
/*  50 */     this.skillIDs = record.getMasterySkillList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSkillTreeID() {
/*  58 */     return this.skillTreeID;
/*     */   }
/*     */   
/*     */   public List<String> getSkillIDList() {
/*  62 */     List<String> list = new LinkedList<>();
/*     */     
/*  64 */     for (DBSkillTreeAlloc alloc : this.skillIDs) {
/*  65 */       list.add(alloc.getSkillID());
/*     */     }
/*     */     
/*  68 */     return list;
/*     */   }
/*     */   
/*     */   public List<DBSkillTreeAlloc> getSkillAllocList() {
/*  72 */     return this.skillIDs;
/*     */   }
/*     */   
/*     */   public DBSkill getMasterySkill() {
/*  76 */     if (this.skillIDs == null) return null;
/*     */     
/*  78 */     for (DBSkillTreeAlloc alloc : this.skillIDs) {
/*  79 */       DBSkill skill = DBSkill.get(alloc.getSkillID());
/*     */       
/*  81 */       if (skill != null && 
/*  82 */         skill.isMastery()) {
/*  83 */         return skill;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearBuffer() {
/*  96 */     hashBuffer.clear();
/*     */   }
/*     */   
/*     */   public static void createTables() throws SQLException {
/* 100 */     String dropTable = "DROP TABLE GD_SKILLTREE";
/* 101 */     String createTable = "CREATE TABLE GD_SKILLTREE (SKILLTREE_ID      VARCHAR(256) NOT NULL, PRIMARY KEY (SKILLTREE_ID))";
/*     */ 
/*     */ 
/*     */     
/* 105 */     try (Connection conn = GDDBData.getConnection()) {
/* 106 */       boolean auto = conn.getAutoCommit();
/* 107 */       conn.setAutoCommit(false);
/*     */       
/* 109 */       try (Statement st = conn.createStatement()) {
/* 110 */         if (GDDBUtil.tableExists(conn, "GD_SKILLTREE")) {
/* 111 */           st.execute(dropTable);
/*     */         }
/* 113 */         st.execute(createTable);
/* 114 */         st.close();
/*     */         
/* 116 */         conn.commit();
/*     */         
/* 118 */         DBSkillTreeAlloc.createTable(conn);
/*     */       }
/* 120 */       catch (SQLException ex) {
/* 121 */         conn.rollback();
/*     */         
/* 123 */         Object[] args = { "GD_SKILLTREE" };
/* 124 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 126 */         GDMsgLogger.addError(msg);
/*     */         
/* 128 */         throw ex;
/*     */       } finally {
/*     */         
/* 131 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(String skillTreeID) throws SQLException {
/* 137 */     String deleteEntry = "DELETE FROM GD_SKILLTREE WHERE SKILLTREE_ID = ?";
/*     */     
/* 139 */     try (Connection conn = GDDBData.getConnection()) {
/* 140 */       boolean auto = conn.getAutoCommit();
/* 141 */       conn.setAutoCommit(false);
/*     */       
/* 143 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 144 */         ps.setString(1, skillTreeID);
/* 145 */         ps.executeUpdate();
/* 146 */         ps.close();
/*     */         
/* 148 */         DBSkillTreeAlloc.delete(conn, skillTreeID);
/*     */         
/* 150 */         conn.commit();
/*     */       }
/* 152 */       catch (SQLException ex) {
/* 153 */         conn.rollback();
/*     */         
/* 155 */         Object[] args = { skillTreeID, "GD_SKILLTREE" };
/* 156 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*     */         
/* 158 */         GDMsgLogger.addError(msg);
/* 159 */         GDMsgLogger.addError(ex);
/*     */         
/* 161 */         throw ex;
/*     */       } finally {
/*     */         
/* 164 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 167 */     } catch (SQLException ex) {
/* 168 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 173 */     DBSkillTree entry = get(record.getFileName());
/*     */     
/* 175 */     if (entry != null)
/*     */       return; 
/* 177 */     DBSkillTree skillTree = new DBSkillTree(record);
/*     */ 
/*     */     
/* 180 */     if (skillTree.skillIDs == null)
/* 181 */       return;  if (skillTree.skillIDs.isEmpty())
/*     */       return; 
/* 183 */     String insert = "INSERT INTO GD_SKILLTREE VALUES (?)";
/*     */     
/* 185 */     try (Connection conn = GDDBData.getConnection()) {
/* 186 */       boolean auto = conn.getAutoCommit();
/* 187 */       conn.setAutoCommit(false);
/*     */       
/* 189 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/*     */         
/* 191 */         ps.setString(1, skillTree.skillTreeID);
/*     */         
/* 193 */         ps.executeUpdate();
/*     */         
/* 195 */         ps.close();
/* 196 */         conn.commit();
/*     */         
/* 198 */         DBSkillTreeAlloc.insert(conn, skillTree);
/*     */       }
/* 200 */       catch (SQLException ex) {
/* 201 */         conn.rollback();
/*     */         
/* 203 */         Object[] args = { record.getFileName(), "GD_SKILLTREE" };
/* 204 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */         
/* 206 */         GDMsgLogger.addLowError(msg);
/* 207 */         GDMsgLogger.addLowError(ex);
/*     */       } finally {
/*     */         
/* 210 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBSkillTree> getMasteryTrees() {
/* 216 */     List<DBSkillTree> list = new LinkedList<>();
/*     */     
/* 218 */     List<DBEnginePlayerMasteries> masteries = DBEnginePlayerMasteries.get();
/*     */     
/* 220 */     if (masteries == null) return list;
/*     */     
/* 222 */     for (DBEnginePlayerMasteries mt : masteries) {
/* 223 */       DBSkillTree tree = get(mt.getSkillTreeID());
/*     */       
/* 225 */       if (tree != null) {
/* 226 */         list.add(tree);
/*     */       }
/*     */     } 
/*     */     
/* 230 */     return list;
/*     */   }
/*     */   
/*     */   public static DBSkillTree get(String skillTreeID) {
/* 234 */     DBSkillTree tree = null;
/*     */     
/* 236 */     tree = hashBuffer.get(skillTreeID);
/*     */     
/* 238 */     if (tree == null)
/*     */     {
/* 240 */       tree = getDB(skillTreeID);
/*     */     }
/*     */     
/* 243 */     return tree;
/*     */   }
/*     */   
/*     */   private static DBSkillTree getDB(String skillTreeID) {
/* 247 */     DBSkillTree skillTree = new DBSkillTree();
/*     */     
/* 249 */     String command = "SELECT * FROM GD_SKILLTREE WHERE SKILLTREE_ID = ?";
/*     */     
/* 251 */     try(Connection conn = GDDBData.getConnection(); 
/* 252 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 253 */       ps.setString(1, skillTreeID);
/*     */       
/* 255 */       try (ResultSet rs = ps.executeQuery()) {
/* 256 */         List<DBSkillTree> list = wrap(rs);
/*     */         
/* 258 */         if (list.isEmpty()) { skillTree = null; }
/* 259 */         else { skillTree = list.get(0); }
/*     */         
/* 261 */         conn.commit();
/*     */       }
/* 263 */       catch (SQLException ex) {
/* 264 */         throw ex;
/*     */       }
/*     */     
/* 267 */     } catch (SQLException ex) {
/* 268 */       Object[] args = { skillTreeID, "GD_SKILLTREE" };
/* 269 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 271 */       GDMsgLogger.addError(msg);
/* 272 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 275 */     return skillTree;
/*     */   }
/*     */   
/*     */   public static DBSkillTree getBySkillID(String skillID) {
/* 279 */     DBEnginePlayer player = DBEnginePlayer.get();
/*     */     
/* 281 */     if (player == null) return null;
/*     */     
/* 283 */     List<DBEnginePlayerMasteries> masteries = player.getMasteryTreeList();
/* 284 */     List<DBSkillTreeAlloc> treeIDs = DBSkillTreeAlloc.getAllocBySkillID(skillID);
/*     */     
/* 286 */     if (masteries == null) return null; 
/* 287 */     if (treeIDs == null) return null; 
/* 288 */     if (treeIDs.isEmpty()) return null;
/*     */     
/* 290 */     String id = null;
/*     */     
/* 292 */     for (DBSkillTreeAlloc alloc : treeIDs) {
/* 293 */       if (DBEnginePlayerMasteries.containsSkillTreeID(masteries, alloc.getTreeID())) {
/* 294 */         id = alloc.getTreeID();
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 300 */     if (id == null) return null;
/*     */     
/* 302 */     DBSkillTree skillTree = get(id);
/*     */     
/* 304 */     return skillTree;
/*     */   }
/*     */   
/*     */   private static List<DBSkillTree> wrap(ResultSet rs) throws SQLException {
/* 308 */     LinkedList<DBSkillTree> list = new LinkedList<>();
/*     */     
/* 310 */     while (rs.next()) {
/* 311 */       DBSkillTree skillTree = new DBSkillTree();
/*     */       
/* 313 */       skillTree.skillTreeID = rs.getString(1);
/*     */       
/* 315 */       DBSkillTree buff = hashBuffer.get(skillTree.skillTreeID);
/* 316 */       if (buff != null) {
/* 317 */         list.add(buff);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 322 */       skillTree.skillIDs = DBSkillTreeAlloc.getAllocByTreeID(skillTree.skillTreeID);
/*     */       
/* 324 */       list.add(skillTree);
/* 325 */       hashBuffer.put(skillTree.skillTreeID, skillTree);
/*     */     } 
/*     */     
/* 328 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBSkillTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */