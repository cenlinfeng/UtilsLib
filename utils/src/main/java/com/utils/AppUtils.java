package com.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/***
 * APP工具类
 */
public class AppUtils {
    private AppUtils() {
    }
    
    /***
     * 获取包名
     * @param context 上下文 {@link android.content.Context }
     * @return 返回包名 {@link String}
     */
    public static String getPackName(Context context) {
        return context.getPackageName();
    }
    
    
    /***
     * 获取版本名称
     * @param context 上下文 {@link Context}
     * @return 版本名称 {@link String}
     */
    public static String getVersionName(Context context) {
        
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackName(context), PackageManager.GET_ACTIVITIES);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    /**
     * 获取版本号
     *
     * @param context 上下文 {@link Context}
     * @return 版本号 {@link Integer}
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackName(context), PackageManager.GET_ACTIVITIES);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    /***
     *  获取已安装的包集合
     * @param context 上下文 {@link Context}
     * @return List<PackageInfo> {@link List<PackageInfo>}
     */
    public static List<PackageInfo> getInstallPackages(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<PackageInfo> packageInfoList = new ArrayList<PackageInfo>();
        for (int i = 0; i < packageInfos.size(); i++) {
            if ((packageInfos.get(i).applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                packageInfoList.add(packageInfos.get(i));
            }
        }
        return packageInfoList;
    }
    
    /**
     * 获取图标
     *
     * @param context 上下文 {@link Context}
     * @return 返回 {@link Drawable}
     */
    public static Drawable getApplicationIcon(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.applicationInfo.loadIcon(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 启动安装应用程序
     *
     * @param activity
     * @param path     应用程序路径
     */
    public static void installApk(Activity activity, String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(path)),
                "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }
    
}
