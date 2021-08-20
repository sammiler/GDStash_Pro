/*     */ package org.gdstash.file;
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
/*     */ public final class DDSReader
/*     */ {
/*  23 */   public static final Order ARGB = new Order(16, 8, 0, 24);
/*  24 */   public static final Order ABGR = new Order(0, 8, 16, 24); private static final int DXT1 = 1146639409; private static final int DXT2 = 1146639410; private static final int DXT3 = 1146639411; private static final int DXT4 = 1146639412; private static final int DXT5 = 1146639413; private static final int A1R5G5B5 = 65538; private static final int X1R5G5B5 = 131074;
/*     */   
/*     */   public static int getHeight(byte[] buffer) {
/*  27 */     return buffer[12] & 0xFF | (buffer[13] & 0xFF) << 8 | (buffer[14] & 0xFF) << 16 | (buffer[15] & 0xFF) << 24;
/*     */   }
/*     */   private static final int A4R4G4B4 = 196610; private static final int X4R4G4B4 = 262146; private static final int R5G6B5 = 327682; private static final int R8G8B8 = 65539; private static final int A8B8G8R8 = 65540; private static final int X8B8G8R8 = 131076; private static final int A8R8G8B8 = 196612; private static final int X8R8G8B8 = 262148;
/*     */   public static int getWidth(byte[] buffer) {
/*  31 */     return buffer[16] & 0xFF | (buffer[17] & 0xFF) << 8 | (buffer[18] & 0xFF) << 16 | (buffer[19] & 0xFF) << 24;
/*     */   }
/*     */   
/*     */   public static int getMipmap(byte[] buffer) {
/*  35 */     return buffer[28] & 0xFF | (buffer[29] & 0xFF) << 8 | (buffer[30] & 0xFF) << 16 | (buffer[31] & 0xFF) << 24;
/*     */   }
/*     */   
/*     */   public static int getPixelFormatFlags(byte[] buffer) {
/*  39 */     return buffer[80] & 0xFF | (buffer[81] & 0xFF) << 8 | (buffer[82] & 0xFF) << 16 | (buffer[83] & 0xFF) << 24;
/*     */   }
/*     */   
/*     */   public static int getFourCC(byte[] buffer) {
/*  43 */     return (buffer[84] & 0xFF) << 24 | (buffer[85] & 0xFF) << 16 | (buffer[86] & 0xFF) << 8 | buffer[87] & 0xFF;
/*     */   }
/*     */   
/*     */   public static int getBitCount(byte[] buffer) {
/*  47 */     return buffer[88] & 0xFF | (buffer[89] & 0xFF) << 8 | (buffer[90] & 0xFF) << 16 | (buffer[91] & 0xFF) << 24;
/*     */   }
/*     */   
/*     */   public static int getRedMask(byte[] buffer) {
/*  51 */     return buffer[92] & 0xFF | (buffer[93] & 0xFF) << 8 | (buffer[94] & 0xFF) << 16 | (buffer[95] & 0xFF) << 24;
/*     */   }
/*     */   
/*     */   public static int getGreenMask(byte[] buffer) {
/*  55 */     return buffer[96] & 0xFF | (buffer[97] & 0xFF) << 8 | (buffer[98] & 0xFF) << 16 | (buffer[99] & 0xFF) << 24;
/*     */   }
/*     */   
/*     */   public static int getBlueMask(byte[] buffer) {
/*  59 */     return buffer[100] & 0xFF | (buffer[101] & 0xFF) << 8 | (buffer[102] & 0xFF) << 16 | (buffer[103] & 0xFF) << 24;
/*     */   }
/*     */   
/*     */   public static int getAlphaMask(byte[] buffer) {
/*  63 */     return buffer[104] & 0xFF | (buffer[105] & 0xFF) << 8 | (buffer[106] & 0xFF) << 16 | (buffer[107] & 0xFF) << 24;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] read(byte[] buffer, Order order, int mipmapLevel) {
/*  69 */     int width = getWidth(buffer);
/*  70 */     int height = getHeight(buffer);
/*  71 */     int mipmap = getMipmap(buffer);
/*     */ 
/*     */     
/*  74 */     int type = getType(buffer);
/*  75 */     if (type == 0) return null;
/*     */ 
/*     */     
/*  78 */     int offset = 128;
/*  79 */     if (mipmapLevel > 0 && mipmapLevel < mipmap) {
/*  80 */       for (int i = 0; i < mipmapLevel; i++) {
/*  81 */         switch (type) { case 1146639409:
/*  82 */             offset += 8 * (width + 3) / 4 * (height + 3) / 4; break;
/*     */           case 1146639410: case 1146639411:
/*     */           case 1146639412:
/*     */           case 1146639413:
/*  86 */             offset += 16 * (width + 3) / 4 * (height + 3) / 4; break;
/*     */           case 65538: case 65539:
/*     */           case 65540:
/*     */           case 131074:
/*     */           case 131076:
/*     */           case 196610:
/*     */           case 196612:
/*     */           case 262146:
/*     */           case 262148:
/*     */           case 327682:
/*  96 */             offset += (type & 0xFF) * width * height; break; }
/*     */         
/*  98 */         width /= 2;
/*  99 */         height /= 2;
/*     */       } 
/* 101 */       if (width <= 0) width = 1; 
/* 102 */       if (height <= 0) height = 1;
/*     */     
/*     */     } 
/* 105 */     int[] pixels = null;
/* 106 */     switch (type) { case 1146639409:
/* 107 */         pixels = decodeDXT1(width, height, offset, buffer, order); break;
/* 108 */       case 1146639410: pixels = decodeDXT2(width, height, offset, buffer, order); break;
/* 109 */       case 1146639411: pixels = decodeDXT3(width, height, offset, buffer, order); break;
/* 110 */       case 1146639412: pixels = decodeDXT4(width, height, offset, buffer, order); break;
/* 111 */       case 1146639413: pixels = decodeDXT5(width, height, offset, buffer, order); break;
/* 112 */       case 65538: pixels = readA1R5G5B5(width, height, offset, buffer, order); break;
/* 113 */       case 131074: pixels = readX1R5G5B5(width, height, offset, buffer, order); break;
/* 114 */       case 196610: pixels = readA4R4G4B4(width, height, offset, buffer, order); break;
/* 115 */       case 262146: pixels = readX4R4G4B4(width, height, offset, buffer, order); break;
/* 116 */       case 327682: pixels = readR5G6B5(width, height, offset, buffer, order); break;
/* 117 */       case 65539: pixels = readR8G8B8(width, height, offset, buffer, order); break;
/* 118 */       case 65540: pixels = readA8B8G8R8(width, height, offset, buffer, order); break;
/* 119 */       case 131076: pixels = readX8B8G8R8(width, height, offset, buffer, order); break;
/* 120 */       case 196612: pixels = readA8R8G8B8(width, height, offset, buffer, order); break;
/* 121 */       case 262148: pixels = readA8R8G8B8(width, height, offset, buffer, order);
/*     */         break; }
/*     */     
/* 124 */     return pixels;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getType(byte[] buffer) {
/* 129 */     int type = 0;
/*     */     
/* 131 */     int flags = getPixelFormatFlags(buffer);
/*     */     
/* 133 */     if ((flags & 0x4) != 0) {
/*     */       
/* 135 */       type = getFourCC(buffer);
/*     */     }
/* 137 */     else if ((flags & 0x40) != 0) {
/*     */       
/* 139 */       int bitCount = getBitCount(buffer);
/* 140 */       int redMask = getRedMask(buffer);
/* 141 */       int greenMask = getGreenMask(buffer);
/* 142 */       int blueMask = getBlueMask(buffer);
/* 143 */       int alphaMask = ((flags & 0x1) != 0) ? getAlphaMask(buffer) : 0;
/* 144 */       if (bitCount == 16) {
/* 145 */         if (redMask == A1R5G5B5_MASKS[0] && greenMask == A1R5G5B5_MASKS[1] && blueMask == A1R5G5B5_MASKS[2] && alphaMask == A1R5G5B5_MASKS[3])
/*     */         {
/* 147 */           type = 65538;
/*     */         }
/* 149 */         else if (redMask == X1R5G5B5_MASKS[0] && greenMask == X1R5G5B5_MASKS[1] && blueMask == X1R5G5B5_MASKS[2] && alphaMask == X1R5G5B5_MASKS[3])
/*     */         {
/* 151 */           type = 131074;
/*     */         }
/* 153 */         else if (redMask == A4R4G4B4_MASKS[0] && greenMask == A4R4G4B4_MASKS[1] && blueMask == A4R4G4B4_MASKS[2] && alphaMask == A4R4G4B4_MASKS[3])
/*     */         {
/* 155 */           type = 196610;
/*     */         }
/* 157 */         else if (redMask == X4R4G4B4_MASKS[0] && greenMask == X4R4G4B4_MASKS[1] && blueMask == X4R4G4B4_MASKS[2] && alphaMask == X4R4G4B4_MASKS[3])
/*     */         {
/* 159 */           type = 262146;
/*     */         }
/* 161 */         else if (redMask == R5G6B5_MASKS[0] && greenMask == R5G6B5_MASKS[1] && blueMask == R5G6B5_MASKS[2] && alphaMask == R5G6B5_MASKS[3])
/*     */         {
/* 163 */           type = 327682;
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 169 */       else if (bitCount == 24) {
/* 170 */         if (redMask == R8G8B8_MASKS[0] && greenMask == R8G8B8_MASKS[1] && blueMask == R8G8B8_MASKS[2] && alphaMask == R8G8B8_MASKS[3])
/*     */         {
/* 172 */           type = 65539;
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 178 */       else if (bitCount == 32) {
/* 179 */         if (redMask == A8B8G8R8_MASKS[0] && greenMask == A8B8G8R8_MASKS[1] && blueMask == A8B8G8R8_MASKS[2] && alphaMask == A8B8G8R8_MASKS[3]) {
/*     */           
/* 181 */           type = 65540;
/*     */         }
/* 183 */         else if (redMask == X8B8G8R8_MASKS[0] && greenMask == X8B8G8R8_MASKS[1] && blueMask == X8B8G8R8_MASKS[2] && alphaMask == X8B8G8R8_MASKS[3]) {
/*     */           
/* 185 */           type = 131076;
/*     */         }
/* 187 */         else if (redMask == A8R8G8B8_MASKS[0] && greenMask == A8R8G8B8_MASKS[1] && blueMask == A8R8G8B8_MASKS[2] && alphaMask == A8R8G8B8_MASKS[3]) {
/*     */           
/* 189 */           type = 196612;
/*     */         }
/* 191 */         else if (redMask == X8R8G8B8_MASKS[0] && greenMask == X8R8G8B8_MASKS[1] && blueMask == X8R8G8B8_MASKS[2] && alphaMask == X8R8G8B8_MASKS[3]) {
/*     */           
/* 193 */           type = 262148;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 204 */     return type;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] decodeDXT1(int width, int height, int offset, byte[] buffer, Order order) {
/* 209 */     int[] pixels = new int[width * height];
/* 210 */     int index = offset;
/* 211 */     int w = (width + 3) / 4;
/* 212 */     int h = (height + 3) / 4;
/* 213 */     for (int i = 0; i < h; i++) {
/* 214 */       for (int j = 0; j < w; j++) {
/* 215 */         int c0 = buffer[index] & 0xFF | (buffer[index + 1] & 0xFF) << 8; index += 2;
/* 216 */         int c1 = buffer[index] & 0xFF | (buffer[index + 1] & 0xFF) << 8; index += 2;
/* 217 */         for (int k = 0; k < 4 && 
/* 218 */           4 * i + k < height; k++) {
/* 219 */           int t0 = buffer[index] & 0x3;
/* 220 */           int t1 = (buffer[index] & 0xC) >> 2;
/* 221 */           int t2 = (buffer[index] & 0x30) >> 4;
/* 222 */           int t3 = (buffer[index++] & 0xC0) >> 6;
/* 223 */           pixels[4 * width * i + 4 * j + width * k + 0] = getDXTColor(c0, c1, 255, t0, order);
/* 224 */           if (4 * j + 1 < width) {
/* 225 */             pixels[4 * width * i + 4 * j + width * k + 1] = getDXTColor(c0, c1, 255, t1, order);
/* 226 */             if (4 * j + 2 < width)
/* 227 */             { pixels[4 * width * i + 4 * j + width * k + 2] = getDXTColor(c0, c1, 255, t2, order);
/* 228 */               if (4 * j + 3 < width)
/* 229 */                 pixels[4 * width * i + 4 * j + width * k + 3] = getDXTColor(c0, c1, 255, t3, order);  } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 233 */     }  return pixels;
/*     */   }
/*     */   
/*     */   private static int[] decodeDXT2(int width, int height, int offset, byte[] buffer, Order order) {
/* 237 */     return decodeDXT3(width, height, offset, buffer, order);
/*     */   }
/*     */   
/*     */   private static int[] decodeDXT3(int width, int height, int offset, byte[] buffer, Order order) {
/* 241 */     int index = offset;
/* 242 */     int w = (width + 3) / 4;
/* 243 */     int h = (height + 3) / 4;
/* 244 */     int[] pixels = new int[width * height];
/* 245 */     int[] alphaTable = new int[16];
/* 246 */     for (int i = 0; i < h; i++) {
/* 247 */       for (int j = 0; j < w; j++) {
/*     */         
/* 249 */         for (int k = 0; k < 4; k++) {
/* 250 */           int a0 = buffer[index++] & 0xFF;
/* 251 */           int a1 = buffer[index++] & 0xFF;
/*     */           
/* 253 */           alphaTable[4 * k + 0] = 17 * ((a0 & 0xF0) >> 4);
/* 254 */           alphaTable[4 * k + 1] = 17 * (a0 & 0xF);
/* 255 */           alphaTable[4 * k + 2] = 17 * ((a1 & 0xF0) >> 4);
/* 256 */           alphaTable[4 * k + 3] = 17 * (a1 & 0xF);
/*     */         } 
/* 258 */         int c0 = buffer[index] & 0xFF | (buffer[index + 1] & 0xFF) << 8; index += 2;
/* 259 */         int c1 = buffer[index] & 0xFF | (buffer[index + 1] & 0xFF) << 8; index += 2;
/* 260 */         for (int m = 0; m < 4 && 
/* 261 */           4 * i + m < height; m++) {
/* 262 */           int t0 = buffer[index] & 0x3;
/* 263 */           int t1 = (buffer[index] & 0xC) >> 2;
/* 264 */           int t2 = (buffer[index] & 0x30) >> 4;
/* 265 */           int t3 = (buffer[index++] & 0xC0) >> 6;
/* 266 */           pixels[4 * width * i + 4 * j + width * m + 0] = getDXTColor(c0, c1, alphaTable[4 * m + 0], t0, order);
/* 267 */           if (4 * j + 1 < width) {
/* 268 */             pixels[4 * width * i + 4 * j + width * m + 1] = getDXTColor(c0, c1, alphaTable[4 * m + 1], t1, order);
/* 269 */             if (4 * j + 2 < width)
/* 270 */             { pixels[4 * width * i + 4 * j + width * m + 2] = getDXTColor(c0, c1, alphaTable[4 * m + 2], t2, order);
/* 271 */               if (4 * j + 3 < width)
/* 272 */                 pixels[4 * width * i + 4 * j + width * m + 3] = getDXTColor(c0, c1, alphaTable[4 * m + 3], t3, order);  } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 276 */     }  return pixels;
/*     */   }
/*     */   
/*     */   private static int[] decodeDXT4(int width, int height, int offset, byte[] buffer, Order order) {
/* 280 */     return decodeDXT5(width, height, offset, buffer, order);
/*     */   }
/*     */   
/*     */   private static int[] decodeDXT5(int width, int height, int offset, byte[] buffer, Order order) {
/* 284 */     int index = offset;
/* 285 */     int w = (width + 3) / 4;
/* 286 */     int h = (height + 3) / 4;
/* 287 */     int[] pixels = new int[width * height];
/* 288 */     int[] alphaTable = new int[16];
/* 289 */     for (int i = 0; i < h; i++) {
/* 290 */       for (int j = 0; j < w; j++) {
/*     */         
/* 292 */         int a0 = buffer[index++] & 0xFF;
/* 293 */         int a1 = buffer[index++] & 0xFF;
/* 294 */         int b0 = buffer[index] & 0xFF | (buffer[index + 1] & 0xFF) << 8 | (buffer[index + 2] & 0xFF) << 16; index += 3;
/* 295 */         int b1 = buffer[index] & 0xFF | (buffer[index + 1] & 0xFF) << 8 | (buffer[index + 2] & 0xFF) << 16; index += 3;
/* 296 */         alphaTable[0] = b0 & 0x7;
/* 297 */         alphaTable[1] = b0 >> 3 & 0x7;
/* 298 */         alphaTable[2] = b0 >> 6 & 0x7;
/* 299 */         alphaTable[3] = b0 >> 9 & 0x7;
/* 300 */         alphaTable[4] = b0 >> 12 & 0x7;
/* 301 */         alphaTable[5] = b0 >> 15 & 0x7;
/* 302 */         alphaTable[6] = b0 >> 18 & 0x7;
/* 303 */         alphaTable[7] = b0 >> 21 & 0x7;
/* 304 */         alphaTable[8] = b1 & 0x7;
/* 305 */         alphaTable[9] = b1 >> 3 & 0x7;
/* 306 */         alphaTable[10] = b1 >> 6 & 0x7;
/* 307 */         alphaTable[11] = b1 >> 9 & 0x7;
/* 308 */         alphaTable[12] = b1 >> 12 & 0x7;
/* 309 */         alphaTable[13] = b1 >> 15 & 0x7;
/* 310 */         alphaTable[14] = b1 >> 18 & 0x7;
/* 311 */         alphaTable[15] = b1 >> 21 & 0x7;
/* 312 */         int c0 = buffer[index] & 0xFF | (buffer[index + 1] & 0xFF) << 8; index += 2;
/* 313 */         int c1 = buffer[index] & 0xFF | (buffer[index + 1] & 0xFF) << 8; index += 2;
/* 314 */         for (int k = 0; k < 4 && 
/* 315 */           4 * i + k < height; k++) {
/* 316 */           int t0 = buffer[index] & 0x3;
/* 317 */           int t1 = (buffer[index] & 0xC) >> 2;
/* 318 */           int t2 = (buffer[index] & 0x30) >> 4;
/* 319 */           int t3 = (buffer[index++] & 0xC0) >> 6;
/* 320 */           pixels[4 * width * i + 4 * j + width * k + 0] = getDXTColor(c0, c1, getDXT5Alpha(a0, a1, alphaTable[4 * k + 0]), t0, order);
/* 321 */           if (4 * j + 1 < width) {
/* 322 */             pixels[4 * width * i + 4 * j + width * k + 1] = getDXTColor(c0, c1, getDXT5Alpha(a0, a1, alphaTable[4 * k + 1]), t1, order);
/* 323 */             if (4 * j + 2 < width)
/* 324 */             { pixels[4 * width * i + 4 * j + width * k + 2] = getDXTColor(c0, c1, getDXT5Alpha(a0, a1, alphaTable[4 * k + 2]), t2, order);
/* 325 */               if (4 * j + 3 < width)
/* 326 */                 pixels[4 * width * i + 4 * j + width * k + 3] = getDXTColor(c0, c1, getDXT5Alpha(a0, a1, alphaTable[4 * k + 3]), t3, order);  } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 330 */     }  return pixels;
/*     */   }
/*     */   
/*     */   private static int[] readA1R5G5B5(int width, int height, int offset, byte[] buffer, Order order) {
/* 334 */     int index = offset;
/* 335 */     int[] pixels = new int[width * height];
/* 336 */     for (int i = 0; i < height * width; i++) {
/* 337 */       int rgba = buffer[index] & 0xFF | (buffer[index + 1] & 0xFF) << 8; index += 2;
/* 338 */       int r = BIT5[(rgba & A1R5G5B5_MASKS[0]) >> 10];
/* 339 */       int g = BIT5[(rgba & A1R5G5B5_MASKS[1]) >> 5];
/* 340 */       int b = BIT5[rgba & A1R5G5B5_MASKS[2]];
/* 341 */       int a = 255 * ((rgba & A1R5G5B5_MASKS[3]) >> 15);
/* 342 */       pixels[i] = a << order.alphaShift | r << order.redShift | g << order.greenShift | b << order.blueShift;
/*     */     } 
/* 344 */     return pixels;
/*     */   }
/*     */   
/*     */   private static int[] readX1R5G5B5(int width, int height, int offset, byte[] buffer, Order order) {
/* 348 */     int index = offset;
/* 349 */     int[] pixels = new int[width * height];
/* 350 */     for (int i = 0; i < height * width; i++) {
/* 351 */       int rgba = buffer[index] & 0xFF | (buffer[index + 1] & 0xFF) << 8; index += 2;
/* 352 */       int r = BIT5[(rgba & X1R5G5B5_MASKS[0]) >> 10];
/* 353 */       int g = BIT5[(rgba & X1R5G5B5_MASKS[1]) >> 5];
/* 354 */       int b = BIT5[rgba & X1R5G5B5_MASKS[2]];
/* 355 */       int a = 255;
/* 356 */       pixels[i] = a << order.alphaShift | r << order.redShift | g << order.greenShift | b << order.blueShift;
/*     */     } 
/* 358 */     return pixels;
/*     */   }
/*     */   
/*     */   private static int[] readA4R4G4B4(int width, int height, int offset, byte[] buffer, Order order) {
/* 362 */     int index = offset;
/* 363 */     int[] pixels = new int[width * height];
/* 364 */     for (int i = 0; i < height * width; i++) {
/* 365 */       int rgba = buffer[index] & 0xFF | (buffer[index + 1] & 0xFF) << 8; index += 2;
/* 366 */       int r = 17 * ((rgba & A4R4G4B4_MASKS[0]) >> 8);
/* 367 */       int g = 17 * ((rgba & A4R4G4B4_MASKS[1]) >> 4);
/* 368 */       int b = 17 * (rgba & A4R4G4B4_MASKS[2]);
/* 369 */       int a = 17 * ((rgba & A4R4G4B4_MASKS[3]) >> 12);
/* 370 */       pixels[i] = a << order.alphaShift | r << order.redShift | g << order.greenShift | b << order.blueShift;
/*     */     } 
/* 372 */     return pixels;
/*     */   }
/*     */   
/*     */   private static int[] readX4R4G4B4(int width, int height, int offset, byte[] buffer, Order order) {
/* 376 */     int index = offset;
/* 377 */     int[] pixels = new int[width * height];
/* 378 */     for (int i = 0; i < height * width; i++) {
/* 379 */       int rgba = buffer[index] & 0xFF | (buffer[index + 1] & 0xFF) << 8; index += 2;
/* 380 */       int r = 17 * ((rgba & A4R4G4B4_MASKS[0]) >> 8);
/* 381 */       int g = 17 * ((rgba & A4R4G4B4_MASKS[1]) >> 4);
/* 382 */       int b = 17 * (rgba & A4R4G4B4_MASKS[2]);
/* 383 */       int a = 255;
/* 384 */       pixels[i] = a << order.alphaShift | r << order.redShift | g << order.greenShift | b << order.blueShift;
/*     */     } 
/* 386 */     return pixels;
/*     */   }
/*     */   
/*     */   private static int[] readR5G6B5(int width, int height, int offset, byte[] buffer, Order order) {
/* 390 */     int index = offset;
/* 391 */     int[] pixels = new int[width * height];
/* 392 */     for (int i = 0; i < height * width; i++) {
/* 393 */       int rgba = buffer[index] & 0xFF | (buffer[index + 1] & 0xFF) << 8; index += 2;
/* 394 */       int r = BIT5[(rgba & R5G6B5_MASKS[0]) >> 11];
/* 395 */       int g = BIT6[(rgba & R5G6B5_MASKS[1]) >> 5];
/* 396 */       int b = BIT5[rgba & R5G6B5_MASKS[2]];
/* 397 */       int a = 255;
/* 398 */       pixels[i] = a << order.alphaShift | r << order.redShift | g << order.greenShift | b << order.blueShift;
/*     */     } 
/* 400 */     return pixels;
/*     */   }
/*     */   
/*     */   private static int[] readR8G8B8(int width, int height, int offset, byte[] buffer, Order order) {
/* 404 */     int index = offset;
/* 405 */     int[] pixels = new int[width * height];
/* 406 */     for (int i = 0; i < height * width; i++) {
/* 407 */       int b = buffer[index++] & 0xFF;
/* 408 */       int g = buffer[index++] & 0xFF;
/* 409 */       int r = buffer[index++] & 0xFF;
/* 410 */       int a = 255;
/* 411 */       pixels[i] = a << order.alphaShift | r << order.redShift | g << order.greenShift | b << order.blueShift;
/*     */     } 
/* 413 */     return pixels;
/*     */   }
/*     */   
/*     */   private static int[] readA8B8G8R8(int width, int height, int offset, byte[] buffer, Order order) {
/* 417 */     int index = offset;
/* 418 */     int[] pixels = new int[width * height];
/* 419 */     for (int i = 0; i < height * width; i++) {
/* 420 */       int r = buffer[index++] & 0xFF;
/* 421 */       int g = buffer[index++] & 0xFF;
/* 422 */       int b = buffer[index++] & 0xFF;
/* 423 */       int a = buffer[index++] & 0xFF;
/* 424 */       pixels[i] = a << order.alphaShift | r << order.redShift | g << order.greenShift | b << order.blueShift;
/*     */     } 
/* 426 */     return pixels;
/*     */   }
/*     */   
/*     */   private static int[] readX8B8G8R8(int width, int height, int offset, byte[] buffer, Order order) {
/* 430 */     int index = offset;
/* 431 */     int[] pixels = new int[width * height];
/* 432 */     for (int i = 0; i < height * width; i++) {
/* 433 */       int r = buffer[index++] & 0xFF;
/* 434 */       int g = buffer[index++] & 0xFF;
/* 435 */       int b = buffer[index++] & 0xFF;
/* 436 */       int a = 255; index++;
/* 437 */       pixels[i] = a << order.alphaShift | r << order.redShift | g << order.greenShift | b << order.blueShift;
/*     */     } 
/* 439 */     return pixels;
/*     */   }
/*     */   
/*     */   private static int[] readA8R8G8B8(int width, int height, int offset, byte[] buffer, Order order) {
/* 443 */     int index = offset;
/* 444 */     int[] pixels = new int[width * height];
/* 445 */     for (int i = 0; i < height * width; i++) {
/* 446 */       int b = buffer[index++] & 0xFF;
/* 447 */       int g = buffer[index++] & 0xFF;
/* 448 */       int r = buffer[index++] & 0xFF;
/* 449 */       int a = buffer[index++] & 0xFF;
/* 450 */       pixels[i] = a << order.alphaShift | r << order.redShift | g << order.greenShift | b << order.blueShift;
/*     */     } 
/* 452 */     return pixels;
/*     */   }
/*     */   
/*     */   private static int[] readX8R8G8B8(int width, int height, int offset, byte[] buffer, Order order) {
/* 456 */     int index = offset;
/* 457 */     int[] pixels = new int[width * height];
/* 458 */     for (int i = 0; i < height * width; i++) {
/* 459 */       int b = buffer[index++] & 0xFF;
/* 460 */       int g = buffer[index++] & 0xFF;
/* 461 */       int r = buffer[index++] & 0xFF;
/* 462 */       int a = 255; index++;
/* 463 */       pixels[i] = a << order.alphaShift | r << order.redShift | g << order.greenShift | b << order.blueShift;
/*     */     } 
/* 465 */     return pixels;
/*     */   }
/*     */   
/*     */   private static int getDXTColor(int c0, int c1, int a, int t, Order order) {
/* 469 */     switch (t) { case 0:
/* 470 */         return getDXTColor1(c0, a, order);
/* 471 */       case 1: return getDXTColor1(c1, a, order);
/* 472 */       case 2: return (c0 > c1) ? getDXTColor2_1(c0, c1, a, order) : getDXTColor1_1(c0, c1, a, order);
/* 473 */       case 3: return (c0 > c1) ? getDXTColor2_1(c1, c0, a, order) : 0; }
/*     */     
/* 475 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getDXTColor2_1(int c0, int c1, int a, Order order) {
/* 480 */     int r = (2 * BIT5[(c0 & 0xFC00) >> 11] + BIT5[(c1 & 0xFC00) >> 11]) / 3;
/* 481 */     int g = (2 * BIT6[(c0 & 0x7E0) >> 5] + BIT6[(c1 & 0x7E0) >> 5]) / 3;
/* 482 */     int b = (2 * BIT5[c0 & 0x1F] + BIT5[c1 & 0x1F]) / 3;
/* 483 */     return a << order.alphaShift | r << order.redShift | g << order.greenShift | b << order.blueShift;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getDXTColor1_1(int c0, int c1, int a, Order order) {
/* 488 */     int r = (BIT5[(c0 & 0xFC00) >> 11] + BIT5[(c1 & 0xFC00) >> 11]) / 2;
/* 489 */     int g = (BIT6[(c0 & 0x7E0) >> 5] + BIT6[(c1 & 0x7E0) >> 5]) / 2;
/* 490 */     int b = (BIT5[c0 & 0x1F] + BIT5[c1 & 0x1F]) / 2;
/* 491 */     return a << order.alphaShift | r << order.redShift | g << order.greenShift | b << order.blueShift;
/*     */   }
/*     */   
/*     */   private static int getDXTColor1(int c, int a, Order order) {
/* 495 */     int r = BIT5[(c & 0xFC00) >> 11];
/* 496 */     int g = BIT6[(c & 0x7E0) >> 5];
/* 497 */     int b = BIT5[c & 0x1F];
/* 498 */     return a << order.alphaShift | r << order.redShift | g << order.greenShift | b << order.blueShift;
/*     */   }
/*     */   
/*     */   private static int getDXT5Alpha(int a0, int a1, int t) {
/* 502 */     if (a0 > a1) { switch (t) { case 0:
/* 503 */           return a0;
/* 504 */         case 1: return a1;
/* 505 */         case 2: return (6 * a0 + a1) / 7;
/* 506 */         case 3: return (5 * a0 + 2 * a1) / 7;
/* 507 */         case 4: return (4 * a0 + 3 * a1) / 7;
/* 508 */         case 5: return (3 * a0 + 4 * a1) / 7;
/* 509 */         case 6: return (2 * a0 + 5 * a1) / 7;
/* 510 */         case 7: return (a0 + 6 * a1) / 7; }
/*     */        }
/* 512 */     else { switch (t) { case 0:
/* 513 */           return a0;
/* 514 */         case 1: return a1;
/* 515 */         case 2: return (4 * a0 + a1) / 5;
/* 516 */         case 3: return (3 * a0 + 2 * a1) / 5;
/* 517 */         case 4: return (2 * a0 + 3 * a1) / 5;
/* 518 */         case 5: return (a0 + 4 * a1) / 5;
/* 519 */         case 6: return 0;
/* 520 */         case 7: return 255; }
/*     */        }
/* 522 */      return 0;
/*     */   }
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
/* 543 */   private static final int[] A1R5G5B5_MASKS = new int[] { 31744, 992, 31, 32768 };
/* 544 */   private static final int[] X1R5G5B5_MASKS = new int[] { 31744, 992, 31, 0 };
/* 545 */   private static final int[] A4R4G4B4_MASKS = new int[] { 3840, 240, 15, 61440 };
/* 546 */   private static final int[] X4R4G4B4_MASKS = new int[] { 3840, 240, 15, 0 };
/* 547 */   private static final int[] R5G6B5_MASKS = new int[] { 63488, 2016, 31, 0 };
/* 548 */   private static final int[] R8G8B8_MASKS = new int[] { 16711680, 65280, 255, 0 };
/* 549 */   private static final int[] A8B8G8R8_MASKS = new int[] { 255, 65280, 16711680, -16777216 };
/* 550 */   private static final int[] X8B8G8R8_MASKS = new int[] { 255, 65280, 16711680, 0 };
/* 551 */   private static final int[] A8R8G8B8_MASKS = new int[] { 16711680, 65280, 255, -16777216 };
/* 552 */   private static final int[] X8R8G8B8_MASKS = new int[] { 16711680, 65280, 255, 0 };
/*     */ 
/*     */   
/* 555 */   private static final int[] BIT5 = new int[] { 0, 8, 16, 25, 33, 41, 49, 58, 66, 74, 82, 90, 99, 107, 115, 123, 132, 140, 148, 156, 165, 173, 181, 189, 197, 206, 214, 222, 230, 239, 247, 255 };
/* 556 */   private static final int[] BIT6 = new int[] { 0, 4, 8, 12, 16, 20, 24, 28, 32, 36, 40, 45, 49, 53, 57, 61, 65, 69, 73, 77, 81, 85, 89, 93, 97, 101, 105, 109, 113, 117, 121, 125, 130, 134, 138, 142, 146, 150, 154, 158, 162, 166, 170, 174, 178, 182, 186, 190, 194, 198, 202, 206, 210, 215, 219, 223, 227, 231, 235, 239, 243, 247, 251, 255 };
/*     */   private static final class Order { public int redShift; public int greenShift;
/*     */     public int blueShift;
/*     */     public int alphaShift;
/*     */     
/*     */     Order(int redShift, int greenShift, int blueShift, int alphaShift) {
/* 562 */       this.redShift = redShift;
/* 563 */       this.greenShift = greenShift;
/* 564 */       this.blueShift = blueShift;
/* 565 */       this.alphaShift = alphaShift;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\file\DDSReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */