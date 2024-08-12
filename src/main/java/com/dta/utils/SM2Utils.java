package com.dta.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

public class SM2Utils {


    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 生成密钥对
     */
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
        keyPairGenerator.initialize(new ECGenParameterSpec("sm2p256v1"), new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 加密
     *
     * @param publicKey
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(PublicKey publicKey, JSONObject data) throws Exception {
        String jsonData = data.toJSONString();
        Cipher cipher = Cipher.getInstance("SM2", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedData = cipher.doFinal(jsonData.getBytes(StandardCharsets.UTF_8));
        return Hex.toHexString(encryptedData);
    }

    /**
     * 解密
     *
     * @param privateKeyString
     * @param encryptedData
     * @return
     * @throws Exception
     */
    public static String decrypt(String privateKeyString, String encryptedData) throws Exception {
        // Convert the private key from String to PrivateKey
        byte[] privateKeyEncoded = Hex.decode(privateKeyString);
        KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyEncoded);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);


        Cipher cipher = Cipher.getInstance("SM2", "BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedData = cipher.doFinal(Hex.decode(encryptedData));
        String decryptedJson = new String(decryptedData, StandardCharsets.UTF_8);
        return decryptedJson;
    }
}
