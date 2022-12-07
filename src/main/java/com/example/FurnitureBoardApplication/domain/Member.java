package com.example.FurnitureBoardApplication.domain;

import lombok.Getter;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id; // pk
    private String email; // 계정 메일
    private String password; // 비밀번호
    private String nickName; // 닉네임
    private String address; // 주소
    private String detailedAddress; // 상세 주소
    private Double hidden; // 회원 탈퇴 여부
    //private Address address; // 주소 (일단 이건 필요 없을 것 같아서 주석 처리)
    // 잠시 order 클래스는 완성 전이라 빼고 테스트
    /*@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)*/

    protected Member(){}

    //==생성 메서드==//
    public static Member createMember(String email, String password, String nickName, String address, String detailedAddress, Double hidden){
        Member member = new Member();
        member.email = email;
        member.password = password;
        member.nickName = nickName;
        member.address = address;
        member.detailedAddress = detailedAddress;
        member.hidden = hidden;
        return member;
    }

    //==회원 수정==//
    public void updateMember(String title, String writer, String content) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.address = address;
        this.detailedAddress = detailedAddress;
    }

    //==회원 탈퇴==//
    public void removeMember() {
        this.hidden = 1.0;
    }
}
