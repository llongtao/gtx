package com.gtx.tm.api;

import com.gtx.core.enums.GlobalStatus;
import com.gtx.core.exception.TransactionException;
import com.gtx.core.transaction.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */

public class DefaultGlobalTransaction implements GlobalTransaction {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGlobalTransaction.class);

    private static final int DEFAULT_GLOBAL_TX_TIMEOUT = 60000;

    private static final String DEFAULT_GLOBAL_TX_NAME = "default";

    private TransactionManager transactionManager;

    private String xid;

    private GlobalStatus status;

    private GlobalTransactionRole role;

    public DefaultGlobalTransaction(String xid, GlobalStatus status, GlobalTransactionRole role) {
        this.xid = xid;
        this.status = status;
        this.role = role;
    }

    public DefaultGlobalTransaction() {

    }

    @Override
    public void begin() throws TransactionException {
    }

    @Override
    public void begin(int timeout) throws TransactionException {

    }

    @Override
    public void begin(int timeout, String name) throws TransactionException {

    }

    @Override
    public void commit() throws TransactionException {

    }

    @Override
    public void rollback() throws TransactionException {

    }

    @Override
    public String getXid() {
        return null;
    }

    @Override
    public GlobalStatus getStatus() throws TransactionException {
        return null;
    }

    @Override
    public void setStatus(GlobalStatus globalStatus) throws TransactionException {

    }
}
