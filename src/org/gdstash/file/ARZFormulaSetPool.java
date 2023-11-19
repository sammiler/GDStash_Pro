/*    */ package org.gdstash.file;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import org.gdstash.db.DBFormulaSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ARZFormulaSetPool
/*    */ {
/*    */   public static final String TEMPLATE_COSTFORMULA = "database/templates/itemcost.tpl";
/* 19 */   private static List<ARZRecord> records = new LinkedList<>();
/*    */   
/*    */   public static boolean isFormula(String template) {
/* 22 */     if (template == null) return false;
/*    */     
/* 24 */     if (template.equals("database/templates/itemcost.tpl")) return true;
/*    */     
/* 26 */     return false;
/*    */   }
/*    */   
/*    */   public static void storeFormulaSet(ARZRecord record) {
/* 30 */     String fn = record.getFileName();
/* 31 */     String tpl = record.getTemplate();
/*    */     
/* 33 */     if (fn == null)
/* 34 */       return;  if (tpl == null)
/* 35 */       return;  if (!isFormula(tpl))
/*    */       return; 
/* 37 */     Iterator<ARZRecord> iter = records.iterator();
/* 38 */     while (iter.hasNext()) {
/* 39 */       ARZRecord rec = iter.next();
/*    */       
/* 41 */       if (rec.getFileName().equals(fn)) {
/* 42 */         iter.remove();
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/*    */     
/* 48 */     records.add(record);
/*    */   }
/*    */   
/*    */   public static DBFormulaSet getFormulaSet(String formulaSetID) {
/* 52 */     DBFormulaSet set = null;
/*    */     
/* 54 */     for (ARZRecord rec : records) {
/* 55 */       if (rec.getFileName().equals(formulaSetID)) {
/* 56 */         set = rec.getDBFormulaSet();
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/*    */     
/* 62 */     return set;
/*    */   }
/*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\file\ARZFormulaSetPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */