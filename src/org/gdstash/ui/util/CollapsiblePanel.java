/*     */ package org.gdstash.ui.util;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.GDTabbedSearchDialog;
/*     */ import org.gdstash.ui.select.ItemFullSelectionPane;
/*     */ import org.gdstash.util.GDImagePool;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class CollapsiblePanel
/*     */   extends AdjustablePanel {
/*     */   private JButton btnCollapse;
/*     */   private boolean collapsed;
/*     */   private String tagTitle;
/*     */   private AdjustablePanel panel;
/*     */   private AdjustableTabbedPane tabbedPane;
/*     */   private int direction;
/*     */   
/*     */   private class CollapsibleListener implements ActionListener {
/*     */     private CollapsibleListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  33 */       CollapsiblePanel.this.collapsed = !CollapsiblePanel.this.collapsed;
/*     */       
/*  35 */       if (CollapsiblePanel.this.collapsed) {
/*  36 */         if (CollapsiblePanel.this.panel != null) CollapsiblePanel.this.panel.setVisible(false); 
/*  37 */         if (CollapsiblePanel.this.tabbedPane != null) CollapsiblePanel.this.tabbedPane.setVisible(false);
/*     */         
/*  39 */         CollapsiblePanel.this.btnCollapse.setIcon(GDImagePool.iconWindowExpand24);
/*     */       } else {
/*  41 */         if (CollapsiblePanel.this.panel != null) CollapsiblePanel.this.panel.setVisible(true); 
/*  42 */         if (CollapsiblePanel.this.tabbedPane != null) CollapsiblePanel.this.tabbedPane.setVisible(true);
/*     */         
/*  44 */         CollapsiblePanel.this.btnCollapse.setIcon(GDImagePool.iconWindowCollapse24);
/*     */       } 
/*     */       
/*  47 */       CollapsiblePanel.this.validate();
/*  48 */       CollapsiblePanel.this.repaint();
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
/*     */   public CollapsiblePanel(AdjustablePanel panel, String tagTitle) {
/*  60 */     this(panel, tagTitle, 0, false);
/*     */   }
/*     */   
/*     */   public CollapsiblePanel(AdjustablePanel panel, String tagTitle, int direction) {
/*  64 */     this(panel, tagTitle, direction, false);
/*     */   }
/*     */   
/*     */   public CollapsiblePanel(AdjustablePanel panel, String tagTitle, boolean collapsed) {
/*  68 */     this(panel, tagTitle, 0, collapsed);
/*     */   }
/*     */   
/*     */   public CollapsiblePanel(AdjustablePanel panel, String tagTitle, int direction, boolean collapsed) {
/*  72 */     this.panel = panel;
/*  73 */     this.tabbedPane = null;
/*  74 */     this.tagTitle = tagTitle;
/*  75 */     this.collapsed = collapsed;
/*  76 */     this.direction = direction;
/*     */     
/*  78 */     adjustUI();
/*     */     
/*  80 */     BorderLayout layout = new BorderLayout();
/*     */     
/*  82 */     setLayout(layout);
/*     */     
/*  84 */     if (direction == 0) {
/*  85 */       add(this.btnCollapse, "North");
/*     */     } else {
/*  87 */       add(this.btnCollapse, "West");
/*     */     } 
/*  89 */     add(panel, "Center");
/*     */     
/*  91 */     if (collapsed) {
/*  92 */       panel.setVisible(false);
/*     */     } else {
/*  94 */       panel.setVisible(true);
/*     */     } 
/*     */     
/*  97 */     Border lowered = BorderFactory.createEtchedBorder(1);
/*  98 */     Border raised = BorderFactory.createEtchedBorder(0);
/*  99 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */     
/* 101 */     setBorder(compound);
/*     */   }
/*     */   
/*     */   public CollapsiblePanel(AdjustableTabbedPane tabbedPane, String tagTitle) {
/* 105 */     this(tabbedPane, tagTitle, 0, false);
/*     */   }
/*     */   
/*     */   public CollapsiblePanel(AdjustableTabbedPane tabbedPane, String tagTitle, int direction) {
/* 109 */     this(tabbedPane, tagTitle, direction, false);
/*     */   }
/*     */   
/*     */   public CollapsiblePanel(AdjustableTabbedPane tabbedPane, String tagTitle, boolean collapsed) {
/* 113 */     this(tabbedPane, tagTitle, 0, collapsed);
/*     */   }
/*     */   
/*     */   public CollapsiblePanel(AdjustableTabbedPane tabbedPane, String tagTitle, int direction, boolean collapsed) {
/* 117 */     this.panel = null;
/* 118 */     this.tabbedPane = tabbedPane;
/* 119 */     this.tagTitle = tagTitle;
/* 120 */     this.collapsed = collapsed;
/* 121 */     this.direction = direction;
/*     */     
/* 123 */     adjustUI();
/*     */     
/* 125 */     BorderLayout layout = new BorderLayout();
/*     */     
/* 127 */     setLayout(layout);
/*     */     
/* 129 */     if (direction == 0) {
/* 130 */       add(this.btnCollapse, "North");
/*     */     } else {
/* 132 */       add(this.btnCollapse, "West");
/*     */     } 
/* 134 */     add(tabbedPane, "Center");
/*     */     
/* 136 */     if (collapsed) {
/* 137 */       tabbedPane.setVisible(false);
/*     */     } else {
/* 139 */       tabbedPane.setVisible(true);
/*     */     } 
/*     */     
/* 142 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 143 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 144 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/*     */     
/* 146 */     setBorder(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 151 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 152 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/* 153 */     if (fntButton == null) fntButton = fntLabel;
/*     */     
/* 155 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 156 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 158 */     if (this.btnCollapse == null) {
/* 159 */       this.btnCollapse = new JButton();
/*     */       
/* 161 */       this.btnCollapse.addActionListener(new CollapsibleListener());
/*     */     } 
/* 163 */     this.btnCollapse.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, this.tagTitle));
/* 164 */     if (this.collapsed) {
/* 165 */       this.btnCollapse.setIcon(GDImagePool.iconWindowExpand24);
/*     */     } else {
/* 167 */       this.btnCollapse.setIcon(GDImagePool.iconWindowCollapse24);
/*     */     } 
/* 169 */     this.btnCollapse.setFont(fntButton);
/*     */     
/* 171 */     if (this.direction == 1) {
/* 172 */       this.btnCollapse.setUI(new VerticalButtonUI(270));
/*     */     }
/*     */     
/* 175 */     if (this.panel != null) this.panel.adjustUI(); 
/* 176 */     if (this.tabbedPane != null) this.tabbedPane.adjustUI(); 
/*     */   }
/*     */   
/*     */   public static void test() {
/* 180 */     ItemFullSelectionPane pnlSel1 = new ItemFullSelectionPane(GDTabbedSearchDialog.Mode.SEARCH);
/* 181 */     ItemFullSelectionPane pnlSel2 = new ItemFullSelectionPane(GDTabbedSearchDialog.Mode.SEARCH);
/*     */     
/* 183 */     CollapsiblePanel pnl1 = new CollapsiblePanel((AdjustablePanel)pnlSel1, "TXT_LANG_COUNTRY", 0);
/* 184 */     CollapsiblePanel pnl2 = new CollapsiblePanel((AdjustablePanel)pnlSel2, "TXT_LANG_COUNTRY", 0, true);
/*     */     
/* 186 */     JPanel panel = new JPanel();
/*     */     
/* 188 */     GroupLayout layout = null;
/* 189 */     GroupLayout.SequentialGroup hGroup = null;
/* 190 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 192 */     layout = new GroupLayout(panel);
/* 193 */     panel.setLayout(layout);
/*     */     
/* 195 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 198 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 201 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 204 */     hGroup
/* 205 */       .addGroup(layout.createParallelGroup()
/* 206 */         .addComponent(pnl1))
/* 207 */       .addGroup(layout.createParallelGroup()
/* 208 */         .addComponent(pnl2));
/* 209 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 212 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 215 */     vGroup
/* 216 */       .addGroup(layout.createParallelGroup()
/* 217 */         .addComponent(pnl1)
/*     */         
/* 219 */         .addComponent(pnl2));
/* 220 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 222 */     JFrame frame = new JFrame();
/* 223 */     frame.add(panel);
/*     */     
/* 225 */     frame.setLocation(GDStashFrame.iniConfig.sectWindow.x, GDStashFrame.iniConfig.sectWindow.y);
/* 226 */     if (GDStashFrame.iniConfig.sectWindow.maximized) {
/* 227 */       frame.setExtendedState(frame.getExtendedState() | 0x6);
/*     */     } else {
/* 229 */       frame.setSize(GDStashFrame.iniConfig.sectWindow.w, GDStashFrame.iniConfig.sectWindow.h);
/*     */     } 
/* 231 */     frame.setDefaultCloseOperation(3);
/*     */     
/* 233 */     frame.setVisible(true);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\u\\util\CollapsiblePanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */