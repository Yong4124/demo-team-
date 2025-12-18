package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PersonalRegisterDto {

    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 4, max = 20, message = "아이디는 4~20자로 입력해주세요")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 6, max = 20, message = "비밀번호는 6~20자로 입력해주세요")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요")
    private String passwordConfirm;

    @NotBlank(message = "이름을 입력해주세요")
    private String firstName;

    @NotBlank(message = "성을 입력해주세요")
    private String lastName;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;

    private String birthDate;
    private String gender;
    private String residence;
    private String lastRank;
    private String serviceCategory;
    private String serviceBranch;
    private String serviceYear;
    private String serviceStation;
    private String unitPosition;
}
