package com.dta.mapper;

import com.dta.pojo.Transaction;
import com.dta.pojo.User;
import com.dta.service.TransactionService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TransactionMapper {
    /**
     * 交易列表条件分页查询
     * @param customerManagerID
     * @return
     */
    List<User> list(@Param("CustomerManagerID") Integer customerManagerID);

    /**
     * 批量删除交易
     * @param customerManagerID
     */
    void delete(@Param("TransactionID") List<Integer> customerManagerID);

    /**
     * 添加交易
     * @param transaction
     */
    @Insert("insert into transaction(CustomerManagerID, CustomerName, TransactionDate, ProductName, TransactionAmount, MarketingProgress, CustomerRating, CreatedDate, UpdatedDate) " +
            "values (#{customerManagerID}, #{customerName}, #{transactionDate}, #{productName}, #{transactionAmount}, #{marketingProgress}, #{customerRating}, #{createdDate}, #{updatedDate})")
    void insert(Transaction transaction);

    /**
     * 根据客户经理id查询（暂时用不上）
     * @param customerManagerID
     * @return
     */
    @Select("select * from user where CustomerManagerID = #{customerManagerID}")
    Transaction listById(Integer customerManagerID);

    /**
     * 修改交易
     * @param transaction
     */
    void update(Transaction transaction);



}
