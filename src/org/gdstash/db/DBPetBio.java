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
/*     */ import org.gdstash.file.ARZRecord;
/*     */ import org.gdstash.file.GDParseException;
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
/*     */ public class DBPetBio
/*     */ {
/*     */   public static final String TABLE_NAME = "GD_PET_BIO";
/*     */   public static final String FIELD_ID = "BIO_ID";
/*     */   private static final int ROW_BIO_ID = 1;
/*     */   private static final int ROW_FORMULA_LIFE = 2;
/*     */   private static final int ROW_FORMULA_MANA = 3;
/*  35 */   private static ConcurrentHashMap<String, DBPetBio> hashBuffer = new ConcurrentHashMap<>();
/*     */   
/*     */   private String bioID;
/*     */   
/*     */   private String formulaLife;
/*     */   private String formulaMana;
/*     */   private Expression expressionLife;
/*     */   private Expression expressionMana;
/*     */   
/*     */   private DBPetBio() {}
/*     */   
/*     */   private DBPetBio(ARZRecord record) {
/*  47 */     this.bioID = record.getFileName();
/*     */     
/*  49 */     this.formulaLife = record.getBioFormulaLife();
/*  50 */     this.formulaMana = record.getBioFormulaMana();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLifeByPetLevel(int level) {
/*  58 */     int lvl = 1;
/*  59 */     if (level > lvl) lvl = level;
/*     */     
/*  61 */     setPetLifeLevel(lvl);
/*     */     
/*  63 */     return (int)(getPetLife() + 0.1D);
/*     */   }
/*     */   
/*     */   private void setPetLifeLevel(int level) {
/*  67 */     if (this.expressionLife == null)
/*     */       return; 
/*  69 */     this.expressionLife.setVariable("CHARLEVEL", level);
/*     */   }
/*     */   
/*     */   private double getPetLife() {
/*  73 */     if (this.expressionLife == null) return 1.0D;
/*     */     
/*  75 */     double value = this.expressionLife.evaluate();
/*     */     
/*  77 */     return value;
/*     */   }
/*     */   
/*     */   public int getManaByPetLevel(int level) {
/*  81 */     int lvl = 1;
/*  82 */     if (level > lvl) lvl = level;
/*     */     
/*  84 */     setPetManaLevel(lvl);
/*     */     
/*  86 */     return (int)(getPetMana() + 0.1D);
/*     */   }
/*     */   
/*     */   private void setPetManaLevel(int level) {
/*  90 */     if (this.expressionMana == null)
/*     */       return; 
/*  92 */     this.expressionMana.setVariable("CHARLEVEL", level);
/*     */   }
/*     */   
/*     */   private double getPetMana() {
/*  96 */     if (this.expressionMana == null) return 1.0D;
/*     */     
/*  98 */     double value = this.expressionMana.evaluate();
/*     */     
/* 100 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setLifeFormula(String formulaLife) throws GDParseException {
/* 108 */     this.formulaLife = formulaLife;
/*     */     
/* 110 */     if (formulaLife != null) {
/* 111 */       ExpressionBuilder builder = new ExpressionBuilder(formulaLife);
/* 112 */       builder = builder.variables(new String[] { "CHARLEVEL" });
/*     */       
/*     */       try {
/* 115 */         this.expressionLife = builder.build();
/*     */       }
/* 117 */       catch (Throwable ex) {
/* 118 */         this.expressionLife = null;
/*     */         
/* 120 */         throw new GDParseException(ex.getMessage());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setManaFormula(String formulaMana) throws GDParseException {
/* 126 */     this.formulaMana = formulaMana;
/*     */     
/* 128 */     if (formulaMana != null) {
/* 129 */       ExpressionBuilder builder = new ExpressionBuilder(formulaMana);
/* 130 */       builder = builder.variables(new String[] { "CHARLEVEL" });
/*     */       
/*     */       try {
/* 133 */         this.expressionMana = builder.build();
/*     */       }
/* 135 */       catch (Throwable ex) {
/* 136 */         this.expressionMana = null;
/*     */         
/* 138 */         throw new GDParseException(ex.getMessage());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearBuffer() {
/* 148 */     hashBuffer.clear();
/*     */   }
/*     */   
/*     */   public static void createTables() throws SQLException {
/* 152 */     String dropTable = "DROP TABLE GD_PET_BIO";
/* 153 */     String createTable = "CREATE TABLE GD_PET_BIO (BIO_ID  VARCHAR(256) NOT NULL, FORMULA_LIFE      VARCHAR(256), FORMULA_MANA      VARCHAR(256), PRIMARY KEY (BIO_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     try (Connection conn = GDDBData.getConnection()) {
/* 162 */       boolean auto = conn.getAutoCommit();
/* 163 */       conn.setAutoCommit(false);
/*     */       
/* 165 */       try (Statement st = conn.createStatement()) {
/* 166 */         if (GDDBUtil.tableExists(conn, "GD_PET_BIO")) {
/* 167 */           st.execute(dropTable);
/*     */         }
/*     */         
/* 170 */         st.execute(createTable);
/* 171 */         st.close();
/*     */         
/* 173 */         conn.commit();
/*     */       }
/* 175 */       catch (SQLException ex) {
/* 176 */         conn.rollback();
/*     */         
/* 178 */         Object[] args = { "GD_PET_BIO" };
/* 179 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 181 */         GDMsgLogger.addError(msg);
/*     */         
/* 183 */         throw ex;
/*     */       } finally {
/*     */         
/* 186 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 192 */     DBPetBio entry = get(record.getFileName());
/*     */     
/* 194 */     if (entry != null)
/*     */       return; 
/* 196 */     DBPetBio bio = new DBPetBio(record);
/*     */     
/* 198 */     String insert = "INSERT INTO GD_PET_BIO VALUES (?,?,?)";
/*     */     
/* 200 */     try (Connection conn = GDDBData.getConnection()) {
/* 201 */       boolean auto = conn.getAutoCommit();
/* 202 */       conn.setAutoCommit(false);
/*     */       
/* 204 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 205 */         ps.setString(1, bio.bioID);
/* 206 */         ps.setString(2, bio.formulaLife);
/* 207 */         ps.setString(3, bio.formulaMana);
/*     */         
/* 209 */         ps.executeUpdate();
/* 210 */         ps.close();
/*     */         
/* 212 */         conn.commit();
/*     */       }
/* 214 */       catch (SQLException ex) {
/* 215 */         conn.rollback();
/*     */         
/* 217 */         Object[] args = { record.getFileName(), "GD_PET_BIO" };
/* 218 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */         
/* 220 */         GDMsgLogger.addLowError(msg);
/* 221 */         GDMsgLogger.addLowError(ex);
/*     */       } finally {
/*     */         
/* 224 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBPetBio get(String bioID) {
/* 230 */     if (bioID == null) return null;
/*     */     
/* 232 */     DBPetBio bio = null;
/*     */     
/* 234 */     bio = hashBuffer.get(bioID);
/*     */     
/* 236 */     if (bio == null) {
/* 237 */       bio = getDB(bioID);
/*     */       
/* 239 */       if (bio != null) hashBuffer.put(bio.bioID, bio);
/*     */     
/*     */     } 
/* 242 */     return bio;
/*     */   }
/*     */   
/*     */   private static DBPetBio getDB(String bioID) {
/* 246 */     DBPetBio bio = null;
/*     */     
/* 248 */     String command = "SELECT * FROM GD_PET_BIO WHERE BIO_ID = ?";
/*     */     
/* 250 */     try(Connection conn = GDDBData.getConnection(); 
/* 251 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 252 */       ps.setString(1, bioID);
/*     */       
/* 254 */       try (ResultSet rs = ps.executeQuery()) {
/* 255 */         List<DBPetBio> list = wrap(rs);
/*     */         
/* 257 */         if (list.isEmpty()) {
/* 258 */           bio = null;
/*     */         } else {
/* 260 */           bio = list.get(0);
/*     */         } 
/*     */         
/* 263 */         conn.commit();
/*     */       }
/* 265 */       catch (SQLException ex) {
/* 266 */         throw ex;
/*     */       }
/*     */     
/* 269 */     } catch (SQLException ex) {
/* 270 */       Object[] args = { bioID, "GD_PET_BIO" };
/* 271 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 273 */       GDMsgLogger.addError(msg);
/* 274 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 277 */     return bio;
/*     */   }
/*     */   
/*     */   private static List<DBPetBio> wrap(ResultSet rs) throws SQLException {
/* 281 */     LinkedList<DBPetBio> list = new LinkedList<>();
/*     */     
/* 283 */     while (rs.next()) {
/* 284 */       DBPetBio bio = new DBPetBio();
/*     */       
/* 286 */       bio.bioID = rs.getString(1);
/* 287 */       bio.formulaLife = rs.getString(2);
/* 288 */       bio.formulaMana = rs.getString(3);
/*     */       
/* 290 */       if (bio.formulaLife != null) bio.formulaLife = bio.formulaLife.toUpperCase(GDConstants.LOCALE_US); 
/* 291 */       if (bio.formulaMana != null) bio.formulaMana = bio.formulaMana.toUpperCase(GDConstants.LOCALE_US);
/*     */       
/*     */       try {
/* 294 */         bio.setLifeFormula(bio.formulaLife);
/*     */       }
/* 296 */       catch (GDParseException ex) {
/* 297 */         GDMsgLogger.addError((Throwable)ex);
/*     */       } 
/*     */       
/*     */       try {
/* 301 */         bio.setManaFormula(bio.formulaMana);
/*     */       }
/* 303 */       catch (GDParseException ex) {
/* 304 */         GDMsgLogger.addError((Throwable)ex);
/*     */       } 
/*     */       
/* 307 */       list.add(bio);
/*     */     } 
/*     */     
/* 310 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBPetBio.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */