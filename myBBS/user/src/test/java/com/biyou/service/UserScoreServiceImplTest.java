package com.biyou.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserScoreServiceImplTest {

    @Autowired
    private UserScoreService userScoreService;

    @Test
    public void increase() {
        userScoreService.increase(11,5);
    }
}