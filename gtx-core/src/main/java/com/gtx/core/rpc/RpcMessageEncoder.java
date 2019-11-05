package com.gtx.core.rpc;

import com.alibaba.fastjson.JSON;
import com.gtx.core.protocol.RpcMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author LILONGTAO
 * @date 2019-11-05
 */
public class RpcMessageEncoder extends MessageToMessageEncoder<RpcMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcMessage rpcMessage, List<Object> out) throws Exception {
        ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(JSON.toJSONString(rpcMessage)), Charset.defaultCharset());
    }
}
