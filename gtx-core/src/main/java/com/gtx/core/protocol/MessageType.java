package com.gtx.core.protocol;

/**
 * @author LILONGTAO
 * @date 2019-11-06
 */
public class MessageType {
    /**
     * The constant TYPE_GLOBAL_BEGIN.
     */
    public static final short TYPE_GLOBAL_BEGIN = 1;
    /**
     * The constant TYPE_GLOBAL_BEGIN_RESULT.
     */
    public static final short TYPE_GLOBAL_BEGIN_RESULT = 2;
    /**
     * The constant TYPE_GLOBAL_COMMIT.
     */
    public static final short TYPE_GLOBAL_COMMIT = 7;
    /**
     * The constant TYPE_GLOBAL_COMMIT_RESULT.
     */
    public static final short TYPE_GLOBAL_COMMIT_RESULT = 8;
    /**
     * The constant TYPE_GLOBAL_ROLLBACK.
     */
    public static final short TYPE_GLOBAL_ROLLBACK = 9;
    /**
     * The constant TYPE_GLOBAL_ROLLBACK_RESULT.
     */
    public static final short TYPE_GLOBAL_ROLLBACK_RESULT = 10;
    /**
     * The constant TYPE_GLOBAL_STATUS.
     */
    public static final short TYPE_GLOBAL_STATUS = 15;
    /**
     * The constant TYPE_GLOBAL_STATUS_RESULT.
     */
    public static final short TYPE_GLOBAL_STATUS_RESULT = 16;
    /**
     * The constant TYPE_GLOBAL_REPORT.
     */
    public static final short TYPE_GLOBAL_REPORT = 17;
    /**
     * The constant TYPE_GLOBAL_REPORT_RESULT.
     */
    public static final short TYPE_GLOBAL_REPORT_RESULT = 18;
    /**
     * The constant TYPE_GLOBAL_LOCK_QUERY.
     */
    public static final short TYPE_GLOBAL_LOCK_QUERY = 21;
    /**
     * The constant TYPE_GLOBAL_LOCK_QUERY_RESULT.
     */
    public static final short TYPE_GLOBAL_LOCK_QUERY_RESULT = 22;

    /**
     * The constant TYPE_BRANCH_COMMIT.
     */
    public static final byte TYPE_BRANCH_COMMIT = 3;
    /**
     * The constant TYPE_BRANCH_COMMIT_RESULT.
     */
    public static final byte TYPE_BRANCH_COMMIT_RESULT = 4;
    /**
     * The constant TYPE_BRANCH_ROLLBACK.
     */
    public static final byte TYPE_BRANCH_ROLLBACK = 5;
    /**
     * The constant TYPE_BRANCH_ROLLBACK_RESULT.
     */
    public static final byte TYPE_BRANCH_ROLLBACK_RESULT = 6;
    /**
     * The constant TYPE_BRANCH_REGISTER.
     */
    public static final byte TYPE_BRANCH_REGISTER = 11;
    /**
     * The constant TYPE_BRANCH_REGISTER_RESULT.
     */
    public static final byte TYPE_BRANCH_REGISTER_RESULT = 12;
    /**
     * The constant TYPE_BRANCH_STATUS_REPORT.
     */
    public static final byte TYPE_BRANCH_STATUS_REPORT = 13;
    /**
     * The constant TYPE_BRANCH_STATUS_REPORT_RESULT.
     */
    public static final byte TYPE_BRANCH_STATUS_REPORT_RESULT = 14;

    /**
     * The constant TYPE_SEATA_MERGE.
     */
    public static final byte TYPE_SEATA_MERGE = 59;
    /**
     * The constant TYPE_SEATA_MERGE_RESULT.
     */
    public static final byte TYPE_SEATA_MERGE_RESULT = 60;

    /**
     * The constant TYPE_REG_CLT.
     */
    public static final byte TYPE_REG_CLT = 101;
    /**
     * The constant TYPE_REG_CLT_RESULT.
     */
    public static final byte TYPE_REG_CLT_RESULT = 102;
    /**
     * The constant TYPE_REG_RM.
     */
    public static final byte TYPE_REG_RM = 103;
    /**
     * The constant TYPE_REG_RM_RESULT.
     */
    public static final byte TYPE_REG_RM_RESULT = 104;
    /**
     * The constant TYPE_RM_DELETE_UNDOLOG.
     */
    public static final byte TYPE_RM_DELETE_UNDOLOG = 111;


    public static Class getTypeClass(byte codecType) {
        Class msgClass = null;
        switch (codecType) {
            case MessageType.TYPE_SEATA_MERGE:
                msgClass = GlobalBeginRequest.class;
                break;
            case MessageType.TYPE_SEATA_MERGE_RESULT:
                msgClass = GlobalBeginRequest.class;
                break;
            case MessageType.TYPE_REG_CLT:
                msgClass = GlobalBeginRequest.class;
                break;
            case MessageType.TYPE_REG_CLT_RESULT:
                msgClass = GlobalBeginRequest.class;
                break;
            case MessageType.TYPE_REG_RM:
                msgClass = GlobalBeginRequest.class;
                break;
            case MessageType.TYPE_REG_RM_RESULT:
                msgClass = GlobalBeginRequest.class;
                break;
            case MessageType.TYPE_BRANCH_COMMIT:
                msgClass = GlobalBeginRequest.class;
                break;
            case MessageType.TYPE_BRANCH_ROLLBACK:
                msgClass = GlobalBeginRequest.class;
                break;
            case MessageType.TYPE_GLOBAL_REPORT:
                msgClass = GlobalBeginRequest.class;
                break;
            default:
                break;
        }
        return msgClass;
    }
}
