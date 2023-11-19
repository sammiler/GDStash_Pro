/*     */ package org.gdstash.ui;
/*     */ 
/*     */ import java.awt.*;
/*     */
/*     */
/*     */
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.*;
/*     */
/*     */
/*     */
/*     */
/*     */ import org.gdstash.db.SelectionCriteria;
/*     */ import org.gdstash.ui.select.*;
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ import org.gdstash.util.GDImagePool;
/*     */ import org.gdstash.util.GDLog;
/*     */ import org.gdstash.util.GDMsgFormatter;
import org.gdstash.util.GDMsgLogger;

/*     */
/*     */ public class GDTabbedSearchDialog extends JDialog {
/*     */   private Component owner;
/*     */   private JButton btnSearch;
/*     */   private JButton btnClear;
/*     */   private SelectAndOrPane pnlAndOr;
/*     */   private GDUISearch uiSearch;
/*     */   private JTabbedPane pnlTabbed;
/*     */   private ItemFullSelectionPane pnlItem;
/*     */   private DamageSelectionPane pnlDamage;
/*     */   private AttribSelectionPane pnlAttrib;
/*     */   private SkillBonusPane pnlBonusSkill;
/*     */   private SkillModifierPane pnlModifiedSkill;
/*     */   private ItemSkillPane pnlItemSkill;
/*     */   private AffixPane pnlAffix;
/*     */   private SelectionCriteria.SoulboundMode soulboundDefault;
/*     */   private boolean soulboundEnabled;
/*     */   private Mode mode;
/*     */   
/*  43 */   public enum Mode { CRAFT, SEARCH, TRANSFER; }
/*     */   
/*     */   private class CloseActionListener implements ActionListener { private CloseActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  48 */       GDTabbedSearchDialog.this.setVisible(false);
/*     */     } }
/*     */   
/*     */   private class SearchActionListener implements ActionListener {
/*     */     private SearchActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  55 */       GDLog log = new GDLog();
/*     */       
/*  57 */       SelectionCriteria criteria = new SelectionCriteria();
/*  58 */       GDTabbedSearchDialog.this.addCriteria(criteria);
/*     */       
/*  60 */       criteria.checkCriteria(log);
/*  61 */       if (log.containsErrors()) {
/*  62 */         GDMsgLogger.addLog(log);
/*     */         
/*  64 */         GDMsgLogger.showLog(GDTabbedSearchDialog.this, GDMsgFormatter.getString(GDMsgFormatter.rbMsg, "ERR_SEARCH"));
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  69 */       GDTabbedSearchDialog.this.setCursor(Cursor.getPredefinedCursor(3));
/*     */       
/*  71 */       if (GDTabbedSearchDialog.this.uiSearch != null) {
/*  72 */         GDTabbedSearchDialog.this.uiSearch.search(criteria);
/*     */       }
/*  74 */       GDTabbedSearchDialog.this.setVisible(false);
/*     */       
/*  76 */       GDTabbedSearchDialog.this.setCursor(Cursor.getDefaultCursor());
/*     */     }
/*     */   }
/*     */   
/*     */   private class ClearActionListener
/*     */     implements ActionListener
/*     */   {
/*     */     private ClearActionListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  86 */       GDTabbedSearchDialog.this.clear();
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
/*     */   public GDTabbedSearchDialog(GDUISearch uiSearch, Mode mode) {
/* 110 */     this((Frame)null, uiSearch, mode, true, SelectionCriteria.SoulboundMode.ALL, true);
/*     */   }
/*     */   
/*     */   public GDTabbedSearchDialog(Frame owner, GDUISearch uiSearch, Mode mode) {
/* 114 */     this(owner, uiSearch, mode, true, SelectionCriteria.SoulboundMode.ALL, true);
/*     */   }
/*     */   
/*     */   public GDTabbedSearchDialog(Frame owner, GDUISearch uiSearch, Mode mode, SelectionCriteria.SoulboundMode soulboundDefault) {
/* 118 */     this(owner, uiSearch, mode, true, soulboundDefault, true);
/*     */   }
/*     */   
/*     */   public GDTabbedSearchDialog(Frame owner, GDUISearch uiSearch, Mode mode, SelectionCriteria.SoulboundMode soulboundDefault, boolean soulboundEnabled) {
/* 122 */     this(owner, uiSearch, mode, true, soulboundDefault, soulboundEnabled);
/*     */   }
/*     */   
/*     */   public GDTabbedSearchDialog(Frame owner, GDUISearch uiSearch, Mode mode, boolean modal) {
/* 126 */     this(owner, uiSearch, mode, modal, SelectionCriteria.SoulboundMode.ALL, true);
/*     */   }
/*     */   
/*     */   public GDTabbedSearchDialog(Frame owner, GDUISearch uiSearch, Mode mode, boolean modal, SelectionCriteria.SoulboundMode soulboundDefault) {
/* 130 */     this(owner, uiSearch, mode, modal, soulboundDefault, true);
/*     */   }
/*     */   
/*     */   public GDTabbedSearchDialog(Frame owner, GDUISearch uiSearch, Mode mode, boolean modal, SelectionCriteria.SoulboundMode soulboundDefault, boolean soulboundEnabled) {
/* 134 */     super(owner, modal);
/*     */     
/* 136 */     this.owner = owner;
/* 137 */     this.uiSearch = uiSearch;
/* 138 */     this.soulboundDefault = soulboundDefault;
/* 139 */     this.soulboundEnabled = soulboundEnabled;
/* 140 */     this.mode = mode;
/*     */     
/* 142 */     adjustUI();
/*     */     
/* 144 */     JPanel pnlMain = buildMainPanel();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     getContentPane().add(pnlMain);
/*     */     
/* 152 */     pack();
/*     */     
/* 154 */     setLocationRelativeTo(owner);
/*     */     
/* 156 */     getRootPane().registerKeyboardAction(new CloseActionListener(), KeyStroke.getKeyStroke(27, 0), 2);
/*     */ 
/*     */     
/* 159 */     getRootPane().setDefaultButton(this.btnSearch);
/* 160 */     this.btnSearch.requestFocus();
/*     */   }
/*     */   
/*     */   public void adjustUI() {
/* 164 */     setTitle(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_SEARCH"));
/*     */     
/* 166 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 167 */     Font fntButton = UIManager.getDefaults().getFont("Button.font");
/* 168 */     if (fntButton == null) fntButton = fntLabel;
/*     */     
/* 170 */     fntButton = fntButton.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 172 */     if (this.btnSearch == null) {
/* 173 */       this.btnSearch = new JButton();
/*     */       
/* 175 */       this.btnSearch.addActionListener(new SearchActionListener());
/*     */     } 
/* 177 */     this.btnSearch.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_SEARCH"));
/* 178 */     this.btnSearch.setIcon(GDImagePool.iconBtnSearch24);
/* 179 */     this.btnSearch.setFont(fntButton);
/* 180 */     GDStashFrame.setMnemonic(this.btnSearch, "MNC_SEARCH");
/*     */     
/* 182 */     if (this.btnClear == null) {
/* 183 */       this.btnClear = new JButton();
/*     */       
/* 185 */       this.btnClear.addActionListener(new ClearActionListener());
/*     */     } 
/* 187 */     this.btnClear.setText(GDMsgFormatter.getString(GDMsgFormatter.rbUI, "BTN_CLEAR"));
/* 188 */     this.btnClear.setIcon(GDImagePool.iconItemDelete24);
/* 189 */     this.btnClear.setFont(fntButton);
/* 190 */     GDStashFrame.setMnemonic(this.btnClear, "MNC_CLEAR");
/*     */     
/* 192 */     if (this.pnlAndOr == null) {
/* 193 */       this.pnlAndOr = new SelectAndOrPane();
/*     */     } else {
/* 195 */       this.pnlAndOr.adjustUI();
/*     */     } 
/*     */     
/* 198 */     if (this.pnlItem == null) {
/* 199 */       this.pnlItem = new ItemFullSelectionPane(this.mode, this.soulboundDefault, this.soulboundEnabled);
/*     */     } else {
/* 201 */       this.pnlItem.adjustUI();
/*     */     } 
/*     */     
/* 204 */     if (this.pnlDamage == null) {
/* 205 */       this.pnlDamage = new DamageSelectionPane();
/*     */     } else {
/* 207 */       this.pnlDamage.adjustUI();
/*     */     } 
/*     */     
/* 210 */     if (this.pnlAttrib == null) {
/* 211 */       this.pnlAttrib = new AttribSelectionPane();
/*     */     } else {
/* 213 */       this.pnlAttrib.adjustUI();
/*     */     } 
/*     */     
/* 216 */     if (this.pnlBonusSkill == null) {
/* 217 */       this.pnlBonusSkill = new SkillBonusPane();
/*     */     } else {
/* 219 */       this.pnlBonusSkill.adjustUI();
/*     */     } 
/*     */     
/* 222 */     if (this.pnlModifiedSkill == null) {
/* 223 */       this.pnlModifiedSkill = new SkillModifierPane();
/*     */     } else {
/* 225 */       this.pnlModifiedSkill.adjustUI();
/*     */     } 
/*     */     
/* 228 */     if (this.pnlItemSkill == null) {
/* 229 */       this.pnlItemSkill = new ItemSkillPane();
/*     */     } else {
/* 231 */       this.pnlItemSkill.adjustUI();
/*     */     } 
/*     */     
/* 234 */     if (this.pnlAffix == null) {
/* 235 */       this.pnlAffix = new AffixPane();
/*     */     } else {
/* 237 */       this.pnlAffix.adjustUI();
/*     */     } 
/*     */     
/* 240 */     if (this.pnlTabbed == null) {
/* 241 */       this.pnlTabbed = new JTabbedPane();
/*     */       
/* 243 */       JPanel panel = buildAttribPanel();
/*     */       
/* 245 */       this.pnlTabbed.add((Component)this.pnlItem);
/* 246 */       this.pnlTabbed.add((Component)this.pnlDamage);
/* 247 */       this.pnlTabbed.add(panel);
/*     */     } 
/* 249 */     this.pnlTabbed.setTitleAt(0, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_SRCH_ITEM_TYPE"));
/* 250 */     this.pnlTabbed.setTitleAt(1, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_SRCH_ITEM_DAMAGE"));
/* 251 */     this.pnlTabbed.setTitleAt(2, GDMsgFormatter.getString(GDMsgFormatter.rbUI, "HEADER_SRCH_ITEM_ATTRIB"));
/*     */     
/* 253 */     pack();
/*     */   }
/*     */   
/*     */   private JPanel buildMainPanel() {
/* 257 */     JPanel panel = new JPanel();
/*     */     
/* 259 */     GroupLayout layout = null;
/* 260 */     GroupLayout.SequentialGroup hGroup = null;
/* 261 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 263 */     JPanel pnlButton = buildButtonPanel();
/*     */ 
/*     */ 
/*     */     
/* 267 */     layout = new GroupLayout(panel);
/* 268 */     panel.setLayout(layout);
/*     */     
/* 270 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 273 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 276 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 279 */     hGroup
/* 280 */       .addGroup(layout.createParallelGroup()
/* 281 */         .addComponent(pnlButton)
/* 282 */         .addComponent(this.pnlTabbed));
/* 283 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 286 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 289 */     vGroup
/* 290 */       .addGroup(layout.createParallelGroup()
/* 291 */         .addComponent(pnlButton))
/* 292 */       .addGroup(layout.createParallelGroup()
/* 293 */         .addComponent(this.pnlTabbed));
/* 294 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 296 */     layout.linkSize(0, new Component[] { pnlButton, this.pnlTabbed });
/*     */     
/* 298 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildAttribPanel() {
/* 302 */     JPanel panel = new JPanel();
/*     */     
/* 304 */     GroupLayout layout = null;
/* 305 */     GroupLayout.SequentialGroup hGroup = null;
/* 306 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 308 */     JPanel pnlButton = buildButtonPanel();
/*     */ 
/*     */ 
/*     */     
/* 312 */     layout = new GroupLayout(panel);
/* 313 */     panel.setLayout(layout);
/*     */     
/* 315 */     layout.setAutoCreateGaps(true);
/*     */ 
/*     */     
/* 318 */     layout.setAutoCreateContainerGaps(true);
/*     */ 
/*     */     
/* 321 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 324 */     hGroup
/* 325 */       .addGroup(layout.createParallelGroup()
/* 326 */         .addComponent((Component)this.pnlAttrib)
/* 327 */         .addComponent((Component)this.pnlBonusSkill)
/* 328 */         .addComponent((Component)this.pnlModifiedSkill)
/* 329 */         .addComponent((Component)this.pnlItemSkill)
/* 330 */         .addComponent((Component)this.pnlAffix));
/* 331 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 334 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 337 */     vGroup
/* 338 */       .addGroup(layout.createParallelGroup()
/* 339 */         .addComponent((Component)this.pnlAttrib))
/* 340 */       .addGroup(layout.createParallelGroup()
/* 341 */         .addComponent((Component)this.pnlBonusSkill))
/* 342 */       .addGroup(layout.createParallelGroup()
/* 343 */         .addComponent((Component)this.pnlModifiedSkill))
/* 344 */       .addGroup(layout.createParallelGroup()
/* 345 */         .addComponent((Component)this.pnlItemSkill))
/* 346 */       .addGroup(layout.createParallelGroup()
/* 347 */         .addComponent((Component)this.pnlAffix));
/* 348 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 350 */     layout.linkSize(0, new Component[] { (Component)this.pnlAttrib, (Component)this.pnlBonusSkill });
/* 351 */     layout.linkSize(0, new Component[] { (Component)this.pnlAttrib, (Component)this.pnlModifiedSkill });
/* 352 */     layout.linkSize(0, new Component[] { (Component)this.pnlAttrib, (Component)this.pnlItemSkill });
/* 353 */     layout.linkSize(0, new Component[] { (Component)this.pnlAttrib, (Component)this.pnlAffix });
/*     */     
/* 355 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildButtonPanel() {
/* 359 */     BorderLayout layout = new BorderLayout();
/*     */     
/* 361 */     JPanel panel = new JPanel();
/*     */     
/* 363 */     panel.setLayout(layout);
/*     */     
/* 365 */     panel.add(this.btnSearch, "Center");
/* 366 */     panel.add(this.btnClear, "West");
/* 367 */     panel.add((Component)this.pnlAndOr, "East");
/*     */     
/* 369 */     return panel;
/*     */   }
/*     */   
/*     */   public void updateConfig() {
/* 373 */     if (this.pnlItem != null) this.pnlItem.updateConfig(); 
/* 374 */     if (this.pnlBonusSkill != null) this.pnlBonusSkill.updateBonusSkills(); 
/* 375 */     if (this.pnlModifiedSkill != null) this.pnlModifiedSkill.updateModifiedSkills(); 
/* 376 */     if (this.pnlItemSkill != null) this.pnlItemSkill.updateItemSkills(); 
/* 377 */     if (this.pnlAffix != null) this.pnlAffix.updateAffixes(); 
/*     */   }
/*     */   
/*     */   public void clear() {
/* 381 */     this.pnlAndOr.clear();
/* 382 */     this.pnlItem.clear();
/* 383 */     this.pnlDamage.clear();
/* 384 */     this.pnlAttrib.clear();
/* 385 */     this.pnlBonusSkill.clear();
/* 386 */     this.pnlModifiedSkill.clear();
/* 387 */     this.pnlItemSkill.clear();
/* 388 */     this.pnlAffix.clear();
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 392 */     criteria.selMode = SelectionCriteria.SelectionMode.ITEM;
/*     */     
/* 394 */     this.pnlAndOr.addCriteria(criteria);
/* 395 */     this.pnlItem.addCriteria(criteria);
/* 396 */     this.pnlDamage.addCriteria(criteria);
/* 397 */     this.pnlAttrib.addCriteria(criteria);
/* 398 */     this.pnlBonusSkill.addCriteria(criteria);
/* 399 */     this.pnlModifiedSkill.addCriteria(criteria);
/* 400 */     this.pnlItemSkill.addCriteria(criteria);
/* 401 */     this.pnlAffix.addCriteria(criteria);
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\GDTabbedSearchDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */