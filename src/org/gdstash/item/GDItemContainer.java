package org.gdstash.item;

import java.util.List;

public interface GDItemContainer {
  List<GDItem> getItemList();
  
  List<GDItem> getRemovedItemList();
  
  boolean addItem(GDItem paramGDItem);
  
  boolean removeItem(GDItem paramGDItem);
  
  boolean hasChanged();
  
  int getContainerHeight();
  
  int getContainerWidth();
  
  int getContainerType();
  
  void refresh();
}


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstash\item\GDItemContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */