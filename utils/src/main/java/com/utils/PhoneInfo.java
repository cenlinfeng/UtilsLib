package com.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

public class PhoneInfo {
    private static final int READ_PHONE_INFO = 0x22;
    
    /***
     * 获取手机号码
     * @param context   Activity
     * @return 返回手机号码，6.0需要获取权限
     */
    public static String getPhoneNumber(Activity context) {
        TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return mTm.getDeviceId() == null ? "" : mTm.getDeviceId().replace(" ", "");
        } else {
            //没有获得权限
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_INFO);
        }
        return mTm.getDeviceId() == null ? "" : mTm.getDeviceId().replace(" ", "");
    }
    
    /***
     * 获取设备id
     * @param context Activity
     * @return 返回手机型号，6.0需要获取权限
     */
    public static String getDeviceId(Activity context) {
        TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return mTm.getDeviceId();//获取智能设备唯一编号
        }
        return mTm.getDeviceId();
    }
    
    /***
     * 获取手机品牌
     * @return 返回手机品牌
     */
    public static String getPhoneBrand() {
        return android.os.Build.BRAND;//手机品牌
    }
    
    /**
     * 获取手机型号
     *
     * @return 返回手机型号
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL; // 手机型号
    }
}
