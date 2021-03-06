package com.gtx.spring.boot.autoconfig.properties;

import com.gtx.spring.boot.autoconfig.PropertiesConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
@Component
@ConfigurationProperties(prefix = PropertiesConstants.GTX_PREFIX)
public class GtxProperties  {
    /**
     * enable auto configuration
     */
    private boolean enabled = true;


    private String applicationId;


    private String txServiceGroup;

    public boolean isEnabled() {
        return enabled;
    }

    public GtxProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public GtxProperties setApplicationId(String applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public String getTxServiceGroup() {
        return txServiceGroup;
    }

    public GtxProperties setTxServiceGroup(String txServiceGroup) {
        this.txServiceGroup = txServiceGroup;
        return this;
    }
}
