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
/*     */ public class DBSkillBonus
/*     */   implements Cloneable
/*     */ {
/*     */   public static final String TABLE_NAME_ITEM = "GD_ITEM_SKILLS";
/*     */   public static final String TABLE_NAME_ITEMSET = "GD_ITEMSET_SKILLS";
/*     */   public static final String TABLE_NAME_AFFIX = "GD_AFFIX_SKILLS";
/*     */   private static final String FIELD_ID_ITEM = "ITEM_ID";
/*     */   private static final String FIELD_ID_ITEMSET = "ITEMSET_ID";
/*     */   private static final String FIELD_ID_AFFIX = "AFFIX_ID";
/*     */   private static final int ROW_ITEM_ID = 1;
/*     */   private static final int ROW_BONUS_TYPE = 2;
/*     */   private static final int ROW_INDEX = 3;
/*     */   private static final int ROW_BONUS_ENTITY = 4;
/*     */   private static final int ROW_ENTITY_NAME = 5;
/*     */   private static final int ROW_MASTERY_ID = 6;
/*     */   public static final int TYPE_MASTERY = 1;
/*     */   public static final int TYPE_CLASS_SKILL = 2;
/*     */   public static final int TYPE_GRANTED_SKILL = 3;
/*     */   public static final int TYPE_PLUS_TO_ALL = 4;
/*     */   private String itemID;
/*     */   private int type;
/*     */   private int index;
/*     */   private String bonusEntity;
/*     */   private String entityName;
/*     */   private String masteryID;
/*     */   private List<DBSkillBonusLevel> levels;
/*     */   private int currBonus;
/*     */   private String masteryName;
/*     */   
/*     */   public DBSkillBonus() {
/*  58 */     this.itemID = null;
/*  59 */     this.type = 0;
/*  60 */     this.index = 0;
/*  61 */     this.bonusEntity = null;
/*  62 */     this.entityName = null;
/*  63 */     this.masteryID = null;
/*  64 */     this.masteryName = null;
/*     */     
/*  66 */     this.levels = new LinkedList<>();
/*     */   }
/*     */   
/*     */   public DBSkillBonus(int index, int type, String bonusEntity) {
/*  70 */     this.itemID = null;
/*  71 */     this.type = type;
/*  72 */     this.index = index;
/*  73 */     this.bonusEntity = bonusEntity;
/*  74 */     this.entityName = null;
/*  75 */     this.masteryID = null;
/*  76 */     this.masteryName = null;
/*     */     
/*  78 */     this.levels = new LinkedList<>();
/*     */   }
/*     */   
/*     */   public DBSkillBonus(int index, int type) {
/*  82 */     this.itemID = null;
/*  83 */     this.type = type;
/*  84 */     this.index = index;
/*  85 */     this.bonusEntity = null;
/*  86 */     this.entityName = null;
/*  87 */     this.masteryID = null;
/*  88 */     this.masteryName = null;
/*     */     
/*  90 */     this.levels = new LinkedList<>();
/*     */   }
/*     */   
/*     */   private DBSkillBonus(DBSkillBonus bonus) {
/*  94 */     this.itemID = bonus.itemID;
/*  95 */     this.type = bonus.type;
/*  96 */     this.index = bonus.index;
/*  97 */     this.bonusEntity = bonus.bonusEntity;
/*  98 */     this.levels = bonus.levels;
/*  99 */     this.entityName = bonus.entityName;
/* 100 */     this.masteryID = bonus.masteryID;
/* 101 */     this.masteryName = bonus.masteryName;
/*     */     
/* 103 */     this.currBonus = bonus.currBonus;
/*     */   }
/*     */   
/*     */   public Object clone() {
/* 107 */     return new DBSkillBonus(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getID() {
/* 115 */     return this.itemID;
/*     */   }
/*     */   
/*     */   public int getSkillBonusType() {
/* 119 */     return this.type;
/*     */   }
/*     */   
/*     */   public int getIndex() {
/* 123 */     return this.index;
/*     */   }
/*     */   
/*     */   public String getEntity() {
/* 127 */     return this.bonusEntity;
/*     */   }
/*     */   
/*     */   public String getEntityName() {
/* 131 */     return this.entityName;
/*     */   }
/*     */   
/*     */   public String getMasteryName() {
/* 135 */     return this.masteryName;
/*     */   }
/*     */   
/*     */   public int getValue() {
/* 139 */     return this.currBonus;
/*     */   }
/*     */   
/*     */   public int getValueForLevel(int level) {
/* 143 */     int value = 0;
/*     */     
/* 145 */     for (DBSkillBonusLevel bl : this.levels) {
/* 146 */       value = bl.getValue();
/*     */       
/* 148 */       if (bl.getLevel() == level) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 153 */     return value;
/*     */   }
/*     */   
/*     */   public List<DBSkillBonusLevel> getLevelList() {
/* 157 */     return this.levels;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(int value) {
/* 165 */     this.currBonus = value;
/*     */   }
/*     */   
/*     */   public static void addEntity(List<DBSkillBonus> list, int bonusIndex, int type, String bonusEntity) {
/* 169 */     boolean found = false;
/*     */     
/* 171 */     for (DBSkillBonus bonus : list) {
/* 172 */       if (bonus.getIndex() == bonusIndex && bonus
/* 173 */         .getSkillBonusType() == type) {
/* 174 */         bonus.setEntity(bonusEntity);
/*     */         
/* 176 */         found = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 182 */     if (!found) {
/* 183 */       DBSkillBonus bonus = new DBSkillBonus(bonusIndex, type, bonusEntity);
/*     */       
/* 185 */       list.add(bonus);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void addValue(List<DBSkillBonus> list, int bonusIndex, int type, int bonusValue, int valueIndex) {
/* 190 */     boolean found = false;
/*     */     
/* 192 */     for (DBSkillBonus bonus : list) {
/* 193 */       if (bonus.getIndex() == bonusIndex && bonus
/* 194 */         .getSkillBonusType() == type) {
/* 195 */         bonus.levels.add(new DBSkillBonusLevel(bonusValue, valueIndex));
/* 196 */         bonus.currBonus = bonusValue;
/*     */         
/* 198 */         found = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 204 */     if (!found) {
/* 205 */       DBSkillBonus bonus = new DBSkillBonus(bonusIndex, type);
/*     */       
/* 207 */       bonus.levels.add(new DBSkillBonusLevel(bonusValue, valueIndex));
/* 208 */       bonus.currBonus = bonusValue;
/*     */       
/* 210 */       list.add(bonus);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setID(String itemID) {
/* 215 */     this.itemID = itemID;
/*     */   }
/*     */   
/*     */   public void setSkillBonusType(int type) {
/* 219 */     this.type = type;
/*     */   }
/*     */   
/*     */   public void setIndex(int index) {
/* 223 */     this.index = index;
/*     */   }
/*     */   
/*     */   public void setEntity(String bonusEntity) {
/* 227 */     this.bonusEntity = bonusEntity;
/*     */   }
/*     */   
/*     */   public void setEntityName(String entityName) {
/* 231 */     this.entityName = entityName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createItemTable(Connection conn) throws SQLException {
/* 239 */     createTable(conn, "GD_ITEM_SKILLS", "GD_ITEM_SKILL_LEVEL", "ITEM_ID");
/*     */   }
/*     */   
/*     */   public static void createItemSetTable(Connection conn) throws SQLException {
/* 243 */     createTable(conn, "GD_ITEMSET_SKILLS", "GD_ITEMSET_SKILL_LEVEL", "ITEMSET_ID");
/*     */   }
/*     */   
/*     */   public static void createAffixTable(Connection conn) throws SQLException {
/* 247 */     createTable(conn, "GD_AFFIX_SKILLS", "GD_AFFIX_SKILL_LEVEL", "AFFIX_ID");
/*     */   }
/*     */   
/*     */   private static void createTable(Connection conn, String tabName, String tabNameLevel, String idName) throws SQLException {
/* 251 */     String dropTable = "DROP TABLE " + tabName;
/* 252 */     String createTable = "CREATE TABLE " + tabName + " (" + idName + "    VARCHAR(" + "256" + ") NOT NULL, TYPE         INTEGER, INDEX        INTEGER, BONUS_ENTITY VARCHAR(" + "256" + "), ENTITY_NAME  VARCHAR(" + "256" + "), MASTERY_ID   VARCHAR(" + "256" + "), PRIMARY KEY (" + idName + ", TYPE, INDEX))";
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
/* 264 */     try (Statement st = conn.createStatement()) {
/* 265 */       if (GDDBUtil.tableExists(conn, tabName)) {
/* 266 */         st.execute(dropTable);
/*     */       }
/* 268 */       st.execute(createTable);
/*     */       
/* 270 */       st.close();
/*     */       
/* 272 */       conn.commit();
/*     */       
/* 274 */       DBSkillBonusLevel.createTable(conn, tabNameLevel, idName);
/*     */     }
/* 276 */     catch (SQLException ex) {
/* 277 */       Object[] args = { tabName };
/* 278 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */       
/* 280 */       GDMsgLogger.addError(msg);
/*     */       
/* 282 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void deleteItem(Connection conn, String id) throws SQLException {
/* 287 */     delete(conn, "GD_ITEM_SKILLS", "GD_ITEM_SKILL_LEVEL", "ITEM_ID", id);
/*     */   }
/*     */   
/*     */   public static void deleteItemSet(Connection conn, String id) throws SQLException {
/* 291 */     delete(conn, "GD_ITEMSET_SKILLS", "GD_ITEMSET_SKILL_LEVEL", "ITEMSET_ID", id);
/*     */   }
/*     */   
/*     */   public static void deleteAffix(Connection conn, String id) throws SQLException {
/* 295 */     delete(conn, "GD_AFFIX_SKILLS", "GD_AFFIX_SKILL_LEVEL", "AFFIX_ID", id);
/*     */   }
/*     */   
/*     */   private static void delete(Connection conn, String tabName, String tabNameLevel, String fieldName, String itemID) throws SQLException {
/* 299 */     String deleteEntry = "DELETE FROM " + tabName + " WHERE " + fieldName + " = ?";
/*     */     
/* 301 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 302 */       ps.setString(1, itemID);
/* 303 */       ps.executeUpdate();
/* 304 */       ps.close();
/*     */       
/* 306 */       DBSkillBonusLevel.delete(conn, tabNameLevel, fieldName, itemID);
/*     */     }
/* 308 */     catch (SQLException ex) {
/* 309 */       conn.rollback();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insertItem(Connection conn, String id, List<DBSkillBonus> bonuses) throws SQLException {
/* 314 */     insert(conn, "GD_ITEM_SKILLS", "GD_ITEM_SKILL_LEVEL", id, bonuses);
/*     */   }
/*     */   
/*     */   public static void insertItemSet(Connection conn, String id, List<DBSkillBonus> bonuses) throws SQLException {
/* 318 */     insert(conn, "GD_ITEMSET_SKILLS", "GD_ITEMSET_SKILL_LEVEL", id, bonuses);
/*     */   }
/*     */   
/*     */   public static void insertAffix(Connection conn, String id, List<DBSkillBonus> bonuses) throws SQLException {
/* 322 */     insert(conn, "GD_AFFIX_SKILLS", "GD_AFFIX_SKILL_LEVEL", id, bonuses);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void insert(Connection conn, String tabName, String tabNameLevel, String id, List<DBSkillBonus> bonuses) throws SQLException {
/* 328 */     String insert = "INSERT INTO " + tabName + " VALUES (?,?,?,?,?,?)";
/*     */     
/* 330 */     if (bonuses == null)
/* 331 */       return;  if (bonuses.isEmpty())
/*     */       return; 
/* 333 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 334 */       for (DBSkillBonus bonus : bonuses) {
/* 335 */         if (bonus.bonusEntity == null && bonus.type != 4) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 340 */         ps.setString(1, id);
/* 341 */         ps.setInt(2, bonus.type);
/* 342 */         ps.setInt(3, bonus.index);
/* 343 */         ps.setString(4, bonus.bonusEntity);
/* 344 */         ps.setString(5, bonus.entityName);
/* 345 */         ps.setString(6, bonus.masteryID);
/*     */         
/* 347 */         ps.executeUpdate();
/*     */         
/* 349 */         ps.clearParameters();
/*     */         
/* 351 */         DBSkillBonusLevel.insert(conn, tabNameLevel, id, bonus);
/*     */       } 
/*     */       
/* 354 */       ps.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBSkillBonus> getItem(String id) throws SQLException {
/* 359 */     return get("GD_ITEM_SKILLS", "GD_ITEM_SKILL_LEVEL", "ITEM_ID", id);
/*     */   }
/*     */   
/*     */   public static List<DBSkillBonus> getItemSet(String id) throws SQLException {
/* 363 */     return get("GD_ITEMSET_SKILLS", "GD_ITEMSET_SKILL_LEVEL", "ITEMSET_ID", id);
/*     */   }
/*     */   
/*     */   public static List<DBSkillBonus> getAffix(String id) throws SQLException {
/* 367 */     return get("GD_AFFIX_SKILLS", "GD_AFFIX_SKILL_LEVEL", "AFFIX_ID", id);
/*     */   }
/*     */   
/*     */   private static List<DBSkillBonus> get(String tableName, String tableNameLevel, String fieldName, String id) {
/* 371 */     List<DBSkillBonus> list = new LinkedList<>();
/*     */     
/* 373 */     String command = "SELECT * FROM " + tableName + " WHERE " + fieldName + " = ?";
/*     */     
/* 375 */     try(Connection conn = GDDBData.getConnection(); 
/* 376 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 377 */       ps.setString(1, id);
/*     */       
/* 379 */       try (ResultSet rs = ps.executeQuery()) {
/* 380 */         list = wrap(rs, tableNameLevel, fieldName);
/*     */         
/* 382 */         conn.commit();
/*     */       }
/* 384 */       catch (SQLException ex) {
/* 385 */         throw ex;
/*     */       }
/*     */     
/* 388 */     } catch (SQLException ex) {
/* 389 */       Object[] args = { id, tableName };
/* 390 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 392 */       GDMsgLogger.addError(msg);
/* 393 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 396 */     return list;
/*     */   }
/*     */   
/*     */   public static List<DBSkillBonus> getAllItem() throws SQLException {
/* 400 */     return getAll("GD_ITEM_SKILLS", "GD_ITEM_SKILL_LEVEL", "ITEM_ID");
/*     */   }
/*     */   
/*     */   public static List<DBSkillBonus> getAllAffix() throws SQLException {
/* 404 */     return getAll("GD_AFFIX_SKILLS", "GD_AFFIX_SKILL_LEVEL", "AFFIX_ID");
/*     */   }
/*     */   
/*     */   private static List<DBSkillBonus> getAll(String tableName, String tableNameLevel, String fieldName) {
/* 408 */     List<DBSkillBonus> list = new LinkedList<>();
/*     */     
/* 410 */     String command = "SELECT * FROM " + tableName;
/*     */     
/* 412 */     try(Connection conn = GDDBData.getConnection(); 
/* 413 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*     */       
/* 415 */       try (ResultSet rs = ps.executeQuery()) {
/* 416 */         list = wrap(rs, tableNameLevel, fieldName);
/*     */         
/* 418 */         conn.commit();
/*     */       }
/* 420 */       catch (SQLException ex) {
/* 421 */         throw ex;
/*     */       }
/*     */     
/* 424 */     } catch (SQLException ex) {
/* 425 */       Object[] args = { "*", tableName };
/* 426 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 428 */       GDMsgLogger.addError(msg);
/* 429 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 432 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBSkillBonus> wrap(ResultSet rs, String tableNameLevel, String fieldName) throws SQLException {
/* 436 */     LinkedList<DBSkillBonus> list = new LinkedList<>();
/*     */     
/* 438 */     while (rs.next()) {
/* 439 */       DBSkillBonus bonus = new DBSkillBonus();
/*     */       
/* 441 */       bonus.itemID = rs.getString(1);
/* 442 */       bonus.type = rs.getInt(2);
/* 443 */       bonus.index = rs.getInt(3);
/* 444 */       bonus.bonusEntity = rs.getString(4);
/* 445 */       bonus.entityName = rs.getString(5);
/* 446 */       bonus.masteryID = rs.getString(6);
/*     */       
/* 448 */       bonus.levels = DBSkillBonusLevel.get(tableNameLevel, fieldName, bonus);
/*     */       
/* 450 */       bonus.currBonus = bonus.getValueForLevel(0);
/*     */       
/* 452 */       if (bonus.type == 2 && 
/* 453 */         bonus.masteryID != null) {
/* 454 */         DBSkill mastery = DBSkill.get(bonus.masteryID);
/*     */         
/* 456 */         if (mastery != null) {
/* 457 */           bonus.masteryName = mastery.getName();
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 462 */       list.add(bonus);
/*     */     } 
/*     */     
/* 465 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateDB() {
/* 474 */     updateItemDB();
/* 475 */     updateItemSetDB();
/* 476 */     updateAffixDB();
/*     */   }
/*     */   
/*     */   private static void updateItemDB() {
/* 480 */     updateDB("GD_ITEM_SKILLS", "GD_ITEM_SKILL_LEVEL", "ITEM_ID");
/*     */   }
/*     */   
/*     */   private static void updateItemSetDB() {
/* 484 */     updateDB("GD_ITEMSET_SKILLS", "GD_ITEMSET_SKILL_LEVEL", "ITEMSET_ID");
/*     */   }
/*     */   
/*     */   private static void updateAffixDB() {
/* 488 */     updateDB("GD_AFFIX_SKILLS", "GD_AFFIX_SKILL_LEVEL", "AFFIX_ID");
/*     */   }
/*     */   
/*     */   private static void updateDB(String tableName, String tableNameLevel, String fieldName) {
/* 492 */     List<DBSkillBonus> list = getEmptyNames(tableName, tableNameLevel, fieldName);
/*     */     
/* 494 */     if (list == null)
/*     */       return; 
/* 496 */     for (DBSkillBonus bonus : list) {
/* 497 */       if (bonus.bonusEntity == null)
/*     */         continue; 
/* 499 */       DBSkill skill = DBSkill.get(bonus.bonusEntity);
/*     */       
/* 501 */       if (skill == null) {
/* 502 */         Object[] args = { tableName, bonus.itemID, bonus.bonusEntity, "GD_SKILL" };
/* 503 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_REF_TABLE_ID_FROM_ID", args);
/*     */         
/* 505 */         GDMsgLogger.addWarning(msg);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 510 */       bonus.entityName = skill.getName();
/* 511 */       bonus.masteryID = skill.getMasteryID();
/*     */     } 
/*     */     
/* 514 */     updateNames(tableName, fieldName, list);
/*     */   }
/*     */   
/*     */   private static List<DBSkillBonus> getEmptyNames(String tableName, String tableNameLevel, String fieldName) {
/* 518 */     List<DBSkillBonus> list = new LinkedList<>();
/*     */     
/* 520 */     String command = "SELECT * FROM " + tableName + " WHERE ENTITY_NAME is null or MASTERY_ID is null";
/*     */     
/* 522 */     try(Connection conn = GDDBData.getConnection(); 
/* 523 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*     */       
/* 525 */       try (ResultSet rs = ps.executeQuery()) {
/* 526 */         list = wrap(rs, tableNameLevel, fieldName);
/*     */         
/* 528 */         conn.commit();
/*     */       }
/* 530 */       catch (SQLException ex) {
/* 531 */         throw ex;
/*     */       }
/*     */     
/* 534 */     } catch (SQLException ex) {
/* 535 */       Object[] args = { "ENTITY_NAME is null", tableName };
/* 536 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 538 */       GDMsgLogger.addError(msg);
/* 539 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 542 */     return list;
/*     */   }
/*     */   
/*     */   private static void updateNames(String tableName, String fieldName, List<DBSkillBonus> list) {
/* 546 */     String command = "UPDATE " + tableName + " SET ENTITY_NAME = ?, MASTERY_ID = ? WHERE " + fieldName + " = ? AND TYPE = ? AND INDEX = ?";
/*     */ 
/*     */     
/* 549 */     try(Connection conn = GDDBData.getConnection(); 
/* 550 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*     */       
/* 552 */       for (DBSkillBonus bonus : list) {
/* 553 */         if (bonus.entityName == null && bonus.masteryID == null)
/*     */           continue; 
/* 555 */         ps.setString(1, bonus.entityName);
/* 556 */         ps.setString(2, bonus.masteryID);
/* 557 */         ps.setString(3, bonus.itemID);
/* 558 */         ps.setInt(4, bonus.type);
/* 559 */         ps.setInt(5, bonus.index);
/*     */         
/* 561 */         ps.executeUpdate();
/*     */         
/* 563 */         ps.clearParameters();
/*     */       } 
/*     */       
/* 566 */       ps.close();
/*     */       
/* 568 */       conn.commit();
/*     */     }
/* 570 */     catch (SQLException ex) {
/* 571 */       Object[] args = { "-", tableName };
/* 572 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 574 */       GDMsgLogger.addError(msg);
/* 575 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void add(List<DBSkillBonus> list, List<DBSkillBonus> addList) {
/* 584 */     if (addList == null)
/*     */       return; 
/* 586 */     for (DBSkillBonus bonusAdd : addList) {
/* 587 */       if (bonusAdd == null || 
/* 588 */         bonusAdd.bonusEntity == null) {
/*     */         continue;
/*     */       }
/* 591 */       bonusAdd = (DBSkillBonus)bonusAdd.clone();
/*     */       
/* 593 */       boolean found = false;
/*     */       
/* 595 */       for (DBSkillBonus bonus : list) {
/* 596 */         if (bonus == null)
/*     */           continue; 
/* 598 */         if (bonusAdd.type == bonus.type && 
/* 599 */           bonusAdd.bonusEntity.equals(bonus.bonusEntity)) {
/* 600 */           bonus.setValue(bonus.getValue() + bonusAdd.getValue());
/*     */           
/* 602 */           found = true;
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 609 */       if (!found) list.add(bonusAdd);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BonusDetail getBonusDetail() {
/* 618 */     BonusDetail bonus = new BonusDetail(this);
/*     */     
/* 620 */     return bonus;
/*     */   }
/*     */   
/*     */   public BonusDetail getBonusDetail(DBStashItem item) {
/* 624 */     BonusDetail bonus = new BonusDetail(item, this);
/*     */     
/* 626 */     return bonus;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBSkillBonus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */