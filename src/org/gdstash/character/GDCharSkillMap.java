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
/*    */ 
/*    */ public class GDCharSkillMap
/*    */ {
/* 20 */   private String skill = null;
/* 21 */   private int active = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void read() throws IOException {
/* 29 */     this.skill = GDReader.readEncString();
/* 30 */     this.active = GDReader.readEncInt(true);
/*    */   }
/*    */   
/*    */   public void write() throws IOException {
/* 34 */     GDWriter.writeString(this.skill);
/* 35 */     GDWriter.writeInt(this.active);
/*    */   }
/*    */   
/*    */   public int getByteSize() {
/* 39 */     int size = 0;
/*    */     
/* 41 */     size += 4;
/* 42 */     if (this.skill != null) size += this.skill.length();
/*    */     
/* 44 */     size += 4;
/*    */     
/* 46 */     return size;
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\character\GDCharSkillMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */