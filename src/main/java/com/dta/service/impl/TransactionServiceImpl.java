package com.dta.service.impl;

import com.dta.mapper.TransactionMapper;
import com.dta.pojo.PageBean;
import com.dta.pojo.Transaction;
import com.dta.pojo.User;
import com.dta.service.TransactionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionMapper transactionMapper;



    /**
     * 用户列表条件分页查询
     * @param page
     * @param pageSize
     * @param customerManagerID
     * @return
     */
    @Override
    public PageBean page(Integer page, Integer pageSize, Integer customerManagerID) {
        //1. 设置分页参数, 第一个参数表示从第几页开始，第二个参数表示一页显示多少条记录
        PageHelper.startPage(page, pageSize);

        //2. 执行查询
        List<User> userList = transactionMapper.list(customerManagerID);
        Page<User> p = (Page<User>) userList;

        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    /**
     * 批量删除交易（根据客户经理id）
     * @param ids
     */
    @Override
    public void delete(List<Integer> ids) {
        transactionMapper.delete(ids);
    }

    /**
     * 添加交易
     * @param transaction
     */
    @Override
    public void save(Transaction transaction) {
        if(transaction.getCreateDate()==null) transaction.setCreateDate(LocalDateTime.now());
        transaction.setUpdatedDate(LocalDateTime.now());
        transactionMapper.insert(transaction);
    }

    /**
     * 根据id查询交易
     * @param id
     * @return
     */
    @Override
    public Transaction listById(Integer id) {
        return transactionMapper.listById(id);
    }

    /**
     * 修改交易
     * @param transaction
     */
    @Override
    public void update(Transaction transaction) {
        transaction.setUpdatedDate(LocalDateTime.now());
        transactionMapper.update(transaction);
    }
}
