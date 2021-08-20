/*     */ package org.gdstash.ui.util;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.ComboBoxModel;
/*     */ import javax.swing.JComboBox;
/*     */ 
/*     */ public class EnabledComboBox<E>
/*     */   extends JComboBox<E>
/*     */ {
/*     */   private List<ActionListener> actionListeners;
/*     */   private EnabledComboBoxRenderer<E> renderer;
/*     */   
/*     */   private class ComboListener
/*     */     implements ActionListener
/*     */   {
/*     */     private ComboListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  22 */       int index = EnabledComboBox.this.getSelectedIndex();
/*     */       
/*  24 */       if (index == -1)
/*     */         return; 
/*  26 */       if (EnabledComboBox.this.isEnabled(index)) {
/*  27 */         EnabledComboBox.this.setSelectedIndex(index);
/*     */         
/*  29 */         for (ActionListener l : EnabledComboBox.this.actionListeners) {
/*  30 */           l.actionPerformed(e);
/*     */         }
/*     */       } 
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
/*     */   public EnabledComboBox() {
/*  45 */     super(new EnabledComboBoxModel<>());
/*     */     
/*  47 */     this.renderer = new EnabledComboBoxRenderer<>((EnabledComboBoxModel)getModel());
/*  48 */     setRenderer(this.renderer);
/*  49 */     super.addActionListener(new ComboListener());
/*     */     
/*  51 */     this.actionListeners = new LinkedList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnabledComboBox(EnabledComboBoxModel<E> model) {
/*  60 */     super(model);
/*     */     
/*  62 */     this.renderer = new EnabledComboBoxRenderer<>(model);
/*  63 */     setRenderer(this.renderer);
/*  64 */     super.addActionListener(new ComboListener());
/*     */     
/*  66 */     this.actionListeners = new LinkedList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addActionListener(ActionListener l) {
/*  71 */     this.actionListeners.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeActionListener(ActionListener l) {
/*  76 */     this.actionListeners.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionListener[] getActionListeners() {
/*  81 */     return (ActionListener[])this.actionListeners.toArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModel(ComboBoxModel<E> model) {
/*  91 */     if (!(model instanceof EnabledComboBoxModel)) {
/*  92 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/*  95 */     super.setModel(model);
/*     */     
/*  97 */     this.renderer = new EnabledComboBoxRenderer<>((EnabledComboBoxModel)model);
/*  98 */     setRenderer(this.renderer);
/*     */   }
/*     */   
/*     */   public boolean isEnabled(int index) {
/* 102 */     EnabledComboBoxModel model = (EnabledComboBoxModel)getModel();
/*     */     
/* 104 */     return model.isEnabled(index);
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean enabled, int index) {
/* 108 */     EnabledComboBoxModel model = (EnabledComboBoxModel)getModel();
/*     */     
/* 110 */     model.setEnabled(enabled, index);
/*     */   }
/*     */   
/*     */   public void setFirstEnabledIndex() {
/* 114 */     EnabledComboBoxModel model = (EnabledComboBoxModel)getModel();
/*     */     
/* 116 */     if (model == null)
/*     */       return; 
/* 118 */     model.setFirstEnabledIndex();
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\u\\util\EnabledComboBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */