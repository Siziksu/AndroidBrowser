package com.siziksu.browser.ui.common.router;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public final class RouterHelper {

    private static final String TAG = "RouterHelper";

    private String action;
    private Uri uri;
    private Bundle bundle;
    private boolean clearBackStack;
    private boolean finishingCurrent;
    private boolean forResult;
    private int code;
    private boolean broadcast;
    private String receiverPermission;
    private String packageName;
    private String packageStartingWith;
    private int enterAnim;
    private int exitAnim;
    private boolean animate;

    @Inject
    public RouterHelper() {}

    void route(Activity activity, Class<?> clazz) {
        if (activity == null || clazz == null) {
            Log.e(TAG, "No valid activity or destination class");
            reset();
            return;
        }
        Intent intent = new Intent(activity, clazz);
        go(activity, intent, null);
    }

    void route(Activity activity, String action) {
        if (activity == null || TextUtils.isEmpty(action)) {
            Log.e(TAG, "No valid activity or action");
            reset();
            return;
        }
        Intent intent = new Intent(action);
        go(activity, intent, null);
    }

    void route(Activity activity, String action, Uri uri) {
        if (activity == null || TextUtils.isEmpty(action) || uri == null) {
            Log.e(TAG, "No valid activity or action or uri");
            reset();
            return;
        }
        Intent intent = new Intent(action, uri);
        go(activity, intent, null);
    }

    void route(Activity activity, Class<?> clazz, Bundle options) {
        if (activity == null || clazz == null) {
            Log.e(TAG, "No valid activity or destination class");
            reset();
            return;
        }
        Intent intent = new Intent(activity, clazz);
        go(activity, intent, options);
    }

    RouterHelper sendBroadcast() {
        broadcast = true;
        return this;
    }

    RouterHelper sendBroadcast(String receiverPermission) {
        broadcast = true;
        this.receiverPermission = receiverPermission;
        return this;
    }

    RouterHelper forResult(int code) {
        this.code = code;
        forResult = true;
        return this;
    }

    RouterHelper clearBackStack() {
        clearBackStack = true;
        return this;
    }

    RouterHelper finishingCurrent() {
        finishingCurrent = true;
        return this;
    }

    /**
     * Specify an explicit transition animation to perform next.
     * <p>
     * <p>As of {@link android.os.Build.VERSION_CODES#JELLY_BEAN} an alternative
     * to using this with starting activities is to supply the desired animation
     * information through a {@link ActivityOptions} bundle to
     * startActivity(Intent, Bundle) or a related function.  This allows
     * you to specify a custom animation even when starting an activity from
     * outside the context of the current top activity.
     *
     * @param enterAnim A resource ID of the animation resource to use for
     *                  the incoming activity.  Use 0 for no animation.
     * @param exitAnim  A resource ID of the animation resource to use for
     *                  the outgoing activity.  Use 0 for no animation.
     */
    RouterHelper animateTransition(int enterAnim, int exitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
        animate = true;
        return this;
    }

    /**
     * (Usually optional) Set an explicit application package name that limits
     * the components this Intent will resolve to.  If left to the default
     * value of null, all components in all applications will considered.
     * If non-null, the Intent can only match the components in the given
     * application package.
     *
     * @param packageName The name of the application package to handle the
     *                    intent, or null to allow any application package.
     *
     * @return Returns the same Intent object, for chaining multiple calls
     * into a single statement.
     */
    RouterHelper setPackage(String packageName) {
        this.packageName = packageName;
        return this;
    }

    RouterHelper setPackageIfExistsAndStartsWith(String packageStartingWith) {
        this.packageStartingWith = packageStartingWith;
        return this;
    }

    /**
     * Add a set of extended data to the intent.  The keys must include a package
     * prefix, for example the app com.android.contacts would use names
     * like "com.android.contacts.ShowAll".
     *
     * @param bundle The Bundle of extras to add to this intent.
     */
    RouterHelper putExtras(Bundle bundle) {
        if (this.bundle == null) {
            this.bundle = new Bundle();
        }
        this.bundle.putAll(bundle);
        return this;
    }

    private void initBundle() {
        if (bundle == null) {
            bundle = new Bundle();
        }
    }

    /**
     * Inserts a Boolean value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a boolean
     */
    RouterHelper putBoolean(@Nullable String key, boolean value) {
        initBundle();
        bundle.putBoolean(key, value);
        return this;
    }

    /**
     * Inserts an int value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value an int
     */
    RouterHelper putInt(@Nullable String key, int value) {
        initBundle();
        bundle.putInt(key, value);
        return this;
    }

    /**
     * Inserts a long value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a long
     */
    RouterHelper putLong(@Nullable String key, long value) {
        initBundle();
        bundle.putLong(key, value);
        return this;
    }

    /**
     * Inserts a double value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a double
     */
    RouterHelper putDouble(@Nullable String key, double value) {
        initBundle();
        bundle.putDouble(key, value);
        return this;
    }

    /**
     * Inserts a String value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a String, or null
     */
    RouterHelper putString(@Nullable String key, @Nullable String value) {
        initBundle();
        bundle.putString(key, value);
        return this;
    }

    /**
     * Inserts a boolean array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a boolean array object, or null
     */
    RouterHelper putBooleanArray(@Nullable String key, @Nullable boolean[] value) {
        initBundle();
        bundle.putBooleanArray(key, value);
        return this;
    }

    /**
     * Inserts an int array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value an int array object, or null
     */
    RouterHelper putIntArray(@Nullable String key, @Nullable int[] value) {
        initBundle();
        bundle.putIntArray(key, value);
        return this;
    }

    /**
     * Inserts a long array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a long array object, or null
     */
    RouterHelper putLongArray(@Nullable String key, @Nullable long[] value) {
        initBundle();
        bundle.putLongArray(key, value);
        return this;
    }

    /**
     * Inserts a double array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a double array object, or null
     */
    RouterHelper putDoubleArray(@Nullable String key, @Nullable double[] value) {
        initBundle();
        bundle.putDoubleArray(key, value);
        return this;
    }

    /**
     * Inserts a String array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a String array object, or null
     */
    RouterHelper putStringArray(@Nullable String key, @Nullable String[] value) {
        initBundle();
        bundle.putStringArray(key, value);
        return this;
    }

    /**
     * Inserts a byte value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a byte
     */
    RouterHelper putByte(@Nullable String key, byte value) {
        initBundle();
        bundle.putByte(key, value);
        return this;
    }

    /**
     * Inserts a char value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a char
     */
    RouterHelper putChar(@Nullable String key, char value) {
        initBundle();
        bundle.putChar(key, value);
        return this;
    }

    /**
     * Inserts a short value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a short
     */
    RouterHelper putShort(@Nullable String key, short value) {
        initBundle();
        bundle.putShort(key, value);
        return this;
    }

    /**
     * Inserts a float value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a float
     */
    RouterHelper putFloat(@Nullable String key, float value) {
        initBundle();
        bundle.putFloat(key, value);
        return this;
    }

    /**
     * Inserts a CharSequence value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a CharSequence, or null
     */
    RouterHelper putCharSequence(@Nullable String key, @Nullable CharSequence value) {
        initBundle();
        bundle.putCharSequence(key, value);
        return this;
    }

    /**
     * Inserts a Parcelable value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a Parcelable object, or null
     */
    RouterHelper putParcelable(@Nullable String key, @Nullable Parcelable value) {
        initBundle();
        bundle.putParcelable(key, value);
        return this;
    }

    /**
     * Inserts a Size value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a Size object, or null
     */
    @RequiresApi(21)
    RouterHelper putSize(@Nullable String key, @Nullable Size value) {
        initBundle();
        bundle.putSize(key, value);
        return this;
    }

    /**
     * Inserts a SizeF value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a SizeF object, or null
     */
    @RequiresApi(21)
    RouterHelper putSizeF(@Nullable String key, @Nullable SizeF value) {
        initBundle();
        bundle.putSizeF(key, value);
        return this;
    }

    /**
     * Inserts an array of Parcelable values into the mapping of this Bundle,
     * replacing any existing value for the given key.  Either key or value may
     * be null.
     *
     * @param key   a String, or null
     * @param value an array of Parcelable objects, or null
     */
    RouterHelper putParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
        initBundle();
        bundle.putParcelableArray(key, value);
        return this;
    }

    /**
     * Inserts a SparceArray of Parcelable values into the mapping of this
     * Bundle, replacing any existing value for the given key.  Either key
     * or value may be null.
     *
     * @param key   a String, or null
     * @param value a SparseArray of Parcelable objects, or null
     */
    RouterHelper putSparseParcelableArray(@Nullable String key, @Nullable SparseArray<? extends Parcelable> value) {
        initBundle();
        bundle.putSparseParcelableArray(key, value);
        return this;
    }

    /**
     * Inserts an ArrayList<Integer> value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value an ArrayList<Integer> object, or null
     */
    RouterHelper putIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        initBundle();
        bundle.putIntegerArrayList(key, value);
        return this;
    }

    /**
     * Inserts an ArrayList<String> value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value an ArrayList<String> object, or null
     */
    RouterHelper putStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        initBundle();
        bundle.putStringArrayList(key, value);
        return this;
    }

    /**
     * Inserts an ArrayList<CharSequence> value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value an ArrayList<CharSequence> object, or null
     */
    RouterHelper putCharSequenceArrayList(@Nullable String key, @Nullable ArrayList<CharSequence> value) {
        initBundle();
        bundle.putCharSequenceArrayList(key, value);
        return this;
    }

    /**
     * Inserts a Serializable value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a Serializable object, or null
     */
    RouterHelper putSerializable(@Nullable String key, @Nullable Serializable value) {
        initBundle();
        bundle.putSerializable(key, value);
        return this;
    }

    /**
     * Inserts a byte array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a byte array object, or null
     */
    RouterHelper putByteArray(@Nullable String key, @Nullable byte[] value) {
        initBundle();
        bundle.putByteArray(key, value);
        return this;
    }

    /**
     * Inserts a short array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a short array object, or null
     */
    RouterHelper putShortArray(@Nullable String key, @Nullable short[] value) {
        initBundle();
        bundle.putShortArray(key, value);
        return this;
    }

    /**
     * Inserts a char array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a char array object, or null
     */
    RouterHelper putCharArray(@Nullable String key, @Nullable char[] value) {
        initBundle();
        bundle.putCharArray(key, value);
        return this;
    }

    /**
     * Inserts a float array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a float array object, or null
     */
    RouterHelper putFloatArray(@Nullable String key, @Nullable float[] value) {
        initBundle();
        bundle.putFloatArray(key, value);
        return this;
    }

    /**
     * Inserts a CharSequence array value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a CharSequence array object, or null
     */
    RouterHelper putCharSequenceArray(@Nullable String key, @Nullable CharSequence[] value) {
        initBundle();
        bundle.putCharSequenceArray(key, value);
        return this;
    }

    /**
     * Inserts a Bundle value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a Bundle object, or null
     */
    RouterHelper putBundle(@Nullable String key, @Nullable Bundle value) {
        initBundle();
        bundle.putBundle(key, value);
        return this;
    }

    /**
     * Inserts an {@link IBinder} value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     * <p>
     * <p class="note">You should be very careful when using this function.  In many
     * places where Bundles are used (such as inside of Intent objects), the Bundle
     * can live longer inside of another process than the process that had originally
     * created it.  In that case, the IBinder you supply here will become invalid
     * when your process goes away, and no longer usable, even if a new process is
     * created for you later on.</p>
     *
     * @param key   a String, or null
     * @param value an IBinder object, or null
     */
    @RequiresApi(18)
    RouterHelper putBinder(@Nullable String key, @Nullable IBinder value) {
        initBundle();
        bundle.putBinder(key, value);
        return this;
    }

    /**
     * Set the general action to be performed.
     *
     * @param action An action name, such as ACTION_VIEW.  Application-specific
     *               actions should be prefixed with the vendor's package name.
     */
    RouterHelper setAction(String action) {
        this.action = action;
        return this;
    }

    /**
     * Set the data this intent is operating on.  This method automatically
     * clears any type that was previously set.
     * <p>
     * <p><em>Note: scheme matching in the Android framework is
     * case-sensitive, unlike the formal RFC. As a result,
     * you should always write your Uri with a lower case scheme
     * to ensure that the scheme is converted to lower case.</em>
     *
     * @param uri The Uri of the data this intent is now targeting.
     *
     * @return Returns the same Intent object, for chaining multiple calls
     * into a single statement.
     */
    RouterHelper setData(Uri uri) {
        this.uri = uri;
        return this;
    }

    private void go(Activity activity, Intent intent, Bundle options) {
        if (intent == null) {
            return;
        }
        if (packageName != null) {
            intent.setPackage(packageName);
            packageName = null;
        }
        if (packageStartingWith != null) {
            setPackageIfExistsAndStartsWith(activity, intent);
            packageStartingWith = null;
        }
        if (action != null) {
            intent.setAction(action);
            action = null;
        }
        if (uri != null) {
            intent.setData(uri);
            uri = null;
        }
        if (bundle != null) {
            intent.putExtras(bundle);
            bundle = null;
        }
        if (broadcast) {
            activity.sendBroadcast(intent, receiverPermission);
            broadcast = false;
            return;
        }
        if (clearBackStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            clearBackStack = false;
        }
        if (forResult) {
            activity.startActivityForResult(intent, code);
            animate(activity);
            code = 0;
            forResult = false;
        } else {
            if (options != null) {
                activity.startActivity(intent, options);
            } else {
                activity.startActivity(intent);
            }
            animate(activity);
        }
        if (finishingCurrent) {
            activity.finish();
            finishingCurrent = false;
        }
    }

    private void setPackageIfExistsAndStartsWith(Activity activity, Intent intent) {
        List<ResolveInfo> matches = activity.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith(packageStartingWith)) {
                intent.setPackage(info.activityInfo.packageName);
            }
        }
    }

    private void animate(Activity activity) {
        if (!animate) {
            return;
        }
        activity.overridePendingTransition(enterAnim, exitAnim);
        animate = false;
    }

    private void reset() {
        action = null;
        uri = null;
        bundle = null;
        clearBackStack = false;
        finishingCurrent = false;
        forResult = false;
        code = 0;
        broadcast = false;
        receiverPermission = null;
        packageName = null;
        packageStartingWith = null;
        enterAnim = 0;
        exitAnim = 0;
        animate = false;
    }
}
