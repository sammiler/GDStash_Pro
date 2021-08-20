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
/*     */ public class GDCharRiftAct2Pane extends GDAbstractRiftPane {
/*     */   private JCheckBox cbArkovianFoothills;
/*     */   private JCheckBox cbOldArkovia;
/*     */   private JCheckBox cbCronleysHideout;
/*     */   private JCheckBox cbTwinFalls;
/*     */   private JCheckBox cbBrokenHills;
/*     */   private JCheckBox cbSmugglersPass;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   private class ChangeActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  35 */       GDCharRiftAct2Pane.this.changed = true;
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
/*     */   public GDCharRiftAct2Pane(int difficulty, int direction) {
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
/*  73 */         .addComponent(this.cbArkovianFoothills)
/*  74 */         .addComponent(this.cbOldArkovia)
/*  75 */         .addComponent(this.cbCronleysHideout)
/*  76 */         .addComponent(this.cbTwinFalls)
/*  77 */         .addComponent(this.cbBrokenHills)
/*  78 */         .addComponent(this.cbSmugglersPass));
/*     */ 
/*     */     
/*  81 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  84 */     vGroup
/*  85 */       .addGroup(layout.createParallelGroup()
/*  86 */         .addComponent(this.cbArkovianFoothills))
/*  87 */       .addGroup(layout.createParallelGroup()
/*  88 */         .addComponent(this.cbOldArkovia))
/*  89 */       .addGroup(layout.createParallelGroup()
/*  90 */         .addComponent(this.cbCronleysHideout))
/*  91 */       .addGroup(layout.createParallelGroup()
/*  92 */         .addComponent(this.cbTwinFalls))
/*  93 */       .addGroup(layout.createParallelGroup()
/*  94 */         .addComponent(this.cbBrokenHills))
/*  95 */       .addGroup(layout.createParallelGroup()
/*  96 */         .addComponent(this.cbSmugglersPass));
/*     */     
/*  98 */     if (direction == 0) {
/*  99 */       layout.setHorizontalGroup(vGroup);
/* 100 */       layout.setVerticalGroup(hGroup);
/*     */     } else {
/* 102 */       layout.setHorizontalGroup(hGroup);
/* 103 */       layout.setVerticalGroup(vGroup);
/*     */     } 
/*     */     
/* 106 */     layout.linkSize(0, new Component[] { this.cbArkovianFoothills, this.cbOldArkovia });
/* 107 */     layout.linkSize(0, new Component[] { this.cbArkovianFoothills, this.cbCronleysHideout });
/* 108 */     layout.linkSize(0, new Component[] { this.cbArkovianFoothills, this.cbTwinFalls });
/* 109 */     layout.linkSize(0, new Component[] { this.cbArkovianFoothills, this.cbBrokenHills });
/* 110 */     layout.linkSize(0, new Component[] { this.cbArkovianFoothills, this.cbSmugglersPass });
/*     */     
/* 112 */     layout.linkSize(1, new Component[] { this.cbArkovianFoothills, this.cbOldArkovia });
/* 113 */     layout.linkSize(1, new Component[] { this.cbArkovianFoothills, this.cbCronleysHideout });
/* 114 */     layout.linkSize(1, new Component[] { this.cbArkovianFoothills, this.cbTwinFalls });
/* 115 */     layout.linkSize(1, new Component[] { this.cbArkovianFoothills, this.cbBrokenHills });
/* 116 */     layout.linkSize(1, new Component[] { this.cbArkovianFoothills, this.cbSmugglersPass });
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
/* 134 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_ACT2"));
/* 135 */     text.setTitleFont(fntBorder);
/*     */     
/* 137 */     setBorder(text);
/*     */     
/* 139 */     if (this.cbArkovianFoothills == null) {
/* 140 */       this.cbArkovianFoothills = new JCheckBox();
/*     */       
/* 142 */       this.cbArkovianFoothills.addActionListener(new ChangeActionListener());
/*     */     } 
/* 144 */     this.cbArkovianFoothills.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_02_ARKOVIAN_FOOTHILLS"));
/* 145 */     this.cbArkovianFoothills.setFont(fntCheck);
/*     */     
/* 147 */     if (this.cbOldArkovia == null) {
/* 148 */       this.cbOldArkovia = new JCheckBox();
/*     */       
/* 150 */       this.cbOldArkovia.addActionListener(new ChangeActionListener());
/*     */     } 
/* 152 */     this.cbOldArkovia.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_02_OLD_ARKOVIA"));
/* 153 */     this.cbOldArkovia.setFont(fntCheck);
/*     */     
/* 155 */     if (this.cbCronleysHideout == null) {
/* 156 */       this.cbCronleysHideout = new JCheckBox();
/*     */       
/* 158 */       this.cbCronleysHideout.addActionListener(new ChangeActionListener());
/*     */     } 
/* 160 */     this.cbCronleysHideout.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_02_CRONLEYS_HIDEOUT"));
/* 161 */     this.cbCronleysHideout.setFont(fntCheck);
/*     */     
/* 163 */     if (this.cbTwinFalls == null) {
/* 164 */       this.cbTwinFalls = new JCheckBox();
/*     */       
/* 166 */       this.cbTwinFalls.addActionListener(new ChangeActionListener());
/*     */     } 
/* 168 */     this.cbTwinFalls.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_02_TWIN_FALLS"));
/* 169 */     this.cbTwinFalls.setFont(fntCheck);
/*     */     
/* 171 */     if (this.cbBrokenHills == null) {
/* 172 */       this.cbBrokenHills = new JCheckBox();
/*     */       
/* 174 */       this.cbBrokenHills.addActionListener(new ChangeActionListener());
/*     */     } 
/* 176 */     this.cbBrokenHills.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_02_BROKEN_HILLS"));
/* 177 */     this.cbBrokenHills.setFont(fntCheck);
/*     */     
/* 179 */     if (this.cbSmugglersPass == null) {
/* 180 */       this.cbSmugglersPass = new JCheckBox();
/*     */       
/* 182 */       this.cbSmugglersPass.addActionListener(new ChangeActionListener());
/*     */     } 
/* 184 */     this.cbSmugglersPass.setText(GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_RIFT_02_SMUGGLERS_PASS"));
/* 185 */     this.cbSmugglersPass.setFont(fntCheck);
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
/* 199 */     this.cbArkovianFoothills.setSelected(selected);
/* 200 */     this.cbOldArkovia.setSelected(selected);
/* 201 */     this.cbCronleysHideout.setSelected(selected);
/* 202 */     this.cbTwinFalls.setSelected(selected);
/* 203 */     this.cbBrokenHills.setSelected(selected);
/* 204 */     this.cbSmugglersPass.setSelected(selected);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDCharUID> getRiftList(boolean found) {
/* 209 */     List<GDCharUID> list = new LinkedList<>();
/*     */     
/* 211 */     if (this.cbArkovianFoothills.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_02_ARKOVIAN_FOOTHILLS); 
/* 212 */     if (this.cbOldArkovia.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_02_OLD_ARKOVIA); 
/* 213 */     if (this.cbCronleysHideout.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_02_CRONLEYS_HIDEOUT); 
/* 214 */     if (this.cbTwinFalls.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_02_TWIN_FALLS); 
/* 215 */     if (this.cbBrokenHills.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_02_BROKEN_HILLS); 
/* 216 */     if (this.cbSmugglersPass.isSelected() == found) list.add(GDCharTeleportList.UID_RIFT_02_SMUGGLERS_PASS);
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
/* 232 */       if (uid.equals(GDCharTeleportList.UID_RIFT_02_ARKOVIAN_FOOTHILLS)) this.cbArkovianFoothills.setSelected(true); 
/* 233 */       if (uid.equals(GDCharTeleportList.UID_RIFT_02_OLD_ARKOVIA)) this.cbOldArkovia.setSelected(true); 
/* 234 */       if (uid.equals(GDCharTeleportList.UID_RIFT_02_CRONLEYS_HIDEOUT)) this.cbCronleysHideout.setSelected(true); 
/* 235 */       if (uid.equals(GDCharTeleportList.UID_RIFT_02_TWIN_FALLS)) this.cbTwinFalls.setSelected(true); 
/* 236 */       if (uid.equals(GDCharTeleportList.UID_RIFT_02_BROKEN_HILLS)) this.cbBrokenHills.setSelected(true); 
/* 237 */       if (uid.equals(GDCharTeleportList.UID_RIFT_02_SMUGGLERS_PASS)) this.cbSmugglersPass.setSelected(true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\character\GDCharRiftAct2Pane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */