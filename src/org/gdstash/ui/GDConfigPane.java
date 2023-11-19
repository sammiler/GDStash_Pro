/*      */ package org.gdstash.ui;
/*      */ import java.awt.Component;
/*      */ import java.awt.Font;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.io.File;
/*      */ import java.util.List;
/*      */ import javax.swing.ComboBoxModel;
/*      */ import javax.swing.DefaultComboBoxModel;
/*      */ import javax.swing.GroupLayout;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JCheckBox;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JRadioButton;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.Border;
/*      */ import org.gdstash.db.DBConfig;
/*      */ import org.gdstash.file.ARCDecompress;
/*      */ import org.gdstash.file.ARCList;
/*      */ import org.gdstash.util.GDConstants;
/*      */ import org.gdstash.util.GDImagePool;
/*      */ import org.gdstash.util.GDMsgFormatter;
/*      */ import org.gdstash.util.GDMsgLogger;
/*      */ 
/*      */ public class GDConfigPane extends AdjustablePanel {
/*      */   private GDStashFrame frame;
/*      */   private TitledBorder brdGeneral;
/*      */   private JButton btnHelp;
/*      */   private File dirGD;
/*      */   private String strGD;
/*      */   private JButton btnGD;
/*      */   private JLabel lblGD;
/*      */   private File dirSave;
/*      */   private String strSave;
/*      */   private JButton btnSave;
/*      */   private JLabel lblSave;
/*      */   private File dirExport;
/*      */   private String strExport;
/*      */   private JButton btnExport;
/*      */   private JLabel lblExport;
/*      */   private JLabel lblGDLocal;
/*      */   private JComboBox<String> cbGDLocal;
/*      */   private JLabel lblMod;
/*      */   private JComboBox<String> cbMod;
/*      */   private JLabel lblModStandalone;
/*      */   private JCheckBox cbModStandalone;
/*      */   private JLabel lblLanguage;
/*      */   private JComboBox<String> cbLanguage;
/*      */   private JLabel lblLookNFeel;
/*      */   private JComboBox<String> cbLookNFeel;
/*      */   private JLabel lblFontSize;
/*      */   private JComboBox<String> cbFontSize;
/*      */   private JLabel lblGraphicScale;
/*      */   private JComboBox<String> cbGraphicScale;
/*      */   private JLabel lblMinimizeTray;
/*      */   private JCheckBox cbMinimizeTray;
/*      */   private TitledBorder brdRestrict;
/*      */   private JCheckBox cbAllowEdit;
/*      */   private JCheckBox cbTransSCHC;
/*      */   private JCheckBox cbTransSoul;
/*      */   private JCheckBox cbCompletionAll;
/*      */   private JCheckBox cbBlacksmithAll;
/*      */   private JLabel lblDBStashMove;
/*      */   private JComboBox<String> cbDBStashMove;
/*      */   private TitledBorder brdCombi;
/*      */   private ButtonGroup bgCombi;
/*      */   private JRadioButton rbCombiValid;
/*      */   private JRadioButton rbCombiAllCombos;
/*      */   private JRadioButton rbCombiAllAffixes;
/*      */   private JButton btnConfig;
/*      */   private JButton btnDBImport;
/*      */   private JButton btnARZExport;
/*      */   private JButton btnDevotion;
/*      */   private JButton btnTexConvert;
/*      */   private JPanel pnlMain;
/*      */   
/*      */   private class DBImportWorker extends SwingWorker<Void, Void> {
/*      */     private GDProgressDialog prog;
/*      */     
/*      */     public DBImportWorker() {
/*   85 */       GDConfigPane.this.setCursor(Cursor.getPredefinedCursor(3));
/*      */       
/*   87 */       this.prog = new GDProgressDialog(GDConfigPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "PROG_LOAD_ARCS"));
/*   88 */       this.completed = false;
/*      */ 
/*      */ 
/*      */       
/*   92 */       addPropertyChangeListener(new PropertyChangeListener() {
/*      */             public void propertyChange(PropertyChangeEvent e) {
/*   94 */               if (e.getPropertyName().equals("progress")) {
/*   95 */                 int i = ((Integer)e.getNewValue()).intValue();
/*      */                 
/*   97 */                 if (i < 10) GDConfigPane.DBImportWorker.this.prog.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "PROG_LOAD_ARCS")); 
/*   98 */                 if (i >= 10 && i < 20) GDConfigPane.DBImportWorker.this.prog.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "PROC_IMP_MOD")); 
/*   99 */                 if (i >= 20 && i < 30) GDConfigPane.DBImportWorker.this.prog.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "PROG_DEC_GDX2_ARZ")); 
/*  100 */                 if (i >= 30 && i < 40) GDConfigPane.DBImportWorker.this.prog.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "PROC_IMP_GDX2_ARZ")); 
/*  101 */                 if (i >= 40 && i < 50) GDConfigPane.DBImportWorker.this.prog.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "PROG_DEC_GDX1_ARZ")); 
/*  102 */                 if (i >= 50 && i < 60) GDConfigPane.DBImportWorker.this.prog.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "PROC_IMP_GDX1_ARZ")); 
/*  103 */                 if (i >= 60 && i < 70) GDConfigPane.DBImportWorker.this.prog.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "PROG_DEC_DB_ARZ")); 
/*  104 */                 if (i >= 70 && i < 80) GDConfigPane.DBImportWorker.this.prog.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "PROC_IMP_DB_ARZ")); 
/*  105 */                 if (i >= 80 && i < 90) GDConfigPane.DBImportWorker.this.prog.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "PROC_IMP_IMAGES")); 
/*  106 */                 if (i >= 90 && i < 100) GDConfigPane.DBImportWorker.this.prog.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "PROC_UPD_STASH")); 
/*  107 */                 if (i >= 100) GDConfigPane.DBImportWorker.this.prog.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "PROG_LOAD_STASH"));
/*      */               
/*      */               } 
/*      */             }
/*      */           });
/*      */       
/*  113 */       this.prog.setVisible(true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean completed;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Void doInBackground() {
/*      */       try {
/*  126 */         GDMsgLogger.clear();
/*      */         
/*  128 */         GDStashFrame.dbConfig.gddbInit = false;
/*      */         
/*  130 */         setProgress(0);
/*      */         
/*  132 */         this.completed = false;
/*      */         
/*  134 */         boolean processVanilla = true;
/*  135 */         String mod = (String)GDConfigPane.this.cbMod.getSelectedItem();
/*      */         
/*  137 */         if (mod != null && 
/*  138 */           !mod.isEmpty()) {
/*  139 */           processVanilla = !GDConfigPane.this.cbModStandalone.isSelected();
/*      */         }
/*      */ 
/*      */         
/*  143 */         String s = null;
/*  144 */         String order = null;
/*  145 */         boolean success = true;
/*      */         
/*  147 */         GDStashFrame.arcList = GDConfigPane.this.getImportTextARCList();
/*  148 */         GDStashFrame.arcList.decompress();
/*      */         
/*  150 */         if (GDMsgLogger.severeErrorsInLog()) success = false;
/*      */         
/*  152 */         if (success) {
/*  153 */           order = GDStashFrame.arcList.getTag(ARCDecompress.FileModule.GD, "tags_items.txt", "tagItemNameOrder", false);
/*      */           
/*  155 */           if (order == null) {
/*  156 */             Object[] args = { "tagItemNameOrder", "tags_items.txt" };
/*  157 */             String msg = GDMsgFormatter.format(GDMsgFormatter.rbMsg, "ERR_TAG_NOT_FOUND", args);
/*      */             
/*  159 */             GDMsgLogger.addError(msg);
/*      */             
/*  161 */             success = false;
/*      */           } 
/*      */         } 
/*      */         
/*  165 */         if (success) {
/*  166 */           GDDBData.clearBuffers();
/*      */           
/*  168 */           success = GDDBData.createDataTables();
/*      */         } 
/*      */         
/*  171 */         if (success) {
/*  172 */           DBEngineTagText.collectTags(GDStashFrame.arcList);
/*      */           
/*  174 */           success = (success && DBConfig.setItemNameOrder("1.0.8", "1.7.4", order));
/*      */           
/*  176 */           if (success) success = DBEngineTagText.storeTags();
/*      */         
/*      */         } 
/*  179 */         if (success && 
/*  180 */           mod != null && 
/*  181 */           !mod.isEmpty()) {
/*  182 */           String db = GDConfigPane.this.getModDatabase();
/*      */           
/*  184 */           if (db != null) {
/*  185 */             setProgress(10);
/*      */             
/*  187 */             GDStashFrame.arz = new ARZDecompress(db);
/*  188 */             GDStashFrame.arz.decompress();
/*      */             
/*  190 */             if (GDMsgLogger.severeErrorsInLog()) success = false;
/*      */             
/*  192 */             if (success) success = GDStashFrame.arz.insertData(false);
/*      */           
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  198 */         if (GDStashFrame.arz != null) GDStashFrame.arz.clear();
/*      */         
/*  200 */         if (success && 
/*  201 */           processVanilla && 
/*  202 */           GDStashFrame.expansionForgottenGods) {
/*  203 */           setProgress(20);
/*      */           
/*  205 */           s = GDConfigPane.this.strGD + GDConstants.FILE_SEPARATOR + "gdx2" + GDConstants.FILE_SEPARATOR + "database" + GDConstants.FILE_SEPARATOR + "GDX2.arz";
/*      */           
/*  207 */           GDStashFrame.arz = new ARZDecompress(s);
/*  208 */           GDStashFrame.arz.decompress();
/*      */           
/*  210 */           if (GDMsgLogger.severeErrorsInLog()) success = false;
/*      */           
/*  212 */           setProgress(30);
/*      */           
/*  214 */           if (success) success = GDStashFrame.arz.insertData(true);
/*      */         
/*      */         } 
/*      */ 
/*      */         
/*  219 */         if (GDStashFrame.arz != null) GDStashFrame.arz.clear();
/*      */         
/*  221 */         if (success && 
/*  222 */           processVanilla && 
/*  223 */           GDStashFrame.expansionAshesOfMalmouth) {
/*  224 */           setProgress(40);
/*      */           
/*  226 */           s = GDConfigPane.this.strGD + GDConstants.FILE_SEPARATOR + "gdx1" + GDConstants.FILE_SEPARATOR + "database" + GDConstants.FILE_SEPARATOR + "GDX1.arz";
/*      */           
/*  228 */           GDStashFrame.arz = new ARZDecompress(s);
/*  229 */           GDStashFrame.arz.decompress();
/*      */           
/*  231 */           if (GDMsgLogger.severeErrorsInLog()) success = false;
/*      */           
/*  233 */           setProgress(50);
/*      */           
/*  235 */           if (success) success = GDStashFrame.arz.insertData(true);
/*      */         
/*      */         } 
/*      */ 
/*      */         
/*  240 */         if (GDStashFrame.arz != null) GDStashFrame.arz.clear();
/*      */         
/*  242 */         if (success && 
/*  243 */           processVanilla) {
/*  244 */           setProgress(60);
/*      */           
/*  246 */           s = GDConfigPane.this.strGD + GDConstants.FILE_SEPARATOR + "database" + GDConstants.FILE_SEPARATOR + "database.arz";
/*  247 */           GDStashFrame.arz = new ARZDecompress(s);
/*  248 */           GDStashFrame.arz.decompress();
/*      */           
/*  250 */           if (GDMsgLogger.severeErrorsInLog()) success = false;
/*      */           
/*  252 */           setProgress(70);
/*      */           
/*  254 */           if (success) success = GDStashFrame.arz.insertData(true);
/*      */         
/*      */         } 
/*      */         
/*  258 */         if (GDStashFrame.arz != null) GDStashFrame.arz.clear();
/*      */         
/*  260 */         if (success) {
/*  261 */           setProgress(80);
/*      */           
/*  263 */           GDStashFrame.arcList = GDConfigPane.this.getImportImageARCList();
/*      */           
/*  265 */           GDStashFrame.arcList.updateImageData();
/*      */           
/*  267 */           GDDBData.updateDB();
/*      */         } 
/*      */         
/*  270 */         if (success)
/*      */         {
/*  272 */           if (GDMsgLogger.severeErrorsInLog()) success = false;
/*      */         
/*      */         }
/*  275 */         if (success) {
/*  276 */           setProgress(90);
/*      */           
/*  278 */           success = GDDBData.updateStash();
/*      */           
/*  280 */           if (GDMsgLogger.severeErrorsInLog()) success = false;
/*      */           
/*  282 */           setProgress(100);
/*      */           
/*  284 */           success = (success && DBConfig.setGDDBInit("1.0.8", "1.7.4", success));
/*  285 */           success = (success && DBConfig.setVersions("1.0.8", "1.7.4"));
/*      */         } 
/*      */         
/*  288 */         if (success) {
/*  289 */           String msg = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_DB_IMPORT");
/*  290 */           GDMsgLogger.addSuccess(msg);
/*      */ 
/*      */           
/*  293 */           GDStashFrame.dbConfig.gddbInit = success;
/*      */           
/*  295 */           GDConfigPane.this.initData();
/*      */         } 
/*      */         
/*  298 */         if (success) {
/*  299 */           GDStashInfoList.findStashes(GDConfigPane.this.frame, null, null);
/*      */           
/*  301 */           if (GDConfigPane.this.frame.pnlTransfer != null) {
/*  302 */             GDConfigPane.this.frame.pnlTransfer.setStash(GDStashInfoList.defaultStash);
/*      */           }
/*  304 */           if (GDConfigPane.this.frame.pnlCraft != null) {
/*  305 */             GDConfigPane.this.frame.pnlCraft.setStash(GDStashInfoList.defaultStash);
/*      */           }
/*      */           
/*  308 */           GDCharInfoList.findChars(GDConfigPane.this.frame, null);
/*      */           
/*  310 */           if (GDConfigPane.this.frame.pnlCharInventory != null) {
/*  311 */             GDConfigPane.this.frame.pnlCharInventory.initCharSelection();
/*      */           }
/*  313 */           if (GDConfigPane.this.frame.pnlCharEdit != null) {
/*  314 */             GDConfigPane.this.frame.pnlCharEdit.initCharSelection();
/*      */           }
/*      */           
/*  317 */           GDConfigPane.this.frame.refresh();
/*      */           
/*  319 */           this.completed = true;
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  328 */       catch (Throwable ex) {
/*  329 */         GDMsgLogger.addError(ex);
/*      */         
/*  331 */         this.completed = false;
/*      */       } 
/*      */       
/*  334 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void done() {
/*  340 */       this.prog.setVisible(false);
/*  341 */       this.prog.dispose();
/*      */       
/*  343 */       if (!this.completed && !GDMsgLogger.severeErrorsInLog()) {
/*  344 */         GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_DB_IMPORT"));
/*      */       }
/*      */       
/*  347 */       GDStashFrame.arcList = null;
/*  348 */       if (GDStashFrame.arz != null) {
/*  349 */         GDStashFrame.arz.clear();
/*      */         
/*  351 */         GDStashFrame.arz = null;
/*      */       } 
/*      */       
/*  354 */       GDConfigPane.this.setCursor(Cursor.getDefaultCursor());
/*      */       
/*  356 */       GDMsgLogger.showSevereLog((Component)GDConfigPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_DB_IMPORT"), GDLog.MessageType.Success, 
/*  357 */           GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_DB_IMPORT"), true, false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class ARZExportWorker extends SwingWorker<Void, Void> {
/*      */     private GDProgressDialog prog;
/*      */     private boolean completed;
/*      */     
/*      */     public ARZExportWorker() {
/*  366 */       GDConfigPane.this.setCursor(Cursor.getPredefinedCursor(3));
/*      */       
/*  368 */       this.prog = new GDProgressDialog(GDConfigPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "PROG_EXP_ARCS"));
/*  369 */       this.completed = false;
/*      */ 
/*      */ 
/*      */       
/*  373 */       addPropertyChangeListener(new PropertyChangeListener() {
/*      */             public void propertyChange(PropertyChangeEvent e) {
/*  375 */               if (e.getPropertyName().equals("progress")) {
/*  376 */                 int i = ((Integer)e.getNewValue()).intValue();
/*      */                 
/*  378 */                 if (i < 30) GDConfigPane.ARZExportWorker.this.prog.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "PROG_EXP_ARCS")); 
/*  379 */                 if (i >= 30 && i < 70) GDConfigPane.ARZExportWorker.this.prog.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "PROG_DEC_DB_ARZ")); 
/*  380 */                 if (i >= 70) GDConfigPane.ARZExportWorker.this.prog.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "PROC_EXP_DB_ARZ"));
/*      */               
/*      */               } 
/*      */             }
/*      */           });
/*      */       
/*  386 */       this.prog.setVisible(true);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Void doInBackground() {
/*  392 */       GDMsgLogger.clear();
/*      */       
/*  394 */       setProgress(0);
/*      */       
/*  396 */       this.completed = false;
/*      */       
/*  398 */       boolean success = true;
/*      */       
/*  400 */       String db = GDConfigPane.this.getModDatabase();
/*      */       
/*  402 */       boolean isMod = (db != null);
/*      */       
/*  404 */       if (!isMod) {
/*  405 */         db = GDConfigPane.this.strGD + GDConstants.FILE_SEPARATOR + "database" + GDConstants.FILE_SEPARATOR + "database.arz";
/*      */       }
/*      */       
/*  408 */       GDStashFrame.arcList = GDConfigPane.this.getExportARCList();
/*  409 */       GDStashFrame.arcList.writeFiles(GDConfigPane.this.strExport, !isMod);
/*      */       
/*  411 */       setProgress(30);
/*      */       
/*  413 */       GDStashFrame.arz = new ARZDecompress(db);
/*  414 */       GDStashFrame.arz.decompress();
/*  415 */       GDStashFrame.arz.extractRecords(GDConfigPane.this.strExport);
/*      */       
/*  417 */       setProgress(90);
/*      */       
/*  419 */       if (GDMsgLogger.errorsInLog()) success = false;
/*      */       
/*  421 */       if (success) this.completed = true;
/*      */       
/*  423 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void done() {
/*  429 */       this.prog.setVisible(false);
/*  430 */       this.prog.dispose();
/*      */       
/*  432 */       if (!this.completed && !GDMsgLogger.errorsInLog()) {
/*  433 */         GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_DB_EXPORT"));
/*      */       }
/*      */       
/*  436 */       GDStashFrame.arcList = null;
/*      */       
/*  438 */       if (GDStashFrame.arz != null) {
/*  439 */         GDStashFrame.arz.clear();
/*      */         
/*  441 */         GDStashFrame.arz = null;
/*      */       } 
/*      */       
/*  444 */       GDConfigPane.this.setCursor(Cursor.getDefaultCursor());
/*      */       
/*  446 */       GDMsgLogger.showLog((Component)GDConfigPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_DB_EXPORT"), GDLog.MessageType.Success, 
/*  447 */           GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_DB_EXPORT"), true, false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class TextureConvertWorker extends SwingWorker<Void, Void> {
/*      */     private GDProgressDialog prog;
/*      */     private boolean completed;
/*      */     
/*      */     public TextureConvertWorker() {
/*  456 */       GDConfigPane.this.setCursor(Cursor.getPredefinedCursor(3));
/*      */       
/*  458 */       this.prog = new GDProgressDialog(GDConfigPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "PROG_CONVERT_TEX"));
/*  459 */       this.completed = false;
/*      */       
/*  461 */       this.prog.setVisible(true);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Void doInBackground() {
/*  467 */       GDMsgLogger.clear();
/*      */       
/*  469 */       this.completed = false;
/*      */       
/*  471 */       boolean success = true;
/*      */       
/*  473 */       ARZDecompress.convertTextures(GDConfigPane.this.strExport);
/*      */       
/*  475 */       if (GDMsgLogger.errorsInLog()) success = false;
/*      */       
/*  477 */       if (success) this.completed = true;
/*      */       
/*  479 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void done() {
/*  485 */       this.prog.setVisible(false);
/*  486 */       this.prog.dispose();
/*      */       
/*  488 */       if (!this.completed && !GDMsgLogger.errorsInLog()) {
/*  489 */         GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_TEX_CONVERT"));
/*      */       }
/*      */       
/*  492 */       GDConfigPane.this.setCursor(Cursor.getDefaultCursor());
/*      */       
/*  494 */       GDMsgLogger.showLog((Component)GDConfigPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_TEX_CONVERT"), GDLog.MessageType.Success, 
/*  495 */           GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_TEX_CONVERT"), true, false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class GDDirListener implements ActionListener { private GDDirListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  502 */       JFileChooser chooser = new JFileChooser(GDConfigPane.this.dirGD);
/*  503 */       chooser.setFileSelectionMode(1);
/*  504 */       chooser.setAcceptAllFileFilterUsed(false);
/*      */       
/*  506 */       if (chooser.showOpenDialog((Component)GDConfigPane.this) == 0) {
/*  507 */         GDConfigPane.this.dirGD = chooser.getSelectedFile();
/*      */         
/*      */         try {
/*  510 */           String s = GDConfigPane.this.dirGD.getCanonicalPath();
/*  511 */           if (GDConfigPane.this.isCorrectInstallDir(s)) {
/*  512 */             GDConfigPane.this.strGD = s;
/*  513 */             GDConfigPane.this.lblGD.setText(GDConfigPane.this.strGD);
/*      */             
/*  515 */             GDConfigPane.this.cbMod.setModel(GDConfigPane.this.buildModModel());
/*  516 */             GDConfigPane.this.cbGDLocal.setModel(GDConfigPane.this.buildGDLanguageModel());
/*      */             
/*  518 */             s = GDConfigPane.this.strGD + GDConstants.FILE_SEPARATOR + "gdx1" + GDConstants.FILE_SEPARATOR + "database" + GDConstants.FILE_SEPARATOR + "GDX1.arz";
/*  519 */             File fGDX1 = new File(s);
/*      */             
/*  521 */             GDStashFrame.expansionAshesOfMalmouth = fGDX1.exists();
/*      */             
/*  523 */             s = GDConfigPane.this.strGD + GDConstants.FILE_SEPARATOR + "gdx2" + GDConstants.FILE_SEPARATOR + "database" + GDConstants.FILE_SEPARATOR + "GDX2.arz";
/*  524 */             File fGDX2 = new File(s);
/*      */             
/*  526 */             GDStashFrame.expansionForgottenGods = fGDX2.exists();
/*      */           } else {
/*      */             
/*  529 */             GDDialog dialog = new GDDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_WRONG_DIR"), 4, GDConfigPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERROR"), true);
/*  530 */             dialog.setVisible(true);
/*      */           
/*      */           }
/*      */         
/*      */         }
/*  535 */         catch (IOException iOException) {}
/*      */       } 
/*      */       
/*  538 */       GDMsgLogger.showLog((Component)GDConfigPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     } }
/*      */   
/*      */   private class SaveDirListener implements ActionListener {
/*      */     private SaveDirListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  545 */       JFileChooser chooser = new JFileChooser(GDConfigPane.this.dirSave);
/*  546 */       chooser.setFileSelectionMode(1);
/*  547 */       chooser.setAcceptAllFileFilterUsed(false);
/*      */       
/*  549 */       if (chooser.showOpenDialog((Component)GDConfigPane.this) == 0) {
/*  550 */         GDConfigPane.this.dirSave = chooser.getSelectedFile();
/*      */         
/*      */         try {
/*  553 */           String s = GDConfigPane.this.dirSave.getCanonicalPath();
/*  554 */           if (GDConfigPane.this.isCorrectSaveDir(s)) {
/*  555 */             GDConfigPane.this.strSave = s;
/*  556 */             GDConfigPane.this.lblSave.setText(GDConfigPane.this.strSave);
/*      */             
/*  558 */             if (GDStashFrame.dbConfig.gddbInit) {
/*  559 */               GDStashFrame.iniConfig.sectDir.savePath = GDConfigPane.this.strSave;
/*      */               
/*  561 */               GDStashInfoList.findStashes(GDConfigPane.this.frame, null, null);
/*  562 */               GDCharInfoList.findChars(GDConfigPane.this.frame, null);
/*  563 */               GDConfigPane.this.frame.pnlTransfer.setStashInfo(GDStashInfoList.defaultStash);
/*  564 */               GDConfigPane.this.frame.pnlCraft.setStashInfo(GDStashInfoList.defaultStash);
/*      */             } 
/*      */           } else {
/*      */             
/*  568 */             GDDialog dialog = new GDDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_WRONG_DIR"), 4, GDConfigPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERROR"), true);
/*  569 */             dialog.setVisible(true);
/*      */           
/*      */           }
/*      */         
/*      */         }
/*  574 */         catch (IOException iOException) {}
/*      */       } 
/*      */       
/*  577 */       GDMsgLogger.showLog((Component)GDConfigPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
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
/*      */   private class ExportDirListener
/*      */     implements ActionListener
/*      */   {
/*      */     private ExportDirListener() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  607 */       JFileChooser chooser = new JFileChooser(GDConfigPane.this.dirExport);
/*  608 */       chooser.setFileSelectionMode(1);
/*  609 */       chooser.setAcceptAllFileFilterUsed(false);
/*      */       
/*  611 */       if (chooser.showOpenDialog((Component)GDConfigPane.this) == 0) {
/*  612 */         GDConfigPane.this.dirExport = chooser.getSelectedFile();
/*      */         
/*      */         try {
/*  615 */           String s = GDConfigPane.this.dirExport.getCanonicalPath();
/*      */           
/*  617 */           GDConfigPane.this.strExport = s;
/*  618 */           GDConfigPane.this.lblExport.setText(GDConfigPane.this.strExport);
/*      */         }
/*  620 */         catch (IOException iOException) {}
/*      */       } 
/*      */       
/*  623 */       GDMsgLogger.showLog((Component)GDConfigPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     } }
/*      */   
/*      */   private class ModListener implements ActionListener {
/*      */     private ModListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  630 */       GDStashFrame.iniConfig.sectHistory.lastMod = (String)GDConfigPane.this.cbMod.getSelectedItem();
/*      */       
/*  632 */       GDConfigPane.this.initData();
/*      */       
/*  634 */       GDMsgLogger.showLog((Component)GDConfigPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "MESSAGES"), GDLog.MessageType.Info, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"), false, false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class LocalizationListener implements ActionListener { private LocalizationListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  641 */       GDStashFrame.dbConfig.gdLocal = (String)GDConfigPane.this.cbGDLocal.getSelectedItem();
/*      */     } }
/*      */   
/*      */   private class LanguageListener implements ActionListener {
/*      */     private LanguageListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  648 */       GDStashFrame.iniConfig.sectUI.language = (String)GDConfigPane.this.cbLanguage.getSelectedItem();
/*      */       
/*      */       try {
/*  651 */         GDStashFrame.setGDLocale(GDStashFrame.iniConfig.sectUI.language, GDConfigPane.this.frame);
/*      */       }
/*  653 */       catch (IOException ex) {
/*  654 */         GDMsgLogger.addError(ex);
/*      */       } 
/*      */       
/*  657 */       GDMsgLogger.showLog((Component)GDConfigPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     }
/*      */   }
/*      */   
/*      */   private class LookNFeelListener implements ActionListener { private LookNFeelListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  664 */       String sLnF = (String)GDConfigPane.this.cbLookNFeel.getSelectedItem();
/*      */       
/*      */       try {
/*  667 */         UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
/*      */         int i;
/*  669 */         for (i = 0; i < info.length; i++) {
/*  670 */           if (info[i].getName().equals(sLnF)) {
/*      */ 
/*      */ 
/*      */             
/*  674 */             GDColor.setLookAndFeelUI(info[i].getClassName());
/*      */             
/*  676 */             UIManager.setLookAndFeel(info[i].getClassName());
/*      */ 
/*      */             
/*  679 */             if (info[i].getClassName().equals("com.seaglasslookandfeel.SeaGlassLookAndFeel")) {
/*  680 */               Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  681 */               if (fntLabel == null) {
/*  682 */                 UIManager.put("Label.font", new Font("Consolas", 0, 12));
/*      */               }
/*      */             } 
/*      */             
/*  686 */             SwingUtilities.updateComponentTreeUI(GDConfigPane.this.frame);
/*      */ 
/*      */ 
/*      */             
/*  690 */             GDStashFrame.iniConfig.sectUI.lookNFeel = info[i].getName();
/*      */             
/*  692 */             GDStashFrame.adjustUI(GDConfigPane.this.frame);
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*  698 */       } catch (Exception ex) {
/*  699 */         GDConfigPane.this.frame; GDConfigPane.this.cbLookNFeel.setSelectedItem(GDStashFrame.iniConfig.sectUI.lookNFeel);
/*      */       } 
/*      */       
/*  702 */       GDMsgLogger.showLog((Component)GDConfigPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     } }
/*      */   
/*      */   private class FontSizeListener implements ActionListener {
/*      */     private FontSizeListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  709 */       GDStashFrame.iniConfig.sectUI.fontSize = Integer.parseInt((String)GDConfigPane.this.cbFontSize.getSelectedItem());
/*      */       
/*  711 */       GDImagePool.loadImages();
/*  712 */       GDStashFrame.adjustUI(GDConfigPane.this.frame);
/*      */       
/*  714 */       GDMsgLogger.showLog((Component)GDConfigPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     }
/*      */   }
/*      */   
/*      */   private class GraphicScaleListener implements ActionListener { private GraphicScaleListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  721 */       String s = (String)GDConfigPane.this.cbGraphicScale.getSelectedItem();
/*  722 */       int pos = s.indexOf("%");
/*  723 */       if (pos != -1) s = s.substring(0, pos);
/*      */       
/*  725 */       GDStashFrame.iniConfig.sectUI.graphicScale = Integer.parseInt(s);
/*      */       
/*  727 */       GDStashFrame.adjustUI(GDConfigPane.this.frame);
/*      */       
/*  729 */       GDMsgLogger.showLog((Component)GDConfigPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     } }
/*      */   
/*      */   private class MinimizeTrayListener implements ActionListener {
/*      */     private MinimizeTrayListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  736 */       GDStashFrame.iniConfig.sectUI.minimizeToTray = GDConfigPane.this.cbMinimizeTray.isSelected();
/*      */       
/*  738 */       if (GDConfigPane.this.frame.trayIcon != null && 
/*  739 */         SystemTray.isSupported()) {
/*  740 */         GDConfigPane.this.frame.tray = SystemTray.getSystemTray();
/*      */         
/*  742 */         if (GDStashFrame.iniConfig.sectUI.minimizeToTray) {
/*      */           try {
/*  744 */             GDConfigPane.this.frame.tray.add(GDConfigPane.this.frame.trayIcon);
/*      */           }
/*  746 */           catch (Exception ex) {
/*  747 */             GDConfigPane.this.frame.tray = null;
/*      */           } 
/*      */         } else {
/*  750 */           GDConfigPane.this.frame.tray.remove(GDConfigPane.this.frame.trayIcon);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class EditListener implements ActionListener { private EditListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  759 */       GDStashFrame.iniConfig.sectRestrict.allowEdit = GDConfigPane.this.cbAllowEdit.isSelected();
/*      */       
/*  761 */       GDConfigPane.this.frame.pnlTabbed.setEdit(GDStashFrame.iniConfig.sectRestrict.allowEdit);
/*      */       
/*  763 */       GDMsgLogger.showLog((Component)GDConfigPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     } }
/*      */   
/*      */   private class HCSCListener implements ActionListener {
/*      */     private HCSCListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  770 */       GDStashFrame.iniConfig.sectRestrict.transferSCHC = GDConfigPane.this.cbTransSCHC.isSelected();
/*      */       
/*  772 */       if (!GDStashFrame.iniConfig.sectRestrict.transferSCHC)
/*      */       {
/*      */ 
/*      */         
/*  776 */         GDConfigPane.this.frame.pnlTransfer.clearSelItems();
/*      */       }
/*      */       
/*  779 */       GDMsgLogger.showLog((Component)GDConfigPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     }
/*      */   }
/*      */   
/*      */   private class SoulboundListener implements ActionListener { private SoulboundListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  786 */       GDStashFrame.iniConfig.sectRestrict.transferSoulbound = GDConfigPane.this.cbTransSoul.isSelected();
/*      */       
/*  788 */       if (!GDStashFrame.iniConfig.sectRestrict.transferSoulbound)
/*      */       {
/*      */ 
/*      */         
/*  792 */         GDConfigPane.this.frame.pnlTransfer.clearSelItems();
/*      */       }
/*      */       
/*  795 */       GDMsgLogger.showLog((Component)GDConfigPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"));
/*      */     } }
/*      */   
/*      */   private class CompletionListener implements ActionListener {
/*      */     private CompletionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  802 */       GDStashFrame.iniConfig.sectRestrict.completionAll = GDConfigPane.this.cbCompletionAll.isSelected();
/*      */     }
/*      */   }
/*      */   
/*      */   private class BlacksmithListener implements ActionListener { private BlacksmithListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  809 */       GDStashFrame.iniConfig.sectRestrict.blacksmithAll = GDConfigPane.this.cbBlacksmithAll.isSelected();
/*      */     } }
/*      */   
/*      */   private class AffixComboListener implements ActionListener {
/*      */     private AffixComboListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  816 */       if (GDConfigPane.this.rbCombiValid.isSelected()) {
/*  817 */         GDStashFrame.iniConfig.sectRestrict.affixCombi = 1;
/*      */       }
/*      */       
/*  820 */       if (GDConfigPane.this.rbCombiAllCombos.isSelected()) {
/*  821 */         GDStashFrame.iniConfig.sectRestrict.affixCombi = 2;
/*      */       }
/*      */       
/*  824 */       if (GDConfigPane.this.rbCombiAllAffixes.isSelected())
/*  825 */         GDStashFrame.iniConfig.sectRestrict.affixCombi = 3; 
/*      */     }
/*      */   }
/*      */   
/*      */   private class DBStashMoveListener implements ActionListener {
/*      */     private DBStashMoveListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  833 */       int i = GDConfigPane.this.cbDBStashMove.getSelectedIndex();
/*      */       
/*  835 */       GDStashFrame.iniConfig.sectRestrict.dbStashMove = (i == 1);
/*      */     } }
/*      */   
/*      */   private class ConfigListener implements ActionListener {
/*      */     private ConfigListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  842 */       if (GDConfigPane.this.isCorrectDirs()) {
/*  843 */         boolean success = true;
/*      */         
/*  845 */         DBConfig dbConfig = (DBConfig)GDStashFrame.dbConfig.clone();
/*  846 */         dbConfig.gdLocal = (String)GDConfigPane.this.cbGDLocal.getSelectedItem();
/*  847 */         dbConfig.modStandalone = GDConfigPane.this.cbModStandalone.isSelected();
/*      */         
/*  849 */         success = DBConfig.setConfigData("1.0.8", "1.7.4", dbConfig);
/*      */         
/*  851 */         if (success) {
/*  852 */           GDStashFrame.dbConfig = dbConfig;
/*      */           
/*  854 */           success = (success && DBConfig.setConfigInit("1.0.8", "1.7.4", success));
/*  855 */           success = (success && DBConfig.setVersions("1.0.8", "1.7.4"));
/*      */         } 
/*      */         
/*  858 */         GDStashFrame.iniConfig.sectDir.gdPath = GDConfigPane.this.strGD;
/*  859 */         GDStashFrame.iniConfig.sectDir.savePath = GDConfigPane.this.strSave;
/*  860 */         GDStashFrame.iniConfig.sectDir.exportPath = GDConfigPane.this.strExport;
/*  861 */         GDStashFrame.iniConfig.sectUI.language = (String)GDConfigPane.this.cbLanguage.getSelectedItem();
/*  862 */         GDStashFrame.iniConfig.sectRestrict.transferSCHC = GDConfigPane.this.cbTransSCHC.isSelected();
/*  863 */         GDStashFrame.iniConfig.sectRestrict.transferSoulbound = GDConfigPane.this.cbTransSoul.isSelected();
/*  864 */         GDStashFrame.iniConfig.sectRestrict.completionAll = GDConfigPane.this.cbCompletionAll.isSelected();
/*  865 */         GDStashFrame.iniConfig.sectRestrict.blacksmithAll = GDConfigPane.this.cbBlacksmithAll.isSelected();
/*  866 */         GDStashFrame.iniConfig.sectRestrict.affixCombi = 1;
/*  867 */         if (GDConfigPane.this.rbCombiAllCombos.isSelected()) GDStashFrame.iniConfig.sectRestrict.affixCombi = 2; 
/*  868 */         if (GDConfigPane.this.rbCombiAllAffixes.isSelected()) GDStashFrame.iniConfig.sectRestrict.affixCombi = 3; 
/*  869 */         GDStashFrame.iniConfig.sectRestrict.dbStashMove = (GDConfigPane.this.cbDBStashMove.getSelectedIndex() == 1);
/*      */         
/*  871 */         GDStashFrame.dbConfig.configInit = GDStashFrame.iniConfig.write();
/*      */         
/*  873 */         GDConfigPane.this.frame.checkTabs();
/*      */         
/*  875 */         GDMsgLogger.showLog((Component)GDConfigPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCC_SETTINGS_SAVE"), GDLog.MessageType.Success, 
/*  876 */             GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_SETTINGS_SAVE"), true, false);
/*      */       } else {
/*      */         
/*  879 */         GDDialog dialog = new GDDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_SPEC_DIRS"), 4, GDConfigPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERROR"), true);
/*  880 */         dialog.setVisible(true);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class DBImportListener
/*      */     implements ActionListener {
/*      */     private DBImportListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  890 */       if (GDConfigPane.this.isCorrectDirs()) {
/*  891 */         GDConfigPane.DBImportWorker worker = new GDConfigPane.DBImportWorker();
/*  892 */         worker.execute();
/*      */         
/*  894 */         GDMsgLogger.showLog((Component)GDConfigPane.this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "MESSAGES"), GDLog.MessageType.Info, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"), false, false);
/*      */       } else {
/*      */         
/*  897 */         GDDialog dialog = new GDDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_WRONG_DIR"), 4, GDConfigPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERROR"), true);
/*  898 */         dialog.setVisible(true);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class ARZExportListener
/*      */     implements ActionListener {
/*      */     private ARZExportListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  908 */       if (GDConfigPane.this.isCorrectDirs()) {
/*  909 */         GDConfigPane.ARZExportWorker worker = new GDConfigPane.ARZExportWorker();
/*  910 */         worker.execute();
/*      */       } else {
/*      */         
/*  913 */         GDDialog dialog = new GDDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_WRONG_DIR"), 4, GDConfigPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERROR"), true);
/*  914 */         dialog.setVisible(true);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class DevotionListener
/*      */     implements ActionListener {
/*      */     private DevotionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  924 */       String fn = GDConfigPane.this.strExport + GDConstants.FILE_SEPARATOR + "devotions.txt";
/*  925 */       DBConstellation.createFile(fn);
/*      */     } }
/*      */   
/*      */   private class TextureConversionListener implements ActionListener {
/*      */     private TextureConversionListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*  932 */       if (GDConfigPane.this.isCorrectDirs()) {
/*  933 */         GDConfigPane.TextureConvertWorker worker = new GDConfigPane.TextureConvertWorker();
/*  934 */         worker.execute();
/*      */       } else {
/*      */         
/*  937 */         GDDialog dialog = new GDDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_WRONG_DIR"), 4, GDConfigPane.this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERROR"), true);
/*  938 */         dialog.setVisible(true);
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
/*      */   public GDConfigPane(GDStashFrame frame) {
/* 1017 */     this.frame = frame;
/*      */     
/* 1019 */     this.strGD = GDStashFrame.iniConfig.sectDir.gdPath;
/* 1020 */     this.strSave = GDStashFrame.iniConfig.sectDir.savePath;
/*      */ 
/*      */     
/* 1023 */     adjustUI();
/*      */     
/* 1025 */     GroupLayout layout = null;
/* 1026 */     GroupLayout.SequentialGroup hGroup = null;
/* 1027 */     GroupLayout.SequentialGroup vGroup = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1034 */     this.pnlMain = buildMainPanel();
/* 1035 */     JScrollPane scroll = new JScrollPane(this.pnlMain);
/* 1036 */     scroll.getVerticalScrollBar().setUnitIncrement(2 * GDStashFrame.iniConfig.sectUI.fontSize);
/*      */     
/* 1038 */     layout = new GroupLayout((Container)this);
/* 1039 */     setLayout(layout);
/*      */     
/* 1041 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/* 1044 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/* 1047 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/* 1050 */     hGroup
/* 1051 */       .addGroup(layout.createParallelGroup()
/* 1052 */         .addComponent(scroll));
/* 1053 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/* 1056 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/* 1059 */     vGroup
/* 1060 */       .addGroup(layout.createParallelGroup()
/* 1061 */         .addComponent(scroll));
/* 1062 */     layout.setVerticalGroup(vGroup);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void adjustUI() {
/* 1071 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 1072 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/* 1073 */     if (fntButton == null) fntButton = fntLabel; 
/* 1074 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 1075 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 1076 */     Font fntRadio = UIManager.getDefaults().getFont("RadioButton.font");
/* 1077 */     if (fntRadio == null) fntRadio = fntLabel; 
/* 1078 */     Font fntCombo = UIManager.getDefaults().getFont("ComboBox.font");
/* 1079 */     if (fntCombo == null) fntCombo = fntLabel; 
/* 1080 */     Font fntTip = UIManager.getDefaults().getFont("ToolTip.font");
/* 1081 */     if (fntTip == null) fntTip = fntLabel; 
/* 1082 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 1083 */     if (fntBorder == null) fntBorder = fntLabel;
/*      */     
/* 1085 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 1086 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 1087 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 1088 */     fntRadio = fntRadio.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 1089 */     fntCombo = fntCombo.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 1090 */     fntTip = fntTip.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 1091 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*      */     
/* 1093 */     if (this.brdGeneral == null || this.brdRestrict == null || this.brdCombi == null) {
/*      */ 
/*      */       
/* 1096 */       Border lowered = BorderFactory.createEtchedBorder(1);
/* 1097 */       Border raised = BorderFactory.createEtchedBorder(0);
/* 1098 */       Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*      */       
/* 1100 */       if (this.brdGeneral == null) this.brdGeneral = BorderFactory.createTitledBorder(compound); 
/* 1101 */       if (this.brdRestrict == null) this.brdRestrict = BorderFactory.createTitledBorder(compound); 
/* 1102 */       if (this.brdCombi == null) this.brdCombi = BorderFactory.createTitledBorder(compound);
/*      */     
/*      */     } 
/* 1105 */     this.brdGeneral.setTitle(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BRD_GENERAL"));
/* 1106 */     this.brdGeneral.setTitleFont(fntBorder);
/*      */     
/* 1108 */     this.brdRestrict.setTitle(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BRD_RESTRICT"));
/* 1109 */     this.brdRestrict.setTitleFont(fntBorder);
/*      */     
/* 1111 */     this.brdCombi.setTitle(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BRD_AFFIX_COMBI"));
/* 1112 */     this.brdCombi.setTitleFont(fntBorder);
/*      */     
/* 1114 */     if (this.btnConfig == null) {
/* 1115 */       this.btnConfig = new JButton();
/*      */       
/* 1117 */       this.btnConfig.addActionListener(new ConfigListener());
/*      */     } 
/* 1119 */     this.btnConfig.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_SAVE_SETTINGS"));
/* 1120 */     this.btnConfig.setIcon(GDImagePool.iconConfigSettingSave24);
/* 1121 */     this.btnConfig.setFont(fntButton);
/*      */     
/* 1123 */     if (this.btnDBImport == null) {
/* 1124 */       this.btnDBImport = new JButton();
/*      */       
/* 1126 */       this.btnDBImport.addActionListener(new DBImportListener());
/*      */     } 
/* 1128 */     this.btnDBImport.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_DB_IMPORT"));
/* 1129 */     this.btnDBImport.setIcon(GDImagePool.iconConfigDBImport24);
/* 1130 */     this.btnDBImport.setFont(fntButton);
/*      */     
/* 1132 */     if (this.btnARZExport == null) {
/* 1133 */       this.btnARZExport = new JButton();
/*      */       
/* 1135 */       this.btnARZExport.addActionListener(new ARZExportListener());
/*      */     } 
/* 1137 */     this.btnARZExport.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_DB_EXPORT"));
/* 1138 */     this.btnARZExport.setIcon(GDImagePool.iconConfigARZExport24);
/* 1139 */     this.btnARZExport.setFont(fntButton);
/*      */     
/* 1141 */     if (this.btnTexConvert == null) {
/* 1142 */       this.btnTexConvert = new JButton();
/*      */       
/* 1144 */       this.btnTexConvert.addActionListener(new TextureConversionListener());
/*      */     } 
/* 1146 */     this.btnTexConvert.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_TEXTURE_CONVERT"));
/* 1147 */     this.btnTexConvert.setIcon(GDImagePool.iconConfigTextureConvert24);
/* 1148 */     this.btnTexConvert.setFont(fntButton);
/*      */     
/* 1150 */     if (this.btnDevotion == null) {
/* 1151 */       this.btnDevotion = new JButton();
/*      */       
/* 1153 */       this.btnDevotion.addActionListener(new DevotionListener());
/*      */     } 
/* 1155 */     this.btnDevotion.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_DEVOTION_LIST"));
/* 1156 */     this.btnDevotion.setIcon(GDImagePool.iconConfigDevotionList24);
/* 1157 */     this.btnDevotion.setFont(fntButton);
/*      */     
/* 1159 */     if (this.btnHelp == null) {
/* 1160 */       this.btnHelp = new JButton();
/*      */       
/* 1162 */       this.btnHelp.addActionListener(new GDHelpActionListener("01_config.html"));
/*      */     } 
/* 1164 */     this.btnHelp.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_HELP"));
/* 1165 */     this.btnHelp.setIcon(GDImagePool.iconBtnQuestion24);
/* 1166 */     this.btnHelp.setFont(fntButton);
/* 1167 */     GDStashFrame.setMnemonic(this.btnHelp, "MNC_HELP");
/*      */     
/* 1169 */     if (this.btnGD == null) {
/* 1170 */       this.btnGD = new JButton();
/*      */       
/* 1172 */       this.btnGD.addActionListener(new GDDirListener());
/*      */     } 
/* 1174 */     this.btnGD.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_GD_INSTALL_DIR"));
/* 1175 */     this.btnGD.setIcon(GDImagePool.iconBtnDir24);
/* 1176 */     this.btnGD.setFont(fntButton);
/*      */     
/* 1178 */     if (this.lblGD == null) this.lblGD = new JLabel(); 
/* 1179 */     this.lblGD.setFont(fntLabel);
/*      */     
/* 1181 */     if (this.btnSave == null) {
/* 1182 */       this.btnSave = new JButton();
/*      */       
/* 1184 */       this.btnSave.addActionListener(new SaveDirListener());
/*      */     } 
/* 1186 */     this.btnSave.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_GD_SAVE_DIR"));
/* 1187 */     this.btnSave.setIcon(GDImagePool.iconBtnDir24);
/* 1188 */     this.btnSave.setFont(fntButton);
/*      */     
/* 1190 */     if (this.lblSave == null) this.lblSave = new JLabel(); 
/* 1191 */     this.lblSave.setFont(fntLabel);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1205 */     if (this.btnExport == null) {
/* 1206 */       this.btnExport = new JButton();
/*      */       
/* 1208 */       this.btnExport.addActionListener(new ExportDirListener());
/*      */     } 
/* 1210 */     this.btnExport.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DB_EXPORT_DIR"));
/* 1211 */     this.btnExport.setIcon(GDImagePool.iconBtnDir24);
/* 1212 */     this.btnExport.setFont(fntButton);
/*      */     
/* 1214 */     if (this.lblExport == null) this.lblExport = new JLabel(); 
/* 1215 */     this.lblExport.setFont(fntLabel);
/*      */     
/* 1217 */     if (this.lblMod == null) this.lblMod = new JLabel(); 
/* 1218 */     this.lblMod.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MOD_SEL"));
/* 1219 */     this.lblMod.setFont(fntLabel);
/*      */     
/* 1221 */     if (this.cbMod == null) {
/* 1222 */       this.cbMod = new JComboBox<>();
/*      */       
/* 1224 */       this.cbMod.setModel(buildModModel());
/* 1225 */       this.cbMod.addActionListener(new ModListener());
/*      */     } 
/* 1227 */     this.cbMod.setFont(fntCombo);
/*      */     
/* 1229 */     if (this.lblModStandalone == null) this.lblModStandalone = new JLabel(); 
/* 1230 */     this.lblModStandalone.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MOD_STANDALONE"));
/* 1231 */     this.lblModStandalone.setFont(fntLabel);
/*      */     
/* 1233 */     if (this.cbModStandalone == null) {
/* 1234 */       this.cbModStandalone = new JCheckBox();
/*      */       
/* 1236 */       this.cbModStandalone.setSelected(GDStashFrame.dbConfig.modStandalone);
/*      */     } 
/* 1238 */     this.cbModStandalone.setFont(fntCheck);
/*      */     
/* 1240 */     if (this.lblGDLocal == null) this.lblGDLocal = new JLabel(); 
/* 1241 */     this.lblGDLocal.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_LOCALIZATION"));
/* 1242 */     this.lblGDLocal.setFont(fntLabel);
/*      */     
/* 1244 */     if (this.cbGDLocal == null) {
/* 1245 */       this.cbGDLocal = new JComboBox<>();
/*      */       
/* 1247 */       this.cbGDLocal.setModel(buildGDLanguageModel());
/* 1248 */       this.cbGDLocal.addActionListener(new LocalizationListener());
/*      */     } 
/* 1250 */     this.cbGDLocal.setFont(fntCombo);
/*      */     
/* 1252 */     if (this.lblLanguage == null) this.lblLanguage = new JLabel(); 
/* 1253 */     this.lblLanguage.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_LANG_COUNTRY"));
/* 1254 */     this.lblLanguage.setFont(fntLabel);
/*      */     
/* 1256 */     if (this.cbLanguage == null) {
/* 1257 */       this.cbLanguage = new JComboBox<>();
/*      */       
/* 1259 */       this.cbLanguage.setModel(buildLanguageModel());
/* 1260 */       this.cbLanguage.addActionListener(new LanguageListener());
/*      */     } 
/* 1262 */     this.cbLanguage.setFont(fntCombo);
/*      */     
/* 1264 */     if (this.lblFontSize == null) this.lblFontSize = new JLabel(); 
/* 1265 */     this.lblFontSize.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_FONT_SIZE"));
/* 1266 */     this.lblFontSize.setFont(fntLabel);
/*      */     
/* 1268 */     if (this.cbFontSize == null) {
/* 1269 */       this.cbFontSize = new JComboBox<>();
/*      */       
/* 1271 */       this.cbFontSize.setModel(buildFontSizeModel());
/* 1272 */       this.cbFontSize.addActionListener(new FontSizeListener());
/*      */     } 
/* 1274 */     this.cbFontSize.setFont(fntCombo);
/*      */     
/* 1276 */     if (this.lblGraphicScale == null) this.lblGraphicScale = new JLabel(); 
/* 1277 */     this.lblGraphicScale.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_GRAPHIC_SCALE"));
/* 1278 */     this.lblGraphicScale.setFont(fntLabel);
/*      */     
/* 1280 */     if (this.cbGraphicScale == null) {
/* 1281 */       this.cbGraphicScale = new JComboBox<>();
/*      */       
/* 1283 */       this.cbGraphicScale.setModel(buildGraphicScaleModel());
/* 1284 */       this.cbGraphicScale.addActionListener(new GraphicScaleListener());
/*      */     } 
/* 1286 */     this.cbGraphicScale.setFont(fntCombo);
/*      */     
/* 1288 */     if (this.lblLookNFeel == null) this.lblLookNFeel = new JLabel(); 
/* 1289 */     this.lblLookNFeel.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_LOOK_FEEL"));
/* 1290 */     this.lblLookNFeel.setFont(fntLabel);
/*      */     
/* 1292 */     if (this.cbLookNFeel == null) {
/* 1293 */       this.cbLookNFeel = new JComboBox<>();
/*      */       
/* 1295 */       this.cbLookNFeel.setModel(buildLnFModel());
/* 1296 */       this.cbLookNFeel.addActionListener(new LookNFeelListener());
/*      */     } 
/* 1298 */     this.cbLookNFeel.setFont(fntCombo);
/*      */     
/* 1300 */     if (this.lblMinimizeTray == null) this.lblMinimizeTray = new JLabel(); 
/* 1301 */     this.lblMinimizeTray.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MINIMIZE_TRAY"));
/* 1302 */     this.lblMinimizeTray.setFont(fntLabel);
/*      */     
/* 1304 */     if (this.cbMinimizeTray == null) {
/* 1305 */       this.cbMinimizeTray = new JCheckBox();
/* 1306 */       this.cbMinimizeTray.setSelected(GDStashFrame.iniConfig.sectUI.minimizeToTray);
/*      */       
/* 1308 */       this.cbMinimizeTray.addActionListener(new MinimizeTrayListener());
/*      */     } 
/* 1310 */     this.cbMinimizeTray.setFont(fntCheck);
/*      */     
/* 1312 */     if (this.cbAllowEdit == null) {
/* 1313 */       this.cbAllowEdit = new JCheckBox();
/* 1314 */       this.cbAllowEdit.setSelected(GDStashFrame.iniConfig.sectRestrict.allowEdit);
/*      */       
/* 1316 */       this.cbAllowEdit.addActionListener(new EditListener());
/*      */     } 
/* 1318 */     this.cbAllowEdit.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_ALLOW_EDIT"));
/* 1319 */     this.cbAllowEdit.setFont(fntCheck);
/*      */     
/* 1321 */     if (this.cbTransSCHC == null) {
/* 1322 */       this.cbTransSCHC = new JCheckBox();
/* 1323 */       this.cbTransSCHC.setSelected(GDStashFrame.iniConfig.sectRestrict.transferSCHC);
/*      */       
/* 1325 */       this.cbTransSCHC.addActionListener(new HCSCListener());
/*      */     } 
/* 1327 */     this.cbTransSCHC.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_TRANSFER_SC_HC"));
/* 1328 */     this.cbTransSCHC.setFont(fntCheck);
/*      */     
/* 1330 */     if (this.cbTransSoul == null) {
/* 1331 */       this.cbTransSoul = new JCheckBox();
/* 1332 */       this.cbTransSoul.setSelected(GDStashFrame.iniConfig.sectRestrict.transferSoulbound);
/*      */       
/* 1334 */       this.cbTransSoul.addActionListener(new SoulboundListener());
/*      */     } 
/* 1336 */     this.cbTransSoul.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_TRANSFER_SOUL"));
/* 1337 */     this.cbTransSoul.setFont(fntCheck);
/*      */     
/* 1339 */     if (this.cbCompletionAll == null) {
/* 1340 */       this.cbCompletionAll = new JCheckBox();
/* 1341 */       this.cbCompletionAll.setSelected(GDStashFrame.iniConfig.sectRestrict.completionAll);
/*      */       
/* 1343 */       this.cbCompletionAll.addActionListener(new CompletionListener());
/*      */     } 
/* 1345 */     this.cbCompletionAll.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_COMPLETE_ALL"));
/* 1346 */     this.cbCompletionAll.setToolTipText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TIP_COMPLETE_ALL"));
/* 1347 */     this.cbCompletionAll.setFont(fntCheck);
/*      */     
/* 1349 */     if (this.cbBlacksmithAll == null) {
/* 1350 */       this.cbBlacksmithAll = new JCheckBox();
/* 1351 */       this.cbBlacksmithAll.setSelected(GDStashFrame.iniConfig.sectRestrict.blacksmithAll);
/*      */       
/* 1353 */       this.cbBlacksmithAll.addActionListener(new BlacksmithListener());
/*      */     } 
/* 1355 */     this.cbBlacksmithAll.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_BLACKSMITH_ALL"));
/* 1356 */     this.cbBlacksmithAll.setFont(fntCheck);
/*      */     
/* 1358 */     if (this.rbCombiValid == null) {
/* 1359 */       this.rbCombiValid = new JRadioButton();
/* 1360 */       this.rbCombiValid.setSelected((GDStashFrame.iniConfig.sectRestrict.affixCombi == 1));
/*      */       
/* 1362 */       this.rbCombiValid.addActionListener(new AffixComboListener());
/*      */     } 
/* 1364 */     this.rbCombiValid.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_AFFIX_VALID"));
/* 1365 */     this.rbCombiValid.setToolTipText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TIP_AFFIX_VALID"));
/* 1366 */     this.rbCombiValid.setFont(fntRadio);
/*      */     
/* 1368 */     if (this.rbCombiAllCombos == null) {
/* 1369 */       this.rbCombiAllCombos = new JRadioButton();
/* 1370 */       this.rbCombiAllCombos.setSelected((GDStashFrame.iniConfig.sectRestrict.affixCombi == 2));
/*      */       
/* 1372 */       this.rbCombiAllCombos.addActionListener(new AffixComboListener());
/*      */     } 
/* 1374 */     this.rbCombiAllCombos.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_AFFIX_ALL_COMB"));
/* 1375 */     this.rbCombiAllCombos.setToolTipText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TIP_AFFIX_ALL_COMB"));
/* 1376 */     this.rbCombiAllCombos.setFont(fntRadio);
/*      */     
/* 1378 */     if (this.rbCombiAllAffixes == null) {
/* 1379 */       this.rbCombiAllAffixes = new JRadioButton();
/* 1380 */       this.rbCombiAllAffixes.setSelected((GDStashFrame.iniConfig.sectRestrict.affixCombi == 3));
/*      */       
/* 1382 */       this.rbCombiAllAffixes.addActionListener(new AffixComboListener());
/*      */     } 
/* 1384 */     this.rbCombiAllAffixes.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_AFFIX_ALL_AFFIX"));
/* 1385 */     this.rbCombiAllAffixes.setToolTipText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TIP_AFFIX_ALL_AFFIX"));
/* 1386 */     this.rbCombiAllAffixes.setFont(fntRadio);
/*      */     
/* 1388 */     if (this.lblDBStashMove == null) this.lblDBStashMove = new JLabel(); 
/* 1389 */     this.lblDBStashMove.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DB_STASH_TRANS"));
/* 1390 */     this.lblDBStashMove.setFont(fntLabel);
/*      */     
/* 1392 */     if (this.cbDBStashMove == null) {
/* 1393 */       this.cbDBStashMove = new JComboBox<>();
/*      */       
/* 1395 */       this.cbDBStashMove.addActionListener(new DBStashMoveListener());
/*      */     } 
/* 1397 */     this.cbDBStashMove.setModel(buildDBStashMoveModel());
/* 1398 */     this.cbDBStashMove.setFont(fntCombo);
/*      */   }
/*      */   
/*      */   private void initData() {
/* 1402 */     DBConfig.reset();
/* 1403 */     DBEngineGame.reset();
/* 1404 */     DBEnginePlayer.reset();
/* 1405 */     DBEngineLevel.reset();
/* 1406 */     DBEngineSkillMaster.reset();
/*      */     
/* 1408 */     GDStashFrame.readDBConfig(this.frame);
/*      */     
/* 1410 */     if (GDStashFrame.dbConfig == null) {
/* 1411 */       this.cbModStandalone.setSelected(false);
/*      */     } else {
/* 1413 */       if (GDStashFrame.iniConfig.sectHistory.lastMod == null || GDStashFrame.iniConfig.sectHistory.lastMod
/* 1414 */         .isEmpty()) {
/* 1415 */         GDStashFrame.dbConfig.modStandalone = false;
/*      */       }
/*      */       
/* 1418 */       this.cbModStandalone.setSelected(GDStashFrame.dbConfig.modStandalone);
/*      */       
/* 1420 */       if (GDStashFrame.dbConfig.gddbInit) {
/* 1421 */         ItemSetPane.loadSets();
/* 1422 */         SkillModifierPane.loadSkills();
/* 1423 */         SkillBonusPane.loadSkills();
/* 1424 */         ItemSkillPane.loadSkills();
/*      */         
/* 1426 */         GDMasteryInfoPane.initStats();
/* 1427 */         GDCharMasteryPane.loadMasteries();
/* 1428 */         GDCharMasteryImagePane.initStats();
/* 1429 */         GDStashFrame.initCraftingAffixes();
/*      */ 
/*      */         
/* 1432 */         if (this.frame.pnlTransfer != null) this.frame.pnlTransfer.updateConfig(); 
/* 1433 */         if (this.frame.pnlCharInventory != null) this.frame.pnlCharInventory.updateConfig(); 
/* 1434 */         if (this.frame.pnlCraft != null) this.frame.pnlCraft.updateConfig(); 
/* 1435 */         if (this.frame.pnlCharEdit != null) this.frame.pnlCharEdit.updateConfig(); 
/* 1436 */         if (this.frame.pnlMasteryInfo != null) this.frame.pnlMasteryInfo.updateConfig(); 
/* 1437 */         if (this.frame.pnlCollection != null) this.frame.pnlCollection.updateConfig();
/*      */       
/*      */       } 
/*      */     } 
/* 1441 */     boolean exists = false;
/*      */     try {
/* 1443 */       exists = GDDBUtil.tableExists("GD_ITEM");
/*      */     }
/* 1445 */     catch (SQLException ex) {
/* 1446 */       exists = false;
/*      */     } 
/*      */     
/* 1449 */     if (!exists) {
/* 1450 */       GDMsgLogger.clear();
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1455 */     this.frame.refresh();
/*      */     
/* 1457 */     this.frame.checkTabs();
/*      */   }
/*      */   
/*      */   private JPanel buildMainPanel() {
/* 1461 */     JPanel panel = new JPanel();
/*      */     
/* 1463 */     GroupLayout layout = null;
/* 1464 */     GroupLayout.SequentialGroup hGroup = null;
/* 1465 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/* 1467 */     JPanel pnlConfig = buildConfigPanel();
/*      */     
/* 1469 */     layout = new GroupLayout(panel);
/* 1470 */     panel.setLayout(layout);
/*      */     
/* 1472 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/* 1475 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/* 1478 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1491 */     hGroup
/* 1492 */       .addGroup(layout.createParallelGroup()
/* 1493 */         .addComponent(pnlConfig)
/* 1494 */         .addComponent(this.btnConfig)
/* 1495 */         .addComponent(this.btnDBImport));
/*      */     
/* 1497 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/* 1500 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1518 */     vGroup
/* 1519 */       .addGroup(layout.createParallelGroup()
/* 1520 */         .addComponent(pnlConfig))
/* 1521 */       .addGroup(layout.createParallelGroup()
/* 1522 */         .addComponent(this.btnConfig))
/* 1523 */       .addGroup(layout.createParallelGroup()
/* 1524 */         .addComponent(this.btnDBImport));
/*      */     
/* 1526 */     layout.setVerticalGroup(vGroup);
/*      */     
/* 1528 */     layout.linkSize(0, new Component[] { pnlConfig, this.btnConfig });
/* 1529 */     layout.linkSize(0, new Component[] { pnlConfig, this.btnDBImport });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1536 */     layout.linkSize(1, new Component[] { this.btnConfig, this.btnDBImport });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1543 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildConfigPanel() {
/* 1547 */     GroupLayout layout = null;
/* 1548 */     GroupLayout.SequentialGroup hGroup = null;
/* 1549 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/* 1551 */     JPanel panel = new JPanel();
/*      */     
/* 1553 */     JPanel pnlGeneral = buildGeneralPanel();
/*      */     
/* 1555 */     JPanel pnlRestrict = buildRestrictPanel();
/*      */     
/* 1557 */     layout = new GroupLayout(panel);
/* 1558 */     panel.setLayout(layout);
/*      */     
/* 1560 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/* 1563 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/* 1566 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/* 1569 */     hGroup
/* 1570 */       .addGroup(layout.createParallelGroup()
/* 1571 */         .addComponent(pnlGeneral)
/* 1572 */         .addComponent(pnlRestrict));
/* 1573 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/* 1576 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/* 1579 */     vGroup
/* 1580 */       .addGroup(layout.createParallelGroup()
/* 1581 */         .addComponent(pnlGeneral))
/* 1582 */       .addGroup(layout.createParallelGroup()
/* 1583 */         .addComponent(pnlRestrict));
/* 1584 */     layout.setVerticalGroup(vGroup);
/*      */     
/* 1586 */     layout.linkSize(0, new Component[] { pnlGeneral, pnlRestrict });
/*      */ 
/*      */ 
/*      */     
/* 1590 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildGeneralPanel() {
/* 1594 */     GroupLayout layout = null;
/* 1595 */     GroupLayout.SequentialGroup hGroup = null;
/* 1596 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/* 1598 */     JPanel panel = new JPanel();
/*      */     
/* 1600 */     this.strGD = GDStashFrame.iniConfig.sectDir.gdPath;
/* 1601 */     if (this.strGD == null) {
/* 1602 */       this.lblGD.setText("");
/*      */     } else {
/* 1604 */       this.lblGD.setText(this.strGD);
/* 1605 */       this.dirGD = new File(this.strGD);
/*      */     } 
/*      */     
/* 1608 */     this.strSave = GDStashFrame.iniConfig.sectDir.savePath;
/* 1609 */     if (this.strSave == null) {
/* 1610 */       this.lblSave.setText("");
/*      */     } else {
/* 1612 */       this.lblSave.setText(this.strSave);
/* 1613 */       this.dirSave = new File(this.strSave);
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
/* 1624 */     this.strExport = GDStashFrame.iniConfig.sectDir.exportPath;
/* 1625 */     if (this.strExport == null || this.strExport.equals("")) {
/* 1626 */       this.strExport = GDConstants.USER_DIR;
/*      */     }
/* 1628 */     this.lblExport.setText(this.strExport);
/* 1629 */     this.dirExport = new File(this.strExport);
/*      */     
/* 1631 */     StrLenDocFilter strLenDocFilter = new StrLenDocFilter(2);
/* 1632 */     AbstractDocument doc = null;
/*      */     
/* 1634 */     layout = new GroupLayout(panel);
/* 1635 */     panel.setLayout(layout);
/*      */     
/* 1637 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/* 1640 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/* 1643 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1676 */     hGroup
/* 1677 */       .addGroup(layout.createParallelGroup()
/* 1678 */         .addComponent(this.btnHelp)
/* 1679 */         .addComponent(this.btnGD)
/* 1680 */         .addComponent(this.btnSave)
/*      */         
/* 1682 */         .addComponent(this.lblGDLocal)
/* 1683 */         .addComponent(this.lblMod)
/* 1684 */         .addComponent(this.lblModStandalone)
/* 1685 */         .addComponent(this.lblLanguage)
/* 1686 */         .addComponent(this.lblLookNFeel)
/* 1687 */         .addComponent(this.lblMinimizeTray)
/* 1688 */         .addComponent(this.lblFontSize)
/* 1689 */         .addComponent(this.lblGraphicScale))
/* 1690 */       .addGroup(layout.createParallelGroup()
/* 1691 */         .addComponent(this.lblGD)
/* 1692 */         .addComponent(this.lblSave)
/*      */         
/* 1694 */         .addComponent(this.cbGDLocal)
/* 1695 */         .addComponent(this.cbMod)
/* 1696 */         .addComponent(this.cbModStandalone)
/* 1697 */         .addComponent(this.cbLanguage)
/* 1698 */         .addComponent(this.cbLookNFeel)
/* 1699 */         .addComponent(this.cbMinimizeTray)
/* 1700 */         .addComponent(this.cbFontSize)
/* 1701 */         .addComponent(this.cbGraphicScale));
/*      */     
/* 1703 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/* 1706 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1750 */     vGroup
/* 1751 */       .addGroup(layout.createParallelGroup()
/* 1752 */         .addComponent(this.btnHelp))
/* 1753 */       .addGroup(layout.createParallelGroup()
/* 1754 */         .addComponent(this.btnGD)
/* 1755 */         .addComponent(this.lblGD))
/* 1756 */       .addGroup(layout.createParallelGroup()
/* 1757 */         .addComponent(this.btnSave)
/* 1758 */         .addComponent(this.lblSave))
/*      */ 
/*      */ 
/*      */       
/* 1762 */       .addGroup(layout.createParallelGroup()
/* 1763 */         .addComponent(this.lblGDLocal)
/* 1764 */         .addComponent(this.cbGDLocal))
/* 1765 */       .addGroup(layout.createParallelGroup()
/* 1766 */         .addComponent(this.lblMod)
/* 1767 */         .addComponent(this.cbMod))
/* 1768 */       .addGroup(layout.createParallelGroup()
/* 1769 */         .addComponent(this.lblModStandalone)
/* 1770 */         .addComponent(this.cbModStandalone))
/* 1771 */       .addGroup(layout.createParallelGroup()
/* 1772 */         .addComponent(this.lblLanguage)
/* 1773 */         .addComponent(this.cbLanguage))
/* 1774 */       .addGroup(layout.createParallelGroup()
/* 1775 */         .addComponent(this.lblLookNFeel)
/* 1776 */         .addComponent(this.cbLookNFeel))
/* 1777 */       .addGroup(layout.createParallelGroup()
/* 1778 */         .addComponent(this.lblMinimizeTray)
/* 1779 */         .addComponent(this.cbMinimizeTray))
/* 1780 */       .addGroup(layout.createParallelGroup()
/* 1781 */         .addComponent(this.lblFontSize)
/* 1782 */         .addComponent(this.cbFontSize))
/* 1783 */       .addGroup(layout.createParallelGroup()
/* 1784 */         .addComponent(this.lblGraphicScale)
/* 1785 */         .addComponent(this.cbGraphicScale));
/*      */     
/* 1787 */     layout.setVerticalGroup(vGroup);
/*      */     
/* 1789 */     layout.linkSize(0, new Component[] { this.btnGD, this.btnHelp });
/* 1790 */     layout.linkSize(0, new Component[] { this.btnGD, this.btnSave });
/*      */ 
/*      */     
/* 1793 */     layout.linkSize(0, new Component[] { this.btnGD, this.lblGDLocal });
/* 1794 */     layout.linkSize(0, new Component[] { this.btnGD, this.lblMod });
/* 1795 */     layout.linkSize(0, new Component[] { this.btnGD, this.lblModStandalone });
/* 1796 */     layout.linkSize(0, new Component[] { this.btnGD, this.lblLanguage });
/* 1797 */     layout.linkSize(0, new Component[] { this.btnGD, this.lblLookNFeel });
/* 1798 */     layout.linkSize(0, new Component[] { this.btnGD, this.lblMinimizeTray });
/* 1799 */     layout.linkSize(0, new Component[] { this.btnGD, this.lblFontSize });
/* 1800 */     layout.linkSize(0, new Component[] { this.btnGD, this.lblGraphicScale });
/*      */     
/* 1802 */     layout.linkSize(0, new Component[] { this.btnGD, this.lblGD });
/* 1803 */     layout.linkSize(0, new Component[] { this.btnGD, this.lblSave });
/*      */ 
/*      */     
/* 1806 */     layout.linkSize(0, new Component[] { this.btnGD, this.cbGDLocal });
/* 1807 */     layout.linkSize(0, new Component[] { this.btnGD, this.cbMod });
/* 1808 */     layout.linkSize(0, new Component[] { this.btnGD, this.cbModStandalone });
/* 1809 */     layout.linkSize(0, new Component[] { this.btnGD, this.cbLanguage });
/* 1810 */     layout.linkSize(0, new Component[] { this.btnGD, this.cbLookNFeel });
/* 1811 */     layout.linkSize(0, new Component[] { this.btnGD, this.cbMinimizeTray });
/* 1812 */     layout.linkSize(0, new Component[] { this.btnGD, this.cbFontSize });
/* 1813 */     layout.linkSize(0, new Component[] { this.btnGD, this.cbGraphicScale });
/*      */     
/* 1815 */     layout.linkSize(1, new Component[] { this.btnGD, this.btnHelp });
/* 1816 */     layout.linkSize(1, new Component[] { this.btnGD, this.btnSave });
/*      */ 
/*      */     
/* 1819 */     layout.linkSize(1, new Component[] { this.btnGD, this.lblGDLocal });
/* 1820 */     layout.linkSize(1, new Component[] { this.btnGD, this.lblMod });
/* 1821 */     layout.linkSize(1, new Component[] { this.btnGD, this.lblModStandalone });
/* 1822 */     layout.linkSize(1, new Component[] { this.btnGD, this.lblLanguage });
/* 1823 */     layout.linkSize(1, new Component[] { this.btnGD, this.lblLookNFeel });
/* 1824 */     layout.linkSize(1, new Component[] { this.btnGD, this.lblMinimizeTray });
/* 1825 */     layout.linkSize(1, new Component[] { this.btnGD, this.lblFontSize });
/* 1826 */     layout.linkSize(1, new Component[] { this.btnGD, this.lblGraphicScale });
/*      */     
/* 1828 */     layout.linkSize(1, new Component[] { this.btnGD, this.lblGD });
/* 1829 */     layout.linkSize(1, new Component[] { this.btnGD, this.lblSave });
/*      */ 
/*      */     
/* 1832 */     layout.linkSize(1, new Component[] { this.btnGD, this.cbGDLocal });
/* 1833 */     layout.linkSize(1, new Component[] { this.btnGD, this.cbMod });
/* 1834 */     layout.linkSize(1, new Component[] { this.btnGD, this.cbModStandalone });
/* 1835 */     layout.linkSize(1, new Component[] { this.btnGD, this.cbLanguage });
/* 1836 */     layout.linkSize(1, new Component[] { this.btnGD, this.cbLookNFeel });
/* 1837 */     layout.linkSize(1, new Component[] { this.btnGD, this.cbMinimizeTray });
/* 1838 */     layout.linkSize(1, new Component[] { this.btnGD, this.cbFontSize });
/* 1839 */     layout.linkSize(1, new Component[] { this.btnGD, this.cbGraphicScale });
/*      */     
/* 1841 */     panel.setBorder(this.brdGeneral);
/*      */     
/* 1843 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildRestrictPanel() {
/* 1847 */     GroupLayout layout = null;
/* 1848 */     GroupLayout.SequentialGroup hGroup = null;
/* 1849 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/* 1851 */     JPanel panel = new JPanel();
/*      */     
/* 1853 */     JPanel pnlMisc = buildMiscRestrictPanel();
/* 1854 */     JPanel pnlAffixCombi = buildAffixCombiPanel();
/* 1855 */     JPanel pnlDBStashMove = buildDBStashMovePanel();
/*      */     
/* 1857 */     layout = new GroupLayout(panel);
/* 1858 */     panel.setLayout(layout);
/*      */     
/* 1860 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/* 1863 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/* 1866 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/* 1869 */     hGroup
/* 1870 */       .addGroup(layout.createParallelGroup()
/* 1871 */         .addComponent(pnlMisc)
/* 1872 */         .addComponent(pnlAffixCombi)
/* 1873 */         .addComponent(pnlDBStashMove));
/* 1874 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/* 1877 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/* 1880 */     vGroup
/* 1881 */       .addComponent(pnlMisc)
/* 1882 */       .addComponent(pnlAffixCombi)
/* 1883 */       .addComponent(pnlDBStashMove);
/* 1884 */     layout.setVerticalGroup(vGroup);
/*      */     
/* 1886 */     layout.linkSize(0, new Component[] { pnlMisc, pnlAffixCombi });
/* 1887 */     layout.linkSize(0, new Component[] { pnlMisc, pnlDBStashMove });
/*      */     
/* 1889 */     panel.setBorder(this.brdRestrict);
/*      */     
/* 1891 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildMiscRestrictPanel() {
/* 1895 */     GroupLayout layout = null;
/* 1896 */     GroupLayout.SequentialGroup hGroup = null;
/* 1897 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/* 1899 */     JPanel panel = new JPanel();
/*      */     
/* 1901 */     layout = new GroupLayout(panel);
/* 1902 */     panel.setLayout(layout);
/*      */     
/* 1904 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/* 1907 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/* 1910 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/* 1913 */     hGroup
/* 1914 */       .addGroup(layout.createParallelGroup()
/* 1915 */         .addComponent(this.cbAllowEdit)
/* 1916 */         .addComponent(this.cbTransSCHC)
/* 1917 */         .addComponent(this.cbTransSoul)
/* 1918 */         .addComponent(this.cbCompletionAll)
/* 1919 */         .addComponent(this.cbBlacksmithAll));
/* 1920 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/* 1923 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/* 1926 */     vGroup
/* 1927 */       .addGroup(layout.createParallelGroup()
/* 1928 */         .addComponent(this.cbAllowEdit))
/* 1929 */       .addGroup(layout.createParallelGroup()
/* 1930 */         .addComponent(this.cbTransSCHC))
/* 1931 */       .addGroup(layout.createParallelGroup()
/* 1932 */         .addComponent(this.cbTransSoul))
/* 1933 */       .addGroup(layout.createParallelGroup()
/* 1934 */         .addComponent(this.cbCompletionAll))
/* 1935 */       .addGroup(layout.createParallelGroup()
/* 1936 */         .addComponent(this.cbBlacksmithAll));
/* 1937 */     layout.setVerticalGroup(vGroup);
/*      */     
/* 1939 */     layout.linkSize(0, new Component[] { this.cbAllowEdit, this.cbTransSCHC });
/* 1940 */     layout.linkSize(0, new Component[] { this.cbAllowEdit, this.cbTransSoul });
/* 1941 */     layout.linkSize(0, new Component[] { this.cbAllowEdit, this.cbCompletionAll });
/* 1942 */     layout.linkSize(0, new Component[] { this.cbAllowEdit, this.cbBlacksmithAll });
/*      */     
/* 1944 */     layout.linkSize(1, new Component[] { this.cbAllowEdit, this.cbTransSCHC });
/* 1945 */     layout.linkSize(1, new Component[] { this.cbAllowEdit, this.cbTransSoul });
/* 1946 */     layout.linkSize(1, new Component[] { this.cbAllowEdit, this.cbCompletionAll });
/* 1947 */     layout.linkSize(1, new Component[] { this.cbAllowEdit, this.cbBlacksmithAll });
/*      */ 
/*      */ 
/*      */     
/* 1951 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildAffixCombiPanel() {
/* 1955 */     GroupLayout layout = null;
/* 1956 */     GroupLayout.SequentialGroup hGroup = null;
/* 1957 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/* 1959 */     JPanel panel = new JPanel();
/*      */     
/* 1961 */     this.bgCombi = new ButtonGroup();
/*      */     
/* 1963 */     this.bgCombi.add(this.rbCombiValid);
/* 1964 */     this.bgCombi.add(this.rbCombiAllCombos);
/* 1965 */     this.bgCombi.add(this.rbCombiAllAffixes);
/*      */     
/* 1967 */     layout = new GroupLayout(panel);
/* 1968 */     panel.setLayout(layout);
/*      */     
/* 1970 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/* 1973 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/* 1976 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/* 1979 */     hGroup
/* 1980 */       .addGroup(layout.createParallelGroup()
/* 1981 */         .addComponent(this.rbCombiValid)
/* 1982 */         .addComponent(this.rbCombiAllCombos)
/* 1983 */         .addComponent(this.rbCombiAllAffixes));
/* 1984 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/* 1987 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/* 1990 */     vGroup
/* 1991 */       .addGroup(layout.createParallelGroup()
/* 1992 */         .addComponent(this.rbCombiValid))
/* 1993 */       .addGroup(layout.createParallelGroup()
/* 1994 */         .addComponent(this.rbCombiAllCombos))
/* 1995 */       .addGroup(layout.createParallelGroup()
/* 1996 */         .addComponent(this.rbCombiAllAffixes));
/* 1997 */     layout.setVerticalGroup(vGroup);
/*      */     
/* 1999 */     layout.linkSize(0, new Component[] { this.rbCombiValid, this.rbCombiAllCombos });
/* 2000 */     layout.linkSize(0, new Component[] { this.rbCombiValid, this.rbCombiAllAffixes });
/*      */     
/* 2002 */     layout.linkSize(1, new Component[] { this.rbCombiValid, this.rbCombiAllCombos });
/* 2003 */     layout.linkSize(1, new Component[] { this.rbCombiValid, this.rbCombiAllAffixes });
/*      */     
/* 2005 */     panel.setBorder(this.brdCombi);
/*      */     
/* 2007 */     return panel;
/*      */   }
/*      */   
/*      */   private JPanel buildDBStashMovePanel() {
/* 2011 */     GroupLayout layout = null;
/* 2012 */     GroupLayout.SequentialGroup hGroup = null;
/* 2013 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/* 2015 */     JPanel panel = new JPanel();
/*      */     
/* 2017 */     layout = new GroupLayout(panel);
/* 2018 */     panel.setLayout(layout);
/*      */     
/* 2020 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/* 2023 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/* 2026 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/* 2029 */     hGroup
/* 2030 */       .addGroup(layout.createParallelGroup()
/* 2031 */         .addComponent(this.lblDBStashMove))
/* 2032 */       .addGroup(layout.createParallelGroup()
/* 2033 */         .addComponent(this.cbDBStashMove));
/* 2034 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/* 2037 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/* 2040 */     vGroup
/* 2041 */       .addGroup(layout.createParallelGroup()
/* 2042 */         .addComponent(this.lblDBStashMove)
/* 2043 */         .addComponent(this.cbDBStashMove));
/* 2044 */     layout.setVerticalGroup(vGroup);
/*      */     
/* 2046 */     layout.linkSize(0, new Component[] { this.lblDBStashMove, this.cbDBStashMove });
/*      */     
/* 2048 */     layout.linkSize(1, new Component[] { this.lblDBStashMove, this.cbDBStashMove });
/*      */ 
/*      */ 
/*      */     
/* 2052 */     return panel;
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
/*      */   private ComboBoxModel<String> buildLnFModel() {
/* 2086 */     DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
/*      */     
/* 2088 */     UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
/* 2089 */     for (int i = 0; i < info.length; i++) {
/* 2090 */       String s = info[i].getName();
/*      */       
/* 2092 */       model.addElement(s);
/*      */     } 
/*      */     
/* 2095 */     model.setSelectedItem(GDStashFrame.iniConfig.sectUI.lookNFeel);
/*      */     
/* 2097 */     return model;
/*      */   }
/*      */   
/*      */   private ComboBoxModel<String> buildFontSizeModel() {
/* 2101 */     DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
/*      */     
/* 2103 */     model.addElement("8");
/* 2104 */     model.addElement("10");
/* 2105 */     model.addElement("12");
/* 2106 */     model.addElement("14");
/* 2107 */     model.addElement("16");
/* 2108 */     model.addElement("18");
/* 2109 */     model.addElement("20");
/* 2110 */     model.addElement("24");
/*      */     
/* 2112 */     model.setSelectedItem(Integer.toString(GDStashFrame.iniConfig.sectUI.fontSize));
/*      */     
/* 2114 */     return model;
/*      */   }
/*      */   
/*      */   private ComboBoxModel<String> buildGraphicScaleModel() {
/* 2118 */     DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
/*      */     
/* 2120 */     model.addElement("50%");
/* 2121 */     model.addElement("75%");
/* 2122 */     model.addElement("100%");
/* 2123 */     model.addElement("125%");
/* 2124 */     model.addElement("150%");
/* 2125 */     model.addElement("175%");
/* 2126 */     model.addElement("200%");
/*      */     
/* 2128 */     String s = Integer.toString(GDStashFrame.iniConfig.sectUI.graphicScale) + "%";
/* 2129 */     model.setSelectedItem(s);
/*      */     
/* 2131 */     return model;
/*      */   }
/*      */   
/*      */   private ComboBoxModel<String> buildDBStashMoveModel() {
/* 2135 */     DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
/*      */     
/* 2137 */     String sCopy = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DB_TRANS_COPY");
/* 2138 */     String sMove = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DB_TRANS_MOVE");
/*      */     
/* 2140 */     model.addElement(sCopy);
/* 2141 */     model.addElement(sMove);
/*      */     
/* 2143 */     if (GDStashFrame.iniConfig.sectRestrict.dbStashMove) {
/* 2144 */       model.setSelectedItem(sMove);
/*      */     } else {
/* 2146 */       model.setSelectedItem(sCopy);
/*      */     } 
/*      */     
/* 2149 */     return model;
/*      */   }
/*      */   
/*      */   private ComboBoxModel<String> buildModModel() {
/* 2153 */     DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
/*      */     
/* 2155 */     model.addElement("");
/*      */     
/* 2157 */     if (this.strGD == null || this.strGD.equals("")) return model;
/*      */     
/* 2159 */     File dir = new File(this.strGD + GDConstants.FILE_SEPARATOR + "mods");
/*      */     
/* 2161 */     if (!dir.exists()) return model; 
/* 2162 */     if (!dir.isDirectory()) return model;
/*      */     
/* 2164 */     int len = this.strGD.length() + 6;
/*      */     
/* 2166 */     File[] files = dir.listFiles(); int i;
/* 2167 */     for (i = 0; i < files.length; i++) {
/* 2168 */       if (files[i].isDirectory()) {
/*      */         
/*      */         try {
/* 2171 */           String s = files[i].getCanonicalPath();
/*      */           
/* 2173 */           if (s.length() > len) s = s.substring(len);
/*      */           
/* 2175 */           model.addElement(s);
/*      */         }
/* 2177 */         catch (IOException iOException) {}
/*      */       }
/*      */     } 
/* 2180 */     if (GDStashFrame.iniConfig.sectHistory.lastMod != null) {
/* 2181 */       model.setSelectedItem(GDStashFrame.iniConfig.sectHistory.lastMod);
/*      */     }
/*      */     
/* 2184 */     return model;
/*      */   }
/*      */   
/*      */   private ComboBoxModel<String> buildGDLanguageModel() {
/* 2188 */     DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
/*      */     
/* 2190 */     model.addElement("");
/*      */     
/* 2192 */     if (this.strGD == null || this.strGD.equals("")) return model;
/*      */     
/* 2194 */     File locDir = new File(this.strGD + GDConstants.FILE_SEPARATOR + "localization");
/*      */     
/* 2196 */     File arcDir = new File(this.strGD + GDConstants.FILE_SEPARATOR + "resources");
/*      */     
/* 2198 */     if (arcDir.exists() && arcDir.isDirectory()) {
/* 2199 */       String[] files = arcDir.list(); int i;
/* 2200 */       for (i = 0; i < files.length; i++) {
/* 2201 */         if (files[i].endsWith(".arc") && 
/* 2202 */           files[i].startsWith("Text_"))
/*      */         {
/* 2204 */           model.addElement(files[i]);
/*      */         }
/*      */       } 
/*      */     } 
/* 2208 */     if (locDir.exists() && locDir.isDirectory()) {
/* 2209 */       String[] files = locDir.list(); int i;
/* 2210 */       for (i = 0; i < files.length; i++) {
/* 2211 */         if (files[i].endsWith(".zip"))
/*      */         {
/* 2213 */           model.addElement(files[i]);
/*      */         }
/*      */       } 
/*      */     } 
/* 2217 */     if (GDStashFrame.dbConfig.gdLocal != null) {
/* 2218 */       model.setSelectedItem(GDStashFrame.dbConfig.gdLocal);
/*      */     }
/*      */     
/* 2221 */     return model;
/*      */   }
/*      */   
/*      */   private ComboBoxModel<String> buildLanguageModel() {
/* 2225 */     DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
/*      */     
/* 2227 */     String defLang = "English";
/* 2228 */     model.addElement(defLang);
/*      */     
/* 2230 */     File dir = new File(GDConstants.USER_DIR);
/*      */     
/* 2232 */     if (!dir.exists()) return model; 
/* 2233 */     if (!dir.isDirectory()) return model;
/*      */     
/* 2235 */     String[] files = dir.list(); int i;
/* 2236 */     for (i = 0; i < files.length; i++) {
/* 2237 */       if (files[i].startsWith("UI") && 
/* 2238 */         files[i].endsWith(".properties")) {
/*      */         
/* 2240 */         int pos1 = files[i].indexOf("_");
/* 2241 */         if (pos1 != -1) {
/*      */           
/* 2243 */           int pos2 = files[i].indexOf(".properties");
/* 2244 */           if (pos2 != -1)
/*      */           
/* 2246 */           { String lang = files[i].substring(pos1 + 1, pos2);
/*      */             
/* 2248 */             if (!lang.equals(defLang)) model.addElement(lang);  } 
/*      */         } 
/*      */       } 
/* 2251 */     }  if (GDStashFrame.iniConfig.sectUI.language != null) {
/* 2252 */       String locale = GDStashFrame.iniConfig.sectUI.language;
/*      */       
/* 2254 */       model.setSelectedItem(locale);
/*      */     } else {
/* 2256 */       model.setSelectedItem(defLang);
/*      */     } 
/*      */     
/* 2259 */     return model;
/*      */   }
/*      */   
/*      */   private boolean isCorrectDirs() {
/* 2263 */     return (isCorrectInstallDir(this.strGD) && isCorrectSaveDir(this.strSave));
/*      */   }
/*      */   
/*      */   private boolean isCorrectInstallDir(String dir) {
/* 2267 */     String s = dir + GDConstants.FILE_SEPARATOR + "database" + GDConstants.FILE_SEPARATOR + "database.arz";
/* 2268 */     File f = new File(s);
/*      */     
/* 2270 */     if (!f.exists()) return false;
/*      */     
/* 2272 */     return true;
/*      */   }
/*      */   
/*      */   private boolean isCorrectSaveDir(String dir) {
/* 2276 */     String s1 = dir + GDConstants.FILE_SEPARATOR + "transfer.gst";
/* 2277 */     String s2 = dir + GDConstants.FILE_SEPARATOR + "transfer.gsh";
/* 2278 */     File f1 = new File(s1);
/* 2279 */     File f2 = new File(s2);
/*      */     
/* 2281 */     if (f1.exists() || f2.exists()) return true;
/*      */     
/* 2283 */     String s = dir.substring(dir.length() - 4);
/*      */     
/* 2285 */     if (!s.equals("save")) return false;
/*      */     
/* 2287 */     String s3 = dir + GDConstants.FILE_SEPARATOR + "main";
/* 2288 */     String s4 = dir + GDConstants.FILE_SEPARATOR + "user";
/* 2289 */     File f3 = new File(s3);
/* 2290 */     File f4 = new File(s4);
/*      */     
/* 2292 */     if (f3.exists() && f3.isDirectory()) return true; 
/* 2293 */     if (f4.exists() && f4.isDirectory()) return true;
/*      */     
/* 2295 */     return false;
/*      */   }
/*      */   
/*      */   public String getSaveDir() {
/* 2299 */     if (!isCorrectSaveDir(this.strSave)) return null;
/*      */     
/* 2301 */     return this.strSave;
/*      */   }
/*      */   
/*      */   public String getSelectedMod() {
/* 2305 */     String mod = (String)this.cbMod.getSelectedItem();
/*      */     
/* 2307 */     if (mod == null) return null; 
/* 2308 */     if (mod.length() == 0) return null;
/*      */     
/* 2310 */     return mod;
/*      */   }
/*      */   
/*      */   private String getModDatabase() {
/* 2314 */     String mod = (String)this.cbMod.getSelectedItem();
/*      */     
/* 2316 */     if (mod == null) return null; 
/* 2317 */     if (mod.length() == 0) return null;
/*      */     
/* 2319 */     String db = null;
/*      */     
/* 2321 */     String dir = this.strGD + GDConstants.FILE_SEPARATOR + "mods" + GDConstants.FILE_SEPARATOR + mod + GDConstants.FILE_SEPARATOR + "database";
/*      */     
/* 2323 */     File file = new File(dir);
/* 2324 */     if (file.isDirectory()) {
/* 2325 */       File[] files = file.listFiles();
/*      */       int i;
/* 2327 */       for (i = 0; i < files.length; i++) {
/*      */         try {
/* 2329 */           String s = files[i].getCanonicalPath();
/* 2330 */           if (s.toUpperCase(GDConstants.LOCALE_US).endsWith(".ARZ")) {
/* 2331 */             db = s;
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/* 2336 */         } catch (IOException iOException) {}
/*      */       } 
/*      */     } 
/*      */     
/* 2340 */     return db;
/*      */   }
/*      */   
/*      */   private List<ARCList.ARCFile> getImportARCFileList() {
/* 2344 */     List<ARCList.ARCFile> arcs = new LinkedList<>();
/* 2345 */     ARCList.ARCFile arcFile = null;
/*      */     
/* 2347 */     String fn = null;
/*      */ 
/*      */     
/* 2350 */     if (GDStashFrame.expansionForgottenGods) {
/* 2351 */       fn = this.strGD + GDConstants.FILE_SEPARATOR + "gdx2" + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "Items.arc";
/* 2352 */       arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.FG, fn);
/* 2353 */       arcs.add(arcFile);
/*      */     } 
/*      */ 
/*      */     
/* 2357 */     if (GDStashFrame.expansionAshesOfMalmouth) {
/* 2358 */       fn = this.strGD + GDConstants.FILE_SEPARATOR + "gdx1" + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "Items.arc";
/* 2359 */       arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.AoM, fn);
/* 2360 */       arcs.add(arcFile);
/*      */     } 
/*      */     
/* 2363 */     fn = this.strGD + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "Items.arc";
/* 2364 */     arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.GD, fn);
/* 2365 */     arcs.add(arcFile);
/*      */     
/* 2367 */     if (GDStashFrame.expansionForgottenGods) {
/* 2368 */       fn = this.strGD + GDConstants.FILE_SEPARATOR + "gdx2" + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "UI.arc";
/* 2369 */       arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.FG, fn);
/* 2370 */       arcs.add(arcFile);
/*      */     } 
/*      */     
/* 2373 */     if (GDStashFrame.expansionAshesOfMalmouth) {
/* 2374 */       fn = this.strGD + GDConstants.FILE_SEPARATOR + "gdx1" + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "UI.arc";
/* 2375 */       arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.AoM, fn);
/* 2376 */       arcs.add(arcFile);
/*      */     } 
/*      */     
/* 2379 */     fn = this.strGD + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "UI.arc";
/* 2380 */     arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.GD, fn);
/* 2381 */     arcs.add(arcFile);
/*      */     
/* 2383 */     if (GDStashFrame.expansionForgottenGods) {
/*      */       
/* 2385 */       fn = this.strGD + GDConstants.FILE_SEPARATOR + "gdx2" + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "System.arc";
/* 2386 */       arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.AoM, fn);
/* 2387 */       arcs.add(arcFile);
/*      */     } 
/*      */     
/* 2390 */     if (GDStashFrame.expansionAshesOfMalmouth) {
/*      */       
/* 2392 */       fn = this.strGD + GDConstants.FILE_SEPARATOR + "gdx1" + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "Level Art.arc";
/* 2393 */       arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.AoM, fn);
/* 2394 */       arcs.add(arcFile);
/*      */     } 
/*      */     
/* 2397 */     if (GDStashFrame.dbConfig.gdLocal != null && 
/* 2398 */       !GDStashFrame.dbConfig.gdLocal.equals("")) {
/*      */       
/* 2400 */       if (GDStashFrame.dbConfig.gdLocal.endsWith(".arc")) {
/* 2401 */         fn = this.strGD + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + GDStashFrame.dbConfig.gdLocal;
/*      */       } else {
/* 2403 */         fn = this.strGD + GDConstants.FILE_SEPARATOR + "localization" + GDConstants.FILE_SEPARATOR + GDStashFrame.dbConfig.gdLocal;
/*      */       } 
/* 2405 */       arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.GD, fn);
/* 2406 */       arcs.add(arcFile);
/*      */     } else {
/* 2408 */       if (GDStashFrame.expansionForgottenGods) {
/* 2409 */         fn = this.strGD + GDConstants.FILE_SEPARATOR + "gdx2" + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "Text_EN.arc";
/* 2410 */         arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.FG, fn);
/* 2411 */         arcs.add(arcFile);
/*      */       } 
/*      */       
/* 2414 */       if (GDStashFrame.expansionAshesOfMalmouth) {
/* 2415 */         fn = this.strGD + GDConstants.FILE_SEPARATOR + "gdx1" + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "Text_EN.arc";
/* 2416 */         arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.AoM, fn);
/* 2417 */         arcs.add(arcFile);
/*      */       } 
/*      */       
/* 2420 */       fn = this.strGD + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "Text_EN.arc";
/* 2421 */       arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.GD, fn);
/* 2422 */       arcs.add(arcFile);
/*      */     } 
/*      */     
/* 2425 */     return arcs;
/*      */   }
/*      */   
/*      */   private ARCList getImportTextARCList() {
/* 2429 */     String mod = (String)this.cbMod.getSelectedItem();
/*      */     
/* 2431 */     String dirMod = null;
/* 2432 */     if (mod != null && 
/* 2433 */       !mod.isEmpty()) {
/* 2434 */       dirMod = this.strGD + GDConstants.FILE_SEPARATOR + "mods" + GDConstants.FILE_SEPARATOR + mod + GDConstants.FILE_SEPARATOR + "resources";
/*      */     }
/*      */     
/* 2437 */     List<ARCList.ARCFile> arcs = getImportARCFileList();
/*      */     
/* 2439 */     ARCList list = new ARCList(dirMod, arcs, ARCList.ListType.Text);
/*      */     
/* 2441 */     return list;
/*      */   }
/*      */   
/*      */   private ARCList getImportImageARCList() {
/* 2445 */     String mod = (String)this.cbMod.getSelectedItem();
/*      */     
/* 2447 */     String dirMod = null;
/* 2448 */     if (mod != null && 
/* 2449 */       !mod.isEmpty()) {
/* 2450 */       dirMod = this.strGD + GDConstants.FILE_SEPARATOR + "mods" + GDConstants.FILE_SEPARATOR + mod + GDConstants.FILE_SEPARATOR + "resources";
/*      */     }
/*      */     
/* 2453 */     List<ARCList.ARCFile> arcs = getImportARCFileList();
/*      */     
/* 2455 */     ARCList list = new ARCList(dirMod, arcs, ARCList.ListType.Image);
/*      */     
/* 2457 */     return list;
/*      */   }
/*      */   
/*      */   private ARCList getExportARCList() {
/* 2461 */     String mod = (String)this.cbMod.getSelectedItem();
/*      */     
/* 2463 */     String dirMod = null;
/* 2464 */     if (mod != null && 
/* 2465 */       !mod.isEmpty()) {
/* 2466 */       dirMod = this.strGD + GDConstants.FILE_SEPARATOR + "mods" + GDConstants.FILE_SEPARATOR + mod + GDConstants.FILE_SEPARATOR + "resources";
/*      */     }
/*      */     
/* 2469 */     List<ARCList.ARCFile> arcs = new LinkedList<>();
/* 2470 */     ARCList.ARCFile arcFile = null;
/*      */     
/* 2472 */     String fn = null;
/*      */ 
/*      */     
/* 2475 */     if (GDStashFrame.expansionForgottenGods) {
/* 2476 */       fn = this.strGD + GDConstants.FILE_SEPARATOR + "gdx2" + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "Items.arc";
/* 2477 */       arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.FG, fn);
/* 2478 */       arcs.add(arcFile);
/*      */     } 
/*      */ 
/*      */     
/* 2482 */     if (GDStashFrame.expansionAshesOfMalmouth) {
/* 2483 */       fn = this.strGD + GDConstants.FILE_SEPARATOR + "gdx1" + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "Items.arc";
/* 2484 */       arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.AoM, fn);
/* 2485 */       arcs.add(arcFile);
/*      */       
/* 2487 */       fn = this.strGD + GDConstants.FILE_SEPARATOR + "gdx1" + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "UI.arc";
/* 2488 */       arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.AoM, fn);
/* 2489 */       arcs.add(arcFile);
/*      */       
/* 2491 */       fn = this.strGD + GDConstants.FILE_SEPARATOR + "gdx1" + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "Scripts.arc";
/* 2492 */       arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.AoM, fn);
/* 2493 */       arcs.add(arcFile);
/*      */     } 
/*      */     
/* 2496 */     fn = this.strGD + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "Items.arc";
/* 2497 */     arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.GD, fn);
/* 2498 */     arcs.add(arcFile);
/*      */     
/* 2500 */     fn = this.strGD + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "UI.arc";
/* 2501 */     arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.GD, fn);
/* 2502 */     arcs.add(arcFile);
/*      */     
/* 2504 */     fn = this.strGD + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "Scripts.arc";
/* 2505 */     arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.GD, fn);
/* 2506 */     arcs.add(arcFile);
/*      */     
/* 2508 */     if (GDStashFrame.dbConfig.gdLocal != null && 
/* 2509 */       !GDStashFrame.dbConfig.gdLocal.equals("")) {
/* 2510 */       fn = this.strGD + GDConstants.FILE_SEPARATOR + "localization" + GDConstants.FILE_SEPARATOR + GDStashFrame.dbConfig.gdLocal;
/* 2511 */       arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.GD, fn);
/* 2512 */       arcs.add(arcFile);
/*      */     } else {
/* 2514 */       if (GDStashFrame.expansionForgottenGods) {
/* 2515 */         fn = this.strGD + GDConstants.FILE_SEPARATOR + "gdx2" + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "Text_EN.arc";
/* 2516 */         arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.FG, fn);
/* 2517 */         arcs.add(arcFile);
/*      */       } 
/*      */       
/* 2520 */       if (GDStashFrame.expansionAshesOfMalmouth) {
/* 2521 */         fn = this.strGD + GDConstants.FILE_SEPARATOR + "gdx1" + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "Text_EN.arc";
/* 2522 */         arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.AoM, fn);
/* 2523 */         arcs.add(arcFile);
/*      */       } 
/*      */       
/* 2526 */       fn = this.strGD + GDConstants.FILE_SEPARATOR + "resources" + GDConstants.FILE_SEPARATOR + "Text_EN.arc";
/* 2527 */       arcFile = new ARCList.ARCFile(ARCDecompress.FileModule.GD, fn);
/* 2528 */       arcs.add(arcFile);
/*      */     } 
/*      */     
/* 2531 */     ARCList list = new ARCList(dirMod, arcs, ARCList.ListType.Mixed);
/*      */     
/* 2533 */     return list;
/*      */   }
/*      */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\GDConfigPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */