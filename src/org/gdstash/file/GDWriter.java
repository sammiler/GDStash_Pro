/*     */ package org.gdstash.file;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.charset.Charset;
/*     */ import org.gdstash.util.GDConstants;
/*     */ import org.gdstash.util.GDMsgFormatter;
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
/*     */ public class GDWriter
/*     */ {
/*     */   private static int key;
/*     */   private static int[] table;
/*     */   private static byte[] buffer;
/*     */   private static int pos;
/*     */   
/*     */   public static void reserveBuffer(GDFileSize gfs) {
/*  37 */     buffer = new byte[gfs.getByteSize()];
/*  38 */     pos = 0;
/*     */   }
/*     */   
/*     */   public static void writeBuffer(File file) throws IOException {
/*  42 */     try (FileOutputStream writer = new FileOutputStream(file)) {
/*  43 */       writer.write(buffer, 0, pos);
/*  44 */       writer.flush();
/*     */     }
/*  46 */     catch (IOException ex) {
/*  47 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeByteArr(byte[] b) throws IOException {
/*  52 */     if (pos + b.length > buffer.length) {
/*  53 */       throw new EOFException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNEXPECTED_EOF"));
/*     */     }
/*     */     
/*  56 */     for (int i = 0; i < b.length; i++) {
/*  57 */       buffer[pos + i] = b[i];
/*     */     }
/*     */     
/*  60 */     pos += b.length;
/*     */   }
/*     */   
/*     */   public static void writeBlockStart(GDReader.Block block, int n) throws IOException {
/*  64 */     writeInt(n);
/*  65 */     writeInt(0);
/*     */     
/*  67 */     block.end = pos;
/*     */   }
/*     */   
/*     */   public static void writeBlockEnd(GDReader.Block block) throws IOException {
/*  71 */     int currPos = pos;
/*     */     
/*  73 */     pos = block.end - 4;
/*  74 */     writeInt(currPos - block.end);
/*     */     
/*  76 */     pos = currPos;
/*     */     
/*  78 */     writeInt(0);
/*     */   }
/*     */   
/*     */   public static void writeByte(byte b) throws IOException {
/*  82 */     byte[] bArr = new byte[1];
/*  83 */     bArr[0] = b;
/*     */     
/*  85 */     writeByteArr(bArr);
/*     */   }
/*     */   
/*     */   public static void writeFloat(float value) throws IOException {
/*  89 */     int i = Float.floatToIntBits(value);
/*     */     
/*  91 */     writeInt(i);
/*     */   }
/*     */   
/*     */   public static void writeInt(int value) throws IOException {
/*  95 */     byte[] b = intToBytes4(value);
/*     */     
/*  97 */     writeByteArr(b);
/*     */   }
/*     */   
/*     */   public static void writeString(String s) throws IOException {
/* 101 */     if (s == null) {
/* 102 */       writeInt(0);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 107 */     int len = s.length();
/* 108 */     writeInt(len);
/*     */     
/* 110 */     byte[] b = s.getBytes(GDConstants.CHARSET_STASH);
/* 111 */     writeByteArr(b);
/*     */   }
/*     */   
/*     */   public static void writeStringUByte(FileOutputStream writer, String s) throws IOException {
/* 115 */     writeStringUByte(writer, s, GDConstants.CHARSET_STASH);
/*     */   }
/*     */   
/*     */   public static void writeStringUByte(FileOutputStream writer, String s, Charset cs) throws IOException {
/* 119 */     if (s == null) {
/* 120 */       writer.write(0);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 125 */     byte[] b = s.getBytes(cs);
/*     */     
/* 127 */     writer.write(b.length);
/*     */     
/* 129 */     writer.write(b);
/*     */   }
/*     */   
/*     */   public static void writeWideString(String s) throws IOException {
/* 133 */     if (s == null) {
/* 134 */       writeInt(0);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 139 */     int len = s.length();
/* 140 */     writeInt(len);
/*     */     
/* 142 */     byte[] b = s.getBytes(GDConstants.CHARSET_WIDE);
/* 143 */     writeByteArr(b);
/*     */   }
/*     */   
/*     */   public static void writeKey(FileOutputStream writer, int key) throws IOException {
/* 147 */     GDWriter.key = key;
/* 148 */     table = GDReader.buildTable(GDWriter.key);
/*     */     
/* 150 */     int k = key ^ 0x55555555;
/*     */     
/* 152 */     byte[] b = intToBytes4(k);
/*     */     
/* 154 */     writer.write(b);
/*     */   }
/*     */   
/*     */   public static void writeCurrentKey(FileOutputStream writer) throws IOException {
/* 158 */     writeEncInt(writer, key, false);
/*     */   }
/*     */   
/*     */   private static void updateKey(int value) {
/* 162 */     byte[] b = ByteBuffer.allocate(4).putInt(value).array();
/*     */     
/* 164 */     for (int i = 0; i < b.length; i++) {
/* 165 */       int j = b[i];
/* 166 */       if (j < 0) j += 256;
/*     */       
/* 168 */       key ^= table[j];
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void updateKey(byte value) {
/* 173 */     byte[] b = new byte[1];
/* 174 */     b[0] = value;
/*     */     
/* 176 */     for (int i = 0; i < b.length; i++) {
/* 177 */       int j = b[i];
/* 178 */       if (j < 0) j += 256;
/*     */       
/* 180 */       key ^= table[j];
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeEncByte(FileOutputStream writer, byte value) throws IOException {
/* 185 */     byte[] b = new byte[1];
/*     */     
/* 187 */     b[0] = (byte)(value ^ key);
/*     */     
/* 189 */     updateKey(value);
/*     */     
/* 191 */     writer.write(b);
/*     */   }
/*     */   
/*     */   public static void writeEncFloat(FileOutputStream writer, float value) throws IOException {
/* 195 */     int i = Float.floatToIntBits(value);
/*     */     
/* 197 */     writeEncInt(writer, i, true);
/*     */   }
/*     */   
/*     */   public static void writeEncInt(FileOutputStream writer, int value, boolean updateKey) throws IOException {
/* 201 */     int enc = value ^ key;
/*     */     
/* 203 */     if (updateKey) updateKey(value);
/*     */     
/* 205 */     byte[] b = intToBytes4(enc);
/*     */     
/* 207 */     writer.write(b);
/*     */   }
/*     */   
/*     */   public static void writeEncString(FileOutputStream writer, String s) throws IOException {
/* 211 */     if (s == null) {
/* 212 */       writeEncInt(writer, 0, true);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 217 */     writeEncInt(writer, s.length(), true);
/*     */     
/* 219 */     char[] c = s.toCharArray();
/* 220 */     for (int i = 0; i < c.length; i++) {
/* 221 */       byte b = (byte)c[i];
/*     */       
/* 223 */       writeEncByte(writer, b);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeByte(OutputStream os, byte value) throws IOException {
/* 228 */     os.write(value);
/*     */   }
/*     */   
/*     */   public static void writeBytes(OutputStream os, byte[] value) throws IOException {
/* 232 */     os.write(value);
/*     */   }
/*     */   
/*     */   public static void writeInt(OutputStream os, int value) throws IOException {
/* 236 */     byte[] b = intToBytes4(value);
/*     */     
/* 238 */     os.write(b);
/*     */   }
/*     */   
/*     */   public static void writeString(OutputStream os, String s) throws IOException {
/* 242 */     if (s == null) {
/* 243 */       writeInt(os, 0);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 248 */     writeInt(os, s.length());
/*     */     
/* 250 */     char[] c = s.toCharArray();
/* 251 */     for (int i = 0; i < c.length; i++) {
/* 252 */       byte b = (byte)c[i];
/*     */       
/* 254 */       writeByte(os, b);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getOSFilePath(String filename) {
/* 263 */     String osName = "";
/* 264 */     int pos = 0;
/*     */     
/* 266 */     while (pos != -1) {
/* 267 */       pos = filename.indexOf("/");
/*     */       
/* 269 */       if (pos != -1) {
/* 270 */         osName = osName + filename.substring(0, pos) + System.getProperty("file.separator");
/*     */         
/* 272 */         filename = filename.substring(pos + 1); continue;
/*     */       } 
/* 274 */       osName = osName + filename;
/*     */     } 
/*     */ 
/*     */     
/* 278 */     return osName;
/*     */   }
/*     */   
/*     */   public static String getOSDirPath(String filename) {
/* 282 */     String osPath = null;
/*     */     
/* 284 */     int pos = filename.lastIndexOf(System.getProperty("file.separator"));
/* 285 */     if (pos != -1) {
/* 286 */       osPath = filename.substring(0, pos + 1);
/*     */     }
/*     */     
/* 289 */     return osPath;
/*     */   }
/*     */   
/*     */   public static int lengthToInt(String s) {
/* 293 */     int i = 0;
/*     */     
/* 295 */     if (s == null) return i;
/*     */     
/* 297 */     i = (s.getBytes()).length;
/*     */     
/* 299 */     return i;
/*     */   }
/*     */   
/*     */   public static byte[] lengthToBytes4(String s) {
/* 303 */     if (s == null) return new byte[4];
/*     */     
/* 305 */     return intToBytes4(s.length());
/*     */   }
/*     */   
/*     */   public static byte[] intToBytes4(int value) {
/* 309 */     byte[] b = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array();
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
/* 320 */     return b;
/*     */   }
/*     */   
/*     */   public static byte[] floatToBytes4(float value) {
/* 324 */     int i = Float.floatToIntBits(value);
/*     */     
/* 326 */     return intToBytes4(i);
/*     */   }
/*     */   
/*     */   public static void write(String filename, byte[] data) throws IOException {
/* 330 */     String dirName = getOSDirPath(filename);
/*     */     
/* 332 */     File dir = new File(dirName);
/* 333 */     if (!dir.exists() && 
/* 334 */       !dir.mkdirs()) {
/* 335 */       Object[] args = { dirName };
/* 336 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DIR_CREATE_FAIL", args);
/*     */       
/* 338 */       throw new IOException(msg);
/*     */     } 
/*     */ 
/*     */     
/* 342 */     File file = new File(filename);
/*     */     
/* 344 */     file.createNewFile();
/*     */     
/* 346 */     try (FileOutputStream writer = new FileOutputStream(file)) {
/* 347 */       writer.write(data);
/* 348 */       writer.flush();
/*     */     }
/* 350 */     catch (IOException ex) {
/* 351 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void write(String filename, String data) throws IOException {
/* 356 */     String dirName = getOSDirPath(filename);
/*     */     
/* 358 */     File dir = new File(dirName);
/* 359 */     if (!dir.exists() && 
/* 360 */       !dir.mkdirs()) {
/* 361 */       Object[] args = { dirName };
/* 362 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_DIR_CREATE_FAIL", args);
/*     */       
/* 364 */       throw new IOException(msg);
/*     */     } 
/*     */ 
/*     */     
/* 368 */     File file = new File(filename);
/*     */     
/* 370 */     file.createNewFile();
/*     */     
/* 372 */     try (FileWriter writer = new FileWriter(file)) {
/* 373 */       writer.write(data);
/* 374 */       writer.flush();
/*     */     }
/* 376 */     catch (IOException ex) {
/* 377 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void copyFile(File source, File target) throws IOException {
/* 382 */     try(FileInputStream is = new FileInputStream(source); 
/* 383 */         FileOutputStream os = new FileOutputStream(target)) {
/* 384 */       byte[] buffer = new byte[1024];
/*     */       
/* 386 */       int length = is.read(buffer);
/* 387 */       while (length > 0) {
/* 388 */         os.write(buffer, 0, length);
/*     */         
/* 390 */         length = is.read(buffer);
/*     */       } 
/*     */       
/* 393 */       os.flush();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\file\GDWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */