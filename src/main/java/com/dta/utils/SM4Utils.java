package com.dta.utils;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

public class SM4Utils {

    private static final String ENCODING = "UTF-8";


    /**
     * 使用SM4加密
     * @param plaintext 明文
     * @param key 密钥（16字节）
     * @return 加密后的Hex编码字符串
     * @throws Exception 异常
     */
    public static String encrypt(String plaintext, String key) throws Exception {
        byte[] keyBytes = key.getBytes(ENCODING);
        byte[] plaintextBytes = plaintext.getBytes(ENCODING);

        SM4Engine engine = new SM4Engine();
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(engine);

        cipher.init(true, new KeyParameter(keyBytes)); // true 表示加密
        byte[] encryptedBytes = new byte[cipher.getOutputSize(plaintextBytes.length)];
        int len = cipher.processBytes(plaintextBytes, 0, plaintextBytes.length, encryptedBytes, 0);
        len += cipher.doFinal(encryptedBytes, len);

        return Hex.toHexString(encryptedBytes);
    }

    /**
     * 使用SM4解密
     * @param encryptedData 加密的Hex编码字符串
     * @param key 密钥（16字节）
     * @return 解密后的明文
     * @throws Exception 异常
     */
    public static String decrypt(String encryptedData, String key) throws Exception {
        // 将密钥从十六进制字符串转换为字节数组
        byte[] keyBytes = hexStringToByteArray(key);

        byte[] encryptedBytes = Hex.decode(encryptedData);

        SM4Engine engine = new SM4Engine();
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(engine);

        cipher.init(false, new KeyParameter(keyBytes)); // false 表示解密
        byte[] decryptedBytes = new byte[cipher.getOutputSize(encryptedBytes.length)];
        int len = cipher.processBytes(encryptedBytes, 0, encryptedBytes.length, decryptedBytes, 0);
        len += cipher.doFinal(decryptedBytes, len);

        return new String(decryptedBytes, 0, len, ENCODING);
    }

    /**
     *
     * 辅助函数：将十六进制字符串转换为字节数组
     * @param s
     * @return
     */
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

}

