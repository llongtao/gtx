package com.gtx.tm;

import com.gtx.core.enums.GlobalStatus;
import com.gtx.core.exception.TransactionException;
import com.gtx.core.transaction.TransactionManager;

/**
 * @author LILONGTAO
 * @date 2019-11-05
 */
public class DefaultTransactionManager implements TransactionManager {


    @Override
    public String begin(String applicationId, String transactionServiceGroup, String name, int timeout) throws TransactionException {
        return null;
    }

    @Override
    public GlobalStatus commit(String xid) throws TransactionException {
        return null;
    }

    @Override
    public GlobalStatus rollback(String xid) throws TransactionException {
        return null;
    }

    @Override
    public GlobalStatus getStatus(String xid) throws TransactionException {
        return null;
    }

    @Override
    public GlobalStatus globalReport(String xid, GlobalStatus globalStatus) throws TransactionException {
        return null;
    }
}
