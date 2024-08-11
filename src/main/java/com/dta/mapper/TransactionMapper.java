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
     * @param jobNumber
     * @return
     */
    List<Transaction> list(@Param("jobNumber") String jobNumber,
                           @Param("startDate") String startDate,
                           @Param("endDate") String endDate);

    /**
     * 批量删除交易
     * @param ids
     */
    void delete(@Param("ids") List<Integer> ids);

    /**
     * 添加交易
     * @param transaction
     */
    @Insert("insert into transaction(JobNumber, CustomerName, TransactionDate, ProductName, TransactionAmount, MarketingProgress, CustomerRating, CreatedDate, UpdatedDate) " +
            "values (#{jobNumber}, #{customerName}, #{transactionDate}, #{productName}, #{transactionAmount}, #{marketingProgress}, #{customerRating}, #{createdDate}, #{updatedDate})")
    void insert(Transaction transaction);

    /**
     * 根据交易id查询（暂时用不上）
     * @param id
     * @return
     */
    @Select("select * from transaction where TransactionID = #{id}")
    Transaction listById(Integer id);

    /**
     * 修改交易
     * @param transaction
     */
    void update(Transaction transaction);

    /**
     * 根据管理员查询其对应的部门编号
     * @param jobNumber
     * @return
     */
    @Select("select dept from user where jobNumber = #{jobNumber}")
    Integer findDept(String jobNumber);

    /**
     * 查找该部门下面的客户经理id
     * @param dept
     * @return
     */
    @Select("select jobNumber from user where dept = #{dept}")
    List<String> findManager(Integer dept);

    /**
     * 查找该客户经理的销售总额
     * @param mJobNumber
     * @return
     */
    @Select("select COALESCE(SUM(TransactionAmount), 0) from transaction where JobNumber = #{mJobNumber}")
    Integer findSalesVolume(String mJobNumber);

    /**
     * 查找该客户经理今年的销售额
     * @param mJobNumber
     * @return
     */
    @Select("SELECT COALESCE(SUM(TransactionAmount),0) AS AmountTYear FROM transaction WHERE EXTRACT(YEAR FROM TransactionDate) = EXTRACT(YEAR FROM CURRENT_DATE) AND JobNumber = #{mJobNumber};")
    Integer findSalesGrowthTYear(String mJobNumber);

    /**
     * 查找该客户经理去年的销售额
     * @param mJobNumber
     * @return
     */
    @Select("SELECT COALESCE(SUM(TransactionAmount),0) AS AmountLYear FROM transaction WHERE EXTRACT(YEAR FROM TransactionDate) = EXTRACT(YEAR FROM CURRENT_DATE)-1 AND JobNumber = #{mJobNumber};")
    Integer findSalesGrowthLYear(String mJobNumber);

    /**
     *
     * 查找该客户经理开发的新客户数量
     * @param mJobNumber
     * @return
     */
    @Select("SELECT COALESCE(COUNT(DISTINCT customerName),0) AS unique_customers_count " +
            "FROM transaction " +
            "WHERE transactionDate >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) " +
            "AND customerName NOT IN ( " +
            "    SELECT customerName" +
            "    FROM transaction " +
            "    WHERE transactionDate < DATE_SUB(CURDATE(), INTERVAL 1 YEAR) " +
            "    AND JobNumber = #{mJobNumber}" +
            ");")
    Integer findNewCustomersNum(String mJobNumber);

    /**
     * 查找该客户经理老客户Name
     * @param mJobNumber
     * @return
     */
    @Select("SELECT DISTINCT customerName " +
            "FROM transaction " +
            "WHERE customerName IN (" +
            "    SELECT customerName " +
            "    FROM transaction" +
            "    WHERE JobNumber = #{mJobNumber}" +
            "    GROUP BY customerName" +
            "    HAVING MIN(transactionDate) <= DATE_SUB(CURDATE(), INTERVAL 1 YEAR)" +
            ");")
    List<String> findOldCustomers(String mJobNumber);

    /**
     * 查找该客户经理老客户留存数量
     * @param mJobNumber
     * @param oldCustomers
     * @return
     */
    Integer findChurnNum(@Param("mJobNumber") String mJobNumber, @Param("oldCustomers") List<String> oldCustomers);

    /**
     * 计算该客户经理满意度
     * @param mJobNumber
     * @return
     */
    @Select("SELECT COALESCE(AVG(customerRating), 0) AS average_rating\n" +
            "FROM transaction " +
            "WHERE jobNumber = #{mJobNumber};")
    float findSatisfaction(String mJobNumber);

    /**
     * 查询客户经理姓名
     * @param mJobNumber
     * @return
     */
    @Select("SELECT username FROM user WHERE jobNumber = #{mJobNumber}")
    String findManagerName(String mJobNumber);

    @Select("SELECT id FROM user WHERE jobNumber = #{mJobNumber}")
    Integer findManagerID(String mJobNumber);

}
