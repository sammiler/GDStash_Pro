/*     */ package org.gdstash.character;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ public class GDCharStats
/*     */ {
/*     */   private static final int VERSION_7 = 7;
/*     */   private static final int VERSION_9 = 9;
/*     */   private static final int VERSION_11 = 11;
/*     */   private static final int BLOCK = 16;
/*     */   private int version;
/*     */   private int playTime;
/*     */   private int deaths;
/*     */   private int kills;
/*     */   private int experienceFromKills;
/*     */   private int healthPotionsUsed;
/*     */   private int manaPotionsUsed;
/*     */   private int maxLevel;
/*     */   private int hitsReceived;
/*     */   private int hitsInflicted;
/*     */   private int criticalHitsInflicted;
/*     */   private int criticalHitsReceived;
/*     */   private float greatestDamageInflicted;
/*     */   private StatsPerDifficulty[] statsDifficulty;
/*     */   private int championKills;
/*     */   private float lastHit;
/*     */   private float lastHitBy;
/*     */   private float greatestDamageReceived;
/*     */   private int heroKills;
/*     */   private int itemsCrafted;
/*     */   private int relicsCrafted;
/*     */   private int transcendentRelicsCrafted;
/*     */   private int mythicalRelicsCrafted;
/*     */   private int shrinesRestored;
/*     */   private int oneShotChestsOpened;
/*     */   private int loreNotesCollected;
/*     */   private int survivalGreatestWave;
/*     */   private int survivalGreatestScore;
/*     */   private int survivalDefensesBuilt;
/*     */   private int survivalPowerUpsActivated;
/*     */   private List<GDCharSkillMap> skillMap;
/*     */   private int endlessSouls;
/*     */   private int endlessEssence;
/*     */   private byte difficultySkip;
/*     */   private int unknown1;
/*     */   private int unknown2;
/*     */   private boolean changed;
/*     */   
/*     */   private static class StatsPerDifficulty
/*     */   {
/*     */     private String greatestMonsterKilledName;
/*     */     private int greatestMonsterKilledLevel;
/*     */     private int greatestMonsterKilledLifeAndMana;
/*     */     private String lastMonsterHit;
/*     */     private String lastMonsterHitBy;
/*     */     private int nemesisKills;
/*     */     
/*     */     private StatsPerDifficulty() {}
/*     */   }
/*     */   
/*     */   public GDCharStats() {
/*  75 */     this.playTime = 0;
/*  76 */     this.deaths = 0;
/*  77 */     this.kills = 0;
/*  78 */     this.experienceFromKills = 0;
/*  79 */     this.healthPotionsUsed = 0;
/*  80 */     this.manaPotionsUsed = 0;
/*  81 */     this.maxLevel = 0;
/*  82 */     this.hitsReceived = 0;
/*  83 */     this.hitsInflicted = 0;
/*  84 */     this.criticalHitsInflicted = 0;
/*  85 */     this.criticalHitsReceived = 0;
/*  86 */     this.greatestDamageInflicted = 0.0F;
/*  87 */     this.statsDifficulty = new StatsPerDifficulty[3];
/*  88 */     for (int i = 0; i < this.statsDifficulty.length; i++) {
/*  89 */       this.statsDifficulty[i] = new StatsPerDifficulty();
/*     */     }
/*  91 */     this.championKills = 0;
/*  92 */     this.lastHit = 0.0F;
/*  93 */     this.lastHitBy = 0.0F;
/*  94 */     this.greatestDamageReceived = 0.0F;
/*  95 */     this.heroKills = 0;
/*  96 */     this.itemsCrafted = 0;
/*  97 */     this.relicsCrafted = 0;
/*  98 */     this.transcendentRelicsCrafted = 0;
/*  99 */     this.mythicalRelicsCrafted = 0;
/* 100 */     this.shrinesRestored = 0;
/* 101 */     this.oneShotChestsOpened = 0;
/* 102 */     this.loreNotesCollected = 0;
/* 103 */     this.survivalGreatestWave = 0;
/* 104 */     this.survivalGreatestScore = 0;
/* 105 */     this.survivalDefensesBuilt = 0;
/* 106 */     this.survivalPowerUpsActivated = 0;
/* 107 */     this.skillMap = new LinkedList<>();
/* 108 */     this.endlessSouls = 0;
/* 109 */     this.endlessEssence = 0;
/* 110 */     this.difficultySkip = 0;
/* 111 */     this.unknown1 = 0;
/* 112 */     this.unknown2 = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 120 */     return this.changed;
/*     */   }
/*     */   
/*     */   public int getDeaths() {
/* 124 */     return this.deaths;
/*     */   }
/*     */   
/*     */   public int getPlayTime() {
/* 128 */     return this.playTime;
/*     */   }
/*     */   
/*     */   public String getPlayTimeAsDHM() {
/* 132 */     String s = null;
/*     */     
/* 134 */     int hours = this.playTime / 3600;
/* 135 */     int days = hours / 24;
/* 136 */     int secs = this.playTime % 3600;
/* 137 */     int mins = secs / 60;
/*     */     
/* 139 */     hours %= 24;
/* 140 */     secs %= 60;
/*     */     
/* 142 */     s = String.format("%02d", new Object[] { Integer.valueOf(days) });
/* 143 */     s = s + ":" + String.format("%02d", new Object[] { Integer.valueOf(hours) });
/* 144 */     s = s + ":" + String.format("%02d", new Object[] { Integer.valueOf(mins) });
/*     */     
/* 146 */     return s;
/*     */   }
/*     */   
/*     */   public int getGreatestDamage() {
/* 150 */     return (int)this.greatestDamageInflicted;
/*     */   }
/*     */   
/*     */   public int getKillsMonster() {
/* 154 */     return this.kills;
/*     */   }
/*     */   
/*     */   public int getKillsHero() {
/* 158 */     return this.championKills;
/*     */   }
/*     */   
/*     */   public int getKillsNemesis() {
/* 162 */     int nemesisKills = 0;
/*     */     
/* 164 */     for (int i = 1; i < this.statsDifficulty.length; i++) {
/* 165 */       nemesisKills += (this.statsDifficulty[i]).nemesisKills;
/*     */     }
/*     */     
/* 168 */     return nemesisKills;
/*     */   }
/*     */   
/*     */   public int getLoreNotesCollected() {
/* 172 */     return this.loreNotesCollected;
/*     */   }
/*     */   
/*     */   public int getOneShotChestsOpened() {
/* 176 */     return this.oneShotChestsOpened;
/*     */   }
/*     */   
/*     */   public int getItemsCrafted() {
/* 180 */     return this.itemsCrafted;
/*     */   }
/*     */   
/*     */   public int getRelicsCrafted() {
/* 184 */     return this.relicsCrafted;
/*     */   }
/*     */   
/*     */   public int getRelicsTranscendentCrafted() {
/* 188 */     return this.transcendentRelicsCrafted;
/*     */   }
/*     */   
/*     */   public int getRelicsMythicalCrafted() {
/* 192 */     return this.mythicalRelicsCrafted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeaths(int deaths) {
/* 200 */     this.deaths = deaths;
/*     */     
/* 202 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setShrinesRestored(int shrinesRestored) {
/* 206 */     this.shrinesRestored = shrinesRestored;
/*     */     
/* 208 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setMaxLevel(int level) {
/* 212 */     this.maxLevel = level;
/*     */     
/* 214 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setGreatestDamage(float damage) {
/* 218 */     this.greatestDamageInflicted = damage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read() throws IOException {
/* 226 */     int val = 0;
/*     */     
/* 228 */     GDReader.Block block = new GDReader.Block();
/*     */     
/* 230 */     val = GDReader.readBlockStart(block);
/* 231 */     if (val != 16) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/* 233 */     this.version = GDReader.readEncInt(true);
/* 234 */     if (this.version != 7 && this.version != 9 && this.version != 11)
/*     */     {
/*     */       
/* 237 */       throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     }
/*     */     
/* 240 */     this.playTime = GDReader.readEncInt(true);
/* 241 */     this.deaths = GDReader.readEncInt(true);
/* 242 */     this.kills = GDReader.readEncInt(true);
/* 243 */     this.experienceFromKills = GDReader.readEncInt(true);
/* 244 */     this.healthPotionsUsed = GDReader.readEncInt(true);
/* 245 */     this.manaPotionsUsed = GDReader.readEncInt(true);
/* 246 */     this.maxLevel = GDReader.readEncInt(true);
/* 247 */     this.hitsReceived = GDReader.readEncInt(true);
/* 248 */     this.hitsInflicted = GDReader.readEncInt(true);
/* 249 */     this.criticalHitsInflicted = GDReader.readEncInt(true);
/* 250 */     this.criticalHitsReceived = GDReader.readEncInt(true);
/* 251 */     this.greatestDamageInflicted = GDReader.readEncFloat(true);
/*     */     int i;
/* 253 */     for (i = 0; i < this.statsDifficulty.length; i++) {
/* 254 */       (this.statsDifficulty[i]).greatestMonsterKilledName = GDReader.readEncString();
/* 255 */       (this.statsDifficulty[i]).greatestMonsterKilledLevel = GDReader.readEncInt(true);
/* 256 */       (this.statsDifficulty[i]).greatestMonsterKilledLifeAndMana = GDReader.readEncInt(true);
/* 257 */       (this.statsDifficulty[i]).lastMonsterHit = GDReader.readEncString();
/* 258 */       (this.statsDifficulty[i]).lastMonsterHitBy = GDReader.readEncString();
/*     */     } 
/*     */     
/* 261 */     this.championKills = GDReader.readEncInt(true);
/* 262 */     this.lastHit = GDReader.readEncFloat(true);
/* 263 */     this.lastHitBy = GDReader.readEncFloat(true);
/* 264 */     this.greatestDamageReceived = GDReader.readEncFloat(true);
/* 265 */     this.heroKills = GDReader.readEncInt(true);
/* 266 */     this.itemsCrafted = GDReader.readEncInt(true);
/* 267 */     this.relicsCrafted = GDReader.readEncInt(true);
/* 268 */     this.transcendentRelicsCrafted = GDReader.readEncInt(true);
/* 269 */     this.mythicalRelicsCrafted = GDReader.readEncInt(true);
/* 270 */     this.shrinesRestored = GDReader.readEncInt(true);
/* 271 */     this.oneShotChestsOpened = GDReader.readEncInt(true);
/* 272 */     this.loreNotesCollected = GDReader.readEncInt(true);
/*     */     
/* 274 */     for (i = 0; i < this.statsDifficulty.length; i++) {
/* 275 */       (this.statsDifficulty[i]).nemesisKills = GDReader.readEncInt(true);
/*     */     }
/*     */     
/* 278 */     if (this.version >= 9) {
/* 279 */       this.survivalGreatestWave = GDReader.readEncInt(true);
/* 280 */       this.survivalGreatestScore = GDReader.readEncInt(true);
/* 281 */       this.survivalDefensesBuilt = GDReader.readEncInt(true);
/* 282 */       this.survivalPowerUpsActivated = GDReader.readEncInt(true);
/*     */     } 
/*     */     
/* 285 */     if (this.version >= 11) {
/* 286 */       this.skillMap.clear();
/* 287 */       int len = GDReader.readEncInt(true); int j;
/* 288 */       for (j = 0; j < len; j++) {
/* 289 */         GDCharSkillMap map = new GDCharSkillMap();
/* 290 */         map.read();
/*     */         
/* 292 */         this.skillMap.add(map);
/*     */       } 
/*     */       
/* 295 */       this.endlessSouls = GDReader.readEncInt(true);
/* 296 */       this.endlessEssence = GDReader.readEncInt(true);
/* 297 */       this.difficultySkip = GDReader.readEncByte();
/*     */     } 
/*     */     
/* 300 */     this.unknown1 = GDReader.readEncInt(true);
/* 301 */     this.unknown2 = GDReader.readEncInt(true);
/*     */ 
/*     */     
/* 304 */     GDReader.readBlockEnd(block);
/*     */     
/* 306 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/* 310 */     GDReader.Block block = new GDReader.Block();
/* 311 */     GDWriter.writeBlockStart(block, 16);
/*     */     
/* 313 */     GDWriter.writeInt(this.version);
/*     */     
/* 315 */     GDWriter.writeInt(this.playTime);
/* 316 */     GDWriter.writeInt(this.deaths);
/* 317 */     GDWriter.writeInt(this.kills);
/* 318 */     GDWriter.writeInt(this.experienceFromKills);
/* 319 */     GDWriter.writeInt(this.healthPotionsUsed);
/* 320 */     GDWriter.writeInt(this.manaPotionsUsed);
/* 321 */     GDWriter.writeInt(this.maxLevel);
/* 322 */     GDWriter.writeInt(this.hitsReceived);
/* 323 */     GDWriter.writeInt(this.hitsInflicted);
/* 324 */     GDWriter.writeInt(this.criticalHitsInflicted);
/* 325 */     GDWriter.writeInt(this.criticalHitsReceived);
/* 326 */     GDWriter.writeFloat(this.greatestDamageInflicted);
/*     */     int i;
/* 328 */     for (i = 0; i < this.statsDifficulty.length; i++) {
/* 329 */       GDWriter.writeString((this.statsDifficulty[i]).greatestMonsterKilledName);
/* 330 */       GDWriter.writeInt((this.statsDifficulty[i]).greatestMonsterKilledLevel);
/* 331 */       GDWriter.writeInt((this.statsDifficulty[i]).greatestMonsterKilledLifeAndMana);
/* 332 */       GDWriter.writeString((this.statsDifficulty[i]).lastMonsterHit);
/* 333 */       GDWriter.writeString((this.statsDifficulty[i]).lastMonsterHitBy);
/*     */     } 
/*     */     
/* 336 */     GDWriter.writeInt(this.championKills);
/* 337 */     GDWriter.writeFloat(this.lastHit);
/* 338 */     GDWriter.writeFloat(this.lastHitBy);
/* 339 */     GDWriter.writeFloat(this.greatestDamageReceived);
/* 340 */     GDWriter.writeInt(this.heroKills);
/* 341 */     GDWriter.writeInt(this.itemsCrafted);
/* 342 */     GDWriter.writeInt(this.relicsCrafted);
/* 343 */     GDWriter.writeInt(this.transcendentRelicsCrafted);
/* 344 */     GDWriter.writeInt(this.mythicalRelicsCrafted);
/* 345 */     GDWriter.writeInt(this.shrinesRestored);
/* 346 */     GDWriter.writeInt(this.oneShotChestsOpened);
/* 347 */     GDWriter.writeInt(this.loreNotesCollected);
/*     */     
/* 349 */     for (i = 0; i < this.statsDifficulty.length; i++) {
/* 350 */       GDWriter.writeInt((this.statsDifficulty[i]).nemesisKills);
/*     */     }
/*     */     
/* 353 */     if (this.version >= 9) {
/* 354 */       GDWriter.writeInt(this.survivalGreatestWave);
/* 355 */       GDWriter.writeInt(this.survivalGreatestScore);
/* 356 */       GDWriter.writeInt(this.survivalDefensesBuilt);
/* 357 */       GDWriter.writeInt(this.survivalPowerUpsActivated);
/*     */     } 
/*     */     
/* 360 */     if (this.version >= 11) {
/* 361 */       int len = this.skillMap.size();
/* 362 */       GDWriter.writeInt(len);
/*     */       
/* 364 */       for (GDCharSkillMap map : this.skillMap) {
/* 365 */         if (map != null) map.write();
/*     */       
/*     */       } 
/* 368 */       GDWriter.writeInt(this.endlessSouls);
/* 369 */       GDWriter.writeInt(this.endlessEssence);
/* 370 */       GDWriter.writeByte(this.difficultySkip);
/*     */     } 
/*     */     
/* 373 */     GDWriter.writeInt(this.unknown1);
/* 374 */     GDWriter.writeInt(this.unknown2);
/*     */ 
/*     */     
/* 377 */     GDWriter.writeBlockEnd(block);
/*     */     
/* 379 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/* 383 */     int size = 0;
/* 384 */     int val = 0;
/*     */     
/* 386 */     size += 4;
/* 387 */     size += 4;
/* 388 */     size += 4;
/* 389 */     size += 4;
/* 390 */     size += 4;
/* 391 */     size += 4;
/* 392 */     size += 4;
/* 393 */     size += 4;
/* 394 */     size += 4;
/* 395 */     size += 4;
/* 396 */     size += 4;
/* 397 */     size += 4;
/* 398 */     size += 4;
/* 399 */     size += 4;
/* 400 */     size += 4;
/*     */     
/* 402 */     for (int i = 0; i < this.statsDifficulty.length; i++) {
/* 403 */       size += 4;
/* 404 */       if ((this.statsDifficulty[i]).greatestMonsterKilledName != null) size += (this.statsDifficulty[i]).greatestMonsterKilledName.length();
/*     */       
/* 406 */       size += 4;
/* 407 */       size += 4;
/*     */       
/* 409 */       size += 4;
/* 410 */       if ((this.statsDifficulty[i]).lastMonsterHit != null) size += (this.statsDifficulty[i]).lastMonsterHit.length();
/*     */       
/* 412 */       size += 4;
/* 413 */       if ((this.statsDifficulty[i]).lastMonsterHitBy != null) size += (this.statsDifficulty[i]).lastMonsterHitBy.length();
/*     */     
/*     */     } 
/* 416 */     size += 4;
/* 417 */     size += 4;
/* 418 */     size += 4;
/* 419 */     size += 4;
/* 420 */     size += 4;
/* 421 */     size += 4;
/* 422 */     size += 4;
/* 423 */     size += 4;
/* 424 */     size += 4;
/* 425 */     size += 4;
/* 426 */     size += 4;
/* 427 */     size += 4;
/*     */     
/* 429 */     size += 4 * this.statsDifficulty.length;
/*     */     
/* 431 */     if (this.version >= 9) {
/* 432 */       size += 4;
/* 433 */       size += 4;
/* 434 */       size += 4;
/* 435 */       size += 4;
/*     */     } 
/*     */     
/* 438 */     if (this.version >= 11) {
/* 439 */       size += 4;
/* 440 */       for (GDCharSkillMap map : this.skillMap) {
/* 441 */         if (map != null) size += map.getByteSize();
/*     */       
/*     */       } 
/* 444 */       size += 4;
/* 445 */       size += 4;
/* 446 */       size++;
/*     */     } 
/*     */     
/* 449 */     size += 4;
/* 450 */     size += 4;
/*     */     
/* 452 */     size += 4;
/*     */     
/* 454 */     return size;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\character\GDCharStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */