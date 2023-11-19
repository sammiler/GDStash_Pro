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
/*    */ public class GDQuestTokenList
/*    */ {
/*    */   private static final int BLOCK = 10;
/*    */   private int version;
/* 26 */   private List<String> tokens = new LinkedList<>();
/*    */ 
/*    */   
/*    */   public void read() throws IOException {
/* 30 */     int val = 0;
/*    */     
/* 32 */     GDReader.Block block = new GDReader.Block();
/*    */     
/* 34 */     val = GDReader.readBlockStart(block);
/* 35 */     if (val != 10) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*    */     
/* 37 */     this.version = GDReader.readEncInt(true);
/* 38 */     if (this.version != 2) {
/* 39 */       throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*    */     }
/*    */     
/* 42 */     this.tokens.clear();
/* 43 */     val = GDReader.readEncInt(true);
/* 44 */     for (int i = 0; i < val; i++) {
/* 45 */       String token = GDReader.readEncString();
/*    */       
/* 47 */       this.tokens.add(token);
/*    */     } 
/*    */ 
/*    */     
/* 51 */     GDReader.readBlockEnd(block);
/*    */   }
/*    */   
/*    */   public void listTokens() {
/* 55 */     for (String s : this.tokens)
/* 56 */       System.out.println(s); 
/*    */   }
/*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\quest\GDQuestTokenList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */