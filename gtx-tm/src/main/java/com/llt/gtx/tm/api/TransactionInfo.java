package com.llt.gtx.tm.api;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public class TransactionInfo {

    /**
     * 默认超时时间
     */
    public static final int DEFAULT_TIME_OUT = 20000;


    public boolean rollbackOn(Throwable ex) {
        return false;
    }

    public int getTimeOut() {
        return 0;
    }

    public String getName() {
        return null;
    }
}
