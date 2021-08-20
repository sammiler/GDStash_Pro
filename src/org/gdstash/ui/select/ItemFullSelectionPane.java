/*     */ package org.gdstash.ui.select;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import org.gdstash.db.SelectionCriteria;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.GDTabbedSearchDialog;
/*     */ import org.gdstash.ui.util.AdjustablePanel;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemFullSelectionPane
/*     */   extends AdjustablePanel
/*     */ {
/*     */   private ItemTypePane pnlItemTypes;
/*     */   private SlotSelectionPane pnlSlot;
/*     */   private ItemSetPane pnlItemSet;
/*     */   private ItemNamePane pnlItemName;
/*     */   private ArmorClassPane pnlArmorClasses;
/*     */   private ItemRarityPane pnlItemRarity;
/*     */   private ArtifactRarityPane pnlArtifactRarity;
/*     */   private StatReqPane pnlStats;
/*     */   private SoulboundPane pnlSoulbound;
/*     */   private SelectionCriteria.SoulboundMode soulboundDefault;
/*     */   private boolean soulboundEnabled;
/*     */   private GDTabbedSearchDialog.Mode mode;
/*     */   
/*     */   public ItemFullSelectionPane(GDTabbedSearchDialog.Mode mode) {
/*  42 */     this(mode, SelectionCriteria.SoulboundMode.ALL, true);
/*     */   }
/*     */   
/*     */   public ItemFullSelectionPane(GDTabbedSearchDialog.Mode mode, SelectionCriteria.SoulboundMode soulboundDefault, boolean soulboundEnabled) {
/*  46 */     this.soulboundDefault = soulboundDefault;
/*  47 */     this.soulboundEnabled = soulboundEnabled;
/*  48 */     this.mode = mode;
/*     */     
/*  50 */     adjustUI();
/*     */     
/*  52 */     JPanel pnlLeft = buildLeftPanel();
/*  53 */     JPanel pnlCenter = buildCenterPanel();
/*  54 */     JPanel pnlRight = buildRightPanel();
/*     */     
/*  56 */     GroupLayout layout = null;
/*  57 */     GroupLayout.SequentialGroup hGroup = null;
/*  58 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  60 */     layout = new GroupLayout((Container)this);
/*  61 */     setLayout(layout);
/*     */     
/*  63 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/*  66 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/*  69 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  72 */     hGroup
/*  73 */       .addGroup(layout.createParallelGroup()
/*  74 */         .addComponent(pnlLeft))
/*  75 */       .addGroup(layout.createParallelGroup()
/*  76 */         .addComponent(pnlCenter))
/*  77 */       .addGroup(layout.createParallelGroup()
/*  78 */         .addComponent(pnlRight));
/*  79 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/*  82 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/*  85 */     vGroup
/*  86 */       .addGroup(layout.createParallelGroup()
/*  87 */         .addComponent(pnlLeft)
/*  88 */         .addComponent(pnlCenter)
/*  89 */         .addComponent(pnlRight));
/*  90 */     layout.setVerticalGroup(vGroup);
/*     */     
/*  92 */     layout.linkSize(0, new Component[] { pnlLeft, pnlRight });
/*     */     
/*  94 */     layout.linkSize(1, new Component[] { pnlLeft, pnlCenter });
/*  95 */     layout.linkSize(1, new Component[] { pnlLeft, pnlRight });
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 100 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 101 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 102 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 104 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 105 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 107 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 108 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 109 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 110 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_ITEM_INFO"));
/* 111 */     text.setTitleFont(fntBorder);
/*     */     
/* 113 */     setBorder(text);
/*     */     
/* 115 */     if (this.pnlItemTypes == null) {
/* 116 */       this.pnlItemTypes = new ItemTypePane(this.mode);
/*     */     } else {
/* 118 */       this.pnlItemTypes.adjustUI();
/*     */     } 
/*     */     
/* 121 */     if (this.pnlSlot == null) {
/* 122 */       this.pnlSlot = new SlotSelectionPane();
/*     */     } else {
/* 124 */       this.pnlSlot.adjustUI();
/*     */     } 
/*     */     
/* 127 */     if (this.pnlItemSet == null) {
/* 128 */       this.pnlItemSet = new ItemSetPane();
/*     */     } else {
/* 130 */       this.pnlItemSet.adjustUI();
/*     */     } 
/*     */     
/* 133 */     if (this.pnlItemName == null) {
/* 134 */       this.pnlItemName = new ItemNamePane();
/*     */     } else {
/* 136 */       this.pnlItemName.adjustUI();
/*     */     } 
/*     */     
/* 139 */     if (this.pnlItemRarity == null) {
/* 140 */       this.pnlItemRarity = new ItemRarityPane(0);
/*     */     } else {
/* 142 */       this.pnlItemRarity.adjustUI();
/*     */     } 
/*     */     
/* 145 */     if (this.pnlArtifactRarity == null) {
/* 146 */       this.pnlArtifactRarity = new ArtifactRarityPane(0);
/*     */     } else {
/* 148 */       this.pnlArtifactRarity.adjustUI();
/*     */     } 
/*     */     
/* 151 */     if (this.pnlArmorClasses == null) {
/* 152 */       this.pnlArmorClasses = new ArmorClassPane(0);
/*     */     } else {
/* 154 */       this.pnlArmorClasses.adjustUI();
/*     */     } 
/*     */     
/* 157 */     if (this.pnlStats == null) {
/* 158 */       this.pnlStats = new StatReqPane();
/*     */     } else {
/* 160 */       this.pnlStats.adjustUI();
/*     */     } 
/*     */     
/* 163 */     if (this.pnlSoulbound == null) {
/* 164 */       this.pnlSoulbound = new SoulboundPane(0, this.soulboundDefault, this.soulboundEnabled);
/*     */     } else {
/* 166 */       this.pnlSoulbound.adjustUI();
/*     */     } 
/*     */   }
/*     */   
/*     */   private JPanel buildLeftPanel() {
/* 171 */     JPanel panel = new JPanel();
/*     */     
/* 173 */     GroupLayout layout = null;
/* 174 */     GroupLayout.SequentialGroup hGroup = null;
/* 175 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 177 */     layout = new GroupLayout(panel);
/* 178 */     panel.setLayout(layout);
/*     */     
/* 180 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 183 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 186 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 189 */     hGroup
/* 190 */       .addGroup(layout.createParallelGroup()
/* 191 */         .addComponent((Component)this.pnlItemTypes));
/* 192 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 195 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 198 */     vGroup
/* 199 */       .addGroup(layout.createParallelGroup()
/* 200 */         .addComponent((Component)this.pnlItemTypes));
/* 201 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 203 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildCenterPanel() {
/* 207 */     JPanel panel = new JPanel();
/*     */     
/* 209 */     GroupLayout layout = null;
/* 210 */     GroupLayout.SequentialGroup hGroup = null;
/* 211 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 213 */     layout = new GroupLayout(panel);
/* 214 */     panel.setLayout(layout);
/*     */     
/* 216 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 219 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 222 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 225 */     hGroup
/* 226 */       .addGroup(layout.createParallelGroup()
/* 227 */         .addComponent((Component)this.pnlSlot));
/* 228 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 231 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 234 */     vGroup
/* 235 */       .addGroup(layout.createParallelGroup()
/* 236 */         .addComponent((Component)this.pnlSlot));
/* 237 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 239 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel buildRightPanel() {
/* 243 */     JPanel panel = new JPanel();
/*     */     
/* 245 */     GroupLayout layout = null;
/* 246 */     GroupLayout.SequentialGroup hGroup = null;
/* 247 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/* 249 */     layout = new GroupLayout(panel);
/* 250 */     panel.setLayout(layout);
/*     */     
/* 252 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 255 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 258 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 261 */     hGroup
/* 262 */       .addGroup(layout.createParallelGroup()
/* 263 */         .addComponent((Component)this.pnlItemSet)
/* 264 */         .addComponent((Component)this.pnlItemName)
/* 265 */         .addComponent((Component)this.pnlItemRarity)
/* 266 */         .addComponent((Component)this.pnlArtifactRarity)
/* 267 */         .addComponent((Component)this.pnlArmorClasses)
/* 268 */         .addComponent((Component)this.pnlStats)
/* 269 */         .addComponent((Component)this.pnlSoulbound));
/* 270 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 273 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 276 */     vGroup
/* 277 */       .addGroup(layout.createParallelGroup()
/* 278 */         .addComponent((Component)this.pnlItemSet))
/* 279 */       .addGroup(layout.createParallelGroup()
/* 280 */         .addComponent((Component)this.pnlItemName))
/* 281 */       .addGroup(layout.createParallelGroup()
/* 282 */         .addComponent((Component)this.pnlItemRarity))
/* 283 */       .addGroup(layout.createParallelGroup()
/* 284 */         .addComponent((Component)this.pnlArtifactRarity))
/* 285 */       .addGroup(layout.createParallelGroup()
/* 286 */         .addComponent((Component)this.pnlArmorClasses))
/* 287 */       .addGroup(layout.createParallelGroup()
/* 288 */         .addComponent((Component)this.pnlStats))
/* 289 */       .addGroup(layout.createParallelGroup()
/* 290 */         .addComponent((Component)this.pnlSoulbound));
/* 291 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 293 */     layout.linkSize(0, new Component[] { (Component)this.pnlItemSet, (Component)this.pnlItemName });
/* 294 */     layout.linkSize(0, new Component[] { (Component)this.pnlItemSet, (Component)this.pnlItemRarity });
/* 295 */     layout.linkSize(0, new Component[] { (Component)this.pnlItemSet, (Component)this.pnlArtifactRarity });
/* 296 */     layout.linkSize(0, new Component[] { (Component)this.pnlItemSet, (Component)this.pnlArmorClasses });
/* 297 */     layout.linkSize(0, new Component[] { (Component)this.pnlItemSet, (Component)this.pnlStats });
/* 298 */     layout.linkSize(0, new Component[] { (Component)this.pnlItemSet, (Component)this.pnlSoulbound });
/*     */     
/* 300 */     layout.linkSize(1, new Component[] { (Component)this.pnlItemSet, (Component)this.pnlItemName });
/* 301 */     layout.linkSize(1, new Component[] { (Component)this.pnlItemSet, (Component)this.pnlItemRarity });
/* 302 */     layout.linkSize(1, new Component[] { (Component)this.pnlItemSet, (Component)this.pnlArtifactRarity });
/* 303 */     layout.linkSize(1, new Component[] { (Component)this.pnlItemSet, (Component)this.pnlArmorClasses });
/*     */     
/* 305 */     return panel;
/*     */   }
/*     */   
/*     */   public void updateConfig() {
/* 309 */     if (this.pnlItemSet != null) this.pnlItemSet.updateItemSets(); 
/*     */   }
/*     */   
/*     */   public void clear() {
/* 313 */     this.pnlItemTypes.clear();
/* 314 */     this.pnlSlot.clear();
/* 315 */     this.pnlItemSet.clear();
/* 316 */     this.pnlItemName.clear();
/* 317 */     this.pnlItemRarity.clear();
/* 318 */     this.pnlArtifactRarity.clear();
/* 319 */     this.pnlArmorClasses.clear();
/* 320 */     this.pnlStats.clear();
/* 321 */     this.pnlSoulbound.clear();
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 325 */     this.pnlItemTypes.addCriteria(criteria);
/* 326 */     this.pnlSlot.addCriteria(criteria);
/* 327 */     this.pnlItemSet.addCriteria(criteria);
/* 328 */     this.pnlItemName.addCriteria(criteria);
/* 329 */     this.pnlItemRarity.addCriteria(criteria);
/* 330 */     this.pnlArtifactRarity.addCriteria(criteria);
/* 331 */     this.pnlArmorClasses.addCriteria(criteria);
/* 332 */     this.pnlStats.addCriteria(criteria);
/* 333 */     this.pnlSoulbound.addCriteria(criteria);
/*     */     
/* 335 */     criteria.selMode = SelectionCriteria.SelectionMode.ITEM;
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\select\ItemFullSelectionPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */