package com.example.demo.service;

import com.example.demo.entity.Application;
import com.example.demo.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    // 지원 조회
    public Optional<Application> findById(Long id) {
        return applicationRepository.findById(id)
                .filter(a -> "N".equals(a.getDelYn()));
    }

    // 개인회원별 지원 목록
    public List<Application> findByPersonalMemberId(Long personalMemberId) {
        return applicationRepository.findByPersonalMemberIdAndDelYnOrderByIdDesc(personalMemberId, "N");
    }

    // 채용공고별 지원자 목록
    public List<Application> findByJobPostingId(Long jobPostingId) {
        return applicationRepository.findByJobPostingIdAndDelYnOrderByIdDesc(jobPostingId, "N");
    }

    // 채용공고별 지원자 목록 (심사상태 필터)
    public List<Application> findByJobPostingIdAndReviewStatus(Long jobPostingId, Integer reviewStatus) {
        return applicationRepository.findByJobPostingIdAndReviewStatusAndDelYnOrderByIdDesc(jobPostingId, reviewStatus, "N");
    }

    // 중복 지원 확인
    public boolean existsByPersonalMemberIdAndJobPostingId(Long personalMemberId, Long jobPostingId) {
        return applicationRepository.existsByPersonalMemberIdAndJobPostingIdAndDelYn(personalMemberId, jobPostingId, "N");
    }

    // 지원하기
    @Transactional
    public Application apply(Application application) {
        application.setReviewStatus(0);  // 미심사
        application.setCancelStatus("N");
        application.setDelYn("N");
        return applicationRepository.save(application);
    }

    // 지원 취소
    @Transactional
    public void cancel(Long id) {
        applicationRepository.findById(id).ifPresent(application -> {
            application.setCancelStatus("Y");
        });
    }

    // 심사상태 변경 (기업회원용)
    @Transactional
    public void updateReviewStatus(Long id, Integer reviewStatus) {
        applicationRepository.findById(id).ifPresent(application -> {
            application.setReviewStatus(reviewStatus);
        });
    }
    
    // 상태 변경 (문자열)
    @Transactional
    public void updateStatus(Long id, String status) {
        applicationRepository.findById(id).ifPresent(application -> {
            application.setStatus(status);
        });
    }

    // 통계: 심사상태별 지원자 수
    public List<Object[]> getReviewStatusStats(Long jobPostingId) {
        return applicationRepository.countByReviewStatus(jobPostingId);
    }
}
