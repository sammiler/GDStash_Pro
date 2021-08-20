/*     */ package org.gdstash.character;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.file.GDReader;
/*     */ import org.gdstash.file.GDWriter;
/*     */ import org.gdstash.item.GDItem;
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
/*     */ public class GDCharInventory
/*     */ {
/*     */   public static final int EQUIPPOS_HEAD = 0;
/*     */   public static final int EQUIPPOS_AMULET = 1;
/*     */   public static final int EQUIPPOS_CHEST = 2;
/*     */   public static final int EQUIPPOS_LEGS = 3;
/*     */   public static final int EQUIPPOS_FEET = 4;
/*     */   public static final int EQUIPPOS_HANDS = 5;
/*     */   public static final int EQUIPPOS_RING_LEFT = 6;
/*     */   public static final int EQUIPPOS_RING_RIGHT = 7;
/*     */   public static final int EQUIPPOS_BELT = 8;
/*     */   public static final int EQUIPPOS_SHOULDERS = 9;
/*     */   public static final int EQUIPPOS_MEDAL = 10;
/*     */   public static final int EQUIPPOS_ARTIFACT = 11;
/*     */   public static final int EQUIPPOS_MAINHAND = 0;
/*     */   public static final int EQUIPPOS_OFFHAND = 1;
/*     */   private static final int VERSION = 4;
/*     */   private static final int BLOCK = 3;
/*     */   private int version;
/*     */   private List<GDCharInventorySack> sacks;
/*     */   private GDCharEquippedContainer equipment;
/*     */   private int focused;
/*     */   private int selected;
/*     */   private byte flag;
/*     */   private GDChar gdc;
/*     */   
/*     */   public GDCharInventory(GDChar gdc) {
/*  50 */     this.sacks = new LinkedList<>();
/*  51 */     this.equipment = new GDCharEquippedContainer(gdc);
/*     */     
/*  53 */     this.gdc = gdc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GDCharInventorySack getInventory() {
/*  61 */     GDCharInventorySack sack = null;
/*     */     
/*  63 */     if (this.sacks != null) {
/*     */       
/*     */       try {
/*  66 */         sack = this.sacks.get(0);
/*     */       }
/*  68 */       catch (IndexOutOfBoundsException ex) {
/*  69 */         sack = null;
/*     */       } 
/*     */     }
/*     */     
/*  73 */     return sack;
/*     */   }
/*     */   
/*     */   public List<GDCharInventorySack> getBags() {
/*  77 */     List<GDCharInventorySack> list = new LinkedList<>();
/*     */     
/*  79 */     if (this.sacks != null) {
/*  80 */       int i = 0;
/*  81 */       for (GDCharInventorySack sack : this.sacks) {
/*     */         
/*  83 */         if (i > 0) list.add(sack);
/*     */         
/*  85 */         i++;
/*     */       } 
/*     */     } 
/*     */     
/*  89 */     return list;
/*     */   }
/*     */   
/*     */   public GDCharEquippedContainer getEquipment() {
/*  93 */     return this.equipment;
/*     */   }
/*     */   
/*     */   public List<GDItem> getItems() {
/*  97 */     List<GDItem> list = new LinkedList<>();
/*     */     
/*  99 */     for (GDCharInventorySack sack : this.sacks) {
/* 100 */       list.addAll(sack.getItemList());
/*     */     }
/*     */     
/* 103 */     list.addAll(this.equipment.getItemList());
/*     */     
/* 105 */     return list;
/*     */   }
/*     */   
/*     */   public boolean hasChanged() {
/* 109 */     boolean hasChanged = false;
/*     */     
/* 111 */     if (this.sacks != null) {
/* 112 */       for (GDCharInventorySack sack : this.sacks) {
/* 113 */         hasChanged = (hasChanged || sack.hasChanged());
/*     */       }
/*     */     }
/*     */     
/* 117 */     hasChanged = (hasChanged || this.equipment.hasChanged());
/*     */     
/* 119 */     return hasChanged;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean addBag() {
/* 128 */     boolean added = false;
/*     */     
/* 130 */     if (this.sacks != null) {
/* 131 */       int pos = 0;
/* 132 */       for (GDCharInventorySack sack : this.sacks) {
/* 133 */         pos++;
/*     */       }
/*     */       
/* 136 */       if (pos <= 5) {
/* 137 */         GDCharInventorySack bag = new GDCharInventorySack(this.gdc, pos);
/* 138 */         this.sacks.add(bag);
/*     */         
/* 140 */         added = true;
/*     */       } 
/*     */     } 
/*     */     
/* 144 */     return added;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read() throws IOException {
/* 152 */     int val = 0;
/*     */     
/* 154 */     GDReader.Block block = new GDReader.Block();
/*     */     
/* 156 */     val = GDReader.readBlockStart(block);
/* 157 */     if (val != 3) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/* 159 */     this.version = GDReader.readEncInt(true);
/* 160 */     if (this.version != 4) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/* 162 */     this.flag = GDReader.readEncByte();
/* 163 */     if (this.flag != 0) {
/* 164 */       int numSacks = GDReader.readEncInt(true);
/*     */       
/* 166 */       this.focused = GDReader.readEncInt(true);
/* 167 */       this.selected = GDReader.readEncInt(true);
/*     */       
/* 169 */       this.sacks.clear(); int i;
/* 170 */       for (i = 0; i < numSacks; i++) {
/* 171 */         GDCharInventorySack sack = new GDCharInventorySack(this.gdc, i);
/* 172 */         sack.read();
/*     */         
/* 174 */         this.sacks.add(sack);
/*     */       } 
/*     */       
/* 177 */       this.equipment.read();
/*     */     } 
/*     */ 
/*     */     
/* 181 */     GDReader.readBlockEnd(block);
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/* 185 */     GDReader.Block block = new GDReader.Block();
/* 186 */     GDWriter.writeBlockStart(block, 3);
/*     */     
/* 188 */     GDWriter.writeInt(4);
/*     */     
/* 190 */     GDWriter.writeByte(this.flag);
/*     */     
/* 192 */     if (this.flag != 0) {
/* 193 */       int numSacks = this.sacks.size();
/* 194 */       GDWriter.writeInt(numSacks);
/*     */       
/* 196 */       GDWriter.writeInt(this.focused);
/* 197 */       GDWriter.writeInt(this.selected);
/*     */       
/* 199 */       for (GDCharInventorySack sack : this.sacks) {
/* 200 */         sack.write();
/*     */       }
/*     */       
/* 203 */       this.equipment.write();
/*     */     } 
/*     */ 
/*     */     
/* 207 */     GDWriter.writeBlockEnd(block);
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/* 211 */     int size = 0;
/*     */     
/* 213 */     size += 4;
/* 214 */     size += 4;
/* 215 */     size += 4;
/* 216 */     size++;
/*     */     
/* 218 */     if (this.flag != 0) {
/* 219 */       size += 4;
/*     */       
/* 221 */       size += 4;
/* 222 */       size += 4;
/*     */       
/* 224 */       for (GDCharInventorySack sack : this.sacks) {
/* 225 */         size += sack.getByteSize();
/*     */       }
/*     */       
/* 228 */       size += this.equipment.getByteSize();
/*     */     } 
/*     */     
/* 231 */     size += 4;
/*     */     
/* 233 */     return size;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\character\GDCharInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */