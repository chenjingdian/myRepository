package com.biyou;

import com.biyou.service.ArticleService;
import com.biyou.utils_entry.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
public class OperationApplication {
    public static void main(String[] args) {
        SpringApplication.run(OperationApplication.class, args);
    }

    @Autowired
    private ArticleService articleService;

    @Bean
    public IdWorker getIDworker() {
        return new IdWorker();
    }


    @Scheduled(cron = "0/10 * * * * ?")
    public void tast(){
        System.out.println("定时任务执行了    ......    ");

        articleService.flushWeight();

    }
}
