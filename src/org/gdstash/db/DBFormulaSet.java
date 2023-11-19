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
/*     */ public class DBFormulaSet
/*     */ {
/*     */   private static final String TABLE_NAME = "GD_FORMULASET";
/*     */   private static final int ROW_FORMULASET_ID = 1;
/*     */   
/*     */   public static class Result
/*     */   {
/*  33 */     public double cost = 0.0D;
/*  34 */     public double cunning = 0.0D;
/*  35 */     public double physique = 0.0D;
/*  36 */     public double spirit = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   private static ConcurrentHashMap<String, DBFormulaSet> hashBuffer = new ConcurrentHashMap<>();
/*     */   
/*     */   private String formulaSetID;
/*     */   
/*     */   private List<DBFormula> formulas;
/*     */   private ParameterSet parameterSet;
/*     */   
/*     */   public DBFormulaSet() {
/*  52 */     this.formulas = new LinkedList<>();
/*  53 */     this.parameterSet = null;
/*     */   }
/*     */   
/*     */   public DBFormulaSet(ARZRecord record) {
/*  57 */     this.formulaSetID = record.getFileName();
/*     */     
/*  59 */     this.formulas = record.getFormulaSetFormulaList();
/*  60 */     this.parameterSet = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormulaSetID() {
/*  68 */     return this.formulaSetID;
/*     */   }
/*     */   
/*     */   public List<DBFormula> getFormulaList() {
/*  72 */     return this.formulas;
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
/*     */   public Result getResult(ParameterSet ps) {
/*  84 */     this.parameterSet = ps;
/*     */     
/*  86 */     for (DBFormula formula : this.formulas) {
/*  87 */       formula.setParameterSet(this.parameterSet);
/*     */     }
/*  89 */     return getResult();
/*     */   }
/*     */   
/*     */   private Result getResult() {
/*  93 */     if (this.parameterSet == null) return null;
/*     */     
/*  95 */     Result result = new Result();
/*     */     
/*  97 */     String itemClass = this.parameterSet.getItemClass();
/*     */     
/*  99 */     if (itemClass.equals("ArmorProtective_Head")) {
/* 100 */       result.cost = getValue("armorCostEquation");
/* 101 */       result.physique = getValue("headStrengthEquation");
/* 102 */       result.spirit = getValue("headIntelligenceEquation");
/*     */     } 
/*     */     
/* 105 */     if (itemClass.equals("ArmorProtective_Shoulders")) {
/* 106 */       result.cost = getValue("armorCostEquation");
/* 107 */       result.physique = getValue("shouldersStrengthEquation");
/*     */     } 
/*     */     
/* 110 */     if (itemClass.equals("ArmorProtective_Chest")) {
/* 111 */       result.cost = getValue("armorCostEquation");
/* 112 */       result.cunning = 0.0D;
/* 113 */       result.physique = getValue("chestStrengthEquation");
/* 114 */       result.spirit = getValue("chestIntelligenceEquation");
/*     */     } 
/*     */     
/* 117 */     if (itemClass.equals("ArmorProtective_Hands")) {
/* 118 */       result.cost = getValue("armorCostEquation");
/* 119 */       result.physique = getValue("handsStrengthEquation");
/*     */     } 
/*     */     
/* 122 */     if (itemClass.equals("ArmorProtective_Legs")) {
/* 123 */       result.cost = getValue("armorCostEquation");
/* 124 */       result.physique = getValue("legsStrengthEquation");
/*     */     } 
/*     */     
/* 127 */     if (itemClass.equals("ArmorProtective_Feet")) {
/* 128 */       result.cost = getValue("armorCostEquation");
/* 129 */       result.physique = getValue("feetStrengthEquation");
/*     */     } 
/*     */     
/* 132 */     if (itemClass.equals("ArmorProtective_Waist")) {
/* 133 */       result.cost = getValue("armorCostEquation");
/* 134 */       result.physique = getValue("waistStrengthEquation");
/*     */     } 
/*     */     
/* 137 */     if (itemClass.equals("ArmorJewelry_Amulet")) {
/* 138 */       result.cost = getValue("jewelryCostEquation");
/* 139 */       result.spirit = getValue("amuletIntelligenceEquation");
/*     */     } 
/*     */     
/* 142 */     if (itemClass.equals("ArmorJewelry_Medal")) {
/* 143 */       result.cost = getValue("jewelryCostEquation");
/* 144 */       result.spirit = getValue("amuletIntelligenceEquation");
/*     */     } 
/*     */     
/* 147 */     if (itemClass.equals("ArmorJewelry_Ring")) {
/* 148 */       result.cost = getValue("jewelryCostEquation");
/* 149 */       result.spirit = getValue("ringIntelligenceEquation");
/*     */     } 
/*     */     
/* 152 */     if (itemClass.equals("WeaponArmor_Offhand")) {
/* 153 */       result.cost = getValue("offhandCostEquation");
/* 154 */       result.spirit = getValue("offhandIntelligenceEquation");
/*     */     } 
/*     */     
/* 157 */     if (itemClass.equals("WeaponArmor_Shield")) {
/* 158 */       result.cost = getValue("shieldCostEquation");
/* 159 */       result.physique = getValue("shieldStrengthEquation");
/*     */     } 
/*     */     
/* 162 */     if (itemClass.equals("WeaponMelee_Axe")) {
/* 163 */       result.cost = getValue("weaponCostEquation");
/* 164 */       result.physique = getValue("axeStrengthEquation");
/*     */     } 
/*     */     
/* 167 */     if (itemClass.equals("WeaponMelee_Mace")) {
/* 168 */       result.cost = getValue("weaponCostEquation");
/* 169 */       result.physique = getValue("maceStrengthEquation");
/*     */     } 
/*     */     
/* 172 */     if (itemClass.equals("WeaponMelee_Sword")) {
/* 173 */       result.cost = getValue("weaponCostEquation");
/* 174 */       result.cunning = getValue("swordDexterityEquation");
/*     */     } 
/*     */     
/* 177 */     if (itemClass.equals("WeaponMelee_Dagger")) {
/* 178 */       result.cost = getValue("weaponCostEquation");
/* 179 */       result.cunning = getValue("daggerDexterityEquation");
/* 180 */       result.spirit = getValue("daggerIntelligenceEquation");
/*     */     } 
/*     */     
/* 183 */     if (itemClass.equals("WeaponMelee_Scepter")) {
/* 184 */       result.cost = getValue("weaponCostEquation");
/* 185 */       result.physique = getValue("scepterStrengthEquation");
/* 186 */       result.spirit = getValue("scepterIntelligenceEquation");
/*     */     } 
/*     */     
/* 189 */     if (itemClass.equals("WeaponHunting_Ranged1h")) {
/* 190 */       result.cost = getValue("weaponRangedCostEquation");
/* 191 */       result.cunning = getValue("ranged1hDexterityEquation");
/*     */     } 
/*     */     
/* 194 */     if (itemClass.equals("WeaponMelee_Axe2h")) {
/* 195 */       result.cost = getValue("weaponMelee2hCostEquation");
/* 196 */       result.physique = getValue("melee2hStrengthEquation");
/*     */     } 
/*     */     
/* 199 */     if (itemClass.equals("WeaponMelee_Mace2h")) {
/* 200 */       result.cost = getValue("weaponMelee2hCostEquation");
/* 201 */       result.physique = getValue("melee2hStrengthEquation");
/*     */     } 
/*     */     
/* 204 */     if (itemClass.equals("WeaponHunting_Spear")) {
/* 205 */       result.cost = getValue("weaponCostEquation");
/* 206 */       result.physique = getValue("maceStrengthEquation");
/*     */     } 
/*     */     
/* 209 */     if (itemClass.equals("WeaponMagical_Staff")) {
/*     */       
/* 211 */       result.cost = getValue("weaponCostEquation");
/* 212 */       result.cunning = getValue("daggerDexterityEquation");
/* 213 */       result.spirit = getValue("daggerIntelligenceEquation");
/*     */     } 
/*     */     
/* 216 */     if (itemClass.equals("WeaponMelee_Sword2h")) {
/* 217 */       result.cost = getValue("weaponMelee2hCostEquation");
/* 218 */       result.physique = getValue("melee2hStrengthEquation");
/*     */     } 
/*     */     
/* 221 */     if (itemClass.equals("WeaponHunting_Ranged2h")) {
/* 222 */       result.cost = getValue("weaponRanged2hCostEquation");
/* 223 */       result.cunning = getValue("ranged2hDexterityEquation");
/*     */     } 
/*     */     
/* 226 */     return result;
/*     */   }
/*     */   
/*     */   private double getValue(String formulaID) {
/* 230 */     double value = 0.0D;
/*     */     
/* 232 */     for (DBFormula formula : this.formulas)
/* 233 */     { if (formula.getFormulaID().equals(formulaID))
/*     */       { try {
/* 235 */           value = formula.getValue();
/*     */           break;
/* 237 */         } catch (IllegalStateException ex) {
/* 238 */           value = 0.0D;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 245 */         return value; }  }  return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearBuffer() {
/* 253 */     hashBuffer.clear();
/*     */   }
/*     */   
/*     */   public static void createTables() throws SQLException {
/* 257 */     String dropTable = "DROP TABLE GD_FORMULASET";
/* 258 */     String createTable = "CREATE TABLE GD_FORMULASET (FORMULASET_ID      VARCHAR(256) NOT NULL, PRIMARY KEY (FORMULASET_ID))";
/*     */ 
/*     */ 
/*     */     
/* 262 */     try (Connection conn = GDDBData.getConnection()) {
/* 263 */       boolean auto = conn.getAutoCommit();
/* 264 */       conn.setAutoCommit(false);
/*     */       
/* 266 */       try (Statement st = conn.createStatement()) {
/* 267 */         if (GDDBUtil.tableExists(conn, "GD_FORMULASET")) {
/* 268 */           st.execute(dropTable);
/*     */         }
/* 270 */         st.execute(createTable);
/* 271 */         st.close();
/*     */         
/* 273 */         conn.commit();
/*     */       
/*     */       }
/* 276 */       catch (SQLException ex) {
/* 277 */         conn.rollback();
/*     */         
/* 279 */         Object[] args = { "GD_FORMULASET" };
/* 280 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 282 */         GDMsgLogger.addError(msg);
/*     */         
/* 284 */         throw ex;
/*     */       } finally {
/*     */         
/* 287 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */     
/* 291 */     DBFormula.createTable();
/*     */   }
/*     */   
/*     */   public static void delete(String formulaSetID) throws SQLException {
/* 295 */     String deleteEntry = "DELETE FROM GD_FORMULASET WHERE FORMULASET_ID = ?";
/*     */     
/* 297 */     try (Connection conn = GDDBData.getConnection()) {
/* 298 */       boolean auto = conn.getAutoCommit();
/* 299 */       conn.setAutoCommit(false);
/*     */       
/* 301 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 302 */         ps.setString(1, formulaSetID);
/* 303 */         ps.executeUpdate();
/* 304 */         ps.close();
/*     */         
/* 306 */         DBFormula.delete(conn, formulaSetID);
/*     */         
/* 308 */         conn.commit();
/*     */       }
/* 310 */       catch (SQLException ex) {
/* 311 */         conn.rollback();
/*     */         
/* 313 */         Object[] args = { formulaSetID, "GD_FORMULASET" };
/* 314 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*     */         
/* 316 */         GDMsgLogger.addError(msg);
/* 317 */         GDMsgLogger.addError(ex);
/*     */         
/* 319 */         throw ex;
/*     */       } finally {
/*     */         
/* 322 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 325 */     } catch (SQLException ex) {
/* 326 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 331 */     DBFormulaSet entry = get(record.getFileName());
/*     */     
/* 333 */     if (entry != null)
/*     */       return; 
/* 335 */     DBFormulaSet formulaSet = new DBFormulaSet(record);
/*     */     
/* 337 */     String insert = "INSERT INTO GD_FORMULASET VALUES (?)";
/*     */     
/* 339 */     try (Connection conn = GDDBData.getConnection()) {
/* 340 */       boolean auto = conn.getAutoCommit();
/* 341 */       conn.setAutoCommit(false);
/*     */       
/* 343 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 344 */         ps.setString(1, record.getFileName());
/*     */         
/* 346 */         ps.executeUpdate();
/* 347 */         ps.close();
/*     */         
/* 349 */         conn.commit();
/*     */         
/* 351 */         DBFormula.insert(conn, formulaSet);
/*     */       }
/* 353 */       catch (SQLException ex) {
/* 354 */         conn.rollback();
/*     */         
/* 356 */         Object[] args = { formulaSet.getFormulaSetID(), "GD_FORMULASET" };
/* 357 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */         
/* 359 */         GDMsgLogger.addError(msg);
/* 360 */         GDMsgLogger.addError(ex);
/*     */       } finally {
/*     */         
/* 363 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBFormulaSet get(String formulaSetID) {
/* 369 */     DBFormulaSet set = null;
/*     */     
/* 371 */     set = hashBuffer.get(formulaSetID);
/*     */     
/* 373 */     if (set == null) {
/* 374 */       set = getDB(formulaSetID);
/*     */       
/* 376 */       if (set != null) hashBuffer.put(set.formulaSetID, set);
/*     */     
/*     */     } 
/* 379 */     return set;
/*     */   }
/*     */   
/*     */   private static DBFormulaSet getDB(String formulaSetID) {
/* 383 */     DBFormulaSet set = null;
/*     */     
/* 385 */     String command = "SELECT * FROM GD_FORMULASET WHERE FORMULASET_ID = ?";
/*     */     
/* 387 */     try(Connection conn = GDDBData.getConnection(); 
/* 388 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 389 */       ps.setString(1, formulaSetID);
/*     */       
/* 391 */       try (ResultSet rs = ps.executeQuery()) {
/* 392 */         List<DBFormulaSet> list = wrap(rs);
/*     */         
/* 394 */         if (list.isEmpty()) { set = null; }
/* 395 */         else { set = list.get(0); }
/*     */         
/* 397 */         conn.commit();
/*     */       }
/* 399 */       catch (SQLException ex) {
/* 400 */         throw ex;
/*     */       }
/*     */     
/* 403 */     } catch (SQLException ex) {
/* 404 */       Object[] args = { formulaSetID, "GD_FORMULASET" };
/* 405 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 407 */       GDMsgLogger.addError(msg);
/* 408 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 411 */     return set;
/*     */   }
/*     */   
/*     */   public static List<DBFormulaSet> getByFormulaSetIDs(List<String> formulaSetIDs) {
/* 415 */     List<DBFormulaSet> list = new LinkedList<>();
/*     */     
/* 417 */     for (String formulaSetID : formulaSetIDs) {
/* 418 */       DBFormulaSet set = get(formulaSetID);
/* 419 */       if (set != null) list.add(set);
/*     */     
/*     */     } 
/* 422 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBFormulaSet> wrap(ResultSet rs) throws SQLException {
/* 426 */     LinkedList<DBFormulaSet> list = new LinkedList<>();
/*     */     
/* 428 */     while (rs.next()) {
/* 429 */       DBFormulaSet set = new DBFormulaSet();
/*     */       
/* 431 */       set.formulaSetID = rs.getString(1);
/*     */       
/* 433 */       if (set.formulaSetID != null) {
/* 434 */         set.formulas = DBFormula.getByFormulaSetID(set.formulaSetID);
/*     */       }
/*     */       
/* 437 */       list.add(set);
/*     */     } 
/*     */     
/* 440 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBFormulaSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */