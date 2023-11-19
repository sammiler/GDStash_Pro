/*     */ package org.gdstash.ui;
/*     */ 
/*     */ import java.awt.Component;
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
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDDialog
/*     */   extends JDialog
/*     */ {
/*     */   public static final int ICON_NONE = 0;
/*     */   public static final int ICON_SUCCESS = 1;
/*     */   public static final int ICON_INFO = 2;
/*     */   public static final int ICON_WARNING = 3;
/*     */   public static final int ICON_ERROR = 4;
/*     */   private Component owner;
/*     */   private JPanel panel;
/*     */   private JLabel lblIcon;
/*     */   private JLabel lblText;
/*     */   private JButton btnClose;
/*     */   
/*     */   private class CloseActionListener
/*     */     implements ActionListener {
/*     */     private CloseActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  40 */       GDDialog.this.setVisible(false);
/*  41 */       GDDialog.this.dispatchEvent(new WindowEvent(GDDialog.this, 201));
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
/*     */   public GDDialog(String text) {
/*  55 */     this.owner = null;
/*     */     
/*  57 */     buildPanel(text, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GDDialog(String text, int iconType) {
/*  63 */     this.owner = null;
/*     */     
/*  65 */     buildPanel(text, iconType);
/*     */   }
/*     */   
/*     */   public GDDialog(String text, Frame owner) {
/*  69 */     this(text, 0, owner, "", false);
/*     */   }
/*     */   
/*     */   public GDDialog(String text, int iconType, Frame owner) {
/*  73 */     this(text, iconType, owner, "", false);
/*     */   }
/*     */   
/*     */   public GDDialog(String text, Frame owner, boolean modal) {
/*  77 */     this(text, 0, owner, "", modal);
/*     */   }
/*     */   
/*     */   public GDDialog(String text, int iconType, Frame owner, boolean modal) {
/*  81 */     this(text, iconType, owner, "", modal);
/*     */   }
/*     */   
/*     */   public GDDialog(String text, Frame owner, String title) {
/*  85 */     this(text, 0, owner, title, false);
/*     */   }
/*     */   
/*     */   public GDDialog(String text, int iconType, Frame owner, String title) {
/*  89 */     this(text, iconType, owner, title, false);
/*     */   }
/*     */   
/*     */   public GDDialog(String text, int iconType, Frame owner, String title, boolean modal) {
/*  93 */     super(owner, title, modal);
/*     */     
/*  95 */     this.owner = owner;
/*     */     
/*  97 */     buildPanel(text, iconType);
/*     */   }
/*     */   
/*     */   protected void buildPanel(String text, int iconType) {
/* 101 */     this.panel = new JPanel();
/*     */     
/* 103 */     GroupLayout layout = null;
/* 104 */     GroupLayout.SequentialGroup hGroup = null;
/* 105 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 107 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 108 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 109 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */     
/* 111 */     JPanel pnlText = buildTextPanel(text, iconType);
/*     */     
/* 113 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 114 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/* 115 */     if (fntButton == null) fntButton = fntLabel;
/*     */     
/* 117 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 118 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 120 */     this.btnClose = new JButton(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_OK"));
/*     */     
/* 122 */     this.btnClose.setFont(fntButton);
/* 123 */     this.btnClose.addActionListener(new CloseActionListener());
/*     */     
/* 125 */     layout = new GroupLayout(this.panel);
/* 126 */     this.panel.setLayout(layout);
/*     */     
/* 128 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 131 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 134 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 137 */     hGroup
/* 138 */       .addGroup(layout.createParallelGroup()
/* 139 */         .addComponent(pnlText)
/* 140 */         .addComponent(this.btnClose));
/* 141 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 144 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 147 */     vGroup
/* 148 */       .addGroup(layout.createParallelGroup()
/* 149 */         .addComponent(pnlText))
/* 150 */       .addGroup(layout.createParallelGroup()
/* 151 */         .addComponent(this.btnClose));
/* 152 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 154 */     layout.linkSize(0, new Component[] { pnlText, this.btnClose });
/*     */     
/* 156 */     getContentPane().add(this.panel);
/*     */     
/* 158 */     pack();
/*     */     
/* 160 */     setLocationRelativeTo(this.owner);
/*     */   }
/*     */   
/*     */   private JPanel buildTextPanel(String text, int iconType) {
/* 164 */     JPanel panel = new JPanel();
/*     */     
/* 166 */     GroupLayout layout = null;
/* 167 */     GroupLayout.SequentialGroup hGroup = null;
/* 168 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 170 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 171 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 172 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */ 
/*     */ 
/*     */     
/* 176 */     this.lblIcon = new JLabel();
/* 177 */     setIcon(iconType);
/* 178 */     this.lblIcon.setBorder(compound);
/*     */     
/* 180 */     this.lblText = new JLabel();
/* 181 */     this.lblText.setText(text);
/* 182 */     this.lblText.setVerticalAlignment(0);
/*     */     
/* 184 */     layout = new GroupLayout(panel);
/* 185 */     panel.setLayout(layout);
/*     */     
/* 187 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 190 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 193 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 196 */     hGroup
/* 197 */       .addGroup(layout.createParallelGroup()
/* 198 */         .addComponent(this.lblIcon))
/* 199 */       .addGroup(layout.createParallelGroup()
/* 200 */         .addComponent(this.lblText));
/* 201 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 204 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 207 */     vGroup
/* 208 */       .addGroup(layout.createParallelGroup()
/* 209 */         .addComponent(this.lblIcon)
/* 210 */         .addComponent(this.lblText));
/* 211 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 213 */     layout.linkSize(1, new Component[] { this.lblIcon, this.lblText });
/*     */     
/* 215 */     return panel;
/*     */   }
/*     */   
/*     */   private void setIcon(int iconType) {
/* 219 */     switch (iconType) {
/*     */       case 1:
/* 221 */         this.lblIcon.setIcon(GDImagePool.iconMsgSuccess32);
/*     */         return;
/*     */       
/*     */       case 2:
/* 225 */         this.lblIcon.setIcon(GDImagePool.iconMsgInfo32);
/*     */         return;
/*     */       
/*     */       case 3:
/* 229 */         this.lblIcon.setIcon(GDImagePool.iconMsgWarning32);
/*     */         return;
/*     */       
/*     */       case 4:
/* 233 */         this.lblIcon.setIcon(GDImagePool.iconMsgError32);
/*     */         return;
/*     */     } 
/*     */     
/* 237 */     this.lblIcon.setIcon((Icon)null);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\GDDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */