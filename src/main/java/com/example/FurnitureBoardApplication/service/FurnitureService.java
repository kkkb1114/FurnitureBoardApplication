package com.example.FurnitureBoardApplication.service;

import com.example.FurnitureBoardApplication.dto.Furniture;
import com.example.FurnitureBoardApplication.repository.FurnitureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FurnitureService {
    private final FurnitureRepository furnitureRepository;

    // 가구 등록
    public Long save_update(Furniture furniture){
        furnitureRepository.save_update(furniture);
        return furniture.getId();
    }

    // 가구 모두 List로 반환
    public List<Furniture> findAll(){
        return furnitureRepository.findAll();
    }

    // 가구 종류와 일치한 가구 List로 반환
    public List<Furniture> findAllKind(String kind){
        return furnitureRepository.findAllKind(kind);
    }

    // 검색한 단어가 이름에 포함된 가구 List로 반환
    public List<Furniture> findAllName(String name){
        return furnitureRepository.findAllName(name);
    }

    // 가구 제거 (데이터를 지우는건 실무에서는 있을수 없는 일이기에 값이 변경된 데이터로 업데이트한다.)
    public void remove(Furniture furniture){
        furnitureRepository.save_update(furniture);
    }
}
