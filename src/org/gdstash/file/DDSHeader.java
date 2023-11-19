/*    */ package org.gdstash.file;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DDSHeader
/*    */ {
/* 59 */   public byte[] version = new byte[4];
/* 60 */   public DDSPixelFormat pixelFormat = new DDSPixelFormat();
/* 61 */   public int[] reserved1 = new int[11];
/*    */   public static final int HEADER_SIZE = 124;
/*    */   public static final int VERSION_DEFAULT = 32;
/*    */   public static final int VERSION_REVERSED = 82;
/*    */   public static final int HEADER_CAPS = 1;
/*    */   public static final int HEADER_HEIGHT = 2;
/*    */   public static final int HEADER_WIDTH = 4;
/*    */   public static final int HEADER_PITCH = 8;
/*    */   public static final int HEADER_PIXELFORMAT = 4096;
/*    */   public static final int HEADER_MIPMAP_COUNT = 131072;
/*    */   public static final int HEADER_LINEAR_SIZE = 524288;
/*    */   public static final int HEADER_DEPTH = 8388608;
/*    */   public static final int CAPS_COMPLEX = 8;
/*    */   public static final int CAPS_TEXTURE = 4096;
/*    */   public static final int CAPS_MIPMAP = 4194304;
/*    */   public static final int CAPS2_CUBEMAP = 512;
/*    */   public static final int CAPS2_CUBE_POS_X = 1024;
/*    */   public static final int CAPS2_CUBE_NEG_X = 2048;
/*    */   public static final int CAPS2_CUBE_POS_Y = 4096;
/*    */   public static final int CAPS2_CUBE_NEG_Y = 8192;
/*    */   public static final int CAPS2_CUBE_POS_Z = 16384;
/*    */   public static final int CAPS2_CUBE_NEG_Z = 32768;
/*    */   public static final int CAPS2_VOLUME = 2097152;
/*    */   public int size;
/*    */   public int flags;
/*    */   public int height;
/*    */   public int width;
/*    */   public int linearSize;
/*    */   public int depth;
/*    */   public int num_mipmap;
/*    */   public int caps;
/*    */   public int caps2;
/*    */   public int caps3;
/*    */   public int caps4;
/*    */   public int reserved2;
/*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\file\DDSHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */