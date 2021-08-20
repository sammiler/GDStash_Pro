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
/*     */ public class GDCharShrineList
/*     */ {
/*  20 */   private static final byte[] SHRINE_01_BURIAL_HILL = new byte[] { -102, -127, -12, 45, -87, 76, -31, 35, 71, -82, -23, -117, 40, -45, 80, 69 };
/*     */   
/*  22 */   private static final byte[] SHRINE_01_DEVILS_AQUIFIER = new byte[] { 97, 8, 108, -79, -49, 71, -42, -82, 79, -88, -115, -120, 45, -58, 82, 32 };
/*     */   
/*  24 */   private static final byte[] SHRINE_01_WIGHTMIRE_CAVERN = new byte[] { 58, -113, -123, 73, -84, 74, -91, -117, 102, -102, 124, -76, 17, 109, 33, 80 };
/*     */   
/*  26 */   private static final byte[] SHRINE_01_FLOODED_PASSAGE = new byte[] { -83, 9, 86, 92, 39, 66, 43, 68, -126, 102, 64, -85, 54, 56, 123, -101 };
/*     */   
/*  28 */   private static final byte[] SHRINE_01_BURIAL_CAVE = new byte[] { -54, 27, 4, 32, 54, 73, 46, 103, -105, 2, 101, -86, -100, -11, -5, 126 };
/*     */   
/*  30 */   private static final byte[] SHRINE_01_HALLOWED_HILL = new byte[] { -21, -43, -83, -116, -82, 71, -111, -85, 88, -40, 42, -71, -28, 44, -71, -93 };
/*     */   
/*  32 */   private static final byte[] SHRINE_01_BURRWITCH_ESTATES = new byte[] { -119, -19, 19, -58, 17, 73, 68, 119, -121, -27, -61, -112, -1, 7, 18, 106 };
/*     */   
/*  34 */   private static final byte[] SHRINE_01_UNDERGROUND_TRANSIT = new byte[] { 111, -65, 4, -14, 42, 66, 4, -81, -39, 122, -54, -122, -120, 15, -55, 10 };
/*     */   
/*  36 */   private static final byte[] SHRINE_01_CRAIGS_CRAGS = new byte[] { -69, -34, -50, 89, 66, 65, 96, 114, 74, -54, 73, -87, 75, 97, 5, 16 };
/*     */   
/*  38 */   private static final byte[] SHRINE_01_GUARDIAN_DREEG = new byte[] { 89, 92, -60, 101, -82, 73, -93, -9, -122, 111, 21, -101, -92, 60, 102, Byte.MIN_VALUE };
/*     */   
/*  40 */   private static final byte[] SHRINE_01_TEMPLE_THREE = new byte[] { 64, -6, -1, 91, 10, 71, -122, -33, 81, -44, 90, -117, -50, -113, -74, 1 };
/*     */   
/*  42 */   private static final byte[] SHRINE_02_SPINED_COVE = new byte[] { -53, -29, 96, 55, 72, 65, 4, 31, 18, -95, 35, -108, -49, 67, -58, 51 };
/*     */   
/*  44 */   private static final byte[] SHRINE_02_ROCKY_COAST = new byte[] { -99, 8, 21, 3, 33, 67, -78, 18, -57, 18, -12, -77, -40, 66, -119, 75 };
/*     */   
/*  46 */   private static final byte[] SHRINE_02_CRONLEYS_HIDEOUT = new byte[] { -51, -3, -112, -122, 43, 70, 54, -47, 115, 89, 13, -67, -2, -40, 107, 61 };
/*     */   
/*  48 */   private static final byte[] SHRINE_02_OLD_ARKOVIA = new byte[] { -115, 21, 10, 80, -66, 77, -86, -19, 86, -71, 57, -123, 71, 86, 77, -81 };
/*     */   
/*  50 */   private static final byte[] SHRINE_02_ARKOVIAN_UNDERCITY = new byte[] { -82, 36, 110, 17, 3, 70, 67, -61, 37, 27, -73, -122, 42, 0, 78, 51 };
/*     */   
/*  52 */   private static final byte[] SHRINE_02_BROKEN_HILLS = new byte[] { -99, -83, 120, 101, 6, 69, -39, -101, 125, -26, -19, -79, -61, 46, 95, Byte.MIN_VALUE };
/*     */   
/*  54 */   private static final byte[] SHRINE_02_STEPS_OF_TORMENT = new byte[] { 50, -28, -6, 11, -81, 70, -64, 7, 77, -125, 38, -95, -70, 41, -8, -28 };
/*     */   
/*  56 */   private static final byte[] SHRINE_03_MOUNTAIN_DEEPS = new byte[] { -21, 33, -60, -20, 27, 79, 55, -77, -46, 72, 50, -80, 64, -54, 41, 67 };
/*     */   
/*  58 */   private static final byte[] SHRINE_03_FORGOTTEN_DEPTHS = new byte[] { 62, 109, -82, -116, -18, 71, 107, -87, 34, 108, -79, -71, 72, 24, 106, 126 };
/*     */   
/*  60 */   private static final byte[] SHRINE_03_TYRANTS_HOLD = new byte[] { -61, -126, -13, 108, 41, 65, 102, -96, -125, -63, -80, -76, 29, -27, -94, 42 };
/*     */   
/*  62 */   private static final byte[] SHRINE_03_INFESTED_FARMS = new byte[] { -96, 78, -78, -47, -110, 67, 96, 98, -54, -36, -127, -73, -32, -110, 68, -40 };
/*     */   
/*  64 */   private static final byte[] SHRINE_03_DEN_OF_THE_LOST = new byte[] { -82, -49, 65, 101, 59, 68, 119, 48, -45, 17, -13, -86, 12, 74, -57, -97 };
/*     */   
/*  66 */   private static final byte[] SHRINE_03_CONFLAGRATION = new byte[] { -63, 103, -126, 74, -109, 70, -84, 4, 73, -12, -94, -81, 66, -78, 82, 102 };
/*     */   
/*  68 */   private static final byte[] SHRINE_03_PORT_VALBURY = new byte[] { 125, -110, -26, -73, -109, 78, -11, -103, 68, -125, 74, -102, 20, 59, 57, -118 };
/*     */   
/*  70 */   private static final byte[] SHRINE_03_BLOOD_GROVE = new byte[] { -27, 73, -97, -11, -114, 76, 58, 12, 22, 30, -20, -93, -10, -103, 97, -95 };
/*     */   
/*  72 */   private static final byte[] SHRINE_03_STONEREND_QUARRY = new byte[] { 81, 16, -89, -54, 76, 64, -53, -114, 21, -46, 54, -76, -70, 114, -101, 45 };
/*     */   
/*  74 */   private static final byte[] SHRINE_03_VILLAGE_OF_DARKVALE = new byte[] { 87, 102, 85, -2, -104, 75, -109, -56, 115, -13, 27, -71, 84, 53, 55, -86 };
/*     */   
/*  76 */   private static final byte[] SHRINE_04_ASTERKARN_VALLEY = new byte[] { 46, 44, 37, -84, -51, 72, 36, -65, -33, -50, -101, -112, 8, -30, -98, -87 };
/*     */   
/*  78 */   private static final byte[] SHRINE_04_MOGDROGENS_SHRINE = new byte[] { 13, -62, -115, 29, 40, 71, 97, 41, -22, -20, 41, -117, 4, -72, 102, 102 };
/*     */   
/*  80 */   private static final byte[] SHRINE_04_TOMB_OF_THE_DAMNED = new byte[] { -125, -87, -80, -33, -51, 74, -56, 74, -30, -23, 14, -91, -85, -92, -78, -59 };
/*     */   
/*  82 */   private static final byte[] SHRINE_04_GATES_OF_NECROPOLIS = new byte[] { -114, -5, -34, -106, 126, 69, -90, 49, -122, -8, -11, -120, -104, 11, 114, -112 };
/*     */   
/*  84 */   private static final byte[] SHRINE_04_BLACK_SEPULCHER = new byte[] { 66, -95, 91, -100, -105, 75, 62, -49, -24, -35, -97, -111, 68, -64, 24, -119 };
/*     */   
/*  86 */   private static final byte[] SHRINE_04_BASTION_OF_CHAOS = new byte[] { -27, 2, 60, 14, 42, 65, -27, -102, 88, 91, 55, -121, 77, 122, 108, -86 };
/*     */   
/*  88 */   private static final byte[] SHRINE_04_TOMB_OF_WATCHERS = new byte[] { 28, -23, 71, -106, Byte.MAX_VALUE, 69, 90, -74, -78, -18, -75, -88, 96, 71, 3, -119 };
/*     */   
/*  90 */   private static final byte[] SHRINE_04_SANCTUM_OF_THE_IMMORTAL = new byte[] { 24, -122, 98, -122, 0, 75, 72, 94, -105, -92, -26, -80, 35, 77, 77, -95 };
/*     */   
/*  92 */   private static final byte[] SHRINE_05_GLOOMWALD_CROSSING = new byte[] { -36, 8, 74, -43, 78, 73, 104, 5, 33, -117, -89, -120, 104, -39, 9, 110 };
/*     */   
/*  94 */   private static final byte[] SHRINE_05_ANCIENT_GROVE = new byte[] { -124, -72, -91, 0, -72, 71, 118, Byte.MIN_VALUE, -94, 88, 26, -116, 56, 20, -100, 113 };
/*     */   
/*  96 */   private static final byte[] SHRINE_05_UGDENBOG_KR_VALL = new byte[] { 58, -108, 120, 117, -14, 67, -29, -82, 78, 70, -1, -105, -45, -7, -21, -88 };
/*     */   
/*  98 */   private static final byte[] SHRINE_05_TOMB_OF_UGDAL = new byte[] { -104, -67, -127, 26, -116, 66, 24, -70, 3, -68, -13, -68, -66, -25, -45, 52 };
/*     */   
/* 100 */   private static final byte[] SHRINE_05_TOMB_OF_UGDAL_ELITE = new byte[] { 83, -101, 113, -66, 11, 77, -89, 10, 69, -61, 66, -99, 8, 49, 126, 80 };
/*     */   
/* 102 */   private static final byte[] SHRINE_05_BARROWHOLM_MINE = new byte[] { 89, 15, -124, 2, -49, 74, 67, 87, -117, -44, -117, -102, -115, 112, 72, 107 };
/*     */   
/* 104 */   private static final byte[] SHRINE_05_DESOLATE_WASTES = new byte[] { 49, 2, -100, 64, -77, 76, 79, 73, -106, 125, 47, -107, -104, 99, 99, 8 };
/*     */   
/* 106 */   private static final byte[] SHRINE_05_MALMOUTH = new byte[] { 54, 116, -47, 41, -56, 66, 109, -69, 67, 43, 33, -77, -10, -42, 75, 16 };
/*     */   
/* 108 */   private static final byte[] SHRINE_05_RANSACKED_LIGHTHOUSE = new byte[] { 13, 71, 9, -2, 48, 71, 39, -4, -91, 52, 84, -79, 93, 40, 100, -81 };
/*     */   
/* 110 */   private static final byte[] SHRINE_05_CROWN_HILL = new byte[] { -64, 36, 66, 36, -72, 71, 90, 13, 76, 114, 107, -77, 112, 121, -46, -91 };
/*     */   
/* 112 */   private static final byte[] SHRINE_05_INFESTATION = new byte[] { 25, 117, 46, -71, 32, 64, -50, -78, -116, 79, 18, -67, 62, -118, -40, 103 };
/*     */   
/* 114 */   private static final byte[] SHRINE_05_SANCTUM_OF_THE_CHOSEN = new byte[] { -88, -53, 57, 29, 113, 65, 48, 122, -44, -44, -34, -118, 97, -115, -28, -108 };
/*     */   
/* 116 */   private static final byte[] SHRINE_06_SHATTERED_REALMS = new byte[] { -67, -6, -79, -114, 32, 64, 2, 109, -106, 81, -6, -84, -41, -42, -28, -23 };
/*     */   
/* 118 */   private static final byte[] SHRINE_06_TEMPLE_OSYR = new byte[] { 118, 58, 60, -99, 36, 68, -10, 2, 117, 11, -62, -70, -27, 30, 52, -120 };
/*     */   
/* 120 */   private static final byte[] SHRINE_06_KORVAN_OASIS = new byte[] { -99, 77, -40, -127, -65, 79, -126, 82, -66, 23, 52, -73, -22, 61, -49, -88 };
/*     */   
/* 122 */   private static final byte[] SHRINE_06_CAIRAN_DOCKS = new byte[] { -10, -46, -74, -17, -18, 72, -110, 104, 16, 17, -15, -107, -39, -29, 68, -10 };
/*     */   
/* 124 */   private static final byte[] SHRINE_06_SANDBLOWN_RUINS = new byte[] { -10, -86, -66, -47, -119, 73, -20, -124, 27, 43, 80, -96, 41, 102, -126, 30 };
/*     */   
/* 126 */   private static final byte[] SHRINE_06_SANCTUARY_HORRAN = new byte[] { 71, -72, -19, -73, -97, 77, 6, 24, -70, 109, -109, -80, 47, -49, -79, -15 };
/*     */   
/* 128 */   private static final byte[] SHRINE_06_RUINS_ABYD = new byte[] { 2, 104, -112, 8, 24, 68, -47, -118, -3, 23, -107, -86, 118, -3, -29, 35 };
/*     */   
/* 130 */   private static final byte[] SHRINE_06_VALLEY_CHOSEN = new byte[] { -70, 70, -88, 102, -56, 71, 44, Byte.MAX_VALUE, -71, -66, -101, -117, -4, Byte.MAX_VALUE, -93, -101 };
/*     */   
/* 132 */   private static final byte[] SHRINE_06_SUNWARD_SPIRE = new byte[] { 54, 8, Byte.MAX_VALUE, -76, -37, 71, 46, -66, 49, 32, 18, -113, 75, -23, 23, -91 };
/*     */   
/* 134 */   private static final byte[] SHRINE_06_ELDRITCH_SUN = new byte[] { 22, -101, 114, -123, -100, 76, 30, 122, 57, -10, -104, -112, 78, 0, -125, 89 };
/*     */   
/* 136 */   private static final byte[] SHRINE_06_SECRET_QUEST = new byte[] { -79, -110, 43, 5, 44, 67, 65, 71, 81, -33, 45, -103, 33, -121, -96, 79 };
/*     */   
/* 138 */   private static final byte[] SHRINE_06_LOST_OASIS = new byte[] { 24, 38, 7, 13, 37, 75, 38, 125, -67, -100, -12, -124, -42, -16, 85, 68 };
/*     */ 
/*     */   
/* 141 */   public static final GDCharUID UID_SHRINE_01_BURIAL_HILL = new GDCharUID(SHRINE_01_BURIAL_HILL);
/* 142 */   public static final GDCharUID UID_SHRINE_01_DEVILS_AQUIFIER = new GDCharUID(SHRINE_01_DEVILS_AQUIFIER);
/* 143 */   public static final GDCharUID UID_SHRINE_01_WIGHTMIRE_CAVERN = new GDCharUID(SHRINE_01_WIGHTMIRE_CAVERN);
/* 144 */   public static final GDCharUID UID_SHRINE_01_FLOODED_PASSAGE = new GDCharUID(SHRINE_01_FLOODED_PASSAGE);
/* 145 */   public static final GDCharUID UID_SHRINE_01_BURIAL_CAVE = new GDCharUID(SHRINE_01_BURIAL_CAVE);
/* 146 */   public static final GDCharUID UID_SHRINE_01_HALLOWED_HILL = new GDCharUID(SHRINE_01_HALLOWED_HILL);
/* 147 */   public static final GDCharUID UID_SHRINE_01_BURRWITCH_ESTATES = new GDCharUID(SHRINE_01_BURRWITCH_ESTATES);
/* 148 */   public static final GDCharUID UID_SHRINE_01_UNDERGROUND_TRANSIT = new GDCharUID(SHRINE_01_UNDERGROUND_TRANSIT);
/* 149 */   public static final GDCharUID UID_SHRINE_01_CRAIGS_CRAGS = new GDCharUID(SHRINE_01_CRAIGS_CRAGS);
/* 150 */   public static final GDCharUID UID_SHRINE_01_GUARDIAN_DREEG = new GDCharUID(SHRINE_01_GUARDIAN_DREEG);
/* 151 */   public static final GDCharUID UID_SHRINE_01_TEMPLE_THREE = new GDCharUID(SHRINE_01_TEMPLE_THREE);
/* 152 */   public static final GDCharUID UID_SHRINE_02_SPINED_COVE = new GDCharUID(SHRINE_02_SPINED_COVE);
/* 153 */   public static final GDCharUID UID_SHRINE_02_ROCKY_COAST = new GDCharUID(SHRINE_02_ROCKY_COAST);
/* 154 */   public static final GDCharUID UID_SHRINE_02_CRONLEYS_HIDEOUT = new GDCharUID(SHRINE_02_CRONLEYS_HIDEOUT);
/* 155 */   public static final GDCharUID UID_SHRINE_02_OLD_ARKOVIA = new GDCharUID(SHRINE_02_OLD_ARKOVIA);
/* 156 */   public static final GDCharUID UID_SHRINE_02_ARKOVIAN_UNDERCITY = new GDCharUID(SHRINE_02_ARKOVIAN_UNDERCITY);
/* 157 */   public static final GDCharUID UID_SHRINE_02_BROKEN_HILLS = new GDCharUID(SHRINE_02_BROKEN_HILLS);
/* 158 */   public static final GDCharUID UID_SHRINE_02_STEPS_OF_TORMENT = new GDCharUID(SHRINE_02_STEPS_OF_TORMENT);
/* 159 */   public static final GDCharUID UID_SHRINE_03_MOUNTAIN_DEEPS = new GDCharUID(SHRINE_03_MOUNTAIN_DEEPS);
/* 160 */   public static final GDCharUID UID_SHRINE_03_FORGOTTEN_DEPTHS = new GDCharUID(SHRINE_03_FORGOTTEN_DEPTHS);
/* 161 */   public static final GDCharUID UID_SHRINE_03_TYRANTS_HOLD = new GDCharUID(SHRINE_03_TYRANTS_HOLD);
/* 162 */   public static final GDCharUID UID_SHRINE_03_INFESTED_FARMS = new GDCharUID(SHRINE_03_INFESTED_FARMS);
/* 163 */   public static final GDCharUID UID_SHRINE_03_DEN_OF_THE_LOST = new GDCharUID(SHRINE_03_DEN_OF_THE_LOST);
/* 164 */   public static final GDCharUID UID_SHRINE_03_CONFLAGRATION = new GDCharUID(SHRINE_03_CONFLAGRATION);
/* 165 */   public static final GDCharUID UID_SHRINE_03_PORT_VALBURY = new GDCharUID(SHRINE_03_PORT_VALBURY);
/* 166 */   public static final GDCharUID UID_SHRINE_03_BLOOD_GROVE = new GDCharUID(SHRINE_03_BLOOD_GROVE);
/* 167 */   public static final GDCharUID UID_SHRINE_03_STONEREND_QUARRY = new GDCharUID(SHRINE_03_STONEREND_QUARRY);
/* 168 */   public static final GDCharUID UID_SHRINE_03_VILLAGE_OF_DARKVALE = new GDCharUID(SHRINE_03_VILLAGE_OF_DARKVALE);
/* 169 */   public static final GDCharUID UID_SHRINE_04_ASTERKARN_VALLEY = new GDCharUID(SHRINE_04_ASTERKARN_VALLEY);
/* 170 */   public static final GDCharUID UID_SHRINE_04_MOGDROGENS_SHRINE = new GDCharUID(SHRINE_04_MOGDROGENS_SHRINE);
/* 171 */   public static final GDCharUID UID_SHRINE_04_TOMB_OF_THE_DAMNED = new GDCharUID(SHRINE_04_TOMB_OF_THE_DAMNED);
/* 172 */   public static final GDCharUID UID_SHRINE_04_GATES_OF_NECROPOLIS = new GDCharUID(SHRINE_04_GATES_OF_NECROPOLIS);
/* 173 */   public static final GDCharUID UID_SHRINE_04_BLACK_SEPULCHER = new GDCharUID(SHRINE_04_BLACK_SEPULCHER);
/* 174 */   public static final GDCharUID UID_SHRINE_04_BASTION_OF_CHAOS = new GDCharUID(SHRINE_04_BASTION_OF_CHAOS);
/* 175 */   public static final GDCharUID UID_SHRINE_04_TOMB_OF_WATCHERS = new GDCharUID(SHRINE_04_TOMB_OF_WATCHERS);
/* 176 */   public static final GDCharUID UID_SHRINE_04_SANCTUM_OF_THE_IMMORTAL = new GDCharUID(SHRINE_04_SANCTUM_OF_THE_IMMORTAL);
/* 177 */   public static final GDCharUID UID_SHRINE_05_GLOOMWALD_CROSSING = new GDCharUID(SHRINE_05_GLOOMWALD_CROSSING);
/* 178 */   public static final GDCharUID UID_SHRINE_05_ANCIENT_GROVE = new GDCharUID(SHRINE_05_ANCIENT_GROVE);
/* 179 */   public static final GDCharUID UID_SHRINE_05_UGDENBOG_KR_VALL = new GDCharUID(SHRINE_05_UGDENBOG_KR_VALL);
/* 180 */   public static final GDCharUID UID_SHRINE_05_TOMB_OF_UGDAL = new GDCharUID(SHRINE_05_TOMB_OF_UGDAL);
/* 181 */   public static final GDCharUID UID_SHRINE_05_TOMB_OF_UGDAL_ELITE = new GDCharUID(SHRINE_05_TOMB_OF_UGDAL_ELITE);
/* 182 */   public static final GDCharUID UID_SHRINE_05_BARROWHOLM_MINE = new GDCharUID(SHRINE_05_BARROWHOLM_MINE);
/* 183 */   public static final GDCharUID UID_SHRINE_05_DESOLATE_WASTES = new GDCharUID(SHRINE_05_DESOLATE_WASTES);
/* 184 */   public static final GDCharUID UID_SHRINE_05_MALMOUTH = new GDCharUID(SHRINE_05_MALMOUTH);
/* 185 */   public static final GDCharUID UID_SHRINE_05_RANSACKED_LIGHTHOUSE = new GDCharUID(SHRINE_05_RANSACKED_LIGHTHOUSE);
/* 186 */   public static final GDCharUID UID_SHRINE_05_CROWN_HILL = new GDCharUID(SHRINE_05_CROWN_HILL);
/* 187 */   public static final GDCharUID UID_SHRINE_05_INFESTATION = new GDCharUID(SHRINE_05_INFESTATION);
/* 188 */   public static final GDCharUID UID_SHRINE_05_SANCTUM_OF_THE_CHOSEN = new GDCharUID(SHRINE_05_SANCTUM_OF_THE_CHOSEN);
/* 189 */   public static final GDCharUID UID_SHRINE_06_SHATTERED_REALMS = new GDCharUID(SHRINE_06_SHATTERED_REALMS);
/* 190 */   public static final GDCharUID UID_SHRINE_06_TEMPLE_OSYR = new GDCharUID(SHRINE_06_TEMPLE_OSYR);
/* 191 */   public static final GDCharUID UID_SHRINE_06_CAIRAN_DOCKS = new GDCharUID(SHRINE_06_CAIRAN_DOCKS);
/* 192 */   public static final GDCharUID UID_SHRINE_06_SANCTUARY_HORRAN = new GDCharUID(SHRINE_06_SANCTUARY_HORRAN);
/* 193 */   public static final GDCharUID UID_SHRINE_06_RUINS_ABYD = new GDCharUID(SHRINE_06_RUINS_ABYD);
/* 194 */   public static final GDCharUID UID_SHRINE_06_VALLEY_CHOSEN = new GDCharUID(SHRINE_06_VALLEY_CHOSEN);
/* 195 */   public static final GDCharUID UID_SHRINE_06_SUNWARD_SPIRE = new GDCharUID(SHRINE_06_SUNWARD_SPIRE);
/* 196 */   public static final GDCharUID UID_SHRINE_06_ELDRITCH_SUN = new GDCharUID(SHRINE_06_ELDRITCH_SUN);
/* 197 */   public static final GDCharUID UID_SHRINE_06_KORVAN_OASIS = new GDCharUID(SHRINE_06_KORVAN_OASIS);
/* 198 */   public static final GDCharUID UID_SHRINE_06_SANDBLOWN_RUINS = new GDCharUID(SHRINE_06_SANDBLOWN_RUINS);
/* 199 */   public static final GDCharUID UID_SHRINE_06_SECRET_QUEST = new GDCharUID(SHRINE_06_SECRET_QUEST);
/* 200 */   public static final GDCharUID UID_SHRINE_06_LOST_OASIS = new GDCharUID(SHRINE_06_LOST_OASIS);
/*     */   
/*     */   private static final int VERSION = 2;
/*     */   
/*     */   private static final int BLOCK = 17;
/*     */   
/*     */   private int version;
/*     */   
/*     */   private ArrayList<List<GDCharUID>> uidLists;
/*     */   
/*     */   private GDChar gdc;
/*     */   private boolean changed;
/*     */   
/*     */   public GDCharShrineList(GDChar gdc) {
/* 214 */     int size = 6;
/*     */     
/* 216 */     this.uidLists = new ArrayList<>(size);
/*     */     
/* 218 */     for (int i = 0; i < size; i++) {
/* 219 */       this.uidLists.add(new LinkedList<>());
/*     */     }
/*     */     
/* 222 */     this.gdc = gdc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getDiscoveredShrinesList(int difficulty) {
/* 230 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 232 */     if (difficulty < 0 || difficulty > 2) return list;
/*     */     
/* 234 */     return this.uidLists.get(2 * difficulty + 1);
/*     */   }
/*     */   
/*     */   public List<GDCharUID> getRestoredShrinesList(int difficulty) {
/* 238 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 240 */     if (difficulty < 0 || difficulty > 2) return list;
/*     */     
/* 242 */     return this.uidLists.get(2 * difficulty);
/*     */   }
/*     */   
/*     */   public boolean hasChanged() {
/* 246 */     return this.changed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDiscoveredShrinesList(int difficulty, List<GDCharUID> addList, List<GDCharUID> removeList) {
/* 254 */     if (difficulty < 0 || difficulty > 2) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 259 */     int index = 2 * difficulty + 1;
/*     */     
/* 261 */     List<GDCharUID> shrines = this.uidLists.get(index);
/* 262 */     List<GDCharUID> newList = new LinkedList<>();
/* 263 */     for (GDCharUID uid : shrines) {
/* 264 */       if (!removeList.contains(uid)) {
/* 265 */         newList.add(uid); continue;
/*     */       } 
/* 267 */       this.changed = true;
/*     */     } 
/*     */ 
/*     */     
/* 271 */     for (GDCharUID uid : addList) {
/* 272 */       if (!newList.contains(uid)) {
/* 273 */         newList.add(uid);
/*     */         
/* 275 */         this.changed = true;
/*     */       } 
/*     */     } 
/*     */     
/* 279 */     this.uidLists.set(index, newList);
/*     */   }
/*     */   
/*     */   public void setRestoredShrinesList(int difficulty, List<GDCharUID> addList, List<GDCharUID> removeList) {
/* 283 */     if (difficulty < 0 || difficulty > 2) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 288 */     int index = 2 * difficulty;
/*     */     
/* 290 */     List<GDCharUID> shrines = this.uidLists.get(index);
/* 291 */     List<GDCharUID> newList = new LinkedList<>();
/* 292 */     for (GDCharUID uid : shrines) {
/* 293 */       if (!removeList.contains(uid)) {
/* 294 */         newList.add(uid); continue;
/*     */       } 
/* 296 */       this.changed = true;
/*     */     } 
/*     */ 
/*     */     
/* 300 */     for (GDCharUID uid : addList) {
/* 301 */       if (!newList.contains(uid)) {
/* 302 */         newList.add(uid);
/*     */         
/* 304 */         this.changed = true;
/*     */       } 
/*     */     } 
/*     */     
/* 308 */     this.uidLists.set(index, newList);
/*     */     
/* 310 */     addDiscoveredShrinesList(difficulty, addList);
/*     */   }
/*     */   
/*     */   private void addDiscoveredShrinesList(int difficulty, List<GDCharUID> list) {
/* 314 */     if (difficulty < 0 || difficulty > 2)
/*     */       return; 
/* 316 */     List<GDCharUID> shrines = this.uidLists.get(2 * difficulty + 1);
/* 317 */     for (GDCharUID uid : list) {
/* 318 */       if (!shrines.contains(uid)) {
/* 319 */         shrines.add(uid);
/*     */         
/* 321 */         this.changed = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read() throws IOException {
/* 331 */     int val = 0;
/*     */     
/* 333 */     GDReader.Block block = new GDReader.Block();
/*     */     
/* 335 */     val = GDReader.readBlockStart(block);
/* 336 */     if (val != 17) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/* 338 */     this.version = GDReader.readEncInt(true);
/* 339 */     if (this.version != 2) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/* 341 */     for (int i = 0; i < this.uidLists.size(); i++) {
/* 342 */       GDChar.readUIDList(this.uidLists.get(i));
/*     */     }
/*     */ 
/*     */     
/* 346 */     GDReader.readBlockEnd(block);
/*     */     
/* 348 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/* 352 */     GDReader.Block block = new GDReader.Block();
/* 353 */     GDWriter.writeBlockStart(block, 17);
/*     */     
/* 355 */     GDWriter.writeInt(2);
/*     */     
/* 357 */     for (int i = 0; i < this.uidLists.size(); i++) {
/* 358 */       GDChar.writeUIDList(this.uidLists.get(i));
/*     */     }
/*     */ 
/*     */     
/* 362 */     GDWriter.writeBlockEnd(block);
/*     */     
/* 364 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/* 368 */     int size = 0;
/*     */     
/* 370 */     size += 4;
/* 371 */     size += 4;
/* 372 */     size += 4;
/*     */     
/* 374 */     for (int i = 0; i < this.uidLists.size(); i++) {
/* 375 */       size += 4;
/* 376 */       size += ((List)this.uidLists.get(i)).size() * GDCharUID.getByteSize();
/*     */     } 
/*     */     
/* 379 */     size += 4;
/*     */     
/* 381 */     return size;
/*     */   }
/*     */   
/*     */   public void print() {
/* 385 */     for (int i = 0; i < this.uidLists.size(); i++) {
/* 386 */       System.out.println("Index : " + Integer.toString(i));
/*     */       
/* 388 */       for (GDCharUID uid : this.uidLists.get(i)) {
/* 389 */         uid.println();
/*     */       }
/*     */       
/* 392 */       System.out.println();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\character\GDCharShrineList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */