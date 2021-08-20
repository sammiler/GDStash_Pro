/*     */ package org.gdstash.character;
/*     */ 
/*     */ import org.gdstash.db.DBEngineTagText;
/*     */ import org.gdstash.util.GDColor;
/*     */ import org.gdstash.util.GDMsgFormatter;
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
/*     */ public class GDCharSummary
/*     */ {
/*     */   private String charName;
/*     */   private byte sex;
/*     */   private String classID;
/*     */   private int level;
/*     */   private boolean hardcore;
/*     */   private boolean expansionAoM;
/*     */   private boolean expansionFG;
/*     */   private boolean isInMainQuest;
/*     */   private String className;
/*     */   
/*     */   public GDCharSummary(GDCharHeader header, GDCharInfo info) {
/*  30 */     this.charName = header.getCharName();
/*  31 */     this.sex = header.getSex();
/*  32 */     this.classID = header.getClassID();
/*  33 */     this.level = header.getLevel();
/*  34 */     this.hardcore = header.isHardcore();
/*  35 */     this.expansionAoM = header.isAsheshOfMalmouthChar();
/*  36 */     this.expansionFG = header.isForgottenGodsChar();
/*  37 */     this.isInMainQuest = info.isInMainQuest();
/*     */     
/*  39 */     this.className = parseClassName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCharName() {
/*  47 */     return this.charName;
/*     */   }
/*     */   
/*     */   public byte getSex() {
/*  51 */     return this.sex;
/*     */   }
/*     */   
/*     */   public String getClassName() {
/*  55 */     return this.className;
/*     */   }
/*     */   
/*     */   public int getLevel() {
/*  59 */     return this.level;
/*     */   }
/*     */   
/*     */   public boolean isHardcore() {
/*  63 */     return this.hardcore;
/*     */   }
/*     */   
/*     */   public String getExpansionInfo() {
/*  67 */     String s = null;
/*     */     
/*  69 */     if (this.expansionFG) {
/*  70 */       s = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_CHAR_FG_SHORT");
/*     */       
/*  72 */       s = GDColor.HTML_COLOR_CHAR_FG + s + "</font>";
/*     */     }
/*  74 */     else if (this.expansionAoM) {
/*  75 */       s = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_CHAR_AOM_SHORT");
/*     */       
/*  77 */       s = GDColor.HTML_COLOR_CHAR_AOM + s + "</font>";
/*     */     } else {
/*  79 */       s = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_CHAR_GD_SHORT");
/*     */       
/*  81 */       s = GDColor.HTML_COLOR_CHAR_GD + s + "</font>";
/*     */     } 
/*     */ 
/*     */     
/*  85 */     return s;
/*     */   }
/*     */   
/*     */   public boolean isInMainQuest() {
/*  89 */     return this.isInMainQuest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String parseClassName() {
/*  97 */     DBEngineTagText tt = null;
/*  98 */     String s = "???";
/*     */     
/* 100 */     if (this.classID != null) tt = DBEngineTagText.get(this.classID);
/*     */     
/* 102 */     if (tt != null) {
/* 103 */       if (this.sex == 1) {
/* 104 */         s = tt.getText(0);
/*     */       } else {
/* 106 */         s = tt.getText(1);
/*     */       } 
/*     */     }
/*     */     
/* 110 */     return s;
/*     */   }
/*     */   
/*     */   public void updateClassName() {
/* 114 */     this.className = parseClassName();
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\character\GDCharSummary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */