/*    */ package org.gdstash.file;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.RandomAccessFile;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.ByteOrder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GDRandomAccessBuffer
/*    */   extends GDAbstractBuffer
/*    */ {
/*    */   private File file;
/*    */   private ByteOrder bo;
/*    */   private RandomAccessFile raf;
/*    */   
/*    */   public GDRandomAccessBuffer(File file) {
/* 23 */     this.file = file;
/* 24 */     this.bo = ByteOrder.LITTLE_ENDIAN;
/*    */     try {
/* 26 */       this.raf = new RandomAccessFile(file, "r");
/*    */     }
/* 28 */     catch (IOException ex) {
/*    */       try {
/* 30 */         this.raf.close();
/*    */       }
/* 32 */       catch (IOException iOException) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getByteArray(int length) throws IOException {
/* 42 */     byte[] b = new byte[length];
/*    */     
/* 44 */     this.raf.readFully(b);
/*    */     
/* 46 */     return b;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getByte() throws IOException {
/* 51 */     return this.raf.readByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getInt() throws IOException {
/* 56 */     byte[] bytes = getByteArray(4);
/*    */     
/* 58 */     return ByteBuffer.wrap(bytes).order(this.bo).getInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public long getLong() throws IOException {
/* 63 */     byte[] bytes = getByteArray(8);
/*    */     
/* 65 */     return ByteBuffer.wrap(bytes).order(this.bo).getLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public short getShort() throws IOException {
/* 70 */     byte[] bytes = getByteArray(2);
/*    */     
/* 72 */     return ByteBuffer.wrap(bytes).order(this.bo).getShort();
/*    */   }
/*    */ 
/*    */   
/*    */   public long getPosition() throws IOException {
/* 77 */     return this.raf.getFilePointer();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPosition(long pos) throws IOException {
/* 82 */     this.raf.seek(pos);
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 87 */     this.raf.close();
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\file\GDRandomAccessBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */