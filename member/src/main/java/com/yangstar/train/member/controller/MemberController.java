package com.yangstar.train.member.controller;

import com.yangstar.train.common.resp.CommonResp;
import com.yangstar.train.member.req.MemberSendCodeReq;
import com.yangstar.train.member.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/member")
public class MemberController {

    //
    @Resource
    private MemberService memberService;

    @GetMapping("/count")
    public CommonResp<Integer> count() {
        int count = memberService.count();
        CommonResp<Integer> commonResp = new CommonResp<>();
        commonResp.setContent(count);
        return commonResp;
    }


    @PostMapping("/register")
    //long是返回结果
    //String mobile是传入参数
    public CommonResp<Long> register(MemberSendCodeReq req) {
        long register = memberService.register(req);
        return new CommonResp<>(register);
    }

}


