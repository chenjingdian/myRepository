package com.biyou.service;

public interface UserScoreService {
    //对某个用户,增加他的积分
    void increase (Long uid,Integer inc);
}
