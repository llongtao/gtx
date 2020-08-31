package com.gtx.tm.api;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public class TransactionInfo {


    private int timeoutMills;

    private String name;


    private Class<? extends Throwable>[] rollbackFor;

    private boolean isLast;

    /**
     * 默认超时时间
     */
    public static final int DEFAULT_TIME_OUT = 20000;

    public TransactionInfo(int timeoutMills,String name,Class<? extends Throwable>[] rollbackFor,boolean isLast) {
        this.timeoutMills = timeoutMills;
        this.name = name;
        this.rollbackFor=rollbackFor;
        this.isLast = isLast;
    }


    public boolean rollbackOn(Throwable ex) {
        Class<? extends Throwable> aClass = ex.getClass();
        for (Class<? extends Throwable> aClass1 : rollbackFor) {
            if (aClass1 == aClass) {
                return true;
            }
        }
        return false;
    }

    public int getTimeOut() {
        return timeoutMills;
    }

    public String getName() {
        return name;
    }
}
