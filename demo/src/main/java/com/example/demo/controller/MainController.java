package com.example.demo.controller;

import com.example.demo.entity.JobPosting;
import com.example.demo.repository.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final JobPostingRepository jobPostingRepository;

    @GetMapping({"/", "/main"})
    public String main(Model model) {
        // 최신 채용공고 12개
        List<JobPosting> recentJobs = jobPostingRepository.findActiveJobPostings(LocalDate.now());
        if (recentJobs.size() > 12) {
            recentJobs = recentJobs.subList(0, 12);
        }
        model.addAttribute("recentJobs", recentJobs);
        
        return "main";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
    
    @GetMapping("/policy")
    public String policy() {
        return "policy";
    }
}
