package com.gtx.core.rpc;

import com.gtx.core.protocol.RpcMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author LILONGTAO
 * @date 2019-11-04
 */
public class Client {
    /*IP地址*/
    static final String HOST = System.getProperty("host", "127.0.0.1");
    /*端口号*/
    static final int PORT1 = Integer.parseInt(System.getProperty("port", "8888"));

    static final int PORT2 = Integer.parseInt(System.getProperty("port", "8888"));

    public static void main(String[] args) throws Exception {
        EventLoopGroup workgroup = new NioEventLoopGroup();
        //客户端
        Bootstrap b = new Bootstrap()
                .group(workgroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nsc) throws Exception {
                        nsc.pipeline().addLast(new StringEncoder());
                        nsc.pipeline().addLast(new ClientHandler());
                    }
                });
        //创建异步连接 可添加多个端口
        ChannelFuture cf1 = b.connect(HOST, PORT1).sync();
        ChannelFuture cf2 = b.connect(HOST, PORT2).sync();

        RpcMessage rpcMessage = new RpcMessage();
        rpcMessage.setId(1);
        //buf
        //client向server端发送数据  Buffer形式
        cf1.channel().writeAndFlush(rpcMessage);
        cf2.channel().writeAndFlush(rpcMessage);


//        cf1.channel().closeFuture().sync();
//        cf2.channel().closeFuture().sync();

        //workgroup.shutdownGracefully();
    }
}
