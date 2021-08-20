/*     */ package org.gdstash.character;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.gdstash.file.GDReader;
/*     */ import org.gdstash.file.GDWriter;
/*     */ import org.gdstash.item.GDAbstractContainer;
/*     */ import org.gdstash.item.GDItem;
/*     */ import org.gdstash.util.FileVersionException;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GDCharStash
/*     */   extends GDAbstractContainer
/*     */ {
/*     */   public static final int VERSION_5 = 5;
/*     */   public static final int VERSION_6 = 6;
/*     */   private static final int BLOCK = 4;
/*     */   private int version;
/*     */   private GDChar gdc;
/*     */   private int numPages;
/*     */   private List<GDAbstractContainer> pages;
/*     */   
/*     */   public GDCharStash(GDChar gdc) {
/*  34 */     super(3);
/*     */     
/*  36 */     this.gdc = gdc;
/*     */     
/*  38 */     this.pages = new LinkedList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  46 */     if (this.pages == null) return 0;
/*     */     
/*  48 */     GDAbstractContainer page = this.pages.get(0);
/*     */     
/*  50 */     if (page == null) return 0;
/*     */     
/*  52 */     return page.getContainerHeight();
/*     */   }
/*     */   
/*     */   public int getWidth() {
/*  56 */     if (this.pages == null) return 0;
/*     */     
/*  58 */     GDAbstractContainer page = this.pages.get(0);
/*     */     
/*  60 */     if (page == null) return 0;
/*     */     
/*  62 */     return page.getContainerWidth();
/*     */   }
/*     */   
/*     */   public List<GDAbstractContainer> getStashPages() {
/*  66 */     return this.pages;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDItem> getItemList() {
/*  71 */     List<GDItem> list = new LinkedList<>();
/*     */     
/*  73 */     if (this.pages == null) return list;
/*     */     
/*  75 */     for (GDAbstractContainer page : this.pages) {
/*  76 */       if (page != null) {
/*  77 */         list.addAll(page.getItemList());
/*     */       }
/*     */     } 
/*     */     
/*  81 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<GDItem> getRemovedItemList() {
/*  86 */     List<GDItem> list = new LinkedList<>();
/*     */     
/*  88 */     if (this.pages == null) return list;
/*     */     
/*  90 */     for (GDAbstractContainer page : this.pages) {
/*  91 */       if (page != null) {
/*  92 */         list.addAll(page.getRemovedItemList());
/*     */       }
/*     */     } 
/*     */     
/*  96 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addItem(GDItem item) {
/* 102 */     if (this.pages == null) return false;
/*     */     
/* 104 */     GDAbstractContainer page = this.pages.get(0);
/*     */     
/* 106 */     if (page == null) return false;
/*     */     
/* 108 */     return page.addItem(item);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeItem(GDItem item) {
/* 114 */     if (this.pages == null) return false;
/*     */     
/* 116 */     GDAbstractContainer page = this.pages.get(0);
/*     */     
/* 118 */     if (page == null) return false;
/*     */     
/* 120 */     return page.removeItem(item);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 125 */     if (this.pages == null) return false;
/*     */     
/* 127 */     boolean changed = false;
/*     */     
/* 129 */     Iterator<GDAbstractContainer> iter = this.pages.iterator();
/* 130 */     while (iter.hasNext()) {
/* 131 */       GDAbstractContainer page = iter.next();
/*     */       
/* 133 */       if (page.hasChanged()) {
/* 134 */         changed = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 140 */     return changed;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerHeight() {
/* 145 */     if (this.pages == null) return 0;
/*     */     
/* 147 */     GDAbstractContainer page = this.pages.get(0);
/*     */     
/* 149 */     if (page == null) return 0;
/*     */     
/* 151 */     return page.getContainerHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerWidth() {
/* 156 */     if (this.pages == null) return 0;
/*     */     
/* 158 */     GDAbstractContainer page = this.pages.get(0);
/*     */     
/* 160 */     if (page == null) return 0;
/*     */     
/* 162 */     return page.getContainerWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerType() {
/* 167 */     if (this.pages == null) return 3;
/*     */     
/* 169 */     GDAbstractContainer page = this.pages.get(0);
/*     */     
/* 171 */     if (page == null) return 3;
/*     */     
/* 173 */     return page.getContainerType();
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh() {
/* 178 */     if (this.pages == null)
/*     */       return; 
/* 180 */     Iterator<GDAbstractContainer> iter = this.pages.iterator();
/* 181 */     while (iter.hasNext()) {
/* 182 */       GDAbstractContainer page = iter.next();
/*     */       
/* 184 */       if (page != null) {
/* 185 */         page.refresh();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read() throws IOException {
/* 195 */     int val = 0;
/*     */     
/* 197 */     GDReader.Block block = new GDReader.Block();
/*     */     
/* 199 */     val = GDReader.readBlockStart(block);
/* 200 */     if (val != 4) throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     
/* 202 */     this.version = GDReader.readEncInt(true);
/* 203 */     if (this.version != 5 && this.version != 6)
/*     */     {
/* 205 */       throw new FileVersionException(GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_UNSUPPORTED_VERSION"));
/*     */     }
/*     */     
/* 208 */     if (this.version >= 6) {
/* 209 */       this.numPages = GDReader.readEncInt(true);
/*     */     } else {
/* 211 */       this.numPages = 1;
/*     */     } 
/*     */     
/* 214 */     this.pages.clear();
/* 215 */     for (int i = 0; i < this.numPages; i++) {
/* 216 */       GDCharStashPage page = new GDCharStashPage(this.gdc, this.version);
/*     */       
/* 218 */       page.read();
/*     */       
/* 220 */       this.pages.add(page);
/*     */     } 
/*     */ 
/*     */     
/* 224 */     GDReader.readBlockEnd(block);
/*     */     
/* 226 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public void write() throws IOException {
/* 230 */     GDReader.Block block = new GDReader.Block();
/* 231 */     GDWriter.writeBlockStart(block, 4);
/*     */     
/* 233 */     GDWriter.writeInt(this.version);
/*     */     
/* 235 */     if (this.version >= 6) {
/* 236 */       GDWriter.writeInt(this.numPages);
/*     */     }
/*     */     
/* 239 */     Iterator<GDAbstractContainer> iter = this.pages.iterator();
/* 240 */     while (iter.hasNext()) {
/* 241 */       GDCharStashPage page = (GDCharStashPage)iter.next();
/*     */       
/* 243 */       if (page != null) page.write();
/*     */     
/*     */     } 
/*     */     
/* 247 */     GDWriter.writeBlockEnd(block);
/*     */     
/* 249 */     this.changed = false;
/*     */   }
/*     */   
/*     */   public int getByteSize() {
/* 253 */     int size = 0;
/*     */     
/* 255 */     size += 4;
/* 256 */     size += 4;
/* 257 */     size += 4;
/*     */     
/* 259 */     if (this.version >= 6) {
/* 260 */       size += 4;
/*     */     }
/*     */     
/* 263 */     Iterator<GDAbstractContainer> iter = this.pages.iterator();
/* 264 */     while (iter.hasNext()) {
/* 265 */       GDCharStashPage page = (GDCharStashPage)iter.next();
/*     */       
/* 267 */       size += page.getByteSize();
/*     */     } 
/*     */     
/* 270 */     size += 4;
/*     */     
/* 272 */     return size;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstash\character\GDCharStash.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */