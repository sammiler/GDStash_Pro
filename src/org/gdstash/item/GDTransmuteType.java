/*     */ package org.gdstash.item;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.db.DBItem;
/*     */ import org.gdstash.db.ItemClass;
/*     */ import org.gdstash.file.GDFileSize;
/*     */ import org.gdstash.file.GDReader;
/*     */ import org.gdstash.file.GDWriter;
/*     */ import org.gdstash.util.GDLog;
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
/*     */ public class GDTransmuteType
/*     */   implements GDFileSize
/*     */ {
/*     */   private int itemType;
/*  28 */   private List<String> items = new LinkedList<>();
/*     */ 
/*     */   
/*     */   public GDTransmuteType(int itemType) {
/*  32 */     this();
/*     */     
/*  34 */     this.itemType = itemType;
/*     */   }
/*     */   public GDTransmuteType() {}
/*     */   public int getTransmuteType() {
/*  38 */     return this.itemType;
/*     */   }
/*     */   
/*     */   public void insertItem(DBItem item) {
/*  42 */     if (item == null)
/*     */       return; 
/*  44 */     int type = ItemClass.getTransmuteType(item.getItemClass());
/*  45 */     String meshID = item.getMeshID();
/*  46 */     String baseID = item.getBaseTextureID();
/*  47 */     String bumpID = item.getBumpTextureID();
/*  48 */     String glowID = item.getGlowTextureID();
/*  49 */     String shaderID = item.getShaderID();
/*  50 */     String itemID = item.getItemID();
/*  51 */     int level = item.getItemLevel();
/*     */     
/*  53 */     if (type != this.itemType)
/*  54 */       return;  if (meshID == null)
/*  55 */       return;  if (meshID.equals("items/gearaccessories/medals/medal_visible.msh"))
/*  56 */       return;  if (itemID == null)
/*  57 */       return;  if (item.isEnemyOnly())
/*     */       return; 
/*  59 */     boolean found = false;
/*     */     
/*  61 */     Iterator<String> iter = this.items.iterator();
/*  62 */     while (iter.hasNext()) {
/*  63 */       String s = iter.next();
/*     */       
/*  65 */       DBItem dbi = DBItem.get(s);
/*     */       
/*  67 */       if (dbi != null) {
/*  68 */         if (itemID.equals(dbi.getItemID())) {
/*  69 */           found = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/*  74 */         if (meshID.equals(dbi.getMeshID())) {
/*  75 */           String base2 = dbi.getBaseTextureID();
/*  76 */           String bump2 = dbi.getBumpTextureID();
/*  77 */           String glow2 = dbi.getGlowTextureID();
/*  78 */           String shader2 = dbi.getShaderID();
/*     */           
/*  80 */           boolean baseFound = false;
/*  81 */           boolean bumpFound = false;
/*  82 */           boolean glowFound = false;
/*  83 */           boolean shaderFound = false;
/*     */           
/*  85 */           if (baseID == null) {
/*  86 */             if (base2 == null) {
/*  87 */               baseFound = true;
/*     */             }
/*     */           }
/*  90 */           else if (baseID.equals(base2)) {
/*  91 */             baseFound = true;
/*     */           } 
/*     */ 
/*     */           
/*  95 */           if (bumpID == null) {
/*  96 */             if (bump2 == null) {
/*  97 */               bumpFound = true;
/*     */             }
/*     */           }
/* 100 */           else if (bumpID.equals(bump2)) {
/* 101 */             bumpFound = true;
/*     */           } 
/*     */ 
/*     */           
/* 105 */           if (glowID == null) {
/* 106 */             if (glow2 == null) {
/* 107 */               glowFound = true;
/*     */             }
/*     */           }
/* 110 */           else if (glowID.equals(glow2)) {
/* 111 */             glowFound = true;
/*     */           } 
/*     */ 
/*     */           
/* 115 */           if (shaderID == null) {
/* 116 */             if (shader2 == null) {
/* 117 */               shaderFound = true;
/*     */             }
/*     */           }
/* 120 */           else if (shaderID.equals(shader2)) {
/* 121 */             shaderFound = true;
/*     */           } 
/*     */ 
/*     */           
/* 125 */           if (baseFound && bumpFound && glowFound && shaderFound) {
/* 126 */             if (level < dbi.getItemLevel()) {
/* 127 */               iter.remove(); continue;
/*     */             } 
/* 129 */             found = true;
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 138 */     if (!found) {
/* 139 */       this.items.add(itemID);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeItem(DBItem item) {
/* 144 */     if (item == null)
/*     */       return; 
/* 146 */     int type = ItemClass.getTransmuteType(item.getItemClass());
/* 147 */     String meshID = item.getMeshID();
/* 148 */     String baseID = item.getBaseTextureID();
/* 149 */     String bumpID = item.getBumpTextureID();
/* 150 */     String itemID = item.getItemID();
/* 151 */     int level = item.getItemLevel();
/*     */     
/* 153 */     if (type != this.itemType)
/*     */       return; 
/* 155 */     Iterator<String> iter = this.items.iterator();
/* 156 */     while (iter.hasNext()) {
/* 157 */       String s = iter.next();
/*     */       
/* 159 */       if (itemID.equals(s)) {
/* 160 */         iter.remove();
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
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
/*     */   public void read(GDLog log) throws IOException {
/* 183 */     GDReader.Block block = new GDReader.Block();
/* 184 */     int val = GDReader.readBlockStart(block);
/*     */     
/* 186 */     this.itemType = GDReader.readEncInt(true);
/*     */     
/* 188 */     int numItems = GDReader.readEncInt(true);
/*     */     
/* 190 */     this.items.clear(); int i;
/* 191 */     for (i = 0; i < numItems; i++) {
/* 192 */       String itemName = GDReader.readEncString();
/*     */       
/* 194 */       this.items.add(itemName);
/*     */     } 
/*     */     
/* 197 */     GDReader.readBlockEnd(block);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getByteSize() {
/* 202 */     int size = 0;
/*     */     
/* 204 */     size += 4;
/* 205 */     size += 4;
/*     */     
/* 207 */     size += 4;
/* 208 */     size += 4;
/*     */ 
/*     */     
/* 211 */     for (String s : this.items) {
/* 212 */       size += 4;
/* 213 */       size += s.length();
/*     */     } 
/*     */     
/* 216 */     size += 4;
/*     */     
/* 218 */     return size;
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/* 222 */     GDReader.Block block = new GDReader.Block();
/* 223 */     GDWriter.writeBlockStart(block, 0);
/*     */     
/* 225 */     GDWriter.writeInt(this.itemType);
/*     */     
/* 227 */     GDWriter.writeInt(this.items.size());
/*     */     
/* 229 */     for (String s : this.items) {
/* 230 */       GDWriter.writeString(s);
/*     */     }
/*     */     
/* 233 */     GDWriter.writeBlockEnd(block);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\item\GDTransmuteType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */