package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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

    // 마이페이지 메인
    @GetMapping("/mypage")
    public String mypage(Authentication authentication, Model model) {
        PersonalMember member = getCurrentMember(authentication);
        
        // 이력서 목록
        List<Resume> resumes = resumeService.findByPersonalMemberId(member.getId());
        // 지원 목록
        List<Application> applications = applicationService.findByPersonalMemberId(member.getId());
        // 관심 채용공고
        List<FavoriteJob> favorites = favoriteJobService.findByPersonalMemberId(member.getId());
        
        model.addAttribute("member", member);
        model.addAttribute("resumes", resumes);
        model.addAttribute("applications", applications);
        model.addAttribute("favorites", favorites);
        
        return "personal/mypage";
    }

    // 회원정보 수정 폼
    @GetMapping("/edit")
    public String editForm(Authentication authentication, Model model) {
        PersonalMember member = getCurrentMember(authentication);
        model.addAttribute("member", member);
        return "personal/edit";
    }

    // 회원정보 수정 처리
    @PostMapping("/edit")
    public String edit(Authentication authentication,
                       @ModelAttribute PersonalMember updatedMember,
                       RedirectAttributes redirectAttributes) {
        PersonalMember member = getCurrentMember(authentication);
        
        // 수정 가능한 필드만 업데이트
        member.setFirstName(updatedMember.getFirstName());
        member.setLastName(updatedMember.getLastName());
        member.setEmail(updatedMember.getEmail());
        member.setGender(updatedMember.getGender());
        member.setResidence(updatedMember.getResidence());
        member.setLastRank(updatedMember.getLastRank());
        member.setServiceCategory(updatedMember.getServiceCategory());
        member.setServiceBranch(updatedMember.getServiceBranch());
        member.setServiceYear(updatedMember.getServiceYear());
        member.setServiceStation(updatedMember.getServiceStation());
        member.setUnitPosition(updatedMember.getUnitPosition());
        
        personalMemberService.update(member);
        
        redirectAttributes.addFlashAttribute("successMessage", "회원정보가 수정되었습니다.");
        return "redirect:/personal/mypage";
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
    public String addFavorite(Authentication authentication, @RequestParam Long jobId) {
        PersonalMember member = getCurrentMember(authentication);
        JobPosting job = jobPostingService.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("채용공고를 찾을 수 없습니다."));
        
        favoriteJobService.addFavorite(member, job);
        return "ok";
    }

    // 관심 해제
    @PostMapping("/favorite/remove")
    @ResponseBody
    public String removeFavorite(Authentication authentication, @RequestParam Long jobId) {
        PersonalMember member = getCurrentMember(authentication);
        favoriteJobService.removeFavorite(member.getId(), jobId);
        return "ok";
    }

    // 지원하기 페이지
    @GetMapping("/apply/{jobId}")
    public String applyForm(@PathVariable Long jobId, Authentication authentication, Model model) {
        PersonalMember member = getCurrentMember(authentication);
        JobPosting job = jobPostingService.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("채용공고를 찾을 수 없습니다."));
        
        // 이미 지원했는지 확인
        if (applicationService.existsByPersonalMemberIdAndJobPostingId(member.getId(), jobId)) {
            return "redirect:/job/detail/" + jobId + "?error=already";
        }
        
        // 이력서 목록
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
        
        // 중복 지원 확인
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

    // 지원 취소
    @PostMapping("/apply/cancel")
    public String cancelApplication(@RequestParam Long applicationId,
                                    RedirectAttributes redirectAttributes) {
        applicationService.cancel(applicationId);
        redirectAttributes.addFlashAttribute("successMessage", "지원이 취소되었습니다.");
        return "redirect:/personal/applications";
    }
}
