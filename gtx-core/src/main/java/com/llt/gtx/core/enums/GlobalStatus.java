package com.llt.gtx.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public enum GlobalStatus {
    /**
     * 未知
     */
    UN_KNOWN(0),

    /**
     * 开始
     */
    BEGIN(1),
    BEGIN_FAILED(2),

    /**
     * 提交中
     */
    COMMITTING(3),

    /**
     * 提交重试
     */
    COMMIT_RETRYING(4),

    /**
     * 回滚中
     */
    ROLL_BACKING(5),

    /**
     * 回滚重试
     */
    ROLLBACK_RETRYING(6),


    /**
     * 提交成功
     */
    COMMITTED(7),

    /**
     * 提交失败
     */
    COMMIT_FAILED(8),

    /**
     * 回滚完成
     */
    // Finally: global transaction is successfully rollbacked.
    ROLL_BACKED(9),

    /**
     * 回滚失败
     */
    ROLLBACK_FAILED(10),

    /**
     * 已结束
     */
    FINISHED(11);

    private int code;

    GlobalStatus(int code) {
        this.code = code;
    }


    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }



    private static final Map<Integer, GlobalStatus> MAP = new HashMap<>(values().length);

    static {
        for (GlobalStatus status : values()) {
            MAP.put(status.code, status);
        }
    }

    /**
     * Get global status.
     *
     * @param code the code
     * @return the global status
     */
    public static GlobalStatus get(byte code) {
        return get((int)code);
    }

    /**
     * Get global status.
     *
     * @param code the code
     * @return the global status
     */
    public static GlobalStatus get(int code) {
        GlobalStatus status = MAP.get(code);

        if (null == status) {
            throw new IllegalArgumentException("Unknown GlobalStatus[" + code + "]");
        }

        return status;
    }
}
