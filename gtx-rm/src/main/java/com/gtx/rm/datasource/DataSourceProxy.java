package com.gtx.rm.datasource;

import com.gtx.rm.datasource.wrapper.DataSourceWrapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public class DataSourceProxy extends DataSourceWrapper {

    public DataSourceProxy(DataSource originalDataSource) {
        super(originalDataSource);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new ConnectionProxy(super.getConnection());
    }
}
