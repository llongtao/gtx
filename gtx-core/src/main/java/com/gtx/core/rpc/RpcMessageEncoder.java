package com.gtx.core.rpc;

import com.alibaba.fastjson.JSON;
import com.gtx.common.constants.GtxConstants;
import com.gtx.core.protocol.ProtocolConstants;
import com.gtx.core.protocol.RpcMessage;
import io.netty.buffer.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;

/**
 * @author LILONGTAO
 * @date 2019-11-05
 */
public class RpcMessageEncoder extends MessageToMessageEncoder<RpcMessage> {
    private Logger log = LoggerFactory.getLogger(ClientHandler.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcMessage rpcMessage, List<Object> out) throws Exception {
        try {
            int fullLength = ProtocolConstants.HEAD_LENGTH;
            int headLength = ProtocolConstants.HEAD_LENGTH;

            byte messageType = rpcMessage.getMessageType();
            ByteBuf bf = ctx.alloc().buffer();

            bf.writeBytes(ProtocolConstants.MAGIC_CODE_BYTES);
            bf.writeByte(ProtocolConstants.VERSION);
            // full Length(4B) and head length(2B) will fix in the end.

            bf.writerIndex(bf.writerIndex() + 6);
            bf.writeByte(messageType);
            bf.writeByte(rpcMessage.getCodec());
            bf.writeByte(rpcMessage.getCompressor());
            bf.writeInt(rpcMessage.getId());

            // direct write head with zero-copy
            Map<String, String> headMap = rpcMessage.getHeadMap();
            if (headMap != null && !headMap.isEmpty()) {
                int headMapBytesLength = HeadMapSerializer.getInstance().encode(headMap, bf);
                headLength += headMapBytesLength;
                fullLength += headMapBytesLength;
            }

            byte[] bodyBytes = JSON.toJSONString(rpcMessage.getBody()).getBytes(GtxConstants.DEFAULT_CHARSET);
            fullLength += bodyBytes.length;
            bf.writeBytes(bodyBytes);


            // fix fullLength and headLength
            int writeIndex = bf.writerIndex();
            // skip magic code(2B) + version(1B)
            bf.writerIndex(writeIndex - fullLength + 3);
            bf.writeInt(fullLength);
            bf.writeShort(headLength);
            bf.writerIndex(writeIndex);
            bf.writeByte('\n');
            out.add(bf);
        } catch (Throwable e) {
            log.error("Encode request error!", e);
        }

    }
}
