package xyz.ismailnurudeen.prefdb;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.List;
import java.util.Set;

import xyz.ismailnurudeen.prefdb.utils.BitmapToStringConverter;
import xyz.ismailnurudeen.prefdb.utils.VUtils;

public class PrefDB {
    private Context mContext;
    private String mName;
    private Gson mGson;
    private SharedPreferences mSharedPref;

    /**
     * This will initialize an instance of PrefDB
     *
     * @param context your current context.
     * @param name    name of preferences file (preferenceName.xml)
     */
    public PrefDB(Context context, String name) {
        mContext = context;
        mName = name;
        mGson = new Gson();
        mSharedPref = mContext.getSharedPreferences(mName, Context.MODE_PRIVATE);
    }

    public boolean insertString(String key, String value) {
        return mSharedPref.edit().putString(key, value).commit();
    }

    public boolean insertBoolean(String key, Boolean value) {
        return mSharedPref.edit().putBoolean(key, value).commit();
    }

    public boolean insertFloat(String key, float value) {
        return mSharedPref.edit().putFloat(key, value).commit();
    }

    public boolean insertInt(String key, int value) {
        return mSharedPref.edit().putInt(key, value).commit();
    }

    public boolean insertLong(String key, long value) {
        return mSharedPref.edit().putLong(key, value).commit();
    }

    public boolean insertStringSet(String key, Set<String> value) {
        return mSharedPref.edit().putStringSet(key, value).commit();
    }

    public boolean insertStringArray(String key, String[] value) {
        return mSharedPref.edit().putString(key, mGson.toJson(value)).commit();
    }

    public boolean insertIntArray(String key, Integer[] value) {
        return mSharedPref.edit().putString(key, mGson.toJson(value)).commit();
    }

    public boolean insertLongArray(String key, Long[] value) {
        return mSharedPref.edit().putString(key, mGson.toJson(value)).commit();
    }

    public boolean insertShortArray(String key, Short[] value) {
        return mSharedPref.edit().putString(key, mGson.toJson(value)).commit();
    }
    public boolean insertFloatArray(String key, Float[] value) {
        return mSharedPref.edit().putString(key, mGson.toJson(value)).commit();
    }

    public boolean insertList(String key, List<Object> value) {
        return mSharedPref.edit().putString(key, mGson.toJson(value)).commit();
    }

    public boolean insertObject(String key, Object value) {
        return mSharedPref.edit().putString(key, mGson.toJson(value)).commit();
    }

    public boolean insertDrawable(String key, Drawable drawable) {
        Bitmap bitmap = new VUtils(mContext).getBitMapFromDrawable(drawable);
        String imgStr = BitmapToStringConverter.convertToString(bitmap);
        return mSharedPref.edit().putString(key, imgStr).commit();
    }

    public boolean insertDrawable(String key, int drawableRes) {
        Bitmap bitmap = new VUtils(mContext).getBitMapFromDrawableRes(drawableRes);
        String imgStr = BitmapToStringConverter.convertToString(bitmap);
        return mSharedPref.edit().putString(key, imgStr).commit();
    }

    public boolean insertBitmap(String key, Bitmap value) {
        String imgStr = BitmapToStringConverter.convertToString(value);
        return mSharedPref.edit().putString(key, imgStr).commit();
    }

    public String readString(String key, String defaultValue) {
        return mSharedPref.getString(key, defaultValue);
    }

    public int readInt(String key, int defaultValue) {
        return mSharedPref.getInt(key, defaultValue);
    }

    public boolean readBoolean(String key, Boolean defaultValue) {
        return mSharedPref.getBoolean(key, defaultValue);
    }

    public long readLong(String key, long defaultValue) {
        return mSharedPref.getLong(key, defaultValue);
    }

    public float readFloat(String key, float defaultValue) {
        return mSharedPref.getFloat(key, defaultValue);
    }

    public Set<String> readStringSet(String key, Set<String> defaultValue) {
        return mSharedPref.getStringSet(key, defaultValue);
    }

    public Drawable readDrawable(String key, int defaultDrawable) {
        VUtils vUtils = new VUtils(mContext);
        String imgStr = mSharedPref.getString(key, "");
        if (!imgStr.equalsIgnoreCase("")) {
            return vUtils.getDrawableFromBitmap(BitmapToStringConverter.convertToBitmap(imgStr));
        }
        return vUtils.getDrawableFromBitmap(vUtils.getBitMapFromDrawableRes(defaultDrawable));
    }

    public Drawable readDrawable(String key, Drawable defaultDrawable) {
        String imgStr = mSharedPref.getString(key, "");
        if (!imgStr.equalsIgnoreCase("")) {
            return new VUtils(mContext).getDrawableFromBitmap(BitmapToStringConverter.convertToBitmap(imgStr));
        }
        return defaultDrawable;
    }

    public Bitmap readBitmap(String key, Bitmap defaultBitmap) {
        String imgStr = mSharedPref.getString(key, "");
        if (!imgStr.equalsIgnoreCase("")) {
            return BitmapToStringConverter.convertToBitmap(imgStr);
        }
        return defaultBitmap;
    }

    public Object readObject(String key, Object defaultValue) {
        String objectStr = mSharedPref.getString(key, "");
        if (!objectStr.equalsIgnoreCase("")) {
            return mGson.fromJson(objectStr, defaultValue.getClass());
        }
        return defaultValue;
    }

    public List readList(String key, List<Object> defaultValue) {
        String objectStr = mSharedPref.getString(key, "");
        if (!objectStr.equalsIgnoreCase("")) {
            return mGson.fromJson(objectStr, defaultValue.getClass());
        }
        return defaultValue;
    }

    public String[] readStringArray(String key, String[] defaultValue) {
        String objectStr = mSharedPref.getString(key, "");
        if (!objectStr.equalsIgnoreCase("")) {
            return mGson.fromJson(objectStr, defaultValue.getClass());
        }
        return defaultValue;
    }

    public int[] readIntArray(String key, int[] defaultValue) {
        String objectStr = mSharedPref.getString(key, "");
        if (!objectStr.equalsIgnoreCase("")) {
            return mGson.fromJson(objectStr, defaultValue.getClass());
        }
        return defaultValue;
    }

    public long[] readLongArray(String key, long[] defaultValue) {
        String objectStr = mSharedPref.getString(key, "");
        if (!objectStr.equalsIgnoreCase("")) {
            return mGson.fromJson(objectStr, defaultValue.getClass());
        }
        return defaultValue;
    }

    public short[] readShortArray(String key, short[] defaultValue) {
        String objectStr = mSharedPref.getString(key, "");
        if (!objectStr.equalsIgnoreCase("")) {
            return mGson.fromJson(objectStr, defaultValue.getClass());
        }
        return defaultValue;
    }

    public float[] readFloatArray(String key, float[] defaultValue) {
        String objectStr = mSharedPref.getString(key, "");
        if (!objectStr.equalsIgnoreCase("")) {
            return mGson.fromJson(objectStr, defaultValue.getClass());
        }
        return defaultValue;
    }

    public boolean deleteAll() {
        return mSharedPref.edit().clear().commit();
    }

    public void deleteValue(String key) {
        mSharedPref.edit().remove(key).apply();
    }

}
