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
/*     */ 
/*     */ public class GDCharUISettings
/*     */ {
/*  34 */   private GDCharUISlot[] slots = null;
/*  35 */   private String[] unknown4 = new String[5];
/*  36 */   private String[] unknown5 = new String[5];
/*  37 */   private byte[] unknown6 = new byte[5];
/*     */   
/*     */   private static final int VERSION_4 = 4;
/*     */   private static final int VERSION_5 = 5;
/*     */   private static final int VERSION_6 = 6;
/*     */   private static final int BLOCK = 14;
/*     */   
/*     */   public void read() throws IOException {
/*  45 */     int val = 0;
/*     */     
/*  47 */     GDReader.Block block = new GDReader.Block();
/*     */     
/*  49 */     val = GDReader.readBlockStart(block);
/*  50 */     if (val != 14) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/*  52 */     this.version = GDReader.readEncInt(true);
/*  53 */     if (this.version != 4 && this.version != 5 && this.version != 6)
/*     */     {
/*     */       
/*  56 */       throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     }
/*     */     
/*  59 */     this.unknown1 = GDReader.readEncByte();
/*  60 */     this.unknown2 = GDReader.readEncInt(true);
/*  61 */     this.unknown3 = GDReader.readEncByte();
/*     */     int i;
/*  63 */     for (i = 0; i < this.unknown4.length; i++) {
/*  64 */       this.unknown4[i] = GDReader.readEncString();
/*  65 */       this.unknown5[i] = GDReader.readEncString();
/*  66 */       this.unknown6[i] = GDReader.readEncByte();
/*     */     } 
/*     */     
/*  69 */     if (this.version >= 6) {
/*  70 */       this.slots = new GDCharUISlot[47];
/*     */     }
/*  72 */     else if (this.version >= 5) {
/*  73 */       this.slots = new GDCharUISlot[46];
/*     */     } else {
/*  75 */       this.slots = new GDCharUISlot[36];
/*     */     } 
/*     */ 
/*     */     
/*  79 */     for (i = 0; i < this.slots.length; i++) {
/*  80 */       GDCharUISlot slot = new GDCharUISlot();
/*  81 */       slot.read();
/*     */       
/*  83 */       this.slots[i] = slot;
/*     */     } 
/*     */     
/*  86 */     this.cameraDistance = GDReader.readEncFloat(true);
/*     */ 
/*     */     
/*  89 */     GDReader.readBlockEnd(block);
/*     */   }
/*     */   private int version; private byte unknown1; private int unknown2; private byte unknown3; private float cameraDistance;
/*     */   public void write() throws IOException {
/*  93 */     GDReader.Block block = new GDReader.Block();
/*  94 */     GDWriter.writeBlockStart(block, 14);
/*     */     
/*  96 */     GDWriter.writeInt(this.version);
/*     */     
/*  98 */     GDWriter.writeByte(this.unknown1);
/*  99 */     GDWriter.writeInt(this.unknown2);
/* 100 */     GDWriter.writeByte(this.unknown3);
/*     */     int i;
/* 102 */     for (i = 0; i < this.unknown4.length; i++) {
/* 103 */       GDWriter.writeString(this.unknown4[i]);
/* 104 */       GDWriter.writeString(this.unknown5[i]);
/* 105 */       GDWriter.writeByte(this.unknown6[i]);
/*     */     } 
/*     */     
/* 108 */     for (i = 0; i < this.slots.length; i++) {
/* 109 */       this.slots[i].write();
/*     */     }
/*     */     
/* 112 */     GDWriter.writeFloat(this.cameraDistance);
/*     */ 
/*     */     
/* 115 */     GDWriter.writeBlockEnd(block);
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/* 119 */     int size = 0;
/*     */     
/* 121 */     size += 4;
/* 122 */     size += 4;
/* 123 */     size += 4;
/* 124 */     size++;
/* 125 */     size += 4;
/* 126 */     size++;
/*     */     int i;
/* 128 */     for (i = 0; i < this.unknown4.length; i++) {
/* 129 */       size += 4;
/* 130 */       if (this.unknown4[i] != null) size += this.unknown4[4].length();
/*     */       
/* 132 */       size += 4;
/* 133 */       if (this.unknown5[i] != null) size += this.unknown5[4].length();
/*     */       
/* 135 */       size++;
/*     */     } 
/*     */     
/* 138 */     for (i = 0; i < this.slots.length; i++) {
/* 139 */       size += this.slots[i].getByteSize();
/*     */     }
/*     */     
/* 142 */     size += 4;
/* 143 */     size += 4;
/*     */     
/* 145 */     return size;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\character\GDCharUISettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */