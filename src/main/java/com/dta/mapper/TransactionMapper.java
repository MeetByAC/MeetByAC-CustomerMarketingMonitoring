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

    /**
     * 查找该部门下面的客户经理id
     * @param dept
     * @return
     */
    @Select("select id from user where dept = #{dept}")
    List<Integer> findManager(Integer dept);

    /**
     * 查找该客户经理的销售总额
     * @param managerid
     * @return
     */
    @Select("select COALESCE(SUM(TransactionAmount), 0) from transaction where CustomerManagerID = #{managerid}")
    Integer findSalesVolume(Integer managerid);

    /**
     * 查找该客户经理今年的销售额
     * @param managerid
     * @return
     */
    @Select("SELECT COALESCE(SUM(TransactionAmount),0) AS AmountTYear FROM transaction WHERE EXTRACT(YEAR FROM TransactionDate) = EXTRACT(YEAR FROM CURRENT_DATE) AND CustomerManagerID = #{managerid};")
    Integer findSalesGrowthTYear(Integer managerid);

    /**
     * 查找该客户经理去年的销售额
     * @param managerid
     * @return
     */
    @Select("SELECT COALESCE(SUM(TransactionAmount),0) AS AmountLYear FROM transaction WHERE EXTRACT(YEAR FROM TransactionDate) = EXTRACT(YEAR FROM CURRENT_DATE)-1 AND CustomerManagerID = #{managerid};")
    Integer findSalesGrowthLYear(Integer managerid);

    /**
     *
     * 查找该客户经理开发的新客户数量
     * @param managerid
     * @return
     */
    @Select("SELECT COALESCE(COUNT(DISTINCT customerName),0) AS unique_customers_count " +
            "FROM transaction " +
            "WHERE transactionDate >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) " +
            "AND customerName NOT IN ( " +
            "    SELECT customerName" +
            "    FROM transaction " +
            "    WHERE transactionDate < DATE_SUB(CURDATE(), INTERVAL 1 YEAR) " +
            "    AND CustomerManagerID = #{managerid}" +
            ");")
    Integer findNewCustomersNum(Integer managerid);

    /**
     * 查找该客户经理老客户Name
     * @param managerid
     * @return
     */
    @Select("SELECT DISTINCT customerName " +
            "FROM transaction " +
            "WHERE customerName IN (" +
            "    SELECT customerName " +
            "    FROM transaction" +
            "    WHERE CustomerManagerID = #{managerid}" +
            "    GROUP BY customerName" +
            "    HAVING MIN(transactionDate) <= DATE_SUB(CURDATE(), INTERVAL 1 YEAR)" +
            ");")
    List<String> findOldCustomers(Integer managerid);

    /**
     * 查找该客户经理老客户留存数量
     * @param managerid
     * @param oldCustomers
     * @return
     */
    Integer findChurnNum(@Param("managerid") Integer managerid, @Param("oldCustomers") List<String> oldCustomers);

    /**
     * 计算该客户经理满意度
     * @param managerid
     * @return
     */
    @Select("SELECT COALESCE(AVG(customerRating), 0) AS average_rating\n" +
            "FROM transaction\n" +
            "WHERE customerManagerID = #{managerid};")
    float findSatisfaction(Integer managerid);

}
