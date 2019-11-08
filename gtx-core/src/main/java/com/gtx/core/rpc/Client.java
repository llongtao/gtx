package com.gtx.core.rpc;

import com.alibaba.fastjson.JSON;
import com.gtx.core.protocol.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author LILONGTAO
 * @date 2019-11-04
 */
public class Client {
    private static final EventLoopGroup workgroup = new NioEventLoopGroup();

    public static Channel start(String ip ,int port) throws InterruptedException {

        //客户端
        Bootstrap b = new Bootstrap()
                .group(workgroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nsc) throws Exception {
                        nsc.pipeline().addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                        nsc.pipeline().addLast("decoder",new RpcMessageDecoder());
                        nsc.pipeline().addLast("encoder",new RpcMessageEncoder());
                        nsc.pipeline().addLast(new ClientHandler());
                    }
                });
        return b.connect(ip, port).sync().channel();


//
//        cf1.channel().closeFuture().sync();
//        cf2.channel().closeFuture().sync();
//
//        workgroup.shutdownGracefully();
    }
}
