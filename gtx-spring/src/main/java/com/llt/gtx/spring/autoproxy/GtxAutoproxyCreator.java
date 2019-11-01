package com.llt.gtx.spring.autoproxy;

import com.llt.gtx.spring.annotation.GlobalTransactional;
import com.llt.gtx.spring.utils.SpringProxyUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public class GtxAutoproxyCreator extends AbstractAutoProxyCreator implements InitializingBean, ApplicationContextAware, DisposableBean {


    private static final Set<String> PROXY_SET = new HashSet<>();

    private MethodInterceptor interceptor;

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(GtxAutoproxyCreator.class);

    public GtxAutoproxyCreator(String applicationId, String txServiceGroup) {
    }

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, TargetSource customTargetSource) throws BeansException {
        System.out.println("beanClass" + beanName + "beanName" + beanName + "customTargetSource" + customTargetSource);
        return new Object[]{interceptor};
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    @Override
    protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {

        if (PROXY_SET.contains(beanName)) {

        }
        try {
            synchronized (PROXY_SET) {

                if (PROXY_SET.contains(beanName)) {
                    return bean;
                }
                interceptor = new GtxMethodInterceptor();

                Class<?> serviceInterface = SpringProxyUtils.findTargetClass(bean);
                Class<?>[] interfacesIfJdk = SpringProxyUtils.findInterfaces(bean);

                if (existsAnnotation(new Class[]{serviceInterface})
                        && existsAnnotation(interfacesIfJdk)) {
                    return bean;
                }

                if (interceptor == null) {
                    interceptor = new GtxMethodInterceptor();
                }
            }


            return super.wrapIfNecessary(bean, beanName, cacheKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    private boolean existsAnnotation(Class<?>[] classes) {
        if (classes != null && classes.length > 0) {
            for (Class clazz : classes) {
                if (clazz == null) {
                    continue;
                }
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    GlobalTransactional trxAnn = method.getAnnotation(GlobalTransactional.class);
                    if (trxAnn != null) {
                        return false;
                    }

                }
            }
        }
        return true;
    }
}
