package com.llt.gtx.common.exception;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public class GtxException extends RuntimeException {
    public GtxException() {
    }

    public GtxException(String message) {
        super(message);
    }

    public GtxException(String message, Throwable cause) {
        super(message, cause);
    }

    public GtxException(Throwable cause) {
        super(cause);
    }


}
