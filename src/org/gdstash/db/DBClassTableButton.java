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
/*     */ public class DBClassTableButton
/*     */ {
/*     */   private static final String TABLE_NAME = "GDC_CLASSTABLE_BUTTON";
/*     */   private static final int ROW_CLASSTABLE_ID = 1;
/*     */   private static final int ROW_BUTTON_ID = 2;
/*     */   private static final int ROW_INDEX = 3;
/*     */   private String classTableID;
/*     */   private String buttonID;
/*     */   private int index;
/*     */   private DBSkillButton dbButton;
/*     */   private DBSkill dbSkill;
/*     */   
/*     */   public DBClassTableButton() {
/*  37 */     this.classTableID = null;
/*  38 */     this.buttonID = null;
/*  39 */     this.index = -1;
/*     */   }
/*     */   
/*     */   public DBClassTableButton(String classTableID, String buttonID, int index) {
/*  43 */     this.classTableID = classTableID;
/*  44 */     this.buttonID = buttonID;
/*  45 */     this.index = index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getButtonID() {
/*  53 */     return this.buttonID;
/*     */   }
/*     */   
/*     */   public int getIndex() {
/*  57 */     return this.index;
/*     */   }
/*     */   
/*     */   public DBSkillButton getButton() {
/*  61 */     return this.dbButton;
/*     */   }
/*     */   
/*     */   public DBSkill getSkill() {
/*  65 */     return this.dbSkill;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable(Connection conn) throws SQLException {
/*  73 */     String dropTable = "DROP TABLE GDC_CLASSTABLE_BUTTON";
/*  74 */     String createTable = "CREATE TABLE GDC_CLASSTABLE_BUTTON (CLASSTABLE_ID      VARCHAR(256) NOT NULL, BUTTON_ID          VARCHAR(256), INDEX              INTEGER, PRIMARY KEY (CLASSTABLE_ID, BUTTON_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     try (Statement st = conn.createStatement()) {
/*  81 */       if (GDDBUtil.tableExists(conn, "GDC_CLASSTABLE_BUTTON")) {
/*  82 */         st.execute(dropTable);
/*     */       }
/*  84 */       st.execute(createTable);
/*  85 */       st.close();
/*     */       
/*  87 */       conn.commit();
/*     */     }
/*  89 */     catch (SQLException ex) {
/*  90 */       conn.rollback();
/*     */       
/*  92 */       Object[] args = { "GDC_CLASSTABLE_BUTTON" };
/*  93 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */       
/*  95 */       GDMsgLogger.addError(msg);
/*     */       
/*  97 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insertButtons(Connection conn, String id, List<DBClassTableButton> buttons) throws SQLException {
/* 102 */     String insert = "INSERT INTO GDC_CLASSTABLE_BUTTON VALUES (?,?,?)";
/*     */     
/* 104 */     if (buttons == null)
/* 105 */       return;  if (buttons.isEmpty())
/*     */       return; 
/* 107 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 108 */       for (DBClassTableButton button : buttons) {
/* 109 */         ps.setString(1, id);
/* 110 */         ps.setString(2, button.buttonID);
/* 111 */         ps.setInt(3, button.index);
/*     */         
/* 113 */         ps.executeUpdate();
/*     */         
/* 115 */         ps.clearParameters();
/*     */       } 
/*     */       
/* 118 */       ps.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBClassTableButton> getButtons(String id) throws SQLException {
/* 123 */     List<DBClassTableButton> list = new LinkedList<>();
/*     */     
/* 125 */     String command = "SELECT * FROM GDC_CLASSTABLE_BUTTON WHERE CLASSTABLE_ID = ?";
/*     */     
/* 127 */     try(Connection conn = GDDBData.getConnection(); 
/* 128 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 129 */       ps.setString(1, id);
/*     */       
/* 131 */       try (ResultSet rs = ps.executeQuery()) {
/* 132 */         list = wrap(rs);
/*     */         
/* 134 */         conn.commit();
/*     */       }
/* 136 */       catch (SQLException ex) {
/* 137 */         throw ex;
/*     */       }
/*     */     
/* 140 */     } catch (SQLException ex) {
/* 141 */       Object[] args = { id, "GDC_CLASSTABLE_BUTTON" };
/* 142 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 144 */       GDMsgLogger.addError(msg);
/* 145 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 148 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBClassTableButton> wrap(ResultSet rs) throws SQLException {
/* 152 */     LinkedList<DBClassTableButton> list = new LinkedList<>();
/*     */     
/* 154 */     while (rs.next()) {
/* 155 */       DBClassTableButton ctb = new DBClassTableButton();
/*     */       
/* 157 */       ctb.classTableID = rs.getString(1);
/* 158 */       ctb.buttonID = rs.getString(2);
/* 159 */       ctb.index = rs.getInt(3);
/*     */       
/* 161 */       if (ctb.buttonID != null) {
/* 162 */         ctb.dbButton = DBSkillButton.getByButtonID(ctb.buttonID);
/*     */         
/* 164 */         if (ctb.dbButton != null) {
/* 165 */           ctb.dbSkill = DBSkill.get(ctb.dbButton.getSkillID());
/*     */         }
/*     */       } 
/*     */       
/* 169 */       list.add(ctb);
/*     */     } 
/*     */     
/* 172 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBClassTableButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */