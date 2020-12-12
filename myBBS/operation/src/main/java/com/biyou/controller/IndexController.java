package com.biyou.controller;


import com.biyou.pojo.User;
import com.biyou.utils_entry.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RequestMapping("/user")
@RestController
public class IndexController {
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/welcome")
    public Result index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("sessionId".equalsIgnoreCase(cookie.getName())) {
                User usr = (User) redisTemplate.opsForValue().get(cookie.getValue());
                if (usr == null) {
                    continue;
                }
                return new Result(true, 1, usr.getUsername());
            }
        }
        return null;
    }
}
