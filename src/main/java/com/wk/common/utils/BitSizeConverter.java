package com.wk.common.utils;

import java.text.DecimalFormat;

public class BitSizeConverter {
    public static String getSize(long size) {
        //获取到的size为：1705230
        // 定义GB的计算常量
        int GB = 1024 * 1024 * 1024;
        //定义MB的计算常量
        int MB = 1024 * 1024;
        //定义KB的计算常量
        int KB = 1024;
        //格式化小数
        DecimalFormat df = new DecimalFormat("0.00");
        String resultSize = "";
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = df.format(size / (float) GB) + "GB";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = df.format(size / (float) MB) + "MB";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = df.format(size / (float) KB) + "KB";
        } else {
            resultSize = size + "B";
        }
        return resultSize;
    }
}
