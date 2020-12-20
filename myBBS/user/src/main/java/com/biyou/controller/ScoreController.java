package com.biyou.controller;

import com.biyou.pojo.UserScore;
import com.biyou.service.UserScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/score")
@RestController
public class ScoreController {

    @Autowired
    private UserScoreService userScoreService;

    @GetMapping("/increase/{uid}/{inc}")
    public void increase(@PathVariable("uid") Integer uid, @PathVariable("inc") Integer inc) {

        userScoreService.increase(uid, inc);


    }
}
