package com.gtx.spring.boot.autoconfig;

import com.gtx.common.exception.GtxException;
import com.gtx.common.utils.StringUtils;
import com.gtx.core.RootConfig;
import com.gtx.rm.datasource.DataSourceProxy;
import com.gtx.spring.autoproxy.GtxAutoproxyCreator;
import com.gtx.spring.boot.autoconfig.properties.GtxProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

import java.util.List;

import static com.gtx.spring.boot.autoconfig.PropertiesConstants.GTX_PREFIX;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
@ComponentScan(basePackages = "com.gtx.spring.boot.autoconfig.properties")
@ConditionalOnProperty(prefix = GTX_PREFIX, name = "enabled", matchIfMissing = true)
@ConditionalOnClass({DataSourceProxy.class})
@Configuration
@EnableConfigurationProperties({GtxProperties.class})
public class GtxAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(GtxAutoConfiguration.class);

    @Resource
    private GtxProperties gtxProperties;

    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name:null}")
    private String appName;


    @Bean
    @ConditionalOnMissingBean(GtxAutoproxyCreator.class)
    public GtxAutoproxyCreator gtxAutoproxyCreator() {

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Automatically configure Seata");
        }
        String applicationId = gtxProperties.getApplicationId();
        String txServiceGroup = gtxProperties.getTxServiceGroup();
        if (StringUtils.isEmpty(applicationId)) {
            if (StringUtils.isEmpty(appName)) {
                throw new GtxException("初始化失败: 未配置applicationId");
            }
            applicationId = appName;
        }
        if (StringUtils.isEmpty(txServiceGroup)) {
            txServiceGroup = PropertiesConstants.DEFAULT_GROUP;
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("gtx-tm-server");
        System.out.println("gtx-tm-server:"+instances);
        ServiceInstance serviceInstance = instances.get(0);
        RootConfig.setServerIp(serviceInstance.getHost());
        RootConfig.setServerPort(serviceInstance.getPort());
        return new GtxAutoproxyCreator(applicationId, txServiceGroup);
    }
}
