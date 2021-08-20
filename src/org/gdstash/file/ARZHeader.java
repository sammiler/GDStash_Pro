package org.gdstash.file;

public class ARZHeader {
  public static final int SIZE = 24;
  
  public short unknown;
  
  public short version;
  
  public int rec_start;
  
  public int rec_size;
  
  public int rec_num;
  
  public int str_start;
  
  public int str_size;
}


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\file\ARZHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */