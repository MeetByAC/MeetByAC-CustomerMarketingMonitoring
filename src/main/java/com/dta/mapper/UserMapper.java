package com.dta.mapper;

import com.dta.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 用户列表条件分页查询
     *
     * @param jobNumber
     * @param dept
     * @param role
     * @return
     */
    List<User> list(@Param("jobNumber") String jobNumber, @Param("dept") Short dept, @Param("role") Short role);

    /**
     * 批量删除用户
     *
     * @param ids
     */
    void delete(@Param("ids") List<Integer> ids);

    /**
     * 添加用户
     *
     * @param user
     */
    @Insert("insert into user(username, password, jobNumber, dept, role, create_time, update_time) " +
            "values (#{username}, #{password}, #{jobNumber}, #{dept}, #{role}, #{createTime}, #{updateTime})")
    void insert(User user);

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User listById(Integer id);

    /**
     * 修改用户
     *
     * @param user
     */
    void update(User user);

    /**
     * 根据前端传来的工号，密码和职位判断是否存在
     *
     * @param jobNumber       工号
     * @param encryptPassword 加密后的密码
     * @return
     */
    @Select("SELECT * FROM user " +
            "WHERE jobNumber = #{jobNumber} " +
            "AND password = #{encryptPassword} " +
            "AND role = #{role}")
    User findUser(@Param("jobNumber") String jobNumber, @Param("encryptPassword") String encryptPassword, @Param("role") Integer role);

    /**
     * 查找部门
     *
     * @param dept
     * @return
     */
    @Select("select name from dept where id=#{dept}")
    String findDept(@Param("dept") Integer dept);
}
