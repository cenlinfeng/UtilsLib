package com.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtils {
    private DateUtils() {
    }
    
    
    /**
     * 获取当前时间
     *
     * @return 返回当前时间的String值 {@link String}
     */
    public static String getNowTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = dateFormat.format(date);
        return time;
    }
    
    /**
     * 获取字符串时间的Date值
     *
     * @param time    时间的String值 {@link String}
     * @param pattern 时间的格式 如 "yyyy-MM-dd HH:mm:ss" {@link String}
     * @return 返回传入时间的Date {@link Date}
     */
    public static Date stringToDate(String time, String pattern) {
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            date = simpleDateFormat.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}
