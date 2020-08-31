package com.gtx.core.rpc;

import com.gtx.common.constants.GtxConstants;
import com.gtx.core.RootConfig;
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

    public static Channel getTmChannel() {
        return CHANNEL_MAP.computeIfAbsent("def",(id)->{

            try {
                return Client.start(RootConfig.getServerIp(),RootConfig.getServerPort());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        });
    }


}
