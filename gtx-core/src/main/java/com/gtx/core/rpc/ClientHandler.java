package com.gtx.core.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @author LILONGTAO
 * @date 2019-11-04
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    // ByteBuf是一个引用计数对象，这个对象必须显示地调用release()方法来释放。
    // 请记住处理器的职责是释放所有传递到处理器的引用计数对象。
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        try{
            //do something
            //接收服务端发来的数据 ByteBuf
            ByteBuf  buf = (ByteBuf)msg;
            //创建一个和buf一样长度的字节空数组
            byte[] data = new byte[buf.readableBytes()];
            //将buf中的数据读取到data数组中
            buf.readBytes(data);
            //将data数组惊醒包装 以String格式输出
            String response = new String(data,"utf-8");
            System.out.println("client :"+response);

            //以上代码是接收服务端发来的反馈数据//

            ctx.close();
        }finally{
            // Discard the received data silently.
            ReferenceCountUtil.release(msg);
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
