/*    */ package org.gdstash.description;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AffixComparator
/*    */   implements Comparator<BonusInfoSort>
/*    */ {
/*    */   public int compare(BonusInfoSort bis1, BonusInfoSort bis2) {
/* 15 */     int it1 = bis1.getInfoTypeInt();
/* 16 */     int it2 = bis1.getInfoTypeInt();
/*    */     
/* 18 */     if (it1 < it2) return -1; 
/* 19 */     if (it1 > it2) return 1;
/*    */     
/* 21 */     int type1 = bis1.getTypeInt();
/* 22 */     int type2 = bis2.getTypeInt();
/*    */     
/* 24 */     if (type1 < type2) return -1; 
/* 25 */     if (type1 > type2) return 1;
/*    */     
/* 27 */     int tag1 = bis1.getTagInt();
/* 28 */     int tag2 = bis2.getTagInt();
/*    */     
/* 30 */     if (tag1 < tag2) return -1; 
/* 31 */     if (tag1 > tag2) return 1;
/*    */     
/* 33 */     int prio1 = bis1.prio;
/* 34 */     int prio2 = bis2.prio;
/*    */     
/* 36 */     if (prio1 < prio2) return -1; 
/* 37 */     if (prio1 > prio2) return 1;
/*    */     
/* 39 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\description\AffixComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */