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
/*     */ import org.gdstash.file.ARCDecompress;
/*     */ import org.gdstash.file.ARCList;
/*     */ import org.gdstash.file.ARZDecompress;
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
/*     */ public class DBEngineTagText
/*     */ {
/*     */   private static final String TABLE_NAME = "GDC_TAGTEXT";
/*     */   private static final String PREFIX_MASTERY_TAG = "tagSkillClassName";
/*     */   private static final int MAX_MASTERIES = 80;
/*     */   private static final int ROW_TAG = 1;
/*     */   private static final int ROW_TEXT_MS = 2;
/*     */   private static final int ROW_TEXT_FS = 3;
/*     */   private static final int ROW_TEXT_NS = 4;
/*     */   private static final int ROW_TEXT_MP = 5;
/*     */   private static final int ROW_TEXT_FP = 6;
/*     */   private static final int ROW_TEXT_NP = 7;
/*  39 */   private static ConcurrentHashMap<String, DBEngineTagText> hashBuffer = new ConcurrentHashMap<>();
/*  40 */   private static List<DBEngineTagText> texts = new LinkedList<>();
/*     */   
/*     */   private String tag;
/*     */   private String textMS;
/*     */   private String textFS;
/*     */   private String textNS;
/*     */   private String textMP;
/*     */   private String textFP;
/*     */   private String textNP;
/*     */   
/*     */   public DBEngineTagText() {
/*  51 */     this.tag = null;
/*  52 */     this.textMS = null;
/*  53 */     this.textFS = null;
/*  54 */     this.textNS = null;
/*  55 */     this.textMP = null;
/*  56 */     this.textFP = null;
/*  57 */     this.textNP = null;
/*     */   }
/*     */   
/*     */   public DBEngineTagText(String tag, String text) {
/*  61 */     this.tag = tag;
/*     */     
/*  63 */     String[] genders = ARZDecompress.getGenderTexts(text);
/*     */     
/*  65 */     this.textMS = genders[0];
/*  66 */     this.textFS = genders[1];
/*  67 */     this.textNS = genders[2];
/*  68 */     this.textMP = genders[3];
/*  69 */     this.textFP = genders[4];
/*  70 */     this.textNP = genders[5];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/*  78 */     return this.tag;
/*     */   }
/*     */   
/*     */   public String getText(int genderID) {
/*  82 */     String s = null;
/*     */     
/*  84 */     switch (genderID)
/*     */     { case 0:
/*  86 */         s = this.textMS;
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
/*     */         
/* 114 */         return s;case 1: s = this.textFS; return s;case 2: s = this.textNS; return s;case 3: s = this.textMP; return s;case 4: s = this.textFP; return s;case 5: s = this.textNP; return s; }  s = ""; return s;
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
/*     */   public static void clearBuffer() {
/* 126 */     hashBuffer.clear();
/*     */   }
/*     */   
/*     */   public static void createTable() throws SQLException {
/* 130 */     String dropTable = "DROP TABLE GDC_TAGTEXT";
/* 131 */     String createTable = "CREATE TABLE GDC_TAGTEXT (TAG     VARCHAR(64), TEXT_MS VARCHAR(256), TEXT_FS VARCHAR(256), TEXT_NS VARCHAR(256), TEXT_MP VARCHAR(256), TEXT_FP VARCHAR(256), TEXT_NP VARCHAR(256), PRIMARY KEY (TAG))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     try (Connection conn = GDDBData.getConnection()) {
/* 142 */       boolean auto = conn.getAutoCommit();
/* 143 */       conn.setAutoCommit(false);
/*     */       
/* 145 */       try (Statement st = conn.createStatement()) {
/* 146 */         if (GDDBUtil.tableExists(conn, "GDC_TAGTEXT")) {
/* 147 */           st.execute(dropTable);
/*     */         }
/* 149 */         st.execute(createTable);
/* 150 */         st.close();
/*     */         
/* 152 */         conn.commit();
/*     */       
/*     */       }
/* 155 */       catch (SQLException ex) {
/* 156 */         conn.rollback();
/*     */         
/* 158 */         Object[] args = { "GDC_TAGTEXT" };
/* 159 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 161 */         GDMsgLogger.addError(msg);
/*     */         
/* 163 */         throw ex;
/*     */       } finally {
/*     */         
/* 166 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(String tag) throws SQLException {
/* 172 */     String deleteEntry = "DELETE FROM GDC_TAGTEXT WHERE TAG = ?";
/*     */     
/* 174 */     try (Connection conn = GDDBData.getConnection()) {
/* 175 */       boolean auto = conn.getAutoCommit();
/* 176 */       conn.setAutoCommit(false);
/*     */       
/* 178 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 179 */         ps.setString(1, tag);
/* 180 */         ps.executeUpdate();
/* 181 */         ps.close();
/*     */         
/* 183 */         conn.commit();
/*     */       }
/* 185 */       catch (SQLException ex) {
/* 186 */         conn.rollback();
/*     */       } finally {
/*     */         
/* 189 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 192 */     } catch (SQLException sQLException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public void insert() throws SQLException {
/* 197 */     String insert = "INSERT INTO GDC_TAGTEXT VALUES (?,?,?,?,?,?,?)";
/*     */     
/* 199 */     try (Connection conn = GDDBData.getConnection()) {
/* 200 */       boolean auto = conn.getAutoCommit();
/* 201 */       conn.setAutoCommit(false);
/*     */       
/* 203 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 204 */         ps.setString(1, this.tag);
/* 205 */         ps.setString(2, this.textMS);
/* 206 */         ps.setString(3, this.textFS);
/* 207 */         ps.setString(4, this.textNS);
/* 208 */         ps.setString(5, this.textMP);
/* 209 */         ps.setString(6, this.textFP);
/* 210 */         ps.setString(7, this.textNP);
/*     */         
/* 212 */         ps.executeUpdate();
/* 213 */         ps.close();
/*     */         
/* 215 */         conn.commit();
/*     */       }
/* 217 */       catch (SQLException ex) {
/* 218 */         conn.rollback();
/*     */         
/* 220 */         GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_IN_PLAYER_CONFIG"));
/* 221 */         GDMsgLogger.addError(ex);
/*     */       } finally {
/*     */         
/* 224 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void insertMod() throws SQLException {
/* 230 */     DBEngineTagText text = get(this.tag);
/*     */     
/* 232 */     if (text != null) {
/* 233 */       delete(this.tag);
/*     */     }
/*     */     
/* 236 */     insert();
/*     */   }
/*     */   
/*     */   public static DBEngineTagText get(String tag) {
/* 240 */     DBEngineTagText tt = null;
/*     */     
/* 242 */     tt = hashBuffer.get(tag);
/*     */     
/* 244 */     if (tt == null)
/*     */     {
/* 246 */       tt = getDB(tag);
/*     */     }
/*     */     
/* 249 */     return tt;
/*     */   }
/*     */   
/*     */   private static DBEngineTagText getDB(String tag) {
/* 253 */     DBEngineTagText tt = new DBEngineTagText();
/*     */     
/* 255 */     String command = "SELECT * FROM GDC_TAGTEXT WHERE TAG = ? ";
/*     */     
/* 257 */     try(Connection conn = GDDBData.getConnection(); 
/* 258 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 259 */       ps.setString(1, tag);
/*     */       
/* 261 */       try (ResultSet rs = ps.executeQuery()) {
/* 262 */         List<DBEngineTagText> list = wrap(rs);
/*     */         
/* 264 */         if (list.isEmpty()) {
/* 265 */           tt = null;
/*     */         } else {
/* 267 */           tt = list.get(0);
/*     */         } 
/*     */         
/* 270 */         conn.commit();
/*     */       }
/* 272 */       catch (SQLException ex) {
/* 273 */         throw ex;
/*     */       }
/*     */     
/* 276 */     } catch (SQLException ex) {
/* 277 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_READ_CONFIG_PLAYER"));
/* 278 */       GDMsgLogger.addError(ex);
/*     */       
/* 280 */       tt = null;
/*     */     } 
/*     */     
/* 283 */     return tt;
/*     */   }
/*     */   
/*     */   private static List<DBEngineTagText> wrap(ResultSet rs) throws SQLException {
/* 287 */     LinkedList<DBEngineTagText> list = new LinkedList<>();
/*     */     
/* 289 */     while (rs.next()) {
/* 290 */       DBEngineTagText tt = new DBEngineTagText();
/*     */       
/* 292 */       tt.tag = rs.getString(1);
/*     */       
/* 294 */       DBEngineTagText buff = hashBuffer.get(tt.tag);
/* 295 */       if (buff != null) {
/* 296 */         list.add(buff);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 301 */       tt.textMS = rs.getString(2);
/* 302 */       tt.textFS = rs.getString(3);
/* 303 */       tt.textNS = rs.getString(4);
/* 304 */       tt.textMP = rs.getString(5);
/* 305 */       tt.textFP = rs.getString(6);
/* 306 */       tt.textNP = rs.getString(7);
/*     */       
/* 308 */       list.add(tt);
/* 309 */       hashBuffer.put(tt.tag, tt);
/*     */     } 
/*     */     
/* 312 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void collectTags(ARCList list) {
/* 320 */     DBEngineTagText tt = null;
/*     */     
/* 322 */     for (int i = 1; i <= 80; i++) {
/* 323 */       String num1 = String.format("%02d", new Object[] { Integer.valueOf(i) });
/*     */       
/* 325 */       String tag1 = "tagSkillClassName" + num1;
/* 326 */       String text = list.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_SKILLS, tag1, false);
/*     */       
/* 328 */       if (text != null && !text.isEmpty()) {
/* 329 */         tt = new DBEngineTagText(tag1, text);
/* 330 */         texts.add(tt);
/*     */         int j;
/* 332 */         for (j = 2; j <= 80; j++) {
/* 333 */           String num2 = String.format("%02d", new Object[] { Integer.valueOf(j) });
/*     */           
/* 335 */           String tag2 = tag1 + num2;
/* 336 */           text = list.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_SKILLS, tag2, false);
/*     */           
/* 338 */           if (text != null && !text.isEmpty()) {
/* 339 */             tt = new DBEngineTagText(tag2, text);
/* 340 */             texts.add(tt);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean storeTags() {
/* 348 */     boolean success = true;
/*     */     
/* 350 */     for (DBEngineTagText tt : texts) {
/*     */       try {
/* 352 */         tt.insertMod();
/*     */       }
/* 354 */       catch (SQLException ex) {
/* 355 */         success = false;
/*     */       } 
/*     */     } 
/*     */     
/* 359 */     texts.clear();
/*     */     
/* 361 */     return success;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBEngineTagText.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */