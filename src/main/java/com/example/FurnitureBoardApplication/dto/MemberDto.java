package com.example.FurnitureBoardApplication.dto;

import com.example.FurnitureBoardApplication.entity.Member;

public class MemberDto {
    private Long memberId; // pk
    private String email; // 계정 메일
    private String password; // 비밀번호
    private String nickName; // 닉네임
    private String address; // 주소
    private String detailedAddress; // 상세 주소
    private Double hidden; // 회원 탈퇴 여부

    public MemberDto(Member member){
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickName = member.getNickName();
        this.address = member.getAddress();
        this.detailedAddress = member.getDetailedAddress();
        this.hidden = member.getHidden();
    }
}
