/*     */ package org.gdstash.ui.character;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.List;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.character.GDCharTeleportList;
/*     */ import org.gdstash.character.GDCharUID;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.util.GDAbstractRiftPane;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDCharRiftAct5Pane extends GDAbstractRiftPane {
/*     */   private JCheckBox cbGloomwald;
/*     */   private JCheckBox cbGloomwaldCrossing;
/*     */   private JCheckBox cbCovensRefuge;
/*     */   private JCheckBox cbUgdenbog;
/*     */   private JCheckBox cbBarrowholm;
/*     */   private JCheckBox cbLoneWatch;
/*     */   private JCheckBox cbMalmouthOutskirts;
/*     */   private JCheckBox cbMalmouthSteelcap;
/*     */   private JCheckBox cbMalmouthCrownHill;
/*     */   private JCheckBox cbMalmouthSewers;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   private class ChangeActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  35 */       GDCharRiftAct5Pane.this.changed = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private ChangeActionListener() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GDCharRiftAct5Pane(int difficulty, int direction) {
/*  54 */     this.difficulty = difficulty;
/*  55 */     this.changed = false;
/*     */     
/*  57 */     adjustUI();
/*     */     
/*  59 */     GroupLayout layout = null;
/*  60 */     GroupLayout.SequentialGroup hGroup = null;
/*  61 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  63 */     layout = new GroupLayout((Container)this);
/*  64 */     setLayout(layout);
/*     */     
/*  66 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  69 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  72 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  75 */     hGroup
/*  76 */       .addGroup(layout.createParallelGroup()
/*  77 */         .addComponent(this.cbGloomwald)
/*  78 */         .addComponent(this.cbGloomwaldCrossing)
/*  79 */         .addComponent(this.cbCovensRefuge)
/*  80 */         .addComponent(this.cbUgdenbog)
/*  81 */         .addComponent(this.cbBarrowholm)
/*  82 */         .addComponent(this.cbLoneWatch)
/*  83 */         .addComponent(this.cbMalmouthOutskirts)
/*  84 */         .addComponent(this.cbMalmouthSewers)
/*  85 */         .addComponent(this.cbMalmouthSteelcap)
/*  86 */         .addComponent(this.cbMalmouthCrownHill));
/*     */ 
/*     */     
/*  89 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  92 */     vGroup
/*  93 */       .addGroup(layout.createParallelGroup()
/*  94 */         .addComponent(this.cbGloomwald))
/*  95 */       .addGroup(layout.createParallelGroup()
/*  96 */         .addComponent(this.cbGloomwaldCrossing))
/*  97 */       .addGroup(layout.createParallelGroup()
/*  98 */         .addComponent(this.cbCovensRefuge))
/*  99 */       .addGroup(layout.createParallelGroup()
/* 100 */         .addComponent(this.cbUgdenbog))
/* 101 */       .addGroup(layout.createParallelGroup()
/* 102 */         .addComponent(this.cbBarrowholm))
/* 103 */       .addGroup(layout.createParallelGroup()
/* 104 */         .addComponent(this.cbLoneWatch))
/* 105 */       .addGroup(layout.createParallelGroup()
/* 106 */         .addComponent(this.cbMalmouthOutskirts))
/* 107 */       .addGroup(layout.createParallelGroup()
/* 108 */         .addComponent(this.cbMalmouthSewers))
/* 109 */       .addGroup(layout.createParallelGroup()
/* 110 */         .addComponent(this.cbMalmouthSteelcap))
/* 111 */       .addGroup(layout.createParallelGroup()
/* 112 */         .addComponent(this.cbMalmouthCrownHill));
/*     */     
/* 114 */     if (direction == 0) {
/* 115 */       layout.setHorizontalGroup(vGroup);
/* 116 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 118 */       layout.setHorizontalGroup(hGroup);
/* 119 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 122 */     layout.linkSize(0, new Component[] { this.cbGloomwald, this.cbGloomwaldCrossing });
/* 123 */     layout.linkSize(0, new Component[] { this.cbGloomwald, this.cbCovensRefuge });
/* 124 */     layout.linkSize(0, new Component[] { this.cbGloomwald, this.cbUgdenbog });
/* 125 */     layout.linkSize(0, new Component[] { this.cbGloomwald, this.cbBarrowholm });
/* 126 */     layout.linkSize(0, new Component[] { this.cbGloomwald, this.cbLoneWatch });
/* 127 */     layout.linkSize(0, new Component[] { this.cbGloomwald, this.cbMalmouthOutskirts });
/* 128 */     layout.linkSize(0, new Component[] { this.cbGloomwald, this.cbMalmouthSewers });
/* 129 */     layout.linkSize(0, new Component[] { this.cbGloomwald, this.cbMalmouthSteelcap });
/* 130 */     layout.linkSize(0, new Component[] { this.cbGloomwald, this.cbMalmouthCrownHill });
/*     */     
/* 132 */     layout.linkSize(1, new Component[] { this.cbGloomwald, this.cbGloomwaldCrossing });
/* 133 */     layout.linkSize(1, new Component[] { this.cbGloomwald, this.cbCovensRefuge });
/* 134 */     layout.linkSize(1, new Component[] { this.cbGloomwald, this.cbUgdenbog });
/* 135 */     layout.linkSize(1, new Component[] { this.cbGloomwald, this.cbBarrowholm });
/* 136 */     layout.linkSize(1, new Component[] { this.cbGloomwald, this.cbLoneWatch });
/* 137 */     layout.linkSize(1, new Component[] { this.cbGloomwald, this.cbMalmouthOutskirts });
/* 138 */     layout.linkSize(1, new Component[] { this.cbGloomwald, this.cbMalmouthSewers });
/* 139 */     layout.linkSize(1, new Component[] { this.cbGloomwald, this.cbMalmouthSteelcap });
/* 140 */     layout.linkSize(1, new Component[] { this.cbGloomwald, this.cbMalmouthCrownHill });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 145 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 146 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 147 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 148 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 149 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 151 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 152 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 153 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 155 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 156 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 157 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 158 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ACT5"));
/* 159 */     text.setTitleFont(fntBorder);
/*     */     
/* 161 */     setBorder(text);
/*     */     
/* 163 */     if (this.cbGloomwald == null) {
/* 164 */       this.cbGloomwald = new JCheckBox();
/*     */       
/* 166 */       this.cbGloomwald.addActionListener(new ChangeActionListener());
/*     */     } 
/* 168 */     this.cbGloomwald.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_05_GLOOMWALD"));
/* 169 */     this.cbGloomwald.setFont(fntCheck);
/*     */     
/* 171 */     if (this.cbGloomwaldCrossing == null) {
/* 172 */       this.cbGloomwaldCrossing = new JCheckBox();
/*     */       
/* 174 */       this.cbGloomwaldCrossing.addActionListener(new ChangeActionListener());
/*     */     } 
/* 176 */     this.cbGloomwaldCrossing.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_05_GLOOMWALD_CROSSING"));
/* 177 */     this.cbGloomwaldCrossing.setFont(fntCheck);
/*     */     
/* 179 */     if (this.cbCovensRefuge == null) {
/* 180 */       this.cbCovensRefuge = new JCheckBox();
/*     */       
/* 182 */       this.cbCovensRefuge.addActionListener(new ChangeActionListener());
/*     */     } 
/* 184 */     this.cbCovensRefuge.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_05_COVENS_REFUGE"));
/* 185 */     this.cbCovensRefuge.setFont(fntCheck);
/*     */     
/* 187 */     if (this.cbUgdenbog == null) {
/* 188 */       this.cbUgdenbog = new JCheckBox();
/*     */       
/* 190 */       this.cbUgdenbog.addActionListener(new ChangeActionListener());
/*     */     } 
/* 192 */     this.cbUgdenbog.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_05_UGDENBOG"));
/* 193 */     this.cbUgdenbog.setFont(fntCheck);
/*     */     
/* 195 */     if (this.cbBarrowholm == null) {
/* 196 */       this.cbBarrowholm = new JCheckBox();
/*     */       
/* 198 */       this.cbBarrowholm.addActionListener(new ChangeActionListener());
/*     */     } 
/* 200 */     this.cbBarrowholm.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_05_BARROWHOLM"));
/* 201 */     this.cbBarrowholm.setFont(fntCheck);
/*     */     
/* 203 */     if (this.cbLoneWatch == null) {
/* 204 */       this.cbLoneWatch = new JCheckBox();
/*     */       
/* 206 */       this.cbLoneWatch.addActionListener(new ChangeActionListener());
/*     */     } 
/* 208 */     this.cbLoneWatch.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_05_LONE_WATCH"));
/* 209 */     this.cbLoneWatch.setFont(fntCheck);
/*     */     
/* 211 */     if (this.cbMalmouthOutskirts == null) {
/* 212 */       this.cbMalmouthOutskirts = new JCheckBox();
/*     */       
/* 214 */       this.cbMalmouthOutskirts.addActionListener(new ChangeActionListener());
/*     */     } 
/* 216 */     this.cbMalmouthOutskirts.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_05_MALMOUTH_OUTSKIRTS"));
/* 217 */     this.cbMalmouthOutskirts.setFont(fntCheck);
/*     */     
/* 219 */     if (this.cbMalmouthSewers == null) {
/* 220 */       this.cbMalmouthSewers = new JCheckBox();
/*     */       
/* 222 */       this.cbMalmouthSewers.addActionListener(new ChangeActionListener());
/*     */     } 
/* 224 */     this.cbMalmouthSewers.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_05_MALMOUTH_SEWERS"));
/* 225 */     this.cbMalmouthSewers.setFont(fntCheck);
/*     */     
/* 227 */     if (this.cbMalmouthSteelcap == null) {
/* 228 */       this.cbMalmouthSteelcap = new JCheckBox();
/*     */       
/* 230 */       this.cbMalmouthSteelcap.addActionListener(new ChangeActionListener());
/*     */     } 
/* 232 */     this.cbMalmouthSteelcap.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_05_MALMOUTH_STEELCAP"));
/* 233 */     this.cbMalmouthSteelcap.setFont(fntCheck);
/*     */     
/* 235 */     if (this.cbMalmouthCrownHill == null) {
/* 236 */       this.cbMalmouthCrownHill = new JCheckBox();
/*     */       
/* 238 */       this.cbMalmouthCrownHill.addActionListener(new ChangeActionListener());
/*     */     } 
/* 240 */     this.cbMalmouthCrownHill.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_05_MALMOUTH_CROWN_HILL"));
/* 241 */     this.cbMalmouthCrownHill.setFont(fntCheck);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 246 */     return this.changed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged(boolean changed) {
/* 251 */     this.changed = changed;
/*     */   }
/*     */   
/*     */   public void setSelected(boolean selected) {
/* 255 */     this.cbGloomwald.setSelected(selected);
/* 256 */     this.cbGloomwaldCrossing.setSelected(selected);
/* 257 */     this.cbCovensRefuge.setSelected(selected);
/* 258 */     this.cbUgdenbog.setSelected(selected);
/* 259 */     this.cbBarrowholm.setSelected(selected);
/* 260 */     this.cbLoneWatch.setSelected(selected);
/* 261 */     this.cbMalmouthOutskirts.setSelected(selected);
/* 262 */     this.cbMalmouthSewers.setSelected(selected);
/* 263 */     this.cbMalmouthSteelcap.setSelected(selected);
/* 264 */     this.cbMalmouthCrownHill.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getRiftList(boolean found) {
/* 269 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 271 */     if (this.cbGloomwald.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_05_GLOOMWALD); 
/* 272 */     if (this.cbGloomwaldCrossing.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_05_GLOOMWALD_CROSSING); 
/* 273 */     if (this.cbCovensRefuge.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_05_COVENS_REFUGE); 
/* 274 */     if (this.cbUgdenbog.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_05_UGDENBOG); 
/* 275 */     if (this.cbBarrowholm.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_05_BARROWHOLM); 
/* 276 */     if (this.cbLoneWatch.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_05_LONE_WATCH); 
/* 277 */     if (this.cbMalmouthOutskirts.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_05_MALMOUTH_OUTSKIRTS); 
/* 278 */     if (this.cbMalmouthSewers.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_05_MALMOUTH_SEWERS); 
/* 279 */     if (this.cbMalmouthSteelcap.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_05_MALMOUTH_STEELCAP); 
/* 280 */     if (this.cbMalmouthCrownHill.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_05_MALMOUTH_CROWN_HILL);
/*     */     
/* 282 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 287 */     setSelected(false);
/*     */     
/* 289 */     this.changed = false;
/*     */     
/* 291 */     boolean expAoM = GDStashFrame.expansionAshesOfMalmouth;
/*     */     
/* 293 */     if (gdc != null) {
/* 294 */       expAoM = (expAoM || gdc.isAsheshOfMalmouthChar() || gdc.isForgottenGodsChar());
/*     */     }
/*     */     
/* 297 */     if (expAoM) {
/* 298 */       this.cbGloomwald.setEnabled(true);
/* 299 */       this.cbGloomwaldCrossing.setEnabled(true);
/* 300 */       this.cbCovensRefuge.setEnabled(true);
/* 301 */       this.cbUgdenbog.setEnabled(true);
/* 302 */       this.cbBarrowholm.setEnabled(true);
/* 303 */       this.cbLoneWatch.setEnabled(true);
/* 304 */       this.cbMalmouthOutskirts.setEnabled(true);
/* 305 */       this.cbMalmouthSewers.setEnabled(true);
/* 306 */       this.cbMalmouthSteelcap.setEnabled(true);
/* 307 */       this.cbMalmouthCrownHill.setEnabled(true);
/*     */     } else {
/* 309 */       this.cbGloomwald.setEnabled(false);
/* 310 */       this.cbGloomwaldCrossing.setEnabled(false);
/* 311 */       this.cbCovensRefuge.setEnabled(false);
/* 312 */       this.cbUgdenbog.setEnabled(false);
/* 313 */       this.cbBarrowholm.setEnabled(false);
/* 314 */       this.cbLoneWatch.setEnabled(false);
/* 315 */       this.cbMalmouthOutskirts.setEnabled(false);
/* 316 */       this.cbMalmouthSewers.setEnabled(false);
/* 317 */       this.cbMalmouthSteelcap.setEnabled(false);
/* 318 */       this.cbMalmouthCrownHill.setEnabled(false);
/*     */     } 
/*     */     
/* 321 */     if (gdc == null)
/*     */       return; 
/* 323 */     List<GDCharUID> list = gdc.getRiftList(this.difficulty);
/*     */     
/* 325 */     for (GDCharUID uid : list) {
/* 326 */       if (uid.equals(GDCharTeleportList.UID_RIFT_05_GLOOMWALD)) this.cbGloomwald.setSelected(true); 
/* 327 */       if (uid.equals(GDCharTeleportList.UID_RIFT_05_GLOOMWALD_CROSSING)) this.cbGloomwaldCrossing.setSelected(true); 
/* 328 */       if (uid.equals(GDCharTeleportList.UID_RIFT_05_COVENS_REFUGE)) this.cbCovensRefuge.setSelected(true); 
/* 329 */       if (uid.equals(GDCharTeleportList.UID_RIFT_05_UGDENBOG)) this.cbUgdenbog.setSelected(true); 
/* 330 */       if (uid.equals(GDCharTeleportList.UID_RIFT_05_BARROWHOLM)) this.cbBarrowholm.setSelected(true); 
/* 331 */       if (uid.equals(GDCharTeleportList.UID_RIFT_05_LONE_WATCH)) this.cbLoneWatch.setSelected(true); 
/* 332 */       if (uid.equals(GDCharTeleportList.UID_RIFT_05_MALMOUTH_OUTSKIRTS)) this.cbMalmouthOutskirts.setSelected(true); 
/* 333 */       if (uid.equals(GDCharTeleportList.UID_RIFT_05_MALMOUTH_SEWERS)) this.cbMalmouthSewers.setSelected(true); 
/* 334 */       if (uid.equals(GDCharTeleportList.UID_RIFT_05_MALMOUTH_STEELCAP)) this.cbMalmouthSteelcap.setSelected(true); 
/* 335 */       if (uid.equals(GDCharTeleportList.UID_RIFT_05_MALMOUTH_CROWN_HILL)) this.cbMalmouthCrownHill.setSelected(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\character\GDCharRiftAct5Pane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */