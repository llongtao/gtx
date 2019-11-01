package com.llt.gtx.spring.annotation;

import com.llt.gtx.tm.api.TransactionInfo;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public @interface GlobalTransactional {

    /**
     * Global transaction timeoutMills in MILLISECONDS.
     *
     * @return timeoutMills in MILLISECONDS.
     */
    int timeoutMills() default TransactionInfo.DEFAULT_TIME_OUT;

    /**
     * Given name of the global transaction instance.
     *
     * @return Given name.
     */
    String name() default "";

    /**
     * roll back for the Class
     * @return
     */
    Class<? extends Throwable>[] rollbackFor() default {};

    /**
     *  roll back for the class name
     * @return
     */
    String[] rollbackForClassName() default {};

    /**
     * not roll back for the Class
     * @return
     */
    Class<? extends Throwable>[] noRollbackFor() default {};

    /**
     * not roll back for the class name
     * @return
     */
    String[] noRollbackForClassName() default {};
}
