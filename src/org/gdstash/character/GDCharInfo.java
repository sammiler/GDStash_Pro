/*     */ package org.gdstash.character;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDCharInfo
/*     */ {
/*     */   private static final int VERSION_2 = 2;
/*     */   private static final int VERSION_3 = 3;
/*     */   private static final int VERSION_4 = 4;
/*     */   private static final int VERSION_5 = 5;
/*     */   private static final int BLOCK = 1;
/*     */   private int version;
/*     */   private byte isInMainQuest;
/*     */   private byte hasBeenInGame;
/*     */   private byte difficulty;
/*     */   private byte greatestCampaignDifficulty;
/*     */   private int money;
/*     */   private byte greatestCrucibleDifficulty;
/*     */   private int tributes;
/*     */   private byte compassState;
/*     */   private int lootMode;
/*     */   private byte skillWindowHelp;
/*     */   private byte alternateConfig;
/*     */   private byte alternateConfigEnabled;
/*     */   private String texture;
/*     */   private byte[] lootFilters;
/*     */   private boolean changed;
/*     */   
/*     */   public boolean isInMainQuest() {
/*  47 */     return (this.isInMainQuest == 1);
/*     */   }
/*     */   
/*     */   public byte getGreatestCampaignDifficulty() {
/*  51 */     return this.greatestCampaignDifficulty;
/*     */   }
/*     */   
/*     */   public byte getGreatestCrucibleDifficulty() {
/*  55 */     return this.greatestCrucibleDifficulty;
/*     */   }
/*     */   
/*     */   public int getMoney() {
/*  59 */     return this.money;
/*     */   }
/*     */   
/*     */   public int getTributes() {
/*  63 */     return this.tributes;
/*     */   }
/*     */   
/*     */   public boolean hasChanged() {
/*  67 */     return this.changed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInMainQuest(boolean isInMainQuest) {
/*  75 */     if (isInMainQuest) {
/*  76 */       this.isInMainQuest = 1;
/*     */     } else {
/*  78 */       this.isInMainQuest = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setGreatestCampaignDifficulty(byte greatestCampaignDifficulty) {
/*  83 */     this.greatestCampaignDifficulty = greatestCampaignDifficulty;
/*     */     
/*  85 */     if (greatestCampaignDifficulty < 0) {
/*  86 */       this.greatestCampaignDifficulty = 0;
/*     */     }
/*     */     
/*  89 */     if (greatestCampaignDifficulty > 2) {
/*  90 */       this.greatestCampaignDifficulty = 2;
/*     */     }
/*     */     
/*  93 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setGreatestCrucibleDifficulty(byte greatestCrucibleDifficulty) {
/*  97 */     this.greatestCrucibleDifficulty = greatestCrucibleDifficulty;
/*     */     
/*  99 */     if (greatestCrucibleDifficulty < 0) {
/* 100 */       this.greatestCrucibleDifficulty = 0;
/*     */     }
/*     */     
/* 103 */     if (greatestCrucibleDifficulty > 2) {
/* 104 */       this.greatestCrucibleDifficulty = 2;
/*     */     }
/*     */     
/* 107 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setMoney(int money) {
/* 111 */     this.money = money;
/*     */     
/* 113 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setTributes(int tributes) {
/* 117 */     this.tributes = tributes;
/*     */     
/* 119 */     this.changed = true;
/*     */   }
/*     */   
/*     */   public void setTexture(String texture) {
/* 123 */     this.texture = texture;
/*     */     
/* 125 */     this.changed = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read() throws IOException {
/* 133 */     int val = 0;
/*     */     
/* 135 */     GDReader.Block block = new GDReader.Block();
/*     */     
/* 137 */     val = GDReader.readBlockStart(block);
/* 138 */     if (val != 1) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/* 140 */     this.version = GDReader.readEncInt(true);
/* 141 */     if (this.version != 3 && this.version != 4 && this.version != 5)
/*     */     {
/*     */       
/* 144 */       throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     }
/*     */     
/* 147 */     this.isInMainQuest = GDReader.readEncByte();
/* 148 */     this.hasBeenInGame = GDReader.readEncByte();
/* 149 */     this.difficulty = GDReader.readEncByte();
/* 150 */     this.greatestCampaignDifficulty = GDReader.readEncByte();
/* 151 */     this.money = GDReader.readEncInt(true);
/*     */     
/* 153 */     if (this.version >= 4) {
/* 154 */       this.greatestCrucibleDifficulty = GDReader.readEncByte();
/* 155 */       this.tributes = GDReader.readEncInt(true);
/*     */     } 
/*     */     
/* 158 */     this.compassState = GDReader.readEncByte();
/*     */     
/* 160 */     if (this.version >= 2 && this.version <= 4)
/*     */     {
/* 162 */       this.lootMode = GDReader.readEncInt(true);
/*     */     }
/*     */     
/* 165 */     this.skillWindowHelp = GDReader.readEncByte();
/* 166 */     this.alternateConfig = GDReader.readEncByte();
/* 167 */     this.alternateConfigEnabled = GDReader.readEncByte();
/* 168 */     this.texture = GDReader.readEncString();
/*     */     
/* 170 */     if (this.version >= 5) {
/* 171 */       int size = GDReader.readEncInt(true);
/*     */       
/* 173 */       this.lootFilters = new byte[size];
/*     */       int i;
/* 175 */       for (i = 0; i < this.lootFilters.length; i++) {
/* 176 */         this.lootFilters[i] = GDReader.readEncByte();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 181 */     GDReader.readBlockEnd(block);
/*     */     
/* 183 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/* 187 */     GDReader.Block block = new GDReader.Block();
/* 188 */     GDWriter.writeBlockStart(block, 1);
/*     */     
/* 190 */     GDWriter.writeInt(this.version);
/*     */     
/* 192 */     GDWriter.writeByte(this.isInMainQuest);
/* 193 */     GDWriter.writeByte(this.hasBeenInGame);
/* 194 */     GDWriter.writeByte(this.difficulty);
/* 195 */     GDWriter.writeByte(this.greatestCampaignDifficulty);
/* 196 */     GDWriter.writeInt(this.money);
/*     */     
/* 198 */     if (this.version >= 4) {
/* 199 */       GDWriter.writeByte(this.greatestCrucibleDifficulty);
/* 200 */       GDWriter.writeInt(this.tributes);
/*     */     } 
/*     */     
/* 203 */     GDWriter.writeByte(this.compassState);
/*     */     
/* 205 */     if (this.version >= 2 && this.version <= 4)
/*     */     {
/* 207 */       GDWriter.writeInt(this.lootMode);
/*     */     }
/*     */     
/* 210 */     GDWriter.writeByte(this.skillWindowHelp);
/* 211 */     GDWriter.writeByte(this.alternateConfig);
/* 212 */     GDWriter.writeByte(this.alternateConfigEnabled);
/* 213 */     GDWriter.writeString(this.texture);
/*     */     
/* 215 */     if (this.version >= 5) {
/* 216 */       GDWriter.writeInt(this.lootFilters.length);
/*     */       
/* 218 */       for (int i = 0; i < this.lootFilters.length; i++) {
/* 219 */         GDWriter.writeByte(this.lootFilters[i]);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 224 */     GDWriter.writeBlockEnd(block);
/*     */     
/* 226 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/* 230 */     int size = 0;
/*     */     
/* 232 */     size += 4;
/* 233 */     size += 4;
/* 234 */     size += 4;
/*     */     
/* 236 */     size++;
/* 237 */     size++;
/* 238 */     size++;
/* 239 */     size++;
/* 240 */     size += 4;
/*     */     
/* 242 */     if (this.version >= 4) {
/* 243 */       size++;
/* 244 */       size += 4;
/*     */     } 
/*     */     
/* 247 */     size++;
/*     */     
/* 249 */     if (this.version >= 2 && this.version <= 4)
/*     */     {
/* 251 */       size += 4;
/*     */     }
/*     */     
/* 254 */     size++;
/* 255 */     size++;
/* 256 */     size++;
/*     */     
/* 258 */     size += 4;
/* 259 */     if (this.texture != null) size += this.texture.length();
/*     */     
/* 261 */     if (this.version >= 5) {
/* 262 */       size += 4;
/* 263 */       size += this.lootFilters.length;
/*     */     } 
/*     */     
/* 266 */     size += 4;
/*     */     
/* 268 */     return size;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\character\GDCharInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */