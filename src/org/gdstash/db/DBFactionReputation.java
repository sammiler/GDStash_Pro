/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.gdstash.file.ARCDecompress;
/*     */ import org.gdstash.file.ARZRecord;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.util.GDConstants;
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
/*     */ public class DBFactionReputation
/*     */ {
/*     */   private static final String TABLE_NAME = "GDC_FACTION_REPUTATION";
/*     */   private static final int ROW_INDEX = 1;
/*     */   private static final int ROW_VALUE = 2;
/*     */   private static final int ROW_STATE_TAG = 3;
/*     */   private static final int ROW_STATE = 4;
/*     */   private int index;
/*     */   private int value;
/*     */   private String stateTag;
/*     */   private String stateText;
/*     */   
/*     */   public int getIndex() {
/*  42 */     return this.index;
/*     */   }
/*     */   
/*     */   public int getValue() {
/*  46 */     return this.value;
/*     */   }
/*     */   
/*     */   public String getStateTag() {
/*  50 */     return this.stateTag;
/*     */   }
/*     */   
/*     */   public String getStateText() {
/*  54 */     return this.stateText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIndex(int index) {
/*  62 */     this.index = index;
/*     */   }
/*     */   
/*     */   public void setValue(int value) {
/*  66 */     this.value = value;
/*     */   }
/*     */   
/*     */   public void setStateTag(String stateTag) {
/*  70 */     this.stateTag = stateTag;
/*     */     
/*  72 */     if (stateTag == null) {
/*  73 */       this.stateText = null;
/*     */     } else {
/*  75 */       this.stateText = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_UI, stateTag, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable(Connection conn) throws SQLException {
/*  84 */     String dropTable = "DROP TABLE GDC_FACTION_REPUTATION";
/*  85 */     String createTable = "CREATE TABLE GDC_FACTION_REPUTATION (INDEX       INTEGER NOT NULL, VALUE       INTEGER, STATE_TAG   VARCHAR(64), STATE_TEXT  VARCHAR(256), PRIMARY KEY (INDEX))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     try (Statement st = conn.createStatement()) {
/*  93 */       if (GDDBUtil.tableExists(conn, "GDC_FACTION_REPUTATION")) {
/*  94 */         st.execute(dropTable);
/*     */       }
/*  96 */       st.execute(createTable);
/*  97 */       st.close();
/*     */       
/*  99 */       conn.commit();
/*     */     }
/* 101 */     catch (SQLException ex) {
/* 102 */       conn.rollback();
/*     */       
/* 104 */       Object[] args = { "GDC_FACTION_REPUTATION" };
/* 105 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */       
/* 107 */       GDMsgLogger.addError(msg);
/*     */       
/* 109 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(Connection conn) throws SQLException {
/* 114 */     String deleteEntry = "DELETE FROM GDC_FACTION_REPUTATION";
/*     */     
/* 116 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/*     */       
/* 118 */       ps.executeUpdate();
/* 119 */       ps.close();
/*     */     }
/* 121 */     catch (SQLException ex) {
/* 122 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(Connection conn, ARZRecord record, DBFactionReputation reputation) throws SQLException {
/* 127 */     String insert = "INSERT INTO GDC_FACTION_REPUTATION VALUES (?,?,?,?)";
/*     */     
/* 129 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 130 */       if (reputation.index > 0) {
/* 131 */         ps.setInt(1, reputation.index);
/* 132 */         ps.setInt(2, reputation.value);
/* 133 */         ps.setString(3, reputation.stateTag);
/* 134 */         ps.setString(4, reputation.stateText);
/*     */         
/* 136 */         ps.executeUpdate();
/*     */         
/* 138 */         ps.clearParameters();
/*     */         
/* 140 */         ps.close();
/*     */         
/* 142 */         conn.commit();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBFactionReputation> get() {
/* 148 */     List<DBFactionReputation> list = null;
/*     */     
/* 150 */     String command = "SELECT * FROM GDC_FACTION_REPUTATION ORDER BY VALUE";
/*     */     
/* 152 */     try(Connection conn = GDDBData.getConnection(); 
/* 153 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*     */       
/* 155 */       try (ResultSet rs = ps.executeQuery()) {
/* 156 */         list = wrap(rs);
/*     */         
/* 158 */         conn.commit();
/*     */       }
/* 160 */       catch (SQLException ex) {
/* 161 */         throw ex;
/*     */       }
/*     */     
/* 164 */     } catch (SQLException ex) {
/* 165 */       Object[] args = { "-", "GDC_FACTION_REPUTATION" };
/* 166 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 168 */       GDMsgLogger.addError(msg);
/* 169 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 172 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBFactionReputation> wrap(ResultSet rs) throws SQLException {
/* 176 */     List<DBFactionReputation> list = new ArrayList<>(8);
/*     */     
/* 178 */     while (rs.next()) {
/* 179 */       DBFactionReputation rep = new DBFactionReputation();
/*     */       
/* 181 */       rep.index = rs.getInt(1);
/* 182 */       rep.value = rs.getInt(2);
/* 183 */       rep.stateTag = rs.getString(3);
/* 184 */       rep.stateText = rs.getString(4);
/*     */       
/* 186 */       if (rep.index > 0) list.add(rep);
/*     */     
/*     */     } 
/* 189 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBFactionReputation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */