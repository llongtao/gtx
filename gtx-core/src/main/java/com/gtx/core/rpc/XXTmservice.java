package com.gtx.core.rpc;


import com.gtx.core.protocol.GlobalBeginRequest;
import com.gtx.core.rpc.annotation.Message;
import com.gtx.core.rpc.annotation.Service;

/**
 * @author LILONGTAO
 * @date 2019-11-08
 */
@Service
public class XXTmservice  {

    public String GlobalBegin(@Message GlobalBeginRequest globalBeginRequest){
        System.out.println("in:"+globalBeginRequest);
        return globalBeginRequest.getXid();
    }


}
