package com.example.FurnitureBoardApplication.service;

import com.example.FurnitureBoardApplication.dto.Member;
import com.example.FurnitureBoardApplication.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private EntityManager entityManager;

    MemberServiceTest() {
    }

    @Test
    void memberJoin() {
        // given
        Member member1 = Member.createMember("kikkb1114@naver.com", "rlarlqja5!", "kkkb1114", "주소", "상세 주소", 0.0);

        // when
        Long id = memberService.memberJoin(member1);

        //then
        Assertions.assertThat(memberRepository.findOneId(id).getNickName()).isEqualTo(member1.getNickName());
    }

    @Test
    void validateDuplicateMember() {
        // given
        Member member1 = Member.createMember("kikkb1114@naver.com", "rlarlqja5!", "kkkb1114", "주소", "상세 주소", 0.0);
        Member member2 = Member.createMember("aaa@naver.com", "aaa1", "kkkb1114", "주소","상세 주소", 0.0);

        // when
        memberService.memberJoin(member1);
        try {
            memberService.memberJoin(member2);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        //then
        Assertions.fail("중복 확인 기능 테스트이기에 에러가 나야해서 해당 경고 표시가 뜨면 안된다.");
    }

    @Test
    void memberFindAll() {
        // given
        Member member1 = Member.createMember("kikkb1114@naver.com", "rlarlqja5!", "kkkb1114", "주소", "상세 주소", 0.0);
        Member member2 = Member.createMember("aaa@naver.com", "aaa1", "aaa12", "주소", "상세 주소", 0.0);
        memberService.memberJoin(member1);
        memberService.memberJoin(member2);

        // when
        List<Member> memberList = memberService.memberFindAll();

        //then
        for (Member member : memberList) {
            System.out.println("Test_memberFindAll: " + member.getId());
            System.out.println("Test_memberFindAll: " + member.getEmail());
            System.out.println("Test_memberFindAll: " + member.getPassword());
            System.out.println("Test_memberFindAll: " + member.getNickName());
            System.out.println("Test_memberFindAll: " + member.getAddress());
        }
    }

    @Test
    void memberFindOne() {
        // given
        Member member1 = Member.createMember("kikkb1114@naver.com", "rlarlqja5!", "kkkb1114", "주소", "상세 주소", 0.0);
        Member member2 = Member.createMember("aaa@naver.com", "aaa1", "aaa12", "주소", "상세 주소", 0.0);
        memberService.memberJoin(member1);
        memberService.memberJoin(member2);

        // when
        List<Member> memberList = memberService.memberFindName("kkkb1114");

        //then
        for (Member member : memberList) {
            System.out.println("Test_memberFindOne: " + member.getId());
            System.out.println("Test_memberFindOne: " + member.getEmail());
            System.out.println("Test_memberFindOne: " + member.getPassword());
            System.out.println("Test_memberFindOne: " + member.getNickName());
            System.out.println("Test_memberFindOne: " + member.getAddress());
        }
    }
}