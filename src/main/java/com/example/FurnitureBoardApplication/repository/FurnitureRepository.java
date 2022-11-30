package com.example.FurnitureBoardApplication.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class FurnitureRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    // 가구 등록
    
    // 가구 모두 List로 반환
    
    // id(pk) 기준 가구 찾기
    
    // 가구 종류와 일치한 가구 List로 반환
    
    // 검색한 단어가 포함된 가구 이름 List로 반환
    
    // 가구 정보 수정
    
    // 가구 제거
}
