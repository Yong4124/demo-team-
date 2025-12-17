package com.example.demo.repository;

import com.example.demo.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    
    // 개인회원별 지원 목록
    List<Application> findByPersonalMemberIdAndDelYnOrderByIdDesc(Long personalMemberId, String delYn);
    
    // 채용공고별 지원자 목록
    List<Application> findByJobPostingIdAndDelYnOrderByIdDesc(Long jobPostingId, String delYn);
    
    // 채용공고별 지원자 목록 (심사상태 필터)
    List<Application> findByJobPostingIdAndReviewStatusAndDelYnOrderByIdDesc(Long jobPostingId, Integer reviewStatus, String delYn);
    
    // 중복 지원 체크
    boolean existsByPersonalMemberIdAndJobPostingIdAndDelYn(Long personalMemberId, Long jobPostingId, String delYn);
    
    // 특정 지원 조회
    Optional<Application> findByPersonalMemberIdAndJobPostingIdAndDelYn(Long personalMemberId, Long jobPostingId, String delYn);
    
    // 통계: 채용공고별 지원자 수
    @Query("SELECT a.jobPosting.id, COUNT(a) FROM Application a WHERE a.delYn = 'N' GROUP BY a.jobPosting.id")
    List<Object[]> countByJobPosting();
    
    // 통계: 심사상태별 지원자 수
    @Query("SELECT a.reviewStatus, COUNT(a) FROM Application a WHERE a.jobPosting.id = :jobPostingId AND a.delYn = 'N' GROUP BY a.reviewStatus")
    List<Object[]> countByReviewStatus(@Param("jobPostingId") Long jobPostingId);
}
