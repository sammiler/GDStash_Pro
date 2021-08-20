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
/*     */ public class DBFormulaSet
/*     */ {
/*     */   private static final String TABLE_NAME = "GD_FORMULASET";
/*     */   private static final int ROW_FORMULASET_ID = 1;
/*     */   
/*     */   public static class Result
/*     */   {
/*  32 */     public double cost = 0.0D;
/*  33 */     public double cunning = 0.0D;
/*  34 */     public double physique = 0.0D;
/*  35 */     public double spirit = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private static ConcurrentHashMap<String, DBFormulaSet> hashBuffer = new ConcurrentHashMap<>();
/*     */   
/*     */   private String formulaSetID;
/*     */   
/*     */   private List<DBFormula> formulas;
/*     */   private ParameterSet parameterSet;
/*     */   
/*     */   public DBFormulaSet() {
/*  51 */     this.formulas = new LinkedList<>();
/*  52 */     this.parameterSet = null;
/*     */   }
/*     */   
/*     */   public DBFormulaSet(ARZRecord record) {
/*  56 */     this.formulaSetID = record.getFileName();
/*     */     
/*  58 */     this.formulas = record.getFormulaSetFormulaList();
/*  59 */     this.parameterSet = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormulaSetID() {
/*  67 */     return this.formulaSetID;
/*     */   }
/*     */   
/*     */   public List<DBFormula> getFormulaList() {
/*  71 */     return this.formulas;
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
/*  83 */     this.parameterSet = ps;
/*     */     
/*  85 */     for (DBFormula formula : this.formulas) {
/*  86 */       formula.setParameterSet(this.parameterSet);
/*     */     }
/*  88 */     return getResult();
/*     */   }
/*     */   
/*     */   private Result getResult() {
/*  92 */     if (this.parameterSet == null) return null;
/*     */     
/*  94 */     Result result = new Result();
/*     */     
/*  96 */     String itemClass = this.parameterSet.getItemClass();
/*     */     
/*  98 */     if (itemClass.equals("ArmorProtective_Head")) {
/*  99 */       result.cost = getValue("armorCostEquation");
/* 100 */       result.physique = getValue("headStrengthEquation");
/* 101 */       result.spirit = getValue("headIntelligenceEquation");
/*     */     } 
/*     */     
/* 104 */     if (itemClass.equals("ArmorProtective_Shoulders")) {
/* 105 */       result.cost = getValue("armorCostEquation");
/* 106 */       result.physique = getValue("shouldersStrengthEquation");
/*     */     } 
/*     */     
/* 109 */     if (itemClass.equals("ArmorProtective_Chest")) {
/* 110 */       result.cost = getValue("armorCostEquation");
/* 111 */       result.cunning = 0.0D;
/* 112 */       result.physique = getValue("chestStrengthEquation");
/* 113 */       result.spirit = getValue("chestIntelligenceEquation");
/*     */     } 
/*     */     
/* 116 */     if (itemClass.equals("ArmorProtective_Hands")) {
/* 117 */       result.cost = getValue("armorCostEquation");
/* 118 */       result.physique = getValue("handsStrengthEquation");
/*     */     } 
/*     */     
/* 121 */     if (itemClass.equals("ArmorProtective_Legs")) {
/* 122 */       result.cost = getValue("armorCostEquation");
/* 123 */       result.physique = getValue("legsStrengthEquation");
/*     */     } 
/*     */     
/* 126 */     if (itemClass.equals("ArmorProtective_Feet")) {
/* 127 */       result.cost = getValue("armorCostEquation");
/* 128 */       result.physique = getValue("feetStrengthEquation");
/*     */     } 
/*     */     
/* 131 */     if (itemClass.equals("ArmorProtective_Waist")) {
/* 132 */       result.cost = getValue("armorCostEquation");
/* 133 */       result.physique = getValue("waistStrengthEquation");
/*     */     } 
/*     */     
/* 136 */     if (itemClass.equals("ArmorJewelry_Amulet")) {
/* 137 */       result.cost = getValue("jewelryCostEquation");
/* 138 */       result.spirit = getValue("amuletIntelligenceEquation");
/*     */     } 
/*     */     
/* 141 */     if (itemClass.equals("ArmorJewelry_Medal")) {
/* 142 */       result.cost = getValue("jewelryCostEquation");
/* 143 */       result.spirit = getValue("amuletIntelligenceEquation");
/*     */     } 
/*     */     
/* 146 */     if (itemClass.equals("ArmorJewelry_Ring")) {
/* 147 */       result.cost = getValue("jewelryCostEquation");
/* 148 */       result.spirit = getValue("ringIntelligenceEquation");
/*     */     } 
/*     */     
/* 151 */     if (itemClass.equals("WeaponArmor_Offhand")) {
/* 152 */       result.cost = getValue("offhandCostEquation");
/* 153 */       result.spirit = getValue("offhandIntelligenceEquation");
/*     */     } 
/*     */     
/* 156 */     if (itemClass.equals("WeaponArmor_Shield")) {
/* 157 */       result.cost = getValue("shieldCostEquation");
/* 158 */       result.physique = getValue("shieldStrengthEquation");
/*     */     } 
/*     */     
/* 161 */     if (itemClass.equals("WeaponMelee_Axe")) {
/* 162 */       result.cost = getValue("weaponCostEquation");
/* 163 */       result.physique = getValue("axeStrengthEquation");
/*     */     } 
/*     */     
/* 166 */     if (itemClass.equals("WeaponMelee_Mace")) {
/* 167 */       result.cost = getValue("weaponCostEquation");
/* 168 */       result.physique = getValue("maceStrengthEquation");
/*     */     } 
/*     */     
/* 171 */     if (itemClass.equals("WeaponMelee_Sword")) {
/* 172 */       result.cost = getValue("weaponCostEquation");
/* 173 */       result.cunning = getValue("swordDexterityEquation");
/*     */     } 
/*     */     
/* 176 */     if (itemClass.equals("WeaponMelee_Dagger")) {
/* 177 */       result.cost = getValue("weaponCostEquation");
/* 178 */       result.cunning = getValue("daggerDexterityEquation");
/* 179 */       result.spirit = getValue("daggerIntelligenceEquation");
/*     */     } 
/*     */     
/* 182 */     if (itemClass.equals("WeaponMelee_Scepter")) {
/* 183 */       result.cost = getValue("weaponCostEquation");
/* 184 */       result.physique = getValue("scepterStrengthEquation");
/* 185 */       result.spirit = getValue("scepterIntelligenceEquation");
/*     */     } 
/*     */     
/* 188 */     if (itemClass.equals("WeaponHunting_Ranged1h")) {
/* 189 */       result.cost = getValue("weaponRangedCostEquation");
/* 190 */       result.cunning = getValue("ranged1hDexterityEquation");
/*     */     } 
/*     */     
/* 193 */     if (itemClass.equals("WeaponMelee_Axe2h")) {
/* 194 */       result.cost = getValue("weaponMelee2hCostEquation");
/* 195 */       result.physique = getValue("melee2hStrengthEquation");
/*     */     } 
/*     */     
/* 198 */     if (itemClass.equals("WeaponMelee_Mace2h")) {
/* 199 */       result.cost = getValue("weaponMelee2hCostEquation");
/* 200 */       result.physique = getValue("melee2hStrengthEquation");
/*     */     } 
/*     */     
/* 203 */     if (itemClass.equals("WeaponHunting_Spear")) {
/* 204 */       result.cost = getValue("weaponCostEquation");
/* 205 */       result.physique = getValue("maceStrengthEquation");
/*     */     } 
/*     */     
/* 208 */     if (itemClass.equals("WeaponMagical_Staff")) {
/*     */       
/* 210 */       result.cost = getValue("weaponCostEquation");
/* 211 */       result.cunning = getValue("daggerDexterityEquation");
/* 212 */       result.spirit = getValue("daggerIntelligenceEquation");
/*     */     } 
/*     */     
/* 215 */     if (itemClass.equals("WeaponMelee_Sword2h")) {
/* 216 */       result.cost = getValue("weaponMelee2hCostEquation");
/* 217 */       result.physique = getValue("melee2hStrengthEquation");
/*     */     } 
/*     */     
/* 220 */     if (itemClass.equals("WeaponHunting_Ranged2h")) {
/* 221 */       result.cost = getValue("weaponRanged2hCostEquation");
/* 222 */       result.cunning = getValue("ranged2hDexterityEquation");
/*     */     } 
/*     */     
/* 225 */     return result;
/*     */   }
/*     */   
/*     */   private double getValue(String formulaID) {
/* 229 */     double value = 0.0D;
/*     */     
/* 231 */     for (DBFormula formula : this.formulas)
/* 232 */     { if (formula.getFormulaID().equals(formulaID))
/*     */       { try {
/* 234 */           value = formula.getValue();
/*     */           break;
/* 236 */         } catch (IllegalStateException ex) {
/* 237 */           value = 0.0D;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 244 */         return value; }  }  return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearBuffer() {
/* 252 */     hashBuffer.clear();
/*     */   }
/*     */   
/*     */   public static void createTables() throws SQLException {
/* 256 */     String dropTable = "DROP TABLE GD_FORMULASET";
/* 257 */     String createTable = "CREATE TABLE GD_FORMULASET (FORMULASET_ID      VARCHAR(256) NOT NULL, PRIMARY KEY (FORMULASET_ID))";
/*     */ 
/*     */ 
/*     */     
/* 261 */     try (Connection conn = GDDBData.getConnection()) {
/* 262 */       boolean auto = conn.getAutoCommit();
/* 263 */       conn.setAutoCommit(false);
/*     */       
/* 265 */       try (Statement st = conn.createStatement()) {
/* 266 */         if (GDDBUtil.tableExists(conn, "GD_FORMULASET")) {
/* 267 */           st.execute(dropTable);
/*     */         }
/* 269 */         st.execute(createTable);
/* 270 */         st.close();
/*     */         
/* 272 */         conn.commit();
/*     */       
/*     */       }
/* 275 */       catch (SQLException ex) {
/* 276 */         conn.rollback();
/*     */         
/* 278 */         Object[] args = { "GD_FORMULASET" };
/* 279 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 281 */         GDMsgLogger.addError(msg);
/*     */         
/* 283 */         throw ex;
/*     */       } finally {
/*     */         
/* 286 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */     
/* 290 */     DBFormula.createTable();
/*     */   }
/*     */   
/*     */   public static void delete(String formulaSetID) throws SQLException {
/* 294 */     String deleteEntry = "DELETE FROM GD_FORMULASET WHERE FORMULASET_ID = ?";
/*     */     
/* 296 */     try (Connection conn = GDDBData.getConnection()) {
/* 297 */       boolean auto = conn.getAutoCommit();
/* 298 */       conn.setAutoCommit(false);
/*     */       
/* 300 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 301 */         ps.setString(1, formulaSetID);
/* 302 */         ps.executeUpdate();
/* 303 */         ps.close();
/*     */         
/* 305 */         DBFormula.delete(conn, formulaSetID);
/*     */         
/* 307 */         conn.commit();
/*     */       }
/* 309 */       catch (SQLException ex) {
/* 310 */         conn.rollback();
/*     */         
/* 312 */         Object[] args = { formulaSetID, "GD_FORMULASET" };
/* 313 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*     */         
/* 315 */         GDMsgLogger.addError(msg);
/* 316 */         GDMsgLogger.addError(ex);
/*     */         
/* 318 */         throw ex;
/*     */       } finally {
/*     */         
/* 321 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 324 */     } catch (SQLException ex) {
/* 325 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 330 */     DBFormulaSet entry = get(record.getFileName());
/*     */     
/* 332 */     if (entry != null)
/*     */       return; 
/* 334 */     DBFormulaSet formulaSet = new DBFormulaSet(record);
/*     */     
/* 336 */     String insert = "INSERT INTO GD_FORMULASET VALUES (?)";
/*     */     
/* 338 */     try (Connection conn = GDDBData.getConnection()) {
/* 339 */       boolean auto = conn.getAutoCommit();
/* 340 */       conn.setAutoCommit(false);
/*     */       
/* 342 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 343 */         ps.setString(1, record.getFileName());
/*     */         
/* 345 */         ps.executeUpdate();
/* 346 */         ps.close();
/*     */         
/* 348 */         conn.commit();
/*     */         
/* 350 */         DBFormula.insert(conn, formulaSet);
/*     */       }
/* 352 */       catch (SQLException ex) {
/* 353 */         conn.rollback();
/*     */         
/* 355 */         Object[] args = { formulaSet.getFormulaSetID(), "GD_FORMULASET" };
/* 356 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */         
/* 358 */         GDMsgLogger.addError(msg);
/* 359 */         GDMsgLogger.addError(ex);
/*     */       } finally {
/*     */         
/* 362 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBFormulaSet get(String formulaSetID) {
/* 368 */     DBFormulaSet set = null;
/*     */     
/* 370 */     set = hashBuffer.get(formulaSetID);
/*     */     
/* 372 */     if (set == null) {
/* 373 */       set = getDB(formulaSetID);
/*     */       
/* 375 */       if (set != null) hashBuffer.put(set.formulaSetID, set);
/*     */     
/*     */     } 
/* 378 */     return set;
/*     */   }
/*     */   
/*     */   private static DBFormulaSet getDB(String formulaSetID) {
/* 382 */     DBFormulaSet set = null;
/*     */     
/* 384 */     String command = "SELECT * FROM GD_FORMULASET WHERE FORMULASET_ID = ?";
/*     */     
/* 386 */     try(Connection conn = GDDBData.getConnection(); 
/* 387 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 388 */       ps.setString(1, formulaSetID);
/*     */       
/* 390 */       try (ResultSet rs = ps.executeQuery()) {
/* 391 */         List<DBFormulaSet> list = wrap(rs);
/*     */         
/* 393 */         if (list.isEmpty()) { set = null; }
/* 394 */         else { set = list.get(0); }
/*     */         
/* 396 */         conn.commit();
/*     */       }
/* 398 */       catch (SQLException ex) {
/* 399 */         throw ex;
/*     */       }
/*     */     
/* 402 */     } catch (SQLException ex) {
/* 403 */       Object[] args = { formulaSetID, "GD_FORMULASET" };
/* 404 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 406 */       GDMsgLogger.addError(msg);
/* 407 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 410 */     return set;
/*     */   }
/*     */   
/*     */   public static List<DBFormulaSet> getByFormulaSetIDs(List<String> formulaSetIDs) {
/* 414 */     List<DBFormulaSet> list = new LinkedList<>();
/*     */     
/* 416 */     for (String formulaSetID : formulaSetIDs) {
/* 417 */       DBFormulaSet set = get(formulaSetID);
/* 418 */       if (set != null) list.add(set);
/*     */     
/*     */     } 
/* 421 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBFormulaSet> wrap(ResultSet rs) throws SQLException {
/* 425 */     LinkedList<DBFormulaSet> list = new LinkedList<>();
/*     */     
/* 427 */     while (rs.next()) {
/* 428 */       DBFormulaSet set = new DBFormulaSet();
/*     */       
/* 430 */       set.formulaSetID = rs.getString(1);
/*     */       
/* 432 */       if (set.formulaSetID != null) {
/* 433 */         set.formulas = DBFormula.getByFormulaSetID(set.formulaSetID);
/*     */       }
/*     */       
/* 436 */       list.add(set);
/*     */     } 
/*     */     
/* 439 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBFormulaSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */