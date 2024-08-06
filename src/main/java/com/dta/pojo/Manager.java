package com.dta.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager {
    private int managerID;
    private int salesVolume;
    private double salesGrowth;
    private int newCustomersNum;
    private double churnRate;
    private float satisfaction;
}
