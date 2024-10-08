package com.dta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class CustomerMarketingMonitoringApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerMarketingMonitoringApplication.class, args);
    }

}
