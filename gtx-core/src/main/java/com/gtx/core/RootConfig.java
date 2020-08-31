package com.gtx.core;

/**
 * @author llt
 * @date 2020-08-31 23:39
 */
public class RootConfig {
    private static String serverIp;
    private static int serverPort;

    public static String getServerIp() {
        return serverIp;
    }

    public static void setServerIp(String serverIp) {
        RootConfig.serverIp = serverIp;
    }

    public static int getServerPort() {

        return serverPort;
    }

    public static void setServerPort(int serverPort) {
        RootConfig.serverPort = serverPort;
    }
}
