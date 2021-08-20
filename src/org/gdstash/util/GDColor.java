/*     */ package org.gdstash.util;
/*     */ 
/*     */ import java.awt.Color;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDColor
/*     */ {
/*     */   public enum UIType
/*     */   {
/*  14 */     Dark, Light;
/*     */   }
/*  16 */   private static UIType uiType = UIType.Light;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  22 */   private static final Color COLOR_FG_COMMON_LIGHTUI = Color.BLACK;
/*  23 */   private static final Color COLOR_FG_MAGICAL_LIGHTUI = new Color(200, 145, 0);
/*  24 */   private static final Color COLOR_FG_RARE_LIGHTUI = new Color(0, 85, 15);
/*  25 */   private static final Color COLOR_FG_EPIC_LIGHTUI = new Color(0, 50, 125);
/*  26 */   private static final Color COLOR_FG_LEGENDARY_LIGHTUI = new Color(68, 0, 125);
/*     */   
/*  28 */   private static final Color COLOR_FG_COMPONENT_LIGHTUI = new Color(182, 122, 57);
/*  29 */   private static final Color COLOR_FG_ENCHANTMENT_LIGHTUI = new Color(112, 157, 0);
/*  30 */   private static final Color COLOR_FG_ARTIFACT_LIGHTUI = new Color(0, 120, 200);
/*     */ 
/*     */   
/*  33 */   private static final Color COLOR_FG_COMMON_DARKUI = Color.WHITE;
/*  34 */   private static final Color COLOR_FG_MAGICAL_DARKUI = new Color(227, 219, 24);
/*  35 */   private static final Color COLOR_FG_RARE_DARKUI = new Color(60, 227, 71);
/*  36 */   private static final Color COLOR_FG_EPIC_DARKUI = new Color(49, 136, 200);
/*  37 */   private static final Color COLOR_FG_LEGENDARY_DARKUI = new Color(188, 148, 175);
/*     */   
/*  39 */   private static final Color COLOR_FG_COMPONENT_DARKUI = new Color(219, 148, 69);
/*  40 */   private static final Color COLOR_FG_ENCHANTMENT_DARKUI = new Color(140, 193, 0);
/*  41 */   private static final Color COLOR_FG_ARTIFACT_DARKUI = new Color(0, 120, 200);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private static final Color COLOR_BG_COMMON_LIGHTUI = Color.WHITE;
/*  48 */   private static final Color COLOR_BG_MAGICAL_LIGHTUI = new Color(240, 240, 140);
/*  49 */   private static final Color COLOR_BG_RARE_LIGHTUI = new Color(200, 240, 180);
/*  50 */   private static final Color COLOR_BG_EPIC_LIGHTUI = new Color(200, 230, 250);
/*  51 */   private static final Color COLOR_BG_LEGENDARY_LIGHTUI = new Color(230, 200, 250);
/*     */ 
/*     */   
/*  54 */   private static final Color COLOR_BG_COMMON_DARKUI = new Color(16, 16, 16);
/*  55 */   private static final Color COLOR_BG_MAGICAL_DARKUI = new Color(80, 70, 0);
/*  56 */   private static final Color COLOR_BG_RARE_DARKUI = new Color(30, 80, 30);
/*  57 */   private static final Color COLOR_BG_EPIC_DARKUI = new Color(0, 30, 80);
/*  58 */   private static final Color COLOR_BG_LEGENDARY_DARKUI = new Color(50, 10, 80);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private static final Color COLOR_OVERLAY_COMMON = new Color(204, 204, 204);
/*  64 */   private static final Color COLOR_OVERLAY_MAGICAL = new Color(229, 204, 0);
/*  65 */   private static final Color COLOR_OVERLAY_RARE = new Color(102, 217, 0);
/*  66 */   private static final Color COLOR_OVERLAY_EPIC = new Color(76, 115, 217);
/*  67 */   private static final Color COLOR_OVERLAY_LEGENDARY = new Color(76, 38, 166);
/*     */   
/*  69 */   private static final Color COLOR_OVERLAY_ARTIFACT = new Color(0, 255, 255);
/*  70 */   private static final Color COLOR_OVERLAY_COMPONENT = new Color(255, 178, 51);
/*  71 */   private static final Color COLOR_OVERLAY_ENCHANTMENT = new Color(145, 203, 0);
/*  72 */   private static final Color COLOR_OVERLAY_QUEST = new Color(204, 51, 229);
/*  73 */   private static final Color COLOR_OVERLAY_LORE = new Color(194, 176, 196);
/*     */   
/*     */   private static final String HTML_COLOR_MAGIC_LIGHTUI = "<font color = \"#B48200\">";
/*     */   
/*     */   private static final String HTML_COLOR_RARE_LIGHTUI = "<font color = \"#00460A\">";
/*     */   
/*     */   private static final String HTML_COLOR_EPIC_LIGHTUI = "<font color = \"#002864\">";
/*     */   
/*     */   private static final String HTML_COLOR_LEGENDARY_LIGHTUI = "<font color = \"#320064\">";
/*     */   
/*     */   private static final String HTML_COLOR_QUEST_LIGHTUI = "<font color = \"#320064\">";
/*     */   
/*     */   private static final String HTML_COLOR_COMPONENT_LIGHTUI = "<font color = \"#DA9345\">";
/*     */   
/*     */   private static final String HTML_COLOR_ENCHANTMENT_LIGHTUI = "<font color = \"#88BE00\">";
/*     */   
/*     */   private static final String HTML_COLOR_SKILL_LIGHTUI = "<font color = \"#598AE5\">";
/*     */   
/*     */   private static final String HTML_COLOR_ITEM_TYPE_LIGHTUI = "<font color = \"#C0A560\">";
/*     */   
/*     */   private static final String HTML_COLOR_ITEMSET_LIGHTUI = "<font color = \"#4D8489\">";
/*     */   
/*     */   private static final String HTML_COLOR_STAT_LIGHTUI = "<font color = \"#404040\">";
/*     */   
/*     */   private static final String HTML_COLOR_USED_SLOTS_LIGHTUI = "<font color = \"#000000\">";
/*     */   
/*     */   private static final String HTML_COLOR_CHAR_GD_LIGHTUI = "<font color = \"#990000\">";
/*     */   
/*     */   private static final String HTML_COLOR_CHAR_AOM_LIGHTUI = "<font color = \"#008800\">";
/*     */   
/*     */   private static final String HTML_COLOR_CHAR_FG_LIGHTUI = "<font color = \"#BB4000\">";
/*     */   
/*     */   private static final String HTML_COLOR_MAGIC_DARKUI = "<font color = \"#FFF62C\">";
/*     */   
/*     */   private static final String HTML_COLOR_RARE_DARKUI = "<font color = \"#10EB5D\">";
/*     */   
/*     */   private static final String HTML_COLOR_EPIC_DARKUI = "<font color = \"#39ABCD\">";
/*     */   
/*     */   private static final String HTML_COLOR_LEGENDARY_DARKUI = "<font color = \"#BD9AC7\">";
/*     */   
/*     */   private static final String HTML_COLOR_QUEST_DARKUI = "<font color = \"#BD9AC7\">";
/*     */   
/*     */   private static final String HTML_COLOR_COMPONENT_DARKUI = "<font color = \"#DA9345\">";
/*     */   
/*     */   private static final String HTML_COLOR_ENCHANTMENT_DARKUI = "<font color = \"#78AA00\">";
/*     */   private static final String HTML_COLOR_SKILL_DARKUI = "<font color = \"#598AE5\">";
/*     */   private static final String HTML_COLOR_ITEM_TYPE_DARKUI = "<font color = \"#C0A560\">";
/*     */   private static final String HTML_COLOR_ITEMSET_DARKUI = "<font color = \"#4D8489\">";
/*     */   private static final String HTML_COLOR_STAT_DARKUI = "<font color = \"#C0C0C0\">";
/*     */   private static final String HTML_COLOR_USED_SLOTS_DARKUI = "<font color = \"#FFFFFF\">";
/*     */   private static final String HTML_COLOR_CHAR_GD_DARKUI = "<font color = \"#990000\">";
/*     */   private static final String HTML_COLOR_CHAR_AOM_DARKUI = "<font color = \"#008800\">";
/*     */   private static final String HTML_COLOR_CHAR_FG_DARKUI = "<font color = \"#BB4000\">";
/*     */   public static final String HTML_COLOR_GD_A_AQUA_LIGHTUI = "<font color = \"#60BC9C\">";
/*     */   public static final String HTML_COLOR_GD_B_BLUE_LIGHTUI = "<font color = \"#3298B5\">";
/*     */   public static final String HTML_COLOR_GD_C_CYAN_LIGHTUI = "<font color = \"#00C6C6\">";
/*     */   public static final String HTML_COLOR_GD_D_DARKGRAY_LIGHTUI = "<font color = \"#404040\">";
/*     */   public static final String HTML_COLOR_GD_F_FUSHIA_LIGHTUI = "<font color = \"#F76782\">";
/*     */   public static final String HTML_COLOR_GD_G_GREEN_LIGHTUI = "<font color = \"#0DBC47\">";
/*     */   public static final String HTML_COLOR_GD_I_INDIGO_LIGHTUI = "<font color = \"#5A039A\">";
/*     */   public static final String HTML_COLOR_GD_K_KHAKI_LIGHTUI = "<font color = \"#A8A163\">";
/*     */   public static final String HTML_COLOR_GD_L_OLIVE_LIGHTUI = "<font color = \"#7CAD00\">";
/*     */   public static final String HTML_COLOR_GD_M_MAROON_LIGHTUI = "<font color = \"#800000\">";
/*     */   public static final String HTML_COLOR_GD_O_ORANGE_LIGHTUI = "<font color = \"#D38E43\">";
/*     */   public static final String HTML_COLOR_GD_P_PURPLE_LIGHTUI = "<font color = \"#A767C6\">";
/*     */   public static final String HTML_COLOR_GD_R_RED_LIGHTUI = "<font color = \"#FF2E05\">";
/*     */   public static final String HTML_COLOR_GD_S_SILVER_LIGHTUI = "<font color = \"#787878\">";
/*     */   public static final String HTML_COLOR_GD_T_TEAL_LIGHTUI = "<font color = \"#00C6A2\">";
/*     */   public static final String HTML_COLOR_GD_W_WHITE_LIGHTUI = "<font color = \"#9A9A9A\">";
/*     */   public static final String HTML_COLOR_GD_Y_YELLOW_LIGHTUI = "<font color = \"#B78021\">";
/*     */   private static final String HTML_COLOR_GD_A_AQUA_DARKUI = "<font color = \"#80FFD5\">";
/*     */   private static final String HTML_COLOR_GD_B_BLUE_DARKUI = "<font color = \"#39ABCD\">";
/*     */   private static final String HTML_COLOR_GD_C_CYAN_DARKUI = "<font color = \"#00FFFF\">";
/*     */   private static final String HTML_COLOR_GD_D_DARKGRAY_DARKUI = "<font color = \"#1A1A1A\">";
/*     */   private static final String HTML_COLOR_GD_F_FUSHIA_DARKUI = "<font color = \"#FF6984\">";
/*     */   private static final String HTML_COLOR_GD_G_GREEN_DARKUI = "<font color = \"#10EB5D\">";
/*     */   private static final String HTML_COLOR_GD_I_INDIGO_DARKUI = "<font color = \"#5A039A\">";
/*     */   private static final String HTML_COLOR_GD_K_KHAKI_DARKUI = "<font color = \"#F1E78D\">";
/*     */   private static final String HTML_COLOR_GD_L_OLIVE_DARKUI = "<font color = \"#92CC00\">";
/*     */   private static final String HTML_COLOR_GD_M_MAROON_DARKUI = "<font color = \"#800000\">";
/*     */   private static final String HTML_COLOR_GD_O_ORANGE_DARKUI = "<font color = \"#F3A44D\">";
/*     */   private static final String HTML_COLOR_GD_P_PURPLE_DARKUI = "<font color = \"#BD9AC7\">";
/*     */   private static final String HTML_COLOR_GD_R_RED_DARKUI = "<font color = \"#FF4200\">";
/*     */   private static final String HTML_COLOR_GD_S_SILVER_DARKUI = "<font color = \"#9A9A9A\">";
/*     */   private static final String HTML_COLOR_GD_T_TEAL_DARKUI = "<font color = \"#00FFD2\">";
/*     */   private static final String HTML_COLOR_GD_W_WHITE_DARKUI = "<font color = \"#FFFFFF\">";
/*     */   private static final String HTML_COLOR_GD_Y_YELLOW_DARKUI = "<font color = \"#FFF62C\">";
/* 160 */   public static Color COLOR_FG_COMMON = COLOR_FG_COMMON_LIGHTUI;
/* 161 */   public static Color COLOR_FG_MAGICAL = COLOR_FG_MAGICAL_LIGHTUI;
/* 162 */   public static Color COLOR_FG_RARE = COLOR_FG_RARE_LIGHTUI;
/* 163 */   public static Color COLOR_FG_EPIC = COLOR_FG_EPIC_LIGHTUI;
/* 164 */   public static Color COLOR_FG_LEGENDARY = COLOR_FG_LEGENDARY_LIGHTUI;
/*     */   
/* 166 */   public static Color COLOR_FG_COMPONENT = COLOR_FG_COMPONENT_LIGHTUI;
/* 167 */   public static Color COLOR_FG_ENCHANTMENT = COLOR_FG_ENCHANTMENT_LIGHTUI;
/* 168 */   public static Color COLOR_FG_ARTIFACT = COLOR_FG_ARTIFACT_LIGHTUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   public static Color COLOR_BG_COMMON = COLOR_BG_COMMON_LIGHTUI;
/* 176 */   public static Color COLOR_BG_MAGICAL = COLOR_BG_MAGICAL_LIGHTUI;
/* 177 */   public static Color COLOR_BG_RARE = COLOR_BG_RARE_LIGHTUI;
/* 178 */   public static Color COLOR_BG_EPIC = COLOR_BG_EPIC_LIGHTUI;
/* 179 */   public static Color COLOR_BG_LEGENDARY = COLOR_BG_LEGENDARY_LIGHTUI;
/*     */   
/* 181 */   public static String HTML_COLOR_MAGIC = "<font color = \"#B48200\">";
/* 182 */   public static String HTML_COLOR_RARE = "<font color = \"#00460A\">";
/* 183 */   public static String HTML_COLOR_EPIC = "<font color = \"#002864\">";
/* 184 */   public static String HTML_COLOR_LEGENDARY = "<font color = \"#320064\">";
/* 185 */   public static String HTML_COLOR_QUEST = "<font color = \"#320064\">";
/*     */   
/* 187 */   public static String HTML_COLOR_COMPONENT = "<font color = \"#DA9345\">";
/* 188 */   public static String HTML_COLOR_ENCHANTMENT = "<font color = \"#88BE00\">";
/* 189 */   public static String HTML_COLOR_SKILL = "<font color = \"#598AE5\">";
/* 190 */   public static String HTML_COLOR_ITEM_TYPE = "<font color = \"#C0A560\">";
/* 191 */   public static String HTML_COLOR_ITEMSET = "<font color = \"#4D8489\">";
/* 192 */   public static String HTML_COLOR_STAT = "<font color = \"#404040\">";
/* 193 */   public static String HTML_COLOR_USED_SLOTS = "<font color = \"#000000\">";
/* 194 */   public static String HTML_COLOR_CHAR_GD = "<font color = \"#990000\">";
/* 195 */   public static String HTML_COLOR_CHAR_AOM = "<font color = \"#008800\">";
/* 196 */   public static String HTML_COLOR_CHAR_FG = "<font color = \"#BB4000\">";
/*     */   
/* 198 */   public static String HTML_COLOR_GD_A_AQUA = "<font color = \"#60BC9C\">";
/* 199 */   public static String HTML_COLOR_GD_B_BLUE = "<font color = \"#3298B5\">";
/* 200 */   public static String HTML_COLOR_GD_C_CYAN = "<font color = \"#00C6C6\">";
/* 201 */   public static String HTML_COLOR_GD_D_DARKGRAY = "<font color = \"#404040\">";
/* 202 */   public static String HTML_COLOR_GD_F_FUSHIA = "<font color = \"#F76782\">";
/* 203 */   public static String HTML_COLOR_GD_G_GREEN = "<font color = \"#0DBC47\">";
/* 204 */   public static String HTML_COLOR_GD_I_INDIGO = "<font color = \"#5A039A\">";
/* 205 */   public static String HTML_COLOR_GD_K_KHAKI = "<font color = \"#A8A163\">";
/* 206 */   public static String HTML_COLOR_GD_L_OLIVE = "<font color = \"#7CAD00\">";
/* 207 */   public static String HTML_COLOR_GD_M_MAROON = "<font color = \"#800000\">";
/* 208 */   public static String HTML_COLOR_GD_O_ORANGE = "<font color = \"#D38E43\">";
/* 209 */   public static String HTML_COLOR_GD_P_PURPLE = "<font color = \"#A767C6\">";
/* 210 */   public static String HTML_COLOR_GD_R_RED = "<font color = \"#FF2E05\">";
/* 211 */   public static String HTML_COLOR_GD_S_SILVER = "<font color = \"#787878\">";
/* 212 */   public static String HTML_COLOR_GD_T_TEAL = "<font color = \"#00C6A2\">";
/* 213 */   public static String HTML_COLOR_GD_W_WHITE = "<font color = \"#9A9A9A\">";
/* 214 */   public static String HTML_COLOR_GD_Y_YELLOW = "<font color = \"#B78021\">";
/*     */   
/*     */   public static final String HTML_COLOR_END = "</font>";
/*     */   
/*     */   public static void setLookAndFeelUI(String className) {
/* 219 */     UIType uiType = getLookAndFeelUIType(className);
/*     */     
/* 221 */     setUI(uiType);
/*     */   }
/*     */   
/*     */   public static UIType getLookAndFeelUIType(String className) {
/* 225 */     if (className == null) return UIType.Light;
/*     */     
/* 227 */     if (className.equals("com.jtattoo.plaf.hifi.HiFiLookAndFeel")) return UIType.Dark; 
/* 228 */     if (className.equals("com.jtattoo.plaf.noire.NoireLookAndFeel")) return UIType.Dark;
/*     */     
/* 230 */     return UIType.Light;
/*     */   }
/*     */   
/*     */   public static void setUI(UIType uiType) {
/* 234 */     GDColor.uiType = uiType;
/*     */     
/* 236 */     if (uiType == UIType.Dark) {
/* 237 */       COLOR_FG_COMMON = COLOR_FG_COMMON_DARKUI;
/* 238 */       COLOR_FG_MAGICAL = COLOR_FG_MAGICAL_DARKUI;
/* 239 */       COLOR_FG_RARE = COLOR_FG_RARE_DARKUI;
/* 240 */       COLOR_FG_EPIC = COLOR_FG_EPIC_DARKUI;
/* 241 */       COLOR_FG_LEGENDARY = COLOR_FG_LEGENDARY_DARKUI;
/*     */       
/* 243 */       COLOR_FG_COMPONENT = COLOR_FG_COMPONENT_DARKUI;
/* 244 */       COLOR_FG_ENCHANTMENT = COLOR_FG_ENCHANTMENT_DARKUI;
/* 245 */       COLOR_FG_ARTIFACT = COLOR_FG_ARTIFACT_DARKUI;
/*     */       
/* 247 */       COLOR_BG_COMMON = COLOR_BG_COMMON_DARKUI;
/* 248 */       COLOR_BG_MAGICAL = COLOR_BG_MAGICAL_DARKUI;
/* 249 */       COLOR_BG_RARE = COLOR_BG_RARE_DARKUI;
/* 250 */       COLOR_BG_EPIC = COLOR_BG_EPIC_DARKUI;
/* 251 */       COLOR_BG_LEGENDARY = COLOR_BG_LEGENDARY_DARKUI;
/*     */       
/* 253 */       HTML_COLOR_MAGIC = "<font color = \"#FFF62C\">";
/* 254 */       HTML_COLOR_RARE = "<font color = \"#10EB5D\">";
/* 255 */       HTML_COLOR_EPIC = "<font color = \"#39ABCD\">";
/* 256 */       HTML_COLOR_LEGENDARY = "<font color = \"#BD9AC7\">";
/* 257 */       HTML_COLOR_QUEST = "<font color = \"#BD9AC7\">";
/*     */       
/* 259 */       HTML_COLOR_COMPONENT = "<font color = \"#DA9345\">";
/* 260 */       HTML_COLOR_ENCHANTMENT = "<font color = \"#78AA00\">";
/* 261 */       HTML_COLOR_SKILL = "<font color = \"#598AE5\">";
/* 262 */       HTML_COLOR_ITEM_TYPE = "<font color = \"#C0A560\">";
/* 263 */       HTML_COLOR_ITEMSET = "<font color = \"#4D8489\">";
/* 264 */       HTML_COLOR_STAT = "<font color = \"#C0C0C0\">";
/* 265 */       HTML_COLOR_USED_SLOTS = "<font color = \"#FFFFFF\">";
/* 266 */       HTML_COLOR_CHAR_GD = "<font color = \"#990000\">";
/* 267 */       HTML_COLOR_CHAR_AOM = "<font color = \"#008800\">";
/* 268 */       HTML_COLOR_CHAR_FG = "<font color = \"#BB4000\">";
/*     */       
/* 270 */       HTML_COLOR_GD_A_AQUA = "<font color = \"#80FFD5\">";
/* 271 */       HTML_COLOR_GD_B_BLUE = "<font color = \"#39ABCD\">";
/* 272 */       HTML_COLOR_GD_C_CYAN = "<font color = \"#00FFFF\">";
/* 273 */       HTML_COLOR_GD_D_DARKGRAY = "<font color = \"#1A1A1A\">";
/* 274 */       HTML_COLOR_GD_F_FUSHIA = "<font color = \"#FF6984\">";
/* 275 */       HTML_COLOR_GD_G_GREEN = "<font color = \"#10EB5D\">";
/* 276 */       HTML_COLOR_GD_I_INDIGO = "<font color = \"#5A039A\">";
/* 277 */       HTML_COLOR_GD_K_KHAKI = "<font color = \"#F1E78D\">";
/* 278 */       HTML_COLOR_GD_L_OLIVE = "<font color = \"#92CC00\">";
/* 279 */       HTML_COLOR_GD_M_MAROON = "<font color = \"#800000\">";
/* 280 */       HTML_COLOR_GD_O_ORANGE = "<font color = \"#F3A44D\">";
/* 281 */       HTML_COLOR_GD_P_PURPLE = "<font color = \"#BD9AC7\">";
/* 282 */       HTML_COLOR_GD_R_RED = "<font color = \"#FF4200\">";
/* 283 */       HTML_COLOR_GD_S_SILVER = "<font color = \"#9A9A9A\">";
/* 284 */       HTML_COLOR_GD_T_TEAL = "<font color = \"#00FFD2\">";
/* 285 */       HTML_COLOR_GD_W_WHITE = "<font color = \"#FFFFFF\">";
/* 286 */       HTML_COLOR_GD_Y_YELLOW = "<font color = \"#FFF62C\">";
/*     */     } 
/*     */     
/* 289 */     if (uiType == UIType.Light) {
/* 290 */       COLOR_FG_COMMON = COLOR_FG_COMMON_LIGHTUI;
/* 291 */       COLOR_FG_MAGICAL = COLOR_FG_MAGICAL_LIGHTUI;
/* 292 */       COLOR_FG_RARE = COLOR_FG_RARE_LIGHTUI;
/* 293 */       COLOR_FG_EPIC = COLOR_FG_EPIC_LIGHTUI;
/* 294 */       COLOR_FG_LEGENDARY = COLOR_FG_LEGENDARY_LIGHTUI;
/*     */       
/* 296 */       COLOR_FG_COMPONENT = COLOR_FG_COMPONENT_LIGHTUI;
/* 297 */       COLOR_FG_ENCHANTMENT = COLOR_FG_ENCHANTMENT_LIGHTUI;
/* 298 */       COLOR_FG_ARTIFACT = COLOR_FG_ARTIFACT_LIGHTUI;
/*     */       
/* 300 */       COLOR_BG_COMMON = COLOR_BG_COMMON_LIGHTUI;
/* 301 */       COLOR_BG_MAGICAL = COLOR_BG_MAGICAL_LIGHTUI;
/* 302 */       COLOR_BG_RARE = COLOR_BG_RARE_LIGHTUI;
/* 303 */       COLOR_BG_EPIC = COLOR_BG_EPIC_LIGHTUI;
/* 304 */       COLOR_BG_LEGENDARY = COLOR_BG_LEGENDARY_LIGHTUI;
/*     */       
/* 306 */       HTML_COLOR_MAGIC = "<font color = \"#B48200\">";
/* 307 */       HTML_COLOR_RARE = "<font color = \"#00460A\">";
/* 308 */       HTML_COLOR_EPIC = "<font color = \"#002864\">";
/* 309 */       HTML_COLOR_LEGENDARY = "<font color = \"#320064\">";
/* 310 */       HTML_COLOR_QUEST = "<font color = \"#320064\">";
/*     */       
/* 312 */       HTML_COLOR_COMPONENT = "<font color = \"#DA9345\">";
/* 313 */       HTML_COLOR_ENCHANTMENT = "<font color = \"#88BE00\">";
/* 314 */       HTML_COLOR_SKILL = "<font color = \"#598AE5\">";
/* 315 */       HTML_COLOR_ITEM_TYPE = "<font color = \"#C0A560\">";
/* 316 */       HTML_COLOR_ITEMSET = "<font color = \"#4D8489\">";
/* 317 */       HTML_COLOR_STAT = "<font color = \"#404040\">";
/* 318 */       HTML_COLOR_USED_SLOTS = "<font color = \"#000000\">";
/* 319 */       HTML_COLOR_CHAR_GD = "<font color = \"#990000\">";
/* 320 */       HTML_COLOR_CHAR_AOM = "<font color = \"#008800\">";
/* 321 */       HTML_COLOR_CHAR_FG = "<font color = \"#BB4000\">";
/*     */       
/* 323 */       HTML_COLOR_GD_A_AQUA = "<font color = \"#60BC9C\">";
/* 324 */       HTML_COLOR_GD_B_BLUE = "<font color = \"#3298B5\">";
/* 325 */       HTML_COLOR_GD_C_CYAN = "<font color = \"#00C6C6\">";
/* 326 */       HTML_COLOR_GD_D_DARKGRAY = "<font color = \"#404040\">";
/* 327 */       HTML_COLOR_GD_F_FUSHIA = "<font color = \"#F76782\">";
/* 328 */       HTML_COLOR_GD_G_GREEN = "<font color = \"#0DBC47\">";
/* 329 */       HTML_COLOR_GD_I_INDIGO = "<font color = \"#5A039A\">";
/* 330 */       HTML_COLOR_GD_K_KHAKI = "<font color = \"#A8A163\">";
/* 331 */       HTML_COLOR_GD_L_OLIVE = "<font color = \"#7CAD00\">";
/* 332 */       HTML_COLOR_GD_M_MAROON = "<font color = \"#800000\">";
/* 333 */       HTML_COLOR_GD_O_ORANGE = "<font color = \"#D38E43\">";
/* 334 */       HTML_COLOR_GD_P_PURPLE = "<font color = \"#A767C6\">";
/* 335 */       HTML_COLOR_GD_R_RED = "<font color = \"#FF2E05\">";
/* 336 */       HTML_COLOR_GD_S_SILVER = "<font color = \"#787878\">";
/* 337 */       HTML_COLOR_GD_T_TEAL = "<font color = \"#00C6A2\">";
/* 338 */       HTML_COLOR_GD_W_WHITE = "<font color = \"#9A9A9A\">";
/* 339 */       HTML_COLOR_GD_Y_YELLOW = "<font color = \"#B78021\">";
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String replaceTags(String s) {
/* 344 */     if (uiType == UIType.Light) return s;
/*     */     
/* 346 */     String temp = s;
/*     */     
/* 348 */     temp = temp.replaceAll("<font color = \"#60BC9C\">", "<font color = \"#80FFD5\">");
/* 349 */     temp = temp.replaceAll("<font color = \"#3298B5\">", "<font color = \"#39ABCD\">");
/* 350 */     temp = temp.replaceAll("<font color = \"#00C6C6\">", "<font color = \"#00FFFF\">");
/* 351 */     temp = temp.replaceAll("<font color = \"#404040\">", "<font color = \"#1A1A1A\">");
/* 352 */     temp = temp.replaceAll("<font color = \"#F76782\">", "<font color = \"#FF6984\">");
/* 353 */     temp = temp.replaceAll("<font color = \"#0DBC47\">", "<font color = \"#10EB5D\">");
/* 354 */     temp = temp.replaceAll("<font color = \"#5A039A\">", "<font color = \"#5A039A\">");
/* 355 */     temp = temp.replaceAll("<font color = \"#A8A163\">", "<font color = \"#F1E78D\">");
/* 356 */     temp = temp.replaceAll("<font color = \"#7CAD00\">", "<font color = \"#92CC00\">");
/* 357 */     temp = temp.replaceAll("<font color = \"#800000\">", "<font color = \"#800000\">");
/* 358 */     temp = temp.replaceAll("<font color = \"#D38E43\">", "<font color = \"#F3A44D\">");
/* 359 */     temp = temp.replaceAll("<font color = \"#A767C6\">", "<font color = \"#BD9AC7\">");
/* 360 */     temp = temp.replaceAll("<font color = \"#FF2E05\">", "<font color = \"#FF4200\">");
/* 361 */     temp = temp.replaceAll("<font color = \"#787878\">", "<font color = \"#9A9A9A\">");
/* 362 */     temp = temp.replaceAll("<font color = \"#00C6A2\">", "<font color = \"#00FFD2\">");
/* 363 */     temp = temp.replaceAll("<font color = \"#9A9A9A\">", "<font color = \"#FFFFFF\">");
/* 364 */     temp = temp.replaceAll("<font color = \"#B78021\">", "<font color = \"#FFF62C\">");
/*     */     
/* 366 */     return temp;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\util\GDColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */