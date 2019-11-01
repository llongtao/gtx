package com.llt.gtx.tm.api;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public enum  GlobalTransactionRole {
    /**
     * The Launcher.
     */
    // The one begins the current global transaction.
    LAUNCHER,

    /**
     * The Participant.
     */
    // The one just joins into a existing global transaction.
    PARTICIPANT
}
