package org.gdstash.file;

public class DDSPixelFormat {
  public static final int HEADER_SIZE = 32;
  
  public static final int ALPHA_PIXELS = 1;
  
  public static final int ALPHA = 2;
  
  public static final int FOUR_CC = 4;
  
  public static final int PALETTE_IDX = 32;
  
  public static final int RGB = 64;
  
  public static final int YUV = 512;
  
  public static final int LUMINANCE = 131072;
  
  public static final int R_BITMASK = 16711680;
  
  public static final int G_BITMASK = 65280;
  
  public static final int B_BITMASK = 255;
  
  public int size;
  
  public int flags;
  
  public int fourCC;
  
  public int rgbBitCount;
  
  public int rBitMask;
  
  public int gBitMask;
  
  public int bBitMask;
  
  public int aBitMask;
}


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\file\DDSPixelFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */