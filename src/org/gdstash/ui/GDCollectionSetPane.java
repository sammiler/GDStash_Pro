/*     */ package org.gdstash.ui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Font;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.RowSorter;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import javax.swing.table.DefaultTableCellRenderer;
/*     */ import javax.swing.table.TableModel;
/*     */ import org.gdstash.ui.select.ItemSetSelectionPane;
/*     */ import org.gdstash.ui.table.GDItemSetCollectionSorter;
/*     */ import org.gdstash.ui.table.GDItemSetCollectionTableModel;
/*     */ import org.gdstash.ui.table.GDItemSetInfo;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ import org.gdstash.util.GDImagePool;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDCollectionSetPane
/*     */   extends AdjustablePanel {
/*     */   private GDStashFrame frame;
/*     */   private List<GDItemSetInfo> data;
/*     */   private GDItemSetCollectionTableModel model;
/*     */   private JTable table;
/*     */   private JButton btnSearch;
/*     */   private JButton btnClear;
/*     */   private JButton btnHelp;
/*     */   private ItemSetSelectionPane pnlSelect;
/*     */   
/*     */   private static class GDItemSetTableCellRenderer extends DefaultTableCellRenderer {
/*     */     public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
/*  51 */       if (col <= 2) {
/*     */         
/*  53 */         JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
/*     */ 
/*     */         
/*  56 */         GDItemSetCollectionTableModel model = (GDItemSetCollectionTableModel)table.getModel();
/*     */         
/*  58 */         label.setForeground(model.getCellTextColor(row, col));
/*  59 */         label.setBackground(model.getCellBackgroundColor(row, col));
/*     */ 
/*     */         
/*  62 */         return label;
/*     */       } 
/*     */       
/*  65 */       if (col >= 3 && col <= 8) {
/*  66 */         JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
/*     */         
/*  68 */         GDItemSetCollectionTableModel model = (GDItemSetCollectionTableModel)table.getModel();
/*     */         
/*  70 */         label.setIcon((Icon)value);
/*  71 */         label.setText((String)null);
/*  72 */         label.setHorizontalAlignment(0);
/*  73 */         label.setBackground(model.getCellBackgroundColor(row, col));
/*     */ 
/*     */         
/*  76 */         return label;
/*     */       } 
/*     */       
/*  79 */       return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
/*     */     }
/*     */     private GDItemSetTableCellRenderer() {} }
/*     */   
/*     */   private class SearchItemsListener implements ActionListener { private SearchItemsListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  86 */       GDCollectionSetPane.this.setCursor(Cursor.getPredefinedCursor(3));
/*     */       
/*  88 */       GDCollectionSetPane.this.search();
/*     */       
/*  90 */       GDCollectionSetPane.this.setCursor(Cursor.getDefaultCursor());
/*     */     } }
/*     */ 
/*     */   
/*     */   private class ClearActionListener implements ActionListener { private ClearActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  97 */       GDCollectionSetPane.this.pnlSelect.clear();
/*     */     } }
/*     */   
/*     */   private class SortListener extends MouseAdapter {
/*     */     private SortListener() {}
/*     */     
/*     */     public void mouseClicked(MouseEvent ev) {
/* 104 */       int idx = GDCollectionSetPane.this.table.getColumnModel().getColumnIndexAtX(ev.getX());
/*     */       
/* 106 */       GDItemSetCollectionSorter sorter = (GDItemSetCollectionSorter)GDCollectionSetPane.this.table.getRowSorter();
/*     */       
/* 108 */       sorter.sort();
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
/*     */ 
/*     */   
/*     */   public GDCollectionSetPane(GDStashFrame frame) {
/* 124 */     this.frame = frame;
/*     */     
/* 126 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 131 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 132 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 133 */     if (fntBorder == null) fntBorder = fntLabel; 
/* 134 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/* 135 */     if (fntButton == null) fntButton = fntLabel; 
/* 136 */     Font fntTable = UIManager.getDefaults().getFont("TableHeader.font");
/* 137 */     if (fntTable == null) fntTable = fntLabel;
/*     */     
/* 139 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 140 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 141 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 142 */     fntTable = fntTable.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 144 */     if (this.btnSearch == null) {
/* 145 */       this.btnSearch = new JButton();
/*     */       
/* 147 */       this.btnSearch.addActionListener(new SearchItemsListener());
/*     */     } 
/* 149 */     this.btnSearch.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_SEARCH"));
/* 150 */     this.btnSearch.setIcon(GDImagePool.iconBtnSearch24);
/* 151 */     this.btnSearch.setFont(fntButton);
/* 152 */     GDStashFrame.setMnemonic(this.btnSearch, "MNC_SEARCH");
/*     */     
/* 154 */     if (this.btnClear == null) {
/* 155 */       this.btnClear = new JButton();
/*     */       
/* 157 */       this.btnClear.addActionListener(new ClearActionListener());
/*     */     } 
/* 159 */     this.btnClear.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_CLEAR"));
/* 160 */     this.btnClear.setIcon(GDImagePool.iconItemDelete24);
/* 161 */     this.btnClear.setFont(fntButton);
/* 162 */     GDStashFrame.setMnemonic(this.btnClear, "MNC_CLEAR");
/*     */     
/* 164 */     if (this.btnHelp == null) {
/* 165 */       this.btnHelp = new JButton();
/*     */       
/* 167 */       this.btnHelp.addActionListener(new GDHelpActionListener("06_collection.html"));
/*     */     } 
/* 169 */     this.btnHelp.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_HELP"));
/* 170 */     this.btnHelp.setIcon(GDImagePool.iconBtnQuestion24);
/* 171 */     this.btnHelp.setFont(fntButton);
/* 172 */     GDStashFrame.setMnemonic(this.btnHelp, "MNC_HELP");
/*     */     
/* 174 */     if (this.pnlSelect == null) {
/* 175 */       this.pnlSelect = new ItemSetSelectionPane();
/*     */     } else {
/* 177 */       this.pnlSelect.adjustUI();
/*     */     } 
/*     */     
/* 180 */     if (this.table == null) {
/* 181 */       this.table = new JTable();
/*     */       
/* 183 */       GDItemSetCollectionSorter sorter = new GDItemSetCollectionSorter();
/* 184 */       this.table.setRowSorter((RowSorter<? extends TableModel>)sorter);
/*     */       
/* 186 */       this.table.getTableHeader().addMouseListener(new SortListener());
/*     */       
/* 188 */       setItemSets((List<GDItemSetInfo>)null);
/*     */     } 
/* 190 */     this.table.getTableHeader().setFont(fntTable);
/* 191 */     this.table.setFont(fntLabel);
/*     */ 
/*     */     
/* 194 */     int height = -1;
/*     */     
/* 196 */     if (this.data != null) {
/* 197 */       for (GDItemSetInfo info : this.data) {
/* 198 */         int h = info.getMaxImageHeight();
/*     */         
/* 200 */         if (h > height) height = h;
/*     */       
/*     */       } 
/*     */     }
/* 204 */     if (height == -1) height = 12;
/*     */     
/* 206 */     height = height * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 207 */     this.table.setRowHeight(height);
/*     */     
/* 209 */     GDItemSetCollectionTableModel model = (GDItemSetCollectionTableModel)this.table.getModel();
/* 210 */     model.adjustUI();
/*     */ 
/*     */     
/* 213 */     updateTableColumns();
/*     */     
/* 215 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 216 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 217 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 218 */     TitledBorder title = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_COLL_OVERVIEW"));
/* 219 */     title.setTitleFont(fntBorder);
/*     */   }
/*     */   
/*     */   private void init() {
/* 223 */     adjustUI();
/*     */     
/* 225 */     GroupLayout layout = null;
/* 226 */     GroupLayout.SequentialGroup hGroup = null;
/* 227 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 229 */     JPanel pnlMain = buildMainPanel();
/* 230 */     JScrollPane scroll = new JScrollPane(pnlMain);
/* 231 */     scroll.getVerticalScrollBar().setUnitIncrement(100);
/* 232 */     scroll.getVerticalScrollBar().putClientProperty("JScrollBar.fastWheelScrolling", Boolean.valueOf(true));
/* 233 */     scroll.setWheelScrollingEnabled(true);
/*     */     
/* 235 */     layout = new GroupLayout((Container)this);
/* 236 */     setLayout(layout);
/*     */     
/* 238 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 241 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 244 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 247 */     hGroup
/* 248 */       .addGroup(layout.createParallelGroup()
/* 249 */         .addComponent(scroll));
/* 250 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 253 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 256 */     vGroup
/* 257 */       .addGroup(layout.createParallelGroup()
/* 258 */         .addComponent(scroll));
/* 259 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel buildMainPanel() {
/* 265 */     BorderLayout layout = new BorderLayout();
/*     */     
/* 267 */     JPanel panel = new JPanel();
/*     */     
/* 269 */     JPanel pnlSearch = buildSearchPanel();
/*     */     
/* 271 */     JPanel pnlTable = buildTablePanel();
/*     */     
/* 273 */     panel.setLayout(layout);
/*     */     
/* 275 */     panel.add(pnlSearch, "North");
/* 276 */     panel.add(pnlTable, "Center");
/*     */     
/* 278 */     setItemSets(this.data);
/*     */     
/* 280 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildSearchPanel() {
/* 284 */     JPanel pnlButton = new JPanel();
/*     */     
/* 286 */     pnlButton.setLayout(new BorderLayout());
/*     */     
/* 288 */     pnlButton.add(this.btnSearch, "Center");
/* 289 */     pnlButton.add(this.btnClear, "West");
/* 290 */     pnlButton.add(this.btnHelp, "East");
/*     */     
/* 292 */     JPanel panel = new JPanel();
/*     */     
/* 294 */     panel.setLayout(new BorderLayout());
/*     */     
/* 296 */     panel.add((Component)this.pnlSelect, "Center");
/* 297 */     panel.add(pnlButton, "North");
/*     */     
/* 299 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildTablePanel() {
/* 303 */     JPanel panel = new JPanel();
/*     */     
/* 305 */     GroupLayout layout = null;
/* 306 */     GroupLayout.SequentialGroup hGroup = null;
/* 307 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 309 */     JScrollPane scrTable = new JScrollPane(this.table);
/*     */ 
/*     */ 
/*     */     
/* 313 */     layout = new GroupLayout(panel);
/* 314 */     panel.setLayout(layout);
/*     */     
/* 316 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 319 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 322 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 325 */     hGroup
/* 326 */       .addGroup(layout.createParallelGroup()
/* 327 */         .addComponent(scrTable));
/* 328 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 331 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 334 */     vGroup
/* 335 */       .addGroup(layout.createParallelGroup()
/* 336 */         .addComponent(scrTable));
/* 337 */     layout.setVerticalGroup(vGroup);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 343 */     return panel;
/*     */   }
/*     */   
/*     */   private void updateTableColumns() {
/* 347 */     this.table.getColumnModel().getColumn(0).setCellRenderer(new GDItemSetTableCellRenderer());
/* 348 */     this.table.getColumnModel().getColumn(1).setCellRenderer(new GDItemSetTableCellRenderer());
/* 349 */     this.table.getColumnModel().getColumn(2).setCellRenderer(new GDItemSetTableCellRenderer());
/* 350 */     this.table.getColumnModel().getColumn(3).setCellRenderer(new GDItemSetTableCellRenderer());
/* 351 */     this.table.getColumnModel().getColumn(4).setCellRenderer(new GDItemSetTableCellRenderer());
/* 352 */     this.table.getColumnModel().getColumn(5).setCellRenderer(new GDItemSetTableCellRenderer());
/* 353 */     this.table.getColumnModel().getColumn(6).setCellRenderer(new GDItemSetTableCellRenderer());
/* 354 */     this.table.getColumnModel().getColumn(7).setCellRenderer(new GDItemSetTableCellRenderer());
/* 355 */     this.table.getColumnModel().getColumn(8).setCellRenderer(new GDItemSetTableCellRenderer());
/*     */     
/* 357 */     int scale = 100;
/* 358 */     int size = 12;
/* 359 */     if (GDStashFrame.iniConfig != null) {
/* 360 */       scale = GDStashFrame.iniConfig.sectUI.graphicScale;
/* 361 */       size = GDStashFrame.iniConfig.sectUI.fontSize;
/*     */     } 
/* 363 */     this.table.getColumnModel().getColumn(0).setPreferredWidth(42 * size);
/* 364 */     this.table.getColumnModel().getColumn(1).setPreferredWidth(12 * size);
/* 365 */     this.table.getColumnModel().getColumn(2).setPreferredWidth(12 * size);
/* 366 */     this.table.getColumnModel().getColumn(3).setPreferredWidth(100 * scale / 100);
/* 367 */     this.table.getColumnModel().getColumn(4).setPreferredWidth(100 * scale / 100);
/* 368 */     this.table.getColumnModel().getColumn(5).setPreferredWidth(100 * scale / 100);
/* 369 */     this.table.getColumnModel().getColumn(6).setPreferredWidth(100 * scale / 100);
/* 370 */     this.table.getColumnModel().getColumn(7).setPreferredWidth(100 * scale / 100);
/* 371 */     this.table.getColumnModel().getColumn(8).setPreferredWidth(100 * scale / 100);
/*     */   }
/*     */   
/*     */   public void setItemSets(List<GDItemSetInfo> list) {
/* 375 */     this.data = list;
/*     */     
/* 377 */     if (this.data != null) Collections.sort(this.data);
/*     */     
/* 379 */     int height = -1;
/*     */     
/* 381 */     if (this.data != null) {
/* 382 */       for (GDItemSetInfo info : this.data) {
/* 383 */         if (info == null)
/*     */           continue; 
/* 385 */         int h = info.getMaxImageHeight();
/*     */         
/* 387 */         if (h > height) height = h;
/*     */       
/*     */       } 
/*     */     }
/* 391 */     if (height == -1) height = 12;
/*     */     
/* 393 */     this.model = new GDItemSetCollectionTableModel(this.data);
/* 394 */     this.table.setModel((TableModel)this.model);
/* 395 */     this.table.setSelectionMode(0);
/* 396 */     this.table.getSelectionModel().addListSelectionListener(this.table);
/* 397 */     GDItemSetCollectionSorter sorter = new GDItemSetCollectionSorter(this.model);
/* 398 */     this.table.setRowSorter((RowSorter<? extends TableModel>)sorter);
/*     */     
/* 400 */     updateTableColumns();
/*     */ 
/*     */     
/* 403 */     Rectangle rect = this.table.getVisibleRect();
/* 404 */     rect.setLocation(0, 0);
/* 405 */     this.table.scrollRectToVisible(rect);
/*     */     
/* 407 */     if (height != -1) {
/* 408 */       height = height * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 409 */       this.table.setRowHeight(height);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void search() {
/* 414 */     this.data = GDItemSetInfo.buildSetInfo();
/*     */     
/* 416 */     int minLevel = this.pnlSelect.getMinLevel();
/* 417 */     int maxLevel = this.pnlSelect.getMaxLevel();
/* 418 */     List<String> rarities = this.pnlSelect.getRarityList();
/*     */     
/* 420 */     if (minLevel != -1 || maxLevel != -1 || !rarities.isEmpty()) {
/* 421 */       List<GDItemSetInfo> filtered = new LinkedList<>();
/*     */       
/* 423 */       for (GDItemSetInfo set : this.data) {
/* 424 */         int level = set.getItemSetLevel();
/* 425 */         String rarity = set.getItemSetRarity();
/* 426 */         boolean keep = true;
/*     */         
/* 428 */         if (minLevel != -1 && minLevel > level) keep = false; 
/* 429 */         if (maxLevel != -1 && maxLevel < level) keep = false;
/*     */         
/* 431 */         boolean found = false;
/* 432 */         for (String selRarity : rarities) {
/* 433 */           if (rarity.equals(selRarity)) {
/* 434 */             found = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */         
/* 440 */         if (!found) keep = false;
/*     */         
/* 442 */         if (keep) filtered.add(set);
/*     */       
/*     */       } 
/* 445 */       this.data = filtered;
/*     */     } 
/*     */     
/* 448 */     setItemSets(this.data);
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\GDCollectionSetPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */