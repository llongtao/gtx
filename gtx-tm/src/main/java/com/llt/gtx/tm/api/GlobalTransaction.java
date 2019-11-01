package com.llt.gtx.tm.api;

import com.llt.gtx.core.enums.GlobalStatus;
import com.llt.gtx.core.exception.TransactionException;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public interface GlobalTransaction {

    /**
     * 开始一个全局事务
     *
     * @throws TransactionException 开始事务异常
     */
    void begin() throws TransactionException;

    /**
     * 开始一个全局事务,含超时时间
     *
     * @param timeout 超时时间 ms
     * @throws TransactionException 开始事务异常
     */
    void begin(int timeout) throws TransactionException;

    /**
     * 开始一个全局事务,含超时时间,和名称
     *
     * @param timeout 超时时间
     * @param name    名称
     * @throws TransactionException 开始事务异常
     */
    void begin(int timeout, String name) throws TransactionException;

    /**
     * 提交一个全局事务
     *
     * @throws TransactionException 提交事务异常
     */
    void commit() throws TransactionException;

    /**
     * 回滚全局事务
     *
     * @throws TransactionException 回滚异常
     */
    void rollback() throws TransactionException;



    /**
     * 获取xid
     *
     * @return xid
     */
    String getXid();


    /**
     * 获取事务状态
     *
     * @return 全局事务状态
     * @throws TransactionException 获取失败
     * @see GlobalStatus 全局事务状态
     */
    GlobalStatus getStatus() throws TransactionException;

    /**
     * 修改事务状态
     *
     * @param globalStatus 事务状态
     * @throws TransactionException 修改状态异常
     */
    void setStatus(GlobalStatus globalStatus) throws TransactionException;
}
