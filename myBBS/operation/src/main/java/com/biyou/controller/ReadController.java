package com.biyou.controller;

import com.biyou.pojo.Article;
import com.biyou.service.ArticleService;
import com.biyou.utils_entry.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/read")
@CrossOrigin
public class ReadController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/findByCreateTime")
    public Result findByCreateTime() throws ParseException {
//        List<Article> list = articleService.findPageBySort(1, 20);
        List<Article> allSort = articleService.findAllSort(1, 20);
        return new Result(true,20,"OK",allSort);
    }
}
