/*     */ package org.gdstash.util;
/*     */ 
/*     */ import java.awt.Component;
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
/*     */ public class GDMsgLogger
/*     */ {
/*  17 */   private static GDLog log = new GDLog();
/*     */ 
/*     */   
/*     */   public static void clear() {
/*  21 */     log.clear();
/*     */   }
/*     */   
/*     */   public static void addMessage(String s, GDLog.MessageType type) {
/*  25 */     log.addMessage(s, type);
/*     */   }
/*     */   
/*     */   public static void addMessage(String s) {
/*  29 */     log.addMessage(s);
/*     */   }
/*     */   
/*     */   public static void addSuccess(String s) {
/*  33 */     log.addSuccess(s);
/*     */   }
/*     */   
/*     */   public static void addInfo(String s) {
/*  37 */     log.addInfo(s);
/*     */   }
/*     */   
/*     */   public static void addWarning(String s) {
/*  41 */     log.addWarning(s);
/*     */   }
/*     */   
/*     */   public static void addError(String s) {
/*  45 */     log.addError(s);
/*     */   }
/*     */   
/*     */   public static void addLowError(String s) {
/*  49 */     log.addLowError(s);
/*     */   }
/*     */   
/*     */   public static void addHighError(String s) {
/*  53 */     log.addHighError(s);
/*     */   }
/*     */   
/*     */   public static void addWarning(Throwable ex) {
/*  57 */     log.addWarning(ex);
/*     */   }
/*     */   
/*     */   public static void addError(Throwable ex) {
/*  61 */     log.addError(ex);
/*     */   }
/*     */   
/*     */   public static void addLowError(Throwable ex) {
/*  65 */     log.addLowError(ex);
/*     */   }
/*     */   
/*     */   public static void addHighError(Throwable ex) {
/*  69 */     log.addHighError(ex);
/*     */   }
/*     */   
/*     */   public static void addLog(GDLog log) {
/*  73 */     GDMsgLogger.log.addLog(log);
/*     */   }
/*     */   
/*     */   public static boolean errorsInLog() {
/*  77 */     return log.containsErrors();
/*     */   }
/*     */   
/*     */   public static boolean severeErrorsInLog() {
/*  81 */     return log.containSevereErrors();
/*     */   }
/*     */   
/*     */   public static int getNumberLowErrors() {
/*  85 */     return log.getNumberLowErrors();
/*     */   }
/*     */   
/*     */   public static int getNumberMediumErrors() {
/*  89 */     return log.getNumberMediumErrors();
/*     */   }
/*     */   
/*     */   public static int getNumberHighErrors() {
/*  93 */     return log.getNumberHighErrors();
/*     */   }
/*     */   
/*     */   public static void showLog(Component owner, String message, GDLog.MessageType type) {
/*  97 */     log.showLog(owner, message, type);
/*     */   }
/*     */   
/*     */   public static void showLog(Component owner, String error) {
/* 101 */     log.showLog(owner, error);
/*     */   }
/*     */   
/*     */   public static void showLog(Component owner, String success, GDLog.MessageType type, String error) {
/* 105 */     log.showLog(owner, success, type, error);
/*     */   }
/*     */   
/*     */   public static void showLog(Component owner, String success, GDLog.MessageType type, String error, boolean showEmpty) {
/* 109 */     log.showLog(owner, success, type, error, showEmpty);
/*     */   }
/*     */   
/*     */   public static void showLog(Component owner, String success, GDLog.MessageType type, String error, boolean showEmpty, boolean errorOnly) {
/* 113 */     log.showLog(owner, success, type, error, showEmpty, errorOnly);
/*     */   }
/*     */   
/*     */   public static void showSevereLog(Component owner, String success, GDLog.MessageType type, String error, boolean showEmpty, boolean errorOnly) {
/* 117 */     log.showSevereLog(owner, success, type, error, showEmpty, errorOnly);
/*     */   }
/*     */   
/*     */   public static void printStackTrace(Exception ex) {
/* 121 */     StringBuffer sb = new StringBuffer(500);
/*     */     
/* 123 */     StackTraceElement[] st = ex.getStackTrace();
/* 124 */     sb.append(ex.getClass().getName() + ": " + ex.getMessage() + "\n");
/* 125 */     for (int i = 0; i < st.length; i++) {
/* 126 */       sb.append("\t at " + st[i].toString() + "\n");
/*     */     }
/*     */     
/* 129 */     System.out.println(sb.toString());
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\util\GDMsgLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */