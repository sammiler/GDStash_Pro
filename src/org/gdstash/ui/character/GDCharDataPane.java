/*     */ package org.gdstash.ui.character;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.*;
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import javax.swing.text.AbstractDocument;
/*     */ import javax.swing.text.DocumentFilter;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.select.CharLenDocFilter;
/*     */ import org.gdstash.ui.select.IntLenDocFilter;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDCharDataPane extends AdjustablePanel {
/*     */   public static final int SEL_SEX_FEMALE = 0;
/*     */   public static final int SEL_SEX_MALE = 1;
/*     */   public static final int SEL_CAMPAIGN_DIFF_NORMAL = 0;
/*     */   public static final int SEL_CAMPAIGN_DIFF_ELITE = 1;
/*     */   public static final int SEL_CAMPAIGN_DIFF_ULTIMATE = 2;
/*     */   public static final int SEL_CRUCIBLE_DIFF_ASPIRANT = 0;
/*     */   public static final int SEL_CRUCIBLE_DIFF_CHALLENGER = 1;
/*     */   public static final int SEL_CRUCIBLE_DIFF_GLADIATOR = 2;
/*     */   
/*     */   private class LevelActionListener implements ActionListener { private LevelActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent ev) {
/*  41 */       if (GDStashFrame.enginePlayer == null)
/*     */         return; 
/*  43 */       JTextField textField = (JTextField)ev.getSource();
/*  44 */       int level = Integer.parseInt(textField.getText());
/*     */       
/*  46 */       int xp = GDStashFrame.enginePlayer.getPlayerXPByLevel(level);
/*     */       
/*  48 */       GDCharDataPane.this.ftExperience.setText(Integer.toString(xp));
/*     */       
/*  50 */       GDCharDataPane.this.updateLevelDependencies(level);
/*     */     } }
/*     */   
/*     */   private class XPActionListener implements ActionListener {
/*     */     private XPActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent ev) {
/*  57 */       if (GDStashFrame.enginePlayer == null)
/*     */         return; 
/*  59 */       JTextField textField = (JTextField)ev.getSource();
/*  60 */       int xp = Integer.parseInt(textField.getText());
/*     */       
/*  62 */       int level = GDStashFrame.enginePlayer.getPlayerLevelByXP(xp);
/*     */       
/*  64 */       GDCharDataPane.this.ftLevel.setText(Integer.toString(level));
/*     */       
/*  66 */       GDCharDataPane.this.updateLevelDependencies(level);
/*     */     }
/*     */   }
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
/*  80 */   private static String[] sexes = null;
/*  81 */   private static String[] campaignDiffs = null;
/*  82 */   private static String[] crucibleDiffs = null; private JLabel lblCharName; private JLabel lblMod; private JLabel lblSex; private JLabel lblHardcore; private JLabel lblDeaths; private JLabel lblMaxCampaignDifficulty; private JLabel lblMaxCrucibleDifficulty; private JLabel lblExperience; private JLabel lblLevel; private JLabel lblMoney; private JFormattedTextField ftCharName; private JCheckBox cbMod;
/*     */   
/*     */   static {
/*  85 */     sexes = new String[2];
/*  86 */     campaignDiffs = new String[3];
/*  87 */     crucibleDiffs = new String[3];
/*     */     
/*  89 */     loadCBText();
/*     */   }
/*     */   private JComboBox<String> cbSex; private JCheckBox cbHardcore; private JTextField ftDeaths; private JComboBox<String> cbMaxCampaignDifficulty; private JComboBox<String> cbMaxCrucibleDifficulty; private JTextField ftExperience; private JTextField ftLevel; private JTextField ftMoney; private int oldLevel; private JPanel pnlMain; private GDCharStatEditPane editPane; private GDCharEditTabbedPane tabPane;
/*     */   private static void loadCBText() {
/*  93 */     sexes[0] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_CHAR_SEX_FEMALE");
/*  94 */     sexes[1] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_CHAR_SEX_MALE");
/*     */     
/*  96 */     campaignDiffs[0] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DIFF_NORMAL");
/*  97 */     campaignDiffs[1] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DIFF_ELITE");
/*  98 */     campaignDiffs[2] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DIFF_ULTIMATE");
/*     */     
/* 100 */     crucibleDiffs[0] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DIFF_ASPIRANT");
/* 101 */     crucibleDiffs[1] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DIFF_CHALLENGER");
/* 102 */     crucibleDiffs[2] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_DIFF_GLADIATOR");
/*     */   }
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
/*     */   
/*     */   public GDCharDataPane(GDCharEditTabbedPane tabPane, GDCharStatEditPane editPane) {
/* 134 */     this.tabPane = tabPane;
/* 135 */     this.editPane = editPane;
/*     */     
/* 137 */     adjustUI();
/*     */ 
/*     */ 
/*     */     
/* 141 */     GroupLayout layout = null;
/* 142 */     GroupLayout.SequentialGroup hGroup = null;
/* 143 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 145 */     layout = new GroupLayout((Container)this);
/* 146 */     setLayout(layout);
/*     */     
/* 148 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 151 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 154 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 157 */     hGroup
/* 158 */       .addGroup(layout.createParallelGroup()
/* 159 */         .addComponent(this.lblCharName)
/* 160 */         .addComponent(this.lblMod)
/* 161 */         .addComponent(this.lblSex)
/* 162 */         .addComponent(this.lblHardcore)
/* 163 */         .addComponent(this.lblDeaths)
/* 164 */         .addComponent(this.lblMaxCampaignDifficulty)
/* 165 */         .addComponent(this.lblMaxCrucibleDifficulty)
/* 166 */         .addComponent(this.lblExperience)
/* 167 */         .addComponent(this.lblLevel)
/* 168 */         .addComponent(this.lblMoney))
/* 169 */       .addGroup(layout.createParallelGroup()
/* 170 */         .addComponent(this.ftCharName)
/* 171 */         .addComponent(this.cbMod)
/* 172 */         .addComponent(this.cbSex)
/* 173 */         .addComponent(this.cbHardcore)
/* 174 */         .addComponent(this.ftDeaths)
/* 175 */         .addComponent(this.cbMaxCampaignDifficulty)
/* 176 */         .addComponent(this.cbMaxCrucibleDifficulty)
/* 177 */         .addComponent(this.ftExperience)
/* 178 */         .addComponent(this.ftLevel)
/* 179 */         .addComponent(this.ftMoney));
/* 180 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 183 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 186 */     vGroup
/* 187 */       .addGroup(layout.createParallelGroup()
/* 188 */         .addComponent(this.lblCharName)
/* 189 */         .addComponent(this.ftCharName))
/* 190 */       .addGroup(layout.createParallelGroup()
/* 191 */         .addComponent(this.lblMod)
/* 192 */         .addComponent(this.cbMod))
/* 193 */       .addGroup(layout.createParallelGroup()
/* 194 */         .addComponent(this.lblSex)
/* 195 */         .addComponent(this.cbSex))
/* 196 */       .addGroup(layout.createParallelGroup()
/* 197 */         .addComponent(this.lblHardcore)
/* 198 */         .addComponent(this.cbHardcore))
/* 199 */       .addGroup(layout.createParallelGroup()
/* 200 */         .addComponent(this.lblDeaths)
/* 201 */         .addComponent(this.ftDeaths))
/* 202 */       .addGroup(layout.createParallelGroup()
/* 203 */         .addComponent(this.lblMaxCampaignDifficulty)
/* 204 */         .addComponent(this.cbMaxCampaignDifficulty))
/* 205 */       .addGroup(layout.createParallelGroup()
/* 206 */         .addComponent(this.lblMaxCrucibleDifficulty)
/* 207 */         .addComponent(this.cbMaxCrucibleDifficulty))
/* 208 */       .addGroup(layout.createParallelGroup()
/* 209 */         .addComponent(this.lblExperience)
/* 210 */         .addComponent(this.ftExperience))
/* 211 */       .addGroup(layout.createParallelGroup()
/* 212 */         .addComponent(this.lblLevel)
/* 213 */         .addComponent(this.ftLevel))
/* 214 */       .addGroup(layout.createParallelGroup()
/* 215 */         .addComponent(this.lblMoney)
/* 216 */         .addComponent(this.ftMoney));
/* 217 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 219 */     layout.linkSize(0, new Component[] { this.lblCharName, this.lblMod });
/* 220 */     layout.linkSize(0, new Component[] { this.lblCharName, this.lblSex });
/* 221 */     layout.linkSize(0, new Component[] { this.lblCharName, this.lblHardcore });
/* 222 */     layout.linkSize(0, new Component[] { this.lblCharName, this.lblDeaths });
/* 223 */     layout.linkSize(0, new Component[] { this.lblCharName, this.lblMaxCampaignDifficulty });
/* 224 */     layout.linkSize(0, new Component[] { this.lblCharName, this.lblMaxCrucibleDifficulty });
/* 225 */     layout.linkSize(0, new Component[] { this.lblCharName, this.lblExperience });
/* 226 */     layout.linkSize(0, new Component[] { this.lblCharName, this.lblLevel });
/* 227 */     layout.linkSize(0, new Component[] { this.lblCharName, this.lblMoney });
/*     */     
/* 229 */     layout.linkSize(0, new Component[] { this.ftCharName, this.cbMod });
/* 230 */     layout.linkSize(0, new Component[] { this.ftCharName, this.cbSex });
/* 231 */     layout.linkSize(0, new Component[] { this.ftCharName, this.cbHardcore });
/* 232 */     layout.linkSize(0, new Component[] { this.ftCharName, this.ftDeaths });
/* 233 */     layout.linkSize(0, new Component[] { this.ftCharName, this.cbMaxCampaignDifficulty });
/* 234 */     layout.linkSize(0, new Component[] { this.ftCharName, this.cbMaxCrucibleDifficulty });
/* 235 */     layout.linkSize(0, new Component[] { this.ftCharName, this.ftExperience });
/* 236 */     layout.linkSize(0, new Component[] { this.ftCharName, this.ftLevel });
/* 237 */     layout.linkSize(0, new Component[] { this.ftCharName, this.ftMoney });
/*     */     
/* 239 */     layout.linkSize(1, new Component[] { this.lblCharName, this.lblMod });
/* 240 */     layout.linkSize(1, new Component[] { this.lblCharName, this.lblSex });
/* 241 */     layout.linkSize(1, new Component[] { this.lblCharName, this.lblHardcore });
/* 242 */     layout.linkSize(1, new Component[] { this.lblCharName, this.lblDeaths });
/* 243 */     layout.linkSize(1, new Component[] { this.lblCharName, this.lblMaxCampaignDifficulty });
/* 244 */     layout.linkSize(1, new Component[] { this.lblCharName, this.lblMaxCrucibleDifficulty });
/* 245 */     layout.linkSize(1, new Component[] { this.lblCharName, this.lblExperience });
/* 246 */     layout.linkSize(1, new Component[] { this.lblCharName, this.lblLevel });
/* 247 */     layout.linkSize(1, new Component[] { this.lblCharName, this.lblMoney });
/*     */     
/* 249 */     layout.linkSize(1, new Component[] { this.lblCharName, this.ftCharName });
/* 250 */     layout.linkSize(1, new Component[] { this.lblCharName, this.cbMod });
/* 251 */     layout.linkSize(1, new Component[] { this.lblCharName, this.cbSex });
/* 252 */     layout.linkSize(1, new Component[] { this.lblCharName, this.cbHardcore });
/* 253 */     layout.linkSize(1, new Component[] { this.lblCharName, this.ftDeaths });
/* 254 */     layout.linkSize(1, new Component[] { this.lblCharName, this.cbMaxCampaignDifficulty });
/* 255 */     layout.linkSize(1, new Component[] { this.lblCharName, this.cbMaxCrucibleDifficulty });
/* 256 */     layout.linkSize(1, new Component[] { this.lblCharName, this.ftExperience });
/* 257 */     layout.linkSize(1, new Component[] { this.lblCharName, this.ftLevel });
/* 258 */     layout.linkSize(1, new Component[] { this.lblCharName, this.ftMoney });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 263 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 264 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/* 265 */     if (fntButton == null) fntButton = fntLabel; 
/* 266 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 267 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 268 */     Font fntCombo = UIManager.getDefaults().getFont("ComboBox.font");
/* 269 */     if (fntCombo == null) fntCombo = fntLabel; 
/* 270 */     Font fntFText = UIManager.getDefaults().getFont("FormattedTextField.font");
/* 271 */     if (fntFText == null) fntFText = fntLabel; 
/* 272 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 273 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 275 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 276 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 277 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 278 */     fntCombo = fntCombo.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 279 */     fntFText = fntFText.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 280 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 282 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 283 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 284 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 285 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_DATA"));
/* 286 */     text.setTitleFont(fntBorder);
/*     */     
/* 288 */     setBorder(text);
/*     */     
/* 290 */     loadCBText();
/*     */     
/* 292 */     if (this.lblCharName == null) this.lblCharName = new JLabel(); 
/* 293 */     this.lblCharName.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_NAME"));
/* 294 */     this.lblCharName.setFont(fntLabel);
/*     */     
/* 296 */     if (this.ftCharName == null) {
/* 297 */       this.ftCharName = new JFormattedTextField();
/* 298 */       CharLenDocFilter charLenDocFilter = new CharLenDocFilter(30);
/* 299 */       AbstractDocument doc = (AbstractDocument)this.ftCharName.getDocument();
/* 300 */       doc.setDocumentFilter((DocumentFilter)charLenDocFilter);
/*     */     } 
/* 302 */     this.ftCharName.setFont(fntFText);
/*     */     
/* 304 */     if (this.lblMod == null) this.lblMod = new JLabel(); 
/* 305 */     this.lblMod.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_MODDED"));
/* 306 */     this.lblMod.setFont(fntLabel);
/*     */     
/* 308 */     if (this.cbMod == null) this.cbMod = new JCheckBox(); 
/* 309 */     this.cbMod.setFont(fntCheck);
/*     */     
/* 311 */     if (this.lblSex == null) this.lblSex = new JLabel(); 
/* 312 */     this.lblSex.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_SEX"));
/* 313 */     this.lblSex.setFont(fntLabel);
/*     */     
/* 315 */     if (this.cbSex == null) {
/* 316 */       this.cbSex = new JComboBox<>(sexes);
/*     */     }
/*     */     else {
/*     */       
/* 320 */       updateLanguage(this.cbSex);
/*     */     } 
/* 322 */     this.cbSex.setFont(fntCombo);
/* 323 */     this.cbSex.setMaximumSize(new Dimension(1000, 3 * GDStashFrame.iniConfig.sectUI.fontSize));
/*     */     
/* 325 */     if (this.lblHardcore == null) this.lblHardcore = new JLabel(); 
/* 326 */     this.lblHardcore.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_HARDCORE"));
/* 327 */     this.lblHardcore.setFont(fntLabel);
/*     */     
/* 329 */     if (this.cbHardcore == null) {
/* 330 */       this.cbHardcore = new JCheckBox();
/*     */     }
/* 332 */     this.cbHardcore.setFont(fntCheck);
/*     */     
/* 334 */     if (this.lblDeaths == null) this.lblDeaths = new JLabel(); 
/* 335 */     this.lblDeaths.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_STAT_DEATHS"));
/* 336 */     this.lblDeaths.setFont(fntLabel);
/*     */     
/* 338 */     if (this.ftDeaths == null) {
/* 339 */       this.ftDeaths = new JFormattedTextField();
/* 340 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(4);
/* 341 */       AbstractDocument doc = (AbstractDocument)this.ftDeaths.getDocument();
/* 342 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */     } 
/* 344 */     this.ftDeaths.setFont(fntFText);
/*     */     
/* 346 */     if (this.lblMaxCampaignDifficulty == null) this.lblMaxCampaignDifficulty = new JLabel(); 
/* 347 */     this.lblMaxCampaignDifficulty.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_DIFFICULTY_MAX"));
/* 348 */     this.lblMaxCampaignDifficulty.setFont(fntLabel);
/*     */     
/* 350 */     if (this.cbMaxCampaignDifficulty == null) {
/* 351 */       this.cbMaxCampaignDifficulty = new JComboBox<>(campaignDiffs);
/*     */     }
/*     */     else {
/*     */       
/* 355 */       updateLanguage(this.cbMaxCampaignDifficulty);
/*     */     } 
/* 357 */     this.cbMaxCampaignDifficulty.setFont(fntCombo);
/* 358 */     this.cbMaxCampaignDifficulty.setMaximumSize(new Dimension(1000, 3 * GDStashFrame.iniConfig.sectUI.fontSize));
/*     */     
/* 360 */     if (this.lblMaxCrucibleDifficulty == null) this.lblMaxCrucibleDifficulty = new JLabel(); 
/* 361 */     this.lblMaxCrucibleDifficulty.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_DIFFICULTY_MAX_CRUCIBLE"));
/* 362 */     this.lblMaxCrucibleDifficulty.setFont(fntLabel);
/*     */     
/* 364 */     if (this.cbMaxCrucibleDifficulty == null) {
/* 365 */       this.cbMaxCrucibleDifficulty = new JComboBox<>(crucibleDiffs);
/*     */     } else {
/* 367 */       updateLanguage(this.cbMaxCrucibleDifficulty);
/*     */     } 
/* 369 */     this.cbMaxCrucibleDifficulty.setFont(fntCombo);
/* 370 */     this.cbMaxCrucibleDifficulty.setMaximumSize(new Dimension(1000, 3 * GDStashFrame.iniConfig.sectUI.fontSize));
/*     */     
/* 372 */     if (this.lblExperience == null) this.lblExperience = new JLabel(); 
/* 373 */     this.lblExperience.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_EXPERIENCE"));
/* 374 */     this.lblExperience.setFont(fntLabel);
/*     */     
/* 376 */     if (this.ftExperience == null) {
/* 377 */       this.ftExperience = new JFormattedTextField();
/* 378 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(9);
/* 379 */       AbstractDocument doc = (AbstractDocument)this.ftExperience.getDocument();
/* 380 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */       
/* 382 */       this.ftExperience.addActionListener(new XPActionListener());
/*     */     } 
/* 384 */     this.ftExperience.setFont(fntFText);
/*     */     
/* 386 */     if (this.lblLevel == null) this.lblLevel = new JLabel(); 
/* 387 */     this.lblLevel.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_LEVEL"));
/* 388 */     this.lblLevel.setFont(fntLabel);
/*     */     
/* 390 */     if (this.ftLevel == null) {
/* 391 */       this.ftLevel = new JFormattedTextField();
/* 392 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(3);
/* 393 */       AbstractDocument doc = (AbstractDocument)this.ftLevel.getDocument();
/* 394 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */       
/* 396 */       this.ftLevel.addActionListener(new LevelActionListener());
/*     */     } 
/* 398 */     this.ftLevel.setFont(fntFText);
/*     */     
/* 400 */     if (this.lblMoney == null) this.lblMoney = new JLabel(); 
/* 401 */     this.lblMoney.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_MONEY"));
/* 402 */     this.lblMoney.setFont(fntLabel);
/*     */     
/* 404 */     if (this.ftMoney == null) {
/* 405 */       this.ftMoney = new JFormattedTextField();
/* 406 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(9);
/* 407 */       AbstractDocument doc = (AbstractDocument)this.ftMoney.getDocument();
/* 408 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */     } 
/* 410 */     this.ftMoney.setFont(fntFText);
/*     */   }
/*     */   
/*     */   private void updateLanguage(JComboBox<String> cb) {
/* 414 */     String[] texts = null;
/*     */     
/* 416 */     if (cb == this.cbSex) texts = sexes; 
/* 417 */     if (cb == this.cbMaxCampaignDifficulty) texts = campaignDiffs; 
/* 418 */     if (cb == this.cbMaxCrucibleDifficulty) texts = crucibleDiffs;
/*     */     
/* 420 */     if (texts != null) {
/* 421 */       int index = cb.getSelectedIndex();
/*     */       
/* 423 */       cb.removeAllItems();
/*     */       int i;
/* 425 */       for (i = 0; i < texts.length; i++) {
/* 426 */         cb.addItem(texts[i]);
/*     */       }
/*     */       
/* 429 */       cb.setSelectedIndex(index);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateLevelDependencies(int level) {
/* 434 */     if (this.oldLevel != level) {
/* 435 */       int statPoints = this.editPane.getStatPoints();
/* 436 */       int statDelta = GDStashFrame.engineLevel.getStatPointsForRange(this.oldLevel, level);
/*     */       
/* 438 */       int skillPoints = this.tabPane.getSkillPoints();
/* 439 */       int skillDelta = GDStashFrame.engineLevel.getSkillPointsForRange(this.oldLevel, level);
/*     */       
/* 441 */       if (this.oldLevel > level) {
/* 442 */         if (statDelta > statPoints) statDelta = statPoints; 
/* 443 */         if (skillDelta > skillPoints) skillDelta = skillPoints;
/*     */         
/* 445 */         statDelta = -1 * statDelta;
/* 446 */         skillDelta = -1 * skillDelta;
/*     */       } 
/*     */       
/* 449 */       this.editPane.setStatPoints(statPoints + statDelta);
/* 450 */       this.tabPane.setSkillPoints(skillPoints + skillDelta);
/*     */       
/* 452 */       this.oldLevel = level;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 457 */     if (gdc == null) {
/* 458 */       this.ftCharName.setText("");
/* 459 */       this.cbMod.setSelected(false);
/* 460 */       this.cbSex.setSelectedIndex(1);
/* 461 */       this.cbHardcore.setSelected(false);
/* 462 */       this.ftDeaths.setText("0");
/* 463 */       this.cbMaxCampaignDifficulty.setSelectedIndex(0);
/* 464 */       this.cbMaxCrucibleDifficulty.setSelectedIndex(0);
/* 465 */       this.ftExperience.setText("0");
/* 466 */       this.ftLevel.setText("0");
/* 467 */       this.ftMoney.setText("0");
/*     */     } else {
/* 469 */       this.ftCharName.setText(gdc.getCharName());
/* 470 */       this.cbMod.setSelected(!gdc.isInMainQuest());
/* 471 */       this.cbSex.setSelectedIndex(gdc.getSex());
/* 472 */       this.cbHardcore.setSelected(gdc.isHardcore());
/* 473 */       this.ftDeaths.setText(Integer.toString(gdc.getDeaths()));
/* 474 */       this.cbMaxCampaignDifficulty.setSelectedIndex(gdc.getGreatestCampaignDifficulty());
/*     */       
/* 476 */       byte crucibleDifficulty = 0;
/* 477 */       switch (gdc.getGreatestCrucibleDifficulty()) {
/*     */         case 0:
/* 479 */           crucibleDifficulty = 0;
/*     */           break;
/*     */ 
/*     */         
/*     */         case 1:
/* 484 */           crucibleDifficulty = 1;
/*     */           break;
/*     */ 
/*     */         
/*     */         case 2:
/* 489 */           crucibleDifficulty = 2;
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 494 */       this.cbMaxCrucibleDifficulty.setSelectedIndex(crucibleDifficulty);
/*     */       
/* 496 */       this.ftExperience.setText(Integer.toString(gdc.getExperience()));
/* 497 */       this.ftLevel.setText(Integer.toString(gdc.getLevel()));
/* 498 */       this.ftMoney.setText(Integer.toString(gdc.getMoney()));
/*     */       
/* 500 */       this.oldLevel = Integer.parseInt(this.ftLevel.getText());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateChar(GDChar gdc) {
/* 505 */     if (gdc == null)
/*     */       return; 
/* 507 */     gdc.setCharName(this.ftCharName.getText());
/* 508 */     gdc.setInMainQuest(!this.cbMod.isSelected());
/* 509 */     gdc.setSex((byte)this.cbSex.getSelectedIndex());
/* 510 */     gdc.setHardcore(this.cbHardcore.isSelected());
/* 511 */     gdc.setDeaths(Integer.parseInt(this.ftDeaths.getText()));
/* 512 */     gdc.setGreatestCampaignDifficulty((byte)this.cbMaxCampaignDifficulty.getSelectedIndex());
/*     */     
/* 514 */     byte crucibleDifficulty = 0;
/* 515 */     switch (this.cbMaxCrucibleDifficulty.getSelectedIndex()) {
/*     */       case 0:
/* 517 */         crucibleDifficulty = 0;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 522 */         crucibleDifficulty = 1;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 527 */         crucibleDifficulty = 2;
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 532 */     gdc.setGreatestCrucibleDifficulty(crucibleDifficulty);
/*     */     
/* 534 */     gdc.setExperience(Integer.parseInt(this.ftExperience.getText()));
/* 535 */     gdc.setLevel(Integer.parseInt(this.ftLevel.getText()));
/* 536 */     gdc.setMoney(Integer.parseInt(this.ftMoney.getText()));
/*     */   }
/*     */   
/*     */   public String getCharName() {
/* 540 */     return this.ftCharName.getText();
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharDataPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */