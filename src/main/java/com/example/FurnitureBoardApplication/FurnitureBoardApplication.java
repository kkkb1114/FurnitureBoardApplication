package com.example.FurnitureBoardApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @EnableJpaAuditing: main method가 있는 클래스에 적용하며 DB에 데이터가 저장되거나 수정될 때 언제, 누가 했는지 자동으로 관리할 수 있게 해준다.
 */
@EnableJpaAuditing
@SpringBootApplication
public class FurnitureBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(FurnitureBoardApplication.class, args);
	}

}
