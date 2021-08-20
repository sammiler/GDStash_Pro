/*     */ package org.gdstash.ui.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.character.GDCharSummary;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.util.GDConstants;
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
/*     */ public class GDCharInfoList
/*     */ {
/*     */   public static List<GDCharFileInfo> gdCharFileInfos;
/*     */   
/*     */   public static class GDCharFileInfo
/*     */   {
/*     */     public File charFile;
/*     */     public String fileName;
/*     */     public GDCharSummary gdcSummary;
/*     */     public String charInfo;
/*     */     public GDChar gdChar;
/*     */   }
/*     */   
/*     */   public static void findChars(GDStashFrame frame, GDCharFileInfo reloadInfo) {
/*  36 */     List<GDCharFileInfo> infos = gdCharFileInfos;
/*     */     
/*  38 */     gdCharFileInfos = new LinkedList<>();
/*     */     
/*  40 */     if (GDStashFrame.iniConfig == null)
/*  41 */       return;  if (GDStashFrame.iniConfig.sectDir.savePath == null)
/*     */       return; 
/*  43 */     String mainDir = GDStashFrame.iniConfig.sectDir.savePath + GDConstants.FILE_SEPARATOR + "main";
/*  44 */     findCharsInDir(mainDir);
/*     */     
/*  46 */     String modDir = GDStashFrame.iniConfig.sectDir.savePath + GDConstants.FILE_SEPARATOR + "user";
/*  47 */     findCharsInDir(modDir);
/*     */     
/*  49 */     adjustCharInfos(infos, reloadInfo);
/*     */     
/*  51 */     if (frame != null) {
/*  52 */       if (frame.pnlCharEdit != null) frame.pnlCharEdit.refreshCharSelection(); 
/*  53 */       if (frame.pnlCharInventory != null) frame.pnlCharInventory.refreshCharSelection(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void findCharsInDir(String charDir) {
/*  58 */     File file = new File(charDir);
/*     */     
/*  60 */     if (file.isDirectory()) {
/*  61 */       processCharDir(file);
/*     */     }
/*     */     
/*  64 */     if (file.isFile()) {
/*  65 */       processCharFile(file);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processCharDir(File dir) {
/*  70 */     if (dir == null)
/*     */       return; 
/*  72 */     File[] files = dir.listFiles();
/*  73 */     if (files == null)
/*     */       return; 
/*  75 */     for (int i = 0; i < files.length; i++) {
/*  76 */       File file = files[i];
/*     */       
/*  78 */       if (file != null) {
/*  79 */         if (file.isDirectory()) processCharDir(file); 
/*  80 */         if (file.isFile()) processCharFile(file); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void processCharFile(File file) {
/*  86 */     String s = file.getName().toUpperCase(GDConstants.LOCALE_US);
/*     */     
/*  88 */     if (s.equals("PLAYER.GDC")) {
/*  89 */       GDCharSummary gdcSum = GDChar.readCharSummary(file);
/*     */       
/*  91 */       if (gdcSum != null) {
/*  92 */         File f = file.getParentFile();
/*     */         
/*     */         try {
/*  95 */           String dir = f.getCanonicalPath();
/*     */           
/*  97 */           int pos = dir.lastIndexOf(GDConstants.FILE_SEPARATOR);
/*  98 */           if (pos != -1) dir = dir.substring(pos + 1);
/*     */ 
/*     */           
/* 101 */           GDCharFileInfo info = new GDCharFileInfo();
/* 102 */           info.charFile = file;
/* 103 */           info.gdcSummary = gdcSum;
/*     */           
/* 105 */           gdCharFileInfos.add(info);
/*     */         }
/* 107 */         catch (IOException ex) {
/* 108 */           GDMsgLogger.addError(ex);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void adjustCharInfo(GDCharFileInfo info) {
/* 115 */     if (info.gdcSummary == null)
/*     */       return; 
/* 117 */     String dir = null;
/*     */     try {
/* 119 */       info.fileName = info.charFile.getCanonicalPath();
/*     */       
/* 121 */       File parent = info.charFile.getParentFile();
/* 122 */       dir = parent.getCanonicalPath();
/*     */     }
/* 124 */     catch (IOException ex) {
/* 125 */       info.fileName = null;
/* 126 */       dir = null;
/*     */     } 
/*     */     
/* 129 */     String sHC = null;
/* 130 */     String sExp = null;
/* 131 */     if (info.gdcSummary.isHardcore()) {
/* 132 */       sHC = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_CHAR_HARDCORE_SHORT");
/*     */     } else {
/* 134 */       sHC = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_CHAR_SOFTCORE_SHORT");
/*     */     } 
/*     */     
/* 137 */     sExp = info.gdcSummary.getExpansionInfo();
/*     */     
/* 139 */     dir = info.charFile.getParentFile().getParentFile().getName();
/*     */     
/* 141 */     Object[] sArgs = { info.gdcSummary.getCharName(), info.gdcSummary.getClassName(), dir, sHC, sExp, Integer.valueOf(info.gdcSummary.getLevel()) };
/* 142 */     String s = GDMsgFormatter.format(GDMsgFormatter.rbUI, "TXT_CHAR_SELECT", sArgs);
/*     */     
/* 144 */     s = "<html>" + s + "</html>";
/*     */     
/* 146 */     info.charInfo = s;
/*     */   }
/*     */   
/*     */   public static void adjustCharInfos(List<GDCharFileInfo> infos, GDCharFileInfo reloadInfo) {
/* 150 */     if (gdCharFileInfos == null)
/*     */       return; 
/* 152 */     for (GDCharFileInfo info : gdCharFileInfos) {
/* 153 */       adjustCharInfo(info);
/*     */ 
/*     */       
/* 156 */       if (infos != null) {
/* 157 */         for (GDCharFileInfo info2 : infos) {
/* 158 */           if (info.fileName.equals(info2.fileName) && 
/* 159 */             info2.gdChar != null && 
/* 160 */             info2 != reloadInfo && 
/* 161 */             info2.gdChar.hasChanged()) info.gdChar = info2.gdChar;
/*     */         
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 168 */       if (reloadInfo != null && 
/* 169 */         info.fileName.equals(reloadInfo.fileName)) {
/* 170 */         if (info.gdChar == null) {
/* 171 */           info.gdChar = new GDChar(info.charFile);
/*     */         }
/*     */         
/* 174 */         info.gdChar.read();
/*     */         
/* 176 */         if (info.gdChar.hasErrors()) info.gdChar = null;
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String[] getCharInfos() {
/* 183 */     int size = 1;
/*     */     
/* 185 */     if (gdCharFileInfos != null) {
/* 186 */       size += gdCharFileInfos.size();
/*     */     }
/*     */     
/* 189 */     String[] cis = new String[size];
/* 190 */     cis[0] = "";
/*     */     
/* 192 */     if (gdCharFileInfos == null) return cis; 
/* 193 */     if (gdCharFileInfos.size() == 0) return cis;
/*     */     
/* 195 */     int i = 1;
/* 196 */     for (GDCharFileInfo info : gdCharFileInfos) {
/* 197 */       cis[i] = info.charInfo;
/*     */       
/* 199 */       i++;
/*     */     } 
/*     */     
/* 202 */     return cis;
/*     */   }
/*     */   
/*     */   public static void updateCharSummary() {
/* 206 */     if (gdCharFileInfos == null)
/*     */       return; 
/* 208 */     for (GDCharFileInfo info : gdCharFileInfos) {
/* 209 */       if (info.gdcSummary == null)
/*     */         continue; 
/* 211 */       info.gdcSummary.updateClassName();
/*     */       
/* 213 */       adjustCharInfo(info);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\u\\util\GDCharInfoList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */