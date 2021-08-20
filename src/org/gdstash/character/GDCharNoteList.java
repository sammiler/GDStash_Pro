/*    */ package org.gdstash.character;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import org.gdstash.file.GDReader;
/*    */ import org.gdstash.file.GDWriter;
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
/*    */ public class GDCharNoteList
/*    */ {
/*    */   private static final int VERSION = 1;
/*    */   private static final int BLOCK = 12;
/*    */   private int version;
/* 27 */   private List<String> notes = new LinkedList<>();
/*    */ 
/*    */   
/*    */   public void read() throws IOException {
/* 31 */     int val = 0;
/*    */     
/* 33 */     GDReader.Block block = new GDReader.Block();
/*    */     
/* 35 */     val = GDReader.readBlockStart(block);
/* 36 */     if (val != 12) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*    */     
/* 38 */     this.version = GDReader.readEncInt(true);
/* 39 */     if (this.version != 1) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*    */     
/* 41 */     this.notes.clear();
/* 42 */     val = GDReader.readEncInt(true);
/* 43 */     for (int i = 0; i < val; i++) {
/* 44 */       String note = GDReader.readEncString();
/*    */       
/* 46 */       this.notes.add(note);
/*    */     } 
/*    */ 
/*    */     
/* 50 */     GDReader.readBlockEnd(block);
/*    */   }
/*    */   
/*    */   public void write() throws IOException {
/* 54 */     GDReader.Block block = new GDReader.Block();
/* 55 */     GDWriter.writeBlockStart(block, 12);
/*    */     
/* 57 */     GDWriter.writeInt(this.version);
/*    */     
/* 59 */     int val = this.notes.size();
/* 60 */     GDWriter.writeInt(val);
/*    */     
/* 62 */     for (String note : this.notes) {
/* 63 */       GDWriter.writeString(note);
/*    */     }
/*    */ 
/*    */     
/* 67 */     GDWriter.writeBlockEnd(block);
/*    */   }
/*    */   
/*    */   public int getByteSize() {
/* 71 */     int size = 0;
/*    */     
/* 73 */     size += 4;
/* 74 */     size += 4;
/* 75 */     size += 4;
/* 76 */     size += 4;
/*    */     
/* 78 */     for (String note : this.notes) {
/* 79 */       size += 4;
/* 80 */       if (note != null) size += note.length();
/*    */     
/*    */     } 
/* 83 */     size += 4;
/*    */     
/* 85 */     return size;
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\character\GDCharNoteList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */