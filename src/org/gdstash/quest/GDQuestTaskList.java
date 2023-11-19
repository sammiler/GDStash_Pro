/*    */ package org.gdstash.quest;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import org.gdstash.file.GDReader;
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
/*    */ public class GDQuestTaskList
/*    */ {
/* 20 */   private List<GDQuestTask> tasks = new LinkedList<>();
/*    */ 
/*    */   
/*    */   public void read() throws IOException {
/* 24 */     int val = 0;
/*    */     
/* 26 */     this.tasks.clear();
/* 27 */     val = GDReader.readEncInt(true);
/* 28 */     for (int i = 0; i < val; i++) {
/* 29 */       GDQuestTask task = new GDQuestTask();
/*    */       
/* 31 */       task.read();
/*    */       
/* 33 */       this.tasks.add(task);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\quest\GDQuestTaskList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */