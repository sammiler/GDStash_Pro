/*     */ package org.gdstash.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.PropertyResourceBundle;
/*     */ import java.util.ResourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDMsgFormatter
/*     */ {
/*     */   private static String resLanguage;
/*     */   private static String language;
/*     */   private static String country;
/*     */   public static Locale locale;
/*     */   private static MessageFormat msgFormatter;
/*     */   public static ResourceBundle rbGD;
/*     */   public static ResourceBundle rbMsg;
/*     */   public static ResourceBundle rbUI;
/*     */   public static ResourceBundle rbHTML;
/*     */   
/*     */   public static void readHTMLResourceBundle() throws IOException {
/*  34 */     if (rbHTML != null)
/*     */       return; 
/*  36 */     String fnHTML = GDConstants.USER_DIR + GDConstants.FILE_SEPARATOR + "HTMLTags.properties";
/*     */     
/*  38 */     try (InputStreamReader reader = new InputStreamReader(new FileInputStream(new File(fnHTML)), GDConstants.CHARSET_PROPERTIES)) {
/*  39 */       rbHTML = new PropertyResourceBundle(reader);
/*     */     }
/*  41 */     catch (IOException ex) {
/*  42 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setGDLocale(String language) throws IOException {
/*  47 */     if (language == null)
/*     */       return; 
/*  49 */     if (resLanguage != null && resLanguage.equals(language)) {
/*     */       return;
/*     */     }
/*     */     
/*  53 */     resLanguage = language;
/*     */     
/*  55 */     String fnGD = GDConstants.USER_DIR + GDConstants.FILE_SEPARATOR + "GrimDawn_" + language + ".properties";
/*  56 */     String fnUI = GDConstants.USER_DIR + GDConstants.FILE_SEPARATOR + "UI_" + language + ".properties";
/*  57 */     String fnMsg = GDConstants.USER_DIR + GDConstants.FILE_SEPARATOR + "Messages_" + language + ".properties";
/*     */     
/*  59 */     try (InputStreamReader reader = new InputStreamReader(new FileInputStream(new File(fnGD)), GDConstants.CHARSET_PROPERTIES)) {
/*  60 */       rbGD = new PropertyResourceBundle(reader);
/*     */     }
/*  62 */     catch (IOException ex) {
/*  63 */       throw ex;
/*     */     } 
/*     */     
/*  66 */     try (InputStreamReader reader = new InputStreamReader(new FileInputStream(new File(fnUI)), GDConstants.CHARSET_PROPERTIES)) {
/*  67 */       rbUI = new PropertyResourceBundle(reader);
/*     */     }
/*  69 */     catch (IOException ex) {
/*  70 */       throw ex;
/*     */     } 
/*     */     
/*  73 */     try (InputStreamReader reader = new InputStreamReader(new FileInputStream(new File(fnMsg)), GDConstants.CHARSET_PROPERTIES)) {
/*  74 */       rbMsg = new PropertyResourceBundle(reader);
/*     */     }
/*  76 */     catch (IOException ex) {
/*  77 */       throw ex;
/*     */     } 
/*     */     
/*  80 */     GDMsgFormatter.language = getString(rbUI, "LANGUAGE");
/*  81 */     country = getString(rbUI, "COUNTRY");
/*     */     
/*  83 */     locale = new Locale(GDMsgFormatter.language, country);
/*  84 */     msgFormatter = new MessageFormat("", locale);
/*     */     
/*  86 */     Locale.setDefault(Locale.Category.DISPLAY, locale);
/*     */   }
/*     */   
/*     */   public static String format_alt(ResourceBundle bundle, String tag, Object[] args) {
/*  90 */     String s = bundle.getString(tag);
/*     */     
/*  92 */     return format(s, args);
/*     */   }
/*     */   
/*     */   public static String format(String text, Object[] args) {
/*  96 */     String s = text;
/*     */     
/*  98 */     if (s == null) return s; 
/*  99 */     if (args == null) return s; 
/* 100 */     if (args.length == 0) return s;
/*     */     
/* 102 */     int pos1 = 0;
/* 103 */     int pos2 = 0;
/*     */     
/* 105 */     pos1 = s.indexOf("{");
/*     */     
/* 107 */     if (pos1 == -1) return s;
/*     */     
/* 109 */     String start = "";
/* 110 */     String rest = s;
/* 111 */     String pattern = null;
/* 112 */     String format1 = null;
/* 113 */     String format2 = null;
/* 114 */     String index = null;
/* 115 */     String value = null;
/*     */     
/* 117 */     while (pos1 != -1) {
/* 118 */       pos2 = rest.indexOf("}");
/*     */       
/* 120 */       if (pos2 == -1) {
/* 121 */         start = start + rest;
/*     */         
/* 123 */         rest = "";
/*     */       } else {
/* 125 */         start = start + rest.substring(0, pos1);
/* 126 */         pattern = rest.substring(pos1 + 1, pos2);
/* 127 */         rest = rest.substring(pos2 + 1);
/*     */         
/* 129 */         pos1 = pattern.indexOf(",");
/* 130 */         if (pos1 == -1) {
/* 131 */           index = pattern;
/* 132 */           format1 = null;
/*     */         } else {
/* 134 */           index = pattern.substring(0, pos1).trim();
/* 135 */           format1 = pattern.substring(pos1 + 1).trim();
/*     */         } 
/*     */         
/* 138 */         int idx = -1;
/*     */         try {
/* 140 */           idx = Integer.parseInt(index);
/*     */         }
/* 142 */         catch (NumberFormatException ex) {
/* 143 */           idx = -1;
/*     */         } 
/*     */         
/* 146 */         if (idx == -1) {
/* 147 */           value = index;
/*     */         }
/* 149 */         else if (format1 == null) {
/*     */           try {
/* 151 */             value = (String)args[idx];
/*     */           }
/* 153 */           catch (ArrayIndexOutOfBoundsException ex) {
/* 154 */             value = index;
/*     */           } 
/*     */         } else {
/* 157 */           pos1 = format1.indexOf(",");
/*     */           
/* 159 */           if (pos1 == -1) {
/* 160 */             format2 = null;
/*     */           } else {
/* 162 */             format2 = format1.substring(pos1 + 1).trim();
/* 163 */             format1 = format1.substring(0, pos1).trim();
/*     */           } 
/*     */           
/* 166 */           if (format1.equals("number")) {
/* 167 */             if (format2 == null) {
/* 168 */               value = (String)args[idx];
/*     */             } else {
/* 170 */               double d = 0.0D;
/* 171 */               boolean found = false;
/*     */               
/* 173 */               if (args[idx].getClass().equals(Double.class)) {
/* 174 */                 d = ((Double)args[idx]).doubleValue();
/* 175 */                 found = true;
/*     */               } 
/*     */               
/* 178 */               if (args[idx].getClass().equals(Float.class)) {
/* 179 */                 float f = ((Float)args[idx]).floatValue();
/*     */                 
/* 181 */                 d = f;
/* 182 */                 found = true;
/*     */               } 
/*     */               
/* 185 */               if (args[idx].getClass().equals(Integer.class)) {
/* 186 */                 int i = ((Integer)args[idx]).intValue();
/*     */                 
/* 188 */                 d = i;
/* 189 */                 found = true;
/*     */               } 
/*     */               
/* 192 */               if (!found) {
/* 193 */                 value = (String)args[idx];
/*     */               }
/* 195 */               else if (format2.equals("integer")) {
/* 196 */                 value = Integer.toString((int)d);
/*     */               } else {
/*     */                 try {
/* 199 */                   DecimalFormat df = new DecimalFormat(format2);
/* 200 */                   value = df.format(d);
/* 201 */                 } catch (IllegalArgumentException ex) {
/* 202 */                   value = index;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } else {
/*     */             
/* 208 */             value = index;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 213 */         start = start + value;
/*     */       } 
/*     */       
/* 216 */       pos1 = rest.indexOf("{");
/*     */     } 
/*     */     
/* 219 */     s = start;
/*     */     
/* 221 */     return s;
/*     */   }
/*     */   
/*     */   public static String format(ResourceBundle bundle, String tag, Object[] args) {
/* 225 */     String s = null;
/*     */     
/*     */     try {
/* 228 */       msgFormatter.applyPattern(bundle.getString(tag));
/* 229 */       s = msgFormatter.format(args);
/*     */     }
/* 231 */     catch (MissingResourceException ex) {
/* 232 */       Object[] errargs = { tag };
/* 233 */       String msg = format(rbMsg, "ERR_TAG_UNKNOWN", errargs);
/*     */       
/* 235 */       GDMsgLogger.addError(msg);
/*     */       
/* 237 */       return "";
/*     */     }
/* 239 */     catch (IllegalArgumentException ex) {
/* 240 */       Object[] errargs = { tag };
/* 241 */       String msg = format(rbMsg, "ERR_PATTERN_WRONG", errargs);
/*     */       
/* 243 */       GDMsgLogger.addError(msg);
/*     */       
/* 245 */       return "";
/*     */     }
/* 247 */     catch (StringIndexOutOfBoundsException ex) {
/* 248 */       Object[] errargs = { tag };
/* 249 */       String msg = format(rbMsg, "ERR_PATTERN_WRONG", errargs);
/*     */       
/* 251 */       GDMsgLogger.addError(msg);
/*     */       
/* 253 */       return "";
/*     */     } 
/* 255 */     return s;
/*     */   }
/*     */   
/*     */   public static String getString(ResourceBundle bundle, String tag) {
/* 259 */     String s = null;
/*     */     
/*     */     try {
/* 262 */       s = bundle.getString(tag);
/*     */     }
/* 264 */     catch (MissingResourceException ex) {
/* 265 */       Object[] errargs = { tag };
/* 266 */       String msg = format(rbMsg, "ERR_TAG_UNKNOWN", errargs);
/*     */       
/* 268 */       GDMsgLogger.addError(msg);
/*     */       
/* 270 */       return "";
/*     */     } 
/*     */     
/* 273 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\util\GDMsgFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */