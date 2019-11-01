package com.llt.gtx.core;

import java.util.Map;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public interface ContextCore {
    /**
     * Put string.
     *
     * @param key   the key
     * @param value the value
     * @return the string
     */
    String put(String key, String value);

    /**
     * Get string.
     *
     * @param key the key
     * @return the string
     */
    String get(String key);

    /**
     * Remove string.
     *
     * @param key the key
     * @return the string
     */
    String remove(String key);

    /**
     * entries
     *
     * @return
     */
    Map<String, String> entries();
}
