package com.gtx.core.protocol;

/**
 * @author LILONGTAO
 * @date 2019-11-07
 */
public class RegisterRMRequest extends AbstractIdentifyRequest {
    /**
     * Instantiates a new Register rm request.
     */
    public RegisterRMRequest() {
        this(null, null);
    }

    /**
     * Instantiates a new Register rm request.
     *
     * @param applicationId           the application id
     * @param transactionServiceGroup the transaction service group
     */
    public RegisterRMRequest(String applicationId, String transactionServiceGroup) {
        super(applicationId, transactionServiceGroup);
    }

    private String resourceIds;

    /**
     * Gets resource ids.
     *
     * @return the resource ids
     */
    public String getResourceIds() {
        return resourceIds;
    }

    /**
     * Sets resource ids.
     *
     * @param resourceIds the resource ids
     */
    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }


    public short getTypeCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "RegisterRMRequest{" +
                "resourceIds='" + resourceIds + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", transactionServiceGroup='" + transactionServiceGroup + '\'' +
                '}';
    }
}
