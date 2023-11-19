package org.gdstash.ui.stash;

import java.awt.AWTEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import org.gdstash.item.GDItem;
import org.gdstash.item.GDItemContainer;

public interface GDUIContainer {
  void setContainer(GDItemContainer paramGDItemContainer);
  
  BufferedImage getBackgroundImage();
  
  BufferedImage drawGraphics();
  
  int getXOffset();
  
  int getYOffset();
  
  int getHeight();
  
  int getWidth();
  
  void dispatchEvent(AWTEvent paramAWTEvent);
  
  boolean addItem(GDItem paramGDItem, int paramInt1, int paramInt2, int paramInt3);
  
  boolean deleteItem(GDItem paramGDItem, int paramInt, boolean paramBoolean);
  
  boolean hasChanged();
  
  List<GDItem> getItemList(int paramInt);
  
  void refresh();
  
  void updateConfig();
}


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\stash\GDUIContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */