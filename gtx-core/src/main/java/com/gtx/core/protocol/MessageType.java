package com.gtx.core.protocol;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LILONGTAO
 * @date 2019-11-06
 */

public enum  MessageType {
    /**
     *
     */
    GlobalBeginRequest((byte) 59,GlobalBeginRequest.class),
    RegisterRMRequest((byte) 60,RegisterRMRequest.class);
    ;


    private byte code;

    private Class cls;

    private static Map<Byte,MessageType> BYTE_MAP = new HashMap<>();

    private static Map<Class,MessageType> CLASS_MAP = new HashMap<>();

    MessageType(byte code, Class cls) {
        this.code = code;
        this.cls = cls;
    }

    static {
        MessageType[] values = MessageType.values();
        for (MessageType value : values) {
            BYTE_MAP.put(value.code,value);
            CLASS_MAP.put(value.cls,value);
        }
    }

    public static Class getTypeClass(byte codecType) {
        return BYTE_MAP.get(codecType).cls;
    }

    public static byte getTypeByte(Class cls) {
        return CLASS_MAP.get(cls).code;
    }
}
