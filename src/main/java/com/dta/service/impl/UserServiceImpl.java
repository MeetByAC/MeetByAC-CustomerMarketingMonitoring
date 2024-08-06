package com.dta.service.impl;

import com.dta.mapper.UserMapper;
import com.dta.pojo.PageBean;
import com.dta.pojo.User;
import com.dta.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

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
        user.setPassword("123456");//这不是固定了密码了吗？
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Override
    public User listById(Integer id) {
        return userMapper.listById(id);
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


}
