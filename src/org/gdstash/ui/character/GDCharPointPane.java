/*     */ package org.gdstash.ui.character;
/*     */ import java.awt.*;
/*     */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*     */ import javax.swing.*;
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
/*     */ import javax.swing.text.DocumentFilter;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.ui.GDStashFrame;
import org.gdstash.ui.select.IntLenDocFilter;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
import org.gdstash.util.GDImagePool;
import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDCharPointPane extends AdjustablePanel {
/*     */   private JLabel lblStatPoints;
/*     */   private JLabel lblSkillPoints;
/*     */   private JLabel lblDevotionPoints;
/*     */   private JLabel lblTributePoints;
/*     */   private JLabel lblSkillReclaimPoints;
/*     */   private JLabel lblDevotionReclaimPoints;
/*     */   private JLabel lblMastery1Points;
/*     */   private JLabel lblMastery2Points;
/*     */   private JTextField ftStatPoints;
/*     */   private JTextField ftSkillPoints;
/*     */   private JTextField ftDevotionPoints;
/*     */   private JTextField ftTributePoints;
/*     */   private JTextField ftSkillReclaimPoints;
/*     */   private JTextField ftDevotionReclaimPoints;
/*     */   private JTextField ftMastery1Points;
/*     */   private JButton btnMastery1Reset;
/*     */   private JTextField ftMastery2Points;
/*     */   private JButton btnMastery2Reset;
/*     */   private GDChar.SkillInfo[] infos;
/*     */   private GDChar gdc;
/*     */   private GDCharEditTabbedPane tabPane;
/*     */   
/*     */   private class ResetMasteryListener implements ActionListener {
/*     */     public ResetMasteryListener(int index) {
/*  41 */       this.index = index;
/*     */     }
/*     */     private int index;
/*     */     
/*     */     public void actionPerformed(ActionEvent ev) {
/*  46 */       if (GDCharPointPane.this.gdc == null)
/*  47 */         return;  if (GDCharPointPane.this.infos == null)
/*  48 */         return;  if (GDCharPointPane.this.infos.length < this.index + 1)
/*     */         return; 
/*  50 */       String masteryID = (GDCharPointPane.this.infos[this.index]).id;
/*     */       
/*  52 */       if (masteryID == null)
/*     */         return; 
/*  54 */       GDCharPointPane.this.tabPane.resetMastery(masteryID);
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
/*     */   public GDCharPointPane(GDCharEditTabbedPane tabPane) {
/*  82 */     this.tabPane = tabPane;
/*     */     
/*  84 */     adjustUI();
/*     */ 
/*     */ 
/*     */     
/*  88 */     GroupLayout layout = null;
/*  89 */     GroupLayout.SequentialGroup hGroup = null;
/*  90 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  92 */     layout = new GroupLayout((Container)this);
/*  93 */     setLayout(layout);
/*     */     
/*  95 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  98 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 101 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 104 */     hGroup
/* 105 */       .addGroup(layout.createParallelGroup()
/* 106 */         .addComponent(this.lblStatPoints)
/* 107 */         .addComponent(this.lblSkillPoints)
/* 108 */         .addComponent(this.lblDevotionPoints)
/* 109 */         .addComponent(this.lblTributePoints)
/* 110 */         .addComponent(this.lblSkillReclaimPoints)
/* 111 */         .addComponent(this.lblDevotionReclaimPoints)
/* 112 */         .addComponent(this.lblMastery1Points)
/* 113 */         .addComponent(this.lblMastery2Points))
/* 114 */       .addGroup(layout.createParallelGroup()
/* 115 */         .addComponent(this.ftStatPoints)
/* 116 */         .addComponent(this.ftSkillPoints)
/* 117 */         .addComponent(this.ftDevotionPoints)
/* 118 */         .addComponent(this.ftTributePoints)
/* 119 */         .addComponent(this.ftSkillReclaimPoints)
/* 120 */         .addComponent(this.ftDevotionReclaimPoints)
/* 121 */         .addComponent(this.ftMastery1Points)
/* 122 */         .addComponent(this.ftMastery2Points))
/* 123 */       .addGroup(layout.createParallelGroup()
/* 124 */         .addComponent(this.btnMastery1Reset)
/* 125 */         .addComponent(this.btnMastery2Reset));
/*     */     
/* 127 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 130 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 133 */     vGroup
/* 134 */       .addGroup(layout.createParallelGroup()
/* 135 */         .addComponent(this.lblStatPoints)
/* 136 */         .addComponent(this.ftStatPoints))
/* 137 */       .addGroup(layout.createParallelGroup()
/* 138 */         .addComponent(this.lblSkillPoints)
/* 139 */         .addComponent(this.ftSkillPoints))
/* 140 */       .addGroup(layout.createParallelGroup()
/* 141 */         .addComponent(this.lblDevotionPoints)
/* 142 */         .addComponent(this.ftDevotionPoints))
/* 143 */       .addGroup(layout.createParallelGroup()
/* 144 */         .addComponent(this.lblTributePoints)
/* 145 */         .addComponent(this.ftTributePoints))
/* 146 */       .addGroup(layout.createParallelGroup()
/* 147 */         .addComponent(this.lblSkillReclaimPoints)
/* 148 */         .addComponent(this.ftSkillReclaimPoints))
/* 149 */       .addGroup(layout.createParallelGroup()
/* 150 */         .addComponent(this.lblDevotionReclaimPoints)
/* 151 */         .addComponent(this.ftDevotionReclaimPoints))
/* 152 */       .addGroup(layout.createParallelGroup()
/* 153 */         .addComponent(this.lblMastery1Points)
/* 154 */         .addComponent(this.ftMastery1Points)
/* 155 */         .addComponent(this.btnMastery1Reset))
/* 156 */       .addGroup(layout.createParallelGroup()
/* 157 */         .addComponent(this.lblMastery2Points)
/* 158 */         .addComponent(this.ftMastery2Points)
/* 159 */         .addComponent(this.btnMastery2Reset));
/* 160 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 162 */     layout.linkSize(0, new Component[] { this.lblStatPoints, this.lblSkillPoints });
/* 163 */     layout.linkSize(0, new Component[] { this.lblStatPoints, this.lblDevotionPoints });
/* 164 */     layout.linkSize(0, new Component[] { this.lblStatPoints, this.lblTributePoints });
/* 165 */     layout.linkSize(0, new Component[] { this.lblStatPoints, this.lblSkillReclaimPoints });
/* 166 */     layout.linkSize(0, new Component[] { this.lblStatPoints, this.lblDevotionReclaimPoints });
/* 167 */     layout.linkSize(0, new Component[] { this.lblStatPoints, this.lblMastery1Points });
/* 168 */     layout.linkSize(0, new Component[] { this.lblStatPoints, this.lblMastery2Points });
/*     */     
/* 170 */     layout.linkSize(0, new Component[] { this.ftStatPoints, this.ftSkillPoints });
/* 171 */     layout.linkSize(0, new Component[] { this.ftStatPoints, this.ftDevotionPoints });
/* 172 */     layout.linkSize(0, new Component[] { this.ftStatPoints, this.ftTributePoints });
/* 173 */     layout.linkSize(0, new Component[] { this.ftStatPoints, this.ftSkillReclaimPoints });
/* 174 */     layout.linkSize(0, new Component[] { this.ftStatPoints, this.ftDevotionReclaimPoints });
/* 175 */     layout.linkSize(0, new Component[] { this.ftStatPoints, this.ftMastery1Points });
/* 176 */     layout.linkSize(0, new Component[] { this.ftStatPoints, this.ftMastery2Points });
/*     */     
/* 178 */     layout.linkSize(1, new Component[] { this.lblStatPoints, this.lblSkillPoints });
/* 179 */     layout.linkSize(1, new Component[] { this.lblStatPoints, this.lblDevotionPoints });
/* 180 */     layout.linkSize(1, new Component[] { this.lblStatPoints, this.lblTributePoints });
/* 181 */     layout.linkSize(1, new Component[] { this.lblStatPoints, this.lblSkillReclaimPoints });
/* 182 */     layout.linkSize(1, new Component[] { this.lblStatPoints, this.lblDevotionReclaimPoints });
/* 183 */     layout.linkSize(1, new Component[] { this.lblStatPoints, this.lblMastery1Points });
/* 184 */     layout.linkSize(1, new Component[] { this.lblStatPoints, this.lblMastery2Points });
/*     */     
/* 186 */     layout.linkSize(1, new Component[] { this.lblStatPoints, this.ftStatPoints });
/* 187 */     layout.linkSize(1, new Component[] { this.lblStatPoints, this.ftSkillPoints });
/* 188 */     layout.linkSize(1, new Component[] { this.lblStatPoints, this.ftDevotionPoints });
/* 189 */     layout.linkSize(1, new Component[] { this.lblStatPoints, this.ftTributePoints });
/* 190 */     layout.linkSize(1, new Component[] { this.lblStatPoints, this.ftSkillReclaimPoints });
/* 191 */     layout.linkSize(1, new Component[] { this.lblStatPoints, this.ftDevotionReclaimPoints });
/* 192 */     layout.linkSize(1, new Component[] { this.lblStatPoints, this.ftMastery1Points });
/* 193 */     layout.linkSize(1, new Component[] { this.lblStatPoints, this.ftMastery2Points });
/*     */     
/* 195 */     layout.linkSize(1, new Component[] { this.lblStatPoints, this.btnMastery1Reset });
/* 196 */     layout.linkSize(1, new Component[] { this.lblStatPoints, this.btnMastery2Reset });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 201 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 202 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/* 203 */     if (fntButton == null) fntButton = fntLabel; 
/* 204 */     Font fntFText = UIManager.getDefaults().getFont("FormattedTextField.font");
/* 205 */     if (fntFText == null) fntFText = fntLabel; 
/* 206 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 207 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 209 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 210 */     fntFText = fntFText.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 211 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 213 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 214 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 215 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 216 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_POINTS"));
/* 217 */     text.setTitleFont(fntBorder);
/*     */     
/* 219 */     setBorder(text);
/*     */     
/* 221 */     if (this.lblStatPoints == null) this.lblStatPoints = new JLabel(); 
/* 222 */     this.lblStatPoints.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_POINTS_STAT"));
/* 223 */     this.lblStatPoints.setFont(fntLabel);
/*     */     
/* 225 */     if (this.ftStatPoints == null) {
/* 226 */       this.ftStatPoints = new JFormattedTextField();
/* 227 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(3);
/* 228 */       AbstractDocument doc = (AbstractDocument)this.ftStatPoints.getDocument();
/* 229 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */     } 
/* 231 */     this.ftStatPoints.setPreferredSize(new Dimension(GDStashFrame.iniConfig.sectUI.fontSize * 4, GDStashFrame.iniConfig.sectUI.fontSize * 2));
/* 232 */     this.ftStatPoints.setFont(fntFText);
/*     */     
/* 234 */     if (this.lblSkillPoints == null) this.lblSkillPoints = new JLabel(); 
/* 235 */     this.lblSkillPoints.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_POINTS_SKILL"));
/* 236 */     this.lblSkillPoints.setFont(fntLabel);
/*     */     
/* 238 */     if (this.ftSkillPoints == null) {
/* 239 */       this.ftSkillPoints = new JFormattedTextField();
/* 240 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(3);
/* 241 */       AbstractDocument doc = (AbstractDocument)this.ftSkillPoints.getDocument();
/* 242 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */     } 
/* 244 */     this.ftSkillPoints.setFont(fntFText);
/*     */     
/* 246 */     if (this.lblDevotionPoints == null) this.lblDevotionPoints = new JLabel(); 
/* 247 */     this.lblDevotionPoints.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_POINTS_DEVOTION"));
/* 248 */     this.lblDevotionPoints.setFont(fntLabel);
/*     */     
/* 250 */     if (this.ftDevotionPoints == null) {
/* 251 */       this.ftDevotionPoints = new JFormattedTextField();
/* 252 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(3);
/* 253 */       AbstractDocument doc = (AbstractDocument)this.ftDevotionPoints.getDocument();
/* 254 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */     } 
/* 256 */     this.ftDevotionPoints.setFont(fntFText);
/*     */     
/* 258 */     if (this.lblTributePoints == null) this.lblTributePoints = new JLabel(); 
/* 259 */     this.lblTributePoints.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_POINTS_TRIBUTE"));
/* 260 */     this.lblTributePoints.setFont(fntLabel);
/*     */     
/* 262 */     if (this.ftTributePoints == null) {
/* 263 */       this.ftTributePoints = new JFormattedTextField();
/* 264 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(3);
/* 265 */       AbstractDocument doc = (AbstractDocument)this.ftTributePoints.getDocument();
/* 266 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */     } 
/* 268 */     this.ftTributePoints.setFont(fntFText);
/*     */     
/* 270 */     if (this.lblSkillReclaimPoints == null) this.lblSkillReclaimPoints = new JLabel(); 
/* 271 */     this.lblSkillReclaimPoints.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_POINTS_SKILL_REC"));
/* 272 */     this.lblSkillReclaimPoints.setFont(fntLabel);
/*     */     
/* 274 */     if (this.ftSkillReclaimPoints == null) {
/* 275 */       this.ftSkillReclaimPoints = new JFormattedTextField();
/* 276 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(3);
/* 277 */       AbstractDocument doc = (AbstractDocument)this.ftSkillReclaimPoints.getDocument();
/* 278 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */     } 
/* 280 */     this.ftSkillReclaimPoints.setFont(fntFText);
/*     */     
/* 282 */     if (this.lblDevotionReclaimPoints == null) this.lblDevotionReclaimPoints = new JLabel(); 
/* 283 */     this.lblDevotionReclaimPoints.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_POINTS_DEVOTION_REC"));
/* 284 */     this.lblDevotionReclaimPoints.setFont(fntLabel);
/*     */     
/* 286 */     if (this.ftDevotionReclaimPoints == null) {
/* 287 */       this.ftDevotionReclaimPoints = new JFormattedTextField();
/* 288 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(3);
/* 289 */       AbstractDocument doc = (AbstractDocument)this.ftDevotionReclaimPoints.getDocument();
/* 290 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */     } 
/* 292 */     this.ftDevotionReclaimPoints.setFont(fntFText);
/*     */     
/* 294 */     if (this.lblDevotionReclaimPoints == null) this.lblDevotionReclaimPoints = new JLabel(); 
/* 295 */     this.lblDevotionReclaimPoints.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_POINTS_DEVOTION_REC"));
/* 296 */     this.lblDevotionReclaimPoints.setFont(fntLabel);
/*     */     
/* 298 */     if (this.ftDevotionReclaimPoints == null) {
/* 299 */       this.ftDevotionReclaimPoints = new JFormattedTextField();
/* 300 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(3);
/* 301 */       AbstractDocument doc = (AbstractDocument)this.ftDevotionReclaimPoints.getDocument();
/* 302 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */     } 
/* 304 */     this.ftDevotionReclaimPoints.setFont(fntFText);
/*     */     
/* 306 */     if (this.lblMastery1Points == null) this.lblMastery1Points = new JLabel(); 
/* 307 */     if (this.infos == null) {
/* 308 */       this.lblMastery1Points.setText("");
/*     */     }
/* 310 */     else if (this.infos.length >= 1) {
/* 311 */       Object[] args = { (this.infos[0]).name };
/* 312 */       String s = GDMsgFormatter.format(GDMsgFormatter.rbUI, "TXT_CHAR_MASTERY_POINTS", args);
/*     */       
/* 314 */       this.lblMastery1Points.setText(s);
/*     */     } 
/*     */     
/* 317 */     this.lblMastery1Points.setFont(fntLabel);
/*     */     
/* 319 */     if (this.ftMastery1Points == null) {
/* 320 */       this.ftMastery1Points = new JFormattedTextField();
/* 321 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(2);
/* 322 */       AbstractDocument doc = (AbstractDocument)this.ftMastery1Points.getDocument();
/* 323 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */     } 
/* 325 */     this.ftMastery1Points.setFont(fntFText);
/*     */     
/* 327 */     if (this.btnMastery1Reset == null) {
/* 328 */       this.btnMastery1Reset = new JButton();
/*     */       
/* 330 */       this.btnMastery1Reset.addActionListener(new ResetMasteryListener(0));
/*     */     } 
/* 332 */     this.btnMastery1Reset.setIcon(GDImagePool.iconCharEditMasteryReset16);
/* 333 */     this.btnMastery1Reset.setFont(fntButton);
/*     */     
/* 335 */     if (this.lblMastery2Points == null) this.lblMastery2Points = new JLabel(); 
/* 336 */     if (this.infos == null) {
/* 337 */       this.lblMastery2Points.setText("");
/*     */     }
/* 339 */     else if (this.infos.length == 1) {
/* 340 */       this.lblMastery2Points.setText("");
/*     */     } else {
/* 342 */       Object[] args = { (this.infos[1]).name };
/* 343 */       String s = GDMsgFormatter.format(GDMsgFormatter.rbUI, "TXT_CHAR_MASTERY_POINTS", args);
/*     */       
/* 345 */       this.lblMastery2Points.setText(s);
/*     */     } 
/*     */     
/* 348 */     this.lblMastery2Points.setFont(fntLabel);
/*     */     
/* 350 */     if (this.ftMastery2Points == null) {
/* 351 */       this.ftMastery2Points = new JFormattedTextField();
/* 352 */       IntLenDocFilter intLenDocFilter = new IntLenDocFilter(2);
/* 353 */       AbstractDocument doc = (AbstractDocument)this.ftMastery2Points.getDocument();
/* 354 */       doc.setDocumentFilter((DocumentFilter)intLenDocFilter);
/*     */     } 
/* 356 */     this.ftMastery2Points.setFont(fntFText);
/*     */     
/* 358 */     if (this.btnMastery2Reset == null) {
/* 359 */       this.btnMastery2Reset = new JButton();
/*     */       
/* 361 */       this.btnMastery2Reset.addActionListener(new ResetMasteryListener(1));
/*     */     } 
/* 363 */     this.btnMastery2Reset.setIcon(GDImagePool.iconCharEditMasteryReset16);
/* 364 */     this.btnMastery2Reset.setFont(fntButton);
/*     */   }
/*     */   
/*     */   private JPanel buildMasteryPanel(JLabel label, JFormattedTextField textField, JButton btnReset) {
/* 368 */     JPanel panel = new JPanel();
/*     */     
/* 370 */     GroupLayout layout = null;
/* 371 */     GroupLayout.SequentialGroup hGroup = null;
/* 372 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 374 */     layout = new GroupLayout(panel);
/* 375 */     panel.setLayout(layout);
/*     */     
/* 377 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 380 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 383 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 386 */     hGroup
/* 387 */       .addGroup(layout.createParallelGroup()
/* 388 */         .addComponent(label))
/* 389 */       .addGroup(layout.createParallelGroup()
/* 390 */         .addComponent(textField))
/* 391 */       .addGroup(layout.createParallelGroup()
/* 392 */         .addComponent(btnReset));
/* 393 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 396 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 399 */     vGroup
/* 400 */       .addGroup(layout.createParallelGroup()
/* 401 */         .addComponent(label)
/* 402 */         .addComponent(textField)
/* 403 */         .addComponent(btnReset));
/* 404 */     layout.setVerticalGroup(vGroup);
/*     */ 
/*     */ 
/*     */     
/* 408 */     layout.linkSize(1, new Component[] { label, textField });
/* 409 */     layout.linkSize(1, new Component[] { label, btnReset });
/*     */     
/* 411 */     return panel;
/*     */   }
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 415 */     this.gdc = gdc;
/*     */     
/* 417 */     if (gdc == null) {
/* 418 */       this.ftStatPoints.setText("0");
/* 419 */       this.ftSkillPoints.setText(Integer.toString(this.tabPane.getSkillPoints()));
/* 420 */       this.ftDevotionPoints.setText("0");
/* 421 */       this.ftTributePoints.setText("0");
/* 422 */       this.ftSkillReclaimPoints.setText("0");
/* 423 */       this.ftDevotionReclaimPoints.setText("0");
/* 424 */       this.lblMastery1Points.setText("");
/* 425 */       this.lblMastery2Points.setText("");
/* 426 */       this.ftMastery1Points.setText("0");
/* 427 */       this.ftMastery2Points.setText("0");
/* 428 */       this.btnMastery1Reset.setEnabled(false);
/* 429 */       this.btnMastery2Reset.setEnabled(false);
/*     */     } else {
/* 431 */       this.ftStatPoints.setText(Integer.toString(gdc.getStatPoints()));
/* 432 */       this.ftSkillPoints.setText(Integer.toString(this.tabPane.getSkillPoints()));
/* 433 */       this.ftDevotionPoints.setText(Integer.toString(gdc.getDevotionPoints()));
/* 434 */       this.ftTributePoints.setText(Integer.toString(gdc.getTributes()));
/* 435 */       this.ftSkillReclaimPoints.setText(Integer.toString(gdc.getSkillReclaimPoints()));
/* 436 */       this.ftDevotionReclaimPoints.setText(Integer.toString(gdc.getDevotionReclaimPoints()));
/*     */       
/* 438 */       updateMasteryInfo();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateMasteryInfo() {
/* 443 */     this.infos = this.gdc.getMasteryInfo();
/*     */     
/* 445 */     if (this.infos == null) {
/* 446 */       this.lblMastery1Points.setText("");
/* 447 */       this.ftMastery1Points.setText("0");
/* 448 */       this.lblMastery2Points.setText("");
/* 449 */       this.ftMastery2Points.setText("0");
/* 450 */       this.btnMastery1Reset.setEnabled(false);
/* 451 */       this.btnMastery2Reset.setEnabled(false);
/*     */     }
/* 453 */     else if (this.infos.length == 1) {
/* 454 */       Object[] args = { (this.infos[0]).name };
/* 455 */       String s = GDMsgFormatter.format(GDMsgFormatter.rbUI, "TXT_CHAR_MASTERY_POINTS", args);
/*     */       
/* 457 */       this.lblMastery1Points.setText(s);
/* 458 */       this.ftMastery1Points.setText(Integer.toString((this.infos[0]).points));
/* 459 */       this.btnMastery1Reset.setEnabled(true);
/*     */       
/* 461 */       this.lblMastery2Points.setText("");
/* 462 */       this.ftMastery2Points.setText("0");
/* 463 */       this.btnMastery2Reset.setEnabled(false);
/*     */     } else {
/* 465 */       Object[] args1 = { (this.infos[0]).name };
/* 466 */       String s1 = GDMsgFormatter.format(GDMsgFormatter.rbUI, "TXT_CHAR_MASTERY_POINTS", args1);
/*     */       
/* 468 */       this.lblMastery1Points.setText(s1);
/* 469 */       this.ftMastery1Points.setText(Integer.toString((this.infos[0]).points));
/* 470 */       this.btnMastery1Reset.setEnabled(true);
/*     */       
/* 472 */       Object[] args2 = { (this.infos[1]).name };
/* 473 */       String s2 = GDMsgFormatter.format(GDMsgFormatter.rbUI, "TXT_CHAR_MASTERY_POINTS", args2);
/*     */       
/* 475 */       this.lblMastery2Points.setText(s2);
/* 476 */       this.ftMastery2Points.setText(Integer.toString((this.infos[1]).points));
/* 477 */       this.btnMastery2Reset.setEnabled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateChar(GDChar gdc) {
/* 483 */     if (gdc == null)
/*     */       return; 
/* 485 */     gdc.setStatPoints(Integer.parseInt(this.ftStatPoints.getText()));
/* 486 */     gdc.setSkillPoints(Integer.parseInt(this.ftSkillPoints.getText()));
/* 487 */     gdc.setDevotionPoints(Integer.parseInt(this.ftDevotionPoints.getText()));
/* 488 */     gdc.setTributes(Integer.parseInt(this.ftTributePoints.getText()));
/* 489 */     gdc.setSkillReclaimPoints(Integer.parseInt(this.ftSkillReclaimPoints.getText()));
/* 490 */     gdc.setDevotionReclaimPoints(Integer.parseInt(this.ftDevotionReclaimPoints.getText()));
/*     */     
/* 492 */     if (this.infos != null) {
/* 493 */       if (this.infos.length >= 1) {
/* 494 */         (this.infos[0]).points = Integer.parseInt(this.ftMastery1Points.getText());
/*     */       }
/*     */       
/* 497 */       if (this.infos.length >= 2) {
/* 498 */         (this.infos[1]).points = Integer.parseInt(this.ftMastery2Points.getText());
/*     */       }
/*     */     } 
/*     */     
/* 502 */     gdc.setSkillLevel(this.infos);
/*     */   }
/*     */   
/*     */   public int getStatPoints() {
/* 506 */     int val = 0;
/*     */     
/*     */     try {
/* 509 */       val = Integer.parseInt(this.ftStatPoints.getText());
/*     */     }
/* 511 */     catch (NumberFormatException ex) {
/* 512 */       val = 0;
/*     */     } 
/*     */     
/* 515 */     return val;
/*     */   }
/*     */   
/*     */   public void setStatPoints(int points) {
/* 519 */     this.ftStatPoints.setText(Integer.toString(points));
/*     */   }
/*     */   
/*     */   public void setSkillPoints(int points) {
/* 523 */     this.ftSkillPoints.setText(Integer.toString(points));
/*     */   }
/*     */   
/*     */   public void resetMastery(String masteryID) {
/* 527 */     updateMasteryInfo();
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharPointPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */