package com.example.miniProject.service;

import com.example.miniProject.domain.Member;
import com.example.miniProject.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //트렌젝셔널 옵션에서 readonly = true라는 옵션을 주면 jpa에서 조회하는곳의 성능을 최적화 시킬수있는데 반드시 조회할때만 옵션을 적용해줘야함
//@AllArgsConstructor
@RequiredArgsConstructor //이건 final을 사용한것만 생성자를 만들어줌
public class MemberService {

    //서비스에서는 레포지토리를 사용해야하기에 호출해줌
    private final MemberRepository memberRepository;

//    public MemberService(MemberRepository memberRepository) {  //멤버 레포지토리를 사용하기 위해 생성자 만들어주기 하지만 이걸 @AllArgsConstructor로 생략할수있음
//        this.memberRepository = memberRepository;
//    }

    //회원 가입 서비스(with 중복 회원 검열)
    @Transactional // 이 서비스클래스에서는 기본적으로 조회가 등록보다 많기에 위에 readonly = true 옵션을 걸어주었고, 등록시에는 따로 트렌젝셔널 어노테이션을 써주면됨.
    public Long join(Member member) {
        validateDuplicateMember(member); //이름을 통한 회원 중복 검증
        memberRepository.save(member);  //회원 가입은 먼저 레포지토리에 구현해줬던 save 함수안에 멤버 객체를 넘겨주면  하지만 반드시 중복 검열 로직 추가해야함
        return member.getId();
    }

    //중복 회원 검증 로직 함수
    public void validateDuplicateMember(Member member) { //문제가 있으면 이 안에서 문제를 터뜨릴것임.
        List<Member> memberList = memberRepository.findByName(member.getName()); //우선 가입하려는 회원과 같은 이름인 회원이 있는지 검사
        if(!memberList.isEmpty()) {  //만약 멤버 리스트가 공백이 아니면 문제가있는것
            throw new IllegalStateException("이미 존재하는 회원입니다"); //문제가 있으면 에러 터뜨려주기
        }

    }

    //회원 전체 조회 서비스
    public List<Member> findMembers() {
        return memberRepository.findAll(); //회원 전체 조회 기능도 마찬가지로 먼저 레포지토리에 개발해놨던 findAll함수를 가져와서 리턴
    }

    //회원을 id를 통하여 한건만 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId); //회원 단일 조회도 레포에있던 함수레 id 넣어줘서 리턴
    }





}
