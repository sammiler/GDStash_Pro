/*     */ package org.gdstash.ui;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.swing.DefaultListSelectionModel;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.RowSorter;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.table.DefaultTableCellRenderer;
/*     */ import javax.swing.table.TableModel;
/*     */ import org.gdstash.item.GDItem;
/*     */ import org.gdstash.item.GDStash;
/*     */ import org.gdstash.ui.table.GDMassImportSorter;
/*     */ import org.gdstash.ui.table.GDMassImportTableModel;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ import org.gdstash.util.GDImagePool;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDItemImportTablePane extends AdjustablePanel {
/*     */   private List<GDItem> data;
/*     */   private GDMassImportTableModel model;
/*     */   private JTable table;
/*     */   private JButton btnSelAll;
/*     */   private JButton btnSelValid;
/*     */   private JButton btnSelNone;
/*     */   private JButton btnClear;
/*     */   
/*     */   private static class GDItemTableCellRenderer extends DefaultTableCellRenderer {
/*     */     private GDItemTableCellRenderer() {}
/*     */     
/*     */     public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
/*  45 */       if (col < 2) return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
/*     */ 
/*     */       
/*  48 */       JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
/*     */ 
/*     */       
/*  51 */       GDMassImportTableModel model = (GDMassImportTableModel)table.getModel();
/*     */       
/*  53 */       label.setForeground(model.getCellTextColor(row, col));
/*     */ 
/*     */       
/*  56 */       return label;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class GDItemTableSelectionModel extends DefaultListSelectionModel {
/*     */     private GDMassImportTableModel model;
/*     */     
/*     */     public GDItemTableSelectionModel(GDMassImportTableModel model) {
/*  64 */       this.model = model;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSelectionInterval(int index0, int index1) {
/*  69 */       super.setSelectionInterval(index0, index1);
/*     */       
/*  71 */       for (int i = index0; i <= index1; i++) {
/*  72 */         (this.model.rows[i]).selected = !(this.model.rows[i]).selected;
/*     */         
/*  74 */         this.model.fireTableCellUpdated(i, 0);
/*     */       } 
/*     */     } }
/*     */   
/*     */   private class SortListener extends MouseAdapter {
/*     */     private SortListener() {}
/*     */     
/*     */     public void mouseClicked(MouseEvent ev) {
/*  82 */       int idx = GDItemImportTablePane.this.table.getColumnModel().getColumnIndexAtX(ev.getX());
/*     */       
/*  84 */       GDMassImportSorter sorter = (GDMassImportSorter)GDItemImportTablePane.this.table.getRowSorter();
/*     */       
/*  86 */       sorter.sort();
/*     */     }
/*     */   }
/*     */   
/*     */   private class SelectAllListener
/*     */     implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  93 */       GDItemImportTablePane.this.model.setSelectAll(true);
/*     */     }
/*     */     private SelectAllListener() {} }
/*     */   
/*     */   private class SelectValidListener implements ActionListener { private SelectValidListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/* 100 */       GDItemImportTablePane.this.model.selectValidOnly();
/*     */     } }
/*     */ 
/*     */   
/*     */   private class SelectNoneListener implements ActionListener { private SelectNoneListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/* 107 */       GDItemImportTablePane.this.model.setSelectAll(false);
/*     */     } }
/*     */   
/*     */   private class ClearListener implements ActionListener {
/*     */     private ClearListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/* 114 */       GDItemImportTablePane.this.model.setData(null);
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
/*     */   
/*     */   public GDItemImportTablePane(List<GDItem> list) {
/* 128 */     this.data = list;
/*     */     
/* 130 */     init();
/*     */   }
/*     */   
/*     */   public GDItemImportTablePane(GDStash stash) {
/* 134 */     this.data = stash.getItems();
/*     */     
/* 136 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 141 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 142 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/* 143 */     if (fntButton == null) fntButton = fntLabel; 
/* 144 */     Font fntTable = UIManager.getDefaults().getFont("TableHeader.font");
/* 145 */     if (fntTable == null) fntTable = fntLabel;
/*     */     
/* 147 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 148 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 149 */     fntTable = fntTable.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 151 */     if (this.btnSelAll == null) {
/* 152 */       this.btnSelAll = new JButton();
/*     */       
/* 154 */       this.btnSelAll.addActionListener(new SelectAllListener());
/*     */     } 
/* 156 */     this.btnSelAll.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_SEL_ALL"));
/* 157 */     this.btnSelAll.setIcon(GDImagePool.iconMassAll24);
/* 158 */     this.btnSelAll.setFont(fntButton);
/*     */     
/* 160 */     if (this.btnSelValid == null) {
/* 161 */       this.btnSelValid = new JButton();
/*     */       
/* 163 */       this.btnSelValid.addActionListener(new SelectValidListener());
/*     */     } 
/* 165 */     this.btnSelValid.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_SEL_VALID"));
/* 166 */     this.btnSelValid.setIcon(GDImagePool.iconMassValid24);
/* 167 */     this.btnSelValid.setFont(fntButton);
/*     */     
/* 169 */     if (this.btnSelNone == null) {
/* 170 */       this.btnSelNone = new JButton();
/*     */       
/* 172 */       this.btnSelNone.addActionListener(new SelectNoneListener());
/*     */     } 
/* 174 */     this.btnSelNone.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_UNSEL_ALL"));
/* 175 */     this.btnSelNone.setIcon(GDImagePool.iconMassNone24);
/* 176 */     this.btnSelNone.setFont(fntButton);
/*     */     
/* 178 */     if (this.btnClear == null) {
/* 179 */       this.btnClear = new JButton();
/*     */       
/* 181 */       this.btnClear.addActionListener(new ClearListener());
/*     */     } 
/* 183 */     this.btnClear.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_CLEAR"));
/* 184 */     this.btnClear.setIcon(GDImagePool.iconBtnTrash24);
/* 185 */     this.btnClear.setFont(fntButton);
/*     */     
/* 187 */     if (this.table == null) {
/* 188 */       this.table = new JTable();
/*     */       
/* 190 */       GDMassImportSorter sorter = new GDMassImportSorter();
/* 191 */       this.table.setRowSorter((RowSorter<? extends TableModel>)sorter);
/*     */       
/* 193 */       this.table.getTableHeader().addMouseListener(new SortListener());
/*     */       
/* 195 */       setItems((List<GDItem>)null);
/*     */     } 
/* 197 */     this.table.getTableHeader().setFont(fntTable);
/* 198 */     this.table.setFont(fntLabel);
/*     */ 
/*     */     
/* 201 */     int height = -1;
/*     */     
/* 203 */     if (this.data != null) {
/* 204 */       for (GDItem item : this.data) {
/* 205 */         BufferedImage img = item.getImage();
/*     */         
/* 207 */         if (img != null && 
/* 208 */           img.getHeight() > height) height = img.getHeight();
/*     */       
/*     */       } 
/*     */     }
/*     */     
/* 213 */     if (height == -1) height = 12;
/*     */     
/* 215 */     if (height != -1) {
/* 216 */       height = height * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 217 */       this.table.setRowHeight(height);
/*     */     } 
/*     */     
/* 220 */     GDMassImportTableModel model = (GDMassImportTableModel)this.table.getModel();
/* 221 */     model.adjustUI();
/*     */ 
/*     */     
/* 224 */     updateTableColumns();
/*     */   }
/*     */   
/*     */   private void init() {
/* 228 */     adjustUI();
/*     */     
/* 230 */     GroupLayout layout = null;
/* 231 */     GroupLayout.SequentialGroup hGroup = null;
/* 232 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 234 */     JPanel panel = buildButtonPanel();
/*     */     
/* 236 */     setItems(this.data);
/*     */     
/* 238 */     JScrollPane scroll = new JScrollPane(this.table);
/* 239 */     scroll.getVerticalScrollBar().setUnitIncrement(2 * GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 241 */     layout = new GroupLayout((Container)this);
/* 242 */     setLayout(layout);
/*     */     
/* 244 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 247 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 250 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 253 */     hGroup
/* 254 */       .addGroup(layout.createParallelGroup()
/* 255 */         .addComponent(scroll)
/* 256 */         .addComponent(panel));
/* 257 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 260 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 263 */     vGroup
/* 264 */       .addGroup(layout.createParallelGroup()
/* 265 */         .addComponent(scroll))
/* 266 */       .addGroup(layout.createParallelGroup()
/* 267 */         .addComponent(panel));
/* 268 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel buildButtonPanel() {
/* 274 */     GroupLayout layout = null;
/* 275 */     GroupLayout.SequentialGroup hGroup = null;
/* 276 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 278 */     JPanel panel = new JPanel();
/*     */     
/* 280 */     layout = new GroupLayout(panel);
/* 281 */     panel.setLayout(layout);
/*     */     
/* 283 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 286 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 289 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 292 */     hGroup
/* 293 */       .addGroup(layout.createParallelGroup()
/* 294 */         .addComponent(this.btnSelAll))
/* 295 */       .addGroup(layout.createParallelGroup()
/* 296 */         .addComponent(this.btnSelValid))
/* 297 */       .addGroup(layout.createParallelGroup()
/* 298 */         .addComponent(this.btnSelNone))
/* 299 */       .addGroup(layout.createParallelGroup()
/* 300 */         .addComponent(this.btnClear));
/* 301 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 304 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 307 */     vGroup
/* 308 */       .addGroup(layout.createParallelGroup()
/* 309 */         .addComponent(this.btnSelAll)
/* 310 */         .addComponent(this.btnSelValid)
/* 311 */         .addComponent(this.btnSelNone)
/* 312 */         .addComponent(this.btnClear));
/* 313 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 315 */     layout.linkSize(0, new Component[] { this.btnSelAll, this.btnSelValid });
/* 316 */     layout.linkSize(0, new Component[] { this.btnSelAll, this.btnSelNone });
/* 317 */     layout.linkSize(0, new Component[] { this.btnSelAll, this.btnClear });
/*     */     
/* 319 */     layout.linkSize(1, new Component[] { this.btnSelAll, this.btnSelValid });
/* 320 */     layout.linkSize(1, new Component[] { this.btnSelAll, this.btnSelNone });
/* 321 */     layout.linkSize(1, new Component[] { this.btnSelAll, this.btnClear });
/*     */     
/* 323 */     return panel;
/*     */   }
/*     */   
/*     */   private void updateTableColumns() {
/* 327 */     this.table.getColumnModel().getColumn(3).setCellRenderer(new GDItemTableCellRenderer());
/* 328 */     this.table.getColumnModel().getColumn(4).setCellRenderer(new GDItemTableCellRenderer());
/* 329 */     this.table.getColumnModel().getColumn(5).setCellRenderer(new GDItemTableCellRenderer());
/* 330 */     this.table.getColumnModel().getColumn(6).setCellRenderer(new GDItemTableCellRenderer());
/* 331 */     this.table.getColumnModel().getColumn(7).setCellRenderer(new GDItemTableCellRenderer());
/* 332 */     this.table.getColumnModel().getColumn(8).setCellRenderer(new GDItemTableCellRenderer());
/* 333 */     this.table.getColumnModel().getColumn(9).setCellRenderer(new GDItemTableCellRenderer());
/* 334 */     this.table.getColumnModel().getColumn(10).setCellRenderer(new GDItemTableCellRenderer());
/* 335 */     this.table.getColumnModel().getColumn(11).setCellRenderer(new GDItemTableCellRenderer());
/* 336 */     this.table.getColumnModel().getColumn(12).setCellRenderer(new GDItemTableCellRenderer());
/*     */     
/* 338 */     int scale = 100;
/* 339 */     int size = 12;
/* 340 */     if (GDStashFrame.iniConfig != null) {
/* 341 */       scale = GDStashFrame.iniConfig.sectUI.graphicScale;
/* 342 */       size = GDStashFrame.iniConfig.sectUI.fontSize;
/*     */     } 
/*     */     
/* 345 */     this.table.getColumnModel().getColumn(0).setPreferredWidth(3 * size);
/* 346 */     this.table.getColumnModel().getColumn(1).setPreferredWidth(3 * size);
/* 347 */     this.table.getColumnModel().getColumn(2).setPreferredWidth(100 * scale / 100);
/* 348 */     this.table.getColumnModel().getColumn(3).setPreferredWidth(25 * size);
/* 349 */     this.table.getColumnModel().getColumn(4).setPreferredWidth(3 * size);
/* 350 */     this.table.getColumnModel().getColumn(5).setPreferredWidth(8 * size);
/* 351 */     this.table.getColumnModel().getColumn(6).setPreferredWidth(8 * size);
/* 352 */     this.table.getColumnModel().getColumn(7).setPreferredWidth(8 * size);
/* 353 */     this.table.getColumnModel().getColumn(8).setPreferredWidth(8 * size);
/* 354 */     this.table.getColumnModel().getColumn(9).setPreferredWidth(8 * size);
/* 355 */     this.table.getColumnModel().getColumn(10).setPreferredWidth(8 * size);
/* 356 */     this.table.getColumnModel().getColumn(11).setPreferredWidth(8 * size);
/* 357 */     this.table.getColumnModel().getColumn(12).setPreferredWidth(25 * size);
/* 358 */     this.table.getColumnModel().getColumn(13).setPreferredWidth(3 * size);
/*     */   }
/*     */   
/*     */   public void setItems(List<GDItem> list) {
/* 362 */     this.data = list;
/*     */     
/* 364 */     if (this.data != null) Collections.sort(this.data);
/*     */     
/* 366 */     int height = -1;
/*     */     
/* 368 */     if (this.data != null) {
/* 369 */       for (GDItem item : this.data) {
/* 370 */         BufferedImage img = item.getImage();
/*     */         
/* 372 */         if (img != null && 
/* 373 */           img.getHeight() > height) height = img.getHeight();
/*     */       
/*     */       } 
/*     */     }
/*     */     
/* 378 */     if (height == -1) height = 12;
/*     */     
/* 380 */     this.model = new GDMassImportTableModel(this.data);
/* 381 */     this.table.setModel((TableModel)this.model);
/* 382 */     this.table.setSelectionModel(new GDItemTableSelectionModel(this.model));
/* 383 */     this.table.setSelectionMode(0);
/* 384 */     GDMassImportSorter sorter = new GDMassImportSorter(this.model);
/* 385 */     this.table.setRowSorter((RowSorter<? extends TableModel>)sorter);
/*     */     
/* 387 */     if (height != -1) {
/* 388 */       height = height * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 389 */       this.table.setRowHeight(height);
/*     */     } 
/*     */     
/* 392 */     updateTableColumns();
/*     */   }
/*     */   
/*     */   public List<GDItem> getSelectedItems() {
/* 396 */     return this.model.getSelectedItems();
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\GDItemImportTablePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */