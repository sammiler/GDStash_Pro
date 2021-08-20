/*     */ package org.gdstash.ui.font;
/*     */ 
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.KeyStroke;
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
/*     */ public class EscapeDialog
/*     */   extends JDialog
/*     */ {
/*     */   protected JButton okButton;
/*     */   
/*     */   public EscapeDialog() {}
/*     */   
/*     */   public EscapeDialog(Dialog owner, boolean modal) {
/*  33 */     super(owner, modal);
/*     */   }
/*     */   
/*     */   public EscapeDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
/*  37 */     super(owner, title, modal, gc);
/*     */   }
/*     */   
/*     */   public EscapeDialog(Dialog owner, String title, boolean modal) {
/*  41 */     super(owner, title, modal);
/*     */   }
/*     */   
/*     */   public EscapeDialog(Dialog owner, String title) {
/*  45 */     super(owner, title);
/*     */   }
/*     */   
/*     */   public EscapeDialog(Dialog owner) {
/*  49 */     super(owner);
/*     */   }
/*     */   
/*     */   public EscapeDialog(Frame owner, boolean modal) {
/*  53 */     super(owner, modal);
/*     */   }
/*     */   
/*     */   public EscapeDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
/*  57 */     super(owner, title, modal, gc);
/*     */   }
/*     */   
/*     */   public EscapeDialog(Frame owner, String title, boolean modal) {
/*  61 */     super(owner, title, modal);
/*     */   }
/*     */   
/*     */   public EscapeDialog(Frame owner, String title) {
/*  65 */     super(owner, title);
/*     */   }
/*     */   
/*     */   public EscapeDialog(Frame owner) {
/*  69 */     super(owner);
/*     */   }
/*     */   
/*     */   public EscapeDialog(Window owner, Dialog.ModalityType modalityType) {
/*  73 */     super(owner, modalityType);
/*     */   }
/*     */   
/*     */   public EscapeDialog(Window owner, String title, Dialog.ModalityType modalityType, GraphicsConfiguration gc) {
/*  77 */     super(owner, title, modalityType, gc);
/*     */   }
/*     */   
/*     */   public EscapeDialog(Window owner, String title, Dialog.ModalityType modalityType) {
/*  81 */     super(owner, title, modalityType);
/*     */   }
/*     */   
/*     */   public EscapeDialog(Window owner, String title) {
/*  85 */     super(owner, title);
/*     */   }
/*     */   
/*     */   public EscapeDialog(Window owner) {
/*  89 */     super(owner);
/*     */   }
/*     */ 
/*     */   
/*     */   protected JRootPane createRootPane() {
/*  94 */     JRootPane rootPane = new JRootPane();
/*  95 */     KeyStroke escapeStroke = KeyStroke.getKeyStroke("ESCAPE");
/*     */     
/*  97 */     Action escapeActionListener = new AbstractAction()
/*     */       {
/*     */         public void actionPerformed(ActionEvent event) {
/* 100 */           EscapeDialog.this.setVisible(false);
/*     */         }
/*     */       };
/*     */     
/* 104 */     KeyStroke enterStroke = KeyStroke.getKeyStroke("ENTER");
/*     */     
/* 106 */     Action enterActionListener = new AbstractAction()
/*     */       {
/*     */         public void actionPerformed(ActionEvent event) {
/* 109 */           EscapeDialog.this.okButton.doClick();
/*     */         }
/*     */       };
/*     */     
/* 113 */     InputMap inputMap = rootPane.getInputMap(2);
/* 114 */     inputMap.put(escapeStroke, "ESCAPE");
/* 115 */     inputMap.put(enterStroke, "ENTER");
/*     */     
/* 117 */     rootPane.getActionMap().put("ESCAPE", escapeActionListener);
/* 118 */     rootPane.getActionMap().put("ENTER", enterActionListener);
/*     */     
/* 120 */     return rootPane;
/*     */   }
/*     */   
/*     */   public JButton getOkButton() {
/* 124 */     return this.okButton;
/*     */   }
/*     */   
/*     */   public void setOkButton(JButton okButton) {
/* 128 */     this.okButton = okButton;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\font\EscapeDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */