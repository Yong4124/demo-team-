package com.example.demo.controller;

import com.example.demo.entity.JobPosting;
import com.example.demo.service.ApplicationService;
import com.example.demo.service.FavoriteJobService;
import com.example.demo.service.JobPostingService;
import com.example.demo.service.PersonalMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {

    private final JobPostingService jobPostingService;
    private final PersonalMemberService personalMemberService;
    private final FavoriteJobService favoriteJobService;
    private final ApplicationService applicationService;

    // 채용공고 목록
    @GetMapping("/list")
    public String list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchField,
            @RequestParam(required = false) String groupCompany,
            @RequestParam(required = false, defaultValue = "START_DATE") String sortOption,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        
        // 정렬 설정
        Sort sort = sortOption.equals("END_DATE") 
                ? Sort.by("endDate").ascending() 
                : Sort.by("startDate").descending();
        
        Pageable pageable = PageRequest.of(page, 10, sort);
        
        // 검색 조건에 따른 조회
        Page<JobPosting> jobPage = jobPostingService.searchJobPostings(keyword, searchField, groupCompany, pageable);
        
        // 페이징 정보
        int totalPages = jobPage.getTotalPages();
        int currentPage = jobPage.getNumber();
        int startPage = Math.max(0, currentPage - 2);
        int endPage = Math.min(totalPages - 1, currentPage + 2);
        
        model.addAttribute("jobPostings", jobPage.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchField", searchField);
        model.addAttribute("groupCompany", groupCompany);
        model.addAttribute("sortOption", sortOption);
        
        return "job/list";
    }

    // 채용공고 상세
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model, Authentication authentication) {
        JobPosting jobPosting = jobPostingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채용공고입니다."));
        
        model.addAttribute("job", jobPosting);
        
        // D-Day 계산
        long dDay = 0;
        if (jobPosting.getEndDate() != null) {
            dDay = ChronoUnit.DAYS.between(LocalDate.now(), jobPosting.getEndDate());
        }
        model.addAttribute("dDay", dDay);
        
        // 기본값 설정
        model.addAttribute("isFavorite", false);
        model.addAttribute("hasApplied", false);
        
        // 로그인한 개인회원인 경우 관심등록 여부, 지원 여부 확인
        if (authentication != null) {
            String loginId = authentication.getName();
            personalMemberService.findByLoginId(loginId).ifPresent(member -> {
                boolean isFavorite = favoriteJobService.isFavorite(member.getId(), id);
                boolean hasApplied = applicationService.existsByPersonalMemberIdAndJobPostingId(member.getId(), id);
                model.addAttribute("isFavorite", isFavorite);
                model.addAttribute("hasApplied", hasApplied);
                model.addAttribute("memberId", member.getId());
            });
        }
        
        return "job/detail";
    }
}
