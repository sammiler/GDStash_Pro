/*     */ package org.gdstash.db;public class SelectionCriteria { public SelectionMode selMode; public CombinationMode combiMode; public SoulboundMode soulboundMode; public List<String> itemIDs; public List<String> itemClass; public List<String> armorClass; public List<String> artifactClass; public List<String> itemRarity; public List<String> itemStyle; public String itemName; public boolean setItem; public String bonusSkill; public boolean allSkills; public boolean masteryBonusSkills; public String modifiedSkill; public boolean masteryModifySkills; public boolean allItemSkills; public String itemSkill; public String prefixID; public String suffixID; public int levelMin; public int levelMax; public int cunningMax; public int physiqueMax; public int spiritMax; public String dmgConversionFrom; public String dmgConversionTo; public boolean noEnemyOnly; public DamageClassInfo dmgClassInfo; public FieldInfo fieldInfo; public List<StatInfo> statInfos; public boolean slotAxe1H; public boolean slotAxe2H; public boolean slotDagger1H; public boolean slotMace1H; public boolean slotMace2H; public boolean slotScepter1H; public boolean slotSpear1H; public boolean slotStaff2H; public boolean slotSword1H; public boolean slotSword2H; public boolean slotRanged1H;
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
/*     */   public boolean petBonus;
/*     */   
/*  17 */   public enum ComboType { AND, OR; }
/*     */   
/*     */   public static class StatInfo {
/*  20 */     public List<String> statTypes = null;
/*     */     public boolean flat = false;
/*     */     public boolean percentage = false;
/*     */     public boolean maxResist = false;
/*  24 */     public SelectionCriteria.ComboType combo = SelectionCriteria.ComboType.AND;
/*     */     
/*     */     public StatInfo(SelectionCriteria criteria, int statType) {
/*  27 */       this.statTypes = SelectionCriteria.determineDamageTypes(criteria.dmgClassInfo, criteria.fieldInfo, statType);
/*  28 */       this.flat = criteria.fieldInfo.flat;
/*  29 */       this.percentage = criteria.fieldInfo.percentage;
/*  30 */       this.maxResist = criteria.fieldInfo.maxResist;
/*  31 */       this.combo = SelectionCriteria.ComboType.AND;
/*     */       
/*  33 */       boolean flagsFilled = (criteria.fieldInfo.flat || criteria.fieldInfo.percentage || criteria.fieldInfo.maxResist);
/*     */       
/*  35 */       if (!flagsFilled) {
/*  36 */         this.flat = true;
/*  37 */         this.percentage = true;
/*     */       } 
/*     */     }
/*     */     
/*     */     public StatInfo(String statType, boolean flat, boolean percentage) {
/*  42 */       this(statType, flat, percentage, SelectionCriteria.ComboType.AND);
/*     */     }
/*     */     
/*     */     public StatInfo(String statType, boolean flat, boolean percentage, SelectionCriteria.ComboType combo) {
/*  46 */       this.statTypes = new LinkedList<>();
/*  47 */       this.statTypes.add(statType);
/*     */       
/*  49 */       this.flat = flat;
/*  50 */       this.percentage = percentage;
/*  51 */       this.maxResist = false;
/*  52 */       this.combo = combo;
/*     */       
/*  54 */       boolean flagsFilled = (flat || percentage);
/*     */       
/*  56 */       if (!flagsFilled) {
/*  57 */         flat = true;
/*  58 */         percentage = true;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class DamageClassInfo {
/*     */     public boolean offense = false;
/*     */     public boolean defense = false;
/*     */     public boolean retaliation = false;
/*     */   }
/*     */   
/*     */   public static class FieldInfo {
/*     */     public boolean flat = false;
/*     */     public boolean percentage = false;
/*     */     public boolean maxResist = false;
/*     */   }
/*     */   
/*  75 */   public enum SelectionMode { ITEM, COMPONENT, AFFIX; }
/*  76 */   public enum CombinationMode { AND, OR; }
/*  77 */   public enum SoulboundMode { ALL, NONBOUND, SOULBOUND; }
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
/*     */   public SelectionCriteria() {
/* 143 */     this.selMode = SelectionMode.ITEM;
/* 144 */     this.combiMode = CombinationMode.OR;
/*     */     
/* 146 */     this.itemIDs = null;
/* 147 */     this.itemClass = new LinkedList<>();
/* 148 */     this.armorClass = new LinkedList<>();
/* 149 */     this.artifactClass = new LinkedList<>();
/* 150 */     this.itemRarity = new LinkedList<>();
/* 151 */     this.itemStyle = new LinkedList<>();
/*     */     
/* 153 */     this.itemName = null;
/* 154 */     this.setItem = false;
/* 155 */     this.bonusSkill = null;
/* 156 */     this.allSkills = false;
/* 157 */     this.masteryBonusSkills = false;
/* 158 */     this.modifiedSkill = null;
/* 159 */     this.masteryModifySkills = false;
/* 160 */     this.allItemSkills = false;
/* 161 */     this.itemSkill = null;
/* 162 */     this.prefixID = null;
/* 163 */     this.suffixID = null;
/* 164 */     this.levelMin = -1;
/* 165 */     this.levelMax = -1;
/* 166 */     this.cunningMax = -1;
/* 167 */     this.physiqueMax = -1;
/* 168 */     this.spiritMax = -1;
/*     */     
/* 170 */     this.noEnemyOnly = true;
/*     */     
/* 172 */     this.dmgClassInfo = new DamageClassInfo();
/* 173 */     this.fieldInfo = new FieldInfo();
/* 174 */     this.statInfos = new LinkedList<>();
/*     */     
/* 176 */     this.slotAxe1H = false;
/* 177 */     this.slotAxe2H = false;
/* 178 */     this.slotDagger1H = false;
/* 179 */     this.slotMace1H = false;
/* 180 */     this.slotMace2H = false;
/* 181 */     this.slotScepter1H = false;
/* 182 */     this.slotSword1H = false;
/* 183 */     this.slotSword2H = false;
/* 184 */     this.slotRanged1H = false;
/* 185 */     this.slotRanged2H = false;
/* 186 */     this.slotShield = false;
/* 187 */     this.slotOffhand = false;
/* 188 */     this.slotAmulet = false;
/* 189 */     this.slotBelt = false;
/* 190 */     this.slotMedal = false;
/* 191 */     this.slotRing = false;
/* 192 */     this.slotHead = false;
/* 193 */     this.slotShoulders = false;
/* 194 */     this.slotChest = false;
/* 195 */     this.slotHands = false;
/* 196 */     this.slotLegs = false;
/* 197 */     this.slotFeet = false;
/*     */     
/* 199 */     this.petBonus = false;
/*     */   }
/*     */   
/*     */   public void checkCriteria(GDLog log) {
/* 203 */     if (this.selMode != SelectionMode.AFFIX && (
/* 204 */       this.itemIDs == null || this.itemIDs.isEmpty()) && (this.itemClass == null || this.itemClass
/* 205 */       .isEmpty())) {
/* 206 */       log.addError(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_SEL_ITEM_TYPE"));
/*     */     }
/*     */ 
/*     */     
/* 210 */     if ((this.dmgClassInfo.defense || this.dmgClassInfo.offense || this.dmgClassInfo.retaliation) && (this.statInfos == null || this.statInfos
/* 211 */       .isEmpty()) && 
/* 212 */       !this.petBonus) {
/* 213 */       log.addError(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_SEL_DMG_COMBI"));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInitial() {
/* 219 */     if (this.itemIDs != null && !this.itemIDs.isEmpty()) return false; 
/* 220 */     if (this.itemClass != null && !this.itemClass.isEmpty()) return false; 
/* 221 */     if (this.armorClass != null && !this.armorClass.isEmpty()) return false; 
/* 222 */     if (this.artifactClass != null && !this.artifactClass.isEmpty()) return false; 
/* 223 */     if (this.itemRarity != null && !this.itemRarity.isEmpty()) return false; 
/* 224 */     if (this.itemStyle != null && !this.itemStyle.isEmpty()) return false;
/*     */     
/* 226 */     if (this.itemName != null) return false; 
/* 227 */     if (this.setItem) return false; 
/* 228 */     if (this.bonusSkill != null) return false; 
/* 229 */     if (this.allSkills) return false; 
/* 230 */     if (this.masteryBonusSkills) return false; 
/* 231 */     if (this.modifiedSkill != null) return false; 
/* 232 */     if (this.masteryModifySkills) return false; 
/* 233 */     if (this.allItemSkills) return false; 
/* 234 */     if (this.itemSkill != null) return false; 
/* 235 */     if (this.prefixID != null) return false; 
/* 236 */     if (this.suffixID != null) return false; 
/* 237 */     if (this.levelMin > -1) return false; 
/* 238 */     if (this.levelMax > -1) return false; 
/* 239 */     if (this.cunningMax > -1) return false; 
/* 240 */     if (this.physiqueMax > -1) return false; 
/* 241 */     if (this.spiritMax > -1) return false; 
/* 242 */     if (this.dmgConversionFrom != null) return false; 
/* 243 */     if (this.dmgConversionTo != null) return false;
/*     */     
/* 245 */     if (this.statInfos != null && !this.statInfos.isEmpty()) return false;
/*     */     
/* 247 */     if (this.slotAxe1H) return false; 
/* 248 */     if (this.slotAxe2H) return false; 
/* 249 */     if (this.slotDagger1H) return false; 
/* 250 */     if (this.slotMace1H) return false; 
/* 251 */     if (this.slotMace2H) return false; 
/* 252 */     if (this.slotScepter1H) return false; 
/* 253 */     if (this.slotSpear1H) return false; 
/* 254 */     if (this.slotStaff2H) return false; 
/* 255 */     if (this.slotSword1H) return false; 
/* 256 */     if (this.slotSword2H) return false; 
/* 257 */     if (this.slotRanged1H) return false; 
/* 258 */     if (this.slotShield) return false; 
/* 259 */     if (this.slotOffhand) return false; 
/* 260 */     if (this.slotAmulet) return false; 
/* 261 */     if (this.slotBelt) return false; 
/* 262 */     if (this.slotMedal) return false; 
/* 263 */     if (this.slotRing) return false; 
/* 264 */     if (this.slotHead) return false; 
/* 265 */     if (this.slotShoulders) return false; 
/* 266 */     if (this.slotChest) return false; 
/* 267 */     if (this.slotHands) return false; 
/* 268 */     if (this.slotLegs) return false; 
/* 269 */     if (this.slotFeet) return false;
/*     */     
/* 271 */     if (this.petBonus) return false;
/*     */     
/* 273 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isDamageClassUsed() {
/* 277 */     return (this.dmgClassInfo.defense || this.dmgClassInfo.offense || this.dmgClassInfo.retaliation);
/*     */   }
/*     */   
/*     */   public boolean isStatTypeUsed() {
/* 281 */     return !this.statInfos.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean isSkillBonusUsed() {
/* 285 */     return (this.bonusSkill != null);
/*     */   }
/*     */   
/*     */   public boolean isAllSkillsUsed() {
/* 289 */     return this.allSkills;
/*     */   }
/*     */   
/*     */   public boolean isMasteryBonusSkillsUsed() {
/* 293 */     return this.masteryBonusSkills;
/*     */   }
/*     */   
/*     */   public boolean isSkillModifierUsed() {
/* 297 */     return (this.modifiedSkill != null);
/*     */   }
/*     */   
/*     */   public boolean isMasteryModifySkillsUsed() {
/* 301 */     return this.masteryModifySkills;
/*     */   }
/*     */   
/*     */   public boolean isAllItemSkillsUsed() {
/* 305 */     return this.allItemSkills;
/*     */   }
/*     */   
/*     */   public boolean isItemSkillsUsed() {
/* 309 */     return (this.itemSkill != null);
/*     */   }
/*     */   
/*     */   public boolean isPrefixUsed() {
/* 313 */     return (this.prefixID != null);
/*     */   }
/*     */   
/*     */   public boolean isSuffixUsed() {
/* 317 */     return (this.suffixID != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<String> determineDamageTypes(DamageClassInfo dmgClassInfo, FieldInfo fieldInfo, int statType) {
/* 325 */     List<String> list = new LinkedList<>();
/*     */     
/* 327 */     if (statType == 301) {
/* 328 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 331 */         list.add("offensiveBasePoison");
/* 332 */         list.add("offensivePoison");
/*     */       } else {
/* 334 */         if (dmgClassInfo.defense) {
/* 335 */           list.add("defensivePoison");
/*     */           
/* 337 */           if (fieldInfo.maxResist && 
/* 338 */             !list.contains("defensiveAll")) list.add("defensiveAll");
/*     */         
/*     */         } 
/* 341 */         if (dmgClassInfo.offense) {
/* 342 */           list.add("offensiveBasePoison");
/* 343 */           list.add("offensivePoison");
/*     */         } 
/* 345 */         if (dmgClassInfo.retaliation) {
/* 346 */           list.add("retaliationPoison");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 351 */     if (statType == 302) {
/* 352 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 355 */         list.add("offensiveBaseAether");
/* 356 */         list.add("offensiveAether");
/*     */       } else {
/* 358 */         if (dmgClassInfo.defense) {
/* 359 */           list.add("defensiveAether");
/*     */           
/* 361 */           if (fieldInfo.maxResist && 
/* 362 */             !list.contains("defensiveAll")) list.add("defensiveAll");
/*     */         
/*     */         } 
/* 365 */         if (dmgClassInfo.offense) {
/* 366 */           list.add("offensiveBaseAether");
/* 367 */           list.add("offensiveAether");
/*     */         } 
/* 369 */         if (dmgClassInfo.retaliation) {
/* 370 */           list.add("retaliationAether");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 375 */     if (statType == 303) {
/* 376 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 379 */         list.add("offensiveBaseChaos");
/* 380 */         list.add("offensiveChaos");
/*     */       } else {
/* 382 */         if (dmgClassInfo.defense) {
/* 383 */           list.add("defensiveChaos");
/*     */           
/* 385 */           if (fieldInfo.maxResist && 
/* 386 */             !list.contains("defensiveAll")) list.add("defensiveAll");
/*     */         
/*     */         } 
/* 389 */         if (dmgClassInfo.offense) {
/* 390 */           list.add("offensiveBaseChaos");
/* 391 */           list.add("offensiveChaos");
/*     */         } 
/* 393 */         if (dmgClassInfo.retaliation) {
/* 394 */           list.add("retaliationChaos");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 399 */     if (statType == 304) {
/* 400 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 403 */         list.add("offensiveBaseCold");
/* 404 */         list.add("offensiveCold");
/*     */       } else {
/* 406 */         if (dmgClassInfo.defense) {
/* 407 */           list.add("defensiveCold");
/*     */           
/* 409 */           if (fieldInfo.maxResist && 
/* 410 */             !list.contains("defensiveAll")) list.add("defensiveAll");
/*     */         
/*     */         } 
/* 413 */         if (dmgClassInfo.offense) {
/* 414 */           list.add("offensiveBaseCold");
/* 415 */           list.add("offensiveCold");
/*     */         } 
/* 417 */         if (dmgClassInfo.retaliation) {
/* 418 */           list.add("retaliationCold");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 423 */     if (statType == 305) {
/* 424 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 427 */         list.add("offensiveElemental");
/*     */       } else {
/* 429 */         if (dmgClassInfo.defense) {
/* 430 */           list.add("defensiveElemental");
/* 431 */           list.add("defensiveElementalResistance");
/*     */           
/* 433 */           if (fieldInfo.maxResist && 
/* 434 */             !list.contains("defensiveAll")) list.add("defensiveAll");
/*     */         
/*     */         } 
/* 437 */         if (dmgClassInfo.offense) {
/* 438 */           list.add("offensiveElemental");
/*     */         }
/* 440 */         if (dmgClassInfo.retaliation) {
/* 441 */           list.add("retaliationElemental");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 446 */     if (statType == 306) {
/* 447 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 450 */         list.add("offensiveBaseFire");
/* 451 */         list.add("offensiveFire");
/*     */       } else {
/* 453 */         if (dmgClassInfo.defense) {
/* 454 */           list.add("defensiveFire");
/*     */           
/* 456 */           if (fieldInfo.maxResist && 
/* 457 */             !list.contains("defensiveAll")) list.add("defensiveAll");
/*     */         
/*     */         } 
/* 460 */         if (dmgClassInfo.offense) {
/* 461 */           list.add("offensiveBaseFire");
/* 462 */           list.add("offensiveFire");
/*     */         } 
/* 464 */         if (dmgClassInfo.retaliation) {
/* 465 */           list.add("retaliationFire");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 470 */     if (statType == 307) {
/* 471 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 474 */         list.add("offensiveBaseLife");
/* 475 */         list.add("offensiveLife");
/*     */       } else {
/* 477 */         if (dmgClassInfo.defense) {
/* 478 */           list.add("defensiveLife");
/*     */           
/* 480 */           if (fieldInfo.maxResist && 
/* 481 */             !list.contains("defensiveAll")) list.add("defensiveAll");
/*     */         
/*     */         } 
/* 484 */         if (dmgClassInfo.offense) {
/* 485 */           list.add("offensiveBaseLife");
/* 486 */           list.add("offensiveLife");
/*     */         } 
/* 488 */         if (dmgClassInfo.retaliation) {
/* 489 */           list.add("retaliationLife");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 494 */     if (statType == 308) {
/* 495 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 498 */         list.add("offensiveBaseLightning");
/* 499 */         list.add("offensiveLightning");
/*     */       } else {
/* 501 */         if (dmgClassInfo.defense) {
/* 502 */           list.add("defensiveLightning");
/*     */           
/* 504 */           if (fieldInfo.maxResist && 
/* 505 */             !list.contains("defensiveAll")) list.add("defensiveAll");
/*     */         
/*     */         } 
/* 508 */         if (dmgClassInfo.offense) {
/* 509 */           list.add("offensiveBaseLightning");
/* 510 */           list.add("offensiveLightning");
/*     */         } 
/* 512 */         if (dmgClassInfo.retaliation) {
/* 513 */           list.add("retaliationLightning");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 518 */     if (statType == 309) {
/* 519 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 522 */         list.add("offensivePhysical");
/* 523 */         list.add("offensiveBonusPhysical");
/*     */       } else {
/* 525 */         if (dmgClassInfo.defense) {
/* 526 */           list.add("defensivePhysical");
/*     */           
/* 528 */           if (fieldInfo.maxResist && 
/* 529 */             !list.contains("defensiveAll")) list.add("defensiveAll");
/*     */         
/*     */         } 
/* 532 */         if (dmgClassInfo.offense) {
/* 533 */           list.add("offensivePhysical");
/* 534 */           list.add("offensiveBonusPhysical");
/*     */         } 
/* 536 */         if (dmgClassInfo.retaliation) {
/* 537 */           list.add("retaliationPhysical");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 542 */     if (statType == 310) {
/* 543 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 546 */         list.add("offensivePierce");
/*     */       } else {
/* 548 */         if (dmgClassInfo.defense) {
/* 549 */           list.add("defensivePierce");
/*     */           
/* 551 */           if (fieldInfo.maxResist && 
/* 552 */             !list.contains("defensiveAll")) list.add("defensiveAll");
/*     */         
/*     */         } 
/* 555 */         if (dmgClassInfo.offense) {
/* 556 */           list.add("offensivePierce");
/*     */         }
/* 558 */         if (dmgClassInfo.retaliation) {
/* 559 */           list.add("retaliationPierce");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 564 */     if (statType == 401) {
/* 565 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 568 */         list.add("offensiveSlowPoison");
/*     */       } else {
/* 570 */         if (dmgClassInfo.defense) {
/* 571 */           if (!list.contains("defensivePoison")) list.add("defensivePoison");
/*     */           
/* 573 */           if (fieldInfo.maxResist && 
/* 574 */             !list.contains("defensiveAll")) list.add("defensiveAll");
/*     */         
/*     */         } 
/* 577 */         if (dmgClassInfo.offense) {
/* 578 */           list.add("offensiveSlowPoison");
/*     */         }
/* 580 */         if (dmgClassInfo.retaliation) {
/* 581 */           list.add("retaliationSlowPoison");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 586 */     if (statType == 404) {
/* 587 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 590 */         list.add("offensiveSlowCold");
/*     */       } else {
/* 592 */         if (dmgClassInfo.defense) {
/* 593 */           if (!list.contains("defensiveCold")) list.add("defensiveCold");
/*     */           
/* 595 */           if (fieldInfo.maxResist && 
/* 596 */             !list.contains("defensiveAll")) list.add("defensiveAll");
/*     */         
/*     */         } 
/* 599 */         if (dmgClassInfo.offense) {
/* 600 */           list.add("offensiveSlowCold");
/*     */         }
/* 602 */         if (dmgClassInfo.retaliation) {
/* 603 */           list.add("retaliationSlowCold");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 608 */     if (statType == 406) {
/* 609 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 612 */         list.add("offensiveSlowFire");
/*     */       } else {
/* 614 */         if (dmgClassInfo.defense) {
/* 615 */           if (!list.contains("defensiveFire")) list.add("defensiveFire");
/*     */           
/* 617 */           if (fieldInfo.maxResist && 
/* 618 */             !list.contains("defensiveAll")) list.add("defensiveAll");
/*     */         
/*     */         } 
/* 621 */         if (dmgClassInfo.offense) {
/* 622 */           list.add("offensiveSlowFire");
/*     */         }
/* 624 */         if (dmgClassInfo.retaliation) {
/* 625 */           list.add("retaliationSlowFire");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 630 */     if (statType == 407) {
/* 631 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 634 */         list.add("offensiveSlowLife");
/*     */       } else {
/* 636 */         if (dmgClassInfo.defense) {
/* 637 */           if (!list.contains("defensiveLife")) list.add("defensiveLife");
/*     */           
/* 639 */           if (fieldInfo.maxResist && 
/* 640 */             !list.contains("defensiveAll")) list.add("defensiveAll");
/*     */         
/*     */         } 
/* 643 */         if (dmgClassInfo.offense) {
/* 644 */           list.add("offensiveSlowLife");
/*     */         }
/* 646 */         if (dmgClassInfo.retaliation) {
/* 647 */           list.add("retaliationSlowLife");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 652 */     if (statType == 408) {
/* 653 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 656 */         list.add("offensiveSlowLightning");
/*     */       } else {
/* 658 */         if (dmgClassInfo.defense) {
/* 659 */           if (!list.contains("defensiveLightning")) list.add("defensiveLightning");
/*     */           
/* 661 */           if (fieldInfo.maxResist && 
/* 662 */             !list.contains("defensiveAll")) list.add("defensiveAll");
/*     */         
/*     */         } 
/* 665 */         if (dmgClassInfo.offense) {
/* 666 */           list.add("offensiveSlowLightning");
/*     */         }
/* 668 */         if (dmgClassInfo.retaliation) {
/* 669 */           list.add("retaliationSlowLightning");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 674 */     if (statType == 409) {
/* 675 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 678 */         list.add("offensiveSlowPhysical");
/*     */       } else {
/* 680 */         if (dmgClassInfo.defense) {
/* 681 */           if (!list.contains("defensivePhysical")) list.add("defensivePhysical");
/*     */           
/* 683 */           if (fieldInfo.maxResist && 
/* 684 */             !list.contains("defensiveAll")) list.add("defensiveAll");
/*     */         
/*     */         } 
/* 687 */         if (dmgClassInfo.offense) {
/* 688 */           list.add("offensiveSlowPhysical");
/*     */         }
/* 690 */         if (dmgClassInfo.retaliation) {
/* 691 */           list.add("retaliationSlowPhysical");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 696 */     if (statType == 410) {
/* 697 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 700 */         list.add("offensiveSlowBleeding");
/*     */       } else {
/* 702 */         if (dmgClassInfo.defense) {
/* 703 */           list.add("defensiveBleeding");
/*     */           
/* 705 */           if (fieldInfo.maxResist && 
/* 706 */             !list.contains("defensiveAll")) list.add("defensiveAll");
/*     */         
/*     */         } 
/* 709 */         if (dmgClassInfo.offense) {
/* 710 */           list.add("offensiveSlowBleeding");
/*     */         }
/* 712 */         if (dmgClassInfo.retaliation) {
/* 713 */           list.add("retaliationSlowBleeding");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 718 */     if (statType == 501) {
/* 719 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 722 */         list.add("offensiveTotalDamage");
/*     */       } else {
/* 724 */         if (dmgClassInfo.defense && 
/* 725 */           !list.contains("defensiveAll")) list.add("defensiveAll");
/*     */         
/* 727 */         if (dmgClassInfo.offense) {
/* 728 */           list.add("offensiveTotalDamage");
/*     */         }
/* 730 */         if (dmgClassInfo.retaliation) {
/* 731 */           list.add("retaliationTotalDamage");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 736 */     if (statType == 502)
/*     */     {
/* 738 */       list.add("offensiveCritDamage");
/*     */     }
/*     */ 
/*     */     
/* 742 */     if (statType == 503) {
/* 743 */       if (dmgClassInfo.defense) {
/* 744 */         list.add("defensiveSlowLifeLeach");
/*     */       }
/* 746 */       if (dmgClassInfo.offense) {
/* 747 */         list.add("offensiveLifeLeech");
/* 748 */         list.add("offensiveSlowLifeLeach");
/*     */       } 
/* 750 */       if (dmgClassInfo.retaliation) {
/* 751 */         list.add("retaliationSlowLifeLeach");
/*     */       }
/*     */     } 
/*     */     
/* 755 */     if (statType == 504)
/*     */     {
/* 757 */       if (!list.contains("defensiveReflect")) list.add("defensiveReflect");
/*     */     
/*     */     }
/*     */     
/* 761 */     if (statType == 505) {
/* 762 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 765 */         if (!list.contains("offensiveSlowAttackSpeed")) list.add("offensiveSlowAttackSpeed"); 
/*     */       } else {
/* 767 */         if ((dmgClassInfo.defense || dmgClassInfo.offense) && 
/* 768 */           !list.contains("offensiveSlowAttackSpeed")) list.add("offensiveSlowAttackSpeed");
/*     */         
/* 770 */         if (dmgClassInfo.retaliation) {
/* 771 */           list.add("retaliationSlowAttackSpeed");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 776 */     if (statType == 506)
/*     */     {
/* 778 */       if (!list.contains("offensiveSlowTotalSpeed")) list.add("offensiveSlowTotalSpeed");
/*     */     
/*     */     }
/*     */     
/* 782 */     if (statType == 507) {
/* 783 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 786 */         if (!list.contains("offensiveSlowDefensiveAbility")) list.add("offensiveSlowDefensiveAbility"); 
/*     */       } else {
/* 788 */         if ((dmgClassInfo.defense || dmgClassInfo.offense) && 
/* 789 */           !list.contains("offensiveSlowDefensiveAbility")) list.add("offensiveSlowDefensiveAbility");
/*     */         
/* 791 */         if (dmgClassInfo.retaliation) {
/* 792 */           list.add("retaliationSlowDefensiveAbility");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 797 */     if (statType == 508) {
/* 798 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 801 */         if (!list.contains("offensiveSlowOffensiveAbility")) list.add("offensiveSlowOffensiveAbility"); 
/*     */       } else {
/* 803 */         if ((dmgClassInfo.defense || dmgClassInfo.offense) && 
/* 804 */           !list.contains("offensiveSlowOffensiveAbility")) list.add("offensiveSlowOffensiveAbility");
/*     */         
/* 806 */         if (dmgClassInfo.retaliation) {
/* 807 */           list.add("retaliationSlowOffensiveAbility");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 812 */     if (statType == 509)
/*     */     {
/* 814 */       if (!list.contains("piercingProjectile")) list.add("piercingProjectile");
/*     */     
/*     */     }
/*     */     
/* 818 */     if (statType == 510)
/*     */     {
/* 820 */       if (!list.contains("offensiveTotalResistanceReductionAbsolute")) list.add("offensiveTotalResistanceReductionAbsolute");
/*     */     
/*     */     }
/*     */     
/* 824 */     if (statType == 511)
/*     */     {
/* 826 */       if (!list.contains("offensiveTotalResistanceReductionPercent")) list.add("offensiveTotalResistanceReductionPercent");
/*     */     
/*     */     }
/*     */     
/* 830 */     if (statType == 512)
/*     */     {
/* 832 */       if (!list.contains("offensivePhysicalResistanceReductionAbsolute")) list.add("offensivePhysicalResistanceReductionAbsolute");
/*     */     
/*     */     }
/*     */     
/* 836 */     if (statType == 513)
/*     */     {
/* 838 */       if (!list.contains("offensivePhysicalResistanceReductionPercent")) list.add("offensivePhysicalResistanceReductionPercent");
/*     */     
/*     */     }
/*     */     
/* 842 */     if (statType == 514)
/*     */     {
/* 844 */       if (!list.contains("offensiveElementalResistanceReductionAbsolute")) list.add("offensiveElementalResistanceReductionAbsolute");
/*     */     
/*     */     }
/*     */     
/* 848 */     if (statType == 515)
/*     */     {
/* 850 */       if (!list.contains("offensiveElementalResistanceReductionPercent")) list.add("offensiveElementalResistanceReductionPercent");
/*     */     
/*     */     }
/*     */     
/* 854 */     if (statType == 516)
/*     */     {
/* 856 */       if (!list.contains("defensivePercentReflectionResistance")) list.add("defensivePercentReflectionResistance");
/*     */     
/*     */     }
/*     */     
/* 860 */     if (statType == 517)
/*     */     {
/* 862 */       if (!list.contains("skillManaCostReduction")) list.add("skillManaCostReduction");
/*     */     
/*     */     }
/*     */     
/* 866 */     if (statType == 601) {
/* 867 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 870 */         list.add("offensiveStun");
/*     */       } else {
/* 872 */         if (dmgClassInfo.defense) {
/* 873 */           list.add("defensiveStun");
/*     */         }
/* 875 */         if (dmgClassInfo.offense) {
/* 876 */           list.add("offensiveStun");
/*     */         }
/* 878 */         if (dmgClassInfo.retaliation) {
/* 879 */           list.add("retaliationStun");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 884 */     if (statType == 602)
/*     */     {
/* 886 */       list.add("defensiveTotalSpeedResistance");
/*     */     }
/*     */ 
/*     */     
/* 890 */     if (statType == 603) {
/* 891 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 894 */         list.add("offensiveTrap");
/*     */       } else {
/* 896 */         if (dmgClassInfo.defense) {
/* 897 */           list.add("defensiveTrap");
/*     */         }
/* 899 */         if (dmgClassInfo.offense) {
/* 900 */           list.add("offensiveTrap");
/*     */         }
/* 902 */         if (dmgClassInfo.retaliation) {
/* 903 */           list.add("retaliationTrap");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 908 */     if (statType == 604) {
/* 909 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 912 */         list.add("offensivePetrify");
/*     */       } else {
/* 914 */         if (dmgClassInfo.defense) {
/* 915 */           list.add("defensivePetrify");
/*     */         }
/* 917 */         if (dmgClassInfo.offense) {
/* 918 */           list.add("offensivePetrify");
/*     */         }
/* 920 */         if (dmgClassInfo.retaliation) {
/* 921 */           list.add("retaliationPetrify");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 926 */     if (statType == 605) {
/* 927 */       if (!dmgClassInfo.defense && !dmgClassInfo.offense && !dmgClassInfo.retaliation) {
/*     */ 
/*     */         
/* 930 */         list.add("offensiveFreeze");
/*     */       } else {
/* 932 */         if (dmgClassInfo.defense) {
/* 933 */           list.add("defensiveFreeze");
/*     */         }
/* 935 */         if (dmgClassInfo.offense) {
/* 936 */           list.add("offensiveFreeze");
/*     */         }
/* 938 */         if (dmgClassInfo.retaliation) {
/* 939 */           list.add("retaliationFreeze");
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 944 */     return list;
/*     */   }
/*     */   
/*     */   public static void addStatInfos(SelectionCriteria criteria, int statType) {
/* 948 */     StatInfo info = new StatInfo(criteria, statType);
/* 949 */     criteria.statInfos.add(info);
/*     */   } }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\SelectionCriteria.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */