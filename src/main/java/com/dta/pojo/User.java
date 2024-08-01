package com.dta.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id; //ID
    private String username; //用户名
    private String password; //密码
    private String jobNumber; //工号
    private Integer dept; //部门号
    private Integer role; //角色
    private LocalDateTime createTime; //创建时间
    private LocalDateTime updateTime; //修改时间
}
