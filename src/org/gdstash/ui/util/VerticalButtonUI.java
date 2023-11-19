/*     */ package org.gdstash.ui.util;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.basic.BasicButtonUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VerticalButtonUI
/*     */   extends BasicButtonUI
/*     */ {
/*     */   protected int angle;
/*     */   
/*     */   public VerticalButtonUI(int angle) {
/*  28 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize(JComponent c) {
/*  33 */     Dimension dim = super.getPreferredSize(c);
/*  34 */     return new Dimension(dim.height, dim.width);
/*     */   }
/*     */   
/*  37 */   private static Rectangle paintIconR = new Rectangle();
/*  38 */   private static Rectangle paintTextR = new Rectangle();
/*  39 */   private static Rectangle paintViewR = new Rectangle();
/*  40 */   private static Insets paintViewInsets = new Insets(0, 0, 0, 0);
/*     */ 
/*     */   
/*     */   public void paint(Graphics g, JComponent c) {
/*  44 */     JButton button = (JButton)c;
/*  45 */     String text = button.getText();
/*  46 */     Icon icon = button.isEnabled() ? button.getIcon() : button.getDisabledIcon();
/*     */     
/*  48 */     if (icon == null && text == null)
/*     */       return; 
/*  50 */     FontMetrics fm = g.getFontMetrics();
/*  51 */     paintViewInsets = c.getInsets(paintViewInsets);
/*     */     
/*  53 */     paintViewR.x = paintViewInsets.left;
/*  54 */     paintViewR.y = paintViewInsets.top;
/*     */ 
/*     */     
/*  57 */     paintViewR.height = c.getWidth() - paintViewInsets.left + paintViewInsets.right;
/*  58 */     paintViewR.width = c.getHeight() - paintViewInsets.top + paintViewInsets.bottom;
/*     */     
/*  60 */     paintIconR.x = paintIconR.y = paintIconR.width = paintIconR.height = 0;
/*  61 */     paintTextR.x = paintTextR.y = paintTextR.width = paintTextR.height = 0;
/*     */     
/*  63 */     Graphics2D g2 = (Graphics2D)g;
/*  64 */     AffineTransform tr = g2.getTransform();
/*     */     
/*  66 */     if (this.angle == 90) {
/*  67 */       g2.rotate(1.5707963267948966D);
/*  68 */       g2.translate(0, -c.getWidth());
/*  69 */       paintViewR.x = c.getHeight() / 2 - (int)fm.getStringBounds(text, g).getWidth() / 2;
/*  70 */       paintViewR.y = c.getWidth() / 2 - (int)fm.getStringBounds(text, g).getHeight() / 2;
/*     */       
/*  72 */       if (icon != null) {
/*  73 */         paintIconR.y = (c.getWidth() - icon.getIconWidth()) / 2;
/*  74 */         paintIconR.x = paintIconR.y;
/*     */       }
/*     */     
/*  77 */     } else if (this.angle == 270) {
/*  78 */       g2.rotate(-1.5707963267948966D);
/*  79 */       g2.translate(-c.getHeight(), 0);
/*  80 */       paintViewR.x = c.getHeight() / 2 - (int)fm.getStringBounds(text, g).getWidth() / 2;
/*  81 */       paintViewR.y = c.getWidth() / 2 - (int)fm.getStringBounds(text, g).getHeight() / 2;
/*     */       
/*  83 */       if (icon != null) {
/*  84 */         paintIconR.y = (c.getWidth() - icon.getIconWidth()) / 2;
/*  85 */         paintIconR.x = c.getHeight() - icon.getIconHeight() - paintIconR.y;
/*     */       } 
/*     */     } 
/*     */     
/*  89 */     if (icon != null) {
/*  90 */       icon.paintIcon(c, g, paintIconR.x, paintIconR.y);
/*     */     }
/*     */     
/*  93 */     if (text != null) {
/*  94 */       int textX = paintTextR.x;
/*  95 */       int textY = paintTextR.y + fm.getAscent();
/*     */       
/*  97 */       if (button.isEnabled()) {
/*  98 */         paintText(g, c, new Rectangle(paintViewR.x, paintViewR.y, textX, textY), text);
/*     */       } else {
/* 100 */         paintText(g, c, new Rectangle(paintViewR.x, paintViewR.y, textX, textY), text);
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     g2.setTransform(tr);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\u\\util\VerticalButtonUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */