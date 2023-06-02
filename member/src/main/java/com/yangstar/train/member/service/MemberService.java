/**
 * @author yangstar
 * @date 2023/5/29 12:14
 * @Description:Service层,被Controller层调用,调用Mapper层
 */

package com.yangstar.train.member.service;

import cn.hutool.core.collection.CollUtil;
import com.yangstar.train.member.domain.Member;
import com.yangstar.train.member.domain.MemberExample;
import com.yangstar.train.member.mapper.MemberMapper;
import com.yangstar.train.member.req.MemberSendCodeReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MemberService {
    @Resource
    private MemberMapper memberMapper;
    public int count() {
         return Math.toIntExact(memberMapper.countByExample(null));
    }

    public long register(MemberSendCodeReq req){
        String mobile = req.getMobile();
        //判断手机号是否已经注册
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);

        //查一个列表是否为空，如果为空，就抛出一个运行时异常，提示“手机号已经存在”。
      /*  isEmpty、isNotEmpty方法
        判断集合是否为空（包括null和没有元素的集合*/
      /*  集合工具 CollUtil
        这个工具主要增加了对数组、集合类的操作。*/
        if(CollUtil.isNotEmpty(list))
        {
            //手机号存在，抛出异常
            throw new RuntimeException("手机号已经存在");

        }
        Member member = new Member();
        //新增的时候，id是自增的，这里是是用此时的时间戳作为id
        member.setId(System.currentTimeMillis());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }
}
