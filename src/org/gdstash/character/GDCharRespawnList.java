/*     */ package org.gdstash.character;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ public class GDCharRespawnList
/*     */ {
/*  20 */   private static final byte[] RESPAWN_DEVILS_CROSSING = new byte[] { 110, -113, 45, 112, -107, 79, 56, 103, 93, 21, 75, -98, 88, 4, 108, 6 };
/*     */   
/*  22 */   private static final byte[] RESPAWN_HOMESTEAD = new byte[] { -121, 64, 3, 63, -43, 76, -62, 58, -20, 76, -104, -69, 28, -65, -111, -115 };
/*     */   
/*  24 */   private static final byte[] RESPAWN_FORT_IKON = new byte[] { Byte.MIN_VALUE, 79, -90, 79, 36, 79, 46, -40, -91, -68, 58, -87, 124, 114, 33, -26 };
/*     */ 
/*     */   
/*  27 */   public static final GDCharUID UID_SPAWN_DEVILS_CROSSING = new GDCharUID(RESPAWN_DEVILS_CROSSING);
/*  28 */   public static final GDCharUID UID_SPAWN_HOMESTEAD = new GDCharUID(RESPAWN_HOMESTEAD);
/*  29 */   public static final GDCharUID UID_SPAWN_FORT_IKON = new GDCharUID(RESPAWN_FORT_IKON);
/*     */   
/*     */   private static final int VERSION = 1;
/*     */   
/*     */   private static final int BLOCK = 5;
/*     */   
/*     */   private int version;
/*     */   
/*     */   private ArrayList<List<GDCharUID>> uidLists;
/*     */   private GDCharUID[] spawns;
/*     */   private boolean changed;
/*     */   
/*     */   public GDCharRespawnList() {
/*  42 */     int size = 3;
/*     */     
/*  44 */     this.uidLists = new ArrayList<>(size);
/*  45 */     for (int i = 0; i < size; i++) {
/*  46 */       this.uidLists.add(new LinkedList<>());
/*     */     }
/*     */     
/*  49 */     this.spawns = new GDCharUID[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GDCharUID getRespawnPoint(int difficulty) {
/*  57 */     GDCharUID spawn = null;
/*     */     
/*  59 */     if (difficulty < 0 || difficulty > 2) return spawn;
/*     */     
/*  61 */     return this.spawns[difficulty];
/*     */   }
/*     */   
/*     */   public boolean hasChanged() {
/*  65 */     return this.changed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRespawnPoint(int difficulty, GDCharUID spawn) {
/*  73 */     if (difficulty < 0 || difficulty > 2)
/*     */       return; 
/*  75 */     this.spawns[difficulty] = spawn;
/*     */     
/*  77 */     this.changed = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read() throws IOException {
/*  85 */     int val = 0;
/*     */     
/*  87 */     GDReader.Block block = new GDReader.Block();
/*     */     
/*  89 */     val = GDReader.readBlockStart(block);
/*  90 */     if (val != 5) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/*  92 */     this.version = GDReader.readEncInt(true);
/*  93 */     if (this.version != 1) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION")); 
/*     */     int i;
/*  95 */     for (i = 0; i < this.uidLists.size(); i++) {
/*  96 */       GDChar.readUIDList(this.uidLists.get(i));
/*     */     }
/*     */     
/*  99 */     for (i = 0; i < this.spawns.length; i++) {
/* 100 */       GDCharUID uid = new GDCharUID();
/* 101 */       uid.read();
/*     */       
/* 103 */       this.spawns[i] = uid;
/*     */     } 
/*     */ 
/*     */     
/* 107 */     GDReader.readBlockEnd(block);
/*     */     
/* 109 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/* 113 */     GDReader.Block block = new GDReader.Block();
/* 114 */     GDWriter.writeBlockStart(block, 5);
/*     */     
/* 116 */     GDWriter.writeInt(this.version);
/*     */     int i;
/* 118 */     for (i = 0; i < this.uidLists.size(); i++) {
/* 119 */       GDChar.writeUIDList(this.uidLists.get(i));
/*     */     }
/*     */     
/* 122 */     for (i = 0; i < this.spawns.length; i++) {
/* 123 */       this.spawns[i].write();
/*     */     }
/*     */ 
/*     */     
/* 127 */     GDWriter.writeBlockEnd(block);
/*     */     
/* 129 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/* 133 */     int size = 0;
/*     */     
/* 135 */     size += 4;
/* 136 */     size += 4;
/* 137 */     size += 4;
/*     */     
/* 139 */     for (int i = 0; i < this.uidLists.size(); i++) {
/* 140 */       size += 4;
/* 141 */       size += ((List)this.uidLists.get(i)).size() * GDCharUID.getByteSize();
/*     */     } 
/*     */     
/* 144 */     size += this.spawns.length * GDCharUID.getByteSize();
/*     */     
/* 146 */     size += 4;
/*     */     
/* 148 */     return size;
/*     */   }
/*     */   public void print() {
/*     */     int i;
/* 152 */     for (i = 0; i < this.uidLists.size(); i++) {
/* 153 */       System.out.println("Index : " + Integer.toString(i));
/*     */       
/* 155 */       String s = "";
/* 156 */       for (GDCharUID uid : this.uidLists.get(i)) {
/* 157 */         uid.println();
/*     */       }
/*     */       
/* 160 */       System.out.println();
/*     */     } 
/*     */     
/* 163 */     for (i = 0; i < this.spawns.length; i++) {
/* 164 */       System.out.println("Spawn : " + Integer.toString(i));
/*     */       
/* 166 */       this.spawns[i].println();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\character\GDCharRespawnList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */