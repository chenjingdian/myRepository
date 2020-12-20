package com.biyou.service;

import com.biyou.dao.UserMapper;
import com.biyou.dao.UserScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserScoreServiceImpl implements UserScoreService {
    @Autowired
    private UserScoreMapper userScoreMapper;

    @Override
    public void increase(Long uid, Integer inc) {

        //模拟提供方出现异常
        int e = 1 / 0;
        userScoreMapper.increase(uid, inc);
    }
}
