/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.description.BonusDetail;
/*     */ import org.gdstash.description.BonusInfo;
/*     */ import org.gdstash.file.ARCDecompress;
/*     */ import org.gdstash.file.ARZRecord;
/*     */ import org.gdstash.file.GDWriter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DBConstellation
/*     */   implements Comparable<DBConstellation>
/*     */ {
/*     */   private static final String TABLE_NAME = "GDC_CONSTELLATION";
/*     */   private static final int ROW_CONSTELLATION_ID = 1;
/*     */   private static final int ROW_NAME = 2;
/*     */   private static final int ROW_DESCRIPTION = 3;
/*     */   private String constellationID;
/*     */   private String name;
/*     */   private String description;
/*  46 */   private List<DBConstellationAffinity> dbAffinities = new LinkedList<>();
/*  47 */   private List<DBConstellationStar> dbStars = new LinkedList<>();
/*     */ 
/*     */   
/*     */   public DBConstellation(ARZRecord record) {
/*  51 */     this();
/*     */     
/*  53 */     this.constellationID = record.getFileName();
/*     */     
/*  55 */     setName(record.getConstellationNameTag());
/*  56 */     setDescription(record.getConstellationInfoTag());
/*     */     
/*  58 */     this.dbAffinities = record.getConstellationAffinityList();
/*  59 */     this.dbStars = record.getConstellationStarList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  68 */     if (o == null) return false; 
/*  69 */     if (!o.getClass().equals(DBConstellation.class)) return false;
/*     */     
/*  71 */     DBConstellation constellation = (DBConstellation)o;
/*     */     
/*  73 */     return constellation.constellationID.equals(this.constellationID);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  78 */     return this.constellationID.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(DBConstellation o) {
/*  86 */     if (!o.getClass().equals(DBConstellation.class)) {
/*  87 */       Object[] args = { DBConstellation.class, o.toString() };
/*  88 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_NOT_OF_TYPE", args);
/*     */       
/*  90 */       throw new ClassCastException(msg);
/*     */     } 
/*     */     
/*  93 */     DBConstellation constellation = o;
/*     */     
/*  95 */     return this.constellationID.compareTo(constellation.constellationID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getConstellationID() {
/* 103 */     return this.constellationID;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 107 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 111 */     return this.description;
/*     */   }
/*     */   
/*     */   public List<DBConstellationAffinity> getAffinityList() {
/* 115 */     return this.dbAffinities;
/*     */   }
/*     */   
/*     */   public List<DBConstellationStar> getStarList() {
/* 119 */     return this.dbStars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setName(String name) {
/* 127 */     this.name = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_SKILLS, name, false);
/*     */   }
/*     */   
/*     */   private void setDescription(String description) {
/* 131 */     this.description = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_SKILLS, description, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTables() throws SQLException {
/* 139 */     String dropTable = "DROP TABLE GDC_CONSTELLATION";
/* 140 */     String createTable = "CREATE TABLE GDC_CONSTELLATION (CONSTELLATION_ID VARCHAR(256) NOT NULL, NAME             VARCHAR(256), DESCRIPTION      VARCHAR(1024), PRIMARY KEY (CONSTELLATION_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     try (Connection conn = GDDBData.getConnection()) {
/* 147 */       boolean auto = conn.getAutoCommit();
/* 148 */       conn.setAutoCommit(false);
/*     */       
/* 150 */       try (Statement st = conn.createStatement()) {
/* 151 */         if (GDDBUtil.tableExists(conn, "GDC_CONSTELLATION")) {
/* 152 */           st.execute(dropTable);
/*     */         }
/* 154 */         st.execute(createTable);
/* 155 */         st.close();
/*     */         
/* 157 */         conn.commit();
/*     */       }
/* 159 */       catch (SQLException ex) {
/* 160 */         conn.rollback();
/*     */         
/* 162 */         Object[] args = { "GDC_CONSTELLATION" };
/* 163 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 165 */         GDMsgLogger.addError(msg);
/*     */         
/* 167 */         throw ex;
/*     */       } finally {
/*     */         
/* 170 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */     
/* 174 */     DBConstellationAffinity.createTable();
/* 175 */     DBConstellationStar.createTable();
/*     */   }
/*     */   
/*     */   public static void delete(String constellationID) throws SQLException {
/* 179 */     String deleteEntry = "DELETE FROM GDC_CONSTELLATION WHERE CONSTELLATION_ID = ?";
/*     */     
/* 181 */     try (Connection conn = GDDBData.getConnection()) {
/* 182 */       boolean auto = conn.getAutoCommit();
/* 183 */       conn.setAutoCommit(false);
/*     */       
/* 185 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 186 */         ps.setString(1, constellationID);
/* 187 */         ps.executeUpdate();
/* 188 */         ps.close();
/*     */         
/* 190 */         DBConstellationAffinity.delete(conn, constellationID);
/* 191 */         DBConstellationStar.delete(conn, constellationID);
/*     */         
/* 193 */         conn.commit();
/*     */       }
/* 195 */       catch (SQLException ex) {
/* 196 */         conn.rollback();
/*     */         
/* 198 */         Object[] args = { constellationID, "GDC_CONSTELLATION" };
/* 199 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*     */         
/* 201 */         GDMsgLogger.addError(msg);
/* 202 */         GDMsgLogger.addError(ex);
/*     */         
/* 204 */         throw ex;
/*     */       } finally {
/*     */         
/* 207 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 210 */     } catch (SQLException ex) {
/* 211 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 216 */     DBConstellation entry = get(record.getFileName());
/*     */     
/* 218 */     if (entry != null)
/*     */       return; 
/* 220 */     DBConstellation constellation = new DBConstellation(record);
/*     */ 
/*     */     
/* 223 */     if (constellation.getStarList() == null)
/* 224 */       return;  if (constellation.getStarList().isEmpty())
/*     */       return; 
/* 226 */     String insert = "INSERT INTO GDC_CONSTELLATION VALUES (?,?,?)";
/*     */     
/* 228 */     try (Connection conn = GDDBData.getConnection()) {
/* 229 */       boolean auto = conn.getAutoCommit();
/* 230 */       conn.setAutoCommit(false);
/*     */       
/* 232 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/*     */         
/* 234 */         ps.setString(1, constellation.constellationID);
/* 235 */         ps.setString(2, constellation.name);
/* 236 */         ps.setString(3, constellation.description);
/*     */         
/* 238 */         ps.executeUpdate();
/*     */         
/* 240 */         ps.close();
/* 241 */         conn.commit();
/*     */         
/* 243 */         DBConstellationAffinity.insert(conn, constellation);
/* 244 */         DBConstellationStar.insert(conn, constellation);
/*     */       }
/* 246 */       catch (SQLException ex) {
/* 247 */         conn.rollback();
/*     */         
/* 249 */         Object[] args = { constellation.constellationID, "GDC_CONSTELLATION" };
/* 250 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */         
/* 252 */         GDMsgLogger.addLowError(msg);
/* 253 */         GDMsgLogger.addLowError(ex);
/*     */       } finally {
/*     */         
/* 256 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insertMod(ARZRecord record) throws SQLException {
/* 262 */     DBItemSet constellation = DBItemSet.get(record.getFileName());
/*     */     
/* 264 */     if (constellation != null) {
/* 265 */       delete(record.getFileName());
/*     */     }
/*     */     
/* 268 */     insert(record);
/*     */   }
/*     */   
/*     */   public static DBConstellation get(String constellationID) {
/* 272 */     DBConstellation constellation = null;
/*     */     
/* 274 */     String command = "SELECT * FROM GDC_CONSTELLATION WHERE CONSTELLATION_ID = ?";
/*     */     
/* 276 */     try(Connection conn = GDDBData.getConnection(); 
/* 277 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 278 */       ps.setString(1, constellationID);
/*     */       
/* 280 */       try (ResultSet rs = ps.executeQuery()) {
/* 281 */         List<DBConstellation> list = wrap(rs);
/*     */         
/* 283 */         if (list.isEmpty()) { constellation = null; }
/* 284 */         else { constellation = list.get(0); }
/*     */         
/* 286 */         conn.commit();
/*     */       }
/* 288 */       catch (SQLException ex) {
/* 289 */         throw ex;
/*     */       }
/*     */     
/* 292 */     } catch (SQLException ex) {
/* 293 */       Object[] args = { constellationID, "GDC_CONSTELLATION" };
/* 294 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 296 */       GDMsgLogger.addError(msg);
/* 297 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 300 */     return constellation;
/*     */   }
/*     */   
/*     */   public static List<DBConstellation> getAll() {
/* 304 */     List<DBConstellation> list = null;
/*     */     
/* 306 */     String command = "SELECT * FROM GDC_CONSTELLATION";
/*     */     
/* 308 */     try(Connection conn = GDDBData.getConnection(); 
/* 309 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*     */       
/* 311 */       try (ResultSet rs = ps.executeQuery()) {
/* 312 */         list = wrap(rs);
/*     */         
/* 314 */         conn.commit();
/*     */       }
/* 316 */       catch (SQLException ex) {
/* 317 */         throw ex;
/*     */       }
/*     */     
/* 320 */     } catch (SQLException ex) {
/* 321 */       Object[] args = { "<all>", "GDC_CONSTELLATION" };
/* 322 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 324 */       GDMsgLogger.addError(msg);
/* 325 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 328 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBConstellation> wrap(ResultSet rs) throws SQLException {
/* 332 */     LinkedList<DBConstellation> list = new LinkedList<>();
/*     */     
/* 334 */     while (rs.next()) {
/* 335 */       DBConstellation constellation = new DBConstellation();
/*     */       
/* 337 */       constellation.constellationID = rs.getString(1);
/* 338 */       constellation.name = rs.getString(2);
/* 339 */       constellation.description = rs.getString(3);
/*     */       
/* 341 */       constellation.dbAffinities = DBConstellationAffinity.getByConstellationID(constellation.constellationID);
/* 342 */       constellation.dbStars = DBConstellationStar.getByConstellationID(constellation.constellationID);
/*     */ 
/*     */       
/* 345 */       list.add(constellation);
/*     */     } 
/*     */     
/* 348 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createFile(String filename) {
/* 356 */     List<DBConstellation> list = getAll();
/*     */     
/* 358 */     String data = "";
/*     */     
/* 360 */     data = data + getHeaderLine();
/*     */     
/* 362 */     Collections.sort(list);
/* 363 */     for (DBConstellation constellation : list) {
/* 364 */       Collections.sort(constellation.dbAffinities);
/* 365 */       Collections.sort(constellation.dbStars);
/*     */       
/* 367 */       data = data + getConstellationSummary(constellation);
/*     */       
/* 369 */       for (DBConstellationStar star : constellation.dbStars) {
/* 370 */         data = data + getStarSummary(constellation, star);
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 375 */       GDWriter.write(filename, data);
/*     */     }
/* 377 */     catch (Exception ex) {
/* 378 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getHeaderLine() {
/* 383 */     String s = "Constellation\tAscendant\tChaos\tEdlritch\tOrder\tPrimordial\tStar\tBonus Value\tBonus Type\tPet Bonus\t# Stars\tAscendant\tChaos\tEdlritch\tOrder\tPrimordial" + GDConstants.LINE_SEPARATOR;
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
/* 401 */     return s;
/*     */   }
/*     */   
/*     */   private static String getConstellationSummary(DBConstellation constellation) {
/* 405 */     String s = constellation.name;
/*     */     
/* 407 */     int tabCount = 0;
/* 408 */     for (DBConstellationAffinity affinity : constellation.dbAffinities) {
/* 409 */       if (!affinity.isRequired())
/*     */         continue; 
/* 411 */       if (affinity.getAffinity().equals("Ascendant")) {
/* 412 */         while (tabCount < 1) {
/* 413 */           s = s + '\t';
/*     */           
/* 415 */           tabCount++;
/*     */         } 
/*     */         
/* 418 */         s = s + Integer.toString(affinity.getPoints());
/*     */       } 
/*     */       
/* 421 */       if (affinity.getAffinity().equals("Chaos")) {
/* 422 */         while (tabCount < 2) {
/* 423 */           s = s + '\t';
/*     */           
/* 425 */           tabCount++;
/*     */         } 
/*     */         
/* 428 */         s = s + Integer.toString(affinity.getPoints());
/*     */       } 
/*     */       
/* 431 */       if (affinity.getAffinity().equals("Eldritch")) {
/* 432 */         while (tabCount < 3) {
/* 433 */           s = s + '\t';
/*     */           
/* 435 */           tabCount++;
/*     */         } 
/*     */         
/* 438 */         s = s + Integer.toString(affinity.getPoints());
/*     */       } 
/*     */       
/* 441 */       if (affinity.getAffinity().equals("Order")) {
/* 442 */         while (tabCount < 4) {
/* 443 */           s = s + '\t';
/*     */           
/* 445 */           tabCount++;
/*     */         } 
/*     */         
/* 448 */         s = s + Integer.toString(affinity.getPoints());
/*     */       } 
/*     */       
/* 451 */       if (affinity.getAffinity().equals("Primordial")) {
/* 452 */         while (tabCount < 5) {
/* 453 */           s = s + '\t';
/*     */           
/* 455 */           tabCount++;
/*     */         } 
/*     */         
/* 458 */         s = s + Integer.toString(affinity.getPoints());
/*     */       } 
/*     */     } 
/*     */     
/* 462 */     while (tabCount < 6) {
/* 463 */       s = s + '\t';
/*     */       
/* 465 */       tabCount++;
/*     */     } 
/*     */     
/* 468 */     s = s + "0";
/*     */     
/* 470 */     while (tabCount < 10) {
/* 471 */       s = s + '\t';
/*     */       
/* 473 */       tabCount++;
/*     */     } 
/*     */     
/* 476 */     s = s + Integer.toString(constellation.dbStars.size());
/*     */     
/* 478 */     for (DBConstellationAffinity affinity : constellation.dbAffinities) {
/* 479 */       if (affinity.isRequired())
/*     */         continue; 
/* 481 */       if (affinity.getAffinity().equals("Ascendant")) {
/* 482 */         while (tabCount < 11) {
/* 483 */           s = s + '\t';
/*     */           
/* 485 */           tabCount++;
/*     */         } 
/*     */         
/* 488 */         s = s + Integer.toString(affinity.getPoints());
/*     */       } 
/*     */       
/* 491 */       if (affinity.getAffinity().equals("Chaos")) {
/* 492 */         while (tabCount < 12) {
/* 493 */           s = s + '\t';
/*     */           
/* 495 */           tabCount++;
/*     */         } 
/*     */         
/* 498 */         s = s + Integer.toString(affinity.getPoints());
/*     */       } 
/*     */       
/* 501 */       if (affinity.getAffinity().equals("Eldritch")) {
/* 502 */         while (tabCount < 13) {
/* 503 */           s = s + '\t';
/*     */           
/* 505 */           tabCount++;
/*     */         } 
/*     */         
/* 508 */         s = s + Integer.toString(affinity.getPoints());
/*     */       } 
/*     */       
/* 511 */       if (affinity.getAffinity().equals("Order")) {
/* 512 */         while (tabCount < 14) {
/* 513 */           s = s + '\t';
/*     */           
/* 515 */           tabCount++;
/*     */         } 
/*     */         
/* 518 */         s = s + Integer.toString(affinity.getPoints());
/*     */       } 
/*     */       
/* 521 */       if (affinity.getAffinity().equals("Primordial")) {
/* 522 */         while (tabCount < 15) {
/* 523 */           s = s + '\t';
/*     */           
/* 525 */           tabCount++;
/*     */         } 
/*     */         
/* 528 */         s = s + Integer.toString(affinity.getPoints());
/*     */       } 
/*     */     } 
/*     */     
/* 532 */     s = s + GDConstants.LINE_SEPARATOR;
/*     */     
/* 534 */     return s;
/*     */   }
/*     */   
/*     */   private static String getStarSummary(DBConstellation constellation, DBConstellationStar star) {
/* 538 */     String pre = constellation.name;
/*     */     
/* 540 */     int tabCount = 0;
/*     */     
/* 542 */     while (tabCount < 6) {
/* 543 */       pre = pre + '\t';
/*     */       
/* 545 */       tabCount++;
/*     */     } 
/*     */     
/* 548 */     pre = pre + Integer.toString(star.getIndex()) + '\t';
/*     */     
/* 550 */     String all = "";
/* 551 */     DBSkill skill = star.getSkill();
/*     */     
/* 553 */     if (skill.isDevotion()) {
/* 554 */       String s = pre + skill.getName() + '\t' + "Skill";
/*     */       
/* 556 */       int tc2 = tabCount;
/* 557 */       while (tc2 < 15) {
/* 558 */         s = s + '\t';
/*     */         
/* 560 */         tc2++;
/*     */       } 
/*     */       
/* 563 */       all = all + s + GDConstants.LINE_SEPARATOR;
/*     */     } 
/* 565 */     if (skill.getSkillID().equals("records/skills/devotion/tier1_27c.dbr")) {
/* 566 */       System.out.println("lala);");
/*     */     }
/* 568 */     List<DBStat> stats = DBStat.getByLevel(skill.getStatListNoPet(), 1);
/* 569 */     List<BonusInfo> infos = BonusDetail.createStatInfos(stats, null);
/*     */     
/* 571 */     for (BonusInfo info : infos) {
/* 572 */       String s = pre;
/*     */       
/* 574 */       s = s + info.text + '\t';
/* 575 */       s = s + info.statDesc + '\t';
/*     */       
/* 577 */       int tc2 = tabCount;
/* 578 */       while (tc2 < 15) {
/* 579 */         s = s + '\t';
/*     */         
/* 581 */         tc2++;
/*     */       } 
/*     */       
/* 584 */       all = all + s + GDConstants.LINE_SEPARATOR;
/*     */     } 
/*     */     
/* 587 */     if (skill.getPetSkill() != null) {
/* 588 */       stats = DBStat.getByLevel(skill.getPetSkill().getStatList(), 1);
/*     */       
/* 590 */       infos = BonusDetail.createStatInfos(stats, null);
/*     */       
/* 592 */       for (BonusInfo info : infos) {
/* 593 */         String s = pre;
/*     */         
/* 595 */         s = s + info.text + '\t';
/* 596 */         s = s + info.statDesc + '\t';
/* 597 */         s = s + "Y" + '\t';
/*     */         
/* 599 */         int tc2 = tabCount;
/* 600 */         while (tc2 < 15) {
/* 601 */           s = s + '\t';
/*     */           
/* 603 */           tc2++;
/*     */         } 
/*     */         
/* 606 */         all = all + s + GDConstants.LINE_SEPARATOR;
/*     */       } 
/*     */     } 
/*     */     
/* 610 */     return all;
/*     */   }
/*     */   
/*     */   public DBConstellation() {}
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBConstellation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */