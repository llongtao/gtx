package com.llt.gtx.tm.api;

import com.llt.gtx.core.RootContext;
import com.llt.gtx.core.enums.GlobalStatus;
import com.llt.gtx.core.exception.TransactionException;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public class GlobalTransactionContext {
    private GlobalTransactionContext() {
    }

    /**
     * Try to create a new GlobalTransaction.
     *
     * @return
     */
    private static GlobalTransaction createNew() {
        GlobalTransaction tx = new DefaultGlobalTransaction();
        return tx;
    }

    /**
     * Get GlobalTransaction instance bind on current thread.
     *
     * @return null if no transaction context there.
     */
    private static GlobalTransaction getCurrent() {
        String xid = RootContext.getXID();
        if (xid == null) {
            return null;
        }
        return new DefaultGlobalTransaction(xid, GlobalStatus.BEGIN, GlobalTransactionRole.PARTICIPANT);
    }

    /**
     * Get GlobalTransaction instance bind on current thread. Create a new on if no existing there.
     *
     * @return new context if no existing there.
     */
    public static GlobalTransaction getCurrentOrCreate() {
        GlobalTransaction tx = getCurrent();
        if (tx == null) {
            return createNew();
        }
        return tx;
    }

    /**
     * Reload GlobalTransaction instance according to the given XID
     *
     * @param xid the xid
     * @return reloaded transaction instance.
     * @throws TransactionException the transaction exception
     */
    public static GlobalTransaction reload(String xid) throws TransactionException {
        GlobalTransaction tx = new DefaultGlobalTransaction(xid, GlobalStatus.UN_KNOWN, GlobalTransactionRole.LAUNCHER) {
            @Override
            public void begin(int timeout, String name) throws TransactionException {
                throw new IllegalStateException("Never BEGIN on a RELOADED GlobalTransaction. ");
            }
        };
        return tx;
    }
}
