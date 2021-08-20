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
/*     */ public class DBEnginePlayerMasteries
/*     */ {
/*     */   public static final String TABLE_NAME = "GDC_PLAYER_MASTERIES";
/*     */   private static final int ROW_SKILLTREE_ID = 1;
/*     */   private static final int ROW_SKILLTREE_INDEX = 2;
/*     */   private String skillTreeID;
/*     */   private int index;
/*     */   
/*     */   public DBEnginePlayerMasteries() {}
/*     */   
/*     */   public DBEnginePlayerMasteries(String skillTreeID, int index) {
/*  36 */     this.skillTreeID = skillTreeID;
/*  37 */     this.index = index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSkillTreeID() {
/*  45 */     return this.skillTreeID;
/*     */   }
/*     */   
/*     */   public int getIndex() {
/*  49 */     return this.index;
/*     */   }
/*     */   
/*     */   public static boolean containsSkillTreeID(List<DBEnginePlayerMasteries> list, String id) {
/*  53 */     boolean found = false;
/*     */     
/*  55 */     for (DBEnginePlayerMasteries mt : list) {
/*  56 */       if (mt.skillTreeID == null)
/*     */         continue; 
/*  58 */       if (mt.skillTreeID.equals(id)) {
/*  59 */         found = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/*  65 */     return found;
/*     */   }
/*     */   
/*     */   public static DBEnginePlayerMasteries retrieveID(List<DBEnginePlayerMasteries> list, String id) {
/*  69 */     DBEnginePlayerMasteries epm = null;
/*     */     
/*  71 */     for (DBEnginePlayerMasteries mt : list) {
/*  72 */       if (mt.skillTreeID == null)
/*     */         continue; 
/*  74 */       if (mt.skillTreeID.equals(id)) {
/*  75 */         epm = mt;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/*  81 */     return epm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable(Connection conn) throws SQLException {
/*  89 */     String dropTable = "DROP TABLE GDC_PLAYER_MASTERIES";
/*  90 */     String createTable = "CREATE TABLE GDC_PLAYER_MASTERIES (SKILLTREE_ID    VARCHAR(256) NOT NULL, SKILLTREE_INDEX INTEGER NOT NULL, PRIMARY KEY (SKILLTREE_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     try (Statement st = conn.createStatement()) {
/*  96 */       if (GDDBUtil.tableExists(conn, "GDC_PLAYER_MASTERIES")) {
/*  97 */         st.execute(dropTable);
/*     */       }
/*  99 */       st.execute(createTable);
/* 100 */       st.close();
/*     */       
/* 102 */       conn.commit();
/*     */     }
/* 104 */     catch (SQLException ex) {
/* 105 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(Connection conn) throws SQLException {
/* 110 */     String deleteEntry = "DELETE FROM GDC_PLAYER_MASTERIES";
/*     */     
/* 112 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 113 */       ps.executeUpdate();
/* 114 */       ps.close();
/*     */     }
/* 116 */     catch (SQLException ex) {
/* 117 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void insert(Connection conn, DBEnginePlayer dbPlayer) throws SQLException {
/* 123 */     if (dbPlayer.getMasteryTreeList() == null)
/* 124 */       return;  if (dbPlayer.getMasteryTreeList().isEmpty())
/*     */       return; 
/* 126 */     String insert = "INSERT INTO GDC_PLAYER_MASTERIES VALUES (?,?)";
/*     */     
/* 128 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 129 */       for (DBEnginePlayerMasteries mt : dbPlayer.getMasteryTreeList()) {
/* 130 */         ps.setString(1, mt.skillTreeID);
/* 131 */         ps.setInt(2, mt.index);
/*     */         
/* 133 */         ps.executeUpdate();
/*     */         
/* 135 */         ps.clearParameters();
/*     */       } 
/* 137 */       ps.close();
/*     */       
/* 139 */       conn.commit();
/*     */     }
/* 141 */     catch (SQLException ex) {
/* 142 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBEnginePlayerMasteries> get() {
/* 147 */     List<DBEnginePlayerMasteries> list = null;
/*     */     
/* 149 */     String command = "SELECT * FROM GDC_PLAYER_MASTERIES";
/*     */     
/* 151 */     try(Connection conn = GDDBData.getConnection(); 
/* 152 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 153 */       try (ResultSet rs = ps.executeQuery()) {
/* 154 */         list = wrap(rs);
/*     */         
/* 156 */         conn.commit();
/*     */       }
/* 158 */       catch (SQLException ex) {
/* 159 */         throw ex;
/*     */       }
/*     */     
/* 162 */     } catch (SQLException ex) {
/* 163 */       Object[] args = { "-", "GDC_PLAYER_MASTERIES" };
/* 164 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 166 */       GDMsgLogger.addError(msg);
/* 167 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 170 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBEnginePlayerMasteries> wrap(ResultSet rs) throws SQLException {
/* 174 */     LinkedList<DBEnginePlayerMasteries> list = new LinkedList<>();
/*     */     
/* 176 */     while (rs.next()) {
/* 177 */       DBEnginePlayerMasteries mt = new DBEnginePlayerMasteries();
/*     */       
/* 179 */       mt.skillTreeID = rs.getString(1);
/* 180 */       mt.index = rs.getInt(2);
/*     */       
/* 182 */       list.add(mt);
/*     */     } 
/*     */     
/* 185 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBEnginePlayerMasteries.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */