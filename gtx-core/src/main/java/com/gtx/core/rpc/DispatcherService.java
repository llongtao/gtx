package com.gtx.core.rpc;

import com.gtx.common.utils.AnnotationUtils;
import com.gtx.core.protocol.MessageType;
import com.gtx.core.protocol.ProtocolConstants;
import com.gtx.core.protocol.RpcMessage;
import com.gtx.core.rpc.annotation.Message;
import com.gtx.core.rpc.annotation.Service;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author LILONGTAO
 * @date 2019-11-08
 */
public class DispatcherService {


    private static final ConcurrentMap<Byte, CallEntity> CALL_MAP =new ConcurrentHashMap<>();


    static {
        Set<Class> allClassByAnn = AnnotationUtils.getAllClassByAnn(Service.class);
        for (Class aClass : allClassByAnn) {
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                Parameter[] parameters = method.getParameters();
                byte typeByte = -1;
                byte typeIndex = -1;
                byte ctxIndex = -1;
                for (int i = 0; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    if (AnnotationUtils.hasAnn(parameter, Message.class)) {
                        typeByte = MessageType.getTypeByte(parameter.getType());
                        typeIndex = (byte) i;
                    } else if (parameter.getType() == ChannelHandlerContext.class) {
                        ctxIndex = (byte) i;
                    }
                }
                if (typeIndex >= 0) {
                    try {
                        CALL_MAP.put(typeByte,new CallEntity(typeByte,typeIndex,ctxIndex,method,aClass.newInstance()));
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    public static void handle(RpcMessage rpcMessage, ChannelHandlerContext ctx) {
        byte messageType = rpcMessage.getMessageType();
        CallEntity callEntity = CALL_MAP.get(rpcMessage.getMessageType());
        if (callEntity != null) {

        }
        assert callEntity != null;
        Object invoke = callEntity.invoke(rpcMessage.getBody(), ctx);
        System.out.println(invoke);


    }

    private static void handleResponse(RpcMessage rpcMessage, ChannelHandlerContext ctx) {

    }


    private static void handleRequest(RpcMessage rpcMessage, ChannelHandlerContext ctx) {
        //TmService.class.getDeclaredMethods();
    }


    public static class CallEntity {
        private byte typeByte = -1;
        private byte typeIndex = -1;
        private byte ctxIndex = -1;
        private Method method;
        private Object instance;

        public CallEntity(byte typeByte, byte typeIndex, byte ctxIndex, Method method,Object instance) {
            this.typeByte = typeByte;
            this.typeIndex = typeIndex;
            this.ctxIndex = ctxIndex;
            this.method = method;
            this.instance = instance;
        }

        Object invoke(Object o,ChannelHandlerContext ctx){
            int length = method.getParameters().length;
            Object[] p =  new Object[length];
            if (typeIndex >= 0) {
                p[typeIndex] = o;
            }
            if (ctxIndex >= 0) {
                p[typeIndex] = ctx;
            }
            try {
                return method.invoke(instance,p);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
