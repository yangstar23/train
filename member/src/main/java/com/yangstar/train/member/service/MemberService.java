/**
 * @author yangstar
 * @date 2023/5/29 12:14
 */

package com.yangstar.train.member.service;

import com.yangstar.train.member.mapper.MemberMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yangstar.train.member.domain.Member;


@Service
public class MemberService {
    @Resource
    private MemberMapper memberMapper;
    public int count() {
         return Math.toIntExact(memberMapper.countByExample(null));
    }

    public long register(String mobile){
        Member member = new Member();
        //系统时间
        member.setId(System.currentTimeMillis());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }
}
