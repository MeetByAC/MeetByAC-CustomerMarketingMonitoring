package com.dta.service;

import com.dta.pojo.PageBean;
import com.dta.pojo.Transaction;
import com.dta.pojo.User;

import java.util.List;

public interface TransactionService {


    /**
     * 交易列表条件分页查询
     * @param page
     * @param pageSize
     * @param customerManagerID
     * @return
     */
    PageBean Transactionpage(Integer page, Integer pageSize, Integer customerManagerID);

    /**
     * 批量删除交易
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 添加交易
     * @param transaction
     */
    void save(Transaction transaction);

    /**
     * 根据id查询交易
     * @param id
     * @return
     */
    Transaction listById(Integer id);

    /**
     * 修改用户
     * @param transaction
     */
    void update(Transaction transaction);



}
