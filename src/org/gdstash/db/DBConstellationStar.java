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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DBConstellationStar
/*     */   implements Comparable<DBConstellationStar>
/*     */ {
/*     */   private static final String TABLE_NAME = "GDC_CONSTELLATION_STAR";
/*     */   private static final int ROW_CONSTELLATION_ID = 1;
/*     */   private static final int ROW_INDEX = 2;
/*     */   private static final int ROW_BUTTON_ID = 3;
/*     */   private String constellationID;
/*     */   private int index;
/*     */   private String buttonID;
/*     */   private DBSkillButton dbButton;
/*     */   
/*     */   public boolean equals(Object o) {
/*  41 */     if (o == null) return false; 
/*  42 */     if (!o.getClass().equals(DBConstellationStar.class)) return false;
/*     */     
/*  44 */     DBConstellationStar star = (DBConstellationStar)o;
/*     */     
/*  46 */     return star.constellationID.equals(this.constellationID);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  51 */     int hash = this.constellationID.hashCode();
/*  52 */     hash += this.index;
/*     */     
/*  54 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(DBConstellationStar o) {
/*  62 */     if (!o.getClass().equals(DBConstellationStar.class)) {
/*  63 */       Object[] args = { DBConstellationStar.class, o.toString() };
/*  64 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_NOT_OF_TYPE", args);
/*     */       
/*  66 */       throw new ClassCastException(msg);
/*     */     } 
/*     */     
/*  69 */     DBConstellationStar star = o;
/*     */     
/*  71 */     int comp = this.constellationID.compareTo(star.constellationID);
/*     */     
/*  73 */     if (comp != 0) return comp;
/*     */     
/*  75 */     return this.index - star.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getButtonID() {
/*  83 */     return this.buttonID;
/*     */   }
/*     */   
/*     */   public int getIndex() {
/*  87 */     return this.index;
/*     */   }
/*     */   
/*     */   public DBSkillButton getButton() {
/*  91 */     return this.dbButton;
/*     */   }
/*     */   
/*     */   public DBSkill getSkill() {
/*  95 */     if (this.dbButton == null) return null;
/*     */     
/*  97 */     return this.dbButton.getSkill();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setButtonID(String buttonID) {
/* 105 */     this.buttonID = buttonID;
/*     */   }
/*     */   
/*     */   public void setIndex(int index) {
/* 109 */     this.index = index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable() throws SQLException {
/* 117 */     String dropTable = "DROP TABLE GDC_CONSTELLATION_STAR";
/* 118 */     String createTable = "CREATE TABLE GDC_CONSTELLATION_STAR (CONSTELLATION_ID VARCHAR(256) NOT NULL, INDEX            INTEGER, BUTTON_ID        VARCHAR(256), PRIMARY KEY (CONSTELLATION_ID, INDEX))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     try (Connection conn = GDDBData.getConnection()) {
/* 125 */       boolean auto = conn.getAutoCommit();
/* 126 */       conn.setAutoCommit(false);
/*     */       
/* 128 */       try (Statement st = conn.createStatement()) {
/* 129 */         if (GDDBUtil.tableExists(conn, "GDC_CONSTELLATION_STAR")) {
/* 130 */           st.execute(dropTable);
/*     */         }
/* 132 */         st.execute(createTable);
/* 133 */         st.close();
/*     */         
/* 135 */         conn.commit();
/*     */       }
/* 137 */       catch (SQLException ex) {
/* 138 */         conn.rollback();
/*     */         
/* 140 */         throw ex;
/*     */       } finally {
/*     */         
/* 143 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(Connection conn, String constellationID) throws SQLException {
/* 149 */     String deleteEntry = "DELETE FROM GDC_CONSTELLATION_STAR WHERE CONSTELLATION_ID = ?";
/*     */     
/* 151 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 152 */       ps.setString(1, constellationID);
/* 153 */       ps.executeUpdate();
/* 154 */       ps.close();
/*     */     }
/* 156 */     catch (SQLException ex) {
/* 157 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void insert(Connection conn, DBConstellation dbConstellation) throws SQLException {
/* 163 */     if (dbConstellation.getStarList() == null)
/* 164 */       return;  if (dbConstellation.getStarList().isEmpty())
/*     */       return; 
/* 166 */     String insert = "INSERT INTO GDC_CONSTELLATION_STAR VALUES (?,?,?)";
/*     */     
/* 168 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 169 */       for (DBConstellationStar dbStar : dbConstellation.getStarList()) {
/* 170 */         if (dbStar.buttonID == null || 
/* 171 */           dbStar.buttonID.isEmpty())
/*     */           continue; 
/* 173 */         ps.setString(1, dbConstellation.getConstellationID());
/* 174 */         ps.setInt(2, dbStar.index);
/* 175 */         ps.setString(3, dbStar.buttonID);
/*     */         
/* 177 */         ps.executeUpdate();
/*     */         
/* 179 */         ps.clearParameters();
/*     */       } 
/* 181 */       ps.close();
/*     */       
/* 183 */       conn.commit();
/*     */     }
/* 185 */     catch (SQLException ex) {
/* 186 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBConstellationStar> getByConstellationID(String constellationID) {
/* 191 */     List<DBConstellationStar> list = null;
/*     */     
/* 193 */     String command = "SELECT * FROM GDC_CONSTELLATION_STAR WHERE CONSTELLATION_ID = ?";
/*     */     
/* 195 */     try(Connection conn = GDDBData.getConnection(); 
/* 196 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 197 */       ps.setString(1, constellationID);
/*     */       
/* 199 */       try (ResultSet rs = ps.executeQuery()) {
/* 200 */         list = wrap(rs);
/*     */         
/* 202 */         conn.commit();
/*     */       }
/* 204 */       catch (SQLException ex) {
/* 205 */         throw ex;
/*     */       }
/*     */     
/* 208 */     } catch (SQLException ex) {
/* 209 */       Object[] args = { constellationID, "GDC_CONSTELLATION_STAR" };
/* 210 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 212 */       GDMsgLogger.addError(msg);
/* 213 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 216 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBConstellationStar> wrap(ResultSet rs) throws SQLException {
/* 220 */     LinkedList<DBConstellationStar> list = new LinkedList<>();
/*     */     
/* 222 */     while (rs.next()) {
/* 223 */       DBConstellationStar star = new DBConstellationStar();
/*     */       
/* 225 */       star.constellationID = rs.getString(1);
/* 226 */       star.index = rs.getInt(2);
/* 227 */       star.buttonID = rs.getString(3);
/*     */       
/* 229 */       if (star.buttonID != null) {
/* 230 */         star.dbButton = DBSkillButton.getByButtonID(star.buttonID);
/*     */       }
/*     */       
/* 233 */       list.add(star);
/*     */     } 
/*     */     
/* 236 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBConstellationStar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */