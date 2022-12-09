package com.example.FurnitureBoardApplication.controller.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PasswordUpdateForm {
    @NotEmpty(message = "현재 비밀번호를 적어주세요")
    String nowPassword;
    @NotEmpty(message = "변경할 비밀번호를 적어주세요")
    String updatePassword;
    @NotEmpty(message = "변경할 비밀번호를 적어주세요")
    String updatePasswordConfirm;
}
