package com.biyou.pojo;

import java.io.Serializable;

/**
 * 这是评价
 */
public class Comment implements Serializable {
    //id
    private long id;

    //这篇评价的 作者
    private long userId;

    //这篇评价的 作者名称
    private String userName;

    //这篇评价的 内容
    private String text;

}
