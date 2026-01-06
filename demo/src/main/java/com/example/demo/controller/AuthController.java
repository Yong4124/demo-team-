package com.example.demo.controller;

import com.example.demo.entity.CompanyMember;
import com.example.demo.entity.PersonalMember;
import com.example.demo.service.CompanyMemberService;
import com.example.demo.service.PersonalMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final PersonalMemberService personalMemberService;
    private final CompanyMemberService companyMemberService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerSelect() {
        return "auth/register";
    }

    // 개인회원 가입 폼
    @GetMapping("/register/personal")
    public String registerPersonalForm() {
        return "auth/register-personal";
    }

    // 개인회원 가입 처리
    @PostMapping("/register/personal")
    public String registerPersonal(
            @RequestParam String loginId,
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam String passwordConfirm,
            @RequestParam String email,
            @RequestParam(required = false) String birthDate,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String residence,
            @RequestParam(required = false) String lastRank,
            @RequestParam(required = false) String serviceCategory,
            @RequestParam(required = false) String serviceBranch,
            @RequestParam(required = false) String serviceYear,
            @RequestParam(required = false) String serviceStation,
            @RequestParam(required = false) String serviceUnit,
            RedirectAttributes redirectAttributes,
            Model model) {

        // 비밀번호 확인
        if (!password.equals(passwordConfirm)) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "auth/register-personal";
        }

        // 아이디 중복 확인
        if (personalMemberService.existsByLoginId(loginId)) {
            model.addAttribute("error", "이미 사용 중인 아이디입니다.");
            return "auth/register-personal";
        }

        // 이메일 중복 확인
        if (personalMemberService.existsByEmail(email)) {
            model.addAttribute("error", "이미 등록된 이메일입니다.");
            return "auth/register-personal";
        }

        // 회원 저장 (name 필드를 firstName에 저장)
        PersonalMember member = PersonalMember.builder()
                .loginId(loginId)
                .password(password)
                .firstName(name)
                .lastName("")
                .email(email)
                .gender(gender)
                .residence(residence)
                .lastRank(lastRank)
                .serviceCategory(serviceCategory)
                .serviceBranch(serviceBranch)
                .serviceYear(serviceYear)
                .serviceStation(serviceStation)
                .unitPosition(serviceUnit)
                .build();

        if (birthDate != null && !birthDate.isEmpty()) {
            member.setBirthDate(LocalDate.parse(birthDate));
        }

        personalMemberService.register(member);

        redirectAttributes.addFlashAttribute("successMessage", "회원가입이 완료되었습니다. 로그인해주세요.");
        return "redirect:/auth/login";
    }

    // 기업회원 가입 폼
    @GetMapping("/register/company")
    public String registerCompanyForm() {
        return "auth/register-company";
    }

    // 기업회원 가입 처리
    @PostMapping("/register/company")
    public String registerCompany(
            @RequestParam String loginId,
            @RequestParam String managerName,
            @RequestParam String password,
            @RequestParam String passwordConfirm,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String company,
            @RequestParam(required = false) String groupCompany,
            @RequestParam(required = false) String companyType,
            @RequestParam(required = false) String industry,
            @RequestParam(required = false) String establishedDate,
            @RequestParam(required = false) String employeeNum,
            @RequestParam(required = false) String capital,
            @RequestParam(required = false) String revenue,
            @RequestParam(required = false) String homepage,
            @RequestParam(required = false) String address,
            RedirectAttributes redirectAttributes,
            Model model) {

        // 비밀번호 확인
        if (!password.equals(passwordConfirm)) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "auth/register-company";
        }

        // 아이디 중복 확인
        if (companyMemberService.existsByLoginId(loginId)) {
            model.addAttribute("error", "이미 사용 중인 아이디입니다.");
            return "auth/register-company";
        }

        // 이메일 중복 확인
        if (companyMemberService.existsByEmail(email)) {
            model.addAttribute("error", "이미 등록된 이메일입니다.");
            return "auth/register-company";
        }

        // 회원 저장
        CompanyMember member = CompanyMember.builder()
                .loginId(loginId)
                .password(password)
                .managerName(managerName)
                .phone(phone)
                .email(email)
                .company(company)
                .parentCompanyCd(groupCompany)
                .companyAddress(address)
                .build();

        companyMemberService.register(member);

        redirectAttributes.addFlashAttribute("successMessage", "회원가입이 완료되었습니다. 로그인해주세요.");
        return "redirect:/auth/login";
    }

    // 아이디 중복 확인 API
    @GetMapping("/check-id")
    @ResponseBody
    public Map<String, Object> checkId(@RequestParam String loginId, @RequestParam String type) {
        Map<String, Object> result = new HashMap<>();
        boolean exists;
        if ("personal".equals(type)) {
            exists = personalMemberService.existsByLoginId(loginId);
        } else {
            exists = companyMemberService.existsByLoginId(loginId);
        }
        result.put("available", !exists);
        return result;
    }

    // 아이디 찾기
    @GetMapping("/find-id")
    public String findId() {
        return "auth/find-id";
    }

    // 비밀번호 찾기
    @GetMapping("/find-pw")
    public String findPassword() {
        return "auth/find-pw";
    }
}
