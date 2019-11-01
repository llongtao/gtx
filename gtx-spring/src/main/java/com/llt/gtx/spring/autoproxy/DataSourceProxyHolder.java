package com.llt.gtx.spring.autoproxy;

import com.llt.gtx.rm.datasource.DataSourceProxy;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public class DataSourceProxyHolder {
    private ConcurrentHashMap<DataSource, DataSourceProxy> dataSourceProxyMap;

    private DataSourceProxyHolder() {
        dataSourceProxyMap = new ConcurrentHashMap<DataSource, DataSourceProxy>(8);
    }


    private static class Holder {
        private static DataSourceProxyHolder INSTANCE;

        static {
            INSTANCE = new DataSourceProxyHolder();
        }

    }

    /**
     * 获取实例
     *
     * @return INSTANCE
     */
    public static DataSourceProxyHolder get() {
        return Holder.INSTANCE;
    }

    /**
     * 代理DataSource,返回被代理类
     *
     * @param dataSource 原DataSource
     * @return  被代理类
     */
    public DataSourceProxy getDataSourceProxy(DataSource dataSource) {
        return this.dataSourceProxyMap.computeIfAbsent(dataSource, DataSourceProxy::new);
    }

}
