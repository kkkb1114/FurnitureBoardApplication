package com.example.FurnitureBoardApplication.repository;

import com.example.FurnitureBoardApplication.domain.Furniture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class FurnitureRepositoryTest {
    @Autowired
    FurnitureRepository furnitureRepository;

    @Test
    void save_update() {
        //Furniture furniture = Furniture.createFurniture();
    }

    @Test
    void findAll() {
    }

    @Test
    void findId() {
    }

    @Test
    void findAllKind() {
    }

    @Test
    void findAllName() {
    }
}