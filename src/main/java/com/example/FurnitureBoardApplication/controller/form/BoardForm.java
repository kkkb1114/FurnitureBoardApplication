package com.example.FurnitureBoardApplication.controller.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BoardForm {
    @NotEmpty(message = "제목을 입력해주세요.")
    private String title;

    @NotEmpty(message = "작성자가 등록되지 않았습니다.\n다시 로그인을 시도해 주십시오.")
    private String writer;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;
}
