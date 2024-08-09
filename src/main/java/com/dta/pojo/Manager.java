package com.dta.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager {
    private int managerID; //客户经理ID
    private String managerName; //客户经理姓名
    private int salesVolume; //销售总额
    private double salesGrowth;//销售增长率（今年销售额-去年销售额）/去年销售额
    private int newCustomersNum;//新客户数（今天交易中新出现的客户数量）
    private double churnRate;//客户流失率（老客户中今年仍然有新交易的比例）
    private float satisfaction;//客户满意度（所有交易客户评分的平均数）
}
