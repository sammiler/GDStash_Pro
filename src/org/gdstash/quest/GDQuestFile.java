/*     */ package org.gdstash.quest;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import org.gdstash.character.GDCharUID;
/*     */ import org.gdstash.file.GDReader;
/*     */ import org.gdstash.util.FileVersionException;
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
/*     */ public class GDQuestFile
/*     */ {
/*     */   private File file;
/*     */   private int key;
/*     */   private int version;
/*     */   private boolean fileError;
/*     */   private GDCharUID id;
/*     */   private GDQuestTokenList tokens;
/*     */   private GDQuestList quests;
/*     */   
/*     */   public GDQuestFile(File file) {
/*  33 */     this.file = file;
/*  34 */     this.fileError = false;
/*     */     
/*  36 */     this.id = new GDCharUID();
/*  37 */     this.tokens = new GDQuestTokenList();
/*  38 */     this.quests = new GDQuestList();
/*     */   }
/*     */   
/*     */   public void read() {
/*  42 */     String path = null;
/*     */     
/*     */     try {
/*  45 */       path = this.file.getCanonicalPath();
/*     */     }
/*  47 */     catch (IOException iOException) {}
/*     */     
/*  49 */     int val = 0;
/*     */     try {
/*  51 */       GDReader.readEncFileToBuffer(this.file);
/*     */       
/*  53 */       this.key = GDReader.readKey();
/*     */       
/*  55 */       val = GDReader.readEncInt(true);
/*  56 */       if (val != 1481921361) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */       
/*  58 */       this.version = GDReader.readEncInt(true);
/*  59 */       if (this.version != 0) {
/*  60 */         throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */       }
/*     */       
/*  63 */       this.id.read();
/*  64 */       this.tokens.read();
/*  65 */       this.quests.read();
/*     */       
/*  67 */       this.tokens.listTokens();
/*     */     }
/*  69 */     catch (FileNotFoundException ex) {
/*  70 */       if (path != null) {
/*  71 */         Object[] args = { path };
/*  72 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*     */         
/*  74 */         GDMsgLogger.addError(msg);
/*     */       } 
/*  76 */       GDMsgLogger.addError(ex);
/*     */       
/*  78 */       this.fileError = true;
/*     */     }
/*  80 */     catch (FileVersionException ex) {
/*  81 */       if (path != null) {
/*  82 */         Object[] args = { path };
/*  83 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*     */         
/*  85 */         GDMsgLogger.addError(msg);
/*     */       } 
/*  87 */       GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "INFO_UPDATE_GDSTASH"));
/*  88 */       GDMsgLogger.addError((Throwable)ex);
/*     */       
/*  90 */       this.fileError = true;
/*     */     }
/*  92 */     catch (Exception ex) {
/*  93 */       if (path != null) {
/*  94 */         Object[] args = { path };
/*  95 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_READ", args);
/*     */         
/*  97 */         GDMsgLogger.addError(msg);
/*     */       } 
/*  99 */       GDMsgLogger.addError(ex);
/*     */       
/* 101 */       this.fileError = true;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\quest\GDQuestFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */