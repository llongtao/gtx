package com.llt.gtx.tm.api;

import com.llt.gtx.core.enums.GlobalStatus;
import com.sun.org.apache.bcel.internal.classfile.Code;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public interface TransactionalExecutor {
    /**
     * Execute the business logic here.
     *
     * @return What the business logic returns.
     * @throws Throwable Any throwable during executing.
     */
    Object execute() throws Throwable;

    /**
     * transaction conf or other attr
     * @return
     */
    TransactionInfo getTransactionInfo();


    /**
     * The type Execution exception.
     */
    class ExecutionException extends Exception {

        private GlobalTransaction transaction;

        private GlobalStatus code;

        private Throwable originalException;

        /**
         * Instantiates a new Execution exception.
         *
         * @param transaction the transaction
         * @param cause       the cause
         * @param code        the code
         */
        public ExecutionException(GlobalTransaction transaction, Throwable cause, GlobalStatus code) {
            this(transaction, cause, code, null);
        }

        /**
         * Instantiates a new Execution exception.
         *
         * @param transaction       the transaction
         * @param code              the code
         * @param originalException the original exception
         */
        public ExecutionException(GlobalTransaction transaction, GlobalStatus code, Throwable originalException) {
            this(transaction, null, code, originalException);
        }

        /**
         * Instantiates a new Execution exception.
         *
         * @param transaction       the transaction
         * @param cause             the cause
         * @param code              the code
         * @param originalException the original exception
         */
        public ExecutionException(GlobalTransaction transaction, Throwable cause, GlobalStatus code,
                                  Throwable originalException) {
            this(transaction, null, cause, code, originalException);
        }

        /**
         * Instantiates a new Execution exception.
         *
         * @param transaction       the transaction
         * @param message           the message
         * @param cause             the cause
         * @param code              the code
         * @param originalException the original exception
         */
        public ExecutionException(GlobalTransaction transaction, String message, Throwable cause, GlobalStatus code,
                                  Throwable originalException) {
            super(message, cause);
            this.transaction = transaction;
            this.code = code;
            this.originalException = originalException;
        }

        /**
         * Gets transaction.
         *
         * @return the transaction
         */
        public GlobalTransaction getTransaction() {
            return transaction;
        }

        /**
         * Sets transaction.
         *
         * @param transaction the transaction
         */
        public void setTransaction(GlobalTransaction transaction) {
            this.transaction = transaction;
        }

        /**
         * Gets code.
         *
         * @return the code
         */
        public GlobalStatus getGlobalStatus() {
            return code;
        }

        /**
         * Sets code.
         *
         * @param code the code
         */
        public void setGlobalStatus(GlobalStatus code) {
            this.code = code;
        }

        /**
         * Gets original exception.
         *
         * @return the original exception
         */
        public Throwable getOriginalException() {
            return originalException;
        }

        /**
         * Sets original exception.
         *
         * @param originalException the original exception
         */
        public void setOriginalException(Throwable originalException) {
            this.originalException = originalException;
        }
    }
}
