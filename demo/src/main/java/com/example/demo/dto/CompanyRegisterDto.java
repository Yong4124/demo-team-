package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CompanyRegisterDto {

    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 4, max = 20, message = "아이디는 4~20자로 입력해주세요")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 6, max = 20, message = "비밀번호는 6~20자로 입력해주세요")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요")
    private String passwordConfirm;

    @NotBlank(message = "담당자명을 입력해주세요")
    private String managerName;

    private String department;

    @NotBlank(message = "전화번호를 입력해주세요")
    private String phone;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;

    @NotBlank(message = "사업자번호를 입력해주세요")
    private String businessRegistNum;

    @NotBlank(message = "회사명을 입력해주세요")
    private String company;

    @NotBlank(message = "대표자명을 입력해주세요")
    private String presidentName;

    private String companyAddress;
    private String parentCompanyCd;
}
