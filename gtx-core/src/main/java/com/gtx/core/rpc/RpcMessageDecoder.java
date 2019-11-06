package com.gtx.core.rpc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.gtx.common.constants.GtxConstants;
import com.gtx.core.protocol.GlobalBeginRequest;
import com.gtx.core.protocol.MessageType;
import com.gtx.core.protocol.ProtocolConstants;
import com.gtx.core.protocol.RpcMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.omg.IOP.CodecFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.plugin2.message.HeartbeatMessage;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @author LILONGTAO
 * @date 2019-11-05
 */
public class RpcMessageDecoder extends MessageToMessageDecoder<ByteBuf> {
    private Logger log = LoggerFactory.getLogger(ClientHandler.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        try {
            out.add(decodeFrame(in));
        } catch (Exception e) {
            log.error("Decode frame error!", e);
            throw e;
        }
    }

    public Object decodeFrame(ByteBuf frame) {
        byte b0 = frame.readByte();
        byte b1 = frame.readByte();
        if (ProtocolConstants.MAGIC_CODE_BYTES[0] != b0
                || ProtocolConstants.MAGIC_CODE_BYTES[1] != b1) {
            throw new IllegalArgumentException("Unknown magic code: " + b0 + ", " + b1);
        }

        byte version = frame.readByte();
        int fullLength = frame.readInt();
        short headLength = frame.readShort();
        byte messageType = frame.readByte();
        byte codecType = frame.readByte();
        byte compressor = frame.readByte();
        int requestId = frame.readInt();

        RpcMessage rpcMessage = new RpcMessage();
        rpcMessage.setCodec(codecType);
        rpcMessage.setId(requestId);
        rpcMessage.setCompressor(compressor);
        rpcMessage.setMessageType(messageType);

        // direct read head with zero-copy
        int headMapLength = headLength - ProtocolConstants.HEAD_LENGTH;
        if (headMapLength > 0) {
            Map<String, String> map = HeadMapSerializer.getInstance().decode(frame, headMapLength);
            rpcMessage.getHeadMap().putAll(map);
        }

        // read body
        int bodyLength = fullLength - headLength;
        if (bodyLength > 0) {
            byte[] bs = new byte[bodyLength];
            frame.readBytes(bs);
            rpcMessage.setBody(deserialization(bs, codecType));
        }

        return rpcMessage;
    }

    private Object deserialization(byte[] bs, byte codecType) {
        Class msgClass = MessageType.getTypeClass(codecType);

        if (null == msgClass) {
            return null;
        }
        return JSON.parseObject(bs, msgClass);
    }


}
