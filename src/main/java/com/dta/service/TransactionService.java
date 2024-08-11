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
     * @param jobNumber
     * @param startDate
     * @param endDate
     * @return
     */
    PageBean page(Integer page, Integer pageSize, String jobNumber, String startDate, String endDate);

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

    /**
     * 按管理员分页查询客户经理信息
     * @param page
     * @param pageSize
     * @param jobNumber
     * @return
     */
    PageBean managerPage(Integer page, Integer pageSize, String jobNumber);
}
