package android.support.v4.view.accessibility;

import android.os.Build.VERSION;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public class AccessibilityNodeProviderCompat
{
  private static final AccessibilityNodeProviderImpl IMPL;
  private final Object mProvider;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 16) {}
    for (IMPL = new AccessibilityNodeProviderJellyBeanImpl();; IMPL = new AccessibilityNodeProviderStubImpl()) {
      return;
    }
  }
  
  public AccessibilityNodeProviderCompat()
  {
    mProvider = IMPL.newAccessibilityNodeProviderBridge(this);
  }
  
  public AccessibilityNodeProviderCompat(Object paramObject)
  {
    mProvider = paramObject;
  }
  
  public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int paramInt)
  {
    return null;
  }
  
  public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String paramString, int paramInt)
  {
    return null;
  }
  
  public Object getProvider()
  {
    return mProvider;
  }
  
  public boolean performAction(int paramInt1, int paramInt2, Bundle paramBundle)
  {
    return false;
  }
  
  static abstract interface AccessibilityNodeProviderImpl
  {
    public abstract Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat paramAccessibilityNodeProviderCompat);
  }
  
  static class AccessibilityNodeProviderJellyBeanImpl
    extends AccessibilityNodeProviderCompat.AccessibilityNodeProviderStubImpl
  {
    public Object newAccessibilityNodeProviderBridge(final AccessibilityNodeProviderCompat paramAccessibilityNodeProviderCompat)
    {
      AccessibilityNodeProviderCompatJellyBean.newAccessibilityNodeProviderBridge(new AccessibilityNodeProviderCompatJellyBean.AccessibilityNodeInfoBridge()
      {
        public Object createAccessibilityNodeInfo(int paramAnonymousInt)
        {
          Object localObject = paramAccessibilityNodeProviderCompat.createAccessibilityNodeInfo(paramAnonymousInt);
          if (localObject == null) {}
          for (localObject = null;; localObject = ((AccessibilityNodeInfoCompat)localObject).getInfo()) {
            return localObject;
          }
        }
        
        public List<Object> findAccessibilityNodeInfosByText(String paramAnonymousString, int paramAnonymousInt)
        {
          List localList = paramAccessibilityNodeProviderCompat.findAccessibilityNodeInfosByText(paramAnonymousString, paramAnonymousInt);
          paramAnonymousString = new ArrayList();
          int i = localList.size();
          for (paramAnonymousInt = 0; paramAnonymousInt < i; paramAnonymousInt++) {
            paramAnonymousString.add(((AccessibilityNodeInfoCompat)localList.get(paramAnonymousInt)).getInfo());
          }
          return paramAnonymousString;
        }
        
        public boolean performAction(int paramAnonymousInt1, int paramAnonymousInt2, Bundle paramAnonymousBundle)
        {
          return paramAccessibilityNodeProviderCompat.performAction(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousBundle);
        }
      });
    }
  }
  
  static class AccessibilityNodeProviderStubImpl
    implements AccessibilityNodeProviderCompat.AccessibilityNodeProviderImpl
  {
    public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat paramAccessibilityNodeProviderCompat)
    {
      return null;
    }
  }
}

/* Location:
 * Qualified Name:     android.support.v4.view.accessibility.AccessibilityNodeProviderCompat
 * Java Class Version: 6 (50.0)
 * JD-Core Version:    0.7.1
 */