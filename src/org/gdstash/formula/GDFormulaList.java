/*     */ package org.gdstash.formula;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.db.DBItem;
/*     */ import org.gdstash.file.GDParseException;
/*     */ import org.gdstash.file.GDReader;
/*     */ import org.gdstash.file.GDWriter;
/*     */ import org.gdstash.item.GDItem;
/*     */ import org.gdstash.util.FileVersionException;
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
/*     */ public class GDFormulaList
/*     */ {
/*     */   private static final String FORMULAS_VERSION = "formulasVersion";
/*     */   private static final String NUM_ENTRIES = "numEntries";
/*     */   private static final String EXPANSION_STATUS = "expansionStatus";
/*     */   private static final String ITEM_NAME = "itemName";
/*     */   private static final String FORMULA_READ = "formulaRead";
/*     */   private File file;
/*     */   private String filename;
/*     */   private boolean hardcore;
/*     */   private boolean error;
/*     */   private byte[] beginBlock;
/*     */   private int version;
/*     */   private int numEntries;
/*     */   private byte expansionStatus;
/*     */   private byte[] endBlock;
/*     */   private List<GDFormulaEntry> entries;
/*     */   
/*     */   public GDFormulaList(File file) {
/*  55 */     this.file = file;
/*     */     
/*  57 */     this.filename = file.getName().toUpperCase(GDConstants.LOCALE_US);
/*     */     
/*  59 */     this.hardcore = false;
/*  60 */     if (this.filename.endsWith(".BSH")) this.hardcore = true; 
/*  61 */     if (this.filename.endsWith(".CSH")) this.hardcore = true; 
/*  62 */     if (this.filename.endsWith(".GSH")) this.hardcore = true;
/*     */     
/*  64 */     this.entries = new LinkedList<>();
/*     */   }
/*     */   
/*     */   public boolean hasErrors() {
/*  68 */     return this.error;
/*     */   }
/*     */   
/*     */   public void addBlueprints() {
/*  72 */     List<DBItem> bp = DBItem.getBlueprints();
/*     */     
/*  74 */     for (DBItem item : bp) {
/*  75 */       boolean found = false;
/*     */       
/*  77 */       for (GDFormulaEntry entry : this.entries) {
/*  78 */         if (entry.getFormulaID().equals(item.getItemID())) {
/*  79 */           found = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/*  85 */       if (!found) {
/*  86 */         GDFormulaEntry entry = new GDFormulaEntry(item.getItemID());
/*     */         
/*  88 */         this.entries.add(entry);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<GDItem> getItems() {
/*  94 */     List<GDItem> items = new LinkedList<>();
/*     */     
/*  96 */     for (GDFormulaEntry entry : this.entries) {
/*  97 */       DBItem dbi = DBItem.get(entry.getFormulaID());
/*  98 */       GDItem item = new GDItem(dbi);
/*     */       
/* 100 */       items.add(item);
/*     */     } 
/*     */     
/* 103 */     return items;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read() {
/* 111 */     String path = null;
/*     */     
/* 113 */     this.error = false;
/*     */     
/* 115 */     this.entries.clear();
/*     */     
/* 117 */     try (InputStream reader = new BufferedInputStream(new FileInputStream(this.file))) {
/* 118 */       String s = null;
/* 119 */       int len = 0;
/*     */       
/* 121 */       s = GDReader.readString(reader);
/* 122 */       if (!"begin_block".equals(s)) {
/* 123 */         Object[] args = { "begin_block", path };
/* 124 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_NOT_FOUND", args);
/*     */         
/* 126 */         throw new GDParseException(msg, 0L);
/*     */       } 
/*     */       
/* 129 */       this.beginBlock = GDReader.readBytes4(reader);
/*     */       
/* 131 */       s = GDReader.readString(reader);
/* 132 */       if (!"formulasVersion".equals(s)) {
/* 133 */         Object[] args = { "formulasVersion", path };
/* 134 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_NOT_FOUND", args);
/*     */         
/* 136 */         throw new GDParseException(msg, 0L);
/*     */       } 
/*     */       
/* 139 */       this.version = GDReader.readUInt(reader);
/*     */       
/* 141 */       if (this.version != 2 && this.version != 3)
/*     */       {
/* 143 */         throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */       }
/*     */       
/* 146 */       s = GDReader.readString(reader);
/* 147 */       if (!"numEntries".equals(s)) {
/* 148 */         Object[] args = { "numEntries", path };
/* 149 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_NOT_FOUND", args);
/*     */         
/* 151 */         throw new GDParseException(msg, 0L);
/*     */       } 
/*     */       
/* 154 */       this.numEntries = GDReader.readUInt(reader);
/*     */       
/* 156 */       if (this.version >= 3) {
/* 157 */         s = GDReader.readString(reader);
/* 158 */         if (!"expansionStatus".equals(s)) {
/* 159 */           Object[] args = { "expansionStatus", path };
/* 160 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_NOT_FOUND", args);
/*     */           
/* 162 */           throw new GDParseException(msg, 0L);
/*     */         } 
/*     */         
/* 165 */         this.expansionStatus = GDReader.readByte(reader);
/*     */       } 
/*     */       
/* 168 */       String name = null;
/* 169 */       int fRead = 0;
/*     */       int i;
/* 171 */       for (i = 0; i < this.numEntries; i++) {
/* 172 */         s = GDReader.readString(reader);
/* 173 */         if (!"itemName".equals(s)) {
/* 174 */           Object[] args = { "itemName", path };
/* 175 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_NOT_FOUND", args);
/*     */           
/* 177 */           throw new GDParseException(msg, 0L);
/*     */         } 
/*     */         
/* 180 */         name = GDReader.readString(reader);
/*     */         
/* 182 */         s = GDReader.readString(reader);
/* 183 */         if (!"formulaRead".equals(s)) {
/* 184 */           Object[] args = { "formulaRead", path };
/* 185 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_NOT_FOUND", args);
/*     */           
/* 187 */           throw new GDParseException(msg, 0L);
/*     */         } 
/*     */         
/* 190 */         fRead = GDReader.readUInt(reader);
/*     */         
/* 192 */         GDFormulaEntry entry = new GDFormulaEntry(name, fRead);
/*     */         
/* 194 */         this.entries.add(entry);
/*     */       } 
/*     */       
/* 197 */       s = GDReader.readString(reader);
/* 198 */       if (!"end_block".equals(s)) {
/* 199 */         Object[] args = { "end_block", path };
/* 200 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_NOT_FOUND", args);
/*     */         
/* 202 */         throw new GDParseException(msg, 0L);
/*     */       } 
/*     */       
/* 205 */       this.endBlock = GDReader.readBytes4(reader);
/*     */     }
/* 207 */     catch (FileNotFoundException ex) {
/* 208 */       if (path != null) {
/* 209 */         Object[] args = { path };
/* 210 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*     */         
/* 212 */         GDMsgLogger.addError(msg);
/*     */       } 
/* 214 */       GDMsgLogger.addError(ex);
/*     */       
/* 216 */       this.error = true;
/*     */     }
/* 218 */     catch (FileVersionException ex) {
/* 219 */       if (path != null) {
/* 220 */         Object[] args = { path };
/* 221 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*     */         
/* 223 */         GDMsgLogger.addError(msg);
/*     */       } 
/*     */       
/* 226 */       GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "INFO_UPDATE_GDSTASH"));
/*     */       
/* 228 */       GDMsgLogger.addError((Throwable)ex);
/*     */       
/* 230 */       this.error = true;
/*     */     }
/* 232 */     catch (Exception ex) {
/* 233 */       if (path != null) {
/* 234 */         Object[] args = { path };
/* 235 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*     */         
/* 237 */         GDMsgLogger.addError(msg);
/*     */       } 
/* 239 */       GDMsgLogger.addError(ex);
/*     */       
/* 241 */       this.error = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/* 246 */     String path = this.file.getParent() + GDConstants.FILE_SEPARATOR;
/*     */     
/* 248 */     String tname = "formulas.t";
/* 249 */     if (this.filename.endsWith(".BST")) tname = "formulas.bt"; 
/* 250 */     if (this.filename.endsWith(".BSH")) tname = "formulas.bh"; 
/* 251 */     if (this.filename.endsWith(".CST")) tname = "formulas.ct"; 
/* 252 */     if (this.filename.endsWith(".CSH")) tname = "formulas.ch"; 
/* 253 */     if (this.filename.endsWith(".GSH")) tname = "formulas.h";
/*     */     
/* 255 */     File temp = new File(path + "temp_tmp.tmp");
/*     */     
/* 257 */     temp.createNewFile();
/*     */     
/* 259 */     String s = null;
/*     */     
/* 261 */     try (OutputStream writer = new BufferedOutputStream(new FileOutputStream(temp))) {
/* 262 */       GDWriter.writeString(writer, "begin_block");
/* 263 */       GDWriter.writeBytes(writer, this.beginBlock);
/* 264 */       GDWriter.writeString(writer, "formulasVersion");
/* 265 */       GDWriter.writeInt(writer, this.version);
/* 266 */       GDWriter.writeString(writer, "numEntries");
/* 267 */       GDWriter.writeInt(writer, this.entries.size());
/*     */       
/* 269 */       if (this.version == 3) {
/* 270 */         GDWriter.writeString(writer, "expansionStatus");
/* 271 */         GDWriter.writeByte(writer, this.expansionStatus);
/*     */       } 
/*     */       
/* 274 */       for (GDFormulaEntry entry : this.entries) {
/* 275 */         GDWriter.writeString(writer, "itemName");
/* 276 */         GDWriter.writeString(writer, entry.getFormulaID());
/* 277 */         GDWriter.writeString(writer, "formulaRead");
/* 278 */         GDWriter.writeInt(writer, entry.getFormulaRead());
/*     */       } 
/*     */       
/* 281 */       GDWriter.writeString(writer, "end_block");
/* 282 */       GDWriter.writeBytes(writer, this.endBlock);
/*     */       
/* 284 */       writer.flush();
/*     */     }
/* 286 */     catch (FileNotFoundException ex) {
/* 287 */       if (path != null) {
/* 288 */         Object[] args = { path };
/* 289 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*     */         
/* 291 */         GDMsgLogger.addError(msg);
/*     */       } 
/* 293 */       GDMsgLogger.addError(ex);
/*     */       
/* 295 */       this.error = true;
/*     */     }
/* 297 */     catch (Exception ex) {
/* 298 */       if (path != null) {
/* 299 */         Object[] args = { path };
/* 300 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*     */         
/* 302 */         GDMsgLogger.addError(msg);
/*     */       } 
/* 304 */       GDMsgLogger.addError(ex);
/*     */       
/* 306 */       this.error = true;
/*     */     } 
/*     */     
/* 309 */     if (this.error)
/*     */       return; 
/* 311 */     File fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(0) }));
/* 312 */     File fNew = null; int count;
/* 313 */     for (count = 9; count >= 0; count--) {
/* 314 */       fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(count) }));
/* 315 */       fNew = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(count + 1) }));
/*     */       
/* 317 */       if (fCurr.exists()) {
/* 318 */         if (count == 9) {
/* 319 */           fCurr.delete();
/*     */         } else {
/* 321 */           fCurr.renameTo(fNew);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 326 */     this.file.renameTo(fCurr);
/* 327 */     temp.renameTo(this.file);
/*     */   }
/*     */   
/*     */   public void write2() throws IOException {
/* 331 */     String path = this.file.getParent() + GDConstants.FILE_SEPARATOR;
/*     */     
/* 333 */     String tname = "formulas.t";
/* 334 */     if (this.filename.endsWith(".BST")) tname = "formulas.bt"; 
/* 335 */     if (this.filename.endsWith(".BSH")) tname = "formulas.bh"; 
/* 336 */     if (this.filename.endsWith(".CST")) tname = "formulas.ct"; 
/* 337 */     if (this.filename.endsWith(".CSH")) tname = "formulas.ch"; 
/* 338 */     if (this.filename.endsWith(".GSH")) tname = "formulas.h";
/*     */ 
/*     */     
/* 341 */     File fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(0) }));
/* 342 */     File fNew = null; int count;
/* 343 */     for (count = 9; count >= 0; count--) {
/* 344 */       fCurr = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(count) }));
/* 345 */       fNew = new File(path + tname + String.format("%02d", new Object[] { Integer.valueOf(count + 1) }));
/*     */       
/* 347 */       if (fCurr.exists()) {
/* 348 */         if (count == 9) {
/* 349 */           fCurr.delete();
/*     */         } else {
/* 351 */           fCurr.renameTo(fNew);
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 357 */     GDWriter.copyFile(this.file, fCurr);
/*     */ 
/*     */     
/* 360 */     try (OutputStream writer = new BufferedOutputStream(new FileOutputStream(this.file))) {
/* 361 */       GDWriter.writeString(writer, "begin_block");
/* 362 */       GDWriter.writeBytes(writer, this.beginBlock);
/* 363 */       GDWriter.writeString(writer, "formulasVersion");
/* 364 */       GDWriter.writeInt(writer, this.version);
/* 365 */       GDWriter.writeString(writer, "numEntries");
/* 366 */       GDWriter.writeInt(writer, this.entries.size());
/*     */       
/* 368 */       if (this.version == 3) {
/* 369 */         GDWriter.writeString(writer, "expansionStatus");
/* 370 */         GDWriter.writeByte(writer, this.expansionStatus);
/*     */       } 
/*     */       
/* 373 */       for (GDFormulaEntry entry : this.entries) {
/* 374 */         GDWriter.writeString(writer, "itemName");
/* 375 */         GDWriter.writeString(writer, entry.getFormulaID());
/* 376 */         GDWriter.writeString(writer, "formulaRead");
/* 377 */         GDWriter.writeInt(writer, entry.getFormulaRead());
/*     */       } 
/*     */       
/* 380 */       GDWriter.writeString(writer, "end_block");
/* 381 */       GDWriter.writeBytes(writer, this.endBlock);
/*     */       
/* 383 */       writer.flush();
/*     */     }
/* 385 */     catch (FileNotFoundException ex) {
/* 386 */       if (path != null) {
/* 387 */         Object[] args = { path };
/* 388 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*     */         
/* 390 */         GDMsgLogger.addError(msg);
/*     */       } 
/*     */       
/* 393 */       this.error = true;
/*     */       
/* 395 */       throw ex;
/*     */     }
/* 397 */     catch (IOException ex) {
/* 398 */       if (path != null) {
/* 399 */         Object[] args = { path };
/* 400 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*     */         
/* 402 */         GDMsgLogger.addError(msg);
/*     */       } 
/*     */       
/* 405 */       this.error = true;
/*     */ 
/*     */       
/* 408 */       GDWriter.copyFile(fCurr, this.file);
/*     */       
/* 410 */       throw ex;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\formula\GDFormulaList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */