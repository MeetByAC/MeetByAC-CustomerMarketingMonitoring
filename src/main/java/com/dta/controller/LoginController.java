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
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import org.springframework.web.bind.annotation.*;

import static com.dta.utils.SM2Utils.decrypt;


/**
 * 登陆验证
 */
@RestController
public class LoginController {
    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    //SM2 私钥
    private static final String PRIVATE_KEY = "308193020100301306072a8648ce3d020106082a811ccf5501822d04793077020101042024ff295180b9323dda62df6147012a3a3c0fb8de29de48dc6eb2c8141f85e699a00a06082a811ccf5501822da1440342000460c160974d6501cb8776b97bc2d0bac854facd7f8d7595f8653fbcb8c8ab89d34dd0cf52d865ceb19ef9a4a67b1dfe6052b7fc323aa3b680a785c74c258f197f";


    //加入国密SM2之后的登录接口
    @PostMapping("/login")
    public Result login(@RequestBody User user) throws Exception {

        //对加密数据进行解密
        String encryptPassword = user.getPassword();
        String decryptPassword = decrypt(PRIVATE_KEY,encryptPassword);
        user.setPassword(decryptPassword);

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

    @GetMapping("/logout")
    public Result logout(){
        return Result.success();
    }
}
