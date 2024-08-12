package com.dta.service;

import com.dta.pojo.PageBean;
import com.dta.pojo.User;

import java.util.List;

public interface UserService {
    /**
     * 用户列表条件分页查询
     * @param page
     * @param pageSize
     * @param jobNumber
     * @param dept
     * @param role
     * @return
     */
    PageBean page(Integer page, Integer pageSize, String jobNumber, Short dept, Short role);

    /**
     * 批量删除用户
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 添加用户
     * @param user
     */
    void save(User user);

    /**
     * 根据工号查询用户
     * @param jobNumber
     * @return
     */
    User listByjobNumber(String jobNumber);

    /**
     * 修改用户
     * @param user
     */
    void update(User user);

    /**
     * 用户登陆
     * @param user
     * @return
     */
    User userLogin(User user);
}
