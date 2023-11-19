/*    */ package org.gdstash.ui.font;
/*    */ 
/*    */ import java.awt.Font;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.font.FontRenderContext;
/*    */ import java.awt.font.TextLayout;
/*    */ import java.awt.geom.Rectangle2D;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FontSamplePanel
/*    */   extends JPanel
/*    */ {
/*    */   private Font font;
/*    */   private String sampleString;
/*    */   
/*    */   public FontSamplePanel(String sampleString) {
/* 26 */     this.sampleString = sampleString;
/*    */   }
/*    */   
/*    */   public void setSampleFont(Font font) {
/* 30 */     this.font = font;
/*    */     
/* 32 */     repaint();
/*    */   }
/*    */ 
/*    */   
/*    */   public void paintComponent(Graphics g) {
/* 37 */     super.paintComponent(g);
/*    */     
/* 39 */     if (this.font == null)
/*    */       return; 
/* 41 */     Graphics2D g2d = (Graphics2D)g;
/* 42 */     FontRenderContext frc = g2d.getFontRenderContext();
/* 43 */     TextLayout layout = new TextLayout(this.sampleString, this.font, frc);
/* 44 */     Rectangle2D bounds = layout.getBounds();
/*    */     
/* 46 */     int width = (int)Math.round(bounds.getWidth());
/* 47 */     int height = (int)Math.round(bounds.getHeight());
/* 48 */     int x = (getWidth() - width) / 2;
/* 49 */     int y = height + (getHeight() - height) / 2;
/*    */     
/* 51 */     layout.draw(g2d, x, y);
/*    */   }
/*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\font\FontSamplePanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */