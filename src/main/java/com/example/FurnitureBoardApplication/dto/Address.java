package com.example.FurnitureBoardApplication.dto;

import lombok.Getter;

import javax.persistence.*;
/**
 * @Embeddable: JPA의 내장 타입이라는 뜻
 * 해당 타입은 데이터가 변경되면 안되기에 setter를 제공하지 않으며 무조건 생성자를 생성할때만 값을 넣도록 만든다.
 * (다른 값을 가진 Address가 필요하면 새로 만들어야한다는 뜻이다.)
 */
@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    //주소 (일단 이건 필요 없을 것 같아서 주석 처리)
    public Address(String city, String street, String zipcode){
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
