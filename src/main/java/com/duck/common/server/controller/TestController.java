package com.duck.common.server.controller;

import com.duck.common.server.core.UnifyResponse;
import com.duck.common.server.core.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 5duck
 * @date 2021-02-13
 */
@RestController
@RequestMapping("")
public class TestController {
    @Autowired
    private RedisOperator redisOperator;

    @GetMapping("/redisGet")
    public UnifyResponse redis() {
        redisOperator.set("key1", "hello");
        return new UnifyResponse<>(0, "success", redisOperator.get("key1"));
    }

}
