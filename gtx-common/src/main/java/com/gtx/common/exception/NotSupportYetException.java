package com.gtx.common.exception;

/**
 * @author LILONGTAO
 * @date 2019-11-04
 */
public class NotSupportYetException extends RuntimeException {
    public NotSupportYetException() {
    }

    public NotSupportYetException(String message) {
        super(message);
    }

    public NotSupportYetException(String message, Throwable cause) {
        super(message, cause);
    }
}
