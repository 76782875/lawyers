package com.ucap.lawyers.tools;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.prefs.PreferencesFactory;

public class PrefUtils {
    /**
     * 保存(boolean)类型
     *
     * @param key     键
     * @param value   值
     * @param context 上下文
     */
    public static void putBoolean(String key, boolean value, Context context) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 获取(boolean)的值
     * param key     键
     *
     * @param defValue 默认值
     * @param context  上下文
     * @return 返回获取到的值，否则就返回defValue
     */
    public static boolean getBoolean(String key, boolean defValue, Context context) {
        return context.getSharedPreferences("config", Context.MODE_PRIVATE).getBoolean(key, defValue);
    }

    /**
     * 保存字符串
     *
     * @param key     键
     * @param value   值
     * @param context
     */
    public static void putString(String key, String value, Context context) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    /**
     * 获取字符串
     *
     * @param key      键
     * @param defValue 默认值
     * @param context
     * @return 返回键所对应的值，否则返回defValue
     */
    public static String getString(String key, String defValue, Context context) {
        return context.getSharedPreferences("config", Context.MODE_PRIVATE).getString(key, defValue);
    }

    /**
     * 删除
     *
     * @param key
     * @param context
     */
    public static void remove(String key, Context context) {
        context.getSharedPreferences("config", Context.MODE_PRIVATE).edit().remove(key).commit();
    }

    /**
     * 保存整型
     *
     * @param key
     * @param value
     * @param context
     */
    public static void putInt(String key, int value, Context context) {
        context.getSharedPreferences("config", Context.MODE_PRIVATE)
                .edit()
                .putInt(key, value)
                .commit();
    }

    /**
     * 获取整型
     *
     * @param key
     * @param defValue
     * @param context
     * @return
     */
    public static int getInt(String key, int defValue, Context context) {
        return context.getSharedPreferences("config", Context.MODE_PRIVATE)
                .getInt(key, defValue);
    }


}
