/**
 * @author yangstar
 * @date 2023/5/29 12:14
 */

package com.yangstar.train.member.service;

import com.yangstar.train.member.mapper.MemberMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class MemberService {
    @Resource
    private MemberMapper memberMapper;
    public int count() {
         return Math.toIntExact(memberMapper.countByExample(null));
    }
}
