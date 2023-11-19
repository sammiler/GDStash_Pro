/*    */ package org.gdstash.ui;
/*    */ 
/*    */ import java.awt.Desktop;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.net.URI;
/*    */ import org.gdstash.util.GDConstants;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GDHelpActionListener
/*    */   implements ActionListener
/*    */ {
/*    */   private URI uri;
/*    */   
/*    */   public GDHelpActionListener(String filename) {
/* 25 */     String s = GDConstants.USER_DIR + GDConstants.FILE_SEPARATOR + "doc" + GDConstants.FILE_SEPARATOR + filename;
/*    */     
/* 27 */     File file = new File(s);
/*    */     
/* 29 */     if (file.exists()) {
/* 30 */       this.uri = file.toURI();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void actionPerformed(ActionEvent ev) {
/* 36 */     if (this.uri != null)
/*    */       try {
/* 38 */         Desktop.getDesktop().browse(this.uri);
/*    */       }
/* 40 */       catch (IOException iOException) {} 
/*    */   }
/*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\GDHelpActionListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */