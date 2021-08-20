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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GDCharFaction
/*    */ {
/*    */   private byte modified;
/*    */   private byte unlocked;
/*    */   private float value;
/*    */   private float positiveBoost;
/*    */   private float negativeBoost;
/*    */   
/*    */   public int getFactionReputation() {
/* 26 */     return (int)this.value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFactionReputation(int reputation) {
/* 34 */     this.value = reputation;
/*    */     
/* 36 */     if (reputation != 0) {
/* 37 */       this.modified = 1;
/* 38 */       this.unlocked = 1;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void read() throws IOException {
/* 47 */     this.modified = GDReader.readEncByte();
/* 48 */     this.unlocked = GDReader.readEncByte();
/* 49 */     this.value = GDReader.readEncFloat(true);
/* 50 */     this.positiveBoost = GDReader.readEncFloat(true);
/* 51 */     this.negativeBoost = GDReader.readEncFloat(true);
/*    */   }
/*    */   
/*    */   public void write() throws IOException {
/* 55 */     GDWriter.writeByte(this.modified);
/* 56 */     GDWriter.writeByte(this.unlocked);
/* 57 */     GDWriter.writeFloat(this.value);
/* 58 */     GDWriter.writeFloat(this.positiveBoost);
/* 59 */     GDWriter.writeFloat(this.negativeBoost);
/*    */   }
/*    */   
/*    */   public static int getByteSize() {
/* 63 */     int size = 0;
/*    */     
/* 65 */     size++;
/* 66 */     size++;
/* 67 */     size += 4;
/* 68 */     size += 4;
/* 69 */     size += 4;
/*    */     
/* 71 */     return size;
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\character\GDCharFaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */