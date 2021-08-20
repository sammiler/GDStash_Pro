/*     */ package org.gdstash.file;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDByteBuffer
/*     */   extends GDAbstractBuffer
/*     */ {
/*     */   private int bufferSize;
/*     */   private int lastBlockSize;
/*     */   private int fileSize;
/*     */   private List<byte[]> list;
/*     */   private int pos;
/*     */   private ByteOrder bo;
/*     */   
/*     */   public GDByteBuffer(String filename) {
/*  32 */     this(new File(filename), 104857600);
/*     */   }
/*     */   
/*     */   public GDByteBuffer(String filename, int bufferSize) {
/*  36 */     this(new File(filename), bufferSize);
/*     */   }
/*     */   
/*     */   public GDByteBuffer(File file) {
/*  40 */     this(file, 104857600);
/*     */   }
/*     */   
/*     */   public GDByteBuffer(File file, int bufferSize) {
/*  44 */     this.bufferSize = bufferSize;
/*  45 */     this.list = (List)new LinkedList<>();
/*     */     
/*  47 */     try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
/*  48 */       this.fileSize = (int)raf.length();
/*     */       
/*  50 */       int rest = this.fileSize;
/*     */       
/*  52 */       int size = 0;
/*  53 */       if (rest < bufferSize) {
/*     */         
/*  55 */         size = rest;
/*     */         
/*  57 */         this.lastBlockSize = rest;
/*     */       } else {
/*  59 */         size = bufferSize;
/*     */       } 
/*     */       
/*  62 */       while (size > 0) {
/*  63 */         byte[] bytes = new byte[size];
/*  64 */         raf.readFully(bytes);
/*     */         
/*  66 */         this.list.add(bytes);
/*     */         
/*  68 */         rest -= size;
/*  69 */         if (rest < bufferSize) {
/*     */           
/*  71 */           size = rest;
/*     */           
/*  73 */           if (rest > 0) this.lastBlockSize = rest;  continue;
/*     */         } 
/*  75 */         size = bufferSize;
/*     */       }
/*     */     
/*     */     }
/*  79 */     catch (IOException ex) {
/*  80 */       this.list = (List)new LinkedList<>();
/*     */     } 
/*     */     
/*  83 */     this.pos = 0;
/*  84 */     this.bo = ByteOrder.LITTLE_ENDIAN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getByteArray(int length) throws IOException {
/*  93 */     return getByteArray(this.pos, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getByteArray(long pos, int length) throws IOException {
/*  98 */     if (pos > 2147483647L) throw new EOFException();
/*     */ 
/*     */     
/* 101 */     int listSize = (this.list.size() - 1) * this.bufferSize + this.lastBlockSize;
/*     */     
/* 103 */     if (listSize < pos) return null;
/*     */     
/* 105 */     int byteSize = length;
/* 106 */     if (listSize < pos + length)
/*     */     {
/* 108 */       byteSize = listSize - (int)pos;
/*     */     }
/*     */     
/* 111 */     if (byteSize <= 0) return null;
/*     */     
/* 113 */     byte[] result = new byte[byteSize];
/*     */ 
/*     */     
/* 116 */     int tempPos = (int)pos;
/* 117 */     int tempLen = length;
/* 118 */     int resOffset = 0;
/*     */ 
/*     */     
/* 121 */     int listPos = 0;
/* 122 */     Iterator<byte[]> iter = this.list.iterator();
/* 123 */     while (iter.hasNext()) {
/* 124 */       byte[] bytes = (byte[])iter.next();
/*     */       
/* 126 */       if (listPos <= tempPos && listPos + bytes.length > tempPos) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 131 */         int bufferOffset = tempPos - listPos;
/* 132 */         int maxPos = bufferOffset + tempLen;
/* 133 */         if (maxPos > bytes.length) maxPos = bytes.length; 
/*     */         int i;
/* 135 */         for (i = bufferOffset; i < maxPos; i++) {
/* 136 */           result[i - bufferOffset + resOffset] = bytes[i];
/*     */         }
/*     */         
/* 139 */         listPos += this.bufferSize;
/*     */         
/* 141 */         if (listPos > tempPos + tempLen) {
/*     */           break;
/*     */         }
/*     */         
/* 145 */         tempPos = listPos;
/* 146 */         tempLen -= maxPos - bufferOffset;
/* 147 */         resOffset += maxPos - bufferOffset; continue;
/*     */       } 
/* 149 */       listPos += this.bufferSize;
/*     */     } 
/*     */ 
/*     */     
/* 153 */     this.pos = tempPos + tempLen;
/*     */     
/* 155 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByte() throws IOException {
/* 160 */     byte[] bytes = getByteArray(1);
/*     */     
/* 162 */     return bytes[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt() throws IOException {
/* 167 */     byte[] bytes = getByteArray(4);
/*     */     
/* 169 */     return ByteBuffer.wrap(bytes).order(this.bo).getInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong() throws IOException {
/* 174 */     byte[] bytes = getByteArray(8);
/*     */     
/* 176 */     return ByteBuffer.wrap(bytes).order(this.bo).getLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort() throws IOException {
/* 181 */     byte[] bytes = getByteArray(2);
/*     */     
/* 183 */     return ByteBuffer.wrap(bytes).order(this.bo).getShort();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getPosition() {
/* 188 */     return this.pos;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPosition(long pos) throws IOException {
/* 193 */     if (pos > 2147483647L) throw new EOFException();
/*     */     
/* 195 */     this.pos = (int)pos;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 200 */     this.list = null;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\file\GDByteBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */