package com.gtx.tm.api;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public interface GtxFailureHandler {
    /**
     * 开始失败
     *
     * @param tx    全局事务
     * @param cause 异常
     */
    void onBeginFailure(GlobalTransaction tx, Throwable cause);

    /**
     * 提交失败
     *
     * @param tx    全局事务
     * @param cause 异常
     */
    void onCommitFailure(GlobalTransaction tx, Throwable cause);

    /**
     * 回滚失败
     *
     * @param tx    全局事务
     * @param cause 异常
     */
    void onRollbackFailure(GlobalTransaction tx, Throwable cause);
}
