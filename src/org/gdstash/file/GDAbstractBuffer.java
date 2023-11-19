/*    */ package org.gdstash.file;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class GDAbstractBuffer
/*    */   implements GDBuffer
/*    */ {
/*    */   public byte[] getByteArray(long pos, int length) throws IOException {
/* 20 */     setPosition(pos);
/*    */     
/* 22 */     return getByteArray(length);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getBytes8() throws IOException {
/* 27 */     return getByteArray(8);
/*    */   }
/*    */ 
/*    */   
/*    */   public long getUnsignedInt() throws IOException {
/* 32 */     int i = getInt();
/*    */     
/* 34 */     long temp = i;
/*    */     
/* 36 */     if (i < 0) {
/* 37 */       temp = intToUnsignedLong(i);
/*    */     }
/*    */     
/* 40 */     return temp;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getString(int len) throws IOException {
/* 45 */     if (len <= 0) return "";
/*    */     
/* 47 */     byte[] bytes = getByteArray(len);
/* 48 */     StringBuilder sb = new StringBuilder(bytes.length);
/*    */     int i;
/* 50 */     for (i = 0; i < bytes.length; i++) {
/* 51 */       sb.append((char)bytes[i]);
/*    */     }
/*    */     
/* 54 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getStringLB() throws IOException {
/* 59 */     byte len = getByte();
/*    */     
/* 61 */     return getString(len);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getStringNT() throws IOException {
/* 66 */     StringBuilder sb = new StringBuilder(256);
/*    */     
/* 68 */     byte b = getByte();
/* 69 */     while (b != 0) {
/* 70 */       sb.append((char)b);
/*    */       
/* 72 */       b = getByte();
/*    */     } 
/*    */     
/* 75 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public ARZString getARZString() throws IOException {
/* 80 */     ARZString str = new ARZString();
/*    */     
/* 82 */     str.len = getInt();
/* 83 */     str.str = getString(str.len);
/*    */     
/* 85 */     return str;
/*    */   }
/*    */   
/*    */   public static String bytesToString(byte[] bytes) {
/* 89 */     StringBuilder sb = new StringBuilder(bytes.length);
/*    */     
/* 91 */     for (int i = 0; i < bytes.length; i++) {
/* 92 */       sb.append((char)bytes[i]);
/*    */     }
/*    */     
/* 95 */     return sb.toString();
/*    */   }
/*    */   
/*    */   public static long intToUnsignedLong(int i) {
/* 99 */     return i & 0xFFFFFFFFL;
/*    */   }
/*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\file\GDAbstractBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */