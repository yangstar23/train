/**
 * @author yangstar
 * @date 2023/6/1 14:27
 */

package com.yangstar.train.member.req;


public class MemberSendCodeReq {


    private String mobile;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString(){
        return "MemberSendCodeReq{" +
                "mobile='" + mobile + '\'' +
                '}';
    }
}
