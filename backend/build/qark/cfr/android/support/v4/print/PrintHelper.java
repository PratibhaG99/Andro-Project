/*
 * Decompiled with CFR 0_124.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  java.io.FileNotFoundException
 *  java.lang.Object
 *  java.lang.String
 */
package android.support.v4.print;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v4.print.PrintHelperKitkat;
import java.io.FileNotFoundException;

public final class PrintHelper {
    public static final int COLOR_MODE_COLOR = 2;
    public static final int COLOR_MODE_MONOCHROME = 1;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;
    public static final int SCALE_MODE_FILL = 2;
    public static final int SCALE_MODE_FIT = 1;
    PrintHelperVersionImpl mImpl;

    public PrintHelper(Context context) {
        if (PrintHelper.systemSupportsPrint()) {
            this.mImpl = new PrintHelperKitkatImpl(context);
            return;
        }
        this.mImpl = new PrintHelperStubImpl();
    }

    public static boolean systemSupportsPrint() {
        if (Build.VERSION.SDK_INT >= 19) {
            return true;
        }
        return false;
    }

    public int getColorMode() {
        return this.mImpl.getColorMode();
    }

    public int getOrientation() {
        return this.mImpl.getOrientation();
    }

    public int getScaleMode() {
        return this.mImpl.getScaleMode();
    }

    public void printBitmap(String string2, Bitmap bitmap) {
        this.mImpl.printBitmap(string2, bitmap);
    }

    public void printBitmap(String string2, Uri uri) throws FileNotFoundException {
        this.mImpl.printBitmap(string2, uri);
    }

    public void setColorMode(int n) {
        this.mImpl.setColorMode(n);
    }

    public void setOrientation(int n) {
        this.mImpl.setOrientation(n);
    }

    public void setScaleMode(int n) {
        this.mImpl.setScaleMode(n);
    }

    private static final class PrintHelperKitkatImpl
    implements PrintHelperVersionImpl {
        private final PrintHelperKitkat mPrintHelper;

        PrintHelperKitkatImpl(Context context) {
            this.mPrintHelper = new PrintHelperKitkat(context);
        }

        @Override
        public int getColorMode() {
            return this.mPrintHelper.getColorMode();
        }

        @Override
        public int getOrientation() {
            return this.mPrintHelper.getOrientation();
        }

        @Override
        public int getScaleMode() {
            return this.mPrintHelper.getScaleMode();
        }

        @Override
        public void printBitmap(String string2, Bitmap bitmap) {
            this.mPrintHelper.printBitmap(string2, bitmap);
        }

        @Override
        public void printBitmap(String string2, Uri uri) throws FileNotFoundException {
            this.mPrintHelper.printBitmap(string2, uri);
        }

        @Override
        public void setColorMode(int n) {
            this.mPrintHelper.setColorMode(n);
        }

        @Override
        public void setOrientation(int n) {
            this.mPrintHelper.setOrientation(n);
        }

        @Override
        public void setScaleMode(int n) {
            this.mPrintHelper.setScaleMode(n);
        }
    }

    private static final class PrintHelperStubImpl
    implements PrintHelperVersionImpl {
        int mColorMode = 2;
        int mOrientation = 1;
        int mScaleMode = 2;

        private PrintHelperStubImpl() {
        }

        @Override
        public int getColorMode() {
            return this.mColorMode;
        }

        @Override
        public int getOrientation() {
            return this.mOrientation;
        }

        @Override
        public int getScaleMode() {
            return this.mScaleMode;
        }

        @Override
        public void printBitmap(String string2, Bitmap bitmap) {
        }

        @Override
        public void printBitmap(String string2, Uri uri) {
        }

        @Override
        public void setColorMode(int n) {
            this.mColorMode = n;
        }

        @Override
        public void setOrientation(int n) {
            this.mOrientation = n;
        }

        @Override
        public void setScaleMode(int n) {
            this.mScaleMode = n;
        }
    }

    static interface PrintHelperVersionImpl {
        public int getColorMode();

        public int getOrientation();

        public int getScaleMode();

        public void printBitmap(String var1, Bitmap var2);

        public void printBitmap(String var1, Uri var2) throws FileNotFoundException;

        public void setColorMode(int var1);

        public void setOrientation(int var1);

        public void setScaleMode(int var1);
    }

}

