package com.llt.gtx.tm.api;

import com.llt.gtx.core.enums.GlobalStatus;
import com.llt.gtx.core.exception.TransactionException;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public class DefaultGlobalTransaction implements GlobalTransaction {
    public DefaultGlobalTransaction(String xid, GlobalStatus unKnown, GlobalTransactionRole launcher) {
    }

    public DefaultGlobalTransaction() {
    }

    public void begin() throws TransactionException {

    }

    public void begin(int timeout) throws TransactionException {

    }

    public void begin(int timeout, String name) throws TransactionException {

    }

    public void commit() throws TransactionException {

    }

    public void rollback() throws TransactionException {

    }

    public String getXid() {
        return null;
    }

    public GlobalStatus getStatus() throws TransactionException {
        return null;
    }

    public void setStatus(GlobalStatus globalStatus) throws TransactionException {

    }
}
