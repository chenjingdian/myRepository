package com.biyou.controller;


import com.biyou.pojo.Article;
import com.biyou.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operation")
@CrossOrigin
public class OperationController {
    @Autowired
    private ArticleService articleService;

    /**
     * 分页排序查询
     */
    @GetMapping("/findPageBySort/{currentPage}/{totalPage}")
    List<Article> findPageBySort(@PathVariable("currentPage") int currentPage, @PathVariable("totalPage") int totalPage) {
        return articleService.findPageBySort(currentPage, totalPage);
    }

    /**
     * 根据id 删除
     *
     * @param id
     */
    @DeleteMapping("/deleteById/{id}")
    void deleteById(@PathVariable("id") long id) {
        articleService.deleteById(id);
    }


    @GetMapping("/findById/{id}")
    Article findById(@PathVariable("id") long id) {
        return articleService.findById(id);
    }

    @PutMapping("/view/{id}")
    int putView(@PathVariable("id") long id) {
        return articleService.increaseView(id);
    }
}
