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
    private float salesGrowth;
    private int newDevelpment;
    private float churnRate;
    private float satisfaction;
}
