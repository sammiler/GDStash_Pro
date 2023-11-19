/*     */ package org.gdstash.description;
/*     */ 
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.db.DBSkill;
/*     */ import org.gdstash.db.DBSkillBonus;
/*     */ import org.gdstash.db.DBSkillModifier;
/*     */ import org.gdstash.db.DBStashItem;
/*     */ import org.gdstash.db.DBStat;
/*     */ import org.gdstash.db.DBStatBonusRace;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ import org.gdstash.util.GDMsgLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BonusDetail
/*     */ {
/*     */   public static final int TYPE_STAT = 1;
/*     */   public static final int TYPE_SKILLBONUS = 2;
/*     */   public static final int TYPE_SKILL = 3;
/*     */   public static final int TYPE_SKILLMODIFIER = 4;
/*     */   public Object entity;
/*     */   public int type;
/*     */   public List<BonusInfo> infos;
/*     */   
/*     */   public BonusDetail(DBStashItem item, int detailType, DBStat stat, String prefix) {
/*  33 */     this.entity = stat;
/*  34 */     this.type = 1;
/*  35 */     this.infos = createStatInfos(item, detailType, stat, prefix);
/*     */   }
/*     */   
/*     */   public BonusDetail(DBStashItem item, int detailType, DBStat stat, DBSkillModifier skillModifier) {
/*  39 */     this.entity = stat;
/*  40 */     this.type = 1;
/*  41 */     this.infos = createStatInfos(item, detailType, stat, skillModifier);
/*     */   }
/*     */   
/*     */   public BonusDetail(DBSkillBonus skillBonus) {
/*  45 */     this.entity = skillBonus;
/*  46 */     this.type = 2;
/*  47 */     this.infos = new LinkedList<>();
/*     */     
/*  49 */     createSkillBonusInfos(null);
/*     */   }
/*     */   
/*     */   public BonusDetail(DBStashItem item, DBSkillBonus skillBonus) {
/*  53 */     this.entity = skillBonus;
/*  54 */     this.type = 2;
/*  55 */     this.infos = new LinkedList<>();
/*     */     
/*  57 */     createSkillBonusInfos(item);
/*     */   }
/*     */   
/*     */   public BonusDetail(DBSkill skill) {
/*  61 */     this.entity = skill;
/*  62 */     this.type = 3;
/*  63 */     this.infos = createSkillInfos(skill);
/*     */   }
/*     */   
/*     */   public BonusDetail(DBSkill skill, DBStat stat) {
/*  67 */     this.entity = skill;
/*  68 */     this.type = 3;
/*  69 */     this.infos = createSkillInfos(skill, stat);
/*     */   }
/*     */   
/*     */   public BonusDetail(DBSkill skill, DBStat stat, String prefix) {
/*  73 */     this.entity = skill;
/*  74 */     this.type = 3;
/*  75 */     this.infos = createSkillInfos(skill, stat, prefix);
/*     */   }
/*     */   
/*     */   public BonusDetail(DBSkillModifier skillModifier) {
/*  79 */     this.entity = skillModifier;
/*  80 */     this.type = 4;
/*  81 */     this.infos = new LinkedList<>();
/*     */     
/*  83 */     createSkillModifierInfos();
/*     */   }
/*     */   
/*     */   private void add(BonusInfo info) {
/*  87 */     if (info == null) {
/*     */       return;
/*     */     }
/*     */     
/*  91 */     if (info.text != null) {
/*  92 */       this.infos.add(info);
/*     */     }
/*     */   }
/*     */   
/*     */   public static List<BonusInfo> createStatInfos(DBStashItem item, int detailType, DBStat stat, String prefix) {
/*  97 */     if (stat.getStatType() == null) {
/*  98 */       return null;
/*     */     }
/*     */     
/* 101 */     TagInfo info = TagInfoHashMap.getInfo(stat.getStatType());
/*     */     
/* 103 */     if (info == null) {
/* 104 */       return null;
/*     */     }
/*     */     
/* 107 */     if (info.getTag() != null) {
/* 108 */       return parseStat(item, detailType, stat, prefix, null, info, false);
/*     */     }
/*     */     
/* 111 */     return null;
/*     */   }
/*     */   
/*     */   public static List<BonusInfo> createStatInfos(DBStashItem item, int detailType, DBStat stat, DBSkillModifier skillModifier) {
/* 115 */     if (stat.getStatType() == null) {
/* 116 */       return null;
/*     */     }
/*     */     
/* 119 */     TagInfo info = TagInfoHashMap.getInfo(stat.getStatType());
/*     */     
/* 121 */     if (info == null) {
/* 122 */       return null;
/*     */     }
/*     */     
/* 125 */     if (info.getTag() != null) {
/* 126 */       return parseStat(item, detailType, stat, null, skillModifier, info, false);
/*     */     }
/*     */     
/* 129 */     return null;
/*     */   }
/*     */   
/*     */   public static List<BonusInfo> createStatInfos(List<DBStat> stats, String prefix) {
/* 133 */     List<BonusInfo> infos = new LinkedList<>();
/*     */     
/* 135 */     if (stats == null) return null;
/*     */     
/* 137 */     for (DBStat stat : stats) {
/* 138 */       if (stat.getStatType() == null)
/*     */         continue; 
/* 140 */       TagInfo info = TagInfoHashMap.getInfo(stat.getStatType());
/*     */       
/* 142 */       if (info == null)
/*     */         continue; 
/* 144 */       if (info.getTag() != null) {
/* 145 */         List<BonusInfo> temp = parseStat(null, 1, stat, prefix, null, info, true);
/*     */         
/* 147 */         infos.addAll(temp);
/*     */       } 
/*     */     } 
/*     */     
/* 151 */     return infos;
/*     */   }
/*     */   
/*     */   private static String buildText(String tag, List<Object> lArgs) {
/* 155 */     Object[] args = new Object[lArgs.size()];
/* 156 */     int i = 0;
/*     */     
/* 158 */     for (Object o : lArgs) {
/* 159 */       args[i] = o;
/*     */       
/* 161 */       i++;
/*     */     } 
/*     */     
/* 164 */     String text = GDMsgFormatter.format(GDMsgFormatter.rbGD, tag, args);
/*     */     
/* 166 */     return text;
/*     */   }
/*     */   
/*     */   private static int adjustValue(DBStashItem item, int detailType, int value) {
/* 170 */     if (item == null) return value;
/*     */     
/* 172 */     int result = value;
/*     */     
/* 174 */     if (item == null) return result;
/*     */     
/* 176 */     if (detailType == 1 && item.isComponent()) {
/* 177 */       int pieces = item.getComponentPieces();
/* 178 */       int var1 = item.getVar1();
/* 179 */       if (var1 == 0) var1 = 1;
/*     */       
/* 181 */       if (var1 < pieces) {
/* 182 */         float f = result;
/* 183 */         f = (float)((f * var1 / pieces) + 0.5D);
/*     */         
/* 185 */         result = (int)f;
/*     */         
/* 187 */         if (result < 1) result = 1;
/*     */       
/*     */       } 
/*     */     } 
/* 191 */     if (detailType == 2 && item.getDBComponent() != null) {
/* 192 */       int pieces = item.getComponentPieces();
/* 193 */       int var1 = item.getVar1();
/* 194 */       if (var1 == 0) var1 = 1;
/*     */       
/* 196 */       if (var1 < pieces) {
/* 197 */         float f = result;
/* 198 */         f = (float)((f * var1 / pieces) + 0.5D);
/*     */         
/* 200 */         result = (int)f;
/*     */         
/* 202 */         if (result < 1) result = 1;
/*     */       
/*     */       } 
/*     */     } 
/* 206 */     return result;
/*     */   }
/*     */   
/*     */   private static List<BonusInfo> fillRaceBonus(int detailType, DBStat stat, String prefix, TagInfo tagInfo, boolean blankStat) {
/* 210 */     List<BonusInfo> infos = new LinkedList<>();
/* 211 */     BonusInfo info = null;
/*     */     
/* 213 */     DBStatBonusRace br = stat.getBonusRace();
/*     */     
/* 215 */     if (br == null) return infos;
/*     */     
/* 217 */     Object[] args = { Float.valueOf(stat.getStatMin()), br.getRaceName() };
/*     */     
/* 219 */     String statDesc = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_UNI_RACE_DAMAGE", args);
/*     */     
/* 221 */     info = new BonusInfo(tagInfo);
/* 222 */     info.prefix = prefix;
/*     */ 
/*     */     
/* 225 */     info.dmgPerc = true;
/*     */     
/* 227 */     String tag = "TXT_UNI_RACE_DAMAGE";
/*     */ 
/*     */     
/* 230 */     info.text = statDesc;
/*     */     
/* 232 */     infos.add(info);
/*     */     
/* 234 */     return infos;
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
/*     */   private static List<BonusInfo> parseCharStat(DBStashItem item, int detailType, DBStat stat, String prefix, DBSkillModifier skillModifier, TagInfo tagInfo, boolean blankStat) {
/* 251 */     List<BonusInfo> infos = new LinkedList<>();
/*     */     
/* 253 */     String tag = null;
/* 254 */     List<Object> lArgs = null;
/* 255 */     BonusInfo info = null;
/*     */     
/* 257 */     String statDesc = GDMsgFormatter.getString(GDMsgFormatter.rbGD, tagInfo.getTag());
/*     */     
/* 259 */     if (prefix != null) {
/* 260 */       Object[] args = { prefix, statDesc };
/*     */       
/* 262 */       statDesc = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_STAT_PREFIX", args);
/*     */     } 
/*     */     
/* 265 */     if (statDesc.equals("")) {
/* 266 */       return infos;
/*     */     }
/*     */     
/* 269 */     if (stat.getStatMin() != 0.0F) {
/* 270 */       lArgs = new LinkedList();
/* 271 */       if (blankStat) {
/* 272 */         lArgs.add("");
/*     */       } else {
/* 274 */         lArgs.add(statDesc);
/*     */       } 
/* 276 */       if (tagInfo.isFloatValue()) {
/* 277 */         lArgs.add(Float.valueOf(stat.getStatMin()));
/*     */       } else {
/* 279 */         lArgs.add(Integer.valueOf((int)stat.getStatMin()));
/*     */       } 
/*     */       
/* 282 */       info = new BonusInfo(tagInfo);
/* 283 */       info.prefix = prefix;
/*     */ 
/*     */       
/* 286 */       info.dmgFlat = true;
/*     */       
/* 288 */       tag = "TXT_INT";
/* 289 */       if (tagInfo.isFloatValue()) tag = "TXT_FLOAT";
/*     */       
/* 291 */       if (tagInfo.isSignMandatory() || skillModifier != null)
/*     */       {
/* 293 */         if (stat.getStatMin() > 0.0F) tag = tag + "_PLUS";
/*     */       
/*     */       }
/* 296 */       if (tagInfo.isMinAsPerc()) tag = tag + "_PERC";
/*     */       
/* 298 */       if (stat.getStatChance() > 0) {
/* 299 */         tag = tag + "_CHANCE";
/*     */         
/* 301 */         lArgs.add(Integer.valueOf(stat.getStatChance()));
/*     */         
/* 303 */         info.dmgChance = true;
/*     */       } 
/*     */       
/* 306 */       info.statDesc = statDesc;
/* 307 */       info.text = buildText(tag, lArgs);
/*     */       
/* 309 */       if (skillModifier != null) {
/* 310 */         String skillname = skillModifier.getSkillName();
/*     */         
/* 312 */         if (skillModifier.getMasteryName() != null) {
/* 313 */           skillname = skillname + " (" + skillModifier.getMasteryName() + ")";
/*     */         }
/*     */         
/* 316 */         Object[] args = { info.text, skillname };
/* 317 */         info.text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_MODIFIER_TO_SKILL", args);
/*     */       } 
/*     */       
/* 320 */       infos.add(info);
/*     */     } 
/*     */     
/* 323 */     if (stat.getStatModifier() != 0) {
/* 324 */       lArgs = new LinkedList();
/* 325 */       if (blankStat) {
/* 326 */         lArgs.add("");
/*     */       } else {
/* 328 */         lArgs.add(statDesc);
/*     */       } 
/* 330 */       lArgs.add(Integer.valueOf(adjustValue(item, detailType, stat.getStatModifier())));
/*     */       
/* 332 */       info = new BonusInfo(tagInfo);
/* 333 */       info.prefix = prefix;
/*     */ 
/*     */       
/* 336 */       info.dmgPerc = true;
/*     */       
/* 338 */       tag = "TXT_PERC";
/*     */       
/* 340 */       if (tagInfo.isSignMandatory() || skillModifier != null)
/*     */       {
/* 342 */         if (stat.getStatMin() > 0.0F) tag = tag + "_PLUS";
/*     */       
/*     */       }
/* 345 */       if (stat.getStatChance() > 0) {
/* 346 */         tag = tag + "_CHANCE";
/*     */         
/* 348 */         lArgs.add(Integer.valueOf(stat.getStatChance()));
/*     */         
/* 350 */         info.dmgChance = true;
/*     */       } 
/*     */       
/* 353 */       info.statDesc = statDesc;
/* 354 */       info.text = buildText(tag, lArgs);
/*     */       
/* 356 */       if (skillModifier != null) {
/* 357 */         String skillname = skillModifier.getSkillName();
/*     */         
/* 359 */         if (skillModifier.getMasteryName() != null) {
/* 360 */           skillname = skillname + " (" + skillModifier.getMasteryName() + ")";
/*     */         }
/*     */         
/* 363 */         Object[] args = { info.text, skillname };
/* 364 */         info.text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_MODIFIER_TO_SKILL", args);
/*     */       } 
/*     */       
/* 367 */       infos.add(info);
/*     */     } 
/*     */     
/* 370 */     return infos;
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
/*     */   private static List<BonusInfo> parseDefense(DBStashItem item, int detailType, DBStat stat, String prefix, DBSkillModifier skillModifier, TagInfo tagInfo, boolean blankStat) {
/* 383 */     List<BonusInfo> infos = new LinkedList<>();
/*     */     
/* 385 */     String tag = null;
/* 386 */     List<Object> lArgs = null;
/* 387 */     BonusInfo info = null;
/*     */     
/* 389 */     String statDesc = GDMsgFormatter.getString(GDMsgFormatter.rbGD, tagInfo.getTag());
/*     */     
/* 391 */     if (prefix != null) {
/* 392 */       Object[] args = { prefix, statDesc };
/*     */       
/* 394 */       statDesc = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_STAT_PREFIX", args);
/*     */     } 
/*     */     
/* 397 */     if (stat.getStatMin() != 0.0F) {
/* 398 */       lArgs = new LinkedList();
/* 399 */       if (blankStat) {
/* 400 */         lArgs.add("");
/*     */       } else {
/* 402 */         lArgs.add(statDesc);
/*     */       } 
/* 404 */       lArgs.add(Integer.valueOf(adjustValue(item, detailType, (int)stat.getStatMin())));
/*     */       
/* 406 */       info = new BonusInfo(tagInfo);
/* 407 */       info.prefix = prefix;
/* 408 */       info.dmgFlat = true;
/*     */       
/* 410 */       tag = "TXT_INT";
/*     */       
/* 412 */       if (tagInfo.isSignMandatory() || skillModifier != null)
/*     */       {
/* 414 */         if (stat.getStatMin() > 0.0F) tag = tag + "_PLUS";
/*     */       
/*     */       }
/* 417 */       if (tagInfo.isMinAsPerc()) {
/* 418 */         tag = tag + "_PERC";
/*     */       }
/*     */       
/* 421 */       if (stat.getStatChance() > 0) {
/* 422 */         lArgs.add(Integer.valueOf(stat.getStatChance()));
/*     */         
/* 424 */         tag = tag + "_CHANCE";
/*     */ 
/*     */ 
/*     */         
/* 428 */         if (tagInfo.getTag().equals("DEF_BLOCK_CHANCE")) {
/* 429 */           tag = "TXT_DEF_UNI_SHIELD_BLOCK";
/*     */           
/* 431 */           lArgs.clear();
/* 432 */           lArgs.add(Float.valueOf(stat.getStatMin()));
/* 433 */           lArgs.add(Integer.valueOf(stat.getStatChance()));
/*     */         } 
/*     */         
/* 436 */         info.dmgChance = true;
/*     */       } 
/*     */       
/* 439 */       info.statDesc = statDesc;
/* 440 */       info.text = buildText(tag, lArgs);
/*     */       
/* 442 */       if (skillModifier != null) {
/* 443 */         String skillname = skillModifier.getSkillName();
/*     */         
/* 445 */         if (skillModifier.getMasteryName() != null) {
/* 446 */           skillname = skillname + " (" + skillModifier.getMasteryName() + ")";
/*     */         }
/*     */         
/* 449 */         Object[] args = { info.text, skillname };
/* 450 */         info.text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_MODIFIER_TO_SKILL", args);
/*     */       } 
/*     */       
/* 453 */       infos.add(info);
/*     */     } 
/*     */     
/* 456 */     if (stat.getStatModifier() != 0) {
/* 457 */       lArgs = new LinkedList();
/* 458 */       if (blankStat) {
/* 459 */         lArgs.add("");
/*     */       } else {
/* 461 */         lArgs.add(statDesc);
/*     */       } 
/* 463 */       lArgs.add(Integer.valueOf(adjustValue(item, detailType, stat.getStatModifier())));
/*     */       
/* 465 */       info = new BonusInfo(tagInfo);
/* 466 */       info.prefix = prefix;
/* 467 */       info.dmgPerc = true;
/*     */       
/* 469 */       tag = "TXT_DEF_PERC";
/*     */       
/* 471 */       info.statDesc = statDesc;
/* 472 */       info.text = buildText(tag, lArgs);
/*     */       
/* 474 */       if (skillModifier != null) {
/* 475 */         String skillname = skillModifier.getSkillName();
/*     */         
/* 477 */         if (skillModifier.getMasteryName() != null) {
/* 478 */           skillname = skillname + " (" + skillModifier.getMasteryName() + ")";
/*     */         }
/*     */         
/* 481 */         Object[] args = { info.text, skillname };
/* 482 */         info.text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_MODIFIER_TO_SKILL", args);
/*     */       } 
/*     */       
/* 485 */       infos.add(info);
/*     */     } 
/*     */     
/* 488 */     if (stat.getMinDuration() > 0) {
/* 489 */       statDesc = GDMsgFormatter.getString(GDMsgFormatter.rbGD, tagInfo.getTag() + "_DOT");
/*     */       
/* 491 */       if (prefix != null) {
/* 492 */         Object[] args = { prefix, statDesc };
/*     */         
/* 494 */         statDesc = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_STAT_PREFIX", args);
/*     */       } 
/*     */       
/* 497 */       lArgs = new LinkedList();
/* 498 */       if (blankStat) {
/* 499 */         lArgs.add("");
/*     */       } else {
/* 501 */         lArgs.add(statDesc);
/*     */       } 
/* 503 */       lArgs.add(Integer.valueOf(stat.getMinDuration()));
/*     */       
/* 505 */       info = new BonusInfo(tagInfo);
/* 506 */       info.prefix = prefix;
/* 507 */       info.dmgPerc = true;
/*     */       
/* 509 */       tag = "TXT_DEF_DUR";
/*     */       
/* 511 */       info.statDesc = statDesc;
/* 512 */       info.text = buildText(tag, lArgs);
/*     */       
/* 514 */       if (skillModifier != null) {
/* 515 */         String skillname = skillModifier.getSkillName();
/*     */         
/* 517 */         if (skillModifier.getMasteryName() != null) {
/* 518 */           skillname = skillname + " (" + skillModifier.getMasteryName() + ")";
/*     */         }
/*     */         
/* 521 */         Object[] args = { info.text, skillname };
/* 522 */         info.text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_MODIFIER_TO_SKILL", args);
/*     */       } 
/*     */       
/* 525 */       infos.add(info);
/*     */     } 
/*     */     
/* 528 */     if (stat.getMaxResist() > 0) {
/* 529 */       lArgs = new LinkedList();
/* 530 */       if (blankStat) {
/* 531 */         lArgs.add("");
/*     */       } else {
/* 533 */         lArgs.add(statDesc);
/*     */       } 
/* 535 */       lArgs.add(Integer.valueOf(stat.getMaxResist()));
/*     */       
/* 537 */       info = new BonusInfo(tagInfo);
/* 538 */       info.prefix = prefix;
/* 539 */       info.dmgPerc = true;
/* 540 */       info.dmgMaxResist = true;
/*     */       
/* 542 */       tag = "TXT_DEF_MAX";
/*     */       
/* 544 */       info.statDesc = statDesc;
/* 545 */       info.text = buildText(tag, lArgs);
/*     */       
/* 547 */       if (skillModifier != null) {
/* 548 */         String skillname = skillModifier.getSkillName();
/*     */         
/* 550 */         if (skillModifier.getMasteryName() != null) {
/* 551 */           skillname = skillname + " (" + skillModifier.getMasteryName() + ")";
/*     */         }
/*     */         
/* 554 */         Object[] args = { info.text, skillname };
/* 555 */         info.text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_MODIFIER_TO_SKILL", args);
/*     */       } 
/*     */       
/* 558 */       infos.add(info);
/*     */     } 
/*     */     
/* 561 */     return infos;
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
/*     */   
/*     */   private static List<BonusInfo> parseOffense(DBStashItem item, int detailType, DBStat stat, String prefix, DBSkillModifier skillModifier, TagInfo tagInfo, boolean blankStat) {
/* 582 */     List<BonusInfo> infos = new LinkedList<>();
/*     */     
/* 584 */     String tag = null;
/* 585 */     List<Object> lArgs = null;
/* 586 */     BonusInfo info = new BonusInfo(tagInfo);
/*     */     
/* 588 */     String statDesc = GDMsgFormatter.getString(GDMsgFormatter.rbGD, tagInfo.getTag());
/*     */     
/* 590 */     if (prefix != null) {
/* 591 */       Object[] args = { prefix, statDesc };
/*     */       
/* 593 */       statDesc = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_STAT_PREFIX", args);
/*     */     } 
/*     */     
/* 596 */     if (statDesc.equals("")) {
/* 597 */       return infos;
/*     */     }
/*     */     
/* 600 */     if (stat.getStatMin() != 0.0F) {
/* 601 */       lArgs = new LinkedList();
/* 602 */       if (blankStat) {
/* 603 */         lArgs.add("");
/*     */       } else {
/* 605 */         lArgs.add(statDesc);
/*     */       } 
/*     */       
/* 608 */       if (stat.getMinDuration() == 0) {
/* 609 */         lArgs.add(Integer.valueOf(adjustValue(item, detailType, (int)stat.getStatMin())));
/*     */       }
/* 611 */       else if (tagInfo.isMinAsPerc()) {
/* 612 */         lArgs.add(Integer.valueOf(adjustValue(item, detailType, (int)stat.getStatMin())));
/*     */       } else {
/*     */         
/* 615 */         lArgs.add(Integer.valueOf(adjustValue(item, detailType, (int)stat.getStatMin() * stat.getMinDuration())));
/* 616 */         lArgs.add(Integer.valueOf(adjustValue(item, detailType, (int)stat.getStatMin())));
/*     */       } 
/*     */ 
/*     */       
/* 620 */       info = new BonusInfo(tagInfo);
/* 621 */       info.prefix = prefix;
/* 622 */       info.dmgGlobal = stat.isGlobal();
/* 623 */       info.dmgXOR = stat.isXOR();
/* 624 */       info.dmgFlat = true;
/*     */       
/* 626 */       tag = "TXT_OFF_ABS";
/*     */       
/* 628 */       if (tagInfo.isSignMandatory() || skillModifier != null)
/*     */       {
/* 630 */         if (stat.getStatMin() > 0.0F) tag = tag + "_PLUS";
/*     */       
/*     */       }
/* 633 */       if (tagInfo.isMinAsPerc()) tag = tag + "_PERC"; 
/* 634 */       if (tagInfo.isMinAsDuration()) tag = tag + "_TIME";
/*     */       
/* 636 */       if (stat.getStatMax() != 0.0F) {
/* 637 */         tag = tag + "_RANGE";
/*     */         
/* 639 */         if (stat.getMinDuration() == 0) {
/* 640 */           lArgs.add(Integer.valueOf(adjustValue(item, detailType, (int)stat.getStatMax())));
/*     */         }
/* 642 */         else if (tagInfo.isMinAsPerc()) {
/* 643 */           lArgs.add(Integer.valueOf(adjustValue(item, detailType, (int)stat.getStatMax())));
/*     */         } else {
/*     */           
/* 646 */           lArgs.add(Integer.valueOf(adjustValue(item, detailType, (int)stat.getStatMax() * stat.getMinDuration())));
/* 647 */           lArgs.add(Integer.valueOf(adjustValue(item, detailType, (int)stat.getStatMax())));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 652 */       if (stat.getMinDuration() > 0) {
/* 653 */         tag = tag + "_DUR";
/*     */         
/* 655 */         lArgs.add(Integer.valueOf(stat.getMinDuration()));
/*     */         
/* 657 */         info.dmgDuration = true;
/*     */       } 
/*     */       
/* 660 */       if (stat.getStatChance() > 0) {
/* 661 */         if (stat.isGlobal()) {
/* 662 */           if (stat.isXOR()) {
/* 663 */             tag = tag + "_CHANCE";
/*     */             
/* 665 */             lArgs.add(Integer.valueOf(stat.getStatChance()));
/*     */             
/* 667 */             info.dmgChance = true;
/*     */           } 
/*     */         } else {
/* 670 */           tag = tag + "_CHANCE";
/*     */           
/* 672 */           lArgs.add(Integer.valueOf(stat.getStatChance()));
/*     */           
/* 674 */           info.dmgChance = true;
/*     */         } 
/*     */       }
/*     */       
/* 678 */       info.statDesc = statDesc;
/* 679 */       info.text = buildText(tag, lArgs);
/*     */       
/* 681 */       if (skillModifier != null) {
/* 682 */         String skillname = skillModifier.getSkillName();
/*     */         
/* 684 */         if (skillModifier.getMasteryName() != null) {
/* 685 */           skillname = skillname + " (" + skillModifier.getMasteryName() + ")";
/*     */         }
/*     */         
/* 688 */         Object[] args = { info.text, skillname };
/* 689 */         info.text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_MODIFIER_TO_SKILL", args);
/*     */       } 
/*     */       
/* 692 */       infos.add(info);
/*     */     } 
/*     */     
/* 695 */     if (stat.getStatModifier() != 0) {
/* 696 */       lArgs = new LinkedList();
/* 697 */       if (blankStat) {
/* 698 */         lArgs.add("");
/*     */       } else {
/* 700 */         lArgs.add(statDesc);
/*     */       } 
/* 702 */       lArgs.add(Integer.valueOf(adjustValue(item, detailType, stat.getStatModifier())));
/*     */       
/* 704 */       info = new BonusInfo(tagInfo);
/* 705 */       info.prefix = prefix;
/* 706 */       info.dmgGlobal = stat.isGlobal();
/* 707 */       info.dmgXOR = stat.isXOR();
/* 708 */       info.dmgPerc = true;
/*     */       
/* 710 */       tag = "TXT_OFF_PERC";
/*     */       
/* 712 */       if (stat.getDurationModifier() > 0) {
/* 713 */         tag = tag + "_DUR";
/*     */         
/* 715 */         lArgs.add(Integer.valueOf(stat.getDurationModifier()));
/*     */       } 
/*     */       
/* 718 */       if (stat.getStatModifierChance() > 0) {
/* 719 */         tag = tag + "_CHANCE";
/*     */         
/* 721 */         lArgs.add(Integer.valueOf(stat.getStatModifierChance()));
/*     */         
/* 723 */         info.dmgChance = true;
/*     */       } 
/*     */       
/* 726 */       info.statDesc = statDesc;
/* 727 */       info.text = buildText(tag, lArgs);
/*     */       
/* 729 */       if (skillModifier != null) {
/* 730 */         String skillname = skillModifier.getSkillName();
/*     */         
/* 732 */         if (skillModifier.getMasteryName() != null) {
/* 733 */           skillname = skillname + " (" + skillModifier.getMasteryName() + ")";
/*     */         }
/*     */         
/* 736 */         Object[] args = { info.text, skillname };
/* 737 */         info.text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_MODIFIER_TO_SKILL", args);
/*     */       } 
/*     */       
/* 740 */       infos.add(info);
/*     */     } 
/*     */     
/* 743 */     return infos;
/*     */   }
/*     */   
/*     */   private static List<BonusInfo> parseStat(DBStashItem item, int detailType, DBStat stat, String prefix, DBSkillModifier skillModifier, TagInfo tagInfo, boolean blankStat) {
/* 747 */     if (tagInfo.getTag().equals("TXT_UNI_RACE_DAMAGE")) {
/* 748 */       return fillRaceBonus(detailType, stat, prefix, tagInfo, blankStat);
/*     */     }
/*     */     
/* 751 */     int infoType = tagInfo.getInfoType();
/*     */     
/* 753 */     switch (infoType) {
/*     */       case 4:
/* 755 */         return parseCharStat(item, detailType, stat, prefix, skillModifier, tagInfo, blankStat);
/*     */       case 1:
/* 757 */         return parseDefense(item, detailType, stat, prefix, skillModifier, tagInfo, blankStat);
/*     */       case 2:
/* 759 */         return parseOffense(item, detailType, stat, prefix, skillModifier, tagInfo, blankStat);
/*     */       case 3:
/* 761 */         return parseOffense(item, detailType, stat, prefix, skillModifier, tagInfo, blankStat);
/*     */     } 
/*     */     
/* 764 */     return null;
/*     */   }
/*     */   
/*     */   private static List<BonusInfo> parseStat(DBStat stat, TagInfo tagInfo, boolean blankStat) {
/* 768 */     int infoType = tagInfo.getInfoType();
/*     */     
/* 770 */     switch (infoType) {
/*     */       case 4:
/* 772 */         return parseCharStat(null, 3, stat, null, null, tagInfo, blankStat);
/*     */       case 1:
/* 774 */         return parseDefense(null, 3, stat, null, null, tagInfo, blankStat);
/*     */       case 2:
/* 776 */         return parseOffense(null, 3, stat, null, null, tagInfo, blankStat);
/*     */       case 3:
/* 778 */         return parseOffense(null, 3, stat, null, null, tagInfo, blankStat);
/*     */     } 
/*     */     
/* 781 */     return null;
/*     */   }
/*     */   
/*     */   private static List<BonusInfo> parseStat(DBStat stat, TagInfo tagInfo, String prefix, boolean blankStat) {
/* 785 */     int infoType = tagInfo.getInfoType();
/*     */     
/* 787 */     switch (infoType) {
/*     */       case 4:
/* 789 */         return parseCharStat(null, 3, stat, prefix, null, tagInfo, blankStat);
/*     */       case 1:
/* 791 */         return parseDefense(null, 3, stat, prefix, null, tagInfo, blankStat);
/*     */       case 2:
/* 793 */         return parseOffense(null, 3, stat, prefix, null, tagInfo, blankStat);
/*     */       case 3:
/* 795 */         return parseOffense(null, 3, stat, prefix, null, tagInfo, blankStat);
/*     */     } 
/*     */     
/* 798 */     return null;
/*     */   }
/*     */   
/*     */   private static BonusInfo getStatInfo(DBStashItem item, int detailType, String statType, String prefix, int value, boolean isPercent) {
/* 802 */     return getStatInfo(item, detailType, statType, prefix, value, 0, isPercent);
/*     */   }
/*     */   
/*     */   private static BonusInfo getStatInfo(DBStashItem item, int detailType, String statType, String prefix, int value, int chance, boolean isPercent) {
/* 806 */     TagInfo tagInfo = TagInfoHashMap.getInfo(statType);
/*     */     
/* 808 */     if (tagInfo == null) return null;
/*     */     
/* 810 */     BonusInfo info = new BonusInfo(tagInfo);
/*     */     
/* 812 */     String tag = null;
/*     */     
/* 814 */     if (isPercent) {
/* 815 */       tag = "TXT_PERC_PLUS";
/*     */     } else {
/* 817 */       tag = "TXT_ABS_PLUS";
/*     */     } 
/*     */     
/* 820 */     String stat = GDMsgFormatter.getString(GDMsgFormatter.rbGD, statType);
/*     */     
/* 822 */     if (prefix != null) {
/* 823 */       Object[] args = { prefix, stat };
/*     */       
/* 825 */       stat = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_STAT_PREFIX", args);
/*     */     } 
/*     */     
/*     */     try {
/* 829 */       if (chance == 0) {
/* 830 */         Object[] args = { stat, Integer.valueOf(adjustValue(item, detailType, value)) };
/*     */         
/* 832 */         info.text = GDMsgFormatter.format(GDMsgFormatter.rbGD, tag, args);
/* 833 */         info.tag = statType;
/*     */         
/* 835 */         info.prefix = prefix;
/* 836 */         info.bonusPerc = isPercent;
/*     */       } else {
/* 838 */         tag = tag + "_CHANCE";
/*     */         
/* 840 */         Object[] args = { stat, Integer.valueOf(adjustValue(item, detailType, value)), Integer.valueOf(chance) };
/*     */         
/* 842 */         info.text = GDMsgFormatter.format(GDMsgFormatter.rbGD, tag, args);
/* 843 */         info.tag = statType;
/*     */         
/* 845 */         info.prefix = prefix;
/* 846 */         info.bonusPerc = isPercent;
/*     */       } 
/* 848 */     } catch (IllegalArgumentException ex) {
/* 849 */       Object[] errargs = { tag, "GrimDawn" };
/* 850 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_PROP_TAG_ARGS", errargs);
/*     */       
/* 852 */       GDMsgLogger.addError(msg);
/*     */       
/* 854 */       info = null;
/*     */     } 
/*     */     
/* 857 */     return info;
/*     */   }
/*     */   
/*     */   private void createSkillBonusInfos(DBStashItem item) {
/* 861 */     DBSkillBonus bonus = (DBSkillBonus)this.entity;
/*     */     
/* 863 */     int value = 0;
/*     */     
/* 865 */     if (item == null) {
/* 866 */       value = bonus.getValue();
/*     */     }
/* 868 */     else if (item.isComponent()) {
/* 869 */       value = bonus.getValueForLevel(item.getComponentPieces() - 1);
/*     */     } else {
/* 871 */       value = bonus.getValue();
/*     */     } 
/*     */     
/* 874 */     if (value > 0) {
/* 875 */       BonusInfo info = new BonusInfo();
/*     */       
/* 877 */       String skillname = bonus.getEntityName();
/*     */       
/* 879 */       if (bonus.getMasteryName() != null) {
/* 880 */         skillname = skillname + " (" + bonus.getMasteryName() + ")";
/*     */       }
/*     */       
/* 883 */       if (bonus.getSkillBonusType() == 3) {
/* 884 */         info.sbMastery = false;
/* 885 */         info.sbPlus = 0;
/*     */         
/* 887 */         DBSkill skill = DBSkill.get(bonus.getEntity());
/*     */         
/* 889 */         if (skill != null) {
/* 890 */           info.text = DetailComposer.getSkillDescription(null, skill, value, null);
/*     */           
/* 892 */           add(info);
/*     */         }
/* 894 */         else if (skillname != null) {
/* 895 */           Object[] args = { skillname };
/* 896 */           info.text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SKILL_GRANTED", args);
/*     */           
/* 898 */           add(info);
/*     */         }
/*     */       
/*     */       }
/* 902 */       else if (bonus.getSkillBonusType() == 4) {
/* 903 */         Object[] args = { Integer.valueOf(value) };
/* 904 */         info.text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_PLUS_ALLSKILLS", args);
/* 905 */         info.sbMastery = false;
/* 906 */         info.sbPlus = value;
/*     */       }
/* 908 */       else if (skillname != null) {
/* 909 */         Object[] args = { Integer.valueOf(value), skillname };
/* 910 */         info.text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SKILL_BONUS", args);
/* 911 */         info.sbMastery = (bonus.getSkillBonusType() == 1);
/* 912 */         info.sbPlus = value;
/*     */         
/* 914 */         add(info);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<BonusInfo> createSkillInfos(DBSkill skill) {
/* 923 */     List<BonusInfo> list = new LinkedList<>();
/* 924 */     String skillName = skill.getName();
/*     */     
/* 926 */     if (skillName != null) {
/* 927 */       BonusInfo info = new BonusInfo();
/*     */       
/* 929 */       Object[] sargs = { skillName };
/* 930 */       info.text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SKILL_GRANTED", sargs);
/*     */       
/* 932 */       list.add(info);
/*     */     } 
/*     */     
/* 935 */     return list;
/*     */   }
/*     */   
/*     */   public static List<BonusInfo> createSkillInfos(DBSkill skill, DBStat stat) {
/* 939 */     if (stat.getStatType() == null) {
/* 940 */       return null;
/*     */     }
/*     */     
/* 943 */     TagInfo info = TagInfoHashMap.getInfo(stat.getStatType());
/*     */     
/* 945 */     if (info == null) {
/* 946 */       return null;
/*     */     }
/*     */     
/* 949 */     if (info.getTag() != null) {
/* 950 */       return parseStat(stat, info, false);
/*     */     }
/*     */     
/* 953 */     return null;
/*     */   }
/*     */   
/*     */   public static List<BonusInfo> createSkillInfos(DBSkill skill, DBStat stat, String prefix) {
/* 957 */     if (stat.getStatType() == null) {
/* 958 */       return null;
/*     */     }
/*     */     
/* 961 */     TagInfo info = TagInfoHashMap.getInfo(stat.getStatType());
/*     */     
/* 963 */     if (info == null) {
/* 964 */       return null;
/*     */     }
/*     */     
/* 967 */     if (info.getTag() != null) {
/* 968 */       return parseStat(stat, info, prefix, false);
/*     */     }
/*     */     
/* 971 */     return null;
/*     */   }
/*     */   
/*     */   private void createSkillModifierInfos() {
/* 975 */     DBSkillModifier sm = (DBSkillModifier)this.entity;
/*     */     
/* 977 */     String skillName = sm.getSkillName();
/*     */     
/* 979 */     if (skillName != null)
/* 980 */       for (DBStat stat : sm.getModifierStatList()) {
/* 981 */         List<BonusInfo> list = createStatInfos((DBStashItem)null, 1, stat, sm);
/*     */         
/* 983 */         if (list != null)
/* 984 */           for (BonusInfo info : list)
/* 985 */             add(info);  
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\description\BonusDetail.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */