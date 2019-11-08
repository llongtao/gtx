package com.gtx.core.protocol;

/**
 * @author LILONGTAO
 * @date 2019-11-07
 */
public class RegisterTMRequest extends AbstractIdentifyRequest {




    private static final long serialVersionUID = -5929081344190543690L;

    /**
     * Instantiates a new Register tm request.
     */
    public RegisterTMRequest() {
        this(null, null);
    }

    /**
     * Instantiates a new Register tm request.
     *
     * @param applicationId           the application id
     * @param transactionServiceGroup the transaction service group
     * @param extraData               the extra data
     */
    public RegisterTMRequest(String applicationId, String transactionServiceGroup, String extraData) {
        super(applicationId, transactionServiceGroup, extraData);

    }

    /**
     * Instantiates a new Register tm request.
     *
     * @param applicationId           the application id
     * @param transactionServiceGroup the transaction service group
     */
    public RegisterTMRequest(String applicationId, String transactionServiceGroup) {
        super(applicationId, transactionServiceGroup);
    }

    public short getTypeCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "RegisterTMRequest{" +
                "applicationId='" + applicationId + '\'' +
                ", transactionServiceGroup='" + transactionServiceGroup + '\'' +
                '}';
    }
}
