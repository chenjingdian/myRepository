package com.biyou.service;

import com.biyou.dao.ArticleRepository;
import com.biyou.feign.UserFeign;
import com.biyou.pojo.Article;
import com.biyou.utils_entry.IdWorker;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.CollectionCallback;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.lang.model.element.VariableElement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserFeign userFeign;


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
        if (images != null && images.length > 0) {
            for (String image : images) {
                if (!StringUtils.isEmpty(image)) {
                    i++;
                }
            }
        }

        article.setWeight(5000 + i);

        articleRepo.save(article);

        String userName = article.getUserName();
        //前面的坑,根据用户名 我从数据库 查 userid
        //这里不查了,userid 都算在11号头上

        //给用户加 积分
        userFeign.increase(11L,1);
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
//        Sort sort = Sort.by("_id").descending();//desc 降序
//        Sort sort = Sort.by("_id").ascending();//asc 升序
        Sort sort = Sort.by("createDate").descending();

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
//        Optional<Article> byId = articleRepo.findById(id);
        Article byId1 = mongoTemplate.findById(id, Article.class);

        return byId1;
    }

    /**
     * 刷新权重分
     */
    @Override
    public void flushWeight() {
        //1.0找出权重分 非0的 文章
        //这里用 LinkedList 效率更高,可以做对列
        List<Article> list = articleRepo.findByWeightAfter(0);



        //这里开启线程池

        ThreadPoolExecutor pool = new ThreadPoolExecutor(2,5,2, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        for (Article article : list) {
//            pool.submit(new MyRunnable());
            pool.submit(() -> {
                article.setWeight(article.getWeight()-1);//这一步是我重写权重分
                //重写到 mongoDB 中去
                //重写mongo中的数据
                Query query = new Query();
                query.addCriteria(Criteria.where("_id").is(article.getId()));
                Update update = new Update();
                update.set("weight", article.getWeight());
                mongoTemplate.updateFirst(query, update, "article");
            });
        }

        // 朝生夕死  放在内存溢出 所以这里关闭线程池
        pool.shutdown();





//        for (Article article : list) {
//            //1.0计算时间
//            Date createDate = article.getCreateDate();
//            long createDateTime = createDate.getTime();
//            long now = System.currentTimeMillis();
//            long timeWeight = now - createDateTime;
//            if (timeWeight >= 30 * 24 * 60 * 60 * 1000L) {
//                article.setWeight(0);
//                continue;
//            } else {
//                long i = -1;
//                int v = article.getViews();
//                int c = 0;
//                if (article.getCommentList() != null) {
//                    c = article.getCommentList().size() * 10;
//                }
//                article.setWeight((int) (i + v + c) + article.getWeight());
//            }
//            //重写mongo中的数据
//            Query query = new Query();
//            query.addCriteria(Criteria.where("_id").is(article.getId()));
//            Update update = new Update();
//            update.set("weight", article.getWeight());
//            mongoTemplate.updateFirst(query, update, "article");
//        }
    }

    /**
     * 分页查询 根据创建时间排序
     * 这个才是真正的 先排序  再 分页
     *
     * @param currentPage
     * @param totalPage
     * @return
     */
    @Override
    public List<Article> findAllSort(int currentPage, int totalPage) throws ParseException {

        Query query = new Query();
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createDate")));
        query.skip(0);//跳过前多少条
        query.limit(5);//每次给多少条

        //准备条件
//        Criteria criteria1 = Criteria.where("status").is("ONLINE");
//        Criteria criteria2 = Criteria.where("update_ts").lte(System.currentTimeMillis());
//        query.addCriteria(criteria1);
//        query.addCriteria(criteria2);
        List<Article> list = mongoTemplate.find(query, Article.class);

        return list;
    }


    /**
     * 根据权重分排序并分页
     *
     * @param currentPage
     * @param totalPage
     * @return
     */
    @Override
    public List<Article> findByWeight(int currentPage, int totalPage) {
        Query query = new Query();
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "weight")));
        //多条件查询 要求 权重分>=0
        Criteria criteria1 = Criteria.where("weight").gt(0);
        query.addCriteria(criteria1);

        query.skip(0);//跳过前多少条
        query.limit(5);//每次给多少条

        List<Article> list = mongoTemplate.find(query, Article.class);
        return list;
    }

    /**
     * 根据关注的 作者名录 以及权重分,查询 文章列表
     *
     * @param names
     * @return
     */
    @Override
    public List<Article> findByMyLove(List<String> names) {

//        QueryBuilder queryBuilder = new QueryBuilder();
//
//        BasicDBObject fieldsObject=new BasicDBObject();//这个是设置 返回字段
//        fieldsObject.put("title", 1);                  //1代表要返回,0代表不要返回
//        fieldsObject.put("userName", 1);                //_id 默认是1要返回,除非设置为0

        //设置查询条件
        Query query = new BasicQuery("{}","{title:1,userName:1}");//返回字段是标题与 作者名 查询条件是空,后面再加
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "weight")));

        //这个条件是 作者id  in 在 这个 collection 里面 这个条件,其实用 作者id查 是比较 高效的,但是前台页面没存 id  存的是作者名,
        Criteria criteria1 = Criteria.where("userName").in(names);

        query.addCriteria(criteria1);

        query.skip(0);//跳过前多少条
        query.limit(50);//每次给多少条

        List<Article> list = mongoTemplate.find(query, Article.class);
        return list;
    }


}
