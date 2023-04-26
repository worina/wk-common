package com.wk.common.utils;

/**
 * @program: AESUtil
 * @description:
 * @author: dm
 * @create: 2021-07-21 13:14
 */

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public class AESUtil {
    private static final String AESKEY = "pcJhwffxg+fRO2ckdmmBHVwHRDhED8Ep";
    /**
     *
     */
    private static final String KEY_ALGORITHM = "AES";
    /**
     * / / /
     * Java 6PKCS5Padding
     * Bouncy CastlePKCS7Padding
     */
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";
    static {
        //PKCS7Padding
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }
    /**
     *
     *
     * @throws Exception
     * @return
     */
    public static String generateKey() throws Exception {
        //
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        /**
         * AES
         * AES128192256javaAES128
         * 192256oraclejdk"Additional Resources"
         * "Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files",[DOWNLOAD]
         * local_policy.jarUS_export_policy.jarjdkjre/lib/security/
         */
        kg.init(128);
        //
        SecretKey secretKey = kg.generateKey();
        //
        return Base64.encodeBase64String(secretKey.getEncoded());
    }
    /**
     * AES
     *
     * @param source
     * @param key
     * @throws Exception
     * @return
     */
    public static String encrypt(String source, String key) throws Exception {
        byte[] sourceBytes = source.getBytes("UTF-8");
        byte[] keyBytes = Base64.decodeBase64(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, KEY_ALGORITHM));
        byte[] decrypted = cipher.doFinal(sourceBytes);
        return Base64.encodeBase64String(decrypted);
    }
    /**
     * AES
     *
     * @param encryptStr
     * @param key
     * @throws Exception
     * @return
     */
    public static String decrypt(String encryptStr, String key) throws Exception {
        byte[] sourceBytes = Base64.decodeBase64(encryptStr);
        byte[] keyBytes = Base64.decodeBase64(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, KEY_ALGORITHM));
        byte[] decoded = cipher.doFinal(sourceBytes);
        return new String(decoded, "UTF-8");
    }
}
