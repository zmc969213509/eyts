package com.guojianyiliao.eryitianshi.MyUtils.utlis;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SP存储工具类
 */
public class SpUtils {
    private static SharedPreferences sp;
    private static SpUtils instance = new SpUtils();

    private SpUtils() {
    }

    public static SpUtils getInstance(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences("ryts", Context.MODE_PRIVATE);
        }
        return instance;
    }

    /**
     * 保存数据-泛型
     *
     * @param key
     * @param value
     */
    public void putlong(String key,Long value){
        sp.edit().putLong(key,value).commit();
    }
    public long getlong(String key){
       return sp.getLong(key,0L);
    }
    public void put(String key, Object value) {
        if (value instanceof Integer) {
            sp.edit().putInt(key, (Integer) value).commit();
        } else if (value instanceof String) {
            sp.edit().putString(key, (String) value).commit();
        } else if (value instanceof Boolean) {
            sp.edit().putBoolean(key, (Boolean) value).commit();
        }
    }

    /**
     * 读取数据-泛型
     * 传入什么类型就返回什么类型
     *
     * @param key
     * @param defValue 泛型
     * @return
     */
    public <T> T get(String key, T defValue) {
        T t = null;
        if (defValue instanceof String || defValue == null) {
            String value = sp.getString(key, (String) defValue);
            t = (T) value;
        } else if (defValue instanceof Integer) {
            Integer value = sp.getInt(key, (Integer) defValue);
            t = (T) value;
        } else if (defValue instanceof Boolean) {
            Boolean value = sp.getBoolean(key, (Boolean) defValue);
            t = (T) value;
        }
        return t;
    }

    /**
     * 移除数据-泛型
     *
     * @param key
     */
    public void remove(String key) {
        sp.edit().remove(key).commit();
    }

    /**
     * 清除xml文件
     */
    public void clear() {
        sp.edit().clear().commit();
    }

}
