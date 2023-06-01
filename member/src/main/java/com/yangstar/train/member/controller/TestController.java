package com.yangstar.train.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello() {
        return "hello yangstar!!!";
    }

    //新增一个接口,测试一下
    @GetMapping("/hello2")
    public String hello2() {
        return "hello yangstar2!!!";
    }


}


