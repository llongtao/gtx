package com.gtx.core.rpc;

import com.alibaba.fastjson.JSON;
import com.gtx.core.protocol.RpcMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author LILONGTAO
 * @date 2019-11-05
 */
public class RpcMessageDecoder extends MessageToMessageEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {
        out.add(JSON.parseObject(byteBuf.toString(),RpcMessage.class));
    }
}
