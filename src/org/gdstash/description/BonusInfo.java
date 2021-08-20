/*    */ package org.gdstash.description;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BonusInfo
/*    */ {
/*    */   public static final int INFOTYPE_UNKNOWN = 0;
/*    */   public static final int INFOTYPE_DEFENSE = 1;
/*    */   public static final int INFOTYPE_OFFENSE = 2;
/*    */   public static final int INFOTYPE_RETALIATION = 3;
/*    */   public static final int INFOTYPE_CHARSTAT = 4;
/*    */   public static final int INFOTYPE_SKILLBONUS = 5;
/*    */   public static final int INFOTYPE_SKILL = 6;
/*    */   public String statDesc;
/* 43 */   public String text = null;
/* 44 */   public String tag = null;
/* 45 */   public String prefix = null;
/*    */   public boolean baseStat = false;
/* 47 */   public int prio = 0;
/* 48 */   public int infoType = 0;
/*    */   
/*    */   public boolean dmgFlat = false;
/*    */   public boolean dmgPerc = false;
/*    */   public boolean dmgChance = false;
/*    */   public boolean dmgDuration = false;
/*    */   public boolean dmgMaxResist = false;
/*    */   public boolean dmgGlobal = false;
/*    */   public boolean dmgXOR = false;
/*    */   public boolean bonusPerc = false;
/*    */   public boolean sbMastery = false;
/* 59 */   public int sbPlus = 0;
/*    */ 
/*    */   
/*    */   public BonusInfo(TagInfo info) {
/* 63 */     this();
/*    */     
/* 65 */     if (info == null)
/*    */       return; 
/* 67 */     this.tag = info.getTag();
/* 68 */     this.infoType = info.getInfoType();
/* 69 */     this.baseStat = info.isBaseStat();
/* 70 */     this.prio = info.getPriority();
/*    */   }
/*    */   
/*    */   public BonusInfo() {}
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\description\BonusInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */