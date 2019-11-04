package com.gtx.tm.api;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public interface TransactionHook {
    /**
     * before tx begin
     */
    void beforeBegin();

    /**
     * after tx begin
     */
    void afterBegin();

    /**
     * before tx commit
     */
    void beforeCommit();

    /**
     * after tx commit
     */
    void afterCommit();

    /**
     * before tx rollback
     */
    void beforeRollback();

    /**
     * after tx rollback
     */
    void afterRollback();

    /**
     * after tx all Completed
     */
    void afterCompletion();
}
