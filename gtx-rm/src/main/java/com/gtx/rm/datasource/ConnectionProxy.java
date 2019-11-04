package com.gtx.rm.datasource;

import com.gtx.rm.datasource.wrapper.ConnectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public class ConnectionProxy extends ConnectionWrapper {
    private static final Logger log = LoggerFactory.getLogger(ConnectionProxy.class);
    public ConnectionProxy(Connection originalConnection) {
        super(originalConnection);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        log.info("==========>Proxy setAutoCommit");
        super.setAutoCommit(autoCommit);
    }

    @Override
    public void commit() throws SQLException {
        log.info("==========>Proxy commit");
        super.commit();
    }

    @Override
    public void rollback() throws SQLException {
        log.info("==========>Proxy rollback");
        super.rollback();
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        log.info("==========>Proxy rollback savepoint");
        super.rollback(savepoint);
    }
}
