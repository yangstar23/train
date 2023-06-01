///**
// * @author yangstar
// * @date 2023/6/1 14:27
// */
//
//package com.yangstar.train.member.req;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Pattern;
//
//public class MemberSendCodeReq {
//    @NotBlank(message = "手机号不能为空")
//
//    //正则表达式
//    @Pattern(regexp = "^1[3|4|5|7|8][0-9]\\d{8}$",message = "手机号格式不正确")
//
//
//    private String mobile;
//
//    public String getMobile() {
//        return mobile;
//    }
//
//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }
//
//    @Override
//    public String toString() {
//        return "MemberSendCodeReq{" +
//                "mobile='" + mobile + '\'' +
//                '}';
//    }
//
//
//}
