package com.example.FurnitureBoardApplication.controller.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CommentForm {
    @NotEmpty
    private String content;
}
