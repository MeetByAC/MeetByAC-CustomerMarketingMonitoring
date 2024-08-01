package com.dta.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private int transactionID; //交易ID
    private int customerManagerID; //客户经理ID
    private String customerName; //客户姓名
    private LocalDateTime transactionDate; //交易日期
    private String productName; //产品类型
    private BigDecimal transactionAmount; //交易金额，格式为10位数字，2位小数
    private String marketingProgress; //营销进度
    private int customerRating; //客户评分，范围1-5
    private LocalDateTime createTime; //创建时间
    private LocalDateTime UpdatedDate; //更新时间
}
