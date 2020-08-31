package com.gtx.spring.autoproxy;

import com.gtx.core.enums.GlobalStatus;
import com.gtx.core.transaction.PC2TransactionManager;
import com.gtx.core.transaction.TransactionManager;
import com.gtx.spring.annotation.GlobalTransactional;
import com.gtx.spring.filter.XidHolder;
import com.gtx.spring.model.GtxMethod;
import com.gtx.tm.api.TransactionInfo;
import com.gtx.tm.api.TransactionalExecutor;
import com.gtx.tm.api.TransactionalTemplate;
import io.netty.util.concurrent.FastThreadLocal;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public class GtxMethodInterceptor implements MethodInterceptor {
    TransactionManager transactionManager= new PC2TransactionManager();
    private static final FastThreadLocal<GtxMethod> GTX_METHOD = new FastThreadLocal<>();
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
        Method specificMethod = ClassUtils.getMostSpecificMethod(invocation.getMethod(), targetClass);
        final Method method = BridgeMethodResolver.findBridgedMethod(specificMethod);

        final GlobalTransactional globalTransactionalAnnotation = AnnotationUtils.getAnnotation(method, GlobalTransactional.class);
        if (globalTransactionalAnnotation != null) {
            return handleGlobalTransaction(invocation, globalTransactionalAnnotation);
        } else {
            return invocation.proceed();
        }
    }


    private Object handleGlobalTransaction(MethodInvocation invocation, GlobalTransactional gtAnn) throws Throwable {

        TransactionalTemplate transactionalTemplate= new TransactionalTemplate();

        return transactionalTemplate.execute(new TransactionalExecutor(){
            @Override
            public Object execute() throws Throwable {
                return invocation.proceed();
            }

            @Override
            public TransactionInfo getTransactionInfo() {
                return new TransactionInfo(gtAnn.timeoutMills(),gtAnn.name(),gtAnn.rollbackFor(),gtAnn.isLast());
            }
        });


    }

    public static FastThreadLocal<GtxMethod> getGtxMethod() {
        return GTX_METHOD;
    }
}
