package com.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;


public class Application extends android.app.Application {
    private static Context context;
    private static Handler handler;
    
    @Override
    public void onCreate() {
        super.onCreate();
        init();
        
    }
    
    private void init() {
        context = this;
        handler = new Handler(Looper.getMainLooper());
    }
    
    public static Context getContext() {
        return context;
    }
    
    public static Handler getHandler() {
        return handler;
    }
}
