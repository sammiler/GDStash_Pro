/*     */ package org.gdstash.db;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DBViewItemSkill
/*     */   implements Comparable<DBViewItemSkill>
/*     */ {
/*     */   private static final int ROW_SKILL_ID = 1;
/*     */   private static final int ROW_NAME = 2;
/*  29 */   private String skillID = null;
/*  30 */   private String name = null;
/*     */ 
/*     */   
/*     */   public DBViewItemSkill(String name) {
/*  34 */     this();
/*     */     
/*  36 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  45 */     if (o == null) return false; 
/*  46 */     if (!o.getClass().equals(DBViewItemSkill.class)) return false;
/*     */     
/*  48 */     DBViewItemSkill skill = (DBViewItemSkill)o;
/*     */     
/*  50 */     if (this.skillID == null) {
/*  51 */       return (skill.skillID == null);
/*     */     }
/*  53 */     return this.skillID.equals(skill.skillID);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  59 */     return this.skillID.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(DBViewItemSkill skill) {
/*  67 */     if (skill == null) return -1;
/*     */     
/*  69 */     return this.name.compareTo(skill.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSkillID() {
/*  77 */     return this.skillID;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  81 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<DBViewItemSkill> getItemSkills() {
/*  89 */     List<DBViewItemSkill> list = new LinkedList<>();
/*     */     
/*  91 */     List<DBViewItemSkill> itemList = getSkillsFromItem();
/*  92 */     List<DBViewItemSkill> affixList = getSkillsFromAffix();
/*     */     
/*  94 */     if (itemList != null) {
/*  95 */       for (DBViewItemSkill skill : itemList) {
/*  96 */         if (skill == null || 
/*  97 */           skill.name == null)
/*     */           continue; 
/*  99 */         boolean found = false;
/* 100 */         for (DBViewItemSkill itemSkill : list) {
/* 101 */           if (itemSkill.skillID.equals(skill.skillID)) {
/* 102 */             found = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */         
/* 108 */         if (!found) list.add(skill);
/*     */       
/*     */       } 
/*     */     }
/* 112 */     if (affixList != null) {
/* 113 */       for (DBViewItemSkill skill : affixList) {
/* 114 */         if (skill == null || 
/* 115 */           skill.name == null)
/*     */           continue; 
/* 117 */         boolean found = false;
/* 118 */         for (DBViewItemSkill itemSkill : list) {
/* 119 */           if (itemSkill.skillID.equals(skill.skillID)) {
/* 120 */             found = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */         
/* 126 */         if (!found) list.add(skill);
/*     */       
/*     */       } 
/*     */     }
/* 130 */     Collections.sort(list);
/*     */     
/* 132 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBViewItemSkill> getSkillsFromItem() {
/* 136 */     List<DBViewItemSkill> list = null;
/*     */     
/* 138 */     String command = "SELECT DISTINCT GD_SKILL.SKILL_ID, GD_SKILL.NAME FROM GD_ITEM, GD_SKILL WHERE GD_ITEM.ITEM_SKILL_ID IS NOT NULL AND GD_ITEM.ITEM_SKILL_ID = GD_SKILL.SKILL_ID ORDER BY GD_SKILL.NAME";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     try(Connection conn = GDDBData.getConnection(); 
/* 144 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*     */       
/* 146 */       try (ResultSet rs = ps.executeQuery()) {
/* 147 */         list = wrap(rs);
/*     */         
/* 149 */         conn.commit();
/*     */       }
/* 151 */       catch (SQLException ex) {
/* 152 */         throw ex;
/*     */       }
/*     */     
/* 155 */     } catch (SQLException ex) {
/* 156 */       Object[] args = { "-", "DBViewItemSkill" };
/* 157 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 159 */       GDMsgLogger.addError(msg);
/* 160 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 163 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBViewItemSkill> getSkillsFromAffix() {
/* 167 */     List<DBViewItemSkill> list = null;
/*     */     
/* 169 */     String command = "SELECT DISTINCT GD_SKILL.SKILL_ID, GD_SKILL.NAME FROM GD_AFFIX, GD_SKILL WHERE GD_AFFIX.ITEM_SKILL_ID IS NOT NULL AND GD_AFFIX.ITEM_SKILL_ID = GD_SKILL.SKILL_ID ORDER BY GD_SKILL.NAME";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     try(Connection conn = GDDBData.getConnection(); 
/* 175 */         PreparedStatement ps = conn.prepareStatement(command)) {
/*     */       
/* 177 */       try (ResultSet rs = ps.executeQuery()) {
/* 178 */         list = wrap(rs);
/*     */         
/* 180 */         conn.commit();
/*     */       }
/* 182 */       catch (SQLException ex) {
/* 183 */         throw ex;
/*     */       }
/*     */     
/* 186 */     } catch (SQLException ex) {
/* 187 */       Object[] args = { "-", "DBViewItemSkill" };
/* 188 */       String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_READ_TABLE_BY_ID", args);
/*     */       
/* 190 */       GDMsgLogger.addError(msg);
/* 191 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */     
/* 194 */     return list;
/*     */   }
/*     */   
/*     */   private static List<DBViewItemSkill> wrap(ResultSet rs) throws SQLException {
/* 198 */     LinkedList<DBViewItemSkill> list = new LinkedList<>();
/*     */     
/* 200 */     while (rs.next()) {
/* 201 */       DBViewItemSkill skill = new DBViewItemSkill();
/*     */       
/* 203 */       skill.skillID = rs.getString(1);
/* 204 */       skill.name = rs.getString(2);
/*     */       
/* 206 */       list.add(skill);
/*     */     } 
/*     */     
/* 209 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 217 */     return this.name;
/*     */   }
/*     */   
/*     */   public DBViewItemSkill() {}
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\db\DBViewItemSkill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */