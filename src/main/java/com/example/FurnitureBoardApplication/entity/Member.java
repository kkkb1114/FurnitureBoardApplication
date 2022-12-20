package com.example.FurnitureBoardApplication.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id; // pk
    private String email; // 계정 메일
    private String password; // 비밀번호
    private String nickName; // 닉네임
    private String address; // 주소
    private String detailedAddress; // 상세 주소
    private Double hidden; // 회원 탈퇴 여부
    
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>(); // 댓글
    
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Board> boardList = new ArrayList<>(); // 게시글

    protected Member() {
    }

    //==생성 메서드==//
    public static Member createMember(String email, String password, String nickName, String address, String detailedAddress, Double hidden) {
        Member member = new Member();
        member.email = email;
        member.password = password;
        member.nickName = nickName;
        member.address = address;
        member.detailedAddress = detailedAddress;
        member.hidden = hidden;
        return member;
    }

    //==회원 비밀번호 변경==//
    public void passwordUpdate(String updatePassword) {
        this.password = updatePassword;
    }

    //==회원 닉네임 변경==//
    public void nickNameUpdate(String password) {
        this.password = password;
    }

    //==회원 탈퇴==//
    public void removeMember() {
        this.hidden = 1.0;
    }
}
