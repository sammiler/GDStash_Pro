package org.gdstash.ui.stash;

import java.util.List;
import org.gdstash.character.GDChar;
import org.gdstash.item.GDItem;

public interface GDUIMultiContainer {
  void clearContainers();
  
  void addContainer(GDUIContainer paramGDUIContainer);
  
  void layoutContainers();
  
  boolean addItem(GDItem paramGDItem, int paramInt1, int paramInt2, int paramInt3);
  
  boolean deleteItem(GDItem paramGDItem, int paramInt, boolean paramBoolean);
  
  boolean hasChanged();
  
  List<GDItem> getItemList(int paramInt);
  
  void refresh();
  
  void setChar(GDChar paramGDChar);
}


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\stash\GDUIMultiContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */