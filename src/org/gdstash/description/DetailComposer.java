/*      */ package org.gdstash.description;
/*      */ 
/*      */ import java.util.Collections;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import org.gdstash.db.DBAffix;
/*      */ import org.gdstash.db.DBController;
/*      */ import org.gdstash.db.DBItem;
/*      */ import org.gdstash.db.DBPet;
/*      */ import org.gdstash.db.DBSkill;
/*      */ import org.gdstash.db.DBSkillBonus;
/*      */ import org.gdstash.db.DBSkillModifier;
/*      */ import org.gdstash.db.DBStashItem;
/*      */ import org.gdstash.db.DBStat;
/*      */ import org.gdstash.item.GDItem;
/*      */ import org.gdstash.item.ItemInfo;
/*      */ import org.gdstash.util.GDColor;
/*      */ import org.gdstash.util.GDMsgFormatter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DetailComposer
/*      */ {
/*      */   public static final String GD_NEWLINE = "{^n}";
/*      */   public static final String HTML_HTMLTAG_START = "<html>";
/*      */   public static final String HTML_HTMLTAG_END = "</html>";
/*      */   public static final String HTML_NEWLINE = "<br>";
/*      */   public static final String HTML_LIST_UNSORTED_START = "<ul>";
/*      */   public static final String HTML_LIST_UNSORTED_END = "</ul>";
/*      */   public static final String HTML_LISTITEM_START = "<li>";
/*      */   public static final String HTML_LISTITEM_END = "</li>";
/*      */   public static final int DETAIL_ITEM = 1;
/*      */   public static final int DETAIL_COMPONENT = 2;
/*   41 */   private static AffixComparator compAffix = new AffixComparator();
/*   42 */   private static ItemComparator compItem = new ItemComparator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   49 */   public String preText = null;
/*   50 */   public List<BonusDetail> bonuses = new LinkedList<>();
/*      */   public List<BonusInfoSort> sorted;
/*      */   
/*      */   public void add(BonusDetail bonus) {
/*   54 */     if (bonus == null)
/*   55 */       return;  if (bonus.infos == null)
/*      */       return; 
/*   57 */     if (bonus.infos.size() > 0) this.bonuses.add(bonus); 
/*      */   }
/*      */   
/*      */   public void add(DetailComposer composer) {
/*   61 */     if (composer == null)
/*   62 */       return;  if (composer.bonuses == null)
/*      */       return; 
/*   64 */     if (composer.bonuses.size() > 0) this.bonuses.addAll(composer.bonuses); 
/*      */   }
/*      */   
/*      */   private void sortAffixBonuses() {
/*   68 */     this.sorted = new LinkedList<>();
/*      */     
/*   70 */     BonusInfoSort sort = null;
/*      */     
/*   72 */     for (BonusDetail bonus : this.bonuses) {
/*   73 */       for (BonusInfo info : bonus.infos) {
/*   74 */         sort = new BonusInfoSort(bonus, info);
/*   75 */         this.sorted.add(sort);
/*      */       } 
/*      */     } 
/*      */     
/*   79 */     Collections.sort(this.sorted, compAffix);
/*      */   }
/*      */   
/*      */   public String getAffixText() {
/*   83 */     sortAffixBonuses();
/*      */     
/*   85 */     String s = this.preText;
/*      */     
/*   87 */     boolean bracket = (this.preText.indexOf("[") != -1);
/*   88 */     boolean separator = bracket;
/*   89 */     int count = 0;
/*      */     
/*   91 */     for (BonusInfoSort sort : this.sorted) {
/*   92 */       if (count > 0 && (
/*   93 */         sort.info.dmgChance || 
/*   94 */         sort.info.dmgGlobal || 
/*   95 */         sort.info.infoType == 3))
/*      */         continue; 
/*   97 */       if (sort.info.dmgDuration && 
/*   98 */         count > 1) {
/*      */         continue;
/*      */       }
/*  101 */       if (!bracket) {
/*  102 */         if (s == null) {
/*  103 */           s = "[";
/*      */         } else {
/*  105 */           s = s + " [";
/*      */         } 
/*      */         
/*  108 */         bracket = true;
/*      */       } 
/*      */       
/*  111 */       if (separator) {
/*  112 */         s = s + ". ";
/*      */       } else {
/*  114 */         separator = true;
/*      */       } 
/*      */       
/*  117 */       s = s + sort.info.text;
/*      */       
/*  119 */       count++;
/*  120 */       if (count >= 4)
/*      */         break; 
/*      */     } 
/*  123 */     if (bracket) s = s + "]";
/*      */     
/*  125 */     return s;
/*      */   }
/*      */   
/*      */   private void sortItemBonuses() {
/*  129 */     this.sorted = new LinkedList<>();
/*      */     
/*  131 */     BonusInfoSort sort = null;
/*      */     
/*  133 */     for (BonusDetail bonus : this.bonuses) {
/*  134 */       for (BonusInfo info : bonus.infos) {
/*  135 */         sort = new BonusInfoSort(bonus, info);
/*  136 */         this.sorted.add(sort);
/*      */       } 
/*      */     } 
/*      */     
/*  140 */     Collections.sort(this.sorted, compItem);
/*      */   }
/*      */   
/*      */   public static DetailComposer createComposer(DBStashItem item, int detailType, List<DBStat> stats, List<DBSkillBonus> skillBonuses) {
/*  144 */     DetailComposer comp = new DetailComposer();
/*  145 */     BonusDetail bonus = null;
/*      */     
/*  147 */     for (DBStat stat : stats) {
/*  148 */       String prefix = null;
/*      */       
/*  150 */       if (stat != null && 
/*  151 */         stat.isPetStat()) {
/*  152 */         prefix = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_PREFIX_PET", null);
/*      */       }
/*      */ 
/*      */       
/*  156 */       bonus = new BonusDetail(item, detailType, stat, prefix);
/*  157 */       if (bonus.infos != null && 
/*  158 */         !bonus.infos.isEmpty()) {
/*  159 */         comp.add(bonus);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  164 */     if (skillBonuses != null && 
/*  165 */       !skillBonuses.isEmpty()) {
/*  166 */       for (DBSkillBonus skillBonus : skillBonuses) {
/*  167 */         bonus = skillBonus.getBonusDetail(item);
/*  168 */         if (!bonus.infos.isEmpty()) comp.add(bonus);
/*      */       
/*      */       } 
/*      */     }
/*  172 */     return comp;
/*      */   }
/*      */   
/*      */   public static DetailComposer createComposerSkillMod(DBStashItem item, int detailType, List<DBSkillModifier> modifiers) {
/*  176 */     DetailComposer comp = new DetailComposer();
/*  177 */     BonusDetail bonus = null;
/*      */     
/*  179 */     for (DBSkillModifier sm : modifiers) {
/*  180 */       List<DBStat> stats = DBStat.getStatsForLevel(sm.getModifierStatList(), 1);
/*      */       
/*  182 */       for (DBStat stat : stats) {
/*  183 */         bonus = new BonusDetail(item, detailType, stat, sm);
/*  184 */         if (bonus.infos != null && 
/*  185 */           !bonus.infos.isEmpty()) comp.add(bonus);
/*      */       
/*      */       } 
/*      */     } 
/*      */     
/*  190 */     return comp;
/*      */   }
/*      */   
/*      */   public static DetailComposer createComposer(DBStashItem item, int detailType, List<DBStat> stats) {
/*  194 */     return createComposer(item, detailType, stats, null);
/*      */   }
/*      */   
/*      */   public static DetailComposer createComposer(DBSkill skill, List<DBStat> stats) {
/*  198 */     DetailComposer comp = new DetailComposer();
/*  199 */     BonusDetail bonus = null;
/*      */     
/*  201 */     for (DBStat stat : stats) {
/*  202 */       String prefix = null;
/*      */       
/*  204 */       if (stat != null && 
/*  205 */         stat.isPetStat()) {
/*  206 */         prefix = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_PREFIX_PET", null);
/*      */       }
/*      */ 
/*      */       
/*  210 */       bonus = new BonusDetail(skill, stat, prefix);
/*  211 */       if (bonus.infos != null && 
/*  212 */         !bonus.infos.isEmpty()) {
/*  213 */         comp.add(bonus);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  218 */     return comp;
/*      */   }
/*      */   
/*      */   public static String getComposerBonuses(DetailComposer comp, boolean showBase, boolean showRest, boolean separateBase) {
/*  222 */     comp.sortItemBonuses();
/*      */     
/*  224 */     boolean inBase = false;
/*  225 */     boolean inOffGlobal = false;
/*  226 */     int offPercent = 0;
/*  227 */     boolean inRetGlobal = false;
/*  228 */     int retPercent = 0;
/*      */     
/*  230 */     String s = null;
/*      */     
/*  232 */     for (BonusInfoSort sort : comp.sorted) {
/*  233 */       if ((!showBase && sort.isBaseStat()) || (
/*  234 */         !showRest && !sort.isBaseStat()))
/*      */         continue; 
/*  236 */       if (sort.isBaseStat()) {
/*  237 */         inBase = true;
/*      */       }
/*  239 */       else if (inBase) {
/*  240 */         if (separateBase) {
/*  241 */           if (s == null) {
/*  242 */             s = "";
/*      */           } else {
/*  244 */             s = s + "<br>";
/*      */           } 
/*      */         }
/*      */         
/*  248 */         inBase = false;
/*      */       } 
/*      */ 
/*      */       
/*  252 */       if (sort.isGlobal()) {
/*  253 */         if (sort.getInfoType() == 2) {
/*  254 */           if (!inOffGlobal) {
/*  255 */             inOffGlobal = true;
/*      */             
/*  257 */             offPercent = sort.getGlobalOffensePerc();
/*  258 */             if (offPercent == 0)
/*      */               continue; 
/*  260 */             Object[] args = { Integer.valueOf(offPercent) };
/*  261 */             String title = "";
/*  262 */             if (sort.isXOR()) {
/*  263 */               title = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_CHANCE_XOR", args);
/*      */             } else {
/*  265 */               title = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_CHANCE_GLOBAL", args);
/*      */             } 
/*      */             
/*  268 */             if (s == null) {
/*  269 */               s = title + "<br>" + "<ul>";
/*      */             } else {
/*  271 */               s = s + title + "<br>" + "<ul>";
/*      */             } 
/*  273 */             s = s + "<li>" + sort.info.text + "</li>";
/*      */           } else {
/*  275 */             if (offPercent == 0)
/*      */               continue; 
/*  277 */             if (s == null) {
/*  278 */               s = "<li>" + sort.info.text + "</li>";
/*      */             } else {
/*  280 */               s = s + "<li>" + sort.info.text + "</li>";
/*      */             } 
/*      */           } 
/*      */         }
/*      */         
/*  285 */         if (sort.getInfoType() == 3) {
/*  286 */           if (!inRetGlobal) {
/*  287 */             inRetGlobal = true;
/*      */             
/*  289 */             retPercent = sort.getGlobalRetaliationPerc();
/*  290 */             if (retPercent == 0)
/*      */               continue; 
/*  292 */             Object[] args = { Integer.valueOf(retPercent) };
/*  293 */             String title = "";
/*  294 */             if (sort.isXOR()) {
/*  295 */               title = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_CHANCE_XOR", args);
/*      */             } else {
/*  297 */               title = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_CHANCE_GLOBAL", args);
/*      */             } 
/*      */             
/*  300 */             if (s == null) {
/*  301 */               s = title + "<br>" + "<ul>";
/*      */             } else {
/*  303 */               s = s + title + "<br>" + "<ul>";
/*      */             } 
/*  305 */             s = s + "<li>" + sort.info.text + "</li>"; continue;
/*      */           } 
/*  307 */           if (retPercent == 0)
/*      */             continue; 
/*  309 */           if (s == null) {
/*  310 */             s = "<li>" + sort.info.text + "</li>"; continue;
/*      */           } 
/*  312 */           s = s + "<li>" + sort.info.text + "</li>";
/*      */         } 
/*      */         
/*      */         continue;
/*      */       } 
/*  317 */       if (inOffGlobal) {
/*  318 */         if (offPercent != 0) {
/*  319 */           if (s == null) {
/*  320 */             s = "";
/*      */           } else {
/*  322 */             s = s + "</ul>";
/*      */           } 
/*      */         }
/*      */         
/*  326 */         inOffGlobal = false;
/*      */       } 
/*  328 */       if (inRetGlobal) {
/*  329 */         if (retPercent != 0) {
/*  330 */           if (s == null) {
/*  331 */             s = "";
/*      */           } else {
/*  333 */             s = s + "</ul>";
/*      */           } 
/*      */         }
/*      */         
/*  337 */         inRetGlobal = false;
/*      */       } 
/*      */       
/*  340 */       if (s == null) {
/*  341 */         s = sort.info.text + "<br>"; continue;
/*      */       } 
/*  343 */       s = s + sort.info.text + "<br>";
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  348 */     if (inOffGlobal && 
/*  349 */       offPercent != 0) {
/*  350 */       if (s == null) {
/*  351 */         s = "";
/*      */       } else {
/*  353 */         s = s + "</ul>";
/*      */       } 
/*      */     }
/*      */     
/*  357 */     if (inRetGlobal && 
/*  358 */       retPercent != 0) {
/*  359 */       if (s == null) {
/*  360 */         s = "";
/*      */       } else {
/*  362 */         s = s + "</ul>";
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  367 */     return s;
/*      */   }
/*      */   
/*      */   private static String getDamageType(String s) {
/*  371 */     if (s == null) return null;
/*      */     
/*  373 */     String tag = null;
/*      */     
/*  375 */     if (s.equals("Aether")) tag = "OFF_AETHER"; 
/*  376 */     if (s.equals("Chaos")) tag = "OFF_CHAOS"; 
/*  377 */     if (s.equals("Cold")) tag = "OFF_COLD"; 
/*  378 */     if (s.equals("Elemental")) tag = "OFF_ELEMENTAL"; 
/*  379 */     if (s.equals("Fire")) tag = "OFF_FIRE"; 
/*  380 */     if (s.equals("Life")) tag = "OFF_LIFE"; 
/*  381 */     if (s.equals("Lightning")) tag = "OFF_LIGHTNING"; 
/*  382 */     if (s.equals("Physical")) tag = "OFF_PHYSICAL"; 
/*  383 */     if (s.equals("Pierce")) tag = "OFF_PIERCE"; 
/*  384 */     if (s.equals("Poison")) tag = "OFF_POISON";
/*      */     
/*  386 */     if (tag == null) return null;
/*      */     
/*  388 */     return GDMsgFormatter.format(GDMsgFormatter.rbGD, tag, null);
/*      */   }
/*      */   
/*      */   private static String getConversion(DBItem item) {
/*  392 */     String s = null;
/*      */     
/*  394 */     if (item.getConvertPerc() > 0) {
/*  395 */       String in = getDamageType(item.getConvertIn());
/*  396 */       String out = getDamageType(item.getConvertOut());
/*      */       
/*  398 */       if (in != null && out != null) {
/*  399 */         Object[] args = { Integer.valueOf(item.getConvertPerc()), in, out };
/*  400 */         String part = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_CONVERT_FROM_TO", args);
/*      */         
/*  402 */         if (s == null) {
/*  403 */           s = part + "<br>";
/*      */         } else {
/*  405 */           s = s + part + "<br>";
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  410 */     if (item.getConvertPerc2() > 0) {
/*  411 */       String in = getDamageType(item.getConvertIn2());
/*  412 */       String out = getDamageType(item.getConvertOut2());
/*      */       
/*  414 */       if (in != null && out != null) {
/*  415 */         Object[] args = { Integer.valueOf(item.getConvertPerc2()), in, out };
/*  416 */         String part = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_CONVERT_FROM_TO", args);
/*      */         
/*  418 */         if (s == null) {
/*  419 */           s = part + "<br>";
/*      */         } else {
/*  421 */           s = s + part + "<br>";
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  426 */     return s;
/*      */   }
/*      */   
/*      */   private static String getConversion(DBAffix affix) {
/*  430 */     if (affix == null) return null;
/*      */     
/*  432 */     String s = null;
/*      */     
/*  434 */     if (affix.getConvertPerc() > 0) {
/*  435 */       String in = getDamageType(affix.getConvertIn());
/*  436 */       String out = getDamageType(affix.getConvertOut());
/*      */       
/*  438 */       if (in != null && out != null) {
/*  439 */         Object[] args = { Integer.valueOf(affix.getConvertPerc()), in, out };
/*  440 */         String part = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_CONVERT_FROM_TO", args);
/*      */         
/*  442 */         if (s == null) {
/*  443 */           s = part + "<br>";
/*      */         } else {
/*  445 */           s = s + part + "<br>";
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  450 */     if (affix.getConvertPerc2() > 0) {
/*  451 */       String in = getDamageType(affix.getConvertIn2());
/*  452 */       String out = getDamageType(affix.getConvertOut2());
/*      */       
/*  454 */       if (in != null && out != null) {
/*  455 */         Object[] args = { Integer.valueOf(affix.getConvertPerc2()), in, out };
/*  456 */         String part = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_CONVERT_FROM_TO", args);
/*      */         
/*  458 */         if (s == null) {
/*  459 */           s = part + "<br>";
/*      */         } else {
/*  461 */           s = s + part + "<br>";
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  466 */     return s;
/*      */   }
/*      */   
/*      */   private static String getConversion(DBSkill skill, DBSkill modifiedSkill) {
/*  470 */     return getConversion(skill, modifiedSkill, 1);
/*      */   }
/*      */   
/*      */   private static String getConversion(DBSkill skill, DBSkill modifiedSkill, int level) {
/*  474 */     if (skill == null) return null;
/*      */     
/*  476 */     String s = null;
/*      */     
/*  478 */     if (skill.getConvertPerc(level) > 0) {
/*  479 */       String in = getDamageType(skill.getConvertIn());
/*  480 */       String out = getDamageType(skill.getConvertOut());
/*      */       
/*  482 */       if (in != null && out != null) {
/*  483 */         Object[] args = { Integer.valueOf(skill.getConvertPerc(level)), in, out };
/*  484 */         String part = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_CONVERT_FROM_TO", args);
/*      */         
/*  486 */         if (modifiedSkill != null) {
/*  487 */           String skillName = modifiedSkill.getName();
/*      */           
/*  489 */           if (modifiedSkill.getMasteryName() != null) {
/*  490 */             skillName = skillName + " (" + modifiedSkill.getMasteryName() + ")";
/*      */           }
/*      */           
/*  493 */           Object[] args2 = { part, skillName };
/*  494 */           part = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_MODIFIER_TO_SKILL", args2);
/*      */         } 
/*      */         
/*  497 */         if (s == null) {
/*  498 */           s = part + "<br>";
/*      */         } else {
/*  500 */           s = s + part + "<br>";
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  505 */     if (skill.getConvertPerc2(level) > 0) {
/*  506 */       String in = getDamageType(skill.getConvertIn2());
/*  507 */       String out = getDamageType(skill.getConvertOut2());
/*      */       
/*  509 */       if (in != null && out != null) {
/*  510 */         Object[] args = { Integer.valueOf(skill.getConvertPerc2(level)), in, out };
/*  511 */         String part = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_CONVERT_FROM_TO", args);
/*      */         
/*  513 */         if (modifiedSkill != null) {
/*  514 */           String skillName = modifiedSkill.getName();
/*      */           
/*  516 */           if (modifiedSkill.getMasteryName() != null) {
/*  517 */             skillName = skillName + " (" + modifiedSkill.getMasteryName() + ")";
/*      */           }
/*      */           
/*  520 */           Object[] args2 = { s, skillName };
/*  521 */           part = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_MODIFIER_TO_SKILL", args2);
/*      */         } 
/*      */         
/*  524 */         if (s == null) {
/*  525 */           s = part + "<br>";
/*      */         } else {
/*  527 */           s = s + part + "<br>";
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  532 */     return s;
/*      */   }
/*      */   
/*      */   private static String getConversion(DBSkillModifier skillModifier) {
/*  536 */     if (skillModifier == null) return null;
/*      */     
/*  538 */     DBSkill modifier = skillModifier.getSkillModifier();
/*  539 */     if (modifier == null) return null;
/*      */     
/*  541 */     DBSkill skill = skillModifier.getSkill();
/*  542 */     if (skill == null) return null;
/*      */     
/*  544 */     String s = getConversion(modifier, skill);
/*      */     
/*  546 */     return s;
/*      */   }
/*      */   
/*      */   private static String getSkills(DBStashItem item, int detailType, List<ItemInfo.Skill> list) {
/*  550 */     if (list == null) return null; 
/*  551 */     if (list.isEmpty()) return null;
/*      */     
/*  553 */     if (item != null) {
/*  554 */       if (detailType == 1 && item.isComponent() && 
/*  555 */         item.isIncompleteComponent()) return null;
/*      */ 
/*      */       
/*  558 */       if (detailType == 2) {
/*  559 */         if (item.getDBComponent() == null) return null;
/*      */         
/*  561 */         if (item.isIncompleteComponent()) return null;
/*      */       
/*      */       } 
/*      */     } 
/*  565 */     String s = null;
/*      */     
/*  567 */     for (ItemInfo.Skill iis : list) {
/*  568 */       DBSkill skill = iis.getSkill();
/*      */       
/*  570 */       String text = getSkillDescription(item, skill, iis.getLevel(), iis.getController());
/*  571 */       if (s == null) {
/*  572 */         s = text; continue;
/*      */       } 
/*  574 */       if (text != null) s = s + text;
/*      */     
/*      */     } 
/*      */     
/*  578 */     return s;
/*      */   }
/*      */   
/*      */   public static String getSkillDescription(DBSkill skill, int level, int charLevel, boolean nextLevel, boolean fullHTML) {
/*  582 */     if (skill == null) return null;
/*      */     
/*  584 */     String s = "";
/*  585 */     if (fullHTML) s = "<html>"; 
/*  586 */     String text = null;
/*      */     
/*  588 */     text = skill.getName();
/*  589 */     if (text != null) {
/*  590 */       s = s + GDColor.HTML_COLOR_SKILL + text + "</font>" + "<br>";
/*      */     }
/*      */     
/*  593 */     text = skill.getDescription();
/*  594 */     if (text != null) {
/*  595 */       s = s + text + "<br>";
/*      */     }
/*      */     
/*  598 */     if (level > 0) {
/*  599 */       if (fullHTML) {
/*  600 */         s = s + "<br>";
/*      */         
/*  602 */         int value = 0;
/*      */         
/*  604 */         value = skill.getManaCost(level);
/*  605 */         if (value > 0) {
/*  606 */           Object[] iArgs = { Integer.valueOf(value) };
/*  607 */           text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_ENERGY_COST", iArgs);
/*      */           
/*  609 */           s = s + text + "<br>";
/*      */         } 
/*      */         
/*  612 */         float fVal = skill.getCooldownTime(level);
/*  613 */         if (fVal > 0.0D) {
/*  614 */           Object[] fArgs = { Float.valueOf(fVal) };
/*  615 */           text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_COOLDOWN_TIME", fArgs);
/*      */           
/*  617 */           s = s + text + "<br>";
/*      */         } 
/*      */         
/*  620 */         value = skill.getActiveDuration(level);
/*  621 */         if (value > 0) {
/*  622 */           Object[] iArgs = { Integer.valueOf(value) };
/*  623 */           text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_ACTIVE_DURATION", iArgs);
/*      */           
/*  625 */           s = s + text + "<br>";
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  637 */       text = getSkillLevelStatDescription(skill, level, DBSkill.SkillDetail.SKILL, false);
/*      */       
/*  639 */       DBSkill petSkill = skill.getPetSkill();
/*  640 */       if (petSkill != null) {
/*  641 */         String petText = getSkillLevelStatDescription(petSkill, level, DBSkill.SkillDetail.PET, true);
/*      */         
/*  643 */         if (petText != null) {
/*  644 */           String line = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_PET_BONUS");
/*      */           
/*  646 */           if (text == null) {
/*  647 */             text = GDColor.HTML_COLOR_SKILL + line + "</font>" + "<br>" + petText;
/*      */           } else {
/*  649 */             text = text + "<br>" + GDColor.HTML_COLOR_SKILL + line + "</font>" + "<br>" + petText;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  655 */       if (text != null) {
/*  656 */         String lvl = "";
/*      */         
/*  658 */         if (fullHTML) {
/*  659 */           Object[] iArgs = { Integer.valueOf(level) };
/*  660 */           lvl = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SKILL_LEVEL", iArgs);
/*      */           
/*  662 */           lvl = GDColor.HTML_COLOR_SKILL + lvl + "</font>" + "<br>";
/*      */         } 
/*      */         
/*  665 */         s = s + "<br>" + lvl + text;
/*      */       } 
/*      */       
/*  668 */       DBPet pet = skill.getPet(level);
/*  669 */       if (pet != null) {
/*  670 */         pet.setPlayerLevel(charLevel);
/*      */         
/*  672 */         s = s + "<br>" + pet.getPetDescription(false);
/*      */       } 
/*      */     } 
/*      */     
/*  676 */     if (nextLevel && 
/*  677 */       skill.getMaxLevel() > level) {
/*  678 */       s = s + "<br>";
/*      */       
/*  680 */       text = getSkillLevelStatDescription(skill, level + 1, DBSkill.SkillDetail.SKILL, false);
/*      */       
/*  682 */       DBSkill petSkill = skill.getPetSkill();
/*  683 */       if (petSkill != null) {
/*  684 */         String petText = getSkillLevelStatDescription(petSkill, level + 1, DBSkill.SkillDetail.PET, true);
/*      */         
/*  686 */         if (petText != null) {
/*  687 */           String line = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_PET_BONUS");
/*      */           
/*  689 */           if (text == null) {
/*  690 */             text = GDColor.HTML_COLOR_SKILL + line + "</font>" + "<br>" + petText;
/*      */           } else {
/*  692 */             text = text + "<br>" + GDColor.HTML_COLOR_SKILL + line + "</font>" + "<br>" + petText;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  698 */       if (text != null) {
/*  699 */         String lvl = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SKILL_NEXT_LEVEL");
/*      */         
/*  701 */         s = s + "<br>" + GDColor.HTML_COLOR_SKILL + lvl + "</font>" + "<br>";
/*      */         
/*  703 */         s = s + text + "<br>";
/*      */       } 
/*      */       
/*  706 */       DBPet pet = skill.getPet(level + 1);
/*  707 */       if (pet != null) {
/*  708 */         pet.setPlayerLevel(charLevel);
/*      */         
/*  710 */         s = s + "<br>" + pet.getPetDescription(false) + "<br>";
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  715 */     if (fullHTML) s = s + "</html>";
/*      */     
/*  717 */     return s;
/*      */   }
/*      */   
/*      */   public static String getSkillLevelStatDescription(DBSkill skill, int level, DBSkill.SkillDetail detail) {
/*  721 */     return getSkillLevelStatDescription(skill, level, detail, false);
/*      */   }
/*      */   
/*      */   public static String getSkillLevelStatDescription(DBSkill skill, int level, DBSkill.SkillDetail detail, boolean petSkill) {
/*  725 */     if (skill == null) return null; 
/*  726 */     if (level == 0) return null;
/*      */ 
/*      */ 
/*      */     
/*  730 */     String s = "";
/*      */     
/*  732 */     String part = getConversion(skill, null, level);
/*  733 */     if (part != null) {
/*  734 */       s = s + part;
/*      */     }
/*      */     
/*  737 */     String[] exclude = { "skillCooldownTime", "skillActiveDuration", "skillMaxLevel" };
/*      */     
/*  739 */     List<DBStat> stats = DBStat.getByLevel(skill.getStatList(exclude, detail, petSkill), level);
/*  740 */     DetailComposer comp = createComposer(skill, stats);
/*      */     
/*  742 */     part = getComposerBonuses(comp, true, true, true);
/*  743 */     if (part != null) {
/*  744 */       s = s + part;
/*      */     }
/*      */     
/*  747 */     return s;
/*      */   }
/*      */   
/*      */   public static String getPetDescription(DBPet pet, boolean fullHTML) {
/*  751 */     if (pet == null) return null;
/*      */     
/*  753 */     String s = "";
/*  754 */     if (fullHTML) s = "<html>"; 
/*  755 */     String text = null;
/*      */     
/*  757 */     text = pet.getName();
/*      */     
/*  759 */     s = s + GDColor.HTML_COLOR_SKILL + text + "</font>" + "<br>";
/*      */     
/*  761 */     int value = 0;
/*      */     
/*  763 */     value = pet.getHealth();
/*  764 */     if (value > 0) {
/*  765 */       Object[] iArgs = { Integer.valueOf(value) };
/*  766 */       text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_PET_HEALTH", iArgs);
/*      */       
/*  768 */       s = s + text + "<br>";
/*      */     } 
/*      */     
/*  771 */     value = pet.getMana();
/*  772 */     if (value > 0) {
/*  773 */       Object[] iArgs = { Integer.valueOf(value) };
/*  774 */       text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_PET_MANA", iArgs);
/*      */       
/*  776 */       s = s + text + "<br>";
/*      */     } 
/*      */     
/*  779 */     s = s + "<br>";
/*      */     
/*  781 */     text = pet.getPetSkillDescriptions();
/*  782 */     if (text != null && 
/*  783 */       !text.isEmpty()) {
/*  784 */       s = s + text;
/*      */     }
/*      */ 
/*      */     
/*  788 */     if (fullHTML) s = s + "</html>";
/*      */     
/*  790 */     return s;
/*      */   }
/*      */   
/*      */   public static String getSkillDescription(DBStashItem item, DBSkill skill, int level, DBController dbController) {
/*  794 */     if (skill == null) return null;
/*      */     
/*  796 */     String s = null;
/*  797 */     String text = null;
/*      */     
/*  799 */     if (skill.getName() == null) {
/*  800 */       text = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_PET_BONUS");
/*      */     } else {
/*  802 */       Object[] args = { skill.getName() };
/*  803 */       text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_SKILL_GRANTED", args);
/*      */     } 
/*      */     
/*  806 */     if (s == null) {
/*  807 */       s = GDColor.HTML_COLOR_SKILL + text + "</font>" + "<br>";
/*      */     } else {
/*  809 */       s = s + GDColor.HTML_COLOR_SKILL + text + "</font>" + "<br>";
/*      */     } 
/*      */     
/*  812 */     if (skill.getDescription() != null) {
/*  813 */       s = s + skill.getDescription() + "<br>" + "<br>";
/*      */     }
/*      */     
/*  816 */     int value = 0;
/*      */     
/*  818 */     value = skill.getTriggerLifePerc(level);
/*  819 */     if (value > 0) {
/*  820 */       Object[] iArgs = { Integer.valueOf(value) };
/*  821 */       text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_TRIGGER_HEALTH", iArgs);
/*      */       
/*  823 */       s = s + text + "<br>";
/*      */     }
/*  825 */     else if (dbController != null) {
/*  826 */       String trigger = dbController.getTriggerType();
/*      */       
/*  828 */       if (trigger != null) {
/*  829 */         value = dbController.getTriggerChance();
/*      */         
/*  831 */         if (value > 0) {
/*  832 */           text = DBController.getTriggerDescription(trigger, value);
/*      */           
/*  834 */           if (text != null) {
/*  835 */             s = s + text + "<br>";
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  842 */     value = skill.getManaCost(level);
/*  843 */     if (value > 0) {
/*  844 */       Object[] iArgs = { Integer.valueOf(value) };
/*  845 */       text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_ENERGY_COST", iArgs);
/*      */       
/*  847 */       s = s + text + "<br>";
/*      */     } 
/*      */     
/*  850 */     float fVal = skill.getCooldownTime(level);
/*  851 */     if (fVal > 0.0D) {
/*  852 */       Object[] fArgs = { Float.valueOf(fVal) };
/*  853 */       text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_COOLDOWN_TIME", fArgs);
/*      */       
/*  855 */       s = s + text + "<br>";
/*      */     } 
/*      */     
/*  858 */     value = skill.getActiveDuration(level);
/*  859 */     if (value > 0) {
/*  860 */       Object[] iArgs = { Integer.valueOf(value) };
/*  861 */       text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_ACTIVE_DURATION", iArgs);
/*      */       
/*  863 */       s = s + text + "<br>";
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  874 */     String[] exclude = { "skillCooldownTime", "skillActiveDuration", "skillMaxLevel" };
/*  875 */     int skillLevel = skill.getSkillLevel();
/*  876 */     if (skillLevel == 0) skillLevel = level;
/*      */     
/*  878 */     List<DBStat> stats = DBStat.getByLevel(skill.getStatList(exclude, DBSkill.SkillDetail.ALL), skillLevel);
/*  879 */     DetailComposer comp = createComposer(item, 1, stats);
/*      */     
/*  881 */     String part = getComposerBonuses(comp, true, true, true);
/*  882 */     if (part != null) {
/*  883 */       s = s + part;
/*      */     }
/*      */     
/*  886 */     return s;
/*      */   }
/*      */   
/*      */   private static String getItemReqs(GDItem item) {
/*  890 */     String s = null;
/*  891 */     int value = 0;
/*      */     
/*  893 */     value = item.getRequiredLevel();
/*  894 */     if (value > 0) {
/*  895 */       Object[] args = { Integer.valueOf(value) };
/*  896 */       String text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_REQ_LEVEL", args);
/*      */       
/*  898 */       if (s == null) {
/*  899 */         s = text + "<br>";
/*      */       } else {
/*  901 */         s = s + text + "<br>";
/*      */       } 
/*      */     } 
/*      */     
/*  905 */     value = item.getRequiredPhysique();
/*  906 */     if (value > 0) {
/*  907 */       Object[] args = { Integer.valueOf(value) };
/*  908 */       String text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_REQ_PHYSIQUE", args);
/*      */       
/*  910 */       if (s == null) {
/*  911 */         s = text + "<br>";
/*      */       } else {
/*  913 */         s = s + text + "<br>";
/*      */       } 
/*      */     } 
/*      */     
/*  917 */     value = item.getRequiredCunning();
/*  918 */     if (value > 0) {
/*  919 */       Object[] args = { Integer.valueOf(value) };
/*  920 */       String text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_REQ_CUNNING", args);
/*      */       
/*  922 */       if (s == null) {
/*  923 */         s = text + "<br>";
/*      */       } else {
/*  925 */         s = s + text + "<br>";
/*      */       } 
/*      */     } 
/*      */     
/*  929 */     value = item.getRequiredSpirit();
/*  930 */     if (value > 0) {
/*  931 */       Object[] args = { Integer.valueOf(value) };
/*  932 */       String text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_REQ_SPIRIT", args);
/*      */       
/*  934 */       if (s == null) {
/*  935 */         s = text + "<br>";
/*      */       } else {
/*  937 */         s = s + text + "<br>";
/*      */       } 
/*      */     } 
/*      */     
/*  941 */     return s;
/*      */   }
/*      */   
/*      */   public static String getItemText(GDItem item, ItemInfo info) {
/*  945 */     DetailComposer compItem = createComposer(item.getDBStashItem(), 1, info.itemStats, info.itemSkillBonuses);
/*  946 */     DetailComposer compSkillModifier = createComposerSkillMod(item.getDBStashItem(), 1, info.skillModifiers);
/*  947 */     DetailComposer compPet = createComposer(item.getDBStashItem(), 1, info.petStats);
/*      */     
/*  949 */     DetailComposer compComponent = createComposer(item.getDBStashItem(), 2, info.componentStats, info.componentSkillBonuses);
/*  950 */     DetailComposer compCompletion = createComposer(item.getDBStashItem(), 1, info.completionStats, info.completionSkillBonuses);
/*  951 */     DetailComposer compEnchantment = createComposer(item.getDBStashItem(), 1, info.enchantmentStats);
/*      */     
/*  953 */     boolean fCompletion = false;
/*      */     
/*  955 */     String s = "<html>";
/*  956 */     String part = null;
/*      */     
/*  958 */     part = item.getItemCategory();
/*  959 */     if (part != null) {
/*  960 */       s = s + part + "<br>" + "<br>";
/*      */     }
/*      */     
/*  963 */     if (item.isElixir() || item
/*  964 */       .isFactionBooster() || item
/*  965 */       .isNote() || item
/*  966 */       .isPotion() || item
/*  967 */       .isQuestItem()) {
/*  968 */       s = s + item.getItemDescription() + "<br>";
/*      */     }
/*      */     
/*  971 */     boolean base = false;
/*      */     
/*  973 */     part = getComposerBonuses(compItem, true, false, true);
/*  974 */     if (part != null) {
/*  975 */       s = s + part;
/*      */       
/*  977 */       base = true;
/*      */     } 
/*      */     
/*  980 */     part = getConversion(item.getDBItem());
/*  981 */     if (part != null) {
/*  982 */       s = s + part;
/*      */       
/*  984 */       base = true;
/*      */     } 
/*      */     
/*  987 */     part = getConversion(item.getPrefix());
/*  988 */     if (part != null) {
/*  989 */       s = s + part;
/*      */       
/*  991 */       base = true;
/*      */     } 
/*      */     
/*  994 */     part = getConversion(item.getSuffix());
/*  995 */     if (part != null) {
/*  996 */       s = s + part;
/*      */       
/*  998 */       base = true;
/*      */     } 
/*      */     
/* 1001 */     if (base) s = s + "<br>";
/*      */     
/* 1003 */     part = getComposerBonuses(compItem, false, true, true);
/* 1004 */     if (part != null) s = s + part;
/*      */     
/* 1006 */     for (DBSkillModifier sm : info.skillModifiers) {
/* 1007 */       part = getConversion(sm);
/*      */       
/* 1009 */       if (part != null) {
/* 1010 */         s = s + part;
/*      */       }
/*      */     } 
/*      */     
/* 1014 */     part = getComposerBonuses(compSkillModifier, true, true, false);
/* 1015 */     if (part != null) s = s + part;
/*      */     
/* 1017 */     part = getComposerBonuses(compPet, true, true, true);
/* 1018 */     if (part != null) {
/* 1019 */       String text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_PET_BONUS", null);
/* 1020 */       s = s + "<br>" + text + "<br>" + part;
/*      */     } 
/*      */     
/* 1023 */     if (item.getPlusAllSkills() > 0) {
/* 1024 */       Object[] args = { Integer.valueOf(item.getPlusAllSkills()) };
/* 1025 */       String text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_PLUS_ALLSKILLS", args);
/*      */       
/* 1027 */       s = s + text + "<br>";
/*      */     } 
/*      */     
/* 1030 */     part = getSkills(item.getDBStashItem(), 1, info.itemSkills);
/* 1031 */     if (part != null) {
/* 1032 */       s = s + "<br>" + part;
/*      */     }
/*      */     
/* 1035 */     part = getSkills(item.getDBStashItem(), 1, info.petSkills);
/* 1036 */     if (part != null) {
/* 1037 */       s = s + "<br>" + part;
/*      */     }
/*      */     
/* 1040 */     part = getComposerBonuses(compComponent, true, true, false);
/* 1041 */     if (part != null) {
/* 1042 */       String text = GDColor.HTML_COLOR_COMPONENT + item.getComponentName() + "</font>";
/* 1043 */       s = s + "<br>" + text + "<br>" + part;
/*      */     } 
/*      */     
/* 1046 */     part = getSkills(item.getDBStashItem(), 2, info.componentSkills);
/* 1047 */     if (part != null) {
/* 1048 */       s = s + "<br>" + part;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1053 */     if (!item.isComponent() && item.getComponentName() == null) {
/* 1054 */       part = getComposerBonuses(compCompletion, true, true, true);
/* 1055 */       if (part != null && 
/* 1056 */         !fCompletion) {
/* 1057 */         String text = GDMsgFormatter.format(GDMsgFormatter.rbGD, "TXT_COMPLETION_BONUS", null);
/* 1058 */         text = GDColor.HTML_COLOR_ITEM_TYPE + text + "</font>";
/* 1059 */         s = s + "<br>" + text + "<br>" + part;
/*      */         
/* 1061 */         fCompletion = true;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1077 */     part = getComposerBonuses(compEnchantment, true, true, false);
/* 1078 */     if (part != null) {
/* 1079 */       String text = GDColor.HTML_COLOR_ENCHANTMENT + item.getEnchantmentName() + "</font>";
/* 1080 */       s = s + "<br>" + text + "<br>" + part;
/*      */     } 
/*      */     
/* 1083 */     part = item.getItemSetBonuses();
/* 1084 */     if (part != null) {
/* 1085 */       s = s + "<br>" + part;
/*      */     }
/*      */     
/* 1088 */     part = getItemReqs(item);
/* 1089 */     if (part != null) {
/* 1090 */       s = s + "<br>" + part;
/*      */     }
/*      */     
/* 1093 */     s = s + "</html>";
/*      */     
/* 1095 */     return s;
/*      */   }
/*      */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\description\DetailComposer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */