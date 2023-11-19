/*     */ package org.gdstash.ui.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.item.GDStash;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.util.GDColor;
/*     */ import org.gdstash.util.GDConstants;
/*     */ import org.gdstash.util.GDLog;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ 
/*     */ public class GDStashInfoList
/*     */ {
/*     */   public static List<GDStashFileInfo> gdStashFileInfos;
/*     */   public static List<String> modDirs;
/*     */   public static GDStashFileInfo defaultStash;
/*     */   
/*     */   private enum StashExt
/*     */   {
/*  23 */     GENERIC, VANILLA, AOM, FG; }
/*     */   
/*     */   public static class GDStashFileInfo {
/*     */     public File stashFile;
/*     */     public String fileName;
/*     */     public String stashInfo;
/*     */     public GDStash gdStash;
/*     */     
/*     */     public void setFile(File file) {
/*  32 */       this.stashFile = file;
/*     */       
/*  34 */       GDStashInfoList.adjustStashInfo(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void findStashes(GDStashFrame frame, GDStashFileInfo reloadInfo, GDLog log) {
/*  44 */     List<GDStashFileInfo> infos = gdStashFileInfos;
/*     */     
/*  46 */     gdStashFileInfos = new LinkedList<>();
/*  47 */     modDirs = new LinkedList<>();
/*  48 */     modDirs.add("");
/*     */     
/*  50 */     if (GDStashFrame.iniConfig == null)
/*  51 */       return;  if (GDStashFrame.iniConfig.sectDir.savePath == null)
/*  52 */       return;  if (GDStashFrame.iniConfig.sectDir.savePath.isEmpty())
/*     */       return; 
/*  54 */     File file = new File(GDStashFrame.iniConfig.sectDir.savePath);
/*     */     
/*  56 */     if (file.isDirectory()) {
/*  57 */       processStashDir(file, 0);
/*  58 */       processStashDir(file, 1);
/*     */       
/*  60 */       processModDir(file);
/*     */     } 
/*     */     
/*  63 */     if (file.isFile()) {
/*  64 */       processStashFile(file);
/*     */     }
/*     */     
/*  67 */     adjustStashInfos(infos, reloadInfo, log);
/*     */     
/*  69 */     if (frame != null) {
/*  70 */       if (frame.pnlTransfer != null) frame.pnlTransfer.refreshStashSelection(); 
/*  71 */       if (frame.pnlCraft != null) frame.pnlCraft.refreshStashSelection(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void processModDir(File dir) {
/*     */     try {
/*  77 */       File[] files = dir.listFiles();
/*  78 */       if (files == null)
/*     */         return; 
/*  80 */       for (int i = 0; i < files.length; i++) {
/*  81 */         File file = files[i];
/*     */         
/*  83 */         if (file != null && 
/*  84 */           file.isDirectory()) {
/*  85 */           String path = file.getCanonicalPath();
/*     */           
/*  87 */           if (path.length() > GDStashFrame.iniConfig.sectDir.savePath.length()) {
/*  88 */             path = path.substring(GDStashFrame.iniConfig.sectDir.savePath.length() + 1);
/*     */             
/*  90 */             if (!path.equals("main") && 
/*  91 */               !path.equals("user"))
/*     */             {
/*  93 */               if (!modDirs.contains(path)) modDirs.add(path);
/*     */             
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 100 */     } catch (IOException iOException) {}
/*     */   }
/*     */   
/*     */   private static void processStashDir(File dir, int recDepth) {
/* 104 */     if (dir == null)
/* 105 */       return;  if (recDepth < 0)
/*     */       return; 
/* 107 */     File[] files = dir.listFiles();
/* 108 */     if (files == null)
/*     */       return; 
/* 110 */     for (int i = 0; i < files.length; i++) {
/* 111 */       File file = files[i];
/*     */       
/* 113 */       if (file != null) {
/* 114 */         if (file.isDirectory()) processStashDir(file, recDepth - 1); 
/* 115 */         if (file.isFile() && 
/* 116 */           recDepth == 0) processStashFile(file);
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void processStashFile(File file) {
/* 123 */     String s = file.getName().toUpperCase(GDConstants.LOCALE_US);
/*     */     
/* 125 */     if (s.equals("TRANSFER.GST") || s.equals("TRANSFER.GSH") || s
/* 126 */       .equals("TRANSFER.BST") || s.equals("TRANSFER.BSH") || s
/* 127 */       .equals("TRANSFER.CST") || s.equals("TRANSFER.CSH")) {
/* 128 */       GDStashFileInfo info = new GDStashFileInfo();
/* 129 */       info.stashFile = file;
/*     */       
/* 131 */       gdStashFileInfos.add(info);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void adjustStashInfo(GDStashFileInfo info) {
/* 136 */     if (info.stashFile == null)
/*     */       return; 
/*     */     try {
/* 139 */       info.fileName = info.stashFile.getCanonicalPath();
/*     */     }
/* 141 */     catch (IOException ex) {
/* 142 */       info.fileName = null;
/*     */     } 
/*     */     
/* 145 */     StashExt stashExt = StashExt.GENERIC;
/* 146 */     String fn = info.stashFile.getName().toUpperCase(GDConstants.LOCALE_US);
/* 147 */     String sHC = null;
/* 148 */     if (fn.equals("TRANSFER.GSH")) {
/* 149 */       sHC = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_STASH_HARDCORE");
/*     */     }
/* 151 */     if (fn.equals("TRANSFER.GST")) {
/* 152 */       sHC = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_STASH_SOFTCORE");
/*     */     }
/* 154 */     if (fn.equals("TRANSFER.BSH")) {
/* 155 */       sHC = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_STASH_HC_BASE");
/*     */       
/* 157 */       stashExt = StashExt.VANILLA;
/*     */     } 
/* 159 */     if (fn.equals("TRANSFER.BST")) {
/* 160 */       sHC = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_STASH_SC_BASE");
/*     */       
/* 162 */       stashExt = StashExt.VANILLA;
/*     */     } 
/* 164 */     if (fn.equals("TRANSFER.CSH")) {
/* 165 */       sHC = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_STASH_HC_AOM");
/*     */       
/* 167 */       stashExt = StashExt.AOM;
/*     */     } 
/* 169 */     if (fn.equals("TRANSFER.CST")) {
/* 170 */       sHC = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_STASH_SC_AOM");
/*     */       
/* 172 */       stashExt = StashExt.AOM;
/*     */     } 
/*     */ 
/*     */     
/* 176 */     String dir = null;
/*     */     try {
/* 178 */       dir = info.stashFile.getParentFile().getCanonicalPath();
/*     */     }
/* 180 */     catch (IOException ex) {
/* 181 */       dir = null;
/*     */     } 
/*     */     
/* 184 */     String s = null;
/* 185 */     if (dir == null) {
/* 186 */       dir = info.stashFile.getParentFile().getName();
/*     */       
/* 188 */       Object[] sArgs = { sHC, dir };
/* 189 */       s = GDMsgFormatter.format(GDMsgFormatter.rbUI, "TXT_STASH_SELECT_MOD", sArgs);
/*     */     }
/* 191 */     else if (dir.length() <= GDStashFrame.iniConfig.sectDir.savePath.length()) {
/* 192 */       Object[] sArgs = { sHC };
/* 193 */       s = GDMsgFormatter.format(GDMsgFormatter.rbUI, "TXT_STASH_SELECT_MAIN", sArgs);
/*     */     } else {
/* 195 */       dir = info.stashFile.getParentFile().getName();
/*     */       
/* 197 */       Object[] sArgs = { sHC, dir };
/* 198 */       s = GDMsgFormatter.format(GDMsgFormatter.rbUI, "TXT_STASH_SELECT_MOD", sArgs);
/*     */     } 
/*     */ 
/*     */     
/* 202 */     if (stashExt == StashExt.VANILLA) {
/* 203 */       s = "<html>" + GDColor.HTML_COLOR_CHAR_GD + s + "</font>" + "</html>";
/*     */     }
/* 205 */     if (stashExt == StashExt.AOM) {
/* 206 */       s = "<html>" + GDColor.HTML_COLOR_CHAR_AOM + s + "</font>" + "</html>";
/*     */     }
/*     */     
/* 209 */     info.stashInfo = s;
/*     */   }
/*     */   
/*     */   public static void adjustStashInfos(List<GDStashFileInfo> infos, GDStashFileInfo reloadInfo, GDLog log) {
/* 213 */     if (gdStashFileInfos == null)
/*     */       return; 
/* 215 */     boolean found = false;
/* 216 */     GDStashFileInfo first = null;
/* 217 */     for (GDStashFileInfo info : gdStashFileInfos) {
/* 218 */       adjustStashInfo(info);
/*     */       
/* 220 */       if (first == null) first = info;
/*     */ 
/*     */       
/* 223 */       if (GDStashFrame.iniConfig.sectHistory.lastStash != null && 
/* 224 */         GDStashFrame.iniConfig.sectHistory.lastStash.equals(info.fileName)) {
/* 225 */         if (info.gdStash == null) {
/* 226 */           found = true;
/*     */           
/* 228 */           info.gdStash = new GDStash(info.stashFile, log);
/*     */           
/* 230 */           if (info.gdStash.hasStashErrors()) {
/* 231 */             info.gdStash = null;
/*     */           }
/*     */         } 
/*     */         
/* 235 */         defaultStash = info;
/* 236 */         GDStashFrame.iniConfig.sectHistory.lastStash = info.fileName;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 241 */       if (infos != null) {
/* 242 */         for (GDStashFileInfo info2 : infos) {
/* 243 */           if (info.fileName.equals(info2.fileName) && 
/* 244 */             info2.gdStash != null && 
/* 245 */             info2 != reloadInfo && 
/* 246 */             info2.gdStash.hasChanged()) info.gdStash = info2.gdStash;
/*     */ 
/*     */ 
/*     */           
/* 250 */           if (reloadInfo != null && 
/* 251 */             info.fileName.equals(reloadInfo.fileName)) {
/* 252 */             if (info.gdStash != null) {
/* 253 */               info.gdStash.read(null);
/*     */               
/* 255 */               if (info.gdStash.hasStashErrors()) info.gdStash = null; 
/*     */             } else {
/* 257 */               info.gdStash = new GDStash(info.stashFile);
/*     */             } 
/*     */             
/* 260 */             GDStashFrame.iniConfig.sectHistory.lastStash = info.fileName;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 267 */     if (!found) {
/* 268 */       defaultStash = first;
/*     */       
/* 270 */       if (first != null) {
/* 271 */         GDStashFrame.iniConfig.sectHistory.lastStash = first.fileName;
/*     */         
/* 273 */         if (first.gdStash == null) {
/* 274 */           first.gdStash = new GDStash(first.stashFile);
/*     */           
/* 276 */           if (first.gdStash.hasStashErrors()) {
/* 277 */             first.gdStash = null;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String[] getStashInfos() {
/* 285 */     int size = 1;
/*     */     
/* 287 */     if (gdStashFileInfos != null) {
/* 288 */       size += gdStashFileInfos.size();
/*     */     }
/*     */     
/* 291 */     String[] cis = new String[size];
/* 292 */     cis[0] = "";
/*     */     
/* 294 */     if (gdStashFileInfos == null) return cis; 
/* 295 */     if (gdStashFileInfos.size() == 0) return cis;
/*     */     
/* 297 */     size--;
/* 298 */     cis = new String[size];
/*     */     
/* 300 */     int i = 0;
/* 301 */     for (GDStashFileInfo info : gdStashFileInfos) {
/* 302 */       cis[i] = info.stashInfo;
/*     */       
/* 304 */       i++;
/*     */     } 
/*     */     
/* 307 */     return cis;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\u\\util\GDStashInfoList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */