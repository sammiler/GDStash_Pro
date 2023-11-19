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
/*     */ public class DBConstellationAffinity
/*     */   implements Comparable<DBConstellationAffinity>
/*     */ {
/*     */   private static final String TABLE_NAME = "GDC_CONSTELLATION_AFFINITY";
/*     */   private static final int ROW_CONSTELLATION_ID = 1;
/*     */   private static final int ROW_AFFINITY = 2;
/*     */   private static final int ROW_REQUIRED = 3;
/*     */   private static final int ROW_POINTS = 4;
/*     */   private String constellationID;
/*     */   private String affinity;
/*     */   private boolean required;
/*     */   private int points;
/*     */   private int index;
/*     */   
/*     */   public boolean equals(Object o) {
/*  43 */     if (o == null) return false; 
/*  44 */     if (!o.getClass().equals(DBConstellationAffinity.class)) return false;
/*     */     
/*  46 */     DBConstellationAffinity affinity = (DBConstellationAffinity)o;
/*     */     
/*  48 */     return affinity.constellationID.equals(this.constellationID);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  53 */     int hash = this.constellationID.hashCode();
/*  54 */     hash += this.index;
/*  55 */     if (this.required) hash += 100;
/*     */     
/*  57 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(DBConstellationAffinity o) {
/*  65 */     if (!o.getClass().equals(DBConstellationAffinity.class)) {
/*  66 */       Object[] args = { DBConstellationAffinity.class, o.toString() };
/*  67 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_NOT_OF_TYPE", args);
/*     */       
/*  69 */       throw new ClassCastException(msg);
/*     */     } 
/*     */     
/*  72 */     DBConstellationAffinity affinity = o;
/*     */     
/*  74 */     int comp = this.constellationID.compareTo(affinity.constellationID);
/*     */     
/*  76 */     if (comp != 0) return comp;
/*     */     
/*  78 */     if (this.required)
/*  79 */     { if (!affinity.required) return -1;
/*     */        }
/*  81 */     else if (affinity.required) { return 1; }
/*     */ 
/*     */     
/*  84 */     return this.index - affinity.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAffinity() {
/*  92 */     return this.affinity;
/*     */   }
/*     */   
/*     */   public boolean isRequired() {
/*  96 */     return this.required;
/*     */   }
/*     */   
/*     */   public int getPoints() {
/* 100 */     return this.points;
/*     */   }
/*     */   
/*     */   public int getIndex() {
/* 104 */     return this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAffinity(String affinity) {
/* 112 */     this.affinity = affinity;
/*     */   }
/*     */   
/*     */   public void setRequired(boolean required) {
/* 116 */     this.required = required;
/*     */   }
/*     */   
/*     */   public void setPoints(int points) {
/* 120 */     this.points = points;
/*     */   }
/*     */   
/*     */   public void setIndex(int index) {
/* 124 */     this.index = index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable() throws SQLException {
/* 132 */     String dropTable = "DROP TABLE GDC_CONSTELLATION_AFFINITY";
/* 133 */     String createTable = "CREATE TABLE GDC_CONSTELLATION_AFFINITY (CONSTELLATION_ID VARCHAR(256) NOT NULL, AFFINITY         VARCHAR(32) NOT NULL, REQUIRED         BOOLEAN, POINTS           INTEGER, PRIMARY KEY (CONSTELLATION_ID, AFFINITY, REQUIRED))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     try (Connection conn = GDDBData.getConnection()) {
/* 141 */       boolean auto = conn.getAutoCommit();
/* 142 */       conn.setAutoCommit(false);
/*     */       
/* 144 */       try (Statement st = conn.createStatement()) {
/* 145 */         if (GDDBUtil.tableExists(conn, "GDC_CONSTELLATION_AFFINITY")) {
/* 146 */           st.execute(dropTable);
/*     */         }
/* 148 */         st.execute(createTable);
/* 149 */         st.close();
/*     */         
/* 151 */         conn.commit();
/*     */       }
/* 153 */       catch (SQLException ex) {
/* 154 */         conn.rollback();
/*     */         
/* 156 */         throw ex;
/*     */       } finally {
/*     */         
/* 159 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(Connection conn, String itemSetID) throws SQLException {
/* 165 */     String deleteEntry = "DELETE FROM GDC_CONSTELLATION_AFFINITY WHERE CONSTELLATION_ID = ?";
/*     */     
/* 167 */     try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 168 */       ps.setString(1, itemSetID);
/* 169 */       ps.executeUpdate();
/* 170 */       ps.close();
/*     */     }
/* 172 */     catch (SQLException ex) {
/* 173 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void insert(Connection conn, DBConstellation dbConstellation) throws SQLException {
/* 179 */     if (dbConstellation.getStarList() == null)
/* 180 */       return;  if (dbConstellation.getStarList().isEmpty())
/*     */       return; 
/* 182 */     String insert = "INSERT INTO GDC_CONSTELLATION_AFFINITY VALUES (?,?,?,?)";
/*     */     
/* 184 */     try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 185 */       for (DBConstellationAffinity dbAffinity : dbConstellation.getAffinityList()) {
/* 186 */         if (dbAffinity.getAffinity() == null || 
/* 187 */           dbAffinity.getAffinity().isEmpty())
/*     */           continue; 
/* 189 */         ps.setString(1, dbConstellation.getConstellationID());
/* 190 */         ps.setString(2, dbAffinity.getAffinity());
/* 191 */         ps.setBoolean(3, dbAffinity.isRequired());
/* 192 */         ps.setInt(4, dbAffinity.getPoints());
/*     */         
/* 194 */         ps.executeUpdate();
/*     */         
/* 196 */         ps.clearParameters();
/*     */       } 
/* 198 */       ps.close();
/*     */       
/* 200 */       conn.commit();
/*     */     }
/* 202 */     catch (SQLException ex) {
/* 203 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<DBConstellationAffinity> getByConstellationID(String constellationID) {
/* 208 */     List<DBConstellationAffinity> dbAffinities = null;
/*     */     
/* 210 */     String command = "SELECT * FROM GDC_CONSTELLATION_AFFINITY WHERE CONSTELLATION_ID = ?";
/*     */     
/* 212 */     try(Connection conn = GDDBData.getConnection(); 
/* 213 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 214 */       ps.setString(1, constellationID);
/*     */       
/* 216 */       try (ResultSet rs = ps.executeQuery()) {
/* 217 */         dbAffinities = wrap(rs);
/*     */         
/* 219 */         conn.commit();
/*     */       }
/* 221 */       catch (SQLException ex) {
/* 222 */         throw ex;
/*     */       }
/*     */     
/* 225 */     } catch (SQLException ex) {
/* 226 */       Object[] args = { constellationID, "GDC_CONSTELLATION_AFFINITY" };
/* 227 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 229 */       GDMsgLogger.addError(msg);
/* 230 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 233 */     return dbAffinities;
/*     */   }
/*     */   
/*     */   private static List<DBConstellationAffinity> wrap(ResultSet rs) throws SQLException {
/* 237 */     LinkedList<DBConstellationAffinity> list = new LinkedList<>();
/*     */     
/* 239 */     while (rs.next()) {
/* 240 */       DBConstellationAffinity affinity = new DBConstellationAffinity();
/*     */       
/* 242 */       affinity.constellationID = rs.getString(1);
/* 243 */       affinity.affinity = rs.getString(2);
/* 244 */       affinity.required = rs.getBoolean(3);
/* 245 */       affinity.points = rs.getInt(4);
/*     */       
/* 247 */       list.add(affinity);
/*     */     } 
/*     */     
/* 250 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBConstellationAffinity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */