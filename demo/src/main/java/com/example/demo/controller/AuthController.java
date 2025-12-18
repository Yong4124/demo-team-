package com.example.demo.controller;

import com.example.demo.dto.CompanyRegisterDto;
import com.example.demo.dto.PersonalRegisterDto;
import com.example.demo.entity.CompanyMember;
import com.example.demo.entity.PersonalMember;
import com.example.demo.service.CompanyMemberService;
import com.example.demo.service.PersonalMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final PersonalMemberService personalMemberService;
    private final CompanyMemberService companyMemberService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "로그아웃되었습니다.");
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerSelect() {
        return "auth/register-select";
    }

    // 개인회원 가입 폼
    @GetMapping("/register/personal")
    public String registerPersonalForm(Model model) {
        model.addAttribute("registerDto", new PersonalRegisterDto());
        return "auth/register-personal";
    }

    // 개인회원 가입 처리
    @PostMapping("/register/personal")
    public String registerPersonal(@Valid @ModelAttribute("registerDto") PersonalRegisterDto dto,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {
        // 비밀번호 확인
        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "error.passwordConfirm", "비밀번호가 일치하지 않습니다.");
        }

        // 아이디 중복 확인
        if (personalMemberService.existsByLoginId(dto.getLoginId())) {
            bindingResult.rejectValue("loginId", "error.loginId", "이미 사용 중인 아이디입니다.");
        }

        // 이메일 중복 확인
        if (personalMemberService.existsByEmail(dto.getEmail())) {
            bindingResult.rejectValue("email", "error.email", "이미 등록된 이메일입니다.");
        }

        if (bindingResult.hasErrors()) {
            return "auth/register-personal";
        }

        // 회원 저장
        PersonalMember member = PersonalMember.builder()
                .loginId(dto.getLoginId())
                .password(dto.getPassword())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .residence(dto.getResidence())
                .lastRank(dto.getLastRank())
                .serviceCategory(dto.getServiceCategory())
                .serviceBranch(dto.getServiceBranch())
                .serviceYear(dto.getServiceYear())
                .serviceStation(dto.getServiceStation())
                .unitPosition(dto.getUnitPosition())
                .build();

        if (dto.getBirthDate() != null && !dto.getBirthDate().isEmpty()) {
            member.setBirthDate(LocalDate.parse(dto.getBirthDate()));
        }

        personalMemberService.register(member);

        redirectAttributes.addFlashAttribute("successMessage", "회원가입이 완료되었습니다. 관리자 승인 후 로그인할 수 있습니다.");
        return "redirect:/auth/login";
    }

    // 기업회원 가입 폼
    @GetMapping("/register/company")
    public String registerCompanyForm(Model model) {
        model.addAttribute("registerDto", new CompanyRegisterDto());
        return "auth/register-company";
    }

    // 기업회원 가입 처리
    @PostMapping("/register/company")
    public String registerCompany(@Valid @ModelAttribute("registerDto") CompanyRegisterDto dto,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        // 비밀번호 확인
        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "error.passwordConfirm", "비밀번호가 일치하지 않습니다.");
        }

        // 아이디 중복 확인
        if (companyMemberService.existsByLoginId(dto.getLoginId())) {
            bindingResult.rejectValue("loginId", "error.loginId", "이미 사용 중인 아이디입니다.");
        }

        // 이메일 중복 확인
        if (companyMemberService.existsByEmail(dto.getEmail())) {
            bindingResult.rejectValue("email", "error.email", "이미 등록된 이메일입니다.");
        }

        // 사업자번호 중복 확인
        if (companyMemberService.existsByBusinessRegistNum(dto.getBusinessRegistNum())) {
            bindingResult.rejectValue("businessRegistNum", "error.businessRegistNum", "이미 등록된 사업자번호입니다.");
        }

        if (bindingResult.hasErrors()) {
            return "auth/register-company";
        }

        // 회원 저장
        CompanyMember member = CompanyMember.builder()
                .loginId(dto.getLoginId())
                .password(dto.getPassword())
                .managerName(dto.getManagerName())
                .department(dto.getDepartment())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .businessRegistNum(dto.getBusinessRegistNum())
                .company(dto.getCompany())
                .presidentName(dto.getPresidentName())
                .companyAddress(dto.getCompanyAddress())
                .parentCompanyCd(dto.getParentCompanyCd())
                .build();

        companyMemberService.register(member);

        redirectAttributes.addFlashAttribute("successMessage", "회원가입이 완료되었습니다. 관리자 승인 후 로그인할 수 있습니다.");
        return "redirect:/auth/login";
    }

    // 아이디 중복 확인 API
    @GetMapping("/check-id")
    @ResponseBody
    public Map<String, Boolean> checkId(@RequestParam String loginId, @RequestParam String type) {
        Map<String, Boolean> result = new HashMap<>();
        boolean exists;
        if ("personal".equals(type)) {
            exists = personalMemberService.existsByLoginId(loginId);
        } else {
            exists = companyMemberService.existsByLoginId(loginId);
        }
        result.put("exists", exists);
        return result;
    }
}
