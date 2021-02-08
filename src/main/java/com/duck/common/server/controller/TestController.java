package com.duck.common.server.controller;

import com.duck.common.server.core.interceptors.LoginRequire;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 5duck
 * @date 2021-02-08
 */
@RequestMapping("/test")
@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "111";
    }

    @GetMapping("/test2")
    @LoginRequire
    public String test2() {
        return "2222";
    }
}
