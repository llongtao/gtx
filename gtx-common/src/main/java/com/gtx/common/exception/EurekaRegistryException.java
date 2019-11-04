package com.gtx.common.exception;

/**
 * @author LILONGTAO
 * @date 2019-11-04
 */
public class EurekaRegistryException extends RuntimeException {
    public EurekaRegistryException() {
    }

    public EurekaRegistryException(String message) {
        super(message);
    }

    public EurekaRegistryException(String message, Throwable cause) {
        super(message, cause);
    }
}
