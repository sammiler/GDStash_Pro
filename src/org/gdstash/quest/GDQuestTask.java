/*    */ package org.gdstash.quest;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
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
/*    */ public class GDQuestTask
/*    */ {
/*    */   private int id1;
/* 27 */   private GDCharUID id2 = new GDCharUID();
/* 28 */   List<Integer> objectives = new LinkedList<>(); private int state;
/*    */   private byte inProgress;
/*    */   
/*    */   public void read() throws IOException {
/* 32 */     int val = 0;
/*    */     
/* 34 */     this.id1 = GDReader.readEncInt(true);
/*    */     
/* 36 */     this.id2.read();
/*    */     
/* 38 */     GDReader.Block block = new GDReader.Block();
/*    */     
/* 40 */     val = GDReader.readBlockStart(block);
/* 41 */     if (val != 0) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*    */     
/* 43 */     this.state = GDReader.readEncInt(true);
/* 44 */     this.inProgress = GDReader.readEncByte();
/*    */     
/* 46 */     this.objectives.clear();
/* 47 */     int size = (block.length - 5) / 4; int i;
/* 48 */     for (i = 0; i < size; i++) {
/* 49 */       val = GDReader.readEncInt(true);
/*    */       
/* 51 */       this.objectives.add(Integer.valueOf(val));
/*    */     } 
/*    */ 
/*    */     
/* 55 */     GDReader.readBlockEnd(block);
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\quest\GDQuestTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */