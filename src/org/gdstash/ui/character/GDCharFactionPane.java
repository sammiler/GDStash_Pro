/*      */ package org.gdstash.ui.character;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Font;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.util.List;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.DefaultComboBoxModel;
/*      */ import javax.swing.GroupLayout;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.border.TitledBorder;
/*      */ import org.gdstash.character.GDChar;
/*      */ import org.gdstash.db.DBEngineGame;
/*      */ import org.gdstash.db.DBFactionReputation;
/*      */ import org.gdstash.ui.GDStashFrame;
/*      */ import org.gdstash.ui.util.AdjustablePanel;
/*      */ import org.gdstash.util.GDMsgFormatter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GDCharFactionPane
/*      */   extends AdjustablePanel
/*      */ {
/*      */   private static final int FACTION_NONE = -1;
/*      */   private static final int FACTION_DEVILS_CROSSING = 1;
/*      */   private static final int FACTION_AETHERIALS = 2;
/*      */   private static final int FACTION_CHTHONIANS = 3;
/*      */   private static final int FACTION_CRONLEYS_GANG = 4;
/*      */   private static final int FACTION_BEASTS = 5;
/*      */   private static final int FACTION_ROVERS = 6;
/*      */   private static final int FACTION_NEUTRAL = 7;
/*      */   private static final int FACTION_HOMESTEAD = 8;
/*      */   private static final int FACTION_CORRIGAN_MINE = 9;
/*      */   private static final int FACTION_THE_OUTCAST = 10;
/*      */   private static final int FACTION_DEATHS_VIGIL = 11;
/*      */   private static final int FACTION_UNDEAD = 12;
/*      */   private static final int FACTION_BLACK_LEGION = 13;
/*      */   private static final int FACTION_KYMONS_CHOSEN = 14;
/*      */   private static final int FACTION_COVEN_UGDENBOG = 15;
/*      */   private static final int FACTION_BARROWHOLM = 16;
/*      */   private static final int FACTION_MALMOUTH_RESISTANCE = 17;
/*      */   private static final int FACTION_AETHERIAL_VANGUARD = 18;
/*      */   private static final int FACTION_CULT_BYSMIEL = 19;
/*      */   private static final int FACTION_CULT_DREEG = 20;
/*      */   private static final int FACTION_CULT_SOLAEL = 21;
/*      */   private static final int FACTION_ELDRITCH_HORRORS = 22;
/*   59 */   public static final int[] FACTIONS_VANILLA = new int[] { 1, 6, 8, 13, 11, 14, 10, -1, -1, -1, -1, -1, -1, 12, -1, 2, 3, 4, -1, -1 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   65 */   public static final int[] FACTIONS_MALMOUTH = new int[] { 1, 6, 8, 13, 11, 14, 10, 15, 16, 17, -1, -1, -1, 12, 5, 2, 3, 4, 18, -1 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   71 */   public static final int[] FACTIONS_GODS = new int[] { 1, 6, 8, 13, 11, 14, 10, 15, 16, 17, 19, 20, 21, 12, 5, 2, 3, 4, 18, 22 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   78 */   public static int[] FACTIONS = null; private static final int TYPE_REPUTATION_ALL = 1; private static final int TYPE_REPUTATION_POSITIVE = 2; private static final int TYPE_REPUTATION_NEGATIVE = 3;
/*      */   
/*      */   static {
/*   81 */     if (GDStashFrame.expansionForgottenGods) {
/*   82 */       FACTIONS = FACTIONS_GODS;
/*      */     }
/*   84 */     else if (GDStashFrame.expansionAshesOfMalmouth) {
/*   85 */       FACTIONS = FACTIONS_MALMOUTH;
/*      */     } else {
/*   87 */       FACTIONS = FACTIONS_VANILLA;
/*      */     } 
/*      */   }
/*      */   
/*      */   private class ReputationListener implements ActionListener {
/*      */     private ReputationListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/*   95 */       if (GDCharFactionPane.this.skipListener)
/*      */         return; 
/*   97 */       JComboBox source = (JComboBox)e.getSource();
/*      */       
/*   99 */       GDCharFactionPane.this.fillReputation(source);
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
/*  159 */   private static int[] minReputationLevels = new int[10]; private static String[] factionNegative; private static String[] factionPositive; private JLabel lblSurvivors; private JLabel lblRovers; private JLabel lblHomestead; private JLabel lblBlackLegion; private JLabel lblDeathsVigil; private JLabel lblKymonsChosen; private JLabel lblOutcast; private JLabel lblCovenUgdenbog; private JLabel lblBarrowholm; private JLabel lblMalmouth; private JLabel lblCultBysmiel;
/*  160 */   private static String[] factionAll = new String[10]; private JLabel lblCultDreeg; private JLabel lblCultSolael; private JLabel lblUndead; private JLabel lblBeasts; private JLabel lblAetherials; private JLabel lblChthonians; private JLabel lblOutlaws; private JLabel lblAetherialVanguard; private JLabel lblEldritchHorrors; private JComboBox<String> cbSurvivors; private JComboBox<String> cbRovers; static {
/*  161 */     factionNegative = new String[5];
/*  162 */     factionPositive = new String[6];
/*      */     
/*  164 */     initReputationLevels();
/*  165 */     loadCBText();
/*      */   }
/*      */   private JComboBox<String> cbHomestead; private JComboBox<String> cbBlackLegion; private JComboBox<String> cbDeathsVigil; private JComboBox<String> cbKymonsChosen; private JComboBox<String> cbOutcast; private JComboBox<String> cbCovenUgdenbog;
/*      */   private JComboBox<String> cbBarrowholm;
/*      */   private JComboBox<String> cbMalmouth;
/*      */   private JComboBox<String> cbCultBysmiel;
/*      */   private JComboBox<String> cbCultDreeg;
/*      */   private JComboBox<String> cbCultSolael;
/*      */   private JComboBox<String> cbUndead;
/*      */   private JComboBox<String> cbBeasts;
/*      */   private JComboBox<String> cbAetherials;
/*      */   private JComboBox<String> cbChthonians;
/*      */   private JComboBox<String> cbOutlaws;
/*      */   private JComboBox<String> cbAetherialVanguard;
/*      */   private JComboBox<String> cbEldritchHorrors;
/*      */   private int[] reputations;
/*      */   private int[] reputationTypes;
/*      */   private JComboBox[] cbReputations;
/*      */   private boolean skipListener;
/*      */   
/*      */   private static void initReputationLevels() {
/*  186 */     boolean defaultRep = true;
/*  187 */     DBEngineGame game = DBEngineGame.get();
/*      */     
/*  189 */     if (game != null) {
/*  190 */       List<DBFactionReputation> values = game.getReputationList();
/*      */       
/*  192 */       if (values.size() >= 8) {
/*  193 */         defaultRep = false;
/*      */         
/*  195 */         minReputationLevels[0] = ((DBFactionReputation)values.get(0)).getValue();
/*  196 */         minReputationLevels[1] = ((DBFactionReputation)values.get(1)).getValue();
/*  197 */         minReputationLevels[2] = ((DBFactionReputation)values.get(2)).getValue();
/*  198 */         minReputationLevels[3] = -1;
/*  199 */         minReputationLevels[4] = 0;
/*  200 */         minReputationLevels[5] = 1;
/*  201 */         minReputationLevels[6] = ((DBFactionReputation)values.get(4)).getValue();
/*  202 */         minReputationLevels[7] = ((DBFactionReputation)values.get(5)).getValue();
/*  203 */         minReputationLevels[8] = ((DBFactionReputation)values.get(6)).getValue();
/*  204 */         minReputationLevels[9] = ((DBFactionReputation)values.get(7)).getValue();
/*      */       } 
/*      */     } 
/*      */     
/*  208 */     if (defaultRep) {
/*  209 */       minReputationLevels[0] = -20000;
/*  210 */       minReputationLevels[1] = -8000;
/*  211 */       minReputationLevels[2] = -1500;
/*  212 */       minReputationLevels[3] = -1;
/*  213 */       minReputationLevels[4] = 0;
/*  214 */       minReputationLevels[5] = 1;
/*  215 */       minReputationLevels[6] = 1500;
/*  216 */       minReputationLevels[7] = 5000;
/*  217 */       minReputationLevels[8] = 10000;
/*  218 */       minReputationLevels[9] = 25000;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void loadCBText() {
/*  223 */     factionAll[0] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_STATUS_NEMESIS");
/*  224 */     factionAll[1] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_STATUS_HATED");
/*  225 */     factionAll[2] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_STATUS_DESPISED");
/*  226 */     factionAll[3] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_STATUS_HOSTILE");
/*  227 */     factionAll[4] = "";
/*  228 */     factionAll[5] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_STATUS_TOLERATED");
/*  229 */     factionAll[6] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_STATUS_FRIENDLY");
/*  230 */     factionAll[7] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_STATUS_RESPECTED");
/*  231 */     factionAll[8] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_STATUS_HONORED");
/*  232 */     factionAll[9] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_STATUS_REVERED");
/*      */     
/*  234 */     factionNegative[0] = factionAll[4];
/*  235 */     factionNegative[1] = factionAll[3];
/*  236 */     factionNegative[2] = factionAll[2];
/*  237 */     factionNegative[3] = factionAll[1];
/*  238 */     factionNegative[4] = factionAll[0];
/*      */     
/*  240 */     factionPositive[0] = factionAll[4];
/*  241 */     factionPositive[1] = factionAll[5];
/*  242 */     factionPositive[2] = factionAll[6];
/*  243 */     factionPositive[3] = factionAll[7];
/*  244 */     factionPositive[4] = factionAll[8];
/*  245 */     factionPositive[5] = factionAll[9];
/*      */   }
/*      */   
/*      */   public GDCharFactionPane() {
/*  249 */     this.reputations = new int[FACTIONS.length];
/*      */     
/*  251 */     adjustUI();
/*      */     
/*  253 */     this.cbReputations = new JComboBox[FACTIONS.length];
/*      */     
/*  255 */     this.cbReputations[0] = this.cbSurvivors;
/*  256 */     this.cbReputations[1] = this.cbRovers;
/*  257 */     this.cbReputations[2] = this.cbHomestead;
/*  258 */     this.cbReputations[3] = this.cbBlackLegion;
/*  259 */     this.cbReputations[4] = this.cbDeathsVigil;
/*  260 */     this.cbReputations[5] = this.cbKymonsChosen;
/*  261 */     this.cbReputations[6] = this.cbOutcast;
/*  262 */     this.cbReputations[7] = this.cbCovenUgdenbog;
/*  263 */     this.cbReputations[8] = this.cbBarrowholm;
/*  264 */     this.cbReputations[9] = this.cbMalmouth;
/*  265 */     this.cbReputations[10] = this.cbCultBysmiel;
/*  266 */     this.cbReputations[11] = this.cbCultDreeg;
/*  267 */     this.cbReputations[12] = this.cbCultSolael;
/*  268 */     this.cbReputations[13] = this.cbUndead;
/*  269 */     this.cbReputations[14] = this.cbBeasts;
/*  270 */     this.cbReputations[15] = this.cbAetherials;
/*  271 */     this.cbReputations[16] = this.cbChthonians;
/*  272 */     this.cbReputations[17] = this.cbOutlaws;
/*  273 */     this.cbReputations[18] = this.cbAetherialVanguard;
/*  274 */     this.cbReputations[19] = this.cbEldritchHorrors;
/*      */     
/*  276 */     this.reputationTypes = new int[FACTIONS.length];
/*      */     
/*  278 */     this.reputationTypes[0] = 2;
/*  279 */     this.reputationTypes[1] = 2;
/*  280 */     this.reputationTypes[2] = 2;
/*  281 */     this.reputationTypes[3] = 2;
/*  282 */     this.reputationTypes[4] = 1;
/*  283 */     this.reputationTypes[5] = 1;
/*  284 */     this.reputationTypes[6] = 1;
/*  285 */     this.reputationTypes[7] = 2;
/*  286 */     this.reputationTypes[8] = 1;
/*  287 */     this.reputationTypes[9] = 2;
/*  288 */     this.reputationTypes[10] = 2;
/*  289 */     this.reputationTypes[11] = 2;
/*  290 */     this.reputationTypes[12] = 2;
/*  291 */     this.reputationTypes[13] = 3;
/*  292 */     this.reputationTypes[14] = 3;
/*  293 */     this.reputationTypes[15] = 3;
/*  294 */     this.reputationTypes[16] = 3;
/*  295 */     this.reputationTypes[17] = 3;
/*  296 */     this.reputationTypes[18] = 3;
/*  297 */     this.reputationTypes[19] = 3;
/*      */ 
/*      */ 
/*      */     
/*  301 */     GroupLayout layout = null;
/*  302 */     GroupLayout.SequentialGroup hGroup = null;
/*  303 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/*  305 */     layout = new GroupLayout((Container)this);
/*  306 */     setLayout(layout);
/*      */     
/*  308 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  311 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  314 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  317 */     hGroup
/*  318 */       .addGroup(layout.createParallelGroup()
/*  319 */         .addComponent(this.lblSurvivors)
/*  320 */         .addComponent(this.lblRovers)
/*  321 */         .addComponent(this.lblHomestead)
/*  322 */         .addComponent(this.lblBlackLegion)
/*  323 */         .addComponent(this.lblDeathsVigil)
/*  324 */         .addComponent(this.lblKymonsChosen)
/*  325 */         .addComponent(this.lblOutcast)
/*  326 */         .addComponent(this.lblCovenUgdenbog)
/*  327 */         .addComponent(this.lblBarrowholm)
/*  328 */         .addComponent(this.lblMalmouth)
/*  329 */         .addComponent(this.lblCultBysmiel)
/*  330 */         .addComponent(this.lblCultDreeg)
/*  331 */         .addComponent(this.lblCultSolael)
/*  332 */         .addComponent(this.lblUndead)
/*  333 */         .addComponent(this.lblBeasts)
/*  334 */         .addComponent(this.lblAetherials)
/*  335 */         .addComponent(this.lblChthonians)
/*  336 */         .addComponent(this.lblOutlaws)
/*  337 */         .addComponent(this.lblAetherialVanguard)
/*  338 */         .addComponent(this.lblEldritchHorrors))
/*  339 */       .addGroup(layout.createParallelGroup()
/*  340 */         .addComponent(this.cbSurvivors)
/*  341 */         .addComponent(this.cbRovers)
/*  342 */         .addComponent(this.cbHomestead)
/*  343 */         .addComponent(this.cbBlackLegion)
/*  344 */         .addComponent(this.cbDeathsVigil)
/*  345 */         .addComponent(this.cbKymonsChosen)
/*  346 */         .addComponent(this.cbOutcast)
/*  347 */         .addComponent(this.cbCovenUgdenbog)
/*  348 */         .addComponent(this.cbBarrowholm)
/*  349 */         .addComponent(this.cbMalmouth)
/*  350 */         .addComponent(this.cbCultBysmiel)
/*  351 */         .addComponent(this.cbCultDreeg)
/*  352 */         .addComponent(this.cbCultSolael)
/*  353 */         .addComponent(this.cbUndead)
/*  354 */         .addComponent(this.cbBeasts)
/*  355 */         .addComponent(this.cbAetherials)
/*  356 */         .addComponent(this.cbChthonians)
/*  357 */         .addComponent(this.cbOutlaws)
/*  358 */         .addComponent(this.cbAetherialVanguard)
/*  359 */         .addComponent(this.cbEldritchHorrors));
/*  360 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  363 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  366 */     vGroup
/*  367 */       .addGroup(layout.createParallelGroup()
/*  368 */         .addComponent(this.lblSurvivors)
/*  369 */         .addComponent(this.cbSurvivors))
/*  370 */       .addGroup(layout.createParallelGroup()
/*  371 */         .addComponent(this.lblRovers)
/*  372 */         .addComponent(this.cbRovers))
/*  373 */       .addGroup(layout.createParallelGroup()
/*  374 */         .addComponent(this.lblHomestead)
/*  375 */         .addComponent(this.cbHomestead))
/*  376 */       .addGroup(layout.createParallelGroup()
/*  377 */         .addComponent(this.lblBlackLegion)
/*  378 */         .addComponent(this.cbBlackLegion))
/*  379 */       .addGroup(layout.createParallelGroup()
/*  380 */         .addComponent(this.lblDeathsVigil)
/*  381 */         .addComponent(this.cbDeathsVigil))
/*  382 */       .addGroup(layout.createParallelGroup()
/*  383 */         .addComponent(this.lblKymonsChosen)
/*  384 */         .addComponent(this.cbKymonsChosen))
/*  385 */       .addGroup(layout.createParallelGroup()
/*  386 */         .addComponent(this.lblOutcast)
/*  387 */         .addComponent(this.cbOutcast))
/*  388 */       .addGroup(layout.createParallelGroup()
/*  389 */         .addComponent(this.lblCovenUgdenbog)
/*  390 */         .addComponent(this.cbCovenUgdenbog))
/*  391 */       .addGroup(layout.createParallelGroup()
/*  392 */         .addComponent(this.lblBarrowholm)
/*  393 */         .addComponent(this.cbBarrowholm))
/*  394 */       .addGroup(layout.createParallelGroup()
/*  395 */         .addComponent(this.lblMalmouth)
/*  396 */         .addComponent(this.cbMalmouth))
/*  397 */       .addGroup(layout.createParallelGroup()
/*  398 */         .addComponent(this.lblCultBysmiel)
/*  399 */         .addComponent(this.cbCultBysmiel))
/*  400 */       .addGroup(layout.createParallelGroup()
/*  401 */         .addComponent(this.lblCultDreeg)
/*  402 */         .addComponent(this.cbCultDreeg))
/*  403 */       .addGroup(layout.createParallelGroup()
/*  404 */         .addComponent(this.lblCultSolael)
/*  405 */         .addComponent(this.cbCultSolael))
/*  406 */       .addGroup(layout.createParallelGroup()
/*  407 */         .addComponent(this.lblUndead)
/*  408 */         .addComponent(this.cbUndead))
/*  409 */       .addGroup(layout.createParallelGroup()
/*  410 */         .addComponent(this.lblBeasts)
/*  411 */         .addComponent(this.cbBeasts))
/*  412 */       .addGroup(layout.createParallelGroup()
/*  413 */         .addComponent(this.lblAetherials)
/*  414 */         .addComponent(this.cbAetherials))
/*  415 */       .addGroup(layout.createParallelGroup()
/*  416 */         .addComponent(this.lblChthonians)
/*  417 */         .addComponent(this.cbChthonians))
/*  418 */       .addGroup(layout.createParallelGroup()
/*  419 */         .addComponent(this.lblOutlaws)
/*  420 */         .addComponent(this.cbOutlaws))
/*  421 */       .addGroup(layout.createParallelGroup()
/*  422 */         .addComponent(this.lblAetherialVanguard)
/*  423 */         .addComponent(this.cbAetherialVanguard))
/*  424 */       .addGroup(layout.createParallelGroup()
/*  425 */         .addComponent(this.lblEldritchHorrors)
/*  426 */         .addComponent(this.cbEldritchHorrors));
/*  427 */     layout.setVerticalGroup(vGroup);
/*      */     
/*  429 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblRovers });
/*  430 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblHomestead });
/*  431 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblBlackLegion });
/*  432 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblDeathsVigil });
/*  433 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblKymonsChosen });
/*  434 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblOutcast });
/*  435 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblCovenUgdenbog });
/*  436 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblBarrowholm });
/*  437 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblMalmouth });
/*  438 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblCultBysmiel });
/*  439 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblCultDreeg });
/*  440 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblCultSolael });
/*  441 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblUndead });
/*  442 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblBeasts });
/*  443 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblAetherials });
/*  444 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblChthonians });
/*  445 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblOutlaws });
/*  446 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblAetherialVanguard });
/*  447 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblEldritchHorrors });
/*      */     
/*  449 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbSurvivors });
/*  450 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbRovers });
/*  451 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbHomestead });
/*  452 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbBlackLegion });
/*  453 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbDeathsVigil });
/*  454 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbKymonsChosen });
/*  455 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbOutcast });
/*  456 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbCovenUgdenbog });
/*  457 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbBarrowholm });
/*  458 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbMalmouth });
/*  459 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbCultBysmiel });
/*  460 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbCultDreeg });
/*  461 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbCultSolael });
/*  462 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbUndead });
/*  463 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbBeasts });
/*  464 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbAetherials });
/*  465 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbChthonians });
/*  466 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbOutlaws });
/*  467 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbAetherialVanguard });
/*  468 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbEldritchHorrors });
/*      */     
/*  470 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblRovers });
/*  471 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblHomestead });
/*  472 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblBlackLegion });
/*  473 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblDeathsVigil });
/*  474 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblKymonsChosen });
/*  475 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblOutcast });
/*  476 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblCovenUgdenbog });
/*  477 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblBarrowholm });
/*  478 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblMalmouth });
/*  479 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblCultBysmiel });
/*  480 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblCultDreeg });
/*  481 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblCultSolael });
/*  482 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblUndead });
/*  483 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblBeasts });
/*  484 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblAetherials });
/*  485 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblChthonians });
/*  486 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblOutlaws });
/*  487 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblAetherialVanguard });
/*  488 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblEldritchHorrors });
/*      */     
/*  490 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbSurvivors });
/*  491 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbRovers });
/*  492 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbHomestead });
/*  493 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbBlackLegion });
/*  494 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbDeathsVigil });
/*  495 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbKymonsChosen });
/*  496 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbOutcast });
/*  497 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbCovenUgdenbog });
/*  498 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbBarrowholm });
/*  499 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbMalmouth });
/*  500 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbCultBysmiel });
/*  501 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbCultDreeg });
/*  502 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbCultSolael });
/*  503 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbUndead });
/*  504 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbBeasts });
/*  505 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbAetherials });
/*  506 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbChthonians });
/*  507 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbOutlaws });
/*  508 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbAetherialVanguard });
/*  509 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbEldritchHorrors });
/*      */   }
/*      */ 
/*      */   
/*      */   public void adjustUI() {
/*  514 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  515 */     Font fntCombo = UIManager.getDefaults().getFont("ComboBox.font");
/*  516 */     if (fntCombo == null) fntCombo = fntLabel; 
/*  517 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/*  518 */     if (fntBorder == null) fntBorder = fntLabel;
/*      */     
/*  520 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  521 */     fntCombo = fntCombo.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  522 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*      */     
/*  524 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  525 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  526 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*  527 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_FACTIONS"));
/*  528 */     text.setTitleFont(fntBorder);
/*      */     
/*  530 */     setBorder(text);
/*      */     
/*  532 */     loadCBText();
/*      */     
/*  534 */     if (this.lblSurvivors == null) this.lblSurvivors = new JLabel(); 
/*  535 */     this.lblSurvivors.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_DEVILS_CROSSING"));
/*  536 */     this.lblSurvivors.setFont(fntLabel);
/*      */     
/*  538 */     if (this.lblRovers == null) this.lblRovers = new JLabel(); 
/*  539 */     this.lblRovers.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_ROVERS"));
/*  540 */     this.lblRovers.setFont(fntLabel);
/*      */     
/*  542 */     if (this.lblHomestead == null) this.lblHomestead = new JLabel(); 
/*  543 */     this.lblHomestead.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_HOMESTEAD"));
/*  544 */     this.lblHomestead.setFont(fntLabel);
/*      */     
/*  546 */     if (this.lblBlackLegion == null) this.lblBlackLegion = new JLabel(); 
/*  547 */     this.lblBlackLegion.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_BLACK_LEGION"));
/*  548 */     this.lblBlackLegion.setFont(fntLabel);
/*      */     
/*  550 */     if (this.lblDeathsVigil == null) this.lblDeathsVigil = new JLabel(); 
/*  551 */     this.lblDeathsVigil.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_DEATHS_VIGIL"));
/*  552 */     this.lblDeathsVigil.setFont(fntLabel);
/*      */     
/*  554 */     if (this.lblKymonsChosen == null) this.lblKymonsChosen = new JLabel(); 
/*  555 */     this.lblKymonsChosen.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_KYMONS_CHOSEN"));
/*  556 */     this.lblKymonsChosen.setFont(fntLabel);
/*      */     
/*  558 */     if (this.lblOutcast == null) this.lblOutcast = new JLabel(); 
/*  559 */     this.lblOutcast.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_OUTCAST"));
/*  560 */     this.lblOutcast.setFont(fntLabel);
/*      */     
/*  562 */     if (this.lblCovenUgdenbog == null) this.lblCovenUgdenbog = new JLabel(); 
/*  563 */     this.lblCovenUgdenbog.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_COVEN_UGDENBOG"));
/*  564 */     this.lblCovenUgdenbog.setFont(fntLabel);
/*  565 */     this.lblCovenUgdenbog.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  567 */     if (this.lblBarrowholm == null) this.lblBarrowholm = new JLabel(); 
/*  568 */     this.lblBarrowholm.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_BARROWHOLM"));
/*  569 */     this.lblBarrowholm.setFont(fntLabel);
/*  570 */     this.lblBarrowholm.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  572 */     if (this.lblMalmouth == null) this.lblMalmouth = new JLabel(); 
/*  573 */     this.lblMalmouth.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_MALMOUTH_RESISTANCE"));
/*  574 */     this.lblMalmouth.setFont(fntLabel);
/*  575 */     this.lblMalmouth.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  577 */     if (this.lblCultBysmiel == null) this.lblCultBysmiel = new JLabel(); 
/*  578 */     this.lblCultBysmiel.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_CULT_BYSMIEL"));
/*  579 */     this.lblCultBysmiel.setFont(fntLabel);
/*  580 */     this.lblCultBysmiel.setVisible(GDStashFrame.expansionForgottenGods);
/*      */     
/*  582 */     if (this.lblCultDreeg == null) this.lblCultDreeg = new JLabel(); 
/*  583 */     this.lblCultDreeg.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_CULT_DREEG"));
/*  584 */     this.lblCultDreeg.setFont(fntLabel);
/*  585 */     this.lblCultDreeg.setVisible(GDStashFrame.expansionForgottenGods);
/*      */     
/*  587 */     if (this.lblCultSolael == null) this.lblCultSolael = new JLabel(); 
/*  588 */     this.lblCultSolael.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_CULT_SOLAEL"));
/*  589 */     this.lblCultSolael.setFont(fntLabel);
/*  590 */     this.lblCultSolael.setVisible(GDStashFrame.expansionForgottenGods);
/*      */     
/*  592 */     if (this.lblUndead == null) this.lblUndead = new JLabel(); 
/*  593 */     this.lblUndead.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_UNDEAD"));
/*  594 */     this.lblUndead.setFont(fntLabel);
/*      */     
/*  596 */     if (this.lblBeasts == null) this.lblBeasts = new JLabel(); 
/*  597 */     this.lblBeasts.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_BEASTS"));
/*  598 */     this.lblBeasts.setFont(fntLabel);
/*  599 */     this.lblBeasts.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  601 */     if (this.lblAetherials == null) this.lblAetherials = new JLabel(); 
/*  602 */     this.lblAetherials.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_AETHERIALS"));
/*  603 */     this.lblAetherials.setFont(fntLabel);
/*      */     
/*  605 */     if (this.lblChthonians == null) this.lblChthonians = new JLabel(); 
/*  606 */     this.lblChthonians.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_CHTHONIANS"));
/*  607 */     this.lblChthonians.setFont(fntLabel);
/*      */     
/*  609 */     if (this.lblOutlaws == null) this.lblOutlaws = new JLabel(); 
/*  610 */     this.lblOutlaws.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_CRONLEYS_GANG"));
/*  611 */     this.lblOutlaws.setFont(fntLabel);
/*      */     
/*  613 */     if (this.lblAetherialVanguard == null) this.lblAetherialVanguard = new JLabel(); 
/*  614 */     this.lblAetherialVanguard.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_AETHERIAL_VANGUARD"));
/*  615 */     this.lblAetherialVanguard.setFont(fntLabel);
/*  616 */     this.lblAetherialVanguard.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  618 */     if (this.lblEldritchHorrors == null) this.lblEldritchHorrors = new JLabel(); 
/*  619 */     this.lblEldritchHorrors.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_ELDRITCH_HORRORS"));
/*  620 */     this.lblEldritchHorrors.setFont(fntLabel);
/*  621 */     this.lblEldritchHorrors.setVisible(GDStashFrame.expansionForgottenGods);
/*      */     
/*  623 */     if (this.cbSurvivors == null) {
/*  624 */       this.cbSurvivors = new JComboBox<>(factionPositive);
/*  625 */       this.cbSurvivors.addActionListener(new ReputationListener());
/*      */     } else {
/*  627 */       updateLanguage(this.cbSurvivors);
/*      */     } 
/*  629 */     this.cbSurvivors.setFont(fntCombo);
/*      */     
/*  631 */     if (this.cbRovers == null) {
/*  632 */       this.cbRovers = new JComboBox<>(factionPositive);
/*  633 */       this.cbRovers.addActionListener(new ReputationListener());
/*      */     } else {
/*  635 */       updateLanguage(this.cbRovers);
/*      */     } 
/*  637 */     this.cbRovers.setFont(fntCombo);
/*      */     
/*  639 */     if (this.cbHomestead == null) {
/*  640 */       this.cbHomestead = new JComboBox<>(factionPositive);
/*  641 */       this.cbHomestead.addActionListener(new ReputationListener());
/*      */     } else {
/*  643 */       updateLanguage(this.cbHomestead);
/*      */     } 
/*  645 */     this.cbHomestead.setFont(fntCombo);
/*      */     
/*  647 */     if (this.cbBlackLegion == null) {
/*  648 */       this.cbBlackLegion = new JComboBox<>(factionPositive);
/*  649 */       this.cbBlackLegion.addActionListener(new ReputationListener());
/*      */     } else {
/*  651 */       updateLanguage(this.cbBlackLegion);
/*      */     } 
/*  653 */     this.cbBlackLegion.setFont(fntCombo);
/*      */     
/*  655 */     if (this.cbDeathsVigil == null) {
/*  656 */       this.cbDeathsVigil = new JComboBox<>(factionAll);
/*  657 */       this.cbDeathsVigil.addActionListener(new ReputationListener());
/*      */     } else {
/*  659 */       updateLanguage(this.cbDeathsVigil);
/*      */     } 
/*  661 */     this.cbDeathsVigil.setFont(fntCombo);
/*      */     
/*  663 */     if (this.cbKymonsChosen == null) {
/*  664 */       this.cbKymonsChosen = new JComboBox<>(factionAll);
/*  665 */       this.cbKymonsChosen.addActionListener(new ReputationListener());
/*      */     } else {
/*  667 */       updateLanguage(this.cbKymonsChosen);
/*      */     } 
/*  669 */     this.cbKymonsChosen.setFont(fntCombo);
/*      */     
/*  671 */     if (this.cbOutcast == null) {
/*  672 */       this.cbOutcast = new JComboBox<>(factionAll);
/*  673 */       this.cbOutcast.addActionListener(new ReputationListener());
/*      */     } else {
/*  675 */       updateLanguage(this.cbOutcast);
/*      */     } 
/*  677 */     this.cbOutcast.setFont(fntCombo);
/*      */     
/*  679 */     if (this.cbCovenUgdenbog == null) {
/*  680 */       this.cbCovenUgdenbog = new JComboBox<>(factionAll);
/*  681 */       this.cbCovenUgdenbog.addActionListener(new ReputationListener());
/*      */     } else {
/*  683 */       updateLanguage(this.cbCovenUgdenbog);
/*      */     } 
/*  685 */     this.cbCovenUgdenbog.setFont(fntCombo);
/*  686 */     this.cbCovenUgdenbog.setEnabled(GDStashFrame.expansionAshesOfMalmouth);
/*  687 */     this.cbCovenUgdenbog.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  689 */     if (this.cbBarrowholm == null) {
/*  690 */       this.cbBarrowholm = new JComboBox<>(factionAll);
/*  691 */       this.cbBarrowholm.addActionListener(new ReputationListener());
/*      */     } else {
/*  693 */       updateLanguage(this.cbBarrowholm);
/*      */     } 
/*  695 */     this.cbBarrowholm.setFont(fntCombo);
/*  696 */     this.cbBarrowholm.setEnabled(GDStashFrame.expansionAshesOfMalmouth);
/*  697 */     this.cbBarrowholm.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  699 */     if (this.cbMalmouth == null) {
/*  700 */       this.cbMalmouth = new JComboBox<>(factionAll);
/*  701 */       this.cbMalmouth.addActionListener(new ReputationListener());
/*      */     } else {
/*  703 */       updateLanguage(this.cbMalmouth);
/*      */     } 
/*  705 */     this.cbMalmouth.setFont(fntCombo);
/*  706 */     this.cbMalmouth.setEnabled(GDStashFrame.expansionAshesOfMalmouth);
/*  707 */     this.cbMalmouth.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  709 */     if (this.cbCultBysmiel == null) {
/*  710 */       this.cbCultBysmiel = new JComboBox<>(factionPositive);
/*  711 */       this.cbCultBysmiel.addActionListener(new ReputationListener());
/*      */     } else {
/*  713 */       updateLanguage(this.cbCultBysmiel);
/*      */     } 
/*  715 */     this.cbCultBysmiel.setFont(fntCombo);
/*  716 */     this.cbCultBysmiel.setEnabled(GDStashFrame.expansionForgottenGods);
/*  717 */     this.cbCultBysmiel.setVisible(GDStashFrame.expansionForgottenGods);
/*      */     
/*  719 */     if (this.cbCultDreeg == null) {
/*  720 */       this.cbCultDreeg = new JComboBox<>(factionPositive);
/*  721 */       this.cbCultDreeg.addActionListener(new ReputationListener());
/*      */     } else {
/*  723 */       updateLanguage(this.cbCultDreeg);
/*      */     } 
/*  725 */     this.cbCultDreeg.setFont(fntCombo);
/*  726 */     this.cbCultDreeg.setEnabled(GDStashFrame.expansionForgottenGods);
/*  727 */     this.cbCultDreeg.setVisible(GDStashFrame.expansionForgottenGods);
/*      */     
/*  729 */     if (this.cbCultSolael == null) {
/*  730 */       this.cbCultSolael = new JComboBox<>(factionPositive);
/*  731 */       this.cbCultSolael.addActionListener(new ReputationListener());
/*      */     } else {
/*  733 */       updateLanguage(this.cbCultSolael);
/*      */     } 
/*  735 */     this.cbCultSolael.setFont(fntCombo);
/*  736 */     this.cbCultSolael.setEnabled(GDStashFrame.expansionForgottenGods);
/*  737 */     this.cbCultSolael.setVisible(GDStashFrame.expansionForgottenGods);
/*      */     
/*  739 */     if (this.cbUndead == null) {
/*  740 */       this.cbUndead = new JComboBox<>(factionNegative);
/*  741 */       this.cbUndead.addActionListener(new ReputationListener());
/*      */     } else {
/*  743 */       updateLanguage(this.cbUndead);
/*      */     } 
/*  745 */     this.cbUndead.setFont(fntCombo);
/*      */     
/*  747 */     if (this.cbBeasts == null) {
/*  748 */       this.cbBeasts = new JComboBox<>(factionNegative);
/*  749 */       this.cbBeasts.addActionListener(new ReputationListener());
/*      */     } else {
/*  751 */       updateLanguage(this.cbBeasts);
/*      */     } 
/*  753 */     this.cbBeasts.setFont(fntCombo);
/*  754 */     this.cbBeasts.setEnabled(GDStashFrame.expansionAshesOfMalmouth);
/*  755 */     this.cbBeasts.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  757 */     if (this.cbAetherials == null) {
/*  758 */       this.cbAetherials = new JComboBox<>(factionNegative);
/*  759 */       this.cbAetherials.addActionListener(new ReputationListener());
/*      */     } else {
/*  761 */       updateLanguage(this.cbAetherials);
/*      */     } 
/*  763 */     this.cbAetherials.setFont(fntCombo);
/*      */     
/*  765 */     if (this.cbChthonians == null) {
/*  766 */       this.cbChthonians = new JComboBox<>(factionNegative);
/*  767 */       this.cbChthonians.addActionListener(new ReputationListener());
/*      */     } else {
/*  769 */       updateLanguage(this.cbChthonians);
/*      */     } 
/*  771 */     this.cbChthonians.setFont(fntCombo);
/*      */     
/*  773 */     if (this.cbOutlaws == null) {
/*  774 */       this.cbOutlaws = new JComboBox<>(factionNegative);
/*  775 */       this.cbOutlaws.addActionListener(new ReputationListener());
/*      */     } else {
/*  777 */       updateLanguage(this.cbOutlaws);
/*      */     } 
/*  779 */     this.cbOutlaws.setFont(fntCombo);
/*      */     
/*  781 */     if (this.cbAetherialVanguard == null) {
/*  782 */       this.cbAetherialVanguard = new JComboBox<>(factionNegative);
/*  783 */       this.cbAetherialVanguard.addActionListener(new ReputationListener());
/*      */     } else {
/*  785 */       updateLanguage(this.cbAetherialVanguard);
/*      */     } 
/*  787 */     this.cbAetherialVanguard.setFont(fntCombo);
/*  788 */     this.cbAetherialVanguard.setEnabled(GDStashFrame.expansionAshesOfMalmouth);
/*  789 */     this.cbAetherialVanguard.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  791 */     if (this.cbEldritchHorrors == null) {
/*  792 */       this.cbEldritchHorrors = new JComboBox<>(factionNegative);
/*  793 */       this.cbEldritchHorrors.addActionListener(new ReputationListener());
/*      */     } else {
/*  795 */       updateLanguage(this.cbEldritchHorrors);
/*      */     } 
/*  797 */     this.cbEldritchHorrors.setFont(fntCombo);
/*  798 */     this.cbEldritchHorrors.setEnabled(GDStashFrame.expansionForgottenGods);
/*  799 */     this.cbEldritchHorrors.setVisible(GDStashFrame.expansionForgottenGods);
/*      */   }
/*      */   
/*      */   private void updateLanguage(JComboBox<String> cb) {
/*  803 */     this.skipListener = true;
/*      */     
/*  805 */     for (int i = 0; i < this.cbReputations.length; i++) {
/*  806 */       if (cb == this.cbReputations[i]) {
/*  807 */         int index = cb.getSelectedIndex();
/*      */         
/*  809 */         cb.removeAllItems();
/*      */         
/*  811 */         String[] factions = factionAll;
/*      */         
/*  813 */         switch (this.reputationTypes[i]) {
/*      */           case 2:
/*  815 */             factions = factionPositive;
/*      */             break;
/*      */ 
/*      */           
/*      */           case 3:
/*  820 */             factions = factionNegative;
/*      */             break;
/*      */         } 
/*      */ 
/*      */         
/*      */         int j;
/*  826 */         for (j = 0; j < factions.length; j++) {
/*  827 */           cb.addItem(factions[j]);
/*      */         }
/*      */         
/*  830 */         cb.setSelectedIndex(index);
/*      */       } 
/*      */     } 
/*      */     
/*  834 */     this.skipListener = false;
/*      */   }
/*      */   
/*      */   private void fillReputation(JComboBox cb) {
/*  838 */     int index = cb.getSelectedIndex();
/*      */     
/*  840 */     for (int i = 0; i < this.cbReputations.length; i++) {
/*  841 */       if (cb == this.cbReputations[i]) {
/*  842 */         switch (this.reputationTypes[i]) {
/*      */           case 1:
/*  844 */             this.reputations[i] = getReputationAll(index);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 2:
/*  849 */             this.reputations[i] = getReputationPositive(index);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 3:
/*  854 */             this.reputations[i] = getReputationNegative(index);
/*      */             break;
/*      */         } 
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getReputationAll(int index) {
/*  866 */     return minReputationLevels[index];
/*      */   }
/*      */   
/*      */   private int getReputationNegative(int index) {
/*  870 */     return minReputationLevels[4 - index];
/*      */   }
/*      */   
/*      */   private int getReputationPositive(int index) {
/*  874 */     return minReputationLevels[index + 4];
/*      */   }
/*      */   private void setReputationAll(JComboBox cb, int reputation) {
/*      */     int i;
/*  878 */     for (i = 0; i < 4; i++) {
/*  879 */       if (reputation <= minReputationLevels[i]) {
/*  880 */         cb.setSelectedIndex(i);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  886 */     for (i = 9; i > 4; i--) {
/*  887 */       if (reputation >= minReputationLevels[i]) {
/*  888 */         cb.setSelectedIndex(i);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  894 */     cb.setSelectedIndex(4);
/*      */   }
/*      */   
/*      */   private void setReputationNegative(JComboBox cb, int reputation) {
/*  898 */     for (int i = 0; i < 4; i++) {
/*  899 */       if (reputation <= minReputationLevels[i]) {
/*  900 */         cb.setSelectedIndex(4 - i);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  906 */     cb.setSelectedIndex(0);
/*      */   }
/*      */   
/*      */   private void setReputationPositive(JComboBox cb, int reputation) {
/*  910 */     for (int i = 9; i > 4; i--) {
/*  911 */       if (reputation >= minReputationLevels[i]) {
/*  912 */         cb.setSelectedIndex(i - 4);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  918 */     cb.setSelectedIndex(0);
/*      */   }
/*      */   
/*      */   private void adjustReputationValues(int faction, JComboBox<String> jcb) {
/*  922 */     this.reputationTypes[faction] = 1;
/*  923 */     if (this.reputations[faction] < 0) this.reputationTypes[faction] = 3; 
/*  924 */     if (this.reputations[faction] > 0) this.reputationTypes[faction] = 2;
/*      */     
/*  926 */     DefaultComboBoxModel<String> model = null;
/*      */     
/*  928 */     switch (this.reputationTypes[faction]) {
/*      */       case 3:
/*  930 */         model = new DefaultComboBoxModel<>(factionNegative);
/*      */         break;
/*      */       
/*      */       case 2:
/*  934 */         model = new DefaultComboBoxModel<>(factionPositive);
/*      */         break;
/*      */       
/*      */       default:
/*  938 */         model = new DefaultComboBoxModel<>(factionAll);
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  943 */     jcb.setModel(model);
/*      */     
/*  945 */     switch (this.reputationTypes[faction]) {
/*      */       case 3:
/*  947 */         setReputationNegative(jcb, this.reputations[faction]);
/*      */         return;
/*      */       
/*      */       case 2:
/*  951 */         setReputationPositive(jcb, this.reputations[faction]);
/*      */         return;
/*      */     } 
/*      */     
/*  955 */     setReputationAll(jcb, this.reputations[faction]);
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
/*      */   public void setChar(GDChar gdc) {
/*  968 */     this.skipListener = true;
/*      */     
/*  970 */     boolean expAoM = GDStashFrame.expansionAshesOfMalmouth;
/*  971 */     boolean expFG = GDStashFrame.expansionForgottenGods;
/*      */     
/*  973 */     if (gdc == null) {
/*  974 */       int i; for (i = 0; i < this.reputations.length; i++) {
/*  975 */         this.reputations[i] = 0;
/*      */       }
/*      */     } else {
/*  978 */       this.reputations = gdc.getFactionReputations();
/*      */       
/*  980 */       expFG = gdc.isForgottenGodsChar();
/*  981 */       expAoM = (expFG || gdc.isAsheshOfMalmouthChar());
/*      */     } 
/*      */     
/*  984 */     if (!expAoM) {
/*  985 */       FACTIONS = FACTIONS_VANILLA;
/*      */     }
/*  987 */     else if (expFG) {
/*  988 */       FACTIONS = FACTIONS_GODS;
/*      */     } else {
/*  990 */       FACTIONS = FACTIONS_MALMOUTH;
/*      */     } 
/*      */ 
/*      */     
/*  994 */     this.lblCovenUgdenbog.setVisible(expAoM);
/*  995 */     this.cbCovenUgdenbog.setEnabled(expAoM);
/*  996 */     this.cbCovenUgdenbog.setVisible(expAoM);
/*      */     
/*  998 */     this.lblBarrowholm.setVisible(expAoM);
/*  999 */     this.cbBarrowholm.setEnabled(expAoM);
/* 1000 */     this.cbBarrowholm.setVisible(expAoM);
/*      */     
/* 1002 */     this.lblMalmouth.setVisible(expAoM);
/* 1003 */     this.cbMalmouth.setEnabled(expAoM);
/* 1004 */     this.cbMalmouth.setVisible(expAoM);
/*      */     
/* 1006 */     this.lblBeasts.setVisible(expAoM);
/* 1007 */     this.cbBeasts.setEnabled(expAoM);
/* 1008 */     this.cbBeasts.setVisible(expAoM);
/*      */     
/* 1010 */     this.lblAetherialVanguard.setVisible(expAoM);
/* 1011 */     this.cbAetherialVanguard.setEnabled(expAoM);
/* 1012 */     this.cbAetherialVanguard.setVisible(expAoM);
/*      */     
/* 1014 */     this.lblCultBysmiel.setVisible(expFG);
/* 1015 */     this.cbCultBysmiel.setEnabled(expFG);
/* 1016 */     this.cbCultBysmiel.setVisible(expFG);
/*      */     
/* 1018 */     this.lblCultDreeg.setVisible(expFG);
/* 1019 */     this.cbCultDreeg.setEnabled(expFG);
/* 1020 */     this.cbCultDreeg.setVisible(expFG);
/*      */     
/* 1022 */     this.lblCultSolael.setVisible(expFG);
/* 1023 */     this.cbCultSolael.setEnabled(expFG);
/* 1024 */     this.cbCultSolael.setVisible(expFG);
/*      */     
/* 1026 */     this.lblEldritchHorrors.setVisible(expFG);
/* 1027 */     this.cbEldritchHorrors.setEnabled(expFG);
/* 1028 */     this.cbEldritchHorrors.setVisible(expFG);
/*      */     
/* 1030 */     DefaultComboBoxModel<String> model = null;
/*      */     
/* 1032 */     setReputationPositive(this.cbSurvivors, this.reputations[0]);
/* 1033 */     setReputationPositive(this.cbRovers, this.reputations[1]);
/* 1034 */     setReputationPositive(this.cbHomestead, this.reputations[2]);
/* 1035 */     setReputationPositive(this.cbBlackLegion, this.reputations[3]);
/* 1036 */     setReputationAll(this.cbDeathsVigil, this.reputations[4]);
/*      */     
/* 1038 */     setReputationAll(this.cbKymonsChosen, this.reputations[5]);
/*      */     
/* 1040 */     setReputationAll(this.cbOutcast, this.reputations[6]);
/*      */     
/* 1042 */     setReputationPositive(this.cbCovenUgdenbog, this.reputations[7]);
/* 1043 */     setReputationAll(this.cbBarrowholm, this.reputations[8]);
/*      */     
/* 1045 */     setReputationPositive(this.cbMalmouth, this.reputations[9]);
/* 1046 */     setReputationPositive(this.cbCultBysmiel, this.reputations[10]);
/* 1047 */     setReputationPositive(this.cbCultDreeg, this.reputations[11]);
/* 1048 */     setReputationPositive(this.cbCultSolael, this.reputations[12]);
/* 1049 */     setReputationNegative(this.cbUndead, this.reputations[13]);
/* 1050 */     setReputationNegative(this.cbBeasts, this.reputations[14]);
/* 1051 */     setReputationNegative(this.cbAetherials, this.reputations[15]);
/* 1052 */     setReputationNegative(this.cbChthonians, this.reputations[16]);
/* 1053 */     setReputationNegative(this.cbOutlaws, this.reputations[17]);
/* 1054 */     setReputationNegative(this.cbAetherialVanguard, this.reputations[18]);
/* 1055 */     setReputationNegative(this.cbEldritchHorrors, this.reputations[19]);
/*      */     
/* 1057 */     this.skipListener = false;
/*      */   }
/*      */   
/*      */   public void updateChar(GDChar gdc) {
/* 1061 */     if (gdc == null)
/*      */       return; 
/* 1063 */     gdc.setFactionReputations(this.reputations);
/*      */   }
/*      */   
/*      */   public void updateConfig() {
/* 1067 */     initReputationLevels();
/* 1068 */     loadCBText();
/*      */   }
/*      */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharFactionPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */