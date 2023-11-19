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
/*  541 */       processed = fillAttribBitmap(attrib, pos, value);
/*  542 */       if (processed)
/*      */         return; 
/*      */     } 
/*  545 */     if (this.records[pos].isInventoryGrid()) {
/*  546 */       processed = fillAttribInventoryGrid(attrib, pos, value);
/*  547 */       if (processed)
/*      */         return; 
/*      */     } 
/*  550 */     if (this.records[pos].isCaravanWindow()) {
/*  551 */       processed = fillAttribCaravanWindow(attrib, pos, value);
/*  552 */       if (processed)
/*      */         return; 
/*      */     } 
/*  555 */     if (this.records[pos].isClassTable()) {
/*  556 */       processed = fillAttribClassTable(attrib, pos, value);
/*  557 */       if (processed)
/*      */         return; 
/*      */     } 
/*  560 */     if (this.records[pos].isSkillMaster()) {
/*  561 */       processed = fillAttribSkillMaster(attrib, pos, value);
/*  562 */       if (processed)
/*      */         return; 
/*      */     } 
/*  565 */     if (this.records[pos].isShrine()) {
/*      */       
/*  567 */       processed = fillAttribShrine(attrib, pos, value);
/*  568 */       if (processed)
/*      */         return; 
/*      */     } 
/*  571 */     if (this.records[pos].isSkillTree()) {
/*  572 */       processed = fillAttribSkillTree(attrib, pos, value);
/*  573 */       if (processed)
/*      */         return; 
/*      */     } 
/*  576 */     if (this.records[pos].isSkill()) {
/*      */       
/*  578 */       processed = fillAttribSkill(attrib, pos, value, index);
/*  579 */       if (processed)
/*      */         return; 
/*      */     } 
/*  582 */     if (this.records[pos].isPet()) {
/*      */       
/*  584 */       processed = fillAttribPet(attrib, pos, value, index);
/*  585 */       if (processed)
/*      */         return; 
/*      */     } 
/*  588 */     if (this.records[pos].isPetBio()) {
/*      */       
/*  590 */       processed = fillAttribPetBio(attrib, pos, value, index);
/*  591 */       if (processed)
/*      */         return; 
/*      */     } 
/*  594 */     if (this.records[pos].isController()) {
/*      */       
/*  596 */       processed = fillAttribController(attrib, pos, value);
/*  597 */       if (processed) {
/*      */         return;
/*      */       }
/*      */     } 
/*  601 */     if (this.records[pos].isAffix()) {
/*      */       
/*  603 */       processed = fillAttribAffix(attrib, pos, value);
/*  604 */       if (processed)
/*      */         return; 
/*      */     } 
/*  607 */     if (this.records[pos].isAffixSet()) {
/*      */       
/*  609 */       processed = fillAttribAffixSet(attrib, pos, value);
/*  610 */       if (processed) {
/*      */         return;
/*      */       }
/*      */     } 
/*  614 */     if (this.records[pos].isItemSet()) {
/*      */       
/*  616 */       processed = fillAttribItemSet(attrib, pos, value);
/*  617 */       if (processed) {
/*      */         return;
/*      */       }
/*      */     } 
/*  621 */     if (this.records[pos].isLootTable()) {
/*      */       
/*  623 */       processed = fillAttribLootTable(attrib, pos, value);
/*  624 */       if (processed)
/*      */         return; 
/*      */     } 
/*  627 */     if (this.records[pos].isLootTableSet()) {
/*      */       
/*  629 */       processed = fillAttribLootTableSet(attrib, pos, value);
/*  630 */       if (processed) {
/*      */         return;
/*      */       }
/*      */     } 
/*  634 */     if (this.records[pos].isFormulaSet()) {
/*      */       
/*  636 */       processed = fillAttribFormula(attrib, pos, value);
/*  637 */       if (processed)
/*      */         return; 
/*      */     } 
/*  640 */     processed = fillAttribItem(attrib, pos, value);
/*  641 */     if (processed)
/*      */       return; 
/*  643 */     processed = fillAttribStat(attrib, pos, value, index);
/*  644 */     if (processed)
/*      */       return; 
/*  646 */     processed = fillAttribSkillBonus(attrib, pos, value, index);
/*  647 */     if (processed) {
/*      */       return;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean fillAttribAffix(String attrib, int pos, String value) {
/*  654 */     if (attrib.equals("lootRandomizerCost")) {
/*  655 */       int iVal = 0;
/*      */       try {
/*  657 */         iVal = Integer.parseInt(value);
/*      */       }
/*  659 */       catch (NumberFormatException ex) {
/*  660 */         iVal = 0;
/*      */         
/*  662 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/*  663 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/*  665 */         GDMsgLogger.addError(msg);
/*      */       } 
/*      */       
/*  668 */       this.records[pos].setLootRandomCost(iVal);
/*      */       
/*  670 */       return true;
/*      */     } 
/*      */     
/*  673 */     if (attrib.equals("lootRandomizerJitter")) {
/*  674 */       int iVal = 0;
/*      */       try {
/*  676 */         iVal = (int)parseFloat(value);
/*      */       }
/*  678 */       catch (NumberFormatException ex) {
/*  679 */         iVal = 0;
/*      */         
/*  681 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/*  682 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/*  684 */         GDMsgLogger.addError(msg);
/*      */       } 
/*  686 */       this.records[pos].setRNGPercent(iVal);
/*      */       
/*  688 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  692 */     if (attrib.equals("lootRandomizerName")) {
/*  693 */       this.records[pos].setLootRandomName(value);
/*      */       
/*  695 */       return true;
/*      */     } 
/*      */     
/*  698 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribAffixSet(String attrib, int pos, String value) {
/*  702 */     if (attrib.startsWith("randomizerLevelMax")) {
/*      */       
/*  704 */       String s = attrib.substring(18);
/*  705 */       int i = parseInt(s);
/*      */       
/*  707 */       int level = parseInt(value);
/*      */       
/*  709 */       this.records[pos].addRandomizerMaxLevelEntry(i, level);
/*      */       
/*  711 */       return true;
/*      */     } 
/*      */     
/*  714 */     if (attrib.startsWith("randomizerLevelMin")) {
/*      */       
/*  716 */       String s = attrib.substring(18);
/*  717 */       int i = parseInt(s);
/*      */       
/*  719 */       int level = parseInt(value);
/*      */       
/*  721 */       this.records[pos].addRandomizerMinLevelEntry(i, level);
/*      */       
/*  723 */       return true;
/*      */     } 
/*      */     
/*  726 */     if (attrib.startsWith("randomizerName")) {
/*      */       
/*  728 */       String s = attrib.substring(14);
/*  729 */       int i = parseInt(s);
/*      */       
/*  731 */       this.records[pos].addRandomizerAffixIDEntry(i, value);
/*      */       
/*  733 */       return true;
/*      */     } 
/*      */     
/*  736 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribItem(String attrib, int pos, String value) {
/*  740 */     if (attrib.equals("amulet")) {
/*  741 */       this.records[pos].setSlotAmulet(value.equals("1"));
/*      */       
/*  743 */       return true;
/*      */     } 
/*      */     
/*  746 */     if (attrib.equals("armorClassification")) {
/*  747 */       this.records[pos].setArmorClass(value);
/*      */       
/*  749 */       return true;
/*      */     } 
/*      */     
/*  752 */     if (attrib.equals("artifactClassification")) {
/*  753 */       this.records[pos].setArtifactClass(value);
/*      */       
/*  755 */       return true;
/*      */     } 
/*      */     
/*  758 */     if (attrib.equals("artifactName") || attrib
/*  759 */       .equals("forcedRandomArtifactName")) {
/*      */       
/*  761 */       if (value == null) return true; 
/*  762 */       if (value.isEmpty()) return true; 
/*  763 */       if (value.contains("loottable")) return true;
/*      */       
/*  765 */       this.records[pos].setArtifactID(value);
/*      */       
/*  767 */       return true;
/*      */     } 
/*      */     
/*  770 */     if (attrib.equals("attributeScalePercent")) {
/*  771 */       int iVal = 0;
/*      */       try {
/*  773 */         iVal = (int)parseFloat(value);
/*      */       }
/*  775 */       catch (NumberFormatException ex) {
/*  776 */         iVal = 0;
/*      */         
/*  778 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/*  779 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/*  781 */         GDMsgLogger.addError(msg);
/*      */       } 
/*  783 */       this.records[pos].setRNGPercent(iVal);
/*      */       
/*  785 */       return true;
/*      */     } 
/*      */     
/*  788 */     if (!this.records[pos].isItemSet())
/*      */     {
/*      */       
/*  791 */       if (attrib.equals("augmentAllLevel")) {
/*  792 */         int iVal = parseInt(value);
/*  793 */         this.records[pos].setPlusAllSkills(iVal);
/*      */         
/*  795 */         return true;
/*      */       } 
/*      */     }
/*      */     
/*  799 */     if (attrib.equals("axe")) {
/*  800 */       this.records[pos].setSlotAxe1H(value.equals("1"));
/*      */       
/*  802 */       return true;
/*      */     } 
/*      */     
/*  805 */     if (attrib.equals("axe2h")) {
/*  806 */       this.records[pos].setSlotAxe2H(value.equals("1"));
/*      */       
/*  808 */       return true;
/*      */     } 
/*      */     
/*  811 */     if (attrib.equals("baseTexture")) {
/*  812 */       this.records[pos].setBaseTextureID(value);
/*      */       
/*  814 */       return true;
/*      */     } 
/*      */     
/*  817 */     if (attrib.equals("baseTextures")) {
/*  818 */       this.records[pos].addBaseTextureID(value);
/*      */       
/*  820 */       return true;
/*      */     } 
/*      */     
/*  823 */     if (attrib.equals("bumpTexture")) {
/*  824 */       this.records[pos].setBumpTextureID(value);
/*      */       
/*  826 */       return true;
/*      */     } 
/*      */     
/*  829 */     if (attrib.equals("bumpTextures")) {
/*  830 */       this.records[pos].addBumpTextureID(value);
/*      */       
/*  832 */       return true;
/*      */     } 
/*      */     
/*  835 */     if (attrib.equals("glowTexture")) {
/*  836 */       this.records[pos].setGlowTextureID(value);
/*      */       
/*  838 */       return true;
/*      */     } 
/*      */     
/*  841 */     if (attrib.equals("glowTextures")) {
/*  842 */       this.records[pos].addGlowTextureID(value);
/*      */       
/*  844 */       return true;
/*      */     } 
/*      */     
/*  847 */     if (attrib.equals("shader")) {
/*  848 */       this.records[pos].setShaderID(value);
/*      */       
/*  850 */       return true;
/*      */     } 
/*      */     
/*  853 */     if (attrib.equals("bitmap") || attrib
/*  854 */       .equals("artifactBitmap") || attrib
/*  855 */       .equals("artifactFormulaBitmapName") || attrib
/*  856 */       .equals("noteBitmap") || attrib
/*  857 */       .equals("relicBitmap") || attrib
/*  858 */       .equals("emptyBitmap") || attrib
/*  859 */       .equals("fullBitmap")) {
/*  860 */       this.records[pos].setBitmapID(value);
/*      */       
/*  862 */       return true;
/*      */     } 
/*      */     
/*  865 */     if (attrib.equals("bonusTableName")) {
/*  866 */       this.records[pos].setBonusAffixSetID(value);
/*      */     }
/*      */     
/*  869 */     if (attrib.equals("cannotPickUp")) {
/*  870 */       this.records[pos].setCannotPickup(value.equals("1"));
/*      */       
/*  872 */       return true;
/*      */     } 
/*      */     
/*  875 */     if (attrib.equals("chest")) {
/*  876 */       this.records[pos].setSlotChest(value.equals("1"));
/*      */       
/*  878 */       return true;
/*      */     } 
/*      */     
/*  881 */     if (attrib.equals("completedRelicLevel")) {
/*  882 */       int iVal = parseInt(value);
/*  883 */       this.records[pos].setComponentPieces(iVal);
/*      */       
/*  885 */       return true;
/*      */     } 
/*      */     
/*  888 */     if (attrib.equals("dagger")) {
/*  889 */       this.records[pos].setSlotDagger1H(value.equals("1"));
/*      */       
/*  891 */       return true;
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
/*  902 */     if (attrib.equals("feet")) {
/*  903 */       this.records[pos].setSlotFeet(value.equals("1"));
/*      */       
/*  905 */       return true;
/*      */     } 
/*      */     
/*  908 */     if (attrib.equals("hands")) {
/*  909 */       this.records[pos].setSlotHands(value.equals("1"));
/*      */       
/*  911 */       return true;
/*      */     } 
/*      */     
/*  914 */     if (attrib.equals("head")) {
/*  915 */       this.records[pos].setSlotHead(value.equals("1"));
/*      */       
/*  917 */       return true;
/*      */     } 
/*      */     
/*  920 */     if (attrib.equals("hidePrefixName")) {
/*  921 */       this.records[pos].setHidePrefix(value.equals("1"));
/*      */       
/*  923 */       return true;
/*      */     } 
/*      */     
/*  926 */     if (attrib.equals("hideSuffixName")) {
/*  927 */       this.records[pos].setHideSuffix(value.equals("1"));
/*      */       
/*  929 */       return true;
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
/*  940 */     if (attrib.equals("itemCostName")) {
/*  941 */       this.records[pos].setCostFormulaSetID(value);
/*      */       
/*  943 */       return true;
/*      */     } 
/*      */     
/*  946 */     if (attrib.equals("itemLevel")) {
/*  947 */       int iVal = parseInt(value);
/*  948 */       this.records[pos].setItemLevel(iVal);
/*      */       
/*  950 */       return true;
/*      */     } 
/*      */     
/*  953 */     if (attrib.equals("itemNameTag") || attrib
/*  954 */       .equals("description")) {
/*  955 */       if (value != null && !value.equals("")) {
/*  956 */         this.records[pos].setItemNameTag(value);
/*      */       }
/*      */       
/*  959 */       return true;
/*      */     } 
/*      */     
/*  962 */     if (attrib.equals("itemQualityTag")) {
/*  963 */       this.records[pos].setQualityTag(value);
/*      */       
/*  965 */       return true;
/*      */     } 
/*      */     
/*  968 */     if (attrib.equals("itemSetName")) {
/*  969 */       this.records[pos].setItemSetID(value);
/*      */       
/*  971 */       return true;
/*      */     } 
/*      */     
/*  974 */     if (attrib.equals("itemStyleTag")) {
/*  975 */       this.records[pos].setStyleTag(value);
/*      */       
/*  977 */       return true;
/*      */     } 
/*      */     
/*  980 */     if (attrib.equals("itemText")) {
/*  981 */       this.records[pos].setItemDescriptionTag(value);
/*      */       
/*  983 */       return true;
/*      */     } 
/*      */     
/*  986 */     if (attrib.equals("legs")) {
/*  987 */       this.records[pos].setSlotLegs(value.equals("1"));
/*      */       
/*  989 */       return true;
/*      */     } 
/*      */     
/*  992 */     if (attrib.equals("mace")) {
/*  993 */       this.records[pos].setSlotMace1H(value.equals("1"));
/*      */       
/*  995 */       return true;
/*      */     } 
/*      */     
/*  998 */     if (attrib.equals("mace2h")) {
/*  999 */       this.records[pos].setSlotMace2H(value.equals("1"));
/*      */       
/* 1001 */       return true;
/*      */     } 
/*      */     
/* 1004 */     if (attrib.equals("maxStackSize")) {
/* 1005 */       int iVal = parseInt(value);
/* 1006 */       this.records[pos].setMaxStackSize(iVal);
/*      */       
/* 1008 */       return true;
/*      */     } 
/*      */     
/* 1011 */     if (attrib.equals("medal")) {
/* 1012 */       this.records[pos].setSlotMedal(value.equals("1"));
/*      */       
/* 1014 */       return true;
/*      */     } 
/*      */     
/* 1017 */     if (attrib.equals("mesh")) {
/* 1018 */       this.records[pos].setMeshID(value);
/*      */       
/* 1020 */       return true;
/*      */     } 
/*      */     
/* 1023 */     if (attrib.equals("offhand")) {
/* 1024 */       this.records[pos].setSlotOffhand(value.equals("1"));
/*      */       
/* 1026 */       return true;
/*      */     } 
/*      */     
/* 1029 */     if (attrib.equals("quest")) {
/* 1030 */       this.records[pos].setQuestItem(value.equals("1"));
/*      */       
/* 1032 */       return true;
/*      */     } 
/*      */     
/* 1035 */     if (attrib.equals("ranged1h")) {
/* 1036 */       this.records[pos].setSlotRanged1H(value.equals("1"));
/*      */       
/* 1038 */       return true;
/*      */     } 
/*      */     
/* 1041 */     if (attrib.equals("ranged2h")) {
/* 1042 */       this.records[pos].setSlotRanged2H(value.equals("1"));
/*      */       
/* 1044 */       return true;
/*      */     } 
/*      */     
/* 1047 */     if (attrib.equals("ring")) {
/* 1048 */       this.records[pos].setSlotRing(value.equals("1"));
/*      */       
/* 1050 */       return true;
/*      */     } 
/*      */     
/* 1053 */     if (attrib.equals("scepter")) {
/* 1054 */       this.records[pos].setSlotScepter1H(value.equals("1"));
/*      */       
/* 1056 */       return true;
/*      */     } 
/*      */     
/* 1059 */     if (attrib.equals("shardBitmap")) {
/* 1060 */       this.records[pos].setShardBitmapID(value);
/*      */       
/* 1062 */       return true;
/*      */     } 
/*      */     
/* 1065 */     if (attrib.equals("shield")) {
/* 1066 */       this.records[pos].setSlotShield(value.equals("1"));
/*      */       
/* 1068 */       return true;
/*      */     } 
/*      */     
/* 1071 */     if (attrib.equals("shoulders")) {
/* 1072 */       this.records[pos].setSlotShoulders(value.equals("1"));
/*      */       
/* 1074 */       return true;
/*      */     } 
/*      */     
/* 1077 */     if (attrib.equals("soulbound")) {
/* 1078 */       this.records[pos].setSoulbound(value.equals("1"));
/*      */       
/* 1080 */       return true;
/*      */     } 
/*      */     
/* 1083 */     if (attrib.equals("spear")) {
/* 1084 */       this.records[pos].setSlotSpear2H(value.equals("1"));
/*      */       
/* 1086 */       return true;
/*      */     } 
/*      */     
/* 1089 */     if (attrib.equals("staff")) {
/* 1090 */       this.records[pos].setSlotStaff2H(value.equals("1"));
/*      */       
/* 1092 */       return true;
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
/* 1103 */     if (attrib.equals("sword")) {
/* 1104 */       this.records[pos].setSlotSword1H(value.equals("1"));
/*      */       
/* 1106 */       return true;
/*      */     } 
/*      */     
/* 1109 */     if (attrib.equals("sword2h")) {
/* 1110 */       this.records[pos].setSlotSword2H(value.equals("1"));
/*      */       
/* 1112 */       return true;
/*      */     } 
/*      */     
/* 1115 */     if (attrib.equals("waist")) {
/* 1116 */       this.records[pos].setSlotBelt(value.equals("1"));
/*      */       
/* 1118 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1122 */     if (attrib.equals("skillName")) {
/* 1123 */       this.records[pos].setItemSkillID(value);
/*      */       
/* 1125 */       return true;
/*      */     } 
/*      */     
/* 1128 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribSkillModifier(String attrib, int pos, String value, int index) {
/* 1132 */     if (attrib.startsWith("modifiedSkillName")) {
/*      */       
/* 1134 */       String s = attrib.substring(17);
/* 1135 */       int i = parseInt(s);
/*      */       
/* 1137 */       this.records[pos].addModifierSkillID(i, value);
/*      */       
/* 1139 */       return true;
/*      */     } 
/*      */     
/* 1142 */     if (attrib.startsWith("modifierSkillName")) {
/*      */       
/* 1144 */       String s = attrib.substring(17);
/* 1145 */       int i = parseInt(s);
/*      */       
/* 1147 */       this.records[pos].addModifierModifierID(i, value);
/*      */       
/* 1149 */       return true;
/*      */     } 
/*      */     
/* 1152 */     if (attrib.equals("itemSkillModifierControl")) {
/* 1153 */       int iVal = parseInt(value);
/*      */       
/* 1155 */       this.records[pos].setItemSetSkillModifierLevel(iVal, index);
/*      */     } 
/*      */     
/* 1158 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribStat(String attrib, int pos, String value, int index) {
/* 1162 */     boolean processed = false;
/*      */     
/* 1164 */     if (attrib.startsWith("defensive")) {
/* 1165 */       processed = fillAttribStatDefense(attrib, pos, value, index);
/* 1166 */       if (processed) return true;
/*      */     
/*      */     } 
/* 1169 */     if (attrib.startsWith("offensive")) {
/* 1170 */       processed = fillAttribStatOffense(attrib, pos, value, index);
/* 1171 */       if (processed) return true;
/*      */     
/*      */     } 
/* 1174 */     if (attrib.startsWith("retaliation")) {
/* 1175 */       processed = fillAttribStatRetaliation(attrib, pos, value, index);
/* 1176 */       if (processed) return true;
/*      */     
/*      */     } 
/* 1179 */     processed = fillAttribStatChar(attrib, pos, value, index);
/*      */     
/* 1181 */     return processed;
/*      */   }
/*      */   
/*      */   private boolean fillAttribStatDetails(String attrib, String search, int pos, String value, int index) {
/* 1185 */     boolean processed = false;
/*      */     
/* 1187 */     int len = search.length();
/* 1188 */     int p = attrib.indexOf(search);
/*      */     
/* 1190 */     if (p == -1) {
/* 1191 */       if (search.equals("retaliationPercentCurrentLife"))
/*      */       {
/*      */         
/* 1194 */         p = attrib.indexOf("retaliationPercentcurrentLife");
/*      */       }
/*      */       
/* 1197 */       if (p == -1) return false;
/*      */     
/*      */     } 
/* 1200 */     String suf = attrib.substring(p + len);
/*      */     
/* 1202 */     DBStat stat = this.records[pos].getCreateDBStat(search, index);
/*      */     
/* 1204 */     float fVal = parseFloat(value);
/* 1205 */     int iVal = (int)fVal;
/*      */     
/* 1207 */     if (suf.equals("Global")) {
/* 1208 */       stat.setGlobal((iVal > 0));
/*      */       
/* 1210 */       processed = true;
/*      */     } 
/*      */     
/* 1213 */     if (suf.equals("XOR")) {
/* 1214 */       stat.setXOR((iVal > 0));
/*      */       
/* 1216 */       processed = true;
/*      */     } 
/*      */     
/* 1219 */     if (suf.equals("")) {
/* 1220 */       stat.setStatMin(fVal);
/*      */ 
/*      */       
/* 1223 */       processed = true;
/*      */     } 
/*      */     
/* 1226 */     if (suf.equals("Min")) {
/* 1227 */       stat.setStatMin(fVal);
/*      */       
/* 1229 */       processed = true;
/*      */     } 
/*      */     
/* 1232 */     if (suf.equals("Max")) {
/* 1233 */       stat.setStatMax(fVal);
/*      */       
/* 1235 */       processed = true;
/*      */     } 
/*      */     
/* 1238 */     if (suf.equals("Chance")) {
/* 1239 */       stat.setStatChance(iVal);
/*      */       
/* 1241 */       processed = true;
/*      */     } 
/*      */     
/* 1244 */     if (suf.equals("Modifier")) {
/* 1245 */       stat.setStatModifier(iVal);
/*      */       
/* 1247 */       processed = true;
/*      */     } 
/*      */     
/* 1250 */     if (suf.equals("ModifierChance")) {
/* 1251 */       stat.setStatModifierChance(iVal);
/*      */       
/* 1253 */       processed = true;
/*      */     } 
/*      */     
/* 1256 */     if (suf.equals("Duration")) {
/* 1257 */       stat.setMinDuration(iVal);
/*      */ 
/*      */       
/* 1260 */       processed = true;
/*      */     } 
/*      */     
/* 1263 */     if (suf.equals("DurationMin")) {
/* 1264 */       stat.setMinDuration(iVal);
/*      */       
/* 1266 */       processed = true;
/*      */     } 
/*      */     
/* 1269 */     if (suf.equals("DurationMax")) {
/* 1270 */       stat.setMaxDuration(iVal);
/*      */       
/* 1272 */       processed = true;
/*      */     } 
/*      */     
/* 1275 */     if (suf.equals("DurationChance")) {
/* 1276 */       stat.setDurationChance(iVal);
/*      */       
/* 1278 */       processed = true;
/*      */     } 
/*      */     
/* 1281 */     if (suf.equals("DurationModifier")) {
/* 1282 */       stat.setDurationModifier(iVal);
/*      */       
/* 1284 */       processed = true;
/*      */     } 
/*      */     
/* 1287 */     if (suf.equals("DurationModifierChance")) {
/* 1288 */       stat.setDurationModifierChance(iVal);
/*      */       
/* 1290 */       processed = true;
/*      */     } 
/*      */     
/* 1293 */     if (suf.equals("MaxResist")) {
/* 1294 */       stat.setMaxResist(iVal);
/*      */       
/* 1296 */       processed = true;
/*      */     } 
/*      */     
/* 1299 */     return processed;
/*      */   }
/*      */   
/*      */   private boolean fillAttribStatChar(String attrib, int pos, String value, int index) {
/* 1303 */     boolean processed = false;
/*      */     
/* 1305 */     processed = fillAttribStatDetails(attrib, "skillManaCost", pos, value, index);
/* 1306 */     if (processed) return processed;
/*      */     
/* 1308 */     processed = fillAttribStatDetails(attrib, "skillCooldownTime", pos, value, index);
/* 1309 */     if (processed) return processed;
/*      */     
/* 1311 */     processed = fillAttribStatDetails(attrib, "skillActiveDuration", pos, value, index);
/* 1312 */     if (processed) return processed;
/*      */     
/* 1314 */     processed = fillAttribStatDetails(attrib, "lifeMonitorPercent", pos, value, index);
/* 1315 */     if (processed) return processed;
/*      */     
/* 1317 */     processed = fillAttribStatDetails(attrib, "weaponDamagePct", pos, value, index);
/* 1318 */     if (processed) return processed;
/*      */     
/* 1320 */     processed = fillAttribStatDetails(attrib, "damageAbsorptionPercent", pos, value, index);
/* 1321 */     if (processed) return processed;
/*      */     
/* 1323 */     processed = fillAttribStatDetails(attrib, "skillTargetRadius", pos, value, index);
/* 1324 */     if (processed) return processed;
/*      */     
/* 1326 */     processed = fillAttribStatDetails(attrib, "petBurstSpawn", pos, value, index);
/* 1327 */     if (processed) return processed;
/*      */     
/* 1329 */     processed = fillAttribStatDetails(attrib, "petLimit", pos, value, index);
/* 1330 */     if (processed) return processed;
/*      */     
/* 1332 */     processed = fillAttribStatDetails(attrib, "skillMaxLevel", pos, value, index);
/* 1333 */     if (processed) return processed;
/*      */     
/* 1335 */     processed = fillAttribStatDetails(attrib, "sparkMaxNumber", pos, value, index);
/* 1336 */     if (processed) return processed;
/*      */     
/* 1338 */     processed = fillAttribStatDetails(attrib, "skillTargetNumber", pos, value, index);
/* 1339 */     if (processed) return processed;
/*      */     
/* 1341 */     processed = fillAttribStatDetails(attrib, "characterLightRadius", pos, value, index);
/* 1342 */     if (processed) return processed;
/*      */     
/* 1344 */     processed = fillAttribStatDetails(attrib, "characterDefensiveAbility", pos, value, index);
/* 1345 */     if (processed) return processed;
/*      */     
/* 1347 */     processed = fillAttribStatDetails(attrib, "characterOffensiveAbility", pos, value, index);
/* 1348 */     if (processed) return processed;
/*      */     
/* 1350 */     processed = fillAttribStatDetails(attrib, "characterMana", pos, value, index);
/* 1351 */     if (processed) return processed;
/*      */     
/* 1353 */     processed = fillAttribStatDetails(attrib, "characterManaLimitReserve", pos, value, index);
/* 1354 */     if (processed) return processed;
/*      */     
/* 1356 */     processed = fillAttribStatDetails(attrib, "characterManaLimitReserveReduction", pos, value, index);
/* 1357 */     if (processed) return processed;
/*      */     
/* 1359 */     processed = fillAttribStatDetails(attrib, "characterManaRegen", pos, value, index);
/* 1360 */     if (processed) return processed;
/*      */     
/* 1362 */     processed = fillAttribStatDetails(attrib, "characterHealIncreasePercent", pos, value, index);
/* 1363 */     if (processed) return processed;
/*      */     
/* 1365 */     processed = fillAttribStatDetails(attrib, "characterLife", pos, value, index);
/* 1366 */     if (processed) return processed;
/*      */     
/* 1368 */     processed = fillAttribStatDetails(attrib, "characterLifeRegen", pos, value, index);
/* 1369 */     if (processed) return processed;
/*      */     
/* 1371 */     processed = fillAttribStatDetails(attrib, "characterConstitution", pos, value, index);
/* 1372 */     if (processed) return processed;
/*      */     
/* 1374 */     processed = fillAttribStatDetails(attrib, "skillLifeBonus", pos, value, index);
/* 1375 */     if (processed) return processed;
/*      */     
/* 1377 */     processed = fillAttribStatDetails(attrib, "skillLifePercent", pos, value, index);
/* 1378 */     if (processed) return processed;
/*      */     
/* 1380 */     processed = fillAttribStatDetails(attrib, "characterDexterity", pos, value, index);
/* 1381 */     if (processed) return processed;
/*      */     
/* 1383 */     processed = fillAttribStatDetails(attrib, "characterStrength", pos, value, index);
/* 1384 */     if (processed) return processed;
/*      */     
/* 1386 */     processed = fillAttribStatDetails(attrib, "characterIntelligence", pos, value, index);
/* 1387 */     if (processed) return processed;
/*      */     
/* 1389 */     processed = fillAttribStatDetails(attrib, "characterAttackSpeed", pos, value, index);
/* 1390 */     if (processed) return processed;
/*      */     
/* 1392 */     processed = fillAttribStatDetails(attrib, "characterSpellCastSpeed", pos, value, index);
/* 1393 */     if (processed) return processed;
/*      */     
/* 1395 */     processed = fillAttribStatDetails(attrib, "characterRunSpeed", pos, value, index);
/* 1396 */     if (processed) return processed;
/*      */     
/* 1398 */     processed = fillAttribStatDetails(attrib, "characterTotalSpeed", pos, value, index);
/* 1399 */     if (processed) return processed;
/*      */     
/* 1401 */     processed = fillAttribStatDetails(attrib, "blockRecoveryTime", pos, value, index);
/* 1402 */     if (processed) return processed;
/*      */     
/* 1404 */     processed = fillAttribStatDetails(attrib, "characterDefensiveBlockRecoveryReduction", pos, value, index);
/* 1405 */     if (processed) return processed;
/*      */     
/* 1407 */     processed = fillAttribStatDetails(attrib, "characterGlobalReqReduction", pos, value, index);
/* 1408 */     if (processed) return processed;
/*      */     
/* 1410 */     processed = fillAttribStatDetails(attrib, "characterEnergyAbsorptionPercent", pos, value, index);
/* 1411 */     if (processed) return processed;
/*      */     
/* 1413 */     processed = fillAttribStatDetails(attrib, "characterIncreasedExperience", pos, value, index);
/* 1414 */     if (processed) return processed;
/*      */     
/* 1416 */     processed = fillAttribStatDetails(attrib, "skillCooldownReduction", pos, value, index);
/* 1417 */     if (processed) return processed;
/*      */     
/* 1419 */     processed = fillAttribStatDetails(attrib, "skillManaCostReduction", pos, value, index);
/* 1420 */     if (processed) return processed;
/*      */     
/* 1422 */     processed = fillAttribStatDetails(attrib, "racialBonusPercentDamage", pos, value, index);
/* 1423 */     if (processed) return processed;
/*      */     
/* 1425 */     processed = fillAttribStatDetails(attrib, "piercingProjectile", pos, value, index);
/* 1426 */     if (processed) return processed;
/*      */     
/* 1428 */     if (attrib.equals("racialBonusRace")) {
/* 1429 */       String tag = value;
/* 1430 */       if (!tag.startsWith("tag")) tag = "tag" + tag;
/*      */       
/* 1432 */       String name = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.All, GDConstants.TAG_TEXT_CREATURES, tag, false);
/*      */       
/* 1434 */       this.records[pos].addRace(value, name);
/*      */       
/* 1436 */       return true;
/*      */     } 
/*      */     
/* 1439 */     processed = fillAttribStatDetails(attrib, "characterDeflectProjectile", pos, value, index);
/* 1440 */     if (processed) return processed;
/*      */     
/* 1442 */     processed = fillAttribStatDetails(attrib, "characterDodgePercent", pos, value, index);
/* 1443 */     if (processed) return processed;
/*      */     
/* 1445 */     processed = fillAttribStatDetails(attrib, "characterLevelReqReduction", pos, value, index);
/* 1446 */     if (processed) return processed;
/*      */     
/* 1448 */     processed = fillAttribStatDetails(attrib, "characterArmorStrengthReqReduction", pos, value, index);
/* 1449 */     if (processed) return processed;
/*      */     
/* 1451 */     processed = fillAttribStatDetails(attrib, "characterHuntingDexterityReqReduction", pos, value, index);
/* 1452 */     if (processed) return processed;
/*      */     
/* 1454 */     processed = fillAttribStatDetails(attrib, "characterMeleeDexterityReqReduction", pos, value, index);
/* 1455 */     if (processed) return processed;
/*      */     
/* 1457 */     processed = fillAttribStatDetails(attrib, "characterMeleeStrengthReqReduction", pos, value, index);
/* 1458 */     if (processed) return processed;
/*      */     
/* 1460 */     processed = fillAttribStatDetails(attrib, "characterWeaponStrengthReqReduction", pos, value, index);
/* 1461 */     if (processed) return processed;
/*      */     
/* 1463 */     processed = fillAttribStatDetails(attrib, "characterWeaponIntelligenceReqReduction", pos, value, index);
/* 1464 */     if (processed) return processed;
/*      */     
/* 1466 */     processed = fillAttribStatDetails(attrib, "characterJewelryIntelligenceReqReduction", pos, value, index);
/* 1467 */     if (processed) return processed;
/*      */     
/* 1469 */     processed = fillAttribStatDetails(attrib, "characterShieldStrengthReqReduction", pos, value, index);
/* 1470 */     if (processed) return processed;
/*      */     
/* 1472 */     processed = fillAttribStatDetails(attrib, "conversionPercentage", pos, value, index);
/* 1473 */     if (processed) return processed;
/*      */     
/* 1475 */     processed = fillAttribStatDetails(attrib, "conversionPercentage2", pos, value, index);
/* 1476 */     if (processed) return processed;
/*      */     
/* 1478 */     if (!processed) {
/* 1479 */       int i = parseInt(value);
/*      */       
/* 1481 */       logTag(attrib, i, this.records[pos]);
/*      */     } 
/*      */     
/* 1484 */     return processed;
/*      */   }
/*      */   
/*      */   private boolean fillAttribStatDefense(String attrib, int pos, String value, int index) {
/* 1488 */     boolean processed = false;
/*      */     
/* 1490 */     processed = fillAttribStatDetails(attrib, "defensiveAbsorption", pos, value, index);
/* 1491 */     if (processed) return processed;
/*      */     
/* 1493 */     processed = fillAttribStatDetails(attrib, "defensiveAether", pos, value, index);
/* 1494 */     if (processed) return processed;
/*      */     
/* 1496 */     processed = fillAttribStatDetails(attrib, "defensiveAll", pos, value, index);
/* 1497 */     if (processed) return processed;
/*      */     
/* 1499 */     processed = fillAttribStatDetails(attrib, "defensiveBleeding", pos, value, index);
/* 1500 */     if (processed) return processed;
/*      */     
/* 1502 */     processed = fillAttribStatDetails(attrib, "defensiveBlock", pos, value, index);
/* 1503 */     if (processed) return processed;
/*      */     
/* 1505 */     processed = fillAttribStatDetails(attrib, "defensiveBlockAmount", pos, value, index);
/* 1506 */     if (processed) return processed;
/*      */     
/* 1508 */     processed = fillAttribStatDetails(attrib, "defensiveBonusProtection", pos, value, index);
/* 1509 */     if (processed) return processed;
/*      */     
/* 1511 */     processed = fillAttribStatDetails(attrib, "defensiveChaos", pos, value, index);
/* 1512 */     if (processed) return processed;
/*      */     
/* 1514 */     processed = fillAttribStatDetails(attrib, "defensiveCold", pos, value, index);
/* 1515 */     if (processed) return processed;
/*      */     
/* 1517 */     processed = fillAttribStatDetails(attrib, "defensiveConfusion", pos, value, index);
/* 1518 */     if (processed) return processed;
/*      */     
/* 1520 */     processed = fillAttribStatDetails(attrib, "defensiveConvert", pos, value, index);
/* 1521 */     if (processed) return processed;
/*      */     
/* 1523 */     processed = fillAttribStatDetails(attrib, "defensiveDisruption", pos, value, index);
/* 1524 */     if (processed) return processed;
/*      */     
/* 1526 */     processed = fillAttribStatDetails(attrib, "defensiveElemental", pos, value, index);
/* 1527 */     if (processed) return processed;
/*      */     
/* 1529 */     processed = fillAttribStatDetails(attrib, "defensiveElementalResistance", pos, value, index);
/* 1530 */     if (processed) return processed;
/*      */     
/* 1532 */     processed = fillAttribStatDetails(attrib, "defensiveFear", pos, value, index);
/* 1533 */     if (processed) return processed;
/*      */     
/* 1535 */     processed = fillAttribStatDetails(attrib, "defensiveFire", pos, value, index);
/* 1536 */     if (processed) return processed;
/*      */     
/* 1538 */     processed = fillAttribStatDetails(attrib, "defensiveFreeze", pos, value, index);
/* 1539 */     if (processed) return processed;
/*      */     
/* 1541 */     processed = fillAttribStatDetails(attrib, "defensiveKnockdown", pos, value, index);
/* 1542 */     if (processed) return processed;
/*      */     
/* 1544 */     processed = fillAttribStatDetails(attrib, "defensiveLife", pos, value, index);
/* 1545 */     if (processed) return processed;
/*      */     
/* 1547 */     processed = fillAttribStatDetails(attrib, "defensiveLightning", pos, value, index);
/* 1548 */     if (processed) return processed;
/*      */     
/* 1550 */     processed = fillAttribStatDetails(attrib, "defensivePercentCurrentLife", pos, value, index);
/* 1551 */     if (processed) return processed;
/*      */     
/* 1553 */     processed = fillAttribStatDetails(attrib, "defensivePercentReflectionResistance", pos, value, index);
/* 1554 */     if (processed) return processed;
/*      */     
/* 1556 */     processed = fillAttribStatDetails(attrib, "defensiveManaBurnRatio", pos, value, index);
/* 1557 */     if (processed) return processed;
/*      */     
/* 1559 */     processed = fillAttribStatDetails(attrib, "defensivePetrify", pos, value, index);
/* 1560 */     if (processed) return processed;
/*      */     
/* 1562 */     processed = fillAttribStatDetails(attrib, "defensivePhysical", pos, value, index);
/* 1563 */     if (processed) return processed;
/*      */     
/* 1565 */     processed = fillAttribStatDetails(attrib, "defensivePierce", pos, value, index);
/* 1566 */     if (processed) return processed;
/*      */     
/* 1568 */     processed = fillAttribStatDetails(attrib, "defensivePoison", pos, value, index);
/* 1569 */     if (processed) return processed;
/*      */     
/* 1571 */     processed = fillAttribStatDetails(attrib, "defensiveProtection", pos, value, index);
/* 1572 */     if (processed) return processed;
/*      */     
/* 1574 */     processed = fillAttribStatDetails(attrib, "defensiveReflect", pos, value, index);
/* 1575 */     if (processed) return processed;
/*      */     
/* 1577 */     processed = fillAttribStatDetails(attrib, "defensiveSleep", pos, value, index);
/* 1578 */     if (processed) return processed;
/*      */     
/* 1580 */     processed = fillAttribStatDetails(attrib, "defensiveSlowLifeLeach", pos, value, index);
/* 1581 */     if (processed) return processed;
/*      */     
/* 1583 */     processed = fillAttribStatDetails(attrib, "defensiveSlowManaLeach", pos, value, index);
/* 1584 */     if (processed) return processed;
/*      */     
/* 1586 */     processed = fillAttribStatDetails(attrib, "defensiveStun", pos, value, index);
/* 1587 */     if (processed) return processed;
/*      */     
/* 1589 */     processed = fillAttribStatDetails(attrib, "defensiveTaunt", pos, value, index);
/* 1590 */     if (processed) return processed;
/*      */     
/* 1592 */     processed = fillAttribStatDetails(attrib, "defensiveTotalSpeedResistance", pos, value, index);
/* 1593 */     if (processed) return processed;
/*      */     
/* 1595 */     processed = fillAttribStatDetails(attrib, "defensiveTrap", pos, value, index);
/* 1596 */     if (processed) return processed;
/*      */     
/* 1598 */     if (!processed) {
/* 1599 */       int i = parseInt(value);
/*      */       
/* 1601 */       logTag(attrib, i, this.records[pos]);
/*      */     } 
/*      */     
/* 1604 */     return processed;
/*      */   }
/*      */   
/*      */   private boolean fillAttribStatOffense(String attrib, int pos, String value, int index) {
/* 1608 */     boolean processed = false;
/*      */     
/* 1610 */     processed = fillAttribStatDetails(attrib, "offensiveAether", pos, value, index);
/* 1611 */     if (processed) return processed;
/*      */     
/* 1613 */     processed = fillAttribStatDetails(attrib, "offensiveBaseAether", pos, value, index);
/* 1614 */     if (processed) return processed;
/*      */     
/* 1616 */     processed = fillAttribStatDetails(attrib, "offensiveBaseChaos", pos, value, index);
/* 1617 */     if (processed) return processed;
/*      */     
/* 1619 */     processed = fillAttribStatDetails(attrib, "offensiveBaseCold", pos, value, index);
/* 1620 */     if (processed) return processed;
/*      */     
/* 1622 */     processed = fillAttribStatDetails(attrib, "offensiveBaseFire", pos, value, index);
/* 1623 */     if (processed) return processed;
/*      */     
/* 1625 */     processed = fillAttribStatDetails(attrib, "offensiveBaseLife", pos, value, index);
/* 1626 */     if (processed) return processed;
/*      */     
/* 1628 */     processed = fillAttribStatDetails(attrib, "offensiveBaseLightning", pos, value, index);
/* 1629 */     if (processed) return processed;
/*      */     
/* 1631 */     processed = fillAttribStatDetails(attrib, "offensiveBasePoison", pos, value, index);
/* 1632 */     if (processed) return processed;
/*      */     
/* 1634 */     processed = fillAttribStatDetails(attrib, "offensiveBonusPhysical", pos, value, index);
/* 1635 */     if (processed) return processed;
/*      */     
/* 1637 */     processed = fillAttribStatDetails(attrib, "offensiveChaos", pos, value, index);
/* 1638 */     if (processed) return processed;
/*      */     
/* 1640 */     processed = fillAttribStatDetails(attrib, "offensiveCold", pos, value, index);
/* 1641 */     if (processed) return processed;
/*      */     
/* 1643 */     processed = fillAttribStatDetails(attrib, "offensiveConfusion", pos, value, index);
/* 1644 */     if (processed) return processed;
/*      */     
/* 1646 */     processed = fillAttribStatDetails(attrib, "offensiveConvert", pos, value, index);
/* 1647 */     if (processed) return processed;
/*      */     
/* 1649 */     processed = fillAttribStatDetails(attrib, "offensiveCritDamage", pos, value, index);
/* 1650 */     if (processed) return processed;
/*      */     
/* 1652 */     processed = fillAttribStatDetails(attrib, "offensiveDamageMult", pos, value, index);
/* 1653 */     if (processed) return processed;
/*      */     
/* 1655 */     processed = fillAttribStatDetails(attrib, "offensiveDisruption", pos, value, index);
/* 1656 */     if (processed) return processed;
/*      */     
/* 1658 */     processed = fillAttribStatDetails(attrib, "offensiveElemental", pos, value, index);
/* 1659 */     if (processed) return processed;
/*      */     
/* 1661 */     processed = fillAttribStatDetails(attrib, "offensiveElementalReductionPercent", pos, value, index);
/* 1662 */     if (processed) return processed;
/*      */     
/* 1664 */     processed = fillAttribStatDetails(attrib, "offensiveElementalResistanceReductionAbsolute", pos, value, index);
/* 1665 */     if (processed) return processed;
/*      */     
/* 1667 */     processed = fillAttribStatDetails(attrib, "offensiveElementalResistanceReductionPercent", pos, value, index);
/* 1668 */     if (processed) return processed;
/*      */     
/* 1670 */     processed = fillAttribStatDetails(attrib, "offensiveFear", pos, value, index);
/* 1671 */     if (processed) return processed;
/*      */     
/* 1673 */     processed = fillAttribStatDetails(attrib, "offensiveFire", pos, value, index);
/* 1674 */     if (processed) return processed;
/*      */     
/* 1676 */     processed = fillAttribStatDetails(attrib, "offensiveFreeze", pos, value, index);
/* 1677 */     if (processed) return processed;
/*      */     
/* 1679 */     processed = fillAttribStatDetails(attrib, "offensiveFumble", pos, value, index);
/* 1680 */     if (processed) return processed;
/*      */     
/* 1682 */     processed = fillAttribStatDetails(attrib, "offensiveKnockdown", pos, value, index);
/* 1683 */     if (processed) return processed;
/*      */     
/* 1685 */     processed = fillAttribStatDetails(attrib, "offensiveLife", pos, value, index);
/* 1686 */     if (processed) return processed;
/*      */     
/* 1688 */     processed = fillAttribStatDetails(attrib, "offensiveLifeLeech", pos, value, index);
/* 1689 */     if (processed) return processed;
/*      */     
/* 1691 */     processed = fillAttribStatDetails(attrib, "offensiveLightning", pos, value, index);
/* 1692 */     if (processed) return processed;
/*      */     
/* 1694 */     processed = fillAttribStatDetails(attrib, "offensivePercentCurrentLife", pos, value, index);
/* 1695 */     if (processed) return processed;
/*      */     
/* 1697 */     processed = fillAttribStatDetails(attrib, "offensiveManaBurnDamageRatio", pos, value, index);
/* 1698 */     if (processed) return processed;
/*      */     
/* 1700 */     processed = fillAttribStatDetails(attrib, "offensiveManaBurnDrain", pos, value, index);
/* 1701 */     if (processed) return processed;
/*      */     
/* 1703 */     processed = fillAttribStatDetails(attrib, "offensivePetrify", pos, value, index);
/* 1704 */     if (processed) return processed;
/*      */     
/* 1706 */     processed = fillAttribStatDetails(attrib, "offensivePhysical", pos, value, index);
/* 1707 */     if (processed) return processed;
/*      */     
/* 1709 */     processed = fillAttribStatDetails(attrib, "offensivePhysicalReductionPercent", pos, value, index);
/* 1710 */     if (processed) return processed;
/*      */     
/* 1712 */     processed = fillAttribStatDetails(attrib, "offensivePhysicalResistanceReductionAbsolute", pos, value, index);
/* 1713 */     if (processed) return processed;
/*      */     
/* 1715 */     processed = fillAttribStatDetails(attrib, "offensivePhysicalResistanceReductionPercent", pos, value, index);
/* 1716 */     if (processed) return processed;
/*      */     
/* 1718 */     processed = fillAttribStatDetails(attrib, "offensivePierce", pos, value, index);
/* 1719 */     if (processed) return processed;
/*      */     
/* 1721 */     processed = fillAttribStatDetails(attrib, "offensivePierceRatio", pos, value, index);
/* 1722 */     if (processed) return processed;
/*      */     
/* 1724 */     processed = fillAttribStatDetails(attrib, "offensivePoison", pos, value, index);
/* 1725 */     if (processed) return processed;
/*      */     
/* 1727 */     processed = fillAttribStatDetails(attrib, "offensiveProjectileFumble", pos, value, index);
/* 1728 */     if (processed) return processed;
/*      */     
/* 1730 */     processed = fillAttribStatDetails(attrib, "offensiveSleep", pos, value, index);
/* 1731 */     if (processed) return processed;
/*      */     
/* 1733 */     processed = fillAttribStatDetails(attrib, "offensiveSlowAttackSpeed", pos, value, index);
/* 1734 */     if (processed) return processed;
/*      */     
/* 1736 */     processed = fillAttribStatDetails(attrib, "offensiveSlowBleeding", pos, value, index);
/* 1737 */     if (processed) return processed;
/*      */     
/* 1739 */     processed = fillAttribStatDetails(attrib, "offensiveSlowCold", pos, value, index);
/* 1740 */     if (processed) return processed;
/*      */     
/* 1742 */     processed = fillAttribStatDetails(attrib, "offensiveSlowDefensiveAbility", pos, value, index);
/* 1743 */     if (processed) return processed;
/*      */     
/* 1745 */     processed = fillAttribStatDetails(attrib, "offensiveSlowDefensiveReduction", pos, value, index);
/* 1746 */     if (processed) return processed;
/*      */     
/* 1748 */     processed = fillAttribStatDetails(attrib, "offensiveSlowFire", pos, value, index);
/* 1749 */     if (processed) return processed;
/*      */     
/* 1751 */     processed = fillAttribStatDetails(attrib, "offensiveSlowLife", pos, value, index);
/* 1752 */     if (processed) return processed;
/*      */     
/* 1754 */     processed = fillAttribStatDetails(attrib, "offensiveSlowLifeLeach", pos, value, index);
/* 1755 */     if (processed) return processed;
/*      */     
/* 1757 */     processed = fillAttribStatDetails(attrib, "offensiveSlowLightning", pos, value, index);
/* 1758 */     if (processed) return processed;
/*      */     
/* 1760 */     processed = fillAttribStatDetails(attrib, "offensiveSlowManaLeach", pos, value, index);
/* 1761 */     if (processed) return processed;
/*      */     
/* 1763 */     processed = fillAttribStatDetails(attrib, "offensiveSlowOffensiveAbility", pos, value, index);
/* 1764 */     if (processed) return processed;
/*      */     
/* 1766 */     processed = fillAttribStatDetails(attrib, "offensiveSlowOffensiveReduction", pos, value, index);
/* 1767 */     if (processed) return processed;
/*      */     
/* 1769 */     processed = fillAttribStatDetails(attrib, "offensiveSlowPhysical", pos, value, index);
/* 1770 */     if (processed) return processed;
/*      */     
/* 1772 */     processed = fillAttribStatDetails(attrib, "offensiveSlowPoison", pos, value, index);
/* 1773 */     if (processed) return processed;
/*      */     
/* 1775 */     processed = fillAttribStatDetails(attrib, "offensiveSlowRunSpeed", pos, value, index);
/* 1776 */     if (processed) return processed;
/*      */     
/* 1778 */     processed = fillAttribStatDetails(attrib, "offensiveSlowTotalSpeed", pos, value, index);
/* 1779 */     if (processed) return processed;
/*      */     
/* 1781 */     processed = fillAttribStatDetails(attrib, "offensiveStun", pos, value, index);
/* 1782 */     if (processed) return processed;
/*      */     
/* 1784 */     processed = fillAttribStatDetails(attrib, "offensiveTaunt", pos, value, index);
/* 1785 */     if (processed) return processed;
/*      */     
/* 1787 */     processed = fillAttribStatDetails(attrib, "offensiveTotalDamage", pos, value, index);
/* 1788 */     if (processed) return processed;
/*      */     
/* 1790 */     processed = fillAttribStatDetails(attrib, "offensiveTotalDamageReductionPercent", pos, value, index);
/* 1791 */     if (processed) return processed;
/*      */     
/* 1793 */     processed = fillAttribStatDetails(attrib, "offensiveTotalResistanceReductionAbsolute", pos, value, index);
/* 1794 */     if (processed) return processed;
/*      */     
/* 1796 */     processed = fillAttribStatDetails(attrib, "offensiveTotalResistanceReductionPercent", pos, value, index);
/* 1797 */     if (processed) return processed;
/*      */     
/* 1799 */     processed = fillAttribStatDetails(attrib, "offensiveTrap", pos, value, index);
/* 1800 */     if (processed) return processed;
/*      */     
/* 1802 */     if (!processed) {
/* 1803 */       int i = parseInt(value);
/*      */       
/* 1805 */       logTag(attrib, i, this.records[pos]);
/*      */     } 
/*      */     
/* 1808 */     return processed;
/*      */   }
/*      */   
/*      */   private boolean fillAttribStatRetaliation(String attrib, int pos, String value, int index) {
/* 1812 */     boolean processed = false;
/*      */     
/* 1814 */     processed = fillAttribStatDetails(attrib, "retaliationAether", pos, value, index);
/* 1815 */     if (processed) return processed;
/*      */     
/* 1817 */     processed = fillAttribStatDetails(attrib, "retaliationChaos", pos, value, index);
/* 1818 */     if (processed) return processed;
/*      */     
/* 1820 */     processed = fillAttribStatDetails(attrib, "retaliationCold", pos, value, index);
/* 1821 */     if (processed) return processed;
/*      */     
/* 1823 */     processed = fillAttribStatDetails(attrib, "retaliationConfusion", pos, value, index);
/* 1824 */     if (processed) return processed;
/*      */     
/* 1826 */     processed = fillAttribStatDetails(attrib, "retaliationConvert", pos, value, index);
/* 1827 */     if (processed) return processed;
/*      */     
/* 1829 */     processed = fillAttribStatDetails(attrib, "retaliationDamagePct", pos, value, index);
/* 1830 */     if (processed) return processed;
/*      */     
/* 1832 */     processed = fillAttribStatDetails(attrib, "retaliationElemental", pos, value, index);
/* 1833 */     if (processed) return processed;
/*      */     
/* 1835 */     processed = fillAttribStatDetails(attrib, "retaliationFear", pos, value, index);
/* 1836 */     if (processed) return processed;
/*      */     
/* 1838 */     processed = fillAttribStatDetails(attrib, "retaliationFire", pos, value, index);
/* 1839 */     if (processed) return processed;
/*      */     
/* 1841 */     processed = fillAttribStatDetails(attrib, "retaliationFreeze", pos, value, index);
/* 1842 */     if (processed) return processed;
/*      */     
/* 1844 */     processed = fillAttribStatDetails(attrib, "retaliationLife", pos, value, index);
/* 1845 */     if (processed) return processed;
/*      */     
/* 1847 */     processed = fillAttribStatDetails(attrib, "retaliationLightning", pos, value, index);
/* 1848 */     if (processed) return processed;
/*      */     
/* 1850 */     processed = fillAttribStatDetails(attrib, "retaliationPercentCurrentLife", pos, value, index);
/* 1851 */     if (processed) return processed;
/*      */     
/* 1853 */     processed = fillAttribStatDetails(attrib, "retaliationPetrify", pos, value, index);
/* 1854 */     if (processed) return processed;
/*      */     
/* 1856 */     processed = fillAttribStatDetails(attrib, "retaliationPhysical", pos, value, index);
/* 1857 */     if (processed) return processed;
/*      */     
/* 1859 */     processed = fillAttribStatDetails(attrib, "retaliationPierce", pos, value, index);
/* 1860 */     if (processed) return processed;
/*      */     
/* 1862 */     processed = fillAttribStatDetails(attrib, "retaliationPierceRatio", pos, value, index);
/* 1863 */     if (processed) return processed;
/*      */     
/* 1865 */     processed = fillAttribStatDetails(attrib, "retaliationPoison", pos, value, index);
/* 1866 */     if (processed) return processed;
/*      */     
/* 1868 */     processed = fillAttribStatDetails(attrib, "retaliationSleep", pos, value, index);
/* 1869 */     if (processed) return processed;
/*      */     
/* 1871 */     processed = fillAttribStatDetails(attrib, "retaliationSlowAttackSpeed", pos, value, index);
/* 1872 */     if (processed) return processed;
/*      */     
/* 1874 */     processed = fillAttribStatDetails(attrib, "retaliationSlowBleeding", pos, value, index);
/* 1875 */     if (processed) return processed;
/*      */     
/* 1877 */     processed = fillAttribStatDetails(attrib, "retaliationSlowCold", pos, value, index);
/* 1878 */     if (processed) return processed;
/*      */     
/* 1880 */     processed = fillAttribStatDetails(attrib, "retaliationSlowDefensiveAbility", pos, value, index);
/* 1881 */     if (processed) return processed;
/*      */     
/* 1883 */     processed = fillAttribStatDetails(attrib, "retaliationSlowFire", pos, value, index);
/* 1884 */     if (processed) return processed;
/*      */     
/* 1886 */     processed = fillAttribStatDetails(attrib, "retaliationSlowLife", pos, value, index);
/* 1887 */     if (processed) return processed;
/*      */     
/* 1889 */     processed = fillAttribStatDetails(attrib, "retaliationSlowLifeLeach", pos, value, index);
/* 1890 */     if (processed) return processed;
/*      */     
/* 1892 */     processed = fillAttribStatDetails(attrib, "retaliationSlowLightning", pos, value, index);
/* 1893 */     if (processed) return processed;
/*      */     
/* 1895 */     processed = fillAttribStatDetails(attrib, "retaliationSlowManaLeach", pos, value, index);
/* 1896 */     if (processed) return processed;
/*      */     
/* 1898 */     processed = fillAttribStatDetails(attrib, "retaliationSlowOffensiveAbility", pos, value, index);
/* 1899 */     if (processed) return processed;
/*      */     
/* 1901 */     processed = fillAttribStatDetails(attrib, "retaliationSlowOffensiveReduction", pos, value, index);
/* 1902 */     if (processed) return processed;
/*      */     
/* 1904 */     processed = fillAttribStatDetails(attrib, "retaliationSlowPhysical", pos, value, index);
/* 1905 */     if (processed) return processed;
/*      */     
/* 1907 */     processed = fillAttribStatDetails(attrib, "retaliationSlowPoison", pos, value, index);
/* 1908 */     if (processed) return processed;
/*      */     
/* 1910 */     processed = fillAttribStatDetails(attrib, "retaliationSlowRunSpeed", pos, value, index);
/* 1911 */     if (processed) return processed;
/*      */     
/* 1913 */     processed = fillAttribStatDetails(attrib, "retaliationStun", pos, value, index);
/* 1914 */     if (processed) return processed;
/*      */     
/* 1916 */     processed = fillAttribStatDetails(attrib, "retaliationTotalDamage", pos, value, index);
/* 1917 */     if (processed) return processed;
/*      */     
/* 1919 */     processed = fillAttribStatDetails(attrib, "retaliationTrap", pos, value, index);
/* 1920 */     if (processed) return processed;
/*      */     
/* 1922 */     if (!processed) {
/* 1923 */       int i = parseInt(value);
/*      */       
/* 1925 */       logTag(attrib, i, this.records[pos]);
/*      */     } 
/*      */     
/* 1928 */     return processed;
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
/* 2027 */     if (value == 0)
/*      */       return; 
/* 2029 */     if (!attrib.startsWith("character") && 
/* 2030 */       !attrib.startsWith("defensive") && 
/* 2031 */       !attrib.startsWith("offensive") && 
/* 2032 */       !attrib.startsWith("retaliation")) {
/*      */       return;
/*      */     }
/*      */     
/* 2036 */     if (attrib.equals("characterBaseAttackSpeedTag") || attrib
/* 2037 */       .equals("characterAttributeEquations") || attrib
/* 2038 */       .equals("characterGenderProfile") || attrib
/* 2039 */       .equals("characterRacialProfile") || attrib
/* 2040 */       .equals("characterRunSpeed") || attrib
/* 2041 */       .equals("characterRunSpeedJitter") || attrib
/* 2042 */       .equals("characterSpellCastSpeed") || attrib
/* 2043 */       .equals("defensiveAbilityDamageFxPak") || attrib
/* 2044 */       .equals("defensiveManaBurn") || attrib
/* 2045 */       .equals("defensiveReductionDamageFxPak") || attrib
/* 2046 */       .equals("offensiveAbilityDamageFxPak") || attrib
/* 2047 */       .equals("offensiveReductionDamageFxPak")) {
/*      */       return;
/*      */     }
/*      */     
/* 2051 */     Object[] args = { attrib };
/* 2052 */     String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_UNKNOWN", args);
/* 2053 */     GDStashFrame.messageToList(msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fillEngineGame(String attrib, int pos, String value, int index) {
/* 2063 */     int i = 0;
/* 2064 */     boolean error = false;
/*      */     
/*      */     try {
/* 2067 */       i = parseInt(value);
/*      */     }
/* 2069 */     catch (NumberFormatException ex) {
/* 2070 */       i = 0;
/* 2071 */       error = true;
/*      */     } 
/*      */     
/* 2074 */     if (attrib.equals("factionAlternateNeutralTag")) {
/* 2075 */       this.records[pos].setFactionAltNeutralTag(value);
/*      */       
/* 2077 */       return true;
/*      */     } 
/*      */     
/* 2080 */     if (attrib.startsWith("factionValue")) {
/*      */       
/* 2082 */       String s = attrib.substring(12);
/* 2083 */       int idx = parseInt(s);
/*      */       
/* 2085 */       this.records[pos].addFactionReputationValue(idx, i);
/*      */       
/* 2087 */       return true;
/*      */     } 
/*      */     
/* 2090 */     if (attrib.startsWith("factionTag")) {
/*      */       
/* 2092 */       String s = attrib.substring(10);
/* 2093 */       int idx = parseInt(s);
/*      */       
/* 2095 */       this.records[pos].addFactionReputationStateTag(idx, value);
/*      */       
/* 2097 */       return true;
/*      */     } 
/*      */     
/* 2100 */     if (attrib.equals("skillMasteryTierLevel")) {
/* 2101 */       this.records[pos].addMasteryTier(index, i);
/*      */       
/* 2103 */       return true;
/*      */     } 
/*      */     
/* 2106 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillFaction(String attrib, int pos, String value) {
/* 2110 */     if (attrib.equals("bountyEnabled")) {
/* 2111 */       this.records[pos].setFactionBountyEnabled(value.equals("1"));
/*      */       
/* 2113 */       return true;
/*      */     } 
/*      */     
/* 2116 */     if (attrib.startsWith("questEnabled")) {
/* 2117 */       this.records[pos].setFactionQuestEnabled(value.equals("1"));
/*      */       
/* 2119 */       return true;
/*      */     } 
/*      */     
/* 2122 */     if (attrib.startsWith("myFaction")) {
/* 2123 */       this.records[pos].setFactionTag(value);
/*      */       
/* 2125 */       return true;
/*      */     } 
/*      */     
/* 2128 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillMerchant(String attrib, int pos, String value) {
/* 2132 */     if (attrib.equals("factions")) {
/* 2133 */       this.records[pos].setMerchantFactionID(value);
/*      */       
/* 2135 */       return true;
/*      */     } 
/*      */     
/* 2138 */     if (attrib.startsWith("marketFileName")) {
/* 2139 */       this.records[pos].setMerchantTableSetID(value);
/*      */       
/* 2141 */       return true;
/*      */     } 
/*      */     
/* 2144 */     if (attrib.startsWith("friendlyNormalTable")) {
/* 2145 */       this.records[pos].addMerchantTableSetTableID(value);
/*      */       
/* 2147 */       return true;
/*      */     } 
/*      */     
/* 2150 */     if (attrib.startsWith("respectedNormalTable")) {
/* 2151 */       this.records[pos].addMerchantTableSetTableID(value);
/*      */       
/* 2153 */       return true;
/*      */     } 
/*      */     
/* 2156 */     if (attrib.startsWith("honoredNormalTable")) {
/* 2157 */       this.records[pos].addMerchantTableSetTableID(value);
/*      */       
/* 2159 */       return true;
/*      */     } 
/*      */     
/* 2162 */     if (attrib.startsWith("reveredNormalTable")) {
/* 2163 */       this.records[pos].addMerchantTableSetTableID(value);
/*      */       
/* 2165 */       return true;
/*      */     } 
/*      */     
/* 2168 */     if (attrib.startsWith("marketStaticItems")) {
/* 2169 */       this.records[pos].addMerchantTableItemID(value);
/*      */       
/* 2171 */       return true;
/*      */     } 
/*      */     
/* 2174 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillEnginePlayer(String attrib, int pos, String value, int index) throws GDParseException {
/* 2178 */     int i = 0;
/* 2179 */     boolean error = false;
/*      */     
/*      */     try {
/* 2182 */       i = parseInt(value);
/*      */     }
/* 2184 */     catch (NumberFormatException ex) {
/* 2185 */       i = 0;
/* 2186 */       error = true;
/*      */     } 
/*      */     
/* 2189 */     if (attrib.equals("characterDexterity")) {
/* 2190 */       if (error) {
/* 2191 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2192 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2194 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2196 */       this.records[pos].setPlayerBaseDex(i);
/*      */       
/* 2198 */       return true;
/*      */     } 
/*      */     
/* 2201 */     if (attrib.equals("characterIntelligence")) {
/* 2202 */       if (error) {
/* 2203 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2204 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2206 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2208 */       this.records[pos].setPlayerBaseInt(i);
/*      */       
/* 2210 */       return true;
/*      */     } 
/*      */     
/* 2213 */     if (attrib.equals("characterLife")) {
/* 2214 */       if (error) {
/* 2215 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2216 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2218 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2220 */       this.records[pos].setPlayerBaseLife(i);
/*      */       
/* 2222 */       return true;
/*      */     } 
/*      */     
/* 2225 */     if (attrib.equals("characterMana")) {
/* 2226 */       if (error) {
/* 2227 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2228 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2230 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2232 */       this.records[pos].setPlayerBaseMana(i);
/*      */       
/* 2234 */       return true;
/*      */     } 
/*      */     
/* 2237 */     if (attrib.equals("characterModifierPoints")) {
/* 2238 */       if (error) {
/* 2239 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2240 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2242 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2244 */       this.records[pos].addLevelStatPoints(i, index);
/*      */       
/* 2246 */       return true;
/*      */     } 
/*      */     
/* 2249 */     if (attrib.equals("characterStrength")) {
/* 2250 */       if (error) {
/* 2251 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2252 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2254 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2256 */       this.records[pos].setPlayerBaseStr(i);
/*      */       
/* 2258 */       return true;
/*      */     } 
/*      */     
/* 2261 */     if (attrib.equals("dexterityIncrement")) {
/* 2262 */       if (error) {
/* 2263 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2264 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2266 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2268 */       this.records[pos].setPlayerIncDex(i);
/*      */       
/* 2270 */       return true;
/*      */     } 
/*      */     
/* 2273 */     if (attrib.equals("experienceLevelEquation")) {
/*      */       
/* 2275 */       this.records[pos].setXPFormula(value.toUpperCase(GDConstants.LOCALE_US));
/*      */       
/* 2277 */       return true;
/*      */     } 
/*      */     
/* 2280 */     if (attrib.equals("intelligenceIncrement")) {
/* 2281 */       if (error) {
/* 2282 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2283 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2285 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2287 */       this.records[pos].setPlayerIncInt(i);
/*      */       
/* 2289 */       return true;
/*      */     } 
/*      */     
/* 2292 */     if (attrib.equals("lifeIncrement")) {
/* 2293 */       if (error) {
/* 2294 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2295 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2297 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2299 */       this.records[pos].setPlayerIncLife(i);
/*      */       
/* 2301 */       return true;
/*      */     } 
/*      */     
/* 2304 */     if (attrib.equals("manaIncrement")) {
/* 2305 */       if (error) {
/* 2306 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2307 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2309 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2311 */       this.records[pos].setPlayerIncMana(i);
/*      */       
/* 2313 */       return true;
/*      */     } 
/*      */     
/* 2316 */     if (attrib.equals("maxDevotionPoints")) {
/* 2317 */       if (error) {
/* 2318 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2319 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2321 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2323 */       this.records[pos].setPlayerMaxDevotion(i);
/*      */       
/* 2325 */       return true;
/*      */     } 
/*      */     
/* 2328 */     if (attrib.equals("maxPlayerLevel")) {
/* 2329 */       if (error) {
/* 2330 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2331 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2333 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2335 */       this.records[pos].setPlayerMaxLevel(i);
/*      */       
/* 2337 */       return true;
/*      */     } 
/*      */     
/* 2340 */     if (attrib.equals("skillModifierPoints")) {
/* 2341 */       if (error) {
/* 2342 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2343 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2345 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2347 */       this.records[pos].addLevelSkillPoints(i, index);
/*      */       
/* 2349 */       return true;
/*      */     } 
/*      */     
/* 2352 */     if (attrib.startsWith("skillTree")) {
/* 2353 */       this.records[pos].addMastery(attrib, value);
/*      */       
/* 2355 */       return true;
/*      */     } 
/*      */     
/* 2358 */     if (attrib.equals("strengthIncrement")) {
/* 2359 */       if (error) {
/* 2360 */         Object[] args = { this.records[pos].getFileName(), attrib, value };
/* 2361 */         String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_INVALID_VALUE", args);
/*      */         
/* 2363 */         GDMsgLogger.addError(msg);
/*      */       } 
/* 2365 */       this.records[pos].setPlayerIncStr(i);
/*      */       
/* 2367 */       return true;
/*      */     } 
/*      */     
/* 2370 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribInventoryGrid(String attrib, int pos, String value) {
/* 2374 */     int i = 0;
/* 2375 */     boolean error = false;
/*      */     
/*      */     try {
/* 2378 */       i = parseInt(value);
/*      */     }
/* 2380 */     catch (NumberFormatException ex) {
/* 2381 */       i = 0;
/* 2382 */       error = true;
/*      */     } 
/*      */     
/* 2385 */     if (attrib.equals("inventoryX")) {
/* 2386 */       this.records[pos].setXOffset(i);
/*      */       
/* 2388 */       return true;
/*      */     } 
/*      */     
/* 2391 */     if (attrib.equals("inventoryXSize")) {
/* 2392 */       this.records[pos].setXSize(i);
/*      */       
/* 2394 */       return true;
/*      */     } 
/*      */     
/* 2397 */     if (attrib.equals("inventoryY")) {
/* 2398 */       this.records[pos].setYOffset(i);
/*      */       
/* 2400 */       return true;
/*      */     } 
/*      */     
/* 2403 */     if (attrib.equals("inventoryYSize")) {
/* 2404 */       this.records[pos].setYSize(i);
/*      */       
/* 2406 */       return true;
/*      */     } 
/*      */     
/* 2409 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribCaravanWindow(String attrib, int pos, String value) {
/* 2413 */     int i = 0;
/* 2414 */     boolean error = false;
/*      */     
/*      */     try {
/* 2417 */       i = parseInt(value);
/*      */     }
/* 2419 */     catch (NumberFormatException ex) {
/* 2420 */       i = 0;
/* 2421 */       error = true;
/*      */     } 
/*      */     
/* 2424 */     if (attrib.equals("InventoryHeight")) {
/* 2425 */       this.records[pos].setYSize(i);
/*      */       
/* 2427 */       return true;
/*      */     } 
/*      */     
/* 2430 */     if (attrib.equals("InventoryWidth")) {
/* 2431 */       this.records[pos].setXSize(i);
/*      */       
/* 2433 */       return true;
/*      */     } 
/*      */     
/* 2436 */     if (attrib.equals("WindowLocationX")) {
/* 2437 */       this.records[pos].setXOffset(i);
/*      */       
/* 2439 */       return true;
/*      */     } 
/*      */     
/* 2442 */     if (attrib.equals("WindowLocationY")) {
/* 2443 */       this.records[pos].setYOffset(i);
/*      */       
/* 2445 */       return true;
/*      */     } 
/*      */     
/* 2448 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribConstellation(String attrib, int pos, String value) {
/* 2452 */     int i = 0;
/* 2453 */     boolean error = false;
/*      */     
/*      */     try {
/* 2456 */       i = parseInt(value);
/*      */     }
/* 2458 */     catch (NumberFormatException ex) {
/* 2459 */       i = 0;
/* 2460 */       error = true;
/*      */     } 
/*      */     
/* 2463 */     if (attrib.equals("constellationDisplayTag")) {
/* 2464 */       this.records[pos].setConstellationNameTag(value);
/*      */       
/* 2466 */       return true;
/*      */     } 
/*      */     
/* 2469 */     if (attrib.equals("constellationInfoTag")) {
/* 2470 */       this.records[pos].getConstellationInfoTag(value);
/*      */       
/* 2472 */       return true;
/*      */     } 
/*      */     
/* 2475 */     if (attrib.startsWith("affinityGivenName")) {
/* 2476 */       this.records[pos].addConstellationAffinityName(attrib, value);
/*      */       
/* 2478 */       return true;
/*      */     } 
/*      */     
/* 2481 */     if (attrib.startsWith("affinityRequiredName")) {
/* 2482 */       this.records[pos].addConstellationAffinityName(attrib, value);
/*      */       
/* 2484 */       return true;
/*      */     } 
/*      */     
/* 2487 */     if (attrib.startsWith("affinityGiven")) {
/* 2488 */       this.records[pos].addConstellationAffinityPoints(attrib, i);
/*      */       
/* 2490 */       return true;
/*      */     } 
/*      */     
/* 2493 */     if (attrib.startsWith("affinityRequired")) {
/* 2494 */       this.records[pos].addConstellationAffinityPoints(attrib, i);
/*      */       
/* 2496 */       return true;
/*      */     } 
/*      */     
/* 2499 */     if (attrib.startsWith("devotionButton")) {
/* 2500 */       this.records[pos].addConstellationStar(attrib, value);
/*      */       
/* 2502 */       return true;
/*      */     } 
/*      */     
/* 2505 */     if (attrib.equals("skillName")) {
/* 2506 */       this.records[pos].setButtonSkillID(value);
/*      */       
/* 2508 */       return true;
/*      */     } 
/*      */     
/* 2511 */     if (attrib.equals("bitmapPositionX")) {
/* 2512 */       this.records[pos].setPosX(i);
/*      */       
/* 2514 */       return true;
/*      */     } 
/*      */     
/* 2517 */     if (attrib.equals("bitmapPositionY")) {
/* 2518 */       this.records[pos].setPosY(i);
/*      */       
/* 2520 */       return true;
/*      */     } 
/*      */     
/* 2523 */     if (attrib.equals("skillOffsetX")) {
/* 2524 */       this.records[pos].setOffsetX(i);
/*      */       
/* 2526 */       return true;
/*      */     } 
/*      */     
/* 2529 */     if (attrib.equals("skillOffsetY")) {
/* 2530 */       this.records[pos].setOffsetY(i);
/*      */       
/* 2532 */       return true;
/*      */     } 
/*      */     
/* 2535 */     if (attrib.equals("isCircular")) {
/* 2536 */       this.records[pos].setCircularButton(value.equals("1"));
/*      */       
/* 2538 */       return true;
/*      */     } 
/*      */     
/* 2541 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribBitmap(String attrib, int pos, String value) {
/* 2545 */     int i = 0;
/* 2546 */     boolean error = false;
/*      */     
/*      */     try {
/* 2549 */       i = parseInt(value);
/*      */     }
/* 2551 */     catch (NumberFormatException ex) {
/* 2552 */       i = 0;
/* 2553 */       error = true;
/*      */     } 
/*      */     
/* 2556 */     if (attrib.equals("bitmapName") || attrib
/* 2557 */       .equals("bitmapFullName")) {
/* 2558 */       this.records[pos].setBitmapID(value);
/*      */       
/* 2560 */       return true;
/*      */     } 
/*      */     
/* 2563 */     if (attrib.equals("bitmapPositionX")) {
/* 2564 */       this.records[pos].setPosX(i);
/*      */       
/* 2566 */       return true;
/*      */     } 
/*      */     
/* 2569 */     if (attrib.equals("bitmapPositionY")) {
/* 2570 */       this.records[pos].setPosY(i);
/*      */       
/* 2572 */       return true;
/*      */     } 
/*      */     
/* 2575 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribClassTable(String attrib, int pos, String value) {
/* 2579 */     int i = 0;
/* 2580 */     boolean error = false;
/*      */     
/*      */     try {
/* 2583 */       i = parseInt(value);
/*      */     }
/* 2585 */     catch (NumberFormatException ex) {
/* 2586 */       i = 0;
/* 2587 */       error = true;
/*      */     } 
/*      */     
/* 2590 */     if (attrib.equals("masteryBar")) {
/* 2591 */       this.records[pos].setMasteryBarID(value);
/*      */       
/* 2593 */       return true;
/*      */     } 
/*      */     
/* 2596 */     if (attrib.equals("skillPaneBaseBitmap")) {
/* 2597 */       this.records[pos].setSkillPaneID(value);
/*      */       
/* 2599 */       return true;
/*      */     } 
/*      */     
/* 2602 */     if (attrib.equals("skillPaneMasteryBitmap")) {
/* 2603 */       this.records[pos].setMasteryBitmapID(value);
/*      */       
/* 2605 */       return true;
/*      */     } 
/*      */     
/* 2608 */     if (attrib.equals("skillTabTitle")) {
/* 2609 */       this.records[pos].setTitleTag(value);
/*      */       
/* 2611 */       return true;
/*      */     } 
/*      */     
/* 2614 */     if (attrib.equals("tabSkillButtons")) {
/* 2615 */       this.records[pos].addSkillButton(value);
/*      */       
/* 2617 */       return true;
/*      */     } 
/*      */     
/* 2620 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribSkillMaster(String attrib, int pos, String value) {
/* 2624 */     if (attrib.startsWith("skillCtrlPane")) {
/* 2625 */       this.records[pos].addSkillMaster(value);
/*      */       
/* 2627 */       return true;
/*      */     } 
/*      */     
/* 2630 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribShrine(String attrib, int pos, String value) {
/* 2634 */     if (attrib.equals("journalTag")) {
/* 2635 */       this.records[pos].setShrineName(value);
/*      */       
/* 2637 */       return true;
/*      */     } 
/*      */     
/* 2640 */     if (attrib.equals("normalDisabled")) {
/* 2641 */       this.records[pos].setShrineNormalDisabled(value.equals("1"));
/*      */       
/* 2643 */       return true;
/*      */     } 
/*      */     
/* 2646 */     if (attrib.equals("normalLocked")) {
/* 2647 */       this.records[pos].setShrineNormalLocked(value.equals("1"));
/*      */       
/* 2649 */       return true;
/*      */     } 
/*      */     
/* 2652 */     if (attrib.equals("epicDisabled")) {
/* 2653 */       this.records[pos].setShrineEpicDisabled(value.equals("1"));
/*      */       
/* 2655 */       return true;
/*      */     } 
/*      */     
/* 2658 */     if (attrib.equals("epicLocked")) {
/* 2659 */       this.records[pos].setShrineEpicLocked(value.equals("1"));
/*      */       
/* 2661 */       return true;
/*      */     } 
/*      */     
/* 2664 */     if (attrib.equals("legendaryDisabled")) {
/* 2665 */       this.records[pos].setShrineLegendaryDisabled(value.equals("1"));
/*      */       
/* 2667 */       return true;
/*      */     } 
/*      */     
/* 2670 */     if (attrib.equals("legendaryLocked")) {
/* 2671 */       this.records[pos].setShrineLegendaryLocked(value.equals("1"));
/*      */       
/* 2673 */       return true;
/*      */     } 
/*      */     
/* 2676 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribSkillTree(String attrib, int pos, String value) {
/* 2680 */     if (attrib.startsWith("skillName")) {
/* 2681 */       int i = 0;
/*      */       try {
/* 2683 */         i = parseInt(attrib.substring(9));
/*      */       }
/* 2685 */       catch (NumberFormatException ex) {
/* 2686 */         i = 0;
/*      */       } 
/*      */       
/* 2689 */       this.records[pos].addMasterySkill(value, i);
/*      */       
/* 2691 */       return true;
/*      */     } 
/*      */     
/* 2694 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribSkill(String attrib, int pos, String value, int index) {
/* 2698 */     int i = 0;
/* 2699 */     boolean error = false;
/*      */     
/*      */     try {
/* 2702 */       i = parseInt(value);
/*      */     }
/* 2704 */     catch (NumberFormatException ex) {
/* 2705 */       i = 0;
/* 2706 */       error = true;
/*      */     } 
/*      */     
/* 2709 */     if (attrib.equals("buffSkillName")) {
/* 2710 */       this.records[pos].setSkillBuffID(value);
/*      */       
/* 2712 */       return true;
/*      */     } 
/*      */     
/* 2715 */     if (attrib.equals("petSkillName")) {
/* 2716 */       this.records[pos].setPetBonusID(value);
/*      */       
/* 2718 */       return true;
/*      */     } 
/*      */     
/* 2721 */     if (attrib.equals("skillBaseDescription")) {
/* 2722 */       this.records[pos].setSkillDescription(value);
/*      */       
/* 2724 */       return true;
/*      */     } 
/*      */     
/* 2727 */     if (attrib.equals("skillDisplayName")) {
/* 2728 */       this.records[pos].setSkillNameTag(value);
/*      */       
/* 2730 */       return true;
/*      */     } 
/*      */     
/* 2733 */     if (attrib.equals("skillMaxLevel")) {
/* 2734 */       this.records[pos].setSkillMaxLevel(i);
/*      */       
/* 2736 */       return true;
/*      */     } 
/*      */     
/* 2739 */     if (attrib.equals("skillTier")) {
/* 2740 */       this.records[pos].setSkillTier(i);
/*      */       
/* 2742 */       return true;
/*      */     } 
/*      */     
/* 2745 */     if (attrib.equals("skillDownBitmapName")) {
/* 2746 */       this.records[pos].setSkillBitmapDownID(value);
/*      */       
/* 2748 */       return true;
/*      */     } 
/*      */     
/* 2751 */     if (attrib.equals("skillUpBitmapName")) {
/* 2752 */       this.records[pos].setSkillBitmapUpID(value);
/*      */       
/* 2754 */       return true;
/*      */     } 
/*      */     
/* 2757 */     if (attrib.equals("skillConnectionOff")) {
/* 2758 */       this.records[pos].addSkillConnectionOff(value, index);
/*      */       
/* 2760 */       return true;
/*      */     } 
/*      */     
/* 2763 */     if (attrib.equals("skillConnectionOn")) {
/* 2764 */       this.records[pos].addSkillConnectionOn(value, index);
/*      */       
/* 2766 */       return true;
/*      */     } 
/*      */     
/* 2769 */     if (attrib.equals("skillDependancyAll")) {
/* 2770 */       this.records[pos].setDependencyAll((i != 0));
/*      */       
/* 2772 */       return true;
/*      */     } 
/*      */     
/* 2775 */     if (attrib.equals("skillDependancy")) {
/* 2776 */       this.records[pos].addSkillDependency(value);
/*      */       
/* 2778 */       return true;
/*      */     } 
/*      */     
/* 2781 */     if (attrib.equals("skillExperienceLevels")) {
/* 2782 */       this.records[pos].addSkillXPLevel(value);
/*      */       
/* 2784 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 2788 */     if (attrib.equals("skillChargeLevel")) {
/* 2789 */       this.records[pos].setSkillLevel(i);
/*      */       
/* 2791 */       return true;
/*      */     } 
/*      */     
/* 2794 */     if (attrib.equals("skillChargeDuration")) {
/* 2795 */       this.records[pos].setSkillDuration(i);
/*      */       
/* 2797 */       return true;
/*      */     } 
/*      */     
/* 2800 */     if (attrib.equals("spawnObjects") || attrib
/* 2801 */       .equals("modSpawnObjects")) {
/* 2802 */       this.records[pos].addSpawnPet(index, value);
/*      */       
/* 2804 */       return true;
/*      */     } 
/*      */     
/* 2807 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribSkillBonus(String attrib, int pos, String value, int index) {
/* 2811 */     if (attrib.startsWith("augmentMasteryName") || attrib
/* 2812 */       .startsWith("augmentSkillName") || attrib
/* 2813 */       .equals("itemSkillName") || attrib
/* 2814 */       .equals("augmentAllLevel")) {
/*      */       
/* 2816 */       String s = null;
/* 2817 */       int type = 0;
/* 2818 */       int idx = -1;
/* 2819 */       String val = null;
/*      */       
/* 2821 */       if (attrib.startsWith("augmentMasteryName")) {
/* 2822 */         s = attrib.substring(18);
/*      */         
/* 2824 */         type = 1;
/* 2825 */         idx = parseInt(s);
/* 2826 */         val = value;
/*      */       } 
/*      */       
/* 2829 */       if (attrib.startsWith("augmentSkillName")) {
/* 2830 */         s = attrib.substring(16);
/*      */         
/* 2832 */         type = 2;
/* 2833 */         idx = parseInt(s);
/* 2834 */         val = value;
/*      */       } 
/*      */       
/* 2837 */       if (attrib.equals("itemSkillName")) {
/* 2838 */         type = 3;
/* 2839 */         idx = 1;
/* 2840 */         val = value;
/*      */       } 
/*      */       
/* 2843 */       if (attrib.equals("augmentAllLevel")) {
/* 2844 */         type = 4;
/* 2845 */         idx = 1;
/* 2846 */         val = null;
/*      */       } 
/*      */       
/* 2849 */       DBSkillBonus.addEntity((this.records[pos]).dbSkillBonuses, idx, type, val);
/*      */       
/* 2851 */       if (!attrib.equals("augmentAllLevel")) return true;
/*      */     
/*      */     } 
/* 2854 */     if (attrib.startsWith("augmentMasteryLevel") || attrib
/* 2855 */       .startsWith("augmentSkillLevel") || attrib
/* 2856 */       .equals("itemSkillLevel") || attrib
/* 2857 */       .equals("augmentAllLevel")) {
/*      */       
/* 2859 */       String s = null;
/* 2860 */       int type = 0;
/* 2861 */       int idx = -1;
/*      */       
/* 2863 */       if (attrib.startsWith("augmentMasteryLevel")) {
/* 2864 */         s = attrib.substring(19);
/*      */         
/* 2866 */         type = 1;
/* 2867 */         idx = parseInt(s);
/*      */       } 
/*      */       
/* 2870 */       if (attrib.startsWith("augmentSkillLevel")) {
/* 2871 */         s = attrib.substring(17);
/*      */         
/* 2873 */         type = 2;
/* 2874 */         idx = parseInt(s);
/*      */       } 
/*      */       
/* 2877 */       if (attrib.equals("itemSkillLevel")) {
/* 2878 */         type = 3;
/* 2879 */         idx = 1;
/*      */       } 
/*      */       
/* 2882 */       if (attrib.equals("augmentAllLevel")) {
/* 2883 */         type = 4;
/* 2884 */         idx = 1;
/*      */       } 
/*      */       
/* 2887 */       int iVal = parseInt(value);
/*      */       
/* 2889 */       DBSkillBonus.addValue((this.records[pos]).dbSkillBonuses, idx, type, iVal, index);
/*      */       
/* 2891 */       return true;
/*      */     } 
/*      */     
/* 2894 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribPet(String attrib, int pos, String value, int index) {
/* 2898 */     if (attrib.equals("charLevel")) {
/* 2899 */       this.records[pos].setPetFormulaLevel(value);
/*      */       
/* 2901 */       return true;
/*      */     } 
/*      */     
/* 2904 */     if (attrib.equals("characterAttributeEquations")) {
/* 2905 */       this.records[pos].setPetBioID(value);
/*      */       
/* 2907 */       return true;
/*      */     } 
/*      */     
/* 2910 */     if (attrib.equals("description")) {
/* 2911 */       this.records[pos].setPetNameTag(value);
/*      */       
/* 2913 */       return true;
/*      */     } 
/*      */     
/* 2916 */     if (attrib.equals("dyingSkillName")) {
/* 2917 */       this.records[pos].setPetDieSkillID(value);
/*      */       
/* 2919 */       return true;
/*      */     } 
/*      */     
/* 2922 */     if (attrib.startsWith("skillName")) {
/*      */       
/* 2924 */       String s = attrib.substring(9);
/* 2925 */       int i = parseInt(s);
/*      */       
/* 2927 */       this.records[pos].addPetSkillsSkillID(i, value);
/*      */       
/* 2929 */       return true;
/*      */     } 
/*      */     
/* 2932 */     if (attrib.startsWith("skillLevel")) {
/*      */       
/* 2934 */       String s = attrib.substring(10);
/* 2935 */       int i = parseInt(s);
/*      */       
/* 2937 */       this.records[pos].addPetSkillsFormulaLevel(i, value);
/*      */       
/* 2939 */       return true;
/*      */     } 
/*      */     
/* 2942 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribPetBio(String attrib, int pos, String value, int index) {
/* 2946 */     if (attrib.equals("characterLife")) {
/* 2947 */       this.records[pos].setBioFormulaLife(value);
/*      */       
/* 2949 */       return true;
/*      */     } 
/*      */     
/* 2952 */     if (attrib.equals("characterMana")) {
/* 2953 */       this.records[pos].setBioFormulaMana(value);
/*      */       
/* 2955 */       return true;
/*      */     } 
/*      */     
/* 2958 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribController(String attrib, int pos, String value) {
/* 2962 */     int i = 0;
/* 2963 */     boolean error = false;
/*      */     
/*      */     try {
/* 2966 */       i = parseInt(value);
/*      */     }
/* 2968 */     catch (NumberFormatException ex) {
/* 2969 */       i = 0;
/* 2970 */       error = true;
/*      */     } 
/*      */     
/* 2973 */     if (attrib.equals("chanceToRun")) {
/* 2974 */       this.records[pos].setTriggerChance(i);
/*      */       
/* 2976 */       return true;
/*      */     } 
/*      */     
/* 2979 */     if (attrib.equals("triggerType")) {
/* 2980 */       this.records[pos].setTriggerType(value);
/*      */       
/* 2982 */       return true;
/*      */     } 
/*      */     
/* 2985 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribItemSet(String attrib, int pos, String value) {
/* 2989 */     if (attrib.equals("setDescription")) {
/* 2990 */       this.records[pos].setItemSetDescriptionTag(value);
/*      */       
/* 2992 */       return true;
/*      */     } 
/*      */     
/* 2995 */     if (attrib.equals("setMembers")) {
/* 2996 */       this.records[pos].addItemSetItemID(value);
/*      */       
/* 2998 */       return true;
/*      */     } 
/*      */     
/* 3001 */     if (attrib.equals("setName")) {
/* 3002 */       this.records[pos].setItemSetNameTag(value);
/*      */       
/* 3004 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3009 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribLootTable(String attrib, int pos, String value) {
/* 3013 */     if (attrib.equals("bothPrefixSuffix")) {
/* 3014 */       this.records[pos].setTableNormalPrefixSuffix((parseInt(value) > 0));
/*      */       
/* 3016 */       return true;
/*      */     } 
/*      */     
/* 3019 */     if (attrib.equals("normalPrefixRareSuffix")) {
/* 3020 */       this.records[pos].setTableNormalPrefixRareSuffix((parseInt(value) > 0));
/*      */       
/* 3022 */       return true;
/*      */     } 
/*      */     
/* 3025 */     if (attrib.equals("rarePrefixNormalSuffix")) {
/* 3026 */       this.records[pos].setTableRarePrefixNormalSuffix((parseInt(value) > 0));
/*      */       
/* 3028 */       return true;
/*      */     } 
/*      */     
/* 3031 */     if (attrib.equals("rareBothPrefixSuffix")) {
/* 3032 */       this.records[pos].setTableRarePrefixSuffix((parseInt(value) > 0));
/*      */       
/* 3034 */       return true;
/*      */     } 
/*      */     
/* 3037 */     if (attrib.startsWith("lootName")) {
/* 3038 */       this.records[pos].addTableItemID(value);
/*      */       
/* 3040 */       return true;
/*      */     } 
/*      */     
/* 3043 */     if (attrib.startsWith("prefixTableLevelMax") || attrib
/* 3044 */       .startsWith("suffixTableLevelMax") || attrib
/* 3045 */       .startsWith("rarePrefixTableLevelMax") || attrib
/* 3046 */       .startsWith("rareSuffixTableLevelMax")) {
/* 3047 */       String search = "TableLevelMax";
/* 3048 */       int len = search.length();
/* 3049 */       int p = attrib.indexOf(search);
/*      */       
/* 3051 */       String pre = attrib.substring(0, p);
/* 3052 */       String num = attrib.substring(p + len);
/* 3053 */       int i = parseInt(num);
/*      */       
/* 3055 */       int type = 0;
/*      */       
/* 3057 */       if (pre.equals("prefix")) type = 1; 
/* 3058 */       if (pre.equals("suffix")) type = 2; 
/* 3059 */       if (pre.equals("rarePrefix")) type = 3; 
/* 3060 */       if (pre.equals("rareSuffix")) type = 4;
/*      */       
/* 3062 */       int iVal = parseInt(value);
/*      */       
/* 3064 */       this.records[pos].addTableMaxLevel(i, type, iVal);
/*      */       
/* 3066 */       return true;
/*      */     } 
/*      */     
/* 3069 */     if (attrib.startsWith("prefixTableLevelMin") || attrib
/* 3070 */       .startsWith("suffixTableLevelMin") || attrib
/* 3071 */       .startsWith("rarePrefixTableLevelMin") || attrib
/* 3072 */       .startsWith("rareSuffixTableLevelMin")) {
/* 3073 */       String search = "TableLevelMin";
/* 3074 */       int len = search.length();
/* 3075 */       int p = attrib.indexOf(search);
/*      */       
/* 3077 */       String pre = attrib.substring(0, p);
/* 3078 */       String num = attrib.substring(p + len);
/* 3079 */       int i = parseInt(num);
/*      */       
/* 3081 */       int type = 0;
/*      */       
/* 3083 */       if (pre.equals("prefix")) type = 1; 
/* 3084 */       if (pre.equals("suffix")) type = 2; 
/* 3085 */       if (pre.equals("rarePrefix")) type = 3; 
/* 3086 */       if (pre.equals("rareSuffix")) type = 4;
/*      */       
/* 3088 */       int iVal = parseInt(value);
/*      */       
/* 3090 */       this.records[pos].addTableMinLevel(i, type, iVal);
/*      */       
/* 3092 */       return true;
/*      */     } 
/*      */     
/* 3095 */     if (attrib.startsWith("prefixTableName") || attrib
/* 3096 */       .startsWith("suffixTableName") || attrib
/* 3097 */       .startsWith("rarePrefixTableName") || attrib
/* 3098 */       .startsWith("rareSuffixTableName")) {
/* 3099 */       String search = "TableName";
/* 3100 */       int len = search.length();
/* 3101 */       int p = attrib.indexOf(search);
/*      */       
/* 3103 */       String pre = attrib.substring(0, p);
/* 3104 */       String num = attrib.substring(p + len);
/* 3105 */       int i = parseInt(num);
/*      */       
/* 3107 */       int type = 0;
/*      */       
/* 3109 */       if (pre.equals("prefix")) type = 1; 
/* 3110 */       if (pre.equals("suffix")) type = 2; 
/* 3111 */       if (pre.equals("rarePrefix")) type = 3; 
/* 3112 */       if (pre.equals("rareSuffix")) type = 4;
/*      */       
/* 3114 */       this.records[pos].addTableAffixSetID(i, type, value);
/*      */       
/* 3116 */       return true;
/*      */     } 
/*      */     
/* 3119 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribLootTableSet(String attrib, int pos, String value) {
/* 3123 */     if (attrib.equals("levels")) {
/* 3124 */       int iVal = parseInt(value);
/*      */       
/* 3126 */       this.records[pos].addTableSetMinLevel(iVal);
/*      */       
/* 3128 */       return true;
/*      */     } 
/*      */     
/* 3131 */     if (attrib.equals("records")) {
/* 3132 */       this.records[pos].addTableSetTableID(value);
/*      */       
/* 3134 */       return true;
/*      */     } 
/*      */     
/* 3137 */     return false;
/*      */   }
/*      */   
/*      */   private boolean fillAttribFormula(String attrib, int pos, String value) {
/* 3141 */     if (attrib.equals("armorCostEquation") || attrib
/* 3142 */       .equals("jewelryCostEquation") || attrib
/* 3143 */       .equals("offhandCostEquation") || attrib
/* 3144 */       .equals("shieldCostEquation") || attrib
/* 3145 */       .equals("weaponCostEquation") || attrib
/* 3146 */       .equals("weaponMelee2hCostEquation") || attrib
/* 3147 */       .equals("weaponRangedCostEquation") || attrib
/* 3148 */       .equals("weaponRanged2hCostEquation") || attrib
/* 3149 */       .equals("daggerDexterityEquation") || attrib
/* 3150 */       .equals("ranged1hDexterityEquation") || attrib
/* 3151 */       .equals("ranged2hDexterityEquation") || attrib
/* 3152 */       .equals("swordDexterityEquation") || attrib
/* 3153 */       .equals("amuletIntelligenceEquation") || attrib
/* 3154 */       .equals("chestIntelligenceEquation") || attrib
/* 3155 */       .equals("daggerIntelligenceEquation") || attrib
/* 3156 */       .equals("headIntelligenceEquation") || attrib
/* 3157 */       .equals("offhandIntelligenceEquation") || attrib
/* 3158 */       .equals("ringIntelligenceEquation") || attrib
/* 3159 */       .equals("scepterIntelligenceEquation") || attrib
/* 3160 */       .equals("axeStrengthEquation") || attrib
/* 3161 */       .equals("chestStrengthEquation") || attrib
/* 3162 */       .equals("feetStrengthEquation") || attrib
/* 3163 */       .equals("handsStrengthEquation") || attrib
/* 3164 */       .equals("headStrengthEquation") || attrib
/* 3165 */       .equals("legsStrengthEquation") || attrib
/* 3166 */       .equals("maceStrengthEquation") || attrib
/* 3167 */       .equals("melee2hStrengthEquation") || attrib
/* 3168 */       .equals("scepterStrengthEquation") || attrib
/* 3169 */       .equals("shieldStrengthEquation") || attrib
/* 3170 */       .equals("shouldersStrengthEquation") || attrib
/* 3171 */       .equals("waistStrengthEquation")) {
/*      */       
/* 3173 */       this.records[pos].addFormulaSetFormula(attrib, value.toUpperCase(GDConstants.LOCALE_US));
/*      */       
/* 3175 */       return true;
/*      */     } 
/*      */     
/* 3178 */     return false;
/*      */   }
/*      */   
/*      */   public static int getGenderCode(String s) {
/* 3182 */     int code = -1;
/*      */     
/* 3184 */     if (s.equals("[ms]")) code = 0; 
/* 3185 */     if (s.equals("[mp]")) code = 3; 
/* 3186 */     if (s.equals("[fs]")) code = 1; 
/* 3187 */     if (s.equals("[fp]")) code = 4; 
/* 3188 */     if (s.equals("[ns]")) code = 2; 
/* 3189 */     if (s.equals("[np]")) code = 5;
/*      */     
/* 3191 */     return code;
/*      */   }
/*      */   
/*      */   public static String[] getGenderTexts(String text) {
/* 3195 */     String[] genders = new String[6];
/*      */     
/* 3197 */     if (text == null) return genders;
/*      */     
/* 3199 */     String remainder = text;
/* 3200 */     String gender = null;
/* 3201 */     String value = null;
/* 3202 */     int code = -1;
/*      */     
/* 3204 */     if (remainder.startsWith("|"))
/*      */     {
/*      */ 
/*      */       
/* 3208 */       remainder = remainder.substring(2);
/*      */     }
/*      */     
/* 3211 */     if (remainder.startsWith("$"))
/*      */     {
/*      */ 
/*      */       
/* 3215 */       remainder = remainder.substring(1);
/*      */     }
/*      */     
/* 3218 */     while (remainder != null) {
/* 3219 */       int pos = remainder.indexOf("[");
/* 3220 */       if (pos != -1) {
/*      */         
/* 3222 */         gender = remainder.substring(pos, pos + 4);
/* 3223 */         remainder = remainder.substring(pos + 4);
/*      */         
/* 3225 */         pos = remainder.indexOf("[");
/* 3226 */         if (pos != -1) {
/* 3227 */           value = remainder.substring(0, pos);
/* 3228 */           remainder = remainder.substring(pos);
/*      */         } else {
/* 3230 */           value = remainder;
/* 3231 */           remainder = null;
/*      */         } 
/*      */         
/* 3234 */         code = getGenderCode(gender);
/* 3235 */         if (code == -1) code = 0;
/*      */         
/* 3237 */         genders[code] = value;
/*      */         
/*      */         continue;
/*      */       } 
/* 3241 */       value = remainder;
/* 3242 */       remainder = null;
/*      */ 
/*      */ 
/*      */       
/* 3246 */       genders[0] = value;
/* 3247 */       genders[1] = value;
/* 3248 */       genders[2] = value;
/* 3249 */       genders[3] = value;
/* 3250 */       genders[4] = value;
/* 3251 */       genders[5] = value;
/*      */     } 
/*      */ 
/*      */     
/* 3255 */     return genders;
/*      */   }
/*      */   
/*      */   private boolean resolveSkillName(ARZRecord record) {
/* 3259 */     String skill = null;
/*      */     
/* 3261 */     if (record.getPetBonusID() != null) skill = record.getPetBonusID(); 
/* 3262 */     if (record.getSkillBuffID() != null) skill = record.getSkillBuffID();
/*      */     
/* 3264 */     if (skill == null) return false;
/*      */     
/* 3266 */     for (int i = 0; i < this.records.length; ) {
/* 3267 */       if (this.records[i] == null || 
/* 3268 */         this.records[i].getSkillNameTag() == null || 
/* 3269 */         !skill.equals(this.records[i].getFileName())) {
/*      */         i++; continue;
/* 3271 */       }  record.setSkillNameTag(this.records[i].getSkillNameTag());
/*      */       
/* 3273 */       return true;
/*      */     } 
/*      */     
/* 3276 */     return false;
/*      */   }
/*      */   
/*      */   private void resolveGrantedSkillName(ARZRecord record) {
/* 3280 */     for (DBSkillBonus bonus : record.dbSkillBonuses) {
/* 3281 */       if (bonus.getEntity() == null)
/*      */         continue;  int i;
/* 3283 */       for (i = 0; i < this.records.length; i++) {
/* 3284 */         if (this.records[i] != null)
/*      */         {
/* 3286 */           if (this.records[i].getSkillName() != null || this.records[i]
/* 3287 */             .getSkillBuffID() != null || this.records[i]
/* 3288 */             .getPetBonusID() != null)
/*      */           {
/*      */ 
/*      */             
/* 3292 */             if (bonus.getEntity().equals(this.records[i].getFileName())) {
/*      */ 
/*      */ 
/*      */               
/* 3296 */               if (this.records[i].getSkillName() != null) {
/* 3297 */                 bonus.setEntityName(this.records[i].getSkillName());
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
/* 3308 */     for (int i = 0; i < this.records.length; i++) {
/* 3309 */       if (this.records[i] != null)
/*      */       {
/*      */ 
/*      */         
/* 3313 */         if (record.getSkillBuffID().equals(this.records[i].getFileName())) {
/*      */           
/* 3315 */           record.setDevotion(this.records[i].isDevotion());
/*      */           break;
/*      */         }  } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void resolveBonusIncrementFlag(ARZRecord record) {
/* 3322 */     for (DBSkillBonus bonus : record.dbSkillBonuses) {
/* 3323 */       if (bonus.getEntity() == null || 
/* 3324 */         bonus.getValue() < 1)
/*      */         continue;  int i;
/* 3326 */       for (i = 0; i < this.records.length; i++) {
/* 3327 */         if (this.records[i] != null)
/*      */         {
/* 3329 */           if (bonus.getEntity().equals(this.records[i].getFileName())) {
/*      */             
/* 3331 */             this.records[i].setSkillBonusIncrement(true);
/*      */             break;
/*      */           }  } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void resolveModifiedSkillFlag(ARZRecord record) {
/* 3339 */     for (DBSkillModifier modif : record.getSkillModifierList()) {
/* 3340 */       if (modif.getSkillID() == null)
/*      */         continue;  int i;
/* 3342 */       for (i = 0; i < this.records.length; i++) {
/* 3343 */         if (this.records[i] != null)
/*      */         {
/* 3345 */           if (modif.getSkillID().equals(this.records[i].getFileName())) {
/*      */             
/* 3347 */             this.records[i].setSkillModified(true);
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
/* 3585 */     for (int i = 0; i < this.records.length; i++) {
/* 3586 */       if (this.records[i] != null) {
/*      */ 
/*      */         
/* 3589 */         if (this.records[i].getItemSetID() != null) {
/* 3590 */           this.records[i].setItemSetNameTag(
/* 3591 */               getAttribValue(this.records[i].getItemSetID(), "itemSetName"));
/*      */         }
/*      */ 
/*      */         
/* 3595 */         if (this.records[i].getItemSkillLevelFormula() != null) {
/* 3596 */           this.records[i].processItemSkillLevelFormula();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 3601 */     boolean changed = true;
/* 3602 */     while (changed) {
/* 3603 */       changed = false;
/*      */       
/* 3605 */       for (int k = 0; k < this.records.length; k++) {
/* 3606 */         if (this.records[k] != null)
/*      */         {
/* 3608 */           if (this.records[k].isSkill())
/*      */           {
/*      */             
/* 3611 */             if (this.records[k].getSkillNameTag() == null) {
/* 3612 */               boolean b = resolveSkillName(this.records[k]);
/*      */               
/* 3614 */               changed = (changed || b);
/*      */             } 
/*      */           }
/*      */         }
/*      */       } 
/*      */     } 
/*      */     int j;
/* 3621 */     for (j = 0; j < this.records.length; j++) {
/* 3622 */       if (this.records[j] != null)
/*      */       {
/* 3624 */         if ((this.records[j]).dbSkillBonuses != null && 
/* 3625 */           !(this.records[j]).dbSkillBonuses.isEmpty()) {
/* 3626 */           resolveGrantedSkillName(this.records[j]);
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
/* 3637 */     for (j = 0; j < this.records.length; j++) {
/* 3638 */       if (this.records[j] != null)
/*      */       {
/* 3640 */         if (this.records[j].getFileName().startsWith("records/skills/") && 
/* 3641 */           this.records[j].getSkillBuffID() != null) {
/* 3642 */           resolveDevotionFlag(this.records[j]);
/*      */         }
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private String getAttribValue(String filename, String attrib) {
/* 3649 */     String value = null;
/*      */     int i;
/* 3651 */     for (i = 0; i < this.records.length; i++) {
/* 3652 */       if (this.records[i] != null)
/*      */       {
/* 3654 */         if (this.records[i].getFileName().equals(filename)) {
/* 3655 */           value = this.records[i].getItemSetNameTag();
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
/* 3673 */     return value;
/*      */   }
/*      */   
/*      */   public boolean insertData(boolean suppressTagWarning) {
/* 3677 */     return GDDBData.insertData(this.records, suppressTagWarning);
/*      */   }
/*      */   
/*      */   public void extractRecords(String dir) {
/*      */     try {
/* 3682 */       File file = new File(this.filename);
/*      */       
/* 3684 */       if (!file.exists()) {
/* 3685 */         Object[] args = { this.filename };
/* 3686 */         String s = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*      */         
/* 3688 */         throw new FileNotFoundException(s);
/*      */       } 
/*      */       
/* 3691 */       if (!file.isFile()) {
/* 3692 */         Object[] args = { this.filename };
/* 3693 */         String s = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*      */         
/* 3695 */         throw new FileNotFoundException(s);
/*      */       } 
/*      */       
/* 3698 */       if (!file.canRead()) {
/* 3699 */         Object[] args = { this.filename };
/* 3700 */         String s = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_CANNOT_READ", args);
/*      */         
/* 3702 */         throw new IOException(s);
/*      */       } 
/*      */       
/* 3705 */       GDBuffer buffer = new GDRandomAccessBuffer(file);
/*      */       try {
/* 3707 */         this.header = getHeader(buffer);
/* 3708 */         this.strings = getStrings(buffer);
/* 3709 */         this.records = getRecords(buffer);
/*      */         
/* 3711 */         recordsToText(buffer, dir);
/*      */       }
/* 3713 */       catch (IOException ex) {
/* 3714 */         throw ex;
/*      */       } finally {
/*      */         
/* 3717 */         buffer.close();
/*      */         
/* 3719 */         buffer = null;
/*      */       }
/*      */     
/* 3722 */     } catch (Exception ex) {
/* 3723 */       GDMsgLogger.addError(ex);
/*      */     } 
/*      */     
/* 3726 */     if (GDMsgLogger.severeErrorsInLog()) {
/* 3727 */       GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_DB_EXTRACT_FAIL"));
/*      */     }
/*      */   }
/*      */   
/*      */   private void recordsToText(GDBuffer buffer, String dir) throws IOException, GDParseException {
/* 3732 */     NumberFormat formatter = new DecimalFormat("#0.000000");
/* 3733 */     LZ4Factory factory = LZ4Factory.fastestInstance();
/* 3734 */     LZ4FastDecompressor decomp = factory.fastDecompressor();
/*      */     int i;
/* 3736 */     for (i = 0; i < this.records.length; i++) {
/* 3737 */       if (this.records[i] != null) {
/*      */         
/* 3739 */         this.records[i].setFileName((this.strings[(this.records[i]).strID]).str);
/*      */         
/* 3741 */         byte[] bComp = buffer.getByteArray(((this.records[i]).offset + 24), (this.records[i]).len_comp);
/*      */ 
/*      */ 
/*      */         
/* 3745 */         byte[] bDecomp = new byte[(this.records[i]).len_decomp];
/*      */         
/* 3747 */         decomp.decompress(bComp, bDecomp);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3757 */         String text = "";
/* 3758 */         String value = "";
/*      */ 
/*      */ 
/*      */         
/* 3762 */         int offset = 0;
/*      */         
/*      */         try {
/* 3765 */           while (offset < bDecomp.length) {
/* 3766 */             int typeOffset = offset;
/* 3767 */             short varType = GDReader.getUShort(bDecomp, offset);
/* 3768 */             offset += 2;
/*      */             
/* 3770 */             short count = GDReader.getUShort(bDecomp, offset);
/* 3771 */             offset += 2;
/*      */             
/* 3773 */             int strIdx = GDReader.getUInt(bDecomp, offset);
/* 3774 */             offset += 4;
/*      */             
/* 3776 */             String tag = (this.strings[strIdx]).str + ",";
/* 3777 */             String values = "";
/*      */             int j;
/* 3779 */             for (j = 0; j < count; j++) {
/* 3780 */               int valInt; float valFloat; int valStrIdx; boolean valBool; Object[] args; String msg; switch (varType) {
/*      */                 case 0:
/* 3782 */                   valInt = GDReader.getInt(bDecomp, offset);
/* 3783 */                   value = String.valueOf(valInt);
/*      */                   break;
/*      */                 
/*      */                 case 1:
/* 3787 */                   valFloat = GDReader.getFloat(bDecomp, offset);
/* 3788 */                   value = formatter.format(valFloat);
/*      */                   break;
/*      */                 
/*      */                 case 2:
/* 3792 */                   valStrIdx = GDReader.getUInt(bDecomp, offset);
/* 3793 */                   value = (this.strings[valStrIdx]).str;
/*      */                   break;
/*      */                 
/*      */                 case 3:
/* 3797 */                   valBool = GDReader.getBool(bDecomp, offset);
/* 3798 */                   if (valBool) { value = "1"; break; }  value = "0";
/*      */                   break;
/*      */                 
/*      */                 default:
/* 3802 */                   args = new Object[] { this.records[i].getFileName(), (this.strings[strIdx]).str };
/* 3803 */                   msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_UNKNOWN_VAR_TYPE", args);
/*      */                   
/* 3805 */                   throw new GDParseException(msg, typeOffset);
/*      */               } 
/*      */               
/* 3808 */               offset += 4;
/*      */               
/* 3810 */               values = values + value;
/*      */               
/* 3812 */               if (j == count - 1) {
/* 3813 */                 values = values + ",";
/*      */               } else {
/* 3815 */                 values = values + ";";
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
/* 3842 */             if (!values.equals("0,") && 
/* 3843 */               !values.equals("0.000000,")) {
/* 3844 */               text = text + tag + values + GDConstants.LINE_SEPARATOR;
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 3851 */         catch (GDParseException ex) {
/* 3852 */           GDMsgLogger.addError(ex);
/*      */           
/* 3854 */           (this.records[i]).error = true;
/*      */           
/* 3856 */           int pos = this.records[i].getFileName().lastIndexOf(".");
/* 3857 */           if (pos != -1) {
/* 3858 */             this.records[i].setFileName(this.records[i].getFileName().substring(0, pos));
/*      */           }
/*      */           
/* 3861 */           this.records[i].setFileName(this.records[i].getFileName() + ".err");
/*      */         } 
/*      */         
/* 3864 */         String osName = GDWriter.getOSFilePath(this.records[i].getFileName());
/*      */         
/* 3866 */         String newName = dir + GDConstants.FILE_SEPARATOR + osName;
/*      */         
/*      */         try {
/* 3869 */           GDWriter.write(newName, text);
/*      */         }
/* 3871 */         catch (Exception ex) {
/* 3872 */           GDMsgLogger.addError(ex);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   public static void convertTextures(String dir) {
/*      */     try {
/* 3879 */       File file = new File(dir);
/*      */       
/* 3881 */       if (!file.exists()) {
/*      */         return;
/*      */       }
/*      */       
/* 3885 */       if (file.isDirectory()) {
/* 3886 */         File[] files = file.listFiles();
/*      */         
/* 3888 */         for (int i = 0; i < files.length; i++) {
/* 3889 */           convertTextures(files[i].getCanonicalPath());
/*      */         }
/*      */       } else {
/*      */         
/* 3893 */         String fn = file.getCanonicalPath();
/*      */         
/*      */         try {
/* 3896 */           if (fn.endsWith(".tex")) {
/* 3897 */             String pngName = fn.substring(0, fn.length() - 4) + ".png";
/*      */             
/* 3899 */             BufferedImage image = null;
/*      */             try {
/* 3901 */               image = DDSLoader.load(fn);
/*      */             }
/* 3903 */             catch (Exception ex) {
/* 3904 */               image = null;
/*      */             } 
/*      */             
/* 3907 */             if (image != null) {
/* 3908 */               File fImg = new File(pngName);
/* 3909 */               ImageIO.write(image, "PNG", fImg);
/*      */             }
/*      */           
/*      */           } 
/* 3913 */         } catch (IOException iOException) {}
/*      */       }
/*      */     
/* 3916 */     } catch (Exception exception) {}
/*      */   }
/*      */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\file\ARZDecompress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */