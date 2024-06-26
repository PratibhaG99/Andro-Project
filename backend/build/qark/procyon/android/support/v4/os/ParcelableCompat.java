// 
// Decompiled by Procyon v1.0-SNAPSHOT
// 

package android.support.v4.os;

import android.os.Parcel;
import android.os.Build$VERSION;
import android.os.Parcelable$Creator;

public class ParcelableCompat
{
    public static <T> Parcelable$Creator<T> newCreator(final ParcelableCompatCreatorCallbacks<T> parcelableCompatCreatorCallbacks) {
        if (Build$VERSION.SDK_INT >= 13) {
            ParcelableCompatCreatorHoneycombMR2Stub.instantiate(parcelableCompatCreatorCallbacks);
        }
        return (Parcelable$Creator<T>)new CompatCreator((ParcelableCompatCreatorCallbacks<Object>)parcelableCompatCreatorCallbacks);
    }
    
    static class CompatCreator<T> implements Parcelable$Creator<T>
    {
        final ParcelableCompatCreatorCallbacks<T> mCallbacks;
        
        public CompatCreator(final ParcelableCompatCreatorCallbacks<T> mCallbacks) {
            this.mCallbacks = mCallbacks;
        }
        
        public T createFromParcel(final Parcel parcel) {
            return this.mCallbacks.createFromParcel(parcel, null);
        }
        
        public T[] newArray(final int n) {
            return this.mCallbacks.newArray(n);
        }
    }
}
