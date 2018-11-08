package com.utils;

/**
 * 工具异常类
 */
public class UtilsThrow extends RuntimeException {
    public UtilsThrow(String message) {
        super(message);
    }
    
    public UtilsThrow(String message, Throwable cause) {
        super(message, cause);
    }
    
    public UtilsThrow(Throwable cause) {
        super(cause);
    }
}
