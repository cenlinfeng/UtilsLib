package com.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/***
 * 图片路径转Base64编码
 * Base64编码转图片
 */
public class Base64Utils {
    public Base64Utils() {
    }
    
    /***
     * 文件路径Base64编码返回字符串
     * @param file 图片路径 {@link String}
     * @return 返回经过Base64编码的字符串 {@link String}
     */
    public static String getBase64ForString(String file) {
        byte[] data = null;
        InputStream inputStream = null;
        String s = null;
        try {
            if (TextUtils.isEmpty(file)) {
                return null;
            }
            inputStream = new FileInputStream(file);
            data = new byte[inputStream.available()];
            inputStream.read();
            s = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return s;
    }
    
    /***
     * Bitmap Base64 编码返回字符串
     * @param bitmap 图片Bitmap {@link Bitmap}
     * @return 返回经过Base64编码的字符串 {@link String}
     */
    public static String getBase64ForString(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                
                baos.flush();
                baos.close();
                
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 传入图片路径返回经过Base64编码的byte字节数组
     *
     * @param file 图片路径 {@link String}
     * @return 返回经过Base64编码的byte字节数据 {@link Byte[]}
     */
    public static byte[] getBase64ForByte(String file) {
        byte[] data = null;
        InputStream inputStream = null;
        byte[] b = null;
        try {
            if (TextUtils.isEmpty(file)) {
                return null;
            }
            inputStream = new FileInputStream(file);
            data = new byte[inputStream.available()];
            inputStream.read();
            b = Base64.encode(data, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return b;
    }
    
    /**
     * 传入图片的Bitmap返回经过Base64编码的byte字节数组
     *
     * @param bitmap 图片的Bitmap {@link Bitmap}
     * @return 返回经过Base64编码的byte字节数组 {@link Byte}
     */
    public static byte[] getBase64ForByte(Bitmap bitmap) {
        byte[] result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                
                baos.flush();
                baos.close();
                
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encode(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 传入经过Base64编码的图片字符串进行Base64解码，返回图片的Bitmap
     *
     * @param string 已经经过Base64编码的图片字符串 {@link String}
     * @return 返回图片的Bitmap {@link Bitmap}
     */
    public static Bitmap base64ToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    
    /**
     * 传入已经经过Base64编码的图片byte字节数组进行Base64解码，返回图片的Bitmap
     *
     * @param b 已经经过Base64编码的图片byte字节数组 {@link Byte}
     * @return 返回图片的Bitmap {@link Bitmap}
     */
    public static Bitmap base64ToBitmap(byte[] b) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    
}
