/*    */ package org.gdstash.db;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.DatabaseMetaData;
/*    */ import java.sql.DriverManager;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GDDBUtil
/*    */ {
/*    */   public static boolean doesExist(String url) {
/* 20 */     boolean exists = false;
/*    */     
/*    */     try {
/* 23 */       Connection conn = DriverManager.getConnection(url + "create=false");
/*    */       
/* 25 */       exists = true;
/*    */       
/* 27 */       conn.close();
/*    */     }
/* 29 */     catch (SQLException sQLException) {}
/*    */     
/* 31 */     return exists;
/*    */   }
/*    */   
/*    */   public static boolean tableExists(String tablename) throws SQLException {
/* 35 */     boolean found = false;
/*    */     
/* 37 */     try (Connection conn = GDDBData.getConnection()) {
/* 38 */       found = tableExists(conn, tablename);
/*    */       
/* 40 */       conn.commit();
/*    */     }
/* 42 */     catch (SQLException ex) {
/* 43 */       throw ex;
/*    */     } 
/*    */     
/* 46 */     return found;
/*    */   }
/*    */   
/*    */   public static boolean tableExists(Connection conn, String tablename) throws SQLException {
/* 50 */     boolean found = false;
/*    */     
/* 52 */     DatabaseMetaData meta = conn.getMetaData();
/*    */     
/* 54 */     try (ResultSet rs = meta.getTables(null, null, tablename, null)) {
/* 55 */       while (rs.next()) {
/* 56 */         String name = rs.getString(3);
/*    */         
/* 58 */         if (name.equals(tablename)) {
/* 59 */           found = true;
/*    */ 
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/* 65 */     } catch (SQLException ex) {
/* 66 */       found = false;
/*    */       
/* 68 */       throw ex;
/*    */     } 
/*    */     
/* 71 */     return found;
/*    */   }
/*    */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\db\GDDBUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */