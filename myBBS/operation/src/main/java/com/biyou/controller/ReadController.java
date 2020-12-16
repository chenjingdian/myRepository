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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/read")
@CrossOrigin
public class ReadController {

    @Autowired
    private ArticleService articleService;

    /**
     * 根据发布时间排序
     * @return
     * @throws ParseException
     */
    @GetMapping("/findByCreateTime")
    public Result findByCreateTime() throws ParseException {
        List<Article> allSort = articleService.findAllSort(1, 20);
        return new Result(true,20,"OK",allSort);
    }

    /**
     * 根据 热门程度 权重分 排序
     */
    @GetMapping("/findByWeight")
    public Result findByWeight() throws ParseException {
        List<Article> list = articleService.findByWeight(1, 20);
        return new Result(true,20,"OK",list);
    }

    @GetMapping("/findByMyLove")
    public Result findByMyLove(String username) throws ParseException {
        System.out.println(username+"关注的作者我就不查数据库表了,我直接 这个给一个list集合...代表id集合");
        List<Integer> ids=new ArrayList<>();
        ids.add(11);
        ids.add(12);
        ids.add(13);
        ids.add(14);
        List<Article> list = articleService.findByMyLove(ids);
        return new Result(true,20,"OK",list);
    }

}
