/*    */ package org.gdstash.quest;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
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
/*    */ public class GDQuestList
/*    */ {
/*    */   private static final int BLOCK = 11;
/*    */   private int version;
/* 26 */   private List<GDQuest> quests = new LinkedList<>();
/*    */ 
/*    */   
/*    */   public void read() throws IOException {
/* 30 */     int val = 0;
/*    */     
/* 32 */     GDReader.Block block = new GDReader.Block();
/*    */     
/* 34 */     val = GDReader.readBlockStart(block);
/* 35 */     if (val != 11) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*    */     
/* 37 */     this.version = GDReader.readEncInt(true);
/* 38 */     if (this.version != 3) {
/* 39 */       throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*    */     }
/*    */     
/* 42 */     this.quests.clear();
/* 43 */     val = GDReader.readEncInt(true);
/* 44 */     for (int i = 0; i < val; i++) {
/* 45 */       GDQuest quest = new GDQuest();
/*    */       
/* 47 */       quest.read();
/*    */       
/* 49 */       this.quests.add(quest);
/*    */     } 
/*    */ 
/*    */     
/* 53 */     GDReader.readBlockEnd(block);
/*    */   }
/*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\quest\GDQuestList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */