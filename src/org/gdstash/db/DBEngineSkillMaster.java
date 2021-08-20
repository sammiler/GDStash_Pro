/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.LinkedList;
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
/*     */ public class DBEngineSkillMaster
/*     */ {
/*     */   private static DBEngineSkillMaster singleton;
/*     */   private List<String> classTableIDs;
/*     */   
/*     */   public DBEngineSkillMaster() {
/*  25 */     this.classTableIDs = new LinkedList<>();
/*     */   }
/*     */   
/*     */   public DBEngineSkillMaster(ARZRecord record) {
/*  29 */     this.classTableIDs = record.getSkillMasterList();
/*     */   }
/*     */   
/*     */   public List<String> getClassTableList() {
/*  33 */     return this.classTableIDs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTables() throws SQLException {
/*  41 */     try (Connection conn = GDDBData.getConnection()) {
/*  42 */       boolean auto = conn.getAutoCommit();
/*  43 */       conn.setAutoCommit(false);
/*     */       
/*     */       try {
/*  46 */         DBEngineSkillMasterAlloc.createTable(conn);
/*     */       }
/*  48 */       catch (SQLException ex) {
/*  49 */         conn.rollback();
/*     */         
/*  51 */         Object[] args = { "GDC_SKILL_MASTER" };
/*  52 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/*  54 */         GDMsgLogger.addError(msg);
/*     */         
/*  56 */         throw ex;
/*     */       } finally {
/*     */         
/*  59 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/*  67 */     singleton = null;
/*     */     
/*  69 */     DBEngineSkillMaster entry = get();
/*     */     
/*  71 */     if (entry != null && 
/*  72 */       entry.classTableIDs != null && 
/*  73 */       !entry.classTableIDs.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  77 */     DBEngineSkillMaster skillMaster = new DBEngineSkillMaster(record);
/*     */ 
/*     */     
/*  80 */     if (skillMaster.classTableIDs == null)
/*  81 */       return;  if (skillMaster.classTableIDs.isEmpty())
/*     */       return; 
/*  83 */     try (Connection conn = GDDBData.getConnection()) {
/*  84 */       boolean auto = conn.getAutoCommit();
/*  85 */       conn.setAutoCommit(false);
/*     */       
/*     */       try {
/*  88 */         DBEngineSkillMasterAlloc.insert(conn, skillMaster);
/*     */       }
/*  90 */       catch (SQLException ex) {
/*  91 */         conn.rollback();
/*     */         
/*  93 */         Object[] args = { record.getFileName(), "GDC_SKILL_MASTER" };
/*  94 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */         
/*  96 */         GDMsgLogger.addLowError(msg);
/*  97 */         GDMsgLogger.addLowError(ex);
/*     */       } finally {
/*     */         
/* 100 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBEngineSkillMaster get() {
/* 106 */     if (singleton != null) return singleton;
/*     */     
/* 108 */     singleton = new DBEngineSkillMaster();
/*     */     
/* 110 */     singleton.classTableIDs = DBEngineSkillMasterAlloc.get();
/*     */     
/* 112 */     return singleton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 120 */     singleton = null;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBEngineSkillMaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */