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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DBPetSkill
/*     */ {
/*     */   public static final String TABLE_NAME = "GD_PET_SKILL";
/*     */   public static final String FIELD_ID = "PET_ID";
/*     */   private static final int ROW_PET_ID = 1;
/*     */   private static final int ROW_INDEX = 2;
/*     */   private static final int ROW_SKILL_ID = 3;
/*     */   private static final int ROW_FORMULA_LEVEL = 4;
/*     */   private String petID;
/*     */   private int index;
/*     */   private String skillID;
/*     */   private String formulaLevel;
/*     */   private DBSkill dbSkill;
/*     */   private Expression expressionLevel;
/*     */   
/*     */   public String getSkillID() {
/*  50 */     return this.skillID;
/*     */   }
/*     */   
/*     */   public int getIndex() {
/*  54 */     return this.index;
/*     */   }
/*     */   
/*     */   public DBSkill getDBSkill() {
/*  58 */     return this.dbSkill;
/*     */   }
/*     */   
/*     */   public int getSkillLevelByPetLevel(int level) {
/*  62 */     int lvl = 1;
/*  63 */     if (level > lvl) lvl = level;
/*     */     
/*  65 */     setCharLevel(lvl);
/*     */     
/*  67 */     return (int)(getSkillLevel() + 0.1D);
/*     */   }
/*     */   
/*     */   private void setCharLevel(int level) {
/*  71 */     if (this.expressionLevel == null)
/*     */       return; 
/*  73 */     this.expressionLevel.setVariable("CHARLEVEL", level);
/*     */   }
/*     */   
/*     */   private double getSkillLevel() {
/*  77 */     if (this.expressionLevel == null) return 1.0D;
/*     */     
/*  79 */     double value = this.expressionLevel.evaluate();
/*     */     
/*  81 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIndex(int index) {
/*  89 */     this.index = index;
/*     */   }
/*     */   
/*     */   public void setSkillID(String skillID) {
/*  93 */     this.skillID = skillID;
/*     */   }
/*     */   
/*     */   public void setFormulaLevel(String formulaLevel) {
/*  97 */     this.formulaLevel = formulaLevel;
/*     */   }
/*     */   
/*     */   private void setLevelFormula(String formulaLevel) throws GDParseException {
/* 101 */     this.formulaLevel = formulaLevel;
/*     */     
/* 103 */     if (formulaLevel == null) {
/* 104 */       this.expressionLevel = null;
/*     */     } else {
/* 106 */       ExpressionBuilder builder = new ExpressionBuilder(formulaLevel);
/*     */       
/* 108 */       if (formulaLevel.contains("CHARLEVEL")) builder = builder.variables(new String[] { "CHARLEVEL" });
/*     */       
/*     */       try {
/* 111 */         this.expressionLevel = builder.build();
/*     */       }
/* 113 */       catch (Throwable ex) {
/* 114 */         this.expressionLevel = null;
/*     */         
/* 116 */         throw new GDParseException(ex.getMessage());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable(Connection conn) throws SQLException {
/* 126 */     String dropTable = "DROP TABLE GD_PET_SKILL";
/* 127 */     String createTable = "CREATE TABLE GD_PET_SKILL (PET_ID  VARCHAR(256) NOT NULL, INDEX             INTEGER, SKILL_ID          VARCHAR(256), FORMULA_LEVEL     VARCHAR(256), PRIMARY KEY (PET_ID, INDEX))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     try (Statement st = conn.createStatement()) {
/* 136 */       if (GDDBUtil.tableExists(conn, "GD_PET_SKILL")) {
/* 137 */         st.execute(dropTable);
/*     */       }
/*     */       
/* 140 */       st.execute(createTable);
/* 141 */       st.close();
/*     */       
/* 143 */       conn.commit();
/*     */     }
/* 145 */     catch (SQLException ex) {
/* 146 */       Object[] args = { "GD_PET_SKILL" };
/* 147 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */       
/* 149 */       GDMsgLogger.addError(msg);
/*     */       
/* 151 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(Connection conn, String petID, List<DBPetSkill> skills) throws SQLException {
/* 156 */     String insert = "INSERT INTO GD_PET_SKILL VALUES (?,?,?,?)";
/*     */     
/* 158 */     if (skills == null)
/* 159 */       return;  if (skills.isEmpty())
/*     */       return; 
/* 161 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 162 */       for (DBPetSkill psk : skills) {
/* 163 */         if (psk.skillID == null || 
/* 164 */           psk.formulaLevel == null || 
/* 165 */           psk.formulaLevel.equals("0"))
/*     */           continue; 
/*     */         try {
/* 168 */           String formula = psk.formulaLevel.toUpperCase(GDConstants.LOCALE_US);
/* 169 */           ExpressionBuilder builder = new ExpressionBuilder(formula);
/*     */           
/* 171 */           if (formula.contains("CHARLEVEL")) builder = builder.variables(new String[] { "CHARLEVEL" });
/*     */           
/* 173 */           Expression expression = builder.build();
/*     */           
/* 175 */           if (formula.contains("CHARLEVEL")) expression.setVariable("CHARLEVEL", 1.0D);
/*     */           
/* 177 */           double d = expression.evaluate();
/*     */         }
/* 179 */         catch (Throwable ex) {
/* 180 */           Object[] args = { petID };
/* 181 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FORMULA_BY_ID", args);
/*     */           
/* 183 */           GDMsgLogger.addWarning(msg);
/* 184 */           GDMsgLogger.addWarning(ex);
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 189 */         ps.setString(1, petID);
/* 190 */         ps.setInt(2, psk.index);
/* 191 */         ps.setString(3, psk.skillID);
/* 192 */         ps.setString(4, psk.formulaLevel);
/*     */         
/* 194 */         ps.executeUpdate();
/*     */         
/* 196 */         ps.clearParameters();
/*     */       } 
/*     */       
/* 199 */       ps.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBPetSkill> getByPetID(String petID) {
/* 204 */     List<DBPetSkill> list = new LinkedList<>();
/*     */     
/* 206 */     String command = "SELECT * FROM GD_PET_SKILL WHERE PET_ID = ?";
/*     */     
/* 208 */     try(Connection conn = GDDBData.getConnection(); 
/* 209 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 210 */       ps.setString(1, petID);
/*     */       
/* 212 */       try (ResultSet rs = ps.executeQuery()) {
/* 213 */         list = wrap(rs);
/*     */         
/* 215 */         conn.commit();
/*     */       }
/* 217 */       catch (SQLException ex) {
/* 218 */         throw ex;
/*     */       }
/*     */     
/* 221 */     } catch (SQLException ex) {
/* 222 */       Object[] args = { petID, "GD_PET_SKILL" };
/* 223 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 225 */       GDMsgLogger.addError(msg);
/* 226 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 229 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBPetSkill> wrap(ResultSet rs) throws SQLException {
/* 233 */     LinkedList<DBPetSkill> list = new LinkedList<>();
/*     */     
/* 235 */     while (rs.next()) {
/* 236 */       DBPetSkill psk = new DBPetSkill();
/*     */       
/* 238 */       psk.petID = rs.getString(1);
/* 239 */       psk.index = rs.getInt(2);
/* 240 */       psk.skillID = rs.getString(3);
/* 241 */       psk.formulaLevel = rs.getString(4);
/*     */       
/* 243 */       if (psk.formulaLevel != null) psk.formulaLevel = psk.formulaLevel.toUpperCase(GDConstants.LOCALE_US);
/*     */       
/*     */       try {
/* 246 */         psk.setLevelFormula(psk.formulaLevel);
/*     */       }
/* 248 */       catch (GDParseException gDParseException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 256 */       if (psk.skillID != null) {
/* 257 */         psk.dbSkill = DBSkill.get(psk.skillID);
/*     */       }
/*     */       
/* 260 */       list.add(psk);
/*     */     } 
/*     */     
/* 263 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBPetSkill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */