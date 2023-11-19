/*     */ package org.gdstash.ui;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowEvent;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import org.gdstash.util.GDImagePool;
/*     */ 
/*     */ public class GDFlexDialog extends JDialog {
/*     */   private Component owner;
/*     */   private JPanel panel;
/*     */   private JLabel lblImage;
/*     */   private JLabel lblIcon;
/*     */   private JLabel lblText;
/*     */   private JButton[] buttons;
/*     */   private int[] i;
/*     */   
/*     */   private class CloseActionListener implements ActionListener {
/*     */     private CloseActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  34 */       JButton btn = (JButton)e.getSource();
/*     */       
/*  36 */       for (int i = 0; i < GDFlexDialog.this.buttons.length; i++) {
/*  37 */         if (GDFlexDialog.this.buttons[i] == btn) {
/*  38 */           GDFlexDialog.this.i[0] = i;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/*  44 */       GDFlexDialog.this.setVisible(false);
/*  45 */       GDFlexDialog.this.dispatchEvent(new WindowEvent(GDFlexDialog.this, 201));
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
/*     */   public static int showDialog(String text, JButton[] buttons) {
/*  58 */     int[] i = { -1 };
/*     */     
/*  60 */     JDialog dialog = new GDFlexDialog(text, buttons, null, "", 0, false, i);
/*     */     
/*  62 */     dialog.setVisible(true);
/*     */     
/*  64 */     return i[0];
/*     */   }
/*     */   
/*     */   public static int showDialog(String text, JButton[] buttons, Frame owner) {
/*  68 */     int[] i = { -1 };
/*     */     
/*  70 */     JDialog dialog = new GDFlexDialog(text, buttons, owner, "", 0, false, i);
/*     */     
/*  72 */     dialog.setVisible(true);
/*     */     
/*  74 */     return i[0];
/*     */   }
/*     */   
/*     */   public static int showDialog(String text, JButton[] buttons, int iconType, Frame owner) {
/*  78 */     int[] i = { -1 };
/*     */     
/*  80 */     JDialog dialog = new GDFlexDialog(text, buttons, owner, "", iconType, false, i);
/*     */     
/*  82 */     dialog.setVisible(true);
/*     */     
/*  84 */     return i[0];
/*     */   }
/*     */   
/*     */   public static int showDialog(String text, JButton[] buttons, Frame owner, boolean modal) {
/*  88 */     int[] i = { -1 };
/*     */     
/*  90 */     JDialog dialog = new GDFlexDialog(text, buttons, owner, "", 0, modal, i);
/*     */     
/*  92 */     dialog.setVisible(true);
/*     */     
/*  94 */     return i[0];
/*     */   }
/*     */   
/*     */   public static int showDialog(String text, JButton[] buttons, int iconType, Frame owner, boolean modal) {
/*  98 */     int[] i = { -1 };
/*     */     
/* 100 */     JDialog dialog = new GDFlexDialog(text, buttons, owner, "", iconType, modal, i);
/*     */     
/* 102 */     dialog.setVisible(true);
/*     */     
/* 104 */     return i[0];
/*     */   }
/*     */   
/*     */   public static int showDialog(String text, JButton[] buttons, Frame owner, String title) {
/* 108 */     int[] i = { -1 };
/*     */     
/* 110 */     JDialog dialog = new GDFlexDialog(text, buttons, owner, title, 0, false, i);
/*     */     
/* 112 */     dialog.setVisible(true);
/*     */     
/* 114 */     return i[0];
/*     */   }
/*     */   
/*     */   public static int showDialog(String text, JButton[] buttons, int iconType, Frame owner, String title) {
/* 118 */     int[] i = { -1 };
/*     */     
/* 120 */     JDialog dialog = new GDFlexDialog(text, buttons, owner, title, iconType, false, i);
/*     */     
/* 122 */     dialog.setVisible(true);
/*     */     
/* 124 */     return i[0];
/*     */   }
/*     */   
/*     */   private GDFlexDialog(String text, JButton[] buttons, Frame owner, String title, int iconType, boolean modal, int[] i) {
/* 128 */     super(owner, title, modal);
/*     */     
/* 130 */     this.owner = owner;
/* 131 */     this.buttons = buttons;
/* 132 */     this.i = i;
/*     */     
/* 134 */     buildPanel(text, iconType);
/*     */   }
/*     */   
/*     */   protected void buildPanel(String text, int iconType) {
/* 138 */     this.panel = new JPanel();
/*     */     
/* 140 */     GroupLayout layout = null;
/* 141 */     GroupLayout.SequentialGroup hGroup = null;
/* 142 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 144 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 145 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 146 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */     
/* 148 */     this.lblImage = new JLabel();
/* 149 */     this.lblImage.setIcon(GDImagePool.iconProgress);
/* 150 */     this.lblImage.setBorder(compound);
/* 151 */     this.lblImage.setHorizontalAlignment(0);
/*     */     
/* 153 */     JPanel pnlText = buildTextPanel(text, iconType);
/* 154 */     JPanel pnlButton = buildButtonPanel();
/*     */     
/* 156 */     layout = new GroupLayout(this.panel);
/* 157 */     this.panel.setLayout(layout);
/*     */     
/* 159 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 162 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 165 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 168 */     hGroup
/* 169 */       .addGroup(layout.createParallelGroup()
/* 170 */         .addComponent(this.lblImage)
/* 171 */         .addComponent(pnlText)
/* 172 */         .addComponent(pnlButton));
/* 173 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 176 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 179 */     vGroup
/* 180 */       .addGroup(layout.createParallelGroup()
/* 181 */         .addComponent(this.lblImage))
/* 182 */       .addGroup(layout.createParallelGroup()
/* 183 */         .addComponent(pnlText))
/* 184 */       .addGroup(layout.createParallelGroup()
/* 185 */         .addComponent(pnlButton));
/* 186 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 188 */     layout.linkSize(0, new Component[] { this.lblImage, pnlText });
/* 189 */     layout.linkSize(0, new Component[] { this.lblImage, pnlButton });
/*     */     
/* 191 */     getContentPane().add(this.panel);
/*     */     
/* 193 */     pack();
/*     */     
/* 195 */     setLocationRelativeTo(this.owner);
/*     */     
/* 197 */     setIconImage(GDImagePool.iconLogo64x64.getImage());
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
/*     */   private JPanel buildButtonPanel() {
/* 211 */     JPanel panel = new JPanel();
/*     */     
/* 213 */     GroupLayout layout = null;
/* 214 */     GroupLayout.SequentialGroup hGroup = null;
/* 215 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 217 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 218 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/* 219 */     if (fntButton == null) fntButton = fntLabel;
/*     */     
/* 221 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 223 */     layout = new GroupLayout(panel);
/* 224 */     panel.setLayout(layout);
/*     */     
/* 226 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 229 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 232 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 235 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 238 */     GroupLayout.ParallelGroup pg = layout.createParallelGroup();
/*     */     int i;
/* 240 */     for (i = 0; i < this.buttons.length; i++) {
/* 241 */       if (this.buttons[i] != null) {
/*     */         
/* 243 */         this.buttons[i].setFont(fntButton);
/* 244 */         this.buttons[i].setPreferredSize(new Dimension(430 / this.buttons.length, fntButton.getSize() * 2));
/* 245 */         this.buttons[i].addActionListener(new CloseActionListener());
/*     */         
/* 247 */         hGroup.addGroup(layout.createParallelGroup().addComponent(this.buttons[i]));
/* 248 */         pg.addComponent(this.buttons[i]);
/*     */       } 
/*     */     } 
/* 251 */     vGroup
/* 252 */       .addGroup(pg);
/*     */     
/* 254 */     layout.setHorizontalGroup(hGroup);
/* 255 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 257 */     for (i = 1; i < this.buttons.length; i++) {
/* 258 */       if (this.buttons[i] != null)
/*     */       {
/* 260 */         layout.linkSize(0, new Component[] { this.buttons[0], this.buttons[i] });
/*     */       }
/*     */     } 
/* 263 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildTextPanel(String text, int iconType) {
/* 267 */     JPanel panel = new JPanel();
/*     */     
/* 269 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*     */     
/* 271 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 273 */     GroupLayout layout = null;
/* 274 */     GroupLayout.SequentialGroup hGroup = null;
/* 275 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 277 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 278 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 279 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */     
/* 281 */     panel.setBorder(compound);
/*     */     
/* 283 */     if (iconType != 0) {
/* 284 */       this.lblIcon = new JLabel();
/* 285 */       setIcon(iconType);
/* 286 */       this.lblIcon.setBorder(compound);
/*     */     } 
/*     */     
/* 289 */     this.lblText = new JLabel();
/* 290 */     this.lblText.setText(text);
/* 291 */     this.lblText.setVerticalAlignment(0);
/* 292 */     this.lblText.setFont(fntLabel);
/*     */     
/* 294 */     layout = new GroupLayout(panel);
/* 295 */     panel.setLayout(layout);
/*     */     
/* 297 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 300 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 303 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 306 */     if (iconType == 0) {
/* 307 */       hGroup
/* 308 */         .addGroup(layout.createParallelGroup()
/* 309 */           .addComponent(this.lblText));
/*     */     } else {
/* 311 */       hGroup
/* 312 */         .addGroup(layout.createParallelGroup()
/* 313 */           .addComponent(this.lblIcon))
/* 314 */         .addGroup(layout.createParallelGroup()
/* 315 */           .addComponent(this.lblText));
/*     */     } 
/* 317 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 320 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 323 */     if (iconType == 0) {
/* 324 */       vGroup
/* 325 */         .addGroup(layout.createParallelGroup()
/* 326 */           .addComponent(this.lblText));
/*     */     } else {
/* 328 */       vGroup
/* 329 */         .addGroup(layout.createParallelGroup()
/* 330 */           .addComponent(this.lblIcon)
/* 331 */           .addComponent(this.lblText));
/*     */     } 
/* 333 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 335 */     if (iconType != 0) layout.linkSize(1, new Component[] { this.lblIcon, this.lblText });
/*     */     
/* 337 */     return panel;
/*     */   }
/*     */   
/*     */   private void setIcon(int iconType) {
/* 341 */     switch (iconType) {
/*     */       case 1:
/* 343 */         this.lblIcon.setIcon(GDImagePool.iconMsgSuccess32);
/*     */         return;
/*     */       
/*     */       case 2:
/* 347 */         this.lblIcon.setIcon(GDImagePool.iconMsgInfo32);
/*     */         return;
/*     */       
/*     */       case 3:
/* 351 */         this.lblIcon.setIcon(GDImagePool.iconMsgWarning32);
/*     */         return;
/*     */       
/*     */       case 4:
/* 355 */         this.lblIcon.setIcon(GDImagePool.iconMsgError32);
/*     */         return;
/*     */     } 
/*     */     
/* 359 */     this.lblIcon.setIcon((Icon)null);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\GDFlexDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */