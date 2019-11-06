package com.gtx.core.protocol;

import com.gtx.common.config.ConfigurationFactory;
import com.gtx.common.config.ConfigurationKeys;

/**
 * @author LILONGTAO
 * @date 2019-11-06
 */
public class ProtocolConstants {
    public static final short HEAD_LENGTH = 16;

    /**
     * Magic code
     */
    public static final byte[] MAGIC_CODE_BYTES = {(byte) 0xda, (byte) 0xda};

    /**
     * Protocol version
     */
    public static final byte VERSION = 1;

    /**
     * Max frame length
     */
    public static final int MAX_FRAME_LENGTH = 8 * 1024 * 1024;


    /**
     * Message type: Request
     */
    public static final byte MSGTYPE_RESQUEST = 0;
    /**
     * Message type: Response
     */
    public static final byte MSGTYPE_RESPONSE = 1;
    /**
     * Message type: Request which no need response
     */
    public static final byte MSGTYPE_RESQUEST_ONEWAY = 2;





}
