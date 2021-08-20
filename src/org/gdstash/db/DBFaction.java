/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.file.ARCDecompress;
/*     */ import org.gdstash.file.ARZRecord;
/*     */ import org.gdstash.ui.GDStashFrame;
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
/*     */ public class DBFaction
/*     */ {
/*     */   private static final String TABLE_NAME = "GDC_FACTION";
/*     */   private static final int ROW_FACTION_ID = 1;
/*     */   private static final int ROW_BOUNTY_ENABLED = 2;
/*     */   private static final int ROW_QUEST_ENABLED = 3;
/*     */   private static final int ROW_FACTION_TAG = 4;
/*     */   private static final int ROW_FACTION_TEXT = 5;
/*     */   private String factionID;
/*     */   private boolean bountyEnabled;
/*     */   private boolean questEnabled;
/*     */   private String factionTag;
/*     */   private String factionText;
/*     */   
/*     */   public DBFaction() {}
/*     */   
/*     */   private DBFaction(ARZRecord record) {
/*  43 */     this.factionID = record.getFileName();
/*     */     
/*  45 */     this.bountyEnabled = record.getFactionBountyEnabled();
/*  46 */     this.questEnabled = record.getFactionQuestEnabled();
/*  47 */     setFactionTag(record.getFactionTag());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFactionID() {
/*  55 */     return this.factionID;
/*     */   }
/*     */   
/*     */   public boolean isBountyEnabled() {
/*  59 */     return this.bountyEnabled;
/*     */   }
/*     */   
/*     */   public boolean isQuestEnabled() {
/*  63 */     return this.questEnabled;
/*     */   }
/*     */   
/*     */   public String getFactionTag() {
/*  67 */     return this.factionTag;
/*     */   }
/*     */   
/*     */   public String getFactionText() {
/*  71 */     return this.factionText;
/*     */   }
/*     */   
/*     */   public static String getFactionTextByItemID(String itemID) {
/*  75 */     String tableID = DBMerchantTableAlloc.getTableIDByItemID(itemID);
/*     */     
/*  77 */     if (tableID == null) {
/*     */ 
/*     */ 
/*     */       
/*  81 */       String s = null;
/*     */       
/*  83 */       int pos = itemID.indexOf("b_");
/*  84 */       if (pos == -1) {
/*  85 */         pos = itemID.indexOf("c_");
/*     */       }
/*  87 */       if (pos == -1) {
/*  88 */         pos = itemID.indexOf("d_");
/*     */       }
/*     */       
/*  91 */       if (pos != -1) {
/*  92 */         String s1 = itemID.substring(0, pos);
/*  93 */         String s2 = itemID.substring(pos + 2);
/*     */         
/*  95 */         s = s1 + "a_" + s2;
/*     */       } 
/*     */       
/*  98 */       if (s != null) {
/*  99 */         tableID = DBMerchantTableAlloc.getTableIDByItemID(s);
/*     */       }
/*     */     } 
/*     */     
/* 103 */     if (tableID == null) {
/*     */ 
/*     */       
/* 106 */       DBItemCraft craft = DBItemCraft.getByCraftID(itemID);
/*     */       
/* 108 */       if (craft != null) {
/* 109 */         tableID = DBMerchantTableAlloc.getTableIDByItemID(craft.getItemID());
/*     */       }
/*     */     } 
/*     */     
/* 113 */     if (tableID == null) return null;
/*     */     
/* 115 */     String tableSetID = DBMerchantTableSetAlloc.getTableSetIDByTableID(tableID);
/*     */     
/* 117 */     if (tableSetID == null) return null;
/*     */     
/* 119 */     DBMerchant dbMerchant = DBMerchant.getByTableSetID(tableSetID);
/*     */     
/* 121 */     if (dbMerchant == null) return null; 
/* 122 */     if (dbMerchant.getFactionID() == null) return null;
/*     */     
/* 124 */     DBFaction dbFaction = get(dbMerchant.getFactionID());
/*     */     
/* 126 */     if (dbFaction == null) return null;
/*     */     
/* 128 */     return dbFaction.getFactionText();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setFactionTag(String factionTag) {
/* 136 */     this.factionTag = factionTag;
/*     */     
/* 138 */     if (factionTag == null) {
/* 139 */       this.factionText = null;
/*     */     } else {
/* 141 */       String tag = "tagFaction" + factionTag;
/* 142 */       this.factionText = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_UI, tag, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTable() throws SQLException {
/* 151 */     String dropTable = "DROP TABLE GDC_FACTION";
/* 152 */     String createTable = "CREATE TABLE GDC_FACTION (FACTION_ID     VARCHAR(256) NOT NULL, BOUNTY_ENABLED BOOLEAN, QUEST_ENABLED  BOOLEAN, FACTION_TAG    VARCHAR(64), FACTION_TEXT   VARCHAR(256), PRIMARY KEY (FACTION_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     try (Connection conn = GDDBData.getConnection()) {
/* 161 */       boolean auto = conn.getAutoCommit();
/* 162 */       conn.setAutoCommit(false);
/*     */       
/* 164 */       try (Statement st = conn.createStatement()) {
/* 165 */         if (GDDBUtil.tableExists(conn, "GDC_FACTION")) {
/* 166 */           st.execute(dropTable);
/*     */         }
/* 168 */         st.execute(createTable);
/* 169 */         st.close();
/*     */         
/* 171 */         conn.commit();
/*     */       }
/* 173 */       catch (SQLException ex) {
/* 174 */         conn.rollback();
/*     */         
/* 176 */         Object[] args = { "GDC_FACTION" };
/* 177 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 179 */         GDMsgLogger.addError(msg);
/*     */         
/* 181 */         throw ex;
/*     */       } finally {
/*     */         
/* 184 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void delete(String factionID) throws SQLException {
/* 190 */     String deleteEntry = "DELETE FROM GDC_FACTION WHERE FACTION_ID = ?";
/*     */     
/* 192 */     try (Connection conn = GDDBData.getConnection()) {
/* 193 */       boolean auto = conn.getAutoCommit();
/* 194 */       conn.setAutoCommit(false);
/*     */       
/* 196 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 197 */         ps.setString(1, factionID);
/* 198 */         ps.executeUpdate();
/* 199 */         ps.close();
/*     */         
/* 201 */         conn.commit();
/*     */       }
/* 203 */       catch (SQLException ex) {
/* 204 */         conn.rollback();
/*     */       } finally {
/*     */         
/* 207 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 210 */     } catch (SQLException sQLException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 215 */     DBFaction entry = get(record.getFileName());
/*     */     
/* 217 */     if (entry != null)
/*     */       return; 
/* 219 */     DBFaction faction = new DBFaction(record);
/*     */     
/* 221 */     String insert = "INSERT INTO GDC_FACTION VALUES (?,?,?,?,?)";
/*     */     
/* 223 */     try (Connection conn = GDDBData.getConnection()) {
/* 224 */       boolean auto = conn.getAutoCommit();
/* 225 */       conn.setAutoCommit(false);
/*     */       
/* 227 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/* 228 */         ps.setString(1, faction.factionID);
/* 229 */         ps.setBoolean(2, faction.bountyEnabled);
/* 230 */         ps.setBoolean(3, faction.questEnabled);
/* 231 */         ps.setString(4, faction.factionTag);
/* 232 */         ps.setString(5, faction.factionText);
/*     */         
/* 234 */         ps.executeUpdate();
/*     */         
/* 236 */         ps.clearParameters();
/*     */         
/* 238 */         ps.close();
/*     */         
/* 240 */         conn.commit();
/*     */       }
/* 242 */       catch (SQLException ex) {
/* 243 */         conn.rollback();
/*     */         
/* 245 */         GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_IN_GAME_CONFIG"));
/* 246 */         GDMsgLogger.addError(ex);
/*     */       } finally {
/*     */         
/* 249 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBFaction get(String factionID) {
/* 255 */     DBFaction faction = null;
/*     */     
/* 257 */     String command = "SELECT * FROM GDC_FACTION WHERE FACTION_ID = ?";
/*     */     
/* 259 */     try(Connection conn = GDDBData.getConnection(); 
/* 260 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 261 */       ps.setString(1, factionID);
/*     */       
/* 263 */       try (ResultSet rs = ps.executeQuery()) {
/* 264 */         List<DBFaction> list = wrap(rs);
/*     */         
/* 266 */         if (list.isEmpty()) { faction = null; }
/* 267 */         else { faction = list.get(0); }
/*     */         
/* 269 */         conn.commit();
/*     */       }
/* 271 */       catch (SQLException ex) {
/* 272 */         throw ex;
/*     */       }
/*     */     
/* 275 */     } catch (SQLException ex) {
/* 276 */       Object[] args = { factionID, "GDC_FACTION" };
/* 277 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 279 */       GDMsgLogger.addError(msg);
/* 280 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 283 */     return faction;
/*     */   }
/*     */   
/*     */   public static List<DBFaction> getAll() {
/* 287 */     List<DBFaction> list = null;
/*     */     
/* 289 */     String command = "SELECT * FROM GDC_FACTION";
/*     */     
/* 291 */     try(Connection conn = GDDBData.getConnection(); 
/* 292 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*     */       
/* 294 */       try (ResultSet rs = ps.executeQuery()) {
/* 295 */         list = wrap(rs);
/*     */         
/* 297 */         conn.commit();
/*     */       }
/* 299 */       catch (SQLException ex) {
/* 300 */         throw ex;
/*     */       }
/*     */     
/* 303 */     } catch (SQLException ex) {
/* 304 */       Object[] args = { "-", "GDC_FACTION" };
/* 305 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 307 */       GDMsgLogger.addError(msg);
/* 308 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 311 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBFaction> wrap(ResultSet rs) throws SQLException {
/* 315 */     List<DBFaction> list = new LinkedList<>();
/*     */     
/* 317 */     while (rs.next()) {
/* 318 */       DBFaction faction = new DBFaction();
/*     */       
/* 320 */       faction.factionID = rs.getString(1);
/* 321 */       faction.bountyEnabled = rs.getBoolean(2);
/* 322 */       faction.questEnabled = rs.getBoolean(3);
/* 323 */       faction.factionTag = rs.getString(4);
/* 324 */       faction.factionText = rs.getString(5);
/*     */       
/* 326 */       list.add(faction);
/*     */     } 
/*     */     
/* 329 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBFaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */