/*     */ package org.gdstash.ui;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseMotionAdapter;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.RowSorter;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import javax.swing.table.DefaultTableCellRenderer;
/*     */ import javax.swing.table.TableModel;
/*     */ import org.gdstash.item.GDItem;
/*     */ import org.gdstash.ui.table.GDItemNameSorter;
/*     */ import org.gdstash.ui.table.GDItemNameTableModel;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ 
/*     */ public class GDItemNameTablePane extends AdjustablePanel {
/*     */   private List<GDItem> data;
/*     */   private GDItemNameTableModel model;
/*     */   private JTable table;
/*     */   private GDItem selItem;
/*     */   private List<ActionListener> listeners;
/*     */   private boolean inclFaction;
/*     */   
/*     */   private class GDItemTableCellRenderer extends DefaultTableCellRenderer { public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
/*  42 */       if (col < 1) return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
/*     */ 
/*     */       
/*  45 */       JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
/*     */ 
/*     */       
/*  48 */       GDItemNameTableModel model = (GDItemNameTableModel)table.getModel();
/*     */       
/*  50 */       label.setForeground(model.getCellTextColor(row, col));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  56 */       return label;
/*     */     }
/*     */     private GDItemTableCellRenderer() {} }
/*     */   
/*     */   private class GDListListener implements ListSelectionListener { private GDListListener() {}
/*     */     
/*     */     public void valueChanged(ListSelectionEvent e) {
/*  63 */       ListSelectionModel model = (ListSelectionModel)e.getSource();
/*     */       
/*  65 */       if (model.getValueIsAdjusting())
/*  66 */         return;  if (model.isSelectionEmpty()) {
/*  67 */         GDItemNameTablePane.this.setSelectedItem(null);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  72 */       int min = e.getFirstIndex();
/*  73 */       int max = e.getLastIndex();
/*     */       
/*  75 */       int sel = -1; int i;
/*  76 */       for (i = min; i <= max; i++) {
/*  77 */         if (model.isSelectedIndex(i)) {
/*  78 */           sel = i;
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/*  85 */       GDItemNameTablePane.this.setSelectedItem(GDItemNameTablePane.this.model.getItem(sel));
/*     */     } }
/*     */   
/*     */   private class SortListener extends MouseAdapter {
/*     */     private SortListener() {}
/*     */     
/*     */     public void mouseClicked(MouseEvent ev) {
/*  92 */       int idx = GDItemNameTablePane.this.table.getColumnModel().getColumnIndexAtX(ev.getX());
/*     */       
/*  94 */       GDItemNameSorter sorter = (GDItemNameSorter)GDItemNameTablePane.this.table.getRowSorter();
/*     */       
/*  96 */       sorter.sort();
/*     */     }
/*     */   }
/*     */   
/*     */   private class HoverListener extends MouseMotionAdapter {
/* 101 */     private int currRow = -1;
/*     */ 
/*     */     
/*     */     public void mouseMoved(MouseEvent e) {
/* 105 */       JTable table = (JTable)e.getSource();
/*     */       
/* 107 */       int col = table.columnAtPoint(e.getPoint());
/*     */       
/* 109 */       if (col != 0)
/*     */         return; 
/* 111 */       int row = table.rowAtPoint(e.getPoint());
/*     */       
/* 113 */       if (row > -1 && row < table.getRowCount() && 
/* 114 */         this.currRow != row) {
/* 115 */         this.currRow = row;
/*     */         
/* 117 */         table.getSelectionModel().setSelectionInterval(row, row);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private HoverListener() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GDItemNameTablePane(List<GDItem> list, boolean inclFaction) {
/* 135 */     this.inclFaction = inclFaction;
/*     */     
/* 137 */     this.listeners = new LinkedList<>();
/* 138 */     this.selItem = null;
/* 139 */     this.data = list;
/*     */     
/* 141 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 146 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 147 */     Font fntTable = UIManager.getDefaults().getFont("TableHeader.font");
/* 148 */     if (fntTable == null) fntTable = fntLabel;
/*     */     
/* 150 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 151 */     fntTable = fntTable.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 153 */     if (this.table == null) {
/* 154 */       this.table = new JTable();
/*     */       
/* 156 */       this.table.getSelectionModel().addListSelectionListener(new GDListListener());
/* 157 */       this.table.setSelectionMode(0);
/* 158 */       this.table.addMouseMotionListener(new HoverListener());
/*     */       
/* 160 */       GDItemNameSorter sorter = new GDItemNameSorter();
/* 161 */       this.table.setRowSorter((RowSorter<? extends TableModel>)sorter);
/*     */       
/* 163 */       this.table.getTableHeader().addMouseListener(new SortListener());
/*     */       
/* 165 */       setItems((List<GDItem>)null);
/*     */     } 
/*     */     
/* 168 */     this.table.getTableHeader().setFont(fntTable);
/* 169 */     this.table.setFont(fntLabel);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     int height = -1;
/*     */     
/* 177 */     if (this.data != null) {
/* 178 */       for (GDItem item : this.data) {
/* 179 */         BufferedImage img = item.getImage();
/*     */         
/* 181 */         if (img != null && 
/* 182 */           img.getHeight() > height) height = img.getHeight();
/*     */       
/*     */       } 
/*     */     }
/*     */     
/* 187 */     if (height == -1) height = 12;
/*     */     
/* 189 */     if (height != -1) {
/* 190 */       height = height * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 191 */       this.table.setRowHeight(height);
/*     */     } 
/*     */     
/* 194 */     GDItemNameTableModel model = (GDItemNameTableModel)this.table.getModel();
/* 195 */     model.adjustUI();
/*     */ 
/*     */     
/* 198 */     updateTableColumns();
/*     */   }
/*     */   
/*     */   public void refresh() {
/* 202 */     if (this.table != null) setItems(null); 
/*     */   }
/*     */   
/*     */   private void init() {
/* 206 */     adjustUI();
/*     */     
/* 208 */     setItems(this.data);
/*     */     
/* 210 */     JScrollPane scroll = new JScrollPane(this.table);
/* 211 */     scroll.getVerticalScrollBar().setUnitIncrement(2 * GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 213 */     GroupLayout layout = null;
/* 214 */     GroupLayout.SequentialGroup hGroup = null;
/* 215 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 217 */     layout = new GroupLayout((Container)this);
/* 218 */     setLayout(layout);
/*     */     
/* 220 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 223 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 226 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 229 */     hGroup
/* 230 */       .addGroup(layout.createParallelGroup()
/* 231 */         .addComponent(scroll));
/* 232 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 235 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 238 */     vGroup
/* 239 */       .addGroup(layout.createParallelGroup()
/* 240 */         .addComponent(scroll));
/* 241 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateTableColumns() {
/* 247 */     this.table.getColumnModel().getColumn(1).setCellRenderer(new GDItemTableCellRenderer());
/* 248 */     this.table.getColumnModel().getColumn(2).setCellRenderer(new GDItemTableCellRenderer());
/*     */     
/* 250 */     int scale = 100;
/* 251 */     int size = 12;
/* 252 */     if (GDStashFrame.iniConfig != null) {
/* 253 */       scale = GDStashFrame.iniConfig.sectUI.graphicScale;
/* 254 */       size = GDStashFrame.iniConfig.sectUI.fontSize;
/*     */     } 
/* 256 */     this.table.getColumnModel().getColumn(0).setPreferredWidth(120 * scale / 100);
/* 257 */     this.table.getColumnModel().getColumn(1).setPreferredWidth(42 * size);
/* 258 */     this.table.getColumnModel().getColumn(2).setPreferredWidth(6 * size);
/*     */   }
/*     */   
/*     */   public List<GDItem> getItemList() {
/* 262 */     return this.data;
/*     */   }
/*     */   
/*     */   public void setItems(List<GDItem> list) {
/* 266 */     this.data = list;
/*     */     
/* 268 */     if (this.data != null) Collections.sort(this.data);
/*     */     
/* 270 */     int height = -1;
/*     */     
/* 272 */     if (this.data != null) {
/* 273 */       Iterator<GDItem> iter = this.data.iterator();
/* 274 */       while (iter.hasNext()) {
/* 275 */         GDItem item = iter.next();
/*     */         
/* 277 */         BufferedImage img = item.getImage();
/*     */         
/* 279 */         if (img == null) {
/* 280 */           iter.remove(); continue;
/*     */         } 
/* 282 */         if (img.getHeight() > height) height = img.getHeight();
/*     */       
/*     */       } 
/*     */     } 
/*     */     
/* 287 */     if (height == -1) height = 12;
/*     */     
/* 289 */     this.model = new GDItemNameTableModel(this.data, this.inclFaction);
/* 290 */     this.table.setModel((TableModel)this.model);
/* 291 */     this.table.setSelectionMode(0);
/* 292 */     this.table.getSelectionModel().addListSelectionListener(this.table);
/* 293 */     GDItemNameSorter sorter = new GDItemNameSorter(this.model);
/* 294 */     this.table.setRowSorter((RowSorter<? extends TableModel>)sorter);
/*     */ 
/*     */     
/* 297 */     Rectangle rect = this.table.getVisibleRect();
/* 298 */     rect.setLocation(0, 0);
/* 299 */     this.table.scrollRectToVisible(rect);
/*     */     
/* 301 */     if (height != -1) {
/* 302 */       height = height * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 303 */       this.table.setRowHeight(height);
/*     */     } 
/*     */     
/* 306 */     updateTableColumns();
/*     */   }
/*     */   
/*     */   public void updateItem(GDItem item) {
/* 310 */     if (item == null) {
/* 311 */       deleteItem();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 316 */     int pos = this.table.getSelectedRow();
/* 317 */     if (pos == -1)
/*     */       return; 
/* 319 */     GDItemNameTableModel model = (GDItemNameTableModel)this.table.getModel();
/*     */     
/* 321 */     model.updateRow(pos, item);
/*     */     
/* 323 */     ListSelectionModel lsm = this.table.getSelectionModel();
/*     */     
/* 325 */     int rows = model.getRowCount();
/* 326 */     if (rows > 0) {
/* 327 */       if (rows <= pos) pos = rows - 1;
/*     */       
/* 329 */       lsm.setSelectionInterval(pos, pos);
/*     */       
/* 331 */       GDItem gdi = model.getItem(pos);
/*     */       
/* 333 */       setSelectedItem(gdi);
/*     */     } else {
/* 335 */       this.table.clearSelection();
/* 336 */       this.selItem = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteItem() {
/* 342 */     int pos = this.table.getSelectedRow();
/* 343 */     if (pos == -1)
/*     */       return; 
/* 345 */     GDItemNameTableModel model = (GDItemNameTableModel)this.table.getModel();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 361 */     model.removeRow(pos);
/*     */     
/* 363 */     ListSelectionModel lsm = this.table.getSelectionModel();
/*     */     
/* 365 */     int rows = model.getRowCount();
/* 366 */     if (rows > 0) {
/* 367 */       if (rows <= pos) pos = rows - 1;
/*     */       
/* 369 */       lsm.setSelectionInterval(pos, pos);
/*     */       
/* 371 */       GDItem item = model.getItem(pos);
/*     */       
/* 373 */       setSelectedItem(item);
/*     */     } else {
/* 375 */       this.table.clearSelection();
/* 376 */       this.selItem = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clearSelection() {
/* 381 */     this.table.clearSelection();
/*     */   }
/*     */   
/*     */   public GDItem getSelectedItem() {
/* 385 */     return this.selItem;
/*     */   }
/*     */   
/*     */   private void setSelectedItem(GDItem item) {
/* 389 */     this.selItem = item;
/*     */     
/* 391 */     if (this.selItem != null && 
/* 392 */       item.isStackable()) {
/* 393 */       this.selItem = this.selItem.clone();
/*     */       
/* 395 */       this.selItem.setStackCount(this.selItem.getDefaultStackSize());
/*     */     } 
/*     */ 
/*     */     
/* 399 */     fireActionEvent();
/*     */   }
/*     */   
/*     */   public void addActionListener(ActionListener listener) {
/* 403 */     this.listeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeActionListener(ActionListener listener) {
/* 407 */     Iterator<ActionListener> iter = this.listeners.iterator();
/* 408 */     while (iter.hasNext()) {
/* 409 */       ActionListener l = iter.next();
/*     */       
/* 411 */       if (listener == l) iter.remove(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fireActionEvent() {
/* 416 */     ActionEvent ev = new ActionEvent(this, 1001, null);
/*     */     
/* 418 */     for (ActionListener l : this.listeners)
/* 419 */       l.actionPerformed(ev); 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\GDItemNameTablePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */