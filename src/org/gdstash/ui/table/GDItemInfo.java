/*     */ package org.gdstash.ui.table;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.UIManager;
/*     */ import org.gdstash.db.DBItem;
/*     */ import org.gdstash.item.GDItem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDItemInfo
/*     */   implements Comparable<GDItemInfo>
/*     */ {
/*  22 */   public static final Color COLOR_GREEN = new Color(0, 72, 0);
/*  23 */   public static final Color COLOR_YELLOW = new Color(128, 48, 0);
/*  24 */   public static final Color COLOR_RED = new Color(72, 0, 0);
/*     */   
/*     */   public GDItem gdItem;
/*     */   public int scCount;
/*     */   public int hcCount;
/*     */   public GDItem scBlueprint;
/*     */   public GDItem hcBlueprint;
/*     */   
/*     */   private GDItemInfo(GDItem item) {
/*  33 */     this.gdItem = item;
/*  34 */     this.scCount = 0;
/*  35 */     this.hcCount = 0;
/*  36 */     this.scBlueprint = null;
/*  37 */     this.hcBlueprint = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(GDItemInfo info) {
/*  45 */     int i = this.gdItem.compareTo(info.gdItem);
/*     */     
/*  47 */     if (i != 0) return i;
/*     */     
/*  49 */     if (this.scCount != info.scCount) return this.scCount - info.scCount;
/*     */     
/*  51 */     return this.hcCount - info.hcCount;
/*     */   }
/*     */   
/*     */   public static List<GDItemInfo> buildItemInfo(List<GDItem> all, List<GDItem> found) {
/*  55 */     if (all == null) return null; 
/*  56 */     if (all.isEmpty()) return null;
/*     */     
/*  58 */     List<GDItemInfo> infos = new LinkedList<>();
/*     */     
/*  60 */     for (GDItem item : all) {
/*  61 */       GDItemInfo info = new GDItemInfo(item);
/*     */       
/*  63 */       infos.add(info);
/*     */       
/*  65 */       for (GDItem item2 : found) {
/*  66 */         if (item.getItemID().equals(item2.getItemID())) {
/*  67 */           if (item2.isHardcore()) {
/*  68 */             info.hcCount++; continue;
/*     */           } 
/*  70 */           info.scCount++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  76 */     return infos;
/*     */   }
/*     */   
/*     */   public BufferedImage getFullImage() {
/*  80 */     if (this.gdItem == null) return null;
/*     */     
/*  82 */     DBItem dbItem = this.gdItem.getDBItem();
/*     */     
/*  84 */     if (this.gdItem.getDBItem() == null) return null;
/*     */     
/*  86 */     BufferedImage img = dbItem.getImage();
/*     */     
/*  88 */     if (img == null) return null;
/*     */ 
/*     */     
/*  91 */     int MARGIN_HEIGHT = 6;
/*  92 */     int BLUEPRINT_HEIGHT = 32;
/*  93 */     int INFO_HEIGHT = 82;
/*  94 */     int IMAGE_HEIGHT = 210;
/*  95 */     int IMAGE_WIDTH = 100;
/*     */     
/*  97 */     BufferedImage clone = new BufferedImage(100, 210, img.getType());
/*  98 */     Graphics2D g = clone.createGraphics();
/*     */     
/* 100 */     int xPos = (100 - img.getWidth()) / 2;
/* 101 */     int yPos = (210 - img.getHeight() - 82) / 2;
/* 102 */     g.drawImage(img, xPos, yPos, (ImageObserver)null);
/* 103 */     img = clone;
/*     */     
/* 105 */     if (this.scCount > 0) {
/* 106 */       String s = Integer.toString(this.scCount);
/*     */       
/* 108 */       Font font = UIManager.getDefaults().getFont("Label.font");
/* 109 */       font = font.deriveFont(1, 10.0F);
/*     */       
/* 111 */       g.setFont(font);
/*     */       
/* 113 */       int h = g.getFontMetrics().getAscent();
/*     */       
/* 115 */       xPos = 6;
/* 116 */       yPos = 210 - h - 6 - 32 - 6;
/*     */       
/* 118 */       g.setPaint(Color.BLACK);
/* 119 */       g.drawString(s, xPos - 1, yPos - 1);
/* 120 */       g.setPaint(Color.BLACK);
/* 121 */       g.drawString(s, xPos - 1, yPos + 1);
/* 122 */       g.setPaint(Color.BLACK);
/* 123 */       g.drawString(s, xPos + 1, yPos - 1);
/* 124 */       g.setPaint(Color.BLACK);
/* 125 */       g.drawString(s, xPos + 1, yPos + 1);
/*     */       
/* 127 */       g.setPaint(Color.WHITE);
/* 128 */       g.drawString(s, xPos, yPos);
/*     */     } 
/*     */     
/* 131 */     if (this.scBlueprint != null) {
/* 132 */       BufferedImage bpImg = this.scBlueprint.getImage();
/*     */       
/* 134 */       if (bpImg != null) {
/* 135 */         xPos = 100 - bpImg.getWidth() - 6;
/* 136 */         yPos = 210 - bpImg.getHeight() - 6 - 32 - 6;
/*     */         
/* 138 */         g.drawImage(bpImg, xPos, yPos, (ImageObserver)null);
/*     */       } 
/*     */     } 
/*     */     
/* 142 */     if (this.hcCount > 0) {
/* 143 */       String s = Integer.toString(this.hcCount);
/*     */       
/* 145 */       Font font = UIManager.getDefaults().getFont("Label.font");
/* 146 */       font = font.deriveFont(1, 10.0F);
/*     */       
/* 148 */       g.setFont(font);
/* 149 */       int w = g.getFontMetrics().stringWidth(s);
/* 150 */       int h = g.getFontMetrics().getDescent();
/*     */       
/* 152 */       xPos = 6;
/* 153 */       yPos = 210 - h - 6;
/*     */       
/* 155 */       g.setPaint(Color.BLACK);
/* 156 */       g.drawString(s, xPos - 1, yPos - 1);
/* 157 */       g.setPaint(Color.BLACK);
/* 158 */       g.drawString(s, xPos - 1, yPos + 1);
/* 159 */       g.setPaint(Color.BLACK);
/* 160 */       g.drawString(s, xPos + 1, yPos - 1);
/* 161 */       g.setPaint(Color.BLACK);
/* 162 */       g.drawString(s, xPos + 1, yPos + 1);
/*     */       
/* 164 */       g.setPaint(Color.WHITE);
/* 165 */       g.drawString(s, xPos, yPos);
/*     */     } 
/*     */     
/* 168 */     if (this.hcBlueprint != null) {
/* 169 */       BufferedImage bpImg = this.hcBlueprint.getImage();
/*     */       
/* 171 */       if (bpImg != null) {
/* 172 */         xPos = 100 - bpImg.getWidth() - 6;
/* 173 */         yPos = 210 - bpImg.getHeight() - 6;
/*     */         
/* 175 */         g.drawImage(bpImg, xPos, yPos, (ImageObserver)null);
/*     */       } 
/*     */     } 
/*     */     
/* 179 */     return img;
/*     */   }
/*     */   
/*     */   public BufferedImage getImage() {
/* 183 */     if (this.gdItem == null) return null;
/*     */     
/* 185 */     return this.gdItem.getImage();
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\table\GDItemInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */