/*      */ package org.gdstash.ui;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Font;
/*      */ import java.awt.Frame;
/*      */ import java.awt.Image;
/*      */ import java.awt.MenuItem;
/*      */ import java.awt.PopupMenu;
/*      */ import java.awt.SystemTray;
/*      */ import java.awt.TrayIcon;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.ComponentAdapter;
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.event.WindowStateListener;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Enumeration;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import javax.swing.AbstractButton;
/*      */ import javax.swing.DefaultComboBoxModel;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.UIManager;
/*      */ import org.gdstash.db.DBAffix;
/*      */ import org.gdstash.db.DBAffixSet;
/*      */ import org.gdstash.db.DBConfig;
/*      */ import org.gdstash.db.DBEngineGame;
/*      */ import org.gdstash.db.DBEngineLevel;
/*      */ import org.gdstash.db.DBEnginePlayer;
/*      */ import org.gdstash.db.DBEngineSkillMaster;
/*      */ import org.gdstash.db.GDDBData;
/*      */ import org.gdstash.db.GDDBUtil;
/*      */ import org.gdstash.file.ARZDecompress;
/*      */ import org.gdstash.file.IniConfig;
/*      */ import org.gdstash.ui.character.GDCharEditPane;
/*      */ import org.gdstash.ui.character.GDCharInventoryPane;
/*      */ import org.gdstash.ui.character.GDCharMasteryImagePane;
/*      */ import org.gdstash.ui.character.GDCharMasteryPane;
/*      */ import org.gdstash.ui.util.GDCharInfoList;
/*      */ import org.gdstash.ui.util.GDStashInfoList;
/*      */ import org.gdstash.util.GDConstants;
/*      */ import org.gdstash.util.GDImagePool;
/*      */ import org.gdstash.util.GDLog;
/*      */ import org.gdstash.util.GDMsgFormatter;
/*      */ import org.gdstash.util.GDMsgLogger;
/*      */ 
/*      */ public class GDStashFrame extends JFrame {
/*      */   public static final boolean canSave = true;
/*      */   public static final boolean showRNGNumbers = false;
/*      */   public static final boolean showARZExport = false;
/*      */   public static final boolean compressedExport = true;
/*      */   public static final boolean importModItemsOnly = false;
/*      */   public static final boolean extraItemTypes = false;
/*      */   public static final boolean sqlDebug = false;
/*      */   public static final boolean suppressTagWarnings = true;
/*      */   private static final boolean logInfo = false;
/*      */   public static final boolean logDetail = false;
/*      */   private static List<String> logList;
/*      */   public static final String PROGRAM_VERSION = "v1.7.4";
/*      */   public static final String CONFIG_VERSION = "1.0.8";
/*      */   public static final String GDDB_VERSION = "1.7.4";
/*      */   
/*      */   private static class Starter extends Thread {
/*      */     public void run() {
/*   75 */       GDStashFrame frame = null;
/*      */       
/*      */       try {
/*   78 */         frame = new GDStashFrame();
/*      */         
/*   80 */         if (!GDStashFrame.dbConfig.configInit || !GDStashFrame.dbConfig.gddbInit)
/*      */         {
/*      */           
/*   83 */           GDMsgLogger.clear();
/*      */         }
/*      */         
/*   86 */         frame.setLocation(GDStashFrame.iniConfig.sectWindow.x, GDStashFrame.iniConfig.sectWindow.y);
/*   87 */         if (GDStashFrame.iniConfig.sectWindow.maximized) {
/*   88 */           frame.setExtendedState(frame.getExtendedState() | 0x6);
/*      */         } else {
/*   90 */           frame.setSize(GDStashFrame.iniConfig.sectWindow.w, GDStashFrame.iniConfig.sectWindow.h);
/*      */         } 
/*      */         
/*   93 */         frame.setVisible(true);
/*   94 */         GDMsgLogger.showLog(frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "MESSAGES"), GDLog.MessageType.Info, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERRORS"), false, false);
/*      */       }
/*   96 */       catch (Exception ex) {
/*   97 */         if (frame == null) {
/*   98 */           String s = null;
/*      */           
/*  100 */           if (ex.getMessage() == null || ex
/*  101 */             .getMessage().isEmpty()) {
/*  102 */             s = ex.toString();
/*      */           } else {
/*  104 */             s = ex.getMessage();
/*      */           } 
/*      */           
/*  107 */           if (s == null) {
/*  108 */             s = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_UNEXPECTED");
/*      */           }
/*      */           
/*  111 */           s = s + GDConstants.LINE_SEPARATOR + GDLog.getStackTrace(ex);
/*      */           
/*  113 */           JOptionPane.showMessageDialog(null, s, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERROR"), 0);
/*      */           
/*  115 */           System.exit(1);
/*      */         } else {
/*  117 */           GDMsgLogger.addError(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_EXCEPTION"));
/*  118 */           GDMsgLogger.addError(ex);
/*      */           
/*  120 */           String s = GDLog.getStackTrace(ex);
/*      */           
/*  122 */           GDMsgLogger.addError(s);
/*      */           
/*  124 */           GDMsgLogger.showLog(frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCCESS"), GDLog.MessageType.Error, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERROR"));
/*      */           
/*  126 */           System.exit(1);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Starter() {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class GDCloseWindowListener
/*      */     extends WindowAdapter
/*      */   {
/*      */     private GDCloseWindowListener() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void windowClosing(WindowEvent e) {
/*  152 */       if (GDStashFrame.iniConfig.sectUI.minimizeToTray && GDStashFrame.this.tray != null) {
/*      */         
/*  154 */         GDStashFrame.this.setDefaultCloseOperation(1);
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  159 */       boolean close = GDStashFrame.this.closeFrame();
/*      */       
/*  161 */       if (close) {
/*  162 */         GDStashFrame frame = (GDStashFrame)e.getSource();
/*      */         
/*  164 */         frame.setDefaultCloseOperation(3);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class GDWindowListener implements WindowStateListener { private GDWindowListener() {}
/*      */     
/*      */     public void windowStateChanged(WindowEvent e) {
/*  172 */       if (e.getNewState() == 0) {
/*      */ 
/*      */         
/*  175 */         Window w = e.getWindow();
/*  176 */         w.setLocation(GDStashFrame.iniConfig.sectWindow.x, GDStashFrame.iniConfig.sectWindow.y);
/*  177 */         w.setSize(GDStashFrame.iniConfig.sectWindow.w, GDStashFrame.iniConfig.sectWindow.h);
/*      */       } 
/*      */     } }
/*      */   
/*      */   private class GDResizeListener extends ComponentAdapter {
/*      */     private GDResizeListener() {}
/*      */     
/*      */     public void componentResized(ComponentEvent e) {
/*  185 */       JFrame frame = (JFrame)e.getComponent();
/*      */       
/*  187 */       boolean maximized = ((frame.getExtendedState() & 0x6) == 6);
/*      */       
/*  189 */       if (!maximized) {
/*      */ 
/*      */ 
/*      */         
/*  193 */         GDStashFrame.iniConfig.sectWindow.x = frame.getX();
/*  194 */         GDStashFrame.iniConfig.sectWindow.y = frame.getY();
/*  195 */         GDStashFrame.iniConfig.sectWindow.w = frame.getWidth();
/*  196 */         GDStashFrame.iniConfig.sectWindow.h = frame.getHeight();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*  201 */   public static DBConfig dbConfig = null;
/*  202 */   public static IniConfig iniConfig = null;
/*  203 */   public static DBEngineGame engineGame = null;
/*  204 */   public static DBEnginePlayer enginePlayer = null;
/*  205 */   public static DBEngineLevel engineLevel = null; public static ARZDecompress arz; public static ARCList arcList; public static boolean expansionAshesOfMalmouth = false; public static boolean expansionForgottenGods = false; private static DefaultComboBoxModel<DBAffix> dmCompletion;
/*      */   private static DefaultComboBoxModel<DBAffix> dmModifier;
/*      */   private static DefaultComboBoxModel<DBAffix> dmModifierCelestial;
/*      */   public static GDSplashScreen splash;
/*      */   public GDTabbedPane pnlTabbed;
/*      */   public GDTransferPane pnlTransfer;
/*      */   public GDCharInventoryPane pnlCharInventory;
/*      */   public GDCraftPane pnlCraft;
/*      */   public GDCharEditPane pnlCharEdit;
/*      */   public GDMasteryInfoPane pnlMasteryInfo;
/*      */   public GDCollectionTabbedPane pnlCollection;
/*      */   public GDMassImportPane pnlMassImport;
/*      */   public GDConfigPane pnlConfig;
/*      */   public SystemTray tray;
/*      */   private PopupMenu trayMenu;
/*      */   public TrayIcon trayIcon;
/*      */   
/*      */   static {
/*      */     try {
/*  224 */       setGDLocale("English", (GDStashFrame)null);
/*  225 */       GDMsgFormatter.readHTMLResourceBundle();
/*      */     }
/*  227 */     catch (IOException ex) {
/*  228 */       System.out.println(ex.getMessage());
/*      */       
/*  230 */       GDMsgLogger.printStackTrace(ex);
/*      */       
/*  232 */       System.exit(1);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  237 */     iniConfig = IniConfig.get();
/*      */     
/*  239 */     if (iniConfig.sectDir.gdPath != null && 
/*  240 */       !iniConfig.sectDir.gdPath.isEmpty()) {
/*  241 */       String s = iniConfig.sectDir.gdPath + GDConstants.FILE_SEPARATOR + "gdx1" + GDConstants.FILE_SEPARATOR + "database" + GDConstants.FILE_SEPARATOR + "GDX1.arz";
/*  242 */       File fGDX1 = new File(s);
/*      */       
/*  244 */       expansionAshesOfMalmouth = fGDX1.exists();
/*      */       
/*  246 */       s = iniConfig.sectDir.gdPath + GDConstants.FILE_SEPARATOR + "gdx2" + GDConstants.FILE_SEPARATOR + "database" + GDConstants.FILE_SEPARATOR + "GDX2.arz";
/*  247 */       File fGDX2 = new File(s);
/*      */       
/*  249 */       expansionForgottenGods = fGDX2.exists();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  256 */     GDImagePool.loadLogos();
/*  257 */     GDImagePool.loadImages();
/*      */     
/*      */     try {
/*  260 */       GDDBData.createConfigTables();
/*      */     }
/*  262 */     catch (SQLException ex) {
/*  263 */       System.out.println(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_INIT_DB"));
/*  264 */       System.out.println(ex.getMessage());
/*  265 */       while (ex.getNextException() != null) {
/*  266 */         ex = ex.getNextException();
/*  267 */         System.out.println(ex.getMessage());
/*      */       } 
/*      */       
/*  270 */       GDMsgLogger.printStackTrace(ex);
/*      */       
/*  272 */       System.exit(1);
/*      */     } 
/*      */     
/*  275 */     readDBConfig((Frame)null);
/*      */     
/*      */     try {
/*  278 */       setGDLocale(iniConfig.sectUI.language, (GDStashFrame)null);
/*      */     }
/*  280 */     catch (IOException ex) {
/*  281 */       System.out.println(ex.getMessage());
/*      */       
/*  283 */       GDMsgLogger.printStackTrace(ex);
/*      */       
/*  285 */       System.exit(1);
/*      */     } 
/*      */     
/*  288 */     GDLog logStash = new GDLog();
/*  289 */     GDStashInfoList.findStashes(null, null, logStash);
/*      */     
/*  291 */     GDCharInfoList.findChars(null, null);
/*      */     
/*  293 */     if (dbConfig.configInit && dbConfig.gddbInit) {
/*      */       
/*  295 */       splash = new GDSplashScreen();
/*      */     } else {
/*      */       
/*  298 */       splash = null;
/*      */ 
/*      */       
/*  301 */       logStash.clear();
/*      */     } 
/*      */     
/*  304 */     GDImagePool.loadTiles();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  353 */       Class.forName("com.jtattoo.plaf.acryl.AcrylLookAndFeel", false, ClassLoader.getSystemClassLoader());
/*  354 */       UIManager.installLookAndFeel("JTattoo - Acryl", "com.jtattoo.plaf.acryl.AcrylLookAndFeel");
/*      */     }
/*  356 */     catch (ClassNotFoundException classNotFoundException) {}
/*      */     
/*      */     try {
/*  359 */       Class.forName("com.jtattoo.plaf.aero.AeroLookAndFeel", false, ClassLoader.getSystemClassLoader());
/*  360 */       UIManager.installLookAndFeel("JTattoo - Aero", "com.jtattoo.plaf.aero.AeroLookAndFeel");
/*      */     }
/*  362 */     catch (ClassNotFoundException classNotFoundException) {}
/*      */     
/*      */     try {
/*  365 */       Class.forName("com.jtattoo.plaf.aluminum.AluminumLookAndFeel", false, ClassLoader.getSystemClassLoader());
/*  366 */       UIManager.installLookAndFeel("JTattoo - Aluminum", "com.jtattoo.plaf.aluminum.AluminumLookAndFeel");
/*      */     }
/*  368 */     catch (ClassNotFoundException classNotFoundException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  377 */       Class.forName("com.jtattoo.plaf.fast.FastLookAndFeel", false, ClassLoader.getSystemClassLoader());
/*  378 */       UIManager.installLookAndFeel("JTattoo - Fast", "com.jtattoo.plaf.fast.FastLookAndFeel");
/*      */     }
/*  380 */     catch (ClassNotFoundException classNotFoundException) {}
/*      */     
/*      */     try {
/*  383 */       Class.forName("com.jtattoo.plaf.graphite.GraphiteLookAndFeel", false, ClassLoader.getSystemClassLoader());
/*  384 */       UIManager.installLookAndFeel("JTattoo - Graphite", "com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
/*      */     }
/*  386 */     catch (ClassNotFoundException classNotFoundException) {}
/*      */     
/*      */     try {
/*  389 */       Class.forName("com.jtattoo.plaf.luna.LunaLookAndFeel", false, ClassLoader.getSystemClassLoader());
/*  390 */       UIManager.installLookAndFeel("JTattoo - Luna", "com.jtattoo.plaf.luna.LunaLookAndFeel");
/*      */     }
/*  392 */     catch (ClassNotFoundException classNotFoundException) {}
/*      */     
/*      */     try {
/*  395 */       Class.forName("com.jtattoo.plaf.mcwin.McWinLookAndFeel", false, ClassLoader.getSystemClassLoader());
/*  396 */       UIManager.installLookAndFeel("JTattoo - McWin", "com.jtattoo.plaf.mcwin.McWinLookAndFeel");
/*      */     }
/*  398 */     catch (ClassNotFoundException classNotFoundException) {}
/*      */     
/*      */     try {
/*  401 */       Class.forName("com.jtattoo.plaf.mint.MintLookAndFeel", false, ClassLoader.getSystemClassLoader());
/*  402 */       UIManager.installLookAndFeel("JTattoo - Mint", "com.jtattoo.plaf.mint.MintLookAndFeel");
/*      */     }
/*  404 */     catch (ClassNotFoundException classNotFoundException) {}
/*      */     
/*      */     try {
/*  407 */       Class.forName("com.jtattoo.plaf.smart.SmartLookAndFeel", false, ClassLoader.getSystemClassLoader());
/*  408 */       UIManager.installLookAndFeel("JTattoo - Smart", "com.jtattoo.plaf.smart.SmartLookAndFeel");
/*      */     }
/*  410 */     catch (ClassNotFoundException classNotFoundException) {}
/*      */     
/*      */     try {
/*  413 */       Class.forName("com.jtattoo.plaf.hifi.HiFiLookAndFeel", false, ClassLoader.getSystemClassLoader());
/*  414 */       UIManager.installLookAndFeel("JTattoo - HiFi (dark)", "com.jtattoo.plaf.hifi.HiFiLookAndFeel");
/*      */     }
/*  416 */     catch (ClassNotFoundException classNotFoundException) {}
/*      */     
/*      */     try {
/*  419 */       Class.forName("com.jtattoo.plaf.noire.NoireLookAndFeel", false, ClassLoader.getSystemClassLoader());
/*  420 */       UIManager.installLookAndFeel("JTattoo - Noire (dark)", "com.jtattoo.plaf.noire.NoireLookAndFeel");
/*      */     }
/*  422 */     catch (ClassNotFoundException classNotFoundException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  438 */       Class.forName("de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel", false, ClassLoader.getSystemClassLoader());
/*  439 */       UIManager.installLookAndFeel("Synthetica", "de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel");
/*      */     }
/*  441 */     catch (ClassNotFoundException classNotFoundException) {}
/*      */     
/*  443 */     initCraftingAffixes();
/*      */ 
/*      */     
/*  446 */     GDMsgLogger.clear();
/*      */     
/*  448 */     if (logStash.containsMessages()) {
/*  449 */       logStash.addWarning(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_STASH_ITEMS_REMOVED"));
/*  450 */       GDMsgLogger.addLog(logStash);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean isGrimDawnRunning() {
/*  455 */     boolean found = false;
/*      */     
/*      */     try {
/*  458 */       Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\tasklist.exe");
/*      */       
/*  460 */       try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
/*  461 */         String line = input.readLine();
/*      */         
/*  463 */         while (line != null) {
/*  464 */           if (line.toUpperCase(GDConstants.LOCALE_US).contains("GRIM DAWN.EXE")) {
/*  465 */             found = true;
/*      */             
/*      */             break;
/*      */           } 
/*      */           
/*  470 */           line = input.readLine();
/*      */         }
/*      */       
/*      */       } 
/*  474 */     } catch (IOException iOException) {}
/*      */     
/*  476 */     return found;
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
/*      */   public static void initCraftingAffixes() {
/*  502 */     if (iniConfig == null)
/*  503 */       return;  if (iniConfig.sectHistory == null)
/*      */       return; 
/*  505 */     if (GDDBData.doesExist(iniConfig.sectHistory.lastMod)) {
/*  506 */       boolean exists = false;
/*      */       try {
/*  508 */         if (GDDBUtil.tableExists("GD_AFFIXSET")) {
/*  509 */           exists = true;
/*      */         }
/*      */       }
/*  512 */       catch (SQLException ex) {
/*  513 */         exists = false;
/*      */       } 
/*      */       
/*  516 */       if (exists) {
/*  517 */         List<DBAffixSet> craftSets = DBAffixSet.getCraftingAffixes();
/*  518 */         List<DBAffix> craftAffixes = GDItemCraftPane.extractAffixes(null, craftSets, 3);
/*  519 */         if (craftAffixes == null) craftAffixes = new LinkedList<>(); 
/*  520 */         Collections.sort(craftAffixes, (Comparator<? super DBAffix>)new DBAffix.AffixComparator());
/*      */         
/*  522 */         List<DBAffixSet> compSets = DBAffixSet.getCompletionAffixes();
/*  523 */         List<DBAffix> compAffixes = GDItemCraftPane.extractAffixes(null, compSets, 4);
/*  524 */         if (compAffixes == null) compAffixes = new LinkedList<>(); 
/*  525 */         Collections.sort(compAffixes, (Comparator<? super DBAffix>)new DBAffix.AffixComparator());
/*      */         
/*  527 */         dmCompletion = new DefaultComboBoxModel<>();
/*  528 */         dmModifier = new DefaultComboBoxModel<>();
/*  529 */         dmModifierCelestial = new DefaultComboBoxModel<>();
/*      */         
/*  531 */         dmCompletion.addElement(null);
/*  532 */         dmModifier.addElement(null);
/*  533 */         dmModifierCelestial.addElement(null);
/*      */         
/*  535 */         for (DBAffix affix : compAffixes) {
/*  536 */           if (affix == null)
/*      */             continue; 
/*  538 */           if (affix.getAffixType() == 4) {
/*  539 */             dmCompletion.addElement(affix);
/*      */           }
/*      */         } 
/*      */         
/*  543 */         for (DBAffix affix : craftAffixes) {
/*  544 */           if (affix == null)
/*      */             continue; 
/*  546 */           if (affix.getAffixType() == 3) {
/*  547 */             dmModifier.addElement(affix);
/*      */ 
/*      */             
/*  550 */             if (affix.getAffixID().equals("records/items/lootaffixes/crafting/ac02_healthregen.dbr") || affix
/*  551 */               .getAffixID().equals("records/items/lootaffixes/crafting/ad08_da.dbr") || affix
/*  552 */               .getAffixID().equals("records/items/lootaffixes/crafting/ao14_oa.dbr")) {
/*  553 */               dmModifierCelestial.addElement(affix);
/*      */             }
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
/*      */   public static void readDBConfig(Frame frame) {
/*  566 */     boolean exists = false;
/*      */     try {
/*  568 */       exists = GDDBUtil.tableExists("INFO_CONFIG");
/*      */     }
/*  570 */     catch (SQLException ex) {
/*  571 */       exists = false;
/*      */       
/*  573 */       GDMsgLogger.clear();
/*      */     } 
/*      */     
/*  576 */     if (!exists) {
/*      */       try {
/*  578 */         GDDBData.createConfigTables();
/*      */       }
/*  580 */       catch (SQLException ex) {
/*  581 */         GDMsgLogger.addError(ex);
/*  582 */         while (ex.getNextException() != null) {
/*  583 */           ex = ex.getNextException();
/*  584 */           GDMsgLogger.addError(ex);
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/*      */     }
/*      */     
/*  591 */     DBConfig.reset();
/*  592 */     DBEngineGame.reset();
/*  593 */     DBEnginePlayer.reset();
/*  594 */     DBEngineLevel.reset();
/*  595 */     DBEngineSkillMaster.reset();
/*      */     
/*  597 */     DBConfig config = DBConfig.get();
/*      */     
/*  599 */     if (config == null) {
/*  600 */       dbConfig = new DBConfig();
/*      */     } else {
/*  602 */       dbConfig = config;
/*      */       
/*  604 */       if (!"1.0.8".equals(config.configVer)) {
/*  605 */         dbConfig.configInit = false;
/*  606 */         dbConfig.configVer = "1.0.8";
/*      */       } 
/*      */       
/*  609 */       if (!"1.7.4".equals(config.gddbVer)) {
/*  610 */         dbConfig.gddbInit = false;
/*  611 */         dbConfig.gddbVer = "1.7.4";
/*      */       } 
/*      */     } 
/*      */     
/*  615 */     if (!dbConfig.configInit && !dbConfig.gddbInit) {
/*      */       
/*  617 */       GDLogoDialog dialog = new GDLogoDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_CONFIG_IMPORT"), 2, frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFORMATION"), true);
/*  618 */       dialog.setVisible(true);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  623 */       if (!dbConfig.configInit) {
/*      */         
/*  625 */         GDLogoDialog dialog = new GDLogoDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_CONFIGURE_PROG"), 2, frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFORMATION"), true);
/*  626 */         dialog.setVisible(true);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  632 */       if (!dbConfig.gddbInit) {
/*      */         
/*  634 */         GDLogoDialog dialog = new GDLogoDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_IMPORT_DB"), 2, frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFORMATION"), true);
/*  635 */         dialog.setVisible(true);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  642 */     if (!dbConfig.gddbInit)
/*      */       return; 
/*  644 */     DBEngineGame game = DBEngineGame.get();
/*  645 */     if (game == null) {
/*  646 */       engineGame = new DBEngineGame();
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/*  654 */       engineGame = game;
/*      */     } 
/*      */     
/*  657 */     DBEnginePlayer player = DBEnginePlayer.get();
/*  658 */     if (player == null) {
/*  659 */       enginePlayer = new DBEnginePlayer();
/*      */     } else {
/*  661 */       enginePlayer = player;
/*      */     } 
/*      */     
/*  664 */     DBEngineLevel level = DBEngineLevel.get();
/*  665 */     if (level == null) {
/*  666 */       engineLevel = new DBEngineLevel();
/*      */     } else {
/*  668 */       engineLevel = level;
/*      */     } 
/*      */     
/*  671 */     GDMasteryInfoPane.initStats();
/*  672 */     GDCharMasteryPane.loadMasteries();
/*  673 */     GDCharMasteryImagePane.initStats();
/*      */   }
/*      */   
/*      */   private static DefaultComboBoxModel<DBAffix> cloneComboBoxModel(DefaultComboBoxModel<DBAffix> model) {
/*  677 */     DefaultComboBoxModel<DBAffix> clone = new DefaultComboBoxModel<>();
/*      */     
/*  679 */     for (int i = 0; i < model.getSize(); i++) {
/*  680 */       DBAffix affix = model.getElementAt(i);
/*      */       
/*  682 */       clone.addElement(affix);
/*      */     } 
/*      */     
/*  685 */     return clone;
/*      */   }
/*      */   
/*      */   public static DefaultComboBoxModel<DBAffix> getCompletion() {
/*  689 */     return cloneComboBoxModel(dmCompletion);
/*      */   }
/*      */   
/*      */   public static DefaultComboBoxModel<DBAffix> getModifier() {
/*  693 */     return cloneComboBoxModel(dmModifier);
/*      */   }
/*      */   
/*      */   public static DefaultComboBoxModel<DBAffix> getCelestialModifier() {
/*  697 */     return cloneComboBoxModel(dmModifierCelestial);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setGDLocale(String language, GDStashFrame frame) throws IOException {
/*  705 */     GDMsgFormatter.setGDLocale(language);
/*      */     
/*  707 */     adjustUI(frame);
/*      */   }
/*      */   
/*      */   private static void changeFontRecursive(Container root, int fontSize) {
/*  711 */     for (Component c : root.getComponents()) {
/*  712 */       Font font = c.getFont();
/*      */       
/*  714 */       font = font.deriveFont(fontSize);
/*      */       
/*  716 */       c.setFont(font);
/*  717 */       if (c instanceof Container) {
/*  718 */         changeFontRecursive((Container)c, fontSize);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void adjustUI(GDStashFrame frame) {
/*  724 */     if (frame != null) frame.adjustUI(); 
/*      */   }
/*      */   
/*      */   public static void setMnemonic(AbstractButton ab, String tag) {
/*  728 */     String s = GDMsgFormatter.getString(GDMsgFormatter.rbUI, tag);
/*      */     
/*  730 */     if (s == null)
/*  731 */       return;  if (s.isEmpty())
/*      */       return; 
/*  733 */     char c = s.charAt(0);
/*      */     
/*  735 */     switch (c) {
/*      */       case 'A':
/*  737 */         ab.setMnemonic(65);
/*      */         break;
/*      */       
/*      */       case 'B':
/*  741 */         ab.setMnemonic(66);
/*      */         break;
/*      */       
/*      */       case 'C':
/*  745 */         ab.setMnemonic(67);
/*      */         break;
/*      */       
/*      */       case 'D':
/*  749 */         ab.setMnemonic(68);
/*      */         break;
/*      */       
/*      */       case 'E':
/*  753 */         ab.setMnemonic(69);
/*      */         break;
/*      */       
/*      */       case 'F':
/*  757 */         ab.setMnemonic(70);
/*      */         break;
/*      */       
/*      */       case 'G':
/*  761 */         ab.setMnemonic(71);
/*      */         break;
/*      */       
/*      */       case 'H':
/*  765 */         ab.setMnemonic(72);
/*      */         break;
/*      */       
/*      */       case 'I':
/*  769 */         ab.setMnemonic(73);
/*      */         break;
/*      */       
/*      */       case 'J':
/*  773 */         ab.setMnemonic(74);
/*      */         break;
/*      */       
/*      */       case 'K':
/*  777 */         ab.setMnemonic(75);
/*      */         break;
/*      */       
/*      */       case 'L':
/*  781 */         ab.setMnemonic(76);
/*      */         break;
/*      */       
/*      */       case 'M':
/*  785 */         ab.setMnemonic(77);
/*      */         break;
/*      */       
/*      */       case 'N':
/*  789 */         ab.setMnemonic(78);
/*      */         break;
/*      */       
/*      */       case 'O':
/*  793 */         ab.setMnemonic(79);
/*      */         break;
/*      */       
/*      */       case 'P':
/*  797 */         ab.setMnemonic(80);
/*      */         break;
/*      */       
/*      */       case 'Q':
/*  801 */         ab.setMnemonic(81);
/*      */         break;
/*      */       
/*      */       case 'R':
/*  805 */         ab.setMnemonic(82);
/*      */         break;
/*      */       
/*      */       case 'S':
/*  809 */         ab.setMnemonic(83);
/*      */         break;
/*      */       
/*      */       case 'T':
/*  813 */         ab.setMnemonic(84);
/*      */         break;
/*      */       
/*      */       case 'U':
/*  817 */         ab.setMnemonic(85);
/*      */         break;
/*      */       
/*      */       case 'V':
/*  821 */         ab.setMnemonic(86);
/*      */         break;
/*      */       
/*      */       case 'W':
/*  825 */         ab.setMnemonic(87);
/*      */         break;
/*      */       
/*      */       case 'X':
/*  829 */         ab.setMnemonic(88);
/*      */         break;
/*      */       
/*      */       case 'Y':
/*  833 */         ab.setMnemonic(89);
/*      */         break;
/*      */       
/*      */       case 'Z':
/*  837 */         ab.setMnemonic(90);
/*      */         break;
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
/*      */   public static void messageToList(String str) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void listToLog() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setApplicationFont(float size) {
/*  880 */     Enumeration enumer = UIManager.getDefaults().keys();
/*  881 */     while (enumer.hasMoreElements()) {
/*      */       
/*  883 */       Object key = enumer.nextElement();
/*  884 */       Object value = UIManager.get(key);
/*  885 */       if (value instanceof Font) {
/*  886 */         Font font = (Font)value;
/*  887 */         font = font.deriveFont(size);
/*  888 */         UIManager.put(key, font);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void renameCharDir(File fDirOld, File fDirNew, GDStashFrame frame) {
/*  894 */     if (GDCharInfoList.gdCharFileInfos == null)
/*      */       return; 
/*  896 */     for (GDCharInfoList.GDCharFileInfo info : GDCharInfoList.gdCharFileInfos) {
/*  897 */       if (info.charFile != null) {
/*  898 */         File fDir = info.charFile.getParentFile();
/*      */         
/*  900 */         if (fDir == null)
/*      */           continue; 
/*  902 */         if (fDir.equals(fDirOld)) {
/*      */           try {
/*  904 */             String name = info.charFile.getName();
/*      */             
/*  906 */             String s = fDirNew.getCanonicalPath() + GDConstants.FILE_SEPARATOR + name;
/*  907 */             File f = new File(s);
/*      */             
/*  909 */             info.charFile = f;
/*      */             
/*  911 */             if (info.gdChar != null) {
/*  912 */               info.gdChar.setFileDir(fDirNew);
/*      */             }
/*      */             
/*  915 */             GDCharInfoList.adjustCharInfo(info);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*  923 */           catch (IOException ex) {
/*  924 */             GDMsgLogger.addError(ex);
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
/*      */   public GDStashFrame() {
/*  949 */     super("Grim Dawn Stash v1.7.4 - " + System.getProperty("java.vm.name") + " " + System.getProperty("java.version"));
/*      */     
/*  951 */     this.pnlTransfer = new GDTransferPane(GDStashInfoList.defaultStash, this);
/*  952 */     this.pnlCharInventory = new GDCharInventoryPane(this);
/*  953 */     this.pnlCraft = new GDCraftPane(GDStashInfoList.defaultStash, this);
/*  954 */     this.pnlCharEdit = new GDCharEditPane(this);
/*  955 */     this.pnlMasteryInfo = new GDMasteryInfoPane(this);
/*  956 */     this.pnlCollection = new GDCollectionTabbedPane(this);
/*  957 */     this.pnlMassImport = new GDMassImportPane(this);
/*  958 */     this.pnlConfig = new GDConfigPane(this);
/*      */     
/*  960 */     this.pnlTabbed = new GDTabbedPane(this);
/*      */     
/*  962 */     setDefaultCloseOperation(0);
/*      */     
/*  964 */     if (SystemTray.isSupported()) {
/*  965 */       this.tray = SystemTray.getSystemTray();
/*  966 */       this.trayMenu = new PopupMenu();
/*      */       
/*  968 */       MenuItem itemShow = new MenuItem(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "MENU_SHOW"));
/*  969 */       MenuItem itemExit = new MenuItem(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "MENU_EXIT"));
/*      */       
/*  971 */       this.trayMenu.add(itemShow);
/*  972 */       itemShow.addActionListener(new ActionListener() {
/*      */             public void actionPerformed(ActionEvent e) {
/*  974 */               GDStashFrame.this.setVisible(true);
/*      */             }
/*      */           });
/*      */       
/*  978 */       this.trayMenu.add(itemExit);
/*  979 */       itemExit.addActionListener(new ActionListener() {
/*      */             public void actionPerformed(ActionEvent e) {
/*  981 */               boolean close = GDStashFrame.this.closeFrame();
/*      */               
/*  983 */               if (close) System.exit(0);
/*      */             
/*      */             }
/*      */           });
/*  987 */       Image image = Toolkit.getDefaultToolkit().getImage("image" + GDConstants.FILE_SEPARATOR + "GDS_TrayIcon.png");
/*  988 */       this.trayIcon = new TrayIcon(image, "GD Stash", this.trayMenu);
/*      */       
/*  990 */       if (iniConfig.sectUI.minimizeToTray) {
/*      */         try {
/*  992 */           this.tray.add(this.trayIcon);
/*      */         }
/*  994 */         catch (Exception ex) {
/*  995 */           this.tray = null;
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1000 */     addWindowListener(new GDCloseWindowListener());
/* 1001 */     addWindowStateListener(new GDWindowListener());
/* 1002 */     addComponentListener(new GDResizeListener());
/*      */     
/* 1004 */     if (splash != null) {
/* 1005 */       splash.setVisible(false);
/* 1006 */       splash.dispose();
/* 1007 */       splash = null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1039 */     setIconImage(GDImagePool.iconLogo64x64.getImage());
/*      */     
/* 1041 */     add((Component)this.pnlTabbed);
/*      */   }
/*      */   
/*      */   private void adjustUI() {
/* 1045 */     if (this.pnlTransfer != null) this.pnlTransfer.adjustUI(); 
/* 1046 */     if (this.pnlCharInventory != null) this.pnlCharInventory.adjustUI(); 
/* 1047 */     if (this.pnlCraft != null) this.pnlCraft.adjustUI(); 
/* 1048 */     if (this.pnlCharEdit != null) this.pnlCharEdit.adjustUI(); 
/* 1049 */     if (this.pnlMasteryInfo != null) this.pnlMasteryInfo.adjustUI(); 
/* 1050 */     if (this.pnlMassImport != null) this.pnlMassImport.adjustUI(); 
/* 1051 */     if (this.pnlCollection != null) this.pnlCollection.adjustUI(); 
/* 1052 */     if (this.pnlConfig != null) this.pnlConfig.adjustUI();
/*      */     
/* 1054 */     if (this.pnlTabbed != null) this.pnlTabbed.adjustUI(); 
/*      */   }
/*      */   
/*      */   public void checkTabs() {
/* 1058 */     if (this.pnlTabbed != null) this.pnlTabbed.checkTabs(); 
/*      */   }
/*      */   
/*      */   public void refresh() {
/* 1062 */     GDDBData.clearBuffers();
/*      */ 
/*      */     
/* 1065 */     GDCharInfoList.updateCharSummary();
/*      */     
/* 1067 */     if (this.pnlTransfer != null) this.pnlTransfer.refresh(); 
/* 1068 */     if (this.pnlCharInventory != null) this.pnlCharInventory.refresh(); 
/* 1069 */     if (this.pnlCraft != null) this.pnlCraft.refresh(); 
/* 1070 */     if (this.pnlCharEdit != null) this.pnlCharEdit.refresh(); 
/*      */   }
/*      */   
/*      */   public boolean closeFrame() {
/* 1074 */     boolean stashChanges = false;
/* 1075 */     boolean charChanges = false;
/*      */     
/* 1077 */     if (GDStashInfoList.gdStashFileInfos != null) {
/* 1078 */       for (GDStashInfoList.GDStashFileInfo info : GDStashInfoList.gdStashFileInfos) {
/* 1079 */         if (info.gdStash != null && info.gdStash.hasChanged()) {
/* 1080 */           stashChanges = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1087 */     if (GDCharInfoList.gdCharFileInfos != null) {
/* 1088 */       for (GDCharInfoList.GDCharFileInfo info : GDCharInfoList.gdCharFileInfos) {
/* 1089 */         if (info.gdChar != null && info.gdChar.hasChanged()) {
/* 1090 */           charChanges = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1097 */     if (stashChanges || charChanges) {
/* 1098 */       int i = 0;
/* 1099 */       JButton[] buttons = new JButton[2];
/*      */       
/* 1101 */       buttons[0] = new JButton(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_SAVE"), GDImagePool.iconBtnSave24);
/* 1102 */       buttons[1] = new JButton(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_QUIT"), GDImagePool.iconBtnCancel24);
/*      */       
/* 1104 */       String tag = null;
/*      */       
/* 1106 */       if (stashChanges && charChanges) {
/* 1107 */         tag = "TXT_SAVE_CHANGES_ALL";
/*      */       }
/* 1109 */       else if (stashChanges) {
/* 1110 */         tag = "TXT_SAVE_CHANGES_STASH";
/*      */       } else {
/* 1112 */         tag = "TXT_SAVE_CHANGES_CHAR";
/*      */       } 
/*      */ 
/*      */       
/* 1116 */       i = GDFlexDialog.showDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, tag), buttons, 2, (Frame)null, true);
/*      */       
/* 1118 */       if (i == -1) return false;
/*      */       
/* 1120 */       if (i == 0) {
/*      */         
/* 1122 */         if (isCloudSaveDir() && 
/* 1123 */           isGrimDawnRunning()) {
/* 1124 */           GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_CLOUD_SAVE"));
/* 1125 */           GDMsgLogger.addInfo(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_CLOSE"));
/*      */           
/* 1127 */           GDMsgLogger.showLog(this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_RUNNING"), GDLog.MessageType.Info, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "INFO_GD_RUNNING"));
/*      */           
/* 1129 */           return false;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1134 */         boolean success = true;
/*      */         
/* 1136 */         if (stashChanges && 
/* 1137 */           GDStashInfoList.gdStashFileInfos != null) {
/* 1138 */           for (GDStashInfoList.GDStashFileInfo info : GDStashInfoList.gdStashFileInfos) {
/* 1139 */             if (info.gdStash != null && info.gdStash.hasChanged()) {
/*      */               try {
/* 1141 */                 info.gdStash.write();
/*      */               }
/* 1143 */               catch (Exception ex) {
/* 1144 */                 GDMsgLogger.addError(ex);
/* 1145 */                 success = false;
/*      */               } 
/*      */             }
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/* 1152 */         if (charChanges && 
/* 1153 */           GDCharInfoList.gdCharFileInfos != null) {
/* 1154 */           for (GDCharInfoList.GDCharFileInfo info : GDCharInfoList.gdCharFileInfos) {
/* 1155 */             if (info.gdChar != null && info.gdChar.hasChanged()) {
/*      */               try {
/* 1157 */                 info.gdChar.write();
/*      */               }
/* 1159 */               catch (Exception ex) {
/* 1160 */                 GDMsgLogger.addError(ex);
/* 1161 */                 success = false;
/*      */               } 
/*      */             }
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/* 1168 */         if (!success) {
/* 1169 */           GDMsgLogger.showLog(this, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "SUCCESS"), GDLog.MessageType.Error, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERROR"));
/* 1170 */           return false;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1175 */     iniConfig.sectWindow.maximized = ((getExtendedState() & 0x6) == 6);
/* 1176 */     if (!iniConfig.sectWindow.maximized) {
/*      */ 
/*      */ 
/*      */       
/* 1180 */       iniConfig.sectWindow.x = getX();
/* 1181 */       iniConfig.sectWindow.y = getY();
/* 1182 */       iniConfig.sectWindow.w = getWidth();
/* 1183 */       iniConfig.sectWindow.h = getHeight();
/*      */     } 
/*      */     
/* 1186 */     iniConfig.writeWindowSection();
/* 1187 */     GDDBData.closeConnections();
/*      */     
/* 1189 */     return true;
/*      */   }
/*      */   
/*      */   public String getSaveDir() {
/* 1193 */     if (this.pnlConfig == null) return null;
/*      */     
/* 1195 */     return this.pnlConfig.getSaveDir();
/*      */   }
/*      */   
/*      */   public boolean isCloudSaveDir() {
/* 1199 */     String dir = getSaveDir();
/*      */     
/* 1201 */     if (dir == null) return false;
/*      */     
/* 1203 */     return dir.contains("219990");
/*      */   }
/*      */   
/*      */   public String getSelectedMod() {
/* 1207 */     if (this.pnlConfig == null) return null;
/*      */     
/* 1209 */     return this.pnlConfig.getSelectedMod();
/*      */   }
/*      */   
/*      */   private static void test() {
/* 1213 */     Object[] args = { "Test1", Float.valueOf(21.3F), Integer.valueOf(3) };
/* 1214 */     System.out.println(GDMsgFormatter.format("{2,number,integer}% Chance of {1,number,integer}% {0}", args));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1219 */     System.exit(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void main(String[] args) {
/*      */     try {
/* 1231 */       UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
/* 1232 */       for (int i = 0; i < info.length; i++) {
/* 1233 */         if (info[i].getName().equals(iniConfig.sectUI.lookNFeel)) {
/* 1234 */           UIManager.setLookAndFeel(info[i].getClassName());
/*      */           
/* 1236 */           GDColor.setLookAndFeelUI(info[i].getClassName());
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 1242 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/* 1245 */     DBAffix.fillBuffer();
/*      */ 
/*      */ 
/*      */     
/* 1249 */     Starter starter = new Starter();
/* 1250 */     SwingUtilities.invokeLater(starter);
/*      */   }
/*      */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\GDStashFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */