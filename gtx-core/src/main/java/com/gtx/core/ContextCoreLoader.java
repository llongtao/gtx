package com.gtx.core;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public class ContextCoreLoader {
    public static ContextCore load() {
        return new ThreadLocalContextCore();
    }
}
