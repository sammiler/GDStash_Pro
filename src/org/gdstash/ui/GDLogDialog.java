/*     */ package org.gdstash.ui;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.util.List;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import org.gdstash.util.GDImagePool;
/*     */ import org.gdstash.util.GDLog;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class GDLogDialog extends JDialog {
/*     */   private JLabel lblMessage;
/*     */   private JButton btnOk;
/*     */   private GDIconCheckBox cbSuccess;
/*     */   private GDIconCheckBox cbInfo;
/*     */   private GDIconCheckBox cbWarning;
/*     */   private GDIconCheckBox cbError;
/*     */   private GDLogTableModel model;
/*     */   private JTable table;
/*     */   
/*     */   private static class GDLogTableModel extends DefaultTableModel {
/*  37 */     private static String[] columnNames = null;
/*     */     
/*     */     private GDLog.LogEntry[] entries;
/*     */     private String[] messages;
/*     */     private Icon[] icons;
/*     */     private int iSuccess;
/*     */     private int iInfo;
/*     */     private int iWarning;
/*     */     private int iError;
/*     */     
/*     */     public GDLogTableModel(GDLog log) {
/*  48 */       List<GDLog.LogEntry> list = log.getLog();
/*     */       
/*  50 */       adjustUI();
/*     */       
/*  52 */       this.entries = new GDLog.LogEntry[list.size()];
/*  53 */       this.iSuccess = 0;
/*  54 */       this.iInfo = 0;
/*  55 */       this.iWarning = 0;
/*  56 */       this.iError = 0;
/*  57 */       int i = 0;
/*     */       
/*  59 */       for (GDLog.LogEntry entry : list) {
/*  60 */         if (entry.type == GDLog.MessageType.Success) this.iSuccess++; 
/*  61 */         if (entry.type == GDLog.MessageType.Info) this.iInfo++; 
/*  62 */         if (entry.type == GDLog.MessageType.Warning) this.iWarning++; 
/*  63 */         if (entry.type == GDLog.MessageType.Error) this.iError++;
/*     */         
/*  65 */         this.entries[i] = entry;
/*     */         
/*  67 */         i++;
/*     */       } 
/*     */       
/*  70 */       filterMessages(true, true, true, true);
/*     */     }
/*     */     
/*     */     public void adjustUI() {
/*  74 */       if (columnNames == null) columnNames = new String[2]; 
/*  75 */       columnNames[0] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_TYPE");
/*  76 */       columnNames[1] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_MESSAGE");
/*     */       
/*  78 */       setColumnIdentifiers((Object[])columnNames);
/*     */     }
/*     */     
/*     */     public int getRowCount() {
/*  82 */       if (this.messages == null) return 0;
/*     */       
/*  84 */       return this.messages.length;
/*     */     }
/*     */     
/*     */     public int getColumnCount() {
/*  88 */       return columnNames.length;
/*     */     }
/*     */     
/*     */     public String getColumnName(int column) {
/*  92 */       return columnNames[column];
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int column) {
/*  96 */       if (this.messages == null) return null;
/*     */       
/*  98 */       if (column == 0) return this.icons[row]; 
/*  99 */       if (column == 1) return this.messages[row];
/*     */       
/* 101 */       return null;
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int column) {
/* 105 */       if (column == 0) return Icon.class; 
/* 106 */       if (column == 1) return String.class;
/*     */       
/* 108 */       return Object.class;
/*     */     }
/*     */     
/*     */     public void filterMessages(boolean success, boolean info, boolean warning, boolean error) {
/* 112 */       int size = 0;
/*     */       
/* 114 */       if (success) size += this.iSuccess; 
/* 115 */       if (info) size += this.iInfo; 
/* 116 */       if (warning) size += this.iWarning; 
/* 117 */       if (error) size += this.iError;
/*     */       
/* 119 */       if (size == 0) {
/* 120 */         this.messages = null;
/* 121 */         this.icons = null;
/*     */         
/* 123 */         fireTableDataChanged();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 128 */       this.messages = new String[size];
/* 129 */       this.icons = new Icon[size];
/*     */       
/* 131 */       int count = 0; int i;
/* 132 */       for (i = 0; i < this.entries.length; i++) {
/* 133 */         boolean match = false;
/* 134 */         Icon icon = null;
/*     */         
/* 136 */         if (success && (this.entries[i]).type == GDLog.MessageType.Success) {
/* 137 */           match = true;
/* 138 */           icon = GDImagePool.iconMsgSuccess16;
/*     */         } 
/*     */         
/* 141 */         if (info && (this.entries[i]).type == GDLog.MessageType.Info) {
/* 142 */           match = true;
/* 143 */           icon = GDImagePool.iconMsgInfo16;
/*     */         } 
/*     */         
/* 146 */         if (warning && (this.entries[i]).type == GDLog.MessageType.Warning) {
/* 147 */           match = true;
/* 148 */           icon = GDImagePool.iconMsgWarning16;
/*     */         } 
/*     */         
/* 151 */         if (error && (this.entries[i]).type == GDLog.MessageType.Error) {
/* 152 */           match = true;
/* 153 */           icon = GDImagePool.iconMsgError16;
/*     */         } 
/*     */         
/* 156 */         if (match) {
/* 157 */           this.messages[count] = (this.entries[i]).str;
/* 158 */           this.icons[count] = icon;
/*     */           
/* 160 */           count++;
/*     */         } 
/*     */       } 
/*     */       
/* 164 */       fireTableDataChanged();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class GDIconCheckBox extends JComponent {
/*     */     private JCheckBox checkbox;
/*     */     private JLabel label;
/*     */     
/*     */     public GDIconCheckBox(Icon icon, boolean selected) {
/* 173 */       this.checkbox = new JCheckBox();
/* 174 */       this.checkbox.setSelected(selected);
/*     */       
/* 176 */       this.label = new JLabel(icon);
/*     */       
/* 178 */       GroupLayout layout = null;
/* 179 */       GroupLayout.SequentialGroup hGroup = null;
/* 180 */       GroupLayout.SequentialGroup vGroup = null;
/*     */       
/* 182 */       layout = new GroupLayout(this);
/* 183 */       setLayout(layout);
/*     */       
/* 185 */       layout.setAutoCreateGaps(true);
/*     */ 
/*     */       
/* 188 */       layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */       
/* 191 */       hGroup = layout.createSequentialGroup();
/*     */ 
/*     */       
/* 194 */       hGroup
/* 195 */         .addGroup(layout.createParallelGroup()
/* 196 */           .addComponent(this.checkbox))
/* 197 */         .addGroup(layout.createParallelGroup()
/* 198 */           .addComponent(this.label));
/* 199 */       layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */       
/* 202 */       vGroup = layout.createSequentialGroup();
/*     */ 
/*     */       
/* 205 */       vGroup
/* 206 */         .addGroup(layout.createParallelGroup()
/* 207 */           .addComponent(this.checkbox)
/* 208 */           .addComponent(this.label));
/* 209 */       layout.setVerticalGroup(vGroup);
/*     */       
/* 211 */       layout.linkSize(1, new Component[] { this.checkbox, this.label });
/*     */     }
/*     */     
/*     */     public void addActionListener(ActionListener listener) {
/* 215 */       this.checkbox.addActionListener(listener);
/*     */     }
/*     */     
/*     */     public void addItemListener(ItemListener listener) {
/* 219 */       this.checkbox.addItemListener(listener);
/*     */     }
/*     */     
/*     */     public void removeActionListener(ActionListener listener) {
/* 223 */       this.checkbox.removeActionListener(listener);
/*     */     }
/*     */     
/*     */     public void removeItemListener(ItemListener listener) {
/* 227 */       this.checkbox.removeItemListener(listener);
/*     */     }
/*     */     
/*     */     public boolean isSeleted() {
/* 231 */       return this.checkbox.isSelected();
/*     */     }
/*     */     
/*     */     public void setSelected(boolean selected) {
/* 235 */       this.checkbox.setSelected(selected);
/*     */     }
/*     */   }
/*     */   
/*     */   private class CheckboxActionListener implements ActionListener { private CheckboxActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/* 242 */       GDLogDialog.this.model.filterMessages(GDLogDialog.this
/* 243 */           .cbSuccess.isSeleted(), GDLogDialog.this
/* 244 */           .cbInfo.isSeleted(), GDLogDialog.this
/* 245 */           .cbWarning.isSeleted(), GDLogDialog.this
/* 246 */           .cbError.isSeleted());
/*     */     } }
/*     */   
/*     */   private class OkActionListener implements ActionListener {
/*     */     private OkActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/* 253 */       GDLogDialog.this.setVisible(false);
/* 254 */       GDLogDialog.this.dispatchEvent(new WindowEvent(GDLogDialog.this, 201));
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
/*     */   public GDLogDialog(Frame owner, String message, GDLog.MessageType type, GDLog log) {
/* 270 */     super(owner, true);
/*     */     
/* 272 */     adjustUI();
/*     */     
/* 274 */     GroupLayout layout = null;
/* 275 */     GroupLayout.SequentialGroup hGroup = null;
/* 276 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 278 */     JPanel panel = buildFilterPanel();
/*     */     
/* 280 */     Icon icon = null;
/*     */     
/* 282 */     if (type == GDLog.MessageType.Success) icon = GDImagePool.iconMsgSuccess32; 
/* 283 */     if (type == GDLog.MessageType.Info) icon = GDImagePool.iconMsgInfo32; 
/* 284 */     if (type == GDLog.MessageType.Warning) icon = GDImagePool.iconMsgWarning32; 
/* 285 */     if (type == GDLog.MessageType.Error) icon = GDImagePool.iconMsgError32;
/*     */     
/* 287 */     this.lblMessage = new JLabel(message, icon, 0);
/* 288 */     this.btnOk.addActionListener(new OkActionListener());
/*     */     
/* 290 */     this.model = new GDLogTableModel(log);
/* 291 */     this.table = new JTable(this.model);
/*     */     
/* 293 */     this.table.setRowHeight(18);
/* 294 */     this.table.getColumnModel().getColumn(0).setPreferredWidth(40);
/* 295 */     this.table.getColumnModel().getColumn(1).setPreferredWidth(400);
/* 296 */     this.table.setAutoResizeMode(3);
/*     */     
/* 298 */     JScrollPane scroll = new JScrollPane(this.table);
/* 299 */     scroll.getVerticalScrollBar().setUnitIncrement(2 * GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 301 */     layout = new GroupLayout(getContentPane());
/* 302 */     getContentPane().setLayout(layout);
/*     */     
/* 304 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 307 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 310 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 313 */     hGroup
/* 314 */       .addGroup(layout.createParallelGroup()
/* 315 */         .addComponent(this.lblMessage)
/* 316 */         .addComponent(this.btnOk)
/* 317 */         .addComponent(panel)
/* 318 */         .addComponent(scroll));
/* 319 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 322 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 325 */     vGroup
/* 326 */       .addGroup(layout.createParallelGroup()
/* 327 */         .addComponent(this.lblMessage))
/* 328 */       .addGroup(layout.createParallelGroup()
/* 329 */         .addComponent(this.btnOk))
/* 330 */       .addGroup(layout.createParallelGroup()
/* 331 */         .addComponent(panel))
/* 332 */       .addGroup(layout.createParallelGroup()
/* 333 */         .addComponent(scroll));
/* 334 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 336 */     layout.linkSize(0, new Component[] { this.lblMessage, this.btnOk });
/* 337 */     layout.linkSize(0, new Component[] { this.lblMessage, panel });
/*     */ 
/*     */     
/* 340 */     layout.linkSize(1, new Component[] { this.lblMessage, this.btnOk });
/*     */ 
/*     */     
/* 343 */     setDefaultCloseOperation(2);
/*     */     
/* 345 */     pack();
/* 346 */     setLocationRelativeTo(owner);
/*     */     
/* 348 */     setVisible(true);
/*     */   }
/*     */   
/*     */   public void adjustUI() {
/* 352 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 353 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/* 354 */     if (fntButton == null) fntButton = fntLabel; 
/* 355 */     Font fntTable = UIManager.getDefaults().getFont("TableHeader.font");
/* 356 */     if (fntTable == null) fntTable = fntLabel;
/*     */     
/* 358 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 359 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 360 */     fntTable = fntTable.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 362 */     if (this.btnOk == null) this.btnOk = new JButton(); 
/* 363 */     this.btnOk.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_OK"));
/* 364 */     this.btnOk.setFont(fntButton);
/*     */     
/* 366 */     if (this.table != null) {
/* 367 */       this.table.getTableHeader().setFont(fntTable);
/* 368 */       this.table.setFont(fntLabel);
/*     */       
/* 370 */       GDLogTableModel model = (GDLogTableModel)this.table.getModel();
/* 371 */       model.adjustUI();
/*     */     } 
/*     */     
/* 374 */     if (this.cbSuccess == null) {
/* 375 */       this.cbSuccess = new GDIconCheckBox(GDImagePool.iconMsgSuccess32, true);
/*     */       
/* 377 */       this.cbSuccess.addActionListener(new CheckboxActionListener());
/*     */     } 
/*     */     
/* 380 */     if (this.cbInfo == null) {
/* 381 */       this.cbInfo = new GDIconCheckBox(GDImagePool.iconMsgInfo32, true);
/*     */       
/* 383 */       this.cbInfo.addActionListener(new CheckboxActionListener());
/*     */     } 
/*     */     
/* 386 */     if (this.cbWarning == null) {
/* 387 */       this.cbWarning = new GDIconCheckBox(GDImagePool.iconMsgWarning32, true);
/*     */       
/* 389 */       this.cbWarning.addActionListener(new CheckboxActionListener());
/*     */     } 
/*     */     
/* 392 */     if (this.cbError == null) {
/* 393 */       this.cbError = new GDIconCheckBox(GDImagePool.iconMsgError32, true);
/*     */       
/* 395 */       this.cbError.addActionListener(new CheckboxActionListener());
/*     */     } 
/*     */   }
/*     */   
/*     */   private JPanel buildFilterPanel() {
/* 400 */     GroupLayout layout = null;
/* 401 */     GroupLayout.SequentialGroup hGroup = null;
/* 402 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 404 */     JPanel panel = new JPanel();
/*     */     
/* 406 */     layout = new GroupLayout(panel);
/* 407 */     panel.setLayout(layout);
/*     */     
/* 409 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 412 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 415 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 418 */     hGroup
/* 419 */       .addGroup(layout.createParallelGroup()
/* 420 */         .addComponent(this.cbSuccess))
/* 421 */       .addGroup(layout.createParallelGroup()
/* 422 */         .addComponent(this.cbInfo))
/* 423 */       .addGroup(layout.createParallelGroup()
/* 424 */         .addComponent(this.cbWarning))
/* 425 */       .addGroup(layout.createParallelGroup()
/* 426 */         .addComponent(this.cbError));
/* 427 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 430 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 433 */     vGroup
/* 434 */       .addGroup(layout.createParallelGroup()
/* 435 */         .addComponent(this.cbSuccess)
/* 436 */         .addComponent(this.cbInfo)
/* 437 */         .addComponent(this.cbWarning)
/* 438 */         .addComponent(this.cbError));
/* 439 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 441 */     layout.linkSize(0, new Component[] { this.cbSuccess, this.cbInfo });
/* 442 */     layout.linkSize(0, new Component[] { this.cbSuccess, this.cbWarning });
/* 443 */     layout.linkSize(0, new Component[] { this.cbSuccess, this.cbError });
/*     */     
/* 445 */     layout.linkSize(1, new Component[] { this.cbSuccess, this.cbInfo });
/* 446 */     layout.linkSize(1, new Component[] { this.cbSuccess, this.cbWarning });
/* 447 */     layout.linkSize(1, new Component[] { this.cbSuccess, this.cbError });
/*     */     
/* 449 */     return panel;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\GDLogDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */