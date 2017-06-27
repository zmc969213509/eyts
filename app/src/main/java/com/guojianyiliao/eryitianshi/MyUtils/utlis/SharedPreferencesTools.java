package com.guojianyiliao.eryitianshi.MyUtils.utlis;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zmc on 2017/5/22.
 * 用于保存特定数据
 */

public class SharedPreferencesTools {

    /**
     * 保存用户是否是登录
     * @param context 上下文
     * @param strName userSave
     * @param key     userLogin
     */
    public static void UserLogin(Context context,String strName,String key){
        SharedPreferences preferences = context.getSharedPreferences(strName, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key,false).commit();
    }

    /**
     *  获取用户是否第一次登录
     * @param context 上下文
     * @param strName userSave
     * @param key     userLogin
     * @return        用户搜索内容 ， 分隔
     */
    public static boolean GetUsearisFirst(Context context,String strName,String key){
        SharedPreferences preferences = context.getSharedPreferences(strName,Context.MODE_PRIVATE);
        return preferences.getBoolean(key,true);
    }

    /**
     * 保存用户搜索记录
     * @param context 上下文
     * @param strName userSave
     * @param key     userSear
     * @param value   用户搜索内容 ， 分隔
     */
    public static void SaveUserSear(Context context,String strName,String key,String value){
        SharedPreferences preferences = context.getSharedPreferences(strName, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).commit();
    }

    /**
     *  取出用户保存的数据
     * @param context 上下文
     * @param strName userSave
     * @param key     userSear
     * @return        用户搜索内容 ， 分隔
     */
    public static String GetUsearSear(Context context,String strName,String key){
        SharedPreferences preferences = context.getSharedPreferences(strName,Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }
    /**
     * 保存uid
     * @param context 上下文
     * @param strName userSave
     * @param key     userId
     * @param value   uid
     */
    public static void SaveUserId(Context context,String strName,String key,String value){
        SharedPreferences preferences = context.getSharedPreferences(strName, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).commit();
    }

    /**
     *  取出用户uid
     * @param context 上下文
     * @param strName userSave
     * @param key     userId
     * @return        uid
     */
    public static String GetUsearId(Context context,String strName,String key){
        SharedPreferences preferences = context.getSharedPreferences(strName,Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    /**
     * 保存登录的用户信息
     * @param context  上下文
     * @param strName  userSave
     * @param key      userInfo
     * @param value    实体 UserInfoLogin 的 json 格式
     */
    public static void SaveUserInfo(Context context,String strName,String key,String value){
        SharedPreferences preferences = context.getSharedPreferences(strName, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).commit();
    }

    /**
     * 获取保存的登录用户信息
     * @param context  上下文
     * @param strName  userSave
     * @param key      userInfo
     * @return         实体 UserInfoLogin 的 json 格式
     */
    public static String GetUsearInfo(Context context,String strName,String key){
        SharedPreferences preferences = context.getSharedPreferences(strName,Context.MODE_PRIVATE);
        return preferences.getString(key,"");
    }

    /**
     * 保存用户登录类型
     * @param context  上下文
     * @param strName  userSave
     * @param key      userType
     * @param value
     */
    public static void SaveLoginType(Context context,String strName,String key,String value){
        SharedPreferences preferences = context.getSharedPreferences(strName, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).commit();
    }

    /**
     * 获取用户登录类型
     * @param context  上下文
     * @param strName  userSave
     * @param key      userType
     * @return         用户登录类型
     */
    public static String GetLoginType(Context context,String strName,String key){
        SharedPreferences preferences = context.getSharedPreferences(strName,Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    /**
     * 保存用户登录数据   第三方登录保存 uid   账号密码登录保存 账号密码用,分隔
     * @param context  上下文
     * @param strName  userSave
     * @param key      userLoginData
     * @param value
     */
    public static void SaveLoginData(Context context,String strName,String key,String value){
        SharedPreferences preferences = context.getSharedPreferences(strName, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).commit();
    }

    /**
     * 获取保存的用户登录数据
     * @param context  上下文
     * @param strName  userSave
     * @param key      userLoginData
     * @return
     */
    public static String GetLoginData(Context context,String strName,String key){
        SharedPreferences preferences = context.getSharedPreferences(strName,Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    /**
     * 保存用户是否是登录状态
     * @param context  上下文
     * @param strName  userSave
     * @param key      userLoginStatus
     * @param value
     */
    public static void SaveLoginStatus(Context context,String strName,String key,boolean value){
        SharedPreferences preferences = context.getSharedPreferences(strName, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key, value).commit();
    }

    /**
     * 获取用户登录状态
     * @param context  上下文
     * @param strName  userSave
     * @param key      userLoginStatus
     * @return
     */
    public static  boolean GetLoginStatus(Context context,String strName,String key){
        SharedPreferences preferences = context.getSharedPreferences(strName,Context.MODE_PRIVATE);
        return preferences.getBoolean(key, true);
    }

    /**
     * 保存所有疾病
     * @param context  上下文
     * @param strName  userSave
     * @param key      allDis
     * @param value    所有疾病
     */
    public static void SaveAllDis(Context context,String strName,String key,String value){
        SharedPreferences preferences = context.getSharedPreferences(strName, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).commit();
    }

    /**
     * 获取所有疾病
     * @param context  上下文
     * @param strName  userSave
     * @param key      allDis
     * @return         所有疾病
     */
    public static String GetAllDis(Context context,String strName,String key){
        SharedPreferences preferences = context.getSharedPreferences(strName,Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    /**
     * 保存所有医生
     * @param context  上下文
     * @param strName  userSave
     * @param key      allDoc
     * @param value    所有医生
     */
    public static void SaveAllDoc(Context context,String strName,String key,String value){
        SharedPreferences preferences = context.getSharedPreferences(strName, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).commit();
    }

    /**
     * 获取所有医生
     * @param context  上下文
     * @param strName  userSave
     * @param key      allDoc
     * @return         所有医生
     */
    public static String GetAllDoc(Context context,String strName,String key){
        SharedPreferences preferences = context.getSharedPreferences(strName,Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    /**
     * 保存就诊人数
     * @param context  上下文
     * @param strName  userSave
     * @param key      userNum
     * @param num
     */
    public static void SaveUserNum(Context context,String strName,String key,int num){
        SharedPreferences preferences = context.getSharedPreferences(strName, Context.MODE_PRIVATE);
        preferences.edit().putInt(key,num).commit();
    }

    /**
     * 获取保存就诊人数
     * @param context  上下文
     * @param strName  userSave
     * @param key      userNum
     * @return         所有医生
     */
    public static int GetUserNum(Context context,String strName,String key){
        SharedPreferences preferences = context.getSharedPreferences(strName,Context.MODE_PRIVATE);
        return preferences.getInt(key, 100);
    }

}
