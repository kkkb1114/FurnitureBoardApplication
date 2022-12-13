package com.example.FurnitureBoardApplication.service;

import com.example.FurnitureBoardApplication.dto.Member;
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
        duplicateNicknameValidation(member); // 닉네임 중복 확인
        duplicateEmailValidation(member); // 이메일 중복 확인
        memberRepository.save(member);
        return member.getId();
    }
    // 회원 이름 중복 확인
    @Transactional(readOnly = true)
    public void duplicateNicknameValidation(Member member){
        List<Member> memberList = memberRepository.findAllName(member.getNickName());
        if (memberList.size() > 0){
            throw new IllegalStateException("이미 존재하는 닉네임 입니다.");
        }
    }
    // 회원 이메일 중복 확인
    @Transactional(readOnly = true)
    public void duplicateEmailValidation(Member member){
        Member findMember = memberRepository.findOneEmail(member.getEmail());
        if (findMember != null){
            throw new IllegalStateException("이미 가입된 이메일 입니다.");
        }
    }

    // 회원 전체 조회
    @Transactional(readOnly = true)
    public List<Member> memberFindAll(){
        return memberRepository.findAll();
    }
    // 회원 조건 조회(pk)
    @Transactional(readOnly = true)
    public Member findOneId(Long id){
        return memberRepository.findOneId(id);
    }
    // 회원 조건 조회(닉네임)
    @Transactional(readOnly = true)
    public List<Member> memberFindName(String name){
        return memberRepository.findAllName(name);
    }
    // 회원 조건 조회(이메일)
    @Transactional(readOnly = true)
    public Member memberFindOneEmail(String email){
        return memberRepository.findOneEmail(email);
    }
    // 회원 조건 조회(이메일)
    @Transactional(readOnly = true)
    public Member memberLogin(String email, String password){
        if (memberRepository.memberLogin(email, password) != null){
            return memberRepository.memberLogin(email, password);
        }else {
            return null;
        }
    }

    // 회원 탈퇴
    @Transactional
    public void memberDelete(Long id){
        Member member = memberRepository.findOneId(id);
        member.removeMember();
    }
}
