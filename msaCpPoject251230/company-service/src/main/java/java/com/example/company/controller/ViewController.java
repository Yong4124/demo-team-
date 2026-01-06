package java.com.example.company.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 기업 회원용 View Controller
 * Company-Service에서 직접 화면 렌더링
 */
@Controller
@RequestMapping("/company")
public class ViewController {

    // 기업 로그인 페이지
    @GetMapping("/login")
    public String companyLogin() {
        return "company/login";
    }

    // 기업 회원가입 페이지
    @GetMapping("/register")
    public String companyRegister() {
        return "company/register";
    }

    // 기업 마이페이지
    @GetMapping("/mypage")
    public String companyMypage() {
        return "company/mypage";
    }

    // 기업 정보 수정
    @GetMapping("/mypage/edit")
    public String companyMypageEdit() {
        return "company/mypage_edit";
    }

    // 기업 비밀번호 변경
    @GetMapping("/mypage/password")
    public String companyMypagePassword() {
        return "company/password_change";
    }

    // 기업 채용공고 관리
    @GetMapping("/jobs")
    public String companyJobs() {
        return "company/job_list";
    }

    // 기업 채용공고 등록
    @GetMapping("/jobs/write")
    public String companyJobWrite() {
        return "company/job_write";
    }
}
