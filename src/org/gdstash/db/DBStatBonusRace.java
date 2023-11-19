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
/*     */ public class DBStatBonusRace
/*     */   implements Cloneable
/*     */ {
/*     */   private static final String TABLE_NAME_ITEM = "GD_ITEM_RACES";
/*     */   private static final String TABLE_NAME_AFFIX = "GD_AFFIX_RACES";
/*     */   private static final String TABLE_NAME_SKILL = "GD_SKILL_RACES";
/*     */   private static final String FIELD_ID_ITEM = "ITEM_ID";
/*     */   private static final String FIELD_ID_AFFIX = "AFFIX_ID";
/*     */   private static final String FIELD_ID_SKILL = "SKILL_ID";
/*     */   private static final int ROW_ITEM_ID = 1;
/*     */   private static final int ROW_RACE_TAG = 2;
/*     */   private static final int ROW_RACE_NAME = 3;
/*     */   private String itemID;
/*     */   private String raceTag;
/*     */   private String raceName;
/*     */   
/*     */   public DBStatBonusRace() {
/*  39 */     this.itemID = null;
/*  40 */     this.raceTag = null;
/*  41 */     this.raceName = null;
/*     */   }
/*     */   
/*     */   public DBStatBonusRace(DBStatBonusRace race) {
/*  45 */     this.itemID = race.itemID;
/*  46 */     this.raceTag = race.raceTag;
/*  47 */     this.raceName = race.raceName;
/*     */   }
/*     */ 
/*     */   
/*     */   public DBStatBonusRace clone() {
/*  52 */     DBStatBonusRace race = new DBStatBonusRace(this);
/*     */     
/*  54 */     return race;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  59 */     if (o == null) return false; 
/*  60 */     if (!o.getClass().equals(DBStatBonusRace.class)) return false;
/*     */     
/*  62 */     DBStatBonusRace br = (DBStatBonusRace)o;
/*     */     
/*  64 */     return this.raceTag.equals(br.raceTag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getID() {
/*  72 */     return this.itemID;
/*     */   }
/*     */   
/*     */   public String getRaceTag() {
/*  76 */     if (this.raceTag == null) return null;
/*     */     
/*  78 */     if (this.raceTag.startsWith("tag")) return this.raceTag;
/*     */     
/*  80 */     return "tag" + this.raceTag;
/*     */   }
/*     */   
/*     */   public String getRaceName() {
/*  84 */     return this.raceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setID(String itemID) {
/*  92 */     this.itemID = itemID;
/*     */   }
/*     */   
/*     */   public void setRaceTag(String raceTag) {
/*  96 */     this.raceTag = raceTag;
/*     */   }
/*     */   
/*     */   public void setRaceName(String raceName) {
/* 100 */     this.raceName = raceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createItemTable(Connection conn) throws SQLException {
/* 108 */     createTable(conn, "GD_ITEM_RACES", "ITEM_ID");
/*     */   }
/*     */   
/*     */   public static void createAffixTable(Connection conn) throws SQLException {
/* 112 */     createTable(conn, "GD_AFFIX_RACES", "AFFIX_ID");
/*     */   }
/*     */   
/*     */   public static void createSkillTable(Connection conn) throws SQLException {
/* 116 */     createTable(conn, "GD_SKILL_RACES", "SKILL_ID");
/*     */   }
/*     */   
/*     */   private static void createTable(Connection conn, String tabName, String idName) throws SQLException {
/* 120 */     String dropTable = "DROP TABLE " + tabName;
/* 121 */     String createTable = "CREATE TABLE " + tabName + " (" + idName + " VARCHAR(" + "256" + ") NOT NULL, RACE_TAG           VARCHAR(" + "64" + "), RACE_NAME          VARCHAR(" + "256" + "), PRIMARY KEY (" + idName + ", RACE_TAG))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     try (Statement st = conn.createStatement()) {
/* 128 */       if (GDDBUtil.tableExists(conn, tabName)) {
/* 129 */         st.execute(dropTable);
/*     */       }
/* 131 */       st.execute(createTable);
/*     */       
/* 133 */       st.close();
/*     */       
/* 135 */       conn.commit();
/*     */     }
/* 137 */     catch (SQLException ex) {
/* 138 */       Object[] args = { tabName };
/* 139 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */       
/* 141 */       GDMsgLogger.addError(msg);
/*     */       
/* 143 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void deleteItem(Connection conn, String id) throws SQLException {
/* 148 */     delete(conn, "GD_ITEM_RACES", "ITEM_ID", id);
/*     */   }
/*     */   
/*     */   public static void deleteAffix(Connection conn, String id) throws SQLException {
/* 152 */     delete(conn, "GD_AFFIX_RACES", "AFFIX_ID", id);
/*     */   }
/*     */   
/*     */   public static void deleteSkill(Connection conn, String id) throws SQLException {
/* 156 */     delete(conn, "GD_SKILL_RACES", "SKILL_ID", id);
/*     */   }
/*     */   
/*     */   private static void delete(Connection conn, String tabName, String idName, String itemID) throws SQLException {
/* 160 */     String deleteEntry = "DELETE FROM " + tabName + " WHERE " + idName + " = ?";
/*     */     
/* 162 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 163 */       ps.setString(1, itemID);
/* 164 */       ps.executeUpdate();
/* 165 */       ps.close();
/*     */     }
/* 167 */     catch (SQLException ex) {
/* 168 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insertItem(Connection conn, String id, List<DBStatBonusRace> races) throws SQLException {
/* 173 */     insert(conn, "GD_ITEM_RACES", id, races);
/*     */   }
/*     */   
/*     */   public static void insertAffix(Connection conn, String id, List<DBStatBonusRace> races) throws SQLException {
/* 177 */     insert(conn, "GD_AFFIX_RACES", id, races);
/*     */   }
/*     */   
/*     */   public static void insertSkill(Connection conn, String id, List<DBStatBonusRace> races) throws SQLException {
/* 181 */     insert(conn, "GD_SKILL_RACES", id, races);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void insert(Connection conn, String tabName, String id, List<DBStatBonusRace> races) throws SQLException {
/* 187 */     String insert = "INSERT INTO " + tabName + " VALUES (?,?,?)";
/*     */     
/* 189 */     if (races == null)
/* 190 */       return;  if (races.isEmpty())
/*     */       return; 
/* 192 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 193 */       for (DBStatBonusRace race : races) {
/* 194 */         ps.setString(1, id);
/* 195 */         ps.setString(2, race.raceTag);
/* 196 */         ps.setString(3, race.raceName);
/*     */         
/* 198 */         ps.executeUpdate();
/*     */         
/* 200 */         ps.clearParameters();
/*     */       } 
/*     */       
/* 203 */       ps.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBStatBonusRace> getItem(String id) throws SQLException {
/* 208 */     return get("GD_ITEM_RACES", "ITEM_ID", id);
/*     */   }
/*     */   
/*     */   public static List<DBStatBonusRace> getAffix(String id) throws SQLException {
/* 212 */     return get("GD_AFFIX_RACES", "AFFIX_ID", id);
/*     */   }
/*     */   
/*     */   public static List<DBStatBonusRace> getSkill(String id) throws SQLException {
/* 216 */     return get("GD_SKILL_RACES", "SKILL_ID", id);
/*     */   }
/*     */   
/*     */   private static List<DBStatBonusRace> get(String tableName, String fieldName, String id) {
/* 220 */     List<DBStatBonusRace> list = new LinkedList<>();
/*     */     
/* 222 */     String command = "SELECT * FROM " + tableName + " WHERE " + fieldName + " = ?";
/*     */     
/* 224 */     try(Connection conn = GDDBData.getConnection(); 
/* 225 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 226 */       ps.setString(1, id);
/*     */       
/* 228 */       try (ResultSet rs = ps.executeQuery()) {
/* 229 */         list = wrap(rs);
/*     */         
/* 231 */         conn.commit();
/*     */       }
/* 233 */       catch (SQLException ex) {
/* 234 */         throw ex;
/*     */       }
/*     */     
/* 237 */     } catch (SQLException ex) {
/* 238 */       Object[] args = { id, tableName };
/* 239 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 241 */       GDMsgLogger.addError(msg);
/* 242 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 245 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBStatBonusRace> wrap(ResultSet rs) throws SQLException {
/* 249 */     LinkedList<DBStatBonusRace> list = new LinkedList<>();
/*     */     
/* 251 */     while (rs.next()) {
/* 252 */       DBStatBonusRace race = new DBStatBonusRace();
/*     */       
/* 254 */       race.itemID = rs.getString(1);
/* 255 */       race.raceTag = rs.getString(2);
/* 256 */       race.raceName = rs.getString(3);
/*     */       
/* 258 */       list.add(race);
/*     */     } 
/*     */     
/* 261 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBStatBonusRace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */