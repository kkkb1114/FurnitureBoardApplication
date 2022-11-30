package com.example.FurnitureBoardApplication.domain;

import lombok.Getter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
@Getter
public class Furniture {
    @Id @GeneratedValue
    @Column(name = "orderFurniture_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    private String name; // 가구 이름
    private String price; // 가구 가격
    private String count; // 가구 재고 수량
    private String kind; // 가구 종류
}
