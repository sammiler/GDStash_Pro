/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
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
/*     */ public class DBSkillButton
/*     */ {
/*     */   private static final String TABLE_NAME = "GDC_BUTTON_SKILL";
/*     */   private static final int ROW_BUTTON_ID = 1;
/*     */   private static final int ROW_SKILL_ID = 2;
/*     */   private static final int ROW_POS_X = 3;
/*     */   private static final int ROW_POS_Y = 4;
/*     */   private static final int ROW_OFFSET_X = 5;
/*     */   private static final int ROW_OFFSET_Y = 6;
/*     */   private static final int ROW_CIRCULAR = 7;
/*  45 */   private String buttonID = null;
/*  46 */   private String skillID = null;
/*  47 */   private int posX = 0;
/*  48 */   private int posY = 0;
/*  49 */   private int offsetX = 0;
/*  50 */   private int offsetY = 0;
/*     */   
/*     */   private boolean circularButton = false;
/*  53 */   private DBSkill dbSkill = null;
/*     */ 
/*     */   
/*     */   public DBSkillButton(ARZRecord record) {
/*  57 */     this();
/*     */     
/*  59 */     this.buttonID = record.getFileName();
/*  60 */     this.skillID = record.getButtonSkillID();
/*  61 */     this.posX = record.getPosX();
/*  62 */     this.posY = record.getPosY();
/*  63 */     this.offsetX = record.getOffsetX();
/*  64 */     this.offsetY = record.getOffsetY();
/*  65 */     this.circularButton = record.getCircularButton();
/*     */   }
/*     */ 
/*     */   
/*     */   public DBSkillButton() {}
/*     */ 
/*     */   
/*     */   public String getButtonID() {
/*  73 */     return this.buttonID;
/*     */   }
/*     */   
/*     */   public String getSkillID() {
/*  77 */     return this.skillID;
/*     */   }
/*     */   
/*     */   public DBSkill getSkill() {
/*  81 */     return this.dbSkill;
/*     */   }
/*     */   
/*     */   public int getPosX() {
/*  85 */     return this.posX;
/*     */   }
/*     */   
/*     */   public int getPosY() {
/*  89 */     return this.posY;
/*     */   }
/*     */   
/*     */   public int getOffsetX() {
/*  93 */     return this.offsetX;
/*     */   }
/*     */   
/*     */   public int getOffsetY() {
/*  97 */     return this.offsetY;
/*     */   }
/*     */   
/*     */   public boolean isCircularButton() {
/* 101 */     return this.circularButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable() throws SQLException {
/* 109 */     String dropTable = "DROP TABLE GDC_BUTTON_SKILL";
/* 110 */     String createTable = "CREATE TABLE GDC_BUTTON_SKILL (BUTTON_ID        VARCHAR(256) NOT NULL, SKILL_ID         VARCHAR(256), POS_X            INTEGER, POS_Y            INTEGER, OFFSET_X         INTEGER, OFFSET_Y         INTEGER, CIRCULAR         BOOLEAN, PRIMARY KEY (BUTTON_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     try (Connection conn = GDDBData.getConnection()) {
/* 121 */       boolean auto = conn.getAutoCommit();
/* 122 */       conn.setAutoCommit(false);
/*     */       
/* 124 */       try (Statement st = conn.createStatement()) {
/* 125 */         if (GDDBUtil.tableExists(conn, "GDC_BUTTON_SKILL")) {
/* 126 */           st.execute(dropTable);
/*     */         }
/* 128 */         st.execute(createTable);
/* 129 */         st.close();
/*     */         
/* 131 */         conn.commit();
/*     */       }
/* 133 */       catch (SQLException ex) {
/* 134 */         conn.rollback();
/*     */         
/* 136 */         throw ex;
/*     */       } finally {
/*     */         
/* 139 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(String buttonID) throws SQLException {
/* 145 */     String deleteEntry = "DELETE FROM GDC_BUTTON_SKILL WHERE BUTTON_ID = ?";
/*     */     
/* 147 */     try (Connection conn = GDDBData.getConnection()) {
/* 148 */       boolean auto = conn.getAutoCommit();
/* 149 */       conn.setAutoCommit(false);
/*     */       
/* 151 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 152 */         ps.setString(1, buttonID);
/* 153 */         ps.executeUpdate();
/* 154 */         ps.close();
/*     */         
/* 156 */         DBConstellationAffinity.delete(conn, buttonID);
/* 157 */         DBConstellationStar.delete(conn, buttonID);
/*     */         
/* 159 */         conn.commit();
/*     */       }
/* 161 */       catch (SQLException ex) {
/* 162 */         conn.rollback();
/*     */         
/* 164 */         Object[] args = { buttonID, "GDC_BUTTON_SKILL" };
/* 165 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*     */         
/* 167 */         GDMsgLogger.addError(msg);
/* 168 */         GDMsgLogger.addError(ex);
/*     */         
/* 170 */         throw ex;
/*     */       } finally {
/*     */         
/* 173 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 176 */     } catch (SQLException ex) {
/* 177 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 182 */     DBSkillButton entry = getByButtonID(record.getFileName());
/*     */     
/* 184 */     if (entry != null)
/*     */       return; 
/* 186 */     DBSkillButton button = new DBSkillButton(record);
/*     */     
/* 188 */     String insert = "INSERT INTO GDC_BUTTON_SKILL VALUES (?,?,?,?,?,?,?)";
/*     */     
/* 190 */     try (Connection conn = GDDBData.getConnection()) {
/* 191 */       boolean auto = conn.getAutoCommit();
/* 192 */       conn.setAutoCommit(false);
/*     */       
/* 194 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/*     */         
/* 196 */         ps.setString(1, button.buttonID);
/* 197 */         ps.setString(2, button.skillID);
/* 198 */         ps.setInt(3, button.posX);
/* 199 */         ps.setInt(4, button.posY);
/* 200 */         ps.setInt(5, button.offsetX);
/* 201 */         ps.setInt(6, button.offsetY);
/* 202 */         ps.setBoolean(7, button.circularButton);
/*     */         
/* 204 */         ps.executeUpdate();
/*     */         
/* 206 */         ps.close();
/* 207 */         conn.commit();
/*     */       }
/* 209 */       catch (SQLException ex) {
/* 210 */         conn.rollback();
/*     */         
/* 212 */         Object[] args = { button.buttonID, "GDC_BUTTON_SKILL" };
/* 213 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */         
/* 215 */         GDMsgLogger.addLowError(msg);
/* 216 */         GDMsgLogger.addLowError(ex);
/*     */       } finally {
/*     */         
/* 219 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBSkillButton getByButtonID(String buttonID) {
/* 225 */     DBSkillButton button = null;
/*     */     
/* 227 */     String command = "SELECT * FROM GDC_BUTTON_SKILL WHERE BUTTON_ID = ?";
/*     */     
/* 229 */     try(Connection conn = GDDBData.getConnection(); 
/* 230 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 231 */       ps.setString(1, buttonID);
/*     */       
/* 233 */       try (ResultSet rs = ps.executeQuery()) {
/* 234 */         List<DBSkillButton> list = wrap(rs);
/*     */         
/* 236 */         if (list.isEmpty()) { button = null; }
/* 237 */         else { button = list.get(0); }
/*     */         
/* 239 */         conn.commit();
/*     */       }
/* 241 */       catch (SQLException ex) {
/* 242 */         throw ex;
/*     */       }
/*     */     
/* 245 */     } catch (SQLException ex) {
/* 246 */       Object[] args = { buttonID, "GDC_BUTTON_SKILL" };
/* 247 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 249 */       GDMsgLogger.addError(msg);
/* 250 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 253 */     return button;
/*     */   }
/*     */   
/*     */   public static DBSkillButton getBySkillID(String skillID) {
/* 257 */     DBSkillButton button = null;
/*     */     
/* 259 */     String command = "SELECT * FROM GDC_BUTTON_SKILL, GDC_CLASSTABLE WHERE SKILL_ID = ? AND GDC_BUTTON_SKILL.BUTTON_ID = GDC_CLASSTABLE.MASTERY_ID";
/*     */ 
/*     */ 
/*     */     
/* 263 */     try(Connection conn = GDDBData.getConnection(); 
/* 264 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 265 */       ps.setString(1, skillID);
/*     */       
/* 267 */       try (ResultSet rs = ps.executeQuery()) {
/* 268 */         List<DBSkillButton> list = wrap(rs);
/*     */         
/* 270 */         if (list.isEmpty()) {
/* 271 */           button = null;
/*     */         } else {
/* 273 */           button = list.get(0);
/*     */         } 
/*     */         
/* 276 */         conn.commit();
/*     */       }
/* 278 */       catch (SQLException ex) {
/* 279 */         throw ex;
/*     */       }
/*     */     
/* 282 */     } catch (SQLException ex) {
/* 283 */       Object[] args = { skillID, "GDC_BUTTON_SKILL" };
/* 284 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 286 */       GDMsgLogger.addError(msg);
/* 287 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 290 */     return button;
/*     */   }
/*     */   
/*     */   private static List<DBSkillButton> wrap(ResultSet rs) throws SQLException {
/* 294 */     LinkedList<DBSkillButton> list = new LinkedList<>();
/*     */     
/* 296 */     while (rs.next()) {
/* 297 */       DBSkillButton button = new DBSkillButton();
/*     */       
/* 299 */       button.buttonID = rs.getString(1);
/* 300 */       button.skillID = rs.getString(2);
/* 301 */       button.posX = rs.getInt(3);
/* 302 */       button.posY = rs.getInt(4);
/* 303 */       button.offsetX = rs.getInt(5);
/* 304 */       button.offsetY = rs.getInt(6);
/* 305 */       button.circularButton = rs.getBoolean(7);
/*     */       
/* 307 */       if (button.skillID != null) {
/* 308 */         button.dbSkill = DBSkill.get(button.skillID);
/*     */       }
/*     */       
/* 311 */       list.add(button);
/*     */     } 
/*     */     
/* 314 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBSkillButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */