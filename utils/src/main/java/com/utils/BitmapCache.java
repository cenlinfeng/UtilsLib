package com.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/***
 * Bitmap缓存类，使用LRUCache缓存机制
 *  {@link BitmapCache#getInstance()#putCache(String, Bitmap);} &&
 *  {@link BitmapCache#getInstance()#getCache(String)}
 *
 *  自定义缓存大小
 *  {@link BitmapCache#getInstance(int)}
 */
public class BitmapCache extends LruCache<String, Bitmap> {
    
    private static BitmapCache instance;
    
    public static BitmapCache getInstance() {
        if (instance == null) {
            synchronized (BitmapCache.class) {
                if (instance == null) {
                    int maxMemory = (int) (Runtime.getRuntime().totalMemory() / 1024);
                    int maxSize = maxMemory / 8;
                    instance = new BitmapCache(maxSize);
                }
            }
        }
        return instance;
    }
    
    public static BitmapCache getInstance(int cacheSize) {
        if (instance == null) {
            synchronized (BitmapCache.class) {
                if (instance == null) {
                    instance = new BitmapCache(cacheSize);
                }
            }
        }
        return instance;
    }
    
    
    private BitmapCache(int maxSize) {
        super(maxSize);
    }
    
    
    public void putCache(String key, Bitmap value) {
        if (getCache(key) == null) {
            instance.put(key, value);
        }
    }
    
    public Bitmap getCache(String key) {
        return instance.get(key);
    }
    
}
