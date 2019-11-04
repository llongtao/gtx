package com.gtx.common.utils;

import java.util.Collection;

/**
 * @author LILONGTAO
 * @date 2019-11-04
 */
public class CollectionUtils {
    /**
     * 是否为空
     *
     * @param collection 集合
     * @return boolean
     */
    public static boolean isEmpty(Collection collection) {
        return (collection == null) || (collection.isEmpty());
    }
}
