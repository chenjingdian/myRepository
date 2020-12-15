package com.biyou.dao;

import com.biyou.pojo.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArticleRepository extends MongoRepository<Article, Long> {

    public List<Article> findByIdIsBefore(long time);

    List<Article>findByWeightAfter(int score);


}
