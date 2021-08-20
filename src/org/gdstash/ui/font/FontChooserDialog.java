/*     */ package org.gdstash.ui.font;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Font;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
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
/*     */ public class FontChooserDialog
/*     */ {
/*  39 */   private static final Insets LABEL_INSETS = new Insets(10, 10, 0, 0);
/*  40 */   private static final Insets COMBOBOX_INSETS = new Insets(0, 10, 0, 0);
/*  41 */   private static final Insets COMBOBOX_INSETS_LAST = new Insets(0, 10, 0, 10);
/*  42 */   private static final Insets BUTTON_INSETS = new Insets(10, 10, 10, 10);
/*     */   
/*     */   private static final String SAMPLE_STRING = "AaBbIiMmYyZz";
/*     */   
/*     */   private boolean okPressed;
/*     */   
/*     */   private int familySize;
/*     */   
/*     */   private int familyStyle;
/*     */   
/*     */   private DefaultListModel<String> fontNameListModel;
/*     */   
/*     */   private DefaultListModel<String> fontStyleListModel;
/*     */   
/*     */   private DefaultListModel<String> fontSizeListModel;
/*     */   
/*     */   private EscapeDialog dialog;
/*     */   
/*     */   private Font font;
/*     */   
/*     */   private JFrame frame;
/*     */   
/*     */   private JPanel mainPanel;
/*     */   private FontSamplePanel samplePanel;
/*     */   private String familyName;
/*     */   private TextFieldList fontNameList;
/*     */   private TextFieldList fontStyleList;
/*     */   private TextFieldList fontSizeList;
/*     */   
/*     */   public FontChooserDialog(JFrame frame) {
/*  72 */     this(frame, null);
/*     */   }
/*     */   
/*     */   public FontChooserDialog(JFrame frame, Font defaultFont) {
/*  76 */     this.frame = frame;
/*  77 */     this.font = defaultFont;
/*  78 */     this.fontNameListModel = new DefaultListModel<>();
/*  79 */     this.fontStyleListModel = new DefaultListModel<>();
/*  80 */     this.fontSizeListModel = new DefaultListModel<>();
/*  81 */     getDefaultFontFields();
/*  82 */     getAllFontNames();
/*  83 */     getAllFontStyles();
/*  84 */     getAllFontSizes();
/*  85 */     createPartControl();
/*     */   }
/*     */   
/*     */   public Font getSelectedFont() {
/*  89 */     return this.font;
/*     */   }
/*     */   
/*     */   public boolean isOkPressed() {
/*  93 */     return this.okPressed;
/*     */   }
/*     */   
/*     */   private void getDefaultFontFields() {
/*  97 */     if (this.font != null) {
/*  98 */       this.familyName = this.font.getFamily();
/*  99 */       this.familyStyle = this.font.getStyle();
/* 100 */       this.familySize = this.font.getSize();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void getAllFontNames() {
/* 105 */     GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 106 */     for (String name : e.getAvailableFontFamilyNames()) {
/* 107 */       this.fontNameListModel.addElement(name);
/*     */     }
/*     */   }
/*     */   
/*     */   private void getAllFontStyles() {
/* 112 */     this.fontStyleListModel.addElement("Regular");
/* 113 */     this.fontStyleListModel.addElement("Bold");
/* 114 */     this.fontStyleListModel.addElement("Italic");
/* 115 */     this.fontStyleListModel.addElement("Bold Italic");
/*     */   }
/*     */   private void getAllFontSizes() {
/*     */     int i;
/* 119 */     for (i = 8; i <= 11; i++) {
/* 120 */       this.fontSizeListModel.addElement(Integer.toString(i));
/*     */     }
/*     */     
/* 123 */     for (i = 12; i <= 28; i += 2) {
/* 124 */       this.fontSizeListModel.addElement(Integer.toString(i));
/*     */     }
/*     */     
/* 127 */     this.fontSizeListModel.addElement(Integer.toString(36));
/* 128 */     this.fontSizeListModel.addElement(Integer.toString(48));
/* 129 */     this.fontSizeListModel.addElement(Integer.toString(72));
/*     */   }
/*     */   
/*     */   private void createPartControl() {
/* 133 */     this.dialog = new EscapeDialog(this.frame);
/* 134 */     this.dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
/* 135 */     this.dialog.setTitle("Font");
/*     */     
/* 137 */     this.mainPanel = new JPanel();
/* 138 */     this.mainPanel.setLayout(new GridBagLayout());
/*     */     
/* 140 */     int gridy = 0;
/* 141 */     gridy = createFontLabelControl(gridy);
/* 142 */     gridy = createFontComboBoxControl(gridy);
/* 143 */     gridy = createSampleControl(gridy);
/* 144 */     gridy = createButtonControl(gridy);
/*     */     
/* 146 */     this.dialog.add(this.mainPanel);
/* 147 */     this.dialog.pack();
/* 148 */     this.dialog.setVisible(true);
/*     */   }
/*     */   
/*     */   private int createFontLabelControl(int gridy) {
/* 152 */     JLabel fontNameLabel = new JLabel("Font");
/* 153 */     addComponent(this.mainPanel, fontNameLabel, 0, gridy, 1, 1, LABEL_INSETS, 21, 0);
/*     */ 
/*     */     
/* 156 */     JLabel fontStyleLabel = new JLabel("Font style");
/* 157 */     addComponent(this.mainPanel, fontStyleLabel, 1, gridy, 1, 1, LABEL_INSETS, 21, 0);
/*     */ 
/*     */     
/* 160 */     JLabel fontSizeLabel = new JLabel("Size");
/* 161 */     addComponent(this.mainPanel, fontSizeLabel, 2, gridy++, 1, 1, LABEL_INSETS, 21, 0);
/*     */ 
/*     */     
/* 164 */     return gridy;
/*     */   }
/*     */   
/*     */   private int createFontComboBoxControl(int gridy) {
/* 168 */     DrawSelectionListener listener = new DrawSelectionListener();
/*     */     
/* 170 */     this.fontNameList = new TextFieldList(this.fontNameListModel);
/* 171 */     this.fontNameList.addListSelectionListener(listener);
/* 172 */     this.fontNameList.setVisibleRowCount(7);
/* 173 */     addComponent(this.mainPanel, this.fontNameList, 0, gridy, 1, 1, COMBOBOX_INSETS, 21, 2);
/*     */ 
/*     */     
/* 176 */     this.fontStyleList = new TextFieldList(this.fontStyleListModel, 75);
/* 177 */     this.fontStyleList.addListSelectionListener(listener);
/* 178 */     this.fontStyleList.setVisibleRowCount(7);
/* 179 */     addComponent(this.mainPanel, this.fontStyleList, 1, gridy, 1, 1, COMBOBOX_INSETS, 21, 2);
/*     */ 
/*     */     
/* 182 */     this.fontSizeList = new TextFieldList(this.fontSizeListModel, 25);
/* 183 */     this.fontSizeList.addListSelectionListener(listener);
/* 184 */     this.fontSizeList.setVisibleRowCount(7);
/* 185 */     addComponent(this.mainPanel, this.fontSizeList, 2, gridy++, 1, 1, COMBOBOX_INSETS_LAST, 21, 2);
/*     */ 
/*     */ 
/*     */     
/* 189 */     if (this.font != null) {
/*     */       
/* 191 */       this.fontStyleList.setSelectedIndex(this.familyStyle);
/* 192 */       this.fontNameList.setSelectedValue(this.familyName);
/* 193 */       this.fontSizeList.setSelectedValue(Integer.valueOf(this.familySize));
/*     */     } 
/*     */     
/* 196 */     return gridy;
/*     */   }
/*     */   
/*     */   private int createSampleControl(int gridy) {
/* 200 */     Border titledBorder = BorderFactory.createTitledBorder("Sample");
/* 201 */     Border bevelBorder = BorderFactory.createBevelBorder(1);
/*     */     
/* 203 */     JPanel titlePanel = new JPanel();
/* 204 */     titlePanel.setBorder(titledBorder);
/* 205 */     titlePanel.setLayout(new BorderLayout());
/*     */     
/* 207 */     this.samplePanel = new FontSamplePanel("AaBbIiMmYyZz");
/* 208 */     this.samplePanel.setBorder(bevelBorder);
/* 209 */     this.samplePanel.setPreferredSize(new Dimension(300, 100));
/*     */     
/* 211 */     titlePanel.add(this.samplePanel, "Center");
/*     */     
/* 213 */     addComponent(this.mainPanel, titlePanel, 0, gridy++, 3, 1, COMBOBOX_INSETS_LAST, 21, 2);
/*     */ 
/*     */     
/* 216 */     if (this.font != null) {
/* 217 */       drawSample();
/*     */     }
/*     */     
/* 220 */     return gridy;
/*     */   }
/*     */   
/*     */   private int createButtonControl(int gridy) {
/* 224 */     JPanel buttondrawingPanel = new JPanel();
/* 225 */     buttondrawingPanel.setLayout(new FlowLayout());
/*     */     
/* 227 */     JButton okButton = new JButton("OK");
/* 228 */     okButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 231 */             Font testFont = FontChooserDialog.this.createSelectedFont();
/* 232 */             if (testFont == null)
/*     */               return; 
/* 234 */             FontChooserDialog.this.okPressed = true;
/* 235 */             FontChooserDialog.this.font = testFont;
/* 236 */             FontChooserDialog.this.dialog.setVisible(false);
/*     */           }
/*     */         });
/*     */     
/* 240 */     this.dialog.setOkButton(okButton);
/* 241 */     buttondrawingPanel.add(okButton);
/*     */     
/* 243 */     JButton cancelButton = new JButton("Cancel");
/* 244 */     cancelButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 247 */             FontChooserDialog.this.okPressed = false;
/* 248 */             FontChooserDialog.this.dialog.setVisible(false);
/*     */           }
/*     */         });
/*     */     
/* 252 */     buttondrawingPanel.add(cancelButton);
/*     */     
/* 254 */     setButtonSizes(new JButton[] { okButton, cancelButton });
/* 255 */     addComponent(this.mainPanel, buttondrawingPanel, 0, gridy++, 3, 1, BUTTON_INSETS, 21, 2);
/*     */ 
/*     */     
/* 258 */     return gridy;
/*     */   }
/*     */   
/*     */   private void setButtonSizes(JButton... buttons) {
/* 262 */     Dimension preferredSize = new Dimension();
/* 263 */     for (JButton button : buttons) {
/* 264 */       Dimension d = button.getPreferredSize();
/* 265 */       preferredSize = setLarger(preferredSize, d);
/*     */     } 
/*     */     
/* 268 */     for (JButton button : buttons) {
/* 269 */       button.setPreferredSize(preferredSize);
/*     */     }
/*     */   }
/*     */   
/*     */   private Dimension setLarger(Dimension a, Dimension b) {
/* 274 */     Dimension d = new Dimension();
/*     */     
/* 276 */     d.height = Math.max(a.height, b.height);
/* 277 */     d.width = Math.max(a.width, b.width);
/*     */     
/* 279 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight, Insets insets, int anchor, int fill) {
/* 286 */     GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0D, 1.0D, anchor, fill, insets, 0, 0);
/*     */ 
/*     */     
/* 289 */     container.add(component, gbc);
/*     */   }
/*     */   
/*     */   public void drawSample() {
/* 293 */     if (this.samplePanel == null)
/*     */       return; 
/* 295 */     this.font = createSelectedFont();
/* 296 */     if (this.font == null)
/*     */       return; 
/* 298 */     this.samplePanel.setSampleFont(this.font);
/*     */   }
/*     */   
/*     */   private Font createSelectedFont() {
/* 302 */     boolean allValues = true;
/*     */     
/* 304 */     if (this.fontNameList == null || this.fontNameList.getSelectedValue() == null) {
/* 305 */       allValues = false;
/*     */     } else {
/* 307 */       this.familyName = this.fontNameList.getSelectedValue();
/*     */     } 
/*     */     
/* 310 */     if (this.fontStyleList == null) {
/* 311 */       allValues = false;
/*     */     } else {
/* 313 */       this.familyStyle = this.fontStyleList.getSelectedIndex();
/*     */     } 
/*     */     
/* 316 */     if (this.fontSizeList == null || this.fontSizeList.getSelectedValue() == null) {
/* 317 */       allValues = false;
/*     */     } else {
/* 319 */       this.familySize = Integer.parseInt(this.fontSizeList.getSelectedValue());
/*     */     } 
/*     */     
/* 322 */     if (allValues) {
/* 323 */       return new Font(this.familyName, this.familyStyle, this.familySize);
/*     */     }
/* 325 */     return null;
/*     */   }
/*     */   
/*     */   public class DrawSelectionListener
/*     */     implements ListSelectionListener
/*     */   {
/*     */     public void valueChanged(ListSelectionEvent event) {
/* 332 */       FontChooserDialog.this.drawSample();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\game\Grim Dawn\GDStash.jar!\org\gdstas\\ui\font\FontChooserDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */