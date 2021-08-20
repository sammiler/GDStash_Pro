/*     */ package org.gdstash.character;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.file.GDReader;
/*     */ import org.gdstash.file.GDWriter;
/*     */ import org.gdstash.ui.character.GDCharFactionPane;
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
/*     */ public class GDCharFactionList
/*     */ {
/*     */   private static final int VERSION = 5;
/*     */   private static final int BLOCK = 13;
/*     */   private int version;
/*  31 */   private List<GDCharFaction> factions = new LinkedList<>();
/*     */ 
/*     */   
/*     */   private int faction;
/*     */ 
/*     */   
/*     */   protected boolean changed = false;
/*     */ 
/*     */   
/*     */   public int[] getFactionReputations() {
/*  41 */     int[] reputations = new int[GDCharFactionPane.FACTIONS.length];
/*     */     
/*  43 */     int pos = 0;
/*  44 */     for (GDCharFaction faction : this.factions) {
/*  45 */       int i; for (i = 0; i < GDCharFactionPane.FACTIONS.length; i++) {
/*  46 */         if (GDCharFactionPane.FACTIONS[i] == pos) {
/*  47 */           reputations[i] = faction.getFactionReputation();
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/*  53 */       pos++;
/*     */     } 
/*     */     
/*  56 */     return reputations;
/*     */   }
/*     */   
/*     */   public boolean hasChanged() {
/*  60 */     return this.changed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFactionReputations(int[] reputations) {
/*  68 */     int pos = 0;
/*  69 */     for (GDCharFaction faction : this.factions) {
/*  70 */       int i; for (i = 0; i < GDCharFactionPane.FACTIONS.length; i++) {
/*  71 */         if (GDCharFactionPane.FACTIONS[i] == pos) {
/*  72 */           faction.setFactionReputation(reputations[i]);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/*  78 */       pos++;
/*     */     } 
/*     */     
/*  81 */     this.changed = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read() throws IOException {
/*  89 */     int val = 0;
/*     */     
/*  91 */     GDReader.Block block = new GDReader.Block();
/*     */     
/*  93 */     val = GDReader.readBlockStart(block);
/*  94 */     if (val != 13) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/*  96 */     this.version = GDReader.readEncInt(true);
/*  97 */     if (this.version != 5) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/*  99 */     this.faction = GDReader.readEncInt(true);
/*     */     
/* 101 */     this.factions.clear();
/* 102 */     val = GDReader.readEncInt(true);
/* 103 */     for (int i = 0; i < val; i++) {
/* 104 */       GDCharFaction f = new GDCharFaction();
/* 105 */       f.read();
/*     */       
/* 107 */       this.factions.add(f);
/*     */     } 
/*     */ 
/*     */     
/* 111 */     GDReader.readBlockEnd(block);
/*     */     
/* 113 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/* 117 */     GDReader.Block block = new GDReader.Block();
/* 118 */     GDWriter.writeBlockStart(block, 13);
/*     */     
/* 120 */     GDWriter.writeInt(5);
/*     */     
/* 122 */     GDWriter.writeInt(this.faction);
/*     */     
/* 124 */     int val = this.factions.size();
/* 125 */     GDWriter.writeInt(val);
/*     */     
/* 127 */     for (GDCharFaction faction : this.factions) {
/* 128 */       if (faction != null) faction.write();
/*     */     
/*     */     } 
/*     */     
/* 132 */     GDWriter.writeBlockEnd(block);
/*     */     
/* 134 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/* 138 */     int size = 0;
/*     */     
/* 140 */     size += 4;
/* 141 */     size += 4;
/* 142 */     size += 4;
/* 143 */     size += 4;
/* 144 */     size += 4;
/* 145 */     size += this.factions.size() * GDCharFaction.getByteSize();
/* 146 */     size += 4;
/*     */     
/* 148 */     return size;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\character\GDCharFactionList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */