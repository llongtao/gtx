package com.gtx.spring.autoproxy;

import com.gtx.spring.annotation.GlobalTransactional;
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

    private Object handleGlobalTransaction(MethodInvocation invocation, GlobalTransactional globalTransactionalAnnotation) throws Throwable {
        System.out.println("pre");
        return  invocation.proceed();
    }
}
