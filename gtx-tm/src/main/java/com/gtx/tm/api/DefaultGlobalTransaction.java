package com.gtx.tm.api;

import com.gtx.core.enums.GlobalStatus;
import com.gtx.core.exception.TransactionException;
import com.gtx.core.protocol.GlobalBeginRequest;
import com.gtx.core.protocol.RpcMessage;
import com.gtx.core.rpc.ChannelManager;
import com.gtx.core.rpc.Client;
import com.gtx.core.rpc.ClientHandler;
import com.gtx.core.transaction.TransactionManager;
import com.gtx.tm.DefaultTransactionManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */

public class DefaultGlobalTransaction implements GlobalTransaction {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGlobalTransaction.class);

    private static final int DEFAULT_GLOBAL_TX_TIMEOUT = 60000;

    private static final String DEFAULT_GLOBAL_TX_NAME = "default";

    private TransactionManager transactionManager=new DefaultTransactionManager();

    private String xid;

    private GlobalStatus status;

    private GlobalTransactionRole role;

    public DefaultGlobalTransaction(String xid, GlobalStatus status, GlobalTransactionRole role) {
        this.xid = xid;
        this.status = status;
        this.role = role;
    }

    public DefaultGlobalTransaction() {
    }

    @Override
    public void begin() throws TransactionException {


        transactionManager.begin("", "", "", 0);
    }

    @Override
    public void begin(int timeout) throws TransactionException {
        transactionManager.begin("", "", "", timeout);
    }

    @Override
    public void begin(int timeout, String name) throws TransactionException {
        Channel tmChannel = ChannelManager.getTmChannel();
        RpcMessage rpcMessage = new RpcMessage();
        rpcMessage.setCodec((byte) 59);
        rpcMessage.setId(23);
        rpcMessage.setMessageType((byte) 59);
        GlobalBeginRequest globalBeginRequest = new GlobalBeginRequest();
        globalBeginRequest.setXid(xid);
        rpcMessage.setBody(globalBeginRequest);
        ChannelFuture channelFuture = tmChannel.writeAndFlush(rpcMessage);
        channelFuture.addListener(future -> System.out.println(future.isDone()));

        transactionManager.begin("", "", name, timeout);
    }

    @Override
    public void commit() throws TransactionException {
        transactionManager.commit(xid);
    }

    @Override
    public void rollback() throws TransactionException {
        transactionManager.rollback(xid);
    }

    @Override
    public String getXid() {
        return xid;
    }

    @Override
    public GlobalStatus getStatus() throws TransactionException {
        return status;
    }

    @Override
    public void setStatus(GlobalStatus globalStatus) throws TransactionException {
        this.status=globalStatus;
    }
}
