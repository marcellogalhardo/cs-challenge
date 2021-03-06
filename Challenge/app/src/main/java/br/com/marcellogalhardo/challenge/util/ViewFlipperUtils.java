package br.com.marcellogalhardo.challenge.util;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ViewFlipper;

public class ViewFlipperUtils {

  private boolean isValidChildIndex(@NonNull ViewFlipper flipper, int index) {
    return index < flipper.getChildCount() && index >= 0;
  }

  public boolean isValidChild(@NonNull ViewFlipper flipper, View child) {
    int index = flipper.indexOfChild(child);
    return index < flipper.getChildCount() && index >= 0;
  }

  public boolean isDisplayedChild(@NonNull ViewFlipper flipper, View child) {
    return flipper.getCurrentView() == child;
  }

  public void setDisplayedChild(ViewFlipper flipper, View child) {
    if (flipper != null && child != null) {
      int index = flipper.indexOfChild(child);
      if (isValidChildIndex(flipper, index)) {
        flipper.setDisplayedChild(index);
      }
    }
  }
}
