package com.example.FurnitureBoardApplication.service;

import com.example.FurnitureBoardApplication.entity.Member;
import com.example.FurnitureBoardApplication.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 가입
    public Long memberJoin(Member member) {
        duplicateNicknameValidation(member); // 닉네임 중복 확인
        duplicateEmailValidation(member); // 이메일 중복 확인
        memberRepository.save(member);
        return member.getMemberId();
    }

    // 회원 이름 중복 확인
    @Transactional(readOnly = true)
    public void duplicateNicknameValidation(Member member) {
        List<Member> memberList = memberRepository.findAllName(member.getNickName());
        if (memberList.size() > 0) {
            throw new IllegalStateException("이미 존재하는 닉네임 입니다.");
        }
    }

    // 회원 이메일 중복 확인
    @Transactional(readOnly = true)
    public void duplicateEmailValidation(Member member) {
        Member findMember = memberRepository.findOneEmail(member.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 이메일 입니다.");
        }
    }

    // 회원 전체 조회
    @Transactional(readOnly = true)
    public List<Member> memberFindAll() {
        return memberRepository.findAll();
    }

    // 회원 조건 조회(pk)
    @Transactional(readOnly = true)
    public Member findOneId(Long id) {
        return memberRepository.findOneId(id);
    }

    // 회원 조건 조회(닉네임)
    @Transactional(readOnly = true)
    public List<Member> memberFindName(String name) {
        return memberRepository.findAllName(name);
    }

    // 회원 조건 조회(이메일)
    @Transactional(readOnly = true)
    public Member memberFindOneEmail(String email) {
        return memberRepository.findOneEmail(email);
    }

    // 회원 조건 조회(이메일)
    @Transactional(readOnly = true)
    public Member memberLogin(String email, String password) {
        Member member = memberRepository.memberLogin(email, password);
        if (member != null){
            return member;
        }else {
            return null;
        }
    }

    // 회원 탈퇴
    @Transactional
    public void memberDelete(Long id) {
        Member member = memberRepository.findOneId(id);
        member.removeMember();
    }

    /**
     * 비밀번호 찾기
     **/
    // 회원 이메일 존재 하는지 확인
    @Transactional(readOnly = true)
    public boolean checkEmail(String email) {
        Member findMember = memberRepository.findOneEmail(email);
        if (findMember != null) {
            return true;
        } else {
            return false;
        }
    }

    // 임시 비밀번호 발급
    public String getTmpPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        StringBuilder password = new StringBuilder();

        // 문자 배열 깅이의 값을 랜덤으로 10개를 뽑아 조합
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            password.append(charSet[idx]);
        }

        System.out.println("임시 비밀번호 생성: " + password);

        return password.toString();
    }

    // 비밀번호 변경
    public void passwordUpdate(Member member, String updatePassword) {
        member.passwordUpdate(updatePassword);
    }
}
