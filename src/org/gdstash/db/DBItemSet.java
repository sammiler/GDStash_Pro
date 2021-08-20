/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.file.ARCDecompress;
/*     */ import org.gdstash.file.ARZRecord;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.util.GDColor;
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
/*     */ public class DBItemSet
/*     */ {
/*     */   private static final String TABLE_NAME = "GD_ITEMSET";
/*     */   private static final int ROW_ITEMSET_ID = 1;
/*     */   private static final int ROW_NAME = 2;
/*     */   private static final int ROW_DESCRIPTION = 3;
/*     */   private static final int ROW_SKILL_MOD_LEVEL = 4;
/*     */   private String itemSetID;
/*     */   private String name;
/*     */   private String description;
/*     */   private int skillModifierLevel;
/*  54 */   private List<String> itemIDs = new LinkedList<>();
/*     */   private String rarity;
/*  56 */   private List<DBStat> stats = new LinkedList<>(); private int reqLevel;
/*  57 */   private List<DBSkillBonus> bonuses = new LinkedList<>();
/*  58 */   private List<DBSkillModifier> skillModifiers = new LinkedList<>();
/*     */   
/*     */   public DBItemSet() {}
/*     */   
/*     */   public DBItemSet(String name) {
/*  63 */     this();
/*     */     
/*  65 */     this.name = name;
/*     */   }
/*     */   
/*     */   public DBItemSet(ARZRecord record) {
/*  69 */     this();
/*     */     
/*  71 */     this.itemSetID = record.getFileName();
/*     */     
/*  73 */     setName(record.getItemSetNameTag());
/*  74 */     setDescription(record.getItemSetDescriptionTag());
/*     */     
/*  76 */     this.itemIDs = record.getItemSetItemIDList();
/*  77 */     this.skillModifierLevel = record.getItemSetSkillModifierLevel();
/*     */     
/*  79 */     this.rarity = record.getRarity();
/*  80 */     this.reqLevel = record.getRequiredLevel();
/*  81 */     this.stats = record.getDBStatList();
/*  82 */     this.bonuses = record.dbSkillBonuses;
/*  83 */     this.skillModifiers = record.getSkillModifierList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getItemSetID() {
/*  91 */     return this.itemSetID;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  95 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  99 */     return this.description;
/*     */   }
/*     */   
/*     */   public List<String> getItemIDList() {
/* 103 */     return this.itemIDs;
/*     */   }
/*     */   
/*     */   public List<DBSkillModifier> getSkillModifiers(int level) {
/* 107 */     if (level == this.skillModifierLevel) return this.skillModifiers;
/*     */     
/* 109 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setName(String name) {
/* 117 */     this.name = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_ITEMS, name, false);
/*     */   }
/*     */   
/*     */   private void setDescription(String description) {
/* 121 */     this.description = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_ITEMS, description, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTables() throws SQLException {
/* 129 */     String dropTable = "DROP TABLE GD_ITEMSET";
/* 130 */     String createTable = "CREATE TABLE GD_ITEMSET (ITEMSET_ID      VARCHAR(256) NOT NULL, NAME            VARCHAR(256), DESCRIPTION     VARCHAR(1024), SKILL_MOD_LEVEL INT, PRIMARY KEY (ITEMSET_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     try (Connection conn = GDDBData.getConnection()) {
/* 138 */       boolean auto = conn.getAutoCommit();
/* 139 */       conn.setAutoCommit(false);
/*     */       
/* 141 */       try (Statement st = conn.createStatement()) {
/* 142 */         if (GDDBUtil.tableExists(conn, "GD_ITEMSET")) {
/* 143 */           st.execute(dropTable);
/*     */         }
/* 145 */         st.execute(createTable);
/* 146 */         st.close();
/*     */         
/* 148 */         conn.commit();
/*     */         
/* 150 */         DBItemSetAlloc.createTable(conn);
/* 151 */         DBStat.createItemSetTable(conn);
/* 152 */         DBSkillBonus.createItemSetTable(conn);
/* 153 */         DBSkillModifier.createItemSetTable(conn);
/*     */       }
/* 155 */       catch (SQLException ex) {
/* 156 */         conn.rollback();
/*     */         
/* 158 */         Object[] args = { "GD_ITEMSET" };
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
/*     */   public static void delete(String itemSetID) throws SQLException {
/* 172 */     String deleteEntry = "DELETE FROM GD_ITEMSET WHERE ITEMSET_ID = ?";
/*     */     
/* 174 */     try (Connection conn = GDDBData.getConnection()) {
/* 175 */       boolean auto = conn.getAutoCommit();
/* 176 */       conn.setAutoCommit(false);
/*     */       
/* 178 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 179 */         ps.setString(1, itemSetID);
/* 180 */         ps.executeUpdate();
/* 181 */         ps.close();
/*     */         
/* 183 */         DBItemSetAlloc.delete(conn, itemSetID);
/* 184 */         DBStat.deleteItemSet(conn, itemSetID);
/* 185 */         DBSkillBonus.deleteItemSet(conn, itemSetID);
/* 186 */         DBSkillModifier.deleteItemSet(conn, itemSetID);
/*     */         
/* 188 */         conn.commit();
/*     */       }
/* 190 */       catch (SQLException ex) {
/* 191 */         conn.rollback();
/*     */         
/* 193 */         Object[] args = { itemSetID, "GD_ITEMSET" };
/* 194 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*     */         
/* 196 */         GDMsgLogger.addError(msg);
/* 197 */         GDMsgLogger.addError(ex);
/*     */         
/* 199 */         throw ex;
/*     */       } finally {
/*     */         
/* 202 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 205 */     } catch (SQLException ex) {
/* 206 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 211 */     DBItemSet entry = get(record.getFileName());
/*     */     
/* 213 */     if (entry != null)
/*     */       return; 
/* 215 */     DBItemSet itemSet = new DBItemSet(record);
/*     */ 
/*     */     
/* 218 */     if (itemSet.itemIDs == null)
/* 219 */       return;  if (itemSet.itemIDs.isEmpty())
/*     */       return; 
/* 221 */     String insert = "INSERT INTO GD_ITEMSET VALUES (?,?,?,?)";
/*     */     
/* 223 */     try (Connection conn = GDDBData.getConnection()) {
/* 224 */       boolean auto = conn.getAutoCommit();
/* 225 */       conn.setAutoCommit(false);
/*     */       
/* 227 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/*     */         
/* 229 */         ps.setString(1, itemSet.itemSetID);
/* 230 */         ps.setString(2, itemSet.name);
/* 231 */         ps.setString(3, itemSet.description);
/* 232 */         ps.setInt(4, itemSet.skillModifierLevel);
/*     */         
/* 234 */         ps.executeUpdate();
/*     */         
/* 236 */         ps.close();
/*     */         
/* 238 */         DBItemSetAlloc.insert(conn, itemSet);
/* 239 */         DBStat.insertItemSet(conn, itemSet.itemSetID, itemSet.stats);
/* 240 */         DBSkillBonus.insertItemSet(conn, itemSet.itemSetID, itemSet.bonuses);
/* 241 */         DBSkillModifier.insertItemSet(conn, itemSet.itemSetID, itemSet.skillModifiers);
/*     */         
/* 243 */         conn.commit();
/*     */       }
/* 245 */       catch (SQLException ex) {
/* 246 */         conn.rollback();
/*     */         
/* 248 */         Object[] args = { record.getFileName(), "GD_ITEMSET" };
/* 249 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */         
/* 251 */         GDMsgLogger.addLowError(msg);
/* 252 */         GDMsgLogger.addLowError(ex);
/*     */       } finally {
/*     */         
/* 255 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBItemSet get(String itemSetID) {
/* 261 */     DBItemSet set = new DBItemSet();
/*     */     
/* 263 */     String command = "SELECT * FROM GD_ITEMSET WHERE ITEMSET_ID = ?";
/*     */     
/* 265 */     try(Connection conn = GDDBData.getConnection(); 
/* 266 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 267 */       ps.setString(1, itemSetID);
/*     */       
/* 269 */       try (ResultSet rs = ps.executeQuery()) {
/* 270 */         List<DBItemSet> list = wrap(rs);
/*     */         
/* 272 */         if (list.isEmpty()) { set = null; }
/* 273 */         else { set = list.get(0); }
/*     */         
/* 275 */         conn.commit();
/*     */       }
/* 277 */       catch (SQLException ex) {
/* 278 */         throw ex;
/*     */       }
/*     */     
/* 281 */     } catch (SQLException ex) {
/* 282 */       Object[] args = { itemSetID, "GD_ITEMSET" };
/* 283 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 285 */       GDMsgLogger.addError(msg);
/* 286 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 289 */     return set;
/*     */   }
/*     */   
/*     */   public static List<DBItemSet> getAll() {
/* 293 */     List<DBItemSet> list = null;
/*     */     
/* 295 */     String command = "SELECT * FROM GD_ITEMSET ORDER BY NAME";
/*     */     
/* 297 */     try(Connection conn = GDDBData.getConnection(); 
/* 298 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 299 */       try (ResultSet rs = ps.executeQuery()) {
/* 300 */         list = wrap(rs);
/*     */         
/* 302 */         conn.commit();
/*     */       }
/* 304 */       catch (SQLException ex) {
/* 305 */         throw ex;
/*     */       }
/*     */     
/* 308 */     } catch (SQLException ex) {
/* 309 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_READ_ITEMSET_ALL"));
/* 310 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 313 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBItemSet> wrap(ResultSet rs) throws SQLException {
/* 317 */     LinkedList<DBItemSet> list = new LinkedList<>();
/*     */     
/* 319 */     while (rs.next()) {
/* 320 */       DBItemSet set = new DBItemSet();
/*     */       
/* 322 */       set.itemSetID = rs.getString(1);
/* 323 */       set.name = rs.getString(2);
/* 324 */       set.description = rs.getString(3);
/* 325 */       set.skillModifierLevel = rs.getInt(4);
/*     */       
/* 327 */       set.itemIDs = DBItemSetAlloc.getByItemSetID(set.itemSetID);
/* 328 */       set.stats = DBStat.getItemSet(set.itemSetID);
/* 329 */       set.bonuses = DBSkillBonus.getItemSet(set.itemSetID);
/* 330 */       set.skillModifiers = DBSkillModifier.getItemSet(set.itemSetID);
/*     */ 
/*     */       
/* 333 */       String rarity = null;
/* 334 */       boolean matchRarity = true;
/* 335 */       int reqLevel = 0;
/* 336 */       boolean matchLevel = true;
/*     */       
/* 338 */       if (set.itemIDs != null) {
/* 339 */         List<DBItem.SetInfo> infos = DBItem.getSetInfoByItemIDs(set.itemIDs);
/*     */         
/* 341 */         for (DBItem.SetInfo info : infos) {
/* 342 */           if (info == null)
/*     */             continue; 
/* 344 */           if (rarity == null) {
/* 345 */             rarity = info.rarity;
/*     */           } else {
/* 347 */             matchRarity = (matchRarity && rarity.equals(info.rarity));
/*     */           } 
/*     */           
/* 350 */           if (reqLevel == 0) {
/* 351 */             reqLevel = info.level; continue;
/*     */           } 
/* 353 */           matchLevel = (reqLevel == info.level);
/*     */         } 
/*     */ 
/*     */         
/* 357 */         if (matchRarity) set.rarity = rarity; 
/* 358 */         if (matchLevel) set.reqLevel = reqLevel;
/*     */       
/*     */       } 
/* 361 */       list.add(set);
/*     */     } 
/*     */     
/* 364 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<List<DBStat>> getBonusesPerLevel() {
/* 372 */     if (this.itemIDs == null) return null; 
/* 373 */     if (this.itemIDs.size() == 0) return null;
/*     */     
/* 375 */     ArrayList<List<DBStat>> arrStat = new ArrayList<>(this.itemIDs.size());
/*     */     
/* 377 */     for (int i = 0; i < this.itemIDs.size(); i++) {
/* 378 */       arrStat.add(DBStat.getStatsForExactLevel(this.stats, i + 1));
/*     */     }
/*     */     
/* 381 */     compactBonuses(arrStat);
/*     */     
/* 383 */     return arrStat;
/*     */   }
/*     */   
/*     */   private static void compactBonuses(ArrayList<List<DBStat>> arrStat) {
/* 387 */     for (int i = 0; i < arrStat.size(); i++) {
/* 388 */       Iterator<DBStat> iter = ((List<DBStat>)arrStat.get(i)).iterator();
/* 389 */       while (iter.hasNext()) {
/* 390 */         DBStat stat1 = iter.next();
/*     */         int j;
/* 392 */         for (j = 0; j < i; j++) {
/* 393 */           for (DBStat stat2 : arrStat.get(j)) {
/* 394 */             if (stat1.getStatType().equals(stat2.getStatType())) {
/* 395 */               iter.remove();
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ArrayList<List<DBSkillBonus>> getItemSetSkillBonusesPerLevel() {
/* 404 */     if (this.itemIDs == null) return null; 
/* 405 */     if (this.itemIDs.isEmpty()) return null;
/*     */ 
/*     */     
/* 408 */     ArrayList<List<DBSkillBonus>> arrBonus = new ArrayList<>(this.itemIDs.size());
/*     */     
/* 410 */     for (int i = 0; i < this.itemIDs.size(); i++) {
/* 411 */       arrBonus.add(new LinkedList<>());
/*     */       
/* 413 */       for (DBSkillBonus bonus : this.bonuses) {
/* 414 */         int value = bonus.getValueForLevel(i);
/*     */         
/* 416 */         if (value > 0) {
/* 417 */           bonus = (DBSkillBonus)bonus.clone();
/* 418 */           bonus.setValue(value);
/*     */           
/* 420 */           ((List<DBSkillBonus>)arrBonus.get(i)).add(bonus);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 425 */     compactSkillBonuses(arrBonus);
/*     */     
/* 427 */     return arrBonus;
/*     */   }
/*     */   
/*     */   private static void compactSkillBonuses(ArrayList<List<DBSkillBonus>> arrBonus) {
/* 431 */     for (int i = 0; i < arrBonus.size(); i++) {
/* 432 */       Iterator<DBSkillBonus> iter = ((List<DBSkillBonus>)arrBonus.get(i)).iterator();
/* 433 */       while (iter.hasNext()) {
/* 434 */         DBSkillBonus bonus1 = iter.next();
/*     */         int j;
/* 436 */         for (j = 0; j < i; j++) {
/* 437 */           for (DBSkillBonus bonus2 : arrBonus.get(j)) {
/* 438 */             if (bonus1.getEntity() == null) {
/* 439 */               if (bonus2.getEntity() == null) {
/* 440 */                 if (bonus1.getValue() == bonus2.getValue()) {
/* 441 */                   iter.remove();
/*     */                 }
/*     */                 break;
/*     */               } 
/*     */               continue;
/*     */             } 
/* 447 */             if (bonus1.getEntity().equals(bonus2.getEntity())) {
/* 448 */               if (bonus1.getValue() == bonus2.getValue()) {
/* 449 */                 iter.remove();
/*     */               }
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 462 */     String s = this.name;
/*     */     
/* 464 */     if (this.reqLevel != 0) {
/* 465 */       Object[] args = { String.format("%03d", new Object[] { Integer.valueOf(this.reqLevel) }) };
/* 466 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_LEVEL_NUM", args);
/*     */       
/* 468 */       s = s + " [" + msg + "]";
/*     */     } 
/*     */     
/* 471 */     if (this.rarity != null) {
/* 472 */       if (this.rarity.equals("Epic")) {
/* 473 */         s = "<html>" + GDColor.HTML_COLOR_EPIC + s + "</font>" + "</html>";
/*     */       }
/*     */       
/* 476 */       if (this.rarity.equals("Legendary")) {
/* 477 */         s = "<html>" + GDColor.HTML_COLOR_LEGENDARY + s + "</font>" + "</html>";
/*     */       }
/*     */     } 
/*     */     
/* 481 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBItemSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */