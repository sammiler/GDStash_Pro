/*    */ package org.gdstash.character;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.gdstash.file.GDReader;
/*    */ import org.gdstash.file.GDWriter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GDCharUID
/*    */ {
/*    */   private byte[] uid;
/*    */   
/*    */   public GDCharUID() {
/* 18 */     this.uid = new byte[16];
/*    */   }
/*    */   
/*    */   public GDCharUID(byte[] uid) {
/* 22 */     this.uid = uid;
/*    */     
/* 24 */     if (uid.length != 16) this.uid = new byte[16]; 
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/* 28 */     if (o == null) return false; 
/* 29 */     if (o.getClass() != GDCharUID.class) return false;
/*    */     
/* 31 */     GDCharUID guid = (GDCharUID)o;
/*    */     
/* 33 */     for (int i = 0; i < this.uid.length; i++) {
/* 34 */       if (this.uid[i] > guid.uid[i]) return false; 
/* 35 */       if (this.uid[i] < guid.uid[i]) return false;
/*    */     
/*    */     } 
/* 38 */     return true;
/*    */   }
/*    */   
/*    */   public void read() throws IOException {
/* 42 */     for (int i = 0; i < this.uid.length; i++) {
/* 43 */       this.uid[i] = GDReader.readEncByte();
/*    */     }
/*    */   }
/*    */   
/*    */   public void write() throws IOException {
/* 48 */     for (int i = 0; i < this.uid.length; i++) {
/* 49 */       GDWriter.writeByte(this.uid[i]);
/*    */     }
/*    */   }
/*    */   
/*    */   public static int getByteSize() {
/* 54 */     return 16;
/*    */   }
/*    */   
/*    */   public void println() {
/* 58 */     String s = "";
/* 59 */     for (int i = 0; i < this.uid.length; i++) {
/* 60 */       String b = Byte.toString(this.uid[i]);
/* 61 */       for (; b.length() < 4; b = " " + b);
/* 62 */       if (i == 0) {
/* 63 */         s = b;
/*    */       } else {
/* 65 */         s = s + ", " + b;
/*    */       } 
/*    */     } 
/*    */     
/* 69 */     System.out.println(s);
/*    */   }
/*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\character\GDCharUID.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */