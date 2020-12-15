package com.biyou.service;

import com.biyou.pojo.Article;
import com.biyou.utils_entry.FastDFSClient;
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


    /**
     * 删除fastDFS文件
     */
    @Test
    public void delF() throws Exception {
        FastDFSClient.deleteFile("groupname","M00/00/00/rBUACF_U3oCAX-7UAAA-KYeT7T8545.png");
    }


    /**
     * 刷新权重
     */
    @Test
    public void fush(){
        articleService.flushWeight();
    }
}