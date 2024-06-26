package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.ViewConfiguration;

public class ViewConfigurationCompat
{
  static final ViewConfigurationVersionImpl IMPL;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 11) {}
    for (IMPL = new FroyoViewConfigurationVersionImpl();; IMPL = new BaseViewConfigurationVersionImpl()) {
      return;
    }
  }
  
  public static int getScaledPagingTouchSlop(ViewConfiguration paramViewConfiguration)
  {
    return IMPL.getScaledPagingTouchSlop(paramViewConfiguration);
  }
  
  static class BaseViewConfigurationVersionImpl
    implements ViewConfigurationCompat.ViewConfigurationVersionImpl
  {
    public int getScaledPagingTouchSlop(ViewConfiguration paramViewConfiguration)
    {
      return paramViewConfiguration.getScaledTouchSlop();
    }
  }
  
  static class FroyoViewConfigurationVersionImpl
    implements ViewConfigurationCompat.ViewConfigurationVersionImpl
  {
    public int getScaledPagingTouchSlop(ViewConfiguration paramViewConfiguration)
    {
      return ViewConfigurationCompatFroyo.getScaledPagingTouchSlop(paramViewConfiguration);
    }
  }
  
  static abstract interface ViewConfigurationVersionImpl
  {
    public abstract int getScaledPagingTouchSlop(ViewConfiguration paramViewConfiguration);
  }
}

/* Location:
 * Qualified Name:     android.support.v4.view.ViewConfigurationCompat
 * Java Class Version: 6 (50.0)
 * JD-Core Version:    0.7.1
 */