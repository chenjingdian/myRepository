package com.biyou.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "operation")
@Component
public interface OperationFeign {

    @GetMapping("/operation/flushweight")
    public void flushWeight();
}
