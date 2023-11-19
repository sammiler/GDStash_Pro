package org.gdstash.file;

import java.io.IOException;

public interface GDBuffer extends AutoCloseable {
  public static final int SIZE_1KB = 1024;
  
  public static final int SIZE_1MB = 1048576;
  
  public static final int SIZE_10MB = 10485760;
  
  public static final int SIZE_100MB = 104857600;
  
  byte[] getByteArray(int paramInt) throws IOException;
  
  byte[] getByteArray(long paramLong, int paramInt) throws IOException;
  
  byte getByte() throws IOException;
  
  byte[] getBytes8() throws IOException;
  
  int getInt() throws IOException;
  
  long getUnsignedInt() throws IOException;
  
  long getLong() throws IOException;
  
  short getShort() throws IOException;
  
  String getString(int paramInt) throws IOException;
  
  String getStringLB() throws IOException;
  
  String getStringNT() throws IOException;
  
  ARZString getARZString() throws IOException;
  
  long getPosition() throws IOException;
  
  void setPosition(long paramLong) throws IOException;
  
  void close() throws IOException;
}


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\file\GDBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */