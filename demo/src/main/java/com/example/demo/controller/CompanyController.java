package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyMemberService companyMemberService;
    private final JobPostingService jobPostingService;
    private final ApplicationService applicationService;

    // 현재 로그인한 기업회원 조회
    private CompanyMember getCurrentMember(Authentication authentication) {
        String loginId = authentication.getName();
        return companyMemberService.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
    }

    // 대시보드
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        CompanyMember member = getCurrentMember(authentication);
        List<JobPosting> jobPostings = jobPostingService.findByCompanyMemberId(member.getId());
        
        // 진행중 공고 수
        long activeJobCount = jobPostings.stream()
                .filter(j -> "1".equals(j.getPostingYn()) && j.getEndDate() != null && !j.getEndDate().isBefore(LocalDate.now()))
                .count();
        
        // 총 지원자 수
        int totalApplicants = 0;
        List<Application> recentApplicants = new java.util.ArrayList<>();
        for (JobPosting job : jobPostings) {
            List<Application> apps = applicationService.findByJobPostingId(job.getId());
            totalApplicants += apps.size();
            recentApplicants.addAll(apps);
        }
        
        // 최근 5명만 (ID 역순으로 정렬 - ID가 클수록 최신)
        recentApplicants.sort((a, b) -> b.getId().compareTo(a.getId()));
        if (recentApplicants.size() > 5) {
            recentApplicants = recentApplicants.subList(0, 5);
        }
        
        model.addAttribute("member", member);
        model.addAttribute("activeJobCount", activeJobCount);
        model.addAttribute("totalApplicants", totalApplicants);
        model.addAttribute("totalJobCount", jobPostings.size());
        model.addAttribute("recentApplicants", recentApplicants);
        
        return "company/dashboard";
    }

    // 마이페이지 메인 (대시보드로 리다이렉트)
    @GetMapping("/mypage")
    public String mypage() {
        return "redirect:/company/dashboard";
    }

    // 회원정보 조회/수정 폼
    @GetMapping("/profile")
    public String profileForm(Authentication authentication, Model model) {
        CompanyMember member = getCurrentMember(authentication);
        model.addAttribute("member", member);
        return "company/profile";
    }

    // 회원정보 수정 처리
    @PostMapping("/profile")
    public String updateProfile(Authentication authentication,
                                @RequestParam String managerName,
                                @RequestParam String email,
                                @RequestParam String phone,
                                @RequestParam String company,
                                @RequestParam(required = false) String parentCompanyCd,
                                @RequestParam(required = false) String companyAddress,
                                @RequestParam(required = false) String changePassword,
                                @RequestParam(required = false) String newPassword,
                                RedirectAttributes redirectAttributes) {
        CompanyMember member = getCurrentMember(authentication);
        
        member.setManagerName(managerName);
        member.setEmail(email);
        member.setPhone(phone);
        member.setCompany(company);
        member.setParentCompanyCd(parentCompanyCd);
        member.setCompanyAddress(companyAddress);
        
        if ("Y".equals(changePassword) && newPassword != null && !newPassword.isEmpty()) {
            companyMemberService.changePassword(member.getId(), newPassword);
        }
        
        companyMemberService.update(member);
        
        redirectAttributes.addFlashAttribute("success", "회원정보가 수정되었습니다.");
        return "redirect:/company/profile";
    }

    // 채용공고 관리
    @GetMapping("/jobs")
    public String jobs(Authentication authentication, Model model) {
        CompanyMember member = getCurrentMember(authentication);
        List<JobPosting> jobs = jobPostingService.findByCompanyMemberId(member.getId());
        
        // 각 공고별 지원자 수를 Map으로 전달
        Map<Long, Integer> applicationCounts = new HashMap<>();
        for (JobPosting job : jobs) {
            int count = applicationService.findByJobPostingId(job.getId()).size();
            applicationCounts.put(job.getId(), count);
        }
        
        model.addAttribute("jobs", jobs);
        model.addAttribute("applicationCounts", applicationCounts);
        return "company/jobs";
    }

    // 채용공고 등록 폼
    @GetMapping("/job/new")
    public String newJobForm(Authentication authentication, Model model) {
        CompanyMember member = getCurrentMember(authentication);
        model.addAttribute("member", member);
        model.addAttribute("job", new JobPosting());
        return "company/job-form";
    }

    // 채용공고 등록 처리
    @PostMapping("/job/new")
    public String createJob(Authentication authentication,
                            @ModelAttribute JobPosting jobPosting,
                            @RequestParam(required = false) String action,
                            RedirectAttributes redirectAttributes) {
        CompanyMember member = getCurrentMember(authentication);
        jobPosting.setCompanyMember(member);
        
        if ("temp".equals(action)) {
            jobPostingService.saveTemp(jobPosting);
            redirectAttributes.addFlashAttribute("successMessage", "임시저장되었습니다.");
        } else {
            jobPostingService.publish(jobPosting);
            redirectAttributes.addFlashAttribute("successMessage", "채용공고가 등록되었습니다.");
        }
        
        return "redirect:/company/jobs";
    }

    // 채용공고 수정 폼
    @GetMapping("/job/edit/{id}")
    public String editJobForm(@PathVariable Long id, Authentication authentication, Model model) {
        CompanyMember member = getCurrentMember(authentication);
        JobPosting job = jobPostingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("채용공고를 찾을 수 없습니다."));
        
        // 본인 공고인지 확인
        if (!job.getCompanyMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        
        model.addAttribute("member", member);
        model.addAttribute("job", job);
        return "company/job-form";
    }

    // 채용공고 수정 처리
    @PostMapping("/job/edit/{id}")
    public String updateJob(@PathVariable Long id,
                            Authentication authentication,
                            @ModelAttribute JobPosting updatedJob,
                            @RequestParam(required = false) String action,
                            RedirectAttributes redirectAttributes) {
        CompanyMember member = getCurrentMember(authentication);
        JobPosting job = jobPostingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("채용공고를 찾을 수 없습니다."));
        
        if (!job.getCompanyMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        
        // 필드 업데이트
        job.setTitle(updatedJob.getTitle());
        job.setStartDate(updatedJob.getStartDate());
        job.setEndDate(updatedJob.getEndDate());
        job.setJobForm(updatedJob.getJobForm());
        job.setJobType(updatedJob.getJobType());
        job.setJobCategory(updatedJob.getJobCategory());
        job.setIndustry(updatedJob.getIndustry());
        job.setWorkTime(updatedJob.getWorkTime());
        job.setRoleLevel(updatedJob.getRoleLevel());
        job.setBaseSalary(updatedJob.getBaseSalary());
        job.setExperience(updatedJob.getExperience());
        job.setJobLocation(updatedJob.getJobLocation());
        job.setCompanyIntro(updatedJob.getCompanyIntro());
        job.setPositionSummary(updatedJob.getPositionSummary());
        job.setSkillQualification(updatedJob.getSkillQualification());
        job.setBenefits(updatedJob.getBenefits());
        job.setNotes(updatedJob.getNotes());
        job.setCompanyType(updatedJob.getCompanyType());
        job.setEstablishedDate(updatedJob.getEstablishedDate());
        job.setEmployeeNum(updatedJob.getEmployeeNum());
        job.setCapital(updatedJob.getCapital());
        job.setRevenue(updatedJob.getRevenue());
        job.setHomepage(updatedJob.getHomepage());
        
        if ("temp".equals(action)) {
            job.setPostingYn("2");
            redirectAttributes.addFlashAttribute("successMessage", "임시저장되었습니다.");
        } else {
            job.setPostingYn("1");
            redirectAttributes.addFlashAttribute("successMessage", "채용공고가 수정되었습니다.");
        }
        
        jobPostingService.update(job);
        return "redirect:/company/jobs";
    }

    // 채용공고 삭제 (GET)
    @GetMapping("/job/delete/{id}")
    public String deleteJobGet(@PathVariable Long id,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        CompanyMember member = getCurrentMember(authentication);
        JobPosting job = jobPostingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("채용공고를 찾을 수 없습니다."));
        
        if (!job.getCompanyMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        
        jobPostingService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "채용공고가 삭제되었습니다.");
        return "redirect:/company/jobs";
    }

    // 채용공고 삭제 (POST)
    @PostMapping("/jobs/{id}/delete")
    public String deleteJob(@PathVariable Long id,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes) {
        return deleteJobGet(id, authentication, redirectAttributes);
    }

    // 지원자 관리 (전체)
    @GetMapping("/applicants")
    public String applicants(@RequestParam(required = false) Long jobId,
                             Authentication authentication, Model model) {
        CompanyMember member = getCurrentMember(authentication);
        List<JobPosting> jobs = jobPostingService.findByCompanyMemberId(member.getId());
        
        List<Application> applicants;
        if (jobId != null) {
            applicants = applicationService.findByJobPostingId(jobId);
        } else {
            applicants = new java.util.ArrayList<>();
            for (JobPosting job : jobs) {
                applicants.addAll(applicationService.findByJobPostingId(job.getId()));
            }
        }
        
        // ID 역순으로 정렬 (최신순)
        applicants.sort((a, b) -> b.getId().compareTo(a.getId()));
        
        model.addAttribute("jobs", jobs);
        model.addAttribute("applicants", applicants);
        model.addAttribute("selectedJobId", jobId);
        
        return "company/applicants";
    }

    // 지원자 상태 변경 (AJAX)
    @PostMapping("/applicant/status")
    @ResponseBody
    public Map<String, Object> updateApplicantStatus(@RequestParam Long applicationId,
                                                     @RequestParam String status) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 문자열 상태를 Integer로 변환
            Integer reviewStatus = convertStatusToInteger(status);
            applicationService.updateReviewStatus(applicationId, reviewStatus);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    // 상태 문자열을 Integer로 변환
    private Integer convertStatusToInteger(String status) {
        return switch (status) {
            case "서류검토중" -> 0;
            case "서류합격" -> 1;
            case "면접예정" -> 1;
            case "최종합격" -> 2;
            case "불합격" -> 3;
            default -> 0; // 접수완료
        };
    }

    // 회원 탈퇴
    @GetMapping("/withdraw")
    public String withdraw(Authentication authentication, RedirectAttributes redirectAttributes) {
        CompanyMember member = getCurrentMember(authentication);
        companyMemberService.delete(member.getId());
        redirectAttributes.addFlashAttribute("message", "회원 탈퇴가 완료되었습니다.");
        return "redirect:/auth/logout";
    }
}
