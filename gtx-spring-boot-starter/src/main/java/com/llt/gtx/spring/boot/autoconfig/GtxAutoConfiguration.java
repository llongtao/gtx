package com.llt.gtx.spring.boot.autoconfig;

import com.llt.gtx.rm.datasource.DataSourceProxy;
import com.llt.gtx.spring.autoproxy.GtxAutoproxyCreator;
import com.llt.gtx.spring.boot.autoconfig.properties.GtxProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import static com.llt.gtx.spring.boot.autoconfig.PropertiesConstants.GTX_PREFIX;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
@ComponentScan(basePackages = "com.llt.gtx.spring.boot.autoconfig.properties")
@ConditionalOnProperty(prefix = GTX_PREFIX, name = "enabled", matchIfMissing = true)
@ConditionalOnClass({DataSourceProxy.class})
@Configuration
@EnableConfigurationProperties({GtxProperties.class})
public class GtxAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(GtxAutoConfiguration.class);
    @Autowired
    private GtxProperties gtxProperties;

    @Bean
    //@DependsOn({"springUtils"})
    @ConditionalOnMissingBean(GtxAutoproxyCreator.class)
    public GtxAutoproxyCreator gtxAutoproxyCreator() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Automatically configure Seata");
        }
        return new GtxAutoproxyCreator(gtxProperties.getApplicationId(), gtxProperties.getTxServiceGroup());
    }
}
