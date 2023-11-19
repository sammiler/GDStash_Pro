/*     */ package org.gdstash.character;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.file.GDReader;
/*     */ import org.gdstash.file.GDWriter;
/*     */ import org.gdstash.util.FileVersionException;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDCharTeleportList
/*     */ {
/*  20 */   private static final byte[] RIFTGATE_01_DEVILS_CROSSING = new byte[] { -73, Byte.MAX_VALUE, 11, Byte.MIN_VALUE, 41, 79, -108, -10, 80, 105, -9, -73, -41, 108, -25, 83 };
/*     */   
/*  22 */   private static final byte[] RIFTGATE_01_LOWER_CROSSING = new byte[] { 37, 51, -100, -49, -33, 66, -113, 56, -86, -2, -24, -100, 16, -26, -111, 63 };
/*     */   
/*  24 */   private static final byte[] RIFTGATE_01_WIGHTMIRE = new byte[] { -13, -19, 78, 35, -40, 79, -103, 113, 65, -93, -125, -123, -102, 38, -62, 82 };
/*     */   
/*  26 */   private static final byte[] RIFTGATE_01_FOGGY_BANK = new byte[] { 88, 48, 20, -29, 54, 68, -5, -14, 105, 2, -65, -102, -63, -117, -123, -18 };
/*     */   
/*  28 */   private static final byte[] RIFTGATE_01_FLOODED_PASSAGE = new byte[] { 46, 69, -87, -24, 60, 70, -125, 6, 23, 43, 40, -121, -94, 4, -19, -67 };
/*     */   
/*  30 */   private static final byte[] RIFTGATE_01_BURRWITCH_OUTSKIRTS = new byte[] { 59, 104, 21, -5, 28, 73, -91, -126, -93, -38, 35, -66, 72, -57, 105, Byte.MIN_VALUE };
/*     */   
/*  32 */   private static final byte[] RIFTGATE_01_BURRWITCH_VILLAGE = new byte[] { -36, -37, 64, 110, 94, 68, -66, -54, 71, 90, -92, -103, 5, 28, -42, -91 };
/*     */   
/*  34 */   private static final byte[] RIFTGATE_01_WARDENS_CELLAR = new byte[] { -99, 46, -79, -109, 90, 74, -74, 58, -8, 43, -56, -83, -81, 81, -120, 46 };
/*     */   
/*  36 */   private static final byte[] RIFTGATE_01_UNDERGROUND_TRANSIT = new byte[] { -127, -73, 112, -94, -17, 65, 124, -81, -52, 60, -91, -89, 73, -3, 71, 31 };
/*     */   
/*  38 */   private static final byte[] RIFTGATE_01_WARDENS_LABORATORY = new byte[] { -62, -125, 9, -98, -104, 73, 121, -2, 10, 49, -75, -119, 107, -24, 2, 12 };
/*     */   
/*  40 */   private static final byte[] RIFTGATE_02_ARKOVIAN_FOOTHILLS = new byte[] { 10, -22, -72, 54, 51, 77, 80, 120, -70, 12, -124, -94, -28, -95, 7, 64 };
/*     */   
/*  42 */   private static final byte[] RIFTGATE_02_OLD_ARKOVIA = new byte[] { -64, -6, -112, -77, 45, 67, 94, 114, 39, -65, -76, -125, 58, 73, 109, 32 };
/*     */   
/*  44 */   private static final byte[] RIFTGATE_02_CRONLEYS_HIDEOUT = new byte[] { 110, 114, -67, -69, -102, 72, 44, -84, 94, 49, 62, -80, 94, 85, -83, 16 };
/*     */   
/*  46 */   private static final byte[] RIFTGATE_02_TWIN_FALLS = new byte[] { 107, -71, -120, -46, -92, 78, 37, 120, 97, -44, 29, Byte.MIN_VALUE, -107, 69, 34, 6 };
/*     */   
/*  48 */   private static final byte[] RIFTGATE_02_BROKEN_HILLS = new byte[] { -97, -79, 6, 41, -106, 68, 99, -31, 4, 72, -67, -99, 71, 115, -62, 100 };
/*     */   
/*  50 */   private static final byte[] RIFTGATE_02_SMUGGLERS_PASS = new byte[] { 32, 53, 77, -34, 50, 72, 44, 121, -22, -85, -78, -73, -81, 96, -74, -16 };
/*     */   
/*  52 */   private static final byte[] RIFTGATE_03_DEADMANS_GULCH = new byte[] { -14, -31, -39, -62, -23, 68, -63, 114, 113, -101, -110, -108, 11, 95, -120, -110 };
/*     */   
/*  54 */   private static final byte[] RIFTGATE_03_PROSPECTORS_TRAIL = new byte[] { 19, 3, 65, 61, -24, 78, -85, -111, 52, -46, -59, -127, -55, 71, -30, 26 };
/*     */   
/*  56 */   private static final byte[] RIFTGATE_03_PINE_BARRENS = new byte[] { 86, -88, 102, 123, 48, 77, Byte.MAX_VALUE, 84, -46, -66, 110, -95, 114, 51, -29, -42 };
/*     */   
/*  58 */   private static final byte[] RIFTGATE_03_HOMESTEAD = new byte[] { -9, 24, -59, -22, 107, 76, -34, 41, 77, -59, -110, -122, -52, 64, 40, -28 };
/*     */   
/*  60 */   private static final byte[] RIFTGATE_03_ROTTING_CROPLANDS = new byte[] { 100, 101, -27, 60, -68, 75, 37, -54, -111, 79, 86, -100, 47, 49, -85, 116 };
/*     */   
/*  62 */   private static final byte[] RIFTGATE_03_SORROWS_BASTION = new byte[] { -36, 28, 70, 46, -14, 74, 121, -117, -19, -83, -35, -68, -63, -64, -3, 9 };
/*     */   
/*  64 */   private static final byte[] RIFTGATE_03_BLOOD_GROVE = new byte[] { -93, 35, 13, -126, 15, 75, -109, 15, -92, -72, 66, -102, -53, -20, 103, -86 };
/*     */   
/*  66 */   private static final byte[] RIFTGATE_03_DARKVALE_GATE = new byte[] { -58, -25, -106, 71, 91, 79, -20, -96, -37, 42, -1, -105, 112, 60, 91, -47 };
/*     */   
/*  68 */   private static final byte[] RIFTGATE_04_ASTERKARN_MOUNTAINS = new byte[] { -37, 49, -54, -17, 99, 76, 93, -123, 46, -116, 123, -70, 119, -24, 0, 42 };
/*     */   
/*  70 */   private static final byte[] RIFTGATE_04_ASTERKARN_ROAD = new byte[] { 104, -114, 40, -115, 125, 64, -5, 90, 43, 90, -28, -88, 126, -3, -71, -110 };
/*     */   
/*  72 */   private static final byte[] RIFTGATE_04_ASTERKARN_VALLEY = new byte[] { -69, -46, -124, -43, 31, 69, 28, -108, 61, -31, -13, -123, -46, 60, -46, 5 };
/*     */   
/*  74 */   private static final byte[] RIFTGATE_04_FORT_IKON = new byte[] { -61, 63, 25, 71, -116, 76, 39, 56, -74, -20, 15, -108, 101, 21, 100, -2 };
/*     */   
/*  76 */   private static final byte[] RIFTGATE_04_GATES_OF_NECROPOLIS = new byte[] { 100, -58, -44, -52, 21, 64, 93, 9, 27, -64, 5, -127, 10, 13, 30, -30 };
/*     */   
/*  78 */   private static final byte[] RIFTGATE_04_NECROPOLIS_INTERIOR = new byte[] { -42, -11, -58, 19, 58, 70, -107, -89, -95, 85, 103, -82, -32, -1, -37, -61 };
/*     */   
/*  80 */   private static final byte[] RIFTGATE_05_GLOOMWALD = new byte[] { -46, 102, -1, -19, 115, 79, -73, 39, -74, 15, -8, -92, -95, -83, -22, 9 };
/*     */   
/*  82 */   private static final byte[] RIFTGATE_05_GLOOMWALD_CROSSING = new byte[] { 47, -72, 51, 40, -85, 75, 61, 119, 25, -59, -17, -109, 97, -25, -8, 99 };
/*     */   
/*  84 */   private static final byte[] RIFTGATE_05_COVENS_REFUGE = new byte[] { -105, 61, 10, 53, 120, 66, -96, -97, -110, -123, -2, -66, 56, -69, -13, -2 };
/*     */   
/*  86 */   private static final byte[] RIFTGATE_05_UGDENBOG = new byte[] { -69, -42, 43, -85, -6, 73, -66, -60, -47, -18, 87, -99, 68, 50, -28, -82 };
/*     */   
/*  88 */   private static final byte[] RIFTGATE_05_BARROWHOLM = new byte[] { -76, 76, -88, 81, -79, 71, 4, 86, 94, -79, -1, -110, 95, 14, 96, 49 };
/*     */   
/*  90 */   private static final byte[] RIFTGATE_05_LONE_WATCH = new byte[] { -5, -47, -50, 94, 78, 70, 75, 85, -102, 1, -26, -123, 9, -82, -5, 17 };
/*     */   
/*  92 */   private static final byte[] RIFTGATE_05_MALMOUTH_OUTSKIRTS = new byte[] { -126, 33, -49, -109, 53, 65, 72, -31, 125, 119, -35, -80, 97, -7, -62, 50 };
/*     */   
/*  94 */   private static final byte[] RIFTGATE_05_MALMOUTH_SEWERS = new byte[] { -1, -93, 103, -127, -45, 69, 95, -53, 110, 82, -116, -77, -71, 94, 79, 107 };
/*     */   
/*  96 */   private static final byte[] RIFTGATE_05_MALMOUTH_STEELCAP = new byte[] { 41, 21, 58, -35, 84, 70, 80, 87, -49, -45, -108, -72, 43, 73, 77, -96 };
/*     */   
/*  98 */   private static final byte[] RIFTGATE_05_MALMOUTH_CROWN_HILL = new byte[] { -85, 55, 74, 122, -124, 76, -40, -52, -80, -67, 121, -66, 19, Byte.MAX_VALUE, 9, -94 };
/*     */   
/* 100 */   private static final byte[] RIFTGATE_06_CONCLAVE_THREE = new byte[] { -100, 104, -45, 84, 119, 65, 126, -49, 48, 115, -17, -118, 115, 86, -41, 100 };
/*     */   
/* 102 */   private static final byte[] RIFTGATE_06_KORVAN_PLATEAU = new byte[] { -49, 111, 34, Byte.MIN_VALUE, -45, 70, -103, 47, -121, -104, -59, -112, 74, -41, -47, 82 };
/*     */   
/* 104 */   private static final byte[] RIFTGATE_06_TEMPLE_OSYR = new byte[] { 41, 33, 83, 10, 99, 66, -72, -88, 91, -69, 26, -79, -62, 95, -82, -29 };
/*     */   
/* 106 */   private static final byte[] RIFTGATE_06_KORVAN_SANDS = new byte[] { 66, -101, -17, 99, -74, 75, 50, -53, -126, 92, 38, -99, -72, 32, 111, -12 };
/*     */   
/* 108 */   private static final byte[] RIFTGATE_06_CAIRAN_DOCKS = new byte[] { 13, -39, 116, -3, 59, 65, -74, 2, 12, -48, 80, -121, -113, -108, 105, -21 };
/*     */   
/* 110 */   private static final byte[] RIFTGATE_06_SUNBANE_OASIS = new byte[] { -9, -85, -69, 38, -68, 66, -44, Byte.MIN_VALUE, 21, -115, 56, -120, 115, -34, 45, -21 };
/*     */   
/* 112 */   private static final byte[] RIFTGATE_06_VANGUARD_THREE = new byte[] { -20, -2, 11, -48, 47, 78, -9, -68, -79, -117, 41, -124, -17, 48, 75, -64 };
/*     */   
/* 114 */   private static final byte[] RIFTGATE_06_RUINS_ABYD = new byte[] { -86, -82, -60, -122, -114, 79, 103, 73, -9, -54, -101, -107, 92, 11, -18, 110 };
/*     */   
/* 116 */   private static final byte[] RIFTGATE_06_INFERNAL_WASTES = new byte[] { 35, -108, 43, -8, 69, 78, 28, -76, -34, 118, -82, -80, 87, -16, -16, 117 };
/*     */   
/* 118 */   private static final byte[] RIFTGATE_06_KORVAN_CITY = new byte[] { 83, 46, -71, -96, -84, 67, 107, -64, -123, -74, -12, -84, 98, -13, -3, -64 };
/*     */   
/* 120 */   private static final byte[] RIFTGATE_06_ELDRITCH_SUN = new byte[] { -47, -30, 123, 15, Byte.MIN_VALUE, 76, 82, -69, -43, 57, 89, -100, -27, -26, -85, -27 };
/*     */   
/* 122 */   private static final byte[] RIFTGATE_06_ELDRITCH_GATE = new byte[] { 121, 117, 47, -17, 48, 75, -35, 4, -52, 104, -39, -78, -76, 92, -74, 1 };
/*     */   
/* 124 */   private static final byte[] RIFTGATE_06_LOST_OASIS = new byte[] { -39, 44, 124, 44, -13, 68, -95, -41, 124, -2, 9, -87, 10, 108, -53, 58 };
/*     */ 
/*     */   
/* 127 */   public static final GDCharUID UID_RIFT_01_DEVILS_CROSSING = new GDCharUID(RIFTGATE_01_DEVILS_CROSSING);
/* 128 */   public static final GDCharUID UID_RIFT_01_LOWER_CROSSING = new GDCharUID(RIFTGATE_01_LOWER_CROSSING);
/* 129 */   public static final GDCharUID UID_RIFT_01_WIGHTMIRE = new GDCharUID(RIFTGATE_01_WIGHTMIRE);
/* 130 */   public static final GDCharUID UID_RIFT_01_FOGGY_BANK = new GDCharUID(RIFTGATE_01_FOGGY_BANK);
/* 131 */   public static final GDCharUID UID_RIFT_01_FLOODED_PASSAGE = new GDCharUID(RIFTGATE_01_FLOODED_PASSAGE);
/* 132 */   public static final GDCharUID UID_RIFT_01_BURRWITCH_OUTSKIRTS = new GDCharUID(RIFTGATE_01_BURRWITCH_OUTSKIRTS);
/* 133 */   public static final GDCharUID UID_RIFT_01_BURRWITCH_VILLAGE = new GDCharUID(RIFTGATE_01_BURRWITCH_VILLAGE);
/* 134 */   public static final GDCharUID UID_RIFT_01_WARDENS_CELLAR = new GDCharUID(RIFTGATE_01_WARDENS_CELLAR);
/* 135 */   public static final GDCharUID UID_RIFT_01_UNDERGROUND_TRANSIT = new GDCharUID(RIFTGATE_01_UNDERGROUND_TRANSIT);
/* 136 */   public static final GDCharUID UID_RIFT_01_WARDENS_LABORATORY = new GDCharUID(RIFTGATE_01_WARDENS_LABORATORY);
/* 137 */   public static final GDCharUID UID_RIFT_02_ARKOVIAN_FOOTHILLS = new GDCharUID(RIFTGATE_02_ARKOVIAN_FOOTHILLS);
/* 138 */   public static final GDCharUID UID_RIFT_02_OLD_ARKOVIA = new GDCharUID(RIFTGATE_02_OLD_ARKOVIA);
/* 139 */   public static final GDCharUID UID_RIFT_02_CRONLEYS_HIDEOUT = new GDCharUID(RIFTGATE_02_CRONLEYS_HIDEOUT);
/* 140 */   public static final GDCharUID UID_RIFT_02_TWIN_FALLS = new GDCharUID(RIFTGATE_02_TWIN_FALLS);
/* 141 */   public static final GDCharUID UID_RIFT_02_BROKEN_HILLS = new GDCharUID(RIFTGATE_02_BROKEN_HILLS);
/* 142 */   public static final GDCharUID UID_RIFT_02_SMUGGLERS_PASS = new GDCharUID(RIFTGATE_02_SMUGGLERS_PASS);
/* 143 */   public static final GDCharUID UID_RIFT_03_DEADMANS_GULCH = new GDCharUID(RIFTGATE_03_DEADMANS_GULCH);
/* 144 */   public static final GDCharUID UID_RIFT_03_PROSPECTORS_TRAIL = new GDCharUID(RIFTGATE_03_PROSPECTORS_TRAIL);
/* 145 */   public static final GDCharUID UID_RIFT_03_PINE_BARRENS = new GDCharUID(RIFTGATE_03_PINE_BARRENS);
/* 146 */   public static final GDCharUID UID_RIFT_03_HOMESTEAD = new GDCharUID(RIFTGATE_03_HOMESTEAD);
/* 147 */   public static final GDCharUID UID_RIFT_03_ROTTING_CROPLANDS = new GDCharUID(RIFTGATE_03_ROTTING_CROPLANDS);
/* 148 */   public static final GDCharUID UID_RIFT_03_SORROWS_BASTION = new GDCharUID(RIFTGATE_03_SORROWS_BASTION);
/* 149 */   public static final GDCharUID UID_RIFT_03_BLOOD_GROVE = new GDCharUID(RIFTGATE_03_BLOOD_GROVE);
/* 150 */   public static final GDCharUID UID_RIFT_03_DARKVALE_GATE = new GDCharUID(RIFTGATE_03_DARKVALE_GATE);
/* 151 */   public static final GDCharUID UID_RIFT_04_ASTERKARN_MOUNTAINS = new GDCharUID(RIFTGATE_04_ASTERKARN_MOUNTAINS);
/* 152 */   public static final GDCharUID UID_RIFT_04_ASTERKARN_ROAD = new GDCharUID(RIFTGATE_04_ASTERKARN_ROAD);
/* 153 */   public static final GDCharUID UID_RIFT_04_ASTERKARN_VALLEY = new GDCharUID(RIFTGATE_04_ASTERKARN_VALLEY);
/* 154 */   public static final GDCharUID UID_RIFT_04_FORT_IKON = new GDCharUID(RIFTGATE_04_FORT_IKON);
/* 155 */   public static final GDCharUID UID_RIFT_04_GATES_OF_NECROPOLIS = new GDCharUID(RIFTGATE_04_GATES_OF_NECROPOLIS);
/* 156 */   public static final GDCharUID UID_RIFT_04_NECROPOLIS_INTERIOR = new GDCharUID(RIFTGATE_04_NECROPOLIS_INTERIOR);
/* 157 */   public static final GDCharUID UID_RIFT_05_GLOOMWALD = new GDCharUID(RIFTGATE_05_GLOOMWALD);
/* 158 */   public static final GDCharUID UID_RIFT_05_GLOOMWALD_CROSSING = new GDCharUID(RIFTGATE_05_GLOOMWALD_CROSSING);
/* 159 */   public static final GDCharUID UID_RIFT_05_COVENS_REFUGE = new GDCharUID(RIFTGATE_05_COVENS_REFUGE);
/* 160 */   public static final GDCharUID UID_RIFT_05_UGDENBOG = new GDCharUID(RIFTGATE_05_UGDENBOG);
/* 161 */   public static final GDCharUID UID_RIFT_05_BARROWHOLM = new GDCharUID(RIFTGATE_05_BARROWHOLM);
/* 162 */   public static final GDCharUID UID_RIFT_05_LONE_WATCH = new GDCharUID(RIFTGATE_05_LONE_WATCH);
/* 163 */   public static final GDCharUID UID_RIFT_05_MALMOUTH_OUTSKIRTS = new GDCharUID(RIFTGATE_05_MALMOUTH_OUTSKIRTS);
/* 164 */   public static final GDCharUID UID_RIFT_05_MALMOUTH_SEWERS = new GDCharUID(RIFTGATE_05_MALMOUTH_SEWERS);
/* 165 */   public static final GDCharUID UID_RIFT_05_MALMOUTH_STEELCAP = new GDCharUID(RIFTGATE_05_MALMOUTH_STEELCAP);
/* 166 */   public static final GDCharUID UID_RIFT_05_MALMOUTH_CROWN_HILL = new GDCharUID(RIFTGATE_05_MALMOUTH_CROWN_HILL);
/* 167 */   public static final GDCharUID UID_RIFT_06_CONCLAVE_THREE = new GDCharUID(RIFTGATE_06_CONCLAVE_THREE);
/* 168 */   public static final GDCharUID UID_RIFT_06_KORVAN_PLATEAU = new GDCharUID(RIFTGATE_06_KORVAN_PLATEAU);
/* 169 */   public static final GDCharUID UID_RIFT_06_TEMPLE_OSYR = new GDCharUID(RIFTGATE_06_TEMPLE_OSYR);
/* 170 */   public static final GDCharUID UID_RIFT_06_KORVAN_SANDS = new GDCharUID(RIFTGATE_06_KORVAN_SANDS);
/* 171 */   public static final GDCharUID UID_RIFT_06_CAIRAN_DOCKS = new GDCharUID(RIFTGATE_06_CAIRAN_DOCKS);
/* 172 */   public static final GDCharUID UID_RIFT_06_SUNBANE_OASIS = new GDCharUID(RIFTGATE_06_SUNBANE_OASIS);
/* 173 */   public static final GDCharUID UID_RIFT_06_VANGUARD_THREE = new GDCharUID(RIFTGATE_06_VANGUARD_THREE);
/* 174 */   public static final GDCharUID UID_RIFT_06_RUINS_ABYD = new GDCharUID(RIFTGATE_06_RUINS_ABYD);
/* 175 */   public static final GDCharUID UID_RIFT_06_INFERNAL_WASTES = new GDCharUID(RIFTGATE_06_INFERNAL_WASTES);
/* 176 */   public static final GDCharUID UID_RIFT_06_KORVAN_CITY = new GDCharUID(RIFTGATE_06_KORVAN_CITY);
/* 177 */   public static final GDCharUID UID_RIFT_06_ELDRITCH_SUN = new GDCharUID(RIFTGATE_06_ELDRITCH_SUN);
/* 178 */   public static final GDCharUID UID_RIFT_06_ELDRITCH_GATE = new GDCharUID(RIFTGATE_06_ELDRITCH_GATE);
/* 179 */   public static final GDCharUID UID_RIFT_06_LOST_OASIS = new GDCharUID(RIFTGATE_06_LOST_OASIS);
/*     */ 
/*     */   
/* 182 */   private static final byte[] RIFTGATE_ROT_01_ROGUE_ENCAMPMENT = new byte[] { -97, 20, -26, -22, -5, 68, 111, 27, -25, 66, 53, -126, 83, 113, -117, -114 };
/*     */   
/* 184 */   private static final byte[] RIFTGATE_ROT_01_COLD_PLAINS = new byte[] { 4, 81, 1, 66, -94, 73, -21, -30, 121, 16, -58, -111, -6, 54, 114, -78 };
/*     */   
/* 186 */   private static final byte[] RIFTGATE_ROT_01_STONY_FIELD = new byte[] { 19, 21, -34, -49, -4, 68, -29, -117, 25, -109, -107, -80, 0, 47, 78, 75 };
/*     */   
/* 188 */   private static final byte[] RIFTGATE_ROT_01_DARK_WOOD = new byte[] { 75, -55, -96, -44, Byte.MIN_VALUE, 70, -52, 75, -6, -125, 72, -110, -39, 2, 75, 20 };
/*     */   
/* 190 */   private static final byte[] RIFTGATE_ROT_01_BLACK_MARSH = new byte[] { 99, 56, -17, -11, -101, 69, 73, 90, 52, -53, -112, -81, 1, -83, 76, -117 };
/*     */   
/* 192 */   private static final byte[] RIFTGATE_ROT_01_CLOISTER_OUTER = new byte[] { 94, -26, -92, -122, 5, 71, 39, 30, -73, 92, -37, -90, 105, 68, 101, 57 };
/*     */   
/* 194 */   private static final byte[] RIFTGATE_ROT_01_JAIL = new byte[] { -44, -85, 96, -62, -13, 72, -69, 108, 90, 2, 93, -71, 38, 45, -33, -1 };
/*     */   
/* 196 */   private static final byte[] RIFTGATE_ROT_01_CLOISTER_INNER = new byte[] { -83, -73, 66, -124, -106, 79, 60, 45, -88, -17, -115, -93, -89, 74, -8, 98 };
/*     */   
/* 198 */   private static final byte[] RIFTGATE_ROT_01_CATACOMBS = new byte[] { 23, 122, -19, -95, -111, 64, 12, 94, 116, -71, -23, -109, 63, -78, -115, 27 };
/*     */   
/* 200 */   private static final byte[] RIFTGATE_ROT_02_LUT_GHOLEIN = new byte[] { -59, -43, -64, 118, 104, 66, 101, -12, -69, 64, 18, -109, -46, -126, 42, 79 };
/*     */   
/* 202 */   private static final byte[] RIFTGATE_ROT_02_SEWERS = new byte[] { -89, 38, -63, 62, -52, 66, -24, -47, -121, 78, -50, -95, -97, -103, 79, -118 };
/*     */   
/* 204 */   private static final byte[] RIFTGATE_ROT_02_DRY_HILLS = new byte[] { 23, 92, -14, -106, 48, 71, 101, 99, 59, -60, 32, -75, -51, 76, -8, 67 };
/*     */   
/* 206 */   private static final byte[] RIFTGATE_ROT_02_HALLS_DEAD = new byte[] { 72, -30, -92, -20, 36, 68, 71, -12, 30, 60, -42, -93, 20, -64, 27, -2 };
/*     */   
/* 208 */   private static final byte[] RIFTGATE_ROT_02_FAR_OASIS = new byte[] { -23, 31, 65, 115, 110, 67, -76, -17, -91, 81, -59, -95, -125, -28, 41, 4 };
/*     */   
/* 210 */   private static final byte[] RIFTGATE_ROT_02_LOST_CITY = new byte[] { -66, -89, -16, -91, 43, 76, -116, 96, -4, 91, -16, -105, -124, 33, -79, 71 };
/*     */   
/* 212 */   private static final byte[] RIFTGATE_ROT_02_PALACE_CELLAR = new byte[] { 54, -50, 96, -63, 22, 67, -94, -27, 126, -126, -88, -102, -61, 110, -47, 24 };
/*     */   
/* 214 */   private static final byte[] RIFTGATE_ROT_02_ARCANE_SANCTUARY = new byte[] { -46, -5, -15, -87, -74, 64, -87, -117, -65, 78, -65, -123, -56, -103, 114, 9 };
/*     */   
/* 216 */   private static final byte[] RIFTGATE_ROT_02_CANYON_MAGI = new byte[] { 116, -56, -57, -54, 24, 67, 116, -124, 78, 90, -85, -76, -88, -10, 4, 101 };
/*     */   
/* 218 */   private static final byte[] RIFTGATE_ROT_03_KURAST_DOCKS = new byte[] { 17, 65, 19, -39, 27, 64, -123, 56, 46, -50, 26, -126, 107, -23, -31, 16 };
/*     */   
/* 220 */   private static final byte[] RIFTGATE_ROT_03_SPIDER_FOREST = new byte[] { -36, -96, -115, 70, -42, 72, -89, 97, -57, 75, 23, -118, 75, -22, 29, 51 };
/*     */   
/* 222 */   private static final byte[] RIFTGATE_ROT_03_GREAT_MARSH = new byte[] { 7, 35, -88, -80, 62, 79, -127, -62, -80, -103, -68, -84, -76, -84, -71, -96 };
/*     */   
/* 224 */   private static final byte[] RIFTGATE_ROT_03_FLAYER_JUNGLE = new byte[] { 37, -47, Byte.MIN_VALUE, -28, -55, 64, 95, 34, 106, 85, 124, -88, -74, 34, -64, 17 };
/*     */   
/* 226 */   private static final byte[] RIFTGATE_ROT_03_KURAST_LOWER = new byte[] { 118, -32, 108, -13, -4, 67, -43, -108, 101, -22, 113, -117, 26, -72, 4, -32 };
/*     */   
/* 228 */   private static final byte[] RIFTGATE_ROT_03_KURAST_BAZAAR = new byte[] { 41, 19, 91, -59, -76, 70, -96, -113, 70, -82, 68, -88, -53, -82, 8, -50 };
/*     */   
/* 230 */   private static final byte[] RIFTGATE_ROT_03_KURAST_UPPER = new byte[] { 72, 103, 22, 90, -90, 64, 85, 2, -126, 45, -70, -101, 47, -122, -72, 114 };
/*     */   
/* 232 */   private static final byte[] RIFTGATE_ROT_03_TRAVINCAL = new byte[] { -1, 124, 78, -102, -121, 64, 0, 83, 119, -87, -61, -110, -32, 7, 16, -97 };
/*     */   
/* 234 */   private static final byte[] RIFTGATE_ROT_03_DURANCE_HATE = new byte[] { -107, -67, -110, -27, 82, 73, -44, 113, -19, -105, -49, -70, -14, 58, -92, 6 };
/*     */   
/* 236 */   private static final byte[] RIFTGATE_ROT_04_PANDEMONIUM_FORTRESS = new byte[] { 80, -116, -33, 67, 5, 72, -9, 54, -118, -89, 25, -114, -52, -22, -76, -97 };
/*     */   
/* 238 */   private static final byte[] RIFTGATE_ROT_04_PLAINS_DESPAIR = new byte[] { 8, -99, 86, -79, 68, 77, -14, 64, 85, -5, 103, -67, 11, -13, -105, 120 };
/*     */   
/* 240 */   private static final byte[] RIFTGATE_ROT_04_CITY_DAMNED = new byte[] { 34, 55, 28, 124, -99, 75, -72, 47, Byte.MIN_VALUE, -43, -89, -81, 7, 111, 54, 93 };
/*     */   
/* 242 */   private static final byte[] RIFTGATE_ROT_04_RIVER_FLAME = new byte[] { -120, -82, -8, -47, 118, 67, 14, -39, 30, -6, -77, -108, 86, -58, 56, -61 };
/*     */   
/* 244 */   private static final byte[] RIFTGATE_ROT_05_HARROGATH = new byte[] { -23, 100, -25, 78, -126, 64, 31, 50, 125, -30, 57, -92, -66, -70, -11, -79 };
/*     */   
/* 246 */   private static final byte[] RIFTGATE_ROT_05_FRIGID_HIGHLANDS = new byte[] { 110, 30, 53, 113, -67, 78, 59, 48, -58, 103, 101, -70, 78, 41, 13, -106 };
/*     */   
/* 248 */   private static final byte[] RIFTGATE_ROT_05_ARREAT_PLATEAU = new byte[] { -112, 60, 101, -110, -111, 70, 45, -79, -89, -75, -29, -106, -95, 102, 119, -82 };
/*     */   
/* 250 */   private static final byte[] RIFTGATE_ROT_05_CRYSTALLINE_PASSAGE = new byte[] { 88, 84, -123, 27, -120, 78, -103, 78, -81, -40, -8, -117, 69, -65, -62, 109 };
/*     */   
/* 252 */   private static final byte[] RIFTGATE_ROT_05_HALLS_PAIN = new byte[] { -7, 22, -48, 106, 12, 78, -54, 120, 0, 3, -42, -66, 62, -120, -81, 117 };
/*     */   
/* 254 */   private static final byte[] RIFTGATE_ROT_05_GLACIAL_TRAIL = new byte[] { 90, 93, 95, -106, 48, 76, 52, -101, -1, 105, 83, -105, -105, -33, -115, 68 };
/*     */   
/* 256 */   private static final byte[] RIFTGATE_ROT_05_FROZEN_TUNDRA = new byte[] { 50, -126, 66, 106, -39, 66, 53, 112, -40, 1, 89, -73, -50, 73, -2, 10 };
/*     */   
/* 258 */   private static final byte[] RIFTGATE_ROT_05_ANCIENTS_WAY = new byte[] { -25, 117, -58, -112, 1, 79, 22, 31, -126, -84, 81, -108, 69, -93, 47, -82 };
/*     */   
/* 260 */   private static final byte[] RIFTGATE_ROT_05_WORLDSTONE_KEEP = new byte[] { 47, 125, -46, 81, -58, 70, -110, -13, -79, 102, 75, -106, -123, -13, -68, Byte.MAX_VALUE };
/*     */   
/* 262 */   private static final byte[] RIFTGATE_ROT_06_SHATTERED_CONCLAVE = new byte[] { 28, 29, 28, 105, 11, 77, 25, 66, 10, -94, 10, -69, 126, 50, 75, 48 };
/*     */   
/* 264 */   private static final byte[] RIFTGATE_ROT_06_TRISTRAM = new byte[] { -3, -5, 72, -23, -110, 68, 103, 99, -90, -37, 83, -88, 93, -23, 78, -63 };
/*     */   
/* 266 */   private static final byte[] RIFTGATE_ROT_06_LEVEL_02 = new byte[] { -104, -97, -123, 103, 91, 77, 105, -119, 126, 97, -70, -81, -36, -68, 38, -63 };
/*     */   
/* 268 */   private static final byte[] RIFTGATE_ROT_06_LEVEL_04 = new byte[] { 68, -70, -37, 69, -67, 70, -26, -87, -41, Byte.MIN_VALUE, -84, -72, -48, -28, -72, 84 };
/*     */   
/* 270 */   private static final byte[] RIFTGATE_ROT_06_LEVEL_06 = new byte[] { 105, 107, -95, -24, 16, 78, -33, -67, 44, 1, -112, -77, -14, -99, -1, -120 };
/*     */   
/* 272 */   private static final byte[] RIFTGATE_ROT_06_LEVEL_08 = new byte[] { -3, 101, 81, 77, -24, 66, -29, -1, -76, 70, -80, -88, -11, -23, 63, -3 };
/*     */   
/* 274 */   private static final byte[] RIFTGATE_ROT_06_LEVEL_10 = new byte[] { 78, -69, 46, 123, -75, 71, -126, 45, 60, 26, -96, -89, -33, -80, 97, -96 };
/*     */   
/* 276 */   private static final byte[] RIFTGATE_ROT_06_LEVEL_12 = new byte[] { -52, 94, -27, -72, 91, 67, 38, 10, -39, -77, -74, -87, -89, 19, -18, -111 };
/*     */   
/* 278 */   private static final byte[] RIFTGATE_ROT_06_LEVEL_14 = new byte[] { 121, -71, 63, -120, -108, 64, 8, -16, 69, -126, -88, -127, -36, -80, -1, -28 };
/*     */   
/* 280 */   private static final byte[] RIFTGATE_ROT_06_LEVEL_16 = new byte[] { 15, 47, 16, 72, 37, 79, 69, 42, -65, 75, 33, -110, 93, 69, -71, 50 };
/*     */   
/* 282 */   private static final byte[] RIFTGATE_ROT_06_HORADRIM_TOMB = new byte[] { 118, -90, -121, 80, -112, 79, -109, -24, -119, 12, 119, -77, 103, 109, -93, 48 };
/*     */ 
/*     */   
/* 285 */   public static final GDCharUID UID_RIFT_ROT_01_ROGUE_ENCAMPMENT = new GDCharUID(RIFTGATE_ROT_01_ROGUE_ENCAMPMENT);
/* 286 */   public static final GDCharUID UID_RIFT_ROT_01_COLD_PLAINS = new GDCharUID(RIFTGATE_ROT_01_COLD_PLAINS);
/* 287 */   public static final GDCharUID UID_RIFT_ROT_01_STONY_FIELD = new GDCharUID(RIFTGATE_ROT_01_STONY_FIELD);
/* 288 */   public static final GDCharUID UID_RIFT_ROT_01_DARK_WOOD = new GDCharUID(RIFTGATE_ROT_01_DARK_WOOD);
/* 289 */   public static final GDCharUID UID_RIFT_ROT_01_BLACK_MARSH = new GDCharUID(RIFTGATE_ROT_01_BLACK_MARSH);
/* 290 */   public static final GDCharUID UID_RIFT_ROT_01_CLOISTER_OUTER = new GDCharUID(RIFTGATE_ROT_01_CLOISTER_OUTER);
/* 291 */   public static final GDCharUID UID_RIFT_ROT_01_JAIL = new GDCharUID(RIFTGATE_ROT_01_JAIL);
/* 292 */   public static final GDCharUID UID_RIFT_ROT_01_CLOISTER_INNER = new GDCharUID(RIFTGATE_ROT_01_CLOISTER_INNER);
/* 293 */   public static final GDCharUID UID_RIFT_ROT_01_CATACOMBS = new GDCharUID(RIFTGATE_ROT_01_CATACOMBS);
/* 294 */   public static final GDCharUID UID_RIFT_ROT_02_LUT_GHOLEIN = new GDCharUID(RIFTGATE_ROT_02_LUT_GHOLEIN);
/* 295 */   public static final GDCharUID UID_RIFT_ROT_02_SEWERS = new GDCharUID(RIFTGATE_ROT_02_SEWERS);
/* 296 */   public static final GDCharUID UID_RIFT_ROT_02_DRY_HILLS = new GDCharUID(RIFTGATE_ROT_02_DRY_HILLS);
/* 297 */   public static final GDCharUID UID_RIFT_ROT_02_HALLS_DEAD = new GDCharUID(RIFTGATE_ROT_02_HALLS_DEAD);
/* 298 */   public static final GDCharUID UID_RIFT_ROT_02_FAR_OASIS = new GDCharUID(RIFTGATE_ROT_02_FAR_OASIS);
/* 299 */   public static final GDCharUID UID_RIFT_ROT_02_LOST_CITY = new GDCharUID(RIFTGATE_ROT_02_LOST_CITY);
/* 300 */   public static final GDCharUID UID_RIFT_ROT_02_PALACE_CELLAR = new GDCharUID(RIFTGATE_ROT_02_PALACE_CELLAR);
/* 301 */   public static final GDCharUID UID_RIFT_ROT_02_ARCANE_SANCTUARY = new GDCharUID(RIFTGATE_ROT_02_ARCANE_SANCTUARY);
/* 302 */   public static final GDCharUID UID_RIFT_ROT_02_CANYON_MAGI = new GDCharUID(RIFTGATE_ROT_02_CANYON_MAGI);
/* 303 */   public static final GDCharUID UID_RIFT_ROT_03_KURAST_DOCKS = new GDCharUID(RIFTGATE_ROT_03_KURAST_DOCKS);
/* 304 */   public static final GDCharUID UID_RIFT_ROT_03_SPIDER_FOREST = new GDCharUID(RIFTGATE_ROT_03_SPIDER_FOREST);
/* 305 */   public static final GDCharUID UID_RIFT_ROT_03_GREAT_MARSH = new GDCharUID(RIFTGATE_ROT_03_GREAT_MARSH);
/* 306 */   public static final GDCharUID UID_RIFT_ROT_03_FLAYER_JUNGLE = new GDCharUID(RIFTGATE_ROT_03_FLAYER_JUNGLE);
/* 307 */   public static final GDCharUID UID_RIFT_ROT_03_KURAST_LOWER = new GDCharUID(RIFTGATE_ROT_03_KURAST_LOWER);
/* 308 */   public static final GDCharUID UID_RIFT_ROT_03_KURAST_BAZAAR = new GDCharUID(RIFTGATE_ROT_03_KURAST_BAZAAR);
/* 309 */   public static final GDCharUID UID_RIFT_ROT_03_KURAST_UPPER = new GDCharUID(RIFTGATE_ROT_03_KURAST_UPPER);
/* 310 */   public static final GDCharUID UID_RIFT_ROT_03_TRAVINCAL = new GDCharUID(RIFTGATE_ROT_03_TRAVINCAL);
/* 311 */   public static final GDCharUID UID_RIFT_ROT_03_DURANCE_HATE = new GDCharUID(RIFTGATE_ROT_03_DURANCE_HATE);
/* 312 */   public static final GDCharUID UID_RIFT_ROT_04_PANDEMONIUM_FORTRESS = new GDCharUID(RIFTGATE_ROT_04_PANDEMONIUM_FORTRESS);
/* 313 */   public static final GDCharUID UID_RIFT_ROT_04_PLAINS_DESPAIR = new GDCharUID(RIFTGATE_ROT_04_PLAINS_DESPAIR);
/* 314 */   public static final GDCharUID UID_RIFT_ROT_04_CITY_DAMNED = new GDCharUID(RIFTGATE_ROT_04_CITY_DAMNED);
/* 315 */   public static final GDCharUID UID_RIFT_ROT_04_RIVER_FLAME = new GDCharUID(RIFTGATE_ROT_04_RIVER_FLAME);
/* 316 */   public static final GDCharUID UID_RIFT_ROT_05_HARROGATH = new GDCharUID(RIFTGATE_ROT_05_HARROGATH);
/* 317 */   public static final GDCharUID UID_RIFT_ROT_05_FRIGID_HIGHLANDS = new GDCharUID(RIFTGATE_ROT_05_FRIGID_HIGHLANDS);
/* 318 */   public static final GDCharUID UID_RIFT_ROT_05_ARREAT_PLATEAU = new GDCharUID(RIFTGATE_ROT_05_ARREAT_PLATEAU);
/* 319 */   public static final GDCharUID UID_RIFT_ROT_05_CRYSTALLINE_PASSAGE = new GDCharUID(RIFTGATE_ROT_05_CRYSTALLINE_PASSAGE);
/* 320 */   public static final GDCharUID UID_RIFT_ROT_05_HALLS_PAIN = new GDCharUID(RIFTGATE_ROT_05_HALLS_PAIN);
/* 321 */   public static final GDCharUID UID_RIFT_ROT_05_GLACIAL_TRAIL = new GDCharUID(RIFTGATE_ROT_05_GLACIAL_TRAIL);
/* 322 */   public static final GDCharUID UID_RIFT_ROT_05_FROZEN_TUNDRA = new GDCharUID(RIFTGATE_ROT_05_FROZEN_TUNDRA);
/* 323 */   public static final GDCharUID UID_RIFT_ROT_05_ANCIENTS_WAY = new GDCharUID(RIFTGATE_ROT_05_ANCIENTS_WAY);
/* 324 */   public static final GDCharUID UID_RIFT_ROT_05_WORLDSTONE_KEEP = new GDCharUID(RIFTGATE_ROT_05_WORLDSTONE_KEEP);
/* 325 */   public static final GDCharUID UID_RIFT_ROT_06_SHATTERED_CONCLAVE = new GDCharUID(RIFTGATE_ROT_06_SHATTERED_CONCLAVE);
/* 326 */   public static final GDCharUID UID_RIFT_ROT_06_TRISTRAM = new GDCharUID(RIFTGATE_ROT_06_TRISTRAM);
/* 327 */   public static final GDCharUID UID_RIFT_ROT_06_LEVEL_02 = new GDCharUID(RIFTGATE_ROT_06_LEVEL_02);
/* 328 */   public static final GDCharUID UID_RIFT_ROT_06_LEVEL_04 = new GDCharUID(RIFTGATE_ROT_06_LEVEL_04);
/* 329 */   public static final GDCharUID UID_RIFT_ROT_06_LEVEL_06 = new GDCharUID(RIFTGATE_ROT_06_LEVEL_06);
/* 330 */   public static final GDCharUID UID_RIFT_ROT_06_LEVEL_08 = new GDCharUID(RIFTGATE_ROT_06_LEVEL_08);
/* 331 */   public static final GDCharUID UID_RIFT_ROT_06_LEVEL_10 = new GDCharUID(RIFTGATE_ROT_06_LEVEL_10);
/* 332 */   public static final GDCharUID UID_RIFT_ROT_06_LEVEL_12 = new GDCharUID(RIFTGATE_ROT_06_LEVEL_12);
/* 333 */   public static final GDCharUID UID_RIFT_ROT_06_LEVEL_14 = new GDCharUID(RIFTGATE_ROT_06_LEVEL_14);
/* 334 */   public static final GDCharUID UID_RIFT_ROT_06_LEVEL_16 = new GDCharUID(RIFTGATE_ROT_06_LEVEL_16);
/* 335 */   public static final GDCharUID UID_RIFT_ROT_06_HORADRIM_TOMB = new GDCharUID(RIFTGATE_ROT_06_HORADRIM_TOMB);
/*     */   
/*     */   private static final int VERSION = 1;
/*     */   
/*     */   private static final int BLOCK = 6;
/*     */   
/*     */   private int version;
/*     */   
/*     */   private ArrayList<List<GDCharUID>> uidLists;
/*     */   
/*     */   private GDChar gdc;
/*     */   private boolean changed;
/*     */   
/*     */   public GDCharTeleportList(GDChar gdc) {
/* 349 */     int size = 3;
/*     */     
/* 351 */     this.uidLists = new ArrayList<>(size);
/* 352 */     for (int i = 0; i < size; i++) {
/* 353 */       this.uidLists.add(new LinkedList<>());
/*     */     }
/*     */     
/* 356 */     this.gdc = gdc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getRiftList(int difficulty) {
/* 364 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 366 */     if (difficulty < 0 || difficulty > 2) return list;
/*     */     
/* 368 */     return this.uidLists.get(difficulty);
/*     */   }
/*     */   
/*     */   public boolean hasChanged() {
/* 372 */     return this.changed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRiftList(int difficulty, List<GDCharUID> addList, List<GDCharUID> removeList) {
/* 380 */     if (difficulty < 0 || difficulty > 2) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 385 */     int index = difficulty;
/*     */     
/* 387 */     List<GDCharUID> riftgates = this.uidLists.get(index);
/* 388 */     List<GDCharUID> newList = new LinkedList<>();
/* 389 */     for (GDCharUID uid : riftgates) {
/* 390 */       if (!removeList.contains(uid)) {
/* 391 */         newList.add(uid); continue;
/*     */       } 
/* 393 */       this.changed = true;
/*     */     } 
/*     */ 
/*     */     
/* 397 */     for (GDCharUID uid : addList) {
/* 398 */       if (!newList.contains(uid)) {
/* 399 */         newList.add(uid);
/*     */         
/* 401 */         this.changed = true;
/*     */       } 
/*     */     } 
/*     */     
/* 405 */     this.uidLists.set(index, newList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read() throws IOException {
/* 413 */     int val = 0;
/*     */     
/* 415 */     GDReader.Block block = new GDReader.Block();
/*     */     
/* 417 */     val = GDReader.readBlockStart(block);
/* 418 */     if (val != 6) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/* 420 */     this.version = GDReader.readEncInt(true);
/* 421 */     if (this.version != 1) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/* 423 */     for (int i = 0; i < this.uidLists.size(); i++) {
/* 424 */       GDChar.readUIDList(this.uidLists.get(i));
/*     */     }
/*     */ 
/*     */     
/* 428 */     GDReader.readBlockEnd(block);
/*     */     
/* 430 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/* 434 */     GDReader.Block block = new GDReader.Block();
/* 435 */     GDWriter.writeBlockStart(block, 6);
/*     */     
/* 437 */     GDWriter.writeInt(this.version);
/*     */     
/* 439 */     for (int i = 0; i < this.uidLists.size(); i++) {
/* 440 */       GDChar.writeUIDList(this.uidLists.get(i));
/*     */     }
/*     */ 
/*     */     
/* 444 */     GDWriter.writeBlockEnd(block);
/*     */     
/* 446 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/* 450 */     int size = 0;
/*     */     
/* 452 */     size += 4;
/* 453 */     size += 4;
/* 454 */     size += 4;
/*     */     
/* 456 */     for (int i = 0; i < this.uidLists.size(); i++) {
/* 457 */       size += 4;
/* 458 */       size += ((List)this.uidLists.get(i)).size() * GDCharUID.getByteSize();
/*     */     } 
/*     */     
/* 461 */     size += 4;
/*     */     
/* 463 */     return size;
/*     */   }
/*     */   
/*     */   public void print() {
/* 467 */     for (int i = 0; i < this.uidLists.size(); i++) {
/* 468 */       System.out.println("Index : " + Integer.toString(i));
/*     */       
/* 470 */       for (GDCharUID uid : this.uidLists.get(i)) {
/* 471 */         uid.println();
/*     */       }
/*     */       
/* 474 */       System.out.println();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\character\GDCharTeleportList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */