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
        System.out.println(username+"关注的作者我就不查数据库表了,我直接 这个给一个list集合...代表names集合");
        List<String> names=new ArrayList<>();
        names.add("鲁迅");
        names.add("巴金");
        names.add("老舍");
        names.add("曹雪芹");
        names.add("罗贯中");
        names.add("施耐庵");
        names.add("吴承恩");
        List<Article> list = articleService.findByMyLove(names);
        return new Result(true,20,"OK",list);
    }

}
