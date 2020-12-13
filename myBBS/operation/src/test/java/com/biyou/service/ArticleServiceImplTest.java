package com.biyou.service;

import com.biyou.pojo.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceImplTest {
    @Autowired
    private ArticleService articleService;

    @Test
    public void findPageBySort() {
        List<Article> pageBySort = articleService.findPageBySort(1, 20);
        for (Article article : pageBySort) {
            System.out.println(article.getTitle()+"   "+article.getCreateDate());
        }
    }

    @Test
    public void findALlSort() throws ParseException {
        List<Article> allSort = articleService.findAllSort(1, 20);
        for (Article article : allSort) {
            System.out.println(article.getTitle()+"   "+article.getCreateDate()+"   "+article.getWeight());
        }
    }

    @Test
    public void addDoc(){
        Article article = new Article();
        article.setWeight(100);
        articleService.addDoc(article);
    }
}