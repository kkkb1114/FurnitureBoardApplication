package com.example.FurnitureBoardApplication.repository;

import com.example.FurnitureBoardApplication.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void save() {
        //given
        Member member = Member.createMember("이메일", "비번", "닉네임", "주소", "상세 주소", 0.0);

        //when
        Long id = memberRepository.save(member);

        //then
        Assertions.assertThat(memberRepository.findOneId(id).getNickName()).isEqualTo(member.getNickName());
    }

    @Test
    void findOneId() {
        //given
        Member member = Member.createMember("이메일", "비번", "닉네임", "주소", "상세 주소", 0.0);

        //when
        Long id = memberRepository.save(member);
        Member findMember = memberRepository.findOneId(id);

        //then
        Assertions.assertThat(findMember.getNickName()).isEqualTo(member.getNickName());
    }

    @Test
    void findAll() {
        //given
        Member member1 = Member.createMember("이메일", "비번", "닉네임", "주소", "상세 주소", 0.0);
        Member member2 = Member.createMember("이메일", "비번", "닉네임", "주소", "상세 주소", 0.0);
        Member member3 = Member.createMember("이메일", "비번", "닉네임", "주소", "상세 주소", 0.0);

        //when
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        //then
        Assertions.assertThat(memberRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    void findAllName() {
        //given
        Member member1 = Member.createMember("이메일", "비번", "닉네임1", "주소", "상세 주소", 0.0);
        Member member2 = Member.createMember("이메일", "비번", "닉네임1", "주소", "상세 주소", 0.0);
        Member member3 = Member.createMember("이메일", "비번", "닉네임2", "주소", "상세 주소", 0.0);

        //when
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        //then
        Assertions.assertThat(memberRepository.findAllName("닉네임1").size()).isEqualTo(2);
    }
}