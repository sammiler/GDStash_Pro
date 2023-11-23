/*    */ package org.gdstash.ui;
/*    */
/*    */ import java.awt.Desktop;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.net.URI;
import java.util.List;
/*    */ import org.gdstash.item.GDItem;
import org.gdstash.ui.stash.GDContainerMapPane;
import org.gdstash.ui.stash.GDContainerPane;
import org.gdstash.ui.stash.GDUIContainer;
import org.gdstash.util.GDConstants;
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class GDAutoSaveActionListener
        /*    */   implements ActionListener
        /*    */ {
    /*    */

                    private GDCraftPane craftPane;
    /*    */   public GDAutoSaveActionListener(GDCraftPane pane) {
                        this.craftPane = pane;
        /*    */   }
    /*    */
    /*    */
    /*    */   public void actionPerformed(ActionEvent ev) {


        GDItem item = craftPane.getGDItemCraftPane().getItem();
        final int width =  10;
        final int height = 18;
        int widthSpace = item.getImage().getWidth() / 32;
        int heightSpace = item.getImage().getHeight() /32;
        List<GDUIContainer> containers = craftPane.getsharedStashPane().getCurrentPage().getGDUIContainers();
        GDContainerPane gdContainerPane = (GDContainerPane) containers.get(0);
        if (!gdContainerPane.getGDItemContainer().getItemList().isEmpty())
        {
            return ;
        }
        System.out.println("ContainersNum:  " + containers.size());
        int rowCount = width/widthSpace;
        int colCount = height/heightSpace;
        for(int w = 0,r = 0; r < rowCount;w+=widthSpace,r++)
        {
            for (int h = 0,c = 0; c < colCount;h+=heightSpace,c++)
            {
                GDItem cloneItem = item.clone();
                cloneItem.createSeed();
                cloneItem.setX(w);
                cloneItem.setY(h);
                gdContainerPane.getGDItemContainer().addItem(cloneItem);
                craftPane.getsharedStashPane().getCurrentPage().refreshPage();
            }
        }
        /*    */   }
    /*    */ }


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\GDHelpActionListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */