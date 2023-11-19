/*      */ package org.gdstash.db.criteria;
/*      */ 
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import org.gdstash.db.ItemClass;
/*      */ import org.gdstash.db.SelectionCriteria;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class AbstractItemCombination
/*      */   implements CriteriaCombination, Cloneable
/*      */ {
/*      */   private String itemClass;
/*      */   private String armorClass;
/*      */   private String artifactClass;
/*      */   private String itemRarity;
/*      */   private boolean useArmorClass;
/*      */   private boolean useArtifactClass;
/*      */   private boolean useItemRarity;
/*      */   private boolean useItemName;
/*      */   private boolean useSetItem;
/*      */   private boolean useBonusSkill;
/*      */   private boolean useAllSkills;
/*      */   private boolean useMasteryBonusSkills;
/*      */   private boolean useSkillModifier;
/*      */   private boolean useMasteryModifySkills;
/*      */   private boolean useAllItemSkills;
/*      */   private boolean useItemSkill;
/*      */   private boolean usePrefix;
/*      */   private boolean useSuffix;
/*      */   private boolean useConversionFrom;
/*      */   private boolean useConversionTo;
/*      */   private boolean useSlots;
/*      */   private boolean petBonus;
/*      */   private String itemName;
/*      */   private boolean setItem;
/*      */   private String bonusSkill;
/*      */   private boolean allSkills;
/*      */   private boolean masteryBonusSkills;
/*      */   private String modifiedSkill;
/*      */   private boolean masteryModifySkills;
/*      */   private boolean allItemSkills;
/*      */   private String itemSkill;
/*      */   private String prefixID;
/*      */   private String suffixID;
/*      */   private int levelMin;
/*      */   private int levelMax;
/*      */   private int cunningMax;
/*      */   private int physiqueMax;
/*      */   private int spiritMax;
/*      */   public String dmgConversionFrom;
/*      */   public String dmgConversionTo;
/*      */   private boolean noEnemyOnly;
/*      */   private SelectionCriteria criteria;
/*      */   
/*      */   public AbstractItemCombination() {
/*   74 */     this.itemClass = null;
/*   75 */     this.armorClass = null;
/*   76 */     this.artifactClass = null;
/*   77 */     this.itemRarity = null;
/*      */     
/*   79 */     this.useArmorClass = false;
/*   80 */     this.useArtifactClass = false;
/*   81 */     this.useItemRarity = false;
/*   82 */     this.useItemName = false;
/*   83 */     this.useSetItem = false;
/*   84 */     this.useBonusSkill = false;
/*   85 */     this.useAllSkills = false;
/*   86 */     this.useMasteryBonusSkills = false;
/*   87 */     this.useSkillModifier = false;
/*   88 */     this.useMasteryModifySkills = false;
/*   89 */     this.useAllItemSkills = false;
/*   90 */     this.useItemSkill = false;
/*   91 */     this.usePrefix = false;
/*   92 */     this.useSuffix = false;
/*   93 */     this.useConversionFrom = false;
/*   94 */     this.useConversionTo = false;
/*   95 */     this.useSlots = false;
/*      */     
/*   97 */     this.petBonus = false;
/*      */     
/*   99 */     this.itemName = null;
/*  100 */     this.setItem = false;
/*  101 */     this.levelMin = -1;
/*  102 */     this.levelMax = -1;
/*  103 */     this.cunningMax = -1;
/*  104 */     this.physiqueMax = -1;
/*  105 */     this.spiritMax = -1;
/*  106 */     this.dmgConversionFrom = null;
/*  107 */     this.dmgConversionTo = null;
/*      */     
/*  109 */     this.noEnemyOnly = true;
/*      */     
/*  111 */     this.criteria = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AbstractItemCombination(AbstractItemCombination combo) {
/*  117 */     this.itemClass = combo.itemClass;
/*  118 */     this.armorClass = combo.armorClass;
/*  119 */     this.artifactClass = combo.artifactClass;
/*  120 */     this.itemRarity = combo.itemRarity;
/*      */     
/*  122 */     this.useArmorClass = combo.useArmorClass;
/*  123 */     this.useArtifactClass = combo.useArtifactClass;
/*  124 */     this.useItemRarity = combo.useItemRarity;
/*  125 */     this.useItemName = combo.useItemName;
/*  126 */     this.useSetItem = combo.useSetItem;
/*  127 */     this.useBonusSkill = combo.useBonusSkill;
/*  128 */     this.useAllSkills = combo.useAllSkills;
/*  129 */     this.useMasteryBonusSkills = combo.useMasteryBonusSkills;
/*  130 */     this.useSkillModifier = combo.useSkillModifier;
/*  131 */     this.useMasteryModifySkills = combo.useMasteryModifySkills;
/*  132 */     this.useAllItemSkills = combo.useAllItemSkills;
/*  133 */     this.useItemSkill = combo.useItemSkill;
/*  134 */     this.usePrefix = combo.usePrefix;
/*  135 */     this.useSuffix = combo.useSuffix;
/*  136 */     this.useConversionFrom = combo.useConversionFrom;
/*  137 */     this.useConversionTo = combo.useConversionTo;
/*  138 */     this.useSlots = combo.useSlots;
/*      */     
/*  140 */     this.petBonus = combo.petBonus;
/*      */     
/*  142 */     this.itemName = combo.itemName;
/*  143 */     this.setItem = combo.setItem;
/*  144 */     this.bonusSkill = combo.bonusSkill;
/*  145 */     this.allSkills = combo.allSkills;
/*  146 */     this.masteryBonusSkills = combo.masteryBonusSkills;
/*  147 */     this.modifiedSkill = combo.modifiedSkill;
/*  148 */     this.masteryModifySkills = combo.masteryModifySkills;
/*  149 */     this.allItemSkills = combo.allItemSkills;
/*  150 */     this.itemSkill = combo.itemSkill;
/*  151 */     this.prefixID = combo.prefixID;
/*  152 */     this.suffixID = combo.suffixID;
/*  153 */     this.levelMin = combo.levelMin;
/*  154 */     this.levelMax = combo.levelMax;
/*  155 */     this.cunningMax = combo.cunningMax;
/*  156 */     this.physiqueMax = combo.physiqueMax;
/*  157 */     this.spiritMax = combo.spiritMax;
/*  158 */     this.dmgConversionFrom = combo.dmgConversionFrom;
/*  159 */     this.dmgConversionTo = combo.dmgConversionTo;
/*      */     
/*  161 */     this.noEnemyOnly = combo.noEnemyOnly;
/*      */     
/*  163 */     this.criteria = combo.criteria;
/*      */   }
/*      */   
/*      */   public AbstractItemCombination(SelectionCriteria criteria) {
/*  167 */     this();
/*      */     
/*  169 */     setItemName(criteria.itemName);
/*  170 */     setBonusSkill(criteria.bonusSkill);
/*  171 */     setSkillModifier(criteria.modifiedSkill);
/*  172 */     setItemSkill(criteria.itemSkill);
/*  173 */     setPrefixID(criteria.prefixID);
/*  174 */     setSuffixID(criteria.suffixID);
/*  175 */     this.setItem = criteria.setItem;
/*  176 */     this.allSkills = criteria.allSkills;
/*  177 */     this.masteryBonusSkills = criteria.masteryBonusSkills;
/*  178 */     this.allItemSkills = criteria.allItemSkills;
/*      */     
/*  180 */     this.levelMin = criteria.levelMin;
/*  181 */     this.levelMax = criteria.levelMax;
/*  182 */     this.cunningMax = criteria.cunningMax;
/*  183 */     this.physiqueMax = criteria.physiqueMax;
/*  184 */     this.spiritMax = criteria.spiritMax;
/*  185 */     this.dmgConversionFrom = criteria.dmgConversionFrom;
/*  186 */     this.dmgConversionTo = criteria.dmgConversionTo;
/*  187 */     this.noEnemyOnly = criteria.noEnemyOnly;
/*      */     
/*  189 */     this.petBonus = criteria.petBonus;
/*      */     
/*  191 */     this.criteria = criteria;
/*      */   }
/*      */ 
/*      */   
/*      */   public AbstractItemCombination clone() {
/*  196 */     AbstractItemCombination combo = new AbstractItemCombination(this);
/*      */     
/*  198 */     return combo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getItemClass() {
/*  206 */     return this.itemClass;
/*      */   }
/*      */   
/*      */   public String getArmorClass() {
/*  210 */     return this.armorClass;
/*      */   }
/*      */   
/*      */   public String getArtifactClass() {
/*  214 */     return this.artifactClass;
/*      */   }
/*      */   
/*      */   public String getItemRarity() {
/*  218 */     return this.itemRarity;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean usesArmorClass() {
/*  223 */     return this.useArmorClass;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean usesArtifactClass() {
/*  228 */     return this.useArtifactClass;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean usesItemRarity() {
/*  233 */     return this.useItemRarity;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean usesItemName() {
/*  238 */     return this.useItemName;
/*      */   }
/*      */   
/*      */   public boolean usesSetItem() {
/*  242 */     return this.useSetItem;
/*      */   }
/*      */   
/*      */   public boolean usesBonusSkill() {
/*  246 */     return this.useBonusSkill;
/*      */   }
/*      */   
/*      */   public boolean usesAllSkills() {
/*  250 */     return this.useAllSkills;
/*      */   }
/*      */   
/*      */   public boolean usesMasteryBonusSkills() {
/*  254 */     return this.useMasteryBonusSkills;
/*      */   }
/*      */   
/*      */   public boolean usesSkillModifier() {
/*  258 */     return this.useSkillModifier;
/*      */   }
/*      */   
/*      */   public boolean usesMasteryModifySkills() {
/*  262 */     return this.useMasteryModifySkills;
/*      */   }
/*      */   
/*      */   public boolean usesAllItemSkills() {
/*  266 */     return this.useAllItemSkills;
/*      */   }
/*      */   
/*      */   public boolean usesItemSkill() {
/*  270 */     return this.useItemSkill;
/*      */   }
/*      */   
/*      */   public boolean usesPrefix() {
/*  274 */     return this.usePrefix;
/*      */   }
/*      */   
/*      */   public boolean usesSuffix() {
/*  278 */     return this.useSuffix;
/*      */   }
/*      */   
/*      */   public boolean usesConversionFrom() {
/*  282 */     return this.useConversionFrom;
/*      */   }
/*      */   
/*      */   public boolean usesConversionTo() {
/*  286 */     return this.useConversionTo;
/*      */   }
/*      */   
/*      */   public boolean usesSlots() {
/*  290 */     return this.useSlots;
/*      */   }
/*      */   
/*      */   public boolean usesPetBonus() {
/*  294 */     return this.petBonus;
/*      */   }
/*      */   
/*      */   public String getItemName() {
/*  298 */     return this.itemName;
/*      */   }
/*      */   
/*      */   public boolean isSetItem() {
/*  302 */     return this.setItem;
/*      */   }
/*      */   
/*      */   public String getBonusSkillID() {
/*  306 */     return this.bonusSkill;
/*      */   }
/*      */   
/*      */   public boolean isAllSkills() {
/*  310 */     return this.allSkills;
/*      */   }
/*      */   
/*      */   public boolean isMasteryBonusSkills() {
/*  314 */     return this.masteryBonusSkills;
/*      */   }
/*      */   
/*      */   public String getModifiedSkillID() {
/*  318 */     return this.modifiedSkill;
/*      */   }
/*      */   
/*      */   public boolean isMasteryModifySkills() {
/*  322 */     return this.masteryModifySkills;
/*      */   }
/*      */   
/*      */   public boolean isAllItemSkills() {
/*  326 */     return this.allItemSkills;
/*      */   }
/*      */   
/*      */   public String getItemSkillID() {
/*  330 */     return this.itemSkill;
/*      */   }
/*      */   
/*      */   public String getPrefixID() {
/*  334 */     return this.prefixID;
/*      */   }
/*      */   
/*      */   public String getSuffixID() {
/*  338 */     return this.suffixID;
/*      */   }
/*      */   
/*      */   public int getMinLevel() {
/*  342 */     return this.levelMin;
/*      */   }
/*      */   
/*      */   public int getMaxLevel() {
/*  346 */     return this.levelMax;
/*      */   }
/*      */   
/*      */   public int getMaxCunning() {
/*  350 */     return this.cunningMax;
/*      */   }
/*      */   
/*      */   public int getMaxPhysique() {
/*  354 */     return this.physiqueMax;
/*      */   }
/*      */   
/*      */   public int getMaxSpirit() {
/*  358 */     return this.spiritMax;
/*      */   }
/*      */   
/*      */   public String getDamageConvertedFrom() {
/*  362 */     return this.dmgConversionFrom;
/*      */   }
/*      */   
/*      */   public String getDamageConvertedTo() {
/*  366 */     return this.dmgConversionTo;
/*      */   }
/*      */   
/*      */   public boolean isNoEnemyOnly() {
/*  370 */     return this.noEnemyOnly;
/*      */   }
/*      */   
/*      */   public List<String> getItemRarities() {
/*  374 */     if (this.criteria == null) return new LinkedList<>();
/*      */     
/*  376 */     return this.criteria.itemRarity;
/*      */   }
/*      */   
/*      */   public List<String> getArmorClasses() {
/*  380 */     if (this.criteria == null) return new LinkedList<>();
/*      */     
/*  382 */     return this.criteria.armorClass;
/*      */   }
/*      */   
/*      */   public List<String> getArtifactClasses() {
/*  386 */     if (this.criteria == null) return new LinkedList<>();
/*      */     
/*  388 */     return this.criteria.artifactClass;
/*      */   }
/*      */ 
/*      */   
/*      */   private void init() {
/*  393 */     this.useArmorClass = false;
/*  394 */     if (!this.criteria.armorClass.isEmpty() && 
/*  395 */       isArmor()) {
/*  396 */       this.useArmorClass = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  402 */     this.useArtifactClass = false;
/*  403 */     if (!this.criteria.artifactClass.isEmpty() && 
/*  404 */       isArtifact()) {
/*  405 */       this.useArtifactClass = true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  410 */     this.useItemRarity = false;
/*  411 */     if (!this.criteria.itemRarity.isEmpty() && 
/*  412 */       ItemClass.hasRarity(this.itemClass)) {
/*  413 */       this.useItemRarity = true;
/*      */     }
/*      */ 
/*      */     
/*  417 */     this.useItemName = false;
/*  418 */     if (this.criteria.itemName != null && 
/*  419 */       !this.criteria.itemName.isEmpty()) this.useItemName = true;
/*      */ 
/*      */     
/*  422 */     this.useSetItem = this.criteria.setItem;
/*      */     
/*  424 */     this.useBonusSkill = false;
/*  425 */     if (this.criteria.bonusSkill != null && 
/*  426 */       !this.criteria.bonusSkill.isEmpty() && 
/*  427 */       !this.criteria.masteryBonusSkills) this.useBonusSkill = true;
/*      */ 
/*      */ 
/*      */     
/*  431 */     this.useAllSkills = this.criteria.allSkills;
/*      */     
/*  433 */     this.useMasteryBonusSkills = this.criteria.masteryBonusSkills;
/*      */     
/*  435 */     this.useSkillModifier = false;
/*  436 */     if (this.criteria.modifiedSkill != null && 
/*  437 */       !this.criteria.modifiedSkill.isEmpty() && 
/*  438 */       !this.criteria.masteryModifySkills) this.useSkillModifier = true;
/*      */ 
/*      */ 
/*      */     
/*  442 */     this.useMasteryModifySkills = this.criteria.masteryModifySkills;
/*      */     
/*  444 */     this.useAllItemSkills = this.criteria.allItemSkills;
/*      */     
/*  446 */     this.useItemSkill = false;
/*  447 */     if (this.criteria.itemSkill != null && 
/*  448 */       !this.criteria.itemSkill.isEmpty()) this.useItemSkill = true;
/*      */ 
/*      */     
/*  451 */     this.usePrefix = false;
/*  452 */     if (this.criteria.prefixID != null && 
/*  453 */       !this.criteria.prefixID.isEmpty()) this.usePrefix = true;
/*      */ 
/*      */     
/*  456 */     this.useSuffix = false;
/*  457 */     if (this.criteria.suffixID != null && 
/*  458 */       !this.criteria.suffixID.isEmpty()) this.useSuffix = true;
/*      */ 
/*      */     
/*  461 */     this.useConversionFrom = false;
/*  462 */     if (this.criteria.dmgConversionFrom != null && 
/*  463 */       !this.criteria.dmgConversionFrom.isEmpty()) this.useConversionFrom = true;
/*      */ 
/*      */     
/*  466 */     this.useConversionTo = false;
/*  467 */     if (this.criteria.dmgConversionTo != null && 
/*  468 */       !this.criteria.dmgConversionTo.isEmpty()) this.useConversionTo = true;
/*      */ 
/*      */     
/*  471 */     this.useSlots = false;
/*  472 */     if (hasSlots()) {
/*  473 */       this.useSlots = true;
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean isArmor() {
/*  478 */     if (this.itemClass == null) return false;
/*      */     
/*  480 */     if (this.itemClass.equals("ArmorProtective_Head") || this.itemClass
/*  481 */       .equals("ArmorProtective_Shoulders") || this.itemClass
/*  482 */       .equals("ArmorProtective_Chest") || this.itemClass
/*  483 */       .equals("ArmorProtective_Hands") || this.itemClass
/*  484 */       .equals("ArmorProtective_Legs") || this.itemClass
/*  485 */       .equals("ArmorProtective_Feet")) {
/*  486 */       return true;
/*      */     }
/*      */     
/*  489 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isArtifact() {
/*  493 */     if (this.itemClass == null) return false;
/*      */     
/*  495 */     if (this.itemClass.equals("ItemArtifact")) {
/*  496 */       return true;
/*      */     }
/*      */     
/*  499 */     return false;
/*      */   }
/*      */   
/*      */   private boolean hasSlots() {
/*  503 */     if (this.itemClass == null) return false;
/*      */     
/*  505 */     if (this.itemClass.equals("ItemRelic") || this.itemClass
/*  506 */       .equals("ItemEnchantment")) {
/*  507 */       return true;
/*      */     }
/*      */     
/*  510 */     return false;
/*      */   }
/*      */   
/*      */   public static List<String> wrapString(ResultSet rs, int pos) throws SQLException {
/*  514 */     LinkedList<String> list = new LinkedList<>();
/*      */     
/*  516 */     String executedQuery = rs.getStatement().toString();
/*  517 */     while (rs.next()) {
/*  518 */       String s = rs.getString(pos);
/*      */       
/*  520 */       if (s != null && !s.isEmpty()) list.add(s);
/*      */     
/*      */     } 
/*  523 */     return list;
/*      */   }
/*      */   
/*      */   public static List<Integer> wrapInteger(ResultSet rs, int pos) throws SQLException {
/*  527 */     LinkedList<Integer> list = new LinkedList<>();
/*      */     
/*  529 */     while (rs.next()) {
/*  530 */       int i = rs.getInt(pos);
/*      */       
/*  532 */       Integer iI = Integer.valueOf(i);
/*      */       
/*  534 */       list.add(iI);
/*      */     } 
/*      */     
/*  537 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String determineSlotConditions(String prefix) {
/*  545 */     String command = "";
/*      */     
/*  547 */     if (!this.useSlots) return command;
/*      */     
/*  549 */     boolean firstSlot = true;
/*      */     
/*  551 */     if (this.criteria.slotAxe1H) {
/*  552 */       if (firstSlot) {
/*  553 */         firstSlot = false;
/*      */         
/*  555 */         command = command + " AND ( ";
/*      */       } else {
/*  557 */         command = command + " OR ";
/*      */       } 
/*  559 */       if (prefix == null) {
/*  560 */         command = command + "SLOT_AXE_1H = true";
/*      */       } else {
/*  562 */         command = command + prefix + "SLOT_AXE_1H = true";
/*      */       } 
/*      */     } 
/*      */     
/*  566 */     if (this.criteria.slotAxe2H) {
/*  567 */       if (firstSlot) {
/*  568 */         firstSlot = false;
/*      */         
/*  570 */         command = command + " AND ( ";
/*      */       } else {
/*  572 */         command = command + " OR ";
/*      */       } 
/*  574 */       if (prefix == null) {
/*  575 */         command = command + "SLOT_AXE_2H = true";
/*      */       } else {
/*  577 */         command = command + prefix + "SLOT_AXE_2H = true";
/*      */       } 
/*      */     } 
/*      */     
/*  581 */     if (this.criteria.slotDagger1H) {
/*  582 */       if (firstSlot) {
/*  583 */         firstSlot = false;
/*      */         
/*  585 */         command = command + " AND ( ";
/*      */       } else {
/*  587 */         command = command + " OR ";
/*      */       } 
/*  589 */       if (prefix == null) {
/*  590 */         command = command + "SLOT_DAGGER_1H = true";
/*      */       } else {
/*  592 */         command = command + prefix + "SLOT_DAGGER_1H = true";
/*      */       } 
/*      */     } 
/*      */     
/*  596 */     if (this.criteria.slotMace1H) {
/*  597 */       if (firstSlot) {
/*  598 */         firstSlot = false;
/*      */         
/*  600 */         command = command + " AND ( ";
/*      */       } else {
/*  602 */         command = command + " OR ";
/*      */       } 
/*  604 */       if (prefix == null) {
/*  605 */         command = command + "SLOT_MACE_1H = true";
/*      */       } else {
/*  607 */         command = command + prefix + "SLOT_MACE_1H = true";
/*      */       } 
/*      */     } 
/*      */     
/*  611 */     if (this.criteria.slotMace2H) {
/*  612 */       if (firstSlot) {
/*  613 */         firstSlot = false;
/*      */         
/*  615 */         command = command + " AND ( ";
/*      */       } else {
/*  617 */         command = command + " OR ";
/*      */       } 
/*  619 */       if (prefix == null) {
/*  620 */         command = command + "SLOT_MACE_2H = true";
/*      */       } else {
/*  622 */         command = command + prefix + "SLOT_MACE_2H = true";
/*      */       } 
/*      */     } 
/*      */     
/*  626 */     if (this.criteria.slotScepter1H) {
/*  627 */       if (firstSlot) {
/*  628 */         firstSlot = false;
/*      */         
/*  630 */         command = command + " AND ( ";
/*      */       } else {
/*  632 */         command = command + " OR ";
/*      */       } 
/*  634 */       if (prefix == null) {
/*  635 */         command = command + "SLOT_SCEPTER_1H = true";
/*      */       } else {
/*  637 */         command = command + prefix + "SLOT_SCEPTER_1H = true";
/*      */       } 
/*      */     } 
/*      */     
/*  641 */     if (this.criteria.slotSpear1H) {
/*  642 */       if (firstSlot) {
/*  643 */         firstSlot = false;
/*      */         
/*  645 */         command = command + " AND ( ";
/*      */       } else {
/*  647 */         command = command + " OR ";
/*      */       } 
/*  649 */       if (prefix == null) {
/*  650 */         command = command + "SLOT_SPEAR_1H = true";
/*      */       } else {
/*  652 */         command = command + prefix + "SLOT_SPEAR_1H = true";
/*      */       } 
/*      */     } 
/*      */     
/*  656 */     if (this.criteria.slotStaff2H) {
/*  657 */       if (firstSlot) {
/*  658 */         firstSlot = false;
/*      */         
/*  660 */         command = command + " AND ( ";
/*      */       } else {
/*  662 */         command = command + " OR ";
/*      */       } 
/*  664 */       if (prefix == null) {
/*  665 */         command = command + "SLOT_STAFF_2H = true";
/*      */       } else {
/*  667 */         command = command + prefix + "SLOT_STAFF_2H = true";
/*      */       } 
/*      */     } 
/*      */     
/*  671 */     if (this.criteria.slotSword1H) {
/*  672 */       if (firstSlot) {
/*  673 */         firstSlot = false;
/*      */         
/*  675 */         command = command + " AND ( ";
/*      */       } else {
/*  677 */         command = command + " OR ";
/*      */       } 
/*  679 */       if (prefix == null) {
/*  680 */         command = command + "SLOT_SWORD_1H = true";
/*      */       } else {
/*  682 */         command = command + prefix + "SLOT_SWORD_1H = true";
/*      */       } 
/*      */     } 
/*      */     
/*  686 */     if (this.criteria.slotSword2H) {
/*  687 */       if (firstSlot) {
/*  688 */         firstSlot = false;
/*      */         
/*  690 */         command = command + " AND ( ";
/*      */       } else {
/*  692 */         command = command + " OR ";
/*      */       } 
/*  694 */       if (prefix == null) {
/*  695 */         command = command + "SLOT_SWORD_2H = true";
/*      */       } else {
/*  697 */         command = command + prefix + "SLOT_SWORD_2H = true";
/*      */       } 
/*      */     } 
/*      */     
/*  701 */     if (this.criteria.slotRanged1H) {
/*  702 */       if (firstSlot) {
/*  703 */         firstSlot = false;
/*      */         
/*  705 */         command = command + " AND ( ";
/*      */       } else {
/*  707 */         command = command + " OR ";
/*      */       } 
/*  709 */       if (prefix == null) {
/*  710 */         command = command + "SLOT_RANGED_1H = true";
/*      */       } else {
/*  712 */         command = command + prefix + "SLOT_RANGED_1H = true";
/*      */       } 
/*      */     } 
/*      */     
/*  716 */     if (this.criteria.slotRanged2H) {
/*  717 */       if (firstSlot) {
/*  718 */         firstSlot = false;
/*      */         
/*  720 */         command = command + " AND ( ";
/*      */       } else {
/*  722 */         command = command + " OR ";
/*      */       } 
/*  724 */       if (prefix == null) {
/*  725 */         command = command + "SLOT_RANGED_2H = true";
/*      */       } else {
/*  727 */         command = command + prefix + "SLOT_RANGED_2H = true";
/*      */       } 
/*      */     } 
/*      */     
/*  731 */     if (this.criteria.slotShield) {
/*  732 */       if (firstSlot) {
/*  733 */         firstSlot = false;
/*      */         
/*  735 */         command = command + " AND ( ";
/*      */       } else {
/*  737 */         command = command + " OR ";
/*      */       } 
/*  739 */       if (prefix == null) {
/*  740 */         command = command + "SLOT_SHIELD = true";
/*      */       } else {
/*  742 */         command = command + prefix + "SLOT_SHIELD = true";
/*      */       } 
/*      */     } 
/*      */     
/*  746 */     if (this.criteria.slotOffhand) {
/*  747 */       if (firstSlot) {
/*  748 */         firstSlot = false;
/*      */         
/*  750 */         command = command + " AND ( ";
/*      */       } else {
/*  752 */         command = command + " OR ";
/*      */       } 
/*  754 */       if (prefix == null) {
/*  755 */         command = command + "SLOT_OFFHAND = true";
/*      */       } else {
/*  757 */         command = command + prefix + "SLOT_OFFHAND = true";
/*      */       } 
/*      */     } 
/*      */     
/*  761 */     if (this.criteria.slotAmulet) {
/*  762 */       if (firstSlot) {
/*  763 */         firstSlot = false;
/*      */         
/*  765 */         command = command + " AND ( ";
/*      */       } else {
/*  767 */         command = command + " OR ";
/*      */       } 
/*  769 */       if (prefix == null) {
/*  770 */         command = command + "SLOT_AMULET = true";
/*      */       } else {
/*  772 */         command = command + prefix + "SLOT_AMULET = true";
/*      */       } 
/*      */     } 
/*      */     
/*  776 */     if (this.criteria.slotBelt) {
/*  777 */       if (firstSlot) {
/*  778 */         firstSlot = false;
/*      */         
/*  780 */         command = command + " AND ( ";
/*      */       } else {
/*  782 */         command = command + " OR ";
/*      */       } 
/*  784 */       if (prefix == null) {
/*  785 */         command = command + "SLOT_BELT = true";
/*      */       } else {
/*  787 */         command = command + prefix + "SLOT_BELT = true";
/*      */       } 
/*      */     } 
/*      */     
/*  791 */     if (this.criteria.slotMedal) {
/*  792 */       if (firstSlot) {
/*  793 */         firstSlot = false;
/*      */         
/*  795 */         command = command + " AND ( ";
/*      */       } else {
/*  797 */         command = command + " OR ";
/*      */       } 
/*  799 */       if (prefix == null) {
/*  800 */         command = command + "SLOT_MEDAL = true";
/*      */       } else {
/*  802 */         command = command + prefix + "SLOT_MEDAL = true";
/*      */       } 
/*      */     } 
/*      */     
/*  806 */     if (this.criteria.slotRing) {
/*  807 */       if (firstSlot) {
/*  808 */         firstSlot = false;
/*      */         
/*  810 */         command = command + " AND ( ";
/*      */       } else {
/*  812 */         command = command + " OR ";
/*      */       } 
/*  814 */       if (prefix == null) {
/*  815 */         command = command + "SLOT_RING = true";
/*      */       } else {
/*  817 */         command = command + prefix + "SLOT_RING = true";
/*      */       } 
/*      */     } 
/*      */     
/*  821 */     if (this.criteria.slotHead) {
/*  822 */       if (firstSlot) {
/*  823 */         firstSlot = false;
/*      */         
/*  825 */         command = command + " AND ( ";
/*      */       } else {
/*  827 */         command = command + " OR ";
/*      */       } 
/*  829 */       if (prefix == null) {
/*  830 */         command = command + "SLOT_HEAD = true";
/*      */       } else {
/*  832 */         command = command + prefix + "SLOT_HEAD = true";
/*      */       } 
/*      */     } 
/*      */     
/*  836 */     if (this.criteria.slotShoulders) {
/*  837 */       if (firstSlot) {
/*  838 */         firstSlot = false;
/*      */         
/*  840 */         command = command + " AND ( ";
/*      */       } else {
/*  842 */         command = command + " OR ";
/*      */       } 
/*  844 */       if (prefix == null) {
/*  845 */         command = command + "SLOT_SHOULDERS = true";
/*      */       } else {
/*  847 */         command = command + prefix + "SLOT_SHOULDERS = true";
/*      */       } 
/*      */     } 
/*      */     
/*  851 */     if (this.criteria.slotChest) {
/*  852 */       if (firstSlot) {
/*  853 */         firstSlot = false;
/*      */         
/*  855 */         command = command + " AND ( ";
/*      */       } else {
/*  857 */         command = command + " OR ";
/*      */       } 
/*  859 */       if (prefix == null) {
/*  860 */         command = command + "SLOT_CHEST = true";
/*      */       } else {
/*  862 */         command = command + prefix + "SLOT_CHEST = true";
/*      */       } 
/*      */     } 
/*      */     
/*  866 */     if (this.criteria.slotHands) {
/*  867 */       if (firstSlot) {
/*  868 */         firstSlot = false;
/*      */         
/*  870 */         command = command + " AND ( ";
/*      */       } else {
/*  872 */         command = command + " OR ";
/*      */       } 
/*  874 */       if (prefix == null) {
/*  875 */         command = command + "SLOT_HANDS = true";
/*      */       } else {
/*  877 */         command = command + prefix + "SLOT_HANDS = true";
/*      */       } 
/*      */     } 
/*      */     
/*  881 */     if (this.criteria.slotLegs) {
/*  882 */       if (firstSlot) {
/*  883 */         firstSlot = false;
/*      */         
/*  885 */         command = command + " AND ( ";
/*      */       } else {
/*  887 */         command = command + " OR ";
/*      */       } 
/*  889 */       if (prefix == null) {
/*  890 */         command = command + "SLOT_LEGS = true";
/*      */       } else {
/*  892 */         command = command + prefix + "SLOT_LEGS = true";
/*      */       } 
/*      */     } 
/*      */     
/*  896 */     if (this.criteria.slotFeet) {
/*  897 */       if (firstSlot) {
/*  898 */         firstSlot = false;
/*      */         
/*  900 */         command = command + " AND ( ";
/*      */       } else {
/*  902 */         command = command + " OR ";
/*      */       } 
/*  904 */       if (prefix == null) {
/*  905 */         command = command + "SLOT_FEET = true";
/*      */       } else {
/*  907 */         command = command + prefix + "SLOT_FEET = true";
/*      */       } 
/*      */     } 
/*      */     
/*  911 */     if (!firstSlot) command = command + " )";
/*      */     
/*  913 */     return command;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemClass(String itemClass) {
/*  922 */     this.itemClass = itemClass;
/*      */     
/*  924 */     if (itemClass != null && itemClass.isEmpty()) this.itemClass = null;
/*      */     
/*  926 */     init();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setArmorClass(String armorClass) {
/*  931 */     this.armorClass = armorClass;
/*      */     
/*  933 */     if (armorClass != null && armorClass.isEmpty()) this.armorClass = null;
/*      */   
/*      */   }
/*      */   
/*      */   public void setArtifactClass(String artifactClass) {
/*  938 */     this.artifactClass = artifactClass;
/*      */     
/*  940 */     if (artifactClass != null && artifactClass.isEmpty()) this.artifactClass = null;
/*      */   
/*      */   }
/*      */   
/*      */   public void setItemRarity(String itemRarity) {
/*  945 */     this.itemRarity = itemRarity;
/*      */     
/*  947 */     if (itemRarity != null && itemRarity.isEmpty()) this.itemRarity = null;
/*      */   
/*      */   }
/*      */   
/*      */   public void setItemName(String itemName) {
/*  952 */     this.itemName = itemName;
/*      */     
/*  954 */     if (itemName != null && itemName.isEmpty()) this.itemName = null; 
/*      */   }
/*      */   
/*      */   public void setBonusSkill(String bonusSkill) {
/*  958 */     this.bonusSkill = bonusSkill;
/*      */     
/*  960 */     if (bonusSkill != null && bonusSkill.isEmpty()) this.bonusSkill = null; 
/*      */   }
/*      */   
/*      */   public void setSkillModifier(String modifiedSkill) {
/*  964 */     this.modifiedSkill = modifiedSkill;
/*      */     
/*  966 */     if (modifiedSkill != null && modifiedSkill.isEmpty()) this.modifiedSkill = null; 
/*      */   }
/*      */   
/*      */   public void setItemSkill(String itemSkill) {
/*  970 */     this.itemSkill = itemSkill;
/*      */     
/*  972 */     if (itemSkill != null && itemSkill.isEmpty()) this.itemSkill = null; 
/*      */   }
/*      */   
/*      */   public void setPrefixID(String prefixID) {
/*  976 */     this.prefixID = prefixID;
/*      */     
/*  978 */     if (prefixID != null && prefixID.isEmpty()) this.prefixID = null; 
/*      */   }
/*      */   
/*      */   public void setSuffixID(String suffixID) {
/*  982 */     this.suffixID = suffixID;
/*      */     
/*  984 */     if (suffixID != null && suffixID.isEmpty()) this.suffixID = null;
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SelectionCriteria getCriteria() {
/*  993 */     return this.criteria;
/*      */   }
/*      */ 
/*      */   
/*      */   public String determineLevel1Statement(CriteriaCombination.Soulbound soulbound, CriteriaCombination.SC_HC schc, boolean isHardcore, String charName) {
/*  998 */     String command = "SELECT I.ITEM_ID FROM GD_ITEM I";
/*      */     
/* 1000 */     boolean statFilled = !(getCriteria()).statInfos.isEmpty();
/*      */     
/* 1002 */     if (getCriteria().isSkillBonusUsed()) {
/* 1003 */       command = command + ", " + "GD_ITEM_SKILLS" + " ISB, " + "GD_ITEM_SKILL_LEVEL" + " ISBL";
/*      */     }
/*      */     
/* 1006 */     if (getCriteria().isSkillModifierUsed()) {
/* 1007 */       command = command + ", " + "GD_ITEM_SKILLMODIFIER" + " ISM";
/*      */     }
/* 1009 */     if (statFilled) {
/* 1010 */       command = command + ", " + "GD_ITEM_STAT" + " IST";
/*      */     }
/*      */     
/* 1013 */     command = command + " WHERE ";
/*      */     
/* 1015 */     if (getCriteria().isSkillBonusUsed()) {
/* 1016 */       command = command + "I.ITEM_ID = ISB.ITEM_ID AND I.ITEM_ID = ISBL.ITEM_ID AND ISB.TYPE = ISBL.TYPE AND ISB.INDEX = ISBL.INDEX AND ";
/*      */     }
/*      */     
/* 1019 */     if (getCriteria().isSkillModifierUsed()) {
/* 1020 */       command = command + "I.ITEM_ID = ISM.ITEM_ID AND ";
/*      */     }
/* 1022 */     if (statFilled) {
/* 1023 */       command = command + "I.ITEM_ID = IST.ITEM_ID AND ";
/*      */     }
/*      */     
/* 1026 */     command = command + "I.ITEM_CLASS = ?";
/*      */     
/* 1028 */     if (usesItemRarity()) command = command + " AND I.RARITY = ?"; 
/* 1029 */     if (usesArmorClass()) command = command + " AND I.ARMOR_CLASS = ?"; 
/* 1030 */     if (usesArtifactClass()) command = command + " AND I.ARTIFACT_CLASS = ?"; 
/* 1031 */     if (usesItemName()) command = command + " AND UPPER(I.NAME_FULL) LIKE ?"; 
/* 1032 */     if (usesSetItem()) command = command + " AND I.SET_ID IS NOT NULL"; 
/* 1033 */     if (usesBonusSkill()) command = command + " AND ISB.BONUS_ENTITY = ? AND ISBL.BONUS_VALUE > 0"; 
/* 1034 */     if (usesMasteryBonusSkills()) command = command + " AND ISB.MASTERY_ID = ? AND ISBL.BONUS_VALUE > 0"; 
/* 1035 */     if (usesAllSkills()) command = command + " AND I.PLUS_ALLSKILLS > 0"; 
/* 1036 */     if (usesSkillModifier()) command = command + " AND ISM.SKILL_ID = ?"; 
/* 1037 */     if (usesMasteryModifySkills()) command = command + " AND ISM.MASTERY_ID = ?"; 
/* 1038 */     if (usesAllItemSkills()) command = command + " AND I.ITEM_SKILL_ID IS NOT NULL"; 
/* 1039 */     if (usesItemSkill()) command = command + " AND I.ITEM_SKILL_ID = ?"; 
/* 1040 */     if (getMinLevel() != -1) command = command + " AND I.REQ_LEVEL >= ?"; 
/* 1041 */     if (getMaxLevel() != -1) command = command + " AND I.REQ_LEVEL <= ?"; 
/* 1042 */     if (getMaxCunning() != -1) command = command + " AND I.REQ_DEX <= ?"; 
/* 1043 */     if (getMaxPhysique() != -1) command = command + " AND I.REQ_STR <= ?"; 
/* 1044 */     if (getMaxSpirit() != -1) command = command + " AND I.REQ_INT <= ?"; 
/* 1045 */     if (usesPetBonus()) command = command + " AND I.PET_BONUS_SKILL_ID IS NOT NULL"; 
/* 1046 */     if (usesConversionFrom()) {
/* 1047 */       if (usesConversionTo()) {
/* 1048 */         command = command + " AND (( I.CONVERT_IN = ? AND I.CONVERT_OUT = ? ) OR ( I.CONVERT_IN_2 = ? AND I.CONVERT_OUT_2 = ? ))";
/*      */       } else {
/* 1050 */         command = command + " AND (( I.CONVERT_IN = ? ) OR ( I.CONVERT_IN_2 = ? ))";
/*      */       }
/*      */     
/* 1053 */     } else if (usesConversionTo()) {
/* 1054 */       command = command + " AND (( I.CONVERT_OUT = ? ) OR ( I.CONVERT_OUT_2 = ? ))";
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1059 */     if (isNoEnemyOnly()) command = command + " AND I.ENEMY_ONLY = ?";
/*      */     
/* 1061 */     command = command + determineSlotConditions("I.");
/*      */     
/* 1063 */     return command;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<CriteriaCombination> createLevel1Combinations(CriteriaCombination cc) {
/* 1068 */     CriteriaCombination combo = cc.clone();
/*      */     
/* 1070 */     List<CriteriaCombination> list = new LinkedList<>();
/*      */     
/* 1072 */     for (String itemClass : (getCriteria()).itemClass) {
/* 1073 */       combo.setItemClass(itemClass);
/*      */       
/* 1075 */       boolean found = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1081 */       if (combo.usesItemRarity()) {
/* 1082 */         for (String itemRarity : getItemRarities()) {
/* 1083 */           combo.setItemRarity(itemRarity);
/*      */           
/* 1085 */           if (combo.usesArmorClass()) {
/* 1086 */             for (String armorClass : getArmorClasses()) {
/* 1087 */               combo.setArmorClass(armorClass);
/*      */ 
/*      */               
/* 1090 */               list.add(combo.clone());
/*      */             } 
/*      */             
/* 1093 */             found = true;
/*      */           } 
/*      */           
/* 1096 */           if (!found)
/*      */           {
/* 1098 */             list.add(combo.clone()); } 
/*      */         } 
/*      */         continue;
/*      */       } 
/* 1102 */       if (combo.usesArmorClass()) {
/* 1103 */         for (String armorClass : getArmorClasses()) {
/* 1104 */           combo.setArmorClass(armorClass);
/*      */ 
/*      */           
/* 1107 */           list.add(combo.clone());
/*      */         } 
/*      */         
/* 1110 */         found = true;
/*      */       } 
/*      */       
/* 1113 */       if (combo.usesArtifactClass()) {
/* 1114 */         for (String artifactClass : getArtifactClasses()) {
/* 1115 */           combo.setArtifactClass(artifactClass);
/*      */ 
/*      */           
/* 1118 */           list.add(combo.clone());
/*      */         } 
/*      */         
/* 1121 */         found = true;
/*      */       } 
/*      */       
/* 1124 */       if (!found)
/*      */       {
/* 1126 */         list.add(combo.clone());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1131 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public int fillLevel1Statement(PreparedStatement ps) throws SQLException {
/* 1136 */     ps.clearParameters();
/*      */     
/* 1138 */     int nextPos = 1;
/*      */     
/* 1140 */     ps.setString(nextPos, getItemClass());
/*      */     
/* 1142 */     nextPos++;
/*      */     
/* 1144 */     if (usesItemRarity()) {
/* 1145 */       ps.setString(nextPos, getItemRarity());
/*      */       
/* 1147 */       nextPos++;
/*      */     } 
/*      */     
/* 1150 */     if (usesArmorClass()) {
/* 1151 */       ps.setString(nextPos, getArmorClass());
/*      */       
/* 1153 */       nextPos++;
/*      */     } 
/*      */     
/* 1156 */     if (usesArtifactClass()) {
/* 1157 */       ps.setString(nextPos, getArtifactClass());
/*      */       
/* 1159 */       nextPos++;
/*      */     } 
/*      */     
/* 1162 */     if (usesItemName()) {
/* 1163 */       ps.setString(nextPos, getItemName().toUpperCase(GDMsgFormatter.locale));
/*      */       
/* 1165 */       nextPos++;
/*      */     } 
/*      */     
/* 1168 */     if (usesBonusSkill()) {
/* 1169 */       ps.setString(nextPos, getBonusSkillID());
/*      */       
/* 1171 */       nextPos++;
/*      */     } 
/*      */     
/* 1174 */     if (usesMasteryBonusSkills()) {
/* 1175 */       ps.setString(nextPos, getBonusSkillID());
/*      */       
/* 1177 */       nextPos++;
/*      */     } 
/*      */     
/* 1180 */     if (usesSkillModifier()) {
/* 1181 */       ps.setString(nextPos, getModifiedSkillID());
/*      */       
/* 1183 */       nextPos++;
/*      */     } 
/*      */     
/* 1186 */     if (usesMasteryModifySkills()) {
/* 1187 */       ps.setString(nextPos, getModifiedSkillID());
/*      */       
/* 1189 */       nextPos++;
/*      */     } 
/*      */     
/* 1192 */     if (usesItemSkill()) {
/* 1193 */       ps.setString(nextPos, getItemSkillID());
/*      */       
/* 1195 */       nextPos++;
/*      */     } 
/*      */     
/* 1198 */     if (getMinLevel() != -1) {
/* 1199 */       ps.setInt(nextPos, getMinLevel());
/*      */       
/* 1201 */       nextPos++;
/*      */     } 
/*      */     
/* 1204 */     if (getMaxLevel() != -1) {
/* 1205 */       ps.setInt(nextPos, getMaxLevel());
/*      */       
/* 1207 */       nextPos++;
/*      */     } 
/*      */     
/* 1210 */     if (getMaxCunning() != -1) {
/* 1211 */       ps.setInt(nextPos, getMaxCunning());
/*      */       
/* 1213 */       nextPos++;
/*      */     } 
/*      */     
/* 1216 */     if (getMaxPhysique() != -1) {
/* 1217 */       ps.setInt(nextPos, getMaxPhysique());
/*      */       
/* 1219 */       nextPos++;
/*      */     } 
/*      */     
/* 1222 */     if (getMaxSpirit() != -1) {
/* 1223 */       ps.setInt(nextPos, getMaxSpirit());
/*      */       
/* 1225 */       nextPos++;
/*      */     } 
/*      */     
/* 1228 */     if (usesConversionFrom()) {
/* 1229 */       if (usesConversionTo()) {
/* 1230 */         ps.setString(nextPos, getDamageConvertedFrom());
/* 1231 */         nextPos++;
/*      */         
/* 1233 */         ps.setString(nextPos, getDamageConvertedTo());
/* 1234 */         nextPos++;
/*      */         
/* 1236 */         ps.setString(nextPos, getDamageConvertedFrom());
/* 1237 */         nextPos++;
/*      */         
/* 1239 */         ps.setString(nextPos, getDamageConvertedTo());
/* 1240 */         nextPos++;
/*      */       } else {
/* 1242 */         ps.setString(nextPos, getDamageConvertedFrom());
/* 1243 */         nextPos++;
/*      */         
/* 1245 */         ps.setString(nextPos, getDamageConvertedFrom());
/* 1246 */         nextPos++;
/*      */       }
/*      */     
/* 1249 */     } else if (usesConversionTo()) {
/* 1250 */       ps.setString(nextPos, getDamageConvertedTo());
/* 1251 */       nextPos++;
/*      */       
/* 1253 */       ps.setString(nextPos, getDamageConvertedTo());
/* 1254 */       nextPos++;
/*      */     } 
/*      */ 
/*      */     
/* 1258 */     if (isNoEnemyOnly()) {
/* 1259 */       ps.setBoolean(nextPos, false);
/*      */       
/* 1261 */       nextPos++;
/*      */     } 
/*      */     
/* 1264 */     return nextPos;
/*      */   }
/*      */ 
/*      */   
/*      */   public String determineLevel1Parameters() {
/* 1269 */     String param = getItemClass();
/*      */     
/* 1271 */     if (usesItemRarity()) param = param + ", " + getItemRarity(); 
/* 1272 */     if (usesArmorClass()) param = param + ", " + getArmorClass(); 
/* 1273 */     if (usesArtifactClass()) param = param + ", " + getArtifactClass();
/*      */     
/* 1275 */     if (usesItemName()) param = param + ", " + getItemName(); 
/* 1276 */     if (usesBonusSkill()) param = param + ", " + getBonusSkillID(); 
/* 1277 */     if (usesSkillModifier()) param = param + ", " + getModifiedSkillID(); 
/* 1278 */     if (usesItemSkill()) param = param + ", " + getItemSkillID(); 
/* 1279 */     if (getMinLevel() != -1) param = param + ", " + Integer.toString(getMinLevel()); 
/* 1280 */     if (getMaxLevel() != -1) param = param + ", " + Integer.toString(getMaxLevel()); 
/* 1281 */     if (getMaxCunning() != -1) param = param + ", " + Integer.toString(getMaxCunning()); 
/* 1282 */     if (getMaxPhysique() != -1) param = param + ", " + Integer.toString(getMaxPhysique()); 
/* 1283 */     if (getMaxSpirit() != -1) param = param + ", " + Integer.toString(getMaxSpirit()); 
/* 1284 */     if (isNoEnemyOnly()) param = param + "false";
/*      */     
/* 1286 */     return param;
/*      */   }
/*      */ 
/*      */   
/*      */   public String determineLevel2Statement(String level1command, SelectionCriteria.StatInfo info) {
/* 1291 */     String level2command = level1command;
/*      */     
/* 1293 */     if (info != null) {
/* 1294 */       level2command = level2command + " AND (IST.STAT_TYPE = ?";
/* 1295 */       if (info.flat == info.percentage) {
/* 1296 */         if (info.maxResist) {
/* 1297 */           if (info.flat) {
/* 1298 */             level2command = level2command + " AND ((IST.STAT_MIN > 0) OR (IST.MODIFIER > 0) OR (IST.MAX_RESIST > 0))";
/*      */           } else {
/* 1300 */             level2command = level2command + " AND (IST.MAX_RESIST > 0)";
/*      */           } 
/*      */         } else {
/* 1303 */           level2command = level2command + " AND ((IST.STAT_MIN > 0) OR (IST.MODIFIER > 0))";
/*      */         } 
/*      */       } else {
/* 1306 */         if (info.flat) {
/* 1307 */           level2command = level2command + " AND ((IST.STAT_MIN > 0)";
/*      */         }
/*      */         
/* 1310 */         if (info.percentage) {
/* 1311 */           level2command = level2command + " AND ((IST.MODIFIER > 0)";
/*      */         }
/*      */         
/* 1314 */         if (info.maxResist) {
/* 1315 */           level2command = level2command + " OR (IST.MAX_RESIST > 0))";
/*      */         } else {
/* 1317 */           level2command = level2command + ")";
/*      */         } 
/*      */       } 
/*      */       
/* 1321 */       level2command = level2command + ")";
/*      */     } 
/*      */     
/* 1324 */     return level2command;
/*      */   }
/*      */ 
/*      */   
/*      */   public int fillLevel2Statement(PreparedStatement ps, String statType, int nextPos) throws SQLException {
/* 1329 */     if (statType != null) {
/* 1330 */       ps.setString(nextPos, statType);
/*      */       
/* 1332 */       nextPos++;
/*      */     } 
/*      */     
/* 1335 */     return nextPos;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addSingleIntCombo(List<Integer> listAll, PreparedStatement ps, String command, String statType) throws SQLException, UnsupportedOperationException {
/* 1340 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   public void addSingleStringCombo(List<String> listAll, PreparedStatement ps, String command, String statType) throws SQLException, UnsupportedOperationException {
/* 1345 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeIntList(List<Integer> listAll, List<Integer> list) {
/* 1353 */     for (Integer i : list) {
/* 1354 */       if (!listAll.contains(i)) listAll.add(i); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void mergeStringList(List<String> listAll, List<String> list) {
/* 1359 */     for (String s : list) {
/* 1360 */       if (!listAll.contains(s)) listAll.add(s); 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\criteria\AbstractItemCombination.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */