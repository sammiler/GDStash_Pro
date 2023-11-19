package org.gdstash.ui.util;

import java.util.List;
import org.gdstash.character.GDChar;
import org.gdstash.character.GDCharUID;

public abstract class GDAbstractRiftPane extends AdjustablePanel {
  public abstract void adjustUI();
  
  public abstract List<GDCharUID> getRiftList(boolean paramBoolean);
  
  public abstract void setChar(GDChar paramGDChar);
  
  public abstract boolean hasChanged();
  
  public abstract void setChanged(boolean paramBoolean);
}


/* Location:              C:\Users\sammiler\Downloads\GDStash_v174\GDStash.jar!\org\gdstas\\u\\util\GDAbstractRiftPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */