package com.biyou.service;

import com.biyou.dao.UserMapper;
import com.biyou.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public int addUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int check(User user) {
        List<User> list = userMapper.select(user);
        return list.size();
    }
}
