package com.biyou.job;

import com.biyou.feign.OperationFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyJob {

    @Autowired
    private OperationFeign operationFeign;
    /**
     * 按照标准时间来算，每隔 10s 执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void job1() {
        operationFeign.flushWeight();
        System.out.println("执行ing........");
    }
}
