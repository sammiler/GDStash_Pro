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
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.gdstash.file.ARZRecord;
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
/*     */ public class DBBitmap
/*     */ {
/*     */   public static final String TABLE_NAME = "GDC_BITMAP";
/*     */   private static final int ROW_RECORD_ID = 1;
/*     */   private static final int ROW_BITMAP_ID = 2;
/*     */   private static final int ROW_BITMAP = 3;
/*     */   private static final int ROW_POS_X = 4;
/*     */   private static final int ROW_POS_Y = 5;
/*     */   
/*     */   public static class ImageInfo
/*     */   {
/*     */     public String recordID;
/*     */     public String bitmapID;
/*     */     public byte[] bitmap;
/*     */   }
/*  44 */   private static ConcurrentHashMap<String, DBBitmap> hashBuffer = new ConcurrentHashMap<>();
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
/*  57 */   private String recordID = null;
/*  58 */   private String bitmapID = null;
/*  59 */   private byte[] bitmap = null;
/*  60 */   private int posX = 0;
/*  61 */   private int posY = 0;
/*  62 */   private BufferedImage image = null;
/*     */ 
/*     */   
/*     */   public DBBitmap(ARZRecord record) {
/*  66 */     this();
/*     */     
/*  68 */     this.recordID = record.getFileName();
/*  69 */     this.bitmapID = record.getBitmapID();
/*  70 */     this.posX = record.getPosX();
/*  71 */     this.posY = record.getPosY();
/*     */   }
/*     */ 
/*     */   
/*     */   public DBBitmap() {}
/*     */ 
/*     */   
/*     */   public BufferedImage getImage() {
/*  79 */     return this.image;
/*     */   }
/*     */   
/*     */   public int getPosX() {
/*  83 */     return this.posX;
/*     */   }
/*     */   
/*     */   public int getPosY() {
/*  87 */     return this.posY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearBuffer() {
/*  95 */     hashBuffer.clear();
/*     */   }
/*     */   
/*     */   public static void createTable() throws SQLException {
/*  99 */     String dropTable = "DROP TABLE GDC_BITMAP";
/* 100 */     String createTable = "CREATE TABLE GDC_BITMAP (RECORD_ID        VARCHAR(256) NOT NULL, BITMAP_ID        VARCHAR(256), BITMAP           BLOB(32M), POS_X            INTEGER, POS_Y            INTEGER, PRIMARY KEY (RECORD_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     try (Connection conn = GDDBData.getConnection()) {
/* 109 */       boolean auto = conn.getAutoCommit();
/* 110 */       conn.setAutoCommit(false);
/*     */       
/* 112 */       try (Statement st = conn.createStatement()) {
/* 113 */         if (GDDBUtil.tableExists(conn, "GDC_BITMAP")) {
/* 114 */           st.execute(dropTable);
/*     */         }
/*     */         
/* 117 */         st.execute(createTable);
/* 118 */         st.close();
/*     */         
/* 120 */         conn.commit();
/*     */       }
/* 122 */       catch (SQLException ex) {
/* 123 */         conn.rollback();
/*     */         
/* 125 */         Object[] args = { "GDC_BITMAP" };
/* 126 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 128 */         GDMsgLogger.addError(msg);
/*     */         
/* 130 */         throw ex;
/*     */       } finally {
/*     */         
/* 133 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 139 */     DBBitmap entry = get(record.getFileName());
/*     */     
/* 141 */     if (entry != null)
/*     */       return; 
/* 143 */     DBBitmap bitmap = new DBBitmap(record);
/*     */     
/* 145 */     String insertBitmap = "INSERT INTO GDC_BITMAP VALUES (?,?,?,?,?)";
/*     */     
/* 147 */     try (Connection conn = GDDBData.getConnection()) {
/* 148 */       boolean auto = conn.getAutoCommit();
/* 149 */       conn.setAutoCommit(false);
/*     */       
/* 151 */       try (PreparedStatement ps = conn.prepareStatement(insertBitmap)) {
/* 152 */         ps.setString(1, bitmap.recordID);
/* 153 */         ps.setString(2, bitmap.bitmapID);
/* 154 */         if (bitmap.bitmap == null) {
/* 155 */           ps.setBlob(3, (Blob)null);
/*     */         } else {
/* 157 */           ps.setBlob(3, new ByteArrayInputStream(bitmap.bitmap));
/*     */         } 
/* 159 */         ps.setInt(4, bitmap.posX);
/* 160 */         ps.setInt(5, bitmap.posY);
/*     */         
/* 162 */         ps.executeUpdate();
/* 163 */         ps.close();
/*     */         
/* 165 */         conn.commit();
/*     */       }
/* 167 */       catch (SQLException ex) {
/* 168 */         conn.rollback();
/*     */         
/* 170 */         Object[] args = { record.getFileName(), "GDC_BITMAP" };
/* 171 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */         
/* 173 */         GDMsgLogger.addLowError(msg);
/* 174 */         GDMsgLogger.addLowError(ex);
/*     */       } finally {
/*     */         
/* 177 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 180 */     } catch (SQLException ex) {
/* 181 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBBitmap get(String recordID) {
/* 186 */     DBBitmap bitmap = null;
/*     */     
/* 188 */     bitmap = hashBuffer.get(recordID);
/*     */     
/* 190 */     if (bitmap == null)
/*     */     {
/* 192 */       bitmap = getDB(recordID);
/*     */     }
/*     */     
/* 195 */     return bitmap;
/*     */   }
/*     */   
/*     */   private static DBBitmap getDB(String recordID) {
/* 199 */     DBBitmap bitmap = null;
/*     */     
/* 201 */     String command = "SELECT * FROM GDC_BITMAP WHERE RECORD_ID = ?";
/*     */     
/* 203 */     try(Connection conn = GDDBData.getConnection(); 
/* 204 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 205 */       ps.setString(1, recordID);
/*     */       
/* 207 */       try (ResultSet rs = ps.executeQuery()) {
/* 208 */         List<DBBitmap> list = wrap(rs);
/*     */         
/* 210 */         if (list.isEmpty()) {
/* 211 */           bitmap = null;
/*     */         } else {
/* 213 */           bitmap = list.get(0);
/*     */         } 
/*     */         
/* 216 */         conn.commit();
/*     */       }
/* 218 */       catch (SQLException ex) {
/* 219 */         throw ex;
/*     */       }
/*     */     
/* 222 */     } catch (SQLException ex) {
/* 223 */       Object[] args = { recordID, "GDC_BITMAP" };
/* 224 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 226 */       GDMsgLogger.addError(msg);
/* 227 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 230 */     return bitmap;
/*     */   }
/*     */   
/*     */   private static List<DBBitmap> wrap(ResultSet rs) throws SQLException {
/* 234 */     LinkedList<DBBitmap> list = new LinkedList<>();
/* 235 */     Blob blob = null;
/*     */     
/* 237 */     while (rs.next()) {
/* 238 */       DBBitmap bitmap = new DBBitmap();
/*     */       
/* 240 */       bitmap.recordID = rs.getString(1);
/*     */       
/* 242 */       DBBitmap buff = hashBuffer.get(bitmap.recordID);
/* 243 */       if (buff != null) {
/* 244 */         list.add(buff);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 249 */       bitmap.bitmapID = rs.getString(2);
/*     */       
/* 251 */       blob = rs.getBlob(3);
/* 252 */       if (blob == null) {
/* 253 */         bitmap.bitmap = null;
/*     */       } else {
/* 255 */         bitmap.bitmap = blob.getBytes(1L, (int)blob.length());
/*     */       } 
/*     */       
/* 258 */       if (bitmap.bitmap != null) {
/*     */         try {
/* 260 */           bitmap.image = DDSLoader.getImage(bitmap.bitmap);
/*     */         }
/* 262 */         catch (GDParseException ex) {
/* 263 */           Object[] args = { bitmap.recordID };
/* 264 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_BITMAP_DECODE_FAILED", args);
/*     */           
/* 266 */           GDMsgLogger.addError(msg);
/*     */           
/* 268 */           bitmap.image = null;
/*     */         } 
/*     */       }
/*     */       
/* 272 */       bitmap.posX = rs.getInt(4);
/* 273 */       bitmap.posY = rs.getInt(5);
/*     */       
/* 275 */       list.add(bitmap);
/* 276 */       hashBuffer.put(bitmap.recordID, bitmap);
/*     */     } 
/*     */     
/* 279 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<ImageInfo> getImageInfos(String id) {
/* 287 */     List<ImageInfo> list = new LinkedList<>();
/* 288 */     Blob blob = null;
/*     */     
/* 290 */     String command = "SELECT RECORD_ID, BITMAP_ID, BITMAP FROM GDC_BITMAP WHERE (BITMAP_ID LIKE '" + id + "%' AND BITMAP IS NULL)";
/*     */ 
/*     */     
/* 293 */     try(Connection conn = GDDBData.getConnection(); 
/* 294 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 295 */       try (ResultSet rs = ps.executeQuery()) {
/* 296 */         while (rs.next()) {
/* 297 */           ImageInfo info = new ImageInfo();
/*     */           
/* 299 */           info.recordID = rs.getString(1);
/* 300 */           info.bitmapID = rs.getString(2);
/* 301 */           blob = rs.getBlob(3);
/* 302 */           if (blob == null) {
/* 303 */             info.bitmap = null;
/*     */           } else {
/* 305 */             info.bitmap = blob.getBytes(1L, (int)blob.length());
/*     */           } 
/*     */           
/* 308 */           list.add(info);
/*     */         } 
/*     */         
/* 311 */         conn.commit();
/*     */       }
/* 313 */       catch (Exception ex) {
/* 314 */         throw ex;
/*     */       }
/*     */     
/* 317 */     } catch (Exception ex) {
/* 318 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 321 */     return list;
/*     */   }
/*     */   
/*     */   public static void deleteNoImageBitmaps() {
/* 325 */     String command = "DELETE FROM GDC_BITMAP WHERE BITMAP IS NULL";
/*     */     
/* 327 */     try(Connection conn = GDDBData.getConnection(); 
/* 328 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 329 */       ps.execute();
/*     */       
/* 331 */       conn.commit();
/*     */     }
/* 333 */     catch (Exception ex) {
/* 334 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void updateImageInfo(List<ImageInfo> list) {
/* 339 */     if (list == null)
/*     */       return; 
/* 341 */     String command = "UPDATE GDC_BITMAP SET BITMAP = ? WHERE RECORD_ID = ?";
/*     */     
/* 343 */     try (Connection conn = GDDBData.getConnection()) {
/* 344 */       boolean auto = conn.getAutoCommit();
/* 345 */       conn.setAutoCommit(false);
/*     */       
/* 347 */       for (ImageInfo info : list) {
/* 348 */         try (PreparedStatement ps = conn.prepareStatement(command)) {
/* 349 */           if (info.bitmap != null) {
/* 350 */             ps.setBlob(1, new ByteArrayInputStream(info.bitmap));
/* 351 */             ps.setString(2, info.recordID);
/*     */             
/* 353 */             ps.executeUpdate();
/* 354 */             ps.close();
/*     */             
/* 356 */             conn.commit();
/*     */           }
/*     */         
/* 359 */         } catch (SQLException ex) {
/* 360 */           conn.rollback();
/*     */           
/* 362 */           Object[] args = { info.recordID };
/* 363 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_IN_ITEM_IMAGE_SIZE", args);
/*     */           
/* 365 */           GDMsgLogger.addWarning(msg);
/*     */           
/* 367 */           GDMsgLogger.addWarning(ex);
/*     */         }
/*     */       
/*     */       } 
/* 371 */     } catch (SQLException ex) {
/* 372 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBBitmap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */