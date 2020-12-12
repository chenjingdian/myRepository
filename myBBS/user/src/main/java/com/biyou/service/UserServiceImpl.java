package com.biyou.service;

import com.biyou.dao.UserMapper;
import com.biyou.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public int addUser(User user) {
        return userMapper.insert(user);
    }
}
