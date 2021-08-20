/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.description.BonusDetail;
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
/*     */ public class DBSkillModifier
/*     */ {
/*     */   public static final String TABLE_NAME_ITEM = "GD_ITEM_SKILLMODIFIER";
/*     */   public static final String TABLE_NAME_ITEMSET = "GD_ITEMSET_SKILLMODIFIER";
/*     */   public static final String TABLE_NAME_AFFIX = "GD_AFFIX_SKILLMODIFIER";
/*     */   public static final String FIELD_ID_ITEM = "ITEM_ID";
/*     */   public static final String FIELD_ID_ITEMSET = "ITEMSET_ID";
/*     */   private static final String FIELD_ID_AFFIX = "AFFIX_ID";
/*     */   private static final int ROW_ITEM_ID = 1;
/*     */   private static final int ROW_MODIFIER_ID = 2;
/*     */   private static final int ROW_SKILL_ID = 3;
/*     */   private static final int ROW_MASTERY_ID = 4;
/*     */   private String itemID;
/*     */   private String modifierID;
/*     */   private String skillID;
/*     */   private String masteryID;
/*     */   private int index;
/*     */   private DBSkill dbSkill;
/*     */   private DBSkill dbModifier;
/*     */   private String masteryName;
/*     */   
/*     */   public int getIndex() {
/*  56 */     return this.index;
/*     */   }
/*     */   
/*     */   public String getSkillID() {
/*  60 */     return this.skillID;
/*     */   }
/*     */   
/*     */   public String getSkillName() {
/*  64 */     if (this.dbSkill == null) return null;
/*     */     
/*  66 */     return this.dbSkill.getName();
/*     */   }
/*     */   
/*     */   public String getMasteryName() {
/*  70 */     return this.masteryName;
/*     */   }
/*     */   
/*     */   public DBSkill getSkill() {
/*  74 */     return this.dbSkill;
/*     */   }
/*     */   
/*     */   public String getModifierID() {
/*  78 */     return this.modifierID;
/*     */   }
/*     */   
/*     */   public List<DBStat> getModifierStatList() {
/*  82 */     List<DBStat> list = null;
/*     */     
/*  84 */     if (this.dbModifier == null) {
/*  85 */       list = new LinkedList<>();
/*     */     } else {
/*  87 */       list = this.dbModifier.getStatList();
/*     */     } 
/*     */     
/*  90 */     return list;
/*     */   }
/*     */   
/*     */   public DBSkill getSkillModifier() {
/*  94 */     return this.dbModifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIndex(int index) {
/* 102 */     this.index = index;
/*     */   }
/*     */   
/*     */   public void setSkillID(String skillID) {
/* 106 */     this.skillID = skillID;
/*     */   }
/*     */   
/*     */   public void setModifierID(String modifierID) {
/* 110 */     this.modifierID = modifierID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createItemTable(Connection conn) throws SQLException {
/* 118 */     createTable(conn, "GD_ITEM_SKILLMODIFIER", "ITEM_ID");
/*     */   }
/*     */   
/*     */   public static void createItemSetTable(Connection conn) throws SQLException {
/* 122 */     createTable(conn, "GD_ITEMSET_SKILLMODIFIER", "ITEMSET_ID");
/*     */   }
/*     */   
/*     */   public static void createAffixTable(Connection conn) throws SQLException {
/* 126 */     createTable(conn, "GD_AFFIX_SKILLMODIFIER", "AFFIX_ID");
/*     */   }
/*     */   
/*     */   private static void createTable(Connection conn, String tabName, String idName) throws SQLException {
/* 130 */     String dropTable = "DROP TABLE " + tabName;
/* 131 */     String createTable = "CREATE TABLE " + tabName + " (" + idName + "       VARCHAR(" + "256" + ") NOT NULL, MODIFIER_ID     VARCHAR(" + "256" + ") NOT NULL, SKILL_ID        VARCHAR(" + "256" + ") NOT NULL, MASTERY_ID      VARCHAR(" + "256" + ") , PRIMARY KEY (" + idName + ", MODIFIER_ID, SKILL_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     try (Statement st = conn.createStatement()) {
/* 139 */       if (GDDBUtil.tableExists(conn, tabName)) {
/* 140 */         st.execute(dropTable);
/*     */       }
/* 142 */       st.execute(createTable);
/*     */       
/* 144 */       st.close();
/*     */       
/* 146 */       conn.commit();
/*     */     }
/* 148 */     catch (SQLException ex) {
/* 149 */       Object[] args = { tabName };
/* 150 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */       
/* 152 */       GDMsgLogger.addError(msg);
/*     */       
/* 154 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void deleteItem(Connection conn, String id) throws SQLException {
/* 159 */     delete(conn, "GD_ITEM_SKILLMODIFIER", "ITEM_ID", id);
/*     */   }
/*     */   
/*     */   public static void deleteItemSet(Connection conn, String id) throws SQLException {
/* 163 */     delete(conn, "GD_ITEMSET_SKILLMODIFIER", "ITEMSET_ID", id);
/*     */   }
/*     */   
/*     */   public static void deleteAffix(Connection conn, String id) throws SQLException {
/* 167 */     delete(conn, "GD_AFFIX_SKILLMODIFIER", "AFFIX_ID", id);
/*     */   }
/*     */   
/*     */   private static void delete(Connection conn, String tabName, String idName, String id) throws SQLException {
/* 171 */     String deleteEntry = "DELETE FROM " + tabName + " WHERE " + idName + " = ?";
/*     */     
/* 173 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 174 */       ps.setString(1, id);
/* 175 */       ps.executeUpdate();
/* 176 */       ps.close();
/*     */     }
/* 178 */     catch (SQLException ex) {
/* 179 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insertItem(Connection conn, String id, List<DBSkillModifier> modifiers) throws SQLException {
/* 184 */     insert(conn, "GD_ITEM_SKILLMODIFIER", id, modifiers);
/*     */   }
/*     */   
/*     */   public static void insertItemSet(Connection conn, String id, List<DBSkillModifier> modifiers) throws SQLException {
/* 188 */     insert(conn, "GD_ITEMSET_SKILLMODIFIER", id, modifiers);
/*     */   }
/*     */   
/*     */   public static void insertAffix(Connection conn, String id, List<DBSkillModifier> modifiers) throws SQLException {
/* 192 */     insert(conn, "GD_AFFIX_SKILLMODIFIER", id, modifiers);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void insert(Connection conn, String tabName, String id, List<DBSkillModifier> modifiers) throws SQLException {
/* 198 */     String insert = "INSERT INTO " + tabName + " VALUES (?,?,?,?)";
/*     */     
/* 200 */     if (modifiers == null)
/* 201 */       return;  if (modifiers.isEmpty())
/*     */       return; 
/* 203 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 204 */       for (DBSkillModifier modifier : modifiers) {
/* 205 */         if (modifier.skillID == null) {
/* 206 */           Object[] args = { id, tabName };
/* 207 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */           
/* 209 */           GDMsgLogger.addWarning(msg);
/*     */           
/*     */           continue;
/*     */         } 
/* 213 */         if (modifier.modifierID == null) {
/* 214 */           Object[] args = { id, tabName };
/* 215 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */           
/* 217 */           GDMsgLogger.addWarning(msg);
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/*     */         try {
/* 223 */           ps.setString(1, id);
/*     */           
/* 225 */           ps.setString(2, modifier.modifierID);
/* 226 */           ps.setString(3, modifier.skillID);
/* 227 */           ps.setString(4, modifier.masteryID);
/*     */           
/* 229 */           ps.executeUpdate();
/*     */           
/* 231 */           ps.clearParameters();
/*     */         }
/* 233 */         catch (SQLException ex) {
/*     */           
/* 235 */           Object[] args = { id, "GD_ITEM" };
/* 236 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */           
/* 238 */           GDMsgLogger.addWarning(msg);
/* 239 */           GDMsgLogger.addWarning(ex);
/*     */         } 
/*     */       } 
/*     */       
/* 243 */       ps.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBSkillModifier> getItem(String id) throws SQLException {
/* 248 */     return get("GD_ITEM_SKILLMODIFIER", "ITEM_ID", id);
/*     */   }
/*     */   
/*     */   public static List<DBSkillModifier> getItemSet(String id) throws SQLException {
/* 252 */     return get("GD_ITEMSET_SKILLMODIFIER", "ITEMSET_ID", id);
/*     */   }
/*     */   
/*     */   public static List<DBSkillModifier> getAffix(String id) throws SQLException {
/* 256 */     return get("GD_AFFIX_SKILLMODIFIER", "AFFIX_ID", id);
/*     */   }
/*     */   
/*     */   private static List<DBSkillModifier> get(String tableName, String idName, String id) {
/* 260 */     List<DBSkillModifier> list = new LinkedList<>();
/*     */     
/* 262 */     String command = "SELECT * FROM " + tableName + " WHERE " + idName + " = ?";
/*     */     
/* 264 */     try(Connection conn = GDDBData.getConnection(); 
/* 265 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 266 */       ps.setString(1, id);
/*     */       
/* 268 */       try (ResultSet rs = ps.executeQuery()) {
/* 269 */         list = wrap(rs);
/*     */         
/* 271 */         conn.commit();
/*     */       }
/* 273 */       catch (SQLException ex) {
/* 274 */         throw ex;
/*     */       }
/*     */     
/* 277 */     } catch (SQLException ex) {
/* 278 */       Object[] args = { id, tableName };
/* 279 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 281 */       GDMsgLogger.addError(msg);
/* 282 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 285 */     return list;
/*     */   }
/*     */   
/*     */   public static List<DBSkillModifier> getAllItem() throws SQLException {
/* 289 */     return getAll("GD_ITEM_SKILLMODIFIER");
/*     */   }
/*     */   
/*     */   public static List<DBSkillModifier> getAllAffix() throws SQLException {
/* 293 */     return getAll("GD_AFFIX_SKILLMODIFIER");
/*     */   }
/*     */   
/*     */   private static List<DBSkillModifier> getAll(String tableName) {
/* 297 */     List<DBSkillModifier> list = new LinkedList<>();
/*     */     
/* 299 */     String command = "SELECT * FROM " + tableName;
/*     */     
/* 301 */     try(Connection conn = GDDBData.getConnection(); 
/* 302 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*     */       
/* 304 */       try (ResultSet rs = ps.executeQuery()) {
/* 305 */         list = wrap(rs);
/*     */         
/* 307 */         conn.commit();
/*     */       }
/* 309 */       catch (SQLException ex) {
/* 310 */         throw ex;
/*     */       }
/*     */     
/* 313 */     } catch (SQLException ex) {
/* 314 */       Object[] args = { "*", tableName };
/* 315 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 317 */       GDMsgLogger.addError(msg);
/* 318 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 321 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBSkillModifier> wrap(ResultSet rs) throws SQLException {
/* 325 */     LinkedList<DBSkillModifier> list = new LinkedList<>();
/*     */     
/* 327 */     while (rs.next()) {
/* 328 */       DBSkillModifier modifier = new DBSkillModifier();
/*     */       
/* 330 */       modifier.itemID = rs.getString(1);
/* 331 */       modifier.modifierID = rs.getString(2);
/* 332 */       modifier.skillID = rs.getString(3);
/* 333 */       modifier.masteryID = rs.getString(4);
/*     */       
/* 335 */       if (modifier.modifierID != null) modifier.dbModifier = DBSkill.get(modifier.modifierID); 
/* 336 */       if (modifier.skillID != null) modifier.dbSkill = DBSkill.get(modifier.skillID);
/*     */       
/* 338 */       if (modifier.dbSkill != null) {
/* 339 */         DBSkillTree skillTree = DBSkillTree.getBySkillID(modifier.dbSkill.getSkillID());
/*     */         
/* 341 */         if (skillTree != null) {
/* 342 */           DBSkill mastery = skillTree.getMasterySkill();
/*     */           
/* 344 */           if (mastery != null) {
/* 345 */             modifier.masteryName = mastery.getName();
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 350 */       list.add(modifier);
/*     */     } 
/*     */     
/* 353 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateDB() {
/* 362 */     updateItemDB();
/* 363 */     updateItemSetDB();
/* 364 */     updateAffixDB();
/*     */   }
/*     */   
/*     */   private static void updateItemDB() {
/* 368 */     updateDB("GD_ITEM_SKILLMODIFIER", "ITEM_ID");
/*     */   }
/*     */   
/*     */   private static void updateItemSetDB() {
/* 372 */     updateDB("GD_ITEMSET_SKILLMODIFIER", "ITEMSET_ID");
/*     */   }
/*     */   
/*     */   private static void updateAffixDB() {
/* 376 */     updateDB("GD_AFFIX_SKILLMODIFIER", "AFFIX_ID");
/*     */   }
/*     */   
/*     */   private static void updateDB(String tableName, String fieldName) {
/* 380 */     List<DBSkillModifier> list = getEmptyMasteries(tableName);
/*     */     
/* 382 */     if (list == null)
/*     */       return; 
/* 384 */     for (DBSkillModifier modifier : list) {
/* 385 */       if (modifier.skillID == null)
/*     */         continue; 
/* 387 */       DBSkill skill = DBSkill.get(modifier.skillID);
/*     */       
/* 389 */       if (skill == null) {
/* 390 */         Object[] args = { tableName, modifier.itemID, modifier.skillID, "GD_SKILL" };
/* 391 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_REF_TABLE_ID_FROM_ID", args);
/*     */         
/* 393 */         GDMsgLogger.addWarning(msg);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 398 */       modifier.masteryID = skill.getMasteryID();
/*     */     } 
/*     */     
/* 401 */     updateMastery(tableName, fieldName, list);
/*     */   }
/*     */   
/*     */   private static List<DBSkillModifier> getEmptyMasteries(String tableName) {
/* 405 */     List<DBSkillModifier> list = new LinkedList<>();
/*     */     
/* 407 */     String command = "SELECT * FROM " + tableName + " WHERE MASTERY_ID is null";
/*     */     
/* 409 */     try(Connection conn = GDDBData.getConnection(); 
/* 410 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*     */       
/* 412 */       try (ResultSet rs = ps.executeQuery()) {
/* 413 */         list = wrap(rs);
/*     */         
/* 415 */         conn.commit();
/*     */       }
/* 417 */       catch (SQLException ex) {
/* 418 */         throw ex;
/*     */       }
/*     */     
/* 421 */     } catch (SQLException ex) {
/* 422 */       Object[] args = { "MASTERY_ID is null", tableName };
/* 423 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 425 */       GDMsgLogger.addError(msg);
/* 426 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 429 */     return list;
/*     */   }
/*     */   
/*     */   private static void updateMastery(String tableName, String fieldName, List<DBSkillModifier> list) {
/* 433 */     String command = "UPDATE " + tableName + " SET MASTERY_ID = ? WHERE " + fieldName + " = ? AND MODIFIER_ID = ? AND SKILL_ID = ?";
/*     */ 
/*     */     
/* 436 */     try(Connection conn = GDDBData.getConnection(); 
/* 437 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*     */       
/* 439 */       for (DBSkillModifier modifier : list) {
/* 440 */         if (modifier.masteryID == null)
/*     */           continue; 
/* 442 */         ps.setString(1, modifier.masteryID);
/* 443 */         ps.setString(2, modifier.itemID);
/* 444 */         ps.setString(3, modifier.modifierID);
/* 445 */         ps.setString(4, modifier.skillID);
/*     */         
/* 447 */         ps.executeUpdate();
/*     */         
/* 449 */         ps.clearParameters();
/*     */       } 
/*     */       
/* 452 */       ps.close();
/*     */       
/* 454 */       conn.commit();
/*     */     }
/* 456 */     catch (SQLException ex) {
/* 457 */       Object[] args = { "-", tableName };
/* 458 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 460 */       GDMsgLogger.addError(msg);
/* 461 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BonusDetail getBonusDetail(DBStashItem item, int detailType, String prefix) {
/* 470 */     BonusDetail bonus = null;
/*     */     
/* 472 */     if (this.dbModifier == null) return bonus;
/*     */     
/* 474 */     bonus = new BonusDetail(this);
/*     */     
/* 476 */     return bonus;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBSkillModifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */