package org.gdstash.ui;

import java.awt.Frame;
import org.gdstash.item.GDItem;
import org.gdstash.ui.util.GDCharInfoList;
import org.gdstash.ui.util.GDStashInfoList;

public interface GDUITransfer {
  public static final int LOCATION_NONE = 0;
  
  public static final int LOCATION_STASH = 1;
  
  public static final int LOCATION_TABLE = 2;
  
  public static final int ACTION_STASH_MOVE_STASH = 1;
  
  public static final int ACTION_STASH_COPY_TABLE = 2;
  
  public static final int ACTION_STASH_MOVE_TABLE = 3;
  
  public static final int ACTION_TABLE_COPY_STASH = 4;
  
  public static final int ACTION_TABLE_DELETE = 5;
  
  public static final int ACTION_STASH_DELETE = 6;
  
  public static final int ACTION_PAGE_COPY_TABLE = 7;
  
  public static final int ACTION_PAGE_MOVE_TABLE = 8;
  
  public static final int ACTION_PAGE_DELETE = 9;
  
  void setChar(GDCharInfoList.GDCharFileInfo paramGDCharFileInfo);
  
  void setStash(GDStashInfoList.GDStashFileInfo paramGDStashFileInfo);
  
  int getItemLocation();
  
  GDItem getSelectedItem();
  
  void setSelectedItem(GDItem paramGDItem, int paramInt);
  
  void transferSelectedItem(int paramInt1, int paramInt2, int paramInt3);
  
  void checkSaveButton();
  
  Frame getFrame();
}


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\ui\GDUITransfer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */