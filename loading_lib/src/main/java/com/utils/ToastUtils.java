package com.utils;

import android.content.Context;
import android.support.annotation.IntDef;
import android.widget.Toast;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/***
 * 吐司工具类
 */
public class ToastUtils {
    private static Toast toast;
    
    public static void showToast(Context context, String text) {
        if (toast == null) {
            synchronized (ToastUtils.class) {
                if (toast == null) {
                    toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                }
            }
        }
        toast.setText(text);
        toast.show();
    }
    
    
    @Documented
    @IntDef(value = {
            Toast.LENGTH_SHORT,
            Toast.LENGTH_LONG
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }
    
    /**
     * 显示吐司带显示时间
     *
     * @param context  上下文
     * @param text     吐司的文字
     * @param duration 持续时间 Toast.LENGTH_LONG 或 Toast.LENGTH_SHORT
     */
    public static void showToast(Context context, String text, @Duration int duration) {
        if (duration != Toast.LENGTH_SHORT && duration != Toast.LENGTH_LONG) {
            throw new UtilsThrow("duration value is 'Toast.LENGTH_LONG' or 'Toast.LENGTH_SHORT'");
        }
        
        if (toast == null) {
            synchronized (ToastUtils.class) {
                if (toast == null) {
                    toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                }
            }
        }
        toast.setText(text);
        toast.show();
    }
}
