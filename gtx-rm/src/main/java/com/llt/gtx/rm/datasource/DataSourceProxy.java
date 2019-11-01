package com.llt.gtx.rm.datasource;

import com.llt.gtx.rm.datasource.wrapper.DataSourceWrapper;

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
        Connection connection = super.getConnection();
        return new ConnectionProxy(connection);
    }
}
