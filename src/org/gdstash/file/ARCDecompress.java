/*      */ package org.gdstash.file;
/*      */ 
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.util.Enumeration;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.zip.ZipEntry;
/*      */ import java.util.zip.ZipFile;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.jpountz.lz4.LZ4Factory;
/*      */ import net.jpountz.lz4.LZ4FastDecompressor;
/*      */ import org.gdstash.db.DBBitmap;
/*      */ import org.gdstash.db.DBItem;
/*      */ import org.gdstash.db.DBSkill;
/*      */ import org.gdstash.db.DBSkillConnector;
/*      */ import org.gdstash.util.GDConstants;
/*      */ import org.gdstash.util.GDLog;
/*      */ import org.gdstash.util.GDMsgFormatter;
/*      */ import org.gdstash.util.GDMsgLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ARCDecompress
/*      */ {
/*      */   private String fileName;
/*      */   private FileModule module;
/*      */   private FileType fileType;
/*      */   private ARCHeader header;
/*      */   private ARCFilePart[] parts;
/*      */   private ARCFileString[] strings;
/*      */   private ARCFileToC[] tocs;
/*      */   
/*      */   public enum FileType
/*      */   {
/*   42 */     ARC, ZIP; }
/*   43 */   public enum FileModule { GD, AoM, FG, Mod, All; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ARCDecompress(FileModule module, String fileName) {
/*   54 */     this.module = module;
/*   55 */     this.fileName = fileName;
/*      */     
/*   57 */     String extension = fileName.substring(fileName.length() - 4).toUpperCase(GDConstants.LOCALE_US);
/*   58 */     if (extension.equals(".ZIP")) {
/*   59 */       this.fileType = FileType.ZIP;
/*      */     } else {
/*   61 */       this.fileType = FileType.ARC;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/*   67 */     if (o == null) return false; 
/*   68 */     if (!o.getClass().equals(ARCDecompress.class)) return false;
/*      */     
/*   70 */     ARCDecompress arc = (ARCDecompress)o;
/*      */     
/*   72 */     return this.fileName.equals(arc.fileName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FileModule getModule() {
/*   80 */     return this.module;
/*      */   }
/*      */   
/*      */   public String getFileName() {
/*   84 */     return this.fileName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ARCHeader getHeader(GDBuffer buffer) throws IOException, GDParseException {
/*   93 */     ARCHeader header = new ARCHeader();
/*      */     
/*   95 */     long temp = 0L;
/*      */     
/*   97 */     header.unknown = buffer.getInt();
/*   98 */     header.version = buffer.getInt();
/*   99 */     header.numFiles = buffer.getInt();
/*  100 */     header.numRecords = buffer.getInt();
/*  101 */     header.sizeRecords = buffer.getUnsignedInt();
/*  102 */     header.sizeStrings = buffer.getUnsignedInt();
/*  103 */     header.offsetRecords = buffer.getUnsignedInt();
/*      */     
/*  105 */     if (header.version != 3) {
/*  106 */       throw new GDParseException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"), 0L);
/*      */     }
/*      */     
/*  109 */     return header;
/*      */   }
/*      */   
/*      */   private ARCFilePart[] getFileParts(GDBuffer buffer) throws IOException {
/*  113 */     if (this.header.numRecords == 0) return null;
/*      */     
/*  115 */     ARCFilePart[] parts = new ARCFilePart[this.header.numRecords];
/*      */     
/*  117 */     buffer.setPosition(this.header.offsetRecords);
/*  118 */     for (int i = 0; i < parts.length; i++) {
/*  119 */       ARCFilePart part = new ARCFilePart();
/*      */       
/*  121 */       part.offset = buffer.getInt();
/*  122 */       part.lenCompressed = buffer.getInt();
/*  123 */       part.lenDecomp = buffer.getInt();
/*      */       
/*  125 */       parts[i] = part;
/*      */     } 
/*      */     
/*  128 */     return parts;
/*      */   }
/*      */   
/*      */   private ARCFileString[] getStrings(GDBuffer buffer) throws IOException {
/*  132 */     ARCFileString[] strings = new ARCFileString[this.header.numFiles];
/*      */     
/*  134 */     buffer.setPosition(this.header.offsetRecords + this.header.sizeRecords);
/*  135 */     for (int i = 0; i < strings.length; i++) {
/*  136 */       String s = buffer.getStringNT();
/*      */       
/*  138 */       strings[i] = new ARCFileString(s);
/*      */     } 
/*      */     
/*  141 */     return strings;
/*      */   }
/*      */   
/*      */   private ARCFileToC[] getFileToCs(GDBuffer buffer) throws IOException {
/*  145 */     ARCFileToC[] tocs = new ARCFileToC[this.header.numFiles];
/*      */     
/*  147 */     buffer.setPosition(this.header.offsetRecords + this.header.sizeRecords + this.header.sizeStrings);
/*  148 */     for (int i = 0; i < tocs.length; i++) {
/*  149 */       ARCFileToC toc = new ARCFileToC();
/*      */       
/*  151 */       toc.type = buffer.getInt();
/*  152 */       toc.offset = buffer.getInt();
/*  153 */       toc.lenCompressed = buffer.getInt();
/*  154 */       toc.lenDecomp = buffer.getInt();
/*  155 */       toc.hash = buffer.getInt();
/*  156 */       toc.fileTime = buffer.getLong();
/*  157 */       toc.numParts = buffer.getInt();
/*  158 */       toc.index = buffer.getInt();
/*  159 */       toc.lenString = buffer.getInt();
/*  160 */       toc.offsetString = buffer.getInt();
/*      */       
/*  162 */       tocs[i] = toc;
/*      */     } 
/*      */     
/*  165 */     return tocs;
/*      */   }
/*      */   
/*      */   private void close() {
/*  169 */     this.header = null;
/*  170 */     this.parts = null;
/*  171 */     this.strings = null;
/*  172 */     this.tocs = null;
/*      */   }
/*      */   
/*      */   private void readFileInfo(GDBuffer buffer, GDLog log) {
/*  176 */     if (this.fileType == FileType.ZIP) {
/*  177 */       decompressZIP();
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  182 */     GDLog tLog = new GDLog();
/*      */     
/*      */     try {
/*  185 */       this.header = getHeader(buffer);
/*  186 */       this.parts = getFileParts(buffer);
/*  187 */       this.strings = getStrings(buffer);
/*  188 */       this.tocs = getFileToCs(buffer);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  193 */       determineToCIndexes();
/*      */ 
/*      */     
/*      */     }
/*  197 */     catch (Exception|Error ex) {
/*  198 */       tLog.addError(ex);
/*      */       
/*  200 */       if (ex.getClass().equals(OutOfMemoryError.class)) {
/*  201 */         String msg = null;
/*      */         
/*  203 */         msg = GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "INFO_USE_64BIT_JAVA");
/*  204 */         tLog.addInfo(msg);
/*      */         
/*  206 */         msg = GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "INFO_USE_BATCH_FILE");
/*  207 */         tLog.addInfo(msg);
/*      */       } 
/*      */     } 
/*      */     
/*  211 */     if (tLog.containsErrors()) {
/*  212 */       Object[] args = { this.fileName };
/*  213 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_EXTRACT_FAIL", args);
/*      */       
/*  215 */       tLog.addError(msg);
/*      */     } 
/*      */     
/*  218 */     if (log == null) {
/*  219 */       GDMsgLogger.addLog(tLog);
/*      */     } else {
/*  221 */       log.addLog(tLog);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void listStrings() {
/*  226 */     String s = null;
/*      */     
/*  228 */     s = "-----" + this.fileName + "-----";
/*  229 */     System.out.println(s);
/*      */     
/*  231 */     s = "-----STRINGS-----";
/*  232 */     System.out.println(s);
/*      */     
/*  234 */     s = "Index,Filename";
/*  235 */     System.out.println(s);
/*      */     
/*  237 */     int i = 0;
/*  238 */     for (ARCFileString str : this.strings) {
/*  239 */       s = Integer.toString(i) + "," + str.getFileName();
/*  240 */       System.out.println(s);
/*      */       
/*  242 */       i++;
/*      */     } 
/*      */     
/*  245 */     System.out.println();
/*      */   }
/*      */   
/*      */   private void listToCs() {
/*  249 */     String s = null;
/*      */     
/*  251 */     s = "-----TOCS-----";
/*  252 */     System.out.println(s);
/*      */     
/*  254 */     s = "Index,Parts,Length (Decomp),Part Index";
/*  255 */     System.out.println(s);
/*      */     
/*  257 */     int i = 0;
/*  258 */     for (ARCFileToC toc : this.tocs) {
/*  259 */       s = Integer.toString(i) + "," + toc.numParts + "," + toc.lenDecomp + "," + toc.index;
/*  260 */       System.out.println(s);
/*      */       
/*  262 */       i++;
/*      */     } 
/*      */     
/*  265 */     System.out.println();
/*      */   }
/*      */   
/*      */   private void listStringToCs() {
/*  269 */     String s = null;
/*      */     
/*  271 */     s = "-----STRING to TOC-----";
/*  272 */     System.out.println(s);
/*      */     
/*  274 */     s = "Index,Filename,ToC Index";
/*  275 */     System.out.println(s);
/*      */     
/*  277 */     int i = 0;
/*  278 */     for (ARCFileString str : this.strings) {
/*  279 */       s = Integer.toString(i) + "," + str.getFileName() + "," + str.getToCIndex();
/*  280 */       System.out.println(s);
/*      */       
/*  282 */       i++;
/*      */     } 
/*      */     
/*  285 */     System.out.println();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void determineToCIndexes() {
/*  309 */     boolean skipToCs = false;
/*      */     
/*  311 */     int numUnused = 0; int i;
/*  312 */     for (i = 0; i < this.tocs.length; i++) {
/*  313 */       if ((this.tocs[i]).numParts == 0) numUnused++;
/*      */     
/*      */     } 
/*  316 */     if (numUnused > 0) {
/*  317 */       for (i = 0; i < numUnused; i++) {
/*  318 */         String s = this.strings[this.strings.length - i - 1].getFileName();
/*      */         
/*  320 */         int pos1 = s.lastIndexOf("/");
/*  321 */         int pos2 = s.lastIndexOf(".");
/*      */         
/*  323 */         if (pos2 != -1)
/*      */         {
/*      */ 
/*      */           
/*  327 */           if (pos1 <= pos2) {
/*      */ 
/*      */ 
/*      */             
/*  331 */             skipToCs = true;
/*      */             break;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*  337 */     if (skipToCs) {
/*      */ 
/*      */       
/*  340 */       for (i = 0; i < this.strings.length; i++) {
/*  341 */         this.strings[i].setToCIndex(i);
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  347 */     int tocIndex = -1;
/*  348 */     int fileIndex = -1;
/*  349 */     int tocMax = this.tocs.length - 1;
/*  350 */     int fileMax = this.strings.length - 1;
/*  351 */     while (tocIndex < tocMax && 
/*  352 */       fileIndex < fileMax) {
/*      */ 
/*      */       
/*  355 */       tocIndex++;
/*      */       
/*  357 */       if ((this.tocs[tocIndex]).lenDecomp == 0 && (this.tocs[tocIndex]).numParts == 0) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  363 */       fileIndex++;
/*      */       
/*  365 */       this.strings[fileIndex].setToCIndex(tocIndex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getFileIndex(String fileName) {
/*  423 */     int index = -1;
/*      */     
/*  425 */     for (int i = 0; i < this.strings.length; ) {
/*  426 */       if (this.strings[i] == null || 
/*  427 */         !fileName.equalsIgnoreCase(this.strings[i].getFileName())) {
/*      */         i++; continue;
/*  429 */       }  index = i;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  434 */     return index;
/*      */   }
/*      */   
/*      */   private void decompressIndex(GDBuffer buffer, LZ4FastDecompressor decomp, int index) throws IOException {
/*  438 */     if (this.strings[index].getData() != null)
/*      */       return; 
/*  440 */     int tocIndex = this.strings[index].getToCIndex();
/*      */     
/*  442 */     if (tocIndex == -1)
/*      */       return; 
/*  444 */     byte[] data = null;
/*      */     
/*  446 */     if ((this.tocs[tocIndex]).type == 1 && (this.tocs[tocIndex]).lenCompressed == (this.tocs[tocIndex]).lenDecomp) {
/*      */       
/*  448 */       data = buffer.getByteArray((this.tocs[tocIndex]).offset, (this.tocs[tocIndex]).lenDecomp);
/*      */     } else {
/*  450 */       data = new byte[(this.tocs[tocIndex]).lenDecomp];
/*      */       
/*  452 */       int offDecomp = 0; int j;
/*  453 */       for (j = 0; j < (this.tocs[tocIndex]).numParts; j++) {
/*  454 */         ARCFilePart part = this.parts[(this.tocs[tocIndex]).index + j];
/*      */         
/*  456 */         if (part.lenCompressed == part.lenDecomp) {
/*      */ 
/*      */           
/*  459 */           byte[] bComp = buffer.getByteArray(part.offset, part.lenCompressed);
/*      */           int k;
/*  461 */           for (k = 0; k < bComp.length; k++) {
/*  462 */             data[k + offDecomp] = bComp[k];
/*      */           }
/*      */         } else {
/*  465 */           byte[] bComp = buffer.getByteArray(part.offset, part.lenCompressed);
/*      */           
/*  467 */           decomp.decompress(bComp, 0, data, offDecomp, part.lenDecomp);
/*      */         } 
/*      */         
/*  470 */         offDecomp += part.lenDecomp;
/*      */       } 
/*      */     } 
/*      */     
/*  474 */     this.strings[index].setData(data);
/*      */   }
/*      */   
/*      */   private File getFile() throws IOException {
/*  478 */     File file = new File(this.fileName);
/*      */     
/*  480 */     if (!file.exists()) {
/*  481 */       Object[] args = { file.getCanonicalPath() };
/*  482 */       String s = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*      */       
/*  484 */       throw new FileNotFoundException(s);
/*      */     } 
/*      */     
/*  487 */     if (!file.isFile()) {
/*  488 */       Object[] args = { file.getCanonicalPath() };
/*  489 */       String s = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*      */       
/*  491 */       throw new FileNotFoundException(s);
/*      */     } 
/*      */     
/*  494 */     if (!file.canRead()) {
/*  495 */       Object[] args = { file.getCanonicalPath() };
/*  496 */       String s = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_CANNOT_READ", args);
/*      */       
/*  498 */       throw new IOException(s);
/*      */     } 
/*      */     
/*  501 */     return file;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void decompress(GDLog log) {
/*  509 */     if (this.fileType == FileType.ARC) decompressARC(log);
/*      */     
/*  511 */     if (this.fileType == FileType.ZIP) decompressZIP(); 
/*      */   }
/*      */   
/*      */   private void decompressARC(GDLog log) {
/*  515 */     GDLog tLog = new GDLog();
/*      */     
/*      */     try {
/*  518 */       File file = getFile();
/*      */       
/*  520 */       GDBuffer buffer = ARZDecompress.getFileBuffer(file);
/*  521 */       decompressARCBuffer(buffer);
/*      */     }
/*  523 */     catch (Exception|Error ex) {
/*  524 */       tLog.addError(ex);
/*      */       
/*  526 */       if (ex.getClass().equals(OutOfMemoryError.class)) {
/*  527 */         String msg = null;
/*      */         
/*  529 */         msg = GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "INFO_USE_64BIT_JAVA");
/*  530 */         tLog.addInfo(msg);
/*      */         
/*  532 */         msg = GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "INFO_USE_BATCH_FILE");
/*  533 */         tLog.addInfo(msg);
/*      */       } 
/*      */     } 
/*      */     
/*  537 */     if (tLog.containsErrors()) {
/*  538 */       Object[] args = { this.fileName };
/*  539 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_EXTRACT_FAIL", args);
/*      */       
/*  541 */       tLog.addError(msg);
/*      */     } 
/*      */     
/*  544 */     if (log == null) {
/*  545 */       GDMsgLogger.addLog(tLog);
/*      */     } else {
/*  547 */       log.addLog(tLog);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void decompressARCBuffer(GDBuffer buffer) throws IOException {
/*  552 */     readFileInfo(buffer, null);
/*  553 */     extractFiles(buffer);
/*  554 */     if (buffer != null) buffer.close(); 
/*      */   }
/*      */   
/*      */   private String decompressZipFile(ZipFile file, ZipEntry entry) throws IOException {
/*  558 */     String text = "";
/*  559 */     StringBuffer line = new StringBuffer(128);
/*      */     
/*  561 */     InputStream is = new BufferedInputStream(file.getInputStream(entry));
/*  562 */     InputStreamReader isr = new InputStreamReader(is, GDConstants.CHARSET_PROPERTIES);
/*  563 */     BufferedReader reader = new BufferedReader(isr);
/*      */     
/*  565 */     int len = (int)entry.getSize();
/*  566 */     char[] chars = new char[len];
/*      */     
/*  568 */     reader.read(chars, 0, len);
/*      */     
/*  570 */     int i = 0;
/*  571 */     while (i < chars.length) {
/*  572 */       if (chars[i] == '\r' && chars[i + 1] == '\n') {
/*      */ 
/*      */         
/*  575 */         text = text + line.toString() + GDConstants.LINE_SEPARATOR;
/*  576 */         line = new StringBuffer(128);
/*      */         
/*  578 */         i += 2; continue;
/*      */       } 
/*  580 */       line.append(chars[i]);
/*      */       
/*  582 */       i++;
/*      */     } 
/*      */ 
/*      */     
/*  586 */     if (line.length() > 0) text = text + line.toString() + GDConstants.LINE_SEPARATOR;
/*      */     
/*  588 */     return text;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void decompressZIP() {
/*  594 */     try (ZipFile file = new ZipFile(this.fileName)) {
/*  595 */       Enumeration<? extends ZipEntry> files = file.entries();
/*      */       
/*  597 */       List<String> fileNames = new LinkedList<>();
/*  598 */       while (files.hasMoreElements()) {
/*  599 */         ZipEntry entry = files.nextElement();
/*      */         
/*  601 */         String s = entry.getName();
/*      */         
/*  603 */         if (s.contains("tags")) {
/*  604 */           fileNames.add(s);
/*      */         }
/*      */       } 
/*      */       
/*  608 */       if (fileNames.size() == 0)
/*      */         return; 
/*  610 */       this.strings = new ARCFileString[fileNames.size()];
/*      */ 
/*      */       
/*  613 */       int i = 0;
/*  614 */       for (String search : fileNames) {
/*  615 */         ZipEntry entry = null;
/*      */         
/*  617 */         entry = file.getEntry(search);
/*  618 */         if (entry != null) {
/*  619 */           this.strings[i] = new ARCFileString(search);
/*  620 */           this.strings[i].setToCIndex(i);
/*      */           
/*  622 */           String text = decompressZipFile(file, entry);
/*  623 */           this.strings[i].setText(text);
/*      */         } 
/*      */         
/*  626 */         i++;
/*      */       }
/*      */     
/*  629 */     } catch (Exception|Error ex) {
/*  630 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void extractFiles(GDBuffer buffer) {
/*  635 */     LZ4Factory factory = LZ4Factory.fastestInstance();
/*  636 */     LZ4FastDecompressor decomp = factory.fastDecompressor();
/*      */     int i;
/*  638 */     for (i = 0; i < this.strings.length; i++) {
/*      */ 
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  647 */         decompressIndex(buffer, decomp, i);
/*      */       }
/*  649 */       catch (IOException ex) {
/*  650 */         this.strings[i].setData(null);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  655 */     this.header = null;
/*  656 */     this.parts = null;
/*  657 */     this.tocs = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeFiles(String dir, String subDir) {
/*  665 */     if (dir == null)
/*      */       return; 
/*  667 */     LZ4Factory factory = LZ4Factory.fastestInstance();
/*  668 */     LZ4FastDecompressor decomp = factory.fastDecompressor();
/*      */     
/*  670 */     GDLog log = new GDLog();
/*      */     
/*      */     try {
/*  673 */       File file = getFile();
/*      */       
/*  675 */       GDBuffer buffer = ARZDecompress.getFileBuffer(file);
/*  676 */       readFileInfo(buffer, log);
/*  677 */       writeFileData(dir, subDir, buffer, decomp, log);
/*      */     }
/*  679 */     catch (Exception|Error ex) {
/*  680 */       log.addError(ex);
/*      */       
/*  682 */       if (ex.getClass().equals(OutOfMemoryError.class)) {
/*  683 */         String msg = null;
/*      */         
/*  685 */         msg = GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "INFO_USE_64BIT_JAVA");
/*  686 */         log.addInfo(msg);
/*      */         
/*  688 */         msg = GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "INFO_USE_BATCH_FILE");
/*  689 */         log.addInfo(msg);
/*      */       } 
/*      */     } 
/*      */     
/*  693 */     close();
/*      */     
/*  695 */     GDMsgLogger.addLog(log);
/*      */   }
/*      */   private void writeFileData(String dir, String subDir, GDBuffer buffer, LZ4FastDecompressor decomp, GDLog log) {
/*      */     int i;
/*  699 */     for (i = 0; i < this.strings.length; i++) {
/*  700 */       String newName = dir + GDConstants.FILE_SEPARATOR + subDir + GDConstants.FILE_SEPARATOR + this.strings[i].getFileName();
/*      */       
/*  702 */       int pos = newName.indexOf("/");
/*  703 */       while (pos != -1) {
/*  704 */         newName = newName.substring(0, pos) + GDConstants.FILE_SEPARATOR + newName.substring(pos + 1);
/*      */         
/*  706 */         pos = newName.indexOf("/");
/*      */       } 
/*      */       
/*      */       try {
/*  710 */         decompressIndex(buffer, decomp, i);
/*      */         
/*  712 */         GDWriter.write(newName, this.strings[i].getData());
/*      */         
/*  714 */         if (newName.endsWith(".tex")) {
/*  715 */           String pngName = newName.substring(0, newName.length() - 4) + ".png";
/*      */           
/*  717 */           BufferedImage image = getImage(this.strings[i].getData());
/*      */           
/*  719 */           if (image != null) {
/*  720 */             File fImg = new File(pngName);
/*  721 */             ImageIO.write(image, "PNG", fImg);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  726 */         this.strings[i].setData(null);
/*      */       }
/*  728 */       catch (IOException ex) {
/*  729 */         Object[] args = { ex.getMessage(), this.strings[i].getFileName() };
/*  730 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_WRITE_ERROR", args);
/*      */         
/*  732 */         log.addError(msg);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] getTexture(GDBuffer buffer, LZ4FastDecompressor decomp, String filename) {
/*  742 */     String arcname = null;
/*  743 */     String id = null;
/*  744 */     int pos = filename.indexOf("/");
/*  745 */     if (pos != -1) {
/*  746 */       arcname = GDConstants.FILE_SEPARATOR + filename.substring(0, pos) + ".ARC";
/*      */       
/*  748 */       arcname = arcname.toUpperCase(GDConstants.LOCALE_US);
/*      */ 
/*      */       
/*  751 */       id = filename.substring(pos + 1);
/*      */     } else {
/*  753 */       id = filename;
/*      */     } 
/*      */ 
/*      */     
/*  757 */     if (arcname != null) {
/*  758 */       String fn = getFileName().toUpperCase(GDConstants.LOCALE_US);
/*      */       
/*  760 */       pos = fn.indexOf(arcname);
/*  761 */       if (pos == -1) return null;
/*      */     
/*      */     } 
/*  764 */     int index = getFileIndex(id);
/*      */     
/*  766 */     if (index == -1) return null;
/*      */     
/*      */     try {
/*  769 */       decompressIndex(buffer, decomp, index);
/*      */     }
/*  771 */     catch (IOException ex) {
/*  772 */       this.strings[index].setData(null);
/*      */     } 
/*      */     
/*  775 */     return this.strings[index].getData();
/*      */   }
/*      */   
/*      */   private BufferedImage getImage(GDBuffer buffer, LZ4FastDecompressor decomp, String filename) {
/*  779 */     byte[] data = getTexture(buffer, decomp, filename);
/*      */     
/*  781 */     return getImage(data);
/*      */   }
/*      */   
/*      */   public static BufferedImage getImage(byte[] data) {
/*  785 */     if (data == null) return null;
/*      */     
/*  787 */     BufferedImage image = null;
/*      */     
/*      */     try {
/*  790 */       image = DDSLoader.getImage(data);
/*      */     }
/*  792 */     catch (Exception ex) {
/*  793 */       image = null;
/*      */     } 
/*      */     
/*  796 */     return image;
/*      */   }
/*      */   
/*      */   public void updateImageData(GDLog log) {
/*  800 */     GDLog tLog = new GDLog();
/*      */     
/*      */     try {
/*  803 */       File file = getFile();
/*      */       
/*  805 */       GDBuffer buffer = new GDRandomAccessBuffer(file);
/*  806 */       updateImages(buffer);
/*      */     }
/*  808 */     catch (Exception|Error ex) {
/*  809 */       tLog.addError(ex);
/*      */       
/*  811 */       if (ex.getClass().equals(OutOfMemoryError.class)) {
/*  812 */         String msg = null;
/*      */         
/*  814 */         msg = GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "INFO_USE_64BIT_JAVA");
/*  815 */         tLog.addInfo(msg);
/*      */         
/*  817 */         msg = GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "INFO_USE_BATCH_FILE");
/*  818 */         tLog.addInfo(msg);
/*      */       } 
/*      */     } 
/*      */     
/*  822 */     if (tLog.containsErrors()) {
/*  823 */       Object[] args = { this.fileName };
/*  824 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_EXTRACT_FAIL", args);
/*      */       
/*  826 */       tLog.addError(msg);
/*      */     } 
/*      */     
/*  829 */     if (log == null) {
/*  830 */       GDMsgLogger.addLog(tLog);
/*      */     } else {
/*  832 */       log.addLog(tLog);
/*      */     } 
/*      */   }
/*      */   
/*      */   private String getArchivePrefix() {
/*  837 */     String fn = getFileName().toLowerCase(GDConstants.LOCALE_US);
/*      */     
/*  839 */     int pos = 0;
/*      */     
/*  841 */     pos = fn.lastIndexOf(GDConstants.FILE_SEPARATOR);
/*  842 */     if (pos != -1) fn = fn.substring(pos + 1);
/*      */     
/*  844 */     pos = fn.lastIndexOf(".arc");
/*  845 */     if (pos != -1) {
/*  846 */       fn = fn.substring(0, pos) + "/";
/*      */     }
/*      */     
/*  849 */     return fn;
/*      */   }
/*      */   
/*      */   private void updateImages(GDBuffer buffer) throws IOException {
/*  853 */     LZ4Factory factory = LZ4Factory.fastestInstance();
/*  854 */     LZ4FastDecompressor decomp = factory.fastDecompressor();
/*      */     
/*  856 */     readFileInfo(buffer, null);
/*      */     
/*  858 */     String fn = getArchivePrefix();
/*      */ 
/*      */     
/*  861 */     List<DBBitmap.ImageInfo> bitmapInfos = DBBitmap.getImageInfos(fn);
/*      */     
/*  863 */     for (DBBitmap.ImageInfo info : bitmapInfos) {
/*  864 */       if (info.bitmapID != null && info.bitmap == null)
/*      */       {
/*  866 */         info.bitmap = getTexture(buffer, decomp, info.bitmapID);
/*      */       }
/*      */     } 
/*      */     
/*  870 */     DBBitmap.updateImageInfo(bitmapInfos);
/*      */ 
/*      */     
/*  873 */     List<DBItem.ImageInfo> itemInfos = DBItem.getImageInfos(fn);
/*      */     
/*  875 */     for (DBItem.ImageInfo info : itemInfos) {
/*  876 */       if (info.bitmapID != null && info.bitmap == null)
/*      */       {
/*  878 */         info.bitmap = getTexture(buffer, decomp, info.bitmapID);
/*      */       }
/*      */       
/*  881 */       if (info.shardBitmapID != null && info.shardBitmap == null)
/*      */       {
/*  883 */         info.shardBitmap = getTexture(buffer, decomp, info.shardBitmapID);
/*      */       }
/*      */     } 
/*      */     
/*  887 */     DBItem.updateImageInfo(itemInfos);
/*      */ 
/*      */     
/*  890 */     List<DBSkill.ImageInfo> skillInfos = DBSkill.getImageInfos(fn);
/*      */     
/*  892 */     for (DBSkill.ImageInfo info : skillInfos) {
/*  893 */       if (info.bitmapDownID != null && info.bitmapDown == null)
/*      */       {
/*  895 */         info.bitmapDown = getTexture(buffer, decomp, info.bitmapDownID);
/*      */       }
/*      */       
/*  898 */       if (info.bitmapUpID != null && info.bitmapUp == null)
/*      */       {
/*  900 */         info.bitmapUp = getTexture(buffer, decomp, info.bitmapUpID);
/*      */       }
/*      */     } 
/*      */     
/*  904 */     DBSkill.updateImageInfo(skillInfos);
/*      */     
/*  906 */     List<DBSkillConnector.ImageInfo> connectionInfos = DBSkillConnector.getImageInfos(fn);
/*      */     
/*  908 */     for (DBSkillConnector.ImageInfo info : connectionInfos) {
/*  909 */       if (info.connectionOffID != null && info.connectionOff == null)
/*      */       {
/*  911 */         info.connectionOff = getTexture(buffer, decomp, info.connectionOffID);
/*      */       }
/*      */       
/*  914 */       if (info.connectionOnID != null && info.connectionOn == null)
/*      */       {
/*  916 */         info.connectionOn = getTexture(buffer, decomp, info.connectionOnID);
/*      */       }
/*      */     } 
/*      */     
/*  920 */     DBSkillConnector.updateImageInfo(connectionInfos);
/*      */     
/*  922 */     buffer.close();
/*      */     
/*  924 */     close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String removeComments(String s) {
/*  932 */     String newS = "";
/*  933 */     String rest = s;
/*      */     
/*  935 */     int pos = 0;
/*      */     
/*  937 */     while (pos != -1) {
/*  938 */       pos = rest.indexOf("/*");
/*      */       
/*  940 */       if (pos == -1) {
/*  941 */         newS = newS + rest; continue;
/*      */       } 
/*  943 */       newS = newS + rest.substring(0, pos);
/*  944 */       rest = rest.substring(pos + 2);
/*      */       
/*  946 */       pos = rest.indexOf("*/");
/*      */       
/*  948 */       if (pos == -1) {
/*  949 */         newS = newS + rest; continue;
/*      */       } 
/*  951 */       rest = rest.substring(pos + 2);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  956 */     return newS;
/*      */   }
/*      */   
/*      */   private static String convertControlChars(String s, boolean insertColor) {
/*  960 */     boolean colortag = false;
/*  961 */     String newS = "";
/*  962 */     String rest = removeComments(s);
/*  963 */     String word = null;
/*  964 */     int len = 0;
/*      */     
/*  966 */     int posControl = rest.indexOf("^");
/*  967 */     int posNewLine = rest.indexOf("{^n}");
/*  968 */     int posBlank = rest.indexOf(" ");
/*  969 */     int pos = posBlank;
/*      */     
/*  971 */     if (pos == -1) {
/*  972 */       if (posControl == -1) {
/*  973 */         pos = posNewLine;
/*      */       } else {
/*  975 */         pos = posControl;
/*      */         
/*  977 */         if (posNewLine != -1 && 
/*  978 */           pos > posNewLine) pos = posNewLine;
/*      */       
/*      */       } 
/*      */     } else {
/*  982 */       if (posControl != -1 && 
/*  983 */         pos > posControl) pos = posControl;
/*      */       
/*  985 */       if (posNewLine != -1 && 
/*  986 */         pos > posNewLine) pos = posNewLine;
/*      */     
/*      */     } 
/*      */     
/*  990 */     while (pos != -1) {
/*  991 */       if (pos == posBlank) {
/*  992 */         word = rest.substring(0, pos);
/*  993 */         rest = rest.substring(pos + 1);
/*  994 */         len += word.length();
/*  995 */         newS = newS + word;
/*      */         
/*  997 */         if (insertColor) {
/*  998 */           if (len < 80) {
/*  999 */             newS = newS + " ";
/* 1000 */             len++;
/*      */           } else {
/* 1002 */             newS = newS + "<br>";
/* 1003 */             len = 0;
/*      */           } 
/*      */         } else {
/* 1006 */           newS = newS + " ";
/* 1007 */           len++;
/*      */         } 
/*      */       } 
/*      */       
/* 1011 */       if (pos == posControl) {
/* 1012 */         word = rest.substring(0, pos);
/* 1013 */         rest = rest.substring(pos + 1);
/* 1014 */         len += word.length();
/* 1015 */         newS = newS + word;
/*      */         
/*      */         try {
/* 1018 */           char c = Character.toUpperCase(rest.charAt(0));
/* 1019 */           rest = rest.substring(1);
/*      */           
/* 1021 */           if (insertColor) {
/* 1022 */             boolean isColor = false;
/* 1023 */             String htmltag = null;
/* 1024 */             switch (c) {
/*      */               case 'A':
/* 1026 */                 htmltag = "<font color = \"#60BC9C\">";
/* 1027 */                 isColor = true;
/*      */                 break;
/*      */               case 'B':
/* 1030 */                 htmltag = "<font color = \"#3298B5\">";
/* 1031 */                 isColor = true;
/*      */                 break;
/*      */               case 'C':
/* 1034 */                 htmltag = "<font color = \"#00C6C6\">";
/* 1035 */                 isColor = true;
/*      */                 break;
/*      */               case 'D':
/* 1038 */                 htmltag = "<font color = \"#404040\">";
/* 1039 */                 isColor = true;
/*      */                 break;
/*      */               case 'F':
/* 1042 */                 htmltag = "<font color = \"#F76782\">";
/* 1043 */                 isColor = true;
/*      */                 break;
/*      */               case 'G':
/* 1046 */                 htmltag = "<font color = \"#0DBC47\">";
/* 1047 */                 isColor = true;
/*      */                 break;
/*      */               case 'I':
/* 1050 */                 htmltag = "<font color = \"#5A039A\">";
/* 1051 */                 isColor = true;
/*      */                 break;
/*      */               case 'K':
/* 1054 */                 htmltag = "<font color = \"#A8A163\">";
/* 1055 */                 isColor = true;
/*      */                 break;
/*      */               case 'L':
/* 1058 */                 htmltag = "<font color = \"#7CAD00\">";
/* 1059 */                 isColor = true;
/*      */                 break;
/*      */               case 'M':
/* 1062 */                 htmltag = "<font color = \"#800000\">";
/* 1063 */                 isColor = true;
/*      */                 break;
/*      */               case 'N':
/* 1066 */                 htmltag = "<br>";
/* 1067 */                 isColor = false;
/* 1068 */                 len = 0;
/*      */                 break;
/*      */               case 'O':
/* 1071 */                 htmltag = "<font color = \"#D38E43\">";
/* 1072 */                 isColor = true;
/*      */                 break;
/*      */               case 'P':
/* 1075 */                 htmltag = "<font color = \"#A767C6\">";
/* 1076 */                 isColor = true;
/*      */                 break;
/*      */               case 'R':
/* 1079 */                 htmltag = "<font color = \"#FF2E05\">";
/* 1080 */                 isColor = true;
/*      */                 break;
/*      */               case 'S':
/* 1083 */                 htmltag = "<font color = \"#787878\">";
/* 1084 */                 isColor = true;
/*      */                 break;
/*      */               case 'T':
/* 1087 */                 htmltag = "<font color = \"#00C6A2\">";
/* 1088 */                 isColor = true;
/*      */                 break;
/*      */               case 'W':
/* 1091 */                 htmltag = "<font color = \"#9A9A9A\">";
/* 1092 */                 isColor = true;
/*      */                 break;
/*      */               case 'Y':
/* 1095 */                 htmltag = "<font color = \"#B78021\">";
/* 1096 */                 isColor = true;
/*      */                 break;
/*      */             } 
/*      */             
/* 1100 */             if (isColor) {
/* 1101 */               if (colortag) newS = newS + "</font>";
/*      */               
/* 1103 */               colortag = true;
/*      */             } 
/* 1105 */             if (htmltag != null) newS = newS + htmltag;
/*      */           
/*      */           } 
/* 1108 */         } catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
/*      */         
/* 1110 */         if (insertColor && 
/* 1111 */           len > 80) {
/* 1112 */           newS = newS + "<br>";
/* 1113 */           len = 0;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1118 */       if (pos == posNewLine) {
/* 1119 */         word = rest.substring(0, pos);
/* 1120 */         rest = rest.substring(pos + 4);
/* 1121 */         len = 0;
/* 1122 */         newS = newS + word + "<br>";
/*      */       } 
/*      */       
/* 1125 */       posControl = rest.indexOf("^");
/* 1126 */       posNewLine = rest.indexOf("{^n}");
/* 1127 */       posBlank = rest.indexOf(" ");
/* 1128 */       pos = posBlank;
/*      */       
/* 1130 */       if (pos == -1) {
/* 1131 */         if (posControl == -1) {
/* 1132 */           pos = posNewLine; continue;
/*      */         } 
/* 1134 */         pos = posControl;
/*      */         
/* 1136 */         if (posNewLine != -1 && 
/* 1137 */           pos > posNewLine) pos = posNewLine;
/*      */         
/*      */         continue;
/*      */       } 
/* 1141 */       if (posControl != -1 && 
/* 1142 */         pos > posControl) pos = posControl;
/*      */       
/* 1144 */       if (posNewLine != -1 && 
/* 1145 */         pos > posNewLine) pos = posNewLine;
/*      */     
/*      */     } 
/*      */ 
/*      */     
/* 1150 */     newS = newS + rest;
/*      */     
/* 1152 */     if (colortag) newS = newS + "</font>";
/*      */     
/* 1154 */     return newS;
/*      */   }
/*      */   
/*      */   private void convertText(int index) {
/* 1158 */     if (this.strings[index].getText() == null) {
/* 1159 */       if (this.strings[index].getData() == null)
/*      */         return; 
/* 1161 */       String text = "";
/* 1162 */       StringBuffer line = new StringBuffer(128);
/*      */       
/* 1164 */       int j = 0;
/* 1165 */       while (j < (this.strings[index].getData()).length) {
/* 1166 */         if (this.strings[index].getData()[j] == 13 && this.strings[index]
/* 1167 */           .getData()[j + 1] == 10) {
/*      */           
/* 1169 */           text = text + line.toString() + GDConstants.LINE_SEPARATOR;
/* 1170 */           line = new StringBuffer(128);
/*      */           
/* 1172 */           j += 2; continue;
/*      */         } 
/* 1174 */         line.append((char)this.strings[index].getData()[j]);
/*      */         
/* 1176 */         j++;
/*      */       } 
/*      */ 
/*      */       
/* 1180 */       if (line.length() > 0) {
/* 1181 */         text = text + line.toString() + GDConstants.LINE_SEPARATOR;
/*      */       }
/*      */       
/* 1184 */       this.strings[index].setText(text);
/* 1185 */       this.strings[index].setData(null);
/*      */     } 
/*      */   }
/*      */   
/*      */   private String findTag(String text, String tag) {
/* 1190 */     int pos1 = text.indexOf(tag + "=");
/* 1191 */     if (pos1 == -1) return null;
/*      */     
/* 1193 */     String result = null;
/*      */ 
/*      */     
/* 1196 */     int pos2 = text.indexOf('\n', pos1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1201 */     int pos3 = text.indexOf(false, pos1);
/* 1202 */     if (pos3 != -1 && pos3 < pos2) pos2 = pos3; 
/* 1203 */     if (pos2 == -1) pos2 = pos3; 
/* 1204 */     if (pos2 == -1) {
/* 1205 */       result = text.substring(pos1 + tag.length() + 1);
/*      */     
/*      */     }
/* 1208 */     else if (text.charAt(pos2 - 1) == '\r') {
/* 1209 */       result = text.substring(pos1 + tag.length() + 1, pos2 - 1);
/*      */     } else {
/* 1211 */       result = text.substring(pos1 + tag.length() + 1, pos2);
/*      */     } 
/*      */ 
/*      */     
/* 1215 */     return result;
/*      */   }
/*      */   
/*      */   public String getTag(List<String> filenames, String tag, boolean insertColor) {
/* 1219 */     if (filenames == null) return null; 
/* 1220 */     if (this.strings == null) return null;
/*      */     
/* 1222 */     String result = null;
/*      */     
/* 1224 */     for (String fn : filenames) {
/* 1225 */       int i; for (i = 0; i < this.strings.length; i++) {
/* 1226 */         if (this.strings[i].getFileName().contains(fn)) {
/*      */           
/* 1228 */           if (this.strings[i].getText() == null) convertText(i);
/*      */           
/* 1230 */           if (this.strings[i].getText() != null) {
/*      */             
/* 1232 */             result = findTag(this.strings[i].getText(), tag);
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1237 */       if (result != null) {
/*      */         break;
/*      */       }
/*      */     } 
/* 1241 */     if (result == null) {
/* 1242 */       int i; for (i = 0; i < this.strings.length; i++) {
/* 1243 */         if (this.strings[i] != null) {
/*      */           
/* 1245 */           if (this.strings[i].getText() == null) convertText(i);
/*      */           
/* 1247 */           if (this.strings[i].getText() != null)
/*      */           
/* 1249 */           { result = findTag(this.strings[i].getText(), tag);
/*      */             
/* 1251 */             if (result != null)
/*      */               break;  } 
/*      */         } 
/*      */       } 
/* 1255 */     }  if (result != null) result = convertControlChars(result, insertColor);
/*      */     
/* 1257 */     return result;
/*      */   }
/*      */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\file\ARCDecompress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */