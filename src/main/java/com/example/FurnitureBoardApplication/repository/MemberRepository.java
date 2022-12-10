package com.example.FurnitureBoardApplication.repository;

import com.example.FurnitureBoardApplication.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 1. @Repository: 해당 클래스를 스프링이 Repository 클래스로 인식 및 스프링 빈에 등록시킨다.
 * 2. @PersistenceContext: 해당 어노테이션이 붙은 EntityManager 관련 변수는 스프링 부트에서 영속성 컨텍스트에서 자동으로 객체 생성해서 내려준다.
 * 3. 여기에 사용되는 SQL문은 전부 스프링 영속성 컨텍스트에 저장되어있다가 빌드 및 사용되면 실행된다.
 */
@Repository
@RequiredArgsConstructor
public class MemberRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    // 회원 정보 저장
    // 들어온 member 객체의 id값이 null이면 회원가입, null이 아니면 회원 정보 수정
    public Long save(Member member){
        if (member.getId() == null){
            entityManager.persist(member);
        }else {
            entityManager.merge(member);
        }
        return member.getId();
    }

    // id(pk) 기준으로 회원 찾기
    public Member findOneId(Long id){
        return entityManager.find(Member.class, id);
    }

    // 회원 정보 List로 전부 반환
    public List<Member> findAll(){
        return entityManager.createQuery("select m from Member m where m.hidden = 0", Member.class)
                .getResultList();
    }

    /** findAll을 제외한 find에 where m.hidden = 0을 넣지 않은 이유는 지금은 존재하는지 확인 용도로만 사용하며 복구 될수도 있기 때문에 아예 중복 될수도 있는 상황을 배제 시켰다. */
    // 입력 받은 name과 동일한 회원 전부 List로 반환
    public List<Member> findAllName(String name){
        return entityManager.createQuery("select m from Member m where m.nickName = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    // 입력 받은 email과 동일한 회원 전부 List로 반환
    public Member findOneEmail(String email){
        Member member = null;
        try {
            member = entityManager.createQuery("select m from Member m where m.email = :email", Member.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return member;
    }

    // 입력 받은 email과 동일한 회원 전부 List로 반환
    public Member memberLogin(String email, String password){
        Member member = null;
        try {
            member = entityManager.createQuery("select m from Member m " +
                    "where m.email = :email and m.password = :password and m.hidden = 0", Member.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return member;
    }
}
