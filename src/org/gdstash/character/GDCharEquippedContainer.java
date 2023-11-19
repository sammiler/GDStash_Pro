/*     */ package org.gdstash.character;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.file.GDReader;
/*     */ import org.gdstash.file.GDWriter;
/*     */ import org.gdstash.item.GDAbstractContainer;
/*     */ import org.gdstash.item.GDItem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDCharEquippedContainer
/*     */   extends GDAbstractContainer
/*     */ {
/*     */   private GDItem[] equipment;
/*     */   private GDItem[] weapon1;
/*     */   private GDItem[] weapon2;
/*     */   private GDItem[] removedEquipment;
/*     */   private GDItem[] removedWeapon1;
/*     */   private GDItem[] removedWeapon2;
/*     */   private byte useAlternate;
/*     */   private byte alternate1;
/*     */   private byte alternate2;
/*     */   private GDChar gdc;
/*     */   
/*     */   public GDCharEquippedContainer(GDChar gdc) {
/*  33 */     super(6);
/*     */     
/*  35 */     this.equipment = new GDItem[12];
/*  36 */     this.weapon1 = new GDItem[2];
/*  37 */     this.weapon2 = new GDItem[2];
/*     */     
/*  39 */     this.removedEquipment = new GDItem[this.equipment.length];
/*  40 */     this.removedWeapon1 = new GDItem[this.weapon1.length];
/*  41 */     this.removedWeapon2 = new GDItem[this.weapon2.length];
/*     */     
/*  43 */     this.gdc = gdc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<GDItem> getItemList() {
/*  52 */     List<GDItem> list = new LinkedList<>();
/*     */     int i;
/*  54 */     for (i = 0; i < this.equipment.length; i++) {
/*  55 */       if (this.equipment[i] != null) {
/*  56 */         GDItem item = this.equipment[i];
/*     */ 
/*     */         
/*  59 */         if (item.getItemID() != null) {
/*  60 */           item.setX(0);
/*  61 */           item.setY(i);
/*     */           
/*  63 */           list.add(item);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  68 */     for (i = 0; i < this.weapon1.length; i++) {
/*  69 */       if (this.weapon1[i] != null) {
/*  70 */         GDItem item = this.weapon1[i];
/*     */ 
/*     */         
/*  73 */         if (item.getItemID() != null) {
/*  74 */           item.setX(1);
/*  75 */           item.setY(i);
/*     */           
/*  77 */           list.add(item);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     for (i = 0; i < this.weapon2.length; i++) {
/*  83 */       if (this.weapon2[i] != null) {
/*  84 */         GDItem item = this.weapon2[i];
/*     */ 
/*     */         
/*  87 */         if (item.getItemID() != null) {
/*  88 */           item.setX(2);
/*  89 */           item.setY(i);
/*     */           
/*  91 */           list.add(item);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  96 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addItem(GDItem item) {
/* 101 */     if (!canAddItem(item)) return false;
/*     */     
/* 103 */     int x = item.getX();
/* 104 */     int y = item.getY();
/*     */     
/* 106 */     switch (x) {
/*     */       
/*     */       case 0:
/* 109 */         this.equipment[y] = item;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 114 */         this.weapon1[y] = item;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 119 */         this.weapon2[y] = item;
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 124 */     this.changed = true;
/*     */     
/* 126 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeItem(GDItem item) {
/* 131 */     boolean found = false;
/*     */     
/* 133 */     int x = item.getX();
/* 134 */     int y = item.getY();
/*     */     
/* 136 */     switch (x) {
/*     */       
/*     */       case 0:
/* 139 */         if (this.equipment.length > y && 
/* 140 */           this.equipment[y] == item) {
/* 141 */           this.equipment[y] = new GDItem(this.gdc.getCharName(), this.gdc.isHardcore(), 6);
/* 142 */           this.equipment[y].setX(x);
/* 143 */           this.equipment[y].setY(y);
/*     */           
/* 145 */           found = true;
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 152 */         if (this.weapon1.length > y && 
/* 153 */           this.weapon1[y] == item) {
/* 154 */           this.weapon1[y] = new GDItem(this.gdc.getCharName(), this.gdc.isHardcore(), 6);
/* 155 */           this.weapon1[y].setX(x);
/* 156 */           this.weapon1[y].setY(y);
/*     */           
/* 158 */           found = true;
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/* 165 */         if (this.weapon2.length > y && 
/* 166 */           this.weapon2[y] == item) {
/* 167 */           this.weapon2[y] = new GDItem(this.gdc.getCharName(), this.gdc.isHardcore(), 6);
/* 168 */           this.weapon2[y].setX(x);
/* 169 */           this.weapon2[y].setY(y);
/*     */           
/* 171 */           found = true;
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 178 */     if (found) this.changed = true;
/*     */     
/* 180 */     return found;
/*     */   }
/*     */   
/*     */   private boolean canAddItem(GDItem item) {
/* 184 */     int x = item.getX();
/* 185 */     int y = item.getY();
/*     */     
/* 187 */     boolean possible = false;
/*     */ 
/*     */     
/* 190 */     if (x == 0) {
/* 191 */       switch (y) {
/*     */         case 0:
/* 193 */           if (this.equipment[0].getItemID() == null && 
/* 194 */             item.getItemClass().equals("ArmorProtective_Head")) possible = true;
/*     */           
/*     */           break;
/*     */         
/*     */         case 1:
/* 199 */           if (this.equipment[1].getItemID() == null && 
/* 200 */             item.getItemClass().equals("ArmorJewelry_Amulet")) possible = true;
/*     */           
/*     */           break;
/*     */         
/*     */         case 2:
/* 205 */           if (this.equipment[2].getItemID() == null && 
/* 206 */             item.getItemClass().equals("ArmorProtective_Chest")) possible = true;
/*     */           
/*     */           break;
/*     */         
/*     */         case 3:
/* 211 */           if (this.equipment[3].getItemID() == null && 
/* 212 */             item.getItemClass().equals("ArmorProtective_Legs")) possible = true;
/*     */           
/*     */           break;
/*     */         
/*     */         case 4:
/* 217 */           if (this.equipment[4].getItemID() == null && 
/* 218 */             item.getItemClass().equals("ArmorProtective_Feet")) possible = true;
/*     */           
/*     */           break;
/*     */         
/*     */         case 5:
/* 223 */           if (this.equipment[5].getItemID() == null && 
/* 224 */             item.getItemClass().equals("ArmorProtective_Hands")) possible = true;
/*     */           
/*     */           break;
/*     */         
/*     */         case 6:
/* 229 */           if (this.equipment[6].getItemID() == null && 
/* 230 */             item.getItemClass().equals("ArmorJewelry_Ring")) possible = true;
/*     */           
/*     */           break;
/*     */         
/*     */         case 7:
/* 235 */           if (this.equipment[7].getItemID() == null && 
/* 236 */             item.getItemClass().equals("ArmorJewelry_Ring")) possible = true;
/*     */           
/*     */           break;
/*     */         
/*     */         case 8:
/* 241 */           if (this.equipment[8].getItemID() == null && 
/* 242 */             item.getItemClass().equals("ArmorProtective_Waist")) possible = true;
/*     */           
/*     */           break;
/*     */         
/*     */         case 9:
/* 247 */           if (this.equipment[9].getItemID() == null && 
/* 248 */             item.getItemClass().equals("ArmorProtective_Shoulders")) possible = true;
/*     */           
/*     */           break;
/*     */         
/*     */         case 10:
/* 253 */           if (this.equipment[10].getItemID() == null && 
/* 254 */             item.getItemClass().equals("ArmorJewelry_Medal")) possible = true;
/*     */           
/*     */           break;
/*     */         
/*     */         case 11:
/* 259 */           if (this.equipment[11].getItemID() == null && 
/* 260 */             item.getItemClass().equals("ItemArtifact")) possible = true;
/*     */           
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     }
/* 267 */     if (x == 1) {
/* 268 */       switch (y) {
/*     */         case 0:
/* 270 */           if (this.weapon1[0].getItemID() == null) {
/* 271 */             if (item.is1HWeapon()) {
/* 272 */               possible = true;
/*     */             }
/*     */             
/* 275 */             if (item.is2HWeapon()) {
/* 276 */               GDItem gdi = this.weapon1[1];
/*     */               
/* 278 */               if (gdi == null || gdi
/* 279 */                 .getItemID() == null) {
/* 280 */                 possible = true;
/*     */               }
/*     */             } 
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 1:
/* 287 */           if (this.weapon1[1].getItemID() == null && (
/* 288 */             item.is1HWeapon() || item.isOffhand())) {
/* 289 */             GDItem gdi = this.weapon1[0];
/*     */             
/* 291 */             if (gdi == null || gdi
/* 292 */               .getItemID() == null) {
/* 293 */               possible = true; break;
/*     */             } 
/* 295 */             if (gdi.is1HWeapon()) possible = true;
/*     */           
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 304 */     if (x == 2) {
/* 305 */       switch (y) {
/*     */         case 0:
/* 307 */           if (this.weapon2[0].getItemID() == null) {
/* 308 */             if (item.is1HWeapon()) {
/* 309 */               possible = true;
/*     */             }
/*     */             
/* 312 */             if (item.is2HWeapon()) {
/* 313 */               GDItem gdi = this.weapon2[1];
/*     */               
/* 315 */               if (gdi == null || gdi
/* 316 */                 .getItemID() == null) {
/* 317 */                 possible = true;
/*     */               }
/*     */             } 
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 1:
/* 324 */           if (this.weapon2[1].getItemID() == null && (
/* 325 */             item.is1HWeapon() || item.isOffhand())) {
/* 326 */             GDItem gdi = this.weapon2[0];
/*     */             
/* 328 */             if (gdi == null || gdi
/* 329 */               .getItemID() == null) {
/* 330 */               possible = true; break;
/*     */             } 
/* 332 */             if (gdi.is1HWeapon()) possible = true;
/*     */           
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     }
/* 340 */     return possible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read() throws IOException {
/* 348 */     this.useAlternate = GDReader.readEncByte();
/*     */     int i;
/* 350 */     for (i = 0; i < this.equipment.length; i++) {
/* 351 */       GDItem item = new GDItem(this.gdc.getCharName(), this.gdc.isHardcore(), 6);
/* 352 */       item.read();
/*     */       
/* 354 */       if (item.hasErrors()) {
/* 355 */         this.removedEquipment[i] = item;
/*     */         
/* 357 */         item = new GDItem(this.gdc.getCharName(), this.gdc.isHardcore(), 6);
/*     */       } else {
/* 359 */         this.removedEquipment[i] = null;
/*     */       } 
/*     */       
/* 362 */       this.equipment[i] = item;
/*     */     } 
/*     */     
/* 365 */     this.alternate1 = GDReader.readEncByte();
/*     */     
/* 367 */     for (i = 0; i < this.weapon1.length; i++) {
/* 368 */       GDItem item = new GDItem(this.gdc.getCharName(), this.gdc.isHardcore(), 6);
/* 369 */       item.read();
/*     */       
/* 371 */       if (item.hasErrors()) {
/* 372 */         this.removedWeapon1[i] = item;
/*     */         
/* 374 */         item = new GDItem(this.gdc.getCharName(), this.gdc.isHardcore(), 6);
/*     */       } else {
/* 376 */         this.removedWeapon1[i] = null;
/*     */       } 
/*     */       
/* 379 */       this.weapon1[i] = item;
/*     */     } 
/*     */     
/* 382 */     this.alternate2 = GDReader.readEncByte();
/*     */     
/* 384 */     for (i = 0; i < this.weapon2.length; i++) {
/* 385 */       GDItem item = new GDItem(this.gdc.getCharName(), this.gdc.isHardcore(), 6);
/* 386 */       item.read();
/*     */       
/* 388 */       if (item.hasErrors()) {
/* 389 */         this.removedWeapon2[i] = item;
/*     */         
/* 391 */         item = new GDItem(this.gdc.getCharName(), this.gdc.isHardcore(), 6);
/*     */       } else {
/* 393 */         this.removedWeapon2[i] = null;
/*     */       } 
/*     */       
/* 396 */       this.weapon2[i] = item;
/*     */     } 
/*     */     
/* 399 */     this.changed = false;
/*     */   }
/*     */   private void mergeRemovedItems() {
/*     */     int i;
/* 403 */     for (i = 0; i < this.removedEquipment.length; i++) {
/* 404 */       if (this.removedEquipment[i] != null) this.equipment[i] = this.removedEquipment[i];
/*     */     
/*     */     } 
/* 407 */     for (i = 0; i < this.removedWeapon1.length; i++) {
/* 408 */       if (this.removedWeapon1[i] != null) this.weapon1[i] = this.removedWeapon1[i];
/*     */     
/*     */     } 
/* 411 */     for (i = 0; i < this.removedWeapon2.length; i++) {
/* 412 */       if (this.removedWeapon2[i] != null) this.weapon2[i] = this.removedWeapon2[i]; 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/* 417 */     mergeRemovedItems();
/*     */     
/* 419 */     GDWriter.writeByte(this.useAlternate);
/*     */     int i;
/* 421 */     for (i = 0; i < this.equipment.length; i++) {
/* 422 */       this.equipment[i].write();
/*     */     }
/*     */     
/* 425 */     GDWriter.writeByte(this.alternate1);
/*     */     
/* 427 */     for (i = 0; i < this.weapon1.length; i++) {
/* 428 */       this.weapon1[i].write();
/*     */     }
/*     */     
/* 431 */     GDWriter.writeByte(this.alternate2);
/*     */     
/* 433 */     for (i = 0; i < this.weapon2.length; i++) {
/* 434 */       this.weapon2[i].write();
/*     */     }
/*     */     
/* 437 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/* 441 */     mergeRemovedItems();
/*     */     
/* 443 */     int size = 0;
/*     */     
/* 445 */     size++;
/*     */     int i;
/* 447 */     for (i = 0; i < this.equipment.length; i++) {
/* 448 */       size += this.equipment[i].getByteSize();
/*     */     }
/*     */     
/* 451 */     size++;
/*     */     
/* 453 */     for (i = 0; i < this.weapon1.length; i++) {
/* 454 */       size += this.weapon1[i].getByteSize();
/*     */     }
/*     */     
/* 457 */     size++;
/*     */     
/* 459 */     for (i = 0; i < this.weapon2.length; i++) {
/* 460 */       size += this.weapon2[i].getByteSize();
/*     */     }
/*     */     
/* 463 */     return size;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\character\GDCharEquippedContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */