/*      */ package org.gdstash.file;
/*      */ 
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.NumberFormat;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.jpountz.lz4.LZ4Factory;
/*      */ import net.jpountz.lz4.LZ4FastDecompressor;
/*      */ import org.gdstash.db.DBSkillBonus;
/*      */ import org.gdstash.db.DBSkillModifier;
/*      */ import org.gdstash.db.DBStat;
/*      */ import org.gdstash.db.GDDBData;
/*      */ import org.gdstash.ui.GDStashFrame;
/*      */ import org.gdstash.util.GDConstants;
/*      */ import org.gdstash.util.GDMsgFormatter;
/*      */ import org.gdstash.util.GDMsgLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ARZDecompress
/*      */ {
/*      */   public static final short REC_VALUE_INT = 0;
/*      */   public static final short REC_VALUE_FLOAT = 1;
/*      */   public static final short REC_VALUE_STRING = 2;
/*      */   public static final short REC_VALUE_BOOL = 3;
/*      */   private String filename;
/*      */   private ARZHeader header;
/*      */   private ARZString[] strings;
/*      */   private ARZRecord[] records;
/*      */   
/*      */   public ARZDecompress(String filename) {
/*   46 */     this.filename = filename;
/*      */   }
/*      */   
/*      */   public static boolean is64BitVM() {
/*   50 */     String arch = System.getProperty("os.arch");
/*      */     
/*   52 */     return arch.contains("64");
/*      */   }
/*      */   
/*      */   public static GDBuffer getFileBuffer(File file) {
/*   56 */     GDBuffer buffer = null;
/*      */     
/*   58 */     if (is64BitVM()) {
/*   59 */       buffer = new GDMappedByteBuffer(file);
/*      */     } else {
/*   61 */       buffer = new GDByteBuffer(file);
/*      */     } 
/*      */     
/*   64 */     return buffer;
/*      */   }
/*      */   
/*      */   public void clear() {
/*   68 */     this.header = null;
/*   69 */     this.strings = null;
/*   70 */     this.records = null;
/*      */   }
/*      */   
/*      */   public void decompress() {
/*   74 */     boolean extractItems = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*   83 */       File file = new File(this.filename);
/*      */       
/*   85 */       if (!file.exists()) {
/*   86 */         Object[] args = { this.filename };
/*   87 */         String s = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*      */         
/*   89 */         throw new FileNotFoundException(s);
/*      */       } 
/*      */       
/*   92 */       if (!file.isFile()) {
/*   93 */         Object[] args = { this.filename };
/*   94 */         String s = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*      */         
/*   96 */         throw new FileNotFoundException(s);
/*      */       } 
/*      */       
/*   99 */       if (!file.canRead()) {
/*  100 */         Object[] args = { this.filename };
/*  101 */         String s = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_CANNOT_READ", args);
/*      */         
/*  103 */         throw new IOException(s);
/*      */       } 
/*      */       
/*  106 */       GDBuffer buffer = getFileBuffer(file);
/*      */       try {
/*  108 */         this.header = getHeader(buffer);
/*  109 */         this.strings = getStrings(buffer);
/*  110 */         this.records = getRecords(buffer);
/*      */         
/*  112 */         extractRecords(buffer, extractItems);
/*      */       }
/*  114 */       catch (IOException ex) {
/*  115 */         throw ex;
/*      */       } finally {
/*      */         
/*  118 */         buffer.close();
/*      */         
/*  120 */         buffer = null;
/*      */       }
/*      */     
/*  123 */     } catch (Exception ex) {
/*  124 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  135 */     resolveAttrib();
/*      */     
/*  137 */     GDStashFrame.listToLog();
/*      */     
/*  139 */     if (GDMsgLogger.severeErrorsInLog()) {
/*  140 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_DB_EXTRACT_FAIL"));
/*      */     }
/*      */   }
/*      */   
/*      */   private static ARZHeader getHeader(GDBuffer buffer) throws IOException, GDParseException {
/*  145 */     ARZHeader header = new ARZHeader();
/*      */     
/*  147 */     header.unknown = buffer.getShort();
/*  148 */     header.version = buffer.getShort();
/*  149 */     header.rec_start = buffer.getInt();
/*  150 */     header.rec_size = buffer.getInt();
/*  151 */     header.rec_num = buffer.getInt();
/*  152 */     header.str_start = buffer.getInt();
/*  153 */     header.str_size = buffer.getInt();
/*      */     
/*  155 */     if (header.unknown != 2 || header.version != 3) {
/*  156 */       throw new GDParseException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"), 0L);
/*      */     }
/*      */     
/*  159 */     return header;
/*      */   }
/*      */ 
/*      */   
/*      */   private ARZString[] getStrings(GDBuffer buffer) throws IOException {
/*  164 */     buffer.setPosition(this.header.str_start);
/*  165 */     int num = buffer.getInt();
/*      */     
/*  167 */     ARZString[] strings = new ARZString[num];
/*      */     int i;
/*  169 */     for (i = 0; i < strings.length; i++) {
/*  170 */       ARZString str = buffer.getARZString();
/*      */       
/*  172 */       strings[i] = str;
/*      */     } 
/*      */     
/*  175 */     return strings;
/*      */   }
/*      */   
/*      */   private ARZRecord[] getRecords(GDBuffer buffer) throws IOException {
/*  179 */     ARZRecord[] records = new ARZRecord[this.header.rec_num];
/*      */     
/*  181 */     buffer.setPosition(this.header.rec_start);
/*  182 */     for (int i = 0; i < records.length; i++) {
/*      */       try {
/*  184 */         ARZRecord rec = new ARZRecord();
/*      */         
/*  186 */         rec.strID = buffer.getInt();
/*  187 */         rec.len_str = buffer.getInt();
/*  188 */         rec.str = buffer.getString(rec.len_str);
/*  189 */         rec.offset = buffer.getInt();
/*  190 */         rec.len_comp = buffer.getInt();
/*  191 */         rec.len_decomp = buffer.getInt();
/*  192 */         rec.filedata = buffer.getBytes8();
/*      */         
/*  194 */         records[i] = rec;
/*      */       }
/*  196 */       catch (Exception ex) {
/*  197 */         GDMsgLogger.addError(ex);
/*      */         
/*  199 */         records[i] = null;
/*      */       } 
/*      */     } 
/*      */     
/*  203 */     return records;
/*      */   }
/*      */   
/*      */   private void extractRecords(GDBuffer buffer, boolean extractItems) {
/*  207 */     NumberFormat formatter = new DecimalFormat("#0.000000");
/*  208 */     LZ4Factory factory = LZ4Factory.fastestInstance();
/*  209 */     LZ4FastDecompressor decomp = factory.fastDecompressor();
/*      */     
/*  211 */     String attrib = null;
/*  212 */     String value = null;
/*      */     int i;
/*  214 */     for (i = 0; i < this.records.length; i++) {
/*      */       try {
/*  216 */         if (this.records[i] != null) {
/*      */           
/*  218 */           this.records[i].setFileName((this.strings[(this.records[i]).strID]).str);
/*  219 */           if ((this.records[i]).error) {
/*  220 */             this.records[i] = null;
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*  225 */           else if (!ARZRecord.processDir(this.records[i].getFileName())) {
/*  226 */             this.records[i] = null;
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  231 */             (this.records[i]).storeInDB = true;
/*      */             
/*  233 */             byte[] bComp = buffer.getByteArray(((this.records[i]).offset + 24), (this.records[i]).len_comp);
/*      */ 
/*      */ 
/*      */             
/*  237 */             byte[] bDecomp = new byte[(this.records[i]).len_decomp];
/*      */             
/*  239 */             decomp.decompress(bComp, bDecomp);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  250 */             int offset = 0;
/*  251 */             value = "";
/*      */             
/*  253 */             while (offset < bDecomp.length) {
/*  254 */               int typeOffset = offset;
/*  255 */               short varType = GDReader.getUShort(bDecomp, offset);
/*  256 */               offset += 2;
/*      */               
/*  258 */               short count = GDReader.getUShort(bDecomp, offset);
/*  259 */               offset += 2;
/*      */               
/*  261 */               int strIdx = GDReader.getUInt(bDecomp, offset);
/*  262 */               offset += 4;
/*      */               int j;
/*  264 */               for (j = 0; j < count; j++) {
/*  265 */                 int valInt; float valFloat; int valStrIdx; boolean valBool; Object[] args; String msg; switch (varType) {
/*      */                   case 0:
/*  267 */                     valInt = GDReader.getInt(bDecomp, offset);
/*  268 */                     value = String.valueOf(valInt);
/*      */                     break;
/*      */                   
/*      */                   case 1:
/*  272 */                     valFloat = GDReader.getFloat(bDecomp, offset);
/*  273 */                     value = formatter.format(valFloat);
/*      */                     break;
/*      */                   
/*      */                   case 2:
/*  277 */                     valStrIdx = GDReader.getUInt(bDecomp, offset);
/*  278 */                     value = (this.strings[valStrIdx]).str;
/*      */                     break;
/*      */                   
/*      */                   case 3:
/*  282 */                     valBool = GDReader.getBool(bDecomp, offset);
/*  283 */                     if (valBool) { value = "1"; break; }  value = "0";
/*      */                     break;
/*      */                   
/*      */                   default:
/*  287 */                     args = new Object[] { this.records[i].getFileName(), (this.strings[strIdx]).str };
/*  288 */                     msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_UNKNOWN_VAR_TYPE", args);
/*      */                     
/*  290 */                     throw new GDParseException(msg, typeOffset);
/*      */                 } 
/*      */                 
/*  293 */                 offset += 4;
/*      */                 
/*  295 */                 attrib = (this.strings[strIdx]).str;
/*      */                 
/*  297 */                 if (attrib.equals("templateName")) {
/*  298 */                   if (!ARZRecord.processTemplate(value, extractItems)) {
/*  299 */                     this.records[i] = null;
/*      */                     
/*      */                     break;
/*      */                   } 
/*  303 */                   if (!ARZRecord.processFile(this.records[i].getFileName(), value)) {
/*  304 */                     this.records[i] = null;
/*      */                     
/*      */                     break;
/*      */                   } 
/*      */                 } 
/*      */                 
/*  310 */                 fillAttrib(attrib, i, value, j);
/*      */               } 
/*      */               
/*  313 */               if (this.records[i] == null)
/*      */                 break; 
/*      */             } 
/*  316 */             if (this.records[i] != null)
/*  317 */               if (this.records[i].getTemplate() == null) {
/*  318 */                 this.records[i] = null;
/*      */ 
/*      */ 
/*      */               
/*      */               }
/*  323 */               else if (ARZFormulaSetPool.isFormula(this.records[i].getTemplate())) {
/*      */ 
/*      */                 
/*  326 */                 ARZFormulaSetPool.storeFormulaSet(this.records[i]);
/*      */               }  
/*      */           } 
/*      */         } 
/*  330 */       } catch (Exception ex) {
/*  331 */         Object[] args = { this.records[i].getFileName() };
/*  332 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_IN_ENTRY", args);
/*  333 */         GDMsgLogger.addError(msg);
/*      */         
/*  335 */         GDMsgLogger.addError(ex);
/*      */         
/*  337 */         this.records[i] = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int parseInt(String s) {
/*  343 */     int pos = s.indexOf(".");
/*  344 */     if (pos == -1) pos = s.indexOf(",");
/*      */     
/*  346 */     int val = 0;
/*      */     
/*  348 */     if (pos == -1) {
/*      */       try {
/*  350 */         val = Integer.parseInt(s);
/*      */       }
/*  352 */       catch (NumberFormatException ex) {
/*  353 */         val = -1;
/*      */       } 
/*      */     } else {
/*  356 */       val = (int)parseFloat(s);
/*      */     } 
/*      */     
/*  359 */     return val;
/*      */   }
/*      */   
/*      */   private static float parseFloat(String s) {
/*  363 */     int pos = s.indexOf(",");
/*  364 */     if (pos != -1) {
/*  365 */       s = s.substring(0, pos) + "." + s.substring(pos + 1);
/*      */     }
/*      */     
/*  368 */     float val = 0.0F;
/*      */     
/*      */     try {
/*  371 */       val = Float.parseFloat(s);
/*      */     }
/*  373 */     catch (NumberFormatException ex) {
/*  374 */       val = -1.0F;
/*      */     } 
/*      */     
/*  377 */     return val;
/*      */   }
/*      */ 
/*      */   
/*      */   private void fillAttrib(String attrib, int pos, String value, int index) throws GDParseException {
/*  382 */     if (attrib.equals("templateName")) {
/*  383 */       this.records[pos].setTemplate(value);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  388 */     if (attrib.equals("Class")) {
/*  389 */       this.records[pos].setRecordClass(value);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  394 */     if (attrib.equals("FileDescription")) {
/*  395 */       this.records[pos].setFileDescription(value);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  401 */     if (attrib.equals("conversionInType")) {
/*  402 */       this.records[pos].setConversionIn(value);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  407 */     if (attrib.equals("conversionInType2")) {
/*  408 */       this.records[pos].setConversionIn2(value);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  413 */     if (attrib.equals("conversionOutType")) {
/*  414 */       this.records[pos].setConversionOut(value);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  419 */     if (attrib.equals("conversionOutType2")) {
/*  420 */       this.records[pos].setConversionOut2(value);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  425 */     if (attrib.equals("dlcRequirement")) {
/*  426 */       this.records[pos].setDLCRequirement(value);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  431 */     if (attrib.equals("itemClassification")) {
/*  432 */       this.records[pos].setRarity(value);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  437 */     if (!this.records[pos].isItemSet())
/*      */     {
/*      */       
/*  440 */       if (attrib.equals("itemSkillName")) {
/*  441 */         this.records[pos].setItemSkillID(value);
/*      */         
/*      */         return;
/*      */       } 
/*      */     }
/*      */     
/*  447 */     if (attrib.equals("itemSkillLevelEq")) {
/*  448 */       int level = parseInt(value);
/*      */       
/*  450 */       if (level == -1) {
/*      */         
/*  452 */         level = 1;
/*      */         
/*  454 */         this.records[pos].setItemSkillLevelFormula(value);
/*      */       } 
/*      */       
/*  457 */       this.records[pos].setItemSkillLevel(level);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  462 */     if (attrib.equals("itemSkillAutoController")) {
/*  463 */       this.records[pos].setSkillControllerID(value);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  468 */     if (attrib.equals("levelRequirement")) {
/*  469 */       int level = parseInt(value);
/*      */       
/*  471 */       this.records[pos].setRequiredLevel(level);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  476 */     if (attrib.equals("offensiveGlobalChance")) {
/*  477 */       int chance = parseInt(value);
/*      */       
/*  479 */       this.records[pos].setOffensiveChance(chance);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  484 */     if (attrib.equals("petBonusName")) {
/*  485 */       this.records[pos].setPetBonusID(value);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  490 */     if (attrib.equals("retaliationGlobalChance")) {
/*  491 */       int chance = parseInt(value);
/*      */       
/*  493 */       this.records[pos].setRetaliationChance(chance);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  498 */     if (fillAttribSkillModifier(attrib, pos, value, index)) {
/*      */       return;
/*      */     }
/*      */     
/*  502 */     boolean processed = false;
/*      */ 
/*      */     
/*  505 */     if (this.records[pos].isGameEngine()) {
/*      */       
/*  507 */       processed = fillEngineGame(attrib, pos, value, index);
/*  508 */       if (processed) {
/*      */         return;
/*      */       }
/*      */     } 
/*  512 */     if (this.records[pos].isFaction()) {
/*      */       
/*  514 */       processed = fillFaction(attrib, pos, value);
/*  515 */       if (processed)
/*      */         return; 
/*      */     } 
/*  518 */     if (this.records[pos].isMerchant() || this.records[pos]
/*  519 */       .isMerchantTableSet() || this.records[pos]
/*  520 */       .isMerchantTable()) {
/*      */       
/*  522 */       processed = fillMerchant(attrib, pos, value);
/*  523 */       if (processed) {
/*      */         return;
/*      */       }
/*      */     } 
/*  527 */     if (this.records[pos].isPlayerEngine()) {
/*      */       
/*  529 */       processed = fillEnginePlayer(attrib, pos, value, index);
/*  530 */       if (processed)
/*      */         return; 
/*      */     } 
/*  533 */     if (this.records[pos].isConstellation() || this.records[pos]
/*  534 */       .isSkillButton()) {
/*      */       
/*  536 */       processed = fillAttribConstellation(attrib, pos, value);
/*  537 */       if (processed)
/*      */         return; 
/*      */     } 
/*  540 */     if (this.records[pos].isBitmap()) {
/*      */       
/*  542 */       processed = fillAttribBitmap(attrib, pos, value);
/*  543 */       if (processed)
/*      */         return; 
/*      */     } 
/*  546 */     if (this.records[pos].isClassTable()) {
/*  547 */       processed = fillAttribClassTable(attrib, pos, value);
/*  548 */       if (processed)
/*      */         return; 
/*      */     } 
/*  551 */     if (this.records[pos].isSkillMaster()) {
/*  552 */       processed = fillAttribSkillMaster(attrib, pos, value);
/*  553 */       if (processed)
/*      */         return; 
/*      */     } 
/*  556 */     if (this.records[pos].isShrine()) {
/*      */       
/*  558 */       processed = fillAttribShrine(attrib, pos, value);
/*  559 */       if (processed)
/*      */         return; 
/*      */     } 
/*  562 */     if (this.records[pos].isSkillTree()) {
/*  563 */       processed = fillAttribSkillTree(attrib, pos, value);
/*  564 */       if (processed)
/*      */         return; 
/*      */     } 
/*  567 */     if (this.records[pos].isSkill()) {
/*      */       
/*  569 */       processed = fillAttribSkill(attrib, pos, value, index);
/*  570 */       if (processed)
/*      */         return; 
/*      */     } 
/*  573 */     if (this.records[pos].isPet()) {
/*      */       
/*  575 */       processed = fillAttribPet(attrib, pos, value, index);
/*  576 */       if (processed)
/*      */         return; 
/*      */     } 
/*  579 */     if (this.records[pos].isPetBio()) {
/*      */       
/*  581 */       processed = fillAttribPetBio(attrib, pos, value, index);
/*  582 */       if (processed)
/*      */         return; 
/*      */     } 
/*  585 */     if (this.records[pos].isController()) {
/*      */       
/*  587 */       processed = fillAttribController(attrib, pos, value);
/*  588 */       if (processed) {
/*      */         return;
/*      */       }
/*      */     } 
/*  592 */     if (this.records[pos].isAffix()) {
/*      */       
/*  594 */       processed = fillAttribAffix(attrib, pos, value);
/*  595 */       if (processed)
/*      */         return; 
/*      */     } 
/*  598 */     if (this.records[pos].isAffixSet()) {
/*      */       
/*  600 */       processed = fillAttribAffixSet(attrib, pos, value);
/*  601 */       if (processed) {
/*      */         return;
/*      */       }
/*      */     } 
/*  605 */     if (this.records[pos].isItemSet()) {
/*      */       
/*  607 */       processed = fillAttribItemSet(attrib, pos, value);
/*  608 */       if (processed) {
/*      */         return;
/*      */       }
/*      */     } 
/*  612 */     if (this.records[pos].isLootTable()) {
/*      */       
/*  614 */       processed = fillAttribLootTable(attrib, pos, value);
/*  615 */       if (processed)
/*      */         return; 
/*      */     } 
/*  618 */     if (this.records[pos].isLootTableSet()) {
/*      */       
/*  620 */       processed = fillAttribLootTableSet(attrib, pos, value);
/*  621 */       if (processed) {
/*      */         return;
/*      */       }
/*      */     } 
/*  625 */     if (this.records[pos].isFormulaSet()) {
/*      */       
/*  627 */       processed = fillAttribFormula(attrib, pos, value);
/*  628 */       if (processed)
/*      */         return; 
/*      */     } 
/*  631 */     processed = fillAttribItem(attrib, pos, value);
/*  632 */     if (processed)
/*      */       return; 
/*  634 */     processed = fillAttribStat(attrib, pos, value, index);
/*  635 */     if (processed)
/*      */       return; 
/*  637 */     processed = fillAttribSkillBonus(attrib, pos, value, index);
/*  638 */     if (processed) {
/*      */       return;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean fillAttribAffix(String attrib, int pos, String value) {
/*  645 */     if (attrib.equals("lootRandomizerCost")) {
/*  646 */       int iVal = 0;
/*      */       try {
/*  648 */         iVal = Integer.parseInt(value);
/*      */       }
/*  650 */       catch (NumberFormatException ex) {
/*  651 */         iVal = 0;
/*      */         
/*  653 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/*  654 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/*  656 */         GDMsgLogger.addError(msg);
/*      */       } 
/*      */       
/*  659 */       this.records[pos].setLootRandomCost(iVal);
/*      */       
/*  661 */       return true;
/*      */     } 
/*      */     
/*  664 */     if (attrib.equals("lootRandomizerJitter")) {
/*  665 */       int iVal = 0;
/*      */       try {
/*  667 */         iVal = (int)parseFloat(value);
/*      */       }
/*  669 */       catch (NumberFormatException ex) {
/*  670 */         iVal = 0;
/*      */         
/*  672 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/*  673 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/*  675 */         GDMsgLogger.addError(msg);
/*      */       } 
/*  677 */       this.records[pos].setRNGPercent(iVal);
/*      */       
/*  679 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  683 */     if (attrib.equals("lootRandomizerName")) {
/*  684 */       this.records[pos].setLootRandomName(value);
/*      */       
/*  686 */       return true;
/*      */     } 
/*      */     
/*  689 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribAffixSet(String attrib, int pos, String value) {
/*  693 */     if (attrib.startsWith("randomizerLevelMax")) {
/*      */       
/*  695 */       String s = attrib.substring(18);
/*  696 */       int i = parseInt(s);
/*      */       
/*  698 */       int level = parseInt(value);
/*      */       
/*  700 */       this.records[pos].addRandomizerMaxLevelEntry(i, level);
/*      */       
/*  702 */       return true;
/*      */     } 
/*      */     
/*  705 */     if (attrib.startsWith("randomizerLevelMin")) {
/*      */       
/*  707 */       String s = attrib.substring(18);
/*  708 */       int i = parseInt(s);
/*      */       
/*  710 */       int level = parseInt(value);
/*      */       
/*  712 */       this.records[pos].addRandomizerMinLevelEntry(i, level);
/*      */       
/*  714 */       return true;
/*      */     } 
/*      */     
/*  717 */     if (attrib.startsWith("randomizerName")) {
/*      */       
/*  719 */       String s = attrib.substring(14);
/*  720 */       int i = parseInt(s);
/*      */       
/*  722 */       this.records[pos].addRandomizerAffixIDEntry(i, value);
/*      */       
/*  724 */       return true;
/*      */     } 
/*      */     
/*  727 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribItem(String attrib, int pos, String value) {
/*  731 */     if (attrib.equals("amulet")) {
/*  732 */       this.records[pos].setSlotAmulet(value.equals("1"));
/*      */       
/*  734 */       return true;
/*      */     } 
/*      */     
/*  737 */     if (attrib.equals("armorClassification")) {
/*  738 */       this.records[pos].setArmorClass(value);
/*      */       
/*  740 */       return true;
/*      */     } 
/*      */     
/*  743 */     if (attrib.equals("artifactClassification")) {
/*  744 */       this.records[pos].setArtifactClass(value);
/*      */       
/*  746 */       return true;
/*      */     } 
/*      */     
/*  749 */     if (attrib.equals("artifactName") || attrib
/*  750 */       .equals("forcedRandomArtifactName")) {
/*      */       
/*  752 */       if (value == null) return true; 
/*  753 */       if (value.isEmpty()) return true; 
/*  754 */       if (value.contains("loottable")) return true;
/*      */       
/*  756 */       this.records[pos].setArtifactID(value);
/*      */       
/*  758 */       return true;
/*      */     } 
/*      */     
/*  761 */     if (attrib.equals("attributeScalePercent")) {
/*  762 */       int iVal = 0;
/*      */       try {
/*  764 */         iVal = (int)parseFloat(value);
/*      */       }
/*  766 */       catch (NumberFormatException ex) {
/*  767 */         iVal = 0;
/*      */         
/*  769 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/*  770 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/*  772 */         GDMsgLogger.addError(msg);
/*      */       } 
/*  774 */       this.records[pos].setRNGPercent(iVal);
/*      */       
/*  776 */       return true;
/*      */     } 
/*      */     
/*  779 */     if (!this.records[pos].isItemSet())
/*      */     {
/*      */       
/*  782 */       if (attrib.equals("augmentAllLevel")) {
/*  783 */         int iVal = parseInt(value);
/*  784 */         this.records[pos].setPlusAllSkills(iVal);
/*      */         
/*  786 */         return true;
/*      */       } 
/*      */     }
/*      */     
/*  790 */     if (attrib.equals("axe")) {
/*  791 */       this.records[pos].setSlotAxe1H(value.equals("1"));
/*      */       
/*  793 */       return true;
/*      */     } 
/*      */     
/*  796 */     if (attrib.equals("axe2h")) {
/*  797 */       this.records[pos].setSlotAxe2H(value.equals("1"));
/*      */       
/*  799 */       return true;
/*      */     } 
/*      */     
/*  802 */     if (attrib.equals("baseTexture")) {
/*  803 */       this.records[pos].setBaseTextureID(value);
/*      */       
/*  805 */       return true;
/*      */     } 
/*      */     
/*  808 */     if (attrib.equals("baseTextures")) {
/*  809 */       this.records[pos].addBaseTextureID(value);
/*      */       
/*  811 */       return true;
/*      */     } 
/*      */     
/*  814 */     if (attrib.equals("bumpTexture")) {
/*  815 */       this.records[pos].setBumpTextureID(value);
/*      */       
/*  817 */       return true;
/*      */     } 
/*      */     
/*  820 */     if (attrib.equals("bumpTextures")) {
/*  821 */       this.records[pos].addBumpTextureID(value);
/*      */       
/*  823 */       return true;
/*      */     } 
/*      */     
/*  826 */     if (attrib.equals("glowTexture")) {
/*  827 */       this.records[pos].setGlowTextureID(value);
/*      */       
/*  829 */       return true;
/*      */     } 
/*      */     
/*  832 */     if (attrib.equals("glowTextures")) {
/*  833 */       this.records[pos].addGlowTextureID(value);
/*      */       
/*  835 */       return true;
/*      */     } 
/*      */     
/*  838 */     if (attrib.equals("shader")) {
/*  839 */       this.records[pos].setShaderID(value);
/*      */       
/*  841 */       return true;
/*      */     } 
/*      */     
/*  844 */     if (attrib.equals("bitmap") || attrib
/*  845 */       .equals("artifactBitmap") || attrib
/*  846 */       .equals("artifactFormulaBitmapName") || attrib
/*  847 */       .equals("noteBitmap") || attrib
/*  848 */       .equals("relicBitmap") || attrib
/*  849 */       .equals("emptyBitmap") || attrib
/*  850 */       .equals("fullBitmap")) {
/*  851 */       this.records[pos].setBitmapID(value);
/*      */       
/*  853 */       return true;
/*      */     } 
/*      */     
/*  856 */     if (attrib.equals("bonusTableName")) {
/*  857 */       this.records[pos].setBonusAffixSetID(value);
/*      */     }
/*      */     
/*  860 */     if (attrib.equals("cannotPickUp")) {
/*  861 */       this.records[pos].setCannotPickup(value.equals("1"));
/*      */       
/*  863 */       return true;
/*      */     } 
/*      */     
/*  866 */     if (attrib.equals("chest")) {
/*  867 */       this.records[pos].setSlotChest(value.equals("1"));
/*      */       
/*  869 */       return true;
/*      */     } 
/*      */     
/*  872 */     if (attrib.equals("completedRelicLevel")) {
/*  873 */       int iVal = parseInt(value);
/*  874 */       this.records[pos].setComponentPieces(iVal);
/*      */       
/*  876 */       return true;
/*      */     } 
/*      */     
/*  879 */     if (attrib.equals("dagger")) {
/*  880 */       this.records[pos].setSlotDagger1H(value.equals("1"));
/*      */       
/*  882 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  893 */     if (attrib.equals("feet")) {
/*  894 */       this.records[pos].setSlotFeet(value.equals("1"));
/*      */       
/*  896 */       return true;
/*      */     } 
/*      */     
/*  899 */     if (attrib.equals("hands")) {
/*  900 */       this.records[pos].setSlotHands(value.equals("1"));
/*      */       
/*  902 */       return true;
/*      */     } 
/*      */     
/*  905 */     if (attrib.equals("head")) {
/*  906 */       this.records[pos].setSlotHead(value.equals("1"));
/*      */       
/*  908 */       return true;
/*      */     } 
/*      */     
/*  911 */     if (attrib.equals("hidePrefixName")) {
/*  912 */       this.records[pos].setHidePrefix(value.equals("1"));
/*      */       
/*  914 */       return true;
/*      */     } 
/*      */     
/*  917 */     if (attrib.equals("hideSuffixName")) {
/*  918 */       this.records[pos].setHideSuffix(value.equals("1"));
/*      */       
/*  920 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  931 */     if (attrib.equals("itemCostName")) {
/*  932 */       this.records[pos].setCostFormulaSetID(value);
/*      */       
/*  934 */       return true;
/*      */     } 
/*      */     
/*  937 */     if (attrib.equals("itemLevel")) {
/*  938 */       int iVal = parseInt(value);
/*  939 */       this.records[pos].setItemLevel(iVal);
/*      */       
/*  941 */       return true;
/*      */     } 
/*      */     
/*  944 */     if (attrib.equals("itemNameTag") || attrib
/*  945 */       .equals("description")) {
/*  946 */       if (value != null && !value.equals("")) {
/*  947 */         this.records[pos].setItemNameTag(value);
/*      */       }
/*      */       
/*  950 */       return true;
/*      */     } 
/*      */     
/*  953 */     if (attrib.equals("itemQualityTag")) {
/*  954 */       this.records[pos].setQualityTag(value);
/*      */       
/*  956 */       return true;
/*      */     } 
/*      */     
/*  959 */     if (attrib.equals("itemSetName")) {
/*  960 */       this.records[pos].setItemSetID(value);
/*      */       
/*  962 */       return true;
/*      */     } 
/*      */     
/*  965 */     if (attrib.equals("itemStyleTag")) {
/*  966 */       this.records[pos].setStyleTag(value);
/*      */       
/*  968 */       return true;
/*      */     } 
/*      */     
/*  971 */     if (attrib.equals("itemText")) {
/*  972 */       this.records[pos].setItemDescriptionTag(value);
/*      */       
/*  974 */       return true;
/*      */     } 
/*      */     
/*  977 */     if (attrib.equals("legs")) {
/*  978 */       this.records[pos].setSlotLegs(value.equals("1"));
/*      */       
/*  980 */       return true;
/*      */     } 
/*      */     
/*  983 */     if (attrib.equals("mace")) {
/*  984 */       this.records[pos].setSlotMace1H(value.equals("1"));
/*      */       
/*  986 */       return true;
/*      */     } 
/*      */     
/*  989 */     if (attrib.equals("mace2h")) {
/*  990 */       this.records[pos].setSlotMace2H(value.equals("1"));
/*      */       
/*  992 */       return true;
/*      */     } 
/*      */     
/*  995 */     if (attrib.equals("maxStackSize")) {
/*  996 */       int iVal = parseInt(value);
/*  997 */       this.records[pos].setMaxStackSize(iVal);
/*      */       
/*  999 */       return true;
/*      */     } 
/*      */     
/* 1002 */     if (attrib.equals("medal")) {
/* 1003 */       this.records[pos].setSlotMedal(value.equals("1"));
/*      */       
/* 1005 */       return true;
/*      */     } 
/*      */     
/* 1008 */     if (attrib.equals("mesh")) {
/* 1009 */       this.records[pos].setMeshID(value);
/*      */       
/* 1011 */       return true;
/*      */     } 
/*      */     
/* 1014 */     if (attrib.equals("offhand")) {
/* 1015 */       this.records[pos].setSlotOffhand(value.equals("1"));
/*      */       
/* 1017 */       return true;
/*      */     } 
/*      */     
/* 1020 */     if (attrib.equals("quest")) {
/* 1021 */       this.records[pos].setQuestItem(value.equals("1"));
/*      */       
/* 1023 */       return true;
/*      */     } 
/*      */     
/* 1026 */     if (attrib.equals("ranged1h")) {
/* 1027 */       this.records[pos].setSlotRanged1H(value.equals("1"));
/*      */       
/* 1029 */       return true;
/*      */     } 
/*      */     
/* 1032 */     if (attrib.equals("ranged2h")) {
/* 1033 */       this.records[pos].setSlotRanged2H(value.equals("1"));
/*      */       
/* 1035 */       return true;
/*      */     } 
/*      */     
/* 1038 */     if (attrib.equals("ring")) {
/* 1039 */       this.records[pos].setSlotRing(value.equals("1"));
/*      */       
/* 1041 */       return true;
/*      */     } 
/*      */     
/* 1044 */     if (attrib.equals("scepter")) {
/* 1045 */       this.records[pos].setSlotScepter1H(value.equals("1"));
/*      */       
/* 1047 */       return true;
/*      */     } 
/*      */     
/* 1050 */     if (attrib.equals("shardBitmap")) {
/* 1051 */       this.records[pos].setShardBitmapID(value);
/*      */       
/* 1053 */       return true;
/*      */     } 
/*      */     
/* 1056 */     if (attrib.equals("shield")) {
/* 1057 */       this.records[pos].setSlotShield(value.equals("1"));
/*      */       
/* 1059 */       return true;
/*      */     } 
/*      */     
/* 1062 */     if (attrib.equals("shoulders")) {
/* 1063 */       this.records[pos].setSlotShoulders(value.equals("1"));
/*      */       
/* 1065 */       return true;
/*      */     } 
/*      */     
/* 1068 */     if (attrib.equals("soulbound")) {
/* 1069 */       this.records[pos].setSoulbound(value.equals("1"));
/*      */       
/* 1071 */       return true;
/*      */     } 
/*      */     
/* 1074 */     if (attrib.equals("spear")) {
/* 1075 */       this.records[pos].setSlotSpear2H(value.equals("1"));
/*      */       
/* 1077 */       return true;
/*      */     } 
/*      */     
/* 1080 */     if (attrib.equals("staff")) {
/* 1081 */       this.records[pos].setSlotStaff2H(value.equals("1"));
/*      */       
/* 1083 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1094 */     if (attrib.equals("sword")) {
/* 1095 */       this.records[pos].setSlotSword1H(value.equals("1"));
/*      */       
/* 1097 */       return true;
/*      */     } 
/*      */     
/* 1100 */     if (attrib.equals("sword2h")) {
/* 1101 */       this.records[pos].setSlotSword2H(value.equals("1"));
/*      */       
/* 1103 */       return true;
/*      */     } 
/*      */     
/* 1106 */     if (attrib.equals("waist")) {
/* 1107 */       this.records[pos].setSlotBelt(value.equals("1"));
/*      */       
/* 1109 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1113 */     if (attrib.equals("skillName")) {
/* 1114 */       this.records[pos].setItemSkillID(value);
/*      */       
/* 1116 */       return true;
/*      */     } 
/*      */     
/* 1119 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribSkillModifier(String attrib, int pos, String value, int index) {
/* 1123 */     if (attrib.startsWith("modifiedSkillName")) {
/*      */       
/* 1125 */       String s = attrib.substring(17);
/* 1126 */       int i = parseInt(s);
/*      */       
/* 1128 */       this.records[pos].addModifierSkillID(i, value);
/*      */       
/* 1130 */       return true;
/*      */     } 
/*      */     
/* 1133 */     if (attrib.startsWith("modifierSkillName")) {
/*      */       
/* 1135 */       String s = attrib.substring(17);
/* 1136 */       int i = parseInt(s);
/*      */       
/* 1138 */       this.records[pos].addModifierModifierID(i, value);
/*      */       
/* 1140 */       return true;
/*      */     } 
/*      */     
/* 1143 */     if (attrib.equals("itemSkillModifierControl")) {
/* 1144 */       int iVal = parseInt(value);
/*      */       
/* 1146 */       this.records[pos].setItemSetSkillModifierLevel(iVal, index);
/*      */     } 
/*      */     
/* 1149 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribStat(String attrib, int pos, String value, int index) {
/* 1153 */     boolean processed = false;
/*      */     
/* 1155 */     if (attrib.startsWith("defensive")) {
/* 1156 */       processed = fillAttribStatDefense(attrib, pos, value, index);
/* 1157 */       if (processed) return true;
/*      */     
/*      */     } 
/* 1160 */     if (attrib.startsWith("offensive")) {
/* 1161 */       processed = fillAttribStatOffense(attrib, pos, value, index);
/* 1162 */       if (processed) return true;
/*      */     
/*      */     } 
/* 1165 */     if (attrib.startsWith("retaliation")) {
/* 1166 */       processed = fillAttribStatRetaliation(attrib, pos, value, index);
/* 1167 */       if (processed) return true;
/*      */     
/*      */     } 
/* 1170 */     processed = fillAttribStatChar(attrib, pos, value, index);
/*      */     
/* 1172 */     return processed;
/*      */   }
/*      */   
/*      */   private boolean fillAttribStatDetails(String attrib, String search, int pos, String value, int index) {
/* 1176 */     boolean processed = false;
/*      */     
/* 1178 */     int len = search.length();
/* 1179 */     int p = attrib.indexOf(search);
/*      */     
/* 1181 */     if (p == -1) {
/* 1182 */       if (search.equals("retaliationPercentCurrentLife"))
/*      */       {
/*      */         
/* 1185 */         p = attrib.indexOf("retaliationPercentcurrentLife");
/*      */       }
/*      */       
/* 1188 */       if (p == -1) return false;
/*      */     
/*      */     } 
/* 1191 */     String suf = attrib.substring(p + len);
/*      */     
/* 1193 */     DBStat stat = this.records[pos].getCreateDBStat(search, index);
/*      */     
/* 1195 */     float fVal = parseFloat(value);
/* 1196 */     int iVal = (int)fVal;
/*      */     
/* 1198 */     if (suf.equals("Global")) {
/* 1199 */       stat.setGlobal((iVal > 0));
/*      */       
/* 1201 */       processed = true;
/*      */     } 
/*      */     
/* 1204 */     if (suf.equals("XOR")) {
/* 1205 */       stat.setXOR((iVal > 0));
/*      */       
/* 1207 */       processed = true;
/*      */     } 
/*      */     
/* 1210 */     if (suf.equals("")) {
/* 1211 */       stat.setStatMin(fVal);
/*      */ 
/*      */       
/* 1214 */       processed = true;
/*      */     } 
/*      */     
/* 1217 */     if (suf.equals("Min")) {
/* 1218 */       stat.setStatMin(fVal);
/*      */       
/* 1220 */       processed = true;
/*      */     } 
/*      */     
/* 1223 */     if (suf.equals("Max")) {
/* 1224 */       stat.setStatMax(fVal);
/*      */       
/* 1226 */       processed = true;
/*      */     } 
/*      */     
/* 1229 */     if (suf.equals("Chance")) {
/* 1230 */       stat.setStatChance(iVal);
/*      */       
/* 1232 */       processed = true;
/*      */     } 
/*      */     
/* 1235 */     if (suf.equals("Modifier")) {
/* 1236 */       stat.setStatModifier(iVal);
/*      */       
/* 1238 */       processed = true;
/*      */     } 
/*      */     
/* 1241 */     if (suf.equals("ModifierChance")) {
/* 1242 */       stat.setStatModifierChance(iVal);
/*      */       
/* 1244 */       processed = true;
/*      */     } 
/*      */     
/* 1247 */     if (suf.equals("Duration")) {
/* 1248 */       stat.setMinDuration(iVal);
/*      */ 
/*      */       
/* 1251 */       processed = true;
/*      */     } 
/*      */     
/* 1254 */     if (suf.equals("DurationMin")) {
/* 1255 */       stat.setMinDuration(iVal);
/*      */       
/* 1257 */       processed = true;
/*      */     } 
/*      */     
/* 1260 */     if (suf.equals("DurationMax")) {
/* 1261 */       stat.setMaxDuration(iVal);
/*      */       
/* 1263 */       processed = true;
/*      */     } 
/*      */     
/* 1266 */     if (suf.equals("DurationChance")) {
/* 1267 */       stat.setDurationChance(iVal);
/*      */       
/* 1269 */       processed = true;
/*      */     } 
/*      */     
/* 1272 */     if (suf.equals("DurationModifier")) {
/* 1273 */       stat.setDurationModifier(iVal);
/*      */       
/* 1275 */       processed = true;
/*      */     } 
/*      */     
/* 1278 */     if (suf.equals("DurationModifierChance")) {
/* 1279 */       stat.setDurationModifierChance(iVal);
/*      */       
/* 1281 */       processed = true;
/*      */     } 
/*      */     
/* 1284 */     if (suf.equals("MaxResist")) {
/* 1285 */       stat.setMaxResist(iVal);
/*      */       
/* 1287 */       processed = true;
/*      */     } 
/*      */     
/* 1290 */     return processed;
/*      */   }
/*      */   
/*      */   private boolean fillAttribStatChar(String attrib, int pos, String value, int index) {
/* 1294 */     boolean processed = false;
/*      */     
/* 1296 */     processed = fillAttribStatDetails(attrib, "skillManaCost", pos, value, index);
/* 1297 */     if (processed) return processed;
/*      */     
/* 1299 */     processed = fillAttribStatDetails(attrib, "skillCooldownTime", pos, value, index);
/* 1300 */     if (processed) return processed;
/*      */     
/* 1302 */     processed = fillAttribStatDetails(attrib, "skillActiveDuration", pos, value, index);
/* 1303 */     if (processed) return processed;
/*      */     
/* 1305 */     processed = fillAttribStatDetails(attrib, "lifeMonitorPercent", pos, value, index);
/* 1306 */     if (processed) return processed;
/*      */     
/* 1308 */     processed = fillAttribStatDetails(attrib, "weaponDamagePct", pos, value, index);
/* 1309 */     if (processed) return processed;
/*      */     
/* 1311 */     processed = fillAttribStatDetails(attrib, "damageAbsorptionPercent", pos, value, index);
/* 1312 */     if (processed) return processed;
/*      */     
/* 1314 */     processed = fillAttribStatDetails(attrib, "skillTargetRadius", pos, value, index);
/* 1315 */     if (processed) return processed;
/*      */     
/* 1317 */     processed = fillAttribStatDetails(attrib, "petBurstSpawn", pos, value, index);
/* 1318 */     if (processed) return processed;
/*      */     
/* 1320 */     processed = fillAttribStatDetails(attrib, "petLimit", pos, value, index);
/* 1321 */     if (processed) return processed;
/*      */     
/* 1323 */     processed = fillAttribStatDetails(attrib, "skillMaxLevel", pos, value, index);
/* 1324 */     if (processed) return processed;
/*      */     
/* 1326 */     processed = fillAttribStatDetails(attrib, "sparkMaxNumber", pos, value, index);
/* 1327 */     if (processed) return processed;
/*      */     
/* 1329 */     processed = fillAttribStatDetails(attrib, "skillTargetNumber", pos, value, index);
/* 1330 */     if (processed) return processed;
/*      */     
/* 1332 */     processed = fillAttribStatDetails(attrib, "characterLightRadius", pos, value, index);
/* 1333 */     if (processed) return processed;
/*      */     
/* 1335 */     processed = fillAttribStatDetails(attrib, "characterDefensiveAbility", pos, value, index);
/* 1336 */     if (processed) return processed;
/*      */     
/* 1338 */     processed = fillAttribStatDetails(attrib, "characterOffensiveAbility", pos, value, index);
/* 1339 */     if (processed) return processed;
/*      */     
/* 1341 */     processed = fillAttribStatDetails(attrib, "characterMana", pos, value, index);
/* 1342 */     if (processed) return processed;
/*      */     
/* 1344 */     processed = fillAttribStatDetails(attrib, "characterManaLimitReserve", pos, value, index);
/* 1345 */     if (processed) return processed;
/*      */     
/* 1347 */     processed = fillAttribStatDetails(attrib, "characterManaLimitReserveReduction", pos, value, index);
/* 1348 */     if (processed) return processed;
/*      */     
/* 1350 */     processed = fillAttribStatDetails(attrib, "characterManaRegen", pos, value, index);
/* 1351 */     if (processed) return processed;
/*      */     
/* 1353 */     processed = fillAttribStatDetails(attrib, "characterHealIncreasePercent", pos, value, index);
/* 1354 */     if (processed) return processed;
/*      */     
/* 1356 */     processed = fillAttribStatDetails(attrib, "characterLife", pos, value, index);
/* 1357 */     if (processed) return processed;
/*      */     
/* 1359 */     processed = fillAttribStatDetails(attrib, "characterLifeRegen", pos, value, index);
/* 1360 */     if (processed) return processed;
/*      */     
/* 1362 */     processed = fillAttribStatDetails(attrib, "characterConstitution", pos, value, index);
/* 1363 */     if (processed) return processed;
/*      */     
/* 1365 */     processed = fillAttribStatDetails(attrib, "skillLifeBonus", pos, value, index);
/* 1366 */     if (processed) return processed;
/*      */     
/* 1368 */     processed = fillAttribStatDetails(attrib, "skillLifePercent", pos, value, index);
/* 1369 */     if (processed) return processed;
/*      */     
/* 1371 */     processed = fillAttribStatDetails(attrib, "characterDexterity", pos, value, index);
/* 1372 */     if (processed) return processed;
/*      */     
/* 1374 */     processed = fillAttribStatDetails(attrib, "characterStrength", pos, value, index);
/* 1375 */     if (processed) return processed;
/*      */     
/* 1377 */     processed = fillAttribStatDetails(attrib, "characterIntelligence", pos, value, index);
/* 1378 */     if (processed) return processed;
/*      */     
/* 1380 */     processed = fillAttribStatDetails(attrib, "characterAttackSpeed", pos, value, index);
/* 1381 */     if (processed) return processed;
/*      */     
/* 1383 */     processed = fillAttribStatDetails(attrib, "characterSpellCastSpeed", pos, value, index);
/* 1384 */     if (processed) return processed;
/*      */     
/* 1386 */     processed = fillAttribStatDetails(attrib, "characterRunSpeed", pos, value, index);
/* 1387 */     if (processed) return processed;
/*      */     
/* 1389 */     processed = fillAttribStatDetails(attrib, "characterTotalSpeed", pos, value, index);
/* 1390 */     if (processed) return processed;
/*      */     
/* 1392 */     processed = fillAttribStatDetails(attrib, "blockRecoveryTime", pos, value, index);
/* 1393 */     if (processed) return processed;
/*      */     
/* 1395 */     processed = fillAttribStatDetails(attrib, "characterDefensiveBlockRecoveryReduction", pos, value, index);
/* 1396 */     if (processed) return processed;
/*      */     
/* 1398 */     processed = fillAttribStatDetails(attrib, "characterGlobalReqReduction", pos, value, index);
/* 1399 */     if (processed) return processed;
/*      */     
/* 1401 */     processed = fillAttribStatDetails(attrib, "characterEnergyAbsorptionPercent", pos, value, index);
/* 1402 */     if (processed) return processed;
/*      */     
/* 1404 */     processed = fillAttribStatDetails(attrib, "characterIncreasedExperience", pos, value, index);
/* 1405 */     if (processed) return processed;
/*      */     
/* 1407 */     processed = fillAttribStatDetails(attrib, "skillCooldownReduction", pos, value, index);
/* 1408 */     if (processed) return processed;
/*      */     
/* 1410 */     processed = fillAttribStatDetails(attrib, "skillManaCostReduction", pos, value, index);
/* 1411 */     if (processed) return processed;
/*      */     
/* 1413 */     processed = fillAttribStatDetails(attrib, "racialBonusPercentDamage", pos, value, index);
/* 1414 */     if (processed) return processed;
/*      */     
/* 1416 */     processed = fillAttribStatDetails(attrib, "piercingProjectile", pos, value, index);
/* 1417 */     if (processed) return processed;
/*      */     
/* 1419 */     if (attrib.equals("racialBonusRace")) {
/* 1420 */       String tag = value;
/* 1421 */       if (!tag.startsWith("tag")) tag = "tag" + tag;
/*      */       
/* 1423 */       String name = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_CREATURES, tag, false);
/*      */       
/* 1425 */       this.records[pos].addRace(value, name);
/*      */       
/* 1427 */       return true;
/*      */     } 
/*      */     
/* 1430 */     processed = fillAttribStatDetails(attrib, "characterDeflectProjectile", pos, value, index);
/* 1431 */     if (processed) return processed;
/*      */     
/* 1433 */     processed = fillAttribStatDetails(attrib, "characterDodgePercent", pos, value, index);
/* 1434 */     if (processed) return processed;
/*      */     
/* 1436 */     processed = fillAttribStatDetails(attrib, "characterLevelReqReduction", pos, value, index);
/* 1437 */     if (processed) return processed;
/*      */     
/* 1439 */     processed = fillAttribStatDetails(attrib, "characterArmorStrengthReqReduction", pos, value, index);
/* 1440 */     if (processed) return processed;
/*      */     
/* 1442 */     processed = fillAttribStatDetails(attrib, "characterHuntingDexterityReqReduction", pos, value, index);
/* 1443 */     if (processed) return processed;
/*      */     
/* 1445 */     processed = fillAttribStatDetails(attrib, "characterMeleeDexterityReqReduction", pos, value, index);
/* 1446 */     if (processed) return processed;
/*      */     
/* 1448 */     processed = fillAttribStatDetails(attrib, "characterMeleeStrengthReqReduction", pos, value, index);
/* 1449 */     if (processed) return processed;
/*      */     
/* 1451 */     processed = fillAttribStatDetails(attrib, "characterWeaponStrengthReqReduction", pos, value, index);
/* 1452 */     if (processed) return processed;
/*      */     
/* 1454 */     processed = fillAttribStatDetails(attrib, "characterWeaponIntelligenceReqReduction", pos, value, index);
/* 1455 */     if (processed) return processed;
/*      */     
/* 1457 */     processed = fillAttribStatDetails(attrib, "characterJewelryIntelligenceReqReduction", pos, value, index);
/* 1458 */     if (processed) return processed;
/*      */     
/* 1460 */     processed = fillAttribStatDetails(attrib, "characterShieldStrengthReqReduction", pos, value, index);
/* 1461 */     if (processed) return processed;
/*      */     
/* 1463 */     processed = fillAttribStatDetails(attrib, "conversionPercentage", pos, value, index);
/* 1464 */     if (processed) return processed;
/*      */     
/* 1466 */     processed = fillAttribStatDetails(attrib, "conversionPercentage2", pos, value, index);
/* 1467 */     if (processed) return processed;
/*      */     
/* 1469 */     if (!processed) {
/* 1470 */       int i = parseInt(value);
/*      */       
/* 1472 */       logTag(attrib, i, this.records[pos]);
/*      */     } 
/*      */     
/* 1475 */     return processed;
/*      */   }
/*      */   
/*      */   private boolean fillAttribStatDefense(String attrib, int pos, String value, int index) {
/* 1479 */     boolean processed = false;
/*      */     
/* 1481 */     processed = fillAttribStatDetails(attrib, "defensiveAbsorption", pos, value, index);
/* 1482 */     if (processed) return processed;
/*      */     
/* 1484 */     processed = fillAttribStatDetails(attrib, "defensiveAether", pos, value, index);
/* 1485 */     if (processed) return processed;
/*      */     
/* 1487 */     processed = fillAttribStatDetails(attrib, "defensiveAll", pos, value, index);
/* 1488 */     if (processed) return processed;
/*      */     
/* 1490 */     processed = fillAttribStatDetails(attrib, "defensiveBleeding", pos, value, index);
/* 1491 */     if (processed) return processed;
/*      */     
/* 1493 */     processed = fillAttribStatDetails(attrib, "defensiveBlock", pos, value, index);
/* 1494 */     if (processed) return processed;
/*      */     
/* 1496 */     processed = fillAttribStatDetails(attrib, "defensiveBlockAmount", pos, value, index);
/* 1497 */     if (processed) return processed;
/*      */     
/* 1499 */     processed = fillAttribStatDetails(attrib, "defensiveBonusProtection", pos, value, index);
/* 1500 */     if (processed) return processed;
/*      */     
/* 1502 */     processed = fillAttribStatDetails(attrib, "defensiveChaos", pos, value, index);
/* 1503 */     if (processed) return processed;
/*      */     
/* 1505 */     processed = fillAttribStatDetails(attrib, "defensiveCold", pos, value, index);
/* 1506 */     if (processed) return processed;
/*      */     
/* 1508 */     processed = fillAttribStatDetails(attrib, "defensiveConfusion", pos, value, index);
/* 1509 */     if (processed) return processed;
/*      */     
/* 1511 */     processed = fillAttribStatDetails(attrib, "defensiveConvert", pos, value, index);
/* 1512 */     if (processed) return processed;
/*      */     
/* 1514 */     processed = fillAttribStatDetails(attrib, "defensiveDisruption", pos, value, index);
/* 1515 */     if (processed) return processed;
/*      */     
/* 1517 */     processed = fillAttribStatDetails(attrib, "defensiveElemental", pos, value, index);
/* 1518 */     if (processed) return processed;
/*      */     
/* 1520 */     processed = fillAttribStatDetails(attrib, "defensiveElementalResistance", pos, value, index);
/* 1521 */     if (processed) return processed;
/*      */     
/* 1523 */     processed = fillAttribStatDetails(attrib, "defensiveFear", pos, value, index);
/* 1524 */     if (processed) return processed;
/*      */     
/* 1526 */     processed = fillAttribStatDetails(attrib, "defensiveFire", pos, value, index);
/* 1527 */     if (processed) return processed;
/*      */     
/* 1529 */     processed = fillAttribStatDetails(attrib, "defensiveFreeze", pos, value, index);
/* 1530 */     if (processed) return processed;
/*      */     
/* 1532 */     processed = fillAttribStatDetails(attrib, "defensiveKnockdown", pos, value, index);
/* 1533 */     if (processed) return processed;
/*      */     
/* 1535 */     processed = fillAttribStatDetails(attrib, "defensiveLife", pos, value, index);
/* 1536 */     if (processed) return processed;
/*      */     
/* 1538 */     processed = fillAttribStatDetails(attrib, "defensiveLightning", pos, value, index);
/* 1539 */     if (processed) return processed;
/*      */     
/* 1541 */     processed = fillAttribStatDetails(attrib, "defensivePercentCurrentLife", pos, value, index);
/* 1542 */     if (processed) return processed;
/*      */     
/* 1544 */     processed = fillAttribStatDetails(attrib, "defensivePercentReflectionResistance", pos, value, index);
/* 1545 */     if (processed) return processed;
/*      */     
/* 1547 */     processed = fillAttribStatDetails(attrib, "defensiveManaBurnRatio", pos, value, index);
/* 1548 */     if (processed) return processed;
/*      */     
/* 1550 */     processed = fillAttribStatDetails(attrib, "defensivePetrify", pos, value, index);
/* 1551 */     if (processed) return processed;
/*      */     
/* 1553 */     processed = fillAttribStatDetails(attrib, "defensivePhysical", pos, value, index);
/* 1554 */     if (processed) return processed;
/*      */     
/* 1556 */     processed = fillAttribStatDetails(attrib, "defensivePierce", pos, value, index);
/* 1557 */     if (processed) return processed;
/*      */     
/* 1559 */     processed = fillAttribStatDetails(attrib, "defensivePoison", pos, value, index);
/* 1560 */     if (processed) return processed;
/*      */     
/* 1562 */     processed = fillAttribStatDetails(attrib, "defensiveProtection", pos, value, index);
/* 1563 */     if (processed) return processed;
/*      */     
/* 1565 */     processed = fillAttribStatDetails(attrib, "defensiveReflect", pos, value, index);
/* 1566 */     if (processed) return processed;
/*      */     
/* 1568 */     processed = fillAttribStatDetails(attrib, "defensiveSleep", pos, value, index);
/* 1569 */     if (processed) return processed;
/*      */     
/* 1571 */     processed = fillAttribStatDetails(attrib, "defensiveSlowLifeLeach", pos, value, index);
/* 1572 */     if (processed) return processed;
/*      */     
/* 1574 */     processed = fillAttribStatDetails(attrib, "defensiveSlowManaLeach", pos, value, index);
/* 1575 */     if (processed) return processed;
/*      */     
/* 1577 */     processed = fillAttribStatDetails(attrib, "defensiveStun", pos, value, index);
/* 1578 */     if (processed) return processed;
/*      */     
/* 1580 */     processed = fillAttribStatDetails(attrib, "defensiveTaunt", pos, value, index);
/* 1581 */     if (processed) return processed;
/*      */     
/* 1583 */     processed = fillAttribStatDetails(attrib, "defensiveTotalSpeedResistance", pos, value, index);
/* 1584 */     if (processed) return processed;
/*      */     
/* 1586 */     processed = fillAttribStatDetails(attrib, "defensiveTrap", pos, value, index);
/* 1587 */     if (processed) return processed;
/*      */     
/* 1589 */     if (!processed) {
/* 1590 */       int i = parseInt(value);
/*      */       
/* 1592 */       logTag(attrib, i, this.records[pos]);
/*      */     } 
/*      */     
/* 1595 */     return processed;
/*      */   }
/*      */   
/*      */   private boolean fillAttribStatOffense(String attrib, int pos, String value, int index) {
/* 1599 */     boolean processed = false;
/*      */     
/* 1601 */     processed = fillAttribStatDetails(attrib, "offensiveAether", pos, value, index);
/* 1602 */     if (processed) return processed;
/*      */     
/* 1604 */     processed = fillAttribStatDetails(attrib, "offensiveBaseAether", pos, value, index);
/* 1605 */     if (processed) return processed;
/*      */     
/* 1607 */     processed = fillAttribStatDetails(attrib, "offensiveBaseChaos", pos, value, index);
/* 1608 */     if (processed) return processed;
/*      */     
/* 1610 */     processed = fillAttribStatDetails(attrib, "offensiveBaseCold", pos, value, index);
/* 1611 */     if (processed) return processed;
/*      */     
/* 1613 */     processed = fillAttribStatDetails(attrib, "offensiveBaseFire", pos, value, index);
/* 1614 */     if (processed) return processed;
/*      */     
/* 1616 */     processed = fillAttribStatDetails(attrib, "offensiveBaseLife", pos, value, index);
/* 1617 */     if (processed) return processed;
/*      */     
/* 1619 */     processed = fillAttribStatDetails(attrib, "offensiveBaseLightning", pos, value, index);
/* 1620 */     if (processed) return processed;
/*      */     
/* 1622 */     processed = fillAttribStatDetails(attrib, "offensiveBasePoison", pos, value, index);
/* 1623 */     if (processed) return processed;
/*      */     
/* 1625 */     processed = fillAttribStatDetails(attrib, "offensiveBonusPhysical", pos, value, index);
/* 1626 */     if (processed) return processed;
/*      */     
/* 1628 */     processed = fillAttribStatDetails(attrib, "offensiveChaos", pos, value, index);
/* 1629 */     if (processed) return processed;
/*      */     
/* 1631 */     processed = fillAttribStatDetails(attrib, "offensiveCold", pos, value, index);
/* 1632 */     if (processed) return processed;
/*      */     
/* 1634 */     processed = fillAttribStatDetails(attrib, "offensiveConfusion", pos, value, index);
/* 1635 */     if (processed) return processed;
/*      */     
/* 1637 */     processed = fillAttribStatDetails(attrib, "offensiveConvert", pos, value, index);
/* 1638 */     if (processed) return processed;
/*      */     
/* 1640 */     processed = fillAttribStatDetails(attrib, "offensiveCritDamage", pos, value, index);
/* 1641 */     if (processed) return processed;
/*      */     
/* 1643 */     processed = fillAttribStatDetails(attrib, "offensiveDamageMult", pos, value, index);
/* 1644 */     if (processed) return processed;
/*      */     
/* 1646 */     processed = fillAttribStatDetails(attrib, "offensiveDisruption", pos, value, index);
/* 1647 */     if (processed) return processed;
/*      */     
/* 1649 */     processed = fillAttribStatDetails(attrib, "offensiveElemental", pos, value, index);
/* 1650 */     if (processed) return processed;
/*      */     
/* 1652 */     processed = fillAttribStatDetails(attrib, "offensiveElementalReductionPercent", pos, value, index);
/* 1653 */     if (processed) return processed;
/*      */     
/* 1655 */     processed = fillAttribStatDetails(attrib, "offensiveElementalResistanceReductionAbsolute", pos, value, index);
/* 1656 */     if (processed) return processed;
/*      */     
/* 1658 */     processed = fillAttribStatDetails(attrib, "offensiveElementalResistanceReductionPercent", pos, value, index);
/* 1659 */     if (processed) return processed;
/*      */     
/* 1661 */     processed = fillAttribStatDetails(attrib, "offensiveFear", pos, value, index);
/* 1662 */     if (processed) return processed;
/*      */     
/* 1664 */     processed = fillAttribStatDetails(attrib, "offensiveFire", pos, value, index);
/* 1665 */     if (processed) return processed;
/*      */     
/* 1667 */     processed = fillAttribStatDetails(attrib, "offensiveFreeze", pos, value, index);
/* 1668 */     if (processed) return processed;
/*      */     
/* 1670 */     processed = fillAttribStatDetails(attrib, "offensiveFumble", pos, value, index);
/* 1671 */     if (processed) return processed;
/*      */     
/* 1673 */     processed = fillAttribStatDetails(attrib, "offensiveKnockdown", pos, value, index);
/* 1674 */     if (processed) return processed;
/*      */     
/* 1676 */     processed = fillAttribStatDetails(attrib, "offensiveLife", pos, value, index);
/* 1677 */     if (processed) return processed;
/*      */     
/* 1679 */     processed = fillAttribStatDetails(attrib, "offensiveLifeLeech", pos, value, index);
/* 1680 */     if (processed) return processed;
/*      */     
/* 1682 */     processed = fillAttribStatDetails(attrib, "offensiveLightning", pos, value, index);
/* 1683 */     if (processed) return processed;
/*      */     
/* 1685 */     processed = fillAttribStatDetails(attrib, "offensivePercentCurrentLife", pos, value, index);
/* 1686 */     if (processed) return processed;
/*      */     
/* 1688 */     processed = fillAttribStatDetails(attrib, "offensiveManaBurnDamageRatio", pos, value, index);
/* 1689 */     if (processed) return processed;
/*      */     
/* 1691 */     processed = fillAttribStatDetails(attrib, "offensiveManaBurnDrain", pos, value, index);
/* 1692 */     if (processed) return processed;
/*      */     
/* 1694 */     processed = fillAttribStatDetails(attrib, "offensivePetrify", pos, value, index);
/* 1695 */     if (processed) return processed;
/*      */     
/* 1697 */     processed = fillAttribStatDetails(attrib, "offensivePhysical", pos, value, index);
/* 1698 */     if (processed) return processed;
/*      */     
/* 1700 */     processed = fillAttribStatDetails(attrib, "offensivePhysicalReductionPercent", pos, value, index);
/* 1701 */     if (processed) return processed;
/*      */     
/* 1703 */     processed = fillAttribStatDetails(attrib, "offensivePhysicalResistanceReductionAbsolute", pos, value, index);
/* 1704 */     if (processed) return processed;
/*      */     
/* 1706 */     processed = fillAttribStatDetails(attrib, "offensivePhysicalResistanceReductionPercent", pos, value, index);
/* 1707 */     if (processed) return processed;
/*      */     
/* 1709 */     processed = fillAttribStatDetails(attrib, "offensivePierce", pos, value, index);
/* 1710 */     if (processed) return processed;
/*      */     
/* 1712 */     processed = fillAttribStatDetails(attrib, "offensivePierceRatio", pos, value, index);
/* 1713 */     if (processed) return processed;
/*      */     
/* 1715 */     processed = fillAttribStatDetails(attrib, "offensivePoison", pos, value, index);
/* 1716 */     if (processed) return processed;
/*      */     
/* 1718 */     processed = fillAttribStatDetails(attrib, "offensiveProjectileFumble", pos, value, index);
/* 1719 */     if (processed) return processed;
/*      */     
/* 1721 */     processed = fillAttribStatDetails(attrib, "offensiveSleep", pos, value, index);
/* 1722 */     if (processed) return processed;
/*      */     
/* 1724 */     processed = fillAttribStatDetails(attrib, "offensiveSlowAttackSpeed", pos, value, index);
/* 1725 */     if (processed) return processed;
/*      */     
/* 1727 */     processed = fillAttribStatDetails(attrib, "offensiveSlowBleeding", pos, value, index);
/* 1728 */     if (processed) return processed;
/*      */     
/* 1730 */     processed = fillAttribStatDetails(attrib, "offensiveSlowCold", pos, value, index);
/* 1731 */     if (processed) return processed;
/*      */     
/* 1733 */     processed = fillAttribStatDetails(attrib, "offensiveSlowDefensiveAbility", pos, value, index);
/* 1734 */     if (processed) return processed;
/*      */     
/* 1736 */     processed = fillAttribStatDetails(attrib, "offensiveSlowDefensiveReduction", pos, value, index);
/* 1737 */     if (processed) return processed;
/*      */     
/* 1739 */     processed = fillAttribStatDetails(attrib, "offensiveSlowFire", pos, value, index);
/* 1740 */     if (processed) return processed;
/*      */     
/* 1742 */     processed = fillAttribStatDetails(attrib, "offensiveSlowLife", pos, value, index);
/* 1743 */     if (processed) return processed;
/*      */     
/* 1745 */     processed = fillAttribStatDetails(attrib, "offensiveSlowLifeLeach", pos, value, index);
/* 1746 */     if (processed) return processed;
/*      */     
/* 1748 */     processed = fillAttribStatDetails(attrib, "offensiveSlowLightning", pos, value, index);
/* 1749 */     if (processed) return processed;
/*      */     
/* 1751 */     processed = fillAttribStatDetails(attrib, "offensiveSlowManaLeach", pos, value, index);
/* 1752 */     if (processed) return processed;
/*      */     
/* 1754 */     processed = fillAttribStatDetails(attrib, "offensiveSlowOffensiveAbility", pos, value, index);
/* 1755 */     if (processed) return processed;
/*      */     
/* 1757 */     processed = fillAttribStatDetails(attrib, "offensiveSlowOffensiveReduction", pos, value, index);
/* 1758 */     if (processed) return processed;
/*      */     
/* 1760 */     processed = fillAttribStatDetails(attrib, "offensiveSlowPhysical", pos, value, index);
/* 1761 */     if (processed) return processed;
/*      */     
/* 1763 */     processed = fillAttribStatDetails(attrib, "offensiveSlowPoison", pos, value, index);
/* 1764 */     if (processed) return processed;
/*      */     
/* 1766 */     processed = fillAttribStatDetails(attrib, "offensiveSlowRunSpeed", pos, value, index);
/* 1767 */     if (processed) return processed;
/*      */     
/* 1769 */     processed = fillAttribStatDetails(attrib, "offensiveSlowTotalSpeed", pos, value, index);
/* 1770 */     if (processed) return processed;
/*      */     
/* 1772 */     processed = fillAttribStatDetails(attrib, "offensiveStun", pos, value, index);
/* 1773 */     if (processed) return processed;
/*      */     
/* 1775 */     processed = fillAttribStatDetails(attrib, "offensiveTaunt", pos, value, index);
/* 1776 */     if (processed) return processed;
/*      */     
/* 1778 */     processed = fillAttribStatDetails(attrib, "offensiveTotalDamage", pos, value, index);
/* 1779 */     if (processed) return processed;
/*      */     
/* 1781 */     processed = fillAttribStatDetails(attrib, "offensiveTotalDamageReductionPercent", pos, value, index);
/* 1782 */     if (processed) return processed;
/*      */     
/* 1784 */     processed = fillAttribStatDetails(attrib, "offensiveTotalResistanceReductionAbsolute", pos, value, index);
/* 1785 */     if (processed) return processed;
/*      */     
/* 1787 */     processed = fillAttribStatDetails(attrib, "offensiveTotalResistanceReductionPercent", pos, value, index);
/* 1788 */     if (processed) return processed;
/*      */     
/* 1790 */     processed = fillAttribStatDetails(attrib, "offensiveTrap", pos, value, index);
/* 1791 */     if (processed) return processed;
/*      */     
/* 1793 */     if (!processed) {
/* 1794 */       int i = parseInt(value);
/*      */       
/* 1796 */       logTag(attrib, i, this.records[pos]);
/*      */     } 
/*      */     
/* 1799 */     return processed;
/*      */   }
/*      */   
/*      */   private boolean fillAttribStatRetaliation(String attrib, int pos, String value, int index) {
/* 1803 */     boolean processed = false;
/*      */     
/* 1805 */     processed = fillAttribStatDetails(attrib, "retaliationAether", pos, value, index);
/* 1806 */     if (processed) return processed;
/*      */     
/* 1808 */     processed = fillAttribStatDetails(attrib, "retaliationChaos", pos, value, index);
/* 1809 */     if (processed) return processed;
/*      */     
/* 1811 */     processed = fillAttribStatDetails(attrib, "retaliationCold", pos, value, index);
/* 1812 */     if (processed) return processed;
/*      */     
/* 1814 */     processed = fillAttribStatDetails(attrib, "retaliationConfusion", pos, value, index);
/* 1815 */     if (processed) return processed;
/*      */     
/* 1817 */     processed = fillAttribStatDetails(attrib, "retaliationConvert", pos, value, index);
/* 1818 */     if (processed) return processed;
/*      */     
/* 1820 */     processed = fillAttribStatDetails(attrib, "retaliationDamagePct", pos, value, index);
/* 1821 */     if (processed) return processed;
/*      */     
/* 1823 */     processed = fillAttribStatDetails(attrib, "retaliationElemental", pos, value, index);
/* 1824 */     if (processed) return processed;
/*      */     
/* 1826 */     processed = fillAttribStatDetails(attrib, "retaliationFear", pos, value, index);
/* 1827 */     if (processed) return processed;
/*      */     
/* 1829 */     processed = fillAttribStatDetails(attrib, "retaliationFire", pos, value, index);
/* 1830 */     if (processed) return processed;
/*      */     
/* 1832 */     processed = fillAttribStatDetails(attrib, "retaliationFreeze", pos, value, index);
/* 1833 */     if (processed) return processed;
/*      */     
/* 1835 */     processed = fillAttribStatDetails(attrib, "retaliationLife", pos, value, index);
/* 1836 */     if (processed) return processed;
/*      */     
/* 1838 */     processed = fillAttribStatDetails(attrib, "retaliationLightning", pos, value, index);
/* 1839 */     if (processed) return processed;
/*      */     
/* 1841 */     processed = fillAttribStatDetails(attrib, "retaliationPercentCurrentLife", pos, value, index);
/* 1842 */     if (processed) return processed;
/*      */     
/* 1844 */     processed = fillAttribStatDetails(attrib, "retaliationPetrify", pos, value, index);
/* 1845 */     if (processed) return processed;
/*      */     
/* 1847 */     processed = fillAttribStatDetails(attrib, "retaliationPhysical", pos, value, index);
/* 1848 */     if (processed) return processed;
/*      */     
/* 1850 */     processed = fillAttribStatDetails(attrib, "retaliationPierce", pos, value, index);
/* 1851 */     if (processed) return processed;
/*      */     
/* 1853 */     processed = fillAttribStatDetails(attrib, "retaliationPierceRatio", pos, value, index);
/* 1854 */     if (processed) return processed;
/*      */     
/* 1856 */     processed = fillAttribStatDetails(attrib, "retaliationPoison", pos, value, index);
/* 1857 */     if (processed) return processed;
/*      */     
/* 1859 */     processed = fillAttribStatDetails(attrib, "retaliationSleep", pos, value, index);
/* 1860 */     if (processed) return processed;
/*      */     
/* 1862 */     processed = fillAttribStatDetails(attrib, "retaliationSlowAttackSpeed", pos, value, index);
/* 1863 */     if (processed) return processed;
/*      */     
/* 1865 */     processed = fillAttribStatDetails(attrib, "retaliationSlowBleeding", pos, value, index);
/* 1866 */     if (processed) return processed;
/*      */     
/* 1868 */     processed = fillAttribStatDetails(attrib, "retaliationSlowCold", pos, value, index);
/* 1869 */     if (processed) return processed;
/*      */     
/* 1871 */     processed = fillAttribStatDetails(attrib, "retaliationSlowDefensiveAbility", pos, value, index);
/* 1872 */     if (processed) return processed;
/*      */     
/* 1874 */     processed = fillAttribStatDetails(attrib, "retaliationSlowFire", pos, value, index);
/* 1875 */     if (processed) return processed;
/*      */     
/* 1877 */     processed = fillAttribStatDetails(attrib, "retaliationSlowLife", pos, value, index);
/* 1878 */     if (processed) return processed;
/*      */     
/* 1880 */     processed = fillAttribStatDetails(attrib, "retaliationSlowLifeLeach", pos, value, index);
/* 1881 */     if (processed) return processed;
/*      */     
/* 1883 */     processed = fillAttribStatDetails(attrib, "retaliationSlowLightning", pos, value, index);
/* 1884 */     if (processed) return processed;
/*      */     
/* 1886 */     processed = fillAttribStatDetails(attrib, "retaliationSlowManaLeach", pos, value, index);
/* 1887 */     if (processed) return processed;
/*      */     
/* 1889 */     processed = fillAttribStatDetails(attrib, "retaliationSlowOffensiveAbility", pos, value, index);
/* 1890 */     if (processed) return processed;
/*      */     
/* 1892 */     processed = fillAttribStatDetails(attrib, "retaliationSlowOffensiveReduction", pos, value, index);
/* 1893 */     if (processed) return processed;
/*      */     
/* 1895 */     processed = fillAttribStatDetails(attrib, "retaliationSlowPhysical", pos, value, index);
/* 1896 */     if (processed) return processed;
/*      */     
/* 1898 */     processed = fillAttribStatDetails(attrib, "retaliationSlowPoison", pos, value, index);
/* 1899 */     if (processed) return processed;
/*      */     
/* 1901 */     processed = fillAttribStatDetails(attrib, "retaliationSlowRunSpeed", pos, value, index);
/* 1902 */     if (processed) return processed;
/*      */     
/* 1904 */     processed = fillAttribStatDetails(attrib, "retaliationStun", pos, value, index);
/* 1905 */     if (processed) return processed;
/*      */     
/* 1907 */     processed = fillAttribStatDetails(attrib, "retaliationTotalDamage", pos, value, index);
/* 1908 */     if (processed) return processed;
/*      */     
/* 1910 */     processed = fillAttribStatDetails(attrib, "retaliationTrap", pos, value, index);
/* 1911 */     if (processed) return processed;
/*      */     
/* 1913 */     if (!processed) {
/* 1914 */       int i = parseInt(value);
/*      */       
/* 1916 */       logTag(attrib, i, this.records[pos]);
/*      */     } 
/*      */     
/* 1919 */     return processed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void logTag(String attrib, int value, ARZRecord record) {
/* 2018 */     if (value == 0)
/*      */       return; 
/* 2020 */     if (!attrib.startsWith("character") && 
/* 2021 */       !attrib.startsWith("defensive") && 
/* 2022 */       !attrib.startsWith("offensive") && 
/* 2023 */       !attrib.startsWith("retaliation")) {
/*      */       return;
/*      */     }
/*      */     
/* 2027 */     if (attrib.equals("characterBaseAttackSpeedTag") || attrib
/* 2028 */       .equals("characterAttributeEquations") || attrib
/* 2029 */       .equals("characterGenderProfile") || attrib
/* 2030 */       .equals("characterRacialProfile") || attrib
/* 2031 */       .equals("characterRunSpeed") || attrib
/* 2032 */       .equals("characterRunSpeedJitter") || attrib
/* 2033 */       .equals("characterSpellCastSpeed") || attrib
/* 2034 */       .equals("defensiveAbilityDamageFxPak") || attrib
/* 2035 */       .equals("defensiveManaBurn") || attrib
/* 2036 */       .equals("defensiveReductionDamageFxPak") || attrib
/* 2037 */       .equals("offensiveAbilityDamageFxPak") || attrib
/* 2038 */       .equals("offensiveReductionDamageFxPak")) {
/*      */       return;
/*      */     }
/*      */     
/* 2042 */     Object[] args = { attrib };
/* 2043 */     String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_UNKNOWN", args);
/* 2044 */     GDStashFrame.messageToList(msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fillEngineGame(String attrib, int pos, String value, int index) {
/* 2054 */     int i = 0;
/* 2055 */     boolean error = false;
/*      */     
/*      */     try {
/* 2058 */       i = parseInt(value);
/*      */     }
/* 2060 */     catch (NumberFormatException ex) {
/* 2061 */       i = 0;
/* 2062 */       error = true;
/*      */     } 
/*      */     
/* 2065 */     if (attrib.equals("factionAlternateNeutralTag")) {
/* 2066 */       this.records[pos].setFactionAltNeutralTag(value);
/*      */       
/* 2068 */       return true;
/*      */     } 
/*      */     
/* 2071 */     if (attrib.startsWith("factionValue")) {
/*      */       
/* 2073 */       String s = attrib.substring(12);
/* 2074 */       int idx = parseInt(s);
/*      */       
/* 2076 */       this.records[pos].addFactionReputationValue(idx, i);
/*      */       
/* 2078 */       return true;
/*      */     } 
/*      */     
/* 2081 */     if (attrib.startsWith("factionTag")) {
/*      */       
/* 2083 */       String s = attrib.substring(10);
/* 2084 */       int idx = parseInt(s);
/*      */       
/* 2086 */       this.records[pos].addFactionReputationStateTag(idx, value);
/*      */       
/* 2088 */       return true;
/*      */     } 
/*      */     
/* 2091 */     if (attrib.equals("playerDevotionCap")) {
/* 2092 */       if (error) {
/* 2093 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2094 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2096 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2098 */       this.records[pos].setDevotionCap(i);
/*      */       
/* 2100 */       return true;
/*      */     } 
/*      */     
/* 2103 */     if (attrib.equals("skillMasteryTierLevel")) {
/* 2104 */       this.records[pos].addMasteryTier(index, i);
/*      */       
/* 2106 */       return true;
/*      */     } 
/*      */     
/* 2109 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillFaction(String attrib, int pos, String value) {
/* 2113 */     if (attrib.equals("bountyEnabled")) {
/* 2114 */       this.records[pos].setFactionBountyEnabled(value.equals("1"));
/*      */       
/* 2116 */       return true;
/*      */     } 
/*      */     
/* 2119 */     if (attrib.startsWith("questEnabled")) {
/* 2120 */       this.records[pos].setFactionQuestEnabled(value.equals("1"));
/*      */       
/* 2122 */       return true;
/*      */     } 
/*      */     
/* 2125 */     if (attrib.startsWith("myFaction")) {
/* 2126 */       this.records[pos].setFactionTag(value);
/*      */       
/* 2128 */       return true;
/*      */     } 
/*      */     
/* 2131 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillMerchant(String attrib, int pos, String value) {
/* 2135 */     if (attrib.equals("factions")) {
/* 2136 */       this.records[pos].setMerchantFactionID(value);
/*      */       
/* 2138 */       return true;
/*      */     } 
/*      */     
/* 2141 */     if (attrib.startsWith("marketFileName")) {
/* 2142 */       this.records[pos].setMerchantTableSetID(value);
/*      */       
/* 2144 */       return true;
/*      */     } 
/*      */     
/* 2147 */     if (attrib.startsWith("friendlyNormalTable")) {
/* 2148 */       this.records[pos].addMerchantTableSetTableID(value);
/*      */       
/* 2150 */       return true;
/*      */     } 
/*      */     
/* 2153 */     if (attrib.startsWith("respectedNormalTable")) {
/* 2154 */       this.records[pos].addMerchantTableSetTableID(value);
/*      */       
/* 2156 */       return true;
/*      */     } 
/*      */     
/* 2159 */     if (attrib.startsWith("honoredNormalTable")) {
/* 2160 */       this.records[pos].addMerchantTableSetTableID(value);
/*      */       
/* 2162 */       return true;
/*      */     } 
/*      */     
/* 2165 */     if (attrib.startsWith("reveredNormalTable")) {
/* 2166 */       this.records[pos].addMerchantTableSetTableID(value);
/*      */       
/* 2168 */       return true;
/*      */     } 
/*      */     
/* 2171 */     if (attrib.startsWith("marketStaticItems")) {
/* 2172 */       this.records[pos].addMerchantTableItemID(value);
/*      */       
/* 2174 */       return true;
/*      */     } 
/*      */     
/* 2177 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillEnginePlayer(String attrib, int pos, String value, int index) throws GDParseException {
/* 2181 */     int i = 0;
/* 2182 */     boolean error = false;
/*      */     
/*      */     try {
/* 2185 */       i = parseInt(value);
/*      */     }
/* 2187 */     catch (NumberFormatException ex) {
/* 2188 */       i = 0;
/* 2189 */       error = true;
/*      */     } 
/*      */     
/* 2192 */     if (attrib.equals("characterDexterity")) {
/* 2193 */       if (error) {
/* 2194 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2195 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2197 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2199 */       this.records[pos].setPlayerBaseDex(i);
/*      */       
/* 2201 */       return true;
/*      */     } 
/*      */     
/* 2204 */     if (attrib.equals("characterIntelligence")) {
/* 2205 */       if (error) {
/* 2206 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2207 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2209 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2211 */       this.records[pos].setPlayerBaseInt(i);
/*      */       
/* 2213 */       return true;
/*      */     } 
/*      */     
/* 2216 */     if (attrib.equals("characterLife")) {
/* 2217 */       if (error) {
/* 2218 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2219 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2221 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2223 */       this.records[pos].setPlayerBaseLife(i);
/*      */       
/* 2225 */       return true;
/*      */     } 
/*      */     
/* 2228 */     if (attrib.equals("characterMana")) {
/* 2229 */       if (error) {
/* 2230 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2231 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2233 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2235 */       this.records[pos].setPlayerBaseMana(i);
/*      */       
/* 2237 */       return true;
/*      */     } 
/*      */     
/* 2240 */     if (attrib.equals("characterModifierPoints")) {
/* 2241 */       if (error) {
/* 2242 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2243 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2245 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2247 */       this.records[pos].addLevelStatPoints(i, index);
/*      */       
/* 2249 */       return true;
/*      */     } 
/*      */     
/* 2252 */     if (attrib.equals("characterStrength")) {
/* 2253 */       if (error) {
/* 2254 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2255 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2257 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2259 */       this.records[pos].setPlayerBaseStr(i);
/*      */       
/* 2261 */       return true;
/*      */     } 
/*      */     
/* 2264 */     if (attrib.equals("dexterityIncrement")) {
/* 2265 */       if (error) {
/* 2266 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2267 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2269 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2271 */       this.records[pos].setPlayerIncDex(i);
/*      */       
/* 2273 */       return true;
/*      */     } 
/*      */     
/* 2276 */     if (attrib.equals("experienceLevelEquation")) {
/*      */       
/* 2278 */       this.records[pos].setXPFormula(value.toUpperCase(GDConstants.LOCALE_US));
/*      */       
/* 2280 */       return true;
/*      */     } 
/*      */     
/* 2283 */     if (attrib.equals("intelligenceIncrement")) {
/* 2284 */       if (error) {
/* 2285 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2286 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2288 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2290 */       this.records[pos].setPlayerIncInt(i);
/*      */       
/* 2292 */       return true;
/*      */     } 
/*      */     
/* 2295 */     if (attrib.equals("lifeIncrement")) {
/* 2296 */       if (error) {
/* 2297 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2298 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2300 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2302 */       this.records[pos].setPlayerIncLife(i);
/*      */       
/* 2304 */       return true;
/*      */     } 
/*      */     
/* 2307 */     if (attrib.equals("manaIncrement")) {
/* 2308 */       if (error) {
/* 2309 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2310 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2312 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2314 */       this.records[pos].setPlayerIncMana(i);
/*      */       
/* 2316 */       return true;
/*      */     } 
/*      */     
/* 2319 */     if (attrib.equals("maxPlayerLevel")) {
/* 2320 */       if (error) {
/* 2321 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2322 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2324 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2326 */       this.records[pos].setPlayerMaxLevel(i);
/*      */       
/* 2328 */       return true;
/*      */     } 
/*      */     
/* 2331 */     if (attrib.equals("skillModifierPoints")) {
/* 2332 */       if (error) {
/* 2333 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2334 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2336 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2338 */       this.records[pos].addLevelSkillPoints(i, index);
/*      */       
/* 2340 */       return true;
/*      */     } 
/*      */     
/* 2343 */     if (attrib.startsWith("skillTree")) {
/* 2344 */       this.records[pos].addMastery(attrib, value);
/*      */       
/* 2346 */       return true;
/*      */     } 
/*      */     
/* 2349 */     if (attrib.equals("strengthIncrement")) {
/* 2350 */       if (error) {
/* 2351 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2352 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2354 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2356 */       this.records[pos].setPlayerIncStr(i);
/*      */       
/* 2358 */       return true;
/*      */     } 
/*      */     
/* 2361 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribConstellation(String attrib, int pos, String value) {
/* 2365 */     int i = 0;
/* 2366 */     boolean error = false;
/*      */     
/*      */     try {
/* 2369 */       i = parseInt(value);
/*      */     }
/* 2371 */     catch (NumberFormatException ex) {
/* 2372 */       i = 0;
/* 2373 */       error = true;
/*      */     } 
/*      */     
/* 2376 */     if (attrib.equals("constellationDisplayTag")) {
/* 2377 */       this.records[pos].setConstellationNameTag(value);
/*      */       
/* 2379 */       return true;
/*      */     } 
/*      */     
/* 2382 */     if (attrib.equals("constellationInfoTag")) {
/* 2383 */       this.records[pos].getConstellationInfoTag(value);
/*      */       
/* 2385 */       return true;
/*      */     } 
/*      */     
/* 2388 */     if (attrib.startsWith("affinityGivenName")) {
/* 2389 */       this.records[pos].addConstellationAffinityName(attrib, value);
/*      */       
/* 2391 */       return true;
/*      */     } 
/*      */     
/* 2394 */     if (attrib.startsWith("affinityRequiredName")) {
/* 2395 */       this.records[pos].addConstellationAffinityName(attrib, value);
/*      */       
/* 2397 */       return true;
/*      */     } 
/*      */     
/* 2400 */     if (attrib.startsWith("affinityGiven")) {
/* 2401 */       this.records[pos].addConstellationAffinityPoints(attrib, i);
/*      */       
/* 2403 */       return true;
/*      */     } 
/*      */     
/* 2406 */     if (attrib.startsWith("affinityRequired")) {
/* 2407 */       this.records[pos].addConstellationAffinityPoints(attrib, i);
/*      */       
/* 2409 */       return true;
/*      */     } 
/*      */     
/* 2412 */     if (attrib.startsWith("devotionButton")) {
/* 2413 */       this.records[pos].addConstellationStar(attrib, value);
/*      */       
/* 2415 */       return true;
/*      */     } 
/*      */     
/* 2418 */     if (attrib.equals("skillName")) {
/* 2419 */       this.records[pos].setButtonSkillID(value);
/*      */       
/* 2421 */       return true;
/*      */     } 
/*      */     
/* 2424 */     if (attrib.equals("bitmapPositionX")) {
/* 2425 */       this.records[pos].setPosX(i);
/*      */       
/* 2427 */       return true;
/*      */     } 
/*      */     
/* 2430 */     if (attrib.equals("bitmapPositionY")) {
/* 2431 */       this.records[pos].setPosY(i);
/*      */       
/* 2433 */       return true;
/*      */     } 
/*      */     
/* 2436 */     if (attrib.equals("skillOffsetX")) {
/* 2437 */       this.records[pos].setOffsetX(i);
/*      */       
/* 2439 */       return true;
/*      */     } 
/*      */     
/* 2442 */     if (attrib.equals("skillOffsetY")) {
/* 2443 */       this.records[pos].setOffsetY(i);
/*      */       
/* 2445 */       return true;
/*      */     } 
/*      */     
/* 2448 */     if (attrib.equals("isCircular")) {
/* 2449 */       this.records[pos].setCircularButton(value.equals("1"));
/*      */       
/* 2451 */       return true;
/*      */     } 
/*      */     
/* 2454 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribBitmap(String attrib, int pos, String value) {
/* 2458 */     int i = 0;
/* 2459 */     boolean error = false;
/*      */     
/*      */     try {
/* 2462 */       i = parseInt(value);
/*      */     }
/* 2464 */     catch (NumberFormatException ex) {
/* 2465 */       i = 0;
/* 2466 */       error = true;
/*      */     } 
/*      */     
/* 2469 */     if (attrib.equals("bitmapName") || attrib
/* 2470 */       .equals("bitmapFullName")) {
/* 2471 */       this.records[pos].setBitmapID(value);
/*      */       
/* 2473 */       return true;
/*      */     } 
/*      */     
/* 2476 */     if (attrib.equals("bitmapPositionX")) {
/* 2477 */       this.records[pos].setPosX(i);
/*      */       
/* 2479 */       return true;
/*      */     } 
/*      */     
/* 2482 */     if (attrib.equals("bitmapPositionY")) {
/* 2483 */       this.records[pos].setPosY(i);
/*      */       
/* 2485 */       return true;
/*      */     } 
/*      */     
/* 2488 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribClassTable(String attrib, int pos, String value) {
/* 2492 */     int i = 0;
/* 2493 */     boolean error = false;
/*      */     
/*      */     try {
/* 2496 */       i = parseInt(value);
/*      */     }
/* 2498 */     catch (NumberFormatException ex) {
/* 2499 */       i = 0;
/* 2500 */       error = true;
/*      */     } 
/*      */     
/* 2503 */     if (attrib.equals("masteryBar")) {
/* 2504 */       this.records[pos].setMasteryBarID(value);
/*      */       
/* 2506 */       return true;
/*      */     } 
/*      */     
/* 2509 */     if (attrib.equals("skillPaneBaseBitmap")) {
/* 2510 */       this.records[pos].setSkillPaneID(value);
/*      */       
/* 2512 */       return true;
/*      */     } 
/*      */     
/* 2515 */     if (attrib.equals("skillPaneMasteryBitmap")) {
/* 2516 */       this.records[pos].setMasteryBitmapID(value);
/*      */       
/* 2518 */       return true;
/*      */     } 
/*      */     
/* 2521 */     if (attrib.equals("skillTabTitle")) {
/* 2522 */       this.records[pos].setTitleTag(value);
/*      */       
/* 2524 */       return true;
/*      */     } 
/*      */     
/* 2527 */     if (attrib.equals("tabSkillButtons")) {
/* 2528 */       this.records[pos].addSkillButton(value);
/*      */       
/* 2530 */       return true;
/*      */     } 
/*      */     
/* 2533 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribSkillMaster(String attrib, int pos, String value) {
/* 2537 */     if (attrib.startsWith("skillCtrlPane")) {
/* 2538 */       this.records[pos].addSkillMaster(value);
/*      */       
/* 2540 */       return true;
/*      */     } 
/*      */     
/* 2543 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribShrine(String attrib, int pos, String value) {
/* 2547 */     if (attrib.equals("journalTag")) {
/* 2548 */       this.records[pos].setShrineName(value);
/*      */       
/* 2550 */       return true;
/*      */     } 
/*      */     
/* 2553 */     if (attrib.equals("normalDisabled")) {
/* 2554 */       this.records[pos].setShrineNormalDisabled(value.equals("1"));
/*      */       
/* 2556 */       return true;
/*      */     } 
/*      */     
/* 2559 */     if (attrib.equals("normalLocked")) {
/* 2560 */       this.records[pos].setShrineNormalLocked(value.equals("1"));
/*      */       
/* 2562 */       return true;
/*      */     } 
/*      */     
/* 2565 */     if (attrib.equals("epicDisabled")) {
/* 2566 */       this.records[pos].setShrineEpicDisabled(value.equals("1"));
/*      */       
/* 2568 */       return true;
/*      */     } 
/*      */     
/* 2571 */     if (attrib.equals("epicLocked")) {
/* 2572 */       this.records[pos].setShrineEpicLocked(value.equals("1"));
/*      */       
/* 2574 */       return true;
/*      */     } 
/*      */     
/* 2577 */     if (attrib.equals("legendaryDisabled")) {
/* 2578 */       this.records[pos].setShrineLegendaryDisabled(value.equals("1"));
/*      */       
/* 2580 */       return true;
/*      */     } 
/*      */     
/* 2583 */     if (attrib.equals("legendaryLocked")) {
/* 2584 */       this.records[pos].setShrineLegendaryLocked(value.equals("1"));
/*      */       
/* 2586 */       return true;
/*      */     } 
/*      */     
/* 2589 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribSkillTree(String attrib, int pos, String value) {
/* 2593 */     if (attrib.startsWith("skillName")) {
/* 2594 */       int i = 0;
/*      */       try {
/* 2596 */         i = parseInt(attrib.substring(9));
/*      */       }
/* 2598 */       catch (NumberFormatException ex) {
/* 2599 */         i = 0;
/*      */       } 
/*      */       
/* 2602 */       this.records[pos].addMasterySkill(value, i);
/*      */       
/* 2604 */       return true;
/*      */     } 
/*      */     
/* 2607 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribSkill(String attrib, int pos, String value, int index) {
/* 2611 */     int i = 0;
/* 2612 */     boolean error = false;
/*      */     
/*      */     try {
/* 2615 */       i = parseInt(value);
/*      */     }
/* 2617 */     catch (NumberFormatException ex) {
/* 2618 */       i = 0;
/* 2619 */       error = true;
/*      */     } 
/*      */     
/* 2622 */     if (attrib.equals("buffSkillName")) {
/* 2623 */       this.records[pos].setSkillBuffID(value);
/*      */       
/* 2625 */       return true;
/*      */     } 
/*      */     
/* 2628 */     if (attrib.equals("petSkillName")) {
/* 2629 */       this.records[pos].setPetBonusID(value);
/*      */       
/* 2631 */       return true;
/*      */     } 
/*      */     
/* 2634 */     if (attrib.equals("skillBaseDescription")) {
/* 2635 */       this.records[pos].setSkillDescription(value);
/*      */       
/* 2637 */       return true;
/*      */     } 
/*      */     
/* 2640 */     if (attrib.equals("skillDisplayName")) {
/* 2641 */       this.records[pos].setSkillNameTag(value);
/*      */       
/* 2643 */       return true;
/*      */     } 
/*      */     
/* 2646 */     if (attrib.equals("skillMaxLevel")) {
/* 2647 */       this.records[pos].setSkillMaxLevel(i);
/*      */       
/* 2649 */       return true;
/*      */     } 
/*      */     
/* 2652 */     if (attrib.equals("skillTier")) {
/* 2653 */       this.records[pos].setSkillTier(i);
/*      */       
/* 2655 */       return true;
/*      */     } 
/*      */     
/* 2658 */     if (attrib.equals("skillDownBitmapName")) {
/* 2659 */       this.records[pos].setSkillBitmapDownID(value);
/*      */       
/* 2661 */       return true;
/*      */     } 
/*      */     
/* 2664 */     if (attrib.equals("skillUpBitmapName")) {
/* 2665 */       this.records[pos].setSkillBitmapUpID(value);
/*      */       
/* 2667 */       return true;
/*      */     } 
/*      */     
/* 2670 */     if (attrib.equals("skillConnectionOff")) {
/* 2671 */       this.records[pos].addSkillConnectionOff(value, index);
/*      */       
/* 2673 */       return true;
/*      */     } 
/*      */     
/* 2676 */     if (attrib.equals("skillConnectionOn")) {
/* 2677 */       this.records[pos].addSkillConnectionOn(value, index);
/*      */       
/* 2679 */       return true;
/*      */     } 
/*      */     
/* 2682 */     if (attrib.equals("skillDependancyAll")) {
/* 2683 */       this.records[pos].setDependencyAll((i != 0));
/*      */       
/* 2685 */       return true;
/*      */     } 
/*      */     
/* 2688 */     if (attrib.equals("skillDependancy")) {
/* 2689 */       this.records[pos].addSkillDependency(value);
/*      */       
/* 2691 */       return true;
/*      */     } 
/*      */     
/* 2694 */     if (attrib.equals("skillExperienceLevels")) {
/* 2695 */       this.records[pos].addSkillXPLevel(value);
/*      */       
/* 2697 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 2701 */     if (attrib.equals("skillChargeLevel")) {
/* 2702 */       this.records[pos].setSkillLevel(i);
/*      */       
/* 2704 */       return true;
/*      */     } 
/*      */     
/* 2707 */     if (attrib.equals("skillChargeDuration")) {
/* 2708 */       this.records[pos].setSkillDuration(i);
/*      */       
/* 2710 */       return true;
/*      */     } 
/*      */     
/* 2713 */     if (attrib.equals("spawnObjects") || attrib
/* 2714 */       .equals("modSpawnObjects")) {
/* 2715 */       this.records[pos].addSpawnPet(index, value);
/*      */       
/* 2717 */       return true;
/*      */     } 
/*      */     
/* 2720 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribSkillBonus(String attrib, int pos, String value, int index) {
/* 2724 */     if (attrib.startsWith("augmentMasteryName") || attrib
/* 2725 */       .startsWith("augmentSkillName") || attrib
/* 2726 */       .equals("itemSkillName") || attrib
/* 2727 */       .equals("augmentAllLevel")) {
/*      */       
/* 2729 */       String s = null;
/* 2730 */       int type = 0;
/* 2731 */       int idx = -1;
/* 2732 */       String val = null;
/*      */       
/* 2734 */       if (attrib.startsWith("augmentMasteryName")) {
/* 2735 */         s = attrib.substring(18);
/*      */         
/* 2737 */         type = 1;
/* 2738 */         idx = parseInt(s);
/* 2739 */         val = value;
/*      */       } 
/*      */       
/* 2742 */       if (attrib.startsWith("augmentSkillName")) {
/* 2743 */         s = attrib.substring(16);
/*      */         
/* 2745 */         type = 2;
/* 2746 */         idx = parseInt(s);
/* 2747 */         val = value;
/*      */       } 
/*      */       
/* 2750 */       if (attrib.equals("itemSkillName")) {
/* 2751 */         type = 3;
/* 2752 */         idx = 1;
/* 2753 */         val = value;
/*      */       } 
/*      */       
/* 2756 */       if (attrib.equals("augmentAllLevel")) {
/* 2757 */         type = 4;
/* 2758 */         idx = 1;
/* 2759 */         val = null;
/*      */       } 
/*      */       
/* 2762 */       DBSkillBonus.addEntity((this.records[pos]).dbSkillBonuses, idx, type, val);
/*      */       
/* 2764 */       if (!attrib.equals("augmentAllLevel")) return true;
/*      */     
/*      */     } 
/* 2767 */     if (attrib.startsWith("augmentMasteryLevel") || attrib
/* 2768 */       .startsWith("augmentSkillLevel") || attrib
/* 2769 */       .equals("itemSkillLevel") || attrib
/* 2770 */       .equals("augmentAllLevel")) {
/*      */       
/* 2772 */       String s = null;
/* 2773 */       int type = 0;
/* 2774 */       int idx = -1;
/*      */       
/* 2776 */       if (attrib.startsWith("augmentMasteryLevel")) {
/* 2777 */         s = attrib.substring(19);
/*      */         
/* 2779 */         type = 1;
/* 2780 */         idx = parseInt(s);
/*      */       } 
/*      */       
/* 2783 */       if (attrib.startsWith("augmentSkillLevel")) {
/* 2784 */         s = attrib.substring(17);
/*      */         
/* 2786 */         type = 2;
/* 2787 */         idx = parseInt(s);
/*      */       } 
/*      */       
/* 2790 */       if (attrib.equals("itemSkillLevel")) {
/* 2791 */         type = 3;
/* 2792 */         idx = 1;
/*      */       } 
/*      */       
/* 2795 */       if (attrib.equals("augmentAllLevel")) {
/* 2796 */         type = 4;
/* 2797 */         idx = 1;
/*      */       } 
/*      */       
/* 2800 */       int iVal = parseInt(value);
/*      */       
/* 2802 */       DBSkillBonus.addValue((this.records[pos]).dbSkillBonuses, idx, type, iVal, index);
/*      */       
/* 2804 */       return true;
/*      */     } 
/*      */     
/* 2807 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribPet(String attrib, int pos, String value, int index) {
/* 2811 */     if (attrib.equals("charLevel")) {
/* 2812 */       this.records[pos].setPetFormulaLevel(value);
/*      */       
/* 2814 */       return true;
/*      */     } 
/*      */     
/* 2817 */     if (attrib.equals("characterAttributeEquations")) {
/* 2818 */       this.records[pos].setPetBioID(value);
/*      */       
/* 2820 */       return true;
/*      */     } 
/*      */     
/* 2823 */     if (attrib.equals("description")) {
/* 2824 */       this.records[pos].setPetNameTag(value);
/*      */       
/* 2826 */       return true;
/*      */     } 
/*      */     
/* 2829 */     if (attrib.equals("dyingSkillName")) {
/* 2830 */       this.records[pos].setPetDieSkillID(value);
/*      */       
/* 2832 */       return true;
/*      */     } 
/*      */     
/* 2835 */     if (attrib.startsWith("skillName")) {
/*      */       
/* 2837 */       String s = attrib.substring(9);
/* 2838 */       int i = parseInt(s);
/*      */       
/* 2840 */       this.records[pos].addPetSkillsSkillID(i, value);
/*      */       
/* 2842 */       return true;
/*      */     } 
/*      */     
/* 2845 */     if (attrib.startsWith("skillLevel")) {
/*      */       
/* 2847 */       String s = attrib.substring(10);
/* 2848 */       int i = parseInt(s);
/*      */       
/* 2850 */       this.records[pos].addPetSkillsFormulaLevel(i, value);
/*      */       
/* 2852 */       return true;
/*      */     } 
/*      */     
/* 2855 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribPetBio(String attrib, int pos, String value, int index) {
/* 2859 */     if (attrib.equals("characterLife")) {
/* 2860 */       this.records[pos].setBioFormulaLife(value);
/*      */       
/* 2862 */       return true;
/*      */     } 
/*      */     
/* 2865 */     if (attrib.equals("characterMana")) {
/* 2866 */       this.records[pos].setBioFormulaMana(value);
/*      */       
/* 2868 */       return true;
/*      */     } 
/*      */     
/* 2871 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribController(String attrib, int pos, String value) {
/* 2875 */     int i = 0;
/* 2876 */     boolean error = false;
/*      */     
/*      */     try {
/* 2879 */       i = parseInt(value);
/*      */     }
/* 2881 */     catch (NumberFormatException ex) {
/* 2882 */       i = 0;
/* 2883 */       error = true;
/*      */     } 
/*      */     
/* 2886 */     if (attrib.equals("chanceToRun")) {
/* 2887 */       this.records[pos].setTriggerChance(i);
/*      */       
/* 2889 */       return true;
/*      */     } 
/*      */     
/* 2892 */     if (attrib.equals("triggerType")) {
/* 2893 */       this.records[pos].setTriggerType(value);
/*      */       
/* 2895 */       return true;
/*      */     } 
/*      */     
/* 2898 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribItemSet(String attrib, int pos, String value) {
/* 2902 */     if (attrib.equals("setDescription")) {
/* 2903 */       this.records[pos].setItemSetDescriptionTag(value);
/*      */       
/* 2905 */       return true;
/*      */     } 
/*      */     
/* 2908 */     if (attrib.equals("setMembers")) {
/* 2909 */       this.records[pos].addItemSetItemID(value);
/*      */       
/* 2911 */       return true;
/*      */     } 
/*      */     
/* 2914 */     if (attrib.equals("setName")) {
/* 2915 */       this.records[pos].setItemSetNameTag(value);
/*      */       
/* 2917 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2922 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribLootTable(String attrib, int pos, String value) {
/* 2926 */     if (attrib.equals("bothPrefixSuffix")) {
/* 2927 */       this.records[pos].setTableNormalPrefixSuffix((parseInt(value) > 0));
/*      */       
/* 2929 */       return true;
/*      */     } 
/*      */     
/* 2932 */     if (attrib.equals("normalPrefixRareSuffix")) {
/* 2933 */       this.records[pos].setTableNormalPrefixRareSuffix((parseInt(value) > 0));
/*      */       
/* 2935 */       return true;
/*      */     } 
/*      */     
/* 2938 */     if (attrib.equals("rarePrefixNormalSuffix")) {
/* 2939 */       this.records[pos].setTableRarePrefixNormalSuffix((parseInt(value) > 0));
/*      */       
/* 2941 */       return true;
/*      */     } 
/*      */     
/* 2944 */     if (attrib.equals("rareBothPrefixSuffix")) {
/* 2945 */       this.records[pos].setTableRarePrefixSuffix((parseInt(value) > 0));
/*      */       
/* 2947 */       return true;
/*      */     } 
/*      */     
/* 2950 */     if (attrib.startsWith("lootName")) {
/* 2951 */       this.records[pos].addTableItemID(value);
/*      */       
/* 2953 */       return true;
/*      */     } 
/*      */     
/* 2956 */     if (attrib.startsWith("prefixTableLevelMax") || attrib
/* 2957 */       .startsWith("suffixTableLevelMax") || attrib
/* 2958 */       .startsWith("rarePrefixTableLevelMax") || attrib
/* 2959 */       .startsWith("rareSuffixTableLevelMax")) {
/* 2960 */       String search = "TableLevelMax";
/* 2961 */       int len = search.length();
/* 2962 */       int p = attrib.indexOf(search);
/*      */       
/* 2964 */       String pre = attrib.substring(0, p);
/* 2965 */       String num = attrib.substring(p + len);
/* 2966 */       int i = parseInt(num);
/*      */       
/* 2968 */       int type = 0;
/*      */       
/* 2970 */       if (pre.equals("prefix")) type = 1; 
/* 2971 */       if (pre.equals("suffix")) type = 2; 
/* 2972 */       if (pre.equals("rarePrefix")) type = 3; 
/* 2973 */       if (pre.equals("rareSuffix")) type = 4;
/*      */       
/* 2975 */       int iVal = parseInt(value);
/*      */       
/* 2977 */       this.records[pos].addTableMaxLevel(i, type, iVal);
/*      */       
/* 2979 */       return true;
/*      */     } 
/*      */     
/* 2982 */     if (attrib.startsWith("prefixTableLevelMin") || attrib
/* 2983 */       .startsWith("suffixTableLevelMin") || attrib
/* 2984 */       .startsWith("rarePrefixTableLevelMin") || attrib
/* 2985 */       .startsWith("rareSuffixTableLevelMin")) {
/* 2986 */       String search = "TableLevelMin";
/* 2987 */       int len = search.length();
/* 2988 */       int p = attrib.indexOf(search);
/*      */       
/* 2990 */       String pre = attrib.substring(0, p);
/* 2991 */       String num = attrib.substring(p + len);
/* 2992 */       int i = parseInt(num);
/*      */       
/* 2994 */       int type = 0;
/*      */       
/* 2996 */       if (pre.equals("prefix")) type = 1; 
/* 2997 */       if (pre.equals("suffix")) type = 2; 
/* 2998 */       if (pre.equals("rarePrefix")) type = 3; 
/* 2999 */       if (pre.equals("rareSuffix")) type = 4;
/*      */       
/* 3001 */       int iVal = parseInt(value);
/*      */       
/* 3003 */       this.records[pos].addTableMinLevel(i, type, iVal);
/*      */       
/* 3005 */       return true;
/*      */     } 
/*      */     
/* 3008 */     if (attrib.startsWith("prefixTableName") || attrib
/* 3009 */       .startsWith("suffixTableName") || attrib
/* 3010 */       .startsWith("rarePrefixTableName") || attrib
/* 3011 */       .startsWith("rareSuffixTableName")) {
/* 3012 */       String search = "TableName";
/* 3013 */       int len = search.length();
/* 3014 */       int p = attrib.indexOf(search);
/*      */       
/* 3016 */       String pre = attrib.substring(0, p);
/* 3017 */       String num = attrib.substring(p + len);
/* 3018 */       int i = parseInt(num);
/*      */       
/* 3020 */       int type = 0;
/*      */       
/* 3022 */       if (pre.equals("prefix")) type = 1; 
/* 3023 */       if (pre.equals("suffix")) type = 2; 
/* 3024 */       if (pre.equals("rarePrefix")) type = 3; 
/* 3025 */       if (pre.equals("rareSuffix")) type = 4;
/*      */       
/* 3027 */       this.records[pos].addTableAffixSetID(i, type, value);
/*      */       
/* 3029 */       return true;
/*      */     } 
/*      */     
/* 3032 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribLootTableSet(String attrib, int pos, String value) {
/* 3036 */     if (attrib.equals("levels")) {
/* 3037 */       int iVal = parseInt(value);
/*      */       
/* 3039 */       this.records[pos].addTableSetMinLevel(iVal);
/*      */       
/* 3041 */       return true;
/*      */     } 
/*      */     
/* 3044 */     if (attrib.equals("records")) {
/* 3045 */       this.records[pos].addTableSetTableID(value);
/*      */       
/* 3047 */       return true;
/*      */     } 
/*      */     
/* 3050 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribFormula(String attrib, int pos, String value) {
/* 3054 */     if (attrib.equals("armorCostEquation") || attrib
/* 3055 */       .equals("jewelryCostEquation") || attrib
/* 3056 */       .equals("offhandCostEquation") || attrib
/* 3057 */       .equals("shieldCostEquation") || attrib
/* 3058 */       .equals("weaponCostEquation") || attrib
/* 3059 */       .equals("weaponMelee2hCostEquation") || attrib
/* 3060 */       .equals("weaponRangedCostEquation") || attrib
/* 3061 */       .equals("weaponRanged2hCostEquation") || attrib
/* 3062 */       .equals("daggerDexterityEquation") || attrib
/* 3063 */       .equals("ranged1hDexterityEquation") || attrib
/* 3064 */       .equals("ranged2hDexterityEquation") || attrib
/* 3065 */       .equals("swordDexterityEquation") || attrib
/* 3066 */       .equals("amuletIntelligenceEquation") || attrib
/* 3067 */       .equals("chestIntelligenceEquation") || attrib
/* 3068 */       .equals("daggerIntelligenceEquation") || attrib
/* 3069 */       .equals("headIntelligenceEquation") || attrib
/* 3070 */       .equals("offhandIntelligenceEquation") || attrib
/* 3071 */       .equals("ringIntelligenceEquation") || attrib
/* 3072 */       .equals("scepterIntelligenceEquation") || attrib
/* 3073 */       .equals("axeStrengthEquation") || attrib
/* 3074 */       .equals("chestStrengthEquation") || attrib
/* 3075 */       .equals("feetStrengthEquation") || attrib
/* 3076 */       .equals("handsStrengthEquation") || attrib
/* 3077 */       .equals("headStrengthEquation") || attrib
/* 3078 */       .equals("legsStrengthEquation") || attrib
/* 3079 */       .equals("maceStrengthEquation") || attrib
/* 3080 */       .equals("melee2hStrengthEquation") || attrib
/* 3081 */       .equals("scepterStrengthEquation") || attrib
/* 3082 */       .equals("shieldStrengthEquation") || attrib
/* 3083 */       .equals("shouldersStrengthEquation") || attrib
/* 3084 */       .equals("waistStrengthEquation")) {
/*      */       
/* 3086 */       this.records[pos].addFormulaSetFormula(attrib, value.toUpperCase(GDConstants.LOCALE_US));
/*      */       
/* 3088 */       return true;
/*      */     } 
/*      */     
/* 3091 */     return false;
/*      */   }
/*      */   
/*      */   public static int getGenderCode(String s) {
/* 3095 */     int code = -1;
/*      */     
/* 3097 */     if (s.equals("[ms]")) code = 0; 
/* 3098 */     if (s.equals("[mp]")) code = 3; 
/* 3099 */     if (s.equals("[fs]")) code = 1; 
/* 3100 */     if (s.equals("[fp]")) code = 4; 
/* 3101 */     if (s.equals("[ns]")) code = 2; 
/* 3102 */     if (s.equals("[np]")) code = 5;
/*      */     
/* 3104 */     return code;
/*      */   }
/*      */   
/*      */   public static String[] getGenderTexts(String text) {
/* 3108 */     String[] genders = new String[6];
/*      */     
/* 3110 */     if (text == null) return genders;
/*      */     
/* 3112 */     String remainder = text;
/* 3113 */     String gender = null;
/* 3114 */     String value = null;
/* 3115 */     int code = -1;
/*      */     
/* 3117 */     if (remainder.startsWith("|"))
/*      */     {
/*      */ 
/*      */       
/* 3121 */       remainder = remainder.substring(2);
/*      */     }
/*      */     
/* 3124 */     if (remainder.startsWith("$"))
/*      */     {
/*      */ 
/*      */       
/* 3128 */       remainder = remainder.substring(1);
/*      */     }
/*      */     
/* 3131 */     while (remainder != null) {
/* 3132 */       int pos = remainder.indexOf("[");
/* 3133 */       if (pos != -1) {
/*      */         
/* 3135 */         gender = remainder.substring(pos, pos + 4);
/* 3136 */         remainder = remainder.substring(pos + 4);
/*      */         
/* 3138 */         pos = remainder.indexOf("[");
/* 3139 */         if (pos != -1) {
/* 3140 */           value = remainder.substring(0, pos);
/* 3141 */           remainder = remainder.substring(pos);
/*      */         } else {
/* 3143 */           value = remainder;
/* 3144 */           remainder = null;
/*      */         } 
/*      */         
/* 3147 */         code = getGenderCode(gender);
/* 3148 */         if (code == -1) code = 0;
/*      */         
/* 3150 */         genders[code] = value;
/*      */         
/*      */         continue;
/*      */       } 
/* 3154 */       value = remainder;
/* 3155 */       remainder = null;
/*      */ 
/*      */ 
/*      */       
/* 3159 */       genders[0] = value;
/* 3160 */       genders[1] = value;
/* 3161 */       genders[2] = value;
/* 3162 */       genders[3] = value;
/* 3163 */       genders[4] = value;
/* 3164 */       genders[5] = value;
/*      */     } 
/*      */ 
/*      */     
/* 3168 */     return genders;
/*      */   }
/*      */   
/*      */   private boolean resolveSkillName(ARZRecord record) {
/* 3172 */     String skill = null;
/*      */     
/* 3174 */     if (record.getPetBonusID() != null) skill = record.getPetBonusID(); 
/* 3175 */     if (record.getSkillBuffID() != null) skill = record.getSkillBuffID();
/*      */     
/* 3177 */     if (skill == null) return false;
/*      */     
/* 3179 */     for (int i = 0; i < this.records.length; ) {
/* 3180 */       if (this.records[i] == null || 
/* 3181 */         this.records[i].getSkillNameTag() == null || 
/* 3182 */         !skill.equals(this.records[i].getFileName())) {
/*      */         i++; continue;
/* 3184 */       }  record.setSkillNameTag(this.records[i].getSkillNameTag());
/*      */       
/* 3186 */       return true;
/*      */     } 
/*      */     
/* 3189 */     return false;
/*      */   }
/*      */   
/*      */   private void resolveGrantedSkillName(ARZRecord record) {
/* 3193 */     for (DBSkillBonus bonus : record.dbSkillBonuses) {
/* 3194 */       if (bonus.getEntity() == null)
/*      */         continue;  int i;
/* 3196 */       for (i = 0; i < this.records.length; i++) {
/* 3197 */         if (this.records[i] != null)
/*      */         {
/* 3199 */           if (this.records[i].getSkillName() != null || this.records[i]
/* 3200 */             .getSkillBuffID() != null || this.records[i]
/* 3201 */             .getPetBonusID() != null)
/*      */           {
/*      */ 
/*      */             
/* 3205 */             if (bonus.getEntity().equals(this.records[i].getFileName())) {
/*      */ 
/*      */ 
/*      */               
/* 3209 */               if (this.records[i].getSkillName() != null) {
/* 3210 */                 bonus.setEntityName(this.records[i].getSkillName());
/*      */               }
/*      */               break;
/*      */             } 
/*      */           }
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void resolveDevotionFlag(ARZRecord record) {
/* 3221 */     for (int i = 0; i < this.records.length; i++) {
/* 3222 */       if (this.records[i] != null)
/*      */       {
/*      */ 
/*      */         
/* 3226 */         if (record.getSkillBuffID().equals(this.records[i].getFileName())) {
/*      */           
/* 3228 */           record.setDevotion(this.records[i].isDevotion());
/*      */           break;
/*      */         }  } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void resolveBonusIncrementFlag(ARZRecord record) {
/* 3235 */     for (DBSkillBonus bonus : record.dbSkillBonuses) {
/* 3236 */       if (bonus.getEntity() == null || 
/* 3237 */         bonus.getValue() < 1)
/*      */         continue;  int i;
/* 3239 */       for (i = 0; i < this.records.length; i++) {
/* 3240 */         if (this.records[i] != null)
/*      */         {
/* 3242 */           if (bonus.getEntity().equals(this.records[i].getFileName())) {
/*      */             
/* 3244 */             this.records[i].setSkillBonusIncrement(true);
/*      */             break;
/*      */           }  } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void resolveModifiedSkillFlag(ARZRecord record) {
/* 3252 */     for (DBSkillModifier modif : record.getSkillModifierList()) {
/* 3253 */       if (modif.getSkillID() == null)
/*      */         continue;  int i;
/* 3255 */       for (i = 0; i < this.records.length; i++) {
/* 3256 */         if (this.records[i] != null)
/*      */         {
/* 3258 */           if (modif.getSkillID().equals(this.records[i].getFileName())) {
/*      */             
/* 3260 */             this.records[i].setSkillModified(true);
/*      */             break;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resolveAttrib() {
/* 3498 */     for (int i = 0; i < this.records.length; i++) {
/* 3499 */       if (this.records[i] != null) {
/*      */ 
/*      */         
/* 3502 */         if (this.records[i].getItemSetID() != null) {
/* 3503 */           this.records[i].setItemSetNameTag(
/* 3504 */               getAttribValue(this.records[i].getItemSetID(), "itemSetName"));
/*      */         }
/*      */ 
/*      */         
/* 3508 */         if (this.records[i].getItemSkillLevelFormula() != null) {
/* 3509 */           this.records[i].processItemSkillLevelFormula();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 3514 */     boolean changed = true;
/* 3515 */     while (changed) {
/* 3516 */       changed = false;
/*      */       
/* 3518 */       for (int k = 0; k < this.records.length; k++) {
/* 3519 */         if (this.records[k] != null)
/*      */         {
/* 3521 */           if (this.records[k].isSkill())
/*      */           {
/*      */             
/* 3524 */             if (this.records[k].getSkillNameTag() == null) {
/* 3525 */               boolean b = resolveSkillName(this.records[k]);
/*      */               
/* 3527 */               changed = (changed || b);
/*      */             } 
/*      */           }
/*      */         }
/*      */       } 
/*      */     } 
/*      */     int j;
/* 3534 */     for (j = 0; j < this.records.length; j++) {
/* 3535 */       if (this.records[j] != null)
/*      */       {
/* 3537 */         if ((this.records[j]).dbSkillBonuses != null && 
/* 3538 */           !(this.records[j]).dbSkillBonuses.isEmpty()) {
/* 3539 */           resolveGrantedSkillName(this.records[j]);
/*      */         }
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3550 */     for (j = 0; j < this.records.length; j++) {
/* 3551 */       if (this.records[j] != null)
/*      */       {
/* 3553 */         if (this.records[j].getFileName().startsWith("records/skills/") && 
/* 3554 */           this.records[j].getSkillBuffID() != null) {
/* 3555 */           resolveDevotionFlag(this.records[j]);
/*      */         }
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private String getAttribValue(String filename, String attrib) {
/* 3562 */     String value = null;
/*      */     int i;
/* 3564 */     for (i = 0; i < this.records.length; i++) {
/* 3565 */       if (this.records[i] != null)
/*      */       {
/* 3567 */         if (this.records[i].getFileName().equals(filename)) {
/* 3568 */           value = this.records[i].getItemSetNameTag();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3586 */     return value;
/*      */   }
/*      */   
/*      */   public boolean insertData(boolean suppressTagWarning) {
/* 3590 */     return GDDBData.insertData(this.records, suppressTagWarning);
/*      */   }
/*      */   
/*      */   public void extractRecords(String dir) {
/*      */     try {
/* 3595 */       File file = new File(this.filename);
/*      */       
/* 3597 */       if (!file.exists()) {
/* 3598 */         Object[] args = { this.filename };
/* 3599 */         String s = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*      */         
/* 3601 */         throw new FileNotFoundException(s);
/*      */       } 
/*      */       
/* 3604 */       if (!file.isFile()) {
/* 3605 */         Object[] args = { this.filename };
/* 3606 */         String s = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*      */         
/* 3608 */         throw new FileNotFoundException(s);
/*      */       } 
/*      */       
/* 3611 */       if (!file.canRead()) {
/* 3612 */         Object[] args = { this.filename };
/* 3613 */         String s = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_CANNOT_READ", args);
/*      */         
/* 3615 */         throw new IOException(s);
/*      */       } 
/*      */       
/* 3618 */       GDBuffer buffer = new GDRandomAccessBuffer(file);
/*      */       try {
/* 3620 */         this.header = getHeader(buffer);
/* 3621 */         this.strings = getStrings(buffer);
/* 3622 */         this.records = getRecords(buffer);
/*      */         
/* 3624 */         recordsToText(buffer, dir);
/*      */       }
/* 3626 */       catch (IOException ex) {
/* 3627 */         throw ex;
/*      */       } finally {
/*      */         
/* 3630 */         buffer.close();
/*      */         
/* 3632 */         buffer = null;
/*      */       }
/*      */     
/* 3635 */     } catch (Exception ex) {
/* 3636 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 3639 */     if (GDMsgLogger.severeErrorsInLog()) {
/* 3640 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_DB_EXTRACT_FAIL"));
/*      */     }
/*      */   }
/*      */   
/*      */   private void recordsToText(GDBuffer buffer, String dir) throws IOException, GDParseException {
/* 3645 */     NumberFormat formatter = new DecimalFormat("#0.000000");
/* 3646 */     LZ4Factory factory = LZ4Factory.fastestInstance();
/* 3647 */     LZ4FastDecompressor decomp = factory.fastDecompressor();
/*      */     int i;
/* 3649 */     for (i = 0; i < this.records.length; i++) {
/* 3650 */       if (this.records[i] != null) {
/*      */         
/* 3652 */         this.records[i].setFileName((this.strings[(this.records[i]).strID]).str);
/*      */         
/* 3654 */         byte[] bComp = buffer.getByteArray(((this.records[i]).offset + 24), (this.records[i]).len_comp);
/*      */ 
/*      */ 
/*      */         
/* 3658 */         byte[] bDecomp = new byte[(this.records[i]).len_decomp];
/*      */         
/* 3660 */         decomp.decompress(bComp, bDecomp);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3670 */         String text = "";
/* 3671 */         String value = "";
/*      */ 
/*      */ 
/*      */         
/* 3675 */         int offset = 0;
/*      */         
/*      */         try {
/* 3678 */           while (offset < bDecomp.length) {
/* 3679 */             int typeOffset = offset;
/* 3680 */             short varType = GDReader.getUShort(bDecomp, offset);
/* 3681 */             offset += 2;
/*      */             
/* 3683 */             short count = GDReader.getUShort(bDecomp, offset);
/* 3684 */             offset += 2;
/*      */             
/* 3686 */             int strIdx = GDReader.getUInt(bDecomp, offset);
/* 3687 */             offset += 4;
/*      */             
/* 3689 */             String tag = (this.strings[strIdx]).str + ",";
/* 3690 */             String values = "";
/*      */             int j;
/* 3692 */             for (j = 0; j < count; j++) {
/* 3693 */               int valInt; float valFloat; int valStrIdx; boolean valBool; Object[] args; String msg; switch (varType) {
/*      */                 case 0:
/* 3695 */                   valInt = GDReader.getInt(bDecomp, offset);
/* 3696 */                   value = String.valueOf(valInt);
/*      */                   break;
/*      */                 
/*      */                 case 1:
/* 3700 */                   valFloat = GDReader.getFloat(bDecomp, offset);
/* 3701 */                   value = formatter.format(valFloat);
/*      */                   break;
/*      */                 
/*      */                 case 2:
/* 3705 */                   valStrIdx = GDReader.getUInt(bDecomp, offset);
/* 3706 */                   value = (this.strings[valStrIdx]).str;
/*      */                   break;
/*      */                 
/*      */                 case 3:
/* 3710 */                   valBool = GDReader.getBool(bDecomp, offset);
/* 3711 */                   if (valBool) { value = "1"; break; }  value = "0";
/*      */                   break;
/*      */                 
/*      */                 default:
/* 3715 */                   args = new Object[] { this.records[i].getFileName(), (this.strings[strIdx]).str };
/* 3716 */                   msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_UNKNOWN_VAR_TYPE", args);
/*      */                   
/* 3718 */                   throw new GDParseException(msg, typeOffset);
/*      */               } 
/*      */               
/* 3721 */               offset += 4;
/*      */               
/* 3723 */               values = values + value;
/*      */               
/* 3725 */               if (j == count - 1) {
/* 3726 */                 values = values + ",";
/*      */               } else {
/* 3728 */                 values = values + ";";
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3755 */             if (!values.equals("0,") && 
/* 3756 */               !values.equals("0.000000,")) {
/* 3757 */               text = text + tag + values + GDConstants.LINE_SEPARATOR;
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 3764 */         catch (GDParseException ex) {
/* 3765 */           GDMsgLogger.addError(ex);
/*      */           
/* 3767 */           (this.records[i]).error = true;
/*      */           
/* 3769 */           int pos = this.records[i].getFileName().lastIndexOf(".");
/* 3770 */           if (pos != -1) {
/* 3771 */             this.records[i].setFileName(this.records[i].getFileName().substring(0, pos));
/*      */           }
/*      */           
/* 3774 */           this.records[i].setFileName(this.records[i].getFileName() + ".err");
/*      */         } 
/*      */         
/* 3777 */         String osName = GDWriter.getOSFilePath(this.records[i].getFileName());
/*      */         
/* 3779 */         String newName = dir + GDConstants.FILE_SEPARATOR + osName;
/*      */         
/*      */         try {
/* 3782 */           GDWriter.write(newName, text);
/*      */         }
/* 3784 */         catch (Exception ex) {
/* 3785 */           GDMsgLogger.addError(ex);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   public static void convertTextures(String dir) {
/*      */     try {
/* 3792 */       File file = new File(dir);
/*      */       
/* 3794 */       if (!file.exists()) {
/*      */         return;
/*      */       }
/*      */       
/* 3798 */       if (file.isDirectory()) {
/* 3799 */         File[] files = file.listFiles();
/*      */         
/* 3801 */         for (int i = 0; i < files.length; i++) {
/* 3802 */           convertTextures(files[i].getCanonicalPath());
/*      */         }
/*      */       } else {
/*      */         
/* 3806 */         String fn = file.getCanonicalPath();
/*      */         
/*      */         try {
/* 3809 */           if (fn.endsWith(".tex")) {
/* 3810 */             String pngName = fn.substring(0, fn.length() - 4) + ".png";
/*      */             
/* 3812 */             BufferedImage image = null;
/*      */             try {
/* 3814 */               image = DDSLoader.load(fn);
/*      */             }
/* 3816 */             catch (Exception ex) {
/* 3817 */               image = null;
/*      */             } 
/*      */             
/* 3820 */             if (image != null) {
/* 3821 */               File fImg = new File(pngName);
/* 3822 */               ImageIO.write(image, "PNG", fImg);
/*      */             }
/*      */           
/*      */           } 
/* 3826 */         } catch (IOException iOException) {}
/*      */       }
/*      */     
/* 3829 */     } catch (Exception exception) {}
/*      */   }
/*      */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\file\ARZDecompress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */