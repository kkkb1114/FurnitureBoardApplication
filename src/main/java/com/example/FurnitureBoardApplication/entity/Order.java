package com.example.FurnitureBoardApplication.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "Orders")
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 회원

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Furniture> furnitureList = new ArrayList<>(); // 주문 가구 리스트

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus; // 배송 상태

    private String address; // 주소

    @CreatedDate // Entity가 최초 저장될때 시간 자동 저장
    private LocalDateTime createdDate; // 주문 시간

    private Double hidden; // 삭제 여부
}