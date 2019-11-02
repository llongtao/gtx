package com.llt.gtx.spring.autoproxy;

import com.llt.gtx.common.utils.StringUtils;
import com.llt.gtx.rm.datasource.DataSourceProxy;
import com.llt.gtx.spring.annotation.GlobalTransactional;
import com.llt.gtx.spring.model.GtxMethod;
import com.llt.gtx.spring.utils.SpringProxyUtils;
import com.llt.gtx.tm.api.FailureHandler;
import org.aopalliance.intercept.MethodInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public class GtxAutoproxyCreator extends AbstractAutoProxyCreator implements InitializingBean, ApplicationContextAware, DisposableBean {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(GtxAutoproxyCreator.class);

    private static final int AT_MODE = 1;
    private static final int MT_MODE = 2;

    private static final int ORDER_NUM = 1024;
    private static final int DEFAULT_MODE = AT_MODE + MT_MODE;

    private static final Set<String> PROXYED_SET = new HashSet<>();
    private static final FailureHandler DEFAULT_FAIL_HANDLER = null;//new DefaultFailureHandlerImpl();

    private MethodInterceptor interceptor;

    private final String applicationId;
    private final String txServiceGroup;
    private final int mode;
    private final boolean disableGlobalTransaction = false;
    //ConfigurationFactory.getInstance().getBoolean("service.disableGlobalTransaction", false);

    private final FailureHandler failureHandlerHook;

    private ApplicationContext applicationContext;

    /**
     * Instantiates a new Global transaction scanner.
     *
     * @param txServiceGroup the tx service group
     */
    public GtxAutoproxyCreator(String txServiceGroup) {
        this(txServiceGroup, txServiceGroup, DEFAULT_MODE);
    }

    /**
     * Instantiates a new Global transaction scanner.
     *
     * @param txServiceGroup the tx service group
     * @param mode           the mode
     */
    public GtxAutoproxyCreator(String txServiceGroup, int mode) {
        this(txServiceGroup, txServiceGroup, mode);
    }

    /**
     * Instantiates a new Global transaction scanner.
     *
     * @param applicationId  the application id
     * @param txServiceGroup the default server group
     */
    public GtxAutoproxyCreator(String applicationId, String txServiceGroup) {
        this(applicationId, txServiceGroup, DEFAULT_MODE);
    }

    /**
     * Instantiates a new Global transaction scanner.
     *
     * @param applicationId  the application id
     * @param txServiceGroup the tx service group
     * @param mode           the mode
     */
    public GtxAutoproxyCreator(String applicationId, String txServiceGroup, int mode) {
        this(applicationId, txServiceGroup, mode, DEFAULT_FAIL_HANDLER);
    }

    /**
     * Instantiates a new Global transaction scanner.
     *
     * @param applicationId      the application id
     * @param txServiceGroup     the tx service group
     * @param failureHandlerHook the failure handler hook
     */
    public GtxAutoproxyCreator(String applicationId, String txServiceGroup, FailureHandler failureHandlerHook) {
        this(applicationId, txServiceGroup, DEFAULT_MODE, failureHandlerHook);
    }

    /**
     * Instantiates a new Global transaction scanner.
     *
     * @param applicationId      the application id
     * @param txServiceGroup     the tx service group
     * @param mode               the mode
     * @param failureHandlerHook the failure handler hook
     */
    public GtxAutoproxyCreator(String applicationId, String txServiceGroup, int mode,
                               FailureHandler failureHandlerHook) {
        setOrder(ORDER_NUM);
        setProxyTargetClass(true);
        this.applicationId = applicationId;
        this.txServiceGroup = txServiceGroup;
        this.mode = mode;
        this.failureHandlerHook = failureHandlerHook;
    }

    @Override
    public void destroy() {
        //ShutdownHook.getInstance().destroyAll();
    }

    private void initClient() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Initializing Global Transaction Clients ... ");
        }
        if (StringUtils.isEmpty(applicationId) || StringUtils.isEmpty(txServiceGroup)) {
            throw new IllegalArgumentException(
                    "applicationId: " + applicationId + ", txServiceGroup: " + txServiceGroup);
        }
        //init TM
        //TMClient.init(applicationId, txServiceGroup);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(
                    "Transaction Manager Client is initialized. applicationId[" + applicationId + "] txServiceGroup["
                            + txServiceGroup + "]");
        }
        //init RM
        //RMClient.init(applicationId, txServiceGroup);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(
                    "Resource Manager is initialized. applicationId[" + applicationId + "] txServiceGroup[" + txServiceGroup
                            + "]");
        }

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Global Transaction Clients are initialized. ");
        }
        registerSpringShutdownHook();

    }

    private void registerSpringShutdownHook() {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            ((ConfigurableApplicationContext) applicationContext).registerShutdownHook();
            //ShutdownHook.removeRuntimeShutdownHook();
        }
        //ShutdownHook.getInstance().addDisposable(TmRpcClient.getInstance(applicationId, txServiceGroup));
        //ShutdownHook.getInstance().addDisposable(RmRpcClient.getInstance(applicationId, txServiceGroup));
    }

    @Override
    protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
        if (disableGlobalTransaction) {
            return bean;
        }
        try {
            if(beanName.contains("demoService")){
                System.out.println(beanName);
            }
            synchronized (PROXYED_SET) {
                if (PROXYED_SET.contains(beanName)) {
                    return bean;
                }
                interceptor = null;

                Class<?> serviceInterface = SpringProxyUtils.findTargetClass(bean);
                //Class<?>[] interfacesIfJdk = SpringProxyUtils.findInterfaces(bean);

                // && !existsAnnotation(interfacesIfJdk)
                if (!existsAnnotation(new Class[]{serviceInterface})) {
                    return bean;
                }

                if (interceptor == null) {
                    interceptor = new GtxMethodInterceptor();
                }


                LOGGER.info(
                        "Bean[" + bean.getClass().getName() + "] with name [" + beanName + "] would use interceptor ["
                                + interceptor.getClass().getName() + "]");
                if (!AopUtils.isAopProxy(bean)) {
                    bean = super.wrapIfNecessary(bean, beanName, cacheKey);
                } else {
                    AdvisedSupport advised = SpringProxyUtils.getAdvisedSupport(bean);
                    Advisor[] advisor = buildAdvisors(beanName, getAdvicesAndAdvisorsForBean(null, null, null));
                    for (Advisor avr : advisor) {
                        advised.addAdvisor(0, avr);
                    }
                }
                PROXYED_SET.add(beanName);
                return bean;
            }
        } catch (Exception exx) {
            throw new RuntimeException(exx);
        }
    }

    private boolean existsAnnotation(Class<?>[] classes) {
        if (classes != null && classes.length > 0) {
            for (Class clazz : classes) {
                if (clazz == null) {
                    continue;
                }
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    GlobalTransactional trxAnno = method.getAnnotation(GlobalTransactional.class);
                    if (trxAnno != null) {
                        return true;
                    }

//                    GlobalLock lockAnno = method.getAnnotation(GlobalLock.class);
//                    if (lockAnno != null) {
//                        return true;
//                    }
                }
            }
        }
        return false;
    }

    private GtxMethod makeMethodDesc(GlobalTransactional anno, Method method) {
        return new GtxMethod(anno, method);
    }

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class beanClass, String beanName, TargetSource customTargetSource)
            throws BeansException {
        return new Object[]{interceptor};
    }

    @Override
    public void afterPropertiesSet() {
        if (disableGlobalTransaction) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Global transaction is disabled.");
            }
            return;
        }
        initClient();

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.setBeanFactory(applicationContext);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //&& ConfigurationFactory.getInstance().getBoolean(DATASOURCE_AUTOPROXY, false);
        if (bean instanceof DataSource && !(bean instanceof DataSourceProxy) ) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Auto proxy of [{}]", beanName);
            }
            DataSourceProxy dataSourceProxy = DataSourceProxyHolder.get().putDataSource((DataSource) bean);
            return Enhancer.create(bean.getClass(), (org.springframework.cglib.proxy.MethodInterceptor) (o, method, args, methodProxy) -> {
                Method m = BeanUtils.findDeclaredMethod(DataSourceProxy.class, method.getName(), method.getParameterTypes());
                if (null != m) {
                    return m.invoke(dataSourceProxy, args);
                } else {
                    boolean oldAccessible = method.isAccessible();
                    try {
                        method.setAccessible(true);
                        return method.invoke(bean, args);
                    } finally {
                        //recover the original accessible for security reason
                        method.setAccessible(oldAccessible);
                    }
                }
            });
        }
        return super.postProcessAfterInitialization(bean, beanName);
    }
}
