/*     */ package org.gdstash.file;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.MappedByteBuffer;
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
/*     */ 
/*     */ public class GDReader
/*     */ {
/*     */   public static final int XOR_BITMAP = 1431655765;
/*     */   public static final int TABLE_MULT = 39916801;
/*     */   public static final long MAX_UNSIGNED_INT = 4294967296L;
/*     */   private static int key;
/*     */   
/*     */   public static class Block
/*     */   {
/*     */     public int length;
/*     */     public int end;
/*     */   }
/*  41 */   private static int[] table = new int[256];
/*     */   
/*     */   private static byte[] buffer;
/*     */   
/*     */   private static int pos;
/*     */ 
/*     */   
/*     */   public static int[] buildTable(int key) {
/*  49 */     int[] table = new int[256];
/*     */     
/*  51 */     int k = key;
/*     */     
/*  53 */     for (int i = 0; i < table.length; i++) {
/*     */       
/*  55 */       k = Integer.rotateRight(k, 1);
/*  56 */       k *= 39916801;
/*  57 */       table[i] = k;
/*     */     } 
/*     */     
/*  60 */     return table;
/*     */   }
/*     */   
/*     */   private static void updateKey(byte[] b) {
/*  64 */     for (int i = 0; i < b.length; i++) {
/*  65 */       int j = b[i];
/*  66 */       if (j < 0) j += 256;
/*     */       
/*  68 */       key ^= table[j];
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void updateKey(int value) {
/*  73 */     byte[] b = ByteBuffer.allocate(4).putInt(value).array();
/*     */     
/*  75 */     updateKey(b);
/*     */   }
/*     */   
/*     */   private static void updateKey(byte value) {
/*  79 */     byte[] b = new byte[1];
/*  80 */     b[0] = value;
/*     */     
/*  82 */     updateKey(b);
/*     */   }
/*     */   
/*     */   public static int readKey() throws IOException {
/*  86 */     int k = readEncInt(false);
/*     */     
/*  88 */     k ^= 0x55555555;
/*     */     
/*  90 */     key = k;
/*  91 */     table = buildTable(key);
/*     */     
/*  93 */     return key;
/*     */   }
/*     */   
/*     */   private static byte[] readEncByteArr(int len) throws IOException {
/*  97 */     if (pos + len > buffer.length) {
/*  98 */       throw new EOFException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNEXPECTED_EOF"));
/*     */     }
/*     */     
/* 101 */     byte[] b = new byte[len];
/*     */     
/* 103 */     for (int i = 0; i < b.length; i++) {
/* 104 */       b[i] = buffer[pos + i];
/*     */     }
/*     */     
/* 107 */     pos += b.length;
/*     */     
/* 109 */     return b;
/*     */   }
/*     */   
/*     */   public static int readBlockStart(Block block) throws IOException {
/* 113 */     int i = readEncInt(true);
/*     */     
/* 115 */     block.length = readEncInt(false);
/* 116 */     block.end = pos + block.length;
/*     */     
/* 118 */     return i;
/*     */   }
/*     */   
/*     */   public static void readBlockEnd(Block block) throws IOException {
/* 122 */     if (pos != block.end) throw new IOException();
/*     */     
/* 124 */     int i = readEncInt(false);
/*     */     
/* 126 */     if (i != 0) throw new IOException(); 
/*     */   }
/*     */   
/*     */   public static byte readEncByte() throws IOException {
/* 130 */     byte[] bArr = readEncByteArr(1);
/*     */     
/* 132 */     byte b = bArr[0];
/*     */     
/* 134 */     bArr[0] = (byte)(bArr[0] ^ key);
/*     */     
/* 136 */     updateKey(b);
/*     */     
/* 138 */     return bArr[0];
/*     */   }
/*     */   
/*     */   public static float readEncFloat(boolean updateKey) throws IOException {
/* 142 */     int i = readEncInt(updateKey);
/*     */     
/* 144 */     float f = Float.intBitsToFloat(i);
/*     */     
/* 146 */     return f;
/*     */   }
/*     */   
/*     */   public static int readEncInt(boolean updateKey) throws IOException {
/* 150 */     byte[] b = readEncByteArr(4);
/*     */     
/* 152 */     int i = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
/*     */     
/* 154 */     int result = i ^ key;
/*     */     
/* 156 */     if (updateKey) updateKey(i);
/*     */     
/* 158 */     return result;
/*     */   }
/*     */   
/*     */   public static String readEncString() throws IOException {
/* 162 */     int len = readEncInt(true);
/*     */     
/* 164 */     if (len == 0) return null;
/*     */     
/* 166 */     StringBuilder sb = new StringBuilder(len);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     byte[] b = readEncByteArr(len);
/* 175 */     for (int i = 0; i < b.length; i++) {
/* 176 */       byte temp = b[i];
/* 177 */       b[i] = (byte)(b[i] ^ key);
/*     */       
/* 179 */       updateKey(temp);
/*     */       
/* 181 */       sb.append((char)b[i]);
/*     */     } 
/*     */     
/* 184 */     String s = sb.toString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 192 */     return s;
/*     */   }
/*     */   
/*     */   public static String readEncWideString() throws IOException {
/* 196 */     int len = readEncInt(true);
/*     */     
/* 198 */     if (len == 0) return null;
/*     */     
/* 200 */     StringBuilder sb = new StringBuilder(len);
/*     */     
/* 202 */     len = 2 * len;
/* 203 */     for (int i = 0; i < len; i += 2) {
/* 204 */       byte b1 = readEncByte();
/* 205 */       byte b2 = readEncByte();
/*     */       
/* 207 */       short s1 = (short)b2;
/* 208 */       s1 = (short)(s1 << 8);
/*     */       
/* 210 */       char c = (char)(b1 | s1);
/*     */       
/* 212 */       sb.append(c);
/*     */     } 
/*     */     
/* 215 */     String s = sb.toString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     return s;
/*     */   }
/*     */   
/*     */   public static void readEncFileToBuffer(File file) throws IOException {
/* 227 */     try (FileInputStream reader = new FileInputStream(file)) {
/* 228 */       buffer = new byte[(int)reader.getChannel().size()];
/* 229 */       pos = 0;
/* 230 */       key = 0;
/* 231 */       reader.read(buffer);
/* 232 */       reader.close();
/*     */     }
/* 234 */     catch (IOException ex) {
/* 235 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] readBytes(InputStream is, int len) throws IOException {
/* 244 */     byte[] b = new byte[len];
/*     */     
/* 246 */     int i = is.read(b);
/*     */     
/* 248 */     if (i < len) throw new EOFException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNEXPECTED_EOF"));
/*     */     
/* 250 */     return b;
/*     */   }
/*     */   
/*     */   public static byte[] readBytes4(InputStream is) throws IOException {
/* 254 */     return readBytes(is, 4);
/*     */   }
/*     */   
/*     */   public static byte readByte(InputStream is) throws IOException {
/* 258 */     byte[] b = new byte[1];
/*     */     
/* 260 */     int i = is.read();
/*     */     
/* 262 */     if (i == -1) throw new EOFException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNEXPECTED_EOF"));
/*     */     
/* 264 */     b[0] = (byte)i;
/*     */     
/* 266 */     return b[0];
/*     */   }
/*     */   
/*     */   public static boolean readByteBool(InputStream is) throws IOException {
/* 270 */     byte[] b = new byte[1];
/*     */     
/* 272 */     int i = is.read();
/*     */     
/* 274 */     if (i == -1) throw new EOFException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNEXPECTED_EOF"));
/*     */     
/* 276 */     return (i != 0);
/*     */   }
/*     */   
/*     */   public static int readUByte(InputStream is) throws IOException {
/* 280 */     int i = is.read();
/*     */     
/* 282 */     if (i == -1) throw new EOFException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNEXPECTED_EOF"));
/*     */     
/* 284 */     return i;
/*     */   }
/*     */   
/*     */   public static float readFloat(InputStream is) throws IOException {
/* 288 */     int i = readInt(is);
/*     */     
/* 290 */     float f = Float.intBitsToFloat(i);
/*     */     
/* 292 */     return f;
/*     */   }
/*     */   
/*     */   public static int readInt(InputStream is) throws IOException {
/* 296 */     byte[] b = readBytes(is, 4);
/*     */ 
/*     */     
/* 299 */     return ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
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
/*     */ 
/*     */   
/*     */   public static long readLong(InputStream is) throws IOException {
/* 317 */     byte[] b = readBytes(is, 8);
/*     */ 
/*     */     
/* 320 */     return ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getLong();
/*     */   }
/*     */   
/*     */   public static int readUInt(InputStream is) throws IOException {
/* 324 */     int size = 4;
/*     */     
/* 326 */     byte[] b = readBytes(is, size);
/*     */     
/* 328 */     int val = 0; int i;
/* 329 */     for (i = size; i > 0; i--) {
/* 330 */       int temp = b[i - 1];
/* 331 */       if (temp < 0) temp = 256 + temp;
/*     */       
/* 333 */       val = val * 256 + temp;
/*     */     } 
/*     */     
/* 336 */     return val;
/*     */   }
/*     */   
/*     */   public static short readShort(InputStream is) throws IOException {
/* 340 */     int size = 2;
/*     */     
/* 342 */     byte[] b = readBytes(is, size);
/*     */     
/* 344 */     short val = 0; int i;
/* 345 */     for (i = size; i > 0; i--) {
/* 346 */       int temp = b[i - 1];
/* 347 */       if (i != size && temp < 0) temp = 256 + temp;
/*     */       
/* 349 */       val = (short)(val * 256 + b[i - 1]);
/*     */     } 
/*     */     
/* 352 */     return val;
/*     */   }
/*     */   
/*     */   public static short readUShort(InputStream is) throws IOException {
/* 356 */     int size = 2;
/*     */     
/* 358 */     byte[] b = readBytes(is, size);
/*     */     
/* 360 */     short val = 0; int i;
/* 361 */     for (i = size; i > 0; i--) {
/* 362 */       int temp = b[i - 1];
/* 363 */       if (temp < 0) temp = 256 + temp;
/*     */       
/* 365 */       val = (short)(val * 256 + temp);
/*     */     } 
/*     */     
/* 368 */     return val;
/*     */   }
/*     */   
/*     */   public static String readString(InputStream is, int len) throws IOException {
/* 372 */     byte[] b = readBytes(is, len);
/*     */     
/* 374 */     String s = ""; int i;
/* 375 */     for (i = 0; i < b.length; i++) {
/* 376 */       s = s + Character.toString((char)b[i]);
/*     */     }
/*     */     
/* 379 */     return s;
/*     */   }
/*     */   
/*     */   public static String readString(InputStream is, int len, Charset cs) throws IOException {
/* 383 */     if (len == 0) return "";
/*     */     
/* 385 */     byte[] b = readBytes(is, len);
/*     */     
/* 387 */     String s = new String(b, cs);
/*     */     
/* 389 */     return s;
/*     */   }
/*     */   
/*     */   public static String readString(InputStream is) throws IOException {
/* 393 */     int len = readUInt(is);
/*     */     
/* 395 */     byte[] b = readBytes(is, len);
/*     */     
/* 397 */     String s = ""; int i;
/* 398 */     for (i = 0; i < b.length; i++) {
/* 399 */       s = s + Character.toString((char)b[i]);
/*     */     }
/*     */     
/* 402 */     return s;
/*     */   }
/*     */   
/*     */   public static String readStringUByte(InputStream is) throws IOException {
/* 406 */     return readStringUByte(is, GDConstants.CHARSET_STASH);
/*     */   }
/*     */   
/*     */   public static String readStringUByte(InputStream is, Charset cs) throws IOException {
/* 410 */     int len = readUByte(is);
/*     */     
/* 412 */     if (len == 0) return null;
/*     */     
/* 414 */     byte[] b = readBytes(is, len);
/*     */     
/* 416 */     String s = new String(b, cs);
/*     */     
/* 418 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getBool(byte[] bytes, int offset) {
/* 426 */     byte[] b = getBytes(bytes, offset, 4);
/*     */     
/* 428 */     boolean val = false;
/*     */     int i;
/* 430 */     for (i = 0; i < b.length; i++) {
/* 431 */       val = (val || b[i] != 0);
/*     */     }
/*     */     
/* 434 */     return val;
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] getBytes(byte[] bytes, int offset, int len) {
/* 439 */     if (len <= 0) return null;
/*     */     
/* 441 */     byte[] b = new byte[len];
/*     */     int i;
/* 443 */     for (i = 0; i < len; i++) {
/* 444 */       b[i] = bytes[offset + i];
/*     */     }
/*     */     
/* 447 */     return b;
/*     */   }
/*     */   
/*     */   public static byte[] getBytes4(byte[] bytes, int offset) {
/* 451 */     return getBytes(bytes, offset, 4);
/*     */   }
/*     */   
/*     */   public static byte[] getBytes8(byte[] bytes, int offset) {
/* 455 */     return getBytes(bytes, offset, 8);
/*     */   }
/*     */   
/*     */   public static float getFloat(byte[] bytes, int offset) {
/* 459 */     byte[] b = getBytes(bytes, offset, 4);
/*     */     
/* 461 */     float val = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getFloat();
/*     */     
/* 463 */     return val;
/*     */   }
/*     */   
/*     */   public static int getInt(byte[] bytes, int offset) {
/* 467 */     byte[] b = getBytes(bytes, offset, 4);
/*     */     
/* 469 */     return ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
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
/*     */   public static long getLong(byte[] bytes, int offset) {
/* 485 */     byte[] b = getBytes(bytes, offset, 8);
/*     */     
/* 487 */     return ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getLong();
/*     */   }
/*     */   
/*     */   public static int getUInt(byte[] bytes, int offset) {
/* 491 */     int size = 4;
/*     */     
/* 493 */     int val = 0; int i;
/* 494 */     for (i = size; i > 0; i--) {
/* 495 */       int temp = bytes[offset + i - 1];
/* 496 */       if (temp < 0) temp = 256 + temp;
/*     */       
/* 498 */       val = val * 256 + temp;
/*     */     } 
/*     */     
/* 501 */     return val;
/*     */   }
/*     */   
/*     */   public static short getShort(byte[] bytes, int offset) {
/* 505 */     int size = 2;
/*     */     
/* 507 */     short val = 0; int i;
/* 508 */     for (i = size; i > 0; i--) {
/* 509 */       int temp = bytes[offset + i - 1];
/* 510 */       if (i != size && temp < 0) temp = 256 + temp;
/*     */       
/* 512 */       val = (short)(val * 256 + temp);
/*     */     } 
/*     */     
/* 515 */     return val;
/*     */   }
/*     */   
/*     */   public static short getUShort(byte[] bytes, int offset) {
/* 519 */     int size = 2;
/*     */     
/* 521 */     short val = 0; int i;
/* 522 */     for (i = size; i > 0; i--) {
/* 523 */       int temp = bytes[offset + i - 1];
/* 524 */       if (temp < 0) temp = 256 + temp;
/*     */       
/* 526 */       val = (short)(val * 256 + temp);
/*     */     } 
/*     */     
/* 529 */     return val;
/*     */   }
/*     */   
/*     */   public static String getString(byte[] bytes, int offset, int len) {
/* 533 */     StringBuffer sb = new StringBuffer(len);
/*     */     int i;
/* 535 */     for (i = 0; i < len; i++) {
/* 536 */       sb.append((char)bytes[offset + i]);
/*     */     }
/*     */     
/* 539 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static String getString0(byte[] bytes, int offset) {
/* 543 */     if (offset >= bytes.length) return "";
/*     */     
/* 545 */     StringBuilder sb = new StringBuilder(128);
/*     */     
/* 547 */     int i = offset;
/* 548 */     while (bytes[i] != 0) {
/* 549 */       sb.append((char)bytes[i]);
/*     */       
/* 551 */       i++;
/*     */     } 
/*     */     
/* 554 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getBytes(MappedByteBuffer buffer, int len) {
/* 562 */     byte[] bytes = new byte[len];
/*     */     
/* 564 */     buffer.get(bytes);
/*     */     
/* 566 */     return bytes;
/*     */   }
/*     */   
/*     */   public static byte[] getBytes4(MappedByteBuffer buffer) {
/* 570 */     return getBytes(buffer, 4);
/*     */   }
/*     */   
/*     */   public static byte[] getBytes8(MappedByteBuffer buffer) {
/* 574 */     return getBytes(buffer, 8);
/*     */   }
/*     */   
/*     */   public static byte[] getBytes(MappedByteBuffer buffer, int offset, int len) {
/* 578 */     byte[] bytes = new byte[len];
/*     */     
/* 580 */     buffer.position(offset);
/* 581 */     buffer.get(bytes);
/*     */     
/* 583 */     return bytes;
/*     */   }
/*     */   
/*     */   public static short getUShort(MappedByteBuffer buffer) {
/* 587 */     byte[] bytes = getBytes(buffer, 2);
/*     */     
/* 589 */     short val = 0;
/* 590 */     for (int i = bytes.length; i > 0; i--) {
/* 591 */       int temp = bytes[i - 1];
/* 592 */       if (temp < 0) temp = 256 + temp;
/*     */       
/* 594 */       val = (short)(val * 256 + temp);
/*     */     } 
/*     */     
/* 597 */     return val;
/*     */   }
/*     */   
/*     */   public static short getUShort(MappedByteBuffer buffer, int offset) {
/* 601 */     byte[] bytes = getBytes(buffer, offset, 2);
/*     */     
/* 603 */     short val = 0; int i;
/* 604 */     for (i = bytes.length; i > 0; i--) {
/* 605 */       int temp = bytes[i - 1];
/* 606 */       if (temp < 0) temp = 256 + temp;
/*     */       
/* 608 */       val = (short)(val * 256 + temp);
/*     */     } 
/*     */     
/* 611 */     return val;
/*     */   }
/*     */   
/*     */   public static String getString(MappedByteBuffer buffer, int len) {
/* 615 */     StringBuffer sb = new StringBuffer(len);
/*     */     
/* 617 */     for (int i = 0; i < len; i++) {
/* 618 */       sb.append((char)buffer.get());
/*     */     }
/*     */     
/* 621 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static String getString0(MappedByteBuffer buffer, int offset) {
/* 625 */     StringBuffer sb = new StringBuffer(128);
/*     */     
/* 627 */     buffer.position(offset);
/* 628 */     byte b = buffer.get();
/* 629 */     while (b != 0) {
/* 630 */       sb.append((char)b);
/*     */       
/* 632 */       b = buffer.get();
/*     */     } 
/*     */     
/* 635 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\file\GDReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */