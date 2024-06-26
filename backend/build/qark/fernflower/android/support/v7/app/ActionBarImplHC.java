package android.support.v7.app;

import android.support.v7.appcompat.R$id;
import android.support.v7.internal.widget.NativeActionModeAwareLayout;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

class ActionBarImplHC extends ActionBarImplBase implements NativeActionModeAwareLayout.OnActionModeForChildListener {
   private ActionMode mCurActionMode;
   final NativeActionModeAwareLayout mNativeActionModeAwareLayout;

   public ActionBarImplHC(ActionBarActivity var1, ActionBar.Callback var2) {
      super(var1, var2);
      this.mNativeActionModeAwareLayout = (NativeActionModeAwareLayout)var1.findViewById(R$id.action_bar_root);
      if (this.mNativeActionModeAwareLayout != null) {
         this.mNativeActionModeAwareLayout.setActionModeForChildListener(this);
      }

   }

   public void hide() {
      super.hide();
      if (this.mCurActionMode != null) {
         this.mCurActionMode.finish();
      }

   }

   boolean isShowHideAnimationEnabled() {
      return this.mCurActionMode == null && super.isShowHideAnimationEnabled();
   }

   public android.view.ActionMode.Callback onActionModeForChild(android.view.ActionMode.Callback var1) {
      return new ActionBarImplHC.CallbackWrapper(var1);
   }

   public void show() {
      super.show();
      if (this.mCurActionMode != null) {
         this.mCurActionMode.finish();
      }

   }

   private class CallbackWrapper implements android.view.ActionMode.Callback {
      private final android.view.ActionMode.Callback mWrappedCallback;

      CallbackWrapper(android.view.ActionMode.Callback var2) {
         this.mWrappedCallback = var2;
      }

      public boolean onActionItemClicked(ActionMode var1, MenuItem var2) {
         return this.mWrappedCallback.onActionItemClicked(var1, var2);
      }

      public boolean onCreateActionMode(ActionMode var1, Menu var2) {
         boolean var3 = this.mWrappedCallback.onCreateActionMode(var1, var2);
         if (var3) {
            ActionBarImplHC.this.mCurActionMode = var1;
            ActionBarImplHC.this.showForActionMode();
         }

         return var3;
      }

      public void onDestroyActionMode(ActionMode var1) {
         this.mWrappedCallback.onDestroyActionMode(var1);
         ActionBarImplHC.this.hideForActionMode();
         ActionBarImplHC.this.mCurActionMode = null;
      }

      public boolean onPrepareActionMode(ActionMode var1, Menu var2) {
         return this.mWrappedCallback.onPrepareActionMode(var1, var2);
      }
   }
}
