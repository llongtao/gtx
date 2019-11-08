package com.gtx.core.rpc;

import com.gtx.common.constants.GtxConstants;
import com.gtx.core.protocol.RpcMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

/**
 * @author LILONGTAO
 * @date 2019-11-07
 */
public class ChannelManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelManager.class);

    private static final ConcurrentMap<String,Channel> CHANNEL_MAP = new ConcurrentHashMap<>();

    public static Channel getTmChannel(String tmId) {
        return CHANNEL_MAP.computeIfAbsent(tmId,(id)->{
            String[] split = id.split(GtxConstants.CLIENT_ID_SPLIT_CHAR);
            String ip = split[1];
            int port = Integer.parseInt(split[2]);
            try {
                return Client.start(ip,port);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Channel tmChannel = ChannelManager.getTmChannel("def:127.0.0.1:8888");
        RpcMessage rpcMessage = new RpcMessage();
        rpcMessage.setCodec((byte) 59);
        rpcMessage.setId(23);
        rpcMessage.setMessageType((byte) 59);
        ChannelFuture channelFuture = tmChannel.writeAndFlush(rpcMessage);
        ClientHandler clientHandler = new ClientHandler();
        ChannelFutureListener channelFutureListener = future -> System.out.println(future.isDone());


    }
}
