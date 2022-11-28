package com.example.FurnitureBoardApplication.domain;

import lombok.Getter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String password;
    private String nickName;
    private String address;
    // 잠시 order 클래스는 완성 전이라 빼고 테스트
    /*@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Order> orderList = new ArrayList<>();

    public void addOrderList(Order order){
        orderList.add(order);
    }*/

    //==생성 메서드==//
    /*public static Member createMember(String email, String password, String nickName, String address, Order... orders){
        Member member = new Member();
        member.email = email;
        member.password = password;
        member.nickName = nickName;
        member.address = address;
        for (Order order : orders){
            member.addOrderList(order);
        }
        return member;
    }*/
    // 잠시 order 클래스는 완성 전이라 빼고 테스트
    public static Member createMember(String email, String password, String nickName, String address){
        Member member = new Member();
        member.email = email;
        member.password = password;
        member.nickName = nickName;
        member.address = address;
        return member;
    }
}
