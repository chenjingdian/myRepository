package com.biyou.service;

import com.biyou.dao.ArticleRepository;
import com.biyou.pojo.Article;
import com.biyou.utils_entry.IdWorker;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.CollectionCallback;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.lang.model.element.VariableElement;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private IdWorker idWorker;

    /**
     * 这个是 类似 通用mapper的代理接口
     */
    @Autowired
    private ArticleRepository articleRepo;

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 新增一条文档
     *
     * @param article
     */
    @Override
    public void addDoc(Article article) {
        //Mongo自增的_id 本身带有机器识别码,为了效率,我们这里还是使用雪花算法给他指定id
        long id = idWorker.nextId();
        article.setId(id);
        //设置存活状态 为 活着
        article.setState("alive");
        //设置创建时间为当前
        Date now = new Date();
        article.setCreateDate(now);

        //初始化给权重分
        String[] images = article.getImages();
        int i = 0;
        for (String image : images) {
            if (!StringUtils.isEmpty(image)) {
                i++;
            }
        }
        article.setWeight(50 + i);

        articleRepo.save(article);

    }

    /**
     * 分页查询
     * 根据创建时间 排序
     * currentPage 是从0开始数 1 2 3 4
     * totalPage 是 一次查多少条出来
     * _id 是雪花算法 自增的 有序不连续的 存数字,所以 创建时间 就 安装 _id排序
     *
     * @param currentPage
     * @param totalPage
     * @return
     */
    @Override
    public List<Article> findPageBySort(int currentPage, int totalPage) {
        Sort sort = Sort.by("_id").descending();//desc 降序
        PageRequest pageRequest = PageRequest.of(currentPage, totalPage, sort);
        Page<Article> all = articleRepo.findAll(pageRequest);
        return all.getContent();
    }

    /**
     * 根据id 删除 某条文档
     *
     * @param id
     */
    @Override
    public void deleteById(long id) {
        articleRepo.deleteById(id);
    }


    /**
     * 优雅方式...
     * 根据id 将某条文档 的 浏览量 自增 +1
     *
     * @param id
     */
    @Override
    public int increaseView(long id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("views", 1);
        mongoTemplate.updateFirst(query, update, "article");

        Optional<Article> byId = articleRepo.findById(id);

        Article article = byId.get();
        return article.getViews();
    }

    /**
     * 根据时间删除某些文档
     *
     * @param time
     */
    @Override
    public void deleteDocByTime(long time) {

        //首先 根据 时间查找 这文档 ,
        //这里利用 类似 jdbc 的 mongoDB driver 使用原生的 查询语句
        List<Article> list = articleRepo.findByIdIsBefore(time);

        for (Article article : list) {
            long id = article.getId();
            articleRepo.deleteById(id);
        }

    }

    /**
     * 根据id 查询某条文档
     *
     * @param id
     * @return
     */
    @Override
    public Article findById(long id) {
        Optional<Article> byId = articleRepo.findById(id);
        return byId.get();
    }

    /**
     * 刷新权重分
     */
    @Override
    public void flushWeight() {
        //1.0找出权重分 非0的 文章
        List<Article> list = articleRepo.findByWeightAfter(0);
        for (Article article : list) {
            //1.0计算时间
            Date createDate = article.getCreateDate();
            long createDateTime = createDate.getTime();
            long now = System.currentTimeMillis();
            long timeWeight = now - createDateTime;
            if (timeWeight >= 30 * 24 * 60 * 60 * 1000L) {
                article.setWeight(0);
                continue;
            } else {
                long i = 30 * 24 * 60 * 60*50L / timeWeight;
                int v = article.getViews();
                int c = article.getCommentList().size() * 10;

                article.setWeight((int) (i+v+c));
            }
        }
    }


}
