/*    */ package org.gdstash.quest;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.gdstash.character.GDCharUID;
/*    */ import org.gdstash.file.GDReader;
/*    */ import org.gdstash.util.FileVersionException;
/*    */ import org.gdstash.util.GDMsgFormatter;
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
/*    */ 
/*    */ 
/*    */ public class GDQuest
/*    */ {
/*    */   private int id1;
/* 25 */   private GDCharUID id2 = new GDCharUID();
/* 26 */   private GDQuestTaskList tasks = new GDQuestTaskList();
/*    */ 
/*    */   
/*    */   public void read() throws IOException {
/* 30 */     int val = 0;
/*    */     
/* 32 */     this.id1 = GDReader.readEncInt(true);
/*    */     
/* 34 */     this.id2.read();
/*    */     
/* 36 */     GDReader.Block block = new GDReader.Block();
/*    */     
/* 38 */     val = GDReader.readBlockStart(block);
/* 39 */     if (val != 0) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*    */     
/* 41 */     this.tasks.read();
/*    */ 
/*    */     
/* 44 */     GDReader.readBlockEnd(block);
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\quest\GDQuest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */