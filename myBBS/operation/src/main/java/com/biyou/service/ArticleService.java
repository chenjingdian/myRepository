package com.biyou.service;


import com.biyou.pojo.Article;

import java.util.List;

public interface ArticleService {
    /**
     * 1.0上传文档
     */
    void addDoc(Article article);

    /**
     * 2.0 分页查询
     * 根据文档创建时间排序
     */
    List<Article> findPageBySort(int currentPage, int totalPage);

    /**
     * 3.0 根据id 删除某条文档
     * @param id
     */
    void deleteById(long id);

    /**
     * 4.0
     * 将某条文档 的浏览量 自增+1
     * @param id
     * 返回现在的 浏览量是 多少
     */
    int increaseView(long id);

    /**
     * 5.0 根据日期删除 某些文档
     */
    void deleteDocByTime(long time);

    /**
     * 6.0 根据id 查询 某条文档
     * @param id
     * @return
     */
    Article findById(long id);

    /**
     * 7.0 刷新权重分
     */
    void flushWeight();
}
