package com.llt.gtx.spring.model;

import com.llt.gtx.spring.annotation.GlobalTransactional;

import java.lang.reflect.Method;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public class GtxMethod {
    private GlobalTransactional transactionAnnotation;
    private Method method;


    public GtxMethod(GlobalTransactional transactionAnnotation, Method method) {
        this.transactionAnnotation = transactionAnnotation;
        this.method = method;
    }

    public GlobalTransactional getTransactionAnnotation() {
        return transactionAnnotation;
    }

    public void setTransactionAnnotation(GlobalTransactional transactionAnnotation) {
        this.transactionAnnotation = transactionAnnotation;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
