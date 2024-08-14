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


import static com.dta.utils.SM4Utils.decrypt;


/**
 * 登陆验证
 */
@RestController
public class LoginController {
    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    //SM4 私钥
    private static final String KEY = "010203040506070809000a0b0c0d0e0f";

    //加入国密SM4之后的登录接口
    @PostMapping("/login")
    public Result login(@RequestBody User user) throws Exception {

        //对加密数据进行解密
        String encryptPassword = user.getPassword();
        String decryptPassword = decrypt(encryptPassword,KEY);
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
