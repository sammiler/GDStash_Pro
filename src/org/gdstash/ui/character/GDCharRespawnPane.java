/*     */ package org.gdstash.ui.character;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import org.gdstash.character.GDChar;
/*     */ import org.gdstash.character.GDCharRespawnList;
/*     */ import org.gdstash.character.GDCharUID;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDCharRespawnPane
/*     */   extends AdjustablePanel {
/*     */   public static final int SEL_SPAWN_NONE = 0;
/*     */   public static final int SEL_SPAWN_DEVILS_CROSSING = 1;
/*     */   public static final int SEL_SPAWN_HOMESTAEAD = 2;
/*     */   public static final int SEL_SPAWN_FORT_IKON = 3;
/*     */   
/*     */   private class ChangeActionListener
/*     */     implements ActionListener {
/*     */     private ChangeActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  33 */       GDCharRespawnPane.this.changed = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   private static String[] spawnPoints = null;
/*     */   
/*     */   static {
/*  45 */     spawnPoints = new String[4];
/*     */     
/*  47 */     spawnPoints[0] = "";
/*  48 */     spawnPoints[1] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SPAWN_DEVILS_CROSSING");
/*  49 */     spawnPoints[2] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SPAWN_HOMESTEAD");
/*  50 */     spawnPoints[3] = GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SPAWN_FORT_IKON");
/*     */   }
/*     */ 
/*     */   
/*     */   private JComboBox cbRespawn;
/*     */   private int difficulty;
/*     */   private boolean changed;
/*     */   
/*     */   public GDCharRespawnPane(int difficulty) {
/*  59 */     this.difficulty = difficulty;
/*  60 */     this.changed = false;
/*     */     
/*  62 */     adjustUI();
/*     */     
/*  64 */     GroupLayout layout = null;
/*  65 */     GroupLayout.SequentialGroup hGroup = null;
/*  66 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  68 */     layout = new GroupLayout((Container)this);
/*  69 */     setLayout(layout);
/*     */     
/*  71 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/*  74 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/*  77 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  80 */     hGroup
/*  81 */       .addGroup(layout.createParallelGroup()
/*  82 */         .addComponent(this.cbRespawn));
/*  83 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  86 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  89 */     vGroup
/*  90 */       .addGroup(layout.createParallelGroup()
/*  91 */         .addComponent(this.cbRespawn));
/*     */     
/*  93 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  98 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  99 */     Font fntCombo = UIManager.getDefaults().getFont("ComboBox.font");
/* 100 */     if (fntCombo == null) fntCombo = fntLabel; 
/* 101 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 102 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 104 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 105 */     fntCombo = fntCombo.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 106 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 108 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 109 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 110 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 111 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_SPAWN_POINT"));
/* 112 */     text.setTitleFont(fntBorder);
/*     */     
/* 114 */     setBorder(text);
/*     */     
/* 116 */     if (this.cbRespawn == null) {
/* 117 */       this.cbRespawn = new JComboBox<>(spawnPoints);
/* 118 */       this.cbRespawn.addActionListener(new ChangeActionListener());
/*     */     } 
/* 120 */     this.cbRespawn.setFont(fntCombo);
/* 121 */     this.cbRespawn.setMaximumSize(new Dimension(1000, 3 * GDStashFrame.iniConfig.sectUI.fontSize));
/*     */   }
/*     */   
/*     */   public boolean hasChanged() {
/* 125 */     return this.changed;
/*     */   }
/*     */   
/*     */   public GDCharUID getSpawnPoint() {
/* 129 */     int idx = this.cbRespawn.getSelectedIndex();
/*     */     
/* 131 */     GDCharUID spawn = null;
/*     */     
/* 133 */     switch (idx) { case 1:
/* 134 */         spawn = GDCharRespawnList.UID_SPAWN_DEVILS_CROSSING; break;
/*     */       case 2:
/* 136 */         spawn = GDCharRespawnList.UID_SPAWN_HOMESTEAD; break;
/*     */       case 3:
/* 138 */         spawn = GDCharRespawnList.UID_SPAWN_FORT_IKON;
/*     */         break; }
/*     */ 
/*     */     
/* 142 */     return spawn;
/*     */   }
/*     */   
/*     */   public void setChar(GDChar gdc) {
/* 146 */     this.changed = false;
/*     */     
/* 148 */     GDCharUID uid = null;
/*     */     
/* 150 */     if (gdc != null) uid = gdc.getRespawnPoint(this.difficulty);
/*     */     
/* 152 */     if (uid == null) {
/* 153 */       this.cbRespawn.setSelectedIndex(0);
/*     */     } else {
/* 155 */       if (uid.equals(GDCharRespawnList.UID_SPAWN_DEVILS_CROSSING)) this.cbRespawn.setSelectedIndex(1); 
/* 156 */       if (uid.equals(GDCharRespawnList.UID_SPAWN_HOMESTEAD)) this.cbRespawn.setSelectedIndex(2); 
/* 157 */       if (uid.equals(GDCharRespawnList.UID_SPAWN_FORT_IKON)) this.cbRespawn.setSelectedIndex(3); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateChar(GDChar gdc) {
/* 162 */     if (gdc == null)
/*     */       return; 
/* 164 */     if (!this.changed)
/*     */       return; 
/* 166 */     GDCharUID spawn = getSpawnPoint();
/*     */     
/* 168 */     if (spawn != null) gdc.setRespawnPoint(this.difficulty, spawn);
/*     */     
/* 170 */     this.changed = false;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\character\GDCharRespawnPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */