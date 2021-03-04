package com.example.eom_rfid.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharePreference 工具类
 */
public final class SPUtil {

    /**
     * 保存用户名
     *
     * @param userName
     * @param context
     */
    public static void saveUserName(Context context, String userName) {
        SharedPreferences share = context.getSharedPreferences("LoginInfos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("UserName", userName);
        editor.apply();
    }

    /**
     * 获取用户名
     *
     * @param context
     * @return
     */
    public static String getUserName(Context context) {
        SharedPreferences sp = context.getSharedPreferences("LoginInfos", Context.MODE_PRIVATE);
        return sp.getString("UserName", "");
    }

    /**
     * 保存密码
     *
     * @param password
     * @param context
     */
    public static void savePassword(Context context, String password) {
        SharedPreferences share = context.getSharedPreferences("LoginInfos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("Password", password);
        editor.apply();
    }

    /**
     * 获取密码
     *
     * @param context
     * @return
     */
    public static String getPassword(Context context) {
        SharedPreferences sp = context.getSharedPreferences("LoginInfos", Context.MODE_PRIVATE);
        return sp.getString("Password", "");
    }

    /**
     * 保存token
     *
     * @param token
     * @param context
     */
    public static void saveToken(Context context, String token) {
        SharedPreferences share = context.getSharedPreferences("LoginInfos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("Token", token);
        editor.apply();
    }

    /**
     * 获取token
     *
     * @param context
     * @return
     */
    public static String getToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("LoginInfos", Context.MODE_PRIVATE);
        return sp.getString("Token", "");
    }


    /**
     * 保存记住密码
     *
     * @param isRemember
     * @param context
     */
    public static void saveIsRemember(Context context, boolean isRemember) {
        SharedPreferences share = context.getSharedPreferences("LoginInfos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putBoolean("IsRemember", isRemember);
        editor.apply();
    }

    /**
     * 获取记住密码
     *
     * @param context
     * @return
     */
    public static boolean getIsRemember(Context context) {
        SharedPreferences sp = context.getSharedPreferences("LoginInfos", Context.MODE_PRIVATE);
        return sp.getBoolean("IsRemember", false);
    }

    public static void clearInfo(Context context, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

}
