/*    */ package org.gdstash.db.criteria;public interface CriteriaCombination extends Cloneable { SelectionCriteria getCriteria(); String determineLevel1Statement(Soulbound paramSoulbound, SC_HC paramSC_HC, boolean paramBoolean, String paramString); List<CriteriaCombination> createLevel1Combinations(CriteriaCombination paramCriteriaCombination); int fillLevel1Statement(PreparedStatement paramPreparedStatement) throws SQLException; String determineLevel1Parameters();
/*    */   String determineLevel2Statement(String paramString, SelectionCriteria.StatInfo paramStatInfo);
/*    */   int fillLevel2Statement(PreparedStatement paramPreparedStatement, String paramString, int paramInt) throws SQLException;
/*    */   void addSingleIntCombo(List<Integer> paramList, PreparedStatement paramPreparedStatement, String paramString1, String paramString2) throws SQLException, UnsupportedOperationException;
/*    */   void addSingleStringCombo(List<String> paramList, PreparedStatement paramPreparedStatement, String paramString1, String paramString2) throws SQLException, UnsupportedOperationException;
/*    */   CriteriaCombination clone();
/*    */   void setItemClass(String paramString);
/*    */   void setArmorClass(String paramString);
/*    */   void setArtifactClass(String paramString);
/*    */   void setItemRarity(String paramString);
/*    */   void setItemName(String paramString);
/*    */   boolean usesArmorClass();
/*    */   boolean usesArtifactClass();
/*    */   boolean usesItemRarity();
/*    */   boolean usesItemName();
/* 16 */   public enum Soulbound { INCLUDED, EXCLUDED, SETTING; }
/* 17 */   public enum SC_HC { SOFTCORE, HARDCORE, ALL, SETTING; }
/*    */    }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\criteria\CriteriaCombination.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */