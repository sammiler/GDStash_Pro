/*     */ package org.gdstash.file;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.db.DBBitmap;
/*     */ import org.gdstash.db.DBItem;
/*     */ import org.gdstash.util.GDConstants;
/*     */ 
/*     */ 
/*     */ public class ARCList
/*     */ {
/*     */   private ListType listType;
/*     */   private List<ARCDecompress> list;
/*     */   
/*     */   public enum ListType
/*     */   {
/*  19 */     Image, Text, Mixed; }
/*     */   
/*     */   public static class ARCFile {
/*     */     public ARCDecompress.FileModule module;
/*     */     public String filename;
/*     */     
/*     */     public ARCFile(ARCDecompress.FileModule module, String filename) {
/*  26 */       this.module = module;
/*  27 */       this.filename = filename;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ARCList(String dirMod, List<ARCFile> arcs, ListType listType) {
/*  35 */     this.listType = ListType.Mixed;
/*     */     
/*  37 */     if (listType == ListType.Text) this.listType = ListType.Text; 
/*  38 */     if (listType == ListType.Image) this.listType = ListType.Image;
/*     */     
/*  40 */     this.list = new LinkedList<>();
/*     */     
/*  42 */     if (dirMod != null && 
/*  43 */       !dirMod.isEmpty()) {
/*  44 */       File file = new File(dirMod);
/*     */       
/*  46 */       if (file.exists()) {
/*  47 */         File[] files = null;
/*     */         
/*  49 */         if (file.isDirectory()) {
/*  50 */           files = file.listFiles();
/*     */         }
/*     */         
/*  53 */         if (file.isFile()) {
/*  54 */           files = new File[1];
/*     */           
/*  56 */           files[0] = file;
/*     */         } 
/*     */         int i;
/*  59 */         for (i = 0; i < files.length; i++) {
/*     */           try {
/*  61 */             String s = files[i].getCanonicalPath();
/*  62 */             String t = s.toUpperCase(GDConstants.LOCALE_US);
/*     */             
/*  64 */             if (t.endsWith(".ZIP") || t
/*  65 */               .endsWith("TEXT_EN.ARC")) {
/*  66 */               if (this.listType != ListType.Image) {
/*  67 */                 ARCDecompress arc = new ARCDecompress(ARCDecompress.FileModule.Mod, s);
/*     */                 
/*  69 */                 this.list.add(arc);
/*     */               }
/*     */             
/*  72 */             } else if (t.endsWith(".ARC") && 
/*  73 */               this.listType != ListType.Text) {
/*  74 */               ARCDecompress arc = new ARCDecompress(ARCDecompress.FileModule.Mod, s);
/*     */               
/*  76 */               this.list.add(arc);
/*     */             
/*     */             }
/*     */           
/*     */           }
/*  81 */           catch (IOException iOException) {}
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  87 */     if (arcs != null) {
/*  88 */       for (ARCFile arcFile : arcs) {
/*  89 */         if (arcFile == null || 
/*  90 */           arcFile.filename == null)
/*     */           continue; 
/*  92 */         String t = arcFile.filename.toUpperCase(GDConstants.LOCALE_US);
/*     */         
/*  94 */         if (t.endsWith(".ZIP") || t
/*  95 */           .endsWith("TEXT_EN.ARC")) {
/*  96 */           if (this.listType != ListType.Image) {
/*  97 */             ARCDecompress arc = new ARCDecompress(arcFile.module, arcFile.filename);
/*     */             
/*  99 */             this.list.add(arc);
/*     */           }  continue;
/*     */         } 
/* 102 */         if (t.endsWith(".ARC") && 
/* 103 */           this.listType != ListType.Text) {
/* 104 */           ARCDecompress arc = new ARCDecompress(arcFile.module, arcFile.filename);
/*     */           
/* 106 */           this.list.add(arc);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decompress() {
/* 115 */     for (ARCDecompress arc : this.list) {
/* 116 */       arc.decompress(null);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeFiles(String dir, boolean writeVanilla) {
/* 121 */     for (ARCDecompress arc : this.list) {
/* 122 */       String fn = arc.getFileName();
/*     */       
/* 124 */       int pos = fn.indexOf(GDConstants.FILE_SEPARATOR + "mods" + GDConstants.FILE_SEPARATOR);
/* 125 */       if (pos == -1 && !writeVanilla)
/*     */         continue; 
/* 127 */       pos = fn.lastIndexOf(GDConstants.FILE_SEPARATOR);
/* 128 */       if (pos == -1) pos = 0;
/*     */       
/* 130 */       String subdir = fn.substring(pos);
/*     */       
/* 132 */       subdir = subdir.substring(0, subdir.length() - 4).toLowerCase();
/*     */       
/* 134 */       arc.writeFiles(dir, subdir);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateImageData() {
/* 139 */     for (ARCDecompress arc : this.list) {
/* 140 */       arc.updateImageData(null);
/*     */     }
/*     */     
/* 143 */     DBBitmap.deleteNoImageBitmaps();
/* 144 */     DBItem.deleteNoImageItems();
/*     */   }
/*     */   
/*     */   public String getTag(ARCDecompress.FileModule module, String filename, String tag, boolean insertColor) {
/* 148 */     String[] filenames = new String[1];
/* 149 */     filenames[0] = filename;
/*     */     
/* 151 */     return getTag(module, filenames, tag, insertColor);
/*     */   }
/*     */   
/*     */   public String getTag(ARCDecompress.FileModule module, String[] filenames, String tag, boolean insertColor) {
/* 155 */     if (tag == null) return null;
/*     */     
/* 157 */     List<String> listFN = new LinkedList<>();
/*     */     int i;
/* 159 */     for (i = 0; i < filenames.length; i++) {
/* 160 */       listFN.add(filenames[i]);
/*     */     }
/*     */     
/* 163 */     String result = null;
/*     */     
/* 165 */     if (module == ARCDecompress.FileModule.All)
/* 166 */     { for (ARCDecompress arc : this.list) {
/* 167 */         result = arc.getTag(listFN, tag, insertColor);
/*     */         
/* 169 */         if (result != null)
/*     */           break; 
/*     */       }  }
/* 172 */     else { for (ARCDecompress arc : this.list) {
/* 173 */         if (module != arc.getModule())
/*     */           continue; 
/* 175 */         result = arc.getTag(listFN, tag, insertColor);
/*     */         
/* 177 */         if (result != null) {
/*     */           break;
/*     */         }
/*     */       } 
/* 181 */       if (result == null)
/* 182 */         for (ARCDecompress arc : this.list) {
/*     */           
/* 184 */           if (module == arc.getModule())
/*     */             continue; 
/* 186 */           result = arc.getTag(listFN, tag, insertColor);
/*     */           
/* 188 */           if (result != null) {
/*     */             break;
/*     */           }
/*     */         }   }
/*     */     
/* 193 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\file\ARCList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */