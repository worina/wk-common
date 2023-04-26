package com.wk.common.utils;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;

import java.io.UnsupportedEncodingException;

/**
 * Base64工具
 *
 */
public class Base64ConvertUtil {
    private Base64ConvertUtil() {
    }

    /**
     * 加密
     *
     * @param input
     * @return java.lang.String
     */
    public static String encode(String input) throws UnsupportedEncodingException {
        return Base64Encoder.encode(input);
    }

    /**
     * 解密
     *
     * @param input
     * @return java.lang.String
     */
    public static String decode(String input) throws UnsupportedEncodingException {
        return Base64Decoder.decodeStr(input);
    }

}
