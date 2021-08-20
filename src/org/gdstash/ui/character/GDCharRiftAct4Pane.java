/*     */ package org.gdstash.ui.character;
/*     */ 
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
/*     */ import org.gdstash.character.GDCharTeleportList;
/*     */ import org.gdstash.character.GDCharUID;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.util.GDAbstractRiftPane;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDCharRiftAct4Pane extends GDAbstractRiftPane {
/*     */   private JCheckBox cbAsterkarnMountains;
/*     */   private JCheckBox cbAsterkarnRoad;
/*     */   private JCheckBox cbAsterkarnValley;
/*     */   private JCheckBox cbFortIkon;
/*     */   private JCheckBox cbGatesOfNecropolis;
/*     */   private JCheckBox cbNecropolisInterior;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   private class ChangeActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  35 */       GDCharRiftAct4Pane.this.changed = true;
/*     */     }
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
/*     */   public GDCharRiftAct4Pane(int difficulty, int direction) {
/*  50 */     this.difficulty = difficulty;
/*  51 */     this.changed = false;
/*     */     
/*  53 */     adjustUI();
/*     */     
/*  55 */     GroupLayout layout = null;
/*  56 */     GroupLayout.SequentialGroup hGroup = null;
/*  57 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  59 */     layout = new GroupLayout((Container)this);
/*  60 */     setLayout(layout);
/*     */     
/*  62 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/*  65 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/*  68 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  71 */     hGroup
/*  72 */       .addGroup(layout.createParallelGroup()
/*  73 */         .addComponent(this.cbAsterkarnMountains)
/*  74 */         .addComponent(this.cbAsterkarnRoad)
/*  75 */         .addComponent(this.cbAsterkarnValley)
/*  76 */         .addComponent(this.cbFortIkon)
/*  77 */         .addComponent(this.cbGatesOfNecropolis)
/*  78 */         .addComponent(this.cbNecropolisInterior));
/*     */ 
/*     */     
/*  81 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  84 */     vGroup
/*  85 */       .addGroup(layout.createParallelGroup()
/*  86 */         .addComponent(this.cbAsterkarnMountains))
/*  87 */       .addGroup(layout.createParallelGroup()
/*  88 */         .addComponent(this.cbAsterkarnRoad))
/*  89 */       .addGroup(layout.createParallelGroup()
/*  90 */         .addComponent(this.cbAsterkarnValley))
/*  91 */       .addGroup(layout.createParallelGroup()
/*  92 */         .addComponent(this.cbFortIkon))
/*  93 */       .addGroup(layout.createParallelGroup()
/*  94 */         .addComponent(this.cbGatesOfNecropolis))
/*  95 */       .addGroup(layout.createParallelGroup()
/*  96 */         .addComponent(this.cbNecropolisInterior));
/*     */     
/*  98 */     if (direction == 0) {
/*  99 */       layout.setHorizontalGroup(vGroup);
/* 100 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 102 */       layout.setHorizontalGroup(hGroup);
/* 103 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 106 */     layout.linkSize(0, new Component[] { this.cbAsterkarnMountains, this.cbAsterkarnRoad });
/* 107 */     layout.linkSize(0, new Component[] { this.cbAsterkarnMountains, this.cbAsterkarnValley });
/* 108 */     layout.linkSize(0, new Component[] { this.cbAsterkarnMountains, this.cbFortIkon });
/* 109 */     layout.linkSize(0, new Component[] { this.cbAsterkarnMountains, this.cbGatesOfNecropolis });
/* 110 */     layout.linkSize(0, new Component[] { this.cbAsterkarnMountains, this.cbNecropolisInterior });
/*     */     
/* 112 */     layout.linkSize(1, new Component[] { this.cbAsterkarnMountains, this.cbAsterkarnRoad });
/* 113 */     layout.linkSize(1, new Component[] { this.cbAsterkarnMountains, this.cbAsterkarnValley });
/* 114 */     layout.linkSize(1, new Component[] { this.cbAsterkarnMountains, this.cbFortIkon });
/* 115 */     layout.linkSize(1, new Component[] { this.cbAsterkarnMountains, this.cbGatesOfNecropolis });
/* 116 */     layout.linkSize(1, new Component[] { this.cbAsterkarnMountains, this.cbNecropolisInterior });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 121 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 122 */     Font fntCheck = UIManager.getDefaults().getFont("CheckBox.font");
/* 123 */     if (fntCheck == null) fntCheck = fntLabel; 
/* 124 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 125 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 127 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 128 */     fntCheck = fntCheck.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 129 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 131 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 132 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 133 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 134 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ACT4"));
/* 135 */     text.setTitleFont(fntBorder);
/*     */     
/* 137 */     setBorder(text);
/*     */     
/* 139 */     if (this.cbAsterkarnMountains == null) {
/* 140 */       this.cbAsterkarnMountains = new JCheckBox();
/*     */       
/* 142 */       this.cbAsterkarnMountains.addActionListener(new ChangeActionListener());
/*     */     } 
/* 144 */     this.cbAsterkarnMountains.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_04_ASTERKARN_MOUNTAINS"));
/* 145 */     this.cbAsterkarnMountains.setFont(fntCheck);
/*     */     
/* 147 */     if (this.cbAsterkarnRoad == null) {
/* 148 */       this.cbAsterkarnRoad = new JCheckBox();
/*     */       
/* 150 */       this.cbAsterkarnRoad.addActionListener(new ChangeActionListener());
/*     */     } 
/* 152 */     this.cbAsterkarnRoad.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_04_ASTERKARN_ROAD"));
/* 153 */     this.cbAsterkarnRoad.setFont(fntCheck);
/*     */     
/* 155 */     if (this.cbAsterkarnValley == null) {
/* 156 */       this.cbAsterkarnValley = new JCheckBox();
/*     */       
/* 158 */       this.cbAsterkarnValley.addActionListener(new ChangeActionListener());
/*     */     } 
/* 160 */     this.cbAsterkarnValley.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_04_ASTERKARN_VALLEY"));
/* 161 */     this.cbAsterkarnValley.setFont(fntCheck);
/*     */     
/* 163 */     if (this.cbFortIkon == null) {
/* 164 */       this.cbFortIkon = new JCheckBox();
/*     */       
/* 166 */       this.cbFortIkon.addActionListener(new ChangeActionListener());
/*     */     } 
/* 168 */     this.cbFortIkon.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_04_FORT_IKON"));
/* 169 */     this.cbFortIkon.setFont(fntCheck);
/*     */     
/* 171 */     if (this.cbGatesOfNecropolis == null) {
/* 172 */       this.cbGatesOfNecropolis = new JCheckBox();
/*     */       
/* 174 */       this.cbGatesOfNecropolis.addActionListener(new ChangeActionListener());
/*     */     } 
/* 176 */     this.cbGatesOfNecropolis.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_04_GATES_OF_NECROPOLIS"));
/* 177 */     this.cbGatesOfNecropolis.setFont(fntCheck);
/*     */     
/* 179 */     if (this.cbNecropolisInterior == null) {
/* 180 */       this.cbNecropolisInterior = new JCheckBox();
/*     */       
/* 182 */       this.cbNecropolisInterior.addActionListener(new ChangeActionListener());
/*     */     } 
/* 184 */     this.cbNecropolisInterior.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_04_NECROPOLIS_INTERIOR"));
/* 185 */     this.cbNecropolisInterior.setFont(fntCheck);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 190 */     return this.changed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged(boolean changed) {
/* 195 */     this.changed = changed;
/*     */   }
/*     */   
/*     */   public void setSelected(boolean selected) {
/* 199 */     this.cbAsterkarnMountains.setSelected(selected);
/* 200 */     this.cbAsterkarnRoad.setSelected(selected);
/* 201 */     this.cbAsterkarnValley.setSelected(selected);
/* 202 */     this.cbFortIkon.setSelected(selected);
/* 203 */     this.cbGatesOfNecropolis.setSelected(selected);
/* 204 */     this.cbNecropolisInterior.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getRiftList(boolean found) {
/* 209 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 211 */     if (this.cbAsterkarnMountains.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_04_ASTERKARN_MOUNTAINS); 
/* 212 */     if (this.cbAsterkarnRoad.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_04_ASTERKARN_ROAD); 
/* 213 */     if (this.cbAsterkarnValley.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_04_ASTERKARN_VALLEY); 
/* 214 */     if (this.cbFortIkon.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_04_FORT_IKON); 
/* 215 */     if (this.cbGatesOfNecropolis.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_04_GATES_OF_NECROPOLIS); 
/* 216 */     if (this.cbNecropolisInterior.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_04_NECROPOLIS_INTERIOR);
/*     */     
/* 218 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 223 */     setSelected(false);
/*     */     
/* 225 */     this.changed = false;
/*     */     
/* 227 */     if (gdc == null)
/*     */       return; 
/* 229 */     List<GDCharUID> list = gdc.getRiftList(this.difficulty);
/*     */     
/* 231 */     for (GDCharUID uid : list) {
/* 232 */       if (uid.equals(GDCharTeleportList.UID_RIFT_04_ASTERKARN_MOUNTAINS)) this.cbAsterkarnMountains.setSelected(true); 
/* 233 */       if (uid.equals(GDCharTeleportList.UID_RIFT_04_ASTERKARN_ROAD)) this.cbAsterkarnRoad.setSelected(true); 
/* 234 */       if (uid.equals(GDCharTeleportList.UID_RIFT_04_ASTERKARN_VALLEY)) this.cbAsterkarnValley.setSelected(true); 
/* 235 */       if (uid.equals(GDCharTeleportList.UID_RIFT_04_FORT_IKON)) this.cbFortIkon.setSelected(true); 
/* 236 */       if (uid.equals(GDCharTeleportList.UID_RIFT_04_GATES_OF_NECROPOLIS)) this.cbGatesOfNecropolis.setSelected(true); 
/* 237 */       if (uid.equals(GDCharTeleportList.UID_RIFT_04_NECROPOLIS_INTERIOR)) this.cbNecropolisInterior.setSelected(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\character\GDCharRiftAct4Pane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */