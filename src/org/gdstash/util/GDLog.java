/*     */ package org.gdstash.util;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.gdstash.ui.GDDialog;
/*     */ import org.gdstash.ui.GDLogDialog;
/*     */ 
/*     */ public class GDLog
/*     */ {
/*     */   private List<LogEntry> list;
/*     */   private boolean error;
/*     */   private int errorLow;
/*     */   private int errorMedium;
/*     */   private int errorHigh;
/*     */   
/*     */   public enum MessageType {
/*  22 */     Success, Info, Warning, Error; }
/*  23 */   public enum Severity { Low, Medium, High; }
/*     */ 
/*     */   
/*     */   public static class LogEntry {
/*     */     public GDLog.MessageType type;
/*     */     public GDLog.Severity severity;
/*     */     public Throwable ex;
/*     */     public String str;
/*     */     
/*     */     public LogEntry(String s) {
/*  33 */       this(s, GDLog.MessageType.Success, GDLog.Severity.Medium);
/*     */     }
/*     */     
/*     */     public LogEntry(String s, GDLog.MessageType type, GDLog.Severity severity) {
/*  37 */       this.type = type;
/*  38 */       this.severity = severity;
/*  39 */       this.str = s;
/*  40 */       this.ex = null;
/*     */     }
/*     */     
/*     */     public LogEntry(Exception ex) {
/*  44 */       this(ex, GDLog.MessageType.Error, GDLog.Severity.Medium);
/*     */     }
/*     */     
/*     */     public LogEntry(Throwable ex, GDLog.MessageType type, GDLog.Severity severity) {
/*  48 */       this.type = type;
/*  49 */       this.severity = severity;
/*  50 */       this.ex = ex;
/*     */       
/*  52 */       if (ex.getMessage() == null || ex
/*  53 */         .getMessage().isEmpty()) {
/*  54 */         this.str = ex.toString();
/*     */       } else {
/*  56 */         this.str = ex.getMessage();
/*     */       } 
/*     */       
/*  59 */       this.str += GDConstants.LINE_SEPARATOR + GDLog.getStackTrace(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GDLog() {
/*  70 */     this.list = new LinkedList<>();
/*  71 */     this.error = false;
/*  72 */     this.errorLow = 0;
/*  73 */     this.errorMedium = 0;
/*  74 */     this.errorHigh = 0;
/*     */   }
/*     */   
/*     */   public static String getStackTrace(Throwable ex) {
/*  78 */     StringWriter sw = new StringWriter();
/*  79 */     PrintWriter pw = new PrintWriter(sw, true);
/*  80 */     ex.printStackTrace(pw);
/*     */     
/*  82 */     return sw.getBuffer().toString();
/*     */   }
/*     */   
/*     */   private void addException(Throwable ex, MessageType type, Severity severity) {
/*  86 */     this.list.add(new LogEntry(ex, type, severity));
/*     */     
/*  88 */     if (type == MessageType.Error) {
/*  89 */       this.error = true;
/*     */       
/*  91 */       if (severity == Severity.Low) this.errorLow++; 
/*  92 */       if (severity == Severity.Medium) this.errorMedium++; 
/*  93 */       if (severity == Severity.High) this.errorHigh++; 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addMessage(String s, MessageType type, Severity severity) {
/*  98 */     this.list.add(new LogEntry(s, type, severity));
/*     */     
/* 100 */     if (type == MessageType.Error) {
/* 101 */       this.error = true;
/*     */       
/* 103 */       if (severity == Severity.Low) this.errorLow++; 
/* 104 */       if (severity == Severity.Medium) this.errorMedium++; 
/* 105 */       if (severity == Severity.High) this.errorHigh++; 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addMessage(String s, MessageType type) {
/* 110 */     addMessage(s, type, Severity.Medium);
/*     */   }
/*     */   
/*     */   public void addMessage(String s) {
/* 114 */     addMessage(s, MessageType.Success);
/*     */   }
/*     */   
/*     */   public void addSuccess(String s) {
/* 118 */     addMessage(s, MessageType.Success);
/*     */   }
/*     */   
/*     */   public void addInfo(String s) {
/* 122 */     addMessage(s, MessageType.Info);
/*     */   }
/*     */   
/*     */   public void addWarning(String s) {
/* 126 */     addMessage(s, MessageType.Warning);
/*     */   }
/*     */   
/*     */   public void addError(String s) {
/* 130 */     addMessage(s, MessageType.Error, Severity.Medium);
/*     */   }
/*     */   
/*     */   public void addLowError(String s) {
/* 134 */     addMessage(s, MessageType.Error, Severity.Low);
/*     */   }
/*     */   
/*     */   public void addHighError(String s) {
/* 138 */     addMessage(s, MessageType.Error, Severity.High);
/*     */   }
/*     */   
/*     */   public void addWarning(Throwable ex) {
/* 142 */     addException(ex, MessageType.Warning, Severity.Medium);
/*     */   }
/*     */   
/*     */   public void addError(Throwable ex) {
/* 146 */     addException(ex, MessageType.Error, Severity.Medium);
/*     */   }
/*     */   
/*     */   public void addLowError(Throwable ex) {
/* 150 */     addException(ex, MessageType.Error, Severity.Low);
/*     */   }
/*     */   
/*     */   public void addHighError(Throwable ex) {
/* 154 */     addException(ex, MessageType.Error, Severity.High);
/*     */   }
/*     */   
/*     */   public void addLog(GDLog log) {
/* 158 */     this.list.addAll(log.list);
/*     */     
/* 160 */     if (log.containsErrors()) {
/* 161 */       this.error = true;
/*     */       
/* 163 */       this.errorLow += log.getNumberLowErrors();
/* 164 */       this.errorMedium += log.getNumberMediumErrors();
/* 165 */       this.errorHigh += log.getNumberHighErrors();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean containsErrors() {
/* 170 */     return this.error;
/*     */   }
/*     */   
/*     */   public boolean containSevereErrors() {
/* 174 */     if (getNumberLowErrors() > 10 || 
/* 175 */       getNumberMediumErrors() > 0 || 
/* 176 */       getNumberHighErrors() > 0) {
/* 177 */       return true;
/*     */     }
/*     */     
/* 180 */     return false;
/*     */   }
/*     */   
/*     */   public boolean containsMessages() {
/* 184 */     return !this.list.isEmpty();
/*     */   }
/*     */   
/*     */   public List<LogEntry> getLog() {
/* 188 */     return this.list;
/*     */   }
/*     */   
/*     */   public int getNumberLowErrors() {
/* 192 */     return this.errorLow;
/*     */   }
/*     */   
/*     */   public int getNumberMediumErrors() {
/* 196 */     return this.errorMedium;
/*     */   }
/*     */   
/*     */   public int getNumberHighErrors() {
/* 200 */     return this.errorHigh;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 204 */     this.list.clear();
/* 205 */     this.error = false;
/* 206 */     this.errorLow = 0;
/* 207 */     this.errorMedium = 0;
/* 208 */     this.errorHigh = 0;
/*     */   }
/*     */   
/*     */   public void showLog(Component owner, String message, MessageType type) {
/* 212 */     JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(owner);
/*     */     
/* 214 */     GDLogDialog dlg = new GDLogDialog(frame, message, type, this);
/*     */     
/* 216 */     clear();
/*     */   }
/*     */   
/*     */   public void showLog(Component owner, String error) {
/* 220 */     showLog(owner, "", MessageType.Error, error, false, true);
/*     */   }
/*     */   
/*     */   public void showLog(Component owner, String success, MessageType type, String error) {
/* 224 */     showLog(owner, success, type, error, false, false);
/*     */   }
/*     */   
/*     */   public void showLog(Component owner, String success, MessageType type, String error, boolean showEmpty) {
/* 228 */     showLog(owner, success, type, error, showEmpty, false);
/*     */   }
/*     */   
/*     */   public void showLog(Component owner, String success, MessageType type, String error, boolean showEmpty, boolean errorOnly) {
/* 232 */     JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(owner);
/*     */     
/* 234 */     if (containsErrors()) {
/* 235 */       GDLogDialog gDLogDialog = new GDLogDialog(frame, error, MessageType.Error, this);
/*     */     }
/* 237 */     else if (containsMessages()) {
/* 238 */       if (!errorOnly) {
/* 239 */         GDLogDialog gDLogDialog = new GDLogDialog(frame, success, type, this);
/*     */       }
/*     */     }
/* 242 */     else if (showEmpty) {
/*     */       
/* 244 */       GDDialog dialog = new GDDialog(success, 1, frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCCESS"), true);
/* 245 */       dialog.setVisible(true);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 252 */     clear();
/*     */   }
/*     */   
/*     */   public void showSevereLog(Component owner, String success, MessageType type, String error, boolean showEmpty, boolean errorOnly) {
/* 256 */     JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(owner);
/*     */     
/* 258 */     if (containSevereErrors()) {
/* 259 */       GDLogDialog gDLogDialog = new GDLogDialog(frame, error, MessageType.Error, this);
/*     */     }
/* 261 */     else if (containsMessages()) {
/* 262 */       if (!errorOnly) {
/* 263 */         GDLogDialog gDLogDialog = new GDLogDialog(frame, success, type, this);
/*     */       }
/*     */     }
/* 266 */     else if (showEmpty) {
/*     */       
/* 268 */       GDDialog dialog = new GDDialog(success, 1, frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCCESS"), true);
/* 269 */       dialog.setVisible(true);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 276 */     clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\util\GDLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */