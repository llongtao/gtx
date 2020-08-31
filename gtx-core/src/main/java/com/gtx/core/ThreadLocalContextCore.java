package com.gtx.core;

import io.netty.util.concurrent.FastThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author llt
 * @date 2020-08-31 22:58
 */
public class ThreadLocalContextCore implements ContextCore{

    private static final FastThreadLocal<Map<String, String>> ft = new FastThreadLocal<>();
    @Override
    public String put(String key, String value) {
        Map<String, String> stringStringMap = ft.get();
        if (stringStringMap == null) {
            stringStringMap = new HashMap<>();
        }
        stringStringMap.put(key, value);
        return value;
    }

    @Override
    public String get(String key) {
        Map<String, String> stringStringMap = ft.get();
        if (stringStringMap==null){
            return null;
        }
        return stringStringMap.get(key);
    }

    @Override
    public String remove(String key) {
        Map<String, String> stringStringMap = ft.get();
        if (stringStringMap==null){
            return null;
        }
        return stringStringMap.remove(key);
    }

    @Override
    public Map<String, String> entries() {
        return ft.get();
    }
}
