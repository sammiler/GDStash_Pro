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
/*  186 */     DBEngineGame game = DBEngineGame.get();
/*      */     
/*  188 */     if (game == null) {
/*  189 */       minReputationLevels[0] = -20000;
/*  190 */       minReputationLevels[1] = -8000;
/*  191 */       minReputationLevels[2] = -1500;
/*  192 */       minReputationLevels[3] = -1;
/*  193 */       minReputationLevels[4] = 0;
/*  194 */       minReputationLevels[5] = 1;
/*  195 */       minReputationLevels[6] = 1500;
/*  196 */       minReputationLevels[7] = 5000;
/*  197 */       minReputationLevels[8] = 10000;
/*  198 */       minReputationLevels[9] = 20000;
/*      */     } else {
/*  200 */       List<DBFactionReputation> values = game.getReputationList();
/*      */       
/*  202 */       minReputationLevels[0] = ((DBFactionReputation)values.get(0)).getValue();
/*  203 */       minReputationLevels[1] = ((DBFactionReputation)values.get(1)).getValue();
/*  204 */       minReputationLevels[2] = ((DBFactionReputation)values.get(2)).getValue();
/*  205 */       minReputationLevels[3] = -1;
/*  206 */       minReputationLevels[4] = 0;
/*  207 */       minReputationLevels[5] = 1;
/*  208 */       minReputationLevels[6] = ((DBFactionReputation)values.get(4)).getValue();
/*  209 */       minReputationLevels[7] = ((DBFactionReputation)values.get(5)).getValue();
/*  210 */       minReputationLevels[8] = ((DBFactionReputation)values.get(6)).getValue();
/*  211 */       minReputationLevels[9] = ((DBFactionReputation)values.get(7)).getValue();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void loadCBText() {
/*  216 */     factionAll[0] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_STATUS_NEMESIS");
/*  217 */     factionAll[1] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_STATUS_HATED");
/*  218 */     factionAll[2] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_STATUS_DESPISED");
/*  219 */     factionAll[3] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_STATUS_HOSTILE");
/*  220 */     factionAll[4] = "";
/*  221 */     factionAll[5] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_STATUS_TOLERATED");
/*  222 */     factionAll[6] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_STATUS_FRIENDLY");
/*  223 */     factionAll[7] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_STATUS_RESPECTED");
/*  224 */     factionAll[8] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_STATUS_HONORED");
/*  225 */     factionAll[9] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_STATUS_REVERED");
/*      */     
/*  227 */     factionNegative[0] = factionAll[4];
/*  228 */     factionNegative[1] = factionAll[3];
/*  229 */     factionNegative[2] = factionAll[2];
/*  230 */     factionNegative[3] = factionAll[1];
/*  231 */     factionNegative[4] = factionAll[0];
/*      */     
/*  233 */     factionPositive[0] = factionAll[4];
/*  234 */     factionPositive[1] = factionAll[5];
/*  235 */     factionPositive[2] = factionAll[6];
/*  236 */     factionPositive[3] = factionAll[7];
/*  237 */     factionPositive[4] = factionAll[8];
/*  238 */     factionPositive[5] = factionAll[9];
/*      */   }
/*      */   
/*      */   public GDCharFactionPane() {
/*  242 */     this.reputations = new int[FACTIONS.length];
/*      */     
/*  244 */     adjustUI();
/*      */     
/*  246 */     this.cbReputations = new JComboBox[FACTIONS.length];
/*      */     
/*  248 */     this.cbReputations[0] = this.cbSurvivors;
/*  249 */     this.cbReputations[1] = this.cbRovers;
/*  250 */     this.cbReputations[2] = this.cbHomestead;
/*  251 */     this.cbReputations[3] = this.cbBlackLegion;
/*  252 */     this.cbReputations[4] = this.cbDeathsVigil;
/*  253 */     this.cbReputations[5] = this.cbKymonsChosen;
/*  254 */     this.cbReputations[6] = this.cbOutcast;
/*  255 */     this.cbReputations[7] = this.cbCovenUgdenbog;
/*  256 */     this.cbReputations[8] = this.cbBarrowholm;
/*  257 */     this.cbReputations[9] = this.cbMalmouth;
/*  258 */     this.cbReputations[10] = this.cbCultBysmiel;
/*  259 */     this.cbReputations[11] = this.cbCultDreeg;
/*  260 */     this.cbReputations[12] = this.cbCultSolael;
/*  261 */     this.cbReputations[13] = this.cbUndead;
/*  262 */     this.cbReputations[14] = this.cbBeasts;
/*  263 */     this.cbReputations[15] = this.cbAetherials;
/*  264 */     this.cbReputations[16] = this.cbChthonians;
/*  265 */     this.cbReputations[17] = this.cbOutlaws;
/*  266 */     this.cbReputations[18] = this.cbAetherialVanguard;
/*  267 */     this.cbReputations[19] = this.cbEldritchHorrors;
/*      */     
/*  269 */     this.reputationTypes = new int[FACTIONS.length];
/*      */     
/*  271 */     this.reputationTypes[0] = 2;
/*  272 */     this.reputationTypes[1] = 2;
/*  273 */     this.reputationTypes[2] = 2;
/*  274 */     this.reputationTypes[3] = 2;
/*  275 */     this.reputationTypes[4] = 1;
/*  276 */     this.reputationTypes[5] = 1;
/*  277 */     this.reputationTypes[6] = 1;
/*  278 */     this.reputationTypes[7] = 2;
/*  279 */     this.reputationTypes[8] = 1;
/*  280 */     this.reputationTypes[9] = 2;
/*  281 */     this.reputationTypes[10] = 2;
/*  282 */     this.reputationTypes[11] = 2;
/*  283 */     this.reputationTypes[12] = 2;
/*  284 */     this.reputationTypes[13] = 3;
/*  285 */     this.reputationTypes[14] = 3;
/*  286 */     this.reputationTypes[15] = 3;
/*  287 */     this.reputationTypes[16] = 3;
/*  288 */     this.reputationTypes[17] = 3;
/*  289 */     this.reputationTypes[18] = 3;
/*  290 */     this.reputationTypes[19] = 3;
/*      */ 
/*      */ 
/*      */     
/*  294 */     GroupLayout layout = null;
/*  295 */     GroupLayout.SequentialGroup hGroup = null;
/*  296 */     GroupLayout.SequentialGroup vGroup = null;
/*      */     
/*  298 */     layout = new GroupLayout((Container)this);
/*  299 */     setLayout(layout);
/*      */     
/*  301 */     layout.setAutoCreateGaps(true);
/*      */ 
/*      */     
/*  304 */     layout.setAutoCreateContainerGaps(true);
/*      */ 
/*      */     
/*  307 */     hGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  310 */     hGroup
/*  311 */       .addGroup(layout.createParallelGroup()
/*  312 */         .addComponent(this.lblSurvivors)
/*  313 */         .addComponent(this.lblRovers)
/*  314 */         .addComponent(this.lblHomestead)
/*  315 */         .addComponent(this.lblBlackLegion)
/*  316 */         .addComponent(this.lblDeathsVigil)
/*  317 */         .addComponent(this.lblKymonsChosen)
/*  318 */         .addComponent(this.lblOutcast)
/*  319 */         .addComponent(this.lblCovenUgdenbog)
/*  320 */         .addComponent(this.lblBarrowholm)
/*  321 */         .addComponent(this.lblMalmouth)
/*  322 */         .addComponent(this.lblCultBysmiel)
/*  323 */         .addComponent(this.lblCultDreeg)
/*  324 */         .addComponent(this.lblCultSolael)
/*  325 */         .addComponent(this.lblUndead)
/*  326 */         .addComponent(this.lblBeasts)
/*  327 */         .addComponent(this.lblAetherials)
/*  328 */         .addComponent(this.lblChthonians)
/*  329 */         .addComponent(this.lblOutlaws)
/*  330 */         .addComponent(this.lblAetherialVanguard)
/*  331 */         .addComponent(this.lblEldritchHorrors))
/*  332 */       .addGroup(layout.createParallelGroup()
/*  333 */         .addComponent(this.cbSurvivors)
/*  334 */         .addComponent(this.cbRovers)
/*  335 */         .addComponent(this.cbHomestead)
/*  336 */         .addComponent(this.cbBlackLegion)
/*  337 */         .addComponent(this.cbDeathsVigil)
/*  338 */         .addComponent(this.cbKymonsChosen)
/*  339 */         .addComponent(this.cbOutcast)
/*  340 */         .addComponent(this.cbCovenUgdenbog)
/*  341 */         .addComponent(this.cbBarrowholm)
/*  342 */         .addComponent(this.cbMalmouth)
/*  343 */         .addComponent(this.cbCultBysmiel)
/*  344 */         .addComponent(this.cbCultDreeg)
/*  345 */         .addComponent(this.cbCultSolael)
/*  346 */         .addComponent(this.cbUndead)
/*  347 */         .addComponent(this.cbBeasts)
/*  348 */         .addComponent(this.cbAetherials)
/*  349 */         .addComponent(this.cbChthonians)
/*  350 */         .addComponent(this.cbOutlaws)
/*  351 */         .addComponent(this.cbAetherialVanguard)
/*  352 */         .addComponent(this.cbEldritchHorrors));
/*  353 */     layout.setHorizontalGroup(hGroup);
/*      */ 
/*      */     
/*  356 */     vGroup = layout.createSequentialGroup();
/*      */ 
/*      */     
/*  359 */     vGroup
/*  360 */       .addGroup(layout.createParallelGroup()
/*  361 */         .addComponent(this.lblSurvivors)
/*  362 */         .addComponent(this.cbSurvivors))
/*  363 */       .addGroup(layout.createParallelGroup()
/*  364 */         .addComponent(this.lblRovers)
/*  365 */         .addComponent(this.cbRovers))
/*  366 */       .addGroup(layout.createParallelGroup()
/*  367 */         .addComponent(this.lblHomestead)
/*  368 */         .addComponent(this.cbHomestead))
/*  369 */       .addGroup(layout.createParallelGroup()
/*  370 */         .addComponent(this.lblBlackLegion)
/*  371 */         .addComponent(this.cbBlackLegion))
/*  372 */       .addGroup(layout.createParallelGroup()
/*  373 */         .addComponent(this.lblDeathsVigil)
/*  374 */         .addComponent(this.cbDeathsVigil))
/*  375 */       .addGroup(layout.createParallelGroup()
/*  376 */         .addComponent(this.lblKymonsChosen)
/*  377 */         .addComponent(this.cbKymonsChosen))
/*  378 */       .addGroup(layout.createParallelGroup()
/*  379 */         .addComponent(this.lblOutcast)
/*  380 */         .addComponent(this.cbOutcast))
/*  381 */       .addGroup(layout.createParallelGroup()
/*  382 */         .addComponent(this.lblCovenUgdenbog)
/*  383 */         .addComponent(this.cbCovenUgdenbog))
/*  384 */       .addGroup(layout.createParallelGroup()
/*  385 */         .addComponent(this.lblBarrowholm)
/*  386 */         .addComponent(this.cbBarrowholm))
/*  387 */       .addGroup(layout.createParallelGroup()
/*  388 */         .addComponent(this.lblMalmouth)
/*  389 */         .addComponent(this.cbMalmouth))
/*  390 */       .addGroup(layout.createParallelGroup()
/*  391 */         .addComponent(this.lblCultBysmiel)
/*  392 */         .addComponent(this.cbCultBysmiel))
/*  393 */       .addGroup(layout.createParallelGroup()
/*  394 */         .addComponent(this.lblCultDreeg)
/*  395 */         .addComponent(this.cbCultDreeg))
/*  396 */       .addGroup(layout.createParallelGroup()
/*  397 */         .addComponent(this.lblCultSolael)
/*  398 */         .addComponent(this.cbCultSolael))
/*  399 */       .addGroup(layout.createParallelGroup()
/*  400 */         .addComponent(this.lblUndead)
/*  401 */         .addComponent(this.cbUndead))
/*  402 */       .addGroup(layout.createParallelGroup()
/*  403 */         .addComponent(this.lblBeasts)
/*  404 */         .addComponent(this.cbBeasts))
/*  405 */       .addGroup(layout.createParallelGroup()
/*  406 */         .addComponent(this.lblAetherials)
/*  407 */         .addComponent(this.cbAetherials))
/*  408 */       .addGroup(layout.createParallelGroup()
/*  409 */         .addComponent(this.lblChthonians)
/*  410 */         .addComponent(this.cbChthonians))
/*  411 */       .addGroup(layout.createParallelGroup()
/*  412 */         .addComponent(this.lblOutlaws)
/*  413 */         .addComponent(this.cbOutlaws))
/*  414 */       .addGroup(layout.createParallelGroup()
/*  415 */         .addComponent(this.lblAetherialVanguard)
/*  416 */         .addComponent(this.cbAetherialVanguard))
/*  417 */       .addGroup(layout.createParallelGroup()
/*  418 */         .addComponent(this.lblEldritchHorrors)
/*  419 */         .addComponent(this.cbEldritchHorrors));
/*  420 */     layout.setVerticalGroup(vGroup);
/*      */     
/*  422 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblRovers });
/*  423 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblHomestead });
/*  424 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblBlackLegion });
/*  425 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblDeathsVigil });
/*  426 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblKymonsChosen });
/*  427 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblOutcast });
/*  428 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblCovenUgdenbog });
/*  429 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblBarrowholm });
/*  430 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblMalmouth });
/*  431 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblCultBysmiel });
/*  432 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblCultDreeg });
/*  433 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblCultSolael });
/*  434 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblUndead });
/*  435 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblBeasts });
/*  436 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblAetherials });
/*  437 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblChthonians });
/*  438 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblOutlaws });
/*  439 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblAetherialVanguard });
/*  440 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.lblEldritchHorrors });
/*      */     
/*  442 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbSurvivors });
/*  443 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbRovers });
/*  444 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbHomestead });
/*  445 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbBlackLegion });
/*  446 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbDeathsVigil });
/*  447 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbKymonsChosen });
/*  448 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbOutcast });
/*  449 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbCovenUgdenbog });
/*  450 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbBarrowholm });
/*  451 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbMalmouth });
/*  452 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbCultBysmiel });
/*  453 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbCultDreeg });
/*  454 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbCultSolael });
/*  455 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbUndead });
/*  456 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbBeasts });
/*  457 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbAetherials });
/*  458 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbChthonians });
/*  459 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbOutlaws });
/*  460 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbAetherialVanguard });
/*  461 */     layout.linkSize(0, new Component[] { this.lblSurvivors, this.cbEldritchHorrors });
/*      */     
/*  463 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblRovers });
/*  464 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblHomestead });
/*  465 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblBlackLegion });
/*  466 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblDeathsVigil });
/*  467 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblKymonsChosen });
/*  468 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblOutcast });
/*  469 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblCovenUgdenbog });
/*  470 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblBarrowholm });
/*  471 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblMalmouth });
/*  472 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblCultBysmiel });
/*  473 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblCultDreeg });
/*  474 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblCultSolael });
/*  475 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblUndead });
/*  476 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblBeasts });
/*  477 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblAetherials });
/*  478 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblChthonians });
/*  479 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblOutlaws });
/*  480 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblAetherialVanguard });
/*  481 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.lblEldritchHorrors });
/*      */     
/*  483 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbSurvivors });
/*  484 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbRovers });
/*  485 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbHomestead });
/*  486 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbBlackLegion });
/*  487 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbDeathsVigil });
/*  488 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbKymonsChosen });
/*  489 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbOutcast });
/*  490 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbCovenUgdenbog });
/*  491 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbBarrowholm });
/*  492 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbMalmouth });
/*  493 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbCultBysmiel });
/*  494 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbCultDreeg });
/*  495 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbCultSolael });
/*  496 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbUndead });
/*  497 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbBeasts });
/*  498 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbAetherials });
/*  499 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbChthonians });
/*  500 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbOutlaws });
/*  501 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbAetherialVanguard });
/*  502 */     layout.linkSize(1, new Component[] { this.lblSurvivors, this.cbEldritchHorrors });
/*      */   }
/*      */ 
/*      */   
/*      */   public void adjustUI() {
/*  507 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  508 */     Font fntCombo = UIManager.getDefaults().getFont("ComboBox.font");
/*  509 */     if (fntCombo == null) fntCombo = fntLabel; 
/*  510 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/*  511 */     if (fntBorder == null) fntBorder = fntLabel;
/*      */     
/*  513 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  514 */     fntCombo = fntCombo.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  515 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*      */     
/*  517 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  518 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  519 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*  520 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_FACTIONS"));
/*  521 */     text.setTitleFont(fntBorder);
/*      */     
/*  523 */     setBorder(text);
/*      */     
/*  525 */     loadCBText();
/*      */     
/*  527 */     if (this.lblSurvivors == null) this.lblSurvivors = new JLabel(); 
/*  528 */     this.lblSurvivors.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_DEVILS_CROSSING"));
/*  529 */     this.lblSurvivors.setFont(fntLabel);
/*      */     
/*  531 */     if (this.lblRovers == null) this.lblRovers = new JLabel(); 
/*  532 */     this.lblRovers.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_ROVERS"));
/*  533 */     this.lblRovers.setFont(fntLabel);
/*      */     
/*  535 */     if (this.lblHomestead == null) this.lblHomestead = new JLabel(); 
/*  536 */     this.lblHomestead.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_HOMESTEAD"));
/*  537 */     this.lblHomestead.setFont(fntLabel);
/*      */     
/*  539 */     if (this.lblBlackLegion == null) this.lblBlackLegion = new JLabel(); 
/*  540 */     this.lblBlackLegion.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_BLACK_LEGION"));
/*  541 */     this.lblBlackLegion.setFont(fntLabel);
/*      */     
/*  543 */     if (this.lblDeathsVigil == null) this.lblDeathsVigil = new JLabel(); 
/*  544 */     this.lblDeathsVigil.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_DEATHS_VIGIL"));
/*  545 */     this.lblDeathsVigil.setFont(fntLabel);
/*      */     
/*  547 */     if (this.lblKymonsChosen == null) this.lblKymonsChosen = new JLabel(); 
/*  548 */     this.lblKymonsChosen.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_KYMONS_CHOSEN"));
/*  549 */     this.lblKymonsChosen.setFont(fntLabel);
/*      */     
/*  551 */     if (this.lblOutcast == null) this.lblOutcast = new JLabel(); 
/*  552 */     this.lblOutcast.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_OUTCAST"));
/*  553 */     this.lblOutcast.setFont(fntLabel);
/*      */     
/*  555 */     if (this.lblCovenUgdenbog == null) this.lblCovenUgdenbog = new JLabel(); 
/*  556 */     this.lblCovenUgdenbog.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_COVEN_UGDENBOG"));
/*  557 */     this.lblCovenUgdenbog.setFont(fntLabel);
/*  558 */     this.lblCovenUgdenbog.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  560 */     if (this.lblBarrowholm == null) this.lblBarrowholm = new JLabel(); 
/*  561 */     this.lblBarrowholm.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_BARROWHOLM"));
/*  562 */     this.lblBarrowholm.setFont(fntLabel);
/*  563 */     this.lblBarrowholm.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  565 */     if (this.lblMalmouth == null) this.lblMalmouth = new JLabel(); 
/*  566 */     this.lblMalmouth.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_MALMOUTH_RESISTANCE"));
/*  567 */     this.lblMalmouth.setFont(fntLabel);
/*  568 */     this.lblMalmouth.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  570 */     if (this.lblCultBysmiel == null) this.lblCultBysmiel = new JLabel(); 
/*  571 */     this.lblCultBysmiel.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_CULT_BYSMIEL"));
/*  572 */     this.lblCultBysmiel.setFont(fntLabel);
/*  573 */     this.lblCultBysmiel.setVisible(GDStashFrame.expansionForgottenGods);
/*      */     
/*  575 */     if (this.lblCultDreeg == null) this.lblCultDreeg = new JLabel(); 
/*  576 */     this.lblCultDreeg.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_CULT_DREEG"));
/*  577 */     this.lblCultDreeg.setFont(fntLabel);
/*  578 */     this.lblCultDreeg.setVisible(GDStashFrame.expansionForgottenGods);
/*      */     
/*  580 */     if (this.lblCultSolael == null) this.lblCultSolael = new JLabel(); 
/*  581 */     this.lblCultSolael.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_CULT_SOLAEL"));
/*  582 */     this.lblCultSolael.setFont(fntLabel);
/*  583 */     this.lblCultSolael.setVisible(GDStashFrame.expansionForgottenGods);
/*      */     
/*  585 */     if (this.lblUndead == null) this.lblUndead = new JLabel(); 
/*  586 */     this.lblUndead.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_UNDEAD"));
/*  587 */     this.lblUndead.setFont(fntLabel);
/*      */     
/*  589 */     if (this.lblBeasts == null) this.lblBeasts = new JLabel(); 
/*  590 */     this.lblBeasts.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_BEASTS"));
/*  591 */     this.lblBeasts.setFont(fntLabel);
/*  592 */     this.lblBeasts.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  594 */     if (this.lblAetherials == null) this.lblAetherials = new JLabel(); 
/*  595 */     this.lblAetherials.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_AETHERIALS"));
/*  596 */     this.lblAetherials.setFont(fntLabel);
/*      */     
/*  598 */     if (this.lblChthonians == null) this.lblChthonians = new JLabel(); 
/*  599 */     this.lblChthonians.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_CHTHONIANS"));
/*  600 */     this.lblChthonians.setFont(fntLabel);
/*      */     
/*  602 */     if (this.lblOutlaws == null) this.lblOutlaws = new JLabel(); 
/*  603 */     this.lblOutlaws.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_CRONLEYS_GANG"));
/*  604 */     this.lblOutlaws.setFont(fntLabel);
/*      */     
/*  606 */     if (this.lblAetherialVanguard == null) this.lblAetherialVanguard = new JLabel(); 
/*  607 */     this.lblAetherialVanguard.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_AETHERIAL_VANGUARD"));
/*  608 */     this.lblAetherialVanguard.setFont(fntLabel);
/*  609 */     this.lblAetherialVanguard.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  611 */     if (this.lblEldritchHorrors == null) this.lblEldritchHorrors = new JLabel(); 
/*  612 */     this.lblEldritchHorrors.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "FACTION_ELDRITCH_HORRORS"));
/*  613 */     this.lblEldritchHorrors.setFont(fntLabel);
/*  614 */     this.lblEldritchHorrors.setVisible(GDStashFrame.expansionForgottenGods);
/*      */     
/*  616 */     if (this.cbSurvivors == null) {
/*  617 */       this.cbSurvivors = new JComboBox<>(factionPositive);
/*  618 */       this.cbSurvivors.addActionListener(new ReputationListener());
/*      */     } else {
/*  620 */       updateLanguage(this.cbSurvivors);
/*      */     } 
/*  622 */     this.cbSurvivors.setFont(fntCombo);
/*      */     
/*  624 */     if (this.cbRovers == null) {
/*  625 */       this.cbRovers = new JComboBox<>(factionPositive);
/*  626 */       this.cbRovers.addActionListener(new ReputationListener());
/*      */     } else {
/*  628 */       updateLanguage(this.cbRovers);
/*      */     } 
/*  630 */     this.cbRovers.setFont(fntCombo);
/*      */     
/*  632 */     if (this.cbHomestead == null) {
/*  633 */       this.cbHomestead = new JComboBox<>(factionPositive);
/*  634 */       this.cbHomestead.addActionListener(new ReputationListener());
/*      */     } else {
/*  636 */       updateLanguage(this.cbHomestead);
/*      */     } 
/*  638 */     this.cbHomestead.setFont(fntCombo);
/*      */     
/*  640 */     if (this.cbBlackLegion == null) {
/*  641 */       this.cbBlackLegion = new JComboBox<>(factionPositive);
/*  642 */       this.cbBlackLegion.addActionListener(new ReputationListener());
/*      */     } else {
/*  644 */       updateLanguage(this.cbBlackLegion);
/*      */     } 
/*  646 */     this.cbBlackLegion.setFont(fntCombo);
/*      */     
/*  648 */     if (this.cbDeathsVigil == null) {
/*  649 */       this.cbDeathsVigil = new JComboBox<>(factionAll);
/*  650 */       this.cbDeathsVigil.addActionListener(new ReputationListener());
/*      */     } else {
/*  652 */       updateLanguage(this.cbDeathsVigil);
/*      */     } 
/*  654 */     this.cbDeathsVigil.setFont(fntCombo);
/*      */     
/*  656 */     if (this.cbKymonsChosen == null) {
/*  657 */       this.cbKymonsChosen = new JComboBox<>(factionAll);
/*  658 */       this.cbKymonsChosen.addActionListener(new ReputationListener());
/*      */     } else {
/*  660 */       updateLanguage(this.cbKymonsChosen);
/*      */     } 
/*  662 */     this.cbKymonsChosen.setFont(fntCombo);
/*      */     
/*  664 */     if (this.cbOutcast == null) {
/*  665 */       this.cbOutcast = new JComboBox<>(factionAll);
/*  666 */       this.cbOutcast.addActionListener(new ReputationListener());
/*      */     } else {
/*  668 */       updateLanguage(this.cbOutcast);
/*      */     } 
/*  670 */     this.cbOutcast.setFont(fntCombo);
/*      */     
/*  672 */     if (this.cbCovenUgdenbog == null) {
/*  673 */       this.cbCovenUgdenbog = new JComboBox<>(factionAll);
/*  674 */       this.cbCovenUgdenbog.addActionListener(new ReputationListener());
/*      */     } else {
/*  676 */       updateLanguage(this.cbCovenUgdenbog);
/*      */     } 
/*  678 */     this.cbCovenUgdenbog.setFont(fntCombo);
/*  679 */     this.cbCovenUgdenbog.setEnabled(GDStashFrame.expansionAshesOfMalmouth);
/*  680 */     this.cbCovenUgdenbog.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  682 */     if (this.cbBarrowholm == null) {
/*  683 */       this.cbBarrowholm = new JComboBox<>(factionAll);
/*  684 */       this.cbBarrowholm.addActionListener(new ReputationListener());
/*      */     } else {
/*  686 */       updateLanguage(this.cbBarrowholm);
/*      */     } 
/*  688 */     this.cbBarrowholm.setFont(fntCombo);
/*  689 */     this.cbBarrowholm.setEnabled(GDStashFrame.expansionAshesOfMalmouth);
/*  690 */     this.cbBarrowholm.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  692 */     if (this.cbMalmouth == null) {
/*  693 */       this.cbMalmouth = new JComboBox<>(factionAll);
/*  694 */       this.cbMalmouth.addActionListener(new ReputationListener());
/*      */     } else {
/*  696 */       updateLanguage(this.cbMalmouth);
/*      */     } 
/*  698 */     this.cbMalmouth.setFont(fntCombo);
/*  699 */     this.cbMalmouth.setEnabled(GDStashFrame.expansionAshesOfMalmouth);
/*  700 */     this.cbMalmouth.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  702 */     if (this.cbCultBysmiel == null) {
/*  703 */       this.cbCultBysmiel = new JComboBox<>(factionPositive);
/*  704 */       this.cbCultBysmiel.addActionListener(new ReputationListener());
/*      */     } else {
/*  706 */       updateLanguage(this.cbCultBysmiel);
/*      */     } 
/*  708 */     this.cbCultBysmiel.setFont(fntCombo);
/*  709 */     this.cbCultBysmiel.setEnabled(GDStashFrame.expansionForgottenGods);
/*  710 */     this.cbCultBysmiel.setVisible(GDStashFrame.expansionForgottenGods);
/*      */     
/*  712 */     if (this.cbCultDreeg == null) {
/*  713 */       this.cbCultDreeg = new JComboBox<>(factionPositive);
/*  714 */       this.cbCultDreeg.addActionListener(new ReputationListener());
/*      */     } else {
/*  716 */       updateLanguage(this.cbCultDreeg);
/*      */     } 
/*  718 */     this.cbCultDreeg.setFont(fntCombo);
/*  719 */     this.cbCultDreeg.setEnabled(GDStashFrame.expansionForgottenGods);
/*  720 */     this.cbCultDreeg.setVisible(GDStashFrame.expansionForgottenGods);
/*      */     
/*  722 */     if (this.cbCultSolael == null) {
/*  723 */       this.cbCultSolael = new JComboBox<>(factionPositive);
/*  724 */       this.cbCultSolael.addActionListener(new ReputationListener());
/*      */     } else {
/*  726 */       updateLanguage(this.cbCultSolael);
/*      */     } 
/*  728 */     this.cbCultSolael.setFont(fntCombo);
/*  729 */     this.cbCultSolael.setEnabled(GDStashFrame.expansionForgottenGods);
/*  730 */     this.cbCultSolael.setVisible(GDStashFrame.expansionForgottenGods);
/*      */     
/*  732 */     if (this.cbUndead == null) {
/*  733 */       this.cbUndead = new JComboBox<>(factionNegative);
/*  734 */       this.cbUndead.addActionListener(new ReputationListener());
/*      */     } else {
/*  736 */       updateLanguage(this.cbUndead);
/*      */     } 
/*  738 */     this.cbUndead.setFont(fntCombo);
/*      */     
/*  740 */     if (this.cbBeasts == null) {
/*  741 */       this.cbBeasts = new JComboBox<>(factionNegative);
/*  742 */       this.cbBeasts.addActionListener(new ReputationListener());
/*      */     } else {
/*  744 */       updateLanguage(this.cbBeasts);
/*      */     } 
/*  746 */     this.cbBeasts.setFont(fntCombo);
/*  747 */     this.cbBeasts.setEnabled(GDStashFrame.expansionAshesOfMalmouth);
/*  748 */     this.cbBeasts.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  750 */     if (this.cbAetherials == null) {
/*  751 */       this.cbAetherials = new JComboBox<>(factionNegative);
/*  752 */       this.cbAetherials.addActionListener(new ReputationListener());
/*      */     } else {
/*  754 */       updateLanguage(this.cbAetherials);
/*      */     } 
/*  756 */     this.cbAetherials.setFont(fntCombo);
/*      */     
/*  758 */     if (this.cbChthonians == null) {
/*  759 */       this.cbChthonians = new JComboBox<>(factionNegative);
/*  760 */       this.cbChthonians.addActionListener(new ReputationListener());
/*      */     } else {
/*  762 */       updateLanguage(this.cbChthonians);
/*      */     } 
/*  764 */     this.cbChthonians.setFont(fntCombo);
/*      */     
/*  766 */     if (this.cbOutlaws == null) {
/*  767 */       this.cbOutlaws = new JComboBox<>(factionNegative);
/*  768 */       this.cbOutlaws.addActionListener(new ReputationListener());
/*      */     } else {
/*  770 */       updateLanguage(this.cbOutlaws);
/*      */     } 
/*  772 */     this.cbOutlaws.setFont(fntCombo);
/*      */     
/*  774 */     if (this.cbAetherialVanguard == null) {
/*  775 */       this.cbAetherialVanguard = new JComboBox<>(factionNegative);
/*  776 */       this.cbAetherialVanguard.addActionListener(new ReputationListener());
/*      */     } else {
/*  778 */       updateLanguage(this.cbAetherialVanguard);
/*      */     } 
/*  780 */     this.cbAetherialVanguard.setFont(fntCombo);
/*  781 */     this.cbAetherialVanguard.setEnabled(GDStashFrame.expansionAshesOfMalmouth);
/*  782 */     this.cbAetherialVanguard.setVisible(GDStashFrame.expansionAshesOfMalmouth);
/*      */     
/*  784 */     if (this.cbEldritchHorrors == null) {
/*  785 */       this.cbEldritchHorrors = new JComboBox<>(factionNegative);
/*  786 */       this.cbEldritchHorrors.addActionListener(new ReputationListener());
/*      */     } else {
/*  788 */       updateLanguage(this.cbEldritchHorrors);
/*      */     } 
/*  790 */     this.cbEldritchHorrors.setFont(fntCombo);
/*  791 */     this.cbEldritchHorrors.setEnabled(GDStashFrame.expansionForgottenGods);
/*  792 */     this.cbEldritchHorrors.setVisible(GDStashFrame.expansionForgottenGods);
/*      */   }
/*      */   
/*      */   private void updateLanguage(JComboBox<String> cb) {
/*  796 */     this.skipListener = true;
/*      */     
/*  798 */     for (int i = 0; i < this.cbReputations.length; i++) {
/*  799 */       if (cb == this.cbReputations[i]) {
/*  800 */         int index = cb.getSelectedIndex();
/*      */         
/*  802 */         cb.removeAllItems();
/*      */         
/*  804 */         String[] factions = factionAll;
/*      */         
/*  806 */         switch (this.reputationTypes[i]) {
/*      */           case 2:
/*  808 */             factions = factionPositive;
/*      */             break;
/*      */ 
/*      */           
/*      */           case 3:
/*  813 */             factions = factionNegative;
/*      */             break;
/*      */         } 
/*      */ 
/*      */         
/*      */         int j;
/*  819 */         for (j = 0; j < factions.length; j++) {
/*  820 */           cb.addItem(factions[j]);
/*      */         }
/*      */         
/*  823 */         cb.setSelectedIndex(index);
/*      */       } 
/*      */     } 
/*      */     
/*  827 */     this.skipListener = false;
/*      */   }
/*      */   
/*      */   private void fillReputation(JComboBox cb) {
/*  831 */     int index = cb.getSelectedIndex();
/*      */     
/*  833 */     for (int i = 0; i < this.cbReputations.length; i++) {
/*  834 */       if (cb == this.cbReputations[i]) {
/*  835 */         switch (this.reputationTypes[i]) {
/*      */           case 1:
/*  837 */             this.reputations[i] = getReputationAll(index);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 2:
/*  842 */             this.reputations[i] = getReputationPositive(index);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 3:
/*  847 */             this.reputations[i] = getReputationNegative(index);
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
/*  859 */     return minReputationLevels[index];
/*      */   }
/*      */   
/*      */   private int getReputationNegative(int index) {
/*  863 */     return minReputationLevels[4 - index];
/*      */   }
/*      */   
/*      */   private int getReputationPositive(int index) {
/*  867 */     return minReputationLevels[index + 4];
/*      */   }
/*      */   private void setReputationAll(JComboBox cb, int reputation) {
/*      */     int i;
/*  871 */     for (i = 0; i < 4; i++) {
/*  872 */       if (reputation <= minReputationLevels[i]) {
/*  873 */         cb.setSelectedIndex(i);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  879 */     for (i = 9; i > 4; i--) {
/*  880 */       if (reputation >= minReputationLevels[i]) {
/*  881 */         cb.setSelectedIndex(i);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  887 */     cb.setSelectedIndex(4);
/*      */   }
/*      */   
/*      */   private void setReputationNegative(JComboBox cb, int reputation) {
/*  891 */     for (int i = 0; i < 4; i++) {
/*  892 */       if (reputation <= minReputationLevels[i]) {
/*  893 */         cb.setSelectedIndex(4 - i);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  899 */     cb.setSelectedIndex(0);
/*      */   }
/*      */   
/*      */   private void setReputationPositive(JComboBox cb, int reputation) {
/*  903 */     for (int i = 9; i > 4; i--) {
/*  904 */       if (reputation >= minReputationLevels[i]) {
/*  905 */         cb.setSelectedIndex(i - 4);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  911 */     cb.setSelectedIndex(0);
/*      */   }
/*      */   
/*      */   private void adjustReputationValues(int faction, JComboBox<String> jcb) {
/*  915 */     this.reputationTypes[faction] = 1;
/*  916 */     if (this.reputations[faction] < 0) this.reputationTypes[faction] = 3; 
/*  917 */     if (this.reputations[faction] > 0) this.reputationTypes[faction] = 2;
/*      */     
/*  919 */     DefaultComboBoxModel<String> model = null;
/*      */     
/*  921 */     switch (this.reputationTypes[faction]) {
/*      */       case 3:
/*  923 */         model = new DefaultComboBoxModel<>(factionNegative);
/*      */         break;
/*      */       
/*      */       case 2:
/*  927 */         model = new DefaultComboBoxModel<>(factionPositive);
/*      */         break;
/*      */       
/*      */       default:
/*  931 */         model = new DefaultComboBoxModel<>(factionAll);
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  936 */     jcb.setModel(model);
/*      */     
/*  938 */     switch (this.reputationTypes[faction]) {
/*      */       case 3:
/*  940 */         setReputationNegative(jcb, this.reputations[faction]);
/*      */         return;
/*      */       
/*      */       case 2:
/*  944 */         setReputationPositive(jcb, this.reputations[faction]);
/*      */         return;
/*      */     } 
/*      */     
/*  948 */     setReputationAll(jcb, this.reputations[faction]);
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
/*  961 */     this.skipListener = true;
/*      */     
/*  963 */     boolean expAoM = GDStashFrame.expansionAshesOfMalmouth;
/*  964 */     boolean expFG = GDStashFrame.expansionForgottenGods;
/*      */     
/*  966 */     if (gdc == null) {
/*  967 */       int i; for (i = 0; i < this.reputations.length; i++) {
/*  968 */         this.reputations[i] = 0;
/*      */       }
/*      */     } else {
/*  971 */       this.reputations = gdc.getFactionReputations();
/*      */       
/*  973 */       expFG = gdc.isForgottenGodsChar();
/*  974 */       expAoM = (expFG || gdc.isAsheshOfMalmouthChar());
/*      */     } 
/*      */     
/*  977 */     if (!expAoM) {
/*  978 */       FACTIONS = FACTIONS_VANILLA;
/*      */     }
/*  980 */     else if (expFG) {
/*  981 */       FACTIONS = FACTIONS_GODS;
/*      */     } else {
/*  983 */       FACTIONS = FACTIONS_MALMOUTH;
/*      */     } 
/*      */ 
/*      */     
/*  987 */     this.lblCovenUgdenbog.setVisible(expAoM);
/*  988 */     this.cbCovenUgdenbog.setEnabled(expAoM);
/*  989 */     this.cbCovenUgdenbog.setVisible(expAoM);
/*      */     
/*  991 */     this.lblBarrowholm.setVisible(expAoM);
/*  992 */     this.cbBarrowholm.setEnabled(expAoM);
/*  993 */     this.cbBarrowholm.setVisible(expAoM);
/*      */     
/*  995 */     this.lblMalmouth.setVisible(expAoM);
/*  996 */     this.cbMalmouth.setEnabled(expAoM);
/*  997 */     this.cbMalmouth.setVisible(expAoM);
/*      */     
/*  999 */     this.lblBeasts.setVisible(expAoM);
/* 1000 */     this.cbBeasts.setEnabled(expAoM);
/* 1001 */     this.cbBeasts.setVisible(expAoM);
/*      */     
/* 1003 */     this.lblAetherialVanguard.setVisible(expAoM);
/* 1004 */     this.cbAetherialVanguard.setEnabled(expAoM);
/* 1005 */     this.cbAetherialVanguard.setVisible(expAoM);
/*      */     
/* 1007 */     this.lblCultBysmiel.setVisible(expFG);
/* 1008 */     this.cbCultBysmiel.setEnabled(expFG);
/* 1009 */     this.cbCultBysmiel.setVisible(expFG);
/*      */     
/* 1011 */     this.lblCultDreeg.setVisible(expFG);
/* 1012 */     this.cbCultDreeg.setEnabled(expFG);
/* 1013 */     this.cbCultDreeg.setVisible(expFG);
/*      */     
/* 1015 */     this.lblCultSolael.setVisible(expFG);
/* 1016 */     this.cbCultSolael.setEnabled(expFG);
/* 1017 */     this.cbCultSolael.setVisible(expFG);
/*      */     
/* 1019 */     this.lblEldritchHorrors.setVisible(expFG);
/* 1020 */     this.cbEldritchHorrors.setEnabled(expFG);
/* 1021 */     this.cbEldritchHorrors.setVisible(expFG);
/*      */     
/* 1023 */     DefaultComboBoxModel<String> model = null;
/*      */     
/* 1025 */     setReputationPositive(this.cbSurvivors, this.reputations[0]);
/* 1026 */     setReputationPositive(this.cbRovers, this.reputations[1]);
/* 1027 */     setReputationPositive(this.cbHomestead, this.reputations[2]);
/* 1028 */     setReputationPositive(this.cbBlackLegion, this.reputations[3]);
/* 1029 */     setReputationAll(this.cbDeathsVigil, this.reputations[4]);
/*      */     
/* 1031 */     setReputationAll(this.cbKymonsChosen, this.reputations[5]);
/*      */     
/* 1033 */     setReputationAll(this.cbOutcast, this.reputations[6]);
/*      */     
/* 1035 */     setReputationPositive(this.cbCovenUgdenbog, this.reputations[7]);
/* 1036 */     setReputationAll(this.cbBarrowholm, this.reputations[8]);
/*      */     
/* 1038 */     setReputationPositive(this.cbMalmouth, this.reputations[9]);
/* 1039 */     setReputationPositive(this.cbCultBysmiel, this.reputations[10]);
/* 1040 */     setReputationPositive(this.cbCultDreeg, this.reputations[11]);
/* 1041 */     setReputationPositive(this.cbCultSolael, this.reputations[12]);
/* 1042 */     setReputationNegative(this.cbUndead, this.reputations[13]);
/* 1043 */     setReputationNegative(this.cbBeasts, this.reputations[14]);
/* 1044 */     setReputationNegative(this.cbAetherials, this.reputations[15]);
/* 1045 */     setReputationNegative(this.cbChthonians, this.reputations[16]);
/* 1046 */     setReputationNegative(this.cbOutlaws, this.reputations[17]);
/* 1047 */     setReputationNegative(this.cbAetherialVanguard, this.reputations[18]);
/* 1048 */     setReputationNegative(this.cbEldritchHorrors, this.reputations[19]);
/*      */     
/* 1050 */     this.skipListener = false;
/*      */   }
/*      */   
/*      */   public void updateChar(GDChar gdc) {
/* 1054 */     if (gdc == null)
/*      */       return; 
/* 1056 */     gdc.setFactionReputations(this.reputations);
/*      */   }
/*      */   
/*      */   public void updateConfig() {
/* 1060 */     initReputationLevels();
/* 1061 */     loadCBText();
/*      */   }
/*      */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\character\GDCharFactionPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */