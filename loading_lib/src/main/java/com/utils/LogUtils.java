package com.utils;

import android.util.Log;

import java.util.Iterator;
import java.util.List;

/***
 * Log工具类
 */
public class LogUtils {
    private static String TAG = "--utils--";
    private static boolean IS_DEBUG = true;
    
    public static void setLogTag(String tag) {
        TAG = tag;
    }
    
    public static void setIsDebug(boolean b) {
        IS_DEBUG = b;
    }
    
    public static void d(String text) {
        if (IS_DEBUG) {
            Log.d(TAG, text == null ? "null" : text);
        }
    }
    
    public static void d(List<Object> list) {
        if (IS_DEBUG) {
            Iterator iterator = list.iterator();
            Log.d(TAG, "------------------(list begin)--------------------");
            while (iterator.hasNext()) {
                Log.d(TAG, iterator.next().toString());
            }
            Log.d(TAG, "------------------(list   end)--------------------");
        }
    }
    
    public static void i(String text) {
        if (IS_DEBUG) {
            Log.i(TAG, text == null ? "null" : text);
        }
    }
    
    public static void i(List<Object> list) {
        if (IS_DEBUG) {
            Iterator iterator = list.iterator();
            Log.d(TAG, "------------------(list begin)--------------------");
            while (iterator.hasNext()) {
                Log.i(TAG, iterator.next().toString());
            }
            Log.d(TAG, "------------------(list   end)--------------------");
        }
        
    }
    
    public static void e(String text) {
        if (IS_DEBUG) {
            Log.e(TAG, text == null ? "null" : text);
        }
    }
    
    public static void e(List<Object> list) {
        if (IS_DEBUG) {
            Iterator iterator = list.iterator();
            Log.d(TAG, "------------------(list begin)--------------------");
            while (iterator.hasNext()) {
                Log.e(TAG, iterator.next().toString());
            }
            Log.d(TAG, "------------------(list   end)--------------------");
        }
    }
    
    public static void w(String text) {
        if (IS_DEBUG) {
            Log.w(TAG, text == null ? "null" : text);
        }
    }
    
    public static void w(List<Object> list) {
        if (IS_DEBUG) {
            Iterator iterator = list.iterator();
            Log.d(TAG, "------------------(list begin)--------------------");
            while (iterator.hasNext()) {
                Log.w(TAG, iterator.next().toString());
            }
            Log.d(TAG, "------------------(list   end)--------------------");
        }
    }
    
}
