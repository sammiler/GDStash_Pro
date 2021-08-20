/*     */ package org.gdstash.ui.character;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.LinkedList;
import java.util.List;
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
/*     */ public class GDCharRiftAct3Pane extends GDAbstractRiftPane {
/*     */   private JCheckBox cbDeadmansGulch;
/*     */   private JCheckBox cbProspectorsTrail;
/*     */   private JCheckBox cbPineBarrens;
/*     */   private JCheckBox cbHomestead;
/*     */   private JCheckBox cbRottingCropland;
/*     */   private JCheckBox cbSorrowsBastion;
/*     */   private JCheckBox cbBloodGrove;
/*     */   private JCheckBox cbDarkvaleGate;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   private class ChangeActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  35 */       GDCharRiftAct3Pane.this.changed = true;
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
/*     */   
/*     */   public GDCharRiftAct3Pane(int difficulty, int direction) {
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
/*  75 */         .addComponent(this.cbDeadmansGulch)
/*  76 */         .addComponent(this.cbProspectorsTrail)
/*  77 */         .addComponent(this.cbPineBarrens)
/*  78 */         .addComponent(this.cbHomestead)
/*  79 */         .addComponent(this.cbRottingCropland)
/*  80 */         .addComponent(this.cbSorrowsBastion)
/*  81 */         .addComponent(this.cbBloodGrove)
/*  82 */         .addComponent(this.cbDarkvaleGate));
/*     */ 
/*     */     
/*  85 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  88 */     vGroup
/*  89 */       .addGroup(layout.createParallelGroup()
/*  90 */         .addComponent(this.cbDeadmansGulch))
/*  91 */       .addGroup(layout.createParallelGroup()
/*  92 */         .addComponent(this.cbProspectorsTrail))
/*  93 */       .addGroup(layout.createParallelGroup()
/*  94 */         .addComponent(this.cbPineBarrens))
/*  95 */       .addGroup(layout.createParallelGroup()
/*  96 */         .addComponent(this.cbHomestead))
/*  97 */       .addGroup(layout.createParallelGroup()
/*  98 */         .addComponent(this.cbRottingCropland))
/*  99 */       .addGroup(layout.createParallelGroup()
/* 100 */         .addComponent(this.cbSorrowsBastion))
/* 101 */       .addGroup(layout.createParallelGroup()
/* 102 */         .addComponent(this.cbBloodGrove))
/* 103 */       .addGroup(layout.createParallelGroup()
/* 104 */         .addComponent(this.cbDarkvaleGate));
/*     */     
/* 106 */     if (direction == 0) {
/* 107 */       layout.setHorizontalGroup(vGroup);
/* 108 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 110 */       layout.setHorizontalGroup(hGroup);
/* 111 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 114 */     layout.linkSize(0, new Component[] { this.cbDeadmansGulch, this.cbProspectorsTrail });
/* 115 */     layout.linkSize(0, new Component[] { this.cbDeadmansGulch, this.cbPineBarrens });
/* 116 */     layout.linkSize(0, new Component[] { this.cbDeadmansGulch, this.cbHomestead });
/* 117 */     layout.linkSize(0, new Component[] { this.cbDeadmansGulch, this.cbRottingCropland });
/* 118 */     layout.linkSize(0, new Component[] { this.cbDeadmansGulch, this.cbSorrowsBastion });
/* 119 */     layout.linkSize(0, new Component[] { this.cbDeadmansGulch, this.cbBloodGrove });
/* 120 */     layout.linkSize(0, new Component[] { this.cbDeadmansGulch, this.cbDarkvaleGate });
/*     */     
/* 122 */     layout.linkSize(1, new Component[] { this.cbDeadmansGulch, this.cbProspectorsTrail });
/* 123 */     layout.linkSize(1, new Component[] { this.cbDeadmansGulch, this.cbPineBarrens });
/* 124 */     layout.linkSize(1, new Component[] { this.cbDeadmansGulch, this.cbHomestead });
/* 125 */     layout.linkSize(1, new Component[] { this.cbDeadmansGulch, this.cbRottingCropland });
/* 126 */     layout.linkSize(1, new Component[] { this.cbDeadmansGulch, this.cbSorrowsBastion });
/* 127 */     layout.linkSize(1, new Component[] { this.cbDeadmansGulch, this.cbBloodGrove });
/* 128 */     layout.linkSize(1, new Component[] { this.cbDeadmansGulch, this.cbDarkvaleGate });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 133 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 134 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 135 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 136 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 137 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 139 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 140 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 141 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 143 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 144 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 145 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 146 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ACT3"));
/* 147 */     text.setTitleFont(fntBorder);
/*     */     
/* 149 */     setBorder(text);
/*     */     
/* 151 */     if (this.cbDeadmansGulch == null) {
/* 152 */       this.cbDeadmansGulch = new JCheckBox();
/*     */       
/* 154 */       this.cbDeadmansGulch.addActionListener(new ChangeActionListener());
/*     */     } 
/* 156 */     this.cbDeadmansGulch.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_03_DEADMANS_GULCH"));
/* 157 */     this.cbDeadmansGulch.setFont(fntCheck);
/*     */     
/* 159 */     if (this.cbProspectorsTrail == null) {
/* 160 */       this.cbProspectorsTrail = new JCheckBox();
/*     */       
/* 162 */       this.cbProspectorsTrail.addActionListener(new ChangeActionListener());
/*     */     } 
/* 164 */     this.cbProspectorsTrail.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_03_PROSPECTORS_TRAIL"));
/* 165 */     this.cbProspectorsTrail.setFont(fntCheck);
/*     */     
/* 167 */     if (this.cbPineBarrens == null) {
/* 168 */       this.cbPineBarrens = new JCheckBox();
/*     */       
/* 170 */       this.cbPineBarrens.addActionListener(new ChangeActionListener());
/*     */     } 
/* 172 */     this.cbPineBarrens.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_03_PINE_BARRENS"));
/* 173 */     this.cbPineBarrens.setFont(fntCheck);
/*     */     
/* 175 */     if (this.cbHomestead == null) {
/* 176 */       this.cbHomestead = new JCheckBox();
/*     */       
/* 178 */       this.cbHomestead.addActionListener(new ChangeActionListener());
/*     */     } 
/* 180 */     this.cbHomestead.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_03_HOMESTEAD"));
/* 181 */     this.cbHomestead.setFont(fntCheck);
/*     */     
/* 183 */     if (this.cbRottingCropland == null) {
/* 184 */       this.cbRottingCropland = new JCheckBox();
/*     */       
/* 186 */       this.cbRottingCropland.addActionListener(new ChangeActionListener());
/*     */     } 
/* 188 */     this.cbRottingCropland.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_03_ROTTING_CROPLANDS"));
/* 189 */     this.cbRottingCropland.setFont(fntCheck);
/*     */     
/* 191 */     if (this.cbSorrowsBastion == null) {
/* 192 */       this.cbSorrowsBastion = new JCheckBox();
/*     */       
/* 194 */       this.cbSorrowsBastion.addActionListener(new ChangeActionListener());
/*     */     } 
/* 196 */     this.cbSorrowsBastion.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_03_SORROWS_BASTION"));
/* 197 */     this.cbSorrowsBastion.setFont(fntCheck);
/*     */     
/* 199 */     if (this.cbBloodGrove == null) {
/* 200 */       this.cbBloodGrove = new JCheckBox();
/*     */       
/* 202 */       this.cbBloodGrove.addActionListener(new ChangeActionListener());
/*     */     } 
/* 204 */     this.cbBloodGrove.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_03_BLOOD_GROVE"));
/* 205 */     this.cbBloodGrove.setFont(fntCheck);
/*     */     
/* 207 */     if (this.cbDarkvaleGate == null) {
/* 208 */       this.cbDarkvaleGate = new JCheckBox();
/*     */       
/* 210 */       this.cbDarkvaleGate.addActionListener(new ChangeActionListener());
/*     */     } 
/* 212 */     this.cbDarkvaleGate.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_03_DARKVALE_GATE"));
/* 213 */     this.cbDarkvaleGate.setFont(fntCheck);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 218 */     return this.changed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged(boolean changed) {
/* 223 */     this.changed = changed;
/*     */   }
/*     */   
/*     */   public void setSelected(boolean selected) {
/* 227 */     this.cbDeadmansGulch.setSelected(selected);
/* 228 */     this.cbProspectorsTrail.setSelected(selected);
/* 229 */     this.cbPineBarrens.setSelected(selected);
/* 230 */     this.cbHomestead.setSelected(selected);
/* 231 */     this.cbRottingCropland.setSelected(selected);
/* 232 */     this.cbSorrowsBastion.setSelected(selected);
/* 233 */     this.cbBloodGrove.setSelected(selected);
/* 234 */     this.cbDarkvaleGate.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getRiftList(boolean found) {
/* 239 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 241 */     if (this.cbDeadmansGulch.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_03_DEADMANS_GULCH); 
/* 242 */     if (this.cbProspectorsTrail.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_03_PROSPECTORS_TRAIL); 
/* 243 */     if (this.cbPineBarrens.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_03_PINE_BARRENS); 
/* 244 */     if (this.cbHomestead.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_03_HOMESTEAD); 
/* 245 */     if (this.cbRottingCropland.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_03_ROTTING_CROPLANDS); 
/* 246 */     if (this.cbSorrowsBastion.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_03_SORROWS_BASTION); 
/* 247 */     if (this.cbBloodGrove.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_03_BLOOD_GROVE); 
/* 248 */     if (this.cbDarkvaleGate.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_03_DARKVALE_GATE);
/*     */     
/* 250 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 255 */     setSelected(false);
/*     */     
/* 257 */     this.changed = false;
/*     */     
/* 259 */     if (gdc == null)
/*     */       return; 
/* 261 */     List<GDCharUID> list = gdc.getRiftList(this.difficulty);
/*     */     
/* 263 */     for (GDCharUID uid : list) {
/* 264 */       if (uid.equals(GDCharTeleportList.UID_RIFT_03_DEADMANS_GULCH)) this.cbDeadmansGulch.setSelected(true); 
/* 265 */       if (uid.equals(GDCharTeleportList.UID_RIFT_03_PROSPECTORS_TRAIL)) this.cbProspectorsTrail.setSelected(true); 
/* 266 */       if (uid.equals(GDCharTeleportList.UID_RIFT_03_PINE_BARRENS)) this.cbPineBarrens.setSelected(true); 
/* 267 */       if (uid.equals(GDCharTeleportList.UID_RIFT_03_HOMESTEAD)) this.cbHomestead.setSelected(true); 
/* 268 */       if (uid.equals(GDCharTeleportList.UID_RIFT_03_ROTTING_CROPLANDS)) this.cbRottingCropland.setSelected(true); 
/* 269 */       if (uid.equals(GDCharTeleportList.UID_RIFT_03_SORROWS_BASTION)) this.cbSorrowsBastion.setSelected(true); 
/* 270 */       if (uid.equals(GDCharTeleportList.UID_RIFT_03_BLOOD_GROVE)) this.cbBloodGrove.setSelected(true); 
/* 271 */       if (uid.equals(GDCharTeleportList.UID_RIFT_03_DARKVALE_GATE)) this.cbDarkvaleGate.setSelected(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\character\GDCharRiftAct3Pane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */