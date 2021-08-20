/*     */ package org.gdstash.character;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.gdstash.file.GDReader;
/*     */ import org.gdstash.file.GDWriter;
/*     */ import org.gdstash.util.FileVersionException;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDCharUISettings
/*     */ {
/*  33 */   private GDCharUISlot[] slots = null;
/*  34 */   private String[] unknown4 = new String[5];
/*  35 */   private String[] unknown5 = new String[5];
/*  36 */   private byte[] unknown6 = new byte[5];
/*     */   
/*     */   private static final int VERSION_4 = 4;
/*     */   private static final int VERSION_5 = 5;
/*     */   private static final int BLOCK = 14;
/*     */   private int version;
/*     */   
/*     */   public void read() throws IOException {
/*  44 */     int val = 0;
/*     */     
/*  46 */     GDReader.Block block = new GDReader.Block();
/*     */     
/*  48 */     val = GDReader.readBlockStart(block);
/*  49 */     if (val != 14) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/*  51 */     this.version = GDReader.readEncInt(true);
/*  52 */     if (this.version != 4 && this.version != 5)
/*     */     {
/*  54 */       throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     }
/*     */     
/*  57 */     this.unknown1 = GDReader.readEncByte();
/*  58 */     this.unknown2 = GDReader.readEncInt(true);
/*  59 */     this.unknown3 = GDReader.readEncByte();
/*     */     int i;
/*  61 */     for (i = 0; i < this.unknown4.length; i++) {
/*  62 */       this.unknown4[i] = GDReader.readEncString();
/*  63 */       this.unknown5[i] = GDReader.readEncString();
/*  64 */       this.unknown6[i] = GDReader.readEncByte();
/*     */     } 
/*     */     
/*  67 */     if (this.version >= 5) {
/*  68 */       this.slots = new GDCharUISlot[46];
/*     */     } else {
/*  70 */       this.slots = new GDCharUISlot[36];
/*     */     } 
/*     */     
/*  73 */     for (i = 0; i < this.slots.length; i++) {
/*  74 */       GDCharUISlot slot = new GDCharUISlot();
/*  75 */       slot.read();
/*     */       
/*  77 */       this.slots[i] = slot;
/*     */     } 
/*     */     
/*  80 */     this.cameraDistance = GDReader.readEncFloat(true);
/*     */ 
/*     */     
/*  83 */     GDReader.readBlockEnd(block);
/*     */   }
/*     */   private byte unknown1; private int unknown2; private byte unknown3; private float cameraDistance;
/*     */   public void write() throws IOException {
/*  87 */     GDReader.Block block = new GDReader.Block();
/*  88 */     GDWriter.writeBlockStart(block, 14);
/*     */     
/*  90 */     GDWriter.writeInt(this.version);
/*     */     
/*  92 */     GDWriter.writeByte(this.unknown1);
/*  93 */     GDWriter.writeInt(this.unknown2);
/*  94 */     GDWriter.writeByte(this.unknown3);
/*     */     int i;
/*  96 */     for (i = 0; i < this.unknown4.length; i++) {
/*  97 */       GDWriter.writeString(this.unknown4[i]);
/*  98 */       GDWriter.writeString(this.unknown5[i]);
/*  99 */       GDWriter.writeByte(this.unknown6[i]);
/*     */     } 
/*     */     
/* 102 */     for (i = 0; i < this.slots.length; i++) {
/* 103 */       this.slots[i].write();
/*     */     }
/*     */     
/* 106 */     GDWriter.writeFloat(this.cameraDistance);
/*     */ 
/*     */     
/* 109 */     GDWriter.writeBlockEnd(block);
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/* 113 */     int size = 0;
/*     */     
/* 115 */     size += 4;
/* 116 */     size += 4;
/* 117 */     size += 4;
/* 118 */     size++;
/* 119 */     size += 4;
/* 120 */     size++;
/*     */     int i;
/* 122 */     for (i = 0; i < this.unknown4.length; i++) {
/* 123 */       size += 4;
/* 124 */       if (this.unknown4[i] != null) size += this.unknown4[4].length();
/*     */       
/* 126 */       size += 4;
/* 127 */       if (this.unknown5[i] != null) size += this.unknown5[4].length();
/*     */       
/* 129 */       size++;
/*     */     } 
/*     */     
/* 132 */     for (i = 0; i < this.slots.length; i++) {
/* 133 */       size += this.slots[i].getByteSize();
/*     */     }
/*     */     
/* 136 */     size += 4;
/* 137 */     size += 4;
/*     */     
/* 139 */     return size;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\character\GDCharUISettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */