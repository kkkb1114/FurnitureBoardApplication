package com.example.FurnitureBoardApplication.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @MappedSuperclass: JPA Entity 클래스들이 해당 클래스를 상속할 경우 해당 클래스의 필드들도 칼럼으로 인식한다.
 * @EntityListeners: JPA Entity에 Persist, Remove, Update, Load에 대한 event 전과 후에 대한 콜백 메서드를 제공한다.
 */
@Getter
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class BaseTimeEntity {

    @CreatedDate // Entity가 최초 저장될때 시간 자동 저장
    private LocalDateTime createdDateTime;

    @LastModifiedDate // Entity가 수정될때 시간 자동 저장
    private LocalDateTime modifiedDateTime;
}
