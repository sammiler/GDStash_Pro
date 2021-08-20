/*     */ package org.gdstash.item;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.file.GDFileSize;
/*     */ import org.gdstash.file.GDReader;
/*     */ import org.gdstash.file.GDWriter;
/*     */ import org.gdstash.util.FileVersionException;
/*     */ import org.gdstash.util.GDConstants;
/*     */ import org.gdstash.util.GDLog;
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
/*     */ public class GDStash
/*     */   implements GDFileSize
/*     */ {
/*     */   public static final int STASH_TYPE_UNENCRYPTED = 0;
/*     */   public static final int STASH_TYPE_ENCRYPTED = 1;
/*     */   public static final int STASH_VERSION_UNENCRYPTED = 9;
/*     */   public static final int STASH_VERSION_ENCRYPTED = 4;
/*     */   public static final int STASH_VERSION_ENCRYPTED_EXPANSION = 5;
/*     */   public static final int VERSION_3 = 3;
/*     */   public static final int VERSION_4 = 4;
/*     */   public static final int VERSION_5 = 5;
/*     */   public static final String BEGIN_BLOCK = "begin_block";
/*     */   public static final String STASH_VERSION = "stashVersion";
/*     */   public static final String MOD = "mod";
/*     */   public static final String SACK_COUNT = "sackCount";
/*     */   public static final String SACK_WIDTH = "sackWidth";
/*     */   public static final String SACK_HEIGHT = "sackHeight";
/*     */   public static final String NUM_ITEMS = "numItems";
/*     */   public static final String STACK_COUNT = "stackCount";
/*     */   public static final String BASE_NAME = "baseName";
/*     */   public static final String PREFIX_NAME = "prefixName";
/*     */   public static final String SUFFIX_NAME = "suffixName";
/*     */   public static final String MODIFIER_NAME = "modifierName";
/*     */   public static final String RELIC_NAME = "relicName";
/*     */   public static final String RELIC_BONUS = "relicBonus";
/*     */   public static final String SEED = "seed";
/*     */   public static final String RELIC_SEED = "relicSeed";
/*     */   public static final String VAR1 = "var1";
/*     */   public static final String END_BLOCK = "end_block";
/*     */   public static final String X_OFFSET = "xOffset";
/*     */   public static final String Y_OFFSET = "yOffset";
/*     */   public static final String VELOCITY = "velocity";
/*     */   public static final String OWNER = "owner";
/*  68 */   public static final String BEGIN_BYTES = new String(new byte[] { -50, -6, 29, -80 });
/*  69 */   public static final String END_BYTES = new String(new byte[] { -34, -64, -83, -34 });
/*     */   
/*     */   private File file;
/*     */   
/*     */   private byte[] beginBlock;
/*     */   
/*     */   private int key;
/*     */   
/*     */   private int version;
/*     */   
/*     */   private String modName;
/*     */   private byte expansionStatus;
/*     */   private int numSacks;
/*     */   private byte[] endBlock;
/*     */   private List<GDStashPage> pages;
/*     */   private boolean hardcore;
/*     */   String filename;
/*     */   private boolean itemError;
/*     */   private boolean pageError;
/*     */   private boolean stashError;
/*     */   private boolean changed;
/*     */   
/*     */   public GDStash(File file) {
/*  92 */     this(file, null);
/*     */   }
/*     */   
/*     */   public GDStash(File file, GDLog log) {
/*  96 */     this.itemError = false;
/*  97 */     this.pageError = false;
/*  98 */     this.stashError = false;
/*  99 */     this.changed = false;
/*     */     
/* 101 */     this.file = file;
/*     */     
/* 103 */     this.pages = new LinkedList<>();
/*     */     
/* 105 */     this.filename = file.getName().toUpperCase(GDConstants.LOCALE_US);
/*     */     
/* 107 */     this.hardcore = false;
/* 108 */     if (this.filename.endsWith(".BSH")) this.hardcore = true; 
/* 109 */     if (this.filename.endsWith(".CSH")) this.hardcore = true; 
/* 110 */     if (this.filename.endsWith(".GSH")) this.hardcore = true;
/*     */     
/* 112 */     read(log);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasItemErrors() {
/* 120 */     return this.itemError;
/*     */   }
/*     */   
/*     */   public boolean hasPageErrors() {
/* 124 */     return this.pageError;
/*     */   }
/*     */   
/*     */   public boolean hasStashErrors() {
/* 128 */     return this.stashError;
/*     */   }
/*     */   
/*     */   public boolean hasChanged() {
/* 132 */     if (this.changed) return true;
/*     */     
/* 134 */     boolean change = false;
/*     */     
/* 136 */     for (GDStashPage page : this.pages) {
/* 137 */       change = (change || page.hasChanged());
/*     */     }
/*     */     
/* 140 */     return change;
/*     */   }
/*     */   
/*     */   public int getNumberOfPages() {
/* 144 */     return this.numSacks;
/*     */   }
/*     */   
/*     */   public List<GDStashPage> getPages() {
/* 148 */     return this.pages;
/*     */   }
/*     */   
/*     */   public List<GDItem> getItems() {
/* 152 */     List<GDItem> list = new LinkedList<>();
/*     */     
/* 154 */     for (GDStashPage page : this.pages) {
/* 155 */       list.addAll(page.getItemList());
/*     */     }
/*     */     
/* 158 */     return list;
/*     */   }
/*     */   
/*     */   public boolean isHardcore() {
/* 162 */     return this.hardcore;
/*     */   }
/*     */   
/*     */   public boolean isExpansionStash() {
/* 166 */     return (this.expansionStatus == 1);
/*     */   }
/*     */   
/*     */   public String getModName() {
/* 170 */     return this.modName;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 174 */     int h = 16;
/*     */     
/* 176 */     if (this.pages.isEmpty()) return h;
/*     */     
/* 178 */     for (GDStashPage page : this.pages) {
/* 179 */       if (page == null)
/*     */         continue; 
/* 181 */       if (h < page.height) h = page.height;
/*     */       
/* 183 */       for (GDItem item : page.items) {
/* 184 */         if (item == null)
/*     */           continue; 
/* 186 */         if (item.getImage() != null) {
/* 187 */           int y = item.getY() + item.getImage().getHeight() / 32;
/*     */           
/* 189 */           if (h < y) h = y;
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/* 194 */     return h;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 198 */     int w = 8;
/*     */     
/* 200 */     if (this.pages.isEmpty()) return w;
/*     */     
/* 202 */     for (GDStashPage page : this.pages) {
/* 203 */       if (page != null) {
/* 204 */         if (w < page.width) w = page.width;
/*     */         
/* 206 */         for (GDItem item : page.items) {
/* 207 */           if (item == null)
/*     */             continue; 
/* 209 */           if (item.getImage() != null) {
/* 210 */             int x = item.getX() + item.getImage().getWidth() / 32;
/*     */             
/* 212 */             if (w < x) w = x;
/*     */           
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 218 */     return w;
/*     */   }
/*     */   
/*     */   public File getFile() {
/* 222 */     return this.file;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModName(String modName) {
/* 230 */     String s = null;
/*     */     
/* 232 */     if (modName != null && 
/* 233 */       !modName.equals("")) s = modName;
/*     */ 
/*     */     
/* 236 */     if (this.modName == null) {
/* 237 */       if (s != null) {
/* 238 */         this.changed = true;
/*     */         
/* 240 */         this.modName = s;
/*     */       }
/*     */     
/* 243 */     } else if (!this.modName.equals(s)) {
/* 244 */       this.changed = true;
/*     */       
/* 246 */       this.modName = s;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFile(File file) {
/* 252 */     this.file = file;
/*     */     
/* 254 */     this.filename = file.getName().toUpperCase(GDConstants.LOCALE_US);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(GDLog log) {
/* 262 */     boolean oldFormat = false;
/* 263 */     String path = null;
/*     */     
/* 265 */     try (InputStream reader = new BufferedInputStream(new FileInputStream(this.file))) {
/* 266 */       path = this.file.getCanonicalPath();
/*     */       
/* 268 */       String s = GDReader.readString(reader, 15);
/*     */       
/* 270 */       s = s.substring(4);
/* 271 */       oldFormat = s.equals("begin_block");
/*     */     }
/* 273 */     catch (FileNotFoundException ex) {
/* 274 */       if (path != null) {
/* 275 */         Object[] args = { path };
/* 276 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*     */         
/* 278 */         if (log == null) {
/* 279 */           GDMsgLogger.addError(msg);
/*     */         } else {
/* 281 */           log.addError(msg);
/*     */         } 
/*     */       } 
/* 284 */       if (log == null) {
/* 285 */         GDMsgLogger.addError(ex);
/*     */       } else {
/* 287 */         log.addError(ex);
/*     */       } 
/*     */       
/* 290 */       this.stashError = true;
/*     */     }
/* 292 */     catch (Exception ex) {
/* 293 */       if (path != null) {
/* 294 */         Object[] args = { path };
/* 295 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*     */         
/* 297 */         if (log == null) {
/* 298 */           GDMsgLogger.addError(msg);
/*     */         } else {
/* 300 */           log.addError(msg);
/*     */         } 
/*     */       } 
/* 303 */       if (log == null) {
/* 304 */         GDMsgLogger.addError(ex);
/*     */       } else {
/* 306 */         log.addError(ex);
/*     */       } 
/*     */       
/* 309 */       this.stashError = true;
/*     */     } 
/*     */     
/* 312 */     if (this.stashError)
/*     */       return; 
/* 314 */     if (oldFormat) {
/* 315 */       readOldFormat();
/*     */     } else {
/* 317 */       readNewFormat(log);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readNewFormat(GDLog log) {
/* 322 */     String path = null;
/*     */     
/* 324 */     int val = 0;
/*     */     try {
/* 326 */       path = this.file.getCanonicalPath();
/*     */       
/* 328 */       GDReader.readEncFileToBuffer(this.file);
/*     */       
/* 330 */       this.key = GDReader.readKey();
/*     */       
/* 332 */       val = GDReader.readEncInt(true);
/* 333 */       if (val != 2) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */ 
/*     */       
/* 336 */       GDReader.Block block = new GDReader.Block();
/*     */       
/* 338 */       val = GDReader.readBlockStart(block);
/* 339 */       if (val != 18) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */       
/* 341 */       this.version = GDReader.readEncInt(true);
/* 342 */       if (this.version != 3 && this.version != 4 && this.version != 5)
/*     */       {
/*     */         
/* 345 */         throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */       }
/*     */       
/* 348 */       val = GDReader.readEncInt(false);
/* 349 */       if (val != 0) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */       
/* 351 */       this.modName = GDReader.readEncString();
/*     */       
/* 353 */       if (this.version >= 5) {
/* 354 */         this.expansionStatus = GDReader.readEncByte();
/*     */       }
/*     */       
/* 357 */       this.numSacks = GDReader.readEncInt(true);
/*     */       
/* 359 */       this.pages.clear(); int i;
/* 360 */       for (i = 0; i < this.numSacks; i++) {
/* 361 */         GDStashPage page = new GDStashPage(null, 1, this.version, 8, this.hardcore, path, i, log);
/*     */ 
/*     */         
/* 364 */         if (!page.hasPageErrors()) {
/* 365 */           this.pages.add(page);
/*     */           
/* 367 */           if (page.hasItemErrors()) {
/* 368 */             this.itemError = true;
/*     */           }
/*     */         } else {
/* 371 */           this.pageError = true;
/* 372 */           this.stashError = true;
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 379 */       GDReader.readBlockEnd(block);
/*     */     }
/* 381 */     catch (FileNotFoundException ex) {
/* 382 */       if (path != null) {
/* 383 */         Object[] args = { path };
/* 384 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*     */         
/* 386 */         if (log == null) {
/* 387 */           GDMsgLogger.addError(msg);
/*     */         } else {
/* 389 */           log.addError(msg);
/*     */         } 
/*     */       } 
/* 392 */       if (log == null) {
/* 393 */         GDMsgLogger.addError(ex);
/*     */       } else {
/* 395 */         log.addError(ex);
/*     */       } 
/*     */       
/* 398 */       this.stashError = true;
/*     */     }
/* 400 */     catch (FileVersionException ex) {
/* 401 */       if (path != null) {
/* 402 */         Object[] args = { path };
/* 403 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*     */         
/* 405 */         if (log == null) {
/* 406 */           GDMsgLogger.addError(msg);
/*     */         } else {
/* 408 */           log.addError(msg);
/*     */         } 
/*     */       } 
/* 411 */       if (log == null) {
/* 412 */         GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "INFO_UPDATE_GDSTASH"));
/* 413 */         GDMsgLogger.addError((Throwable)ex);
/*     */       } else {
/* 415 */         log.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "INFO_UPDATE_GDSTASH"));
/* 416 */         log.addError((Throwable)ex);
/*     */       } 
/*     */       
/* 419 */       this.stashError = true;
/*     */     }
/* 421 */     catch (Exception ex) {
/* 422 */       if (path != null) {
/* 423 */         Object[] args = { path };
/* 424 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*     */         
/* 426 */         if (log == null) {
/* 427 */           GDMsgLogger.addError(msg);
/*     */         } else {
/* 429 */           log.addError(msg);
/*     */         } 
/*     */       } 
/* 432 */       if (log == null) {
/* 433 */         GDMsgLogger.addError(ex);
/*     */       } else {
/* 435 */         log.addError(ex);
/*     */       } 
/*     */       
/* 438 */       this.stashError = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readOldFormat() {
/* 443 */     String path = null;
/*     */     
/* 445 */     try (InputStream reader = new BufferedInputStream(new FileInputStream(this.file))) {
/* 446 */       int width = 0;
/* 447 */       String s = null;
/*     */       
/* 449 */       path = this.file.getCanonicalPath();
/*     */       
/* 451 */       int len = 0;
/* 452 */       while (len != -1) {
/* 453 */         len = GDReader.readUInt(reader);
/*     */         
/* 455 */         if (len != -1) {
/* 456 */           s = GDReader.readString(reader, len);
/*     */           
/* 458 */           if (s.equals("begin_block")) {
/* 459 */             this.beginBlock = GDReader.readBytes4(reader);
/*     */           }
/* 461 */           if (s.equals("stashVersion")) {
/* 462 */             this.version = GDReader.readUInt(reader);
/*     */             
/* 464 */             if (this.version < 7 || this.version > 9) {
/* 465 */               Object[] args = { path, Integer.valueOf(this.version) };
/* 466 */               String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_STASH_VER", args);
/*     */               
/* 468 */               GDMsgLogger.addError(msg);
/*     */               
/* 470 */               this.stashError = true;
/*     */             } 
/*     */           } 
/* 473 */           if (s.equals("mod")) {
/* 474 */             GDReader.readBytes4(reader);
/*     */           }
/* 476 */           if (s.equals("sackCount")) {
/* 477 */             this.numSacks = GDReader.readInt(reader);
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 482 */           if (s.equals("sackWidth")) {
/*     */             
/* 484 */             width = GDReader.readInt(reader);
/*     */             
/* 486 */             this.numSacks = 1;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 492 */       if (!this.stashError) {
/*     */ 
/*     */         
/* 495 */         this.pages.clear(); int i;
/* 496 */         for (i = 0; i < this.numSacks; i++) {
/* 497 */           GDStashPage page = new GDStashPage(reader, 0, this.version, width, this.hardcore, path, i, null);
/*     */ 
/*     */           
/* 500 */           if (!page.hasPageErrors()) {
/* 501 */             this.pages.add(page);
/*     */             
/* 503 */             if (page.hasItemErrors()) {
/* 504 */               this.itemError = true;
/*     */             }
/*     */           } else {
/* 507 */             this.pageError = true;
/* 508 */             this.stashError = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */         
/* 514 */         if (!this.pageError) {
/* 515 */           len = GDReader.readUInt(reader);
/*     */           
/* 517 */           if (len != -1) {
/* 518 */             s = GDReader.readString(reader, len);
/* 519 */             if (s.equals("end_block")) {
/* 520 */               this.endBlock = GDReader.readBytes4(reader);
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 526 */     } catch (FileNotFoundException ex) {
/* 527 */       if (path != null) {
/* 528 */         Object[] args = { path };
/* 529 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*     */         
/* 531 */         GDMsgLogger.addError(msg);
/*     */       } 
/* 533 */       GDMsgLogger.addError(ex);
/*     */       
/* 535 */       this.stashError = true;
/*     */     }
/* 537 */     catch (Exception ex) {
/* 538 */       if (path != null) {
/* 539 */         Object[] args = { path };
/* 540 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*     */         
/* 542 */         GDMsgLogger.addError(msg);
/*     */       } 
/* 544 */       GDMsgLogger.addError(ex);
/*     */       
/* 546 */       this.stashError = true;
/*     */     } 
/*     */     
/* 549 */     this.version = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getByteSize() {
/* 554 */     int size = 0;
/*     */     
/* 556 */     size += 4;
/* 557 */     size += 4;
/* 558 */     size += 4;
/* 559 */     size += 4;
/*     */     
/* 561 */     size += 4;
/* 562 */     size += 4;
/* 563 */     size += 4;
/* 564 */     if (this.modName != null) size += this.modName.length();
/*     */     
/* 566 */     if (this.version >= 5) {
/* 567 */       size++;
/*     */     }
/*     */     
/* 570 */     size += 4;
/*     */     
/* 572 */     int count = 0;
/* 573 */     for (GDStashPage page : this.pages) {
/* 574 */       size += page.getByteSize();
/*     */       
/* 576 */       count++;
/*     */     } 
/*     */     
/* 579 */     size += 12 * count;
/* 580 */     size += 4;
/*     */     
/* 582 */     return size;
/*     */   }
/*     */   
/*     */   private void createBuffer() throws IOException {
/* 586 */     GDWriter.reserveBuffer(this);
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
/* 603 */     GDWriter.writeInt(1431655765);
/* 604 */     GDWriter.writeInt(2);
/*     */     
/* 606 */     GDReader.Block block = new GDReader.Block();
/* 607 */     GDWriter.writeBlockStart(block, 18);
/*     */ 
/*     */ 
/*     */     
/* 611 */     if (this.version < 4) this.version = 4;
/*     */     
/* 613 */     GDWriter.writeInt(this.version);
/* 614 */     GDWriter.writeInt(0);
/*     */     
/* 616 */     GDWriter.writeString(this.modName);
/*     */     
/* 618 */     if (this.version >= 5) {
/* 619 */       GDWriter.writeByte(this.expansionStatus);
/*     */     }
/*     */     
/* 622 */     GDWriter.writeInt(this.pages.size());
/*     */     
/* 624 */     for (GDStashPage page : this.pages) {
/* 625 */       page.write();
/*     */     }
/*     */     
/* 628 */     GDWriter.writeBlockEnd(block);
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/* 632 */     if (!hasChanged())
/*     */       return; 
/* 634 */     createBuffer();
/*     */     
/* 636 */     String path = this.file.getParent() + GDConstants.FILE_SEPARATOR;
/*     */     
/* 638 */     String tname = "transfer.t";
/* 639 */     if (this.filename.endsWith(".BST")) tname = "transfer.bt"; 
/* 640 */     if (this.filename.endsWith(".BSH")) tname = "transfer.bh"; 
/* 641 */     if (this.filename.endsWith(".CST")) tname = "transfer.ct"; 
/* 642 */     if (this.filename.endsWith(".CSH")) tname = "transfer.ch"; 
/* 643 */     if (this.filename.endsWith(".GSH")) tname = "transfer.h";
/*     */     
/* 645 */     File temp = new File(path + "temp_tmp.tmp");
/*     */     
/* 647 */     temp.createNewFile();
/*     */     
/* 649 */     GDWriter.writeBuffer(temp);
/*     */     
/* 651 */     File fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(0) }));
/* 652 */     File fNew = null; int i;
/* 653 */     for (i = 9; i >= 0; i--) {
/* 654 */       fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(i) }));
/* 655 */       fNew = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(i + 1) }));
/*     */       
/* 657 */       if (fCurr.exists()) {
/* 658 */         if (i == 9) {
/* 659 */           fCurr.delete();
/*     */         } else {
/* 661 */           fCurr.renameTo(fNew);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 666 */     this.file.renameTo(fCurr);
/* 667 */     temp.renameTo(this.file);
/*     */     
/* 669 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public void write2() throws IOException {
/* 673 */     if (!hasChanged())
/*     */       return; 
/* 675 */     String path = this.file.getParent() + GDConstants.FILE_SEPARATOR;
/*     */     
/* 677 */     String tname = "transfer.t";
/* 678 */     if (this.filename.endsWith(".BST")) tname = "transfer.bt"; 
/* 679 */     if (this.filename.endsWith(".BSH")) tname = "transfer.bh"; 
/* 680 */     if (this.filename.endsWith(".CST")) tname = "transfer.ct"; 
/* 681 */     if (this.filename.endsWith(".CSH")) tname = "transfer.ch"; 
/* 682 */     if (this.filename.endsWith(".GSH")) tname = "transfer.h";
/*     */ 
/*     */     
/* 685 */     File fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(0) }));
/* 686 */     File fNew = null; int i;
/* 687 */     for (i = 9; i >= 0; i--) {
/* 688 */       fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(i) }));
/* 689 */       fNew = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(i + 1) }));
/*     */       
/* 691 */       if (fCurr.exists()) {
/* 692 */         if (i == 9) {
/* 693 */           fCurr.delete();
/*     */         } else {
/* 695 */           fCurr.renameTo(fNew);
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 701 */     GDWriter.copyFile(this.file, fCurr);
/*     */ 
/*     */     
/*     */     try {
/* 705 */       createBuffer();
/*     */       
/* 707 */       GDWriter.writeBuffer(this.file);
/*     */       
/* 709 */       this.changed = false;
/*     */     }
/* 711 */     catch (IOException ex) {
/*     */       
/* 713 */       GDWriter.copyFile(fCurr, this.file);
/*     */       
/* 715 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeOldFormat() throws IOException {
/* 720 */     if (!hasChanged())
/*     */       return; 
/* 722 */     String separator = System.getProperty("file.separator");
/*     */     
/* 724 */     String path = this.file.getCanonicalPath();
/* 725 */     int pos = path.lastIndexOf(separator);
/* 726 */     if (pos != -1) path = path.substring(0, pos + 1);
/*     */     
/* 728 */     String tname = "transfer.t";
/* 729 */     if (this.filename.endsWith(".GSH")) tname = "transfer.h";
/*     */     
/* 731 */     File temp = new File(path + "temp_tmp.tmp");
/*     */     
/* 733 */     temp.createNewFile();
/*     */     
/* 735 */     FileOutputStream writer = new FileOutputStream(temp);
/*     */     
/* 737 */     writer.write(GDWriter.lengthToBytes4("begin_block"));
/* 738 */     writer.write("begin_block".getBytes(GDConstants.CHARSET_STASH));
/* 739 */     writer.write(this.beginBlock);
/*     */     
/* 741 */     writer.write(GDWriter.lengthToBytes4("stashVersion"));
/* 742 */     writer.write("stashVersion".getBytes(GDConstants.CHARSET_STASH));
/* 743 */     writer.write(GDWriter.intToBytes4(9));
/*     */     
/* 745 */     writer.write(GDWriter.lengthToBytes4("mod"));
/* 746 */     writer.write("mod".getBytes(GDConstants.CHARSET_STASH));
/* 747 */     writer.write(GDWriter.intToBytes4(0));
/*     */     
/* 749 */     writer.write(GDWriter.lengthToBytes4("sackCount"));
/* 750 */     writer.write("sackCount".getBytes(GDConstants.CHARSET_STASH));
/* 751 */     writer.write(GDWriter.intToBytes4(this.pages.size()));
/*     */     
/* 753 */     for (GDStashPage page : this.pages) {
/* 754 */       page.writeOldFormat(writer, GDConstants.CHARSET_STASH);
/*     */     }
/*     */     
/* 757 */     writer.write(GDWriter.lengthToBytes4("end_block"));
/* 758 */     writer.write("end_block".getBytes(GDConstants.CHARSET_STASH));
/* 759 */     writer.write(this.endBlock);
/*     */     
/* 761 */     writer.flush();
/* 762 */     writer.close();
/*     */     
/* 764 */     File fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(0) }));
/* 765 */     File fNew = null; int count;
/* 766 */     for (count = 9; count >= 0; count--) {
/* 767 */       fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(count) }));
/* 768 */       fNew = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(count + 1) }));
/*     */       
/* 770 */       if (fCurr.exists()) {
/* 771 */         if (count == 9) {
/* 772 */           fCurr.delete();
/*     */         } else {
/* 774 */           fCurr.renameTo(fNew);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 779 */     this.file.renameTo(fCurr);
/* 780 */     temp.renameTo(this.file);
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\item\GDStash.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */