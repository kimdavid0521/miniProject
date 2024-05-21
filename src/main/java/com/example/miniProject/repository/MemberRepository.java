package com.example.miniProject.repository;

import com.example.miniProject.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //어쨋든 레포지토리에서 JPA를 접근하며 관리해야하기때문에 JPA에서 표준어 어노테이션인 persistence context 써주기
//    @PersistenceContext   //@RequiredArgsConstructor로 생성자 생성 생략...
    private final EntityManager em;  //이렇게 해주면 em에 스프링이 엔티티매니저를 주입해줌

    //멤버 저장 로직
    public void save(Member member) {
        em.persist(member); //이렇게 주입시켜주면 JPA에 저장되는 로직
    }

    //멤버 id로 멤버 조회 로직
    public Member findOne(Long id) {
        return em.find(Member.class, id); //이렇게 해주면 JPA에서 member을 찾고 그에 맞는 id 값인 멤버를 찾아서 반환해줌
    }

    //모든 멤버를 리스트 조회
    public List<Member> findAll() {  //전체를 조회하려면 em.createQuery를 통하여 jpql을 작성해줘야함
        List<Member> memberList = em.createQuery("select m from Member m", Member.class)  //첫번째가 jpql을 사용하고 두번째가 반환타입 넣으면됨.
                .getResultList();
        return memberList;
        //이 3줄을 한줄로 합칠수있는데 이해상 3줄로 냅두겠음.
    }

    //이름으로 회원 조회
    public List<Member> findByName(String name) {
        List<Member> memberList = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return memberList;
    }



}
