package com.biyou.controller;

import com.biyou.pojo.User;
import com.biyou.pojo.UserScore;
import com.biyou.service.UserScoreService;
import com.biyou.utils_entry.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RequestMapping("/score")
@RestController
public class ScoreController {

    @Autowired
    private UserScoreService userScoreService;


    @GetMapping("/increase/{uid}/{inc}")
    public void increase(@PathVariable("uid") Long uid, @PathVariable("inc") Integer inc) {
        userScoreService.increase(uid, inc);
    }
}
