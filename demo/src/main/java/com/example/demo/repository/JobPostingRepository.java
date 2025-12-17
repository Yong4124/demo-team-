package com.example.demo.repository;

import com.example.demo.entity.CompanyMember;
import com.example.demo.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    
    // 공개된 채용공고 목록 (공고완료 + 미삭제 + 마감일 지나지 않음)
    @Query("SELECT j FROM JobPosting j WHERE j.postingYn = '1' AND j.delYn = 'N' AND j.endDate >= :today ORDER BY j.id DESC")
    List<JobPosting> findActiveJobPostings(@Param("today") LocalDate today);
    
    // 기업회원별 채용공고 목록
    List<JobPosting> findByCompanyMemberAndDelYnOrderByIdDesc(CompanyMember companyMember, String delYn);
    
    // 기업회원 ID로 채용공고 목록
    List<JobPosting> findByCompanyMemberIdAndDelYnOrderByIdDesc(Long companyMemberId, String delYn);
    
    // 검색: 제목 또는 회사명
    @Query("SELECT j FROM JobPosting j WHERE j.postingYn = '1' AND j.delYn = 'N' AND j.endDate >= :today " +
           "AND (j.title LIKE %:keyword% OR j.companyMember.company LIKE %:keyword%) ORDER BY j.id DESC")
    List<JobPosting> searchByKeyword(@Param("keyword") String keyword, @Param("today") LocalDate today);
    
    // 관리자용: 전체 채용공고 목록
    List<JobPosting> findByDelYnOrderByIdDesc(String delYn);
    
    // 통계: 월별 채용공고 수
    @Query("SELECT FUNCTION('MONTH', j.startDate) as month, COUNT(j) FROM JobPosting j " +
           "WHERE j.delYn = 'N' AND FUNCTION('YEAR', j.startDate) = :year GROUP BY FUNCTION('MONTH', j.startDate)")
    List<Object[]> countByMonth(@Param("year") int year);
    
    // 통계: 회사별 채용공고 수
    @Query("SELECT j.companyMember.company, COUNT(j) FROM JobPosting j " +
           "WHERE j.delYn = 'N' GROUP BY j.companyMember.company ORDER BY COUNT(j) DESC")
    List<Object[]> countByCompany();
}
