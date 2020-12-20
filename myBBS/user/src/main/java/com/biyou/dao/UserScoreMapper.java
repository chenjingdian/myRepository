package com.biyou.dao;

import com.biyou.pojo.UserScore;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

@Repository
public interface UserScoreMapper extends Mapper<UserScore> {

    //增加某 用户的 积分+多少
    @Update("update t_userscore set score=score+ #{inc} where id = #{uid}")
    void increase(@Param("uid") Long uid,@Param("inc") Integer inc);
}
