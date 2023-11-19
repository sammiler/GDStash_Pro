/*     */ package org.gdstash.db;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemSlots
/*     */   implements Cloneable
/*     */ {
/*     */   public boolean slotAxe1H;
/*     */   public boolean slotAxe2H;
/*     */   public boolean slotDagger1H;
/*     */   public boolean slotMace1H;
/*     */   public boolean slotMace2H;
/*     */   public boolean slotScepter1H;
/*     */   public boolean slotSpear1H;
/*     */   public boolean slotStaff2H;
/*     */   public boolean slotSword1H;
/*     */   public boolean slotSword2H;
/*     */   public boolean slotRanged1H;
/*     */   public boolean slotRanged2H;
/*     */   public boolean slotShield;
/*     */   public boolean slotOffhand;
/*     */   public boolean slotAmulet;
/*     */   public boolean slotBelt;
/*     */   public boolean slotMedal;
/*     */   public boolean slotRing;
/*     */   public boolean slotHead;
/*     */   public boolean slotShoulders;
/*     */   public boolean slotChest;
/*     */   public boolean slotHands;
/*     */   public boolean slotLegs;
/*     */   public boolean slotFeet;
/*     */   
/*     */   public ItemSlots clone() {
/*  40 */     ItemSlots slots = new ItemSlots();
/*     */     
/*  42 */     slots.slotAxe1H = this.slotAxe1H;
/*  43 */     slots.slotAxe2H = this.slotAxe2H;
/*  44 */     slots.slotDagger1H = this.slotDagger1H;
/*  45 */     slots.slotMace1H = this.slotMace1H;
/*  46 */     slots.slotMace2H = this.slotMace2H;
/*  47 */     slots.slotScepter1H = this.slotScepter1H;
/*  48 */     slots.slotSpear1H = this.slotSpear1H;
/*  49 */     slots.slotStaff2H = this.slotStaff2H;
/*  50 */     slots.slotSword1H = this.slotSword1H;
/*  51 */     slots.slotSword2H = this.slotSword2H;
/*  52 */     slots.slotRanged1H = this.slotRanged1H;
/*  53 */     slots.slotRanged2H = this.slotRanged2H;
/*  54 */     slots.slotShield = this.slotShield;
/*  55 */     slots.slotOffhand = this.slotOffhand;
/*  56 */     slots.slotAmulet = this.slotAmulet;
/*  57 */     slots.slotBelt = this.slotBelt;
/*  58 */     slots.slotMedal = this.slotMedal;
/*  59 */     slots.slotRing = this.slotRing;
/*  60 */     slots.slotHead = this.slotHead;
/*  61 */     slots.slotShoulders = this.slotShoulders;
/*  62 */     slots.slotChest = this.slotChest;
/*  63 */     slots.slotHands = this.slotHands;
/*  64 */     slots.slotLegs = this.slotLegs;
/*  65 */     slots.slotFeet = this.slotFeet;
/*     */     
/*  67 */     return slots;
/*     */   }
/*     */   
/*     */   public boolean usesSlots() {
/*  71 */     if (this.slotAxe1H) return true; 
/*  72 */     if (this.slotAxe2H) return true; 
/*  73 */     if (this.slotDagger1H) return true; 
/*  74 */     if (this.slotMace1H) return true; 
/*  75 */     if (this.slotMace2H) return true; 
/*  76 */     if (this.slotScepter1H) return true; 
/*  77 */     if (this.slotSpear1H) return true; 
/*  78 */     if (this.slotStaff2H) return true; 
/*  79 */     if (this.slotSword1H) return true; 
/*  80 */     if (this.slotSword2H) return true; 
/*  81 */     if (this.slotRanged1H) return true; 
/*  82 */     if (this.slotRanged2H) return true; 
/*  83 */     if (this.slotShield) return true; 
/*  84 */     if (this.slotOffhand) return true; 
/*  85 */     if (this.slotAmulet) return true; 
/*  86 */     if (this.slotBelt) return true; 
/*  87 */     if (this.slotMedal) return true; 
/*  88 */     if (this.slotRing) return true; 
/*  89 */     if (this.slotHead) return true; 
/*  90 */     if (this.slotShoulders) return true; 
/*  91 */     if (this.slotChest) return true; 
/*  92 */     if (this.slotHands) return true; 
/*  93 */     if (this.slotLegs) return true; 
/*  94 */     if (this.slotFeet) return true;
/*     */     
/*  96 */     return false;
/*     */   }
/*     */   
/*     */   public boolean usesAllArmor() {
/* 100 */     boolean all = (this.slotBelt && this.slotHead && this.slotShoulders && this.slotChest && this.slotHands && this.slotLegs && this.slotFeet);
/*     */     
/* 102 */     return all;
/*     */   }
/*     */   
/*     */   public boolean usesAllWeapons() {
/* 106 */     boolean all = (usesMeleeWeapons() && usesRangedWeapons());
/*     */     
/* 108 */     return all;
/*     */   }
/*     */   
/*     */   public boolean usesMeleeWeapons() {
/* 112 */     boolean all = (this.slotAxe1H && this.slotAxe2H && this.slotDagger1H && this.slotMace1H && this.slotMace2H && this.slotScepter1H && this.slotSword1H && this.slotSword2H);
/*     */ 
/*     */     
/* 115 */     return all;
/*     */   }
/*     */   
/*     */   public boolean usesRangedWeapons() {
/* 119 */     boolean all = (this.slotRanged1H && this.slotRanged2H);
/*     */     
/* 121 */     return all;
/*     */   }
/*     */   
/*     */   public boolean uses1HWeapons() {
/* 125 */     boolean all = (this.slotAxe1H && this.slotDagger1H && this.slotMace1H && this.slotScepter1H && this.slotSword1H && this.slotRanged1H);
/*     */ 
/*     */     
/* 128 */     return all;
/*     */   }
/*     */   
/*     */   public boolean uses2HWeapons() {
/* 132 */     boolean all = (this.slotAxe2H && this.slotMace2H && this.slotSword2H && this.slotRanged2H);
/*     */     
/* 134 */     return all;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 138 */     clearAllArmor();
/* 139 */     clearAllWeapons();
/*     */   }
/*     */   
/*     */   public void clearAllArmor() {
/* 143 */     this.slotBelt = false;
/* 144 */     this.slotHead = false;
/* 145 */     this.slotShoulders = false;
/* 146 */     this.slotChest = false;
/* 147 */     this.slotHands = false;
/* 148 */     this.slotLegs = false;
/* 149 */     this.slotFeet = false;
/*     */   }
/*     */   
/*     */   public void clearAllWeapons() {
/* 153 */     clearMeleeWeapons();
/* 154 */     clearRangedWeapons();
/*     */   }
/*     */   
/*     */   public void clearMeleeWeapons() {
/* 158 */     this.slotAxe1H = false;
/* 159 */     this.slotAxe2H = false;
/* 160 */     this.slotDagger1H = false;
/* 161 */     this.slotMace1H = false;
/* 162 */     this.slotMace2H = false;
/* 163 */     this.slotScepter1H = false;
/*     */     
/* 165 */     this.slotSword1H = false;
/* 166 */     this.slotSword2H = false;
/*     */   }
/*     */   
/*     */   public void clearRangedWeapons() {
/* 170 */     this.slotRanged1H = false;
/* 171 */     this.slotRanged2H = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear1HWeapons() {
/* 176 */     this.slotAxe1H = false;
/* 177 */     this.slotDagger1H = false;
/* 178 */     this.slotMace1H = false;
/* 179 */     this.slotScepter1H = false;
/*     */     
/* 181 */     this.slotSword1H = false;
/* 182 */     this.slotRanged1H = false;
/*     */   }
/*     */   
/*     */   public void clear2HWeapons() {
/* 186 */     this.slotAxe2H = false;
/* 187 */     this.slotMace2H = false;
/*     */     
/* 189 */     this.slotSword2H = false;
/* 190 */     this.slotRanged2H = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\ItemSlots.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */