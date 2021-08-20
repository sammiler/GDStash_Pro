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
/*     */ public class DBController
/*     */ {
/*     */   public static final String TABLE_NAME = "GD_CONTROLLER";
/*     */   public static final String FIELD_ID = "CONTROLLER_ID";
/*     */   private static final int ROW_CONTROLLER_ID = 1;
/*     */   private static final int ROW_TRIGGER_CHANCE = 2;
/*     */   private static final int ROW_TRIGGER_TYPE = 3;
/*     */   public static final String TRIGGER_ENEMY_KILL = "OnKill";
/*     */   public static final String TRIGGER_ENEMY_HIT = "AttackEnemy";
/*     */   public static final String TRIGGER_ENEMY_CRIT = "AttackEnemyCrit";
/*     */   public static final String TRIGGER_SELF_HIT = "HitByEnemy";
/*     */   public static final String TRIGGER_SELF_CRIT = "HitByCrit";
/*     */   public static final String TRIGGER_SELF_HIT_RANGE = "HitByProjectile";
/*     */   public static final String TRIGGER_SELF_HIT_MELEE = "HitByMelee";
/*     */   public static final String TRIGGER_SELF_BLOCK = "Block";
/*     */   public static final String TRIGGER_SELF_LOW_HEALTH = "LowHealth";
/*  42 */   private static ConcurrentHashMap<String, DBController> hashBuffer = new ConcurrentHashMap<>();
/*     */   
/*     */   private String controllerID;
/*     */   private int triggerChance;
/*     */   private String triggerType;
/*     */   
/*     */   public DBController() {
/*  49 */     this.controllerID = null;
/*  50 */     this.triggerChance = 0;
/*  51 */     this.triggerType = null;
/*     */   }
/*     */   
/*     */   private DBController(ARZRecord record) {
/*  55 */     this.controllerID = record.getFileName();
/*     */     
/*  57 */     this.triggerChance = record.getTriggerChance();
/*  58 */     this.triggerType = record.getTriggerType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getControllerID() {
/*  66 */     return this.controllerID;
/*     */   }
/*     */   
/*     */   public int getTriggerChance() {
/*  70 */     return this.triggerChance;
/*     */   }
/*     */   
/*     */   public String getTriggerType() {
/*  74 */     return this.triggerType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearBuffer() {
/*  82 */     hashBuffer.clear();
/*     */   }
/*     */   
/*     */   public static void createTables() throws SQLException {
/*  86 */     String dropTable = "DROP TABLE GD_CONTROLLER";
/*  87 */     String createTable = "CREATE TABLE GD_CONTROLLER (CONTROLLER_ID  VARCHAR(256) NOT NULL, TRIGGER_CHANCE    INTEGER    , TRIGGER_TYPE      VARCHAR(32), PRIMARY KEY (CONTROLLER_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     try (Connection conn = GDDBData.getConnection()) {
/*  95 */       boolean auto = conn.getAutoCommit();
/*  96 */       conn.setAutoCommit(false);
/*     */       
/*  98 */       try (Statement st = conn.createStatement()) {
/*  99 */         if (GDDBUtil.tableExists(conn, "GD_CONTROLLER")) {
/* 100 */           st.execute(dropTable);
/*     */         }
/*     */         
/* 103 */         st.execute(createTable);
/* 104 */         st.close();
/*     */         
/* 106 */         conn.commit();
/*     */       }
/* 108 */       catch (SQLException ex) {
/* 109 */         conn.rollback();
/*     */         
/* 111 */         Object[] args = { "GD_CONTROLLER" };
/* 112 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 114 */         GDMsgLogger.addError(msg);
/*     */         
/* 116 */         throw ex;
/*     */       } finally {
/*     */         
/* 119 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(String controllerID) throws SQLException {
/* 125 */     String deleteEntry = "DELETE FROM GD_CONTROLLER WHERE CONTROLLER_ID = ?";
/*     */     
/* 127 */     try (Connection conn = GDDBData.getConnection()) {
/* 128 */       boolean auto = conn.getAutoCommit();
/* 129 */       conn.setAutoCommit(false);
/*     */       
/* 131 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 132 */         ps.setString(1, controllerID);
/* 133 */         ps.executeUpdate();
/* 134 */         ps.close();
/*     */         
/* 136 */         conn.commit();
/*     */       }
/* 138 */       catch (SQLException ex) {
/* 139 */         conn.rollback();
/*     */         
/* 141 */         Object[] args = { controllerID, "GD_CONTROLLER" };
/* 142 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*     */         
/* 144 */         GDMsgLogger.addError(msg);
/* 145 */         GDMsgLogger.addError(ex);
/*     */         
/* 147 */         throw ex;
/*     */       } finally {
/*     */         
/* 150 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 153 */     } catch (SQLException ex) {
/* 154 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 159 */     DBController entry = get(record.getFileName());
/*     */     
/* 161 */     if (entry != null)
/*     */       return; 
/* 163 */     DBController controller = new DBController(record);
/*     */     
/* 165 */     String insert = "INSERT INTO GD_CONTROLLER VALUES (?,?,?)";
/*     */     
/* 167 */     try (Connection conn = GDDBData.getConnection()) {
/* 168 */       boolean auto = conn.getAutoCommit();
/* 169 */       conn.setAutoCommit(false);
/*     */       
/* 171 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 172 */         ps.setString(1, controller.controllerID);
/* 173 */         ps.setInt(2, controller.triggerChance);
/* 174 */         ps.setString(3, controller.triggerType);
/*     */         
/* 176 */         ps.executeUpdate();
/* 177 */         ps.close();
/*     */         
/* 179 */         conn.commit();
/*     */       }
/* 181 */       catch (SQLException ex) {
/* 182 */         conn.rollback();
/*     */         
/* 184 */         Object[] args = { record.getFileName(), "GD_CONTROLLER" };
/* 185 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */         
/* 187 */         GDMsgLogger.addLowError(msg);
/* 188 */         GDMsgLogger.addLowError(ex);
/*     */       } finally {
/*     */         
/* 191 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBController get(String controllerID) {
/* 197 */     DBController controller = hashBuffer.get(controllerID);
/*     */     
/* 199 */     if (controller == null) {
/* 200 */       controller = getDB(controllerID);
/*     */       
/* 202 */       if (controller != null) hashBuffer.put(controller.controllerID, controller);
/*     */     
/*     */     } 
/* 205 */     return controller;
/*     */   }
/*     */   
/*     */   private static DBController getDB(String controllerID) {
/* 209 */     DBController controller = null;
/*     */     
/* 211 */     String command = "SELECT * FROM GD_CONTROLLER WHERE CONTROLLER_ID = ?";
/*     */     
/* 213 */     try(Connection conn = GDDBData.getConnection(); 
/* 214 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 215 */       ps.setString(1, controllerID);
/*     */       
/* 217 */       try (ResultSet rs = ps.executeQuery()) {
/* 218 */         List<DBController> list = wrap(rs);
/*     */         
/* 220 */         if (list.isEmpty()) {
/* 221 */           controller = null;
/*     */         } else {
/* 223 */           controller = list.get(0);
/*     */         } 
/*     */         
/* 226 */         conn.commit();
/*     */       }
/* 228 */       catch (SQLException ex) {
/* 229 */         throw ex;
/*     */       }
/*     */     
/* 232 */     } catch (SQLException ex) {
/* 233 */       Object[] args = { controllerID, "GD_CONTROLLER" };
/* 234 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 236 */       GDMsgLogger.addError(msg);
/* 237 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 240 */     return controller;
/*     */   }
/*     */   
/*     */   private static List<DBController> wrap(ResultSet rs) throws SQLException {
/* 244 */     LinkedList<DBController> list = new LinkedList<>();
/*     */     
/* 246 */     while (rs.next()) {
/* 247 */       DBController controller = new DBController();
/*     */       
/* 249 */       controller.controllerID = rs.getString(1);
/* 250 */       controller.triggerChance = rs.getInt(2);
/* 251 */       controller.triggerType = rs.getString(3);
/*     */       
/* 253 */       list.add(controller);
/*     */     } 
/*     */     
/* 256 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getTriggerDescription(String triggerType, int triggerChance) {
/* 264 */     if (triggerType == null) return null; 
/* 265 */     if (triggerChance == 0) return null;
/*     */     
/* 267 */     String tag = null;
/* 268 */     if (triggerType.equals("OnKill")) tag = "SKILL_TRIGGER_ENEMY_KILL"; 
/* 269 */     if (triggerType.equals("AttackEnemy")) tag = "SKILL_TRIGGER_ENEMY_HIT"; 
/* 270 */     if (triggerType.equals("AttackEnemyCrit")) tag = "SKILL_TRIGGER_ENEMY_CRIT"; 
/* 271 */     if (triggerType.equals("HitByEnemy")) tag = "SKILL_TRIGGER_SELF_HIT"; 
/* 272 */     if (triggerType.equals("HitByCrit")) tag = "SKILL_TRIGGER_SELF_CRIT"; 
/* 273 */     if (triggerType.equals("HitByProjectile")) tag = "SKILL_TRIGGER_SELF_HIT_RANGE"; 
/* 274 */     if (triggerType.equals("HitByMelee")) tag = "SKILL_TRIGGER_SELF_HIT_MELEE"; 
/* 275 */     if (triggerType.equals("Block")) tag = "SKILL_TRIGGER_SELF_BLOCK"; 
/* 276 */     if (triggerType.equals("LowHealth")) tag = "SKILL_TRIGGER_SELF_LOW_HEALTH";
/*     */     
/* 278 */     if (tag == null) return null;
/*     */     
/* 280 */     String text = null;
/*     */     
/* 282 */     Object[] iArgs = { Integer.valueOf(triggerChance) };
/* 283 */     text = GDMsgFormatter.format(GDMsgFormatter.rbGD, tag, iArgs);
/*     */     
/* 285 */     return text;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */