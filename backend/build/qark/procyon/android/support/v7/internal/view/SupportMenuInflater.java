// 
// Decompiled by Procyon v1.0-SNAPSHOT
// 

package android.support.v7.internal.view;

import android.content.res.TypedArray;
import android.support.v7.appcompat.R;
import android.view.SubMenu;
import android.view.View;
import android.support.v7.internal.view.menu.MenuItemWrapperICS;
import android.support.v7.internal.view.menu.MenuItemImpl;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.support.v4.view.ActionProvider;
import android.view.MenuItem;
import java.lang.reflect.Method;
import android.view.MenuItem$OnMenuItemClickListener;
import android.content.res.XmlResourceParser;
import android.view.InflateException;
import android.util.Xml;
import android.support.v4.internal.view.SupportMenu;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import android.view.Menu;
import android.util.AttributeSet;
import org.xmlpull.v1.XmlPullParser;
import android.content.Context;
import android.view.MenuInflater;

public class SupportMenuInflater extends MenuInflater
{
    private static final Class<?>[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE;
    private static final Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
    private static final String LOG_TAG = "SupportMenuInflater";
    private static final int NO_ID = 0;
    private static final String XML_GROUP = "group";
    private static final String XML_ITEM = "item";
    private static final String XML_MENU = "menu";
    private final Object[] mActionProviderConstructorArguments;
    private final Object[] mActionViewConstructorArguments;
    private Context mContext;
    private Object mRealOwner;
    
    static {
        ACTION_VIEW_CONSTRUCTOR_SIGNATURE = new Class[] { Context.class };
        ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = SupportMenuInflater.ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
    }
    
    public SupportMenuInflater(final Context context) {
        super(context);
        this.mContext = context;
        this.mRealOwner = context;
        this.mActionViewConstructorArguments = new Object[] { context };
        this.mActionProviderConstructorArguments = this.mActionViewConstructorArguments;
    }
    
    private void parseMenu(final XmlPullParser xmlPullParser, final AttributeSet set, final Menu menu) throws XmlPullParserException, IOException {
        final MenuState menuState = new MenuState(menu);
        int i = xmlPullParser.getEventType();
        int n = 0;
        String anObject = null;
        String name3;
        while (true) {
            while (i != 2) {
                final int next = xmlPullParser.next();
                if ((i = next) == 1) {
                    final int next2 = next;
                    int j = 0;
                    int next3 = next2;
                    while (j == 0) {
                        String name = null;
                        int n2 = 0;
                        int n3 = 0;
                        switch (next3) {
                            default: {
                                name = anObject;
                                n2 = j;
                                n3 = n;
                                break;
                            }
                            case 2: {
                                n3 = n;
                                n2 = j;
                                name = anObject;
                                if (n != 0) {
                                    break;
                                }
                                name = xmlPullParser.getName();
                                if (name.equals("group")) {
                                    menuState.readGroup(set);
                                    n3 = n;
                                    n2 = j;
                                    name = anObject;
                                    break;
                                }
                                if (name.equals("item")) {
                                    menuState.readItem(set);
                                    n3 = n;
                                    n2 = j;
                                    name = anObject;
                                    break;
                                }
                                if (name.equals("menu")) {
                                    this.parseMenu(xmlPullParser, set, (Menu)menuState.addSubMenuItem());
                                    n3 = n;
                                    n2 = j;
                                    name = anObject;
                                    break;
                                }
                                n3 = 1;
                                n2 = j;
                                break;
                            }
                            case 3: {
                                final String name2 = xmlPullParser.getName();
                                if (n != 0 && name2.equals(anObject)) {
                                    n3 = 0;
                                    name = null;
                                    n2 = j;
                                    break;
                                }
                                if (name2.equals("group")) {
                                    menuState.resetGroup();
                                    n3 = n;
                                    n2 = j;
                                    name = anObject;
                                    break;
                                }
                                if (name2.equals("item")) {
                                    n3 = n;
                                    n2 = j;
                                    name = anObject;
                                    if (menuState.hasAddedItem()) {
                                        break;
                                    }
                                    if (menuState.itemActionProvider != null && menuState.itemActionProvider.hasSubMenu()) {
                                        menuState.addSubMenuItem();
                                        n3 = n;
                                        n2 = j;
                                        name = anObject;
                                        break;
                                    }
                                    menuState.addItem();
                                    n3 = n;
                                    n2 = j;
                                    name = anObject;
                                    break;
                                }
                                else {
                                    n3 = n;
                                    n2 = j;
                                    name = anObject;
                                    if (name2.equals("menu")) {
                                        n2 = 1;
                                        n3 = n;
                                        name = anObject;
                                        break;
                                    }
                                    break;
                                }
                                break;
                            }
                            case 1: {
                                throw new RuntimeException("Unexpected end of document");
                            }
                        }
                        next3 = xmlPullParser.next();
                        n = n3;
                        j = n2;
                        anObject = name;
                    }
                    return;
                }
            }
            name3 = xmlPullParser.getName();
            if (name3.equals("menu")) {
                final int next2 = xmlPullParser.next();
                continue;
            }
            break;
        }
        throw new RuntimeException("Expecting menu, got " + name3);
    }
    
    public void inflate(final int n, final Menu menu) {
        if (!(menu instanceof SupportMenu)) {
            super.inflate(n, menu);
            return;
        }
        XmlResourceParser xmlResourceParser = null;
        XmlResourceParser xmlResourceParser2 = null;
        XmlResourceParser layout = null;
        try {
            final XmlResourceParser xmlResourceParser3 = xmlResourceParser2 = (xmlResourceParser = (layout = this.mContext.getResources().getLayout(n)));
            this.parseMenu((XmlPullParser)xmlResourceParser3, Xml.asAttributeSet((XmlPullParser)xmlResourceParser3), menu);
        }
        catch (XmlPullParserException ex) {
            xmlResourceParser = layout;
            throw new InflateException("Error inflating menu XML", (Throwable)ex);
        }
        catch (IOException ex2) {
            xmlResourceParser = xmlResourceParser2;
            throw new InflateException("Error inflating menu XML", (Throwable)ex2);
        }
        finally {
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
        }
    }
    
    private static class InflatedOnMenuItemClickListener implements MenuItem$OnMenuItemClickListener
    {
        private static final Class<?>[] PARAM_TYPES;
        private Method mMethod;
        private Object mRealOwner;
        
        static {
            PARAM_TYPES = new Class[] { MenuItem.class };
        }
        
        public InflatedOnMenuItemClickListener(final Object mRealOwner, final String s) {
            this.mRealOwner = mRealOwner;
            final Class<?> class1 = mRealOwner.getClass();
            try {
                this.mMethod = class1.getMethod(s, InflatedOnMenuItemClickListener.PARAM_TYPES);
            }
            catch (Exception ex2) {
                final InflateException ex = new InflateException("Couldn't resolve menu item onClick handler " + s + " in class " + class1.getName());
                ex.initCause((Throwable)ex2);
                throw ex;
            }
        }
        
        public boolean onMenuItemClick(final MenuItem menuItem) {
            try {
                if (this.mMethod.getReturnType() == Boolean.TYPE) {
                    return (boolean)this.mMethod.invoke(this.mRealOwner, menuItem);
                }
                this.mMethod.invoke(this.mRealOwner, menuItem);
                return true;
            }
            catch (Exception cause) {
                throw new RuntimeException(cause);
            }
        }
    }
    
    private class MenuState
    {
        private static final int defaultGroupId = 0;
        private static final int defaultItemCategory = 0;
        private static final int defaultItemCheckable = 0;
        private static final boolean defaultItemChecked = false;
        private static final boolean defaultItemEnabled = true;
        private static final int defaultItemId = 0;
        private static final int defaultItemOrder = 0;
        private static final boolean defaultItemVisible = true;
        private int groupCategory;
        private int groupCheckable;
        private boolean groupEnabled;
        private int groupId;
        private int groupOrder;
        private boolean groupVisible;
        private ActionProvider itemActionProvider;
        private String itemActionProviderClassName;
        private String itemActionViewClassName;
        private int itemActionViewLayout;
        private boolean itemAdded;
        private char itemAlphabeticShortcut;
        private int itemCategoryOrder;
        private int itemCheckable;
        private boolean itemChecked;
        private boolean itemEnabled;
        private int itemIconResId;
        private int itemId;
        private String itemListenerMethodName;
        private char itemNumericShortcut;
        private int itemShowAsAction;
        private CharSequence itemTitle;
        private CharSequence itemTitleCondensed;
        private boolean itemVisible;
        private Menu menu;
        
        public MenuState(final Menu menu) {
            this.menu = menu;
            this.resetGroup();
        }
        
        private char getShortcut(final String s) {
            if (s == null) {
                return '\0';
            }
            return s.charAt(0);
        }
        
        private <T> T newInstance(final String s, final Class<?>[] parameterTypes, final Object[] initargs) {
            try {
                return (T)SupportMenuInflater.this.mContext.getClassLoader().loadClass(s).getConstructor(parameterTypes).newInstance(initargs);
            }
            catch (Exception ex) {
                Log.w("SupportMenuInflater", "Cannot instantiate class: " + s, (Throwable)ex);
                return null;
            }
        }
        
        private void setItem(final MenuItem menuItem) {
            menuItem.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled).setCheckable(this.itemCheckable >= 1).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId).setAlphabeticShortcut(this.itemAlphabeticShortcut).setNumericShortcut(this.itemNumericShortcut);
            if (this.itemShowAsAction >= 0) {
                MenuItemCompat.setShowAsAction(menuItem, this.itemShowAsAction);
            }
            if (this.itemListenerMethodName != null) {
                if (SupportMenuInflater.this.mContext.isRestricted()) {
                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
                menuItem.setOnMenuItemClickListener((MenuItem$OnMenuItemClickListener)new InflatedOnMenuItemClickListener(SupportMenuInflater.this.mRealOwner, this.itemListenerMethodName));
            }
            if (menuItem instanceof MenuItemImpl) {
                final MenuItemImpl menuItemImpl = (MenuItemImpl)menuItem;
            }
            if (this.itemCheckable >= 2) {
                if (menuItem instanceof MenuItemImpl) {
                    ((MenuItemImpl)menuItem).setExclusiveCheckable(true);
                }
                else if (menuItem instanceof MenuItemWrapperICS) {
                    ((MenuItemWrapperICS)menuItem).setExclusiveCheckable(true);
                }
            }
            boolean b = false;
            if (this.itemActionViewClassName != null) {
                MenuItemCompat.setActionView(menuItem, this.newInstance(this.itemActionViewClassName, SupportMenuInflater.ACTION_VIEW_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionViewConstructorArguments));
                b = true;
            }
            if (this.itemActionViewLayout > 0) {
                if (!b) {
                    MenuItemCompat.setActionView(menuItem, this.itemActionViewLayout);
                }
                else {
                    Log.w("SupportMenuInflater", "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                }
            }
            if (this.itemActionProvider != null) {
                MenuItemCompat.setActionProvider(menuItem, this.itemActionProvider);
            }
        }
        
        public void addItem() {
            this.itemAdded = true;
            this.setItem(this.menu.add(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle));
        }
        
        public SubMenu addSubMenuItem() {
            this.itemAdded = true;
            final SubMenu addSubMenu = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
            this.setItem(addSubMenu.getItem());
            return addSubMenu;
        }
        
        public boolean hasAddedItem() {
            return this.itemAdded;
        }
        
        public void readGroup(final AttributeSet set) {
            final TypedArray obtainStyledAttributes = SupportMenuInflater.this.mContext.obtainStyledAttributes(set, R.styleable.MenuGroup);
            this.groupId = obtainStyledAttributes.getResourceId(1, 0);
            this.groupCategory = obtainStyledAttributes.getInt(3, 0);
            this.groupOrder = obtainStyledAttributes.getInt(4, 0);
            this.groupCheckable = obtainStyledAttributes.getInt(5, 0);
            this.groupVisible = obtainStyledAttributes.getBoolean(2, true);
            this.groupEnabled = obtainStyledAttributes.getBoolean(0, true);
            obtainStyledAttributes.recycle();
        }
        
        public void readItem(final AttributeSet set) {
            final TypedArray obtainStyledAttributes = SupportMenuInflater.this.mContext.obtainStyledAttributes(set, R.styleable.MenuItem);
            this.itemId = obtainStyledAttributes.getResourceId(2, 0);
            this.itemCategoryOrder = ((0xFFFF0000 & obtainStyledAttributes.getInt(5, this.groupCategory)) | (0xFFFF & obtainStyledAttributes.getInt(6, this.groupOrder)));
            this.itemTitle = obtainStyledAttributes.getText(7);
            this.itemTitleCondensed = obtainStyledAttributes.getText(8);
            this.itemIconResId = obtainStyledAttributes.getResourceId(0, 0);
            this.itemAlphabeticShortcut = this.getShortcut(obtainStyledAttributes.getString(9));
            this.itemNumericShortcut = this.getShortcut(obtainStyledAttributes.getString(10));
            if (obtainStyledAttributes.hasValue(11)) {
                int itemCheckable;
                if (obtainStyledAttributes.getBoolean(11, false)) {
                    itemCheckable = 1;
                }
                else {
                    itemCheckable = 0;
                }
                this.itemCheckable = itemCheckable;
            }
            else {
                this.itemCheckable = this.groupCheckable;
            }
            this.itemChecked = obtainStyledAttributes.getBoolean(3, false);
            this.itemVisible = obtainStyledAttributes.getBoolean(4, this.groupVisible);
            this.itemEnabled = obtainStyledAttributes.getBoolean(1, this.groupEnabled);
            this.itemShowAsAction = obtainStyledAttributes.getInt(13, -1);
            this.itemListenerMethodName = obtainStyledAttributes.getString(12);
            this.itemActionViewLayout = obtainStyledAttributes.getResourceId(14, 0);
            this.itemActionViewClassName = obtainStyledAttributes.getString(15);
            this.itemActionProviderClassName = obtainStyledAttributes.getString(16);
            boolean b;
            if (this.itemActionProviderClassName != null) {
                b = true;
            }
            else {
                b = false;
            }
            if (b && this.itemActionViewLayout == 0 && this.itemActionViewClassName == null) {
                this.itemActionProvider = this.newInstance(this.itemActionProviderClassName, SupportMenuInflater.ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionProviderConstructorArguments);
            }
            else {
                if (b) {
                    Log.w("SupportMenuInflater", "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                }
                this.itemActionProvider = null;
            }
            obtainStyledAttributes.recycle();
            this.itemAdded = false;
        }
        
        public void resetGroup() {
            this.groupId = 0;
            this.groupCategory = 0;
            this.groupOrder = 0;
            this.groupCheckable = 0;
            this.groupVisible = true;
            this.groupEnabled = true;
        }
    }
}
