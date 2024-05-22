package com.example.miniProject.service;

import com.example.miniProject.MiniProjectApplication;
import com.example.miniProject.domain.Member;
import com.example.miniProject.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;

    @Autowired MemberRepository memberRepository;

    //회원가입 테스트 코드 작성
    @Test
    public void memberJoinTest() throws Exception {
        Member member = new Member();
        member.setName("k");

        Long saveId = memberService.join(member);

        assertEquals(member, memberRepository.findOne(saveId));

    }

    //멤버 중복 테스트 코드 작성(이름 중복)
    @Test
    public void memberValidateTest() throws Exception {
        Member member1 = new Member();
        member1.setName("김");

        Member member2 = new Member();
        member2.setName("김");

        memberService.join(member1);
        try {
            memberService.join(member2); //이름이 중복되어서 여기서 에러가 발생해야함
        }
        catch (IllegalStateException e) { //만약 에러가 발생하면 catch에 걸려서 return 되면서 테스트 통과
            return;
        }

        fail("예외가 발생해야합니다."); //이미 예외처리를 해서 만약 예외처리가 먹히면 이 코드가 실행되기전에 밖으로 나가게되는데 안나가게되면 이 코드가 실행됨.
    }
}