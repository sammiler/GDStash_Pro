/*     */ package org.gdstash.ui;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.table.DefaultTableCellRenderer;
/*     */ import javax.swing.table.JTableHeader;
/*     */ import javax.swing.table.TableModel;
/*     */ import org.gdstash.db.DBStashItem;
/*     */ import org.gdstash.ui.table.GDItemSimilarTableHeader;
/*     */ import org.gdstash.ui.table.GDItemSimilarTableModel;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ 
/*     */ public class GDItemInfoSimilarTablePane
/*     */   extends AdjustablePanel {
/*     */   private GDItemSimilarTableModel model;
/*     */   private GDItemSimilarTableHeader header;
/*     */   private JTable table;
/*     */   private JScrollPane scrollPane;
/*     */   private DBStashItem.DuplicateInfo info;
/*     */   
/*     */   private class GDItemSimilarTableCellRenderer extends DefaultTableCellRenderer {
/*     */     public GDItemSimilarTableCellRenderer() {
/*  30 */       setHorizontalAlignment(0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
/*  36 */       JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
/*     */ 
/*     */       
/*  39 */       GDItemSimilarTableModel model = (GDItemSimilarTableModel)table.getModel();
/*     */       
/*  41 */       label.setForeground(model.getCellTextColor(row, col));
/*  42 */       label.setBackground(model.getCellBackgroundColor(row, col));
/*     */       
/*  44 */       return label;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GDItemInfoSimilarTablePane() {
/*  55 */     this.info = null;
/*     */     
/*  57 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/*  62 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/*  63 */     Font fntTable = UIManager.getDefaults().getFont("TableHeader.font");
/*  64 */     if (fntTable == null) fntTable = fntLabel;
/*     */     
/*  66 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*  67 */     fntTable = fntTable.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/*  69 */     GDItemSimilarTableHeader.updateToolTips();
/*     */     
/*  71 */     if (this.table == null) {
/*  72 */       this.table = new JTable();
/*     */       
/*  74 */       setInfo(this.info);
/*     */       
/*  76 */       if (this.header == null) {
/*  77 */         this.header = new GDItemSimilarTableHeader(this.table.getColumnModel());
/*  78 */         this.table.setTableHeader((JTableHeader)this.header);
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     this.table.getTableHeader().setFont(fntTable);
/*  83 */     this.table.setFont(fntLabel);
/*     */     
/*  85 */     int size = 12;
/*  86 */     if (GDStashFrame.iniConfig != null) size = GDStashFrame.iniConfig.sectUI.fontSize;
/*     */     
/*  88 */     this.table.setRowHeight((int)(1.5D * size));
/*     */     
/*  90 */     GDItemSimilarTableModel model = (GDItemSimilarTableModel)this.table.getModel();
/*  91 */     model.adjustUI();
/*     */     
/*  93 */     if (this.scrollPane == null) {
/*  94 */       this.scrollPane = new JScrollPane(this.table);
/*  95 */       this.scrollPane.getVerticalScrollBar().setUnitIncrement(2 * GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     } 
/*     */ 
/*     */     
/*  99 */     updateTableColumns();
/*     */ 
/*     */ 
/*     */     
/* 103 */     JTableHeader jth = this.table.getTableHeader();
/* 104 */     int rh = this.table.getRowHeight();
/* 105 */     int hh = (jth.getPreferredSize()).height;
/* 106 */     Dimension dimMax = new Dimension((this.table.getPreferredSize()).width, this.table.getRowHeight() + (jth.getPreferredSize()).height + 6);
/*     */     
/* 108 */     setMaximumSize(dimMax);
/*     */   }
/*     */   
/*     */   private void init() {
/* 112 */     adjustUI();
/*     */     
/* 114 */     GroupLayout layout = null;
/* 115 */     GroupLayout.SequentialGroup hGroup = null;
/* 116 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 118 */     layout = new GroupLayout((Container)this);
/* 119 */     setLayout(layout);
/*     */     
/* 121 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 124 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 127 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 130 */     hGroup
/* 131 */       .addGroup(layout.createParallelGroup()
/* 132 */         .addComponent(this.scrollPane));
/* 133 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 136 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 139 */     vGroup
/* 140 */       .addGroup(layout.createParallelGroup()
/* 141 */         .addComponent(this.scrollPane));
/* 142 */     layout.setVerticalGroup(vGroup);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateTableColumns() {
/* 148 */     this.table.getColumnModel().getColumn(0).setCellRenderer(new GDItemSimilarTableCellRenderer());
/* 149 */     this.table.getColumnModel().getColumn(1).setCellRenderer(new GDItemSimilarTableCellRenderer());
/* 150 */     this.table.getColumnModel().getColumn(2).setCellRenderer(new GDItemSimilarTableCellRenderer());
/* 151 */     this.table.getColumnModel().getColumn(3).setCellRenderer(new GDItemSimilarTableCellRenderer());
/* 152 */     this.table.getColumnModel().getColumn(4).setCellRenderer(new GDItemSimilarTableCellRenderer());
/* 153 */     this.table.getColumnModel().getColumn(5).setCellRenderer(new GDItemSimilarTableCellRenderer());
/*     */   }
/*     */   
/*     */   public void setInfo(DBStashItem.DuplicateInfo info) {
/* 157 */     this.info = info;
/*     */     
/* 159 */     this.model = new GDItemSimilarTableModel(info);
/*     */     
/* 161 */     this.table.setModel((TableModel)this.model);
/*     */     
/* 163 */     updateTableColumns();
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\GDItemInfoSimilarTablePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */