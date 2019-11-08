package com.gtx.core.rpc;

import com.gtx.core.protocol.RpcMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author LILONGTAO
 * @date 2019-11-04
 */
public class ClientHandler extends SimpleChannelInboundHandler<RpcMessage> {
    private Logger log = LoggerFactory.getLogger(ClientHandler.class);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMessage rpcMessage) throws Exception {
        try {
            // 报文解析处理
            log.info("收到客户端[" + ctx.channel().remoteAddress() + "]参数:"+rpcMessage);
            //ctx.writeAndFlush("解析成功");
            DispatcherService.handle(rpcMessage,ctx);


        } catch (Exception e) {
            String errorCode = "-1\n";
            ctx.writeAndFlush(errorCode);
            log.error("报文解析失败: " + e.getMessage());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

}
