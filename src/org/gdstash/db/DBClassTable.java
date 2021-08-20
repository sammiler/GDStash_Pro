/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.gdstash.file.ARCDecompress;
/*     */ import org.gdstash.file.ARZDecompress;
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
/*     */ public class DBClassTable
/*     */ {
/*     */   public static final String TABLE_NAME = "GDC_CLASSTABLE";
/*     */   private static final int ROW_CLASSTABLE_ID = 1;
/*     */   private static final int ROW_MASTERY_BAR_ID = 2;
/*     */   private static final int ROW_SKILL_PANE_ID = 3;
/*     */   private static final int ROW_MASTERY_BITMAP_ID = 4;
/*     */   private static final int ROW_MASTERY_ID = 5;
/*     */   private static final int ROW_TITLE_TAG = 6;
/*     */   private static final int ROW_TITLE_TEXT_MALE = 7;
/*     */   private static final int ROW_TITLE_TEXT_FEMALE = 8;
/*  41 */   private static ConcurrentHashMap<String, DBClassTable> hashBuffer = new ConcurrentHashMap<>();
/*  42 */   private static ConcurrentHashMap<String, String> hashID = new ConcurrentHashMap<>();
/*     */   
/*     */   private String classTableID;
/*     */   
/*     */   private String masteryBarID;
/*     */   
/*     */   private String skillPaneID;
/*     */   
/*     */   private String masteryBitmapID;
/*     */   
/*     */   private String masteryID;
/*     */   
/*     */   private String titleTag;
/*     */   private String titleMale;
/*     */   private String titleFemale;
/*     */   private DBBitmap dbMasteryBar;
/*     */   private DBBitmap dbSillPane;
/*     */   private DBBitmap dbMasteryBitmap;
/*  60 */   private List<DBClassTableButton> buttons = new LinkedList<>();
/*     */ 
/*     */   
/*     */   public DBClassTable(ARZRecord record) {
/*  64 */     this();
/*     */     
/*  66 */     this.classTableID = record.getFileName();
/*  67 */     this.masteryBarID = record.getMasteryBarID();
/*  68 */     this.skillPaneID = record.getSkillPaneID();
/*  69 */     this.masteryBitmapID = record.getMasteryBitmapID();
/*  70 */     setTitleTag(record.getTitleTag());
/*     */     
/*  72 */     String id1 = null;
/*  73 */     String id2 = null;
/*     */     
/*  75 */     int i = 1;
/*  76 */     for (String s : record.getSkillButtonList()) {
/*  77 */       DBClassTableButton button = new DBClassTableButton(this.classTableID, s, i);
/*     */       
/*  79 */       this.buttons.add(button);
/*     */ 
/*     */       
/*  82 */       if (i == 1) id1 = s; 
/*  83 */       if (s.contains("classtraining")) id2 = s;
/*     */       
/*  85 */       if (id2 != null) {
/*  86 */         this.masteryID = id2;
/*     */       } else {
/*  88 */         this.masteryID = id1;
/*     */       } 
/*     */       
/*  91 */       i++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DBClassTable() {}
/*     */ 
/*     */   
/*     */   public BufferedImage getMasteryBar() {
/* 100 */     if (this.dbMasteryBar == null) return null;
/*     */     
/* 102 */     return this.dbMasteryBar.getImage();
/*     */   }
/*     */   
/*     */   public DBBitmap getMasteryBarDB() {
/* 106 */     return this.dbMasteryBar;
/*     */   }
/*     */   
/*     */   public BufferedImage getSkillPane() {
/* 110 */     if (this.dbSillPane == null) return null;
/*     */     
/* 112 */     return this.dbSillPane.getImage();
/*     */   }
/*     */   
/*     */   public DBBitmap getSkillPaneDB() {
/* 116 */     return this.dbSillPane;
/*     */   }
/*     */   
/*     */   public BufferedImage getMasteryBitmap() {
/* 120 */     if (this.dbMasteryBitmap == null) return null;
/*     */     
/* 122 */     return this.dbMasteryBitmap.getImage();
/*     */   }
/*     */   
/*     */   public DBBitmap getMasteryBitmapDB() {
/* 126 */     return this.dbMasteryBitmap;
/*     */   }
/*     */   
/*     */   public String getMasteryID() {
/* 130 */     return this.masteryID;
/*     */   }
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
/*     */   public List<DBClassTableButton> getButtonList() {
/* 145 */     return this.buttons;
/*     */   }
/*     */   
/*     */   public String getMasteryTitle(int genderCode) {
/* 149 */     String title = this.titleMale;
/*     */     
/* 151 */     if (genderCode == 1) {
/* 152 */       title = this.titleFemale;
/*     */     }
/*     */     
/* 155 */     return title;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setTitleTag(String titleTag) {
/* 163 */     this.titleTag = titleTag;
/*     */     
/* 165 */     if (titleTag == null) {
/* 166 */       this.titleMale = null;
/* 167 */       this.titleFemale = null;
/*     */     } else {
/* 169 */       this.titleMale = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_SKILLS, titleTag, false);
/*     */       
/* 171 */       if (this.titleMale != null) {
/* 172 */         String[] genders = ARZDecompress.getGenderTexts(this.titleMale);
/*     */         
/* 174 */         this.titleMale = genders[0];
/* 175 */         this.titleFemale = genders[1];
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearBuffer() {
/* 185 */     hashBuffer.clear();
/* 186 */     hashID.clear();
/*     */   }
/*     */   
/*     */   public static void createTables() throws SQLException {
/* 190 */     String dropTable = "DROP TABLE GDC_CLASSTABLE";
/* 191 */     String createTable = "CREATE TABLE GDC_CLASSTABLE (CLASSTABLE_ID      VARCHAR(256) NOT NULL, MASTERY_BAR_ID     VARCHAR(256), SKILLPANE_ID       VARCHAR(256), MASTERY_BITMAP_ID  VARCHAR(256), MASTERY_ID         VARCHAR(256), TITLE_TAG          VARCHAR(64), TITLE_MALE         VARCHAR(64), TITLE_FEMALE       VARCHAR(64), PRIMARY KEY (CLASSTABLE_ID))";
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
/* 202 */     try (Connection conn = GDDBData.getConnection()) {
/* 203 */       boolean auto = conn.getAutoCommit();
/* 204 */       conn.setAutoCommit(false);
/*     */       
/* 206 */       try (Statement st = conn.createStatement()) {
/* 207 */         if (GDDBUtil.tableExists(conn, "GDC_CLASSTABLE")) {
/* 208 */           st.execute(dropTable);
/*     */         }
/*     */         
/* 211 */         st.execute(createTable);
/* 212 */         st.close();
/*     */         
/* 214 */         conn.commit();
/*     */         
/* 216 */         DBClassTableButton.createTable(conn);
/*     */       }
/* 218 */       catch (SQLException ex) {
/* 219 */         conn.rollback();
/*     */         
/* 221 */         Object[] args = { "GDC_CLASSTABLE" };
/* 222 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 224 */         GDMsgLogger.addError(msg);
/*     */         
/* 226 */         throw ex;
/*     */       } finally {
/*     */         
/* 229 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 235 */     DBClassTable entry = get(record.getFileName());
/*     */     
/* 237 */     if (entry != null)
/*     */       return; 
/* 239 */     DBClassTable ct = new DBClassTable(record);
/*     */     
/* 241 */     String insertItem = "INSERT INTO GDC_CLASSTABLE VALUES (?,?,?,?,?,?,?,?)";
/*     */     
/* 243 */     try (Connection conn = GDDBData.getConnection()) {
/* 244 */       boolean auto = conn.getAutoCommit();
/* 245 */       conn.setAutoCommit(false);
/*     */       
/* 247 */       try (PreparedStatement ps = conn.prepareStatement(insertItem)) {
/* 248 */         ps.setString(1, ct.classTableID);
/* 249 */         ps.setString(2, ct.masteryBarID);
/* 250 */         ps.setString(3, ct.skillPaneID);
/* 251 */         ps.setString(4, ct.masteryBitmapID);
/* 252 */         ps.setString(5, ct.masteryID);
/* 253 */         ps.setString(6, ct.titleTag);
/* 254 */         ps.setString(7, ct.titleMale);
/* 255 */         ps.setString(8, ct.titleFemale);
/*     */         
/* 257 */         ps.executeUpdate();
/* 258 */         ps.close();
/*     */         
/* 260 */         DBClassTableButton.insertButtons(conn, ct.classTableID, ct.buttons);
/*     */         
/* 262 */         conn.commit();
/*     */       }
/* 264 */       catch (SQLException ex) {
/* 265 */         conn.rollback();
/*     */         
/* 267 */         Object[] args = { record.getFileName(), "GDC_CLASSTABLE" };
/* 268 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */         
/* 270 */         GDMsgLogger.addLowError(msg);
/* 271 */         GDMsgLogger.addLowError(ex);
/*     */       } finally {
/*     */         
/* 274 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 277 */     } catch (SQLException ex) {
/* 278 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBClassTable getByMasteryID(String masteryID) {
/* 283 */     String classTableID = hashID.get(masteryID);
/*     */     
/* 285 */     if (classTableID != null) {
/* 286 */       return get(classTableID);
/*     */     }
/*     */     
/* 289 */     String command = "SELECT GDC_CLASSTABLE.CLASSTABLE_ID FROM GDC_CLASSTABLE, GDC_SKILL_MASTER WHERE GDC_CLASSTABLE.MASTERY_ID = ? AND GDC_CLASSTABLE.CLASSTABLE_ID = GDC_SKILL_MASTER.CLASSTABLE_ID";
/*     */ 
/*     */ 
/*     */     
/* 293 */     try(Connection conn = GDDBData.getConnection(); 
/* 294 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 295 */       ps.setString(1, masteryID);
/*     */       
/* 297 */       try (ResultSet rs = ps.executeQuery()) {
/* 298 */         if (rs.next()) {
/* 299 */           classTableID = rs.getString(1);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 304 */         conn.commit();
/*     */       }
/* 306 */       catch (SQLException ex) {
/* 307 */         throw ex;
/*     */       }
/*     */     
/* 310 */     } catch (SQLException ex) {
/* 311 */       Object[] args = { masteryID, "GDC_CLASSTABLE" };
/* 312 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 314 */       GDMsgLogger.addError(msg);
/* 315 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 318 */     if (classTableID == null) return null;
/*     */     
/* 320 */     hashID.put(masteryID, classTableID);
/*     */     
/* 322 */     return get(classTableID);
/*     */   }
/*     */   
/*     */   public static DBClassTable get(String classTableID) {
/* 326 */     DBClassTable ct = null;
/*     */     
/* 328 */     ct = hashBuffer.get(classTableID);
/*     */     
/* 330 */     if (ct == null)
/*     */     {
/* 332 */       ct = getDB(classTableID);
/*     */     }
/*     */     
/* 335 */     return ct;
/*     */   }
/*     */   
/*     */   public static List<DBClassTable> getAll() {
/* 339 */     String command = "SELECT CLASSTABLE_ID FROM GDC_CLASSTABLE";
/*     */     
/* 341 */     List<DBClassTable> cTabs = new LinkedList<>();
/* 342 */     List<String> ctIDs = new LinkedList<>();
/* 343 */     try(Connection conn = GDDBData.getConnection(); 
/* 344 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 345 */       try (ResultSet rs = ps.executeQuery()) {
/* 346 */         while (rs.next()) {
/* 347 */           String s = rs.getString(1);
/* 348 */           ctIDs.add(s);
/*     */         }
/*     */       
/* 351 */       } catch (SQLException ex) {
/* 352 */         throw ex;
/*     */       } 
/*     */       
/* 355 */       conn.commit();
/*     */       
/* 357 */       for (String ctID : ctIDs) {
/* 358 */         DBClassTable ct = get(ctID);
/* 359 */         cTabs.add(ct);
/*     */       }
/*     */     
/* 362 */     } catch (SQLException ex) {
/* 363 */       Object[] args = { "-", "GDC_CLASSTABLE" };
/* 364 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 366 */       GDMsgLogger.addError(msg);
/* 367 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 370 */     return cTabs;
/*     */   }
/*     */   
/*     */   private static DBClassTable getDB(String classTableID) {
/* 374 */     DBClassTable ct = null;
/*     */     
/* 376 */     String command = "SELECT * FROM GDC_CLASSTABLE WHERE CLASSTABLE_ID = ?";
/*     */     
/* 378 */     try(Connection conn = GDDBData.getConnection(); 
/* 379 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 380 */       ps.setString(1, classTableID);
/*     */       
/* 382 */       try (ResultSet rs = ps.executeQuery()) {
/* 383 */         List<DBClassTable> list = wrap(rs);
/*     */         
/* 385 */         if (list.isEmpty()) {
/* 386 */           ct = null;
/*     */         } else {
/* 388 */           ct = list.get(0);
/*     */         } 
/*     */         
/* 391 */         conn.commit();
/*     */       }
/* 393 */       catch (SQLException ex) {
/* 394 */         throw ex;
/*     */       }
/*     */     
/* 397 */     } catch (SQLException ex) {
/* 398 */       Object[] args = { classTableID, "GDC_CLASSTABLE" };
/* 399 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 401 */       GDMsgLogger.addError(msg);
/* 402 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 405 */     return ct;
/*     */   }
/*     */   
/*     */   private static List<DBClassTable> wrap(ResultSet rs) throws SQLException {
/* 409 */     LinkedList<DBClassTable> list = new LinkedList<>();
/* 410 */     Blob blob = null;
/*     */     
/* 412 */     while (rs.next()) {
/* 413 */       DBClassTable ct = new DBClassTable();
/*     */       
/* 415 */       ct.classTableID = rs.getString(1);
/*     */       
/* 417 */       DBClassTable buff = hashBuffer.get(ct.classTableID);
/* 418 */       if (buff != null) {
/* 419 */         list.add(buff);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 424 */       ct.masteryBarID = rs.getString(2);
/* 425 */       ct.skillPaneID = rs.getString(3);
/* 426 */       ct.masteryBitmapID = rs.getString(4);
/* 427 */       ct.masteryID = rs.getString(5);
/* 428 */       ct.titleTag = rs.getString(6);
/* 429 */       ct.titleMale = rs.getString(7);
/* 430 */       ct.titleFemale = rs.getString(8);
/*     */       
/* 432 */       ct.buttons = DBClassTableButton.getButtons(ct.classTableID);
/*     */       
/* 434 */       if (ct.masteryBarID != null) ct.dbMasteryBar = DBBitmap.get(ct.masteryBarID); 
/* 435 */       if (ct.skillPaneID != null) ct.dbSillPane = DBBitmap.get(ct.skillPaneID); 
/* 436 */       if (ct.masteryBitmapID != null) ct.dbMasteryBitmap = DBBitmap.get(ct.masteryBitmapID);
/*     */       
/* 438 */       list.add(ct);
/* 439 */       hashBuffer.put(ct.classTableID, ct);
/*     */     } 
/*     */     
/* 442 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBClassTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */