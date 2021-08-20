/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ public class DBMerchant
/*     */ {
/*     */   private static final String TABLE_NAME = "GDC_MERCHANT";
/*     */   private static final int ROW_MERCHANT_ID = 1;
/*     */   private static final int ROW_FACTION_ID = 2;
/*     */   private static final int ROW_TABLESET_ID = 3;
/*     */   private String merchantID;
/*     */   private String factionID;
/*     */   private String tableSetID;
/*     */   
/*     */   public DBMerchant() {}
/*     */   
/*     */   private DBMerchant(ARZRecord record) {
/*  39 */     this.merchantID = record.getFileName();
/*     */     
/*  41 */     this.factionID = record.getMerchantFactionID();
/*  42 */     this.tableSetID = record.getMerchantTableSetID();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMerchantID() {
/*  50 */     return this.merchantID;
/*     */   }
/*     */   
/*     */   public String getFactionID() {
/*  54 */     return this.factionID;
/*     */   }
/*     */   
/*     */   public String getTableSetID() {
/*  58 */     return this.tableSetID;
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
/*     */   public static void createTables() throws SQLException {
/*  70 */     String dropTable = "DROP TABLE GDC_MERCHANT";
/*  71 */     String createTable = "CREATE TABLE GDC_MERCHANT (MERCHANT_ID    VARCHAR(256) NOT NULL, FACTION_ID     VARCHAR(256), TABLESET_ID    VARCHAR(256), PRIMARY KEY (MERCHANT_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     try (Connection conn = GDDBData.getConnection()) {
/*  78 */       boolean auto = conn.getAutoCommit();
/*  79 */       conn.setAutoCommit(false);
/*     */       
/*  81 */       try (Statement st = conn.createStatement()) {
/*  82 */         if (GDDBUtil.tableExists(conn, "GDC_MERCHANT")) {
/*  83 */           st.execute(dropTable);
/*     */         }
/*  85 */         st.execute(createTable);
/*  86 */         st.close();
/*     */         
/*  88 */         conn.commit();
/*     */       }
/*  90 */       catch (SQLException ex) {
/*  91 */         conn.rollback();
/*     */         
/*  93 */         Object[] args = { "GDC_MERCHANT" };
/*  94 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/*  96 */         GDMsgLogger.addError(msg);
/*     */         
/*  98 */         throw ex;
/*     */       } finally {
/*     */         
/* 101 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(String merchantID) throws SQLException {
/* 107 */     String deleteEntry = "DELETE FROM GDC_MERCHANT WHERE MERCHANT_ID = ?";
/*     */     
/* 109 */     try (Connection conn = GDDBData.getConnection()) {
/* 110 */       boolean auto = conn.getAutoCommit();
/* 111 */       conn.setAutoCommit(false);
/*     */       
/* 113 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 114 */         ps.setString(1, merchantID);
/* 115 */         ps.executeUpdate();
/* 116 */         ps.close();
/*     */         
/* 118 */         conn.commit();
/*     */       }
/* 120 */       catch (SQLException ex) {
/* 121 */         conn.rollback();
/*     */         
/* 123 */         Object[] args = { merchantID, "GDC_MERCHANT" };
/* 124 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*     */         
/* 126 */         GDMsgLogger.addError(msg);
/* 127 */         GDMsgLogger.addError(ex);
/*     */         
/* 129 */         throw ex;
/*     */       } finally {
/*     */         
/* 132 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 135 */     } catch (SQLException ex) {
/* 136 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 141 */     DBMerchant entry = get(record.getFileName());
/*     */     
/* 143 */     if (entry != null)
/*     */       return; 
/* 145 */     DBMerchant merchant = new DBMerchant(record);
/*     */     
/* 147 */     String insert = "INSERT INTO GDC_MERCHANT VALUES (?,?,?)";
/*     */     
/* 149 */     try (Connection conn = GDDBData.getConnection()) {
/* 150 */       boolean auto = conn.getAutoCommit();
/* 151 */       conn.setAutoCommit(false);
/*     */       
/* 153 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/*     */         
/* 155 */         ps.setString(1, merchant.merchantID);
/* 156 */         ps.setString(2, merchant.factionID);
/* 157 */         ps.setString(3, merchant.tableSetID);
/*     */         
/* 159 */         ps.executeUpdate();
/*     */         
/* 161 */         ps.close();
/* 162 */         conn.commit();
/*     */       }
/* 164 */       catch (SQLException ex) {
/* 165 */         conn.rollback();
/*     */         
/* 167 */         Object[] args = { record.getFileName(), "GDC_MERCHANT" };
/* 168 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */         
/* 170 */         GDMsgLogger.addLowError(msg);
/* 171 */         GDMsgLogger.addLowError(ex);
/*     */       } finally {
/*     */         
/* 174 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBMerchant get(String merchantID) {
/* 180 */     DBMerchant merchant = new DBMerchant();
/*     */     
/* 182 */     String command = "SELECT * FROM GDC_MERCHANT WHERE MERCHANT_ID = ?";
/*     */     
/* 184 */     try(Connection conn = GDDBData.getConnection(); 
/* 185 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 186 */       ps.setString(1, merchantID);
/*     */       
/* 188 */       try (ResultSet rs = ps.executeQuery()) {
/* 189 */         List<DBMerchant> list = wrap(rs);
/*     */         
/* 191 */         if (list.isEmpty()) { merchant = null; }
/* 192 */         else { merchant = list.get(0); }
/*     */         
/* 194 */         conn.commit();
/*     */       }
/* 196 */       catch (SQLException ex) {
/* 197 */         throw ex;
/*     */       }
/*     */     
/* 200 */     } catch (SQLException ex) {
/* 201 */       Object[] args = { merchantID, "GDC_MERCHANT" };
/* 202 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 204 */       GDMsgLogger.addError(msg);
/* 205 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 208 */     return merchant;
/*     */   }
/*     */   
/*     */   public static DBMerchant getByTableSetID(String tableSetID) {
/* 212 */     DBMerchant merchant = new DBMerchant();
/*     */     
/* 214 */     String command = "SELECT * FROM GDC_MERCHANT WHERE TABLESET_ID = ?";
/*     */     
/* 216 */     try(Connection conn = GDDBData.getConnection(); 
/* 217 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 218 */       ps.setString(1, tableSetID);
/*     */       
/* 220 */       try (ResultSet rs = ps.executeQuery()) {
/* 221 */         List<DBMerchant> list = wrap(rs);
/*     */         
/* 223 */         if (list.isEmpty()) { merchant = null; }
/* 224 */         else { merchant = list.get(0); }
/*     */         
/* 226 */         conn.commit();
/*     */       }
/* 228 */       catch (SQLException ex) {
/* 229 */         throw ex;
/*     */       }
/*     */     
/* 232 */     } catch (SQLException ex) {
/* 233 */       Object[] args = { tableSetID, "GDC_MERCHANT" };
/* 234 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 236 */       GDMsgLogger.addError(msg);
/* 237 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 240 */     return merchant;
/*     */   }
/*     */   
/*     */   private static List<DBMerchant> wrap(ResultSet rs) throws SQLException {
/* 244 */     LinkedList<DBMerchant> list = new LinkedList<>();
/*     */     
/* 246 */     while (rs.next()) {
/* 247 */       DBMerchant merchant = new DBMerchant();
/*     */       
/* 249 */       merchant.merchantID = rs.getString(1);
/* 250 */       merchant.factionID = rs.getString(2);
/* 251 */       merchant.tableSetID = rs.getString(3);
/*     */ 
/*     */       
/* 254 */       list.add(merchant);
/*     */     } 
/*     */     
/* 257 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBMerchant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */