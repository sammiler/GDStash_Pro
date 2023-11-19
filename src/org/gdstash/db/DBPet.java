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
/*     */ import net.objecthunter.exp4j.Expression;
/*     */ import net.objecthunter.exp4j.ExpressionBuilder;
/*     */ import org.gdstash.description.DetailComposer;
/*     */ import org.gdstash.file.ARZRecord;
/*     */ import org.gdstash.file.GDParseException;
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
/*     */ public class DBPet
/*     */ {
/*     */   public static final String TABLE_NAME = "GD_PET";
/*     */   public static final String FIELD_ID = "PET_ID";
/*     */   private static final int ROW_PET_ID = 1;
/*     */   private static final int ROW_NAME = 2;
/*     */   private static final int ROW_FORMULA_LEVEL = 3;
/*     */   private static final int ROW_BIO_ID = 4;
/*     */   private static final int ROW_SKILL_DIE = 5;
/*  39 */   private static ConcurrentHashMap<String, DBPet> hashBuffer = new ConcurrentHashMap<>();
/*     */   
/*     */   private String petID;
/*     */   
/*     */   private String name;
/*     */   
/*     */   private String formulaLevel;
/*     */   private String bioID;
/*     */   private String dieSkillID;
/*     */   private List<DBPetSkill> skills;
/*     */   private DBPetBio dbBio;
/*     */   private Expression expressionLevel;
/*     */   private int petLevel;
/*     */   
/*     */   private DBPet() {}
/*     */   
/*     */   private DBPet(ARZRecord record) {
/*  56 */     this.petID = record.getFileName();
/*     */     
/*  58 */     this.name = record.getPetName();
/*  59 */     this.formulaLevel = record.getPetFormulaLevel();
/*  60 */     this.bioID = record.getPetBioID();
/*  61 */     this.dieSkillID = record.getPetDieSkillID();
/*  62 */     this.skills = record.getPetSkillList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  70 */     return this.name;
/*     */   }
/*     */   
/*     */   private int getPetLevelByPlayerLevel(int level) {
/*  74 */     int lvl = 1;
/*  75 */     if (level > lvl) lvl = level;
/*     */     
/*  77 */     setCharLevel(lvl);
/*     */     
/*  79 */     return (int)(getPetCharLevel() + 0.1D);
/*     */   }
/*     */   
/*     */   private void setCharLevel(int level) {
/*  83 */     if (this.expressionLevel == null)
/*     */       return; 
/*  85 */     this.expressionLevel.setVariable("CHARLEVEL", level);
/*     */   }
/*     */   
/*     */   private int getPetCharLevel() {
/*  89 */     if (this.expressionLevel == null) return 1;
/*     */     
/*  91 */     double value = this.expressionLevel.evaluate();
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
/* 108 */     return (int)(value + 0.1D);
/*     */   }
/*     */   
/*     */   public int getHealth() {
/* 112 */     if (this.dbBio == null) return 0;
/*     */     
/* 114 */     return this.dbBio.getLifeByPetLevel(getPetCharLevel());
/*     */   }
/*     */   
/*     */   public int getMana() {
/* 118 */     if (this.dbBio == null) return 0;
/*     */     
/* 120 */     return this.dbBio.getManaByPetLevel(getPetCharLevel());
/*     */   }
/*     */   
/*     */   public String getPetSkillDescriptions() {
/* 124 */     String s = "";
/*     */     
/* 126 */     for (DBPetSkill psk : this.skills) {
/* 127 */       int lvl = psk.getSkillLevelByPetLevel(this.petLevel);
/*     */       
/* 129 */       if (lvl <= 0)
/*     */         continue; 
/* 131 */       DBSkill sk = psk.getDBSkill();
/*     */       
/* 133 */       if (sk == null)
/*     */         continue; 
/* 135 */       if (sk.getName() == null)
/*     */         continue; 
/* 137 */       if (!s.isEmpty()) s = s + "<br>";
/*     */       
/* 139 */       if (sk.getSkillID().equals(this.dieSkillID)) {
/* 140 */         s = s + GDColor.HTML_COLOR_COMPONENT + GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_PET_ON_DEATH") + "</font>" + " ";
/*     */       }
/*     */       
/* 143 */       s = s + sk.getSkillDescription(lvl, 100, false);
/*     */     } 
/*     */     
/* 146 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerLevel(int level) {
/* 154 */     this.petLevel = getPetLevelByPlayerLevel(level);
/*     */   }
/*     */   
/*     */   private void setLevelFormula(String formulaLevel) throws GDParseException {
/* 158 */     this.formulaLevel = formulaLevel;
/*     */     
/* 160 */     if (formulaLevel == null) {
/* 161 */       this.expressionLevel = null;
/*     */     } else {
/* 163 */       ExpressionBuilder builder = new ExpressionBuilder(formulaLevel);
/*     */       
/* 165 */       if (formulaLevel.contains("CHARLEVEL")) builder = builder.variables(new String[] { "CHARLEVEL" });
/*     */       
/*     */       try {
/* 168 */         this.expressionLevel = builder.build();
/*     */       }
/* 170 */       catch (Throwable ex) {
/* 171 */         this.expressionLevel = null;
/*     */         
/* 173 */         throw new GDParseException(ex.getMessage());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearBuffer() {
/* 183 */     hashBuffer.clear();
/*     */   }
/*     */   
/*     */   public static void createTables() throws SQLException {
/* 187 */     String dropTable = "DROP TABLE GD_PET";
/* 188 */     String createTable = "CREATE TABLE GD_PET (PET_ID  VARCHAR(256) NOT NULL, NAME              VARCHAR(256), FORMULA_LEVEL     VARCHAR(256), BIO_ID            VARCHAR(256), DIE_SKILL_ID      VARCHAR(256), PRIMARY KEY (PET_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 197 */     try (Connection conn = GDDBData.getConnection()) {
/* 198 */       boolean auto = conn.getAutoCommit();
/* 199 */       conn.setAutoCommit(false);
/*     */       
/* 201 */       try (Statement st = conn.createStatement()) {
/* 202 */         if (GDDBUtil.tableExists(conn, "GD_PET")) {
/* 203 */           st.execute(dropTable);
/*     */         }
/*     */         
/* 206 */         st.execute(createTable);
/* 207 */         st.close();
/*     */         
/* 209 */         conn.commit();
/*     */         
/* 211 */         DBPetSkill.createTable(conn);
/*     */       }
/* 213 */       catch (SQLException ex) {
/* 214 */         conn.rollback();
/*     */         
/* 216 */         Object[] args = { "GD_PET" };
/* 217 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 219 */         GDMsgLogger.addError(msg);
/*     */         
/* 221 */         throw ex;
/*     */       } finally {
/*     */         
/* 224 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 230 */     DBPet entry = get(record.getFileName());
/*     */     
/* 232 */     if (entry != null)
/*     */       return; 
/* 234 */     DBPet pet = new DBPet(record);
/*     */     
/* 236 */     if (pet.formulaLevel == null)
/*     */       return; 
/*     */     try {
/* 239 */       String formula = pet.formulaLevel.toUpperCase(GDConstants.LOCALE_US);
/* 240 */       ExpressionBuilder builder = new ExpressionBuilder(formula);
/*     */       
/* 242 */       if (formula.contains("CHARLEVEL")) builder = builder.variables(new String[] { "CHARLEVEL" });
/*     */       
/* 244 */       Expression expression = builder.build();
/*     */       
/* 246 */       if (formula.contains("CHARLEVEL")) expression.setVariable("CHARLEVEL", 1.0D);
/*     */       
/* 248 */       double d = expression.evaluate();
/*     */     }
/* 250 */     catch (Throwable ex) {
/* 251 */       Object[] args = { record.getFileName() };
/* 252 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FORMULA_BY_ID", args);
/*     */       
/* 254 */       GDMsgLogger.addWarning(msg);
/* 255 */       GDMsgLogger.addWarning(ex);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 260 */     String insert = "INSERT INTO GD_PET VALUES (?,?,?,?,?)";
/*     */     
/* 262 */     try (Connection conn = GDDBData.getConnection()) {
/* 263 */       boolean auto = conn.getAutoCommit();
/* 264 */       conn.setAutoCommit(false);
/*     */       
/* 266 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 267 */         ps.setString(1, pet.petID);
/* 268 */         ps.setString(2, pet.name);
/* 269 */         ps.setString(3, pet.formulaLevel);
/* 270 */         ps.setString(4, pet.bioID);
/* 271 */         ps.setString(5, pet.dieSkillID);
/*     */         
/* 273 */         ps.executeUpdate();
/* 274 */         ps.close();
/*     */         
/* 276 */         DBPetSkill.insert(conn, pet.petID, pet.skills);
/*     */         
/* 278 */         conn.commit();
/*     */       }
/* 280 */       catch (SQLException ex) {
/* 281 */         conn.rollback();
/*     */         
/* 283 */         Object[] args = { record.getFileName(), "GD_PET" };
/* 284 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */         
/* 286 */         GDMsgLogger.addLowError(msg);
/* 287 */         GDMsgLogger.addLowError(ex);
/*     */       } finally {
/*     */         
/* 290 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBPet get(String petID) {
/* 296 */     if (petID == null) return null;
/*     */     
/* 298 */     DBPet pet = null;
/*     */     
/* 300 */     pet = hashBuffer.get(petID);
/*     */     
/* 302 */     if (pet == null) {
/* 303 */       pet = getDB(petID);
/*     */       
/* 305 */       if (pet != null) hashBuffer.put(pet.petID, pet);
/*     */     
/*     */     } 
/* 308 */     return pet;
/*     */   }
/*     */   
/*     */   private static DBPet getDB(String petID) {
/* 312 */     DBPet pet = null;
/*     */     
/* 314 */     String command = "SELECT * FROM GD_PET WHERE PET_ID = ?";
/*     */     
/* 316 */     try(Connection conn = GDDBData.getConnection(); 
/* 317 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 318 */       ps.setString(1, petID);
/*     */       
/* 320 */       try (ResultSet rs = ps.executeQuery()) {
/* 321 */         List<DBPet> list = wrap(rs);
/*     */         
/* 323 */         if (list.isEmpty()) {
/* 324 */           pet = null;
/*     */         } else {
/* 326 */           pet = list.get(0);
/*     */         } 
/*     */         
/* 329 */         conn.commit();
/*     */       }
/* 331 */       catch (SQLException ex) {
/* 332 */         throw ex;
/*     */       }
/*     */     
/* 335 */     } catch (SQLException ex) {
/* 336 */       Object[] args = { petID, "GD_PET" };
/* 337 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 339 */       GDMsgLogger.addError(msg);
/* 340 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 343 */     return pet;
/*     */   }
/*     */   
/*     */   private static List<DBPet> wrap(ResultSet rs) throws SQLException {
/* 347 */     LinkedList<DBPet> list = new LinkedList<>();
/*     */     
/* 349 */     while (rs.next()) {
/* 350 */       DBPet pet = new DBPet();
/*     */       
/* 352 */       pet.petID = rs.getString(1);
/* 353 */       pet.name = rs.getString(2);
/* 354 */       pet.formulaLevel = rs.getString(3);
/* 355 */       pet.bioID = rs.getString(4);
/* 356 */       pet.dieSkillID = rs.getString(5);
/*     */       
/* 358 */       pet.skills = DBPetSkill.getByPetID(pet.petID);
/*     */       
/* 360 */       if (pet.formulaLevel != null) pet.formulaLevel = pet.formulaLevel.toUpperCase(GDConstants.LOCALE_US);
/*     */       
/*     */       try {
/* 363 */         pet.setLevelFormula(pet.formulaLevel);
/*     */       }
/* 365 */       catch (GDParseException gDParseException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 373 */       if (pet.bioID != null) {
/* 374 */         pet.dbBio = DBPetBio.get(pet.bioID);
/*     */       }
/*     */       
/* 377 */       pet.setPlayerLevel(1);
/*     */       
/* 379 */       list.add(pet);
/*     */     } 
/*     */     
/* 382 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPetDescription(boolean fullHTML) {
/* 390 */     return DetailComposer.getPetDescription(this, fullHTML);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBPet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */