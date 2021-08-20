/*      */ package org.gdstash.ui;
/*      */ 
/*      */ import java.awt.*;
/*      */
/*      */
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
/*      */ import javax.swing.*;
/*      */
/*      */
/*      */
/*      */
/*      */ import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
/*      */ import org.gdstash.character.GDChar;
import org.gdstash.db.DBStashItem;
import org.gdstash.db.DBStat;
import org.gdstash.db.SelectionCriteria;
/*      */ import org.gdstash.db.criteria.CriteriaCombination;
import org.gdstash.formula.GDFormulaList;
/*      */ import org.gdstash.item.GDItem;
/*      */ import org.gdstash.item.GDStash;
import org.gdstash.item.GDTransmute;
/*      */ import org.gdstash.trade.GDTradeList;
import org.gdstash.ui.util.AdjustablePanel;
import org.gdstash.util.*;
/*      */
/*      */
/*      */

/*      */
/*      */ public class GDMassImportPane extends AdjustablePanel implements GDUISearch {
/*      */   private String strSave;
/*      */   private GDStashFrame frame;
/*      */   private JLabel lblDir;
/*      */   private JButton btnHelp;
/*      */   private JButton btnSetDir;
/*      */   private JButton btnLoadDir;
/*      */   private JButton btnLoadGDS;
/*      */   private JButton btnLoadIAS;
/*      */   private JButton btnImport;
/*      */   private JButton btnSearch;
/*      */   private JButton btnGDSExp;
/*      */   private JButton btnAddTransmute;
/*      */   private JButton btnFullTransmute;
/*      */   private JButton btnFullBlueprint;
/*      */   private JButton btnUniqueClipboard;
/*      */   private JButton btnLegendaryClipboard;
/*      */   private String strDir;
/*      */   private File fileDir;
/*      */   private List<GDItem> items;
/*      */   private GDItemImportTablePane pnlTable;
/*      */   private JPanel pnlDir;
/*      */   private JPanel pnlStash;
/*      */   private JPanel pnlDB;
/*      */   private JPanel pnlTransmute;
/*      */   private JPanel pnlBlueprint;
/*      */   private JPanel pnlClipboard;
/*      */   private GDTabbedSearchDialog dlgSearch;
/*      */   
/*      */   private class ImportDirListener implements ActionListener { public void actionPerformed(ActionEvent e) {
/*   55 */       JFileChooser chooser = new JFileChooser(GDMassImportPane.this.fileDir);
/*   56 */       chooser.setFileSelectionMode(1);
/*   57 */       chooser.setAcceptAllFileFilterUsed(false);
/*      */       
/*   59 */       if (chooser.showOpenDialog((Component)GDMassImportPane.this) == 0)
/*      */         try {
/*   61 */           GDMassImportPane.this.fileDir = chooser.getSelectedFile();
/*   62 */           GDMassImportPane.this.strDir = GDMassImportPane.this.fileDir.getCanonicalPath();
/*      */           
/*   64 */           GDMassImportPane.this.lblDir.setText(GDMassImportPane.this.strDir);
/*      */         }
/*   66 */         catch (IOException iOException) {}
/*      */     }
/*      */     
/*      */     private ImportDirListener() {} }
/*      */   
/*      */   private class FileLoadListener implements ActionListener { private FileLoadListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*   74 */       if (GDMassImportPane.this.strDir == null)
/*      */         return; 
/*   76 */       GDMassImportPane.this.setCursor(Cursor.getPredefinedCursor(3));
/*      */       
/*   78 */       GDMassImportPane.this.items = new LinkedList();
/*      */       
/*   80 */       File file = new File(GDMassImportPane.this.strDir);
/*      */       
/*   82 */       if (file.isDirectory()) {
/*   83 */         GDMassImportPane.this.processDir(file);
/*      */       }
/*      */       
/*   86 */       if (file.isFile()) {
/*   87 */         GDMassImportPane.this.processFile(file);
/*      */       }
/*      */       
/*   90 */       GDMassImportPane.this.pnlTable.setItems(GDMassImportPane.this.items);
/*      */       
/*   92 */       GDMassImportPane.this.setCursor(Cursor.getDefaultCursor());
/*      */       
/*   94 */       GDMsgLogger.showLog(GDMassImportPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_IMPORT"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_IMPORT"));
/*      */     } }
/*      */ 
/*      */   
/*      */   private class GDStashLoadListener implements ActionListener { private GDStashLoadListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  101 */       JFileChooser chooser = new JFileChooser(GDMassImportPane.this.strSave);
/*  102 */       chooser.setFileSelectionMode(0);
/*  103 */       chooser.setFileFilter(new FileNameExtensionFilter(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_GD_STASH"), new String[] { "gds" }));
/*  104 */       chooser.setAcceptAllFileFilterUsed(false);
/*      */       
/*  106 */       if (chooser.showOpenDialog((Component)GDMassImportPane.this) == 0) {
/*  107 */         File fStash = chooser.getSelectedFile();
/*  108 */         if (!fStash.exists()) {
/*      */           
/*  110 */           GDDialog dialog = new GDDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_FILE_NOT_EXIST"), 4, GDMassImportPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERROR"), true);
/*  111 */           dialog.setVisible(true);
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */ 
/*      */         
/*  118 */         GDMassImportPane.this.setCursor(Cursor.getPredefinedCursor(3));
/*      */         
/*  120 */         GDMassImportPane.this.items = DBStashItem.loadGDS(fStash, GDConstants.CHARSET_STASH);
/*      */         
/*  122 */         GDMassImportPane.this.pnlTable.setItems(GDMassImportPane.this.items);
/*      */         
/*  124 */         GDMassImportPane.this.setCursor(Cursor.getDefaultCursor());
/*      */         
/*  126 */         GDMsgLogger.showLog(GDMassImportPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_IMPORT"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_IMPORT"));
/*      */       } 
/*      */     } }
/*      */   
/*      */   private class ItemAssistantLoadListener implements ActionListener {
/*      */     private ItemAssistantLoadListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  134 */       JFileChooser chooser = new JFileChooser(GDMassImportPane.this.strSave);
/*  135 */       chooser.setFileSelectionMode(0);
/*  136 */       chooser.setFileFilter(new FileNameExtensionFilter(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_ITEM_ASSISTANT"), new String[] { "ias" }));
/*  137 */       chooser.setAcceptAllFileFilterUsed(false);
/*      */       
/*  139 */       if (chooser.showOpenDialog((Component)GDMassImportPane.this) == 0) {
/*  140 */         File fStash = chooser.getSelectedFile();
/*  141 */         if (!fStash.exists()) {
/*      */           
/*  143 */           GDDialog dialog = new GDDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_FILE_NOT_EXIST"), 4, GDMassImportPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERROR"), true);
/*  144 */           dialog.setVisible(true);
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */ 
/*      */         
/*  151 */         GDMassImportPane.this.setCursor(Cursor.getPredefinedCursor(3));
/*      */         
/*  153 */         GDMassImportPane.this.items = DBStashItem.loadIAS(fStash);
/*      */         
/*  155 */         GDMassImportPane.this.pnlTable.setItems(GDMassImportPane.this.items);
/*      */         
/*  157 */         GDMassImportPane.this.setCursor(Cursor.getDefaultCursor());
/*      */         
/*  159 */         GDMsgLogger.showLog(GDMassImportPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_IMPORT"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_IMPORT"));
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class ImportDBListener implements ActionListener { private ImportDBListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  167 */       List<GDItem> items = GDMassImportPane.this.pnlTable.getSelectedItems();
/*      */       
/*  169 */       if (items == null || items
/*  170 */         .isEmpty()) {
/*      */         
/*  172 */         GDDialog dialog = new GDDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_ITEM_NO_SEL"), 4, GDMassImportPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERROR"), true);
/*  173 */         dialog.setVisible(true);
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */ 
/*      */       
/*  180 */       GDMassImportPane.this.setCursor(Cursor.getPredefinedCursor(3));
/*      */       
/*  182 */       DBStashItem.storeGDItems(items);
/*      */       
/*  184 */       GDMassImportPane.this.setCursor(Cursor.getDefaultCursor());
/*      */       
/*  186 */       GDMsgLogger.showLog(GDMassImportPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_STORE"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_STORE"), true);
/*      */     } }
/*      */   
/*      */   private class SearchItemsListener implements ActionListener {
/*      */     private SearchItemsListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  193 */       if (GDMassImportPane.this.dlgSearch == null) {
/*  194 */         GDMassImportPane.this.dlgSearch = new GDTabbedSearchDialog(GDMassImportPane.this.frame, GDMassImportPane.this, GDTabbedSearchDialog.Mode.SEARCH);
/*      */       }
/*      */ 
/*      */       
/*  198 */       GDMassImportPane.this.dlgSearch.setVisible(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class GDStashExportListener implements ActionListener { private GDStashExportListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  205 */       JFileChooser chooser = new JFileChooser(GDMassImportPane.this.strSave);
/*  206 */       chooser.setFileSelectionMode(0);
/*  207 */       chooser.setFileFilter(new FileNameExtensionFilter(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_GD_STASH"), new String[] { "gds" }));
/*  208 */       chooser.setAcceptAllFileFilterUsed(false);
/*      */       
/*  210 */       if (chooser.showSaveDialog((Component)GDMassImportPane.this) == 0) {
/*      */         try {
/*  212 */           String filename = chooser.getSelectedFile().getCanonicalPath();
/*      */           
/*  214 */           if (!filename.endsWith(".gds")) filename = filename + ".gds";
/*      */           
/*  216 */           File fStash = new File(filename);
/*      */           
/*  218 */           List<GDItem> items = GDMassImportPane.this.pnlTable.getSelectedItems();
/*      */           
/*  220 */           if (items != null && !items.isEmpty()) {
/*  221 */             DBStashItem.writeGDStashItemListGDS(fStash, GDConstants.CHARSET_STASH, items);
/*      */           } else {
/*  223 */             DBStashItem.writeAllGDS(fStash, GDConstants.CHARSET_STASH);
/*      */           }
/*      */         
/*  226 */         } catch (IOException ex) {
/*  227 */           GDMsgLogger.addError(ex);
/*      */         } 
/*      */         
/*  230 */         GDMsgLogger.showLog(GDMassImportPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_EXPORT"), GDLog.MessageType.Success, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_EXPORT"), true);
/*      */       } 
/*      */     } }
/*      */   
/*      */   private class AddTransmuteListener implements ActionListener {
/*      */     private AddTransmuteListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  238 */       if (GDMassImportPane.this.strSave == null)
/*  239 */         return;  if (GDMassImportPane.this.strSave.isEmpty())
/*      */         return; 
/*  241 */       if (GDStashFrame.dbConfig.gddbInit) {
/*  242 */         String dirname = GDMassImportPane.this.strSave;
/*  243 */         String mod = GDMassImportPane.this.frame.getSelectedMod();
/*      */         
/*  245 */         if (mod != null && 
/*  246 */           !mod.isEmpty()) {
/*  247 */           dirname = dirname + GDConstants.FILE_SEPARATOR + mod;
/*      */         }
/*      */ 
/*      */         
/*  251 */         boolean scExists = false;
/*  252 */         boolean hcExists = false;
/*      */         
/*  254 */         String scFilename = dirname + GDConstants.FILE_SEPARATOR + "transmutes.gst";
/*      */         
/*  256 */         File file = new File(scFilename);
/*      */         
/*  258 */         if (file.exists()) {
/*  259 */           scExists = true;
/*      */           
/*  261 */           GDMassImportPane.this.setCursor(Cursor.getPredefinedCursor(3));
/*      */           
/*      */           try {
/*  264 */             GDTransmute gdt = new GDTransmute(file);
/*  265 */             gdt.read(null);
/*      */             
/*  267 */             if (!gdt.hasErrors()) {
/*  268 */               gdt.insertCollectedItems();
/*  269 */               gdt.write();
/*      */             }
/*      */           
/*  272 */           } catch (Exception ex) {
/*  273 */             GDMsgLogger.addError(ex);
/*      */           } 
/*      */           
/*  276 */           GDMassImportPane.this.setCursor(Cursor.getDefaultCursor());
/*      */         } 
/*      */         
/*  279 */         String hcFilename = dirname + GDConstants.FILE_SEPARATOR + "transmutes.gsh";
/*      */         
/*  281 */         file = new File(hcFilename);
/*      */         
/*  283 */         if (file.exists()) {
/*  284 */           hcExists = true;
/*      */           
/*  286 */           GDMassImportPane.this.setCursor(Cursor.getPredefinedCursor(3));
/*      */           
/*      */           try {
/*  289 */             GDTransmute gdt = new GDTransmute(file);
/*  290 */             gdt.read(null);
/*      */             
/*  292 */             if (!gdt.hasErrors()) {
/*  293 */               gdt.insertCollectedItems();
/*  294 */               gdt.write();
/*      */             }
/*      */           
/*  297 */           } catch (Exception ex) {
/*  298 */             GDMsgLogger.addError(ex);
/*      */           } 
/*      */           
/*  301 */           GDMassImportPane.this.setCursor(Cursor.getDefaultCursor());
/*      */         } 
/*      */         
/*  304 */         if (!scExists && !hcExists) {
/*  305 */           Object[] args = { scFilename };
/*  306 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*      */           
/*  308 */           GDMsgLogger.addError(msg);
/*      */           
/*  310 */           Object[] args2 = { hcFilename };
/*  311 */           String msg2 = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*      */           
/*  313 */           GDMsgLogger.addError(msg2);
/*      */         } 
/*      */         
/*  316 */         GDMsgLogger.showLog((Component)GDMassImportPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_TRANSMUTE_ADD"), GDLog.MessageType.Success, 
/*  317 */             GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_TRANSMUTE_ADD"), true, false);
/*      */       } else {
/*      */         
/*  320 */         GDDialog dialog = new GDDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_DB_NOT_IMPORTED"), 4, GDMassImportPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERROR"), true);
/*  321 */         dialog.setVisible(true);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class FullTransmuteListener
/*      */     implements ActionListener {
/*      */     private FullTransmuteListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  331 */       if (GDMassImportPane.this.strSave == null)
/*  332 */         return;  if (GDMassImportPane.this.strSave.isEmpty())
/*      */         return; 
/*  334 */       if (GDStashFrame.dbConfig.gddbInit) {
/*  335 */         String dirname = GDMassImportPane.this.strSave;
/*  336 */         String mod = GDMassImportPane.this.frame.getSelectedMod();
/*      */         
/*  338 */         if (mod != null && 
/*  339 */           !mod.isEmpty()) {
/*  340 */           dirname = dirname + GDConstants.FILE_SEPARATOR + mod;
/*      */         }
/*      */ 
/*      */         
/*  344 */         boolean scExists = false;
/*  345 */         boolean hcExists = false;
/*      */         
/*  347 */         String scFilename = dirname + GDConstants.FILE_SEPARATOR + "transmutes.gst";
/*      */         
/*  349 */         File file = new File(scFilename);
/*      */         
/*  351 */         if (file.exists()) {
/*  352 */           scExists = true;
/*      */           
/*  354 */           GDMassImportPane.this.setCursor(Cursor.getPredefinedCursor(3));
/*      */           
/*      */           try {
/*  357 */             GDTransmute gdt = new GDTransmute(file);
/*  358 */             gdt.read(null);
/*      */             
/*  360 */             if (!gdt.hasErrors()) {
/*  361 */               gdt.insertAllItems();
/*  362 */               gdt.write();
/*      */             }
/*      */           
/*  365 */           } catch (Exception ex) {
/*  366 */             GDMsgLogger.addError(ex);
/*      */           } 
/*      */           
/*  369 */           GDMassImportPane.this.setCursor(Cursor.getDefaultCursor());
/*      */         } 
/*      */         
/*  372 */         String hcFilename = dirname + GDConstants.FILE_SEPARATOR + "transmutes.gsh";
/*      */         
/*  374 */         file = new File(hcFilename);
/*      */         
/*  376 */         if (file.exists()) {
/*  377 */           hcExists = true;
/*      */           
/*  379 */           GDMassImportPane.this.setCursor(Cursor.getPredefinedCursor(3));
/*      */           
/*      */           try {
/*  382 */             GDTransmute gdt = new GDTransmute(file);
/*  383 */             gdt.read(null);
/*      */             
/*  385 */             if (!gdt.hasErrors()) {
/*  386 */               gdt.insertAllItems();
/*  387 */               gdt.write();
/*      */             }
/*      */           
/*  390 */           } catch (Exception ex) {
/*  391 */             GDMsgLogger.addError(ex);
/*      */           } 
/*      */           
/*  394 */           GDMassImportPane.this.setCursor(Cursor.getDefaultCursor());
/*      */         } 
/*      */         
/*  397 */         if (!scExists && !hcExists) {
/*  398 */           Object[] args = { scFilename };
/*  399 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*      */           
/*  401 */           GDMsgLogger.addError(msg);
/*      */           
/*  403 */           Object[] args2 = { hcFilename };
/*  404 */           String msg2 = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*      */           
/*  406 */           GDMsgLogger.addError(msg2);
/*      */         } 
/*      */         
/*  409 */         GDMsgLogger.showLog((Component)GDMassImportPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_TRANSMUTE_FULL"), GDLog.MessageType.Success, 
/*  410 */             GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_TRANSMUTE_FULL"), true, false);
/*      */       } else {
/*      */         
/*  413 */         GDDialog dialog = new GDDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_DB_NOT_IMPORTED"), 4, GDMassImportPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERROR"), true);
/*  414 */         dialog.setVisible(true);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class UniqueClipboardListener
/*      */     implements ActionListener {
/*      */     private UniqueClipboardListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  424 */       if (GDStashFrame.dbConfig.gddbInit) {
/*  425 */         GDMassImportPane.this.setCursor(Cursor.getPredefinedCursor(3));
/*      */         
/*  427 */         List<GDItem> items = GDMassImportPane.this.pnlTable.getSelectedItems();
/*      */         
/*  429 */         if (items != null && !items.isEmpty()) {
/*  430 */           GDTradeList.extractUniques(items);
/*      */         } else {
/*  432 */           GDTradeList.extractUniques();
/*      */         } 
/*      */         
/*  435 */         GDMassImportPane.this.setCursor(Cursor.getDefaultCursor());
/*      */ 
/*      */         
/*  438 */         GDLogoDialog dialog = new GDLogoDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_UNIQUES_TO_CLIPBOARD"), 2, GDMassImportPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFORMATION"), true);
/*  439 */         dialog.setVisible(true);
/*      */       } else {
/*      */         
/*  442 */         GDDialog dialog = new GDDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_DB_NOT_IMPORTED"), 4, GDMassImportPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERROR"), true);
/*  443 */         dialog.setVisible(true);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class LegendaryClipboardListener
/*      */     implements ActionListener {
/*      */     private LegendaryClipboardListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  453 */       if (GDStashFrame.dbConfig.gddbInit) {
/*  454 */         GDMassImportPane.this.setCursor(Cursor.getPredefinedCursor(3));
/*      */         
/*  456 */         List<GDItem> items = GDMassImportPane.this.pnlTable.getSelectedItems();
/*      */         
/*  458 */         if (items != null && !items.isEmpty()) {
/*  459 */           GDTradeList.extractLegendaries(items);
/*      */         } else {
/*  461 */           GDTradeList.extractLegendaries();
/*      */         } 
/*      */         
/*  464 */         GDMassImportPane.this.setCursor(Cursor.getDefaultCursor());
/*      */ 
/*      */         
/*  467 */         GDLogoDialog dialog = new GDLogoDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_LEGEND_TO_CLIPBOARD"), 2, GDMassImportPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFORMATION"), true);
/*  468 */         dialog.setVisible(true);
/*      */       } else {
/*      */         
/*  471 */         GDDialog dialog = new GDDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_DB_NOT_IMPORTED"), 4, GDMassImportPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERROR"), true);
/*  472 */         dialog.setVisible(true);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class FullBlueprintListener
/*      */     implements ActionListener {
/*      */     private FullBlueprintListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  482 */       if (GDMassImportPane.this.strSave == null)
/*  483 */         return;  if (GDMassImportPane.this.strSave.isEmpty())
/*      */         return; 
/*  485 */       if (GDStashFrame.dbConfig.gddbInit) {
/*  486 */         String dirname = GDMassImportPane.this.strSave;
/*  487 */         String mod = GDMassImportPane.this.frame.getSelectedMod();
/*      */         
/*  489 */         if (mod != null && 
/*  490 */           !mod.isEmpty()) {
/*  491 */           dirname = dirname + GDConstants.FILE_SEPARATOR + mod;
/*      */         }
/*      */ 
/*      */         
/*  495 */         boolean scExists = false;
/*  496 */         boolean hcExists = false;
/*      */         
/*  498 */         String scFilename = dirname + GDConstants.FILE_SEPARATOR + "formulas.gst";
/*      */         
/*  500 */         File file = new File(scFilename);
/*      */         
/*  502 */         if (file.exists()) {
/*  503 */           scExists = true;
/*      */           
/*  505 */           GDMassImportPane.this.setCursor(Cursor.getPredefinedCursor(3));
/*      */           
/*      */           try {
/*  508 */             GDFormulaList fl = new GDFormulaList(file);
/*  509 */             fl.read();
/*      */             
/*  511 */             if (!fl.hasErrors()) {
/*  512 */               fl.addBlueprints();
/*  513 */               fl.write();
/*      */             }
/*      */           
/*  516 */           } catch (Exception ex) {
/*  517 */             GDMsgLogger.addError(ex);
/*      */           } 
/*      */           
/*  520 */           GDMassImportPane.this.setCursor(Cursor.getDefaultCursor());
/*      */         } 
/*      */         
/*  523 */         String hcFilename = dirname + GDConstants.FILE_SEPARATOR + "formulas.gsh";
/*      */         
/*  525 */         file = new File(hcFilename);
/*      */         
/*  527 */         if (file.exists()) {
/*  528 */           hcExists = true;
/*      */           
/*  530 */           GDMassImportPane.this.setCursor(Cursor.getPredefinedCursor(3));
/*      */           
/*      */           try {
/*  533 */             GDFormulaList fl = new GDFormulaList(file);
/*  534 */             fl.read();
/*      */             
/*  536 */             if (!fl.hasErrors()) {
/*  537 */               fl.addBlueprints();
/*  538 */               fl.write();
/*      */             }
/*      */           
/*  541 */           } catch (Exception ex) {
/*  542 */             GDMsgLogger.addError(ex);
/*      */           } 
/*      */           
/*  545 */           GDMassImportPane.this.setCursor(Cursor.getDefaultCursor());
/*      */         } 
/*      */         
/*  548 */         if (!scExists && !hcExists) {
/*  549 */           Object[] args = { scFilename };
/*  550 */           String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*      */           
/*  552 */           GDMsgLogger.addError(msg);
/*      */           
/*  554 */           Object[] args2 = { hcFilename };
/*  555 */           String msg2 = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_FILE_NOT_FOUND", args);
/*      */           
/*  557 */           GDMsgLogger.addError(msg2);
/*      */         } 
/*      */         
/*  560 */         GDMsgLogger.showLog((Component)GDMassImportPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_BLUEPRINT_FULL"), GDLog.MessageType.Success, 
/*  561 */             GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_BLUEPRINT_FULL"), true, false);
/*      */       } else {
/*      */         
/*  564 */         GDDialog dialog = new GDDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_DB_NOT_IMPORTED"), 4, GDMassImportPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERROR"), true);
/*  565 */         dialog.setVisible(true);
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
/*      */   public GDMassImportPane(GDStashFrame frame) {
/*  606 */     this.strSave = GDStashFrame.iniConfig.sectDir.savePath;
/*      */ 
/*      */     
/*  609 */     this.frame = frame;
/*      */     
/*  611 */     adjustUI();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  617 */     BorderLayout layout = new BorderLayout();
/*      */ 
/*      */ 
/*      */     
/*  621 */     JPanel pnlTable = buildTablePanel();
/*  622 */     JPanel pnlSide = buildSideButtonPanel();
/*      */ 
/*      */ 
/*      */     
/*  626 */     setLayout(layout);
/*      */     
/*  628 */     add(pnlSide, "West");
/*  629 */     add(pnlTable, "Center");
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
/*      */   public void adjustUI() {
/*  665 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  666 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/*  667 */     if (fntButton == null) fntButton = fntLabel; 
/*  668 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/*  669 */     if (fntBorder == null) fntBorder = fntLabel;
/*      */     
/*  671 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  672 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  673 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*      */     
/*  675 */     if (this.btnSetDir == null) {
/*  676 */       this.btnSetDir = new JButton();
/*      */       
/*  678 */       this.btnSetDir.addActionListener(new ImportDirListener());
/*      */     } 
/*  680 */     this.btnSetDir.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_SET_IMPORT_DIR"));
/*  681 */     this.btnSetDir.setIcon(GDImagePool.iconBtnDir24);
/*  682 */     this.btnSetDir.setFont(fntButton);
/*  683 */     if (this.lblDir == null) this.lblDir = new JLabel(); 
/*  684 */     this.lblDir.setFont(fntLabel);
/*      */     
/*  686 */     if (this.btnHelp == null) {
/*  687 */       this.btnHelp = new JButton();
/*      */       
/*  689 */       this.btnHelp.addActionListener(new GDHelpActionListener("02_im_export.html"));
/*      */     } 
/*  691 */     this.btnHelp.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_HELP"));
/*  692 */     this.btnHelp.setIcon(GDImagePool.iconBtnQuestion24);
/*  693 */     this.btnHelp.setFont(fntButton);
/*  694 */     GDStashFrame.setMnemonic(this.btnHelp, "MNC_HELP");
/*      */     
/*  696 */     if (this.btnLoadDir == null) {
/*  697 */       this.btnLoadDir = new JButton();
/*      */       
/*  699 */       this.btnLoadDir.addActionListener(new FileLoadListener());
/*      */     } 
/*  701 */     this.btnLoadDir.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_LOAD_TRANSFER"));
/*  702 */     this.btnLoadDir.setIcon(GDImagePool.iconMassItemLoad24);
/*  703 */     this.btnLoadDir.setFont(fntButton);
/*      */     
/*  705 */     if (this.btnLoadGDS == null) {
/*  706 */       this.btnLoadGDS = new JButton();
/*      */       
/*  708 */       this.btnLoadGDS.addActionListener(new GDStashLoadListener());
/*      */     } 
/*  710 */     this.btnLoadGDS.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_LOAD_GDS_FILE"));
/*  711 */     this.btnLoadGDS.setIcon(GDImagePool.iconMassGDSLoad24);
/*  712 */     this.btnLoadGDS.setFont(fntButton);
/*      */     
/*  714 */     if (this.btnLoadIAS == null) {
/*  715 */       this.btnLoadIAS = new JButton();
/*      */       
/*  717 */       this.btnLoadIAS.addActionListener(new ItemAssistantLoadListener());
/*      */     } 
/*  719 */     this.btnLoadIAS.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_LOAD_IAS_FILE"));
/*  720 */     this.btnLoadIAS.setIcon(GDImagePool.iconMassIASLoad24);
/*  721 */     this.btnLoadIAS.setFont(fntButton);
/*      */     
/*  723 */     if (this.btnImport == null) {
/*  724 */       this.btnImport = new JButton();
/*      */       
/*  726 */       this.btnImport.addActionListener(new ImportDBListener());
/*      */     } 
/*  728 */     this.btnImport.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_IMPORT_ITEMS"));
/*  729 */     this.btnImport.setIcon(GDImagePool.iconMassItemImport24);
/*  730 */     this.btnImport.setFont(fntButton);
/*      */     
/*  732 */     if (this.btnSearch == null) {
/*  733 */       this.btnSearch = new JButton();
/*      */       
/*  735 */       this.btnSearch.addActionListener(new SearchItemsListener());
/*      */     } 
/*  737 */     this.btnSearch.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_SEARCH"));
/*  738 */     this.btnSearch.setIcon(GDImagePool.iconBtnSearch24);
/*  739 */     this.btnSearch.setFont(fntButton);
/*      */     
/*  741 */     if (this.btnGDSExp == null) {
/*  742 */       this.btnGDSExp = new JButton();
/*      */       
/*  744 */       this.btnGDSExp.addActionListener(new GDStashExportListener());
/*      */     } 
/*  746 */     this.btnGDSExp.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_EXPORT_GDS_FILE"));
/*  747 */     this.btnGDSExp.setIcon(GDImagePool.iconMassGDSExport24);
/*  748 */     this.btnGDSExp.setFont(fntButton);
/*      */     
/*  750 */     if (this.btnAddTransmute == null) {
/*  751 */       this.btnAddTransmute = new JButton();
/*      */       
/*  753 */       this.btnAddTransmute.addActionListener(new AddTransmuteListener());
/*      */     } 
/*  755 */     this.btnAddTransmute.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_TRANSMUTE_ADD"));
/*  756 */     this.btnAddTransmute.setIcon(GDImagePool.iconConfigTransmuteAdd24);
/*  757 */     this.btnAddTransmute.setFont(fntButton);
/*      */     
/*  759 */     if (this.btnFullTransmute == null) {
/*  760 */       this.btnFullTransmute = new JButton();
/*      */       
/*  762 */       this.btnFullTransmute.addActionListener(new FullTransmuteListener());
/*      */     } 
/*  764 */     this.btnFullTransmute.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_TRANSMUTE_FULL"));
/*  765 */     this.btnFullTransmute.setIcon(GDImagePool.iconConfigTransmuteFull24);
/*  766 */     this.btnFullTransmute.setFont(fntButton);
/*      */     
/*  768 */     if (this.btnFullBlueprint == null) {
/*  769 */       this.btnFullBlueprint = new JButton();
/*      */       
/*  771 */       this.btnFullBlueprint.addActionListener(new FullBlueprintListener());
/*      */     } 
/*  773 */     this.btnFullBlueprint.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_BLUEPRINT_FULL"));
/*  774 */     this.btnFullBlueprint.setIcon(GDImagePool.iconMassBlueprints24);
/*  775 */     this.btnFullBlueprint.setFont(fntButton);
/*      */     
/*  777 */     if (this.btnUniqueClipboard == null) {
/*  778 */       this.btnUniqueClipboard = new JButton();
/*      */       
/*  780 */       this.btnUniqueClipboard.addActionListener(new UniqueClipboardListener());
/*      */     } 
/*  782 */     this.btnUniqueClipboard.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_UNQIUES_CLIPBOARD"));
/*  783 */     this.btnUniqueClipboard.setIcon(GDImagePool.iconMassUniqueClipboard24);
/*  784 */     this.btnUniqueClipboard.setFont(fntButton);
/*      */     
/*  786 */     if (this.btnLegendaryClipboard == null) {
/*  787 */       this.btnLegendaryClipboard = new JButton();
/*      */       
/*  789 */       this.btnLegendaryClipboard.addActionListener(new LegendaryClipboardListener());
/*      */     } 
/*  791 */     this.btnLegendaryClipboard.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_LEGEND_CLIPBOARD"));
/*  792 */     this.btnLegendaryClipboard.setIcon(GDImagePool.iconMassLegendClipboard24);
/*  793 */     this.btnLegendaryClipboard.setFont(fntButton);
/*      */     
/*  795 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  796 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  797 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*  798 */     TitledBorder text = null;
/*      */     
/*  800 */     if (this.pnlDir != null) {
/*  801 */       text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BRD_DIRECTORY"));
/*  802 */       text.setTitleFont(fntBorder);
/*  803 */       this.pnlDir.setBorder(text);
/*      */     } 
/*      */     
/*  806 */     if (this.pnlStash != null) {
/*  807 */       text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BRD_IMPORT_ITEMS"));
/*  808 */       text.setTitleFont(fntBorder);
/*  809 */       this.pnlStash.setBorder(text);
/*      */     } 
/*      */     
/*  812 */     if (this.pnlDB != null) {
/*  813 */       text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BRD_EXPORT_DB"));
/*  814 */       text.setTitleFont(fntBorder);
/*  815 */       this.pnlDB.setBorder(text);
/*      */     } 
/*      */     
/*  818 */     if (this.pnlTable == null) this.pnlTable = new GDItemImportTablePane((List<GDItem>)null); 
/*  819 */     this.pnlTable.adjustUI();
/*      */   }
/*      */   
/*      */   private JPanel buildTablePanel() {
/*  823 */     GroupLayout layout = null;
/*  824 */     GroupLayout.SequentialGroup hGroup = null;
/*  825 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/*  827 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  828 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  829 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*      */     
/*  831 */     JPanel pnlImpExp = buildImpExpPanel();
/*      */     
/*  833 */     JPanel panel = new JPanel();
/*      */     
/*  835 */     layout = new GroupLayout(panel);
/*  836 */     panel.setLayout(layout);
/*      */     
/*  838 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  841 */     layout.setAutoCreateContainerGaps(false);
/*      */ 
/*      */     
/*  844 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  847 */     hGroup
/*  848 */       .addGroup(layout.createParallelGroup()
/*  849 */         .addComponent(pnlImpExp)
/*  850 */         .addComponent((Component)this.pnlTable));
/*      */     
/*  852 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  855 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  858 */     vGroup
/*  859 */       .addGroup(layout.createParallelGroup()
/*  860 */         .addComponent(pnlImpExp))
/*  861 */       .addGroup(layout.createParallelGroup()
/*  862 */         .addComponent((Component)this.pnlTable));
/*  863 */     layout.setVerticalGroup(vGroup);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  869 */     panel.setBorder(compound);
/*      */     
/*  871 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildImpExpPanel() {
/*  875 */     GroupLayout layout = null;
/*  876 */     GroupLayout.SequentialGroup hGroup = null;
/*  877 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/*  879 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  880 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  881 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*      */     
/*  883 */     this.pnlDir = buildDirPanel();
/*  884 */     JPanel pnlButton = buildImExButtonPanel();
/*      */     
/*  886 */     JPanel panel = new JPanel();
/*      */     
/*  888 */     layout = new GroupLayout(panel);
/*  889 */     panel.setLayout(layout);
/*      */     
/*  891 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  894 */     layout.setAutoCreateContainerGaps(false);
/*      */ 
/*      */     
/*  897 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  900 */     hGroup
/*  901 */       .addGroup(layout.createParallelGroup()
/*  902 */         .addComponent(this.pnlDir)
/*  903 */         .addComponent(pnlButton));
/*      */     
/*  905 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  908 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  911 */     vGroup
/*  912 */       .addGroup(layout.createParallelGroup()
/*  913 */         .addComponent(this.pnlDir))
/*  914 */       .addGroup(layout.createParallelGroup()
/*  915 */         .addComponent(pnlButton));
/*  916 */     layout.setVerticalGroup(vGroup);
/*      */     
/*  918 */     layout.linkSize(0, new Component[] { this.pnlDir, pnlButton });
/*      */ 
/*      */ 
/*      */     
/*  922 */     panel.setBorder(compound);
/*      */     
/*  924 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildImExButtonPanel() {
/*  928 */     GroupLayout layout = null;
/*  929 */     GroupLayout.SequentialGroup hGroup = null;
/*  930 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/*  932 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  933 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  934 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*      */     
/*  936 */     JPanel panel = new JPanel();
/*      */     
/*  938 */     layout = new GroupLayout(panel);
/*  939 */     panel.setLayout(layout);
/*      */     
/*  941 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  944 */     layout.setAutoCreateContainerGaps(false);
/*      */ 
/*      */     
/*  947 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  950 */     hGroup
/*  951 */       .addGroup(layout.createParallelGroup()
/*  952 */         .addComponent(this.btnLoadDir))
/*  953 */       .addGroup(layout.createParallelGroup()
/*  954 */         .addComponent(this.btnLoadGDS))
/*  955 */       .addGroup(layout.createParallelGroup()
/*  956 */         .addComponent(this.btnLoadIAS))
/*  957 */       .addGroup(layout.createParallelGroup()
/*  958 */         .addComponent(this.btnImport))
/*  959 */       .addGroup(layout.createParallelGroup()
/*  960 */         .addComponent(this.btnSearch))
/*  961 */       .addGroup(layout.createParallelGroup()
/*  962 */         .addComponent(this.btnGDSExp));
/*      */     
/*  964 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  967 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  970 */     vGroup
/*  971 */       .addGroup(layout.createParallelGroup()
/*  972 */         .addComponent(this.btnLoadDir)
/*  973 */         .addComponent(this.btnLoadGDS)
/*  974 */         .addComponent(this.btnLoadIAS)
/*  975 */         .addComponent(this.btnImport)
/*  976 */         .addComponent(this.btnSearch)
/*  977 */         .addComponent(this.btnGDSExp));
/*  978 */     layout.setVerticalGroup(vGroup);
/*      */     
/*  980 */     layout.linkSize(0, new Component[] { this.btnLoadDir, this.btnLoadGDS });
/*  981 */     layout.linkSize(0, new Component[] { this.btnLoadDir, this.btnLoadIAS });
/*  982 */     layout.linkSize(0, new Component[] { this.btnLoadDir, this.btnImport });
/*  983 */     layout.linkSize(0, new Component[] { this.btnLoadDir, this.btnSearch });
/*  984 */     layout.linkSize(0, new Component[] { this.btnLoadDir, this.btnGDSExp });
/*      */     
/*  986 */     layout.linkSize(1, new Component[] { this.btnLoadDir, this.btnLoadGDS });
/*  987 */     layout.linkSize(1, new Component[] { this.btnLoadDir, this.btnLoadIAS });
/*  988 */     layout.linkSize(1, new Component[] { this.btnLoadDir, this.btnImport });
/*  989 */     layout.linkSize(1, new Component[] { this.btnLoadDir, this.btnSearch });
/*  990 */     layout.linkSize(1, new Component[] { this.btnLoadDir, this.btnGDSExp });
/*      */     
/*  992 */     panel.setBorder(compound);
/*      */     
/*  994 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildSideButtonPanel() {
/*  998 */     GroupLayout layout = null;
/*  999 */     GroupLayout.SequentialGroup hGroup = null;
/* 1000 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/* 1002 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 1003 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 1004 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*      */     
/* 1006 */     JPanel panel = new JPanel();
/*      */     
/* 1008 */     layout = new GroupLayout(panel);
/* 1009 */     panel.setLayout(layout);
/*      */     
/* 1011 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/* 1014 */     layout.setAutoCreateContainerGaps(false);
/*      */ 
/*      */     
/* 1017 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/* 1020 */     hGroup
/* 1021 */       .addGroup(layout.createParallelGroup()
/* 1022 */         .addComponent(this.btnUniqueClipboard)
/* 1023 */         .addComponent(this.btnLegendaryClipboard)
/* 1024 */         .addComponent(this.btnFullBlueprint)
/* 1025 */         .addComponent(this.btnAddTransmute)
/* 1026 */         .addComponent(this.btnFullTransmute));
/*      */     
/* 1028 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/* 1031 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/* 1034 */     vGroup
/* 1035 */       .addGroup(layout.createParallelGroup()
/* 1036 */         .addComponent(this.btnUniqueClipboard))
/* 1037 */       .addGroup(layout.createParallelGroup()
/* 1038 */         .addComponent(this.btnLegendaryClipboard))
/* 1039 */       .addGroup(layout.createParallelGroup()
/* 1040 */         .addComponent(this.btnFullBlueprint))
/* 1041 */       .addGroup(layout.createParallelGroup()
/* 1042 */         .addComponent(this.btnAddTransmute))
/* 1043 */       .addGroup(layout.createParallelGroup()
/* 1044 */         .addComponent(this.btnFullTransmute));
/* 1045 */     layout.setVerticalGroup(vGroup);
/*      */     
/* 1047 */     layout.linkSize(0, new Component[] { this.btnUniqueClipboard, this.btnLegendaryClipboard });
/* 1048 */     layout.linkSize(0, new Component[] { this.btnUniqueClipboard, this.btnFullBlueprint });
/* 1049 */     layout.linkSize(0, new Component[] { this.btnUniqueClipboard, this.btnAddTransmute });
/* 1050 */     layout.linkSize(0, new Component[] { this.btnUniqueClipboard, this.btnFullTransmute });
/*      */     
/* 1052 */     layout.linkSize(1, new Component[] { this.btnUniqueClipboard, this.btnLegendaryClipboard });
/* 1053 */     layout.linkSize(1, new Component[] { this.btnUniqueClipboard, this.btnFullBlueprint });
/* 1054 */     layout.linkSize(1, new Component[] { this.btnUniqueClipboard, this.btnAddTransmute });
/* 1055 */     layout.linkSize(1, new Component[] { this.btnUniqueClipboard, this.btnFullTransmute });
/*      */     
/* 1057 */     panel.setBorder(compound);
/*      */     
/* 1059 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildDirPanel() {
/* 1063 */     GroupLayout layout = null;
/* 1064 */     GroupLayout.SequentialGroup hGroup = null;
/* 1065 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/* 1067 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 1068 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 1069 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 1070 */     Border text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BRD_DIRECTORY"));
/*      */     
/* 1072 */     JPanel panel = new JPanel();
/*      */     
/* 1074 */     JPanel pnlHelp = buildHelpButtonPanel();
/*      */     
/* 1076 */     this.strDir = GDStashFrame.iniConfig.sectDir.savePath;
/* 1077 */     if (this.strDir != null) this.fileDir = new File(this.strDir);
/*      */     
/* 1079 */     if (this.strDir == null) { this.lblDir.setText(""); }
/* 1080 */     else { this.lblDir.setText(this.strDir); }
/*      */     
/* 1082 */     layout = new GroupLayout(panel);
/* 1083 */     panel.setLayout(layout);
/*      */     
/* 1085 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/* 1088 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/* 1091 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/* 1094 */     hGroup
/* 1095 */       .addGroup(layout.createParallelGroup()
/* 1096 */         .addComponent(pnlHelp))
/* 1097 */       .addGroup(layout.createParallelGroup()
/* 1098 */         .addComponent(this.lblDir));
/* 1099 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/* 1102 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/* 1105 */     vGroup
/* 1106 */       .addGroup(layout.createParallelGroup()
/* 1107 */         .addComponent(pnlHelp)
/* 1108 */         .addComponent(this.lblDir));
/* 1109 */     layout.setVerticalGroup(vGroup);
/*      */     
/* 1111 */     layout.linkSize(1, new Component[] { pnlHelp, this.lblDir });
/*      */     
/* 1113 */     panel.setBorder(text);
/*      */     
/* 1115 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildHelpButtonPanel() {
/* 1119 */     BorderLayout layout = new BorderLayout();
/*      */     
/* 1121 */     JPanel panel = new JPanel();
/*      */     
/* 1123 */     panel.setLayout(layout);
/*      */     
/* 1125 */     panel.add(this.btnSetDir, "Center");
/* 1126 */     panel.add(this.btnHelp, "West");
/*      */     
/* 1128 */     int size = 12;
/* 1129 */     if (GDStashFrame.iniConfig != null) size = GDStashFrame.iniConfig.sectUI.fontSize; 
/* 1130 */     Dimension dimPref = new Dimension(20 * size, 2 * size);
/* 1131 */     Dimension dimMax = new Dimension(35 * size, 2 * size);
/*      */     
/* 1133 */     panel.setPreferredSize(dimPref);
/* 1134 */     panel.setMaximumSize(dimMax);
/*      */     
/* 1136 */     return panel;
/*      */   }
/*      */   
/*      */   private void processDir(File dir) {
/* 1140 */     if (dir == null)
/*      */       return; 
/* 1142 */     File[] files = dir.listFiles();
/* 1143 */     if (files == null)
/*      */       return; 
/* 1145 */     for (int i = 0; i < files.length; i++) {
/* 1146 */       File file = files[i];
/*      */       
/* 1148 */       if (file != null) {
/* 1149 */         if (file.isDirectory()) processDir(file); 
/* 1150 */         if (file.isFile()) processFile(file); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void processFile(File file) {
/* 1156 */     String s = file.getName().toUpperCase(GDConstants.LOCALE_US);
/*      */     
/* 1158 */     if (s.endsWith(".GST") || s.endsWith(".GSH")) {
/* 1159 */       if (s.equals("TRANSMUTES.GST") || s.equals("TRANSMUTES.GSH")) {
/*      */         return;
/*      */       }
/*      */       
/* 1163 */       if (s.equals("FORMULAS.GST") || s.equals("FORMULAS.GSH")) {
/* 1164 */         GDFormulaList fl = new GDFormulaList(file);
/* 1165 */         fl.read();
/*      */         
/* 1167 */         if (fl.hasErrors())
/*      */           return; 
/* 1169 */         this.items.addAll(fl.getItems());
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1174 */       GDStash stash = new GDStash(file);
/*      */       
/* 1176 */       if (stash.hasStashErrors())
/*      */         return; 
/* 1178 */       this.items.addAll(stash.getItems());
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1183 */     if (s.equals("PLAYER.GDC")) {
/* 1184 */       GDChar gdc = new GDChar(file);
/* 1185 */       gdc.read();
/*      */       
/* 1187 */       if (gdc.hasErrors())
/*      */         return; 
/* 1189 */       this.items.addAll(gdc.getItems());
/*      */       return;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadStash(File file) {
/* 1196 */     String s = file.getName();
/* 1197 */     int pos = s.indexOf(".GST");
/* 1198 */     if (pos == -1) pos = s.indexOf(".gst");
/*      */     
/* 1200 */     if (pos == -1) pos = s.indexOf(".GSH"); 
/* 1201 */     if (pos == -1) pos = s.indexOf(".gsh");
/*      */     
/* 1203 */     if (pos == s.length() - 4) {
/* 1204 */       GDStash stash = new GDStash(file);
/*      */       
/* 1206 */       if (stash.hasStashErrors())
/*      */         return; 
/* 1208 */       this.items.addAll(stash.getItems());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void search(SelectionCriteria criteria) {
/* 1218 */     criteria.noEnemyOnly = true;
/*      */     
/* 1220 */     List<GDItem> itemsAll = DBStashItem.getGDItemByCriteria(criteria, CriteriaCombination.Soulbound.INCLUDED, CriteriaCombination.SC_HC.ALL, false, null);
/*      */     
/* 1222 */     if (criteria.combiMode == SelectionCriteria.CombinationMode.AND) {
/* 1223 */       List<GDItem> list = new LinkedList<>();
/*      */       
/* 1225 */       for (GDItem gdi : itemsAll) {
/* 1226 */         if (!DBStat.statsMeetCriteria(gdi.getStatList(), criteria))
/*      */           continue; 
/* 1228 */         if (criteria.dmgConversionFrom != null && 
/* 1229 */           !gdi.hasConvertIn(criteria.dmgConversionFrom)) {
/*      */           continue;
/*      */         }
/* 1232 */         if (criteria.dmgConversionTo != null && 
/* 1233 */           !gdi.hasConvertOut(criteria.dmgConversionTo)) {
/*      */           continue;
/*      */         }
/* 1236 */         if (criteria.petBonus && 
/* 1237 */           !gdi.hasPetBonus()) {
/*      */           continue;
/*      */         }
/* 1240 */         list.add(gdi);
/*      */       } 
/*      */       
/* 1243 */       itemsAll = list;
/*      */     } 
/*      */     
/* 1246 */     this.items = itemsAll;
/*      */     
/* 1248 */     this.pnlTable.setItems(this.items);
/*      */     
/* 1250 */     GDMsgLogger.showLog((Component)this, GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_SEARCH"));
/*      */   }
/*      */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\GDMassImportPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */