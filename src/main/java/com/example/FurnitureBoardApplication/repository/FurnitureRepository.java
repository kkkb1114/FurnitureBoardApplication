package com.example.FurnitureBoardApplication.repository;

import com.example.FurnitureBoardApplication.entity.Furniture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FurnitureRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    // 가구 등록 / 수정 / 제거 (데이터를 지우는건 실무에서는 있을수 없는 일이기에 값이 변경된 데이터로 업데이트한다.)
    public Long save_update(Furniture furniture){
        if (furniture.getId() == null){
            entityManager.persist(furniture);
        }else {
            entityManager.merge(furniture);
        }
        return furniture.getId();
    }
    
    // 가구 모두 List로 반환
    public List<Furniture> findAll(){
        return entityManager.createQuery("select f from Furniture f", Furniture.class)
                .getResultList();
    }
    
    // id(pk) 기준 게시글 찾기
    public Furniture findId(Long id){
        return entityManager.find(Furniture.class, id);
    }
    
    // 가구 종류와 일치한 게시글 List로 반환
    public List<Furniture> findAllKind(String kind){
        return entityManager.createQuery("select f from Furniture f where f.kind = :kind", Furniture.class)
                .setParameter("kind", kind)
                .getResultList();
    }
    
    // 검색한 단어가 이름에 포함된 가구 List로 반환
    public List<Furniture> findAllName(String name){
        return entityManager.createQuery("select f from Furniture f where f.name like :name", Furniture.class)
                .setParameter("name", name)
                .getResultList();
    }
}
