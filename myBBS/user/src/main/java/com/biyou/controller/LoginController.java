package com.biyou.controller;

import com.biyou.pojo.User;
import com.biyou.service.UserService;
import com.biyou.utils_entry.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RequestMapping("/user")
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/login")
    public Result login(User user, HttpServletResponse response) {
        int check = userService.check(user);
        if (check > 0) {
            String s = sessionId(user);
            Cookie cookie = new Cookie("sessionId",s);
            cookie.setPath("/");//如此可以 同一服务器共享cookie 即同一服务器根目录/下不同项目可共享
            cookie.setMaxAge(60*30);//表示寿命单位秒
            response.addCookie(cookie);
            return new Result(true, 1, "http://localhost/index.html");
        } else {
            return new Result(false, 0, "");
        }

    }

    private String sessionId(User user) {
        String sessionId = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(sessionId, user, 30, TimeUnit.MINUTES);
        return sessionId;
    }

}
