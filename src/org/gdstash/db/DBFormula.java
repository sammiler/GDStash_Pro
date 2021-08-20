/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.objecthunter.exp4j.Expression;
/*     */ import net.objecthunter.exp4j.ExpressionBuilder;
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
/*     */ public class DBFormula
/*     */ {
/*     */   private static final String TABLE_NAME = "GD_FORMULA";
/*     */   private static final int ROW_FORMULASET_ID = 1;
/*     */   private static final int ROW_FORMULA_ID = 2;
/*     */   private static final int ROW_FORMULA = 3;
/*     */   public static final String FORMULA_COST_ARMOR = "armorCostEquation";
/*     */   public static final String FORMULA_COST_JEWELRY = "jewelryCostEquation";
/*     */   public static final String FORMULA_COST_OFFHAND = "offhandCostEquation";
/*     */   public static final String FORMULA_COST_SHIELD = "shieldCostEquation";
/*     */   public static final String FORMULA_COST_MELEE_1H = "weaponCostEquation";
/*     */   public static final String FORMULA_COST_MELEE_2H = "weaponMelee2hCostEquation";
/*     */   public static final String FORMULA_COST_RANGED_1H = "weaponRangedCostEquation";
/*     */   public static final String FORMULA_COST_RANGED_2H = "weaponRanged2hCostEquation";
/*     */   public static final String FORMULA_DEX_DAGGER = "daggerDexterityEquation";
/*     */   public static final String FORMULA_DEX_RANGED_1H = "ranged1hDexterityEquation";
/*     */   public static final String FORMULA_DEX_RANGED_2H = "ranged2hDexterityEquation";
/*     */   public static final String FORMULA_DEX_SWORD = "swordDexterityEquation";
/*     */   public static final String FORMULA_INT_AMULET = "amuletIntelligenceEquation";
/*     */   public static final String FORMULA_INT_CHEST = "chestIntelligenceEquation";
/*     */   public static final String FORMULA_INT_DAGGER = "daggerIntelligenceEquation";
/*     */   public static final String FORMULA_INT_HEAD = "headIntelligenceEquation";
/*     */   public static final String FORMULA_INT_OFFHAND = "offhandIntelligenceEquation";
/*     */   public static final String FORMULA_INT_RING = "ringIntelligenceEquation";
/*     */   public static final String FORMULA_INT_SCEPTER = "scepterIntelligenceEquation";
/*     */   public static final String FORMULA_STR_AXE = "axeStrengthEquation";
/*     */   public static final String FORMULA_STR_CHEST = "chestStrengthEquation";
/*     */   public static final String FORMULA_STR_FEET = "feetStrengthEquation";
/*     */   public static final String FORMULA_STR_HANDS = "handsStrengthEquation";
/*     */   public static final String FORMULA_STR_HEAD = "headStrengthEquation";
/*     */   public static final String FORMULA_STR_LEGS = "legsStrengthEquation";
/*     */   public static final String FORMULA_STR_MACE = "maceStrengthEquation";
/*     */   public static final String FORMULA_STR_MELEE_2H = "melee2hStrengthEquation";
/*     */   public static final String FORMULA_STR_SCEPTER = "scepterStrengthEquation";
/*     */   public static final String FORMULA_STR_SHIELD = "shieldStrengthEquation";
/*     */   public static final String FORMULA_STR_SHOULDERS = "shouldersStrengthEquation";
/*     */   public static final String FORMULA_STR_BELT = "waistStrengthEquation";
/*     */   private String formulaSetID;
/*     */   private String formulaID;
/*     */   private String formula;
/*  69 */   private Expression expression = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormulaSetID() {
/*  77 */     return this.formulaSetID;
/*     */   }
/*     */   
/*     */   public String getFormulaID() {
/*  81 */     return this.formulaID;
/*     */   }
/*     */   
/*     */   public String getFormula() {
/*  85 */     return this.formula;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFormulaSetID(String formulaSetID) {
/*  93 */     this.formulaSetID = formulaSetID;
/*     */   }
/*     */   
/*     */   public void setFormulaID(String formulaID) {
/*  97 */     this.formulaID = formulaID;
/*     */   }
/*     */   
/*     */   public void setFormula(String formula) {
/* 101 */     this.formula = formula;
/*     */     
/* 103 */     if (formula != null) {
/* 104 */       ExpressionBuilder builder = new ExpressionBuilder(formula);
/* 105 */       builder = builder.variables(new String[] { "charAttackSpeed", "damageAvgBase", "damageAvgPierceRatio", "defenseAttrArmor", "itemLevel", "itemPrefixCost", "itemSuffixCost", "shieldBlockChance", "shieldBlockDefense", "totalAttCount", "CHARATTACKSPEED", "DAMAGEAVGBASE", "DAMAGEAVGPIERCERATIO", "DEFENSEATTRARMOR", "ITEMLEVEL", "ITEMPREFIXCOST", "ITEMSUFFIXCOST", "SHIELDBLOCKCHANCE", "SHIELDBLOCKDEFENSE", "TOTALATTCOUNT" });
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
/* 129 */       this.expression = builder.build();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParameterSet(ParameterSet ps) {
/* 138 */     if (this.expression == null) {
/*     */       return;
/*     */     }
/* 141 */     this.expression.setVariable("charAttackSpeed", ps.getCharAttackSpeed());
/* 142 */     this.expression.setVariable("damageAvgBase", ps.getDamageAvgBase());
/* 143 */     this.expression.setVariable("damageAvgPierceRatio", ps.getDamageAvgPierceRatio());
/* 144 */     this.expression.setVariable("defenseAttrArmor", ps.getDefenseAttrArmor());
/* 145 */     this.expression.setVariable("itemLevel", ps.getItemLevel());
/* 146 */     this.expression.setVariable("itemPrefixCost", ps.getItemPrefixCost());
/* 147 */     this.expression.setVariable("itemSuffixCost", ps.getItemSuffixCost());
/* 148 */     this.expression.setVariable("shieldBlockChance", ps.getShieldBlockChance());
/* 149 */     this.expression.setVariable("shieldBlockDefense", ps.getShieldBlockDefense());
/* 150 */     this.expression.setVariable("totalAttCount", ps.getTotalAttCount());
/*     */     
/* 152 */     this.expression.setVariable("CHARATTACKSPEED", ps.getCharAttackSpeed());
/* 153 */     this.expression.setVariable("DAMAGEAVGBASE", ps.getDamageAvgBase());
/* 154 */     this.expression.setVariable("DAMAGEAVGPIERCERATIO", ps.getDamageAvgPierceRatio());
/* 155 */     this.expression.setVariable("DEFENSEATTRARMOR", ps.getDefenseAttrArmor());
/* 156 */     this.expression.setVariable("ITEMLEVEL", ps.getItemLevel());
/* 157 */     this.expression.setVariable("ITEMPREFIXCOST", ps.getItemPrefixCost());
/* 158 */     this.expression.setVariable("ITEMSUFFIXCOST", ps.getItemSuffixCost());
/* 159 */     this.expression.setVariable("SHIELDBLOCKCHANCE", ps.getShieldBlockChance());
/* 160 */     this.expression.setVariable("SHIELDBLOCKDEFENSE", ps.getShieldBlockDefense());
/* 161 */     this.expression.setVariable("TOTALATTCOUNT", ps.getTotalAttCount());
/*     */   }
/*     */   
/*     */   public double getValue() {
/* 165 */     if (this.expression == null) return 0.0D;
/*     */     
/* 167 */     double value = this.expression.evaluate();
/*     */     
/* 169 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable() throws SQLException {
/* 177 */     String dropTable = "DROP TABLE GD_FORMULA";
/* 178 */     String createTable = "CREATE TABLE GD_FORMULA (FORMULASET_ID   VARCHAR(256) NOT NULL, FORMULA_ID      VARCHAR(64), FORMULA         VARCHAR(256), PRIMARY KEY (FORMULASET_ID, FORMULA_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 184 */     try (Connection conn = GDDBData.getConnection()) {
/* 185 */       boolean auto = conn.getAutoCommit();
/* 186 */       conn.setAutoCommit(false);
/*     */       
/* 188 */       try (Statement st = conn.createStatement()) {
/* 189 */         if (GDDBUtil.tableExists(conn, "GD_FORMULA")) {
/* 190 */           st.execute(dropTable);
/*     */         }
/* 192 */         st.execute(createTable);
/* 193 */         st.close();
/*     */         
/* 195 */         conn.commit();
/*     */       
/*     */       }
/* 198 */       catch (SQLException ex) {
/* 199 */         conn.rollback();
/*     */         
/* 201 */         Object[] args = { "GD_FORMULA" };
/* 202 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 204 */         GDMsgLogger.addError(msg);
/*     */         
/* 206 */         throw ex;
/*     */       } finally {
/*     */         
/* 209 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(Connection conn, String formulaSetID) throws SQLException {
/* 215 */     String deleteEntry = "DELETE FROM GD_FORMULA WHERE FORMULASET_ID = ?";
/*     */     
/* 217 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 218 */       ps.setString(1, formulaSetID);
/* 219 */       ps.executeUpdate();
/* 220 */       ps.close();
/*     */     }
/* 222 */     catch (SQLException ex) {
/* 223 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void insert(Connection conn, DBFormulaSet dbFormulaSet) throws SQLException {
/* 229 */     if (dbFormulaSet.getFormulaList() == null)
/* 230 */       return;  if (dbFormulaSet.getFormulaList().isEmpty())
/*     */       return; 
/* 232 */     String insert = "INSERT INTO GD_FORMULA VALUES (?,?,?)";
/*     */     
/* 234 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 235 */       for (DBFormula formula : dbFormulaSet.getFormulaList()) {
/* 236 */         ps.setString(1, dbFormulaSet.getFormulaSetID());
/* 237 */         ps.setString(2, formula.formulaID);
/* 238 */         ps.setString(3, formula.formula);
/*     */         
/* 240 */         ps.executeUpdate();
/*     */         
/* 242 */         ps.clearParameters();
/*     */       } 
/* 244 */       ps.close();
/*     */       
/* 246 */       conn.commit();
/*     */     }
/* 248 */     catch (SQLException ex) {
/* 249 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBFormula> getByFormulaSetID(String formulaSetID) {
/* 254 */     List<DBFormula> list = null;
/*     */     
/* 256 */     String command = "SELECT * FROM GD_FORMULA WHERE FORMULASET_ID = ?";
/*     */     
/* 258 */     try(Connection conn = GDDBData.getConnection(); 
/* 259 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 260 */       ps.setString(1, formulaSetID);
/*     */       
/* 262 */       try (ResultSet rs = ps.executeQuery()) {
/* 263 */         list = wrap(rs);
/*     */         
/* 265 */         conn.commit();
/*     */       }
/* 267 */       catch (SQLException ex) {
/* 268 */         throw ex;
/*     */       }
/*     */     
/* 271 */     } catch (SQLException ex) {
/* 272 */       Object[] args = { formulaSetID, "GD_FORMULA" };
/* 273 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 275 */       GDMsgLogger.addError(msg);
/* 276 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 279 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBFormula> wrap(ResultSet rs) throws SQLException {
/* 283 */     LinkedList<DBFormula> list = new LinkedList<>();
/*     */     
/* 285 */     while (rs.next()) {
/* 286 */       DBFormula formula = new DBFormula();
/*     */       
/* 288 */       formula.formulaSetID = rs.getString(1);
/* 289 */       formula.formulaID = rs.getString(2);
/* 290 */       formula.formula = rs.getString(3);
/*     */       
/* 292 */       if (formula.formula != null) formula.formula = formula.formula.toUpperCase(GDConstants.LOCALE_US);
/*     */       
/* 294 */       formula.setFormula(formula.formula);
/*     */       
/* 296 */       list.add(formula);
/*     */     } 
/*     */     
/* 299 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBFormula.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */