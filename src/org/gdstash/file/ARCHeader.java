package org.gdstash.file;

public class ARCHeader {
  public static final int SIZE = 28;
  
  public int unknown;
  
  public int version;
  
  public int numFiles;
  
  public int numRecords;
  
  public long sizeRecords;
  
  public long sizeStrings;
  
  public long offsetRecords;
}


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\file\ARCHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */