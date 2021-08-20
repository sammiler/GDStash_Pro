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
/*     */ public class DBSkillBonusLevel
/*     */ {
/*     */   public static final String TABLE_NAME_ITEM = "GD_ITEM_SKILL_LEVEL";
/*     */   public static final String TABLE_NAME_ITEMSET = "GD_ITEMSET_SKILL_LEVEL";
/*     */   public static final String TABLE_NAME_AFFIX = "GD_AFFIX_SKILL_LEVEL";
/*     */   private static final String FIELD_ID_ITEM = "ITEM_ID";
/*     */   private static final String FIELD_ID_ITEMSET = "ITEMSET_ID";
/*     */   private static final String FIELD_ID_AFFIX = "AFFIX_ID";
/*     */   private static final int ROW_ITEM_ID = 1;
/*     */   private static final int ROW_BONUS_TYPE = 2;
/*     */   private static final int ROW_INDEX = 3;
/*     */   private static final int ROW_BONUS_LEVEL = 4;
/*     */   private static final int ROW_BONUS_VALUE = 5;
/*     */   private String itemID;
/*     */   private int type;
/*     */   private int index;
/*     */   private int bonusLevel;
/*     */   private int bonusValue;
/*     */   
/*     */   public DBSkillBonusLevel() {
/*  44 */     this.itemID = null;
/*     */   }
/*     */   
/*     */   public DBSkillBonusLevel(int bonusValue, int bonusLevel) {
/*  48 */     this.itemID = null;
/*  49 */     this.bonusLevel = bonusLevel;
/*  50 */     this.bonusValue = bonusValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLevel() {
/*  58 */     return this.bonusLevel;
/*     */   }
/*     */   
/*     */   public int getValue() {
/*  62 */     return this.bonusValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable(Connection conn, String tabName, String idName) throws SQLException {
/*  70 */     String dropTable = "DROP TABLE " + tabName;
/*  71 */     String createTable = "CREATE TABLE " + tabName + " (" + idName + "    VARCHAR(" + "256" + ") NOT NULL, TYPE         INTEGER, INDEX        INTEGER, LEVEL        INTEGER, BONUS_VALUE  INTEGER, PRIMARY KEY (" + idName + ", TYPE, INDEX, LEVEL))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     try (Statement st = conn.createStatement()) {
/*  80 */       if (GDDBUtil.tableExists(conn, tabName)) {
/*  81 */         st.execute(dropTable);
/*     */       }
/*  83 */       st.execute(createTable);
/*     */       
/*  85 */       st.close();
/*     */       
/*  87 */       conn.commit();
/*     */     }
/*  89 */     catch (SQLException ex) {
/*  90 */       Object[] args = { tabName };
/*  91 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */       
/*  93 */       GDMsgLogger.addError(msg);
/*     */       
/*  95 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(Connection conn, String tabName, String idName, String itemID) throws SQLException {
/* 100 */     String deleteEntry = "DELETE FROM " + tabName + " WHERE " + idName + " = ?";
/*     */     
/* 102 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 103 */       ps.setString(1, itemID);
/*     */       
/* 105 */       ps.executeUpdate();
/* 106 */       ps.close();
/*     */     }
/* 108 */     catch (SQLException ex) {
/* 109 */       conn.rollback();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void insert(Connection conn, String tabName, String id, DBSkillBonus bonus) throws SQLException {
/* 115 */     String insert = "INSERT INTO " + tabName + " VALUES (?,?,?,?,?)";
/*     */     
/* 117 */     if (bonus.getLevelList() == null)
/* 118 */       return;  if (bonus.getLevelList().isEmpty())
/*     */       return; 
/* 120 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 121 */       for (DBSkillBonusLevel level : bonus.getLevelList()) {
/* 122 */         ps.setString(1, id);
/* 123 */         ps.setInt(2, bonus.getSkillBonusType());
/* 124 */         ps.setInt(3, bonus.getIndex());
/* 125 */         ps.setInt(4, level.bonusLevel);
/* 126 */         ps.setInt(5, level.bonusValue);
/*     */         
/* 128 */         ps.executeUpdate();
/*     */         
/* 130 */         ps.clearParameters();
/*     */       } 
/*     */       
/* 133 */       ps.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBSkillBonusLevel> get(String tableName, String fieldName, DBSkillBonus bonus) {
/* 138 */     List<DBSkillBonusLevel> list = new LinkedList<>();
/*     */     
/* 140 */     String command = "SELECT * FROM " + tableName + " WHERE " + fieldName + " = ? AND TYPE = ? AND INDEX = ?";
/*     */     
/* 142 */     try(Connection conn = GDDBData.getConnection(); 
/* 143 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 144 */       ps.setString(1, bonus.getID());
/* 145 */       ps.setInt(2, bonus.getSkillBonusType());
/* 146 */       ps.setInt(3, bonus.getIndex());
/*     */       
/* 148 */       try (ResultSet rs = ps.executeQuery()) {
/* 149 */         list = wrap(rs);
/*     */         
/* 151 */         conn.commit();
/*     */       }
/* 153 */       catch (SQLException ex) {
/* 154 */         throw ex;
/*     */       }
/*     */     
/* 157 */     } catch (SQLException ex) {
/* 158 */       Object[] args = { bonus.getID(), tableName };
/* 159 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 161 */       GDMsgLogger.addError(msg);
/* 162 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 165 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBSkillBonusLevel> wrap(ResultSet rs) throws SQLException {
/* 169 */     LinkedList<DBSkillBonusLevel> list = new LinkedList<>();
/*     */     
/* 171 */     while (rs.next()) {
/* 172 */       DBSkillBonusLevel level = new DBSkillBonusLevel();
/*     */       
/* 174 */       level.itemID = rs.getString(1);
/* 175 */       level.type = rs.getInt(2);
/* 176 */       level.index = rs.getInt(3);
/* 177 */       level.bonusLevel = rs.getInt(4);
/* 178 */       level.bonusValue = rs.getInt(5);
/*     */       
/* 180 */       list.add(level);
/*     */     } 
/*     */     
/* 183 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBSkillBonusLevel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */