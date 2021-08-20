/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.file.DDSLoader;
/*     */ import org.gdstash.file.GDParseException;
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
/*     */ public class DBSkillConnector
/*     */ {
/*     */   public static final String TABLE_NAME = "GD_SKILL_CONNECTOR";
/*     */   public static final String FIELD_ID = "SKILL_ID";
/*     */   private static final int ROW_SKILL_ID = 1;
/*     */   private static final int ROW_INDEX = 2;
/*     */   private static final int ROW_CONNECTION_OFF_ID = 3;
/*     */   private static final int ROW_CONNECTION_OFF = 4;
/*     */   private static final int ROW_CONNECTION_ON_ID = 5;
/*     */   private static final int ROW_CONNECTION_ON = 6;
/*     */   private String skillID;
/*     */   private int index;
/*     */   private String connectionOffID;
/*     */   private String connectionOnID;
/*     */   private byte[] connectionOff;
/*     */   private byte[] connectionOn;
/*     */   private BufferedImage imageOff;
/*     */   private BufferedImage imageOn;
/*     */   
/*     */   public static class ImageInfo
/*     */   {
/*     */     public String skillID;
/*     */     public int index;
/*     */     public String connectionOffID;
/*     */     public String connectionOnID;
/*     */     public byte[] connectionOff;
/*     */     public byte[] connectionOn;
/*     */   }
/*     */   
/*     */   public DBSkillConnector() {}
/*     */   
/*     */   public DBSkillConnector(int index, String connectionOffID, String connectionOnID) {
/*  62 */     this.index = index;
/*  63 */     this.connectionOffID = connectionOffID;
/*  64 */     this.connectionOnID = connectionOnID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/*  72 */     return this.index;
/*     */   }
/*     */   
/*     */   public BufferedImage getConnectionOff() {
/*  76 */     return this.imageOff;
/*     */   }
/*     */   
/*     */   public BufferedImage getConnectionOn() {
/*  80 */     return this.imageOn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConnectionOffID(String connectionOffID) {
/*  88 */     this.connectionOffID = connectionOffID;
/*     */   }
/*     */   
/*     */   public void setConnectionOnID(String connectionOnID) {
/*  92 */     this.connectionOnID = connectionOnID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable(Connection conn) throws SQLException {
/* 100 */     String dropTable = "DROP TABLE GD_SKILL_CONNECTOR";
/* 101 */     String createTable = "CREATE TABLE GD_SKILL_CONNECTOR (SKILL_ID  VARCHAR(256) NOT NULL, INDEX             INTEGER, CONNECTION_OFF_ID VARCHAR(256), CONNECTION_OFF    BLOB(128K), CONNECTION_ON_ID  VARCHAR(256), CONNECTION_ON     BLOB(128K), PRIMARY KEY (SKILL_ID, INDEX))";
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
/* 112 */     try (Statement st = conn.createStatement()) {
/* 113 */       if (GDDBUtil.tableExists(conn, "GD_SKILL_CONNECTOR")) {
/* 114 */         st.execute(dropTable);
/*     */       }
/* 116 */       st.execute(createTable);
/*     */       
/* 118 */       st.close();
/*     */       
/* 120 */       conn.commit();
/*     */     }
/* 122 */     catch (SQLException ex) {
/* 123 */       Object[] args = { "GD_SKILL_CONNECTOR" };
/* 124 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */       
/* 126 */       GDMsgLogger.addError(msg);
/*     */       
/* 128 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(Connection conn, String skillID) throws SQLException {
/* 133 */     String deleteEntry = "DELETE FROM GD_SKILL_CONNECTOR WHERE SKILL_ID = ?";
/*     */     
/* 135 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 136 */       ps.setString(1, skillID);
/* 137 */       ps.executeUpdate();
/* 138 */       ps.close();
/*     */     }
/* 140 */     catch (SQLException ex) {
/* 141 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(Connection conn, String skillID, List<DBSkillConnector> connections) throws SQLException {
/* 146 */     String insert = "INSERT INTO GD_SKILL_CONNECTOR VALUES (?,?,?,?,?,?)";
/*     */     
/* 148 */     if (connections == null)
/* 149 */       return;  if (connections.isEmpty())
/*     */       return; 
/* 151 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 152 */       for (DBSkillConnector connection : connections) {
/* 153 */         ps.setString(1, skillID);
/* 154 */         ps.setInt(2, connection.index);
/* 155 */         ps.setString(3, connection.connectionOffID);
/* 156 */         if (connection.connectionOff == null) {
/* 157 */           ps.setBlob(4, (Blob)null);
/*     */         } else {
/* 159 */           ps.setBlob(4, new ByteArrayInputStream(connection.connectionOff));
/*     */         } 
/* 161 */         ps.setString(5, connection.connectionOnID);
/* 162 */         if (connection.connectionOn == null) {
/* 163 */           ps.setBlob(6, (Blob)null);
/*     */         } else {
/* 165 */           ps.setBlob(6, new ByteArrayInputStream(connection.connectionOn));
/*     */         } 
/*     */         
/* 168 */         ps.executeUpdate();
/*     */         
/* 170 */         ps.clearParameters();
/*     */       } 
/*     */       
/* 173 */       ps.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBSkillConnector> getBySkillID(String skillID) {
/* 178 */     if (skillID == null) return null;
/*     */     
/* 180 */     List<DBSkillConnector> list = new LinkedList<>();
/*     */     
/* 182 */     String command = "SELECT * FROM GD_SKILL_CONNECTOR WHERE SKILL_ID = ?ORDER BY INDEX";
/*     */ 
/*     */     
/* 185 */     try(Connection conn = GDDBData.getConnection(); 
/* 186 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 187 */       ps.setString(1, skillID);
/*     */       
/* 189 */       try (ResultSet rs = ps.executeQuery()) {
/* 190 */         list = wrap(rs);
/*     */         
/* 192 */         conn.commit();
/*     */       }
/* 194 */       catch (SQLException ex) {
/* 195 */         throw ex;
/*     */       }
/*     */     
/* 198 */     } catch (SQLException ex) {
/* 199 */       Object[] args = { skillID, "GD_SKILL_CONNECTOR" };
/* 200 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 202 */       GDMsgLogger.addError(msg);
/* 203 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 206 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBSkillConnector> wrap(ResultSet rs) throws SQLException {
/* 210 */     LinkedList<DBSkillConnector> list = new LinkedList<>();
/* 211 */     Blob blob = null;
/*     */     
/* 213 */     while (rs.next()) {
/* 214 */       DBSkillConnector connection = new DBSkillConnector();
/*     */       
/* 216 */       connection.skillID = rs.getString(1);
/* 217 */       connection.index = rs.getInt(2);
/* 218 */       connection.connectionOffID = rs.getString(3);
/* 219 */       blob = rs.getBlob(4);
/* 220 */       if (blob == null) {
/* 221 */         connection.connectionOff = null;
/*     */       } else {
/* 223 */         connection.connectionOff = blob.getBytes(1L, (int)blob.length());
/*     */       } 
/*     */       
/* 226 */       if (connection.connectionOff != null) {
/*     */         try {
/* 228 */           connection.imageOff = DDSLoader.getImage(connection.connectionOff);
/*     */         }
/* 230 */         catch (GDParseException ex) {
/* 231 */           Object[] args = { connection.skillID };
/* 232 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_BITMAP_DECODE_FAILED", args);
/*     */           
/* 234 */           GDMsgLogger.addError(msg);
/*     */           
/* 236 */           connection.imageOff = null;
/*     */         } 
/*     */       }
/*     */       
/* 240 */       connection.connectionOnID = rs.getString(5);
/* 241 */       blob = rs.getBlob(6);
/* 242 */       if (blob == null) {
/* 243 */         connection.connectionOn = null;
/*     */       } else {
/* 245 */         connection.connectionOn = blob.getBytes(1L, (int)blob.length());
/*     */       } 
/*     */       
/* 248 */       if (connection.connectionOn != null) {
/*     */         try {
/* 250 */           connection.imageOn = DDSLoader.getImage(connection.connectionOn);
/*     */         }
/* 252 */         catch (GDParseException ex) {
/* 253 */           Object[] args = { connection.skillID };
/* 254 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_BITMAP_DECODE_FAILED", args);
/*     */           
/* 256 */           GDMsgLogger.addError(msg);
/*     */           
/* 258 */           connection.imageOn = null;
/*     */         } 
/*     */       }
/*     */       
/* 262 */       list.add(connection);
/*     */     } 
/*     */     
/* 265 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<ImageInfo> getImageInfos(String id) {
/* 273 */     List<ImageInfo> list = new LinkedList<>();
/* 274 */     Blob blob = null;
/*     */     
/* 276 */     String command = "SELECT SKILL_ID, INDEX, CONNECTION_OFF_ID, CONNECTION_OFF, CONNECTION_ON_ID, CONNECTION_ON FROM GD_SKILL_CONNECTOR WHERE ((CONNECTION_OFF_ID LIKE '" + id + "%' AND CONNECTION_OFF IS NULL) OR (CONNECTION_ON_ID LIKE '" + id + "%' AND CONNECTION_ON IS NULL))";
/*     */ 
/*     */ 
/*     */     
/* 280 */     try(Connection conn = GDDBData.getConnection(); 
/* 281 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 282 */       try (ResultSet rs = ps.executeQuery()) {
/* 283 */         while (rs.next()) {
/* 284 */           ImageInfo info = new ImageInfo();
/*     */           
/* 286 */           info.skillID = rs.getString(1);
/* 287 */           info.index = rs.getInt(2);
/*     */           
/* 289 */           info.connectionOffID = rs.getString(3);
/* 290 */           blob = rs.getBlob(4);
/* 291 */           if (blob == null) {
/* 292 */             info.connectionOff = null;
/*     */           } else {
/* 294 */             info.connectionOff = blob.getBytes(1L, (int)blob.length());
/*     */           } 
/*     */           
/* 297 */           info.connectionOnID = rs.getString(5);
/* 298 */           blob = rs.getBlob(6);
/* 299 */           if (blob == null) {
/* 300 */             info.connectionOn = null;
/*     */           } else {
/* 302 */             info.connectionOn = blob.getBytes(1L, (int)blob.length());
/*     */           } 
/*     */           
/* 305 */           list.add(info);
/*     */         } 
/*     */         
/* 308 */         conn.commit();
/*     */       }
/* 310 */       catch (Exception ex) {
/* 311 */         throw ex;
/*     */       }
/*     */     
/* 314 */     } catch (Exception ex) {
/* 315 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 318 */     return list;
/*     */   }
/*     */   
/*     */   public static void deleteNoImageSkills() {
/* 322 */     String command = "DELETE FROM GD_SKILL_CONNECTOR WHERE BITMAP_UP IS NULL";
/*     */     
/* 324 */     try(Connection conn = GDDBData.getConnection(); 
/* 325 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 326 */       ps.execute();
/*     */       
/* 328 */       conn.commit();
/*     */     }
/* 330 */     catch (Exception ex) {
/* 331 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void updateImageInfo(List<ImageInfo> list) {
/* 336 */     if (list == null)
/*     */       return; 
/* 338 */     String command = "UPDATE GD_SKILL_CONNECTOR SET CONNECTION_OFF = ?, CONNECTION_ON = ? WHERE SKILL_ID = ? AND INDEX = ?";
/*     */ 
/*     */     
/* 341 */     try (Connection conn = GDDBData.getConnection()) {
/* 342 */       boolean auto = conn.getAutoCommit();
/* 343 */       conn.setAutoCommit(false);
/*     */       
/* 345 */       for (ImageInfo info : list) {
/* 346 */         try (PreparedStatement ps = conn.prepareStatement(command)) {
/* 347 */           if (info.connectionOff != null || info.connectionOn != null)
/*     */           {
/*     */             
/* 350 */             if (info.connectionOff == null) {
/* 351 */               ps.setBlob(1, (Blob)null);
/*     */             } else {
/* 353 */               ps.setBlob(1, new ByteArrayInputStream(info.connectionOff));
/*     */             } 
/*     */             
/* 356 */             if (info.connectionOn == null) {
/* 357 */               ps.setBlob(2, (Blob)null);
/*     */             } else {
/* 359 */               ps.setBlob(2, new ByteArrayInputStream(info.connectionOn));
/*     */             } 
/*     */             
/* 362 */             ps.setString(3, info.skillID);
/* 363 */             ps.setInt(4, info.index);
/*     */             
/* 365 */             ps.executeUpdate();
/* 366 */             ps.close();
/*     */             
/* 368 */             conn.commit();
/*     */           }
/*     */         
/* 371 */         } catch (SQLException ex) {
/* 372 */           conn.rollback();
/*     */           
/* 374 */           Object[] args = { info.skillID };
/* 375 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_IN_ITEM_IMAGE_SIZE", args);
/*     */           
/* 377 */           GDMsgLogger.addWarning(msg);
/*     */           
/* 379 */           GDMsgLogger.addWarning(ex);
/*     */         }
/*     */       
/*     */       } 
/* 383 */     } catch (SQLException ex) {
/* 384 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBSkillConnector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */