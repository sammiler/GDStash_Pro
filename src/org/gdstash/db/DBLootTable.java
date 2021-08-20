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
/*     */ import org.gdstash.item.GDItem;
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
/*     */ public class DBLootTable
/*     */ {
/*     */   private static final String TABLE_NAME = "GD_LOOTTABLE_HD";
/*     */   private static final int ROW_TABLE_ID = 1;
/*     */   private static final int ROW_NPRE_NSUF = 2;
/*     */   private static final int ROW_NPRE_RSUF = 3;
/*     */   private static final int ROW_RPRE_NSUF = 4;
/*     */   private static final int ROW_RPRE_RSUF = 5;
/*  36 */   private static ConcurrentHashMap<String, DBLootTable> hashBuffer = new ConcurrentHashMap<>();
/*     */   
/*     */   private String tableID;
/*     */   
/*     */   private boolean nPre_nSuf;
/*     */   
/*     */   private boolean nPre_rSuf;
/*     */   private boolean rPre_nSuf;
/*     */   private boolean rPre_rSuf;
/*     */   private List<DBLootTableItemAlloc> items;
/*     */   private List<DBLootTableAffixSetAlloc> affixSets;
/*     */   private List<DBAffix> magicPrefixes;
/*     */   private List<DBAffix> rarePrefixes;
/*     */   private List<DBAffix> magicSuffixes;
/*     */   private List<DBAffix> rareSuffixes;
/*     */   private List<String> itemIDs;
/*     */   
/*     */   public DBLootTable() {
/*  54 */     this.items = new LinkedList<>();
/*  55 */     this.affixSets = new LinkedList<>();
/*     */   }
/*     */   
/*     */   private DBLootTable(ARZRecord record) {
/*  59 */     this.tableID = record.getFileName();
/*     */     
/*  61 */     this.nPre_nSuf = record.getTableNormalPrefixSuffix();
/*  62 */     this.nPre_rSuf = record.getTableNormalPrefixRareSuffix();
/*  63 */     this.rPre_nSuf = record.getTableRarePrefixNormalSuffix();
/*  64 */     this.rPre_rSuf = record.getTableRarePrefixSuffix();
/*     */     
/*  66 */     this.items = record.getTableItemAllocList();
/*  67 */     this.affixSets = record.getTableAffixSetAllocList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTableID() {
/*  75 */     return this.tableID;
/*     */   }
/*     */   
/*     */   public boolean isMagicPrefixMagicSuffixAllowed() {
/*  79 */     return this.nPre_nSuf;
/*     */   }
/*     */   
/*     */   public boolean isMagicPrefixRareSuffixAllowed() {
/*  83 */     return this.nPre_rSuf;
/*     */   }
/*     */   
/*     */   public boolean isRarePrefixMagicSuffixAllowed() {
/*  87 */     return this.rPre_nSuf;
/*     */   }
/*     */   
/*     */   public boolean isRarePrefixRareSuffixAllowed() {
/*  91 */     return this.rPre_rSuf;
/*     */   }
/*     */   
/*     */   public List<DBLootTableItemAlloc> getItemAllocList() {
/*  95 */     return this.items;
/*     */   }
/*     */   
/*     */   public List<DBLootTableAffixSetAlloc> getAffixSetAllocList() {
/*  99 */     return this.affixSets;
/*     */   }
/*     */   
/*     */   public List<String> getItemIDs() {
/* 103 */     List<String> list = new LinkedList<>();
/*     */     
/* 105 */     if (this.items == null) return list;
/*     */     
/* 107 */     for (DBLootTableItemAlloc item : this.items) {
/* 108 */       if (item != null && item
/* 109 */         .getItemID() != null) {
/* 110 */         list.add(item.getItemID());
/*     */       }
/*     */     } 
/*     */     
/* 114 */     return list;
/*     */   }
/*     */   
/*     */   public List<String> getAffixSetIDs() {
/* 118 */     List<String> list = new LinkedList<>();
/*     */     
/* 120 */     if (this.affixSets == null) return list;
/*     */     
/* 122 */     for (DBLootTableAffixSetAlloc set : this.affixSets) {
/* 123 */       if (set != null && set
/* 124 */         .getAffixSetID() != null) {
/* 125 */         list.add(set.getAffixSetID());
/*     */       }
/*     */     } 
/*     */     
/* 129 */     return list;
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
/*     */   public static void clearBuffer() {
/* 141 */     hashBuffer.clear();
/*     */   }
/*     */   
/*     */   public static void createTables() throws SQLException {
/* 145 */     String dropTable = "DROP TABLE GD_LOOTTABLE_HD";
/* 146 */     String createTable = "CREATE TABLE GD_LOOTTABLE_HD (TABLE_ID    VARCHAR(256) NOT NULL, NPRE_NSUF   BOOLEAN, NPRE_RSUF   BOOLEAN, RPRE_NSUF   BOOLEAN, RPRE_RSUF   BOOLEAN, PRIMARY KEY (TABLE_ID))";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 154 */     try (Connection conn = GDDBData.getConnection()) {
/* 155 */       boolean auto = conn.getAutoCommit();
/* 156 */       conn.setAutoCommit(false);
/*     */       
/* 158 */       try (Statement st = conn.createStatement()) {
/* 159 */         if (GDDBUtil.tableExists(conn, "GD_LOOTTABLE_HD")) {
/* 160 */           st.execute(dropTable);
/*     */         }
/* 162 */         st.execute(createTable);
/* 163 */         st.close();
/*     */         
/* 165 */         conn.commit();
/*     */       }
/* 167 */       catch (SQLException ex) {
/* 168 */         conn.rollback();
/*     */         
/* 170 */         Object[] args = { "GD_LOOTTABLE_HD" };
/* 171 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_CREATE_TABLE", args);
/*     */         
/* 173 */         GDMsgLogger.addError(msg);
/*     */         
/* 175 */         throw ex;
/*     */       } finally {
/*     */         
/* 178 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */     
/* 182 */     DBLootTableItemAlloc.createTable();
/* 183 */     DBLootTableAffixSetAlloc.createTable();
/*     */   }
/*     */   
/*     */   public static void delete(String tableID) throws SQLException {
/* 187 */     String deleteEntry = "DELETE FROM GD_LOOTTABLE_HD WHERE TABLE_ID = ?";
/*     */     
/* 189 */     try (Connection conn = GDDBData.getConnection()) {
/* 190 */       boolean auto = conn.getAutoCommit();
/* 191 */       conn.setAutoCommit(false);
/*     */       
/* 193 */       try (PreparedStatement ps = conn.prepareStatement(deleteEntry)) {
/* 194 */         ps.setString(1, tableID);
/* 195 */         ps.executeUpdate();
/* 196 */         ps.close();
/*     */         
/* 198 */         DBLootTableItemAlloc.delete(conn, tableID);
/*     */         
/* 200 */         DBLootTableAffixSetAlloc.delete(conn, tableID);
/*     */         
/* 202 */         conn.commit();
/*     */       }
/* 204 */       catch (SQLException ex) {
/* 205 */         conn.rollback();
/*     */         
/* 207 */         Object[] args = { tableID, "GD_LOOTTABLE_HD" };
/* 208 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DEL_TABLE_BY_ID", args);
/*     */         
/* 210 */         GDMsgLogger.addError(msg);
/* 211 */         GDMsgLogger.addError(ex);
/*     */         
/* 213 */         throw ex;
/*     */       } finally {
/*     */         
/* 216 */         conn.setAutoCommit(auto);
/*     */       }
/*     */     
/* 219 */     } catch (SQLException ex) {
/* 220 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void insert(ARZRecord record) throws SQLException {
/* 225 */     DBLootTable entry = get(record.getFileName());
/*     */     
/* 227 */     if (entry != null)
/*     */       return; 
/* 229 */     DBLootTable table = new DBLootTable(record);
/*     */ 
/*     */     
/* 232 */     if (table.items == null)
/* 233 */       return;  if (table.items.isEmpty())
/* 234 */       return;  if (table.affixSets == null)
/* 235 */       return;  if (table.affixSets.isEmpty())
/*     */       return; 
/* 237 */     String insert = "INSERT INTO GD_LOOTTABLE_HD VALUES (?,?,?,?,?)";
/*     */     
/* 239 */     try (Connection conn = GDDBData.getConnection()) {
/* 240 */       boolean auto = conn.getAutoCommit();
/* 241 */       conn.setAutoCommit(false);
/*     */       
/* 243 */       try (PreparedStatement ps = conn.prepareStatement(insert)) {
/*     */         
/* 245 */         ps.setString(1, table.tableID);
/* 246 */         ps.setBoolean(2, table.nPre_nSuf);
/* 247 */         ps.setBoolean(3, table.nPre_rSuf);
/* 248 */         ps.setBoolean(4, table.rPre_nSuf);
/* 249 */         ps.setBoolean(5, table.rPre_rSuf);
/*     */         
/* 251 */         ps.executeUpdate();
/* 252 */         ps.close();
/*     */         
/* 254 */         conn.commit();
/*     */         
/* 256 */         DBLootTableItemAlloc.insert(conn, table);
/*     */         
/* 258 */         DBLootTableAffixSetAlloc.insert(conn, table);
/*     */       }
/* 260 */       catch (SQLException ex) {
/* 261 */         conn.rollback();
/*     */         
/* 263 */         Object[] args = { record.getFileName(), "GD_LOOTTABLE_HD" };
/* 264 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_INS_TABLE_BY_ID", args);
/*     */         
/* 266 */         GDMsgLogger.addLowError(msg);
/* 267 */         GDMsgLogger.addLowError(ex);
/*     */       } finally {
/*     */         
/* 270 */         conn.setAutoCommit(auto);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DBLootTable get(String tableID) {
/* 276 */     DBLootTable table = null;
/*     */     
/* 278 */     table = hashBuffer.get(tableID);
/*     */     
/* 280 */     if (table == null) {
/* 281 */       table = getDB(tableID);
/*     */       
/* 283 */       if (table != null) hashBuffer.put(table.tableID, table);
/*     */     
/*     */     } 
/* 286 */     return table;
/*     */   }
/*     */   
/*     */   private static DBLootTable getDB(String tableID) {
/* 290 */     DBLootTable table = null;
/*     */     
/* 292 */     String command = "SELECT * FROM GD_LOOTTABLE_HD WHERE TABLE_ID = ?";
/*     */     
/* 294 */     try(Connection conn = GDDBData.getConnection(); 
/* 295 */         PreparedStatement ps = conn.prepareStatement(command)) {
/* 296 */       ps.setString(1, tableID);
/*     */       
/* 298 */       try (ResultSet rs = ps.executeQuery()) {
/* 299 */         List<DBLootTable> list = wrap(rs);
/*     */         
/* 301 */         if (list.isEmpty()) { table = null; }
/* 302 */         else { table = list.get(0); }
/*     */         
/* 304 */         conn.commit();
/*     */       }
/* 306 */       catch (SQLException ex) {
/* 307 */         throw ex;
/*     */       }
/*     */     
/* 310 */     } catch (SQLException ex) {
/* 311 */       Object[] args = { tableID, "GD_LOOTTABLE_HD" };
/* 312 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 314 */       GDMsgLogger.addError(msg);
/* 315 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 318 */     return table;
/*     */   }
/*     */   
/*     */   public static List<DBLootTable> getByItemID(String itemID) {
/* 322 */     List<DBLootTable> list = new LinkedList<>();
/* 323 */     List<DBLootTableItemAlloc> items = null;
/*     */     
/* 325 */     items = DBLootTableItemAlloc.getByItemID(itemID);
/* 326 */     if (items == null) return list; 
/* 327 */     if (items.isEmpty()) return list;
/*     */     
/* 329 */     for (DBLootTableItemAlloc item : items) {
/* 330 */       DBLootTable table = get(item.getTableID());
/* 331 */       if (table != null) list.add(table);
/*     */     
/*     */     } 
/* 334 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBLootTable> wrap(ResultSet rs) throws SQLException {
/* 338 */     LinkedList<DBLootTable> list = new LinkedList<>();
/* 339 */     boolean found = false;
/*     */     
/* 341 */     while (rs.next()) {
/* 342 */       DBLootTable table = new DBLootTable();
/*     */       
/* 344 */       table.tableID = rs.getString(1);
/* 345 */       table.nPre_nSuf = rs.getBoolean(2);
/* 346 */       table.nPre_rSuf = rs.getBoolean(3);
/* 347 */       table.rPre_nSuf = rs.getBoolean(4);
/* 348 */       table.rPre_rSuf = rs.getBoolean(5);
/*     */       
/* 350 */       table.items = DBLootTableItemAlloc.getByTableID(table.tableID);
/* 351 */       table.affixSets = DBLootTableAffixSetAlloc.getByTableID(table.tableID);
/*     */       
/* 353 */       table.groupAffixes();
/*     */       
/* 355 */       list.add(table);
/*     */     } 
/*     */     
/* 358 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<DBAffix> getAllAffixes() {
/* 366 */     List<DBAffix> list = new LinkedList<>();
/*     */     
/* 368 */     for (DBAffix affix : this.rarePrefixes) {
/* 369 */       list.add(affix);
/*     */     }
/*     */     
/* 372 */     for (DBAffix affix : this.magicPrefixes) {
/* 373 */       list.add(affix);
/*     */     }
/*     */     
/* 376 */     for (DBAffix affix : this.rareSuffixes) {
/* 377 */       list.add(affix);
/*     */     }
/*     */     
/* 380 */     for (DBAffix affix : this.magicSuffixes) {
/* 381 */       list.add(affix);
/*     */     }
/*     */     
/* 384 */     return list;
/*     */   }
/*     */   
/*     */   public List<DBAffix> getMagicPrefixes() {
/* 388 */     return this.magicPrefixes;
/*     */   }
/*     */   
/*     */   public List<DBAffix> getRarePrefixes() {
/* 392 */     return this.rarePrefixes;
/*     */   }
/*     */   
/*     */   public List<DBAffix> getMagicSuffixes() {
/* 396 */     return this.magicSuffixes;
/*     */   }
/*     */   
/*     */   public List<DBAffix> getRareSuffixes() {
/* 400 */     return this.rareSuffixes;
/*     */   }
/*     */   
/*     */   public List<DBAffix> getFilterPrefixesForSuffix(DBAffix suffix, List<DBAffix> filterPrefixes) {
/* 404 */     List<DBAffix> filter = new LinkedList<>();
/*     */     
/* 406 */     if (suffix == null) {
/* 407 */       for (DBAffix affix : this.rarePrefixes) {
/* 408 */         if (filterPrefixes.contains(affix)) filter.add(affix);
/*     */       
/*     */       } 
/* 411 */       for (DBAffix affix : this.magicPrefixes) {
/* 412 */         if (filterPrefixes.contains(affix)) filter.add(affix);
/*     */       
/*     */       } 
/* 415 */       return filter;
/*     */     } 
/*     */     
/* 418 */     if (suffix.getAffixType() != 2) return filter;
/*     */     
/* 420 */     if ("Rare".equals(suffix.getRarity())) {
/* 421 */       if (!this.rareSuffixes.contains(suffix)) return filter;
/*     */       
/* 423 */       if (this.rPre_rSuf) {
/* 424 */         for (DBAffix affix : this.rarePrefixes) {
/* 425 */           if (filterPrefixes.contains(affix)) filter.add(affix);
/*     */         
/*     */         } 
/*     */       }
/* 429 */       if (this.nPre_rSuf) {
/* 430 */         for (DBAffix affix : this.magicPrefixes) {
/* 431 */           if (filterPrefixes.contains(affix)) filter.add(affix);
/*     */         
/*     */         } 
/*     */       }
/*     */     } else {
/*     */       
/* 437 */       if (!this.magicSuffixes.contains(suffix)) return filter;
/*     */       
/* 439 */       if (this.rPre_nSuf) {
/* 440 */         for (DBAffix affix : this.rarePrefixes) {
/* 441 */           if (filterPrefixes.contains(affix)) filter.add(affix);
/*     */         
/*     */         } 
/*     */       }
/* 445 */       if (this.nPre_nSuf) {
/* 446 */         for (DBAffix affix : this.magicPrefixes) {
/* 447 */           if (filterPrefixes.contains(affix)) filter.add(affix);
/*     */         
/*     */         } 
/*     */       }
/*     */     } 
/* 452 */     return filter;
/*     */   }
/*     */   
/*     */   public List<DBAffix> getFilterSuffixesForPrefix(DBAffix prefix, List<DBAffix> filterSuffixes) {
/* 456 */     List<DBAffix> filter = new LinkedList<>();
/*     */     
/* 458 */     if (prefix == null) {
/* 459 */       for (DBAffix affix : this.rareSuffixes) {
/* 460 */         if (filterSuffixes.contains(affix)) filter.add(affix);
/*     */       
/*     */       } 
/* 463 */       for (DBAffix affix : this.magicSuffixes) {
/* 464 */         if (filterSuffixes.contains(affix)) filter.add(affix);
/*     */       
/*     */       } 
/* 467 */       return filter;
/*     */     } 
/*     */     
/* 470 */     if (prefix.getAffixType() != 1) return filter;
/*     */     
/* 472 */     if ("Rare".equals(prefix.getRarity())) {
/* 473 */       if (!this.rarePrefixes.contains(prefix)) return filter;
/*     */       
/* 475 */       if (this.rPre_rSuf) {
/* 476 */         for (DBAffix affix : this.rareSuffixes) {
/* 477 */           if (filterSuffixes.contains(affix)) filter.add(affix);
/*     */         
/*     */         } 
/*     */       }
/* 481 */       if (this.rPre_nSuf) {
/* 482 */         for (DBAffix affix : this.magicSuffixes) {
/* 483 */           if (filterSuffixes.contains(affix)) filter.add(affix);
/*     */         
/*     */         } 
/*     */       }
/*     */     } else {
/*     */       
/* 489 */       if (!this.magicPrefixes.contains(prefix)) return filter;
/*     */       
/* 491 */       if (this.nPre_rSuf) {
/* 492 */         for (DBAffix affix : this.rareSuffixes) {
/* 493 */           if (filterSuffixes.contains(affix)) filter.add(affix);
/*     */         
/*     */         } 
/*     */       }
/* 497 */       if (this.nPre_nSuf) {
/* 498 */         for (DBAffix affix : this.magicSuffixes) {
/* 499 */           if (filterSuffixes.contains(affix)) filter.add(affix);
/*     */         
/*     */         } 
/*     */       }
/*     */     } 
/* 504 */     return filter;
/*     */   }
/*     */   
/*     */   public boolean containsItem(DBItem item) {
/* 508 */     return this.itemIDs.contains(item.getItemID());
/*     */   }
/*     */   
/*     */   public static void mixAffixes(GDItem item, List<DBAffix> allAffixes, List<DBAffix> addAffixes) {
/* 512 */     for (DBAffix affix : addAffixes) {
/*     */ 
/*     */       
/* 515 */       if (item != null && (
/* 516 */         item.getRarity().equals("Epic") || item
/* 517 */         .getRarity().equals("Legendary")) && 
/* 518 */         !item.getRarity().equals(affix.getRarity())) {
/*     */         continue;
/*     */       }
/*     */       
/* 522 */       if (!allAffixes.contains(affix)) allAffixes.add(affix); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void groupAffixes() {
/* 527 */     this.magicPrefixes = new LinkedList<>();
/* 528 */     this.rarePrefixes = new LinkedList<>();
/* 529 */     this.magicSuffixes = new LinkedList<>();
/* 530 */     this.rareSuffixes = new LinkedList<>();
/* 531 */     this.itemIDs = getItemIDs();
/*     */     
/* 533 */     for (DBLootTableAffixSetAlloc as : this.affixSets) {
/* 534 */       List<DBAffixSet.DBEntry> asEntries = as.getAffixEntries();
/*     */       
/* 536 */       if (asEntries == null)
/*     */         continue; 
/* 538 */       for (DBAffixSet.DBEntry asEntry : asEntries) {
/* 539 */         if (asEntry == null)
/*     */           continue; 
/* 541 */         String affixID = asEntry.getAffixID();
/*     */         
/* 543 */         if (affixID == null)
/*     */           continue; 
/* 545 */         DBAffix affix = DBAffix.get(affixID);
/*     */         
/* 547 */         if (affix == null)
/*     */           continue; 
/* 549 */         String rarity = affix.getRarity();
/* 550 */         int type = affix.getAffixType();
/*     */         
/* 552 */         if (rarity == null)
/*     */           continue; 
/* 554 */         if (type == 1) {
/* 555 */           if (rarity.equals("Magical") && 
/* 556 */             !this.magicPrefixes.contains(affix)) this.magicPrefixes.add(affix);
/*     */           
/* 558 */           if (rarity.equals("Rare") && 
/* 559 */             !this.rarePrefixes.contains(affix)) this.rarePrefixes.add(affix);
/*     */ 
/*     */ 
/*     */           
/* 563 */           if (rarity.equals("Epic") && 
/* 564 */             !this.magicPrefixes.contains(affix)) this.magicPrefixes.add(affix);
/*     */           
/* 566 */           if (rarity.equals("Legendary") && 
/* 567 */             !this.magicPrefixes.contains(affix)) this.magicPrefixes.add(affix);
/*     */         
/*     */         } 
/*     */         
/* 571 */         if (type == 2) {
/* 572 */           if (rarity.equals("Magical") && 
/* 573 */             !this.magicSuffixes.contains(affix)) this.magicSuffixes.add(affix);
/*     */           
/* 575 */           if (rarity.equals("Rare") && 
/* 576 */             !this.rareSuffixes.contains(affix)) this.rareSuffixes.add(affix);
/*     */ 
/*     */ 
/*     */           
/* 580 */           if (rarity.equals("Epic") && 
/* 581 */             !this.magicSuffixes.contains(affix)) this.magicSuffixes.add(affix);
/*     */           
/* 583 */           if (rarity.equals("Legendary") && 
/* 584 */             !this.magicSuffixes.contains(affix)) this.magicSuffixes.add(affix); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\DBLootTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */