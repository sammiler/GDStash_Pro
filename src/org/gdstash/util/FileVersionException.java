/*    */ package org.gdstash.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FileVersionException
/*    */   extends IOException
/*    */ {
/*    */   public FileVersionException() {}
/*    */   
/*    */   public FileVersionException(String message) {
/* 19 */     super(message);
/*    */   }
/*    */   
/*    */   public FileVersionException(String message, Throwable cause) {
/* 23 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public FileVersionException(Throwable cause) {
/* 27 */     super(cause);
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\util\FileVersionException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */