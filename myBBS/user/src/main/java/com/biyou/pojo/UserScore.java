package com.biyou.pojo;

import java.io.Serializable;

public class UserScore implements Serializable {

    private Integer id;

    private Integer score;

    public UserScore() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
