package com.dta.controller;

import com.alibaba.fastjson.JSONObject;
import com.dta.mapper.UserMapper;
import com.dta.pojo.Result;
import com.dta.pojo.User;
import com.dta.service.UserService;
import com.dta.utils.JwtUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

//国密SM2
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.asn1.gm.GMObjectIdentifiers;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import org.springframework.web.bind.annotation.*;


/**
 * 登陆验证
 */
@RestController
public class LoginController {
    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    //确保BouncyCastle提供者被添加到Java的安全提供者列表中，提供SM2加密算法的实现
//    static {
//        Security.addProvider(new BouncyCastleProvider());
//    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){


        User userLogin = userService.userLogin(user);

        if(userLogin == null){
            //登录失败;没有找到用户名或者密码
            return Result.error("登录失败！");
        }else {
            //登录成功
            Map<String,Object> map = new HashMap<>();
            map.put("userName",userLogin.getUsername());
            map.put("jobNumber",userLogin.getJobNumber());
            map.put("role",userLogin.getRole());
            map.put("dept",userMapper.findDept(userLogin.getDept()));
            //生成令牌
            String jwt = JwtUtils.generateJwt(map);

            //把生成的令牌封装到响应数据中
            return Result.success(jwt);
        }
    }

    //加入国密SM2之后的登录接口
//    @PostMapping("/login")
//    public Result login(@RequestBody Map<String, String> body){
//        try {
//            String encryptedData = body.get("encrypted");
//            String privateKeyStr = "你的SM2私钥"; // 替换为实际的SM2私钥
//
//            byte[] privateKeyBytes = Hex.decode(privateKeyStr);
//            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
//            KeyFactory keyFactory = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
//            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
//
//            Cipher cipher = Cipher.getInstance("SM2", BouncyCastleProvider.PROVIDER_NAME);
//            cipher.init(Cipher.DECRYPT_MODE, privateKey);
//
//            byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
//            String decryptedText = new String(decryptedData, StandardCharsets.UTF_8);
//
//            // 解析用户信息
//            JSONObject userInfo = new JSONObject(decryptedText.isEmpty());
//            String jobNumber = userInfo.getString("jobNumber");
//            String password = userInfo.getString("password");
//            int role = userInfo.getInteger("role");
//
//            //在数据库中进行查询
//            User newUser = new User();
//            newUser.setJobNumber(jobNumber);
//            newUser.setPassword(password);
//            newUser.setRole(role);
//
//            User userLogin = userService.userLogin(newUser);
//
//            if(userLogin == null){
//                 //登录失败;没有找到用户名或者密码
//                 return Result.error("登录失败！");
//            }else {
//                //登录成功
//                Map<String,Object> map = new HashMap<>();
//                map.put("userName",userLogin.getUsername());
//                map.put("jobNumber",userLogin.getJobNumber());
//                map.put("role",userLogin.getRole());
//                map.put("dept",userMapper.findDept(userLogin.getDept()));
//                //生成令牌
//                String jwt = JwtUtils.generateJwt(map);
//
//                //把生成的令牌封装到响应数据中
//                return Result.success(jwt);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.error("登录失败！");
//        }
//    }

    @GetMapping("/logout")
    public Result logout(){
        return Result.success();
    }
}
