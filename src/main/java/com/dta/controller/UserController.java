package com.dta.controller;

import com.dta.pojo.PageBean;
import com.dta.pojo.Result;
import com.dta.pojo.User;
import com.dta.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理Controller
 */
@Slf4j
@RestController
@RequestMapping("/users")

public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户列表条件分页查询
     * @param page
     * @param pageSize
     * @param jobNumber
     * @param dept
     * @param role
     * @return
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String jobNumber, Short dept, Short role){
        log.info("用户列表分页查询, {},{},{},{},{}", page, pageSize, jobNumber, dept, role);
        //调用service进行用户列表分页查询
        PageBean pageBean = userService.page(page, pageSize, jobNumber, dept, role);
        return Result.success(pageBean);
    }

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("批量删除用户操作, ids: {}", ids);
        //调用service批量删除用户
        userService.delete(ids);
        return Result.success();
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping
    public Result save(@RequestBody User user){
        log.info("添加用户操作, user: {}", user);
        //调用service添加用户
        userService.save(user);
        return Result.success();
    }

    /**
     * 根据工号查询用户
     * @param jobNumber
     * @return
     */
    @GetMapping("/{jobNumber}")
    public Result listByjobNumber(@PathVariable String jobNumber){
        log.info("根据工号查询用户, jobNumber: {}", jobNumber);
        //调用service根据工号查询用户
        User user = userService.listByjobNumber(jobNumber);
        return Result.success(user);
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @PutMapping
    public Result update(@RequestBody User user){
        log.info("修改用户信息，user: {}", user);
        userService.update(user);
        return Result.success();
    }
}
