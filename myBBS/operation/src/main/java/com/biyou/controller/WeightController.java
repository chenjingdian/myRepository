package com.biyou.controller;

import com.biyou.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operation")
@CrossOrigin
public class WeightController {
    @Autowired
    private ArticleService articleService;


    @GetMapping("/flushweight")
    public void flushWeight() {
        articleService.flushWeight();
    }
}
