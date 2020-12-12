package com.biyou.service;

import com.biyou.pojo.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    //注册新用户
    @Transactional
    int addUser(User user);

    //检查用户名 密码
    int check(User user);
}
