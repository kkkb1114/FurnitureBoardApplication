package com.example.FurnitureBoardApplication.service;

import com.example.FurnitureBoardApplication.domain.Member;
import com.example.FurnitureBoardApplication.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 가입
    public Long memberJoin(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
    // 회원 이름 중복 확인
    @Transactional(readOnly = true)
    public void validateDuplicateMember(Member member){
        List<Member> memberList = memberRepository.findAllName(member.getNickName());
        if (memberList.size() > 0){
            throw new IllegalStateException("이미 존재하는 닉네임 입니다.");
        }
    }
    // 회원 전체 조회
    @Transactional(readOnly = true)
    public List<Member> memberFindAll(){
        return memberRepository.findAll();
    }
    // 회원 조건 조회
    @Transactional(readOnly = true)
    public List<Member> memberFindOne(String name){
        return memberRepository.findAllName(name);
    }
}
