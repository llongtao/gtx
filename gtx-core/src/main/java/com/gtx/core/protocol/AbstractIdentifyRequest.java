package com.gtx.core.protocol;

/**
 * @author LILONGTAO
 * @date 2019-11-07
 */
public class AbstractIdentifyRequest {
    /**
     * The Application id.
     */
    protected String applicationId;

    /**
     * The Transaction service group.
     */
    protected String transactionServiceGroup;

    /**
     * The Extra data.
     */
    protected String extraData;

    /**
     * Instantiates a new Abstract identify request.
     *
     * @param applicationId           the application id
     * @param transactionServiceGroup the transaction service group
     */
    public AbstractIdentifyRequest(String applicationId, String transactionServiceGroup) {
        this.applicationId = applicationId;
        this.transactionServiceGroup = transactionServiceGroup;
    }

    /**
     * Instantiates a new Abstract identify request.
     *
     * @param applicationId           the application id
     * @param transactionServiceGroup the transaction service group
     * @param extraData               the extra data
     */
    public AbstractIdentifyRequest(String applicationId, String transactionServiceGroup, String extraData) {
        this.applicationId = applicationId;
        this.transactionServiceGroup = transactionServiceGroup;
        this.extraData = extraData;
    }


    /**
     * Gets application id.
     *
     * @return the application id
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * Sets application id.
     *
     * @param applicationId the application id
     */
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * Gets transaction service group.
     *
     * @return the transaction service group
     */
    public String getTransactionServiceGroup() {
        return transactionServiceGroup;
    }

    /**
     * Sets transaction service group.
     *
     * @param transactionServiceGroup the transaction service group
     */
    public void setTransactionServiceGroup(String transactionServiceGroup) {
        this.transactionServiceGroup = transactionServiceGroup;
    }

    /**
     * Gets extra data.
     *
     * @return the extra data
     */
    public String getExtraData() {
        return extraData;
    }

    /**
     * Sets extra data.
     *
     * @param extraData the extra data
     */
    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }
}
