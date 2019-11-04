/*
 *  Copyright 1999-2019 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.gtx.discovery.core.registry;

import com.gtx.common.config.ConfigurationFactory;
import com.gtx.common.config.ConfigurationKeys;

import com.gtx.common.exception.NotSupportYetException;
import com.gtx.common.loader.EnhancedServiceLoader;
import com.gtx.discovery.eureka.EurekaRegistryProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Objects;

/**
 * The type Registry factory.
 *
 * @author jimin.jm @alibaba-inc.com
 * @date 2019 /2/1
 */
public class RegistryFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistryFactory.class);

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static RegistryService getInstance() {
        RegistryType registryType;
        String registryTypeName = "Eureka";
//        ConfigurationFactory.CURRENT_FILE_INSTANCE.getConfig(
//            ConfigurationKeys.FILE_ROOT_REGISTRY + ConfigurationKeys.FILE_CONFIG_SPLIT_CHAR
//                + ConfigurationKeys.FILE_ROOT_TYPE);
        try {
            registryType = RegistryType.getType(registryTypeName);
        } catch (Exception exx) {
            throw new NotSupportYetException("not support registry type: " + registryTypeName);
        }
        if (RegistryType.File == registryType) {
            return FileRegistryServiceImpl.getInstance();
        } else {
            return EnhancedServiceLoader.load(RegistryProvider.class, Objects.requireNonNull(registryType).name()).provide();
        }
    }

    public static void main(String[] args) throws Exception {
        EurekaRegistryProvider eurekaRegistryProvider = new EurekaRegistryProvider();
        RegistryService provide = eurekaRegistryProvider.provide();
        InetSocketAddress unresolved = InetSocketAddress.createUnresolved("192.168.11.198", 1111);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("192.168.11.198",1111);
        provide.register(inetSocketAddress);
        List app_tst = provide.lookup("app_tst");
        System.out.println(app_tst);
    }
}
