package com.biyou.feign;


import org.springframework.stereotype.Component;


/**
 * 消费方降级 一个类 实现 feign 接口 即可
 */
@Component
public class UserFeignFailBack implements UserFeign {
    /**
     * 降级方法
     * @param uid
     * @param inc
     */
    @Override
    public void increase(Long uid, Integer inc) {
        System.out.println("消费方 用户积分增长 降级");
    }
}
