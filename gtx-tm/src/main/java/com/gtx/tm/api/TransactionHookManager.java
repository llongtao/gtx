package com.gtx.tm.api;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public class TransactionHookManager {

    private static List<TransactionHook> transactionHookList
            = new ArrayList<>();

    public static void clear() {

    }

    public static List<TransactionHook> getHooks() {
        return transactionHookList;
    }
}
