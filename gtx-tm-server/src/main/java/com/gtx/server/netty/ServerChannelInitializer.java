package com.gtx.server.netty;

import com.gtx.core.rpc.RpcMessageDecoder;
import com.gtx.core.rpc.RpcMessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author LILONGTAO
 * @date 2019-11-05
 */
public class ServerChannelInitializer  extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //添加编解码
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("encoder",new RpcMessageEncoder());
        pipeline.addLast("decoder",new RpcMessageDecoder());
        pipeline.addLast(new NettyServerHandler());


    }
}
