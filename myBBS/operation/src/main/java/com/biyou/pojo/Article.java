package com.biyou.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 这是一篇长文or心情
 */
public class Article implements Serializable {
    //id 使用雪花算法生成id
    private long id;

    //这篇是 长文 还是 短文
    private String extent;

    //标题
    private String title;
    //文本
    private String text;

    //图片 url 们
    private String[] images;

    //这篇长文所属的标签
    private String tag;

    //作者id
    private long userId;

    //作者name
    private String userName;

    //这篇文章 所属城市
    private String city;

    //创建时间
    private Date createDate;

    //这篇长文 的 存活状态
    private String state;

    //评论
    private List<Comment> commentList;

    //这篇长文 的浏览量 点赞量 =>这个数据是进程变动的,而且这个数据不用再浏览文章的时候展示
    private int views;

    //这篇长文的 转发量
    private int forwardNumber;


    public Article() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExtent() {
        return extent;
    }

    public void setExtent(String extent) {
        this.extent = extent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getForwardNumber() {
        return forwardNumber;
    }

    public void setForwardNumber(int forwardNumber) {
        this.forwardNumber = forwardNumber;
    }
}
