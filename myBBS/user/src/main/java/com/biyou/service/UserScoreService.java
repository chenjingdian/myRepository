package com.biyou.service;

import org.springframework.transaction.annotation.Transactional;

public interface UserScoreService {
    //对某个用户,增加他的积分
    @Transactional
    void increase (Long uid,Integer inc);
}
