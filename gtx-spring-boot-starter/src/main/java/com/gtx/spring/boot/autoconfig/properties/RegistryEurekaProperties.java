package com.gtx.spring.boot.autoconfig.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static com.gtx.spring.boot.autoconfig.PropertiesConstants.REGISTRY_EUREKA_PREFIX;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
@Component
@ConfigurationProperties(prefix = REGISTRY_EUREKA_PREFIX)
public class RegistryEurekaProperties {
    private String serviceUrl = "http://localhost:8761/eureka";
    private String application = "default";
    private String weight = "1";

    public String getServiceUrl() {
        return serviceUrl;
    }

    public RegistryEurekaProperties setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
        return this;
    }

    public String getApplication() {
        return application;
    }

    public RegistryEurekaProperties setApplication(String application) {
        this.application = application;
        return this;
    }

    public String getWeight() {
        return weight;
    }

    public RegistryEurekaProperties setWeight(String weight) {
        this.weight = weight;
        return this;
    }
}
