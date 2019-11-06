package com.gtx.server.netty;

import com.gtx.core.protocol.GlobalBeginRequest;
import com.gtx.core.protocol.MessageType;
import com.gtx.core.protocol.RpcMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * @author LILONGTAO
 * @date 2019-11-05
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMessage msg) {
        try {
            // 报文解析处理
            log.info("收到客户端[" + ctx.channel().remoteAddress() + "]参数:" + msg);
            RpcMessage rpcMessage = new RpcMessage();
            rpcMessage.setId(99);
            rpcMessage.setCodec(MessageType.TYPE_SEATA_MERGE);
            GlobalBeginRequest globalBeginRequest = new GlobalBeginRequest();
            globalBeginRequest.setXid("666");
            rpcMessage.setBody(globalBeginRequest);
            ctx.writeAndFlush(rpcMessage);
            //ctx.close();
        } catch (Exception e) {
            RpcMessage rpcMessage = new RpcMessage();
            rpcMessage.setId(99);
            rpcMessage.setCodec(MessageType.TYPE_SEATA_MERGE);
            ctx.writeAndFlush(rpcMessage);
            log.error("报文解析失败: " + e.getMessage());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        log.info("客户端[{}:{}]连接", insocket.getAddress(), insocket.getPort());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 当出现异常就关闭连接
        ctx.close();
    }


}
