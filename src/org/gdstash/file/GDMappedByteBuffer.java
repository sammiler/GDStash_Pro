/*    */ package org.gdstash.file;
/*    */ 
/*    */ import java.io.EOFException;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.RandomAccessFile;
/*    */ import java.nio.ByteOrder;
/*    */ import java.nio.MappedByteBuffer;
/*    */ import java.nio.channels.FileChannel;
/*    */ import org.gdstash.util.GDMsgLogger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GDMappedByteBuffer
/*    */   extends GDAbstractBuffer
/*    */ {
/*    */   private File file;
/*    */   private RandomAccessFile raf;
/*    */   private MappedByteBuffer buffer;
/*    */   
/*    */   public GDMappedByteBuffer(File file) {
/* 26 */     this.file = file;
/*    */     
/*    */     try {
/* 29 */       this.raf = new RandomAccessFile(file, "r");
/* 30 */       this.buffer = this.raf.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, this.raf.length());
/* 31 */       this.buffer.order(ByteOrder.LITTLE_ENDIAN);
/*    */     }
/* 33 */     catch (IOException ex) {
/* 34 */       GDMsgLogger.addError(ex);
/*    */       
/*    */       try {
/* 37 */         close();
/*    */       }
/* 39 */       catch (IOException iOException) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getByteArray(int length) throws IOException {
/* 49 */     byte[] b = new byte[length];
/*    */     
/* 51 */     this.buffer.get(b);
/*    */     
/* 53 */     return b;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getByte() {
/* 58 */     return this.buffer.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getInt() throws IOException {
/* 63 */     return this.buffer.getInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public long getLong() throws IOException {
/* 68 */     return this.buffer.getLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public short getShort() throws IOException {
/* 73 */     return this.buffer.getShort();
/*    */   }
/*    */ 
/*    */   
/*    */   public long getPosition() throws IOException {
/* 78 */     return this.buffer.position();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPosition(long pos) throws IOException {
/* 83 */     if (pos > 2147483647L) throw new EOFException();
/*    */     
/* 85 */     this.buffer.position((int)pos);
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 90 */     this.buffer = null;
/*    */     
/* 92 */     this.raf.close();
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\file\GDMappedByteBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */