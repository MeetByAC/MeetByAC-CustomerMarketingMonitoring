package com.dta.controller;

import com.dta.pojo.PageBean;
import com.dta.pojo.Result;
import com.dta.pojo.Transaction;
import com.dta.pojo.User;
import com.dta.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 营销管理Controller
 */
@Slf4j
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/managerPage")
    public Result managerPage(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       Integer administratorID){
        log.info("交易列表分页查询, {},{},{}",page, pageSize, administratorID);
        //调用service进行用户列表分页查询
        PageBean pageBean = transactionService.managerPage(page, pageSize, administratorID);
        return Result.success(pageBean);
    }

    /**
     * 交易按客户经理id分页查询
     * @param page
     * @param pageSize
     * @param customerManagerID
     * @return
     */
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       Integer customerManagerID){
        log.info("交易列表分页查询, {},{},{}", page, pageSize, customerManagerID);
        //调用service进行用户列表分页查询
        PageBean pageBean = transactionService.page(page, pageSize, customerManagerID);
        return Result.success(pageBean);
    }

    /**
     * 批量删除交易
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("批量删除交易操作, ids: {}", ids);
        //调用service批量删除用户
        transactionService.delete(ids);
        return Result.success();
    }

    /**
     * 添加交易
     * @param transaction
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Transaction transaction){
        log.info("添加交易操作, user: {}", transaction);
        //调用service添加用户
        transactionService.save(transaction);
        return Result.success();
    }

    /**
     * 根据id查询交易
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result listById(@PathVariable Integer id){
        log.info("根据id查询交易, id: {}", id);
        //调用service根据id查询用户
        Transaction transaction = transactionService.listById(id);
        return Result.success(transaction);
    }

    /**
     * 修改交易信息
     * @param transaction
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Transaction transaction){
        log.info("修改交易信息，user: {}", transaction);
        transactionService.update(transaction);
        return Result.success();
    }




}
