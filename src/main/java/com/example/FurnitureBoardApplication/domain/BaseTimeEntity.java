package com.example.FurnitureBoardApplication.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // JPA Entity 클래스들이 해당 클래스를 상속할 경우 해당 클래스의 필드들도 칼럼으로 인식한다.
public class BaseTimeEntity {

    @CreatedDate // Entity가 최초 저장될때 시간 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate // Entity가 수정될때 시간 자동 저장
    private LocalDateTime modifiedDate;
}
