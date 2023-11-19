/*     */ package org.gdstash.ui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.UIManager;
/*     */ import org.gdstash.db.SelectionCriteria;
/*     */ import org.gdstash.ui.select.AffixSelectionPane;
/*     */ import org.gdstash.ui.select.SelectAndOrPane;
/*     */ import org.gdstash.util.GDImagePool;
/*     */ import org.gdstash.util.GDLog;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ import org.gdstash.util.GDMsgLogger;
/*     */ 
/*     */ public class GDFilterDialog
/*     */   extends JDialog {
/*     */   private Component owner;
/*     */   private JButton btnSearch;
/*     */   private JButton btnClear;
/*     */   private SelectAndOrPane pnlAndOr;
/*     */   private AffixSelectionPane pnlSelect;
/*     */   private GDUIFilter uiFilter;
/*     */   private JPanel pnlMain;
/*     */   
/*     */   private class CloseActionListener
/*     */     implements ActionListener {
/*     */     private CloseActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  39 */       GDFilterDialog.this.setVisible(false);
/*     */     } }
/*     */   
/*     */   private class FilterActionListener implements ActionListener {
/*     */     private FilterActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  46 */       GDLog log = new GDLog();
/*     */       
/*  48 */       SelectionCriteria criteria = new SelectionCriteria();
/*  49 */       GDFilterDialog.this.pnlAndOr.addCriteria(criteria);
/*  50 */       GDFilterDialog.this.pnlSelect.addCriteria(criteria);
/*     */       
/*  52 */       criteria.checkCriteria(log);
/*  53 */       if (log.containsErrors()) {
/*  54 */         GDMsgLogger.addLog(log);
/*     */         
/*  56 */         GDMsgLogger.showLog(GDFilterDialog.this, GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_FILTER"));
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  61 */       GDFilterDialog.this.setCursor(Cursor.getPredefinedCursor(3));
/*     */       
/*  63 */       if (GDFilterDialog.this.uiFilter != null) {
/*  64 */         GDFilterDialog.this.uiFilter.filter(criteria);
/*     */       }
/*  66 */       GDFilterDialog.this.setVisible(false);
/*     */       
/*  68 */       GDFilterDialog.this.setCursor(Cursor.getDefaultCursor());
/*     */     }
/*     */   }
/*     */   
/*     */   private class ClearActionListener
/*     */     implements ActionListener
/*     */   {
/*     */     private ClearActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  78 */       GDFilterDialog.this.pnlAndOr.clear();
/*  79 */       GDFilterDialog.this.pnlSelect.clear();
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
/*     */   public GDFilterDialog(GDUIFilter uiFilter) {
/*  93 */     this((Frame)null, uiFilter, true);
/*     */   }
/*     */   
/*     */   public GDFilterDialog(Frame owner, GDUIFilter uiFilter) {
/*  97 */     this(owner, uiFilter, true);
/*     */   }
/*     */   
/*     */   public GDFilterDialog(Frame owner, GDUIFilter uiFilter, boolean modal) {
/* 101 */     super(owner, modal);
/*     */     
/* 103 */     this.owner = owner;
/* 104 */     this.uiFilter = uiFilter;
/*     */     
/* 106 */     adjustUI();
/*     */     
/* 108 */     this.pnlMain = buildMainPanel();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     getContentPane().add(this.pnlMain);
/*     */     
/* 115 */     pack();
/*     */     
/* 117 */     setLocationRelativeTo(owner);
/*     */     
/* 119 */     getRootPane().registerKeyboardAction(new CloseActionListener(), KeyStroke.getKeyStroke(27, 0), 2);
/*     */ 
/*     */     
/* 122 */     getRootPane().setDefaultButton(this.btnSearch);
/* 123 */     this.btnSearch.requestFocus();
/*     */   }
/*     */   
/*     */   public void adjustUI() {
/* 127 */     setTitle(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_FILTER"));
/*     */     
/* 129 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 130 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/* 131 */     if (fntButton == null) fntButton = fntLabel;
/*     */     
/* 133 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 135 */     if (this.btnSearch == null) {
/* 136 */       this.btnSearch = new JButton();
/*     */       
/* 138 */       this.btnSearch.addActionListener(new FilterActionListener());
/*     */     } 
/* 140 */     this.btnSearch.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_FILTER"));
/* 141 */     this.btnSearch.setIcon(GDImagePool.iconBtnFilter24);
/* 142 */     this.btnSearch.setFont(fntButton);
/* 143 */     GDStashFrame.setMnemonic(this.btnSearch, "MNC_FILTER");
/*     */     
/* 145 */     if (this.btnClear == null) {
/* 146 */       this.btnClear = new JButton();
/*     */       
/* 148 */       this.btnClear.addActionListener(new ClearActionListener());
/*     */     } 
/* 150 */     this.btnClear.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_CLEAR"));
/* 151 */     this.btnClear.setIcon(GDImagePool.iconItemDelete24);
/* 152 */     this.btnClear.setFont(fntButton);
/* 153 */     GDStashFrame.setMnemonic(this.btnClear, "MNC_CLEAR");
/*     */     
/* 155 */     if (this.pnlAndOr == null) {
/* 156 */       this.pnlAndOr = new SelectAndOrPane();
/*     */     } else {
/* 158 */       this.pnlAndOr.adjustUI();
/*     */     } 
/*     */     
/* 161 */     if (this.pnlSelect == null) {
/* 162 */       this.pnlSelect = new AffixSelectionPane();
/*     */     } else {
/* 164 */       this.pnlSelect.adjustUI();
/*     */     } 
/*     */     
/* 167 */     pack();
/*     */   }
/*     */   
/*     */   private JPanel buildMainPanel() {
/* 171 */     JPanel panel = new JPanel();
/*     */     
/* 173 */     GroupLayout layout = null;
/* 174 */     GroupLayout.SequentialGroup hGroup = null;
/* 175 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 177 */     JPanel pnlButton = buildButtonPanel();
/*     */ 
/*     */ 
/*     */     
/* 181 */     layout = new GroupLayout(panel);
/* 182 */     panel.setLayout(layout);
/*     */     
/* 184 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 187 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 190 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 193 */     hGroup
/* 194 */       .addGroup(layout.createParallelGroup()
/* 195 */         .addComponent(pnlButton)
/* 196 */         .addComponent((Component)this.pnlSelect));
/* 197 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 200 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 203 */     vGroup
/* 204 */       .addGroup(layout.createParallelGroup()
/* 205 */         .addComponent(pnlButton))
/* 206 */       .addGroup(layout.createParallelGroup()
/* 207 */         .addComponent((Component)this.pnlSelect));
/* 208 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 210 */     layout.linkSize(0, new Component[] { pnlButton, (Component)this.pnlSelect });
/*     */     
/* 212 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildButtonPanel() {
/* 216 */     BorderLayout layout = new BorderLayout();
/*     */     
/* 218 */     JPanel panel = new JPanel();
/*     */     
/* 220 */     panel.setLayout(layout);
/*     */     
/* 222 */     panel.add(this.btnSearch, "Center");
/* 223 */     panel.add(this.btnClear, "West");
/* 224 */     panel.add((Component)this.pnlAndOr, "East");
/*     */     
/* 226 */     return panel;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\GDFilterDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */