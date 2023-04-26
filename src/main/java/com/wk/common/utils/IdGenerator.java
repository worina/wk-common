package com.wk.common.utils;

import cn.hutool.core.net.NetUtil;
import jodd.util.StringUtil;

import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {

    private static final String EMPTY_STRING = "";
    private static String P_ID_CACHE = null;
    private static String IP_16 = "ffffffff";
    private static AtomicInteger COUNT = new AtomicInteger(1000);

    static {
        try {
            String ipAddress = findLocalAddress();
            if (null != ipAddress) {
                IP_16 = getIp16(ipAddress);
            }
        } catch (Exception e) {

        }
    }

    public static String generate() {
        return getId(IP_16, System.currentTimeMillis(), getNextId());
    }

    /**
     * @param ip
     * @param timestamp
     * @param nextId
     * @return
     */
    private static String getId(String ip, long timestamp, int nextId) {
        StringBuilder appender = new StringBuilder(30);
        appender.append(ip).append(timestamp).append(nextId).append(getPid());
        return appender.toString();
    }

    /**
     * @return
     */
    private static int getNextId() {
        for (; ; ) {
            int current = COUNT.get();
            int next = (current > 9000) ? 1000 : current + 1;
            if (COUNT.compareAndSet(current, next)) {
                return next;
            }
        }
    }

    /**
     * @return
     */
    private static String getPid() {
        if (P_ID_CACHE != null) {
            return P_ID_CACHE;
        }
        String processName = ManagementFactory.getRuntimeMXBean().getName();

        if (isBlank(processName)) {
            return EMPTY_STRING;
        }

        String[] processSplitName = processName.split("@");

        if (processSplitName.length == 0) {
            return EMPTY_STRING;
        }

        String pid = processSplitName[0];

        if (isBlank(pid)) {
            return EMPTY_STRING;
        }
        P_ID_CACHE = pid;
        return pid;
    }

    /**
     * @param str
     * @return
     */
    private static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param ip
     * @return
     */
    private static String getIp16(String ip) {
        String[] ips = ip.split("\\.");
        StringBuilder sb = new StringBuilder();
        for (String column : ips) {
            String hex = Integer.toHexString(Integer.parseInt(column));
            if (hex.length() == 1) {
                sb.append('0').append(hex);
            } else {
                sb.append(hex);
            }
        }
        return sb.toString();
    }

    /**
     *
     * @return
     */
    private static String findLocalAddress() {
        String ip = NetUtil.getLocalhostStr();
        if (StringUtil.isNotBlank(ip)) {
            return ip;
        }
        return "127.0.0.1";
    }
}
