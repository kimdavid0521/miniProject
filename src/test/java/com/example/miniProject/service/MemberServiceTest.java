package com.example.miniProject.service;

import com.example.miniProject.domain.Member;
import com.example.miniProject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    public void memberJoinTest() throws Exception {
        Member member = new Member();
        member.setName("김태영");

        Long saveId = memberService.join(member);
        assertEquals(member, memberRepository.findOne(saveId));

    }

    public void validateMemberTest() throws Exception {

    }
}