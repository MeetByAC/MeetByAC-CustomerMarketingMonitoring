package com.dta.service.impl;

import com.dta.mapper.UserMapper;
import com.dta.pojo.PageBean;
import com.dta.pojo.Result;
import com.dta.pojo.User;
import com.dta.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.awt.geom.RectangularShape;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    /**
     * 盐值，加强密码校验
     */
    private static final String SALT = "CQ";

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户列表条件分页查询
     * @param page
     * @param pageSize
     * @param jobNumber
     * @param dept
     * @param role
     * @return
     */
    @Override
    public PageBean page(Integer page, Integer pageSize, String jobNumber, Short dept, Short role) {
        //1. 设置分页参数, 第一个参数表示从第几页开始，第二个参数表示一页显示多少条记录
        PageHelper.startPage(page, pageSize);

        //2. 执行查询
        List<User> userList = userMapper.list(jobNumber, dept, role);
        Page<User> p = (Page<User>) userList;

        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    /**
     * 批量删除用户
     * @param ids
     */
    @Override
    public void delete(List<Integer> ids) {
        userMapper.delete(ids);
    }

    /**
     * 添加用户
     * @param user
     */
    @Override
    public void save(User user) {
        //如果没有设置密码，则使用123456作为默认密码
        if(user.getPassword() == null || user.getPassword() == ""){
            user.setPassword("123456");
        }
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    /**
     * 根据工号查询用户
     * @param jobNumber
     * @return
     */
    @Override
    public User listByjobNumber(String jobNumber) {
        return userMapper.listByjobNumber(jobNumber);
    }

    /**
     * 修改用户
     * @param user
     */
    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @Override
    public User userLogin(User user) {

        String jobNumber = user.getJobNumber();
        String password = user.getPassword();
        int role = user.getRole();
        //1.校验
        //非空
        if(StringUtils.isAnyBlank(jobNumber,password)){
            return null;
        }
        //2.加密
        //String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        //System.out.println(encryptPassword);
        //账户不能重复
        User userLogin = userMapper.findUser(jobNumber,password,role);

        return userLogin;
    }
}
