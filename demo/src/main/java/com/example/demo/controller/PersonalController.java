package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/personal")
@RequiredArgsConstructor
public class PersonalController {

    private final PersonalMemberService personalMemberService;
    private final ResumeService resumeService;
    private final ApplicationService applicationService;
    private final FavoriteJobService favoriteJobService;
    private final JobPostingService jobPostingService;

    // 현재 로그인한 회원 조회
    private PersonalMember getCurrentMember(Authentication authentication) {
        String loginId = authentication.getName();
        return personalMemberService.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
    }

    // 대시보드
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        PersonalMember member = getCurrentMember(authentication);

        List<Resume> resumes = resumeService.findByPersonalMemberId(member.getId());
        List<Application> applications = applicationService.findByPersonalMemberId(member.getId());
        List<FavoriteJob> favorites = favoriteJobService.findByPersonalMemberId(member.getId());

        model.addAttribute("member", member);
        model.addAttribute("applicationCount", applications.size());
        model.addAttribute("favoriteCount", favorites.size());
        model.addAttribute("resumeCount", resumes.size());
        model.addAttribute("recentApplications", applications.stream().limit(5).toList());

        return "personal/dashboard";
    }

    // 마이페이지 메인 (대시보드로 리다이렉트)
    @GetMapping("/mypage")
    public String mypage() {
        return "redirect:/personal/dashboard";
    }

    // 회원정보 조회/수정 폼
    @GetMapping("/profile")
    public String profileForm(Authentication authentication, Model model) {
        PersonalMember member = getCurrentMember(authentication);
        model.addAttribute("member", member);
        return "personal/profile";
    }

    // 회원정보 수정 처리
    @PostMapping("/profile")
    public String updateProfile(Authentication authentication,
                                @RequestParam(required = false) String changePassword,
                                @RequestParam(required = false) String newPassword,
                                @RequestParam(required = false) String gender,
                                @RequestParam(required = false) String residence,
                                @RequestParam(required = false) String lastRank,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) String serviceCategory,
                                @RequestParam(required = false) String serviceBranch,
                                RedirectAttributes redirectAttributes) {
        PersonalMember member = getCurrentMember(authentication);

        member.setGender(gender);
        member.setResidence(residence);
        member.setLastRank(lastRank);
        member.setEmail(email);
        member.setServiceCategory(serviceCategory);
        member.setServiceBranch(serviceBranch);

        // 비밀번호 변경
        if ("Y".equals(changePassword) && newPassword != null && !newPassword.isEmpty()) {
            personalMemberService.changePassword(member.getId(), newPassword);
        }

        personalMemberService.update(member);

        redirectAttributes.addFlashAttribute("success", "회원정보가 수정되었습니다.");
        return "redirect:/personal/profile";
    }

    // 지원현황
    @GetMapping("/applications")
    public String applications(Authentication authentication, Model model) {
        PersonalMember member = getCurrentMember(authentication);
        List<Application> applications = applicationService.findByPersonalMemberId(member.getId());

        model.addAttribute("applications", applications);
        return "personal/applications";
    }

    // 관심 채용공고
    @GetMapping("/favorites")
    public String favorites(Authentication authentication, Model model) {
        PersonalMember member = getCurrentMember(authentication);
        List<FavoriteJob> favorites = favoriteJobService.findByPersonalMemberId(member.getId());

        model.addAttribute("favorites", favorites);
        return "personal/favorites";
    }

    // 관심 등록
    @PostMapping("/favorite/add")
    @ResponseBody
    public Map<String, Object> addFavorite(Authentication authentication, @RequestParam Long jobId) {
        Map<String, Object> result = new HashMap<>();
        try {
            PersonalMember member = getCurrentMember(authentication);
            JobPosting job = jobPostingService.findById(jobId)
                    .orElseThrow(() -> new IllegalArgumentException("채용공고를 찾을 수 없습니다."));

            favoriteJobService.addFavorite(member, job);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    // 관심 해제
    @PostMapping("/favorite/remove")
    @ResponseBody
    public Map<String, Object> removeFavorite(Authentication authentication, @RequestParam Long jobId) {
        Map<String, Object> result = new HashMap<>();
        try {
            PersonalMember member = getCurrentMember(authentication);
            favoriteJobService.removeFavorite(member.getId(), jobId);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    // 지원하기 페이지
    @GetMapping("/apply/{jobId}")
    public String applyForm(@PathVariable Long jobId, Authentication authentication, Model model) {
        PersonalMember member = getCurrentMember(authentication);
        JobPosting job = jobPostingService.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("채용공고를 찾을 수 없습니다."));

        if (applicationService.existsByPersonalMemberIdAndJobPostingId(member.getId(), jobId)) {
            return "redirect:/job/detail/" + jobId + "?error=already";
        }

        List<Resume> resumes = resumeService.findByPersonalMemberId(member.getId());

        model.addAttribute("job", job);
        model.addAttribute("resumes", resumes);
        return "personal/apply";
    }

    // 지원 처리
    @PostMapping("/apply")
    public String apply(Authentication authentication,
                        @RequestParam Long jobId,
                        @RequestParam Long resumeId,
                        RedirectAttributes redirectAttributes) {
        PersonalMember member = getCurrentMember(authentication);
        JobPosting job = jobPostingService.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("채용공고를 찾을 수 없습니다."));
        Resume resume = resumeService.findById(resumeId)
                .orElseThrow(() -> new IllegalArgumentException("이력서를 찾을 수 없습니다."));

        if (applicationService.existsByPersonalMemberIdAndJobPostingId(member.getId(), jobId)) {
            redirectAttributes.addFlashAttribute("errorMessage", "이미 지원한 공고입니다.");
            return "redirect:/job/detail/" + jobId;
        }

        Application application = Application.builder()
                .jobPosting(job)
                .resume(resume)
                .personalMember(member)
                .build();

        applicationService.apply(application);

        redirectAttributes.addFlashAttribute("successMessage", "지원이 완료되었습니다.");
        return "redirect:/personal/applications";
    }

    // 회원 탈퇴
    @GetMapping("/withdraw")
    public String withdraw(Authentication authentication, RedirectAttributes redirectAttributes) {
        PersonalMember member = getCurrentMember(authentication);
        personalMemberService.delete(member.getId());
        redirectAttributes.addFlashAttribute("message", "회원 탈퇴가 완료되었습니다.");
        return "redirect:/auth/logout";
    }
}