/*     */ package org.gdstash.ui.util;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnabledComboBoxModel<E>
/*     */   extends DefaultComboBoxModel<E>
/*     */ {
/*     */   private Vector<Boolean> enabled;
/*     */   
/*     */   public EnabledComboBoxModel() {
/*  20 */     this.enabled = new Vector<>();
/*     */   }
/*     */   
/*     */   public EnabledComboBoxModel(E[] items) {
/*  24 */     super(items);
/*     */     
/*  26 */     this.enabled = new Vector<>();
/*     */   }
/*     */   
/*     */   public EnabledComboBoxModel(Vector<E> v) {
/*  30 */     super(v);
/*     */     
/*  32 */     this.enabled = new Vector<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addElement(E anObject) {
/*  37 */     addElement(anObject, true);
/*     */   }
/*     */   
/*     */   public void addElement(E anObject, boolean enabled) {
/*  41 */     Boolean b = null;
/*     */     
/*  43 */     if (enabled) {
/*  44 */       b = Boolean.TRUE;
/*     */     } else {
/*  46 */       b = Boolean.FALSE;
/*     */     } 
/*     */     
/*  49 */     this.enabled.add(b);
/*     */     
/*  51 */     super.addElement(anObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public void insertElementAt(E anObject, int index) {
/*  56 */     insertElementAt(anObject, index, true);
/*     */   }
/*     */   
/*     */   public void insertElementAt(E anObject, int index, boolean enabled) {
/*  60 */     super.insertElementAt(anObject, index);
/*     */     
/*  62 */     Boolean b = null;
/*     */     
/*  64 */     if (enabled) {
/*  65 */       b = Boolean.TRUE;
/*     */     } else {
/*  67 */       b = Boolean.FALSE;
/*     */     } 
/*     */     
/*  70 */     this.enabled.add(index, b);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAllElements() {
/*  75 */     super.removeAllElements();
/*     */     
/*  77 */     this.enabled.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeElement(Object anObject) {
/*  82 */     int index = getIndexOf(anObject);
/*     */     
/*  84 */     if (index == -1)
/*     */       return; 
/*  86 */     super.removeElement(anObject);
/*     */     
/*  88 */     this.enabled.remove(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeElementAt(int index) {
/*  93 */     super.removeElementAt(index);
/*     */     
/*  95 */     this.enabled.remove(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelectedItem(Object anObject) {
/* 100 */     int index = getIndexOf(anObject);
/*     */     
/* 102 */     if (index == -1)
/* 103 */       return;  if (!isEnabled(index))
/*     */       return; 
/* 105 */     super.setSelectedItem(anObject);
/*     */   }
/*     */   
/*     */   public boolean isEnabled(int index) {
/* 109 */     Boolean b = null;
/*     */     
/*     */     try {
/* 112 */       b = this.enabled.get(index);
/*     */     }
/* 114 */     catch (ArrayIndexOutOfBoundsException ex) {
/* 115 */       return false;
/*     */     } 
/*     */     
/* 118 */     if (b == null) return false;
/*     */     
/* 120 */     return b.equals(Boolean.TRUE);
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean enabled, int index) {
/* 124 */     Boolean b = null;
/*     */     
/* 126 */     if (enabled) {
/* 127 */       b = Boolean.TRUE;
/*     */     } else {
/* 129 */       b = Boolean.FALSE;
/*     */     } 
/*     */     
/* 132 */     this.enabled.set(index, b);
/*     */   }
/*     */   
/*     */   public void setFirstEnabledIndex() {
/* 136 */     int size = getSize();
/*     */     
/* 138 */     int index = -1;
/* 139 */     for (int i = 0; i < size; i++) {
/* 140 */       if (isEnabled(i)) {
/* 141 */         index = i;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     setSelectedItem(getElementAt(index));
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\u\\util\EnabledComboBoxModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */