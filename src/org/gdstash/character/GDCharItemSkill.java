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
/*    */ public class GDCharItemSkill
/*    */ {
/*    */   private String name;
/*    */   private String autoCastSkill;
/*    */   private String autoCastController;
/*    */   private int itemSlot;
/*    */   private String itemID;
/*    */   
/*    */   public void read() throws IOException {
/* 23 */     this.name = GDReader.readEncString();
/* 24 */     this.autoCastSkill = GDReader.readEncString();
/* 25 */     this.autoCastController = GDReader.readEncString();
/* 26 */     this.itemSlot = GDReader.readEncInt(true);
/* 27 */     this.itemID = GDReader.readEncString();
/*    */   }
/*    */   
/*    */   public void write() throws IOException {
/* 31 */     GDWriter.writeString(this.name);
/* 32 */     GDWriter.writeString(this.autoCastSkill);
/* 33 */     GDWriter.writeString(this.autoCastController);
/* 34 */     GDWriter.writeInt(this.itemSlot);
/* 35 */     GDWriter.writeString(this.itemID);
/*    */   }
/*    */   
/*    */   public int getByteSize() {
/* 39 */     int size = 0;
/*    */     
/* 41 */     size += 4;
/* 42 */     if (this.name != null) size += this.name.length();
/*    */     
/* 44 */     size += 4;
/* 45 */     if (this.autoCastSkill != null) size += this.autoCastSkill.length();
/*    */     
/* 47 */     size += 4;
/* 48 */     if (this.autoCastController != null) size += this.autoCastController.length();
/*    */     
/* 50 */     size += 4;
/*    */     
/* 52 */     size += 4;
/* 53 */     if (this.itemID != null) size += this.itemID.length();
/*    */     
/* 55 */     return size;
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\character\GDCharItemSkill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */