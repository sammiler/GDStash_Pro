/*     */ package org.gdstash.ui;
/*     */ 
/*     */ import java.awt.*;
/*     */
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
/*     */ import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
/*     */ import javax.swing.*;
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
/*     */ import org.gdstash.db.DBItem;
/*     */ import org.gdstash.db.DBStashItem;
import org.gdstash.db.SelectionCriteria;
/*     */ import org.gdstash.item.GDItem;
/*     */ import org.gdstash.ui.select.ItemSelectionPane;
import org.gdstash.ui.table.GDItemCollectionSorter;
/*     */ import org.gdstash.ui.table.GDItemCollectionTableModel;
/*     */ import org.gdstash.ui.table.GDItemInfo;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
import org.gdstash.util.GDImagePool;
/*     */ import org.gdstash.util.GDMsgFormatter;
import org.gdstash.util.GDMsgLogger;

/*     */
/*     */ public class GDCollectionPane extends AdjustablePanel {
/*     */   private GDStashFrame frame;
/*     */   private List<GDItemInfo> data;
/*     */   private List<GDItem> itemsAll;
/*     */   private List<GDItem> itemsFound;
/*     */   private GDItemCollectionTableModel model;
/*     */   private JTable table;
/*     */   private JPanel pnlInfo;
/*     */   private JLabel lblCountSC;
/*     */   private JTextField tfCountSC;
/*     */   private JTextField tfPercSC;
/*     */   private JLabel lblCountHC;
/*     */   private JTextField tfCountHC;
/*     */   private JTextField tfPercHC;
/*     */   private int countAll;
/*     */   private int countSC;
/*     */   private int countHC;
/*     */   private float ratioSC;
/*     */   private float ratioHC;
/*     */   private JButton btnSearch;
/*     */   private JButton btnClear;
/*     */   private JButton btnHelp;
/*     */   private ItemSelectionPane pnlSelect;
/*     */   private JButton btnFilterAll;
/*     */   private JButton btnFilterFound;
/*     */   private JButton btnFilterMiss;
/*     */   
/*     */   private static class GDItemTableCellRenderer extends DefaultTableCellRenderer { public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
/*  56 */       if (col == 0) return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
/*     */ 
/*     */       
/*  59 */       JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
/*     */ 
/*     */       
/*  62 */       GDItemCollectionTableModel model = (GDItemCollectionTableModel)table.getModel();
/*     */       
/*  64 */       label.setForeground(model.getCellTextColor(row, col));
/*  65 */       label.setBackground(model.getCellBackgroundColor(row, col));
/*     */ 
/*     */       
/*  68 */       return label;
/*     */     }
/*     */     private GDItemTableCellRenderer() {} }
/*     */   
/*     */   private class SearchItemsListener implements ActionListener { private SearchItemsListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  75 */       GDCollectionPane.this.setCursor(Cursor.getPredefinedCursor(3));
/*     */       
/*  77 */       GDCollectionPane.this.search();
/*     */       
/*  79 */       GDCollectionPane.this.setCursor(Cursor.getDefaultCursor());
/*     */     } }
/*     */   
/*     */   private class ClearActionListener implements ActionListener {
/*     */     private ClearActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  86 */       GDCollectionPane.this.pnlSelect.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   private class SelectFoundListener implements ActionListener { private SelectFoundListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  93 */       GDCollectionPane.this.model.filter(2);
/*     */     } }
/*     */   
/*     */   private class SelectMissingListener implements ActionListener {
/*     */     private SelectMissingListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/* 100 */       GDCollectionPane.this.model.filter(3);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SelectAllListener implements ActionListener { private SelectAllListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/* 107 */       GDCollectionPane.this.model.filter(1);
/*     */     } }
/*     */   
/*     */   private class SortListener extends MouseAdapter {
/*     */     private SortListener() {}
/*     */     
/*     */     public void mouseClicked(MouseEvent ev) {
/* 114 */       int idx = GDCollectionPane.this.table.getColumnModel().getColumnIndexAtX(ev.getX());
/*     */       
/* 116 */       GDItemCollectionSorter sorter = (GDItemCollectionSorter)GDCollectionPane.this.table.getRowSorter();
/*     */       
/* 118 */       sorter.sort();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GDCollectionPane(GDStashFrame frame) {
/* 153 */     this.frame = frame;
/*     */     
/* 155 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 160 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 161 */     Font fntText = UIManager.getDefaults().getFont("TextField.font");
/* 162 */     if (fntText == null) fntText = fntLabel; 
/* 163 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 164 */     if (fntBorder == null) fntBorder = fntLabel; 
/* 165 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/* 166 */     if (fntButton == null) fntButton = fntLabel; 
/* 167 */     Font fntTable = UIManager.getDefaults().getFont("TableHeader.font");
/* 168 */     if (fntTable == null) fntTable = fntLabel;
/*     */     
/* 170 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 171 */     fntText = fntText.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 172 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 173 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 174 */     fntTable = fntTable.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 176 */     if (this.btnSearch == null) {
/* 177 */       this.btnSearch = new JButton();
/*     */       
/* 179 */       this.btnSearch.addActionListener(new SearchItemsListener());
/*     */     } 
/* 181 */     this.btnSearch.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_SEARCH"));
/* 182 */     this.btnSearch.setIcon(GDImagePool.iconBtnSearch24);
/* 183 */     this.btnSearch.setFont(fntButton);
/* 184 */     GDStashFrame.setMnemonic(this.btnSearch, "MNC_SEARCH");
/*     */     
/* 186 */     if (this.btnClear == null) {
/* 187 */       this.btnClear = new JButton();
/*     */       
/* 189 */       this.btnClear.addActionListener(new ClearActionListener());
/*     */     } 
/* 191 */     this.btnClear.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_CLEAR"));
/* 192 */     this.btnClear.setIcon(GDImagePool.iconItemDelete24);
/* 193 */     this.btnClear.setFont(fntButton);
/* 194 */     GDStashFrame.setMnemonic(this.btnClear, "MNC_CLEAR");
/*     */     
/* 196 */     if (this.btnHelp == null) {
/* 197 */       this.btnHelp = new JButton();
/*     */       
/* 199 */       this.btnHelp.addActionListener(new GDHelpActionListener("06_collection.html"));
/*     */     } 
/* 201 */     this.btnHelp.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_HELP"));
/* 202 */     this.btnHelp.setIcon(GDImagePool.iconBtnQuestion24);
/* 203 */     this.btnHelp.setFont(fntButton);
/* 204 */     GDStashFrame.setMnemonic(this.btnHelp, "MNC_HELP");
/*     */     
/* 206 */     if (this.btnFilterAll == null) {
/* 207 */       this.btnFilterAll = new JButton();
/*     */       
/* 209 */       this.btnFilterAll.addActionListener(new SelectAllListener());
/*     */     } 
/* 211 */     this.btnFilterAll.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_FILTER_ALL"));
/* 212 */     this.btnFilterAll.setIcon(GDImagePool.iconCollectAll24);
/* 213 */     this.btnFilterAll.setFont(fntButton);
/* 214 */     GDStashFrame.setMnemonic(this.btnFilterAll, "MNC_FILTER_ALL");
/*     */     
/* 216 */     if (this.btnFilterFound == null) {
/* 217 */       this.btnFilterFound = new JButton();
/*     */       
/* 219 */       this.btnFilterFound.addActionListener(new SelectFoundListener());
/*     */     } 
/* 221 */     this.btnFilterFound.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_FILTER_FOUND"));
/* 222 */     this.btnFilterFound.setIcon(GDImagePool.iconCollectFound24);
/* 223 */     this.btnFilterFound.setFont(fntButton);
/* 224 */     GDStashFrame.setMnemonic(this.btnFilterFound, "MNC_FILTER_FOUND");
/*     */     
/* 226 */     if (this.btnFilterMiss == null) {
/* 227 */       this.btnFilterMiss = new JButton();
/*     */       
/* 229 */       this.btnFilterMiss.addActionListener(new SelectMissingListener());
/*     */     } 
/* 231 */     this.btnFilterMiss.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_FILTER_MISS"));
/* 232 */     this.btnFilterMiss.setIcon(GDImagePool.iconCollectMiss24);
/* 233 */     this.btnFilterMiss.setFont(fntButton);
/* 234 */     GDStashFrame.setMnemonic(this.btnFilterMiss, "MNC_FILTER_MISS");
/*     */     
/* 236 */     if (this.table == null) {
/* 237 */       this.table = new JTable();
/*     */       
/* 239 */       GDItemCollectionSorter sorter = new GDItemCollectionSorter();
/* 240 */       this.table.setRowSorter((RowSorter<? extends TableModel>)sorter);
/*     */       
/* 242 */       this.table.getTableHeader().addMouseListener(new SortListener());
/*     */       
/* 244 */       setItems((List<GDItemInfo>)null);
/*     */     } 
/* 246 */     this.table.getTableHeader().setFont(fntTable);
/* 247 */     this.table.setFont(fntLabel);
/*     */ 
/*     */     
/* 250 */     int height = -1;
/*     */     
/* 252 */     if (this.data != null) {
/* 253 */       for (GDItemInfo info : this.data) {
/* 254 */         BufferedImage img = info.gdItem.getImage();
/*     */         
/* 256 */         if (img != null && 
/* 257 */           img.getHeight() > height) height = img.getHeight();
/*     */       
/*     */       } 
/*     */     }
/*     */     
/* 262 */     if (height == -1) height = 12;
/*     */     
/* 264 */     height = height * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 265 */     this.table.setRowHeight(height);
/*     */     
/* 267 */     GDItemCollectionTableModel model = (GDItemCollectionTableModel)this.table.getModel();
/* 268 */     model.adjustUI();
/*     */ 
/*     */     
/* 271 */     updateTableColumns();
/*     */     
/* 273 */     if (this.lblCountSC == null) {
/* 274 */       this.lblCountSC = new JLabel();
/*     */     }
/* 276 */     this.lblCountSC.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_SOFTCORE"));
/*     */     
/* 278 */     if (this.tfCountSC == null) {
/* 279 */       this.tfCountSC = new JTextField();
/*     */       
/* 281 */       this.tfCountSC.setEditable(false);
/* 282 */       this.tfCountSC.setColumns(9);
/*     */     } 
/* 284 */     Object[] argcntSC = { Integer.valueOf(this.countSC), Integer.valueOf(this.countAll) };
/* 285 */     this.tfCountSC.setText(GDMsgFormatter.format(GDMsgFormatter.rbUI, "TXT_ITEM_COUNT", argcntSC));
/*     */     
/* 287 */     if (this.tfPercSC == null) {
/* 288 */       this.tfPercSC = new JTextField();
/*     */       
/* 290 */       this.tfPercSC.setEditable(false);
/*     */     } 
/* 292 */     Object[] argpctSC = { Float.valueOf(this.ratioSC) };
/* 293 */     this.tfPercSC.setText(GDMsgFormatter.format(GDMsgFormatter.rbUI, "TXT_ITEM_PERCENT", argpctSC));
/*     */     
/* 295 */     if (this.lblCountHC == null) {
/* 296 */       this.lblCountHC = new JLabel();
/*     */     }
/* 298 */     this.lblCountHC.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_HARDCORE"));
/*     */     
/* 300 */     if (this.tfCountHC == null) {
/* 301 */       this.tfCountHC = new JTextField();
/*     */       
/* 303 */       this.tfCountHC.setEditable(false);
/*     */     } 
/* 305 */     Object[] argcntHC = { Integer.valueOf(this.countSC), Integer.valueOf(this.countAll) };
/* 306 */     this.tfCountHC.setText(GDMsgFormatter.format(GDMsgFormatter.rbUI, "TXT_ITEM_COUNT", argcntHC));
/*     */     
/* 308 */     if (this.tfPercHC == null) {
/* 309 */       this.tfPercHC = new JTextField();
/*     */       
/* 311 */       this.tfPercHC.setEditable(false);
/*     */     } 
/* 313 */     Object[] argpctHC = { Float.valueOf(this.ratioSC) };
/* 314 */     this.tfPercHC.setText(GDMsgFormatter.format(GDMsgFormatter.rbUI, "TXT_ITEM_PERCENT", argpctHC));
/*     */     
/* 316 */     if (this.pnlSelect == null) {
/* 317 */       this.pnlSelect = new ItemSelectionPane(GDTabbedSearchDialog.Mode.SEARCH);
/*     */     } else {
/* 319 */       this.pnlSelect.adjustUI();
/*     */     } 
/*     */     
/* 322 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 323 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 324 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 325 */     TitledBorder title = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_COLL_OVERVIEW"));
/* 326 */     title.setTitleFont(fntBorder);
/*     */     
/* 328 */     if (this.pnlInfo == null) {
/* 329 */       buildInfoPanel();
/*     */     }
/*     */     
/* 332 */     this.pnlInfo.setBorder(title);
/*     */   }
/*     */   
/*     */   public void refresh() {
/* 336 */     if (this.table != null) {
/* 337 */       GDItemCollectionTableModel model = (GDItemCollectionTableModel)this.table.getModel();
/* 338 */       model.setData(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void init() {
/* 343 */     this.countAll = 0;
/* 344 */     this.countSC = 0;
/* 345 */     this.countHC = 0;
/* 346 */     this.ratioSC = 0.0F;
/* 347 */     this.ratioHC = 0.0F;
/*     */     
/* 349 */     adjustUI();
/*     */     
/* 351 */     GroupLayout layout = null;
/* 352 */     GroupLayout.SequentialGroup hGroup = null;
/* 353 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 355 */     JPanel pnlMain = buildMainPanel();
/* 356 */     JScrollPane scroll = new JScrollPane(pnlMain);
/* 357 */     scroll.getVerticalScrollBar().setUnitIncrement(100);
/* 358 */     scroll.getVerticalScrollBar().putClientProperty("JScrollBar.fastWheelScrolling", Boolean.valueOf(true));
/* 359 */     scroll.setWheelScrollingEnabled(true);
/*     */     
/* 361 */     layout = new GroupLayout((Container)this);
/* 362 */     setLayout(layout);
/*     */     
/* 364 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 367 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 370 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 373 */     hGroup
/* 374 */       .addGroup(layout.createParallelGroup()
/* 375 */         .addComponent(scroll));
/* 376 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 379 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 382 */     vGroup
/* 383 */       .addGroup(layout.createParallelGroup()
/* 384 */         .addComponent(scroll));
/* 385 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel buildMainPanel() {
/* 391 */     JPanel panel = new JPanel();
/*     */     
/* 393 */     GroupLayout layout = null;
/* 394 */     GroupLayout.SequentialGroup hGroup = null;
/* 395 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 397 */     JPanel pnlSearch = buildSearchPanel();
/*     */     
/* 399 */     JPanel pnlTable = buildTablePanel();
/*     */ 
/*     */ 
/*     */     
/* 403 */     setItems(this.data);
/*     */     
/* 405 */     layout = new GroupLayout(panel);
/* 406 */     panel.setLayout(layout);
/*     */     
/* 408 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 411 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 414 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 417 */     hGroup
/* 418 */       .addGroup(layout.createParallelGroup()
/* 419 */         .addComponent(pnlSearch))
/* 420 */       .addGroup(layout.createParallelGroup()
/* 421 */         .addComponent(pnlTable));
/* 422 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 425 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 428 */     vGroup
/* 429 */       .addGroup(layout.createParallelGroup()
/* 430 */         .addComponent(pnlSearch)
/* 431 */         .addComponent(pnlTable));
/* 432 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 434 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildButtonPanel() {
/* 438 */     BorderLayout layout = new BorderLayout();
/*     */     
/* 440 */     JPanel panel = new JPanel();
/*     */     
/* 442 */     panel.setLayout(layout);
/*     */     
/* 444 */     panel.add(this.btnSearch, "Center");
/* 445 */     panel.add(this.btnClear, "West");
/* 446 */     panel.add(this.btnHelp, "East");
/*     */     
/* 448 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildSearchPanel() {
/* 452 */     GroupLayout layout = null;
/* 453 */     GroupLayout.SequentialGroup hGroup = null;
/* 454 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 456 */     JPanel pnlButton = buildButtonPanel();
/*     */     
/* 458 */     JPanel panel = new JPanel();
/*     */     
/* 460 */     layout = new GroupLayout(panel);
/* 461 */     panel.setLayout(layout);
/*     */     
/* 463 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 466 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 469 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 472 */     hGroup
/* 473 */       .addGroup(layout.createParallelGroup()
/* 474 */         .addComponent(pnlButton)
/* 475 */         .addComponent((Component)this.pnlSelect)
/* 476 */         .addComponent(this.pnlInfo));
/* 477 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 480 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 483 */     vGroup
/* 484 */       .addGroup(layout.createParallelGroup()
/* 485 */         .addComponent(pnlButton))
/* 486 */       .addGroup(layout.createParallelGroup()
/* 487 */         .addComponent((Component)this.pnlSelect))
/* 488 */       .addGroup(layout.createParallelGroup()
/* 489 */         .addComponent(this.pnlInfo));
/* 490 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 492 */     layout.linkSize(0, new Component[] { pnlButton, (Component)this.pnlSelect });
/* 493 */     layout.linkSize(0, new Component[] { pnlButton, this.pnlInfo });
/*     */     
/* 495 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildTablePanel() {
/* 499 */     JPanel panel = new JPanel();
/*     */     
/* 501 */     GroupLayout layout = null;
/* 502 */     GroupLayout.SequentialGroup hGroup = null;
/* 503 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 505 */     JPanel pnlFilter = buildFilterPanel();
/*     */     
/* 507 */     JScrollPane scrTable = new JScrollPane(this.table);
/*     */ 
/*     */ 
/*     */     
/* 511 */     layout = new GroupLayout(panel);
/* 512 */     panel.setLayout(layout);
/*     */     
/* 514 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 517 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 520 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 523 */     hGroup
/* 524 */       .addGroup(layout.createParallelGroup()
/* 525 */         .addComponent(scrTable)
/* 526 */         .addComponent(pnlFilter));
/* 527 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 530 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 533 */     vGroup
/* 534 */       .addGroup(layout.createParallelGroup()
/* 535 */         .addComponent(scrTable))
/* 536 */       .addGroup(layout.createParallelGroup()
/* 537 */         .addComponent(pnlFilter));
/* 538 */     layout.setVerticalGroup(vGroup);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 544 */     return panel;
/*     */   }
/*     */   
/*     */   private void buildInfoPanel() {
/* 548 */     this.pnlInfo = new JPanel();
/*     */     
/* 550 */     GroupLayout layout = null;
/* 551 */     GroupLayout.SequentialGroup hGroup = null;
/* 552 */     GroupLayout.SequentialGroup vGroup = null;
/*     */ 
/*     */ 
/*     */     
/* 556 */     layout = new GroupLayout(this.pnlInfo);
/* 557 */     this.pnlInfo.setLayout(layout);
/*     */     
/* 559 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 562 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 565 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 568 */     hGroup
/* 569 */       .addGroup(layout.createParallelGroup()
/* 570 */         .addComponent(this.lblCountSC)
/* 571 */         .addComponent(this.lblCountHC))
/* 572 */       .addGroup(layout.createParallelGroup()
/* 573 */         .addComponent(this.tfCountSC)
/* 574 */         .addComponent(this.tfCountHC))
/* 575 */       .addGroup(layout.createParallelGroup()
/* 576 */         .addComponent(this.tfPercSC)
/* 577 */         .addComponent(this.tfPercHC));
/* 578 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 581 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 584 */     vGroup
/* 585 */       .addGroup(layout.createParallelGroup()
/* 586 */         .addComponent(this.lblCountSC)
/* 587 */         .addComponent(this.tfCountSC)
/* 588 */         .addComponent(this.tfPercSC))
/* 589 */       .addGroup(layout.createParallelGroup()
/* 590 */         .addComponent(this.lblCountHC)
/* 591 */         .addComponent(this.tfCountHC)
/* 592 */         .addComponent(this.tfPercHC));
/* 593 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 595 */     layout.linkSize(1, new Component[] { this.lblCountSC, this.tfCountSC });
/* 596 */     layout.linkSize(1, new Component[] { this.lblCountSC, this.tfPercSC });
/* 597 */     layout.linkSize(1, new Component[] { this.lblCountSC, this.lblCountHC });
/* 598 */     layout.linkSize(1, new Component[] { this.lblCountSC, this.tfCountHC });
/* 599 */     layout.linkSize(1, new Component[] { this.lblCountSC, this.tfPercHC });
/*     */     
/* 601 */     layout.linkSize(0, new Component[] { this.lblCountSC, this.lblCountHC });
/* 602 */     layout.linkSize(0, new Component[] { this.tfCountSC, this.tfPercSC });
/* 603 */     layout.linkSize(0, new Component[] { this.tfCountSC, this.tfCountHC });
/* 604 */     layout.linkSize(0, new Component[] { this.tfCountSC, this.tfPercHC });
/*     */   }
/*     */   
/*     */   private JPanel buildFilterPanel() {
/* 608 */     GroupLayout layout = null;
/* 609 */     GroupLayout.SequentialGroup hGroup = null;
/* 610 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 612 */     JPanel panel = new JPanel();
/*     */     
/* 614 */     layout = new GroupLayout(panel);
/* 615 */     panel.setLayout(layout);
/*     */     
/* 617 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 620 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 623 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 626 */     hGroup
/* 627 */       .addGroup(layout.createParallelGroup()
/* 628 */         .addComponent(this.btnFilterMiss))
/* 629 */       .addGroup(layout.createParallelGroup()
/* 630 */         .addComponent(this.btnFilterFound))
/* 631 */       .addGroup(layout.createParallelGroup()
/* 632 */         .addComponent(this.btnFilterAll));
/* 633 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 636 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 639 */     vGroup
/* 640 */       .addGroup(layout.createParallelGroup()
/* 641 */         .addComponent(this.btnFilterMiss)
/* 642 */         .addComponent(this.btnFilterFound)
/* 643 */         .addComponent(this.btnFilterAll));
/* 644 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 646 */     layout.linkSize(0, new Component[] { this.btnFilterMiss, this.btnFilterFound });
/* 647 */     layout.linkSize(0, new Component[] { this.btnFilterMiss, this.btnFilterAll });
/*     */     
/* 649 */     layout.linkSize(1, new Component[] { this.btnFilterMiss, this.btnFilterFound });
/* 650 */     layout.linkSize(1, new Component[] { this.btnFilterMiss, this.btnFilterAll });
/*     */     
/* 652 */     return panel;
/*     */   }
/*     */   
/*     */   public void updateConfig() {
/* 656 */     if (this.pnlSelect != null) this.pnlSelect.updateConfig(); 
/*     */   }
/*     */   
/*     */   private void search() {
/* 660 */     this.itemsAll = new LinkedList<>();
/* 661 */     List<String> itemIDs = new LinkedList<>();
/*     */     
/* 663 */     SelectionCriteria criteria = new SelectionCriteria();
/*     */     
/* 665 */     this.pnlSelect.addCriteria(criteria);
/* 666 */     criteria.noEnemyOnly = true;
/*     */     
/* 668 */     if ((criteria.itemIDs == null || criteria.itemIDs
/* 669 */       .isEmpty()) && (criteria.itemClass == null || criteria.itemClass
/*     */       
/* 671 */       .isEmpty())) {
/*     */       
/* 673 */       GDDialog dialog = new GDDialog(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERR_SEL_ITEM_TYPE"), 4, this.frame, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "ERROR"), true);
/* 674 */       dialog.setVisible(true);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 681 */     List<DBItem> dbItems = DBItem.getByCriteria(criteria);
/*     */     
/* 683 */     for (DBItem dbi : dbItems) {
/* 684 */       GDItem item = new GDItem(dbi);
/*     */       
/* 686 */       this.itemsAll.add(item);
/* 687 */       if (dbi == null) {
/* 688 */         itemIDs.add(null); continue;
/*     */       } 
/* 690 */       itemIDs.add(dbi.getItemID());
/*     */     } 
/*     */ 
/*     */     
/* 694 */     Collections.sort(this.itemsAll);
/*     */     
/* 696 */     this.itemsFound = DBStashItem.getGDItemsByItemIDs(itemIDs);
/*     */     
/* 698 */     this.data = GDItemInfo.buildItemInfo(this.itemsAll, this.itemsFound);
/*     */     
/* 700 */     setItems(this.data);
/*     */     
/* 702 */     GDMsgLogger.showLog((Component)this, GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_SEARCH"));
/*     */   }
/*     */   
/*     */   private void updateTableColumns() {
/* 706 */     this.table.getColumnModel().getColumn(1).setCellRenderer(new GDItemTableCellRenderer());
/* 707 */     this.table.getColumnModel().getColumn(2).setCellRenderer(new GDItemTableCellRenderer());
/*     */     
/* 709 */     int scale = 100;
/* 710 */     int size = 12;
/* 711 */     if (GDStashFrame.iniConfig != null) {
/* 712 */       scale = GDStashFrame.iniConfig.sectUI.graphicScale;
/* 713 */       size = GDStashFrame.iniConfig.sectUI.fontSize;
/*     */     } 
/* 715 */     this.table.getColumnModel().getColumn(0).setPreferredWidth(100 * scale / 100);
/* 716 */     this.table.getColumnModel().getColumn(1).setPreferredWidth(42 * size);
/* 717 */     this.table.getColumnModel().getColumn(2).setPreferredWidth(8 * size);
/* 718 */     this.table.getColumnModel().getColumn(3).setPreferredWidth(3 * size);
/* 719 */     this.table.getColumnModel().getColumn(4).setPreferredWidth(3 * size);
/*     */   }
/*     */   
/*     */   public void setItems(List<GDItemInfo> list) {
/* 723 */     this.data = list;
/*     */     
/* 725 */     if (this.data != null) Collections.sort(this.data);
/*     */     
/* 727 */     int height = -1;
/* 728 */     this.countAll = 0;
/* 729 */     this.countSC = 0;
/* 730 */     this.countHC = 0;
/* 731 */     this.ratioSC = 0.0F;
/* 732 */     this.ratioHC = 0.0F;
/*     */     
/* 734 */     if (this.data != null) {
/* 735 */       for (GDItemInfo info : this.data) {
/* 736 */         if (info == null)
/*     */           continue; 
/* 738 */         BufferedImage img = info.gdItem.getImage();
/*     */         
/* 740 */         if (img != null && 
/* 741 */           img.getHeight() > height) height = img.getHeight();
/*     */ 
/*     */         
/* 744 */         this.countAll++;
/* 745 */         if (info.scCount != 0) this.countSC++; 
/* 746 */         if (info.hcCount != 0) this.countHC++;
/*     */       
/*     */       } 
/*     */     }
/* 750 */     if (height == -1) height = 12;
/*     */     
/* 752 */     if (this.countAll > 0) this.ratioSC = this.countSC / this.countAll * 100.0F; 
/* 753 */     if (this.countAll > 0) this.ratioHC = this.countHC / this.countAll * 100.0F;
/*     */     
/* 755 */     if (this.tfCountSC != null) {
/* 756 */       Object[] argcntSC = { Integer.valueOf(this.countSC), Integer.valueOf(this.countAll) };
/* 757 */       this.tfCountSC.setText(GDMsgFormatter.format(GDMsgFormatter.rbUI, "TXT_ITEM_COUNT", argcntSC));
/*     */     } 
/*     */     
/* 760 */     if (this.tfPercSC != null) {
/* 761 */       Object[] argpctSC = { Float.valueOf(this.ratioSC) };
/* 762 */       this.tfPercSC.setText(GDMsgFormatter.format(GDMsgFormatter.rbUI, "TXT_ITEM_PERCENT", argpctSC));
/*     */     } 
/*     */     
/* 765 */     if (this.tfCountHC != null) {
/* 766 */       Object[] argcntHC = { Integer.valueOf(this.countHC), Integer.valueOf(this.countAll) };
/* 767 */       this.tfCountHC.setText(GDMsgFormatter.format(GDMsgFormatter.rbUI, "TXT_ITEM_COUNT", argcntHC));
/*     */     } 
/*     */     
/* 770 */     if (this.tfPercHC != null) {
/* 771 */       Object[] argpctHC = { Float.valueOf(this.ratioHC) };
/* 772 */       this.tfPercHC.setText(GDMsgFormatter.format(GDMsgFormatter.rbUI, "TXT_ITEM_PERCENT", argpctHC));
/*     */     } 
/*     */     
/* 775 */     this.model = new GDItemCollectionTableModel(this.data);
/* 776 */     this.table.setModel((TableModel)this.model);
/* 777 */     this.table.setSelectionMode(0);
/* 778 */     this.table.getSelectionModel().addListSelectionListener(this.table);
/* 779 */     GDItemCollectionSorter sorter = new GDItemCollectionSorter(this.model);
/* 780 */     this.table.setRowSorter((RowSorter<? extends TableModel>)sorter);
/*     */     
/* 782 */     updateTableColumns();
/*     */ 
/*     */     
/* 785 */     Rectangle rect = this.table.getVisibleRect();
/* 786 */     rect.setLocation(0, 0);
/* 787 */     this.table.scrollRectToVisible(rect);
/*     */     
/* 789 */     if (height != -1) {
/* 790 */       height = height * GDStashFrame.iniConfig.sectUI.graphicScale / 100;
/* 791 */       this.table.setRowHeight(height);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\GDCollectionPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */