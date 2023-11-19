package org.gdstash.ui.stash;

import org.gdstash.item.GDItem;

public interface GDUIInventory {
  GDItem getSelectedItem();
  
  void setSelectedItem(GDItem paramGDItem);
  
  void refresh();
}


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\stash\GDUIInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */