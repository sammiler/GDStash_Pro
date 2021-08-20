/*     */ package org.gdstash.ui.character;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.character.GDCharShrineList;
/*     */ import org.gdstash.character.GDCharUID;
/*     */ import org.gdstash.db.DBShrine;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.util.GDAbstractShrinePane;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDCharShrineAct2Pane extends GDAbstractShrinePane {
/*     */   private JCheckBox cbSpinedCove;
/*     */   private JCheckBox cbRockyCoast;
/*     */   private JCheckBox cbCronleysHideout;
/*     */   private JCheckBox cbOldArkovia;
/*     */   private JCheckBox cbArkovianUndercity;
/*     */   private JCheckBox cbBrokenHills;
/*     */   private JCheckBox cbStepsOfTorment;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   private class ChangeActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  36 */       GDCharShrineAct2Pane.this.changed = true;
/*     */     }
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
/*     */   public GDCharShrineAct2Pane(int difficulty, int direction) {
/*  52 */     this.difficulty = difficulty;
/*  53 */     this.changed = false;
/*     */     
/*  55 */     adjustUI();
/*     */     
/*  57 */     GroupLayout layout = null;
/*  58 */     GroupLayout.SequentialGroup hGroup = null;
/*  59 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  61 */     layout = new GroupLayout((Container)this);
/*  62 */     setLayout(layout);
/*     */     
/*  64 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  67 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  70 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  73 */     hGroup
/*  74 */       .addGroup(layout.createParallelGroup()
/*  75 */         .addComponent(this.cbSpinedCove)
/*  76 */         .addComponent(this.cbRockyCoast)
/*  77 */         .addComponent(this.cbCronleysHideout)
/*  78 */         .addComponent(this.cbOldArkovia)
/*  79 */         .addComponent(this.cbArkovianUndercity)
/*  80 */         .addComponent(this.cbBrokenHills)
/*  81 */         .addComponent(this.cbStepsOfTorment));
/*     */ 
/*     */     
/*  84 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  87 */     vGroup
/*  88 */       .addGroup(layout.createParallelGroup()
/*  89 */         .addComponent(this.cbSpinedCove))
/*  90 */       .addGroup(layout.createParallelGroup()
/*  91 */         .addComponent(this.cbRockyCoast))
/*  92 */       .addGroup(layout.createParallelGroup()
/*  93 */         .addComponent(this.cbCronleysHideout))
/*  94 */       .addGroup(layout.createParallelGroup()
/*  95 */         .addComponent(this.cbOldArkovia))
/*  96 */       .addGroup(layout.createParallelGroup()
/*  97 */         .addComponent(this.cbArkovianUndercity))
/*  98 */       .addGroup(layout.createParallelGroup()
/*  99 */         .addComponent(this.cbBrokenHills))
/* 100 */       .addGroup(layout.createParallelGroup()
/* 101 */         .addComponent(this.cbStepsOfTorment));
/*     */     
/* 103 */     if (direction == 0) {
/* 104 */       layout.setHorizontalGroup(vGroup);
/* 105 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 107 */       layout.setHorizontalGroup(hGroup);
/* 108 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 111 */     layout.linkSize(0, new Component[] { this.cbSpinedCove, this.cbRockyCoast });
/* 112 */     layout.linkSize(0, new Component[] { this.cbSpinedCove, this.cbCronleysHideout });
/* 113 */     layout.linkSize(0, new Component[] { this.cbSpinedCove, this.cbOldArkovia });
/* 114 */     layout.linkSize(0, new Component[] { this.cbSpinedCove, this.cbArkovianUndercity });
/* 115 */     layout.linkSize(0, new Component[] { this.cbSpinedCove, this.cbBrokenHills });
/* 116 */     layout.linkSize(0, new Component[] { this.cbSpinedCove, this.cbStepsOfTorment });
/*     */     
/* 118 */     layout.linkSize(1, new Component[] { this.cbSpinedCove, this.cbRockyCoast });
/* 119 */     layout.linkSize(1, new Component[] { this.cbSpinedCove, this.cbCronleysHideout });
/* 120 */     layout.linkSize(1, new Component[] { this.cbSpinedCove, this.cbOldArkovia });
/* 121 */     layout.linkSize(1, new Component[] { this.cbSpinedCove, this.cbArkovianUndercity });
/* 122 */     layout.linkSize(1, new Component[] { this.cbSpinedCove, this.cbBrokenHills });
/* 123 */     layout.linkSize(1, new Component[] { this.cbSpinedCove, this.cbStepsOfTorment });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 128 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 129 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 130 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 131 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 132 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 134 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 135 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 136 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 138 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 139 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 140 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 141 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_ACT2"));
/* 142 */     text.setTitleFont(fntBorder);
/*     */     
/* 144 */     setBorder(text);
/*     */     
/* 146 */     if (this.cbSpinedCove == null) {
/* 147 */       this.cbSpinedCove = new JCheckBox();
/*     */       
/* 149 */       this.cbSpinedCove.addActionListener(new ChangeActionListener());
/*     */     } 
/* 151 */     this.cbSpinedCove.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_02_SPINED_COVE"));
/* 152 */     this.cbSpinedCove.setFont(fntCheck);
/*     */     
/* 154 */     if (this.cbRockyCoast == null) {
/* 155 */       this.cbRockyCoast = new JCheckBox();
/*     */       
/* 157 */       this.cbRockyCoast.addActionListener(new ChangeActionListener());
/*     */     } 
/* 159 */     this.cbRockyCoast.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_02_ROCKY_COAST"));
/* 160 */     this.cbRockyCoast.setFont(fntCheck);
/*     */     
/* 162 */     if (this.cbCronleysHideout == null) {
/* 163 */       this.cbCronleysHideout = new JCheckBox();
/*     */       
/* 165 */       this.cbCronleysHideout.addActionListener(new ChangeActionListener());
/*     */     } 
/* 167 */     this.cbCronleysHideout.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_02_CRONLEYS_HIDEOUT"));
/* 168 */     this.cbCronleysHideout.setFont(fntCheck);
/*     */     
/* 170 */     if (this.cbOldArkovia == null) {
/* 171 */       this.cbOldArkovia = new JCheckBox();
/*     */       
/* 173 */       this.cbOldArkovia.addActionListener(new ChangeActionListener());
/*     */     } 
/* 175 */     this.cbOldArkovia.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_02_OLD_ARKOVIA"));
/* 176 */     this.cbOldArkovia.setFont(fntCheck);
/*     */     
/* 178 */     if (this.cbArkovianUndercity == null) {
/* 179 */       this.cbArkovianUndercity = new JCheckBox();
/*     */       
/* 181 */       this.cbArkovianUndercity.addActionListener(new ChangeActionListener());
/*     */     } 
/* 183 */     this.cbArkovianUndercity.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_02_ARKOVIAN_UNDERCITY"));
/* 184 */     this.cbArkovianUndercity.setFont(fntCheck);
/*     */     
/* 186 */     if (this.cbBrokenHills == null) {
/* 187 */       this.cbBrokenHills = new JCheckBox();
/*     */       
/* 189 */       this.cbBrokenHills.addActionListener(new ChangeActionListener());
/*     */     } 
/* 191 */     this.cbBrokenHills.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_02_BROKEN_HILLS"));
/* 192 */     this.cbBrokenHills.setFont(fntCheck);
/*     */ 
/*     */     
/* 195 */     if (this.cbStepsOfTorment == null) {
/* 196 */       this.cbStepsOfTorment = new JCheckBox();
/*     */       
/* 198 */       this.cbStepsOfTorment.addActionListener(new ChangeActionListener());
/*     */     } 
/* 200 */     this.cbStepsOfTorment.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SHRINE_02_STEPS_OF_TORMENT"));
/* 201 */     this.cbStepsOfTorment.setFont(fntCheck);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 206 */     return this.changed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged(boolean changed) {
/* 211 */     this.changed = changed;
/*     */   }
/*     */   
/*     */   public void setSelected(boolean selected) {
/* 215 */     this.cbSpinedCove.setSelected(selected);
/* 216 */     this.cbRockyCoast.setSelected(selected);
/* 217 */     this.cbCronleysHideout.setSelected(selected);
/* 218 */     this.cbOldArkovia.setSelected(selected);
/* 219 */     this.cbArkovianUndercity.setSelected(selected);
/* 220 */     this.cbBrokenHills.setSelected(selected);
/* 221 */     this.cbStepsOfTorment.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getShrineList(boolean found) {
/* 226 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 228 */     if (this.cbSpinedCove.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_02_SPINED_COVE); 
/* 229 */     if (this.cbRockyCoast.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_02_ROCKY_COAST); 
/* 230 */     if (this.cbCronleysHideout.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_02_CRONLEYS_HIDEOUT); 
/* 231 */     if (this.cbOldArkovia.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_02_OLD_ARKOVIA); 
/* 232 */     if (this.cbArkovianUndercity.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_02_ARKOVIAN_UNDERCITY); 
/* 233 */     if (this.cbBrokenHills.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_02_BROKEN_HILLS); 
/* 234 */     if (this.cbStepsOfTorment.isSelected() == found) list.add(GDCharShrineList.UID_SHRINE_02_STEPS_OF_TORMENT);
/*     */     
/* 236 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 241 */     setSelected(false);
/*     */     
/* 243 */     this.changed = false;
/*     */     
/* 245 */     boolean expFG = GDStashFrame.expansionForgottenGods;
/*     */     
/* 247 */     if (gdc != null) {
/* 248 */       expFG = (expFG || gdc.isForgottenGodsChar());
/*     */     }
/*     */     
/* 251 */     DBShrine.Info info = null;
/*     */     
/* 253 */     info = DBShrine.getShrineInfo("devotionshrineb01.dbr", this.difficulty);
/* 254 */     this.cbSpinedCove.setEnabled(info.active);
/*     */     
/* 256 */     info = DBShrine.getShrineInfo("devotionshrineb06.dbr", this.difficulty);
/* 257 */     this.cbRockyCoast.setEnabled(info.active);
/*     */     
/* 259 */     info = DBShrine.getShrineInfo("devotionshrineb04.dbr", this.difficulty);
/* 260 */     this.cbCronleysHideout.setEnabled(info.active);
/*     */     
/* 262 */     info = DBShrine.getShrineInfo("devotionshrineb02.dbr", this.difficulty);
/* 263 */     this.cbOldArkovia.setEnabled(info.active);
/*     */     
/* 265 */     info = DBShrine.getShrineInfo("devotionshrineb03.dbr", this.difficulty);
/* 266 */     this.cbArkovianUndercity.setEnabled(info.active);
/*     */     
/* 268 */     info = DBShrine.getShrineInfo("devotionshrineb05.dbr", this.difficulty);
/* 269 */     this.cbBrokenHills.setEnabled(info.active);
/*     */     
/* 271 */     info = DBShrine.getShrineInfo("devotionshrineb07.dbr", this.difficulty);
/* 272 */     this.cbStepsOfTorment.setEnabled(info.active);
/*     */     
/* 274 */     if (gdc == null)
/*     */       return; 
/* 276 */     List<GDCharUID> list = gdc.getRestoredShrinesList(this.difficulty);
/*     */     
/* 278 */     for (GDCharUID uid : list) {
/* 279 */       if (uid.equals(GDCharShrineList.UID_SHRINE_02_SPINED_COVE)) this.cbSpinedCove.setSelected(true); 
/* 280 */       if (uid.equals(GDCharShrineList.UID_SHRINE_02_ROCKY_COAST)) this.cbRockyCoast.setSelected(true); 
/* 281 */       if (uid.equals(GDCharShrineList.UID_SHRINE_02_CRONLEYS_HIDEOUT)) this.cbCronleysHideout.setSelected(true); 
/* 282 */       if (uid.equals(GDCharShrineList.UID_SHRINE_02_OLD_ARKOVIA)) this.cbOldArkovia.setSelected(true); 
/* 283 */       if (uid.equals(GDCharShrineList.UID_SHRINE_02_ARKOVIAN_UNDERCITY)) this.cbArkovianUndercity.setSelected(true); 
/* 284 */       if (uid.equals(GDCharShrineList.UID_SHRINE_02_BROKEN_HILLS)) this.cbBrokenHills.setSelected(true); 
/* 285 */       if (uid.equals(GDCharShrineList.UID_SHRINE_02_STEPS_OF_TORMENT)) this.cbStepsOfTorment.setSelected(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\character\GDCharShrineAct2Pane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */