/*    */ package org.gdstash.character;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
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
/*    */ public class GDCharMarkerList
/*    */ {
/*    */   private static final int VERSION = 1;
/*    */   private static final int BLOCK = 7;
/*    */   private int version;
/*    */   private ArrayList<List<GDCharUID>> uidLists;
/*    */   
/*    */   public GDCharMarkerList() {
/* 28 */     int size = 3;
/*    */     
/* 30 */     this.uidLists = new ArrayList<>(size);
/* 31 */     for (int i = 0; i < size; i++) {
/* 32 */       this.uidLists.add(new LinkedList<>());
/*    */     }
/*    */   }
/*    */   
/*    */   public void read() throws IOException {
/* 37 */     int val = 0;
/*    */     
/* 39 */     GDReader.Block block = new GDReader.Block();
/*    */     
/* 41 */     val = GDReader.readBlockStart(block);
/* 42 */     if (val != 7) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*    */     
/* 44 */     this.version = GDReader.readEncInt(true);
/* 45 */     if (this.version != 1) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*    */     
/* 47 */     for (int i = 0; i < this.uidLists.size(); i++) {
/* 48 */       GDChar.readUIDList(this.uidLists.get(i));
/*    */     }
/*    */ 
/*    */     
/* 52 */     GDReader.readBlockEnd(block);
/*    */   }
/*    */   
/*    */   public void write() throws IOException {
/* 56 */     GDReader.Block block = new GDReader.Block();
/* 57 */     GDWriter.writeBlockStart(block, 7);
/*    */     
/* 59 */     GDWriter.writeInt(1);
/*    */     
/* 61 */     for (int i = 0; i < this.uidLists.size(); i++) {
/* 62 */       GDChar.writeUIDList(this.uidLists.get(i));
/*    */     }
/*    */ 
/*    */     
/* 66 */     GDWriter.writeBlockEnd(block);
/*    */   }
/*    */   
/*    */   public int getByteSize() {
/* 70 */     int size = 0;
/*    */     
/* 72 */     size += 4;
/* 73 */     size += 4;
/* 74 */     size += 4;
/*    */     
/* 76 */     for (int i = 0; i < this.uidLists.size(); i++) {
/* 77 */       size += 4;
/* 78 */       size += ((List)this.uidLists.get(i)).size() * GDCharUID.getByteSize();
/*    */     } 
/*    */     
/* 81 */     size += 4;
/*    */     
/* 83 */     return size;
/*    */   }
/*    */   
/*    */   public void print() {
/* 87 */     for (int i = 0; i < this.uidLists.size(); i++) {
/* 88 */       System.out.println("Index : " + Integer.toString(i));
/*    */       
/* 90 */       for (GDCharUID uid : this.uidLists.get(i)) {
/* 91 */         uid.println();
/*    */       }
/*    */       
/* 94 */       System.out.println();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\character\GDCharMarkerList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */