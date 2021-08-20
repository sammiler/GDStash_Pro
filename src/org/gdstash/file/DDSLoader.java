/*     */ package org.gdstash.file;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DDSLoader
/*     */ {
/*     */   public static BufferedImage load(String filename) {
/*  32 */     File file = new File(filename);
/*     */     
/*  34 */     if (!file.exists()) {
/*  35 */       return null;
/*     */     }
/*     */     
/*  38 */     if (!file.isFile()) {
/*  39 */       return null;
/*     */     }
/*     */     
/*  42 */     int len = (int)file.length();
/*  43 */     byte[] bytes = new byte[len];
/*     */     
/*     */     try {
/*  46 */       InputStream reader = new BufferedInputStream(new FileInputStream(file));
/*     */ 
/*     */       
/*  49 */       reader.read(bytes, 0, len);
/*     */       
/*  51 */       return getImage(bytes);
/*     */     }
/*  53 */     catch (FileNotFoundException fileNotFoundException) {
/*     */     
/*  55 */     } catch (IOException iOException) {
/*     */     
/*  57 */     } catch (GDParseException gDParseException) {}
/*     */ 
/*     */     
/*  60 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage getImage(byte[] bytes) throws GDParseException {
/*  67 */     BufferedImage image = null;
/*     */     
/*     */     try {
/*  70 */       TEXHeader texHdr = getTEXHeader(bytes);
/*  71 */       byte[] ddsBytes = getDDSBytes(bytes, texHdr);
/*  72 */       DDSHeader ddsHdr = getDDSHeader(ddsBytes);
/*     */       
/*  74 */       fixDDSHeader(ddsBytes, ddsHdr);
/*     */ 
/*     */       
/*  77 */       int[] pixels = DDSReader.read(ddsBytes, DDSReader.ARGB, 0);
/*  78 */       int width = DDSReader.getWidth(ddsBytes);
/*  79 */       int height = DDSReader.getHeight(ddsBytes);
/*  80 */       image = new BufferedImage(width, height, 2);
/*  81 */       image.setRGB(0, 0, width, height, pixels, 0, width);
/*     */     }
/*  83 */     catch (ArrayIndexOutOfBoundsException ex) {
/*  84 */       image = null;
/*     */     } 
/*     */     
/*  87 */     return image;
/*     */   }
/*     */   
/*     */   public static BufferedImage getScaledImage(BufferedImage image, int w, int h) {
/*  91 */     BufferedImage imgResized = new BufferedImage(w, h, image.getType());
/*  92 */     Graphics2D g2 = imgResized.createGraphics();
/*     */     
/*     */     try {
/*  95 */       g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/*  96 */       g2.drawImage(image, 0, 0, w, h, null);
/*     */     } finally {
/*     */       
/*  99 */       g2.dispose();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     return imgResized;
/*     */   }
/*     */   
/*     */   private static TEXHeader getTEXHeader(byte[] bytes) throws GDParseException {
/* 126 */     TEXHeader header = new TEXHeader();
/*     */     
/* 128 */     header.version = GDReader.getBytes4(bytes, 0);
/* 129 */     header.unknown = GDReader.getUInt(bytes, 4);
/* 130 */     header.size = GDReader.getUInt(bytes, 8);
/*     */     
/* 132 */     if (header.version[3] > 2) {
/* 133 */       throw new GDParseException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"), 0L);
/*     */     }
/*     */     
/* 136 */     return header;
/*     */   }
/*     */   
/*     */   private static byte[] getDDSBytes(byte[] bytes, TEXHeader texHdr) {
/* 140 */     byte[] b = GDReader.getBytes(bytes, 12, texHdr.size);
/*     */     
/* 142 */     return b;
/*     */   }
/*     */   
/*     */   private static DDSHeader getDDSHeader(byte[] bytes) throws GDParseException {
/* 146 */     DDSHeader header = new DDSHeader();
/*     */     
/* 148 */     int offset = 0;
/*     */     
/* 150 */     header.version = GDReader.getBytes4(bytes, offset);
/* 151 */     offset += 4;
/*     */     
/* 153 */     header.size = GDReader.getUInt(bytes, offset);
/* 154 */     offset += 4;
/*     */     
/* 156 */     if (header.size != 124) {
/* 157 */       throw new GDParseException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_HD_SIZE"), 0L);
/*     */     }
/*     */     
/* 160 */     header.flags = GDReader.getUInt(bytes, offset);
/* 161 */     offset += 4;
/*     */     
/* 163 */     header.height = GDReader.getUInt(bytes, offset);
/* 164 */     offset += 4;
/*     */     
/* 166 */     header.width = GDReader.getUInt(bytes, offset);
/* 167 */     offset += 4;
/*     */     
/* 169 */     header.linearSize = GDReader.getUInt(bytes, offset);
/* 170 */     offset += 4;
/*     */     
/* 172 */     header.depth = GDReader.getUInt(bytes, offset);
/* 173 */     offset += 4;
/*     */     
/* 175 */     header.num_mipmap = GDReader.getUInt(bytes, offset);
/* 176 */     offset += 4;
/*     */     
/* 178 */     for (int i = 0; i < header.reserved1.length; i++) {
/* 179 */       header.reserved1[i] = GDReader.getUInt(bytes, offset);
/* 180 */       offset += 4;
/*     */     } 
/*     */ 
/*     */     
/* 184 */     header.pixelFormat.size = GDReader.getUInt(bytes, offset);
/* 185 */     offset += 4;
/*     */     
/* 187 */     if (header.pixelFormat.size != 32) {
/* 188 */       throw new GDParseException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_PIX_SIZE"), 0L);
/*     */     }
/*     */     
/* 191 */     header.pixelFormat.flags = GDReader.getUInt(bytes, offset);
/* 192 */     offset += 4;
/*     */     
/* 194 */     header.pixelFormat.fourCC = GDReader.getUInt(bytes, offset);
/* 195 */     offset += 4;
/*     */     
/* 197 */     header.pixelFormat.rgbBitCount = GDReader.getUInt(bytes, offset);
/* 198 */     offset += 4;
/*     */     
/* 200 */     header.pixelFormat.rBitMask = GDReader.getUInt(bytes, offset);
/* 201 */     offset += 4;
/*     */     
/* 203 */     header.pixelFormat.gBitMask = GDReader.getUInt(bytes, offset);
/* 204 */     offset += 4;
/*     */     
/* 206 */     header.pixelFormat.bBitMask = GDReader.getUInt(bytes, offset);
/* 207 */     offset += 4;
/*     */     
/* 209 */     header.pixelFormat.aBitMask = GDReader.getUInt(bytes, offset);
/* 210 */     offset += 4;
/*     */ 
/*     */     
/* 213 */     header.caps = GDReader.getUInt(bytes, offset);
/* 214 */     offset += 4;
/*     */     
/* 216 */     header.caps2 = GDReader.getUInt(bytes, offset);
/* 217 */     offset += 4;
/*     */     
/* 219 */     header.caps3 = GDReader.getUInt(bytes, offset);
/* 220 */     offset += 4;
/*     */     
/* 222 */     header.caps4 = GDReader.getUInt(bytes, offset);
/* 223 */     offset += 4;
/*     */     
/* 225 */     header.reserved2 = GDReader.getUInt(bytes, offset);
/* 226 */     offset += 4;
/*     */     
/* 228 */     return header;
/*     */   }
/*     */   
/*     */   private static void fixDDSHeader(byte[] bytes, DDSHeader header) {
/* 232 */     if (header.version[3] == 82) {
/* 233 */       bytes[3] = 32;
/*     */     }
/*     */     
/* 236 */     int flags = header.flags | 0x1 | 0x2 | 0x4 | 0x1000;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 242 */     int caps = header.caps | 0x1000;
/*     */     
/* 244 */     if (header.num_mipmap > 1) {
/* 245 */       header.flags |= 0x20000;
/* 246 */       header.caps = header.caps | 0x8 | 0x400000;
/*     */     } 
/*     */     
/* 249 */     if ((header.caps2 & 0x200) != 0) {
/* 250 */       caps |= 0x8;
/*     */     }
/*     */     
/* 253 */     if (header.depth > 1) {
/* 254 */       flags |= 0x800000;
/* 255 */       caps |= 0x8;
/*     */     } 
/*     */     
/* 258 */     if ((header.pixelFormat.flags & 0x4) != 0 && header.linearSize != 0)
/*     */     {
/* 260 */       flags |= 0x80000;
/*     */     }
/*     */     
/* 263 */     if (((header.pixelFormat.flags & 0x40) == 64 || (header.pixelFormat.flags & 0x200) == 512 || (header.pixelFormat.flags & 0x20000) == 131072 || (header.pixelFormat.flags & 0x2) == 2) && header.linearSize != 0)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 268 */       flags |= 0x8;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 284 */     writeBytes(bytes, 8, flags);
/*     */     
/* 286 */     writeBytes(bytes, 80, header.pixelFormat.flags);
/*     */     
/* 288 */     writeBytes(bytes, 92, 16711680);
/* 289 */     writeBytes(bytes, 96, 65280);
/* 290 */     writeBytes(bytes, 100, 255);
/* 291 */     writeBytes(bytes, 104, 255);
/*     */     
/* 293 */     writeBytes(bytes, 108, caps);
/*     */   }
/*     */   
/*     */   private static void writeBytes(byte[] bytes, int offset, byte[] value) {
/* 297 */     for (int i = 0; i < value.length; i++) {
/* 298 */       bytes[i + offset] = value[i];
/*     */     }
/*     */   }
/*     */   
/*     */   private static void writeBytes(byte[] bytes, int offset, int value) {
/* 303 */     ByteBuffer buffer = ByteBuffer.allocate(4);
/* 304 */     buffer.order(ByteOrder.LITTLE_ENDIAN);
/* 305 */     buffer.putInt(0, value);
/*     */     
/* 307 */     byte[] b = buffer.array();
/*     */     
/* 309 */     writeBytes(bytes, offset, b);
/*     */   }
/*     */   
/*     */   private static void writeFile(String filename, byte[] data) {
/* 313 */     int pos = 0;
/* 314 */     pos = filename.lastIndexOf(".");
/* 315 */     if (pos == -1)
/*     */       return; 
/* 317 */     String ddsname = filename.substring(0, pos) + ".dds";
/*     */     
/* 319 */     File file = new File(ddsname);
/*     */     
/*     */     try {
/* 322 */       file.createNewFile();
/*     */       
/* 324 */       FileOutputStream writer = new FileOutputStream(file);
/*     */       
/* 326 */       writer.write(data);
/* 327 */       writer.flush();
/* 328 */       writer.close();
/*     */     }
/* 330 */     catch (IOException ex) {
/* 331 */       GDMsgLogger.addError(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\file\DDSLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */