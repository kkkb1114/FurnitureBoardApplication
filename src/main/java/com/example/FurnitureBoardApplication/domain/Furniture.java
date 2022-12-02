package com.example.FurnitureBoardApplication.domain;

import lombok.Getter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
@Getter
public class Furniture {
    @Id @GeneratedValue
    @Column(name = "furniture_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    private String name; // 가구 이름
    private String price; // 가구 가격
    private String count; // 가구 재고 수량
    private String kind; // 가구 종류
    private Double hidden; // 삭제 여부

    // Entity 클래스를 기본 생성자로 생성하지 못하게 한다.
    protected Furniture(){}

    public static Furniture createFurniture(Order order, String name, String price, String count, String kind, Double hidden){
        Furniture furniture = new Furniture();
        furniture.order = order;
        furniture.name = name;
        furniture.price = price;
        furniture.count = count;
        furniture.kind = kind;
        furniture.hidden = hidden;
        return furniture;
    }
}
