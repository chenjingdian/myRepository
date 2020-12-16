package com.biyou.controller;

import com.biyou.pojo.User;
import com.biyou.service.UserService;
import com.biyou.utils_entry.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RequestMapping("/user")
@RestController
public class RegistryController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @PostMapping("/registry")
    public Result registry(@RequestBody User user, HttpServletResponse response) {
        userService.addUser(user);
        String s = sessionId(user);
        Cookie cookie = new Cookie("sessionId", s);
        cookie.setPath("/");//如此可以 同一服务器共享cookie 即同一服务器根目录/下不同项目可共享
        cookie.setMaxAge(60 * 30);//表示寿命单位秒
        response.addCookie(cookie);
        return new Result(true, 1, "http://localhost:8000/");

    }

    private String sessionId(User user) {
        String sessionId = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(sessionId, user, 30, TimeUnit.MINUTES);
        return sessionId;
    }
}
