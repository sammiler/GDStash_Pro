/*     */ package org.gdstash.ui.character;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JFormattedTextField;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.db.DBClassTable;
/*     */ import org.gdstash.db.DBEnginePlayerMasteries;
/*     */ import org.gdstash.db.DBSkill;
/*     */ import org.gdstash.db.DBSkillButton;
/*     */ import org.gdstash.db.DBSkillTree;
/*     */ import org.gdstash.ui.GDHelpActionListener;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ import org.gdstash.util.GDImagePool;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDCharMasteryPane extends AdjustablePanel {
/*     */   private static MasteryInfo[] CLASS_INFOS;
/*     */   private GDChar gdc;
/*     */   private GDCharMasteryImagePane imgPane;
/*     */   private JComboBox<MasteryInfo> cbMastery;
/*     */   private JLabel lblSkillDesc;
/*     */   private JLabel lblSkillPoints;
/*     */   private JTextField ftSkillPoints;
/*     */   private JButton btnHelp;
/*     */   private GDUIMasterySupport masterySupport;
/*     */   private boolean selMastery;
/*     */   private String masteryID;
/*     */   
/*     */   public static class MasteryInfo implements Comparable<MasteryInfo> {
/*     */     public DBClassTable ct;
/*     */     
/*     */     public String toString() {
/*  53 */       return this.name;
/*     */     }
/*     */     public String id; public String name;
/*     */     public String getID() {
/*  57 */       return this.id;
/*     */     }
/*     */     
/*     */     public String getMasteryTitle(int genderCode) {
/*  61 */       return this.ct.getMasteryTitle(genderCode);
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(MasteryInfo info) {
/*  66 */       if (info == null) return -1;
/*     */       
/*  68 */       return this.name.compareTo(info.name);
/*     */     }
/*     */   }
/*     */   
/*     */   private class MouseInfo extends MouseAdapter { private MouseInfo() {}
/*     */     
/*     */     public void mouseMoved(MouseEvent e) {
/*  75 */       int x = e.getX();
/*  76 */       int y = e.getY();
/*     */       
/*  78 */       int cx = GDCharMasteryPane.this.imgPane.getX();
/*  79 */       int cy = GDCharMasteryPane.this.imgPane.getY();
/*  80 */       int cw = GDCharMasteryPane.this.imgPane.getWidth();
/*  81 */       int ch = GDCharMasteryPane.this.imgPane.getHeight();
/*     */       
/*  83 */       if (cx <= x && cx + cw >= x && cy <= y && cy + ch >= y) {
/*     */         
/*  85 */         String s = GDCharMasteryPane.this.imgPane.getSkillDescription(x - cx, y - cy);
/*     */         
/*  87 */         if (s == null) s = "";
/*     */         
/*  89 */         GDCharMasteryPane.this.lblSkillDesc.setText(s);
/*  90 */         GDCharMasteryPane.this.lblSkillDesc.setVerticalTextPosition(1);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseClicked(MouseEvent e) {
/*  96 */       int x = e.getX();
/*  97 */       int y = e.getY();
/*     */       
/*  99 */       int cx = GDCharMasteryPane.this.imgPane.getX();
/* 100 */       int cy = GDCharMasteryPane.this.imgPane.getY();
/* 101 */       int cw = GDCharMasteryPane.this.imgPane.getWidth();
/* 102 */       int ch = GDCharMasteryPane.this.imgPane.getHeight();
/*     */       
/* 104 */       if (cx <= x && cx + cw >= x && cy <= y && cy + ch >= y) {
/*     */         
/* 106 */         String s = null;
/*     */         
/* 108 */         if (SwingUtilities.isLeftMouseButton(e)) {
/* 109 */           s = GDCharMasteryPane.this.imgPane.incSkill(x - cx, y - cy);
/*     */         }
/*     */         
/* 112 */         if (SwingUtilities.isRightMouseButton(e)) {
/* 113 */           s = GDCharMasteryPane.this.imgPane.decSkill(x - cx, y - cy);
/*     */         }
/*     */         
/* 116 */         if (s == null) s = "";
/*     */         
/* 118 */         GDCharMasteryPane.this.lblSkillDesc.setText(s);
/*     */       } 
/*     */     } }
/*     */   
/*     */   private class MasteryListener implements ActionListener {
/*     */     private MasteryListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/* 126 */       int i = GDCharMasteryPane.this.cbMastery.getSelectedIndex();
/*     */       
/* 128 */       if (GDCharMasteryPane.this.masteryID != null && 
/* 129 */         !(GDCharMasteryPane.CLASS_INFOS[i]).id.equals(GDCharMasteryPane.this.masteryID)) {
/* 130 */         int p = GDCharMasteryPane.this.imgPane.getPointsSpent();
/* 131 */         GDCharMasteryPane.this.masterySupport.setSkillPoints(GDCharMasteryPane.this.masterySupport.getSkillPoints() + p);
/*     */       } 
/*     */ 
/*     */       
/* 135 */       GDCharMasteryPane.this.setMastery((GDCharMasteryPane.CLASS_INFOS[i]).id);
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
/*     */   public static void loadMasteries() {
/* 154 */     List<DBEnginePlayerMasteries> masteryTrees = DBEnginePlayerMasteries.get();
/*     */     
/* 156 */     if (masteryTrees == null)
/*     */       return; 
/* 158 */     List<DBClassTable> cTabs = new LinkedList<>();
/* 159 */     for (DBEnginePlayerMasteries mt : masteryTrees) {
/* 160 */       DBSkillTree tree = DBSkillTree.get(mt.getSkillTreeID());
/*     */       
/* 162 */       if (tree == null)
/*     */         continue; 
/* 164 */       DBSkill skill = tree.getMasterySkill();
/*     */       
/* 166 */       if (skill == null)
/*     */         continue; 
/* 168 */       DBSkillButton button = DBSkillButton.getBySkillID(skill.getSkillID());
/*     */       
/* 170 */       if (button == null)
/*     */         continue; 
/* 172 */       DBClassTable ct = DBClassTable.getByMasteryID(button.getButtonID());
/*     */       
/* 174 */       if (ct == null)
/*     */         continue; 
/* 176 */       cTabs.add(ct);
/*     */     } 
/*     */     
/* 179 */     CLASS_INFOS = new MasteryInfo[cTabs.size() + 1];
/*     */     
/* 181 */     CLASS_INFOS[0] = new MasteryInfo();
/* 182 */     (CLASS_INFOS[0]).ct = null;
/* 183 */     (CLASS_INFOS[0]).id = "";
/* 184 */     (CLASS_INFOS[0]).name = "";
/*     */     
/* 186 */     int i = 1;
/* 187 */     for (DBClassTable ct : cTabs) {
/* 188 */       CLASS_INFOS[i] = new MasteryInfo();
/* 189 */       (CLASS_INFOS[i]).ct = ct;
/*     */       
/* 191 */       DBSkillButton sb = DBSkillButton.getByButtonID(ct.getMasteryID());
/*     */       
/* 193 */       if (sb == null) {
/* 194 */         (CLASS_INFOS[i]).id = "";
/* 195 */         (CLASS_INFOS[i]).name = "";
/*     */       } else {
/* 197 */         DBSkill skill = DBSkill.get(sb.getSkillID());
/*     */         
/* 199 */         if (skill == null) {
/* 200 */           (CLASS_INFOS[i]).id = "";
/* 201 */           (CLASS_INFOS[i]).name = "";
/*     */         } else {
/* 203 */           (CLASS_INFOS[i]).id = skill.getSkillID();
/* 204 */           (CLASS_INFOS[i]).name = ct.getMasteryTitle(0);
/*     */           
/* 206 */           if ((CLASS_INFOS[i]).name == null) (CLASS_INFOS[i]).name = skill.getName(); 
/* 207 */           if ((CLASS_INFOS[i]).name == null) (CLASS_INFOS[i]).name = (CLASS_INFOS[i]).id;
/*     */         
/*     */         } 
/*     */       } 
/* 211 */       i++;
/*     */     } 
/*     */     
/* 214 */     sortMasteryInfo();
/*     */   }
/*     */   
/*     */   private static void setClassInfoByGender(int genderCode) {
/* 218 */     for (int i = 0; i < CLASS_INFOS.length; i++) {
/* 219 */       if ((CLASS_INFOS[i]).ct != null)
/*     */       {
/* 221 */         (CLASS_INFOS[i]).name = (CLASS_INFOS[i]).ct.getMasteryTitle(genderCode); } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void sortMasteryInfo() {
/* 226 */     Arrays.sort((Object[])CLASS_INFOS);
/*     */   }
/*     */   
/*     */   public GDCharMasteryPane(GDUIMasterySupport masterySupport, boolean selMastery) {
/* 230 */     this.masterySupport = masterySupport;
/* 231 */     this.selMastery = selMastery;
/*     */     
/* 233 */     this.imgPane = new GDCharMasteryImagePane(masterySupport, selMastery);
/*     */     
/* 235 */     adjustUI();
/*     */     
/* 237 */     JPanel pnlPoints = buildSkillPointPanel();
/*     */     
/* 239 */     GroupLayout layout = null;
/* 240 */     GroupLayout.SequentialGroup hGroup = null;
/* 241 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 243 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 244 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 245 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */     
/* 247 */     layout = new GroupLayout((Container)this);
/* 248 */     setLayout(layout);
/*     */     
/* 250 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 253 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 256 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 259 */     hGroup
/* 260 */       .addGroup(layout.createParallelGroup()
/* 261 */         .addComponent(pnlPoints)
/* 262 */         .addComponent((Component)this.imgPane))
/* 263 */       .addGroup(layout.createParallelGroup()
/* 264 */         .addComponent(this.lblSkillDesc));
/* 265 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 268 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 271 */     vGroup
/* 272 */       .addGroup(layout.createParallelGroup()
/* 273 */         .addComponent(pnlPoints))
/* 274 */       .addGroup(layout.createParallelGroup()
/* 275 */         .addComponent((Component)this.imgPane)
/* 276 */         .addComponent(this.lblSkillDesc));
/* 277 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 279 */     layout.linkSize(1, new Component[] { (Component)this.imgPane, this.lblSkillDesc });
/*     */     
/* 281 */     addMouseListener(new MouseInfo());
/* 282 */     addMouseMotionListener(new MouseInfo());
/*     */   }
/*     */   
/*     */   public void adjustUI() {
/* 286 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 287 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/* 288 */     if (fntButton == null) fntButton = fntLabel; 
/* 289 */     Font fntCombo = UIManager.getDefaults().getFont("ComboBox.font");
/* 290 */     if (fntCombo == null) fntCombo = fntLabel; 
/* 291 */     Font fntFText = UIManager.getDefaults().getFont("FormattedTextField.font");
/* 292 */     if (fntFText == null) fntFText = fntLabel;
/*     */     
/* 294 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 295 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 296 */     fntCombo = fntCombo.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 297 */     fntFText = fntFText.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 299 */     if (this.cbMastery == null) {
/* 300 */       if (CLASS_INFOS == null) {
/* 301 */         this.cbMastery = new JComboBox<>();
/*     */       } else {
/* 303 */         this.cbMastery = new JComboBox<>(CLASS_INFOS);
/*     */       } 
/*     */       
/* 306 */       this.cbMastery.setEnabled(this.selMastery);
/* 307 */       this.cbMastery.addActionListener(new MasteryListener());
/*     */     } 
/* 309 */     this.cbMastery.setFont(fntCombo);
/*     */     
/* 311 */     if (this.lblSkillDesc == null) {
/* 312 */       this.lblSkillDesc = new JLabel();
/* 313 */       this.lblSkillDesc.setVerticalTextPosition(1);
/* 314 */       this.lblSkillDesc.setHorizontalAlignment(0);
/*     */     } 
/* 316 */     this.lblSkillDesc.setFont(fntLabel);
/*     */     
/* 318 */     if (this.lblSkillPoints == null) this.lblSkillPoints = new JLabel(); 
/* 319 */     this.lblSkillPoints.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_CHAR_POINTS_SKILL"));
/* 320 */     this.lblSkillPoints.setFont(fntLabel);
/*     */     
/* 322 */     if (this.ftSkillPoints == null) {
/* 323 */       this.ftSkillPoints = new JFormattedTextField();
/* 324 */       this.ftSkillPoints.setEditable(false);
/*     */     } 
/* 326 */     this.ftSkillPoints.setFont(fntFText);
/*     */     
/* 328 */     if (this.btnHelp == null) {
/* 329 */       this.btnHelp = new JButton();
/*     */       
/* 331 */       this.btnHelp.addActionListener((ActionListener)new GDHelpActionListener("08_masteryinfo.html"));
/*     */     } 
/* 333 */     this.btnHelp.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_HELP"));
/* 334 */     this.btnHelp.setIcon(GDImagePool.iconBtnQuestion24);
/* 335 */     this.btnHelp.setFont(fntButton);
/* 336 */     GDStashFrame.setMnemonic(this.btnHelp, "MNC_HELP");
/*     */     
/* 338 */     if (this.imgPane != null) this.imgPane.adjustUI(); 
/*     */   }
/*     */   
/*     */   private JPanel buildSkillPointPanel() {
/* 342 */     JPanel panel = new JPanel();
/*     */     
/* 344 */     GroupLayout layout = null;
/* 345 */     GroupLayout.SequentialGroup hGroup = null;
/* 346 */     GroupLayout.SequentialGroup vGroup = null;
/*     */ 
/*     */ 
/*     */     
/* 350 */     layout = new GroupLayout(panel);
/* 351 */     panel.setLayout(layout);
/*     */     
/* 353 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 356 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 359 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 362 */     if (this.selMastery) {
/* 363 */       hGroup
/* 364 */         .addGroup(layout.createParallelGroup()
/* 365 */           .addComponent(this.cbMastery))
/* 366 */         .addGroup(layout.createParallelGroup()
/* 367 */           .addComponent(this.lblSkillPoints))
/* 368 */         .addGroup(layout.createParallelGroup()
/* 369 */           .addComponent(this.ftSkillPoints))
/* 370 */         .addGroup(layout.createParallelGroup()
/* 371 */           .addComponent(this.btnHelp));
/*     */     } else {
/* 373 */       hGroup
/* 374 */         .addGroup(layout.createParallelGroup()
/* 375 */           .addComponent(this.cbMastery))
/* 376 */         .addGroup(layout.createParallelGroup()
/* 377 */           .addComponent(this.lblSkillPoints))
/* 378 */         .addGroup(layout.createParallelGroup()
/* 379 */           .addComponent(this.ftSkillPoints));
/*     */     } 
/* 381 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 384 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 387 */     if (this.selMastery) {
/* 388 */       vGroup
/* 389 */         .addGroup(layout.createParallelGroup()
/* 390 */           .addComponent(this.cbMastery)
/* 391 */           .addComponent(this.lblSkillPoints)
/* 392 */           .addComponent(this.ftSkillPoints)
/* 393 */           .addComponent(this.btnHelp));
/*     */     } else {
/* 395 */       vGroup
/* 396 */         .addGroup(layout.createParallelGroup()
/* 397 */           .addComponent(this.cbMastery)
/* 398 */           .addComponent(this.lblSkillPoints)
/* 399 */           .addComponent(this.ftSkillPoints));
/*     */     } 
/* 401 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 403 */     layout.linkSize(0, new Component[] { this.lblSkillPoints, this.cbMastery });
/* 404 */     layout.linkSize(0, new Component[] { this.lblSkillPoints, this.ftSkillPoints });
/* 405 */     if (this.selMastery) layout.linkSize(0, new Component[] { this.lblSkillPoints, this.btnHelp });
/*     */     
/* 407 */     layout.linkSize(1, new Component[] { this.lblSkillPoints, this.cbMastery });
/* 408 */     layout.linkSize(1, new Component[] { this.lblSkillPoints, this.ftSkillPoints });
/* 409 */     if (this.selMastery) layout.linkSize(1, new Component[] { this.lblSkillPoints, this.btnHelp });
/*     */     
/* 411 */     return panel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateConfig() {
/* 417 */     setSkillPoints(this.masterySupport.getSkillPoints());
/*     */     
/* 419 */     this.imgPane.updateConfig();
/*     */     
/* 421 */     if (this.selMastery) {
/* 422 */       int p = this.imgPane.getPointsSpent();
/* 423 */       this.masterySupport.setSkillPoints(this.masterySupport.getSkillPoints() + p);
/* 424 */       this.imgPane.setMastery((String)null);
/*     */       
/* 426 */       if (this.cbMastery != null) {
/* 427 */         DefaultComboBoxModel<MasteryInfo> model = new DefaultComboBoxModel<>(CLASS_INFOS);
/*     */         
/* 429 */         this.cbMastery.setModel(model);
/* 430 */         this.cbMastery.setSelectedIndex(0);
/*     */       }
/*     */     
/* 433 */     } else if (this.cbMastery != null) {
/* 434 */       MasteryInfo info = (MasteryInfo)this.cbMastery.getSelectedItem();
/*     */       
/* 436 */       DefaultComboBoxModel<MasteryInfo> model = new DefaultComboBoxModel<>(CLASS_INFOS);
/*     */       
/* 438 */       this.cbMastery.setModel(model);
/*     */       
/* 440 */       int index = 0;
/* 441 */       if (info != null) {
/* 442 */         int i; for (i = 0; i < CLASS_INFOS.length; i++) {
/* 443 */           if (info.id.equals((CLASS_INFOS[i]).id)) {
/* 444 */             index = i;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 451 */       this.cbMastery.setSelectedIndex(index);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPointsForSkill(String skillID) {
/* 457 */     if (this.imgPane == null) return 0;
/*     */     
/* 459 */     return this.imgPane.getPointsForSkill(skillID);
/*     */   }
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 463 */     this.gdc = gdc;
/*     */     
/* 465 */     this.imgPane.setChar(gdc);
/*     */     
/* 467 */     setSkillPoints(this.masterySupport.getSkillPoints());
/*     */   }
/*     */   
/*     */   public void setSkillPoints(int points) {
/* 471 */     this.ftSkillPoints.setText(Integer.toString(points));
/*     */   }
/*     */   
/*     */   public void updateChar(GDChar gdc) {
/* 475 */     if (gdc == null)
/*     */       return; 
/* 477 */     this.imgPane.updateChar(gdc);
/*     */   }
/*     */   
/*     */   public void setMastery(String masteryID) {
/* 481 */     this.masteryID = masteryID;
/*     */     
/* 483 */     this.imgPane.setMastery(masteryID);
/*     */     
/* 485 */     if (CLASS_INFOS == null)
/*     */       return; 
/* 487 */     int index = 0;
/* 488 */     if (masteryID != null) {
/* 489 */       for (int i = 0; i < CLASS_INFOS.length; i++) {
/* 490 */         if ((CLASS_INFOS[i]).id.equals(masteryID)) {
/* 491 */           index = i;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 498 */     this.cbMastery.setSelectedIndex(index);
/*     */   }
/*     */   
/*     */   public void resetMastery(String masteryID) {
/* 502 */     if (masteryID == null)
/*     */       return; 
/* 504 */     if (!masteryID.equals(this.masteryID))
/*     */       return; 
/* 506 */     this.imgPane.setMastery((String)null);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharMasteryPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */