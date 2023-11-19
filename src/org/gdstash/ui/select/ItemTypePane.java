/*     */ package org.gdstash.ui.select;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import org.gdstash.db.SelectionCriteria;
/*     */ import org.gdstash.ui.GDStashFrame;
/*     */ import org.gdstash.ui.GDTabbedSearchDialog;
/*     */ import org.gdstash.util.GDMsgFormatter;
/*     */ 
/*     */ public class ItemTypePane extends AdjustablePanel {
/*     */   public static final int SEL_NONE = 0;
/*     */   public static final int SEL_ITEM_ALL = 1;
/*     */   public static final int SEL_WEAPON_ALL = 2;
/*     */   public static final int SEL_ARMOR_JEWEL_ALL = 3;
/*     */   public static final int SEL_ARMOR_ALL = 4;
/*     */   public static final int SEL_JEWEL_ALL = 5;
/*     */   public static final int SEL_WEAPON_MELEE = 6;
/*     */   public static final int SEL_WEAPON_MELEE_1H = 7;
/*     */   public static final int SEL_WEAPON_MELEE_2H = 8;
/*     */   public static final int SEL_WEAPON_CASTER = 9;
/*     */   public static final int SEL_WEAPON_RANGED = 10;
/*     */   
/*     */   private class SelectionActionListener implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  33 */       JComboBox cb = (JComboBox)e.getSource();
/*     */       
/*  35 */       int i = cb.getSelectedIndex();
/*  36 */       if (i == -1)
/*     */         return; 
/*  38 */       ItemTypePane.this.pnlWeaponTypes.setSelection(i);
/*  39 */       ItemTypePane.this.pnlArmorTypes.setSelection(i);
/*  40 */       ItemTypePane.this.pnlMiscTypes.setSelection(i);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SelectionActionListener() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private static String[] selections = null;
/*     */   
/*     */   private JComboBox<String> cbSelection;
/*     */   
/*     */   private WeaponTypePane pnlWeaponTypes;
/*     */   private ArmorTypePane pnlArmorTypes;
/*     */   private MiscTypePane pnlMiscTypes;
/*     */   private GDTabbedSearchDialog.Mode mode;
/*     */   
/*     */   static {
/*  66 */     selections = new String[11];
/*     */     
/*  68 */     selections[0] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_NONE");
/*  69 */     selections[1] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_ITEM_ALL");
/*  70 */     selections[2] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_WEAPON_ALL");
/*  71 */     selections[3] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_ARMOR_JEWEL_ALL");
/*  72 */     selections[4] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_ARMOR_ALL");
/*  73 */     selections[5] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_JEWEL_ALL");
/*  74 */     selections[6] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_WEAPON_MELEE");
/*  75 */     selections[7] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_WEAPON_MELEE_1H");
/*  76 */     selections[8] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_WEAPON_MELEE_2H");
/*  77 */     selections[9] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_WEAPON_CASTER");
/*  78 */     selections[10] = GDMsgFormatter.getString(GDMsgFormatter.rbUI, "TXT_WEAPON_RANGED");
/*     */   }
/*     */   
/*     */   public ItemTypePane(GDTabbedSearchDialog.Mode mode) {
/*  82 */     this.mode = mode;
/*     */     
/*  84 */     adjustUI();
/*     */     
/*  86 */     JPanel pnlTypes = new JPanel();
/*     */     
/*  88 */     GroupLayout layout = null;
/*  89 */     GroupLayout.SequentialGroup hGroup = null;
/*  90 */     GroupLayout.SequentialGroup vGroup = null;
/*     */     
/*  92 */     layout = new GroupLayout(pnlTypes);
/*  93 */     pnlTypes.setLayout(layout);
/*     */     
/*  95 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/*  98 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 101 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 104 */     hGroup
/* 105 */       .addGroup(layout.createParallelGroup()
/* 106 */         .addComponent((Component)this.pnlWeaponTypes))
/* 107 */       .addGroup(layout.createParallelGroup()
/* 108 */         .addComponent((Component)this.pnlArmorTypes))
/* 109 */       .addGroup(layout.createParallelGroup()
/* 110 */         .addComponent((Component)this.pnlMiscTypes));
/* 111 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 114 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 117 */     vGroup
/* 118 */       .addGroup(layout.createParallelGroup()
/* 119 */         .addComponent((Component)this.pnlWeaponTypes)
/* 120 */         .addComponent((Component)this.pnlArmorTypes)
/* 121 */         .addComponent((Component)this.pnlMiscTypes));
/*     */     
/* 123 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 125 */     layout.linkSize(0, new Component[] { (Component)this.pnlWeaponTypes, (Component)this.pnlArmorTypes });
/* 126 */     layout.linkSize(0, new Component[] { (Component)this.pnlWeaponTypes, (Component)this.pnlMiscTypes });
/*     */     
/* 128 */     layout.linkSize(1, new Component[] { (Component)this.pnlWeaponTypes, (Component)this.pnlArmorTypes });
/* 129 */     layout.linkSize(1, new Component[] { (Component)this.pnlWeaponTypes, (Component)this.pnlMiscTypes });
/*     */ 
/*     */ 
/*     */     
/* 133 */     layout = new GroupLayout((Container)this);
/* 134 */     setLayout(layout);
/*     */     
/* 136 */     layout.setAutoCreateGaps(false);
/*     */ 
/*     */     
/* 139 */     layout.setAutoCreateContainerGaps(false);
/*     */ 
/*     */     
/* 142 */     hGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 145 */     hGroup
/* 146 */       .addGroup(layout.createParallelGroup()
/* 147 */         .addComponent(this.cbSelection)
/* 148 */         .addComponent(pnlTypes));
/* 149 */     layout.setHorizontalGroup(hGroup);
/*     */ 
/*     */     
/* 152 */     vGroup = layout.createSequentialGroup();
/*     */ 
/*     */     
/* 155 */     vGroup
/* 156 */       .addGroup(layout.createParallelGroup()
/* 157 */         .addComponent(this.cbSelection))
/* 158 */       .addGroup(layout.createParallelGroup()
/* 159 */         .addComponent(pnlTypes));
/* 160 */     layout.setVerticalGroup(vGroup);
/*     */     
/* 162 */     layout.linkSize(0, new Component[] { this.cbSelection, pnlTypes });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void adjustUI() {
/* 169 */     Font fntLabel = UIManager.getDefaults().getFont("Label.font");
/* 170 */     Font fntCombo = UIManager.getDefaults().getFont("ComboBox.font");
/* 171 */     if (fntCombo == null) fntCombo = fntLabel; 
/* 172 */     Font fntBorder = UIManager.getDefaults().getFont("TitledBorder.font");
/* 173 */     if (fntBorder == null) fntBorder = fntLabel;
/*     */     
/* 175 */     fntLabel = fntLabel.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 176 */     fntCombo = fntCombo.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/* 177 */     fntBorder = fntBorder.deriveFont(GDStashFrame.iniConfig.sectUI.fontSize);
/*     */     
/* 179 */     Border lowered = BorderFactory.createEtchedBorder(1);
/* 180 */     Border raised = BorderFactory.createEtchedBorder(0);
/* 181 */     Border compound = BorderFactory.createCompoundBorder(raised, lowered);
/* 182 */     TitledBorder text = BorderFactory.createTitledBorder(compound, GDMsgFormatter.getString(GDMsgFormatter.rbGD, "TXT_ITEM_INFO"));
/* 183 */     text.setTitleFont(fntBorder);
/*     */     
/* 185 */     setBorder(text);
/*     */     
/* 187 */     if (this.cbSelection == null) {
/* 188 */       this.cbSelection = new JComboBox<>(selections);
/*     */       
/* 190 */       this.cbSelection.addActionListener(new SelectionActionListener());
/*     */     } 
/* 192 */     this.cbSelection.setFont(fntCombo);
/* 193 */     this.cbSelection.setMaximumSize(new Dimension(1000, 3 * GDStashFrame.iniConfig.sectUI.fontSize));
/*     */     
/* 195 */     if (this.pnlWeaponTypes == null) {
/* 196 */       this.pnlWeaponTypes = new WeaponTypePane();
/*     */     } else {
/* 198 */       this.pnlWeaponTypes.adjustUI();
/*     */     } 
/*     */     
/* 201 */     if (this.pnlArmorTypes == null) {
/* 202 */       this.pnlArmorTypes = new ArmorTypePane();
/*     */     } else {
/* 204 */       this.pnlArmorTypes.adjustUI();
/*     */     } 
/*     */     
/* 207 */     if (this.pnlMiscTypes == null) {
/* 208 */       this.pnlMiscTypes = new MiscTypePane(this.mode);
/*     */     } else {
/* 210 */       this.pnlMiscTypes.adjustUI();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clear() {
/* 215 */     this.pnlWeaponTypes.clear();
/* 216 */     this.pnlArmorTypes.clear();
/* 217 */     this.pnlMiscTypes.clear();
/*     */   }
/*     */   
/*     */   public void addCriteria(SelectionCriteria criteria) {
/* 221 */     this.pnlWeaponTypes.addCriteria(criteria);
/* 222 */     this.pnlArmorTypes.addCriteria(criteria);
/* 223 */     this.pnlMiscTypes.addCriteria(criteria);
/*     */     
/* 225 */     criteria.selMode = SelectionCriteria.SelectionMode.ITEM;
/*     */   }
/*     */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\select\ItemTypePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */