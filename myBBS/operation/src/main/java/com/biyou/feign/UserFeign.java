package com.biyou.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user",fallback = UserFeignFailBack.class)
public interface UserFeign {


    @GetMapping("/score/increase/{uid}/{inc}")
    public void increase(@PathVariable("uid") Long uid, @PathVariable("inc") Integer inc);

}
