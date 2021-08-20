/*     */ package org.gdstash.item;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
import java.io.IOException;
/*     */ import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
/*     */ import org.gdstash.db.DBItem;
/*     */ import org.gdstash.db.DBStashItem;
import org.gdstash.db.ItemClass;
import org.gdstash.file.GDFileSize;
import org.gdstash.file.GDParseException;
import org.gdstash.file.GDReader;
/*     */ import org.gdstash.file.GDWriter;
/*     */ import org.gdstash.util.*;
/*     */
/*     */
/*     */

/*     */
/*     */ public class GDTransmute implements GDFileSize {
/*     */   public static final int ITEM_TYPE_UNSUPPORTED = -1;
/*     */   public static final int ITEM_TYPE_HEAD = 1;
/*     */   public static final int ITEM_TYPE_TORSO = 3;
/*     */   public static final int ITEM_TYPE_LEGS = 4;
/*     */   public static final int ITEM_TYPE_FEET = 5;
/*     */   public static final int ITEM_TYPE_HANDS = 7;
/*     */   public static final int ITEM_TYPE_OFFHAND = 8;
/*     */   public static final int ITEM_TYPE_WEAPON = 9;
/*     */   public static final int ITEM_TYPE_SHOULDERS = 14;
/*     */   public static final int ITEM_TYPE_MEDAL = 15;
/*     */   private static final int VERSION_1 = 1;
/*     */   private static final int VERSION_2 = 2;
/*     */   private File file;
/*     */   private int key;
/*     */   private int version;
/*     */   private boolean fileError;
/*     */   private String modName;
/*     */   private byte expansionStatus;
/*     */   private String filename;
/*     */   private boolean hardcore;
/*     */   private List<GDTransmuteType> types;
/*     */   
/*     */   private static class SortItemByPicLevel implements Comparator<DBItem> {
/*     */     public int compare(DBItem i1, DBItem i2) {
/*  38 */       if (i1 == null) return 1; 
/*  39 */       if (i2 == null) return -1;
/*     */       
/*  41 */       int level = i1.getItemLevel() - i2.getItemLevel();
/*  42 */       if (level != 0) return level;
/*     */       
/*  44 */       String mesh1 = i1.getMeshID();
/*  45 */       String mesh2 = i2.getMeshID();
/*     */       
/*  47 */       if (mesh1 == null && mesh2 != null) return 1; 
/*  48 */       if (mesh1 != null && mesh2 == null) return -1; 
/*  49 */       if (mesh1 != null && mesh2 != null) {
/*  50 */         int mesh = mesh1.compareTo(mesh2);
/*     */         
/*  52 */         if (mesh != 0) return mesh;
/*     */       
/*     */       } 
/*  55 */       String base1 = i1.getBaseTextureID();
/*  56 */       String base2 = i2.getBaseTextureID();
/*     */       
/*  58 */       if (base1 == null && base2 != null) return -1; 
/*  59 */       if (base1 != null && base2 == null) return 1; 
/*  60 */       if (base1 != null && base2 != null) {
/*  61 */         int base = base1.compareTo(base2);
/*     */         
/*  63 */         if (base != 0) return base;
/*     */       
/*     */       } 
/*  66 */       String bump1 = i1.getBumpTextureID();
/*  67 */       String bump2 = i2.getBumpTextureID();
/*     */       
/*  69 */       if (bump1 == null && bump2 != null) return -1; 
/*  70 */       if (bump1 != null && bump2 == null) return 1; 
/*  71 */       if (bump1 != null && bump2 != null) {
/*  72 */         int bump = bump1.compareTo(bump2);
/*     */         
/*  74 */         if (bump != 0) return bump;
/*     */       
/*     */       } 
/*  77 */       String glow1 = i1.getGlowTextureID();
/*  78 */       String glow2 = i2.getGlowTextureID();
/*     */       
/*  80 */       if (glow1 == null && glow2 != null) return -1; 
/*  81 */       if (glow1 != null && glow2 == null) return 1; 
/*  82 */       if (glow1 != null && glow2 != null) {
/*  83 */         int glow = glow1.compareTo(glow2);
/*     */         
/*  85 */         if (glow != 0) return glow;
/*     */       
/*     */       } 
/*  88 */       String shader1 = i1.getShaderID();
/*  89 */       String shader2 = i2.getShaderID();
/*     */       
/*  91 */       if (shader1 == null && shader2 != null) return -1; 
/*  92 */       if (shader1 != null && shader2 == null) return 1; 
/*  93 */       if (shader1 != null && shader2 != null) {
/*  94 */         int shader = shader1.compareTo(shader2);
/*     */         
/*  96 */         if (shader != 0) return shader;
/*     */       
/*     */       } 
/*  99 */       int level1 = i1.getRequiredlevel();
/* 100 */       int level2 = i2.getRequiredlevel();
/*     */       
/* 102 */       int delta = level1 - level2;
/*     */       
/* 104 */       if (delta != 0) return delta;
/*     */       
/* 106 */       int itemID = i1.getItemID().compareTo(i2.getItemID());
/*     */       
/* 108 */       return itemID;
/*     */     }
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
/*     */     private SortItemByPicLevel() {}
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GDTransmute(File file) {
/* 140 */     this.file = file;
/* 141 */     this.fileError = false;
/*     */     
/* 143 */     this.filename = file.getName().toUpperCase(GDConstants.LOCALE_US);
/*     */     
/* 145 */     this.hardcore = false;
/* 146 */     if (this.filename.endsWith(".BSH")) this.hardcore = true; 
/* 147 */     if (this.filename.endsWith(".CSH")) this.hardcore = true; 
/* 148 */     if (this.filename.endsWith(".GSH")) this.hardcore = true;
/*     */     
/* 150 */     this.types = new LinkedList<>();
/*     */   }
/*     */   
/*     */   public boolean hasErrors() {
/* 154 */     return this.fileError;
/*     */   }
/*     */   
/*     */   public void insertCollectedItems() {
/* 158 */     List<DBItem> list = DBStashItem.getAllDistinct(this.hardcore);
/*     */     
/* 160 */     insertItems(list);
/*     */   }
/*     */   
/*     */   public void insertAllItems() {
/* 164 */     List<DBItem> list = DBItem.getAll();
/*     */     
/* 166 */     insertItems(list);
/*     */   }
/*     */   
/*     */   private void insertItems(List<DBItem> list) {
/* 170 */     List<DBItem> condensed = condenseItems(list);
/*     */     
/* 172 */     for (DBItem dbi : condensed) {
/* 173 */       insertItem(dbi);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean containsItem(List<DBItem> list, DBItem item) {
/* 178 */     if (item == null) return true;
/*     */     
/* 180 */     String meshID = item.getMeshID();
/* 181 */     String baseID = item.getBaseTextureID();
/* 182 */     String bumpID = item.getBumpTextureID();
/* 183 */     String glowID = item.getGlowTextureID();
/* 184 */     String shaderID = item.getShaderID();
/*     */     
/* 186 */     if (meshID == null) return true;
/*     */     
/* 188 */     boolean found = false;
/*     */     
/* 190 */     for (DBItem dbi : list) {
/* 191 */       if (dbi != null && 
/* 192 */         meshID.equals(dbi.getMeshID())) {
/* 193 */         String base2 = dbi.getBaseTextureID();
/* 194 */         String bump2 = dbi.getBumpTextureID();
/* 195 */         String glow2 = dbi.getGlowTextureID();
/* 196 */         String shader2 = dbi.getShaderID();
/*     */         
/* 198 */         boolean baseFound = false;
/* 199 */         boolean bumpFound = false;
/* 200 */         boolean glowFound = false;
/* 201 */         boolean shaderFound = false;
/*     */         
/* 203 */         if (baseID == null) {
/* 204 */           if (base2 == null) {
/* 205 */             baseFound = true;
/*     */           }
/*     */         }
/* 208 */         else if (baseID.equals(base2)) {
/* 209 */           baseFound = true;
/*     */         } 
/*     */ 
/*     */         
/* 213 */         if (bumpID == null) {
/* 214 */           if (bump2 == null) {
/* 215 */             bumpFound = true;
/*     */           }
/*     */         }
/* 218 */         else if (bumpID.equals(bump2)) {
/* 219 */           bumpFound = true;
/*     */         } 
/*     */ 
/*     */         
/* 223 */         if (glowID == null) {
/* 224 */           if (glow2 == null) {
/* 225 */             glowFound = true;
/*     */           }
/*     */         }
/* 228 */         else if (glowID.equals(glow2)) {
/* 229 */           glowFound = true;
/*     */         } 
/*     */ 
/*     */         
/* 233 */         if (shaderID == null) {
/* 234 */           if (shader2 == null) {
/* 235 */             shaderFound = true;
/*     */           }
/*     */         }
/* 238 */         else if (shaderID.equals(shader2)) {
/* 239 */           shaderFound = true;
/*     */         } 
/*     */ 
/*     */         
/* 243 */         if (baseFound && bumpFound && glowFound && shaderFound) {
/* 244 */           found = true;
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 252 */     return found;
/*     */   }
/*     */   
/*     */   private List<DBItem> condenseItems(List<DBItem> list) {
/* 256 */     List<DBItem> condensed = new LinkedList<>();
/*     */     
/* 258 */     Collections.sort(list, new SortItemByPicLevel());
/*     */     
/* 260 */     for (DBItem dbi : list) {
/* 261 */       if (dbi.isEnemyOnly())
/*     */         continue; 
/* 263 */       int type = ItemClass.getTransmuteType(dbi.getItemClass());
/* 264 */       if (type == -1)
/*     */         continue; 
/* 266 */       String meshID = dbi.getMeshID();
/* 267 */       if (meshID == null)
/*     */         continue; 
/* 269 */       boolean found = containsItem(condensed, dbi);
/*     */       
/* 271 */       if (!found) {
/* 272 */         condensed.add(dbi);
/*     */       }
/*     */     } 
/*     */     
/* 276 */     return condensed;
/*     */   }
/*     */   
/*     */   private void insertItem(DBItem item) {
/* 280 */     int type = ItemClass.getTransmuteType(item.getItemClass());
/*     */     
/* 282 */     boolean found = false;
/*     */     
/* 284 */     for (GDTransmuteType tt : this.types) {
/* 285 */       if (tt.getTransmuteType() == type) {
/* 286 */         found = true;
/*     */         
/* 288 */         tt.insertItem(item);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 294 */     if (!found) {
/* 295 */       GDTransmuteType tt = new GDTransmuteType(type);
/*     */       
/* 297 */       tt.insertItem(item);
/*     */       
/* 299 */       this.types.add(tt);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeItem(DBItem item) {
/* 304 */     int type = ItemClass.getTransmuteType(item.getItemClass());
/*     */     
/* 306 */     boolean found = false;
/*     */     
/* 308 */     for (GDTransmuteType tt : this.types) {
/* 309 */       if (tt.getTransmuteType() == type) {
/* 310 */         found = true;
/*     */         
/* 312 */         tt.removeItem(item);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(GDLog log) {
/* 324 */     String path = null;
/*     */     
/* 326 */     int val = 0;
/*     */     try {
/* 328 */       path = this.file.getCanonicalPath();
/*     */       
/* 330 */       GDReader.readEncFileToBuffer(this.file);
/*     */       
/* 332 */       this.key = GDReader.readKey();
/*     */       
/* 334 */       val = GDReader.readEncInt(true);
/* 335 */       if (val != 1) throw new GDParseException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"), 0L);
/*     */ 
/*     */       
/* 338 */       GDReader.Block block = new GDReader.Block();
/*     */       
/* 340 */       val = GDReader.readBlockStart(block);
/* 341 */       if (val != 19) throw new GDParseException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"), 0L);
/*     */       
/* 343 */       this.version = GDReader.readEncInt(true);
/* 344 */       if (this.version != 1 && this.version != 2)
/*     */       {
/* 346 */         throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */       }
/*     */       
/* 349 */       val = GDReader.readEncInt(false);
/* 350 */       if (val != 0) throw new GDParseException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"), 0L);
/*     */       
/* 352 */       this.modName = GDReader.readEncString();
/*     */       
/* 354 */       if (this.version >= 2) {
/* 355 */         this.expansionStatus = GDReader.readEncByte();
/*     */       }
/*     */       
/* 358 */       int numTypes = GDReader.readEncInt(true);
/*     */       
/* 360 */       this.types.clear(); int i;
/* 361 */       for (i = 0; i < numTypes; i++) {
/* 362 */         GDTransmuteType tt = new GDTransmuteType();
/* 363 */         tt.read(log);
/*     */         
/* 365 */         this.types.add(tt);
/*     */       } 
/*     */       
/* 368 */       GDReader.readBlockEnd(block);
/*     */     }
/* 370 */     catch (FileNotFoundException ex) {
/* 371 */       if (path != null) {
/* 372 */         Object[] args = { path };
/* 373 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*     */         
/* 375 */         if (log == null) {
/* 376 */           GDMsgLogger.addError(msg);
/*     */         } else {
/* 378 */           log.addError(msg);
/*     */         } 
/*     */       } 
/*     */       
/* 382 */       if (log == null) {
/* 383 */         GDMsgLogger.addError(ex);
/*     */       } else {
/* 385 */         log.addError(ex);
/*     */       } 
/*     */       
/* 388 */       this.fileError = true;
/*     */     }
/* 390 */     catch (FileVersionException ex) {
/* 391 */       if (path != null) {
/* 392 */         Object[] args = { path };
/* 393 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*     */         
/* 395 */         if (log == null) {
/* 396 */           GDMsgLogger.addError(msg);
/*     */         } else {
/* 398 */           log.addError(msg);
/*     */         } 
/*     */       } 
/*     */       
/* 402 */       if (log == null) {
/* 403 */         GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "INFO_UPDATE_GDSTASH"));
/*     */       } else {
/* 405 */         log.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "INFO_UPDATE_GDSTASH"));
/*     */       } 
/*     */       
/* 408 */       if (log == null) {
/* 409 */         GDMsgLogger.addError((Throwable)ex);
/*     */       } else {
/* 411 */         log.addError((Throwable)ex);
/*     */       } 
/*     */       
/* 414 */       this.fileError = true;
/*     */     }
/* 416 */     catch (Exception ex) {
/* 417 */       if (path != null) {
/* 418 */         Object[] args = { path };
/* 419 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*     */         
/* 421 */         if (log == null) {
/* 422 */           GDMsgLogger.addError(msg);
/*     */         } else {
/* 424 */           log.addError(msg);
/*     */         } 
/*     */       } 
/*     */       
/* 428 */       if (log == null) {
/* 429 */         GDMsgLogger.addError(ex);
/*     */       } else {
/* 431 */         log.addError(ex);
/*     */       } 
/*     */       
/* 434 */       this.fileError = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getByteSize() {
/* 440 */     int size = 0;
/*     */     
/* 442 */     size += 4;
/* 443 */     size += 4;
/* 444 */     size += 4;
/* 445 */     size += 4;
/*     */     
/* 447 */     size += 4;
/* 448 */     size += 4;
/* 449 */     size += 4;
/* 450 */     if (this.modName != null) size += this.modName.length();
/*     */     
/* 452 */     if (this.version >= 2) {
/* 453 */       size++;
/*     */     }
/*     */     
/* 456 */     size += 4;
/*     */     
/* 458 */     for (GDTransmuteType tt : this.types) {
/* 459 */       size += tt.getByteSize();
/*     */     }
/*     */     
/* 462 */     size += 4;
/*     */     
/* 464 */     return size;
/*     */   }
/*     */   
/*     */   private void createBuffer() throws IOException {
/* 468 */     GDWriter.reserveBuffer(this);
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
/* 485 */     GDWriter.writeInt(1431655765);
/* 486 */     GDWriter.writeInt(1);
/*     */     
/* 488 */     GDReader.Block block = new GDReader.Block();
/* 489 */     GDWriter.writeBlockStart(block, 19);
/*     */     
/* 491 */     GDWriter.writeInt(this.version);
/* 492 */     GDWriter.writeInt(0);
/*     */     
/* 494 */     GDWriter.writeString(this.modName);
/*     */     
/* 496 */     if (this.version >= 2) {
/* 497 */       GDWriter.writeByte(this.expansionStatus);
/*     */     }
/*     */     
/* 500 */     GDWriter.writeInt(this.types.size());
/*     */     
/* 502 */     for (GDTransmuteType tt : this.types) {
/* 503 */       tt.write();
/*     */     }
/*     */     
/* 506 */     GDWriter.writeBlockEnd(block);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write() throws IOException {
/* 512 */     createBuffer();
/*     */     
/* 514 */     String path = this.file.getParent() + GDConstants.FILE_SEPARATOR;
/*     */     
/* 516 */     String tname = "transmutes.t";
/* 517 */     if (this.filename.endsWith(".BST")) tname = "transmutes.bt"; 
/* 518 */     if (this.filename.endsWith(".BSH")) tname = "transmutes.bh"; 
/* 519 */     if (this.filename.endsWith(".CST")) tname = "transmutes.ct"; 
/* 520 */     if (this.filename.endsWith(".CSH")) tname = "transmutes.ch"; 
/* 521 */     if (this.filename.endsWith(".GSH")) tname = "transmutes.h";
/*     */     
/* 523 */     File temp = new File(path + "temp_tmp.tmp");
/*     */     
/* 525 */     temp.createNewFile();
/*     */     
/* 527 */     GDWriter.writeBuffer(temp);
/*     */     
/* 529 */     File fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(0) }));
/* 530 */     File fNew = null; int i;
/* 531 */     for (i = 9; i >= 0; i--) {
/* 532 */       fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(i) }));
/* 533 */       fNew = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(i + 1) }));
/*     */       
/* 535 */       if (fCurr.exists()) {
/* 536 */         if (i == 9) {
/* 537 */           fCurr.delete();
/*     */         } else {
/* 539 */           fCurr.renameTo(fNew);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 544 */     this.file.renameTo(fCurr);
/* 545 */     temp.renameTo(this.file);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write2() throws IOException {
/* 551 */     String path = this.file.getParent() + GDConstants.FILE_SEPARATOR;
/*     */     
/* 553 */     String tname = "transmutes.t";
/* 554 */     if (this.filename.endsWith(".BST")) tname = "transmutes.bt"; 
/* 555 */     if (this.filename.endsWith(".BSH")) tname = "transmutes.bh"; 
/* 556 */     if (this.filename.endsWith(".CST")) tname = "transmutes.ct"; 
/* 557 */     if (this.filename.endsWith(".CSH")) tname = "transmutes.ch"; 
/* 558 */     if (this.filename.endsWith(".GSH")) tname = "transmutes.h";
/*     */ 
/*     */     
/* 561 */     File fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(0) }));
/* 562 */     File fNew = null; int i;
/* 563 */     for (i = 9; i >= 0; i--) {
/* 564 */       fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(i) }));
/* 565 */       fNew = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(i + 1) }));
/*     */       
/* 567 */       if (fCurr.exists()) {
/* 568 */         if (i == 9) {
/* 569 */           fCurr.delete();
/*     */         } else {
/* 571 */           fCurr.renameTo(fNew);
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 577 */     GDWriter.copyFile(this.file, fCurr);
/*     */ 
/*     */     
/*     */     try {
/* 581 */       createBuffer();
/*     */       
/* 583 */       GDWriter.writeBuffer(this.file);
/*     */     }
/* 585 */     catch (IOException ex) {
/*     */       
/* 587 */       GDWriter.copyFile(fCurr, this.file);
/*     */       
/* 589 */       throw ex;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\item\GDTransmute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */