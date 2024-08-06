package com.dta.mapper;

import com.dta.pojo.Manager;
import com.dta.pojo.Transaction;
import com.dta.pojo.User;
import com.dta.service.TransactionService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TransactionMapper {
    /**
     * 交易列表条件分页查询
     * @param customerManagerID
     * @return
     */
    List<Transaction> list(@Param("customerManagerID") Integer customerManagerID);

    /**
     * 批量删除交易
     * @param ids
     */
    void delete(@Param("ids") List<Integer> ids);

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
    @Select("select * from transaction where TransactionID = #{customerManagerID}")
    Transaction listById(Integer customerManagerID);

    /**
     * 修改交易
     * @param transaction
     */
    void update(Transaction transaction);

    /**
     * 根据管理员查询其对应的部门编号
     * @param administratorID
     * @return
     */
    @Select("select dept from user where id = #{administratorID}")
    Integer findDept(Integer administratorID);


    @Select("select id from user where dept = #{dept}")
    List<Integer> findManager(Integer dept);

    Manager findManagerInfo(@Param("managerid") Integer managerid);



}
