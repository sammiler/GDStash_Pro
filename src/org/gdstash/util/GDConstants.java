/*    */ package org.gdstash.util;
/*    */ 
/*    */ import java.nio.charset.Charset;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface GDConstants
/*    */ {
/* 15 */   public static final String[] TAG_TEXT_CREATURES = new String[] { "tagsgdx2_creatures.txt", "tagsgdx1_creatures.txt", "tags_creatures.txt" };
/* 16 */   public static final String[] TAG_TEXT_ITEMS = new String[] { "tagsgdx2_items.txt", "tagsgdx1_items.txt", "tags_items.txt", "tagsgdx2_endlessdungeon.txt" };
/* 17 */   public static final String[] TAG_TEXT_SHRINES = new String[] { "tagsgdx2_storyelements.txt", "tagsgdx1_storyelements.txt", "tags_storyelements.txt" };
/* 18 */   public static final String[] TAG_TEXT_SKILLS = new String[] { "tagsgdx2_skills.txt", "tagsgdx1_skills.txt", "tags_skills.txt" };
/* 19 */   public static final String[] TAG_TEXT_PETS = new String[] { "tagsgdx2_creatures.txt", "tagsgdx1_creatures.txt", "tags_creatures.txt" };
/* 20 */   public static final String[] TAG_TEXT_STORY = new String[] { "tagsgdx2_storyelements.txt", "tagsgdx1_storyelements.txt", "tags_storyelements.txt" };
/* 21 */   public static final String[] TAG_TEXT_UI = new String[] { "tagsgdx2_ui.txt", "tagsgdx1_ui.txt", "tags_ui.txt" };
/*    */   
/*    */   public static final char TEXT_SEPARATOR_00 = '\000';
/*    */   
/*    */   public static final char TEXT_SEPARATOR_LINE_FEED = '\n';
/*    */   public static final char TEXT_SEPARATOR_CARRIAGE_RETURN = '\r';
/*    */   public static final char TEXT_SEPARATOR_TAB = '\t';
/* 28 */   public static final String FILE_SEPARATOR = System.getProperty("file.separator");
/* 29 */   public static final String LINE_SEPARATOR = System.getProperty("line.separator");
/* 30 */   public static final String USER_DIR = System.getProperty("user.dir");
/* 31 */   public static final String USER_HOME = System.getProperty("user.home");
/*    */   
/*    */   public static final String STR_CHARSET_PROPERTIES = "UTF-8";
/*    */   
/*    */   public static final String STR_CHARSET_STASH = "UTF-8";
/*    */   
/*    */   public static final String STR_CHARSET_WIDE = "UTF-16LE";
/*    */   
/*    */   public static final String STRLEN_ID = "256";
/*    */   public static final String STRLEN_ID_X2 = "512";
/*    */   public static final String STRLEN_TAG = "64";
/*    */   public static final String STRLEN_NAME = "256";
/*    */   public static final String STRLEN_DESC = "1024";
/*    */   public static final String STRLEN_DESC_LONG = "8000";
/*    */   public static final String STRLEN_FORMULA = "256";
/*    */   public static final String STRLEN_CONVERT = "16";
/*    */   public static final String STRLEN_CLASSIFY = "32";
/*    */   public static final String STRLEN_TEXT = "64";
/*    */   public static final String STRLEN_STAT = "64";
/*    */   public static final int BLOB_SIZE_IMAGE = 131072;
/*    */   public static final String BLOB_SIZE_IMAGE_KB = "128K";
/*    */   public static final int BLOB_SIZE_SHARD = 32768;
/*    */   public static final String BLOB_SIZE_SHARD_KB = "32K";
/*    */   public static final int BLOB_SIZE_BITMAP = 33554432;
/*    */   public static final String BLOB_SIZE_BITMAP_MB = "32M";
/* 56 */   public static final Charset CHARSET_PROPERTIES = Charset.forName("UTF-8");
/* 57 */   public static final Charset CHARSET_STASH = Charset.forName("UTF-8");
/* 58 */   public static final Charset CHARSET_WIDE = Charset.forName("UTF-16LE");
/* 59 */   public static final Locale LOCALE_US = Locale.US;
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\util\GDConstants.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */